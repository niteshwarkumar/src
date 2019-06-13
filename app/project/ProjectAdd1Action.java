//ProjectAdd1Action.java collects values related to 
//project from this part of the wizard
//It then adds this info to a newly created
//project and then adds it to the db

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
import app.user.*;
import app.db.*;
import app.client.*;
import app.quote.*;
import app.project.*;
import app.workspace.*;
import app.standardCode.*;
import app.security.*;

public final class ProjectAdd1Action extends Action {


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
	
        //values for adding a new project and quote
        DynaValidatorForm qae1 = (DynaValidatorForm) form;
        
        //from wizard add client
        Client c;
        String clientViewId = (String) request.getAttribute("clientViewId");
        if(clientViewId == null) {
            String Company_name = (String) (qae1.get("client"));   
            c = ClientService.getInstance().getSingleClientByName(Company_name);  
        }
        else {
            c = ClientService.getInstance().getSingleClient(Integer.valueOf(clientViewId));
        }
        
        //insert new project into db, building one-to-many link between client and project
        Project p = new Project();
        p.setNumber(ProjectService.getInstance().getNewProjectNumber());
        p.setStatus("active");
        p.setPmPercent("10.00");
        p.setAe(c.getSales_rep());
        User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
        p.setCreatedBy(u.getFirstName() + " " + u.getLastName());
        p.setCreatedDate(new Date(System.currentTimeMillis()));
        
        Integer projectId = ProjectService.getInstance().addProjectWithClient(p, c);              
        
        //START add Inspection list to this project
        Project pLazyLoad = ProjectService.getInstance().getSingleProject(p.getProjectId());
        String[] defaultInspections = ProjectService.getInstance().getDefaultInspectionOptions();
        String[] inspections = ProjectService.getInstance().getInspectionOptions();
        
        int j = 0;
        for(int i = 0; i < inspections.length; i++) {
            Inspection i2 = new Inspection();
            if(j < 7 && defaultInspections[j].equals(inspections[i])) { //if default inspection
                i2.setInDefault(true);
                i2.setApplicable(true);
                j++;
            }
            else {
               i2.setInDefault(false);
               i2.setApplicable(false);
            }
            i2.setOrderNum(new Integer(i+1));
            i2.setMilestone(inspections[i]);
            //upload to db
            ProjectService.getInstance().addInspectionWithProject(pLazyLoad, i2);
        }      
        //END add Inspection list to this project
                
        //add this project id to cookies; this will remember the last project
        response.addCookie(StandardCode.getInstance().setCookie("projectAddId", String.valueOf(projectId)));
        response.addCookie(StandardCode.getInstance().setCookie("projectViewId", String.valueOf(projectId)));
                
        //place client into cookie; this will be used later in wizard
        response.addCookie(StandardCode.getInstance().setCookie("projectAddClientId", String.valueOf(c.getClientId())));
        request.setAttribute("projectAddClientId", String.valueOf(c.getClientId()));
        
        //clear project pre file list
        request.getSession(false).setAttribute("fileLocations", null);
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
