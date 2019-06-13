/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.hrAdmin;

import app.standardCode.DateService;
import app.user.Training;
import app.user.User;
import app.user.UserService;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
 * @author Neil
 */
public class HrTrainingAddAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {
        String jsonData = request.getParameter("trainingJSON");


        String userId = null;
       
        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
        
        userId=request.getParameter("userViewId");
        
        //check attribute in request
        if (userId == null) {
            userId = (String) request.getAttribute("userViewId");
        }

        //id of user from cookie
        if (userId == null) {
            //  userId = StandardCode.getInstance().getCookie("userViewId", request.getCookies());
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
  UserService.getInstance().unlinkTraining(id);
            for (int i = 0; i < training.length(); i++) {
                JSONObject j = (JSONObject) training.get(i);
                //System.out.println("JSONObject>>>>>>>>>>>>>>>>>>>>>>>>>" + j);
                Training td = new Training();
//                if (j.getString("id").equalsIgnoreCase("new")) {
                  
                    td = new Training();
               
                String dateCompleted = j.getString("to");

                String datestarted = j.getString("from");
                //DateFormat df = new SimpleDateFormat("MM-dd-yyyy");

                try {
                    if (datestarted.length() > 0) {
                       // //System.out.println("first Date is>>>>>>>>>>>>>>>>>" + (df.parse(datestarted)));
                        td.setDateStart(DateService.getInstance().convertDate1(datestarted).getTime());
                    }
                    ////System.out.println("first Date is>>>>>>>>>>>>>>>>>" + (df.parse(j.getString("firstDraft")).getTime()));
                } catch (Exception ex) {
                    

                }
                try {
                    if (dateCompleted.length() > 0) {
                        td.setDateCompleted(DateService.getInstance().convertDate1(dateCompleted).getTime());
                    }
                } catch (Exception ex) {
                }
                td.setDescription(j.getString("description"));
                td.setCompany(j.getString("company"));
                td.setLocation(j.getString("location"));
//                td.setTrainer(j.getString("trainer"));
                td.setResult(j.getString("result"));
                td.setEvidence(j.getString("evidence"));
                td.setEffectiveness(j.getString("effectiveness"));
                td.setNotes(j.getString("notes"));
                td.setVerificationResult(j.getString("verificationResult"));
                td.setVerifiedBy(j.getString("verifiedBy"));

                //upload new training to db
                UserService.getInstance().addTraining(td, u);
//
//                 }else{
//                    UserService.getInstance().unlinkTraining(id);
//                    td = UserService.getInstance().getSingleTraining(Integer.parseInt(j.getString("id")));
//
//                String dateCompleted = j.getString("to");
//
//                String datestarted = j.getString("from");
//                DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
//
//                try {
//                    if (datestarted.length() > 0) {
//                        //System.out.println("first Date is>>>>>>>>>>>>>>>>>" + (df.parse(datestarted)));
//                        td.setDateStart(DateService.getInstance().convertDate1(datestarted).getTime());
//                    }
//                    ////System.out.println("first Date is>>>>>>>>>>>>>>>>>" + (df.parse(j.getString("firstDraft")).getTime()));
//                } catch (Exception ex) {
//
//
//                }
//                try {
//                    if (dateCompleted.length() > 0) {
//                        td.setDateCompleted(DateService.getInstance().convertDate1(dateCompleted).getTime());
//                    }
//                } catch (Exception ex) {
//                }
//                td.setDescription(j.getString("description"));
//                td.setCompany(j.getString("company"));
//                td.setLocation(j.getString("location"));
//                td.setTrainer(j.getString("trainer"));
//                td.setResult(j.getString("result"));
//                td.setEvidence(j.getString("evidence"));
//
//                //upload new training to db
//                UserService.getInstance().addTraining(td, u);
//                 }

            }
        }

        request.setAttribute("userViewId", ""+id);
        return (mapping.findForward("Success"));

    }
}
