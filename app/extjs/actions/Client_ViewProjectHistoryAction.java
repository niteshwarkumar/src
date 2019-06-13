//ClientViewProjectHistory.java gets the current client from the
//db and places it in an attribute for display.  Client is related
//to project by: one client has one or more projects.
//It then updates its tab location into a cookie.
package app.extjs.actions;

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
import java.util.*;
import app.project.*;
import app.security.*;
import app.standardCode.*;
import app.user.*;
import java.io.PrintWriter;
import org.json.JSONArray;
import org.json.JSONObject;

public final class Client_ViewProjectHistoryAction extends Action {

    // ----------------------------------------------------- Instance Variables
    /**
     * The
     * <code>Log</code> instance for this application.
     */
    private Log log =
            LogFactory.getLog("org.apache.struts.webapp.Example");

    // --------------------------------------------------------- Public Methods
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an
     * <code>ActionForward</code> instance describing where and how control
     * should be forwarded, or
     * <code>null</code> if the response has already been completed.
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
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)

        //get current client ID

        //id from request attribute
        String clientId = (String) request.getAttribute("clientViewId");

        //id from cookie
        if (clientId == null) {
            //   clientId = StandardCode.getInstance().getCookie("clientViewId", request.getCookies());
            String username = StandardCode.getInstance().getCookie("storedUsername", request.getCookies());
            User user = UserService.getInstance().getSingleUser(username);
            clientId = user.getID_Client().toString();
        }
        //System.out.println("clinrt id of ClientViewProjectHistoryAction>>>>>>>>>>>>>>>>>>>>>>" + clientId);

        //Integer id = Integer.parseInt(clientId);


        //END get the client's project dollar total per year

        //place yearly totals, source and target counts into the request for display
        // request.setAttribute("dollarTotals", ProjectHelper.sumAllProjectAmounts(id));


        //place the client into an attribute for displaying in jsp
        // request.setAttribute("client", c);

        //add this client id to cookies; this will remember the last client
        //response.addCookie(StandardCode.getInstance().setCookie("clientViewId", clientId));

        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("clientViewTab", "Project History"));
        // long end1 = System.currentTimeMillis();
        ////System.out.println("old way:"+(end1-start1));
        // long start2 = System.currentTimeMillis();
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        Integer nk = 1;
        List temp = null;
        Calendar cal = new GregorianCalendar();
        String year = request.getParameter("projectYear");
        if (year.equalsIgnoreCase("")) {
            year = "" + cal.get(Calendar.YEAR);
        }
        if (year.equalsIgnoreCase("all")) {
            temp = ProjectHelper.getProjectListForClientActive(clientId);
        } else {
            temp = ProjectHelper.getProjectListForClientActive(clientId, year);
        }
        ArrayList projectHistory = new ArrayList();
        for (ListIterator iter = temp.listIterator(); iter.hasNext();) {
            Project p = (Project) iter.next();

            //System.out.println(nk + "       " + p.getNumber());
            nk++;
            JSONObject jo = ProjectHelper.ClientProjectToJson2(p);
            projectHistory.add(jo);
        }
        //System.out.println("projectHistory" + projectHistory);
        // //System.out.println(actResponse.toXML());
        PrintWriter out = response.getWriter();
        out.println(new JSONArray(projectHistory.toArray()));
        out.flush();
        // long end2 = System.currentTimeMillis();
        ////System.out.println("Finnished ClientViewProjectHistoryAction!");
        // Forward control to the specified success URI
        //return (mapping.findForward("Success"));
        return null;
    }
}
