/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.tools;

import app.admin.AdminService;
import app.admin.MailingListClient;
import app.client.ClientContact;
import app.client.ClientService;
import app.security.SecurityService;
import app.user.User;
import app.user.UserService;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
 * @author Nishika
 */
public class AdminMassEmailAction extends Action {

  // ----------------------------------------------------- Instance Variables 
  /**
   * The
   * <code>Log</code> instance for this application.
   */
  private Log log =
          LogFactory.getLog("org.apache.struts.webapp.Example");

  // --------------------------------------------------------- Public Methods 
  /**
   * Process the specified HTTP request, and create the corresponding
   * HTTP response (or forward to another web component that will
   * create it). Return an
   * <code>ActionForward</code> instance describing where and how
   * control should be forwarded, or
   * <code>null</code> if the response has already been completed.
   *
   * @param mapping The ActionMapping used to select this instance
   * @param form The optional ActionForm bean for this request (if
   * any)
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

    //values for generating mass emails 
    DynaValidatorForm met = (DynaValidatorForm) form;
    String fromAddress = (String) met.get("fromAddress");

    User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
    String subject = (String) met.get("subject");
//        String body = (String) met.get("body"); 
    String body = request.getParameter("hiddennote");


    AdminMassEmailAction amea = new AdminMassEmailAction();
    String mailId = request.getParameter("mailId");

    List mailList = AdminService.getInstance().getMailingList(Integer.parseInt(mailId), "C");
    String[] emailList = new String[mailList.size()];
    for (int i = 0; i < mailList.size(); i++) {
      MailingListClient mc = (MailingListClient) mailList.get(i);
      ClientContact cc = ClientService.getInstance().getSingleClientContact(mc.getClientId());
//        for(; iterFirstName.hasNext();) { 
      //get this email info 
//      String emailFirstName = cc.getFirst_name();
//      emailFirstName = emailFirstName.trim();
//      String emailEmail = cc.getEmail_address();
//      emailEmail = emailEmail.trim();
//      emailList[i] = emailEmail.trim();
//      String emailClient = cc.getCompany().getCompany_name();
//      emailClient = emailClient.trim();
//      String emailLastName = cc.getLast_name();
//      emailLastName = emailLastName.trim();
      String[] toEmail = new String[1];
      toEmail[0] = cc.getEmail_address();
//      SendMassMail sendMassMail = new SendMassMail();
//      sendMassMail.postMail(cc.getEmail_address(), subject, body, fromAddress);
      RunnableDemo RD = amea.getRunnableDemo(toEmail, subject, body, fromAddress);
      Thread t = new Thread(RD);
      t.start();
    }
//         body = body.replaceAll ("&lt;First Name&gt;", emailFirstName); 
//            body = body.replaceAll("&lt;To Email&gt;", emailEmail); 
//            body = body.replaceAll("&lt;Client&gt;", emailClient); 
//            body = body.replaceAll("&lt;Last Name&gt;", emailLastName); 



    

    // Forward control to the specified success URI 
    return (mapping.findForward("Success"));
  }

  private RunnableDemo getRunnableDemo(String[] recipients, String subject, String message, String from) {
    return new RunnableDemo(recipients, subject, message, from); //To change body of generated methods, choose Tools | Templates.
  }
  
  class RunnableDemo implements Runnable {

    private Thread t;
    private String[] recipients;
    private String subject;
    private String message;
    private String from;

    RunnableDemo(String[] recipients, String subject, String message, String from) {
      this.recipients = recipients;
      this.subject = subject;
      this.message = message;
      this.from = from;
      //System.out.println("Creating " + recipients);
    }

    @Override
    public void run() {
          boolean debug = false;
    SendEmail sendMassMail = new SendEmail();
      try {
        sendMassMail.postMail(recipients, subject, message, from);
      } catch (MessagingException ex) {
        Logger.getLogger(AdminMassEmailAction.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

}
