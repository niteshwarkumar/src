/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import app.security.SecurityService;
import app.standardCode.StandardCode;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 *
 * @author Niteshwar
 */
public class QMSLibraryAddDocumentPreAction extends Action {

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
            HttpServletResponse response) throws Exception {
        // save errors
        ActionMessages errors = new ActionMessages();

        //START check for login (security)
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)

        //PRIVS check that admin user is viewing this page
//        if (!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "admin")) {
//            return (mapping.findForward("accessDenied"));
//        }//END PRIVS check that admin user is viewing this page

        String id = request.getParameter("id");
        String mainTab = request.getParameter("mainTab");
        request.setAttribute("id", id);
        request.setAttribute("mainTab", mainTab);
        if (id != null) {
            QMSLibrary qms = QMSService.getInstance().getSingleQMSLibraryDocument(Integer.parseInt(id));
            DynaValidatorForm qvg = (DynaValidatorForm) form;
            qvg.set("allCheck", "" + qms.isAllCheck());
            qvg.set("pmCheck", "" + qms.isPmCheck());
            qvg.set("engCheck", "" + qms.isEngCheck());
            qvg.set("dtpCheck", "" + qms.isDtpCheck());
            qvg.set("vmCheck", "" + qms.isVmCheck());
            qvg.set("smCheck", "" + qms.isSmCheck());
            qvg.set("manCheck", "" + qms.isManCheck());
            qvg.set("accCheck", "" + qms.isAccCheck());
            qvg.set("hrCheck", "" + qms.isHrCheck());
            qvg.set("itCheck", "" + qms.isItCheck());
            qvg.set("qmCheck", "" + qms.isQmCheck());
            qvg.set("mqaCheck", "" + qms.isMqaCheck());
            qvg.set("other", qms.getOther());

            qvg.set("person1", "" + qms.getPerson1());
            qvg.set("person2", "" + qms.getPerson2());
            qvg.set("person3", "" + qms.getPerson3());

            try {
                if(qms.isAffected()==true){qvg.set("affected", "on" );}
                else if(qms.isAffected()==false){qvg.set("notaffected", "on");}
            } catch (Exception e) {
            }


            

            if (qms.getDate1() != null) {
                qvg.set("date1", StandardCode.getInstance().noNull("" + qms.getDate1()));
            }
            if (qms.getDate2() != null) {
                qvg.set("date2", StandardCode.getInstance().noNull("" + qms.getDate2()));
            }
            if (qms.getDate3() != null) {
                qvg.set("date3", StandardCode.getInstance().noNull("" + qms.getDate3()));
            }
            if (qms.getReleaseDate() != null) {

                qvg.set("release", StandardCode.getInstance().noNull("" + qms.getReleaseDate()));
            }
            qvg.set("htmlLink", qms.getLink());
            qvg.set("docFormat", qms.getFormat());
            qvg.set("owner", qms.getOwner());
            qvg.set("type", qms.getType());
            qvg.set("version", qms.getVersion());
            qvg.set("docId", qms.getDocId());
            qvg.set("category", qms.getCategory());
            qvg.set("description", qms.getDescription());
            qvg.set("title", qms.getTitle());
            qvg.set("isoreference", qms.getIsoreference());
            qvg.set("affectedBox", qms.getAffectedBox());

        }


        return (mapping.findForward("Success"));
    }
}
