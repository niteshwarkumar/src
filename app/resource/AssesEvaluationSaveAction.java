/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.resource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
public class AssesEvaluationSaveAction  extends Action{

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {


        String jsonProducts = request.getParameter("blogJSON");
String resource=request.getParameter("resourceViewId");


              System.out.println("resourceeeeeeeeeeeeeeee"+resource);
            if(resource==null)
                       resource= request.getSession(false).getAttribute("resource").toString();

           //   resource=request.getAttribute(resource).toString();
              System.out.println("resourceeBBBBBBBBBBBBBBBBBB"+resource);

ResourceService.getInstance().unlinkAssesEval(resource);
       // List eval=ResourceService.getInstance().getEvaluationList()
          if(jsonProducts!=null && !"".equals(jsonProducts)){
        JSONArray products = new JSONArray(jsonProducts);

        for(int i=0;i< products.length();i++){
            JSONObject j = (JSONObject)products.get(i);
            AssesEval pr=null;
            System.out.println("Alert ID"+j.getString("id"));
            if(j.getString("id").equalsIgnoreCase("new")){
            pr = new AssesEval();
            }else{
            pr=ResourceService.getInstance().getSingleAssesEval(Integer.parseInt(j.getString("id")));
            }

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
              try {
            java.sql.Date d2 = new java.sql.Date(df.parse(j.getString("assessmentDate")).getTime());
            pr.setAssessmentDate(d2);
            } catch (Exception ex){}
            pr.setTest(j.getString("test"));
            pr.setEnteredBy(j.getString("enteredBy"));
            pr.setRejected(j.getString("rejected"));
            pr.setApproved(j.getString("approved"));
            try {
                 pr.setScore(Integer.parseInt(j.getString("score")));
            } catch (NumberFormatException e) {
            }
           
            pr.setResourceId(Integer.parseInt(resource));





      // pr.setPostedOn(j.getString("postedOn"));

            ResourceService.getInstance().addAssesEvaluation(pr);
            }

        }


      return (mapping.findForward("Success"));

    }

    }