/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.admin;

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
import app.user.User;
import app.user.UserService;

/**
 *
 * @author Niteshwar
 */
public class adminTickerUpdateAction extends Action {

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
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        String[] datafromJsp = null;
       String boxNo = request.getParameter("boxNo");
        //END check for login (security)
        if (Integer.parseInt(boxNo) == 10) {
            datafromJsp = request.getParameterValues("email1");
        }
        if (Integer.parseInt(boxNo) == 11) {
            datafromJsp = request.getParameterValues("email2");
        }
        if (Integer.parseInt(boxNo) == 20) {
            datafromJsp = request.getParameterValues("email3");
        }
        if (Integer.parseInt(boxNo) == 21) {
            datafromJsp = request.getParameterValues("email4");
        }
        if (Integer.parseInt(boxNo) == 30) {
            datafromJsp = request.getParameterValues("email5");
        }
        if (Integer.parseInt(boxNo) == 31) {
            datafromJsp = request.getParameterValues("email6");
        }

        Integer box = Integer.parseInt(boxNo) / 10;
        box = box * 10;


        for (int i = 0; i < datafromJsp.length; i++) {
            try {
                User user = UserService.getInstance().getSingleUser(Integer.parseInt(datafromJsp[i]));
                Ticker ticker = AdminService.getInstance().getSingleTicker(user.getUserId(), box);
                if (ticker == null) {
                    ticker = AdminService.getInstance().getSingleTicker(user.getUserId(), box + 1);
                }
                if (ticker == null) {
                    ticker = new Ticker();
                }

                ticker.setuserId(user.getUserId());
                ticker.setUserEmail(user.getWorkEmail1());
                ticker.setboxNumber(Integer.parseInt(boxNo));

                AdminService.getInstance().updateTicker(ticker);



            } catch (Exception e) {
            }

        }

        // String datafromJsp=request.getParameter("email1");



        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
