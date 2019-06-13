/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.comm;

import app.project.Project;
import app.project.ProjectService;
import app.quote.Quote1;
import app.quote.QuoteService;
import app.security.SecurityService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * @author niteshwar
 */
public class RequirementPreAction extends Action {


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
           // return (mapping.findForward("welcome"));
        }
        //END check for login (security)

        Requirement reqmt = new Requirement();
       
         String projectAll = request.getParameter("projectAll");
        if(null == projectAll) { projectAll = (String) request.getAttribute("projectAll");}
        if(null == projectAll) { projectAll = "N";}
        request.setAttribute("projectAll", projectAll);
        
        String edit = request.getParameter("edit");
        if(null == edit) { edit = (String) request.getAttribute("edit");}
        if(null == edit) { edit = "N";}
        
        String tab = request.getParameter("tab");
        if (null == tab) {tab = (String) request.getAttribute("tab");}
        String type = request.getParameter("type");
        if (null == type) {type = (String) request.getAttribute("type");}
              
        String projectId = request.getParameter("projectId");
        if (null == projectId) {projectId = (String) request.getAttribute("projectId");}
        if(request.getParameter("quoteViewId")!=null){
        String quoteId = request.getParameter("quoteViewId");
            Quote1 q = QuoteService.getInstance().getSingleQuote(Integer.parseInt(quoteId));
            projectId = ""+q.getProject().getProjectId();
        }
        if (null == projectId) {projectId = "0";}
         String clientId = request.getParameter("clientId");
        if (null == clientId) {clientId = (String) request.getAttribute("clientId");}
        if (null == clientId) {
            if(!projectId.equalsIgnoreCase("0")){
            Project p = ProjectService.getInstance().getSingleProject(Integer.parseInt(projectId));
            clientId = ""+p.getCompany().getClientId();
            }}
        if (null == clientId) {clientId = "0";}
         String id = request.getParameter("id");
        if (null == id) {id = (String) request.getAttribute("id");}
        if (null == id) {id = "0";}
        
        List<Requirement> reqLst = CommService.getInstance().getRequirement(type, tab, Integer.parseInt(clientId),Integer.parseInt(projectId));
        request.setAttribute("reqLst", reqLst);
        request.setAttribute("tab", tab);
        request.setAttribute("edit", edit);
        request.setAttribute("type", type);
        request.setAttribute("projectId", projectId);
        request.setAttribute("clientId", clientId);
        
        if(projectAll.equalsIgnoreCase("Y")){
            reqLst = CommService.getInstance().getRequirement("P", tab, Integer.parseInt(clientId),Integer.parseInt(projectId));
            reqLst.addAll(CommService.getInstance().getRequirement("C", tab, Integer.parseInt(clientId),0));
            reqLst.addAll(CommService.getInstance().getRequirement("G", tab, 0, 0));
            request.setAttribute("reqLst", reqLst);
            request.setAttribute("edit", "Y");
            request.setAttribute("type", "A");
            return (mapping.findForward("ProjectEdit"));
        }
      
	// Forward control to the specified success URI
        if(type.equalsIgnoreCase("A")){
        
            reqLst = CommService.getInstance().getRequirement("P", tab, Integer.parseInt(clientId),Integer.parseInt(projectId));
            reqLst.addAll(CommService.getInstance().getRequirement("C", tab, Integer.parseInt(clientId),0));
            reqLst.addAll(CommService.getInstance().getRequirement("G", tab, 0,0));
            request.setAttribute("reqLst", reqLst);
            return (mapping.findForward("NonEdit"));
           
            
            
        }
	return (mapping.findForward("Success"));
    }

}
