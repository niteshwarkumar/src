/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.admin;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author abc
 */
public class AdminExcelIssueEditAction extends Action {

    // ----------------------------------------------------- Instance Variables
    /**
     * The
     * <code>Log</code> instance for this application.
     */
    private Log log =
            LogFactory.getLog("org.apache.struts.webapp.Example");

    // --------------------------------------------------------- Public Methods
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an
     * <code>ActionForward</code> instance describing where and how control
     * should be forwarded, or
     * <code>null</code> if the response has already been completed.
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


        String issueStatus = request.getParameter("issueStatus");
        String issueJSON = request.getParameter("IssueJSON");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        if ("1".equals(issueStatus)) {
//            AdminService.getInstance().unlinkExcelnetIssue(); 
//        }
        //First delete all products, and then re-insert it
        if (issueJSON != null && !"".equals(issueJSON)) {
            JSONArray issue = new JSONArray(issueJSON);
            for (int i = 0; i < issue.length(); i++) {
                JSONObject j = (JSONObject) issue.get(i);
                 Excelnetissue ei = new Excelnetissue();
                if(!j.getString("id").equalsIgnoreCase("new")){
                   ei = AdminService.getInstance().getSingleExcelnetIssue(Integer.parseInt(j.getString("id"))); 
                }else{
                ei = new Excelnetissue();
                ei.setStatus(1);
                }           
                if ("1".equals(issueStatus)) {
                    ei.setPriority(j.getString("priority")); 
                    ei.setRisk(j.getString("risk")); 
                    
                    ei.setItem(j.getString("item"));
                    ei.setIssue(j.getString("issue"));
                    ei.setReference(j.getString("reference"));

                    try {
                        java.sql.Date d2 = new java.sql.Date(df.parse(j.getString("dateStart")).getTime());
                        ei.setDateStart(d2);
                    } catch (Exception ex) {
                    }
                    try {
                        java.sql.Date d2 = new java.sql.Date(df.parse(j.getString("dateDue")).getTime());
                        ei.setDateDue(d2);
                    } catch (Exception ex) {
                    }
                    ei.setNotes(j.getString("notes"));


                } else if ("2".equals(issueStatus)) {
                    ei.setPriority(j.getString("priority"));
                    ei.setItem(j.getString("item"));
                    ei.setTested(j.getString("tested"));
                    ei.setTestedBy(j.getString("testedBy"));
                    ei.setUploaded(j.getString("uploaded"));
                    ei.setResult(j.getString("result"));
                    ei.setApproval1(j.getString("approval1"));
                    ei.setApproval2(j.getString("approval2"));
                    ei.setApproval3(j.getString("approval3"));
                    ei.setApproval4(j.getString("approval4"));                 //  ei.setFfinal(j.getString("_final"));
                   
                    ei.setLiveTested(j.getString("liveTested"));
                    ei.setTestTested(j.getString("testTested"));
           
                    
                    try {
                        java.sql.Date d2 = new java.sql.Date(df.parse(j.getString("date1")).getTime());
                        ei.setDate1(d2);
                    } catch (Exception ex) {
                    }
                    try {
                        java.sql.Date d2 = new java.sql.Date(df.parse(j.getString("date2")).getTime());
                        ei.setDate2(d2);
                    } catch (Exception ex) {
                    }
                    try {
                        java.sql.Date d2 = new java.sql.Date(df.parse(j.getString("date3")).getTime());
                        ei.setDate3(d2);
                    } catch (Exception ex) {
                    }
                    try {
                        java.sql.Date d2 = new java.sql.Date(df.parse(j.getString("date4")).getTime());
                        ei.setDate4(d2);
                    } catch (Exception ex) {
                    }
                    try {
                        java.sql.Date d2 = new java.sql.Date(df.parse(j.getString("dateFinal")).getTime());
                        ei.setDateFinal(d2);
                    } catch (Exception ex) {
                    }                  


                    ei.setNotes(j.getString("notes"));


                }else if ("3".equals(issueStatus)) {
                ei.setLiveIssue(j.getString("liveIssue"));
                ei.setNotes(j.getString("notes"));
                
                }
                AdminService.getInstance().saveOrUpdateExcelnetissue(ei);

            }

        }



        if ("1".equals(issueStatus)) {
            return (mapping.findForward("Success1"));
        } else if ("2".equals(issueStatus)) {
            return (mapping.findForward("Success2"));
        } else if ("3".equals(issueStatus)) {
            return (mapping.findForward("Success3"));
        }

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
        //return null;
    }
}
