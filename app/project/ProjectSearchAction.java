//ProjectSearchAction.java collects the search criteria from the form
//and performs the search on projects

package app.project;

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
import java.util.*;
import app.security.*;
import app.standardCode.StandardCode;


public final class ProjectSearchAction extends Action {


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
	
        //values for search
        DynaValidatorForm ps = (DynaValidatorForm) form;
        
        String status = (String) ps.get("status");
        String companyName = (String) ps.get("companyNameProjectSearch");
        String projectNumber = (String) ps.get("projectNumberProjectSearch");
        String product = (String) ps.get("productProjectSearch");
        String productDescription = (String) ps.get("productDescriptionProjectSearch");
        String notes = (String) ps.get("notesProjectSearch");
        String invoicing = (String) ps.get("projectSearchInvoicing");
        String deliveryDateFrom = (String) ps.get("deliveryDateFrom");
        String deliveryDateTo = (String) ps.get("deliveryDateTo");
        String startDateFrom = (String) ps.get("startDateFrom");
        String startDateTo = (String) ps.get("startDateTo");
        String srcLang = (String) ps.get("sourceProjectSearch");
        String tgtLang = (String) ps.get("targetProjectSearch");
        String pm = (String) ps.get("projectManager");
        String ae = (String) ps.get("accountManager");
        String sales = (String) ps.get("sales");
        String postProjectReview = (String) ps.get("postProjectReview");
        
        String cancelled = request.getParameter("projectSearchCancelled");

               
        Date deliveryFrom = null;
        Date deliveryTo = null;
        
        if(deliveryDateFrom!=null){
            if(!deliveryDateFrom.equals("")){
            deliveryFrom = StandardCode.getInstance().formatDate(deliveryDateFrom,"MM/dd/yyyy", "yyyy-MM-dd");
        }

        }
        
        if(deliveryDateTo!=null){
            if(!deliveryDateTo.equals("")){
            deliveryTo = StandardCode.getInstance().formatDate(deliveryDateTo,"MM/dd/yyyy", "yyyy-MM-dd");

         
            }
        }
        
         Date startFrom = null;
        Date startTo = null;
        
        if(startDateFrom!=null){
            if(!startDateFrom.equals("")){
            startFrom = StandardCode.getInstance().formatDate(startDateFrom,"MM/dd/yyyy", "yyyy-MM-dd");

            }}
        
        if(startDateTo!=null){
             if(!startDateTo.equals("")){
            startTo = StandardCode.getInstance().formatDate(startDateTo,"MM/dd/yyyy", "yyyy-MM-dd");

             }}
        //perform search and store results in a List
        List results = ProjectService.getInstance().getProjectSearch(status, companyName, projectNumber, product, productDescription, notes, invoicing, deliveryFrom, deliveryTo, startFrom, startTo, cancelled,srcLang,tgtLang,pm,ae,sales,postProjectReview);
        List results1=null;


        request.setAttribute("results", results);
        request.getSession(false).setAttribute("sortedProjectResults", results) ;
        
        //place result size into request
        if(results != null) {

            request.setAttribute("projectSearchResults", String.valueOf(results.size()));
            request.setAttribute("projectSearchInvoicing", invoicing);
            request.setAttribute("status", status);
            
        }
        else {
            request.setAttribute("projectSearchResults", "0");
        }
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
