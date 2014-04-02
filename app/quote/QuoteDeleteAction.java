//QuoteViewGeneralRejectedAction.java marks the quote as rejected

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
import java.util.*;
import app.standardCode.*;
import app.security.*;


public final class QuoteDeleteAction extends Action {


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
    @Override
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
        //String rejectReason = request.getParameter("rejectReason");
        
        //check attribute in request
        if(quoteId == null) {
            quoteId = (String) request.getAttribute("quoteViewId");
        }
        
        //id of quote from cookie
        if(quoteId == null) {            
            quoteId = StandardCode.getInstance().getCookie("quoteViewId", request.getCookies());
        }
        Integer id;
        try{
        id = Integer.valueOf(quoteId);
        }catch(Exception e){
            id=Integer.valueOf(request.getParameter("id"));
        }
        
        
        //END get id of current quote from either request, attribute, or cookie               
        
        //get quote
        Quote1 q = QuoteService.getInstance().getSingleQuote(id); 
                           
        //update quote as rejected
        q.setStatus("deleted");
        q.setApprovalDate(new Date());
        //q.setRejectReason(rejectReason);
       
         if(request.getSession(false).getAttribute("username")!=null){
             q.setLastModifiedById((String)request.getSession(false).getAttribute("username")); 
             q.setLastModifiedByTS(new Date());
        }

        QuoteService.getInstance().updateQuote(q);
        request.setAttribute("quote", q);
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
