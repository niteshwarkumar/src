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
import app.standardCode.StandardCode;
import org.apache.struts.validator.*;

/**
 *
 * @author Niteshwar
 */
public class InDtpPreAction extends Action {

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
        Quote1 q = null;
        Client_Quote cq = null;
        String quoteViewId = request.getParameter("quoteViewId");



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

        //END get id of current project from either request, attribute, or cookie

        //get project to edit
        Integer cqQuoteId = 0;
        Project p = ProjectService.getInstance().getSingleProject(id);
        String porq = "p";
        String number = p.getNumber() + p.getCompany().getCompany_code();
        Integer porqid = p.getProjectId();
        try {
            if (quoteViewId != null) {
                if (quoteViewId != "0") {
                    q = QuoteService.getInstance().getSingleQuote(Integer.parseInt(quoteViewId));
                    cq = QuoteService.getInstance().get_SingleClientQuote(Integer.parseInt(quoteViewId));
                    projectId = q.getProject().getProjectId().toString();
                    request.setAttribute("quote", q);
                    porq = "q";
                    number = q.getNumber();
                    porqid = q.getQuote1Id();
                }
            }
        } catch (Exception e) {
        }

        Quote1 quote = QuoteService.getInstance().getSingleQuoteFromProject(id);
        try {


            List clq = QuoteService.getInstance().getClient_Quote(quote.getQuote1Id());
            cq = (Client_Quote) clq.get(0);
            cqQuoteId = cq.getid();
            List ptl = ProjectService.getInstance().getProjectTechnicalList(p.getProjectId());
            if (!quote.getStatus().equalsIgnoreCase("pending") && ptl.size() == 0) {

                List technicalList = QuoteService.getInstance().getTechnicalList(cqQuoteId);
                for (int i = 0; i < technicalList.size(); i++) {
                    Technical t = (Technical) technicalList.get(i);
                    Project_Technical pt = new Project_Technical();


//     t.getTechnicalId());
                    pt.setSourceos(t.getSourceOs());
                    pt.setTargetos(t.getTargetOs());
                    pt.setSourceapp(t.getSourceApplication());
                    pt.setTargetapp(t.getTargetApplication());
                    pt.setSourcever(t.getSourceVersion());
                    pt.setTargetver(t.getTargetVersion());
                    pt.setProjectid(p.getProjectId());
                    ProjectService.getInstance().addUpdateProjectTechnical(pt);

                }
            }

        } catch (Exception e) {
        }



        //get this project's sources
        Set sources = p.getSourceDocs();
        if (sources.size() == 0) {
            sources = q.getSourceDocs();
        }



        //for each source add each sources' Tasks
        List totalDtpTasks = new ArrayList();
        List totalEngTasks = new ArrayList();
        Integer lang0 = 0, lang1 = 0, lang2 = 0, lang3 = 0, lang4 = 0, lang5 = 0, lang6 = 0;

        //for each source
        for (Iterator sourceIter = sources.iterator(); sourceIter.hasNext();) {
            SourceDoc sd = (SourceDoc) sourceIter.next();

            //for each target of this source
            for (Iterator linTargetIter = sd.getTargetDocs().iterator(); linTargetIter.hasNext();) {
                TargetDoc td = (TargetDoc) linTargetIter.next();

                //for each dtp Task of this target
                for (Iterator dtpTaskIter = td.getDtpTasks().iterator(); dtpTaskIter.hasNext();) {
                    DtpTask dt = (DtpTask) dtpTaskIter.next();
                    totalDtpTasks.add(dt);
                }
                //for each dtp Task of this target
                for (Iterator engTaskIter = td.getEngTasks().iterator(); engTaskIter.hasNext();) {
                    EngTask et = (EngTask) engTaskIter.next();
                    totalEngTasks.add(et);
                }

                if (!td.getLanguage().equals("All")) { //if not placeholder for task lists

                    if (!td.getLanguage().equalsIgnoreCase("")) {
                        Language l = ProjectService.getInstance().getLanguage(ProjectService.getInstance().getLanguageId(td.getLanguage()));

                        if (l.getLang_group().equalsIgnoreCase("1")) {
                            lang1++;
                        } else if (l.getLang_group().equalsIgnoreCase("2")) {
                            lang2++;
                        } else if (l.getLang_group().equalsIgnoreCase("3")) {
                            lang3++;
                        } else if (l.getLang_group().equalsIgnoreCase("4")) {
                            lang4++;
                        } else if (l.getLang_group().equalsIgnoreCase("5")) {
                            lang5++;
                        } else {
                            lang6++;
                        }

                        lang0++;

                    }
                }
            }
        }


        //array for display in jsp

        DtpTask[] dtpTasksArray = (DtpTask[]) totalDtpTasks.toArray(new DtpTask[0]);
        EngTask[] engTasksArray = (EngTask[]) totalEngTasks.toArray(new EngTask[0]);

        //find total of DtpTasks
        double dtpTaskTotal = 0;
        for (int i = 0; i < dtpTasksArray.length; i++) {
            if (dtpTasksArray[i].getTotal() != null) {
                dtpTaskTotal += dtpTasksArray[i].getTotal();
            }
        }
        double dtpTaskTotalTeam = 0;
        for (int i = 0; i < dtpTasksArray.length; i++) {
            if (dtpTasksArray[i].getTotalTeam() != null) {
                dtpTaskTotalTeam += dtpTasksArray[i].getTotalTeam();
            }
        }


        //find total of EngTasks
        double engTaskTotal = 0;
        for (int i = 0; i < engTasksArray.length; i++) {
            if (engTasksArray[i].getTotal() != null) {
                engTaskTotal += engTasksArray[i].getTotal();
            }
        }
        double engTaskTotalTeam = 0;
        for (int i = 0; i < engTasksArray.length; i++) {
            if (engTasksArray[i].getTotalTeam() != null) {
                engTaskTotalTeam += engTasksArray[i].getTotalTeam();
            }
        }


        INDtp iDtp = InteqaService.getInstance().getINDtp(id);

        //place this project into request for further display in jsp page
        DynaValidatorForm uvg = (DynaValidatorForm) form;
        Boolean setTrue = true;

        try {
            uvg.set("prepTime", iDtp.getPrepTime());
        } catch (Exception e) {
            uvg.set("prepTime", "");
        }
        try {
            uvg.set("prepLang", iDtp.getPrepLang());
        } catch (Exception e) {
            uvg.set("prepLang", "");
        }
        try {
            uvg.set("prepTotal", iDtp.getPrepTotal());
        } catch (Exception e) {
            uvg.set("prepTotal", "");
        }
        try {
            uvg.set("deliveryTime", iDtp.getDeliveryTime());
        } catch (Exception e) {
            uvg.set("deliveryTime", "");
        }
        try {
            uvg.set("deliveryLang", iDtp.getDeliveryLang());
        } catch (Exception e) {
            uvg.set("deliveryLang", "");
        }
        try {
            uvg.set("deliveryTotal", iDtp.getDeliveryTotal());
        } catch (Exception e) {
            uvg.set("deliveryTotal", "");
        }
        try {
            uvg.set("srcPf1",""+ iDtp.isSrcPf1());
        } catch (Exception e) {
//            try{
//             if(cq.getOs().equalsIgnoreCase("windows")) {
//                uvg.set("srcPf1",""+ true);
//            }
//            else {
//                uvg.set("srcPf1", "");
//            }}catch(Exception e1){
            uvg.set("srcPf1", "");
//            }
        }
        try {
            uvg.set("srcPf2",""+ iDtp.isSrcPf2());
        } catch (Exception e) {
//            try{
//            if(cq.getOs().equalsIgnoreCase("mac")) {
//                uvg.set("srcPf2","on");
//            }
//            else {
//                uvg.set("srcPf2", "");
//            }}catch(Exception e1){
            uvg.set("srcPf2", "");
//            }
        }
        try {
            uvg.set("srcPf3",""+ iDtp.isSrcPf3());
        } catch (Exception e) {
            uvg.set("srcPf3", "");
        }
        try {
            uvg.set("srcPf4", ""+iDtp.isSrcPf4());
        } catch (Exception e) {
            uvg.set("srcPf4", "");
        }
        try {
            uvg.set("srcPf5",""+ iDtp.isSrcPf5());
        } catch (Exception e) {
            uvg.set("srcPf5", "");
        }
        try {
            uvg.set("srcPf6",""+ iDtp.isSrcPf6());
        } catch (Exception e) {
            uvg.set("srcPf6", "");
        }
        try {

            uvg.set("dtpToolName", iDtp.getDtpToolName());
        } catch (Exception e) {
            uvg.set("dtpToolName", "");
        }
        try {
            uvg.set("dtpTool1",""+ iDtp.isDtpTool1());
        } catch (Exception e) {
            uvg.set("dtpTool1", "");
        }
        try {
            uvg.set("dtpToolOth", iDtp.isDtpToolOth());
        } catch (Exception e) {
            uvg.set("dtpToolOth", "");
        }
        try {
            uvg.set("dtpToolOthText", iDtp.getDtpToolOthText());
        } catch (Exception e) {
            uvg.set("dtpToolOthText", "");
        }
        try {
            uvg.set("srcFont1",""+ iDtp.isSrcFont1());
        } catch (Exception e) {
            uvg.set("srcFont1", "");
        }
        try {
            uvg.set("srcFont2",""+ iDtp.isSrcFont2());
        } catch (Exception e) {
            uvg.set("srcFont2", "");
        }
        try {
            uvg.set("tgtFont1",""+ iDtp.isTgtFont1());
        } catch (Exception e) {
            uvg.set("tgtFont1", "");
        }
        try {
            uvg.set("tgtFont2",""+ iDtp.isTgtFont2());
        } catch (Exception e) {
            uvg.set("tgtFont2", "");
        }
        try {
            uvg.set("TgtFontText", iDtp.getTgtFontText());
        } catch (Exception e) {
            uvg.set("TgtFontText", "");
        }
        try {
            uvg.set("verified",""+ iDtp.isVerified());
        } catch (Exception e) {
            uvg.set("verified", "");
        }
        try {
            uvg.set("verifiedBy", iDtp.getVerifiedBy());
        } catch (Exception e) {
            uvg.set("verifiedBy", "");
        }
        try {
            uvg.set("verifiedDate",""+ iDtp.getVerifiedDate());
        } catch (Exception e) {
            uvg.set("verifiedDate", "");
        }
        try {
            uvg.set("verifiedText", iDtp.getVerifiedText());
        } catch (Exception e) {
            uvg.set("verifiedText", "");
        }
        try {
            uvg.set("textBox1", iDtp.getTextBox1());
        } catch (Exception e) {
            uvg.set("textBox1", "");
        }
        try {
            uvg.set("textBox2", iDtp.getTextBox2());
        } catch (Exception e) {
            uvg.set("textBox2", "");
        }
        try {
            uvg.set("textBox3", iDtp.getTextBox3());
        } catch (Exception e) {
            uvg.set("textBox3", "");
        }
        try {
            uvg.set("textBox4", iDtp.getTextBox4());
        } catch (Exception e) {
            uvg.set("textBox4", "");
        }

        try {
            uvg.set("check1", "" + iDtp.isCheck1());
        } catch (Exception e) {
            uvg.set("check1", "");
        }
        try {
            uvg.set("check2", "" + iDtp.isCheck2());
        } catch (Exception e) {
            uvg.set("check2", "");
        }
        try {
            uvg.set("check21", "" + iDtp.isCheck21());
        } catch (Exception e) {
            uvg.set("check21", "");
        }
        try {
            uvg.set("check22", "" + iDtp.isCheck22());
        } catch (Exception e) {
            uvg.set("check22", "");
        }
        try {
            uvg.set("checkText3", "" + iDtp.getCheckText3());
        } catch (Exception e) {
            uvg.set("checkText3", "");
        }
        try {
            uvg.set("check4", "" + iDtp.isCheck4());
        } catch (Exception e) {
            uvg.set("check4", "");
        }
        try {
            uvg.set("check5", "" + iDtp.isCheck5());
        } catch (Exception e) {
            uvg.set("check5", "");
        }
        try {
            uvg.set("checkText1", "" + iDtp.getCheckText1());
        } catch (Exception e) {
            uvg.set("checkText1", "");
        }
        try {
            uvg.set("checkText2", "" + iDtp.getCheckText2());
        } catch (Exception e) {
            uvg.set("checkText2", "");
        }
        try {
            uvg.set("check3", "" + iDtp.isCheck3());
        } catch (Exception e) {
            uvg.set("check3", "");
        }
        try {
            uvg.set("check6", "" + iDtp.isCheck6());
        } catch (Exception e) {
            uvg.set("check6", "");
        }
        try {
            uvg.set("check7", "" + iDtp.isCheck7());
        } catch (Exception e) {
            uvg.set("check7", "");
        }

        try {
            uvg.set("check6text", "" + iDtp.getCheck6text());
        } catch (Exception e) {
            uvg.set("check6text", "");
        }
        try {
            uvg.set("check7text", "" + iDtp.getCheck7text());
        } catch (Exception e) {
            uvg.set("check7text", "");
        }

        try {
            if(iDtp.getHours0()!=null)
            uvg.set("hours0","" + iDtp.getHours0());
            else uvg.set("hours0", "");
        } catch (Exception e) {
            uvg.set("hours0", ""); 
        }

        try {
            if(iDtp.getHours1()!=null)
            uvg.set("hours1", "" + iDtp.getHours1());
            else uvg.set("hours1", "");            
        } catch (Exception e) {
            uvg.set("hours1", "");
        }
        try {
            if(iDtp.getHours2()!=null)
            uvg.set("hours2","" + iDtp.getHours2());
            else uvg.set("hours2", "");
        } catch (Exception e) {
            uvg.set("hours2", "");
        }
        try {
            if(iDtp.getHours3()!=null)
            uvg.set("hours3", "" + iDtp.getHours3());
            else uvg.set("hours3", "");
        } catch (Exception e) {
            uvg.set("hours3", "");
        }
        try {
            if(iDtp.getHours4()!=null)
            uvg.set("hours4", "" + iDtp.getHours4());
            else uvg.set("hours4", "");
        } catch (Exception e) {
            uvg.set("hours4", "");
        }
        try {
            if(iDtp.getHours5()!=null)
            uvg.set("hours5", "" + iDtp.getHours5());
            else uvg.set("hours5", "");
        } catch (Exception e) {
            uvg.set("hours5", "");
        }
        try {
            if(iDtp.getHours6()!=null)
            uvg.set("hours6", "" + iDtp.getHours6());
            else uvg.set("hours6", "");
        } catch (Exception e) {
            uvg.set("hours6", "");
        }


        try {
            uvg.set("unit0", "" + iDtp.getUnit0());
        } catch (Exception e) {
            uvg.set("unit0", "");
        }
        try {
            uvg.set("unit1", "" + iDtp.getUnit1());
        } catch (Exception e) {
            uvg.set("unit1", "");
        }
        try {
            uvg.set("unit2", "" + iDtp.getUnit2());
        } catch (Exception e) {
            uvg.set("unit2", "");
        }
        try {
            uvg.set("unit3", "" + iDtp.getUnit3());
        } catch (Exception e) {
            uvg.set("unit3", "");
        }
        try {
            uvg.set("unit4", "" + iDtp.getUnit4());
        } catch (Exception e) {
            uvg.set("unit4", "");
        }
        try {
            uvg.set("unit5", "" + iDtp.getUnit5());
        } catch (Exception e) {
            uvg.set("unit5", "");
        }
        try {
            uvg.set("unit6", "" + iDtp.getUnit6());
        } catch (Exception e) {
            uvg.set("unit6", "");
        }
        
             INQualityFeedback iqf = null;
        try {
            iqf = InteqaService.getInstance().getINQualityFeedback(iDtp.getId(), porq, "d");
        } catch (Exception e) {
        }

        try {
            uvg.set("comScore", "" + iqf.getComScore());
        } catch (Exception e) {
            uvg.set("comScore", "");
        }

        try {
            uvg.set("comExplain", "" + iqf.getComExplain());
        } catch (Exception e) {
            uvg.set("comExplain", "");
        }

        try {
            uvg.set("comEnteredBy", "" + iqf.getComEnteredBy());
        } catch (Exception e) {
            uvg.set("comEnteredBy", "");
        }

        try {
            uvg.set("timScore", "" + iqf.getTimScore());
        } catch (Exception e) {
            uvg.set("timScore", "");
        }

        try {
            uvg.set("timExplain", "" + iqf.getTimExplain());
        } catch (Exception e) {
            uvg.set("timExplain", "");
        }

        try {
            uvg.set("timEnteredBy", "" + iqf.getTimEnteredBy());
        } catch (Exception e) {
            uvg.set("timEnteredBy", "");
        }

        try {
            uvg.set("quaScore", "" + iqf.getQuaScore());
        } catch (Exception e) {
            uvg.set("quaScore", "");
        }

        try {
            uvg.set("quaExplain", "" + iqf.getQuaExplain());
        } catch (Exception e) {
            uvg.set("quaExplain", "");
        }

        try {
            uvg.set("quaEnteredBy", "" + iqf.getQuaEnteredBy());
        } catch (Exception e) {
            uvg.set("quaEnteredBy", "");
        }

        try {
            uvg.set("othScore", "" + iqf.getOthScore());
        } catch (Exception e) {
            uvg.set("othScore", "");
        }

        try {
            uvg.set("othExplain", "" + iqf.getOthExplain());
        } catch (Exception e) {
            uvg.set("othExplain", "");
        }

        try {
            uvg.set("othEnteredBy", "" + iqf.getOthEnteredBy());
        } catch (Exception e) {
            uvg.set("othEnteredBy", "");
        }


        request.setAttribute("formValue", uvg);
        request.setAttribute("INDtp", iDtp);
        request.setAttribute("project", p); 
        request.setAttribute("lang1", "" + lang1);
        request.setAttribute("lang2", "" + lang2);
        request.setAttribute("lang3", "" + lang3);
        request.setAttribute("lang4", "" + lang4);
        request.setAttribute("lang5", "" + lang5);
        request.setAttribute("lang6", "" + lang6);
        request.setAttribute("lang0", "" + lang0);





//        request.setAttribute("preTransDtp",  dtpTaskTotal);
//        request.setAttribute("postTransDtp", dtpTaskTotalTeam);
//        request.setAttribute("preTransEng", engTaskTotal);
//        request.setAttribute("postTransEng", engTaskTotalTeam);

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
