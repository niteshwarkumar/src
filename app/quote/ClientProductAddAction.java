/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.quote;
import app.client.ClientService;
import app.extjs.vo.Product;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import app.security.*;
import app.user.User;
import app.user.UserService;
import org.apache.struts.validator.DynaValidatorForm;

/**
 *
 * @author Niteshwar
 */
public class ClientProductAddAction extends Action{


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

        User u =UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
        Product p=new Product();

        String pname=request.getParameter("pname");
        String category=request.getParameter("category");
        String desc=request.getParameter("desc");
         if(category.equalsIgnoreCase("Enter category of the product here")) {
            category = "";
        }
         if(desc.equalsIgnoreCase("Enter very short description of product here")) {
            desc = "";
        }
        if(!pname.contains("Enter the name of your product here")){

        p.setProduct(pname);
        p.setCategory(category);
        p.setDescription(desc);
        p.setID_Client(u.getID_Client());
        
       
        ClientService.getInstance().saveProduct(p);
         }
        DynaValidatorForm qvg = (DynaValidatorForm) form;
         qvg.set("pName",request.getParameter("pname"));

        return (mapping.findForward("Success"));
    }

}
