//ProjectViewTeamTradosUploadPreAction.java gets the display
//ready (the heading) for uploading trados values

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
import app.security.*;
import app.standardCode.*;


public final class ProjectViewTeamTradosUploadPreAction extends Action {


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
                     
        //get current project ID
        
        //id from request attribute
        String projectId = (String) request.getAttribute("projectViewId");
        
        //id from cookie
        if(projectId == null) {
            projectId = StandardCode.getInstance().getCookie("projectViewId", request.getCookies());	
        }
        
        Integer id = Integer.valueOf(projectId);        
        
        //get project with id from database
        Project p = ProjectService.getInstance().getSingleProject(id);
        
        //put this project into the request
        request.setAttribute("project", p);
        
        //place lin task id in cookie; used during upload of trados file
        response.addCookie(StandardCode.getInstance().setCookie("projectViewTeamTradosUploadId", request.getParameter("id")));
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
