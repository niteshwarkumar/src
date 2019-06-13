/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.admin;

import app.security.SecurityService;
import app.user.User;
import app.user.UserService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

/**
 *
 * @author Nishika
 */
public class MailingListClientPreAction extends Action {

  private Log log = LogFactory.getLog("org.apache.struts.webapp.Example");

  public ActionForward execute(ActionMapping mapping,
          ActionForm form,
          HttpServletRequest request,
          HttpServletResponse response) throws Exception {


    // Extract attributes we will nee

    MessageResources messages = getResources(request);

    // save errors
    ActionMessages errors = new ActionMessages();

    //START check for login (security)
    if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
      return (mapping.findForward("welcome"));
    }
    User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
    Integer mailingId = 0;
    if (null != request.getAttribute("mailingId")) {
      mailingId = (Integer) request.getAttribute("mailingId");
    } else {
      mailingId = Integer.parseInt(request.getParameter("mailingId"));
    }

    List mailingList = AdminService.getInstance().getMailingList(mailingId,"C");
    List mailingIdList = new ArrayList();
    if (null != mailingList) {
      for (int i = 0; i < mailingList.size(); i++) {
        MailingListClient mlc = (MailingListClient) mailingList.get(i);
        mailingIdList.add(mlc.getClientId());
      }
    } else {
      mailingIdList = null;
    }
    request.setAttribute("mailingIdList", mailingIdList);
    request.setAttribute("mailingId", mailingId);
    return (mapping.findForward("Success"));
  }
}
