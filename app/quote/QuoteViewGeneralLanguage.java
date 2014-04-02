/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.quote;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import java.util.*;
import app.user.*;
import app.security.*;
import app.project.*;
import app.standardCode.*;
import org.apache.struts.validator.*;


/**
 *
 * @author Neil
 */
public class QuoteViewGeneralLanguage extends Action{

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

        //START get id of current quote from either request, attribute, or cookie
        //id of quote from request
	String quoteId = null;
	quoteId = request.getParameter("quoteViewId");

        //check attribute in request
        if(quoteId == null) {
            quoteId = (String) request.getAttribute("quoteViewId");
        }

        //id of quote from cookie
        if(quoteId == null) {
            quoteId = StandardCode.getInstance().getCookie("quoteViewId", request.getCookies());
        }

        //default client to first if not in request or cookie
        if(quoteId == null || "null".equals(quoteId)) {
                List results = QuoteService.getInstance().getQuoteList();
                Quote1 first = (Quote1) results.get(0);
                quoteId = String.valueOf(first.getQuote1Id());
            }

        Integer id = Integer.valueOf(quoteId);

        //END get id of current quote from either request, attribute, or cookie

        //get quote to edit
        Quote1 q = QuoteService.getInstance().getSingleQuote(id);

        //get this quote's sources
        Set sources = q.getSourceDocs();
       // System.out.println("alexxxx:::quoteId="+quoteId);
        //for each source add each sources' Tasks
        List totalLinTasks = new ArrayList();
        List totalLinTasks1 = new ArrayList();
        List totalEngTasks = new ArrayList();
        List totalDtpTasks = new ArrayList();
        List totalOthTasks = new ArrayList();


      //  System.out.println("quoteViewId"+quoteId+"         "+request.getParameter("quoteViewId")+"        "+quoteViewId);
           Integer CQuote =Integer.parseInt(quoteId);
           // Integer CQuote=1410;
         //    List results = new ArrayList();
       //  Session session = ConnectionFactory.getInstance().getSession();
            System.out.println("getNewClientQuoteNumber" + CQuote);
           List rs=null;
           int size=1;
           List sourcelang=null;

try{
          //  PreparedStatement pstmt = session.connection().prepareStatement("select * from client_quote where Quote_ID = " + CQuote);
           // rs = pstmt.executeQuery();

            rs=QuoteService.getInstance().getClient_Quote(CQuote);
            size=rs.size();
            System.out.println("RS ka sizeeeeeeeeeeeeeeeeeee"+rs.size());


}catch(Exception e){size=0;}

            if(size==0){
                        sourcelang=QuoteService.getInstance().getSourceLang1(q);
                        for (int ii = 0; ii < sourcelang.size(); ii++) {

                          SourceDoc sd = (SourceDoc) sourcelang.get(ii);

                          List targetlang=QuoteService.getInstance().getTargetLang(sd.getSourceDocId());
                        for(int jj=0;jj<targetlang.size();jj++) {
                            TargetDoc td = (TargetDoc) targetlang.get(jj);

                      List linTaskList=QuoteService.getInstance().getLinTask(td.getTargetDocId());
                      List engTaskList=QuoteService.getInstance().getEnggTask(td.getTargetDocId());
                      List forTaskList=QuoteService.getInstance().getFormatTask(td.getTargetDocId());
                      List otherTaskList=QuoteService.getInstance().getOtherTask(td.getTargetDocId());


                            for(int ll=0;ll<linTaskList.size();ll++){
                                         LinTask lt=(LinTask) linTaskList.get(ll);
                                    
                                       //linTasksArray.
                                    System.out.println("linTaskList" +lt.getTaskName());

                                    }


                        for(int ll=0;ll<linTaskList.size();ll++){
                                         LinTask lt=(LinTask) linTaskList.get(ll);
                                      if(lt.getTaskName().equalsIgnoreCase("Translation"))
                                         totalLinTasks.add(lt);
                                       //linTasksArray.
                                    System.out.println("linTaskList" +lt.getTaskName());

                                    }
                      for(int ll=0;ll<linTaskList.size();ll++){
                                         LinTask lt=(LinTask) linTaskList.get(ll);
                                      if(lt.getTaskName().equalsIgnoreCase("Editing"))
                                         totalLinTasks.add(lt);
                                       //linTasksArray.
                                   System.out.println("linTaskList" +lt.getTaskName());

                                    }
                      for(int ll=0;ll<linTaskList.size();ll++){
                                         LinTask lt=(LinTask) linTaskList.get(ll);
                                      if(lt.getTaskName().equalsIgnoreCase("ICR "))
                                         totalLinTasks.add(lt);
                                       //linTasksArray.
                                    System.out.println("linTaskList" +lt.getTaskName());

                                    }
                      for(int ll=0;ll<linTaskList.size();ll++){
                                         LinTask lt=(LinTask) linTaskList.get(ll);
                                      if(lt.getTaskName().equalsIgnoreCase("Proofreading "))
                                         totalLinTasks.add(lt);
                                       //linTasksArray.
                                     System.out.println("linTaskList" +lt.getTaskName());

                                    }
                      for(int ll=0;ll<linTaskList.size();ll++){
                                         LinTask lt=(LinTask) linTaskList.get(ll);
                                      if(!lt.getTaskName().equalsIgnoreCase("Proofreading ")&&!lt.getTaskName().equalsIgnoreCase("ICR ")&&!lt.getTaskName().equalsIgnoreCase("Editing")&&!lt.getTaskName().equalsIgnoreCase("Translation"))
                                         totalLinTasks.add(lt);
                                       //linTasksArray.
                                     System.out.println("linTaskList" +lt.getTaskName());

                                    }




                      for(int ll=0;ll<engTaskList.size();ll++){
                                         EngTask et=(EngTask) engTaskList.get(ll);
                                          totalEngTasks.add(et);
                               }

                      for(int ll=0;ll<forTaskList.size();ll++){
                                         DtpTask dt=(DtpTask) forTaskList.get(ll);

                                         totalDtpTasks.add(dt);

                       }
                      for(int ll=0;ll<otherTaskList.size();ll++){
                                         OthTask ot=(OthTask) otherTaskList.get(ll);
                                        totalOthTasks.add(ot);
                                     }

            }
        }




             size=1;}else{

              for(int i=0;i<size;i++){
                try{
                  Client_Quote cq=(Client_Quote) rs.get(i);
                   // Client_Quote cq=QuoteService.getInstance().getSingleClient_Quote(rs.getInt("id"));
                    sourcelang=QuoteService.getInstance().getSourceLang(q,cq.getId());

                }catch(Exception e){ sourcelang=QuoteService.getInstance().getSourceLang1(q);}

                    for (int ii = 0; ii < sourcelang.size(); ii++) {

                          SourceDoc sd = (SourceDoc) sourcelang.get(ii);

                          List targetlang=QuoteService.getInstance().getTargetLang(sd.getSourceDocId());
                        for(int jj=0;jj<targetlang.size();jj++) {
                            TargetDoc td = (TargetDoc) targetlang.get(jj);

                      List linTaskList=QuoteService.getInstance().getLinTask(td.getTargetDocId());
                      List engTaskList=QuoteService.getInstance().getEnggTask(td.getTargetDocId());
                      List forTaskList=QuoteService.getInstance().getFormatTask(td.getTargetDocId());
                      List otherTaskList=QuoteService.getInstance().getOtherTask(td.getTargetDocId());



                                for(int ll=0;ll<linTaskList.size();ll++){
                                         LinTask lt=(LinTask) linTaskList.get(ll);

                                       //linTasksArray.
                                    System.out.println("linTaskList" +lt.getTaskName());
                                }

                        for(int ll=0;ll<linTaskList.size();ll++){
                                         LinTask lt=(LinTask) linTaskList.get(ll);
                                      if(lt.getTaskName().equalsIgnoreCase("Translation"))
                                         totalLinTasks.add(lt);
                                       //linTasksArray.
                                 //   System.out.println("linTaskList" +lt.getTaskName());

                                    }
                      for(int ll=0;ll<linTaskList.size();ll++){
                                         LinTask lt=(LinTask) linTaskList.get(ll);
                                      if(lt.getTaskName().equalsIgnoreCase("Editing"))
                                         totalLinTasks.add(lt);
                                       //linTasksArray.
                                 //    System.out.println("linTaskList" +lt.getTaskName());

                                    }
                      for(int ll=0;ll<linTaskList.size();ll++){
                                         LinTask lt=(LinTask) linTaskList.get(ll);
                                      if(lt.getTaskName().equalsIgnoreCase("ICR "))
                                         totalLinTasks.add(lt);
                                       //linTasksArray.
                                //    System.out.println("linTaskList" +lt.getTaskName());

                                    }
                      for(int ll=0;ll<linTaskList.size();ll++){
                                         LinTask lt=(LinTask) linTaskList.get(ll);
                                      if(lt.getTaskName().equalsIgnoreCase("Proofreading "))
                                         totalLinTasks.add(lt);
                                       //linTasksArray.
                                    //  System.out.println("linTaskList" +lt.getTaskName());

                                    }
                      for(int ll=0;ll<linTaskList.size();ll++){
                                         LinTask lt=(LinTask) linTaskList.get(ll);
                                      if(!lt.getTaskName().equalsIgnoreCase("Proofreading ")&&!lt.getTaskName().equalsIgnoreCase("ICR ")&&!lt.getTaskName().equalsIgnoreCase("Editing")&&!lt.getTaskName().equalsIgnoreCase("Translation"))
                                         totalLinTasks.add(lt);
                                       //linTasksArray.
                                    //  System.out.println("linTaskList" +lt.getTaskName());

                                    }




                      for(int ll=0;ll<engTaskList.size();ll++){
                                         EngTask et=(EngTask) engTaskList.get(ll);
                                          totalEngTasks.add(et);
                                          System.out.println(et.getTaskName());
                               }

                      for(int ll=0;ll<forTaskList.size();ll++){
                                         DtpTask dt=(DtpTask) forTaskList.get(ll);

                                         totalDtpTasks.add(dt);
                                         System.out.println(dt.getTaskName());

                       }
                      for(int ll=0;ll<otherTaskList.size();ll++){
                                         OthTask ot=(OthTask) otherTaskList.get(ll);
                                         if(!ot.getTaskName().equalsIgnoreCase("Project Management"))
                                        totalOthTasks.add(ot);
                                     }

            }
        }
              }}
        //Sort by task (orderNum), then source (language), then target (language)
       // Collections.sort(totalLinTasks, CompareTaskLin.getInstance());
       //Collections.sort(totalEngTasks, CompareTaskEng.getInstance());
       // Collections.sort(totalDtpTasks, CompareTaskDtp.getInstance());
      //  Collections.sort(totalOthTasks, CompareTaskOth.getInstance());

        //array for display in jsp
        LinTask[] linTasksArray = (LinTask[]) totalLinTasks.toArray(new LinTask[0]);
        EngTask[] engTasksArray = (EngTask[]) totalEngTasks.toArray(new EngTask[0]);
        DtpTask[] dtpTasksArray = (DtpTask[]) totalDtpTasks.toArray(new DtpTask[0]);
        OthTask[] othTasksArray = (OthTask[]) totalOthTasks.toArray(new OthTask[0]);
        System.out.println("linnnnnnnnnnnnnnnnnnnnn Task..............."+linTasksArray.length);
        //find total of LinTasks
        double linTaskTotal = 0;
        for(int i = 0; i < linTasksArray.length; i++) {
            if(linTasksArray[i].getDollarTotal() != null) {
                //remove comma's
                String linTotal = linTasksArray[i].getDollarTotal();
                linTotal = linTotal.replaceAll(",","");
                Double total = Double.valueOf(linTotal);
                linTaskTotal += total.doubleValue();

            }
        }

        //find total of EngTasks
        double engTaskTotal = 0;
        for(int i = 0; i < engTasksArray.length; i++) {
            if(engTasksArray[i].getDollarTotal() != null) {
                //remove comma's
                String engTotal = engTasksArray[i].getDollarTotal();
                engTotal = engTotal.replaceAll(",","");
                Double total = Double.valueOf(engTotal);
                engTaskTotal += total.doubleValue();
            }
        }

        //find total of DtpTasks
        double dtpTaskTotal = 0;
        for(int i = 0; i < dtpTasksArray.length; i++) {
            if(dtpTasksArray[i].getDollarTotal() != null) {
                //remove comma's
                String dtpTotal = dtpTasksArray[i].getDollarTotal();
                dtpTotal = dtpTotal.replaceAll(",","");
                Double total = Double.valueOf(dtpTotal);
                dtpTaskTotal += total.doubleValue();
            }
        }

        //find total of OthTasks
        double othTaskTotal = 0;
        for(int i = 0; i < othTasksArray.length; i++) {
            if(othTasksArray[i].getDollarTotal() != null) {
                //remove comma's
                String othTotal = othTasksArray[i].getDollarTotal();
                othTotal = othTotal.replaceAll(",","");
                Double total = Double.valueOf(othTotal);
                othTaskTotal += total.doubleValue();
            }
        }

        //place TaskTotals in request as formated string
        request.setAttribute("linTaskTotal", StandardCode.getInstance().formatDouble(new Double(linTaskTotal)));
        request.setAttribute("engTaskTotal", StandardCode.getInstance().formatDouble(new Double(engTaskTotal)));
        request.setAttribute("dtpTaskTotal", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotal)));
        request.setAttribute("othTaskTotal", StandardCode.getInstance().formatDouble(new Double(othTaskTotal)));

        //place all Tasks for this quote into the form for display
        DynaValidatorForm qvg = (DynaValidatorForm) form;
        qvg.set("linTasks", linTasksArray);
        qvg.set("engTasks", engTasksArray);
        qvg.set("dtpTasks", dtpTasksArray);
        qvg.set("othTasks", othTasksArray);

        //place pm and rush block into form for display
        qvg.set("pmPercent", q.getPmPercent());
        qvg.set("pmPercentDollarTotal", q.getPmPercentDollarTotal());
        qvg.set("rushPercent", q.getRushPercent());
        qvg.set("rushPercentDollarTotal", q.getRushPercentDollarTotal());
        qvg.set("approvalTimeEsimate", q.getApprovalTimeEsimate());
        qvg.set("archiveId", q.getArchiveId());
        System.out.println(q.getApprovalTimeEsimate());

        //HERE down is standard and does not need to change when adding task blocks
        //place this quote into request for further display in jsp page
        request.setAttribute("quote", q);

        //add this quote id to cookies; this will remember the last quote
        response.addCookie(StandardCode.getInstance().setCookie("quoteViewId", quoteId));

        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("quoteViewTab", "General Info"));

        //an update of totals may be required
        Integer autoUpdate = (Integer) request.getAttribute("AutoUpdate");
        if(autoUpdate != null && autoUpdate.equals(new Integer(0))) { //make sure it was just updated
            return (mapping.findForward("AutoUpdate"));
        }

	// Forward control to the specified success URI
       User u=UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username")) ;




           if(u.getuserType() != null && u.getuserType().equalsIgnoreCase("client")){
        return (mapping.findForward("ClientSuccess"));}
           else
               return (mapping.findForward("Success"));



    }

}

