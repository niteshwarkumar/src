/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.inteqa;

import app.project.*;
import app.quote.Quote1;
import app.quote.QuoteService;
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
import org.apache.struts.validator.*;

/**
 *
 * @author Niteshwar
 */
public class InEngineeringUpdateAction extends Action {

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
        Quote1 q = null;
        String quoteViewId = request.getParameter("quoteViewId");
        
        Integer id = Integer.valueOf(projectId);

        //END get id of current project from either request, attribute, or cookie

        //get project to edit
        Project p = ProjectService.getInstance().getSingleProject(id);

        INEngineering iNEngineering = InteqaService.getInstance().getINEngineering(id);
        if (iNEngineering == null) {
            iNEngineering = new INEngineering();
            iNEngineering.setProjectId(id);
        }
String porq="p"; 
String number=p.getNumber()+p.getCompany().getCompany_code();
Integer porqid=p.getProjectId();
        try {
            if (quoteViewId != null && !quoteViewId.equalsIgnoreCase("")&& !quoteViewId.equalsIgnoreCase("0")) {
            q = QuoteService.getInstance().getSingleQuote(Integer.parseInt(quoteViewId));
            request.setAttribute("quote", q);
            //iNEngineering.setPorq("q");
            porq="q";
            number=q.getNumber();
            porqid=q.getQuote1Id();

        }
        } catch (Exception e) {
        }
        

        //INEngineering iNEngineering=InteqaService.getInstance().getINEngineering(1000);

        //place this project into request for further display in jsp page
        DynaValidatorForm upd = (DynaValidatorForm) form;

        String prepTime = (String) upd.get("prepTime");
        String prepLang = (String) upd.get("prepLang");
        String prepTotal = (String) upd.get("prepTotal");
        String deliveryTime = (String) upd.get("deliveryTime");
        String deliveryLang = (String) upd.get("deliveryLang");
        String deliveryTotal = (String) upd.get("deliveryTotal");

        String filePrep01 = (String) upd.get("filePrep01");
        String filePrep02 = (String) upd.get("filePrep02");
        String filePrep1 = (String) upd.get("filePrep1");
        String filePrep2 = (String) upd.get("filePrep2");
        String filePrep3 = (String) upd.get("filePrep3");
        String filePrep4 = (String) upd.get("filePrep4");
        String filePrep5 = (String) upd.get("filePrep5");
        String filePrep6 = (String) upd.get("filePrep6");

        String extract1 = (String) upd.get("extract1");
        String extract2 = (String) upd.get("extract2");
        String extractText = (String) upd.get("extractText");
        String verified = (String) upd.get("verified");
        String verifiedBy = (String) upd.get("verifiedBy");
        String verifiedDate = (String) upd.get("verifiedDate");
        String verifiedText = (String) upd.get("verifiedText");
        String textBox1 = (String) upd.get("textBox1");

        String check1 = (String) upd.get("check1");
        String check2 = (String) upd.get("check2");
        String check21 = (String) upd.get("check21");
        String check22 = (String) upd.get("check22");
        String check3Text = (String) upd.get("check3Text");
        String check4 = (String) upd.get("check4");
        String check5 = (String) upd.get("check5");
        String checkText1 = (String) upd.get("checkText1");
        String checkText2 = (String) upd.get("checkText2");

        String lpr6 = (String) upd.get("lpr6");
        String lpr1 = (String) upd.get("lpr1");
        String lpr2 = (String) upd.get("lpr2");
        String lpr3 = (String) upd.get("lpr3");
        String lpr4 = (String) upd.get("lpr4");
        String lpr5 = (String) upd.get("lpr5");
        String lpr5Text = (String) upd.get("lpr5Text");
        String lpr6Text = (String) upd.get("lpr6Text");
        String lprInfo = (String) upd.get("lprInfo");

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
        String engnotes = request.getParameter("note");
        
        String tmCheck1 = (String) upd.get("tmCheck1");    
        String tmCheck2 = (String) upd.get("tmCheck2");    
        String tmCheck3 = (String) upd.get("tmCheck3");    
        String tmPath1 = (String) upd.get("tmPath1");    
        String tmPath2 = (String) upd.get("tmPath2");    
        String tmPath3 = (String) upd.get("tmPath3");    
        String tmName1 = (String) upd.get("tmName1");    
        String tmName2 = (String) upd.get("tmName2");    
        String tmName3 = (String) upd.get("tmName3");    
        String tmNotes = (String) upd.get("tmNotes");    
        

        iNEngineering.setEnggNotes(engnotes);
        try {
            iNEngineering.setDeliveryTime(Float.parseFloat(deliveryTime));
        } catch (Exception e) {
        }
        try {
            iNEngineering.setDeliveryTotal(Float.parseFloat(deliveryTotal));
        } catch (Exception e) {
        }
        try {
            iNEngineering.setPrepTime(Float.parseFloat(prepTime));
        } catch (Exception e) {
        }
        try {
            iNEngineering.setPrepTotal(Float.parseFloat(prepTotal));
        } catch (Exception e) {
        }
        //Batch Update
        iNEngineering.setDeliveryLang(deliveryLang);

        iNEngineering.setExtractText(extractText);

        iNEngineering.setPrepLang(prepLang);
        iNEngineering.setTextBox1(textBox1);
        iNEngineering.setLpr6Text(lpr6Text);
        iNEngineering.setLpr5Text(lpr5Text);
        iNEngineering.setLprInfo(lprInfo);

        iNEngineering.setCheck3Text(check3Text);
        iNEngineering.setCheckText1(checkText1);
        iNEngineering.setCheckText2(checkText2);

        iNEngineering.setVerifiedText(verifiedText);
        // iNEngineering.setVerifiedDate(null);
        try {
            if (verifiedDate.length() > 0) { //if present
                iNEngineering.setVerifiedDate(DateService.getInstance().convertDate(verifiedDate).getTime());
            }
        } catch (Exception e) {
            System.out.println("Date Errooooorr " + e.getMessage());
        }
        iNEngineering.setVerifiedBy(verifiedBy);

        if (extract1.equalsIgnoreCase("on")) {
            iNEngineering.setExtract1(true);
        } else {
            iNEngineering.setExtract1(false);
        }
        if (extract2.equalsIgnoreCase("on")) {
            iNEngineering.setExtract2(true);
        } else {
            iNEngineering.setExtract2(false);
        }
        if (filePrep01.equalsIgnoreCase("on")) {
            iNEngineering.setFilePrep01(true);
        } else {
            iNEngineering.setFilePrep01(false);
        }
        if (filePrep02.equalsIgnoreCase("on")) {
            iNEngineering.setFilePrep02(true);
        } else {
            iNEngineering.setFilePrep02(false);
        }
        if (filePrep1.equalsIgnoreCase("on")) {
            iNEngineering.setFilePrep1(true);
        } else {
            iNEngineering.setFilePrep1(false);
        }
        if (filePrep2.equalsIgnoreCase("on")) {
            iNEngineering.setFilePrep2(true);
        } else {
            iNEngineering.setFilePrep2(false);
        }
        if (filePrep3.equalsIgnoreCase("on")) {
            iNEngineering.setFilePrep3(true);
        } else {
            iNEngineering.setFilePrep3(false);
        }
        if (filePrep4.equalsIgnoreCase("on")) {
            iNEngineering.setFilePrep4(true);
        } else {
            iNEngineering.setFilePrep4(false);
        }
        if (filePrep5.equalsIgnoreCase("on")) {
            iNEngineering.setFilePrep5(true);
        } else {
            iNEngineering.setFilePrep5(false);
        }
        if (filePrep6.equalsIgnoreCase("on")) {
            iNEngineering.setFilePrep6(true);
        } else {
            iNEngineering.setFilePrep6(false);
        }
        if (verified.equalsIgnoreCase("on")) {
            iNEngineering.setVerified(true);
        } else {
            iNEngineering.setVerified(false);
        }


        if (check1.equalsIgnoreCase("on")) {
            iNEngineering.setCheck1(true);
        } else {
            iNEngineering.setCheck1(false);
        }
        if (check2.equalsIgnoreCase("on")) {
            iNEngineering.setCheck2(true);
        } else {
            iNEngineering.setCheck2(false);
        }
        if (check21.equalsIgnoreCase("on")) {
            iNEngineering.setCheck21(true);
        } else {
            iNEngineering.setCheck21(false);
        }
        if (check22.equalsIgnoreCase("on")) {
            iNEngineering.setCheck22(true);
        } else {
            iNEngineering.setCheck22(false);
        }
        if (check4.equalsIgnoreCase("on")) {
            iNEngineering.setCheck4(true);
        } else {
            iNEngineering.setCheck4(false);
        }
        if (check5.equalsIgnoreCase("on")) {
            iNEngineering.setCheck5(true);
        } else {
            iNEngineering.setCheck5(false);
        }

        if (lpr1.equalsIgnoreCase("on")) {
            iNEngineering.setLpr1(true);
        } else {
            iNEngineering.setLpr1(false);
        }
        if (lpr2.equalsIgnoreCase("on")) {
            iNEngineering.setLpr2(true);
        } else {
            iNEngineering.setLpr2(false);
        }
        if (lpr3.equalsIgnoreCase("on")) {
            iNEngineering.setLpr3(true);
        } else {
            iNEngineering.setLpr3(false);
        }
        if (lpr4.equalsIgnoreCase("on")) {
            iNEngineering.setLpr4(true);
        } else {
            iNEngineering.setLpr4(false);
        }
        if (lpr5.equalsIgnoreCase("on")) {
            iNEngineering.setLpr5(true);
        } else {
            iNEngineering.setLpr5(false);
        }
        if (lpr6.equalsIgnoreCase("on")) {
            iNEngineering.setLpr6(true);
        } else {
            iNEngineering.setLpr6(false);
        }
        if(tmCheck1.equalsIgnoreCase("on")){
            iNEngineering.setTmCheck1(true);
        }else{
            iNEngineering.setTmCheck1(false);
        }
        if(tmCheck2.equalsIgnoreCase("on")){
            iNEngineering.setTmCheck2(true);
        }else{
            iNEngineering.setTmCheck2(false);
        }
        if(tmCheck3.equalsIgnoreCase("on")){
            iNEngineering.setTmCheck3(true);
        }else{
            iNEngineering.setTmCheck3(false);
        }
         iNEngineering.setTmName1(tmName1);
         iNEngineering.setTmName2(tmName2);
         iNEngineering.setTmName3(tmName3);
         iNEngineering.setTmNotes(tmNotes);
         iNEngineering.setTmPath1(tmPath1);
         iNEngineering.setTmPath2(tmPath2);
         iNEngineering.setTmPath3(tmPath3);
                 


        InteqaService.getInstance().updateInEngineering(iNEngineering);
        iNEngineering = InteqaService.getInstance().getINEngineering(id);
        
        INQualityFeedback iqf = InteqaService.getInstance().getINQualityFeedback(iNEngineering.getId(), porq, "e");
        if (iqf == null) {
            iqf = new INQualityFeedback();
            iqf.setInteqaid(iNEngineering.getId());
            iqf.setPorq(porq);
            iqf.setTab("e");
            iqf.setPorqid(porqid);
            iqf.setNumber(number);
        }
        
//        iqf.setPorq(porq);
//        iqf.setInteqaid(iNEngineering.getId());
//        iqf.setNumber(number);
//        iqf.setTab("e");
//        iqf.setPorqid(porqid);
        
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
        
        String qualityFeedback = request.getParameter("qualityFeedback");
        if (qualityFeedback.equalsIgnoreCase("y")) {
        InteqaService.getInstance().updateInQualityFeedback(iqf);
}
        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
