//ResourceSearchProjectCartSearchExisting1Action.java adds
//the resources to an existing project cart

package app.resource;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;

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


public final class ResourceSearchProjectCartSearchExisting1Action extends Action {


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
        
        //to check if adding from the view menu, or from search
        String resourceViewIdAddToCart = (String) request.getSession(false).getAttribute("resourceViewIdAddToCart");
        
        //the projectCart id to add resources to
        String projectCartId = request.getParameter("projectCartId");
        
        //load resource info for searching
        DynaValidatorForm rs = (DynaValidatorForm) form;  
        
        //get this projectCart
        ProjectCart pc = UserService.getInstance().getSingleProjectCart(Integer.valueOf(projectCartId));
         
        //get existing resource list from project cart
        String resourceIds = pc.getResourceIds();
        
        //add resource(s) to the existing cart
        if(!resourceViewIdAddToCart.equals("-1")) { //add from view
            resourceIds = resourceIds + resourceViewIdAddToCart + ",";
            pc.setResourceIds(resourceIds);            
        }
        else { //add from search
            String[] ids = (String[]) rs.get("projectCartIds");
            StringBuffer idArray = new StringBuffer("");
            for(int j = 0; j < ids.length; j++) {
                idArray.append(ids[j] + ",");
            }
            resourceIds = resourceIds + idArray.toString();
            pc.setResourceIds(resourceIds); 
        }
        
        //upload updated projectCart to db
        UserService.getInstance().updateProjectCart(pc);
                       
	//forward to proper location
        if(resourceViewIdAddToCart.equals("-1")) { //came from search
            return (mapping.findForward("Search"));
        }
        else { //came from view
            return (mapping.findForward("View"));
        }
    }

}
