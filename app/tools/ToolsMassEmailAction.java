//ToolsMassEmailAction.java sends an email to each person 
//from a text-file list 
package app.tools; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 
import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory; 
import org.apache.struts.action.*; 
import org.apache.struts.action.ActionForm; 
import org.apache.struts.action.ActionForward; 
import org.apache.struts.action.ActionMapping; 
import org.apache.struts.util.MessageResources; 
import org.apache.struts.upload.*; 
import javax.mail.*; //for mail 
import javax.activation.*; //for mail 
import javax.mail.internet.*; //for mail 
import java.io.*; 
import java.util.*; 
import app.user.*; 
import app.security.*; 
import org.apache.struts.validator.*; 

public final class ToolsMassEmailAction extends Action { 

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
                      
        //values for generating mass emails 
        DynaValidatorForm met = (DynaValidatorForm) form; 
        String fromAddress = (String) met.get("fromAddress"); 
        FormFile myFile = (FormFile) met.get("toAddress"); 
        FormFile myImage = (FormFile) met.get("embeddedImage"); 
        User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username")); 
        String subject = (String) met.get("subject"); 
        String body = (String) met.get("body1"); 
        String bodyBottom = (String) met.get("bodyBottom"); 
        
        
         //get input stream 
      InputStream in2 = myImage.getInputStream(); 
      File imgFile = File.createTempFile("tempImage", ".jpg");
      OutputStream out = new FileOutputStream(imgFile);
    
        // Transfer bytes from in to out
       byte[] fileData2 = myImage.getFileData(); //byte array of entire file 
       int fileSize = fileData2.length;
       
        int len;
        while ((len = in2.read(fileData2)) > 0) {
            out.write(fileData2, 0, len);
        }
        in2.close();
        out.close();

      
      DataSource fds = new FileDataSource(imgFile); 
        //START process the email to-list text file 
      
        ArrayList firstName = new ArrayList(); 
        ArrayList email = new ArrayList(); 
        ArrayList client = new ArrayList(); 
        ArrayList lastName = new ArrayList(); 
        
        
        
      //get input stream 
      InputStream in = myFile.getInputStream(); 
      byte[] fileData = myFile.getFileData(); //byte array of entire file 
      in.read(fileData); //read data into fileData 
      String entireRead = new String(fileData); //the entire file as a string 
      String[] lines = entireRead.split("\n"); //lines within the file 
      String[] parts; //each "record field" per line (seperated by a ,) 
      
      for(int i = 0; i < lines.length ; i++) { 
          parts = lines[i].split(","); 
          firstName.add(parts[0]); 
          email.add(parts[1]); 
          client.add(parts[2]); 
          lastName.add(parts[3]); 
      }       
      //END process the email to-list text file 
            
      //START build email and send 
        
        //properties for smtp server 
        Properties props = new Properties(); 
        props.put ("mail.smtp.host", "xltrans.com"); 
        javax.mail.Session session = javax.mail.Session.getDefaultInstance(props, null); 
        
        //START build email message 
        ListIterator iterFirstName = firstName.listIterator(); 
        ListIterator iterEmail = email.listIterator(); 
        ListIterator iterClient = client.listIterator(); 
        ListIterator iterLastName = lastName.listIterator(); 
        
        for(; iterFirstName.hasNext();) { 
            //get this email info 
            String emailFirstName = (String) iterFirstName.next(); 
            emailFirstName = emailFirstName.trim(); 
            String emailEmail = (String) iterEmail.next(); 
            emailEmail = emailEmail.trim(); 
            String emailClient = (String) iterClient.next(); 
            emailClient = emailClient.trim(); 
            String emailLastName = (String) iterLastName.next(); 
            emailLastName = emailLastName.trim(); 
            
            //Message newMessage = new MimeMessage(session); 
            MimeMessage newMessage = new MimeMessage(session); 
            newMessage.setFrom(new InternetAddress(fromAddress)); 
                          
        
        

            //Address replyToArray[] = {new InternetAddress(fromAddress)}; 
            //newMessage.setReplyTo(replyToArray); 
            Address toArray[] = {new InternetAddress(emailEmail)}; 
            newMessage.setRecipients(Message.RecipientType.TO, toArray); 
            newMessage.setSubject(subject); 
            newMessage.setSentDate(new Date()); 
            
// 
        // This HTML mail have to 2 part, the BODY and the embedded image 
        // 
        MimeMultipart multipart = new MimeMultipart("related"); 

        // first part  (the html) 
        BodyPart messageBodyPart = new MimeBodyPart(); 
         //set dynamic data into body  sdsdsdsdasdasd
            String dynamicBody = body; 
            //dynamicBody = dynamicBody.replaceAll("\n", "<br>"); 
            dynamicBody = dynamicBody.replaceAll ("&lt;First Name&gt;", emailFirstName); 
            dynamicBody = dynamicBody.replaceAll("&lt;To Email&gt;", emailEmail); 
            dynamicBody = dynamicBody.replaceAll("&lt;Client&gt;", emailClient); 
            dynamicBody = dynamicBody.replaceAll("&lt;Last Name&gt;", emailLastName); 
            
            //dynamicBody = dynamicBody.replaceAll("<Company Logo>", "<img src=' http://66.47.182.163/images/excel_logo.gif'>"); 
            //newMessage.setText(dynamicBody); werwerwerwe
            
            //END build email message 
String dynamicBodyBottom = bodyBottom; 
            //dynamicBody = dynamicBody.replaceAll("\n", "<br>"); 
            dynamicBodyBottom = dynamicBodyBottom.replaceAll ("&lt;First Name&gt;", emailFirstName); 
            dynamicBodyBottom = dynamicBodyBottom.replaceAll("&lt;To Email&gt;", emailEmail); 
            dynamicBodyBottom = dynamicBodyBottom.replaceAll("&lt;Client&gt;", emailClient); 
            dynamicBodyBottom = dynamicBodyBottom.replaceAll("&lt;Last Name&gt;", emailLastName); 
            
        String wholeMessageBodyString = dynamicBody;
        //wholeMessageBodyString += "fileSize="+fileSize;
        if(fileSize!=0){
            wholeMessageBodyString += "<p align=center><img src=\"cid:embeddedImg\"></p>";
        }else{
            wholeMessageBodyString += "<br>";
        }
        wholeMessageBodyString += dynamicBodyBottom;
            messageBodyPart.setContent(wholeMessageBodyString, "text/html"); 


        // add it 
        multipart.addBodyPart(messageBodyPart); 
         if(fileSize!=0){
            // second part (the image) 
            MimeBodyPart messageBodyPart2 = new MimeBodyPart(); 
            //DataSource fds = new FileDataSource 
            //  ("C:\\images\\jht.gif"); 

            messageBodyPart2.setDataHandler(new DataHandler(fds)); 
            messageBodyPart2.setHeader("Content-ID","<embeddedImg>"); 

            // add it 
            multipart.addBodyPart(messageBodyPart2); 
         }
        

// first part  (the html) 
       


// put everything together 
        newMessage.setContent(multipart); 


            
            
            //END build email message 
            //send message 
            Transport.send(newMessage); 
        } 
        //END build email and send 
      
        // Forward control to the specified success URI 
 return (mapping.findForward ("Success")); 
    } 
} 