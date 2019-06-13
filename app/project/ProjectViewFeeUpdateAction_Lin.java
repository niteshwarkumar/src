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
import app.standardCode.*;
import app.user.User;
import app.user.UserService;
import org.apache.struts.validator.*;



public final class ProjectViewFeeUpdateAction_Lin extends Action {


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

        if(request.getParameter("euroToUsdExchangeRate")!=null){
            p.setEuroToUsdExchangeRate(new Double(request.getParameter("euroToUsdExchangeRate")));
        }

        ProjectService.getInstance().updateProject(p);

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
        double linCurrencyTotal=0;
        Map<String, Double> linRates = new HashMap<>();
        //START LIN TASKS
        //get the updated list of lin Tasks for this project
        LinTask[] linTasks = (LinTask[]) qvg.get("linTasksProject");

        //process each lin Task to db
        for(int i = 0; i < linTasks.length; i++) {
            LinTask lt = linTasks[i];


            //START process new total values (2 of them: Total and dollar TOTAL)
       if(!"Y".equals(request.getParameter("comingFromFeeScreen"))){
                int word100 = 0;
                int wordRep = 0;
                int word95 = 0;
                int word85 = 0;
                int word75 = 0;
                double wordNew = 0;
                int word8599 = 0;
                int wordContext=0;
                int wordPerfect=0;
                double wordNew4 = 0;

                if(lt.getWord100() != null) {
                    word100 = lt.getWord100();
                }
                if(lt.getWordRep() != null) {
                    wordRep = lt.getWordRep();
                }
                if(lt.getWord95() != null) {
                    word95 = lt.getWord95();
                }
                if(lt.getWord85() != null) {
                    word85 = lt.getWord85();
                }
                if(lt.getWord75() != null) {
                    word75 = lt.getWord75();
                }
                if(lt.getWordNew() != null) {
                    wordNew = lt.getWordNew();
                }
                if(lt.getWord8599() != null) {
                    word8599 = lt.getWord8599();
                }
                if(lt.getWordNew4() != null) {
                    wordNew4 = lt.getWordNew4();
                }
                if(lt.getWordContext() != null) {
                    wordContext = lt.getWordContext();
                }
                if(lt.getWordPerfect() != null) {
                    wordPerfect = lt.getWordPerfect();
                }



                double rate = 0;
                try{
                try {
                    rate = Double.valueOf(lt.getRate());
                } catch(java.lang.NumberFormatException nfe) {
                    rate = 0;
                }}catch(Exception e){
                rate=0;
                }
                
                if(lt.getTaskName().contains("Translation")){
                    linRates.put(lt.getTargetDoc().getName(), rate);
                }
                if(ProjectHelper.isProcessRate(p.getCompany().getClientId(),lt)){
                    rate = linRates.getOrDefault(lt.getTargetDoc().getName(), 0.00)*ExcelConstants.CLIENT_BBS_PROOFREADING_RATE;
                }

                double wordTotal = word100 + wordRep + word8599 + wordNew4 + wordContext + wordPerfect;

                double thisTotal = 0.0;
                //scale the rates (either default or custom)
                if(p.getCompany().isScaleDefault(p.getProjectId())) {
                    double scale100 = new Double(p.getCompany().getScale100(p.getProjectId(),p.getCompany().getClientId()));
                    double scaleRep = new Double(p.getCompany().getScaleRep(p.getProjectId(),p.getCompany().getClientId()));
                    double scale8599 = new Double(p.getCompany().getScale8599());
                    double scaleNew4 = new Double(p.getCompany().getScaleNew4());
                    double scalePerfect = Double.parseDouble(p.getCompany().getScalePerfect(p.getProjectId(),p.getCompany().getClientId()));
                    double scaleContext = new Double(p.getCompany().getScaleContext(p.getProjectId(),p.getCompany().getClientId()));
                   // double scaleContext = new Double(p.getCompany().getS)
                    thisTotal = word100*scale100*rate + wordRep*scaleRep*rate + word8599*scale8599*rate + wordNew4*scaleNew4*rate +wordContext*scaleContext*rate+wordPerfect*scalePerfect*rate;
                    wordTotal = word100 + wordRep + word8599 + wordNew4+wordContext+wordPerfect;
                } else {
                    double scale100 = new Double(p.getCompany().getScale100(p.getProjectId(),p.getCompany().getClientId()));
                    double scaleRep = new Double(p.getCompany().getScaleRep(p.getProjectId(),p.getCompany().getClientId()));
                    double scale95 = new Double(p.getCompany().getScale95(p.getProjectId(),p.getCompany().getClientId()));
                    double scale85 = new Double(p.getCompany().getScale85(p.getProjectId(),p.getCompany().getClientId()));
                    double scale75 = new Double(p.getCompany().getScale75(p.getProjectId(),p.getCompany().getClientId()));
                    double scaleNew = new Double(p.getCompany().getScaleNew(p.getProjectId(),p.getCompany().getClientId()));
                    double scalePerfect = new Double(p.getCompany().getScalePerfect(p.getProjectId(),p.getCompany().getClientId()));
                    double scaleContext=new Double(p.getCompany().getScaleContext(p.getProjectId(),p.getCompany().getClientId()));
                    thisTotal = word100*scale100*rate + wordRep*scaleRep*rate + word95*scale95*rate + word85*scale85*rate + word75*scale75*rate + wordNew*scaleNew*rate+wordContext*scaleContext*rate+wordPerfect*scalePerfect*rate;
                    wordTotal = word100 + wordRep + word95 + word85 + word75 +wordNew +wordContext+wordPerfect;
                }

                linTotal += thisTotal; //update lin block

                lt.setWordTotal(new Double(wordTotal));
                lt.setDollarTotal(StandardCode.getInstance().formatDouble(new Double(thisTotal)));
                lt.setRate(StandardCode.getInstance().formatDouble4(new Double(rate)));
               // lt.setICRcheck(new Boolean(ICRcheck));


          }else{//start FEE screen processing

            //if((lt.getWord100Fee() != null  && !lt.getWord100Fee().equals(new Integer(0))) || (lt.getWordRepFee() != null  && !lt.getWordRepFee().equals(new Integer(0))) || (lt.getWord85Fee() != null  && !lt.getWord85Fee().equals(new Integer(0))) || (lt.getWordNewFee() != null  && !lt.getWordNewFee().equals(new Integer(0))) || (lt.getWordNew4Fee() != null  && !lt.getWordNew4Fee().equals(new Double(0)))) { //trados
                int word100 = 0;
                int wordRep = 0;
                int word95 = 0;
                int word85 = 0;
                int word75 = 0;
                double wordNew = 0;
                int word8599 = 0;
                double wordNew4 = 0;
                double minFeeRsf = 0;
                int wordContext=0;
                int wordPerfect=0;

                if(lt.getWord100Fee() != null) {
                    word100 = lt.getWord100Fee();
                }
                if(lt.getWordRepFee() != null) {
                    wordRep = lt.getWordRepFee();
                }
                if(lt.getWord95Fee() != null) {
                    word95 = lt.getWord95Fee();
                }
                if(lt.getWord85Fee() != null) {
                    word85 = lt.getWord85Fee();
                }
                if(lt.getWord75Fee() != null) {
                    word75 = lt.getWord75Fee();
                }
                if(lt.getWordNewFee() != null) {
                    wordNew = lt.getWordNewFee();
                }
                if(lt.getWord8599Fee() != null) {
                    word8599 = lt.getWord8599Fee();
                }
                if(lt.getWordNew4Fee() != null) {
                    wordNew4 = lt.getWordNew4Fee();
                }
                if(lt.getMinFeeRsf() != null) {
                    minFeeRsf = lt.getMinFeeRsf();
                }
                if(lt.getWordContextFee() != null) {
                    wordContext = lt.getWordContextFee();
                }
                if(lt.getWordPerfectFee() != null) {
                    wordPerfect = lt.getWordPerfectFee();
                }

                double rate = 0;
                try {
                    String unformattedRate = lt.getRateFee().replaceAll(",", "");
                    rate = Double.valueOf(unformattedRate);
                } catch(java.lang.NumberFormatException nfe) {
                    rate = 0;
                }
                if(lt.getTaskName().contains("Translation")){
                    linRates.put(lt.getTargetDoc().getName(), rate);
                }

                double wordTotal = word100 + wordRep + word8599 + wordNew4+wordContext+wordPerfect;

                double thisTotal = 0.0;
                //scale the rates (either default or custom)
                if(p.getCompany().isScaleDefault(p.getProjectId())) {
                    double scale100 = new Double(p.getCompany().getScale100(p.getProjectId(),p.getCompany().getClientId()));
                    double scaleRep = new Double(p.getCompany().getScaleRep(p.getProjectId(),p.getCompany().getClientId()));
                    double scale8599 = new Double(p.getCompany().getScale8599());
                    double scaleNew4 = new Double(p.getCompany().getScaleNew4());
                    double scalePerfect = new Double(p.getCompany().getScalePerfect(p.getProjectId(),p.getCompany().getClientId()));
                    double scaleContext=new Double(p.getCompany().getScaleContext(p.getProjectId(),p.getCompany().getClientId()));
                    thisTotal = word100*scale100*rate + wordRep*scaleRep*rate + word8599*scale8599*rate + wordNew4*scaleNew4*rate+wordContext*scaleContext*rate+wordPerfect*scalePerfect*rate;
                    wordTotal = word100 + wordRep + word8599 + wordNew4 + wordContext + wordPerfect;
                } else {
                    double scale100 = Double.parseDouble(p.getCompany().getScale100(p.getProjectId(),p.getCompany().getClientId()));
                    double scaleRep = Double.parseDouble(p.getCompany().getScaleRep(p.getProjectId(),p.getCompany().getClientId()));
                    double scale95 = Double.parseDouble(p.getCompany().getScale95(p.getProjectId(),p.getCompany().getClientId()));
                    double scale85 = new Double(p.getCompany().getScale85(p.getProjectId(),p.getCompany().getClientId()));
                    double scale75 = new Double(p.getCompany().getScale75(p.getProjectId(),p.getCompany().getClientId()));
                    double scaleNew = new Double(p.getCompany().getScaleNew(p.getProjectId(),p.getCompany().getClientId()));
                    double scalePerfect = new Double(p.getCompany().getScalePerfect(p.getProjectId(),p.getCompany().getClientId()));
                    double scaleContext=new Double(p.getCompany().getScaleContext(p.getProjectId(),p.getCompany().getClientId()));
                    thisTotal = word100*scale100*rate + wordRep*scaleRep*rate + word95*scale95*rate + word85*scale85*rate + word75*scale75*rate + wordNew*scaleNew*rate+wordContext*scaleContext*rate+wordPerfect*scalePerfect*rate;
                    wordTotal = word100 + wordRep + word95 + word85 + word75 + wordNew + wordContext + wordPerfect;
                }
                thisTotal+=minFeeRsf;
               try{
                      //System.out.println("Currencyyyyyyyy"+lt.getCurrencyFee());
                      if (lt.getCurrencyFee().equalsIgnoreCase("EURO")){
                        //linCurrencyTotal=thisTotal;
                      linCurrencyTotal+=thisTotal*p.getEuroToUsdExchangeRate();
                      }else{
                       linCurrencyTotal+=thisTotal;
                      }
           } catch (Exception e) {
               linCurrencyTotal+=thisTotal;
           }
                       linTotal += thisTotal; //update lin block
                lt.setWordTotalFee(wordTotal);

                //Let user overwrite automatic calculations
                //if(lt.getDollarTotalFee()==null || "".equals(lt.getDollarTotalFee())){
                    lt.setDollarTotalFee(StandardCode.getInstance().formatDouble(thisTotal));
                //}

                lt.setRateFee(StandardCode.getInstance().formatDouble4(rate));


            }//END process FEE values

            //update to db
            ProjectService.getInstance().updateLinTask(lt);

        }//END LIN TASKS

        //START ENG TASKS
        //get the updated list of eng Tasks for this project
        EngTask[] engTasks = (EngTask[]) qvg.get("engTasksProject");

        //process each eng Task to db
        for (EngTask et : engTasks) {
            //START process new TOTAL value
            if(et.getTotal() != null) { //if from delete then make sure it is not null
                double total = et.getTotal();
                double rate = 0;
                try {
                    rate = Double.valueOf(et.getRate());
                } catch(java.lang.NumberFormatException nfe) {
                    rate = 0;
                }

                double thisTotal = total*rate;

                try{
                    if(et.getCurrency().equalsIgnoreCase("EURO")){
                        engTotal+=thisTotal*p.getEuroToUsdExchangeRate();
                    }else{engTotal+=thisTotal;}

                }catch(Exception e){
                    engTotal += thisTotal;
                }

                //engTotal += thisTotal; //update eng block

//                et.setDollarTotal(StandardCode.getInstance().formatDouble(new Double(thisTotal)));
//                et.setRate(StandardCode.getInstance().formatDouble3(new Double(rate)));
            }
            //END process new total value

            //update to db
//            ProjectService.getInstance().updateEngTask(et);
        } //END ENG TASKS

        //START DTP TASKS
        //get the updated list of dtp Tasks for this project
        DtpTask[] dtpTasks = (DtpTask[]) qvg.get("dtpTasksProject");

        //process each dtp Task to db
        for(int i = 0; i < dtpTasks.length; i++) {
            DtpTask dt = dtpTasks[i];

            //START process new TOTAL value
            if(dt.getTotal() != null) { //if from delete then make sure it is not null
                double total = dt.getTotal();
                double rate = 0;
                try {
                    rate = Double.valueOf(dt.getRate());
                } catch(java.lang.NumberFormatException nfe) {
                    rate = 0;
                }

                double thisTotal = total*rate;
                  try{
                    if(dt.getCurrency().equalsIgnoreCase("EURO")){
                    dtpTotal+=thisTotal*p.getEuroToUsdExchangeRate();
                    }else{dtpTotal+=thisTotal;}

                }catch(Exception e){
                dtpTotal += thisTotal; 
                }


                //update dtp block
//
//                dt.setDollarTotal(StandardCode.getInstance().formatDouble(new Double(thisTotal)));
//                dt.setRate(StandardCode.getInstance().formatDouble3(new Double(rate)));
            }
            //END process new total value

            //update to db
//            ProjectService.getInstance().updateDtpTask(dt);

        }//END DTP TASKS

        //START OTH TASKS
        //get the updated list of oth Tasks for this project
        OthTask[] othTasks = (OthTask[]) qvg.get("othTasksProject");

        //process each oth Task to db
        for(int i = 0; i < othTasks.length; i++) {
            OthTask ot = othTasks[i];

            //START process new TOTAL value
            if(ot.getTotal() != null) { //if from delete then make sure it is not null
                double total = ot.getTotal();
                double rate = 0;
                try {
                    rate = Double.valueOf(ot.getRate());
                } catch(java.lang.NumberFormatException nfe) {
                    rate = 0;
                }

                double thisTotal = total*rate;


                othTotal += thisTotal; //update oth block
//
//                ot.setDollarTotal(StandardCode.getInstance().formatDouble(new Double(thisTotal)));
//                ot.setRate(StandardCode.getInstance().formatDouble3(new Double(rate)));
            }
            //END process new total value

            //update to db
//            ProjectService.getInstance().updateOthTask(ot);

        }//END OTH TASKS

        //START subTotal value
        double subTotal = linCurrencyTotal + engTotal + dtpTotal + othTotal;
        p.setSubDollarTotal(StandardCode.getInstance().formatDouble(new Double(subTotal)));
        //END subTotal value

      //START pm block
        //percent values from form
        String pmPercent = (String) qvg.get("pmPercent");
        String pmPercentDollarTotal = (String) qvg.get("pmPercentDollarTotal");

        double pmRate = 0;
        double pmPercentDollarTotalDouble = 0;
        if(pmPercent != null && !pmPercent.equals("") && !pmPercent.equals("0.00")) { //if pmPercent is present
            try {
                pmRate = Double.valueOf(pmPercent);
            } catch(java.lang.NumberFormatException nfe) {
                pmRate = 0;
            }
            pmPercentDollarTotalDouble = (pmRate / 100) * subTotal;
        }else if(pmPercentDollarTotal != null && !pmPercentDollarTotal.equals("") && !pmPercentDollarTotal.equals("0.00")) { //if rushPercent is present
            try {
                pmPercentDollarTotalDouble = Double.valueOf(pmPercentDollarTotal.replaceAll(",",""));
            } catch(java.lang.NumberFormatException nfe) {
                pmRate = 0;
            }

        }

        //set totals for each line to project object
        p.setPmPercentDollarTotal(pmPercentDollarTotal);
        p.setPmPercent(pmPercent);
        //END pm block


        //START sub total with pm block
        double subPmTotal = subTotal + pmPercentDollarTotalDouble;
//        p.setSubPmDollarTotal(StandardCode.getInstance().formatDouble(new Double(subPmTotal)));
        //END sub total with pm block


        //START rush block
        String rushPercent = (String) qvg.get("rushPercent");
        String rushPercentDollarTotal = (String) qvg.get("rushPercentDollarTotal");

        double rushRate = 0;
        double rushPercentDollarTotalDouble = 0;
        if(rushPercent != null && !rushPercent.equals("") && !rushPercent.equals("0.00")) { //if rushPercent is present
            try {
                rushRate = Double.valueOf(rushPercent);
            } catch(java.lang.NumberFormatException nfe) {
                rushRate = 0;
            }
            rushPercentDollarTotalDouble = (rushRate / 100) * subPmTotal;
        }else if(rushPercentDollarTotal != null && !rushPercentDollarTotal.equals("") && !rushPercentDollarTotal.equals("0.00")) { //if rushPercent is present
            try {
                rushPercentDollarTotalDouble = Double.valueOf(rushPercentDollarTotal.replaceAll(",",""));
            } catch(java.lang.NumberFormatException nfe) {
                rushRate = 0;
            }

        }

        p.setRushPercentDollarTotal(rushPercentDollarTotal);
        p.setRushPercent(rushPercent);
        //END rush block

        //START total value
//        double projectTotal = subPmTotal + rushPercentDollarTotal;
        double projectTotal = subPmTotal + rushPercentDollarTotalDouble;

        //START DISCOUNT block
        String discountPercent = (String) qvg.get("discountPercent");
        String discountDollarTotal = (String) qvg.get("discountDollarTotal");
        double discountRate = 0;
        double discountPercentDollarTotal=0;
        if(discountPercent != null && !discountPercent.equals("") && !discountPercent.equals("0.00")) { //if rushPercent is present
            try {
                discountRate = Double.valueOf(discountPercent.replaceAll(",",""));
            } catch(java.lang.NumberFormatException nfe) {
                discountRate = 0;
            }
            //discountDollarTotal = ;
            projectTotal = projectTotal - (discountRate / 100) * projectTotal;
        }else if(discountDollarTotal != null && !discountDollarTotal.equals("") && !discountDollarTotal.equals("0.00")) { //if rushPercent is present
            try {
                discountPercentDollarTotal = Double.valueOf(discountDollarTotal.replaceAll(",",""));
            } catch(java.lang.NumberFormatException nfe) {
                discountRate = 0;
            }
            projectTotal = projectTotal - discountPercentDollarTotal;

        }

        p.setDiscountDollarTotal(discountDollarTotal);
        p.setDiscountPercent(discountPercent);
        p.setSubDiscountDollarTotal(""+projectTotal);
        
        
        String otherText=p.getOtherText();
        String otherPercent=p.getOtherPercent();
        String otherDollarTotal=p.getOtherDollarTotal();

        
        double otherPercentRate = 0;
        double otherPercentDollarTotal = 0;

        if (otherPercent != null && !otherPercent.equals("") && !otherPercent.equals("0.00")) { //if rushPercent is present
            try {
                otherPercentRate = Double.valueOf(otherPercent.replaceAll(",", ""));
            } catch (java.lang.NumberFormatException nfe) {
                otherPercentRate = 0;
            }
            //discountDollarTotal = ;
            projectTotal =projectTotal+((otherPercentRate / 100) * projectTotal);
        } else if (otherDollarTotal != null && !otherDollarTotal.equals("") && !otherDollarTotal.equals("0.00")) { //if rushPercent is present
            try {
                otherPercentDollarTotal = Double.valueOf(otherDollarTotal.replaceAll(",", ""));
            } catch (java.lang.NumberFormatException nfe) {
                otherPercentDollarTotal = 0;
            }
            projectTotal = projectTotal + otherPercentDollarTotal;

        }
//END DISCOUNT block


//            //START DISCOUNT block
//        String otherPercent = (String) qvg.get("discountPercent");
//        String otherDollarTotal = (String) qvg.get("discountDollarTotal");
//        double otherRate = 0;
//        double otherPercentDollarTotal=0;
//        if(otherPercent != null && !otherPercent.equals("") && !otherPercent.equals("0.00")) { //if rushPercent is present
//            try {
//                otherRate = Double.valueOf(otherPercent.replaceAll(",",""));
//            } catch(java.lang.NumberFormatException nfe) {
//                otherRate = 0;
//            }
//            //discountDollarTotal = ;
//            projectTotal = projectTotal - (otherRate / 100) * projectTotal;
//        }else if(otherDollarTotal != null && !otherPercent.equals("") && !otherPercent.equals("0.00")) { //if rushPercent is present
//            try {
//                otherPercentDollarTotal = Double.valueOf(otherDollarTotal.replaceAll(",",""));
//            } catch(java.lang.NumberFormatException nfe) {
//                otherPercentDollarTotal = 0;
//            }
//            projectTotal = projectTotal - otherPercentDollarTotal;
//
//        }
//
//        p.setDiscountDollarTotal(otherDollarTotal);
//        p.setDiscountPercent(otherPercent);
//        p.setSubDiscountDollarTotal(""+projectTotal);
////END DISCOUNT block




//        p.setProjectAmount(new Double(projectTotal));
        //END total value
        User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
        p.setLastModifiedBy(u.getFirstName() + " " + u.getLastName());
        p.setLastModifiedDate(new Date(System.currentTimeMillis()));
        //update project to db

//        if(request.getParameter("euroToUsdExchangeRate")!=null){
//            p.setEuroToUsdExchangeRate(new Double(request.getParameter("euroToUsdExchangeRate")));
//        }

        ProjectService.getInstance().updateProject(p);
        ProjectHelper.updateLanguageCount(p);
        ProjectService1.getInstance().updateProjectAmount(p, u);
        //mark AutoUpdate as true
        if(autoUpdate != null) {
            Integer newAutoUpdate = new Integer(autoUpdate + 1);
            request.setAttribute("AutoUpdate", newAutoUpdate); //place true value (1) into request
        }

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }

}
