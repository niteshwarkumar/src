//ProjectAddPreFilesAction.java adds the new
//file (pre trados) to the db and builds the one-to-many
//relationship between quote and file (quote file)

package app.project;

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
import app.quote.*;
import app.standardCode.*;
import app.db.*;
import app.workspace.*;
import app.security.*;


public final class ProjectAddPreFilesAction extends Action {


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
          
        //get new file
        String fileName = request.getParameter("fileName");
        String locationType = request.getParameter("locationType");
        String note = request.getParameter("note");
        
        File f = new File();
        f.setFileName(fileName);
        f.setLocationType(locationType);
        f.setNote(note);
        f.setBeforeAnalysis("true");
        
        StringBuffer sb = (StringBuffer) request.getSession(false).getAttribute("fileLocations");
        if(sb == null) {
            sb = new StringBuffer("");
            sb.append("%0AFile Location Information: ");
        }
        if(f.getFileName().length() > 0) {
            sb.append("%0A%20File Name: " + StandardCode.getInstance().noNull(f.getFileName()));
            sb.append("%0A%20File Location: " + StandardCode.getInstance().noNull(f.getLocationType()));
            sb.append("%0A%20File Notes: " + StandardCode.getInstance().noNull(f.getNote()) + "%0A");
        }        
       
        request.getSession(false).setAttribute("fileLocations", sb);
        
       	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
