/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.client;

import app.extjs.vo.Upload_Doc;
import app.quote.QuoteService;
import app.user.User;
import app.user.UserService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Nishika
 */
public class ClientProfileAction   extends Action {


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
				 HttpServletResponse response)throws Exception

    {
        User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
        String profileId=request.getParameter("profileId");
        String doAction=request.getParameter("doAction");
//        List<Upload_Doc> profileList = QuoteService.getInstance().getClientProfileList();
        
        List<ClientProfile> profileList = ClientService.getInstance().getClientProfileList();
        
        request.setAttribute("profileList", profileList);
        request.setAttribute("profileId", profileId);



        // Forward control to the specified success URI

        return (mapping.findForward("Success"));

    }
}

