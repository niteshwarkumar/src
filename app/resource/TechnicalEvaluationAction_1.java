/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.resource;
import javax.servlet.http.HttpServletRequest ;
    import javax.servlet.http.HttpServletResponse ;
    import org.apache.commons.logging.Log ;
    import org.apache.commons.logging.LogFactory ;
    import  org.apache.struts.action.*;
    import org.apache.struts.action.ActionForm;
    import org.apache.struts.action.ActionForward;
    import org.apache.struts.action.ActionMapping;
    import app.db.*;
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

/**
 *
 * @author Neil
 */
public class TechnicalEvaluationAction_1 extends Action {


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


            List results = new ArrayList();



            Session session = ConnectionFactory.getInstance().getSession();
            boolean t = true;
            Transaction tx = null;
            String jsonProducts = request.getParameter("blogJSON");
            
            String resource=request.getParameter("resourceViewId");


              //System.out.println("resourceeeeeeeeeeeeeeee"+resource);
            if(resource==null)
                       resource= request.getSession(false).getAttribute("resource").toString();

           //   resource=request.getAttribute(resource).toString();
              //System.out.println("resourceeBBBBBBBBBBBBBBBBBB"+resource);
            try {

                tx = session.beginTransaction();


                PreparedStatement pstmt = session.connection().prepareStatement("select eval_id,evaluation_area,enteredBy,projectId,comment,rate from evaluation where evaluation.resourceId='"+resource+"'");
                ResultSet rs = pstmt.executeQuery();
                // ClientService.getInstance().getBlogList();
                Integer result = null;


                while (rs.next()) {
                    JSONObject jo = new JSONObject();
                    jo.put("id", rs.getInt("eval_id"));
                    jo.put("topic", rs.getString("evaluation_area"));
                    jo.put("enteredBy", rs.getString("enteredBy"));
                    jo.put("project", rs.getString("projectId"));
                    jo.put("comment", rs.getString("comment"));
                    jo.put("rate", rs.getInt("rate"));


                    //System.out.println("author.............." + rs.getString("evaluation_area"));
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

