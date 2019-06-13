/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.client;

import app.security.SecurityService;
import app.user.User;
import app.user.UserService;
import app.util.Temp;
import app.util.UtilService;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.DynaValidatorForm;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author abhisheksingh
 */
public class ClientSaveCRMAction  extends Action {

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
    @Override
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        // Extract attributes we will need
        MessageResources messages = getResources(request);

        // save errors
        ActionMessages errors = new ActionMessages();

        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));

        //START check for login (security)
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)
        
        
     
 //{"clientname":"Test1","contact":{"zip":"60045-2952","country":"USA1","address2":"Suite 1400","city":"Lake Forest",
//"address1":"243 Niles Avenue","mobile":"","telephone":"847-812-2879","title":"President","lastname":"Pohl",
//"firstName":"John","state":"IL","fax":"847-295-9485","email":"jpohl@14thfloorsolutions.com"},"id":1}
        
        String clientname = request.getParameter("clientname");
        String zip = request.getParameter("zip");
        String country = request.getParameter("country");
        String address2 = request.getParameter("address2");
        String city = request.getParameter("city");
        String address1 = request.getParameter("address1");
        String mobile = request.getParameter("mobile");
        String telephone = request.getParameter("telephone");
        String title = request.getParameter("title");
        String lastname = request.getParameter("lastname");
        String firstName = request.getParameter("firstName");
        String state = request.getParameter("state");
        String fax = request.getParameter("fax");
        String email = request.getParameter("email");
        
        String clientStr = request.getParameter("clients");
        if(null!=clientStr){
        Temp tmp = UtilService.getInstance().getSingleTemp(Integer.parseInt(clientStr));
       
        JSONObject clients = new JSONObject(tmp.getContent());
         clientname = clients.getString("clientname");
         zip = clients.getJSONObject("contact").getString("zip");
         country = clients.getJSONObject("contact").getString("country");
         address2 = clients.getJSONObject("contact").getString("address2");
         city = clients.getJSONObject("contact").getString("city");
         address1 = clients.getJSONObject("contact").getString("address1");
         mobile = clients.getJSONObject("contact").getString("mobile");
         telephone = clients.getJSONObject("contact").getString("telephone");
         title = clients.getJSONObject("contact").getString("title");
         lastname = clients.getJSONObject("contact").getString("lastname");
         firstName = clients.getJSONObject("contact").getString("firstName");
         state = clients.getJSONObject("contact").getString("state");
         fax = clients.getJSONObject("contact").getString("fax");
         email = clients.getJSONObject("contact").getString("email");
        }
        Client client = ClientService.getInstance().getSingleClientByName(clientname);
        if(null == client)
        {
            client = new Client();
            client.setCompany_name(clientname);
            client = ClientService.getInstance().addClient(client);
        }
        ClientContact contact = ClientService.getInstance().getSingleClientContact(firstName,lastname);
        if(null == contact){
            contact = new ClientContact();
            contact.setFirst_name(firstName);
            contact.setLast_name(lastname);
            contact.setAddress_1(address1);
            contact.setAddress_2(address2);
            contact.setCell_phone_number(mobile);
            contact.setCompany(client);
            contact.setCity(city);
            contact.setCountry(country);
            contact.setEmail_address(email);
            contact.setFax_number(fax);
            contact.setTitle(title);
            contact.setState_prov(state);
            contact.setTelephone_number(telephone);
            contact.setZip_postal_code(zip);
            
            ClientService.getInstance().clientContactUpdate(contact);
            
            
                  
                    
        }
        
        
request.setAttribute("clientViewId",client.getClientId().toString());
//       String clientStr = request.getParameter("clients");
//       Temp tmp = UtilService.getInstance().getSingleTemp(Integer.parseInt(clientStr));
//       
//       JSONObject clients = new JSONObject(tmp.getContent());
//        request.setAttribute("clients", clients);
        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
