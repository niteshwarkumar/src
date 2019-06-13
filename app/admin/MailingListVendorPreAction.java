/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.admin;

import app.resource.ResourceService;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
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

/**
 *
 * @author Nishika
 */
public class MailingListVendorPreAction extends Action {

    private Log log = LogFactory.getLog("org.apache.struts.webapp.Example");

    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // Extract attributes we will nee
        MessageResources messages = getResources(request);

        // save errors
        ActionMessages errors = new ActionMessages();

        //START check for login (security)
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
        Integer mailingId = 0;
        if (null != request.getAttribute("mailingId")) {
            mailingId = (Integer) request.getAttribute("mailingId");
        } else {
            mailingId = Integer.parseInt(request.getParameter("mailingId"));
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

        MailingList ml = AdminService.getInstance().getSingleMailingList(mailingId);
        if (null != ml) {
            String SourceId = StandardCode.getInstance().noNull(ml.getFilter3());
            String TargetId = StandardCode.getInstance().noNull(ml.getFilter4());
            String medicalScore = StandardCode.getInstance().noNull(ml.getFilter5());
            String technicalScore = StandardCode.getInstance().noNull(ml.getFilter6());
            String softwareScore = StandardCode.getInstance().noNull(ml.getFilter7());
            String legalScore = StandardCode.getInstance().noNull(ml.getFilter8());
            String marketingScore = StandardCode.getInstance().noNull(ml.getFilter9());

            String specialty = StandardCode.getInstance().noNull(ml.getFilter2());

            String currency = StandardCode.getInstance().noNull(ml.getFilter1());

            String prefferedVend = StandardCode.getInstance().noNull(ml.getFilter10());
            String pc_criteria = StandardCode.getInstance().noNull(ml.getFilter11());
            String projectCount = StandardCode.getInstance().noNull(ml.getFilter12());
            boolean isPrefVen = false;
            if (prefferedVend.equalsIgnoreCase("on")) {
                isPrefVen = true;
            }

            List results = ResourceService.getInstance().getResourceSearch(currency, SourceId, TargetId, specialty, medicalScore, technicalScore, softwareScore, legalScore, marketingScore,
                    isPrefVen, pc_criteria, projectCount);

            //place results in attribute for displaying in jsp
            List finalResults = new ArrayList();
            if (null != results) {
//            finalResults.add(results);
                request.setAttribute("finalResults", results);
            } else {
                request.setAttribute("finalResults", ResourceService.getInstance().getResourceList());
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

        } else {
            request.setAttribute("finalResults", ResourceService.getInstance().getResourceList());
        }

        request.setAttribute("mailingIdList", mailingIdList);
        request.setAttribute("mailingId", mailingId);
//    request.setAttribute("vendorList", ResourceService.getInstance().getResourceList());
////    Session session = ConnectionFactory.getInstance().getSession();
//    List vendorList = ResourceService.getInstance().getResourceList();
//    for (int i = 0; i < vendorList.size(); i++) {
//        Resource r = (Resource) vendorList.get(i);
//        
////        Hibernate.initialize(r.getResourceContacts());
//        Set contacts = r.getResourceContacts();
//       
//        //System.out.println(""+contacts.size());
//    }
        return (mapping.findForward("Success"));
    }
}
