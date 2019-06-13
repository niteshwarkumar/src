//AdminProjectMainUpdateAction.java gets project-module related options
//and updates changes to them to the db

package app.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.*;
import java.util.*;
import app.user.*;
import app.standardCode.*;
import app.security.*;


public final class AdminProjectMainUpdateAction extends Action {


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
	     
        //PRIVS check that admin user is viewing this page 
        if(!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "admin")) {
            return (mapping.findForward("accessDenied"));
        }//END PRIVS check that admin user is viewing this page
        
        //get team related admin options for update
        List teamOptions = AdminService.getInstance().getTeamOptions();
                        
        //get team admin options from form/request
        DynaValidatorForm ad = (DynaValidatorForm) form;
        String showOldRatesScores = request.getParameter("showOldRatesScores");  
        
        //update each team admin option
        for(ListIterator iter = teamOptions.listIterator(); iter.hasNext();) {
            AdminOption ao = (AdminOption) iter.next();
            if(ao.getDescription().equals("showOldRatesScores")) {
                if(showOldRatesScores != null) { 
                    ao.setTrueFalseOption("on");
                }
                else {
                    ao.setTrueFalseOption("off");
                }
            }
            
            
            //update this admin option to db
            AdminService.getInstance().updateAdminOption(ao);
        }
        
        //get user list for privileges
        User[] usersArray = (User[]) ad.get("users");
        
        //update the user privileges
        for(int i = 0; i < usersArray.length; i++) {
            //get privileges from request
            String viewAccountTab = request.getParameter("viewAccountTab" + String.valueOf(i));
            String updateAccountTab = request.getParameter("updateAccountTab" + String.valueOf(i));
            
            //update each privilege per user
            if(viewAccountTab != null) {
                if(!StandardCode.getInstance().checkPrivSet(usersArray[i].getPrivileges(), "viewAccountTab")) {
                    Privilege p = new Privilege();
                    p.setPrivilege("viewAccountTab");
                    UserService.getInstance().addPrivilege(p, usersArray[i]);
                }
            }
            else {
                for(Iterator iter = usersArray[i].getPrivileges().iterator(); iter.hasNext();) {
                    Privilege p = (Privilege) iter.next();
                    if(p.getPrivilege().equals("viewAccountTab")) {
                        UserService.getInstance().deletePrivilege(p);
                    }
                }
            }
            
            if(updateAccountTab != null) {
                if(!StandardCode.getInstance().checkPrivSet(usersArray[i].getPrivileges(), "updateAccountTab")) {
                    Privilege p = new Privilege();
                    p.setPrivilege("updateAccountTab");
                    UserService.getInstance().addPrivilege(p, usersArray[i]);
                }
            }
            else {
                for(Iterator iter = usersArray[i].getPrivileges().iterator(); iter.hasNext();) {
                    Privilege p = (Privilege) iter.next();
                    if(p.getPrivilege().equals("updateAccountTab")) {
                        UserService.getInstance().deletePrivilege(p);
                    }
                }
            }
            
        }
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
