//ProjectViewFormsGen13Action.java creates the
//Translation Approval pdf

package app.project;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import java.util.*;
import java.text.*;
import java.io.*;
import com.lowagie.text.pdf.*;
import app.security.*;
import app.standardCode.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class ProjectViewFormsGen14Action extends Action {

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
        
             //START process pdf
            try {
                PdfReader reader = new PdfReader("C:/templates/ISO03_004.pdf"); //the template
                
                //save the pdf in memory
                ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
                
                //the filled-in pdf
                PdfStamper stamp = new PdfStamper(reader, pdfStream);
                
                //stamp.setEncryption(true, "pass", "pass", PdfWriter.AllowCopy | PdfWriter.AllowPrinting);
                AcroFields form1 = stamp.getAcroFields();

                //get sources and targets
                StringBuffer sources = new StringBuffer("");
                StringBuffer targets = new StringBuffer("");
                if(p.getSourceDocs() != null) {
                    for(Iterator iterSource = p.getSourceDocs().iterator(); iterSource.hasNext();) {
                        SourceDoc sd = (SourceDoc) iterSource.next();
                        sources.append(sd.getLanguage() + ", ");
                        if(sd.getTargetDocs() != null) {
                            for(Iterator iterTarget = sd.getTargetDocs().iterator(); iterTarget.hasNext();) {
                                TargetDoc td = (TargetDoc) iterTarget.next();
                                if(!td.getLanguage().equals("All"))
                                    targets.append(td.getLanguage() + ", ");
                            }
                        }
                    }
                }
                form1.setField("source", sources.toString().substring(0, sources.length()-2));
                form1.setField("languages", targets.toString().substring(0, targets.length()-2));
                //set the field values in the pdf form
//                form1.setField("languages", targets.toString());
                form1.setField("projectnr", p.getNumber() + p.getCompany().getCompany_code());
                form1.setField("Text3", StandardCode.getInstance().noNull(p.getPm()));
                form1.setField("Text2", p.getCompany().getCompany_name());
                form1.setField("project", p.getNumber() + p.getCompany().getCompany_code());
                form1.setField("Text4", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
                form1.setField("company", StandardCode.getInstance().noNull(p.getCompany().getCompany_name()));
                form1.setField("description",StandardCode.getInstance().noNull(p.getProduct()) + " - " + StandardCode.getInstance().noNull(p.getProductDescription()));
//                form1.setField("source", sources.toString());
                form1.setField("pm", StandardCode.getInstance().noNull(p.getPm()));
                form1.setField("date", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
                
//                //START add images





//                if(u.getPicture() != null && u.getPicture().length() > 0) {
//                    PdfContentByte over;
//                    Image img = Image.getInstance("C:/Program Files/Apache Software Foundation/Tomcat 5.5/webapps/logo/images/" + u.getPicture());
//                    img.setAbsolutePosition(200, 200);
//                    over = stamp.getOverContent(1);
//                    over.addImage(img, 45, 0,0, 45, 300,100);
//                }
//                //END add images
                
                //stamp.setFormFlattening(true);
                stamp.close();
                
                //write to client (web browser)
                response.setHeader("Content-disposition", "attachment; filename=" + p.getNumber() + p.getCompany().getCompany_code() + "-Certification" + ".pdf");
                
                OutputStream os = response.getOutputStream();
                pdfStream.writeTo(os);
                os.flush();
            }                
            catch(Exception e) {
                System.err.println("PDF Exception:" + e.getMessage());
		throw new RuntimeException(e);
            }
            //END process pdf        
        p.setTranslationApprovalConfirmation("on");
        ProjectService.getInstance().updateProject(p);
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
