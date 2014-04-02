/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.hr;

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
import java.text.*;
import app.user.*;
import app.security.*;
import app.standardCode.*;
import java.io.PrintWriter;
import org.apache.struts.validator.*;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 *
 * @author Niteshwar
 */
public class HrViewPrivateReview extends Action {


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

        //START get id of current user from either request, attribute, or cookie
        //id of user from request
	String userId = null;
	userId = request.getParameter("userViewId");

        //check attribute in request
        if(userId == null) {
            userId = (String) request.getAttribute("userViewId");
        }

        //id of user from cookie
        if(userId == null) {
            userId = StandardCode.getInstance().getCookie("userViewId", request.getCookies());
        }

        //default user to first if not in request or cookie
        if(userId == null) {
                List results = UserService.getInstance().getUserList();
                User first = (User) results.get(0);
                userId = String.valueOf(first.getUserId());
            }

        Integer id = Integer.valueOf(userId);

        //END get id of current user from either request, attribute, or cookie
         List results = new ArrayList();
        //get user to edit
        User u = UserService.getInstance().getSingleUser(id);
        Set performanceReviews = u.getPerformanceReviews();
        if(performanceReviews != null) {
        for(Iterator iter = performanceReviews.iterator(); iter.hasNext(); ) {
             PerformanceReview pr = (PerformanceReview) iter.next();
             JSONObject jo = new JSONObject();
             jo.put("desc", pr.getDescription());
             jo.put("actualDate", pr.getActualDate());
             jo.put("dueDate", pr.getDueDate());
             jo.put("filledDate", pr.getFiledDate());
             jo.put("signedDate", pr.getSignedDate());
             jo.put("id", pr.getPerformanceReviewId());
             results.add(jo);

            }
        }

         response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        // System.out.println(actResponse.toXML());
        PrintWriter out = response.getWriter();

        out.println(new JSONArray(results.toArray()));
        //request.setAttribute("blogJSArray",new JSONArray(results.toArray()));

        out.flush();

        request.setAttribute("user", u);


        //add this user id to cookies; this will remember the last user
        response.addCookie(StandardCode.getInstance().setCookie("userViewId", userId));

        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("userViewTab", "Private Info"));

	// Forward control to the specified success URI
	return (null);
    }

}
