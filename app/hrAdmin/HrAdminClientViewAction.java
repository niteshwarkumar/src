//HomePageAction.java prepares the display for homepage info
//such as announcements

package app.hrAdmin;
import app.client.Client;
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
import java.util.*;
import app.user.*;
import app.security.*;
import app.standardCode.StandardCode;
import java.io.PrintWriter;
import org.json.JSONArray;
import org.json.JSONObject;


public final class HrAdminClientViewAction extends Action {


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
        //END check for login (security)
        List results = UserService.getInstance().getUserList();
        List result = new ArrayList();
        for(ListIterator iter = results.listIterator(); iter.hasNext(); ) {
        User u = (User) iter.next();

        //if from current city display
        if((u.getuserType()==null||u.getuserType().equalsIgnoreCase("admin"))){
        System.out.println("here");
        } else if(u.getuserType().equalsIgnoreCase("client")){
        Client client=ClientService.getInstance().getClient(u.getId_client());
        String lastName = u.getLastName();
        if(lastName != null){
        lastName = lastName.replaceAll(" ","");
        }
       // String uname;
        JSONObject jo = new JSONObject();

        jo.put("uname"," <a href=javascript:deleteEmployee(\""+StandardCode.getInstance().noNull(lastName) +"\",\"" + u.getUserId() + "\")>" + StandardCode.getInstance().noNull(u.getFirstName()) + " " + StandardCode.getInstance().noNull(u.getLastName()) + "</a>");
       // jo.put("uname", u.getUsername());
        jo.put("cname",client.getCompany_name());
        jo.put("skypeId",u.getSkypeId());
         result.add(jo);
        }}

        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        // System.out.println(actResponse.toXML());
        PrintWriter out = response.getWriter();

        out.println(new JSONArray(result.toArray()));
        //request.setAttribute("blogJSArray",new JSONArray(results.toArray()));

        out.flush();


        return (null);
    }

}
