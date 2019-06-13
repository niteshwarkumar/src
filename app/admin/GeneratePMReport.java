/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.admin;

import app.client.Client;
import app.project.DtpTask;
import app.project.EngTask;
import app.project.LinTask;
import app.project.OthTask;
import app.project.Project;
import app.project.ProjectService;
import app.project.SourceDoc;
import app.project.TargetDoc;
import app.quote.Quote1;
import app.quote.QuoteService;
import app.security.SecurityService;
import app.user.User;
import app.user.UserService;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.rtf.IRTFDocumentTransformer;
import net.sourceforge.rtf.RTFTemplate;
import net.sourceforge.rtf.handler.RTFDocumentHandler;
import net.sourceforge.rtf.template.velocity.RTFVelocityTransformerImpl;
import net.sourceforge.rtf.template.velocity.VelocityTemplateEngineImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.apache.velocity.app.VelocityEngine;

/**
 *
 * @author niteshwar
 */
public class GeneratePMReport extends Action {

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

        User user = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));

        String rtfSource = "/Users/abhisheksingh/Project/templates/PMStatusReport.rtf";
//        rtfSource = "/Users/abhisheksingh/Project/templates/GENERAL Template.rtf";
        rtfSource = "C:/templates/PMStatusReport.rtf";
        RTFTemplate rtfTemplate = new RTFTemplate();
        // Parser
        RTFDocumentHandler parser = new RTFDocumentHandler();
        rtfTemplate.setParser(parser);
        // Transformer
        IRTFDocumentTransformer transformer = new RTFVelocityTransformerImpl();
        rtfTemplate.setTransformer(transformer);
        // Template engine 
        VelocityTemplateEngineImpl templateEngine = new VelocityTemplateEngineImpl();
        // Initialize velocity engine
        VelocityEngine velocityEngine = new VelocityEngine();
        templateEngine.setVelocityEngine(velocityEngine);
        rtfTemplate.setTemplateEngine(templateEngine);

        java.io.File file = new java.io.File(rtfSource);

        List projectList = ProjectService.getInstance().getMyProjects(user);
        List quoteList = QuoteService.getInstance().getPMPendingQuotes(user.getFirstName() + " " + user.getLastName());
//  
        // 3. Set the RTF model source 
        rtfTemplate.setTemplate(file);

        rtfTemplate.put("pmName", user.getFirstName() + "_" + user.getLastName());
        rtfTemplate.put("date", new Date());
        if (null != projectList) {
            rtfTemplate.put("noOfProject", projectList.size());
        } else {
            rtfTemplate.put("noOfProject", 0);
        }
        if (null != quoteList) {
            rtfTemplate.put("noOfQuote", quoteList.size());
        } else {
            rtfTemplate.put("noOfQuote", 0);
        }

        List PLIST = new ArrayList();
        if (null != projectList) {
            for (int i = 0; i < projectList.size(); i++) {
                Map<String, Object> project = new HashMap<>();
                Project p = (Project) projectList.get(i);

                Client c = p.getCompany();
                project.put("clientName", c.getCompany_name());
                project.put("dueDate", p.getDueDate());
                project.put("product", p.getProduct());
                project.put("description", p.getProjectDescription());

                project.put("src", p.getSrcLang());
                project.put("tgt", p.getTargetLang());
                project.put("tgtCnt", p.getTargetLangCnt());
                project.put("task", p.getTask());
                project.put("inteqaNotes", "");

                project.put("notes", p.getNotes());
                project.put("number", p.getNumber() + c.getCompany_code());
                PLIST.add(project);

            }
            rtfTemplate.put("project", PLIST);
        }
        List QLIST = new ArrayList();
        if (null != quoteList) {
            for (int i = 0; i < quoteList.size(); i++) {
                Quote1 q = (Quote1) quoteList.get(i);
                Client c = q.getProject().getCompany();

                Set sourceList = q.getSourceDocs();
                String src = "", tgt = "", task = "";
                int tgtCnt = 0;
                // ArrayList tasksIncluded = new ArrayList();
                //for each source
                try{
                for (Iterator sourceIter = sourceList.iterator(); sourceIter.hasNext();) {
                    SourceDoc sd = (SourceDoc) sourceIter.next();
                    if (!src.isEmpty()) {
                        src += ", ";
                    }
                    src += sd.getLanguage();
                    for (Iterator linTargetIter = sd.getTargetDocs().iterator(); linTargetIter.hasNext();) {
                        TargetDoc td = (TargetDoc) linTargetIter.next();
                        if (!td.getLanguage().equalsIgnoreCase("all") && !tgt.contains(td.getLanguage())) {
                            if (!tgt.isEmpty()) {
                                tgt += ", ";
                            }
                            tgtCnt++;
                            tgt += td.getLanguage();

                        }

                        if (td.getLinTasks() != null) {
                            for (Iterator iterLin = td.getLinTasks().iterator(); iterLin.hasNext();) {
                                LinTask t = (LinTask) iterLin.next();

                                if (t.getTaskName().equals("Other")) { //use notes
                                    if (!task.contains(t.getNotes())) {
                                        if (!task.isEmpty()) {
                                            task += ", ";
                                        }
                                        task += t.getNotes();
                                    }
                                } else //use taskName
                                if (!task.contains(t.getTaskName())) {
                                    if (!task.isEmpty()) {
                                        task += ", ";
                                    }
                                    task += t.getTaskName();
                                }

                            }
                        }//end linTasks

                        if (td.getEngTasks() != null) {
                            for (Iterator iterEng = td.getEngTasks().iterator(); iterEng.hasNext();) {
                                EngTask t = (EngTask) iterEng.next();
                                if (t.getTaskName().equals("Other")) { //use notes
                                    if (!task.contains(t.getNotes())) {
                                        if (!task.isEmpty()) {
                                            task += ", ";
                                        }
                                        task += t.getNotes();
                                    }
                                } else //use taskName
                                if (!task.contains(t.getTaskName())) {
                                    if (!task.isEmpty()) {
                                        task += ", ";
                                    }
                                    task += t.getTaskName();
                                }
                            }
                        }//end engTasks

                        if (td.getDtpTasks() != null) {
                            for (Iterator iterDtp = td.getDtpTasks().iterator(); iterDtp.hasNext();) {
                                DtpTask t = (DtpTask) iterDtp.next();
                                if (t.getTaskName().equals("Other")) { //use notes
                                    if (!task.contains(t.getNotes())) {
                                        if (!task.isEmpty()) {
                                            task += ", ";
                                        }
                                        task += t.getNotes();
                                    }
                                } else //use taskName
                                if (!task.contains(t.getTaskName())) {
                                    if (!task.isEmpty()) {
                                        task += ", ";
                                    }
                                    task += t.getTaskName();
                                }
                            }
                        }//end dtpTasks

                        if (td.getOthTasks() != null) {
                            for (Iterator iterOth = td.getOthTasks().iterator(); iterOth.hasNext();) {
                                OthTask t = (OthTask) iterOth.next();
                                if (t.getTaskName().equals("Other")) { //use notes
                                    if (!task.contains(t.getNotes())) {
                                        if (!task.isEmpty()) {
                                            task += ", ";
                                        }
                                        task += t.getNotes();
                                    }
                                } else //use taskName
                                if (!task.contains(t.getTaskName())) {
                                    if (!task.isEmpty()) {
                                        task += ", ";
                                    }
                                    task += t.getTaskName();
                                }
                            }
                        }//end othTasks

                        if (!td.getLanguage().equalsIgnoreCase("all") && !tgt.contains(td.getLanguage())) {
                            if (!tgt.isEmpty()) {
                                tgt += ", ";
                            }
                            tgtCnt++;
                            tgt += td.getLanguage();

                        }
                    }
                }
            }catch(Exception ee){}
                Map<String, Object> quote = new HashMap<>();
                quote.put("clientName", c.getCompany_name());
//            quote.put("dueDate", q.getDueDate()) ;
                quote.put("product", q.getProject().getProduct());
                quote.put("description", q.getProject().getProductDescription().replaceAll(q.getProject().getProduct() + " - ", "").replaceAll(q.getProject().getProduct() + "-", ""));
//            
                quote.put("src", src);
                quote.put("tgt", tgt);
                quote.put("tgtCnt", tgtCnt);
                quote.put("task", task) ;
                quote.put("inteqaNotes", "");

                quote.put("notes", q.getNote());
                quote.put("number", q.getNumber());
                QLIST.add(quote);
            }
            rtfTemplate.put("quote", QLIST);
        }
        //END content
        String rtfTarget = user.getFirstName() + "_" + user.getLastName() + ".doc";

        rtfTemplate.merge(rtfTarget);
        //System.out.println(rtfTemplate.getTransformedDocument().toString());

        try {
//            String fname = "C:/PO" + p.getNumber() + p.getCompany().getCompany_code() + ".zip";
            String strHeader = "Attachment;Filename=" + rtfTarget;
            response.setHeader("Content-Disposition", strHeader);
            java.io.File fileToDownload = new java.io.File(rtfTarget);
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
        deleteFile(rtfTarget);

//        //write to client (web browser)
//        response.setHeader("Content-Type", "Application/msword");
//        response.setContentLength((int) content.length());  
////        response.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
//        String filename = q.getNumber() + "-" + q.getProject().getCompany().getCompany_name().replaceAll(" ", "_").replaceAll(",", "_") + "_quote.doc";
//        response.setHeader("Content-disposition", "attachment; filename=" + filename);
//        OutputStream os = response.getOutputStream();
//        os.write(content.getBytes());
//        os.flush(); 
        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }

    // Returns the contents of the file in a byte array.
    private static byte[] getBytesFromFile(java.io.File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }

    public static boolean deleteFile(String sFilePath) {
        java.io.File oFile = new java.io.File(sFilePath);
        if (oFile.isDirectory()) {
            java.io.File[] aFiles = oFile.listFiles();
            for (java.io.File oFileCur : aFiles) {
                deleteFile(oFileCur.getAbsolutePath());
            }
        }
        return oFile.delete();
    }
}
