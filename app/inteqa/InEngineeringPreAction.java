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
public class InEngineeringPreAction extends Action {

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
        String porq = "p";
        String quoteViewId = request.getParameter("quoteViewId");
        
          try {
            if (quoteViewId != null && !quoteViewId.equalsIgnoreCase("")) {
            q = QuoteService.getInstance().getSingleQuote(Integer.parseInt(quoteViewId));
            projectId = q.getProject().getProjectId().toString();
            request.setAttribute("quote", q);
            porq = "q";
        }
             } catch (Exception e) {
        }
        
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
        Project p = ProjectService.getInstance().getSingleProject(id);





        //get this project's sources
        Set sources = p.getSourceDocs();
        if (sources.size() == 0) {
            sources = q.getSourceDocs();
        }

        //for each source add each sources' Tasks
        List totalDtpTasks = new ArrayList();
        List totalEngTasks = new ArrayList();
        Integer lang0 = 0;


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


        INEngineering INEngg = InteqaService.getInstance().getINEngineering(id);

        //place this project into request for further display in jsp page
        DynaValidatorForm uvg1 = (DynaValidatorForm) form;


        try {
            uvg1.set("prepTime", "" + INEngg.getPrepTime().toString());
        } catch (Exception e) {
            uvg1.set("prepTime", "");

            //System.out.println("erooooooor" + e.getMessage());
        }
        try {
            uvg1.set("prepLang", "" + INEngg.getPrepLang());
        } catch (Exception e) {
            uvg1.set("prepLang", "");
        }
        try {
            uvg1.set("prepTotal", "" + INEngg.getPrepTotal().toString());
        } catch (Exception e) {
            uvg1.set("prepTotal", "");
        }
        try {
            uvg1.set("deliveryTime", "" + INEngg.getDeliveryTime().toString());
        } catch (Exception e) {
            uvg1.set("deliveryTime", "");
        }
        try {
            uvg1.set("deliveryLang", "" + INEngg.getDeliveryLang());
        } catch (Exception e) {
            uvg1.set("deliveryLang", "");
        }
        try {
            uvg1.set("deliveryTotal", "" + INEngg.getDeliveryTotal().toString());
        } catch (Exception e) {
            uvg1.set("deliveryTotal", "");
        }
        try {
            uvg1.set("filePrep01", "" + INEngg.isFilePrep01());
        } catch (Exception e) {
            uvg1.set("filePrep01", "");
        }
        try {
            uvg1.set("filePrep02", "" + INEngg.isFilePrep02());
        } catch (Exception e) {
            uvg1.set("filePrep02", "");
        }
        try {
            uvg1.set("filePrep1", "" + INEngg.isFilePrep1());
        } catch (Exception e) {
            uvg1.set("filePrep1", "");
        }
        try {
            uvg1.set("filePrep2", "" + INEngg.isFilePrep2());
        } catch (Exception e) {
            uvg1.set("filePrep2", "");
        }
        try {
            uvg1.set("filePrep3", "" + INEngg.isFilePrep3());
        } catch (Exception e) {
            uvg1.set("filePrep3", "");
        }
        try {
            uvg1.set("filePrep4", "" + INEngg.isFilePrep4());
        } catch (Exception e) {
            uvg1.set("filePrep4", "");
        }
        try {
            uvg1.set("filePrep5", "" + INEngg.isFilePrep5());
        } catch (Exception e) {
            uvg1.set("filePrep5", "");
        }
        try {
            uvg1.set("filePrep6", "" + INEngg.isFilePrep6());
        } catch (Exception e) {
            uvg1.set("filePrep6", "");
        }
        try {
            uvg1.set("extractText", "" + StandardCode.getInstance().noNull(INEngg.getExtractText()));
        } catch (Exception e) {
            uvg1.set("extractText", "");
        }
        try {
            uvg1.set("extract1", "" + INEngg.isExtract1());
        } catch (Exception e) {
            uvg1.set("extract1", "");
        }
        try {
            uvg1.set("extract2", "" + INEngg.isExtract2());
        } catch (Exception e) {
            uvg1.set("extract2", "");
        }
//        try {
//            uvg1.set("extractText", "" + INEngg.isExtract2());
//        } catch (Exception e) {
//            uvg1.set("extractText", "");
//        }

        try {
            uvg1.set("verified", "" + INEngg.isVerified());
        } catch (Exception e) {
            uvg1.set("verified", "");
        }
        try {
            uvg1.set("verifiedBy", "" + INEngg.getVerifiedBy());
        } catch (Exception e) {
            uvg1.set("verifiedBy", "");
        }
        try {
            uvg1.set("verifiedDate", "" + StandardCode.getInstance().noNull((INEngg.getVerifiedDate()).toString()));
        } catch (Exception e) {
            uvg1.set("verifiedDate", "");
        }

        try {
            uvg1.set("textBox1", "" + StandardCode.getInstance().noNull(INEngg.getTextBox1()));
        } catch (Exception e) {
            uvg1.set("textBox1", "");
        }
        try {
            uvg1.set("verifiedText", "" + StandardCode.getInstance().noNull(INEngg.getVerifiedText()));
        } catch (Exception e) {
            uvg1.set("verifiedText", "");
        }

        try {
            uvg1.set("check1", "" + INEngg.isCheck1());
        } catch (Exception e) {
            uvg1.set("check1", "");
        }
        try {
            uvg1.set("check2", "" + INEngg.isCheck2());
        } catch (Exception e) {
            uvg1.set("check2", "");
        }
        try {
            uvg1.set("check21", "" + INEngg.isCheck21());
        } catch (Exception e) {
            uvg1.set("check21", "");
        }
        try {
            uvg1.set("check22", "" + INEngg.isCheck22());
        } catch (Exception e) {
            uvg1.set("check22", "");
        }
        try {
            uvg1.set("check3Text", "" + StandardCode.getInstance().noNull(INEngg.getCheck3Text()));
        } catch (Exception e) {
            uvg1.set("check3Text", "");
        }
        try {
            uvg1.set("check4", "" + INEngg.isCheck4());
        } catch (Exception e) {
            uvg1.set("check4", "");
        }
        try {
            uvg1.set("check5", "" + INEngg.isCheck5());
        } catch (Exception e) {
            uvg1.set("check5", "");
        }
        try {
            uvg1.set("checkText1", "" + StandardCode.getInstance().noNull(INEngg.getCheckText1()));
        } catch (Exception e) {
            uvg1.set("checkText1", "");
        }
        try {
            uvg1.set("checkText2", "" + StandardCode.getInstance().noNull(INEngg.getCheckText2()));
        } catch (Exception e) {
            uvg1.set("checkText2", "");
        }

        try {
            uvg1.set("lpr1", "" + INEngg.isLpr1());
        } catch (Exception e) {
            uvg1.set("lpr1", "");
        }
        try {
            uvg1.set("lpr2", "" + INEngg.isLpr2());
        } catch (Exception e) {
            uvg1.set("lpr2", "");
        }
        try {
            uvg1.set("lpr3", "" + INEngg.isLpr3());
        } catch (Exception e) {
            uvg1.set("lpr3", "");
        }
        try {
            uvg1.set("lpr4", "" + INEngg.isLpr4());
        } catch (Exception e) {
            uvg1.set("lpr4", "");
        }
        try {
            uvg1.set("lpr5", "" + INEngg.isLpr5());
        } catch (Exception e) {
            uvg1.set("lpr5", "");
        }
        try {
            uvg1.set("lpr6", "" + INEngg.isLpr6());
        } catch (Exception e) {
            uvg1.set("lpr6", "");
        }
    try {
            uvg1.set("lpr3Text", "" + StandardCode.getInstance().noNull(INEngg.getLpr3Text()));
        } catch (Exception e) {
            uvg1.set("lpr3Text", "");
        }
        try {
            uvg1.set("lpr5Text", "" + StandardCode.getInstance().noNull(INEngg.getLpr5Text()));
        } catch (Exception e) {
            uvg1.set("lpr5Text", "");
        }
        try {
            uvg1.set("lpr6Text", "" + StandardCode.getInstance().noNull(INEngg.getLpr6Text()));
        } catch (Exception e) {
            uvg1.set("lpr6Text", "");
        }


        try {
            uvg1.set("lprInfo", "" + StandardCode.getInstance().noNull(INEngg.getLprInfo()));
        } catch (Exception e) {
            uvg1.set("lprInfo", "");
        }
        INQualityFeedback iqf = null;
        try {
            iqf = InteqaService.getInstance().getINQualityFeedback(INEngg.getId(), porq, "e");
        } catch (Exception e) {
        }

        try {
            uvg1.set("comScore", "" + iqf.getComScore());
        } catch (Exception e) {
            uvg1.set("comScore", "");
        }

        try {
            uvg1.set("comExplain", "" + iqf.getComExplain());
        } catch (Exception e) {
            uvg1.set("comExplain", "");
        }

        try {
            uvg1.set("comEnteredBy", "" + iqf.getComEnteredBy());
        } catch (Exception e) {
            uvg1.set("comEnteredBy", "");
        }

        try {
            uvg1.set("timScore", "" + iqf.getTimScore());
        } catch (Exception e) {
            uvg1.set("timScore", "");
        }

        try {
            uvg1.set("timExplain", "" + iqf.getTimExplain());
        } catch (Exception e) {
            uvg1.set("timExplain", "");
        }

        try {
            uvg1.set("timEnteredBy", "" + iqf.getTimEnteredBy());
        } catch (Exception e) {
            uvg1.set("timEnteredBy", "");
        }

        try {
            uvg1.set("quaScore", "" + iqf.getQuaScore());
        } catch (Exception e) {
            uvg1.set("quaScore", "");
        }

        try {
            uvg1.set("quaExplain", "" + iqf.getQuaExplain());
        } catch (Exception e) {
            uvg1.set("quaExplain", "");
        }

        try {
            uvg1.set("quaEnteredBy", "" + iqf.getQuaEnteredBy());
        } catch (Exception e) {
            uvg1.set("quaEnteredBy", "");
        }

        try {
            uvg1.set("othScore", "" + iqf.getOthScore());
        } catch (Exception e) {
            uvg1.set("othScore", "");
        }

        try {
            uvg1.set("othExplain", "" + iqf.getOthExplain());
        } catch (Exception e) {
            uvg1.set("othExplain", "");
        }

        try {
            uvg1.set("othEnteredBy", "" + iqf.getOthEnteredBy());
        } catch (Exception e) {
            uvg1.set("othEnteredBy", "");
        }
        
        //TM  Management

        try {           
            uvg1.set("tmCheck1", "" + INEngg.isTmCheck1());
        } catch (Exception e) {
            uvg1.set("tmCheck1", "");
        }
        try {
            uvg1.set("tmCheck2", "" + INEngg.isTmCheck2());
        } catch (Exception e) {
            uvg1.set("tmCheck2", "");
        }
        try {
            uvg1.set("tmCheck3", "" + INEngg.isTmCheck3());
        } catch (Exception e) {
            uvg1.set("tmCheck3", "");
        }

        try {
            uvg1.set("tmPath1", "" + StandardCode.getInstance().noNull(INEngg.getTmPath1()));
        } catch (Exception e) {
            uvg1.set("tmPath1", "");
        }
        
        try {
            uvg1.set("tmPath2", "" + StandardCode.getInstance().noNull(INEngg.getTmPath2()));
        } catch (Exception e) {
            uvg1.set("tmPath2", "");
        }
        try {
            uvg1.set("tmPath3", "" + StandardCode.getInstance().noNull(INEngg.getTmPath3()));
        } catch (Exception e) {
            uvg1.set("tmPath3", "");
        }
        try {
            uvg1.set("tmName1", "" + StandardCode.getInstance().noNull(INEngg.getTmName1()));
        } catch (Exception e) {
            uvg1.set("tmName1", "");
        }
        try {
            uvg1.set("tmName2", "" + StandardCode.getInstance().noNull(INEngg.getTmName2()));
        } catch (Exception e) {
            uvg1.set("tmName2", "");
        }
        try {
            uvg1.set("tmName3", "" + StandardCode.getInstance().noNull(INEngg.getTmName3()));
        } catch (Exception e) {
            uvg1.set("tmName3", "");
        }
        try {
            uvg1.set("tmNotes", "" + StandardCode.getInstance().noNull(INEngg.getTmNotes()));
        } catch (Exception e) {
            uvg1.set("tmNotes", "");
        }
        
        

        request.setAttribute("formValue1", uvg1);
        request.setAttribute("INEngineering", INEngg);
        request.setAttribute("project", p);
        request.setAttribute("lang0", "" + lang0);

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
