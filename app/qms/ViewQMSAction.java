/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import app.admin.AdminService;
import app.admin.Excelnetissue;
import app.extjs.helpers.HrHelper;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author abc
 */
public class ViewQMSAction   extends Action {

    // ----------------------------------------------------- Instance Variables
    /**
     * The
     * <code>Log</code> instance for this application.
     */
    private Log log =
            LogFactory.getLog("org.apache.struts.webapp.Example");

    // --------------------------------------------------------- Public Methods
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an
     * <code>ActionForward</code> instance describing where and how control
     * should be forwarded, or
     * <code>null</code> if the response has already been completed.
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
            HttpServletResponse response) throws Exception {

        List results = new ArrayList();
        List qmsActionList = QMSService.getInstance().getQMSActionList();
        for (int i = 0; i < qmsActionList.size(); i++) {
            JSONObject jo = new JSONObject();
            QMSAction qmsAction = (QMSAction) qmsActionList.get(i);
            jo.put("id", qmsAction.getId());
            jo.put("actionItem", qmsAction.getActionItem());
            jo.put("actual", qmsAction.getActual());
            jo.put("area", qmsAction.getArea());
            jo.put("department", qmsAction.getDepartment());
            jo.put("disposition", qmsAction.getDisposition());
            jo.put("mrm", qmsAction.getMrm());
            jo.put("responsible", qmsAction.getResponsible());
            jo.put("target", qmsAction.getTarget());
            results.add(jo);
        }
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        out.println(new JSONArray(results.toArray()));
        out.flush();
        return (null);
    }
}
