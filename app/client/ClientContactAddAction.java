//ClientContactAddAction.java collects the new contact info
//to be added to the database.  Also, it needs to know the 
//parent client this contact belongs to so it can build a
//one-to-many link between client and contact.
//namely, a foreign key in contact that links to its
//parent client

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
import java.util.*;
import app.standardCode.*;
import app.security.*;

public final class ClientContactAddAction extends Action {

    public ClientContactAddAction()
    {
        System.out.println("ClientContactAddAction construtor calling&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
    }

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
	
        //values for adding of client's contact
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
        String TimeZone = (ccef.getTimeZone());
        String Division = (ccef.getDivision());
        
        //need the client to build the one-to-many link between client and contact
        String clientId = StandardCode.getInstance().getCookie("clientViewId", request.getCookies());
        Client c = ClientService.getInstance().getSingleClient(Integer.valueOf(clientId));  
        
        //create new contact object to be put into db
        ClientContact cc = new ClientContact(First_name, Last_name, Title, Email_address, Email_address2, Department, Telephone_number, workPhoneEx, Fax_number, Cell_phone_number, Address_1, Address_2, City, State_prov, Zip_postal_code, Country,TimeZone,Division, Note, c, new HashSet());
	cc.setKey_personnel(ccef.isKey_personnel());
        //insert into db, building one-to-many link between client and contact
        ClientService.getInstance().addContact(c, cc);
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
