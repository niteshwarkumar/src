/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import app.security.SecurityService;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author niteshwar
 */
public class ChangeUpdateAction  extends Action {

    // ----------------------------------------------------- Instance Variables
    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log =
            LogFactory.getLog("org.apache.struts.webapp.Example");

    // --------------------------------------------------------- Public Methods
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @exception Exception if business logic throws an exception
     */
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        // Extract attributes we will need
        MessageResources messages = getResources(request);

        // save errors
        ActionMessages errors = new ActionMessages();

        //START check for login (security)
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        String delJson = request.getParameter("DelData");
        String jsonList = request.getParameter("auditJSON");
        
        try{

        JSONArray delJsonArray = new JSONArray(delJson);
          for(int i=0;i<delJson.length();i++){
                JSONObject j=(JSONObject)delJsonArray.get(i);
                QMSServiceDelete.getInstance().deleteStrategicChange(j.getInt("id"));
          }
        }catch(Exception e){}

        try{
            JSONArray jsonListArray = new JSONArray(jsonList);
        for(int i=0;i<jsonListArray.length();i++){
            JSONObject j=(JSONObject)jsonListArray.get(i);
            StrategicChange sc=null;

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
           
               try {
            java.sql.Date d2= new java.sql.Date(df.parse(j.getString("cdate")).getTime());

            String yy=(""+d2).substring(2, 4);

            try{
                String num= (j.getString("number").split(">"))[1].split("<")[0];
            sc = QMSService.getInstance().getSingleStrategicChange(num);
            }catch(Exception e){}
            if(sc==null){
            sc=new StrategicChange();
            sc.setNumber(QMSService.getInstance().getNewStrategicChangeNumber(yy));
            }
               sc.setCdate(d2);

               } catch (Exception ex){
                   //System.out.println("eroooooooooooooooo"+ex.getMessage());
            }

            try {
                 sc.setDescription(j.getString("description"));
            } catch (Exception e) {
            }
            
            
             try {
                 sc.setEmployee(j.getString("employee"));
            } catch (Exception e) {
            }
             try {
                sc.setReportedby(j.getString("reportedby"));
            } catch (Exception e) {
            }
             
             try {
                sc.setStatus(j.getString("status"));

            } catch (Exception e) {
            }

            
            QMSServiceAddUpdate.getInstance().saveStrategicChange(sc);
             
        }
        }catch(Exception e){}




       // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
