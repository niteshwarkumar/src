//ClientViewContactsAction.java gets the current client from the
//db and places it in an attribute for display. It updates its tab location
//into a cookie.

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


public final class ClientViewContactsAction extends Action {


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

        //get current client ID

        //id from request attribute

       String clientId = request.getParameter("clientViewId");

        if(clientId == null || clientId.equals("")) {
            clientId =  (String) request.getAttribute("clientViewId");
        }


        //id from cookie
        if(clientId == null  || clientId.equals("")) {
            clientId = StandardCode.getInstance().getCookie("clientViewId", request.getCookies());
        }




   //  Integer id = Integer.valueOf(clientId);

        //get the client from db
      Client c=null;
        if (clientId!=null && ! clientId.equals(""))
       
        c = ClientService.getInstance().getSingleClient(Integer.parseInt(clientId));
             else
        {
        c = new Client();
        c.setClientId(0);
        }
      //Client  c = ClientService.getInstance().getSingleClient(Integer.parseInt(clientId));

        //place the client into an attribute for displaying in jsp
        request.setAttribute("client", c);
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");

        //add this client id to cookies; this will remember the last client
        response.addCookie(StandardCode.getInstance().setCookie("clientViewId", clientId));

        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("clientViewTab", "Contacts"));

	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
