//ProjectViewPoUpdateAction.java updates the performance scores
//for each task and binded resource for this project
//It also updates the average project score per resource

package app.project;

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


public final class ProjectViewPoUpdateAction extends Action {


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
        
                    
        //get updated tasks that contain the score
        DynaValidatorForm qvg = (DynaValidatorForm) form;
        LinTask[] linTasksArray = (LinTask[]) qvg.get("linTasksProject");        
        EngTask[] engTasksArray = (EngTask[]) qvg.get("engTasksProject");
        DtpTask[] dtpTasksArray = (DtpTask[]) qvg.get("dtpTasksProject");
        OthTask[] othTasksArray = (OthTask[]) qvg.get("othTasksProject");
        
        //update each score (Integer) to db and update each resource's projectScoreAverage (Double) per task
        
        //START lin block
        for(int i = 0; i < linTasksArray.length; i++) {
            //check if score is differenct from db
            LinTask ltDb = ProjectService.getInstance().getSingleLinTask(linTasksArray[i].getLinTaskId());
            if(ltDb.getScore() == null || !ltDb.getScore().equals(linTasksArray[i].getScore())) {
            boolean update = false; //update existing or add new score
            if(ltDb.getScore() != null && !ltDb.getScore().equals(linTasksArray[i].getScore())) {
                update = true;
            }
            //START get total tasks (all four categories) for this resource to find new average
            List linTasks = ProjectService.getInstance().getResourceLinTasks(linTasksArray[i].getPersonName());
            List engTasks = ProjectService.getInstance().getResourceEngTasks(linTasksArray[i].getPersonName());
            List dtpTasks = ProjectService.getInstance().getResourceDtpTasks(linTasksArray[i].getPersonName());
            List othTasks = ProjectService.getInstance().getResourceOthTasks(linTasksArray[i].getPersonName());
            
            //number of scores
            int scoresNumber = 0;
            if(linTasks != null) {
                scoresNumber += linTasks.size();
            }
            if(engTasks != null) {
                scoresNumber += engTasks.size();
            }
            if(dtpTasks != null) {
                scoresNumber += dtpTasks.size();
            }
            if(othTasks != null) {
                scoresNumber += othTasks.size();
            }
                     
            //list of scores for this resource
            Integer[] scores = new Integer[scoresNumber];
            //total of scores
            int scoresTotal = 0;
            
            //add each of the scores for each task
            if(linTasks != null) {
            for(ListIterator iter = linTasks.listIterator(); iter.hasNext();) {
                LinTask lt = (LinTask) iter.next();
                scoresTotal += lt.getScore().intValue();
            }
            }
            if(engTasks != null) {
            for(ListIterator iter = engTasks.listIterator(); iter.hasNext();) {
                EngTask et = (EngTask) iter.next();
                scoresTotal += et.getScore().intValue();
            }
            }
            if(dtpTasks != null) {
            for(ListIterator iter = dtpTasks.listIterator(); iter.hasNext();) {
                DtpTask dt = (DtpTask) iter.next();
                scoresTotal += dt.getScore().intValue();
            }
            }
            if(othTasks != null) {
            for(ListIterator iter = othTasks.listIterator(); iter.hasNext();) {
                OthTask ot = (OthTask) iter.next();
                scoresTotal += ot.getScore().intValue();
            }
            }
            
            //get resource to update projectScoreAverage
            Resource r = ResourceService.getInstance().getSingleResourceNoLazy(Integer.valueOf(linTasksArray[i].getPersonName()));
            
            //get new scoresTotal and scoresNumber
            Integer scoresTotalNew = new Integer(0);
            Integer scoresNumberNew = new Integer(0);
            if(!update) {
                scoresTotalNew = new Integer(scoresTotal + linTasksArray[i].getScore().intValue());
                scoresNumberNew = new Integer(scoresNumber + 1);
            }
            else {
               scoresTotalNew = new Integer(scoresTotal - ltDb.getScore().intValue() + linTasksArray[i].getScore().intValue());
               scoresNumberNew = new Integer(scoresNumber); 
            }
            
            //update the projectScoreAverage
            r.setProjectScoreAverage(new Double(scoresTotalNew.doubleValue() / scoresNumberNew.doubleValue()));
            
            //update projectScoreAverage to db
            ResourceService.getInstance().updateResource(r);
            
            //END get total tasks (all four categories) for this resource to find new average 
            
            //update this task's score to db
            ProjectService.getInstance().updateLinTask(linTasksArray[i]);  
            }
        }
        //END lin block
        
        //START eng block
        for(int i = 0; i < engTasksArray.length; i++) {
            //check if score is differenct from db
            EngTask etDb = ProjectService.getInstance().getSingleEngTask(engTasksArray[i].getEngTaskId());
            if(etDb.getScore() == null || !etDb.getScore().equals(engTasksArray[i].getScore())) {
            boolean update = false; //update existing or add new score
            if(etDb.getScore() != null && !etDb.getScore().equals(engTasksArray[i].getScore())) {
                update = true;
            }
            //START get total tasks (all four categories) for this resource to find new average
            List linTasks = ProjectService.getInstance().getResourceLinTasks(engTasksArray[i].getPersonName());
            List engTasks = ProjectService.getInstance().getResourceEngTasks(engTasksArray[i].getPersonName());
            List dtpTasks = ProjectService.getInstance().getResourceDtpTasks(engTasksArray[i].getPersonName());
            List othTasks = ProjectService.getInstance().getResourceOthTasks(engTasksArray[i].getPersonName());
            
            //number of scores
            int scoresNumber = 0;
            if(linTasks != null) {
                scoresNumber += linTasks.size();
            }
            if(engTasks != null) {
                scoresNumber += engTasks.size();
            }
            if(dtpTasks != null) {
                scoresNumber += dtpTasks.size();
            }
            if(othTasks != null) {
                scoresNumber += othTasks.size();
            }
                     
            //list of scores for this resource
            Integer[] scores = new Integer[scoresNumber];
            //total of scores
            int scoresTotal = 0;
            
            //add each of the scores for each task
            if(linTasks != null) {
            for(ListIterator iter = linTasks.listIterator(); iter.hasNext();) {
                LinTask lt = (LinTask) iter.next();
                scoresTotal += lt.getScore().intValue();
            }
            }
            if(engTasks != null) {
            for(ListIterator iter = engTasks.listIterator(); iter.hasNext();) {
                EngTask et = (EngTask) iter.next();
                scoresTotal += et.getScore().intValue();
            }
            }
            if(dtpTasks != null) {
            for(ListIterator iter = dtpTasks.listIterator(); iter.hasNext();) {
                DtpTask dt = (DtpTask) iter.next();
                scoresTotal += dt.getScore().intValue();
            }
            }
            if(othTasks != null) {
            for(ListIterator iter = othTasks.listIterator(); iter.hasNext();) {
                OthTask ot = (OthTask) iter.next();
                scoresTotal += ot.getScore().intValue();
            }
            }
            
            //get resource to update projectScoreAverage
            Resource r = ResourceService.getInstance().getSingleResourceNoLazy(Integer.valueOf(engTasksArray[i].getPersonName()));
            
            //get new scoresTotal and scoresNumber
            Integer scoresTotalNew = new Integer(0);
            Integer scoresNumberNew = new Integer(0);
            if(!update) {
                scoresTotalNew = new Integer(scoresTotal + engTasksArray[i].getScore().intValue());
                scoresNumberNew = new Integer(scoresNumber + 1);
            }
            else {
               scoresTotalNew = new Integer(scoresTotal - etDb.getScore().intValue() + engTasksArray[i].getScore().intValue());
               scoresNumberNew = new Integer(scoresNumber); 
            }
            
            //update the projectScoreAverage
            r.setProjectScoreAverage(new Double(scoresTotalNew.doubleValue() / scoresNumberNew.doubleValue()));
            
            //update projectScoreAverage to db
            ResourceService.getInstance().updateResource(r);
            
            //END get total tasks (all four categories) for this resource to find new average 
            
            //update this task's score to db
            ProjectService.getInstance().updateEngTask(engTasksArray[i]);  
            }
        }
        //END eng block
        
        //START dtp block
        for(int i = 0; i < dtpTasksArray.length; i++) {
            //check if score is differenct from db
            DtpTask dtDb = ProjectService.getInstance().getSingleDtpTask(dtpTasksArray[i].getDtpTaskId());
            if(dtDb.getScore() == null || !dtDb.getScore().equals(dtpTasksArray[i].getScore())) {
            boolean update = false; //update existing or add new score
            if(dtDb.getScore() != null && !dtDb.getScore().equals(dtpTasksArray[i].getScore())) {
                update = true;
            }
            //START get total tasks (all four categories) for this resource to find new average
            List linTasks = ProjectService.getInstance().getResourceLinTasks(dtpTasksArray[i].getPersonName());
            List engTasks = ProjectService.getInstance().getResourceEngTasks(dtpTasksArray[i].getPersonName());
            List dtpTasks = ProjectService.getInstance().getResourceDtpTasks(dtpTasksArray[i].getPersonName());
            List othTasks = ProjectService.getInstance().getResourceOthTasks(dtpTasksArray[i].getPersonName());
            
            //number of scores
            int scoresNumber = 0;
            if(linTasks != null) {
                scoresNumber += linTasks.size();
            }
            if(engTasks != null) {
                scoresNumber += engTasks.size();
            }
            if(dtpTasks != null) {
                scoresNumber += dtpTasks.size();
            }
            if(othTasks != null) {
                scoresNumber += othTasks.size();
            }
                     
            //list of scores for this resource
            Integer[] scores = new Integer[scoresNumber];
            //total of scores
            int scoresTotal = 0;
            
            //add each of the scores for each task
            if(linTasks != null) {
            for(ListIterator iter = linTasks.listIterator(); iter.hasNext();) {
                LinTask lt = (LinTask) iter.next();
                scoresTotal += lt.getScore().intValue();
            }
            }
            if(engTasks != null) {
            for(ListIterator iter = engTasks.listIterator(); iter.hasNext();) {
                EngTask et = (EngTask) iter.next();
                scoresTotal += et.getScore().intValue();
            }
            }
            if(dtpTasks != null) {
            for(ListIterator iter = dtpTasks.listIterator(); iter.hasNext();) {
                DtpTask dt = (DtpTask) iter.next();
                scoresTotal += dt.getScore().intValue();
            }
            }
            if(othTasks != null) {
            for(ListIterator iter = othTasks.listIterator(); iter.hasNext();) {
                OthTask ot = (OthTask) iter.next();
                scoresTotal += ot.getScore().intValue();
            }
            }
            
            //get resource to update projectScoreAverage
            Resource r = ResourceService.getInstance().getSingleResourceNoLazy(Integer.valueOf(dtpTasksArray[i].getPersonName()));
            
            //get new scoresTotal and scoresNumber
            Integer scoresTotalNew = new Integer(0);
            Integer scoresNumberNew = new Integer(0);
            if(!update) {
                scoresTotalNew = new Integer(scoresTotal + dtpTasksArray[i].getScore().intValue());
                scoresNumberNew = new Integer(scoresNumber + 1);
            }
            else {
               scoresTotalNew = new Integer(scoresTotal - dtDb.getScore().intValue() + dtpTasksArray[i].getScore().intValue());
               scoresNumberNew = new Integer(scoresNumber); 
            }
            
            //update the projectScoreAverage
            r.setProjectScoreAverage(new Double(scoresTotalNew.doubleValue() / scoresNumberNew.doubleValue()));
            
            //update projectScoreAverage to db
            ResourceService.getInstance().updateResource(r);
            
            //END get total tasks (all four categories) for this resource to find new average 
            
            //update this task's score to db
            ProjectService.getInstance().updateDtpTask(dtpTasksArray[i]);  
            }
        }
        //END dtp block
        
        //START oth block
        for(int i = 0; i < othTasksArray.length; i++) {
            //check if score is differenct from db
            OthTask otDb = ProjectService.getInstance().getSingleOthTask(othTasksArray[i].getOthTaskId());
            if(otDb.getScore() == null || !otDb.getScore().equals(othTasksArray[i].getScore())) {
            boolean update = false; //update existing or add new score
            if(otDb.getScore() != null && !otDb.getScore().equals(othTasksArray[i].getScore())) {
                update = true;
            }
            //START get total tasks (all four categories) for this resource to find new average
            List linTasks = ProjectService.getInstance().getResourceLinTasks(othTasksArray[i].getPersonName());
            List engTasks = ProjectService.getInstance().getResourceEngTasks(othTasksArray[i].getPersonName());
            List dtpTasks = ProjectService.getInstance().getResourceDtpTasks(othTasksArray[i].getPersonName());
            List othTasks = ProjectService.getInstance().getResourceOthTasks(othTasksArray[i].getPersonName());
            
            //number of scores
            int scoresNumber = 0;
            if(linTasks != null) {
                scoresNumber += linTasks.size();
            }
            if(engTasks != null) {
                scoresNumber += engTasks.size();
            }
            if(dtpTasks != null) {
                scoresNumber += dtpTasks.size();
            }
            if(othTasks != null) {
                scoresNumber += othTasks.size();
            }
                     
            //list of scores for this resource
            Integer[] scores = new Integer[scoresNumber];
            //total of scores
            int scoresTotal = 0;
            
            //add each of the scores for each task
            if(linTasks != null) {
            for(ListIterator iter = linTasks.listIterator(); iter.hasNext();) {
                LinTask lt = (LinTask) iter.next();
                scoresTotal += lt.getScore().intValue();
            }
            }
            if(engTasks != null) {
            for(ListIterator iter = engTasks.listIterator(); iter.hasNext();) {
                EngTask et = (EngTask) iter.next();
                scoresTotal += et.getScore().intValue();
            }
            }
            if(dtpTasks != null) {
            for(ListIterator iter = dtpTasks.listIterator(); iter.hasNext();) {
                DtpTask dt = (DtpTask) iter.next();
                scoresTotal += dt.getScore().intValue();
            }
            }
            if(othTasks != null) {
            for(ListIterator iter = othTasks.listIterator(); iter.hasNext();) {
                OthTask ot = (OthTask) iter.next();
                scoresTotal += ot.getScore().intValue();
            }
            }
            try{
            //get resource to update projectScoreAverage
            Resource r = ResourceService.getInstance().getSingleResourceNoLazy(Integer.valueOf(othTasksArray[i].getPersonName()));
            
            //get new scoresTotal and scoresNumber
            Integer scoresTotalNew = new Integer(0);
            Integer scoresNumberNew = new Integer(0);
            if(!update) {
                scoresTotalNew = new Integer(scoresTotal + othTasksArray[i].getScore().intValue());
                scoresNumberNew = new Integer(scoresNumber + 1);
            }
            else {
               scoresTotalNew = new Integer(scoresTotal - otDb.getScore().intValue() + othTasksArray[i].getScore().intValue());
               scoresNumberNew = new Integer(scoresNumber); 
            }
            
            //update the projectScoreAverage
            r.setProjectScoreAverage(new Double(scoresTotalNew.doubleValue() / scoresNumberNew.doubleValue()));
            
            //update projectScoreAverage to db
            ResourceService.getInstance().updateResource(r);
                }catch(Exception e){}
            //END get total tasks (all four categories) for this resource to find new average 
            
            //update this task's score to db
            ProjectService.getInstance().updateOthTask(othTasksArray[i]);  
            }
        }
        //END oth block
        
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
