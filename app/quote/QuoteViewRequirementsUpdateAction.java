/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.quote;

import app.client.ClientContact;
import app.client.ClientService;
import app.extjs.vo.Product;
import app.project.Project;
import app.project.ProjectService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.*;
import app.user.*;
import app.standardCode.*;
import app.security.*;

/**
 *
 * @author Niteshwar
 */
public class QuoteViewRequirementsUpdateAction extends Action {

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
	User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
        //START get id of current quote from either request, attribute, or cookie
        //id of quote from request
	String quoteId = null;
	quoteId = request.getParameter("quoteViewId");

        //check attribute in request
        if(quoteId == null) {
            quoteId = (String) request.getAttribute("quoteViewId");
        }

        //id of quote from cookie
        if(quoteId == null) {
            quoteId = StandardCode.getInstance().getCookie("quoteViewId", request.getCookies());
        }

        Quote1 q = QuoteService.getInstance().getSingleQuote(Integer.parseInt(quoteId));


        //updated values of the project
        DynaValidatorForm qvpr = (DynaValidatorForm) form;

        String beforeWorkTurn = (String) (qvpr.get("beforeWorkTurn"));
        String beforeWorkTurnUnits = (String) (qvpr.get("beforeWorkTurnUnits"));
        String afterWorkTurn = (String) (qvpr.get("afterWorkTurn"));
        String afterWorkTurnUnits = (String) (qvpr.get("afterWorkTurnUnits"));
        String afterWorkTurnReason = (String) (qvpr.get("afterWorkTurnReason"));
        String reqProjDelDate = (String) (qvpr.get("reqProjDelDate"));
        String newProduct="";
        String[] products=request.getParameterValues("product");
        String productDescription= (String) (qvpr.get("productDescription"));
        String projectManager = (String) qvpr.get("projectManager");
        String clientContact = (String) qvpr.get("clientContact");
        
        String caretaker = (String) qvpr.get("caretaker");
        String orderReqNum = (String) qvpr.get("orderReqNum");
      
        //update the new values
        Project p = q.getProject();
        p.setBeforeWorkTurnUnits(beforeWorkTurnUnits);
        p.setAfterWorkTurn(afterWorkTurn);
        p.setAfterWorkTurnUnits(afterWorkTurnUnits);
        p.setAfterWorkTurnReason(afterWorkTurnReason);
        p.setOrderReqNum(orderReqNum);
        try{
            p.setReqProjDelDate(DateService.getInstance().convertDate(reqProjDelDate).getTime());
        }catch(Exception e){}
        p.setBeforeWorkTurn(beforeWorkTurn);
        if(null == p.getPm()){
          String[] pmEmail = projectManager.split(" ", 2);
          User pmUser = UserService.getInstance().getSingleCurrentUserRealName(pmEmail[0], pmEmail[1]);
          p.setPm(projectManager);
          p.setPm_id(pmUser.getUserId());
        }
        else if(!p.getPm().equalsIgnoreCase(projectManager)){
          String[] pmEmail = projectManager.split(" ", 2);
          User pmUser = UserService.getInstance().getSingleCurrentUserRealName(pmEmail[0], pmEmail[1]);
          p.setPm(projectManager);
          p.setPm_id(pmUser.getUserId());
        }
        
        if(!p.getContact().equals(clientContact)){
            ClientContact cc =  ClientService.getInstance().getSingleClientContact(clientContact);
         p.setContact(cc);
        }
        try{
//        if(!StandardCode.getInstance().noNull(p.getCareTaker()).equals(caretaker)){
            ClientContact cc =  ClientService.getInstance().getSingleClientContact(caretaker);
            p.setCareTaker(cc);
        }catch(Exception e){}
        
        
        if(products!=null){
            for(int i=0;i<products.length;i++){
            newProduct+=products[i];
            if(i<products.length-1){
            newProduct+=",";
            }}
        p.setProduct(newProduct);
        }
        p.setProjectDescription(productDescription);
        p.setProductDescription(productDescription);

        //update to db
        ProjectService.getInstance().updateProject(p);
        QuoteService.getInstance().updateQuote(q,(String)request.getSession(false).getAttribute("username"));
        Client_Quote cq = (Client_Quote) QuoteService.getInstance().getSingleClientQuote(q.getQuote1Id()).get(0);
        Product prod= ClientService.getSingleProduct(p.getCompany().getClientId(), newProduct.split(",")[0]);
        cq.setProduct_ID(prod.getID_Product());
        QuoteService.getInstance().updateClientQuote(cq);


        //place quote into attribute for display
        request.setAttribute("quote", q);


	// Forward control to the specified success URI


          // if(u.getuserType() != null && u.getuserType().equalsIgnoreCase("client")){
        return (mapping.findForward("Success"));//}
        //   else
         //      return (mapping.findForward("Success"));

    }


}
