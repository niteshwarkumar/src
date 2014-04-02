//HrAdminEmployeeMaintenanceAddTraining1Action.java gets the new hr info
//from a form.  It then uploads the new values (training) for
//this new employee to the db

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
import org.apache.struts.validator.*;
import app.user.*;
import app.standardCode.*;
import app.security.*;


public final class HrAdminEmployeeMaintenanceAddTraining1Action extends Action {


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
        DynaValidatorForm hat = (DynaValidatorForm) form;        
        
        String description = (String) hat.get("description");
        String dateCompleted = (String) hat.get("dateCompleted");
        String type = (String) hat.get("type");
        String company = (String) hat.get("company");
        String trainer = (String) hat.get("trainer");
        String location = (String) hat.get("location");
        String datestarted = (String) hat.get("datestarted");
        String result = (String) hat.get("result");
        String evidence = (String) hat.get("evidence");

                        
        //add new training to db
        Training t = new Training();
        if(datestarted.length() > 0) {
           t.setDateStart(DateService.getInstance().convertDate(datestarted).getTime());
        }
        if(dateCompleted.length() > 0) {
           t.setDateCompleted(DateService.getInstance().convertDate(dateCompleted).getTime());
        }
        t.setDescription(description);
        t.setCompany(company);
        t.setTrainer(trainer);
        t.setEvidence(evidence);
        t.setLocation(location);
        t.setResult(result);
        t.setType(type);
        
        //upload new training to db
        UserService.getInstance().addTraining(t, u);
               
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}