/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.reports;

import app.project.Project;
import app.project.ProjectService;
import app.standardCode.StandardCode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class KpiReportL2  extends Action {


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
        	// Forward control to the specified success URI
        
        Calendar cal= new GregorianCalendar();
         
        List<Kpi> kpiList = ReportsService.getInstance().getKpi("Location2");
        List<String> key  = new ArrayList<>();
        Map<String, String> cs = ProjectService.getInstance().getClientSatisfactionPerYear();

        Map<String, String> location = new HashMap<>();
        JSONObject kpiObj = new JSONObject();
        Project p = new Project();
        
           
        for (Kpi kpi : kpiList) {
            for (int ii = 2012; ii <= cal.get(Calendar.YEAR); ii++){
                if(kpi.getYear()==ii){
                   JSONArray locCS = ProjectService.getInstance().getClientSatisfactionPerLocation(ii);
                    String refDate = "";
                    if(null!=kpi.getReferanceDate()) refDate = kpi.getReferanceDate().toString();
                    String yearlyReferance = "";
                    String referance = "";
                    if(!StandardCode.getInstance().noNull(kpi.getReferancevalue()).equalsIgnoreCase("")){
                        //System.out.println(kpi.getReferancevalue());
                        referance =kpi.getReferancevalue();
                        referance = referance.replaceAll("\\$", "").replaceAll(",", "").trim();
                        yearlyReferance = "<br> "
                                //+" $"//+ StandardCode.getInstance().formatDouble(Double.parseDouble(referance)/2)
                                +" (Annually)<br><br> Value set on <br>"
                                +refDate;
                                referance = StandardCode.getInstance().formatDouble(Double.parseDouble(referance));
                    }
                    String[] action = StandardCode.getInstance().noNull(kpi.getAction()).split("##");     
                    JSONObject singleKpiObj = new JSONObject();
                    
                    singleKpiObj.put("id", kpi.getId());
                    singleKpiObj.put("goal", kpi.getGoal());
                    singleKpiObj.put("refDate", kpi.getReferanceDate());
                    singleKpiObj.put("referance", referance);
                    singleKpiObj.put("yearlyReferance", yearlyReferance);
                    //singleKpiObj.put("referance2", "atleast "+refValue2 +" projects");
                    singleKpiObj.put("refDate2", kpi.getReferanceDate2());
                    singleKpiObj.put("responsibility", kpi.getResponsibility());
                    singleKpiObj.put("source", kpi.getSource());
                    singleKpiObj.put("type", kpi.getType());
                    singleKpiObj.put("data", locCS);
//                    String oddYr = ""+ii;
//                    String evenYr = ""+ ++ii;
                    singleKpiObj.put("year", ""+ii);
//                    key.add(oddYr +"-"+ evenYr);
                   location.put(""+ii, ""+locCS.length());
                    try{singleKpiObj.put("action", action[0]);}catch(Exception e){singleKpiObj.put("action", "");}
//                    try{singleKpiObj.put("q2action", action[1]);}catch(Exception e){singleKpiObj.put("q2action", "");}
//                    try{singleKpiObj.put("q3action", action[2]);}catch(Exception e){singleKpiObj.put("q3action", "");}
//                    try{singleKpiObj.put("q4action", action[3]);}catch(Exception e){singleKpiObj.put("q4action", "");}
                    
                    
                    
                    
                    kpiObj.put(""+ii,singleKpiObj);
                }
            }
        }
        int endyear = cal.get(Calendar.YEAR);
        if(endyear%2!=0){endyear=endyear+1; }
        request.setAttribute("kpi", kpiObj);
        request.setAttribute("startYear", 2012);
        request.setAttribute("endYear", endyear);
        request.setAttribute("type", "Location2");
        request.setAttribute("locationYearly", location);
        request.setAttribute("clientSatisfaction", cs);
       
        
	return (mapping.findForward("Success"));
    }

}
