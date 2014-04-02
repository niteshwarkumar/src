//ResourceSearchCheckAvailAction.java gets the display
//ready for team unavails

package app.resource;

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
import java.text.*;
import app.user.*;
import app.client.*;
import app.project.*;
import app.standardCode.*;
import app.db.*;
import app.workspace.*;
import app.security.*;


public final class ResourceSearchCheckAvailAction extends Action {


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
        
        //START get report criteria
        String dateAString = request.getParameter("dateA");
        Date dateA = new Date();
        if(dateAString != null && dateAString.length() > 0) {
            dateA = DateService.getInstance().convertDate(dateAString).getTime();
        }
        
        String dateBString = request.getParameter("dateB");
        Date dateB = new Date();
        if(dateBString != null && dateBString.length() > 0) {
            dateB = DateService.getInstance().convertDate(dateBString).getTime();
        }
        //END get report criteria
        
        
        //run report search
        List results = ResourceService.getInstance().checkAvail(dateA, dateB);
        
        //place results and criteria into request
        request.setAttribute("dateA", DateFormat.getDateInstance(DateFormat.SHORT).format(dateA));
        request.setAttribute("dateB", DateFormat.getDateInstance(DateFormat.SHORT).format(dateB));
        request.setAttribute("results", results);
        String resultsSize;
        if(results != null) {
            resultsSize = "<br>Found " + String.valueOf(results.size()) + " Team Unavailabilities";
        }
        else {
            resultsSize = "<br>Found " + "0" + " Team Unavailabilities";
        }
        request.setAttribute("title", "All Team Unavailabilities from " + DateFormat.getDateInstance(DateFormat.SHORT).format(dateA) + " to " + DateFormat.getDateInstance(DateFormat.SHORT).format(dateB) + resultsSize);
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
