//QuoteViewGeneralModifyAction.java gets the current
//quote from db and creates a new sub quote object with all
//the same values except for quote number.  this new
//quote is linked to the old quote though the old quote's project

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
import net.sf.hibernate.util.SerializationHelper;
import java.util.*;
import app.project.*;
import app.standardCode.*;
import app.security.*;


public final class QuoteViewGeneralModifyAction extends Action {


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
	
        //START get id of current quote from either request, attribute, or cookie 
        //id of quote from request
	String quoteId = null;
	quoteId = request.getParameter("quoteViewId");
        
        //check attribute in request
        if(quoteId == null) {
            quoteId = (String) request.getAttribute("quoteViewId");
        }
        
        //id of quote from cookie
        if(quoteId == null) {            
            quoteId = StandardCode.getInstance().getCookie("quoteViewId", request.getCookies());
        }
        
        
        Integer id = Integer.valueOf(quoteId);
        
        //END get id of current quote from either request, attribute, or cookie               
        
        //get quote to and then its sources
        Quote1 q = QuoteService.getInstance().getSingleQuote(id); 
        
        ////System.out.println("alexx:quoteId="+quoteId);
        // //System.out.println("alexx:oldNumber="+q.getQuote1Id());
        //create new sub quote from current quote (q)
        Quote1 newQ = (Quote1) SerializationHelper.clone(q);
        
        
        //null the id so it will be persisted to db
        newQ.setQuote1Id(null);
        //set new quote number
        String qNumber = QuoteService.getInstance().getNewQuoteNumber();
        newQ.setNumber(qNumber);
        ////System.out.println("alexx:newNumber="+qNumber);
        //set new quote date to current
        newQ.setQuoteDate(new Date());
        newQ.setSubQuoteId(quoteId);
        newQ.setSubquotes(quoteId);
        Integer newQId = QuoteService.getInstance().addNewQuote(newQ);
        newQ = QuoteService.getInstance().getSingleQuote(newQId);
       // //System.out.println("alexx:after addNewQuote newQId="+newQId);
        //add all sets, such as source doc, target doc, and the four tasks, as new objects to the new q
        Set sourceDocs = q.getSourceDocs();
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
                                Integer newLtId = ProjectService.getInstance().linkTargetDocLinTask(td, lt);
                            }
                        }
                        
                        engTasks = oldTd.getEngTasks();                
                        if(engTasks != null) {
                            for(Iterator engIter = engTasks.iterator(); engIter.hasNext();) {
                                EngTask et = (EngTask) engIter.next();
                                Integer oldEngId = et.getEngTaskId(); //save id
                                et.setEngTaskId(null);
                                Integer newEtId = ProjectService.getInstance().linkTargetDocEngTask(td, et);
                            }
                        }
                        
                        dtpTasks = oldTd.getDtpTasks();                
                        if(dtpTasks != null) {
                            for(Iterator dtpIter = dtpTasks.iterator(); dtpIter.hasNext();) {
                                DtpTask dt = (DtpTask) dtpIter.next();
                                Integer oldDtpId = dt.getDtpTaskId(); //save id
                                dt.setDtpTaskId(null);
                                Integer newDtId = ProjectService.getInstance().linkTargetDocDtpTask(td, dt);
                            }
                        }
                        
                        othTasks = oldTd.getOthTasks();                
                        if(othTasks != null) {
                            for(Iterator othIter = othTasks.iterator(); othIter.hasNext();) {
                                OthTask ot = (OthTask) othIter.next();
                                Integer oldOthId = ot.getOthTaskId(); //save id
                                ot.setOthTaskId(null);
                                Integer newOtId = ProjectService.getInstance().linkTargetDocOthTask(td, ot);
                            }
                        }
                        
                    }//end for(Iterator targetIter = targetDocs.iterator(); targetIter.hasNext();) {
                }//end if(targetDocs != null) {
            }//end for(Iterator iter = sourceDocs.iterator(); iter.hasNext();) {
        }//end if(sourceDocs != null) {
        
                 Client_Quote cq = QuoteService.getInstance().get_SingleClientQuote(id);
       if(null != cq){
        Client_Quote newCQ = (Client_Quote) SerializationHelper.clone(cq);
        newCQ.setQuote_ID(newQ.getQuote1Id());
       
        QuoteService.getInstance().updateClientQuote(newCQ);
       }
//        q.setSubQuoteId(quoteId);
//        QuoteService.getInstance().updateQuote(q);
        //add this quote id to cookies; this will remember the last quote
        request.setAttribute("quoteViewId", String.valueOf(newQId));
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
