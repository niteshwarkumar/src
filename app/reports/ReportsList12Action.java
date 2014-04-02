//ReportsList12Action.java gets the display
//ready for reporting info, such as graphs, dollar totals, etc.

package app.reports;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionError;
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


public final class ReportsList12Action extends Action {


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
        String setRangeA = request.getParameter("setRangeA");
        if(setRangeA == null) { //default to lowest range
            setRangeA = "0 - 2,000";
        }
                
        String dateBString = request.getParameter("dateB");
        Date dateB = new Date();
        if(dateBString != null && dateBString.length() > 0) {
            dateB = DateService.getInstance().convertDate(dateBString).getTime();
        }
        
        String dateCString = request.getParameter("dateC");
        Date dateC = new Date();
        if(dateCString != null && dateCString.length() > 0) {
            dateC = DateService.getInstance().convertDate(dateCString).getTime();
        }
        //END get report criteria
        
        //find amounts
        String setRangeATemp = setRangeA;
        setRangeATemp = setRangeATemp.replaceAll("$", "");
        setRangeATemp = setRangeATemp.replaceAll(",", "");
        String[] ranges = setRangeATemp.split(" ");
        Double amountA = Double.valueOf(ranges[0]);
        Double amountB = new Double(0.0);
        if(!ranges[2].equals("over")) {
            amountB = Double.valueOf(ranges[2]);
        }
                
        //run report search
        List results = new ArrayList();
        if(!ranges[2].equals("over")) {
            results = ReportsService.getInstance().runReport9(amountA, amountB, dateB, dateC);
        }
        else {
            results = ReportsService.getInstance().runReport9Over(amountA, dateB, dateC);
        }
        
        
        //place results and criteria into request
        request.setAttribute("setRangeA", setRangeA);
        request.setAttribute("dateB", DateFormat.getDateInstance(DateFormat.SHORT).format(dateB));
        request.setAttribute("dateC", DateFormat.getDateInstance(DateFormat.SHORT).format(dateC));
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
        
        request.setAttribute("title", "All Projects with a Client Fee within Set Range $ " + setRangeA + " from " + DateFormat.getDateInstance(DateFormat.SHORT).format(dateB) + " to " + DateFormat.getDateInstance(DateFormat.SHORT).format(dateC) + resultsSize);
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
