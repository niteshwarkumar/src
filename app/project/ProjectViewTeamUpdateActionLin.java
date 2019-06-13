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
import app.resource.*;
import app.standardCode.*;
import org.apache.struts.validator.*;

public final class ProjectViewTeamUpdateActionLin extends Action {

    // ----------------------------------------------------- Instance Variables
    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log
            = LogFactory.getLog("org.apache.struts.webapp.Example");

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
//        Integer newScaleprojectId =11713;
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
        for (int i = 0; i < linTasks.length; i++) {
            LinTask lt = linTasks[i];
            double scale100 = 0.25;
            double scaleRep = 0.25;
            double scale8599 = 0.50;
            double scaleNew4 = 1.0;
            double scalePerfect = 0.20;
            double scaleContext = 0.20;
            double scale95 = 0.50;
            double scale85 = 0.50;
            double scale75 = 0.60;
            double scaleNew = 1.0;

//            if(id<newScaleprojectId){
//            try {
//                Resource res = ResourceService.getInstance().getSingleResource(Integer.parseInt(lt.getPersonName()));
//
//                scale100 = new Double(res.getScale100());
//                scaleRep = new Double(res.getScaleRep());
//                scale8599 = new Double(res.getScale8599());
//                scaleNew4 = new Double(res.getScaleNew4());
//                scalePerfect = new Double(res.getScalePerfect());
//                scaleContext = new Double(res.getScaleContext());
//                scale95 = new Double(res.getScale95());
//                scale85 = new Double(res.getScale85());
//                scale75 = new Double(res.getScale75());
//                scaleNew = new Double(res.getScaleNew());
//            } catch (Exception e) {
//            }
//            }else{
//                        if(id<newScaleprojectId){
            try {
                Resource res = ResourceService.getInstance().getSingleResource(Integer.parseInt(lt.getPersonName()));
                if (res.isDefaultRate()) {
                    if (null != lt.getScale()) {
                        scale100 = new Double(lt.getScale().getScale100());
                        scaleRep = new Double(lt.getScale().getScaleRep());
                        scaleNew4 = new Double(lt.getScale().getScaleNew());
                        scalePerfect = new Double(lt.getScale().getScalePerfect());
                        scaleContext = new Double(lt.getScale().getScaleContext());
                        scale95 = new Double(lt.getScale().getScale95());
                        scale85 = new Double(lt.getScale().getScale85());
                        scale75 = new Double(lt.getScale().getScale75());
                        scaleNew = new Double(lt.getScale().getScaleNew());
                    } else {
                        scale100 = new Double(res.getScale100());
                        scaleRep = new Double(res.getScaleRep());
//                    scale8599 = new Double(res.getScale8599());
                        try {
                            scaleNew4 = new Double(res.getScaleNew4());
                        } catch (Exception e) {
                            scaleNew4 = new Double(res.getScaleNew());
                        }
                        scalePerfect = new Double(res.getScalePerfect());
                        scaleContext = new Double(res.getScaleContext());
                        scale95 = new Double(res.getScale95());
                        scale85 = new Double(res.getScale85());
                        scale75 = new Double(res.getScale75());
                        scaleNew = new Double(res.getScaleNew());
                        Scale scale = new Scale();
                        scale.setScale100(res.getScale100());
                        scale.setScale75(res.getScale75());
                        scale.setScale85(res.getScale85());
                        scale.setScale95(res.getScale95());
                        scale.setScaleContext(res.getScaleContext());
                        scale.setScaleNew(res.getScaleNew());
                        scale.setScalePerfect(res.getScalePerfect());
                        scale.setScaleRep(res.getScaleRep());
                        lt.setScale(scale);
                        ProjectService.getInstance().updateScale(scale);

                    }
                } else {
                    if (null != lt.getScale()) {
                        scale100 = new Double(lt.getScale().getScale100());
                        scaleRep = new Double(lt.getScale().getScaleRep());
//                        scaleNew4 = new Double(lt.getScale().getScaleNew4());
                        scalePerfect = new Double(lt.getScale().getScalePerfect());
                        scaleContext = new Double(lt.getScale().getScaleContext());
                        scale95 = new Double(lt.getScale().getScale95());
                        scale85 = new Double(lt.getScale().getScale85());
                        scale75 = new Double(lt.getScale().getScale75());
                        scaleNew = new Double(lt.getScale().getScaleNew());
                    } else {
                        scale100 = p.getCompany().getScale100_team();
                        scaleRep = p.getCompany().getScaleRep_team();
                        try {
                            scale8599 = p.getCompany().getScale8599_team();
                        } catch (Exception e1) {
                        }
                        try {
                            scaleNew4 = p.getCompany().getScaleNew4_team();
                        } catch (Exception e1) {
                        }
                        scalePerfect = p.getCompany().getScalePerfect_team();
                        scaleContext = p.getCompany().getScaleContext_team();
                        scale95 = p.getCompany().getScale95_team();
                        scale85 = p.getCompany().getScale85_team();
                        scale75 = p.getCompany().getScale75_team();
                        scaleNew = p.getCompany().getScaleNew_team();
                        Scale scale = new Scale();
                        scale.setScale100(p.getCompany().getScale100_team().toString());
                        scale.setScale75(p.getCompany().getScale75_team().toString());
                        scale.setScale85(p.getCompany().getScale85_team().toString());
                        scale.setScale95(p.getCompany().getScale95_team().toString());
                        scale.setScaleContext(p.getCompany().getScaleContext_team().toString());
                        scale.setScaleNew(p.getCompany().getScaleNew_team().toString());
                        scale.setScalePerfect(p.getCompany().getScalePerfect_team().toString());
                        scale.setScaleRep(p.getCompany().getScaleRep_team().toString());
                        lt.setScale(scale);
                        ProjectService.getInstance().updateScale(scale);

                    }
                }
            } catch (Exception e) {
                scale100 = p.getCompany().getScale100_team();
                scaleRep = p.getCompany().getScaleRep_team();
                try {
                    scale8599 = p.getCompany().getScale8599_team();
                } catch (Exception e1) {
                }
                try {
                    scaleNew4 = p.getCompany().getScaleNew4_team();
                } catch (Exception e1) {
                }
                scalePerfect = p.getCompany().getScalePerfect_team();
                scaleContext = p.getCompany().getScaleContext_team();
                scale95 = p.getCompany().getScale95_team();
                scale85 = p.getCompany().getScale85_team();
                scale75 = p.getCompany().getScale75_team();
                scaleNew = p.getCompany().getScaleNew_team();
            }
//            }
//            }else{
//            scale100 = p.getCompany().getScale100_team();
//                scaleRep = p.getCompany().getScaleRep_team();
//                scale8599 = p.getCompany().getScale8599_team();
//                scaleNew4 = p.getCompany().getScaleNew4_team();
//                scalePerfect = p.getCompany().getScalePerfect_team();
//                scaleContext = p.getCompany().getScaleContext_team();
//                scale95 = p.getCompany().getScale95_team();
//                scale85 = p.getCompany().getScale85_team();
//                scale75 = p.getCompany().getScale75_team();
//                scaleNew = p.getCompany().getScaleNew_team();
////            }

            if (lt.getICRcheck() != null) {
                Boolean ICRcheck = lt.getICRcheck().FALSE;

            }

            if (lt.getICRcheck() != null) {
                Boolean ICRcheck = lt.getICRcheck().FALSE;

            }
//            String resourceName = request.getParameter("resourceName" + String.valueOf(i));
//                if (resourceName.length() >= 1) {
//                    Resource r = ResourceService.getInstance().getSingleResourceByUserName(resourceName);
//                } else {
//                    lt.setReceivedDateDate(null);
//                }
//            

            //START process new total values (2 of them: Total and dollar TOTAL)
            // if((lt.getWord100() != null  && !lt.getWord100().equals(new Integer(0))) || (lt.getWordRep() != null  && !lt.getWordRep().equals(new Integer(0))) || (lt.getWord85() != null  && !lt.getWord85().equals(new Integer(0))) || (lt.getWordNew() != null  && !lt.getWordNew().equals(new Integer(0))) || (lt.getWordNew4() != null  && !lt.getWordNew4().equals(new Double(0)))) { //trados
            int word100 = 0;
            int wordRep = 0;
            int word95 = 0;
            int word85 = 0;
            int word75 = 0;
            double wordNew = 0;
            int word8599 = 0;
            int wordContext = 0;
            int wordPerfect = 0;
            double wordNew4 = 0;
            double minFee = 0;

            if (lt.getWord100() != null) {
                word100 = lt.getWord100().intValue();
            }
            if (lt.getWordRep() != null) {
                wordRep = lt.getWordRep().intValue();
            }
            if (lt.getWord95() != null) {
                word95 = lt.getWord95().intValue();
            }
            if (lt.getWord85() != null) {
                word85 = lt.getWord85().intValue();
            }
            if (lt.getWord75() != null) {
                word75 = lt.getWord75().intValue();
            }
            if (lt.getWordNew() != null) {
                wordNew = lt.getWordNew().doubleValue();
            }
            if (lt.getWord8599() != null) {
                word8599 = lt.getWord8599().intValue();
            }
            if (lt.getWordNew4() != null) {
                wordNew4 = lt.getWordNew4();
            }
            if (lt.getWordContext() != null) {
                wordContext = lt.getWordContext().intValue();
            }
            if (lt.getWordPerfect() != null) {
                wordPerfect = lt.getWordPerfect().intValue();
            }

            double rate = 0;
            try {
                String unformattedRate = lt.getInternalRate().replaceAll(",", "");
                rate = Double.valueOf(unformattedRate);
            } catch (java.lang.NumberFormatException nfe) {
                rate = 0;
            }

            double wordTotal = word100 + wordRep + word8599 + wordNew4 + wordContext + wordPerfect;

            if (!p.getCompany().isScaleDefault(p.getProjectId())) {
                wordTotal = word100 + wordRep + word95 + word85 + word75 + wordNew + wordContext + wordPerfect;
            }

            //rate and cost
            double thisTotal = 0.0;
            //scale the rates (either default or custom)
            /// Resource r = null; //get this task's team member to see if default scale used or not
//                if(lt.getPersonName() != null && lt.getPersonName().length() > 0) {
//                  //  r = ResourceService.getInstance().getSingleResource(Integer.valueOf(lt.getPersonName()));
//                }
            if (p.getCompany().isScaleDefault(p.getProjectId())) {
                double rateNew4 = rate * scaleNew4;
                double rate100 = rate * scale100;
                double rateRep = rate * scaleRep;
                double rate8599 = rate * scale8599;
                double ratePerfect = rate * scalePerfect;
                double rateContext = rate * scaleContext;
                double costNew4 = wordNew4 * rateNew4;
                double cost100 = word100 * rate100;
                double costRep = wordRep * rateRep;
                double cost8599 = word8599 * rate8599;
                double costPerfect = wordPerfect * ratePerfect;
                double costContext = wordContext * rateContext;

                thisTotal = costNew4 + cost100 + costRep + cost8599 + costPerfect + costContext;
            } else {
                double rateNew4 = rate * scaleNew4;
                double rate100 = rate * scale100;
                double rateRep = rate * scaleRep;
                double rate7584 = rate * scale75;
                double rate8594 = rate * scale85;
                double rate9599 = rate * scale95;
                double ratePerfect = rate * scalePerfect;
                double rateContext = rate * scaleContext;

                double costNew4 = wordNew * rateNew4;
                double cost100 = word100 * rate100;
                double costRep = wordRep * rateRep;
                double cost7584 = word75 * rate7584;
                double cost8594 = word85 * rate8594;
                double cost9599 = word95 * rate9599;
                double costPerfect = wordPerfect * ratePerfect;
                double costContext = wordContext * rateContext;

                thisTotal = costNew4 + cost100 + costRep + cost7584 + cost8594 + cost9599 + costPerfect + costContext;
                //thisTotal = wordTotal*rate;
            }

            // lt.setMinFee(null);
            try {
                if (!lt.getMulti().equalsIgnoreCase("")) {
                    lt.setMulti(lt.getMulti());
                }

                if (lt.getPersonName() != null && lt.getMulti().equalsIgnoreCase("minimum fee")) {
                    LinTask lintask = ProjectService.getInstance().getSingleLinTask(lt.getLinTaskId());

                    Resource res = ResourceService.getInstance().getSingleResource(Integer.parseInt(lt.getPersonName()));

                    for (Iterator outer = res.getLanguagePairs().iterator(); outer.hasNext();) {
                        LanguagePair lp = (LanguagePair) outer.next();
                        for (Iterator inner = lp.getRateScoreLanguages().iterator(); inner.hasNext();) {
                            RateScoreLanguage rsl = (RateScoreLanguage) inner.next();
                            if (rsl.getSource().equals(lt.getTargetDoc().getSourceDoc().getLanguage()) && rsl.getTarget().equals(lt.getTargetDoc().getLanguage())) {
                                minFee = rsl.getMin();
                            }
                        }
                    }
                    if (lt.getMinFee() != null && !lt.getMinFee().toString().equalsIgnoreCase("")) {
                        minFee = lt.getMinFee();
                    }
                    thisTotal = minFee;
                    lt.setMinFee(minFee);
                } else {
                    lt.setMinFee(null);
                }

            } catch (Exception e) {
            }
            linTotal += thisTotal; //update lin block

            lt.setWordTotal(new Double(wordTotal));
            ////Let user overwrite automatic calculations
            ///ALEX: Do NOT let user overwrite the calculations
            //if(lt.getInternalDollarTotal()==null || "".equals(lt.getInternalDollarTotal())){
            lt.setInternalDollarTotal(StandardCode.getInstance().formatDouble(new Double(thisTotal)));

            // }
            lt.setInternalRate(StandardCode.getInstance().formatDouble4(new Double(rate)));

            //END process new total values (2 of them)
            if (autoUpdate == null) { //update dates only if not updating tasks
                //START update lin dates
                String linTasksProjectSentArray = request.getParameter("linTasksProjectSentArray" + lt.getLinTaskId());
                try {
                    if (linTasksProjectSentArray.length() >= 1) {
                        lt.setSentDateDate(DateService.getInstance().convertDate(linTasksProjectSentArray).getTime());
                    } else {
                        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                        System.out.println("request.getParameter(\"linTasksProjectSentArray\" + lt.getLinTaskId()"); 
                        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//                        lt.setSentDateDate(null);
                    }
                } catch (Exception e) {
                     System.out.println("################################################################################################");
                     e.printStackTrace();
                     System.out.println("################################################################################################");
                }
                String linTasksProjectDueArray = request.getParameter("linTasksProjectDueArray" + lt.getLinTaskId());
                try {
                    if (linTasksProjectDueArray.length() >= 1) {
                        lt.setDueDateDate(DateService.getInstance().convertDate(linTasksProjectDueArray).getTime());
                    } else {
//                        lt.setDueDateDate("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String linTasksProjectReceivedArray = request.getParameter("linTasksProjectReceivedArray" + lt.getLinTaskId());
                try {
                    if (linTasksProjectReceivedArray.length() >= 1) {
                        lt.setReceivedDateDate(DateService.getInstance().convertDate(linTasksProjectReceivedArray).getTime());
                    } else {
//                        lt.setReceivedDateDate("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String linTasksProjectICRSentArray = request.getParameter("linTasksProjectICRSentArray" + lt.getLinTaskId());
                try {
                    if (linTasksProjectICRSentArray.length() >= 1) {
                        lt.setIcrSentDate(DateService.getInstance().convertDate(linTasksProjectICRSentArray).getTime());
                    } else {
//                        lt.setIcrSentDate(null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String linTasksProjectICRReceivedArray = request.getParameter("linTasksProjectICRReceivedArray" + lt.getLinTaskId());
                try {
                    if (linTasksProjectICRReceivedArray.length() >= 1) {
                        lt.setIcrRecievedDate(DateService.getInstance().convertDate(linTasksProjectICRReceivedArray).getTime());
                    } else {
//                        lt.setIcrRecievedDate(null);
                    }
                } catch (Exception e) {
                   e.printStackTrace();
                }

                String linTasksProjectInvoiceArray = request.getParameter("linTasksProjectInvoiceArray" + lt.getLinTaskId());
                try {
                    if (linTasksProjectInvoiceArray.length() >= 1) {
                        lt.setInvoiceDateDate(DateService.getInstance().convertDate(linTasksProjectInvoiceArray).getTime());
                    } else {
//                        lt.setInvoiceDateDate(null);
                    }
                } catch (Exception e) {
                   e.printStackTrace();
                }
                String linTasksProjectICRFinal = request.getParameter("linTasksProjectICRFinalArray");
                lt.setIcrFinal(linTasksProjectICRFinal);

                //END update lin dates
            }

            //update to db
            ProjectService.getInstance().updateLinTask(lt);

        }//END LIN TASKS

        //update project to db
        //NOTE: NO PROJECT TOTALS CHANGES 
        if (request.getParameter("euroToUsdExchangeRate") != null) {
            p.setEuroToUsdExchangeRate(new Double(request.getParameter("euroToUsdExchangeRate")));
        }
        ProjectService.getInstance().updateProject(p);

        //mark AutoUpdate as true        
        if (autoUpdate != null) {
            Integer newAutoUpdate = new Integer(autoUpdate.intValue() + 1);
            request.setAttribute("AutoUpdate", newAutoUpdate); //place true value (1) into request 
        }

        if (request.getParameter("projectInformalsJSON") != null) {

            ProjectHelper.updateProjectReqs(id.intValue(), request.getParameter("projectInformalsJSON"), "LIN");

        }

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
