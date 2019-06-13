/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.reports;

import app.db.ConnectionFactory;
import app.security.SecurityService;
import app.standardCode.ExcelConstants;
import app.standardCode.StandardCode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
 * @author Nishika
 */
public class FeedBackReport  extends Action {

 
       
        String[] rateType = {"Very positive","Positive","Somewhat positive","Somewhat negative","Negative","Very negative"};
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
            return (mapping.findForward("accessDenied"));}
       String language = request.getParameter("language");
       String client = request.getParameter("client");
       String textType = request.getParameter("textType");
       String pm = request.getParameter("projectManager");
       if(null == language) language = "All";
       if(null == client) client = "0";
       if(null == textType) textType = "All";
       if(null == pm) pm = "All";
       
       
int feedBackStartYear = ExcelConstants.FEEDBACK_STARTYEAR;

        JSONArray s0 = new JSONArray(); //Very positive
        JSONArray s1 = new JSONArray(); //Somewhat positive
        JSONArray s2 = new JSONArray(); //Positive
        JSONArray s4 = new JSONArray(); //Negative
        JSONArray s5 = new JSONArray(); //Somewhat negative

        JSONArray s6 = new JSONArray(); //Very negative

        Calendar cal= new GregorianCalendar();
       
            int cnt =0;
             JSONObject data = new JSONObject();
            for (int ii = feedBackStartYear; ii <= cal.get(Calendar.YEAR); ii++){
               
                 data = new JSONObject();data.put("x",  ii);data.put("y", 0);s0.put(data);
               
                data = new JSONObject();data.put("x",  ii);data.put("y", 0);s2.put(data);
                 data = new JSONObject();data.put("x",  ii);data.put("y", 0);s1.put(data);
//                data = new JSONObject();data.put("x",  ii);data.put("y", 0);s3.put(data);
                  data = new JSONObject();data.put("x",  ii);data.put("y", 0);s5.put(data);
                data = new JSONObject();data.put("x",  ii);data.put("y", 0);s4.put(data);
               
                data = new JSONObject();data.put("x",  ii);data.put("y", 0);s6.put(data);
               
                //System.out.println("cnt"+cnt);
                cnt++;
                
            }
        
            JSONArray report = getChartData(s0,s1,s2,s4,s5,s6,language,Integer.parseInt(client.trim()),textType,pm,cnt);
        request.setAttribute("reportData", report);
//        request.setAttribute("reportDataPie", pieReport);
//        request.setAttribute("reportCat", reportCat);
        
        	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

  private JSONArray getChartData( JSONArray s0, JSONArray s1, JSONArray s2, JSONArray s4, JSONArray s5, JSONArray s6, 
          String language, int client, String textType, String pm, int cnt) {
    
        JSONArray result = new JSONArray();
        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;
       
        try {

            tx = session.beginTransaction();
            String languageQuery = "";
            String textTypeQuery = "";
            String clientQuery = "";
            String pmQuery = "";
if(!"All".equalsIgnoreCase(language.trim())){
  languageQuery = " AND language = '"+ language +"' ";
}
if(0!=client){
  clientQuery = " AND p.ID_Client = "+ client +" ";
}
if(!"All".equalsIgnoreCase(textType.trim())){
  textTypeQuery = " AND textType = '"+ textType.trim() +"' ";
}
if(!"All".equalsIgnoreCase(pm.trim())){
  pmQuery = " AND p.pm = '"+ pm +"' ";
}

             String queryString="SELECT COUNT(0) countFeedback,YEAR(informalDate) yr,rate " +
                            "FROM project_informal i INNER JOIN project p ON i.ID_Project = p.ID_Project \n" +
                            "WHERE rate IS NOT NULL AND rate <> '' AND rate <> 'Neutral' "+clientQuery+textTypeQuery+languageQuery+pmQuery+
                            "GROUP BY YEAR(informalDate),rate\n" +
                            "ORDER BY YEAR(informalDate) DESC,rate desc";
         PreparedStatement pstmt = session.connection().prepareStatement(queryString);
         ResultSet rs = pstmt.executeQuery();
                 

           while(rs.next())
            {
            int year = rs.getInt("yr");
//            if(!"Neutral".equalsIgnoreCase(rs.getString("rate"))){  
            for(int i = 0;i<cnt;i++){
                     
              JSONObject valueObj = new JSONObject();
            if("Very positive".equalsIgnoreCase(rs.getString("rate"))) valueObj = s0.getJSONObject(i);
          
            if("Positive".equalsIgnoreCase(rs.getString("rate"))) valueObj = s2.getJSONObject(i);
              if("Somewhat positive".equalsIgnoreCase(rs.getString("rate")))  valueObj = s1.getJSONObject(i);
              if("Somewhat negative".equalsIgnoreCase(rs.getString("rate"))) valueObj = s5.getJSONObject(i);
            if("Negative".equalsIgnoreCase(rs.getString("rate"))) valueObj = s4.getJSONObject(i);
            
            if("Very negative".equalsIgnoreCase(rs.getString("rate"))) valueObj = s6.getJSONObject(i);
//            if("Neutral".equalsIgnoreCase(rs.getString("rate"))) valueObj = s3.getJSONObject(i);
            if(year == valueObj.getInt("x")){
                        valueObj.remove("y");
                        valueObj.put("y", rs.getInt("countFeedback"));
                        }
                        
       
//            }
              }
            }
            tx.commit();
            String[] color = ExcelConstants.REPORT_CHART_COLOR_SEQUENCE_FEEDBACK;
             for(int src = 0; src< rateType.length;src++){
                JSONObject maindata = new JSONObject();
                maindata.put("key", rateType[src]);
                if(src == 0) {maindata.put("values",s0);}
                if(src == 2) {maindata.put("values",s2);}
                 if(src == 1) {maindata.put("values",s1);}
//                if(src == 3) {maindata.put("values",s3);}
                 if(src == 4) {maindata.put("values",s4);}
                if(src == 3) {maindata.put("values",s5);}
                
                if(src == 5) {maindata.put("values",s6);}
                
                maindata.put("color", color[src]);
                result.put(maindata);
//                pieReport.put(maindataPie);
           
            }
            
            
            
        }
        catch (Exception e) {
            try {
                tx.rollback(); //error
            }
            catch (HibernateException he) {
                System.err.println("Hibernate Exception" + e.getMessage());
                throw new RuntimeException(e);
            }
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        }
                /*
                 * Regardless of whether the above processing resulted in an Exception
                 * or proceeded normally, we want to close the Hibernate session.  When
                 * closing the session, we must allow for the possibility of a Hibernate
                 * Exception.
                 *
                 */
        finally {
            if (session != null) {
                try {

                    session.close();
                }
                catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }
      return result;
  }
        
        

}

