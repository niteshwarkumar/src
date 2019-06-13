/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.tools;

import app.standardCode.StandardCode;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author niteshwar
 */
public class SendMail extends Thread {

    private String recipients[];
    private String subject;
    private String message;
    private String from;
    private String ccRecipients[];
    private String bccRecipients[];
    private Message msg;

    public SendMail(String recipients[], String subject, String message, String from) {
        setDaemon(true);
        this.from = from;
        this.message = message;
        this.recipients = recipients;
        this.subject = subject;
        this.msg = StandardCode.getInstance().getMimeMessage();
        this.bccRecipients = null;
    }

    public SendMail(String recipients[], String ccRecipients[], String subject, String message, String from) {
        setDaemon(true);
        this.from = from;
        this.message = message;
        this.recipients = recipients;
        this.subject = subject;
        this.ccRecipients = ccRecipients;
        this.msg = StandardCode.getInstance().getMimeMessage();
        this.bccRecipients = null;
    }

    public SendMail(String recipients[], String ccRecipients[], String bccRecipients[], String subject, String message, String from, String pwd) {
        setDaemon(true);
        this.from = from;
        this.message = message;
        this.recipients = recipients;
        this.subject = subject;
        this.ccRecipients = ccRecipients;
        this.msg = StandardCode.getInstance().getMimeMessage(from, pwd);
        this.bccRecipients = bccRecipients;
    }
    
    public SendMail(String recipients[], String ccRecipients[], String bccRecipients[], String subject, String message) {
        setDaemon(true);
        this.message = message;
        this.recipients = recipients;
        this.subject = subject;
        this.ccRecipients = ccRecipients;
        this.msg = StandardCode.getInstance().getMimeMessage();
        this.bccRecipients = bccRecipients;
    }

    @Override
    public void run() {
        try {
            // create a message
//            Message msg = StandardCode.getInstance().getMimeMessage();

            InternetAddress[] addressTo = new InternetAddress[recipients.length];
            for (int i = 0; i < recipients.length; i++) {
                addressTo[i] = new InternetAddress(recipients[i]);
            }
            msg.setRecipients(Message.RecipientType.TO, addressTo);
            try {
                if (ccRecipients != null) {
                    if (!ccRecipients.equals("")) {
                        InternetAddress[] addressCC = new InternetAddress[ccRecipients.length];
                        for (int i = 0; i < ccRecipients.length; i++) {
                            if (!ccRecipients[i].equalsIgnoreCase("")) {
                                addressCC[i] = new InternetAddress(ccRecipients[i]);
                            }
                        }
                        msg.setRecipients(Message.RecipientType.CC, addressCC);
                    }
                }
            } catch (Exception e) {
            }
            try {
                if (bccRecipients != null) {
                    if (!bccRecipients.equals("")) {
                        InternetAddress[] addressBCC = new InternetAddress[bccRecipients.length];
                        for (int i = 0; i < ccRecipients.length; i++) {
                            if (!bccRecipients[i].equalsIgnoreCase("")) {
                                addressBCC[i] = new InternetAddress(bccRecipients[i]);
                            }
                        }
                        msg.setRecipients(Message.RecipientType.BCC, addressBCC);
                    }
                }
            } catch (Exception e) {
            }

            // Setting the Subject and Content Type
            msg.setSubject(subject);
            msg.setContent(message, "text/html");
            Transport.send(msg);
        } catch (MessagingException ex) {
            try {
                // create a message
                msg = StandardCode.getInstance().getMimeMessage();

                InternetAddress[] addressTo = new InternetAddress[1];
//                addressTo[0] = new InternetAddress("rdecortie@xltrans.com");
                addressTo[0] = new InternetAddress("nkumar@xltrans.com");
                msg.setRecipients(Message.RecipientType.TO, addressTo);

                String errormessage = "<br>" + ex.getMessage();
                errormessage += "<br>To Address: ";
                for (int i = 0; i < recipients.length; i++) {
                    errormessage += recipients[i];
                }
                errormessage += "<br>Cc Address: ";
                for (int i = 0; i < ccRecipients.length; i++) {
                    errormessage += ccRecipients[i];
                }
                errormessage += "<br>Bcc Address: ";
                for (int i = 0; i < bccRecipients.length; i++) {
                    errormessage += bccRecipients[i];
                }

                message += errormessage;
                // Setting the Subject and Content Type
                msg.setSubject("Error in sending " + subject);
                msg.setContent(message, "text/html");
                Transport.send(msg);
            } catch (MessagingException ex1) {
                Logger.getLogger(SendMail.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

}
