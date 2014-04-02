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
import app.security.*;
import app.standardCode.DateService;
import org.apache.struts.validator.DynaValidatorForm;

/**
 *
 * @author Niteshwar
 */
public class CapaIdUpdateAction extends Action {

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
        DynaValidatorForm upd = (DynaValidatorForm) form;

        String number=request.getParameter("number");
        //String capa_number = (String) upd.get("prepTime");
        String rca = (String) upd.get("rca");

        String nc = (String) upd.get("nc");
        String ncyesno = (String) upd.get("ncyesno");
        String ncminormajor = (String) upd.get("ncminormajor");
        String actionplan = (String) upd.get("actionplan");
        String actionplan_approve = (String) upd.get("actionplan_approve");
        String actionplan_approve2 = (String) upd.get("actionplan_approve2");
        String actionimp = (String) upd.get("actionimp");

        String effectiveplan = (String) upd.get("effectiveplan");
        String verify = (String) upd.get("verify");

        String verify_approve = (String) upd.get("verify_approve");
         String verify_approve2 = (String) upd.get("verify_approve2");
        String comments = (String) upd.get("comments");
        String actionimp_status = (String) upd.get("actionimp_status");

        String admin_rec_person = (String) upd.get("admin_rec_person");
        String admin_own_person = (String) upd.get("admin_own_person");
        String admin_disp_person = (String) upd.get("admin_disp_person");

        String admin_disposition = (String) upd.get("admin_disposition");
        String owner = (String) upd.get("owner");
        String owner2 = (String) upd.get("owner2");
        String owner3 = (String) upd.get("owner3");

        String rca_t_date = (String) upd.get("rca_t_date");
        String rca_a_date = (String) upd.get("rca_a_date");

        String actionimp_t_date = (String) upd.get("actionimp_t_date");
        String actionimp_a_date = (String) upd.get("actionimp_a_date");

        String verify_t_date = (String) upd.get("verify_t_date");
        String verify_a_date = (String) upd.get("verify_a_date");

        String admin_rec_date = (String) upd.get("admin_rec_date");
        String admin_own_date = (String) upd.get("admin_own_date");
        String admin_disp_date = (String) upd.get("admin_disp_date");
        String capaid_description = (String) upd.get("capaid_description");

        try{
        String source=(String) upd.get("source");
       
        Capa capa=QMSService.getInstance().getSingleCapa(number);
        capa.setSource(source);
        QMSService.getInstance().saveCapa(capa);


        }catch(Exception e){}

        CapaId cid = QMSService.getInstance().getSingleCapaId(number);
        if (cid == null) {
            cid = new CapaId();
            cid.setCapa_number(number);
        }

        cid.setActionimp(actionimp);
        try {
            if (actionimp_a_date.length() > 0) { //if present
                cid.setActionimp_a_date(DateService.getInstance().convertDate(actionimp_a_date).getTime());
            }
        } catch (Exception e) {
//            System.out.println("Date Errooooorr " + e.getMessage());
        }
        try {
            if (actionimp_t_date.length() > 0) { //if present
                cid.setActionimp_t_date(DateService.getInstance().convertDate(actionimp_t_date).getTime());
            }
        } catch (Exception e) {
//            System.out.println("Date Errooooorr " + e.getMessage());
        }
        cid.setActionplan(actionplan);
        cid.setActionplan_approve(actionplan_approve);
        cid.setAdmin_disposition(admin_disposition);
        try {
            if (admin_own_date.length() > 0) { //if present
                cid.setAdmin_own_date(DateService.getInstance().convertDate(admin_own_date).getTime());
            }
        } catch (Exception e) {
//            System.out.println("Date Errooooorr " + e.getMessage());
        }

        cid.setAdmin_own_person(admin_own_person);
        try {
            if (admin_rec_date.length() > 0) { //if present
                cid.setAdmin_rec_date(DateService.getInstance().convertDate(admin_rec_date).getTime());
            }
        } catch (Exception e) {
            //System.out.println("Date Errooooorr " + e.getMessage());
        }
        cid.setAdmin_rec_person(admin_rec_person);
        cid.setComments(comments);
        cid.setEffectiveplan(effectiveplan);
        cid.setNc(nc);
        cid.setNcyesno(ncyesno);
        cid.setOwner(owner);
        cid.setOwner2(owner2);
        cid.setOwner3(owner3);
        cid.setCapaid_description(capaid_description);
        cid.setRca(rca);
        try {
            if (rca_a_date.length() > 0) { //if present
                cid.setRca_a_date(DateService.getInstance().convertDate(rca_a_date).getTime());
            }
        } catch (Exception e) {
            //System.out.println("Date Errooooorr " + e.getMessage());
        }
        try {
            if (rca_t_date.length() > 0) { //if present
                cid.setRca_t_date(DateService.getInstance().convertDate(rca_t_date).getTime());
            }
        } catch (Exception e) {
            //System.out.println("Date Errooooorr " + e.getMessage());
        }
        cid.setVerify(verify);
        try {
            if (verify_a_date.length() > 0) { //if present
                cid.setVerify_a_date(DateService.getInstance().convertDate(verify_a_date).getTime());
            }
        } catch (Exception e) {
            //System.out.println("Date Errooooorr " + e.getMessage());
        }
        cid.setVerify_approve(verify_approve);
        try {
            if (verify_t_date.length() > 0) { //if present
                cid.setVerify_t_date(DateService.getInstance().convertDate(verify_t_date).getTime());
            }
        } catch (Exception e) {
            //System.out.println("Date Errooooorr " + e.getMessage());
        }
        try {
            if (admin_disp_date.length() > 0) { //if present
                cid.setAdmin_disp_date(DateService.getInstance().convertDate(admin_disp_date).getTime());
            }
        } catch (Exception e) {
            //System.out.println("Date Errooooorr " + e.getMessage());
        }

        cid.setAdmin_disp_person(admin_disp_person);
        cid.setNcminormajor(ncminormajor);
        cid.setActionplan_approve2(actionplan_approve2);
        cid.setActionimp_status(actionimp_status);
        cid.setVerify_approve2(verify_approve2);

        QMSService.getInstance().saveCapaId(cid);

        return (mapping.findForward("Success"));


    }
}
