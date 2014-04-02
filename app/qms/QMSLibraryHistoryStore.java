/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.qms;

import app.extjs.helpers.HrHelper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 *
 * @author Niteshwar
 */
public class QMSLibraryHistoryStore extends Action {

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

        String id = request.getParameter("id");


        List docList = QMSService.getInstance().getQMSLibraryHistory(Integer.parseInt(id));
        for (int i = 0; i < docList.size(); i++) {
            QMSLibraryHistory lu = (QMSLibraryHistory) docList.get(i);
            JSONObject jo = new JSONObject();

            jo.put("id", lu.getId());
            jo.put("title", lu.getTitle());
            jo.put("docId", lu.getDocId());
            jo.put("version", lu.getVersion());
            jo.put("release", lu.getReleaseDate());
            jo.put("changes", lu.getChanges());

            results.add(jo);
        }
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        out.println(new JSONArray(results.toArray()));

        out.flush();

        // Forward control to the specified success URI
        return (null);

    }
}
