//ClientViewProjectHistory.java gets the current client from the
//db and places it in an attribute for display.  Client is related
//to project by: one client has one or more projects.
//It then updates its tab location into a cookie.

package app.client;
import app.extjs.helpers.ProjectHelper;
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
import app.standardCode.*;



public final class ClientViewProjectHistoryAction extends Action {


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

        //get current client ID

        //id from request attribute
        String clientId = (String) request.getAttribute("clientViewId");

        //id from cookie
        if(clientId == null) {
            clientId = StandardCode.getInstance().getCookie("clientViewId", request.getCookies());
        }

       // Integer id = Integer.valueOf(clientId);

        //get the client from db
        //Client c = ClientService.getInstance().getSingleClient(id);

         Client c=null;
        if (clientId!=null && clientId.trim() != "")
        c = ClientService.getInstance().getSingleClient(Integer.parseInt(clientId));
        else
        {
        c = new Client();
        c.setClientId(0);
        }


  /*
        //START get the client's project dollar total per year
        //how many years to display
        GregorianCalendar today = new GregorianCalendar();
        today.setTime(new Date());
        int startOfApp = 2001;
        int years = today.get(GregorianCalendar.YEAR) - startOfApp;
        ArrayList[] yearsDisplay = new ArrayList[years + 1];
        //init year lists
        for(int i = 0; i < yearsDisplay.length; i++) {
            yearsDisplay[i] = new ArrayList();
        }

        //counts of source and target language per project
        ArrayList[] sourceCounts = new ArrayList[years + 1];
        for(int i = 0; i < sourceCounts.length; i++) {
            sourceCounts[i] = new ArrayList();
        }
        ArrayList[] targetCounts = new ArrayList[years + 1];
        for(int i = 0; i < targetCounts.length; i++) {
            targetCounts[i] = new ArrayList();
        }

        //add projects to list by year and accumulate yearly totals, source and target totals
        double[] totals = new double[yearsDisplay.length];
        for(Iterator iter = c.getProjects().iterator(); iter.hasNext();) {
            Project p = (Project) iter.next();
            if((p.getStartDate() != null || p.getDeliveryDate() != null) && !p.getStatus().equals("notApproved")) { //add by start or delivery date
                GregorianCalendar pDate = new GregorianCalendar();
                if(p.getStartDate() != null) {
                    pDate.setTime(p.getStartDate());
                }
                if(p.getDeliveryDate() != null) {
                    pDate.setTime(p.getDeliveryDate());
                }
                int position = pDate.get(GregorianCalendar.YEAR) - startOfApp;
                yearsDisplay[position].add(p);
                //update total
                if(p.getProjectAmount() != null) {
                    totals[position] += p.getProjectAmount().doubleValue();
                }
                //get source and target counts
                int sources = 0;
                int targets = 0;
                p = ProjectService.getInstance().getSingleProject(p.getProjectId());
                if(p.getSourceDocs() != null && p.getSourceDocs().size() > 0) {
                    for(Iterator sIter = p.getSourceDocs().iterator(); sIter.hasNext();) {
                        sources++; //count this source
                        SourceDoc sd = (SourceDoc) sIter.next();
                        if(sd.getTargetDocs() != null && sd.getTargetDocs().size() > 0) {
                            for(Iterator tIter = sd.getTargetDocs().iterator(); tIter.hasNext();) {
                                TargetDoc td = (TargetDoc) tIter.next();
                                if(!td.getLanguage().equals("All")) {
                                    targets++; //count this target, if not a placeholder for universal tasks
                                }
                            }
                        }
                    }
                }
                //add source and target counts for one project within one year
                sourceCounts[position].add(String.valueOf(sources));
                targetCounts[position].add(String.valueOf(targets));
            }
        }

        //display totals as formatted strings
        String[] dollarTotals = new String[yearsDisplay.length];
        for(int i = 0; i < totals.length; i++) {
            dollarTotals[i] = StandardCode.getInstance().formatDouble(new Double(totals[i]));
        }
        //END get the client's project dollar total per year

        //place yearly totals, source and target counts into the request for display
        request.setAttribute("dollarTotals", dollarTotals);
        request.setAttribute("yearsDisplay", yearsDisplay);
        request.setAttribute("sourceCounts", sourceCounts);
        request.setAttribute("targetCounts", targetCounts);
       */

        request.setAttribute("dollarTotals", ""+ProjectHelper.sumAllProjectAmounts(c.getClientId()));
        //place the client into an attribute for displaying in jsp
        request.setAttribute("client", c);

        //add this client id to cookies; this will remember the last client
        response.addCookie(StandardCode.getInstance().setCookie("clientViewId", clientId));

        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("clientViewTab", "Project History"));

        String projectYear=request.getParameter("projectYear");
//        String clientViewId=request.getParameter("clientViewId");
        request.setAttribute("projectYear", projectYear);
//        request.setAttribute("clientViewId", clientViewId);

	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
