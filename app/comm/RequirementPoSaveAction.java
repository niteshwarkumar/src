/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.comm;

import app.qms.QMSServiceDelete;
import app.security.SecurityService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 *
 * @author niteshwar
 */
public class RequirementPoSaveAction extends Action {

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
        // save errors
        ActionMessages errors = new ActionMessages();

        //START check for login (security)
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)

        String[] requirements = request.getParameterValues("requirement");
        String projectId = request.getParameter("projectId");
        String type = request.getParameter("tab");
       
        CommService.getInstance().deleteReqPO(Integer.parseInt(projectId), type);
    
        if(requirements != null){
            for(String requirement : requirements){
                RequirementPO reqPo = new RequirementPO();
                reqPo.setRequirementId(Integer.parseInt(requirement));
                reqPo.setProjectId(Integer.parseInt(projectId));
                reqPo.setType(type);
                CommService.getInstance().addReqPo(reqPo);

            }
        }
        request.setAttribute("projectAll", "Y");
//        request.setAttribute("tab", tab);
//        request.setAttribute("type", type);
//        request.setAttribute("projectId", projectId);
//        request.setAttribute("clientId", clientId);
        return (mapping.findForward("Success"));
    }
}
