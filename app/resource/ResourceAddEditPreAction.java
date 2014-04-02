//ResourceAddEditPreAction.java creates a new Resource object
//(team) and places it into the form. It is not uploaded
//to db yet

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
import app.resource.*;
import app.security.*;
import org.apache.struts.validator.*;


public final class ResourceAddEditPreAction extends Action {


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
        
        //add resource info for editing/adding
        DynaValidatorForm ra = (DynaValidatorForm) form;
                
        //place new resource into form
        Resource r = new Resource();
        r.setScaleDefault(true); //default to "default linguistic rate scaling"
        ra.set("resourceNew", r);
        
        String type = request.getParameter("type");
        
        if(type.equals("agency")) {
            return (mapping.findForward("Agency"));
        }
        else if(type.equals("freelance")) {
            return (mapping.findForward("Freelance"));
        }
        
        // default forward
	return (mapping.findForward("Success"));
    }

}
