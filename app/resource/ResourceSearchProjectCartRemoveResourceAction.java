//ResourceSearchProjectCartRemoveResourceAction.java removes
//one resource from a project cart

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


public final class ResourceSearchProjectCartRemoveResourceAction extends Action {


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
        
        //id of resource to remove from projectCart
        String resourceRemoveId = request.getParameter("resourceRemoveId");
        String projectCartId = request.getParameter("projectCartId");
        
        //get this projectCart
        ProjectCart pc = UserService.getInstance().getSingleProjectCart(Integer.valueOf(projectCartId));
        
        //get string of resource ids in this cart
        String[] ids = pc.getResourceIds().split(",");
        
        //remove the resource from the list
        /////////////////Resource[] projectCartResources = new Resource[ids.length - 1];
        for(int i = 0; i < ids.length; i++) {
            if(ids[i].equals(resourceRemoveId)) { //flag as removed
                ids[i] = "-1";
            }
        }
        StringBuffer idArray = new StringBuffer("");
        for(int i = 0; i < ids.length; i++) {
            if(!ids[i].equals("-1")) {
                idArray.append(ids[i] + ",");
            }
        }
        pc.setResourceIds(idArray.toString()); 
        
        //update projectCart
        UserService.getInstance().updateProjectCart(pc);
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
