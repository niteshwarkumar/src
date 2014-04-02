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
import app.standardCode.*;
import app.user.User;
import app.user.UserService;

public final class ProjectViewFeeCostUpdateAction extends Action {

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
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)

        //START get id of current project from either request, attribute, or cookie
        //id of project from request
        String projectId = null;
        projectId = request.getParameter("projectViewId");

        //check attribute in request
        if (projectId == null) {
            projectId = (String) request.getAttribute("projectViewId");
        }

        //id of project from cookie
        if (projectId == null) {
            projectId = StandardCode.getInstance().getCookie("projectViewId", request.getCookies());
        }

        //default project to last if not in request or cookie
        if (projectId == null) {
            List results = ProjectService.getInstance().getProjectList();

            ListIterator iterScroll = null;
            for (iterScroll = results.listIterator(); iterScroll.hasNext(); iterScroll.next()) {
            }
            iterScroll.previous();
            Project p = (Project) iterScroll.next();
            projectId = String.valueOf(p.getProjectId());
        }

        Integer id = Integer.valueOf(projectId);

        //END get id of current project from either request, attribute, or cookie


        //get project to edit
        Project p = ProjectService.getInstance().getSingleProject(id);

        //get this project's sources
        Set sources = p.getSourceDocs();

        //for each source add each sources' Tasks
        List totalLinTasks = new ArrayList();
        List totalEngTasks = new ArrayList();
        List totalDtpTasks = new ArrayList();
        List totalOthTasks = new ArrayList();

        //for each source
        for (Iterator sourceIter = sources.iterator(); sourceIter.hasNext();) {
            SourceDoc sd = (SourceDoc) sourceIter.next();

            //for each target of this source
            for (Iterator linTargetIter = sd.getTargetDocs().iterator(); linTargetIter.hasNext();) {
                TargetDoc td = (TargetDoc) linTargetIter.next();

                //for each lin Task of this target
                for (Iterator linTaskIter = td.getLinTasks().iterator(); linTaskIter.hasNext();) {
                    LinTask lt = (LinTask) linTaskIter.next();
                    totalLinTasks.add(lt);
                }

                //for each eng Task of this target
                for (Iterator engTaskIter = td.getEngTasks().iterator(); engTaskIter.hasNext();) {
                    EngTask et = (EngTask) engTaskIter.next();
                    totalEngTasks.add(et);
                }

                //for each dtp Task of this target
                for (Iterator dtpTaskIter = td.getDtpTasks().iterator(); dtpTaskIter.hasNext();) {
                    DtpTask dt = (DtpTask) dtpTaskIter.next();
                    totalDtpTasks.add(dt);
                }

                //for each oth Task of this target
                for (Iterator othTaskIter = td.getOthTasks().iterator(); othTaskIter.hasNext();) {
                    OthTask ot = (OthTask) othTaskIter.next();
                    totalOthTasks.add(ot);
                }
            }
        }

        //Sort by task (orderNum), then source (language), then target (language)
        Collections.sort(totalLinTasks, CompareTaskLin.getInstance());
        Collections.sort(totalEngTasks, CompareTaskEng.getInstance());
        Collections.sort(totalEngTasks, CompareTaskLanguages.getInstance());

        Collections.sort(totalDtpTasks, CompareTaskDtp.getInstance());
        Collections.sort(totalOthTasks, CompareTaskOth.getInstance());

        //array for display in jsp
        LinTask[] linTasksArray = (LinTask[]) totalLinTasks.toArray(new LinTask[0]);
        EngTask[] engTasksArray = (EngTask[]) totalEngTasks.toArray(new EngTask[0]);
        DtpTask[] dtpTasksArray = (DtpTask[]) totalDtpTasks.toArray(new DtpTask[0]);
        OthTask[] othTasksArray = (OthTask[]) totalOthTasks.toArray(new OthTask[0]);



        //find total of LinTasks
        double linTaskTotal = 0;
        for (int i = 0; i < linTasksArray.length; i++) {
            if (linTasksArray[i].getDollarTotalFee() != null) {
                //remove comma's
                String linTotal = linTasksArray[i].getDollarTotalFee();
                linTotal = linTotal.replaceAll(",", "");
                if ("".equals(linTotal)) {
                    linTotal = "0";
                }
                Double total = Double.valueOf(linTotal);

                try {
                    // System.out.println("Currencyyyyyyyy"+lt.getCurrencyFee());
                    if (linTasksArray[i].getCurrencyFee().equalsIgnoreCase("EURO")) {
                        //linCurrencyTotal=thisTotal;
                        linTaskTotal += total.doubleValue() * p.getEuroToUsdExchangeRate();
                    }
                } catch (Exception e) {

                    linTaskTotal += total.doubleValue();
                }
            }
        }
        //find total of EngTasks
        double engTaskTotal = 0;
        for (int i = 0; i < engTasksArray.length; i++) {
            if (engTasksArray[i].getDollarTotal() != null) {
                //remove comma's
                String engTotal = engTasksArray[i].getDollarTotal();
                engTotal = engTotal.replaceAll(",", "");
                if ("".equals(engTotal)) {
                    engTotal = "0";
                }
                Double total = Double.valueOf(engTotal);
                engTaskTotal += total.doubleValue();
            }
        }

        //find total of DtpTasks
        double dtpTaskTotal = 0;
        for (int i = 0; i < dtpTasksArray.length; i++) {
            if (dtpTasksArray[i].getDollarTotal() != null) {
                //remove comma's
                String dtpTotal = dtpTasksArray[i].getDollarTotal();
                dtpTotal = dtpTotal.replaceAll(",", "");
                if ("".equals(dtpTotal)) {
                    dtpTotal = "0";
                }
                Double total = Double.valueOf(dtpTotal);
                dtpTaskTotal += total.doubleValue();
            }
        }

        //find total of OthTasks
        double othTaskTotal = 0;
        for (int i = 0; i < othTasksArray.length; i++) {
            if (othTasksArray[i].getDollarTotal() != null) {
                //remove comma's
                String othTotal = othTasksArray[i].getDollarTotal();
                othTotal = othTotal.replaceAll(",", "");
                if ("".equals(othTotal)) {
                    othTotal = "0";
                }
                Double total = Double.valueOf(othTotal);
                othTaskTotal += total.doubleValue();
            }
        }

        //START subTotal value
        double subTotal = 0.0;
        try {
//            subTotal = p.getProjectAmount();
            subTotal = Double.parseDouble(p.getSubDollarTotal().replaceAll(",", ""));
        } catch (Exception e) {
            try{
                subTotal = p.getProjectAmount();
            }catch(Exception e1){}

        }
        //END subTotal value


//START pm block
        //percent values from form
        String pmPercent = request.getParameter("pmPercent");
        String pmPercentDollarTotal = request.getParameter("pmPercentDollarTotal");
//        String pmPercentFromDB = p.getPmPercent();
//        String pmPercentDollarTotalFromDB = p.getPmPercentDollarTotal();

//        double pmRateFromDB = 0;
//        double pmPercentDollarTotalDoubleFromDB = 0;
//        if (pmPercentFromDB != null) { //if pmPercent is present
//            try {
//                pmRateFromDB = Double.valueOf(pmPercent).doubleValue();
//            } catch (java.lang.NumberFormatException nfe) {
//                pmRateFromDB = 0;
//            }
//            pmPercentDollarTotalDoubleFromDB = (pmRateFromDB / 100) * subTotal;
//        } else if (pmPercentDollarTotalFromDB != null) { //if rushPercent is present
//            try {
//                pmPercentDollarTotalDoubleFromDB = Double.valueOf(pmPercentDollarTotal.replaceAll(",", "")).doubleValue();
//            } catch (java.lang.NumberFormatException nfe) {
//                pmRateFromDB = 0;
//            }
//
//        }
//
//        subTotal=subTotal-pmPercentDollarTotalDoubleFromDB;


        double pmRate = 0;
        double pmPercentDollarTotalDouble = 0;

        if (pmPercent != null  && !pmPercent.equals("") && !pmPercent.equals("0.00")) { //if pmPercent is present
            try {
                pmRate = Double.valueOf(pmPercent).doubleValue();
            } catch (java.lang.NumberFormatException nfe) {
                pmRate = 0;
            }
            pmPercentDollarTotalDouble = (pmRate / 100) * subTotal;
        } else if (pmPercentDollarTotal != null  && !pmPercentDollarTotal.equals("") && !pmPercentDollarTotal.equals("0.00")) { //if rushPercent is present
            try {
                pmPercentDollarTotalDouble = Double.valueOf(pmPercentDollarTotal.replaceAll(",", "")).doubleValue();
            } catch (java.lang.NumberFormatException nfe) {
                pmRate = 0;
            }

        }

        //set totals for each line to project object
        p.setPmPercentDollarTotal(pmPercentDollarTotal);
        p.setPmPercent(pmPercent);
        //END pm block


        //START sub total with pm block
        double subPmTotal = subTotal + pmPercentDollarTotalDouble;
        p.setSubPmDollarTotal(StandardCode.getInstance().formatDouble(new Double(subPmTotal)));
        //END sub total with pm block


        //START rush block
        String rushPercent = request.getParameter("rushPercent");
        String rushPercentDollarTotal = request.getParameter("rushPercentDollarTotal");

        double rushRate = 0;
        double rushPercentDollarTotalDouble = 0;
        if (rushPercent != null && !rushPercent.equals("") && !rushPercent.equals("0.00")) { //if rushPercent is present

            try {
                rushRate = Double.valueOf(rushPercent).doubleValue();
            } catch (java.lang.NumberFormatException nfe) {
                rushRate = 0;
            }
            rushPercentDollarTotalDouble = (rushRate / 100) * subPmTotal;
        } else if (rushPercentDollarTotal != null && !rushPercentDollarTotal.equals("") && !rushPercentDollarTotal.equals("0.00")) { //if rushPercent is present
            try {
                rushPercentDollarTotalDouble = Double.valueOf(rushPercentDollarTotal.replaceAll(",", "")).doubleValue();
                try {
                    if (p.getCompany().getCcurrency().equalsIgnoreCase("EURO")) {
                        rushPercentDollarTotalDouble = rushPercentDollarTotalDouble * p.getEuroToUsdExchangeRate();
                    }
                } catch (Exception e) {
                }
            } catch (java.lang.NumberFormatException nfe) {
                rushRate = 0;
            }

        }

        p.setRushPercentDollarTotal(rushPercentDollarTotal);
        p.setRushPercent(rushPercent);
        //END rush block


        //START total value
        double projectTotal = subPmTotal + rushPercentDollarTotalDouble;

        //START DISCOUNT block
        String discountPercent = request.getParameter("discountPercent");
        String discountDollarTotal = request.getParameter("discountDollarTotal");
        double discountRate = 0;
        double discountPercentDollarTotal = 0;
        if (discountPercent != null && !discountPercent.equals("") && !discountPercent.equals("0.00")) { //if rushPercent is present
            try {
                discountRate = Double.valueOf(discountPercent.replaceAll(",", "")).doubleValue();
            } catch (java.lang.NumberFormatException nfe) {
                discountRate = 0;
            }
            //discountDollarTotal = ;
            projectTotal = projectTotal - (discountRate / 100) * projectTotal;
        } else if (discountDollarTotal != null && !discountDollarTotal.equals("") && !discountDollarTotal.equals("0.00")) { //if rushPercent is present
            try {
                discountPercentDollarTotal = Double.valueOf(discountDollarTotal.replaceAll(",", "")).doubleValue();
            } catch (java.lang.NumberFormatException nfe) {
                discountRate = 0;
            }
            projectTotal = projectTotal - discountPercentDollarTotal;

        }

        p.setDiscountDollarTotal(discountDollarTotal);
        p.setDiscountPercent(discountPercent);
        p.setSubDiscountDollarTotal("" + projectTotal);


        String otherText=request.getParameter("otherText");
        String otherPercent=request.getParameter("otherPercent");
        String otherDollarTotal=request.getParameter("otherDollarTotal");

        p.setOtherDollarTotal(otherDollarTotal);
        p.setOtherText(otherText);
        p.setOtherPercent(otherPercent);
        
        double otherPercentRate = 0;
        double otherPercentDollarTotal = 0;

        if (otherPercent != null && !otherPercent.equals("") && !otherPercent.equals("0.00")) { //if rushPercent is present
            try {
                otherPercentRate = Double.valueOf(otherPercent.replaceAll(",", "")).doubleValue();
            } catch (java.lang.NumberFormatException nfe) {
                otherPercentRate = 0;
            }
            //discountDollarTotal = ;
            projectTotal =projectTotal+(otherPercentRate / 100) * subTotal;
        } else if (otherDollarTotal != null && !otherDollarTotal.equals("") && !otherDollarTotal.equals("0.00")) { //if rushPercent is present
            try {
                otherPercentDollarTotal = Double.valueOf(otherDollarTotal.replaceAll(",", "")).doubleValue();
            } catch (java.lang.NumberFormatException nfe) {
                otherPercentDollarTotal = 0;
            }
            projectTotal = projectTotal + otherPercentDollarTotal;

        }

        //END DISCOUNT block


        p.setProjectAmount(new Double(projectTotal));
        //END total value
        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
        p.setLastModifiedBy(u.getFirstName() + " " + u.getLastName());
        p.setLastModifiedDate(new Date(System.currentTimeMillis()));
        //update project to db
        ProjectService.getInstance().updateProject(p);



        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
