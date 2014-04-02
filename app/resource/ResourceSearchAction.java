//ResourceSearchAction.java collects the search criteria from the form
//and performs the search on team

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
import org.apache.struts.validator.*;
import java.util.*;
import app.project.*;
import app.standardCode.*;
import app.security.*;
import java.util.ArrayList;


public final class ResourceSearchAction extends Action {


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
        if(!SecurityService.getInstance().checkForLogin(request.getSession(false))) { 
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)
	
        
        //load resource info for searching
        DynaValidatorForm rs = (DynaValidatorForm) form;     
             
        //values for search
        String FirstName = (String) rs.get("resourceSearchFirstName");
        String LastName = (String) rs.get("resourceSearchLastName");
        String SingleCompanyName=(String) rs.get("resourceSearchSingleCompanyName");
        String ContactFirstName = (String) rs.get("resourceSearchContactFirstName");
        String ContactLastName = (String) rs.get("resourceSearchContactLastName");
        String Agency = (String) rs.get("resourceSearchAgency");
        String OldId = (String) rs.get("resourceSearchOldId");
        
        String SourceId = (String) rs.get("resourceSearchSourceId");
        String TargetId = (String) rs.get("resourceSearchTargetId");
        String source = ProjectService.getInstance().getLanguageString(Integer.parseInt(SourceId));
        String target = ProjectService.getInstance().getLanguageString(Integer.parseInt(TargetId));
        
        String Status = (String) rs.get("resourceSearchStatus");
        String IncludeDoNot = request.getParameter("resourceSearchIncludeDoNot");
        if(IncludeDoNot == null) {    
            rs.set("resourceSearchIncludeDoNot", "off");
        }
            
        String Translator = request.getParameter("resourceSearchTranslator");
        if(Translator == null) {    
            rs.set("resourceSearchTranslator", "off");
        }
        String Editor = request.getParameter("resourceSearchEditor");
        if(Editor == null) {    
            rs.set("resourceSearchEditor", "off");
        }
        String Proofreader = request.getParameter("resourceSearchProofreader");
        if(Proofreader == null) {    
            rs.set("resourceSearchProofreader", "off");
        }
        String Evaluator = request.getParameter("resourceSearchEvaluator");
        if(Proofreader == null) {
            rs.set("resourceSearchEvaluator", "off");
        }
        
        String other = request.getParameter("resourceSearchOther");
         String tne = request.getParameter("resourceSearchTne");
        String consultant = request.getParameter("resourceSearchConsultant");
        String partner = request.getParameter("resourceSearchPartner");
        String engineering = request.getParameter("resourceSearchEngineering");
        String businesssuport = request.getParameter("resourceSearchBusinesssuport");
        String fqa = request.getParameter("resourceSearchFqa");
        
        String resourceSearchExpert = request.getParameter("resourceSearchExpert");
        String resourceSearchInterpreting = request.getParameter("resourceSearchInterpreting");
        String resourceSearchQuality = request.getParameter("resourceSearchQuality");



            String informationTechnology= request.getParameter("resourceSearchInformationTechnology");
            String humanResource= request.getParameter("resourceSearchHumanResource");
            String office= request.getParameter("resourceSearchOffice");
            String sales= request.getParameter("resourceSearchSales");
            String accounting= request.getParameter("resourceSearchAccounting");
            String bsdOther= request.getParameter("resourceSearchBsdOther");
            String prodOther= request.getParameter("resourceSearchProdOther");

        if(informationTechnology == null) {
            rs.set("resourceSearchInformationTechnology", "off");
        }
            if(humanResource == null) {
            rs.set("resourceSearchHumanResource", "off");
        }
            if(office == null) {
            rs.set("resourceSearchOffice", "off");
        }
            if(sales == null) {
            rs.set("resourceSearchSales", "off");
        }
            if(accounting == null) {
            rs.set("resourceSearchAccounting", "off");
        }
            if(bsdOther == null) {
            rs.set("resourceSearchBsdOther", "off");
        }
            if(prodOther == null) {
            rs.set("resourceSearchProdOther", "off");
        }
        
        if(resourceSearchExpert == null) {    
            rs.set("resourceSearchExpert", "off");
        }
        
        if(resourceSearchInterpreting == null) {    
            rs.set("resourceSearchInterpreting", "off");
        }
        
        if(resourceSearchQuality == null) {    
            rs.set("resourceSearchQuality", "off");
        }
        
        if(other == null) {    
            rs.set("resourceSearchOther", "off");
        }

        if(tne == null) {
            rs.set("resourceSearchTne", "off");
        }
        
        if(consultant == null) {    
            rs.set("resourceSearchConsultant", "off");
        }
        
        if(partner == null) {    
            rs.set("resourceSearchPartner", "off");
        }
        
        if(engineering == null) {    
            rs.set("resourceSearchEngineering", "off");
        }
        
        if(businesssuport == null) {    
            rs.set("resourceSearchBusinesssuport", "off");
        }
         if(fqa == null) {
            rs.set("resourceSearchFqa", "off");
        }
        
        
        
        
        String Dtp = request.getParameter("resourceSearchDtp");
        if(Dtp == null) {    
            rs.set("resourceSearchDtp", "off");
        }
        String Icr = request.getParameter("resourceSearchIcr");
        if(Icr == null) {    
            rs.set("resourceSearchIcr", "off");
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
        //String riskrating = (String) rs.get("resourceSearchRiskRating");
        String RateOldDb = request.getParameter("resourceSearchRateOldDb");       
        if(RateOldDb == null) {    
            rs.set("resourceSearchRateOldDb", "off");
        }
        
        String Specific[] = (String[]) rs.get("resourceSearchSpecific");     
        String General[] = (String[]) rs.get("resourceSearchGeneral");  
        
        String[] ScoresLin = (String[]) rs.get("resourceSearchScoresLin");
        for(int i=0;i<ScoresLin.length;i++){

            System.out.println(" 1                                                                                        "+ScoresLin[i]);

        }

        String ScoreOldDb = request.getParameter("resourceSearchScoreOldDb");   
        if(ScoreOldDb == null) {    
            rs.set("resourceSearchScoreOldDb", "off");
        }
        
        String ProjectScoreGreater = (String) rs.get("resourceSearchProjectScoreGreater");   
        
        String UsesTrados = request.getParameter("resourceSearchUsesTrados");
        if(UsesTrados == null) {    
            rs.set("resourceSearchUsesTrados", "off");
        }
        String UsesSdlx = request.getParameter("resourceSearchUsesSdlx");
        if(UsesSdlx == null) {    
            rs.set("resourceSearchUsesSdlx", "off");
        }
        String UsesTransit = request.getParameter("resourceSearchUsesTransit");  
        if(UsesTransit == null) {    
            rs.set("resourceSearchUsesTransit", "off");
        }
        String UsesDejavu = request.getParameter("resourceSearchUsesDejavu");
        if(UsesDejavu == null) {    
            rs.set("resourceSearchUsesDejavu", "off");
        }
        String UsesCatalyst = request.getParameter("resourceSearchUsesCatalyst");
        if(UsesCatalyst == null) {    
            rs.set("resourceSearchUsesCatalyst", "off");
        }
        String UsesOtherTool1 = (String) rs.get("resourceSearchUsesOtherTool1");
        String UsesOtherTool2 = (String) rs.get("resourceSearchUsesOtherTool2");
        
        String City = (String) rs.get("resourceSearchCity");
        String Country = (String) rs.get("resourceSearchCountry");
        
        String Resume = (String) rs.get("resourceSearchResume");

        String RiskRating = (String) rs.get("resourceSearchRiskRating");
        String clientName = request.getParameter("clientName");
        //perform search and store results in a List
        //perform main search 
        List results = ResourceService.getInstance().getResourceSearch(clientName, FirstName,  LastName, SingleCompanyName, ContactFirstName, ContactLastName,  Agency,  OldId,  SourceId,  TargetId, source, target, Status,  IncludeDoNot,  Translator,  Editor,  Proofreader, Evaluator , Dtp, Icr,  TRate,  ERate,  TERate,  PRate,  DtpRate, dtpSource, dtpTarget,  RateOldDb,  Specific,  General,  ScoresLin, ResourceService.getInstance().getRateScoreCategoryList(),  ScoreOldDb,  ProjectScoreGreater,  UsesTrados,  UsesSdlx,   UsesDejavu,  UsesCatalyst,   UsesOtherTool1,  UsesOtherTool2,  UsesTransit,   City,   Country, Resume,  other,tne,   consultant,  partner,  engineering, businesssuport, fqa, resourceSearchQuality, resourceSearchInterpreting,resourceSearchExpert,RiskRating,informationTechnology, humanResource ,office,sales ,accounting,bsdOther ,prodOther);
        List results1 = null; //oldRate and oldScore from old db is required
        List results2 = null; //just oldRate from old db is required
        List results3 = null; //just oldScore from old db is required
        boolean bothOld = false; //search for oldRate and oldScore, or just one of the two
        if(RateOldDb != null && ScoreOldDb != null) { //search for both oldRate and oldScore
            results1 = ResourceService.getInstance().getResourceSearchOldRatesScores(FirstName,  LastName, SingleCompanyName, ContactFirstName, ContactLastName,  Agency,  OldId,  SourceId,  TargetId, source, target, Status,  IncludeDoNot,  Translator,  Editor,  Proofreader,  Dtp, Icr,  TRate,  ERate,  TERate,  PRate,  DtpRate,  RateOldDb,  Specific,  General,  ScoresLin, ResourceService.getInstance().getRateScoreCategoryList(),  ScoreOldDb,  ProjectScoreGreater,  UsesTrados,  UsesSdlx,   UsesDejavu,  UsesCatalyst,   UsesOtherTool1,  UsesOtherTool2,  UsesTransit,   City,   Country, Resume,  other,  consultant,  partner,  engineering, businesssuport, fqa);
            bothOld = true;
        }
        if(RateOldDb != null && !bothOld) { //search for oldRate only
            results2 = ResourceService.getInstance().getResourceSearchOldRates(FirstName,  LastName, SingleCompanyName, ContactFirstName, ContactLastName,  Agency,  OldId,  SourceId,  TargetId, source, target, Status,  IncludeDoNot,  Translator,  Editor,  Proofreader,  Dtp, Icr,  TRate,  ERate,  TERate,  PRate,  DtpRate,  RateOldDb,  Specific,  General,  ScoresLin, ResourceService.getInstance().getRateScoreCategoryList(),  ScoreOldDb,  ProjectScoreGreater,  UsesTrados,  UsesSdlx,   UsesDejavu,  UsesCatalyst,   UsesOtherTool1,  UsesOtherTool2,  UsesTransit,   City,   Country, Resume, other,  consultant,  partner,  engineering, businesssuport, fqa);
        }
        if(ScoreOldDb != null && !bothOld) { //search for oldScore only
            results3 = ResourceService.getInstance().getResourceSearchOldScores(FirstName,  LastName, SingleCompanyName, ContactFirstName, ContactLastName,  Agency,  OldId,  SourceId,  TargetId, source, target, Status,  IncludeDoNot,  Translator,  Editor,  Proofreader,  Dtp, Icr,  TRate,  ERate,  TERate,  PRate,  DtpRate,  RateOldDb,  Specific,  General,  ScoresLin, ResourceService.getInstance().getRateScoreCategoryList(),  ScoreOldDb,  ProjectScoreGreater,  UsesTrados,  UsesSdlx,   UsesDejavu,  UsesCatalyst,   UsesOtherTool1,  UsesOtherTool2,  UsesTransit,   City,   Country, Resume, other,  consultant,  partner,  engineering, businesssuport, fqa);
        }
        
        //add the old db scores to the result set if present
        ArrayList finalResults = null;
        if(results != null) { //if main search has results
            finalResults = new ArrayList(results);
            if(finalResults != null) {
                if(results1 != null) { //load oldRate and oldScore
                    for(ListIterator outer = results1.listIterator(); outer.hasNext();) {
                        Resource r1 = (Resource) outer.next();
                        boolean add = true;
                        for(ListIterator inner = finalResults.listIterator(); inner.hasNext();) {
                            Resource r2 = (Resource) inner.next();
                            if(r1.getResourceId().equals(r2.getResourceId())) {
                                add = false;
                            }
                        }
                        if(add) {
                            finalResults.add(r1);
                        }
                    }
                }
                if(results2 != null) { //load oldRate only
                    for(ListIterator outer = results2.listIterator(); outer.hasNext();) {
                        Resource r1 = (Resource) outer.next();
                        boolean add = true;
                        for(ListIterator inner = finalResults.listIterator(); inner.hasNext();) {
                            Resource r2 = (Resource) inner.next();
                            if(r1.getResourceId().equals(r2.getResourceId())) {
                                add = false;
                            }
                        }
                        if(add) {
                            finalResults.add(r1);
                        }
                    }
                }
                if(results3 != null) { //load oldScore only
                    for(ListIterator outer = results3.listIterator(); outer.hasNext();) {
                        Resource r1 = (Resource) outer.next();
                        boolean add = true;
                        for(ListIterator inner = finalResults.listIterator(); inner.hasNext();) {
                            Resource r2 = (Resource) inner.next();
                            if(r1.getResourceId().equals(r2.getResourceId())) {
                                add = false;
                            }
                        }
                        if(add) {
                            finalResults.add(r1);
                        }
                    }
                }                
            }
        }
        else { //if main search does not have results
         finalResults = new ArrayList();
            if(finalResults != null) {
                if(results1 != null) { //load oldRate and oldScore
                    for(ListIterator outer = results1.listIterator(); outer.hasNext();) {
                        Resource r1 = (Resource) outer.next();
                        boolean add = true;
                        for(ListIterator inner = finalResults.listIterator(); inner.hasNext();) {
                            Resource r2 = (Resource) inner.next();
                            if(r1.getResourceId().equals(r2.getResourceId())) {
                                add = false;
                            }
                        }
                        if(add) {
                            finalResults.add(r1);
                        }
                    }
                }
                if(results2 != null) { //load oldRate only
                    for(ListIterator outer = results2.listIterator(); outer.hasNext();) {
                        Resource r1 = (Resource) outer.next();
                        boolean add = true;
                        for(ListIterator inner = finalResults.listIterator(); inner.hasNext();) {
                            Resource r2 = (Resource) inner.next();
                            if(r1.getResourceId().equals(r2.getResourceId())) {
                                add = false;
                            }
                        }
                        if(add) {
                            finalResults.add(r1);
                        }
                    }
                }
                if(results3 != null) { //load oldScore only
                    for(ListIterator outer = results3.listIterator(); outer.hasNext();) {
                        Resource r1 = (Resource) outer.next();
                        boolean add = true;
                        for(ListIterator inner = finalResults.listIterator(); inner.hasNext();) {
                            Resource r2 = (Resource) inner.next();
                            if(r1.getResourceId().equals(r2.getResourceId())) {
                                add = false;
                            }
                        } 
                        if(add) {
                            finalResults.add(r1);
                        }
                    }
                }                
            }
        }
        
        ArrayList appVendorResults=new ArrayList();
        try {
             if(finalResults!=null){
          if(request.getParameter("appVen")!=null && request.getParameter("appVen").equalsIgnoreCase("Y")){
               request.setAttribute("appVen", request.getParameter("appVen"));
            for(ListIterator appVend=finalResults.listIterator(); appVend.hasNext();){
                 Resource r2 = (Resource) appVend.next();
                 if(TeamHelper.getAssessmentISA(r2)>=21){
                 appVendorResults.add(r2);
               }
            }
          }else appVendorResults=finalResults;
        }
        } catch (Exception e) {
            appVendorResults=finalResults;
        }
       
         
        ArrayList finalFinalResults = new ArrayList();
        
        for(ListIterator outer = appVendorResults.listIterator(); outer.hasNext();) {
                        Resource r1 = (Resource) outer.next();
                        Vector tempResult = TeamHelper.getVendorCounts(r1.getResourceId().intValue());
                        
                        r1.setWordCount((String)tempResult.get(1));
                        r1.setProjectCount((String)tempResult.get(0));
                        
                        //System.out.println("word count="+r1.getWordCount());
                        //System.out.println("project count="+r1.getProjectCount());
                        
                        finalFinalResults.add(r1);
        }
        
          
        if(finalFinalResults != null) {
           //sort the list by last name/agency
            if(request.getParameter("sortByProjects")!=null){
                Collections.sort(finalFinalResults, CompareResourceByProjectCount.getInstance());
                Collections.reverse(finalFinalResults); 
            }else if(request.getParameter("sortByWords")!=null){
                Collections.sort(finalFinalResults, CompareResourceByWordCount.getInstance());
                Collections.reverse(finalFinalResults); 
            }else {
                Comparators myComp = new Comparators();
                myComp.multiplier = 1;
                myComp.compareClass = "TEAM";
                myComp.compareField = "ResourceName";
                Comparator comp = myComp.createComparator();
                Collections.sort(finalFinalResults, comp);
                
               // Collections.sort(finalFinalResults, CompareResource.getInstance());
                //Collections.sort(finalFinalResults,CompareResource.getInstance());
            }

        }

        
        //place results in attribute for displaying in jsp
        request.getSession(false).setAttribute("finalResults", finalFinalResults);
        
        String resourceRecordTotal; //total number of records
        //put search result ids into array for viewing from view menu
        if(finalFinalResults != null) { //if results present
            Integer[] resourceSearchResultIds = new Integer[finalFinalResults.size()];
            int i = 0;
            for(Iterator iter = finalFinalResults.iterator(); iter.hasNext(); i++) {
                Resource r = (Resource) iter.next();
                resourceSearchResultIds[i] = new Integer(0);
                resourceSearchResultIds[i] = r.getResourceId();
            }

            //set Ids into session for viewing
            request.getSession(false).setAttribute("resourceSearchResultIds", resourceSearchResultIds);
            resourceRecordTotal = String.valueOf(finalFinalResults.size());
        }
        else { //no results present
            resourceRecordTotal = "0";
        }
    
        //set total records into request, session and cookie
        request.setAttribute("resourceRecordTotal", resourceRecordTotal);
        request.getSession(false).setAttribute("resourceRecordTotal", resourceRecordTotal);
        response.addCookie(StandardCode.getInstance().setCookie("resourceRecordTotal", resourceRecordTotal));

        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
