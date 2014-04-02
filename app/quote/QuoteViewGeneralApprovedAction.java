//QuoteViewGeneralApprovedAction.java prepares the quote
//and project for working on the project after a quote
//gets approved
package app.quote;

import app.admin.AdminService;
import app.admin.Ticker;
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
import app.project.*;
import app.standardCode.*;
import app.security.*;
import app.client.*;
import app.tools.SendEmail;

public final class QuoteViewGeneralApprovedAction extends Action {

    // ----------------------------------------------------- Instance Variables
    /**
     * The
     * <code>Log</code> instance for this application.
     */
    private Log log =
            LogFactory.getLog("org.apache.struts.webapp.Example");

    // --------------------------------------------------------- Public Methods
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an
     * <code>ActionForward</code> instance describing where and how control
     * should be forwarded, or
     * <code>null</code> if the response has already been completed.
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
        Runtime r = Runtime.getRuntime();
        // save errors
        ActionMessages errors = new ActionMessages();

        //START check for login (security)
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)

        //START get id of current quote from either request, attribute, or cookie
        //id of quote from request
        String quoteId = null;
        quoteId = request.getParameter("id");
        System.out.println("XXXXXXXX " + quoteId);
        //check attribute in request
        if (quoteId == null) {
            try {
                quoteId = (String) request.getAttribute("id");
            } catch (Exception e) {
            }
            
        }

        //id of quote from cookie
        if (quoteId == null) {
            quoteId = StandardCode.getInstance().getCookie("quoteViewId", request.getCookies());
        }


        Integer id = Integer.valueOf(quoteId);

        //END get id of current quote from either request, attribute, or cookie

        //get quote to and then its sources
        List typeOfTextList = new ArrayList<String>();
        String typeOfText = "";
        Quote1 q = QuoteService.getInstance().getSingleQuote(id);
        List clientQuoteList = QuoteService.getInstance().getClient_Quote(q.getQuote1Id());
        for (int i = 0; i < clientQuoteList.size(); i++) {
            Client_Quote clientQuote = (Client_Quote) clientQuoteList.get(i);
            if (null != clientQuote.getTypeOfText()) {
                if (!clientQuote.getTypeOfText().equalsIgnoreCase("") && !typeOfTextList.contains(clientQuote.getTypeOfText())) {

                    typeOfTextList.add(clientQuote.getTypeOfText());
                    typeOfText += clientQuote.getTypeOfText() + ",";
                }
            }
        }
        try {
            typeOfText = typeOfText.substring(0, typeOfText.length() - 1);
        } catch (Exception e) {
        }


        //update the quote and project status to reflect an active project
        q.setStatus("approved");
        q.setApprovalDate(new Date()); //set approval date to current date

        if (request.getSession(false).getAttribute("username") != null) {
            q.setLastModifiedById((String) request.getSession(false).getAttribute("username"));
            q.setLastModifiedByTS(new Date());
        }

        QuoteService.getInstance().updateQuote(q);
        Project p = ProjectService.getInstance().getSingleProject(q.getProject().getProjectId());
        try {
            List subQuoteList = QuoteService.getInstance().getSubQuoteFromProject(p.getProjectId());
            for (int i = 0; i < subQuoteList.size(); i++) {
                Quote1 subQ = (Quote1) subQuoteList.get(i);
                if (subQ.getQuote1Id() != id && !subQ.getStatus().equalsIgnoreCase("approved")) {
                    subQ.setStatus("rejected");
                    subQ.setRejectReason("Was a SubQuote");
                    subQ.setApprovalDate(new Date());
                    QuoteService.getInstance().updateQuote(subQ);

                    r.gc();
                }
            }

        } catch (Exception e) {
        }

//        Project pLazyLoad = ProjectService.getInstance().getSingleProject(q.getProject().getProjectId());
//        Set subQuotes = pLazyLoad.getQuotes();  
//        boolean firstQuote = true;
//for(Iterator iter = subQuotes.iterator(); iter.hasNext();) {
//    Quote1 q1 = (Quote1) iter.next();

        p.setStatus("active");
        //update project dollar values (fee tab) to match quote
        if (q.getSubDollarTotal() != null) {
            p.setSubDollarTotal(q.getSubDollarTotal());
        }
        if (q.getSubPmDollarTotal() != null) {
            p.setSubPmDollarTotal(q.getSubPmDollarTotal());
        }
        if (q.getPmPercent() != null) {
            p.setPmPercent(q.getPmPercent());
        }
        if (q.getPmPercentDollarTotal() != null) {
            p.setPmPercentDollarTotal(q.getPmPercentDollarTotal());
        }
        if (q.getRushPercent() != null) {
            p.setRushPercent(q.getRushPercent());
        }
        if (q.getRushPercentDollarTotal() != null) {
            p.setRushPercentDollarTotal(q.getRushPercentDollarTotal());
        }
        Double EuroToDollarConversion = 1.41;
        try {
            EuroToDollarConversion = StandardCode.getInstance().getEuro();
            p.setEuroToUsdExchangeRate(EuroToDollarConversion);
        } catch (Exception e) {
        }
        if (q.getQuoteDollarTotal() != null) {
            if (p.getCompany().getCcurrency().equalsIgnoreCase("EURO")) {
                p.setProjectAmount(q.getQuoteDollarTotal() * EuroToDollarConversion);
            } else {
                p.setProjectAmount(q.getQuoteDollarTotal());
            }
        }
        try {
            if (q.getNote() != null && (p.getNotes() == null || "".equals(p.getNotes()))) {
                p.setNotes(q.getNote());
            } else {
                String temp = q.getNote().concat(p.getNotes());
                p.setNotes(temp);
            }
        } catch (Exception e) {
        }

        //  p.setNumber("100000");
        p.setStartDate(new Date());
        p.setIndependent(false);
        p.setClientSatisfaction(95.00);
        p.setTypeOfText(typeOfText);




        try {
            String[] fname = p.getPm().split(" ");
            User pm = null;
            if (fname.length == 2) {
                pm = UserService.getInstance().getSingleUserRealName(fname[0], fname[1]);
            } else if (fname.length == 3) {
                pm = UserService.getInstance().getSingleUserRealName(fname[0], fname[1] + " " + fname[2]);
            } else if (fname.length == 4) {
                pm = UserService.getInstance().getSingleUserRealName(fname[0], fname[1] + " " + fname[2] + " " + fname[3]);
            }
            p.setPm_id(pm.getUserId());
        } catch (Exception e) {
            p.setPm_id(0);
        }


        if (q.getIsTracked() != null) {
            p.setIsTracked(q.getIsTracked());

            if ("Y".equals(p.getIsTracked())) {

                //If very first project for this client, create Master Access pwd
                if (p.getCompany().getTracker_password() == null) {
                    String randomPwd = ProjectService.getInstance().generateRandomPassword(8);
                    p.getCompany().setTracker_password(randomPwd);
                    ClientService.getInstance().updateClient(p.getCompany());

                }
                //If very first project for this client contact, create Client Contact's pwd
                if (p.getContact().getTracker_password() == null) {
                    String randomPwd = ProjectService.getInstance().generateRandomPassword(8);
                    p.getContact().setTracker_password(randomPwd);
                    ClientService.getInstance().clientContactUpdate(p.getContact());
                }
            }

        }

        ProjectService.getInstance().updateProject(p);
        // r.gc();
        //       p.setNumber("200000");
        //    p.setNumber(ProjectService.getInstance().getNewProjectNumber());
        //    ProjectService.getInstance().updateProject(p);
        //   r.gc();
        //copy sourceDocs, targetDocs, and tasks form quote to project
        //add all sets, such as source doc, target doc, and the four tasks, as new objects to the active project
        Set sourceDocs = q.getSourceDocs();
        Set targetDocs = null;
        Set linTasks = null;
        Set engTasks = null;
        Set dtpTasks = null;
        Set othTasks = null;
        String sourceLang = "";
        String targetLang = "";




        //sources
        if (sourceDocs != null) {
            targetLang = "";
            for (Iterator iter = sourceDocs.iterator(); iter.hasNext();) {
                SourceDoc sd = (SourceDoc) iter.next();
                Integer oldSdId = sd.getSourceDocId(); //save source id
                sd.setSourceDocId(null);
                sd.setQuote(null); //this is now part of project
                Integer newSdId = ProjectService.getInstance().addSourceWithProject(p, sd);
                sd = ProjectService.getInstance().getSingleSourceDoc(newSdId);
                SourceDoc oldSd = ProjectService.getInstance().getSingleSourceDoc(oldSdId);
                targetDocs = oldSd.getTargetDocs();
                sourceLang += sd.getLanguage() + "  ";

                //targets
                if (targetDocs != null) {
                    for (Iterator targetIter = targetDocs.iterator(); targetIter.hasNext();) {
                        TargetDoc td = (TargetDoc) targetIter.next();
                        Integer oldTdId = td.getTargetDocId(); //save target id
                        td.setTargetDocId(null);
                        Integer newTdId = ProjectService.getInstance().linkSourceDocTargetDoc(sd, td);
                        td = ProjectService.getInstance().getSingleTargetDoc(newTdId);
                        TargetDoc oldTd = ProjectService.getInstance().getSingleTargetDoc(oldTdId);
                        targetLang += td.getLanguage() + "  ";
                        //the 4 tasks (lin, eng, dtp, oth)
                        linTasks = oldTd.getLinTasks();
                        if (linTasks != null) {
                            for (Iterator linIter = linTasks.iterator(); linIter.hasNext();) {
                                LinTask lt = (LinTask) linIter.next();

                                //Copy the values into Fee tab as well

                                //if(lt.getDollarTotalFee()==null || "0.00".equals(lt.getDollarTotalFee())){
                                lt.setNotesFee(lt.getNotes());
                                lt.setUnitsFee(lt.getUnits());
                                lt.setWord100Fee(lt.getWord100());
                                lt.setWordRepFee(lt.getWordRep());
                                lt.setWord95Fee(lt.getWord95());
                                lt.setWord85Fee(lt.getWord85());
                                lt.setWord75Fee(lt.getWord75());
                                lt.setWordNewFee(lt.getWordNew());
                                lt.setWord8599Fee(lt.getWord8599());
                                lt.setWordNew4Fee(lt.getWordNew4());
                                lt.setWordTotalFee(lt.getWordTotal());
                                lt.setCurrencyFee(lt.getInternalCurrency());
                                lt.setMinFeeRsf(lt.getMinFee());
                                //  lt.setMinFee(0.00);



                                if (lt.getRate() == null || "0.00".equals(lt.getRate())) {
                                    lt.setRateFee(lt.getInternalRate());
                                } else {
                                    lt.setRateFee(lt.getRate());
                                }


                                double rate = 0;
                                if (lt.getRateFee() != null && !"".equals(lt.getRateFee())) {
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
                                int wordPerfect = 0;
                                int wordContext = 0;
                                double wordNew4 = 0;

                                if (lt.getWord100Fee() != null) {
                                    word100 = lt.getWord100Fee().intValue();
                                }
                                if (lt.getWordRepFee() != null) {
                                    wordRep = lt.getWordRepFee().intValue();
                                }
                                if (lt.getWord95Fee() != null) {
                                    word95 = lt.getWord95Fee().intValue();
                                }
                                if (lt.getWord85Fee() != null) {
                                    word85 = lt.getWord85Fee().intValue();
                                }
                                if (lt.getWord75Fee() != null) {
                                    word75 = lt.getWord75Fee().intValue();
                                }
                                if (lt.getWordNewFee() != null) {
                                    wordNew = lt.getWordNewFee().intValue();
                                }
                                if (lt.getWord8599Fee() != null) {
                                    word8599 = lt.getWord8599Fee().intValue();
                                }
                                if (lt.getWordNew4Fee() != null) {
                                    wordNew4 = lt.getWordNew4Fee().doubleValue();
                                }
                                if (lt.getWordContext() != null) {
                                    wordPerfect = lt.getWordPerfect().intValue();
                                }
                                if (lt.getWordPerfect() != null) {
                                    wordContext = lt.getWordContext().intValue();
                                }
                                double wordTotal = 0;
                                double scalePerfect = 0, scaleContext = 0;
                                if (p.getCompany().isScaleDefault()) {
                                    double scale100 = new Double(p.getCompany().getScale100()).doubleValue();
                                    double scaleRep = new Double(p.getCompany().getScaleRep()).doubleValue();
                                    double scale8599 = new Double(p.getCompany().getScale8599()).doubleValue();
                                    double scaleNew4 = new Double(p.getCompany().getScaleNew4()).doubleValue();
                                    try {
                                        scalePerfect = new Double(q.getProject().getCompany().getScalePerfect()).doubleValue();
                                        scaleContext = new Double(q.getProject().getCompany().getScaleContext()).doubleValue();
                                    } catch (Exception e) {
                                    }
                                    thisTotal = word100 * scale100 * rate + wordRep * scaleRep * rate + word8599 * scale8599 * rate + wordNew4 * scaleNew4 * rate + wordPerfect * scalePerfect * rate + wordContext * scaleContext * rate;
                                    wordTotal = word100 + wordRep + word8599 + wordNew4;
                                } else {
                                    double scale100 = new Double(p.getCompany().getScale100()).doubleValue();
                                    double scaleRep = new Double(p.getCompany().getScaleRep()).doubleValue();
                                    double scale95 = new Double(p.getCompany().getScale95()).doubleValue();
                                    double scale85 = new Double(p.getCompany().getScale85()).doubleValue();
                                    double scale75 = new Double(p.getCompany().getScale75()).doubleValue();
                                    double scaleNew = new Double(p.getCompany().getScaleNew()).doubleValue();
                                    try {
                                        scalePerfect = new Double(q.getProject().getCompany().getScalePerfect()).doubleValue();
                                        scaleContext = new Double(q.getProject().getCompany().getScaleContext()).doubleValue();
                                    } catch (Exception e) {
                                    }
                                    thisTotal = word100 * scale100 * rate + wordRep * scaleRep * rate + word95 * scale95 * rate + word85 * scale85 * rate + word75 * scale75 * rate + wordNew * scaleNew * rate + wordPerfect * scalePerfect * rate + wordContext * scaleContext * rate;
                                    wordTotal = word100 + wordRep + word95 + word85 + word75 + wordNew;
                                }
                                request.setAttribute("wordTotal", "" + wordTotal);

                                if (lt.getMinFee() != null) {
                                    thisTotal += lt.getMinFee().doubleValue();
                                }

                                lt.setDollarTotalFee(StandardCode.getInstance().formatDouble(new Double(thisTotal)));
                                lt.setDollarTotal(StandardCode.getInstance().formatDouble(new Double(thisTotal)));
                                lt.setWordTotalFee(new Double(wordTotal));
                                ProjectService.getInstance().updateLinTask(lt);

                                // }


                                Integer oldLinId = lt.getLinTaskId(); //save id
                                lt.setLinTaskId(null);
                                Integer newLtId = ProjectService.getInstance().linkTargetDocLinTask(td, lt);
                                LinTask newlt = ProjectService.getInstance().getSingleLinTask(newLtId);
                                newlt.setMinFee(0.00);
                                ProjectService.getInstance().updateLinTask(newlt);


                            }
                        }

                        engTasks = oldTd.getEngTasks();
                        if (engTasks != null) {
                            for (Iterator engIter = engTasks.iterator(); engIter.hasNext();) {
                                EngTask et = (EngTask) engIter.next();
                                Integer oldEngId = et.getEngTaskId(); //save id
                                et.setEngTaskId(null);
                                Integer newEtId = ProjectService.getInstance().linkTargetDocEngTask(td, et);
                            }
                        }

                        dtpTasks = oldTd.getDtpTasks();
                        if (dtpTasks != null) {
                            for (Iterator dtpIter = dtpTasks.iterator(); dtpIter.hasNext();) {
                                DtpTask dt = (DtpTask) dtpIter.next();
                                Integer oldDtpId = dt.getDtpTaskId(); //save id
                                dt.setDtpTaskId(null);
                                Integer newDtId = ProjectService.getInstance().linkTargetDocDtpTask(td, dt);
                            }
                        }

                        othTasks = oldTd.getOthTasks();
                        if (othTasks != null) {
                            for (Iterator othIter = othTasks.iterator(); othIter.hasNext();) {
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
        String toAddress = "", approvalDate="";
        String pmName="",aeName="";
        User pm = UserService.getInstance().getSingleUser(p.getPm_id());
        QuotePriority qpl = QuoteService.getInstance().getSingleQuotePriority(q.getQuote1Id());
        qpl.setPriority(0);
        QuoteService.getInstance().addQuotePriority(qpl);
        try {

            request.setAttribute("pm", pm.getWorkEmail1());
            pmName = pm.getFirstName() + " " + pm.getLastName();
            if(!StandardCode.getInstance().noNull(pm.getWorkEmail1()).equalsIgnoreCase("")){
        toAddress +=  "," + StandardCode.getInstance().noNull(pm.getWorkEmail1());}
        } catch (Exception e) {

            request.setAttribute("pm", "excelnet@xltrans.com");
        }

        String[] fname = p.getCompany().getSales_rep().split(" ");
        String lName = p.getCompany().getSales_rep().replaceAll(fname[0], "").trim();
        r.gc();
        User ae = UserService.getInstance().getSingleUserRealName(fname[0], lName);
        p.setNumber(ProjectService.getInstance().getNewProjectNumber());
        ProjectService.getInstance().updateProject(p);

        try {
            QuotePriority qp = QuoteService.getInstance().getSingleQuotePriority(q.getQuote1Id());
            qp.setPriority(0);
            QuoteService.getInstance().addQuotePriority(qp);
        } catch (Exception e) {
        }

        try {

            request.setAttribute("ae", ae.getWorkEmail1());
            aeName = ae.getFirstName() + " " + ae.getLastName();
            if(!StandardCode.getInstance().noNull(ae.getWorkEmail1()).equalsIgnoreCase("")){
            toAddress +=  "," + StandardCode.getInstance().noNull(ae.getWorkEmail1());}
        } catch (Exception e) {
             request.setAttribute("ae", "");
           
        }
        request.setAttribute("ae", ae.getWorkEmail1());
        //add this project id to cookies and request; this will remember the new project for display
        response.addCookie(StandardCode.getInstance().setCookie("projectViewId", String.valueOf(p.getProjectId())));
        request.setAttribute("projectViewId", String.valueOf(p.getProjectId()));
        request.setAttribute("projectAmount", p.getCompany().getCcurrency() + " " + StandardCode.getInstance().formatDouble(q.getQuoteDollarTotal()));
        request.setAttribute("sourceLang", sourceLang);
        request.setAttribute("targetLang", targetLang);
        request.setAttribute("pNumber", p.getNumber() + p.getCompany().getCompany_code());
        request.setAttribute("qNumber", q.getNumber());
        request.setAttribute("clientName", p.getCompany().getCompany_name());
        request.setAttribute("approvedDate", "" + new Date());
        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
        request.setAttribute("approvedBy", u.getFirstName() + " " + u.getLastName());
        
        String emailMsgTxt = "";
        try {
            String[] approvalDateArray = q.getApprovalDate().toString().split(" ");
            approvalDate = approvalDateArray[0]+" "+approvalDateArray[1]+" "+approvalDateArray[2]+" "+approvalDateArray[5]+" "
                    + "("+approvalDateArray[3]+" "+approvalDateArray[4]+") ";
        
        } catch (Exception e) {
        }
        String emailSubjectTxt = "Quote Approval: " + q.getNumber() + " (project " + p.getNumber() + p.getCompany().getCompany_code() + ")";
        // Forward control to the specified success URI
        if (u.getuserType() != null && u.getuserType().equalsIgnoreCase("client")) {

            emailSubjectTxt += " by " + u.getFirstName() + " " + u.getLastName() + "(" + p.getCompany().getCompany_name() + ")";
            if(!StandardCode.getInstance().noNull(u.getWorkEmail1()).equalsIgnoreCase("")){
             toAddress += ","+  StandardCode.getInstance().noNull(u.getWorkEmail1()) ;}
            if(null != p.getCompany().getAuto_alert()){
              toAddress += ","+  p.getCompany().getAuto_alert();
            }
            
            
        
        
   
            emailMsgTxt = "<div>Hello,</div><div>&nbsp;</div><div>This message is automatically generated by ExcelNet."
                    + "Please do not reply to this email (contact your account or project manager directly if you have any questions or concerns).</div><div>&nbsp;</div>"
                    + "<div><strong>" + q.getNumber() + " </strong>has been approved: (project " + p.getNumber() + p.getCompany().getCompany_code() + ")"
                    + "</div><div>&nbsp;</div><div>=============================</div><div>&nbsp;</div>"
                    + "<div><table><tr><td style='padding-right: 15px;'>AE: </td><td>" + aeName + "</td></tr>"
                    + "<tr><td>PM:</td><td> " + pmName + "</td></tr>"
                    + "<tr><td>&nbsp;</td></tr>"
                    + "<tr><td>Approval Date: </td><td>" + approvalDate + "</td></tr>"
                    + "<tr><td>Approved by: </td><td>" + u.getFirstName() + " " + u.getLastName() + "</td></tr>"
                    + "<tr><td>&nbsp;</td></tr><div>Source: </td><td> " + sourceLang + "</td></tr>"
                    + "<tr><td>Target:</td><td> " + targetLang + "</td></tr>"
                    + "<tr><td>&nbsp;</td></tr><tr><td>Client Fee:</td><td> " + p.getCompany().getCcurrency() + " " + StandardCode.getInstance().formatDouble(q.getQuoteDollarTotal()) + "</td></tr></table></div><div>&nbsp;</div><div>=============================</div>"
                    + "<div>&nbsp;</div><div>Best regards,</div>"
                    + "<div>&nbsp;</div><div>ExcelNet Administrator</div><div><img src=http://excelnet.xltrans.com/logo/images/-1168566039logoExcel.gif><div>";
        } else {
            emailMsgTxt = "<div>Hello,</div><div>&nbsp;</div><div>This message is automatically generated by ExcelNet.</div><div>&nbsp;</div>"
                    + "<div><strong>" + q.getNumber() + " </strong>has been approved: (project " + p.getNumber() + p.getCompany().getCompany_code() + ")"
                    + "</div><div>&nbsp;</div><div>=============================</div><div>&nbsp;</div>"
                    + "<div><table><tr><td style='padding-right: 15px;'>AE: </td><td>" + aeName + "</td></tr>"
                    + "<tr><td>PM:</td><td> " + pmName + "</td></tr>"
                    + "<tr><td>&nbsp;</td></tr>"
                    + "<tr><td>Approval Date:</td><td>" + approvalDate + "</td></tr>"
                    + "<tr><td>Approved by:</td><td> " + u.getFirstName() + " " + u.getLastName() + "</td></tr>"
                    + "<tr><td>&nbsp;</div><tr><td>Source: </td><td> " + sourceLang + "</td></tr>"
                    + "<tr><td>Target:</td><td>" + targetLang + "</td></tr>"
                    + "<tr><td>&nbsp;</td></tr><tr><td>Client Fee:</td><td>" + p.getCompany().getCcurrency() + " " + StandardCode.getInstance().formatDouble(q.getQuoteDollarTotal()) + "</td></tr></table></div><div>&nbsp;</div><div>=============================</div>"
                    + "<div>&nbsp;</div><div>Best regards,</div>"
                    + "<div>&nbsp;</div><div>ExcelNet Administrator</div><div><img src=http://excelnet.xltrans.com/logo/images/-1168566039logoExcel.gif><div>";
           
        }
            
            List tickerList = AdminService.getInstance().getTickerList(30);
            for (int i = 0; i < tickerList.size(); i++) {
                Ticker ticker = (Ticker) tickerList.get(i);
                if (toAddress.equalsIgnoreCase("")) {
                    try { 
                        toAddress = ticker.getUserEmail();
                    } catch (Exception e) {
                    }
                } else {
                    try {
                        toAddress += "," + ticker.getUserEmail();
                    } catch (Exception e) {
                    }
                }
            }
        
//       toAddress = "niteshwarkumar@gmail.com,niteshwarkumar@gmail.com,niteshwarkumar@gmail.com,niteshwarkumar@gmail.com,niteshwarkumar@gmail.com";
//        toAddress +="";
        String[] emailList = toAddress.split(",");
        
        Set<String> temp = new HashSet<String>(Arrays.asList(emailList));
        temp.remove("");
        emailList = temp.toArray(new String[temp.size()]);
        
        SendEmail smtpMailSender = new SendEmail();

        smtpMailSender.postMail(emailList, emailSubjectTxt, emailMsgTxt,
                "excelnet@xltrans.com");
        if (u.getuserType()
                != null && u.getuserType().equalsIgnoreCase("client")) {
            return (mapping.findForward("Client"));
        } else {
            return (mapping.findForward("Success"));
        }
    }
}