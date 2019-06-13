/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.portal;

import app.extjs.actions.DocService;
import app.extjs.vo.Upload_Doc;
import app.project.Project;
import app.project.ProjectService;
import app.security.SecurityService;
import app.user.User;
import app.user.UserService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author niteshwar
 */
public class AdminViewExcelWikiAction extends Action {

//ProjectAddPreFilesAction.java adds the new
//file (pre trados) to the db and builds the one-to-many
//relationship between quote and file (quote file)
    // ----------------------------------------------------- Instance Variables
    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log = LogFactory.getLog("org.apache.struts.webapp.Example");

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
    @Override
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
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));

        JSONArray docs = new JSONArray();
        List<Upload_Doc> uDocList = DocService.getInstance().getDocList("WIKIPROJ");
        uDocList.forEach((Upload_Doc uD) -> {
            Upload_Doc uDoc = (Upload_Doc) uD;
            JSONObject jo = new JSONObject();
            Project p = ProjectService.getInstance().getSingleProject(uDoc.getProjectID());
            jo.put("projectId", p.getProjectId());
            jo.put("pNumber", p.getNumber() + p.getCompany().getCompany_code());
            if(uDoc.getTitle().equalsIgnoreCase("DEACTIVATED"))
                jo.put("pStatus", "<font style=\"color:red\">Not active</font>");
            else
                jo.put("pStatus", "<font style=\"color:green\">Active</font>");    
            jo.put("pm", p.getPm());
            jo.put("ae", p.getAe()); 
            jo.put("wikiUrl", PortalUtil.WIKI_URL_FOLDER+uDoc.getPath());
            docs.put(jo);
        });

        // Forward control to the specified success URI
        request.setAttribute("docs", docs);

        return (mapping.findForward("Success"));
    }
}
