//ClientViewGeneralAction.java gets the current client from the db
//It then places that client into the client form for
//display in the jsp page. It then places location values into
//cookies

package app.client;
import app.extjs.helpers.HrHelper;
import app.extjs.vo.ClientLocation;
import app.extjs.vo.Communication;
import app.extjs.vo.Product;
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
import app.security.*;
import app.standardCode.*;
import app.user.User;
import app.user.UserService;
import org.json.JSONObject;


public final class ClientViewGeneralAction extends Action {


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
        
        //START get id of current client from either request, attribute, or cookie 
        //id of client from request
	String clientId = null;
        Integer flag=0;
        User u=UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
       try{
           System.out.println(u.getuserType()+"============== dddddddddddddddddddddddddddddddd======"+u.getId_client());
     if(u.getuserType().equalsIgnoreCase("client")){
           clientId=""+u.getId_client();
        flag=1;
     }

       }catch(Exception e){
       flag=0;

       }
        if(flag==1){
        System.out.println("Client id>>>>>>>>>>>>>>>>>>>..............................."+clientId);
        }else{
	clientId = request.getParameter("clientViewId");
        System.out.println("Client id>>>>>>>>>>>>>>>>>>>..............................."+clientId);
        //check attribute in request
        if(clientId == null) {
            clientId = (String) request.getAttribute("clientViewId");
        }
        
        //id of client from cookie
        if(clientId == null) {            
            clientId = StandardCode.getInstance().getCookie("clientViewId", request.getCookies());
        }

        //default client to first if not in request or cookie
        if(clientId == null || "null".equals(clientId)) {
                List results = ClientService.getInstance().getClientList();
                Client first = (Client) results.get(0);
                clientId = String.valueOf(first.getClientId());
            }            
        }
      //  Integer id = Integer.valueOf(clientId);
        
        //END get id of current client from either request, attribute, or cookie 
              
        
        //get client to edit
        //Client c = ClientService.getInstance().getSingleClient(id);

         Client c=null;
        if (clientId!=null && clientId.trim() != "")
        {
        c = ClientService.getInstance().getSingleClient(Integer.parseInt(clientId));
         System.out.println("clientid>....................................."+c);
        }
        else
        {
        c = new Client();
        c.setClientId(0);
        }
                
        //START get current record number
        //all current clients in db in number order
        List clients = ClientService.getInstance().getClientList();
                        
        //set the total now
        String clientRecordTotal = String.valueOf(new Integer(clients.size()));
        Client newC;
        Client currentC = c;
	int current = 0;
        
        //get the current record number (e.g., "3" of 40)
        for(ListIterator iter = clients.listIterator(); iter.hasNext(); ) {
                  newC = (Client) iter.next();
                  current++; //advance current record count
                  if(newC.getClientId().equals(currentC.getClientId())) { //check if current                         
                      break; //stop looking for current count
                  }
        }
        //set current record and total records into session and cookie
        request.getSession(false).setAttribute("clientRecordCurrent", String.valueOf(new Integer(current)));
        response.addCookie(StandardCode.getInstance().setCookie("clientRecordCurrent", String.valueOf(new Integer(current))));
        
        //END get current record number
        
        //load client info for editing
        ClientEditForm cef = new ClientEditForm();
        cef.setCompany_name(c.getCompany_name());
        cef.setCompany_code(c.getCompany_code());
        cef.setAddress_1(c.getAddress_1());
        cef.setAddress_2(c.getAddress_2());
        cef.setCity(c.getCity());
        cef.setState_prov(c.getState_prov());
        cef.setZip_postal_code(c.getZip_postal_code());
        cef.setCountry(c.getCountry());
        cef.setMain_telephone_numb(c.getMain_telephone_numb());
        cef.setWorkPhoneEx(c.getWorkPhoneEx());
        cef.setFax_number(c.getFax_number());
        cef.setBusiness_desc(c.getBusiness_desc());
       
        
        //make sure industry is not null
        Industry industry = c.getIndustry();
        if(industry != null) {
            cef.setIndustry(industry.getDescription());
        }
       
        cef.setUrl(c.getUrl());
        cef.setStatus(c.getStatus());
        cef.setEmail_address(c.getEmail_address());
        cef.setNote(c.getNote());
        cef.setProject_mngr(c.getProject_mngr());
        cef.setBackup_pm(c.getBackup_pm());
        cef.setSales_rep(c.getSales_rep());
        cef.setSatisfaction_score(c.getSatisfaction_score());
        cef.setSatisfaction_level(c.getSatisfaction_level());
        cef.setFtp_host_excel(c.getFtp_host_excel());
        cef.setFtp_user_id_excel(c.getFtp_user_id_excel());
        cef.setFtp_password_excel(c.getFtp_password_excel());
        cef.setFtp_host_client(c.getFtp_host_client());
        cef.setFtp_user_id_client(c.getFtp_user_id_client());
        cef.setFtp_password_client(c.getFtp_password_client());
        cef.setFtp_password_client(c.getFtp_password_client());
        cef.setCertifications(c.getCertifications());
        
        cef.setMain_dtp(c.getMain_dtp());
        cef.setMain_engineer(c.getMain_engineer());
        cef.setOther_dtp(c.getOther_dtp());
        cef.setOther_engineer(c.getOther_engineer());
        cef.setOtherContact1(c.getOtherContact1());
        cef.setOtherContact2(c.getOtherContact2());
        cef.setOtherContact3(c.getOtherContact3());
        cef.setOtherContact4(c.getOtherContact4());
        cef.setOtherContact5(c.getOtherContact5());
     
         cef.setAuto_alert(c.getAuto_alert());
        
        //place this client into the client form for display
        request.setAttribute("clientEditForm", cef);        
        request.setAttribute("client", c);
        List clientProducts = ClientService.getInstance().getProductListForClient(new Integer(clientId));
        String productsJSArray = "";
        for(int i=0; i<clientProducts.size();i++){
            Product pr = (Product)clientProducts.get(i);
            JSONObject j = new JSONObject();
              productsJSArray+= "[\""+pr.getID_Product()+"\",";
              productsJSArray+= "\""+HrHelper.jsSafe(pr.getProduct())+"\",";
              productsJSArray+= "\""+HrHelper.jsSafe(pr.getCategory())+"\",";
              productsJSArray+= "\""+HrHelper.jsSafe(pr.getDescription())+"\"]";
             
                if(i!=clientProducts.size()-1){
                  productsJSArray+=",";
                }
            
        }
        
        
        request.setAttribute("productsJSArray", productsJSArray);
        
        System.out.println("Products JASON"+productsJSArray);
        List clientComms = ClientService.getInstance().getCommunicationListForClient(clientId);
        String commJSArray = "";
        for(int i=0; i<clientComms.size();i++){
            Communication pr = (Communication)clientComms.get(i);
            JSONObject j = new JSONObject();
              commJSArray+= "[\""+HrHelper.jsSafe(pr.getCommunicationType())+"\",";
              commJSArray+= "\""+HrHelper.jsSafe(pr.getDescription())+"\"]";
                if(i!=clientComms.size()-1){
                  commJSArray+=",";
                }
            
        }
        
        
        request.setAttribute("communicationJSArray", commJSArray);
        
        
        List clientLocations = ClientService.getInstance().getLocationsListForClient(clientId);
        String locationJSArray = "";
        for(int i=0; i<clientLocations.size();i++){
            ClientLocation pr = (ClientLocation)clientLocations.get(i);
            JSONObject j = new JSONObject();
              locationJSArray+= "[\""+pr.getLocation_id()+"\",";
            //  locationJSArray+= "\""+StandardCode.getInstance().noNull(pr.getDivision())+"\",";
              locationJSArray+= "\""+HrHelper.jsSafe(StandardCode.getInstance().noNull(pr.getDivision()))+"\",";
              locationJSArray+= "\""+HrHelper.jsSafe(pr.getAddress1())+"\",";
              locationJSArray+= "\""+HrHelper.jsSafe("")+"\",";
              locationJSArray+= "\""+HrHelper.jsSafe(pr.getCity())+"\",";
              locationJSArray+= "\""+HrHelper.jsSafe(pr.getState())+"\",";
              locationJSArray+= "\""+HrHelper.jsSafe(pr.getZip())+"\",";
              locationJSArray+= "\""+HrHelper.jsSafe(pr.getCountry())+"\"]";
                if(i!=clientLocations.size()-1){
                  locationJSArray+=",";
                }
            
        }
        
        
        request.setAttribute("locationJSArray", locationJSArray);
        
        //add this client id to cookies; this will remember the last client
        response.addCookie(StandardCode.getInstance().setCookie("clientViewId", clientId));
        
        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("clientViewTab", "General Info"));
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
