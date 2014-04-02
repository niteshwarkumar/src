/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.quote;

import app.security.SecurityService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.apache.struts.action.Action;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.DynaValidatorForm;

/**
 *
 * @author Niteshwar
 */
public class ClientProductAddEditWizard extends Action {


    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {


        MessageResources messages = getResources(request);

        // save errors
        ActionMessages errors = new ActionMessages();




         //START check for login (security)
        if(!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        
        DynaValidatorForm qvg = (DynaValidatorForm) form;
           String type=(String) (qvg.get("Type"));


             // Forward control to the specified success URI
        return (mapping.findForward("Success"));

    }
}

