/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.project;

import app.resource.Resource;
import app.resource.ResourceService;
import app.security.SecurityService;
import app.standardCode.DateService;
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
 * @author abc
 */
public class ProjectViewTeamCopyLin extends Action {

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
        //get autoUpdate value
        Integer autoUpdate = (Integer) request.getAttribute("AutoUpdate");


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

        double linTotal = 0;
        double engTotal = 0;
        double dtpTotal = 0;
        double othTotal = 0;
        double pmTotal = 0;

        //get project to edit
        Project p = ProjectService.getInstance().getSingleProject(id);
        DynaValidatorForm qvg = (DynaValidatorForm) form;
        LinTask[] linTasks = (LinTask[]) qvg.get("linTasksProject");

        //process each dtp Task to db

        for (int iq = 0; iq < linTasks.length; iq++) {
            LinTask t = linTasks[iq];
            if ((StandardCode.getInstance().noNull(t.getPersonName())).length() > 0) {

                //process each lin Task to db
                for (int i = 0; i < linTasks.length; i++) {
                    LinTask lt = linTasks[i];


                    if(lt.getPersonName()==null){
                    lt.setPersonName(t.getPersonName());
                    lt.setICRcheck(t.getICRcheck());
                    
                    lt.setWord100(t.getWord100());
                    lt.setWordRep(t.getWordRep());
                    lt.setWord95(t.getWord95());
                    lt.setWord85(t.getWord85());
                    lt.setWord75(t.getWord75());
                    lt.setWordNew(t.getWordNew());
                    lt.setWord8599(t.getWord8599());
                    lt.setWordNew4(t.getWordNew4());
                    lt.setWordContext(t.getWordContext());
                    lt.setMulti(t.getMulti());
                    lt.setMinFee(t.getMinFee());
                    lt.setWordPerfect(t.getWordPerfect());

                    lt.setWordTotal(t.getWordTotal());
                    lt.setInternalDollarTotal(t.getInternalDollarTotal());

                    lt.setInternalRate(t.getInternalRate());
                    lt.setSentDateDate(t.getSentDateDate());
                    lt.setDueDateDate(t.getDueDateDate());
                    lt.setReceivedDateDate(t.getReceivedDateDate());
                    lt.setIcrSentDate(t.getIcrSentDate());
                    lt.setIcrRecievedDate(t.getIcrRecievedDate());
                    lt.setInvoiceDateDate(t.getInvoiceDateDate());
                    lt.setIcrFinal(t.getIcrFinal());

                    //update to db
                    ProjectService.getInstance().updateLinTask(lt);
                    }
                }//END LIN TASKS
                break;
            }
        }



        //END process new total value

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
