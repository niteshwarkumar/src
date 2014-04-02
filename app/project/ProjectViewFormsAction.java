//ProjectViewForms1Action.java prepares the display
//for adding and generating PDF forms

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
import java.util.*;
import app.security.*;
import app.standardCode.*;

public final class ProjectViewFormsAction extends Action {


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
        
        //get list of team members (contractors) for this project
        ArrayList linTasks = new ArrayList();
        ArrayList dtpTasks = new ArrayList();
        for(Iterator iterSources = p.getSourceDocs().iterator(); iterSources.hasNext();) {
            SourceDoc sd = (SourceDoc) iterSources.next();
            for(Iterator iterTargets = sd.getTargetDocs().iterator(); iterTargets.hasNext();) {
                TargetDoc td = (TargetDoc) iterTargets.next();
                if(td.getLinTasks() != null) {
                    for(Iterator iterLin = td.getLinTasks().iterator(); iterLin.hasNext();) {
                        LinTask lt = (LinTask) iterLin.next();
                        if((lt.getPersonName() != null && lt.getPersonName().length() > 0)) {
                            linTasks.add(lt);
                        }
                    }
                }
                if(td.getDtpTasks() != null) {
                    for(Iterator iterDtp = td.getDtpTasks().iterator(); iterDtp.hasNext();) {
                        DtpTask dt = (DtpTask) iterDtp.next();
                        if((dt.getPersonName() != null && dt.getPersonName().length() > 0)) {
                            dtpTasks.add(dt);
                        }
                    }
                }
            }
        }
        
        //add contractor lists to request
        request.setAttribute("linTasks", linTasks);
        request.setAttribute("dtpTasks", dtpTasks);
        
        //add this project id to cookies; this will remember the last project
        response.addCookie(StandardCode.getInstance().setCookie("projectViewId", projectId));
        
        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("projectViewTab", "Forms"));
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
