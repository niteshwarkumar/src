/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.extjs.actions;

import app.inteqa.INReport;
import app.inteqa.InteqaService;
import app.project.Change1;
import app.project.Project;
import app.project.ProjectService;
import app.quote.Quote1;
import app.quote.QuotePriority;
import app.quote.QuoteService;
import app.security.SecurityService;
import app.standardCode.DateService;
import app.standardCode.StandardCode;
import java.util.List;
import java.util.ListIterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.DynaValidatorForm;

/**
 *
 * @author niteshwar
 */
public class ApproveChanges extends Action {

    // ----------------------------------------------------- Instance Variables
    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log
            = LogFactory.getLog("org.apache.struts.webapp.Example");

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
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)

        //START get id of current project from either request, attribute, or cookie
        //id of project from request
        String projectId = null;
        projectId = request.getParameter("projectViewId");

        //check attribute in request
        if (projectId == null) {
            projectId = (String) request.getAttribute("projectViewId");
        }

        //id of project from cookie
        if (projectId == null) {
            projectId = StandardCode.getInstance().getCookie("projectViewId", request.getCookies());
        }

        //default project to last if not in request or cookie
        if (projectId == null) {
            List results = ProjectService.getInstance().getProjectList();

            ListIterator iterScroll = null;
            for (iterScroll = results.listIterator(); iterScroll.hasNext(); iterScroll.next()) {
            }
            iterScroll.previous();
            Project p = (Project) iterScroll.next();
            projectId = String.valueOf(p.getProjectId());

        }

        Integer id = Integer.valueOf(projectId);
        Project proj = ProjectService.getInstance().getSingleProject(id);
        String changeId = request.getParameter("changeId");
        String changeDesc = request.getParameter("changeDesc");

        Change1 change = ProjectService.getInstance().getSingleChange(Integer.parseInt(changeId));
        if(change == null){ 
            change = new Change1();
            change.setNumber(changeDesc);
            ProjectService.getInstance().addChangeWithProject(proj, change);
        }

        //END get id of current project from either request, attribute, or cookie
        DynaValidatorForm upd = (DynaValidatorForm) form;

        String verified = (String) upd.get("finalVerification");
        String verifiedBy = (String) upd.get("finalVerificationBy");
        String verifiedDate = (String) upd.get("finalVerificationDate");

        String dtpverified = (String) upd.get("dtpVerification");
        String dtpverifiedBy = (String) upd.get("dtpVerificationBy");
        String dtpverifiedDate = (String) upd.get("dtpVerificationDate");

        String engverified = (String) upd.get("engVerification");
        String engverifiedBy = (String) upd.get("engVerificationBy");
        String engverifiedDate = (String) upd.get("engVerificationDate");
        String description = (String) upd.get("description");
        String changename = (String) upd.getString("name");
        
        String locationFiles = (String) upd.get("locationFiles");
        String clientApproval = (String) upd.getString("clientApproval");
        String clientApprovalDate = (String) upd.getString("clientApprovalDate");
        String clientApprovalSrc = (String) upd.getString("clientApprovalSrc");
        String clientApprovalDesc = (String) upd.getString("clientApprovalDesc");

//Change1s 
        try{
            change.setDescription(description);
        }catch(Exception e){
            change.setDescription("");
        }
        try{
            change.setName(changename);
        }catch(Exception e){
            
        }
         if (clientApproval.equalsIgnoreCase("on")) {
            change.setClientApproval(true);
        } else {
            change.setClientApproval(false);
        }
         try{
            change.setLocationFiles(locationFiles);
        }catch(Exception e){
            change.setLocationFiles("");
        }
        
        try {
            if (verifiedDate.length() > 0) { //if present
                change.setFinalVerificationDate(DateService.getInstance().convertDate(verifiedDate).getTime());
            } else {
                change.setFinalVerificationDate(null);
            }
        } catch (Exception e) {
            //System.out.println("Date Errooooorr " + e.getMessage());
        }
        try {
            change.setFinalVerificationBy(verifiedBy);
        } catch (Exception e) {
            change.setFinalVerificationBy("");
        }
        if (verified.equalsIgnoreCase("on")) {
            change.setFinalVerification(true);
        } else {
            change.setFinalVerification(false);
        }

        try {
            if (dtpverifiedDate.length() > 0) { //if present
                change.setDtpVerificationDate(DateService.getInstance().convertDate(dtpverifiedDate).getTime());
            } else {
                change.setDtpVerificationDate(null);
            }
        } catch (Exception e) {
            //System.out.println("Date Errooooorr " + e.getMessage());
        }
        try {
            change.setDtpVerificationBy(dtpverifiedBy);
        } catch (Exception e) {
            change.setDtpVerificationBy("");
        }

        if (dtpverified.equalsIgnoreCase("on")) {
            change.setDtpVerification(true);
        } else {
            change.setDtpVerification(false);
        }

        try {
            if (engverifiedDate.length() > 0) { //if present
                change.setEngVerificationDate(DateService.getInstance().convertDate(engverifiedDate).getTime());
            } else {
                change.setEngVerificationDate(null);
            }
        } catch (Exception e) {
            //System.out.println("Date Errooooorr " + e.getMessage());
        }
        try {
            change.setEngVerificationBy(engverifiedBy);
        } catch (Exception e) {
            change.setEngVerificationBy("");
        }

        if (engverified.equalsIgnoreCase("on")) {
            change.setEngVerification(true);
        } else {
            change.setEngVerification(false);
        }
        try {
            if (clientApprovalDate.length() > 0) { //if present
                change.setClientApprovalDate(DateService.getInstance().convertDate(clientApprovalDate).getTime());
            } else {
                change.setClientApprovalDate(null);
            }
        } catch (Exception e) {
            //System.out.println("Date Errooooorr " + e.getMessage());
        }
        try{
            change.setClientApprovalSrc(clientApprovalSrc);
        }catch(Exception e){
            change.setClientApprovalSrc("");
        }
        try{
            change.setClientApprovalDesc(clientApprovalDesc);
        }catch(Exception e){
            change.setClientApprovalDesc("");
        }

        ProjectService.getInstance().updateChange(change);
        String destination = request.getParameter("destination");
        if(!StandardCode.getInstance().noNull(destination).isEmpty()){
        return (mapping.findForward(destination));
        }

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
