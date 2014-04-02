/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import app.extjs.helpers.HrHelper;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
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
public class QMSLibraryStore extends Action {

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

        String mainTab = request.getParameter("mainTab");
        String query=request.getParameter("query");
        if(query==null)query="";

      
        List docList = QMSService.getInstance().getQMSLibraryDocumentByTabs(mainTab,query);
        for (int i = 0; i < docList.size(); i++) {
            QMSLibrary lu = (QMSLibrary) docList.get(i);
            JSONObject jo = new JSONObject();

            jo.put("id", lu.getId());
            jo.put("title", lu.getTitle());
            jo.put("description", lu.getDescription());
            jo.put("category", lu.getCategory());
            jo.put("docId", lu.getDocId());
            jo.put("docFormat", lu.getFormat());
            if (lu.getLink() == null) {
            } else if (lu.getLink().equalsIgnoreCase("")) {
            } else {
                jo.put("htmlLink", "<a href=\"" + lu.getLink() + "\" target=\"_blank\">Go to Web</a>");
            }
            jo.put("version", lu.getVersion());
            jo.put("docType", lu.getType());
             if (lu.getFileSaveName() == null) {
            } else if (lu.getFileSaveName().equalsIgnoreCase("")) {
            } else {
             jo.put("readFile", "<a href=\"http://excelnet.xltrans.com/logo/Library/QMS/" + lu.getFileSaveName() + "\" target=\"_blank\">" + lu.getFileName() + "</a>");
//                jo.put("readFlie",  "NKS");
            }

            jo.put("release", lu.getReleaseDate());
            jo.put("owner", lu.getOwner());
            jo.put("mainTab", lu.getMainTab());
            jo.put("isoreference", lu.getIsoreference());


            jo.put("history", "<a " + HrHelper.LINK_STYLE + " href=../qmsLibraryHistory.do?mainTab="+mainTab+"&id=" + lu.getId() + ">Click</a>");
            jo.put("admin", "<a " + HrHelper.LINK_STYLE + " href=../qmsLibraryAdmin.do?mainTab="+mainTab+"&id=" + lu.getId() + ">Click</a>");
            if(StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(true).getAttribute("userPrivs"), "hrAdmin")) {
            jo.put("update", "<a " + HrHelper.LINK_STYLE + " href=../qmsLibUpdateDocumentPreAction.do?mainTab="+mainTab+"&id=" + lu.getId() + ">Click</a>");
            }
            jo.put("training", "<a " + HrHelper.LINK_STYLE + " href=../qmsLibraryTraining.do?mainTab="+mainTab+"&id=" + lu.getId() + ">Click</a>");

          
            results.add(jo);
        }

        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        // System.out.println(actResponse.toXML());
        PrintWriter out = response.getWriter();

        out.println(new JSONArray(results.toArray()));
        //request.setAttribute("blogJSArray",new JSONArray(results.toArray()));

        out.flush();

        // Forward control to the specified success URI
        return (null);

    }
}
