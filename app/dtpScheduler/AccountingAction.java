/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.dtpScheduler;

import app.project.Project;
import app.project.ProjectService;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import org.apache.struts.util.MessageResources;
import app.standardCode.StandardCode.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.io.PrintWriter;
import java.util.*;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Niteshwar
 */
public class AccountingAction extends Action{

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
            HttpServletResponse response)
            throws Exception {

        // Extract attributes we will need
        MessageResources messages = getResources(request);

        // save errors
        ActionMessages errors = new ActionMessages();

        //START check for login (security)
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        List results = new ArrayList();
        String FromDate="";
        String ToDate="";
                    Date myDate=new Date();
        @SuppressWarnings("deprecation")
Integer month = myDate.getMonth() + 1;
        @SuppressWarnings("deprecation")
Integer day = myDate.getDate();
        @SuppressWarnings("deprecation")
Integer year = myDate.getYear()+1900;
Integer monthNew = month - 3;
        @SuppressWarnings("deprecation")
Integer dayNew = myDate.getDate();
        @SuppressWarnings("deprecation")
Integer yearNew = myDate.getYear()+1900;
if(month<=3){
    monthNew=month+12-3;
            yearNew -= 1;}
        FromDate=request.getParameter("FromDate");
        ToDate=request.getParameter("ToDate");
        if(FromDate==null){
          FromDate=yearNew+"-"+monthNew+"-"+dayNew;
        }else{
        FromDate=FromDate.replaceAll("/", "-");
        String flip[] = FromDate.split("-");
        FromDate = flip[2]+"-"+flip[0]+"-"+flip[1];

        }
        if(ToDate==null){
         ToDate=year+"-"+month+"-"+day;
        }else{
        ToDate=ToDate.replaceAll("/", "-");
         String flip[] = ToDate.split("-");
        ToDate = flip[2]+"-"+flip[0]+"-"+flip[1];

        }


        List projectList=ProjectService.getInstance().getProjectListByStartDate(FromDate,ToDate);
        for(int i=0;i<projectList.size();i++)
        {
            Project proj=(Project) projectList.get(i);
            JSONObject jo=new JSONObject();
            jo.put("IdProject",proj.getNumber() );
            jo.put("pm",StandardCode.getInstance().noNull(proj.getPm()) );
            jo.put("client",StandardCode.getInstance().noNull(proj.getCompany().getCompany_name()) );
            jo.put("amount", StandardCode.getInstance().formatDouble(proj.getProjectAmount()));
            jo.put("startDate",proj.getStartDate()) ;
            jo.put("deliveryDate",proj.getDeliveryDate()) ;

            jo.put("invoiced",StandardCode.getInstance().noNull(proj.getTotalAmountInvoiced()) );
            jo.put("isConfirmed",proj.isConfirmationRecieved());

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
      return null;
    }
}
