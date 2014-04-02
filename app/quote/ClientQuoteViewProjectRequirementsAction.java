//QuoteViewProjectRequirementsAction.java gets the
//project requirements and places them into a form
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
import app.project.*;
import app.standardCode.*;
import app.security.*;

public final class ClientQuoteViewProjectRequirementsAction extends Action {

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
        quoteId = request.getParameter("quoteViewId");

        //check attribute in request
        if (quoteId == null) {
            quoteId = (String) request.getAttribute("quoteViewId");
        }

        //id of quote from cookie
        if (quoteId == null) {
            quoteId = StandardCode.getInstance().getCookie("quoteViewId", request.getCookies());
        }
        int qnum = Integer.parseInt(request.getSession(false).getAttribute("ClientQuoteId").toString());
        Client_Quote cq=QuoteService.getInstance().getSingleClient_Quote(qnum);

        Integer id = Integer.valueOf(quoteId);

        System.out.println("------------------------------------------------"+id);
        //END get id of current quote from either request, attribute, or cookie

        //get quote to and then its sources
        Quote1 q = QuoteService.getInstance().getSingleQuote(id);
        Project p = q.getProject();
        System.out.println("product Namemmmmmmmmmmmmmmmmmmmmm" + q.getProject().getProduct());
        //values of the project
        DynaValidatorForm qvpr = (DynaValidatorForm) form;
        // qvpr.set("product", q.getProject().getProduct());
        //   qvpr.set("productDescription", q.getProject().getProductDescription());
        //  qvpr.set("productUnits", q.getProject().getProductUnits());
        qvpr.set("projectRequirements", cq.getRequirement());
        qvpr.set("projectDescription", cq.getInstruction());
//        qvpr.set("product", q.getProject().getProduct());
//        qvpr.set("projectManager", q.getProject().getPm());
//        qvpr.set("beforeWorkTurn", q.getProject().getBeforeWorkTurn());
//        qvpr.set("beforeWorkTurnUnits", q.getProject().getBeforeWorkTurnUnits());
//        qvpr.set("afterWorkTurn", q.getProject().getAfterWorkTurn());
//        qvpr.set("afterWorkTurnUnits", q.getProject().getAfterWorkTurnUnits());
//        qvpr.set("afterWorkTurnReason", q.getProject().getAfterWorkTurnReason());
//        qvpr.set("sourceOs", p.getSourceOS());
//        qvpr.set("sourceApplication", p.getSourceApplication());
//        qvpr.set("sourceVersion", p.getSourceVersion());
//        qvpr.set("sourceTechNotes", p.getSourceTechNotes());
//        qvpr.set("deliverableOs", p.getDeliverableOS());
//        qvpr.set("deliverableApplication", p.getDeliverableApplication());
//        qvpr.set("deliverableVersion", p.getDeliverableVersion());
//        qvpr.set("deliverableTechNotes", p.getDeliverableTechNotes());
//        qvpr.set("productFeeUnit", p.getFee());

        //place quote into attribute for display
        request.setAttribute("quote", q);

        if (p.getDeliverableSame() != null && p.getDeliverableSame().equals("true")) {
            request.setAttribute("deliverableSame", "checked");
        }

        //place sources into request for display
        SourceDoc[] sources = new SourceDoc[q.getSourceDocs().size()];
        Iterator iter = q.getSourceDocs().iterator();
        for (int i = 0; i < sources.length; i++) {
            SourceDoc sd = (SourceDoc) iter.next();
            sources[i] = sd;
        }
        request.setAttribute("sourceArray", sources);

        //add this quote id to cookies; this will remember the last quote
        response.addCookie(StandardCode.getInstance().setCookie("quoteViewId", quoteId));

        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("quoteViewTab", "Project Requirements"));

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
