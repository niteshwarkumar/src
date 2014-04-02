/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.client;

/**
 *
 * @author Neil
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
public class GetMyAE extends Action{

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


String name1="";
String name2="";
User u=UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
try{
Client c=ClientService.getInstance().getClient(u.getId_client());
String name[]=c.getSales_rep().split(" ");
 name1=name[0];
name2="";
try{
name2=name[1];}catch(Exception e){name2="";}

try{
name2+=" "+name[2];
}catch(Exception e){name2+="";}
        System.out.println(name1);
        System.out.println(name2);


//for(int i=0;i<name.length();i++){
} catch (Exception e) {}


//}

//User pmu=UserService.getInstance().getSingleUserRealName(ERROR_KEY, ERROR_KEY)
    List results = new ArrayList();

                // Session session = ConnectionFactory.getInstance().getSession();
      //  boolean t = true;
     //   Transaction tx = null;
      //String jsonProducts = request.getParameter("clientQuote2JSON");


Client HW=ClientService.getInstance().getClient(u.getId_client());

            System.out.println("                  working                     ");
            //String ty="lk";

try{
User uPM=UserService.getInstance().getSingleUserRealName(name1, name2);

            JSONObject jo = new JSONObject();
            jo.put("name",uPM.getFirstName()+" "+uPM.getLastName());
            jo.put("office",uPM.getWorkCity());
            jo.put("email",uPM.getWorkEmail1());
            jo.put("phone",uPM.getWorkPhone());
            jo.put("skype","");

        results.add(jo);

} catch (Exception e) {}





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

