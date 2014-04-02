//HrAdminAddEmployee1Action.java gets the new hr info
//from a form.  It then uploads the new values for
//this new employee to the db
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
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.*;
import java.io.*;
import java.util.*;
import app.user.*;
import app.client.*;
import app.standardCode.*;
import app.security.*;
import app.tools.SendEmail;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;
import javax.servlet.http.HttpSession;
import java.util.Random;
import javax.mail.*; //for mail
import javax.mail.internet.*; //for mail

public final class HrAdminAddEmployee1Action extends Action {

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

        // Extract attributes we will need
        MessageResources messages = getResources(request);

        // save errors
        ActionMessages errors = new ActionMessages();

        //START check for login (security)
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)

        //PRIVS check that hrAdmin user is viewing this page 
        if (!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "hrAdmin")) {
            return (mapping.findForward("accessDenied"));
        }//END PRIVS check that hrAdmin user is viewing this page
        User currentUser = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
        User u = new User();

        //values for update from form; change what is stored in db to these values
        DynaValidatorForm uvg = (DynaValidatorForm) form;

        String firstName = (String) uvg.get("firstName");
        String lastName = (String) uvg.get("lastName");
        FormFile picture = (FormFile) uvg.get("picture");
        String userType = (String) uvg.get("userType");
        System.out.println("UserTypeeeeeeeeeeeeeeeee" + userType);
        String client = (String) uvg.get("client");
        Client c = ClientService.getInstance().getSingleClient(client);
        // p = UserService.getInstance().getSinglePosition(userType);
        String username = (String) uvg.get("username");
        String password = "";//= (String) uvg.get("password");
        char[] alphabets = {'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M'};
        long randNum = Math.round(Math.random() * 26);
        Long temp = new Long(randNum);

        try {
            password += firstName.substring(0, 2);
        } catch (Exception e) {
            password += alphabets[temp.intValue()];
            temp = new Long(randNum);
            password += alphabets[temp.intValue()];
        }
        password += userType.substring(0, 2).toUpperCase();
        Random randomGenerator = new Random();

        int randomInt = randomGenerator.nextInt(9);
        password += "" + randomInt;

        temp = new Long(randNum);
        password += alphabets[temp.intValue()];
        char[] characters = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '-', '+', '=', '|'};

        long randNum2 = Math.round(Math.random() * 15);
        Long temp2 = new Long(randNum2);
        password += characters[temp2.intValue()];

        long randNum1 = Math.round(Math.random() * 26);
        Long temp1 = new Long(randNum1);
        // password+= alphabets[temp1.intValue()].toUpperCase() ;

        System.out.println("Passssssswwwwwwwwoooooorrrrrdddd" + password);

        String birthDay = (String) uvg.get("birthDay");
        String hireDate = (String) uvg.get("hireDate");
        String vacationDaysTotal = (String) uvg.get("vacationDaysTotal");
        String sickDaysTotal = (String) uvg.get("sickDaysTotal");
        String position = "";
        String department = "";
        String location = "";
        Location l = null;
        Department d = null;
        Position1 p = null;


        if (userType.equalsIgnoreCase("admin")) {
            position = (String) uvg.get("position");
            p = UserService.getInstance().getSinglePosition(position);

            department = (String) uvg.get("department");
            d = UserService.getInstance().getSingleDepartment(department);

            location = (String) uvg.get("location");
            l = UserService.getInstance().getSingleLocation(location);
        } else {
            position = "Consultant";
            p = UserService.getInstance().getSinglePosition(position);

            department = "Accounting";
            d = UserService.getInstance().getSingleDepartment(department);

            location = "Pittsburgh";
            l = UserService.getInstance().getSingleLocation(location);
        }
        String homeAddress = (String) uvg.get("homeAddress");
        String homeCity = (String) uvg.get("homeCity");
        String homeState = (String) uvg.get("homeState");
        String homeCountry = (String) uvg.get("homeCountry");
        String homeZip = (String) uvg.get("homeZip");
        String homePhone = (String) uvg.get("homePhone");
        String homeCell = (String) uvg.get("homeCell");
        String homeEmail1 = (String) uvg.get("homeEmail1");
        String homeEmail2 = (String) uvg.get("homeEmail2");
        String workAddress = (String) uvg.get("workAddress");
        String workCity = (String) uvg.get("workCity");
        String workState = (String) uvg.get("workState");
        String workCountry = (String) uvg.get("workCountry");
        String workZip = (String) uvg.get("workZip");
        String workPhone = (String) uvg.get("workPhone");
        String workPhoneEx = (String) uvg.get("workPhoneEx");
        String workCell = (String) uvg.get("workCell");
        String workEmail1 = (String) uvg.get("workEmail1");


        String workEmail2 = (String) uvg.get("workEmail2");
        String emergencyContactName = (String) uvg.get("emergencyContactName");
        String emergencyContactRelation = (String) uvg.get("emergencyContactRelation");
        String emergencyContactPhone = (String) uvg.get("emergencyContactPhone");
        String skypeId = (String) uvg.get("skypeId");
        HttpSession session = request.getSession(false);

        if (username.equalsIgnoreCase("") || password.equalsIgnoreCase("")) {
            session.setAttribute("Blank", "true");
            return (mapping.findForward("Fail"));
        } else {
            session.setAttribute("Blank", "false");
        }
        String userEmailId = "excelnet@xltrans.com";
        if (!workEmail1.equalsIgnoreCase("")) {
            session.setAttribute("email", "valid");
            userEmailId = workEmail1;
        } else if (!workEmail2.equalsIgnoreCase("")) {
            session.setAttribute("email", "valid");
            userEmailId = workEmail2;
        } else {
            session.setAttribute("email", "invalid");
            return (mapping.findForward("Fail"));
        }

        User u1 = UserService.getInstance().getSingleUser(username);
        if (u1 != null) {
            //HttpSession session = request.getSession(false);
            session.setAttribute("Duplicate", "true");
            return (mapping.findForward("Fail"));
        } else {
            session.setAttribute("Duplicate", "false");
        }
        //process picture upload
        String saveFileName = null;
        if (picture.getFileName().length() > 0) {
            String fileName = picture.getFileName();
            byte[] fileData = picture.getFileData(); //byte array of file data
            //random number in image name to prevent repeats
            Random gen = new Random(new Date().getSeconds());
            saveFileName = String.valueOf(gen.nextInt()) + fileName;
            File saveFile = new File("C:/Program Files (x86)/Apache Software Foundation/Tomcat 7.0/webapps/logo/images/" + saveFileName);
            FileOutputStream out = new FileOutputStream(saveFile);
            out.write(fileData);
            out.flush();
            out.close();
        }

        //update the user's values
        u.setFirstName(firstName);
        u.setLastName(lastName);

        if (saveFileName != null) { //was a new picture uploaded
            u.setPicture(saveFileName);
        }

        u.setUsername(username);
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
            u.setPassword(hash);
        }
        u.setuserType(userType);
        // u.setId_client(100);
        if (userType.equalsIgnoreCase("client")) {
            u.setID_Client(c);

            System.out.println("XXXXXXXXXXXXXX " + c.getClientId());
        }
        if (birthDay.length() > 0) { //if present
            u.setBirthDay(DateService.getInstance().convertDate(birthDay).getTime());
        }
        if (hireDate.length() > 0) { //if present
            u.setHireDate(DateService.getInstance().convertDate(hireDate).getTime());
        }

        if (vacationDaysTotal.length() > 0) { //if present
            u.setVacationDaysTotal(Double.valueOf(vacationDaysTotal));
        }

        if (sickDaysTotal.length() > 0) { //if present
            u.setSickDaysTotal(Double.valueOf(sickDaysTotal));
        }

        u.setPosition(p);
        System.out.println("Postion & Client name   ===================" + position + "+++++++++++" + client + "============" + userType);
        u.setDepartment(d);
        u.setLocation(l);
        u.setHomeAddress(homeAddress);
        u.setHomeCity(homeCity);
        u.setHomeState(homeState);
        u.setHomeCountry(homeCountry);
        u.setHomeZip(homeZip);
        u.setHomePhone(homePhone);
        u.setHomeCell(homeCell);
        u.setHomeEmail1(homeEmail1);
        u.setHomeEmail2(homeEmail2);
        u.setWorkAddress(workAddress);
        u.setWorkCity(workCity);
        u.setWorkState(workState);
        u.setWorkCountry(workCountry);
        u.setWorkZip(workZip);
        u.setWorkPhone(workPhone);
        u.setWorkPhoneEx(workPhoneEx);
        u.setWorkCell(workCell);
        u.setWorkEmail1(workEmail1);
        u.setWorkEmail2(workEmail2);
        u.setEmergencyContactName(emergencyContactName);
        u.setEmergencyContactRelation(emergencyContactRelation);
        u.setEmergencyContactPhone(emergencyContactPhone);
        if (userType.equalsIgnoreCase("admin")) {
            u.setCurrentEmployee("true"); //flag this employee as "active"
            u.setHuman("true"); //include this employee in reports
            u.setDropDown("true"); //include this employee in web-page lists
        } else {
            u.setCurrentEmployee("false"); //does not flag this employee as "active"
            u.setHuman("false"); //does not include this employee in reports
            u.setDropDown("false"); //does not include this employee in web-page lists
        }
        //set vacation and sick days used to "0"
        u.setVacationDaysUsed(new Double(0));
        u.setSickDaysUsed(new Double(0));
        u.setSkypeId(skypeId);

        //set updated values to db
        UserService.getInstance().addUser(u);
        //String msg="<font size='2'><span style='font-family: Verdana,Arial,Helvetica,sans-serif;'>Dear <span style='color: rgb(255, 0, 0);'>"+firstName+"</span>,<br><br style='font-family: Verdana,Arial,Helvetica,sans-serif;'></span><span style='font-family: Verdana,Arial,Helvetica,sans-serif;'>We have created an account for you on ExcelNet, Excel Translations' proprietary intra/extranet system. <br><br> Below you will find the information you need to enter our system, please keep this only to yourself in a secure place.  </span><br style='font-family: Verdana,Arial,Helvetica,sans-serif;'><span style='font-family: Verdana,Arial,Helvetica,sans-serif;'><br>User Name :"+username+"</span><br style='font-family: Verdana,Arial,Helvetica,sans-serif;'><span style='font-family: Verdana,Arial,Helvetica,sans-serif;'>Password&nbsp;&nbsp; :"+password+"</span><br style='font-family: Verdana,Arial,Helvetica,sans-serif;'><br style='font-family: Verdana,Arial,Helvetica,sans-serif;'><span style='font-family: Verdana,Arial,Helvetica,sans-serif;'>You can log in into your Account on by clicking </span><a style='font-family: Verdana,Arial,Helvetica,sans-serif;' href='https://excelnet.xltrans.com'>here</a><span style='font-family: Verdana,Arial,Helvetica,sans-serif;'>.<br><br><br>Best Regards,</span><br style='font-family: Verdana,Arial,Helvetica,sans-serif;'><br style='font-family: Verdana,Arial,Helvetica,sans-serif;'><span style='font-family: Verdana,Arial,Helvetica,sans-serif; Best regards,<br style='font-family: Verdana,Arial,Helvetica,sans-serif;'></font><font style='font-family: Verdana,Arial,Helvetica,sans-serif;' size='2'><span class='Apple-style-span' style='border-collapse: separate; color: rgb(0, 0, 0); font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: normal; orphans: 2; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; font-size: medium;'><span class='Apple-style-span' style='color: rgb(59, 105, 119); font-size: 11px; text-align: left;'><br> ExcelNet Administrator<br>Excel Translations, Inc.<br><br><img src=http://excelnet.xltrans.com/images/-1168566039logoExcel.gif></span></span></font><br> ";
        String msg = "<font size='2'><span style='font-family: Verdana,Arial,Helvetica,sans-serif;'>Dear <span style='color: rgb(0, 0, 0);'>"+firstName+"</span>,<br><br style='font-family: Verdana,Arial,Helvetica,sans-serif;'></span><span style='font-family: Verdana,Arial,Helvetica,sans-serif;'>We have created an account for you on ExcelNet, Excel Translations' proprietary intra/extranet system. <br><br> Below you will find the information you need to enter our system, please keep this only to yourself in a secure place.&nbsp; </span><br style='font-family: Verdana,Arial,Helvetica,sans-serif;'><span style='font-family: Verdana,Arial,Helvetica,sans-serif;'><br>User Name :"+username+"</span><br style='font-family: Verdana,Arial,Helvetica,sans-serif;'><span style='font-family: Verdana,Arial,Helvetica,sans-serif;'>Password&nbsp;&nbsp; :"+password+"</span><br style='font-family: Verdana,Arial,Helvetica,sans-serif;'><br style='font-family: Verdana,Arial,Helvetica,sans-serif;'><span style='font-family: Verdana,Arial,Helvetica,sans-serif;'>You can log in to your Account on by clicking </span><a style='font-family: Verdana,Arial,Helvetica,sans-serif;' href='http://excelnet.xltrans.com'>here</a><span style='font-family: Verdana,Arial,Helvetica,sans-serif;'>.<br><br><br>Best Regards,</span><br style='font-family: Verdana,Arial,Helvetica,sans-serif;'><br style='font-family: Verdana,Arial,Helvetica,sans-serif;'><span style='font-family: Verdana,Arial,Helvetica,sans-serif;' font-family:='' verdana,arial,helvetica,sans-serif;=''></span></font><font style='font-family: Verdana,Arial,Helvetica,sans-serif;' size='2'><span class='Apple-style-span' style='border-collapse: separate; color: rgb(0, 0, 0); font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: normal; orphans: 2; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; font-size: medium;'><span class='Apple-style-span' style='color: rgb(59, 105, 119); font-size: 11px; text-align: left;'><font size='1'><br> <font size='2'>ExcelNet Administrator<br>Excel Translations, Inc.</font></font><br><br><img src='http://excelnet.xltrans.com/logo/images/-1168566039logoExcel.gif'></span></span></font><br> <br>  ";
        String emailMsgTxt = msg;
        String emailSubjectTxt = "New User Account";
        String adminEmail;
        if (currentUser.getWorkEmail1() == null || currentUser.getWorkEmail1().equalsIgnoreCase("")) {
            adminEmail = "excelnet@xltrans.com";

        } else {
            adminEmail = currentUser.getWorkEmail1();
        }




        try {
            String[] emailList = {userEmailId, adminEmail};
            SendEmail smtpMailSender = new SendEmail();
            smtpMailSender.postMail(emailList, emailSubjectTxt, emailMsgTxt, emailFromAddress);
        } catch (Exception e) {
// String[] emailList = {"niteshwar.kumarpatialideas.com"};
// SendEmail smtpMailSender = new SendEmail();
// smtpMailSender.postMail( emailList, emailSubjectTxt, emailMsgTxt, emailFromAddress);
        }

        request.setAttribute("userFullName", firstName+" "+lastName);
        request.setAttribute("username", username);

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
    private static final String SMTP_HOST_NAME = "xltrans.com";
    private static final String SMTP_AUTH_USER = "excelnet@xltrans.com";
    private static final String SMTP_AUTH_PWD = "3vB@zMsp";
    //private static final String emailSubjectTxt  = "Request for Quote Analysis :"+newQ1.;
    private static final String emailFromAddress = "excelnet@xltrans.com";

    // Add List of Email address to who email needs to be sent to
    // private static final String[] emailList = {"niteshwar.kumar@spatialideas.com"};
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
