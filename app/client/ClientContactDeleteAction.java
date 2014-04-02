//ClientContactDeleteAction.java deletes
//one change1 object for this project

package app.client;

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
import app.user.User;
import app.user.UserService;


public final class ClientContactDeleteAction extends Action {


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

        User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
         if(u.getuserType() != null && u.getuserType().equalsIgnoreCase("client")){
        return (mapping.findForward("accessDeniedClient"));}


        //PRIVS check that correct user is viewing this page 
        if(!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "viewAccountTab")) {
            return (mapping.findForward("accessDenied"));
        }//END PRIVS check that correct user is viewing this page
         
        //PRIVS check that correct user is viewing this page 
        if(!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "updateAccountTab")) {
            return (mapping.findForward("accessDenied"));
        }//END PRIVS check that correct user is viewing this page
        
                
        //get quality to delete
        String contactId = request.getParameter("clientContactId");
        ClientContact cc = ClientService.getInstance().getSingleClientContact(contactId);
        
        //delete this quality
        ClientService.getInstance().deleteClientContact(cc);
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
