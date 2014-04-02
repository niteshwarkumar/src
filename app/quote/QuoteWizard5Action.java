/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.quote;

import app.client.Client;
import app.client.ClientService;
import app.extjs.global.LanguageAbs;
import app.extjs.vo.Upload_Doc;
import app.project.SourceDoc;
import app.project.TargetDoc;
import org.apache.struts.util.MessageResources;
import app.security.*;
import app.standardCode.StandardCode;
import org.apache.struts.upload.FormFile;
import java.io.*;
import javax.servlet.http.HttpSession;
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
public class QuoteWizard5Action extends Action {

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
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        // Extract attributes we will need
        MessageResources messages = getResources(request);

        // save errors
        ActionMessages errors = new ActionMessages();
        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
        String quoteViewId = "0";
        try {
            HttpSession session = request.getSession(false);
            quoteViewId = session.getAttribute("quoteViewId").toString();
        } catch (Exception e) {
        }
        Quote1 q = QuoteService.getInstance().getSingleQuote(Integer.parseInt(quoteViewId));
        Client_Quote ur1 = QuoteService.getInstance().get_SingleClientQuote(Integer.parseInt(quoteViewId));
        //Notationification Mail
        String fromAddress = "excelnet@xltrans.com";
        // String toAddress ="rdecortie@xltrans.com";
        String toAddress = "rdecortie@xltrans.com";
        User pm = null;
        User ae = null;
        Client c = null;
        try {
            c = ClientService.getInstance().getSingleClient(u.getID_Client());
        } catch (Exception e) {
        }

        try {
            String[] pmname = c.getProject_mngr().split(" ");
            String pmFname = pmname[0];
            String pmLname = c.getProject_mngr().replace(pmname[0] + " ", "");
            pm = UserService.getInstance().getSingleUserRealName(pmFname, pmLname);
            toAddress += "," + pm.getWorkEmail1();
        } catch (Exception e) {
        }
        try {
            String[] aename = c.getSales_rep().split(" ");
            String aeFname = aename[0];
            String aeLname = c.getSales_rep().replace(aename[0] + " ", "");
            //String aeLname=c.getProject_mngr().replace(aename[0]+" ", "");
            ae = UserService.getInstance().getSingleUserRealName(aeFname, aeLname);

            toAddress += "," + ae.getWorkEmail1();
        } catch (Exception e) {
        }
        try {
            toAddress += "," + u.getWorkEmail1();
        } catch (Exception e) {
            try {
                toAddress += "," + u.getHomeEmail1();
            } catch (Exception e1) {
            }

        }
        String qDate=""+q.getQuoteDate();
        try{
        String[] qDateArray=(q.getQuoteDate().toString()).split("-");
        qDate=qDateArray[1]+"-"+qDateArray[2]+"-"+qDateArray[0];

        }catch(Exception e){}


        String sources11 = "";
        String task1 = "";
                                        String targets11 = "";
                                        List sourcelang1 = QuoteService.getInstance().getSourceLang(q, ur1.getId());
                                        String targets1 = "";
                                        String sources1 = "";
                                        task1 = "";
                                        int idTask1 = 0;
                                        HashMap alreadyAdded = new HashMap();
                                        for (int ii = 0; ii < sourcelang1.size(); ii++) {
                                            SourceDoc sd1 = (SourceDoc) sourcelang1.get(ii);
                                            sources11 += (String) LanguageAbs.getInstance().getAbs().get(sd1.getLanguage());
                                            if (ii != sourcelang1.size() - 1) {
                                                sources11 += ", ";
                                            }
                                            List targetlang = QuoteService.getInstance().getTargetLang(sd1.getSourceDocId());
                                            for (int jj = 0; jj < targetlang.size(); jj++) {
                                                TargetDoc td1 = (TargetDoc) targetlang.get(jj);
                                                String srcLang1 = td1.getLanguage();
                                                String abr1 = (String) LanguageAbs.getInstance().getAbs().get(srcLang1);
                                                //Also prevent duplicates
                                                if (abr1 != null && !"".equals(srcLang1) && !"".equals(abr1) && alreadyAdded.get(abr1) == null) {
                                                    targets11 += abr1 + ", ";
                                                    alreadyAdded.put(abr1, abr1);
                                                } else if (abr1 == null && !"".equals(srcLang1) && !"".equals(abr1) && alreadyAdded.get(srcLang1) == null) {
                                                    targets11 += srcLang1 + ", ";
                                                    alreadyAdded.put(srcLang1, srcLang1);
                                                }

                                                idTask1 = td1.getTargetDocId();
                                            }//System.out.println("targets"+targets);
                                            if (targets11.endsWith(", ")) {
                                                targets11 = targets11.substring(0, targets11.length() - 2);
                                            }


                                        }
                                        String sourcee = StandardCode.getInstance().noNull(sources11);
                                        String targett = StandardCode.getInstance().noNull(targets11);
                                        Upload_Doc ud=QuoteService.getInstance().getUploadDoc(new Integer(quoteViewId));
                                        String fName="";
                                        try{
                                             fName="<br><div>Files: <a href="+ud.getPathname()+">"+ud.getPath()+"</a></div>";
                                        }catch(Exception e){}




        String subject = q.getNumber()+" - New Quote - " + c.getCompany_name();
        String body = "Hello,<div><div><br></div>This message is automatically generated by ExcelNet.</div><div><br></div><br>"
                + "<div>" + u.getFirstName() + " " + u.getLastName() + " of " + c.getCompany_name() + " has created quote <b>" + q.getNumber() + " </b> on &nbsp;" + qDate
                + "<br><br><div>Product: " + q.getProject().getProduct() + " </div>"
                + "<br><div>Description: " + q.getProject().getProductDescription() + "</div>"
                + "<br><div>Source: " + sourcee + "</div>"
                + "<div>Target: " + targett + "</div>"
                + fName
                + "<br><br><div>Best regards,</div><div><br></div><div>ExcelNet Administrator</div>"
                + "<div><img src='http://excelnet.xltrans.com/logo/images/-1168566039logoExcel.gif'></div>";


        try {
            String[] emailList = toAddress.split(",");
            QuoteWizard5Action smtpMailSender = new QuoteWizard5Action();

            smtpMailSender.postMail(emailList, subject, body, emailFromAddress);
            //smtpMailSender.postMail(emailList, subject, body, emailFromAddress);

            //System.out.println("mail send ");
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

        try {
            InternetAddress addressBcc = new InternetAddress("niteshwar.kumar@spatialideas.com");
            msg.setRecipient(Message.RecipientType.BCC, addressBcc);
        } catch (Exception e) {
        }



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
