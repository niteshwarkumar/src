/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import app.security.SecurityService;

import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;
import app.standardCode.*;
import java.util.Date;

/**
 *
 * @author Niteshwar
 */
public class QMSLibraryApproveDoc extends Action {

    // ----------------------------------------------------- Instance Variables
    /**
     * The
     * <code>Log</code> instance for this application.
     */
    private Log log =
            LogFactory.getLog("org.apache.struts.webapp.Example");

    // --------------------------------------------------------- Public Methods
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an
     * <code>ActionForward</code> instance describing where and how control
     * should be forwarded, or
     * <code>null</code> if the response has already been completed.
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
        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
        String userName = u.getFirstName() + " " + u.getLastName();
        String id = request.getParameter("id");
        String mainTab = request.getParameter("mainTab");
        String person = request.getParameter("person");
        String click = StandardCode.getInstance().noNull(request.getParameter("click"));
        request.setAttribute("id", id);
        request.setAttribute("mainTab", mainTab);
        String signPerson = "";
        String errorMsg = "";
        String releaseDate = "";
        if (id != null) {
            QMSLibrary qms = QMSService.getInstance().getSingleQMSLibraryDocument(Integer.parseInt(id));
            DynaValidatorForm qvg = (DynaValidatorForm) form;
            if (person.equalsIgnoreCase("person1")) {
                signPerson = (String) qvg.get("person1");
                if (userName.equalsIgnoreCase(signPerson)) {
                    qms.setSign1(true);
                    qms.setPerson1(signPerson);
                    qms.setType1((String) qvg.get("type1"));
//                    if(click.equalsIgnoreCase("true1")){
//                      qms.setSign1(false);
//                      qms.setPerson1("");
//                    }
                } else {
                    errorMsg = "Please Sign in your name.";
                }
                releaseDate = (String) qvg.get("date1");
                try {
                    if (releaseDate.length() > 0) { //if present
                        if(releaseDate.contains("-")){
                            qms.setDate1(DateService.getInstance().convertDate1(releaseDate).getTime());
                        }else {
                        qms.setDate1(DateService.getInstance().convertDate(releaseDate).getTime());
                    }}
                } catch (Exception e) {
                    System.out.println("Date Errooooorr " + e.getMessage());
                }

            }

            if (person.equalsIgnoreCase("person2")) {
                signPerson = (String) qvg.get("person2");
                if (userName.equalsIgnoreCase(signPerson)) {
                    qms.setSign2(true);
                    qms.setPerson2(signPerson);
                    qms.setType2((String) qvg.get("type2"));
//                     if(click.equalsIgnoreCase("true2")){
//                     qms.setSign2(false);
//                      qms.setPerson2("");
//                    }
                } else {
                    errorMsg = "Please Sign in your name.";
                }
                releaseDate = (String) qvg.get("date2");
                try {
                    if (releaseDate.length() > 0) { //if present
                        if(releaseDate.contains("-")){
                            qms.setDate2(DateService.getInstance().convertDate1(releaseDate).getTime());
                        }else {
                        qms.setDate2(DateService.getInstance().convertDate(releaseDate).getTime());
                    }}
                } catch (Exception e) {
                    System.out.println("Date Errooooorr " + e.getMessage());
                }
            }

            if (person.equalsIgnoreCase("person3")) {
                signPerson = (String) qvg.get("person3");
                if (userName.equalsIgnoreCase(signPerson)) {
                    qms.setSign3(true);
                    qms.setPerson3(signPerson);
                    qms.setType3((String) qvg.get("type3"));
//                    if(click.equalsIgnoreCase("true3")){
//                     qms.setSign3(false);
//                      qms.setPerson3("");
//                    }
                } else {
                    errorMsg = "Please Sign in your name.";

                }
                releaseDate = (String) qvg.get("date3");
                try {
                    if (releaseDate.length() > 0) { //if present
                        if(releaseDate.contains("-")){
                            qms.setDate3(DateService.getInstance().convertDate1(releaseDate).getTime());
                        }else {
                        qms.setDate3(DateService.getInstance().convertDate(releaseDate).getTime());
                    }}
                } catch (Exception e) {
                    System.out.println("Date Errooooorr " + e.getMessage());
                }
            }




            Integer id1 = QMSService.getInstance().addLibrary(qms);

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

            qvg.set("type1", "" + qms.getType1());
            qvg.set("type2", "" + qms.getType2());
            qvg.set("type3", "" + qms.getType3());

            if (qms.getDate1() != null) {
                qvg.set("date1", StandardCode.getInstance().noNull("" + qms.getDate1()));
            }
            if (qms.getDate2() != null) {
                qvg.set("date2", StandardCode.getInstance().noNull("" + qms.getDate2()));
            }
            if (qms.getDate3() != null) {
                qvg.set("date3", StandardCode.getInstance().noNull("" + qms.getDate3()));
            }



            request.setAttribute("errorMsg", errorMsg);
        }
        if (errorMsg.length() > 10) {
            return (mapping.findForward("Success"));
        }
        return (mapping.findForward("Matrix"));
    }
}
