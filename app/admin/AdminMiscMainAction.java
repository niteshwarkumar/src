//AdminMiscMainAction.java gets misc related options
//ready

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


public final class AdminMiscMainAction extends Action {


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
        
        //get misc related admin options
        List miscOptions = AdminService.getInstance().getMiscOptions();
                
        //place misc admin options into form
        DynaValidatorForm ad = (DynaValidatorForm) form;
        for(ListIterator iter = miscOptions.listIterator(); iter.hasNext();) {
            AdminOption ao = (AdminOption) iter.next();
            if(ao.getDescription().equals("showOldRatesScores")) {
                ad.set("showOldRatesScores", ao.getTrueFalseOption());  
            }
        }
        
        //get user list for privileges
        List users = UserService.getInstance().getUserListCurrent();               
        //place users into form
        User[] usersArray = (User[]) users.toArray(new User[0]);
        ad.set("users", usersArray);          
        
        //add privileges
        for(int i = 0; i < usersArray.length; i++) {
            if(StandardCode.getInstance().checkPrivSet(usersArray[i].getPrivileges(), "admin")) {
                request.setAttribute("viewUpdateAdmin" + String.valueOf(i), "checked");
            }
            else {
                request.setAttribute("viewUpdateAdmin" + String.valueOf(i), "");
            }
            
            if(StandardCode.getInstance().checkPrivSet(usersArray[i].getPrivileges(), "hrAdmin")) {
                request.setAttribute("viewUpdateHrAdmin" + String.valueOf(i), "checked");
            }
            else {
                request.setAttribute("viewUpdateHrAdmin" + String.valueOf(i), "");
            }
            
            if(StandardCode.getInstance().checkPrivSet(usersArray[i].getPrivileges(), "reports")) {
                request.setAttribute("viewUpdateReports" + String.valueOf(i), "checked");
            }
            else {
                request.setAttribute("viewUpdateReports" + String.valueOf(i), "");
            }
        }
        
        //get locations for display and place in form
        List locations = UserService.getInstance().getLocationList();
        for(ListIterator iter = locations.listIterator(); iter.hasNext();) {
            Location l = (Location) iter.next();
            if(l.getLocationId().equals(new Integer(1)))
                ad.set("location2", l);
            if(l.getLocationId().equals(new Integer(2)))
                ad.set("location1", l);
            if(l.getLocationId().equals(new Integer(3)))
                ad.set("location3", l);
            if(l.getLocationId().equals(new Integer(4)))
                ad.set("location4", l);
        }
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
