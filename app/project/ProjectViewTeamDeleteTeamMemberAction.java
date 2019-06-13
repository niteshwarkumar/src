/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.project;

import app.security.SecurityService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

/**
 *
 * @author Nishika
 */
public class ProjectViewTeamDeleteTeamMemberAction  extends Action {


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
	
        //get taskId from request
        String linTaskId = request.getParameter("linTaskId");
        if(null != linTaskId){
          LinTask linTask = ProjectService.getInstance().getSingleLinTask(Integer.parseInt(linTaskId));
          linTask.setPersonName("");
          linTask.setScale(null);
          ProjectService.getInstance().updateLinTask(linTask);
        }
        
        String dtpTaskId = request.getParameter("dtpTaskId");
        if(null != dtpTaskId){
          DtpTask dtpTask = ProjectService.getInstance().getSingleDtpTask(Integer.parseInt(dtpTaskId));
          dtpTask.setPersonName("");
          ProjectService.getInstance().updateDtpTask(dtpTask);
        }
        
        String engTaskId = request.getParameter("engTaskId");
        if(null != engTaskId){
          EngTask engTask = ProjectService.getInstance().getSingleEngTask(Integer.parseInt(engTaskId));
          engTask.setPersonName("");
          ProjectService.getInstance().updateEngTask(engTask);
        }
        
        String othTaskId = request.getParameter("othTaskId");
        if(null != othTaskId){
          OthTask othTask = ProjectService.getInstance().getSingleOthTask(Integer.parseInt(othTaskId));
          othTask.setPersonName("");
          ProjectService.getInstance().updateOthTask(othTask);
        }
        
      
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
