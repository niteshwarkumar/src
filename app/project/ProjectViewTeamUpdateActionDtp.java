//ProjectViewTeamUpdateAction.java updates team/tracking info
//for a project

package app.project;
import app.extjs.helpers.ProjectHelper;
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
import app.resource.*;
import app.standardCode.*;
import org.apache.struts.validator.*;



public final class ProjectViewTeamUpdateActionDtp extends Action {


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
              
        
        //get project to edit
        Project p = ProjectService.getInstance().getSingleProject(id); 
        
        //get autoUpdate value
        Integer autoUpdate = (Integer) request.getAttribute("AutoUpdate");
        
        //values for update from form; change what is stored in db to these values
        DynaValidatorForm qvg = (DynaValidatorForm) form;
        
        //dollar totals of each block
        double linTotal = 0;
        double engTotal = 0;
        double dtpTotal = 0;
        double othTotal = 0;        
        double pmTotal = 0;
        
        //START DTP TASKS
        //get the updated list of dtp Tasks for this project
        DtpTask[] dtpTasks = (DtpTask[]) qvg.get("dtpTasksProject");
        
        //process each dtp Task to db
        for(int i = 0; i < dtpTasks.length; i++) {
            DtpTask dt = dtpTasks[i];
            
            //START process new TOTAL value
            if(dt.getTotalTeam() != null) { //if from delete then make sure it is not null
                double total = dt.getTotalTeam();
                double rate = 0;
                try {
                    rate = Double.valueOf(dt.getInternalRate().replaceAll(",", ""));
                }
                catch(java.lang.NumberFormatException nfe) {
                    rate = 0;
                }                
                
                double thisTotal = total*rate;
                dtpTotal += thisTotal; //update dtp block
                
                dt.setInternalDollarTotal(StandardCode.getInstance().formatDouble(new Double(thisTotal)));
                dt.setInternalRate(StandardCode.getInstance().formatDouble4(new Double(rate)));            
            }
            //END process new total value
            
            if(autoUpdate == null) { //update dates only if not updating tasks
                //START update dtp dates
                String dtpTasksProjectSentArray = request.getParameter("dtpTasksProjectSentArray" + dt.getDtpTaskId());
                if(dtpTasksProjectSentArray.length() >= 1) {
                    dt.setSentDateDate(DateService.getInstance().convertDate(dtpTasksProjectSentArray).getTime());
                }else dt.setSentDateDate(null);
                String dtpTasksProjectDueArray = request.getParameter("dtpTasksProjectDueArray" + dt.getDtpTaskId());
                if(dtpTasksProjectDueArray.length() >= 1) {
                    dt.setDueDateDate(DateService.getInstance().convertDate(dtpTasksProjectDueArray).getTime());
                }else dt.setDueDateDate(null);
                String dtpTasksProjectReceivedArray = request.getParameter("dtpTasksProjectReceivedArray" + dt.getDtpTaskId());
                if(dtpTasksProjectReceivedArray.length() >= 1) {
                    dt.setReceivedDateDate(DateService.getInstance().convertDate(dtpTasksProjectReceivedArray).getTime());
                }else dt.setReceivedDateDate(null);
                String dtpTasksProjectInvoiceArray = request.getParameter("dtpTasksProjectInvoiceArray" + dt.getDtpTaskId());
                if(dtpTasksProjectInvoiceArray.length() >= 1) {
                    dt.setInvoiceDateDate(DateService.getInstance().convertDate(dtpTasksProjectInvoiceArray).getTime());
                }else dt.setInvoiceDateDate(null);
                //END update dtp dates
            }
            
            //update to db
            ProjectService.getInstance().updateDtpTask(dt);
           
        }//END DTP TASKS
        
  
        //update project to db
        //NOTE: NO PROJECT TOTALS CHANGES 
        if(request.getParameter("euroToUsdExchangeRate")!=null){
            p.setEuroToUsdExchangeRate(new Double(request.getParameter("euroToUsdExchangeRate")));
        }
        ProjectService.getInstance().updateProject(p);
        
        //mark AutoUpdate as true        
        if(autoUpdate != null) {
            Integer newAutoUpdate = new Integer(autoUpdate.intValue() + 1);
            request.setAttribute("AutoUpdate", newAutoUpdate); //place true value (1) into request 
        }

        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
