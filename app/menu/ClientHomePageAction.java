/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.menu;

import app.client.Client;
import app.client.ClientService;
import app.security.SecurityService;
import app.user.User;
import app.user.UserService;
import java.util.List;
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
 * @author abc
 */
public class ClientHomePageAction  extends Action {


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
	
        //get the current user for displaying personal info, such as "My Projects"
        User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
        //Client c = ClientService.getInstance().getClient(u.getID_Client());
   
        
        //get announcements
        List announcements = MenuService.getInstance().getAnnouncementList(u.getID_Client());        
        //place announcements into request for display
        request.setAttribute("announcements", announcements);
        long end = System.currentTimeMillis();
        
   
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}


