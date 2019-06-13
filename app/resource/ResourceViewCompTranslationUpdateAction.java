/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.resource;

import app.extjs.vo.Upload_Doc;
import app.quote.QuoteService;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
public class ResourceViewCompTranslationUpdateAction  extends Action {
    
    private static final long serialVersionUID = 1L;


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
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (!isMultipart) {
            System.err.println("not multipart");
        }

        //START get id of current resource from either request, attribute, or cookie 
        //id of resource from request
	String resourceId = null;
	resourceId = request.getParameter("resourceViewId");
        String gq_institution= request.getParameter("gq_institution");
        
        String gq_field= request.getParameter("gq_field");
        String gq_graduation_year= request.getParameter("gq_graduation_year");
//        String gq_evidence_filename= request.getParameter("gq_evidence_filename");
//        String gq_evidence_filepath= request.getParameter("gq_evidence_filepath");

        String gqo_institution= request.getParameter("gqo_institution");
        String gqo_field= request.getParameter("gqo_field");
        String gqo_graduation_year= request.getParameter("gqo_graduation_year");
//        String gqo_evidence_filename= request.getParameter("gqo_evidence_filename");
//        String gqo_evidence_filepath= request.getParameter("gqo_evidence_filepath");

        String coc_institution= request.getParameter("coc_institution");
        String coc_field= request.getParameter("coc_field");
        String coc_graduation_year= request.getParameter("coc_graduation_year");
//        String coc_evidence_filename= request.getParameter("coc_evidence_filename");
//        String coc_evidence_filepath= request.getParameter("coc_evidence_filepath");

        String exp_translatorSince= request.getParameter("exp_translatorSince");

        String cc_standard= request.getParameter("cc_standard");
        String cc_edition= request.getParameter("cc_edition");
        String cc_certifiedIn= request.getParameter("cc_certifiedIn");
        String cc_expiration= request.getParameter("cc_expiration");
        
         String gq_note= request.getParameter("gq_note");
         String gqo_note= request.getParameter("gqo_note");
          String exp_note= request.getParameter("exp_note");
//        String cc_evidence_filename= request.getParameter("cc_evidence_filename");
//        String cc_evidence_filepath= request.getParameter("cc_evidence_filepath");
        
        DynaValidatorForm uvg = (DynaValidatorForm) form;
        FormFile cc_evidence_file = (FormFile) uvg.get("cc_evidence_file");
        FormFile coc_evidence_file = (FormFile) uvg.get("coc_evidence_file");
        FormFile gq_evidence_file = (FormFile) uvg.get("gq_evidence_file");
        FormFile gqo_evidence_file = (FormFile) uvg.get("gqo_evidence_file");

        
       
        
        
        //check attribute in request
        if(resourceId == null) {
            resourceId = (String) request.getAttribute("resourceViewId");
        }
        
        //id of resource from cookie
        if(resourceId == null) {            
            resourceId = StandardCode.getInstance().getCookie("resourceViewId", request.getCookies());
        }

        //default resource to first if not in request or cookie
        if(resourceId == null) {
                List results = ResourceService.getInstance().getResourceList();
                Resource first = (Resource) results.get(0);
                resourceId = String.valueOf(first.getResourceId());
            }            
        
        Integer id = Integer.valueOf(resourceId);
        
        CompetanceTranslation cot = ResourceService.getInstance().getCompetanceTranslation(id);
        if(cot==null) cot = new CompetanceTranslation();
        cot.setCc_edition(cc_edition);
        cot.setCc_certifiedIn(cc_certifiedIn);
//        cot.setCc_evidence_filename(cc_evidence_filename);
//        cot.setCc_evidence_filepath(cc_evidence_filepath);
        cot.setCc_expiration(cc_expiration);
        cot.setCc_standard(cc_standard);
//        cot.setCoc_evidence_filename(coc_evidence_filename);
//        cot.setCoc_evidence_filepath(coc_evidence_filepath);
        cot.setCoc_field(coc_field);
        cot.setCoc_graduation_year(coc_graduation_year);
        cot.setCoc_institution(coc_institution);
        cot.setExp_translatorSince(exp_translatorSince);
//        cot.setGq_evidence_filename(gq_evidence_filename);
//        cot.setGq_evidence_filepath(gq_evidence_filepath);
        cot.setGq_field(gq_field);
        cot.setGq_graduation_year(gq_graduation_year);
        cot.setGq_institution(gq_institution);
//        cot.setGqo_evidence_filename(gqo_evidence_filename);
//        cot.setGqo_evidence_filepath(gqo_evidence_filepath);
        cot.setGqo_field(gqo_field);
        cot.setGqo_graduation_year(gqo_graduation_year);
        cot.setGqo_institution(gqo_institution);
        cot.setGq_note(gq_note);
        cot.setGqo_note(gqo_note);
        cot.setExp_note(exp_note);
        

        String saveFileName = null;
        if(cc_evidence_file!=null){
        if (cc_evidence_file.getFileName().length() > 0) {
            String fileName = cc_evidence_file.getFileName();
            byte[] fileData = cc_evidence_file.getFileData(); //byte array of file data
            //random number in image name to prevent repeats
            Random gen = new Random(new Date().getSeconds());
            saveFileName = String.valueOf(gen.nextInt()) + fileName;
            File saveFile = new File(StandardCode.getInstance().getPropertyValue("mac.fileUpload.path") + saveFileName);
            FileOutputStream out = new FileOutputStream(saveFile);
            out.write(fileData);
            out.flush();
            out.close();
            Upload_Doc ud = new Upload_Doc();
            ud.setOwner("");
            ud.setResourceId(id);
            ud.setPathname(cc_evidence_file.getFileName());
            ud.setPath(saveFileName);
            ud.setUploadDate(new Date());
            ud.setType("COMPETANCECC");
        
            QuoteService.getInstance().addUpload_Doc(ud);
                                            
                                            
                                    
//            cot.setCc_evidence_filename(cc_evidence_file.getFileName());
//            cot.setCc_evidence_filepath(saveFileName);
        }}saveFileName = null;
        if(coc_evidence_file!=null){
        if (coc_evidence_file.getFileName().length() > 0) {
            String fileName = coc_evidence_file.getFileName();
            byte[] fileData = coc_evidence_file.getFileData(); //byte array of file data
            //random number in image name to prevent repeats
            Random gen = new Random(new Date().getSeconds());
            saveFileName = String.valueOf(gen.nextInt()) + fileName;
            File saveFile = new File(StandardCode.getInstance().getPropertyValue("mac.fileUpload.path") + saveFileName);
            FileOutputStream out = new FileOutputStream(saveFile);
            out.write(fileData);
            out.flush();
            out.close();
            Upload_Doc ud = new Upload_Doc();
            ud.setOwner("");
            ud.setResourceId(id);
            ud.setPathname(coc_evidence_file.getFileName());
            ud.setPath(saveFileName);
            ud.setUploadDate(new Date());
            ud.setType("COMPETANCECOC");
        
            QuoteService.getInstance().addUpload_Doc(ud);
//            cot.setCoc_evidence_filename(coc_evidence_file.getFileName());
//            cot.setCoc_evidence_filepath(saveFileName);
        }}saveFileName = null;
        if(gq_evidence_file!=null){
        if (gq_evidence_file.getFileName().length() > 0) {
            String fileName = gq_evidence_file.getFileName();
            byte[] fileData = gq_evidence_file.getFileData(); //byte array of file data
            //random number in image name to prevent repeats
            Random gen = new Random(new Date().getSeconds());
            saveFileName = String.valueOf(gen.nextInt()) + fileName;
            File saveFile = new File(StandardCode.getInstance().getPropertyValue("mac.fileUpload.path") + saveFileName);
            FileOutputStream out = new FileOutputStream(saveFile);
            out.write(fileData);
            out.flush();
            out.close();
            Upload_Doc ud = new Upload_Doc();
            ud.setOwner("");
            ud.setResourceId(id);
            ud.setPathname(gq_evidence_file.getFileName());
            ud.setPath(saveFileName);
            ud.setUploadDate(new Date());
            ud.setType("COMPETANCEGQ");
        
            QuoteService.getInstance().addUpload_Doc(ud);
//            cot.setGq_evidence_filename(gq_evidence_file.getFileName());
//            cot.setGq_evidence_filepath(saveFileName);
        }}saveFileName = null;
        if(gqo_evidence_file!=null){
        if (gqo_evidence_file.getFileName().length() > 0) {
            String fileName = gqo_evidence_file.getFileName();
            byte[] fileData = gqo_evidence_file.getFileData(); //byte array of file data
            //random number in image name to prevent repeats
            Random gen = new Random(new Date().getSeconds());
            saveFileName = String.valueOf(gen.nextInt()) + fileName;
            File saveFile = new File(StandardCode.getInstance().getPropertyValue("mac.fileUpload.path") + saveFileName);
            FileOutputStream out = new FileOutputStream(saveFile);
            out.write(fileData);
            out.flush();
            out.close();
            Upload_Doc ud = new Upload_Doc();
            ud.setOwner("");
            ud.setResourceId(id);
            ud.setPathname(gqo_evidence_file.getFileName());
            ud.setPath(saveFileName);
            ud.setUploadDate(new Date());
            ud.setType("COMPETANCEGQO");
        
            QuoteService.getInstance().addUpload_Doc(ud);
//            cot.setGqo_evidence_filename(gqo_evidence_file.getFileName());
//            cot.setGqo_evidence_filepath(saveFileName);
        }}
        
        
  
        ResourceService.getInstance().addUpdateCompetanceTranslattion(cot);
                                        
        //place this resource into the resource form for display 
         //get resource to edit
        Resource r = ResourceService.getInstance().getSingleResource(id); 
        request.setAttribute("resource", r);        
        request.setAttribute("cot", cot);
        //add this resource id to cookies; this will remember the last resource
        response.addCookie(StandardCode.getInstance().setCookie("resourceViewId", resourceId));
        
        return (mapping.findForward("Success"));
    }
    // Forward control to the specified success URI

}
