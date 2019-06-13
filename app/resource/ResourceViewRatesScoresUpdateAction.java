//ResourceViewRatesScoresUpdateAction.java gets updated resource info
//from a form.  It then uploads the new values (rate-score related) for
//this resource to the db

package app.resource;

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
import java.util.*;
import app.security.*;
import app.standardCode.*;

public final class ResourceViewRatesScoresUpdateAction extends Action {


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
        
	//START get id of current resource from either request, attribute, or cookie 
        //id of resource from request
	String resourceId = null;
	resourceId = request.getParameter("resourceViewId");
        
        //check attribute in request
        if(resourceId == null) {
            resourceId = (String) request.getAttribute("resourceViewId");
        }
        
        //id of resource from cookie
        if(resourceId == null) {            
            resourceId = StandardCode.getInstance().getCookie("resourceViewId", request.getCookies());
        }

        //default resource to first if not in request or cookie
        if(resourceId == null) {
                List results = ResourceService.getInstance().getResourceList();
                Resource first = (Resource) results.get(0);
                resourceId = String.valueOf(first.getResourceId());
            }            
        
        Integer id = Integer.valueOf(resourceId);
        
        //END get id of current resource from either request, attribute, or cookie               
        
        //get resource to edit
        Resource r = ResourceService.getInstance().getSingleResource(id); 
        
        //load resource info for editing
        DynaValidatorForm rvrs = (DynaValidatorForm) form;
        
        RateScoreLanguage[] ratescorelanguages = (RateScoreLanguage[]) rvrs.get("rateScoreLanguages");
        RateScoreDtp[] rateScoreDtps = (RateScoreDtp[]) rvrs.get("rateScoreDtps");
        
        
        //update each rateSourceLanguage
        for(int i = 0; i < ratescorelanguages.length; i++) {
          
            
            ResourceService.getInstance().updateRateScoreLanguage(ratescorelanguages[i]);
            //System.out.println("rate score language..........................."+ResourceService.getInstance().updateRateScoreLanguage(ratescorelanguages[i]));
        }
        
        //update each rateSourceDtp
        for(int i = 0; i < rateScoreDtps.length; i++) {
            //rateScoreDtps[i].setAssesmentSent(request.getParameter("rateScoreDtps["+i+"].assesmentSent"));
           // //System.out.println("getApplication="+rateScoreDtps[i].getApplication());
            ResourceService.getInstance().updateRateScoreDtp(rateScoreDtps[i]);
        }
            r.setCurrency(request.getParameter("toggleCurrencies"));
            if("LIN".equals(request.getParameter("myTab"))){
            r.setLinRatesNotes(request.getParameter("linRatesNote"));
            
            r.setLinDiscountDescription1(request.getParameter("linDiscountDescription1"));
            r.setLinDiscountName1(request.getParameter("linDiscountName1"));
            r.setLinDiscountDescription2(request.getParameter("linDiscountDescription2"));
            r.setLinDiscountName2(request.getParameter("linDiscountName2"));
            r.setLinDiscountDescription3(request.getParameter("linDiscountDescription3"));
            r.setLinDiscountName3(request.getParameter("linDiscountName3"));
            r.setLinDiscountDescription4(request.getParameter("linDiscountDescription4"));
            r.setLinDiscountName4(request.getParameter("linDiscountName4"));
            r.setScale100(request.getParameter("scale100"));
            r.setScale75(request.getParameter("scale75"));
            r.setScale85(request.getParameter("scale85"));
            r.setScale8599(request.getParameter("scale8599"));
            r.setScale95(request.getParameter("scale95"));
            r.setScaleContext(request.getParameter("scaleContext"));
            r.setScalePerfect(request.getParameter("scalePerfect"));
            r.setScaleNew(request.getParameter("scaleNew"));
            r.setScaleNew4(request.getParameter("scaleNew"));
            r.setScaleRep(request.getParameter("scaleRep"));
            try{if(request.getParameter("defaultRate").equalsIgnoreCase("on"))r.setDefaultRate(true);
            else r.setDefaultRate(false);
            }catch(Exception e){r.setDefaultRate(false);}
             ResourceService.getInstance().updateResource(r);
            
                    
        }else if("DTP".equals(request.getParameter("myTab"))){
            r.setDtpRatesNotes(request.getParameter("dtpRatesNote"));
            
            r.setDtpDiscountDescription1(request.getParameter("dtpDiscountDescription1"));
            r.setDtpDiscountName1(request.getParameter("dtpDiscountName1"));
            r.setDtpDiscountDescription2(request.getParameter("dtpDiscountDescription2"));
            r.setDtpDiscountName2(request.getParameter("dtpDiscountName2"));
            r.setDtpDiscountDescription3(request.getParameter("dtpDiscountDescription3"));
            r.setDtpDiscountName3(request.getParameter("dtpDiscountName3"));
            r.setDtpDiscountDescription4(request.getParameter("dtpDiscountDescription4"));
            r.setDtpDiscountName4(request.getParameter("dtpDiscountName4"));
             ResourceService.getInstance().updateResource(r);
            
        }else if("ISA".equals(request.getParameter("myTab"))){
             r.setLinISANotes(request.getParameter("linIsaNote"));
             r.setDtpISANotes(request.getParameter("dtpIsaNote"));
             
             ResourceService.getInstance().updateResource(r);
        }
       
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
