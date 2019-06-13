//QuoteViewGeneralAddTaskOth2Action.java gets the added tasks
//per target and adds them to the db

package app.quote;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleException;
import org.apache.struts.util.MessageResources;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.validator.*;
import java.util.*;
import app.user.*;
import app.project.*;
import app.standardCode.*;
import app.db.*;
import app.workspace.*;
import app.security.*;


public final class QuoteViewGeneralAddTaskOth2Action extends Action {


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
	
        //values for adding a new target Doc
        DynaValidatorForm qvgato1 = (DynaValidatorForm) form;
        
        String[] targets = (String[]) qvgato1.get("targets");  
        String all = (String) qvgato1.get("all"); //the all languages box
        String othTask = (String) qvgato1.get("othTask"); 
                
        //get the user's chosen source array for later use in case new target "new" needs to be created
        HttpSession session = request.getSession(false);
        SourceDoc[] sources = (SourceDoc[]) session.getAttribute("sourceArray");
                
        if(!all.equals("on")) { //if specific to only a few targets
            //for each target, add the new target to db and each target's new other tasks selected from form
            for(int i = 0; i < targets.length; i++) {          
                Set othTasks = new HashSet(); //list of new othTasks

                //need target doc and source doc to add tasks to it
                TargetDoc td = ProjectService.getInstance().getSingleTargetDoc(Integer.valueOf(targets[i]));
                SourceDoc sd = td.getSourceDoc();
                Project p = sd.getQuote().getProject();

                    OthTask ot = new OthTask();
                    ot.setSourceLanguage(sd.getLanguage());
                    ot.setTargetLanguage(td.getLanguage());
                    ot.setCurrency(p.getCompany().getCcurrency());
                    ot.setTargetDoc(td);
                    ot.setTaskName(othTask);
                    ot.setOrderNum(new Integer(1));
                    othTasks.add(ot);
                    
                 //for each othTask, add it to db and link it to this targetDoc
                 for(Iterator iter = othTasks.iterator(); iter.hasNext();) {
                    OthTask ot2 = (OthTask) iter.next();
                
                    //link this othTask to the targetDoc; add new othTask to db
                    Integer w = ProjectService.getInstance().linkTargetDocOthTask(td, ot2); 
                }
                
            }
        } //end if all.equals("on")
        else { //add tasks to all targets (actually a single "all" target)
            //for each source, add the new target to db and each target's new other tasks selected from form
            for(int i = 0; i < sources.length; i++) {  
            Set othTasks = new HashSet(); //list of new othTasks

            //target language's new object
            TargetDoc td = new TargetDoc(new HashSet(), new HashSet(), new HashSet(), new HashSet());
            td.setLanguage("All");
            SourceDoc sd = sources[i];
            Project p = sd.getQuote().getProject();
            
            //link this target Doc to the source Doc; add new target Doc to db
            Integer x = ProjectService.getInstance().linkSourceDocTargetDoc(sd, td); 
            
                    OthTask ot = new OthTask();
                    ot.setSourceLanguage(sd.getLanguage());
                    ot.setTargetLanguage(td.getLanguage());
                    ot.setCurrency(p.getCompany().getCcurrency());
                    ot.setTargetDoc(td);
                    ot.setTaskName(othTask);
                    ot.setOrderNum(new Integer(1));
                    othTasks.add(ot);
                
             //for each othTask, add it to db and link it to this targetDoc
            for(Iterator iter = othTasks.iterator(); iter.hasNext();) {
                OthTask ot2 = (OthTask) iter.next();
                
                //link this othTask to the targetDoc; add new othTask to db
                Integer w = ProjectService.getInstance().linkTargetDocOthTask(td, ot2); 
            }
        }
        }//end else (new target per source with tasks)
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
