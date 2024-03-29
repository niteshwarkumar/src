//ResourceViewLanguagesAdd1Action.java gets the new
//language pair and adds it to the db. Also, it links
//the language pair to this resource
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
import app.resource.*;
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;

public final class ResourceViewLanguagesAdd1Action extends Action {

    // ----------------------------------------------------- Instance Variables
    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log
            = LogFactory.getLog("org.apache.struts.webapp.Example");

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
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)

        //START get id of current resource from either request, attribute, or cookie 
        //id of resource from request
        String resourceId = null;
        resourceId = request.getParameter("resourceViewId");

        //check attribute in request
        if (resourceId == null) {
            resourceId = (String) request.getAttribute("resourceViewId");
        }

        //id of resource from cookie
        if (resourceId == null) {
            resourceId = StandardCode.getInstance().getCookie("resourceViewId", request.getCookies());
        }

        //default resource to first if not in request or cookie
        if (resourceId == null) {
            List results = ResourceService.getInstance().getResourceList();
            Resource first = (Resource) results.get(0);
            resourceId = String.valueOf(first.getResourceId());
        }

        Integer id = Integer.valueOf(resourceId);
        //Integer lpid = Integer.valueOf(request.getParameter("id"));

        //END get id of current resource from either request, attribute, or cookie               
        //get resource to edit
        Resource r = ResourceService.getInstance().getSingleResource(id);

        Set languagePairs = r.getLanguagePairs();
        LanguagePair[] languagePairsArray = (LanguagePair[]) languagePairs.toArray(new LanguagePair[0]);
        
        //values for add from form; change what is stored in db to these values
        DynaValidatorForm rvl = (DynaValidatorForm) form;

        String sourceId = (String) rvl.get("sourceId");
        String targetId = (String) rvl.get("targetId");
        String level = (String) rvl.get("level");
        String experienceSince = (String) rvl.get("experienceSince");
        String nativeLanguage = (String) rvl.get("nativeLanguage");
        String country = (String) rvl.get("country");
        boolean update = true;
        
        for (LanguagePair lp : languagePairsArray) {
            if (sourceId.equalsIgnoreCase("" + lp.getSourceId()) && targetId.equalsIgnoreCase("" + lp.getTargetId())) {
                update = false;
                try {
                //lp=ResourceService.getInstance().getSingleLanguagePair(lpid);
                lp.setSourceId(Integer.valueOf(sourceId));
                lp.setTargetId(Integer.valueOf(targetId));
                lp.setExperienceSince(Integer.valueOf(experienceSince));
                if (nativeLanguage.equals("on")) {
                    lp.setNativeLanguage(true);
                } else {
                    lp.setNativeLanguage(false);
                }
                lp.setCountry(country);
                lp.setLevel(level);
                //add languagePair to db; create one=to=many link between resource and languagePair
                ResourceService.getInstance().linkResourceLanguagePair(r, lp);
                
                } catch (Exception e) {
                }
                
            }
        }
        if(update){
        //the new languagePair
                LanguagePair lp = new LanguagePair(new HashSet());
                try {
                //lp=ResourceService.getInstance().getSingleLanguagePair(lpid);
                lp.setSourceId(Integer.valueOf(sourceId));
                lp.setTargetId(Integer.valueOf(targetId));
                lp.setExperienceSince(Integer.valueOf(experienceSince));
                if (nativeLanguage.equals("on")) {
                    lp.setNativeLanguage(true);
                } else {
                    lp.setNativeLanguage(false);
                }
                lp.setCountry(country);
                lp.setLevel(level);
                //add languagePair to db; create one=to=many link between resource and languagePair
                ResourceService.getInstance().linkResourceLanguagePair(r, lp);
                
                } catch (Exception e) {
                }
        }
        String screen = request.getParameter("screen");
        if (screen != null) {
            if (screen.equalsIgnoreCase("competance")) {
                return (mapping.findForward("Competance"));
            }
        }

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }

}
