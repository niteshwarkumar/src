/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.project;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import java.util.*;
import java.text.*;
import java.io.*;
import com.lowagie.text.pdf.*;
import app.security.*;
import app.standardCode.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
/**
 *
 * @author abc
 */
public class ProjectViewFormsGen141Action  extends Action {


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
	      
        //START get id of current project from either request, attribute, or cookie 
        //id of project from request
	String projectId = null;
	projectId = request.getParameter("projectViewId");
        
        //check attribute in request
        if(projectId == null) {
            projectId = (String) request.getAttribute("projectViewId");
        }
        
        //id of project from cookie
        if(projectId == null) {            
            projectId = StandardCode.getInstance().getCookie("projectViewId", request.getCookies());
        }

        //default project to last if not in request or cookie
        if(projectId == null) {
                java.util.List results = ProjectService.getInstance().getProjectList();
                                
                ListIterator iterScroll = null;
                for(iterScroll = results.listIterator(); iterScroll.hasNext(); iterScroll.next()) {}
                   iterScroll.previous();
                   Project p = (Project) iterScroll.next();  
                   projectId = String.valueOf(p.getProjectId());
         }            
        
        Integer id = Integer.valueOf(projectId);
        
        //END get id of current project from either request, attribute, or cookie               
        
        //get project
        Project p = ProjectService.getInstance().getSingleProject(id); 
        (new java.io.File("C:/TA-" + projectId)).mkdir();
             //START process pdf
            try {
//                PdfReader reader = new PdfReader("C:/templates/ISO03_001.pdf"); //the template
//                
//                //save the pdf in memory
                ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
//               // PdfStamper 
//                
//                //the filled-in pdf
//                PdfStamper stamp = new PdfStamper(reader, pdfStream);
//                
//                //stamp.setEncryption(true, "pass", "pass", PdfWriter.AllowCopy | PdfWriter.AllowPrinting);
//                AcroFields form1 = stamp.getAcroFields();

                //get sources and targets
                StringBuffer sources = new StringBuffer("");
                StringBuffer targets = new StringBuffer("");
                if(p.getSourceDocs() != null) {
                    for(Iterator iterSource = p.getSourceDocs().iterator(); iterSource.hasNext();) {
                        SourceDoc sd = (SourceDoc) iterSource.next();
                        sources.append(sd.getLanguage() + " ");
                        if(sd.getTargetDocs() != null) {
                            for(Iterator iterTarget = sd.getTargetDocs().iterator(); iterTarget.hasNext();) {
                                TargetDoc td = (TargetDoc) iterTarget.next();
                                if(!td.getLanguage().equals("All")){
                                  PdfReader reader = new PdfReader("C:/templates/ISO03_001.pdf");
                                    pdfStream = new ByteArrayOutputStream();
                                   PdfStamper stamp = new PdfStamper(reader, new FileOutputStream("C:/TA-" + projectId + "/" + p.getNumber() + p.getCompany().getCompany_code() + "-" + td.getLanguage() + "-Translation-Approval.pdf")); 
                                     //set the field values in the pdf form
                                   AcroFields form1 = stamp.getAcroFields();
                                        form1.setField("languages", td.getLanguage());
                                        form1.setField("projectnr", p.getNumber() + p.getCompany().getCompany_code());
                                        form1.setField("Text3", StandardCode.getInstance().noNull(p.getPm()));
                                        form1.setField("Text2", p.getCompany().getCompany_name());
                                        form1.setField("project", p.getNumber() + p.getCompany().getCompany_code());
                                        form1.setField("Text4", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
                                        form1.setField("company", StandardCode.getInstance().noNull(p.getCompany().getCompany_name()));
                                        form1.setField("description", StandardCode.getInstance().noNull(p.getProductDescription()));
                                        form1.setField("source", sources.toString());
                                        form1.setField("pm", StandardCode.getInstance().noNull(p.getPm()));
                                        form1.setField("date", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));

                                     stamp.close();
                                    
                                    targets.append(td.getLanguage() + " ");
                        
                            
                                }
                            
                            
                            }
                        }
                    }
                }
              PdfReader reader = new PdfReader("C:/templates/ISO03_001.pdf");
                pdfStream = new ByteArrayOutputStream();
               PdfStamper stamp = new PdfStamper(reader, pdfStream);
               AcroFields form1 = stamp.getAcroFields();
                //set the field values in the pdf form
                form1.setField("languages", targets.toString());
                form1.setField("projectnr", p.getNumber() + p.getCompany().getCompany_code());
                form1.setField("Text3", StandardCode.getInstance().noNull(p.getPm()));
                form1.setField("Text2", p.getCompany().getCompany_name());
                form1.setField("project", p.getNumber() + p.getCompany().getCompany_code());
                form1.setField("Text4", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
                form1.setField("company", StandardCode.getInstance().noNull(p.getCompany().getCompany_name()));
                form1.setField("description", StandardCode.getInstance().noNull(p.getProductDescription()));
                form1.setField("source", sources.toString());
                form1.setField("pm", StandardCode.getInstance().noNull(p.getPm()));
                form1.setField("date", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
                
//                //START add images





//                if(u.getPicture() != null && u.getPicture().length() > 0) {
//                    PdfContentByte over;
//                    Image img = Image.getInstance("C:/Program Files (x86)/Apache Software Foundation/Tomcat 7.0/webapps/logo/images/" + u.getPicture());
//                    img.setAbsolutePosition(200, 200);
//                    over = stamp.getOverContent(1);
//                    over.addImage(img, 45, 0,0, 45, 300,100);
//                }
//                //END add images
                
                //stamp.setFormFlattening(true);
                stamp.close();
                
//                //write to client (web browser)
//                response.setHeader("Content-disposition", "attachment; filename=" + p.getNumber() + p.getCompany().getCompany_code() + "-Translation-Approval" + ".pdf");
//                
//                OutputStream os = response.getOutputStream();
//                pdfStream.writeTo(os);
//                os.flush();
            }                
            catch(Exception e) {
                System.err.println("PDF Exception:" + e.getMessage());
		throw new RuntimeException(e);
            }
            //END process pdf      
            
            
               try {
            java.io.File inFolder = new java.io.File("C:/TA-" + projectId);
            java.io.File outFolder = new java.io.File("C:/" + p.getNumber() + p.getCompany().getCompany_code() + "-Translation-Approval.zip");
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outFolder)));
            BufferedInputStream in = null;
            byte[] data = new byte[1000];
            String files[] = inFolder.list();
            for (int i = 0; i < files.length; i++) {
                in = new BufferedInputStream(new FileInputStream(inFolder.getPath() + "/" + files[i]), 1000);
                out.putNextEntry(new ZipEntry(files[i]));
                int count;
                while ((count = in.read(data, 0, 1000)) != -1) {
                    out.write(data, 0, count);
                }
                out.closeEntry();
                in.close();
            }
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String fname = "C:/" + p.getNumber() + p.getCompany().getCompany_code() + "-Translation-Approval.zip";
            String strHeader = "Attachment;Filename=" + fname;
            response.setHeader("Content-Disposition", strHeader);
            java.io.File fileToDownload = new java.io.File(fname);
            FileInputStream fileInputStream = new FileInputStream(fileToDownload);
            int i;
            OutputStream out = response.getOutputStream();
            while ((i = fileInputStream.read()) != -1) {
                out.write(i);
            }
            fileInputStream.close();
            out.close();

        } catch (Exception e) // file IO errors
        {
            e.printStackTrace();
        }

        deleteFile("C:/TA-" + p.getNumber() + p.getCompany().getCompany_code() + "-Translation-Approval.zip");
        //   (new java.io.File("C:/"+p.getNumber() + p.getCompany().getCompany_code())).mkdir();

        for (Iterator iter1 = p.getSourceDocs().iterator(); iter1.hasNext();) {
            SourceDoc sd = (SourceDoc) iter1.next();
            for (Iterator iter2 = sd.getTargetDocs().iterator(); iter2.hasNext();) {
                TargetDoc td = (TargetDoc) iter2.next();
                                  File inpt = new File("C:/TA-" + projectId + "/" + p.getNumber() + p.getCompany().getCompany_code() + "-" + td.getLanguage() + "-Translation-Approval.pdf");
                 inpt.delete();
              
            }
        }
//               File inpt = new File("C:/" + projectId + "/" + p.getNumber() + p.getCompany().getCompany_code() + "-" + td.getLanguage() + "-Translation-Approval.pdf");
//                 inpt.delete();
              
        deleteFile("C:/TA-" + projectId);
            
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }
    
    public static boolean deleteFile(String sFilePath) {
        File oFile = new File(sFilePath);
        if (oFile.isDirectory()) {
            File[] aFiles = oFile.listFiles();
            for (File oFileCur : aFiles) {
                deleteFile(oFileCur.getAbsolutePath());
            }
        }
        return oFile.delete();
    }

}
