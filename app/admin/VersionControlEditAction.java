/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Niteshwar
 */
public class VersionControlEditAction extends Action {

    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        String jsonVC = request.getParameter("blogJSON");
        VersionControl vc = null;

        if (jsonVC != null && !"".equals(jsonVC)) {
            JSONArray versionControl = new JSONArray(jsonVC);
            for (int i = 0; i < versionControl.length(); i++) {
                JSONObject j = (JSONObject) versionControl.get(i);
                if (j.getString("id").equalsIgnoreCase("new")) {
                    vc = new VersionControl();
                } else {
                    vc = AdminService.getInstance().getSingleVersionControl(Integer.parseInt(j.getString("id")));
                }
                vc.setAuthor("");
                vc.setBuilt(j.getString("built"));
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    java.sql.Date d2 = new java.sql.Date(df.parse(j.getString("releaseDate")).getTime());
                    vc.setReleaseDate(d2);
                    // //System.out.println("first Date is>>>>>>>>>>>>>>>>>"+  (df.parse(j.getString("firstDraft")).getTime()));
                } catch (Exception ex) {
                }

                vc.setReleaseNote(j.getString("releaseNote"));
                vc.setVersion(j.getString("version"));

                AdminService.getInstance().saveOrUpdateVC(vc);
            }

        }
        return (mapping.findForward("Success"));
    }
}
