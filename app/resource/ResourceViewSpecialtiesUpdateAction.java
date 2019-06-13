//ResourceViewSpecialtiesUpdateAction.java gets updated 
//specialties for this resource and updates them to db

package app.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.*;
import app.client.*;
import app.security.*;


public final class ResourceViewSpecialtiesUpdateAction extends Action {


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
        
	//which resource to update from hidden value in form
        String id = request.getParameter("resourceViewId");  
        //get the resource to be updated from db
        Resource r = ResourceService.getInstance().getSingleResource(Integer.valueOf(id));
                
        //values for update from form; change what is stored in db to these values
        DynaValidatorForm rvsa = (DynaValidatorForm) form;
        
        SpecificIndustry[] specifics = (SpecificIndustry[]) rvsa.get("specifics");
        Industry[] generals = (Industry[]) rvsa.get("generals");
        
        //current list of generals and specifics
        Integer[] generalIds = (Integer[]) request.getSession(false).getAttribute("generalIds");
        Integer[] specificIds = (Integer[]) request.getSession(false).getAttribute("specificIds");
        
        //update each general industry, resource relationship
        //unlink the links that no longer exist
        ResourceService.getInstance().unlinkResourceIndustry(r, generals, generalIds);
        ResourceService.getInstance().unlinkResourceSpecificIndustry(r, specifics, specificIds);
                
        //add the new links to general list
        for(int i = 0; i < generalIds.length; i++) {  
            boolean add = true;
            for(int j = 0; j < generalIds.length; j++) {
                if(generalIds[j].equals(generals[i].getIndustryId())) {
                    add = false;
                }               
            }
            if(add) {
                 ResourceService.getInstance().linkResourceIndustry(r, ClientService.getInstance().getSingleIndustry(generals[i].getIndustryId()));
            }
       }
         
        //add the new links to specific list
        for(int i = 0; i < specificIds.length; i++) {  
            boolean add = true;
            for(int j = 0; j < specificIds.length; j++) {
                if(specificIds[j].equals(specifics[i].getSpecificIndustryId())) {
                    add = false;
                }               
            }
            if(add) {
                 ResourceService.getInstance().linkResourceSpecificIndustry(r, ClientService.getInstance().getSingleSpecificIndustry(specifics[i].getSpecificIndustryId()));
            }
       }
       
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
