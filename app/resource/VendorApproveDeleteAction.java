/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.resource;

import app.standardCode.StandardCode;
import app.tools.SendEmail;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
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
import sun.misc.BASE64Encoder;

/**
 *
 * @author niteshwar
 */
public class VendorApproveDeleteAction  extends Action {

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
       // System.err.println("LoginUserAction @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

	// Extract attributes we will need
	MessageResources messages = getResources(request);

	// save errors
	ActionMessages errors = new ActionMessages();
        String act = request.getParameter("act");
        String resourceId = request.getParameter("resourceId");
                
        if("approve".equalsIgnoreCase(act)){
            Resource r = ResourceService.getInstance().getSingleResource(Integer.parseInt(resourceId));
            String name = r.getFirstName()+" "+r.getLastName();
            r.setUsername(getUserName(name));
            String password = getPassword();
            String pass = password;
            if (password.length() > 0) { //if present, hash password for safe db store
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("SHA"); //step 2
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            try {
                md.update(password.getBytes("UTF-8")); //step 3
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            byte raw[] = md.digest(); //step 4
             String hash = (new BASE64Encoder()).encode(raw); //step 5
               r.setPassword(hash);
 }
            
            
            r.setIsEnabled(true);
        ResourceService.getInstance().updateResource(r);
        String msg = "<font size='2'><span style='font-family: Verdana,Arial,Helvetica,sans-serif;'>Dear <span style='color: rgb(0, 0, 0);'>"+r.getFirstName()+
                "</span>,<br><br style='font-family: Verdana,Arial,Helvetica,sans-serif;'></span><span style='font-family: Verdana,Arial,Helvetica,sans-serif;'>"
                + "We have created an account for you on ExcelNet, Excel Translations' proprietary intra/extranet system. <br><br> Below you will find the "
                + "information you need to enter our system, please keep this only to yourself in a secure place.&nbsp; </span><br style='font-family: "
                + "Verdana,Arial,Helvetica,sans-serif;'><span style='font-family: Verdana,Arial,Helvetica,sans-serif;'><br>User Name :"+r.getUsername()+"</span>"
                + "<br style='font-family: Verdana,Arial,Helvetica,sans-serif;'><span style='font-family: Verdana,Arial,Helvetica,sans-serif;'>Password"
                + "&nbsp;&nbsp; :"+password+"</span><br style='font-family: Verdana,Arial,Helvetica,sans-serif;'><br style='font-family: Verdana,Arial,Helvetica,"
                + "sans-serif;'><span style='font-family: Verdana,Arial,Helvetica,sans-serif;'>You can log in to your Account on by clicking </span>"
                + "<a style='font-family: Verdana,Arial,Helvetica,sans-serif;' href='http://excelnet.xltrans.com'>here</a><span style='font-family: Verdana,Arial,"
                + "Helvetica,sans-serif;'>.<br><br><br>Best Regards,</span><br style='font-family: Verdana,Arial,Helvetica,sans-serif;'>"
                + "<br style='font-family: Verdana,Arial,Helvetica,sans-serif;'><span style='font-family: Verdana,Arial,Helvetica,sans-serif;' "
                + "font-family:='' verdana,arial,helvetica,sans-serif;=''></span></font><font style='font-family: Verdana,Arial,Helvetica,sans-serif;' size='2'>"
                + "<span class='Apple-style-span' style='border-collapse: separate; color: rgb(0, 0, 0); font-style: normal; font-variant: normal; "
                + "font-weight: normal; letter-spacing: normal; line-height: normal; orphans: 2; text-indent: 0px; text-transform: none; white-space: normal; "
                + "widows: 2; word-spacing: 0px; font-size: medium;'><span class='Apple-style-span' style='color: rgb(59, 105, 119); font-size: 11px; "
                + "text-align: left;'><font size='1'><br> <font size='2'>ExcelNet Administrator<br>Excel Translations, Inc.</font></font><br><br>"
                + "<img src='https://excelnet.xltrans.com/logo/images/excel-logo-blue.jpg'></span></span></font><br> <br>  ";
        String emailMsgTxt = msg;
        String emailSubjectTxt = "New User Account";
        String adminEmail;
//        if (r.getEmail_address1() == null || r.getEmail_address1().equalsIgnoreCase("")) {
//            adminEmail = "excelnet@xltrans.com";
//
//        } else {
            adminEmail = r.getEmail_address1();
//        }




        try {
            String[] emailList = {adminEmail, adminEmail};
            SendEmail smtpMailSender = new SendEmail();
            smtpMailSender.postMail(emailList, emailSubjectTxt, emailMsgTxt, StandardCode.emailFromAddress);
        } catch (Exception e) {
// String[] emailList = {"niteshwar.kumarpatialideas.com"};
// SendEmail smtpMailSender = new SendEmail();
// smtpMailSender.postMail( emailList, emailSubjectTxt, emailMsgTxt, emailFromAddress);
        }
        }
       
     
        return (mapping.findForward("Success"));
        
    }

 

    private String getPassword() {
        String password = "";
         String hash = "";
        char[] alphabets = {'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M',
            'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm',
            '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '-', '+', '=', '|'};
        Long randNum = Math.round(Math.random() * 67);
        
       
        Long pwdLength = Math.round(Math.random() * 3);
        
        for(int i = 0; i<6+pwdLength ;i++){
        randNum = Math.round(Math.random() * 67);
        password += alphabets[randNum.intValue()];
            //System.out.println("password: "+password);
        }
 
      return password;
    }

    private String getUserName(String name) {
        return name.split(" ")[0].toLowerCase();
    }

}
