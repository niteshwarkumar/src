//ReportsList9Action.java gets the display
//ready for reporting info, such as graphs, dollar totals, etc.

package app.reports;

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
import org.apache.struts.validator.*;
import java.util.*;
import java.text.*;
import app.user.*;
import app.client.*;
import app.project.*;
import app.standardCode.*;
import app.db.*;
import app.workspace.*;
import app.security.*;


public final class ReportsList9Action extends Action {


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
        
        //PRIVS check that reports user is viewing this page 
        if(!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "reports")) {
            return (mapping.findForward("accessDenied"));
        }//END PRIVS check that reports user is viewing this page
        
        //START get report criteria
        String amountAString = request.getParameter("amountA");
        Double amountA;
        if(amountAString == null) { //default to $0.00
            amountA = new Double(0.0);
        }
        else { 
            amountAString = amountAString.replaceAll(",",""); //remove comma
            amountA = new Double(amountAString);
        }
        
        String amountBString = request.getParameter("amountB");
        Double amountB;
        if(amountBString == null) { //default to $0.00
            amountB = new Double(0.0);
        }
        else { 
            amountBString = amountBString.replaceAll(",",""); //remove comma
            amountB = new Double(amountBString);
        }
        
        String dateCString = request.getParameter("dateC");
        Date dateC = new Date();
        if(dateCString != null && dateCString.length() > 0) {
            dateC = DateService.getInstance().convertDate(dateCString).getTime();
        }
        
        String dateDString = request.getParameter("dateD");
        Date dateD = new Date();
        if(dateDString != null && dateDString.length() > 0) {
            dateD = DateService.getInstance().convertDate(dateDString).getTime();
        }
        //END get report criteria
        
        
        //run report search
        List results = ReportsService.getInstance().runReport9(amountA, amountB, dateC, dateD);
        
        //place results and criteria into request
        request.setAttribute("amountA", StandardCode.getInstance().formatDouble(amountA));
        request.setAttribute("amountB", StandardCode.getInstance().formatDouble(amountB));
        request.setAttribute("dateC", DateFormat.getDateInstance(DateFormat.SHORT).format(dateC));
        request.setAttribute("dateD", DateFormat.getDateInstance(DateFormat.SHORT).format(dateD));
        request.setAttribute("results", results);
        String resultsSize;
        if(results != null) {
            resultsSize = "<br>Found " + String.valueOf(results.size()) + " Projects";
        }
        else {
            resultsSize = "<br>Found " + "0" + " Projects";
        }
        double total = 0.0;
        if(results != null) {
            for(ListIterator iter = results.listIterator(); iter.hasNext();) {
                Project p = (Project) iter.next();
                if(p.getProjectAmount() != null) {
                    total += p.getProjectAmount().doubleValue();
                }
            }
        }
        request.setAttribute("total", StandardCode.getInstance().formatDouble(new Double(total)));
        
        request.setAttribute("title", "All Projects with a Client Fee between $" + StandardCode.getInstance().formatDouble(amountA) + " and $" + StandardCode.getInstance().formatDouble(amountB) + " from " + DateFormat.getDateInstance(DateFormat.SHORT).format(dateC) + " to " + DateFormat.getDateInstance(DateFormat.SHORT).format(dateD) + resultsSize);
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
