//ClientEditUpdateAction.java gets updated client info
//from a form.  It then uploads the new values for
//this client to the db

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


public final class ClientUpdateAccounting extends Action {
    public ClientUpdateAccounting()
    {
        //System.out.println("ClientUpdateAccounting constructor**********************");
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
        
        //which client to update from hidden value in form
        String id = request.getParameter("clientViewId");
        //System.out.println("client id***************"+id);
        
        //get the client to be updated from db
        //Client c = ClientService.getInstance().getSingleClient(Integer.valueOf(id));

        Client c=null;
        if (id!=null){
        c = ClientService.getInstance().getSingleClient(Integer.parseInt(id));
         c.setBilling_address1(request.getParameter("getBilling_address1"));
        //System.out.println(request.getParameter("getBilling_address1"));
        c.setBilling_address2(request.getParameter("getBilling_address2"));
         c.setBilling_zip(request.getParameter("getBilling_zip"));
          c.setBilling_city(request.getParameter("getBilling_city"));
           c.setBilling_country(request.getParameter("getBilling_country"));
           c.setBilling_attention(request.getParameter("getBilling_attention"));
        c.setBilling_reference(request.getParameter("getBilling_reference"));
        }else
        {
        c = new Client();
        c.setClientId(0);
        c.setBilling_address1(request.getParameter("getBilling_address1"));
        //System.out.println(request.getParameter("getBilling_address1"));
        c.setBilling_address2(request.getParameter("getBilling_address2"));
         c.setBilling_zip(request.getParameter("getBilling_zip"));
          c.setBilling_city(request.getParameter("getBilling_city"));
           c.setBilling_country(request.getParameter("getBilling_country"));
           c.setBilling_attention(request.getParameter("getBilling_attention"));
        c.setBilling_reference(request.getParameter("getBilling_reference"));
        }

        
       
        ClientService.getInstance().updateClient(c);
       
        String jsonProducts = request.getParameter("billingReqsJSON");
       // //System.out.println("serviceJSON="+jsonProducts+", clientViewId="+id);
        request.setAttribute("clientViewId",id);
        
        
        ClientService.getInstance().updateClientBillingReq(Integer.parseInt(id),request.getParameter("billingReqsJSON"));
        
        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
    
}
