/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.project;

import app.comm.EmailHelper;
import app.quote.Quote1;
import app.quote.QuoteService;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import java.util.List;
import java.util.ListIterator;
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

/**
 *
 * @author niteshwar
 */
public class HandOffEmail extends Action {

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
        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
        //START get id of current project from either request, attribute, or cookie 
        //id of project from request
        String projectId = null;
        projectId = request.getParameter("projectViewId");

        //check attribute in request
        if (projectId == null) {
            projectId = (String) request.getAttribute("projectViewId");
        }

        //id of project from cookie
        if (projectId == null) {
            projectId = StandardCode.getInstance().getCookie("projectViewId", request.getCookies());
        }

        //default project to last if not in request or cookie
        if (projectId == null || "null".equals(projectId)) {
            List results = ProjectService.getInstance().getProjectList();

            ListIterator iterScroll = null;
            for (iterScroll = results.listIterator(); iterScroll.hasNext(); iterScroll.next()) {
            }
            iterScroll.previous();
            Project p = (Project) iterScroll.next();
            projectId = String.valueOf(p.getProjectId());
        }

        Integer id = Integer.valueOf(projectId);

        //END get id of current project from either request, attribute, or cookie 
        //get project to edit
        Project p = ProjectService.getInstance().getSingleProject(id);

        Quote1 q = QuoteService.getInstance().getSingleQuoteFromProject(p.getProjectId());

        String taskString = "";
        String taskName = "";
        String linTaskName = "", dtpTaskName = "", engTaskName = "", othTaskName = "";
        Integer taskCounter = 0;
        
        List technicalList = ProjectService.getInstance().getProjectTechnicalList(p.getProjectId());

//Linguistic
        List taskList = ProjectService.getInstance().getLinTaskListforProject(p.getProjectId());
        if (taskList.size() > 0) {
            linTaskName += "<div style='display: table-cell;padding-right:10px' valign='top'><font color='blue'>Linguistic:&nbsp</font>";
        }
        for (int t = 0; t < taskList.size(); t++) {
            taskName = (String) taskList.get(t);
            linTaskName += "" + taskName + "";
            if (t < taskList.size() - 1) {
                linTaskName += ", ";
            }
        }
        if (taskList.size() > 0) {
            linTaskName += "</div>";
            taskCounter += taskList.size();
        }

//DTP
        taskList = ProjectService.getInstance().getDTPTaskListforProject(p.getProjectId());
        if (taskList.size() > 0) {
            dtpTaskName += "<div style='display: table-cell;padding-right:10px' valign='top'><font color='blue'>DTP:&nbsp</font>";
        }
        for (int t = 0; t < taskList.size(); t++) {
            taskName = (String) taskList.get(t);
            dtpTaskName += "" + taskName + "";
            if (t < taskList.size() - 1) {
                dtpTaskName += ", ";
            }
        }
        if (taskList.size() > 0) {
            dtpTaskName += "</div>";
            taskCounter += taskList.size();
        }
//Engg
        taskList = ProjectService.getInstance().getEngTaskListforProject(p.getProjectId());
        if (taskList.size() > 0) {
            engTaskName += "<div style='display: table-cell;padding-right:10px' valign='top'><font color='blue'>Engineering:&nbsp</font>";
        }
        for (int t = 0; t < taskList.size(); t++) {
            taskName = (String) taskList.get(t);
            engTaskName += "" + taskName + "";
            if (t < taskList.size() - 1) {
                engTaskName += ", ";
            }
        }
        if (taskList.size() > 0) {
            engTaskName += "</div>";
            taskCounter += taskList.size();
        }
//Other
        taskList = ProjectService.getInstance().getOthTaskListforProject(p.getProjectId());
        if (taskList.size() > 0) {
            othTaskName += "<div style='display: table-cell;padding-right:10px' valign='top'><font color='blue'>Other:&nbsp</font>";
        }
        for (int t = 0; t < taskList.size(); t++) {
            taskName = (String) taskList.get(t);
            othTaskName += "" + taskName + "";
            if (t < taskList.size() - 1) {
                othTaskName += ", ";
            }
        }
        if (taskList.size() > 0) {
            othTaskName += "</div>";
            taskCounter += taskList.size();
        }
        taskString += linTaskName + dtpTaskName + engTaskName + othTaskName + "";

        String techString = "";
//            List technicalList = ProjectService.getInstance().getProjectTechnicalList(p.getProjectId());
        for (int i = 0; i < technicalList.size(); i++) {
            Project_Technical t = (Project_Technical) technicalList.get(i);
            techString += "<div style='display: table-cell;padding-right:10px' valign='top'><font color='blue'>Source:</font> " + t.getSourceos() + "/" + t.getSourceapp() + "/" + t.getSourcever() + " -> <font color='blue'>Target: </font>" + t.getTargetos() + "/" + t.getTargetapp() + "/" + t.getTargetver() + "</div>";
        }

        Double clientInviceTotalEuro = 0.0;
        try {
            clientInviceTotalEuro = p.getProjectAmount() / p.getEuroToUsdExchangeRate();
        } catch (Exception e) {
            clientInviceTotalEuro = 0.0;
        }
        String req = "";
        String ins = "";

        
        String emailMsgTxt = "<html>";
        emailMsgTxt += "<head>"+EmailHelper.getInstance().getReqAnalysisEmailCss()+"</head>";
            emailMsgTxt += "<body  style=\"padding: 5px;font-size: 10.0pt;\">";
        
            if (null != q) {
            emailMsgTxt += "<div   style='padding:5px;'>Hello,<br> "
                    + "<br> Quote " + q.getNumber() + " was approved. The project number is  " + p.getNumber() + p.getCompany().getCompany_code()
                    + "<br><br><font color='blue'><b>Engineering:</b></font>"
                    + "&nbsp;&nbsp;&nbsp; Please confirm the ToTrans folder is ready."
                    + "<br>";
        } else {
            emailMsgTxt += "<div   style='padding:5px;'>Hello,<br> "
                    + "<br> The project number is  " + p.getNumber() + p.getCompany().getCompany_code()
                    + "<br><br><font color='blue'><b>Engineering:</b></font>"
                    + "&nbsp;&nbsp;&nbsp; Please Confirm the ToTrans folder is ready."
                    + "<br>";
        }
        emailMsgTxt += "<br><div align='left'><font color='blue'><b> Quick Project Overview: </b></font></div>";
        emailMsgTxt += "<div><hr></div>";
       emailMsgTxt +=  "<table>"+EmailHelper.getInstance().getReqAnalysisEmailProduct(p)
                    + "<tr><td class='wdth200'><b>Authorization</b></td><td>" + p.getClientAuthorization() + " " + p.getClientPO() + "</td></tr>"
                    + "</table>"
                    + "<br>"+EmailHelper.getInstance().getReqAnalysisEmailLanguage(q, p, false,"")+ "<br>";
                   
         emailMsgTxt += "<div><hr></div>";
        emailMsgTxt += "<table><tr><td class='wdth200'><b>Task(" + taskCounter + ")</b>:</td><td>" + taskString + "</td></tr>";
        emailMsgTxt += "<tr><td colspan='5'><hr></td></tr>";
        if (!techString.equalsIgnoreCase("")) {
            emailMsgTxt += "<tr><td class='wdth200'><b>Format</b>:</td><td>" + techString + "</div><br>";
        }
        emailMsgTxt += "<tr><td><b>AE</b>:</td><td>" + StandardCode.getInstance().noNull(p.getAe()) + "</td></tr>";
        if (!p.getCompany().getCcurrency().equalsIgnoreCase("EURO")) {
            emailMsgTxt += "<tr><td class='wdth200'><b>Client Fee</b>:</td><td>" + StandardCode.getInstance().formatDouble(p.getProjectAmount()) + " USD</td></tr>";
        } else {
            emailMsgTxt += "<tr><td class='wdth200'><b>Client Fee</b>:</td><td>" + StandardCode.getInstance().formatDouble(clientInviceTotalEuro) + " Euro</td></tr>";
        }
         emailMsgTxt += "</table>";
         emailMsgTxt += "<br><div style='display: table;'><font color='darkred'><b> Further Instructions or Details: </b></font></div><br>";

        String recipientEmailId = "";

      
        emailMsgTxt += EmailHelper.getInstance().getReqAnalysisEmailBody(p, q, "project");


        emailMsgTxt += EmailHelper.getInstance().getReqAnalysisEmailFooter(u);
        emailMsgTxt += "</body></html>";

        String recipientCCEmailId = "rdecortie@xltrans.com";
        if (u.getUserId() != 3) {
            recipientCCEmailId += "," + u.getWorkEmail1();
        }
        User pm = UserService.getInstance().getSingleUser(p.getPm_id());

        try {
            if (!recipientCCEmailId.contains(pm.getWorkEmail1())) {
                recipientCCEmailId += "," + pm.getWorkEmail1();
            }
        } catch (Exception e) {
            recipientCCEmailId += "," + e.getMessage();
        }
        try {
            String[] aeEmail = (p.getAe()).split(" ", 2);
            User ae = UserService.getInstance().getSingleCurrentUserRealName(aeEmail[0], aeEmail[1]);
            if (!recipientCCEmailId.contains(ae.getWorkEmail1())) {
                recipientCCEmailId += "," + ae.getWorkEmail1();
            }
        } catch (Exception e) {
        }

        String emailSubjectTxt = "";
        if (null != q) {
            emailSubjectTxt = "" + p.getNumber() + p.getCompany().getCompany_code() + " (" + q.getNumber() + ") - Hand-Off to Production - " + p.getCompany().getCompany_name();
        } else {
            emailSubjectTxt = "" + p.getNumber() + p.getCompany().getCompany_code() + " - Hand-Off to Production - " + p.getCompany().getCompany_name();
        }

        if (recipientEmailId.equalsIgnoreCase("")) {
            recipientEmailId = "engineering@xltrans.com";
        }
        if(EmailHelper.getInstance().ifReqAnalysisEmailDtpAddress(p.getProjectId()))
            recipientEmailId += ",dtp@xltrans.com";

        request.setAttribute("emailMsgTxt", emailMsgTxt);
        request.setAttribute("recipientEmailId", recipientEmailId);
        request.setAttribute("recipientCCEmailId", recipientCCEmailId);
        request.setAttribute("senderId", EmailHelper.senderId);
        request.setAttribute("emailSubjectTxt", emailSubjectTxt);
        request.setAttribute("project", p);
        response.addCookie(StandardCode.getInstance().setCookie("projectViewId", String.valueOf(p.getProjectId())));

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
