//ReportsList14Action.java gets the display
//ready for reporting info, such as graphs, dollar totals, etc.

package app.reports;

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
import java.text.*;
import app.user.*;
import app.client.*;
import app.project.*;
import app.standardCode.*;
import app.db.*;
import app.workspace.*;
import app.security.*;


public final class ReportsList14Action extends Action {


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
        
        //PRIVS check that reports user is viewing this page 
        if(!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "reports")) {
            return (mapping.findForward("accessDenied"));
        }//END PRIVS check that reports user is viewing this page
        
        //START get report criteria
        String profitAString = request.getParameter("profitA");
        Double profitA;
        if(profitAString == null) { //default to 100.0%
            profitA = new Double(100.0);
        }
        else { 
            profitA = new Double(profitAString);
        }
        
        String taskB = request.getParameter("taskB");
        if(taskB == null) { //default to "Total Project Profitability"
            taskB = "Total Project Profitability";
        }
        
        String dateCString = request.getParameter("dateC");
        Date dateC = new Date();
        if(dateCString != null && dateCString.length() > 0) {
            dateC = DateService.getInstance().convertDate(dateCString).getTime();
        }
        
        String dateDString = request.getParameter("dateD");
        Date dateD = new Date();
        if(dateDString != null && dateDString.length() > 0) {
            dateD = DateService.getInstance().convertDate(dateDString).getTime();
        }
        //END get report criteria       
        
        //run report search
        List results = ReportsService.getInstance().runReport14(dateC, dateD);
        
        //START get projects with profitability lower than profitA for taskB
            ArrayList finalResults = new ArrayList();
            if(results != null) {
                for(ListIterator iter = results.listIterator(); iter.hasNext();) {
                    Project p = (Project) iter.next();
                    p = ProjectService.getInstance().getSingleProject(p.getProjectId());
                //get total of each task: both actual (team tab) and budget (fee tab)

                //START actual
                //get this project's sources
                Set sources = p.getSourceDocs();

                //for each source add each sources' Tasks
                List totalLinTasks = new ArrayList();
                List totalEngTasks = new ArrayList();
                List totalDtpTasks = new ArrayList();
                List totalOthTasks = new ArrayList();

                //for each source
                for(Iterator sourceIter = sources.iterator(); sourceIter.hasNext();) {
                    SourceDoc sd = (SourceDoc) sourceIter.next();

                    //for each target of this source
                    for(Iterator linTargetIter = sd.getTargetDocs().iterator(); linTargetIter.hasNext();) {
                        TargetDoc td = (TargetDoc) linTargetIter.next();

                        //for each lin Task of this target
                        for(Iterator linTaskIter = td.getLinTasks().iterator(); linTaskIter.hasNext();) {
                            LinTask lt = (LinTask) linTaskIter.next();
                            totalLinTasks.add(lt);
                        }

                        //for each eng Task of this target
                        for(Iterator engTaskIter = td.getEngTasks().iterator(); engTaskIter.hasNext();) {
                            EngTask et = (EngTask) engTaskIter.next();
                            totalEngTasks.add(et);
                        }

                        //for each dtp Task of this target
                        for(Iterator dtpTaskIter = td.getDtpTasks().iterator(); dtpTaskIter.hasNext();) {
                            DtpTask dt = (DtpTask) dtpTaskIter.next();
                            totalDtpTasks.add(dt);
                        }

                        //for each oth Task of this target
                        for(Iterator othTaskIter = td.getOthTasks().iterator(); othTaskIter.hasNext();) {
                            OthTask ot = (OthTask) othTaskIter.next();
                            totalOthTasks.add(ot);
                        }             
                    }   
                }       

                //Sort by task (orderNum), then source (language), then target (language)
                Collections.sort(totalLinTasks, CompareTaskLin.getInstance());
                Collections.sort(totalEngTasks, CompareTaskEng.getInstance());
                Collections.sort(totalDtpTasks, CompareTaskDtp.getInstance());
                Collections.sort(totalOthTasks, CompareTaskOth.getInstance());

                //array for display in jsp
                LinTask[] linTasksArray = (LinTask[]) totalLinTasks.toArray(new LinTask[0]);
                EngTask[] engTasksArray = (EngTask[]) totalEngTasks.toArray(new EngTask[0]);
                DtpTask[] dtpTasksArray = (DtpTask[]) totalDtpTasks.toArray(new DtpTask[0]);
                OthTask[] othTasksArray = (OthTask[]) totalOthTasks.toArray(new OthTask[0]);

                //find total of LinTasks
                double linTaskTotal = 0;
                for(int i = 0; i < linTasksArray.length; i++) {
                    if(linTasksArray[i].getInternalDollarTotal() != null) {
                        //remove comma's
                        String linTotal = linTasksArray[i].getInternalDollarTotal();
                        linTotal = linTotal.replaceAll(",","");
                        Double total = Double.valueOf(linTotal);
                        linTaskTotal += total.doubleValue();
                    }
                }     

                //find total of EngTasks
                double engTaskTotal = 0;
                for(int i = 0; i < engTasksArray.length; i++) {
                    if(engTasksArray[i].getInternalDollarTotal() != null) {
                        //remove comma's
                        String engTotal = engTasksArray[i].getInternalDollarTotal();
                        engTotal = engTotal.replaceAll(",","");
                        Double total = Double.valueOf(engTotal);
                        engTaskTotal += total.doubleValue();
                    }
                }    

                //find total of DtpTasks
                double dtpTaskTotal = 0;
                for(int i = 0; i < dtpTasksArray.length; i++) {
                    if(dtpTasksArray[i].getInternalDollarTotal() != null) {
                        //remove comma's
                        String dtpTotal = dtpTasksArray[i].getInternalDollarTotal();
                        dtpTotal = dtpTotal.replaceAll(",","");
                        Double total = Double.valueOf(dtpTotal);
                        dtpTaskTotal += total.doubleValue();
                    }
                }    

                //find total of OthTasks
                double othTaskTotal = 0;
                for(int i = 0; i < othTasksArray.length; i++) {
                    if(othTasksArray[i].getInternalDollarTotal() != null) {
                        //remove comma's
                        String othTotal = othTasksArray[i].getInternalDollarTotal();
                        othTotal = othTotal.replaceAll(",","");
                        Double total = Double.valueOf(othTotal);
                        othTaskTotal += total.doubleValue();
                    }
                }    

                //find team TOTAL
                double teamTotal = linTaskTotal + engTaskTotal + dtpTaskTotal + othTaskTotal;

//                //place TaskTotals in request as formated string
//                request.setAttribute("linTaskTotal", StandardCode.getInstance().formatDouble(new Double(linTaskTotal)));
//                request.setAttribute("engTaskTotal", StandardCode.getInstance().formatDouble(new Double(engTaskTotal)));
//                request.setAttribute("dtpTaskTotal", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotal)));
//                request.setAttribute("othTaskTotal", StandardCode.getInstance().formatDouble(new Double(othTaskTotal)));
//                request.setAttribute("total", StandardCode.getInstance().formatDouble(new Double(teamTotal)));
//                //END actual

                //START budget
                //find total of LinTasks
                double linTaskTotalBudget = 0;
                for(int i = 0; i < linTasksArray.length; i++) {
                    if(linTasksArray[i].getDollarTotal() != null) {
                        //remove comma's
                        String linTotal = linTasksArray[i].getDollarTotal();
                        linTotal = linTotal.replaceAll(",","");
                        Double total = Double.valueOf(linTotal);
                        linTaskTotalBudget += total.doubleValue();
                    }
                }     

                //find total of EngTasks
                double engTaskTotalBudget = 0;
                for(int i = 0; i < engTasksArray.length; i++) {
                    if(engTasksArray[i].getDollarTotal() != null) {
                        //remove comma's
                        String engTotal = engTasksArray[i].getDollarTotal();
                        engTotal = engTotal.replaceAll(",","");
                        Double total = Double.valueOf(engTotal);
                        engTaskTotalBudget += total.doubleValue();
                    }
                }    

                //find total of DtpTasks
                double dtpTaskTotalBudget = 0;
                for(int i = 0; i < dtpTasksArray.length; i++) {
                    if(dtpTasksArray[i].getDollarTotal() != null) {
                        //remove comma's
                        String dtpTotal = dtpTasksArray[i].getDollarTotal();
                        dtpTotal = dtpTotal.replaceAll(",","");
                        Double total = Double.valueOf(dtpTotal);
                        dtpTaskTotalBudget += total.doubleValue();
                    }
                }    

                //find total of OthTasks
                double othTaskTotalBudget = 0;
                for(int i = 0; i < othTasksArray.length; i++) {
                    if(othTasksArray[i].getDollarTotal() != null) {
                        //remove comma's
                        String othTotal = othTasksArray[i].getDollarTotal();
                        othTotal = othTotal.replaceAll(",","");
                        Double total = Double.valueOf(othTotal);
                        othTaskTotalBudget += total.doubleValue();
                    }
                }    

                //team (fee) TOTAL
                double teamTotalBudget = 0;
                if(p.getProjectAmount() != null) {
                    teamTotalBudget = p.getProjectAmount().doubleValue();
                }

                //place TaskTotals in request as formated string
//                request.setAttribute("linTaskTotalBudget", StandardCode.getInstance().formatDouble(new Double(linTaskTotalBudget)));
//                request.setAttribute("engTaskTotalBudget", StandardCode.getInstance().formatDouble(new Double(engTaskTotalBudget)));
//                request.setAttribute("dtpTaskTotalBudget", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotalBudget)));
//                request.setAttribute("othTaskTotalBudget", StandardCode.getInstance().formatDouble(new Double(othTaskTotalBudget)));
//                request.setAttribute("totalBudget", StandardCode.getInstance().formatDouble(new Double(teamTotalBudget)));
//                //END budget

                //START find profitability
                double linProf = 0;
                double engProf = 0;
                double dtpProf = 0;
                double othProf = 0;
                double totalProf = 0;

                if(linTaskTotalBudget != 0.0) {
                    linProf = (linTaskTotalBudget - linTaskTotal) / linTaskTotal;
                }
                if(engTaskTotalBudget != 0.0) {
                    engProf = (engTaskTotalBudget - engTaskTotal) / engTaskTotal;
                }
                if(dtpTaskTotalBudget != 0.0) {
                    dtpProf = (dtpTaskTotalBudget - dtpTaskTotal) / dtpTaskTotal;
                }
                if(othTaskTotalBudget != 0.0) {
                    othProf = (othTaskTotalBudget - othTaskTotal) / othTaskTotal;
                }
                if(teamTotalBudget != 0.0) {
                    totalProf = (teamTotalBudget - teamTotal) / teamTotal;
                }
//                request.setAttribute("linProf", StandardCode.getInstance().formatDouble(new Double(linProf * 100.0)));
//                request.setAttribute("engProf", StandardCode.getInstance().formatDouble(new Double(engProf * 100.0)));
//                request.setAttribute("dtpProf", StandardCode.getInstance().formatDouble(new Double(dtpProf * 100.0)));
//                request.setAttribute("othProf", StandardCode.getInstance().formatDouble(new Double(othProf * 100.0)));
//                request.setAttribute("totalProf", StandardCode.getInstance().formatDouble(new Double(totalProf * 100.0)));        
//                
                //check if this project's task group is less than criteria percentage
                if(taskB.equals("Total Project Profitability")) {
                    if((totalProf * 100.0) < profitA.doubleValue() && (totalProf * 100.0) > 0.0) {
                        finalResults.add(p);
                    }
                }
                else if(taskB.equals("Linguistic Profitability")) {
                    if((linProf * 100.0) < profitA.doubleValue() && (linProf * 100.0) > 0.0) {
                        finalResults.add(p);
                    }
                }
                else if(taskB.equals("Engineering Profitability")) {
                    if((engProf * 100.0) < profitA.doubleValue() && (engProf * 100.0) > 0.0) {
                        finalResults.add(p);
                    }
                }
                else if(taskB.equals("Dtp Profitability")) {
                    if((dtpProf * 100.0) < profitA.doubleValue() && (dtpProf * 100.0) > 0.0) {
                        finalResults.add(p);
                    }
                }
                else if(taskB.equals("Other Profitability")) {
                    if((othProf * 100.0) < profitA.doubleValue() && (othProf * 100.0) > 0.0) {
                        finalResults.add(p);
                    }
                }
                
                //END find profitability
                }
        }
        //END get projects with profitability lower than profitA for taskB
        
        //START build profit chart
        String totals[][] = new String[0][0];
        if(finalResults != null && finalResults.size() > 0) {
           totals = new String[finalResults.size()][4];
           int iCount = 0;
                for(ListIterator iter = finalResults.listIterator(); iter.hasNext(); iCount++) {
                    Project p = (Project) iter.next();
                    p = ProjectService.getInstance().getSingleProject(p.getProjectId());
                //get total of each task: both actual (team tab) and budget (fee tab)

                //START actual
                //get this project's sources
                Set sources = p.getSourceDocs();

                //for each source add each sources' Tasks
                List totalLinTasks = new ArrayList();
                List totalEngTasks = new ArrayList();
                List totalDtpTasks = new ArrayList();
                List totalOthTasks = new ArrayList();

                //for each source
                for(Iterator sourceIter = sources.iterator(); sourceIter.hasNext();) {
                    SourceDoc sd = (SourceDoc) sourceIter.next();

                    //for each target of this source
                    for(Iterator linTargetIter = sd.getTargetDocs().iterator(); linTargetIter.hasNext();) {
                        TargetDoc td = (TargetDoc) linTargetIter.next();

                        //for each lin Task of this target
                        for(Iterator linTaskIter = td.getLinTasks().iterator(); linTaskIter.hasNext();) {
                            LinTask lt = (LinTask) linTaskIter.next();
                            totalLinTasks.add(lt);
                        }

                        //for each eng Task of this target
                        for(Iterator engTaskIter = td.getEngTasks().iterator(); engTaskIter.hasNext();) {
                            EngTask et = (EngTask) engTaskIter.next();
                            totalEngTasks.add(et);
                        }

                        //for each dtp Task of this target
                        for(Iterator dtpTaskIter = td.getDtpTasks().iterator(); dtpTaskIter.hasNext();) {
                            DtpTask dt = (DtpTask) dtpTaskIter.next();
                            totalDtpTasks.add(dt);
                        }

                        //for each oth Task of this target
                        for(Iterator othTaskIter = td.getOthTasks().iterator(); othTaskIter.hasNext();) {
                            OthTask ot = (OthTask) othTaskIter.next();
                            totalOthTasks.add(ot);
                        }             
                    }   
                }       

                //Sort by task (orderNum), then source (language), then target (language)
                Collections.sort(totalLinTasks, CompareTaskLin.getInstance());
                Collections.sort(totalEngTasks, CompareTaskEng.getInstance());
                Collections.sort(totalDtpTasks, CompareTaskDtp.getInstance());
                Collections.sort(totalOthTasks, CompareTaskOth.getInstance());

                //array for display in jsp
                LinTask[] linTasksArray = (LinTask[]) totalLinTasks.toArray(new LinTask[0]);
                EngTask[] engTasksArray = (EngTask[]) totalEngTasks.toArray(new EngTask[0]);
                DtpTask[] dtpTasksArray = (DtpTask[]) totalDtpTasks.toArray(new DtpTask[0]);
                OthTask[] othTasksArray = (OthTask[]) totalOthTasks.toArray(new OthTask[0]);

                //find total of LinTasks
                double linTaskTotal = 0;
                for(int i = 0; i < linTasksArray.length; i++) {
                    if(linTasksArray[i].getInternalDollarTotal() != null) {
                        //remove comma's
                        String linTotal = linTasksArray[i].getInternalDollarTotal();
                        linTotal = linTotal.replaceAll(",","");
                        Double total = Double.valueOf(linTotal);
                        linTaskTotal += total.doubleValue();
                    }
                }     

                //find total of EngTasks
                double engTaskTotal = 0;
                for(int i = 0; i < engTasksArray.length; i++) {
                    if(engTasksArray[i].getInternalDollarTotal() != null) {
                        //remove comma's
                        String engTotal = engTasksArray[i].getInternalDollarTotal();
                        engTotal = engTotal.replaceAll(",","");
                        Double total = Double.valueOf(engTotal);
                        engTaskTotal += total.doubleValue();
                    }
                }    

                //find total of DtpTasks
                double dtpTaskTotal = 0;
                for(int i = 0; i < dtpTasksArray.length; i++) {
                    if(dtpTasksArray[i].getInternalDollarTotal() != null) {
                        //remove comma's
                        String dtpTotal = dtpTasksArray[i].getInternalDollarTotal();
                        dtpTotal = dtpTotal.replaceAll(",","");
                        Double total = Double.valueOf(dtpTotal);
                        dtpTaskTotal += total.doubleValue();
                    }
                }    

                //find total of OthTasks
                double othTaskTotal = 0;
                for(int i = 0; i < othTasksArray.length; i++) {
                    if(othTasksArray[i].getInternalDollarTotal() != null) {
                        //remove comma's
                        String othTotal = othTasksArray[i].getInternalDollarTotal();
                        othTotal = othTotal.replaceAll(",","");
                        Double total = Double.valueOf(othTotal);
                        othTaskTotal += total.doubleValue();
                    }
                }    

                //find team TOTAL
                double teamTotal = linTaskTotal + engTaskTotal + dtpTaskTotal + othTaskTotal;

//                //place TaskTotals in request as formated string
//                request.setAttribute("linTaskTotal", StandardCode.getInstance().formatDouble(new Double(linTaskTotal)));
//                request.setAttribute("engTaskTotal", StandardCode.getInstance().formatDouble(new Double(engTaskTotal)));
//                request.setAttribute("dtpTaskTotal", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotal)));
//                request.setAttribute("othTaskTotal", StandardCode.getInstance().formatDouble(new Double(othTaskTotal)));
//                request.setAttribute("total", StandardCode.getInstance().formatDouble(new Double(teamTotal)));
//                //END actual

                //START budget
                //find total of LinTasks
                double linTaskTotalBudget = 0;
                for(int i = 0; i < linTasksArray.length; i++) {
                    if(linTasksArray[i].getDollarTotal() != null) {
                        //remove comma's
                        String linTotal = linTasksArray[i].getDollarTotal();
                        linTotal = linTotal.replaceAll(",","");
                        Double total = Double.valueOf(linTotal);
                        linTaskTotalBudget += total.doubleValue();
                    }
                }     

                //find total of EngTasks
                double engTaskTotalBudget = 0;
                for(int i = 0; i < engTasksArray.length; i++) {
                    if(engTasksArray[i].getDollarTotal() != null) {
                        //remove comma's
                        String engTotal = engTasksArray[i].getDollarTotal();
                        engTotal = engTotal.replaceAll(",","");
                        Double total = Double.valueOf(engTotal);
                        engTaskTotalBudget += total.doubleValue();
                    }
                }    

                //find total of DtpTasks
                double dtpTaskTotalBudget = 0;
                for(int i = 0; i < dtpTasksArray.length; i++) {
                    if(dtpTasksArray[i].getDollarTotal() != null) {
                        //remove comma's
                        String dtpTotal = dtpTasksArray[i].getDollarTotal();
                        dtpTotal = dtpTotal.replaceAll(",","");
                        Double total = Double.valueOf(dtpTotal);
                        dtpTaskTotalBudget += total.doubleValue();
                    }
                }    

                //find total of OthTasks
                double othTaskTotalBudget = 0;
                for(int i = 0; i < othTasksArray.length; i++) {
                    if(othTasksArray[i].getDollarTotal() != null) {
                        //remove comma's
                        String othTotal = othTasksArray[i].getDollarTotal();
                        othTotal = othTotal.replaceAll(",","");
                        Double total = Double.valueOf(othTotal);
                        othTaskTotalBudget += total.doubleValue();
                    }
                }    

                //team (fee) TOTAL
                double teamTotalBudget = 0;
                if(p.getProjectAmount() != null) {
                    teamTotalBudget = p.getProjectAmount().doubleValue();
                }

                //place TaskTotals in request as formated string
//                request.setAttribute("linTaskTotalBudget", StandardCode.getInstance().formatDouble(new Double(linTaskTotalBudget)));
//                request.setAttribute("engTaskTotalBudget", StandardCode.getInstance().formatDouble(new Double(engTaskTotalBudget)));
//                request.setAttribute("dtpTaskTotalBudget", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotalBudget)));
//                request.setAttribute("othTaskTotalBudget", StandardCode.getInstance().formatDouble(new Double(othTaskTotalBudget)));
//                request.setAttribute("totalBudget", StandardCode.getInstance().formatDouble(new Double(teamTotalBudget)));
//                //END budget

                //START find profitability
                double linProf = 0;
                double engProf = 0;
                double dtpProf = 0;
                double othProf = 0;
                double totalProf = 0;

                if(linTaskTotalBudget != 0.0) {
                    linProf = (linTaskTotalBudget - linTaskTotal) / linTaskTotal;
                }
                if(engTaskTotalBudget != 0.0) {
                    engProf = (engTaskTotalBudget - engTaskTotal) / engTaskTotal;
                }
                if(dtpTaskTotalBudget != 0.0) {
                    dtpProf = (dtpTaskTotalBudget - dtpTaskTotal) / dtpTaskTotal;
                }
                if(othTaskTotalBudget != 0.0) {
                    othProf = (othTaskTotalBudget - othTaskTotal) / othTaskTotal;
                }
                if(teamTotalBudget != 0.0) {
                    totalProf = (teamTotalBudget - teamTotal) / teamTotal;
                }
//                request.setAttribute("linProf", StandardCode.getInstance().formatDouble(new Double(linProf * 100.0)));
//                request.setAttribute("engProf", StandardCode.getInstance().formatDouble(new Double(engProf * 100.0)));
//                request.setAttribute("dtpProf", StandardCode.getInstance().formatDouble(new Double(dtpProf * 100.0)));
//                request.setAttribute("othProf", StandardCode.getInstance().formatDouble(new Double(othProf * 100.0)));
//                request.setAttribute("totalProf", StandardCode.getInstance().formatDouble(new Double(totalProf * 100.0)));        
//                
                //check if this project's task group is less than criteria percentage
                if(taskB.equals("Total Project Profitability")) {
                    if((totalProf * 100.0) < profitA.doubleValue() && (totalProf * 100.0) > 0.0) {
                        totals[iCount][0] = p.getNumber()+p.getCompany().getCompany_code();
                        totals[iCount][1] = p.getPm();
                        if(p.getDeliveryDate() != null) {
                            totals[iCount][2] = DateFormat.getDateInstance(DateFormat.SHORT).format(p.getDeliveryDate());
                        }
                        totals[iCount][3] = StandardCode.getInstance().formatDouble(new Double(totalProf * 100));
                    }
                }
                else if(taskB.equals("Linguistic Profitability")) {
                    if((linProf * 100.0) < profitA.doubleValue() && (linProf * 100.0) > 0.0) {
                        totals[iCount][0] = p.getNumber()+p.getCompany().getCompany_code();
                        totals[iCount][1] = p.getPm();
                        if(p.getDeliveryDate() != null) {
                            totals[iCount][2] = DateFormat.getDateInstance(DateFormat.SHORT).format(p.getDeliveryDate());
                        }
                        totals[iCount][3] = StandardCode.getInstance().formatDouble(new Double(linProf * 100));
                    }
                }
                else if(taskB.equals("Engineering Profitability")) {
                    if((engProf * 100.0) < profitA.doubleValue() && (engProf * 100.0) > 0.0) {
                        totals[iCount][0] = p.getNumber()+p.getCompany().getCompany_code();
                        totals[iCount][1] = p.getPm();
                        if(p.getDeliveryDate() != null) {
                            totals[iCount][2] = DateFormat.getDateInstance(DateFormat.SHORT).format(p.getDeliveryDate());
                        }
                        totals[iCount][3] = StandardCode.getInstance().formatDouble(new Double(engProf * 100));
                    }
                }
                else if(taskB.equals("Dtp Profitability")) {
                    if((dtpProf * 100.0) < profitA.doubleValue() && (dtpProf * 100.0) > 0.0) {
                        totals[iCount][0] = p.getNumber()+p.getCompany().getCompany_code();
                        totals[iCount][1] = p.getPm();
                        if(p.getDeliveryDate() != null) {
                            totals[iCount][2] = DateFormat.getDateInstance(DateFormat.SHORT).format(p.getDeliveryDate());
                        }
                        totals[iCount][3] = StandardCode.getInstance().formatDouble(new Double(dtpProf * 100));
                    }
                }
                else if(taskB.equals("Other Profitability")) {
                    if((othProf * 100.0) < profitA.doubleValue() && (othProf * 100.0) > 0.0) {
                        totals[iCount][0] = p.getNumber()+p.getCompany().getCompany_code();
                        totals[iCount][1] = p.getPm();
                        if(p.getDeliveryDate() != null) {
                            totals[iCount][2] = DateFormat.getDateInstance(DateFormat.SHORT).format(p.getDeliveryDate());
                        }
                        totals[iCount][3] = StandardCode.getInstance().formatDouble(new Double(othProf * 100));
                    }
                }
                
                //END find profitability
                }
        }
        //END build profit chart
        
        //place results and criteria into request
        request.setAttribute("totals", totals);
        request.setAttribute("profitA", StandardCode.getInstance().formatDouble(profitA));
        request.setAttribute("taskB", taskB);
        request.setAttribute("dateC", DateFormat.getDateInstance(DateFormat.SHORT).format(dateC));
        request.setAttribute("dateD", DateFormat.getDateInstance(DateFormat.SHORT).format(dateD));
        request.setAttribute("results", finalResults);
        String resultsSize;
        if(finalResults != null) {
            resultsSize = "<br>Found " + String.valueOf(finalResults.size()) + " Projects";
        }
        else {
            resultsSize = "<br>Found " + "0" + " Projects";
        }
        double total = 0.0;
        if(results != null) {
            for(ListIterator iter = results.listIterator(); iter.hasNext();) {
                Project p = (Project) iter.next();
                if(p.getProjectAmount() != null) {
                    total += p.getProjectAmount().doubleValue();
                }
            }
        }
        request.setAttribute("total", StandardCode.getInstance().formatDouble(new Double(total)));
        
        request.setAttribute("title", "All Projects with Profitability lower than " + StandardCode.getInstance().formatDouble(profitA) + "% for Task Group " + taskB + " from " + DateFormat.getDateInstance(DateFormat.SHORT).format(dateC) + " to " + DateFormat.getDateInstance(DateFormat.SHORT).format(dateD) + resultsSize);
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
