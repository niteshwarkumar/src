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
public class CouplesEditAction extends Action{

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {


        String jsonProducts = request.getParameter("couplesJSON");
        String resource = request.getParameter("resourceViewId");
        String couple = request.getParameter("couple");


ResourceService.getInstance().unlinkCouples(Integer.parseInt(resource));
//ResourceService.getInstance().unlinkAssesEval(resource);
       // List eval=ResourceService.getInstance().getEvaluationList()
          if(jsonProducts!=null && !"".equals(jsonProducts)){
        JSONArray products = new JSONArray(jsonProducts);

        for(int i=0;i< products.length();i++){
            JSONObject j = (JSONObject)products.get(i);
            Couples pr=null;
            Couples coup=null;
            String name="";
            Resource res=ResourceService.getInstance().getSingleResource(Integer.parseInt(resource));
            if(!res.getCompanyName().equalsIgnoreCase("")&&!res.getCompanyName().equalsIgnoreCase("null")&&!res.getCompanyName().equalsIgnoreCase("freelance")){
                        name=res.getCompanyName();
                        }else{
                        name=res.getFirstName()+" "+res.getLastName();
                        }
                        name=name.replaceAll("\"", "");

            Resource coupleRes=ResourceService.getInstance().getSingleResourceByName(j.getString("couplename"));
            if(j.getString("id").equalsIgnoreCase("new")){
            pr = new Couples();
            coup = new Couples();
            }else{
                 
            pr=ResourceService.getInstance().getSingleCouples(Integer.parseInt(j.getString("id")));
            coup=ResourceService.getInstance().getSingleCouples(coupleRes.getResourceId());
            }
            pr.setCouplename(j.getString("couplename"));
            pr.setCoupleid(coupleRes.getResourceId());
            pr.setResourcename(name);
            pr.setResourcetype(j.getString("resourcetype"));
            pr.setResourceid(res.getResourceId());
            pr.setCoupletype(j.getString("coupletype"));
            pr.setYesno(couple);
            
            coup.setResourcename(j.getString("couplename"));
            coup.setResourceid(coupleRes.getResourceId());
            coup.setCouplename(name);
            coup.setCoupletype(j.getString("resourcetype"));
            coup.setCoupleid(res.getResourceId());
            coup.setResourcetype(j.getString("coupletype"));
            coup.setYesno(couple);
            
            
            ResourceService.getInstance().addCouples(pr);
            ResourceService.getInstance().addCouples(coup);
            }

        }
 request.setAttribute("resourceViewId",resource);
 request.setAttribute("couple",couple);

      return (mapping.findForward("Success"));

    }

    }