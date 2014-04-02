//ClientViewProjectHistory.java gets the current client from the
//db and places it in an attribute for display.  Client is related
//to project by: one client has one or more projects.
//It then updates its tab location into a cookie.

package app.extjs.actions;
import app.extjs.helpers.QuoteHelper;
import app.quote.Quote1;
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
import app.security.*;
import app.standardCode.*;
import app.user.*;
import java.io.PrintWriter;
import org.json.JSONArray;
import org.json.JSONObject;



public final class Client_QuotesSearchRejectAction extends Action {


    // ----------------------------------------------------- Instance Variables


    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log =
        LogFactory.getLog("org.apache.struts.webapp.Example");



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
//String status="approved";
        //get current client ID

        //id from request attribute
        String clientId = (String) request.getAttribute("clientViewId");

        //id from cookie
        if(clientId == null) {
            //clientId = StandardCode.getInstance().getCookie("clientViewId", request.getCookies());
             String username = StandardCode.getInstance().getCookie("storedUsername", request.getCookies());
            User user = UserService.getInstance().getSingleUser(username);
            clientId = user.getID_Client().toString();

        }

   
        response.addCookie(StandardCode.getInstance().setCookie("clientViewTab", "Project History"));

        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        List temp = null;
        Calendar cal = new GregorianCalendar();
        String year = request.getParameter("projectYear");
        if (year.equalsIgnoreCase("")) {
            year = "" + cal.get(Calendar.YEAR);
        }
        if (year.equalsIgnoreCase("all")) {
            temp = QuoteHelper.getClientRejectQuoteListForClient(clientId);
        } else {
            temp = QuoteHelper.getClientRejectQuoteListForClient(clientId, year);
        }
//        List temp = QuoteHelper.getClientRejectQuoteListForClient(clientId);
        ArrayList quoteHistory = new ArrayList();
        for(ListIterator iter = temp.listIterator(); iter.hasNext();) {
                Quote1 q = (Quote1) iter.next();
                JSONObject jo = QuoteHelper.ClientQuoteToJson2(q);
                quoteHistory.add(jo);
            }
      // System.out.println(actResponse.toXML());
        System.out.println("quoteHistory"+quoteHistory);
        PrintWriter out = response.getWriter();
        out.println(new JSONArray(quoteHistory.toArray()));
        out.flush();

           return null;
    }

}
