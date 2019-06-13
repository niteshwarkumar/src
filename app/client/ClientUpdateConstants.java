/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
import app.standardCode.StandardCode;

/**
 *
 * @author Niteshwar
 */
public class ClientUpdateConstants extends Action {


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
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)

        //which client to update from hidden value in form
        String clientId = request.getParameter("clientViewId");
        //System.out.println("Client id of client updaterate***********************" + clientId);

        //get the client to be updated from db
        Client c = null;
        if (clientId != null && clientId.trim() != "") {
            c = ClientService.getInstance().getSingleClient(Integer.parseInt(clientId));
        } else {
            c = new Client();
            c.setClientId(0);
        }
        //Client c = ClientService.getInstance().getSingleClient(Integer.valueOf(clientId));
        //System.out.println("client id value of client update rate*********************************");
             c.setCcurrency(request.getParameter("currency"));
        if(request.getParameter("mainSource")!=null){
                          if(!request.getParameter("mainSource").equalsIgnoreCase("")){
            c.setMainSource(request.getParameter("mainSource"));
                          }else{
                             c.setMainSource(null);
                          }}
        if(request.getParameter("mainTarget")!=null){
              if(!request.getParameter("mainTarget").equalsIgnoreCase("")){
            c.setMainTarget(request.getParameter("mainTarget"));
              }else{
                             c.setMainTarget(null);
                          }}
        try{c.setPmfeePercentage(Double.parseDouble(request.getParameter("pmFeePercentage")));}catch(Exception e){} 
        try{c.setTargetProfitability(Double.parseDouble(request.getParameter("targetProfitability")));}catch(Exception e){} 
        
        String autoPmFee = request.getParameter("autoPmFee");
        if(StandardCode.getInstance().noNull(autoPmFee).equalsIgnoreCase("on")){
            c.setAutoPmFee(Boolean.TRUE);
        }else{
            c.setAutoPmFee(Boolean.FALSE);
        }



        request.setAttribute("clientViewId", c);




        ClientService.getInstance().updateClient(c);

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}