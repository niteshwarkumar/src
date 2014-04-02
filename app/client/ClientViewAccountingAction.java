//ClientViewAccountingAction.java gets the current client from the
//db and places it in an attribute for display featuring 
//accounting information

package app.client;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleException;
import org.apache.struts.util.MessageResources;
import org.apache.commons.beanutils.PropertyUtils;
import java.util.*;
import app.user.*;
import app.db.*;
import app.workspace.*;
import app.project.*;
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;


public final class ClientViewAccountingAction extends Action {


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
        String clientId = (String) request.getAttribute("clientViewId");
        
        //id from cookie
        if(clientId == null) {
            clientId = StandardCode.getInstance().getCookie("clientViewId", request.getCookies());	
        }
        
        Integer id = Integer.valueOf(clientId);
        
        //get the client from db
        Client c = ClientService.getInstance().getSingleClient(id);  
        
        //START get the client's project dollar total per year
        //how many years to display
        GregorianCalendar today = new GregorianCalendar();
        today.setTime(new Date());
        int startOfApp = 2001;
        int years = today.get(GregorianCalendar.YEAR) - startOfApp;
        ArrayList[] yearsDisplay = new ArrayList[++years];
        //init year lists
        for(int i = 0; i < yearsDisplay.length; i++) {
            yearsDisplay[i] = new ArrayList();
        }
        
        //add projects to list by year and accumulate yearly totals
        double[] totals = new double[yearsDisplay.length];
        for(Iterator iter = c.getProjects().iterator(); iter.hasNext();) {
            Project p = (Project) iter.next();
            if(p.getDeliveryDate() != null && !p.getStatus().equals("notApproved")) { //add by delivery date
                GregorianCalendar pDate = new GregorianCalendar();
                pDate.setTime(p.getDeliveryDate());
                int position = pDate.get(GregorianCalendar.YEAR) - startOfApp;
                yearsDisplay[position].add(p);
                //update total
                if(p.getProjectAmount() != null) {
                    totals[position] += p.getProjectAmount().doubleValue();
                }
            }            
        }
        
        //display totals as formatted strings
        String[] dollarTotals = new String[yearsDisplay.length];
        for(int i = 0; i < totals.length; i++) {
            dollarTotals[i] = StandardCode.getInstance().formatDouble(new Double(totals[i]));
        }        
        //END get the client's project dollar total per year
        
        //place yearly totals into the request for display
        request.setAttribute("dollarTotals", dollarTotals);
        request.setAttribute("yearsDisplay", yearsDisplay);
        
        //place billing info and requirements into form
        DynaValidatorForm cva = (DynaValidatorForm) form;
        cva.set("billing1", c.getBilling1()); 
        cva.set("billing2", c.getBilling2()); 
        cva.set("billing3", c.getBilling3()); 
        //get this client's language pairs
        Set clps = c.getClientLanguagePairs();
        //array for display in jsp
        ClientLanguagePair[] clpsArray = (ClientLanguagePair[]) clps.toArray(new ClientLanguagePair[0]);
        cva.set("clientLanguagePairs", clpsArray);   
        //get this client's other rates
        Set cors = c.getClientOtherRates();
        //array for display in jsp
        ClientOtherRate[] corsArray = (ClientOtherRate[]) cors.toArray(new ClientOtherRate[0]);
        cva.set("clientOtherRates", corsArray);   
        
        //place linguistic rate scaling into form
        cva.set("scaleRep", c.getScaleRep());
        cva.set("scale100", c.getScale100());
        cva.set("scale95", c.getScale95());
        cva.set("scale85", c.getScale85());
        cva.set("scale75", c.getScale75());
        cva.set("scaleNew", c.getScaleNew());
        cva.set("scale8599", c.getScale8599());
        cva.set("scaleNew4", c.getScaleNew4());
        
                
        //place the client into an attribute for displaying in jsp
        request.setAttribute("client", c);
                
        //add this client id to cookies; this will remember the last client
        response.addCookie(StandardCode.getInstance().setCookie("clientViewId", clientId));
        
        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("clientViewTab", "Accounting"));
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
