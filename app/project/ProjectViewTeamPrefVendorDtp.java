/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.project;

import app.extjs.helpers.TeamHelper;
import app.resource.Resource;
import app.resource.ResourceService;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Vector;
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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author niteshwar
 */
public class ProjectViewTeamPrefVendorDtp   extends Action {


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
	
                //START get id of current project from either request, attribute, or cookie 
        //id of project from request
	String projectId = null;
	projectId = request.getParameter("projectViewId");
        
        //check attribute in request
        if(projectId == null) {
            projectId = (String) request.getAttribute("projectViewId");
        }
        
        //id of project from cookie
        if(projectId == null) {            
            projectId = StandardCode.getInstance().getCookie("projectViewId", request.getCookies());
        }

        //default project to last if not in request or cookie
        if(projectId == null) {
                List results = ProjectService.getInstance().getProjectList();
                                
                ListIterator iterScroll = null;
                for(iterScroll = results.listIterator(); iterScroll.hasNext(); iterScroll.next()) {}
                   iterScroll.previous();
                   Project p = (Project) iterScroll.next();  
                   projectId = String.valueOf(p.getProjectId());
         }            
        
        Integer id = Integer.valueOf(projectId);
        String type = request.getParameter("type");
        
        //END get id of current project from either request, attribute, or cookie 
              
        
        //get project to edit
        Project p = ProjectService.getInstance().getSingleProject(id); 
        
        //get this project's sources
        Set sources = p.getSourceDocs();
        List langlst = ProjectService.getInstance().getLanguageList();
        HashMap langMap = new HashMap();
        HashMap targetlangMap = new HashMap();
        for(int i =0; i < langlst.size(); i++){
            Language lang = (Language) langlst.get(i);
            langMap.put(lang.getLanguage(), lang.getLanguageId());
            targetlangMap.put(lang.getLanguage(), lang.getDtp_lang_group());
        }
        
        JSONArray resArray = new JSONArray();
        
        //for each source
        for(Iterator sourceIter = sources.iterator(); sourceIter.hasNext();) {
            SourceDoc sd = (SourceDoc) sourceIter.next();
            
            //for each target of this source
            for(Iterator linTargetIter = sd.getTargetDocs().iterator(); linTargetIter.hasNext();) {
                TargetDoc td = (TargetDoc) linTargetIter.next();
                 JSONObject resObj = new JSONObject();
                resObj.put("lang", sd.getLanguage()+"-"+td.getLanguage());
//                resObj.put("task", t.getTaskName()); 
                


                if(type.equalsIgnoreCase("dtp")){
                for (Iterator iterDtp = td.getDtpTasks().iterator(); iterDtp.hasNext();) {
                  DtpTask t = (DtpTask) iterDtp.next();
                    //System.out.println("app.project.ProjectViewTeamPrefVendorDtp.execute()"+t.getTaskName());
                  if ("Desktop Publishing".equalsIgnoreCase(t.getTaskName())) {
                       resObj.put("DTP", t.getDtpTaskId());              
                                   
                   } else if ("DTP".equalsIgnoreCase(t.getTaskName())&&type.equalsIgnoreCase("dtp")) {
                               resObj.put("DTP", t.getDtpTaskId());         
                                  
//                   } else if (t.getTaskName().contains("FQA")) {
//                            resObj.put("FQA", t.getDtpTaskId());            
                                   
                   }
                  
                }}else{
                    for (Iterator iterDtp = td.getDtpTasks().iterator(); iterDtp.hasNext();) {
                  DtpTask t = (DtpTask) iterDtp.next();
                     if (t.getTaskName().contains("FQA")) {
                           resObj.put("DTP", t.getDtpTaskId());            
                                   
                   }
                  
                }
                }
        //perform search and store results in a List
        //perform main search 
        try{
            List results = new ArrayList();
           if(type.equalsIgnoreCase("dtp")){ 
                results = ResourceService.getInstance().getPreferredResourceSearchDTP( langMap.get(sd.getLanguage()).toString(), targetlangMap.get(td.getLanguage()).toString());
                request.setAttribute("header", "DTP");
           }else{
                results = ResourceService.getInstance().getPreferredResourceSearchFQA();
                request.setAttribute("header", "FQA");
           }
        
         if(results!=null) { 
        if(results.size()>0) {     
       
//                resObj.put("taskId", t.getDtpTaskId());
                JSONArray vendorArr = new JSONArray();
        

        
        for(ListIterator outer = results.listIterator(); outer.hasNext();) {
                        JSONObject venObj = new JSONObject();
                        Resource r = (Resource) outer.next();
                        if(!StandardCode.getInstance().noNull(r.getFirstName()).equalsIgnoreCase("")){
                        venObj.put("vendor", r.getFirstName()+" "+r.getLastName());
                       
                        }else{
                        venObj.put("vendor", r.getCompanyName());
                        }
                        venObj.put("vendorId", r.getResourceId());  
                        Vector tempResult = TeamHelper.getVendorCountsDTP(r.getResourceId());
                        Vector tempResultClient =  TeamHelper.getVendorCountsDTP(r.getResourceId(), p.getCompany().getClientId());
                        ////System.out.println(tempResult);
                        venObj.put("project", (String)tempResult.get(0)+" ("+(String)tempResultClient.get(0)+") ");
                        vendorArr.put(venObj);
        }
        resObj.put("vendor", vendorArr);
            resArray.put(resObj);
        }
         }
               // }
        }catch(Exception e){
           e.printStackTrace();
        }     
            }   
        }  
      
   // //System.out.println(resArray);
        //set total records into request, session and cookie
        request.setAttribute("prefResource", resArray);
//        request.getSession(false).setAttribute("resourceRecordTotal", resourceRecordTotal);
//        response.addCookie(StandardCode.getInstance().setCookie("resourceRecordTotal", resourceRecordTotal));
        

        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
