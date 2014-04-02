/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.extjs.actions;
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
import app.standardCode.*;
import app.security.*;
import app.quote.*;
import app.user.User;
import app.user.UserService;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author Neil
 */
public class QuotePublish extends Action {


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
    @SuppressWarnings("deprecation")
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

        //default client to first if not in request or cookie
        if(quoteId == null) {
                List results = QuoteService.getInstance().getQuoteList();
                Quote1 first = (Quote1) results.get(0);
                quoteId = String.valueOf(first.getQuote1Id());
            }

        Integer id = Integer.valueOf(quoteId);

        //END get id of current quote from either request, attribute, or cookie

        //get the quote to be updated from db
        Quote1 q = QuoteService.getInstance().getSingleQuote(id);
        User u=UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
        DateFormat dateFormat= new SimpleDateFormat("MM/dd/yyyy");
Date date= new Date();
String date1=dateFormat.format(date);

        q.setPublish(true);
        q.setPublishDate(date);
        q.setPublishBy(u.getFirstName()+" "+u.getLastName());

        QuoteService.getInstance().updateQuote(q);

        QuotePriority qp=new QuotePriority();
        qp.setID_Quote1(q.getQuote1Id());
        qp.setNumber(q.getNumber());
        qp.setPriority(QuoteService.getInstance().getLastQuotePriority()+1);
        QuoteService.getInstance().addQuotePriority(qp);


    return (mapping.findForward("Success"));
    }
}
