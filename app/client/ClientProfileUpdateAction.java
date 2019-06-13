/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.client;

import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Nishika
 */
public class ClientProfileUpdateAction extends Action {

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
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
        String profileId = request.getParameter("profileId");
        String clientId = request.getParameter("clientId");
        String doAction = request.getParameter("doAction");
        String editType = request.getParameter("editType");

        String note = "";

        if (!profileId.equalsIgnoreCase("null")) {
            note = StandardCode.getInstance().noNull(request.getParameter("note"));
        }

//     String ae_text = request.getParameter("ae_text");
//     String process_text = request.getParameter("process_text");
//     String icr_text = request.getParameter("icr_text");
//     String deliverable_text = request.getParameter("deliverable_text");
//     String technical_project_text = request.getParameter("technical_project_text");
//     String pricing_text = request.getParameter("pricing_text");
//     String current_invoicing_process_text = request.getParameter("current_invoicing_process_text");
//     String typical_project_text = request.getParameter("typical_project_text");
//    Upload_Doc uploadDoc = null;
//    if (null == profileId) {
//      uploadDoc = new Upload_Doc();
//    } else if (profileId.equalsIgnoreCase("null")) {
//      uploadDoc = new Upload_Doc();
//    } else {
//      uploadDoc = QuoteService.getInstance().getSingleUploadFile(Integer.parseInt(profileId));
//    }
//    try {
//      if (doAction.equalsIgnoreCase("delete")) {
//        uploadDoc.setType("delete");
//        QuoteService.getInstance().addUpload_Doc(uploadDoc);
//        return (mapping.findForward("Success"));
//      }
//    } catch (Exception e) {
//    }
        ClientProfile clientProfile = null;
        if (null == profileId) {
            clientProfile = new ClientProfile();
        } else if (profileId.equalsIgnoreCase("null")) {
            clientProfile = new ClientProfile();
            clientProfile.setClientId(Integer.parseInt(clientId));
        } else {
            clientProfile = ClientService.getInstance().getSingleClientProfile(Integer.parseInt(profileId));
        }
        try {
            if (doAction.equalsIgnoreCase("delete")) {
//        clientProfile.setType("delete");
                ClientService.getInstance().deleteClientProfile(clientProfile);
                return (mapping.findForward("Success"));
            }
        } catch (Exception e) {
        }

        if (!profileId.equalsIgnoreCase("null")) {
            clientProfile.setAe_text(request.getParameter("aeNotes"));

            if (!profileId.equalsIgnoreCase("null")) {
                clientProfile.setClientId(clientProfile.getClientId());
            }
            clientProfile.setCurrent_invoicing_process_text(request.getParameter("currentInvoicingProcess"));
            clientProfile.setDeliverable_text(request.getParameter("deliverable"));
            clientProfile.setIcr_text(request.getParameter("icr"));
            clientProfile.setPricing_text(request.getParameter("pricing"));
            clientProfile.setProcess_text(request.getParameter("process"));
            clientProfile.setTechnical_project_text(request.getParameter("techProject"));
            clientProfile.setTypical_project_text(request.getParameter("typicalProject"));
        }
        clientProfile.setUpdateDate(new Date());
        clientProfile.setUpdatedBy(u.getFirstName() + " " + u.getLastName());

        ClientService.getInstance().addClientProfile(clientProfile);

//    DynaValidatorForm uvg = (DynaValidatorForm) form;
//    FormFile Upload = (FormFile) uvg.get("uploadFile");
//
//
//    String saveFileName = null;
//    String fileName = null;
//    if (Upload.getFileName().length() > 0) {
//      fileName = Upload.getFileName();
//      byte[] fileData = Upload.getFileData(); //byte array of file data
//      //random number in image name to prevent  repeats
//      Random gen = new Random(new Date().getSeconds());
//      saveFileName = String.valueOf(gen.nextInt()) + fileName;
////            java.io.File saveFile = new java.io.File("C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/logo/Library/"+mainTab+"/" + saveFileName);
////            java.io.File saveFile = new java.io.File("D:/Upload/" + saveFileName);
//      java.io.File saveFile = new java.io.File("C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/logo/Profile/" + saveFileName);
//      FileOutputStream out = new FileOutputStream(saveFile);
//      out.write(fileData);
//      out.flush();
//      out.close();
////            boolean success = (new File("C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/logo/Library/"+ uploadDoc.getPath())).delete();
//
//      java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
//      uploadDoc.setPath(fileName);
//      uploadDoc.setPathname(saveFileName);
//      uploadDoc.setUploadedBy(u.getFirstName() + " " + u.getLastName());
//      uploadDoc.setUploadDate(new Date());
//      if (clientId != null) {
//        uploadDoc.setClientID(Integer.parseInt(clientId));
//        uploadDoc.setType("Profile");
//      }
//
//
//    }
//    QuoteService.getInstance().addUpload_Doc(uploadDoc);
        // Forward control to the specified success URI
        return (mapping.findForward("Success"));

    }
}
