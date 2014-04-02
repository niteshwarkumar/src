/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.quote;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
/**
 *
 * @author Niteshwar
 */
public class QuotePriorityUpdateAction extends Action{

    //     private Log log =
    //    LogFactory.getLog("org.apache.struts.webapp.Example");
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        int startList=0,endList=0;
        int pstart=Integer.parseInt(request.getParameter("startindex"));
        int pend=Integer.parseInt(request.getParameter("endindex"));
        if(pstart>pend){
        startList=pend;
        endList=pstart;
        }else{
        startList=pstart;
        endList=pend;
        }

       List quotePriorityList=QuoteService.getInstance().getQuotePriorityListBetween(startList,endList);
        for(int i=0;i<quotePriorityList.size();i++){
        QuotePriority qpl=(QuotePriority) quotePriorityList.get(i);
        if(qpl.getPriority()==pstart){
        qpl.setPriority(pend);
        Quote1 q=QuoteService.getInstance().getSingleQuote(qpl.getID_Quote1());
         request.setAttribute("quote", q);
        }else if(qpl.getPriority()>pstart&&qpl.getPriority()<=pend){
        qpl.setPriority(qpl.getPriority()-1);
        }
        else if(qpl.getPriority()<pstart&&qpl.getPriority()>=pend){
        qpl.setPriority(qpl.getPriority()+1);
        }
         
        QuoteService.getInstance().addQuotePriority(qpl);
        }

       List qpl1 = QuoteService.getInstance().getQuotePriorityList();
        for(int i=0;i<qpl1.size();i++){
           QuotePriority qplist=(QuotePriority) qpl1.get(i);
           qplist.setPriority(i+1);
           QuoteService.getInstance().addQuotePriority(qplist);

        }


       

    return (mapping.findForward("Success"));
    }

}
