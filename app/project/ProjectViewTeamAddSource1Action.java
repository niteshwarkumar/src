//ProjectViewTeamAddSource1Action.java adds the
//new source to the existing project

package app.project;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleException;
import org.apache.struts.util.MessageResources;
import org.apache.commons.beanutils.PropertyUtils;
import java.util.*;
import java.text.*;
import app.user.*;
import app.db.*;
import app.workspace.*;
import app.security.*;
import app.project.*;
import app.standardCode.*;
import org.apache.struts.validator.*;
import app.client.*;


public final class ProjectViewTeamAddSource1Action extends Action {


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
                     
        //get current project ID
        
        //id from request attribute
        String projectId = (String) request.getAttribute("projectViewId");
        
        //id from cookie
        if(projectId == null) {
            projectId = StandardCode.getInstance().getCookie("projectViewId", request.getCookies());	
        }
        
        Integer id = Integer.valueOf(projectId);        
        
        //get project with id from database
        Project p = ProjectService.getInstance().getSingleProject(id);
        
        //values for adding a new source
        DynaValidatorForm qae4 = (DynaValidatorForm) form;        
        String sourceLanguage = (String) (qae4.get("sourceLanguage"));  
        
        //new SourceDoc object for this quote
        SourceDoc sd = new SourceDoc(new HashSet());
        
        //update values
        sd.setLanguage(sourceLanguage);
        
        //add SourceDoc to db building one-to-many relationship between project and source
        Integer sdId = ProjectService.getInstance().addSourceWithProject(p, sd);       
        
        //place sourceDoc into cookie; this will be used later in for adding targetDocs
        response.addCookie(StandardCode.getInstance().setCookie("projectViewGeneralAddSourceId", String.valueOf(sdId)));
        request.setAttribute("projectAddLanguage",  sourceLanguage); //used in form for display
        
        //put this project into the request
        request.setAttribute("project", p);
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
