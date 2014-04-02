//ProjectViewFormsGen15Action.java creates the
//Quick Reference pdf

package app.project;

import app.extjs.helpers.ProjectHelper;
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

public final class ProjectViewFormsGen15Action extends Action {


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
                PdfReader reader = new PdfReader("C:/templates/PM04_001.pdf"); //the template
                
                //save the pdf in memory
                ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
                
                //the filled-in pdf
                PdfStamper stamp = new PdfStamper(reader, pdfStream);
                
                //stamp.setEncryption(true, "pass", "pass", PdfWriter.AllowCopy | PdfWriter.AllowPrinting);
                AcroFields form1 = stamp.getAcroFields();


                List projectInformalsLin = ProjectHelper.getProjectReqs(p.getProjectId().toString(),"LIN");
        String projectInformalsLinJSArray = "";
        if(projectInformalsLin!=null){
                for(int i=0; i<projectInformalsLin.size();i++){
                    app.extjs.vo.ProjectRequirements pr = (app.extjs.vo.ProjectRequirements)projectInformalsLin.get(i);
                    //JSONObject j = new JSONObject();
                      projectInformalsLinJSArray+= pr.getRequirement().replace("\n"," " );

                        if(i!=projectInformalsLin.size()-1){
                          projectInformalsLinJSArray+=",";
                        }

                }
        }

        List projectInformalsDtp = ProjectHelper.getProjectReqs(p.getProjectId().toString(),"DTP");
        String projectInformalsDtpJSArray = "";
        if(projectInformalsDtp!=null){
                for(int i=0; i<projectInformalsDtp.size();i++){
                    app.extjs.vo.ProjectRequirements pr = (app.extjs.vo.ProjectRequirements)projectInformalsDtp.get(i);
                    //JSONObject j = new JSONObject();
                      projectInformalsDtpJSArray+= pr.getRequirement().replace("\n"," " );

                        if(i!=projectInformalsDtp.size()-1){
                          projectInformalsDtpJSArray+=",";
                        }

                }
        }
        List projectInformalsEng = ProjectHelper.getProjectReqs(p.getProjectId().toString(),"ENG");
        String projectInformalsEngJSArray = "";
        if(projectInformalsEng!=null){
                for(int i=0; i<projectInformalsEng.size();i++){
                    app.extjs.vo.ProjectRequirements pr = (app.extjs.vo.ProjectRequirements)projectInformalsEng.get(i);
                    //JSONObject j = new JSONObject();
                      projectInformalsEngJSArray+= pr.getRequirement().replace("\n"," " );

                        if(i!=projectInformalsEng.size()-1){
                          projectInformalsEngJSArray+=",";
                        }

                }
        }
        List projectInformalsOth = ProjectHelper.getProjectReqs(p.getProjectId().toString(),"Oth");
        String projectInformalsOthJSArray = "";
        if(projectInformalsOth!=null){
                for(int i=0; i<projectInformalsOth.size();i++){
                    app.extjs.vo.ProjectRequirements pr = (app.extjs.vo.ProjectRequirements)projectInformalsOth.get(i);
                    //JSONObject j = new JSONObject();
                      projectInformalsOthJSArray+= pr.getRequirement().replace("\n"," " );

                        if(i!=projectInformalsOth.size()-1){
                          projectInformalsOthJSArray+=",";
                        }

                }
        }


                form1.setField("client", p.getCompany().getCompany_name());
                form1.setField("Project", p.getNumber() + p.getCompany().getCompany_code());
                form1.setField("ae", p.getCompany().getSales_rep());
                form1.setField("contact", StandardCode.getInstance().noNull(p.getContact().getFirst_name()) + " " + StandardCode.getInstance().noNull(p.getContact().getLast_name()));
                form1.setField("turnaroundtime", p.getBeforeWorkTurn() + " " + p.getBeforeWorkTurnUnits());                
                form1.setField("start", (p.getStartDate() != null) ? DateFormat.getDateInstance(DateFormat.SHORT).format(p.getStartDate()) : "");
                form1.setField("deadline", (p.getDueDate() != null) ? DateFormat.getDateInstance(DateFormat.SHORT).format(p.getDueDate()) : "");
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
                form1.setField("source", sources.toString());
                form1.setField("target", targets.toString());
                form1.setField("product", StandardCode.getInstance().noNull(p.getProduct()));
                form1.setField("productdescription", StandardCode.getInstance().noNull(p.getProductDescription()));
                
                form1.setField("generalproductdesc", StandardCode.getInstance().noNull(p.getProjectDescription()));
                form1.setField("generalproductreq", StandardCode.getInstance().noNull(p.getProjectRequirements()));
                form1.setField("lingreq", StandardCode.getInstance().noNull(projectInformalsLinJSArray));
                form1.setField("dtpreq", StandardCode.getInstance().noNull(projectInformalsDtpJSArray));
                form1.setField("engreq", StandardCode.getInstance().noNull(projectInformalsEngJSArray));
                form1.setField("otherreq", StandardCode.getInstance().noNull(projectInformalsOthJSArray));
                form1.setField("os", StandardCode.getInstance().noNull(p.getDeliverableOS()));
                form1.setField("app", StandardCode.getInstance().noNull(p.getDeliverableApplication()));
                form1.setField("version", StandardCode.getInstance().noNull(p.getDeliverableVersion()));
                form1.setField("notes", StandardCode.getInstance().noNull(p.getDeliverableTechNotes()));
                
                //find changes
                double changeTotal = 0;
                int changeCount = 1;
                java.util.Set changes = p.getChange1s();
                if(changes != null && changes.size() > 0) {
                    for(Iterator iter = changes.iterator(); iter.hasNext();) {
                        Change1 c = (Change1) iter.next();
                        if(c.getDollarTotal() != null && c.getDollarTotal().length() > 0) {
                            changeTotal += new Double(c.getDollarTotal()).doubleValue();
                            changeCount++;
                        }
                    }
                }      
                
                if(p.getProjectAmount() != null) {
                    form1.setField("totaloriginal", "$" + StandardCode.getInstance().formatDouble(new Double(p.getProjectAmount().doubleValue() - changeTotal+new Double(p.getRushPercentDollarTotal()))));
                    form1.setField("totalchanges", "$" + StandardCode.getInstance().formatDouble(new Double(changeTotal)));
                    form1.setField("total", "$" + StandardCode.getInstance().formatDouble(p.getProjectAmount()+new Double(p.getRushPercentDollarTotal())));
                }
                        
                form1.setField("clientpo", StandardCode.getInstance().noNull(p.getClientPO()));
                
//                //START add images
//                if(u.getPicture() != null && u.getPicture().length() > 0) {
//                    PdfContentByte over;
//                    Image img = Image.getInstance("C:/Program Files (x86)/Apache Software Foundation/Tomcat 7.0/webapps/logo/images/" + u.getPicture());
//                    img.setAbsolutePosition(200, 200);
//                    over = stamp.getOverContent(1);
//                    over.addImage(img, 54, 0,0, 65, 47, 493);
//                }
//                //END add images
                
                //stamp.setFormFlattening(true);
                stamp.close();
                
                //write to client (web browser)
                
                response.setHeader("Content-disposition", "attachment; filename=" + p.getNumber() + p.getCompany().getCompany_code() + "-Quick-Reference" + ".pdf");
                                
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
