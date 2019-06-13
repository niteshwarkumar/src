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
import app.workspace.*;
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;

 
public final class DTPSchedulerModifyProjectAction extends Action {


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
        
        
        //if(request.getParameter("pid")!=null && !"".equals(request.getParameter("pid"))){
        
        ////System.out.println("ALEXXXX pid:"+request.getParameter("prid"));
        ////System.out.println("ALEXXXX operatorEstEndDate:"+request.getParameter("operatorEstEndDate"));
        ////System.out.println("ALEXXXX status:"+request.getParameter("status"));
        int dtpId = Integer.parseInt(request.getParameter("dtpId"));
        DTPScheduler dtpSched = DTPSchedulerService.getInstance().getSingleDTPProject(dtpId);

        //}
        
        dtpSched.setID_Project(Integer.parseInt(request.getParameter("projectid")));
        dtpSched.setEndDate(request.getParameter("endDate"));   
        dtpSched.setStartDate(request.getParameter("startDate"));
        dtpSched.setVolume(request.getParameter("volume"));
        dtpSched.setApplication(request.getParameter("application"));
        dtpSched.setUnits(request.getParameter("units"));
        dtpSched.setStage(request.getParameter("stage"));
        
        
        if("A".equals(dtpSched.getStatus())){
            int priority = 1;
            if(request.getParameter("priority")!=null && !"".equals(request.getParameter("priority"))){
                priority = Integer.parseInt(request.getParameter("priority"));
            }
            dtpSched.setPriority(priority);   
            dtpSched.setOperatorStartDate(request.getParameter("operatorStartDate"));   
            dtpSched.setOperatorEndDate(request.getParameter("operatorEstEndDate"));
            dtpSched.setOperatorName(request.getParameter("operatorName"));
        }
        
               
         DTPSchedulerService.getInstance().modifyDtpProject(dtpSched);   
                       
                       
                       
        
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}

