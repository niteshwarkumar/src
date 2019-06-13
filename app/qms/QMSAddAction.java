/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import app.admin.AdminService;
import app.admin.Excelnetissue;
import app.extjs.helpers.HrHelper;
import app.security.SecurityService;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author abc
 */
public class QMSAddAction  extends Action {

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

         // Extract attributes we will need
        MessageResources messages = getResources(request);

        // save errors
        ActionMessages errors = new ActionMessages();

        //START check for login (security)
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        List qmsActionList = QMSService.getInstance().getQMSActionList();
        List qmsActionListId = new ArrayList();
        
        String mrmJSON = request.getParameter("MRMJSON");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (mrmJSON != null && !"".equals(mrmJSON)) {
            JSONArray qmsAction = new JSONArray(mrmJSON);
            for (int i = 0; i < qmsAction.length(); i++) {
                JSONObject j = (JSONObject) qmsAction.get(i);
                 QMSAction ei = new QMSAction();
                if(!j.getString("id").equalsIgnoreCase("new")){
                   ei = QMSService.getInstance().getSingleQMSAction(Integer.parseInt(j.getString("id"))); 
                   qmsActionListId.add(j.getString("id"));
                }
               
                    ei.setActionItem(j.getString("actionItem"));
                    ei.setArea(j.getString("area"));
                    ei.setDepartment(j.getString("department"));
                    ei.setDisposition(j.getString("disposition"));
                    ei.setMrm(j.getString("mrm"));
                    ei.setResponsible(j.getString("responsible"));
                    try {
                        java.sql.Date d2 = new java.sql.Date(df.parse(j.getString("actual")).getTime());
                        ei.setActual(d2);
                    } catch (Exception ex) {
                    }
                    try {
                        java.sql.Date d2 = new java.sql.Date(df.parse(j.getString("target")).getTime());
                        ei.setTarget(d2);
                    } catch (Exception ex) {
                    }
                QMSServiceAddUpdate.getInstance().saveOrUpdateQMSAction(ei);
            }
        }
        for(int i = 0; i< qmsActionList.size(); i++){
            QMSAction qma = (QMSAction) qmsActionList.get(i);
            if(!qmsActionListId.contains(qma.getId().toString()))
                QMSServiceAddUpdate.getInstance().deleteQMSAction(qma);
        
        }


        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
        //return null;
    }
}
