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
import java.text.SimpleDateFormat;
import org.apache.struts.validator.*;

/**
 *
 * @author Niteshwar
 */
public class InQReportPreAction extends Action {

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
        //id of project from request
        String projectId = null;
        String porq = "project";
        String quoteViewId = request.getParameter("quoteViewId");
        if (quoteViewId != null) {
            Quote1 q = QuoteService.getInstance().getSingleQuote(Integer.parseInt(quoteViewId));
            projectId = q.getProject().getProjectId().toString();
            porq = "quote";

        }
        request.setAttribute("porq", porq);
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

        //place new inspection object into this form for editing
        DynaValidatorForm qvg = (DynaValidatorForm) form;

        Set sources = p.getSourceDocs();
        if (StandardCode.getInstance().noNull(quoteViewId).equalsIgnoreCase("")) {
            //sources=q.
        } else {
            //get this project's sources
            sources = p.getSourceDocs();
        }



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


        INReport ir = InteqaService.getInstance().getINReport(id);



        DynaValidatorForm uvg = (DynaValidatorForm) form;
        try {
            uvg.set("dtpNotes", ir.getDtpNotes());
        } catch (Exception e) {
            uvg.set("dtpNotes", "");
        }

        try {
            uvg.set("dtpRequirements", ir.getDtpRequirements());
        } catch (Exception e) {
            uvg.set("dtpRequirements", "");
        }

        try {
            uvg.set("enggNotes", ir.getEnggNotes());
        } catch (Exception e) {
            uvg.set("enggNotes", "");
        }

        try {
            uvg.set("enggRequirements", ir.getEnggRequirements());
        } catch (Exception e) {
            uvg.set("enggRequirements", "");
        }

        try {
            uvg.set("lingReq1", "" + ir.isLingReq1());
        } catch (Exception e) {
            uvg.set("lingReq1", "");

            System.out.println(e.getMessage());
        }

        try {
            uvg.set("lingReq2", "" + ir.isLingReq2());
        } catch (Exception e) {
            uvg.set("lingReq2", "");
        }

        try {
            uvg.set("lingReq3", "" + ir.isLingReq3());
        } catch (Exception e) {
            uvg.set("lingReq3", "");
        }

        try {
            uvg.set("lingReq4", "" + ir.isLingReq4());
        } catch (Exception e) {
            uvg.set("lingReq4", "");
        }

        try {
            uvg.set("lingReqText5", "" + StandardCode.getInstance().noNull(ir.getLingReqText5()));
        } catch (Exception e) {
            uvg.set("lingReqText5", "");
        }

        try {
            uvg.set("lingReq5", "" + ir.isLingReq5());
        } catch (Exception e) {
            uvg.set("lingReq5", "");
        }

        try {
            uvg.set("lingReqText6", StandardCode.getInstance().noNull(ir.getLingReqText6()));
        } catch (Exception e) {
            uvg.set("lingReqText6", "");
        }

        try {
            uvg.set("lingReq6", "" + ir.isLingReq6());
        } catch (Exception e) {
            uvg.set("lingReq6", "");
        }

        try {
            uvg.set("otherInfo", ir.getOtherInfo());
        } catch (Exception e) {
            uvg.set("otherInfo", "");
        }
        try {
            uvg.set("verified", "" + ir.isVerified());
        } catch (Exception e) {
            uvg.set("verified", "");
        }
        try {
            uvg.set("verifiedBy", "" + ir.getVerifiedBy());
        } catch (Exception e) {
            uvg.set("verifiedBy", "");
        }
        SimpleDateFormat myFormat = new SimpleDateFormat("MM-dd-yyyy");


        try {
            uvg.set("verifiedDate", "" + StandardCode.getInstance().noNull((myFormat.format(ir.getVerifiedDate())).toString()));
        } catch (Exception e) {
            uvg.set("verifiedDate", "");
        }
        try {
            uvg.set("verifiedText", StandardCode.getInstance().noNull(ir.getVerifiedText()));
        } catch (Exception e) {
            uvg.set("verifiedText", "");
        }
//         try {
//            uvg.set("normal",""+ ir.isNormal());
//        } catch (Exception e) {
//            uvg.set("normal", "");
//        }
//         try {
//            uvg.set("someUrgent",""+ ir.isSomeUrgent());
//        } catch (Exception e) {
//            uvg.set("someUrgent", "");
//        }
//         try {
//            uvg.set("urgent",""+ ir.isSomeUrgent());
//        } catch (Exception e) {
//            uvg.set("urgent", "");
//        }
//         try {
//            uvg.set("inProgress",""+ ir.isInProgress());
//        } catch (Exception e) {
//            uvg.set("inProgress", "");
//        }
//         try {
//            uvg.set("done1",""+ ir.isDone1());
//        } catch (Exception e) {
//            uvg.set("done1", "");
//        }
//         try {
//            uvg.set("cross",""+ ir.isCross());
//        } catch (Exception e) {
//            uvg.set("cross", "");
//        }



        try {
            if (ir.getPre_dtp() != null) {
                uvg.set("preTransDtp", "" + ir.getPre_dtp());
            } else {
                uvg.set("preTransDtp", "" + dtpTaskTotal);
            }
        } catch (Exception e) {
            uvg.set("preTransDtp", "" + dtpTaskTotal);
        }

        try {
            if (ir.getPre_dtp() != null) {
                uvg.set("postTransDtp", "" + ir.getPost_dtp());
            } else {
                uvg.set("postTransDtp", "" + dtpTaskTotalTeam);
            }
        } catch (Exception e) {
            uvg.set("postTransDtp", "" + dtpTaskTotalTeam);
        }

        try {
            if (ir.getPre_dtp() != null) {
                uvg.set("preTransEng", "" + ir.getPre_engg());
            } else {
                uvg.set("preTransEng", "" + engTaskTotal);
            }
        } catch (Exception e) {
            uvg.set("preTransEng", "" + engTaskTotal);
        }

        try {
            if (ir.getPre_dtp() != null) {
                uvg.set("postTransEng", "" + ir.getPost_engg());
            } else {
                uvg.set("postTransEng", "" + engTaskTotalTeam);
            }
        } catch (Exception e) {
            uvg.set("postTransEng", "" + engTaskTotalTeam);
        }
        Quote1 q = null;
        QuotePriority qp = null;
        try {
           q = QuoteService.getInstance().getSingleQuoteFromProject(id);
          qp = QuoteService.getInstance().getSingleQuotePriority(q.getQuote1Id());
        } catch (Exception e) {
        }


       try{ if (qp.getUrgency() == 0) {
            uvg.set("normal", "on");
        }}catch(Exception e){}try{
        if (qp.getUrgency() == 1) {
            uvg.set("someUrgent", "on");
        }}catch(Exception e){}try{
        if (qp.getUrgency() == 2) {
            uvg.set("urgent", "on");
        }}catch(Exception e){}try{
        if (qp.getStatus() == 0) {
            uvg.set("inProgress", "on");
        }}catch(Exception e){}try{
        if (qp.getStatus() == 1) {
            uvg.set("done1", "on");
        }}catch(Exception e){}try{
        if (qp.getStatus() == 2) {
            uvg.set("cross", "on");
        }}catch(Exception e){}


        //place this project into request for further display in jsp page

        request.setAttribute("inReport", ir);
        request.setAttribute("project", p);
        response.addCookie(StandardCode.getInstance().setCookie("projectViewId", String.valueOf(p.getProjectId())));

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
