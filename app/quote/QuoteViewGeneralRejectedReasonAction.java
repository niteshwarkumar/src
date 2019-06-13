//QuoteViewGeneralRejectedAction.java marks the quote as rejected
package app.quote;

import app.client.Client;
import app.client.ClientService;
import app.project.Project;
import app.project.ProjectService;
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
import app.user.*;
import app.standardCode.*;
import app.security.*;

public final class QuoteViewGeneralRejectedReasonAction extends Action {

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
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)

        //START get id of current quote from either request, attribute, or cookie 
        //id of quote from request
        String quoteId = null;
        quoteId = (String) request.getSession().getAttribute("RejectedQuote");
        //System.out.println("XXXXXXX" + quoteId);
        String rejectReason = request.getParameter("rejectReason");


        //check attribute in request
        if (quoteId == null) {
            quoteId = (String) request.getAttribute("quoteViewId");
        }
        if (quoteId == null) {
            quoteId = (String) request.getParameter("quoteViewId");
        }

        //id of quote from cookie
        if (quoteId == null) {
            quoteId = StandardCode.getInstance().getCookie("quoteViewId", request.getCookies());
        }

        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
        Integer id = Integer.valueOf(quoteId);

        //END get id of current quote from either request, attribute, or cookie               

        //get quote
        Quote1 q = QuoteService.getInstance().getSingleQuote(id);

        //update quote as rejected
        q.setStatus("rejected");
        //System.out.println("Quote Status          ----------" + q.getQuote1Id() + q.getStatus());
        q.setApprovalDate(new Date());
        if (u.getuserType() != null && u.getuserType().equalsIgnoreCase("client")) {
            q.setClientRejectReason(rejectReason);
        } else {
            q.setRejectReason(rejectReason);
        }



        if (request.getSession(false).getAttribute("username") != null) {
            q.setLastModifiedById((String) request.getSession(false).getAttribute("username"));
            q.setLastModifiedByTS(new Date());
        }

        QuoteService.getInstance().updateQuote(q,(String)request.getSession(false).getAttribute("username"));
        Project p = ProjectService.getInstance().getSingleProject(q.getProject().getProjectId());
        try {
            User pm = UserService.getInstance().getSingleUser(p.getPm_id());
            request.setAttribute("pm", pm.getWorkEmail1());
        } catch (Exception e) {

            request.setAttribute("pm", "excelnet@xltrans.com");
        }

        ;


        request.setAttribute("qNumber", q.getNumber());

        request.setAttribute("approvedDate", "" + new Date());

        request.setAttribute("rejectReason", rejectReason);

        // Forward control to the specified success URI
        //User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
        request.setAttribute("approvedBy", u.getFirstName() + " " + u.getLastName());
        if (u.getUserType() != null && u.getUserType().equalsIgnoreCase("client")) {
            Client c = ClientService.getInstance().getClient(u.getID_Client());
            request.setAttribute("clientName", c.getCompany_name());

            String[] fname = c.getSales_rep().split(" ");
            String lName = c.getSales_rep().replaceAll(fname[0], "").trim();
            User ae = UserService.getInstance().getSingleUserRealName(fname[0], lName);
            request.setAttribute("ae", ae.getWorkEmail1());

            return (mapping.findForward("Client"));
        } else {
            return (mapping.findForward("Success"));
        }
    }
}
