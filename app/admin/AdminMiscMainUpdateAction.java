//AdminMiscMainUpdateAction.java gets misc related options
//and updates changes to them to the db

package app.admin;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleException;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.*;
import org.apache.commons.beanutils.PropertyUtils;
import java.util.*;
import app.user.*;
import app.db.*;
import app.project.*;
import app.workspace.*;
import app.standardCode.*;
import app.security.*;


public final class AdminMiscMainUpdateAction extends Action {


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
        
        //get misc related admin options for update
        List miscOptions = AdminService.getInstance().getMiscOptions();
                        
        //get misc admin options from form/request
        DynaValidatorForm ad = (DynaValidatorForm) form;
        Location location1 = (Location) ad.get("location1");  
        Location location2 = (Location) ad.get("location2");  
        Location location3 = (Location) ad.get("location3");  
        Location location4 = (Location) ad.get("location4");  
        
        //update each misc admin option
        for(ListIterator iter = miscOptions.listIterator(); iter.hasNext();) {
            AdminOption ao = (AdminOption) iter.next();
            if(ao.getDescription().equals("showOldRatesScores")) {
               
            }
            
            
            //update this admin option to db
            AdminService.getInstance().updateAdminOption(ao);
        }
        
        //get user list for privileges
        User[] usersArray = (User[]) ad.get("users");
        
        //update the user privileges
        for(int i = 0; i < usersArray.length; i++) {
            //get privileges from request
            String viewUpdateAdmin = request.getParameter("viewUpdateAdmin" + String.valueOf(i));
            String viewUpdateHrAdmin = request.getParameter("viewUpdateHrAdmin" + String.valueOf(i));
            String viewUpdateReports = request.getParameter("viewUpdateReports" + String.valueOf(i));
            
            //update each privilege per user
            if(viewUpdateAdmin != null) {
                if(!StandardCode.getInstance().checkPrivSet(usersArray[i].getPrivileges(), "admin")) {
                    Privilege p = new Privilege();
                    p.setPrivilege("admin");
                    UserService.getInstance().addPrivilege(p, usersArray[i]);
                }
            }
            else {
                for(Iterator iter = usersArray[i].getPrivileges().iterator(); iter.hasNext();) {
                    Privilege p = (Privilege) iter.next();
                    if(p.getPrivilege().equals("admin")) {
                        UserService.getInstance().deletePrivilege(p);
                    }
                }
            }
            
            if(viewUpdateHrAdmin != null) {
                if(!StandardCode.getInstance().checkPrivSet(usersArray[i].getPrivileges(), "hrAdmin")) {
                    Privilege p = new Privilege();
                    p.setPrivilege("hrAdmin");
                    UserService.getInstance().addPrivilege(p, usersArray[i]);
                }
            }
            else {
                for(Iterator iter = usersArray[i].getPrivileges().iterator(); iter.hasNext();) {
                    Privilege p = (Privilege) iter.next();
                    if(p.getPrivilege().equals("hrAdmin")) {
                        UserService.getInstance().deletePrivilege(p);
                    }
                }
            }
            
            if(viewUpdateReports != null) {
                if(!StandardCode.getInstance().checkPrivSet(usersArray[i].getPrivileges(), "reports")) {
                    Privilege p = new Privilege();
                    p.setPrivilege("reports");
                    UserService.getInstance().addPrivilege(p, usersArray[i]);
                }
            }
            else {
                for(Iterator iter = usersArray[i].getPrivileges().iterator(); iter.hasNext();) {
                    Privilege p = (Privilege) iter.next();
                    if(p.getPrivilege().equals("reports")) {
                        UserService.getInstance().deletePrivilege(p);
                    }
                }
            }
            
        }
        
        //update the three locations
        UserService.getInstance().updateLocation(location1);
        UserService.getInstance().updateLocation(location2);
        UserService.getInstance().updateLocation(location3);
        UserService.getInstance().updateLocation(location4);
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
