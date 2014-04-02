//HrViewNotesAction.java gets the current user's notes
//and places them in an attribute for display

package app.hr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import java.util.*;
import app.user.*;
import app.security.*;
import app.standardCode.*;

public final class HrViewNotesAction extends Action {


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
	
        
	//START get id of current user from either request, attribute, or cookie 
        //id of user from request
	String userId = null;
	userId = request.getParameter("userViewId");
        
        //check attribute in request
        if(userId == null) {
            userId = (String) request.getAttribute("userViewId");
        }
        
        //id of user from cookie
        if(userId == null) {            
            userId = StandardCode.getInstance().getCookie("userViewId", request.getCookies());
        }

        //default user to first if not in request or cookie
        if(userId == null) {
                List results = UserService.getInstance().getUserList();
                User first = (User) results.get(0);
                userId = String.valueOf(first.getUserId());
            }            
        
        Integer id = Integer.valueOf(userId);
        
        //END get id of current user from either request, attribute, or cookie               
        
        //get user to edit
        User u = UserService.getInstance().getSingleUser(id);


//PRIVS check that logged-in user is viewing this employee record
        if(request.getSession(false).getAttribute("username").toString().equals(u.getUsername())||StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "admin")) {//this user is not viewing only there employee information

        }else  return (mapping.findForward("accessDenied"));
        //END PRIVS check that logged-in user is viewing this employee record
        
        
        //put this user into the request
        request.setAttribute("user", u);
        
        //add this user id to cookies; this will remember the last user
        response.addCookie(StandardCode.getInstance().setCookie("userViewId", userId));
        
        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("userViewTab", "Notes"));
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
