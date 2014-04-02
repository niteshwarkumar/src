//ProjectViewFormsGen11Action.java creates the
//Client Invoice pdf

package app.project;

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
import app.workspace.*;
import app.security.*;
import app.standardCode.*;

public final class ProjectViewFormsGen11Action extends Action {


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
        
        //get user (project manager)
        User u = UserService.getInstance().getSingleUserRealName(StandardCode.getInstance().getFirstName(p.getPm()), StandardCode.getInstance().getLastName(p.getPm()));
            
            //START process pdf
            try {
                PdfReader reader = new PdfReader("C:/templates/PM03_001.pdf"); //the template
                
                //save the pdf in memory 
                ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
                
                //the filled-in pdf
                PdfStamper stamp = new PdfStamper(reader, pdfStream);
                
                //stamp.setEncryption(true, "pass", "pass", PdfWriter.AllowCopy | PdfWriter.AllowPrinting);
                AcroFields form1 = stamp.getAcroFields();

                //set the field values in the pdf form
                form1.setField("client", p.getCompany().getCompany_name());                
                form1.setField("Project", p.getNumber() + p.getCompany().getCompany_code());
                form1.setField("PO", StandardCode.getInstance().noNull(p.getClientPO()));
                form1.setField("contact", StandardCode.getInstance().noNull(p.getContact().getFirst_name()) + " " + StandardCode.getInstance().noNull(p.getContact().getLast_name()));
                form1.setField("address1", StandardCode.getInstance().noNull(p.getCompany().getAddress_1()));
                form1.setField("address2", StandardCode.getInstance().noNull(p.getCompany().getAddress_2()));
                form1.setField("City", StandardCode.getInstance().noNull(p.getCompany().getCity()));
                form1.setField("State", StandardCode.getInstance().noNull(p.getCompany().getState_prov()));
                form1.setField("ZIP", StandardCode.getInstance().noNull(p.getCompany().getZip_postal_code()));
                form1.setField("Country", StandardCode.getInstance().noNull(p.getCompany().getCountry()));
                form1.setField("Product", StandardCode.getInstance().noNull(p.getProduct()));
                form1.setField("Description", StandardCode.getInstance().noNull(p.getProductDescription()));
                //form1.setField("InvoiceInstr", StandardCode.getInstance().noNull(p.getCompany().getBilling3()));
                
                form1.setField("PM", p.getPm());     
                form1.setField("date", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
                form1.setField("AE", p.getCompany().getSales_rep());
//                if(u.getWorkPhoneEx() != null && u.getWorkPhoneEx().length() > 0) { //ext present
//                    form1.setField("phonepm", StandardCode.getInstance().noNull(u.getWorkPhone()) + " ext " + StandardCode.getInstance().noNull(u.getWorkPhoneEx()));
//                }
//                else { //no ext present
//                    form1.setField("phonepm", StandardCode.getInstance().noNull(u.getWorkPhone()));
//                }
//                form1.setField("faxpm", StandardCode.getInstance().noNull(u.getLocation().getFax_number()));
//                form1.setField("postalpm", StandardCode.getInstance().printLocation(u.getLocation()));
               
//                //START add images
//                if(u.getPicture() != null && u.getPicture().length() > 0) {
//                    PdfContentByte over;
//                    Image img = Image.getInstance("C:/Program Files (x86)/Apache Software Foundation/Tomcat 7.0/webapps/logo/images/" + u.getPicture());
//                    img.setAbsolutePosition(200, 200);
//                    over = stamp.getOverContent(1);
//                    over.addImage(img, 54, 0,0, 65, 47, 493);
//                }
//                //END add images
                
//                form1.setField("project", p.getNumber() + p.getCompany().getCompany_code());
//                form1.setField("description", p.getProjectDescription());
//                form1.setField("additional", p.getProjectRequirements());
                
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
                                if(!td.getLanguage().equals("All"))
                                    targets.append(td.getLanguage() + " ");
                            }
                        }
                    }
                }
                
//                form1.setField("source", sources.toString());
                form1.setField("Language", targets.toString());
//                form1.setField("start", (p.getStartDate() != null) ? DateFormat.getDateInstance(DateFormat.SHORT).format(p.getStartDate()) : "");
//                form1.setField("due", (p.getDueDate() != null) ? DateFormat.getDateInstance(DateFormat.SHORT).format(p.getDueDate()) : "");
                
                //START find billing details (tasks and changes)
                //get this project's sources
                Set sourceList = p.getSourceDocs();
                
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
              
                //team (fee) TOTAL
                double teamTotal = 0;
                if(p.getProjectAmount() != null) {
                    teamTotal = p.getProjectAmount().doubleValue();
                }
                
                form1.setField("volume1", StandardCode.getInstance().formatDouble(linVolume));
                form1.setField("volume2", StandardCode.getInstance().formatDouble(new Double(dtpVolume)));
                form1.setField("volume3", StandardCode.getInstance().formatDouble(new Double(engVolume)));
                form1.setField("volume4", StandardCode.getInstance().formatDouble(new Double(othVolume)));
                
                form1.setField("total linguistic", StandardCode.getInstance().formatDouble(new Double(linTaskTotal)));
                form1.setField("Total DTP", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotal)));
                form1.setField("Total Eng", StandardCode.getInstance().formatDouble(new Double(engTaskTotal)));
                form1.setField("Total Other", StandardCode.getInstance().formatDouble(new Double(othTaskTotal)));
                form1.setField("Total PM", StandardCode.getInstance().noNull(p.getPmPercentDollarTotal()));
                form1.setField("Total", StandardCode.getInstance().formatDouble(new Double(teamTotal)));
                
                //find changes
                double changeTotal = 0;
                int changeCount = 1;
                java.util.Set changes = p.getChange1s();
                if(changes != null && changes.size() > 0) {
                    for(Iterator iter = changes.iterator(); iter.hasNext();) {
                        Change1 c = (Change1) iter.next();
                        if(c.getDollarTotal() != null && c.getDollarTotal().length() > 0) {
                            changeTotal += new Double(c.getDollarTotal()).doubleValue();
                            form1.setField("Change" + String.valueOf(changeCount), StandardCode.getInstance().formatDouble(new Double(c.getDollarTotal())));
                            form1.setField("change" + String.valueOf(changeCount) + "_description", StandardCode.getInstance().noNull(c.getDescription()));
                            changeCount++;
                        }
                    }
                }
                if(changeCount > 1) { //changes were made, so show change total
                    form1.setField("changeTotal", StandardCode.getInstance().formatDouble(new Double(changeTotal)));
                }        
               //END find billing details (tasks and changes)
                
                
                //stamp.setFormFlattening(true);
                stamp.close();
                
                //write to client (web browser)
                
                response.setHeader("Content-disposition", "attachment; filename=" + p.getNumber() + p.getCompany().getCompany_code() + "-ClientInvoice" + ".pdf");
                                
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
