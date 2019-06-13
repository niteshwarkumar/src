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
public class ClientUploadCRM extends Action {

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
     
//        UtilService.getInstance().hqlTruncate("temp");
        DynaValidatorForm uvg = (DynaValidatorForm) form;
        FormFile Upload = (FormFile) uvg.get("uploadFile");
        JSONObject clients = new JSONObject();
        if (Upload.getFileName().length() > 0) {
            byte[] fileData = Upload.getFileData(); //byte array of file data
            BufferedReader br = null;
            InputStream is = null;
            String line = "";
            String cvsSplitBy = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
            
            JSONArray clientArray = new JSONArray();

            try {
                int rowCount = 0;
                is = new ByteArrayInputStream(fileData);
                br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                   
                    String[] clientCRM = line.split(cvsSplitBy,-1);
                    JSONObject client = new JSONObject();
                    JSONObject contacts = new JSONObject();
                    client.put("id", rowCount++);
                    try{client.put("clientname", clientCRM[2].replaceAll("\"", ""));}catch(Exception e){}
                    try{contacts.put("firstName", clientCRM[0].replaceAll("\"", ""));}catch(Exception e){contacts.put("firstName", "");}
                    try{contacts.put("lastname", clientCRM[1].replaceAll("\"", ""));}catch(Exception e){contacts.put("lastName", "");}
                    try{contacts.put("title", clientCRM[3].replaceAll("\"", ""));}catch(Exception e){contacts.put("title", "");}
                    try{contacts.put("email", clientCRM[4]);}catch(Exception e){contacts.put("email", "");}
                    try{contacts.put("telephone", clientCRM[5]);}catch(Exception e){contacts.put("telephone", "");}
                    try{contacts.put("fax", clientCRM[6]);}catch(Exception e){contacts.put("fax", "");}
                    try{contacts.put("mobile", clientCRM[7]);}catch(Exception e){contacts.put("mobile", "");}
                    try{contacts.put("address1", clientCRM[8].replaceAll("\"", ""));}catch(Exception e){contacts.put("address1", "");}
                    try{contacts.put("address2", clientCRM[9].replaceAll("\"", ""));}catch(Exception e){contacts.put("address2", "");}
                    try{contacts.put("city", clientCRM[10].replaceAll("\"", ""));}catch(Exception e){contacts.put("city", "");}
                    try{contacts.put("state", clientCRM[11].replaceAll("\"", ""));}catch(Exception e){contacts.put("state", "");}
                    try{contacts.put("zip", clientCRM[12]);}catch(Exception e){contacts.put("zip", "");}
                    try{contacts.put("country", clientCRM[13].replaceAll("\"", ""));}catch(Exception e){contacts.put("country", "");}
                    client.put("contact", contacts);
                    Temp tmp = new Temp();
                    tmp.setContent(client.toString(2));
                    client.put("id", UtilService.getInstance().addTemp(tmp));
                   
                    
                    clientArray.put(client);
                }
                clients.put("client", clientArray);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                        if(is != null) is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            
        }
        request.setAttribute("clients", clients);
        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
