/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.menu;

/**
 *
 * @author Neil
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;




import app.extjs.vo.Product;
import app.menu.*;
import app.user.User;
import app.user.UserService;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 *
 * @author Niteshwar
 */
public class GetSWListAction extends Action{

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
                                                                 HttpServletResponse response)throws Exception

    {




    List results = new ArrayList();

                // Session session = ConnectionFactory.getInstance().getSession();
      //  boolean t = true;
     //   Transaction tx = null;
      //String jsonProducts = request.getParameter("clientQuote2JSON");
        try {

List SW=MenuService.getInstance().getSWList();

            System.out.println("                  working                     ");
            //String ty="lk";

for(int i=0;i<SW.size();i++){
    Sw h=(Sw)SW.get(i);
            JSONObject jo = new JSONObject();
            jo.put("Application",h.getApplication());
            jo.put("Description",h.getDescription());
            jo.put("Platform",h.getPlatform());
            jo.put("Language",h.getLanguage());
            jo.put("Version",h.getVersion());
            jo.put("Purchase",h.getPurchase_date());




           // System.out.println("Type.............."+rs.getString("Type"));
        results.add(jo);

          //  tx.commit();
}  }
        catch (Exception e) {
          //  try {
               // tx.rollback(); //error
           // }
            //catch (HibernateException he) {
               // System.err.println("Hibernate Exception" + e.getMessage());
             //   throw new RuntimeException(e);
            //}
           // System.err.println("Hibernate Exception" + e.getMessage());
           // throw new RuntimeException(e);
        }
                /*
                 * Regardless of whether the above processing resulted in an Exception
                 * or proceeded normally, we want to close the Hibernate session.  When
                 * closing the session, we must allow for the possibility of a Hibernate
                 * Exception.
                 *
                 */
      //  finally {
           // if (session != null) {
            //    try {
//
            //        session.close();
            //    }
             //   catch (HibernateException e) {
            //        System.err.println("Hibernate Exception" + e.getMessage());
             //       throw new RuntimeException(e);
            //    }

          //  }
       // }
           //ClientService.getInstance().updateClientLocation(Integer.parseInt(id),request.getParameter("locationJSON"));

     response.setContentType("text/json");
     response.setHeader("Cache-Control", "no-cache");
        // System.out.println(actResponse.toXML());
        PrintWriter out = response.getWriter();

        out.println(new JSONArray(results.toArray()));
        //request.setAttribute("blogJSArray",new JSONArray(results.toArray()));

        out.flush();

        // Forward control to the specified success URI
       return (null);

    }
}

