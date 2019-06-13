/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.quote;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import app.db.*;


import app.extjs.vo.Product;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.struts.util.MessageResources;
import javax.servlet.http.HttpSession;


/**
 *
 * @author Neil
 */
public class ClientNewQuote extends Action {

    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {


        MessageResources messages = getResources(request);

        // save errors
        ActionMessages errors = new ActionMessages();

        List results = new ArrayList();
        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;
        //  String jsonProducts = request.getParameter("clientQuote2JSON");
        try {
            tx = session.beginTransaction();



            //clientId = Integer.parseInt(request.getSession(false).getAttribute("clientViewId").toString());


            // String quoteId = request.getSession(false).getAttribute("quoteViewId").toString();
            String quoteId = request.getParameter("quoteViewId");

            //StandardCode.getInstance().getCookie("quoteAddId", request.getCookies());




            // String quoteViewId =request.getParameter("quoteViewId");

            //System.out.println("quoteViewId" + quoteId + "         " + request.getParameter("quoteViewId"));
            Integer CQuote = Integer.parseInt(quoteId);
            //System.out.println("getNewClientQuoteNumber" + CQuote);
              HttpSession session1 = request.getSession(false);
                 session1.setAttribute("quoteViewId", request.getParameter("quoteViewId"));
                 //System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"+request.getSession(false).getAttribute("quoteViewId").toString());
           // session.s
            PreparedStatement pstmt = session.connection().prepareStatement("select Product_ID,medical,component,Type from client_quote where Quote_ID = " + CQuote);
            ResultSet rs = pstmt.executeQuery();




            //System.out.println("                  working                     ");

//int id=Integer.parseInt(rs.getString("Product_ID"));
            List newQA = QuoteService.getInstance().getSingleClientQuote(CQuote);
            int i = 0;
            ////System.out.println("Product_IDProduct_ID"+rs.getInt(Product_ID));
// Product pr=app.client.ClientService.getInstance().getSingleProduct(id);

            if (u.getuserType() != null && u.getuserType().equalsIgnoreCase("client")) {







                while (rs.next()) {
                    JSONObject jo = new JSONObject();
                    Client_Quote ur = (Client_Quote) newQA.get(i);
                    Product pr = app.client.ClientService.getInstance().getSingleProduct(ur.getProduct_ID());
                    jo.put("product", pr.getProduct());
                    jo.put("category", pr.getCategory());
                    jo.put("description", pr.getDescription());
                    jo.put("medical", rs.getString("medical"));
                    jo.put("component", rs.getString("component"));
                    jo.put("detail", rs.getString("Type"));



                    i++;

                    //System.out.println("Type.............." + rs.getString("Type"));
                    results.add(jo);
                }
                tx.commit();

            }


        } catch (Exception e) {
            try {
                tx.rollback(); //error
            } catch (HibernateException he) {
                System.err.println("Hibernate Exception" + e.getMessage());
                throw new RuntimeException(e);
            }
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
            if (session != null) {
                try {

                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }
        //ClientService.getInstance().updateClientLocation(Integer.parseInt(id),request.getParameter("locationJSON"));

        response.setContentType("text/html");
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
