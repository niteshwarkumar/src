package app.quote;

import app.project.*;
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


public final class QuoteViewGeneralDeleteAction extends Action {


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
        //System.out.println("VVVVVVVVVVVVVVVVVV "+ quoteId);
     //   //System.out.println("IDIDIDIDDIDIDI "+  request.getParameter("id"));
       // String rejectReason = request.getParameter("rejectReason");

        //check attribute in request
        if(quoteId == null) {
            quoteId = (String) request.getAttribute("quoteViewId");
        }

        //id of quote from cookie
        if(quoteId == null) {
            quoteId = StandardCode.getInstance().getCookie("quoteViewId", request.getCookies());
        }

        //System.out.println(" FFFFFFFFFFFF "+ quoteId);
        Integer id = Integer.valueOf(quoteId);
      //  request.getSession(true).setAttribute("RejectedQuote", quoteId);
        //END get id of current quote from either request, attribute, or cookie

        //get quote
        Quote1 q = QuoteService.getInstance().getSingleQuote(id);
        Client_Quote cq=QuoteService.getInstance().get_SingleClientQuote(id);
        Project p=ProjectService.getInstance().getSingleProject(q.getProject().getProjectId());
        //Project p=ProjectService.getInstance().getSingleProject(id)
         QuoteService.getInstance().deleteClient_Quote(cq);
        QuoteService.getInstance().deleteQuote(q);
       

        //QuoteService.getInstance().deleteClient_Quote(cq);
       // ProjectService.getInstance().deleteProject(null);


        //update quote as rejected
       // q.setStatus("rejected");
       // //System.out.println("q.setStatus(rejected)."+q.getNumber()+"       "+q.getQuote1Id()+"         " +q.getStatus());
        //q.setApprovalDate(new Date());
        //q.setRejectReason(rejectReason);

        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
