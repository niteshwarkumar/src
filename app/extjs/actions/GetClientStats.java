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
import app.security.*;
import java.io.PrintWriter;
import java.util.List;
import org.json.JSONArray;


public final class GetClientStats extends Action {

    public GetClientStats()
    {
    //System.out.println ("GetClientStats constructor calling**************************************");
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
        
        
        
        
        //response.setContentType("text/html");
        //response.setHeader("Cache-Control", "no-cache");
        // //System.out.println(actResponse.toXML());
        // PrintWriter out = response.getWriter();
        
        //out.println("<response success=\"true\"></response>");
        //out.flush();
        long startProjects = System.currentTimeMillis();
        List myStats = null;           
        String clientViewId = request.getParameter("clientViewId");
        String origin = request.getParameter("ORIGIN");
        Integer id = 0;
        String pm=null;
        try{
        id=Integer.parseInt(clientViewId);
        
        }catch(Exception e){//System.out.println("error msg"+e.getMessage());
            
        }

       
            if("WorkLoad".equals(origin)){
               myStats=ClientHelper.getClientStatsWorkLoad(id);
            }else if("Volume".equals(origin)){
                myStats=ClientHelper.getClientStatsVolume(id);
            }else if("Top10".equals(origin)){
                myStats=ClientHelper.getClientTop10(id);
            }else if("ProjectSize".equals(origin)){
                 myStats=ClientHelper.getClientStatsProjectSize(id);
            }else if("PM".equals(origin)){
                myStats=ClientHelper.getClientStatsPM(id);
            }
        
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        // //System.out.println(actResponse.toXML());
        PrintWriter out = response.getWriter();
        long endProjects = System.currentTimeMillis();
        out.println(new JSONArray(myStats.toArray()));
        out.flush();
        //System.out.println("GetClientStats took:"+ ((endProjects-startProjects)/1000.0));
        
        // Forward control to the specified success URI
       return (null);
        //return null;
    }
    
}
