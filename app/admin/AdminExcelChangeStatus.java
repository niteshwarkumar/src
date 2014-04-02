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
/**
 *
 * @author abc
 */
public class AdminExcelChangeStatus  extends Action {

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
            HttpServletResponse response) throws Exception {
        
        String statusStr = request.getParameter("status");
        String id = request.getParameter("id");
        Excelnetissue ei = AdminService.getInstance().getSingleExcelnetIssue(Integer.parseInt(id)); 
        if(statusStr.equalsIgnoreCase("0")){
            AdminService.getInstance().delete(ei);
        return (mapping.findForward("Success1"));
        }
        
        Integer status=Integer.parseInt(statusStr) + 1;        
        ei.setStatus(status);
        AdminService.getInstance().saveOrUpdateExcelnetissue(ei);
        
        return (mapping.findForward("Success"+statusStr));
    }
}
