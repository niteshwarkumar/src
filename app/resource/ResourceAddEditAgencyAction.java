//ResourceAddEditAgencyAction.java gets new team info
//from a form.  It then adds the new values for
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


public final class ResourceAddEditAgencyAction extends Action {


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
        DynaValidatorForm ra = (DynaValidatorForm) form;
        
        Resource r = (Resource) ra.get("resourceNew");
        
        //this is an agency
        r.setAgency(true);
        


       if(!"".equals(request.getParameter("resourceNew.translator") ) && request.getParameter("resourceNew.translator")!=null ) {
            r.setTranslator(true);
        }
        else {
            r.setTranslator(false);
        }
        if(!"".equals(request.getParameter("resourceNew.editor")) && request.getParameter("resourceNew.editor")!=null  ) {
            r.setEditor(true);
        }
        else {
            r.setEditor(false);
        }
        if(!"".equals(request.getParameter("resourceNew.proofreader")) && request.getParameter("resourceNew.proofreader")!=null ) {
            r.setProofreader(true);
        }
        else {
            r.setProofreader(false);
        }
         if(!"".equals(request.getParameter("resourceNew.evaluator")) && request.getParameter("resourceNew.evaluator")!=null ) {
            r.setEvaluator(true);
        }
        else {
            r.setEvaluator(false);
        }

        if(!"".equals(request.getParameter("resourceNew.other")) && request.getParameter("resourceNew.other")!=null ) {
            r.setOther(true);
        }
        else {
            r.setOther(false);
        }

        if(!"".equals(request.getParameter("resourceNew.consultant")) && request.getParameter("resourceNew.consultant")!=null ) {
            r.setConsultant(true);
        }
        else {
            r.setConsultant(false);
        }

        if(!"".equals(request.getParameter("resourceNew.partner")) && request.getParameter("resourceNew.partner")!=null ) {
            r.setPartner(true);
        }
        else {
            r.setPartner(false);
        }

        if(!"".equals(request.getParameter("resourceNew.engineering")) && request.getParameter("resourceNew.engineering")!=null ) {
            r.setEngineering(true);
        }
        else {
            r.setEngineering(false);
        }

        if(!"".equals(request.getParameter("resourceNew.businesssuport")) && request.getParameter("resourceNew.businesssuport")!=null ) {
            r.setBusinesssuport(true);
        }
        else {
            r.setBusinesssuport(false);
        }

        if(!"".equals(request.getParameter("resourceNew.fqa")) && request.getParameter("resourceNew.fqa")!=null ) {
            r.setFqa(true);
        }
        else {
            r.setFqa(false);
        }

        if(!"".equals(request.getParameter("resourceNew.informationTechnology")) && request.getParameter("resourceNew.informationTechnology")!=null ) {
            r.setInformationTechnology(true);
        }
        else {
            r.setInformationTechnology(false);
        }

        if(!"".equals(request.getParameter("resourceNew.humanResource")) && request.getParameter("resourceNew.humanResource")!=null ) {
            r.setHumanResource(true);
        }
        else {
            r.setHumanResource(false);
        }

        if(!"".equals(request.getParameter("resourceNew.office")) && request.getParameter("resourceNew.office")!=null ) {
            r.setOffice(true);
        }
        else {
            r.setOffice(false);
        }

        if(!"".equals(request.getParameter("resourceNew.sales")) && request.getParameter("resourceNew.sales")!=null ) {
            r.setSales(true);
        }
        else {
            r.setSales(false);
        }

        if(!"".equals(request.getParameter("resourceNew.accounting")) && request.getParameter("resourceNew.accounting")!=null ) {
            r.setAccounting(true);
        }
        else {
            r.setAccounting(false);
        }

        if(!"".equals(request.getParameter("resourceNew.bsdOther")) && request.getParameter("resourceNew.bsdOther")!=null ) {
            r.setBsdOther(true);
        }
        else {
            r.setBsdOther(false);
        }

        if(!"".equals(request.getParameter("resourceNew.prodOther")) && request.getParameter("resourceNew.prodOther")!=null ) {
            r.setProdOther(true);
        }
        else {
            r.setProdOther(false);
        }


        if(request.getParameter("resourceNew.resourceFromForm.localizer") != null) {
            r.setLocalizer(true);
        }
        else {
            r.setLocalizer(false);
        }
        if(!"".equals(request.getParameter("resourceNew.dtp")) && request.getParameter("resourceNew.dtp")!=null ) {
            r.setDtp(true);
        }
        else {
            r.setDtp(false);
        }
        if(!"".equals(request.getParameter("resourceNew.icr")) && request.getParameter("resourceNew.icr")!=null ) {
            r.setIcr(true);
        }
        else {
            r.setIcr(false);
        }

        if(request.getParameter("resourceNew.confiAgreement") != null) {
            r.setConfiAgreement(true);
        }
        else {
            r.setConfiAgreement(false);
        }

        if(!"".equals(request.getParameter("resourceNew.interpreting")) && request.getParameter("resourceNew.interpreting")!=null ) {
            r.setInterpreting(true);
        }
        else {
            r.setInterpreting(false);
        }

        if(!"".equals(request.getParameter("resourceNew.expert")) && request.getParameter("resourceNew.expert")!=null ) {
            r.setExpert(true);
        }
        else {
            r.setExpert(false);
        }

        if(!"".equals(request.getParameter("resourceNew.quality")) && request.getParameter("resourceNew.quality")!=null ) {
            r.setQuality(true);
        }
        else {
            r.setQuality(false);
        }
        
        if(!"".equals(request.getParameter("resourceNew.tne")) && request.getParameter("resourceNew.tne")!=null ) {
            r.setTne(true);
        }
        else {
            r.setTne(false);
        }

        
        String msaSentNew = (String) ra.get("msaSentNew");
        String msaReceivedNew = (String) ra.get("msaReceivedNew");
        
        if(msaSentNew.length() > 0) { //if present
            r.setMsaSent(DateService.getInstance().convertDate(msaSentNew).getTime());
        }
        if(msaReceivedNew.length() > 0) { //if present
            r.setMsaReceived(DateService.getInstance().convertDate(msaReceivedNew).getTime());
        }
         
        if(request.getSession(false).getAttribute("username")!=null){
             r.setEnteredById((String)request.getSession(false).getAttribute("username"));
            
        }else{
            r.setEnteredById("N/A");
        }
        r.setEnteredByTS(new Date());
        
        //set new values to db
        Integer resourceViewId = ResourceService.getInstance().addResource(r);
        
        //set new record for display in view menu
        String total = "1";
        Integer[] resourceSearchResultIds = new Integer[1];
        resourceSearchResultIds[0] = new Integer(0);
        resourceSearchResultIds[0] = resourceViewId;
        //set Ids into session for viewing
        request.getSession(false).setAttribute("resourceSearchResultIds", resourceSearchResultIds);
            
        //set current record and total records into session and cookie
        request.getSession(false).setAttribute("resourceRecordTotal", String.valueOf(new Integer(total)));
        response.addCookie(StandardCode.getInstance().setCookie("resourceRecordTotal", String.valueOf(new Integer(total))));
        
        //inform the general tab to display this record
        request.setAttribute("resourceViewId", String.valueOf(resourceViewId));
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
