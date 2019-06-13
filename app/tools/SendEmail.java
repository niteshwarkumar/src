/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.tools;
import app.client.Client;
import app.client.ClientService;
import app.extjs.global.LanguageAbs;
import app.extjs.vo.Product;
import app.project.DtpTask;
import app.project.EngTask;
import app.project.LinTask;
import app.project.OthTask;
import app.project.SourceDoc;
import app.project.TargetDoc;
import app.quote.Client_Quote;
import app.quote.Quote1;
import app.quote.QuoteService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import javax.mail.*; //for mail
import javax.mail.internet.*; //for mail
import java.util.*;
import app.security.*;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Neil
 */
public class SendEmail extends Action{


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


  if(!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }

 String productName = "";
                    String description = "";
                    String sourcee = "";
                    String targett = "";
                    String sos = "";
                    String sApplication = "";
                    String sVersion = "";
                    String tos = "";
                    String tApplication = "";
                    String tVersion = "";
                    String task1 = "";
                    String ComponentList = "";
                    String qNo = "";
                    String req = "";
                    String ins = "";
                    String msg = "";
                    Integer clientId=0;
                    Double linTotal=0.00;
                    Double engTotal=0.00;
                    Double dtpTotal=0.00;
                    Double othTotal=0.00;
        //int idTask1=0;
                    String quoteId = null;
                    quoteId = request.getParameter("quoteViewId");

                    //check attribute in request
                    if (quoteId == null) {
                        quoteId = (String) request.getAttribute("quoteViewId");
                    }

                    //id of quote from cookie
                    if (quoteId == null) {
                        quoteId = StandardCode.getInstance().getCookie("quoteViewId", request.getCookies());
                    }
                    if (quoteId == null) {
                        quoteId = request.getSession(false).getAttribute("ClientQuoteId").toString();
                    }

                    User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
                    // String  = request.getSession(false).getAttribute("clientQuoteId").toString();
                    String ur11 = quoteId;

                  

                    //System.out.println("iiiiiiiiiiiiiiiddddddddddddddddddddddiiiiiiiiii" + ur11);
                    String quoteViewId1 = quoteId;
                    Quote1 newQ1 = QuoteService.getInstance().getSingleQuote(new Integer(quoteViewId1));
msg+="<table border='1'><tr><td style='background-color:orange'><font color='blue' size='4'><b>SUMMARY</b></font></td><td width='640'><font color='blue' size='4'><b>"+newQ1.getNumber()+"</b></font></td></tr></table>";
  List qList = QuoteService.getInstance().getClient_Quote(Integer.parseInt(quoteId));
            for (int i = 0; i < qList.size(); i++) {


                Client_Quote ur1 = (Client_Quote) qList.get(i);


                try {

                    sos = StandardCode.getInstance().noNull(ur1.getOs());
                    sApplication = StandardCode.getInstance().noNull(ur1.getApplication());
                    sVersion = StandardCode.getInstance().noNull(ur1.getVersion());
                    tos = StandardCode.getInstance().noNull(ur1.getTarget_os());
                    tApplication = StandardCode.getInstance().noNull(ur1.getTarget_application());
                    tVersion = StandardCode.getInstance().noNull(ur1.getTarget_version());
                    qNo = StandardCode.getInstance().noNull(newQ1.getNumber());
                } catch (Exception e) {
                }

                String sources11 = "";
                String targets11 = "";
                //System.out.println("Hello");
                //System.out.println("newQ,ur.getProduct_ID()" + newQ1.getQuote1Id() + "        " + ur1.getId());
                try {
                    List sourcelang1 = QuoteService.getInstance().getSourceLang(newQ1, ur1.getId());

                    String targets1 = "";
                    String sources1 = "";
                    task1 = "";
                    int idTask1 = 0;
                    HashMap alreadyAdded = new HashMap();


                    // for(Iterator iterSources = newQ1.getSourceDocs().iterator(); iterSources.hasNext();) {
                    for (int ii = 0; ii < sourcelang1.size(); ii++) {
                        SourceDoc sd1 = (SourceDoc) sourcelang1.get(ii);
                        sources11 += (String) LanguageAbs.getInstance().getAbs().get(sd1.getLanguage());
                        //if(iterSources.hasNext()){
                        if (ii != sourcelang1.size() - 1) {
                            sources11 += ", ";
                        }
                        //System.out.println("sources" + sources11);
                        List targetlang = QuoteService.getInstance().getTargetLang(sd1.getSourceDocId());
                        for (int jj = 0; jj < targetlang.size(); jj++) {
                            TargetDoc td1 = (TargetDoc) targetlang.get(jj);

                            String srcLang1 = td1.getLanguage();


                            String abr1 = (String) LanguageAbs.getInstance().getAbs().get(srcLang1);
                            //Also prevent duplicates
                            if (abr1 != null && !"".equals(srcLang1) && !"".equals(abr1) && alreadyAdded.get(abr1) == null) {
                                targets11 += abr1 + ", ";
                                alreadyAdded.put(abr1, abr1);
                            } else if (abr1 == null && !"".equals(srcLang1) && !"".equals(abr1) && alreadyAdded.get(srcLang1) == null) {
                                targets11 += srcLang1 + ", ";
                                alreadyAdded.put(srcLang1, srcLang1);
                            }

                            idTask1 = td1.getTargetDocId();
                        }////System.out.println("targets"+targets);
                        if (targets11.endsWith(", ")) {
                            targets11 = targets11.substring(0, targets11.length() - 2);
                        }

                    }
                    List linTaskList = QuoteService.getInstance().getLinTask(idTask1);
                    List engTaskList = QuoteService.getInstance().getEnggTask(idTask1);
                    List forTaskList = QuoteService.getInstance().getFormatTask(idTask1);
                    List otherTaskList = QuoteService.getInstance().getOtherTask(idTask1);

                    // for(Iterator iterLinTasks=td.getLinTasks().iterator();iterLinTasks.hasNext();){
                    // LinTask lt=(LinTask).iterLinTasks.next();
                    // }
                    for (int ll = 0; ll < linTaskList.size(); ll++) {
                        LinTask lt = (LinTask) linTaskList.get(ll);
                        if (alreadyAdded.get(lt.getTaskName()) == null) {

                            if (task1 != "") {
                                task1 += ", ";
                            }
                            task1 += lt.getTaskName();
                            linTotal+=Double.parseDouble(lt.getDollarTotal().replaceAll(",",""));

                        }


                    }
                    for (int ll = 0; ll < engTaskList.size(); ll++) {
                        EngTask lt = (EngTask) engTaskList.get(ll);
                        if (alreadyAdded.get(lt.getTaskName()) == null) {
                            if (task1 != "") {
                                task1 += ", ";
                            }
                            task1 += lt.getTaskName();
                            engTotal+=Double.parseDouble(lt.getDollarTotal().replaceAll(",",""));

                        }


                    }
                    for (int ll = 0; ll < forTaskList.size(); ll++) {
                        DtpTask lt = (DtpTask) forTaskList.get(ll);
                        if (alreadyAdded.get(lt.getTaskName()) == null) {
                            if (task1 != "") {
                                task1 += ", ";
                            }
                            task1 += lt.getTaskName();
                            dtpTotal+=Double.parseDouble(lt.getDollarTotal().replaceAll(",",""));

                        }


                    }
                    for (int ll = 0; ll < otherTaskList.size(); ll++) {

                        OthTask lt = (OthTask) otherTaskList.get(ll);
                        if (alreadyAdded.get(lt.getTaskName()) == null) {
                            if (task1 != "") {
                                task1 += ", ";
                            }
                            task1 += lt.getTaskName();
                            othTotal+=Double.parseDouble(lt.getDollarTotal().replaceAll(",",""));
                        }

                    }

                } catch (Exception e) {
                }



                Product prod1 = app.client.ClientService.getInstance().getSingleProduct(ur1.getProduct_ID());

                productName = prod1.getProduct();
                ComponentList = ur1.getComponent();
                description = prod1.getDescription();
                sourcee = sources11;
                targett = targets11;
                req = ur1.getRequirement();
                ins = ur1.getInstruction();


msg+=" <table ><tr><td>&nbsp;</td></tr><tr><td><table cellspacing='0' cellpadding='0' frame='below'>";
msg+=" <tr><td><font color='blue'>Product</font></td><td><font color='maroon' size='3'><b>"+productName+"</b></font> </td></tr><tr><td ><font color='blue'>Component</font></td><td>"+ComponentList+"</td></tr>";
msg+="<tr><td>&nbsp;</td></tr><tr><td></td><td  style='border-right:solid 1px black;border-bottom:solid 1px black;'><b>&nbsp;&nbsp;&nbsp;&nbsp;Source</b></td><td style='border-bottom:solid 1px black;'><b>&nbsp;&nbsp;&nbsp;&nbsp;Target</b></td></tr>";
msg+="<tr><td ><font color='blue'>OS</font></td><td style='border-right:solid 1px black;'>"+sos+"</td><td>&nbsp;&nbsp;"+tos+"</td></tr>";
msg+="<tr><td width='140'><font color='blue'>Source Application</font></td><td  width='300' style='border-right:solid 1px black;'>"+sApplication+"</td><td width='300'>&nbsp;&nbsp;"+tApplication+"</td></tr>";
msg+="<tr><td ><font color='blue'>Source Version</font></td><td style='border-right:solid 1px black;'>"+sVersion+"</td><td>&nbsp;&nbsp;"+tVersion+"</td></tr><tr><td >&nbsp;</td></tr>";
msg+="<tr><td ><font color='blue'>Source Language</font></td><td style='border-right:solid 1px black;'>"+sourcee+"</td><td>"+targett+"</td></tr><tr><td>&nbsp;</td></tr>";
msg+="<tr><td><font  color='blue'>Tasks</font></td><td>"+task1+"</td></tr><tr><td>&nbsp;</td></tr><tr><td><font color='blue'>Instructions</font></td><td>"+ins+"</td></tr>";
msg+="<tr><td>&nbsp;</td></tr><tr><td><font color='blue'>Requirements</font></td><td>"+req+"</td></tr>";
msg+="<tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr></table>" ;
        


clientId=ur1.getID_Client();


            }

  msg+="<tr><td><table><tr><td>&nbsp;</td></tr>" +
        "<tr><td><b><u>Cost Analysis</u></b></td></tr>" +
        "<tr><td><font color='blue'>Linguistic</font></td><td>"+linTotal+"</td></tr>" +
      "<tr><td><font color='blue'>DTP/Format</font></td><td>"+dtpTotal+"</td></tr>" +
      "<tr><td><font color='blue'>Engineering</font></td><td>"+engTotal+"</td></tr>" +
      "<tr><td><font color='blue'>Other</font></td><td>"+othTotal+"</td></tr>" +

        "</table></td></tr></table>";


String emailMsgTxt=msg;
String emailSubjectTxt  = "Request for Quote Analysis :"+newQ1.getNumber();
//String whoToNotify=request.getSession(false).getAttribute("whoToNotify").toString();
HttpSession session = request.getSession(false);
String whoToNotify=(String) session.getAttribute("whoToNotify");
        //System.out.println("whoToNotify"+whoToNotify);


          String emailId=request.getParameter("id");
   //                 //System.out.println("mailId"+emailId);
 //emailId="excelnet@xltrans.com";
Client c=ClientService.getInstance().getSingleClient(clientId);
///User user=UserService.getInstance().getSingleUser(c.getProject_mngr());
try{
//emailId+=","+user.getWorkEmail1();

}catch(Exception e){}

//Client c=ClientService.getInstance().getSingleClient(clientId);


//String[] emailList = {"niteshwar.kumar@spatialideas.com"};
String[] emailList = {emailId};


    SendEmail smtpMailSender = new SendEmail();

    smtpMailSender.postMail( emailList, emailSubjectTxt, emailMsgTxt, StandardCode.emailFromAddress);
    //System.out.println("Sucessfully Sent mail to All Users");



 return (mapping.findForward("Success"));
    }



    private void execute(SendMail sm){
		ExecutorService execSvc = Executors.newSingleThreadExecutor();
		execSvc.execute(sm);
		execSvc.shutdown();
		
	}


  // Add List of Email address to who email needs to be sent to
 // private static final String[] emailList = {"niteshwar.kumar@spatialideas.com"};


     public void postMail( String recipients[ ], String subject,String message , String from) throws MessagingException
    {
      SendMail sm = new SendMail(recipients, subject, message, from);
      execute(sm);
    }
    
     public void postMail( String recipients[ ],String ccRecipients[ ], String subject,String message , String from) throws MessagingException
    {
        SendMail sm = new SendMail(recipients,ccRecipients, subject, message, from);
      execute(sm);
    }

}