//ProjectViewInspectionUpdateAction.java updates inspection info
//for a project

package app.project;

import app.tracker.MilestoneLanguage;
import app.tracker.TrackerService;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleException;
import org.apache.struts.util.MessageResources;
import org.apache.commons.beanutils.PropertyUtils;
import java.util.*;
import java.text.*;
import app.user.*;
import app.db.*;
import app.workspace.*;
import app.security.*;
import app.project.*;
import app.standardCode.*;
import org.apache.struts.validator.*;
import app.client.*;
import java.util.Enumeration;
import java.sql.Timestamp;


public final class ProjectViewInspectionUpdateAction_1 extends Action {


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
        
        //START get id of current project from either request, attribute, or cookie 
        //id of project from request
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

        //default project to last if not in request or cookie
        if(projectId == null) {
                List results = ProjectService.getInstance().getProjectList();
                                
                ListIterator iterScroll = null;
                for(iterScroll = results.listIterator(); iterScroll.hasNext(); iterScroll.next()) {}
                   iterScroll.previous();
                   Project p = (Project) iterScroll.next();  
                   projectId = String.valueOf(p.getProjectId());
         }            
        
        Integer id = Integer.valueOf(projectId);
        
        //END get id of current project from either request, attribute, or cookie 
              
        //get the form containing the inspections
        DynaValidatorForm pvi = (DynaValidatorForm) form;
        
        //get the updated list of inspections for this project
        Inspection[] inspections = (Inspection[]) pvi.get("inspections"); 
        
        //update each inspection
        for(int i = 0; i < inspections.length; i++) {
            //set the check box to correct boolean value
            if(request.getParameter("inspections[" + String.valueOf(i) + "].applicable") != null) {
                inspections[i].setApplicable(true);
            }
            else {
                inspections[i].setApplicable(false);
            }
            if(request.getParameter("inspections[" + String.valueOf(i) + "].approved") != null) {
                inspections[i].setApproved(true);
            }
            else {
                inspections[i].setApproved(false);
            }
            if(request.getParameter("inspections[" + String.valueOf(i) + "].rejected") != null) {
                inspections[i].setRejected(true);
            }
            else {
                inspections[i].setRejected(false);
            }            
            
            //START update inspection dates
            String inspectionDateArray = request.getParameter("inspectionDateArray" + String.valueOf(i));
            if(inspectionDateArray.length() >= 1) {
                inspections[i].setInDate(DateService.getInstance().convertDate(inspectionDateArray).getTime());
            }
            //END update inspection dates
            
            //update to db
            ProjectService.getInstance().updateInspection(inspections[i]);
            
            
            
            }
        
            
        // 1) get existing tracker values from DB
        Hashtable millanValues = new Hashtable();
        List trackerItems = TrackerService.getInstance().getMilLanList(id);
        if(trackerItems!=null){    
        for(ListIterator iter = trackerItems.listIterator(); iter.hasNext(); ) {
                MilestoneLanguage millan = (MilestoneLanguage) iter.next();
                ////THIS FORM: MILLAN_<%=iDisplay.getInspectionId()+"_"+languageId+"_"+iDisplay.getProject().getProjectId()
                millanValues.put("MILLAN_"+millan.getMilestone_id()+"_"+millan.getLang_id()+"_"+millan.getProject_id(),millan);
            }
        }
            
            Timestamp milestonesTs = new Timestamp(System.currentTimeMillis());
            //2) Delete existing tracker values from DB
            TrackerService.getInstance().deleteAllMilestonsForProject(projectId.toString());
            Enumeration paramNames = request.getParameterNames();
            while(paramNames.hasMoreElements()) {
                String parm = (String)paramNames.nextElement();
               
              //3) If parm exists in both ht and request, use one from ht. 
               //If parm only exists in request, then use that one
              if(parm.startsWith("MILLAN_")&& request.getParameter(parm)!=null){
                    StringTokenizer strTok = new StringTokenizer(parm,"_");
                    //THIS FORM: MILLAN_<%=iDisplay.getInspectionId()+"_"+languageId+"_"+iDisplay.getProject().getProjectId()
                    //Ignore first
                    strTok.nextToken();
                    String inspectionId =  strTok.nextToken();
                    String languageId =  strTok.nextToken();
                    String prId =  strTok.nextToken();
                    MilestoneLanguage millan = (MilestoneLanguage)millanValues.get("MILLAN_"+inspectionId+"_"+languageId+"_"+prId);
                    MilestoneLanguage ml = new MilestoneLanguage();
                    if(millan!=null){
                         ml.setLang_id(millan.getLang_id());
                         ml.setMilestone_id(millan.getMilestone_id());
                         ml.setProject_id(millan.getProject_id());
                         ml.setCompleted_ts(millan.getCompleted_ts());
                    }else{
                    
                        
                        ml.setLang_id(new Integer(languageId));
                        ml.setMilestone_id(new Integer(inspectionId));
                        ml.setProject_id(new Integer(prId));
                        ml.setCompleted_ts(milestonesTs);
                        
                    }
                   TrackerService.getInstance().updateMilestoneLanguage(ml);
                    
                    
                    
              
              }
            
        }
        
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
