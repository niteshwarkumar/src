//ProjectAdd4Action.java collects the source info
//for this project

package app.extjs.actions;
import app.extjs.helpers.ProjectHelper;
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
import app.quote.QuoteService;
import app.security.*;

public final class ChangesAdd2Action extends Action {


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
        Project p = ProjectService.getInstance().getSingleProject(Integer.valueOf(projectId));

        int changeNo = ProjectHelper.selectMaxChangeNoForProject(p.getProjectId().intValue())+1;
        //new SourceDoc object for this quote
       // SourceDoc sd = new SourceDoc(new HashSet());
        if("Y".equals(request.getParameter("isBack"))){
           changeNo=changeNo-1;

        }
               Integer flag1=0;
        Set sources = p.getSourceDocs();
        for(Iterator sourceIter = sources.iterator(); sourceIter.hasNext();) {
            flag1=0;
         SourceDoc sd1 = (SourceDoc) sourceIter.next();
             //for each target of this source
         if(sd1.getChangeNo()== changeNo){
         for(Iterator linTargetIter = sd1.getTargetDocs().iterator(); linTargetIter.hasNext();) {
         TargetDoc tdoc1 = (TargetDoc) linTargetIter.next();
         List linTaskList = ProjectService.getInstance().getLinTask(tdoc1.getTargetDocId());
         List engTaskList = ProjectService.getInstance().getEnggTask(tdoc1.getTargetDocId());
         List forTaskList = ProjectService.getInstance().getFormatTask(tdoc1.getTargetDocId());
         List otherTaskList = ProjectService.getInstance().getOtherTask(tdoc1.getTargetDocId());
         if(linTaskList.size()==0 && engTaskList.size()==0 && forTaskList.size()==0 && otherTaskList.size()==0){
         flag1=1;

         ProjectHelper.unlinkTargetsForLang(tdoc1.getTargetDocId());
         }else{flag1=0;}


         }}

         if(flag1==1){
         ProjectHelper.unlinkSourcesAndTargetsForLang(p,sd1.getLanguage());
            }
        }


        Integer flag=0;

        try{
        for(int i=0;i<mainSrc.length;i++){
            flag=0;
        SourceDoc sdoc=ProjectService.getInstance().getSingleSourceDoc(p.getProjectId(),mainSrc[i]);
         try{
        List targetlang=QuoteService.getInstance().getTargetLang(sdoc.getSourceDocId());

         for (int jj = 0; jj < targetlang.size(); jj++) {
         TargetDoc tdoc = (TargetDoc) targetlang.get(jj);

        //TargetDoc tdoc=ProjectService.getInstance().getSingleTargetDoc(mainSrc[i], sdoc.getSourceDocId());
         List linTaskList = ProjectService.getInstance().getLinTask(tdoc.getTargetDocId());
         List engTaskList = ProjectService.getInstance().getEnggTask(tdoc.getTargetDocId());
         List forTaskList = ProjectService.getInstance().getFormatTask(tdoc.getTargetDocId());
         List otherTaskList = ProjectService.getInstance().getOtherTask(tdoc.getTargetDocId());
         if(linTaskList.size()==0 && engTaskList.size()==0 && forTaskList.size()==0 && otherTaskList.size()==0){
         flag=1;
         }else{flag=0;}}
 }catch(Exception e){

            System.out.println("Exception"+ e.getMessage());
 }

        if(flag==0){
         if("Y".equals(request.getParameter("isBack"))){
             //if()
        //    ProjectHelper.unlinkSourcesAndTargetsForLang(p,mainSrc[i]);

        }
        }
       }}catch(Exception e){}
 try{
        for(int i=0;i<sourceLanguage.length;i++){
        flag=0;
        SourceDoc sdoc=ProjectService.getInstance().getSingleSourceDoc(p.getProjectId(),sourceLanguage[i] );
  try{
        List targetlang=QuoteService.getInstance().getTargetLang(sdoc.getSourceDocId());

         for (int jj = 0; jj < targetlang.size(); jj++) {
         TargetDoc tdoc = (TargetDoc) targetlang.get(jj);
         List linTaskList = ProjectService.getInstance().getLinTask(tdoc.getTargetDocId());
         List engTaskList = ProjectService.getInstance().getEnggTask(tdoc.getTargetDocId());
         List forTaskList = ProjectService.getInstance().getFormatTask(tdoc.getTargetDocId());
         List otherTaskList = ProjectService.getInstance().getOtherTask(tdoc.getTargetDocId());
         if(linTaskList.size()==0 && engTaskList.size()==0 && forTaskList.size()==0 && otherTaskList.size()==0){
         flag=1;
        

         }else{flag=0;}}
}catch(Exception e){}
        if(flag==0){
         if("Y".equals(request.getParameter("isBack"))){
       //     ProjectHelper.unlinkSourcesAndTargetsForLang(p,sourceLanguage[i]);

        }
        }
        }}catch(Exception e){}

        
       SourceDoc sd =null;
       // System.out.println("mainTarget="+mainTarget);
//update values

        //System.out.println("new changeNo="+changeNo+" for p.getProjectId().intValue()="+p.getProjectId().intValue());
        try{
        for(int i=0; i<mainSrc.length;i++){
            Integer sdId=0;
            sd=ProjectService.getInstance().getSingleSourceDoc(p.getProjectId(),mainSrc[i]);
            if(sd!=null){
            sd.setChangeNo(new Integer(changeNo));
            sdId= sd.getSourceDocId();

            ProjectService.getInstance().UpdateSource(sd);
            }else{
                sd = new SourceDoc(new HashSet());
            sd.setLanguage(mainSrc[i]);
            sd.setChangeNo(new Integer(changeNo));

            //Integer sdId=ProjectService.getInstance().ge
            //add SourceDoc to db building one-to-many relationship between project and source
            sdId = ProjectService.getInstance().addSourceWithProject(p, sd);
            }
            if(targetLanguage!=null) {
                    for (int j = 0; j < targetLanguage.length; j++) {
                        Integer x = 0;
                        TargetDoc tgtDoc = ProjectService.getInstance().getSingleTargetDoc(targetLanguage[j], sdId);
                        if (tgtDoc != null) {
                            tgtDoc.setChangeNo(new Integer(changeNo));
                            ProjectService.getInstance().UpdateTarget(tgtDoc);
                        } else {
                            TargetDoc td = new TargetDoc(new HashSet(), new HashSet(), new HashSet(), new HashSet());
                            td.setLanguage(targetLanguage[j]);
                            td.setChangeNo(new Integer(changeNo));
                            x = ProjectService.getInstance().linkSourceDocTargetDoc(sd, td);
                        }
                    }
                }
            if(mainTarget!=null) {
                    for (int j = 0; j < mainTarget.length; j++) {
                        Integer x = 0;
                        TargetDoc tgtDoc = ProjectService.getInstance().getSingleTargetDoc(mainTarget[j], sdId);
                        if (tgtDoc != null) {
                            tgtDoc.setChangeNo(new Integer(changeNo));
                           ProjectService.getInstance().UpdateTarget(tgtDoc);
                        } else {
                            TargetDoc td = new TargetDoc(new HashSet(), new HashSet(), new HashSet(), new HashSet());
                            td.setLanguage(mainTarget[j]);
                            td.setChangeNo(new Integer(changeNo));
                            x = ProjectService.getInstance().linkSourceDocTargetDoc(sd, td);
                        }
                    }
                }


        }    }catch(Exception e){}


//update values
    if(sourceLanguage!=null) {
            for (int i = 0; i < sourceLanguage.length; i++) {
                Integer sdId = 0;
                 sd = ProjectService.getInstance().getSingleSourceDoc(p.getProjectId(), sourceLanguage[i]);
                if (sd != null) {
                    sd.setChangeNo(new Integer(changeNo));
                    sdId= sd.getSourceDocId();
                    //sdId= sdId = ProjectService.getInstance().addSourceWithProject(p, sd);
                    ProjectService.getInstance().UpdateSource(sd);
                } else {
                     sd = new SourceDoc(new HashSet());
                    sd.setLanguage(sourceLanguage[i]);
                    sdId = ProjectService.getInstance().addSourceWithProject(p, sd);
                }
                if (targetLanguage != null) {
                    for (int j = 0; j < targetLanguage.length; j++) {
                        Integer x = 0;
                        TargetDoc tgtDoc = ProjectService.getInstance().getSingleTargetDoc(targetLanguage[j], sdId);
                        if (tgtDoc != null) {
                            tgtDoc.setChangeNo(new Integer(changeNo));
                            ProjectService.getInstance().UpdateTarget(tgtDoc);
                        } else {
                            TargetDoc td = new TargetDoc(new HashSet(), new HashSet(), new HashSet(), new HashSet());
                            td.setLanguage(targetLanguage[j]);td.setChangeNo(new Integer(changeNo));

                            x = ProjectService.getInstance().linkSourceDocTargetDoc(sd, td);
                        }
                    }
                }
                if (mainTarget != null) {
                    for (int j = 0; j < mainTarget.length; j++) {
                        Integer x = 0;
                        TargetDoc tgtDoc = ProjectService.getInstance().getSingleTargetDoc(mainTarget[j], sdId);
                        if (tgtDoc != null) {
                            tgtDoc.setChangeNo(new Integer(changeNo));
                            ProjectService.getInstance().UpdateTarget(tgtDoc);
                        } else {
                            TargetDoc td = new TargetDoc(new HashSet(), new HashSet(), new HashSet(), new HashSet());
                            td.setLanguage(mainTarget[j]);
                            td.setChangeNo(new Integer(changeNo));
                            x = ProjectService.getInstance().linkSourceDocTargetDoc(sd, td);
                        }
                    }
                }
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

    private void id(boolean b) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
