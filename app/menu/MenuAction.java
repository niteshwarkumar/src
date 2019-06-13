package app.menu;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.validator.DynaValidatorForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleException;
import java.util.Date;
import java.text.*;
import org.apache.struts.util.MessageResources;
import org.apache.commons.beanutils.PropertyUtils;
import javax.servlet.http.Cookie;
import app.workspace.*;
import app.security.*;


public final class MenuAction extends Action {

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
	
        String move = (String) request.getParameter("move");              
	
	// Report any errors we have discovered back to the original form
	if (!errors.isEmpty()) {
	    saveMessages(request, errors);
            return (mapping.getInputForward());
	}
        
        //where to forward
        String forward = new String();
        
        //used for sub menu display
        String[] colors = new String[12];
        String[] links = new String[6];
        String[] displayText = new String[6];
    
        if(move.equals("client")) {
            colors[0] = "#007FFF";
            colors[1] = "#B0C4DE";
            colors[2] = "#B0C4DE";
            colors[3] = "#B0C4DE";
            colors[4] = "#B0C4DE";
            colors[5] = "#B0C4DE";
            colors[6] = "#007FFF";
            colors[7] = "#007FFF";
            colors[8] = "#B0C4DE";
            colors[9] = "#B0C4DE";
            colors[10] = "#B0C4DE";
            colors[11] = "#B0C4DE";
            
            links[0] = "/app/menu.do?move=clientSearch";
            links[1] = "/app/menu.do?move=clientAdd";
            links[2] = "";
            links[3] = "";
            links[4] = "";
            links[5] = "";
            
            displayText[0] = "Search";
            displayText[1] = "Add";
            displayText[2] = "";
            displayText[3] = "";
            displayText[4] = "";
            displayText[5] = "";
            
            forward = "MainSub";            
        }
        else if(move.equals("clientSearch")) {
            colors[0] = "#007FFF";
            colors[1] = "#B0C4DE";
            colors[2] = "#B0C4DE";
            colors[3] = "#B0C4DE";
            colors[4] = "#B0C4DE";
            colors[5] = "#B0C4DE";
            colors[6] = "#007FFF";
            colors[7] = "#007FFF";
            colors[8] = "#B0C4DE";
            colors[9] = "#B0C4DE";
            colors[10] = "#B0C4DE";
            colors[11] = "#B0C4DE";
            
            links[0] = "/app/menu.do?move=clientSearch";
            links[1] = "/app/menu.do?move=clientAdd";
            links[2] = "";
            links[3] = "";
            links[4] = "";
            links[5] = "";
            
            displayText[0] = "Search";
            displayText[1] = "Add";
            displayText[2] = "";
            displayText[3] = "";
            displayText[4] = "";
            displayText[5] = "";
            
            forward = "ClientSearch";       
        }
        else if(move.equals("clientSearchAction")) {
            colors[0] = "#007FFF";
            colors[1] = "#B0C4DE";
            colors[2] = "#B0C4DE";
            colors[3] = "#B0C4DE";
            colors[4] = "#B0C4DE";
            colors[5] = "#B0C4DE";
            colors[6] = "#007FFF";
            colors[7] = "#007FFF";
            colors[8] = "#B0C4DE";
            colors[9] = "#B0C4DE";
            colors[10] = "#B0C4DE";
            colors[11] = "#B0C4DE";
            
            links[0] = "/app/menu.do?move=clientSearch";
            links[1] = "/app/menu.do?move=clientAdd";
            links[2] = "";
            links[3] = "";
            links[4] = "";
            links[5] = "";
            
            displayText[0] = "Search";
            displayText[1] = "Add";
            displayText[2] = "";
            displayText[3] = "";
            displayText[4] = "";
            displayText[5] = "";
            
            forward = "ClientSearchAction";       
        }
        else if(move.equals("clientEdit")) {
            colors[0] = "#007FFF";
            colors[1] = "#B0C4DE";
            colors[2] = "#B0C4DE";
            colors[3] = "#B0C4DE";
            colors[4] = "#B0C4DE";
            colors[5] = "#B0C4DE";
            colors[6] = "#007FFF";
            colors[7] = "#007FFF";
            colors[8] = "#B0C4DE";
            colors[9] = "#B0C4DE";
            colors[10] = "#B0C4DE";
            colors[11] = "#B0C4DE";
            
            links[0] = "/app/menu.do?move=clientSearch";
            links[1] = "/app/menu.do?move=clientAdd";
            links[2] = "";
            links[3] = "";
            links[4] = "";
            links[5] = "";
            
            displayText[0] = "Search";
            displayText[1] = "Add";
            displayText[2] = "";
            displayText[3] = "";
            displayText[4] = "";
            displayText[5] = "";
            
            forward = "ClientEdit";       
        }
         else if(move.equals("clientContactEdit")) {
            colors[0] = "#007FFF";
            colors[1] = "#B0C4DE";
            colors[2] = "#B0C4DE";
            colors[3] = "#B0C4DE";
            colors[4] = "#B0C4DE";
            colors[5] = "#B0C4DE";
            colors[6] = "#007FFF";
            colors[7] = "#007FFF";
            colors[8] = "#B0C4DE";
            colors[9] = "#B0C4DE";
            colors[10] = "#B0C4DE";
            colors[11] = "#B0C4DE";
            
            links[0] = "/app/menu.do?move=clientSearch";
            links[1] = "/app/menu.do?move=clientAdd";
            links[2] = "";
            links[3] = "";
            links[4] = "";
            links[5] = "";
            
            displayText[0] = "Search";
            displayText[1] = "Add";
            displayText[2] = "";
            displayText[3] = "";
            displayText[4] = "";
            displayText[5] = "";
            
            forward = "ClientContactEdit";       
        }
      
        Workspace w = new Workspace();
        w.setColors(colors);
        w.setLinks(links);
        w.setDisplayText(displayText);
        request.setAttribute("Workspace", w);
        
                 
	// Forward control to the specified success URI
        return (mapping.findForward(forward));

    }

}
