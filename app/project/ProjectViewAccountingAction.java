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

public final class ProjectViewAccountingAction extends Action {


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
                    totalLinTasks.add(lt);
                }
                
                //for each eng Task of this target
                for(Iterator engTaskIter = td.getEngTasks().iterator(); engTaskIter.hasNext();) {
                    EngTask et = (EngTask) engTaskIter.next();
                    totalEngTasks.add(et);
                }
                
                //for each dtp Task of this target
                for(Iterator dtpTaskIter = td.getDtpTasks().iterator(); dtpTaskIter.hasNext();) {
                    DtpTask dt = (DtpTask) dtpTaskIter.next();
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
        double linTaskTotalUsd = 0;
        double linTaskTotalEuro = 0;
        
        for(int i = 0; i < linTasksArray.length; i++) {
            if(linTasksArray[i].getInternalDollarTotal() != null) {
                //remove comma's
                String linTotal = linTasksArray[i].getInternalDollarTotal();
                linTotal = linTotal.replaceAll(",","");
                if("".equals(linTotal)){
                    linTotal = "0";
                }
                Double total = Double.valueOf(linTotal);
                if("EURO".equals(linTasksArray[i].getInternalCurrency())){
                    linTaskTotalEuro+=total.doubleValue();
                     if(p.getEuroToUsdExchangeRate()==null)
                        total=new Double(total.doubleValue()*StandardCode.getInstance().getEuro());
                    else
                        total = new Double(total.doubleValue()*p.getEuroToUsdExchangeRate().doubleValue());;
                    
                }else{
                    linTaskTotalUsd+=total.doubleValue();
                }
                
                linTaskTotal += total.doubleValue();
            }
        }     
        
        //find total of EngTasks
        double engTaskTotal = 0;
        double engTaskTotalEuro = 0;
        double engTaskTotalUsd = 0;
        
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
                     engTaskTotalEuro+=total.doubleValue();
                     if(p.getEuroToUsdExchangeRate()==null)
                        total=new Double(total.doubleValue()*StandardCode.getInstance().getEuro());
                    else
                        total = new Double(total.doubleValue()*p.getEuroToUsdExchangeRate().doubleValue());;
                   
                }else{
                    engTaskTotalUsd+=total.doubleValue();
                }
                
                engTaskTotal += total.doubleValue();
            }
        }    
        
        //find total of DtpTasks
        double dtpTaskTotal = 0;
        double dtpTaskTotalEuro = 0;
        double dtpTaskTotalUsd = 0;
        
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
                    dtpTaskTotalEuro+=total.doubleValue();
                     if(p.getEuroToUsdExchangeRate()==null)
                        total=new Double(total.doubleValue()*StandardCode.getInstance().getEuro());
                    else
                        total = new Double(total.doubleValue()*p.getEuroToUsdExchangeRate().doubleValue());;
                    
                }else{
                    dtpTaskTotalUsd+=total.doubleValue();
                }
                
                dtpTaskTotal += total.doubleValue();
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
        
        if(p.getLegacyCost() != null && p.getLegacyCost().length() > 0) {
            String teamTotalTemp = p.getLegacyCost().replaceAll(",", "");
            teamTotal = new Double(teamTotalTemp).doubleValue();
        }
        
        //place TaskTotals in request as formated string
        request.setAttribute("linTaskTotal", StandardCode.getInstance().formatDouble(new Double(linTaskTotal)));
        request.setAttribute("engTaskTotal", StandardCode.getInstance().formatDouble(new Double(engTaskTotal)));
        request.setAttribute("dtpTaskTotal", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotal)));
        request.setAttribute("othTaskTotal", StandardCode.getInstance().formatDouble(new Double(othTaskTotal)));
        request.setAttribute("total", StandardCode.getInstance().formatDouble(new Double(teamTotal)));
        
        //EURO TOTALS
        request.setAttribute("linTaskTotalEuro", StandardCode.getInstance().formatDouble(new Double(linTaskTotalEuro)));
        request.setAttribute("engTaskTotalEuro", StandardCode.getInstance().formatDouble(new Double(engTaskTotalEuro)));
        request.setAttribute("dtpTaskTotalEuro", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotalEuro)));
        request.setAttribute("othTaskTotalEuro", StandardCode.getInstance().formatDouble(new Double(othTaskTotalEuro)));
        double totalTaskTotalEuro=(linTaskTotalEuro)+(engTaskTotalEuro)+(dtpTaskTotalEuro)+(othTaskTotalEuro);
         request.setAttribute("totalTaskTotalEuro", StandardCode.getInstance().formatDouble(new Double(totalTaskTotalEuro)));
        //USD TOTALS
        request.setAttribute("linTaskTotalUsd", StandardCode.getInstance().formatDouble(new Double(linTaskTotalUsd)));
        request.setAttribute("engTaskTotalUsd", StandardCode.getInstance().formatDouble(new Double(engTaskTotalUsd)));
        request.setAttribute("dtpTaskTotalUsd", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotalUsd)));
        request.setAttribute("othTaskTotalUsd", StandardCode.getInstance().formatDouble(new Double(othTaskTotalUsd)));
        double totalTaskTotalUsd=(linTaskTotalUsd)+(engTaskTotalUsd)+(dtpTaskTotalUsd)+(othTaskTotalUsd);
        request.setAttribute("totalTaskTotalUsd", StandardCode.getInstance().formatDouble(new Double(totalTaskTotalUsd)));


        //END actual
        
        //START budget
        //find total of LinTasks
        double linTaskTotalBudget = 0;
        double linTaskTotalBudgetEuro = 0;
        double linTaskTotalBudgetUsd = 0;
        
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
                    linTaskTotalBudgetEuro+=total.doubleValue();
                     if(p.getEuroToUsdExchangeRate()==null)
                        total=new Double(total.doubleValue()*StandardCode.getInstance().getEuro());
                    else
                        total = new Double(total.doubleValue()*p.getEuroToUsdExchangeRate().doubleValue());;
                    
                }else{
                    linTaskTotalBudgetUsd+=total.doubleValue();
                }
                
                linTaskTotalBudget += total.doubleValue();
            }
        }     
        
        //find total of EngTasks
        double engTaskTotalBudget = 0;
        double engTaskTotalBudgetEuro = 0;
        double engTaskTotalBudgetUsd = 0;
        
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
                    engTaskTotalBudgetEuro+=total.doubleValue();
                     if(p.getEuroToUsdExchangeRate()==null)
                        total=new Double(total.doubleValue()*StandardCode.getInstance().getEuro());
                    else
                        total = new Double(total.doubleValue()*p.getEuroToUsdExchangeRate().doubleValue());;
                    
                }else{
                    engTaskTotalBudgetUsd+=total.doubleValue();
                }
                
                engTaskTotalBudget += total.doubleValue();
            }
        }    
        
        //find total of DtpTasks
        double dtpTaskTotalBudget = 0;
        double dtpTaskTotalBudgetEuro = 0;
        double dtpTaskTotalBudgetUsd = 0;
        
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
                    dtpTaskTotalBudgetEuro+=total.doubleValue();
                     if(p.getEuroToUsdExchangeRate()==null)
                        total=new Double(total.doubleValue()*StandardCode.getInstance().getEuro());
                    else
                        total = new Double(total.doubleValue()*p.getEuroToUsdExchangeRate().doubleValue());;
                    
                }else{
                    dtpTaskTotalBudgetUsd+=total.doubleValue();
                }
                
                dtpTaskTotalBudget += total.doubleValue();
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
                    othTaskTotalBudgetEuro+=total.doubleValue();
                     if(p.getEuroToUsdExchangeRate()==null)
                        total=new Double(total.doubleValue()*StandardCode.getInstance().getEuro());
                    else
                        total = new Double(total.doubleValue()*p.getEuroToUsdExchangeRate().doubleValue());;
                    
                }else{
                    othTaskTotalBudgetUsd+=total.doubleValue();
                }
                othTaskTotalBudget += total.doubleValue();
            }
        }    
              
        //team (fee) TOTAL
        double teamTotalBudget = 0;
        //if(p.getProjectAmount() != null) {
       //     teamTotalBudget = p.getProjectAmount().doubleValue();
       // }
    
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


        //place TaskTotals in request as formated string
        request.setAttribute("linTaskTotalBudget", StandardCode.getInstance().formatDouble(new Double(linTaskTotalBudget)));
        request.setAttribute("engTaskTotalBudget", StandardCode.getInstance().formatDouble(new Double(engTaskTotalBudget)));
        request.setAttribute("dtpTaskTotalBudget", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotalBudget)));
        request.setAttribute("othTaskTotalBudget", StandardCode.getInstance().formatDouble(new Double(othTaskTotalBudget)));
        //euro
        request.setAttribute("linTaskTotalBudgetEuro", StandardCode.getInstance().formatDouble(new Double(linTaskTotalBudgetEuro)));
        request.setAttribute("engTaskTotalBudgetEuro", StandardCode.getInstance().formatDouble(new Double(engTaskTotalBudgetEuro)));
        request.setAttribute("dtpTaskTotalBudgetEuro", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotalBudgetEuro)));
        request.setAttribute("othTaskTotalBudgetEuro", StandardCode.getInstance().formatDouble(new Double(othTaskTotalBudgetEuro)));
        //usd
        request.setAttribute("linTaskTotalBudgetUsd", StandardCode.getInstance().formatDouble(new Double(linTaskTotalBudgetUsd)));
        request.setAttribute("engTaskTotalBudgetUsd", StandardCode.getInstance().formatDouble(new Double(engTaskTotalBudgetUsd)));
        request.setAttribute("dtpTaskTotalBudgetUsd", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotalBudgetUsd)));
        request.setAttribute("othTaskTotalBudgetUsd", StandardCode.getInstance().formatDouble(new Double(othTaskTotalBudgetUsd)));
        
        double totalTaskTotalBudgetUsd=(linTaskTotalBudgetUsd)+(engTaskTotalBudgetUsd)+(dtpTaskTotalBudgetUsd)+(othTaskTotalBudgetUsd);
         request.setAttribute("totalTaskTotalBudgetUsd", StandardCode.getInstance().formatDouble(new Double(totalTaskTotalBudgetUsd)));
         
         double totalTaskTotalBudgetEuro=(linTaskTotalBudgetEuro)+(engTaskTotalBudgetEuro)+(dtpTaskTotalBudgetEuro)+(othTaskTotalBudgetEuro);
         request.setAttribute("totalTaskTotalBudgetEuro", StandardCode.getInstance().formatDouble(new Double(totalTaskTotalBudgetEuro)));

        request.setAttribute("totalBudget", StandardCode.getInstance().formatDouble(new Double(teamTotalBudget)));
        request.setAttribute("discountBudget", StandardCode.getInstance().formatDouble(new Double(discountBudget)));
        request.setAttribute("rushBudget", StandardCode.getInstance().formatDouble(new Double(rushBudget)));
        request.setAttribute("pmBudget", StandardCode.getInstance().formatDouble(new Double(pmBudget)));
                
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
