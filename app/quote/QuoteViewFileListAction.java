//QuoteViewFileListAction.java gets the
//file list and places it into a form

package app.quote;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.*;
import java.util.*;
import app.standardCode.*;
import app.security.*;


public final class QuoteViewFileListAction extends Action {


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
	
        //START get id of current quote from either request, attribute, or cookie 
        //id of quote from request
	String quoteId = null;
	quoteId = request.getParameter("quoteViewId");
        
        //check attribute in request
        if(quoteId == null) {
            quoteId = (String) request.getAttribute("quoteViewId");
        }
        
        //id of quote from cookie
        if(quoteId == null) {            
            quoteId = StandardCode.getInstance().getCookie("quoteViewId", request.getCookies());
        }
        
        
        Integer id = Integer.valueOf(quoteId);
        
        //END get id of current quote from either request, attribute, or cookie               
        
        //get quote
        Quote1 q = QuoteService.getInstance().getSingleQuote(id); 
          
        //get quote file list
        Set files = q.getFiles();
        
        //array for display in jsp
        File[] filesArray = (File[]) files.toArray(new File[0]);
        
        //place all files for this quote into the form for display
        DynaValidatorForm qvf = (DynaValidatorForm) form;
        qvf.set("quoteFiles", filesArray);        
        
        //place quote into attribute for display
        request.setAttribute("quote", q);
        
        //add this quote id to cookies; this will remember the last quote
        response.addCookie(StandardCode.getInstance().setCookie("quoteViewId", quoteId));
        
        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("quoteViewTab", "File List"));
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}