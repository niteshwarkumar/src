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
public class RiskProbabilityUpdate  extends Action {

    private Log log =
            LogFactory.getLog("org.apache.struts.webapp.Example");

    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {
        
           
        Integer severity = Integer.parseInt(request.getParameter("severity"));
        Integer probability = Integer.parseInt(request.getParameter("probability"));
        Integer riskIndex = Integer.parseInt(request.getParameter("riskIndex"));
        String indexcolor = request.getParameter("indexcolor");

//        RiskHazardIndex rhi = new RiskHazardIndex();
        
          RiskHazardIndex  rhi = QMSService.getInstance().getSingleRiskAcceptabilityIndex(severity, probability);
        if(null == rhi){
            rhi = new RiskHazardIndex();
        }
        
        rhi.setIndexcolor(indexcolor);
        rhi.setProbability(probability);
        rhi.setRiskindex(riskIndex);
        rhi.setSeverity(severity);
        
        QMSServiceAddUpdate.getInstance().addUpdateRiskHazardIndex(rhi);
         
       return (mapping.findForward("Success"));
  
    }
}
