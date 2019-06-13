//ProjectViewAccountingGenerateInvoiceAction.java creates the
//Client Invoice pdf

package app.project;

import app.client.ClientService;
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

public final class ProjectViewAccountingGenerateInvoiceAction extends Action {


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

        //get invoice

        //get user (project manager)
       // User u = UserService.getInstance().getSingleUserRealName(StandardCode.getInstance().getFirstName(p.getPm()), StandardCode.getInstance().getLastName(p.getPm()));

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
                try{
                form1.setField("division", p.getContact().getDivision());
                }catch(Exception e){}
                form1.setField("Project", p.getNumber() + p.getCompany().getCompany_code());
                form1.setField("PO", StandardCode.getInstance().noNull(p.getClientPO()));



//            content = content.replaceAll("INSERT_CONTACTNAME_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getFirst_name()) + " " + StandardCode.getInstance().noNull(q.getProject().getContact().getLast_name()));
//            //System.out.println("hereeeeee3");
//            content = content.replaceAll("INSERT_CONTACTTITLE_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getTitle()));
//String comma=", ";
//if("".equalsIgnoreCase(StandardCode.getInstance().noNull(q.getProject().getContact().getDivision()))){comma="";}
//            content = content.replaceAll("INSERT_CONTACTADDRESS1_INSERT",StandardCode.getInstance().noNull(q.getProject().getContact().getDivision())+comma+ StandardCode.getInstance().noNull(q.getProject().getContact().getAddress_1()));
//            content = content.replaceAll("INSERT_CONTACTADDRESS2_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getAddress_2()));
//            content = content.replaceAll("INSERT_CONTACTCITY_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getCity()));
//            content = content.replaceAll("INSERT_CONTACTSTATE_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getState_prov()));
//            content = content.replaceAll("INSERT_CONTACTZIPCODE_INSERT", StandardCode.getInstance().noNull(q.getProject().getCompany().getZip_postal_code()));
//            if (q.getProject().getContact().getWorkPhoneEx() != null && q.getProject().getContact().getWorkPhoneEx().length() > 0) { //ext present
//                content = content.replaceAll("INSERT_CONTACTPHONE_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getTelephone_number()) + " ext " + StandardCode.getInstance().noNull(q.getProject().getContact().getWorkPhoneEx()));
//            } else { //no ext present
//                content = content.replaceAll("INSERT_CONTACTPHONE_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getTelephone_number()));
//            }
//            content = content.replaceAll("INSERT_CONTACTFAX_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getFax_number()));
//            content = content.replaceAll("INSERT_CONTACTEMAIL_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getEmail_address()));

                try{
                String comma=", ";
           if("".equalsIgnoreCase(StandardCode.getInstance().noNull(p.getContact().getDivision()))){comma="";}
                form1.setField("contact", StandardCode.getInstance().noNull(p.getContact().getFirst_name()) + " " + StandardCode.getInstance().noNull(p.getContact().getLast_name()));
                form1.setField("address1", StandardCode.getInstance().noNull(p.getContact().getDivision())+comma+StandardCode.getInstance().noNull(p.getContact().getAddress_1()));
                form1.setField("address2", StandardCode.getInstance().noNull(p.getContact().getAddress_2()));
                form1.setField("City", StandardCode.getInstance().noNull(p.getContact().getCity()));
                form1.setField("State", StandardCode.getInstance().noNull(p.getContact().getState_prov()));
                form1.setField("ZIP", StandardCode.getInstance().noNull(p.getContact().getZip_postal_code()));
                form1.setField("Country", StandardCode.getInstance().noNull(p.getContact().getCountry()));

                }catch(Exception e){

                form1.setField("contact", StandardCode.getInstance().noNull(p.getContact().getFirst_name()) + " " + StandardCode.getInstance().noNull(p.getContact().getLast_name()));
                form1.setField("address1", StandardCode.getInstance().noNull(p.getCompany().getAddress_1()));
                form1.setField("address2", StandardCode.getInstance().noNull(p.getCompany().getAddress_2()));
                form1.setField("City", StandardCode.getInstance().noNull(p.getCompany().getCity()));
                form1.setField("State", StandardCode.getInstance().noNull(p.getCompany().getState_prov()));
                form1.setField("ZIP", StandardCode.getInstance().noNull(p.getCompany().getZip_postal_code()));
                form1.setField("Country", StandardCode.getInstance().noNull(p.getCompany().getCountry()));
                 }
                form1.setField("Product", StandardCode.getInstance().noNull(p.getProduct()));
                form1.setField("Description", StandardCode.getInstance().noNull(p.getProductDescription()));
                //form1.setField("InvoiceInstr", StandardCode.getInstance().noNull(p.getCompany().getBilling3()));
                 form1.setField("currency", StandardCode.getInstance().noNull(p.getCompany().getCcurrency()));
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
//                    Image img = Image.getInstance("C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/logo/images/" + u.getPicture());
//                    img.setAbsolutePosition(200, 200);
//                    over = stamp.getOverContent(1);
//                    over.addImage(img, 54, 0,0, 65, 47, 493);
//                }
//                //END add images

//                form1.setField("project", p.getNumber() + p.getCompany().getCompany_code());
//                form1.setField("description", p.getProjectDescription());
//                form1.setField("additional", p.getProjectRequirements());
                ////System.out.println("alexxxx=");
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

                HashMap htTaskDescriptions = new HashMap();
                //find total of LinTasks
                double linTaskTotal = 0;
                boolean gotFirstLin = false;
                Double linVolume = new Double(0.0);
                for(int i = 0; i < linTasksArray.length; i++) {
                    if(!gotFirstLin && linTasksArray[i].getWordTotal() != null) {
                        linVolume = linTasksArray[i].getWordTotal();
                        gotFirstLin = true;
                    }
                    if(linTasksArray[i].getDollarTotalFee() != null) {
                        //remove comma's
                        String linTotal = linTasksArray[i].getDollarTotalFee();
                        linTotal = linTotal.replaceAll(",","");
                        if(linTotal.equals("")){
                            linTotal = "0";
                        }
                        Double total = Double.valueOf(linTotal);
                        linTaskTotal += total.doubleValue();
                        htTaskDescriptions.put(linTasksArray[i].getTaskName(), "");
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
                        if(engTotal.equals("")){
                            engTotal = "0";
                        }
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
                        if(dtpTotal.equals("")){
                            dtpTotal = "0";
                        }
                        Double total = Double.valueOf(dtpTotal);
                        dtpTaskTotal += total.doubleValue();
                        htTaskDescriptions.put(dtpTasksArray[i].getTaskName(), "");
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
                        if(othTotal.equals("")){
                            othTotal = "0";
                        }
                        Double total = Double.valueOf(othTotal);
                        othTaskTotal += total.doubleValue();
                        htTaskDescriptions.put(othTasksArray[i].getTaskName(), "");
                    }

                }

                //team (fee) TOTAL
                double teamTotal = 0;
                ////System.out.println("p.getProjectAmount()="+p.getProjectAmount());
                if(p.getProjectAmount() != null && !"".equals(p.getProjectAmount())) {
                    teamTotal = p.getProjectAmount().doubleValue();
                }


                form1.setField("linvol", StandardCode.getInstance().formatDouble(linVolume));
                form1.setField("dtpvol", StandardCode.getInstance().formatDouble(new Double(dtpVolume)));
                form1.setField("engvol", StandardCode.getInstance().formatDouble(new Double(engVolume)));
                form1.setField("othvol", StandardCode.getInstance().formatDouble(new Double(othVolume)));

                form1.setField("Total_linguistic", StandardCode.getInstance().formatDouble(new Double(linTaskTotal)).replaceAll(",", ""));
                form1.setField("Total_dtp", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotal)).replaceAll(",", ""));
                form1.setField("Total_eng", StandardCode.getInstance().formatDouble(new Double(engTaskTotal)).replaceAll(",", ""));
                form1.setField("Total_other", StandardCode.getInstance().formatDouble(new Double(othTaskTotal)).replaceAll(",", ""));
                double taskTotal=  linTaskTotal+ dtpTaskTotal+ engTaskTotal+othTaskTotal;
                double subtotal = 0;
                try{
                ClientInvoice ci = ProjectService.getInstance().getSingleClientInvoice(Integer.valueOf(request.getParameter("id")));
                if(ci.getAmount()!=null && !"".equals(ci.getAmount())){
                    subtotal = Double.parseDouble(ci.getAmount().replaceAll(",", ""));
                }}catch(Exception e){
                     subtotal=p.getProjectAmount();
                    if(p.getCompany().getCcurrency().equalsIgnoreCase("EURO")){
                    subtotal=p.getProjectAmount()/p.getEuroToUsdExchangeRate();
                    }




               // ClientInvoice ci=ProjectService.getInstance().getClientInvoiceByProject(Integer.parseInt(projectId));

                }
                if(p.getRushPercentDollarTotal()!=null && !"".equals(p.getRushPercentDollarTotal()) && !"0.00".equals(p.getRushPercentDollarTotal()) ){
                    form1.setField("Total_rush", StandardCode.getInstance().formatDouble(new Double(p.getRushPercentDollarTotal().replaceAll(",", ""))).replaceAll(",", ""));
                    //subtotal += Double.parseDouble(p.getRushPercentDollarTotal().replaceAll(",", ""));
                     htTaskDescriptions.put("Rush", "");
                }
                try{
                if(p.getPmPercent()!=null && !"".equals(p.getPmPercent())){
                Double pmFee=taskTotal*Double.parseDouble(p.getPmPercent())/100;
                 form1.setField("Total_pm", StandardCode.getInstance().formatDouble(pmFee).replaceAll(",", ""));
                }else if(p.getPmPercentDollarTotal()!=null && !"".equals(p.getPmPercentDollarTotal())){
                    form1.setField("Total_pm", StandardCode.getInstance().formatDouble(new Double(p.getPmPercentDollarTotal().replaceAll(",", ""))).replaceAll(",", ""));
                    //subtotal += Double.parseDouble(p.getPmPercentDollarTotal().replaceAll(",", ""));
                }
                }catch(Exception e){}

                // //System.out.println("subtotal="+subtotal);
                form1.setField("Total_grandtotal", StandardCode.getInstance().formatDouble(new Double(subtotal)));
                int taskNameCounter = 2;

                 for(Iterator iter = htTaskDescriptions.keySet().iterator(); iter.hasNext();) {

                    String taskName = (String) iter.next();

                    if(taskNameCounter<10){
                        form1.setField("Task"+taskNameCounter, taskName);
                        taskNameCounter++;
                    }
                 }
                //find changes
                double changeTotal = 0;
                int changeCount = 1;
                java.util.Set changes = p.getChange1s();
                if(changes != null && changes.size() > 0) {
                    for(Iterator iter = changes.iterator(); iter.hasNext();) {
                        Change1 c = (Change1) iter.next();
                        if(c.getDollarTotal() != null && c.getDollarTotal().length() > 0) {
                            changeTotal += new Double(c.getDollarTotal().replaceAll(",", "").replaceAll("$","")).doubleValue();
                            form1.setField("change" + String.valueOf(changeCount) + "Total", StandardCode.getInstance().formatDouble(new Double(c.getDollarTotal().replaceAll(",", ""))));
                            changeCount++;
                        }
                    }
                }
                ////System.out.println("changeTotal="+changeTotal);
                if(changeCount > 1) { //changes were made, so show change total
                    form1.setField("changeTotal", StandardCode.getInstance().formatDouble(new Double(changeTotal)));
                }
               //END find billing details (tasks and changes)

                try{

                form1.setField("Describe changes here (if needed)", "");
                form1.setField("Invoice Details or Requirements","" );

                List billingReqs = ClientService.getInstance().getBillingReqListForClient(""+p.getCompany().getClientId());

        String billingReqsJSArray = "";
        if(billingReqs!=null){
                for(int i=0; i<billingReqs.size();i++){
                    app.extjs.vo.BillingRequirement pr = (app.extjs.vo.BillingRequirement)billingReqs.get(i);


                      billingReqsJSArray+= pr.getRequirement();

                        if(i!=billingReqs.size()-1){
                          billingReqsJSArray+=", ";
                        }

                }
        }

                form1.setField("InvoiceInstr",billingReqsJSArray );
                }catch(Exception e){}
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

