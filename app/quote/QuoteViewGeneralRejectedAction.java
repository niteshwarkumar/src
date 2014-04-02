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


public final class QuoteViewGeneralRejectedAction extends Action {


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
	quoteId = request.getParameter("id");
        System.out.println("VVVVVVVVVVVVVVVVVV "+ quoteId);
        System.out.println("IDIDIDIDDIDIDI "+  request.getParameter("id"));
        String rejectReason = request.getParameter("rejectReason");
        
        //check attribute in request
        if(quoteId == null) {
            quoteId = (String) request.getAttribute("quoteViewId");
        }
        if(quoteId == null){
            quoteId=(String)request.getParameter("quoteViewId");
            System.out.println("quoteId"+quoteId);
        }

        
        //id of quote from cookie
        if(quoteId == null) {            
            quoteId = StandardCode.getInstance().getCookie("quoteViewId", request.getCookies());
        }
        
        System.out.println(" FFFFFFFFFFFF "+ quoteId);
        Integer id = Integer.valueOf(quoteId);
        request.getSession(true).setAttribute("RejectedQuote", quoteId);
        //END get id of current quote from either request, attribute, or cookie               
        
        //get quote
        Quote1 q = QuoteService.getInstance().getSingleQuote(id);
        try {
            QuotePriority qp=QuoteService.getInstance().getSingleQuotePriority(id);
        qp.setStatus(0);
        QuoteService.getInstance().addQuotePriority(qp);
        } catch (Exception e) {
        }

                           
        //update quote as rejected
        q.setStatus("rejected");
        System.out.println("q.setStatus(rejected)."+q.getNumber()+"       "+q.getQuote1Id()+"         " +q.getStatus());
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
