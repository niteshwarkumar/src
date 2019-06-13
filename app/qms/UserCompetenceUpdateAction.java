/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import app.admin.AdminConstants;
import app.admin.AdminMisc;
import app.admin.AdminService;
import app.project.Change1;
import app.project.Project;
import app.project.ProjectService;
import app.security.SecurityService;
import app.standardCode.DateService;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
public class UserCompetenceUpdateAction extends Action {

    // ----------------------------------------------------- Instance Variables
    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log
            = LogFactory.getLog("org.apache.struts.webapp.Example");

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
            HttpServletResponse response)
            throws Exception {

        // Extract attributes we will need
        MessageResources messages = getResources(request);

        // save errors
        ActionMessages errors = new ActionMessages();

        //START check for login (security)
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)
        String idStr = request.getParameter("id");
        String roles = request.getParameter("role");
        String trainingReq = request.getParameter("trainingReq");

        User cUser = UserService.getInstance().getSingleUser(Integer.parseInt(idStr));
        List competence = AdminService.getInstance().getAdminMiscList(AdminConstants.COMPETANCE);
        List<Competence>  userCompLst = QMSService.getInstance().getUserCompetenceLst(Integer.parseInt(idStr));
        int baseYear = 2017;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i >= baseYear; i--) {
            for (int ii = 0; ii < competence.size(); ii++) {

                AdminMisc am = (AdminMisc) competence.get(ii);
                Competence comp = QMSService.getInstance().getSingleUserCompetence(Integer.parseInt(idStr), am.getId(), i);
                String req = request.getParameter("req" + am.getId() + i);
                String act = request.getParameter("act" + am.getId() + i);
                if (null == comp) {
                    comp = new Competence();
                    comp.setCompetence(am.getId());
                    comp.setUserId(Integer.parseInt(idStr));
                    comp.setCyear(i);
                }else{
                    
                    for (Iterator<Competence> iterator = userCompLst.iterator(); iterator.hasNext();) {
                        Competence uC = iterator.next();
                        if(uC.getId().compareTo(comp.getId())==0){
                            iterator.remove();
                        }
                    }

                    
                }

                if (StandardCode.getInstance().noNull(trainingReq).equalsIgnoreCase("on")) {
                    comp.setIsTrainingReq(Boolean.TRUE);
                } else {
                    comp.setIsTrainingReq(Boolean.FALSE);
                }
                 if (StandardCode.getInstance().noNull(roles).isEmpty()) {
                    comp.setRole(0);
                } else {
                    comp.setRole(Integer.parseInt(roles));
                }
                comp.setActual(Integer.parseInt(act));
                comp.setRequired(Integer.parseInt(req));
                QMSServiceAddUpdate.getInstance().updateUserCompetence(comp);
                System.out.println(cUser.getFirstName() + am.getId() + req + act);
            }

        }
        
        userCompLst.forEach((uC) -> {
            QMSServiceAddUpdate.getInstance().delete(uC);
        });

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
