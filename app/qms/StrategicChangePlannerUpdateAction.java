/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import app.security.SecurityService;
import app.standardCode.DateService;
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
public class StrategicChangePlannerUpdateAction  extends Action {

    private Log log = LogFactory.getLog("org.apache.struts.webapp.Example");

    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // Extract attributes we will need
        MessageResources messages = getResources(request);

        // save errors
        ActionMessages errors = new ActionMessages();

        //START check for login (security)
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }

        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
        //END check for login (security)
      

        String id = request.getParameter("id");
        String scid = request.getParameter("scid");
         String number = request.getParameter("number");
        request.setAttribute("number", number);
        String action = request.getParameter("action");
        String responsible = request.getParameter("responsible");
        String effectiveness = request.getParameter("effect");
        String approver = request.getParameter("approver");
        String approval = request.getParameter("approval");
        String duedate = request.getParameter("duedate");
        String doi = request.getParameter("doi");
        
         String delete = request.getParameter("delete");
         

        StrategicChange_Planner scpl;
        if(id == null){
            scpl = new StrategicChange_Planner();
            scpl.setScid(Integer.parseInt(scid));
        }else{
            scpl = QMSService.getInstance().getSingleStrategicChange_Planner(Integer.parseInt(id));
        }
        
        
        if (scpl == null) {
            scpl = new StrategicChange_Planner();
            scpl.setScid(Integer.parseInt(scid));
        }

        if(StandardCode.getInstance().noNull(delete).equalsIgnoreCase("D")){
            QMSServiceAddUpdate.getInstance().deleteStrategicChange_Planner(scpl);
         return (mapping.findForward("Success")); 
        }
        scpl.setAction(action);
        
        scpl.setResponsible(Integer.parseInt(responsible));
        scpl.setEffectiveness(effectiveness);
        scpl.setApprover(Integer.parseInt(approver));
        if (StandardCode.getInstance().noNull(approval).equalsIgnoreCase("on")){
             scpl.setApproval(true);
        }else{
            scpl.setApproval(false);
        }
       
        try {
            if (duedate.length() > 0) { //if present
                scpl.setDuedate(DateService.getInstance().convertDate(duedate).getTime());
            }
        } catch (Exception e) {
            ////System.out.println("Date Errooooorr " + e.getMessage());
        }
        
        try {
            if (doi.length() > 0) { //if present
                scpl.setDoi(DateService.getInstance().convertDate(doi).getTime());
            }
        } catch (Exception e) {
            ////System.out.println("Date Errooooorr " + e.getMessage());
        }
        
        QMSServiceAddUpdate.getInstance().saveStrategicChange_Planner(scpl);

        return (mapping.findForward("Success"));

    }
}
