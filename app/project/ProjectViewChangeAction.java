//ProjectViewAccountingAction.java gets the current project's accounting
//info and places it in an attribute for display

package app.project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.*;
import java.util.*;
import java.text.*;
import app.security.*;
import app.standardCode.*;

public final class ProjectViewChangeAction extends Action {


    // ----------------------------------------------------- Instance Variables


    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log =
        LogFactory.getLog("org.apache.struts.webapp.Example");


    // --------------------------------------------------------- Public Methods


    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @exception Exception if business logic throws an exception
     */
    public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
	throws Exception {

	// Extract attributes we will need
	MessageResources messages = getResources(request);	

	// save errors
	ActionMessages errors = new ActionMessages();
        
        //START check for login (security)
        if(!SecurityService.getInstance().checkForLogin(request.getSession(false))) { 
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)
	
        //PRIVS check that correct user is viewing this page 
        if(!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "viewAccountTab")) {
            return (mapping.findForward("accessDenied"));
        }//END PRIVS check that correct user is viewing this page
        
        //get current project ID
        
        //id from request attribute
        String projectId = (String) request.getAttribute("projectViewId");
        
        //id from cookie
        if(projectId == null) {
            projectId = StandardCode.getInstance().getCookie("projectViewId", request.getCookies());	
        }
        
        Integer id = Integer.valueOf(projectId);
        
        
        //get project with id from database
        Project p = ProjectService.getInstance().getSingleProject(id);
        
        //get total of each task: both actual (team tab) and budget (fee tab)
        
        //START actual
        //get this project's sources
        Set sources = p.getSourceDocs();
        
        //for each source add each sources' Tasks
        List totalLinTasks = new ArrayList();
        List totalEngTasks = new ArrayList();
        List totalDtpTasks = new ArrayList();
        List totalOthTasks = new ArrayList();
        
        //for each source
        for(Iterator sourceIter = sources.iterator(); sourceIter.hasNext();) {
            SourceDoc sd = (SourceDoc) sourceIter.next();
            
            //for each target of this source
         
            
            for(Iterator linTargetIter = sd.getTargetDocs().iterator(); linTargetIter.hasNext();) {
                TargetDoc td = (TargetDoc) linTargetIter.next();
                
                //for each lin Task of this target
                for(Iterator linTaskIter = td.getLinTasks().iterator(); linTaskIter.hasNext();) {
                    LinTask lt = (LinTask) linTaskIter.next();
                    lt.setChangeNo(sd.getChangeNo().intValue());
                    totalLinTasks.add(lt);
                }
                
                //for each eng Task of this target
                for(Iterator engTaskIter = td.getEngTasks().iterator(); engTaskIter.hasNext();) {
                    EngTask et = (EngTask) engTaskIter.next();
                    et.setChangeNo(sd.getChangeNo().intValue());
                    totalEngTasks.add(et);
                }
                
                //for each dtp Task of this target
                for(Iterator dtpTaskIter = td.getDtpTasks().iterator(); dtpTaskIter.hasNext();) {
                    DtpTask dt = (DtpTask) dtpTaskIter.next();
                    dt.setChangeNo(sd.getChangeNo().intValue());
                    totalDtpTasks.add(dt);
                }
                
                //for each oth Task of this target
                for(Iterator othTaskIter = td.getOthTasks().iterator(); othTaskIter.hasNext();) {
                    OthTask ot = (OthTask) othTaskIter.next();
                    totalOthTasks.add(ot);
                }             
            }   
        }
     
       
        
        //Sort by task (orderNum), then source (language), then target (language)
        Collections.sort(totalLinTasks, CompareTaskLin.getInstance());
        Collections.sort(totalEngTasks, CompareTaskEng.getInstance());
        Collections.sort(totalDtpTasks, CompareTaskDtp.getInstance());
        Collections.sort(totalOthTasks, CompareTaskOth.getInstance());
        
        //array for display in jsp
        LinTask[] linTasksArray = (LinTask[]) totalLinTasks.toArray(new LinTask[0]);
        EngTask[] engTasksArray = (EngTask[]) totalEngTasks.toArray(new EngTask[0]);
        DtpTask[] dtpTasksArray = (DtpTask[]) totalDtpTasks.toArray(new DtpTask[0]);
        OthTask[] othTasksArray = (OthTask[]) totalOthTasks.toArray(new OthTask[0]);
                
        //find total of LinTasks
        double linTaskTotal = 0;
        double linTaskTotalChanges = 0;
        
        double linTaskTotalEuro = 0;
        double linTaskTotalChangesEuro = 0;
        double linTaskTotalUsd = 0;
        double linTaskTotalChangesUsd = 0;
        
        
        for(int i = 0; i < linTasksArray.length; i++) {
            if(linTasksArray[i].getInternalDollarTotal() != null) {
                //remove comma's
               String linTotal = linTasksArray[i].getInternalDollarTotal();
//                String linTotal = linTasksArray[i].getDollarTotalFee();
                linTotal = linTotal.replaceAll(",","");
                if("".equals(linTotal)){
                    linTotal = "0";
                }
                Double total = Double.valueOf(linTotal);
                System.out.println("rray[i]..................................."+linTasksArray[i].getChangeDesc());
                if("EURO".equals(linTasksArray[i].getCurrency())){
                    
                    if(linTasksArray[i].getChangeDesc()==null){
                        linTaskTotalEuro += total.doubleValue();
                      }else{
                        linTaskTotalChangesEuro += total.doubleValue();
                      }
                    
                    
                     if(p.getEuroToUsdExchangeRate()==null)
                        total=new Double(total.doubleValue()*StandardCode.getInstance().getEuro());
                    else
                        total = new Double(total.doubleValue()*p.getEuroToUsdExchangeRate().doubleValue());;
                }else{
                    if(linTasksArray[i].getChangeDesc()==null){
                        linTaskTotalUsd += total.doubleValue();
                      }else{
                        linTaskTotalChangesUsd += total.doubleValue();
                      }
                }
                
                  if(linTasksArray[i].getChangeDesc()==null){
                    linTaskTotal += total.doubleValue();
                  }else{
                    linTaskTotalChanges += total.doubleValue();
                  }
            }
        }     
        
        //find total of EngTasks
        double engTaskTotal = 0;
        double engTaskTotalChanges = 0;
        double engTaskTotalEuro = 0;
        double engTaskTotalChangesEuro = 0;
        double engTaskTotalUsd = 0;
        double engTaskTotalChangesUsd = 0;
        
        for(int i = 0; i < engTasksArray.length; i++) {
            if(engTasksArray[i].getInternalDollarTotal() != null) {
                //remove comma's
                String engTotal = engTasksArray[i].getInternalDollarTotal();
                engTotal = engTotal.replaceAll(",","");
                if("".equals(engTotal)){
                    engTotal = "0";
                }
                Double total = Double.valueOf(engTotal);
                
                if("EURO".equals(engTasksArray[i].getInternalCurrency())){
                    
                    if(engTasksArray[i].getChangeDesc()==null){
                        engTaskTotalEuro += total.doubleValue();
                      }else{
                        engTaskTotalChangesEuro += total.doubleValue();
                      }
                    
                     if(p.getEuroToUsdExchangeRate()==null)
                        total=new Double(total.doubleValue()*StandardCode.getInstance().getEuro());
                    else
                        total = new Double(total.doubleValue()*p.getEuroToUsdExchangeRate().doubleValue());;
                }else{
                    if(engTasksArray[i].getChangeDesc()==null){
                        engTaskTotalUsd += total.doubleValue();
                      }else{
                        engTaskTotalChangesUsd += total.doubleValue();
                      }
                    
                }
                
                if(engTasksArray[i].getChangeDesc()==null){
                    engTaskTotal += total.doubleValue();
                }else{
                    engTaskTotalChanges += total.doubleValue();
                }
                
            }
        }    
        
        //find total of DtpTasks
        double dtpTaskTotal = 0;
        double dtpTaskTotalChanges = 0;
        double dtpTaskTotalEuro = 0;
        double dtpTaskTotalChangesEuro = 0;
        double dtpTaskTotalUsd = 0;
        double dtpTaskTotalChangesUsd = 0;
        
        for(int i = 0; i < dtpTasksArray.length; i++) {
            if(dtpTasksArray[i].getInternalDollarTotal() != null) {
                //remove comma's
                String dtpTotal = dtpTasksArray[i].getInternalDollarTotal();
                dtpTotal = dtpTotal.replaceAll(",","");
                if("".equals(dtpTotal)){
                    dtpTotal = "0";
                }
                Double total = Double.valueOf(dtpTotal);
                
                if("EURO".equals(dtpTasksArray[i].getInternalCurrency())){
                    if(dtpTasksArray[i].getChangeDesc()==null){
                        dtpTaskTotalEuro += total.doubleValue();
                      }else{
                        dtpTaskTotalChangesEuro += total.doubleValue();
                      }
                    
                     if(p.getEuroToUsdExchangeRate()==null)
                        total=new Double(total.doubleValue()*StandardCode.getInstance().getEuro());
                    else
                        total = new Double(total.doubleValue()*p.getEuroToUsdExchangeRate().doubleValue());;
                }else{
                    if(dtpTasksArray[i].getChangeDesc()==null){
                        dtpTaskTotalUsd += total.doubleValue();
                      }else{
                        dtpTaskTotalChangesUsd += total.doubleValue();
                      }
                }
                
                
                if(dtpTasksArray[i].getChangeDesc()==null){
                    dtpTaskTotal += total.doubleValue();
                }else{
                    dtpTaskTotalChanges += total.doubleValue();
                }
            }
        }    
        
        //find total of OthTasks
        double othTaskTotal = 0;
        double othTaskTotalEuro = 0;
        double othTaskTotalUsd = 0;
        
        for(int i = 0; i < othTasksArray.length; i++) {
            if(othTasksArray[i].getInternalDollarTotal() != null) {
                //remove comma's
                String othTotal = othTasksArray[i].getInternalDollarTotal();
                othTotal = othTotal.replaceAll(",","");
                if("".equals(othTotal)){
                    othTotal = "0";
                }
                Double total = Double.valueOf(othTotal);
                if("EURO".equals(othTasksArray[i].getInternalCurrency())){
                    othTaskTotalEuro+=total.doubleValue();
                     if(p.getEuroToUsdExchangeRate()==null)
                        total=new Double(total.doubleValue()*StandardCode.getInstance().getEuro());
                    else
                        total = new Double(total.doubleValue()*p.getEuroToUsdExchangeRate().doubleValue());;
                }else{
                    othTaskTotalUsd+=total.doubleValue();
                }
                
                othTaskTotal += total.doubleValue();
            }
        }    
              
        //find team TOTAL
        double teamTotal = linTaskTotal + engTaskTotal + dtpTaskTotal + othTaskTotal;
        //find team TOTAL
        double teamTotalChanges = linTaskTotalChanges + engTaskTotalChanges + dtpTaskTotalChanges;
        
        if(p.getLegacyCost() != null && p.getLegacyCost().length() > 0) {
            String teamTotalTemp = p.getLegacyCost().replaceAll(",", "");
            teamTotal = new Double(teamTotalTemp).doubleValue();
        }
        
        //place TaskTotals in request as formated string
        request.setAttribute("linTaskTotal", StandardCode.getInstance().formatDouble(new Double(linTaskTotal)));
        request.setAttribute("engTaskTotal", StandardCode.getInstance().formatDouble(new Double(engTaskTotal)));
        request.setAttribute("dtpTaskTotal", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotal)));
        
        request.setAttribute("linTaskTotalEuro", StandardCode.getInstance().formatDouble(new Double(linTaskTotalEuro)));
        request.setAttribute("engTaskTotalEuro", StandardCode.getInstance().formatDouble(new Double(engTaskTotalEuro)));
        request.setAttribute("dtpTaskTotalEuro", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotalEuro)));
        
        request.setAttribute("linTaskTotalUsd", StandardCode.getInstance().formatDouble(new Double(linTaskTotalUsd)));
        request.setAttribute("engTaskTotalUsd", StandardCode.getInstance().formatDouble(new Double(engTaskTotalUsd)));
        request.setAttribute("dtpTaskTotalUsd", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotalUsd)));
        
        
        request.setAttribute("linTaskTotalChanges", StandardCode.getInstance().formatDouble(new Double(linTaskTotalChanges)));
        request.setAttribute("engTaskTotalChanges", StandardCode.getInstance().formatDouble(new Double(engTaskTotalChanges)));
        request.setAttribute("dtpTaskTotalChanges", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotalChanges)));
        
        request.setAttribute("linTaskTotalChangesEuro", StandardCode.getInstance().formatDouble(new Double(linTaskTotalChangesEuro)));
        request.setAttribute("engTaskTotalChangesEuro", StandardCode.getInstance().formatDouble(new Double(engTaskTotalChangesEuro)));
        request.setAttribute("dtpTaskTotalChangesEuro", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotalChangesEuro)));
        
        
        request.setAttribute("linTaskTotalChangesUsd", StandardCode.getInstance().formatDouble(new Double(linTaskTotalChangesUsd)));
        request.setAttribute("engTaskTotalChangesUsd", StandardCode.getInstance().formatDouble(new Double(engTaskTotalChangesUsd)));
        request.setAttribute("dtpTaskTotalChangesUsd", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotalChangesUsd)));
        
        
        request.setAttribute("othTaskTotal", StandardCode.getInstance().formatDouble(new Double(othTaskTotal)));
        request.setAttribute("othTaskTotalUsd", StandardCode.getInstance().formatDouble(new Double(othTaskTotalUsd)));
        request.setAttribute("othTaskTotalEuro", StandardCode.getInstance().formatDouble(new Double(othTaskTotalEuro)));
        
        request.setAttribute("total", StandardCode.getInstance().formatDouble(new Double(teamTotal)));
        request.setAttribute("totalChanges", StandardCode.getInstance().formatDouble(new Double(teamTotalChanges)));
        //END actual
        
        //START budget
        //find total of LinTasks
        double linTaskTotalBudget = 0;
        double linTaskTotalBudgetChanges = 0;
        double linTaskTotalBudgetEuro = 0;
        double linTaskTotalBudgetChangesEuro = 0;
        double linTaskTotalBudgetUsd = 0;
        double linTaskTotalBudgetChangesUsd = 0;
        
        
        for(int i = 0; i < linTasksArray.length; i++) {
            //System.out.println("fee="+linTasksArray[i].getDollarTotalFee());
            if(linTasksArray[i].getDollarTotalFee() != null) {
                //remove comma's
                String linTotal = linTasksArray[i].getDollarTotalFee();
                linTotal = linTotal.replaceAll(",","");
                if("".equals(linTotal)){
                    linTotal = "0";
                }
                Double total = Double.valueOf(linTotal);
                 if("EURO".equals(linTasksArray[i].getCurrencyFee())){
                    
                    if(linTasksArray[i].getChangeDesc()==null){
                        linTaskTotalBudgetEuro += total.doubleValue();
                      }else{
                        linTaskTotalBudgetChangesEuro += total.doubleValue();
                      }
                    
                     if(p.getEuroToUsdExchangeRate()==null)
                        total=new Double(total.doubleValue()*StandardCode.getInstance().getEuro());
                    else
                        total = new Double(total.doubleValue()*p.getEuroToUsdExchangeRate().doubleValue());;
                }else{
                    if(linTasksArray[i].getChangeDesc()==null){
                        linTaskTotalBudgetUsd += total.doubleValue();
                      }else{
                        linTaskTotalBudgetChangesUsd += total.doubleValue();
                      }
                }
                
                if(linTasksArray[i].getChangeDesc()==null){
                    linTaskTotalBudget += total.doubleValue();
                }else{
                    linTaskTotalBudgetChanges += total.doubleValue();
                }
            }
        }     
        
        //find total of EngTasks
        double engTaskTotalBudget = 0;
        double engTaskTotalBudgetChanges = 0;
        double engTaskTotalBudgetEuro = 0;
        double engTaskTotalBudgetChangesEuro = 0;
        double engTaskTotalBudgetUsd = 0;
        double engTaskTotalBudgetChangesUsd = 0;
        
        for(int i = 0; i < engTasksArray.length; i++) {
            if(engTasksArray[i].getDollarTotal() != null) {
                //remove comma's
                String engTotal = engTasksArray[i].getDollarTotal();
                engTotal = engTotal.replaceAll(",","");
                if("".equals(engTotal)){
                    engTotal = "0";
                }
                Double total = Double.valueOf(engTotal);
                if("EURO".equals(engTasksArray[i].getCurrency())){
                    
                    if(engTasksArray[i].getChangeDesc()==null){
                        engTaskTotalBudgetEuro += total.doubleValue();
                      }else{
                        engTaskTotalBudgetChangesEuro += total.doubleValue();
                      }
                    
                    
                     if(p.getEuroToUsdExchangeRate()==null)
                        total=new Double(total.doubleValue()*StandardCode.getInstance().getEuro());
                    else
                        total = new Double(total.doubleValue()*p.getEuroToUsdExchangeRate().doubleValue());;
                }else{
                    if(engTasksArray[i].getChangeDesc()==null){
                        engTaskTotalBudgetUsd += total.doubleValue();
                      }else{
                        engTaskTotalBudgetChangesUsd += total.doubleValue();
                      }
                }
                if(engTasksArray[i].getChangeDesc()==null){
                    engTaskTotalBudget += total.doubleValue();
                }else{
                    engTaskTotalBudgetChanges += total.doubleValue();
                }
            }
        }    
        
        //find total of DtpTasks
        double dtpTaskTotalBudget = 0;
        double dtpTaskTotalBudgetChanges = 0;
         double dtpTaskTotalBudgetUsd = 0;
        double dtpTaskTotalBudgetChangesUsd = 0;
         double dtpTaskTotalBudgetEuro = 0;
        double dtpTaskTotalBudgetChangesEuro = 0;
        
        
        for(int i = 0; i < dtpTasksArray.length; i++) {
            if(dtpTasksArray[i].getDollarTotal() != null) {
                //remove comma's
                String dtpTotal = dtpTasksArray[i].getDollarTotal();
                dtpTotal = dtpTotal.replaceAll(",","");
                 if("".equals(dtpTotal)){
                    dtpTotal = "0";
                }
                Double total = Double.valueOf(dtpTotal);
                if("EURO".equals(dtpTasksArray[i].getCurrency())){
                    if(dtpTasksArray[i].getChangeDesc()==null){
                        dtpTaskTotalBudgetEuro += total.doubleValue();
                      }else{
                        dtpTaskTotalBudgetChangesEuro += total.doubleValue();
                      }
                    
                    
 if(p.getEuroToUsdExchangeRate()==null)
                        total=new Double(total.doubleValue()*StandardCode.getInstance().getEuro());
                    else
                        total = new Double(total.doubleValue()*p.getEuroToUsdExchangeRate().doubleValue());

                }else{
                    if(dtpTasksArray[i].getChangeDesc()==null){
                        dtpTaskTotalBudgetUsd += total.doubleValue();
                      }else{
                        dtpTaskTotalBudgetChangesUsd += total.doubleValue();
                      }
                }
                
                
                if(dtpTasksArray[i].getChangeDesc()==null){
                    dtpTaskTotalBudget += total.doubleValue();
                }else{
                    dtpTaskTotalBudgetChanges += total.doubleValue();
                }
            }
        }    
        
        //find total of OthTasks
        double othTaskTotalBudget = 0;
        double othTaskTotalBudgetEuro = 0;
        double othTaskTotalBudgetUsd = 0;
        
        for(int i = 0; i < othTasksArray.length; i++) {
            if(othTasksArray[i].getDollarTotal() != null) {
                //remove comma's
                String othTotal = othTasksArray[i].getDollarTotal();
                othTotal = othTotal.replaceAll(",","");
                if("".equals(othTotal)){
                    othTotal = "0";
                }
                Double total = Double.valueOf(othTotal);
                
                if("EURO".equals(othTasksArray[i].getCurrency())){
                    othTaskTotalBudgetEuro += total.doubleValue();
                     if(p.getEuroToUsdExchangeRate()==null)
                        total=new Double(total.doubleValue()*StandardCode.getInstance().getEuro());
                    else
                        total = new Double(total.doubleValue()*p.getEuroToUsdExchangeRate().doubleValue());;
                }else{
                    othTaskTotalBudgetUsd += total.doubleValue();
                }
                othTaskTotalBudget += total.doubleValue();
            }
        }    
        
      
              
        //team (fee) TOTAL
        double teamTotalBudget = 0;
        //if(p.getProjectAmount() != null) {
       //     teamTotalBudget = p.getProjectAmount().doubleValue();
       // }
        teamTotalBudget = linTaskTotalBudget + engTaskTotalBudget + dtpTaskTotalBudget+othTaskTotalBudget;
        double teamTotalBudgetChanges = linTaskTotalBudgetChanges + engTaskTotalBudgetChanges + dtpTaskTotalBudgetChanges;
        //place TaskTotals in request as formated string
        request.setAttribute("linTaskTotalBudgetChanges", StandardCode.getInstance().formatDouble(new Double(linTaskTotalBudgetChanges)));
        request.setAttribute("engTaskTotalBudgetChanges", StandardCode.getInstance().formatDouble(new Double(engTaskTotalBudgetChanges)));
        request.setAttribute("dtpTaskTotalBudgetChanges", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotalBudgetChanges)));
        request.setAttribute("othTaskTotalBudgetChanges", StandardCode.getInstance().formatDouble(new Double(othTaskTotalBudget)));
        
         request.setAttribute("linTaskTotalBudgetChangesEuro", StandardCode.getInstance().formatDouble(new Double(linTaskTotalBudgetChangesEuro)));
        request.setAttribute("engTaskTotalBudgetChangesEuro", StandardCode.getInstance().formatDouble(new Double(engTaskTotalBudgetChangesEuro)));
        request.setAttribute("dtpTaskTotalBudgetChangesEuro", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotalBudgetChangesEuro)));
        request.setAttribute("othTaskTotalBudgetChangesEuro", StandardCode.getInstance().formatDouble(new Double(othTaskTotalBudgetEuro)));
        
        
         request.setAttribute("linTaskTotalBudgetChangesUsd", StandardCode.getInstance().formatDouble(new Double(linTaskTotalBudgetChangesUsd)));
        request.setAttribute("engTaskTotalBudgetChangesUsd", StandardCode.getInstance().formatDouble(new Double(engTaskTotalBudgetChangesUsd)));
        request.setAttribute("dtpTaskTotalBudgetChangesUsd", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotalBudgetChangesUsd)));
        request.setAttribute("othTaskTotalBudgetChangesUsd", StandardCode.getInstance().formatDouble(new Double(othTaskTotalBudgetUsd)));
        
        
        
        request.setAttribute("totalBudget", StandardCode.getInstance().formatDouble(new Double(teamTotalBudget)));
        
        request.setAttribute("linTaskTotalBudget", StandardCode.getInstance().formatDouble(new Double(linTaskTotalBudget)));
        request.setAttribute("engTaskTotalBudget", StandardCode.getInstance().formatDouble(new Double(engTaskTotalBudget)));
        request.setAttribute("dtpTaskTotalBudget", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotalBudget)));
        
         request.setAttribute("linTaskTotalBudgetUsd", StandardCode.getInstance().formatDouble(new Double(linTaskTotalBudgetUsd)));
        request.setAttribute("engTaskTotalBudgetUsd", StandardCode.getInstance().formatDouble(new Double(engTaskTotalBudgetUsd)));
        request.setAttribute("dtpTaskTotalBudgetUsd", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotalBudgetUsd)));
        
         request.setAttribute("linTaskTotalBudgetEuro", StandardCode.getInstance().formatDouble(new Double(linTaskTotalBudgetEuro)));
        request.setAttribute("engTaskTotalBudgetEuro", StandardCode.getInstance().formatDouble(new Double(engTaskTotalBudgetEuro)));
        request.setAttribute("dtpTaskTotalBudgetEuro", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotalBudgetEuro)));
        
        
        request.setAttribute("totalBudgetChanges", StandardCode.getInstance().formatDouble(new Double(teamTotalBudgetChanges)));
        //END budget
        
        //START find profitability
        double linProf = 0;
        double engProf = 0;
        double dtpProf = 0;
        double othProf = 0;
        double totalProf = 0;
        
        if(linTaskTotalBudget != 0.0) {
            linProf = (linTaskTotalBudget - linTaskTotal) / linTaskTotal;
        }
        if(engTaskTotalBudget != 0.0) {
            engProf = (engTaskTotalBudget - engTaskTotal) / engTaskTotal;
        }
        if(dtpTaskTotalBudget != 0.0) {
            dtpProf = (dtpTaskTotalBudget - dtpTaskTotal) / dtpTaskTotal;
        }
        if(othTaskTotalBudget != 0.0) {
            othProf = (othTaskTotalBudget - othTaskTotal) / othTaskTotal;
        }
        if(teamTotalBudget != 0.0) {
            totalProf = (teamTotalBudget - teamTotal) / teamTotal;
        }
        request.setAttribute("linProf", StandardCode.getInstance().formatDouble(new Double(linProf * 100.0)));
        request.setAttribute("engProf", StandardCode.getInstance().formatDouble(new Double(engProf * 100.0)));
        request.setAttribute("dtpProf", StandardCode.getInstance().formatDouble(new Double(dtpProf * 100.0)));
        request.setAttribute("othProf", StandardCode.getInstance().formatDouble(new Double(othProf * 100.0)));
        request.setAttribute("totalProf", StandardCode.getInstance().formatDouble(new Double(totalProf * 100.0)));        
        //END find profitability
        
        //START find profitability Changes
        double linProfChanges = 0;
        double engProfChanges = 0;
        double dtpProfChanges = 0;
        double totalProfChanges = 0;
        
        if(linTaskTotalBudgetChanges != 0.0) {
            linProfChanges = (linTaskTotalBudgetChanges - linTaskTotalChanges) / linTaskTotalChanges;
        }
        if(engTaskTotalBudgetChanges != 0.0) {
            engProfChanges = (engTaskTotalBudgetChanges - engTaskTotalChanges) / engTaskTotalChanges;
        }
        if(dtpTaskTotalBudgetChanges != 0.0) {
            dtpProfChanges = (dtpTaskTotalBudgetChanges - dtpTaskTotalChanges) / dtpTaskTotalChanges;
        }
     
        if(teamTotalBudgetChanges != 0.0) {
            totalProfChanges = (teamTotalBudgetChanges - teamTotalChanges) / teamTotalChanges;
        }
        request.setAttribute("linProfChanges", StandardCode.getInstance().formatDouble(new Double(linProfChanges * 100.0)));
        request.setAttribute("engProfChanges", StandardCode.getInstance().formatDouble(new Double(engProfChanges * 100.0)));
        request.setAttribute("dtpProfChanges", StandardCode.getInstance().formatDouble(new Double(dtpProfChanges * 100.0)));
        request.setAttribute("totalProfChanges", StandardCode.getInstance().formatDouble(new Double(totalProfChanges * 100.0)));        
        //END find profitability
       
        
        //O
        double discountBudget = 0;
      double pmBudget = 0;
      double rushBudget = 0;
      teamTotalBudget = linTaskTotalBudget + engTaskTotalBudget + dtpTaskTotalBudget+othTaskTotalBudget;
    
     


 if(p.getPmPercent()!=null && !p.getPmPercent().equals("") ){   
        double discountRate = Double.valueOf(p.getPmPercent().replaceAll(",","")).doubleValue();
        pmBudget =  (discountRate / 100) * teamTotalBudget;
    }else if(p.getPmPercentDollarTotal()!=null && !p.getPmPercentDollarTotal().equals("")){
   
    pmBudget = Double.parseDouble(p.getPmPercentDollarTotal());
   
    }
 teamTotalBudget +=pmBudget;
 
 if(p.getRushPercent()!=null && !p.getRushPercent().equals("") ){   
        double discountRate = Double.valueOf(p.getRushPercent().replaceAll(",","")).doubleValue();
        rushBudget =  (discountRate / 100) * teamTotalBudget;
    }else if(p.getRushPercentDollarTotal()!=null && !p.getRushPercentDollarTotal().equals("")){
   
    rushBudget = Double.parseDouble(p.getRushPercentDollarTotal());
   
    }
   teamTotalBudget +=rushBudget;   
   
   
    if(p.getDiscountPercent()!=null && !p.getDiscountPercent().equals("") ){   
        double discountRate = Double.valueOf(p.getDiscountPercent().replaceAll(",","")).doubleValue();
        discountBudget =  (discountRate / 100) * teamTotalBudget;
    }else if(p.getDiscountDollarTotal()!=null && !p.getDiscountDollarTotal().equals("")){
   
    discountBudget = Double.parseDouble(p.getDiscountDollarTotal());
   
    }
teamTotalBudget -= discountBudget;

 request.setAttribute("discountBudget", StandardCode.getInstance().formatDouble(new Double(discountBudget)));
        request.setAttribute("rushBudget", StandardCode.getInstance().formatDouble(new Double(rushBudget)));
        request.setAttribute("pmBudget", StandardCode.getInstance().formatDouble(new Double(pmBudget)));
     request.setAttribute("finalFeeBudget", StandardCode.getInstance().formatDouble(new Double(rushBudget+pmBudget-discountBudget)));   
        //START prepare changes for display
        //get this project's changes
        Set changes = p.getChange1s();
        
        //array for display in jsp
        Change1[] change1sArray = (Change1[]) changes.toArray(new Change1[0]);        
                         
        //set up changeDates
        for(int i = 0; i < change1sArray.length; i++) {
            if(change1sArray[i].getChangeDate() != null) {
                request.setAttribute("dateChanged" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(change1sArray[i].getChangeDate()));
            }
            else {
                request.setAttribute("dateChanged" + String.valueOf(i), "");
            }
        }
        
        //place all changes for this project into the form for display
        DynaValidatorForm pva = (DynaValidatorForm) form;
        pva.set("change1s", change1sArray);    
        //END prepare changes for display
                
        //START prepare client invoice for display
        if(p.getInvoiceDate() != null) {
            request.setAttribute("clientInvoiceDate", DateFormat.getDateInstance(DateFormat.SHORT).format(p.getInvoiceDate()));
        }
        if(p.getInvoicePaid() != null) {
            request.setAttribute("clientInvoicePaid", DateFormat.getDateInstance(DateFormat.SHORT).format(p.getInvoicePaid()));
        }
        //END prepare client invoice for display
        
        //START NEW prepare invoices for display
        //get this project's changes
        Set clientInvoices = p.getClientInvoices();
        
        //array for display in jsp
        ClientInvoice[] clientInvoicesArray = (ClientInvoice[]) clientInvoices.toArray(new ClientInvoice[0]);        
                         
        //set up dates
        for(int i = 0; i < clientInvoicesArray.length; i++) {
            if(clientInvoicesArray[i].getInvoiceRequestDate() != null) {
                request.setAttribute("requestDate" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(clientInvoicesArray[i].getInvoiceRequestDate()));
            }
            else {
                request.setAttribute("requestDate" + String.valueOf(i), "");
            }
            if(clientInvoicesArray[i].getInvoicePaidDate() != null) {
                request.setAttribute("paidDate" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(clientInvoicesArray[i].getInvoicePaidDate()));
            }
            else {
                request.setAttribute("paidDate" + String.valueOf(i), "");
            }
        }
        
        //place all invoices for this project into the form for display
        pva.set("clientInvoices", clientInvoicesArray);    
        //END NEW prepare invoices for display
        
        //put this client into the request; in ClientViewNotesEdit.jsp, get <code>note</code> for display
        request.setAttribute("project", p);
        
        //add this project id to cookies for display
        response.addCookie(StandardCode.getInstance().setCookie("projectViewId", projectId));
        
        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("projectViewTab", "Accounting"));
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
