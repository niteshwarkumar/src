/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.extjs.actions;

import app.extjs.vo.Upload_Doc;
import app.qms.QMSReportUpload;
import app.qms.QMSService;
import app.qms.QMSServiceAddUpdate;
import app.qms.QMSServiceDelete;
import app.security.SecurityService;
import app.user.User;
import app.user.UserService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

/**
 *
 * @author niteshwar
 */
public class FileUploadAction extends Action {

    private Log log =
            LogFactory.getLog("org.apache.struts.webapp.Example");

    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        String isDelete = request.getParameter("isDelete");

        if (isDelete.equalsIgnoreCase("delete")) {
            try {
                String id = request.getParameter("id");
                QMSServiceDelete.getInstance().deletetQMSReport(QMSService.getInstance().getSingleQMSReport(Integer.parseInt(id)));               
            } catch (Exception e) {                
            }
            return (mapping.findForward("Success"));

        }

        File dBDir = new File("C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/logo/Team");
        boolean exists = dBDir.exists();
        if (!exists) {
            // It returns false if File or directory does not exist

            String strDirectoy = "C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/logo/Team";


            // Create one directory
            boolean success = (new File(strDirectoy)).mkdir();
        } else {
            // It returns true if File or directory exists
        }
        String auditId = "0";
        try {
            HttpSession session = request.getSession(false);
            auditId = session.getAttribute("auditId").toString();
        } catch (Exception e) {
        }
        DynaValidatorForm uvg = (DynaValidatorForm) form;
        FormFile Upload = (FormFile) uvg.get("Upload");
        QMSReportUpload newDoc = new QMSReportUpload();
        //System.out.println("Upload File Name" + Upload);
        String saveFileName = null;
        if (Upload.getFileName().length() > 0) {
            String fileName = Upload.getFileName();
            byte[] fileData = Upload.getFileData(); //byte array of file data
            //random number in image name to prevent repeats
            Random gen = new Random(new Date().getSeconds());
            saveFileName = String.valueOf(gen.nextInt()) + fileName;
            File saveFile = new File("C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/logo/QMSUpload/" + saveFileName);
            FileOutputStream out = new FileOutputStream(saveFile);
            out.write(fileData);
            out.flush();
            out.close();
        }
        try {
            newDoc.setAuditno(Integer.valueOf(auditId));
        } catch (Exception e) {
        }
        // newDoc.setPath(ERROR_KEY);
        newDoc.setPathname(Upload.getFileName());
        if (saveFileName != null) { //was a new picture uploaded
            newDoc.setPath(saveFileName);

        }

        QMSServiceAddUpdate.getInstance().addUpload_Doc(newDoc);

        return (mapping.findForward("Success"));
    }
}
