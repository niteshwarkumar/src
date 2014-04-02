//ResourceViewLanguagesUpdateAction.java gets updated language-pair info
//from a form.  It then uploads the new values for
//this resource to the db

package app.resource;

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
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.*;
import java.io.*;
import java.util.*;
import app.user.*;
import app.client.*;
import app.db.*;
import app.workspace.*;
import app.security.*;


public final class ResourceViewLanguagesUpdateAction extends Action {


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
        
	//which resource to update from hidden value in form
        String id = request.getParameter("resourceViewId");  
        //get the resource to be updated from db
        Resource r = ResourceService.getInstance().getSingleResource(Integer.valueOf(id));
                
        //values for update from form; change what is stored in db to these values
        DynaValidatorForm rvl = (DynaValidatorForm) form;
        
        LanguagePair[] languagePairs = (LanguagePair[]) rvl.get("languagePairs");
        
        //update each languagePair
        for(int i = 0; i < languagePairs.length; i++) {
            //set the check box to correct boolean value
            if(request.getParameter("languagePairs[" + String.valueOf(i) + "].nativeLanguage") != null) {
                languagePairs[i].setNativeLanguage(true);
            }
            else {
                languagePairs[i].setNativeLanguage(false);
            }
            ResourceService.getInstance().updateLanguagePair(languagePairs[i]);
        }
               
        //place user id into request for display
        request.setAttribute("resource", r);
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
