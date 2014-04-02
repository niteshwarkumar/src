/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.resource;

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
public class CoupleData  extends Action{


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
				 HttpServletResponse response)throws Exception

    {

    List results = new ArrayList();
    Integer resourceId=Integer.parseInt(request.getParameter("resourceViewId"));
    String yesno=request.getParameter("couple");
    List coupleList=ResourceService.getInstance().getCoupleList(resourceId,yesno);
    for(int i=0;i<coupleList.size();i++){
        Couples t=(Couples) coupleList.get(i);
    JSONObject jo=new JSONObject();
    jo.put("id", t.getId());
    jo.put("resourceid",t.getResourceid());
    jo.put("resourcename",t.getResourcename());
    jo.put("resourcetype",t.getResourcetype());
    jo.put("couplename",t.getCouplename());
    jo.put("coupletype",t.getCoupletype());
    results.add(jo);
    }



    response.setContentType("text/json");
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
