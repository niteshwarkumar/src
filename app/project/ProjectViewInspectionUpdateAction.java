//ProjectViewInspectionUpdateAction.java updates inspection info
//for a project

package app.project;
import app.extjs.helpers.ProjectHelper;
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
import app.security.*;
import app.standardCode.*;



public final class ProjectViewInspectionUpdateAction extends Action {


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
        
        //START get id of current project from either request, attribute, or cookie 
        //id of project from request
	String projectId = null;
	projectId = request.getParameter("projectId");
        
                 
        
        Integer id = Integer.valueOf(projectId);
         if(request.getParameter("projectInformalsJSON")!=null){
            
          ProjectHelper.updateInspections(id.intValue(),request.getParameter("projectInformalsJSON"), "RECEIVING");
         
        }
        
        if(request.getParameter("projectInformalsJSON2")!=null){
            
          ProjectHelper.updateInspections(id.intValue(),request.getParameter("projectInformalsJSON2"), "IN-PROCESS");
         
        }
        
        if(request.getParameter("projectInformalsJSON3")!=null){
            
          ProjectHelper.updateInspections(id.intValue(),request.getParameter("projectInformalsJSON3"), "FINAL");
         
        }
            
        
        
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
