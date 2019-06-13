/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.project;

import app.client.ClientNotes;
import app.client.ClientService;
import app.comm.EmailHelper;
import app.extjs.global.LanguageAbs;
import static app.extjs.helpers.ProjectHelper.noNull;
import app.inteqa.INBasics;
import app.inteqa.INDelivery;
import app.inteqa.INDeliveryReq;
import app.inteqa.INDtp;
import app.inteqa.INEngineering;
import app.inteqa.INReference;
import app.inteqa.INSourceFile;
import app.inteqa.InteqaService;
//import app.quote.Quote1;
import app.quote.QuotePriority;
//import app.quote.QuoteService;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
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
public class ChangeHandOffEmail  extends Action {

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
        boolean isChange= true;
        String changeName = "";
        String changeNumber = "";
        String chg = request.getParameter("changeId");
        String changeDesc = request.getParameter("changeDesc");
        request.setAttribute("changeDesc", changeDesc);
        String destination = request.getParameter("destination");
         String changeDescription = "";
         String changeFileDescription = "";
        request.getAttribute("destination");
        if (null != chg) {
              int chgId = Integer.parseInt(chg);
              request.setAttribute("changeId", chgId);
              Change1 change = ProjectService.getInstance().getSingleChange(chgId);
              changeNumber = change.getNumber();
              changeName = StandardCode.getInstance().noNull(change.getName());
              if(changeName.isEmpty())
                changeName = change.getNumber();
              changeDescription = StandardCode.getInstance().noNull(change.getDescription());   
              changeFileDescription = StandardCode.getInstance().noNull(change.getLocationFiles()).replaceAll("\\\\", "\\\\\\\\");
        }     

        //END get id of current project from either request, attribute, or cookie 
        //get project to edit
        Project p = ProjectService.getInstance().getSingleProject(id);

//        Quote1 q = QuoteService.getInstance().getSingleQuoteFromProject(p.getProjectId());

        Set sourcelang1 = p.getSourceDocs();
//                                 List sourcelang1 = ProjectService.getInstance().getSourceLang1(q);
        StringBuffer sources = new StringBuffer("");
        StringBuffer targets = new StringBuffer("");
        String targetAbb = "";
        List<String> tgtLst = new ArrayList();
        List<String> srcLst = new ArrayList();
        List targetList = new ArrayList();
        String taskString = "";
        String taskName = "";
        String linTaskName = "", dtpTaskName = "", engTaskName = "", othTaskName = "";
         List<String> linTaskNameLst = new ArrayList(), dtpTaskNameLst = new ArrayList(), engTaskNameLst = new ArrayList(), othTaskNameLst = new ArrayList();
        if (p.getSourceDocs() != null) {
            for (Iterator iterSource = sourcelang1.iterator(); iterSource.hasNext();) {
                SourceDoc sd = (SourceDoc) iterSource.next();
//                sources.append(sd.getLanguage() + "  ");
                if (sd.getTargetDocs() != null) {
                    for (Iterator iterTarget = sd.getTargetDocs().iterator(); iterTarget.hasNext();) {
                        TargetDoc td = (TargetDoc) iterTarget.next();
                        if (!td.getLanguage().equals("All")) {
//                            targets.append(td.getLanguage() + "  ");
//                            targetList.add((String) LanguageAbs.getInstance().getAbs().get(td.getLanguage()));
                            
                                Set<LinTask> linTask = td.getLinTasks();
                                Set<DtpTask> dtpTask = td.getDtpTasks();
                                Set<EngTask> engTask = td.getEngTasks();
                                Set<OthTask> othTask = td.getOthTasks();
                                for (LinTask lt : linTask) {
                                    //System.out.println("&&&"+lt.getTaskName()+lt.getChangeDesc()+td.getLanguage()+sd.getLanguage());
                                    if (StandardCode.getInstance().noNull(lt.getChangeDesc()).equalsIgnoreCase(changeNumber)) {
                                        if (!tgtLst.contains(td.getLanguage())) {
                                            tgtLst.add(td.getLanguage());
                                            targets.append(td.getLanguage()).append("  ");
                                            targetList.add((String) LanguageAbs.getInstance().getAbs().get(td.getLanguage()));
                                        }
                                        if (!srcLst.contains(sd.getLanguage())) {
                                            srcLst.add(sd.getLanguage());
                                            sources.append(sd.getLanguage()).append("  ");
                                        }
                                        if(!linTaskNameLst.contains(lt.getTaskName())){
                                            linTaskNameLst.add(lt.getTaskName());
                                        }

                                    }
                                }
                                for (DtpTask lt : dtpTask) {
                                    //System.out.println("&&&"+lt.getTaskName()+lt.getChangeDesc()+td.getLanguage()+sd.getLanguage());
                                    if (StandardCode.getInstance().noNull(lt.getChangeDesc()).equalsIgnoreCase(changeNumber)) {
                                        if (!tgtLst.contains(td.getLanguage())) {
                                            tgtLst.add(td.getLanguage());
                                            targets.append(td.getLanguage()).append("  ");
                                            targetList.add((String) LanguageAbs.getInstance().getAbs().get(td.getLanguage()));
                                        }
                                        if (!srcLst.contains(sd.getLanguage())) {
                                            srcLst.add(sd.getLanguage());
                                            sources.append(sd.getLanguage()).append("  ");
                                        }
                                        if(!dtpTaskNameLst.contains(lt.getTaskName())){
                                            dtpTaskNameLst.add(lt.getTaskName());
                                        }

                                    }
                                }
                                for (EngTask lt : engTask) {
                                    //System.out.println("&&&"+lt.getTaskName()+lt.getChangeDesc()+td.getLanguage()+sd.getLanguage());
                                    if (StandardCode.getInstance().noNull(lt.getChangeDesc()).equalsIgnoreCase(changeNumber)) {
                                        if (!tgtLst.contains(td.getLanguage())) {
                                            tgtLst.add(td.getLanguage());
                                            targets.append(td.getLanguage()).append("  ");
                                            targetList.add((String) LanguageAbs.getInstance().getAbs().get(td.getLanguage()));
                                        }
                                        if (!srcLst.contains(sd.getLanguage())) {
                                            srcLst.add(sd.getLanguage());
                                            sources.append(sd.getLanguage()).append("  ");
                                        }
                                        if(!engTaskNameLst.contains(lt.getTaskName())){
                                            engTaskNameLst.add(lt.getTaskName());
                                        }

                                    }
                                }
                        }
                    }
                }
            }
        }
        Collections.sort(targetList);
        for (int i = 0; i < targetList.size(); i++) {
            if (targetAbb.equalsIgnoreCase("")) {
                targetAbb += targetList.get(i);
            } else {
                targetAbb += ", " + targetList.get(i);
            }
        }
        targetAbb += " (" + targetList.size() + ")";

//targetsString+="</div>";
        
        Integer taskCounter = 0;
        INDtp indtp = InteqaService.getInstance().getINDtp(p.getProjectId());
        INBasics inbasic = InteqaService.getInstance().getINBasics(p.getProjectId());
        INEngineering inengg = InteqaService.getInstance().getINEngineering(p.getProjectId());
       
        List technicalList = ProjectService.getInstance().getProjectTechnicalList(p.getProjectId());

        List linguisticStyles = ClientService.getInstance().getNotesList(p.getCompany().getClientId(), "LIN");
        List billingReqs = ClientService.getInstance().getBillingReqListForClient(p.getCompany().getClientId().toString());
        List regulatoryList = ClientService.getInstance().getRegulatoryListForClient(p.getCompany().getClientId().toString());
        List auditList = ClientService.getInstance().getAuditListForClient(p.getCompany().getClientId().toString());
        List serviceList = ClientService.getInstance().getServiceListForClient(p.getCompany().getClientId().toString());
        List techStyles = ClientService.getInstance().getNotesList(p.getCompany().getClientId(), "TECH");

//Linguistic
        List taskList = linTaskNameLst;
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
        taskList = dtpTaskNameLst;
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
        taskList = engTaskNameLst;
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

        INDelivery indel = InteqaService.getInstance().getINDelivery(p.getProjectId());
        try {
            List inDelR = InteqaService.getInstance().getInDeliveryReqGrid(indel.getId(), "R");

            for (int i = 0; i < inDelR.size(); i++) {
                INDeliveryReq inDeliveryReq = (INDeliveryReq) inDelR.get(i);
                req += "<div>" + inDeliveryReq.getClientReqText() + "</div>";
            }

            List inDelI = InteqaService.getInstance().getInDeliveryReqGrid(indel.getId(), "I");

            for (int i = 0; i < inDelI.size(); i++) {
                INDeliveryReq inDeliveryReq = (INDeliveryReq) inDelI.get(i);
                ins += "<div>" + inDeliveryReq.getClientReqText() + "</div>";
            }
        } catch (Exception e) {
        }
        String emailMsgTxt = "<html>";
        emailMsgTxt += "<head>"+EmailHelper.getInstance().getReqAnalysisEmailCss()+"</head>";

      
            emailMsgTxt += "<div   style='padding:5px;font-size: 10.0pt;'>Hello,<br> "
                    + "<br> Change " + changeName + " was approved. The project number is  " + p.getNumber() + p.getCompany().getCompany_code();
            
            if(!changeDescription.isEmpty()){
                    emailMsgTxt +=   "<br><b>Description ["+changeName+"]:</b>  " + changeDescription;
                }
            if(!changeFileDescription.isEmpty()){
                    emailMsgTxt +=   "<br><b>Location Files ["+changeName+"]:</b>  " + changeFileDescription;
                }
                   emailMsgTxt += "<br><br><font color='red'><b>Engineering:</b></font>"
                    + "<br>Please confirm the ToTrans folder is ready."
                    + "<br>";
        
         emailMsgTxt += "<br><div align='left'><font color='blue'><b> Quick Project Overview: </b></font></div>";
        emailMsgTxt += "<div><hr></div>";
       emailMsgTxt +=  "<table>"+EmailHelper.getInstance().getReqAnalysisEmailProduct(p)
                    + "<tr><td class='wdth200'><b>Authorization</b></td><td>" + p.getClientAuthorization() + " " + p.getClientPO() + "</td></tr>"
                    + "</table>"
                    + "<br>"+EmailHelper.getInstance().getReqAnalysisEmailLanguage(null, p, false,"")+ "<br>";
        emailMsgTxt += "<div><hr></div>";
        emailMsgTxt += "<div style='display: table;'>Task(" + taskCounter + "):&nbsp;&nbsp;&nbsp;      " + taskString + "</div>";
        emailMsgTxt += "<div><hr></div>";
        if (!techString.equalsIgnoreCase("")) {
            emailMsgTxt += "<div style='display: table;'>Format:      &nbsp;&nbsp;&nbsp;" + techString + "</div><br>";
        }
        emailMsgTxt += "<div style='display: table;'>AE:       &nbsp;&nbsp;&nbsp;" + StandardCode.getInstance().noNull(p.getAe()) + "</div>";
        if (!p.getCompany().getCcurrency().equalsIgnoreCase("EURO")) {
            emailMsgTxt += "<div style='display: table;'>Client Fee:    &nbsp;&nbsp;&nbsp;  " + StandardCode.getInstance().formatDouble(p.getProjectAmount()) + " USD</div>";
        } else {
            emailMsgTxt += "<div style='display: table;'>Client Fee:     &nbsp;&nbsp;&nbsp;  " + StandardCode.getInstance().formatDouble(clientInviceTotalEuro) + " Euro</div>";
        }

        emailMsgTxt += "<br><div style='display: table;'><font color='red'><b> Further Instructions or Details: </b></font></div><br><br>";

        String recipientEmailId = "";


        emailMsgTxt += EmailHelper.getInstance().getReqAnalysisEmailBody(p, null, "project");


        emailMsgTxt += EmailHelper.getInstance().getReqAnalysisEmailFooter(u);
        emailMsgTxt += "</body></html>";
//            String recipientEmailId = "engineering@xltrans.com,dtp@xltrans.com";

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
        emailSubjectTxt = "" + p.getNumber() + p.getCompany().getCompany_code() + " (Change:" + changeName + ") - Hand-Off to Production - " + p.getCompany().getCompany_name();
        

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
