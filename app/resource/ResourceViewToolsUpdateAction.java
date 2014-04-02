//HrViewGeneralUpdateAction.java gets updated hr info
//from a form.  It then uploads the new values for
//this employee to the db

package app.resource;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleException;
import org.apache.struts.util.MessageResources;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.*;
import java.io.*;
import java.util.*;
import app.user.*;
import app.client.*;
import app.db.*;
import app.workspace.*;
import app.security.*;


public final class ResourceViewToolsUpdateAction extends Action {


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
                      
        //values for update from form; change what is stored in db to these values
        DynaValidatorForm rvt = (DynaValidatorForm) form;
        
        Resource r = (Resource) rvt.get("resourceFromTools");
       
        
        //treat checkboxes specially (because they only get submitted if checked)
        if(request.getParameter("resourceFromTools.usesTrados") != null) {
            r.setUsesTrados(true);
        }
        else {
            r.setUsesTrados(false);
        }
        if(request.getParameter("resourceFromTools.usesSdlx") != null) {
            r.setUsesSdlx(true);
        }
        else {
            r.setUsesSdlx(false);
        }
        if(request.getParameter("resourceFromTools.usesDejavu") != null) {
            r.setUsesDejavu(true);
        }
        else {
            r.setUsesDejavu(false);
        }
        if(request.getParameter("resourceFromTools.usesTransit") != null) {
            r.setUsesTransit(true);
        }
        else {
            r.setUsesTransit(false);
        }
        if(request.getParameter("resourceFromTools.usesCatalyst") != null) {
            r.setUsesCatalyst(true);
        }
        else {
            r.setUsesCatalyst(false);
        }
        if(request.getParameter("resourceFromTools.usesOther") != null) {
            r.setUsesOther(true);
        }
        else {
            r.setUsesOther(false);
        }
        
        if(request.getSession(false).getAttribute("username")!=null){
                r.setLastModifiedById((String)request.getSession(false).getAttribute("username")); 
                r.setLastModifiedByTS(new Date());
        }
        //set updated values to db
        ResourceService.getInstance().updateResource(r);
        
        MALinguistic maLin = (MALinguistic) rvt.get("linMA");
        //treat checkboxes specially (because they only get submitted if checked)
        if(request.getParameter("linMA.nativeSpeaker") == null) {
            maLin.setNativeSpeaker(null);
        } 
        if(request.getParameter("linMA.professionalLinguist") == null) {
            maLin.setProfessionalLinguist(null);
        }
        if(request.getParameter("linMA.professionalLinguistFT") == null) {
            maLin.setProfessionalLinguistFT(null);
        }
        if(request.getParameter("linMA.professionalLinguistPT") == null) {
            maLin.setProfessionalLinguistPT(null);
        }
        if(request.getParameter("linMA.nativeLocation") == null) {
            maLin.setNativeLocation(null);
        }
        
        if(request.getParameter("linMA.medicalExpirience") == null) {
            maLin.setMedicalExpirience(null);
        }
        if(request.getParameter("linMA.softwareExpirience") == null) {
            maLin.setSoftwareExpirience(null);
        }
        if(request.getParameter("linMA.techExpirience") == null) {
            maLin.setTechExpirience(null);
        }
        if(request.getParameter("linMA.legalExpirience") == null) {
            maLin.setLegalExpirience(null);
        }
        if(request.getParameter("linMA.marketingExpirience") == null) {
            maLin.setMarketingExpirience(null);
        }
        if(request.getParameter("linMA.iso9001") == null) {
            maLin.setIso9001(null);
        }
        if(request.getParameter("linMA.iso13485") == null) {
            maLin.setIso13485(null);
        }
        if(request.getParameter("linMA.iso14971") == null) {
            maLin.setIso14971(null);
        }
        if(request.getParameter("linMA.industry1") == null) {
            maLin.setIndustry1(null);
        }
        if(request.getParameter("linMA.industry2") == null) {
            maLin.setIndustry2(null);
        }
        
        
                
                
        ResourceService.getInstance().updateMALinguistic(maLin);
        
        
        MADtpEng maDtp = (MADtpEng) rvt.get("dtpMA");
        if(request.getParameter("dtpMA.industry1") == null) {
            maDtp.setIndustry1(null);
        }
        if(request.getParameter("dtpMA.industry2") == null) {
            maDtp.setIndustry2(null);
        }
        if(request.getParameter("dtpMA.iso1") == null) {
            maDtp.setIso1(null);
        }
        if(request.getParameter("dtpMA.iso2") == null) {
            maDtp.setIso2(null);
        }
        if(request.getParameter("dtpMA.iso3") == null) {
            maDtp.setIso3(null);
        }
        
        
        ResourceService.getInstance().updateMADtpEng(maDtp);
        
         MAExpert maExpert = (MAExpert) rvt.get("expertMA");
        ResourceService.getInstance().updateMAExpert(maExpert);
        
        
        
        
         MAOther maOther = (MAOther) rvt.get("otherMA");
         if(request.getParameter("otherMA.deductionReason1") == null) {
            maOther.setDeductionReason1(null);
        }
         
         if(request.getParameter("otherMA.generalTandC") == null) {
            maOther.setGeneralTandC(null);
        }
         
         if(request.getParameter("otherMA.paymentPolicy") == null) {
            maOther.setPaymentPolicy(null);
        }
         
           if(request.getParameter("otherMA.tradosPolicy") == null) {
            maOther.setTradosPolicy(null);
        }
          if(request.getParameter("otherMA.paymentCheck") == null) {
            maOther.setDeductionReason1(null);
        }
         
          if(request.getParameter("otherMA.paymentMail") == null) {
            maOther.setPaymentMail(null);
        }
         
          if(request.getParameter("otherMA.paymentRegistered") == null) {
            maOther.setPaymentRegistered(null);
        }
          if(request.getParameter("otherMA.wireTransfer") == null) {
            maOther.setWireTransfer(null);
        }
         
        ResourceService.getInstance().updateMAOther(maOther);
        
        
        //process other tools update
        ResourceTool[] resourceTools = (ResourceTool[]) rvt.get("resourceTools");
        for(int i = 0; i < resourceTools.length; i++) {
            ResourceService.getInstance().updateResourceTool(resourceTools[i]);
        }     
        
               
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
