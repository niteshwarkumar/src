/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.quote;

import app.extjs.vo.Upload_Doc;
import org.apache.struts.util.MessageResources;
import app.security.*;
import app.standardCode.StandardCode;
import app.user.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.*;
import java.io.*;
import java.util.*;
import java.util.Date;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Niteshwar
 */
public class UploadFile  extends Action {

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
    @Override
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        // Extract attributes we will need
        MessageResources messages = getResources(request);

        // save errors
        ActionMessages errors = new ActionMessages();
        String quoteId = null;
	quoteId = request.getParameter("quoteViewId");

        //check attribute in request
        if(quoteId == null) {
            quoteId = (String) request.getAttribute("quoteViewId");
        }

        //id of quote from cookie
        if(quoteId == null) {
            quoteId = StandardCode.getInstance().getCookie("quoteViewId", request.getCookies());
        }
        String quoteViewId=quoteId;
        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));

//        try {
//            HttpSession session = request.getSession(false);
//            quoteViewId = session.getAttribute("quoteViewId").toString();
//        } catch (Exception e) {
//        }
        Quote1 q = QuoteService.getInstance().getSingleQuote(Integer.parseInt(quoteViewId));
        Client_Quote cq = QuoteService.getInstance().get_SingleClientQuote(Integer.parseInt(quoteViewId));

        //START check for login (security)
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)

        java.io.File dBDir = new java.io.File("C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/logo/ClientUpload");
        boolean exists = dBDir.exists();
        if (!exists) {
            String strDirectoy = "C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/logo/ClientUpload";
            // Create one directory
            boolean success = (new java.io.File(strDirectoy)).mkdir();
        } else {
            // It returns true if File or directory exists
        }
        DynaValidatorForm uvg = (DynaValidatorForm) form;
        FormFile Upload = (FormFile) uvg.get("Upload");
        Upload_Doc uDoc = new Upload_Doc();
        String saveFileName = null;
        String fileName = null;
        if (Upload.getFileName().length() > 0) {
            fileName = Upload.getFileName();
            byte[] fileData = Upload.getFileData(); //byte array of file data
            //random number in image name to prevent repeats
            Random gen = new Random(new Date().getSeconds());
            saveFileName = String.valueOf(gen.nextInt()) + fileName;
            java.io.File saveFile = new java.io.File("C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/logo/ClientUpload/" + saveFileName);
            FileOutputStream out = new FileOutputStream(saveFile);
            out.write(fileData);
            out.flush();
            out.close();
        }

        uDoc.setClientID(u.getID_Client());
        uDoc.setPath(fileName);
        uDoc.setPathname(saveFileName);
        uDoc.setQuoteID(Integer.parseInt(quoteViewId));
        uDoc.setUploadedBy(u.getFirstName()+" "+u.getLastName());
        Date date=new Date();
        uDoc.setUploadDate(date);
        QuoteService.getInstance().addUpload_Doc(uDoc);

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
