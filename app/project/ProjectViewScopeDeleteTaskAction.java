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
import app.security.*;
import app.admin.*;
import app.extjs.global.LanguageAbs;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Niteshwar
 */
public class ProjectViewScopeDeleteTaskAction extends Action {

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

        //get id of source
        String id = request.getParameter("id");
        String taskName = request.getParameter("task");
        Project p = ProjectService.getInstance().getSingleProject(Integer.parseInt(id));
        String task="";

        //delete tasks

        for (Iterator iter1 = p.getSourceDocs().iterator(); iter1.hasNext();) {
            SourceDoc sd = (SourceDoc) iter1.next();
            Set targets = sd.getTargetDocs();
            for (Iterator iter2 = targets.iterator(); iter2.hasNext();) {
                TargetDoc td = (TargetDoc) iter2.next();
                Set linTask = td.getLinTasks();
                Set dtpTasks = td.getDtpTasks();
                Set engTasks = td.getEngTasks();
                Set othTasks = td.getOthTasks();
                
                for (Iterator iter3 = linTask.iterator(); iter3.hasNext();) {
                    LinTask lt = (LinTask) iter3.next();
                    if(taskName.equalsIgnoreCase(lt.getTaskName().trim())) {
                        ProjectService.getInstance().deleteLinTask(lt);
                    }
                }
                for (Iterator iter3 = dtpTasks.iterator(); iter3.hasNext();) {
                    DtpTask lt = (DtpTask) iter3.next();
                    if(taskName.equalsIgnoreCase(lt.getTaskName())){
                        ProjectService.getInstance().deleteDtpTask(lt);
                    }
                }
                for (Iterator iter3 = engTasks.iterator(); iter3.hasNext();) {
                    EngTask lt = (EngTask) iter3.next();
                    if(taskName.equalsIgnoreCase(lt.getTaskName())) {
                        ProjectService.getInstance().deleteEngTask(lt);
                    }
                }
                for (Iterator iter3 = othTasks.iterator(); iter3.hasNext();) {
                    OthTask lt = (OthTask) iter3.next();
                    if(taskName.equalsIgnoreCase(lt.getTaskName())) {
                        ProjectService.getInstance().deleteOthTask(lt);
                    }
                }
            }
        }

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
