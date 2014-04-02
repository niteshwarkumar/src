/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.comm;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Niteshwar
 */
public class LibraryAddDocumentPreAction  extends Action {


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
				 HttpServletResponse response)throws Exception

    {


	// save errors
	ActionMessages errors = new ActionMessages();

        //START check for login (security)
         String mainTab=request.getParameter("mainTab");
     
        
        if(!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)
        try {
           //PRIVS check that admin user is viewing this page
        if(!mainTab.equalsIgnoreCase("Forms")&&!mainTab.equalsIgnoreCase("Meetings")&&!mainTab.equalsIgnoreCase("Rates")&&
                !mainTab.equalsIgnoreCase("Regulatory")&&!mainTab.equalsIgnoreCase("Reports")&&!mainTab.equalsIgnoreCase("Requirements")){
//        if(!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "admin")) {
//            return (mapping.findForward("accessDenied"));
//        }//END PRIVS check that admin user is viewing this page
        } 
        } catch (Exception e) {
        }
        
        String clientViewId=request.getParameter("clientViewId");
         if(clientViewId!=null)
        {
            request.setAttribute("clientViewId", clientViewId);
        } 
        
        String libId=request.getParameter("id");
        if(request.getParameter("id")==null){
         libId=request.getParameter("libId");
        }
       
        String subTab=request.getParameter("subTab");
        request.setAttribute("id", libId);
        request.setAttribute("libId", libId);
        request.setAttribute("mainTab", mainTab);
        request.setAttribute("subTab", subTab);
      return (mapping.findForward("Success"));  
    }
}



        