//ResourceViewSpecialtiesAction.java gets the current team object (resource)
//from the db.  It then places that resource into the 
//form featuring this resource's specialties

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
import java.io.*;
import java.util.*;
import java.text.*;
import app.user.*;
import app.client.*;
import app.resource.*;
import app.db.*;
import app.workspace.*;
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;


public final class ResourceViewSpecialtiesAction extends Action {


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
                
        //get this resources general and specific lists
        Set specifics = r.getSpecificIndustries();
        Set generals = r.getIndustries();
        
	//prepare the lists for display
        SpecificIndustry[] specificsArray  = (SpecificIndustry[]) specifics.toArray(new SpecificIndustry[0]);
        Industry[] generalsArray  = (Industry[]) generals.toArray(new Industry[0]);
        
        //load array of ids general into session for editing
        Integer[] generalIds = new Integer[generalsArray.length];
        for(int i = 0; i < generalsArray.length; i++) {
           generalIds[i] = generalsArray[i].getIndustryId();
        }
        
        //load array of ids specific into session for editing
        Integer[] specificIds = new Integer[specificsArray.length];
        for(int i = 0; i < specificsArray.length; i++) {
           specificIds[i] = specificsArray[i].getSpecificIndustryId();
        }
        
        //put the current arrays into session for specialties
        request.getSession(false).setAttribute("generalIds", generalIds);
        request.getSession(false).setAttribute("specificIds", specificIds);
        
        //load resource info for editing
        DynaValidatorForm rvs = (DynaValidatorForm) form;
        rvs.set("specifics", specificsArray);
        rvs.set("generals", generalsArray);
        
        //place this resource into the form for display      
        request.setAttribute("resource", r);        
        
        //add this resource id to cookies; this will remember the last resource
        response.addCookie(StandardCode.getInstance().setCookie("resourceViewId", resourceId));
        
        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("resourceViewTab", "Specialties"));
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
