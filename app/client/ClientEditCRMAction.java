/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.client;

import app.security.SecurityService;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import app.util.Temp;
import app.util.UtilService;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author abhisheksingh
 */
public class ClientEditCRMAction  extends Action {

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
     
        JSONArray nearMatch = new JSONArray();
        JSONArray nearMatchContact = new JSONArray();
        
       String clientStr = request.getParameter("clients");
       Temp tmp = UtilService.getInstance().getSingleTemp(Integer.parseInt(clientStr));
        JSONObject clients = new JSONObject(tmp.getContent());
        int minDistClient = (clients.getString("clientname")).length()/2;
        int minDistContact = (clients.getJSONObject("contact").getString("firstName")).length()/2;
       List clientList = ClientService.getInstance().getClientList();
       for(int i = 0; i<clientList.size();i++){
           Client cl =  (Client) clientList.get(i);
           int minDist = StandardCode.getInstance().minDistance(clients.getString("clientname").split(" ")[0], cl.getCompany_name().split(" ")[0]);
           if(minDist == 0) {
               clients.put("exact", cl.getCompany_name());
               Set contactList = cl.getContacts();
               for(Iterator iter = contactList.iterator(); iter.hasNext(); ) {
                    ClientContact cc = (ClientContact) iter.next();
                    int ccminDist = StandardCode.getInstance().minDistance(clients.getJSONObject("contact").getString("firstName")+"_"+clients.getJSONObject("contact").getString("lastname")  , cc.getFirst_name()+"_"+cc.getLast_name());
                    if(ccminDist == 0) {
                        clients.getJSONObject("contact").put("exact", clients.getJSONObject("contact").getString("firstName")+
                                " "+clients.getJSONObject("contact").getString("lastname")+" of "+cl.getCompany_name());
                    }else if(ccminDist < minDistContact ){
                        nearMatchContact.put(clients.getJSONObject("contact").getString("firstName")+
                                " "+clients.getJSONObject("contact").getString("lastname")+" of "+cl.getCompany_name());
                    }
               }
           }
           else if(minDist < minDistClient){
           nearMatch.put(cl.getCompany_name());
           Set contactList = cl.getContacts();
               for(Iterator iter = contactList.iterator(); iter.hasNext(); ) {
                    ClientContact cc = (ClientContact) iter.next();
                    int ccminDist = StandardCode.getInstance().minDistance(clients.getJSONObject("contact").getString("firstName")+"_"+clients.getJSONObject("contact").getString("lastname")  , cc.getFirst_name()+"_"+cc.getLast_name());
                    if(ccminDist < minDistContact ) {
                    
                        nearMatchContact.put(clients.getJSONObject("contact").getString("firstName")+
                                " "+clients.getJSONObject("contact").getString("lastname")+" of "+cl.getCompany_name());
                    }
               }
           }
           
       }
       clients.getJSONObject("contact").put("near", nearMatchContact);
       clients.put("near", nearMatch);
      
       
       
        request.setAttribute("clients", clients);
        request.setAttribute("clientList", clientList);
        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
