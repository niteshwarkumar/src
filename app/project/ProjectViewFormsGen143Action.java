/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.project;

import app.security.SecurityService;
import app.standardCode.StandardCode;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.docx4j.Docx4J;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

/**
 *
 * @author niteshwar
 */
public class ProjectViewFormsGen143Action extends Action {

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

        //START get id of current project from either request, attribute, or cookie 
        //id of project from request
        String projectId = null;
        projectId = request.getParameter("projectViewId");

        //check attribute in request
        if (projectId == null) {
            projectId = (String) request.getAttribute("projectViewId");
        }

        //id of project from cookie
        if (projectId == null) {
            projectId = StandardCode.getInstance().getCookie("projectViewId", request.getCookies());
        }

        //default project to last if not in request or cookie
        if (projectId == null) {
            java.util.List results = ProjectService.getInstance().getProjectList();

            ListIterator iterScroll = null;
            for (iterScroll = results.listIterator(); iterScroll.hasNext(); iterScroll.next()) {
            }
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

            //get sources and targets
            if (p.getSourceDocs() != null) {
                for (Iterator iterSource = p.getSourceDocs().iterator(); iterSource.hasNext();) {
                    SourceDoc sd = (SourceDoc) iterSource.next();
                    if (sd.getTargetDocs() != null) {
                        for (Iterator iterTarget = sd.getTargetDocs().iterator(); iterTarget.hasNext();) {
                            TargetDoc td = (TargetDoc) iterTarget.next();
                            if (!td.getLanguage().equals("All")) {

                                String inputfilepath = "/Users/abhisheksingh/Project/templates/COT1.docx";
                                inputfilepath = "C:/templates/COT1.docx";
                                WordprocessingMLPackage wordMLPackage = Docx4J.load(new java.io.File(inputfilepath));
                                java.util.HashMap mappings = new java.util.HashMap();
                                VariablePrepare.prepare(wordMLPackage);//see notes
                                mappings.put("SOURCE", sd.getLanguage());
                                mappings.put("TARGET", td.getLanguage());
                                mappings.put("PROJECTNUMBER", p.getNumber() + p.getCompany().getCompany_code());
                                mappings.put("PM", StandardCode.getInstance().noNull(p.getPm()));
                                mappings.put("COMPANY", p.getCompany().getCompany_name());
                                mappings.put("DATE", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
                                mappings.put("COMPANY", StandardCode.getInstance().noNull(p.getCompany().getCompany_name()));
                                mappings.put("PRODUCT", StandardCode.getInstance().noNull(p.getProduct()) + " - " + StandardCode.getInstance().noNull(p.getProductDescription()));
//              
                                Random rand = new Random();
                                wordMLPackage.getMainDocumentPart().variableReplace(mappings);
                                writeDocxToStream(wordMLPackage, "C:/TA-" + projectId + "/" + p.getNumber() + p.getCompany().getCompany_code() + "-" + td.getLanguage() + "-Translation-Approval.docx");

//                                  
                            }

                        }
                    }
                }
            }
        } catch (Exception e) {
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

    private void writeDocxToStream(WordprocessingMLPackage template, String target) throws IOException, Docx4JException {
        File f = new File(target);
        template.save(f);
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
