//ResourceViewGeneralAction.java gets the current team object (resource)
//from the db.  It then places that resource into the resource form for
//display in the jsp page. It then places location values into
//cookies

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
import java.util.*;
import java.text.*;
import app.resource.*;
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;


public final class ResourceViewGeneralAction extends Action {


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
        
        //if from another module (i.e., a link), display only one record
        String fromLink = request.getParameter("fromLink");
        
        //check attribute in request
        if(resourceId == null) {
            resourceId = (String) request.getAttribute("resourceViewId");
        }
        
        //id of resource from cookie
        if(resourceId == null) {            
            resourceId = StandardCode.getInstance().getCookie("resourceViewId", request.getCookies());
        }

        //default resource to first if not in request or cookie
        if(resourceId == null || "null".equals(resourceId)){
                List results = ResourceService.getInstance().getResourceList();
                Resource first = (Resource) results.get(0);
                resourceId = String.valueOf(first.getResourceId());
        }            
        
        Integer id = Integer.valueOf(resourceId);
        
        //END get id of current resource from either request, attribute, or cookie               
        
        //get resource to edit
        Resource r = ResourceService.getInstance().getSingleResource(id); 
                
        //START get current record number
        //get what list to scroll over

        Integer[] resourceSearchResultIds = (Integer[]) request.getSession(false).getAttribute("resourceSearchResultIds");
        List resources = new ArrayList(); //the list to scroll over
	try{
        if(resourceSearchResultIds == null || resourceSearchResultIds.length == 0) { //no search results present
            //all current resources in db in number order
            resources = ResourceService.getInstance().getResourceList();
        }
        else { //get all search result objects
            for(int i = 0; i < resourceSearchResultIds.length; i++) {
                Resource r1 = ResourceService.getInstance().getSingleResourceNoLazy(resourceSearchResultIds[i]);
                resources.add(r1);
            }            
        }}catch(Exception e){}
        //load just one record because you just came from a link (another module)
        if(fromLink != null && fromLink.equals("true")) {
            Resource r1 = ResourceService.getInstance().getSingleResourceNoLazy(Integer.valueOf(resourceId));
      resources = new ArrayList();
            resources.add(r1);
            
            //set only this resource id for later use in team module
            Integer[] resourceSearchResultIdsFromLink = new Integer[1];
            resourceSearchResultIdsFromLink[0] = new Integer(0);
            resourceSearchResultIdsFromLink[0] = Integer.valueOf(resourceId);
            //set Ids into session for viewing
            request.getSession(false).setAttribute("resourceSearchResultIds", resourceSearchResultIdsFromLink);
            
            //set the total to "1"
            request.getSession(false).setAttribute("resourceRecordTotal", "1");        
        }
        
        //set the total now
        String resourceRecordTotal = String.valueOf(new Integer(resources.size()));
        Resource newR;
        Resource currentR = r;
	int current = 0;
        
        //get the current record number (e.g., "3" of 40)
        for(ListIterator iter = resources.listIterator(); iter.hasNext(); ) {
                  newR = (Resource) iter.next();
                  current++; //advance current record count
                  if(newR.getResourceId().equals(currentR.getResourceId())) { //check if current                         
                      break; //stop looking for current count
                  }
        }
        
        //END get current record number
        
        //load resource info for editing
        DynaValidatorForm rvt = (DynaValidatorForm) form;
        
        //treat dates special for display and also so the javascript calendar works
        if(r.getYearsInIndustry() != null) {
            rvt.set("yearsInIndustry", DateFormat.getDateInstance(DateFormat.SHORT).format(r.getYearsInIndustry()));
        }
        else {
            rvt.set("yearsInIndustry", null);
        }
        if(r.getMsaSent() != null) {
            rvt.set("msaSent", DateFormat.getDateInstance(DateFormat.SHORT).format(r.getMsaSent()));
        }
        else {
            rvt.set("msaSent", null);
        }
        if(r.getMsaReceived() != null) {
            rvt.set("msaReceived", DateFormat.getDateInstance(DateFormat.SHORT).format(r.getMsaReceived()));
        }
        else {
            rvt.set("msaReceived", null);
        }
        
        //place main resource into form
        rvt.set("resourceFromForm", r);
        
        //get unavail events
        Set unavails = r.getUnavails();
          //place into form for display
        Unavail[] unavailsArray = (Unavail[]) unavails.toArray(new Unavail[0]);
        for(int i = 0; i < unavailsArray.length; i++) {
          
            if(new Date().after(unavailsArray[i].getEndDate()))  {
                
                unavails.remove(unavailsArray[i]);
            }
            
        }
       
        //place into form for display
         unavailsArray = (Unavail[]) unavails.toArray(new Unavail[0]);
        
         Unavail[] unavailsArrayCurrent = new Unavail[unavailsArray.length];
               
        //set up unavail dates
        for(int i = 0; i < unavailsArray.length; i++) {
            if(unavailsArray[i].getStartDate() != null) {
                request.setAttribute("unavailStart" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(unavailsArray[i].getStartDate()));
            }
            else {
                request.setAttribute("unavailStart" + String.valueOf(i), "");
            }
        }
        for(int i = 0; i < unavailsArray.length; i++) {
            int cnrtr = 0;
            if(unavailsArray[i].getEndDate() != null && new Date().before(unavailsArray[i].getEndDate()))  {
                request.setAttribute("unavailEnd" + String.valueOf(i), DateFormat.getDateInstance(DateFormat.SHORT).format(unavailsArray[i].getEndDate()));
            }
            else {
                request.setAttribute("unavailEnd" + String.valueOf(i), "");
            }
        }
     
        
         rvt.set("unavails", unavailsArray);
        
        //place this resource into the request for display       
        request.setAttribute("resource", r);        
        
        //set current record and total records into session and cookie
        request.getSession(false).setAttribute("resourceRecordCurrent", String.valueOf(new Integer(current)));
        response.addCookie(StandardCode.getInstance().setCookie("resourceRecordCurrent", String.valueOf(new Integer(current))));
        
        //add this resource id to cookies; this will remember the last resource
        response.addCookie(StandardCode.getInstance().setCookie("resourceViewId", resourceId));
        
        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("resourceViewTab", "General Info"));
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
