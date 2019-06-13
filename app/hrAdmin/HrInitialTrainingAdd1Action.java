/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.hrAdmin;

import app.project.ProjectService;
import app.project.TargetDoc;
import app.user.TrainingInitial;
import app.user.User;
import app.user.UserService;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author niteshwar
 */
public class HrInitialTrainingAdd1Action  extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {
       
        String userId = null;
        userId = request.getParameter("userViewId");
        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));

//check attribute in request
        if (userId == null) {
            userId = (String) request.getAttribute("userViewId");
        }

//id of user from cookie
        if (userId == null) {
            userId = "" + u.getUserId();
        }

//default user to first if not in request or cookie
        if (userId == null || "null".equals(userId)) {
            List results = UserService.getInstance().getUserList();
//look for first human user
            for (ListIterator iter = results.listIterator(); iter.hasNext();) {
                u = (User) iter.next();
                if (u.getLastName() != null && u.getLastName().length() > 0) {
                    userId = String.valueOf(u.getUserId());
                    break;
                }
            }
        }

        Integer id = Integer.valueOf(userId);
        String[] ddId  = request.getParameterValues("ddId");
        
        if(ddId!=null) {
            for (String ddId1 : ddId) {
                String[] training = ddId1.split("##");
                TrainingInitial td = new TrainingInitial();
                td.setDepartment(training[0]);
                td.setCompleted("");
                td.setPrevexp("");
                if(training.length==3)
                    td.setTrainer(training[2]);
                else
                    td.setTrainer("");
                
                if(training.length>1)
                    td.setTrainingitem(training[1]);
                else
                    td.setTrainingitem("");
                
                
                td.setID_User(id);
                UserService.getInstance().addTrainingInitial(td);
            }
        }

        request.setAttribute("userViewId", "" + id);
        return (mapping.findForward("Success"));

    }
}
