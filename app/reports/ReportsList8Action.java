//ReportsList8Action.java gets the display
//ready for reporting info, such as graphs, dollar totals, etc.

package app.reports;

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
import app.resource.*;
import app.standardCode.*;
import app.security.*;


public final class ReportsList8Action extends Action {


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
        
        //PRIVS check that reports user is viewing this page 
        if(!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "reports")) {
            return (mapping.findForward("accessDenied"));
        }//END PRIVS check that reports user is viewing this page
        
        //START get report criteria
        String resourceId = request.getParameter("resourceId");
        if(resourceId == null) { //default to first in db
            List results = ResourceService.getInstance().getResourceList();
            Resource rResult = (Resource) results.get(0);
            resourceId = String.valueOf(rResult.getResourceId());
        }
        
        //END get report criteria
        
        
        //run report search
        //get resource to edit
        Resource r = ResourceService.getInstance().getSingleResource(Integer.valueOf(resourceId)); 
                
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
        
              
                
        //place results and criteria into request
        //place tasks into display
        request.setAttribute("linTasksDisplay", linTasksDisplay);
        //request.setAttribute("linTasksDisplayNumbers", linTasksDisplayNumbers);
        request.setAttribute("engTasksDisplay", engTasksDisplay);
        //request.setAttribute("engTasksDisplayNumbers", engTasksDisplayNumbers);
        request.setAttribute("dtpTasksDisplay", dtpTasksDisplay);
        //request.setAttribute("dtpTasksDisplayNumbers", dtpTasksDisplayNumbers);
        request.setAttribute("othTasksDisplay", othTasksDisplay);
        //request.setAttribute("othTasksDisplayNumbers", othTasksDisplayNumbers);
        request.setAttribute("resourceId", resourceId);
        
        String resultsSize;
        int count = 0;
        if(linTasksDisplay != null) {
            count += linTasksDisplay.size();
        }
        if(engTasksDisplay != null) {
            count += engTasksDisplay.size();
        }
        if(dtpTasksDisplay != null) {
            count += dtpTasksDisplay.size();
        }
        if(othTasksDisplay != null) {
            count += othTasksDisplay.size();
        }        
        resultsSize = "<br>Found " + String.valueOf(count) + " Projects";
        
        double total = 0.0;
        if(linTasksDisplay != null) {
            for(ListIterator iter = linTasksDisplay.listIterator(); iter.hasNext();) {
                LinTask t = (LinTask) iter.next();
                if(t.getInternalDollarTotal() != null) {
                    total += new Double(t.getInternalDollarTotal()).doubleValue();
                }
            }
        }
        if(engTasksDisplay != null) {
            for(ListIterator iter = engTasksDisplay.listIterator(); iter.hasNext();) {
                EngTask t = (EngTask) iter.next();
                if(t.getInternalDollarTotal() != null) {
                    total += new Double(t.getInternalDollarTotal()).doubleValue();
                }
            }
        }
        if(dtpTasksDisplay != null) {
            for(ListIterator iter = dtpTasksDisplay.listIterator(); iter.hasNext();) {
                DtpTask t = (DtpTask) iter.next();
                if(t.getInternalDollarTotal() != null) {
                    total += new Double(t.getInternalDollarTotal()).doubleValue();
                }
            }
        }
        if(othTasksDisplay != null) {
            for(ListIterator iter = othTasksDisplay.listIterator(); iter.hasNext();) {
                OthTask t = (OthTask) iter.next();
                if(t.getInternalDollarTotal() != null) {
                    total += new Double(t.getInternalDollarTotal()).doubleValue();
                }
            }
        }
        request.setAttribute("total", StandardCode.getInstance().formatDouble(new Double(total)));
        
        try{
        if((r.getFirstName().length() >= 1 && r.getFirstName() != null) && (r.getLastName().length() >= 1 && r.getLastName() != null)) {
            request.setAttribute("title", "All Projects where Team Member " + StandardCode.getInstance().noNull(r.getFirstName()) + " " + StandardCode.getInstance().noNull(r.getLastName()) + " was used" + resultsSize);
        }
        else {
            request.setAttribute("title", "All Projects where Team Member " + StandardCode.getInstance().noNull(r.getCompanyName()) + " was used" + resultsSize);
        }}catch(Exception e){}
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
