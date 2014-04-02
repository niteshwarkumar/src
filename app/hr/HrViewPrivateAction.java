//HrViewPrivateAction.java gets the current hr object (employee)
//from the db.  It then places that employee (only private info) 
//into the hr form for display in the jsp page. It then 
//places location values into cookies

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
import java.text.*;
import app.user.*;
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;


public final class HrViewPrivateAction extends Action {


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
        
                
        //load user info for editing
        DynaValidatorForm uvp = (DynaValidatorForm) form;
        if(u.getBirthDay() != null) { //if date exists
            uvp.set("birthDay", DateFormat.getDateInstance(DateFormat.SHORT).format(u.getBirthDay()));
        }
        else {
            uvp.set("birthDay", null);
        }
        
        if(u.getHireDate() != null) { //if date exists
            uvp.set("hireDate", DateFormat.getDateInstance(DateFormat.SHORT).format(u.getHireDate()));
        }
        else {
            uvp.set("hireDate", null);
        }
        
        if(u.getVacationDaysTotal() != null) {
            uvp.set("vacationDaysTotal", String.valueOf(u.getVacationDaysTotal())); 
        }
        else {
           uvp.set("vacationDaysTotal", ""); 
        }
        
        if(u.getVacationDaysUsed() != null) {
            uvp.set("vacationDaysUsed", String.valueOf(u.getVacationDaysUsed())); 
        }
        else {
            uvp.set("vacationDaysUsed", "");
        }
        
        if(u.getSickDaysTotal() != null) {
            uvp.set("sickDaysTotal", String.valueOf(u.getSickDaysTotal()));
        }
        else {
            uvp.set("sickDaysTotal", "");
        }
        
        if(u.getSickDaysUsed() != null) {
            uvp.set("sickDaysUsed", String.valueOf(u.getSickDaysUsed())); 
        }
        else {
            uvp.set("sickDaysUsed", ""); 
        }
        
        if(u.getUnpaidDaysUsed() != null) {
            uvp.set("unpaidDaysUsed", String.valueOf(u.getUnpaidDaysUsed())); 
        }
        else {
            uvp.set("unpaidDaysUsed", "0"); 
        }
        
        //get training list
        List training = UserService.getInstance().getTraining(u.getUserId());
        
        //place this user into the user form for display
        request.setAttribute("userViewPrivate", uvp);
        request.setAttribute("training", training);
        request.setAttribute("user", u);
        
        
        //add this user id to cookies; this will remember the last user
        response.addCookie(StandardCode.getInstance().setCookie("userViewId", userId));
        
        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("userViewTab", "Private Info"));
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
