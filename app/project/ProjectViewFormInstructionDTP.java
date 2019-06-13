/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.project;

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
import java.io.*;
import app.user.*;
import app.security.*;
import app.standardCode.*;

/**
 *
 * @author Niteshwar
 */
public class ProjectViewFormInstructionDTP  extends Action {

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
            java.util.List results = ProjectService.getInstance().getProjectList();

            ListIterator iterScroll = null;
            for (iterScroll = results.listIterator(); iterScroll.hasNext(); iterScroll.next()) {
            }
            iterScroll.previous();
            Project p = (Project) iterScroll.next();
            projectId = String.valueOf(p.getProjectId());
        }

        Integer id = Integer.valueOf(projectId);

        //END get id of current project from either request, attribute, or cookie

        //get project
        Project p = ProjectService.getInstance().getSingleProject(id);

        //get user (project manager)
        User u = UserService.getInstance().getSingleUserRealName(StandardCode.getInstance().getFirstName(p.getPm()), StandardCode.getInstance().getLastName(p.getPm()));

        byte[] template = getBytesFromFile(new java.io.File("C:/templates2/Instructions - DTP-QA.rtf"));

        String content = new String(template);
        //START content
        try {
            content = content.replaceAll("INSERT_PM_INSERT", StandardCode.getInstance().noNull(u.getFirstName()) + " " + StandardCode.getInstance().noNull(u.getLastName()));
        } catch (Exception e) {
            content = content.replaceAll("INSERT_PM_INSERT", "");
        }
        try {
            content = content.replaceAll("INSERT_PM_EMAIL_INSERT", StandardCode.getInstance().noNull(u.getWorkEmail1()));
        } catch (Exception e) {
            content = content.replaceAll("INSERT_PM_EMAIL_INSERT", "");
        }
        try {
            content = content.replaceAll("INSERT_PM_PHONE_INSERT", StandardCode.getInstance().noNull(u.getWorkPhone()) + " ext " + StandardCode.getInstance().noNull(u.getWorkPhoneEx()));
        } catch (Exception e) {
            content = content.replaceAll("INSERT_PM_PHONE_INSERT", "");
        }
        try {
            content = content.replaceAll("INSERT_PM_SKYPE_INSERT", StandardCode.getInstance().noNull(u.getSkypeId()));
        } catch (Exception e) {
            content = content.replaceAll("INSERT_PM_SKYPE_INSERT", "");
        }
        content = content.replaceAll("INSERT_PROJECT_NUMBER_INSERT", p.getNumber() + p.getCompany().getCompany_code());

        String filename = p.getNumber() + p.getCompany().getCompany_code() + "_Instruction_Linguistic.doc";

        content = content.replaceAll("T5 - Quote template - short_non_medical.rtf", StandardCode.getInstance().noNull(filename));
        //System.out.println("hereeeeee6");
        //END content
        //write to client (web browser)
        response.setHeader("Content-Type", "Application/msword");
        response.setHeader("Content-disposition", "attachment; filename=" + filename);
        OutputStream os = response.getOutputStream();
        os.write(content.getBytes());
        os.flush();

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }

    // Returns the contents of the file in a byte array.
    private static byte[] getBytesFromFile(java.io.File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }
}
