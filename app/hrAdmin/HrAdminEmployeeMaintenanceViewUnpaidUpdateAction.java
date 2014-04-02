//HrAdminEmployeeMaintenanceViewUnpaidUpdateAction.java updates a
//list of away events for a user

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
import org.apache.struts.validator.*;
import org.apache.commons.beanutils.PropertyUtils;
import java.util.*;
import app.user.*;
import app.db.*;
import app.project.*;
import app.workspace.*;
import app.standardCode.*;
import app.security.*;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;
import sun.misc.CharacterEncoder;

public final class HrAdminEmployeeMaintenanceViewUnpaidUpdateAction extends Action {


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
        
        //get the updated list of away events for a user
        DynaValidatorForm ha = (DynaValidatorForm) form;
        Away[] aways = (Away[]) ha.get("aways");
        
        //update the away events
        for(int i = 0; i < aways.length; i++) {
            String hrAdminRequest = request.getParameter("hrAdminRequest" + String.valueOf(i));
            if(hrAdminRequest.length() >= 1) {
                aways[i].setRequestedDate(DateService.getInstance().convertDate(hrAdminRequest).getTime());
            }
            String hrAdminApproved = request.getParameter("hrAdminApproved" + String.valueOf(i));
            if(hrAdminApproved.length() >= 1) {
                aways[i].setApprovedDate(DateService.getInstance().convertDate(hrAdminApproved).getTime());
            }
            String hrAdminStartDate = request.getParameter("hrAdminStartDate" + String.valueOf(i));
            if(hrAdminStartDate.length() >= 1) {
                aways[i].setStartDate(DateService.getInstance().convertDate(hrAdminStartDate).getTime());
            }
            String hrAdminEndDate = request.getParameter("hrAdminEndDate" + String.valueOf(i));
            if(hrAdminEndDate.length() >= 1) {
                aways[i].setEndDate(DateService.getInstance().convertDate(hrAdminEndDate).getTime());
            }
            UserService.getInstance().updateAway(aways[i]);
        }
        
//        //update the used away days for the user (NOTE: if used, put inside update loop above)
//        User u = UserService.getInstance().getSingleUser(Integer.valueOf(StandardCode.getInstance().getCookie("hrAdminUserId", request.getCookies())));
//        int awayOld = 0;
//        if(u.getVacationDaysUsed() != null) {
//            awayHold = u.getVacationDaysUsed().intValue();
//        }
//        if()
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
