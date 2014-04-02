//ProjectEditUpdateAction.java gets updated project info
//(from General Info tab) from a form.  It then uploads the new values for
//this project to the db

package app.project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import app.client.*;
import app.security.*;


public final class RegenerateCCPasswordAction extends Action {


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
	
        //which project to update from hidden value in form
        String id = request.getParameter("projectViewId");
        
        //get the project to be updated from db
        Project p = ProjectService.getInstance().getSingleProject(Integer.valueOf(id));
        
                String randomPwd = ProjectService.getInstance().generateRandomPassword(8);
                p.getContact().setTracker_password(randomPwd);
                ClientService.getInstance().clientContactUpdate(p.getContact());
                
         
            
       
        
        
        //set updated values to db
       // ProjectService.getInstance().updateProject(p);
        
        
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
