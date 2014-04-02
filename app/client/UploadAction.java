//QuoteViewGeneralTradosUploadAction.java adds the new
//trados values to the current lin task.
//It also collects the file names from the .log file
//and adds them to this quote

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
import org.apache.struts.validator.*;
import org.apache.struts.upload.*;
import java.io.*;
import java.util.*;
import app.user.*;
import app.project.*;
import app.standardCode.*;
import app.db.*;
import app.extjs.helpers.ProjectHelper;
import app.workspace.*;
import app.security.*;
import org.json.JSONArray;
import org.json.JSONObject;


public  class UploadAction extends Action {


    // ----------------------------------------------------- Instance Variables


    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log =
        LogFactory.getLog("org.apache.struts.webapp.Example");


    // --------------------------------------------------------- Public Methods


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
       
        //id from request attribute
        String clientId = (String) request.getAttribute("clientViewId");

        //id from cookie
        if(clientId == null) {
            clientId = StandardCode.getInstance().getCookie("clientViewId", request.getCookies());
        }
        System.out.println("clinrt id of ClientViewProjectHistoryAction>>>>>>>>>>>>>>>>>>>>>>"+clientId);

        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("clientViewTab", "Project History"));
       // long end1 = System.currentTimeMillis();
        //System.out.println("old way:"+(end1-start1));
       // long start2 = System.currentTimeMillis();
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        List temp = ProjectHelper.getProjectListForClient(clientId);
        ArrayList projectHistory = new ArrayList();
        for(ListIterator iter = temp.listIterator(); iter.hasNext();) {
                Project p = (Project) iter.next();
                JSONObject jo = ProjectHelper.ProjectToJson2(p);
                projectHistory.add(jo);
            }
        // System.out.println(actResponse.toXML());
        PrintWriter out = response.getWriter();
        out.println(new JSONArray(projectHistory.toArray()));
        out.flush();
       // long end2 = System.currentTimeMillis();
       //System.out.println("Finnished ClientViewProjectHistoryAction!");
	// Forward control to the specified success URI
	//return (mapping.findForward("Success"));
            return null;    }

}
