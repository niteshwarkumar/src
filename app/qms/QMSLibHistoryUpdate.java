/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import app.extjs.vo.Upload_Doc;
import app.resource.ResourceService;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.DynaValidatorForm;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author niteshwar
 */
public class QMSLibHistoryUpdate  extends Action {

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

        String mainTab=request.getParameter("mainTab");
        String id=request.getParameter("id");
        String hid=request.getParameter("hid");
        
        QMSLibraryHistory his = QMSService.getInstance().getSingleQMSLibraryHistory(Integer.parseInt(hid));
        //END check for login (security)
        String folderPath  = StandardCode.getInstance().getPropertyValue("mac.fileUpload.path")+"Library/History/";
      
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
             his.setFileSaveName(Upload.getFileName());
             if (saveFileName != null) { //was a new picture uploaded
            his.setFileName(saveFileName);
           QMSServiceAddUpdate.getInstance().addHistory(his);

        }
            
            //System.out.println("Filename=====================>>>>>>>" + saveFileName);
        }

        request.setAttribute("mainTab", mainTab);
        request.setAttribute("id", id);


       return (mapping.findForward("Success"));
    }
}
