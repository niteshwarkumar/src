/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.client;

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
public class BlogEditAction extends Action {
    @SuppressWarnings("static-access")
    @Override
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {


        String jsonProducts = request.getParameter("blogJSON");



 ClientService.getInstance().unlinkBlog();
          if(jsonProducts!=null && !"".equals(jsonProducts)){
        JSONArray products = new JSONArray(jsonProducts);

        for(int i=0;i< products.length();i++){
            JSONObject j = (JSONObject)products.get(i);
            //System.out.println("JSONObject>>>>>>>>>>>>>>>>>>>>>>>>>"+j);
            Blog pr = new Blog();
            //System.out.println("blog author...................."+(j.getString("author")));
            pr.setAuthor(j.getString("author"));
            pr.setTopic(j.getString("topic"));
            //SimpleDateFormat sdf = new SimpleDateFormat("");

           DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
           // java.util.Date d = df.parse(j.getString("firstDraft").split("T")[0].toString());
          //  java.sql.Date d1= new java.sql.Date(d.getTime());




              try {
            java.sql.Date d2 = new java.sql.Date(df.parse(j.getString("firstDraft")).getTime());
            pr.setFirstDraft(d2);
            //System.out.println("first Date is>>>>>>>>>>>>>>>>>"+  (df.parse(j.getString("firstDraft")).getTime()));
            } catch (Exception ex){}
            try {
            java.sql.Date d3 = new java.sql.Date(df.parse(j.getString("finalDraft")).getTime());
            pr.setFinalDraft(d3);
            } catch (Exception ex){}

             try {
            java.sql.Date d4 = new java.sql.Date(df.parse(j.getString("postedOn")).getTime());
            pr.setPostedOn(d4);
            } catch (Exception ex){}


      // pr.setPostedOn(j.getString("postedOn"));

            ClientService.getInstance().saveBlog(pr);

       }
        }


      return (mapping.findForward("Success"));

    }

    }


