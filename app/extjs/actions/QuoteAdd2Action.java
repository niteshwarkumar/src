//ProjectAdd4Action.java collects the source info
//for this project

package app.extjs.actions;
import app.extjs.helpers.ProjectHelper;
import app.extjs.helpers.QuoteHelper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import java.util.*;
import app.project.*;
import app.quote.Quote1;
import app.quote.QuoteService;
import app.security.*;

public final class QuoteAdd2Action extends Action {


    // ----------------------------------------------------- Instance Variables


    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log =
        LogFactory.getLog("org.apache.struts.webapp.Example");


    // --------------------------------------------------------- Public Methods


    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @exception Exception if business logic throws an exception
     */
    public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
	throws Exception {

	// Extract attributes we will need
	MessageResources messages = getResources(request);	

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
        
        Quote1 newQ = QuoteService.getInstance().getSingleQuote(new Integer(request.getParameter("quoteViewId"))); 
        Project p = ProjectService.getInstance().getSingleProject(newQ.getProject().getProjectId());
        
        //Project p = ProjectService.getInstance().getSingleProject(Integer.valueOf(projectId)); 
        
        //new SourceDoc object for this quote
        SourceDoc sd = new SourceDoc(new HashSet());
        ProjectHelper.unlinkSourcesAndTargets(p);
        QuoteHelper.unlinkSourcesAndTargets(newQ);
        
       // //System.out.println("mainTarget="+mainTarget);
//update values
        ////System.out.println("project id="+p.getProjectId());
        if(mainSrc!=null)
        for(int i=0; i<mainSrc.length;i++){
            sd.setLanguage(mainSrc[i]);
            
            //add SourceDoc to db building one-to-many relationship between project and source
            //Integer sdId = ProjectService.getInstance().addSourceWithProject(p, sd);
            QuoteService.getInstance().addSourceWithQuote(newQ, sd) ;

            ////System.out.println("linking source id="+sd.getSourceDocId());
        if(targetLanguage!=null)
            for(int j = 0; j < targetLanguage.length; j++) {
                //target language's new object
                TargetDoc td = new TargetDoc(new HashSet(), new HashSet(), new HashSet(), new HashSet());
                td.setLanguage(targetLanguage[j]);
                ////System.out.println("linking target id="+td.getTargetDocId());
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


//update values
    if(sourceLanguage!=null)
        for(int i=0; i<sourceLanguage.length;i++){
            sd.setLanguage(sourceLanguage[i]);
            
            //add SourceDoc to db building one-to-many relationship between project and source
            Integer sdId = ProjectService.getInstance().addSourceWithProject(p, sd); 
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
        
     
        
        //place sourceDoc into cookie; this will be used later in wizard when adding targetDocs
        //response.addCookie(StandardCode.getInstance().setCookie("projectAddSourceDocId", String.valueOf(sdId)));
        //save in cookie for future target adding
       // response.addCookie(StandardCode.getInstance().setCookie("projectAddLanguage", sourceLanguage));
       // request.setAttribute("projectAddLanguage",  sourceLanguage); //used in form for display
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
