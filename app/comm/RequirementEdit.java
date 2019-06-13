/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.comm;

import app.admin.AdminService;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class RequirementEdit extends Action {


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

         String projectAll = request.getParameter("projectAll");
        if(null == projectAll) { projectAll = (String) request.getAttribute("projectAll");}
        if(null == projectAll) { projectAll = "N";}
        request.setAttribute("projectAll", projectAll);
        String isReqComp = request.getParameter("isReqComp");
//        String reqComp = request.getParameter("reqComp");
        
        Requirement reqmt = new Requirement();        
        
        
       
              
        String tab = request.getParameter("tab");
        if (null == tab) {tab = (String) request.getAttribute("tab");}
        String type = request.getParameter("type");
        if (null == type) {type = (String) request.getAttribute("type");}
              
        String projectId = request.getParameter("projectId");
        if (null == projectId) {projectId = (String) request.getAttribute("projectId");}
        if (null == projectId) {projectId = "0";}
         String clientId = request.getParameter("clientId");
        if (null == clientId) {clientId = (String) request.getAttribute("clientId");}
        if (null == clientId) {clientId = "0";}
         String id = request.getParameter("id");
        if (null == id) {id = (String) request.getAttribute("id");}
        if (null == id) {id = "0";}
        if(Integer.parseInt(id) > 0){
            reqmt = CommService.getInstance().getRequirement(Integer.parseInt(id));
            projectId = ""+reqmt.getProjectId();
            tab = ""+reqmt.getTab();
            type = ""+reqmt.getType();
            clientId = ""+reqmt.getClientId();
            
        }
         request.setAttribute("requirement", reqmt);
        request.setAttribute("tab", tab);
        request.setAttribute("type", type);
        request.setAttribute("projectId", projectId);
        request.setAttribute("clientId", clientId);
	// Forward control to the specified success URI
        if(null != reqmt){
            if (null != isReqComp) {
                String reqComp = StandardCode.getInstance().noNull(request.getParameter("reqComp"));

                if (reqComp.equalsIgnoreCase("on")) {
                   User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
                   reqmt.setUserId(u.getUserId());
                   reqmt.setCompDt(new Date());
                } else {
                    reqmt.setUserId(0);
                   reqmt.setCompDt(null);
                }
                AdminService.getInstance().addObject(reqmt);
                return (mapping.findForward("Update"));
            }
        }
         
       
	return (mapping.findForward("Success"));
    }

}
