/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.admin;

import app.security.SecurityService;
import app.standardCode.StandardCode;
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
public class AdminMiscUpdateAction extends Action {

    // ----------------------------------------------------- Instance Variables
    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log
            = LogFactory.getLog("org.apache.struts.webapp.Example");

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
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)

        //PRIVS check that admin user is viewing this page
        if (!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "admin")) {
            return (mapping.findForward("accessDenied"));
        }//END PRIVS check that admin user is viewing this page

        String nameText = "";
        String description = "";
        String color = "";
        String type = "";

        String adminMiscId = "";
        AdminMisc adminMisc = null;

        try {
            adminMiscId = request.getParameter("adminMiscId");
        } catch (Exception e) {
        }

        if (adminMiscId == null) {
            adminMisc = new AdminMisc();

        } else {
            adminMisc = UserService.getInstance().getSingleAdminMiscById(Integer.parseInt(adminMiscId));
        }
        if(adminMisc == null){
        adminMisc = new AdminMisc();
        }

        try {
            nameText = request.getParameter("nameText");
        } catch (Exception e) {
        }
        try {
            description = request.getParameter("description");
        } catch (Exception e) {
        }
        try {
            color = request.getParameter("color");
        } catch (Exception e) {
        }
        try {
            type = request.getParameter("type");
        } catch (Exception e) {
        }

        adminMisc.setDescription(description);
        adminMisc.setBgColor(color);
        adminMisc.setType(type);
        adminMisc.setValue(nameText);

        UserService.getInstance().addUpdateAdminMisc(adminMisc);

        // Forward control to the specified success URI
        if(!type.isEmpty())
            return (mapping.findForward(type));
        return (mapping.findForward("Success"));
    }
}
