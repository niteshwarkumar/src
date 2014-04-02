//ProjectViewTeamAddTaskDtp2Action.java gets the added tasks
//per target and adds them to the db

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


public final class ProjectViewTeamAddTaskDtp2Action extends Action {


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
        String dtpCom = (String) qvgate1.get("dtpCom");
        String dtpDesk = (String) qvgate1.get("dtpDesk");
        String dtpFormatting = (String) qvgate1.get("dtpFormatting"); 
        String dtpGraphics = (String) qvgate1.get("dtpGraphics");
        String dtpSpecial = (String) qvgate1.get("dtpSpecial");
        String dtpOther = (String) qvgate1.get("dtpOther");
        String dtp0 = (String) (qvgate1.get("dtp0"));  
        String dtp1 = (String) (qvgate1.get("dtp1"));  
        String dtp2 = (String) (qvgate1.get("dtp2"));  
        String dtp3 = (String) (qvgate1.get("dtp3"));  
        String dtp4 = (String) (qvgate1.get("dtp4"));  
        String dtp5 = (String) (qvgate1.get("dtp5"));  
        String dtp6 = (String) (qvgate1.get("dtp6"));  
        String dtp7 = (String) (qvgate1.get("dtp7"));  
        String[] dtpTaskOptions = ProjectService.getInstance().getDtpTaskOptions();
        
        //get the user's chosen source array for later use in case new target "new" needs to be created
        HttpSession session = request.getSession(false);
        SourceDoc[] sources = (SourceDoc[]) session.getAttribute("sourceArray");
                
        if(!all.equals("on")) { //if specific to only a few targets
            //for each target, add the new target to db and each target's new DTP tasks selected from form
            for(int i = 0; i < targets.length; i++) {          
                Set dtpTasks = new HashSet(); //list of new dtpTasks

                //need target doc and source doc to add tasks to it
                TargetDoc td = ProjectService.getInstance().getSingleTargetDoc(Integer.valueOf(targets[i]));
                SourceDoc sd = td.getSourceDoc();

                if(dtp1.equals("on")) { //if checked in form, then add this task to target Doc
                    DtpTask dt = new DtpTask();
                    dt.setSourceLanguage(sd.getLanguage());
                    dt.setTargetLanguage(td.getLanguage());
                    dt.setPostQuote("true");
                    dt.setTargetDoc(td);
                    dt.setTaskName(dtpTaskOptions[1-1]);
                    dt.setOrderNum(new Integer(1));
                    dtpTasks.add(dt);
                }

                if(dtp2.equals("on")) { //if checked in form, then add this task to target Doc
                    DtpTask dt = new DtpTask();
                    dt.setSourceLanguage(sd.getLanguage());
                    dt.setTargetLanguage(td.getLanguage());
                    dt.setPostQuote("true");
                    dt.setTargetDoc(td);
                    dt.setTaskName(dtpTaskOptions[2-1]);
                    dt.setOrderNum(new Integer(2));
                    dtpTasks.add(dt);
                }
                
                if(dtp3.equals("on")) { //if checked in form, then add this task to target Doc
                    DtpTask dt = new DtpTask();
                    dt.setSourceLanguage(sd.getLanguage());
                    dt.setTargetLanguage(td.getLanguage());
                    dt.setPostQuote("true");
                    dt.setTargetDoc(td);
                    dt.setTaskName(dtpTaskOptions[3-1]);
                    dt.setOrderNum(new Integer(3));
                    dtpTasks.add(dt);
                }

                if(dtp4.equals("on")) { //if checked in form, then add this task to target Doc
                    DtpTask dt = new DtpTask();
                    dt.setSourceLanguage(sd.getLanguage());
                    dt.setTargetLanguage(td.getLanguage());
                    dt.setPostQuote("true");
                    dt.setTargetDoc(td);
                    dt.setTaskName(dtpTaskOptions[4-1]);
                    dt.setOrderNum(new Integer(4));
                    dtpTasks.add(dt);
                }
                
                if(dtp5.equals("on")) { //if checked in form, then add this task to target Doc
                    DtpTask dt = new DtpTask();
                    dt.setSourceLanguage(sd.getLanguage());
                    dt.setTargetLanguage(td.getLanguage());
                    dt.setPostQuote("true");
                    dt.setTargetDoc(td);
                    dt.setTaskName(dtpTaskOptions[5-1]);
                    dt.setOrderNum(new Integer(5));
                    dtpTasks.add(dt);
                }
                
                if(dtp6.equals("on")) { //if checked in form, then add this task to target Doc
                    DtpTask dt = new DtpTask();
                    dt.setSourceLanguage(sd.getLanguage());
                    dt.setTargetLanguage(td.getLanguage());
                    dt.setPostQuote("true");
                    dt.setTargetDoc(td);
                    dt.setTaskName(dtpTaskOptions[6-1]);
                    dt.setOrderNum(new Integer(6));
                    dtpTasks.add(dt);
                }
                
                if(dtp7.equals("on")) { //if checked in form, then add this task to target Doc
                    DtpTask dt = new DtpTask();
                    dt.setNotes((String) (qvgate1.get("dtpOtherText")));
                    dt.setSourceLanguage(sd.getLanguage());
                    dt.setTargetLanguage(td.getLanguage());
                    dt.setPostQuote("true");
                    dt.setTargetDoc(td);
                    dt.setTaskName(dtpTaskOptions[7-1]);
                    dt.setOrderNum(new Integer(7));
                    dtpTasks.add(dt);
                }
                
             //for each DtpTask, add it to db and link it to this targetDoc
            for(Iterator iter = dtpTasks.iterator(); iter.hasNext();) {
                DtpTask dt = (DtpTask) iter.next();
                
                //link this dtpTask to the targetDoc; add new dtpTask to db
                Integer z = ProjectService.getInstance().linkTargetDocDtpTask(td, dt); 
            }
                
            }
        } //end if all.equals("on")
        else { //add tasks to all targets (actually a single "all" target)
            //for each source, add the new target to db and each target's new dtp tasks selected from form
            for(int i = 0; i < sources.length; i++) {  
            Set dtpTasks = new HashSet(); //list of new dtpTasks

            //target language's new object
            TargetDoc td = new TargetDoc(new HashSet(), new HashSet(), new HashSet(), new HashSet());
            td.setLanguage("All");
            SourceDoc sd = sources[i];
            
            //link this target Doc to the source Doc; add new target Doc to db
            Integer x = ProjectService.getInstance().linkSourceDocTargetDoc(sd, td); 
            
                if(dtp1.equals("on")) { //if checked in form, then add this task to target Doc
                    DtpTask dt = new DtpTask();
                    dt.setSourceLanguage(sd.getLanguage());
                    dt.setTargetLanguage(td.getLanguage());
                    dt.setPostQuote("true");
                    dt.setTargetDoc(td);
                    dt.setTaskName(dtpTaskOptions[1-1]);
                    dt.setOrderNum(new Integer(1));
                    dtpTasks.add(dt);
                }

                if(dtp2.equals("on")) { //if checked in form, then add this task to target Doc
                    DtpTask dt = new DtpTask();
                    dt.setSourceLanguage(sd.getLanguage());
                    dt.setTargetLanguage(td.getLanguage());
                    dt.setPostQuote("true");
                    dt.setTargetDoc(td);
                    dt.setTaskName(dtpTaskOptions[2-1]);
                    dt.setOrderNum(new Integer(2));
                    dtpTasks.add(dt);
                }
                
                if(dtp3.equals("on")) { //if checked in form, then add this task to target Doc
                    DtpTask dt = new DtpTask();
                    dt.setSourceLanguage(sd.getLanguage());
                    dt.setTargetLanguage(td.getLanguage());
                    dt.setPostQuote("true");
                    dt.setTargetDoc(td);
                    dt.setTaskName(dtpTaskOptions[3-1]);
                    dt.setOrderNum(new Integer(3));
                    dtpTasks.add(dt);
                }

                if(dtp4.equals("on")) { //if checked in form, then add this task to target Doc
                    DtpTask dt = new DtpTask();
                    dt.setSourceLanguage(sd.getLanguage());
                    dt.setTargetLanguage(td.getLanguage());
                    dt.setPostQuote("true");
                    dt.setTargetDoc(td);
                    dt.setTaskName(dtpTaskOptions[4-1]);
                    dt.setOrderNum(new Integer(4));
                    dtpTasks.add(dt);
                }
                
                if(dtp5.equals("on")) { //if checked in form, then add this task to target Doc
                    DtpTask dt = new DtpTask();
                    dt.setSourceLanguage(sd.getLanguage());
                    dt.setTargetLanguage(td.getLanguage());
                    dt.setPostQuote("true");
                    dt.setTargetDoc(td);
                    dt.setTaskName(dtpTaskOptions[5-1]);
                    dt.setOrderNum(new Integer(5));
                    dtpTasks.add(dt);
                }
                
                if(dtp6.equals("on")) { //if checked in form, then add this task to target Doc
                    DtpTask dt = new DtpTask();
                    dt.setSourceLanguage(sd.getLanguage());
                    dt.setTargetLanguage(td.getLanguage());
                    dt.setPostQuote("true");
                    dt.setTargetDoc(td);
                    dt.setTaskName(dtpTaskOptions[6-1]);
                    dt.setOrderNum(new Integer(6));
                    dtpTasks.add(dt);
                }
                
                if(dtp7.equals("on")) { //if checked in form, then add this task to target Doc
                    DtpTask dt = new DtpTask();
                    dt.setNotes((String) (qvgate1.get("dtpOtherText")));
                    dt.setSourceLanguage(sd.getLanguage());
                    dt.setTargetLanguage(td.getLanguage());
                    dt.setPostQuote("true");
                    dt.setTargetDoc(td);
                    dt.setTaskName(dtpTaskOptions[7-1]);
                    dt.setOrderNum(new Integer(7));
                    dtpTasks.add(dt);
                }
                
             //for each DtpTask, add it to db and link it to this targetDoc
            for(Iterator iter = dtpTasks.iterator(); iter.hasNext();) {
                DtpTask dt = (DtpTask) iter.next();
                
                //link this dtpTask to the targetDoc; add new dtpTask to db
                Integer z = ProjectService.getInstance().linkTargetDocDtpTask(td, dt); 
            }
        }
        }//end else (new target per source with tasks)
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
