//QuoteViewGeneralAddTaskEng2Action.java gets the added tasks
//per target and adds them to the db

package app.quote;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.*;
import java.util.*;
import app.project.*;
import app.security.*;


public final class QuoteViewGeneralAddTaskEng2Action extends Action {


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
        DynaValidatorForm qvgate1 = (DynaValidatorForm) form;
        
        String[] targets = (String[]) qvgate1.get("targets");  
        String all = (String) qvgate1.get("all"); //the all languages box
        String engPrelim = (String) qvgate1.get("engPrelim"); 
        String engOther = (String) qvgate1.get("engOther"); 
        String engVerification = (String) qvgate1.get("engVerification");
        String eng0 = (String) (qvgate1.get("eng0"));  
        String eng1 = (String) (qvgate1.get("eng1"));  
        String eng2 = (String) (qvgate1.get("eng2"));  
        String eng3 = (String) (qvgate1.get("eng3"));  
        String eng4 = (String) (qvgate1.get("eng4"));  
        String eng5 = (String) (qvgate1.get("eng5"));  
        String eng6 = (String) (qvgate1.get("eng6"));  
        String eng7 = (String) (qvgate1.get("eng7"));  
        String eng8 = (String) (qvgate1.get("eng8")); 
        String eng9 = (String) (qvgate1.get("eng9"));
        String[] engTaskOptions = QuoteService.getInstance().getEngTaskOptions();
        
        //get the user's chosen source array for later use in case new target "new" needs to be created
        HttpSession session = request.getSession(false);
        SourceDoc[] sources = (SourceDoc[]) session.getAttribute("sourceArray");
                
        if(!all.equals("on")) { //if specific to only a few targets
            //for each target, add the new target to db and each target's new engineering tasks selected from form
            for(int i = 0; i < targets.length; i++) {          
                Set engTasks = new HashSet(); //list of new engTasks

                //need target doc and source doc to add tasks to it
                TargetDoc td = ProjectService.getInstance().getSingleTargetDoc(Integer.valueOf(targets[i]));
                SourceDoc sd = td.getSourceDoc();
                Project p = sd.getQuote().getProject();

                if(eng1.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                    et.setTargetDoc(td);
                    et.setCurrency(p.getCompany().getCcurrency());
                    et.setTaskName(engTaskOptions[1-1]);
                    et.setOrderNum(new Integer(1));
                    engTasks.add(et);
                }

                if(eng2.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                    et.setTargetDoc(td);
                     et.setCurrency(p.getCompany().getCcurrency());
                    et.setTaskName(engTaskOptions[2-1]);
                    et.setOrderNum(new Integer(2));
                    engTasks.add(et);
                }                
                
                if(eng3.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                     et.setCurrency(p.getCompany().getCcurrency());
                    et.setTargetDoc(td);
                    et.setTaskName(engTaskOptions[3-1]);
                    et.setOrderNum(new Integer(3));
                    engTasks.add(et);
                }
                
                if(eng4.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                     et.setCurrency(p.getCompany().getCcurrency());
                    et.setTargetDoc(td);
                    et.setTaskName(engTaskOptions[4-1]);
                    et.setOrderNum(new Integer(4));
                    engTasks.add(et);
                }
                
                if(eng5.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                    et.setCurrency(p.getCompany().getCcurrency());
                    et.setTargetDoc(td);
                    et.setTaskName(engTaskOptions[5-1]);
                    et.setOrderNum(new Integer(5));
                    engTasks.add(et);
                }
                
                if(eng6.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                     et.setCurrency(p.getCompany().getCcurrency());
                    et.setTargetDoc(td);
                    et.setTaskName(engTaskOptions[6-1]);
                    et.setOrderNum(new Integer(6));
                    engTasks.add(et);
                }
                
                if(eng7.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                     et.setCurrency(p.getCompany().getCcurrency());
                    et.setTargetDoc(td);
                    et.setTaskName(engTaskOptions[7-1]);
                    et.setOrderNum(new Integer(7));
                    engTasks.add(et);
                }
                if(eng9.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                     et.setCurrency(p.getCompany().getCcurrency());
                    et.setTargetDoc(td);
                    et.setTaskName(engTaskOptions[9-1]);
                    et.setOrderNum(new Integer(9));
                    engTasks.add(et);
                }
                
                if(eng8.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setNotes((String) (qvgate1.get("engOtherText")));
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                     et.setCurrency(p.getCompany().getCcurrency());
                    et.setTargetDoc(td);
                    et.setTaskName(engTaskOptions[8-1]);
                    et.setOrderNum(new Integer(8));
                    engTasks.add(et);
                }
                
             //for each EngTask, add it to db and link it to this targetDoc
            for(Iterator iter = engTasks.iterator(); iter.hasNext();) {
                EngTask et = (EngTask) iter.next();
                
                //link this engTask to the targetDoc; add new engTask to db
                Integer z = ProjectService.getInstance().linkTargetDocEngTask(td, et); 
            }
                
            }
        } //end if all.equals("on")
        else { //add tasks to all targets (actually a single "all" target)
            //for each source, add the new target to db and each target's new engineering tasks selected from form
            for(int i = 0; i < sources.length; i++) {  
            Set engTasks = new HashSet(); //list of new engTasks

            //target language's new object
            TargetDoc td = new TargetDoc(new HashSet(), new HashSet(), new HashSet(), new HashSet());
            td.setLanguage("All");
            SourceDoc sd = sources[i];
             Project p = sd.getQuote().getProject();
            //link this target Doc to the source Doc; add new target Doc to db
            Integer x = ProjectService.getInstance().linkSourceDocTargetDoc(sd, td); 
            
                if(eng1.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                    et.setCurrency(p.getCompany().getCcurrency());
                    et.setTargetDoc(td);
                    et.setTaskName(engTaskOptions[1-1]);
                    et.setOrderNum(new Integer(1));
                    engTasks.add(et);
                }

                if(eng2.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                    et.setCurrency(p.getCompany().getCcurrency());
                    et.setTargetDoc(td);
                    et.setTaskName(engTaskOptions[2-1]);
                    et.setOrderNum(new Integer(2));
                    engTasks.add(et);
                }                
                
                if(eng3.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                    et.setCurrency(p.getCompany().getCcurrency());
                    et.setTargetDoc(td);
                    et.setTaskName(engTaskOptions[3-1]);
                    et.setOrderNum(new Integer(3));
                    engTasks.add(et);
                }
                
                if(eng4.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                     et.setCurrency(p.getCompany().getCcurrency());
                    et.setTargetDoc(td);
                    et.setTaskName(engTaskOptions[4-1]);
                    et.setOrderNum(new Integer(4));
                    engTasks.add(et);
                }
                
                if(eng5.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                    et.setCurrency(p.getCompany().getCcurrency());
                    et.setTargetDoc(td);
                    et.setTaskName(engTaskOptions[5-1]);
                    et.setOrderNum(new Integer(5));
                    engTasks.add(et);
                }
                
                if(eng6.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                     et.setCurrency(p.getCompany().getCcurrency());
                    et.setTargetDoc(td);
                    et.setTaskName(engTaskOptions[6-1]);
                    et.setOrderNum(new Integer(6));
                    engTasks.add(et);
                }
                
                if(eng7.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                    et.setCurrency(p.getCompany().getCcurrency());
                    et.setTargetDoc(td);
                    et.setTaskName(engTaskOptions[7-1]);
                    et.setOrderNum(new Integer(7));
                    engTasks.add(et);
                }
                if(eng9.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                     et.setCurrency(p.getCompany().getCcurrency());
                    et.setTargetDoc(td);
                    et.setTaskName(engTaskOptions[9-1]);
                    et.setOrderNum(new Integer(9));
                    engTasks.add(et);
                }
                
                if(eng8.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setNotes((String) (qvgate1.get("engOtherText")));
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                     et.setCurrency(p.getCompany().getCcurrency());
                    et.setTargetDoc(td);
                    et.setTaskName(engTaskOptions[8-1]);
                    et.setOrderNum(new Integer(8));
                    engTasks.add(et);
                }
            
             //for each EngTask, add it to db and link it to this targetDoc
            for(Iterator iter = engTasks.iterator(); iter.hasNext();) {
                EngTask et = (EngTask) iter.next();
                
                //link this engTask to the targetDoc; add new engTask to db
                Integer z = ProjectService.getInstance().linkTargetDocEngTask(td, et); 
            }
        }
        }//end else (new target per source with tasks)
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
