//HrAdminEmployeeMaintenanceAddAway1Action.java gets the new hr info
//from a form.  It then uploads the new values (vacation/sick/unpaid/travel days) for
//this employee to the db

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
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.*;
import java.io.*;
import java.util.*;
import app.user.*;
import app.client.*;
import app.db.*;
import app.workspace.*;
import app.standardCode.*;
import app.security.*;


public final class HrAdminEmployeeMaintenanceAddAway1Action extends Action {


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
        
        //get user to edit from form hidden field
        String hrAdminUserId = request.getParameter("hrAdminUserId");
        User u = UserService.getInstance().getSingleUser(Integer.valueOf(hrAdminUserId));
        
        //values for update from form; change what is stored in db to these values
        DynaValidatorForm uvg = (DynaValidatorForm) form;        
        
        String vacationRequest = (String) uvg.get("vacationRequest");
        String vacationApproved = (String) uvg.get("vacationApproved");
        String vacationStartDate = (String) uvg.get("vacationStartDate");
        String vacationEndDate = (String) uvg.get("vacationEndDate");
        String totalVacationDays = (String) uvg.get("totalVacationDays");
        String sickRequest = (String) uvg.get("sickRequest");
        String sickApproved = (String) uvg.get("sickApproved");
        String sickStartDate = (String) uvg.get("sickStartDate");
        String sickEndDate = (String) uvg.get("sickEndDate");
        String totalSickDays = (String) uvg.get("totalSickDays");        
        String unpaidRequest = (String) uvg.get("unpaidRequest");
        String unpaidApproved = (String) uvg.get("unpaidApproved");
        String unpaidStartDate = (String) uvg.get("unpaidStartDate");
        String unpaidEndDate = (String) uvg.get("unpaidEndDate");
        String totalUnpaidDays = (String) uvg.get("totalUnpaidDays");        
        String travelStartDate = (String) uvg.get("travelStartDate");
        String travelEndDate = (String) uvg.get("travelEndDate");
        String travelDescription = (String) uvg.get("travelDescription");
                
        //update the user's values    
        
        //VACATION
        if(vacationStartDate.length() > 0 && vacationEndDate.length() > 0 && totalVacationDays.length() > 0) { //if present
            u.setVacationDaysUsed(new Double(Double.valueOf(totalVacationDays).doubleValue() + u.getVacationDaysUsed().doubleValue()));
            //update employee's used days
            UserService.getInstance().updateUser(u);
            
            //add new away event
            Away a = new Away();
            if(vacationRequest.length() > 0) {
                a.setRequestedDate(DateService.getInstance().convertDate(vacationRequest).getTime());
            }
            if(vacationApproved.length() > 0) {
                a.setApprovedDate(DateService.getInstance().convertDate(vacationApproved).getTime());
            }
            a.setStartDate(DateService.getInstance().convertDate(vacationStartDate).getTime());
            a.setStartDate(DateService.getInstance().convertDate(vacationStartDate).getTime());
            a.setEndDate(DateService.getInstance().convertDate(vacationEndDate).getTime());
            a.setDaysUsed(new Double(totalVacationDays));
            a.setType("Vacation");
            UserService.getInstance().addAway(a, u);
        }
        
        //SICK
        if(sickStartDate.length() > 0 && sickEndDate.length() > 0 && totalSickDays.length() > 0) { //if present
            u.setSickDaysUsed(new Double(Double.valueOf(totalSickDays).doubleValue() + u.getSickDaysUsed().doubleValue()));
            //update employee's used days
            UserService.getInstance().updateUser(u);
            
            //add new away event
            Away a = new Away();
            if(sickRequest.length() > 0) {
                a.setRequestedDate(DateService.getInstance().convertDate(sickRequest).getTime());
            }
            if(sickApproved.length() > 0) {
                a.setApprovedDate(DateService.getInstance().convertDate(sickApproved).getTime());
            }
            a.setStartDate(DateService.getInstance().convertDate(sickStartDate).getTime());
            a.setEndDate(DateService.getInstance().convertDate(sickEndDate).getTime());
            a.setDaysUsed(new Double(totalSickDays));
            a.setType("Sick");
            UserService.getInstance().addAway(a, u);
        }
        
        //UNPAID
        if(unpaidStartDate.length() > 0 && unpaidEndDate.length() > 0 && totalUnpaidDays.length() > 0) { //if present
            if(u.getUnpaidDaysUsed() == null) {
                u.setUnpaidDaysUsed(new Double(0));
            }
            u.setUnpaidDaysUsed(new Double(Double.valueOf(totalUnpaidDays).doubleValue() + u.getUnpaidDaysUsed().doubleValue()));
            //update employee's used days
            UserService.getInstance().updateUser(u);
            
            //add new away event
            Away a = new Away();
            if(unpaidRequest.length() > 0) {
                a.setRequestedDate(DateService.getInstance().convertDate(unpaidRequest).getTime());
            }
            if(unpaidApproved.length() > 0) {
                a.setApprovedDate(DateService.getInstance().convertDate(unpaidApproved).getTime());
            }
            a.setStartDate(DateService.getInstance().convertDate(unpaidStartDate).getTime());
            a.setEndDate(DateService.getInstance().convertDate(unpaidEndDate).getTime());
            a.setDaysUsed(new Double(totalUnpaidDays));
            a.setType("Unpaid");
            UserService.getInstance().addAway(a, u);
        }
        
        //TRAVEL
        if(travelStartDate.length() > 0 && travelEndDate.length() > 0) { //if present
            //add new away event
            Away a = new Away();
            a.setStartDate(DateService.getInstance().convertDate(travelStartDate).getTime());
            a.setEndDate(DateService.getInstance().convertDate(travelEndDate).getTime());
            a.setDescription(travelDescription);
            a.setType("Travel");
            UserService.getInstance().addAway(a, u);
        }        
                               
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
