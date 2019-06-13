//HrViewNavigationAction.java processes the navigation buttons
//(the left and right arrow) within the hr view menu.  It gets the
//location values from cookies.  It then determines which employee to display
//next in abc order.

package app.hr;

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
import app.user.*;
import app.client.*;
import app.db.*;
import app.workspace.*;
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;


public final class HrViewNavigationAction extends Action {


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
        
        //id of current user
        String userId = StandardCode.getInstance().getCookie("userViewId", request.getCookies());
        Integer currentId = Integer.valueOf(userId);
                        
        //get where to move: <code>previous, stay, next</code>
        //where to move from request
	String move = request.getParameter("userViewMove");   
      
        //get current user being viewed from db
        User currentU = UserService.getInstance().getSingleUser(currentId);
        User newU = null; //save next new move
                
        //all current (employees) users in db in alphabetical order
        List users = UserService.getInstance().getUserListCurrent();
        
        //determine which user to display next
        if(move.equals("previous")) {
              for(ListIterator iter = users.listIterator(); iter.hasNext(); ) {
                  newU = (User) iter.next();
                  if(newU.getLastName().equalsIgnoreCase(currentU.getLastName()) && newU.getFirstName().equals(currentU.getFirstName())) { //get previous before current
                      iter.previous();
                      if(iter.hasPrevious()) { //if this is not first
                        newU = (User) iter.previous();
                      }
                      else { //it is the first; load last
                          ListIterator iterScroll = null;
                          for(iterScroll = users.listIterator(); iterScroll.hasNext(); iterScroll.next()) {}
                          iterScroll.previous();
                          newU = (User) iterScroll.next();                
                      }                          
                      break; //stop looking for previous
                  }
              }
        }
        else if(move.equals("next")) {
            for(ListIterator iter = users.listIterator(); iter.hasNext(); ) {
                  newU = (User) iter.next();
                  if(newU.getLastName().equalsIgnoreCase(currentU.getLastName()) && newU.getFirstName().equals(currentU.getFirstName())) { //get next after current
                      if(iter.hasNext()) { //if this is not the last
                        newU = (User) iter.next();
                      }
                      else { //it is the last; load first
                         ListIterator iterScroll = users.listIterator();
                         newU = (User) iterScroll.next();
                      }
                      break; //stop looking for next
                  }
              }
        }
        
                
        
        //add this user id to request; this will remember which user is
        //to be viewed after the move
        request.setAttribute("userViewId", String.valueOf(newU.getUserId()));
        
        //determine where to forward based on tab value
        String userViewTab = StandardCode.getInstance().getCookie("userViewTab", request.getCookies());
        
	if(userViewTab.equals("General Info")) {
            return (mapping.findForward("General Info"));
        }
        else if(userViewTab.equals("Private Info")) {
            return (mapping.findForward("Private Info"));
        }
        else if(userViewTab.equals("Notes")) {
            return (mapping.findForward("Notes"));
        }
        else if(userViewTab.equals("Project History")) {
            return (mapping.findForward("Project History"));
        }
        else if(userViewTab.equals("Quote History")) {
            return (mapping.findForward("Quote History"));
        }
        
        //default location
        return (mapping.findForward("General Info"));
	
    }

}
