/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.quote;

import app.client.ClientService;
import app.extjs.vo.Product;
import app.inteqa.INEngineering;
import app.inteqa.InteqaService;
import app.project.Project;
import app.project.ProjectService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;
import app.security.SecurityService;
import app.user.*;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

/**
 *
 * @author Neil
 */
public class ClientQuoteReviewUpdateAction extends Action {

    @SuppressWarnings({"static-access", "empty-statement"})
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        org.apache.struts.util.MessageResources messages = getResources(request);

        // save errors
        ActionMessages errors = new ActionMessages();

        //START check for login (security)
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        String id1 = request.getParameter("id");
        String quoteId="";
        try {
            quoteId = request.getSession(false).getAttribute("quoteViewId").toString();
        } catch (Exception e) {
            quoteId = request.getParameter("quoteViewId");
        }
        
        
        String quoteViewId = quoteId;//request.getParameter("quoteViewId");

        //System.out.println("quoteViewId" + quoteId + "         " + request.getParameter("quoteViewId") + "        " + quoteViewId);
        Integer CQuote = Integer.parseInt(quoteViewId);
        //System.out.println("getNewClientQuoteNumber" + CQuote);


        String jsonProducts = request.getParameter("clientQuoteJSON");
        int count = 0;
        if (jsonProducts != null && !"".equals(jsonProducts)) {
            JSONArray pro = new JSONArray(jsonProducts);
            count = pro.length();

        }
        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
        // Integer CQuote = QuoteService.getInstance().getNewClientQuoteNumber() - 1;

        List cqList = QuoteService.getInstance().getClient_Quote(CQuote);
        //System.out.println("Size of cqList" + cqList.size());


        //  Integer id = QuoteService.getInstance().getNewClientQuoteid() ;

        //  //System.out.println("idooooooooooooooooooooooooooooo"+id);

        if (jsonProducts != null && !"".equals(jsonProducts)) {
        }
        //System.out.println("Count--------------->" + count);
        
        Quote1 quo=QuoteService.getInstance().getSingleQuote(CQuote);
        Project p = ProjectService.getInstance().getSingleProject(quo.getProject().getProjectId());
        DynaValidatorForm qvg = (DynaValidatorForm) form;
        if (jsonProducts != null && !"".equals(jsonProducts)) {
            JSONArray products = new JSONArray(jsonProducts);

            Client_Quote pr = new Client_Quote();


            //System.out.println("CQUOTE00000000000000000000000000000----->" + products.length());

            Integer id;

            for (int n = 0; n < cqList.size(); n++) {
                Client_Quote q = (Client_Quote) cqList.get(n);
                //  rs.next();getSingleClient_Quote
                id = q.getId();
                //--id;
                //System.out.println("Count================>" + count + "              " + id);
                // Client_Quote q=QuoteService.getInstance().getSingleClient_Quote(id);
                JSONObject j = (JSONObject) products.get(n);
                //System.out.println("JSONObject>>>>>>>>>>>>>>>>>>>>>>>>>" + j);
                // Client_Quote pr = new Client_Quote();
                //  //System.out.println("Product ...................." + (j.getString("detail")));
                Product prod= ClientService.getSingleProduct(q.getID_Client(), j.getString("product"));
                q.setQuote_ID(CQuote);
                q.setID_Client(q.getID_Client());
                q.setProduct_ID(prod.getID_Product());

                q.setComponent(q.getComponent());
                ////System.out.println("Client Id   ------------------->" + rs.getInt("ID_Client") + "vvvvvvvvvvv" + rs.getInt("Product_ID"));

                q.getDeliverable();                //pr.
                q.setApplication(j.getString("application"));

                //  q.setVolume(j.getString("volume"));

                q.setOs(j.getString("os"));
                q.setVersion(j.getString("version"));
                q.setTarget_os(j.getString("tos"));
                q.setTarget_application(j.getString("tapplication"));
                q.setTarget_version(j.getString("tversion"));
                q.setTypeOfText(j.getString("ToT"));
                 
                try {
                    if(p.getTypeOfText().equals(null)){
                    p.setTypeOfText(j.getString("ToT"));
                    
                    }
                } catch (Exception e) {
                }
                try {
                    p.setProduct(prod.getProduct());
                } catch (Exception e) {
                }

                // //System.out.println("dataaaaaaaaa" + j.getString("detail") + "                " + j.getString("deliverable"));
                //System.out.println("idddddddd" + id);
                //   rs.deleteRow();
                QuoteService.getInstance().updateClientQuote(q);
                HttpSession session = request.getSession(false);
                session.setAttribute("clientQuoteId", String.valueOf(0));
                // QuoteService.getInstance().updateQuote(q);

               
                Quote1 q1 = QuoteService.getInstance().getSingleQuote(q.getQuote_ID());
                //System.out.println((String) qvg.get("approvalTimeEsimate"));
                q1.setApprovalTimeEsimate((String) qvg.get("approvalTimeEsimate"));
                
                QuoteService.getInstance().updateQuote(q1,(String)request.getSession(false).getAttribute("username"));
               
            }
        }
        try {
        p.setProjectDescription((String) qvg.get("productDescription"));
      } catch (Exception e) {
      }
         
         ProjectService.getInstance().updateProject(p);
        INEngineering iNEngineering = InteqaService.getInstance().getINEngineering(quo.getProject().getProjectId());
        if (iNEngineering == null) {
            iNEngineering = new INEngineering();
            iNEngineering.setProjectId(quo.getProject().getProjectId());
        }
                String filePrep01=request.getParameter("filePrep01");
                String filePrep02=request.getParameter("filePrep02");
        try{
        if (filePrep01.equalsIgnoreCase("on")) {
            iNEngineering.setFilePrep01(true);
        } else {
            iNEngineering.setFilePrep01(false);
        }}catch(Exception e){iNEngineering.setFilePrep01(false);
        }
       try{ if (filePrep02.equalsIgnoreCase("on")) {
            iNEngineering.setFilePrep02(true);
        } else {
            iNEngineering.setFilePrep02(false);
        }}catch(Exception e){ iNEngineering.setFilePrep02(false);
        }
                InteqaService.getInstance().updateInEngineering(iNEngineering);

        return (mapping.findForward("Success"));

    }
}
