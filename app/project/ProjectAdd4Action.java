//ProjectAdd4Action.java collects the source info
//for this project

package app.project;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleException;
import org.apache.struts.util.MessageResources;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.validator.*;
import java.util.*;
import app.user.*;
import app.db.*;
import app.quote.*;
import app.client.*;
import app.project.*;
import app.workspace.*;
import app.standardCode.*;
import app.security.*;

public final class ProjectAdd4Action extends Action {


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
	
        //values for adding a new source
        DynaValidatorForm qae4 = (DynaValidatorForm) form;
        
        String sourceLanguage = (String) (qae4.get("sourceLanguage"));  
        
        //need the project to add this source
        String projectId = StandardCode.getInstance().getCookie("projectAddId", request.getCookies());
        Project p = ProjectService.getInstance().getSingleProject(Integer.valueOf(projectId)); 
        
        //new SourceDoc object for this quote
        SourceDoc sd = new SourceDoc(new HashSet());
        
        //update values
        sd.setLanguage(sourceLanguage);
        
        //add SourceDoc to db building one-to-many relationship between project and source
        Integer sdId = ProjectService.getInstance().addSourceWithProject(p, sd);           
        
        //place sourceDoc into cookie; this will be used later in wizard when adding targetDocs
        response.addCookie(StandardCode.getInstance().setCookie("projectAddSourceDocId", String.valueOf(sdId)));
        //save in cookie for future target adding
        response.addCookie(StandardCode.getInstance().setCookie("projectAddLanguage", sourceLanguage));
        request.setAttribute("projectAddLanguage",  sourceLanguage); //used in form for display
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
