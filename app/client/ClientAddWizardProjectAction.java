//ClientAddWizardProjectAction.java inserts a new client into the db

package app.client;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;

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
import app.standardCode.*;
import app.security.*;

public final class ClientAddWizardProjectAction extends Action {


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
        
        //values for new client from form
        ClientAddEditForm cef = (ClientAddEditForm) form;
        
        String Company_name = cef.getCompany_name();
        
        //START build company code
        String Company_code = null;
        if(Company_name.replaceAll(" ", "").replaceAll(",","").replaceAll("&","").length() >= 4) {
            Company_code = Company_name.replaceAll(" ", "").replaceAll(",","").replaceAll("&","").substring(0, 4).toLowerCase(); 
        }
        else {
            Company_code = Company_name.replaceAll(" ", "").replaceAll(",","").replaceAll("&","").substring(0, Company_name.length()).toLowerCase(); 
        }
        //END build company code
        
        //do not allow duplicate company codes <code>Company_code</code>
        //if duplicate set to null; user will enter manually
        if(ClientService.getInstance().getSingleClientByCode(Company_code) != null) {
             Company_code = null;
        }
        
        String Address_1 = cef.getAddress_1();
        String Address_2 = cef.getAddress_2();
        String City = cef.getCity();
        String State_prov = cef.getState_prov();
        String Zip_postal_code = cef.getZip_postal_code();
        String Country = cef.getCountry();
        String Main_telephone_numb = cef.getMain_telephone_numb();
        String workPhoneEx = cef.getWorkPhoneEx();
        String Fax_number = cef.getFax_number();
        
        //get industry
        String Industry = cef.getIndustry();
        Industry industry = ClientService.getInstance().getClientIndustry(Industry);
        
        String url = cef.getUrl();
        String Status = cef.getStatus();        
        String Email_address = cef.getEmail_address();
        String Note = cef.getNote();
        String Project_mngr = cef.getProject_mngr();
        String Backup_pm = cef.getBackup_pm();
        String Sales_rep = cef.getSales_rep();
        String Sales = cef.getSales();
        String Satisfaction_score = cef.getSatisfaction_score();
        String Satisfaction_level = cef.getSatisfaction_level();
        String Ftp_host_excel = cef.getFtp_host_excel();
        String Ftp_user_id_excel = cef.getFtp_user_id_excel();
        String Ftp_password_excel = cef.getFtp_password_excel();
        String Ftp_host_client = cef.getFtp_host_client();
        String Ftp_user_id_client = cef.getFtp_user_id_client();
        String Ftp_password_client = cef.getFtp_password_client();
        String otherContact1=cef.getOtherContact1();
        String otherContact2=cef.getOtherContact2();
        String otherContact3=cef.getOtherContact3();
        String otherContact4=cef.getOtherContact4();
        String otherContact5=cef.getOtherContact5();
        String specialNotes= cef.getSpecialNotes();
//    private String specialNotes;
        
        //create new client object from above values
        Client c = new Client(Company_name, Company_code, Address_1, Address_2, "", "", "", City, State_prov, Zip_postal_code, Country, Main_telephone_numb, workPhoneEx, Fax_number, url, Status, Email_address, Note, Project_mngr, Backup_pm, Sales_rep, Sales, Satisfaction_score, Satisfaction_level, Ftp_host_excel, Ftp_user_id_excel, Ftp_password_excel, Ftp_host_client, Ftp_user_id_client, Ftp_password_client, "",".3333",".3333","","", "","",".6666","1.0",".25",".25", true, industry, new HashSet(), new HashSet(), new HashSet(), new HashSet(),otherContact1,otherContact2,otherContact3,otherContact4,otherContact5,"USD","","",specialNotes);
        c.setPmfeePercentage(0.0);
        //Add new client to db
        ClientService.getInstance().addClient(c);        
        
        //update total record count for display
        //all clients in db in alphabetical order
        List clients = ClientService.getInstance().getClientList();        
        //set the total now
        String clientRecordTotal = String.valueOf(new Integer(clients.size()));
        request.getSession(false).setAttribute("clientRecordTotal", clientRecordTotal);
        response.addCookie(StandardCode.getInstance().setCookie("clientRecordTotal", String.valueOf(new Integer(clientRecordTotal))));
        
        
        //put new client id in request; used by <code>ClientViewGeneralAction</code>
        c = ClientService.getInstance().getSingleClientByName(Company_name);        
        request.setAttribute("clientViewId", String.valueOf(c.getClientId()));
            
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
