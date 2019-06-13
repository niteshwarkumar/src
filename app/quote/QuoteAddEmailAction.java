//QuoteAddEmailAction.java sends an email with
//quote number to inform someone to perform analysis
//on the source doc

package app.quote;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleException;
import org.apache.struts.util.MessageResources;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.validator.*;
import javax.mail.*; //for mail
import javax.activation.*; //for mail
import com.sun.mail.smtp.*; //for mail
import java.util.*; //for Properties class (for mail)
import javax.mail.internet.*; //for mail
import java.util.*;
import app.user.*;
import app.db.*;
import app.client.*;
import app.project.*;
import app.workspace.*;
import app.standardCode.*;
import app.security.*;

public final class QuoteAddEmailAction extends Action {


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
	
        //START build email and send
        
        //need the quote number to display in email
        //String quoteId = StandardCode.getInstance().getCookie("quoteAddId", request.getCookies());
        Quote1 q = QuoteService.getInstance().getSingleQuote(Integer.valueOf("1")); 
        
        //properties for smtp server
        Properties props = new Properties();
        props.put("mail.smtp.host", "xltrans.com");
        javax.mail.Session session = javax.mail.Session.getDefaultInstance(props, null);
        
        //START build email message
        Message newMessage = new MimeMessage(session);
        
        newMessage.setFrom(new InternetAddress("ksredzienski@xltrans.com"));
        
        Address replyToArray[] = {new InternetAddress("ksredzienski@xltrans.com")};
        newMessage.setReplyTo(replyToArray);
        
        Address toArray[] = {new InternetAddress("ksredzienski@xltrans.com")};
        newMessage.setRecipients(Message.RecipientType.TO, toArray); 
        
        newMessage.setSubject("Quote Analysis");
        
        newMessage.setSentDate(new Date());
        
        newMessage.setText("Analysis has been requested for quote number: " + q.getNumber());
        //END build email message
        
        //send message
        Transport.send(newMessage);
        
        //END build email and send

        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
