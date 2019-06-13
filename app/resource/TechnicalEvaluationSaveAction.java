/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.resource;

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
public class TechnicalEvaluationSaveAction extends Action{

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


              //System.out.println("resourceeeeeeeeeeeeeeee"+resource);
            if(resource==null)
                       resource= request.getSession(false).getAttribute("resource").toString();

           //   resource=request.getAttribute(resource).toString();
              //System.out.println("resourceeBBBBBBBBBBBBBBBBBB"+resource);

ResourceService.getInstance().unlinkEval(resource);
       // List eval=ResourceService.getInstance().getEvaluationList()
          if(jsonProducts!=null && !"".equals(jsonProducts)){
        JSONArray products = new JSONArray(jsonProducts);

        for(int i=0;i< products.length();i++){
            JSONObject j = (JSONObject)products.get(i);
            Evaluation pr=null;
            //System.out.println("Alert ID"+j.getString("id"));
            if(j.getString("id").equalsIgnoreCase("new")){
            pr = new Evaluation();
            }else{
            pr=ResourceService.getInstance().getEvaluation(Integer.parseInt(j.getString("id")));
            }
            pr.setEvaluation_area(j.getString("topic"));
            pr.setEnteredBy(j.getString("enteredBy"));
            pr.setProjectId(j.getString("project"));
            pr.setComment(j.getString("comment"));
            pr.setRate(Integer.parseInt(j.getString("rate")));
            pr.setResourceId(""+resource);
         


            

      // pr.setPostedOn(j.getString("postedOn"));

            ResourceService.getInstance().addEvaluation(pr);
            }
       
        }


      return (mapping.findForward("Success"));

    }

    }


