/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.comm;

import app.client.Client;
import app.client.ClientNotes;
import app.client.ClientService;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
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
public class RequirementAddUpdateAction   extends Action {

    // ----------------------------------------------------- Instance Variables
    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log
            = LogFactory.getLog("org.apache.struts.webapp.Example");

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
     * @return 
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

        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));

        //START check for login (security)
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)
        String projectAll = request.getParameter("projectAll");
        if(null == projectAll) { projectAll = (String) request.getAttribute("projectAll");}
        if(null == projectAll) { projectAll = "N";}
        request.setAttribute("projectAll", projectAll);


        String tab =  request.getParameter("tab");
        String id = request.getParameter("id");
        String type = request.getParameter("type");
        String clientId = request.getParameter("clientId");
        String projectId = request.getParameter("projectId");
        String userId = request.getParameter("userId");
        String note = request.getParameter("note");
        if (null == id) {id = "0";}
        if (null == clientId) {clientId = "0";}
        if (null == projectId) {projectId = "0";}
        if (null == userId) {userId = "0";}
        if(type.equalsIgnoreCase("A")){
            if(!projectId.equalsIgnoreCase("0")) type="P";
            else if(!clientId.equalsIgnoreCase("0")) type="C";
            else type="G";
        }

        
        Requirement requirement = new Requirement();
        String deleteNote = request.getParameter("deleteNote");
        if (null != deleteNote) {
            if ("yes".equalsIgnoreCase(deleteNote)) {
                requirement = CommService.getInstance().getRequirement(Integer.parseInt(id));
                CommService.getInstance().deleteRequirement(requirement);
                return (mapping.findForward("Success"));
            }
        }

       
       
        
        if(Integer.parseInt(id)>0){
            requirement = CommService.getInstance().getRequirement(Integer.parseInt(id));
            requirement.setRequirement(note);
            CommService.getInstance().updateRequirement(requirement);
            
        }else{
            requirement=new Requirement();
            requirement.setUserId(Integer.parseInt(userId));
            requirement.setProjectId(Integer.parseInt(projectId));
            requirement.setClientId(Integer.parseInt(clientId));
            requirement.setTab(tab);
            requirement.setType(type);

            requirement.setRequirement(note);
            CommService.getInstance().addRequirement(requirement);
        
        }
        
       
        
        


        request.setAttribute("tab", tab);
        request.setAttribute("type", type);
        request.setAttribute("projectId", projectId);
        request.setAttribute("clientId", clientId);
        
        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }

}
