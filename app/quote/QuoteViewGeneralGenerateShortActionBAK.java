//QuoteViewGeneralGenerateShortAction.java creates the
//Short Quote pdf

package app.quote;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleException;
import org.apache.struts.util.MessageResources;
import org.apache.commons.beanutils.PropertyUtils;
import java.util.*;
import java.text.*;
import java.io.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import app.user.*;
import app.resource.*;
import app.db.*;
import app.project.*;
import app.workspace.*;
import app.security.*;
import app.standardCode.*;

public final class QuoteViewGeneralGenerateShortActionBAK extends Action {


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
        
        //START process pdf
            try {
                PdfReader reader = new PdfReader("C:/templates/CL03_001.pdf"); //the template
                
                //save the pdf in memory
                ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
                
                //the filled-in pdf
                PdfStamper stamp = new PdfStamper(reader, pdfStream);
                
                //stamp.setEncryption(true, "pass", "pass", PdfWriter.AllowCopy | PdfWriter.AllowPrinting);
                AcroFields form1 = stamp.getAcroFields();
                
                //get sources and targets
                String curr = new String("");
                StringBuffer sources = new StringBuffer("");
                StringBuffer targets = new StringBuffer("");
                if(q.getSourceDocs() != null) {
                    for(Iterator iterSource = q.getSourceDocs().iterator(); iterSource.hasNext();) {
                        SourceDoc sd = (SourceDoc) iterSource.next();
                        sources.append(sd.getLanguage() + " ");
                        if(sd.getTargetDocs() != null) {
                            for(Iterator iterTarget = sd.getTargetDocs().iterator(); iterTarget.hasNext();) {
                                TargetDoc td = (TargetDoc) iterTarget.next();
                                if(!td.getLanguage().equals("All")) {
                                    targets.append(td.getLanguage() + " ");
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
                
                form1.setField("date", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
                form1.setField("company", q.getProject().getCompany().getCompany_name()); 
                form1.setField("contact", StandardCode.getInstance().noNull(q.getProject().getContact().getFirst_name()) + " " + StandardCode.getInstance().noNull(q.getProject().getContact().getLast_name())); 
                form1.setField("title", StandardCode.getInstance().noNull(q.getProject().getContact().getTitle())); 
                form1.setField("address1", StandardCode.getInstance().noNull(q.getProject().getCompany().getAddress_1())); 
                form1.setField("address2", StandardCode.getInstance().noNull(q.getProject().getCompany().getAddress_2())); 
                form1.setField("city", StandardCode.getInstance().noNull(q.getProject().getCompany().getCity())); 
                form1.setField("state", StandardCode.getInstance().noNull(q.getProject().getCompany().getState_prov())); 
                form1.setField("zip", StandardCode.getInstance().noNull(q.getProject().getCompany().getZip_postal_code())); 
                if(q.getProject().getContact().getWorkPhoneEx() != null && q.getProject().getContact().getWorkPhoneEx().length() > 0) { //ext present
                    form1.setField("phone", StandardCode.getInstance().noNull(q.getProject().getContact().getTelephone_number()) + " ext " + StandardCode.getInstance().noNull(q.getProject().getContact().getWorkPhoneEx()));
                }
                else { //no ext present
                    form1.setField("phone", StandardCode.getInstance().noNull(q.getProject().getContact().getTelephone_number()));
                }
                form1.setField("fax", StandardCode.getInstance().noNull(q.getProject().getCompany().getFax_number())); 
                form1.setField("email", StandardCode.getInstance().noNull(q.getProject().getContact().getEmail_address())); 
                
                form1.setField("product name", StandardCode.getInstance().noNull(q.getProject().getProduct()));
                
                StringBuffer files = new StringBuffer("");
                if(q.getFiles() != null) {
                    for(Iterator iter = q.getFiles().iterator(); iter.hasNext();) {
                        File f = (File) iter.next();
                        if(f.getBeforeAnalysis().equals("true")) {
                            files.append(StandardCode.getInstance().noNull(f.getFileName() + " "));
                        }
                    }
                }
                form1.setField("file names", StandardCode.getInstance().noNull(files.toString()));
                
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
                int count = 1;
                for(ListIterator iterDisplay = tasksIncludedLin.listIterator(); iterDisplay.hasNext(); count++) {
                    String display = (String) iterDisplay.next();
                    form1.setField("task" + String.valueOf(count), StandardCode.getInstance().noNull(display));
                }
                for(ListIterator iterDisplay = tasksIncludedEng.listIterator(); iterDisplay.hasNext(); count++) {
                    String display = (String) iterDisplay.next();
                    form1.setField("task" + String.valueOf(count), StandardCode.getInstance().noNull(display));
                }
                for(ListIterator iterDisplay = tasksIncludedDtp.listIterator(); iterDisplay.hasNext(); count++) {
                    String display = (String) iterDisplay.next();
                    form1.setField("task" + String.valueOf(count), StandardCode.getInstance().noNull(display));
                }
                for(ListIterator iterDisplay = tasksIncludedOth.listIterator(); iterDisplay.hasNext(); count++) {
                    String display = (String) iterDisplay.next();
                    form1.setField("task" + String.valueOf(count), StandardCode.getInstance().noNull(display));
                }
                
                form1.setField("source", StandardCode.getInstance().noNull(sources.toString()));
                form1.setField("target", StandardCode.getInstance().noNull(targets.toString()));
                
                form1.setField("curr", StandardCode.getInstance().noNull(curr));
                
                //START find billing details (tasks and changes)
                //get this project's sources
                Set sourceList = q.getSourceDocs();
                
                //for each source add each sources' Tasks
                java.util.List totalLinTasks = new java.util.ArrayList();
                java.util.List totalEngTasks = new java.util.ArrayList();
                java.util.List totalDtpTasks = new java.util.ArrayList();
                java.util.List totalOthTasks = new java.util.ArrayList();

                //for each source
                for(Iterator sourceIter = sourceList.iterator(); sourceIter.hasNext();) {
                    SourceDoc sd = (SourceDoc) sourceIter.next();

                    //for each target of this source
                    for(Iterator linTargetIter = sd.getTargetDocs().iterator(); linTargetIter.hasNext();) {
                        TargetDoc td = (TargetDoc) linTargetIter.next();

                        //for each lin Task of this target
                        for(Iterator linTaskIter = td.getLinTasks().iterator(); linTaskIter.hasNext();) {
                            LinTask lt = (LinTask) linTaskIter.next();
                            totalLinTasks.add(lt);
                        }

                        //for each eng Task of this target
                        for(Iterator engTaskIter = td.getEngTasks().iterator(); engTaskIter.hasNext();) {
                            EngTask et = (EngTask) engTaskIter.next();
                            totalEngTasks.add(et);
                        }

                        //for each dtp Task of this target
                        for(Iterator dtpTaskIter = td.getDtpTasks().iterator(); dtpTaskIter.hasNext();) {
                            DtpTask dt = (DtpTask) dtpTaskIter.next();
                            totalDtpTasks.add(dt);
                        }

                        //for each oth Task of this target
                        for(Iterator othTaskIter = td.getOthTasks().iterator(); othTaskIter.hasNext();) {
                            OthTask ot = (OthTask) othTaskIter.next();
                            totalOthTasks.add(ot);
                        }             
                    }   
                }       

                //array for display in jsp
                LinTask[] linTasksArray = (LinTask[]) totalLinTasks.toArray(new LinTask[0]);
                EngTask[] engTasksArray = (EngTask[]) totalEngTasks.toArray(new EngTask[0]);
                DtpTask[] dtpTasksArray = (DtpTask[]) totalDtpTasks.toArray(new DtpTask[0]);
                OthTask[] othTasksArray = (OthTask[]) totalOthTasks.toArray(new OthTask[0]);
        
                //find total of LinTasks
                double linTaskTotal = 0;
                boolean gotFirstLin = false;
                Double linVolume = new Double(0.0);
                for(int i = 0; i < linTasksArray.length; i++) {
                    if(!gotFirstLin && linTasksArray[i].getWordTotal() != null) {
                        linVolume = linTasksArray[i].getWordTotal();
                        gotFirstLin = true;
                    }
                    if(linTasksArray[i].getDollarTotal() != null) {
                        //remove comma's
                        String linTotal = linTasksArray[i].getDollarTotal();
                        linTotal = linTotal.replaceAll(",","");
                        Double total = Double.valueOf(linTotal);
                        linTaskTotal += total.doubleValue();
                    }
                }     

                //find total of EngTasks
                double engTaskTotal = 0;
                double engVolume = 0.0;
                for(int i = 0; i < engTasksArray.length; i++) {
                    if(engTasksArray[i].getTotal() != null) {
                        engVolume += engTasksArray[i].getTotal().doubleValue();
                    }
                    if(engTasksArray[i].getDollarTotal() != null) {
                        //remove comma's
                        String engTotal = engTasksArray[i].getDollarTotal();
                        engTotal = engTotal.replaceAll(",","");
                        Double total = Double.valueOf(engTotal);
                        engTaskTotal += total.doubleValue();
                    }
                }    

                //find total of DtpTasks
                double dtpTaskTotal = 0;
                double dtpVolume = 0.0;
                for(int i = 0; i < dtpTasksArray.length; i++) {
                    if(dtpTasksArray[i].getTotal() != null) {
                        dtpVolume += dtpTasksArray[i].getTotal().doubleValue();
                    }
                    if(dtpTasksArray[i].getDollarTotal() != null) {
                        //remove comma's
                        String dtpTotal = dtpTasksArray[i].getDollarTotal();
                        dtpTotal = dtpTotal.replaceAll(",","");
                        Double total = Double.valueOf(dtpTotal);
                        dtpTaskTotal += total.doubleValue();
                    }
                }    

                //find total of OthTasks
                double othTaskTotal = 0;
                double othVolume = 0.0;
                for(int i = 0; i < othTasksArray.length; i++) {
                    if(othTasksArray[i].getTotal() != null) {
                        othVolume += othTasksArray[i].getTotal().doubleValue();
                    }
                    if(othTasksArray[i].getDollarTotal() != null) {
                        //remove comma's
                        String othTotal = othTasksArray[i].getDollarTotal();
                        othTotal = othTotal.replaceAll(",","");
                        Double total = Double.valueOf(othTotal);
                        othTaskTotal += total.doubleValue();
                    }
                }    
                
                //quote TOTAL
                double quoteTotal = 0;
                if(q.getQuoteDollarTotal() != null) {
                    quoteTotal = q.getQuoteDollarTotal().doubleValue();
                }
                
                //find pm total
                double pmTotal = 0;
                String strPmSubTotal = q.getSubDollarTotal().replaceAll(",", "");
                if(q.getQuoteDollarTotal() != null) {
                    pmTotal = q.getQuoteDollarTotal().doubleValue() - (new Double(strPmSubTotal)).doubleValue();
                }
                form1.setField("ling", StandardCode.getInstance().formatDouble(new Double(linTaskTotal)));
                form1.setField("dtp", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotal)));
                form1.setField("eng", StandardCode.getInstance().formatDouble(new Double(engTaskTotal)));
                form1.setField("pm", StandardCode.getInstance().formatDouble(new Double(pmTotal)));
                form1.setField("other", StandardCode.getInstance().formatDouble(new Double(othTaskTotal)));
                form1.setField("total", StandardCode.getInstance().formatDouble(new Double(quoteTotal)));
                                
                form1.setField("quote number", StandardCode.getInstance().noNull(q.getNumber()));
                form1.setField("preparedby", StandardCode.getInstance().noNull(q.getProject().getPm()));
                
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
                
                //write to client (web browser)
                response.setHeader("Content-disposition", "attachment; filename=" + q.getNumber() + ".pdf");
                OutputStream os = response.getOutputStream();
                pdfStream.writeTo(os);
                os.flush();
            }                
            catch(Exception e) {
                System.err.println("PDF Exception:" + e.getMessage());
		throw new RuntimeException(e);
            }
            //END process pdf        
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
