/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.qms;

import app.project.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import java.util.*;
import app.security.*;
import app.standardCode.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.jasper.tagplugins.jstl.core.Catch;
import org.apache.struts.validator.*;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Niteshwar
 */
public class CapaUpdateAction extends Action {

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
                QMSService.getInstance().deleteCapa(j.getInt("id"));
          }
        }catch(Exception e){}

        try{
            JSONArray jsonListArray = new JSONArray(jsonList);
        for(int i=0;i<jsonListArray.length();i++){
            JSONObject j=(JSONObject)jsonListArray.get(i);
            Capa capa=null;

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                java.sql.Date d1= new java.sql.Date(df.parse(j.getString("vofe")).getTime());
                capa.setVofe(d1);
            } catch (Exception e) {
            }
               try {
            java.sql.Date d2= new java.sql.Date(df.parse(j.getString("cdate")).getTime());

            String yy=(""+d2).substring(2, 4);

            try{
                String num= (j.getString("number").split(">"))[1].split("<")[0];
            capa = QMSService.getInstance().getSingleCapa(num);
            }catch(Exception e){}
            if(capa==null){
            capa=new Capa();
            capa.setNumber(QMSService.getInstance().getNewCapaNumber(yy));
            }
               capa.setCdate(d2);

               } catch (Exception ex){
                   System.out.println("eroooooooooooooooo"+ex.getMessage());
            }

            try {
                 capa.setDescription(j.getString("description"));
            } catch (Exception e) {
            }
             try {
                capa.setFromc(j.getString("from"));
            } catch (Exception e) {
            }
             try {
                capa.setIssueId(j.getString("issueId"));
            } catch (Exception e) {
            }
             try {
                capa.setLocation(j.getString("location"));
            } catch (Exception e) {
            }
             try {
                capa.setNcr(j.getString("ncr"));
            } catch (Exception e) {
            }
             try {
                 capa.setEmployee(j.getString("employee"));
            } catch (Exception e) {
            }
             try {
                capa.setReportedby(j.getString("reportedby"));
            } catch (Exception e) {
            }
             try {
                capa.setSource(j.getString("source"));
            } catch (Exception e) {
            }
             try {
                capa.setStatus(j.getString("status"));

            } catch (Exception e) {
            }

            
            QMSService.getInstance().saveCapa(capa);
             
        }
        }catch(Exception e){}




       // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
