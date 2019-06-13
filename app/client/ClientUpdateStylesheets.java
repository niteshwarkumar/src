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


public final class ClientUpdateStylesheets extends Action {


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
         int clientId = 0;
            if(id != null && ! id.equals("")){
            clientId = Integer.parseInt(id);
            }
         //Integer id1=Integer.parseInt(id);

        //get the client to be updated from db
       /*Client c =null;
        if (id!=null)
        {
        c = ClientService.getInstance().getSingleClient(clientId);
        }
        else
        {
        c = new Client();
        c.setClientId(0);
        }*/

               Client c  = ClientService.getInstance().getSingleClient(clientId);
               //System.out.println("clinet id............................"+c);

          


        String jsonProducts = request.getParameter("linguisticStylesJSON");
        request.setAttribute("clientViewId",id);

        if(request.getParameter("linguisticStylesJSON")!=null){

          ClientService.getInstance().updateLinguisticStylesheets(clientId,request.getParameter("linguisticStylesJSON"));
          c.setLin_css_other(request.getParameter("lin_css_other") );
          c.setTm_maintained(request.getParameter("TM_Maintained"));
          ClientService.getInstance().updateClient(c);

          return (mapping.findForward("linguistics"));
        }else if(request.getParameter("techStylesJSON")!=null){

             ClientService.getInstance().updateTechStylesheets(clientId,request.getParameter("techStylesJSON"));
          

           c.setTech_css_other(request.getParameter("tech_css_other"));
                
             ClientService.getInstance().updateClient(c);
            return (mapping.findForward("technical"));
        }
        // Forward control to the specified success URI
        return (mapping.findForward("lin"));
    }

}
