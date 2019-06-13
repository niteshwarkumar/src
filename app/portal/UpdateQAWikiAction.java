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
public class UpdateQAWikiAction  extends Action {

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
        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));


        String pId = request.getParameter("projectId");
        String id = request.getParameter("id");
        String wikiId = request.getParameter("wikiId");
        Project p = ProjectService.getInstance().getSingleProject(Integer.parseInt(pId));

        String type = request.getParameter("type");
        String ref = request.getParameter("ref");
        String ques = request.getParameter("ques");
        String ans = request.getParameter("ans");
        Wiki wiki=new Wiki();
        if(null != id)
            wiki= PortalUtil.getInstance().getSingleWikiQA(Integer.parseInt(id), type);
        if(wiki==null){
            wiki=new Wiki();
        }
        wiki.setType(type);
        wiki.setAns(ans);
        wiki.setQues(ques);
        wiki.setReference(ref);
        wiki.setProjectId(p.getProjectId());
        AdminService.getInstance().addObject(wiki);
        
        request.setAttribute("projectId", pId);
        if(StandardCode.getInstance().noNull(type).equalsIgnoreCase("WIKIGENLIN")){
            return (mapping.findForward("Lin"));
        }else if(StandardCode.getInstance().noNull(type).equalsIgnoreCase("WIKIGENDTP")){
            return (mapping.findForward("Dtp"));
        }
        return (mapping.findForward("Success"));
    }


}

