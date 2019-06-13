//QuoteViewGeneralAddSource1Action.java adds the
//new source to the existing quote

package app.quote;

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
import java.util.*;
import app.project.*;
import app.standardCode.*;
import app.security.*;


public final class QuoteViewGeneralAddSource1Action extends Action {


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
        
        //get current quote
        Quote1 q = QuoteService.getInstance().getSingleQuote(id); 
                           
        //values for adding a new source
        DynaValidatorForm qae4 = (DynaValidatorForm) form;        
        String sourceLanguage = (String) (qae4.get("sourceLanguage"));  
        
        //new SourceDoc object for this quote
        SourceDoc sd = new SourceDoc(new HashSet());
        
        //update values
        sd.setLanguage(sourceLanguage);
        //System.out.println("sddddddddddddd"+sd.getSourceDocId());

        Client_Quote cq=QuoteService.getInstance().get_SingleClientQuote(id);
        
        //add SourceDoc to db building one-to-many relationship between quote and source
        Integer sdId = QuoteService.getInstance().clientAddSourceWithQuote(q, sd,cq);
        
        //place sourceDoc into cookie; this will be used later in for adding targetDocs
        response.addCookie(StandardCode.getInstance().setCookie("quoteViewGeneralAddSourceId", String.valueOf(sdId)));
        request.setAttribute("quoteAddLanguage",  sourceLanguage); //used in form for display
        
        //place quote into attribute for display
        request.setAttribute("quote", q);
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
