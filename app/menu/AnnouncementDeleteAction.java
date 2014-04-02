/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.menu;

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
import app.user.*;
import app.db.*;
import app.security.*;
import app.project.*;
import app.standardCode.*;
import net.sf.hibernate.Session;
import org.apache.struts.validator.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 *
 * @author Neil
 */
public class AnnouncementDeleteAction extends Action{
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


        Integer announce=Integer.parseInt(request.getParameter("announce"));

        System.out.println(announce);
        Announcement a=MenuService.getInstance().getSingleAnnouncement(announce);
        MenuService.getInstance().unlinkAnnouncement(a);

       return (mapping.findForward("Success"));
    }
}