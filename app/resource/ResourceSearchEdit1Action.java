//ResourceSearchEdit1Action.java prepares the form
//for collecting search criteria (places form into session)

package app.resource;

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
import app.security.*;
import app.standardCode.StandardCode;
import org.apache.struts.validator.*;


public final class ResourceSearchEdit1Action extends Action {


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
        
        //load resource info for searching
        if(request.getParameter("appVen")!=null){                   
        response.addCookie(StandardCode.getInstance().setCookie("appVen", request.getParameter("appVen")));
        request.setAttribute("appVen", request.getParameter("appVen"));
        }else{
        response.addCookie(StandardCode.getInstance().setCookie("appVen", "N"));
        request.setAttribute("appVen", "N");
        }
        


        DynaValidatorForm rs = (DynaValidatorForm) form;

               
        //START prepare the rateScoreLanguage Array with the number of categories from the db
        List rscs = ResourceService.getInstance().getRateScoreCategoryList();
        
        String[] fromFormRsc = (String[]) rs.get("resourceSearchScoresLin");
        boolean reset = true;
        for(int i = 0; i < fromFormRsc.length; i++) {
            if(!fromFormRsc[i].equals("-1")) { //reset flag set
                reset = false;
            }
        }
        if(reset) {
            String[] rateScoreLanguagesArray = new String[rscs.size()];
            ListIterator iter = rscs.listIterator();
            for(int i = 0; i < rateScoreLanguagesArray.length; i++) {
                RateScoreCategory rsc = (RateScoreCategory) iter.next(); 
                rateScoreLanguagesArray[i] = new String("");


                rs.set("resourceSearchScoresLin", rateScoreLanguagesArray);
            }
        }//END prepare the rateScoreLanguage Array with the number of categories from the db
        
               
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
