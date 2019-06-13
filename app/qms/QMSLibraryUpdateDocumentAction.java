/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import app.standardCode.DateService;
import app.standardCode.StandardCode;
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
public class QMSLibraryUpdateDocumentAction extends Action {

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
            HttpServletResponse response) throws Exception {
        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
        User currentUser=null;
         String userViewId = StandardCode.getInstance().getCookie("userViewId", request.getCookies());
        try {
            currentUser= UserService.getInstance().getSingleUser(Integer.parseInt(userViewId));
        } catch (Exception e) {
        }

        String libId = request.getParameter("id");
        String doAction = request.getParameter("doAction");
        QMSLibrary uDoc = QMSService.getInstance().getSingleQMSLibraryDocument(Integer.parseInt(libId));
        if (doAction.equalsIgnoreCase("delete")) {
            QMSServiceDelete.getInstance().deleteDocument(uDoc);
            return (mapping.findForward(uDoc.getMainTab()));
        }

        DynaValidatorForm uvg = (DynaValidatorForm) form;
        FormFile Upload = (FormFile) uvg.get("uploadFile");

        String saveFileName = null;
        String fileName = null;
        if (Upload.getFileName().length() > 0) {
            fileName = Upload.getFileName();
            byte[] fileData = Upload.getFileData(); //byte array of file data
            //random number in image name to prevent  repeats
            Random gen = new Random(new Date().getSeconds());
            saveFileName = String.valueOf(gen.nextInt()) + fileName;
//            java.io.File saveFile = new java.io.File("D:/Library/QMS/" + saveFileName);
            java.io.File saveFile = new java.io.File("C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/logo/Library/QMS/" + saveFileName);
            FileOutputStream out = new FileOutputStream(saveFile);
            out.write(fileData);
            out.flush();
            out.close();
            boolean success = (new File("C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/logo/Library/QMS/" + uDoc.getFileSaveName())).delete();
            //Update Old Lib HIstory 


            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
            uDoc.setFileName(fileName);
            uDoc.setFileSaveName(saveFileName);
            uDoc.setUploadBy(u.getFirstName() + " " + u.getLastName());
            uDoc.setUploadDate(new Date());
            uDoc.setSign1(false);
            uDoc.setSign2(false);
            uDoc.setSign3(false);
            uDoc.setDate1(null);
            uDoc.setDate2(null);
            uDoc.setDate3(null);
            uDoc.setPerson1(null);
            uDoc.setPerson2(null);
            uDoc.setPerson3(null);
              List tlist=QMSService.getInstance().getTrainingNotify(Integer.parseInt(libId));
              for(int i = 0; i < tlist.size();i++)
              {
                TrainingNotify tn=(TrainingNotify) tlist.get(i);
                QMSServiceDelete.getInstance().deletetTraininNotify(tn);
              }

        }
        uDoc.setDocId((String) uvg.get("docId"));
        uDoc.setVersion((String) uvg.get("version"));
        uDoc.setType((String) uvg.get("type"));
        uDoc.setOwner((String) uvg.get("owner"));
        uDoc.setLink((String) uvg.get("htmlLink"));
        uDoc.setDescription((String) uvg.get("description"));
        uDoc.setFormat((String) uvg.get("docFormat"));
        uDoc.setUploadBy(u.getFirstName() + " " + u.getLastName());
        uDoc.setUploadDate(new Date());
        uDoc.setTitle((String) uvg.get("title"));
        uDoc.setCategory((String) uvg.get("category"));
        String releaseDate = (String) uvg.get("release");
        uDoc.setIsoreference((String) uvg.get("isoreference"));
        if(uDoc.getMainTab().equalsIgnoreCase("Review")){
            uDoc.setOwner(currentUser.getFirstName()+" "+currentUser.getLastName());
            uDoc.setOwnerid(u.getUserId());
        }
 uDoc.setAffectedBox((String) uvg.get("affectedBox"));

        String notaffected =request.getParameter("notaffected");
        String affected =request.getParameter("affected");

        if(notaffected!=null){
            uDoc.setAffected(false);
        }else if(affected!=null){
            uDoc.setAffected(true);
        }


        try {
            if (releaseDate.length() > 0) { //if present
                uDoc.setReleaseDate(DateService.getInstance().convertDate(releaseDate).getTime());
            }
        } catch (Exception e) {
            //System.out.println("Date Errooooorr " + e.getMessage());
        }
        Integer id = QMSServiceAddUpdate.getInstance().addLibrary(uDoc);
        if (Upload.getFileName().length() > 0) {

            uDoc = QMSService.getInstance().getSingleQMSLibraryDocument(id);
            //Add new History
            QMSLibraryHistory libHistory = new QMSLibraryHistory();
            libHistory.setDocId(uDoc.getDocId()); 
            libHistory.setTitle(uDoc.getTitle());
            libHistory.setVersion(uDoc.getVersion());
            libHistory.setReleaseDate(uDoc.getReleaseDate());
            libHistory.setQMSLibId(uDoc.getId());
            libHistory.setChanges((String) uvg.get("changes"));

            QMSServiceAddUpdate.getInstance().addHistory(libHistory);

        }



        // Forward control to the specified success URI
        if(uvg.get("isoreference")!= null&&!uDoc.getMainTab().equalsIgnoreCase("Review")){
        return (mapping.findForward("Matrix"));
        }

        return (mapping.findForward(uDoc.getMainTab()));

    }
}
