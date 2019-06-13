//HrAdminEmployeeMaintenanceViewSickPreAction.java sets up the display
//for viewing/editing away events

package app.hrAdmin;

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
import org.apache.struts.validator.*;
import java.util.*;
import java.text.*;
import app.user.*;
import app.db.*;
import app.workspace.*;
import app.standardCode.*;
import app.security.*;
import app.standardCode.*;

public final class HrAdminEmployeeMaintenanceViewSickPreAction extends Action {


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
        
        //get user to edit from request variable
        String hrAdminUserId = request.getParameter("hrAdminUserId");
        User u = null;
        if(hrAdminUserId != null) {
            u = UserService.getInstance().getSingleUser(Integer.valueOf(hrAdminUserId));
        }
        else {
            hrAdminUserId = StandardCode.getInstance().getCookie("hrAdminUserId", request.getCookies());
            u = UserService.getInstance().getSingleUser(Integer.valueOf(StandardCode.getInstance().getCookie("hrAdminUserId", request.getCookies())));
        }
        
        //get away events
        List aways = UserService.getInstance().getUserSickHistoryYear(u.getUserId(), u.getHireDate());
        
        //place into form for display
        DynaValidatorForm ha = (DynaValidatorForm) form;
        Away[] awaysArray = (Away[]) aways.toArray(new Away[0]);
        ha.set("aways", awaysArray);
               
        //set up away dates
        for(int i = 0; i < awaysArray.length; i++) {
            if(awaysArray[i].getRequestedDate() != null) {
                request.setAttribute("hrAdminRequest" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(awaysArray[i].getRequestedDate()));
            }
            else {
                request.setAttribute("hrAdminRequest" + String.valueOf(i), "");
            }
        }
        for(int i = 0; i < awaysArray.length; i++) {
            if(awaysArray[i].getApprovedDate() != null) {
                request.setAttribute("hrAdminApproved" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(awaysArray[i].getApprovedDate()));
            }
            else {
                request.setAttribute("hrAdminApproved" + String.valueOf(i), "");
            }
        }
        for(int i = 0; i < awaysArray.length; i++) {
            if(awaysArray[i].getStartDate() != null) {
                request.setAttribute("hrAdminStartDate" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(awaysArray[i].getStartDate()));
            }
            else {
                request.setAttribute("hrAdminStartDate" + String.valueOf(i), "");
            }
        }
        for(int i = 0; i < awaysArray.length; i++) {
            if(awaysArray[i].getEndDate() != null) {
                request.setAttribute("hrAdminEndDate" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(awaysArray[i].getEndDate()));
            }
            else {
                request.setAttribute("hrAdminEndDate" + String.valueOf(i), "");
            }
        }
        
        //set away total days
        double awayTotal = 0;
        for(int i = 0; i < awaysArray.length; i++) {
            if(awaysArray[i].getDaysUsed() != null) {
                awayTotal += awaysArray[i].getDaysUsed().doubleValue();
            }
        }
        
        //set user and away total into request for display
        request.setAttribute("user", u);
        request.setAttribute("awayTotal", String.valueOf(awayTotal));
        response.addCookie(StandardCode.getInstance().setCookie("hrAdminUserId", hrAdminUserId));
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
