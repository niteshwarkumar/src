//HomePageAction.java prepares the display for homepage info
//such as announcements

package app.extjs.actions;
import app.extjs.helpers.QuoteHelper;
import app.quote.Quote1;
import app.quote.QuoteService;
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
import java.io.PrintWriter;
import org.json.JSONArray;
import org.json.JSONObject;

public final class GetMyBackupQuotesAction extends Action {
    
    
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
        ArrayList myQuotes = new ArrayList();
        //get a user's Backup projects
        long startProjects = System.currentTimeMillis();
        //System.out.println("getPMBackupQuotes entered:");
        
        
//look for pm or ae that matches active projects for this user
        String myName = u.getFirstName() + " " + u.getLastName();
//        List pmQuotes = QuoteHelper.getPMBackupQuotes(myName);
        List pmQuotes = QuoteService.getInstance().getPMBackupQuotes(myName);
        Quote1 q = null;
        
        if(null != pmQuotes){
        for(ListIterator iter = pmQuotes.listIterator(); iter.hasNext();) {
             
                q = (Quote1) iter.next();
                JSONObject jo = QuoteHelper.quoteToJson(q);
                myQuotes.add(jo);          
            }
        }
           
        long endProjects = System.currentTimeMillis();
        //System.out.println("getPMBackupQuotes took:"+ ((endProjects-startProjects)/1000.0));
        
        
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        // //System.out.println(actResponse.toXML());
        PrintWriter out = response.getWriter();
        out.println(new JSONArray(myQuotes.toArray()));
        out.flush();
        
        
 /*
            if(p.getCompany().getBackup_pm() != null) {
                if(p.getCompany().getBackup_pm().equals(myName)) {
                    myBackupProjects.add(p);
                }
            }
  
        }
        //place my projects into request for display
        request.setAttribute("myProjects", myProjects);
        long endProjects = System.currentTimeMillis();
        //look for backup pm that matches active projects for this user
  
        //place my backup projects into request for display
        request.setAttribute("myBackupProjects", myBackupProjects);
  
        //get a user's quotes
        ArrayList myQuotes = new ArrayList();
        long startQuote = System.currentTimeMillis();
  
        List allQuotes = QuoteService.getInstance().getQuoteListPending();
        //get a user's quotes
        ArrayList myBackupQuotes = new ArrayList();
        //look for pm or ae that matches pending quotes for this user
        for(ListIterator iter = allQuotes.listIterator(); iter.hasNext();) {
            Quote1 q = (Quote1) iter.next();
            boolean added = false;
            Project p = q.getProject();
            //Project pLazyLoad = ProjectService.getInstance().getSingleProject(p.getProjectId());
  
            Quote1 recentQ = QuoteService.getInstance().getLastQuote(p.getQuotes());
            if(q.getNumber().equals(recentQ.getNumber())) { //only add most recent quote
                if(q.getProject().getPm() != null) {
                    if(q.getProject().getPm().equals(myName)) {
                        myQuotes.add(q);
                        added = true;
                    }
                }
                if(q.getProject().getCompany().getSales_rep() != null && !added) {
                    if(q.getProject().getCompany().getSales_rep().equals(myName)) {
                        myQuotes.add(q);
                    }
                }
  
                if(q.getProject().getCompany().getBackup_pm() != null) {
                    if(q.getProject().getCompany().getBackup_pm().equals(myName)) {
                        myBackupQuotes.add(q);
                        added = true;
                    }
                }
  
  
            }
        }
        //place my quotes into request for display
        request.setAttribute("myQuotes", myQuotes);
  
  
        //look for backup pm that matches pending quotes for this user
  
  
        long endQuote = System.currentTimeMillis();
        //place my backup quotes into request for display
        request.setAttribute("myBackupQuotes", myBackupQuotes);
  
        //get announcements
        List announcements = MenuService.getInstance().getAnnouncementList();
        //place announcements into request for display
        request.setAttribute("announcements", announcements);
        long end = System.currentTimeMillis();
  
        Date d = new Date();
        int currentMonth = d.getMonth();
        int currentYear = d.getYear();
        if(request.getParameter("abscencesMonth")!=null){
            currentMonth = Integer.parseInt(request.getParameter("abscencesMonth"));
            currentYear = Integer.parseInt(request.getParameter("abscencesYear"));
        }
        if(currentYear<1000){
            currentYear+=1900;
        }
        if(currentMonth==-1){
            request.getSession(false).setAttribute("abscencesMonth","11");
            request.getSession(false).setAttribute("abscencesYear",(currentYear)+"");
        }else if(currentMonth==12){
            request.getSession(false).setAttribute("abscencesMonth","0");
            request.getSession(false).setAttribute("abscencesYear",(currentYear)+"");
  
        }else{
            request.getSession(false).setAttribute("abscencesMonth",currentMonth+"");
            request.getSession(false).setAttribute("abscencesYear",(currentYear)+"");
        }
  
  */
        
        
        
        
        // Forward control to the specified success URI
        return (null);
    }
    
}
