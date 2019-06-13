/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
import app.security.*;
import app.standardCode.StandardCode;
import java.util.List;
import org.json.*;

/**
 *
 * @author Neil
 */
public class ProductAddEditWizardAction extends Action{
   

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
////        MessageResources messages = getResources(request);

        // save errors
        ActionMessages errors = new ActionMessages();

        //START check for login (security)
        if(!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)

        //which client to update from hidden value in form
        String clientId = null;
	clientId = request.getParameter("clientViewId");
        //System.out.println("Client id>>>>>>>>>>>>>>>>>>>..............................."+clientId);
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






       // String id = request.getParameter("clientViewId");
                Client c = ClientService.getInstance().getSingleClient(Integer.valueOf(clientId));


         String jsonProducts = request.getParameter("productJSON");
        ////System.out.println("jsonProducts="+jsonProducts);

        //First delete all products, and then re-insert it
       // ClientService.getInstance().unlinkClientAndProducts(Integer.parseInt(id));
        if(jsonProducts!=null && !"".equals(jsonProducts)){
        JSONArray products = new JSONArray(jsonProducts);
            for(int i=0;i< products.length();i++){
                JSONObject j = (JSONObject)products.get(i);
                Product pr=ClientService.getInstance().getSingleProduct(c.getClientId(), j.getString("product").replaceAll("\n","<br>").replaceAll("\r","<br>"));
               if(pr==null){  pr = new Product();}
                pr.setID_Client(c.getClientId());
                pr.setDescription(j.getString("description"));
                pr.setProduct(j.getString("product").replaceAll("\n","<br>").replaceAll("\r","<br>"));
                pr.setCategory(j.getString("category"));
                ClientService.getInstance().saveProduct(pr);
            }
        }


      return (mapping.findForward("Success"));
    }

}
