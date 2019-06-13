/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.quote;

import app.client.Client;
import app.client.ClientService;
import app.project.DtpTask;
import app.project.EngTask;
import app.project.LinTask;
import app.project.Project;
import app.project.SourceDoc;
import app.project.TargetDoc;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

/**
 *
 * @author niteshwar
 */
public class QuoteViewGeneralGenerateAbbvieAction  extends Action {

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
        
        Quote1 q = QuoteService.getInstance().getSingleQuote(id);
        Project p = q.getProject();
        Client c = ClientService.getInstance().getClient(p.getCompany().getClientId());
        Client_Quote cq = QuoteService.getInstance().get_SingleClientQuote(id);


        List quoteList = QuoteService.getInstance().getSingleClientQuote(id);

        String filein = "C:/templates/abbvie_excel_template.xls";
//         filein = "/Users/abhisheksingh/Project/templates/abbvie_excel_template.xls";
        //String fileout = "/Users/abhisheksingh/Project/template/lectra_excel.xls";

        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filein));

        HSSFWorkbook wb = new HSSFWorkbook(fs, true);

        HSSFSheet excelSheet = wb.getSheetAt(0);


        printExcel(wb, 0, 1, 8, q.getNumber());
        printExcel(wb, 0, 2, 8, p.getProductDescription());
        printExcel(wb, 0, 3, 8, q.getEnteredByTS());
        if(q.getPmPercent()!=null)
            printExcel(wb, 0, 29, 2, Double.parseDouble(q.getPmPercent()));
        printExcel(wb, 0, 36, 4, c.getCcurrency());




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

                if (sd.getTargetDocs() != null) {
                    
                    for (Iterator iterTarget = sd.getTargetDocs().iterator(); iterTarget.hasNext();) {
                        page3langCol++;
                        TargetDoc td = (TargetDoc) iterTarget.next();
                        List linTaskList = QuoteService.getInstance().getLinTask(td.getTargetDocId());
                        List engTaskList = QuoteService.getInstance().getEnggTask(td.getTargetDocId());
                        List forTaskList = QuoteService.getInstance().getFormatTask(td.getTargetDocId());
                        List otherTaskList = QuoteService.getInstance().getOtherTask(td.getTargetDocId());
                        
                        if(!linTaskList.isEmpty()||!engTaskList.isEmpty()||!forTaskList.isEmpty()){
                        count++;
                        //System.out.println("Count---------------------------->>>>>>"+td.getLanguage());

                        if (count == 0) {
                            printExcel(wb, 0, 6, 3, td.getLanguage());
                            excelSheet.getRow(18).getCell(3).setCellFormula(("SUM(D16:D18)"));
                                
                        } else {
                            try {
                                for (int i = 6; i <= 34; i++) {
                                    copyExcelStyle(wb, i, 3, i, (count * 3) + 3);
                                    copyExcelStyle(wb, i, 4, i, (count * 3) + 4);
                                    copyExcelStyle(wb, i, 5, i, (count * 3) + 5);
                                }
                                try {
                                    copyExcelStyle(wb, 6, 4, 6, (count * 3) + 4, td.getLanguage());
                                } catch (Exception e) {
                                }
                                try {
                                    copyExcelStyle(wb, 6, 5, 6, (count * 3) + 5, td.getLanguage());
                                } catch (Exception e) {
                                }
                                excelSheet.addMergedRegion(new Region(6, (short) ((count * 3) + 3), 6, (short) ((count * 3) + 5)));
//                                excelSheet.addMergedRegion(new Region(7, (short) ((count * 3) + 3), 7, (short) ((count * 3) + 5)));
                                try {
                                    copyExcelStyle(wb, 6, 3, 6, (count * 3) + 3, td.getLanguage());
                                } catch (Exception e) {
                                }
                                copyExcelStyle(wb, 7, 3, 7, (count * 3) + 3, "Qty");
                                copyExcelStyle(wb, 7, 4, 7, (count * 3) + 4, "Cost unit.");
                                copyExcelStyle(wb, 7, 5, 7, (count * 3) + 5, "Total");

                                
                                
                                //PRODUCT(C35,F34)
                                // f = new Formula(1, 10, buf.toString());


                            } catch (Exception e) {
                            }

                        }

                        double engHr = 0, engPages = 0;
                        double engHrRate = 0, engPagesRate = 0;
                        double engHrTotal = 0, engPagesTotal = 0;
                        List totalLinTasks = new ArrayList();
                        



                        if (td.getLinTasks() != null) {
                            tFlag = 1;



                            ////////////////////////////////////////////////////Lin Task
                            excelSheet.getRow(11).getCell((count * 3) + 5).setCellFormula(("SUM(" + getCellName((count * 3) + 5) + "10:" + getCellName((count * 3) + 5) + "11)"));
                                excelSheet.getRow(12).getCell((count * 3) + 5).setCellFormula(("SUM(" + getCellName((count * 3) + 5) + "10:" + getCellName((count * 3) + 5) + "11)"));
                                excelSheet.getRow(18).getCell((count * 3) + 5).setCellFormula(("SUM(" + getCellName((count * 3) + 5) + "16:" + getCellName((count * 3) + 5) + "18)"));
                                excelSheet.getRow(24).getCell((count * 3) + 5).setCellFormula(("SUM(" + getCellName((count * 3) + 5) + "19," + getCellName((count * 3) + 5) + "23)"));
                                excelSheet.getRow(28).getCell((count * 3) + 5).setCellFormula(("SUM(" + getCellName((count * 3) + 5) + "13," + getCellName((count * 3) + 5) + "25)"));
                                excelSheet.getRow(34).getCell((count * 3) + 5).setCellFormula(("SUM(" + getCellName((count * 3) + 5) + "29:" + getCellName((count * 3) + 5) + "33)"));
                                excelSheet.getRow(29).getCell((count * 3) + 5).setCellFormula(("PRODUCT(C30,SUM(" + getCellName((count * 3) + 5)+"33,"+ getCellName((count * 3) + 5) + "29),0.01)"));
                                
                                excelSheet.getRow(18).getCell((count * 3) + 3).setCellFormula(("SUM(" + getCellName((count * 3) + 3) + "16:" + getCellName((count * 3) + 3) + "18)"));
                                
int wdNew=0,wd100=0,wd95=0,wd85=0,wd75=0,wdPerfect=0,wdContext=0;
double csNew=0.00,cs100=0.00,cs95=0.00,cs85=0.00,cs75=0.00,csPerfect=0.00,csContext=0.00,csReps=0.00;
                            for (int ll = 0; ll < linTaskList.size(); ll++) {
                                LinTask t = (LinTask) linTaskList.get(ll);
                                if (t.getTaskName().equalsIgnoreCase("Translation")) {
                                    totalLinTasks.add(t);
                                   
                                    if (count == 0) {
                                        
                                        if(t.getMinFee()>0){
                                        printExcel(wb, 0, 32, 5, t.getMinFee());
                                        }
                                        try {
                                            wdNew +=t.getWordNew();
                                            printExcel(wb, 0, 15, 3, wdNew);
                                        } catch (Exception e) {
                                        }
                                        try {
                                            wd100+=t.getWord95()+t.getWord100() + t.getWordRep() + t.getWordContext() + t.getWordPerfect();
                                            printExcel(wb, 0, 17, 3, wd100);
                                        } catch (Exception e) {
                                        }
                                        try {
                                            wd95+=t.getWord85()+t.getWord75();
                                            printExcel(wb, 0, 16, 3, wd95);
                                        } catch (Exception e) {
                                        }
                                        
                                        
                                        try {
                                            //1.0
                                            printExcel(wb, 0, 15, 4, Double.parseDouble(t.getRate()) * 
                                                    Double.parseDouble(c.getScaleNew(q.getProject().getProjectId(),c.getClientId())));
                                        } catch (Exception e) {
                                        }
//                                        try {
//                                            printExcel(wb, 0, 17, 4, Double.parseDouble(t.getRate()) * 
//                                                    Double.parseDouble(c.getScale100(q.getProject().getProjectId(),c.getClientId())));
//                                        } catch (Exception e) {
//                                        } 
//                                        try {
//                                            printExcel(wb, 0, 16, 4, Double.parseDouble(t.getRate()) * 
//                                                    Double.parseDouble(c.getScale75(q.getProject().getProjectId(),c.getClientId())));
//                                        } catch (Exception e) {
//                                        }
                                         ////////////////////////////
                                        try {
                                            csNew += Double.parseDouble(t.getRate()) * 
                                                    Double.parseDouble(c.getScaleNew(q.getProject().getProjectId(),c.getClientId())) * t.getWordNew();
                                        } catch (Exception e) {
                                        }
                                        try {
                                            cs100+= Double.parseDouble(t.getRate()) * 
                                                    Double.parseDouble(c.getScale100(q.getProject().getProjectId(),c.getClientId())) * (t.getWord100());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            csReps+= Double.parseDouble(t.getRate()) * 
                                                    Double.parseDouble(c.getScaleRep(q.getProject().getProjectId(),c.getClientId())) * (t.getWordRep());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            cs95+= Double.parseDouble(t.getRate()) * 
                                                    Double.parseDouble(c.getScale95(q.getProject().getProjectId(),c.getClientId())) * t.getWord95();
                                        } catch (Exception e) {
                                        }
                                        try {
                                            cs85+= Double.parseDouble(t.getRate()) * 
                                                    Double.parseDouble(c.getScale85(q.getProject().getProjectId(),c.getClientId())) * t.getWord85();
                                        } catch (Exception e) {
                                        }
                                        try {
                                            cs75+= Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScale75(q.getProject().getProjectId(),c.getClientId())) * t.getWord75();
                                        } catch (Exception e) {
                                        }
                                        try {
                                            csPerfect+= Double.parseDouble(t.getRate()) * 
                                                    Double.parseDouble(c.getScalePerfect(q.getProject().getProjectId(),c.getClientId())) * t.getWordPerfect();
                                        } catch (Exception e) {
                                        }
                                        try {
                                            csContext+= Double.parseDouble(t.getRate()) * 
                                                    Double.parseDouble(c.getScaleContext(q.getProject().getProjectId(),c.getClientId())) * t.getWordContext();
                                        } catch (Exception e) {
                                        }
                                        
                                        ///////////////////////////
                                        try {
                                            printExcel(wb, 0, 15, 5, csNew);
                                        } catch (Exception e) {
                                        }
                                        try {
                                            printExcel(wb, 0, 17, 5, cs95+cs100+csPerfect+csContext+csReps);
                                        } catch (Exception e) {
                                        }
                                        try {
                                            printExcel(wb, 0, 16, 5, cs85+cs75);
                                        } catch (Exception e) {
                                        }
                                       
                                    } else {
                                        if(t.getMinFee()>0){
                                            copyExcelStyle(wb, 32, 5, 32, (count * 3) + 5, t.getMinFee());
                                        }
                                        
                                        try {
                                            copyExcelStyle(wb, 15, 3, 15, (count * 3) + 3, t.getWordNew());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            copyExcelStyle(wb, 17, 3, 17, (count * 3) + 3, (t.getWord95()+ t.getWord100() + t.getWordRep()+t.getWordContext()+t.getWordPerfect()));
                                        } catch (Exception e) {
                                        }
                                       
                                        try {
                                            copyExcelStyle(wb, 16, 3, 16, (count * 3) + 3, (t.getWord85()+t.getWord75()));
                                        } catch (Exception e) {
                                        }
                                       
                                        try {
                                            copyExcelStyle(wb, 15, 4, 15, (count * 3) + 4, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScaleNew(q.getProject().getProjectId(),c.getClientId())));
                                        } catch (Exception e) {
                                        }
//                                        try {
//                                            copyExcelStyle(wb, 17, 4, 17, (count * 3) + 4, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScale100(q.getProject().getProjectId(),c.getClientId())));
//                                        } catch (Exception e) {
//                                            //System.out.println(e.getMessage());
//                                        }
//                                        try {
//                                            copyExcelStyle(wb, 16, 4, 16, (count * 3) + 4, Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScale75(q.getProject().getProjectId(),c.getClientId())));
//                                        } catch (Exception e) {
//                                        }
                                        
                                         ////////////////////////////
                                        try {
                                            csNew += Double.parseDouble(t.getRate()) * 
                                                    Double.parseDouble(c.getScaleNew(q.getProject().getProjectId(),c.getClientId())) * t.getWordNew();
                                        } catch (Exception e) {
                                        }
                                        try {
                                            cs100+= Double.parseDouble(t.getRate()) * 
                                                    Double.parseDouble(c.getScale100(q.getProject().getProjectId(),c.getClientId())) * (t.getWord100());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            csReps+= Double.parseDouble(t.getRate()) * 
                                                    Double.parseDouble(c.getScaleRep(q.getProject().getProjectId(),c.getClientId())) * (t.getWordRep());
                                        } catch (Exception e) {
                                        }
                                        try {
                                            cs95+= Double.parseDouble(t.getRate()) * 
                                                    Double.parseDouble(c.getScale95(q.getProject().getProjectId(),c.getClientId())) * t.getWord95();
                                        } catch (Exception e) {
                                        }
                                        try {
                                            cs85+= Double.parseDouble(t.getRate()) * 
                                                    Double.parseDouble(c.getScale85(q.getProject().getProjectId(),c.getClientId())) * t.getWord85();
                                        } catch (Exception e) {
                                        }
                                        try {
                                            cs75+= Double.parseDouble(t.getRate()) * Double.parseDouble(c.getScale75(q.getProject().getProjectId(),c.getClientId())) * t.getWord75();
                                        } catch (Exception e) {
                                        }
                                        try {
                                            csPerfect+= Double.parseDouble(t.getRate()) * 
                                                    Double.parseDouble(c.getScalePerfect(q.getProject().getProjectId(),c.getClientId())) * t.getWordPerfect();
                                        } catch (Exception e) {
                                        }
                                        try {
                                            csContext+= Double.parseDouble(t.getRate()) * 
                                                    Double.parseDouble(c.getScaleContext(q.getProject().getProjectId(),c.getClientId())) * t.getWordContext();
                                        } catch (Exception e) {
                                        }
                                        ///////////////////////////
                                        
                                        //engg
                                 //lin
//                                excelSheet.getRow(15).getCell((count * 3) + 5).setCellFormula(("PRODUCT("+getCellName((count * 3) + 3) + "16," + getCellName((count * 3) + 4) + "16)"));
//                                excelSheet.getRow(16).getCell((count * 3) + 5).setCellFormula(("PRODUCT("+getCellName((count * 3) + 3) + "17," + getCellName((count * 3) + 4) + "17)"));
//                                excelSheet.getRow(17).getCell((count * 3) + 5).setCellFormula(("PRODUCT("+getCellName((count * 3) + 3) + "18," + getCellName((count * 3) + 4) + "18)"));
                                //dtp
                               
                                        try {
                                            copyExcelStyle(wb, 32, 5, 32, (count * 3) + 5, t.getMinFee());
                                            printExcel(wb, 0, 15, (count * 3) + 5, csNew);
                                        } catch (Exception e) {
                                        }
                                        try {
                                            printExcel(wb, 0, 17, (count * 3) + 5, cs95+cs100+csPerfect+csContext+csReps);
                                        } catch (Exception e) {
                                        }
                                        try {
                                            printExcel(wb, 0, 16, (count * 3) + 5, cs85+cs75);
                                        } catch (Exception e) {
                                        }
                                    }
                                } else {
//                                    printExcel(wb, 0, 22, 2, "No");
                                }


                            }
                        }

                        ////////////////////////////////////////////////////Engg Task
                        for (int ll = 0; ll < engTaskList.size(); ll++) {
                            //eFlag=1;
                            EngTask lt = (EngTask) engTaskList.get(ll);

                            if (eLang == 0) {
//                                printExcel(wb, 0, (31 + eFlag++), 2, lt.getTaskName());
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
                        printExcel(wb, 0, 9, (count * 3) + 3, engHr);
                        printExcel(wb, 0, 10, (count * 3) + 3, engPages);

                        printExcel(wb, 0, 9, (count * 3) + 4, engHrRate);
                        printExcel(wb, 0, 10, (count * 3) + 4, engPagesRate);

//                        printExcel(wb, 0, 9, (count * 3) + 5, engHrTotal);
//                        printExcel(wb, 0, 10, (count * 3) + 5, engPagesTotal);

                        ////////////////////////////////////////////////////DTP Task


                        for (int ll = 0; ll < forTaskList.size(); ll++) {



                            DtpTask lt = (DtpTask) forTaskList.get(ll);
                            if (dLang == 0) {
//                                printExcel(wb, 0, (25 + dFlag++), 2, lt.getTaskName());
                            }
                            if (lt.getUnits().equalsIgnoreCase("hours")){
//                            if (lt.getTaskName().equalsIgnoreCase("Desktop Publishing") || lt.getTaskName().contains("DTP")) {
                                if(lt.getTotalTeam()>0){
                                try {
                                    printExcel(wb, 0, 20, (count * 3) + 3, lt.getTotalTeam());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    printExcel(wb, 0, 20, (count * 3) + 4, lt.getRate());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                }
                                
//                                try {
//                                    printExcel(wb, 0, 20, (count * 3) + 5, Double.parseDouble(lt.getDollarTotal()));
//                                } catch (Exception e) {
//                                }

//                            } else if (lt.getTaskName().contains("Graphics")) {
                            } else if (lt.getUnits().equalsIgnoreCase("pages")){
                                if(lt.getTotalTeam()>0){
                                try {
                                    printExcel(wb, 0, 21, (count * 3) + 3, lt.getTotalTeam());
                                } catch (Exception e) {
                                }
                                try {
                                    printExcel(wb, 0, 21, (count * 3) + 4, lt.getRate());
                                } catch (Exception e) {
                                }
                                }
//                                try {
//                                    printExcel(wb, 0, 21, (count * 3) + 5, Double.parseDouble(lt.getDollarTotal()));
//                                } catch (Exception e) {
//                                }
                            }
                        }
                        excelSheet.getRow(22).getCell((count * 3) + 5).setCellFormula(("SUM(" + getCellName((count * 3) + 5) + "21," + getCellName((count * 3) + 5) + "22)"));
                        excelSheet.getRow(20).getCell((count * 3) + 5).setCellFormula((getCellName((count * 3) + 3) + "21*" + getCellName((count * 3) + 4) + "21"));
                        excelSheet.getRow(21).getCell((count * 3) + 5).setCellFormula((getCellName((count * 3) + 3) + "22*" + getCellName((count * 3) + 4) + "22"));

                        
                        excelSheet.getRow(9).getCell((count * 3) + 5).setCellFormula(("PRODUCT("+getCellName((count * 3) + 3) + "10," + getCellName((count * 3) + 4) + "10)"));
                        excelSheet.getRow(10).getCell((count * 3) + 5).setCellFormula(("PRODUCT("+getCellName((count * 3) + 3) + "11," + getCellName((count * 3) + 4) + "11)"));
                               
                        eLang++;
                        dLang++;
                    }}
                }
            }
        }
 

//        try{excelSheet.getRow(34).getCell(2).setCellFormula(("SUM(F40:CZ40)"));}catch(Exception e){}
       

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
        HSSFSheet excelSheet = wb.getSheetAt(0);
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
        HSSFSheet excelSheet = wb.getSheetAt(0);
        HSSFRow myRow = excelSheet.getRow(row), myRow1 = excelSheet.getRow(row1);
        HSSFCell myCell = myRow.getCell(column), myCell1 = myRow1.createCell(column1);
        myCell1.setCellStyle(myCell.getCellStyle());
        myCell1.setCellValue(data);
    }

    private void printExcel(HSSFWorkbook wb, int sheet, int row, int column, double data) {
        HSSFSheet excelSheet = wb.getSheetAt(sheet);
        HSSFRow myRow = excelSheet.getRow(row);
        HSSFCell myCell = myRow.getCell(column);
        try{
        myCell.setCellValue(data);
        }catch(Exception e){}
    }

    private void copyExcelStyle(HSSFWorkbook wb, int row, int column, int row1, int column1, double data) {
        HSSFSheet excelSheet = wb.getSheetAt(0);
        HSSFRow myRow = excelSheet.getRow(row), myRow1 = excelSheet.getRow(row1);
       
        HSSFCell myCell = myRow.getCell(column), myCell1 = myRow1.createCell(column1);
        myCell.getCellType();
        myCell1.setCellStyle(myCell.getCellStyle());
        myCell1.setCellValue(data);
    }

    private void copyExcelStyle(HSSFWorkbook wb, int row, int column, int row1, int column1) {
        try{
        HSSFSheet excelSheet = wb.getSheetAt(0);
        HSSFRow myRow = excelSheet.getRow(row), myRow1 = excelSheet.getRow(row1);
        HSSFCell myCell = myRow.getCell(column), myCell1 = myRow1.createCell(column1);
        myCell1.setCellStyle(myCell.getCellStyle());
        }catch(Exception e){
            e.printStackTrace();
        }

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
            //System.out.println("iremain--------------------------->"+index);
            if ((index % 26) == 0) {
                sCol = "" + (char) (A+iRemain-1) + "" + (char) (A+(index % 26));
                //System.out.println("sColbbbbb--------------"+sCol);
                //System.out.println("sColbbbbb--------------"+sCol);
            } else {
                sCol = "" + (char) (A + iRemain-1) + (char) (A+(index % 26));
                //System.out.println("sColccccc--------------"+sCol);
                 //System.out.println("sColccccc--------------"+sCol);
            }
        }
        if (sCol.equalsIgnoreCase(" ")) {

//            //System.out.println("getCellName(index + 1)----"+index+"----------"+getCellName(index + 1));
            return (getCellName(index + 1));
        }
//        //System.out.println("sCol----"+index+"----------"+sCol);
            return sCol;
       

    }
}
