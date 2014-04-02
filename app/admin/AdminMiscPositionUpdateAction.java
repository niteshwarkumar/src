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
import app.standardCode.*;
import app.security.*;
import app.user.Position1;
import app.user.UserService;

/**
 *
 * @author Niteshwar
 */
public class AdminMiscPositionUpdateAction extends Action {

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
        String position="";
String department="";
String  reportto="";
String  positiondesc="";
String  duties="";
String  supervisoryduties="";
String  jobqualification="";
String  experience="";
String  skills="";
String  note="";

        String posId = "";
        Position1 profilePosition = null;

        try {
            posId = request.getParameter("posIdString");
        } catch (Exception e) {
        }

        if (posId == null) {
            profilePosition = new Position1();
            position = request.getParameter("positionText");
        } else {
            profilePosition = UserService.getInstance().getSinglePositionById(Integer.parseInt(posId));
            position=profilePosition.getPosition();
        }


        try {
            
        } catch (Exception e) {
        }
        try {
            department = request.getParameter("department1");
        } catch (Exception e) {
        }
        try {
            reportto = request.getParameter("reportto");
        } catch (Exception e) {
        }
        try {
            positiondesc = request.getParameter("positiondesc");
        } catch (Exception e) {
        }
        try {
            duties = request.getParameter("duties");
        } catch (Exception e) {
        }
        try {
            supervisoryduties = request.getParameter("supervisoryduties");
        } catch (Exception e) {
        }
        try {
            jobqualification = request.getParameter("jobqualification");
        } catch (Exception e) {
        }
        try {
            experience = request.getParameter("experience");
        } catch (Exception e) {
        }
        try {
            skills = request.getParameter("skills");
        } catch (Exception e) {
        }
       
        try {
            note = request.getParameter("note");
        } catch (Exception e) {
        }

        profilePosition.setDepartment(department);
        profilePosition.setDuties(duties);
        profilePosition.setExperience(experience);
        profilePosition.setJobqualification(jobqualification);
        profilePosition.setNote(note);
        profilePosition.setPosition(position);
        profilePosition.setPositiondesc(positiondesc);
        profilePosition.setReportto(reportto);
        profilePosition.setSkills(skills);
        profilePosition.setSupervisoryduties(supervisoryduties);


        UserService.getInstance().addPosition(profilePosition);

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
