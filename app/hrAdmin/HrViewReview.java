/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.hrAdmin;


import app.extjs.helpers.HrHelper;
import app.qms.QMSLibrary;
import app.qms.QMSService;
import app.standardCode.StandardCode; 
import app.user.*;
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
public class HrViewReview  extends Action {

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

        String userViewId = "";
        userViewId = request.getParameter("userViewId");
        if(userViewId==null)
         userViewId = StandardCode.getInstance().getCookie("userViewId", request.getCookies());
        else if(userViewId.isEmpty())
         userViewId = StandardCode.getInstance().getCookie("userViewId", request.getCookies());
        User  u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
        User currentUser= UserService.getInstance().getSingleUser(Integer.parseInt(userViewId));

        //PRIVS check that admin user is viewing this page
        if (!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "hrAdmin")&&u.getUserId()!=Integer.parseInt(userViewId)) {
            return (mapping.findForward("accessDenied"));
        }//END PRIVS check that admin user is viewing this page


        List results = new ArrayList();

        String mainTab = request.getParameter("mainTab");
//        String userViewId1 = request.getParameter("userViewId");
        List docList = new ArrayList();
         String query=" where owner='"+currentUser.getFirstName()+" "+currentUser.getLastName()+"' and mainTab='Review'";
        try{
         docList = QMSService.getInstance().getQMSLibraryDocumentByTabs("Review",query);
        }catch(Exception e){}
//        List docList = new ArrayList<Object>();
        if(docList.isEmpty()){
             query=" where ownerid like'"+userViewId+"%' and mainTab='Review'";
            docList = QMSService.getInstance().getQMSLibraryDocumentByTabs("Review",query);
        }
        
        for (int i = 0; i < docList.size(); i++) {
            QMSLibrary lu = (QMSLibrary) docList.get(i);
//            if(lu.getOwner().equalsIgnoreCase(currentUser.getFirstName()+" "+currentUser.getLastName())){
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
            jo.put("uploadBy", lu.getUploadBy());
            jo.put("mainTab", lu.getMainTab());
            jo.put("isoreference", lu.getIsoreference());

            if(StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(true).getAttribute("userPrivs"), "hrAdmin")) {
            jo.put("update", "<a " + HrHelper.LINK_STYLE + " href=../qmsLibUpdateDocumentPreAction.do?mainTab="+mainTab+"&id=" + lu.getId() + ">Click</a>");
            }
            results.add(jo);
//            }
        }

        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        // //System.out.println(actResponse.toXML());
        PrintWriter out = response.getWriter();

        out.println(new JSONArray(results.toArray()));
        //request.setAttribute("blogJSArray",new JSONArray(results.toArray()));

        out.flush();

        // Forward control to the specified success URI
        return (null);

    }
}
