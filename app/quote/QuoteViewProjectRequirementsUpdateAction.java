//QuoteViewProjectRequirementsUpdateAction.java gets the
//updated project requirements and loads them to db

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
import app.user.*;
import app.standardCode.*;
import app.security.*;


public final class QuoteViewProjectRequirementsUpdateAction extends Action {


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
	User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
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

        String cqID=request.getSession(false).getAttribute("ClientQuoteId").toString();
        Integer id = Integer.valueOf(cqID);
//        Client c=ClientService.getInstance().getSingleClient(u.getId_client());
        Client_Quote cq=QuoteService.getInstance().getSingleClient_Quote(id);
        Quote1 q = QuoteService.getInstance().getSingleQuote(cq.getQuote_ID());
//        List quoteList=QuoteService.getInstance().getSingleClientQuote(id);
         //cq1=QuoteService.getInstance().get_SingleClientQuote(id);
         //System.out.println("Client Quoteeee Iddddddddddddddddddddddddd"+cq.getId());

        //updated values of the project
        DynaValidatorForm qvpr = (DynaValidatorForm) form;
//        String product = (String) (qvpr.get("product"));
//        //System.out.println("prooooooooooooooooooooooooooooooood"+product);
        String projectRequirements = (String) qvpr.get("projectRequirements");
        String projectDescription = (String) qvpr.get("projectDescription");
        
//        String beforeWorkTurn = (String) (qvpr.get("beforeWorkTurn"));
//        String beforeWorkTurnUnits = (String) (qvpr.get("beforeWorkTurnUnits"));
//        String afterWorkTurn = (String) (qvpr.get("afterWorkTurn"));
//        String afterWorkTurnUnits = (String) (qvpr.get("afterWorkTurnUnits"));
//        String afterWorkTurnReason = (String) (qvpr.get("afterWorkTurnReason"));
        //update the new values
//        Project p = q.getProject();
    //    p.setProjectRequirements(projectRequirements);
        cq.setRequirement(projectRequirements);
        cq.setInstruction(projectDescription);
     //   p.setProjectDescription(projectDescription);
//             p.setBeforeWorkTurn(beforeWorkTurn);
//        p.setBeforeWorkTurnUnits(beforeWorkTurnUnits);
//        p.setAfterWorkTurn(afterWorkTurn);
//        p.setAfterWorkTurnUnits(afterWorkTurnUnits);
//        p.setAfterWorkTurnReason(afterWorkTurnReason);
        //update to db
//        ProjectService.getInstance().updateProject(p);
        QuoteService.getInstance().saveClientQuote(cq);



        //place quote into attribute for display
        request.setAttribute("quote", q);


	// Forward control to the specified success URI


          // if(u.getuserType() != null && u.getuserType().equalsIgnoreCase("client")){
        return (mapping.findForward("Success"));//}
        //   else
         //      return (mapping.findForward("Success"));

    }

}
