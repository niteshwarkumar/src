/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.reports;


//import app.extjs.vo.ClientService;
import app.extjs.helpers.ClientHelper;
import app.project.ProjectService;
import app.standardCode.StandardCode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
public class KpiReport2  extends Action {


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
         
        List<Kpi> kpiList = ReportsService.getInstance().getKpi("New Client");
        List<String> key  = new ArrayList<>();

        ClientHelper ch = new ClientHelper();
//        JSONArray clientCount = ch.getClientCountPerYear();
         Map<String, String> clientCountBiYearly = new HashMap<>();
        JSONObject kpiObj = new JSONObject();
           
        for (Kpi kpi : kpiList) {
            for (int ii = 2011; ii <= cal.get(Calendar.YEAR); ii++){
                if(kpi.getYear()==ii){
                    int refValue2 =0;
                   if(kpi.getReferancevalue2()!=null){
                       refValue2 = Integer.parseInt(kpi.getReferancevalue2());
                   }
                    JSONArray clientCount = ch.getClientCountPerYear(refValue2,ii);
                    String yearlyReferance = "";
                    String referance = "";
                    if(!StandardCode.getInstance().noNull(kpi.getReferancevalue()).equalsIgnoreCase("")){
                        //System.out.println(kpi.getReferancevalue());
                        referance =kpi.getReferancevalue();
                        referance = referance.replaceAll(",", "").trim();
                        yearlyReferance = "<br> (Bi-Annually)<br><br>"
                                //+" $"//+ StandardCode.getInstance().formatDouble(Double.parseDouble(referance)/2)
                                //+" (Annually)<br><br> Value set on <br>"
                                +kpi.getReferanceDate();
//                        referance = StandardCode.getInstance().formatDouble(Double.parseDouble(referance));
                    }
                    String[] action = StandardCode.getInstance().noNull(kpi.getAction()).split("##");     
                    JSONObject singleKpiObj = new JSONObject();
                    
                    singleKpiObj.put("id", kpi.getId());
                    singleKpiObj.put("goal", kpi.getGoal());
                    singleKpiObj.put("refDate", kpi.getReferanceDate());
                    singleKpiObj.put("referance", referance);
                    singleKpiObj.put("yearlyReferance", yearlyReferance);
                    singleKpiObj.put("referance2", "atleast "+refValue2 +" projects");
                    singleKpiObj.put("refDate2", kpi.getReferanceDate2());
                    singleKpiObj.put("responsibility", kpi.getResponsibility());
                    singleKpiObj.put("source", kpi.getSource());
                    singleKpiObj.put("type", kpi.getType());
                    singleKpiObj.put("data", clientCount);
                    String oddYr = ""+ii;
                    String evenYr = ""+ ++ii;
                    singleKpiObj.put("year", oddYr +"-"+ evenYr);
                    key.add(oddYr +"-"+ evenYr);
                   clientCountBiYearly.put(oddYr +"-"+ evenYr, ""+clientCount.length());
                    try{singleKpiObj.put("action", action[0]);}catch(Exception e){singleKpiObj.put("action", "");}
//                    try{singleKpiObj.put("q2action", action[1]);}catch(Exception e){singleKpiObj.put("q2action", "");}
//                    try{singleKpiObj.put("q3action", action[2]);}catch(Exception e){singleKpiObj.put("q3action", "");}
//                    try{singleKpiObj.put("q4action", action[3]);}catch(Exception e){singleKpiObj.put("q4action", "");}
                    
                    
                    
                    
                    kpiObj.put(oddYr +"-"+ evenYr,singleKpiObj);
                }
            }
        }
        int endyear = cal.get(Calendar.YEAR);
        if(endyear%2!=0){endyear=endyear+1; }
        request.setAttribute("kpi", kpiObj);
        request.setAttribute("startYear", 2005);
        request.setAttribute("endYear", endyear);
        request.setAttribute("type", "New Client");
        request.setAttribute("clientCountBiYearly", clientCountBiYearly);
       
        
	return (mapping.findForward("Success"));
    }

}
