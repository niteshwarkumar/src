//ClientViewAccountingAddPreRateAction.java prepares the form
//for adding a new client other rate object

package app.client;

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


public final class ClientViewAccountingAddPreRateAction extends Action {


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
        
        //START get id of current client from either request, attribute, or cookie 
        //id of client from request
	String clientId = null;
	clientId = request.getParameter("clientViewId");
        
        //check attribute in request
        if(clientId == null) {
            clientId = (String) request.getAttribute("clientViewId");
        }
        
        //id of client from cookie
        if(clientId == null) {            
            clientId = StandardCode.getInstance().getCookie("clientViewId", request.getCookies());
        }

        //default client to first if not in request or cookie
        if(clientId == null) {
                List results = ClientService.getInstance().getClientList();
                Client first = (Client) results.get(0);
                clientId = String.valueOf(first.getClientId());
            }            
        
        Integer id = Integer.valueOf(clientId);
        
        //END get id of current client from either request, attribute, or cookie 
              
        
        //get client to edit
        Client c = ClientService.getInstance().getSingleClient(id); 
        
        //place new client language pair object into this form for editing
        DynaValidatorForm qvg = (DynaValidatorForm) form;
        ClientOtherRate cor = new ClientOtherRate();
        qvg.set("clientOtherRateNew", cor);    
        
        //place this project into request for further display in jsp page
        request.setAttribute("client", c);
                        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
