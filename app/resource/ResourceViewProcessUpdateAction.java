//ResourceViewResumeUpdateAction.java gets updated team info
//from a form.  It then uploads the new values for
//this resource to the db related to notes

package app.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.*;
import java.util.*;
import app.security.*;


public final class ResourceViewProcessUpdateAction extends Action {


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
                      
        //values for update from form; change what is stored in db to these values
        DynaValidatorForm rvt = (DynaValidatorForm) form;
        
        Resource r = (Resource) rvt.get("resourceFromForm");
        if(request.getSession(false).getAttribute("username")!=null){
                r.setLastModifiedById((String)request.getSession(false).getAttribute("username")); 
                r.setLastModifiedByTS(new Date());
        }   
        
        r.setQualityProcess(request.getParameter("hiddennote"));
        //set updated values to db
        ResourceService.getInstance().updateResource(r);
        
        //place resource id into request for display
        request.setAttribute("resource", r);
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
