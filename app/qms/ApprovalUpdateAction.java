/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import app.security.SecurityService;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import java.util.ArrayList;
import java.util.Date;
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
public class ApprovalUpdateAction extends Action {

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
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
    Date currentDate = new Date();
    List approvalList = new ArrayList();
    if(StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "admin")) {
      approvalList = QMSService.getInstance().getApproval();
    }else{
      approvalList = QMSService.getInstance().getApproval(u.getUserId());
    }
    for (int i = 0; i < approvalList.size(); i++) {
      Approval approval = (Approval) approvalList.get(i);
      String approvalReq = request.getParameter(""+approval.getId());
      if(null != approval.getDocId()){
     
      try {
        if(approvalReq.equalsIgnoreCase("on")&& approval.isStatus()== false){
        approval.setStatus(true);
        approval.setUpdateDate(currentDate);
        QMSServiceAddUpdate.getInstance().addUpdateApproval(approval);
      }
      } catch (Exception e) {
        if(approval.isStatus()== true){approval.setStatus(false);
        approval.setUpdateDate(currentDate);
        QMSServiceAddUpdate.getInstance().addUpdateApproval(approval);}
      }
      
      }
      
    }


    return (mapping.findForward("Success"));
  }
}
