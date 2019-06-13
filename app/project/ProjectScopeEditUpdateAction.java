//ProjectScopeEditUpdateAction.java gets updated project info
//(from Scope tab) from a form.  It then uploads the new values for
//this project to the db

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
import app.workspace.*;
import app.security.*;


public final class ProjectScopeEditUpdateAction extends Action {


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
	
        //which project to update from hidden value in form
        String id = request.getParameter("projectViewId");
        
        //get the project to be updated from db
        Project p = ProjectService.getInstance().getSingleProject(Integer.valueOf(id));
        
        //values for update from form; change what is stored in db to these values
        DynaValidatorForm pvs = (DynaValidatorForm) form;
        
        String product = (String) pvs.get("product");
        String productDescription = (String) pvs.get("productDescription");
        String productUnits = (String) pvs.get("productUnits");
        String projectDescription = (String) pvs.get("projectDescription");
        String projectRequirements = (String) pvs.get("projectRequirements");
        String linRequirements = (String) pvs.get("linRequirements");
        String dtpRequirements = (String) pvs.get("dtpRequirements");
        String engRequirements = (String) pvs.get("engRequirements");
        String othRequirements = (String) pvs.get("othRequirements");
        String sourceOS = (String) pvs.get("sourceOs");
        String sourceApplication = (String) pvs.get("sourceApplication");
        String sourceVersion = (String) pvs.get("sourceVersion");
        String sourceTechNotes = (String) pvs.get("sourceTechNotes");
        String deliverableOS = (String) pvs.get("deliverableOs");
        String deliverableApplication = (String) pvs.get("deliverableApplication");
        String deliverableVersion = (String) pvs.get("deliverableVersion");
        String deliverableTechNotes = (String) pvs.get("deliverableTechNotes");
        String deliverableSame = request.getParameter("deliverableSame");
        String productFeeUnit = request.getParameter("productFeeUnit");
               
        //update the project's values
        p.setProduct(product);
        p.setProductDescription(productDescription);
        p.setProductUnits(productUnits);
        p.setProjectDescription(projectDescription);
        p.setProjectRequirements(projectRequirements);
        p.setLinRequirements(linRequirements);
        p.setDtpRequirements(dtpRequirements);
        p.setEngRequirements(engRequirements);
        p.setOthRequirements(othRequirements);
        p.setSourceOS(sourceOS);
        p.setSourceApplication(sourceApplication);
        p.setSourceVersion(sourceVersion);
        p.setSourceTechNotes(sourceTechNotes);
        p.setDeliverableOS(deliverableOS);
        p.setDeliverableApplication(deliverableApplication);
        p.setDeliverableVersion(deliverableVersion);
        p.setDeliverableTechNotes(deliverableTechNotes);
        p.setFee(productFeeUnit);
        
        if(deliverableSame != null) {
            p.setDeliverableSame("true");
            p.setDeliverableOS(sourceOS);
            p.setDeliverableApplication(sourceApplication);
            p.setDeliverableVersion(sourceVersion);
            //p.setDeliverableTechNotes(sourceTechNotes);
        }
        else {
            p.setDeliverableSame("false");
        }
            User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
        p.setLastModifiedBy(u.getFirstName() + " " + u.getLastName());
        p.setLastModifiedDate(new Date(System.currentTimeMillis()));        
        //set updated values to db
        ProjectService.getInstance().updateProject(p);
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
