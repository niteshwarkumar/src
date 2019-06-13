//ProjectViewFeeAction.java displays client fee info
//for a project

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
import java.util.*;
import java.text.*;
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;
import org.json.JSONObject;

 
public final class ProjectViewFeeAction_Lin extends Action {


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
    @Override
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
        Set changesLst = p.getChange1s();
        JSONObject changeObj = new JSONObject();
        String verifymsg ="<font color=\"red\">Verification was not performed yet. Please check and try again.</font>";
        for(Iterator chg = changesLst.iterator(); chg.hasNext();) {
            try{
            Change1 change1 = (Change1) chg.next();
            
            JSONObject jo = new JSONObject();
            jo.put("name", change1.getName());
            jo.put("desc", change1.getDescription());
            
            if(change1.isFinalVerification()){
                jo.put("finalVerify",  "Final verification done by ");
                jo.put("finalVerifyMsg",  change1.getFinalVerificationBy()+ " on "+StandardCode.getInstance().noNull(change1.getFinalVerificationDate(), ""));
            }
            else{
                jo.put("finalVerify", verifymsg);
            }
            if(change1.isEngVerification()){
                jo.put("engVerify",  "Eng verification done by ");
                 jo.put("engVerifyMsg",  change1.getEngVerificationBy()+ " on "+StandardCode.getInstance().noNull(change1.getEngVerificationDate(), ""));
            }
            else{
                jo.put("engVerify", "");
            }
            if(change1.isDtpVerification()){
                jo.put("dtpVerify",  "Dtp verification done by ");
                jo.put("dtpVerifyMsg",  change1.getDtpVerificationBy()+ " on "+StandardCode.getInstance().noNull(change1.getDtpVerificationDate(), ""));
            }
            else{
                jo.put("dtpVerify", "");
            }
            if(change1.isClientApproval()){
                jo.put("CvOn",  "Client approval received on: ");
                jo.put("CvOnMsg",  StandardCode.getInstance().noNull(change1.getClientApprovalDate(), ""));
                jo.put("CvBy",  "Client approval received by: ");
                jo.put("CvByMsg",  StandardCode.getInstance().noNull(change1.getClientApprovalSrc(), ""));
                jo.put("CvNote",  "Note: ");
                jo.put("CvNoteMsg",  StandardCode.getInstance().noNull(change1.getClientApprovalDesc(), ""));
           
            }
            jo.put("number", change1.getNumber());
            jo.put("changeId", change1.getChange1Id());
            changeObj.put(change1.getNumber(), jo);
            }catch(Exception e){
               e.printStackTrace();
            }
        }



        //for each source add each sources' Tasks
        List totalLinTasks = new ArrayList();
        TreeSet<String> changes = new TreeSet();
//        List totalDtpTasks = new ArrayList();
//        List totalOthTasks = new ArrayList();

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
                   
                    String change = StandardCode.getInstance().noNull(lt.getChangeDesc());
                    if(!changes.contains(change)){
                        changes.add(change);
                    }
                }
            }
        }

        //array for display in jsp
        LinTask[] linTasksArray = (LinTask[]) totalLinTasks.toArray(new LinTask[0]);

        //START set up lin dates
        for(int i = 0; i < totalLinTasks.size(); i++) {
            if(linTasksArray[i].getSentDateDate() != null) {
                request.setAttribute("linTasksProjectSentArray" + linTasksArray[i].getLinTaskId(), DateFormat.getDateInstance(DateFormat.SHORT).format(linTasksArray[i].getSentDateDate()));
            }
            else {
                request.setAttribute("linTasksProjectSentArray" + linTasksArray[i].getLinTaskId(), "");
            }
        }
        for(int i = 0; i < totalLinTasks.size(); i++) {
            if(linTasksArray[i].getIcrSentDate() != null) {
                request.setAttribute("linTasksProjectICRSentArray" + linTasksArray[i].getLinTaskId(), DateFormat.getDateInstance(DateFormat.SHORT).format(linTasksArray[i].getIcrSentDate()));
            }
            else {
                request.setAttribute("linTasksProjectICRSentArray" + linTasksArray[i].getLinTaskId(), "");
            }
        }

        for(int i = 0; i < totalLinTasks.size(); i++) {
            if(linTasksArray[i].getDueDateDate() != null) {
                request.setAttribute("linTasksProjectDueArray" + linTasksArray[i].getLinTaskId(), DateFormat.getDateInstance(DateFormat.SHORT).format(linTasksArray[i].getDueDateDate()));
            }
            else {
                request.setAttribute("linTasksProjectDueArray" + linTasksArray[i].getLinTaskId(), "");
            }
        }
        for(int i = 0; i < totalLinTasks.size(); i++) {
            if(linTasksArray[i].getReceivedDateDate() != null) {
                request.setAttribute("linTasksProjectReceivedArray" + linTasksArray[i].getLinTaskId(), DateFormat.getDateInstance(DateFormat.SHORT).format(linTasksArray[i].getReceivedDateDate()));
            }
            else {
                request.setAttribute("linTasksProjectReceivedArray" + linTasksArray[i].getLinTaskId(), "");
            }
        }
        for(int i = 0; i < totalLinTasks.size(); i++) {
            if(linTasksArray[i].getIcrRecievedDate() != null) {
                request.setAttribute("linTasksProjectICRReceivedArray" + linTasksArray[i].getLinTaskId(), DateFormat.getDateInstance(DateFormat.SHORT).format(linTasksArray[i].getIcrRecievedDate()));
            }
            else {
                request.setAttribute("linTasksProjectICRReceivedArray" + linTasksArray[i].getLinTaskId(), "");
            }
        }
        for(int i = 0; i < totalLinTasks.size(); i++) {
            if(linTasksArray[i].getInvoiceDateDate() != null) {
                request.setAttribute("linTasksProjectInvoiceArray" + linTasksArray[i].getLinTaskId(), DateFormat.getDateInstance(DateFormat.SHORT).format(linTasksArray[i].getInvoiceDateDate()));
            }
            else {
                request.setAttribute("linTasksProjectInvoiceArray" + linTasksArray[i].getLinTaskId(), "");
            }

           // //System.out.println("    Lintask ICRRRRRRRRRRRRRRRRRR"+linTasksArray[i].getIcrRecievedDate());
        }


        DynaValidatorForm qvg = (DynaValidatorForm) form;
        //find total of LinTasks
        double linTaskTotal = 0;
        double linMinFeeTotal = 0;
        String ICRCheck="";
//        JSONArray changesFeeArray = new JSONArray();
//        JSONObject changeObj = new JSONObject();
        for(String change : changes){
            
          double changeTotal = 0;  
        for(int i = 0; i < linTasksArray.length; i++) {
            if(StandardCode.getInstance().noNull(linTasksArray[i].getChangeDesc()).equalsIgnoreCase(change)){
                
            if(linTasksArray[i].getDollarTotalFee() != null) {
               
                //remove comma's
                String linTotal = linTasksArray[i].getDollarTotalFee();
                linTotal = linTotal.replaceAll(",","");
                if("".equals(linTotal)){
                    linTotal = "0";
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

                linTaskTotal += total;
                changeTotal += total;
            }
            
            if(ICRCheck.equalsIgnoreCase(linTasksArray[i].getTargetLanguage())){
            //qvg.set("ICR","1");
            request.setAttribute("ICR" + linTasksArray[i].getLinTaskId(), "0");
            }else{

            ICRCheck=linTasksArray[i].getTargetLanguage();
            //qvg.set("ICR","0");
            request.setAttribute("ICR" + linTasksArray[i].getLinTaskId(), "1");           
            }         
             
            }
        }
        
        
        JSONObject jo = new JSONObject();
        if(!change.equalsIgnoreCase("")){
            if(changeObj.has(change))
             jo = changeObj.getJSONObject(change);
        } 
        jo.put("usd", StandardCode.getInstance().formatDouble(changeTotal));
        jo.put("euro", StandardCode.getInstance().formatDouble(changeTotal/p.getEuroToUsdExchangeRate().doubleValue()));
        changeObj.put(change, jo);
        }
Double linTotal =0.0;

        //team (fee) TOTAL
        double teamTotal = 0;
        if(p.getProjectAmount() != null) {
            teamTotal = p.getProjectAmount().doubleValue();
        }
        //if(p.get)
        double linTaskTotalEuro=linTaskTotal/p.getEuroToUsdExchangeRate().doubleValue();
        //place TaskTotals in request as formated string
        request.setAttribute("linTaskTotal", StandardCode.getInstance().formatDouble(new Double(linTaskTotal)));
        request.setAttribute("linTaskTotalEuro", StandardCode.getInstance().formatDouble(new Double(linTaskTotalEuro)));
        request.setAttribute("teamTotal", StandardCode.getInstance().formatDouble(new Double(teamTotal)));
        request.setAttribute("changes", changes);
        //System.out.println(changeObj);
        request.setAttribute("changesFee", changeObj);

        qvg.set("linTasksProject", linTasksArray);

        qvg.set("pmPercent", p.getPmPercent());
        qvg.set("pmPercentDollarTotal", p.getPmPercentDollarTotal());
        qvg.set("rushPercent", p.getRushPercent());
        qvg.set("rushPercentDollarTotal", p.getRushPercentDollarTotal());
        //HERE down is standard and does not need to change when adding task blocks
        //place this project into request for further display in jsp page
        request.setAttribute("project", p);
        //add this project id to cookies; this will remember the last project
        response.addCookie(StandardCode.getInstance().setCookie("projectViewId", projectId));
        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("projectViewTab", "Fee"));
        //an update of totals may be required
        Integer autoUpdate = (Integer) request.getAttribute("AutoUpdate");
        if(autoUpdate != null && autoUpdate.equals(new Integer(0))) { //make sure it was just updated
            return (mapping.findForward("AutoUpdate"));
        }


        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
