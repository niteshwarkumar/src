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
import java.util.Random;
import java.io.*;
import java.util.*;
import app.security.*;
import app.user.User;
import app.user.UserService;
import org.json.*;


public final class ClientUpdateRegulatoryAction extends Action {
    
    public ClientUpdateRegulatoryAction()
    {
        System.out.println("ClientUpdateRegulatoryAction consturtor****************************");
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
        
        //get the client to be updated from db
       // Client c = ClientService.getInstance().getSingleClient(Integer.valueOf(id));
         Client c=null;
        if (id!=null)
        c = ClientService.getInstance().getSingleClient(Integer.parseInt(id));
        else
        {
        c = new Client();
        c.setClientId(0);
        }
      
        
        String jsonProducts = request.getParameter("regulatoryJSON");
        request.setAttribute("clientViewId",id);
        
        
        ClientService.getInstance().updateClientRegulatory(Integer.parseInt(id),request.getParameter("regulatoryJSON"));
        ClientService.getInstance().updateClientAudit(Integer.parseInt(id),request.getParameter("auditJSON"));
        
        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
    
}
