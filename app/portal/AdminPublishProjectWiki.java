/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.portal;

import app.project.Project;
import app.project.ProjectService;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import app.tools.SendMail;
import app.user.User;
import app.user.UserService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.mail.MessagingException;
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
public class AdminPublishProjectWiki extends Action {

//ProjectAddPreFilesAction.java adds the new
//file (pre trados) to the db and builds the one-to-many
//relationship between quote and file (quote file)
    // ----------------------------------------------------- Instance Variables
    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log
            = LogFactory.getLog("org.apache.struts.webapp.Example");
//    private static final boolean hasDTP;

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
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }

        String type = StandardCode.getInstance().noNull(request.getParameter("type"));

        if (!type.isEmpty()) {
            String pId = request.getParameter("projectId");
            Project p = ProjectService.getInstance().getSingleProject(Integer.parseInt(pId));
            User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
            if (type.equalsIgnoreCase("DEACTIVATE")) {
                PortalUtil.getInstance().removeWiki(p, u);
                return (mapping.findForward("Success"));
            } else if (type.equalsIgnoreCase("ACTIVATE")) {
                PortalUtil.getInstance().generateWiki(p, u);
                return (mapping.findForward("Success"));
            }
        }

        DynaValidatorForm met = (DynaValidatorForm) form;
        String fromAddress = (String) met.get("fromAddress");

        String pwd = (String) met.get("pwd");
        String toAddress = (String) met.get("toAddress");
        String ccAddress = (String) met.get("ccAddress");
//        String bccAddress = (String) met.get("bccAddress");
        String[] emailListBCC = request.getParameterValues("bccEmail");
        String subject = (String) met.get("subject");
        String body = request.getParameter("hiddennote");
//        String alias = (String) met.get("alias");

        try {
            toAddress = toAddress.replaceAll(";", ",").replaceAll(" ", "");
            ccAddress = ccAddress.replaceAll(";", ",").replaceAll(" ", "");
//            bccAddress = bccAddress.replaceAll(";", ",").replaceAll(" ", "");
            String[] emailList = toAddress.split(",");
            String[] emailListCC = ccAddress.split(",");
//            String[] emailListBCC = bccAddress.split(",");

            postMail(emailList, emailListCC, emailListBCC, subject, body, fromAddress, pwd);
        } catch (Exception e) {
        }
// Forward control to the specified success URI
        return (mapping.findForward("Success"));

    }

    private void execute(SendMail sm) {
        ExecutorService execSvc = Executors.newSingleThreadExecutor();
        execSvc.execute(sm);
        execSvc.shutdown();

    }

    // Add List of Email address to who email needs to be sent to
    public void postMail(String recipients[], String recipientsCC[], String recipientsBCC[], String subject,
            String message, String from, String pwd) throws MessagingException {
        SendMail sm = null;
        if (from.isEmpty()) {
            sm = new SendMail(recipients, recipientsCC, recipientsBCC, subject, message);
        } else {
            sm = new SendMail(recipients, recipientsCC, recipientsBCC, subject, message, from+"@xltrans.com", pwd);
        }
        if (sm != null) {
            execute(sm);
        }

    }

}
