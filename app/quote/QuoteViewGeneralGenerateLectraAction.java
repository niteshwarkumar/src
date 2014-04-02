/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.quote;

import app.client.Client;
import app.client.ClientService;
import app.extjs.vo.Product;
import app.extjs.vo.Upload_Doc;
import app.project.DtpTask;
import app.project.EngTask;
import app.project.LinTask;
import app.project.Project;
import app.project.SourceDoc;
import app.project.TargetDoc;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import app.security.*;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import java.io.FileInputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *
 * @author Niteshwar
 */
public class QuoteViewGeneralGenerateLectraAction extends Action {

    // ----------------------------------------------------- Instance Variables
    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log =
            LogFactory.getLog("org.apache.struts.webapp.Example");

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
        //Row,column,sheet starts from 0

        String quoteId = null;
        quoteId = request.getParameter("quoteViewId");

        //check attribute in request
        if (quoteId == null) {
            quoteId = (String) request.getAttribute("quoteViewId");
        }

        //id of quote from cookie
        if (quoteId == null) {
            quoteId = StandardCode.getInstance().getCookie("quoteViewId", request.getCookies());
        }

        //default client to first if not in request or cookie
        if (quoteId == null) {
            java.util.List results = QuoteService.getInstance().getQuoteList();
            Quote1 first = (Quote1) results.get(0);
            quoteId = String.valueOf(first.getQuote1Id());
        }

        Integer id = Integer.valueOf(quoteId);
        Integer tFlag = 0, dFlag = 0, eFlag = 0, dLang = 0, eLang = 0;
        Locale locale = Locale.getDefault();

        //int quoteId = 4025;
        Client c = ClientService.getInstance().getClient(100);
        Quote1 q = QuoteService.getInstance().getSingleQuote(id);
        Project p = q.getProject();
        Client_Quote cq = QuoteService.getInstance().get_SingleClientQuote(id);


        List quoteList = QuoteService.getInstance().getSingleClientQuote(id);

        String filein = "C:/templates/lectra_excel_template.xls";
        //String fileout = "D:/lectra_excel.xls";

        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filein));

        HSSFWorkbook wb = new HSSFWorkbook(fs, true);

        HSSFSheet excelSheet = wb.getSheetAt(1);



//    String excelName = "Report";
//    OutputStream out = null;
//
//
//        response.setContentType("application/vnd.ms-excel");
//        response.setHeader("Content-Disposition", "attachment; filename="+excelName+".xls");
//        Workbook template = Workbook.getWorkbook(this.getClass().getResourceAsStream("/static/RRTemplate.xls"));
//        WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream(), template);
//        WritableSheet worksheet = workbook.getSheet(0);


        //Project Info
        printExcel(wb, 0, 7, 2, cq.getProductText());
        printExcel(wb, 0, 8, 2, cq.getType());
        printExcel(wb, 0, 9, 2, p.getProduct());
        printExcel(wb, 0, 10, 2, "");
        printExcel(wb, 0, 12, 2, cq.getComponent());

         printExcel(wb, 1, 1, 3, q.getNumber());
        printExcel(wb, 1, 2, 3, p.getProductDescription());
        printExcel(wb, 1, 3, 3, q.getEnteredByTS());
        if(q.getPmPercent()!=null)
        printExcel(wb, 1, 34, 2, Double.parseDouble(q.getPmPercent()));

        printExcel(wb, 2, 7, 1, q.getNumber()+" - "+StandardCode.getInstance().noNull(cq.getProductText()));
        printExcel(wb, 2, 8, 1, cq.getComponent());
//        printExcel(wb, 0, 12, 2, cq.getComponent());

        try {
            printExcel(wb, 0, 13, 2, StandardCode.getInstance().noNull(q.getProject().getContact().getFirst_name()) + " " + StandardCode.getInstance().noNull(q.getProject().getContact().getLast_name()));
        } catch (Exception e) {
            try {
//                User u = UserService.getInstance().getSingleUser(q.getEnteredById());
//                ClientContact cc = ClientService.getInstance().getSingleClientContact(u.getClient_contact());
//                printExcel(wb, 0, 13, 2, StandardCode.getInstance().noNull(cc.getFirst_name()) + " " + StandardCode.getInstance().noNull(cc.getLast_name()));
            } catch (Exception e1) {
                User u = UserService.getInstance().getSingleUser(q.getEnteredById());
                printExcel(wb, 0, 13, 2, StandardCode.getInstance().noNull(u.getFirstName()) + " " + StandardCode.getInstance().noNull(u.getLastName()));
            }
        }

        //Timing Quote & Project
        printExcel(wb, 0, 18, 2, q.getEnteredByTS());


        //Services Requested
        int srcCount = 37, tgtCount = 40, dtpCount = 24, engCount = 31, componentCount = 58, sourceCount = 58, deliverableCount = 58, fileCount = 65, count = -1;
        String othSrcLang = "", othTgtLang = "", othDtp = "", othEng = "";
        int page3langRow = 12, page3langCol = 0;
        ArrayList tasksIncludedLin = new ArrayList();
        ArrayList tasksIncludedEng = new ArrayList();
        ArrayList tasksIncludedDtp = new ArrayList();
        ArrayList tasksIncludedOth = new ArrayList();
        if (q.getSourceDocs() != null) {
            for (Iterator iterSource = q.getSourceDocs().iterator(); iterSource.hasNext();) {
                SourceDoc sd = (SourceDoc) iterSource.next();
                if (sd.getLanguage().contains("English")) {
                    printExcel(wb, 0, srcCount++, 2, "ENGLISH");
                } else if (sd.getLanguage().contains("French")) {
                    printExcel(wb, 0, srcCount++, 2, "FRENCH");
                } else {
                    othSrcLang += sd.getLanguage();
                } 
                if (sd.getTargetDocs() != null) {
                    
                    for (Iterator iterTarget = sd.getTargetDocs().iterator(); iterTarget.hasNext();) {
                        page3langCol++;
                        TargetDoc td = (TargetDoc) iterTarget.next();
                        count++;
                        System.out.println("Count---------------------------->>>>>>"+count);

                        if (page3langRow < 18) {
                            if (page3langCol < 18) {
                                try {
                                    if(page3langCol>2){page3langCol=1;page3langRow++;}
                                    printExcel(wb, 2, page3langRow, page3langCol, td.getLanguage());
                                } catch (Exception e) {
                                }
                                
                                
                            }

                            
                        }

                        if (tgtCount < 50) {
                            printExcel(wb, 0, tgtCount++, 2, td.getLanguage());
                        } else {
                            othSrcLang += td.getLanguage();
                        }
                        if (count == 0) {
                            printExcel(wb, 1, 7, 3, td.getLanguage());
                        } else {
                            try {
                                for (int i = 7; i <= 40; i++) {
                                    copyExcelStyle(wb, i, 3, i, (count * 3) + 3);
                                    copyExcelStyle(wb, i, 4, i, (count * 3) + 4);
                                    copyExcelStyle(wb, i, 5, i, (count * 3) + 5);
                                }
                                try {
                                    copyExcelStyle(wb, 7, 4, 7, (count * 3) + 4, td.getLanguage());
                                } catch (Exception e) {
                                }
                                try {
                                    copyExcelStyle(wb, 7, 5, 7, (count * 3) + 5, td.getLanguage());
                                } catch (Exception e) {
                                }
                                excelSheet.addMergedRegion(new Region(7, (short) ((count * 3) + 3), 7, (short) ((count * 3) + 5)));
//                                excelSheet.addMergedRegion(new Region(7, (short) ((count * 3) + 3), 7, (short) ((count * 3) + 5)));
                                try {
                                    copyExcelStyle(wb, 7, 3, 7, (count * 3) + 3, td.getLanguage());
                                } catch (Exception e) {
                                }
                                copyExcelStyle(wb, 8, 3, 8, (count * 3) + 3, "Qty");
                                copyExcelStyle(wb, 8, 4, 8, (count * 3) + 4, "Cost unit.");
                                copyExcelStyle(wb, 8, 5, 8, (count * 3) + 5, "Total");


                                excelSheet.getRow(12).getCell((count * 3) + 5).setCellFormula(("SUM(" + getCellName((count * 3) + 5) + "11:" + getCellName((count * 3) + 5) + "12)"));
                                excelSheet.getRow(13).getCell((count * 3) + 5).setCellFormula(("SUM(" + getCellName((count * 3) + 5) + "11:" + getCellName((count * 3) + 5) + "12)"));
                                excelSheet.getRow(23).getCell((count * 3) + 5).setCellFormula(("SUM(" + getCellName((count * 3) + 5) + "16:" + getCellName((count * 3) + 5) + "23)"));
                                excelSheet.getRow(27).getCell((count * 3) + 5).setCellFormula(("SUM(" + getCellName((count * 3) + 5) + "26," + getCellName((count * 3) + 5) + "27)"));
                                excelSheet.getRow(29).getCell((count * 3) + 5).setCellFormula(("SUM(" + getCellName((count * 3) + 5) + "28," + getCellName((count * 3) + 5) + "24)"));
                                excelSheet.getRow(33).getCell((count * 3) + 5).setCellFormula(("SUM(" + getCellName((count * 3) + 5) + "14," + getCellName((count * 3) + 5) + "30)"));
                                excelSheet.getRow(39).getCell((count * 3) + 5).setCellFormula(("SUM(" + getCellName((count * 3) + 5) + "34:" + getCellName((count * 3) + 5) + "38)"));
                                excelSheet.getRow(34).getCell((count * 3) + 5).setCellFormula(("PRODUCT(C35," + getCellName((count * 3) + 5) + "34,0.01)"));
//PRODUCT(C35,F34)
                                // f = new Formula(1, 10, buf.toString());


                            } catch (Exception e) {
                            }

                        }

                        double engHr = 0, engPages = 0;
                        double engHrRate = 0, engPagesRate = 0;
                        double engHrTotal = 0, engPagesTotal = 0;
                        List totalLinTasks = new ArrayList();
                        List linTaskList = QuoteService.getInstance().getLinTask(td.getTargetDocId());
                        List engTaskList = QuoteService.getInstance().getEnggTask(td.getTargetDocId());
                        List forTaskList = QuoteService.getInstance().getFormatTask(td.getTargetDocId());
                        List otherTaskList = QuoteService.getInstance().getOtherTask(td.getTargetDocId());



                        if (td.getLinTasks() != null) {
                            tFlag = 1;



                            ////////////////////////////////////////////////////Lin Task

                            for (int ll = 0; ll < linTaskList.size(); ll++) {
                                LinTask t = (LinTask) linTaskList.get(ll);
                                if (t.getTaskName().equalsIgnoreCase("Translation")) {
                                    totalLinTasks.add(t);
                                    if (count == 0) {
                                        try {
                                            printExcel(wb, 1, 16, 3, t.getWordNew());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            printExcel(wb, 1, 17, 3, (t.getWord100() + t.getWordRep()));
                                        } catch (Exception e) {
                                        }
                                        try {
                                            printExcel(wb, 1, 18, 3, t.getWord95());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            printExcel(wb, 1, 19, 3, t.getWord85());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            printExcel(wb, 1, 20, 3, t.getWord75());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            printExcel(wb, 1, 21, 3, t.getWordPerfect());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            printExcel(wb, 1, 22, 3, t.getWordContext());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            printExcel(wb, 1, 16, 4, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScaleNew()));
                                        } catch (Exception e) {
                                        }
                                        try {
                                            printExcel(wb, 1, 17, 4, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScale100()));
                                        } catch (Exception e) {
                                        } 
                                        try {
                                            printExcel(wb, 1, 18, 4, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScale95()));
                                        } catch (Exception e) {
                                        }
                                        try {
                                            printExcel(wb, 1, 19, 4, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScale85()));
                                        } catch (Exception e) {
                                        }
                                        try {
                                            printExcel(wb, 1, 20, 4, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScale75()));
                                        } catch (Exception e) {
                                        }
                                        try {
                                            printExcel(wb, 1, 21, 4, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScalePerfect()));
                                        } catch (Exception e) {
                                        }
                                        try {
                                            printExcel(wb, 1, 22, 4, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScaleContext()));
                                        } catch (Exception e) {
                                        }
                                        try {
                                            printExcel(wb, 1, 16, 5, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScaleNew()) * t.getWordNew());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            printExcel(wb, 1, 17, 5, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScale100()) * (t.getWord100() + t.getWordRep()));
                                        } catch (Exception e) {
                                        }
                                        try {
                                            printExcel(wb, 1, 18, 5, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScale95()) * t.getWord95());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            printExcel(wb, 1, 19, 5, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScale85()) * t.getWord85());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            printExcel(wb, 1, 20, 5, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScale75()) * t.getWord75());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            printExcel(wb, 1, 21, 5, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScalePerfect()) * t.getWordPerfect());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            printExcel(wb, 1, 22, 5, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScaleContext()) * t.getWordContext());
                                        } catch (Exception e) {
                                        }
                                    } else {
                                        try {
                                            copyExcelStyle(wb, 16, 3, 16, (count * 3) + 3, t.getWordNew());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            copyExcelStyle(wb, 17, 3, 17, (count * 3) + 3, (t.getWord100() + t.getWordRep()));
                                        } catch (Exception e) {
                                        }
                                        try {
                                            copyExcelStyle(wb, 18, 3, 18, (count * 3) + 3, t.getWord95());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            copyExcelStyle(wb, 19, 3, 19, (count * 3) + 3, t.getWord85());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            copyExcelStyle(wb, 20, 3, 20, (count * 3) + 3, t.getWord75());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            copyExcelStyle(wb, 21, 3, 21, (count * 3) + 3, t.getWordPerfect());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            copyExcelStyle(wb, 22, 3, 22, (count * 3) + 3, t.getWordContext());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            copyExcelStyle(wb, 16, 4, 16, (count * 3) + 4, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScaleNew()));
                                        } catch (Exception e) {
                                        }
                                        try {
                                            copyExcelStyle(wb, 17, 4, 17, (count * 3) + 4, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScale100()));
                                        } catch (Exception e) {
                                            System.out.println(e.getMessage());
                                        }
                                        try {
                                            copyExcelStyle(wb, 18, 4, 18, (count * 3) + 4, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScale95()));
                                        } catch (Exception e) {
                                        }
                                        try {
                                            copyExcelStyle(wb, 19, 4, 19, (count * 3) + 4, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScale85()));
                                        } catch (Exception e) {
                                        }
                                        try {
                                            copyExcelStyle(wb, 20, 4, 20, (count * 3) + 4, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScale75()));
                                        } catch (Exception e) {
                                            System.out.println(e.getMessage());
                                        }
                                        try {
                                            copyExcelStyle(wb, 21, 4, 21, (count * 3) + 4, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScalePerfect()));
                                        } catch (Exception e) {
                                        }
                                        try {
                                            copyExcelStyle(wb, 22, 4, 22, (count * 3) + 4, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScaleContext()));
                                        } catch (Exception e) {
                                        }
                                        try {
                                            copyExcelStyle(wb, 16, 5, 16, (count * 3) + 5, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScaleNew()) * t.getWordNew());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            copyExcelStyle(wb, 17, 5, 17, (count * 3) + 5, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScale100()) * (t.getWord100() + t.getWordRep()));
                                        } catch (Exception e) {
                                        }
                                        try {
                                            copyExcelStyle(wb, 18, 5, 18, (count * 3) + 5, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScale95()) * t.getWord95());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            copyExcelStyle(wb, 19, 5, 19, (count * 3) + 5, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScale85()) * t.getWord85());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            copyExcelStyle(wb, 20, 5, 20, (count * 3) + 5, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScale75()) * t.getWord75());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            copyExcelStyle(wb, 21, 5, 21, (count * 3) + 5, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScalePerfect()) * t.getWordPerfect());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            copyExcelStyle(wb, 22, 5, 22, (count * 3) + 5, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScaleContext()) * t.getWordContext());
                                        } catch (Exception e) {
                                        }
                                    }
                                } else {
                                    printExcel(wb, 0, 22, 2, "No");
                                }


                            }
                        }

                        ////////////////////////////////////////////////////Engg Task
                        for (int ll = 0; ll < engTaskList.size(); ll++) {
                            //eFlag=1;
                            EngTask lt = (EngTask) engTaskList.get(ll);

                            if (eLang == 0) {
                                printExcel(wb, 0, (31 + eFlag++), 2, lt.getTaskName());
                            }

                            if (lt.getUnits().equalsIgnoreCase("hours")) {
                                try {
                                    engHr += lt.getTotalTeam();
                                } catch (Exception e) {
                                }
                                try {
                                    engHrRate = Double.parseDouble(lt.getRate());
                                } catch (Exception e) {
                                }
                                try {
                                    engHrTotal += lt.getTotalTeam() * engHrRate;
                                } catch (Exception e) {
                                }
                            } else {
                                try {
                                    engPages += lt.getTotalTeam();
                                } catch (Exception e) {
                                }
                                try {
                                    engPagesRate = Double.parseDouble(lt.getRate());
                                } catch (Exception e) {
                                }
                                try {
                                    engPagesTotal += lt.getTotalTeam() * engPagesRate;
                                } catch (Exception e) {
                                }

                            }
                        }
                        printExcel(wb, 1, 10, (count * 3) + 3, engHr);
                        printExcel(wb, 1, 11, (count * 3) + 3, engPages);

                        printExcel(wb, 1, 10, (count * 3) + 4, engHrRate);
                        printExcel(wb, 1, 11, (count * 3) + 4, engPagesRate);

                        printExcel(wb, 1, 10, (count * 3) + 5, engHrTotal);
                        printExcel(wb, 1, 11, (count * 3) + 5, engPagesTotal);

                        ////////////////////////////////////////////////////DTP Task


                        for (int ll = 0; ll < forTaskList.size(); ll++) {



                            DtpTask lt = (DtpTask) forTaskList.get(ll);
                            if (dLang == 0) {
                                printExcel(wb, 0, (25 + dFlag++), 2, lt.getTaskName());
                            }
                            if (lt.getUnits().equalsIgnoreCase("hours")){
//                            if (lt.getTaskName().equalsIgnoreCase("Desktop Publishing") || lt.getTaskName().contains("DTP")) {
                                try {
                                    printExcel(wb, 1, 25, (count * 3) + 3, lt.getTotalTeam());
                                } catch (Exception e) {
                                }
                                try {
                                    printExcel(wb, 1, 25, (count * 3) + 4, lt.getRate());
                                } catch (Exception e) {
                                }
                                try {
                                    printExcel(wb, 1, 25, (count * 3) + 5, Double.parseDouble(lt.getDollarTotal()));
                                } catch (Exception e) {
                                }

//                            } else if (lt.getTaskName().contains("Graphics")) {
                            } else if (lt.getUnits().equalsIgnoreCase("pages")){
                                try {
                                    printExcel(wb, 1, 26, (count * 3) + 3, lt.getTotalTeam());
                                } catch (Exception e) {
                                }
                                try {
                                    printExcel(wb, 1, 26, (count * 3) + 4, lt.getRate());
                                } catch (Exception e) {
                                }
                                try {
                                    printExcel(wb, 1, 26, (count * 3) + 5, Double.parseDouble(lt.getDollarTotal()));
                                } catch (Exception e) {
                                }
                            }
                        }
                        eLang++;
                        dLang++;
                    }
                }
            }
        }
        // for(Iterator iterLinTasks=td.getLinTasks().iterator();iterLinTasks.hasNext();){
        // LinTask lt=(LinTask).iterLinTasks.next();
        // }

        if (tFlag > 0) {
            printExcel(wb, 0, 22, 2, "Yes");
        }
        if (dFlag > 0) {
            printExcel(wb, 0, 24, 2, "Yes");
        }
        if (eFlag > 0) {
            printExcel(wb, 0, 30, 2, "Yes");
        }

        try{excelSheet.getRow(41).getCell(2).setCellFormula(("SUM(F40:CZ40)"));}catch(Exception e){}
        String med = "";
        String detail = "";
        String category = "";
        String[] component;
        for (int i = 0; i < quoteList.size(); i++) {
            cq = (Client_Quote) quoteList.get(i);
            Product prod = ClientService.getSingleProduct(cq.getProduct_ID());
            detail += StandardCode.getInstance().noNull(cq.getType());
            med += StandardCode.getInstance().noNull(cq.getMedical());
            category += StandardCode.getInstance().noNull(prod.getCategory());
            try {
                component = cq.getComponent().split(",");
                for (int comp = 0; comp < component.length; comp++) {
                    if (componentCount < 62) {
                        printExcel(wb, 0, componentCount++, 0, component[comp]);
                    } else {
                        othSrcLang += component[comp];
                    }

                }

                if (sourceCount < 62) {
                    printExcel(wb, 0, sourceCount++, 2, cq.getApplication());
                    printExcel(wb, 0, deliverableCount++, 4, cq.getTarget_application());
                    printExcel(wb, 0, deliverableCount++, 5, cq.getTarget_version());
                } else {
                    othSrcLang += cq.getApplication();
                }



            } catch (Exception e) {
            }

            if (i != quoteList.size() - 1) {
                detail += ",";
                med += ",";
                category += ",";

            }
        }

        Upload_Doc ud1 = QuoteService.getInstance().getUploadDoc(id);
        List pname = QuoteService.getInstance().getUploadDocList(id);
        String uDoc = null;
        if (ud1 != null) {
            for (int i = 0; i < pname.size() - 1; i++) {
                Upload_Doc ud = (Upload_Doc) pname.get(i);
                if (fileCount < 71) {
                    printExcel(wb, 0, fileCount++, 0, ud.getPathname());
                } else {
                    othSrcLang += ud.getPathname();
                }
            }
        }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Page 2



       

        excelSheet.setForceFormulaRecalculation(true);


        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=Quote-" + q.getNumber() + ".xls");

        ServletOutputStream out = response.getOutputStream();
        wb.write(out);
        out.flush();
        out.close();

        return null;
    }

    private void printExcel(HSSFWorkbook wb, int sheet, int row, int column, String data) {
        HSSFSheet excelSheet = wb.getSheetAt(sheet);
        HSSFRow myRow = excelSheet.getRow(row);
        HSSFCell myCell = myRow.getCell(column);
        myCell.setCellValue(data);
    }

    private void printExcel(HSSFWorkbook wb, int sheet, int row, int column, Date data) {
        HSSFSheet excelSheet = wb.getSheetAt(sheet);
        HSSFRow myRow = excelSheet.getRow(row);
        HSSFCell myCell = myRow.getCell(column);
        myCell.setCellValue(data);
    }

    private void copyExcelStyle(HSSFWorkbook wb, int row, int column, int row1, int column1, String data) {
        HSSFSheet excelSheet = wb.getSheetAt(1);
        HSSFRow myRow = excelSheet.getRow(row), myRow1 = excelSheet.getRow(row1);
        HSSFCell myCell = myRow.getCell(column), myCell1 = myRow1.createCell(column1);
        myCell1.setCellStyle(myCell.getCellStyle());
        myCell1.setCellValue(data);
    }

    private void printExcel(HSSFWorkbook wb, int sheet, int row, int column, int data) {
        HSSFSheet excelSheet = wb.getSheetAt(sheet);
        HSSFRow myRow = excelSheet.getRow(row);
        HSSFCell myCell = myRow.getCell(column);
        myCell.setCellValue(data);
    }

    private void copyExcelStyle(HSSFWorkbook wb, int row, int column, int row1, int column1, int data) {
        HSSFSheet excelSheet = wb.getSheetAt(1);
        HSSFRow myRow = excelSheet.getRow(row), myRow1 = excelSheet.getRow(row1);
        HSSFCell myCell = myRow.getCell(column), myCell1 = myRow1.createCell(column1);
        myCell1.setCellStyle(myCell.getCellStyle());
        myCell1.setCellValue(data);
    }

    private void printExcel(HSSFWorkbook wb, int sheet, int row, int column, double data) {
        HSSFSheet excelSheet = wb.getSheetAt(sheet);
        HSSFRow myRow = excelSheet.getRow(row);
        HSSFCell myCell = myRow.getCell(column);
        myCell.setCellValue(data);
    }

    private void copyExcelStyle(HSSFWorkbook wb, int row, int column, int row1, int column1, double data) {
        HSSFSheet excelSheet = wb.getSheetAt(1);
        HSSFRow myRow = excelSheet.getRow(row), myRow1 = excelSheet.getRow(row1);
        HSSFCell myCell = myRow.getCell(column), myCell1 = myRow1.createCell(column1);
        myCell1.setCellStyle(myCell.getCellStyle());
        myCell1.setCellValue(data);
    }

    private void copyExcelStyle(HSSFWorkbook wb, int row, int column, int row1, int column1) {
        HSSFSheet excelSheet = wb.getSheetAt(1);
        HSSFRow myRow = excelSheet.getRow(row), myRow1 = excelSheet.getRow(row1);
        HSSFCell myCell = myRow.getCell(column), myCell1 = myRow1.createCell(column1);
        myCell1.setCellStyle(myCell.getCellStyle());
    }

    private String getCellName(int index) {
        int A = 65;    //ASCII value for capital A
        String sCol;
        int iRemain = 0;
        // THIS ALGORITHM ONLY WORKS UP TO ZZ. It fails on AAA
        if (index > 701) {
            return "";
        }
        if (index <= 25) {
            if (index == 0) {
                sCol = "" + (char) ((A + 25));
            } else {
                sCol = "" + (char) ((A + index));

            }
        } else {
            iRemain = ((index / 26));
            System.out.println("iremain--------------------------->"+index);
            if ((index % 26) == 0) {
                sCol = "" + (char) (A+iRemain-1) + "" + (char) (A+(index % 26));
                System.out.println("sColbbbbb--------------"+sCol);
                System.out.println("sColbbbbb--------------"+sCol);
            } else {
                sCol = "" + (char) (A + iRemain-1) + (char) (A+(index % 26));
                System.out.println("sColccccc--------------"+sCol);
                 System.out.println("sColccccc--------------"+sCol);
            }
        }
        if (sCol.equalsIgnoreCase(" ")) {

//            System.out.println("getCellName(index + 1)----"+index+"----------"+getCellName(index + 1));
            return (getCellName(index + 1));
        }
//        System.out.println("sCol----"+index+"----------"+sCol);
            return sCol;
       

    }
}
