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
import java.text.*;
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;


public final class GeneratePoPre extends Action {


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

        //START get id of current project from either request, attribute, or cookie
        //id of project from request
	String projectId = null;
	projectId = request.getParameter("projectViewId");

        //check attribute in request
        if(projectId == null) {
            projectId = (String) request.getAttribute("projectViewId");
        }

        //id of project from cookie
        if(projectId == null) {
            projectId = StandardCode.getInstance().getCookie("projectViewId", request.getCookies());
        }

        //default project to last if not in request or cookie
        if(projectId == null) {
                List results = ProjectService.getInstance().getProjectList();

                ListIterator iterScroll = null;
                for(iterScroll = results.listIterator(); iterScroll.hasNext(); iterScroll.next()) {}
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
        List totalLinTasks = new ArrayList();

        //for each source
        for(Iterator sourceIter = sources.iterator(); sourceIter.hasNext();) {
            SourceDoc sd = (SourceDoc) sourceIter.next();

            //for each target of this source
            for(Iterator linTargetIter = sd.getTargetDocs().iterator(); linTargetIter.hasNext();) {
                TargetDoc td = (TargetDoc) linTargetIter.next();

                //for each lin Task of this target
                for(Iterator linTaskIter = td.getLinTasks().iterator(); linTaskIter.hasNext();) {
                    LinTask lt = (LinTask) linTaskIter.next();
                    if(lt.getPersonName()!=null&&lt.getDueDateDate()!=null&&(lt.getTaskName().equalsIgnoreCase("Translation")||lt.getTaskName().equalsIgnoreCase("Editing")||lt.getTaskName().trim().equalsIgnoreCase("Proofreading")||lt.getTaskName().equalsIgnoreCase("Proofreading / Linguistic QA"))){
                    totalLinTasks.add(lt);
                    }
                    System.out.println(sd.getLanguage()+"--------"+td.getLanguage()+"---------"+lt.getTaskName());
                }


            }
        }

        //Sort by task (orderNum), then source (language), then target (language)
        Collections.sort(totalLinTasks, CompareTaskLin.getInstance());
        Collections.sort(totalLinTasks, CompareTaskLanguages.getInstance());


        //array for display in jsp
        LinTask[] linTasksArray = (LinTask[]) totalLinTasks.toArray(new LinTask[0]);

for(int i=0;i<totalLinTasks.size();i++){
request.setAttribute(projectId, log);
}

        //place all Tasks for this project into the form for display
        DynaValidatorForm qvg = (DynaValidatorForm) form;


        //HERE down is standard and does not need to change when adding task blocks
        //place this project into request for further display in jsp page
        request.setAttribute("project", p);

        //add this project id to cookies; this will remember the last project
        response.addCookie(StandardCode.getInstance().setCookie("projectViewId", projectId));

        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("projectViewTab", "Team"));

        //an update of totals may be required


        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
