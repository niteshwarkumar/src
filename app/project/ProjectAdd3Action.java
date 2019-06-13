//ProjectAdd3Action.java collects values related to 
//the project such as project manager from this part of the wizard
//It then adds this info to the db

package app.project;

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
import app.db.*;
import app.client.*;
import app.quote.*;
import app.project.*;
import app.workspace.*;
import app.standardCode.*;
import app.security.*;

public final class ProjectAdd3Action extends Action {


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
	
        //values for updating the project
        DynaValidatorForm qae3 = (DynaValidatorForm) form;
        
        String projectDescription = (String) (qae3.get("projectDescription"));  
        String projectRequirements = (String) (qae3.get("projectRequirements"));
        String projectManager = (String) (qae3.get("projectManager"));  
        String beforeWorkTurn = (String) (qae3.get("beforeWorkTurn"));
        String beforeWorkTurnUnits = (String) (qae3.get("beforeWorkTurnUnits"));
        String afterWorkTurn = (String) (qae3.get("afterWorkTurn"));
        String afterWorkTurnUnits = (String) (qae3.get("afterWorkTurnUnits"));
        String afterWorkTurnReason = (String) (qae3.get("afterWorkTurnReason"));
        
        //need the project, quote to update project
        String projectId = StandardCode.getInstance().getCookie("projectAddId", request.getCookies());
        Project p = ProjectService.getInstance().getSingleProject(Integer.valueOf(projectId)); 
        
        //update values
        p.setProjectDescription(projectDescription);
        p.setProjectRequirements(projectRequirements);        
        p.setPm(projectManager);        
        p.setBeforeWorkTurn(beforeWorkTurn);
        p.setBeforeWorkTurnUnits(beforeWorkTurnUnits);
        p.setAfterWorkTurn(afterWorkTurn);
        p.setAfterWorkTurnUnits(afterWorkTurnUnits);
        p.setAfterWorkTurnReason(afterWorkTurnReason);
        
        //Set PM RATE 
        String clientViewId = StandardCode.getInstance().getCookie("projectAddClientId", request.getCookies());
        Client c = ClientService.getInstance().getSingleClient(Integer.valueOf(clientViewId));
        ClientLanguagePair[] clp = null;
        if(c!=null){
            if(c.getClientLanguagePairs()!=null){
                clp = (ClientLanguagePair[])c.getClientLanguagePairs().toArray(new ClientLanguagePair[0]);
                
            }
        }
       if(clp!=null){ 
         
        for(int z=0; z<clp.length; z++){      
             
                            if( clp[z].getTask()!=null && clp[z].getTask().equals("PM - Percentage")){                            
                                p.setPmPercent(clp[z].getRate());
                                break;
                            }
                        }
       }
        
        //update project to db
        ProjectService.getInstance().updateProject(p);            
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
