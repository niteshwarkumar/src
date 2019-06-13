/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.inteqa;

import app.project.Project;
import app.project.ProjectService;
import app.quote.Quote1;
import app.quote.QuoteService;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import java.util.List;
import java.util.ListIterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.DynaValidatorForm;

/**
 *
 * @author niteshwar
 */
public class InTimePreAction  extends Action {

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

        //START get id of current project from either request, attribute, or cookie


         String fromPorQ="Q";
        String projectId = null;
        String quoteViewId = request.getParameter("quoteViewId");
        if (quoteViewId != null) {
            Quote1 q = QuoteService.getInstance().getSingleQuote(Integer.parseInt(quoteViewId));
            projectId = q.getProject().getProjectId().toString();
//            request.setAttribute("fromPorQ", "Q");

        }

        if (projectId == null) {
            projectId = request.getParameter("projectViewId");
        }

        //check attribute in request
        if (projectId == null) {
            projectId = (String) request.getAttribute("projectViewId");
        }

        //id of project from cookie
        if (projectId == null) {
            projectId = StandardCode.getInstance().getCookie("projectViewId", request.getCookies());
        }

        //default project to last if not in request or cookie
        if (projectId == null) {
            List results = ProjectService.getInstance().getProjectList();

            ListIterator iterScroll = null;
            for (iterScroll = results.listIterator(); iterScroll.hasNext(); iterScroll.next()) {
            }
            iterScroll.previous();
            Project p = (Project) iterScroll.next();
            projectId = String.valueOf(p.getProjectId());
        }

        Integer id = Integer.valueOf(projectId);

        //END get id of current project from either request, attribute, or cookie


        INTime inTime = new INTime();
        //place this project into request for further display in jsp page
        DynaValidatorForm uvg = (DynaValidatorForm) form;


        try {
            uvg.set("irtdate1", inTime.getIrtdate1());
        } catch (Exception e) {
            uvg.set("irtdate1", "");
        }

        try {
            uvg.set("irtdate2", inTime.getIrtdate2());
        } catch (Exception e) {
            uvg.set("irtdate2", "");
        }
        try {
            uvg.set("cstdate1", "" + inTime.getCstdate1());
        } catch (Exception e) {
            uvg.set("cstdate1", "");
        }
        try {
            uvg.set("cstdate2", "" + inTime.getCstdate2());
        } catch (Exception e) {
            uvg.set("cstdate2", "");
        }
        try {
            uvg.set("csttime1", "" + inTime.getCsttime1());
        } catch (Exception e) {
            uvg.set("csttime1", "");
        }

        try {
            uvg.set("csttime2", "" + inTime.getCsttime2());
        } catch (Exception e) {
            uvg.set("csttime2", "");
        }
         try {
            uvg.set("irttime1", "" + inTime.getIrttime1());
        } catch (Exception e) {
            uvg.set("irttime1", "");
        }
         try {
            uvg.set("irttime2", "" + inTime.getIrttime2());
        } catch (Exception e) {
            uvg.set("irttime2", "");
        }

       
        
        
        // Forward control to the specified success URI
        
             return (mapping.findForward("Success"));

    }
}
