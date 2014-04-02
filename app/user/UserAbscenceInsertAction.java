//ClientViewGeneralAction.java gets the current client from the db
//It then places that client into the client form for
//display in the jsp page. It then places location values into
//cookies

package app.user;

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

 
public final class UserAbscenceInsertAction extends Action {


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
       
        
        UserAbscence uab = new UserAbscence();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
        uab.setAbscence_date(sdf.parse(request.getParameter("abscence_date")));
        
        if(request.getParameter("id_user")!=null && !request.getParameter("id_user").equals(""))
            uab.setID_User(new Integer(request.getParameter("id_user")));
        
        uab.setNotes(request.getParameter("notes"));
        uab.setReason(request.getParameter("reason"));
        uab.setTimeOut(new Double(request.getParameter("timeOut")));
        if(request.getParameter("id_location")!=null && !request.getParameter("id_location").equals(""))
            uab.setID_Location(new Integer(request.getParameter("id_location")));
        UserService.getInstance().updateUserAbscence(uab);
        request.getSession(false).setAttribute("updateStatus", "Y");
                       
                       
                       
        
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}

