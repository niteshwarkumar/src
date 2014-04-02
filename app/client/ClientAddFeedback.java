/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.client;

import app.admin.AdminService;
import app.admin.Ticker;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;
import app.client.*;
import app.db.ConnectionFactory;
import app.extjs.helpers.ClientHelper;
import app.tools.SendEmail;
import app.user.User;
import app.user.UserService;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Niteshwar
 */
public class ClientAddFeedback extends Action {


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

User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));

//if(request.getParameter("projectInformalsJSON")!=null)
       //   ClientHelper.updateProjectInformals(Integer.parseInt(request.getParameter("projectId")),request.getParameter("projectInformalsJSON"));

    Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;
        String jsonComm="";
        Integer clientId=0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(request.getParameter("clientId")!=null)  {
        clientId=Integer.parseInt(request.getParameter("clientId"));}
        if(request.getParameter("projectInformalsJSON")!=null)  {
        jsonComm=request.getParameter("projectInformalsJSON");}
        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from client_feedback where clientId=?");
            st.setInt(1, clientId);
            st.executeUpdate();

            if(jsonComm!=null && !"".equals(jsonComm)){
        JSONArray comm = new JSONArray(jsonComm);
    //    System.out.println("comm.length()="+comm.length());
            for(int i=0;i< comm.length();i++){
                JSONObject j = (JSONObject)comm.get(i);
                app.client.ClientFeedback pr = new app.client.ClientFeedback();
                pr.setClientId(new Integer(clientId));

                try{pr.setFeedback(j.getString("Feedback"));
                 }catch(Exception e){

                }try{
                pr.setFeedbackDate(sdf.parse(j.getString("feedbackDate")));
                 }catch(Exception e){

                }try{
                pr.setClientContact(j.getString("ClientContact"));
                 }catch(Exception e){

                }
                try{
                pr.setDepartment(j.getString("Department"));
                 }catch(Exception e){

                }
                try{
                pr.setDetails("");
                }catch(Exception e){

                }

                try{
                     pr.setFromRole(j.getString("FromRole"));
                }catch(Exception e){

                }
                 try{
                pr.setLanguage(j.getString("Language"));
                 }catch(Exception e){

                }
                 try{
                pr.setRate(j.getString("Rate"));
                 }catch(Exception e){

                }
                //pr.setSatisfaction(new Integer(j.getString("Satisfaction")));

                 session.save(pr);
                 try {

                 if(j.getString("Details").equalsIgnoreCase("new")){
                             Calendar cal = Calendar.getInstance();
                             SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yy");
                             Client client=ClientService.getInstance().getSingleClient(clientId);


                     String emailMsgTxt="<div>Hello,</div><div>&nbsp;</div><div>This message is automatically generated by ExcelNet.</div><div>&nbsp;</div><b>"
                + u.getFirstName()+" "+u.getLastName() +"</b> has made the following entry in the client module for <b>"+client.getCompany_name()+"</b>:<br>"
+"<br>=============================<br><br>"+sdf1.format(cal.getTime())+" &ndash; <b>"+j.getString("Rate")+"<br>"+j.getString("Feedback")+"<br>"
+"<br>=============================<br><br>Best regards,<br><br>ExcelNet Administrator<br><br>"
                + "<div><img src=http://excelnet.xltrans.com/logo/images/-1168566039logoExcel.gif><div>";

                     String toAddress="";

            List tickerList= AdminService.getInstance().getTickerList(10);
            for(int ii=0;ii<tickerList.size();ii++){
               Ticker ticker = (Ticker) tickerList.get(ii);
               if(toAddress.equalsIgnoreCase("")){try{toAddress =ticker.getUserEmail();}catch(Exception e){}}else{
               try{toAddress +=","+ ticker.getUserEmail();}catch(Exception e){}}
            }
            String[] emailList = toAddress.split(",");

            String emailSubjectTxt="Quality Alert: "+client.getCompany_name();

            SendEmail smtpMailSender = new SendEmail();
            smtpMailSender.postMail( emailList, emailSubjectTxt, emailMsgTxt, "excelnet@xltrans.com");
            }
         } catch (Exception e) {
           }


            }
        }


            tx.commit();
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

    // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}

