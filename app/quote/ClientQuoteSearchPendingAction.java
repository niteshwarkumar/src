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
import java.lang.*;
import app.user.*;
import app.standardCode.*;
import app.project.*;
import app.security.*;

/**
 *
 * @author Neil
 */
public class ClientQuoteSearchPendingAction extends Action{


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
//        //END check for login (security)
//        //dsf;
//
//        User user = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
//        //values for search
//      //  DynaValidatorForm qs = (DynaValidatorForm) form;
//        Project project = new Project();
//        String status1 = "pending";
//
//
//        Integer id = user.getID_Client();
//        //String companyName = (String) qs.get("companyName");
//        String quoteNum ="";//= (String) qs.get("quoteNum");
//        String product1 ="";//= (String) qs.get("productQuoteSearch");
//
//
//        String productDescription ="";//= (String) qs.get("productDescriptionQuoteSearch");
//
//        String startQuoteDate="";// = (String) qs.get("startQuoteDate");
//        String endQuoteDate ="";//= (String) qs.get("endQuoteDate");
//        String startQuoteTotal ="";//= (String) qs.get("startQuoteTotal");
//        String endQuoteTotal ="";//= (String) qs.get("endQuoteTotal");
//
//        //convert date and double (quote total)
//     Date startQuoteDateDate = null;
//        if(startQuoteDate.length() >= 1) {
//            startQuoteDateDate = DateService.getInstance().convertDate(startQuoteDate).getTime();
//        }
//       Date endQuoteDateDate = null;
//        if(endQuoteDate.length() >= 1) {
//            endQuoteDateDate = DateService.getInstance().convertDate(endQuoteDate).getTime();
//        }
//       Double startQuoteTotalDouble = null;
//        if(startQuoteTotal.length() >= 1) {
//            startQuoteTotalDouble = Double.valueOf(startQuoteTotal);
//        }
//        Double endQuoteTotalDouble = null;
//        if(endQuoteTotal.length() >= 1) {
//           endQuoteTotalDouble = Double.valueOf(endQuoteTotal);
//        }
//
//        //perform search and store results in a List
//       project.setProduct(product1);
//       project.setProductDescription(productDescription);
//
//       List results = null;
//       try{
//       results = QuoteService.getInstance().getClientQuoteSearch(status1,id, project,productDescription,quoteNum, startQuoteDateDate, endQuoteDateDate, startQuoteTotalDouble, endQuoteTotalDouble);
//       }
//       catch(Exception e){
//           e.printStackTrace();
//       }
//       // System.out.println("Result size of Quote >>>>>>>>>>>>>>>"+results.size());
//        ArrayList finalResults = new ArrayList();
//        System.out.println("Status                         "+status1);
//        //remove all sub quotes (only display the most recent quote per parent object (project))
//        if(results != null) { //there are results to process
//            for(ListIterator iter = results.listIterator(); iter.hasNext();) {
//                Quote1 q = (Quote1) iter.next();
//                Project p = q.getProject();
//                //System.out.println("p.getProjectId()="+p.getProjectId());
//                Project pLazyLoad = null;
//                try{
//                    pLazyLoad = ProjectService.getInstance().getSingleProject(p.getProjectId());
//                    Set quotes = pLazyLoad.getQuotes();
//                    Quote1 lastQuote = QuoteService.getInstance().getLastQuote(quotes);
//                    if(lastQuote!=null && q.getNumber().equals(lastQuote.getNumber())) {
//                        //add this quote to final list; it is  the last modified sub quote
//                        finalResults.add(q);
//                    }
//
//                }catch(Exception e){
//                    System.out.println("QuoteSearchAction::"+e);
//                }
//
//            }
//        }
//
//        //place final results in attribute for displaying in jsp
//        request.setAttribute("results", finalResults);
//
//        //place result size into request
//        if(results != null) {
//            request.setAttribute("quoteSearchResults", String.valueOf(finalResults.size()));
//        }
//        else {
//            request.setAttribute("quoteSearchResults", "0");
//        }
        
                        String projectYear=request.getParameter("projectYear");
        request.setAttribute("projectYear", projectYear);

	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
