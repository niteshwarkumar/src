/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.client;

import app.admin.AdminService;
import app.extjs.helpers.ProjectHelper;
import app.project.ClientInvoice;
import app.project.Project;
import app.project.ProjectService;
import app.security.SecurityService;
import app.standardCode.DateService;
import app.standardCode.StandardCode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author niteshwar
 */
public class ClientViewInvoicesAction extends Action {

    // ----------------------------------------------------- Instance Variables
    /**
     * The <code>Log</code> instance for this application.
     */
    private final Log log
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
     * @return
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
        //END check for login (security)

        //PRIVS check that correct user is viewing this page 
        if (!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "viewAccountTab")) {
            return (mapping.findForward("accessDenied"));
        }//END PRIVS check that correct user is viewing this page

        String clientId = (String) request.getParameter("clientViewId");
        if(clientId==null){
            clientId = (String) request.getAttribute("clientId");
        }
        String paidId =  request.getParameter("paidInvId");
        if(paidId!=null){
        ClientInvoice ci = ProjectService.getInstance().getSingleClientInvoice(Integer.parseInt(paidId));
        Calendar cal = Calendar.getInstance();
       
        ci.setInvoicePaidDate(DateService.getInstance().convertDate(new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime())).getTime());
        AdminService.getInstance().addObject(ci);
        
        }
//        

        List<Project> pList = ProjectHelper.getProjectListForClientNotComplete(clientId);

        //START NEW prepare invoices for display
        JSONArray clInvoiceArray = new JSONArray();
        pList.forEach((Project p) -> {
            Set clientInvoices = p.getClientInvoices();

            //array for display in jsp
            ClientInvoice[] clientInvoicesArray = (ClientInvoice[]) clientInvoices.toArray(new ClientInvoice[0]);
            

            //set up dates
            for (ClientInvoice clientInvoice : clientInvoicesArray) {
                JSONObject jo = new JSONObject();
                if (clientInvoice.getInvoiceRequestDate() != null) {
                    jo.put("requestDate", DateFormat.getDateInstance(DateFormat.SHORT).format(clientInvoice.getInvoiceRequestDate()));
                } else {
                    jo.put("requestDate", "");
                }
                if (clientInvoice.getInvoicePaidDate() != null) {
                    jo.put("paidDate", DateFormat.getDateInstance(DateFormat.SHORT).format(clientInvoice.getInvoicePaidDate()));
                } else {
                    jo.put("paidDate", "");
                }
                jo.put("amt", Double.parseDouble(clientInvoice.getAmount().replaceAll(",", "")));
                Double pamt = 0.00;
                if (p.getCompany().getCcurrency().equalsIgnoreCase("EURO")) {
                    pamt = StandardCode.getInstance().roundDouble1(p.getProjectAmount()/p.getEuroToUsdExchangeRate());
                } else {
                    pamt = p.getProjectAmount();
                }
                if(pamt.compareTo(Double.parseDouble(clientInvoice.getAmount()))==-1){
                    jo.put("error",true);
                }
                jo.put("pamt",pamt);
                jo.put("curr", p.getCompany().getCcurrency());
                jo.put("invoiceId", clientInvoice.getClientInvoiceId());
                jo.put("desc", clientInvoice.getDescription());
                jo.put("num", clientInvoice.getNumber());
                jo.put("projectId", p.getProjectId());
                jo.put("invoicePeriod", StandardCode.getInstance().noNull(clientInvoice.getInvoicePeriod()));
                try{
                    jo.put("careTaker", p.getCareTaker().getFirst_name()+" "+p.getCareTaker().getLast_name());
                }catch(Exception e){
                    jo.put("careTaker","");
                }
                
                jo.put("orderNo", StandardCode.getInstance().noNull(p.getOrderReqNum()));

                jo.put("pNumber", p.getNumber() + p.getCompany().getCompany_code());
                if (p.getStartDate() != null) {
                    jo.put("pStart", DateFormat.getDateInstance(DateFormat.SHORT).format(p.getStartDate()));
                } else {
                    jo.put("pStart", "");
                }
                if(null==clientInvoice.getInvoicePaidDate())
                clInvoiceArray.put(jo);
            }
        });
        request.setAttribute("invoices", clInvoiceArray);
        request.setAttribute("clientViewId", clientId);
      
        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }

}
