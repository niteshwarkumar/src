//QuoteViewGeneralGenerateShortAction.java creates the
//Short Quote pdf

package app.quote;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import java.util.*;
import app.project.*;
import app.security.*;
import app.standardCode.*;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.NumberFormats;

public final class QuoteViewGeneralGenerateExcelAction extends Action {


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
	      
        //START get id of current quote from either request, attribute, or cookie 
        //id of quote from request
	String quoteId = null;
	quoteId = request.getParameter("quoteViewId");
        
        //check attribute in request
        if(quoteId == null) {
            quoteId = (String) request.getAttribute("quoteViewId");
        }
        
        //id of quote from cookie
        if(quoteId == null) {            
            quoteId = StandardCode.getInstance().getCookie("quoteViewId", request.getCookies());
        }

        //default client to first if not in request or cookie
        if(quoteId == null) {
                java.util.List results = QuoteService.getInstance().getQuoteList();
                Quote1 first = (Quote1) results.get(0);
                quoteId = String.valueOf(first.getQuote1Id());
            }            
        
        Integer id = Integer.valueOf(quoteId);
        
        //END get id of current quote from either request, attribute, or cookie               
        
        //get quote to edit
        Quote1 q = QuoteService.getInstance().getSingleQuote(id); 
        
           OutputStream out = null;            
           

           WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
           

           
           int counter=1;
           for(Iterator sources = q.getSourceDocs().iterator(); sources.hasNext();) {
                SourceDoc sd = (SourceDoc)sources.next(); 
                WritableSheet detail = w.createSheet(sd.getLanguage(), counter);
                populatePricingSheet(detail,sd, q.getProject());
                counter++;
           }
           WritableSheet summary = w.createSheet("Summary", 0);
           populateSummarySheet(summary,q);

           //Output to the system
           response.setContentType("application/vnd.ms-excel");
           response.setHeader
             ("Content-Disposition", "attachment; filename=" + q.getNumber() + "-"+q.getProject().getCompany().getCompany_name().replaceAll(" ", "_").replaceAll(",", "_")+"_quote.xls");
           
           w.write();
           w.close();
          
          //  out.close();
        
        //write to client (web browser)
       // response.setHeader("Content-Type", "Application/msword");
       // response.setHeader("Content-disposition", "attachment; filename=" + q.getNumber() + "-"+q.getProject().getCompany().getCompany_name()+"_quote.doc");
        //response.setHeader("Content-disposition", "attachment; filename=" + q.getNumber() + ".doc");
       // OutputStream os = response.getOutputStream();
       // os.write(content.getBytes());        
       // os.flush();          
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }
    
 
 public WritableSheet populateSummarySheet(WritableSheet s, Quote1 q ) throws Exception{
 	
    Project p = q.getProject();
            
    //Define field label font
    WritableFont labelFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, true); 
    WritableCellFormat labelFormat = new WritableCellFormat (labelFont); 
    labelFormat.setWrap(true);
    //Define data font
    WritableFont dataFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, true); 
    WritableCellFormat dataFormat = new WritableCellFormat (dataFont); 
    dataFormat.setWrap(true);
    
    
    //s.addCell(new Number(0, 1, 1,labelFormat));
   // s.addCell(new Number(0, 2, 2,labelFormat));
    //Formula formulaSe = new Formula(0, 3,"SUM("+getExcelCellName(0,1)+":"+getExcelCellName(0,2)+")",labelFormat);
   // s.addCell(formulaSe);
    
    s.setColumnView(1,20);
    s.setColumnView(2,40);
    s.setColumnView(3,40);

    s.addCell(new Label(1, 2, "Scope of Work:",labelFormat));
    //s.addCell(new Label(2, 2, "INSERT_PRODUCTNAME_INSERT",dataFormat));
    s.addCell(new Label(2, 2, p.getProduct(),dataFormat));
    
    //////////
    //s.addCell(new Label(1, 3, "",labelFormat));
    //s.addCell(new Label(2, 3, "INSERT_PRODUCTDESCRIPTION_INSERT",dataFormat));
    s.addCell(new Label(2, 3, p.getProductDescription(),dataFormat));
        
//
    s.addCell(new Label(1, 4, "Tasks:",labelFormat));
    
    //Description of Tasks Included in Pricing
                ArrayList tasksIncludedLin = new ArrayList();
                ArrayList tasksIncludedEng = new ArrayList();
                ArrayList tasksIncludedDtp = new ArrayList();
                ArrayList tasksIncludedOth = new ArrayList();
                if(q.getSourceDocs() != null) {
                    for(Iterator iterSource = q.getSourceDocs().iterator(); iterSource.hasNext();) {
                        SourceDoc sd = (SourceDoc) iterSource.next();
                        if(sd.getTargetDocs() != null) {
                            for(Iterator iterTarget = sd.getTargetDocs().iterator(); iterTarget.hasNext();) {
                                TargetDoc td = (TargetDoc) iterTarget.next();
                                
                                if(td.getLinTasks() != null) {
                                    for(Iterator iterLin = td.getLinTasks().iterator(); iterLin.hasNext();) {
                                        LinTask t = (LinTask) iterLin.next();
                                        //check if in list
                                        boolean isIn = false;
                                        for(ListIterator li = tasksIncludedLin.listIterator(); li.hasNext();) {
                                            String displayText = (String) li.next();
                                            if(t.getTaskName().equals("Other")) { //use notes
                                                if(displayText.equals(t.getNotes()) && !isIn) {
                                                    isIn = true;
                                                }
                                            }
                                            else { //use taskName
                                                if(displayText.equals(t.getTaskName()) && !isIn) {
                                                    isIn = true;
                                                }
                                            }
                                        }
                                        if(!isIn) {
                                            if(t.getTaskName().equals("Other")) { //use notes
                                                tasksIncludedLin.add(t.getNotes());
                                            }
                                            else { //use taskName
                                                tasksIncludedLin.add(t.getTaskName());
                                            }
                                        }
                                    }    
                                }//end linTasks
                                
                                if(td.getEngTasks() != null) {
                                    for(Iterator iterEng = td.getEngTasks().iterator(); iterEng.hasNext();) {
                                        EngTask t = (EngTask) iterEng.next();
                                        //check if in list
                                        boolean isIn = false;
                                        for(ListIterator li = tasksIncludedEng.listIterator(); li.hasNext();) {
                                            String displayText = (String) li.next();
                                            if(t.getTaskName().equals("Other")) { //use notes
                                                if(displayText.equals(t.getNotes()) && !isIn) {
                                                    isIn = true;
                                                }
                                            }
                                            else { //use taskName
                                                if(displayText.equals(t.getTaskName()) && !isIn) {
                                                    isIn = true;
                                                }
                                            }
                                        }
                                        if(!isIn) {
                                            if(t.getTaskName().equals("Other")) { //use notes
                                                tasksIncludedEng.add(t.getNotes());
                                            }
                                            else { //use taskName
                                                tasksIncludedEng.add(t.getTaskName());
                                            }
                                        }
                                    }    
                                }//end engTasks
                                
                                if(td.getDtpTasks() != null) {
                                    for(Iterator iterDtp = td.getDtpTasks().iterator(); iterDtp.hasNext();) {
                                        DtpTask t = (DtpTask) iterDtp.next();
                                        //check if in list
                                        boolean isIn = false;
                                        for(ListIterator li = tasksIncludedDtp.listIterator(); li.hasNext();) {
                                            String displayText = (String) li.next();
                                            if(t.getTaskName().equals("Other")) { //use notes
                                                if(displayText.equals(t.getNotes()) && !isIn) {
                                                    isIn = true;
                                                }
                                            }
                                            else { //use taskName
                                                if(displayText.equals(t.getTaskName()) && !isIn) {
                                                    isIn = true;
                                                }
                                            }
                                        }
                                        if(!isIn) {
                                            if(t.getTaskName().equals("Other")) { //use notes
                                                tasksIncludedDtp.add(t.getNotes());
                                            }
                                            else { //use taskName
                                                tasksIncludedDtp.add(t.getTaskName());
                                            }
                                        }
                                    }    
                                }//end dtpTasks
                                
                                if(td.getOthTasks() != null) {
                                    for(Iterator iterOth = td.getOthTasks().iterator(); iterOth.hasNext();) {
                                        OthTask t = (OthTask) iterOth.next();
                                        //check if in list
                                        boolean isIn = false;
                                        for(ListIterator li = tasksIncludedOth.listIterator(); li.hasNext();) {
                                            String displayText = (String) li.next();
                                            if(t.getTaskName().equals("Other")) { //use notes
                                                if(displayText.equals(t.getNotes()) && !isIn) {
                                                    isIn = true;
                                                }
                                            }
                                            else { //use taskName
                                                if(displayText.equals(t.getTaskName()) && !isIn) {
                                                    isIn = true;
                                                }
                                            }
                                        }
                                        if(!isIn) {
                                            if(t.getTaskName().equals("Other")) { //use notes
                                                tasksIncludedOth.add(t.getNotes());
                                            }
                                            else { //use taskName
                                                tasksIncludedOth.add(t.getTaskName());
                                            }
                                        }
                                    }    
                                }//end othTasks
                            }
                        }
                    }
                }
                StringBuffer tasks = new StringBuffer("");
                for(ListIterator iterDisplay = tasksIncludedLin.listIterator(); iterDisplay.hasNext();) {
                    String display = (String) iterDisplay.next();
                    tasks.append(StandardCode.getInstance().noNull(display) + ", ");
                }
                for(ListIterator iterDisplay = tasksIncludedEng.listIterator(); iterDisplay.hasNext();) {
                    String display = (String) iterDisplay.next();
                    tasks.append(StandardCode.getInstance().noNull(display) + ", ");
                }
                for(ListIterator iterDisplay = tasksIncludedDtp.listIterator(); iterDisplay.hasNext();) {
                    String display = (String) iterDisplay.next();
                    tasks.append(StandardCode.getInstance().noNull(display) + ", ");
                }
                for(ListIterator iterDisplay = tasksIncludedOth.listIterator(); iterDisplay.hasNext();) {
                    String display = (String) iterDisplay.next();
                    tasks.append(StandardCode.getInstance().noNull(display) + ", ");
                }
                
    s.addCell(new Label(2, 4, tasks.toString(),dataFormat));
    //Repeat this section as long as there are more Source languages
    //Keep track of index and keep increasing it
    s.addCell(new Label(1, 5, "Languages:",labelFormat));
    //get sources and targets
                String curr = new String("");
                StringBuffer sources = new StringBuffer("");
                StringBuffer targets = new StringBuffer("");
                StringBuffer languages = new StringBuffer("");
                
                if(q.getSourceDocs() != null) {
                    for(Iterator iterSource = q.getSourceDocs().iterator(); iterSource.hasNext();) {
                        SourceDoc sd = (SourceDoc) iterSource.next();
                        sources.append(sd.getLanguage() + " ");
                        languages.append(sd.getLanguage() + " to");
                        if(sd.getTargetDocs() != null) {
                            for(Iterator iterTarget = sd.getTargetDocs().iterator(); iterTarget.hasNext();) {
                                TargetDoc td = (TargetDoc) iterTarget.next();
                                if(!td.getLanguage().equals("All")) {
                                    targets.append(td.getLanguage() + "");
                                    languages.append(" " + td.getLanguage() + ", ");
                                    if((curr.length() == 0) && (td.getLinTasks() != null)) {
                                        for(Iterator iterTasks = td.getLinTasks().iterator(); iterTasks.hasNext();) {
                                            LinTask lt = (LinTask) iterTasks.next();
                                            if(lt.getCurrency() != null && lt.getCurrency().length() > 0) {
                                                curr = lt.getCurrency();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if(languages!=null && languages.toString().endsWith(", ")){
                    languages = new StringBuffer(languages.toString().substring(0, languages.toString().length()-2));
                }
    //Ideally, separate into multiple rows
    //            s.addCell(new Label(2, 5, "INSERT_LANG_SOURCE1_INSERT",dataFormat));
   // s.addCell(new Label(3, 5, "INSERT_LANG_TARGETS_MULTI_INSERT",dataFormat));
    s.addCell(new Label(2, 5, languages.toString(),dataFormat));
    
    //
    s.addCell(new Label(1, 6, "Deliverable:",labelFormat));
    s.addCell(new Label(2, 6, p.getDeliverableOS()+" - " + p.getDeliverableApplication() +" - " + p.getDeliverableVersion(),dataFormat));
    s.addCell(new Label(3, 6, p.getDeliverableTechNotes(), dataFormat));
    //
    s.addCell(new Label(1, 7, "Fee:",labelFormat));
    WritableCellFormat moneyFormat = new WritableCellFormat (NumberFormats.ACCOUNTING_FLOAT);    
    s.addCell(new Number(2, 7, q.getQuoteDollarTotal().doubleValue(),moneyFormat));
   // WritableCellFormat floatFormat = new WritableCellFormat (NumberFormats.ACCOUNTING_FLOAT); 
    //Number number3 = new Number(2, 7, Double.parseDouble(p.getTotalAmountInvoiced()), floatFormat); 
    //s.addCell(number3);
    
        
//
    s.addCell(new Label(1, 8, "Turnaround Time:",labelFormat));
    s.addCell(new Label(2, 8, p.getBeforeWorkTurn()+" " + p.getBeforeWorkTurnUnits(),dataFormat));
    
    //
    
    s.addCell(new Label(1, 12, "Notes:",labelFormat));
    s.addCell(new Label(2, 12, p.getNotes(),labelFormat));
    //
    StringBuffer files = new StringBuffer("");
                if(q.getFiles() != null) {
                    for(Iterator iter = q.getFiles().iterator(); iter.hasNext();) {
                        File f = (File) iter.next();
                        if(f.getBeforeAnalysis().equals("false")) {
                            files.append(StandardCode.getInstance().noNull(f.getFileName() + ", "));
                        }
                    }
                }
    s.addCell(new Label(1, 13, "File List:",labelFormat));
    s.addCell(new Label(2, 13, files.toString(),dataFormat));
    
    return s;
 	
 }
 
 public WritableSheet populatePricingSheet(WritableSheet s, SourceDoc sd, Project p) throws Exception{
 	
    //for each source add each sources' Tasks
     //Define field label font
     //System.out.println("Language="+sd.getLanguage());
    WritableFont labelFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, true); 
    WritableCellFormat labelFormat = new WritableCellFormat (labelFont); 
    labelFormat.setWrap(true);
    //Define data font
    WritableFont dataFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, true); 
    WritableCellFormat dataFormat = new WritableCellFormat (dataFont); 
    dataFormat.setWrap(true);
    
    WritableCellFormat moneyFormat = new WritableCellFormat (NumberFormats.ACCOUNTING_FLOAT); 
    WritableCellFormat intFormat = new WritableCellFormat (NumberFormats.THOUSANDS_INTEGER ); 
    


    //First column
    s.setColumnView(0,15);
    s.addCell(new Label(0, 3, "Source:",labelFormat));
    s.addCell(new Label(0, 5, "Linguistic",labelFormat));
    s.addCell(new Label(0, 12, "DTP",labelFormat));
    s.addCell(new Label(0, 15, "Engineering",labelFormat));
    s.addCell(new Label(0, 19, "PM (5%)",labelFormat));
    s.addCell(new Label(0, 20, "Total",labelFormat));
    //////////
    //Second Column
   
    int startColumn = 1;
    	
    
	    s.setColumnView(startColumn,20);
	    s.addCell(new Label(startColumn, 3, sd.getLanguage(),labelFormat));
	    s.addCell(new Label(startColumn, 5, "100%",labelFormat));
	    s.addCell(new Label(startColumn, 6, "95-99%",labelFormat));
	    s.addCell(new Label(startColumn, 7, "85-94%",labelFormat));
	    s.addCell(new Label(startColumn, 8, "75-84%",labelFormat));
	    s.addCell(new Label(startColumn, 9, "New",labelFormat));
	    s.addCell(new Label(startColumn, 10, "Reps",labelFormat));
	    s.addCell(new Label(startColumn, 11, "Total",labelFormat));
	    s.addCell(new Label(startColumn, 12, p.getSourceApplication(),dataFormat));
	    s.addCell(new Label(startColumn, 13, "Graphics",labelFormat));
	    s.addCell(new Label(startColumn, 14, "Output",labelFormat));
	    s.addCell(new Label(startColumn, 15, "Prep/Analysis",labelFormat));
	    s.addCell(new Label(startColumn, 16, "Compilation",labelFormat));
	    s.addCell(new Label(startColumn, 17, "TM Mngmnt",labelFormat));
	    s.addCell(new Label(startColumn, 18, "Re-sizing",labelFormat));
	 
	    //Loop for every target language
	for(Iterator linTargetIter = sd.getTargetDocs().iterator(); linTargetIter.hasNext();) {
             
            TargetDoc td = (TargetDoc) linTargetIter.next(); 
            
            if(!"All".equals(td.getLanguage())){
	    s.setColumnView(startColumn+1,20);
	    s.addCell(new Label(startColumn+1, 3, "Target: " + td.getLanguage(),labelFormat));
             ////System.out.println("td.getLanguage()="+td.getLanguage());

            //for each lin Task of this target
            int totalWords100 = 0;
            int totalWords9599 = 0;
            int totalWords8594 = 0;
            int totalWords7584 = 0;
            int totalWordsNew = 0;
            int totalWordsRepeat = 0;
            
            double totalWords100Fee = 0;
            double totalWords9599Fee = 0;
            double totalWords8594Fee = 0;
            double totalWords7584Fee = 0;
            double totalWordsNewFee = 0;
            double totalWordsRepeatFee = 0;
           
           double word100Total = 0;
           double word9599Total = 0;
           double word8594Total = 0;
           double word7584Total = 0;
           double wordNewTotal = 0;
           double wordRepeatTotal = 0;
        
       
          
//        double scale100 = new Double(p.getCompany().getScale100()).doubleValue();
//        double scaleRep = new Double(p.getCompany().getScaleRep()).doubleValue();
//        double scale95 = new Double(p.getCompany().getScale95()).doubleValue();
//        double scale85 = new Double(p.getCompany().getScale85()).doubleValue();
//        double scale75 = new Double(p.getCompany().getScale75()).doubleValue();
//        double scaleNew = new Double(p.getCompany().getScaleNew()).doubleValue();
        
          double scale100 = 2.0;
        double scaleRep = 2.0;
        double scale95 = 2.0;
        double scale85 = 2.0;
        double scale75 = 2.0;
        double scaleNew = 2.0;
           
                for(Iterator linTaskIter = td.getLinTasks().iterator(); linTaskIter.hasNext();) {
                 
               
                LinTask lt = (LinTask) linTaskIter.next();
                double rate = 0;
                try {
                    rate = Double.valueOf(lt.getRate()).doubleValue();
                } catch(java.lang.NumberFormatException nfe) {
                    rate = 0;
                }
                                   
//                    totalWords100 += lt.getWord100Fee().intValue();
//                    totalWords9599 += lt.getWord95Fee().intValue();
//                    totalWords8594 += lt.getWord85Fee().intValue();
//                    totalWords7584 += lt.getWord75Fee().intValue();
//                    totalWordsNew += lt.getWordNewFee().intValue();
//                    totalWordsRepeat += lt.getWordRepFee().intValue();
//
//            word100Total += lt.getWord100Fee().intValue()*scale100*rate;
//            word9599Total += lt.getWord95Fee().intValue()*scale95*rate;
//            word8594Total += lt.getWord85Fee().intValue()*scale85*rate;
//            word7584Total += lt.getWord75Fee().intValue()*scale75*rate;
//            wordNewTotal += lt.getWordNewFee().intValue()*scaleNew*rate;
//            wordRepeatTotal += lt.getWordRepFee().intValue()*scaleRep*rate;
                    
                }
           
            
	    s.addCell(new Label(startColumn+1, 4, "Unit",labelFormat));
	    s.addCell(new Number(startColumn+1, 5, totalWords100,intFormat));
	    s.addCell(new Number(startColumn+1, 6,totalWords9599,intFormat));
	    s.addCell(new Number(startColumn+1, 7, totalWords8594,intFormat));
	    s.addCell(new Number(startColumn+1, 8, totalWords7584,intFormat));
	    s.addCell(new Number(startColumn+1, 9, totalWordsNew,intFormat));
	    s.addCell(new Number(startColumn+1, 10, totalWordsRepeat,intFormat));
            Formula formulaSe2 = new Formula(startColumn+1, 11,"SUM("+getExcelCellName(startColumn+1,5)+":"+getExcelCellName(startColumn+1,10)+")",intFormat);
            s.addCell(formulaSe2);
	    //s.addCell(new Label(startColumn+1, 11, "FORMULA FOR ADDING FIELDS FOR UNITS",dataFormat));
	    //dtp
            double totalDtpGraphicsUnits = 0;
            double totalDtpGraphicsFee = 0;
            for(Iterator dtpTaskIter = td.getDtpTasks().iterator(); dtpTaskIter.hasNext();) {
                 
               
                DtpTask dt = (DtpTask) dtpTaskIter.next();
                 if("Graphics Localization".equalsIgnoreCase(dt.getTaskName())){
                    totalDtpGraphicsUnits += dt.getTotal().doubleValue();
                    totalDtpGraphicsFee += new Double(dt.getDollarTotal().replaceAll(",","")).doubleValue();
                }
                
            }
            
            
            s.addCell(new Label(startColumn+1, 12, p.getDeliverableApplication(),dataFormat));            
	    s.addCell(new Number(startColumn+1, 13, totalDtpGraphicsUnits,intFormat));
	    //s.addCell(new Label(startColumn+1, 14, "SPECIAL_OUPUT_SRC",dataFormat));
             s.addCell(new Number(startColumn+1, 14, 0,intFormat));
            //engineering
           
             double totalEngPrepUnits = 0;
             double totalEngPrepFee = 0;
             double totalEngCompUnits = 0;
             double totalEngCompFee = 0;
              double totalEngTMUnits = 0;
             double totalEngTMFee = 0;
              double totalEngResizeUnits = 0;
             double totalEngResizeFee = 0;
             
             for(Iterator engTaskIter = td.getEngTasks().iterator(); engTaskIter.hasNext();) {
                 
               
                EngTask et = (EngTask) engTaskIter.next();
                if(et.getTaskName().indexOf("TM ")>-1){
                    totalEngTMUnits += et.getTotal().doubleValue();
                    totalEngTMFee += new Double(et.getDollarTotal().replaceAll(",","")).doubleValue();
                }else if(et.getTaskName().indexOf("Compilation")>-1){
                    totalEngCompUnits += et.getTotal().doubleValue();
                    totalEngCompFee += new Double(et.getDollarTotal().replaceAll(",","")).doubleValue();
                }else if(et.getTaskName().indexOf("Preparation")>-1){
                    totalEngPrepUnits += et.getTotal().doubleValue();
                totalEngPrepFee += new Double(et.getDollarTotal().replaceAll(",","")).doubleValue();
                }/*else if(et.getTaskName().indexOf("TM ")>-1){
                    totalEngResizeUnits += Double.parseDouble(et.getUnits());
                    totalEngResizeFee += et.getTotal().doubleValue();
                }*/
                
                
            }
	    s.addCell(new Number(startColumn+1, 15, totalEngPrepUnits,intFormat));
	    s.addCell(new Number(startColumn+1, 16, totalEngCompUnits,intFormat));
	    s.addCell(new Number(startColumn+1, 17, totalEngTMUnits,intFormat));
	   // s.addCell(new Label(startColumn+1, 18, "RESIZE_SRC",dataFormat));
            s.addCell(new Number(startColumn+1, 18, 0,intFormat));
	//second column    
	    s.setColumnView(startColumn+2,20);
            
	    s.addCell(new Label(startColumn+2, 4, "Fee",labelFormat));
	    s.addCell(new Number(startColumn+2, 5, word100Total,moneyFormat));
	    s.addCell(new Number(startColumn+2, 6, word9599Total,moneyFormat));
	    s.addCell(new Number(startColumn+2, 7, word8594Total,moneyFormat));
	    s.addCell(new Number(startColumn+2, 8, word7584Total,moneyFormat));
	    s.addCell(new Number(startColumn+2, 9, wordNewTotal,moneyFormat));
	    s.addCell(new Number(startColumn+2, 10,wordRepeatTotal,moneyFormat));
	    Formula formulaSe = new Formula(startColumn+2, 11,"SUM("+getExcelCellName(startColumn+2,5)+":"+getExcelCellName(startColumn+2,10)+")",moneyFormat);
            s.addCell(formulaSe);
            
          //  s.addCell(new Label(startColumn+2, 11, "FORMULA FOR ADDING FIELDS FOR FEE",dataFormat));
            
            
	    s.addCell(new Label(startColumn+2, 12,  "",dataFormat));
	    s.addCell(new Number(startColumn+2, 13,totalDtpGraphicsFee,moneyFormat));
	    //s.addCell(new Label(startColumn+2, 14, "FEE_SPECIAL_OUPUT_SRC",dataFormat));
            s.addCell(new Number(startColumn+2, 14, 0,moneyFormat));
            //eng
	    s.addCell(new Number(startColumn+2, 15, totalEngPrepFee,moneyFormat));
	    s.addCell(new Number(startColumn+2, 16, totalEngCompFee,moneyFormat));
	    s.addCell(new Number(startColumn+2, 17, totalEngTMFee,moneyFormat));
	    //s.addCell(new Label(startColumn+2, 18, "FEE_RESIZE_SRC",dataFormat));
            s.addCell(new Number(startColumn+2, 18, 0,moneyFormat));
	    //s.addCell(new Label(startColumn+2, 19, "FORMULA: (SUM OF FEES * 0.05)",dataFormat));
            Formula formulaSe3 = new Formula(startColumn+2, 19,"0.05*SUM("+getExcelCellName(startColumn+2,11)+":"+getExcelCellName(startColumn+2,18)+")",moneyFormat);
            s.addCell(formulaSe3);
            
	    s.addCell(new Label(startColumn+2, 20, "FORMULA: (SUM OF FEES + PM FEE)",dataFormat));
            Formula formulaSe4 = new Formula(startColumn+2, 20,"1.05*SUM("+getExcelCellName(startColumn+2,11)+":"+getExcelCellName(startColumn+2,18)+")",moneyFormat);
            s.addCell(formulaSe4);
            
            startColumn+=2;
        }
     }
    
    
    return s;
 	
 }
 
	public static final String cellNames = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
 	public String getExcelCellName(int col, int row){
 		return cellNames.substring(col,col+1) + (row+1);
 		
 		
 	}
 
        
        
}

