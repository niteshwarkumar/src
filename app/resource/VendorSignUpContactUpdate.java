/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.resource;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

/**
 *
 * @author niteshwar
 */
public class VendorSignUpContactUpdate  extends Action {

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
        System.err.println("LoginUserAction @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

	// Extract attributes we will need
	MessageResources messages = getResources(request);

	// save errors
	ActionMessages errors = new ActionMessages();

        //the values the user enter in the browser
       Resource r = new Resource(); 
       r.setEnteredById("external");
       r.setEnteredByTS(new Date());
////////////////////////////////////////////////////////////////////////Contact Info
        String firstName = (String) request.getParameter("firstName");
        String lastName = (String) request.getParameter("lastName");        
        String legalName = (String) request.getParameter("legalName");//column not in db
        String website = (String) request.getParameter("website");
        String email = (String) request.getParameter("email");
        String secEmail = (String) request.getParameter("secEmail");
        String phoneHome = (String) request.getParameter("phoneHome");
        String phoneMobile = (String) request.getParameter("phoneMobile");
        String skype = (String) request.getParameter("skype");
        String address1 = (String) request.getParameter("address1");
        String address2 = (String) request.getParameter("address2");
        String city = (String) request.getParameter("city");
        String state = (String) request.getParameter("state");
        String country = (String) request.getParameter("country");
        String zip = (String) request.getParameter("zip");
 //Set Contact Info into resource  
        r.setFirstName(firstName);
        r.setLastName(lastName);
        r.setUrl(website);
        r.setEmail_address1(email);
        r.setEmail_address2(secEmail);
        r.setMain_telephone_numb1(phoneHome);
        r.setCellPhone(phoneMobile);
        r.setSkypeId(skype);
        r.setAddress_1(address1);
        r.setAddress_2(address2);
        r.setCity(city);
        r.setState_prov(state);
        r.setCountry(country);
        r.setZip_postal_code(zip);
        
      
////////////////////////////////////////////////////////////////////////Task
        String linguistic = (String) request.getParameter("linguistic");//column not in db
        String translation = (String) request.getParameter("translation");
        String editing = (String) request.getParameter("editing");
        String TandE = (String) request.getParameter("TandE");
        String proofreading = (String) request.getParameter("proofreading");
        String transcreation = (String) request.getParameter("transcreation");//column not in db
        String postediting = (String) request.getParameter("postediting");
        String interpret = (String) request.getParameter("interpret");
        String dtp = (String) request.getParameter("dtp");
        String fqa = (String) request.getParameter("fqa");
        String expertreview = (String) request.getParameter("expertreview");
        String avproduction = (String) request.getParameter("avproduction");//column not in db
        String other = (String) request.getParameter("other");
        
        if("on".equalsIgnoreCase(other))
            r.setOther(true);
        if("on".equalsIgnoreCase(translation))r.setTranslator(true);
        if("on".equalsIgnoreCase(editing))r.setEditor(true);
        if("on".equalsIgnoreCase(TandE))r.setTne(true);
        if("on".equalsIgnoreCase(proofreading))r.setProofreader(true);
        if("on".equalsIgnoreCase(postediting))r.setPostEditing(true);
        if("on".equalsIgnoreCase(interpret))r.setInterpreting(true);
        if("on".equalsIgnoreCase(dtp))r.setDtp(true);
        if("on".equalsIgnoreCase(fqa))r.setFqa(true);
        if("on".equalsIgnoreCase(expertreview))r.setExpert(true);
        
        ResourceService.getInstance().addResource(r);
        
         
        

        
//Update Resource
        
   

        
          return (mapping.findForward("Success"));
         
        // return mapping.findForward("");

    }

}
