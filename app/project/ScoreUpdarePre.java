/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.project;

import org.apache.struts.action.Action;
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
import app.standardCode.*;
import app.security.*;

/**
 *
 * @author Neil
 */
public class ScoreUpdarePre extends Action {

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
        String taskId = request.getParameter("taskId");
        String score = request.getParameter("score");
        String pNo = request.getParameter("pno");
        String projectManager = request.getParameter("pm");
        LinTask lt = ProjectService.getInstance().getSingleLinTask(Integer.parseInt(taskId));

        DynaValidatorForm qvpr = (DynaValidatorForm) form;

        //  qvpr.set("projectNo", lt.getTargetDoc().getSourceDoc().getProject().getProjectId());
        qvpr.set("language", lt.getTargetDoc().getLanguage());
        qvpr.set("description", StandardCode.getInstance().noNull(lt.getScoreDescription()));
        qvpr.set("taskId", taskId);
        qvpr.set("pNo", pNo);
        qvpr.set("projectManager", projectManager);
        if (score != null) {
            qvpr.set("score", score);
        } else {
            qvpr.set("score", "" + lt.getScore());
        }


        return (mapping.findForward("Success"));
    }
}


