/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;


/**
 *
 * @author Neil
 */
public class ResourceAddEditNew extends Action {

public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
	throws Exception {

    return (mapping.findForward("Success"));


}
}
