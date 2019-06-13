/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.reports;

import app.qms.Capa;
import app.qms.QMSService;
import app.security.SecurityService;
import app.standardCode.ExcelConstants;
import app.standardCode.StandardCode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
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
public class CapaReport extends Action {


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
        if(!SecurityService.getInstance().checkForLogin(request.getSession(false))) { 
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)	
        
        //PRIVS check that reports user is viewing this page 
        if(!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "reports")) {
            return (mapping.findForward("accessDenied"));
       
        }
        String reportType = request.getParameter("reportType");
        String reportCat = request.getParameter("reportCat");
        int capaStartYear = ExcelConstants.CAPA_STARTYEAR;
        String[] capaLocation = ExcelConstants.CAPA_LOCATIONS;
        String[] capaSource = ExcelConstants.CAPA_SOURCE;
        
        //location Array
        JSONArray l1 = new JSONArray();
        JSONArray l2 = new JSONArray();
        JSONArray l0 = new JSONArray();
        //Source Array
        JSONArray s1 = new JSONArray();
        JSONArray s2 = new JSONArray();
        JSONArray s3 = new JSONArray();
        JSONArray s4 = new JSONArray();
        JSONArray s5 = new JSONArray();
        JSONArray s6 = new JSONArray();
        JSONArray s0 = new JSONArray();
        
          Calendar cal= new GregorianCalendar();
       
            int cnt =0;
            for (int ii = capaStartYear; ii <= cal.get(Calendar.YEAR); ii++){
                JSONObject data = new JSONObject();data.put("x",  ii);data.put("y", 0);l1.put(data);
                data = new JSONObject();data.put("x",  ii);data.put("y", 0);l2.put(data);
                data = new JSONObject();data.put("x",  ii);data.put("y", 0);l0.put(data);
                
                data = new JSONObject();data.put("x",  ii);data.put("y", 0);s1.put(data);
                data = new JSONObject();data.put("x",  ii);data.put("y", 0);s2.put(data);
                data = new JSONObject();data.put("x",  ii);data.put("y", 0);s3.put(data);
                data = new JSONObject();data.put("x",  ii);data.put("y", 0);s4.put(data);
                data = new JSONObject();data.put("x",  ii);data.put("y", 0);s5.put(data);
                data = new JSONObject();data.put("x",  ii);data.put("y", 0);s6.put(data);
                data = new JSONObject();data.put("x",  ii);data.put("y", 0);s0.put(data);
                
                cnt++;
            }
            int pl0 =0,pl1=0,pl2=0;
            int ps0 =0,ps1=0,ps2=0,ps3=0,ps4=0,ps5=0,ps6=0;

        
      SimpleDateFormat df = new SimpleDateFormat("yyyy");
        
        List capaList = QMSService.getInstance().getCapa();
        for (Object capaList1 : capaList) {
            Capa capa = (Capa) capaList1;
            int year = Integer.parseInt(df.format(capa.getCdate()));
            for(int loc = 0 ; loc < capaLocation.length; loc++ ){
                
            if(capa.getLocation().equalsIgnoreCase(capaLocation[loc])){  
                    for(int i = 0;i<cnt;i++){
                        JSONObject valueObj = new JSONObject();
                        
                        if(loc == 0) {valueObj = l0.getJSONObject(i);}
                        if(loc == 1) {valueObj = l1.getJSONObject(i);}
                        if(loc == 2) {valueObj = l2.getJSONObject(i);}
                        if(year==valueObj.getInt("x")){
                            if(loc == 0) {pl0++;}
                            if(loc == 1) {pl1++;}
                            if(loc == 2) {pl2++;}
                            int p = valueObj.getInt("y")+1;
                            valueObj.remove("y");
                            valueObj.put("y", p);
                        }
                    }
                   
                }
            }
            
            
            for(int src = 0 ; src < capaSource.length; src++ ){
            if(capa.getSource().equalsIgnoreCase(capaSource[src])){  
                    for(int i = 0;i<cnt;i++){
                        JSONObject valueObj = new JSONObject();
                        
                        if(src == 0){ valueObj = s0.getJSONObject(i);}
                        if(src == 1){ valueObj = s1.getJSONObject(i);}
                        if(src == 2){ valueObj = s2.getJSONObject(i);}
                        if(src == 3){ valueObj = s3.getJSONObject(i);}
                        if(src == 4){ valueObj = s4.getJSONObject(i);}
                        if(src == 5){ valueObj = s5.getJSONObject(i);}
                        if(src == 6){ valueObj = s6.getJSONObject(i);}
                        
                        if(year==valueObj.getInt("x")){
                            if(src == 0){ valueObj = s0.getJSONObject(i);ps0++;}
                        if(src == 1){ ps1++;}
                        if(src == 2){ ps2++;}
                        if(src == 3){ ps3++;}
                        if(src == 4){ ps4++;}
                        if(src == 5){ ps5++;}
                        if(src == 6){ ps6++;}
                            int p = valueObj.getInt("y")+1;
                            valueObj.remove("y");
                            valueObj.put("y", p);
                        }
                    }
                   
                }
            
            }
        }
        JSONArray report = new JSONArray();
        JSONArray pieReport = new JSONArray();
        String[] color = ExcelConstants.REPORT_CHART_COLOR_SEQUENCE;
         
        if(reportType.equalsIgnoreCase("location")) { 
            for(int loc = 0; loc< capaLocation.length;loc++){
                JSONObject maindata = new JSONObject();
                JSONObject maindataPie = new JSONObject();
                maindata.put("key", capaLocation[loc]);
                maindataPie.put("label", capaLocation[loc]);
                if(loc == 0) {maindata.put("values",l0);maindataPie.put("value", pl0);}
                if(loc == 1) {maindata.put("values",l1);maindataPie.put("value", pl1);}
                if(loc == 2) {maindata.put("values",l2);maindataPie.put("value", pl2);}
                maindata.put("color", color[loc]);
                report.put(maindata);
                pieReport.put(maindataPie);
           
            }}else{
            for(int src = 0; src< capaSource.length;src++){
                JSONObject maindata = new JSONObject();
                JSONObject maindataPie = new JSONObject();
                maindata.put("key", capaSource[src]);
                maindataPie.put("label", capaSource[src]);
                if(src == 0) {maindata.put("values",s0);maindataPie.put("value", ps0);}
                if(src == 1) {maindata.put("values",s1);maindataPie.put("value", ps1);}
                if(src == 2) {maindata.put("values",s2);maindataPie.put("value", ps2);}
                if(src == 3) {maindata.put("values",s3);maindataPie.put("value", ps3);}
                if(src == 4) {maindata.put("values",s4);maindataPie.put("value", ps4);}
                if(src == 5) {maindata.put("values",s5);maindataPie.put("value", ps5);}
                if(src == 6) {maindata.put("values",s6);maindataPie.put("value", ps6);}
                
                maindata.put("color", color[src]);
                report.put(maindata);
                pieReport.put(maindataPie);
           
            }
        
        }
            
        request.setAttribute("reportData", report);
        request.setAttribute("reportDataPie", pieReport);
        request.setAttribute("reportCat", reportCat);
        
        	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}