/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.*;
import java.util.*;
import app.user.*;
import app.security.*;

/**
 *
 * @author Neil
 */
public class Client_ProjectSearchAction extends Action{

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
	//MessageResources messages = getResources(request);

	// save errors
	ActionMessages errors = new ActionMessages();

        //START check for login (security)
        if(!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)
	User user = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
        //values for search
       Integer id=user.getID_Client();
        DynaValidatorForm ps = (DynaValidatorForm) form;

        String status = (String) ps.get("status");
        //Integer id=user.getID_Client();
       // String companyName = (String) ps.get("companyNameProjectSearch");
        String projectNumber = (String) ps.get("projectNumberProjectSearch");
        String product = (String) ps.get("productProjectSearch");
        String productDescription = (String) ps.get("productDescriptionProjectSearch");
        String notes = (String) ps.get("notesProjectSearch");
        String invoicing = (String) ps.get("projectSearchInvoicing");
        String deliveryDateFrom = (String) ps.get("deliveryDateFrom");
        String deliveryDateTo = (String) ps.get("deliveryDateTo");
        Date deliveryFrom = null;
        Date deliveryTo = null;

        if(deliveryDateFrom!=null){
            StringTokenizer st = new StringTokenizer(deliveryDateFrom,"/");
            if(st.countTokens()==3){
                //valid date
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());

                String month = st.nextToken();
                if(month.startsWith("0")){
                    month = month.substring(1);
                }
                c.set(Calendar.MONTH, Integer.parseInt(month)-1);
                String date = st.nextToken();
                 if(date.startsWith("0")){
                    date = date.substring(1);
                }
                c.set(Calendar.DATE, Integer.parseInt(date));

                String year = st.nextToken();
                c.set(Calendar.YEAR, Integer.parseInt(year));

                deliveryFrom = c.getTime();
                //deliveryFrom.setMonth(Integer.parseInt(month));
                //deliveryFrom.setDate(Integer.parseInt(date));
                //deliveryFrom.setYear(Integer.parseInt(year));

            }
        }

        if(deliveryDateTo!=null){
            StringTokenizer st = new StringTokenizer(deliveryDateTo,"/");
            if(st.countTokens()==3){

                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                //valid date
                String month = st.nextToken();
                if(month.startsWith("0")){
                    month = month.substring(1);

                }
                c.set(Calendar.MONTH, Integer.parseInt(month)-1);
                String date = st.nextToken();
                 if(date.startsWith("0")){
                    date = date.substring(1);

                }
                 c.set(Calendar.DATE, Integer.parseInt(date));

                String year = st.nextToken();
                c.set(Calendar.YEAR, Integer.parseInt(year));
                deliveryTo = c.getTime();
                //deliveryTo = new Date();
                //deliveryTo.setMonth(Integer.parseInt(month));
                //deliveryTo.setDate(Integer.parseInt(date));
                //deliveryTo.setYear(Integer.parseInt(year));

            }
        }
        //perform search and store results in a List
      List results = ProjectService.getInstance().getClientProjectSearch(status,id,projectNumber, product, productDescription, notes, invoicing, deliveryFrom, deliveryTo);
       // List results=null;
        //place results in attribute for displaying in jsp
        request.setAttribute("results", results);
        request.getSession(false).setAttribute("sortedProjectResults", results) ;

        //place result size into request
        if(results != null) {
            request.setAttribute("clientProjectSearchResults", String.valueOf(results.size()));
            request.setAttribute("projectSearchInvoicing", invoicing);
            request.setAttribute("status", status);

        }
        else {
            request.setAttribute("clientProjectSearchResults", "0");
        }

	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
