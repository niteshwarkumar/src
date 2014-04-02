//ClientContactEditUpdateAction.java collects the new values
//for the contact.  It then updates the contact to the db

package app.client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import app.security.*;


public final class ClientContactEditUpdateAction extends Action {


    // ----------------------------------------------------- Instance Variables


    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log =
        LogFactory.getLog("org.apache.struts.webapp.Example");


    // --------------------------------------------------------- Public Methods


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
	
        //values for update of client's contact
        ClientContactEditForm ccef = (ClientContactEditForm) form;
        
        String First_name = (ccef.getFirst_name());
        String Last_name = (ccef.getLast_name());
        String Title = (ccef.getTitle());
        String Email_address = (ccef.getEmail_address());
        String Email_address2 = (ccef.getEmail_address2());
        String Department = (ccef.getDepartment());
        String Telephone_number = (ccef.getTelephone_number());
        String workPhoneEx = ccef.getWorkPhoneEx();
        String Fax_number = (ccef.getFax_number());
        String Cell_phone_number = (ccef.getCell_phone_number());        
        String Address_1 = (ccef.getAddress_1());
        String Address_2 = (ccef.getAddress_2());
        String City = (ccef.getCity());
        String State_prov = (ccef.getState_prov());
        String Zip_postal_code = (ccef.getZip_postal_code());
        String Country = (ccef.getCountry());        
        String Note = (ccef.getNote());
        String timeZone = (ccef.getTimeZone());
        String division=(ccef.getDivision());
        
        //client contact to-be-updated
        String id = request.getParameter("clientContactId");
        
        //get the contact to be updated
        ClientContact cc = ClientService.getInstance().getSingleClientContact(Integer.valueOf(id));
        
        //update the contacts values
        cc.setFirst_name(First_name);
        cc.setLast_name(Last_name);
        cc.setTitle(Title);
        cc.setEmail_address(Email_address);
        cc.setEmail_address2(Email_address2);
        cc.setDepartment(Department);
        cc.setTelephone_number(Telephone_number);
        cc.setWorkPhoneEx(workPhoneEx);
        cc.setFax_number(Fax_number);
        cc.setCell_phone_number(Cell_phone_number);           
        cc.setAddress_1(Address_1);
        cc.setAddress_2(Address_2);   
        cc.setCity(City);   
        cc.setState_prov(State_prov);   
        cc.setZip_postal_code(Zip_postal_code);    
        cc.setCountry(Country);   
        cc.setNote(Note);
        cc.setTimeZone(timeZone);
        cc.setKey_personnel(ccef.isKey_personnel());
        cc.setDivision(division);
        //update client's contact to db
        ClientService.getInstance().clientContactUpdate(cc);
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
