/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import app.security.*;
import app.standardCode.*;
import app.user.User;
import app.user.UserService;

/**
 *
 * @author Nishika
 */
public class ProjectViewAccountingGenerateInvoiceEmailAction  extends Action {


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
User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
        //get project
        Project p = ProjectService.getInstance().getSingleProject(id);

      
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
//
//
//                form1.setField("linvol", StandardCode.getInstance().formatDouble(linVolume));
//                form1.setField("dtpvol", StandardCode.getInstance().formatDouble(new Double(dtpVolume)));
//                form1.setField("engvol", StandardCode.getInstance().formatDouble(new Double(engVolume)));
//                form1.setField("othvol", StandardCode.getInstance().formatDouble(new Double(othVolume)));
//
//                form1.setField("Total_linguistic", StandardCode.getInstance().formatDouble(new Double(linTaskTotal)).replaceAll(",", ""));
//                form1.setField("Total_dtp", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotal)).replaceAll(",", ""));
//                form1.setField("Total_eng", StandardCode.getInstance().formatDouble(new Double(engTaskTotal)).replaceAll(",", ""));
//                form1.setField("Total_other", StandardCode.getInstance().formatDouble(new Double(othTaskTotal)).replaceAll(",", ""));
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

                }
                      double pmTotal = ProjectService1.getInstance().getTotalPMFee(p);
                     double rushTotal = ProjectService1.getInstance().getTotalRushFee(p);
                
//                String rushTotal = "0.00",pmTotal = "";
//                if(p.getRushPercentDollarTotal()!=null && !"".equals(p.getRushPercentDollarTotal()) && !"0.00".equals(p.getRushPercentDollarTotal()) ){
//                   rushTotal  = StandardCode.getInstance().formatDouble(new Double(p.getRushPercentDollarTotal().replaceAll(",", ""))).replaceAll(",", "");
//                    //subtotal += Double.parseDouble(p.getRushPercentDollarTotal().replaceAll(",", ""));
//                     htTaskDescriptions.put("Rush", "");
//                }
//                try{
//                if(p.getPmPercent()!=null && !"".equals(p.getPmPercent())){
//                Double pmFee=taskTotal*Double.parseDouble(p.getPmPercent())/100;
//                 pmTotal =  StandardCode.getInstance().formatDouble(pmFee).replaceAll(",", "");
//                }else if(p.getPmPercentDollarTotal()!=null && !"".equals(p.getPmPercentDollarTotal())){
//                   pmTotal = StandardCode.getInstance().formatDouble(new Double(p.getPmPercentDollarTotal().replaceAll(",", ""))).replaceAll(",", "");
//                    //subtotal += Double.parseDouble(p.getPmPercentDollarTotal().replaceAll(",", ""));
//                }
//                }catch(Exception e){}

                int taskNameCounter = 2;

                 
//                //find changes
//                double changeTotal = 0;
//                int changeCount = 1;
//                java.util.Set changes = p.getChange1s();
//                if(changes != null && changes.size() > 0) {
//                    for(Iterator iter = changes.iterator(); iter.hasNext();) {
//                        Change1 c = (Change1) iter.next();
//                        if(c.getDollarTotal() != null && c.getDollarTotal().length() > 0) {
//                            changeTotal += new Double(c.getDollarTotal().replaceAll(",", "").replaceAll("$","")).doubleValue();
//                            form1.setField("change" + String.valueOf(changeCount) + "Total", StandardCode.getInstance().formatDouble(new Double(c.getDollarTotal().replaceAll(",", ""))));
//                            changeCount++;
//                        }
//                    }
//                }
//                ////System.out.println("changeTotal="+changeTotal);
//                if(changeCount > 1) { //changes were made, so show change total
//                    form1.setField("changeTotal", StandardCode.getInstance().formatDouble(new Double(changeTotal)));
//                }
               //END find billing details (tasks and changes)



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


             
   String divClassLeft="display: table-cell;width:150px; valign='top'";
   String divClassRight="display:table-cell; valign='top'"; 
   String htmlBody="<div style='font-family:verdana;font-size:11'>";
              String comma=", ";
             htmlBody += "<div><div>Hello,</div></div>";
            
            htmlBody += "<div><div>Please find below the invoice for "+p.getNumber() + p.getCompany().getCompany_code()+" :</div></div>";

htmlBody += "<div><div><hr></div></div>";

htmlBody += "<div><div style=\""+divClassLeft+"\">Client: </div><div style=\""+divClassRight+"\">"+ p.getCompany().getCompany_name()+"</div></div>";
htmlBody += "<div><div style=\""+divClassLeft+"\">Project: </div><div style=\""+divClassRight+"\">"+p.getNumber() + p.getCompany().getCompany_code()+"</div></div>";
htmlBody += "<div><div style=\""+divClassLeft+"\">Client PO:</div><div style=\""+divClassRight+"\">"+StandardCode.getInstance().noNull(p.getClientPO())+"</div></div>";
htmlBody += "<div><div><hr></div></div>";

htmlBody += "<div><div style=\""+divClassLeft+"\">Contact:</div><div style=\""+divClassRight+"\">"+StandardCode.getInstance().noNull(p.getContact().getFirst_name()) + " " + StandardCode.getInstance().noNull(p.getContact().getLast_name())+"</div></div>";
htmlBody += "<div><div style=\""+divClassLeft+"\"></div><div style=\""+divClassRight+"\">"+StandardCode.getInstance().noNull(p.getContact().getDivision())+comma+StandardCode.getInstance().noNull(p.getContact().getAddress_1())+"</div></div>";
htmlBody += "<div><div style=\""+divClassLeft+"\"></div><div style=\""+divClassRight+"\">"+StandardCode.getInstance().noNull(p.getContact().getCity())+", "+StandardCode.getInstance().noNull(p.getContact().getState_prov())+", "+StandardCode.getInstance().noNull(p.getContact().getZip_postal_code())+", "+StandardCode.getInstance().noNull(p.getContact().getCountry())+"</div></div>";
htmlBody += "<div><div><hr></div></div>";

htmlBody += "<div><div style=\""+divClassLeft+"\">Target:  </div><div style=\""+divClassRight+"\">"+targets.toString()+"</div></div>";
htmlBody += "<div><div style=\""+divClassLeft+"\">Product: </div><div style=\""+divClassRight+"\">"+StandardCode.getInstance().noNull(p.getProduct())+"</div></div>";
htmlBody += "<div><div style=\""+divClassLeft+"\">Description:</div><div style=\""+divClassRight+"\">"+ StandardCode.getInstance().noNull(p.getProductDescription())+"</div></div>";

htmlBody += "<div><div><hr></div></div>";

htmlBody += "<div><div>INVOICE"+"</div></div>";

htmlBody += "<div><div style=\""+divClassLeft+"\">Currency: </div><div style=\""+divClassRight+"\">"+ StandardCode.getInstance().noNull(p.getCompany().getCcurrency())+"</div></div>";

htmlBody += "<div><div style=\""+divClassLeft+"\">Linguistic: </div><div style=\""+divClassRight+"\">"+StandardCode.getInstance().formatMoney(new Double(linTaskTotal))+"</div></div>";
htmlBody += "<div><div style=\""+divClassLeft+"\">Engineering:</div><div style=\""+divClassRight+"\">"+StandardCode.getInstance().formatMoney(new Double(engTaskTotal))+"</div></div>";
htmlBody += "<div><div style=\""+divClassLeft+"\">DTP:    </div><div style=\""+divClassRight+"\">"+StandardCode.getInstance().formatMoney(new Double(dtpTaskTotal))+"</div></div>";
htmlBody += "<div><div style=\""+divClassLeft+"\">Other: </div><div style=\""+divClassRight+"\">"+StandardCode.getInstance().formatMoney(new Double(othTaskTotal))+"</div></div>";
htmlBody += "<div><div style=\""+divClassLeft+"\">PM:     </div><div style=\""+divClassRight+"\">"+pmTotal+"</div></div>";
htmlBody += "<div><div style=\""+divClassLeft+"\">Rush:    </div><div style=\""+divClassRight+"\">"+rushTotal+"</div></div>";

htmlBody += "<div><div style=\""+divClassLeft+"\">TOTAL:  </div><div style=\""+divClassRight+"\">"+StandardCode.getInstance().formatMoney(new Double(subtotal))+"</div></div>";
htmlBody += "<div><div>&nbsp;</div></div>";
String task="";
for(Iterator iter = htTaskDescriptions.keySet().iterator(); iter.hasNext();) {

                    String taskName = (String) iter.next();
                    if(task.equalsIgnoreCase(""))task = taskName; 
                    else task += ", " + taskName;
                    
                 }
htmlBody += "<div><div style=\""+divClassLeft+"\">Task:  </div><div style=\""+divClassRight+"\">"+task+"</div></div>";
htmlBody += "<div><div style=\""+divClassLeft+"\">Description of Changes:</div></div>";
htmlBody += "<div><div>Describe changes here (if needed)"+"</div></div>";

htmlBody += "<div><div style=\""+divClassLeft+"\">Invoice Instructions:</div><div style=\""+divClassRight+"\">"+billingReqsJSArray+"</div></div>";

htmlBody += "<div><div><hr></div></div>";

htmlBody += "<div><div style=\""+divClassLeft+"\">Project Manager: </div><div style=\""+divClassRight+"\">"+ p.getPm()+"</div></div>";
htmlBody += "<div><div style=\""+divClassLeft+"\">Date:     </div><div style=\""+divClassRight+"\">"+ DateFormat.getDateInstance(DateFormat.SHORT).format(new Date())+"</div></div>";
htmlBody += "<div><div style=\""+divClassLeft+"\">Account Manager:</div><div style=\""+divClassRight+"\">"+ p.getCompany().getSales_rep()+"</div></div>";

htmlBody += "<div><div><hr></div></div>";

htmlBody += "<div><div>Please let me know if you have any questions."+"</div></div>";

htmlBody += "<div><div>"+u.getFirstName()+" "+u.getLastName()+"</div></div></div>";

request.setAttribute("htmlBody", htmlBody);
request.setAttribute("htmlSubject", p.getNumber() + p.getCompany().getCompany_code()+ " - " + p.getPm());


        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}

