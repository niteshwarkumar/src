/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.resource;

import app.client.Client;
import app.extjs.actions.DocService;
import app.project.LinTask;
import app.project.ProjectService;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

/**
 *
 * @author niteshwar
 */
public class ResourceViewCompTranslation  extends Action {

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
        
        CompetanceTranslation cot = ResourceService.getInstance().getCompetanceTranslation(id);
        if(cot==null) {
            cot = new CompetanceTranslation();
            cot.setResourceId(id);
            cot.setCc_edition("");
            cot.setCc_certifiedIn("");
            cot.setCc_evidence_filename("");
            cot.setCc_evidence_filepath("");
            cot.setCc_expiration("");
            cot.setCc_standard("");
            cot.setCoc_evidence_filename("");
            cot.setCoc_evidence_filepath("");
            cot.setCoc_field("");
            cot.setCoc_graduation_year("");
            cot.setCoc_institution("");
            cot.setExp_translatorSince("");
            cot.setGq_evidence_filename("");
            cot.setGq_evidence_filepath("");
            cot.setGq_field("");
            cot.setGq_graduation_year("");
            cot.setGq_institution("");
            cot.setGqo_evidence_filename("");
            cot.setGqo_evidence_filepath("");
            cot.setGqo_field("");
            cot.setGqo_graduation_year("");
            cot.setGqo_institution("");
            cot.setCultureNote("");
            cot.setResearchNote("");
            cot.setCultureYesNo(Boolean.FALSE);
            cot.setResearchYesNo(Boolean.FALSE);
            cot.setGq_note("");
            cot.setGqo_note("");
            cot.setExp_note("");
            ResourceService.getInstance().addUpdateCompetanceTranslattion(cot);
        }
        //place this resource into the resource form for display 
         //get resource to edit
         
         request.setAttribute("COMPETANCECC", DocService.getInstance().getDocList(id,"COMPETANCECC"));
         request.setAttribute("COMPETANCECOC", DocService.getInstance().getDocList(id,"COMPETANCECOC"));
         request.setAttribute("COMPETANCEGQ", DocService.getInstance().getDocList(id,"COMPETANCEGQ"));
         request.setAttribute("COMPETANCEGQO", DocService.getInstance().getDocList(id,"COMPETANCEGQO")); 
       
        Resource r = ResourceService.getInstance().getSingleResource(id);  
        request.setAttribute("resource", r);        
        request.setAttribute("cot", cot);
        //add this resource id to cookies; this will remember the last resource
        response.addCookie(StandardCode.getInstance().setCookie("resourceViewId", resourceId));
        
        
        return (mapping.findForward("Success"));
    }
    // Forward control to the specified success URI

}
