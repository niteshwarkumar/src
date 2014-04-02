//ResourceSearchProjectCartManage1Action.java gets a 
//single project cart for display

package app.resource;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleException;
import org.apache.struts.util.MessageResources;
import org.apache.commons.beanutils.PropertyUtils;
import java.util.*;
import java.text.*;
import app.user.*;
import app.resource.*;
import app.db.*;
import app.workspace.*;
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;


public final class ResourceSearchProjectCartManage1Action extends Action {


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
        
        //id of project cart to view
        String projectCartId = request.getParameter("projectCartId");
        
        //get this projectCart
        ProjectCart pc = UserService.getInstance().getSingleProjectCart(Integer.valueOf(projectCartId));
        
        //get string of resource ids in this cart
        String[] ids = pc.getResourceIds().split(",");
        
        //get all of the resources from db for this cart
        Resource[] projectCartResources = new Resource[ids.length];
        try {  //are there resources in the cart
            for(int i = 0; i < ids.length; i++) {
                projectCartResources[i] = ResourceService.getInstance().getSingleResource(Integer.valueOf(ids[i]));
            }
        }
        catch(Exception e) { //no resources in cart
            projectCartResources = null;
        }
        
        //place projectCart in request for display
        request.setAttribute("projectCart", pc);
                       
        //place resources in request for display
        request.setAttribute("projectCartResources", projectCartResources);
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
