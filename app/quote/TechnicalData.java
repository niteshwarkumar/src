/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.quote;

import app.project.LinTask;
import app.project.ProjectService;
import app.project.Project_Technical;
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
public class TechnicalData extends Action{


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
    Integer id=Integer.parseInt(request.getParameter("ClientQuoteId"));
    double count = 0.00;
    if(id==0){
      
        
    Integer projectid=Integer.parseInt(request.getParameter("projectid"));
    List technicalList=ProjectService.getInstance().getProjectTechnicalList(projectid);
    for(int i=0;i<technicalList.size();i++){
        Project_Technical t=(Project_Technical) technicalList.get(i);
    JSONObject jo=new JSONObject();
    jo.put("id", t.getId());
    jo.put("sourceOs",t.getSourceos() );
    jo.put("targetOs",t.getTargetos() );
    jo.put("sourceApplication",t.getSourceapp() );
    jo.put("targetApplication",t.getTargetapp() );
    jo.put("sourceVersion",t.getSourcever() );
    jo.put("targetVersion",t.getTargetver() );
    jo.put("unitCount",t.getUnitCount() );
    count+=t.getUnitCount();
    results.add(jo);
    }
    }else{


    List technicalList=QuoteService.getInstance().getTechnicalList(id);
    for(int i=0;i<technicalList.size();i++){
        Technical t=(Technical) technicalList.get(i);
    JSONObject jo=new JSONObject();
    jo.put("id", t.getTechnicalId());
    jo.put("sourceOs",t.getSourceOs() );
    jo.put("targetOs",t.getTargetOs() );
    jo.put("sourceApplication",t.getSourceApplication() );
    jo.put("targetApplication",t.getTargetApplication() );
    jo.put("sourceVersion",t.getSourceVersion() );
    jo.put("targetVersion",t.getTargetVersion() );
    jo.put("unitCount",t.getUnitCount() );
    count+=t.getUnitCount();
    results.add(jo);
    }
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