/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.extjs.actions;

import app.extjs.vo.Upload_Doc;
import app.quote.QuoteService;
import app.resource.ResourceService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.*;
import java.io.*;
import java.util.*;
import app.security.*;
import app.standardCode.StandardCode;
import app.user.*;
import java.util.Date;
import java.util.zip.*;

/**
 *
 * @author Neil
 */
public class UploadFilesAction extends Action {

//ProjectAddPreFilesAction.java adds the new
//file (pre trados) to the db and builds the one-to-many
//relationship between quote and file (quote file)
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

        //START check for login (security)
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)
        String folderPath  = StandardCode.getInstance().getPropertyValue("mac.fileUpload.path");
        String docId = request.getParameter("docId");
        Upload_Doc newDoc = new Upload_Doc();
        if(null!=docId){
        if(Integer.parseInt(docId)>0){
            newDoc = UserService.getInstance().getDocList(Integer.parseInt(docId));
        }
        }
        (new File(folderPath)).mkdir();
        DynaValidatorForm uvg = (DynaValidatorForm) form;
        FormFile Upload = (FormFile) uvg.get("uploadFile");
        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));        
        
        //System.out.println("Upload File Name" + Upload);
        String saveFileName = null;
        if (Upload.getFileName().length() > 0) {
            String fileName = Upload.getFileName();
            byte[] fileData = Upload.getFileData(); //byte array of file data
            //random number in image name to prevent repeats
            Random gen = new Random(new Date().getTime());
            saveFileName = String.valueOf(gen.nextInt()) + fileName;
            File saveFile = new File(folderPath + saveFileName);
            FileOutputStream out = new FileOutputStream(saveFile);
            out.write(fileData);
            out.flush();
            out.close();
             newDoc.setPathname(Upload.getFileName());
             if (saveFileName != null) { //was a new picture uploaded
            newDoc.setPath(saveFileName);
            
        }
            
            //System.out.println("Filename=====================>>>>>>>" + saveFileName);
        }
        
       
        newDoc.setClientID(0);
      
        
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        String owner = request.getParameter("owner");
        String docFormat = request.getParameter("docFormat");
        String resourceViewId = request.getParameter("resourceViewId");
        String pId = request.getParameter("pId");
        String oId = request.getParameter("oId");
        newDoc.setType(category);
        try {
            newDoc.setResourceId(Integer.parseInt(resourceViewId));
        } catch (Exception e) {
        }
        try {
            newDoc.setProjectID(Integer.parseInt(pId));
        } catch (Exception e) {
        }
        try {
            newDoc.setOthId(Integer.parseInt(oId));
        } catch (Exception e) {
        }
        newDoc.setUploadDate(new Date());
        newDoc.setUploadedBy(u.getFirstName() + " " + u.getLastName());
        newDoc.setTitle(title);
        newDoc.setOwner(owner);
        newDoc.setDescription(description);
        newDoc.setDocFormat(docFormat);
        
        QuoteService.getInstance().addUpload_Doc(newDoc);
        request.setAttribute("SuccessMessage", "File uploaded successfully.");
        // Forward control to the specified success URI
        String doAction = request.getParameter("doAction");
        request.setAttribute("projectId", pId);
        
        return (mapping.findForward(doAction));
    }
}
