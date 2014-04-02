/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.dtpScheduler;


import app.extjs.helpers.HrHelper;
import app.standardCode.*;
import app.standardCode.StandardCode.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import app.user.User;
import app.user.UserService;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Neil
 */
public class ENGProject extends Action {


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



User u=UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));

    List results = new ArrayList();
String pORq="";
pORq=request.getParameter("pORq");
        System.out.println(pORq);
List engProjectList=DTPSchedulerService.getInstance().getAllENGProjectsOrQuotes(pORq);
        System.out.println("Hello");
for(int i=0;i<engProjectList.size();i++){

ENGScheduler eng=(ENGScheduler) engProjectList.get(i);
            JSONObject jo = new JSONObject();
            jo.put("requester",eng.getRequester());
            jo.put("pno",StandardCode.getInstance().noNull(eng.getID_Project()));
            System.out.println(StandardCode.getInstance().noNull(eng.getID_Quote()));
            jo.put("qno", StandardCode.getInstance().noNull(eng.getID_Quote()));
            jo.put("task",eng.getTask());
            jo.put("timeNeed",eng.getTimeNeeded());
            jo.put("finishDate",eng.getFinishDate());
            jo.put("hour", eng.getHour());
            jo.put("timeNeededUnit", eng.getTimeNeededUnit());
            jo.put("id", eng.getID_Schedule());
           
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

