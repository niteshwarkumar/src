//ProjectViewFormsGenChangeAction.java creates the
//change order pdf

package app.project;

import app.extjs.global.LanguageAbs;
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
import app.user.*;
import app.security.*;
import app.standardCode.*;

public final class ProjectViewFormsGenChangeAction extends Action {


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
        
        //get change1 object and project
        Project p = ProjectService.getInstance().getSingleProject(id);
        String change = request.getParameter("change");
        change = StandardCode.getInstance().noNull(change);
        //get user (project manager)
        User u = UserService.getInstance().getSingleUserRealName(StandardCode.getInstance().getFirstName(p.getPm()), StandardCode.getInstance().getLastName(p.getPm()));
         
            //START process pdf
            try {
                //PdfReader reader = new PdfReader("C:/templates/CL03_001.pdf"); //OLD template
                PdfReader reader = new PdfReader("C:/templates/CL06_001.pdf"); //ALEX: Current  template
                
                //save the pdf in memory
                ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
                
                //the filled-in pdf
                PdfStamper stamp = new PdfStamper(reader, pdfStream);
                
                //stamp.setEncryption(true, "pass", "pass", PdfWriter.AllowCopy | PdfWriter.AllowPrinting);
                AcroFields form1 = stamp.getAcroFields();

                form1.setField("firstname", p.getContact().getFirst_name());                
                form1.setField("emailpm", u.getWorkEmail1());                
                if(u.getWorkPhoneEx() != null && u.getWorkPhoneEx().length() > 0) { //ext present
                    form1.setField("phonepm", StandardCode.getInstance().noNull(u.getWorkPhone()) + " ext " + StandardCode.getInstance().noNull(u.getWorkPhoneEx()));
                }
                else { //no ext present
                    form1.setField("phonepm", StandardCode.getInstance().noNull(u.getWorkPhone()));
                }
                form1.setField("faxpm", StandardCode.getInstance().noNull(u.getLocation().getFax_number()));
                form1.setField("postalpm", StandardCode.getInstance().printLocation(u.getLocation()));              
                form1.setField("pm", p.getPm()); 
                if(change.equalsIgnoreCase("All")||change.equalsIgnoreCase("")){}else{
                    Change1 chg = ProjectService.getInstance().getSingleChange(p.getProjectId(),change);
                    if(chg !=null)
                        form1.setField("description", StandardCode.getInstance().noNull(chg.getDescription()));
                }
        
                
                form1.setField("project", p.getNumber() + p.getCompany().getCompany_code());
                
                //get sources and targets
                StringBuffer sources = new StringBuffer("");
                StringBuffer targets = new StringBuffer("");
                List<String> tgtLst = new ArrayList();
                Set sourcelang1 = p.getSourceDocs();
        List<String> srcLst = new ArrayList();
        List targetList = new ArrayList();
        String taskString = "";
        String taskName = "";
        String linTaskName = "", dtpTaskName = "", engTaskName = "", othTaskName = "";
         List<String> linTaskNameLst = new ArrayList(), dtpTaskNameLst = new ArrayList(), engTaskNameLst = new ArrayList(), othTaskNameLst = new ArrayList();
        if (p.getSourceDocs() != null) {
            for (Iterator iterSource = sourcelang1.iterator(); iterSource.hasNext();) {
                SourceDoc sd = (SourceDoc) iterSource.next();
//                sources.append(sd.getLanguage() + "  ");
                if (sd.getTargetDocs() != null) {
                    for (Iterator iterTarget = sd.getTargetDocs().iterator(); iterTarget.hasNext();) {
                        TargetDoc td = (TargetDoc) iterTarget.next();
                        if (!td.getLanguage().equals("All")) {
//                            targets.append(td.getLanguage() + "  ");
//                            targetList.add((String) LanguageAbs.getInstance().getAbs().get(td.getLanguage()));
                            
                                Set<LinTask> linTask = td.getLinTasks();
                                Set<DtpTask> dtpTask = td.getDtpTasks();
                                Set<EngTask> engTask = td.getEngTasks();
                                Set<OthTask> othTask = td.getOthTasks();
                                for (LinTask lt : linTask) {
                                    //System.out.println("&&&"+lt.getTaskName()+lt.getChangeDesc()+td.getLanguage()+sd.getLanguage());
                                    if (StandardCode.getInstance().noNull(lt.getChangeDesc()).equalsIgnoreCase(change)) {
                                        if (!tgtLst.contains(td.getLanguage())) {
                                            tgtLst.add(td.getLanguage());
                                            targets.append(td.getLanguage()).append("  ");
                                            targetList.add((String) LanguageAbs.getInstance().getAbs().get(td.getLanguage()));
                                        }
                                        if (!srcLst.contains(sd.getLanguage())) {
                                            srcLst.add(sd.getLanguage());
                                            sources.append(sd.getLanguage()).append("  ");
                                        }
                                        if(!linTaskNameLst.contains(lt.getTaskName())){
                                            linTaskNameLst.add(lt.getTaskName());
                                        }

                                    }
                                }
                                for (DtpTask lt : dtpTask) {
                                    //System.out.println("&&&"+lt.getTaskName()+lt.getChangeDesc()+td.getLanguage()+sd.getLanguage());
                                    if (StandardCode.getInstance().noNull(lt.getChangeDesc()).equalsIgnoreCase(change)) {
                                        if (!tgtLst.contains(td.getLanguage())) {
                                            tgtLst.add(td.getLanguage());
                                            targets.append(td.getLanguage()).append("  ");
                                            targetList.add((String) LanguageAbs.getInstance().getAbs().get(td.getLanguage()));
                                        }
                                        if (!srcLst.contains(sd.getLanguage())) {
                                            srcLst.add(sd.getLanguage());
                                            sources.append(sd.getLanguage()).append("  ");
                                        }
                                        if(!dtpTaskNameLst.contains(lt.getTaskName())){
                                            dtpTaskNameLst.add(lt.getTaskName());
                                        }

                                    }
                                }
                                for (EngTask lt : engTask) {
                                    //System.out.println("&&&"+lt.getTaskName()+lt.getChangeDesc()+td.getLanguage()+sd.getLanguage());
                                    if (StandardCode.getInstance().noNull(lt.getChangeDesc()).equalsIgnoreCase(change)) {
                                        if (!tgtLst.contains(td.getLanguage())) {
                                            tgtLst.add(td.getLanguage());
                                            targets.append(td.getLanguage()).append("  ");
                                            targetList.add((String) LanguageAbs.getInstance().getAbs().get(td.getLanguage()));
                                        }
                                        if (!srcLst.contains(sd.getLanguage())) {
                                            srcLst.add(sd.getLanguage());
                                            sources.append(sd.getLanguage()).append("  ");
                                        }
                                        if(!engTaskNameLst.contains(lt.getTaskName())){
                                            engTaskNameLst.add(lt.getTaskName());
                                        }

                                    }
                                }
                        }
                    }
                }
            }
        }
       

                
                form1.setField("source", sources.toString());
                form1.setField("target", targets.toString());
                form1.setField("start", (p.getStartDate() != null) ? DateFormat.getDateInstance(DateFormat.SHORT).format(p.getStartDate()) : "");
                form1.setField("due", (p.getDueDate() != null) ? DateFormat.getDateInstance(DateFormat.SHORT).format(p.getDueDate()) : "");
                
                form1.setField("method", p.getDeliveryMethod());
                if(p.getContact()!=null){
                     form1.setField("contactname", p.getContact().getFirst_name()+" "+p.getContact().getLast_name());
                }
               
            
                
                //START add images
//                if(u.getPicture() != null && u.getPicture().length() > 0) {
//                    PdfContentByte over;
//                    Image img = Image.getInstance("C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/logo/images/" + u.getPicture());
//                    img.setAbsolutePosition(200, 200);
//                    over = stamp.getOverContent(1);
//                    over.addImage(img, 60, 0,0, 70, 451,563);
//                }
                //END add images
                
                //stamp.setFormFlattening(true);
                stamp.close();
                
                //write to client (web browser)
                response.setHeader("Content-disposition", "attachment; filename=" +p.getNumber()+p.getCompany().getCompany_code()+"Change-Order" + ".pdf");
                                
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
