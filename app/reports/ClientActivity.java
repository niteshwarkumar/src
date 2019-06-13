/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.reports;

import app.db.ConnectionFactory;
import app.project.ProjectService;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
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
public class ClientActivity  extends Action {


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
        String reportData = "";
        String yrQuery="";
        String repCriteria = request.getParameter("repCriteria");
//MAX(IF(`order` = 1, data, NULL)) data1,
//        MAX(IF(`order` = 2, data, NULL)) data2
                        
          reportData+="<thead style=\"font-size: 12px;font-family: Verdana;\"><tr><th>Company Name</th>";              
         Calendar cal= new GregorianCalendar();
        for (int ii = cal.get(Calendar.YEAR); ii > 2004; ii--){
              if(!repCriteria.equalsIgnoreCase("volume"))    
                 yrQuery+="SUM(IF(Year(p.startDate) = "+ii+", p.projectAmount, NULL)) p"+ii+", ";
              else
                 yrQuery+="Count(IF(Year(p.startDate) = "+ii+", p.ID_Project, NULL)) p"+ii+", ";
            
        reportData+="<th>"+ii+"</th>";  
        } 
        
       reportData+="</tr></thead>";  
        String q = "SELECT c.Company_name, p.ID_Client, p.projectAmount, "+yrQuery+" Count(p.number) as cnt " +
"FROM project p inner join client_information c on  p.ID_Client = c.ID_Client " +
"where p.projectAmount is not null group by p.ID_Client " +
"order by c.Company_name  asc";
        
         Session session = ConnectionFactory.getInstance().getSession();

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            
            PreparedStatement st = session.connection().prepareStatement(q);
            ResultSet rs = st.executeQuery();
            reportData+="<tbody style=\"font-size: 11px;font-family: Verdana;\">";  
            while (rs.next()) {
                reportData+="<tr>"; 
                reportData+="<td>"+rs.getString("Company_name")+"</td>"; 
                //System.out.println(rs.getString("Company_name"));
                for (int ii = cal.get(Calendar.YEAR); ii > 2004; ii--){
                    if(null==rs.getString("p"+ii)){
                    reportData+="<td> - </td>";
                    }else
                    if(!repCriteria.equalsIgnoreCase("volume"))    
                        reportData+="<td> $"+StandardCode.getInstance().formatDouble(rs.getDouble("p"+ii))+"</td>"; 
                    else
                        reportData+="<td> "+(rs.getInt("p"+ii))+"</td>"; 
                } 
                reportData+="</tr>"; 
            }
           reportData+="</tbody>"; 
            st.close();
            

        } catch (Exception e) {
            System.err.println("Exception" + e.getMessage());
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

        
        
        
        
        
        
        
        
        
       request.setAttribute("reportData", reportData);
       
       
        
        	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }
    
        public List getClientActivity() {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        String queryString="SELECT c.Company_name, p.ID_Client, p.projectAmount, Year(p.startDate) as yr, Count(p.number) as cnt " +
"FROM project p inner join client_information c on  p.ID_Client = c.ID_Client " +
"where p.projectAmount is not null group by p.ID_Client,Year(p.startDate) " +
"order by c.Company_name  asc, Year(p.startDate) desc";
        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             *///select pathname from Upload_Doc as Upload_Doc where Upload_Doc.QuoteID ="+ CQuote
            query = session.createQuery(queryString);
            // "select quote from app.quote.Quote1 quote where quote.status = 'pending' order by quote.number"
            return query.list();
        } catch (HibernateException e) {
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



    }

}