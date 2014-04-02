/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.inteqa;

import app.security.SecurityService;
import org.apache.struts.util.MessageResources;
import app.standardCode.StandardCode.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.io.PrintWriter;
import java.util.*;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Niteshwar
 */
public class InDeliveryReqGrid extends Action {

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
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        List results = new ArrayList();
        String fromPorQ = request.getParameter("fromPorQ");
        String projectId = request.getParameter("projectId");
        INDelivery indel=InteqaService.getInstance().getINDelivery(Integer.parseInt(projectId));

        List inDel = InteqaService.getInstance().getInDeliveryReqGrid(indel.getId());
        for (int i = 0; i < inDel.size(); i++) {
            INDeliveryReq inDeliveryReq = (INDeliveryReq) inDel.get(i);
            JSONObject jo = new JSONObject();
            if (fromPorQ.equalsIgnoreCase("Q")) {
                if (inDeliveryReq.getFromPorQ().equalsIgnoreCase("Q")) {
                    jo.put("requirement", inDeliveryReq.getClientReqText());
                    jo.put("reqCheck", inDeliveryReq.isClientReqCheck());
                    jo.put("reqBy", inDeliveryReq.getClientReqBy());
                    jo.put("id", inDeliveryReq.getId());
                    results.add(jo);
                }
            } else {
                jo.put("requirement", inDeliveryReq.getClientReqText());
                jo.put("reqCheck", inDeliveryReq.isClientReqCheck());
                jo.put("reqBy", inDeliveryReq.getClientReqBy());
                jo.put("id", inDeliveryReq.getId());
                results.add(jo);
            }
            
        }


        response.setContentType("text/json");
        response.setHeader("Cache-Control", "no-cache");
        // System.out.println(actResponse.toXML());
        PrintWriter out = response.getWriter();
        out.println(new JSONArray(results.toArray()));

        out.flush();

        // Forward control to the specified success URI
        return (null);
    }
}
