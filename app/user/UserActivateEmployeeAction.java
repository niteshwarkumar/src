//HrViewGeneralUpdateAction.java gets updated hr info
//from a form.  It then uploads the new values for
//this employee to the db //NOTE not completed yet

package app.user;

import app.admin.AdminService;
import app.client.Client;
import app.client.ClientService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import app.user.*;
import app.security.*;
import app.standardCode.StringCleaner;
import java.util.List;


public final class UserActivateEmployeeAction extends Action {


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
        
	//which user to update from hidden value in form
        String id = request.getParameter("userViewId");  
        //get the user to be updated from db
        User u = UserService.getInstance().getSingleUser(Integer.valueOf(id));
        //'false' or 'true'
        String activateFlag = request.getParameter("activateFlag");
        
       
            u.setCurrentEmployee(activateFlag);
            u.setDropDown(activateFlag);
       
        //set updated values to db
        StringCleaner sc = new StringCleaner();
        UserService.getInstance().updateUser(u);
        if(activateFlag.equalsIgnoreCase("false")){
            String userFullName = sc.convertToAscii(u.getFirstName()+" "+u.getLastName(),false);
            AdminService as = new  AdminService();
            as.deleteTicker(Integer.valueOf(id));
            ClientService cs = new ClientService();
            List clientList  = cs.getClientList();
            for(int i = 0; i< clientList.size();i++){
            Client c = new Client();
                boolean update=false;
            if(sc.convertToAscii(c.getProject_mngr(), false).equalsIgnoreCase(userFullName)){c.setProject_mngr("House");update=true;}
            if(sc.convertToAscii(c.getBackup_pm(), false).equalsIgnoreCase(userFullName)){c.setBackup_pm("House");update=true;}
            if(sc.convertToAscii(c.getSales(), false).equalsIgnoreCase(userFullName)){c.setSales("House");update=true;}
            if(sc.convertToAscii(c.getSales_rep(), false).equalsIgnoreCase(userFullName)){c.setSales_rep("House");update=true;}
            if(sc.convertToAscii(c.getMain_dtp(), false).equalsIgnoreCase(userFullName)){c.setMain_dtp("House");update=true;}
            if(sc.convertToAscii(c.getMain_engineer(), false).equalsIgnoreCase(userFullName)){c.setMain_engineer("House");update=true;}
            if(sc.convertToAscii(c.getOther_engineer(), false).equalsIgnoreCase(userFullName)){c.setOther_engineer("House");update=true;}
            if(sc.convertToAscii(c.getOther_dtp(), false).equalsIgnoreCase(userFullName)){c.setOther_dtp("House");update=true;}
            if(update)
            {cs.updateClient(c);}
            }
            
        
        
        }
        
        //place user id into request for display
        //request.setAttribute("user", u);
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
