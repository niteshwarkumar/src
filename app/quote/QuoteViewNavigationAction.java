//QuoteViewNavigationAction.java processes the navigation buttons
//(the left and right arrow) within the quote view menu.  It gets the
//location values from cookies.  It then determines which quote to display
//next in quote number order.  For example, if at first quote, Q000000, and previous
//button is pushed, go to Q000015.  It then determines which page to forward to
//based on which tab is currently being viewed.

package app.quote;

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
import app.client.*;
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;


public final class QuoteViewNavigationAction extends Action {


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
        
        //id of current quote
        String quoteId = StandardCode.getInstance().getCookie("quoteViewId", request.getCookies());
        Integer currentId = Integer.valueOf(quoteId);
                        
        //get where to move: <code>previous, stay, next</code>
        //where to move from request
	String move = request.getParameter("quoteViewMove");   
      
        //get current project being viewed from db
        Quote1 currentQ = QuoteService.getInstance().getSingleQuote(currentId);
        Quote1 newQ = null; //save next new move
                
        //all clients in db in alphabetical order
        List quotes = QuoteService.getInstance().getQuoteList();
        
        //determine which project to display next
        if(move.equals("previous")) {
              for(ListIterator iter = quotes.listIterator(); iter.hasNext(); ) {
                  newQ = (Quote1) iter.next();
                  if(newQ.getNumber().equals(currentQ.getNumber())) { //get previous before current
                      iter.previous();
                      if(iter.hasPrevious()) { //if this is not first
                        newQ = (Quote1) iter.previous();
                      }
                      else { //it is the first; load last
                          ListIterator iterScroll = null;
                          for(iterScroll = quotes.listIterator(); iterScroll.hasNext(); iterScroll.next()) {}
                          iterScroll.previous();
                          newQ = (Quote1) iterScroll.next();                
                      }                          
                      break; //stop looking for previous
                  }
              }
        }
        else if(move.equals("next")) {
            for(ListIterator iter = quotes.listIterator(); iter.hasNext(); ) {
                  newQ = (Quote1) iter.next();
                  if(newQ.getNumber().equals(currentQ.getNumber())) { //get next after current
                      if(iter.hasNext()) { //if this is not the last
                        newQ = (Quote1) iter.next();
                      }
                      else { //it is the last; load first
                         ListIterator iterScroll = quotes.listIterator();
                         newQ = (Quote1) iterScroll.next();
                      }
                      break; //stop looking for next
                  }
              }
        }
        
                
        
        //add this quote id to request; this will remember which quote is
        //to be viewed after the move
        request.setAttribute("quoteViewId", String.valueOf(newQ.getQuote1Id()));
        
        //determine where to forward based on tab value
        String quoteViewTab = StandardCode.getInstance().getCookie("quoteViewTab", request.getCookies());
        
	if(quoteViewTab.equals("General Info")) {
            return (mapping.findForward("General Info"));
        }
        else if(quoteViewTab.equals("Project Requirements")) {
            return (mapping.findForward("Project Requirements"));
        }
        else if(quoteViewTab.equals("Notes")) {
            return (mapping.findForward("Notes"));
        }
        else if(quoteViewTab.equals("Sub Q")) {
            return (mapping.findForward("Sub Q"));
        }
        else if(quoteViewTab.equals("File List")) {
            return (mapping.findForward("File List"));
        }
        
        //default location
        return (mapping.findForward("Overview"));
	
    }

}
