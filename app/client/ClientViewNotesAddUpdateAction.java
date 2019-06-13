/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.client;

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
 * @author abhisheksingh
 */
public class ClientViewNotesAddUpdateAction  extends Action {

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

        //START get id of current quote from either request, attribute, or cookie 
                //id from request attribute
        String clientId = (String) request.getAttribute("clientViewId");
        
        //id from cookie
        if(clientId == null) {
            clientId = StandardCode.getInstance().getCookie("clientViewId", request.getCookies());	
        }
        
        Integer id = Integer.valueOf(clientId);
        
        //get the client from db
        Client c = ClientService.getInstance().getSingleClient(id);  

       String tab =  request.getParameter("tab");

        String noteId = request.getParameter("noteId");
        String deleteNote = request.getParameter("deleteNote");
        if (null != deleteNote) {
            if ("yes".equalsIgnoreCase(deleteNote)) {
                ClientNotes notes = ClientService.getInstance().getSingleNotes(Integer.parseInt(noteId));
                ClientService.getInstance().deleteNotes(notes);
                return (mapping.findForward("Success"));
            }
        }

        if (null == noteId) {
            noteId = "0";
        }
        String bgcolor = request.getParameter("bgcolor");
        ClientNotes notes = new ClientNotes();
        if (Integer.parseInt(noteId) > 0) {
            notes = ClientService.getInstance().getSingleNotes(Integer.parseInt(noteId));
            if (!bgcolor.equalsIgnoreCase("")) {
                notes.setBgcolor(bgcolor);
            }
        } else {
            notes.setCreateDate(new Date(System.currentTimeMillis()));
            notes.setBgcolor(bgcolor);

        }

        //updated values of the quote
        // DynaValidatorForm qvn = (DynaValidatorForm) form;
        //String note = (String) qvn.get("note");
        //the new note data from the form
        String note = request.getParameter("note");
        //update the new values
        notes.setNotes(StandardCode.getInstance().normalizeText(note));

        notes.setAuthor(u.getFirstName() + " " + u.getLastName());

        notes.setEditDate(new Date(System.currentTimeMillis()));
        notes.setId_Client(id);
        
        notes.setTab(tab);

        notes.setUserid(u.getUserId());
//                                            
        ClientService.getInstance().addUpdateNotes(notes);
        //place quote into attribute for display

        request.setAttribute("tab", tab);
        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }

}
