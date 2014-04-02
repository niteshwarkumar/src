//ResourceSearchSave1Action.java gets the search
//criteria and saves this search to the db
//binding it to the user

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
import app.user.*;
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;


public final class ResourceSearchSave1Action extends Action {


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
            
        //values for search save
        String description = (String) rs.get("description");
        String ClientName = (String) rs.get("clientName");
        String FirstName = (String) rs.get("resourceSearchFirstName");
        String LastName = (String) rs.get("resourceSearchLastName");
        String singleCompanyName = (String) rs.get("resourceSearchSingleCompanyName");
        String ContactFirstName = (String) rs.get("resourceSearchContactFirstName");
        String ContactLastName = (String) rs.get("resourceSearchContactLastName");
        String Agency = (String) rs.get("resourceSearchAgency");
        String OldId = (String) rs.get("resourceSearchOldId");
        
        String SourceId = (String) rs.get("resourceSearchSourceId");
        String TargetId = (String) rs.get("resourceSearchTargetId");
        
    String Status = (String) rs.get("resourceSearchStatus");
        String IncludeDoNot = (String) rs.get("resourceSearchIncludeDoNot");
        if(IncludeDoNot == null) {    
            rs.set("resourceSearchIncludeDoNot", "off");
        }
            
        String Translator = (String) rs.get("resourceSearchTranslator");
        if(Translator == null) {    
            rs.set("resourceSearchTranslator", "off");
        }
        String Editor = (String) rs.get("resourceSearchEditor");
        if(Editor == null) {    
            rs.set("resourceSearchEditor", "off");
        }
        String Proofreader = (String) rs.get("resourceSearchProofreader");
        if(Proofreader == null) {    
            rs.set("resourceSearchProofreader", "off");
        }
        
        String other = (String) rs.get("resourceSearchOther");
        String consultant = (String) rs.get("resourceSearchConsultant");
        String partner = (String) rs.get("resourceSearchPartner");
        String engineering = (String) rs.get("resourceSearchEngineering");
        String businesssuport = (String) rs.get("resourceSearchBusinesssuport");
        String fqa = (String) rs.get("resourceSearchFqa");
        
        if(other == null) {    
            rs.set("resourceSearchOther", "off");
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
        
        
        String Dtp = (String) rs.get("resourceSearchDtp");
        if(Dtp == null) {    
            rs.set("resourceSearchDtp", "off");
        }
        String Icr = (String) rs.get("resourceSearchIcr");
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
        
        String RateOldDb = (String) rs.get("resourceSearchRateOldDb");       
        if(RateOldDb == null) {    
            rs.set("resourceSearchRateOldDb", "off");
        }
        
        String Specific[] = (String[]) rs.get("resourceSearchSpecific");     
        String General[] = (String[]) rs.get("resourceSearchGeneral");  
        
        String[] ScoresLin = (String[]) rs.get("resourceSearchScoresLin");
        String ScoreOldDb = (String) rs.get("resourceSearchScoreOldDb");   
        if(ScoreOldDb == null) {    
            rs.set("resourceSearchScoreOldDb", "off");
        }
        
        String ProjectScoreGreater = (String) rs.get("resourceSearchProjectScoreGreater");   
        
        String UsesTrados = (String) rs.get("resourceSearchUsesTrados");
        if(UsesTrados == null) {    
            rs.set("resourceSearchUsesTrados", "off");
        }
        String UsesSdlx = (String) rs.get("resourceSearchUsesSdlx");
        if(UsesSdlx == null) {    
            rs.set("resourceSearchUsesSdlx", "off");
        }
        String UsesTransit = (String) rs.get("resourceSearchUsesTransit");  
        if(UsesTransit == null) {    
            rs.set("resourceSearchUsesTransit", "off");
        }
        String UsesDejavu = (String) rs.get("resourceSearchUsesDejavu");
        if(UsesDejavu == null) {    
            rs.set("resourceSearchUsesDejavu", "off");
        }
        String UsesCatalyst = (String) rs.get("resourceSearchUsesCatalyst");
        if(UsesCatalyst == null) {    
            rs.set("resourceSearchUsesCatalyst", "off");
        }
        String UsesOtherTool1 = (String) rs.get("resourceSearchUsesOtherTool1");
        String UsesOtherTool2 = (String) rs.get("resourceSearchUsesOtherTool2");
        
        String City = (String) rs.get("resourceSearchCity");
        String Country = (String) rs.get("resourceSearchCountry");
        
        String Resume = (String) rs.get("resourceSearchResume");
        
        //save search to db
        //get this user
        User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));

        SavedSearch ss = new SavedSearch();
        ss.setClientName(ClientName);
        ss.setFirstName(FirstName);
        ss.setLastName(LastName);
        ss.setAgency(Agency);
        ss.setContactFirstName(ContactFirstName);
        ss.setContactLastName(ContactLastName);
        ss.setOldId(OldId);
        ss.setSourceId(SourceId);
        ss.setTargetId(TargetId);
        ss.setStatus(Status);
        ss.setIncludeDoNot(IncludeDoNot);
        ss.setTranslator(Translator);
        ss.setEditor(Editor);
        ss.setProofreader(Proofreader);
        ss.setDtp(Dtp);
        ss.setIcr(Icr);
        ss.setTrate(TRate);
        ss.setErate(ERate);
        ss.setTerate(TERate);
        ss.setPrate(PRate);
        ss.setDtpRate(DtpRate);
        ss.setDtpSourceId(DtpSourceId);
        ss.setDtpTargetId(DtpTargetId);
        ss.setRateOldDb(RateOldDb);
        ss.setTranslator(Translator);
        ss.setSingleCompanyName(singleCompanyName);
//          if(Specific.length >= 1) {
//            StringBuffer specifics = new StringBuffer("");
//            for(int i = 0; i < Specific.length; i++) {
//                specifics.append(Specific[i] + ",");
//            }
//            ss.setSpecific(specifics.toString());
//            System.out.println("Specificssssssssssss"+specifics.toString());
//        }
//        else {
//            ss.setSpecific("0,");
//        }

        if(General.length >= 1) {
            StringBuffer generals = new StringBuffer("");
            for(int j = 0; j < General.length; j++) {
                generals.append(General[j] + ",");
            }
            ss.setGeneral(generals.toString());
        }
        else {
            ss.setGeneral("0,");
        }

        StringBuffer scoresLin = new StringBuffer("");
        for(int k = 0; k < ScoresLin.length; k++) {
            scoresLin.append(ScoresLin[k] + ",");
        }
      ss.setScoresLin(scoresLin.toString());
//
        ss.setScoreOldDb(ScoreOldDb);
        ss.setProjectScoreGreater(ProjectScoreGreater);
        ss.setUsesTrados(UsesTrados); 
        ss.setUsesDejavu(UsesDejavu);
        ss.setUsesSdlx(UsesSdlx);
        ss.setUsesCatalyst(UsesCatalyst);
        ss.setUsesTransit(UsesTransit);
        ss.setUsesOtherTool1(UsesOtherTool1);
        ss.setUsesOtherTool2(UsesOtherTool2);
        ss.setCity(City);
        ss.setCountry(Country);
        ss.setResume(Resume);
        ss.setOther(other);
        ss.setConsultant(consultant);
        ss.setPartner(partner);
        ss.setEngineering(engineering);
        ss.setBusinesssuport(businesssuport);
        ss.setBusinesssuport(fqa);
        
        
        
        
        //set search description
        ss.setDescription(description);
        
        //get total record count
        String resourceRecordTotal = (String) request.getAttribute("resourceRecordTotal");
        if(resourceRecordTotal == null) {
            resourceRecordTotal = (String) request.getSession(false).getAttribute("resourceRecordTotal");
        }
        if(resourceRecordTotal == null) {
            resourceRecordTotal = StandardCode.getInstance().getCookie("resourceRecordTotal", request.getCookies());
        }
        ss.setResultTotal(resourceRecordTotal);
        //add savedSearch to db building one-to-many relationship between user and savedSearch
        UserService.getInstance().addSavedSearch(u, ss);
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
