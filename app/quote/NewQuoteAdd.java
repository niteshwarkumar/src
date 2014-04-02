/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.quote;

import app.client.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpSession;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import javax.servlet.http.Cookie;
import org.apache.struts.validator.*;

/**
 *
 * @author Neil
 */
public class NewQuoteAdd extends Action {

//     private Log log =
    //    LogFactory.getLog("org.apache.struts.webapp.Example");
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {




        DynaValidatorForm qae1 = (DynaValidatorForm) form;
        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
        Client c;
        String clientViewId = (String) request.getAttribute("clientViewId");

        if (clientViewId == null) {
            String Company_name = (String) (qae1.get("client"));
            try {
                c = ClientService.getInstance().getSingleClient(Integer.parseInt(Company_name));//String.valueOf(quoteId))

            } catch (Exception e) {
                c = ClientService.getInstance().getSingleClient(u.getId_client());
            }

            Cookie cookie1 = new Cookie("clientViewId", Company_name);
            cookie1.setMaxAge(365 * 24 * 60 * 60);
            response.addCookie(cookie1);
            System.out.println("StandardCode.getInstance().getCookie(clientViewId, request.getCookies())" + StandardCode.getInstance().getCookie("clientViewId", request.getCookies()));

            Integer quoteId = 0;
            request.setAttribute("clientViewId1", String.valueOf(c.getClientId()));
            HttpSession session = request.getSession(false);
            session.setAttribute("clientViewId", String.valueOf(c.getClientId()));
            session.setAttribute("projectManager", request.getParameter("projectManager"));

            session.setAttribute("contactId", request.getParameter("contact"));
            session.setAttribute("quoteViewId", String.valueOf(quoteId));
            request.setAttribute("quoteViewId", String.valueOf(quoteId));

            System.out.println("ClientIddddddddddddddddddddd" + request.getAttribute("clientViewId"));

        } else {
            c = ClientService.getInstance().getSingleClient(Integer.valueOf(clientViewId));
        }

        return (mapping.findForward("Success"));


    }
}
