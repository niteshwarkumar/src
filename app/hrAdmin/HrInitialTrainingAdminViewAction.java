/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.hrAdmin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import app.db.*;
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
/**
 *
 * @author Niteshwar
 */
public class HrInitialTrainingAdminViewAction  extends Action {


     private Log log =
        LogFactory.getLog("org.apache.struts.webapp.Example");

        public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)throws Exception

    {

           List result = new ArrayList();

       String userId = null;
	userId = request.getParameter("userViewId");
        User  u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));

        //check attribute in request
        if(userId == null) {
            userId = (String) request.getAttribute("userViewId");
        }

        //id of user from cookie
        if(userId == null) {
          //  userId = StandardCode.getInstance().getCookie("userViewId", request.getCookies());
            userId=""+u.getUserId();
        }

        //default user to first if not in request or cookie
        if(userId == null || "null".equals(userId)) {
//                List results = UserService.getInstance().getUserList();
                //look for first human user
//                for(ListIterator iter = results.listIterator(); iter.hasNext();) {
//                     u = (User) iter.next();
//                    if(u.getLastName() != null && u.getLastName().length() > 0) {
//                        userId = String.valueOf(u.getUserId());
//                        break;
//                    }
//                }
             userId = String.valueOf(u.getUserId());

        }

        Integer id = Integer.valueOf(userId);

        //END get id of current user from either request, attribute, or cookie

        //get user to edit
        u = UserService.getInstance().getSingleUser(id);

	 Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;
      String jsonProducts = request.getParameter("blogJSON");

        try {

            tx = session.beginTransaction();


         PreparedStatement pstmt = session.connection().prepareStatement("select * from traininginitialadmin ");
         ResultSet rs = pstmt.executeQuery();
         // ClientService.getInstance().getBlogList();



           while(rs.next())
            {
            JSONObject jo = new JSONObject();
            jo.put("id", rs.getInt("id"));
            jo.put("empname", rs.getString("empname"));
            jo.put("department", rs.getString("department"));
            jo.put("position", rs.getString("position"));
            jo.put("hiredate", rs.getDate("hiredate"));
            jo.put("mentor", rs.getString("mentor"));
            jo.put("supervisor", rs.getString("supervisor"));

            result.add(jo);
            }
            tx.commit();
        }
        catch (Exception e) {
            try {
                tx.rollback(); //error
            }
            catch (HibernateException he) {
                System.err.println("Hibernate Exception" + e.getMessage());
                throw new RuntimeException(e);
            }
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        }
                /*
                 * Regardless of whether the above processing resulted in an Exception
                 * or proceeded normally, we want to close the Hibernate session.  When
                 * closing the session, we must allow for the possibility of a Hibernate
                 * Exception.
                 *
                 */
        finally {
            if (session != null) {
                try {

                    session.close();
                }
                catch (HibernateException e) {
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

        out.println(new JSONArray(result.toArray()));
        //request.setAttribute("blogJSArray",new JSONArray(results.toArray()));

        out.flush();

        // Forward control to the specified success URI
        return (null);

    }



}
