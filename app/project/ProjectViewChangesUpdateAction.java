//ProjectViewAccountingUpdateAction.java updates the change
//objects for this project

package app.project;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleException;
import org.apache.struts.util.MessageResources;
import org.apache.commons.beanutils.PropertyUtils;
import java.util.*;
import java.text.*;
import app.user.*;
import app.db.*;
import app.workspace.*;
import app.admin.*;
import app.security.*;
import app.project.*;
import app.standardCode.*;
import org.apache.struts.validator.*;
import app.client.*;


public final class ProjectViewChangesUpdateAction extends Action {


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
        
        //PRIVS check that correct user is viewing this page 
        if(!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "viewAccountTab")) {
            return (mapping.findForward("accessDenied"));
        }//END PRIVS check that correct user is viewing this page
         
        //PRIVS check that correct user is viewing this page 
        if(!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "updateAccountTab")) {
            return (mapping.findForward("accessDenied"));
        }//END PRIVS check that correct user is viewing this page
        
                
        //get current project ID
        
        //id from request attribute
        String projectId = (String) request.getAttribute("projectViewId");
        
        //id from cookie
        if(projectId == null) {
            projectId = StandardCode.getInstance().getCookie("projectViewId", request.getCookies());	
        }
        
        Integer id = Integer.valueOf(projectId);
        
        
        //get project with id from database
        Project p = ProjectService.getInstance().getSingleProject(id);
        
        //get changes to update
        DynaValidatorForm qvg = (DynaValidatorForm) form;
        Change1[] change1s = (Change1[]) qvg.get("change1s");    
        
        //process each updated change to db
        for(int i = 0; i < change1s.length; i++) {
                Change1 c = change1s[i];
                
                //START update change dates
                String changeDateChanged = request.getParameter("dateChanged" + String.valueOf(i));
                if(changeDateChanged.length() >= 1) {
                    c.setChangeDate(DateService.getInstance().convertDate(changeDateChanged).getTime());
                }else{
                c.setChangeDate(null);
                }
                //END update change dates
            
                //set the check box to correct boolean value
                if(request.getParameter("change1s[" + String.valueOf(i) + "].approved") != null) {
                    change1s[i].setApproved(true);
                }
                else {
                    change1s[i].setApproved(false);
                }
                
                //upload quality changes to db
                ProjectService.getInstance().updateChange(c);
        }
        
        String amountInvoiced  = (String) request.getParameter("amountInvoicedForm");
        p.setTotalAmountInvoiced(amountInvoiced);
        User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
        p.setLastModifiedBy(u.getFirstName() + " " + u.getLastName());
        p.setLastModifiedDate(new Date(System.currentTimeMillis()));
        ProjectService.getInstance().updateProject(p);
        
        ClientInvoice[] cis = (ClientInvoice[]) qvg.get("clientInvoices");    
        
        //process each updated change to db
        for(int i = 0; i < cis.length; i++) {
                ClientInvoice c = cis[i];
                
                //START update dates
                String changeDateChanged = request.getParameter("requestDate" + String.valueOf(i));
                if(changeDateChanged.length() >= 1) {
                    c.setInvoiceRequestDate(DateService.getInstance().convertDate(changeDateChanged).getTime());
                }else{
                    c.setInvoiceRequestDate(null);
                }
                String paidDate = request.getParameter("paidDate" + String.valueOf(i));
                if(paidDate.length() >= 1) {
                    c.setInvoicePaidDate(DateService.getInstance().convertDate(paidDate).getTime());
                }else{
                    c.setInvoicePaidDate(null);
                }
                //END update dates           
                
                //upload to db
                AdminService.getInstance().updateObject(c);
        }
                
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
