//ProjectViewTeamUpdateAction.java updates team/tracking info
//for a project

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
import java.util.*;
import java.text.*;
import app.user.*;
import app.db.*;
import app.workspace.*;
import app.security.*;
import app.resource.*;
import app.project.*;
import app.standardCode.*;
import org.apache.struts.validator.*;
import app.client.*;


public final class ProjectViewFeeUpdateAction extends Action {
    
    
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
        for(int i = 0; i < linTasks.length; i++) {
            LinTask lt = linTasks[i];
            
            //START process new total values (2 of them: Total and dollar TOTAL)
       if(!"Y".equals(request.getParameter("comingFromFeeScreen"))){
            //if((lt.getWord100() != null  && !lt.getWord100().equals(new Integer(0))) || (lt.getWordRep() != null  && !lt.getWordRep().equals(new Integer(0))) || (lt.getWord85() != null  && !lt.getWord85().equals(new Integer(0))) || (lt.getWordNew() != null  && !lt.getWordNew().equals(new Integer(0))) || (lt.getWordNew4() != null  && !lt.getWordNew4().equals(new Double(0))) || (lt.getWordNew4() != null  && !lt.getWordNew4().equals(new Double(0)))) { //trados
                int word100 = 0;
                int wordRep = 0;
                int word95 = 0;
                int word85 = 0;
                int word75 = 0;
                int wordNew = 0;
                int word8599 = 0;
                double wordNew4 = 0;
                
                if(lt.getWord100() != null) {
                    word100 = lt.getWord100().intValue();
                }
                if(lt.getWordRep() != null) {
                    wordRep = lt.getWordRep().intValue();
                }
                if(lt.getWord95() != null) {
                    word95 = lt.getWord95().intValue();
                }
                if(lt.getWord85() != null) {
                    word85 = lt.getWord85().intValue();
                }
                if(lt.getWord75() != null) {
                    word75 = lt.getWord75().intValue();
                }
                if(lt.getWordNew() != null) {
                    wordNew = lt.getWordNew().intValue();
                }
                if(lt.getWord8599() != null) {
                    word8599 = lt.getWord8599().intValue();
                }
                if(lt.getWordNew4() != null) {
                    wordNew4 = lt.getWordNew4().doubleValue();
                }
                
                
                double rate = 0;
                try {
                    rate = Double.valueOf(lt.getRate()).doubleValue();
                } catch(java.lang.NumberFormatException nfe) {
                    rate = 0;
                }
                
                double wordTotal = word100 + wordRep + word8599 + wordNew4;
                
                double thisTotal = 0.0;
                //scale the rates (either default or custom)
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
                
                linTotal += thisTotal; //update lin block
                
                lt.setWordTotal(new Double(wordTotal));
                lt.setDollarTotal(StandardCode.getInstance().formatDouble(new Double(thisTotal)));
                lt.setRate(StandardCode.getInstance().formatDouble3(new Double(rate)));
                
           /* } else { //non-trados; such as hourly
                if(lt.getWordTotal() != null) { //if from delete then make sure it is not null
                    double total = lt.getWordTotal().doubleValue();
                    double rate = 0;
                    try {
                        rate = Double.valueOf(lt.getRate()).doubleValue();
                    } catch(java.lang.NumberFormatException nfe) {
                        rate = 0;
                    }
                    
                    double thisTotal = total*rate;
                    linTotal += thisTotal; //update lin block
                    
                    lt.setDollarTotal(StandardCode.getInstance().formatDouble(new Double(thisTotal)));
                    lt.setRate(StandardCode.getInstance().formatDouble3(new Double(rate)));
                }
            }*/
            
          }else{//start FEE screen processing
                
            //if((lt.getWord100Fee() != null  && !lt.getWord100Fee().equals(new Integer(0))) || (lt.getWordRepFee() != null  && !lt.getWordRepFee().equals(new Integer(0))) || (lt.getWord85Fee() != null  && !lt.getWord85Fee().equals(new Integer(0))) || (lt.getWordNewFee() != null  && !lt.getWordNewFee().equals(new Integer(0))) || (lt.getWordNew4Fee() != null  && !lt.getWordNew4Fee().equals(new Double(0)))) { //trados
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
                
                double rate = 0;
                try {
                    String unformattedRate = lt.getRateFee().replaceAll(",", "");
                    rate = Double.valueOf(unformattedRate).doubleValue();
                } catch(java.lang.NumberFormatException nfe) {
                    rate = 0;
                }
                
                double wordTotal = word100 + wordRep + word8599 + wordNew4;
                
                double thisTotal = 0.0;
                //scale the rates (either default or custom)
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
                
                linTotal += thisTotal; //update lin block
                
                lt.setWordTotalFee(new Double(wordTotal));
                
                //Let user overwrite automatic calculations
                //if(lt.getDollarTotalFee()==null || "".equals(lt.getDollarTotalFee())){
                    lt.setDollarTotalFee(StandardCode.getInstance().formatDouble(new Double(thisTotal)));
                //}
                
                lt.setRateFee(StandardCode.getInstance().formatDouble3(new Double(rate)));
           /*} else { //non-trados; such as hourly
                if(lt.getWordTotalFee() != null) { //if from delete then make sure it is not null
                    double total = lt.getWordTotalFee().doubleValue();
                    double rate = 0;
                    try {
                        rate = Double.valueOf(lt.getRateFee()).doubleValue();
                    } catch(java.lang.NumberFormatException nfe) {
                        rate = 0;
                    }
                    
                    double thisTotal = total*rate;
                    linTotal += thisTotal; //update lin block
                    //if(lt.getDollarTotalFee()==null || "".equals(lt.getDollarTotalFee())){
                        lt.setDollarTotalFee(StandardCode.getInstance().formatDouble(new Double(thisTotal)));
                   // }
                    lt.setRateFee(StandardCode.getInstance().formatDouble3(new Double(rate)));
                }
            }*/
            /*
             //Update a row if fee is updated first and nothing was previously entered for Team values
             if(lt.getInternalDollarTotal()==null || "0.00".equals(lt.getInternalDollarTotal())){
                //Now, copy values to team fields
                if(lt.getNotesFee()!=null)
                    lt.setNotes(lt.getNotesFee());
                //etc...
                if(lt.getUnitsFee()!=null)
                    lt.setUnits(lt.getUnitsFee());
                if(lt.getWord100Fee()!=null)
                    lt.setWord100(lt.getWord100Fee());
                if(lt.getWordRepFee()!=null)
                    lt.setWordRep(lt.getWordRepFee());
                if(lt.getWord95Fee()!=null)
                    lt.setWord95 (lt.getWord95Fee());
                if(lt.getWord85Fee()!=null)
                    lt.setWord85(lt.getWord85Fee());
                if(lt.getWord75Fee()!=null)
                    lt.setWord75(lt.getWord75Fee());
                if(lt.getWordNewFee()!=null)
                    lt.setWordNew(lt.getWordNewFee());
                if(lt.getWord8599Fee()!=null)
                    lt.setWord8599(lt.getWord8599Fee());
                if(lt.getWordNew4Fee()!=null)
                    lt.setWordNew4(lt.getWordNew4Fee());
                if(lt.getWordTotalFee()!=null)
                    lt.setWordTotal(lt.getWordTotalFee());
                if(lt.getCurrencyFee()!=null)
                    lt.setInternalCurrency(lt.getCurrencyFee());
                if(lt.getRateFee()!=null)
                    lt.setInternalRate(lt.getRateFee());
                if(lt.getDollarTotalFee()!=null)
                    lt.setInternalDollarTotal(lt.getDollarTotalFee());
           
          } */
            
          
           
            }//END process FEE values
            
            //update to db
            ProjectService.getInstance().updateLinTask(lt);
            
        }//END LIN TASKS
        
        //START ENG TASKS
        //get the updated list of eng Tasks for this project
        EngTask[] engTasks = (EngTask[]) qvg.get("engTasksProject");
        
        //process each eng Task to db
        for(int i = 0; i < engTasks.length; i++) {
            EngTask et = engTasks[i];
            
            //START process new TOTAL value
            if(et.getTotal() != null) { //if from delete then make sure it is not null
                double total = et.getTotal().doubleValue();
                double rate = 0;
                try {
                    rate = Double.valueOf(et.getRate()).doubleValue();
                } catch(java.lang.NumberFormatException nfe) {
                    rate = 0;
                }
                
                double thisTotal = total*rate;
                engTotal += thisTotal; //update eng block
                
                et.setDollarTotal(StandardCode.getInstance().formatDouble(new Double(thisTotal)));
                et.setRate(StandardCode.getInstance().formatDouble3(new Double(rate)));
            }
            //END process new total value
            
            //update to db
            ProjectService.getInstance().updateEngTask(et);
            
        }//END ENG TASKS
        
        //START DTP TASKS
        //get the updated list of dtp Tasks for this project
        DtpTask[] dtpTasks = (DtpTask[]) qvg.get("dtpTasksProject");
        
        //process each dtp Task to db
        for(int i = 0; i < dtpTasks.length; i++) {
            DtpTask dt = dtpTasks[i];
            
            //START process new TOTAL value
            if(dt.getTotal() != null) { //if from delete then make sure it is not null
                double total = dt.getTotal().doubleValue();
                double rate = 0;
                try {
                    rate = Double.valueOf(dt.getRate()).doubleValue();
                } catch(java.lang.NumberFormatException nfe) {
                    rate = 0;
                }
                
                double thisTotal = total*rate;
                dtpTotal += thisTotal; //update dtp block
                
                dt.setDollarTotal(StandardCode.getInstance().formatDouble(new Double(thisTotal)));
                dt.setRate(StandardCode.getInstance().formatDouble3(new Double(rate)));
            }
            //END process new total value
            
            //update to db
            ProjectService.getInstance().updateDtpTask(dt);
            
        }//END DTP TASKS
        
        //START OTH TASKS
        //get the updated list of oth Tasks for this project
        OthTask[] othTasks = (OthTask[]) qvg.get("othTasksProject");
        
        //process each oth Task to db
        for(int i = 0; i < othTasks.length; i++) {
            OthTask ot = othTasks[i];
            
            //START process new TOTAL value
            if(ot.getTotal() != null) { //if from delete then make sure it is not null
                double total = ot.getTotal().doubleValue();
                double rate = 0;
                try {
                    rate = Double.valueOf(ot.getRate()).doubleValue();
                } catch(java.lang.NumberFormatException nfe) {
                    rate = 0;
                }
                
                double thisTotal = total*rate;
                othTotal += thisTotal; //update oth block
                
                ot.setDollarTotal(StandardCode.getInstance().formatDouble(new Double(thisTotal)));
                ot.setRate(StandardCode.getInstance().formatDouble3(new Double(rate)));
            }
            //END process new total value
            
            //update to db
            ProjectService.getInstance().updateOthTask(ot);
            
        }//END OTH TASKS
        
        //START subTotal value
        double subTotal = linTotal + engTotal + dtpTotal + othTotal;
        p.setSubDollarTotal(StandardCode.getInstance().formatDouble(new Double(subTotal)));
        //END subTotal value
        
        //START pm block
        //percent values from form
        String pmPercent = (String) qvg.get("pmPercent");
        
        double pmRate = 0;
        double pmPercentDollarTotal = 0;
        if(pmPercent != null) { //if pmPercent is present
            try {
                pmRate = Double.valueOf(pmPercent).doubleValue();
            } catch(java.lang.NumberFormatException nfe) {
                pmRate = 0;
            }
            pmPercentDollarTotal = (pmRate / 100) * subTotal;
        }
        
        //set totals for each line to project object
        p.setPmPercentDollarTotal(StandardCode.getInstance().formatDouble(new Double(pmPercentDollarTotal)));
        p.setPmPercent(StandardCode.getInstance().formatDouble(new Double(pmRate)));
        //END pm block
        
        
        //START sub total with pm block
        double subPmTotal = subTotal + pmPercentDollarTotal;
        p.setSubPmDollarTotal(StandardCode.getInstance().formatDouble(new Double(subPmTotal)));
        //END sub total with pm block
        
        
        //START rush block
        String rushPercent = (String) qvg.get("rushPercent");
        
        double rushRate = 0;
        double rushPercentDollarTotal = 0;
        if(rushPercent != null) { //if rushPercent is present
            try {
                rushRate = Double.valueOf(rushPercent).doubleValue();
            } catch(java.lang.NumberFormatException nfe) {
                rushRate = 0;
            }
            rushPercentDollarTotal = (rushRate / 100) * subPmTotal;
        }
        
        p.setRushPercentDollarTotal(StandardCode.getInstance().formatDouble(new Double(rushPercentDollarTotal)));
        p.setRushPercent(StandardCode.getInstance().formatDouble(new Double(rushRate)));
        //END rush block
        
        
        //START total value
        double projectTotal = subPmTotal + rushPercentDollarTotal;
        p.setProjectAmount(new Double(projectTotal));
        //END total value
        User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
        p.setLastModifiedBy(u.getFirstName() + " " + u.getLastName());
        p.setLastModifiedDate(new Date(System.currentTimeMillis()));
        //update project to db
        ProjectService.getInstance().updateProject(p);
        
        //mark AutoUpdate as true
        if(autoUpdate != null) {
            Integer newAutoUpdate = new Integer(autoUpdate.intValue() + 1);
            request.setAttribute("AutoUpdate", newAutoUpdate); //place true value (1) into request
        }
        
        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
    
}
