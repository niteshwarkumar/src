//HrAdminEmployeeMaintenancePerformanceReviewViewAction.java gets the 
//performance reviews for one user (employee)

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


public final class HrAdminEmployeeMaintenancePerformanceReviewViewAction extends Action {


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
        
        //get the employee to edit from the request or session
        String hrAdminUserId = request.getParameter("hrAdminUserId");
        User u = null;
        if(hrAdminUserId == null) {
            hrAdminUserId = (String) request.getSession(false).getAttribute("hrAdminUserId");
            u = UserService.getInstance().getSingleUser(Integer.valueOf(hrAdminUserId));            
        }
        else {
            u = UserService.getInstance().getSingleUser(Integer.valueOf(hrAdminUserId));
        }
        
        //get performanceReviews
        Set performanceReviews = u.getPerformanceReviews();
        
        //place performanceReviews into form for display
        DynaValidatorForm ha = (DynaValidatorForm) form;
        PerformanceReview[] performanceReviewsArray = (PerformanceReview[]) performanceReviews.toArray(new PerformanceReview[0]);
        ha.set("performanceReviews", performanceReviewsArray);
               
        //set up performance review dates
        for(int i = 0; i < performanceReviewsArray.length; i++) {
            if(performanceReviewsArray[i].getDueDate() != null) {
                request.setAttribute("hrAdminDueDate" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(performanceReviewsArray[i].getDueDate()));
            }
            else {
                request.setAttribute("hrAdminDueDate" + String.valueOf(i), "");
            }
        }
        for(int i = 0; i < performanceReviewsArray.length; i++) {
            if(performanceReviewsArray[i].getActualDate() != null) {
                request.setAttribute("hrAdminActualDate" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(performanceReviewsArray[i].getActualDate()));
            }
            else {
                request.setAttribute("hrAdminActualDate" + String.valueOf(i), "");
            }
        }
        for(int i = 0; i < performanceReviewsArray.length; i++) {
            if(performanceReviewsArray[i].getSignedDate() != null) {
                request.setAttribute("hrAdminSignedDate" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(performanceReviewsArray[i].getSignedDate()));
            }
            else {
                request.setAttribute("hrAdminSignedDate" + String.valueOf(i), "");
            }
        }
        
        request.setAttribute("user", u); //for display
        
        //place user id into session
        request.getSession(false).setAttribute("hrAdminUserId", hrAdminUserId);
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
