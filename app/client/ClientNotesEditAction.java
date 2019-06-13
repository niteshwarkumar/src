//ClientNotesEditAction.java gets the current client's notes
//and places them in an attribute for display

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
import java.util.*;
import app.security.*;
import app.standardCode.*;

public final class ClientNotesEditAction extends Action {


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
        String clientId = (String) request.getAttribute("clientViewId");
        
        //id from cookie
        if(clientId == null) {
            clientId = StandardCode.getInstance().getCookie("clientViewId", request.getCookies());	
        }
        
        //Integer id = Integer.valueOf(clientId);
        
        
        //get client with id from database
       // Client c = ClientService.getInstance().getSingleClient(id);

        Client c=null;
        if (clientId!=null && clientId.trim() != "")
        c = ClientService.getInstance().getSingleClient(Integer.parseInt(clientId));
        else
        {
        c = new Client();
        c.setClientId(0);
        }
        
              
        String noteId = request.getParameter("noteId");
        String tab = request.getParameter("tab");
        if(null == tab){
        tab = (String) request.getAttribute("tab");
        }
        
        
//        List noteList = ClientService.getInstance().getNotesList(c.getClientId(),tab);
        
        
//        String noteId = request.getParameter("noteId");
        ClientNotes note = null;
 
        
        if(null != noteId)
            note = ClientService.getInstance().getSingleNotes(Integer.parseInt(noteId));
        
        
        //place quote into attribute for display
        
//        request.setAttribute("notes", noteList);
        request.setAttribute("note", note);
        //put this client into the request; in ClientViewNotesEdit.jsp, get <code>note</code> for display
        request.setAttribute("client", c);
        
        //add this client id to cookies; this will remember the last client
        response.addCookie(StandardCode.getInstance().setCookie("clientViewId", clientId));
        
        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("clientViewTab", "Notes"));
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
