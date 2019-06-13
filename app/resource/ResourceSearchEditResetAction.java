//ResourceSearchEditResetAction.java resets the form
//so a search can begin without searching within results.
//That is, start with a blank form.
package app.resource;

import app.extjs.helpers.TeamHelper;
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

public final class ResourceSearchEditResetAction extends Action {

  // ----------------------------------------------------- Instance Variables
  /**
   * The
   * <code>Log</code> instance for this application.
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

    //load resource info for searching
    DynaValidatorForm rs = (DynaValidatorForm) form;

    //reset search criteria values in the form
    rs.set("clientName", "");
    rs.set("resourceSearchFirstName", "");
    rs.set("resourceSearchLastName", "");
    rs.set("resourceSearchSingleCompanyName", "");
    rs.set("resourceSearchContactFirstName", "");
    rs.set("resourceSearchContactLastName", "");
    rs.set("resourceSearchAgency", "");
    rs.set("resourceSearchOldId", "");

    rs.set("resourceSearchSourceId", "0");
    rs.set("resourceSearchTargetId", "0");

    rs.set("resourceSearchStatus", "0");
    rs.set("resourceSearchIncludeDoNot", "off");
    rs.set("resourceSearchisAgency", "off");

    rs.set("resourceSearchTranslator", "off");
    rs.set("resourceSearchEditor", "off");
    rs.set("resourceSearchProofreader", "off");
    rs.set("resourceSearchDtp", "off");
    rs.set("resourceSearchIcr", "off");
    rs.set("resourceSearchDtpSourceId", "0");
    rs.set("resourceSearchDtpTargetId", "0");

    rs.set("resourceSearchTne", "0");
    rs.set("resourceSearchPostEditing", "0");
    rs.set("resourceSearchEvaluator", "0");
    rs.set("resourceSearchQuality", "0");
    rs.set("resourceSearchExpert", "0");

    rs.set("resourceSearchOther", "off");
    rs.set("resourceSearchConsultant", "off");
    rs.set("resourceSearchPartner", "off");
    rs.set("resourceSearchEngineering", "off");
    rs.set("resourceSearchBusinesssuport", "off");
    rs.set("resourceSearchFqa", "off");

    rs.set("resourceSearchRiskRating", "");
    rs.set("resourceSearchTRate", "");
    rs.set("resourceSearchERate", "");
    rs.set("resourceSearchTERate", "");
    rs.set("resourceSearchPRate", "");
    rs.set("resourceSearchDtpRate", "");
    rs.set("resourceSearchRateOldDb", "off");

    rs.set("resourceSearchSpecific", new String[0]);
    rs.set("resourceSearchGeneral", new String[0]);

    List ratescorelanguages2 = ResourceService.getInstance().getRateScoreCategoryList();
    String[] ratescorelanguagesArray = new String[ratescorelanguages2.size()];

    int i = 0;
    for (ListIterator iter = ratescorelanguages2.listIterator(); iter.hasNext(); i++) {
      RateScoreCategory rsc = (RateScoreCategory) iter.next();
      ratescorelanguagesArray[i] = new String("");
    }

    //save values in form
    rs.set("resourceSearchScoresLin", ratescorelanguagesArray);

    rs.set("resourceSearchScoreOldDb", "off");

    rs.set("resourceSearchProjectScoreGreater", "null");

    rs.set("resourceSearchUsesTrados", "off");
    rs.set("resourceSearchUsesSdlx", "off");
    rs.set("resourceSearchUsesTransit", "off");
    rs.set("resourceSearchUsesDejavu", "off");
    rs.set("resourceSearchUsesCatalyst", "off");
    rs.set("resourceSearchUsesOtherTool1", "");
    rs.set("resourceSearchUsesOtherTool2", "");

    rs.set("resourceSearchCity", "");
    rs.set("resourceSearchCountry", "0");

    rs.set("resourceSearchResume", "");
    rs.set("resourceSearchNote", "");

    rs.set("resourceSearchSecondary", "");
    rs.set("resourceSearchSecondaryCount", "");
    rs.set("resourceSearchPrimaryCount", "");
    rs.set("resourceSearchPrimary", "");
    rs.set("resourceSearchLevel", "");
    rs.set("resourceSearchClientName", "");

    if (request.getParameter("appVen") != null) {
      response.addCookie(StandardCode.getInstance().setCookie("appVen", request.getParameter("appVen")));
      request.setAttribute("appVen", request.getParameter("appVen"));
    } else {
      response.addCookie(StandardCode.getInstance().setCookie("appVen", "N"));
      request.setAttribute("appVen", "N");
    }

    // Forward control to the specified success URI
    return (mapping.findForward("Success"));
  }
}
