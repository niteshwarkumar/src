/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import java.util.List;
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
import app.user.Department;
import app.user.Training;
import app.user.User;
import app.user.UserService;
import java.util.Date;

/**
 *
 * @author Niteshwar
 */
public class QMSLibraryTrainingUpdateAction extends Action {

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
        String userId = request.getParameter("user");
        User u = UserService.getInstance().getSingleUser(Integer.parseInt(userId));
        String trainingId = request.getParameter("trainingId");
        String departmentId = request.getParameter("departmentId");
        QMSLibrary qms = QMSService.getInstance().getSingleQMSLibraryDocument(Integer.parseInt(trainingId));
        List training = UserService.getInstance().getTraining(Integer.parseInt(userId), Integer.parseInt(trainingId), Integer.parseInt(departmentId));
        if (training.size() == 0) {
            Training train = new Training();
            //QMSLibrary lib=QMSService.getInstance().getSingleQMSLibraryDocument(Integer.SIZE)
            train.setDepartmentId(Integer.parseInt(departmentId));
            train.setUser(u);
            train.setDocId(Integer.parseInt(trainingId));
            train.setDescription(qms.getDescription()+" "+qms.getVersion());
            train.setDateCompleted(new Date());
            train.setEvidence("QMS Document");
            train.setLocation("Online");
            train.setResult("Passed");
            train.setCompany("Excel Translations");
          //  train.setDateStart(qms.);
            Integer uid = UserService.getInstance().addTraining(train, u);

        }
        //UserService.getInstance().
        request.setAttribute("id", trainingId);
//Department dep=UserService.getInstance().getSingleDepartment(Integer.parseInt(trainingId));


// Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
