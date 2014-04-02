//ResourceSearch2Action.java collects the search criteria from the form
//and performs the search on team for binding to a project
package app.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.*;
import java.util.*;
import app.project.*;
import app.security.*;

public final class ResourceSearch2Action extends Action {

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


//load resource info for searching
        DynaValidatorForm rs = (DynaValidatorForm) form;

//values for search
        String FirstName = (String) rs.get("resourceSearchFirstName");
        String LastName = (String) rs.get("resourceSearchLastName");
        String SingleCompanyName = (String) rs.get("resourceSearchSingleCompanyName");
        String ContactFirstName = (String) rs.get("resourceSearchContactFirstName");
        String ContactLastName = (String) rs.get("resourceSearchContactLastName");
        String Agency = (String) rs.get("resourceSearchAgency");
        String OldId = (String) rs.get("resourceSearchOldId");

        String SourceId = (String) rs.get("resourceSearchSourceId");
        String TargetId = (String) rs.get("resourceSearchTargetId");
        String source = ProjectService.getInstance().getLanguageString(Integer.valueOf(SourceId));
        String target = ProjectService.getInstance().getLanguageString(Integer.valueOf(TargetId));

        String Status = (String) rs.get("resourceSearchStatus");
        String IncludeDoNot = request.getParameter("resourceSearchIncludeDoNot");
        String RiskRating = "";
        try {
            RiskRating = (String) rs.get("resourceSearchRiskRating");
        } catch (Exception e) {
        }

        String other = request.getParameter("resourceSearchOther");
        String tne = request.getParameter("resourceSearchTne");
        String consultant = request.getParameter("resourceSearchConsultant");
        String partner = request.getParameter("resourceSearchPartner");
        String engineering = request.getParameter("resourceSearchEngineering");
        String businesssuport = request.getParameter("resourceSearchBusinesssuport");
        String fqa = request.getParameter("resourceSearchFqa");


        String informationTechnology = request.getParameter("resourceSearchInformationTechnology");
        String humanResource = request.getParameter("resourceSearchHumanResource");
        String office = request.getParameter("resourceSearchOffice");
        String sales = request.getParameter("resourceSearchSales");
        String accounting = request.getParameter("resourceSearchAccounting");
        String bsdOther = request.getParameter("resourceSearchBsdOther");
        String prodOther = request.getParameter("resourceSearchProdOther");

        if (informationTechnology == null) {
            rs.set("resourceSearchInformationTechnology", "off");
        }
        if (humanResource == null) {
            rs.set("resourceSearchHumanResource", "off");
        }
        if (office == null) {
            rs.set("resourceSearchOffice", "off");
        }
        if (sales == null) {
            rs.set("resourceSearchSales", "off");
        }
        if (accounting == null) {
            rs.set("resourceSearchAccounting", "off");
        }
        if (bsdOther == null) {
            rs.set("resourceSearchBsdOther", "off");
        }
        if (prodOther == null) {
            rs.set("resourceSearchProdOther", "off");
        }


        if (other == null) {
            rs.set("resourceSearchOther", "off");
        }
        if (tne == null) {
            rs.set("resourceSearchOther", "off");
        }

        if (consultant == null) {
            rs.set("resourceSearchConsultant", "off");
        }

        if (partner == null) {
            rs.set("resourceSearchPartner", "off");
        }

        if (engineering == null) {
            rs.set("resourceSearchEngineering", "off");
        }

        if (businesssuport == null) {
            rs.set("resourceSearchBusinesssuport", "off");
        }

        if (fqa == null) {
            rs.set("resourceSearchFqa", "off");
        }

        if (IncludeDoNot == null) {
            rs.set("resourceSearchIncludeDoNot", "off");
        }

        String Translator = request.getParameter("resourceSearchTranslator");
        if (Translator == null) {
            rs.set("resourceSearchTranslator", "off");
        }
        String Editor = request.getParameter("resourceSearchEditor");
        if (Editor == null) {
            rs.set("resourceSearchEditor", "off");
        }
        String Proofreader = request.getParameter("resourceSearchProofreader");
        if (Proofreader == null) {
            rs.set("resourceSearchProofreader", "off");
        }
        String Dtp = request.getParameter("resourceSearchDtp");
        if (Dtp == null) {
            rs.set("resourceSearchDtp", "off");
        }
        String Icr = request.getParameter("resourceSearchIcr");
        if (Icr == null) {
            rs.set("resourceSearchIcr", "off");
        }
        String Evaluator = request.getParameter("resourceSearchEvaluator");
        if (Evaluator == null) {
            rs.set("resourceSearchEvaluator", "off");
        }

        String TRate = (String) rs.get("resourceSearchTRate");
        String ERate = (String) rs.get("resourceSearchERate");
        String TERate = (String) rs.get("resourceSearchTERate");
        String PRate = (String) rs.get("resourceSearchPRate");
        String DtpRate = (String) rs.get("resourceSearchDtpRate");
        String DtpSourceId = (String) rs.get("resourceSearchDtpSourceId");
        String DtpTargetId = (String) rs.get("resourceSearchDtpTargetId");
        String dtpSource = ProjectService.getInstance().getLanguageString(Integer.valueOf(DtpSourceId));
        String dtpTarget = ProjectService.getInstance().getLanguageString(Integer.valueOf(DtpTargetId));

        String RateOldDb = request.getParameter("resourceSearchRateOldDb");
        if (RateOldDb == null) {
            rs.set("resourceSearchRateOldDb", "off");
        }

        String Specific[] = (String[]) rs.get("resourceSearchSpecific");
        String General[] = (String[]) rs.get("resourceSearchGeneral");

        String[] ScoresLin = (String[]) rs.get("resourceSearchScoresLin");
        String ScoreOldDb = request.getParameter("resourceSearchScoreOldDb");
        if (ScoreOldDb == null) {
            rs.set("resourceSearchScoreOldDb", "off");
        }

        String ProjectScoreGreater = (String) rs.get("resourceSearchProjectScoreGreater");

        String UsesTrados = request.getParameter("resourceSearchUsesTrados");
        if (UsesTrados == null) {
            rs.set("resourceSearchUsesTrados", "off");
        }
        String UsesSdlx = request.getParameter("resourceSearchUsesSdlx");
        if (UsesSdlx == null) {
            rs.set("resourceSearchUsesSdlx", "off");
        }
        String UsesTransit = request.getParameter("resourceSearchUsesTransit");
        if (UsesTransit == null) {
            rs.set("resourceSearchUsesTransit", "off");
        }
        String UsesDejavu = request.getParameter("resourceSearchUsesDejavu");
        if (UsesDejavu == null) {
            rs.set("resourceSearchUsesDejavu", "off");
        }
        String UsesCatalyst = request.getParameter("resourceSearchUsesCatalyst");
        if (UsesCatalyst == null) {
            rs.set("resourceSearchUsesCatalyst", "off");
        }
        String UsesOtherTool1 = (String) rs.get("resourceSearchUsesOtherTool1");
        String UsesOtherTool2 = (String) rs.get("resourceSearchUsesOtherTool2");

        String City = (String) rs.get("resourceSearchCity");
        String Country = (String) rs.get("resourceSearchCountry");

        String Resume = (String) rs.get("resourceSearchResume");
        String clientName = request.getParameter("clientName");

        String resourceSearchExpert = request.getParameter("resourceSearchExpert");
        String resourceSearchInterpreting = request.getParameter("resourceSearchInterpreting");
        String resourceSearchQuality = request.getParameter("resourceSearchQuality");

        if (resourceSearchExpert == null) {
            rs.set("resourceSearchExpert", "off");
        }

        if (resourceSearchInterpreting == null) {
            rs.set("resourceSearchInterpreting", "off");
        }
//String RiskRating = (String) rs.get("resourceSearchRiskRating");
        if (resourceSearchQuality == null) {
            rs.set("resourceSearchQuality", "off");
        }
//perform search and store results in a List
//perform main search
        List results = ResourceService.getInstance().getResourceSearch(clientName, FirstName, LastName, SingleCompanyName, ContactFirstName, ContactLastName, Agency, OldId, SourceId, TargetId, source, target, Status, IncludeDoNot, Translator, Editor, Proofreader, Evaluator, Dtp, Icr, TRate, ERate, TERate, PRate, DtpRate, dtpSource, dtpTarget, RateOldDb, Specific, General, ScoresLin, ResourceService.getInstance().getRateScoreCategoryList(), ScoreOldDb, ProjectScoreGreater, UsesTrados, UsesSdlx, UsesDejavu, UsesCatalyst, UsesOtherTool1, UsesOtherTool2, UsesTransit, City, Country, Resume, other, tne, consultant, partner, engineering, businesssuport, fqa, resourceSearchQuality, resourceSearchInterpreting, resourceSearchExpert, RiskRating, informationTechnology, humanResource, office, sales, accounting, bsdOther, prodOther);
        List results1 = null; //oldRate and oldScore from old db is required
        List results2 = null; //just oldRate from old db is required
        List results3 = null; //just oldScore from old db is required
        boolean bothOld = false; //search for oldRate and oldScore, or just one of the two
        if (RateOldDb != null && ScoreOldDb != null) { //search for both oldRate and oldScore
            results1 = ResourceService.getInstance().getResourceSearchOldRatesScores(FirstName, LastName, SingleCompanyName, ContactFirstName, ContactLastName, Agency, OldId, SourceId, TargetId, source, target, Status, IncludeDoNot, Translator, Editor, Proofreader, Dtp, Icr, TRate, ERate, TERate, PRate, DtpRate, RateOldDb, Specific, General, ScoresLin, ResourceService.getInstance().getRateScoreCategoryList(), ScoreOldDb, ProjectScoreGreater, UsesTrados, UsesSdlx, UsesDejavu, UsesCatalyst, UsesOtherTool1, UsesOtherTool2, UsesTransit, City, Country, Resume, other, consultant, partner, engineering, businesssuport, fqa);
            bothOld = true;
        }
        if (RateOldDb != null && !bothOld) { //search for oldRate only
            results2 = ResourceService.getInstance().getResourceSearchOldRates(FirstName, LastName, SingleCompanyName, ContactFirstName, ContactLastName, Agency, OldId, SourceId, TargetId, source, target, Status, IncludeDoNot, Translator, Editor, Proofreader, Dtp, Icr, TRate, ERate, TERate, PRate, DtpRate, RateOldDb, Specific, General, ScoresLin, ResourceService.getInstance().getRateScoreCategoryList(), ScoreOldDb, ProjectScoreGreater, UsesTrados, UsesSdlx, UsesDejavu, UsesCatalyst, UsesOtherTool1, UsesOtherTool2, UsesTransit, City, Country, Resume, other, consultant, partner, engineering, businesssuport, fqa);
        }
        if (ScoreOldDb != null && !bothOld) { //search for oldScore only
            results3 = ResourceService.getInstance().getResourceSearchOldScores(FirstName, LastName, SingleCompanyName, ContactFirstName, ContactLastName, Agency, OldId, SourceId, TargetId, source, target, Status, IncludeDoNot, Translator, Editor, Proofreader, Dtp, Icr, TRate, ERate, TERate, PRate, DtpRate, RateOldDb, Specific, General, ScoresLin, ResourceService.getInstance().getRateScoreCategoryList(), ScoreOldDb, ProjectScoreGreater, UsesTrados, UsesSdlx, UsesDejavu, UsesCatalyst, UsesOtherTool1, UsesOtherTool2, UsesTransit, City, Country, Resume, other, consultant, partner, engineering, businesssuport, fqa);
        }

//add the old db scores to the result set if present
        ArrayList finalResults = null;
        if (results != null) { //if main search has results
            finalResults = new ArrayList(results);
            if (finalResults != null) {
                if (results1 != null) { //load oldRate and oldScore
                    for (ListIterator outer = results1.listIterator(); outer.hasNext();) {
                        Resource r1 = (Resource) outer.next();
                        boolean add = true;
                        for (ListIterator inner = finalResults.listIterator(); inner.hasNext();) {
                            Resource r2 = (Resource) inner.next();
                            if (r1.getResourceId().equals(r2.getResourceId())) {
                                add = false;
                            }
                        }
                        if (add) {
                            finalResults.add(r1);
                        }
                    }
                }
                if (results2 != null) { //load oldRate only
                    for (ListIterator outer = results2.listIterator(); outer.hasNext();) {
                        Resource r1 = (Resource) outer.next();
                        boolean add = true;
                        for (ListIterator inner = finalResults.listIterator(); inner.hasNext();) {
                            Resource r2 = (Resource) inner.next();
                            if (r1.getResourceId().equals(r2.getResourceId())) {
                                add = false;
                            }
                        }
                        if (add) {
                            finalResults.add(r1);
                        }
                    }
                }
                if (results3 != null) { //load oldScore only
                    for (ListIterator outer = results3.listIterator(); outer.hasNext();) {
                        Resource r1 = (Resource) outer.next();
                        boolean add = true;
                        for (ListIterator inner = finalResults.listIterator(); inner.hasNext();) {
                            Resource r2 = (Resource) inner.next();
                            if (r1.getResourceId().equals(r2.getResourceId())) {
                                add = false;
                            }
                        }
                        if (add) {
                            finalResults.add(r1);
                        }
                    }
                }
            }
        } else { //if main search does not have results
            finalResults = new ArrayList();
            if (finalResults != null) {
                if (results1 != null) { //load oldRate and oldScore
                    for (ListIterator outer = results1.listIterator(); outer.hasNext();) {
                        Resource r1 = (Resource) outer.next();
                        boolean add = true;
                        for (ListIterator inner = finalResults.listIterator(); inner.hasNext();) {
                            Resource r2 = (Resource) inner.next();
                            if (r1.getResourceId().equals(r2.getResourceId())) {
                                add = false;
                            }
                        }
                        if (add) {
                            finalResults.add(r1);
                        }
                    }
                }
                if (results2 != null) { //load oldRate only
                    for (ListIterator outer = results2.listIterator(); outer.hasNext();) {
                        Resource r1 = (Resource) outer.next();
                        boolean add = true;
                        for (ListIterator inner = finalResults.listIterator(); inner.hasNext();) {
                            Resource r2 = (Resource) inner.next();
                            if (r1.getResourceId().equals(r2.getResourceId())) {
                                add = false;
                            }
                        }
                        if (add) {
                            finalResults.add(r1);
                        }
                    }
                }
                if (results3 != null) { //load oldScore only
                    for (ListIterator outer = results3.listIterator(); outer.hasNext();) {
                        Resource r1 = (Resource) outer.next();
                        boolean add = true;
                        for (ListIterator inner = finalResults.listIterator(); inner.hasNext();) {
                            Resource r2 = (Resource) inner.next();
                            if (r1.getResourceId().equals(r2.getResourceId())) {
                                add = false;
                            }
                        }
                        if (add) {
                            finalResults.add(r1);
                        }
                    }
                }
            }
        }


        if (finalResults != null) {
//sort the list by id
            Collections.sort(finalResults, CompareResource.getInstance());
        }

//place results in attribute for displaying in jsp
        request.setAttribute("results", finalResults);


// Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
