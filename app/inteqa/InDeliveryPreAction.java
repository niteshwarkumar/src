/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.inteqa;

import app.project.*;
import app.quote.*;
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
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;

/**
 *
 * @author Niteshwar
 */
public class InDeliveryPreAction extends Action {

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
        //id of project from requestString quoteViewId = request.getParameter("quoteViewId");
//        if (request.getAttribute("fromPorQ") == null) {
//            request.setAttribute("fromPorQ", "P");
//        } else {
//            request.setAttribute("fromPorQ", (String) request.getAttribute("fromPorQ"));
//        }

        String fromPorQ="P";
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




        //get project to edit
        Project p = ProjectService.getInstance().getSingleProject(id);





        //get this project's sources
        Set sources = p.getSourceDocs();

        //for each source add each sources' Tasks
        List totalDtpTasks = new ArrayList();
        List totalEngTasks = new ArrayList();


        //for each source
        for (Iterator sourceIter = sources.iterator(); sourceIter.hasNext();) {
            SourceDoc sd = (SourceDoc) sourceIter.next();

            //for each target of this source
            for (Iterator linTargetIter = sd.getTargetDocs().iterator(); linTargetIter.hasNext();) {
                TargetDoc td = (TargetDoc) linTargetIter.next();

                //for each dtp Task of this target
                for (Iterator dtpTaskIter = td.getDtpTasks().iterator(); dtpTaskIter.hasNext();) {
                    DtpTask dt = (DtpTask) dtpTaskIter.next();
                    totalDtpTasks.add(dt);
                }
                //for each dtp Task of this target
                for (Iterator engTaskIter = td.getEngTasks().iterator(); engTaskIter.hasNext();) {
                    EngTask et = (EngTask) engTaskIter.next();
                    totalEngTasks.add(et);
                }


            }
        }



        //array for display in jsp

        DtpTask[] dtpTasksArray = (DtpTask[]) totalDtpTasks.toArray(new DtpTask[0]);
        EngTask[] engTasksArray = (EngTask[]) totalEngTasks.toArray(new EngTask[0]);

        //find total of DtpTasks
        double dtpTaskTotal = 0;
        for (int i = 0; i < dtpTasksArray.length; i++) {
            if (dtpTasksArray[i].getTotal() != null) {
                dtpTaskTotal += dtpTasksArray[i].getTotal();
            }
        }
        double dtpTaskTotalTeam = 0;
        for (int i = 0; i < dtpTasksArray.length; i++) {
            if (dtpTasksArray[i].getTotalTeam() != null) {
                dtpTaskTotalTeam += dtpTasksArray[i].getTotalTeam();
            }
        }


        //find total of EngTasks
        double engTaskTotal = 0;
        for (int i = 0; i < engTasksArray.length; i++) {
            if (engTasksArray[i].getTotal() != null) {
                engTaskTotal += engTasksArray[i].getTotal();
            }
        }
        double engTaskTotalTeam = 0;
        for (int i = 0; i < engTasksArray.length; i++) {
            if (engTasksArray[i].getTotalTeam() != null) {
                engTaskTotalTeam += engTasksArray[i].getTotalTeam();
            }
        }


        INDelivery iDelivery = InteqaService.getInstance().getINDelivery(id);

        //place this project into request for further display in jsp page
        DynaValidatorForm uvg = (DynaValidatorForm) form;


        try {
            uvg.set("notes", iDelivery.getNotes());
        } catch (Exception e) {
            uvg.set("notes", "");
        }

        try {
            uvg.set("caveats", iDelivery.getCaveats());
        } catch (Exception e) {
            uvg.set("caveats", "");
        }
        try {
            uvg.set("verified", "" + iDelivery.isVerified());
        } catch (Exception e) {
            uvg.set("verified", "");
        }
        try {
            uvg.set("verifiedBy", "" + iDelivery.getVerifiedBy());
        } catch (Exception e) {
            uvg.set("verifiedBy", "");
        }
        try {
            uvg.set("verifiedDate", "" + StandardCode.getInstance().noNull((iDelivery.getVerifiedDate()).toString()));
        } catch (Exception e) {
            uvg.set("verifiedDate", "");
        }

        try {
            uvg.set("verifiedText", "" + iDelivery.getVerifiedText());
        } catch (Exception e) {
            uvg.set("verifiedText", "");
        }

        try {
            uvg.set("dtpverified", "" + iDelivery.isDtpverified());
        } catch (Exception e) {
            uvg.set("dtpverified", "");
        }
        try {
            uvg.set("dtpverifiedBy", "" + iDelivery.getDtpverifiedBy());
        } catch (Exception e) {
            uvg.set("dtpverifiedBy", "");
        }
        try {
            uvg.set("dtpverifiedDate", "" + StandardCode.getInstance().noNull((iDelivery.getDtpverifiedDate()).toString()));
        } catch (Exception e) {
            uvg.set("dtpverifiedDate", "");
        }

        try {
            uvg.set("dtpverifiedText", "" + iDelivery.getDtpverifiedText());
        } catch (Exception e) {
            uvg.set("dtpverifiedText", "");
        }


        try {
            uvg.set("engverified", "" + iDelivery.isEngverified());
        } catch (Exception e) {
            uvg.set("engverified", "");
        }
        try {
            uvg.set("engverifiedBy", "" + iDelivery.getEngverifiedBy());
        } catch (Exception e) {
            uvg.set("engverifiedBy", "");
        }
        try {
            uvg.set("engverifiedDate", "" + StandardCode.getInstance().noNull((iDelivery.getEngverifiedDate()).toString()));
        } catch (Exception e) {
            uvg.set("engverifiedDate", "");
        }

        try {
            uvg.set("engverifiedText", "" + iDelivery.getEngverifiedText());
        } catch (Exception e) {
            uvg.set("engverifiedText", "");
        }
            try {
            uvg.set("vendor", "" + iDelivery.isVendor());
        } catch (Exception e) {
            uvg.set("vendor", "");
        }

        request.setAttribute("project", p);
        request.setAttribute("fromPorQ", "P");
        // Forward control to the specified success URI
        

        return (mapping.findForward("Success"));
    }
}
