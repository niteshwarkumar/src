/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import app.security.SecurityService;
import app.standardCode.StandardCode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
public class QMSLibraryAdminAction extends Action {

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
        if (!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "admin")) {
            return (mapping.findForward("accessDenied"));
        }//END PRIVS check that admin user is viewing this page

        String id = request.getParameter("id");
        String mainTab = request.getParameter("mainTab");


        QMSLibrary qms = QMSService.getInstance().getSingleQMSLibraryDocument(Integer.parseInt(id));
        DynaValidatorForm qvg = (DynaValidatorForm) form;

        String allCheck = (String) qvg.get("allCheck");
        String pmCheck = (String) qvg.get("pmCheck");
        String engCheck = (String) qvg.get("engCheck");
        String dtpCheck = (String) qvg.get("dtpCheck");
        String vmCheck = (String) qvg.get("vmCheck");
        String smCheck = (String) qvg.get("smCheck");
        String manCheck = (String) qvg.get("manCheck");
        String accCheck = (String) qvg.get("accCheck");
        String hrCheck = (String) qvg.get("hrCheck");
        String itCheck = (String) qvg.get("itCheck");
        String qmCheck = (String) qvg.get("qmCheck");
        String mqaCheck = (String) qvg.get("mqaCheck");
        qms.setOther((String) qvg.get("other"));

        if (allCheck.equalsIgnoreCase("on")) {
            qms.setAllCheck(true);
        } else {
            qms.setAllCheck(false);
        }
        if (pmCheck.equalsIgnoreCase("on")) {
            qms.setPmCheck(true);
        } else {
            qms.setPmCheck(false);
        }
        if (engCheck.equalsIgnoreCase("on")) {
            qms.setEngCheck(true);
        } else {
            qms.setEngCheck(false);
        }
        if (dtpCheck.equalsIgnoreCase("on")) {
            qms.setDtpCheck(true);
        } else {
            qms.setDtpCheck(false);
        }
        if (vmCheck.equalsIgnoreCase("on")) {
            qms.setVmCheck(true);
        } else {
            qms.setVmCheck(false);
        }
        if (smCheck.equalsIgnoreCase("on")) {
            qms.setSmCheck(true);
        } else {
            qms.setSmCheck(false);
        }
        if (manCheck.equalsIgnoreCase("on")) {
            qms.setManCheck(true);
        } else {
            qms.setManCheck(false);
        }
        if (accCheck.equalsIgnoreCase("on")) {
            qms.setAccCheck(true);
        } else {
            qms.setAccCheck(false);
        }
        if (hrCheck.equalsIgnoreCase("on")) {
            qms.setHrCheck(true);
        } else {
            qms.setHrCheck(false);
        }
        if (itCheck.equalsIgnoreCase("on")) {
            qms.setItCheck(true);
        } else {
            qms.setItCheck(false);
        }
        if (qmCheck.equalsIgnoreCase("on")) {
            qms.setQmCheck(true);
        } else {
            qms.setQmCheck(false);
        }
        if (mqaCheck.equalsIgnoreCase("on")) {
            qms.setMqaCheck(true);
        } else {
            qms.setMqaCheck(false);
        }



Integer idi=QMSServiceAddUpdate.getInstance().addLibrary(qms);
        

QMSServiceDelete.getInstance().deleteIsoDoc(Integer.parseInt(id));
String[] isoStandard  = request.getParameterValues("isoStandard");
if(isoStandard != null){
    for(String iso : isoStandard){
        IsoDoc isoDoc = new IsoDoc();
        isoDoc.setDocId(Integer.parseInt(id));
        isoDoc.setIsoStandard(Integer.parseInt(iso));
        QMSServiceAddUpdate.getInstance().addIsoDoc(isoDoc);
    }
}
        List<String> isoKeyHeader = new ArrayList();
        List<String> isoKey = new ArrayList();
        String[] isoKeyHeaderArr = request.getParameterValues("isoKeyHeader");
        String[] isoKeyArr = request.getParameterValues("isoKey");
        if(isoKeyHeaderArr!=null) isoKeyHeader =  Arrays.asList(isoKeyHeaderArr);
        if(isoKeyArr!=null) isoKey =  Arrays.asList(isoKeyArr);
        request.setAttribute("isoKeyHeader", isoKeyHeader); 
        request.setAttribute("isoKey", isoKey);
        request.setAttribute("isoHeader",false);
        
        return (mapping.findForward(mainTab));
    }
}
