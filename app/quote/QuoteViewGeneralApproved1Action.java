//QuoteViewGeneralApprovedAction.java prepares the quote
//and project for working on the project after a quote
//gets approved

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
import app.project.*;
import app.standardCode.*;
import app.security.*;
import app.client.*;


public final class QuoteViewGeneralApproved1Action extends Action {


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
	quoteId = request.getParameter("id");
        System.out.println("XXXXXXXX "+quoteId );
        //check attribute in request
        if(quoteId == null) {
            quoteId = (String) request.getAttribute("id");
           // sout
        }

        //id of quote from cookie
        if(quoteId == null) {
            quoteId = StandardCode.getInstance().getCookie("quoteViewId", request.getCookies());
        }


        Integer id = Integer.valueOf(quoteId);

        //END get id of current quote from either request, attribute, or cookie

        //get quote to and then its sources
        Quote1 q = QuoteService.getInstance().getSingleQuote(id);

        //update the quote and project status to reflect an active project
        q.setStatus("approved");
        q.setApprovalDate(new Date()); //set approval date to current date

        if(request.getSession(false).getAttribute("username")!=null){
             q.setLastModifiedById((String)request.getSession(false).getAttribute("username"));
             q.setLastModifiedByTS(new Date());
        }

        QuoteService.getInstance().updateQuote(q);
        Project p = ProjectService.getInstance().getSingleProject(q.getProject().getProjectId());
        p.setStatus("active");
        //update project dollar values (fee tab) to match quote
        if(q.getSubDollarTotal() != null) {
            p.setSubDollarTotal(q.getSubDollarTotal());
        }
        if(q.getSubPmDollarTotal() != null) {
            p.setSubPmDollarTotal(q.getSubPmDollarTotal());
        }
        if(q.getPmPercent() != null) {
            p.setPmPercent(q.getPmPercent());
        }
        if(q.getPmPercentDollarTotal() != null) {
            p.setPmPercentDollarTotal(q.getPmPercentDollarTotal());
        }
        if(q.getRushPercent() != null) {
            p.setRushPercent(q.getRushPercent());
        }
        if(q.getRushPercentDollarTotal() != null) {
            p.setRushPercentDollarTotal(q.getRushPercentDollarTotal());
        }
        if(q.getQuoteDollarTotal() != null) {
            p.setProjectAmount(q.getQuoteDollarTotal());
        }
        if(q.getNote() != null && (p.getNotes()==null||"".equals(p.getNotes()))){
            p.setNotes(q.getNote());
        }
        p.setNumber(ProjectService.getInstance().getNewProjectNumber());
        p.setStartDate(new Date());



        if(q.getIsTracked() != null) {
            p.setIsTracked(q.getIsTracked());

            if("Y".equals(p.getIsTracked())) {

                //If very first project for this client, create Master Access pwd
                if(p.getCompany().getTracker_password()==null){
                    String randomPwd = ProjectService.getInstance().generateRandomPassword(8);
                    p.getCompany().setTracker_password(randomPwd);
                    ClientService.getInstance().updateClient(p.getCompany());

                }
                //If very first project for this client contact, create Client Contact's pwd
                if(p.getContact().getTracker_password()==null){
                    String randomPwd = ProjectService.getInstance().generateRandomPassword(8);
                    p.getContact().setTracker_password(randomPwd);
                    ClientService.getInstance().clientContactUpdate(p.getContact());
                }
        }

        }

        ProjectService.getInstance().updateProject(p);

        //copy sourceDocs, targetDocs, and tasks form quote to project
        //add all sets, such as source doc, target doc, and the four tasks, as new objects to the active project
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
                sd.setQuote(null); //this is now part of project
                Integer newSdId = ProjectService.getInstance().addSourceWithProject(p, sd);
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

                               //Copy the values into Fee tab as well

                    //if(lt.getDollarTotalFee()==null || "0.00".equals(lt.getDollarTotalFee())){
                                    lt.setNotesFee(lt.getNotes());
                                    lt.setUnitsFee(lt.getUnits());
                                    lt.setWord100Fee(lt.getWord100());
                                    lt.setWordRepFee(lt.getWordRep());
                                    lt.setWord95Fee (lt.getWord95 ());
                                    lt.setWord85Fee(lt.getWord85());
                                    lt.setWord75Fee(lt.getWord75());
                                    lt.setWordNewFee(lt.getWordNew());
                                    lt.setWord8599Fee(lt.getWord8599());
                                    lt.setWordNew4Fee(lt.getWordNew4());
                                    lt.setWordTotalFee(lt.getWordTotal ());
                                    lt.setCurrencyFee(lt.getInternalCurrency());


                                    if(lt.getRate()==null || "0.00".equals(lt.getRate())){
                                        lt.setRateFee(lt.getInternalRate());
                                    }else{
                                        lt.setRateFee(lt.getRate());
                                    }


                                   double rate = 0;
                                   if(lt.getRateFee()!=null && !"".equals(lt.getRateFee())){
                                       rate = Double.valueOf(lt.getRateFee()).doubleValue();
                                   }



                            double rateNew4 = 0;
                            double rate100 = 0;
                            double rateRep = 0;
                            double rate8599 = 0;
                            /*double costNew4 = lt.getWordNew4().doubleValue() * rateNew4;
                            double cost100 = lt.getWord100().intValue()* rate100;
                            double costRep = lt.getWordRep().intValue() * rateRep;
                            double cost8599 = lt.getWord8599().intValue() * rate8599; */
                            double costNew4 = 0;
                            double cost100 = 0;
                            double costRep = 0;
                            double cost8599 = 0;

                            double thisTotal = costNew4 + cost100 + costRep + cost8599;




                             /*rate100 = rate * .3333;
                             rateRep = rate * .3333;
                             rate8599 = rate * .6666;
                             costNew4 = lt.getWordNew4().doubleValue() * rateNew4;
                             cost100 = lt.getWord100().intValue()* rate100;
                             costRep = lt.getWordRep().intValue() * rateRep;
                             cost8599 = lt.getWord8599().intValue() * rate8599;

                             thisTotal = costNew4 + cost100 + costRep + cost8599;
                           lt.setDollarTotalFee(StandardCode.getInstance().formatDouble(new Double(thisTotal))); */


                           //NEW PROCESSING
                int word100 = 0;
                int wordRep = 0;
                int word95 = 0;
                int word85 = 0;
                int word75 = 0;
                int wordNew = 0;
                int word8599 = 0;
                double wordNew4 = 0;

                if(lt.getWord100Fee() != null) {
                    word100 = lt.getWord100Fee().intValue();
                }
                if(lt.getWordRepFee() != null) {
                    wordRep = lt.getWordRepFee().intValue();
                }
                if(lt.getWord95Fee() != null) {
                    word95 = lt.getWord95Fee().intValue();
                }
                if(lt.getWord85Fee() != null) {
                    word85 = lt.getWord85Fee().intValue();
                }
                if(lt.getWord75Fee() != null) {
                    word75 = lt.getWord75Fee().intValue();
                }
                if(lt.getWordNewFee() != null) {
                    wordNew = lt.getWordNewFee().intValue();
                }
                if(lt.getWord8599Fee() != null) {
                    word8599 = lt.getWord8599Fee().intValue();
                }
                if(lt.getWordNew4Fee() != null) {
                    wordNew4 = lt.getWordNew4Fee().doubleValue();
                }
                double wordTotal   = 0;
                if(p.getCompany().isScaleDefault()) {
                    double scale100 = new Double(p.getCompany().getScale100()).doubleValue();
                    double scaleRep = new Double(p.getCompany().getScaleRep()).doubleValue();
                    double scale8599 = new Double(p.getCompany().getScale8599()).doubleValue();
                    double scaleNew4 = new Double(p.getCompany().getScaleNew4()).doubleValue();
                    thisTotal = word100*scale100*rate + wordRep*scaleRep*rate + word8599*scale8599*rate + wordNew4*scaleNew4*rate;
                    wordTotal = word100 + wordRep + word8599 + wordNew4;
                } else {
                    double scale100 = new Double(p.getCompany().getScale100()).doubleValue();
                    double scaleRep = new Double(p.getCompany().getScaleRep()).doubleValue();
                    double scale95 = new Double(p.getCompany().getScale95()).doubleValue();
                    double scale85 = new Double(p.getCompany().getScale85()).doubleValue();
                    double scale75 = new Double(p.getCompany().getScale75()).doubleValue();
                    double scaleNew = new Double(p.getCompany().getScaleNew()).doubleValue();
                    thisTotal = word100*scale100*rate + wordRep*scaleRep*rate + word95*scale95*rate + word85*scale85*rate + word75*scale75*rate + wordNew*scaleNew*rate;
                    wordTotal = word100 + wordRep + word95 + word85 + word75 + wordNew;
                }


                if(lt.getMinFee()!=null)
                    thisTotal+=lt.getMinFee().doubleValue();

                           lt.setDollarTotalFee(StandardCode.getInstance().formatDouble(new Double(thisTotal)));
                           lt.setDollarTotal(StandardCode.getInstance().formatDouble(new Double(thisTotal)));
                           lt.setWordTotalFee(new Double(wordTotal));
                           ProjectService.getInstance().updateLinTask(lt);

                         // }


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


        //add this project id to cookies and request; this will remember the new project for display
        response.addCookie(StandardCode.getInstance().setCookie("projectViewId", String.valueOf(p.getProjectId())));
        request.setAttribute("projectViewId", String.valueOf(p.getProjectId()));

	return (mapping.findForward("Success"));
    }

}
