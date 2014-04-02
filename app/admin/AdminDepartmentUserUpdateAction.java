/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import app.security.*;
import app.user.Department;
import app.user.DepartmentUser;
import app.user.User;
import app.user.UserService;

/**
 *
 * @author Niteshwar
 */
public class AdminDepartmentUserUpdateAction extends Action {

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


        Department du=null;
        String departmentId=request.getParameter("department");
        try {
              du=UserService.getInstance().getSingleDepartment(Integer.parseInt(departmentId));
        } catch (Exception e) {
            du=UserService.getInstance().getSingleDepartment(1);
        }


        request.setAttribute("departmentId", du.getDepartmentId());
        request.setAttribute("department", du.getDepartment());

        //START check for login (security)
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        String[] datafromJsp = null;
       String boxNo = request.getParameter("boxNo");
        //END check for login (security)
        if (Integer.parseInt(boxNo) == 0) {
            datafromJsp = request.getParameterValues("email2");
                 for (int i = 0; i < datafromJsp.length; i++) {
            try {
                DepartmentUser dus = UserService.getInstance().getSingleDepartmentUser(Integer.parseInt(datafromJsp[i]),Integer.parseInt(departmentId));
                UserService.getInstance().deleteDepartmentUser(dus);

            } catch (Exception e) {
               System.err.println(e.getMessage());
            }

        }
            
        }
        else {
            datafromJsp = request.getParameterValues("email1");
              for (int i = 0; i < datafromJsp.length; i++) {
            try {
                DepartmentUser dus=new DepartmentUser();
                dus.setDepartmentId(Integer.parseInt(departmentId));
                dus.setUserId(Integer.parseInt(datafromJsp[i]));
                UserService.getInstance().addDepartmentUser(dus);

            } catch (Exception e) {
            }

        }
        }

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
