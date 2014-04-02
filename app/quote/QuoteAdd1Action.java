//QuoteAdd1Action.java collects values related to 
//quote and project from this part of the wizard
//It then adds this info to a newly created
//project and quote and then adds it to the db
//Project does not have a project # (e.g., 000000)
//but quote does (e.g., Q000000)

package app.quote;

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
import app.user.*;
import app.db.*;
import app.client.*;
import app.project.*;
import app.workspace.*;
import app.standardCode.*;
import app.security.*;

public final class QuoteAdd1Action extends Action {


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
	
        //values for adding a new project and quote
        DynaValidatorForm qae1 = (DynaValidatorForm) form;
        
        //from wizard add client
        Client c;
        String clientViewId = (String) request.getAttribute("clientViewId");
        
        if(clientViewId == null) {
            String Company_name = (String) (qae1.get("client"));   
            c = ClientService.getInstance().getSingleClientByName(Company_name);  
        }
        else {
            c = ClientService.getInstance().getSingleClient(Integer.valueOf(clientViewId));
        }
        
        
        //insert new project and quote into db, building one-to-many link between client and project
        //and between project and quote
        //Integer quoteId = QuoteService.getInstance().addQuoteWithNewProject(c, ProjectService.getInstance().getNewProjectNumber()); 
        Integer quoteId = QuoteService.getInstance().addQuoteWithNewProject(c, "000000"); 
        
         
        Quote1 newQ = QuoteService.getInstance().getSingleQuote(quoteId); 
        if(request.getSession(false).getAttribute("username")!=null){
         newQ.setEnteredById((String)request.getSession(false).getAttribute("username"));
         newQ.setEnteredByTS(new Date());
        }
        QuoteService.getInstance().updateQuote(newQ);
        
        //START add Inspection list to this project
        Project p = QuoteService.getInstance().getSingleQuote(quoteId).getProject();
        Project pLazyLoad = ProjectService.getInstance().getSingleProject(p.getProjectId());
        
        
        
        
        String[] defaultInspections = ProjectService.getInstance().getDefaultInspectionOptions();
        String[] inspections = ProjectService.getInstance().getInspectionOptions();
        
        int j = 0;
        for(int i = 0; i < inspections.length; i++) {
            Inspection i2 = new Inspection();
            if(j < 7 && defaultInspections[j].equals(inspections[i])) { //if default inspection
                i2.setInDefault(true);
                i2.setApplicable(true);
                j++;
            }
            else {
               i2.setInDefault(false);
               i2.setApplicable(false);
            }
            i2.setOrderNum(new Integer(i+1));
            i2.setMilestone(inspections[i]);
            //upload to db
            ProjectService.getInstance().addInspectionWithProject(pLazyLoad, i2);
        }      
        //END add Inspection list to this project
                
        //add this quote id to cookies; this will remember the last quote
        response.addCookie(StandardCode.getInstance().setCookie("quoteAddId", String.valueOf(quoteId)));
        response.addCookie(StandardCode.getInstance().setCookie("quoteViewId", String.valueOf(quoteId)));
        request.setAttribute("quoteViewId", String.valueOf(quoteId));        
        //place client into cookie; this will be used later in wizard
        response.addCookie(StandardCode.getInstance().setCookie("quoteAddClientId", String.valueOf(c.getClientId())));
        request.setAttribute("quoteAddClientId", String.valueOf(c.getClientId()));
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
