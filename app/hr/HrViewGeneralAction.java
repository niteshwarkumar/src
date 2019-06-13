//HrViewGeneralAction.java gets the current hr object (employee)
//from the db.  It then places that employee into the hr form for
//display in the jsp page. It then places location values into
//cookies

package app.hr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import java.util.*;
import app.user.*;
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;


public final class HrViewGeneralAction extends Action {


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
        
        //START get id of current user from either request, attribute, or cookie 
        //id of user from request
	String userId = null;
	userId = request.getParameter("userViewId");
        User  u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
        
        //check attribute in request
        if(userId == null) {
            userId = (String) request.getAttribute("userViewId");
        }
        
        //id of user from cookie
        if(userId == null) {            
          //  userId = StandardCode.getInstance().getCookie("userViewId", request.getCookies());
            userId=""+u.getUserId();
        }

        //default user to first if not in request or cookie
        if(userId == null || "null".equals(userId)) {
                List results = UserService.getInstance().getUserList();
                //look for first human user
                for(ListIterator iter = results.listIterator(); iter.hasNext();) {
                     u = (User) iter.next();
                    if(u.getLastName() != null && u.getLastName().length() > 0) {
                        userId = String.valueOf(u.getUserId());
                        break;
                    }
                }               
        }            
        
        Integer id = Integer.valueOf(userId);
        
        //END get id of current user from either request, attribute, or cookie               
        
        //get user to edit
        u = UserService.getInstance().getSingleUser(id); 
                
        //load user info for editing
        DynaValidatorForm uvg = (DynaValidatorForm) form;
        uvg.set("lastName", u.getLastName());
        uvg.set("firstName", u.getFirstName());        
        
        uvg.set("activeLevel", u.getActiveLevel());        
        
        //make sure location is not null
        Location location = u.getLocation();
        if(location != null) {
            uvg.set("location", location.getLocation());
        }
        
        //make sure position is not null
        Position1 position = u.getPosition();
        if(position != null) {
            uvg.set("position", position.getPosition());
        }
        
        //make sure department is not null
        Department department = u.getDepartment();
        if(department != null) {
            uvg.set("department", department.getDepartment());
        }
       
        uvg.set("homeAddress", u.getHomeAddress());
        uvg.set("homeCity", u.getHomeCity());
        uvg.set("homeState", u.getHomeState());
        uvg.set("homeCountry", u.getHomeCountry());
        uvg.set("homeZip", u.getHomeZip());
        uvg.set("homePhone", u.getHomePhone());
        uvg.set("homeCell", u.getHomeCell());
        uvg.set("homeEmail1", u.getHomeEmail1());
        uvg.set("homeEmail2", u.getHomeEmail2());
        
        uvg.set("workAddress", u.getWorkAddress());
        uvg.set("workCity", u.getWorkCity());
        uvg.set("workState", u.getWorkState());
        uvg.set("workCountry", u.getWorkCountry());
        uvg.set("workZip", u.getWorkZip());
        uvg.set("workPhone", u.getWorkPhone());
        uvg.set("workPhoneEx", u.getWorkPhoneEx());
        uvg.set("workCell", u.getWorkCell());
        uvg.set("workEmail1", u.getWorkEmail1());
        uvg.set("workEmail2", u.getWorkEmail2());
        uvg.set("skypeId",u.getSkypeId());
        //System.out.println(u.getSkypeId());
        uvg.set("emergencyContactName", u.getEmergencyContactName());
        uvg.set("emergencyContactRelation", u.getEmergencyContactRelation());
        uvg.set("emergencyContactPhone", u.getEmergencyContactPhone());
        
        //place this user into the user form for display
        request.setAttribute("userViewGeneral", uvg);        
        request.setAttribute("user", u);
        
        
        //add this user id to cookies; this will remember the last user
        response.addCookie(StandardCode.getInstance().setCookie("userViewId", userId));
        
        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("userViewTab", "General Info"));
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
