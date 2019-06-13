//HomePageAction.java prepares the display for homepage info
//such as announcements

package app.extjs.actions;
import app.extjs.helpers.HrHelper;
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


public final class UpdateAdminDropdowns extends Action {


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

        HrHelper.saveDropdowns(request);


        //response.setContentType("text/html");
        //response.setHeader("Cache-Control", "no-cache");
        // //System.out.println(actResponse.toXML());
        // PrintWriter out = response.getWriter();

        //out.println("<response success=\"true\"></response>");
        //out.flush();

        String origin = request.getParameter("ORIGIN");

            if("COMM".equals(origin)){
                return (mapping.findForward("calendar"));
            }else if("CLIENT".equals(origin)){
                return (mapping.findForward("client"));
            }else if("PROJECT".equals(origin)){
                return (mapping.findForward("project"));
            }else if("TEAM".equals(origin)){
                return (mapping.findForward("team"));
            }else if("QUOTE".equals(origin)){
                return (mapping.findForward("quote"));
            }else if("TASK".equals(origin)){
                return (mapping.findForward("task"));
            }else if("USER".equals(origin)){
                return (mapping.findForward("user"));
            }else if("QMS".equals(origin)){
                return (mapping.findForward("qms"));
            }else if("TRAINING".equals(origin)){
                return (mapping.findForward("training"));
            }else if("QUOTELECT".equals(origin)){
                return (mapping.findForward("quoteLect"));
            }

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
        //return null;
    }

}
