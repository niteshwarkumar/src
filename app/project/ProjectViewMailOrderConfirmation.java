/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.project;

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
import javax.mail.*; //for mail
import javax.mail.internet.*; //for mail
import org.apache.struts.validator.DynaValidatorForm;

/**
 *
 * @author Niteshwar
 */
public class ProjectViewMailOrderConfirmation extends Action {

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
    @SuppressWarnings("empty-statement")
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {


        DynaValidatorForm met = (DynaValidatorForm) form;
        String fromAddress = (String) met.get("fromAddress");
        String toAddress = (String) met.get("toAddress");
        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
        String subject = (String) met.get("subject");
        String body = request.getParameter("hiddennote");


        try {
            String[] emailList = toAddress.split(",");
            ProjectViewMailOrderConfirmation smtpMailSender = new ProjectViewMailOrderConfirmation();

            smtpMailSender.postMail(emailList, subject, body, emailFromAddress);
            System.out.println("mail send ");
        } catch (Exception e) {
        }
// Forward control to the specified success URI
        return (mapping.findForward("Success"));

    }
    private static final String SMTP_HOST_NAME = "xltrans.com";
    private static final String SMTP_AUTH_USER = "excelnet@xltrans.com";
    private static final String SMTP_AUTH_PWD = "3vB@zMsp";
    private static final String emailFromAddress = "excelnet@xltrans.com";

    public void postMail(String recipients[], String subject,
            String message, String from) throws MessagingException {
        boolean debug = false;

        //Set the host smtp address
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.timeout", 60000);

        Authenticator auth = new SMTPAuthenticator();
        Session session = Session.getDefaultInstance(props, auth);

        session.setDebug(debug);

        // create a message
        Message msg = new MimeMessage(session);

        // set the from and to address
        InternetAddress addressFrom = new InternetAddress(from);
        msg.setFrom(addressFrom);

        InternetAddress[] addressTo = new InternetAddress[recipients.length];
        for (int i = 0; i < recipients.length; i++) {
            addressTo[i] = new InternetAddress(recipients[i]);
        }
        msg.setRecipients(Message.RecipientType.TO, addressTo);


        // Setting the Subject and Content Type
        msg.setSubject(subject);
        msg.setContent(message, "text/html");
        Transport.send(msg);
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {

        public PasswordAuthentication getPasswordAuthentication() {
            String username = SMTP_AUTH_USER;
            String password = SMTP_AUTH_PWD;
            return new PasswordAuthentication(username, password);
        }
    }
}
