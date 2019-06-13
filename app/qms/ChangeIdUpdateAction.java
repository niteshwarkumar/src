/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import app.admin.AdminService;
import app.admin.OptionsSelect;
import app.security.SecurityService;
import app.standardCode.DateService;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import java.util.Date;
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
import org.apache.struts.validator.DynaValidatorForm;

/**
 *
 * @author niteshwar
 */
public class ChangeIdUpdateAction extends Action {

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
        DynaValidatorForm upd = (DynaValidatorForm) form;

        String number = request.getParameter("number");
        String close_owner = request.getParameter("close_owner_val");
        String close_supervisor = request.getParameter("close_supervisor_val");
        String scdescription = (String) upd.get("scdescription");
        String analysis_desired = (String) upd.get("analysis_desired");
        String analysis_nondesired = (String) upd.get("analysis_nondesired");
        String analysis_action = (String) upd.get("analysis_action");
        String prn_onetime = (String) upd.get("prn_onetime");
        String approval_note = (String) upd.get("approval_note");
        String prn_ongoing = (String) upd.get("prn_ongoing");
        String target = (String) upd.get("target");
        String approval_date = (String) upd.get("approval_date");
        String approvalby = (String) upd.get("approvalby");

        String close_comment = (String) upd.get("close_comment");
        String actionp1 = (String) upd.get("actionp1");
        String actionp2 = (String) upd.get("actionp2");
        String actionp3 = (String) upd.get("actionp3");
        String actionp4 = (String) upd.get("actionp4");
        String responsiblep1 = (String) upd.get("responsiblep1");
        String responsiblep2 = (String) upd.get("responsiblep2");
        String responsiblep3 = (String) upd.get("responsiblep3");
        String responsiblep4 = (String) upd.get("responsiblep4");
        String effectivenessp1 = (String) upd.get("effectivenessp1");
        String effectivenessp2 = (String) upd.get("effectivenessp2");
        String effectivenessp3 = (String) upd.get("effectivenessp3");
        String effectivenessp4 = (String) upd.get("effectivenessp4");
        String approverp1 = (String) upd.get("approverp1");
        String approverp2 = (String) upd.get("approverp2");
        String approverp3 = (String) upd.get("approverp3");
        String approverp4 = (String) upd.get("approverp4");
        String approvalp1 = (String) upd.get("approvalp1");
        String approvalp2 = (String) upd.get("approvalp2");
        String approvalp3 = (String) upd.get("approvalp3");
        String approvalp4 = (String) upd.get("approvalp4");
        String duedatep1 = (String) upd.get("duedatep1");
        String duedatep2 = (String) upd.get("duedatep2");
        String duedatep3 = (String) upd.get("duedatep3");
        String duedatep4 = (String) upd.get("duedatep4");
        String doip1 = (String) upd.get("doip1");
        String doip2 = (String) upd.get("doip2");
        String doip3 = (String) upd.get("doip3");
        String doip4 = (String) upd.get("doip4");
        
        String[] options = request.getParameterValues("targetAud");
        String[] prsaff = request.getParameterValues("prsaff");
//        ArrayUtils.addAll
       
        StrategicChange sc = QMSService.getInstance().getSingleStrategicChange(number);

        StrategicChange_Id scid = QMSService.getInstance().getSingleStrategicChange_Id(number);
        AdminService.getInstance().removeOptionsChange(scid.getSid());
        if (options != null) {
            for (String opt : options) {
                OptionsSelect os = new OptionsSelect();
                os.setScreen(scid.getSid());
                os.setOptionId(Integer.parseInt(opt));
                AdminService.getInstance().addOptionSelect(os);
            }
        }
        if (prsaff != null) {
            for (String opt : prsaff) {
                OptionsSelect os = new OptionsSelect();
                os.setScreen(scid.getSid());
                os.setOptionId(Integer.parseInt(opt));
                AdminService.getInstance().addOptionSelect(os);
            }
        }
        
        try {
            if (approval_date.length() > 0) { //if present
                scid.setApproval_date(DateService.getInstance().convertDate(approval_date).getTime());
            }
        } catch (Exception e) {
            ////System.out.println("Date Errooooorr " + e.getMessage());
        }
        if (scid == null) {
            scid = new StrategicChange_Id();
            scid.setNumber(number);
        }

        scid.setAnalysis_action(analysis_action);
        scid.setAnalysis_desired(analysis_desired);
        scid.setAnalysis_nondesired(analysis_nondesired);
        scid.setApproval_note(approval_note);
        if (!StandardCode.getInstance().noNull(close_owner).isEmpty()  ) {
//            User user = UserService.getInstance().getSingleUser(close_owner);
            scid.setClose_owner(close_owner);
//            if (sc.getReportedby().equalsIgnoreCase(user.getFirstName() + " " + user.getLastName())) {
//                scid.setClose_owner(close_owner);
//            }
        }
        if (!StandardCode.getInstance().noNull(close_supervisor).isEmpty()) {
            scid.setClose_supervisor(close_supervisor);
        }
        scid.setDescription(scdescription);
        scid.setPrn_onetime(prn_onetime);
        scid.setPrn_ongoing(prn_ongoing);
        scid.setApprovalby(approvalby);
        scid.setTarget(target);
        scid.setActionp1(actionp1);
        scid.setActionp2(actionp2);
        scid.setActionp3(actionp3);
        scid.setActionp4(actionp4);
        scid.setResponsiblep1(responsiblep1);
        scid.setResponsiblep2(responsiblep2);
        scid.setResponsiblep3(responsiblep3);
        scid.setResponsiblep4(responsiblep4);
        scid.setEffectivenessp1(effectivenessp1);
        scid.setEffectivenessp2(effectivenessp2);
        scid.setEffectivenessp3(effectivenessp3);
        scid.setEffectivenessp4(effectivenessp4);
        scid.setApproverp1(approverp1);
        scid.setApproverp2(approverp2);
        scid.setApproverp3(approverp3);
        scid.setApproverp4(approverp4);
        scid.setClose_comment(close_comment);
        scid.setApprovalp1(approvalp1);
        scid.setApprovalp2(approvalp2);
        scid.setApprovalp3(approvalp3);
        scid.setApprovalp4(approvalp4);
        try {
            if (duedatep1.length() > 0) { //if present
                scid.setDuedatep1(DateService.getInstance().convertDate(duedatep1).getTime());
            }
        } catch (Exception e) {
            ////System.out.println("Date Errooooorr " + e.getMessage());
        }
        try {
            if (duedatep2.length() > 0) { //if present
                scid.setDuedatep2(DateService.getInstance().convertDate(duedatep2).getTime());
            }
        } catch (Exception e) {
            ////System.out.println("Date Errooooorr " + e.getMessage());
        }
        try {
            if (duedatep3.length() > 0) { //if present
                scid.setDuedatep3(DateService.getInstance().convertDate(duedatep3).getTime());
            }
        } catch (Exception e) {
            ////System.out.println("Date Errooooorr " + e.getMessage());
        }
        try {
            if (duedatep4.length() > 0) { //if present
                scid.setDuedatep4(DateService.getInstance().convertDate(duedatep4).getTime());
            }
        } catch (Exception e) {
            ////System.out.println("Date Errooooorr " + e.getMessage());
        }
        try {
            if (doip1.length() > 0) { //if present
                scid.setDoip1(DateService.getInstance().convertDate(doip1).getTime());
            }
        } catch (Exception e) {
            ////System.out.println("Date Errooooorr " + e.getMessage());
        }
        try {
            if (doip2.length() > 0) { //if present
                scid.setDoip2(DateService.getInstance().convertDate(doip2).getTime());
            }
        } catch (Exception e) {
            ////System.out.println("Date Errooooorr " + e.getMessage());
        }
        try {
            if (doip3.length() > 0) { //if present
                scid.setDoip3(DateService.getInstance().convertDate(doip3).getTime());
            }
        } catch (Exception e) {
            ////System.out.println("Date Errooooorr " + e.getMessage());
        }
        try {
            if (doip4.length() > 0) { //if present
                scid.setDoip4(DateService.getInstance().convertDate(doip4).getTime());
            }
        } catch (Exception e) {
            ////System.out.println("Date Errooooorr " + e.getMessage());
        }

        QMSServiceAddUpdate.getInstance().saveStrategicChange_Id(scid);

        return (mapping.findForward("Success"));

    }
}
