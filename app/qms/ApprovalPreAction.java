/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import app.security.SecurityService;
import app.user.User;
import app.user.UserService;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
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
public class ApprovalPreAction extends Action {

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
    List docList = new ArrayList();
    Map docMap = new HashMap();
    
    List qmsLibraryList = QMSService.getInstance().getQMSLibraryDocumentByTabs("Matrix", " and type = 'C' order by mainTab");
    List currentUserList = UserService.getInstance().getUserListCurrent();
    for(int q =0;q<qmsLibraryList.size();q++){
      QMSLibrary qmsLibrary = (QMSLibrary) qmsLibraryList.get(q);
       docList.add(qmsLibrary.getId());
       docMap.put(qmsLibrary.getId(), qmsLibrary.getDocId()+"-"+qmsLibrary.getVersion()+"  "+qmsLibrary.getTitle());
      for(int u =0;u<currentUserList.size();u++){
        User currentUser = (User) currentUserList.get(u);
        try {
          Approval approval = QMSService.getInstance().getSingleApproval(currentUser.getUserId(), qmsLibrary.getId());
        } catch (Exception e) {
          Approval approval = new Approval();
          approval.setDocId(qmsLibrary.getId());
        //  approval.setDocName(qmsLibrary.getTitle());
        //  approval.setDocType(qmsLibrary.getMainTab());
          approval.setUserId(currentUser.getUserId());
     //     approval.setUser(currentUser.getUsername());
          QMSServiceAddUpdate.getInstance().addUpdateApproval(approval);
        }
        
     }
    }

//    List approvalList = QMSService.getInstance().getApproval();
  
    

    
//    request.setAttribute("approvalList", approvalList);
    request.setAttribute("docList", docList);
    request.setAttribute("docMap", docMap);
    request.setAttribute("userList", currentUserList);

    return (mapping.findForward("Success"));
  }
}
