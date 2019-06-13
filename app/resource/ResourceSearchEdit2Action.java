//ResourceSearchEdit2Action.java prepares the form
//for collecting search criteria (places form into session)
//this comes from project, so we are trying to add team
//members to tasks by clicking on a link from project

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
import app.resource.*;
import app.project.*;
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;


public final class ResourceSearchEdit2Action extends Action {


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
               
        //START prepare the ratescorelanguage Array with the number of categories from the db
        List rscs = ResourceService.getInstance().getRateScoreCategoryList();
        
        String[] fromFormRsc = (String[]) rs.get("resourceSearchScoresLin");
        boolean reset = true;
        for(int i = 0; i < fromFormRsc.length; i++) {
            if(!fromFormRsc[i].equals("-1")) { //reset flag set
                reset = false;
            }
        }
        if(reset) {
            String[] ratescorelanguagesArray = new String[rscs.size()];
            ListIterator iter = rscs.listIterator();
            for(int i = 0; i < ratescorelanguagesArray.length; i++) {
                RateScoreCategory rsc = (RateScoreCategory) iter.next(); 
                ratescorelanguagesArray[i] = new String("");


                rs.set("resourceSearchScoresLin", ratescorelanguagesArray);
            }
        }//END prepare the ratescorelanguage Array with the number of categories from the db
        
        
        //START from project, so load search criteria for the task
        //get task to create po
        String linId = null;
        String engId = null;
        String dtpId = null;
        String othId = null;
        
        linId = request.getParameter("linId");
        if(linId == null) {
            engId = request.getParameter("engId");
            if(engId == null) {
                dtpId = request.getParameter("dtpId");
                if(dtpId == null) {
                    othId = request.getParameter("othId");
                }
            }            
        }    
        
            if(!StandardCode.getInstance().noNull(linId).equalsIgnoreCase("")) {
            //get the lin task for entering search criteria (source and target)
            LinTask t = ProjectService.getInstance().getSingleLinTask(Integer.valueOf(linId));
            
            //reset search criteria values in the form and set the source and target language
            rs.set("resourceSearchFirstName", "");
            rs.set("resourceSearchLastName", "");
            rs.set("resourceSearchSingleCompanyName", "");
            rs.set("resourceSearchContactFirstName", "");
            rs.set("resourceSearchContactLastName", "");
            rs.set("resourceSearchAgency", "");
            rs.set("resourceSearchOldId", "");

            rs.set("resourceSearchSourceId", String.valueOf(ProjectService.getInstance().getLanguageId(t.getSourceLanguage())));
            rs.set("resourceSearchTargetId", String.valueOf(ProjectService.getInstance().getLanguageId(t.getTargetLanguage())));

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
              
            rs.set("resourceSearchOther", "off");
            rs.set("resourceSearchConsultant", "off");
            rs.set("resourceSearchPartner", "off");
            rs.set("resourceSearchEngineering", "off");
            rs.set("resourceSearchBusinesssuport", "off");

            rs.set("resourceSearchInformationTechnology", "off");
            rs.set("resourceSearchPostEditing","off");
            rs.set("resourceSearchHumanResource", "off");
            rs.set("resourceSearchOffice", "off");
            rs.set("resourceSearchSales", "off");
            rs.set("resourceSearchAccounting", "off");
            

            rs.set("resourceSearchFqa", "off");
        
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
                for(ListIterator iter = ratescorelanguages2.listIterator(); iter.hasNext(); i++) {
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
            
            //set the id into session for later use (binding team to project's task)
            request.getSession(false).setAttribute("projectViewTeamBindLinId", linId);
            request.getSession(false).setAttribute("projectViewTeamBindEngId", null);
            request.getSession(false).setAttribute("projectViewTeamBindDtpId", null);
            request.getSession(false).setAttribute("projectViewTeamBindOthId", null);
        }
        else if(!StandardCode.getInstance().noNull(engId).equalsIgnoreCase("")) {
            //get the eng task for entering search criteria (source and target)
            EngTask t = ProjectService.getInstance().getSingleEngTask(Integer.valueOf(engId));
            
            //reset search criteria values in the form and set the source and target language
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
            
            rs.set("resourceSearchOther", "off");
            rs.set("resourceSearchConsultant", "off");
            rs.set("resourceSearchPartner", "off");
            rs.set("resourceSearchEngineering", "off");
            rs.set("resourceSearchBusinesssuport", "off");        
            rs.set("resourceSearchFqa", "off");

             rs.set("resourceSearchInformationTechnology", "off");
             rs.set("resourceSearchPostEditing","off");
            rs.set("resourceSearchHumanResource", "off");
            rs.set("resourceSearchOffice", "off");
            rs.set("resourceSearchSales", "off");
            rs.set("resourceSearchAccounting", "off");

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
                for(ListIterator iter = ratescorelanguages2.listIterator(); iter.hasNext(); i++) {
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
            
            //set the id into session for later use (binding team to project's task)
            request.getSession(false).setAttribute("projectViewTeamBindLinId", null);
            request.getSession(false).setAttribute("projectViewTeamBindEngId", engId);
            request.getSession(false).setAttribute("projectViewTeamBindDtpId", null);
            request.getSession(false).setAttribute("projectViewTeamBindOthId", null);

        }
        else if(!StandardCode.getInstance().noNull(dtpId).equalsIgnoreCase("")) {
            //get the dtp task for entering search criteria (source and target)
            DtpTask t = ProjectService.getInstance().getSingleDtpTask(Integer.valueOf(dtpId));
            
            //reset search criteria values in the form and set the source and target language
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
            rs.set("resourceSearchDtp", "on");
            rs.set("resourceSearchIcr", "off");
            rs.set("resourceSearchDtpSourceId", "0");
            rs.set("resourceSearchDtpTargetId", "0");
            
            rs.set("resourceSearchOther", "off");
            rs.set("resourceSearchConsultant", "off");
            rs.set("resourceSearchPartner", "off");
            rs.set("resourceSearchEngineering", "off");
            rs.set("resourceSearchBusinesssuport", "off");        
            rs.set("resourceSearchFqa", "off");

             rs.set("resourceSearchInformationTechnology", "off");
             rs.set("resourceSearchPostEditing","off");
            rs.set("resourceSearchHumanResource", "off");
            rs.set("resourceSearchOffice", "off");
            rs.set("resourceSearchSales", "off");
            rs.set("resourceSearchAccounting", "off");

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
                for(ListIterator iter = ratescorelanguages2.listIterator(); iter.hasNext(); i++) {
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
            
            //set the id into session for later use (binding team to project's task)
            request.getSession(false).setAttribute("projectViewTeamBindLinId", null);
            request.getSession(false).setAttribute("projectViewTeamBindEngId", null);
            request.getSession(false).setAttribute("projectViewTeamBindDtpId", dtpId);
            request.getSession(false).setAttribute("projectViewTeamBindOthId", null);
        }
        else if(!StandardCode.getInstance().noNull(othId).equalsIgnoreCase("")) {
            //get the oth task for entering search criteria (source and target)
            OthTask t = ProjectService.getInstance().getSingleOthTask(Integer.valueOf(othId));
            
            //reset search criteria values in the form and set the source and target language
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
            
             rs.set("resourceSearchOther", "off");
            rs.set("resourceSearchConsultant", "off");
            rs.set("resourceSearchPartner", "off");
            rs.set("resourceSearchEngineering", "off");
            rs.set("resourceSearchBusinesssuport", "off");
            rs.set("resourceSearchFqa", "off");

             rs.set("resourceSearchInformationTechnology", "off");
             rs.set("resourceSearchPostEditing","off");
            rs.set("resourceSearchHumanResource", "off");
            rs.set("resourceSearchOffice", "off");
            rs.set("resourceSearchSales", "off");
            rs.set("resourceSearchAccounting", "off");

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
                for(ListIterator iter = ratescorelanguages2.listIterator(); iter.hasNext(); i++) {
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
            
            //set the id into session for later use (binding team to project's task)
            request.getSession(false).setAttribute("projectViewTeamBindLinId", null);
            request.getSession(false).setAttribute("projectViewTeamBindEngId", null);
            request.getSession(false).setAttribute("projectViewTeamBindDtpId", null);
            request.getSession(false).setAttribute("projectViewTeamBindOthId", othId);
        }

        String projectId = null;
	projectId = request.getParameter("projectViewId");

        //check attribute in request
        if(projectId == null) {
            projectId = (String) request.getAttribute("projectViewId");
        }

        //id of project from cookie
        if(projectId == null) {
            projectId = StandardCode.getInstance().getCookie("projectViewId", request.getCookies());
        }
         request.getSession(false).setAttribute("projectId", projectId);
        
        //END from project, so load search criteria for the task
        
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
