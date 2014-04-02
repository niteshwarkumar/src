/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.project;

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
import app.standardCode.StandardCode;
import org.apache.struts.validator.DynaValidatorForm;

/**
 *
 * @author abc
 */
public class ProjectViewTeamCopyDTP extends Action {

    // ----------------------------------------------------- Instance Variables
    /**
     * The
     * <code>Log</code> instance for this application.
     */
    private Log log =
            LogFactory.getLog("org.apache.struts.webapp.Example");

    // --------------------------------------------------------- Public Methods
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an
     * <code>ActionForward</code> instance describing where and how control
     * should be forwarded, or
     * <code>null</code> if the response has already been completed.
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
        String projectId = null;
        projectId = request.getParameter("projectViewId");

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

        double dtpTotal = 0;

        //get project to edit
        Project p = ProjectService.getInstance().getSingleProject(id);
        DynaValidatorForm qvg = (DynaValidatorForm) form;
        DtpTask[] dtpTasks = (DtpTask[]) qvg.get("dtpTasksProject");
        //process each dtp Task to db

        for (int iq = 0; iq < dtpTasks.length; iq++) {
            DtpTask t = dtpTasks[iq];
            if ((StandardCode.getInstance().noNull(t.getPersonName())).length() > 0) {

                for (int i = 0; i < dtpTasks.length; i++) {
                    DtpTask dt = dtpTasks[i];
                    dt.setPersonName(t.getPersonName());
                    dt.setInternalRate(t.getInternalRate());
                    dt.setUnitsTeam(t.getUnitsTeam());
                    dt.setInternalCurrency(t.getInternalCurrency());
                    dt.setTotalTeam(t.getTotalTeam());
                          
                    //START process new TOTAL value
                    if (dt.getTotalTeam() != null) { //if from delete then make sure it is not null
                        double total = dt.getTotalTeam().doubleValue();
                        double rate = 0;
                        try {
                            rate = Double.valueOf(dt.getInternalRate()).doubleValue();
                        } catch (java.lang.NumberFormatException nfe) {
                            rate = 0;
                        }

                        double thisTotal = total * rate;
                        dtpTotal += thisTotal; //update dtp block

                        dt.setInternalDollarTotal(StandardCode.getInstance().formatDouble(new Double(thisTotal)));
                        dt.setInternalRate(StandardCode.getInstance().formatDouble3(new Double(rate)));
                    }
                    ProjectService.getInstance().updateDtpTask(dt);
                }
break;
            }
        }



        //END process new total value

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
