//ClientViewNavigationAction.java processes the navigation buttons
//(the left and right arrow) within the client view menu.  It gets the
//location values from cookies.  It then determines which client to display
//next in abc order.  For example, if at first client, aaa, and previous
//button is pushed, go to zzz.  It then determines which page to forward to
//based on which tab is currently being viewed.

package app.client;

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
import app.db.*;
import app.workspace.*;
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;


public final class ClientViewNavigationAction extends Action {

    public ClientViewNavigationAction()
    {
        //System.out.println("ClientViewNavigationAction constructor is calling*****************************************************");
    }

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
        
        //id of current client
        String clientId = StandardCode.getInstance().getCookie("clientViewId", request.getCookies());
        Integer currentId = Integer.valueOf(clientId);
        //System.out.println("currentId value*****************************"+currentId);
                        
        //get where to move: <code>previous, stay, next</code>
        //where to move from request
	String move = request.getParameter("clientViewMove");   
      
        //for display in jsp of record-view location; e.g., record 2 of 400
        int current = 0;
        String clientRecordCurrent;
        String clientRecordTotal;
        
        //get current client being viewed from db
        Client currentC = ClientService.getInstance().getSingleClient(currentId);
        //System.out.println("currentC value*****************************"+currentC);
        Client newC = null; //save next new move
                
        //all clients in db in alphabetical order
        List clients = ClientService.getInstance().getClientList();
        
        //set the total now
        clientRecordTotal = String.valueOf(new Integer(clients.size()));
        //System.out.println("clientRecordTotal*******************"+clientRecordTotal);
        
        //determine which client to display next
        if(move.equals("previous")) {
              for(ListIterator iter = clients.listIterator(); iter.hasNext(); ) {
                  newC = (Client) iter.next();
                  current++; //advance current record count
                  if(newC.getCompany_name().equals(currentC.getCompany_name())) { //get previous before current
                      iter.previous();
                      if(iter.hasPrevious()) { //if this is not first
                        newC = (Client) iter.previous();
                        current--; //move back current record count
                      }
                      else { //it is the first; load last
                          ListIterator iterScroll = null;
                          for(iterScroll = clients.listIterator(); iterScroll.hasNext(); iterScroll.next()) {}
                          iterScroll.previous();
                          newC = (Client) iterScroll.next(); 
                          current = clients.size(); //set to last record
                      }                          
                      break; //stop looking for previous
                  }
              }
        }
        else if(move.equals("next")) {
            for(ListIterator iter = clients.listIterator(); iter.hasNext(); ) {
                  newC = (Client) iter.next();
                  current++; //advance current record count
                  if(newC.getCompany_name().equals(currentC.getCompany_name())) { //get next after current
                      if(iter.hasNext()) { //if this is not the last
                        newC = (Client) iter.next();
                        current++; //advance current record count
                      }
                      else { //it is the last; load first
                         ListIterator iterScroll = clients.listIterator();
                         newC = (Client) iterScroll.next();
                         current = 1; //set to first record
                      }
                      break; //stop looking for next
                  }
              }
        }
        
        //set the current record now
        clientRecordCurrent = String.valueOf(new Integer(current));        
        
        //set current record and total records into session and cookie
        request.getSession(false).setAttribute("clientRecordCurrent", clientRecordCurrent);
        request.getSession(false).setAttribute("clientRecordTotal", clientRecordTotal);
        response.addCookie(StandardCode.getInstance().setCookie("clientRecordCurrent", String.valueOf(new Integer(clientRecordCurrent))));
        response.addCookie(StandardCode.getInstance().setCookie("clientRecordTotal", String.valueOf(new Integer(clientRecordTotal))));
        
        
        //add this client id to request; this will remember which client is
        //to be viewed after the move
        request.setAttribute("clientViewId", String.valueOf(newC.getClientId()));
        
        //determine where to forward based on tab value
        String clientViewTab = StandardCode.getInstance().getCookie("clientViewTab", request.getCookies());
        
	if(clientViewTab.equals("General Info")) {
            return (mapping.findForward("General Info"));
        }
        else if(clientViewTab.equals("Contacts")) {
            return (mapping.findForward("Contacts"));
        }
        else if(clientViewTab.equals("Notes")) {
            return (mapping.findForward("Notes"));
        }
        else if(clientViewTab.equals("Project History")) {
            return (mapping.findForward("Project History"));
        }
        else if(clientViewTab.equals("Quote History")) {
            return (mapping.findForward("Quote History"));
        }
        else if(clientViewTab.equals("Team History")) {
            return (mapping.findForward("Team History"));
        }
        else if(clientViewTab.equals("Accounting")) {
            return (mapping.findForward("Accounting"));
        }
        
        //default location
        return (mapping.findForward("General Info"));
	
    }

}
