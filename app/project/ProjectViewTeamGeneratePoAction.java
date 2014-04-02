//ProjectViewTeamGeneratePoAction.java creates a purchase
//order (PDF) for a specific task and binded resource

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

public final class ProjectViewTeamGeneratePoAction extends Action {


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
        
        //get task to create po
        String linId = null;
        String engId = null;
        String dtpId = null;
        String othId = null;
          
        linId = request.getParameter("linId");
        if(linId == null) {
            engId = request.getParameter("engId");
            if(engId == null) {
                dtpId = request.getParameter("dtpId");
                if(dtpId == null) {
                    othId = request.getParameter("othId");
                }
            }            
        }    
        
        //get the next po number for this project
        String poNumber = ProjectService.getInstance().getNewPoNumber(p);
        
        if(linId != null) { //generate lin PO
            LinTask lt = (LinTask) ProjectService.getInstance().getSingleLinTask(Integer.valueOf(linId));
            
            //allow blank po
            Resource r = new Resource();
            if(lt.getPersonName() != null && lt.getPersonName().length() > 0) {
                //get resource
                r = ResourceService.getInstance().getSingleResource(Integer.valueOf(lt.getPersonName()));
                
                //Add default score of "0"for this resource
                lt.setScore(new Integer(0));
                lt.setPoNumber(poNumber);
            }
                        
            ProjectService.getInstance().updateLinTask(lt);
            
            //get user (project manager)
            User u = UserService.getInstance().getSingleUserRealName(StandardCode.getInstance().getFirstName(p.getPm()), StandardCode.getInstance().getLastName(p.getPm()));
            String targetLang = "";
            //START process pdf
            try {
                PdfReader reader = new PdfReader("C:/templates/LI01_thru_LI04.pdf"); //the template
                
                //save the pdf in memory
                ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
                
                //the filled-in pdf
                PdfStamper stamp = new PdfStamper(reader, pdfStream);
                
                //stamp.setEncryption(true, "pass", "pass", PdfWriter.AllowCopy | PdfWriter.AllowPrinting);
                AcroFields form1 = stamp.getAcroFields();

                //set the field values in the pdf form
                if((r.getFirstName() != null && r.getFirstName().length() >= 1) && (r.getLastName() != null && r.getLastName().length() >= 1)) {
                    form1.setField("contractor", StandardCode.getInstance().noNull(r.getFirstName()) + " " + StandardCode.getInstance().noNull(r.getLastName()));
                }
                else {
                    form1.setField("contractor", StandardCode.getInstance().noNull(r.getCompanyName()));
                }
                form1.setField("Project", p.getNumber() + p.getCompany().getCompany_code());
                form1.setField("PO", p.getNumber() + p.getCompany().getCompany_code() + "-PO-" + lt.getPoNumber());
                form1.setField("Currency", lt.getInternalCurrency());
                if(lt.getWordNew4() != null) {
                    form1.setField("Volume_new", String.valueOf(lt.getWordNew4()));
                }
                if(lt.getWord100() != null) {
                    form1.setField("Volume_100", String.valueOf(lt.getWord100()));
                }
                if(lt.getWordRep() != null) {
                    form1.setField("Volume_rep", String.valueOf(lt.getWordRep()));
                }
                if(lt.getWord8599() != null) {
                    form1.setField("Volume_8599", String.valueOf(lt.getWord8599()));
                }
                if(lt.getWordTotal() != null) {
                    form1.setField("Volume_total", String.valueOf(lt.getWordTotal().intValue()));
                }
                //rate and cost
                if(lt.getInternalRate() != null && lt.getInternalRate().length() > 0) {
                    double rateNew = new Double(lt.getInternalRate()).doubleValue();
                    double rate100 = new Double(lt.getInternalRate()).doubleValue() * .25;
                    double rateRep = new Double(lt.getInternalRate()).doubleValue() * .25;
                    double rate8599 = new Double(lt.getInternalRate()).doubleValue() * .5;                
                    //form1.setField("Rate_new",StandardCode.getInstance().formatDouble4(new Double(rateNew)));
                    //form1.setField("Rate_100",StandardCode.getInstance().formatDouble4(new Double(rate100)));
                    //form1.setField("Rate_rep",StandardCode.getInstance().formatDouble4(new Double(rateRep)));
                    //form1.setField("Rate_8599",StandardCode.getInstance().formatDouble4(new Double(rate8599)));
                    form1.setField("Rate_new", String.valueOf(rateNew));
                    form1.setField("Rate_100", String.valueOf(rate100));
                    form1.setField("Rate_rep", String.valueOf(rateRep));
                    form1.setField("Rate_8599", String.valueOf(rate8599));
                    double costNew = lt.getWordNew4().doubleValue() * rateNew;
                    double cost100 = lt.getWord100().doubleValue() * rate100;
                    double costRep = lt.getWordRep().doubleValue() * rateRep;
                    double cost8599 = lt.getWord8599().doubleValue() * rate8599;    
                    /*form1.setField("Cost_new", StandardCode.getInstance().formatDouble(new Double(costNew)));
                    form1.setField("Cost_100", StandardCode.getInstance().formatDouble(new Double(cost100)));
                    form1.setField("Cost_rep", StandardCode.getInstance().formatDouble(new Double(costRep)));
                    form1.setField("Cost_8599", StandardCode.getInstance().formatDouble(new Double(cost8599)));
                    form1.setField("Cost_total", StandardCode.getInstance().formatDouble(new Double(costNew + cost100 + costRep + cost8599)));*/
                    
                    form1.setField("Cost_new", StandardCode.getInstance().formatDouble(new Double(costNew)));
                    form1.setField("Cost_100", StandardCode.getInstance().formatDouble(new Double(cost100)));
                    form1.setField("Cost_rep", StandardCode.getInstance().formatDouble(new Double(costRep)));
                    form1.setField("Cost_8599", StandardCode.getInstance().formatDouble(new Double(cost8599)));
                    double costTotal = costNew + cost100 + costRep + cost8599;
                    form1.setField("Cost_total", StandardCode.getInstance().formatDouble(new Double(costTotal)));
                }
                
                form1.setField("Language", lt.getTargetLanguage());
                targetLang = lt.getTargetLanguage();
                form1.setField("Task1", lt.getTaskName());
                
                if(lt.getDueDateDate() != null) {
                    form1.setField("Deadline", DateFormat.getDateInstance(DateFormat.SHORT).format(lt.getDueDateDate()));
                }
                
                form1.setField("PM", p.getPm());                
                form1.setField("date", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
                form1.setField("Email", u.getWorkEmail1());
                if(u.getWorkPhoneEx() != null && u.getWorkPhoneEx().length() > 0) { //ext present
                    form1.setField("Phone", StandardCode.getInstance().noNull(u.getWorkPhone()) + " ext " + StandardCode.getInstance().noNull(u.getWorkPhoneEx()));
                }
                else { //no ext present
                    form1.setField("Phone", StandardCode.getInstance().noNull(u.getWorkPhone()));
                }
                form1.setField("Fax", StandardCode.getInstance().noNull(u.getLocation().getFax_number()));
                form1.setField("Address", StandardCode.getInstance().printLocation(u.getLocation()));
               
                //START add images
                if(u.getPicture() != null && u.getPicture().length() > 0) {
                    PdfContentByte over;
                    Image img = Image.getInstance("C:/Program Files (x86)/Apache Software Foundation/Tomcat 7.0/webapps/logo/images/" + u.getPicture());
                    img.setAbsolutePosition(200, 200);
                    over = stamp.getOverContent(1);
                    over.addImage(img, 45, 0,0, 45, 300,100);
                }
                //END add images
                
                //stamp.setFormFlattening(true);
                stamp.close();
                
                //write to client (web browser)
                //project number + sequential PO number + abbreviation of target language 
   // Example: 007941therm-002-DE.pdf 
response.setHeader("Content-disposition", "attachment; filename=" + p.getNumber() + p.getCompany().getCompany_code() + "-" + lt.getPoNumber() +"-"+targetLang+ ".pdf");
             //   System.out.println("att:"+p.getNumber() + p.getCompany().getCompany_code() + "-" + lt.getPoNumber() +"-"+targetLang);
//response.setHeader("Content-disposition", "attachment; filename=\"test.pdf\"");

                //response.setHeader("Content-disposition", "attachment; filename=" + p.getNumber() + p.getCompany().getCompany_code() + "-PO-" + lt.getPoNumber() + ".pdf");
                OutputStream os = response.getOutputStream();
                pdfStream.writeTo(os);
                os.flush();
            }                
            catch(Exception e) {
                System.err.println("PDF Exception:" + e.getMessage());
		throw new RuntimeException(e);
            }
            //END process pdf
        }
        if(engId != null) { //generate eng PO
            EngTask et = (EngTask) ProjectService.getInstance().getSingleEngTask(Integer.valueOf(engId));
            
            //allow blank po
            Resource r = new Resource();
            if(et.getPersonName() != null && et.getPersonName().length() > 0) {
                //get resource
                r = ResourceService.getInstance().getSingleResource(Integer.valueOf(et.getPersonName()));
                 //Add default score of "0"for this resource
                et.setScore(new Integer(0));
                et.setPoNumber(poNumber);
            }
            
            ProjectService.getInstance().updateEngTask(et);
            
            //get user (project manager)
            User u = UserService.getInstance().getSingleUserRealName(StandardCode.getInstance().getFirstName(p.getPm()), StandardCode.getInstance().getLastName(p.getPm()));
            
            //START process pdf
            try {
                PdfReader reader = new PdfReader("C:/templates/LI01_thru_LI04.pdf"); //the template
                
                //save the pdf in memory
                ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
                
                //the filled-in pdf
                PdfStamper stamp = new PdfStamper(reader, pdfStream);
                
                //stamp.setEncryption(true, "pass", "pass", PdfWriter.AllowCopy | PdfWriter.AllowPrinting);
                AcroFields form1 = stamp.getAcroFields();

                //set the field values in the pdf form
                if((r.getFirstName() != null && r.getFirstName().length() >= 1) && (r.getLastName() != null && r.getLastName().length() >= 1)) {
                    form1.setField("contractor", StandardCode.getInstance().noNull(r.getFirstName()) + " " + StandardCode.getInstance().noNull(r.getLastName()));
                }
                else {
                    form1.setField("contractor", StandardCode.getInstance().noNull(r.getCompanyName()));
                }
                form1.setField("Project", p.getNumber() + p.getCompany().getCompany_code());
                form1.setField("PO", p.getNumber() + p.getCompany().getCompany_code() + "-PO-" + et.getPoNumber());
                form1.setField("Currency", et.getInternalCurrency());
                if(et.getTotal() != null) {
                    form1.setField("Volume_total", String.valueOf(et.getTotal()));
                }
                
                //rate and cost
                if(et.getInternalRate() != null) {
                    double rateNew = new Double(et.getInternalRate()).doubleValue();
                    form1.setField("Rate_new",String.valueOf(rateNew));
                    double costNew = et.getTotal().doubleValue() * rateNew;
                    form1.setField("Cost_new", String.valueOf(costNew));
                    form1.setField("Cost_total", String.valueOf(costNew));
                }
                String targetLang = et.getTargetLanguage();
                form1.setField("Language", et.getTargetLanguage());
                form1.setField("Task1", et.getTaskName());
                
                if(et.getDueDateDate() != null) {
                    form1.setField("Deadline", DateFormat.getDateInstance(DateFormat.SHORT).format(et.getDueDateDate()));
                }
                
                form1.setField("PM", p.getPm());                
                form1.setField("date", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
                form1.setField("Email", u.getWorkEmail1());
                if(u.getWorkPhoneEx() != null && u.getWorkPhoneEx().length() > 0) { //ext present
                    form1.setField("Phone", StandardCode.getInstance().noNull(u.getWorkPhone()) + " ext " + StandardCode.getInstance().noNull(u.getWorkPhoneEx()));
                }
                else { //no ext present
                    form1.setField("Phone", StandardCode.getInstance().noNull(u.getWorkPhone()));
                }
                form1.setField("Fax", StandardCode.getInstance().noNull(u.getLocation().getFax_number()));
                form1.setField("Address", StandardCode.getInstance().printLocation(u.getLocation()));
               
                //START add images
                if(u.getPicture() != null && u.getPicture().length() > 0) {
                    PdfContentByte over;
                    Image img = Image.getInstance("C:/Program Files (x86)/Apache Software Foundation/Tomcat 7.0/webapps/logo/images/" + u.getPicture());
                    img.setAbsolutePosition(200, 200);
                    over = stamp.getOverContent(1);
                    over.addImage(img, 45, 0,0, 45, 300,100);
                }
                //END add images
                
                //stamp.setFormFlattening(true);
                stamp.close();
                
                //write to client (web browser)
                response.setHeader("Content-disposition", "attachment; filename=" + p.getNumber() + p.getCompany().getCompany_code() + "-" + et.getPoNumber() +"-"+targetLang+ ".pdf");

               // response.setHeader("Content-disposition", "attachment; filename=" + p.getNumber() + p.getCompany().getCompany_code() + "-PO-" + et.getPoNumber() + ".pdf");
                OutputStream os;
                os = response.getOutputStream();
                pdfStream.writeTo(os);
                os.flush();
            }                
            catch(Exception e) {
                System.err.println("PDF Exception:" + e.getMessage());
		throw new RuntimeException(e);
            }
            //END process pdf
        }
        if(dtpId != null) { //generate dtp PO
            DtpTask dt = (DtpTask) ProjectService.getInstance().getSingleDtpTask(Integer.valueOf(dtpId));
            
            //allow blank po
            Resource r = new Resource();
            if(dt.getPersonName() != null && dt.getPersonName().length() > 0) {
                //get resource
                r = ResourceService.getInstance().getSingleResource(Integer.valueOf(dt.getPersonName()));
                 //Add default score of "0"for this resource
                dt.setScore(new Integer(0));
                dt.setPoNumber(poNumber);
            }
            
            ProjectService.getInstance().updateDtpTask(dt);
            
            //get user (project manager)
            User u = UserService.getInstance().getSingleUserRealName(StandardCode.getInstance().getFirstName(p.getPm()), StandardCode.getInstance().getLastName(p.getPm()));
            
            //START process pdf
            try {
                PdfReader reader = new PdfReader("C:/templates/LI01_thru_LI04.pdf"); //the template
                
                //save the pdf in memory
                ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
                
                //the filled-in pdf
                PdfStamper stamp = new PdfStamper(reader, pdfStream);
                
                //stamp.setEncryption(true, "pass", "pass", PdfWriter.AllowCopy | PdfWriter.AllowPrinting);
                AcroFields form1 = stamp.getAcroFields();

                //set the field values in the pdf form
                if((r.getFirstName() != null && r.getFirstName().length() >= 1) && (r.getLastName() != null && r.getLastName().length() >= 1)) {
                    form1.setField("contractor", StandardCode.getInstance().noNull(r.getFirstName()) + " " + StandardCode.getInstance().noNull(r.getLastName()));
                }
                else {
                    form1.setField("contractor", StandardCode.getInstance().noNull(r.getCompanyName()));
                }
                form1.setField("Project", p.getNumber() + p.getCompany().getCompany_code());
                form1.setField("PO", p.getNumber() + p.getCompany().getCompany_code() + "-PO-" + dt.getPoNumber());
                form1.setField("Currency", dt.getInternalCurrency());
                if(dt.getTotal() != null) {
                    form1.setField("Volume_total", String.valueOf(dt.getTotal()));
                }
                
                //rate and cost
                if(dt.getInternalRate() != null) {
                    double rateNew = new Double(dt.getInternalRate()).doubleValue();
                    form1.setField("Rate_new",StandardCode.getInstance().formatDouble3(new Double(rateNew)));
                    double costNew = dt.getTotal().doubleValue() * rateNew;
                    form1.setField("Cost_new", StandardCode.getInstance().formatDouble(new Double(costNew)));
                    form1.setField("Cost_total", StandardCode.getInstance().formatDouble(new Double(costNew)));
                }
                
                form1.setField("Language", dt.getTargetLanguage());
                form1.setField("Task1", dt.getTaskName());
                
                if(dt.getDueDateDate() != null) {
                    form1.setField("Deadline", DateFormat.getDateInstance(DateFormat.SHORT).format(dt.getDueDateDate()));
                }
                
                form1.setField("PM", p.getPm());                
                form1.setField("date", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
                form1.setField("Email", u.getWorkEmail1());
                if(u.getWorkPhoneEx() != null && u.getWorkPhoneEx().length() > 0) { //ext present
                    form1.setField("Phone", StandardCode.getInstance().noNull(u.getWorkPhone()) + " ext " + StandardCode.getInstance().noNull(u.getWorkPhoneEx()));
                }
                else { //no ext present
                    form1.setField("Phone", StandardCode.getInstance().noNull(u.getWorkPhone()));
                }
                form1.setField("Fax", StandardCode.getInstance().noNull(u.getLocation().getFax_number()));
                form1.setField("Address", StandardCode.getInstance().printLocation(u.getLocation()));
               
                //START add images
                if(u.getPicture() != null && u.getPicture().length() > 0) {
                    PdfContentByte over;
                    Image img = Image.getInstance("C:/Program Files (x86)/Apache Software Foundation/Tomcat 7.0/webapps/logo/images/" + u.getPicture());
                    img.setAbsolutePosition(200, 200);
                    over = stamp.getOverContent(1);
                    over.addImage(img, 45, 0,0, 45, 300,100);
                }
                //END add images
                
                //stamp.setFormFlattening(true);
                stamp.close();
                
                //write to client (web browser)
              response.setHeader("Content-disposition", "attachment; filename=" + p.getNumber() + p.getCompany().getCompany_code() + "-" + dt.getPoNumber() +"-"+dt.getTargetLanguage()+ ".pdf");
  
             //  response.setHeader("Content-disposition", "attachment; filename=" + p.getNumber() + p.getCompany().getCompany_code() + "-PO-" + dt.getPoNumber() + ".pdf");
                OutputStream os;
                os = response.getOutputStream();
                pdfStream.writeTo(os);
                os.flush();
            }                
            catch(Exception e) {
                System.err.println("PDF Exception:" + e.getMessage());
		throw new RuntimeException(e);
            }
            //END process pdf
        }
        if(othId != null) { //generate oth PO
            OthTask ot = (OthTask) ProjectService.getInstance().getSingleOthTask(Integer.valueOf(othId));
            
            //allow blank po
            Resource r = new Resource();
            if(ot.getPersonName() != null && ot.getPersonName().length() > 0) {
                //get resource
                r = ResourceService.getInstance().getSingleResource(Integer.valueOf(ot.getPersonName()));
                 //Add default score of "0"for this resource
                ot.setScore(new Integer(0));
                ot.setPoNumber(poNumber);
            }
            
            ProjectService.getInstance().updateOthTask(ot);
            
            //get user (project manager)
            User u = UserService.getInstance().getSingleUserRealName(StandardCode.getInstance().getFirstName(p.getPm()), StandardCode.getInstance().getLastName(p.getPm()));
            
            //START process pdf
            try {
                PdfReader reader = new PdfReader("C:/templates/LI01_thru_LI04.pdf"); //the template
                
                //save the pdf in memory
                ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
                
                //the filled-in pdf
                PdfStamper stamp = new PdfStamper(reader, pdfStream);
                
                //stamp.setEncryption(true, "pass", "pass", PdfWriter.AllowCopy | PdfWriter.AllowPrinting);
                AcroFields form1 = stamp.getAcroFields();

                //set the field values in the pdf form
                if((r.getFirstName() != null && r.getFirstName().length() >= 1) && (r.getLastName() != null && r.getLastName().length() >= 1)) {
                    form1.setField("contractor", StandardCode.getInstance().noNull(r.getFirstName()) + " " + StandardCode.getInstance().noNull(r.getLastName()));
                }
                else {
                    form1.setField("contractor", StandardCode.getInstance().noNull(r.getCompanyName()));
                }
                form1.setField("Project", p.getNumber() + p.getCompany().getCompany_code());
                form1.setField("PO", p.getNumber() + p.getCompany().getCompany_code() + "-PO-" + ot.getPoNumber());
                form1.setField("Currency", ot.getInternalCurrency());
                if(ot.getTotal() != null) {
                    form1.setField("Volume_total", String.valueOf(ot.getTotal()));
                }
                
                //rate and cost
                if(ot.getInternalRate() != null) {
                    double rateNew = new Double(ot.getInternalRate()).doubleValue();
                    form1.setField("Rate_new",StandardCode.getInstance().formatDouble3(new Double(rateNew)));
                    double costNew = ot.getTotal().doubleValue() * rateNew;
                    form1.setField("Cost_new", StandardCode.getInstance().formatDouble(new Double(costNew)));
                    form1.setField("Cost_total", StandardCode.getInstance().formatDouble(new Double(costNew)));
                }
                
                form1.setField("Language", ot.getTargetLanguage());
                form1.setField("Task1", ot.getTaskName());
                
                if(ot.getDueDateDate() != null) {
                    form1.setField("Deadline", DateFormat.getDateInstance(DateFormat.SHORT).format(ot.getDueDateDate()));
                }
                
                form1.setField("PM", p.getPm());                
                form1.setField("date", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
                form1.setField("Email", u.getWorkEmail1());
                if(u.getWorkPhoneEx() != null && u.getWorkPhoneEx().length() > 0) { //ext present
                    form1.setField("Phone", StandardCode.getInstance().noNull(u.getWorkPhone()) + " ext " + StandardCode.getInstance().noNull(u.getWorkPhoneEx()));
                }
                else { //no ext present
                    form1.setField("Phone", StandardCode.getInstance().noNull(u.getWorkPhone()));
                }
                form1.setField("Fax", StandardCode.getInstance().noNull(u.getLocation().getFax_number()));
                form1.setField("Address", StandardCode.getInstance().printLocation(u.getLocation()));
               
                //START add images
                if(u.getPicture() != null && u.getPicture().length() > 0) {
                    PdfContentByte over;
                    Image img = Image.getInstance("C:/Program Files (x86)/Apache Software Foundation/Tomcat 7.0/webapps/logo/images/" + u.getPicture());
                    img.setAbsolutePosition(200, 200);
                    over = stamp.getOverContent(1);
                    over.addImage(img, 45, 0,0, 45, 300,100);
                }
                //END add images
                
                //stamp.setFormFlattening(true);
                stamp.close();
                
                //write to client (web browser)
                //response.setHeader("Content-disposition", "attachment; filename=" + p.getNumber() + p.getCompany().getCompany_code() + "-PO-" + ot.getPoNumber() + ".pdf");
                 response.setHeader("Content-disposition", "attachment; filename=" + p.getNumber() + p.getCompany().getCompany_code() + "-" + ot.getPoNumber() +"-"+ot.getTargetLanguage()+ ".pdf");
  
                OutputStream os;
                os = response.getOutputStream();
                pdfStream.writeTo(os);
                os.flush();
            }                
            catch(Exception e) {
                System.err.println("PDF Exception:" + e.getMessage());
		throw new RuntimeException(e);
            }
            //END process pdf
        }
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
