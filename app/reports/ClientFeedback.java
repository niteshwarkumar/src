/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.reports;

import app.db.ConnectionFactory;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
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

/**
 *
 * @author niteshwar
 */
public class ClientFeedback  extends Action {


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
        String dateC = request.getParameter("dateC");
        String dateB= request.getParameter("dateB");
        String clientId = request.getParameter("clientId");
        String dId = request.getParameter("dId");
        String reportData = "";

        String repCriteria = request.getParameter("repCriteria");
        LocalDate ldt =                     LocalDate.ofYearDay(LocalDate.now().getYear(),1);
        LocalDate now = LocalDate.now();
        String queryStartDate=ldt.getMonthValue()+"/"+ldt.getDayOfMonth()+"/"+ldt.getYear();
        String queryEndDate=now.getMonthValue()+"/"+now.getDayOfMonth()+"/"+now.getYear();

        if(dateB != null){
            queryStartDate=dateB;
        }
        if(dateC != null){
            queryEndDate=dateC;
        }
         queryStartDate = queryStartDate.replaceAll("/", "-");
            String flip[] = queryStartDate.split("-");
            queryStartDate = flip[2] + "-" + flip[0] + "-" + flip[1];

            queryEndDate = queryEndDate.replaceAll("/", "-");
            flip = queryEndDate.split("-");
            queryEndDate = flip[2] + "-" + flip[0] + "-" + flip[1];
        
        String clientQuery = "";
        String dispositionQuery = "";
        if(clientId !=null){
            if(!clientId.equalsIgnoreCase("0")){
                clientQuery = " and c.ID_Client = "+Integer.parseInt(clientId)+" ";
            }
        }
        if(dId !=null){
            if(!dId.equalsIgnoreCase("0")){
                dispositionQuery = " and pi.disposition = '"+dId+"' ";
            }
        }
                        
          reportData+="<thead style=\"font-size: 12px;font-family: Verdana;\"><tr>";
          if(clientQuery.isEmpty()) reportData+="<th>Company Name</th>";
          reportData+="<th>Client Contact</th>"
                  + "<th>Project</th>"
                  + "<th>Lang</th>"
                  + "<th>Rate</th>"
                  + "<th>Disposition</th>"
                  + "<th>Date</th>"
                  + "<th style=\"width:20%\">Feedback</th>"
                  + "<th style=\"width:20%\">Analysis</th>"
                  + "<th style=\"width:20%\">Notes</th>";              
       
        
       reportData+="</tr></thead>";  
        String q = "SELECT c.Company_name,Concat(p.number,c.Company_code) as pNumber, pi.informalDate, pi.clientContact, pi.feedback, pi.fromRole, pi.department, "
                + "pi.language, pi.rate, pi.disposition, pi.cause, pi.notes, pi.textType "
                + "from project_informal pi inner join project p on pi.ID_Project = p.ID_Project "
                + "inner join client_information c on  p.ID_Client = c.ID_Client "
                + "where pi.informalDate between '"+queryStartDate+"' and '"+queryEndDate+"' "+clientQuery+dispositionQuery
                + "order by c.Company_name, Concat(p.number,c.Company_code) desc,pi.informalDate";
        
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
               if(clientQuery.isEmpty()) reportData+="<td>"+rs.getString("Company_name")+"</td>"; 
                reportData+="<td>"+StandardCode.getInstance().noNull(rs.getString("clientContact"))+"</td>"; 
                reportData+="<td>"+StandardCode.getInstance().noNull(rs.getString("pNumber"))+"</td>"; 
                reportData+="<td>"+StandardCode.getInstance().noNull(rs.getString("language"))+"</td>"; 
                reportData+="<td>"+StandardCode.getInstance().noNull(rs.getString("rate"))+"</td>"; 
                reportData+="<td>"+StandardCode.getInstance().noNull(rs.getString("disposition"))+"</td>"; 
                reportData+="<td>"+rs.getString("informalDate")+"</td>"; 
                reportData+="<td>"+StandardCode.getInstance().noNull(rs.getString("feedback"))+"</td>"; 
                reportData+="<td>"+StandardCode.getInstance().noNull(rs.getString("cause"))+"</td>"; 
                reportData+="<td>"+StandardCode.getInstance().noNull(rs.getString("notes"))+"</td>"; 
                
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
       return (mapping.findForward("Success"));
    }
    
       

}