/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.inteqa;

import app.project.*;
import app.quote.Quote1;
import app.quote.QuotePriority;
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
import org.apache.struts.validator.DynaValidatorForm;

/**
 *
 * @author Niteshwar
 */
public class InQReportUpdateAction extends Action {

  // ----------------------------------------------------- Instance Variables
  /**
   * The
   * <code>Log</code>  instance for this application.
   */
  private Log log =
          LogFactory.getLog("org.apache.struts.webapp.Example");

  // --------------------------------------------------------- Public Methods
  /**
   * Process the specified HTTP request, and create the corresponding
   * HTTP response (or forward to another web component that will
   * create it). Return an
   * <code>ActionForward</code> instance describing where and how
   * control should be forwarded, or
   * <code>null</code> if the response has already been completed.
   *
   * @param mapping The ActionMapping used to select this instance
   * @param form The optional ActionForm bean for this request (if
   * any)
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

    Integer id = Integer.valueOf(projectId);

    //END get id of current project from either request, attribute, or cookie
    DynaValidatorForm upd = (DynaValidatorForm) form;


    String DTPNotes = (String) upd.get("dtpNotes");
    String DtpRequirements = (String) upd.get("dtpRequirements");
    String EnggNotes = (String) upd.get("enggNotes");
    String EnggRequirements = (String) upd.get("enggRequirements");
    String LingReq1 = (String) upd.get("lingReq1");
    String LingReq2 = (String) upd.get("lingReq2");
    String LingReq3 = (String) upd.get("lingReq3");
    String LingReq4 = (String) upd.get("lingReq4");
    String LingReq5 = (String) upd.get("lingReq5");
    String LingReq6 = (String) upd.get("lingReq6");
    String LingReqText5 = (String) upd.get("lingReqText5");
    String LingReqText6 = (String) upd.get("lingReqText6");
    String OtherInfo = (String) upd.get("otherInfo");
    String verified = (String) upd.get("verified");
    String verifiedBy = (String) upd.get("verifiedBy");
    String verifiedDate = (String) upd.get("verifiedDate");
    String verifiedText = (String) upd.get("verifiedText");
    String normal = (String) upd.get("normal");
    String someUrgent = (String) upd.get("someUrgent");
    String urgent = (String) upd.get("urgent");
    String inProgress = (String) upd.get("inProgress");
    String done1 = (String) upd.get("done1");
    String cross = (String) upd.get("cross");
    String quoteSentDate = (String) upd.get("quoteSentDate");


    Quote1 q = QuoteService.getInstance().getSingleQuoteFromProject(id);
    QuotePriority qp = QuoteService.getInstance().getSingleQuotePriority(q.getQuote1Id());
    if (qp == null) {
      qp = new QuotePriority();
      qp.setID_Quote1(q.getQuote1Id());
      qp.setNumber(q.getNumber());
      qp.setPriority(QuoteService.getInstance().getLastQuotePriority() + 1);
    }
    try {
      if (normal.equalsIgnoreCase("on")) {
        qp.setUrgency(0);
      } else if (someUrgent.equalsIgnoreCase("on")) {
        qp.setUrgency(1);
      } else if (urgent.equalsIgnoreCase("on")) {
        qp.setUrgency(2);
      } else {
        qp.setUrgency(3);
      }
    } catch (Exception e) {
    }
    try {
      if (inProgress.equalsIgnoreCase("on")) {
        qp.setPriority(QuoteService.getInstance().getLastQuotePriority() + 1);
        qp.setStatus(0);
      } else if (done1.equalsIgnoreCase("on")) {
        qp.setStatus(1);
        qp.setPriority(0);
      } else if (cross.equalsIgnoreCase("on")) {
        qp.setPriority(QuoteService.getInstance().getLastQuotePriority() + 1);
        qp.setStatus(2);

        //QuoteService.getInstance().updateQuotePriorityList();
      } else {
        qp.setStatus(3);
      }
    } catch (Exception e) {
    }
    QuoteService.getInstance().addQuotePriority(qp);

    INReport inReport = InteqaService.getInstance().getINReport(id);
    if (inReport == null) {
      inReport = new INReport();
      inReport.setProjectId(id);
    }
    try {
      Double preTransDtp = Double.parseDouble((String) upd.get("preTransDtp"));
      Double postTransDtp = Double.parseDouble((String) upd.get("postTransDtp"));
      Double preTransEng = Double.parseDouble((String) upd.get("preTransEng"));
      Double postTransEng = Double.parseDouble((String) upd.get("postTransEng"));
      inReport.setPost_dtp(postTransDtp);
      inReport.setPost_engg(postTransEng);
      inReport.setPre_dtp(preTransDtp);
      inReport.setPre_engg(preTransEng);
    } catch (Exception e) {
      //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa" + e.getLocalizedMessage());
    }
    inReport.setDtpRequirements(DtpRequirements);
    inReport.setDtpNotes(DTPNotes);
    inReport.setEnggNotes(EnggNotes.replaceAll("\\\\", "\\\\\\\\"));
    inReport.setEnggRequirements(EnggRequirements);
    try {
      if (LingReq1.equalsIgnoreCase("on")) {
        inReport.setLingReq1(true);
      } else {
        inReport.setLingReq1(false);
      }
    } catch (Exception e) {
      inReport.setLingReq1(false);
    }

    try {
      if (LingReq2.equalsIgnoreCase("on")) {
        inReport.setLingReq2(true);
      } else {
        inReport.setLingReq2(false);
      }


    } catch (Exception e) {
      inReport.setLingReq2(false);
    }
    try {
      if (LingReq3.equalsIgnoreCase("on")) {
        inReport.setLingReq3(true);
      } else {
        inReport.setLingReq3(false);
      }


    } catch (Exception e) {
      inReport.setLingReq3(false);
    }
    try {

      if (LingReq4.equalsIgnoreCase("on")) {
        inReport.setLingReq4(true);
      } else {
        inReport.setLingReq4(false);
      }

    } catch (Exception e) {
      inReport.setLingReq4(false);
    }
    try {
      if (LingReq5.equalsIgnoreCase("on")) {
        inReport.setLingReq5(true);
      } else {
        inReport.setLingReq5(false);
      }


    } catch (Exception e) {
      inReport.setLingReq5(false);
    }
    try {
      if (LingReq6.equalsIgnoreCase("on")) {
        inReport.setLingReq6(true);
      } else {
        inReport.setLingReq6(false);
      }

    } catch (Exception e) {
      inReport.setLingReq6(false);
    }

    inReport.setLingReqText5(LingReqText5);
    inReport.setLingReqText6(LingReqText6);
    inReport.setOtherInfo(OtherInfo);


    try {
      if (verifiedDate.length() > 0) { //if present
        inReport.setVerifiedDate(DateService.getInstance().convertDate(verifiedDate).getTime());
      }else{
           inReport.setVerifiedDate(null);
      }
    } catch (Exception e) {
      //System.out.println("Date Errooooorr " + e.getMessage());
    }

    inReport.setVerifiedBy(verifiedBy);
    inReport.setVerifiedText(verifiedText);
    if (verified.equalsIgnoreCase("on")) {
      inReport.setVerified(true);
    } else {
      inReport.setVerified(false);
    }

    try {
      if (quoteSentDate.length() > 0) { //if present
        inReport.setQuoteSentDate(DateService.getInstance().convertDate(quoteSentDate).getTime());
      }else{
           inReport.setQuoteSentDate(null);
      }
    } catch (Exception e) {
      //System.out.println("Date Errooooorr " + e.getMessage());
    }



//        try {
//            if (normal.equalsIgnoreCase("on")) {
//                inReport.setNormal(true);
//            } else {
//                inReport.setNormal(false);
//            }
//
//        } catch (Exception e) {
//            inReport.setNormal(false);
//        }
//        try {
//            if (cross.equalsIgnoreCase("on")) {
//                inReport.setCross(true);
//            } else {
//                inReport.setCross(false);
//            }
//
//        } catch (Exception e) {
//            inReport.setCross(false);
//        }
//        try {
//            if (urgent.equalsIgnoreCase("on")) {
//                inReport.setSomeUrgent(true);
//            } else {
//                inReport.setSomeUrgent(false);
//            }
//
//        } catch (Exception e) {
//            inReport.setSomeUrgent(false);
//        }
//        try {
//            if (done1.equalsIgnoreCase("on")) {
//                inReport.setDone1(true);
//            } else {
//                inReport.setDone1(false);
//            }
//
//        } catch (Exception e) {
//            inReport.setDone1(false);
//        }
//        try {
//            if (inProgress.equalsIgnoreCase("on")) {
//                inReport.setInProgress(true);
//            } else {
//                inReport.setInProgress(false);
//            }
//
//        } catch (Exception e) {
//            inReport.setInProgress(false);
//        }
//        try {
//            if (someUrgent.equalsIgnoreCase("on")) {
//                inReport.setSomeUrgent(true);
//            } else {
//                inReport.setSomeUrgent(false);
//            }
//
//        } catch (Exception e) {
//            inReport.setSomeUrgent(false);
//        }


    InteqaService.getInstance().updateInReport(inReport);
    //get project to edit


    // Forward control to the specified success URI
    return (mapping.findForward("Success"));
  }
}
