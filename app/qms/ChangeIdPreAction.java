/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import app.security.SecurityService;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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
public class ChangeIdPreAction extends Action {

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
        //END check for login (security)
        List results = new ArrayList();
        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));

        String number = request.getParameter("number");
        if (number == null) {
            number = request.getParameter("id");
        }
        if (number == null) {
            number = (String)request.getAttribute("number");
        }
        StrategicChange_Id scId = QMSService.getInstance().getSingleStrategicChange_Id(number);
        if(scId == null){
            scId = new StrategicChange_Id();
            scId.setNumber(number);
            QMSServiceAddUpdate.getInstance().saveStrategicChange_Id(scId);
        }
        request.setAttribute("scid", scId.getSid());
        StrategicChange sc = QMSService.getInstance().getSingleStrategicChange(number);

        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");

//        Date date = ...;  // wherever you get this
//DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
//String text = df.format(date);
////System.out.println(text);
        DynaValidatorForm uvg = (DynaValidatorForm) form;

        try {
            uvg.set("number", StandardCode.getInstance().noNull(sc.getNumber()));
        } catch (Exception e) {
            uvg.set("number", "");
        }
        try {
            uvg.set("cdate", ""+sc.getCdate());
        } catch (Exception e) {
            uvg.set("cdate", "");
        }
        try {
            uvg.set("employee", StandardCode.getInstance().noNull(sc.getEmployee()));
        } catch (Exception e) {
            uvg.set("employee", "");
        }

        try {
            uvg.set("description", StandardCode.getInstance().noNull(sc.getDescription()));
        } catch (Exception e) {
            uvg.set("description", "");
        }
        try {
            uvg.set("reportedby", StandardCode.getInstance().noNull(sc.getReportedby()));
//            request.setAttribute("reportedby", StandardCode.getInstance().noNull(capa.getReportedby()));
        } catch (Exception e) {
            uvg.set("reportedby", "");
//            request.setAttribute("reportedby","");
        }

        try {
            uvg.set("status", StandardCode.getInstance().noNull(sc.getStatus()));

        } catch (Exception e) {
            uvg.set("status", "");

        }

        try {
            uvg.set("scdescription", StandardCode.getInstance().noNull(scId.getDescription()));
        } catch (Exception e) {
            uvg.set("scdescription", "");
        }

        try {
            uvg.set("target", StandardCode.getInstance().noNull(scId.getTarget()));
        } catch (Exception e) {
            uvg.set("target", "");
        }

        try {
            uvg.set("analysis_desired", StandardCode.getInstance().noNull(scId.getAnalysis_desired()));
        } catch (Exception e) {
            uvg.set("analysis_desired", "");
        }
        try {
            uvg.set("analysis_nondesired", StandardCode.getInstance().noNull(scId.getAnalysis_nondesired()));
        } catch (Exception e) {
            uvg.set("analysis_nondesired", "");
        }

        try {
            uvg.set("analysis_action", StandardCode.getInstance().noNull(scId.getAnalysis_action()));
        } catch (Exception e) {
            uvg.set("analysis_action", "");
        }

        try {
            uvg.set("prn_onetime", StandardCode.getInstance().noNull(scId.getPrn_onetime()));
        } catch (Exception e) {
            uvg.set("prn_onetime", "");
        }
        try {
            uvg.set("prn_ongoing", StandardCode.getInstance().noNull(scId.getPrn_ongoing()));
        } catch (Exception e) {
            uvg.set("prn_ongoing", "");
        }

        try {
            if (scId.getApproval_date() == null) {
                uvg.set("approval_date", "");
            } else {
                uvg.set("approval_date", "" + df.format(scId.getApproval_date()));
            }
        } catch (Exception e) {
            uvg.set("approval_date", "");
        }
        try {
            uvg.set("approval_note", StandardCode.getInstance().noNull(scId.getApproval_note()));
        } catch (Exception e) {
            uvg.set("approval_note", "");
        }
        try {
            uvg.set("approval", StandardCode.getInstance().noNull(scId.getApproval()));
        } catch (Exception e) {
            uvg.set("approval", "");
        }
        try {
            uvg.set("approvalby", StandardCode.getInstance().noNull(scId.getApprovalby()));
        } catch (Exception e) {
            uvg.set("approvalby", "");
        }
        try {
            uvg.set("approval_note", StandardCode.getInstance().noNull(scId.getApproval_note()));
        } catch (Exception e) {
            uvg.set("approval_note", "");
        }
        try {
            uvg.set("close_owner", StandardCode.getInstance().noNull(scId.getClose_owner()));
        } catch (Exception e) {
            uvg.set("close_owner", "");
        }
        String close_owner = "", close_supervisor = "";

        if (!StandardCode.getInstance().noNull(scId.getClose_owner()).isEmpty()) {
            User user = UserService.getInstance().getSingleUser(scId.getClose_owner());
            if(!StandardCode.getInstance().noNull(user.getSignature()).isEmpty())
                close_owner = "https://excelnet.xltrans.com/logo/images/" + user.getSignature();
            else
                close_owner = user.getFirstName()+" "+user.getLastName();
        }

        if (!StandardCode.getInstance().noNull(scId.getClose_supervisor()).isEmpty()) {
            User user = UserService.getInstance().getSingleUser(scId.getClose_supervisor());
            if(!StandardCode.getInstance().noNull(user.getSignature()).isEmpty())
                close_supervisor = "https://excelnet.xltrans.com/logo/images/" + user.getSignature();
            else
                close_supervisor = user.getFirstName()+" "+user.getLastName();
        }
        
        try {
            uvg.set("close_comment", StandardCode.getInstance().noNull(scId.getClose_comment()));
        } catch (Exception e) {
            uvg.set("close_comment", "");
        }
    try {
            uvg.set("actionp1", StandardCode.getInstance().noNull(scId.getActionp1()));
        } catch (Exception e) {
            uvg.set("actionp1", "");
        }
    try {
            uvg.set("actionp2", StandardCode.getInstance().noNull(scId.getActionp2()));
        } catch (Exception e) {
            uvg.set("actionp2", "");
        }
    try {
            uvg.set("actionp3", StandardCode.getInstance().noNull(scId.getActionp3()));
        } catch (Exception e) {
            uvg.set("actionp3", "");
        }
    try {
            uvg.set("actionp4", StandardCode.getInstance().noNull(scId.getActionp4()));
        } catch (Exception e) {
            uvg.set("actionp4", "");
        }
    try {
            uvg.set("responsiblep1", StandardCode.getInstance().noNull(scId.getResponsiblep1()));
        } catch (Exception e) {
            uvg.set("responsiblep1", "");
        }
    try {
            uvg.set("responsiblep2", StandardCode.getInstance().noNull(scId.getResponsiblep2()));
        } catch (Exception e) {
            uvg.set("responsiblep2", "");
        }
    try {
            uvg.set("responsiblep3", StandardCode.getInstance().noNull(scId.getResponsiblep3()));
        } catch (Exception e) {
            uvg.set("responsiblep3", "");
        }
    try {
            uvg.set("responsiblep4", StandardCode.getInstance().noNull(scId.getResponsiblep4()));
        } catch (Exception e) {
            uvg.set("responsiblep4", "");
        }
    try {
            uvg.set("effectivenessp1", StandardCode.getInstance().noNull(scId.getEffectivenessp1()));
        } catch (Exception e) {
            uvg.set("effectivenessp1", "");
        }
    try {
            uvg.set("effectivenessp2", StandardCode.getInstance().noNull(scId.getEffectivenessp2()));
        } catch (Exception e) {
            uvg.set("effectivenessp2", "");
        }
    try {
            uvg.set("effectivenessp3", StandardCode.getInstance().noNull(scId.getEffectivenessp3()));
        } catch (Exception e) {
            uvg.set("effectivenessp3", "");
        }
    try {
            uvg.set("effectivenessp4", StandardCode.getInstance().noNull(scId.getEffectivenessp4()));
        } catch (Exception e) {
            uvg.set("effectivenessp4", "");
        }
    try {
            uvg.set("approverp1", StandardCode.getInstance().noNull(scId.getApproverp1()));
        } catch (Exception e) {
            uvg.set("approverp1", "");
        }
    try {
            uvg.set("approverp2", StandardCode.getInstance().noNull(scId.getApproverp2()));
        } catch (Exception e) {
            uvg.set("approverp2", "");
        }
    try {
            uvg.set("approverp3", StandardCode.getInstance().noNull(scId.getApproverp3()));
        } catch (Exception e) {
            uvg.set("approverp3", "");
        }
    try {
            uvg.set("approverp4", StandardCode.getInstance().noNull(scId.getApproverp4()));
        } catch (Exception e) {
            uvg.set("approverp4", "");
        }
    try {
            uvg.set("approvalp1", StandardCode.getInstance().noNull(scId.getApprovalp1()));
        } catch (Exception e) {
            uvg.set("approvalp1", "");
        }
    try {
            uvg.set("approvalp2", StandardCode.getInstance().noNull(scId.getApprovalp2()));
        } catch (Exception e) {
            uvg.set("approvalp2", "");
        }
    try {
            uvg.set("approvalp3", StandardCode.getInstance().noNull(scId.getApprovalp3()));
        } catch (Exception e) {
            uvg.set("approvalp3", "");
        }
    try {
            uvg.set("approvalp4", StandardCode.getInstance().noNull(scId.getApprovalp4()));
        } catch (Exception e) {
            uvg.set("approvalp4", "");
        }
    try {
            if (scId.getDuedatep1() == null) {
                uvg.set("duedatep1", "");
            } else {uvg.set("duedatep1", "" + df.format(scId.getDuedatep1()));}
        } catch (Exception e) {
            uvg.set("duedatep1", "");
        }
    try {
            if (scId.getDuedatep2() == null) {
                uvg.set("duedatep2", "");
            } else {uvg.set("duedatep2", "" + df.format(scId.getDuedatep2()));}
        } catch (Exception e) {
            uvg.set("duedatep2", "");
        }
    try {
            if (scId.getDuedatep3() == null) {
                uvg.set("duedatep3", "");
            } else {uvg.set("duedatep3", "" + df.format(scId.getDuedatep3()));}
        } catch (Exception e) {
            uvg.set("duedatep3", "");
        }
    try {
            if (scId.getDuedatep4() == null) {
                uvg.set("duedatep4", "");
            } else {uvg.set("duedatep4", "" + df.format(scId.getDuedatep4()));}
        } catch (Exception e) {
            uvg.set("duedatep4", "");
        }
    try {
            if (scId.getDoip1() == null) {
                uvg.set("doip1", "");
            } else {uvg.set("doip1", "" + df.format(scId.getDoip1()));}
        } catch (Exception e) {
            uvg.set("doip1", "");
        }
    try {
            if (scId.getDoip2() == null) {
                uvg.set("doip2", "");
            } else {uvg.set("doip2", "" + df.format(scId.getDoip2()));}
        } catch (Exception e) {
            uvg.set("doip2", "");
        }
    try {
            if (scId.getDoip3() == null) {
                uvg.set("doip3", "");
            } else {uvg.set("doip3", "" + df.format(scId.getDoip3()));}
        } catch (Exception e) {
            uvg.set("doip3", "");
        }
    try {
            if (scId.getDoip4() == null) {
                uvg.set("doip4", "");
            } else {uvg.set("doip4", "" + df.format(scId.getDoip4()));}
        } catch (Exception e) {
            uvg.set("doip4", "");
        }

        request.setAttribute("close_supervisor", close_supervisor);
        request.setAttribute("close_owner", close_owner);
        request.setAttribute("formValue", uvg);
        request.setAttribute("number", number);

        return (mapping.findForward("Success"));

    }
}
