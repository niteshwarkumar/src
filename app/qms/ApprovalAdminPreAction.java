/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import app.project.Project;
import app.project.ProjectService;
import app.quote.Quote1;
import app.quote.QuoteService;
import app.resource.Resource;
import app.resource.ResourceService;
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
public class ApprovalAdminPreAction extends Action {

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
//    Quality
//Management
//Resource
//Product
//Risk
//Measure
//MRM
    List qmsLibraryTabList = new ArrayList();
    qmsLibraryTabList.add("Quality");
    qmsLibraryTabList.add("Management");
    qmsLibraryTabList.add("Resource");
    qmsLibraryTabList.add("Product");
    qmsLibraryTabList.add("Risk");
    qmsLibraryTabList.add("Measure");

    docMap.put("Quality", "Quality Management System");
    docMap.put("Management", "Management Responsibility");
    docMap.put("Resource", "Resource Management");
    docMap.put("Product", "Product Realization");
    docMap.put("Risk", "Risk Management");
    docMap.put("Measure", "Measure, Analysis and Improvement");
    
    List currentUserList = UserService.getInstance().getUserListCurrent();
    for(int q =0;q<qmsLibraryTabList.size();q++){
      String qmsLibrary = (String) qmsLibraryTabList.get(q);
       docList.add(qmsLibrary);
//       docMap.put(qmsLibrary, qmsLibrary);
      for(int u =0;u<currentUserList.size();u++){
        User currentUser = (User) currentUserList.get(u);
        try {
          Approval approval = QMSService.getInstance().getSingleApproval(currentUser.getUserId(), qmsLibrary);
        } catch (Exception e) {
          Approval approval = new Approval();
          approval.setMainTab(qmsLibrary);
        //  approval.setDocName(qmsLibrary.getTitle());
        //  approval.setDocType(qmsLibrary.getMainTab());
          approval.setUserId(currentUser.getUserId());
     //     approval.setUser(currentUser.getUsername());
          QMSServiceAddUpdate.getInstance().addUpdateApproval(approval);
        }
        
     }
    }

//    List approvalList = QMSService.getInstance().getApproval();
  /*
delete this   
   */
//    List resList = ResourceService.getInstance().getResourceList();
//    for(int i =0; i<resList.size();i++){
//      Resource res = (Resource) resList.get(i);
////      res.setResume(res.getResume());
//      res.setNote(res.getNote());
//      //System.out.println(res.getResourceId()+"====================>"+res.getNote());
//    }
//     resList = ProjectService.getInstance().getProjectList();
//    for(int i =0; i<resList.size();i++){
//      Project res = (Project) resList.get(i);
//     
//      res.setNotes(res.getNotes());
//    }
//     resList = QuoteService.getInstance().getQuoteList();
//    for(int i =0; i<resList.size();i++){
//      Quote1 res = (Quote1) resList.get(i);
//     
//      res.setNote(res.getNote());
//    }
//            

    
//    request.setAttribute("approvalList", approvalList);
    request.setAttribute("docList", docList);
    request.setAttribute("docMap", docMap);
    request.setAttribute("userList", currentUserList);

    return (mapping.findForward("Success"));
  }
}
