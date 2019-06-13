/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.resourcePortal;

import app.resource.Resource;
import app.resource.ResourceService;
import app.security.SecurityObject;
import app.standardCode.StandardCode;
import app.user.Privilege;
import app.user.User;
import app.user.UserService;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

/**
 *
 * @author niteshwar
 */
public class LoginVendorAction  extends Action {

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
        System.err.println("LoginUserAction @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

	// Extract attributes we will need
	MessageResources messages = getResources(request);

	// save errors
	ActionMessages errors = new ActionMessages();

        //the values the user enter in the browser
        String username = (String) request.getParameter("username");
        String password = (String) request.getParameter("password");
      
//        //System.out.println("user name@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+username);
//        //System.out.println("password@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+password);
       
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

        if(!ResourceService.getInstance().checkLogin(username, password)) {
            errors.add("login", new ActionMessage("login.failed"));
            saveMessages(request, errors);
            request.setAttribute("failed", "true");
            return (mapping.getInputForward());
        }

        //get this user from db
        Resource r = ResourceService.getInstance().getSingleResourceByUserName(username);

        //make sure this is a current employee
        //also, make sure if this is an admin, to continue
        try {
           if(r.isIsEnabled()!= true) {
            errors.add("login", new ActionMessage("login.failed"));
            saveMessages(request, errors);
            request.setAttribute("failed", "true");
            return (mapping.getInputForward());
        }
    
        }
        catch(java.lang.NullPointerException e) {
            errors.add("login", new ActionMessage("login.failed"));
            saveMessages(request, errors);
            request.setAttribute("failed", "true");
            return (mapping.getInputForward());
        }


        //create new session for this user
        //save our logged-in user in the session
	HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        session = request.getSession(true);

        //session expires after 15 minutes (in case someone forgot to click "logout")
        session.setMaxInactiveInterval(60*15*1);
        //set first name, username into session for display
        //also, username is used for various privileges
	session.setAttribute("username", r.getUsername());
//        session.setAttribute("firstName", u.getFirstName());

        //set this user's privileges into session
//        ArrayList privs = new ArrayList();
//        for(Iterator iter = u.getPrivileges().iterator(); iter.hasNext();) {
//            Privilege priv = (Privilege) iter.next();
//            privs.add(priv.getPrivilege()); //the privilege as a string
//        }
//        String[] privileges = (String[]) privs.toArray(new String[0]);
//        session.setAttribute("userPrivs", privileges);

        //set security object into session
	session.setAttribute("SecurityObject", new SecurityObject());

        //put username in cookie for later use in Welcome.jsp
        response.addCookie(StandardCode.getInstance().setCookie("storedUsername", username));


        //MISC TASKS that should be only done once per login:
        //Delete Unavailable Dates automatically
   //      ResourceService.getInstance().deletePastDateUnavailableDates();

       //if reports login, forward to reports display
     /*if(StandardCode.getInstance().checkPrivStringArray(privileges, "reports")) {
        return (mapping.findForward("Reports"));
        }*/

//        //if hrAdmin login, forward to hrAdmin display
//        if(StandardCode.getInstance().checkPrivStringArray(privileges, "hrAdmin")) {
//            return (mapping.findForward("HrAdmin"));
//        }
//
//        //if admin login, forward to admin display
//        if(StandardCode.getInstance().checkPrivStringArray(privileges, "admin")) {
//            return (mapping.findForward("Admin"));
//        }
//
//        //if reports login, forward to reports display
//        if(StandardCode.getInstance().checkPrivStringArray(privileges, "reports")) {
//            return (mapping.findForward("Reports"));
//        }

         
	// Forward control to the specified Success URI
//         //System.out.println("UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUser Typeeeeeeeeeeeeeeeeeeeeeeeeeee"+ u.getuserType());
//
//         if(u.getuserType() != null){
//      if(u.getuserType().equalsIgnoreCase("client"))
//         return (mapping.findForward("ClientSuccess"));
//         }
    return (mapping.findForward("Success"));
         
        // return mapping.findForward("");

    }

}
