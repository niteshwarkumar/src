//ClientContactAddEditAction.java gets the current client from a
//cookie for displaying the heading in the jsp

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


public final class ClientContactAddEditAction extends Action {


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
          ClientContactEditForm ccef = (ClientContactEditForm) form;
          ClientContactEditForm cfg=  new ClientContactEditForm();
         cfg.setFirst_name(ccef.getFirst_name());
         cfg.setLast_name(ccef.getLast_name());
         cfg.setTitle(ccef.getTitle());
         cfg.setEmail_address(ccef.getEmail_address());
         cfg.setEmail_address2(ccef.getEmail_address2());
         cfg.setDepartment(ccef.getDepartment());
         cfg.setTelephone_number(ccef.getTelephone_number());
         cfg.setFax_number(ccef.getFax_number());
         cfg.setWorkPhoneEx(ccef.getWorkPhoneEx());
         cfg.setCell_phone_number(ccef.getCell_phone_number());
         cfg.setNote(ccef.getNote());
         cfg.setTimeZone(ccef.getTimeZone());

 

         //get client to put in request for displaying heading in ClientContactAddEdit.jsp
        String clientId = StandardCode.getInstance().getCookie("clientViewId", request.getCookies());
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
         if(clientLocationId.equalsIgnoreCase("")){
       
         cfg.setAddress_1("");
         cfg.setAddress_2("");
         cfg.setCity("");
         cfg.setCountry("");
         cfg.setDivision("");
         cfg.setState_prov("");
         cfg.setZip_postal_code("");
         request.setAttribute("clientContactEditForm", cfg);
 }


        }
        //get client for request
       // Client c = ClientService.getInstance().getSingleClient(Integer.valueOf(clientId));
        Client c=null;
        if (clientId!=null && ! clientId.equals(""))
        {
        c = ClientService.getInstance().getSingleClient(Integer.parseInt(clientId));
        }
        else
        {
        c = new Client();
        c.setClientId(0);
        }

        //place in request for .jsp pages to use
        request.setAttribute("client", c);

	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
