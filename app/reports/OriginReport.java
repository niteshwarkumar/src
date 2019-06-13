/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.reports;

import app.db.ConnectionFactory;
import app.extjs.helpers.ClientHelper;
import app.security.SecurityService;
import app.standardCode.ExcelConstants;
import app.standardCode.StandardCode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
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
public class OriginReport extends Action {

    String[] rateType = {"Very positive", "Positive", "Somewhat positive", "Somewhat negative", "Negative", "Very negative"};
    String Verypositive = "Very positive";
    String Positive = "Positive";
    String Somewhatpositive = "Somewhat positive";
    String Somewhatnegative = "Somewhat negative";
    String Negative = "Negative";
    String Verynegative = "Very negative";
    
    
    // ----------------------------------------------------- Instance Variables

    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log
            = LogFactory.getLog("org.apache.struts.webapp.Example");

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
        //END check for login (security)	

        //PRIVS check that reports user is viewing this page 
        if (!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "reports")) {
            return (mapping.findForward("accessDenied"));
        }
           Calendar cal= new GregorianCalendar();
           int currentMnth =cal.get(Calendar.MONTH)+1;
            String FromDate = cal.get(Calendar.YEAR)+"-01-01";
            String ToDate = cal.get(Calendar.YEAR)+"-"+currentMnth+"-"+cal.get(Calendar.DAY_OF_MONTH);
            
        try{
            if(request.getParameter("dateB")!=null){
            FromDate = request.getParameter("dateB");            
            
            
            FromDate = FromDate.replaceAll("/", "-");
            String[] flip = FromDate.split("-");
            FromDate = flip[2] + "-" + flip[0] + "-" + flip[1];}
            if(request.getParameter("dateC")!=null){
            ToDate = request.getParameter("dateC");
            ToDate = ToDate.replaceAll("/", "-");
            String[] flip = ToDate.split("-");
            ToDate = flip[2] + "-" + flip[0] + "-" + flip[1];}
        } catch (Exception e) {
        }
//        int feedBackStartYear = ExcelConstants.FEEDBACK_STARTYEAR;
      //  int feedBackStartYear = 2017;

        

        JSONArray report = getData(FromDate, ToDate);
        JSONObject repData = processData(report);
        
        //System.out.println("app.reports.OriginReport.execute()  "+report);
        request.setAttribute("reportData", repData.getJSONArray("result"));
        request.setAttribute("totData", repData.getJSONObject("totData"));
        
        request.setAttribute("fromDate", FromDate);
        request.setAttribute("toDate", ToDate);
//        request.setAttribute("reportDataPie", pieReport);
//        request.setAttribute("reportCat", reportCat);

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }

    private JSONArray getData(String FromDate, String ToDate) {

        JSONArray result = new JSONArray();
        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();


            String queryString = "SELECT COUNT(0) countFeedback,YEAR(informalDate) yr,rate, c.Company_name, c.id_client "
                    + " FROM project_informal i , project p, client_information c "
                    + " WHERE i.ID_Project = p.ID_Project and c.`ID_Client` = p.ID_Client and "
                    + " rate IS NOT NULL AND rate <> '' AND rate <> 'Neutral' and p.startdate between '"+FromDate+"' and '"+ToDate+"'"
                    + " GROUP BY rate,c.Company_name\n"
                    + " ORDER BY rate,c.Company_name desc";
            
            //System.out.println(queryString);
            PreparedStatement pstmt = session.connection().prepareStatement(queryString);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                
                    result = updateToJSONObj(result, rs.getString("Company_name"), 
                            rs.getInt("countFeedback"), rs.getString("rate"), 
                            rs.getString("id_client"),FromDate,ToDate);

            }
            tx.commit();
           

        } catch (Exception e) {
            try {
                tx.rollback(); //error
            } catch (HibernateException he) {
                System.err.println("Hibernate Exception" + e.getMessage());
                throw new RuntimeException(e);
            }
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
                 * Regardless of whether the above processing resulted in an Exception
                 * or proceeded normally, we want to close the Hibernate session.  When
                 * closing the session, we must allow for the possibility of a Hibernate
                 * Exception.
                 *
         */ finally {
            if (session != null) {
                try {

                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }
        return result;

    }

    private JSONArray updateToJSONObj(JSONArray result, String Company_name, int countFeedback, String rate, String id_client, String FromDate, String ToDate) {
        for(int i =0; i <result.length(); i++){
            JSONObject comp = result.getJSONObject(i);
            if(comp.getString("label").equalsIgnoreCase(Company_name)){
                comp.put(rate.replaceAll(" ", ""), countFeedback);
//                result.put(comp);
                return result;
                
            }
        }
        return addToJSONObj(result, Company_name, countFeedback, rate, id_client, FromDate, ToDate);
    }

    private JSONArray addToJSONObj(JSONArray result, String Company_name, int countFeedback, String rate,String id_client, String FromDate, String ToDate) {
//        ClientHelper ch = new ClientHelper();
        JSONObject comp = new JSONObject();
        comp.put("label", Company_name);
        comp.put(rate.replaceAll(" ", ""), countFeedback);
        comp.put("id", id_client);
        comp.put("totalProj", ClientHelper.getProjectCountByClient(id_client,FromDate ,ToDate));
        result.put(comp);
        return result;
    }

    private JSONObject processData(JSONArray result) {
        JSONObject report = new JSONObject();
        double totSum = 0.00;
        int totProj = 0;
        double totRespRate =0.00;
        
        for(int i =0; i <result.length(); i++){
            JSONObject comp = result.getJSONObject(i);
            double vp = comp.optDouble(Verypositive.replaceAll(" ", ""),0.00);
            double p = comp.optDouble(Positive.replaceAll(" ", ""),0.00);       
            double sp = comp.optDouble(Somewhatpositive.replaceAll(" ", ""),0.00);                
            double vn = comp.optDouble(Verynegative.replaceAll(" ", ""),0.00);
            double n = comp.optDouble(Negative.replaceAll(" ", ""),0.00);       
            double sn = comp.optDouble(Somewhatnegative.replaceAll(" ", ""),0.00);
           double sum = vp+p+sp+sn+n+vn;
           double respRate = 100*(sum/comp.getInt("totalProj"));
           double respRateVp = 100*(vp/comp.getInt("totalProj"));
           double respRateP = 100*(p/comp.getInt("totalProj"));
           double respRateSp = 100*(sp/comp.getInt("totalProj"));
           double respRateVn = 100*(vn/comp.getInt("totalProj"));
           double respRateN = 100*(n/comp.getInt("totalProj"));
           double respRateSn = 100*(sn/comp.getInt("totalProj"));
           totSum+=sum;totProj+=comp.getInt("totalProj");
           comp.put("sum", StandardCode.getInstance().formatDouble0(sum));
           comp.put("respRate", StandardCode.getInstance().formatDouble(respRate));
           comp.put("respRateVp", StandardCode.getInstance().formatDouble(respRateVp));
           comp.put("respRateP", StandardCode.getInstance().formatDouble(respRateP));
           comp.put("respRateSp", StandardCode.getInstance().formatDouble(respRateSp));
           comp.put("respRateVn", StandardCode.getInstance().formatDouble(respRateVn));
           comp.put("respRateN", StandardCode.getInstance().formatDouble(respRateN));
           comp.put("respRateSn", StandardCode.getInstance().formatDouble(respRateSn));
           comp.put("value", StandardCode.getInstance().formatDouble(sum));
  
        }
        JSONObject comp = new JSONObject();
        comp.put("total", totSum);
        comp.put("respRate", StandardCode.getInstance().formatDouble(100*(totSum/totProj)));
        comp.put("totProj", totProj);
        comp.put("name", "Total");
        report.put("totData",comp);
        report.put("result", result);
        return report;
    }
}
