/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import java.text.SimpleDateFormat;
import java.util.Date;
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
public class QMSRiskMitigationUpdateAction extends Action {

    private Log log
            = LogFactory.getLog("org.apache.struts.webapp.Example");

    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        String act = request.getParameter("act");
        RiskMitigation rm = null;
        if (act.equalsIgnoreCase("new")) {
            rm = new RiskMitigation();
        } else if (act.equalsIgnoreCase("delete")) {
            Integer id = Integer.parseInt(request.getParameter("id"));
            rm = QMSService.getInstance().getSingleRiskMitigation(id);
            QMSServiceDelete.getInstance().deleteRiskMitigation(rm);
            return (mapping.findForward("Success"));
        } else {

            Integer id = Integer.parseInt(request.getParameter("id"));
//            request.setAttribute("id", id);
            rm = QMSService.getInstance().getSingleRiskMitigation(id);
            request.setAttribute("mitigation", rm);
        }

        String probId = request.getParameter("probability");
        String seveId = request.getParameter("severity");
        String probId1 = request.getParameter("probability1");
        String seveId1 = request.getParameter("severity1");
        RiskProbability rp = null;
        RiskSeverity rs = null;

        if (null != probId) {
            if (!probId.equalsIgnoreCase("")) {
                rp = QMSService.getInstance().getSingleRiskProbability(Integer.parseInt(probId));
            }
        }

        if (null != seveId) {
            if (!seveId.equalsIgnoreCase("")) {
                rs = QMSService.getInstance().getSingleRiskSeverity(Integer.parseInt(seveId));
            }
        }
        rm.setProbability(rp);
        rm.setSeverity(rs);
        if ((null != rs) && (null != rp)) {
            RiskHazardIndex rhi = QMSService.getInstance().getSingleRiskAcceptabilityIndex(rs.getId(), rp.getId());
            if (null != rhi) {
                rm.setIndexcolor(rhi.getIndexcolor());
                rm.setHazardnumber(rhi.getRiskindex());
            }
            if (null != probId1) {
                if (!probId1.equalsIgnoreCase("")) {
                    rp = QMSService.getInstance().getSingleRiskProbability(Integer.parseInt(probId1));
                }
            }

            if (null != seveId1) {
                if (!seveId1.equalsIgnoreCase("")) {
                    rs = QMSService.getInstance().getSingleRiskSeverity(Integer.parseInt(seveId1));
                }
            }
            if ((null != rs) && (null != rp)) {
                rhi = QMSService.getInstance().getSingleRiskAcceptabilityIndex(rs.getId(), rp.getId());
                if (null != rhi) {
                    rm.setIndexcolor1(rhi.getIndexcolor());
                    rm.setHazardnumber1(rhi.getRiskindex());
                }

                rm.setHazard(request.getParameter("hazard"));
                rm.setDescription(request.getParameter("description"));
                rm.setProbability1(rp);
                rm.setSeverity1(rs);
                rm.setExistingmitigation(request.getParameter("existingMitigationFactors"));
                rm.setNonexistingmitigation(request.getParameter("nonExistingMitigationFactors"));
                rm.setComment(request.getParameter("comments"));
                rm.setNotePre(request.getParameter("notePre"));
                rm.setApprove1(request.getParameter("approve1"));
                rm.setApprove2(request.getParameter("approve2"));
        if(null != request.getParameter("approveDate")&& !request.getParameter("approveDate").equalsIgnoreCase("")){

            try{
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        rm.setDate(sdf.parse(request.getParameter("approveDate")));
            }catch(Exception e){
              SimpleDateFormat  sdf = new SimpleDateFormat("MM/dd/yyyy");
                        rm.setDate(sdf.parse(request.getParameter("approveDate")));
            
            }
             }else{
                        rm.setDate(null);
                    }

             
            }

        }
        QMSServiceAddUpdate.getInstance().addUpdateRiskMitigation(rm);

        return (mapping.findForward("Success"));
    }
}
