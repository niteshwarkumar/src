/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.client;

import app.extjs.helpers.ProjectHelper;
import app.project.ClientInvoice;
import app.project.Project;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author niteshwar
 */
public class ClientViewInvoiceHistoryAction  extends Action {


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

        //get current client ID

        //id from request attribute
        String clientId =  request.getParameter("clientViewId");
        //id from cookie
        if(clientId == null) {
            clientId = (String) request.getAttribute("clientViewId");
        }

        //id from cookie
        if(clientId == null) {
            clientId = StandardCode.getInstance().getCookie("clientViewId", request.getCookies());
        }


         Client c=null;
        if (clientId!=null && clientId.trim() != "")
        c = ClientService.getInstance().getSingleClient(Integer.parseInt(clientId));
        else
        {
        c = new Client();
        c.setClientId(0);
        }
        Calendar cal= new GregorianCalendar();
        String projectYear=request.getParameter("projectYear");
        if (projectYear == null) {
            projectYear = ""+cal.get(Calendar.YEAR);
        }
        
       
        //START NEW prepare invoices for display
        JSONArray clInvoiceArray = new JSONArray();
        JSONObject clInvoiceObj = new JSONObject();
        if(c.getClientId() == 851){
        List<Project> pList = ProjectHelper.getProjectListForClientInvoice(clientId,projectYear);
        pList.forEach((Project p) -> {
            Set clientInvoices = p.getClientInvoices();

            //array for display in jsp
            ClientInvoice[] clientInvoicesArray = (ClientInvoice[]) clientInvoices.toArray(new ClientInvoice[0]);
            

            //set up dates
            for (ClientInvoice clientInvoice : clientInvoicesArray) {
                String invoicePd = StandardCode.getInstance().noNull(clientInvoice.getInvoicePeriod());
                if(invoicePd.isEmpty()) invoicePd="Not Available";
                
               
                
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
//                 if(!StandardCode.getInstance().noNull(clientInvoice.getInvoicePeriod()).isEmpty())
                    clInvoiceArray.put(jo);
                    JSONArray invPdArr = new JSONArray();
                     if(clInvoiceObj.has(invoicePd)){
                     invPdArr = clInvoiceObj.getJSONArray(invoicePd);
                     }
                     invPdArr.put(jo);
                     clInvoiceObj.put(invoicePd,invPdArr);
            }
        });}
        request.setAttribute("invoices", clInvoiceObj);
        System.err.println(clInvoiceObj);
        for(String key: clInvoiceObj.keySet()){
        if(!key.equalsIgnoreCase("Not Available")){
            JSONArray ci = clInvoiceObj.getJSONArray(key);
        }
        
        }
        
        

        request.setAttribute("dollarTotals", ""+ProjectHelper.sumAllProjectAmounts(c.getClientId()));
        //place the client into an attribute for displaying in jsp
        request.setAttribute("client", c); 

        //add this client id to cookies; this will remember the last client
        response.addCookie(StandardCode.getInstance().setCookie("clientViewId", clientId));

        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("clientViewTab", "Project History"));

        
//        String clientViewId=request.getParameter("clientViewId");
        request.setAttribute("projectYear", projectYear);
//        request.setAttribute("clientViewId", clientViewId);

	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
