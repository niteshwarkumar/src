//QuoteViewNotesUpdateAction.java gets the
//updated notes and loads them to db

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
import app.project.*;
import app.standardCode.*;
import app.db.*;
import app.workspace.*;
import app.security.*;


public final class QuoteViewNotesUpdateAction extends Action {


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
	
        //START get id of current quote from either request, attribute, or cookie 
        //id of quote from request
	String quoteId = null;
	quoteId = request.getParameter("quoteViewId");
        
        //check attribute in request
        if(quoteId == null) {
            quoteId = (String) request.getAttribute("quoteViewId");
        }
        
        //id of quote from cookie
        if(quoteId == null) {            
            quoteId = StandardCode.getInstance().getCookie("quoteViewId", request.getCookies());
        }
        
        
        Integer id = Integer.valueOf(quoteId);
        
        //END get id of current quote from either request, attribute, or cookie               
        
        //get quote to update
        Quote1 q = QuoteService.getInstance().getSingleQuote(id); 
              
        //updated values of the quote
       // DynaValidatorForm qvn = (DynaValidatorForm) form;
        //String note = (String) qvn.get("note");
        //the new note data from the form
        String note = request.getParameter("note");
        //update the new values
        q.setNote(note);
        
        if(q.getStatus().equalsIgnoreCase("approved")){
        if(q.getNote() != null && q.getProject()!=null && (q.getProject().getNotes()==null||"".equals(q.getProject().getNotes()))){
  
            q.getProject().setNotes(q.getNote());
            ProjectService.getInstance().updateProject(q.getProject());
        }}
        
        if(request.getSession(false).getAttribute("username")!=null){
             q.setLastModifiedById((String)request.getSession(false).getAttribute("username")); 
             q.setLastModifiedByTS(new Date());
        }

        //update to db
        QuoteService.getInstance().updateQuote(q);      
        
        //place quote into attribute for display
        request.setAttribute("quote", q);
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}