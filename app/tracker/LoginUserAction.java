//LoginUserAction.java contains the code that checks the
//db for a matching username and password.
//It is useable from the public, so no security checks
//need to be made.  Also, put privileges in session.

package app.tracker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import app.standardCode.*;
import app.security.*;
import app.user.*;


public final class LoginUserAction extends Action {

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
	
        //the values the user enter in the browser
        String username = (String) request.getParameter("username");
        String password = (String) request.getParameter("password");
        
        //check for insert, update, delete, select, drop statements in public login fields (SQL injection)
        String usernameCheck = username.toLowerCase();
        String passwordCheck = password.toLowerCase();
        if((usernameCheck.indexOf("insert") != -1) || (usernameCheck.indexOf("update") != -1) || (usernameCheck.indexOf("delete") != -1) || (usernameCheck.indexOf("select") != -1) || (usernameCheck.indexOf("drop") != -1) || (passwordCheck.indexOf("insert") != -1) || (passwordCheck.indexOf("update") != -1) || (passwordCheck.indexOf("delete") != -1) || (passwordCheck.indexOf("select") != -1)|| (passwordCheck.indexOf("drop") != -1)) {
            errors.add("login", new ActionMessage("login.failed"));
            saveMessages(request, errors);
            request.setAttribute("failed", "true");
            return (mapping.getInputForward()); 
        }
        
        //Validate User by checking for a matching username and password in db
        if(!UserService.getInstance().checkTrackerLogin(username, password)) {
            errors.add("login", new ActionMessage("login.failed"));
            saveMessages(request, errors);
            request.setAttribute("failed", "true");
            return (mapping.getInputForward());
        }
        
        //get this user from db
        //User u = UserService.getInstance().getSingleUser(username);
        
        
        
        //create new session for this user
        //save our logged-in user in the session
	HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        session = request.getSession(true);
                
        //session expires after 12 hours (in case someone forgot to click "logout")
        session.setMaxInactiveInterval(60*60*12);
        //set first name, username into session for display
        //also, username is used for various privileges
	session.setAttribute("username", username);
       
        
        
        
        //set security object into session
	session.setAttribute("SecurityObject", new SecurityObject());
        
        //put username in cookie for later use in Welcome.jsp
        response.addCookie(StandardCode.getInstance().setCookie("storedUsername", username));
           
    
        
	// Forward control to the specified Success URI
	return (mapping.findForward("Success"));

    }

}
