/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.project;

/**
 *
 * @author Niteshwar
 */


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import java.io.*;
import java.util.*;
import app.standardCode.*;
import java.text.DateFormat;
import javax.mail.*; //for mail
import javax.mail.internet.*; //for mail

/**
 *
 * @author Niteshwar
 */
public class ProjectViewMailDeliveryConfirmation extends Action {


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
    String fname="C://templates/deliveryconfirmation.txt";


    File file= new File(fname);
    int len;
      char[] chr = new char[4096];
      final StringBuffer buffer = new StringBuffer();
      final FileReader reader = new FileReader(file);
      try {
          while ((len = reader.read(chr)) > 0) {
              buffer.append(chr, 0, len);
          }
      } finally {
          reader.close();
      }
//System.out.println(buffer.toString());
String msg=buffer.toString();
//String pnumber= "110001Levi";
String emailMsgTxt=msg;

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
                java.util.List results = ProjectService.getInstance().getProjectList();

                ListIterator iterScroll = null;
                for(iterScroll = results.listIterator(); iterScroll.hasNext(); iterScroll.next()) {}
                   iterScroll.previous();
                   Project p = (Project) iterScroll.next();
                   projectId = String.valueOf(p.getProjectId());
         }

        Integer id = Integer.valueOf(projectId);

        //END get id of current project from either request, attribute, or cookie

        //get project
        Project p = ProjectService.getInstance().getSingleProject(id);

        //get user (project manager)
     //   User u = UserService.getInstance().getSingleUserRealName(StandardCode.getInstance().getFirstName(p.getPm()), StandardCode.getInstance().getLastName(p.getPm()));
     StringBuffer sources = new StringBuffer("");
                StringBuffer targets = new StringBuffer("");
                if(p.getSourceDocs() != null) {
                    for(Iterator iterSource = p.getSourceDocs().iterator(); iterSource.hasNext();) {
                        SourceDoc sd = (SourceDoc) iterSource.next();
                        sources.append(sd.getLanguage() + " ");
                        if(sd.getTargetDocs() != null) {
                            for(Iterator iterTarget = sd.getTargetDocs().iterator(); iterTarget.hasNext();) {
                                TargetDoc td = (TargetDoc) iterTarget.next();
                                if(!td.getLanguage().equals("All"))
                                    targets.append(td.getLanguage() + " ");
                            }
                        }
                    }
                }




 //emailMsgTxt.

 emailMsgTxt = emailMsgTxt.replace("_PROJECT_NUMBER_",(p.getContact() != null)?p.getNumber() + p.getCompany().getCompany_code():"");
 emailMsgTxt = emailMsgTxt.replaceAll("_FIRST_NAME_", StandardCode.getInstance().noNull(p.getContact().getFirst_name()));
 emailMsgTxt = emailMsgTxt.replace("_PROJECT_MANAGER_", StandardCode.getInstance().noNull(p.getPm()));
 emailMsgTxt = emailMsgTxt.replace("_PRODUCT_", StandardCode.getInstance().noNull(p.getProduct()));
 emailMsgTxt = emailMsgTxt.replace("_PRODUCTDESCRIPTION_", StandardCode.getInstance().noNull(p.getProductDescription()));
 emailMsgTxt = emailMsgTxt.replace("_SOURCE_", sources.toString());
 emailMsgTxt = emailMsgTxt.replace("_TARGET_", targets.toString());
// emailMsgTxt = emailMsgTxt.replace("_START_DATE_",(p.getStartDate() != null) ? DateFormat.getDateInstance(DateFormat.SHORT).format(p.getStartDate()) : "");
// emailMsgTxt = emailMsgTxt.replace("_DUE_DATE_", (p.getDueDate() != null) ? DateFormat.getDateInstance(DateFormat.SHORT).format(p.getDueDate()) : "");
// emailMsgTxt = emailMsgTxt.replace("_CURRENCY_", p.getCompany().getCcurrency());
// emailMsgTxt = emailMsgTxt.replace("_TOTAL_FEE_", (p.getProjectAmount() != null) ?  StandardCode.getInstance().formatDouble(p.getProjectAmount()) : "");
 emailMsgTxt = emailMsgTxt.replace("_DELIVERY_METHOD_", StandardCode.getInstance().noNull(p.getDeliveryMethod()));


        System.out.println(emailMsgTxt);

 String userEmailId="niteshwarkumar@gmail.com";
     String adminEmail="rdecortie@xltrans.com";
        String emailSubjectTxt  = "(TEST)"+ p.getNumber() + p.getCompany().getCompany_code() +" - Delivery Confirmation";

         try{
    String[] emailList = {userEmailId,adminEmail};
       ProjectViewMailOrderConfirmation smtpMailSender = new ProjectViewMailOrderConfirmation();
   smtpMailSender.postMail( emailList, emailSubjectTxt, emailMsgTxt, emailFromAddress);
             System.out.println("mail send ");
}catch(Exception e){

    }
return null;


// String[] emailList = {"niteshwar.kumarpatialideas.com"};
// SendEmail smtpMailSender = new SendEmail();
// smtpMailSender.postMail( emailList, emailSubjectTxt, emailMsgTxt, emailFromAddress);

}

 private static final String SMTP_HOST_NAME = "xltrans.com";
 private static final String SMTP_AUTH_USER = "excelnet@xltrans.com";
 private static final String SMTP_AUTH_PWD  = "3vB@zMsp";
 //private static final String emailSubjectTxt  = "Request for Quote Analysis :"+newQ1.;
 private static final String emailFromAddress = "excelnet@xltrans.com";

  // Add List of Email address to who email needs to be sent to
 // private static final String[] emailList = {"niteshwar.kumar@spatialideas.com"};


     public void postMail( String recipients[ ], String subject,
                            String message , String from) throws MessagingException
  {
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
    for (int i = 0; i < recipients.length; i++)
    {
        addressTo[i] = new InternetAddress(recipients[i]);
    }
    msg.setRecipients(Message.RecipientType.TO, addressTo);


    // Setting the Subject and Content Type
    msg.setSubject(subject);
    msg.setContent(message, "text/html");
    Transport.send(msg);
 }

        private class SMTPAuthenticator extends javax.mail.Authenticator
{

    public PasswordAuthentication getPasswordAuthentication()
    {
        String username = SMTP_AUTH_USER;
        String password = SMTP_AUTH_PWD;
        return new PasswordAuthentication(username, password);
    }
}
    }
