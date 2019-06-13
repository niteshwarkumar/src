//QuoteAdd3Action.java collects values related to 
//the project for this quote from this part of the wizard
//It then adds this info to the db

package app.quote;

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
import app.init.ClientRate;
import app.project.*;
import app.standardCode.*;
import app.security.*;

public final class QuoteAdd3Action extends Action {


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
	
        //values for updating the related project
        DynaValidatorForm qae3 = (DynaValidatorForm) form;
        String product = (String) (qae3.get("product"));  
        String productDescription = (String) (qae3.get("productDescription"));  
        String productUnits = (String) (qae3.get("productUnits"));  
        String projectDescription = (String) (qae3.get("projectDescription"));  
        String projectManager = (String) (qae3.get("projectManager"));  
        String projectRequirements = (String) (qae3.get("projectRequirements"));
        String beforeWorkTurn = (String) (qae3.get("beforeWorkTurn"));
        String beforeWorkTurnUnits = (String) (qae3.get("beforeWorkTurnUnits"));
        String afterWorkTurn = (String) (qae3.get("afterWorkTurn"));
        String afterWorkTurnUnits = (String) (qae3.get("afterWorkTurnUnits"));
        String afterWorkTurnReason = (String) (qae3.get("afterWorkTurnReason"));
        String productFeeUnit = (String) (qae3.get("productFeeUnit"));
        
        //need the project, quote to update project
        String quoteId = StandardCode.getInstance().getCookie("quoteAddId", request.getCookies());
        Quote1 q = QuoteService.getInstance().getSingleQuote(Integer.valueOf(quoteId)); 
        
        Project p = q.getProject();
        
        //update values
        p.setProduct(product);
       // p.setProductDescription(productDescription);
        p.setProductUnits(productUnits);
        p.setProjectDescription(projectDescription);
        p.setPm(projectManager);        
        p.setProjectRequirements(projectRequirements);
        p.setBeforeWorkTurn(beforeWorkTurn);
        p.setBeforeWorkTurnUnits(beforeWorkTurnUnits);
        p.setAfterWorkTurn(afterWorkTurn);
        p.setAfterWorkTurnUnits(afterWorkTurnUnits);
        p.setAfterWorkTurnReason(afterWorkTurnReason);
        p.setFee(productFeeUnit);
        
        //Set PM RATE 
        String clientViewId = StandardCode.getInstance().getCookie("quoteAddClientId", request.getCookies());
        Client c = ClientService.getInstance().getSingleClient(Integer.valueOf(clientViewId));
       // client
                
        ClientLanguagePair[] clp = null;
        if(c!=null){
            if(c.getClientLanguagePairs()!=null){
                clp = (ClientLanguagePair[])c.getClientLanguagePairs().toArray(new ClientLanguagePair[0]);
                
            }
        }
       if(clp!=null){ 
         
        for(int z=0; z<clp.length; z++){      
             
                            if( clp[z].getTask()!=null && clp[z].getTask().equals("PM - Percentage")){                            
                                q.setPmPercent(clp[z].getRate());
                                break;
                            }
                        }
       }
        
        //update project to db
        ProjectService.getInstance().updateProject(p);   
        QuoteService.getInstance().updateQuote(q,(String)request.getSession(false).getAttribute("username"));
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
