//TodoDeleteAction.java deletes one item
//from a user's todo list.
//It also checks to make sure user's delete
//only their items

package app.menu;

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
import org.apache.struts.validator.*;
import java.util.*;
import java.text.*;
import app.user.*;
import app.db.*;
import app.workspace.*;
import app.security.*;
import app.standardCode.*;

public final class TodoDeleteAction extends Action {


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
	
        //get the current user to make sure this user only deletes his or her own items
        User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
        
        //get the todo item to delete
        String todoId = request.getParameter("id");
        Todo t = MenuService.getInstance().getSingleTodo(Integer.valueOf(todoId));
        
        //make sure the user in the session is the owner of this todo list item
        boolean owner = false;
        for(Iterator iter = u.getTodos().iterator(); iter.hasNext();) {
            Todo tUser = (Todo) iter.next();
            if(tUser.getTodoId().equals(t.getTodoId())) {
                owner = true;
                break;
            }
        }
        
        //this user is trying to delete someone else's todo list items
        if(!owner) {
            return (mapping.findForward("accessDenied"));
        }
        
        //delete this todo list item
        MenuService.getInstance().deleteTodo(t);
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
