//HrAdminEmployeeMaintenanceChangeDates1Action.java gets the hr info
//from a form.  It then uploads the new values (username/password) for
//this employee to the db

package app.hrAdmin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.*;
import app.user.*;
import app.standardCode.*;
import app.security.*;
import app.tools.SendEmail;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import sun.misc.BASE64Encoder;
import javax.mail.*; //for mail
import javax.mail.internet.*; //for mail
import javax.servlet.http.HttpSession;

public final class HrAdminEmployeeMaintenanceChangeUsernamePassword1Action extends Action {


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
        
        //PRIVS check that hrAdmin user is viewing this page 
        if(!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "hrAdmin")) {
            return (mapping.findForward("accessDenied"));
        }//END PRIVS check that hrAdmin user is viewing this page
        
        //get user to edit from form hidden field
        String hrAdminUserId = request.getParameter("hrAdminUserId");
        User u = UserService.getInstance().getSingleUser(Integer.valueOf(hrAdminUserId));
        User currentUser=UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));

        //values for update from form; change what is stored in db to these values
        DynaValidatorForm uvg = (DynaValidatorForm) form;        
        
        String username = (String) uvg.get("username");
        String password = (String) uvg.get("password");
        
        //update the user's values       
        if(username.length() > 0) { //if present            
            u.setUsername(username);
        }
        if(password.length() > 0) { //if present, hash password for safe db store
            MessageDigest md = null;
            try {
              md = MessageDigest.getInstance("SHA"); //step 2
            }
            catch(NoSuchAlgorithmException e) {
              throw new RuntimeException(e);
            }
            try {
              md.update(password.getBytes("UTF-8")); //step 3
            }
            catch(UnsupportedEncodingException e) {
              throw new RuntimeException(e);
            }
            byte raw[] = md.digest(); //step 4
            String hash = (new BASE64Encoder()).encode(raw); //step 5
            u.setPassword(hash);
        }
                
        //set updated values to db
        UserService.getInstance().updateUser(u);
        String firstName=u.getFirstName();
          String msg="<font size='2'><span style='font-family: Verdana,Arial,Helvetica,sans-serif;'>Dear <span style='color: rgb(255, 0, 0);'>"+firstName+"</span>,<br><br style='font-family: Verdana,Arial,Helvetica,sans-serif;'></span><span style='font-family: Verdana,Arial,Helvetica,sans-serif;'>We have created an account for you on ExcelNet, Excel Translations' proprietary intra/extranet system. <br><br> Below you will find the information you need to enter our system, please keep this only to yourself in a secure place.  </span><br style='font-family: Verdana,Arial,Helvetica,sans-serif;'><span style='font-family: Verdana,Arial,Helvetica,sans-serif;'><br>User Name: "+username+"</span><br style='font-family: Verdana,Arial,Helvetica,sans-serif;'><span style='font-family: Verdana,Arial,Helvetica,sans-serif;'>Password&nbsp;&nbsp;: "+password+"</span><br style='font-family: Verdana,Arial,Helvetica,sans-serif;'><br style='font-family: Verdana,Arial,Helvetica,sans-serif;'><span style='font-family: Verdana,Arial,Helvetica,sans-serif;'>You can log in into your Account on by clicking </span><a style='font-family: Verdana,Arial,Helvetica,sans-serif;' href='http://excelnet.xltrans.com'>here</a><span style='font-family: Verdana,Arial,Helvetica,sans-serif;'>.<br><br><br>Best Regards,</span><br style='font-family: Verdana,Arial,Helvetica,sans-serif;'><br style='font-family: Verdana,Arial,Helvetica,sans-serif;'><span style='font-family: Verdana,Arial,Helvetica,sans-serif; Best regards,<br style='font-family: Verdana,Arial,Helvetica,sans-serif;'></font><font style='font-family: Verdana,Arial,Helvetica,sans-serif;' size='2'><span class='Apple-style-span' style='border-collapse: separate; color: rgb(0, 0, 0); font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: normal; orphans: 2; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; font-size: medium;'><span class='Apple-style-span' style='color: rgb(59, 105, 119); font-size: 11px; text-align: left;'><br> ExcelNet Administrator<br>Excel Translations, Inc.<br><br><img src=https://excelnet.xltrans.com/logo/images/excel-logo-blue.jpg></span></span></font><br> ";
        String emailMsgTxt=msg;
        String emailSubjectTxt  = "New User Account";
      String adminEmail;
      String userEmailId="excelnet@xltrans.com";
if(!u.getWorkEmail1().equalsIgnoreCase("")){

userEmailId=u.getWorkEmail1();
}else if(!u.getWorkEmail2().equalsIgnoreCase("")){
userEmailId=u.getWorkEmail2();
}else{userEmailId="excelnet@xltrans.com";


}
if(currentUser.getWorkEmail1()==null||currentUser.getWorkEmail1().equalsIgnoreCase("")){
adminEmail="excelnet@xltrans.com";

}else adminEmail=currentUser.getWorkEmail1();




try{
    String[] emailList = {userEmailId,adminEmail};
        SendEmail smtpMailSender = new SendEmail();
    smtpMailSender.postMail( emailList, emailSubjectTxt, emailMsgTxt, StandardCode.emailFromAddress);
}catch(Exception e){

// String[] emailList = {"niteshwar.kumarpatialideas.com"};
// SendEmail smtpMailSender = new SendEmail();
// smtpMailSender.postMail( emailList, emailSubjectTxt, emailMsgTxt, emailFromAddress);

}


               
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }




  // Add List of Email address to who email needs to be sent to
 // private static final String[] emailList = {"niteshwar.kumar@spatialideas.com"};


     public void postMail( String recipients[ ], String subject,
                            String message , String from) throws MessagingException
  {
    
    // create a message
    Message msg = StandardCode.getInstance().getMimeMessage();

    // set the from and to address
    //InternetAddress addressFrom = new InternetAddress(from);
    //msg.setFrom(addressFrom);

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





}
