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
import app.user.DepartmentUser;
import app.user.User;
import app.user.UserService;
import java.util.List;

/**
 *
 * @author Niteshwar
 */
public class QMSLibraryTrainingNotifyUpdateAction extends Action {

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
        TrainingNotify train = null;
        String userId = request.getParameter("user");
        try {
            User u = UserService.getInstance().getSingleUser(Integer.parseInt(userId));
        } catch (Exception e) {
        }

        String trainingId = request.getParameter("trainingId");
        String departmentId = request.getParameter("departmentId");
        String deleteTrain = request.getParameter("deleteTrain");
        String needed = request.getParameter("needed");
        QMSLibrary qmsLib=QMSService.getInstance().getSingleQMSLibraryDocument(Integer.parseInt(trainingId));
        if(needed!=null){
        if(needed.equalsIgnoreCase("Y")){
            qmsLib.setNeeded("Y");
            qmsLib.setReason("");
            QMSServiceAddUpdate.getInstance().addLibrary(qmsLib);
            request.setAttribute("id", trainingId);
            return (mapping.findForward("Success"));

        }else if(needed.equalsIgnoreCase("N")) {
            qmsLib.setNeeded("N");
            qmsLib.setReason(request.getParameter("reason"));
            QMSServiceAddUpdate.getInstance().addLibrary(qmsLib);
            request.setAttribute("id", trainingId);
            return (mapping.findForward("Success"));

        }
        }


        if (userId == null) {
            train = QMSService.getInstance().getTrainingNotify(Integer.parseInt(departmentId), Integer.parseInt(trainingId),0);
        } else {
            train = QMSService.getInstance().getTrainingNotify(Integer.parseInt(departmentId), Integer.parseInt(trainingId), Integer.parseInt(userId));
        }
        if (deleteTrain != null) {
            QMSServiceDelete.getInstance().deletetTraininNotify(train);
            request.setAttribute("id", trainingId);
            return (mapping.findForward("Success"));
        }
        if (train == null) {
            train = new TrainingNotify();
        }

        if(userId.equalsIgnoreCase("0")){
            List duList = UserService.getInstance().getDepartmentUserByDEpartment(Integer.parseInt(departmentId));
            for(int i=0;i<duList.size();i++)
            {
                DepartmentUser du = (DepartmentUser) duList.get(i);
                train = QMSService.getInstance().getTrainingNotify(Integer.parseInt(departmentId), Integer.parseInt(trainingId), du.getUserId());
                if (train == null) {
                train = new TrainingNotify();
                train.setDepartmentId(Integer.parseInt(departmentId));
                train.setTrainingId(Integer.parseInt(trainingId));
                train.setUserId(du.getUserId());
                QMSServiceAddUpdate.getInstance().addTrainingNotify(train);
            }
           }

         request.setAttribute("id", trainingId);
         return (mapping.findForward("Success"));
        }


        try {
            train.setDepartmentId(Integer.parseInt(departmentId));
        } catch (Exception e) {
        }
        try {
            train.setTrainingId(Integer.parseInt(trainingId));
        } catch (Exception e) {
        }
        try {
            train.setUserId(Integer.parseInt(userId));
        } catch (Exception e) {
            train.setUserId(0);
        }
        QMSServiceAddUpdate.getInstance().addTrainingNotify(train);
        request.setAttribute("id", trainingId);

// Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
