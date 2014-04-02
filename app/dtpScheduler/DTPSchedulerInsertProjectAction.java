//ClientViewGeneralAction.java gets the current client from the db
//It then places that client into the client form for
//display in the jsp page. It then places location values into
//cookies

package app.dtpScheduler;

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
import app.user.*;
import app.db.*;
import app.project.*;
import app.workspace.*;
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;

 
public final class DTPSchedulerInsertProjectAction extends Action {


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
       
        //get client to edit
        DTPScheduler dtp = new DTPScheduler();
        int projectid = 1;
        //if(request.getParameter("pid")!=null && !"".equals(request.getParameter("pid"))){
        
        //System.out.println("ALEXXXX pid:"+request.getParameter("prid"));
        //System.out.println("ALEXXXX operatorEstEndDate:"+request.getParameter("operatorEstEndDate"));
        //System.out.println("ALEXXXX status:"+request.getParameter("status"));
        projectid = Integer.parseInt(request.getParameter("prid"));
        //}
        dtp.setID_Project(projectid);
        dtp.setOperatorEndDate(request.getParameter("operatorEstEndDate"));  
        int priority = 1;
        if(request.getParameter("priority")!=null && !"".equals(request.getParameter("priority"))){
            priority = Integer.parseInt(request.getParameter("priority"));
        }
        dtp.setPriority(priority);   
        dtp.setOperatorStartDate(request.getParameter("operatorStartDate"));   
        dtp.setEndDate(request.getParameter("endDate"));   
        dtp.setStartDate(request.getParameter("startDate")); 
        dtp.setStatus(request.getParameter("status"));
        dtp.setOperatorName(request.getParameter("operatorName")); 
        dtp.setStage(request.getParameter("stage"));
        
        
        //for each source
        String units = "";
        //String languages = "";
        double volume = 0;
        Project p = ProjectService.getInstance().getSingleProject(new Integer(projectid));
        Set sources = p.getSourceDocs();
        for(Iterator sourceIter = sources.iterator(); sourceIter.hasNext();) {
            SourceDoc sd = (SourceDoc) sourceIter.next();
            for(Iterator linTargetIter = sd.getTargetDocs().iterator(); linTargetIter.hasNext();) {
                TargetDoc td = (TargetDoc) linTargetIter.next();                
                //for each dtp Task of this target
                for(Iterator dtpTaskIter = td.getDtpTasks().iterator(); dtpTaskIter.hasNext();) {
                    DtpTask dt = (DtpTask) dtpTaskIter.next();
                        if(dt.getUnits()!=null && units.indexOf(dt.getUnits())==-1) {
                            units += dt.getUnits()+", ";
                        }
                       // if(dt.getTargetLanguage()!=null && languages.indexOf(dt.getTargetLanguage())==-1) {
                        //    languages += dt.getTargetLanguage()+", ";
                        //}
                         if(dt.getTotal()!=null){   
                            volume += dt.getTotal().doubleValue();
                         }
                    }
                }
            }
        //Clean up output
        if(units.endsWith(", ")){
            units = units.substring(0,units.length()-2);
        }
        //if(languages.endsWith(", ")){
         //   languages = languages.substring(0,languages.length()-2);
        //}
        
        dtp.setVolume(""+volume);
        dtp.setUnits(units);
        dtp.setApplication(p.getDeliverableApplication());
        
        
        DTPSchedulerService.getInstance().addDtpProject(dtp);   
                       
                       
                       
        
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}

