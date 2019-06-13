/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.project;

import app.quote.Quote1;
import app.quote.QuoteService;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

/**
 *
 * @author abhisheksingh
 */
public class ProjectViewNotesAddUpdateAction extends Action {

    // ----------------------------------------------------- Instance Variables
    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log
            = LogFactory.getLog("org.apache.struts.webapp.Example");

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

        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));

        //START check for login (security)
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)

        //START get id of current quote from either request, attribute, or cookie 
        //id of quote from request
        String quoteId = null;
        quoteId = request.getParameter("quoteViewId");

        //check attribute in request
        if (quoteId == null) {
            quoteId = (String) request.getAttribute("quoteViewId");
        }

        //id of quote from cookie
        if (quoteId != null) {
            request.setAttribute("quoteViewId", quoteId);
        }

        String projectId = request.getParameter("projectId");
        String noteId = request.getParameter("noteId");

        request.setAttribute("quoteViewId", quoteId);
        request.setAttribute("projectId", projectId);

        //END get id of current quote from either request, attribute, or cookie               
        //get quote to update
        if (projectId == null & quoteId != null) {
            Integer qid = Integer.valueOf(quoteId);
            Quote1 q = QuoteService.getInstance().getSingleQuote(qid);
            projectId = q.getProject().getProjectId().toString();

        }

        String ppr = request.getParameter("ppr");
        if (null != ppr) {
            String pprcb = StandardCode.getInstance().noNull(request.getParameter("pprcb"));
            Project p = ProjectService.getInstance().getSingleProject(Integer.parseInt(projectId));
            if (pprcb.equalsIgnoreCase("on")) {
                p.setPostProjectReview(Boolean.TRUE);

            } else {
                p.setPostProjectReview(Boolean.FALSE);
            }
            ProjectService.getInstance().updateProject(p);

        } else {

            String deleteNote = request.getParameter("deleteNote");
            if (null != deleteNote) {
                if ("yes".equalsIgnoreCase(deleteNote)) {
                    Notes notes = ProjectService.getInstance().getSingleNotes(Integer.parseInt(noteId));
                    ProjectService.getInstance().deleteNotes(notes);
                    return (mapping.findForward("Success"));
                }
            }

            if (null == noteId) {
                noteId = "0";
            }
            String bgcolor = request.getParameter("bgcolor");
            Notes notes = new Notes();
            if (Integer.parseInt(noteId) > 0) {
                notes = ProjectService.getInstance().getSingleNotes(Integer.parseInt(noteId));
                if (!bgcolor.equalsIgnoreCase("")) {
                    notes.setBgcolor(bgcolor);
                }
            } else {
                notes.setCreateDate(new Date(System.currentTimeMillis()));
                notes.setBgcolor(bgcolor);

            }

            //updated values of the quote
            // DynaValidatorForm qvn = (DynaValidatorForm) form;
            //String note = (String) qvn.get("note");
            //the new note data from the form
            String note = request.getParameter("note");
            //update the new values
            notes.setNotes(note);

            notes.setAuthor(u.getFirstName() + " " + u.getLastName());

            notes.setEditDate(new Date(System.currentTimeMillis()));
            notes.setProjectId(Integer.parseInt(projectId));
            if (null != quoteId) {
                notes.setQuoteId(Integer.parseInt(quoteId));
            }
            notes.setUserid(u.getUserId());
//                                                notes.set
//                                        notes.set
            ProjectService.getInstance().addUpdateNotes(notes);
            //place quote into attribute for display
        }
        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }

}
