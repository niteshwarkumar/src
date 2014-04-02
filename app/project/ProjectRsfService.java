/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
import java.util.*;
import java.text.*;
import app.security.*;
import app.quote.*;
import app.standardCode.*;
import org.apache.struts.validator.*;


/**
 *
 * @author Neil
 */
public class ProjectRsfService  extends Action{

//ProjectViewOverviewAction.java gets the current project from the db
//It then places that project into the project form for
//display in the jsp page. It then places location values into
//cookies





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

        //START get id of current project from either request, attribute, or cookie
        //id of project from request
	String projectId = null;
	projectId = request.getParameter("projectViewId");

        //check attribute in request
        if(projectId == null) {
            projectId = (String) request.getAttribute("projectViewId");
        }

        //id of project from cookie
        if(projectId == null) {
            projectId = StandardCode.getInstance().getCookie("projectViewId", request.getCookies());
        }

        //default project to last if not in request or cookie
        if(projectId == null || "null".equals(projectId)) {
                List results = ProjectService.getInstance().getProjectList();

                ListIterator iterScroll = null;
                for(iterScroll = results.listIterator(); iterScroll.hasNext(); iterScroll.next()) {}
                   iterScroll.previous();
                   Project p = (Project) iterScroll.next();
                   projectId = String.valueOf(p.getProjectId());
         }

        Integer id = Integer.valueOf(projectId);

        //END get id of current project from either request, attribute, or cookie


        //get project to edit
        Project p = ProjectService.getInstance().getSingleProject(id);

        //START get current record number
        //all current projects in db in number order
        List projects = ProjectService.getInstance().getProjectListActive();

        //set the total now
        String projectRecordTotal = String.valueOf(new Integer(projects.size()));
        Project newP;
        Project currentP = p;
	int current = 0;

        //get the current record number (e.g., "3" of 40)
        for(ListIterator iter = projects.listIterator(); iter.hasNext(); ) {
                  newP = (Project) iter.next();
                  current++; //advance current record count
                  if(newP.getProjectId().equals(currentP.getProjectId())) { //check if current
                      break; //stop looking for current count
                  }
        }
        //set current record and total records into session and cookie
        request.getSession(false).setAttribute("projectRecordCurrent", String.valueOf(new Integer(current)));
        response.addCookie(StandardCode.getInstance().setCookie("projectRecordCurrent", String.valueOf(new Integer(current))));

        //END get current record number

        //get final quote for display
        Quote1 q = null;
        if(p.getQuotes() != null && p.getQuotes().size() > 0) { //this project many not have quotes
            q = QuoteService.getInstance().getLastQuote(p.getQuotes());
        }

        //load project info for editing
        DynaValidatorForm pvo = (DynaValidatorForm) form;
        if(p.getContact() != null) {
            pvo.set("contactId", String.valueOf(p.getContact().getClientContactId()));
        }
        else {
            pvo.set("contactId", "0");
        }
        pvo.set("PM", p.getPm());
        pvo.set("clientPO", p.getClientPO());
        if(q != null && q.getApprovalDate() != null) {
            pvo.set("approvalDate", DateFormat.getDateInstance(DateFormat.SHORT).format(q.getApprovalDate()));
        }
        if(p.getStartDate() != null) {
            pvo.set("startDate", DateFormat.getDateInstance(DateFormat.SHORT).format(p.getStartDate()));
        }
        if(p.getDueDate() != null) {
            pvo.set("dueDate", DateFormat.getDateInstance(DateFormat.SHORT).format(p.getDueDate()));
        }
        if(p.getDeliveryDate() != null) {
            pvo.set("deliveryDate", DateFormat.getDateInstance(DateFormat.SHORT).format(p.getDeliveryDate()));
        }
        pvo.set("deliveryMethod", p.getDeliveryMethod());
        pvo.set("status", p.getStatus());
        pvo.set("beforeWorkTurn", p.getBeforeWorkTurn());
        pvo.set("beforeWorkTurnUnits", p.getBeforeWorkTurnUnits());
        pvo.set("afterWorkTurn", p.getAfterWorkTurn());
        pvo.set("afterWorkTurnUnits", p.getAfterWorkTurnUnits());
        pvo.set("afterWorkTurnReason", p.getAfterWorkTurnReason());

        //place this project into the project form for display
        request.setAttribute("projectViewOverview", pvo);
        request.setAttribute("project", p);
        request.setAttribute("quote", q);

        if(p.getCancelled() != null && p.getCancelled().equals("true")) {
            request.setAttribute("cancelled", new String("checked"));
        }
        else {
            request.setAttribute("cancelled", new String(""));
        }

        //add this project id to cookies; this will remember the last project
        response.addCookie(StandardCode.getInstance().setCookie("projectViewId", projectId));

        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("projectViewTab", "Overview"));


       // List results = ProjectService.getInstance().getAllAdditionalDates(p.getProjectId());
      //  request.setAttribute("additionalDeliveryDates",results);
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
