/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import app.admin.AdminService;
import app.admin.OptionsSelect;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
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
public class GenerateChangeWord extends Action {

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

        String number = request.getParameter("number");
        StrategicChange sc = QMSService.getInstance().getSingleStrategicChange(number);
        StrategicChange_Id scId = QMSService.getInstance().getSingleStrategicChange_Id(number);
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");

        String templateLocation = StandardCode.getInstance().getPropertyValue("mac.templates.path");
        byte[] template = getBytesFromFile(new java.io.File(templateLocation + "templates2/Strategic-Change.rtf"));

        String content = new String(template);

        content = content.replaceAll("INSERT_ID_NO_INSERT", sc.getNumber());
        try {
            content = content.replaceAll("INSERT_DATE_INSERT", DateFormat.getDateInstance(DateFormat.SHORT).format(sc.getCdate()));
        } catch (Exception e) {
            content = content.replaceAll("INSERT_DATE_INSERT", "");
        }
        content = content.replaceAll("INSERT_OWNER1_INSERT", sc.getEmployee());
        content = content.replaceAll("INSERT_REQUESTER_INSERT", sc.getReportedby());
        content = content.replaceAll("INSERT_STATUS_INSERT", sc.getStatus());
        content = content.replaceAll("INSERT_ADMINDESCRIPTION_INSERT", sc.getDescription());

        content = content.replaceAll("INSERT_DESCRIPTION_INSERT", scId.getDescription());
        JSONArray tgtAud = AdminService.getInstance().getOptions(OptionsSelect.TGTAUD,scId.getSid());
        JSONArray prrAff = AdminService.getInstance().getOptions(OptionsSelect.PRSAFF,scId.getSid());
         List<String> tgt = new ArrayList<>(); 
        for(int i = 0; i< tgtAud.length();i++){  
         JSONObject option = tgtAud.getJSONObject(i);
         JSONArray childList = option.getJSONArray("child");
         if(option.optString("status").equalsIgnoreCase("checked")){
           String title =""; List<String> csb = new ArrayList<>();
            for(int c = 0; c< childList.length();c++){  
              JSONObject child = childList.getJSONObject(c);
             
                if(child.optString("status").equalsIgnoreCase("checked")){
                    csb.add(child.getString("title"));
                }
            }
             if(!csb.isEmpty()){
                     title ="("+StringUtils.join(csb, ", ")+")";
                }
             tgt.add(option.getString("title")+title);
         }}
        
        List<String> prc = new ArrayList<>(); 
        for(int i = 0; i< prrAff.length();i++){  
         JSONObject option = prrAff.getJSONObject(i);
         JSONArray childList = option.getJSONArray("child");
         if(option.optString("status").equalsIgnoreCase("checked")){
           String title =""; List<String> csb = new ArrayList<>();
            for(int c = 0; c< childList.length();c++){  
              JSONObject child = childList.getJSONObject(c);
             
                if(child.optString("status").equalsIgnoreCase("checked")){
                    csb.add(child.getString("title"));
                }
            }
             if(!csb.isEmpty()){
                     title ="("+StringUtils.join(csb, ", ")+")";
                }
             prc.add(option.getString("title")+title);
         }}
        content = content.replaceAll("INSERT_TARGETAUDIENCE_INSERT", StringUtils.join(tgt, ", "));
        content = content.replaceAll("INSERT_TARGETAUDIENCE_NOTES_INSERT", scId.getTarget());
        content = content.replaceAll("INSERT_PROCESSAFFECTED_INSERT", StringUtils.join(prc, ", "));
        content = content.replaceAll("INSERT_ANALYSIS_EDOP_INSERT", scId.getAnalysis_desired());
        content = content.replaceAll("INSERT_ANALYSIS_NDOP_INSERT", scId.getAnalysis_nondesired());
        content = content.replaceAll("INSERT_ANALYSIS_NDOP_ACT_INSERT", scId.getAnalysis_action());
        content = content.replaceAll("INSERT_PLANNEDRESOURCE_ONETIME_INSERT", scId.getPrn_onetime());
        content = content.replaceAll("INSERT_PLANNEDRESOURCE_ONGOING_ACT_INSERT", scId.getPrn_ongoing());

        content = content.replaceAll("INSERT_APPROVAL_APPROVAL_INSERT", StandardCode.getInstance().noNull(scId.getApproval()));
        try {
            content = content.replaceAll("INSERT_APPROVAL_APPROVALDATE_INSERT", DateFormat.getDateInstance(DateFormat.SHORT).format(scId.getApproval_date()));
        } catch (Exception e) {
            content = content.replaceAll("INSERT_APPROVAL_APPROVALDATE_INSERT", "");
        }
        content = content.replaceAll("INSERT_APPROVAL_APPROVEDBY_INSERT", scId.getApprovalby());
        content = content.replaceAll("INSERT_APPROVAL_NOTES_INSERT", scId.getApproval_note());

        List<StrategicChange_Planner> scplLst = QMSService.getInstance().getStrategicPlanners(scId.getSid());
        StringBuffer action = new StringBuffer();

        String strategicplanning = "\\\\pard \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sl276\\\\slmult1\\\\widctlpar\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0\\\\itap0\\\\pararsid10429859 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid10429859 \n"
                + "\\\\par \\\\ltrrow}\\\\trowd \\\\irow0\\\\irowband0\\\\ltrrow\\\\ts22\\\\trgaph108\\\\trleft-108\\\\trftsWidth1\\\\trftsWidthB3\\\\trftsWidthA3\\\\trautofit1\\\\trpaddl108\\\\trpaddr108\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid15687257\\\\tbllkhdrrows\\\\tbllkhdrcols\\\\tbllknocolband\\\\tblind0\\\\tblindtype3 \n"
                + "\\\\clvertalt\\\\clbrdrt\\\\brdrtbl \\\\clbrdrl\\\\brdrtbl \\\\clbrdrb\\\\brdrtbl \\\\clbrdrr\\\\brdrtbl \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2628\\\\clshdrawnil \\\\cellx2520\\\\clvertalt\\\\clbrdrt\\\\brdrtbl \\\\clbrdrl\\\\brdrtbl \\\\clbrdrb\\\\brdrtbl \\\\clbrdrr\\\\brdrtbl \n"
                + "\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth6948\\\\clshdrawnil \\\\cellx9468\\\\pard\\\\plain \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sl276\\\\slmult1\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0\\\\pararsid10429859\\\\yts22 \\\\rtlch\\\\fcs1 \\\\af0\\\\afs22\\\\alang16393 \\\\ltrch\\\\fcs0 \n"
                + "\\\\f35\\\\fs22\\\\lang16393\\\\langfe16393\\\\cgrid\\\\langnp16393\\\\langfenp16393 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid210401 Action\\\\cell }\\\\pard \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sl276\\\\slmult1\n"
                + "\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0\\\\pararsid15687257\\\\yts22 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid15687257\\\\charrsid15687257 INSERT_PLANNING_ACTION_INSERT}{\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid210401 \\\\cell \n"
                + "}\\\\pard\\\\plain \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 \\\\rtlch\\\\fcs1 \\\\af0\\\\afs22\\\\alang1033 \\\\ltrch\\\\fcs0 \\\\f35\\\\fs22\\\\lang1033\\\\langfe1033\\\\cgrid\\\\langnp1033\\\\langfenp1033 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \n"
                + "\\\\insrsid210401 \\\\trowd \\\\irow0\\\\irowband0\\\\ltrrow\\\\ts22\\\\trgaph108\\\\trleft-108\\\\trftsWidth1\\\\trftsWidthB3\\\\trftsWidthA3\\\\trautofit1\\\\trpaddl108\\\\trpaddr108\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid15687257\\\\tbllkhdrrows\\\\tbllkhdrcols\\\\tbllknocolband\\\\tblind0\n"
                + "\\\\tblindtype3 \\\\clvertalt\\\\clbrdrt\\\\brdrtbl \\\\clbrdrl\\\\brdrtbl \\\\clbrdrb\\\\brdrtbl \\\\clbrdrr\\\\brdrtbl \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2628\\\\clshdrawnil \\\\cellx2520\\\\clvertalt\\\\clbrdrt\\\\brdrtbl \\\\clbrdrl\\\\brdrtbl \\\\clbrdrb\\\\brdrtbl \\\\clbrdrr\\\\brdrtbl \n"
                + "\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth6948\\\\clshdrawnil \\\\cellx9468\\\\row \\\\ltrrow}\\\\pard\\\\plain \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sl276\\\\slmult1\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0\\\\pararsid10429859\\\\yts22 \\\\rtlch\\\\fcs1 \\\\af0\\\\afs22\\\\alang16393 \n"
                + "\\\\ltrch\\\\fcs0 \\\\f35\\\\fs22\\\\lang16393\\\\langfe16393\\\\cgrid\\\\langnp16393\\\\langfenp16393 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid210401 Responsible\\\\cell }\\\\pard \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sl276\\\\slmult1\n"
                + "\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0\\\\pararsid15687257\\\\yts22 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid15687257\\\\charrsid15687257 INSERT_PLANNING_RESP_INSERT}{\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid210401 \\\\cell \n"
                + "}\\\\pard\\\\plain \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 \\\\rtlch\\\\fcs1 \\\\af0\\\\afs22\\\\alang1033 \\\\ltrch\\\\fcs0 \\\\f35\\\\fs22\\\\lang1033\\\\langfe1033\\\\cgrid\\\\langnp1033\\\\langfenp1033 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \n"
                + "\\\\insrsid210401 \\\\trowd \\\\irow1\\\\irowband1\\\\ltrrow\\\\ts22\\\\trgaph108\\\\trleft-108\\\\trftsWidth1\\\\trftsWidthB3\\\\trftsWidthA3\\\\trautofit1\\\\trpaddl108\\\\trpaddr108\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid15687257\\\\tbllkhdrrows\\\\tbllkhdrcols\\\\tbllknocolband\\\\tblind0\n"
                + "\\\\tblindtype3 \\\\clvertalt\\\\clbrdrt\\\\brdrtbl \\\\clbrdrl\\\\brdrtbl \\\\clbrdrb\\\\brdrtbl \\\\clbrdrr\\\\brdrtbl \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2628\\\\clshdrawnil \\\\cellx2520\\\\clvertalt\\\\clbrdrt\\\\brdrtbl \\\\clbrdrl\\\\brdrtbl \\\\clbrdrb\\\\brdrtbl \\\\clbrdrr\\\\brdrtbl \n"
                + "\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth6948\\\\clshdrawnil \\\\cellx9468\\\\row \\\\ltrrow}\\\\pard\\\\plain \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sl276\\\\slmult1\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0\\\\pararsid10429859\\\\yts22 \\\\rtlch\\\\fcs1 \\\\af0\\\\afs22\\\\alang16393 \n"
                + "\\\\ltrch\\\\fcs0 \\\\f35\\\\fs22\\\\lang16393\\\\langfe16393\\\\cgrid\\\\langnp16393\\\\langfenp16393 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid210401 Due Date\\\\cell }\\\\pard \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sl276\\\\slmult1\n"
                + "\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0\\\\pararsid15687257\\\\yts22 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid15687257\\\\charrsid15687257 INSERT_PLANNING_DUEDATE_INSERT}{\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid210401 \\\\cell \n"
                + "}\\\\pard\\\\plain \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 \\\\rtlch\\\\fcs1 \\\\af0\\\\afs22\\\\alang1033 \\\\ltrch\\\\fcs0 \\\\f35\\\\fs22\\\\lang1033\\\\langfe1033\\\\cgrid\\\\langnp1033\\\\langfenp1033 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \n"
                + "\\\\insrsid210401 \\\\trowd \\\\irow2\\\\irowband2\\\\ltrrow\\\\ts22\\\\trgaph108\\\\trleft-108\\\\trftsWidth1\\\\trftsWidthB3\\\\trftsWidthA3\\\\trautofit1\\\\trpaddl108\\\\trpaddr108\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid15687257\\\\tbllkhdrrows\\\\tbllkhdrcols\\\\tbllknocolband\\\\tblind0\n"
                + "\\\\tblindtype3 \\\\clvertalt\\\\clbrdrt\\\\brdrtbl \\\\clbrdrl\\\\brdrtbl \\\\clbrdrb\\\\brdrtbl \\\\clbrdrr\\\\brdrtbl \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2628\\\\clshdrawnil \\\\cellx2520\\\\clvertalt\\\\clbrdrt\\\\brdrtbl \\\\clbrdrl\\\\brdrtbl \\\\clbrdrb\\\\brdrtbl \\\\clbrdrr\\\\brdrtbl \n"
                + "\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth6948\\\\clshdrawnil \\\\cellx9468\\\\row \\\\ltrrow}\\\\pard\\\\plain \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sl276\\\\slmult1\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0\\\\pararsid10429859\\\\yts22 \\\\rtlch\\\\fcs1 \\\\af0\\\\afs22\\\\alang16393 \n"
                + "\\\\ltrch\\\\fcs0 \\\\f35\\\\fs22\\\\lang16393\\\\langfe16393\\\\cgrid\\\\langnp16393\\\\langfenp16393 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid210401 Effectiveness\\\\cell }\\\\pard \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sl276\\\\slmult1\n"
                + "\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0\\\\pararsid15687257\\\\yts22 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid15687257\\\\charrsid15687257 INSERT_PLANNING_EFFECTIVENESS_INSERT}{\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid210401 \\\\cell \n"
                + "}\\\\pard\\\\plain \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 \\\\rtlch\\\\fcs1 \\\\af0\\\\afs22\\\\alang1033 \\\\ltrch\\\\fcs0 \\\\f35\\\\fs22\\\\lang1033\\\\langfe1033\\\\cgrid\\\\langnp1033\\\\langfenp1033 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \n"
                + "\\\\insrsid210401 \\\\trowd \\\\irow3\\\\irowband3\\\\ltrrow\\\\ts22\\\\trgaph108\\\\trleft-108\\\\trftsWidth1\\\\trftsWidthB3\\\\trftsWidthA3\\\\trautofit1\\\\trpaddl108\\\\trpaddr108\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid15687257\\\\tbllkhdrrows\\\\tbllkhdrcols\\\\tbllknocolband\\\\tblind0\n"
                + "\\\\tblindtype3 \\\\clvertalt\\\\clbrdrt\\\\brdrtbl \\\\clbrdrl\\\\brdrtbl \\\\clbrdrb\\\\brdrtbl \\\\clbrdrr\\\\brdrtbl \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2628\\\\clshdrawnil \\\\cellx2520\\\\clvertalt\\\\clbrdrt\\\\brdrtbl \\\\clbrdrl\\\\brdrtbl \\\\clbrdrb\\\\brdrtbl \\\\clbrdrr\\\\brdrtbl \n"
                + "\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth6948\\\\clshdrawnil \\\\cellx9468\\\\row \\\\ltrrow}\\\\pard\\\\plain \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sl276\\\\slmult1\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0\\\\pararsid10429859\\\\yts22 \\\\rtlch\\\\fcs1 \\\\af0\\\\afs22\\\\alang16393 \n"
                + "\\\\ltrch\\\\fcs0 \\\\f35\\\\fs22\\\\lang16393\\\\langfe16393\\\\cgrid\\\\langnp16393\\\\langfenp16393 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid210401 Date of Implementation\\\\cell }\\\\pard \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sl276\\\\slmult1\n"
                + "\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0\\\\pararsid15687257\\\\yts22 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid15687257\\\\charrsid15687257 INSERT_PLANNING_IMPLEMENTATIONDATE_INSERT}{\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid210401 \\\\cell \n"
                + "}\\\\pard\\\\plain \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 \\\\rtlch\\\\fcs1 \\\\af0\\\\afs22\\\\alang1033 \\\\ltrch\\\\fcs0 \\\\f35\\\\fs22\\\\lang1033\\\\langfe1033\\\\cgrid\\\\langnp1033\\\\langfenp1033 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \n"
                + "\\\\insrsid210401 \\\\trowd \\\\irow4\\\\irowband4\\\\ltrrow\\\\ts22\\\\trgaph108\\\\trleft-108\\\\trftsWidth1\\\\trftsWidthB3\\\\trftsWidthA3\\\\trautofit1\\\\trpaddl108\\\\trpaddr108\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid15687257\\\\tbllkhdrrows\\\\tbllkhdrcols\\\\tbllknocolband\\\\tblind0\n"
                + "\\\\tblindtype3 \\\\clvertalt\\\\clbrdrt\\\\brdrtbl \\\\clbrdrl\\\\brdrtbl \\\\clbrdrb\\\\brdrtbl \\\\clbrdrr\\\\brdrtbl \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2628\\\\clshdrawnil \\\\cellx2520\\\\clvertalt\\\\clbrdrt\\\\brdrtbl \\\\clbrdrl\\\\brdrtbl \\\\clbrdrb\\\\brdrtbl \\\\clbrdrr\\\\brdrtbl \n"
                + "\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth6948\\\\clshdrawnil \\\\cellx9468\\\\row \\\\ltrrow}\\\\pard\\\\plain \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sl276\\\\slmult1\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0\\\\pararsid10429859\\\\yts22 \\\\rtlch\\\\fcs1 \\\\af0\\\\afs22\\\\alang16393 \n"
                + "\\\\ltrch\\\\fcs0 \\\\f35\\\\fs22\\\\lang16393\\\\langfe16393\\\\cgrid\\\\langnp16393\\\\langfenp16393 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid210401 Final Approval\\\\cell }\\\\pard \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sl276\\\\slmult1\n"
                + "\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0\\\\pararsid15687257\\\\yts22 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid15687257\\\\charrsid15687257 INSERT_PLANNING_APPROVAL_INSERT}{\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid210401 \\\\cell \n"
                + "}\\\\pard\\\\plain \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 \\\\rtlch\\\\fcs1 \\\\af0\\\\afs22\\\\alang1033 \\\\ltrch\\\\fcs0 \\\\f35\\\\fs22\\\\lang1033\\\\langfe1033\\\\cgrid\\\\langnp1033\\\\langfenp1033 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \n"
                + "\\\\insrsid210401 \\\\trowd \\\\irow5\\\\irowband5\\\\ltrrow\\\\ts22\\\\trgaph108\\\\trleft-108\\\\trftsWidth1\\\\trftsWidthB3\\\\trftsWidthA3\\\\trautofit1\\\\trpaddl108\\\\trpaddr108\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid15687257\\\\tbllkhdrrows\\\\tbllkhdrcols\\\\tbllknocolband\\\\tblind0\n"
                + "\\\\tblindtype3 \\\\clvertalt\\\\clbrdrt\\\\brdrtbl \\\\clbrdrl\\\\brdrtbl \\\\clbrdrb\\\\brdrtbl \\\\clbrdrr\\\\brdrtbl \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2628\\\\clshdrawnil \\\\cellx2520\\\\clvertalt\\\\clbrdrt\\\\brdrtbl \\\\clbrdrl\\\\brdrtbl \\\\clbrdrb\\\\brdrtbl \\\\clbrdrr\\\\brdrtbl \n"
                + "\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth6948\\\\clshdrawnil \\\\cellx9468\\\\row \\\\ltrrow}\\\\pard\\\\plain \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sl276\\\\slmult1\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0\\\\pararsid10429859\\\\yts22 \\\\rtlch\\\\fcs1 \\\\af0\\\\afs22\\\\alang16393 \n"
                + "\\\\ltrch\\\\fcs0 \\\\f35\\\\fs22\\\\lang16393\\\\langfe16393\\\\cgrid\\\\langnp16393\\\\langfenp16393 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid210401 Approver\\\\cell }\\\\pard \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sl276\\\\slmult1\n"
                + "\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0\\\\pararsid15687257\\\\yts22 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid15687257\\\\charrsid15687257 INSERT_PLANNING_APPROVER_INSERT}{\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid210401 \\\\cell \n"
                + "}\\\\pard\\\\plain \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 \\\\rtlch\\\\fcs1 \\\\af0\\\\afs22\\\\alang1033 \\\\ltrch\\\\fcs0 \\\\f35\\\\fs22\\\\lang1033\\\\langfe1033\\\\cgrid\\\\langnp1033\\\\langfenp1033 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \n"
                + "\\\\insrsid210401 \\\\trowd \\\\irow6\\\\irowband6\\\\lastrow \\\\ltrrow\n"
                + "\\\\ts22\\\\trgaph108\\\\trleft-108\\\\trftsWidth1\\\\trftsWidthB3\\\\trftsWidthA3\\\\trautofit1\\\\trpaddl108\\\\trpaddr108\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid15687257\\\\tbllkhdrrows\\\\tbllkhdrcols\\\\tbllknocolband\\\\tblind0\\\\tblindtype3 \\\\clvertalt\\\\clbrdrt\\\\brdrtbl \\\\clbrdrl\n"
                + "\\\\brdrtbl \\\\clbrdrb\\\\brdrtbl \\\\clbrdrr\\\\brdrtbl \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2628\\\\clshdrawnil \\\\cellx2520\\\\clvertalt\\\\clbrdrt\\\\brdrtbl \\\\clbrdrl\\\\brdrtbl \\\\clbrdrb\\\\brdrtbl \\\\clbrdrr\\\\brdrtbl \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth6948\\\\clshdrawnil \\\\cellx9468\\\\row }\\\\pard \\\\ltrpar\n"
                + "\\\\ql \\\\li0\\\\ri0\\\\sl276\\\\slmult1\\\\widctlpar\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0\\\\itap0\\\\pararsid10429859 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid10429859 \n"
                + "\\\\par }INSERT_STRATEGIC_PLANNING_INSERT";
        boolean isFirstScp = true;
//        String planning = strategicplanning;
        for (StrategicChange_Planner scp : scplLst) {

            content = content.replaceAll("INSERT_STRATEGIC_PLANNING_INSERT", strategicplanning);

            isFirstScp = false;
            
            if (StandardCode.getInstance().noNull(scp.getResponsible()) > 0) {
                User resp = UserService.getInstance().getSingleUser(scp.getResponsible());
                content = content.replaceAll("INSERT_PLANNING_RESP_INSERT", resp.getFirstName() + " " + resp.getLastName());
            } else {
                content = content.replaceAll("INSERT_PLANNING_RESP_INSERT", "House");
            }
            if (StandardCode.getInstance().noNull(scp.getApprover()) > 0) {
                User approver = UserService.getInstance().getSingleUser(scp.getApprover());
                content = content.replaceAll("INSERT_PLANNING_APPROVER_INSERT", approver.getFirstName() + " " + approver.getLastName());
            } else {
                content = content.replaceAll("INSERT_PLANNING_APPROVER_INSERT", "House");
            }

            content = content.replaceAll("INSERT_PLANNING_ACTION_INSERT", scp.getAction());

            try {
                content = content.replaceAll("INSERT_PLANNING_DUEDATE_INSERT", DateFormat.getDateInstance(DateFormat.SHORT).format(scp.getDuedate()));
            } catch (Exception e) {
                content = content.replaceAll("INSERT_PLANNING_DUEDATE_INSERT", "");
            }
            try {
                content = content.replaceAll("INSERT_PLANNING_IMPLEMENTATIONDATE_INSERT", DateFormat.getDateInstance(DateFormat.SHORT).format(scp.getDoi()));
            } catch (Exception e) {
                content = content.replaceAll("INSERT_PLANNING_IMPLEMENTATIONDATE_INSERT", "");
            }
            content = content.replaceAll("INSERT_PLANNING_EFFECTIVENESS_INSERT", scp.getEffectiveness());
            if (scp.isApproval()) {
                content = content.replaceAll("INSERT_PLANNING_APPROVAL_INSERT", "Yes");
            } else {
                content = content.replaceAll("INSERT_PLANNING_APPROVAL_INSERT", "No");
            }
        }
         content = content.replaceAll("INSERT_STRATEGIC_PLANNING_INSERT", "");

//        content = content.replaceAll("INSERT_STRATEGIC_PLANNING_INSERT", planning);
   content = content.replaceAll("INSERT_SUPERVISOR_PERSON_INSERT", scId.getClose_supervisor());
        content = content.replaceAll("INSERT_OWNER_PERSON_INSERT", scId.getClose_owner());
        content = content.replaceAll("INSERT_CLOSE_COMMENT_INSERT", scId.getClose_comment());

        String filename = scId.getNumber() + ".doc";

        content = content.replaceAll("NC-CAPA Internal.rtf", StandardCode.getInstance().noNull(filename));
        //System.out.println("hereeeeee6");
        //write to client (web browser)
        response.setHeader("Content-Type", "Application/msword");
        response.setHeader("Content-disposition", "attachment; filename=" + filename);
        //response.setHeader("Content-disposition", "attachment; filename=" + q.getNumber() + ".doc");
        OutputStream os = response.getOutputStream();
        os.write(content.getBytes());
        os.flush();
        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }

    // Returns the contents of the file in a byte array.
    private static byte[] getBytesFromFile(java.io.File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }
}
