/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.resource;

import java.io.PrintWriter;
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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Nishika
 */
public class ViewResourceClientAction   extends Action{


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
    List resourceClient=ResourceService.getInstance().getResourceClientList(resourceId);
    for(int i=0;i<resourceClient.size();i++){
        ResourceClient t=(ResourceClient) resourceClient.get(i);
    JSONObject jo=new JSONObject();
    jo.put("id", t.getId());
    jo.put("client",t.getClient());
    jo.put("language",t.getLanguage());
    jo.put("level",t.getLevel());
    jo.put("primary",t.getPrimry());
    jo.put("primaryCount",t.getPrimaryCount());
    jo.put("secondry",t.getSecondry());
    jo.put("secondryCount",t.getSecondryCount());
   
    results.add(jo);
    }



    response.setContentType("text/json");
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
