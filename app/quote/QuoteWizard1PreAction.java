/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
import app.security.*;
import javax.servlet.http.HttpSession;
import org.apache.struts.validator.DynaValidatorForm;

/**
 *
 * @author Niteshwar
 */
public class QuoteWizard1PreAction extends Action{

//QuoteViewGeneralAddTaskLinPreAction.java gets all of the sources
//for this quote and places them into a form





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
        Integer quoteId = 0;
        HttpSession session = request.getSession(false);
        session.setAttribute("quoteViewId", String.valueOf(quoteId));
        session.setAttribute("clientQuoteId", String.valueOf(quoteId));
        DynaValidatorForm qvg = (DynaValidatorForm) form;
        qvg.set("Type","");
        qvg.set("pName","");
        qvg.set("mainComponent","");
        qvg.set("productFreeText","");
        qvg.set("otherTask","");
        qvg.set("approvalTimeEsimate","");
        qvg.set("instruction","");
        qvg.set("Upload",null); 

        //START check for login (security)
        if(!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)



	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}

