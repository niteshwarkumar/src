/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.admin;

import app.client.Client;
import app.client.ClientContact;
import app.client.ClientService;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
public class MailingListClientUpdateAction extends Action {

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
    Integer mailingId = Integer.parseInt(request.getParameter("mailingId"));
      MailingList ml = null;
     String mailName = request.getParameter("mailName");
      if(mailingId == 0){ 
      ml = new MailingList();
      ml.setMailName(mailName);
      ml.setMailCategory("C");
      mailingId = AdminService.getInstance().saveMailingList(ml);
    }else{
     ml = AdminService.getInstance().getSingleMailingList(mailingId);
    
    if (!mailName.equals(ml.getMailName())) {
      ml.setMailName(mailName);
      AdminService.getInstance().saveMailingList(ml);
    }}

    List mailingList = AdminService.getInstance().getMailingList(mailingId,"C");
    List mailingIdList = new ArrayList();
    if (null != mailingList) {
      for (int i = 0; i < mailingList.size(); i++) {
        MailingListClient mlc = (MailingListClient) mailingList.get(i);
        mailingIdList.add(mlc.getClientId());
      }
    }


    List clientList = ClientService.getInstance().getClientList();
    for (int i = 0; i < clientList.size(); i++) {
      Client c = (Client) clientList.get(i);

      Set contacts = c.getContacts();
      if (contacts != null & contacts.size() > 0) {

        for (Iterator iter = contacts.iterator(); iter.hasNext();) {
          ClientContact cc = (ClientContact) iter.next();
          //System.out.println(request.getParameter("" + cc.getClientContactId()));
//          List mailingList = AdminService.getInstance().getMailingList(LOCALE_KEY)
          if (null != request.getParameter("" + cc.getClientContactId())) {
//              MailingListClient mlc = new MailingListClient();
            if (!mailingIdList.contains(cc.getClientContactId()) && request.getParameter("" + cc.getClientContactId()).equalsIgnoreCase("on")) {
//              MailingListClient mlc =  AdminService.getInstance().getSingleClientMailingList(cc.getClientContactId());
              MailingListClient mlc = new MailingListClient();
              mlc.setClientId(cc.getClientContactId());
              mlc.setMailId(mailingId);
              AdminService.getInstance().saveMailingListClient(mlc);

            }
          } else if (request.getParameter("" + cc.getClientContactId()) == null) {
            if (mailingIdList.contains(cc.getClientContactId())) {
              MailingListClient mlc = AdminService.getInstance().getSingleClientMailingList(cc.getClientContactId());
              AdminService.getInstance().deleteMailingListClient(mlc);
            }
          }

        }
      }
    }
    request.setAttribute("mailingId", mailingId);
    return (mapping.findForward("Success"));
  }
}
