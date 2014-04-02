/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.hrAdmin;

import app.extjs.helpers.HrHelper;
import app.extjs.vo.Dropdown;
import app.hr.HrService;
import app.standardCode.DateService;
import app.user.TrainingInitial;
import app.user.TrainingInitialAdmin;
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
public class HrInitialTrainingAdminAddAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {
        String jsonData = request.getParameter("trainingJSON");


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
            UserService.getInstance().unlinkInitialTrainingAdmin();
            for (int i = 0; i < training.length(); i++) {
                JSONObject j = (JSONObject) training.get(i);
                System.out.println("JSONObject>>>>>>>>>>>>>>>>>>>>>>>>>" + j);
                TrainingInitialAdmin td = new TrainingInitialAdmin();
                td = new TrainingInitialAdmin();
                String datestarted = j.getString("hiredate");

                try {
                    if (datestarted.length() > 0) {
                        td.setHiredate(DateService.getInstance().convertDate1(datestarted).getTime());
                    }
                } catch (Exception ex) {
                }
                String[] userArray=j.getString("empname").split(" ");
                String fName=userArray[0];
                String lName=j.getString("empname").replace(userArray[0]+" ", "");
                User user = UserService.getInstance().getSingleUserRealName(fName, lName);
                if(user==null){user=UserService.getInstance().getSingleUserLastName(lName);}
                td.setEmpname(j.getString("empname"));
                td.setDepartment(j.getString("department"));
                td.setMentor(j.getString("mentor"));
                td.setSupervisor(j.getString("supervisor"));
                td.setPosition(j.getString("position"));
                td.setID_User(user.getUserId());
                
                if(j.getString("id").equalsIgnoreCase("New")){
                 List topicList =  HrHelper.getAdminTopicList(j.getString("department"), "TRAINING");
                 for(int ii = 0;ii<topicList.size();ii++){
                    Dropdown dd = (Dropdown) topicList.get(ii);
                    TrainingInitial ti = new TrainingInitial();
                    ti.setDepartment(j.getString("department"));
                    ti.setID_User(user.getUserId());
                    ti.setTrainingitem(dd.getDropdownValue());
                    ti.setTrainer(dd.getPriority());
                    ti.setPrevexp("No");
                    ti.setCompleted("No");
                 UserService.getInstance().addTrainingInitial(ti);

                 }
                }
                UserService.getInstance().addTrainingInitialAdmin(td);

            }
        }

        request.setAttribute("userViewId", "" + id);
        return (mapping.findForward("Success"));

    }
}
