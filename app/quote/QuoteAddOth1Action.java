//QuoteAddOth1Action.java gets the sources
//the user chose and places them into a form for display

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
import java.util.*;
import java.text.*;
import app.user.*;
import app.db.*;
import app.workspace.*;
import app.security.*;
import app.project.*;
import app.standardCode.*;
import org.apache.struts.validator.*;
import app.client.*;


public final class QuoteAddOth1Action extends Action {


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
                     
        //START get id of current project from either request, attribute, or cookie 
        //id of project from cookie
        String projectId = null;
        
        if(projectId == null) {            
            projectId = StandardCode.getInstance().getCookie("quoteAddId", request.getCookies());
        }       
        
        Integer id = Integer.valueOf(projectId);
        
        //END get id of current project from either request, attribute, or cookie 
              
        
        //get project to edit
        Quote1 p = QuoteService.getInstance().getSingleQuote(id); 
        
        //place sources into form
        DynaValidatorForm qvgatl1 = (DynaValidatorForm) form; 
        
        String sourceId = StandardCode.getInstance().getCookie("quoteAddSourceDocId", request.getCookies());
        String[] sourceIds = new String[1];
        sourceIds[0] = sourceId;
        SourceDoc[] sources = new SourceDoc[sourceIds.length];
        for(int i = 0; i < sourceIds.length; i++) {
            sources[i] = ProjectService.getInstance().getSingleSourceDoc(Integer.valueOf(sourceIds[i]));
        }
               
        request.setAttribute("sourceArray", sources);        
                
        //save the source array for later use in case new target "new" needs to be created
        HttpSession session = request.getSession(false);
        session.setAttribute("sourceArray", sources);
        
        //place project into attribute for display
        request.setAttribute("quote", p);
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
