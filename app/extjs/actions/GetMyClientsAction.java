//HomePageAction.java prepares the display for homepage info
//such as announcements

package app.extjs.actions;
import app.extjs.helpers.ClientHelper;
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
import java.io.OutputStream;
import java.io.PrintWriter;
import org.json.CDL;
import org.json.JSONArray;

public final class GetMyClientsAction extends Action {
    public GetMyClientsAction()
    {
        //System.out.println("GetMyClientsAction constructor*********************************");
    }
    
    
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
        
        //get the current user for displaying personal info, such as "My Projects"
        User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
        
        //get a user's projects
        //ArrayList myClients = new ArrayList();
        //get a user's Backup projects
        long startProjects = System.currentTimeMillis();
        String myName = u.getFirstName() + " " + u.getLastName();
        
         
        String print = request.getParameter("print");
        if(print!=null){
         if(print.equalsIgnoreCase("yes")){
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=\"ClientList_"+myName.replaceAll(" ","_")+".csv\"");
            try
            {
                List myClients = ClientHelper.getClientListForPMPrint(myName);
                String csv = CDL.toString(new JSONArray(myClients.toArray()));
                //System.out.println(csv);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(csv.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            }
            catch(Exception e)
            {
                //System.out.println(e.toString());
            }
             return (null);
         }}
        
        List myClients = ClientHelper.getClientListForPM(myName);
           
        long endProjects = System.currentTimeMillis();
        //System.out.println("GetMyClientsAction took:"+ ((endProjects-startProjects)/1000.0));
        
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        
        // //System.out.println(actResponse.toXML());
        PrintWriter out = response.getWriter();
        
        out.println(new JSONArray(myClients.toArray()));
        out.flush();
        
        // Forward control to the specified success URI
        return (null);
    }
    
}
