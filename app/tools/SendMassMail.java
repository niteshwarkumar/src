/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.tools;

import app.client.Client;
import app.client.ClientService;
import app.extjs.global.LanguageAbs;
import app.extjs.vo.Product;
import app.project.DtpTask;
import app.project.EngTask;
import app.project.LinTask;
import app.project.OthTask;
import app.project.SourceDoc;
import app.project.TargetDoc;
import app.quote.Client_Quote;
import app.quote.Quote1;
import app.quote.QuoteService;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

/**
 *
 * @author Nishika
 */
public class SendMassMail {

  public void postMail(String recipients[], String ccRecipients[], String subject,
          String message, String from) throws MessagingException {
   

    // create a message
    Message msg = StandardCode.getInstance().getMimeMessage();

    // set the from and to address
    //InternetAddress addressFrom = new InternetAddress(from);
    //msg.setFrom(addressFrom);

    InternetAddress[] addressTo = new InternetAddress[recipients.length];
    for (int i = 0; i < recipients.length; i++) {
      addressTo[i] = new InternetAddress(recipients[i]);
    }
    msg.setRecipients(Message.RecipientType.TO, addressTo);

    InternetAddress[] addressCC = new InternetAddress[ccRecipients.length];
    for (int i = 0; i < ccRecipients.length; i++) {
      addressCC[i] = new InternetAddress(ccRecipients[i]);
    }
    msg.setRecipients(Message.RecipientType.CC, addressCC);
//

    // Setting the Subject and Content Type
    msg.setSubject(subject);
    msg.setContent(message, "text/html");
    Transport.send(msg);
  }
  
  public void postMail(String recipient, String subject,
          String message, String from) throws MessagingException {
    
    // create a message
    Message msg = StandardCode.getInstance().getMimeMessage();

    // set the from and to address
    //InternetAddress addressFrom = new InternetAddress(from);
    //msg.setFrom(addressFrom);
    
    InternetAddress[] addressTo = new InternetAddress[1];
    
      addressTo[0] = new InternetAddress(recipient);
  
    msg.setRecipients(Message.RecipientType.TO, addressTo);

    // Setting the Subject and Content Type
    msg.setSubject(subject);
    msg.setContent(message, "text/html");
    Transport.send(msg);
  }

  public void postMassMail(String recipients[], String subject,
          String message, String from) throws MessagingException {

    SendMassMail sendMail = new SendMassMail();
    for (int i = 0; i < recipients.length; i++) {

      RunnableDemo RD = sendMail.getRunnableDemo(recipients[i], subject, message, from);
      Thread t = new Thread(RD);
      t.start();
    }

  }

  private RunnableDemo getRunnableDemo(String recipients, String subject, String message, String from) {
    return new RunnableDemo(recipients, subject, message, from); //To change body of generated methods, choose Tools | Templates.
  }

  class RunnableDemo implements Runnable {

    private Thread t;
    private String recipients;
    private String subject;
    private String message;
    private String from;

    RunnableDemo(String recipients, String subject, String message, String from) {
      this.recipients = recipients;
      this.subject = subject;
      this.message = message;
      this.from = from;
      //System.out.println("Creating " + recipients);
    }

    @Override
    public void run() {
      try {
        

        boolean debug = false;

       
        // create a message
        Message msg = StandardCode.getInstance().getMimeMessage();

        InternetAddress[] addressTo = new InternetAddress[1];
        addressTo[0] = new InternetAddress(recipients);
        msg.setRecipients(Message.RecipientType.TO, addressTo);
        // set the from and to address
        //InternetAddress addressFrom = new InternetAddress(from);
        //msg.setFrom(addressFrom);
        msg.setSubject(subject);
        msg.setContent(message, "text/html");

        Transport.send(msg);
        //System.out.println("Sent " + recipients);

      } catch (AddressException ex) {
        Logger.getLogger(SendMassMail.class.getName()).log(Level.SEVERE, null, ex);
      } catch (MessagingException ex) {
        Logger.getLogger(SendMassMail.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

}