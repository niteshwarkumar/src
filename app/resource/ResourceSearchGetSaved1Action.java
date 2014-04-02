//ResourceSearchGetSaved1Action.java places the saved
//criteria into the form for display and search

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
import java.util.*;
import app.user.*;
import app.security.*;
import app.standardCode.StandardCode;
import org.apache.struts.validator.*;


public final class ResourceSearchGetSaved1Action extends Action {


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
        
        //get savedSearch to load
        String savedSearchId = request.getParameter("savedSearchId");
        SavedSearch ss = UserService.getInstance().getSingleSavedSearch(Integer.valueOf(savedSearchId));
        
        //load resource info for searching
        DynaValidatorForm rs = (DynaValidatorForm) form;    
            
        //reset search criteria values in the form
        rs.set("resourceSearchFirstName", ss.getFirstName());
        rs.set("resourceSearchLastName", ss.getLastName());
        rs.set("resourceSearchSingleCompanyName", ss.getSingleCompanyName());
        rs.set("resourceSearchContactFirstName", ss.getContactFirstName());
        rs.set("resourceSearchContactLastName", ss.getContactLastName());
        rs.set("resourceSearchAgency", ss.getAgency());
        rs.set("resourceSearchOldId", ss.getOldId());
        rs.set("clientName", StandardCode.getInstance().noNull(ss.getClientName()));
        rs.set("resourceSearchSourceId", ss.getSourceId());
        rs.set("resourceSearchTargetId", ss.getTargetId());
        
        rs.set("resourceSearchStatus", ss.getStatus());
        rs.set("resourceSearchIncludeDoNot", ss.getIncludeDoNot());
        
        rs.set("resourceSearchTranslator", ss.getTranslator());
        rs.set("resourceSearchEditor", ss.getEditor());
        rs.set("resourceSearchProofreader", ss.getProofreader());
        rs.set("resourceSearchDtp", ss.getDtp());
        rs.set("resourceSearchIcr", ss.getIcr());
        rs.set("resourceSearchDtpSourceId", ss.getDtpSourceId());
        rs.set("resourceSearchDtpTargetId", ss.getDtpTargetId());

        rs.set("resourceSearchOther", ss.getOther());
        rs.set("resourceSearchConsultant", ss.getConsultant());
        rs.set("resourceSearchPartner", ss.getPartner());
        rs.set("resourceSearchEngineering", ss.getEngineering());
        rs.set("resourceSearchBusinesssuport", ss.getBusinesssuport());
        rs.set("resourceSearchFqa", ss.getFqa());
        
        rs.set("resourceSearchTRate", ss.getTrate());
        rs.set("resourceSearchERate", ss.getErate());
        rs.set("resourceSearchTERate", ss.getTerate());
        rs.set("resourceSearchPRate", ss.getPrate());
        rs.set("resourceSearchDtpRate", ss.getDtpRate());
        rs.set("resourceSearchRateOldDb", ss.getRateOldDb());       
        
//        String[] savedSpecifics = ss.getSpecific().split(",");
//        String[] specifics = new String[savedSpecifics.length];
//        for(int i = 0; i < savedSpecifics.length; i++) {
//            specifics[i] = savedSpecifics[i];
//        }
//        rs.set("resourceSearchSpecific", specifics);
//        if(specifics.length == 0) {
//            rs.set("resourceSearchSpecific", new String[0]);
//        }

//        String[] savedGenerals = ss.getGeneral().split(",");
//        String[] generals = new String[savedGenerals.length];
//        for(int i = 0; i < savedGenerals.length; i++) {
//            generals[i] = savedGenerals[i];
//        }
//        rs.set("resourceSearchGeneral", generals);
//        if(generals.length == 0) {
//            rs.set("resourceSearchGeneral", new String[0]);
//        }

        List rscs = ResourceService.getInstance().getRateScoreCategoryList();
        String[] savedScoresLin = ss.getScoresLin().split(",");
        String[] scoresLin = new String[rscs.size()];
        for(int i = 0; i < rscs.size(); i++) { //init all of array
            scoresLin[i] = new String("");
        }
        for(int i = 0; i < savedScoresLin.length; i++) { //only place what was saved
            scoresLin[i] = savedScoresLin[i];
        }
        rs.set("resourceSearchScoresLin", scoresLin);

        rs.set("resourceSearchScoreOldDb", ss.getScoreOldDb());

        rs.set("resourceSearchProjectScoreGreater", ss.getProjectScoreGreater());

        rs.set("resourceSearchUsesTrados", ss.getUsesTrados());
        rs.set("resourceSearchUsesSdlx", ss.getUsesSdlx());
        rs.set("resourceSearchUsesTransit", ss.getUsesTransit());
        rs.set("resourceSearchUsesDejavu", ss.getUsesDejavu());
        rs.set("resourceSearchUsesCatalyst", ss.getUsesCatalyst());
        rs.set("resourceSearchUsesOtherTool1", ss.getUsesOtherTool1());
        rs.set("resourceSearchUsesOtherTool2", ss.getUsesOtherTool2());

        rs.set("resourceSearchCity", ss.getCity());
        rs.set("resourceSearchCountry", ss.getCountry());

        rs.set("resourceSearchResume", ss.getResume());
//        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
