/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.inteqa;

import app.comm.EmailHelper;
import app.project.Project;
import app.project.ProjectService;
import app.quote.Quote1;
import app.quote.QuoteService;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import java.util.ArrayList;
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
public class RequestAnalysisAction extends Action {

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
        String porq = request.getParameter("porq");

        List technicalList = new ArrayList();

        request.setAttribute("porq", porq);
        if (projectId == null) {
            projectId = request.getParameter("projectViewId");
        }
        //check attribute in request
        if (projectId == null) {
            projectId = (String) request.getAttribute("projectViewId");
        }

        //id of project from cookie
        if (projectId == null) {
            projectId = StandardCode.getInstance().getCookie("projectViewId", request.getCookies());
        }

        //default project to last if not in request or cookie
        if (projectId == null) {
            List results = ProjectService.getInstance().getProjectList();

            ListIterator iterScroll = null;
            for (iterScroll = results.listIterator(); iterScroll.hasNext(); iterScroll.next()) {
            }
            iterScroll.previous();
            Project p = (Project) iterScroll.next();
            projectId = String.valueOf(p.getProjectId());
        }

        Integer id = Integer.valueOf(projectId);
//        boolean isChange= false;
        boolean isChange = false;
        String changeName = "C1";

        //END get id of current project from either request, attribute, or cookie
        //get project to edit
        Project p = ProjectService.getInstance().getSingleProject(id);
        Quote1 q = QuoteService.getInstance().getSingleQuoteFromProject(id);
       String emailMsgTxt ="";
//        emailMsgTxt = "<html>";
//        emailMsgTxt += "<head></head>";
//        emailMsgTxt += "<body style='padding:5px; font-size: 10.0pt; font-family: verdana,tahoma,arial,helvetica,sans-serif;'>"+EmailHelper.getInstance().getReqAnalysisEmailCss()+ "";

        emailMsgTxt = "<div style='padding: 5px;font-size: 10.0pt;font-family: verdana,tahoma,arial,helvetica,sans-serif;'>"+EmailHelper.getInstance().getReqAnalysisEmailCss()+ "";
         emailMsgTxt += "<div>Hello,<br> ";
        if (!isChange) {
            if(q!=null){
            emailMsgTxt += "<br>Request Analysis for Quote No: " + q.getNumber() + " - " + u.getFirstName() + " " + u.getLastName() + "";
            }else{
           emailMsgTxt += "<br>Request Analysis for Project No: " + p.getNumber() + p.getCompany().getCompany_code() + " - " + u.getFirstName() + " " + u.getLastName() + "";
            }       
        } else {
            emailMsgTxt += "<br>Request Analysis for Change: " + changeName + " of Project:" + p.getNumber() + p.getCompany().getCompany_code() + " ";
                    
        }
        
         emailMsgTxt += "<br><br><table>"+EmailHelper.getInstance().getReqAnalysisEmailPriority(q)+EmailHelper.getInstance().getReqAnalysisEmailProduct(p)
                    + "</table>"
                    + "<br>"+EmailHelper.getInstance().getReqAnalysisEmailLanguage(q, p, isChange,"")+ "<br>";
       
         emailMsgTxt += EmailHelper.getInstance().getReqAnalysisEmailBody(p, q, porq);

        emailMsgTxt += EmailHelper.getInstance().getReqAnalysisEmailFooter(u);
        emailMsgTxt += "</div>";
//        emailMsgTxt += "</body></html>";
        System.out.println(emailMsgTxt);
        String recipientEmailId = "engineering@xltrans.com";

        String recipientCCEmailId = u.getWorkEmail1();

        String emailSubjectTxt = q.getNumber() + " - Request Analysis - " + u.getFirstName() + " " + u.getLastName() + " - " + p.getCompany().getCompany_name();
        if (EmailHelper.getInstance().ifReqAnalysisEmailDtpAddress(p.getProjectId())) {
            recipientEmailId += ",dtp@xltrans.com";
        }
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
