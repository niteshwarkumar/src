//ClientEditUpdateAction.java gets updated client info
//from a form.  It then uploads the new values for
//this client to the db

package app.client;
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
import org.apache.struts.upload.FormFile;
import java.io.*;
import java.util.*;
import app.security.*;
import app.user.User;
import app.user.UserService;
import org.json.*;


public final class ClientEditUpdateAction extends Action {


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

        //which client to update from hidden value in form
        String id = request.getParameter("clientViewId");

        //get the client to be updated from db
        Client c = ClientService.getInstance().getSingleClient(Integer.valueOf(id));

        //values for update from form; change what is stored in db to these values
        ClientEditForm cef = (ClientEditForm) form;

        String Company_name = cef.getCompany_name();

        String Company_code = cef.getCompany_code().toLowerCase();
        String Company_code_current = c.getCompany_code();
        //do not allow duplicate company codes <code>Company_code</code>
        //if duplicate set to null; user will enter manually
        if(!Company_code.equals(Company_code_current)) { //user changed company code in form
            if(ClientService.getInstance().getSingleClientByCode(Company_code) != null) {
                Company_code = null;
            }
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

        //get industry (this is in another table, so it is an object (not String))
        String Industry = cef.getIndustry();
        Industry industry = ClientService.getInstance().getClientIndustry(Industry);

        String url = cef.getUrl();
        String Status = cef.getStatus();
        String Email_address = cef.getEmail_address();
        FormFile logo = cef.getLogo();
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
        String Business_desc = cef.getBusiness_desc();
        String certifications = cef.getCertifications();
        int delinquent =      cef.getDelinquent();
        FormFile terms=cef.getTerms();
        String termsNames=cef.getTermsNames();
        String auto_alert = cef.getAuto_alert();
        String specialNotes = cef.getSpecialNotes();
        
        //process logo upload
        String saveFileName = null;
        if(logo.getFileName().length() > 0) {
            String fileName = logo.getFileName();
            byte[] fileData  = logo.getFileData(); //byte array of file data
            //random number in image name to prevent repeats
            Random gen = new Random(new Date().getSeconds());
            saveFileName = String.valueOf(gen.nextInt()) + fileName;
            File saveFile = new File("C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/logo/images/" + saveFileName); 
            FileOutputStream out = new FileOutputStream(saveFile);
            out.write(fileData);
            out.flush();
            out.close();
        }

           String saveTermsName = null;
           String fileName="";
        if(terms.getFileName().length() > 0) {
            fileName = terms.getFileName();
            byte[] fileData  = terms.getFileData(); //byte array of file data
            //random number in image name to prevent repeats
            Random gen = new Random(new Date().getSeconds());
            saveTermsName = String.valueOf(gen.nextInt()) + fileName;
            File saveFile = new File("C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/logo/ClientUpload/" + saveTermsName);
            FileOutputStream out = new FileOutputStream(saveFile);
            out.write(fileData);
            out.flush();
            out.close();
        }

         if(saveTermsName != null) { //was a new logo uploaded
            c.setTerms(saveTermsName);
            c.setTermsNames(fileName);
        }
        //update the client's values
        c.setCompany_name(Company_name);
        c.setCompany_code(Company_code);
        c.setAddress_1(Address_1);
        c.setAddress_2(Address_2);
        c.setCity(City);
        c.setState_prov(State_prov);
        c.setZip_postal_code(Zip_postal_code);
        c.setCountry(Country);
        c.setMain_telephone_numb(Main_telephone_numb);
        c.setWorkPhoneEx(workPhoneEx);
        c.setFax_number(Fax_number);
        c.setIndustry(industry);
        c.setUrl(url);
        c.setStatus(Status);
        c.setEmail_address(Email_address);
        if(saveFileName != null) { //was a new logo uploaded
            c.setLogo(saveFileName);
        }
        c.setProject_mngr(Project_mngr);
        c.setBackup_pm(Backup_pm);
        c.setSales_rep(Sales_rep);
        c.setSales(Sales);
        c.setSatisfaction_score(Satisfaction_score);
        c.setSatisfaction_level(Satisfaction_level);
        c.setFtp_host_excel(Ftp_host_excel);
        c.setFtp_user_id_excel(Ftp_user_id_excel);
        c.setFtp_password_excel(Ftp_password_excel);
        c.setFtp_host_client(Ftp_host_client);
        c.setFtp_user_id_client(Ftp_user_id_client);
        c.setFtp_password_client(Ftp_password_client);
        c.setDelinquent(delinquent);
        c.setBusiness_desc(Business_desc);
        c.setCertifications(certifications);

        c.setMain_dtp(cef.getMain_dtp());
        c.setMain_engineer(cef.getMain_engineer());
        c.setOther_dtp(cef.getOther_dtp());
        c.setOther_engineer(cef.getOther_engineer());
        c.setOtherContact1(cef.getOtherContact1());
        c.setOtherContact2(cef.getOtherContact2());
        c.setOtherContact3(cef.getOtherContact3());
        c.setOtherContact4(cef.getOtherContact4());
        c.setOtherContact5(cef.getOtherContact5());
        c.setAuto_alert(auto_alert);
        c.setSpecialNotes(specialNotes);
        User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));

        c.setModifiedByDate(new Date());
        c.setModifiedBy(u.getFirstName()+" " + u.getLastName());
        // //System.out.println("alexx:delinquent="+delinquent);
        //set updated values to db
        ClientService.getInstance().clientUpdateNoContact(c);

        String jsonProducts = request.getParameter("productJSON");
        ////System.out.println("jsonProducts="+jsonProducts);

        //First delete all products, and then re-insert it
       // ClientService.getInstance().unlinkClientAndProducts(Integer.parseInt(id));
        if(jsonProducts!=null && !"".equals(jsonProducts)){
        JSONArray products = new JSONArray(jsonProducts);
            for(int i=0;i< products.length();i++){
                JSONObject j = (JSONObject)products.get(i);
              //  //System.out.println(j.getString("productId"));
               Product pr=null;
               try{
                 pr=ClientService.getInstance().getSingleProduct(Integer.parseInt(j.getString("productId")));
                }catch(Exception e){
                 pr = new Product();}
                pr.setID_Client(c.getClientId());
                pr.setDescription(j.getString("description"));
                pr.setProduct(j.getString("product").replaceAll("\n","<br>").replaceAll("\r","<br>"));
                pr.setCategory(j.getString("category"));
                ClientService.getInstance().saveProduct(pr);

            }
        List prList=ClientService.getInstance().getProductListForClient(c.getClientId());
        for(int i=0;i<prList.size();i++){
        Product prod=(Product) prList.get(i);
        Integer flag=0;
        for(int ii=0;ii< products.length();ii++){
        JSONObject j = (JSONObject)products.get(ii);
try{

                if(prod.getID_Product()==Integer.parseInt(j.getString("productId")))
                {
                    flag=1;
                }

            }catch(Exception e){flag=1;}
            }
        if(flag==0){ClientService.getInstance().deleteProduct(prod);}

        }

        }
        //Update communication table
        ClientService.getInstance().updateClientCommunication(Integer.parseInt(id),request.getParameter("commJSON"));
        //update location table
        ClientService.getInstance().updateClientLocation(Integer.parseInt(id),request.getParameter("locationJSON"));

        request.setAttribute("lastOpennedTab",request.getParameter("lastOpennedTab"));
        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }

}
