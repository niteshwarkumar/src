/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.admin;

import app.resource.Resource;
import app.resource.ResourceContact;
import app.resource.ResourceService;
import app.security.SecurityService;
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
public class MailingListVendorUpdateAction extends Action {

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
    String filter = request.getParameter("filter");
    
    MailingList ml = null;
    String mailName = request.getParameter("mailName");
    if (mailingId == 0) {
      ml = new MailingList();
      ml.setMailName(mailName);
      ml.setMailCategory("V");
      mailingId = AdminService.getInstance().saveMailingList(ml);
    } else {
      ml = AdminService.getInstance().getSingleMailingList(mailingId);

      if (!mailName.equals(ml.getMailName())) {
        ml.setMailName(mailName);
        AdminService.getInstance().saveMailingList(ml);
      }
    }
   

    List mailingList = AdminService.getInstance().getMailingList(mailingId, "V");
    List mailingIdList = new ArrayList();
    if (null != mailingList) {
      for (int i = 0; i < mailingList.size(); i++) {
        MailingListVendor mlv = (MailingListVendor) mailingList.get(i);
        mailingIdList.add(mlv.getVendorId());
      }
    }


    List vendorList = ResourceService.getInstance().getResourceList();
    for (int i = 0; i < vendorList.size(); i++) {
      Resource r = (Resource) vendorList.get(i);
      if (null != request.getParameter("" + r.getResourceId())) {
        if (!mailingIdList.contains(r.getResourceId()) && request.getParameter("" + r.getResourceId()).equalsIgnoreCase("on")) {
          MailingListVendor mlv = new MailingListVendor();
          mlv.setVendorId("" + r.getResourceId());
          mlv.setMailId(mailingId);
          AdminService.getInstance().saveMailingListVendor(mlv);

        }
      }else if (mailingIdList.contains("" + r.getResourceId())) {
        MailingListVendor mlv = AdminService.getInstance().getSingleVendorMailingList("" + r.getResourceId());
        AdminService.getInstance().deleteMailingListVendor(mlv);
      }


      //System.out.println(r.getResourceId() + "->" + request.getParameter("" + r.getResourceId()));
      Set contacts = r.getResourceContacts();
      if (contacts != null & contacts.size() > 0) {

        for (Iterator iter = contacts.iterator(); iter.hasNext();) {
          ResourceContact cc = (ResourceContact) iter.next();
          String vendorId = r.getResourceId() + "#" + cc.getResourceContactId();
          request.getParameter("" + cc.getResourceContactId());
          //System.out.println(r.getResourceId() + "->" + request.getParameter("" + r.getResourceId()) + "||||||||||" + r.getResourceId() + "#" + cc.getResourceContactId() + "->" + request.getParameter(r.getResourceId() + "#" + cc.getResourceContactId()));
          if (null != request.getParameter(vendorId)) {
            if (!mailingIdList.contains(r.getResourceId() + "#" + cc.getResourceContactId()) && request.getParameter(r.getResourceId() + "#" + cc.getResourceContactId()).equalsIgnoreCase("on")) {
              MailingListVendor mlv = new MailingListVendor();
              mlv.setVendorId(r.getResourceId() + "#" + cc.getResourceContactId());
              mlv.setMailId(mailingId);
              AdminService.getInstance().saveMailingListVendor(mlv);

            }
          } else if (null == request.getParameter(r.getResourceId() + "#" + cc.getResourceContactId())) {

            if (mailingIdList.contains(r.getResourceId() + "#" + cc.getResourceContactId())) {
              MailingListVendor mlv = AdminService.getInstance().getSingleVendorMailingList(r.getResourceId() + "#" + cc.getResourceContactId());
              AdminService.getInstance().deleteMailingListVendor(mlv);
            }
          }

        }
      }
    }
    request.setAttribute("mailingId", mailingId);
    return (mapping.findForward("Success"));
  }
}
