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





/**
 *
 * @author Neil
 */
public class ClientQuoteAddUpload1 extends Action{
     public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {



    return (mapping.findForward("Success"));
}
}
