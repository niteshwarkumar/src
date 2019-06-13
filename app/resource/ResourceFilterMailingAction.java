/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.resource;

import app.admin.AdminService;
import app.admin.MailingList;
import app.admin.MailingListVendor;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import java.util.ArrayList;
import java.util.List;
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
import org.apache.struts.validator.DynaValidatorForm;

/**
 *
 * @author abhisheksingh
 */
public class ResourceFilterMailingAction extends Action {

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

        //load resource info for searching
        DynaValidatorForm rs = (DynaValidatorForm) form;

//        //values for search
        String SourceId = request.getParameter("sourceId");
        String TargetId = request.getParameter("targetId");

//   medicalScore,technicalScore,softwareScore,legalScore,marketingScore
        String medicalScore = request.getParameter("resourceMedicalScore");
        String technicalScore = request.getParameter("resourceTechnicalScores");
        String softwareScore = request.getParameter("resourceSoftwareScores");
        String legalScore = request.getParameter("resourceLegalScores");
        String marketingScore = request.getParameter("resourceMarketingScore");

        String specialty = request.getParameter("specialty");
        String prefferedVend = request.getParameter("prefferedVend");
        boolean isPrefVen = false;
        
        if (StandardCode.getInstance().noNull(prefferedVend).equalsIgnoreCase("on")) {
            isPrefVen = true;
        }

        String pc_criteria = request.getParameter("pc_gtlt");
        String projectCount = request.getParameter("projectCount");

        String currency = request.getParameter("currency");
//        String approved =  request.getParameter("capproved");
//        String notApproved =  request.getParameter("cnotApproved");
//        
        Integer mailingId = 0;
        if (null != request.getAttribute("mailingId")) {
            mailingId = (Integer) request.getAttribute("mailingId");
        } else {
            mailingId = Integer.parseInt(request.getParameter("mailingId"));
        }

        MailingList ml = AdminService.getInstance().getSingleMailingList(mailingId);
        if (ml == null) {
            ml = new MailingList();
        }

        ml.setFilter1(currency);
        ml.setFilter2(specialty);
        ml.setFilter3(SourceId);
        ml.setFilter4(TargetId);
        ml.setFilter5(medicalScore);
        ml.setFilter6(technicalScore);
        ml.setFilter7(softwareScore);
        ml.setFilter8(legalScore);
        ml.setFilter9(marketingScore);
        ml.setFilter10(prefferedVend);
        ml.setFilter11(pc_criteria);
        ml.setFilter12(projectCount);

        AdminService.getInstance().saveMailingList(ml);

        //perform search and store results in a List
        //perform main search 
        List results = ResourceService.getInstance().getResourceSearch(currency, SourceId, TargetId, specialty, medicalScore, technicalScore, softwareScore, legalScore, marketingScore, 
                isPrefVen,pc_criteria,projectCount);

        //place results in attribute for displaying in jsp
        List finalResults = new ArrayList();
        if (null != results) {
//            finalResults.add(results);
            request.setAttribute("finalResults", results);
            request.setAttribute("finalResultsStatus", "<span style='color:green'>(" + results.size() + " Team found.)<span>");
        } else {
            request.setAttribute("finalResults", ResourceService.getInstance().getResourceList());
            request.setAttribute("finalResultsStatus", "<span style='color:red'>(No Team found.)<span>");
        }

//        request.setAttribute("finalResults", finalResults);
        request.setAttribute("resourceMedicalScore", medicalScore);
        request.setAttribute("resourceTechnicalScores", technicalScore);
        request.setAttribute("resourceSoftwareScores", softwareScore);
        request.setAttribute("resourceLegalScores", legalScore);
        request.setAttribute("resourceMarketingScore", marketingScore);

        request.setAttribute("specialty", specialty);
        request.setAttribute("currency", currency);
        request.setAttribute("targetId", TargetId);
        request.setAttribute("sourceId", SourceId);
        request.setAttribute("mailingId", mailingId);
        if (pc_criteria.equalsIgnoreCase("le")) {
                request.setAttribute("pc_criteria_ge", "");
                request.setAttribute("pc_criteria_le", "selected");
            } else {
                request.setAttribute("pc_criteria_ge", "selected");
                request.setAttribute("pc_criteria_le", "");
            }
        request.setAttribute("projectCount", projectCount);
        if (isPrefVen) {
            request.setAttribute("preffered", "checked");
        } else {
            request.setAttribute("preffered", "");
        }
        List mailingList = AdminService.getInstance().getMailingList(mailingId, "V");
        List mailingIdList = new ArrayList();
        if (null != mailingList) {
            for (int i = 0; i < mailingList.size(); i++) {
                MailingListVendor mlv = (MailingListVendor) mailingList.get(i);
                mailingIdList.add(mlv.getVendorId());
            }
        } else {
            mailingIdList = null;
        }
        request.setAttribute("mailingIdList", mailingIdList);

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }

}
