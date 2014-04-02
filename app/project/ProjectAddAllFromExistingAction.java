//ProjectAddFromExistingAction.java collects values related to 
//project from an existing project and adds it to the db

package app.project;

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
import org.apache.struts.validator.*;
import java.util.*;
import app.user.*;
import app.db.*;
import app.client.*;
import app.quote.*;
import app.project.*;
import app.workspace.*;
import app.standardCode.*;
import app.security.*;

public final class ProjectAddAllFromExistingAction extends Action {


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
	
        //get project to copy from
        String fromId = request.getParameter("id");
        Project pFrom = ProjectService.getInstance().getSingleProject(Integer.valueOf(fromId));
        
        //get client
        Client c = pFrom.getCompany();
        c = ClientService.getInstance().getSingleClient(c.getClientId());
        
        //insert new project into db, building one-to-many link between client and project
        Project p = new Project();
        p.setNumber(ProjectService.getInstance().getNewProjectNumber());
        p.setStatus("active");
        p.setAe(c.getSales_rep());
        Integer projectId = ProjectService.getInstance().addProjectWithClient(p, c);              
        
        //update new projects info
        Project newP = ProjectService.getInstance().getSingleProject(projectId);
             try{
        String[] fname = pFrom.getPm().split(" ");
         User pm = null;
            if (fname.length == 2) {
                pm = UserService.getInstance().getSingleUserRealName(fname[0], fname[1]);
            } else if (fname.length == 3) {
                pm = UserService.getInstance().getSingleUserRealName(fname[0], fname[1] + " " + fname[2]);
            } else if (fname.length == 4) {
                pm = UserService.getInstance().getSingleUserRealName(fname[0], fname[1] + " " + fname[2] + " " + fname[3]);
            }
         p.setPm_id(pm.getUserId());
        }catch(Exception e){ p.setPm_id(0); }
        newP.setPm(pFrom.getPm());
        newP.setPmPercent(pFrom.getPmPercent());
        newP.setRushPercent(pFrom.getRushPercent());
        
         User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
        newP.setCreatedBy(u.getFirstName() + " " + u.getLastName());
        newP.setCreatedDate(new Date(System.currentTimeMillis()));
        
        
        newP.setProductDescription(pFrom.getProductDescription());
        newP.setProduct(pFrom.getProduct());
        newP.setNotes(pFrom.getNotes());
        newP.setProjectAmount(pFrom.getProjectAmount());
        newP.setBeforeWorkTurn(pFrom.getBeforeWorkTurn());
        newP.setBeforeWorkTurnUnits(pFrom.getBeforeWorkTurnUnits());
        newP.setProjectRequirements(pFrom.getProjectRequirements());
        newP.setProjectDescription(pFrom.getProjectDescription());
        newP.setLinRequirements(pFrom.getLinRequirements());
        newP.setDtpRequirements(pFrom.getDtpRequirements());
        newP.setEngRequirements(pFrom.getEngRequirements());
        newP.setOthRequirements(pFrom.getOthRequirements());
        newP.setSourceApplication(pFrom.getSourceApplication());
        newP.setSourceTechNotes(pFrom.getSourceTechNotes());
        newP.setSourceOS(pFrom.getSourceOS());
        newP.setSourceVersion(pFrom.getSourceVersion());
        newP.setDeliverableApplication(pFrom.getDeliverableApplication());
        newP.setDeliverableOS(pFrom.getDeliverableOS());
        newP.setDeliverableSame(pFrom.getDeliverableSame());
        newP.setDeliverableTechNotes(pFrom.getDeliverableTechNotes());
        newP.setDeliverableVersion(pFrom.getDeliverableVersion());
        newP.setStartDate(new Date());
        newP.setOriginal_project_id(pFrom.getProjectId());
        newP.setOriginal_project_number(pFrom.getNumber());
        ProjectService.getInstance().updateProject(newP);
       
        //bind contact
        ClientContact cc = ClientService.getInstance().getSingleClientContact(pFrom.getContact().getClientContactId());          
        //insert into db, building link between contact and project
        ProjectService.getInstance().linkProjectClientContact(newP, cc);
        
        //add all sets, such as source doc, target doc, and the four tasks, as new objects to the new q
        Set sourceDocs = pFrom.getSourceDocs();
        Set targetDocs = null;
        Set linTasks = null;
        Set engTasks = null;
        Set dtpTasks = null;
        Set othTasks = null;
        
        //sources        
        if(sourceDocs != null) {
            for(Iterator iter = sourceDocs.iterator(); iter.hasNext();) {
                SourceDoc sd = (SourceDoc) iter.next();
                Integer oldSdId = sd.getSourceDocId(); //save source id
                sd.setSourceDocId(null);
                Integer newSdId = ProjectService.getInstance().addSourceWithProject(newP, sd);
                sd = ProjectService.getInstance().getSingleSourceDoc(newSdId);
                SourceDoc oldSd = ProjectService.getInstance().getSingleSourceDoc(oldSdId);
                targetDocs = oldSd.getTargetDocs();
                
                //targets                 
                if(targetDocs != null) {
                    for(Iterator targetIter = targetDocs.iterator(); targetIter.hasNext();) {
                        TargetDoc td = (TargetDoc) targetIter.next();
                        Integer oldTdId = td.getTargetDocId(); //save target id
                        td.setTargetDocId(null);
                        Integer newTdId = ProjectService.getInstance().linkSourceDocTargetDoc(sd, td);
                        td = ProjectService.getInstance().getSingleTargetDoc(newTdId);
                        TargetDoc oldTd = ProjectService.getInstance().getSingleTargetDoc(oldTdId);
                        
                        //the 4 tasks (lin, eng, dtp, oth)
                        linTasks = oldTd.getLinTasks();                
                        if(linTasks != null) {
                            for(Iterator linIter = linTasks.iterator(); linIter.hasNext();) {
                                LinTask lt = (LinTask) linIter.next();
                                Integer oldLinId = lt.getLinTaskId(); //save id
                                lt.setLinTaskId(null);
                                lt.setSentDate(null);
                                lt.setDueDate(null);
                                lt.setReceivedDate(null);
                                lt.setInvoiceDate(null);
                                lt.setSentDateDate(null);
                                lt.setDueDateDate(null);
                                lt.setReceivedDate(null);
                                
                                lt.setReceivedDateDate(null);
                                
                                lt.setInvoiceDateDate(null);
                                lt.setPoNumber(null);
                                
                                /*lt.setNotes(null);
                                
                                lt.setScore(null);
                                lt.setPostQuote(null);
                                
                                
                                
                                
                                lt.setQuantity(null);
                                
                                lt.setWord100(new Integer(0));
                                lt.setWordRep(new Integer(0));
                                lt.setWord95(new Integer(0));
                                lt.setWord85(new Integer(0));
                                lt.setWord75(new Integer(0));
                                lt.setWordNew(new Integer(0));
                                lt.setWord8599(new Integer(0));
                                lt.setWordNew4(new Double(0));
                                lt.setWordTotal(new Double(0.0));
                                lt.setRate("0.000");
                                lt.setDollarTotal("0.000");
                                lt.setInternalRate("0.000");
                                lt.setInternalDollarTotal("0.000");
                                lt.setRate("0.000");    */                            
                                
                                Integer newLtId = ProjectService.getInstance().linkTargetDocLinTask(td, lt);
                            }
                        }
                        
                        engTasks = oldTd.getEngTasks();                
                        if(engTasks != null) {
                            for(Iterator engIter = engTasks.iterator(); engIter.hasNext();) {
                                EngTask et = (EngTask) engIter.next();
                                Integer oldEngId = et.getEngTaskId(); //save id
                                et.setEngTaskId(null);
                                et.setDueDate(null);
                                et.setReceivedDate(null);
                                et.setReceivedDate(null);
                                et.setSentDateDate(null);
                                et.setDueDateDate(null);
                                et.setReceivedDateDate(null);
                                et.setInvoiceDate(null);
                                et.setInvoiceDateDate(null);
                                et.setPoNumber(null);
                                
                                /*et.setNotes(null);
                                
                                et.setScore(null);
                                et.setPostQuote(null);
                                et.setSentDate(null);
                                
                               
                                
                                et.setQuantity(null);
                                et.setTotal(new Double(0.0));
                                et.setRate("0.000");
                                et.setDollarTotal("0.000");
                                et.setInternalRate("0.000");
                                et.setInternalDollarTotal("0.000");*/
                                
                                Integer newEtId = ProjectService.getInstance().linkTargetDocEngTask(td, et);
                            }
                        }
                        
                        dtpTasks = oldTd.getDtpTasks();                
                        if(dtpTasks != null) {
                            for(Iterator dtpIter = dtpTasks.iterator(); dtpIter.hasNext();) {
                                DtpTask dt = (DtpTask) dtpIter.next();
                                Integer oldDtpId = dt.getDtpTaskId(); //save id
                                dt.setDtpTaskId(null);
                                dt.setSentDate(null);
                                dt.setDueDate(null);
                                dt.setReceivedDate(null);
                                dt.setReceivedDate(null);
                                dt.setSentDateDate(null);
                                dt.setDueDateDate(null);
                                dt.setReceivedDateDate(null);
                                dt.setInvoiceDate(null);
                                dt.setInvoiceDateDate(null);
                                dt.setPoNumber(null);
                                /*dt.setNotes(null);
                                
                                dt.setScore(null);
                                dt.setPostQuote(null);
                                
                                dt.setQuantity(null);
                                dt.setTotal(new Double(0.0));
                                dt.setRate("0.000");
                                dt.setDollarTotal("0.000");
                                dt.setInternalRate("0.000");
                                dt.setInternalDollarTotal("0.000");*/
                                
                                Integer newDtId = ProjectService.getInstance().linkTargetDocDtpTask(td, dt);
                            }
                        }
                        
                        othTasks = oldTd.getOthTasks();                
                        if(othTasks != null) {
                            for(Iterator othIter = othTasks.iterator(); othIter.hasNext();) {
                                OthTask ot = (OthTask) othIter.next();
                                Integer oldOthId = ot.getOthTaskId(); //save id
                                ot.setOthTaskId(null);
                                ot.setSentDate(null);
                                ot.setDueDate(null);
                                ot.setReceivedDate(null);
                                ot.setReceivedDate(null);
                                ot.setSentDateDate(null);
                                ot.setDueDateDate(null);
                                ot.setReceivedDateDate(null);
                                ot.setInvoiceDate(null);
                                ot.setInvoiceDateDate(null);
                                ot.setPoNumber(null);
                                
                                /*ot.setNotes(null);
                                
                                ot.setScore(null);
                                ot.setPostQuote(null);
                                
                                ot.setQuantity(null);
                                ot.setTotal(new Double(0.0));
                                ot.setRate("0.000");
                                ot.setDollarTotal("0.000");
                                ot.setInternalRate("0.000");
                                ot.setInternalDollarTotal("0.000");*/
                                
                                Integer newOtId = ProjectService.getInstance().linkTargetDocOthTask(td, ot);
                            }
                        }
                        
                    }//end for(Iterator targetIter = targetDocs.iterator(); targetIter.hasNext();) {
                }//end if(targetDocs != null) {
            }//end for(Iterator iter = sourceDocs.iterator(); iter.hasNext();) {
        }//end if(sourceDocs != null) {        
        
        //START add Inspection list to this project
        Project pLazyLoad = ProjectService.getInstance().getSingleProject(p.getProjectId());
        String[] defaultInspections = ProjectService.getInstance().getDefaultInspectionOptions();
        String[] inspections = ProjectService.getInstance().getInspectionOptions();
        
        int j = 0;
        for(int i = 0; i < inspections.length; i++) {
            Inspection i2 = new Inspection();
            if(j < 7 && defaultInspections[j].equals(inspections[i])) { //if default inspection
                i2.setInDefault(true);
                i2.setApplicable(true);
                j++;
            }
            else {
               i2.setInDefault(false);
               i2.setApplicable(false);
            }
            i2.setOrderNum(new Integer(i+1));
            i2.setMilestone(inspections[i]);
            //upload to db
            ProjectService.getInstance().addInspectionWithProject(pLazyLoad, i2);
        }      
        //END add Inspection list to this project
                
        //add this project id to cookies; this will remember the last project
        request.setAttribute("projectViewId", String.valueOf(projectId));
        request.setAttribute("new", "Shallow");
        request.setAttribute("copyProject", pFrom.getNumber()+pFrom.getCompany().getCompany_code() );
        request.setAttribute("newProject", newP.getNumber()+newP.getCompany().getCompany_code());
        response.addCookie(StandardCode.getInstance().setCookie("projectViewId", String.valueOf(projectId)));
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
