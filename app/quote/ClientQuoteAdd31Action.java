/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.quote;

import app.extjs.helpers.QuoteHelper;
import app.project.Project;
import app.project.ProjectService;
import app.project.SourceDoc;
import app.project.TargetDoc;
import app.security.SecurityService;
import app.user.User;
import app.user.UserService;
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Neil
 */
public class ClientQuoteAdd31Action extends Action{
    @Override
     public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

	// Extract attributes we will need
	org.apache.struts.util.MessageResources messages = getResources(request);

	// save errors
	ActionMessages errors = new ActionMessages();

        //START check for login (security)
        if(!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)

        //values for adding a new source
        //DynaValidatorForm qae4 = (DynaValidatorForm) form;

       // String sourceLanguage = (String) (qae4.get("sourceLanguage"));
        String[] sourceLanguage = request.getParameterValues("sourceLanguage");
        String[] targetLanguage = request.getParameterValues("targetLanguages");

        String[] mainTarget  = request.getParameterValues("mainTarget");
        String[] mainSrc  = request.getParameterValues("mainSrc");
        //need the project to add this source
        //String projectId = StandardCode.getInstance().getCookie("projectAddId", request.getCookies());
        String projectId = request.getParameter("projectViewId");
        Integer productId=Integer.parseInt(request.getParameter("productId"));
         System.out.println("productIdproductIdproductId"+productId);
        //System.out.println("gggggggggggggggggggggggggggggggggggggggggggg"+request.getParameter("id"));


        Client_Quote newQA=QuoteService.getInstance().getSingleClient_Quote(new Integer(request.getParameter("quoteViewId")));
        Quote1 newQ = QuoteService.getInstance().getSingleQuote(newQA.getQuote_ID());
        Project p = ProjectService.getInstance().getSingleProject(newQ.getProject().getProjectId());

        System.out.println("hi");
//int qnum=newQ.getQuote1Id();
     //     int qnum= Integer.parseInt(request.getSession(false).getAttribute("ClientQuoteId").toString());
     //    System.out.println(qnum);

         // getSingleClient_Quote(Integer id)
       // Client_Quote newQA = QuoteService.getInstance().getSingleClientQuoteFromProduct(newQ.getQuote1Id(),productId);
int z=0;
//HttpSession session1 = request.getSession(false);
                 //session1.setAttribute("quoteViewId", request.getParameter("quoteViewId"));
HttpSession session = request.getSession(false);
        session.setAttribute("cid",newQA.getId());

         System.out.println(request.getSession(false).getAttribute("cid").toString());

        //Project p = ProjectService.getInstance().getSingleProject(Integer.valueOf(projectId));

        //new SourceDoc object for this quote
        SourceDoc sd = new SourceDoc(new HashSet());

        /// Disabled this option for checking --Nitesh 29-03

        //ProjectHelper.unlinkSourcesAndTargets(p);
        QuoteHelper.clientUnlinkSourcesAndTargets(newQ,newQA.getId());
  //JSONObject jo = new JSONObject();
  //   Client_Quote ur = (Client_Quote) newQA.get(z);
  // System.out.println("mainTarget="+mainTarget);
  //update values
        //System.out.println("project id="+p.getProjectId());
        if(mainSrc!=null)
        for(int i=0; i<mainSrc.length;i++){
            sd.setLanguage(mainSrc[i]);
            System.out.println("   sd.setLanguage(mainSrc[i]);"+mainSrc[i]);
            //add SourceDoc to db building one-to-many relationship between project and source
            //Integer sdId = ProjectService.getInstance().addSourceWithProject(p, sd);
           Integer sdId = ProjectService.getInstance().addSourceWithProject(p, sd,newQA,newQ);

            //System.out.println("linking source id="+sd.getSourceDocId());
        if(targetLanguage!=null)
            for(int j = 0; j < targetLanguage.length; j++) {
                //target language's new object
                TargetDoc td = new TargetDoc(new HashSet(), new HashSet(), new HashSet(), new HashSet());
                td.setLanguage(targetLanguage[j]);
                //System.out.println("linking target id="+td.getTargetDocId());
                //link this target Doc to the source Doc; add new target Doc to db
                Integer x = ProjectService.getInstance().linkSourceDocTargetDoc(sd, td);
            }

        if(mainTarget!=null)
            for(int j = 0; j < mainTarget.length; j++) {
                //target language's new object
                if(!mainTarget[j].equalsIgnoreCase("t")){
                System.out.println("main Targetttttttttt::::"+mainTarget[j]);
                TargetDoc td = new TargetDoc(new HashSet(), new HashSet(), new HashSet(), new HashSet());
                td.setLanguage(mainTarget[j]);

                //link this target Doc to the source Doc; add new target Doc to db
                Integer x = ProjectService.getInstance().linkSourceDocTargetDoc(sd, td);
                }
            }
        }
//update values
    if(sourceLanguage!=null)
        for(int i=0; i<sourceLanguage.length;i++){
            sd.setLanguage(sourceLanguage[i]);
            System.out.println("   sd.setLanguage(mainSrc[i]);"+sourceLanguage[i]);
            //add SourceDoc to db building one-to-many relationship between project and source
              //QuoteService.getInstance().clientAddSourceWithQuote(newQ, sd,newQA) ;
            Integer sdId = ProjectService.getInstance().addSourceWithProject(p, sd,newQA,newQ);
          if(targetLanguage!=null)
            for(int j = 0; j < targetLanguage.length; j++) {
                //target language's new object
                TargetDoc td = new TargetDoc(new HashSet(), new HashSet(), new HashSet(), new HashSet());
                td.setLanguage(targetLanguage[j]);

                //link this target Doc to the source Doc; add new target Doc to db
                Integer x = ProjectService.getInstance().linkSourceDocTargetDoc(sd, td);
            }
           if(mainTarget!=null)
            for(int j = 0; j < mainTarget.length; j++) {
                //target language's new object
                TargetDoc td = new TargetDoc(new HashSet(), new HashSet(), new HashSet(), new HashSet());
                td.setLanguage(mainTarget[j]);

                //link this target Doc to the source Doc; add new target Doc to db
                Integer x = ProjectService.getInstance().linkSourceDocTargetDoc(sd, td);
            }


        }





User u=UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
String userType=u.getuserType();
if(userType==null||userType.equalsIgnoreCase("admin"))
        return (mapping.findForward("Success"));
else return (mapping.findForward("ClientSuccess"));
    }

}
