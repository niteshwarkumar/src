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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ProjectViewFormsGen142Action  extends Action {

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
//        Project p = ProjectService.getInstance().getSingleProject(id); 
//        String inputfilepath = "/Users/abhisheksingh/Project/templates/COT1.docx";
             //START process pdf
            try {
            Project p = ProjectService.getInstance().getSingleProject(id);
            String inputfilepath = "/Users/abhisheksingh/Project/templates/COT1.docx";
            inputfilepath = "C:/templates/COT1.docx";
            WordprocessingMLPackage wordMLPackage = Docx4J.load(new java.io.File(inputfilepath));
            java.util.HashMap mappings = new java.util.HashMap();
            VariablePrepare.prepare(wordMLPackage);//see notes
            mappings.put("SOURCE", p.getSrcLang());
                mappings.put("TARGET", p.getTargetLang());
                mappings.put("PROJECTNUMBER", p.getNumber() + p.getCompany().getCompany_code());
                mappings.put("PM", StandardCode.getInstance().noNull(p.getPm()));
                mappings.put("COMPANY", p.getCompany().getCompany_name());
                mappings.put("DATE", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
                mappings.put("COMPANY", StandardCode.getInstance().noNull(p.getCompany().getCompany_name()));
                mappings.put("PRODUCT",StandardCode.getInstance().noNull(p.getProduct()) + " - " + StandardCode.getInstance().noNull(p.getProductDescription()));
//              
            Random rand = new Random();
            wordMLPackage.getMainDocumentPart().variableReplace(mappings);
                //write to client (web browser)
                response.setContentType("application/x-msdownload");  
                response.setHeader("Content-disposition", "attachment; filename=" + p.getNumber() + p.getCompany().getCompany_code() + "-Certification" + ".docx");
                
                OutputStream os = response.getOutputStream();
                wordMLPackage.save(os);
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
    
    public static void main(String[] args) throws Exception{
        try {
            Project p = ProjectService.getInstance().getSingleProject(12677);
            String inputfilepath = "/Users/abhisheksingh/Project/templates/COT1.docx";
            
            WordprocessingMLPackage wordMLPackage = Docx4J.load(new java.io.File(inputfilepath));
            java.util.HashMap mappings = new java.util.HashMap();
            VariablePrepare.prepare(wordMLPackage);//see notes
            mappings.put("SOURCE", p.getSrcLang());
                mappings.put("TARGET", p.getTargetLang());
                mappings.put("PROJECTNUMBER", p.getNumber() + p.getCompany().getCompany_code());
                mappings.put("PM", StandardCode.getInstance().noNull(p.getPm()));
                mappings.put("COMPANY", p.getCompany().getCompany_name());
                mappings.put("DATE", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
                mappings.put("COMPANY", StandardCode.getInstance().noNull(p.getCompany().getCompany_name()));
                mappings.put("PRODUCT",StandardCode.getInstance().noNull(p.getProduct()) + " - " + StandardCode.getInstance().noNull(p.getProductDescription()));
//              
            Random rand = new Random();
            wordMLPackage.getMainDocumentPart().variableReplace(mappings);
            wordMLPackage.save(new File("/Users/abhisheksingh/Project/templates/cot"+rand.nextInt()+".docx"));
        } catch (Exception ex) {
            Logger.getLogger(ProjectViewFormsGen142Action.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
