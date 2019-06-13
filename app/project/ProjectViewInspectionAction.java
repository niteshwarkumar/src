//ProjectViewInspectionAction.java displays inspection info
//for a project

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
import org.apache.struts.validator.*;
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
import app.tracker.*;


public final class ProjectViewInspectionAction extends Action {


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
              
        
        //get project for inspections
        Project p = ProjectService.getInstance().getSingleProject(id); 
        
        
        //array for display in jsp
        Set inspections = p.getInspections();
        Inspection[] inspectionsArray = (Inspection[]) inspections.toArray(new Inspection[0]);
        
        //START set up inspection dates
        for(int i = 0; i < inspections.size(); i++) {
            if(inspectionsArray[i].getInDate() != null) {
                request.setAttribute("inspectionDateArray" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(inspectionsArray[i].getInDate()));
            }
            else {
                request.setAttribute("inspectionDateArray" + String.valueOf(i), "");
            }
        }
        //END set up inspection dates
        
        //place all inspections for this project into the form for display
        DynaValidatorForm pvi = (DynaValidatorForm) form;
        pvi.set("inspections", inspectionsArray);        
        
        //put this project into the request
        request.setAttribute("project", p);
        
        //Set tracker checkbox values
        Hashtable millanValues = new Hashtable();
        List trackerItems = TrackerService.getInstance().getMilLanList(p.getProjectId());
        if(trackerItems!=null){    
        for(ListIterator iter = trackerItems.listIterator(); iter.hasNext(); ) {
                MilestoneLanguage millan = (MilestoneLanguage) iter.next();
                ////THIS FORM: MILLAN_<%=iDisplay.getInspectionId()+"_"+languageId+"_"+iDisplay.getProject().getProjectId()
               // //System.out.println("alexx:"+"MILLAN_"+millan.getMilestone_id()+"_"+millan.getLang_id()+"_"+millan.getProject_id());
                millanValues.put("MILLAN_"+millan.getMilestone_id()+"_"+millan.getLang_id()+"_"+millan.getProject_id()," checked ");
            }
        }
        
        request.setAttribute("millanValues", millanValues);
        //add this project id to cookies; this will remember the last project
        response.addCookie(StandardCode.getInstance().setCookie("projectViewId", projectId));
        
        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("projectViewTab", "Inspection"));
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
