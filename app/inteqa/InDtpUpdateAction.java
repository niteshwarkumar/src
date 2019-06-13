/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.inteqa;

import app.project.*;
import app.quote.Client_Quote;
import app.quote.Quote1;
import app.quote.QuoteService;
import app.quote.Technical;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import java.util.*;
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.DynaValidatorForm;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Niteshwar
 */
public class InDtpUpdateAction extends Action {

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
        //END check for login (security)

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
        if (projectId == null) {
            List results = ProjectService.getInstance().getProjectList();

            ListIterator iterScroll = null;
            for (iterScroll = results.listIterator(); iterScroll.hasNext(); iterScroll.next()) {
            }
            iterScroll.previous();
            Project p = (Project) iterScroll.next();
            projectId = String.valueOf(p.getProjectId());
        }

        Project p = ProjectService.getInstance().getSingleProject(Integer.parseInt(projectId));
        String number = p.getNumber() + p.getCompany().getCompany_code();
        Integer porqid = p.getProjectId();
        String qualityFeedback = request.getParameter("qualityFeedback");
        String porq = request.getParameter("porq");
        String sOs = "";
        String dOs = "";
        String sApp = "";
        String dApp = "";
        String sVersion = "";
        String tVersion = "";

        String jsonTechnical = request.getParameter("technicalJSON");
        
        
         String oper = request.getParameter("oper");
         

        if (porq.equalsIgnoreCase("p")) {

            try {
                if(null!=oper){
                    if(oper.equalsIgnoreCase("del")) {
                        int reqid = Integer.parseInt(request.getParameter("id"));
                        ProjectService.unlinkProjectTechnical(reqid);
                    }else{
                        Project_Technical tech = new Project_Technical();
                        if (oper.equalsIgnoreCase("edit")) {
                             int reqid = Integer.parseInt(request.getParameter("id"));
                             tech = ProjectService.getInstance().getSingleTechnical(reqid);
                        } 
                            tech.setSourceapp((request.getParameter("sourceApplication")));
                            tech.setSourceos((request.getParameter("sourceOs")));
                            tech.setSourcever((request.getParameter("sourceVersion")));
                            tech.setTargetos((request.getParameter("targetOs")));
                            tech.setTargetapp((request.getParameter("targetApplication")));
                            tech.setTargetver(request.getParameter("targetVersion"));
                            if(!"".equalsIgnoreCase(request.getParameter("unitCount")))
                                tech.setUnitCount(Double.parseDouble(request.getParameter("unitCount")));
                            tech.setProjectid(p.getProjectId());
                            ProjectService.getInstance().addUpdateProjectTechnical(tech);
                    
                    }
                }

            } catch (Exception e) {
            }

        } else {
             Quote1 q = QuoteService.getInstance().getSingleQuoteFromProject(p.getProjectId());
            if(null!=oper){
                    if(oper.equalsIgnoreCase("del")) {
                        int reqid = Integer.parseInt(request.getParameter("id"));
                        QuoteService.unlinkTechnical(reqid);
                    }else{
                        Technical tech = new Technical();
                        if (oper.equalsIgnoreCase("edit")) {
                             int reqid = Integer.parseInt(request.getParameter("id"));
                             tech = QuoteService.getInstance().getSingleTechnical(reqid);
                        } 
                            List clq = QuoteService.getInstance().getClient_Quote(q.getQuote1Id());
                            Client_Quote cq = (Client_Quote) clq.get(0);    
                            tech.setClientQuote_ID(cq.getId());
                            tech.setSourceApplication((request.getParameter("sourceApplication")));
                            tech.setSourceOs((request.getParameter("sourceOs")));
                            tech.setSourceVersion((request.getParameter("sourceVersion")));
                            tech.setTargetOs((request.getParameter("targetOs")));
                            tech.setTargetApplication((request.getParameter("targetApplication")));
                            tech.setTargetVersion(request.getParameter("targetVersion"));
                            if(!"".equalsIgnoreCase(request.getParameter("unitCount")))
                                tech.setUnitCount(Double.parseDouble(request.getParameter("unitCount")));
                             QuoteService.getInstance().saveTechnical(tech);
                    
                    }
                }

        }
        request.setAttribute("porq", porq);
        Integer id = Integer.valueOf(projectId);

        DynaValidatorForm upd = (DynaValidatorForm) form;

        String prepTime = (String) upd.get("prepTime");
        String prepLang = (String) upd.get("prepLang");
        String prepTotal = (String) upd.get("prepTotal");
        String deliveryTime = (String) upd.get("deliveryTime");
        String deliveryLang = (String) upd.get("deliveryLang");
        String deliveryTotal = (String) upd.get("deliveryTotal");
        String srcPf1 = (String) upd.get("srcPf1");
        String srcPf2 = (String) upd.get("srcPf2");
        String srcPf3 = (String) upd.get("srcPf3");
        String srcPf4 = (String) upd.get("srcPf4");
        String srcPf5 = (String) upd.get("srcPf5");
        String srcPf6 = (String) upd.get("srcPf6");
        String dtpToolName = (String) upd.get("dtpToolName");
        String dtpTool1 = (String) upd.get("dtpTool1");
        String dtpToolOth = (String) upd.get("dtpToolOth");
        String dtpToolOthText = (String) upd.get("dtpToolOthText");
        String srcFont1 = (String) upd.get("srcFont1");
        String srcFont2 = (String) upd.get("srcFont2");
        String tgtFont1 = (String) upd.get("tgtFont1");
        String tgtFont2 = (String) upd.get("tgtFont2");

        String TgtFontText = (String) upd.get("TgtFontText");

        String verified = (String) upd.get("verified");
        String verifiedBy = (String) upd.get("verifiedBy");
        String verifiedDate = (String) upd.get("verifiedDate");
        String verifiedText = (String) upd.get("verifiedText");

        String textBox1 = (String) upd.get("textBox1");
        String textBox2 = (String) upd.get("textBox2");
        String textBox3 = (String) upd.get("textBox3");
        String textBox4 = (String) upd.get("textBox4");

        String check1 = (String) upd.get("check1");
        String check2 = (String) upd.get("check2");
        String check3 = (String) upd.get("check3");
        String check21 = (String) upd.get("check21");
        String check22 = (String) upd.get("check22");
        String check4 = (String) upd.get("check4");
        String check5 = (String) upd.get("check5");
        String check6 = (String) upd.get("check6");
        String check7 = (String) upd.get("check7");
        String check6text = (String) upd.get("check6text");
        String check7text = (String) upd.get("check7text");
        String checkText1 = (String) upd.get("checkText1");
        String checkText2 = (String) upd.get("checkText2");
        String checkText3 = (String) upd.get("checkText3");
        String unit0 = (String) upd.get("unit0");
        String unit1 = (String) upd.get("unit1");
        String unit2 = (String) upd.get("unit2");
        String unit3 = (String) upd.get("unit3");
        String unit4 = (String) upd.get("unit4");
        String unit5 = (String) upd.get("unit5");
        String unit6 = (String) upd.get("unit6");
        String hours0 = (String) upd.get("hours0");
        String hours1 = (String) upd.get("hours1");
        String hours2 = (String) upd.get("hours2");
        String hours3 = (String) upd.get("hours3");
        String hours4 = (String) upd.get("hours4");
        String hours5 = (String) upd.get("hours5");
        String hours6 = (String) upd.get("hours6");
        String dtpnotes = request.getParameter("note");

        //INDtp iDtp=InteqaService.getInstance().getINDtp(1000);
        INDtp iDtp = InteqaService.getInstance().getINDtp(id);
        if (iDtp == null) {
            iDtp = new INDtp();
            iDtp.setProjectId(id);
        }

        if (porq.equalsIgnoreCase("p")) {
            iDtp.setPorq("p");
        } else {
            iDtp.setPorq("q");
        }

        iDtp.setDeliveryLang(deliveryLang);
        try {
            iDtp.setDeliveryTime(Float.parseFloat(deliveryTime));
        } catch (Exception e) {
        }

        try {
            iDtp.setDeliveryTotal(Float.parseFloat(deliveryTotal));
        } catch (Exception e) {
        }
        iDtp.setDtpToolName(dtpToolName);

        iDtp.setDtpToolOthText(dtpToolOthText);
        iDtp.setPrepLang(prepLang);
        try {
            iDtp.setPrepTime(Float.parseFloat(prepTime));
        } catch (Exception e) {
        }
        try {
            iDtp.setPrepTotal(Float.parseFloat(prepTotal));
        } catch (Exception e) {
        }

        iDtp.setTgtFontText(TgtFontText);

        iDtp.setVerifiedBy(verifiedBy);
        try {
            if (verifiedDate.length() > 0) { //if present
                iDtp.setVerifiedDate(DateService.getInstance().convertDate(verifiedDate).getTime());
            }else{
                iDtp.setVerifiedDate(null);
            }
        } catch (Exception e) {
            //System.out.println("Date Errooooorr " + e.getMessage());
        }
        iDtp.setVerifiedText(verifiedText);


        if (dtpTool1.equalsIgnoreCase("on")) {
            iDtp.setDtpTool1(true);
        } else {
            iDtp.setDtpTool1(false);
        }
        if (dtpToolOth.equalsIgnoreCase("on")) {
            iDtp.setDtpToolOth(true);
        } else {
            iDtp.setDtpToolOth(false);
        }
        if (srcFont1.equalsIgnoreCase("on")) {
            iDtp.setSrcFont1(true);
        } else {
            iDtp.setSrcFont1(false);
        }
        if (srcFont2.equalsIgnoreCase("on")) {
            iDtp.setSrcFont2(true);
        } else {
            iDtp.setSrcFont2(false);
        }
        if (srcPf1.equalsIgnoreCase("on")) {
            iDtp.setSrcPf1(true);
        } else {
            iDtp.setSrcPf1(false);
        }
        if (srcPf2.equalsIgnoreCase("on")) {
            iDtp.setSrcPf2(true);
        } else {
            iDtp.setSrcPf2(false);
        }
        if (srcPf3.equalsIgnoreCase("on")) {
            iDtp.setSrcPf3(true);
        } else {
            iDtp.setSrcPf3(false);
        }
        if (srcPf4.equalsIgnoreCase("on")) {
            iDtp.setSrcPf4(true);
        } else {
            iDtp.setSrcPf4(false);
        }
        if (srcPf5.equalsIgnoreCase("on")) {
            iDtp.setSrcPf5(true);
        } else {
            iDtp.setSrcPf5(false);
        }
        if (srcPf6.equalsIgnoreCase("on")) {
            iDtp.setSrcPf6(true);
        } else {
            iDtp.setSrcPf6(false);
        }
        if (tgtFont1.equalsIgnoreCase("on")) {
            iDtp.setTgtFont1(true);
        } else {
            iDtp.setTgtFont1(false);
        }
        if (tgtFont2.equalsIgnoreCase("on")) {
            iDtp.setTgtFont2(true);
        } else {
            iDtp.setTgtFont2(false);
        }
        if (verified.equalsIgnoreCase("on")) {
            iDtp.setVerified(true);
        } else {
            iDtp.setVerified(false);
        }

        if (check1.equalsIgnoreCase("on")) {
            iDtp.setCheck1(true);
        } else {
            iDtp.setCheck1(false);
        }
        if (check2.equalsIgnoreCase("on")) {
            iDtp.setCheck2(true);
        } else {
            iDtp.setCheck2(false);
        }
        if (check21.equalsIgnoreCase("on")) {
            iDtp.setCheck21(true);
        } else {
            iDtp.setCheck21(false);
        }
        if (check22.equalsIgnoreCase("on")) {
            iDtp.setCheck22(true);
        } else {
            iDtp.setCheck22(false);
        }
        if (check3.equalsIgnoreCase("on")) {
            iDtp.setCheck3(true);
        } else {
            iDtp.setCheck3(false);
        }
        if (check4.equalsIgnoreCase("on")) {
            iDtp.setCheck4(true);
        } else {
            iDtp.setCheck4(false);
        }
        if (check5.equalsIgnoreCase("on")) {
            iDtp.setCheck5(true);
        } else {
            iDtp.setCheck5(false);
        }
        if (check6.equalsIgnoreCase("on")) {
            iDtp.setCheck6(true);
        } else {
            iDtp.setCheck6(false);
        }
        if (check7.equalsIgnoreCase("on")) {
            iDtp.setCheck7(true);
        } else {
            iDtp.setCheck7(false);
        }


        iDtp.setTextBox1(textBox1);
        iDtp.setTextBox2(textBox2);
        iDtp.setTextBox3(textBox3);
        iDtp.setTextBox4(textBox4);
        iDtp.setCheckText1(checkText1);
        iDtp.setCheckText2(checkText2);
        iDtp.setCheckText3(checkText3);
        iDtp.setCheck7text(check7text);
        iDtp.setCheck6text(check6text);
        iDtp.setUnit0(unit0);
        iDtp.setUnit1(unit1);
        iDtp.setUnit2(unit2);
        iDtp.setUnit3(unit3);
        iDtp.setUnit4(unit4);
        iDtp.setUnit5(unit5);
        iDtp.setUnit6(unit6);

        try {
            iDtp.setHours0(Double.parseDouble(hours0));
        } catch (Exception e) {
        }
        try {
            iDtp.setHours1(Double.parseDouble(hours1));
        } catch (Exception e) {
        }
        try {
            iDtp.setHours2(Double.parseDouble(hours2));
        } catch (Exception e) {
        }
        try {
            iDtp.setHours3(Double.parseDouble(hours3));
        } catch (Exception e) {
        }
        try {
            iDtp.setHours4(Double.parseDouble(hours4));
        } catch (Exception e) {
        }
        try {
            iDtp.setHours5(Double.parseDouble(hours5));
        } catch (Exception e) {
        }
        try {
            iDtp.setHours6(Double.parseDouble(hours6));
        } catch (Exception e) {
        }

        iDtp.setDtpnotes(dtpnotes);
        request.setAttribute("project", p);

        InteqaService.getInstance().updateInDtp(iDtp);


        iDtp = InteqaService.getInstance().getINDtp(id);
        String comScore = (String) upd.get("comScore");
        String comExplain = (String) upd.get("comExplain");
        String comEnteredBy = (String) upd.get("comEnteredBy");

        String timScore = (String) upd.get("timScore");
        String timExplain = (String) upd.get("timExplain");
        String timEnteredBy = (String) upd.get("timEnteredBy");

        String quaScore = (String) upd.get("quaScore");
        String quaExplain = (String) upd.get("quaExplain");
        String quaEnteredBy = (String) upd.get("quaEnteredBy");

        String othScore = (String) upd.get("othScore");
        String othExplain = (String) upd.get("othExplain");
        String othEnteredBy = (String) upd.get("othEnteredBy");
        INQualityFeedback iqf = InteqaService.getInstance().getINQualityFeedback(iDtp.getId(), porq, "d");
        if (iqf == null) {
            iqf = new INQualityFeedback();
            iqf.setInteqaid(iDtp.getId());
            iqf.setPorq(porq);
            iqf.setTab("d");
            iqf.setPorqid(porqid);
            iqf.setNumber(number);
        }

        iqf.setComScore(comScore);
        iqf.setComExplain(comExplain);
        iqf.setComEnteredBy(comEnteredBy);

        iqf.setTimScore(timScore);
        iqf.setTimEnteredBy(timEnteredBy);
        iqf.setTimExplain(timExplain);

        iqf.setQuaEnteredBy(quaEnteredBy);
        iqf.setQuaExplain(quaExplain);
        iqf.setQuaScore(quaScore);

        iqf.setOthEnteredBy(othEnteredBy);
        iqf.setOthExplain(othExplain);
        iqf.setOthScore(othScore);
        if (null!=qualityFeedback) {
            if (qualityFeedback.equalsIgnoreCase("y")) {
                InteqaService.getInstance().updateInQualityFeedback(iqf);
        }}

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
