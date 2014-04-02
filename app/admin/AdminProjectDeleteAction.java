//AdminProjectDeleteAction.java deletes one project from the db
//and the project's one-to-many mappings

package app.admin;

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
import app.project.*;
import app.standardCode.*;
import app.security.*;


public final class AdminProjectDeleteAction extends Action {


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
	        
        //PRIVS check that admin user is viewing this page 
        if(!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "admin")) {
            return (mapping.findForward("accessDenied"));
        }//END PRIVS check that admin user is viewing this page
        
        //get object to delete
        String id = request.getParameter("id");

        Project object = ProjectService.getInstance().getSingleProject(Integer.valueOf(id));    
        
        //delete one-to-many mappings first, then delete main (or top) object
        AdminService.getInstance().deleteCollection(object.getInspections());
        AdminService.getInstance().deleteCollection(object.getChange1s());
        AdminService.getInstance().deleteCollection(object.getQualities());
        for(Iterator iter = object.getSourceDocs().iterator(); iter.hasNext();) {
            SourceDoc sd = (SourceDoc) iter.next();
            for(Iterator iterT = sd.getTargetDocs().iterator(); iterT.hasNext();) {
                TargetDoc td = (TargetDoc) iterT.next();
                AdminService.getInstance().deleteCollection(td.getLinTasks());
                AdminService.getInstance().deleteCollection(td.getEngTasks());
                AdminService.getInstance().deleteCollection(td.getDtpTasks());
                AdminService.getInstance().deleteCollection(td.getOthTasks());
            }
            AdminService.getInstance().deleteCollection(sd.getTargetDocs());        
        }
        AdminService.getInstance().deleteCollection(object.getSourceDocs()); 
         ProjectService.getInstance().deleteProject(object);
        
        //ALEKS 03-15-2006:  We also have to reset this projectViewId from the session  
        response.addCookie(StandardCode.getInstance().setCookie("projectViewId", null ));
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
