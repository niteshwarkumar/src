//ProjectViewTeamAddTarget2Action.java collects the target(s) info
//for one or more sources within one project

package app.project;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleException;
import org.apache.struts.util.MessageResources;
import org.apache.commons.beanutils.PropertyUtils;
import java.util.*;
import java.text.*;
import app.user.*;
import app.db.*;
import app.workspace.*;
import app.security.*;
import app.project.*;
import app.standardCode.*;
import org.apache.struts.validator.*;
import app.client.*;


public final class ProjectViewTeamAddTarget2Action extends Action {


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
                     
        //get current project ID
        
        //id from request attribute
        String projectId = (String) request.getAttribute("projectViewId");
        
        //id from cookie
        if(projectId == null) {
            projectId = StandardCode.getInstance().getCookie("projectViewId", request.getCookies());	
        }
        
        Integer id = Integer.valueOf(projectId);        
        
        //get project with id from database
        Project p = ProjectService.getInstance().getSingleProject(id);
        
       //get form values and sources to add targets to
        DynaValidatorForm qvgatl1 = (DynaValidatorForm) form; 
        
        String[] targetLanguage = (String[]) (qvgatl1.get("targetLanguageRight"));  
        String linCompilation = (String) qvgatl1.get("linCompilation");
        String linTranslation = (String) qvgatl1.get("linTranslation");
        String linTrEd = (String) qvgatl1.get("linTrEd");
        String linEdit = (String) qvgatl1.get("linEdit");
        String linProofreading = (String) qvgatl1.get("linProofreading");
        String linLin = (String) qvgatl1.get("linLin");
        String linIn = (String) qvgatl1.get("linIn");           
        String linEval = (String) qvgatl1.get("linEval");
        String linImplementation = (String) qvgatl1.get("linImplementation");
        String linProofCC = (String) qvgatl1.get("linProofCC");
        String linFinal = (String) qvgatl1.get("linFinal");
        String linOther = (String) qvgatl1.get("linOther");
        String lin0 = (String) (qvgatl1.get("lin0"));  
        String lin1 = (String) (qvgatl1.get("lin1"));  
        String lin2 = (String) (qvgatl1.get("lin2"));  
        String lin3 = (String) (qvgatl1.get("lin3"));  
        String lin4 = (String) (qvgatl1.get("lin4"));  
        String lin5 = (String) (qvgatl1.get("lin5"));  
        String lin6 = (String) (qvgatl1.get("lin6"));  
        String lin7 = (String) (qvgatl1.get("lin7"));  
        String lin8 = (String) (qvgatl1.get("lin8"));  
        String lin9 = (String) (qvgatl1.get("lin9"));  
        String lin10 = (String) (qvgatl1.get("lin10"));  
        String lin11 = (String) (qvgatl1.get("lin11"));  
        String[] linTaskOptions = ProjectService.getInstance().getLinTaskOptions();
        
        //get sources from session
        HttpSession session = request.getSession(false);
        SourceDoc[] sources = (SourceDoc[]) session.getAttribute("projectViewGeneralAddTargetSources");        
        
        for(int j = 0; j < sources.length; j++) {            
            //get the current source doc from array
            SourceDoc sd = sources[j]; 

            //for each target, add the new target to db and each target's new linguistic tasks selected from form
            for(int i = 0; i < targetLanguage.length; i++) {
                //target language's new object
                TargetDoc td = new TargetDoc(new HashSet(), new HashSet(), new HashSet(), new HashSet());
                td.setLanguage(targetLanguage[i]);

                //link this target Doc to the source Doc; add new target Doc to db
                Integer x = ProjectService.getInstance().linkSourceDocTargetDoc(sd, td); 

                Set linTasks = new HashSet(); //list of new linTasks

            if(lin1.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(targetLanguage[i]);
                lt.setPostQuote("true");
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[1-1]);
                lt.setOrderNum(new Integer(1));
                linTasks.add(lt);
            }
                  
            if(lin2.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(targetLanguage[i]);
                lt.setPostQuote("true");
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[2-1]);
                lt.setOrderNum(new Integer(2));
                linTasks.add(lt);
            }
            
            if(lin3.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(targetLanguage[i]);
                lt.setPostQuote("true");
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[3-1]);
                lt.setOrderNum(new Integer(3));
                linTasks.add(lt);
            }
            
            if(lin4.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(targetLanguage[i]);
                lt.setPostQuote("true");
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[4-1]);
                lt.setOrderNum(new Integer(4));
                linTasks.add(lt);
            }
                        
            if(lin5.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(targetLanguage[i]);
                lt.setPostQuote("true");
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[5-1]);
                lt.setOrderNum(new Integer(5));
                linTasks.add(lt);
            }
            
            if(lin6.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(targetLanguage[i]);
                lt.setPostQuote("true");
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[6-1]);
                lt.setOrderNum(new Integer(6));
                linTasks.add(lt);
            }
            
            if(lin7.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(targetLanguage[i]);
                lt.setPostQuote("true");
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[7-1]);
                lt.setOrderNum(new Integer(7));
                linTasks.add(lt);
            }
            
            if(lin8.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(targetLanguage[i]);
                lt.setPostQuote("true");
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[8-1]);
                lt.setOrderNum(new Integer(8));
                linTasks.add(lt);
            }
            
            if(lin9.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(targetLanguage[i]);
                lt.setPostQuote("true");
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[9-1]);
                lt.setOrderNum(new Integer(9));
                linTasks.add(lt);
            }
            
            if(lin10.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(targetLanguage[i]);
                lt.setPostQuote("true");
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[10-1]);
                lt.setOrderNum(new Integer(10));
                linTasks.add(lt);
            }
            
            if(lin11.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setNotes((String) (qvgatl1.get("linOtherText")));
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(targetLanguage[i]);
                lt.setPostQuote("true");
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[11-1]);
                lt.setOrderNum(new Integer(11));
                linTasks.add(lt);
            }

                //for each LinTask, add it to db and link it to this targetDoc
                for(Iterator iter = linTasks.iterator(); iter.hasNext();) {
                    LinTask lt = (LinTask) iter.next();

                    //link this linTask to the targetDoc; add new linTask to db
                    Integer y = ProjectService.getInstance().linkTargetDocLinTask(td, lt); 
                }

            }//end target for
        }//end source for
        
        //put this project into the request
        request.setAttribute("project", p);
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
