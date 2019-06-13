//HomePageAction.java prepares the display for homepage info
//such as announcements

package app.menu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import java.util.*;
import app.user.*;
import app.security.*;

public final class HomePageAction extends Action {


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
	
        //get the current user for displaying personal info, such as "My Projects"
        User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
   
        
        //get announcements
        List announcements = app.menu.MenuService.getInstance().getAnnouncementList();        
        //place announcements into request for display
        request.setAttribute("announcements", announcements);
        long end = System.currentTimeMillis();
        
   /*     Date d = new Date();
        int currentMonth = d.getMonth();
        int currentYear = d.getYear();
        if(request.getParameter("abscencesMonth")!=null){
            currentMonth = Integer.parseInt(request.getParameter("abscencesMonth"));
            currentYear = Integer.parseInt(request.getParameter("abscencesYear"));
        }
        if(currentYear<1000){
            currentYear+=1900;
        }
        if(currentMonth==-1){
            request.getSession(false).setAttribute("abscencesMonth","11");
            request.getSession(false).setAttribute("abscencesYear",(currentYear)+"");
        }else if(currentMonth==12){
            request.getSession(false).setAttribute("abscencesMonth","0");
            request.getSession(false).setAttribute("abscencesYear",(currentYear)+"");
            
        }else{
            request.getSession(false).setAttribute("abscencesMonth",currentMonth+"");
            request.getSession(false).setAttribute("abscencesYear",(currentYear)+"");
        }*/
        
        
        
        
  
  
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}


