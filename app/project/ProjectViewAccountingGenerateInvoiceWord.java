/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.project;

import app.client.ClientService;
import app.security.SecurityService;
import app.standardCode.ExcelConstants;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
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

/**
 *
 * @author abhisheksingh
 */
public class ProjectViewAccountingGenerateInvoiceWord extends Action {

    // ----------------------------------------------------- Instance Variables
    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log
            = LogFactory.getLog("org.apache.struts.webapp.Example");

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
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }

        String templateLocation = StandardCode.getInstance().getPropertyValue("mac.templates.path");

        //START get id of current project from either request, attribute, or cookie
        //id of project from request
        String projectId = null;
        projectId = request.getParameter("projectViewId");

        //check attribute in request
        if (projectId == null) {
            projectId = (String) request.getAttribute("projectViewId");
        }

        //id of project from cookie
        if (projectId == null) {
            projectId = StandardCode.getInstance().getCookie("projectViewId", request.getCookies());
        }

        //default project to last if not in request or cookie
        if (projectId == null) {
            java.util.List results = ProjectService.getInstance().getProjectList();

            ListIterator iterScroll = null;
            for (iterScroll = results.listIterator(); iterScroll.hasNext(); iterScroll.next()) {
            }
            iterScroll.previous();
            Project p = (Project) iterScroll.next();
            projectId = String.valueOf(p.getProjectId());
        }

        Integer id = Integer.valueOf(projectId);

        //END get id of current project from either request, attribute, or cookie
        //get project
        Project p = ProjectService.getInstance().getSingleProject(id);
        
        String templatename =  templateLocation + "/templates2/Client-Invoice-Form.rtf";
        if(p.getCompany().getClientId()==ExcelConstants.CLIENT_BBS){
            templatename =  templateLocation + "/templates/Client-Invoice-FormBBS.rtf";
        }
//        templatename="/Users/abhisheksingh/Project/templates/Client-Invoice-FormBBS.rtf";
        //END check for login (security)
        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));

        byte[] template = getBytesFromFile(new java.io.File(templatename));
        

        String content = new String(template);
        content = content.replaceAll("#DATE#", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
        content = content.replaceAll("#CLIENT_NAME#", p.getCompany().getCompany_name());
        content = content.replaceAll("#PROJECTNO#", p.getNumber() + p.getCompany().getCompany_code());
        content = content.replaceAll("#PO#", StandardCode.getInstance().noNull(p.getClientPO()));

        try {
            String comma = ", ";
            if ("".equalsIgnoreCase(StandardCode.getInstance().noNull(p.getContact().getDivision()))) {
                comma = "";
            }

            content = content.replaceAll("#NAME#", StandardCode.getInstance().noNull(p.getContact().getFirst_name()) + " " + StandardCode.getInstance().noNull(p.getContact().getLast_name()));
            content = content.replaceAll("#COMPANY#", p.getCompany().getCompany_name());
            content = content.replaceAll("#ADDRESS#", StandardCode.getInstance().noNull(p.getContact().getDivision()) + comma + StandardCode.getInstance().noNull(p.getContact().getAddress_1()));
            content = content.replaceAll("#STATE#", StandardCode.getInstance().noNull(p.getContact().getCity()) + ", " + StandardCode.getInstance().noNull(p.getContact().getState_prov()));
            content = content.replaceAll("#ZIP#", StandardCode.getInstance().noNull(p.getContact().getZip_postal_code()));
            content = content.replaceAll("#COUNTRY#", StandardCode.getInstance().noNull(p.getContact().getCountry()));
            content = content.replaceAll("#EMAIL#", StandardCode.getInstance().noNull(p.getContact().getEmail_address()));
            content = content.replaceAll("#CARETAKER#", StandardCode.getInstance().noNull(p.getCareTaker().getFirst_name())+" "+StandardCode.getInstance().noNull(p.getCareTaker().getLast_name()));

        } catch (Exception e) {

            content = content.replaceAll("#NAME#", StandardCode.getInstance().noNull(p.getContact().getFirst_name()) + " " + StandardCode.getInstance().noNull(p.getContact().getLast_name()));
            content = content.replaceAll("#COMPANY#", p.getNumber() + p.getCompany().getCompany_name());
            content = content.replaceAll("#ADDRESS#", StandardCode.getInstance().noNull(p.getCompany().getAddress_1()));
            content = content.replaceAll("#STATE#", StandardCode.getInstance().noNull(p.getCompany().getCity()) + ", " + StandardCode.getInstance().noNull(p.getCompany().getState_prov()));
            content = content.replaceAll("#ZIP#", StandardCode.getInstance().noNull(p.getCompany().getZip_postal_code()));
            content = content.replaceAll("#COUNTRY#", StandardCode.getInstance().noNull(p.getCompany().getCountry()));
            content = content.replaceAll("#EMAIL#", StandardCode.getInstance().noNull(p.getContact().getEmail_address()));
        }

        StringBuffer sources = new StringBuffer("");
        StringBuffer targets = new StringBuffer("");
        if (p.getSourceDocs() != null) {
            for (Iterator iterSource = p.getSourceDocs().iterator(); iterSource.hasNext();) {
                SourceDoc sd = (SourceDoc) iterSource.next();
                sources.append(sd.getLanguage() + " ");
                if (sd.getTargetDocs() != null) {
                    for (Iterator iterTarget = sd.getTargetDocs().iterator(); iterTarget.hasNext();) {
                        TargetDoc td = (TargetDoc) iterTarget.next();
                        if (!td.getLanguage().equals("All")) {
                            targets.append(td.getLanguage() + " ");
                        }
                    }
                }
            }
        }

        Set sourceList = p.getSourceDocs();

        //for each source add each sources' Tasks
        java.util.List totalLinTasks = new java.util.ArrayList();
        java.util.List totalEngTasks = new java.util.ArrayList();
        java.util.List totalDtpTasks = new java.util.ArrayList();
        java.util.List totalOthTasks = new java.util.ArrayList();

        //for each source
        for (Iterator sourceIter = sourceList.iterator(); sourceIter.hasNext();) {
            SourceDoc sd = (SourceDoc) sourceIter.next();

            //for each target of this source
            for (Iterator linTargetIter = sd.getTargetDocs().iterator(); linTargetIter.hasNext();) {
                TargetDoc td = (TargetDoc) linTargetIter.next();

                //for each lin Task of this target
                for (Iterator linTaskIter = td.getLinTasks().iterator(); linTaskIter.hasNext();) {
                    LinTask lt = (LinTask) linTaskIter.next();
                    totalLinTasks.add(lt);
                }

                //for each eng Task of this target
                for (Iterator engTaskIter = td.getEngTasks().iterator(); engTaskIter.hasNext();) {
                    EngTask et = (EngTask) engTaskIter.next();
                    totalEngTasks.add(et);
                }

                //for each dtp Task of this target
                for (Iterator dtpTaskIter = td.getDtpTasks().iterator(); dtpTaskIter.hasNext();) {
                    DtpTask dt = (DtpTask) dtpTaskIter.next();
                    totalDtpTasks.add(dt);
                }

                //for each oth Task of this target
                for (Iterator othTaskIter = td.getOthTasks().iterator(); othTaskIter.hasNext();) {
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
        for (int i = 0; i < linTasksArray.length; i++) {
            if (!gotFirstLin && linTasksArray[i].getWordTotal() != null) {
                linVolume = linTasksArray[i].getWordTotal();
                gotFirstLin = true;
            }
            if (linTasksArray[i].getDollarTotalFee() != null) {
                //remove comma's
                String linTotal = linTasksArray[i].getDollarTotalFee();
                linTotal = linTotal.replaceAll(",", "");
                if (linTotal.equals("")) {
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
        for (int i = 0; i < engTasksArray.length; i++) {
            if (engTasksArray[i].getTotal() != null) {
                engVolume += engTasksArray[i].getTotal().doubleValue();
            }
            if (engTasksArray[i].getDollarTotal() != null) {
                //remove comma's
                String engTotal = engTasksArray[i].getDollarTotal();
                engTotal = engTotal.replaceAll(",", "");
                if (engTotal.equals("")) {
                    engTotal = "0";
                }
                Double total = Double.valueOf(engTotal);
                engTaskTotal += total.doubleValue();

            }

        }

        //find total of DtpTasks
        double dtpTaskTotal = 0;
        double dtpVolume = 0.0;
        for (int i = 0; i < dtpTasksArray.length; i++) {
            if (dtpTasksArray[i].getTotal() != null) {
                dtpVolume += dtpTasksArray[i].getTotal().doubleValue();
            }
            if (dtpTasksArray[i].getDollarTotal() != null) {
                //remove comma's
                String dtpTotal = dtpTasksArray[i].getDollarTotal();
                dtpTotal = dtpTotal.replaceAll(",", "");
                if (dtpTotal.equals("")) {
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
        for (int i = 0; i < othTasksArray.length; i++) {
            if (othTasksArray[i].getTotal() != null) {
                othVolume += othTasksArray[i].getTotal().doubleValue();
            }
            if (othTasksArray[i].getDollarTotal() != null) {
                //remove comma's
                String othTotal = othTasksArray[i].getDollarTotal();
                othTotal = othTotal.replaceAll(",", "");
                if (othTotal.equals("")) {
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
        if (p.getProjectAmount() != null && !"".equals(p.getProjectAmount())) {
            teamTotal = p.getProjectAmount().doubleValue();
        }

        content = content.replaceAll("#LINGUISTIC#", StandardCode.getInstance().formatDouble(new Double(linTaskTotal)));
        content = content.replaceAll("#ENGINEERING#", StandardCode.getInstance().formatDouble(new Double(engTaskTotal)));
        content = content.replaceAll("#DTP#", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotal)));

        content = content.replaceAll("#OTHER#", StandardCode.getInstance().formatDouble(new Double(othTaskTotal)));
//                form1.setField("linvol", StandardCode.getInstance().formatDouble(linVolume));
//                form1.setField("dtpvol", StandardCode.getInstance().formatDouble(new Double(dtpVolume)));
//                form1.setField("engvol", StandardCode.getInstance().formatDouble(new Double(engVolume)));
//                form1.setField("othvol", StandardCode.getInstance().formatDouble(new Double(othVolume)));
//
//                form1.setField("Total_linguistic", StandardCode.getInstance().formatDouble(new Double(linTaskTotal)).replaceAll(",", ""));
//                form1.setField("Total_dtp", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotal)).replaceAll(",", ""));
//                form1.setField("Total_eng", StandardCode.getInstance().formatDouble(new Double(engTaskTotal)).replaceAll(",", ""));
//                form1.setField("Total_other", StandardCode.getInstance().formatDouble(new Double(othTaskTotal)).replaceAll(",", ""));
        double taskTotal = linTaskTotal + dtpTaskTotal + engTaskTotal + othTaskTotal;
        double subtotal = 0;
        try {
            ClientInvoice ci = ProjectService.getInstance().getSingleClientInvoice(Integer.valueOf(request.getParameter("id")));
            if (ci.getAmount() != null && !"".equals(ci.getAmount())) {
                subtotal = Double.parseDouble(ci.getAmount().replaceAll(",", ""));
            }
        } catch (Exception e) {
            subtotal = p.getProjectAmount();
            if (p.getCompany().getCcurrency().equalsIgnoreCase("EURO")) {
                subtotal = p.getProjectAmount() / p.getEuroToUsdExchangeRate();
            }

// content = content.replaceAll("#PM#", );
            // ClientInvoice ci=ProjectService.getInstance().getClientInvoiceByProject(Integer.parseInt(projectId));
        }
        double pmTotal = ProjectService1.getInstance().getTotalPMFee(p);
        double rushTotal = ProjectService1.getInstance().getTotalRushFee(p);
        
         content = content.replaceAll("#RUSH#", StandardCode.getInstance().formatDouble(rushTotal));
         content = content.replaceAll("#PM#", StandardCode.getInstance().formatDouble(pmTotal));

//        if (p.getRushPercentDollarTotal() != null && !"".equals(p.getRushPercentDollarTotal()) && !"0.00".equals(p.getRushPercentDollarTotal())) {
////                    form1.setField("Total_rush", StandardCode.getInstance().formatDouble(new Double(p.getRushPercentDollarTotal().replaceAll(",", ""))).replaceAll(",", ""));
//            content = content.replaceAll("#RUSH#", StandardCode.getInstance().formatDouble(new Double(p.getRushPercentDollarTotal().replaceAll(",", ""))).replaceAll(",", ""));
//            //subtotal += Double.parseDouble(p.getRushPercentDollarTotal().replaceAll(",", ""));
//            htTaskDescriptions.put("Rush", "");
//        } else {
//            content = content.replaceAll("#RUSH#", "0.00");
//        }
//        try {
//            if (p.getPmPercent() != null && !"".equals(p.getPmPercent())) {
//                Double pmFee = taskTotal * Double.parseDouble(p.getPmPercent()) / 100;
//                content = content.replaceAll("#PM#", StandardCode.getInstance().formatDouble(pmFee).replaceAll(",", ""));
////                 form1.setField("Total_pm", StandardCode.getInstance().formatDouble(pmFee).replaceAll(",", ""));
//            } else if (p.getPmPercentDollarTotal() != null && !"".equals(p.getPmPercentDollarTotal())) {
////                    form1.setField("Total_pm", StandardCode.getInstance().formatDouble(new Double(p.getPmPercentDollarTotal().replaceAll(",", ""))).replaceAll(",", ""));
//                content = content.replaceAll("#PM#", StandardCode.getInstance().formatDouble(new Double(p.getPmPercentDollarTotal().replaceAll(",", ""))).replaceAll(",", ""));
////subtotal += Double.parseDouble(p.getPmPercentDollarTotal().replaceAll(",", ""));
//            }
//        } catch (Exception e) {
//        }

        // //System.out.println("subtotal="+subtotal);
        content = content.replaceAll("#TOTAL#", StandardCode.getInstance().formatDouble(new Double(subtotal)));

//                form1.setField("Total_grandtotal", StandardCode.getInstance().formatDouble(new Double(subtotal)));
        int taskNameCounter = 2;

//                 for(Iterator iter = htTaskDescriptions.keySet().iterator(); iter.hasNext();) {
//
//                    String taskName = (String) iter.next();
//
//                    if(taskNameCounter<10){
//                        form1.setField("Task"+taskNameCounter, taskName);
//                        taskNameCounter++;
//                    }
//                 }
        //find changes
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
        try {

//                form1.setField("Describe changes here (if needed)", "");
//                form1.setField("Invoice Details or Requirements","" );
            String billingReq = "}{\\rtlch\\fcs1 \\af1\\afs20 \\ltrch\\fcs0 \\f1\\fs20\\cf20\\insrsid7626187 \\par }"
                    + "{\\rtlch\\fcs1 \\af1\\afs20 \\ltrch\\fcs0 \\f1\\fs20\\cf20\\insrsid13120543 \\hich\\af1\\dbch\\af37\\loch\\f1 #BILLING_REQUIREMENT#"
                    + "";

            List billingReqs = ClientService.getInstance().getBillingReqListForClient("" + p.getCompany().getClientId());

            String billingReqsJSArray = "";
            if (billingReqs != null) {
                for (int i = 0; i < billingReqs.size(); i++) {
                    app.extjs.vo.BillingRequirement pr = (app.extjs.vo.BillingRequirement) billingReqs.get(i);

                    billingReqsJSArray += pr.getRequirement();
                    content = content.replace("#BILLING_REQUIREMENT#", pr.getRequirement() + billingReq);
//                        if(i!=billingReqs.size()-1){
//                          billingReqsJSArray+=" \\\\par ";
//                        }

                }
            }
            content = content.replace("#BILLING_REQUIREMENT#", "");

//                content = content.replace("#BILLING_REQUIREMENT#", billingReqsJSArray);
//                form1.setField("InvoiceInstr",billingReqsJSArray );
        } catch (Exception e) {
            e.printStackTrace();
            content = content.replaceAll("#BILLING_REQUIREMENT#", "");
        }
         Calendar cal = Calendar.getInstance();
         
        content = content.replaceAll("#CURRDATE#",new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime()));
        content = content.replaceAll("#MONTH#",new SimpleDateFormat("MMM").format(cal.getTime()));
        content = content.replaceAll("#YEAR#",new SimpleDateFormat("yyyy").format(cal.getTime()));
        
        content = content.replaceAll("#ORDERNO#",StandardCode.getInstance().noNull(p.getOrderReqNum()));


        content = content.replaceAll("#LANGUAGE#", targets.toString());
        content = content.replaceAll("#PRODUCT#", StandardCode.getInstance().noNull(p.getProduct()));
        content = content.replaceAll("#DESCRIPTION#", StandardCode.getInstance().noNull(p.getProductDescription()));
         content = content.replaceAll("#PRODUCTDESC#", StandardCode.getInstance().noNull(p.getProduct())+" "+StandardCode.getInstance().noNull(p.getProductDescription()));
       
        content = content.replaceAll("#CURRENCY#", StandardCode.getInstance().noNull(p.getCompany().getCcurrency()));

//         content = content.replaceAll("#BILLING_REQUIREMENT#", );
        content = content.replaceAll("#PM_NAME#", p.getPm());
        content = content.replaceAll("#AE_NAME#", p.getCompany().getSales_rep());
//         content = content.replaceAll("##", );
//         content = content.replaceAll("##", );

//        content = content.replaceAll("INSERT_COMPANYLOGO_INSERT", "");
//        content = content.replaceAll("INSERT_NC_NO_INSERT", capaId.getCapa_number());

        String filename = p.getNumber() + p.getCompany().getCompany_code() + "-Client-Invoice.doc";
        if(p.getCompany().getClientId() == ExcelConstants.CLIENT_BBS){
            filename = p.getNumber() + p.getCompany().getCompany_code() + "-BBS-Order-"+StandardCode.getInstance().noNull(p.getOrderReqNum())+"-Client-Invoice.doc";
        }
        
//         013762volc-Client-Invoice.docx

//        content = content.replaceAll("NC-CAPA Internal.rtf", StandardCode.getInstance().noNull(filename));
        //System.out.println("hereeeeee6");
        //write to client (web browser)
        response.setHeader("Content-Type", "Application/msword");
        response.setHeader("Content-disposition", "attachment; filename=" + filename);
        //response.setHeader("Content-disposition", "attachment; filename=" + q.getNumber() + ".doc");
        OutputStream os = response.getOutputStream();
        os.write(content.getBytes());
        os.flush();
        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }

    // Returns the contents of the file in a byte array.
    private static byte[] getBytesFromFile(java.io.File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }
}
