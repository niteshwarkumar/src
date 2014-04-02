/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.tools;

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
public class ConversionViewAction  extends Action{

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
        List vc = ToolsService.getInstance().getConversionList();
        for (int i = 0; i < vc.size(); i++) {
            JSONObject jo = new JSONObject();
            Conversion v = (Conversion) vc.get(i);
            jo.put("id", v.getId());
            jo.put("source", v.getSource());
            jo.put("target", v.getTarget());
            jo.put("conversion", v.getConversion());


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
