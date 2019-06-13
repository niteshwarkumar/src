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
public class RiskProabilitySeverityUpdate extends Action {

    private Log log
            = LogFactory.getLog("org.apache.struts.webapp.Example");

    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        String act = request.getParameter("act");
        String service = request.getParameter("service");
        if (service.equalsIgnoreCase("severity")) {
            RiskSeverity rs = null;
            if (act.equalsIgnoreCase("new")) {
                rs = new RiskSeverity();
            } else if (act.equalsIgnoreCase("delete")) {
                Integer id = Integer.parseInt(request.getParameter("id"));
                rs = QMSService.getInstance().getSingleRiskSeverity(id);
                QMSServiceDelete.getInstance().deleteRiskSeverity(rs);
                return (mapping.findForward("Success"));
            } else {

                Integer id = Integer.parseInt(request.getParameter("id"));
                rs = QMSService.getInstance().getSingleRiskSeverity(id);

            }
            rs.setCategory(request.getParameter("code"));
            rs.setDescription(request.getParameter("description"));
            rs.setHazard(request.getParameter("riskText"));
            QMSServiceAddUpdate.getInstance().addUpdateRiskSeverity(rs);
        } else if (service.equalsIgnoreCase("probability")) {
            RiskProbability rp = null;
            if (act.equalsIgnoreCase("new")) {
                rp = new RiskProbability();
            } else if (act.equalsIgnoreCase("delete")) {
                Integer id = Integer.parseInt(request.getParameter("id"));
                rp = QMSService.getInstance().getSingleRiskProbability(id);
                QMSServiceDelete.getInstance().deleteRiskProbability(rp);
                return (mapping.findForward("Success"));
            } else {

                Integer id = Integer.parseInt(request.getParameter("id"));
                rp = QMSService.getInstance().getSingleRiskProbability(id);

            }
            rp.setLevel(request.getParameter("code"));
            rp.setDescription(request.getParameter("description"));
            rp.setProbability(request.getParameter("riskText"));
            QMSServiceAddUpdate.getInstance().addUpdateRiskProbability(rp);
        }

        return (mapping.findForward("Success"));
    }
}
