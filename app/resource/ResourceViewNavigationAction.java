//ResourceViewNavigationAction.java processes the navigation buttons
//(the left and right arrow) within the resource view menu.  It gets the
//location values from cookies.  It then determines which resource to display
//next in id order.  The list can be from the full db list, or it can be
//from the search results list

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
import app.resource.*;
import app.client.*;
import app.db.*;
import app.workspace.*;
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;


public final class ResourceViewNavigationAction extends Action {


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
        
        //id of current resource
        String resourceId = StandardCode.getInstance().getCookie("resourceViewId", request.getCookies());
        Integer currentId = Integer.valueOf(resourceId);
                        
        //get where to move: <code>previous, stay, next</code>
        //where to move from request
	String move = request.getParameter("resourceViewMove");   
        
        //for display in jsp of record-view location; e.g., record 2 of 400
        int current = 0;
        String resourceRecordCurrent;
        String resourceRecordTotal;
        
        //get current resource being viewed from db
        Resource currentR = ResourceService.getInstance().getSingleResourceNoLazy(currentId);
        Resource newR = null; //save next new move
            
        //get what list to scroll over
        Integer[] resourceSearchResultIds = (Integer[]) request.getSession(false).getAttribute("resourceSearchResultIds");
        List resources = new ArrayList(); //the list to scroll over
	if(resourceSearchResultIds == null || resourceSearchResultIds.length == 0) { //no search results present        
            //all current resources in db in number order
            resources = ResourceService.getInstance().getResourceList();
        }
        else { //get all search result objects
            for(int i = 0; i < resourceSearchResultIds.length; i++) {
                Resource r = ResourceService.getInstance().getSingleResourceNoLazy(resourceSearchResultIds[i]);
                resources.add(r);
            }            
        }
        
        //set the total now
        resourceRecordTotal = String.valueOf(new Integer(resources.size()));
        
        //determine which resource to display next
        if(move.equals("previous")) {
              for(ListIterator iter = resources.listIterator(); iter.hasNext(); ) {
                  newR = (Resource) iter.next();
                  current++; //advance current record count
                  if(newR.getResourceId().equals(currentR.getResourceId())) { //get previous before current
                      iter.previous();
                      if(iter.hasPrevious()) { //if this is not first
                        newR = (Resource) iter.previous();
                        current--; //move back current record count
                      }
                      else { //it is the first; load last
                          ListIterator iterScroll = null;
                          for(iterScroll = resources.listIterator(); iterScroll.hasNext(); iterScroll.next()) {}
                          iterScroll.previous();
                          newR = (Resource) iterScroll.next(); 
                          current = resources.size(); //set to last record
                      }                          
                      break; //stop looking for previous
                  }
              }
        }
        else if(move.equals("next")) {
            for(ListIterator iter = resources.listIterator(); iter.hasNext(); ) {
                  newR = (Resource) iter.next();
                  current++; //advance current record count
                  if(newR.getResourceId().equals(currentR.getResourceId())) { //get next after current
                      if(iter.hasNext()) { //if this is not the last
                        newR = (Resource) iter.next();
                        current++; //advance current record count
                      }
                      else { //it is the last; load first
                         ListIterator iterScroll = resources.listIterator();
                         newR = (Resource) iterScroll.next();
                         current = 1; //set to first record
                      }
                      break; //stop looking for next
                  }
              }
        }
        //load first record
        else if(move.equals("first")) {
            ListIterator iterScroll = resources.listIterator();
            newR = (Resource) iterScroll.next();
            current = 1; //set to first record           
        }  
        //load last record
        else if(move.equals("last")) {
            ListIterator iterScroll = null;
            for(iterScroll = resources.listIterator(); iterScroll.hasNext(); iterScroll.next()) {}
            iterScroll.previous();
            newR = (Resource) iterScroll.next(); 
            current = resources.size(); //set to last record            
        }
        
        //set the current record now
        resourceRecordCurrent = String.valueOf(new Integer(current));                
        
        //add this resource id to request; this will remember which resource is
        //to be viewed after the move
        request.setAttribute("resourceViewId", String.valueOf(newR.getResourceId()));
        
        //set current record and total records into session and cookie
        request.getSession(false).setAttribute("resourceRecordCurrent", resourceRecordCurrent);
        request.getSession(false).setAttribute("resourceRecordTotal", resourceRecordTotal);
        response.addCookie(StandardCode.getInstance().setCookie("resourceRecordCurrent", String.valueOf(new Integer(resourceRecordCurrent))));
        response.addCookie(StandardCode.getInstance().setCookie("resourceRecordTotal", String.valueOf(new Integer(resourceRecordTotal))));

        //determine where to forward based on tab value
        String resourceViewTab = StandardCode.getInstance().getCookie("resourceViewTab", request.getCookies());
        
	if(resourceViewTab.equals("General Info")) {
            return (mapping.findForward("General Info"));
        }
        else if(resourceViewTab.equals("Agency")) {
            return (mapping.findForward("Agency"));
        }
        else if(resourceViewTab.equals("Languages")) {
            return (mapping.findForward("Languages"));
        }
        else if(resourceViewTab.equals("Specialties")) {
            return (mapping.findForward("Specialties"));
        }
        else if(resourceViewTab.equals("RatesScores")) {
            return (mapping.findForward("RatesScores"));
        }
        else if(resourceViewTab.equals("Tools")) {
            return (mapping.findForward("Tools"));
        }
        else if(resourceViewTab.equals("Resume")) {
            return (mapping.findForward("Resume"));
        }
        else if(resourceViewTab.equals("Notes")) {
            return (mapping.findForward("Notes"));
        }
        else if(resourceViewTab.equals("Forms")) {
            return (mapping.findForward("Forms"));
        }
        else if(resourceViewTab.equals("Projects")) {
            return (mapping.findForward("Projects"));
        }
        else if(resourceViewTab.equals("Account")) {
            return (mapping.findForward("Account"));
        }
        
        //default location
        return (mapping.findForward("General Info"));
	
    }

}
