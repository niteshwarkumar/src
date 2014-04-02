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
public class InBasicsPreAction extends Action {

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
          Quote1 q=null;
        String quoteViewId = request.getParameter("quoteViewId");
        if (quoteViewId != null && !quoteViewId.equalsIgnoreCase("0")) {
            q = QuoteService.getInstance().getSingleQuote(Integer.parseInt(quoteViewId));
            projectId = q.getProject().getProjectId().toString();
            request.setAttribute("quote", q);
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


String dtpFlag="no";


        //get this project's sources
        Set sources = p.getSourceDocs();
          if(sources.size()==0) {
            sources = q.getSourceDocs();
        }

        //for each source add each sources' Tasks
        List totalDtpTasks = new ArrayList();
        List totalEngTasks = new ArrayList();


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
                    if(dt.getTaskName().equalsIgnoreCase("dtp")){
                     dtpFlag="yes";
                    }
                }
                //for each dtp Task of this target
                for (Iterator engTaskIter = td.getEngTasks().iterator(); engTaskIter.hasNext();) {
                    EngTask et = (EngTask) engTaskIter.next();
                    totalEngTasks.add(et);
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


        INBasics INBasics = InteqaService.getInstance().getINBasics(id);

        //place this project into request for further display in jsp page
        DynaValidatorForm uvg = (DynaValidatorForm) form;


        try {
            uvg.set("server", "" + INBasics.getServer());
        } catch (Exception e) {
            uvg.set("server", "");
        }
        try {
            uvg.set("ftp", "" + INBasics.getFtp());
        } catch (Exception e) {
            uvg.set("ftp", "");
        }
        try {
            uvg.set("other", "" + INBasics.getOther());
        } catch (Exception e) {
            uvg.set("other", "");
        }

        try {
            uvg.set("dtpReq1", "" + INBasics.isDtpReq1());
        } catch (Exception e) {
            uvg.set("dtpReq1", "");
        }
        try {
            uvg.set("dtpReq2", "" + INBasics.isDtpReq2());
        } catch (Exception e) {
            if(dtpFlag.equalsIgnoreCase("yes")){uvg.set("dtpReq2", "true");
            }else{
            uvg.set("dtpReq2", "");}
        }
        try {
            uvg.set("genGra1", "" + INBasics.isGenGra1());
        } catch (Exception e) {
            uvg.set("genGra1", "");
        }
        try {
            uvg.set("genGra2", "" + INBasics.isGenGra2());
        } catch (Exception e) {
            uvg.set("genGra2", "");
        }
        try {
            uvg.set("genGra3", "" + INBasics.isGenGra3());
        } catch (Exception e) {
            uvg.set("genGra3", "");
        }
        try {
            uvg.set("genGra4", "" + INBasics.isGenGra4());
        } catch (Exception e) {
            uvg.set("genGra4", "");
        }
        try {
            uvg.set("screen1", "" + INBasics.isScreen1());
        } catch (Exception e) {
            uvg.set("screen1", "");
        }
        try {
            uvg.set("screen2", "" + INBasics.isScreen2());
        } catch (Exception e) {
            uvg.set("screen2", "");
        }
        try {
            uvg.set("screen3", "" + INBasics.isScreen3());
        } catch (Exception e) {
            uvg.set("screen3", "");
        }
        try {
            uvg.set("screen4", "" + INBasics.isScreen4());
        } catch (Exception e) {
            uvg.set("screen4", "");
        }
        try {
            uvg.set("deliveryFormat1", "" + INBasics.isDeliveryFormat1());
        } catch (Exception e) {
            uvg.set("deliveryFormat1", "");
        }
        try {
            uvg.set("deliveryFormatOth", "" + INBasics.isDeliveryFormatOth());
        } catch (Exception e) {
            uvg.set("deliveryFormatOth", "");
        }
        try {
            uvg.set("deliveryFormatOthText", "" + INBasics.getDeliveryFormatOthText());
        } catch (Exception e) {
            uvg.set("deliveryFormatOthText", "");
        }
        try {
            uvg.set("DeliveryFiles1", "" + INBasics.isDeliveryFiles1());
        } catch (Exception e) {
            uvg.set("DeliveryFiles1", "");
        }
        try {
            uvg.set("DeliveryFiles2", "" + INBasics.isDeliveryFiles2());
        } catch (Exception e) {
            uvg.set("DeliveryFiles2", "");
        }
        try {
            uvg.set("DeliveryFilesOth", "" + INBasics.isDeliveryFilesOth());
        } catch (Exception e) {
            uvg.set("DeliveryFilesOth", "");
        }
        try {
            uvg.set("DeliveryFilesOthText", "" + INBasics.getDeliveryFilesOthText());
        } catch (Exception e) {
            uvg.set("DeliveryFilesOthText", "");
        }
        try {
            uvg.set("OtherInstruction", "" + INBasics.getOtherInstruction());
        } catch (Exception e) {
            uvg.set("OtherInstruction", "");
        }
        try {
            uvg.set("verified", "" + INBasics.isVerified());
        } catch (Exception e) {
            uvg.set("verified", "");
        }
        try {
            uvg.set("verifiedBy", "" + INBasics.getVerifiedBy());
        } catch (Exception e) {
            uvg.set("verifiedBy", "");
        }
        try {
            uvg.set("verifiedDate", "" + INBasics.getVerifiedDate());
            if (INBasics.getVerifiedDate() == null) {
                uvg.set("verifiedDate", "");
            }
        } catch (Exception e) {
            uvg.set("verifiedDate", "");
        }
        try {
            uvg.set("verifiedText", "" + INBasics.getVerifiedText());
        } catch (Exception e) {
            uvg.set("verifiedText", "");
        }
        try {
            uvg.set("textBox1", "" + INBasics.getTextBox1());
        } catch (Exception e) {
            uvg.set("textBox1", "");
        }
        try {
            uvg.set("textBox2", "" + INBasics.getTextBox2());
        } catch (Exception e) {
            uvg.set("textBox2", "");
        }
        try {
            uvg.set("textBox3", "" + INBasics.getTextBox3());
        } catch (Exception e) {
            uvg.set("textBox3", "");
        }
        int quantitySum=0;
        try {
                  List sourceFileList=InteqaService.getInstance().getSourceFileList(p.getProjectId());
    for(int i=0;i<sourceFileList.size();i++){
        INSourceFile t=(INSourceFile) sourceFileList.get(i);
            quantitySum+=t.getQuantity();

    }
        } catch (Exception e) {
        }
   



        request.setAttribute("quantitySum", quantitySum);
        request.setAttribute("formValue", uvg);
        request.setAttribute("INBasics", INBasics);
        request.setAttribute("project", p);
//        request.setAttribute("preTransDtp",  dtpTaskTotal);
//        request.setAttribute("postTransDtp", dtpTaskTotalTeam);
//        request.setAttribute("preTransEng", engTaskTotal);
//        request.setAttribute("postTransEng", engTaskTotalTeam);

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
