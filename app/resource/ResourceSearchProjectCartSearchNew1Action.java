//ResourceSearchProjectCartSearchNew1Action.java adds
//the resource(s) to a new project cart

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
import app.user.*;
import app.security.*;
import org.apache.struts.validator.*;


public final class ResourceSearchProjectCartSearchNew1Action extends Action {


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
        
        //load resource info for searching
        DynaValidatorForm rs = (DynaValidatorForm) form;  
        
        //get the cart name
        String description = (String) rs.get("description");
        
        //get this user
        User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
         
        //create new cart
        ProjectCart pc = new ProjectCart();
        pc.setDescription(description);
        
        //add resource(s) to the new cart
        if(!resourceViewIdAddToCart.equals("-1")) { //add from view
            pc.setResourceIds(resourceViewIdAddToCart + ",");            
        }
        else { //add from search
            String[] ids = (String[]) rs.get("projectCartIds");
            StringBuffer idArray = new StringBuffer("");
            for(int j = 0; j < ids.length; j++) {
                idArray.append(ids[j] + ",");
            }
            pc.setResourceIds(idArray.toString());
        }
        
        //upload projectCart to db, building one-to-many relationship between user and projectCart
        Integer projectCartId = UserService.getInstance().addProjectCartWithUser(u, pc);
                       
	//forward to proper location
        if(resourceViewIdAddToCart.equals("-1")) { //came from search
            return (mapping.findForward("Search"));
        }
        else { //came from view
            return (mapping.findForward("View"));
        }
        
    }

}
