/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

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
public class QMSUploadFileAction extends Action {

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
                QMSService.getInstance().deletetQMSReport(QMSService.getInstance().getSingleQMSReport(Integer.parseInt(id)));               
            } catch (Exception e) {                
            }
            return (mapping.findForward("Success"));

        }

        File dBDir = new File("C:/Program Files (x86)/Apache Software Foundation/Tomcat 7.0/webapps/logo/QMSUpload");
        boolean exists = dBDir.exists();
        if (!exists) {
            // It returns false if File or directory does not exist

            String strDirectoy = "C:/Program Files (x86)/Apache Software Foundation/Tomcat 7.0/webapps/logo/QMSUpload";


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
        System.out.println("Upload File Name" + Upload);
        String saveFileName = null;
        if (Upload.getFileName().length() > 0) {
            String fileName = Upload.getFileName();
            byte[] fileData = Upload.getFileData(); //byte array of file data
            //random number in image name to prevent repeats
            Random gen = new Random(new Date().getSeconds());
            saveFileName = String.valueOf(gen.nextInt()) + fileName;
            File saveFile = new File("C:/Program Files (x86)/Apache Software Foundation/Tomcat 7.0/webapps/logo/QMSUpload/" + saveFileName);
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

        QMSService.getInstance().addUpload_Doc(newDoc);

        return (mapping.findForward("Success"));
    }
}
