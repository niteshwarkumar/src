//ProjectViewTeamBindFromSearchAction.java binds a resource to
//a task for one project (this came from team search)

package app.project;

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
import app.security.*;
import app.resource.*;
import app.client.*;


public final class ProjectViewTeamBindFromSearchAction extends Action {


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
       
        //get resource and task to bind
        String resourceId = request.getParameter("resourceId");
        String linId = request.getParameter("linId");
        String engId = request.getParameter("engId");
        String dtpId = request.getParameter("dtpId");
        String othId = request.getParameter("othId");
        
        
        
        //bind the task to the resource (team member)
        if(linId != null) {
            
            Resource r = ResourceService.getInstance().getSingleResource(Integer.valueOf(resourceId));
        
        //get this resources language pairs
        Set languagePairs = r.getLanguagePairs();
        
        //the list for display (load all ratescorelanguage entries)
        List ratescorelanguages = new ArrayList();
        for(Iterator iter = languagePairs.iterator(); iter.hasNext();) {
            LanguagePair lp = (LanguagePair) iter.next();
            for(Iterator rateScoreIter = lp.getRateScoreLanguages().iterator(); rateScoreIter.hasNext();) {
                RateScoreLanguage rsl = (RateScoreLanguage) rateScoreIter.next();
                ratescorelanguages.add(rsl);
            }
                
        }
        
        
	//prepare the ratescorelanguages and rateScoreDtps for display
        RateScoreLanguage[] ratescorelanguagesArray = (RateScoreLanguage[]) ratescorelanguages.toArray(new RateScoreLanguage[0]);
        RateScoreDtp[] rateScoreDtpsArray = (RateScoreDtp[]) r.getRateScoreDtps().toArray(new RateScoreDtp[0]);
        
            LinTask t = ProjectService.getInstance().getSingleLinTask(Integer.valueOf(linId));
            t.setPersonName(resourceId);
            t.setInternalCurrency(r.getCurrency());
            //t.setCurrency(r.getCurrency());
            //t.setCurrencyFee(r.getCurrency());
            
            //Map Fee amount from Rate/Score tab
            Client c = t.getTargetDoc().getSourceDoc().getProject().getCompany();
            String clientSpec = c.getIndustry().getDescription();
            ////System.out.println("clientSpec="+clientSpec);
            ////System.out.println("t.getTaskName()="+t.getTaskName());
            ////System.out.println("t.getTargetLanguage()="+t.getTargetLanguage());
            ////System.out.println("t.getSourceLanguage()="+t.getSourceLanguage());
            
            
            for(int i=0; i<ratescorelanguagesArray.length;i++){
                
               // //System.out.println("ratescorelanguagesArray.getSource():"+ratescorelanguagesArray[i].getSource());
               // //System.out.println("ratescorelanguagesArray.getTarget():"+ratescorelanguagesArray[i].getTarget());
                
                if(ratescorelanguagesArray[i].getSource().equals(t.getSourceLanguage()) && ratescorelanguagesArray[i].getTarget().equals(t.getTargetLanguage())){
                   // //System.out.println("ratescorelanguagesArray[i].getSpecialty()="+ratescorelanguagesArray[i].getSpecialty());


                    if(  
                        ("Medical".equals(ratescorelanguagesArray[i].getSpecialty()) && "Medical, Dental, Pharmaceutical".equals(clientSpec))||
                        ("Software".equals(ratescorelanguagesArray[i].getSpecialty()) && "Computer: Hardware & Software, Localization".equals(clientSpec))||
                        ("Legal/Financial".equals(ratescorelanguagesArray[i].getSpecialty()) && "Business & Finance & Legal: Banking, Commerce, Management".equals(clientSpec))||
                        "Technical".equals(ratescorelanguagesArray[i].getSpecialty()) 
                    ){

                        if("Translation".equalsIgnoreCase(t.getTaskName())){
                            t.setInternalRate(""+ratescorelanguagesArray[i].getT());
                          //  //System.out.println("Setting rate for Translation:"+ratescorelanguagesArray[i].getT());
                             break;
                        }else if("Editing".equalsIgnoreCase(t.getTaskName())){
                            t.setInternalRate(""+ratescorelanguagesArray[i].getE());
                          //  //System.out.println("Setting rate for Editing"+ratescorelanguagesArray[i].getE());
                             break;
                        }else if("Proofreading / Linguistic QA".equalsIgnoreCase(t.getTaskName())){
                            t.setInternalRate(""+ratescorelanguagesArray[i].getP());
                           // //System.out.println("Setting rate for Proofreading:"+ratescorelanguagesArray[i].getP());
                             break;
                        }


                    }
                }
                
            }
                         
            ProjectService.getInstance().updateLinTask(t);
        }
        else if(engId != null) {
            EngTask t = ProjectService.getInstance().getSingleEngTask(Integer.valueOf(engId));
            t.setPersonName(resourceId);
            ProjectService.getInstance().updateEngTask(t);        
        }
        else if(dtpId != null) {
                        
            DtpTask t = ProjectService.getInstance().getSingleDtpTask(Integer.valueOf(dtpId));
            t.setPersonName(resourceId);
            ProjectService.getInstance().updateDtpTask(t);        
        }
        else if(othId != null) {
            OthTask t = ProjectService.getInstance().getSingleOthTask(Integer.valueOf(othId));
            t.setPersonName(resourceId);
            ProjectService.getInstance().updateOthTask(t);        
        }
        
        if(engId != null || othId != null){
          return (mapping.findForward("Eng"));  
        }else if(dtpId != null) {
             return (mapping.findForward("Dtp"));  
        }else{
             return (mapping.findForward("Lin"));  
        }
        // Forward control to the specified success URI
	
    }
  
   

}
