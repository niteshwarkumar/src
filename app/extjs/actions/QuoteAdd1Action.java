//ProjectAdd1Action.java collects values related to 
//project from this part of the wizard
//It then adds this info to a newly created
//project and then adds it to the db

package app.extjs.actions;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.*;
import app.client.*;
import app.user.*;
import app.project.*;
import app.quote.Quote1;
import app.quote.QuoteService;
import app.standardCode.*;
import app.security.*;
import java.util.Date;

public final class QuoteAdd1Action extends Action {


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
	
        //values for adding a new project and quote
        DynaValidatorForm qae1 = (DynaValidatorForm) form;
        
        //from wizard add client
        Client c;
        //String clientViewId = (String) request.getAttribute("clientViewId");
        String clientViewId = request.getParameter("client");
        if(clientViewId == null) {
            String Company_name = (String) (qae1.get("client"));   
            c = ClientService.getInstance().getSingleClientByName(Company_name);  
        }
        else {
            c = ClientService.getInstance().getSingleClient(Integer.valueOf(clientViewId));
        }
        
        Quote1 newQ = null;

        if(request.getParameter("quoteViewId")==null || "".equals(request.getParameter("quoteViewId"))){
            Integer quoteId = QuoteService.getInstance().addQuoteWithNewProject(c, "000000"); 
            newQ = QuoteService.getInstance().getSingleQuote(quoteId); 
            if(request.getSession(false).getAttribute("username")!=null){
                newQ.setEnteredById((String)request.getSession(false).getAttribute("username"));
                newQ.setEnteredByTS(new Date());
               }
                QuoteService.getInstance().updateQuote(newQ);
                
        }else{
           
            newQ = QuoteService.getInstance().getSingleQuote(new Integer(request.getParameter("quoteViewId"))); 
            
        }
        

//insert new project into db, building one-to-many link between client and project
        
        
        //START add Inspection list to this project
        //Project p = QuoteService.getInstance().getSingleQuote(quoteId).getProject();
        Project p = ProjectService.getInstance().getSingleProject(newQ.getProject().getProjectId());
        
        
         
        
        p.setStatus("active");
        p.setPmPercent("10.00");
       // p.setProduct(qvg.);
        //p.setProductDescription(request.getParameter("productDescription") );
        p.setPm(request.getParameter("projectManager"));
             try{
        String[] fname = request.getParameter("projectManager").split(" ");
         User pm = null;
            if (fname.length == 2) {
                pm = UserService.getInstance().getSingleUserRealName(fname[0], fname[1]);
            } else if (fname.length == 3) {
                pm = UserService.getInstance().getSingleUserRealName(fname[0], fname[1] + " " + fname[2]);
            } else if (fname.length == 4) {
                pm = UserService.getInstance().getSingleUserRealName(fname[0], fname[1] + " " + fname[2] + " " + fname[3]);
            }
         p.setPm_id(pm.getUserId());
        }catch(Exception e){ p.setPm_id(0); }
        p.setAe(c.getSales_rep());
        
        User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
        p.setCreatedBy(u.getFirstName() + " " + u.getLastName());
        p.setCreatedDate(new Date(System.currentTimeMillis()));
        
        Integer projectId = ProjectService.getInstance().addProjectWithClient(p, c);              
             
        //insert into db, building link between contact and project
        String   contactId = request.getParameter("contact");
        ClientContact cc = ClientService.getInstance().getSingleClientContact(Integer.valueOf(contactId)); 
        ProjectService.getInstance().linkProjectClientContact(p, cc);           
        
        
        //Set PM RATE 
        ClientLanguagePair[] clp = null;
        if(c!=null){
            if(c.getClientLanguagePairs()!=null){
                clp = (ClientLanguagePair[])c.getClientLanguagePairs().toArray(new ClientLanguagePair[0]);
                
            }
        }
       if(clp!=null){ 
         
        for(int z=0; z<clp.length; z++){      
             
                            if( clp[z].getTask()!=null && clp[z].getTask().equals("PM - Percentage")){                            
                                p.setPmPercent(clp[z].getRate());
                                break;
                            }
                        }
       }
        
        //update project to db
        ProjectService.getInstance().updateProject(p); 
        
        //add this project id to cookies; this will remember the last project
        response.addCookie(StandardCode.getInstance().setCookie("projectAddId", String.valueOf(projectId)));
        response.addCookie(StandardCode.getInstance().setCookie("projectViewId", String.valueOf(projectId)));
        request.setAttribute("projectViewId", String.valueOf(projectId));    
        request.setAttribute("quoteViewId", String.valueOf(newQ.getQuote1Id())); 
        //System.out.println("quoteViewId="+newQ.getQuote1Id());
        //place client into cookie; this will be used later in wizard
        response.addCookie(StandardCode.getInstance().setCookie("projectAddClientId", String.valueOf(c.getClientId())));
        request.setAttribute("projectAddClientId", String.valueOf(c.getClientId()));
        
        //clear project pre file list
        request.getSession(false).setAttribute("fileLocations", null);
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
