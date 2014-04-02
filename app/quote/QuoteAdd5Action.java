//QuoteAdd5Action.java collects the target(s) info
//for one source within one quote

package app.quote;

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
import org.apache.struts.validator.*;
import java.util.*;
import app.user.*;
import app.db.*;
import app.client.*;
import app.project.*;
import app.workspace.*;
import app.standardCode.*;
import app.security.*;

public final class QuoteAdd5Action extends Action {


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
	
        //values for adding a new target Doc
        DynaValidatorForm qae5 = (DynaValidatorForm) form;
        
        String[] targetLanguage = (String[]) (qae5.get("targetLanguageRight"));  
        String linTranslation = (String) (qae5.get("linTranslation")); 
        String linEditing = (String) (qae5.get("linEditing")); 
        String linTrEd = (String) (qae5.get("linTrEd")); 
        String linProofreading = (String) (qae5.get("linProofreading"));
        String linIn = (String) (qae5.get("linIn"));  
        String linOther = (String) (qae5.get("linOther"));  
        String linOtherText = (String) (qae5.get("linOtherText"));  
        String lin0 = (String) (qae5.get("lin0"));  
        String lin1 = (String) (qae5.get("lin1"));  
        String lin2 = (String) (qae5.get("lin2"));  
        String lin3 = (String) (qae5.get("lin3"));  
        String lin4 = (String) (qae5.get("lin4"));  
        String lin5 = (String) (qae5.get("lin5"));  
        String lin6 = (String) (qae5.get("lin6"));  
        String lin7 = (String) (qae5.get("lin7"));  
        String lin8 = (String) (qae5.get("lin8"));  
        String lin9 = (String) (qae5.get("lin9"));  
        String lin10 = (String) (qae5.get("lin10"));  
        String lin11 = (String) (qae5.get("lin11"));  
        String[] linTaskOptions = QuoteService.getInstance().getLinTaskOptions();
        
        //need the source to add the target(s)
        String sourceDocId = StandardCode.getInstance().getCookie("quoteAddSourceDocId", request.getCookies());
        SourceDoc sd = ProjectService.getInstance().getSingleSourceDoc(Integer.valueOf(sourceDocId)); 
        
        String clientViewId = StandardCode.getInstance().getCookie("quoteAddClientId", request.getCookies());
        Client c = ClientService.getInstance().getSingleClient(Integer.valueOf(clientViewId));
        ClientLanguagePair[] clp = null;
        
        if(c!=null){
            if(c.getClientLanguagePairs()!=null){
                clp = (ClientLanguagePair[])c.getClientLanguagePairs().toArray(new ClientLanguagePair[0]);
                
            }
        }
        
        
         
        //for each target, add the new target to db and each target's new linguistic tasks selected from form
        for(int i = 0; i < targetLanguage.length; i++) {
            //target language's new object
            TargetDoc td = new TargetDoc(new HashSet(), new HashSet(), new HashSet(), new HashSet());
            td.setLanguage(targetLanguage[i]);
            
            //link this target Doc to the source Doc; add new target Doc to db
            Integer x = ProjectService.getInstance().linkSourceDocTargetDoc(sd, td); 
            
            Set linTasks = new HashSet(); //list of new linTasks
                                    
            if(lin1.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(targetLanguage[i]);
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[1-1]);
                lt.setOrderNum(new Integer(1));
            
                   //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){
                        
                        
                    
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage()) 
                                && clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){
                                
                                lt.setRate(clp[z].getRate());
                                lt.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
               
                linTasks.add(lt);
            }
                  
            if(lin2.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(targetLanguage[i]);
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[2-1]);
                lt.setOrderNum(new Integer(2));
                
                //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){
                        
                        
                    
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){
                             //   System.out.println("alexxx:assigning clp[z].getRate()= "+clp[z].getRate());
                                lt.setRate(clp[z].getRate());
                                lt.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                
                linTasks.add(lt);
            }
            
            if(lin3.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(targetLanguage[i]);
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[3-1]);
                lt.setOrderNum(new Integer(3));
                
                //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){
                        
                        
                    
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){
                               // System.out.println("alexxx:assigning clp[z].getRate()= "+clp[z].getRate());
                                lt.setRate(clp[z].getRate());
                                lt.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                
                linTasks.add(lt);
            }
            
            if(lin4.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(targetLanguage[i]);
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[4-1]);
                lt.setOrderNum(new Integer(4));
                //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){
                        
                        
                    
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){
                               // System.out.println("alexxx:assigning clp[z].getRate()= "+clp[z].getRate());
                                lt.setRate(clp[z].getRate());
                                lt.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                
                linTasks.add(lt);
            }
                        
            if(lin5.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(targetLanguage[i]);
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[5-1]);
                lt.setOrderNum(new Integer(5));
                //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){
                        
                        
                    
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){
                               // System.out.println("alexxx:assigning clp[z].getRate()= "+clp[z].getRate());
                                lt.setRate(clp[z].getRate());
                                lt.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                linTasks.add(lt);
            }
            
            if(lin6.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(targetLanguage[i]);
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[6-1]);
                lt.setOrderNum(new Integer(6));
                
                //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){
                        
                        
                    
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){
                                //System.out.println("alexxx:assigning clp[z].getRate()= "+clp[z].getRate());
                                lt.setRate(clp[z].getRate());
                                lt.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                
                linTasks.add(lt);
            }
            
            if(lin7.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(targetLanguage[i]);
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[7-1]);
                lt.setOrderNum(new Integer(7));
                //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){
                        
                        
                    
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){
                                //System.out.println("alexxx:assigning clp[z].getRate()= "+clp[z].getRate());
                                lt.setRate(clp[z].getRate());
                                lt.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                
                linTasks.add(lt);
            }
            
            if(lin8.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(targetLanguage[i]);
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[8-1]);
                lt.setOrderNum(new Integer(8));
                
                //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){
                        
                        
                    
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){
                                //System.out.println("alexxx:assigning clp[z].getRate()= "+clp[z].getRate());
                                lt.setRate(clp[z].getRate());
                                lt.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                
                linTasks.add(lt);
            }
            
            if(lin9.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(targetLanguage[i]);
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[9-1]);
                lt.setOrderNum(new Integer(9));
                
                //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){
                        
                        
                    
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){
                               // System.out.println("alexxx:assigning clp[z].getRate()= "+clp[z].getRate());
                                lt.setRate(clp[z].getRate());
                                lt.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                
                linTasks.add(lt);
            }
            
            if(lin10.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(targetLanguage[i]);
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[10-1]);
                lt.setOrderNum(new Integer(10));
                
                //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){
                        
                        
                    
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){
                               // System.out.println("alexxx:assigning clp[z].getRate()= "+clp[z].getRate());
                                lt.setRate(clp[z].getRate());
                                lt.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                
                linTasks.add(lt);
            }
            
            if(lin11.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setNotes((String) (qae5.get("linOtherText")));
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(targetLanguage[i]);
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[11-1]);
                lt.setOrderNum(new Integer(11));
                
                //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){
                        
                        
                    
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){
                                //System.out.println("alexxx:assigning clp[z].getRate()= "+clp[z].getRate());
                                lt.setRate(clp[z].getRate());
                                lt.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                linTasks.add(lt);
            }
                       
            //for each LinTask, add it to db and link it to this targetDoc
            for(Iterator iter = linTasks.iterator(); iter.hasNext();) {
                LinTask lt = (LinTask) iter.next();
                
                //link this linTask to the targetDoc; add new linTask to db
                Integer y = ProjectService.getInstance().linkTargetDocLinTask(td, lt); 
            }
            
        }
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
