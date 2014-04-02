/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.*;
import java.util.*;
import app.project.*;
import app.standardCode.*;
import app.security.*;
import app.user.Location;
import app.user.UserService;

/**
 *
 * @author Niteshwar
 */
public class AdminMiscLocationUpdateAction extends Action {

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

        //PRIVS check that admin user is viewing this page
        if (!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "admin")) {
            return (mapping.findForward("accessDenied"));
        }//END PRIVS check that admin user is viewing this page

        //get languages
//        List languages = ProjectService.getInstance().getLanguageList();

        //place languages into form
//        DynaValidatorForm amg = (DynaValidatorForm) form;
//        Language[] languagesArray = (Language[]) languages.toArray(new Language[0]);
//        amg.set("languages", languagesArray);
        String location = "";
        String friendlyName = "";
        String address1 = "";
        String address2 = "";
        String city = "";
        String state = "";
        String zip = "";
        String country = "";
        String telno = "";
        String extno = "";
        String faxno = "";
        String url = "www.xltrans.com";
        String emailadd = "";
        String note = "";
        String locId = "";
        Location officeLocation = null;

        try {
            locId = request.getParameter("locIdString");
        } catch (Exception e) {
        }

        if (locId == null) {
            officeLocation = new Location();
        } else {
            officeLocation = UserService.getInstance().getSingleLocationByLocId(Integer.parseInt(locId));
        }


        try {
            location = request.getParameter("location1");
        } catch (Exception e) {
        }
        try {
            friendlyName = request.getParameter("friendlyName");
        } catch (Exception e) {
        }
        try {
            address1 = request.getParameter("address1");
        } catch (Exception e) {
        }
        try {
            address2 = request.getParameter("address2");
        } catch (Exception e) {
        }
        try {
            city = request.getParameter("city");
        } catch (Exception e) {
        }
        try {
            state = request.getParameter("state");
        } catch (Exception e) {
        }
        try {
            zip = request.getParameter("zip");
        } catch (Exception e) {
        }
        try {
            country = request.getParameter("country");
        } catch (Exception e) {
        }
        try {
            telno = request.getParameter("telno");
        } catch (Exception e) {
        }
        try {
            extno = request.getParameter("extno");
        } catch (Exception e) {
        }
        try {
            faxno = request.getParameter("faxno");
        } catch (Exception e) {
        }
        try {
            url = request.getParameter("url");
        } catch (Exception e) {
        }
        try {
            emailadd = request.getParameter("emailadd");
        } catch (Exception e) {
        }
        try {
            note = request.getParameter("note");
        } catch (Exception e) {
        }

        officeLocation.setFriendlyName(friendlyName);
        officeLocation.setAddress_1(address1);
        officeLocation.setAddress_2(address2);
        officeLocation.setCity(city);
        officeLocation.setCountry(country);
        officeLocation.setEmail_address(emailadd);
        officeLocation.setFax_number(faxno);
        officeLocation.setLocation(location);
        officeLocation.setMain_telephone_numb(telno);
        officeLocation.setNote(note);
        officeLocation.setState_prov(state);
        officeLocation.setUrl(url);
        officeLocation.setWorkPhoneEx(extno);
        officeLocation.setZip_postal_code(zip);


        UserService.getInstance().addLocation(officeLocation);

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
