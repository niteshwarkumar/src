/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.project;

import app.standardCode.StandardCode;
import app.tools.SendMail;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import java.util.*;
import app.user.User;
import app.user.UserService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.mail.*; //for mail
import javax.mail.internet.*; //for mail
import org.apache.struts.validator.DynaValidatorForm;

/**
 *
 * @author Niteshwar
 */
public class ProjectViewSendMailConfirmation extends Action {

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
    @Override
    @SuppressWarnings("empty-statement")
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        DynaValidatorForm met = (DynaValidatorForm) form;
        String fromAddress = (String) met.get("fromAddress");
        String toAddress = (String) met.get("toAddress");
        String ccAddress = (String) met.get("ccAddress");
        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
        String subject = (String) met.get("subject");
        String body = request.getParameter("hiddennote");
        String mailSource = request.getParameter("source");

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
            java.util.List results = ProjectService.getInstance().getProjectList();

            ListIterator iterScroll = null;
            for (iterScroll = results.listIterator(); iterScroll.hasNext(); iterScroll.next()) {
            }
            iterScroll.previous();
            Project p = (Project) iterScroll.next();
            projectId = String.valueOf(p.getProjectId());
        }

        Integer id = Integer.valueOf(projectId);
        

        //END get id of current project from either request, attribute, or cookie               
        //get project
        Project p = ProjectService.getInstance().getSingleProject(id);
//        String aename[] =p.getAe().split(" ");
//        UserService.getInstance().getSingleUserRealName(aename[0], p.getAe().replace(aename[0], "").trim());
try{
        if (mailSource.equalsIgnoreCase("orderConfirmation")) {
            p.setOrderConfirmationMail("on");
            ProjectService.getInstance().updateProject(p);
        }
        if (mailSource.equalsIgnoreCase("deliveryConfirmation")) {
            p.setDeliveryConfirmationMail("on");
            ProjectService.getInstance().updateProject(p);
        }
}catch(Exception e){}
        try {
            toAddress = toAddress.replaceAll(";", ",").replaceAll(" ", "");
            ccAddress = ccAddress.replaceAll(";", ",").replaceAll(" ", "");
            String[] emailList = toAddress.split(",");
            String[] emailListCC = ccAddress.split(",");
            ProjectViewSendMailConfirmation smtpMailSender = new ProjectViewSendMailConfirmation();

            smtpMailSender.postMail(emailList, emailListCC, subject, body, StandardCode.emailFromAddress);
            //System.out.println("mail send ");
        } catch (Exception e) {
        }
// Forward control to the specified success URI
        return (mapping.findForward("Success"));

    }
    private void execute(SendMail sm){
		ExecutorService execSvc = Executors.newSingleThreadExecutor();
		execSvc.execute(sm);
		execSvc.shutdown();
		
	}

    // Add List of Email address to who email needs to be sent to
    public void postMail(String recipients[], String recipientsCC[], String subject,
            String message, String from) throws MessagingException {
        
        SendMail sm = new SendMail(recipients, recipientsCC, subject, message, from);
        execute(sm);
        
        
//        boolean debug = false;
//
//        // create a message
//        Message msg = StandardCode.getInstance().getMimeMessage();
//        // set the from and to address
////        InternetAddress addressFrom = new InternetAddress(StandardCode.emailFromAddress);
////        
////       
////        //msg.setFrom(addressFrom);
//        Set<String> temp = new HashSet<String>(Arrays.asList(recipients));
//        recipients = temp.toArray(new String[temp.size()]);
//        InternetAddress[] addressTo = new InternetAddress[recipients.length];
//        for (int i = 0; i < recipients.length; i++) {
//            addressTo[i] = new InternetAddress(recipients[i]);
//        }
//
//        msg.setRecipients(Message.RecipientType.TO, addressTo);
//
//        try {
//            temp = new HashSet<String>(Arrays.asList(recipientsCC));
//            recipientsCC = temp.toArray(new String[temp.size()]);
//            InternetAddress[] addressCC = new InternetAddress[recipientsCC.length];
//            for (int i = 0; i < recipientsCC.length; i++) {
//                addressCC[i] = new InternetAddress(recipientsCC[i]);
//            }
//            msg.setRecipients(Message.RecipientType.CC, addressCC);
//        } catch (Exception e) {
//        }
////       msg.setFrom("YOUR FROM ADDRESS"); 
//
//// message.setContent(messageText, "text/html");
////         message.setContent(multipart);
////         Transport.send(message);
//        // Setting the Subject and Content Type
//        msg.setSubject(subject);
//        msg.setContent(message, "text/html");
//        Transport.send(msg);
    }

}
