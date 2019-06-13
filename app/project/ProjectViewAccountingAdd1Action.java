//ProjectViewAccountingAdd1Action.java adds the new
//change object to the db and builds
//the one-to-many relationship between project and change

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


public final class ProjectViewAccountingAdd1Action extends Action {


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
        
        //PRIVS check that correct user is viewing this page 
        if(!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "viewAccountTab")) {
            return (mapping.findForward("accessDenied"));
        }//END PRIVS check that correct user is viewing this page
        
        //PRIVS check that correct user is viewing this page 
        if(!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "updateAccountTab")) {
            return (mapping.findForward("accessDenied"));
        }//END PRIVS check that correct user is viewing this page
        
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
        
        //get new filled-in change object
        DynaValidatorForm qvg = (DynaValidatorForm) form;
        Change1 c = (Change1) qvg.get("changeNew");
        
        //START find total of changes (from all 4 tasks)
        String[] linIds = (String[]) qvg.get("linTasksGroup");
        String[] engIds = (String[]) qvg.get("engTasksGroup");
        String[] dtpIds = (String[]) qvg.get("dtpTasksGroup");
        String[] othIds = (String[]) qvg.get("othTasksGroup");
        double total = 0;
        
        for(int j = 0; j < linIds.length; j++) {
            LinTask t = ProjectService.getInstance().getSingleLinTask(Integer.valueOf(linIds[j]));
            if(t.getDollarTotal() != null) {
                String tTotal = t.getDollarTotal().replaceAll(",","");
                total += Double.valueOf(tTotal).doubleValue();
            }
        }
        for(int j = 0; j < engIds.length; j++) {
            EngTask t = ProjectService.getInstance().getSingleEngTask(Integer.valueOf(engIds[j]));
            if(t.getDollarTotal() != null) {
                String tTotal = t.getDollarTotal().replaceAll(",","");
                total += Double.valueOf(tTotal).doubleValue();
            }
        }
        for(int j = 0; j < dtpIds.length; j++) {
            DtpTask t = ProjectService.getInstance().getSingleDtpTask(Integer.valueOf(dtpIds[j]));
            if(t.getDollarTotal() != null) {
                String tTotal = t.getDollarTotal().replaceAll(",","");
                total += Double.valueOf(tTotal).doubleValue();
            }
        }
        for(int j = 0; j < othIds.length; j++) {
            OthTask t = ProjectService.getInstance().getSingleOthTask(Integer.valueOf(othIds[j]));
            if(t.getDollarTotal() != null) {
                String tTotal = t.getDollarTotal().replaceAll(",","");
                total += Double.valueOf(tTotal).doubleValue();
            }
        }
        
        c.setDollarTotal(StandardCode.getInstance().formatDouble(new Double(total)));
        //END find total of changes (from all 4 tasks)
        
        //add this change to db and bind it to this project
        ProjectService.getInstance().addChangeWithProject(p, c);
                       
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
