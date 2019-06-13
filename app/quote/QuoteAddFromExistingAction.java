//QuoteAddFromExistingAction.java collects values related to 
//quote and project from an existing quote and adds it to the db

package app.quote;

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
import app.client.*;
import app.project.*;
import app.standardCode.*;
import app.security.*;

public final class QuoteAddFromExistingAction extends Action {


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
	
        //get quote to copy from
        String fromId = request.getParameter("id");
        Quote1 qFrom = QuoteService.getInstance().getSingleQuote(Integer.valueOf(fromId));
        
        //insert new project and quote into db, building one-to-many link between client and project
        //and between project and quote
        Client c = qFrom.getProject().getCompany();
        c = ClientService.getInstance().getSingleClient(c.getClientId());
        Integer quoteId = QuoteService.getInstance().addQuoteWithNewProject(c, ProjectService.getInstance().getNewProjectNumber());              
        
        ClientContact cc = ClientService.getInstance().getSingleClientContact(qFrom.getProject().getContact().getClientContactId());          
        Project p = QuoteService.getInstance().getSingleQuote(quoteId).getProject();
        //insert into db, building link between contact and project
        ProjectService.getInstance().linkProjectClientContact(p, cc);
       // if(qFrom.getNote() != null && (p.getNotes()==null||"".equals(p.getNotes()))) {
           // p.setNotes(qFrom.getNote());
        //}
        Project pFrom = qFrom.getProject();
        p.setPm(qFrom.getProject().getPm());
        
        p.setPmPercent(pFrom.getPmPercent());
        p.setRushPercent(pFrom.getRushPercent());
        p.setProductDescription(pFrom.getProductDescription());
        p.setProduct(pFrom.getProduct());
        p.setNotes(pFrom.getNotes());
        p.setProjectAmount(pFrom.getProjectAmount());
        p.setBeforeWorkTurn(pFrom.getBeforeWorkTurn());
        p.setBeforeWorkTurnUnits(pFrom.getBeforeWorkTurnUnits());
        p.setProjectRequirements(pFrom.getProjectRequirements());
        p.setProjectDescription(pFrom.getProjectDescription());
        p.setLinRequirements(pFrom.getLinRequirements());
        p.setDtpRequirements(pFrom.getDtpRequirements());
        p.setEngRequirements(pFrom.getEngRequirements());
        p.setOthRequirements(pFrom.getOthRequirements());
        p.setSourceApplication(pFrom.getSourceApplication());
        p.setSourceTechNotes(pFrom.getSourceTechNotes());
        p.setSourceOS(pFrom.getSourceOS());
        p.setSourceVersion(pFrom.getSourceVersion());
        p.setDeliverableApplication(pFrom.getDeliverableApplication());
        p.setDeliverableOS(pFrom.getDeliverableOS());
        p.setDeliverableSame(pFrom.getDeliverableSame());
        p.setDeliverableTechNotes(pFrom.getDeliverableTechNotes());
        p.setDeliverableVersion(pFrom.getDeliverableVersion());
        
        ProjectService.getInstance().updateProject(p);
        
        //update new quotes info
        Quote1 newQ = QuoteService.getInstance().getSingleQuote(quoteId);     
        newQ.setPmPercent(qFrom.getPmPercent());
        newQ.setRushPercent(qFrom.getRushPercent());
        newQ.setQuoteDollarTotal(qFrom.getQuoteDollarTotal());
        newQ.setSubDollarTotal(qFrom.getSubDollarTotal());
        newQ.setPublish(Boolean.TRUE);
        newQ.setQuoteDollarTotal(0.00);
        newQ.setSubDollarTotal("0.00");
        
        if(request.getSession(false).getAttribute("username")!=null){
         newQ.setEnteredById((String)request.getSession(false).getAttribute("username"));
         newQ.setEnteredByTS(new Date());
        }
        newQ.setQuoteDate(new Date());

        QuoteService.getInstance().updateQuote(newQ,(String)request.getSession(false).getAttribute("username"));
        Client_Quote fromcQuote = QuoteService.getInstance().get_SingleClientQuote(Integer.valueOf(fromId));
        Client_Quote cQuote = new Client_Quote();
        cQuote = fromcQuote;
        cQuote.setQuote_ID(quoteId);
        cQuote.setInstruction("");
        cQuote.setRequirement("");
        QuoteService.getInstance().updateClientQuote(cQuote);
        
        //add all sets, such as source doc, target doc, and the four tasks, as new objects to the new q
        Set sourceDocs = qFrom.getSourceDocs();
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
                Integer newSdId = QuoteService.getInstance().addSourceWithQuote1(newQ, sd);
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
                                lt.setNotes(null);
                                lt.setPoNumber(null);
                                lt.setScore(null);
                                lt.setPostQuote(null);
                                lt.setSentDate(null);
                                lt.setDueDate(null);
                                lt.setReceivedDate(null);
                                lt.setReceivedDate(null);
                                lt.setSentDateDate(null);
                                lt.setDueDateDate(null);
                                lt.setReceivedDateDate(null);
                                lt.setInvoiceDate(null);
                                lt.setInvoiceDateDate(null);
                                lt.setQuantity(null);
                                
                                lt.setWord100(new Integer(0));
                                lt.setWordRep(new Integer(0));
                                lt.setWord95(new Integer(0));
                                lt.setWord85(new Integer(0));
                                lt.setWord75(new Integer(0));
                                lt.setWordNew(new Double(0));
                                lt.setWord8599(new Integer(0));
                                lt.setWordNew4(new Double(0));
                                lt.setWordTotal(new Double(0.0));
//                                lt.setRate("0.000");
                                lt.setDollarTotal("0.000");
//                                lt.setInternalRate("0.000");
                                lt.setInternalDollarTotal("0.000");
//                                lt.setRate("0.000");     
                                
                                lt.setWord100Fee(new Integer(0));
                                lt.setWordRepFee(new Integer(0));
                                lt.setWord95Fee(new Integer(0));
                                lt.setWord85Fee(new Integer(0));
                                lt.setWord75Fee(new Integer(0));
                                lt.setWordNewFee(new Double(0));
                                lt.setWord8599Fee(new Integer(0));
                                lt.setWordNew4Fee(new Double(0));
                                lt.setWordTotalFee(new Double(0.0));
//                                lt.setRateFee("0.000");
                                lt.setDollarTotalFee("0.000");
//                                lt.setRateFee("0.000");                                
                                lt.setMinFee(new Double(0));
                                
                                Integer newLtId = ProjectService.getInstance().linkTargetDocLinTask(td, lt);
                            }
                        }
                        
                        engTasks = oldTd.getEngTasks();                
                        if(engTasks != null) {
                            for(Iterator engIter = engTasks.iterator(); engIter.hasNext();) {
                                EngTask et = (EngTask) engIter.next();
                                Integer oldEngId = et.getEngTaskId(); //save id
                                et.setEngTaskId(null);
                                et.setNotes(null);
                                et.setPoNumber(null);
                                et.setScore(null);
                                et.setPostQuote(null);
                                et.setSentDate(null);
                                et.setDueDate(null);
                                et.setReceivedDate(null);
                                et.setReceivedDate(null);
                                et.setSentDateDate(null);
                                et.setDueDateDate(null);
                                et.setReceivedDateDate(null);
                                et.setInvoiceDate(null);
                                et.setInvoiceDateDate(null);
                                et.setQuantity(null);
                                et.setTotal(new Double(0.0));
//                                et.setRate("0.000");
                                et.setDollarTotal("0.000");
//                                et.setInternalRate("0.000");
                                et.setInternalDollarTotal("0.000");
                                
                                Integer newEtId = ProjectService.getInstance().linkTargetDocEngTask(td, et);
                            }
                        }
                        
                        dtpTasks = oldTd.getDtpTasks();                
                        if(dtpTasks != null) {
                            for(Iterator dtpIter = dtpTasks.iterator(); dtpIter.hasNext();) {
                                DtpTask dt = (DtpTask) dtpIter.next();
                                Integer oldDtpId = dt.getDtpTaskId(); //save id
                                dt.setDtpTaskId(null);
                                dt.setNotes(null);
                                dt.setPoNumber(null);
                                dt.setScore(null);
                                dt.setPostQuote(null);
                                dt.setSentDate(null);
                                dt.setDueDate(null);
                                dt.setReceivedDate(null);
                                dt.setReceivedDate(null);
                                dt.setSentDateDate(null);
                                dt.setDueDateDate(null);
                                dt.setReceivedDateDate(null);
                                dt.setInvoiceDate(null);
                                dt.setInvoiceDateDate(null);
                                dt.setQuantity(null);
                                dt.setTotal(new Double(0.0));
//                                dt.setRate("0.000");
                                dt.setDollarTotal("0.000");
//                                dt.setInternalRate("0.000");
                                dt.setInternalDollarTotal("0.000");
                                
                                Integer newDtId = ProjectService.getInstance().linkTargetDocDtpTask(td, dt);
                            }
                        }
                        
                        othTasks = oldTd.getOthTasks();                
                        if(othTasks != null) {
                            for(Iterator othIter = othTasks.iterator(); othIter.hasNext();) {
                                OthTask ot = (OthTask) othIter.next();
                                Integer oldOthId = ot.getOthTaskId(); //save id
                                ot.setOthTaskId(null);
                                ot.setNotes(null);
                                ot.setPoNumber(null);
                                ot.setScore(null);
                                ot.setPostQuote(null);
                                ot.setSentDate(null);
                                ot.setDueDate(null);
                                ot.setReceivedDate(null);
                                ot.setReceivedDate(null);
                                ot.setSentDateDate(null);
                                ot.setDueDateDate(null);
                                ot.setReceivedDateDate(null);
                                ot.setInvoiceDate(null);
                                ot.setInvoiceDateDate(null);
                                ot.setQuantity(null);
                                ot.setTotal(new Double(0.0));
//                                ot.setRate("0.000");
                                ot.setDollarTotal("0.000");
//                                ot.setInternalRate("0.000");
                                ot.setInternalDollarTotal("0.000");
                                
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
                
        //add this quote id to cookies; this will remember the last quote
        request.setAttribute("quoteViewId", String.valueOf(quoteId));
        request.setAttribute("new", "Shallow");
        request.setAttribute("newQuote", newQ.getNumber());
        request.setAttribute("copyQuote", qFrom.getNumber());
        response.addCookie(StandardCode.getInstance().setCookie("quoteViewId", String.valueOf(quoteId)));
                
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
