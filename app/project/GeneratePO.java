/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.project;

import app.inteqa.INEngineering;
import app.inteqa.InteqaService;
import app.resource.Resource;
import app.resource.ResourceService;
import java.text.*;
import app.standardCode.*;
import com.lowagie.text.pdf.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import java.io.*;
import java.util.*;
import app.security.*;
import app.user.*;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.struts.validator.DynaValidatorForm;

/**
 *
 * @author Neil
 */
public class GeneratePO extends Action {

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
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }

        //END check for login (security)
        String pdfNameLng = "";
        String pdfNamePoNo = "";
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
            List results = ProjectService.getInstance().getProjectList();

            ListIterator iterScroll = null;
            for (iterScroll = results.listIterator(); iterScroll.hasNext(); iterScroll.next()) {
            }
            iterScroll.previous();
            Project p = (Project) iterScroll.next();
            projectId = String.valueOf(p.getProjectId());
        }

        Integer id = Integer.valueOf(projectId);
        DynaValidatorForm qvg = (DynaValidatorForm) form;




        (new java.io.File("C:/" + projectId)).mkdir();
        Project p = ProjectService.getInstance().getSingleProject(id);

        String Po6Tier = "6";
        if (!p.getCompany().isScaleDefault()) {
            Po6Tier = "";
        }

        String test = p.getCompany().getAddress_1();
        ArrayList poNums = new ArrayList();
        Integer flagT = 0, flagE = 0, flagP = 0;
        String formFileName = "";
        String tasktype = "";
        User u = UserService.getInstance().getSingleUserRealName(StandardCode.getInstance().getFirstName(p.getPm()), StandardCode.getInstance().getLastName(p.getPm()));
        LinTask[] linTasks = (LinTask[]) qvg.get("linTasksProject");

        LinTask lt = null;
        String poNumber = ProjectService.getInstance().getNewPoNumber(p);
        for (int i = 0; i < linTasks.length; i++) {
            /// System.out.println(request.getParameter("POCheck" + String.valueOf(i)));
            if (request.getParameter("POCheck" + String.valueOf(i)) != null) {
                try {

                    lt = ProjectService.getInstance().getSingleLinTask(Integer.parseInt(request.getParameter("LinId" + String.valueOf(i))));
                } catch (Exception e) {
                    lt = linTasks[i];
                }
                System.out.println(lt.getTaskName() + "      " + request.getParameter("POCheck" + String.valueOf(i)));
                lt.setPoNumber(poNumber);
                ProjectService.getInstance().updateLinTask(lt);
                flagT = 0;
                flagP = 0;
                flagE = 0;
                if (lt.getTaskName().contentEquals("Translation")) {
                    flagT = 1;
                }
                if (lt.getTaskName().contentEquals("Editing")) {
                    flagE = 1;
                }
                if (lt.getTaskName().trim().contentEquals("Proofreading") || lt.getTaskName().contentEquals("Proofreading / Linguistic QA")) {
                    flagP = 1;
                }
                if (lt.getMulti() != null) {

                    if (lt.getMulti() != null && !lt.getMulti().equalsIgnoreCase("T&E") && !lt.getMulti().equalsIgnoreCase("T&P") && !lt.getMulti().equalsIgnoreCase("E&P")) {


                        if (!p.getCompany().isScaleDefault()) {
                            if (flagT == 1 && flagE == 0 && flagP == 0) {
                                formFileName = "PO_Translator.pdf";
                                tasktype = "Translation";
                            }
                            if (flagT == 0 && flagE == 1 && flagP == 0) {
                                formFileName = "PO_Editor.pdf";
                                tasktype = "Editing";
                            }
                            if (flagT == 0 && flagE == 0 && flagP == 1) {
                                formFileName = "PO_Proofreader.pdf";
                                tasktype = "Proofreading";
                            }
                        } else {
                            if (flagT == 1 && flagE == 0 && flagP == 0) {
                                formFileName = "PO_Translator6.pdf";
                                tasktype = "Translation";
                            }
                            if (flagT == 0 && flagE == 1 && flagP == 0) {
                                formFileName = "PO_Editor6.pdf";
                                tasktype = "Editing";
                            }
                            if (flagT == 0 && flagE == 0 && flagP == 1) {
                                formFileName = "PO_Proofreader6.pdf";
                                tasktype = "Proofreading";
                            }
                        }
                    } else {

                        if (lt.getMulti() != null && !p.getCompany().isScaleDefault()) {
                            if (lt.getMulti().equalsIgnoreCase("T&E")) {
                                formFileName = "PO_TE.pdf";
                                tasktype = "T&E";
                            }
                            if (lt.getMulti().equalsIgnoreCase("T&P")) {
                                formFileName = "PO_TP.pdf";
                                tasktype = "T&P";
                            }
                            if (lt.getMulti().equalsIgnoreCase("E&P")) {
                                formFileName = "PO_EP.pdf";
                                tasktype = "E&P";
                            }
                        } else {
                            if (lt.getMulti().equalsIgnoreCase("T&E")) {
                                formFileName = "PO_TE6.pdf";
                                tasktype = "T&E";
                            }
                            if (lt.getMulti().equalsIgnoreCase("T&P")) {
                                formFileName = "PO_TP6.pdf";
                                tasktype = "T&P";
                            }
                            if (lt.getMulti().equalsIgnoreCase("E&P")) {
                                formFileName = "PO_EP6.pdf";
                                tasktype = "E&P";
                            }
                        }


                    }
                } else {
                    if (!p.getCompany().isScaleDefault()) {
                        if (flagT == 1 && flagE == 0 && flagP == 0) {
                            formFileName = "PO_Translator.pdf";
                            tasktype = "Translation";
                        }
                        if (flagT == 0 && flagE == 1 && flagP == 0) {
                            formFileName = "PO_Editor.pdf";
                            tasktype = "Editing";
                        }
                        if (flagT == 0 && flagE == 0 && flagP == 1) {
                            formFileName = "PO_Proofreader.pdf";
                            tasktype = "Proofreading";
                        }
                    } else {
                        if (flagT == 1 && flagE == 0 && flagP == 0) {
                            formFileName = "PO_Translator6.pdf";
                            tasktype = "Translation";
                        }
                        if (flagT == 0 && flagE == 1 && flagP == 0) {
                            formFileName = "PO_Editor6.pdf";
                            tasktype = "Editing";
                        }
                        if (flagT == 0 && flagE == 0 && flagP == 1) {
                            formFileName = "PO_Proofreader6.pdf";
                            tasktype = "Proofreading";
                        }
                    }

                }

                System.out.println(lt.getTaskName() + "       ---------         " + formFileName);
                pdfNameLng = lt.getTargetDoc().getLanguage();
                pdfNamePoNo = lt.getPoNumber();
                PdfReader reader = new PdfReader("C:/templates/" + formFileName);
                ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
                PdfStamper stamp = new PdfStamper(reader, new FileOutputStream("C:/" + projectId + "/" + p.getNumber() + p.getCompany().getCompany_code() + "-" + pdfNameLng + "-" + tasktype + ".pdf"));
                AcroFields form1 = stamp.getAcroFields();


                try {
                    form1.setField("contractor", " ");
                    if (lt.getPersonName() != null && lt.getPersonName().length() > 0) {
                        Resource r = ResourceService.getInstance().getSingleResource(Integer.valueOf(lt.getPersonName()));
                        //set the field values in the pdf form

                        if ((r.getFirstName().length() >= 1 && r.getFirstName() != null) && (r.getLastName().length() >= 1 && r.getLastName() != null)) {
                            form1.setField("contractor", StandardCode.getInstance().noNull(r.getFirstName()) + " " + StandardCode.getInstance().noNull(r.getLastName()));
                        } else {
                            form1.setField("contractor", StandardCode.getInstance().noNull(r.getCompanyName()));
                        }
                    }
                } catch (Exception e) {
                    form1.setField("contractor", " ");
                }
                form1.setField("Project", p.getNumber() + p.getCompany().getCompany_code());
                form1.setField("PO", p.getNumber() + p.getCompany().getCompany_code() + "-PO-" + lt.getPoNumber());
                poNumber = lt.getPoNumber();
                form1.setField("Deliverby", "email");
                if (lt.getDueDateDate() != null) {
                    form1.setField("Deadline", DateFormat.getDateInstance(DateFormat.SHORT).format(lt.getDueDateDate()));
                }

                form1.setField("date", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
                form1.setField("PM", p.getPm());
                //form1.setField("Instructions", p.get); Instructions doesn't exist in DB
                form1.setField("Currency", lt.getInternalCurrency());
                try {
                    form1.setField("Email", u.getWorkEmail1());

                    if (u.getWorkPhoneEx() != null && u.getWorkPhoneEx().length() > 0) { //ext present
                        form1.setField("Phone", StandardCode.getInstance().noNull(u.getWorkPhone()) + " ext " + StandardCode.getInstance().noNull(u.getWorkPhoneEx()));
                    } else { //no ext present
                        form1.setField("Phone", StandardCode.getInstance().noNull(u.getWorkPhone()));
                    }
                    form1.setField("Fax", StandardCode.getInstance().noNull(u.getLocation().getFax_number()));
                    form1.setField("Address", StandardCode.getInstance().printLocation(u.getLocation()));
                } catch (Exception e) {
                }

                form1.setField("Language", lt.getTargetDoc().getLanguage());

                form1.setField("Unit1", lt.getUnits());
            double scale100 = 0.25;
            double scaleRep = 0.25;
            double scale8599 = 0.50;
            double scaleNew4 = 1.0;
            double scalePerfect = 0.20;
            double scaleContext = 0.20;
            double scale95 = 0.50;
            double scale85 = 0.50;
            double scale75 = 0.60;
            double scaleNew = 1.0;

                 try {
                Resource res = ResourceService.getInstance().getSingleResource(Integer.parseInt(lt.getPersonName()));

                scale100 = new Double(res.getScale100()).doubleValue();
                scaleRep = new Double(res.getScaleRep()).doubleValue();
                scale8599 = new Double(res.getScale8599()).doubleValue();
                scaleNew4 = new Double(res.getScaleNew4()).doubleValue();
                scalePerfect = new Double(res.getScalePerfect()).doubleValue();
                scaleContext = new Double(res.getScaleContext()).doubleValue();
                scale95 = new Double(res.getScale95()).doubleValue();
                scale85 = new Double(res.getScale85()).doubleValue();
                scale75 = new Double(res.getScale75()).doubleValue();
                scaleNew = new Double(res.getScaleNew()).doubleValue();
            } catch (Exception e) {
            }

                double rate = 0.0;
                if (lt.getInternalRate() != null) {
                    form1.setField("Rate_new", lt.getInternalRate());
                    rate = Double.parseDouble(lt.getInternalRate());
                }

                double totalWords = 0;
                double newWords = 0;




                if (!p.getCompany().isScaleDefault() && lt.getWordNew() != null) {
                    form1.setField("Volume_new", lt.getWordNew().toString());
                    newWords = lt.getWordNew().doubleValue();
                    totalWords += newWords;
                } else if (lt.getWordNew4() != null) {
                    form1.setField("Volume_new", lt.getWordNew4().toString());
                    newWords = lt.getWordNew4().doubleValue();
                    totalWords += newWords;
                }

                double costTotalNew = rate * newWords;
                form1.setField("Cost_new", "" + StandardCode.getInstance().formatDouble(costTotalNew * 100 / 100.00));

                //Set VOLUME fields
                double word100Cost = 0.0;
                if (lt.getWord100() != null) {
                    form1.setField("Volume_100", lt.getWord100().toString());
                    word100Cost = rate * scale100 * lt.getWord100().intValue();
                    totalWords += lt.getWord100().intValue();
                }


                double wordRepCost = 0.0;
                double wordRep8599 = 0.0;
                double wordRep7584 = 0.0;
                double wordRep8594 = 0.0;
                double wordRep9599 = 0.0;
                double wordPerfect = 0.0;
                double wordContext = 0.0;



                if (lt.getWordRep() != null) {
                    form1.setField("Volume_rep", lt.getWordRep().toString());
                    wordRepCost = rate * scaleRep * lt.getWordRep().intValue();
                    totalWords += lt.getWordRep().intValue();
                }
                if (!p.getCompany().isScaleDefault() && lt.getWord75() != null) {
                    form1.setField("Volume_7584", lt.getWord75().toString());
                    wordRep7584 = rate * scale75 * lt.getWord75().intValue();
                    totalWords += lt.getWord75().intValue();
                    form1.setField("Rate_7584", StandardCode.getInstance().formatDouble3(new Double(rate * scale75)));
                    form1.setField("Cost_7584", "" + StandardCode.getInstance().formatDouble(new Double(wordRep7584)));
                }
                if (!p.getCompany().isScaleDefault() && lt.getWord85() != null) {
                    form1.setField("Volume_8594", lt.getWord85().toString());
                    wordRep8594 = rate * scale85 * lt.getWord85().intValue();
                    totalWords += lt.getWord85().intValue();
                    form1.setField("Rate_8594", StandardCode.getInstance().formatDouble3(new Double(rate * scale85)));
                    form1.setField("Cost_8594", "" + StandardCode.getInstance().formatDouble(new Double(wordRep8594)));
                }

                if (!p.getCompany().isScaleDefault() && lt.getWord95() != null) {
                    form1.setField("Volume_9599", lt.getWord95().toString());
                    wordRep9599 = rate * scale95 * lt.getWord95().intValue();
                    totalWords += lt.getWord95().intValue();
                    form1.setField("Rate_9599", StandardCode.getInstance().formatDouble3(new Double(rate * scale95)));
                    form1.setField("Cost_9599", "" + StandardCode.getInstance().formatDouble(new Double(wordRep9599)));
                }
                if (lt.getWordPerfect() != null) {
                    form1.setField("Volume_Perfect", lt.getWordPerfect().toString());
                    wordPerfect = rate * scalePerfect * lt.getWordPerfect().intValue();
                    totalWords += lt.getWordPerfect().intValue();
                    form1.setField("Rate_Perfect", StandardCode.getInstance().formatDouble3(new Double(rate * scalePerfect)));
                    form1.setField("Cost_Perfect", "" + StandardCode.getInstance().formatDouble(new Double(wordPerfect)));
                }
                if (lt.getWordContext() != null) {
                    form1.setField("Volume_Context", lt.getWordContext().toString());
                    wordContext = rate * scaleContext * lt.getWordContext().intValue();
                    totalWords += lt.getWordContext().intValue();
                    form1.setField("Rate_Context", StandardCode.getInstance().formatDouble3(new Double(rate *scaleContext)));
                    form1.setField("Cost_Context", "" + StandardCode.getInstance().formatDouble(new Double(wordContext)));
                }
                if (p.getCompany().isScaleDefault() && lt.getWord8599() != null) {
                    form1.setField("Volume_8599", lt.getWord8599().toString());
                    wordRep8599 = rate * scale8599 * lt.getWord8599().intValue();
                    totalWords += lt.getWord8599().intValue();
                    form1.setField("Rate_8599", StandardCode.getInstance().formatDouble3(new Double(rate * scale8599)));
                    form1.setField("Cost_8599", "" + StandardCode.getInstance().formatDouble(new Double(wordRep8599)));
                }




                form1.setField("Rate_100", StandardCode.getInstance().formatDouble3(new Double(rate * scale100)));
                form1.setField("Rate_rep", StandardCode.getInstance().formatDouble3(new Double(rate * scaleRep)));
                form1.setField("Cost_100", "" + StandardCode.getInstance().formatDouble(new Double(word100Cost)));
                form1.setField("Cost_rep", "" + StandardCode.getInstance().formatDouble(new Double(wordRepCost)));

                double totalAmount = 0;
                if (!p.getCompany().isScaleDefault()) {
                    totalAmount = costTotalNew + word100Cost + wordRepCost + wordRep8599 + wordRep9599 + wordRep7584 + wordRep8594 + wordContext + wordPerfect;
                } else {
                    totalAmount = costTotalNew + word100Cost + wordRepCost + wordRep8599 + wordContext + wordPerfect;
                }
                form1.setField("Cost_total", "" + StandardCode.getInstance().formatDouble(new Double(totalAmount)));

                form1.setField("totalwords", "" + totalWords);

                //Add tasks
                //tasktype="Translation";

                form1.setField("Task1", lt.getTaskName());
                try {
                    if (lt.getMulti() != null && lt.getMulti().equalsIgnoreCase("T&E")) {
                        form1.setField("Task1", "Translation");
                        form1.setField("Task2", "Editing");
                    }
                    if (lt.getMulti() != null && lt.getMulti().equalsIgnoreCase("T&P")) {
                        form1.setField("Task1", "Translation");
                        form1.setField("Task2", "Proofreading");
                    }
                    if (lt.getMulti() != null && lt.getMulti().equalsIgnoreCase("E&P")) {
                        form1.setField("Task1", "Editing");
                        form1.setField("Task2", "Proofreading");
                    }
                } catch (Exception e) {
                }
                try {
                     INEngineering inEngg = InteqaService.getInstance().getINEngineering(id);
                String Instr3="";
                if(inEngg.isLpr1()==true){Instr3+="Keep file name as is\n";}
                if(inEngg.isLpr2()==true){Instr3+="Keep folder structure as is\n";}
                if(inEngg.isLpr3()==true){Instr3+="Use TagEditor\n";}
                if(inEngg.isLpr4()==true){Instr3+="Use the specific .ini.file\n";}
                if(inEngg.isLpr5()==true){Instr3+=inEngg.getLpr5Text()+"\n";}
                if(inEngg.isLpr6()==true){Instr3+=inEngg.getLpr6Text()+"\n";}
              
                form1.setField("Instr-3", Instr3);
                    
                } catch (Exception e) {
                }
               
               

//                if(tasktype.equalsIgnoreCase("T&E")){
//                form1.setField("Task1", "Translation");
//                form1.setField("Task2", "Editing");
//                }else if(tasktype.equalsIgnoreCase("T&P")){
//                form1.setField("Task1", "Translation");
//                form1.setField("Task2", "Proofreading");
//                }else if(tasktype.equalsIgnoreCase("E&P")){
//                form1.setField("Task1", "Editing");
//                form1.setField("Task2", "Proofreading");
//                }else
//                form1.setField("Task1", lt.getTaskName());


                stamp.close();

                //(new File("C:/log")).mkdir();
                //FileOutputStream fs=new FileOutputStream("C:/"+p.getNumber() + p.getCompany().getCompany_code() + "-" + pdfNamePoNo +"-"+pdfNameLng+ ".pdf");
                //  fs=new FileOutputStream("C:/"+pdfStream.toString());
                // fs=new FileOutputStream(null);
                // System.out.println(fs.toString());
                // response.getOutputStream();
//       response.setHeader("Content-disposition", "hidden; filename=" + p.getNumber() + p.getCompany().getCompany_code() + "-" + pdfNamePoNo +"-"+pdfNameLng+ ".pdf");
//             OutputStream os = response.getOutputStream();
//             pdfStream.writeTo(os);
//             os.flush();
            }
        }
        //}}
        try {
            java.io.File inFolder = new java.io.File("C:/" + projectId);
            java.io.File outFolder = new java.io.File("C:/PO" + p.getNumber() + p.getCompany().getCompany_code() + ".zip");
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
            String fname = "C:/PO" + p.getNumber() + p.getCompany().getCompany_code() + ".zip";
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

        deleteFile("C:/PO" + p.getNumber() + p.getCompany().getCompany_code() + ".zip");
        //   (new java.io.File("C:/"+p.getNumber() + p.getCompany().getCompany_code())).mkdir();

        for (Iterator iter1 = p.getSourceDocs().iterator(); iter1.hasNext();) {
            SourceDoc sd = (SourceDoc) iter1.next();
            for (Iterator iter2 = sd.getTargetDocs().iterator(); iter2.hasNext();) {
                TargetDoc td = (TargetDoc) iter2.next();
                poNumber = ProjectService.getInstance().getNewPoNumber(p);
                for (Iterator iter = td.getLinTasks().iterator(); iter.hasNext();) {
                    LinTask lt1 = (LinTask) iter.next();
                    pdfNameLng = lt1.getTargetDoc().getLanguage();
                    pdfNamePoNo = lt1.getPoNumber();
                    File inpt = new File("C:/" + projectId + "/" + p.getNumber() + p.getCompany().getCompany_code() + "-" + pdfNameLng + "-" + tasktype + ".pdf");
                    // deleteFile("C:/"+projectId+"/"+p.getNumber() + p.getCompany().getCompany_code() + "-" + pdfNamePoNo +"-"+pdfNameLng+ ".pdf");
                    inpt.delete();

                }
            }
        }

        deleteFile("C:/" + projectId);



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
