//AdminMiscGeneralSpecificViewAction.java gets the specific
//industries ready for editing

package app.admin;

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
import app.client.*;
import app.standardCode.*;
import app.security.*;


public final class AdminMiscGeneralSpecificViewAction extends Action {


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
	     
        //PRIVS check that admin user is viewing this page 
        if(!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "admin")) {
            return (mapping.findForward("accessDenied"));
        }//END PRIVS check that admin user is viewing this page
        
        //get specific industries
        String generalId = request.getParameter("id");
        Industry i = null;
        if(generalId != null) {
            i = ClientService.getInstance().getSingleIndustry(Integer.valueOf(generalId));
        }
        else {
            generalId = StandardCode.getInstance().getCookie("generalId", request.getCookies());
            i = ClientService.getInstance().getSingleIndustry(Integer.valueOf(generalId));
        }           
        Set specifics = i.getSpecificIndustries();
        
        //place specific industries into form
        DynaValidatorForm amg = (DynaValidatorForm) form;
        SpecificIndustry[] specificsArray = (SpecificIndustry[]) specifics.toArray(new SpecificIndustry[0]);
        amg.set("specifics", specificsArray);          
        
        //set industry id into cookie and request for later use
        response.addCookie(StandardCode.getInstance().setCookie("generalId", generalId));
        request.getSession(false).setAttribute("adminGeneral", i);
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
