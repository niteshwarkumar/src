/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.quote;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;



import app.standardCode.StandardCode;

/**
 *
 * @author Neil
 */
public class ClientQuoteSummaryAction extends Action{


       private Log log =
        LogFactory.getLog("org.apache.struts.webapp.Example");


          public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
	throws Exception {

	// Extract attributes we will need


                MessageResources messages = getResources(request);

	// save errors
	ActionMessages errors = new ActionMessages();
         String quoteId = StandardCode.getInstance().getCookie("quoteAddId", request.getCookies());
             String quoteViewId =quoteId ;
              //System.out.println("quoteViewId"+quoteViewId);

               return (mapping.findForward("Success"));

          }
}
