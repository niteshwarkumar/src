//ClientContactEditAction.java loads the contact form with the
//contact's values for this client that are to be edited.
//It places the values in an attribute for display

package app.client;

import app.extjs.vo.ClientLocation;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import app.standardCode.*;
import app.security.*;
import java.util.List;


public final class ClientContactEditAction extends Action {


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
        String clientId = StandardCode.getInstance().getCookie("clientViewId", request.getCookies());

	//START get id of current client contact from either cookie or request
        //id of client contact from request
	String clientContactId = null;
	clientContactId = request.getParameter("clientContactId");
        System.out.println("clientContactId="+clientContactId);
        
        if(clientContactId == null) {
            //id of client contact from cookie
            clientContactId = StandardCode.getInstance().getCookie("clientContactId", request.getCookies());
        }  
        
        Integer id = Integer.valueOf(clientContactId);
        
        //END get id of current client contact from either cookie or request
        
        //get client contact with id from database
        ClientContact cc = ClientService.getInstance().getSingleClientContact(id);
        
         ClientContactEditForm cfg=  new ClientContactEditForm();
        //load client info for editing
       // ClientContactEditForm ccef = new ClientContactEditForm();
        cfg.setFirst_name(cc.getFirst_name());
        cfg.setLast_name(cc.getLast_name());
        cfg.setTitle(cc.getTitle());
        cfg.setEmail_address(cc.getEmail_address());
        cfg.setEmail_address2(cc.getEmail_address2());
        cfg.setDepartment(cc.getDepartment());
        cfg.setTelephone_number(cc.getTelephone_number());
        cfg.setWorkPhoneEx(cc.getWorkPhoneEx());
        cfg.setFax_number(cc.getFax_number());
        cfg.setCell_phone_number(cc.getCell_phone_number());
        cfg.setAddress_1(cc.getAddress_1());
        cfg.setAddress_2(cc.getAddress_2());
        cfg.setCity(cc.getCity());
        cfg.setState_prov(cc.getState_prov());
        cfg.setZip_postal_code(cc.getZip_postal_code());
        cfg.setCountry(cc.getCountry());
        cfg.setNote(cc.getNote());
        cfg.setTimeZone(cc.getTimeZone());
        cfg.setDivision(StandardCode.getInstance().noNull(cc.getDivision()));



        String clientLocationId=request.getParameter("clientLocationId");
        if(clientLocationId!=null){
        List clientLocation=ClientService.getInstance().getLocationsListForClient(clientId);

        //DynaValidatorForm cfg=(DynaValidatorForm) form;
        for(int i=0;i<clientLocation.size();i++){
         ClientLocation cl=  (ClientLocation) clientLocation.get(i);

        if(clientLocationId.equalsIgnoreCase(cl.getDivision())){
         cfg.setAddress_1(cl.getAddress1());
         cfg.setAddress_2(cl.getAddress2());
         cfg.setCity(cl.getCity());
         cfg.setCountry(cl.getCountry());
         cfg.setDivision(cl.getDivision());
         cfg.setState_prov(cl.getState());
         cfg.setZip_postal_code(cl.getZip());


            request.setAttribute("clientContactEditForm", cfg);

         }
        }
 if(clientLocationId.equalsIgnoreCase("main")){
        Client cl=ClientService.getInstance().getClient(Integer.parseInt(clientId));
         cfg.setAddress_1(cl.getAddress_1());
         cfg.setAddress_2(cl.getAddress_2());
         cfg.setCity(cl.getCity());
         cfg.setCountry(cl.getCountry());
         cfg.setDivision("");
         cfg.setState_prov(cl.getState_prov());
         cfg.setZip_postal_code(cl.getZip_postal_code());
         request.setAttribute("clientContactEditForm", cfg);
 }
        }

        
        //set the client's values and contact form into attributes for display
        request.setAttribute("clientContactEditForm", cfg);
        request.setAttribute("clientContact", cc);
        request.setAttribute("client", cc.getCompany());
        
        //add this client contact id to cookies; this will remember the last client contact
        response.addCookie(StandardCode.getInstance().setCookie("clientContactId", clientContactId));
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
