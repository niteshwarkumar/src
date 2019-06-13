/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.comm;

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
public class LibraryUploadStore extends Action {

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
        String subTab = request.getParameter("subTab");
        //System.out.println("subTab--->"+subTab);
         String clientViewId=request.getParameter("clientViewId"); 
         List docList=null;
         
         if(clientViewId==null)
        {
            docList = CommService.getInstance().getLibraryDocumentByTabs(mainTab, subTab);
        } else{
        
         docList = CommService.getInstance().getLibraryDocumentByTabs(mainTab, subTab, Integer.parseInt(clientViewId));
         }
        for (int i = 0; i < docList.size(); i++) {
            LibraryUpload lu = (LibraryUpload) docList.get(i);
            JSONObject jo = new JSONObject();
            jo.put("libId", lu.getLibId());
            jo.put("title", lu.getTitle());
            jo.put("description", lu.getDescription());
            jo.put("uploadDate", lu.getUploadDate());
            jo.put("uploadBy", lu.getUploadBy());
            if (lu.getHtmlLink() == null) {
            } else if (lu.getHtmlLink().equalsIgnoreCase("")) {
            } else {
                jo.put("htmlLink", "<a href=\"" + lu.getHtmlLink() + "\" target=\"_blank\">Go to Web</a>");
            }
            jo.put("docFormat", lu.getFormat());
            jo.put("btnDelete", "<a " + HrHelper.LINK_STYLE + " href=../libraryUpdateDoc.do?libId=" + lu.getLibId() + ">Update/Delete</a>");
            if (lu.getFileSaveName() == null) {
            } else if (lu.getFileSaveName().equalsIgnoreCase("")) {
            } else {
                jo.put("readFlie", "<a href=\"http://excelnet.xltrans.com/logo/Library/" + mainTab + "/" + lu.getFileSaveName() + "\" target=\"_blank\">" + lu.getFileName() + "</a>");
            }
            

//            "<a "+HrHelper.LINK_STYLE+" href=\"http://www.google.com('"+p.getProjectId()+"','"+StandardCode.getInstance().noNull(p.getNumber() + p.getCompany().getCompany_code())+"')\">" + StandardCode.getInstance().noNull(p.getNumber()+p.getCompany().getCompany_code()) + "</a>");

            results.add(jo);

        }

//       <a href="javascript:window.open('http://www.google.com');">General Terms and Conditions</a>
// onclick="window.open('http://www.lifesciencestranslations.blogspot.com')" href="javascript:void(0)">
//<h3>Click here to view blog</h3>
//</a>

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
