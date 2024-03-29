//ResourceViewRatesScoresAction.java gets the current team object (resource)
//from the db.  It then places that resource into the 
//form featuring rates and scores for this resource

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
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;


public final class ResourceViewRatesScoresAction extends Action {


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
                
        //get this resources language pairs
        Set languagePairs = r.getLanguagePairs();
        
        //the list for display (load all ratescorelanguage entries)
        List ratescorelanguages = new ArrayList();
        for(Iterator iter = languagePairs.iterator(); iter.hasNext();) {
            LanguagePair lp = (LanguagePair) iter.next();
            for(Iterator rateScoreIter = lp.getRateScoreLanguages().iterator(); rateScoreIter.hasNext();) {
                RateScoreLanguage rsl = (RateScoreLanguage) rateScoreIter.next();
                ratescorelanguages.add(rsl);
            }
                
        }
        
        
	//prepare the ratescorelanguages and rateScoreDtps for display
        RateScoreLanguage[] ratescorelanguagesArray = (RateScoreLanguage[]) ratescorelanguages.toArray(new RateScoreLanguage[0]);
        RateScoreDtp[] rateScoreDtpsArray = (RateScoreDtp[]) r.getRateScoreDtps().toArray(new RateScoreDtp[0]);
        
        //load resource info for editing
        DynaValidatorForm rvrs = (DynaValidatorForm) form;
        rvrs.set("resourceFromForm", r);
        rvrs.set("rateScoreLanguages", ratescorelanguagesArray);
        rvrs.set("rateScoreDtps", rateScoreDtpsArray);
        
        //place linguistic rate scaling into form
        rvrs.set("scaleRep", r.getScaleRep());
        rvrs.set("scale100", r.getScale100());
        rvrs.set("scale95", r.getScale95());
        rvrs.set("scale85", r.getScale85());
        rvrs.set("scale75", r.getScale75());
        rvrs.set("scaleNew", r.getScaleNew());
        if(r.isScaleDefault()) {
            rvrs.set("scaleDefault", "on");
        }
        else {
            rvrs.set("scaleDefault", "off");
        }
        
        //place this resource into the resource form for display     
        request.setAttribute("resource", r);        
        
        //add this resource id to cookies; this will remember the last resource
        response.addCookie(StandardCode.getInstance().setCookie("resourceViewId", resourceId));
        
        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("resourceViewTab", "RatesScores"));
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
