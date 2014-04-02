/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.menu;

import app.security.SecurityService;
import app.standardCode.DateService;
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
 * @author abc
 */
public class ClientHomePageAddAnnouncement1Action  extends Action {


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
        if(!SecurityService.getInstance().checkForLogin(request.getSession(false))) { 
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)
	
        //add resource info for editing/adding
        DynaValidatorForm hpaa = (DynaValidatorForm) form;
             // homePageAnnouncementAdd
        //get the form values
        Announcement a = (Announcement) hpaa.get("announcementAdd");
        String startDate = (String) hpaa.get("announcementAddStartDate");
        String endDate = (String) hpaa.get("announcementAddEndDate");
        String clientId = (String) hpaa.get("announcementAddClient");
        FormFile picture = (FormFile) hpaa.get("announcementPicture");
        
        if(clientId != null && clientId.length() > 0){
            a.setID_Client(Integer.parseInt(clientId));
        }
        
        //set date values if present
        if(startDate != null && startDate.length() > 0) {
            a.setStartDate(DateService.getInstance().convertDate(startDate).getTime());
        }
        if(endDate != null && endDate.length() > 0) {
            a.setEndDate(DateService.getInstance().convertDate(endDate).getTime());
        }
        
        //process picture upload
        String saveFileName = null;
        if(picture.getFileName().length() > 0) {
            String fileName = picture.getFileName();
            fileName=fileName.replaceAll(" ","_");
            byte[] fileData  = picture.getFileData(); //byte array of file data
            //random number in image name to prevent repeats
            Random gen = new Random(new Date().getSeconds());
            saveFileName = String.valueOf(gen.nextInt()) + fileName;
            File saveFile = new File("C:/Program Files (x86)/Apache Software Foundation/Tomcat 7.0/webapps/logo/images/" + saveFileName);        
            FileOutputStream out = new FileOutputStream(saveFile);
            out.write(fileData);
            out.flush();
            out.close();
        }
        if(saveFileName != null) { //was a new picture uploaded
            a.setPicture(saveFileName);
        }
        
        //add new announcement to db
        MenuService.getInstance().addAnnouncement(a);
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
