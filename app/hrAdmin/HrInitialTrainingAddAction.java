/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.hrAdmin;

import app.standardCode.DateService;
import app.user.TrainingInitial;
import app.user.User;
import app.user.UserService;
import java.util.List;
import java.util.ListIterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Niteshwar
 */
public class HrInitialTrainingAddAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {
        String jsonData = new String(request.getParameter("trainingJSON").getBytes(), "UTF-8");


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

//END get id of current user from either request, attribute, or cookie

//get user to edit
        u = UserService.getInstance().getSingleUser(id);


        if (jsonData != null && !"".equals(jsonData)) {
            JSONArray training = new JSONArray(jsonData);
            UserService.getInstance().unlinkInitialTraining(id);
            for (int i = 0; i < training.length(); i++) {
                JSONObject j = (JSONObject) training.get(i);
                //System.out.println("JSONObject>>>>>>>>>>>>>>>>>>>>>>>>>" + j);
                TrainingInitial td = new TrainingInitial();
                td = new TrainingInitial();
                String datestarted = j.getString("traindate");

                try {
                    if (datestarted.length() > 0) {
                        td.setTraindate(DateService.getInstance().convertDate1(datestarted).getTime());
                    }
                } catch (Exception ex) {
                }
                td.setDepartment(j.getString("department"));
                td.setCompleted(j.getString("completed"));
                td.setPrevexp(j.getString("prevexp"));
                td.setTrainer(j.getString("trainer"));
                td.setTrainingitem(j.getString("trainingitem"));
                td.setID_User(id);

                UserService.getInstance().addTrainingInitial(td);

            }
        }

        request.setAttribute("userViewId", "" + id);
        return (mapping.findForward("Success"));

    }
}
