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
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

/**
 *
 * @author Niteshwar
 */
public class QuoteWizard2Action extends Action{
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
        //String srcComment = request.getParameter("srcComment");
        //need the project to add this source       
       
        DynaValidatorForm qvpr = (DynaValidatorForm) form;
        String srcComment = (String) (qvpr.get("srcComment"));


        Quote1 newQ = QuoteService.getInstance().getSingleQuote(new Integer(request.getParameter("quoteViewId")));
        
        Project p = ProjectService.getInstance().getSingleProject(newQ.getProject().getProjectId());

          int qnum= Integer.parseInt(request.getSession(false).getAttribute("clientQuoteViewId").toString());
         //System.out.println(qnum);

         // getSingleClient_Quote(Integer id)
        Client_Quote newQA = QuoteService.getInstance().getSingleClient_Quote(qnum);
        int z=0;
       newQA.setSrcComment(srcComment);
       QuoteService.getInstance().updateClientQuote(newQA);

        //new SourceDoc object for this quote
        SourceDoc sd = new SourceDoc(new HashSet());

        /// Disabled this option for checking --Nitesh 29-03

       
        QuoteHelper.clientUnlinkSourcesAndTargets(newQ,newQA.getId());

  //update values
        
        if(mainSrc!=null) {
            for (int i = 0; i < mainSrc.length; i++) {
                sd.setLanguage(mainSrc[i]);
                //System.out.println("   sd.setLanguage(mainSrc[i]);" + mainSrc[i]);
                Integer sdId = ProjectService.getInstance().addSourceWithProject(p, sd, newQA, newQ);
                if (targetLanguage != null) {
                    for (int j = 0; j < targetLanguage.length; j++) {
                        TargetDoc td = new TargetDoc(new HashSet(), new HashSet(), new HashSet(), new HashSet());
                        td.setLanguage(targetLanguage[j]);
                        Integer x = ProjectService.getInstance().linkSourceDocTargetDoc(sd, td);
                    }
                }
                if (mainTarget != null) {
                    for (int j = 0; j < mainTarget.length; j++) {
                        if (!mainTarget[j].equalsIgnoreCase("t")) {
                            //System.out.println("main Targetttttttttt::::" + mainTarget[j]);
                            TargetDoc td = new TargetDoc(new HashSet(), new HashSet(), new HashSet(), new HashSet());
                            td.setLanguage(mainTarget[j]);
                            Integer x = ProjectService.getInstance().linkSourceDocTargetDoc(sd, td);
                        }
                    }
                }
            }
        }
//update values
    if(sourceLanguage!=null) {
            for (int i = 0; i < sourceLanguage.length; i++) {
                sd.setLanguage(sourceLanguage[i]);
                //System.out.println("   sd.setLanguage(mainSrc[i]);" + sourceLanguage[i]);
                Integer sdId = ProjectService.getInstance().addSourceWithProject(p, sd, newQA, newQ);
                if (targetLanguage != null) {
                    for (int j = 0; j < targetLanguage.length; j++) {
                        TargetDoc td = new TargetDoc(new HashSet(), new HashSet(), new HashSet(), new HashSet());
                        td.setLanguage(targetLanguage[j]);
                        Integer x = ProjectService.getInstance().linkSourceDocTargetDoc(sd, td);
                    }
                }
                if (mainTarget != null) {
                    for (int j = 0; j < mainTarget.length; j++) {
                        TargetDoc td = new TargetDoc(new HashSet(), new HashSet(), new HashSet(), new HashSet());
                        td.setLanguage(mainTarget[j]);
                        Integer x = ProjectService.getInstance().linkSourceDocTargetDoc(sd, td);
                    }
                }
            }
        }



            return mapping.findForward("Success");
    

    }

}