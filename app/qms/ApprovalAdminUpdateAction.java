/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import app.security.SecurityService;
import app.user.User;
import app.user.UserService;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.hibernate.HibernateException;
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
public class ApprovalAdminUpdateAction extends Action {

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

    List approvalList = QMSService.getInstance().getAdminApproval();
    for (int i = 0; i < approvalList.size(); i++) {
      Approval approval = (Approval) approvalList.get(i);
      String approvalReq = request.getParameter("" + approval.getId());

      try {
        if (approvalReq.equalsIgnoreCase("on") && approval.isStatus() == false) {
          approval.setStatus(true);
          setDisabledStatus(true, approval);

          QMSServiceAddUpdate.getInstance().addUpdateApproval(approval);
        }
      } catch (Exception e) {
        if ( approval.isStatus() == true) {
          approval.setStatus(false);
          setDisabledStatus(false, approval);
          QMSServiceAddUpdate.getInstance().addUpdateApproval(approval);
        }
      }



    }


    return (mapping.findForward("Success"));
  }

  private void setDisabledStatus(boolean status, Approval approval) throws HibernateException {
    List qmsLibraryList = QMSService.getInstance().getQMSLibraryDocumentByTabs(approval.getMainTab(), " and type = 'C' ");
    for (int iq = 0; iq < qmsLibraryList.size(); iq++) {
      try {
        QMSLibrary qmsLibrary = (QMSLibrary) qmsLibraryList.get(iq);
        Approval setapproval = QMSService.getInstance().getSingleApproval(approval.getUserId(), qmsLibrary.getId());
        setapproval.setDisable(status);
        QMSServiceAddUpdate.getInstance().addUpdateApproval(setapproval);
      } catch (Exception e) {
      }

    }
  }
}
