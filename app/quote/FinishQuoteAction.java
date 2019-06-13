/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.quote;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;




import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import com.sun.org.apache.xml.internal.dtm.ref.DTMDefaultBaseIterators.ParentIterator;
import javax.servlet.http.HttpSession;
import org.apache.struts.util.MessageResources;
/**
 *
 * @author Neil
 */
public class FinishQuoteAction extends Action{

    
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {


        MessageResources messages = getResources(request);

      
             Integer quote1Id =0 ;
             response.addCookie(StandardCode.getInstance().setCookie("quoteViewId", "0"));
              String quoteId = StandardCode.getInstance().getCookie("quoteViewId", request.getCookies());
                  HttpSession session = request.getSession(false);
      session.setAttribute("quoteViewId", String.valueOf(quote1Id));
        request.setAttribute("quoteViewId", String.valueOf(quote1Id));





              User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));

             //System.out.println("Qu0te id "+quoteId);
         if(u.getuserType() != null){
      if(u.getuserType().equalsIgnoreCase("client"))
          //href=\"javascript:parent.openSingleContactWindow
     //   javascript.openQuoteWindowAfterSubmit();

         return (mapping.findForward("ClientSuccess"));
         }
    return (mapping.findForward("Success"));
    





    }
}
