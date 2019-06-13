//ResourceViewAccountAction.java gets the current team object (resource)
//from the db.  It then places that resource into the resource form for
//display in the jsp page featuring accounting info. It then places location values into
//cookies

package app.resource;

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
import app.resource.*;
import app.project.*;
import app.db.*;
import app.workspace.*;
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;


public final class ResourceViewAccountAction extends Action {


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
        
        //START get id of current resource from either request, attribute, or cookie 
        //id of resource from request
	String resourceId = null;
	resourceId = request.getParameter("resourceViewId");
        
        //check attribute in request
        if(resourceId == null) {
            resourceId = (String) request.getAttribute("resourceViewId");
        }
        
        //id of resource from cookie
        if(resourceId == null) {            
            resourceId = StandardCode.getInstance().getCookie("resourceViewId", request.getCookies());
        }

        //default resource to first if not in request or cookie
        if(resourceId == null) {
                List results = ResourceService.getInstance().getResourceList();
                Resource first = (Resource) results.get(0);
                resourceId = String.valueOf(first.getResourceId());
        }            
        
        Integer id = Integer.valueOf(resourceId);
        
        //END get id of current resource from either request, attribute, or cookie               
        
        //get resource to edit
        Resource r = ResourceService.getInstance().getSingleResource(id); 
                
        //get Tasks for display
        List linTasksDisplay = new ArrayList();
        List engTasksDisplay = new ArrayList();
        List dtpTasksDisplay = new ArrayList();
        List othTasksDisplay = new ArrayList();
        
        //get this resources tasks from db
        linTasksDisplay = ResourceService.getInstance().getResourceLinTasks(String.valueOf(r.getResourceId()));
        engTasksDisplay = ResourceService.getInstance().getResourceEngTasks(String.valueOf(r.getResourceId()));
        dtpTasksDisplay = ResourceService.getInstance().getResourceDtpTasks(String.valueOf(r.getResourceId()));
        othTasksDisplay = ResourceService.getInstance().getResourceOthTasks(String.valueOf(r.getResourceId()));
        
        
        //place tasks into display
        request.setAttribute("linTasksDisplay", linTasksDisplay);
        //request.setAttribute("linTasksDisplayNumbers", linTasksDisplayNumbers);
        request.setAttribute("engTasksDisplay", engTasksDisplay);
        //request.setAttribute("engTasksDisplayNumbers", engTasksDisplayNumbers);
        request.setAttribute("dtpTasksDisplay", dtpTasksDisplay);
        //request.setAttribute("dtpTasksDisplayNumbers", dtpTasksDisplayNumbers);
        request.setAttribute("othTasksDisplay", othTasksDisplay);
        //request.setAttribute("othTasksDisplayNumbers", othTasksDisplayNumbers);
      
        //find total of all work (all four tasks)
        double total = 0;
        if(linTasksDisplay != null) {
        for(ListIterator iter = linTasksDisplay.listIterator(); iter.hasNext();) {
            LinTask t = (LinTask) iter.next();
            if(t.getInternalDollarTotal() != null && t.getInternalDollarTotal().length() > 0) {
                String tTotal = t.getInternalDollarTotal().replaceAll(",","");
                total += Double.valueOf(tTotal).doubleValue();
            }
        }
        }
        if(engTasksDisplay != null) {
        for(ListIterator iter = engTasksDisplay.listIterator(); iter.hasNext();) {
            EngTask t = (EngTask) iter.next();
            if(t.getInternalDollarTotal() != null && t.getInternalDollarTotal().length() > 0) {
                String tTotal = t.getInternalDollarTotal().replaceAll(",","");
                total += Double.valueOf(tTotal).doubleValue();
            }
        }
        }
        if(dtpTasksDisplay != null) {
        for(ListIterator iter = dtpTasksDisplay.listIterator(); iter.hasNext();) {
            DtpTask t = (DtpTask) iter.next();
            if(t.getInternalDollarTotal() != null && t.getInternalDollarTotal().length() > 0) {
                String tTotal = t.getInternalDollarTotal().replaceAll(",","");
                total += Double.valueOf(tTotal).doubleValue();
            }
        }
        }
        if(othTasksDisplay != null) {
        for(ListIterator iter = othTasksDisplay.listIterator(); iter.hasNext();) {
            OthTask t = (OthTask) iter.next();
            if(t.getInternalDollarTotal() != null && t.getInternalDollarTotal().length() > 0) {
                String tTotal = t.getInternalDollarTotal().replaceAll(",","");
                total += Double.valueOf(tTotal).doubleValue();
            }
        }
        }
        //add total to request for display
        request.setAttribute("total", StandardCode.getInstance().formatDouble(new Double(total)));
        
        
        //place this resource into the resource form for display   
        request.setAttribute("resource", r);        
        
        //add this resource id to cookies; this will remember the last resource
        response.addCookie(StandardCode.getInstance().setCookie("resourceViewId", resourceId));
        
        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("resourceViewTab", "Account"));
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
