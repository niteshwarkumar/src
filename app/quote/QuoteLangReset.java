/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.quote;

import app.security.SecurityService;
import app.standardCode.StandardCode;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import java.util.List;
import javax.servlet.http.HttpSession;


/**
 *
 * @author Niteshwar
 */
public class QuoteLangReset extends Action{

  public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

	// Extract attributes we will need
	org.apache.struts.util.MessageResources messages = getResources(request);

	// save errors
	ActionMessages errors = new ActionMessages();

        Integer qid=0;
        Integer pid=1;


        //START check for login (security)
        if(!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
       // String quoteId = request.getParameter("quoteViewId");
        String quoteId = StandardCode.getInstance().getCookie("quoteAddId", request.getCookies());


        //String quoteViewId=request.getSession(false).getAttribute("quoteViewId").toString();
     try{
        String qid1=request.getSession(false).getAttribute("clientQuoteId").toString();
        if(qid1!=null)qid=Integer.parseInt(qid1);
     }

      catch(Exception e) {

     }
       Integer Cquote= Integer.parseInt(quoteId);
       List cq=QuoteService.getInstance().getClient_Quote(Cquote);
       Integer cqid=0;
         System.out.println("cq sizeeeeeeeeeeeeeeeeeeee"+cq.size());
       for(int i=0;i<cq.size();i++)
       {
           Client_Quote Quote=(Client_Quote) cq.get(i);
       cqid=Quote.getId();

       }
         System.out.println("iddddddddddddddddddd" + cqid);


         if(qid==0){
          Client_Quote Quote=(Client_Quote) cq.get(0);
          cqid=Quote.getId();
         HttpSession session = request.getSession(false);
         session.setAttribute("clientQuoteId", String.valueOf(cqid));
         session.setAttribute("pid", String.valueOf(pid));
          System.out.println("iiiiiiiiiiiiiiiiiiiiiiiiiiii" + request.getSession(false).getAttribute("clientQuoteId").toString());
           return (mapping.findForward("Iterate"));


         }else
       if(qid<cqid){
           Integer id=qid;
        HttpSession session = request.getSession(false);
        session.setAttribute("clientQuoteId", String.valueOf(qid+1));
        session.setAttribute("pid", String.valueOf(pid+1));
         System.out.println("dddddddddddddddddddddddddddddddd" + request.getSession(false).getAttribute("clientQuoteId").toString());
         return (mapping.findForward("Iterate"));


       }else

             //  request.getSession(false).getAttribute("clientViewId").toString()

       {

        HttpSession session = request.getSession(false);
        Integer zero=0;
        session.setAttribute("clientQuoteId", String.valueOf(zero));
         session.setAttribute("pid", String.valueOf(zero));
           System.out.println("zeroooooooooooooooo" +request.getSession(false).getAttribute("clientQuoteId").toString());
        return (mapping.findForward("Iterate"));}
    }

}
