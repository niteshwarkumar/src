//ProjectViewQualityUpdateAction.java updates the quality
//control objects for this project

package app.project;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;
import app.client.*;
import app.extjs.helpers.ClientHelper;
import app.user.User;
import app.user.UserService;
//import com.sun.rsasign.c;


public final class ProjectViewQualityUpdateAction extends Action {


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
         User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
        //get qualities to update
        String userName=u.getFirstName()+" "+u.getLastName();
        DynaValidatorForm qvg = (DynaValidatorForm) form;
        Quality[] qualities = (Quality[]) qvg.get("qualities");    
        Integer projectId = null;
        //process each updated quality to db
        for(int i = 0; i < qualities.length; i++) {
                Quality q = qualities[i];
                projectId = q.getProject().getProjectId();
                //START update quality dates
                String qualityDateRaised = request.getParameter("qualityDateRaised" + String.valueOf(i));
                if(qualityDateRaised.length() >= 1) {
                    q.setDateRaised(DateService.getInstance().convertDate(qualityDateRaised).getTime());
                }
                String qualityDateClosed = request.getParameter("qualityDateClosed" + String.valueOf(i));
                if(qualityDateClosed.length() >= 1) {
                    q.setDateClosed(DateService.getInstance().convertDate(qualityDateClosed).getTime());
                }
                //END update quality dates
            
                //upload quality changes to db
                ProjectService.getInstance().updateQuality(q);
        }
        
        if(request.getParameter("projectInformalsJSON")!=null){
            
          ClientHelper.updateProjectInformals(Integer.parseInt(request.getParameter("projectId")),request.getParameter("projectInformalsJSON"),userName);
         
        }
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
