/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.quote;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Neil
 */
import app.client.ClientService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import app.db.*;


import app.extjs.vo.Product;
import app.standardCode.StandardCode;
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

public final class ClientQuote2Action extends Action {

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
            HttpServletResponse response) throws Exception {


        MessageResources messages = getResources(request);

	// save errors
	ActionMessages errors = new ActionMessages();

        List results = new ArrayList();

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;
        String jsonProducts = request.getParameter("clientQuote2JSON");
        try {
            tx = session.beginTransaction();
           //  String quoteId = StandardCode.getInstance().getCookie("quoteAddId", request.getCookies());
             String quoteId=request.getSession(false).getAttribute("quoteViewId").toString();
             String quoteViewId =quoteId ;//request.getParameter("quoteViewId");
                
            //System.out.println("quoteViewId"+quoteId+"         "+request.getParameter("quoteViewId")+"        "+quoteViewId);
           Integer CQuote =Integer.parseInt(quoteViewId);
            //System.out.println("getNewClientQuoteNumber" + CQuote);

            PreparedStatement pstmt = session.connection().prepareStatement("select Type,application,os,unit,volume,version,deliverable from client_quote where Quote_ID = " + CQuote);
            ResultSet rs = pstmt.executeQuery();
//int pid=rs.getInt(Product_ID);
 // Product prod = app.client.ClientService.getInstance().getSingleProduct(rs.getInt(Product_ID));
 

            //System.out.println("                  working                     ");
       

            while (rs.next()) {
                JSONObject jo = new JSONObject();

                jo.put("detail", rs.getString("Type"));
                jo.put("application", rs.getString("application"));
                jo.put("deliverable", rs.getString("deliverable"));
                jo.put("unit",rs.getString("unit"));
                jo.put("volume",rs.getString("volume"));
                jo.put("os", rs.getString("os"));
                jo.put("version",rs.getString("version"));




                //System.out.println("Type.............." + rs.getString("Type"));
                results.add(jo);
            }
            tx.commit();
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

