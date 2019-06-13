//ProjectViewFormsGen1Action.java creates the
//Translator Checklist pdf

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
import java.io.*;
import com.lowagie.text.pdf.*;
import app.resource.*;
import app.security.*;
import app.standardCode.*;

public final class ProjectViewFormsGen1Action extends Action {


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
        
        //get resource to create form
        String linId = request.getParameter("linId");
         //System.out.println("linguitic id>>>>>>>>>>>>>>>>>>>>>>>>>"+linId);
        //LinTask lt = ProjectService.getInstance().getSingleLinTask(Integer.valueOf(linId));
        //Resource r = ResourceService.getInstance().getSingleResource(Integer.valueOf(lt.getPersonName()));
          //System.out.println("linguitic id>>>>>>>>>>>>>>>>>>>>>>>>>"+linId);
        Resource r=null;
        LinTask lt =null;

        if(request.getParameter("linId") != null) {
            r = ResourceService.getInstance().getSingleResource(Integer.valueOf(StandardCode.getInstance().noNull(linId)));
        }
        else {
             lt = ProjectService.getInstance().getSingleLinTask(Integer.valueOf(linId));
            //System.out.println(" else stmt. lin task>>>>>>>>>>>>>"+lt);
            r = ResourceService.getInstance().getSingleResource(Integer.valueOf(StandardCode.getInstance().noNull(lt.getPersonName())));
            //System.out.println("resources name>>>>>>>>>>>>>>>>>>>>>>>>>>>"+r);
        }
        

        
            //START process pdf
            try {
                PdfReader reader = new PdfReader("C:/templates/LI01_001.pdf"); //the template
                
                //save the pdf in memory
                ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
                
                //the filled-in pdf
                PdfStamper stamp = new PdfStamper(reader, pdfStream);
                
                //stamp.setEncryption(true, "pass", "pass", PdfWriter.AllowCopy | PdfWriter.AllowPrinting);
                AcroFields form1 = stamp.getAcroFields();
                if(r!=null){
                //set the field values in the pdf form
                if((r.getFirstName().length() >= 1 && r.getFirstName() != null) && (r.getLastName().length() >= 1 && r.getLastName() != null)) {
                    form1.setField("contractor", StandardCode.getInstance().noNull(r.getFirstName()) + " " + StandardCode.getInstance().noNull(r.getLastName()));
                }
                else {
                    form1.setField("contractor", StandardCode.getInstance().noNull(r.getCompanyName()));
                }}
                form1.setField("Project", p.getNumber() + p.getCompany().getCompany_code());
                form1.setField("PO", p.getNumber() + p.getCompany().getCompany_code() );
               
//                //START add images
//                if(u.getPicture() != null && u.getPicture().length() > 0) {
//                    PdfContentByte over;
//                    Image img = Image.getInstance("C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/logo/images/" + u.getPicture());
//                    img.setAbsolutePosition(200, 200);
//                    over = stamp.getOverContent(1);
//                    over.addImage(img, 45, 0,0, 45, 300,100);
//                }
//                //END add images
                
                //stamp.setFormFlattening(true);
                stamp.close();
                
                //write to client (web browser)
                response.setHeader("Content-disposition", "attachment; filename=" + p.getNumber() + p.getCompany().getCompany_code() +  "-TranslatorChecklist" + ".pdf");
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
