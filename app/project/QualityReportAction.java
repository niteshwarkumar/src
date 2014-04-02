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
import app.security.*;

/**
 *
 * @author Niteshwar
 */
public class QualityReportAction  extends Action{

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
	//MessageResources messages = getResources(request);

	// save errors
	ActionMessages errors = new ActionMessages();

        //START check for login (security)
        if(!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        String id = request.getParameter("id");
        QualityReport qr = new QualityReport();

//        String id = request.getParameter("id");
//        String id = request.getParameter("id");
        String score = request.getParameter("text"+id);
        if(Integer.parseInt(id)==0){
        String yr = request.getParameter("yr");
        String newQuality = request.getParameter("newQuality");
        String newGoal = request.getParameter("newGoal");
        score = request.getParameter("newActual");

        qr.setQuality(newQuality);
        qr.setQualitydesc(newGoal);
//        qr.setQualityscore(score);
        qr.setQualityyear(yr);
        } else{
        qr = ProjectService.getInstance().getQualityReport(Integer.parseInt(id));
        
        }
        qr.setQualityscore(score);
        ProjectService.getInstance().updateQulityReport(qr);
      String projectYear=request.getParameter("projectYear");
//        String clientViewId=request.getParameter("clientViewId");
        request.setAttribute("projectYear", projectYear);


        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
