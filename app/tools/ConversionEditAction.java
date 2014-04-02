/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.tools;

/**
 *
 * @author Niteshwar
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;




public class ConversionEditAction extends Action {

    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        String jsonConv = request.getParameter("blogJSON");
        Conversion conv = null;

        if (jsonConv != null && !"".equals(jsonConv)) {
            JSONArray versionControl = new JSONArray(jsonConv);
            for (int i = 0; i < versionControl.length(); i++) {
                JSONObject j = (JSONObject) versionControl.get(i);
                if (j.getString("id").equalsIgnoreCase("new")) {
                    conv = new Conversion();
                } else {
                    //conv = AdminService.getInstance().getSingleVersionControl(Integer.parseInt(j.getString("id")));
                    conv = ToolsService.getInstance().getSingleConversion(Integer.parseInt(j.getString("id")));
                }
                conv.setSource(j.getString("source"));
                conv.setTarget(j.getString("target"));
                conv.setConversion(Float.parseFloat(j.getString("conversion")));
                ToolsService.getInstance().addConversionRate(conv);
            }

        }
        return (mapping.findForward("Success"));
    }
}
