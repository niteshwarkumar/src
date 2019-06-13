//ProjectViewNavigationAction.java processes the navigation buttons
//(the left and right arrow) within the project view menu.  It gets the
//location values from cookies.  It then determines which project to display
//next in project number order.  For example, if at first project, 000000, and previous
//button is pushed, go to 999999.  It then determines which page to forward to
//based on which tab is currently being viewed.

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
import java.util.*;
import app.user.*;
import app.db.*;
import app.workspace.*;
import app.client.*;
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;


public final class ProjectViewNavigationAction extends Action {


    public ProjectViewNavigationAction()
    {
        //System.out.println("ProjectViewNavigationAction constructor is calling***************************************");
    }
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
        
        //id of current project
        String projectId = StandardCode.getInstance().getCookie("projectViewId", request.getCookies());
        Integer currentId = Integer.valueOf(projectId);
        //System.out.println("currentId****************************************"+currentId);
                        
        //get where to move: <code>previous, stay, next</code>
        //where to move from request
	String move = request.getParameter("projectViewMove");   
      
        //for display in jsp of record-view location; e.g., record 2 of 400
        int current = 0;
        String projectRecordCurrent;
        String projectRecordTotal;
        
        //get current project being viewed from db
        Project currentP = ProjectService.getInstance().getSingleProject(currentId);
        Project newP = null; //save next new move
                
        //all active or complete projects in db in number order
        List projects = ProjectService.getInstance().getProjectListActiveComplete();
        
        //set the total now
        projectRecordTotal = String.valueOf(new Integer(projects.size()));
        
        //determine which project to display next
        if(move.equals("previous")) {
              for(ListIterator iter = projects.listIterator(); iter.hasNext(); ) {
                  newP = (Project) iter.next();
                  current++; //advance current record count
                  //if(newP.getStatus().equals("active")) {
                      if(newP.getNumber().equals(currentP.getNumber())) { //get previous before current
                          iter.previous();
                          if(iter.hasPrevious()) { //if this is not first
                            newP = (Project) iter.previous();
                            current--; //move back current record count
                          }
                          else { //it is the first; load last
                              ListIterator iterScroll = null;
                              for(iterScroll = projects.listIterator(); iterScroll.hasNext(); iterScroll.next()) {}
                              iterScroll.previous();
                              newP = (Project) iterScroll.next(); 
                              current = projects.size(); //set to last record
                          }                          
                          break; //stop looking for previous
                      }
                  //}
              }
        }
        else if(move.equals("next")) {
            for(ListIterator iter = projects.listIterator(); iter.hasNext(); ) {
                  newP = (Project) iter.next();
                  current++; //advance current record count
                  //if(newP.getStatus().equals("active")) {
                      if(newP.getNumber().equals(currentP.getNumber())) { //get next after current
                          if(iter.hasNext()) { //if this is not the last
                            newP = (Project) iter.next();
                            current++; //advance current record count
                          }
                          else { //it is the last; load first
                             ListIterator iterScroll = projects.listIterator();
                             newP = (Project) iterScroll.next();
                             current = 1; //set to first record
                          }
                          break; //stop looking for next
                      }
                //}
            }
        }
        
        //set the current record now
        projectRecordCurrent = String.valueOf(new Integer(current));        
        
        //set current record and total records into session and cookie
        request.getSession(false).setAttribute("projectRecordCurrent", projectRecordCurrent);
        request.getSession(false).setAttribute("projectRecordTotal", projectRecordTotal);
        response.addCookie(StandardCode.getInstance().setCookie("projectRecordCurrent", String.valueOf(new Integer(projectRecordCurrent))));
        response.addCookie(StandardCode.getInstance().setCookie("projectRecordTotal", String.valueOf(new Integer(projectRecordTotal))));
           
        
        //add this project id to request; this will remember which project is
        //to be viewed after the move
        request.setAttribute("projectViewId", String.valueOf(newP.getProjectId()));
        
        //determine where to forward based on tab value
        String projectViewTab = StandardCode.getInstance().getCookie("projectViewTab", request.getCookies());
        
	if(projectViewTab.equals("Overview")) {
            return (mapping.findForward("Overview"));
        }
        else if(projectViewTab.equals("Scope")) {
            return (mapping.findForward("Scope"));
        }
        else if(projectViewTab.equals("Notes")) {
            return (mapping.findForward("Notes"));
        }
        else if(projectViewTab.equals("Forms")) {
            return (mapping.findForward("Forms"));
        }
        else if(projectViewTab.equals("Team")) {
            return (mapping.findForward("Team"));
        }
        else if(projectViewTab.equals("Inspection")) {
            return (mapping.findForward("Inspection"));
        }
        else if(projectViewTab.equals("PO")) {
            return (mapping.findForward("PO"));
        }
        else if(projectViewTab.equals("Quality")) {
            return (mapping.findForward("Quality"));
        }
        else if(projectViewTab.equals("Accounting")) {
            return (mapping.findForward("Accounting"));
        }
        else if(projectViewTab.equals("Fee")) {
            return (mapping.findForward("Fee"));
        }
        
        //default location
        return (mapping.findForward("Overview"));
	
    }

}
