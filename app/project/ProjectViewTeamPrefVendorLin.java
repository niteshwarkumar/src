/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.project;

import app.extjs.helpers.TeamHelper;
import app.resource.LanguagePair;
import app.resource.RateScoreLanguage;
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
import net.sf.hibernate.Hibernate;
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
public class ProjectViewTeamPrefVendorLin  extends Action {


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
	String projectId =  request.getParameter("projectViewId");
        
        //check attribute in request
        if(projectId == null) {
            projectId = (String) request.getAttribute("projectViewId");
        }
        
        //id of project from cookie
        if(projectId == null) {            
            projectId = StandardCode.getInstance().getCookie("projectViewId", request.getCookies());
        }
        
        String change = request.getParameter("change");
        
        HashMap<String, String> totHashMap = new HashMap<>();
        totHashMap.put("Medical", "Medical");
        totHashMap.put("Technical", "Technical");
        totHashMap.put("Software", "Software");
        totHashMap.put("General", "General");
        totHashMap.put("Marketing", "Marketing");
        totHashMap.put("Financial", "Legal/Financial");
        totHashMap.put("Legal", "Legal/Financial");
        totHashMap.put("Regulatory", "Technical");

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
        
        //END get id of current project from either request, attribute, or cookie 
              
        
        //get project to edit
        Project p = ProjectService.getInstance().getSingleProject(id); 
        
        //get this project's sources
        Set sources = p.getSourceDocs();
        List langlst = ProjectService.getInstance().getLanguageList();
        HashMap langMap = new HashMap();
        for(int i =0; i < langlst.size(); i++){
            Language lang = (Language) langlst.get(i);
            langMap.put(lang.getLanguage(), lang.getLanguageId());
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
                
                for (Iterator iterLin = td.getLinTasks().iterator(); iterLin.hasNext();) {
                  LinTask t = (LinTask) iterLin.next();
                 if(StandardCode.getInstance().noNull(change).equalsIgnoreCase(StandardCode.getInstance().noNull(t.getChangeDesc()))){
                  if ("Translation".equalsIgnoreCase(t.getTaskName())) {
                       resObj.put("Translation", t.getLinTaskId());              
                                   
                   } else if ("Editing".equalsIgnoreCase(t.getTaskName())) {
                               resObj.put("Editing", t.getLinTaskId());         
                                  
                   } else if (t.getTaskName().contains("Proofreading")) {
                            resObj.put("Proofreading", t.getLinTaskId());            
                                   
                   }
                 }
                }
        //perform search and store results in a List
        //perform main search 
        try{
        List results = ResourceService.getInstance().getPreferredResourceSearch( langMap.get(sd.getLanguage()).toString(),  langMap.get(td.getLanguage()).toString());
         if(results!=null) { 
        if(results.size()>0) {     
       
//                resObj.put("taskId", t.getLinTaskId());
                JSONArray vendorArr = new JSONArray();
        

        
        for(ListIterator outer = results.listIterator(); outer.hasNext();) {
            boolean addVendor = false;
                        JSONObject venObj = new JSONObject();
                        Resource res = (Resource) outer.next();
                        Resource r = ResourceService.getInstance().getSingleResource(res.getResourceId());
                        if(!StandardCode.getInstance().noNull(r.getFirstName()).equalsIgnoreCase("")){
                        venObj.put("vendor", r.getFirstName()+" "+r.getLastName());
                       
                        }else{
                        venObj.put("vendor", r.getCompanyName());
                        }
                        ResourceService.getInstance().getSingleLanguagePair(id);
                        Set languagePairs = r.getLanguagePairs();
                //the list for display (load all ratescorelanguage entries)
                       // List ratescorelanguages = new ArrayList();
                        for(Iterator iter = languagePairs.iterator(); iter.hasNext();) {
                            LanguagePair lp = (LanguagePair) iter.next();
                            for(Iterator rateScoreIter = lp.getRateScoreLanguages().iterator(); rateScoreIter.hasNext();) {
                              RateScoreLanguage rsl = (RateScoreLanguage) rateScoreIter.next();
                            if(rsl.getSource().equalsIgnoreCase(sd.getLanguage())&&rsl.getTarget().equalsIgnoreCase(td.getLanguage())){
                            if(!StandardCode.getInstance().noNull(p.getTypeOfText()).isEmpty() ){
                              if(rsl.getSpecialty().equals(totHashMap.get(p.getTypeOfText()))) { 
                                venObj.put(rsl.getSpecialty(), rsl.getScore());
                                if(rsl.getScore()<21) 
                                    venObj.put("color", "red");
                                    
                              }
                            }else{
                                venObj.put(rsl.getSpecialty(), rsl.getScore());
                                
                            }
                        
                        }}
                        
      
            }
//                        for(int i = 0; i < languagePairs.size(); i++)
////                        for(Iterator itlp = r.getLanguagePairs().iterator(); itlp.hasNext();){
//                            LanguagePair lp = languagePairs.;
//                            if(lp.getSource().equalsIgnoreCase(sd.getLanguage())&&lp.getTarget().equalsIgnoreCase(td.getLanguage())){}
//                               // venObj.put("score", lp.)
//                        }
                        
                        venObj.put("vendorId", r.getResourceId());  
                        Vector tempResult = TeamHelper.getVendorCounts(r.getResourceId());
                        Vector tempResultClient =  TeamHelper.getVendorCounts(r.getResourceId(), p.getCompany().getClientId());
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
        request.setAttribute("speciality", StandardCode.getInstance().noNull(p.getTypeOfText()));
//        request.getSession(false).setAttribute("resourceRecordTotal", resourceRecordTotal);
//        response.addCookie(StandardCode.getInstance().setCookie("resourceRecordTotal", resourceRecordTotal));
        

        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
