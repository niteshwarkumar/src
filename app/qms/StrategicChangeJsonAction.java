/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

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
 * @author niteshwar
 */
public class StrategicChangeJsonAction extends Action {

    private Log log = LogFactory.getLog("org.apache.struts.webapp.Example");

    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        List results = new ArrayList();
        List<StrategicChange> sclist = QMSService.getInstance().getStrategicChanges();

        for (StrategicChange v : sclist) {
            JSONObject jo = new JSONObject();
            jo.put("id", v.getId());
            jo.put("number", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:openSingleChangeWindow('" + v.getNumber() + "')\">" + v.getNumber() + "</a>");
            jo.put("cdate", v.getCdate());
            jo.put("employee", v.getEmployee());
            jo.put("description", v.getDescription());
            jo.put("reportedby", v.getReportedby());
            jo.put("status", v.getStatus());
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
