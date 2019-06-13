/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.project;

import app.admin.AdminService;
import app.client.Client;
import app.client.ClientService;
import app.extjs.helpers.ProjectHelper;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
 * @author niteshwar
 */
public class ProjectViewAccountingGenerateInvoiceBBSWord  extends Action {

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
        int clientId = ExcelConstants.CLIENT_BBS;
        Client c = ClientService.getInstance().getClient(ExcelConstants.CLIENT_BBS);
        
        String[] invoiceIds = request.getParameterValues("invoice");
        String mnth = request.getParameter("mnth");
        String yr = request.getParameter("yr");
        
        if(invoiceIds!=null){
  
         List<String> invoiceList = Arrays.asList(invoiceIds);
       
        
        String templatename =  templateLocation + "/templates/Client-Invoice-FormBBS-Combined.rtf";

//        templatename="/Users/abhisheksingh/Project/templates/Client-Invoice-FormBBS-Combined.rtf";
        //END check for login (security)
        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));

        byte[] template = getBytesFromFile(new java.io.File(templatename));
        

        String content = new String(template);
        content = content.replaceAll("#DATE#", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
        
         Calendar cal = Calendar.getInstance();
         
        content = content.replaceAll("#CURRDATE#",new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime()));
        if(StandardCode.getInstance().noNull(mnth).isEmpty()){
            mnth = new SimpleDateFormat("MMM").format(cal.getTime());
        }
        if(StandardCode.getInstance().noNull(yr).isEmpty()){
            yr = new SimpleDateFormat("yyyy").format(cal.getTime());
        }
        content = content.replaceAll("#MONTH#",mnth);        
        content = content.replaceAll("#YEAR#",yr);  
        
        content = content.replaceAll("#CLIENT_NAME#", c.getCompany_name());
        String tabRw = "\\\\pard \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 {\\\\rtlch\\\\fcs1 \\\\af1 \\\\ltrch\\\\fcs0 \n" +
"\\\\f1\\\\insrsid14449969 \\\\trowd \\\\irow0\\\\irowband0\\\\ltrrow\\\\ts21\\\\trgaph108\\\\trrh404\\\\trleft-540\\\\trbrdrt\\\\brdrs\\\\brdrw10 \\\\trbrdrl\\\\brdrs\\\\brdrw10 \\\\trbrdrb\\\\brdrs\\\\brdrw10 \\\\trbrdrr\\\\brdrs\\\\brdrw10 \\\\trbrdrh\\\\brdrs\\\\brdrw10 \\\\trbrdrv\\\\brdrs\\\\brdrw10 \n" +
"\\\\trftsWidth3\\\\trwWidth9908\\\\trftsWidthB3\\\\trftsWidthA3\\\\trautofit1\\\\trpaddl108\\\\trpaddr108\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid15755460\\\\tbllkhdrrows\\\\tbllkhdrcols\\\\tbllknocolband\\\\tblind-432\\\\tblindtype3 \\\\clvertalc\\\\clbrdrt\\\\brdrs\\\\brdrw10 \\\\clbrdrl\n" +
"\\\\brdrs\\\\brdrw10 \\\\clbrdrb\\\\brdrs\\\\brdrw10 \\\\clbrdrr\\\\brdrs\\\\brdrw10 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1440\\\\clshdrawnil \\\\cellx1170\\\\clvertalc\\\\clbrdrt\\\\brdrs\\\\brdrw10 \\\\clbrdrl\\\\brdrs\\\\brdrw10 \\\\clbrdrb\\\\brdrs\\\\brdrw10 \\\\clbrdrr\\\\brdrs\\\\brdrw10 \n" +
"\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1260\\\\clshdrawnil \\\\cellx3134\\\\clvertalc\\\\clbrdrt\\\\brdrs\\\\brdrw10 \\\\clbrdrl\\\\brdrs\\\\brdrw10 \\\\clbrdrb\\\\brdrs\\\\brdrw10 \\\\clbrdrr\\\\brdrs\\\\brdrw10 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth4230\\\\clshdrawnil \\\\cellx6028\\\\clvertalc\\\\clbrdrt\\\\brdrs\\\\brdrw10 \n" +
"\\\\clbrdrl\\\\brdrs\\\\brdrw10 \\\\clbrdrb\\\\brdrs\\\\brdrw10 \\\\clbrdrr\\\\brdrs\\\\brdrw10 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1710\\\\clshdrawnil \\\\cellx7978\\\\clvertalc\\\\clbrdrt\\\\brdrs\\\\brdrw10 \\\\clbrdrl\\\\brdrs\\\\brdrw10 \\\\clbrdrb\\\\brdrs\\\\brdrw10 \\\\clbrdrr\\\\brdrs\\\\brdrw10 \n" +
"\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1268\\\\clshdrawnil \\\\cellx9368\\\\row \\\\ltrrow}\\\\trowd \\\\irow1\\\\irowband1\\\\ltrrow\\\\ts21\\\\trgaph108\\\\trrh253\\\\trleft-540\\\\trbrdrt\\\\brdrs\\\\brdrw10 \\\\trbrdrl\\\\brdrs\\\\brdrw10 \\\\trbrdrb\\\\brdrs\\\\brdrw10 \\\\trbrdrr\\\\brdrs\\\\brdrw10 \\\\trbrdrh\\\\brdrs\\\\brdrw10 \n" +
"\\\\trbrdrv\\\\brdrs\\\\brdrw10 \\\\trftsWidth3\\\\trwWidth9908\\\\trftsWidthB3\\\\trftsWidthA3\\\\trautofit1\\\\trpaddl108\\\\trpaddr108\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid5460213\\\\tbllkhdrrows\\\\tbllkhdrcols\\\\tbllknocolband\\\\tblind-432\\\\tblindtype3 \\\\clvertalc\\\\clbrdrt\n" +
"\\\\brdrs\\\\brdrw10 \\\\clbrdrl\\\\brdrs\\\\brdrw10 \\\\clbrdrb\\\\brdrs\\\\brdrw10 \\\\clbrdrr\\\\brdrs\\\\brdrw10 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1440\\\\clshdrawnil \\\\cellx1170\\\\clvertalc\\\\clbrdrt\\\\brdrs\\\\brdrw10 \\\\clbrdrl\\\\brdrs\\\\brdrw10 \\\\clbrdrb\\\\brdrs\\\\brdrw10 \\\\clbrdrr\\\\brdrs\\\\brdrw10 \n" +
"\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1260\\\\clshdrawnil \\\\cellx3134\\\\clvertalc\\\\clbrdrt\\\\brdrs\\\\brdrw10 \\\\clbrdrl\\\\brdrs\\\\brdrw10 \\\\clbrdrb\\\\brdrs\\\\brdrw10 \\\\clbrdrr\\\\brdrs\\\\brdrw10 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth4230\\\\clshdrawnil \\\\cellx6028\\\\clvertalc\\\\clbrdrt\\\\brdrs\\\\brdrw10 \n" +
"\\\\clbrdrl\\\\brdrs\\\\brdrw10 \\\\clbrdrb\\\\brdrs\\\\brdrw10 \\\\clbrdrr\\\\brdrs\\\\brdrw10 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1710\\\\clshdrawnil \\\\cellx7978\\\\clvertalc\\\\clbrdrt\\\\brdrs\\\\brdrw10 \\\\clbrdrl\\\\brdrs\\\\brdrw10 \\\\clbrdrb\\\\brdrs\\\\brdrw10 \\\\clbrdrr\\\\brdrs\\\\brdrw10 \n" +
"\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1268\\\\clshdrawnil \\\\cellx9368\\\\pard \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0\\\\pararsid5460213 {\\\\rtlch\\\\fcs1 \\\\af1 \\\\ltrch\\\\fcs0 \\\\f1\\\\insrsid14449969\\\\charrsid14449969 \n" +
"\\\\hich\\\\af1\\\\dbch\\\\af31505\\\\loch\\\\f1 #ORDERNO#}{\\\\rtlch\\\\fcs1 \\\\af1 \\\\ltrch\\\\fcs0 \\\\f1\\\\insrsid13594001 \\\\cell }{\\\\rtlch\\\\fcs1 \\\\af1 \\\\ltrch\\\\fcs0 \\\\f1\\\\insrsid14449969\\\\charrsid14449969 \\\\hich\\\\af1\\\\dbch\\\\af31505\\\\loch\\\\f1 #PROJECTNO#}{\\\\rtlch\\\\fcs1 \\\\af1 \\\\ltrch\\\\fcs0 \n" +
"\\\\f1\\\\insrsid13594001 \\\\cell }{\\\\rtlch\\\\fcs1 \\\\af1 \\\\ltrch\\\\fcs0 \\\\f1\\\\insrsid14449969\\\\charrsid14449969 \\\\hich\\\\af1\\\\dbch\\\\af31505\\\\loch\\\\f1 #PRODUCTDESC#}{\\\\rtlch\\\\fcs1 \\\\af1 \\\\ltrch\\\\fcs0 \\\\f1\\\\insrsid13594001 \\\\cell }{\\\\rtlch\\\\fcs1 \\\\af1 \\\\ltrch\\\\fcs0 \n" +
"\\\\f1\\\\insrsid14449969\\\\charrsid14449969 \\\\hich\\\\af1\\\\dbch\\\\af31505\\\\loch\\\\f1 #CARETAKER#}{\\\\rtlch\\\\fcs1 \\\\af1 \\\\ltrch\\\\fcs0 \\\\f1\\\\insrsid13594001 \\\\cell }{\\\\rtlch\\\\fcs1 \\\\af1 \\\\ltrch\\\\fcs0 \\\\f1\\\\insrsid14449969\\\\charrsid14449969 \\\\loch\\\\af1\\\\dbch\\\\af31505\\\\hich\\\\f1 \\\\u8364\\\\'db\\\\loch\\\\f1 \n" +
"#TOTAL#}{\\\\rtlch\\\\fcs1 \\\\af1 \\\\ltrch\\\\fcs0 \\\\f1\\\\insrsid13594001 \\\\cell } #TABLEROW#";
        
         List<Project> pList = ProjectHelper.getProjectListForClientNotComplete(""+ExcelConstants.CLIENT_BBS);
double totInvoicePending = 0.00;  
        for(Project p: pList){
            Set clientInvoices = p.getClientInvoices();

            //array for display in jsp
            ClientInvoice[] clientInvoicesArray = (ClientInvoice[]) clientInvoices.toArray(new ClientInvoice[0]);
            for (ClientInvoice clientInvoice : clientInvoicesArray) {
                if(invoiceList.contains(clientInvoice.getClientInvoiceId().toString())){
                    content = content.replaceAll("#TABLEROW#",tabRw);
                content = content.replaceAll("#PROJECTNO#", p.getNumber() + c.getCompany_code());
                content = content.replaceAll("#TOTAL#", clientInvoice.getAmount());
                content = content.replaceAll("#PRODUCTDESC#", StandardCode.getInstance().noNull(p.getProduct())+" "+StandardCode.getInstance().noNull(p.getProductDescription()));
                try{
                content = content.replaceAll("#CARETAKER#", StandardCode.getInstance().noNull(p.getCareTaker().getFirst_name())+" "+StandardCode.getInstance().noNull(p.getCareTaker().getLast_name()));
                }catch(Exception e){
                     content = content.replaceAll("#CARETAKER#","");
                }
                content = content.replaceAll("#PO#", StandardCode.getInstance().noNull(p.getClientPO()));
                content = content.replaceAll("#ORDERNO#", p.getOrderReqNum());
                totInvoicePending += Double.parseDouble(clientInvoice.getAmount().replaceAll(",", ""));
                clientInvoice.setInvoicePeriod(StandardCode.getInstance().noNull(mnth)+"/"+StandardCode.getInstance().noNull(yr));
                AdminService.getInstance().addObject(clientInvoice);
                }
        }
        }

        
        content = content.replaceAll("#CURRENCY#", StandardCode.getInstance().noNull(c.getCcurrency()));
 content = content.replaceAll("#TABLEROW#","");
 content = content.replaceAll("#TOTALINVAMT#", StandardCode.getInstance().formatDouble(totInvoicePending));


        String filename =  c.getCompany_code() + "-Client-Invoice.doc";
        if(c.getClientId() == ExcelConstants.CLIENT_BBS){
            filename = c.getCompany_code() + "-BBS-Order-Client-Invoice.doc";
        }

        //write to client (web browser)
        response.setHeader("Content-Type", "Application/msword");
        response.setHeader("Content-disposition", "attachment; filename=" + filename);
        //response.setHeader("Content-disposition", "attachment; filename=" + q.getNumber() + ".doc");
        OutputStream os = response.getOutputStream();
        os.write(content.getBytes());
        os.flush();
        }
        request.setAttribute("clientId", ""+c.getClientId());
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
