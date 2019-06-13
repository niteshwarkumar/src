/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.project;

import app.client.Client;
import app.resource.LanguagePair;
import app.resource.RateScoreLanguage;
import app.resource.Resource;
import app.resource.ResourceService;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

/**
 *
 * @author niteshwar
 */
public class ProjectViewTeamPrefVendorUpdateDtp  extends Action {

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

        int counter = Integer.parseInt(request.getParameter("counter"));
        for (int count = 0; count < counter; count++) {
            
            String VendorCheckDTP = request.getParameter("VendorCheckDTP" + String.valueOf(count));
//            String VendorCheckFAQ = request.getParameter("VendorCheckFAQ" + String.valueOf(count));
          
            
        
            
                if (VendorCheckDTP != null ) {

                //get resource and task to bind
                String resourceId = request.getParameter("ResourceId" + String.valueOf(count));
                String dtpId = request.getParameter("DTP" + String.valueOf(count));
//                String linIdE = request.getParameter("Editing" + String.valueOf(count));
//                String linIdP = request.getParameter("Proofreading" + String.valueOf(count));
                List taskList = new ArrayList();
//                if(VendorCheckT != null)
//                 t.add(ProjectService.getInstance().getSingleLinTask(Integer.valueOf(linIdT)));
                if(!StandardCode.getInstance().noNull(dtpId).equalsIgnoreCase(""))
                 taskList.add(ProjectService.getInstance().getSingleDtpTask(Integer.valueOf(dtpId)));
                //bind the task to the resource (team member)
                for(int tl = 0; tl<taskList.size(); tl++) {
                    DtpTask t = (DtpTask)taskList.get(tl);
                    Resource r = ResourceService.getInstance().getSingleResource(Integer.valueOf(resourceId));

//                    //get this resources language pairs
//                    Set languagePairs = r.getLanguagePairs();
//
//                    //the list for display (load all ratescorelanguage entries)
//                    List ratescorelanguages = new ArrayList();
//                    for (Iterator iter = languagePairs.iterator(); iter.hasNext();) {
//                        LanguagePair lp = (LanguagePair) iter.next();
//                        for (Iterator rateScoreIter = lp.getRateScoreLanguages().iterator(); rateScoreIter.hasNext();) {
//                            RateScoreLanguage rsl = (RateScoreLanguage) rateScoreIter.next();
//                            ratescorelanguages.add(rsl);
//                        }
//
//                    }
//
//                    //prepare the ratescorelanguages and rateScoreDtps for display
//                    RateScoreLanguage[] ratescorelanguagesArray = (RateScoreLanguage[]) ratescorelanguages.toArray(new RateScoreLanguage[0]);
//
                    t.setPersonName(resourceId);
                    t.setInternalCurrency(r.getCurrency());

//
//                    //Map Fee amount from Rate/Score tab
//                    Client c = t.getTargetDoc().getSourceDoc().getProject().getCompany();
//                    String clientSpec = c.getIndustry().getDescription();
//
//
//                    for (int i = 0; i < ratescorelanguagesArray.length; i++) {
//
//                        // //System.out.println("ratescorelanguagesArray.getSource():"+ratescorelanguagesArray[i].getSource());
//                        // //System.out.println("ratescorelanguagesArray.getTarget():"+ratescorelanguagesArray[i].getTarget());
//                        if (ratescorelanguagesArray[i].getSource().equals(t.getSourceLanguage()) && ratescorelanguagesArray[i].getTarget().equals(t.getTargetLanguage())) {
//                            // //System.out.println("ratescorelanguagesArray[i].getSpecialty()="+ratescorelanguagesArray[i].getSpecialty());
//
//                            if (("Medical".equals(ratescorelanguagesArray[i].getSpecialty()) && "Medical, Dental, Pharmaceutical".equals(clientSpec))
//                                    || ("Software".equals(ratescorelanguagesArray[i].getSpecialty()) && "Computer: Hardware & Software, Localization".equals(clientSpec))
//                                    || ("Legal/Financial".equals(ratescorelanguagesArray[i].getSpecialty()) && "Business & Finance & Legal: Banking, Commerce, Management".equals(clientSpec))
//                                    || "Technical".equals(ratescorelanguagesArray[i].getSpecialty())) {
//
//                                if ("Translation".equalsIgnoreCase(t.getTaskName())&& VendorCheckE == null && VendorCheckP == null) {
//                                    if(VendorCheckT != null){
//                                        t.setInternalRate("" + ratescorelanguagesArray[i].getT());
//                                        t.setMulti("");
//                                        t.setMinFee(0.00);
//                                    }else if(VendorCheckTE != null){
//                                        t.setInternalRate("" + ratescorelanguagesArray[i].getTe());
//                                        t.setMulti("T&E");
//                                        t.setMinFee(0.00);
//                                    }else if(VendorCheckMF != null){
//                                        t.setMinFee(ratescorelanguagesArray[i].getMin());
//                                        t.setMulti("Minimum Fee");
//                                        t.setInternalRate("");
//                                    }
//                                    ProjectService.getInstance().updateLinTask(t);
//                                    //  //System.out.println("Setting rate for Translation:"+ratescorelanguagesArray[i].getT());
//                                    break;
//                                } else if ("Editing".equalsIgnoreCase(t.getTaskName())&& VendorCheckE != null) {
//                                    t.setInternalRate("" + ratescorelanguagesArray[i].getE());
//                                    ProjectService.getInstance().updateLinTask(t);
//                                    //  //System.out.println("Setting rate for Editing"+ratescorelanguagesArray[i].getE());
//                                    break;
//                                } else if (t.getTaskName().contains("Proofreading")&& VendorCheckP != null) {
//                                    t.setInternalRate("" + ratescorelanguagesArray[i].getP());
//                                    ProjectService.getInstance().updateLinTask(t);
//                                    // //System.out.println("Setting rate for Proofreading:"+ratescorelanguagesArray[i].getP());
//                                    break;
//                                }
//
//                            }
//                        }
//
//                    }

                    ProjectService.getInstance().updateDtpTask(t);

                }
            }
        }

        return (mapping.findForward("Success"));
    }
    // Forward control to the specified success URI

}
