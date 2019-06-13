/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.comm;


import org.apache.struts.upload.FormFile;
import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import java.util.*;
import app.user.User;
import app.user.UserService;
import org.apache.struts.validator.DynaValidatorForm;

/**
 *
 * @author Niteshwar
 */
public class LibraryAddDocumentAction extends Action {


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
				 HttpServletResponse response)throws Exception

    {
        User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
        String mainTab=request.getParameter("mainTab");
        String subTab=request.getParameter("subTab");
        String clientViewId=request.getParameter("clientViewId");
        
       
     
        
        
//        String desc=request.getParameter("desc");

//           java.io.File dBDir = new java.io.File("C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/logo/Library/"+mainTab);
//        boolean exists = dBDir.exists();
//        if (!exists) {
//            String strDirectoy = "C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/logo/Library/"+mainTab;
//            // Create one directory
//            boolean success = (new java.io.File(strDirectoy)).mkdir();
//        } else {
//            // It returns true if File or directory exists
//        }


         DynaValidatorForm uvg = (DynaValidatorForm) form;
        FormFile Upload = (FormFile) uvg.get("uploadFile");
        
         LibraryUpload uDoc = new LibraryUpload();
        String saveFileName = null;
        String fileName = null;
        if (Upload.getFileName().length() > 0) {
            fileName = Upload.getFileName();
            byte[] fileData = Upload.getFileData(); //byte array of file data
            //random number in image name to prevent repeats
            Random gen = new Random(new Date().getSeconds());
            saveFileName = String.valueOf(gen.nextInt()) + fileName;
//            java.io.File saveFile = new java.io.File("D:/Library/"+mainTab+"/" + saveFileName);
            java.io.File saveFile = new java.io.File("C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/logo/Library/"+mainTab+"/" + saveFileName);
            FileOutputStream out = new FileOutputStream(saveFile);
            out.write(fileData);
            out.flush();
            out.close();
        }
        if(fileName!=null){
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
        uDoc.setFileName(fileName);
        uDoc.setFileSaveName(saveFileName);
        }
        uDoc.setHtmlLink((String) uvg.get("htmlLink"));
        uDoc.setDescription((String) uvg.get("description"));
        uDoc.setMaintab(mainTab);
        uDoc.setFormat((String) uvg.get("docFormat"));
        uDoc.setHeading(subTab);
        uDoc.setUploadBy(u.getFirstName() +" "+u.getLastName());
        uDoc.setUploadDate(new Date());
        uDoc.setTitle((String) uvg.get("title"));
        
        try {
             if(clientViewId!=null)
        {
            uDoc.setClientId(Integer.parseInt(clientViewId));
        } 
        } catch (Exception e) {
        }
        
        
        CommService.getInstance().addLibrary(uDoc);

        

        // Forward control to the specified success URI

         return (mapping.findForward(mainTab));

    }
}

