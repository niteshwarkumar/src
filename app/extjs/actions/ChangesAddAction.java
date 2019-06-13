/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.extjs.actions;

import app.extjs.helpers.ProjectHelper;
import app.project.Change1;
import app.project.LinTask;
import app.project.Project;
import app.project.ProjectService;
import app.project.SourceDoc;
import app.project.TargetDoc;
import app.quote.QuoteService;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
 * @author niteshwar
 */
public class ChangesAddAction  extends Action {


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
//        String[] sourceLanguage = request.getParameterValues("sourceLanguage");
//        String[] targetLanguage = request.getParameterValues("targetLanguages");
//
//        String[] mainTarget  = request.getParameterValues("mainTarget");
//        String[] mainSrc  = request.getParameterValues("mainSrc");
        //need the project to add this source
        //String projectId = StandardCode.getInstance().getCookie("projectAddId", request.getCookies());
        String projectId = request.getParameter("projectViewId");
        Project p = ProjectService.getInstance().getSingleProject(Integer.valueOf(projectId));
        String changeId = request.getParameter("change");

        int changeNo = ProjectHelper.selectMaxChangeNoForProject(p.getProjectId().intValue())+1;
        //new SourceDoc object for this quote
       // SourceDoc sd = new SourceDoc(new HashSet());
        if("Y".equals(request.getParameter("isBack"))){
           changeNo=changeNo-1;

        }
        if(!StandardCode.getInstance().noNull(changeId).isEmpty()){
            if(Integer.parseInt(changeId)>0){
            Change1 change1 = ProjectService.getInstance().getSingleChange(Integer.valueOf(changeId));
                if (!StandardCode.getInstance().noNull(change1.getName()).isEmpty()) {
                    request.setAttribute("changes", change1.getName());
                } else {
                    request.setAttribute("changes", StandardCode.getInstance().noNull(change1.getNumber()));
                }
            }
            if(Integer.parseInt(changeId)==-1){
                 request.setAttribute("changes", "Original");
            }
        }else{
            request.setAttribute("changes", "");
        }
        
        
               Integer flag1=0;
        Set sources = p.getSourceDocs();
         List<String> changes = new ArrayList();
         Map<String,Integer> changeMap = new HashMap();


        //for each source
//        for(Iterator sourceIter = sources.iterator(); sourceIter.hasNext();) {
//            SourceDoc sd = (SourceDoc) sourceIter.next();
//
//            //for each target of this source
//            for(Iterator linTargetIter = sd.getTargetDocs().iterator(); linTargetIter.hasNext();) {
//                TargetDoc td = (TargetDoc) linTargetIter.next();
//
//                //for each lin Task of this target
//                for(Iterator linTaskIter = td.getLinTasks().iterator(); linTaskIter.hasNext();) {
//                    LinTask lt = (LinTask) linTaskIter.next();
////                    String change = StandardCode.getInstance().noNull(lt.getChangeDesc());
////                    if(!changes.contains(change)){
////                        changes.add(change);
////                        Change1 change1 = ProjectService.getInstance().getSingleChange(Integer.valueOf(projectId), change);
////                        if(change1 ==null){
////                            change1 = new Change1();
////                            change1.setName(change);
////                            change1.setNumber(change);
////                            ProjectService.getInstance().addChangeWithProject(p, change1);
////                            change1 = ProjectService.getInstance().getSingleChange(Integer.valueOf(projectId), change);
////                        }
////                            changeMap.put(change, change1.getChange1Id());
////                    }
//                }
//            }
//        }
//        request.setAttribute("changes", changes);
        request.setAttribute("changeId", changeId);
//        request.setAttribute("changesMap", changeMap);
        request.setAttribute("autoNo", ""+changeNo);
        

        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

    private void id(boolean b) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
