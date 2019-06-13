//HrViewNotesUpdateAction.java gets updated hr info
//from a form (the private notes).  It then uploads the new values for
//this employee's notes to the db

package app.hr;

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
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.*;
import java.io.*;
import java.util.*;
import app.user.*;
import app.client.*;
import app.db.*;
import app.workspace.*;
import app.security.*;
import app.standardCode.*;


public final class HrViewNotesUpdateAction extends Action {


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
        
        //PRIVS check that logged-in user is editing this employee record 
        if(!request.getSession(false).getAttribute("username").toString().equals(u.getUsername())) {//this user is not viewing only there employee information
            return (mapping.findForward("accessDenied"));
        }//END PRIVS check that logged-in user is editing this employee record 
        
        //values for update from form; change what is stored in db to these values
        DynaValidatorForm uvn = (DynaValidatorForm) form;
        
        String notes = (String) uvn.get("notes");
        
        //update the user's values
        u.setNotes(notes);
        
        //set updated values to db
        UserService.getInstance().updateUser(u);
        
        //place user id into request for display
        request.setAttribute("user", u);
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
