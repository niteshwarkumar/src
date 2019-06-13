/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.extjs.actions;

import app.project.Change1;
import app.project.Project;
import app.project.ProjectService;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import java.text.SimpleDateFormat;
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
public class ApproveChangesPreAction extends Action {

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
        
//        Integer id = Integer.valueOf(projectId);
        
        request.setAttribute("projectViewId", projectId);

        String chg = request.getParameter("change");
        String changeDesc = request.getParameter("changeDesc");
        request.setAttribute("changeDesc", changeDesc);
        String destination = request.getParameter("destination");
        request.getAttribute("destination");
      
        DynaValidatorForm qvg = (DynaValidatorForm) form;
        boolean blank = false;
        if (!StandardCode.getInstance().noNull(chg).isEmpty()) {
              int chgId = Integer.parseInt(chg);
              request.setAttribute("changeId", chgId);
            if(chgId>0){
            Change1 change1 = ProjectService.getInstance().getSingleChange(chgId);
            qvg.set("name", StandardCode.getInstance().noNull(change1.getName()));
            qvg.set("number", StandardCode.getInstance().noNull(change1.getNumber()));
            qvg.set("finalVerificationBy", StandardCode.getInstance().noNull(change1.getFinalVerificationBy()));
            qvg.set("dtpVerificationBy", StandardCode.getInstance().noNull(change1.getDtpVerificationBy()));
            qvg.set("engVerificationBy", StandardCode.getInstance().noNull(change1.getEngVerificationBy()));
            qvg.set("description", StandardCode.getInstance().noNull(change1.getDescription()));
            qvg.set("locationFiles", StandardCode.getInstance().noNull(change1.getLocationFiles()));
            qvg.set("clientApprovalSrc", StandardCode.getInstance().noNull(change1.getClientApprovalSrc()));
            qvg.set("clientApprovalDesc", StandardCode.getInstance().noNull(change1.getClientApprovalDesc()));
            
            if(StandardCode.getInstance().noNull(change1.getName()).isEmpty()){
                request.setAttribute("cname", StandardCode.getInstance().noNull(change1.getNumber()));
//                qvg.set("cname", StandardCode.getInstance().noNull(change1.getNumber()));
            }else{
                request.setAttribute("cname", change1.getName());
//                qvg.set("cname", StandardCode.getInstance().noNull(change1.getNumber()));
            }
            SimpleDateFormat myFormat = new SimpleDateFormat("MM-dd-yyyy");
            try {
                qvg.set("finalVerificationDate", "" + StandardCode.getInstance().noNull((myFormat.format(change1.getFinalVerificationDate()))));
            } catch (Exception e) {
                qvg.set("finalVerificationDate", "");
            }
            try {
                qvg.set("engVerificationDate", "" + StandardCode.getInstance().noNull((myFormat.format(change1.getEngVerificationDate()))));
            } catch (Exception e) {
                qvg.set("engVerificationDate", "");
            }
            try {
                qvg.set("dtpVerificationDate", "" + StandardCode.getInstance().noNull((myFormat.format(change1.getDtpVerificationDate()))));
            } catch (Exception e) {
                qvg.set("dtpVerificationDate", "");
            }
            try {
                qvg.set("clientApprovalDate", "" + StandardCode.getInstance().noNull((myFormat.format(change1.getClientApprovalDate()))));
            } catch (Exception e) {
                qvg.set("clientApprovalDate", "");
            }
            try {
                qvg.set("finalVerification", "" + change1.isFinalVerification());
            } catch (Exception e) {
                qvg.set("finalVerification", "");
            }
            try {
                qvg.set("engVerification", "" + change1.isEngVerification());
            } catch (Exception e) {
                qvg.set("engVerification", "");
            }
            try {
                qvg.set("dtpVerification", "" + change1.isDtpVerification());
            } catch (Exception e) {
                qvg.set("dtpVerification", "");
            }
            try {
                qvg.set("clientApproval", "" + change1.isClientApproval());
            } catch (Exception e) {
                qvg.set("clientApproval", "");
            }
            }else{blank = true;}
        } else {
           blank = true;
        }
        if(blank){
          
            request.setAttribute("changeId", 0);
            qvg.set("description", "");
            qvg.set("finalVerificationBy", "");
            qvg.set("dtpVerificationBy", "");
            qvg.set("engVerificationBy", "");
            qvg.set("description", "");
            qvg.set("finalVerificationDate", "");
            qvg.set("engVerificationDate", "");
            qvg.set("dtpVerificationDate", "");
            qvg.set("finalVerification", "");
            qvg.set("engVerification", "");
            qvg.set("dtpVerification", "");
        }
        

        //END get id of current project from either request, attribute, or cookie
        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
