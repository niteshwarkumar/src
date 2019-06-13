/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.admin;

import app.extjs.helpers.HrHelper;
import app.standardCode.StandardCode;
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
 * @author abc
 */
public class ViewExcelnetIssue extends Action {

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

        String status = request.getParameter("status");
        List results = new ArrayList();
        List vc = null;
        if (status.equalsIgnoreCase("1")) {
            vc = AdminService.getInstance().getExcelnetIssueList();
        } else {
            vc = AdminService.getInstance().getExcelnetIssueList(status);
        }
        for (int i = 0; i < vc.size(); i++) {
            JSONObject jo = new JSONObject();
            Excelnetissue v = (Excelnetissue) vc.get(i);
            jo.put("id", v.getId());
            jo.put("status", v.getStatus());
            jo.put("itemNo", v.getId());
            jo.put("priority", v.getPriority());
            jo.put("risk",StandardCode.getInstance().noNull(v.getRisk()));
            jo.put("item", v.getItem());
            jo.put("reference", v.getReference());
            jo.put("tested", v.getTested());
            jo.put("testedBy", v.getTestedBy());
            jo.put("result", v.getResult());
            jo.put("uploaded", v.getUploaded());
            jo.put("approval1", v.getApproval1());
            jo.put("approval2", v.getApproval2());
            jo.put("approval3", v.getApproval3());
            jo.put("approval4", v.getApproval4());
            jo.put("testTested", v.getTestTested());
            jo.put("liveTested", v.getLiveTested());
            jo.put("liveIssue", v.getLiveIssue());
            jo.put("_final", v.getFfinal());
            jo.put("issue", v.getIssue());
            jo.put("notes", v.getNotes());
            jo.put("dateStart", v.getDateStart());
            jo.put("dateDue", v.getDateDue());
            jo.put("dateFinal", v.getDateFinal());
            jo.put("date1", v.getDate1());
            jo.put("date2", v.getDate2());
            jo.put("date3", v.getDate3());
            jo.put("date4", v.getDate4());
            if (status.equalsIgnoreCase("1") && v.getStatus() == 2) {                
            } else {
                jo.put("validate", "<a " + HrHelper.LINK_STYLE + " href=../adminExcelChangeStatus.do?status=" + status + "&id=" + v.getId() + ">Validate</a>");
            }
            if (status.equalsIgnoreCase("1") && v.getStatus() == 1) {
                jo.put("deleteIssue", "<a " + HrHelper.LINK_STYLE + " href=../adminExcelChangeStatus.do?status=0&id=" + v.getId() + ">Delete</a>");
            }
            jo.put("action", "<a " + HrHelper.LINK_STYLE + " href=AdminExcelImprovementsAction.jsp?status=" + status + "&vid=" + v.getId() + ">Action</a>");
            
            
            
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
