/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.project;

import app.admin.AdminService;
import app.admin.Ticker;
import app.extjs.helpers.TeamHelper;
import app.resource.Resource;
import app.resource.ResourceService;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import app.tools.SendEmail;
import app.user.User;
import app.user.UserService;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.DynaValidatorForm;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author niteshwar
 */
public class ScoreUpdateEngAction extends Action{


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
        if(!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)
	User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
	DynaValidatorForm qvpr = (DynaValidatorForm) form;
        String taskId = (String) qvpr.get("taskId");
        EngTask lt=ProjectService.getInstance().getSingleEngTask(Integer.parseInt(taskId));
        String resourceName="";

//        String fname="C://templates/vendorQualityAlert.txt";
//       // fname="/Users/abhisheksingh/Project/templates/orderConfirmation.txt";
//
//
//           java.io.File file= new java.io.File(fname);
//           int len;
//             char[] chr = new char[4096];
//             final StringBuffer buffer = new StringBuffer();
//             final FileReader reader = new FileReader(file);
//             try {
//                 while ((len = reader.read(chr)) > 0) {
//                     buffer.append(chr, 0, len);
//                 }
//             } finally {
//                 reader.close();
//             }
//       ////System.out.println(buffer.toString());
//       String emailMsgTxt=buffer.toString();
       
        
        lt.setScoreDescription((String) qvpr.get("description"));
        lt.setScore(Integer.parseInt((String) qvpr.get("score")));
      //  lt.setScore(Integer.parseInt((String) qvpr.get("score")));

        ProjectService.getInstance().updateEngTask(lt);

           Resource res=ResourceService.getInstance().getSingleResource(Integer.parseInt(lt.getPersonName()));

        if(!(StandardCode.getInstance().noNull(res.getFirstName())).equalsIgnoreCase("")){
            resourceName=res.getFirstName()+" "+res.getLastName();
        }else{
            resourceName=res.getCompanyName();
        }

         Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");

    String projectNo=request.getParameter("pno");
    if (projectNo == null){
     projectNo = lt.getTargetDoc().getSourceDoc().getProject().getNumber()+
                lt.getTargetDoc().getSourceDoc().getProject().getCompany().getCompany_code();
    }
    
    String projectManager=request.getParameter("pm");
    
    String osaHistoryString = "";
        JSONArray osaHistory = TeamHelper.getAllOSAHistoryJSON(Integer.parseInt(lt.getPersonName()));
        for(int i = 0; i < osaHistory.length(); i++){
            JSONObject jo = osaHistory.getJSONObject(i);
            if(!jo.optString("id").equalsIgnoreCase(taskId)){
                osaHistoryString += "<tr>";
                osaHistoryString += "<td class='t1' valign='top'>"+jo.getString("projectNumb") +"</td>";
                osaHistoryString += "<td class='t1' valign='top'>"+jo.getString("delDate") +"</td>";
                osaHistoryString += "<td class='t1' valign='top'>"+jo.getString("taskName") +"</td>";
                osaHistoryString += "<td class='t1' valign='top'>"+jo.getString("src") +" - "+ jo.getString("tgt")+"</td>";
                osaHistoryString += "<td class='t1' valign='top'>"+jo.getString("osaScore") +"</td>";
                osaHistoryString += "<td class='t1' valign='top'>"+jo.getString("scoreDesc") +"</td>";
                osaHistoryString += "</tr>";
            }
        }

 String emailMsgTxt ="<style type='text/css'>"//border-size:1px;border-color: #000000 border-style:solid;
                + "div {margin: 0.0px 0.0px 0.0px 0.0px; font: 12.0px 'Helvetica Neue'; color: #454545; min-height: 14.0px}"
                + "table.tab1{border-collapse: collapse;font: 12.0px 'Helvetica Neue';;border-style: solid;border-width: thin;}"
                + "td.t1 {border-style: solid; border-size: 1.0px; border-color: #aaaaaa ; padding: 1.0px 5.0px 1.0px 5.0px;font: 12.0px 'Helvetica Neue';;border-style: solid;border-width: thin; }"
                + "hr {border: 1px dashed;}"
                + "</style>"
                + ""
                + "<div>"
                + "Hello,"
                + "<br/><br/>"
                + "This message is automatically generated by ExcelNet<b>.&nbsp;" + u.getFirstName() +" " + u.getLastName() +"</b>&nbsp;has made the following vendor assessment (OSA):"
                + "<br><br/>"
                + "<hr>"
                + "<br><br/>"
                + "<table cellspacing='0' cellpadding='0' class='tab1'><tbody>"
                + "<tr><td class='t1'  valign='top'>Date:</td><td class='t1' valign='top'>"+ sdf.format(cal.getTime()) + "</td></tr>"
                + "<tr><td class='t1' valign='top'>Project:</td><td class='t1' valign='top'>"+projectNo+"</td></tr>"
                + "<tr><td class='t1' valign='top'><br></td><td class='t1' valign='top'><br></td></tr>"
                + "<tr><td class='t1' valign='top'>Vendor:</td><td class='t1' valign='top'><b>"+resourceName+"</b></td></tr>"
                + "<tr><td class='t1' valign='top'>Language:</td><td class='t1' valign='top'><b>"+lt.getSourceLanguage()+" - "+lt.getTargetLanguage()+"</b></td></tr>"
                + "<tr><td class='t1' valign='top'>Score:</td><td class='t1' valign='top'><b>"+(String) qvpr.get("score")+"</b></td></tr>"
                + "<tr><td class='t1' valign='top'>Comment:</td><td class='t1' valign='top'>"+(String) qvpr.get("description")+"</td></tr>"
                + "</tbody></table>"
                + "<br/><br/><br/>"

                + "Previous ten \"non-35\" scores for this vendor:"
                + "<br/><br/>"
                + "<table cellspacing='0' cellpadding='0' class='t1'>"
                + "<tbody>"
                + "<tr>"
                + "<td class='t1' valign='top'><b>Project</b></td>"
                + "<td class='t1' valign='top'><b>Delivery Date</b></td>"
                + "<td class='t1' valign='top'><b>Task</b></td>"
                + "<td class='t1' valign='top'><b>Lang</b></td>"
                + "<td class='t1' valign='top'><b>Score</b></td>"
                + "<td class='t1' valign='top'><b>Comment</b></td>"
                + "</tr>"
                + osaHistoryString
                + ""
                + "</tbody>"
                + "</table>"
                + "<br/><br/>"
                + "<br>"
                + "<hr>"
                + "<br>"
                + "Best regards,<br/>"
                + "ExcelNet Administrator"
                + "<br>"
                + "<div><img src=https://excelnet.xltrans.com/logo/images/excel-logo-blue.jpg></div><br>"
                + "</div>";
 
        String toAddress="";
         List tickerList= AdminService.getInstance().getTickerList(20);
            for(int i=0;i<tickerList.size();i++){
               Ticker ticker = (Ticker) tickerList.get(i);
               if(toAddress.equalsIgnoreCase("")){try{toAddress =ticker.getUserEmail();}catch(Exception e){}}else{
               try{toAddress +=","+ ticker.getUserEmail();}catch(Exception e){}}
            }
//            toAddress="nkumar@xltrans.com";
        String[] emailList = toAddress.split(",");
        String emailSubjectTxt="Vendor Quality Alert: "+resourceName+" ("+(String) qvpr.get("score")+")";

         SendEmail smtpMailSender = new SendEmail();
         smtpMailSender.postMail( emailList, emailSubjectTxt, emailMsgTxt, "excelnet@xltrans.com");
         request.setAttribute("resourceViewId", ""+res.getResourceId());
         request.setCharacterEncoding("UTF-8");
         response.setCharacterEncoding("UTF-8");

        // JSObject win = (JSObject) JSObject.getWindow(this);
 //  win.eval("self.close();");
        return (mapping.findForward("Success"));//}


    }

}
