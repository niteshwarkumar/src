/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.*;
import java.util.*;
import app.user.*;
import app.extjs.helpers.ProjectHelper;
import app.standardCode.*;
import app.security.*;

/**
 *
 * @author Neil
 */
public class ProjectRsfServiceUpdate extends Action {
    /*
     * To change this template, choose Tools | Templates
     * and open the template in the editor.
     */

//ProjectViewOverviewAction.java gets the current project from the db
//It then places that project into the project form for
//display in the jsp page. It then places location values into
//cookies
    // ----------------------------------------------------- Instance Variables
    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log =
            LogFactory.getLog("org.apache.struts.webapp.Example");

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

        //which project to update from hidden value in form
        String id = request.getParameter("projectViewId");

        //get the project to be updated from db
        Project p = ProjectService.getInstance().getSingleProject(Integer.valueOf(id));

        //values for update from form; change what is stored in db to these values
        DynaValidatorForm pvo = (DynaValidatorForm) form;


        String startDate = (String) pvo.get("startDate");
        String dueDate = (String) pvo.get("dueDate");
        String deliveryDate = (String) pvo.get("deliveryDate");
        String deliveryMethod = (String) pvo.get("deliveryMethod");
     //   String status = (String) (pvo.get("status"));
        String beforeWorkTurn = (String) (pvo.get("beforeWorkTurn"));
        String beforeWorkTurnUnits = (String) (pvo.get("beforeWorkTurnUnits"));
        String afterWorkTurn = (String) (pvo.get("afterWorkTurn"));
        String afterWorkTurnUnits = (String) (pvo.get("afterWorkTurnUnits"));
        String afterWorkTurnReason = (String) (pvo.get("afterWorkTurnReason"));



        //update the project's values

        if (startDate.length() >= 1) {
            p.setStartDate(DateService.getInstance().convertDate(startDate).getTime());
        } else {
            p.setStartDate(null);
        }
        if (dueDate.length() >= 1) {
            p.setDueDate(DateService.getInstance().convertDate(dueDate).getTime());
        } else {
            p.setDueDate(null);
        }
        if (deliveryDate.length() >= 1) {
            p.setDeliveryDate(DateService.getInstance().convertDate(deliveryDate).getTime());
        } else {
            p.setDeliveryDate(null);
        }

        p.setDeliveryMethod(deliveryMethod);
//        p.setClientAuthorization(request.getParameter("clientAuthorization"));
       // p.setProduct(request.getParameter("product"));
        String[] components = request.getParameterValues("component");
        if (components != null) {
            String compString = "";
            for (int i = 0; i < components.length; i++) {
                compString += components[i] + ",";
            }
            p.setComponent(compString);
        }

//        if (status.equals("active")) {
//            p.setCompleteDate(null);
//        } else if (!p.getStatus().equals("complete")) {
//            p.setCompleteDate(new Date());
//        }
//        p.setStatus(status);

        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
        p.setLastModifiedBy(u.getFirstName() + " " + u.getLastName());
        p.setLastModifiedDate(new Date(System.currentTimeMillis()));

        p.setBeforeWorkTurn(beforeWorkTurn);
        p.setBeforeWorkTurnUnits(beforeWorkTurnUnits);
        p.setAfterWorkTurn(afterWorkTurn);
        p.setAfterWorkTurnUnits(afterWorkTurnUnits);
        p.setAfterWorkTurnReason(afterWorkTurnReason);


        p.setAllInOne(request.getParameter("allInOne"));
        p.setOrderConfirmation(request.getParameter("orderConfirmation"));
        p.setDeliveryConfirmation(request.getParameter("deliveryConfirmation"));
        p.setTranslationApprovalConfirmation(request.getParameter("translationApproval"));
        p.setOrderConfirmationMail(request.getParameter("orderConfirmationMail"));
        p.setDeliveryConfirmationMail(request.getParameter("deliveryConfirmationMail"));
        p.setTranslationApprovalConfirmationMulti(request.getParameter("translationApprovalConfirmationMulti"));
        p.setOther(request.getParameter("other"));
        //p.setProjectDescription(request.getParameter("projectDescription"));
        //set updated values to db
        ProjectService.getInstance().updateProject(p);

        //update delivery dates
//        java.util.Enumeration params = request.getParameterNames();
//        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
//        ProjectService.getInstance().deleteAllAdditionalDates(p.getProjectId());
//        while (params.hasMoreElements()) {
////             Get the next parameter name.
//            String paramName = (String) params.nextElement();
//
//            if (paramName.indexOf("ADDITIONAL_DEL_DATES_") > -1 && !"".equals(request.getParameter(paramName))) {
//                String index = paramName.substring("ADDITIONAL_DEL_DATES_".length());
//                String date = request.getParameter(paramName);
//                String value = request.getParameter("DESC_DEL_DATES_" + index);
//                AdditionalDeliveryDate add = new AdditionalDeliveryDate();
//                add.setID_Project(p.getProjectId().intValue());
//                add.setDelivery_date(sdf.parse(date));
//                add.setDescription(value);
//
//                ProjectService.getInstance().addAdditionalDeliveryDate(add);
//
//            }
//        }



        if (request.getParameter("projectInformalsJSON") != null) {

            ProjectHelper.updateProjectIncrementals(Integer.valueOf(id).intValue(), request.getParameter("projectInformalsJSON"));

        }

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
