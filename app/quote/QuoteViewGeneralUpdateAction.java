//QuoteViewGeneralUpdateAction.java gets updated quote info
//(from General Info tab) from a form.  It then uploads the new values for
//this quote to the db
package app.quote;

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
import org.apache.struts.validator.*;
import java.util.*;
import app.project.*;
import app.standardCode.*;
import app.security.*;

public final class QuoteViewGeneralUpdateAction extends Action {

    // ----------------------------------------------------- Instance Variables
    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log = LogFactory.getLog("org.apache.struts.webapp.Example");

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
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)

        //START get id of current quote from either request, attribute, or cookie
        //id of quote from request
        String quoteId = null;
        quoteId = request.getParameter("quoteViewId");

        //check attribute in request
        if (quoteId == null) {
            quoteId = (String) request.getAttribute("quoteViewId");
        }

        //id of quote from cookie
        if (quoteId == null) {
            quoteId = StandardCode.getInstance().getCookie("quoteViewId", request.getCookies());
        }

        //default client to first if not in request or cookie
        if (quoteId == null) {
            List results = QuoteService.getInstance().getQuoteList();
            Quote1 first = (Quote1) results.get(0);
            quoteId = String.valueOf(first.getQuote1Id());
        }

        Integer id = Integer.valueOf(quoteId);

        //END get id of current quote from either request, attribute, or cookie

        //get the quote to be updated from db
        Quote1 q = QuoteService.getInstance().getSingleQuote(id);
        //   //System.out.println("isTracked="+request.getParameter("isTracked"));
        if (request.getParameter("isTracked") != null) {
            q.setIsTracked("Y");
        } else {
            q.setIsTracked("N");
        }
        String rejectReason = request.getParameter("rejectReason");

        //values for update from form; change what is stored in db to these values
        DynaValidatorForm qvg = (DynaValidatorForm) form;

        //dollar totals of each block
        double linTotal = 0;
        double engTotal = 0;
        double dtpTotal = 0;
        double othTotal = 0;

         String linTotalString = "";
        String engTotalString = "";
        String dtpTotalString = "";
        String othTotalString = "";
        double pmTotal = 0;
        Map<String, Double> linRates = new HashMap<>();

        //START LIN TASKS
        //get the updated list of lin Tasks for this quote
        LinTask[] linTasks = (LinTask[]) qvg.get("linTasks");
        DtpTask[] dtpTasks = (DtpTask[]) qvg.get("dtpTasks");
        //  //System.out.println("---------------------------------------------------------------------------------->"+linTasks[0].getWord100());
        //process each lin Task to db
        for (int i = 0; i < linTasks.length; i++) {
            LinTask lt = linTasks[i];

            // //System.out.println("alexxx:before lt.getRate()="+lt.getRate());
            //   //System.out.println("alexxx:before lt.getCurrency()="+lt.getCurrency());
            lt.setInternalCurrency(lt.getCurrency());
            //START process new total values (2 of them: Total and dollar TOTAL)
            if ((lt.getWord100() != null) || (lt.getWordRep() != null) || (lt.getWord85() != null) || (lt.getWordNew() != null) || (lt.getWordNew4() != null)) { //trados
                int word100 = 0;
                int wordRep = 0;
                int word95 = 0;
                int word85 = 0;
                int word75 = 0;
                double wordNew = 0;
                int word8599 = 0;
                int wordPerfect = 0;
                int wordContext = 0;
                double wordNew4 = 0;

                if (lt.getWord100() != null) {
                    word100 = lt.getWord100();
                }
                if (lt.getWordRep() != null) {
                    wordRep = lt.getWordRep();
                }
                if (lt.getWord95() != null) {
                    word95 = lt.getWord95();
                }
                if (lt.getWord85() != null) {
                    word85 = lt.getWord85();
                }
                if (lt.getWord75() != null) {
                    word75 = lt.getWord75();
                }
                if (lt.getWordNew() != null) {
                    wordNew = lt.getWordNew();
                }
                if (lt.getWord8599() != null) {
                    word8599 = lt.getWord8599();
                }
                if (lt.getWordNew4() != null) {
                    wordNew4 = lt.getWordNew4();
                }
                if (lt.getWordContext() != null) {
                    wordPerfect = lt.getWordPerfect();
                }
                if (lt.getWordPerfect() != null) {
                    wordContext = lt.getWordContext();
                }
               


                double rate = 0;
                try {
                    rate = Double.valueOf(lt.getRate());
                    ////System.out.println("Rateeeeeeeeeeeee" + rate);
                } catch (java.lang.NumberFormatException nfe) {
                    rate = 0;
                }
                if(lt.getTaskName().contains("Translation")){
                    linRates.put(lt.getTargetDoc().getName(), rate);
                }
                if(ProjectHelper.isProcessRate(q.getProject().getCompany().getClientId(),lt)){
                    rate = linRates.getOrDefault(lt.getTargetDoc().getName(), 0.00)*ExcelConstants.CLIENT_BBS_PROOFREADING_RATE;
                }

                double wordTotal = 0;

                double thisTotal = 0.0;
                double scalePerfect=0,scaleContext=0;
                //scale the rates (either default or custom)
                if (q.getProject().getCompany().isScaleDefault(q.getProject().getProjectId())) {
                    double scale100 = new Double(q.getProject().getCompany().getScale100(q.getProject().getProjectId(),q.getProject().getCompany().getClientId()));
                    double scaleRep = new Double(q.getProject().getCompany().getScaleRep(q.getProject().getProjectId(),q.getProject().getCompany().getClientId()));
                    double scale8599 = new Double(q.getProject().getCompany().getScale8599());
                    double scaleNew4 = new Double(q.getProject().getCompany().getScaleNew4());
                    try{
                    scalePerfect = new Double(q.getProject().getCompany().getScalePerfect(q.getProject().getProjectId(),q.getProject().getCompany().getClientId()));
                    scaleContext = new Double(q.getProject().getCompany().getScaleContext(q.getProject().getProjectId(),q.getProject().getCompany().getClientId()));
                    } catch (Exception e) {
                    }
                    thisTotal = word100 * scale100 * rate + wordRep * scaleRep * rate + word8599 * scale8599 * rate + wordNew4 * scaleNew4 * rate + wordPerfect * scalePerfect * rate + wordContext * scaleContext* rate;
                    wordTotal = word100 + wordRep + word8599 + wordNew4 + wordPerfect + wordContext;
                } else {
                    double scale100 = new Double(q.getProject().getCompany().getScale100(q.getProject().getProjectId(),q.getProject().getCompany().getClientId()));
                    double scaleRep = new Double(q.getProject().getCompany().getScaleRep(q.getProject().getProjectId(),q.getProject().getCompany().getClientId()));
                    double scale95 = new Double(q.getProject().getCompany().getScale95(q.getProject().getProjectId(),q.getProject().getCompany().getClientId()));
                    double scale85 = new Double(q.getProject().getCompany().getScale85(q.getProject().getProjectId(),q.getProject().getCompany().getClientId()));
                    double scale75 = new Double(q.getProject().getCompany().getScale75(q.getProject().getProjectId(),q.getProject().getCompany().getClientId()));
                    double scaleNew = new Double(q.getProject().getCompany().getScaleNew(q.getProject().getProjectId(),q.getProject().getCompany().getClientId()));
                    try {
                    scalePerfect = new Double(q.getProject().getCompany().getScalePerfect(q.getProject().getProjectId(),q.getProject().getCompany().getClientId()));
                    scaleContext = new Double(q.getProject().getCompany().getScaleContext(q.getProject().getProjectId(),q.getProject().getCompany().getClientId()));
                    } catch (Exception e) {
                    }
                    thisTotal = word100 * scale100 * rate + wordRep * scaleRep * rate + word95 * scale95 * rate + word85 * scale85 * rate + word75 * scale75 * rate + wordNew * scaleNew * rate + wordPerfect * scalePerfect * rate + wordContext * scaleContext* rate;
                    wordTotal = word100 + wordRep + word95 + word85 + word75 + wordNew + wordPerfect + wordContext;
                }
                if (lt.getMinFee() != null) {
                    thisTotal += lt.getMinFee();
                }

                linTotal += Double.parseDouble((StandardCode.getInstance().formatDouble4(new Double(thisTotal))).replaceAll(",","")); //update lin block

                lt.setWordTotal(new Double(wordTotal));
                lt.setDollarTotal(StandardCode.getInstance().formatDouble4(new Double(thisTotal)));
                lt.setRate(StandardCode.getInstance().formatDouble4(new Double(rate)));
                // //System.out.println("alexxx:lt.getDollarTotalFee()="+lt.getDollarTotalFee());

                //if(lt.getDollarTotalFee()==null || "0.00".equals(lt.getDollarTotalFee())){
                //    //System.out.println("alexxx: updating from QuoteViewGeneralUpdateAction");
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
                lt.setCurrencyFee(lt.getCurrency());
                lt.setWordPerfectFee(lt.getWordPerfect());
                lt.setWordContextFee(lt.getWordContext());

                //lt.setRateFee(lt.getInternalRate());
                //lt.setDollarTotalFee( lt.getInternalDollarTotal());
                lt.setDollarTotalFee(lt.getDollarTotal());
                lt.setRateFee(lt.getRate());
                //lt.setRateFee(lt.getInternalRate());
                //????lt.setCurrencyFee(lt.getInternalCurrency());
                // }


            } else { //non-trados; such as hourly
                if (lt.getWordTotal() != null) { //if from delete then make sure it is not null


                    double total = lt.getWordTotal();
                    double rate = 0;
                    try {
                        rate = Double.valueOf(lt.getRate());
                    } catch (java.lang.NumberFormatException nfe) {
                        rate = 0;
                    }

                    double thisTotal = total * rate;
                    if (lt.getMinFee() != null) {
                        thisTotal += lt.getMinFee();
                    }
                    linTotal += Double.parseDouble((StandardCode.getInstance().formatDouble4(new Double(thisTotal))).replaceAll(",","")); //update lin block

                    lt.setDollarTotal(StandardCode.getInstance().formatDouble4(new Double(thisTotal)));
                    lt.setRate(StandardCode.getInstance().formatDouble4(new Double(rate)));

                    //Copy the values into Fee tab as well
                    ////System.out.println("alexxx:lt.getDollarTotalFee()="+lt.getDollarTotalFee());
                    // if(lt.getDollarTotalFee()==null || "0.00".equals(lt.getDollarTotalFee())){
                    ////System.out.println("alexxx: updating from QuoteViewGeneralUpdateAction");
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
                    lt.setWordPerfectFee(lt.getWordPerfect());
                    lt.setWordContextFee(lt.getWordContext());
                    //lt.setRateFee(lt.getInternalRate());
                    //lt.setDollarTotalFee( lt.getInternalDollarTotal());
                    lt.setDollarTotalFee(lt.getDollarTotal());
                    //lt.setRateFee(lt.getRate());
                    lt.setRateFee(lt.getInternalRate());
                    if(lt.getTaskName().contains("Translation")){
                        linRates.put(lt.getTargetDoc().getName(), rate);
                    }


                    //  }



                }
            }

            //END process new total values (2 of them)

            //update to db
            ProjectService.getInstance().updateLinTask(lt);

        }//END LIN TASKS

        //START ENG TASKS
        //get the updated list of eng Tasks for this quote
        EngTask[] engTasks = (EngTask[]) qvg.get("engTasks");

        //process each eng Task to db
        for (int i = 0; i < engTasks.length; i++) {
            EngTask et = engTasks[i];

            //START process new TOTAL value
            if (et.getTotal() != null) { //if from delete then make sure it is not null
                double total = et.getTotal();
                double rate = 0.00;
                try {
                    rate = Double.valueOf(et.getRate().replaceAll(",", ""));
                } catch (java.lang.NumberFormatException nfe) {
                   // nfe.printStackTrace();
                    rate = 0;
                }

                double thisTotal = total * rate;
                engTotal += thisTotal; //update eng block

                et.setDollarTotal(StandardCode.getInstance().formatDouble4(new Double(thisTotal)));
                et.setRate(StandardCode.getInstance().formatDouble4(new Double(rate)));
                et.setNotesTeam(et.getNotes());
                et.setTotalTeam(et.getTotal());
                et.setUnitsTeam(et.getUnits());

            }
            //END process new total value

            //update to db
            ProjectService.getInstance().updateEngTask(et);

        }//END ENG TASKS

        //START DTP TASKS
        //get the updated list of dtp Tasks for this quote
       

        //process each dtp Task to db
        for (int i = 0; i < dtpTasks.length; i++) {
            DtpTask dt = dtpTasks[i];

            //START process new TOTAL value
            if (dt.getTotal() != null) { //if from delete then make sure it is not null
                double total = dt.getTotal();
                double rate = 0;
                try {
                    rate = Double.valueOf(dt.getRate().replaceAll(",", ""));
                } catch (java.lang.NumberFormatException nfe) {
                    rate = 0;
                }

                double thisTotal = total * rate;
                dtpTotal += thisTotal; //update dtp block

                dt.setDollarTotal(StandardCode.getInstance().formatDouble4(new Double(thisTotal)));
                dt.setRate(StandardCode.getInstance().formatDouble4(new Double(rate)));

                dt.setNotesTeam(dt.getNotes());
                dt.setTotalTeam(dt.getTotal());
                dt.setUnitsTeam(dt.getUnits());

            }
            //END process new total value

            //update to db
            ProjectService.getInstance().updateDtpTask(dt);

        }//END DTP TASKS

        //START OTH TASKS
        //get the updated list of oth Tasks for this quote
        OthTask[] othTasks = (OthTask[]) qvg.get("othTasks");

        //process each oth Task to db
        for (int i = 0; i < othTasks.length; i++) {
            OthTask ot = othTasks[i];

            //START process new TOTAL value
            if (ot.getTotal() != null) { //if from delete then make sure it is not null
                double total = ot.getTotal();
                double rate = 0;
                try {
                    rate = Double.valueOf(ot.getRate());
                } catch (java.lang.NumberFormatException nfe) {
                    rate = 0;
                }

                double thisTotal = total * rate;
                othTotal += thisTotal; //update oth block

                ot.setDollarTotal(StandardCode.getInstance().formatDouble4(new Double(thisTotal)));
                ot.setRate(StandardCode.getInstance().formatDouble4(new Double(rate)));

                ot.setNotesTeam(ot.getNotes());
                ot.setTotalTeam(ot.getTotal());
                ot.setUnitsTeam(ot.getUnits());
            }
            //END process new total value

            //update to db
            ProjectService.getInstance().updateOthTask(ot);

        }//END OTH TASKS


        //START subTotal value
        double subTotal = 0.00;

                subTotal=linTotal + engTotal + dtpTotal + othTotal;
        q.setSubDollarTotal(StandardCode.getInstance().formatDouble(new Double(subTotal)));



        //END subTotal value

        //START pm block
//             <html:option value="0">Auto Calculate</html:option>
//            <html:option value="1">Manual Value</html:option>
//            <html:option value="2">Default Value</html:option>


        String manualPMFee=(String) qvg.get("manualPMFee");
  
        q.setManualPMFee(manualPMFee);
        

        //percent values from form
        String pmPercent = (String) qvg.get("pmPercent");
        String pmDollarTotal = ((String) qvg.get("pmPercentDollarTotal")).replaceAll(",", "");
        //if
        if(pmDollarTotal.isEmpty()) pmDollarTotal="0.00";

        double pmRate = 0;
        double pmPercentDollarTotal = 0;
        try {
            
        
        if(pmPercent!=null && !manualPMFee.equalsIgnoreCase("1")){

        pmRate=QuoteService.getInstance().getPmFee(subTotal);
         pmPercentDollarTotal = (pmRate / 100) * subTotal;
         q.setPmPercent(StandardCode.getInstance().formatDouble(pmRate));
          q.setPmPercentDollarTotal(StandardCode.getInstance().formatDouble(pmPercentDollarTotal));



        }else{
        if (pmPercent != null && !"".equals(pmPercent)) { //if pmPercent is present
            try {
                pmRate = Double.valueOf(pmPercent);
            } catch (java.lang.NumberFormatException nfe) {
                pmRate = 0;
            }
            pmPercentDollarTotal = (pmRate / 100) * subTotal;
            q.setPmPercentDollarTotal(StandardCode.getInstance().formatDouble(new Double(pmPercentDollarTotal)));
        q.setPmPercent(StandardCode.getInstance().formatDouble(new Double(pmRate)));
        } else if (pmDollarTotal != null && !"".equals(pmDollarTotal)) { //if rushPercent is present
            try {
                pmPercentDollarTotal = Double.valueOf(pmDollarTotal.replaceAll(",", ""));
                q.setPmPercentDollarTotal(StandardCode.getInstance().formatDouble(new Double(pmPercentDollarTotal)));
                q.setPmPercent(null);
              ///  pmRate=(pmPercentDollarTotal*100)/subTotal;
            } catch (java.lang.NumberFormatException nfe) {
                pmRate = 0;
            }
            //quoteTotal = quoteTotal - discountPercentDollarTotal;

        }}} catch (Exception e) {
            e.printStackTrace();
        }

        //set totals for each line to quote object
//        q.setPmPercentDollarTotal(StandardCode.getInstance().formatDouble(new Double(pmPercentDollarTotal)));
//        q.setPmPercent(StandardCode.getInstance().formatDouble(new Double(pmRate)));
        //END pm block


        //START sub total with pm block
        double subPmTotal = subTotal + pmPercentDollarTotal;
        q.setSubPmDollarTotal(StandardCode.getInstance().formatDouble(new Double(subPmTotal)));
        //END sub total with pm block


        //START rush block
        String rushPercent = (String) qvg.get("rushPercent");
        String rushTotal = (String) qvg.get("rushPercentDollarTotal");
        String archiveId = (String) qvg.get("archiveId");
        q.setArchiveId(archiveId);
        ////System.out.println(" q.setApprovalTimeEsimate q.setApprovalTimeEsimate" + request.getParameter("approvalTimeEsimate"));
        q.setApprovalTimeEsimate((String) qvg.get("approvalTimeEsimate"));

        double rushRate = 0;
        double rushPercentDollarTotal = 0;
        if (rushPercent != null && !"".equals(rushPercent)) { //if rushPercent is present
            try {
                rushRate = Double.valueOf(rushPercent);
            } catch (java.lang.NumberFormatException nfe) {
                rushRate = 0;
            }
            rushPercentDollarTotal = (rushRate / 100) * subPmTotal;
        } else if (rushTotal != null && !"".equals(rushTotal)) { //if rushPercent is present
            try {
                rushPercentDollarTotal = Double.valueOf(rushTotal.replaceAll(",", ""));
            } catch (java.lang.NumberFormatException nfe) {
                rushRate = 0;
            }
            //quoteTotal = quoteTotal - discountPercentDollarTotal;

        }

        q.setRushPercentDollarTotal(StandardCode.getInstance().formatDouble(new Double(rushPercentDollarTotal)));
        q.setRushPercent(StandardCode.getInstance().formatDouble(new Double(rushRate)));
        //END rush block

        double quoteTotal = subPmTotal + rushPercentDollarTotal;
        q.setSubDiscountDollarTotal(StandardCode.getInstance().formatDouble(new Double(quoteTotal)));
        //START DISCOUNT block
        String discountPercent = request.getParameter("discountPercent");
        String discountDollarTotal = request.getParameter("discountDollarTotal");
        ////System.out.println("discountPercent=" + discountPercent + ",discountDollarTotal=" + discountDollarTotal);
        double discountRate = 0;
        double discountPercentDollarTotal = 0;


        if (discountPercent != null && !"".equals(discountPercent)) { //if rushPercent is present
            try {
                discountRate = Double.valueOf(discountPercent);
            } catch (java.lang.NumberFormatException nfe) {
                discountRate = 0;
            }
            discountPercentDollarTotal = (discountRate / 100) * quoteTotal;


            quoteTotal = quoteTotal - (discountRate / 100) * quoteTotal;

        } else if (discountDollarTotal != null && !"".equals(discountDollarTotal)) { //if rushPercent is present
            try {
                discountPercentDollarTotal = Double.valueOf(discountDollarTotal.replaceAll(",", ""));
            } catch (java.lang.NumberFormatException nfe) {
                discountRate = 0;
            }
            quoteTotal = quoteTotal - discountPercentDollarTotal;

        }

        q.setDiscountDollarTotal(StandardCode.getInstance().formatDouble(new Double(discountPercentDollarTotal)));
        q.setDiscountPercent(StandardCode.getInstance().formatDouble(new Double(discountRate)));
        //END DISCOUNT block


        //START total value

        q.setQuoteDollarTotal(new Double(quoteTotal));
        //END total value
        if (request.getSession(false).getAttribute("username") != null) {
            q.setLastModifiedById((String) request.getSession(false).getAttribute("username"));
            q.setLastModifiedByTS(new Date());
        }
        if (rejectReason != null) {
            q.setRejectReason(rejectReason);
        }


        //update Quote to db
        QuoteService.getInstance().updateQuote(q,(String)request.getSession(false).getAttribute("username"));

        //mark AutoUpdate as true
        Integer autoUpdate = (Integer) request.getAttribute("AutoUpdate");
        if (autoUpdate != null) {
            Integer newAutoUpdate = new Integer(autoUpdate + 1);
            request.setAttribute("AutoUpdate", newAutoUpdate); //place true value (1) into request
        }

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
