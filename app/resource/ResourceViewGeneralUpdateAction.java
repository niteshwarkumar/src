//ResourceViewGeneralUpdateAction.java gets updated team info
//from a form.  It then uploads the new values for
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


public final class ResourceViewGeneralUpdateAction extends Action {


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
         request.setAttribute("lastOpennedTab",request.getParameter("lastOpennedTab"));
             
        //values for update from form; change what is stored in db to these values
        DynaValidatorForm rvt = (DynaValidatorForm) form;
        
        Resource r = (Resource) rvt.get("resourceFromForm");
        if(request.getParameter("resourceFromForm.doNotUse")!= null){
            r.setDoNotUse(true);
        }else {
            r.setDoNotUse(false);
        }

        if(request.getParameter("resourceFromForm.beingTested")!= null){
            r.setBeingTested(true);
        }else {
            r.setBeingTested(false);
        }

        if(request.getParameter("resourceFromForm.beingTestedBy")!= null){
            r.setBeingTestedBy(request.getParameter("resourceFromForm.beingTestedBy"));
        }else {
            r.setBeingTestedBy("");
        }

//        if(request.getParameter("resourceFromForm:riskrating")!= null){
//            r.setRiskrating(request.getParameter("resourceFromForm:riskrating"));
//        }else {
//            r.setRiskrating("");
//        }
//
        
        if(request.getParameter("resourceFromForm.agency") != null) {
            r.setAgency(true);
            r.setFirstName("");
            r.setLastName("");
            r.setSingleCompanyName("");
        }
        else {
            r.setAgency(false);
            r.setCompanyName("Freelance");
        }
        
        if(!"".equals(request.getParameter("translator") )) {
            r.setTranslator(true);
        }
        else {
            r.setTranslator(false);
        }
        if(!"".equals(request.getParameter("editor")) ) {
            r.setEditor(true);
        }
        else {
            r.setEditor(false);
        }
        if(!"".equals(request.getParameter("proofreader"))) {
            r.setProofreader(true);
        }
        else {
            r.setProofreader(false);
        }
         if(!"".equals(request.getParameter("evaluator"))) {
            r.setEvaluator(true);
        }
        else {
            r.setEvaluator(false);
        }
        
        if(!"".equals(request.getParameter("other"))) {
            r.setOther(true);
        }
        else {
            r.setOther(false);
        }
          
        if(!"".equals(request.getParameter("consultant"))) {
            r.setConsultant(true);
        }
        else {
            r.setConsultant(false);
        }
        
        if(!"".equals(request.getParameter("partner"))) {
            r.setPartner(true);
        }
        else {
            r.setPartner(false);
        }
        
        if(!"".equals(request.getParameter("tne"))) {
            r.setTne(true);
        }
        else {
            r.setTne(false);
        }
        
        if(!"".equals(request.getParameter("engineering"))) {
            r.setEngineering(true);
        }
        else {
            r.setEngineering(false);
        }
         
        if(!"".equals(request.getParameter("businesssuport"))) {
            r.setBusinesssuport(true);
        }
        else {
            r.setBusinesssuport(false);
        }

        if(!"".equals(request.getParameter("fqa"))) {
            r.setFqa(true);
        }
        else {
            r.setFqa(false);
        }

        if(!"".equals(request.getParameter("informationTechnology"))) {
            r.setInformationTechnology(true);
        }
        else {
            r.setInformationTechnology(false);
        }

        if(!"".equals(request.getParameter("humanResource"))) {
            r.setHumanResource(true);
        }
        else {
            r.setHumanResource(false);
        }

        if(!"".equals(request.getParameter("office"))) {
            r.setOffice(true);
        }
        else {
            r.setOffice(false);
        }

        if(!"".equals(request.getParameter("sales"))) {
            r.setSales(true);
        }
        else {
            r.setSales(false);
        }

        if(!"".equals(request.getParameter("accounting"))) {
            r.setAccounting(true);
        }
        else {
            r.setAccounting(false);
        }

        if(!"".equals(request.getParameter("bsdOther"))) {
            r.setBsdOther(true);
        }
        else {
            r.setBsdOther(false);
        }

        if(!"".equals(request.getParameter("prodOther"))) {
            r.setProdOther(true);
        }
        else {
            r.setProdOther(false);
        }
        
        if(!"".equals(request.getParameter("postEditing"))) {
            r.setPostEditing(true);
        }
        else {
            r.setPostEditing(false);
        }
        
        
        if(request.getParameter("resourceFromForm.localizer") != null) {
            r.setLocalizer(true);
        }
        else {
            r.setLocalizer(false);
        }
        if(!"".equals(request.getParameter("dtp"))) {
            r.setDtp(true);
        }
        else {
            r.setDtp(false);
        }
        if(!"".equals(request.getParameter("icr"))) {
            r.setIcr(true);
        }
        else {
            r.setIcr(false);
        }
       
        if(request.getParameter("resourceFromForm.confiAgreement") != null) {
            r.setConfiAgreement(true);
        }
        else {
            r.setConfiAgreement(false);
        }
        
        if(!"".equals(request.getParameter("interpreting"))) {
            r.setInterpreting(true);
        }
        else {
            r.setInterpreting(false);
        }
        
        if(!"".equals(request.getParameter("expert"))) {
            r.setExpert(true);
        }
        else {
            r.setExpert(false);
        }
        
        if(!"".equals(request.getParameter("quality"))) {
            r.setQuality(true);
        }
        else {
            r.setQuality(false);
        }
//        if(!"".equals((String) rvt.get("confiAgreement"))){
//            //System.out.println("onononononononono");}else{
//            //System.out.println("ofofofofofofofofof");
//            }
        String msaSent = (String) rvt.get("msaSent");
        String msaReceived = (String) rvt.get("msaReceived");
        
        if(msaSent!=null && msaSent.length() > 0) { //if present
            r.setMsaSent(DateService.getInstance().convertDate(msaSent).getTime());
        }
        if(msaReceived!=null &&msaReceived.length() > 0) { //if present
            r.setMsaReceived(DateService.getInstance().convertDate(msaReceived).getTime());
        }
        
        

        if(request.getSession(false).getAttribute("username")!=null){
                r.setLastModifiedById((String)request.getSession(false).getAttribute("username")); 
                r.setLastModifiedByTS(new Date());
        }
        r.setRiskrating(request.getParameter("resourceFromForm.riskrating"));

          r.setOtherText(request.getParameter("resourceFromForm.otherText"));
            r.setProdOtherText(request.getParameter("resourceFromForm.prodOtherText"));
              r.setBsdOtherText(request.getParameter("resourceFromForm.bsdOtherText"));
//              r.setPostEditing(request.getParameter("resourceFromForm.postEditing"));
       
        //set updated values to db
        ResourceService.getInstance().updateResource(r);
        
         //get the updated list of unavail events for a resource
         Unavail[] unavails = (Unavail[]) rvt.get("unavails");
        
        //update the away events
        for(int i = 0; i < unavails.length; i++) {
            String unavailStart = request.getParameter("unavailStart" + String.valueOf(i));
            if(unavailStart.length() >= 1) {
                unavails[i].setStartDate(DateService.getInstance().convertDate(unavailStart).getTime());
            }
            String unavailEnd = request.getParameter("unavailEnd" + String.valueOf(i));
            if(unavailEnd.length() >= 1) {
                unavails[i].setEndDate(DateService.getInstance().convertDate(unavailEnd).getTime());
            }
            ResourceService.getInstance().updateUnavail(unavails[i]);
        }

        
        //place resource id into request for display
        request.setAttribute("resource", r);
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
