/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import java.util.*;
import app.security.*;
import app.standardCode.StandardCode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.struts.validator.*;

/**
 *
 * @author Niteshwar
 */
public class CapaIdPreAction extends Action {

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

        String number = request.getParameter("number");
        CapaId capaId = QMSService.getInstance().getSingleCapaId(number);
        Capa capa = QMSService.getInstance().getSingleCapa(number);

        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");

//        Date date = ...;  // wherever you get this
//DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
//String text = df.format(date);
//System.out.println(text);
       
        DynaValidatorForm uvg = (DynaValidatorForm) form;


        try {
            uvg.set("number", StandardCode.getInstance().noNull(capa.getNumber()));
        } catch (Exception e) {
            uvg.set("number", "");
        }
        try {
            uvg.set("cdate", capa.getCdate());
        } catch (Exception e) {
            uvg.set("cdate", "");
        }
        try {
            uvg.set("employee", StandardCode.getInstance().noNull(capa.getEmployee()));
        } catch (Exception e) {
            uvg.set("employee", "");
        }
        try {
            uvg.set("location", StandardCode.getInstance().noNull(capa.getLocation()));
        } catch (Exception e) {
            uvg.set("location", "");
        }
        try {
            uvg.set("description", StandardCode.getInstance().noNull(capa.getDescription()));
        } catch (Exception e) {
            uvg.set("description", "");
        }
        try {
            uvg.set("reportedby", StandardCode.getInstance().noNull(capa.getReportedby()));
        } catch (Exception e) {
            uvg.set("reportedby", "");
        }
        try {
            uvg.set("fromc", StandardCode.getInstance().noNull(capa.getFromc()));
        } catch (Exception e) {
            uvg.set("fromc", "");
        }

        try {
            uvg.set("issueId", StandardCode.getInstance().noNull(capa.getIssueId()));
        } catch (Exception e) {
            uvg.set("issueId", "");
        }
        try {
            uvg.set("source", StandardCode.getInstance().noNull(capa.getSource()));
        } catch (Exception e) {
            uvg.set("source", "");
        }
        try {
            uvg.set("status", StandardCode.getInstance().noNull(capa.getStatus()));
        } catch (Exception e) {
            uvg.set("status", "");
        }
        try {
            uvg.set("ncr", StandardCode.getInstance().noNull(capa.getNcr()));
        } catch (Exception e) {
            uvg.set("ncr", "");
        }
        try {
            uvg.set("rca", StandardCode.getInstance().noNull(capaId.getRca()));
        } catch (Exception e) {
            uvg.set("rca", "");
        }
        try {
            if (capaId.getRca_t_date() == null) {
                uvg.set("rca_t_date", "");
            } else {
                uvg.set("rca_t_date", "" + df.format(capaId.getRca_t_date()));
            }
        } catch (Exception e) {
            uvg.set("rca_t_date", "");
        }
        try {
            if (capaId.getRca_a_date() == null) {
                uvg.set("rca_a_date", "");
            } else {
                uvg.set("rca_a_date", "" + df.format(capaId.getRca_a_date()));
            }
        } catch (Exception e) {
            uvg.set("rca_a_date", "");
        }
        try {
            uvg.set("nc", StandardCode.getInstance().noNull(capaId.getNc()));
        } catch (Exception e) {
            uvg.set("nc", "");
        }
        try {
            uvg.set("ncyesno", capaId.getNcyesno());
        } catch (Exception e) {
            uvg.set("ncyesno", "");
        }
        try {
            uvg.set("actionplan", StandardCode.getInstance().noNull(capaId.getActionplan()));
        } catch (Exception e) {
            uvg.set("actionplan", "");
        }
        try {
            uvg.set("actionplan_approve", StandardCode.getInstance().noNull(capaId.getActionplan_approve()));
        } catch (Exception e) {
            uvg.set("actionplan_approve", "");
        }
        try {
            uvg.set("actionimp", StandardCode.getInstance().noNull(capaId.getActionimp()));
        } catch (Exception e) {
            uvg.set("actionimp", "");
        }

        try {
            if (capaId.getActionimp_t_date() == null) {
                uvg.set("actionimp_t_date", "");
            } else {
                uvg.set("actionimp_t_date", "" + df.format(capaId.getActionimp_t_date()));
            }
        } catch (Exception e) {
            uvg.set("actionimp_t_date", "");
        }

        try {
            if (capaId.getActionimp_a_date() == null) {
                uvg.set("actionimp_a_date", "");
            } else {
                uvg.set("actionimp_a_date", "" + df.format(capaId.getActionimp_a_date()));
            }
        } catch (Exception e) {
            uvg.set("actionimp_a_date", "");
        }

        try {
            uvg.set("effectiveplan", StandardCode.getInstance().noNull(capaId.getEffectiveplan()));
        } catch (Exception e) {
            uvg.set("effectiveplan", "");
        }

        try {
            uvg.set("verify", StandardCode.getInstance().noNull(capaId.getVerify()));
        } catch (Exception e) {
            uvg.set("verify", "");
        }

        try {
            if (capaId.getVerify_t_date() == null) {
                uvg.set("verify_t_date", "");
            } else {
                uvg.set("verify_t_date", "" + df.format(capaId.getVerify_t_date()));
            }
        } catch (Exception e) {
            uvg.set("verify_t_date", "");
        }

        try {
            if (capaId.getVerify_a_date() == null) {
                uvg.set("verify_a_date", "");
            } else {
                uvg.set("verify_a_date", "" + df.format(capaId.getVerify_a_date()));
            }
        } catch (Exception e) {
            uvg.set("verify_a_date", "");
        }

        try {
            uvg.set("verify_approve", StandardCode.getInstance().noNull(capaId.getVerify_approve()));
        } catch (Exception e) {
            uvg.set("verify_approve", "");
        }

        try {
            uvg.set("comments", StandardCode.getInstance().noNull(capaId.getComments()));
        } catch (Exception e) {
            uvg.set("comments", "");
        }

        try {
            if (capaId.getAdmin_rec_date() == null) {
                uvg.set("admin_rec_date", "");
            } else {
                uvg.set("admin_rec_date", "" + df.format(capaId.getAdmin_rec_date()));
            }
        } catch (Exception e) {
            uvg.set("admin_rec_date", "");
        }

        try {
            if (capaId.getAdmin_own_date() == null) {
                uvg.set("admin_own_date", "");
            } else {
                uvg.set("admin_own_date", "" + df.format(capaId.getAdmin_own_date()));
            }
        } catch (Exception e) {
            uvg.set("admin_own_date", "");
        }

        try {
            uvg.set("admin_rec_person", StandardCode.getInstance().noNull(capaId.getAdmin_rec_person()));
        } catch (Exception e) {
            uvg.set("admin_rec_person", "");
        }

        try {
            uvg.set("admin_own_person", StandardCode.getInstance().noNull(capaId.getAdmin_own_person()));
        } catch (Exception e) {
            uvg.set("admin_own_person", "");
        }
        try {
            uvg.set("admin_disposition", StandardCode.getInstance().noNull(capaId.getAdmin_disposition()));
        } catch (Exception e) {
            uvg.set("admin_disposition", "");
        }
        try {
            uvg.set("owner", StandardCode.getInstance().noNull(capaId.getOwner()));
        } catch (Exception e) {
            uvg.set("owner", "");
        }
        try {
            uvg.set("owner2", StandardCode.getInstance().noNull(capaId.getOwner2()));
        } catch (Exception e) {
            uvg.set("owner2", "");
        }
        try {
            uvg.set("owner3", StandardCode.getInstance().noNull(capaId.getOwner3()));
        } catch (Exception e) {
            uvg.set("owner3", "");
        }

        try {
            uvg.set("capaid_description", StandardCode.getInstance().noNull(capaId.getCapaid_description()));
        } catch (Exception e) {
            uvg.set("capaid_description", "");
        }

        try {
            if (capaId.getAdmin_disp_date() == null) {
                uvg.set("admin_disp_date", "");
            } else {
                uvg.set("admin_disp_date", "" + df.format(capaId.getAdmin_disp_date()));
            }
        } catch (Exception e) {
            uvg.set("admin_disp_date", "");
        }
         try {
            uvg.set("ncminormajor", StandardCode.getInstance().noNull(capaId.getNcminormajor()));
        } catch (Exception e) {
            uvg.set("ncminormajor", "");
        }
         try {
            uvg.set("actionplan_approve2", StandardCode.getInstance().noNull(capaId.getActionplan_approve2()));
        } catch (Exception e) {
            uvg.set("actionplan_approve2", "");
        }
         try {
            uvg.set("actionimp_status", StandardCode.getInstance().noNull(capaId.getActionimp_status()));
        } catch (Exception e) {
            uvg.set("actionimp_status", "");
        }
         try {
            uvg.set("verify_approve2", StandardCode.getInstance().noNull(capaId.getVerify_approve2()));
        } catch (Exception e) {
            uvg.set("verify_approve2", "");
        }
         try {
            uvg.set("admin_disp_person", StandardCode.getInstance().noNull(capaId.getAdmin_disp_person()));
        } catch (Exception e) {
            uvg.set("admin_disp_person", "");
        }

        request.setAttribute("formValue", uvg);
        request.setAttribute("number", number);

        return (mapping.findForward("Success"));




    }
}
