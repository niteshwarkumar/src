//ResourceViewRatesScoresDtpAdd1Action.java gets the
//source and target language and prepares the
//rateScoreDtp object for adding

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
import java.util.*;
import java.text.*;
import app.user.*;
import app.resource.*;
import app.client.*;
import app.project.*;
import app.db.*;
import app.workspace.*;
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;


public final class ResourceViewRatesScoresDtpAdd1Action extends Action {


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
                 
        //values for getting rateScoreCategory from form
        DynaValidatorForm rvrs = (DynaValidatorForm) form;
        
        //get the source and target from form (as the language name, e.g., English)
        String sourceId = (String) rvrs.get("sourceId");
        String targetId = (String) rvrs.get("targetId");
           
        RateScoreDtp rateScoreDtpAdd = new RateScoreDtp();
        rateScoreDtpAdd.setSource(sourceId);
        rateScoreDtpAdd.setTarget(targetId);        
        
        //place this resource into the request for display       
        request.setAttribute("resource", r);        
        request.getSession(false).setAttribute("resourceViewDtpSource", sourceId);
        request.getSession(false).setAttribute("resourceViewDtpTarget", targetId);
        
        //the new rate-score-dtp object for this language pair
        rvrs.set("rateScoreDtpAdd", rateScoreDtpAdd);
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
