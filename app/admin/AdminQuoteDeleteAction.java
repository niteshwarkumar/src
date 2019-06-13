//AdminQuoteDeleteAction.java deletes one quote from the db
//and the quote's one-to-many mappings

package app.admin;

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
import app.project.*;
import app.quote.*;
import app.standardCode.*;
import app.security.*;


public final class AdminQuoteDeleteAction extends Action {


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
    @Override
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
	     
        //PRIVS check that admin user is viewing this page 
        if(!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "admin")) {
            return (mapping.findForward("accessDenied"));
        }//END PRIVS check that admin user is viewing this page
        
        //get object to delete
        String id = request.getParameter("id");
        Quote1 obj = QuoteService.getInstance().getSingleQuote(id);
        List cq=QuoteService.getInstance().getClient_Quote(obj.getQuote1Id());
      
        //delete one-to-many mappings first, then delete main (or top) object
        AdminService.getInstance().deleteCollection(obj.getFiles());
        Project pLazy = ProjectService.getInstance().getSingleProject(obj.getProject().getProjectId());
        
        AdminService.getInstance().deleteCollection(pLazy.getInspections());
       
        try{
        //delete sources, targets, tasks
        for(Iterator iter = obj.getSourceDocs().iterator(); iter.hasNext();) {
            SourceDoc sd = (SourceDoc) iter.next();
            for(Iterator iterT = sd.getTargetDocs().iterator(); iterT.hasNext();) {
                TargetDoc td = (TargetDoc) iterT.next();
                AdminService.getInstance().deleteCollection(td.getLinTasks());
                AdminService.getInstance().deleteCollection(td.getEngTasks());
                AdminService.getInstance().deleteCollection(td.getDtpTasks());
                AdminService.getInstance().deleteCollection(td.getOthTasks());
            }
            AdminService.getInstance().deleteCollection(sd.getTargetDocs());        
        }
        AdminService.getInstance().deleteCollection(obj.getSourceDocs());
        }catch(Exception e){
            try{
        List sdDocs = QuoteService.getInstance().getSourceLang1(obj);
        for(Iterator iter = sdDocs.iterator(); iter.hasNext();) {
            SourceDoc sd = (SourceDoc) iter.next();
            for(Iterator iterT = sd.getTargetDocs().iterator(); iterT.hasNext();) {
                TargetDoc td = (TargetDoc) iterT.next();
                AdminService.getInstance().deleteCollection(td.getLinTasks());
                AdminService.getInstance().deleteCollection(td.getEngTasks());
                AdminService.getInstance().deleteCollection(td.getDtpTasks());
                AdminService.getInstance().deleteCollection(td.getOthTasks());
            }
            AdminService.getInstance().deleteCollection(sd.getTargetDocs());        
        }
        AdminService.getInstance().deleteCollection(obj.getSourceDocs());
            }catch(Exception e1){}
        }
        try{
        for(int i=0;i<cq.size();i++){
            Client_Quote q1=(Client_Quote) cq.get(i);
            QuoteService.getInstance().deleteClient_Quote(q1);
        }}catch(Exception e){}
        //QuoteService.getInstance().deleteClient_Quote(null);
        
       // ProjectService.getInstance().deleteProject(pLazy);
       // File f=QuoteService.getInstance().getSingleFile(object.getFiles().size())
      //  try{
     //   List fileList=QuoteService.getInstance().getFileListByQuote(obj.getQuote1Id());
      //  for(int i=0;i< fileList.size();i++)
     //   {
     //    File f= (File) fileList.get(i);
     //    QuoteService.getInstance().deleteFile(f);

    //    }}catch(Exception e){}

        
        QuoteService.getInstance().deleteQuote(obj);
        
        //ALEKS 03-15-2006:  We also have to reset this quoteViewId from the session  
        response.addCookie(StandardCode.getInstance().setCookie("quoteViewId", null ));
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
