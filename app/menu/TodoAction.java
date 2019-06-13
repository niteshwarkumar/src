//TodoAction.java prepares the display for the user's todo list

package app.menu;

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
import org.apache.struts.validator.*;
import java.util.*;
import java.text.*;
import app.user.*;
import app.db.*;
import app.workspace.*;
import app.security.*;
import app.standardCode.*;

public final class TodoAction extends Action {


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
	
        //get the current user for the todo list
        User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
        
        //get todo list
        Set todos = u.getTodos();
        //todo array for display
        Todo[] todosArray = (Todo[]) todos.toArray(new Todo[0]);
        
        //START set up todo dates
        for(int i = 0; i < todos.size(); i++) {
            if(todosArray[i].getDateAdded() != null) {
                request.setAttribute("todoAddedArray" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(todosArray[i].getDateAdded()));
            }
            else {
                request.setAttribute("todoAddedArray" + String.valueOf(i), "");
            }
        }
        for(int i = 0; i < todos.size(); i++) {
            if(todosArray[i].getDateDue() != null) {
                request.setAttribute("todoDueArray" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(todosArray[i].getDateDue()));
            }
            else {
                request.setAttribute("todoDueArray" + String.valueOf(i), "");
            }
        }
        for(int i = 0; i < todos.size(); i++) {
            if(todosArray[i].getDateCompleted() != null) {
                request.setAttribute("todoCompletedArray" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(todosArray[i].getDateCompleted()));
            }
            else {
                request.setAttribute("todoCompletedArray" + String.valueOf(i), "");
            }
        }        
        //END set up todo dates
        
        //add list to form for display
        DynaValidatorForm tf = (DynaValidatorForm) form;
        tf.set("todos", todosArray);
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
