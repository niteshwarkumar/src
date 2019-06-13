//ProjectViewScopeDeleteTargetAction.java deletes one target
//and its tasks

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
import app.extjs.helpers.ProjectHelper;


public final class ProjectViewScopeDeleteTargetAction extends Action {


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
        
        //get id of source
        String id = request.getParameter("id");
        
        //delete target, tasks
        TargetDoc td = ProjectService.getInstance().getSingleTargetDoc(Integer.valueOf(id));
        AdminService.getInstance().deleteCollection(td.getLinTasks());
        AdminService.getInstance().deleteCollection(td.getEngTasks());
        AdminService.getInstance().deleteCollection(td.getDtpTasks());
        AdminService.getInstance().deleteCollection(td.getOthTasks());
        
    String abr1 = (String) LanguageAbs.getInstance().getAbs().get(td.getLanguage());
    String abr="";
                                    //Also prevent duplicates
                                    if (abr1 != null && !"".equals(td.getLanguage()) ) {
                                        abr = abr1;

                                    } else if (abr1 == null && !"".equals(td.getLanguage()) ) {
                                        abr = td.getLanguage();  }

    SourceDoc sd =td.getSourceDoc();
    Project p=ProjectService.getInstance().getSingleProject(sd.getProject().getProjectId());


         AdminService.getInstance().deleteInspection(abr,td.getSourceDoc().getProject().getProjectId());
        AdminService.getInstance().delete(td);        
        ProjectHelper.updateLanguageCount(p);
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
