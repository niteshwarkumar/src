/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.dtpScheduler;


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
 * @author Neil
 */
public class ENGProjectAction extends Action{


    @SuppressWarnings("static-access")
    @Override
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {


        String engProjectJSON = request.getParameter("engProjectJSON");
        String QuoteJSON=request.getParameter("QuoteJSON");



          if(engProjectJSON!=null && !"".equals(engProjectJSON)){

        JSONArray products = new JSONArray(engProjectJSON);
         DTPSchedulerService.getInstance().unlinkEng("P");
        for(int i=0;i< products.length();i++){
            JSONObject j = (JSONObject)products.get(i);
            //System.out.println("JSONObject>>>>>>>>>>>>>>>>>>>>>>>>>"+j);
            ENGScheduler eng = new ENGScheduler();

            eng.setRequester(j.getString("requester"));
            eng.setID_Project(j.getString("pno"));
           // eng.setFinishDate(j.getString("finishDate"));
             DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
              try {
            java.sql.Date d2 = new java.sql.Date(df.parse(j.getString("finishDate")).getTime());
            eng.setFinishDate(d2);
            ////System.out.println("first Date is>>>>>>>>>>>>>>>>>"+  (df.parse(j.getString("firstDraft")).getTime()));
            } catch (Exception ex){}
  

            eng.setTask(j.getString("task"));
            eng.setTimeNeeded(j.getString("timeNeed"));
            eng.setHour(j.getString("hour"));
            eng.setTimeNeededUnit(j.getString("timeNeededUnit"));
            eng.setProjectOrQuote("P");


            DTPSchedulerService.getInstance().addENGProject(eng);


       }
        }


        if(QuoteJSON!=null && !"".equals(QuoteJSON)){
        JSONArray products = new JSONArray(QuoteJSON);
DTPSchedulerService.getInstance().unlinkEng("Q");
        for(int i=0;i< products.length();i++){
            JSONObject j = (JSONObject)products.get(i);
            //System.out.println("JSONObject>>>>>>>>>>>>>>>>>>>>>>>>>"+j);
            ENGScheduler eng = new ENGScheduler();

            eng.setRequester(j.getString("requester"));
            eng.setID_Quote(j.getString("qno"));
             DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
              try {
            java.sql.Date d3 = new java.sql.Date(df.parse(j.getString("finishDate")).getTime());
            eng.setFinishDate(d3);
            ////System.out.println("first Date is>>>>>>>>>>>>>>>>>"+  (df.parse(j.getString("firstDraft")).getTime()));
            } catch (Exception ex){}
            eng.setTask(j.getString("task"));
            eng.setTimeNeeded(j.getString("timeNeed"));
            eng.setHour(j.getString("hour"));
            eng.setTimeNeededUnit(j.getString("timeNeededUnit"));
            eng.setProjectOrQuote("Q");
            ////System.out.println("ddddddddddddddddddddddddddd"+j.getString("id"));

            DTPSchedulerService.getInstance().addENGProject(eng);


       }
        }


      return (mapping.findForward("Success"));

    }

}


