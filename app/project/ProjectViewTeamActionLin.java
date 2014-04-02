//ProjectViewTeamAction.java displays team/tracking info
//for a project

package app.project;

import app.resource.Resource;
import app.resource.ResourceService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import java.util.*;
import java.text.*;
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;


public final class ProjectViewTeamActionLin extends Action {


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
    @SuppressWarnings("unchecked")
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
        
        //START get id of current project from either request, attribute, or cookie 
        //id of project from request
	String projectId = null;
	projectId = request.getParameter("projectViewId");
        
        //check attribute in request
        if(projectId == null) {
            projectId = (String) request.getAttribute("projectViewId");
        }
        
        //id of project from cookie
        if(projectId == null) {            
            projectId = StandardCode.getInstance().getCookie("projectViewId", request.getCookies());
        }

        //default project to last if not in request or cookie
        if(projectId == null) {
                List results = ProjectService.getInstance().getProjectList();
                                
                ListIterator iterScroll = null;
                for(iterScroll = results.listIterator(); iterScroll.hasNext(); iterScroll.next()) {}
                   iterScroll.previous();
                   Project p = (Project) iterScroll.next();  
                   projectId = String.valueOf(p.getProjectId());
         }            
        
        Integer id = Integer.valueOf(projectId);
        
        //END get id of current project from either request, attribute, or cookie 
              
        
        //get project to edit
        Project p = ProjectService.getInstance().getSingleProject(id); 
        
        //get this project's sources
        Set sources = p.getSourceDocs();
        
        //for each source add each sources' Tasks
        List totalLinTasks = new ArrayList();
        List totalLinTasks1 = new ArrayList();
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
                    if(lt.getTaskName().equalsIgnoreCase("Translation"))
                    totalLinTasks.add(lt);
                    //System.out.println(sd.getLanguage()+"--------"+td.getLanguage()+"---------"+lt.getTaskName());
                }
                for(Iterator linTaskIter = td.getLinTasks().iterator(); linTaskIter.hasNext();) {
                    LinTask lt = (LinTask) linTaskIter.next();
                   if(lt.getTaskName().startsWith("Editing"))
                    totalLinTasks.add(lt);
                    //System.out.println(sd.getLanguage()+"--------"+td.getLanguage()+"---------"+lt.getTaskName());
                }
                for(Iterator linTaskIter = td.getLinTasks().iterator(); linTaskIter.hasNext();) {
                    LinTask lt = (LinTask) linTaskIter.next();
                    if(lt.getTaskName().equalsIgnoreCase("ICR "))
                    totalLinTasks.add(lt);
                    //System.out.println(sd.getLanguage()+"--------"+td.getLanguage()+"---------"+lt.getTaskName());
                }
                for(Iterator linTaskIter = td.getLinTasks().iterator(); linTaskIter.hasNext();) {
                    LinTask lt = (LinTask) linTaskIter.next();
                    if(lt.getTaskName().equalsIgnoreCase("Proofreading "))
                    totalLinTasks.add(lt);
                    //System.out.println(sd.getLanguage()+"--------"+td.getLanguage()+"---------"+lt.getTaskName());
                }
                for(Iterator linTaskIter = td.getLinTasks().iterator(); linTaskIter.hasNext();) {
                    LinTask lt = (LinTask) linTaskIter.next();
                   if(!lt.getTaskName().equalsIgnoreCase("Proofreading ")&&!lt.getTaskName().equalsIgnoreCase("ICR ")&&!lt.getTaskName().equalsIgnoreCase("Editing")&&!lt.getTaskName().equalsIgnoreCase("Translation"))
                   totalLinTasks.add(lt);
                    //System.out.println(sd.getLanguage()+"--------"+td.getLanguage()+"---------"+lt.getTaskName());
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
     //   Collections.sort(totalLinTasks, CompareTaskLin.getInstance());
      //  Collections.sort(totalLinTasks, CompareTaskLanguages.getInstance());
        Collections.sort(totalEngTasks, CompareTaskEng.getInstance());
        Collections.sort(totalDtpTasks, CompareTaskDtp.getInstance());
        Collections.sort(totalOthTasks, CompareTaskOth.getInstance());
        
        //array for display in jsp
        LinTask[] linTasksArray = (LinTask[]) totalLinTasks.toArray(new LinTask[0]);
        EngTask[] engTasksArray = (EngTask[]) totalEngTasks.toArray(new EngTask[0]);
        DtpTask[] dtpTasksArray = (DtpTask[]) totalDtpTasks.toArray(new DtpTask[0]);
        OthTask[] othTasksArray = (OthTask[]) totalOthTasks.toArray(new OthTask[0]);
        
        //START set up lin dates
        for(int i = 0; i < totalLinTasks.size(); i++) {
            if(linTasksArray[i].getSentDateDate() != null) {
                request.setAttribute("linTasksProjectSentArray" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(linTasksArray[i].getSentDateDate()));
            }
            else {
                request.setAttribute("linTasksProjectSentArray" + String.valueOf(i), "");
            }
        }
        for(int i = 0; i < totalLinTasks.size(); i++) {
            if(linTasksArray[i].getDueDateDate() != null) {
                request.setAttribute("linTasksProjectDueArray" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(linTasksArray[i].getDueDateDate()));
            }
            else {
                request.setAttribute("linTasksProjectDueArray" + String.valueOf(i), "");
            }
        }
        for(int i = 0; i < totalLinTasks.size(); i++) {
            if(linTasksArray[i].getReceivedDateDate() != null) {
                request.setAttribute("linTasksProjectReceivedArray" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(linTasksArray[i].getReceivedDateDate()));
            }
            else {
                request.setAttribute("linTasksProjectReceivedArray" + String.valueOf(i), "");
            }
        }
         for(int i = 0; i < totalLinTasks.size(); i++) {
            if(linTasksArray[i].getIcrRecievedDate() != null) {
                request.setAttribute("linTasksProjectICRReceivedArray" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(linTasksArray[i].getIcrRecievedDate()));
            }
            else {
                request.setAttribute("linTasksProjectICRReceivedArray" + String.valueOf(i), "");
            }
             System.out.println(linTasksArray[i].getIcrRecievedDate());
        }


          for(int i = 0; i < totalLinTasks.size(); i++) {
            if(linTasksArray[i].getIcrSentDate() != null) {
                request.setAttribute("linTasksProjectICRSentArray" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(linTasksArray[i].getIcrSentDate()));
            }
            else {
                request.setAttribute("linTasksProjectICRSentArray" + String.valueOf(i), "");
            }
        }
for(int i=0;i<totalLinTasks.size();i++){
request.setAttribute(projectId, log);
}
        for(int i = 0; i < totalLinTasks.size(); i++) {
            if(linTasksArray[i].getInvoiceDateDate() != null) {
                request.setAttribute("linTasksProjectInvoiceArray" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(linTasksArray[i].getInvoiceDateDate()));
            }
            else {
                request.setAttribute("linTasksProjectInvoiceArray" + String.valueOf(i), "");
            }
        }
        
        
//        for(int i = 0; i < totalLinTasks.size(); i++) {
//            try {
//                
//                if(linTasksArray[i].getMulti().equalsIgnoreCase("Minimum Fee")&&linTasksArray[i].getMinFee()<=0.00) {               
//                Resource res = ResourceService.getInstance().getSingleResource(Integer.parseInt(linTasksArray[i].getPersonName()));                
//                request.setAttribute("linTasksProjectMinFee" + String.valueOf(i), res.getMin());
//                System.out.println(res.getMin());
//            }
//            else {
//                 request.setAttribute("linTasksProjectMinFee" + String.valueOf(i), linTasksArray[i].getMinFee());
//                 
//                 System.out.println("---"+linTasksArray[i].getMinFee());
//            }
//                
//                 } catch (Exception e) {
//                     request.setAttribute("linTasksProjectMinFee" + String.valueOf(i), 0.00);
//            }
//            
//        }
        
        
        
        //END set up lin dates
        
        //START set up eng dates
        for(int i = 0; i < totalEngTasks.size(); i++) {
            if(engTasksArray[i].getSentDateDate() != null) {
                request.setAttribute("engTasksProjectSentArray" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(engTasksArray[i].getSentDateDate()));
            }
            else {
                request.setAttribute("engTasksProjectSentArray" + String.valueOf(i), "");
            }
        }
        for(int i = 0; i < totalEngTasks.size(); i++) {
            if(engTasksArray[i].getDueDateDate() != null) {
                request.setAttribute("engTasksProjectDueArray" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(engTasksArray[i].getDueDateDate()));
            }
            else {
                request.setAttribute("engTasksProjectDueArray" + String.valueOf(i), "");
            }
        }
        for(int i = 0; i < totalEngTasks.size(); i++) {
            if(engTasksArray[i].getReceivedDateDate() != null) {
                request.setAttribute("engTasksProjectReceivedArray" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(engTasksArray[i].getReceivedDateDate()));
            }
            else {
                request.setAttribute("engTasksProjectReceivedArray" + String.valueOf(i), "");
            }
        }
        for(int i = 0; i < totalEngTasks.size(); i++) {
            if(engTasksArray[i].getInvoiceDateDate() != null) {
                request.setAttribute("engTasksProjectInvoiceArray" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(engTasksArray[i].getInvoiceDateDate()));
            }
            else {
                request.setAttribute("engTasksProjectInvoiceArray" + String.valueOf(i), "");
            }
        }
        //END set up eng dates
        
        //START set up dtp dates
        for(int i = 0; i < totalDtpTasks.size(); i++) {
            if(dtpTasksArray[i].getSentDateDate() != null) {
                request.setAttribute("dtpTasksProjectSentArray" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(dtpTasksArray[i].getSentDateDate()));
            }
            else {
                request.setAttribute("dtpTasksProjectSentArray" + String.valueOf(i), "");
            }
        }
        for(int i = 0; i < totalDtpTasks.size(); i++) {
            if(dtpTasksArray[i].getDueDateDate() != null) {
                request.setAttribute("dtpTasksProjectDueArray" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(dtpTasksArray[i].getDueDateDate()));
            }
            else {
                request.setAttribute("dtpTasksProjectDueArray" + String.valueOf(i), "");
            }
        }
        for(int i = 0; i < totalDtpTasks.size(); i++) {
            if(dtpTasksArray[i].getReceivedDateDate() != null) {
                request.setAttribute("dtpTasksProjectReceivedArray" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(dtpTasksArray[i].getReceivedDateDate()));
            }
            else {
                request.setAttribute("dtpTasksProjectReceivedArray" + String.valueOf(i), "");
            }
        }
        for(int i = 0; i < totalDtpTasks.size(); i++) {
            if(dtpTasksArray[i].getInvoiceDateDate() != null) {
                request.setAttribute("dtpTasksProjectInvoiceArray" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(dtpTasksArray[i].getInvoiceDateDate()));
            }
            else {
                request.setAttribute("dtpTasksProjectInvoiceArray" + String.valueOf(i), "");
            }
        }
        //END set up dtp dates
        
        //START set up oth dates
        for(int i = 0; i < totalOthTasks.size(); i++) {
            if(othTasksArray[i].getSentDateDate() != null) {
                request.setAttribute("othTasksProjectSentArray" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(othTasksArray[i].getSentDateDate()));
            }
            else {
                request.setAttribute("othTasksProjectSentArray" + String.valueOf(i), "");
            }
        }
        for(int i = 0; i < totalOthTasks.size(); i++) {
            if(othTasksArray[i].getDueDateDate() != null) {
                request.setAttribute("othTasksProjectDueArray" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(othTasksArray[i].getDueDateDate()));
            }
            else {
                request.setAttribute("othTasksProjectDueArray" + String.valueOf(i), "");
            }
        }
        for(int i = 0; i < totalOthTasks.size(); i++) {
            if(othTasksArray[i].getReceivedDateDate() != null) {
                request.setAttribute("othTasksProjectReceivedArray" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(othTasksArray[i].getReceivedDateDate()));
            }
            else {
                request.setAttribute("othTasksProjectReceivedArray" + String.valueOf(i), "");
            }
        }
        for(int i = 0; i < totalOthTasks.size(); i++) {
            if(othTasksArray[i].getInvoiceDateDate() != null) {
                request.setAttribute("othTasksProjectInvoiceArray" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(othTasksArray[i].getInvoiceDateDate()));
            }
            else {
                request.setAttribute("othTasksProjectInvoiceArray" + String.valueOf(i), "");
            }
        }
        //END set up oth dates
        
        //find total of LinTasks
        double linTaskTotal = 0;
        double linMinFeeTotal = 0;
        for(int i = 0; i < linTasksArray.length; i++) {
            if(linTasksArray[i].getInternalDollarTotal() != null) {
                //remove comma's
                String linTotal = linTasksArray[i].getInternalDollarTotal();
                linTotal = linTotal.replaceAll(",","");
                if("".equals(linTotal)){
                    linTotal = "0";
                }
                Double total = Double.valueOf(linTotal);
                
               if("EURO".equals(linTasksArray[i].getCurrencyFee())){
                     if(p.getEuroToUsdExchangeRate()==null)
                        total=new Double(total.doubleValue()*StandardCode.getInstance().getEuro());
                    else
                        total = new Double(total.doubleValue()*p.getEuroToUsdExchangeRate().doubleValue());
                }
                
                linTaskTotal += total.doubleValue();
            }
        }


           for(int i = 0; i < linTasksArray.length; i++) {
            if(linTasksArray[i].getDollarTotalFee() != null) {
                //remove comma's
                Double linTotal = linTasksArray[i].getMinFee();
                //linTotal = linTotal.replaceAll(",","");
                if("".equals(linTotal)){
                    linTotal = 0.0;
                }
                Double total = Double.valueOf(linTotal);

                 if("EURO".equals(linTasksArray[i].getCurrencyFee())){
                   // total = new Double(total.doubleValue()*p.getEuroToUsdExchangeRate().doubleValue());
                    if(p.getEuroToUsdExchangeRate()==null)
                        total=new Double(total.doubleValue()*StandardCode.getInstance().getEuro());
                    else
                        total = new Double(total.doubleValue()*p.getEuroToUsdExchangeRate().doubleValue());
                   // qvg.set("TotalFee",Double.parseDouble(p.getSubDollarTotal())*StandardCode.getInstance().getEuro());
                }

                linTaskTotal += total.doubleValue();
            }

        }
        
        //find total of EngTasks
        double engTaskTotal = 0;
        for(int i = 0; i < engTasksArray.length; i++) {
            if(engTasksArray[i].getInternalDollarTotal() != null) {
                //remove comma's
                String engTotal = engTasksArray[i].getInternalDollarTotal();
                engTotal = engTotal.replaceAll(",","");
                if("".equals(engTotal)){
                    engTotal = "0";
                }
                Double total = Double.valueOf(engTotal);
                
                if("EURO".equals(linTasksArray[i].getCurrencyFee())){
                     if(p.getEuroToUsdExchangeRate()==null)
                        total=new Double(total.doubleValue()*StandardCode.getInstance().getEuro());
                    else
                        total = new Double(total.doubleValue()*p.getEuroToUsdExchangeRate().doubleValue());
                }
                
                engTaskTotal += total.doubleValue();
            }
        }    
        
        //find total of DtpTasks
        double dtpTaskTotal = 0;
        for(int i = 0; i < dtpTasksArray.length; i++) {
            if(dtpTasksArray[i].getInternalDollarTotal() != null) {
                //remove comma's
                String dtpTotal = dtpTasksArray[i].getInternalDollarTotal();
                dtpTotal = dtpTotal.replaceAll(",","");
                if("".equals(dtpTotal)){
                    dtpTotal = "0";
                }
                Double total = Double.valueOf(dtpTotal);
                
                if("EURO".equals(linTasksArray[i].getCurrencyFee())){
                     if(p.getEuroToUsdExchangeRate()==null)
                        total=new Double(total.doubleValue()*StandardCode.getInstance().getEuro());
                    else
                        total = new Double(total.doubleValue()*p.getEuroToUsdExchangeRate().doubleValue());
                }
                
                dtpTaskTotal += total.doubleValue();
            }
        }    
        
        //find total of OthTasks
        double othTaskTotal = 0;
        for(int i = 0; i < othTasksArray.length; i++) {
            if(othTasksArray[i].getInternalDollarTotal() != null) {
                //remove comma's
                String othTotal = othTasksArray[i].getInternalDollarTotal();
                othTotal = othTotal.replaceAll(",","");
                if("".equals(othTotal)){
                    othTotal = "0";
                }
                Double total = Double.valueOf(othTotal);
                
                 if("EURO".equals(linTasksArray[i].getCurrencyFee())){
                     if(p.getEuroToUsdExchangeRate()==null)
                        total=new Double(total.doubleValue()*StandardCode.getInstance().getEuro());
                    else
                        total = new Double(total.doubleValue()*p.getEuroToUsdExchangeRate().doubleValue());
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
        request.setAttribute("teamTotal", StandardCode.getInstance().formatDouble(new Double(teamTotal)));
            
        
        //place all Tasks for this project into the form for display
        DynaValidatorForm qvg = (DynaValidatorForm) form;
        qvg.set("linTasksProject", linTasksArray);        
        qvg.set("engTasksProject", engTasksArray);
        qvg.set("dtpTasksProject", dtpTasksArray);
        qvg.set("othTasksProject", othTasksArray);
        
        
        //HERE down is standard and does not need to change when adding task blocks
        //place this project into request for further display in jsp page
        request.setAttribute("project", p);
        
        //add this project id to cookies; this will remember the last project
        response.addCookie(StandardCode.getInstance().setCookie("projectViewId", projectId));
        
        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("projectViewTab", "Team"));
        
        //an update of totals may be required
        Integer autoUpdate = (Integer) request.getAttribute("AutoUpdate");
        if(autoUpdate != null && autoUpdate.equals(new Integer(0))) { //make sure it was just updated
            return (mapping.findForward("AutoUpdate"));
        }
        
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
