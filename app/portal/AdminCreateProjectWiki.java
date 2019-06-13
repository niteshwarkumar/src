/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.portal;

import app.admin.AdminService;
import app.project.Project;
import app.project.ProjectService;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import app.util.TemplateUtil;
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

/**
 *
 * @author niteshwar
 */
public class AdminCreateProjectWiki extends Action {

//ProjectAddPreFilesAction.java adds the new
//file (pre trados) to the db and builds the one-to-many
//relationship between quote and file (quote file)
    // ----------------------------------------------------- Instance Variables
    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log
            = LogFactory.getLog("org.apache.struts.webapp.Example");
//    private static final boolean hasDTP;

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
//        TemplateUtil.bDebugMode = Boolean.TRUE;

        String pId = request.getParameter("projectId");
        Project p = ProjectService.getInstance().getSingleProject(Integer.parseInt(pId));
        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));

        

       
        String type = StandardCode.getInstance().noNull(request.getParameter("type"));
        
        if(type.equalsIgnoreCase("DEACTIVATE")){
             PortalUtil.getInstance().removeWiki(p, u);
             return (mapping.findForward("Publish"));
        }else if(type.equalsIgnoreCase("ACTIVATE")){
             PortalUtil.getInstance().generateWiki(p, u);
             return (mapping.findForward("Publish"));
        }
        String noteType = "PROJWIKINOTE";
        if (null != type) {
            noteType += type;
        }
        
         String note = request.getParameter("note");
         String ftp = request.getParameter("ftp");
         

        Wiki wiki = PortalUtil.getInstance().getSingleWikiNotes(p.getProjectId(), noteType);
        if (wiki == null) {
            wiki = new Wiki();
        }
        if(ftp!=null){
            wiki.setFtp(true);
         }
        wiki.setNotes(note);
        wiki.setType(noteType);
        wiki.setProjectId(p.getProjectId());
        AdminService.getInstance().addObject(wiki);
//        PortalUtil.getInstance().generateWiki(p, u);
        // Forward control to the specified success URI
        request.setAttribute("projectId", pId);
        if (StandardCode.getInstance().noNull(type).equalsIgnoreCase("LIN")) {
            return (mapping.findForward("Lin"));
        } else if (StandardCode.getInstance().noNull(type).equalsIgnoreCase("DTP")) {
            return (mapping.findForward("Dtp"));
        }
        return (mapping.findForward("Success"));
    }


}
