//HrAdminEmployeeMaintenanceAddAwayPreAction.java gets the 
//employee to edit from the db and places away totals
//for vacation/sick days left into the request

package app.hrAdmin;

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
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.validator.*;
import java.util.*;
import java.text.*;
import app.user.*;
import app.project.*;
import app.standardCode.*;
import app.db.*;
import app.workspace.*;
import app.security.*;


public final class HrAdminEmployeeMaintenanceAddAwayPreAction extends Action {


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
        
        //get the employee to edit from the request
        String hrAdminUserId = request.getParameter("hrAdminUserId");
        User u = UserService.getInstance().getSingleUser(Integer.valueOf(hrAdminUserId));
        
        //get total vacation/sick days left for display
        Double vacationTotal = u.getVacationDaysTotal();
        Double sickTotal = u.getSickDaysTotal();
        if(vacationTotal == null) {
            vacationTotal = new Double(0);
        }
        if(sickTotal == null) {
            sickTotal = new Double(0);
        }
        Double vacationUsed = u.getVacationDaysUsed();
        Double sickUsed = u.getSickDaysUsed();
        if(vacationUsed == null) {
            vacationUsed = new Double(0);
        }
        if(sickUsed == null) {
            sickUsed = new Double(0);
        }
        
        String vacationDaysLeft = String.valueOf(vacationTotal.doubleValue() - vacationUsed.doubleValue());
        String sickDaysLeft = String.valueOf(sickTotal.doubleValue() - sickUsed.doubleValue());
        
        //unpaid days used
        Double unpaidUsed = u.getUnpaidDaysUsed();
        if(unpaidUsed == null) {
            unpaidUsed = new Double(0);
        }
        
        //values for display
        request.setAttribute("vacationDaysTotal", String.valueOf(vacationTotal));
        request.setAttribute("sickDaysTotal", String.valueOf(sickTotal));
        request.setAttribute("vacationDaysUsed", String.valueOf(vacationUsed));
        request.setAttribute("sickDaysUsed", String.valueOf(sickUsed));
        request.setAttribute("unpaidDaysUsed", String.valueOf(unpaidUsed));
        request.setAttribute("vacationDaysLeft", vacationDaysLeft);
        request.setAttribute("sickDaysLeft", sickDaysLeft);
        request.setAttribute("user", u); //for display
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
