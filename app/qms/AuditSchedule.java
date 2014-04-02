/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Niteshwar
 */
public class AuditSchedule extends Action {

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
            HttpServletResponse response) throws Exception {
        List results = new ArrayList();
        String type = request.getParameter("type");

        List auditSchedule = QMSService.getInstance().getAuditSchedule();
        for (int i = 0; i < auditSchedule.size(); i++) {
            JSONObject jo = new JSONObject();
            QMSAudit v = (QMSAudit) auditSchedule.get(i);
            if (type == null) {
                jo.put("type", v.getType());
                jo.put("by", v.getAuditby());
                jo.put("standard", v.getStandard());
            jo.put("company", v.getCompany());
            jo.put("office", v.getOffice());
            jo.put("version", v.getVersion());
            jo.put("leadAuditor", v.getLeadauditor());
            jo.put("scheduled", v.getScheduled());
            jo.put("actual", v.getActual());
            jo.put("id", v.getAuditno());
            results.add(jo);
            } else if (type.equalsIgnoreCase("internal")&&v.getType().equalsIgnoreCase("internal")) {
                jo.put("by", v.getAuditby());
                jo.put("nonconformities", v.getNonconformities());
                jo.put("report", "<input type='button' value='Report' class='x-btn' onclick=\"javascript:uploadFile('" + v.getAuditno() + "')\">");
                jo.put("result", v.getResult());
                jo.put("standard", v.getStandard());
            jo.put("company", v.getCompany());
            jo.put("office", v.getOffice());
            jo.put("version", v.getVersion());
            jo.put("leadAuditor", v.getLeadauditor());
            jo.put("scheduled", v.getScheduled());
            jo.put("actual", v.getActual());
            jo.put("id", v.getAuditno());
            results.add(jo);
            } else if (type.equalsIgnoreCase("extclient")&&v.getType().equalsIgnoreCase("external")&&v.getAuditby().equalsIgnoreCase("client")) {
                jo.put("nonconformities", v.getNonconformities());
                jo.put("report", "<input type='button' value='Report' class='x-btn' onclick=\"javascript:uploadFile('" + v.getAuditno() + "')\">");
                jo.put("result", v.getResult());
                jo.put("standard", v.getStandard());
            jo.put("company", v.getCompany());
            jo.put("office", v.getOffice());
            jo.put("version", v.getVersion());
            jo.put("leadAuditor", v.getLeadauditor());
            jo.put("scheduled", v.getScheduled());
            jo.put("actual", v.getActual());
            jo.put("id", v.getAuditno());
            results.add(jo);
            } else if (type.equalsIgnoreCase("extregistrar")&&v.getType().equalsIgnoreCase("external")&&v.getAuditby().equalsIgnoreCase("registrar")) {
                jo.put("nonconformities", v.getNonconformities());
                jo.put("report", "<input type='button' value='Report' class='x-btn' onclick=\"javascript:uploadFile('" + v.getAuditno() + "')\">");
                jo.put("result", v.getResult());
                jo.put("standard", v.getStandard());
            jo.put("company", v.getCompany());
            jo.put("office", v.getOffice());
            jo.put("version", v.getVersion());
            jo.put("leadAuditor", v.getLeadauditor());
            jo.put("scheduled", v.getScheduled());
            jo.put("actual", v.getActual());
            jo.put("id", v.getAuditno());
            results.add(jo);
            }
            
        }
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        out.println(new JSONArray(results.toArray()));
        out.flush();
        return (null);


    }
}
