//ClientViewQuoteHistory.java gets the current client from the
//db and places it in an attribute for display.  Client is related
//to project by: one client has one or more projects. 
//It then updates its tab location into a cookie.

package app.client;

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
import app.user.*;
import app.db.*;
import app.workspace.*;
import app.security.*;
import app.standardCode.*;
import app.project.*;
import app.quote.*;
import org.apache.struts.validator.*;


public final class ClientViewQuoteHistoryAction extends Action {


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
        
        //get current client ID
        
        //id from request attribute
        String clientId = (String) request.getAttribute("clientViewId");
        
        //id from cookie
        if(clientId == null) {
            clientId = StandardCode.getInstance().getCookie("clientViewId", request.getCookies());	
        }
        
        /*Integer id = Integer.valueOf(clientId);

        //get the client from db
        Client c = ClientService.getInstance().getSingleClient(id);*/


         Client c=null;
        if (clientId!=null && clientId.trim() != "")
        c = ClientService.getInstance().getSingleClient(Integer.parseInt(clientId));
        else
        {
        c = new Client();
        c.setClientId(0);
        }
        
        //START get this client's pending quote total
   /*     double quoteTotalNum = 0;
        
        Set projects = c.getProjects();
        if(projects != null) {
            for(Iterator iter = projects.iterator(); iter.hasNext(); ) {
                Project p = (Project) iter.next();

                //for each quote within project
                Set quotes = p.getQuotes();       

                if(quotes != null && quotes.size() > 0) { //if this project has quotes
                        Quote1 q = QuoteService.getInstance().getLastQuote(quotes);
                        //if this quote has status "pending" then add to quoteTotal
                        if(q.getStatus().equals("pending")) {
                            if(q.getQuoteDollarTotal() != null) {
                                quoteTotalNum += q.getQuoteDollarTotal().doubleValue();
                            }
                        }
                }
            }
        }
        //END get this client's pending quote total        
        
        //START get the source and target count
        Set projects2 = c.getProjects();
        
        if(projects2 != null && projects2.size() > 0) {
            int i = 0; //for display in jsp
            for(Iterator pIter = projects2.iterator(); pIter.hasNext(); i++) {
                int sources = 0;
                int targets = 0;
                Project p = (Project) pIter.next();
                p = ProjectService.getInstance().getSingleProject(p.getProjectId());
                if(p.getQuotes() != null && p.getQuotes().size() > 0) {
                        Quote1 q = QuoteService.getInstance().getLastQuote(p.getQuotes());
                        q = QuoteService.getInstance().getSingleQuote(q.getQuote1Id());
                        for(Iterator sIter = q.getSourceDocs().iterator(); sIter.hasNext();) {
                        sources++; //count this source
                        SourceDoc sd = (SourceDoc) sIter.next();
                        if(sd.getTargetDocs() != null && sd.getTargetDocs().size() > 0) {
                            for(Iterator tIter = sd.getTargetDocs().iterator(); tIter.hasNext();) {
                                TargetDoc td = (TargetDoc) tIter.next();
                                if(!td.getLanguage().equals("All")) {
                                    targets++; //count this target, if not a placeholder for universal tasks
                                }                                
                            }
                        }
                    }                
                request.setAttribute("source" + String.valueOf(i), String.valueOf(sources));
                request.setAttribute("target" + String.valueOf(i), String.valueOf(targets));
                }
            }
        }//END get the source and target count
    **/
        
        //place the client into an attribute for displaying in jsp
        request.setAttribute("client", c);
              
        //place the client's pending quote total into the request for display
       // request.setAttribute("quoteTotal", StandardCode.getInstance().formatDouble(new Double(quoteTotalNum)));
        
        //add this client id to cookies; this will remember the last client
        response.addCookie(StandardCode.getInstance().setCookie("clientViewId", clientId));
        
        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("clientViewTab", "Quote History"));
                
        String quoteYear=request.getParameter("quoteYear");
//        String clientViewId=request.getParameter("clientViewId");
        request.setAttribute("quoteYear", quoteYear);
        
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
