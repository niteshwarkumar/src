//ProjectViewScopeAction.java gets current project-scope info from the db
//It then places that info into the project scope form for
//display in the jsp page. It then places location values into
//cookies

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
import java.util.*;
import app.user.*;
import app.db.*;
import app.workspace.*;
import app.security.*;
import app.quote.*;
import app.standardCode.*;
import org.apache.struts.validator.*;
import app.client.*;


public final class ProjectViewScopeAction extends Action {


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
        
        Integer id = Integer.valueOf(projectId);
        
        //END get id of current project from either request, attribute, or cookie 
              
        
        //get project to edit
        Project p = ProjectService.getInstance().getSingleProject(id); 
                
        //load project info for editing
        DynaValidatorForm pvs = (DynaValidatorForm) form;
        pvs.set("product", p.getProduct());
        pvs.set("productDescription", p.getProductDescription());
        pvs.set("productUnits", p.getProductUnits());
        pvs.set("projectDescription", p.getProjectDescription());
        pvs.set("projectRequirements", p.getProjectRequirements());
        pvs.set("linRequirements", p.getLinRequirements());
        pvs.set("dtpRequirements", p.getDtpRequirements());
        pvs.set("engRequirements", p.getEngRequirements());
        pvs.set("othRequirements", p.getOthRequirements());
        pvs.set("sourceOs", p.getSourceOS());
        pvs.set("sourceApplication", p.getSourceApplication());
        pvs.set("sourceVersion", p.getSourceVersion());
        pvs.set("sourceTechNotes", p.getSourceTechNotes());
        pvs.set("deliverableOs", p.getDeliverableOS());
        pvs.set("deliverableApplication", p.getDeliverableApplication());
        pvs.set("deliverableVersion", p.getDeliverableVersion());
        pvs.set("deliverableTechNotes", p.getDeliverableTechNotes());
        pvs.set("productFeeUnit", p.getFee());
        
        if(p.getDeliverableSame() != null && p.getDeliverableSame().equals("true")) {
            request.setAttribute("deliverableSame", "checked");
        }
        
        //place this project into the project form for display
        request.setAttribute("projectViewScope", pvs);        
        request.setAttribute("project", p);
        
        //place sources into request for display
        SourceDoc[] sources = new SourceDoc[p.getSourceDocs().size()];
        Iterator iter = p.getSourceDocs().iterator();
        for(int i = 0; i < sources.length; i++) {
            SourceDoc sd = (SourceDoc) iter.next();
            sources[i] = sd;
        }
               
        request.setAttribute("sourceArray", sources); 
        
        //add this project id to cookies; this will remember the last project
        response.addCookie(StandardCode.getInstance().setCookie("projectViewId", projectId));
        
        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("projectViewTab", "Scope"));
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
