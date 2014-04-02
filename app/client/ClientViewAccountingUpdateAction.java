//ClientViewAccountingUpdateAction.java gets updated billing
//info from the form and updates the client to db

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


public final class ClientViewAccountingUpdateAction extends Action {


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
        
        //get updated billing info from the form
        DynaValidatorForm cva = (DynaValidatorForm) form;
   /*     String billing1 = (String) cva.get("billing1"); 
        String billing2 = (String) cva.get("billing2"); 
        String billing3 = (String) cva.get("billing3"); 
        
        //update this client's billing info
        c.setBilling1(billing1);
        c.setBilling2(billing2);
        c.setBilling3(billing3);*/
           
        //update client language pairs
        ClientLanguagePair[] clpsArray = (ClientLanguagePair[]) cva.get("clientLanguagePairs");    
        
        //process each updated client language pair to db
        for(int i = 0; i < clpsArray.length; i++) {
                ClientLanguagePair clp = clpsArray[i];
                
                //upload client language pair changes to db
                ClientService.getInstance().updateClp(clp);
        }
        
        //update client other rates
        ClientOtherRate[] corsArray = (ClientOtherRate[]) cva.get("clientOtherRates");    
        
        //process each updated client other rate to db
        for(int i = 0; i < corsArray.length; i++) {
                ClientOtherRate cor = corsArray[i];
                
                //upload client other rate changes to db
                ClientService.getInstance().updateCor(cor);
        }
        
        //START process linguistic scaling
        String scaleRep = (String) cva.get("scaleRep");
        String scale100 = (String) cva.get("scale100");
        String scale95 = (String) cva.get("scale95");
        String scale85 = (String) cva.get("scale85");
        String scale75 = (String) cva.get("scale75");
        String scaleNew = (String) cva.get("scaleNew");
        String scale8599 = (String) cva.get("scale8599");
        String scaleNew4 = (String) cva.get("scaleNew4");
        
        c.setScaleRep(scaleRep);
        c.setScale100(scale100);
        c.setScale95(scale95);
        c.setScale85(scale85);
        c.setScale8599(scale8599);
        c.setScale75(scale75);
        c.setScaleNew(scaleNew);
        c.setScaleNew4(scaleNew4);
        
        if(request.getParameter("rowsSubmit").equals("4")) {
            c.setScaleDefault(true);
        }
        else {
            c.setScaleDefault(false);
        }
        //END process linguistic scaling
        
        
        //upload client to db
        ClientService.getInstance().updateClient(c);        
                
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
