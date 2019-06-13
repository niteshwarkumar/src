/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.qms;
import app.extjs.helpers.HrHelper;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Niteshwar
 */
public class CapaJsonAction extends Action{

    private Log log=LogFactory.getLog("org.apache.struts.webapp.Example");

    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
            List results = new ArrayList();
            List capa = null;
            String client = request.getParameter("client");
            if(client == null)
             capa=QMSService.getInstance().getCapa();
            else 
              capa=QMSService.getInstance().getCapa(client);

             for (int i = 0; i < capa.size(); i++) {
            JSONObject jo = new JSONObject();
            String locked ="";
            Capa v = (Capa) capa.get(i);
            try{ 
                if(v.getIsLocked())
                locked ="<br/>(<font color='red'>Locked</font>)";
            }catch(Exception e){}
            jo.put("id", v.getCapa_id());
            jo.put("number","<a "+HrHelper.LINK_STYLE+" href=\"javascript:openSingleCapaWindow('"+ v.getNumber()+"')\">"+v.getNumber()+locked+"</a>");
            jo.put("cdate", v.getCdate());
            try{
                jo.put("vofe", v.getVofe());
            }catch(Exception e){
                jo.put("vofe", "");
            }
            
            jo.put("location", v.getLocation());
            jo.put("employee", v.getEmployee());
            jo.put("description", v.getDescription());
            jo.put("reportedby", v.getReportedby());
            jo.put("from", v.getFromc());
            jo.put("issueId", v.getIssueId());
            jo.put("source", v.getSource());
            jo.put("status", v.getStatus());
            jo.put("isLocked", v.getIsLocked());
                 try {
                     CapaId capaid=QMSService.getInstance().getSingleCapaId(v.getNumber());
                     if(capaid.getNcyesno().equalsIgnoreCase("Yes")&&capaid.getNcminormajor()!=null)
                     jo.put("ncr", "Yes - "+capaid.getNcminormajor());
                     else if(capaid.getNcyesno().equalsIgnoreCase("No"))
                     {
                          jo.put("ncr", "No");
                     }
                     try{
                jo.put("vofe", capaid.getVerify_t_date());
            }catch(Exception e){
                jo.put("vofe", "");
            }

                 } catch (Exception e) {
                     jo.put("ncr", v.getNcr());
                 }

            //jo.put("ncr", v.getNcr());

            results.add(jo);
            }


        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        out.println(new JSONArray(results.toArray()));
        out.flush();
        return (null);


    }
}
