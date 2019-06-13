/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author niteshwar
 */
public class QMSRiskMitigationPre extends Action {

    private Log log =
            LogFactory.getLog("org.apache.struts.webapp.Example");

    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {
        
        String act = request.getParameter("act");
        request.setAttribute("act", act);
        if(!act.equalsIgnoreCase("new"))
        {
            Integer id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("id", id);
            RiskMitigation rm = QMSService.getInstance().getSingleRiskMitigation(id);
            request.setAttribute("mitigation", rm);
            
        }
       return (mapping.findForward("Success"));
    }
}
