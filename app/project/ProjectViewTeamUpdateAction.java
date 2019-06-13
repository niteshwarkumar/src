//ProjectViewTeamUpdateAction.java updates team/tracking info
//for a project

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


public final class ProjectViewTeamUpdateAction extends Action {


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
        
        //START LIN TASKS
        //get the updated list of lin Tasks for this project
        LinTask[] linTasks = (LinTask[]) qvg.get("linTasksProject");


             
        //process each lin Task to db
        for(int i = 0; i < linTasks.length; i++) {
            LinTask lt = linTasks[i];

            if(lt.getICRcheck()!=null){
            Boolean ICRcheck=lt.getICRcheck().FALSE;

            }


          //-  if(lt.getNotes())
            
            //START process new total values (2 of them: Total and dollar TOTAL)
           // if((lt.getWord100() != null  && !lt.getWord100().equals(new Integer(0))) || (lt.getWordRep() != null  && !lt.getWordRep().equals(new Integer(0))) || (lt.getWord85() != null  && !lt.getWord85().equals(new Integer(0))) || (lt.getWordNew() != null  && !lt.getWordNew().equals(new Integer(0))) || (lt.getWordNew4() != null  && !lt.getWordNew4().equals(new Double(0)))) { //trados
                int word100 = 0;
                int wordRep = 0;
                int word95 = 0;
                int word85 = 0;
                int word75 = 0;
                double wordNew = 0;
                int word8599 = 0;
                double wordNew4 = 0;
                
                if(lt.getWord100() != null) {   
                    word100 = lt.getWord100().intValue();
                }
                if(lt.getWordRep() != null) {     
                    wordRep = lt.getWordRep().intValue();
                }
                if(lt.getWord95() != null) { 
                    word95 = lt.getWord95().intValue();
                }
                if(lt.getWord85() != null) { 
                    word85 = lt.getWord85().intValue();
                }
                if(lt.getWord75() != null) { 
                    word75 = lt.getWord75().intValue();
                }
                if(lt.getWordNew() != null) { 
                    wordNew = lt.getWordNew().doubleValue();
                }
                if(lt.getWord8599() != null) { 
                    word8599 = lt.getWord8599().intValue();
                }
                if(lt.getWordNew4() != null) { 
                    wordNew4 = lt.getWordNew4().doubleValue();  
                }
                
                
                
                double rate = 0;
                try {
                    String unformattedRate = lt.getInternalRate().replaceAll(",", "");
                    rate = Double.valueOf(unformattedRate).doubleValue();
                }
                catch(java.lang.NumberFormatException nfe) {
                    rate = 0;
                }        
                
                double wordTotal = word100 + wordRep + word8599 + wordNew4;
                
                 if(!p.getCompany().isScaleDefault(p.getProjectId())) {
                    wordTotal = word100 + wordRep + word95 + word85 + word75 + wordNew;
                } 
                
                //rate and cost                
                double thisTotal = 0.0;
                //scale the rates (either default or custom)
                Resource r = null; //get this task's team member to see if default scale used or not
                if(lt.getPersonName() != null && lt.getPersonName().length() > 0) {
                    r = ResourceService.getInstance().getSingleResource(Integer.valueOf(lt.getPersonName()));
                }
                if(p.getCompany().isScaleDefault(p.getProjectId())) {
                    double rateNew4 = rate;
                    double rate100 = rate * Double.parseDouble(p.getCompany().getScale100(p.getProjectId(),p.getCompany().getClientId()));
                    double rateRep = rate * Double.parseDouble(p.getCompany().getScaleRep(p.getProjectId(),p.getCompany().getClientId()));
                    double rate8599 = rate * Double.parseDouble(p.getCompany().getScale8599());   
                    double costNew4 = wordNew4 * rateNew4;
                    double cost100 = word100 * rate100;
                    double costRep = wordRep * rateRep;
                    double cost8599 = word8599 * rate8599; 
                    
                    thisTotal = costNew4 + cost100 + costRep + cost8599;
                }
                else {
                    double rateNew4 = rate;
                    double rate100 = rate * Double.parseDouble(p.getCompany().getScale100(p.getProjectId(),p.getCompany().getClientId()));
                    double rateRep = rate * Double.parseDouble(p.getCompany().getScaleRep(p.getProjectId(),p.getCompany().getClientId()));
                    double rate7584 = rate * Double.parseDouble(p.getCompany().getScale75(p.getProjectId(),p.getCompany().getClientId()));  
                    double rate8594 = rate * Double.parseDouble(p.getCompany().getScale85(p.getProjectId(),p.getCompany().getClientId())); 
                    double rate9599 = rate * Double.parseDouble(p.getCompany().getScale95(p.getProjectId(),p.getCompany().getClientId())); 
                    
                    double costNew4 = wordNew * rateNew4;
                    double cost100 = word100 * rate100;
                    double costRep = wordRep * rateRep;
                    double cost7584 = word75 * rate7584; 
                    double cost8594 = word85 * rate8594; 
                    double cost9599 = word95 * rate9599; 
                    
                    thisTotal = costNew4 + cost100 + costRep + cost7584 + cost8594 + cost9599;
                    //thisTotal = wordTotal*rate;
                }
                    
                linTotal += thisTotal; //update lin block
                
                lt.setWordTotal(new Double(wordTotal));
                
                ////Let user overwrite automatic calculations
                ///ALEX: Do NOT let user overwrite the calculationsØ
                //if(lt.getInternalDollarTotal()==null || "".equals(lt.getInternalDollarTotal())){
                    lt.setInternalDollarTotal(StandardCode.getInstance().formatDouble(new Double(thisTotal)));
               
               // }
                
                lt.setInternalRate(StandardCode.getInstance().formatDouble4(new Double(rate)));
                
           /* }
            else { //non-trados; such as hourly                
                if(lt.getWordTotal() != null) { //if from delete then make sure it is not null
                    double total = lt.getWordTotal().doubleValue();
                    double rate = 0;
                    try {
                        rate = Double.valueOf(lt.getInternalRate()).doubleValue();
                    }
                    catch(java.lang.NumberFormatException nfe) {
                        rate = 0;
                    }                

                    double thisTotal = total*rate;
                    //System.out.println("inside of else:total="+total+",rate="+rate+"thisTotal="+thisTotal);
                    linTotal += thisTotal; //update lin block
                   //If user didn't leave it empty, then override it'
                  //if(lt.getInternalDollarTotal()==null || "".equals(lt.getInternalDollarTotal())){
                    lt.setInternalDollarTotal(StandardCode.getInstance().formatDouble(new Double(thisTotal)));
                  // }
                    lt.setInternalRate(StandardCode.getInstance().formatDouble3(new Double(rate)));
                }
            }*/
            
            //END process new total values (2 of them)
            
            if(autoUpdate == null) { //update dates only if not updating tasks
                //START update lin dates
                String linTasksProjectSentArray = request.getParameter("linTasksProjectSentArray" + lt.getLinTaskId());
                if(linTasksProjectSentArray.length() >= 1) {
                    lt.setSentDateDate(DateService.getInstance().convertDate(linTasksProjectSentArray).getTime());
                }
                String linTasksProjectDueArray = request.getParameter("linTasksProjectDueArray" + lt.getLinTaskId());
                if(linTasksProjectDueArray.length() >= 1) {
                    lt.setDueDateDate(DateService.getInstance().convertDate(linTasksProjectDueArray).getTime());
                }
                String linTasksProjectReceivedArray = request.getParameter("linTasksProjectReceivedArray" + lt.getLinTaskId());
                if(linTasksProjectReceivedArray.length() >= 1) {
                    lt.setReceivedDateDate(DateService.getInstance().convertDate(linTasksProjectReceivedArray).getTime());
                }
                String linTasksProjectInvoiceArray = request.getParameter("linTasksProjectInvoiceArray" + lt.getLinTaskId());
                if(linTasksProjectInvoiceArray.length() >= 1) {
                    lt.setInvoiceDateDate(DateService.getInstance().convertDate(linTasksProjectInvoiceArray).getTime());
                }
                //END update lin dates
            }
            
            //update to db
            ProjectService.getInstance().updateLinTask(lt);
           
        }//END LIN TASKS
        
        //START ENG TASKS
        //get the updated list of eng Tasks for this project
        EngTask[] engTasks = (EngTask[]) qvg.get("engTasksProject");
        
        //process each eng Task to db
        for(int i = 0; i < engTasks.length; i++) {
            EngTask et = engTasks[i];
            
                //START process new TOTAL value
             if(et.getTotalTeam() != null) { //if from delete then make sure it is not null
                double total = et.getTotalTeam().doubleValue();
                double rate = 0;
                try {
                    rate = Double.valueOf(et.getInternalRate()).doubleValue();
                }
                catch(java.lang.NumberFormatException nfe) {
                    rate = 0;
                }                
                
                double thisTotal = total*rate;
                engTotal += thisTotal; //update eng block
                
                et.setInternalDollarTotal(StandardCode.getInstance().formatDouble(new Double(thisTotal)));
                et.setInternalRate(StandardCode.getInstance().formatDouble4(new Double(rate)));            
             }
            //END process new total value
            
            if(autoUpdate == null) { //update dates only if not updating tasks
                //START update eng dates
                String engTasksProjectSentArray = request.getParameter("engTasksProjectSentArray" + et.getEngTaskId());
                if(engTasksProjectSentArray.length() >= 1) {
                    et.setSentDateDate(DateService.getInstance().convertDate(engTasksProjectSentArray).getTime());
                }
                String engTasksProjectDueArray = request.getParameter("engTasksProjectDueArray" + et.getEngTaskId());
                if(engTasksProjectDueArray.length() >= 1) {
                    et.setDueDateDate(DateService.getInstance().convertDate(engTasksProjectDueArray).getTime());
                }
                String engTasksProjectReceivedArray = request.getParameter("engTasksProjectReceivedArray" + et.getEngTaskId());
                if(engTasksProjectReceivedArray.length() >= 1) {
                    et.setReceivedDateDate(DateService.getInstance().convertDate(engTasksProjectReceivedArray).getTime());
                }
                String engTasksProjectInvoiceArray = request.getParameter("engTasksProjectInvoiceArray" + et.getEngTaskId());
                if(engTasksProjectInvoiceArray.length() >= 1) {
                    et.setInvoiceDateDate(DateService.getInstance().convertDate(engTasksProjectInvoiceArray).getTime());
                }
                //END update eng dates
            }
            
            //update to db
            ProjectService.getInstance().updateEngTask(et);
           
        }//END ENG TASKS
        
        //START DTP TASKS
        //get the updated list of dtp Tasks for this project
        DtpTask[] dtpTasks = (DtpTask[]) qvg.get("dtpTasksProject");
        
        //process each dtp Task to db
        for(int i = 0; i < dtpTasks.length; i++) {
            DtpTask dt = dtpTasks[i];
            
            //START process new TOTAL value
            if(dt.getTotalTeam() != null) { //if from delete then make sure it is not null
                double total = dt.getTotalTeam().doubleValue();
                double rate = 0;
                try {
                    rate = Double.valueOf(dt.getInternalRate()).doubleValue();
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
                }
                String dtpTasksProjectDueArray = request.getParameter("dtpTasksProjectDueArray" + dt.getDtpTaskId());
                if(dtpTasksProjectDueArray.length() >= 1) {
                    dt.setDueDateDate(DateService.getInstance().convertDate(dtpTasksProjectDueArray).getTime());
                }
                String dtpTasksProjectReceivedArray = request.getParameter("dtpTasksProjectReceivedArray" + dt.getDtpTaskId());
                if(dtpTasksProjectReceivedArray.length() >= 1) {
                    dt.setReceivedDateDate(DateService.getInstance().convertDate(dtpTasksProjectReceivedArray).getTime());
                }
                String dtpTasksProjectInvoiceArray = request.getParameter("dtpTasksProjectInvoiceArray" + dt.getDtpTaskId());
                if(dtpTasksProjectInvoiceArray.length() >= 1) {
                    dt.setInvoiceDateDate(DateService.getInstance().convertDate(dtpTasksProjectInvoiceArray).getTime());
                }
                //END update dtp dates
            }
            
            //update to db
            ProjectService.getInstance().updateDtpTask(dt);
           
        }//END DTP TASKS
        
        //START OTH TASKS
        //get the updated list of oth Tasks for this project
        OthTask[] othTasks = (OthTask[]) qvg.get("othTasksProject");
        
        //process each oth Task to db
        for(int i = 0; i < othTasks.length; i++) {
            OthTask ot = othTasks[i];
            
            //START process new TOTAL value
            if(ot.getTotalTeam() != null) { //if from delete then make sure it is not null
                double total = ot.getTotalTeam().doubleValue();
                double rate = 0;
                try {
                    rate = Double.valueOf(ot.getInternalRate()).doubleValue();
                }
                catch(java.lang.NumberFormatException nfe) {
                    rate = 0;
                }                
                
                double thisTotal = total*rate;
                othTotal += thisTotal; //update oth block
                
                ot.setInternalDollarTotal(StandardCode.getInstance().formatDouble(new Double(thisTotal)));
                ot.setInternalRate(StandardCode.getInstance().formatDouble4(new Double(rate)));            
            }
            //END process new total value
            
            if(autoUpdate == null) { //update dates only if not updating tasks
                //START update oth dates
                String othTasksProjectSentArray = request.getParameter("othTasksProjectSentArray" + String.valueOf(i));
                if(othTasksProjectSentArray.length() >= 1) {
                    ot.setSentDateDate(DateService.getInstance().convertDate(othTasksProjectSentArray).getTime());
                }
                String othTasksProjectDueArray = request.getParameter("othTasksProjectDueArray" + String.valueOf(i));
                if(othTasksProjectDueArray.length() >= 1) {
                    ot.setDueDateDate(DateService.getInstance().convertDate(othTasksProjectDueArray).getTime());
                }
                String othTasksProjectReceivedArray = request.getParameter("othTasksProjectReceivedArray" + String.valueOf(i));
                if(othTasksProjectReceivedArray.length() >= 1) {
                    ot.setReceivedDateDate(DateService.getInstance().convertDate(othTasksProjectReceivedArray).getTime());
                }
                String othTasksProjectInvoiceArray = request.getParameter("othTasksProjectInvoiceArray" + String.valueOf(i));
                if(othTasksProjectInvoiceArray.length() >= 1) {
                    ot.setInvoiceDateDate(DateService.getInstance().convertDate(othTasksProjectInvoiceArray).getTime());
                }
                //END update oth dates
            }
            
            //update to db
            ProjectService.getInstance().updateOthTask(ot);
           
        }//END OTH TASKS
        
        //update project to db
        //NOTE: NO PROJECT TOTALS CHANGES ProjectService.getInstance().updateProject(p);
        
        //mark AutoUpdate as true        
        if(autoUpdate != null) {
            Integer newAutoUpdate = new Integer(autoUpdate.intValue() + 1);
            request.setAttribute("AutoUpdate", newAutoUpdate); //place true value (1) into request 
        }
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
