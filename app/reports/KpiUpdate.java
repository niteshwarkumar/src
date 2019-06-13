/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.reports;

import app.standardCode.DateService;
import app.standardCode.StandardCode;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

/**
 *
 * @author niteshwar
 */
public class KpiUpdate extends Action{

    // ----------------------------------------------------- Instance Variables
    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log
            = LogFactory.getLog("org.apache.struts.webapp.Example");

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
        // Forward control to the specified success URI
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String goal = request.getParameter("goal");
        String referance = request.getParameter("referance");
        String referance2 = request.getParameter("referance2");
        String responsibility = request.getParameter("responsibility");
        String source = request.getParameter("source");
        String setDate = request.getParameter("setDate");
        String setDate2 = request.getParameter("setDate2");

        String year = request.getParameter("year");
        String type = request.getParameter("type");
        String q1action = request.getParameter("q1action");
        String q2action = request.getParameter("q2action");
        String q3action = request.getParameter("q3action");
        String q4action = request.getParameter("q4action");
        String action = StandardCode.getInstance().noNull(q1action) + "##"
                + StandardCode.getInstance().noNull(q2action) + "##"
                + StandardCode.getInstance().noNull(q3action) + "##"
                + StandardCode.getInstance().noNull(q4action);

        Kpi kpi = ReportsService.getInstance().getSingleKpi(year, type);
        kpi.setAction(action);
        kpi.setGoal(goal);
        if (null != setDate && !setDate.equalsIgnoreCase("") && !setDate.equalsIgnoreCase("null") ) {
            try {
                kpi.setReferanceDate(DateService.getInstance().convertDate(setDate).getTime());
            } catch (Exception e) {
            }
        }
        if (null != setDate2 && !setDate.equalsIgnoreCase("") && !setDate.equalsIgnoreCase("null")) {
            try {
                kpi.setReferanceDate2(DateService.getInstance().convertDate(setDate2).getTime());
            } catch (Exception e) {
            }
        }
        kpi.setReferancevalue(referance);
        kpi.setReferancevalue2(referance2);
        kpi.setResponsibility(responsibility);
        kpi.setSource(source);
        kpi.setType(type);
        kpi.setYear(Integer.parseInt(year.split("-")[0]));

        ReportsService.getInstance().updateKpi(kpi);

        return (mapping.findForward(type));
    }

}
