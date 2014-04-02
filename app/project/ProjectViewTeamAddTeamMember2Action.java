//ProjectViewTeamAddTeamMember2Action.java gets the resource
//and task; it then binds them together

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

public final class ProjectViewTeamAddTeamMember2Action extends Action {


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
	
        //get resource from request
        String resourceId = request.getParameter("projectViewTeamResourceId");
                
        //get task from session
        String linId = null;
        String engId = null;
        String dtpId = null;
        String othId = null;
          
        linId = (String) request.getSession(false).getAttribute("projectViewTeamLinTaskId");
        if(linId == null) {
            engId = (String) request.getSession(false).getAttribute("projectViewTeamEngTaskId");
            if(engId == null) {
                dtpId = (String) request.getSession(false).getAttribute("projectViewTeamDtpTaskId");
                if(dtpId == null) {
                    othId = (String) request.getSession(false).getAttribute("projectViewTeamOthTaskId");
                }
            }            
        }
        //get from db and bind
        LinTask lt = null;
        if(linId != null) {
            lt = ProjectService.getInstance().getSingleLinTask(Integer.valueOf(linId));
            lt.setPersonName(resourceId);
            ProjectService.getInstance().updateLinTask(lt);
        }
        EngTask et = null;
        if(engId != null) {
            et = ProjectService.getInstance().getSingleEngTask(Integer.valueOf(engId));
            et.setPersonName(resourceId);
            ProjectService.getInstance().updateEngTask(et);
        }
        DtpTask dt = null;
        if(dtpId != null) {
            dt = ProjectService.getInstance().getSingleDtpTask(Integer.valueOf(dtpId));
            dt.setPersonName(resourceId);
            ProjectService.getInstance().updateDtpTask(dt);
        }
        OthTask ot = null;
        if(othId != null) {
            ot = ProjectService.getInstance().getSingleOthTask(Integer.valueOf(othId));
            ot.setPersonName(resourceId);
            ProjectService.getInstance().updateOthTask(ot);
        } 
        
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
