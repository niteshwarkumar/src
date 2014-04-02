//HrAdminRemoveEmployee1Action.java gets the employee
//to remove from the request.  It then marks the employee
//as currentEmployee=false.  The user is still in db;
//just this user will not show up in drop down boxes, etc.

package app.hrAdmin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import app.user.*;
import app.standardCode.*;
import app.security.*;


public final class HrAdminRemoveEmployee1Action extends Action {


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
        
        //PRIVS check that hrAdmin user is viewing this page 
        if(!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "hrAdmin")) {
            return (mapping.findForward("accessDenied"));
        }//END PRIVS check that hrAdmin user is viewing this page
        
        //get the user's id to delete from request
        String hrAdminUserId = request.getParameter("hrAdminUserId");
        
        //get the user to delete from db
        User u = UserService.getInstance().getSingleUser(Integer.valueOf(hrAdminUserId));
        
        //mark the user as not active (i.e., "deleted" from active list)
        u.setCurrentEmployee("false");
        u.setDropDown("false"); //exclude from dropdown boxes
        if(u.getUserType()!=null||u.getUserType().equalsIgnoreCase("client")){
            u.setUserType("Delete Client");
        }
        
        
        //set updated values to db
        UserService.getInstance().updateUser(u);
        
        //ALEKS 03-15-2006:  We also have to reset this userViewId from the session  
        response.addCookie(StandardCode.getInstance().setCookie("userViewId", null ));
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
