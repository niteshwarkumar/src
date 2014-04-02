/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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

/**
 *
 * @author Niteshwar
 */
public class ProjectViewReqUpdateLin extends Action {
    
    
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
        projectId = request.getParameter("projectViewId");
        
        //check attribute in request
        if(projectId == null) {
            projectId = (String) request.getAttribute("projectViewId");
        }
        
        //id of project from cookie
        if(projectId == null) {
            projectId = StandardCode.getInstance().getCookie("projectViewId", request.getCookies());
        }
        
        //default project to last if not in request or cookie
        if(projectId == null) {
            List results = ProjectService.getInstance().getProjectList();
            
            ListIterator iterScroll = null;
            for(iterScroll = results.listIterator(); iterScroll.hasNext(); iterScroll.next()) {}
            iterScroll.previous();
            Project p = (Project) iterScroll.next();
            projectId = String.valueOf(p.getProjectId());
        }
        
        Integer id = Integer.valueOf(projectId);



      if(request.getParameter("projectInformalsLinJSON")!=null){

          ProjectHelper.updateProjectReqs(id.intValue(),request.getParameter("projectInformalsLinJSON"),"LIN");

        }
        if(request.getParameter("projectInformalsDtpJSON")!=null){

          ProjectHelper.updateProjectReqs(id.intValue(),request.getParameter("projectInformalsDtpJSON"),"DTP");

        }
        if(request.getParameter("projectInformalsEngJSON")!=null){

          ProjectHelper.updateProjectReqs(id.intValue(),request.getParameter("projectInformalsEngJSON"),"ENG");

        }
    // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }

}
