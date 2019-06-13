//LogoutUserAction.java handles the logout action of the
//user.  It clears all session level variables: security
//values and privs.  Now that they are clear, the user
//(or anyone else using the same computer) will be required
//to log in again.

package app.tracker;

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
import org.apache.struts.validator.DynaValidatorForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleException;
import java.util.Date;
import java.text.*;
import app.user.*;
import org.apache.struts.util.MessageResources;
import org.apache.commons.beanutils.PropertyUtils;
import app.workspace.*;
import app.security.*;


public final class LogoutUserAction extends Action {

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
	
	// Report any errors we have discovered back to the original form
	if (!errors.isEmpty()) {
	    saveMessages(request, errors);
            return (mapping.getInputForward());
	}        

        User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
        u.setLast_activity(new java.sql.Timestamp(System.currentTimeMillis()-1000*60*16));
        UserService.getInstance().updateUser(u);
// clear session variables
	HttpSession session = request.getSession(false);
        session.invalidate();
                
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));

    }

}
