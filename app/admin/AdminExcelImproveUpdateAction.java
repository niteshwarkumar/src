/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.admin;

import app.security.SecurityService;
import app.standardCode.DateService;
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

/**
 *
 * @author niteshwar
 */
public class AdminExcelImproveUpdateAction extends Action {

    private Log log = LogFactory.getLog("org.apache.struts.webapp.Example");

    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // Extract attributes we will need
        MessageResources messages = getResources(request);

        // save errors
        ActionMessages errors = new ActionMessages();

        //START check for login (security)
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }

        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
        //END check for login (security)
      

        String id = request.getParameter("id");
        String vid = request.getParameter("vid");
        String action = request.getParameter("action");
        String responsible = request.getParameter("responsible");
        String status = request.getParameter("status");
        String approver = request.getParameter("approver");
        String duedate = request.getParameter("duedate");
        String doi = request.getParameter("doi");
        String comment = request.getParameter("comment");
        
        String pStatus = request.getParameter("pStatus");
           
        
         String delete = request.getParameter("delete");
         

        ExcelnetIssueAction eia;
        if(id == null){
            eia = new ExcelnetIssueAction();
            eia.setVid(Integer.parseInt(vid));
        }else{
            eia = AdminService.getInstance().getSinglegetExcelnetIssueAction(Integer.parseInt(id));
        }
        
        
        if (eia == null) {
            eia = new ExcelnetIssueAction();
            eia.setVid(Integer.parseInt(vid));
        }

        if(StandardCode.getInstance().noNull(delete).equalsIgnoreCase("D")){
            AdminService.getInstance().delete(eia);
         return (mapping.findForward("Success")); 
        }
        eia.setAction(action);
        eia.setResponsible(Integer.parseInt(responsible));
        eia.setStatus(status);
        eia.setComment(comment);
        eia.setApprover(Integer.parseInt(approver));
        String filePath="C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/logo/Library/";
//        filePath="/Users/abhisheksingh/Project/log/";
        String saveFileName = null;
        String fileName = null;
        DynaValidatorForm uvg = (DynaValidatorForm) form;
        FormFile Upload = (FormFile) uvg.get("uploadFile");
          if (Upload.getFileName().length() > 0) {
            fileName = Upload.getFileName();
            byte[] fileData = Upload.getFileData(); //byte array of file data
            //random number in image name to prevent  repeats
            Random gen = new Random(new Date().getSeconds());
            saveFileName = String.valueOf(gen.nextInt()) + fileName;
            java.io.File saveFile = new java.io.File(filePath + saveFileName);
            FileOutputStream out = new FileOutputStream(saveFile);
            out.write(fileData);
            out.flush();
            out.close();
            boolean success = (new File(filePath + eia.getFilepath())).delete();
            //Update Old Lib HIstory 


            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
            eia.setFileName(fileName);
            eia.setFilepath(saveFileName);
         

        }
       
        try {
            if (duedate.length() > 0) { //if present
                eia.setStartdate(DateService.getInstance().convertDate(duedate).getTime());
            }
        } catch (Exception e) {
            ////System.out.println("Date Errooooorr " + e.getMessage());
        }
        
        try {
            if (doi.length() > 0) { //if present
                eia.setEnddate(DateService.getInstance().convertDate(doi).getTime());
            }
        } catch (Exception e) {
            ////System.out.println("Date Errooooorr " + e.getMessage());
        }
        AdminService.getInstance().addObject(eia);
        
        if(pStatus.equalsIgnoreCase("2")){
            return (mapping.findForward("Success2"));
        }
         

        return (mapping.findForward("Success1"));

    }
}
