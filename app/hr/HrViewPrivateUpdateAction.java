//HrViewGeneralUpdateAction.java gets updated hr info
//from a form.  It then uploads the new values for
//this employee to the db //NOTE not completed yet

package app.hr;

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
import app.security.*;


public final class HrViewPrivateUpdateAction extends Action {


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
        
	//which user to update from hidden value in form
        String id = request.getParameter("userViewId");  
        //get the user to be updated from db
        User u = UserService.getInstance().getSingleUser(Integer.valueOf(id));
        
        //PRIVS check that logged-in user is editing this employee record 
        if(!request.getSession(false).getAttribute("username").toString().equals(u.getUsername())) {//this user is not editing only there employee information
            return (mapping.findForward("Success"));
        }
        
        //values for update from form; change what is stored in db to these values
        DynaValidatorForm uvg = (DynaValidatorForm) form;
        
        String firstName = (String) uvg.get("firstName");
        String lastName = (String) uvg.get("lastName");
        
        String position = (String) uvg.get("position");
        Position1 p = UserService.getInstance().getSinglePosition(position);
        
        String department = (String) uvg.get("department");
        Department d = UserService.getInstance().getSingleDepartment(department);
        
        String location = (String) uvg.get("location");
        Location l = UserService.getInstance().getSingleLocation(location);
        
        String homeAddress = (String) uvg.get("homeAddress");
        String homeCity = (String) uvg.get("homeCity");
        String homeState = (String) uvg.get("homeState");
        String homeCountry = (String) uvg.get("homeCountry");
        String homeZip = (String) uvg.get("homeZip");
        String homePhone = (String) uvg.get("homePhone");
        String homeCell = (String) uvg.get("homeCell");
        String homeEmail1 = (String) uvg.get("homeEmail1");
        String homeEmail2 = (String) uvg.get("homeEmail2");
        String workAddress = (String) uvg.get("workAddress");
        String workCity = (String) uvg.get("workCity");
        String workState = (String) uvg.get("workState");
        String workCountry = (String) uvg.get("workCountry");
        String workZip = (String) uvg.get("workZip");
        String workPhone = (String) uvg.get("workPhone");
        String workCell = (String) uvg.get("workCell");
        String workEmail1 = (String) uvg.get("workEmail1");
        String workEmail2 = (String) uvg.get("workEmail2");
        String emergencyContactName = (String) uvg.get("emergencyContactName");
        String emergencyContactRelation = (String) uvg.get("emergencyContactRelation");
        String emergencyContactPhone = (String) uvg.get("emergencyContactPhone");
        
        //update the user's values
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setPosition(p);
        u.setDepartment(d);
        u.setLocation(l);
        u.setHomeAddress(homeAddress);
        u.setHomeCity(homeCity);
        u.setHomeState(homeState);
        u.setHomeCountry(homeCountry);
        u.setHomeZip(homeZip);
        u.setHomePhone(homePhone);
        u.setHomeCell(homeCell);
        u.setHomeEmail1(homeEmail1);
        u.setHomeEmail2(homeEmail2);
        u.setWorkAddress(workAddress);
        u.setWorkCity(workCity);
        u.setWorkState(workState);
        u.setWorkCountry(workCountry);
        u.setWorkZip(workZip);
        u.setWorkPhone(workPhone);
        u.setWorkCell(workCell);
        u.setWorkEmail1(workEmail1);
        u.setWorkEmail2(workEmail2);
        u.setEmergencyContactName(emergencyContactName);
        u.setEmergencyContactRelation(emergencyContactRelation);
        u.setEmergencyContactPhone(emergencyContactPhone);
        
        //set updated values to db
        UserService.getInstance().updateUser(u);
        
        //place user id into request for display
        request.setAttribute("user", u);
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
