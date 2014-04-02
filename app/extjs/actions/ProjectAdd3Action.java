//ProjectAdd5Action.java collects the target(s) info
//for one source within one project

package app.extjs.actions;
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
import app.client.*;
import app.extjs.global.LanguageAbs;
import app.project.*;
import app.security.*;


public final class ProjectAdd3Action extends Action {


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
        //DynaValidatorForm qae5 = (DynaValidatorForm) form;
        
     /*   String[] targetLanguage = (String[]) (qae5.get("targetLanguageRight"));  
        String linCompilation = (String) qae5.get("linCompilation");
        String linTranslation = (String) qae5.get("linTranslation");
        String linEdit = (String) qae5.get("linEditing");
        String linTrEd = (String) qae5.get("linTrEd");
        String linProofreading = (String) qae5.get("linProofreading");
        String linLin = (String) qae5.get("linLinguistic");
        String linIn = (String) qae5.get("linIn");           
        String linEval = (String) qae5.get("linEvaluation");
        String linImplementation = (String) qae5.get("linImplementation");
        String linProofCC = (String) qae5.get("linProofreadingCC");
        String linFinal = (String) qae5.get("linFinal");
        String linOther = (String) qae5.get("linOther");
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
        String lin11 = (String) (qae5.get("lin11"));  */
        String linOther = noNull(request.getParameter("linOther"));
        String lin0 = noNull(request.getParameter("lin0"));  
        String lin1 = noNull(request.getParameter("lin1"));  
        String lin2 = noNull(request.getParameter("lin2"));  
        String lin3 = noNull(request.getParameter("lin3"));  
        String lin4 = noNull(request.getParameter("lin4"));  
        String lin5 = noNull(request.getParameter("lin5"));  
        String lin6 = noNull(request.getParameter("lin6"));  
        String lin7 = noNull(request.getParameter("lin7"));  
        String lin8 = noNull(request.getParameter("lin8"));  
        String lin9 = noNull(request.getParameter("lin9"));  
        String lin10 = noNull(request.getParameter("lin10"));  
        String lin11 = noNull(request.getParameter("lin11")); 
        System.out.println("linOthererrrrrrrrrrrrrrrrrrrr"+linOther);
        String dtpOther = noNull(request.getParameter("dtpOther"));
        String dtp0 = noNull(request.getParameter("dtp0"));  
        String dtp1 = noNull(request.getParameter("dtp1"));  
        String dtp2 = noNull(request.getParameter("dtp2"));  
        String dtp3 = noNull(request.getParameter("dtp3"));  
        String dtp4 = noNull(request.getParameter("dtp4"));  
        String dtp5 = noNull(request.getParameter("dtp5"));  
        String dtp6 = noNull(request.getParameter("dtp6"));  
        String dtp7 = noNull(request.getParameter("dtp7"));
        
        String engOther = noNull(request.getParameter("engOther"));
        String eng0 = noNull(request.getParameter("eng0"));  
        String eng1 = noNull(request.getParameter("eng1"));  
        String eng2 = noNull(request.getParameter("eng2"));  
        String eng3 = noNull(request.getParameter("eng3"));  
        String eng4 = noNull(request.getParameter("eng4"));  
        String eng5 = noNull(request.getParameter("eng5"));  
        String eng6 = noNull(request.getParameter("eng6"));  
        String eng7 = noNull(request.getParameter("eng7"));  
        String eng8 = noNull(request.getParameter("eng8"));  
        
        
        String projectViewId = request.getParameter("projectViewId");
        Project currentProject = ProjectService.getInstance().getSingleProject(Integer.valueOf(projectViewId)); 
        String[] linTaskOptions = ProjectService.getInstance().getLinTaskOptions();
        String[] dtpTaskOptions = ProjectService.getInstance().getDtpTaskOptions();
        String[] engTaskOptions = ProjectService.getInstance().getEngTaskOptions();
         
        Client c =currentProject.getCompany();
        ClientLanguagePair[] clp = null;
       
        
        if(c!=null){
            if(c.getClientLanguagePairs()!=null){
                clp = (ClientLanguagePair[])c.getClientLanguagePairs().toArray(new ClientLanguagePair[0]);
               
            }
        }
        
                        //START add Inspection list to this project
        Project pLazyLoad = ProjectService.getInstance().getSingleProject(currentProject.getProjectId()); 
        String[] defaultInspections = ProjectService.getInstance().getDefaultInspectionOptions();
        String[] inspections = ProjectService.getInstance().getInspectionOptions();
        
        for(Iterator iterSources = currentProject.getSourceDocs().iterator(); iterSources.hasNext();) {
                        SourceDoc sd = (SourceDoc) iterSources.next();
                        
                 
             
            
          for(Iterator iterTargets = sd.getTargetDocs().iterator(); iterTargets.hasNext();) {
                            
                            TargetDoc td = (TargetDoc) iterTargets.next();
                        Set linTasks = new HashSet(); //list of new linTasks         
                        Set dtpTasks = new HashSet(); //list of new dtpTasks  
                        Set engTasks = new HashSet(); //list of new engTasks
                        
                        
       
        
        
        int j = 0;
        for(int i = 0; i < inspections.length; i++) {
            Inspection i2 = new Inspection();
            if(j < 7 && defaultInspections[j].equals(inspections[i])) { //if default inspection
                i2.setInDefault(true);
                i2.setApplicable(true);
                j++;
            }
            else {
               i2.setInDefault(false);
               i2.setApplicable(false);
            }
            i2.setOrderNum(new Integer(i+1));
            i2.setMilestone(inspections[i]);
            i2.setInspector("");
            i2.setNote("");
            i2.setLanguage((String)LanguageAbs.getInstance().getAbs().get(sd.getLanguage()) +" to "+(String)LanguageAbs.getInstance().getAbs().get(td.getLanguage()));
            
            //upload to db
            if(i2.isApplicable()){
                i2.setInspectionType("RECEIVING");
                ProjectService.getInstance().addInspectionWithProject(pLazyLoad, i2);
                i2.setInspectionType("IN-PROCESS");
                ProjectService.getInstance().addInspectionWithProject(pLazyLoad, i2);
                i2.setInspectionType("FINAL");
                ProjectService.getInstance().addInspectionWithProject(pLazyLoad, i2);
            }
        }      
        //END add Inspection list to this project
        
        
                        
        if(lin1.equals("on")) { //if checked in form, then add this task to target Doc
                System.out.println("inside lin1"); 
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(td.getLanguage());
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[1-1]);
                lt.setOrderNum(new Integer(1));
                 
                //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){
                             if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage()) 
                                && clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){                               
                                lt.setRateFee(clp[z].getRate());
                                lt.setUnitsFee(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                
                linTasks.add(lt);
                
            }
                  
            if(lin2.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(td.getLanguage());
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[2-1]);
                lt.setOrderNum(new Integer(2));
                
                //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){
                             if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage()) 
                                && clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){                               
                                lt.setRateFee(clp[z].getRate());
                                lt.setUnitsFee(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                
                linTasks.add(lt);
            }
            
            if(lin3.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(td.getLanguage());
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[3-1]);
                lt.setOrderNum(new Integer(3));
                //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){
                             if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage()) 
                                && clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){                               
                                lt.setRateFee(clp[z].getRate());
                                lt.setUnitsFee(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                linTasks.add(lt);
            }
            
            if(lin4.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(td.getLanguage());
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[4-1]);
                lt.setOrderNum(new Integer(4));
                //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){
                             if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage()) 
                                && clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){                               
                                lt.setRateFee(clp[z].getRate());
                                lt.setUnitsFee(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                linTasks.add(lt);
            }
                        
            if(lin5.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(td.getLanguage());
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[5-1]);
                lt.setOrderNum(new Integer(5));
                
                //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){
                             if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage()) 
                                && clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){                               
                                lt.setRateFee(clp[z].getRate());
                                lt.setUnitsFee(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                
                linTasks.add(lt);
            }
            
            if(lin6.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(td.getLanguage());
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[6-1]);
                lt.setOrderNum(new Integer(6));
                
                //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){
                             if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage()) 
                                && clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){                               
                                lt.setRateFee(clp[z].getRate());
                                lt.setUnitsFee(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                
                linTasks.add(lt);
            }
            
            if(lin7.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(td.getLanguage());
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[7-1]);
                lt.setOrderNum(new Integer(7));
                
                //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){
                             if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage()) 
                                && clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){                               
                                lt.setRateFee(clp[z].getRate());
                                lt.setUnitsFee(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                
                linTasks.add(lt);
            }
            
            if(lin8.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(td.getLanguage());
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[8-1]);
                lt.setOrderNum(new Integer(8));
                //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){
                             if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage()) 
                                && clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){                               
                                lt.setRateFee(clp[z].getRate());
                                lt.setUnitsFee(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                linTasks.add(lt);
            }
            
            if(lin9.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(td.getLanguage());
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[9-1]);
                lt.setOrderNum(new Integer(9));
                
                //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){
                             if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage()) 
                                && clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){                               
                                lt.setRateFee(clp[z].getRate());
                                lt.setUnitsFee(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                
                linTasks.add(lt);
            }
            
            if(lin10.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(td.getLanguage());
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[10-1]);
                lt.setOrderNum(new Integer(10));
                
                //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){
                             if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage()) 
                                && clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){                               
                                lt.setRateFee(clp[z].getRate());
                                lt.setUnitsFee(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                
                linTasks.add(lt);
            }
            
            if(lin11.equals("on")) { //if checked in form, then add this task to target Doc
                LinTask lt = new LinTask();
                lt.setNotes(noNull(request.getParameter("linOtherText")));
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(td.getLanguage());
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[11-1]);
                lt.setOrderNum(new Integer(11));
                
                //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){
                             if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage()) 
                                && clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){                               
                                lt.setRateFee(clp[z].getRate());
                                lt.setUnitsFee(clp[z].getUnits());
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
                        
          //Add DTP tasks now
                        if(dtp1.equals("on")) { //if checked in form, then add this task to target Doc
                    DtpTask dt = new DtpTask();
                    dt.setSourceLanguage(sd.getLanguage());
                    dt.setTargetLanguage(td.getLanguage());
                    dt.setTargetDoc(td);
                    dt.setTaskName(dtpTaskOptions[1-1]);
                    dt.setOrderNum(new Integer(1));
                    
                    //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){                       
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(dt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(dt.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask().equals("DTP - "+dt.getTaskName())){                            
                                dt.setRate(clp[z].getRate());
                                dt.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                    
                    dtpTasks.add(dt);
                }

                if(dtp2.equals("on")) { //if checked in form, then add this task to target Doc
                    DtpTask dt = new DtpTask();
                    dt.setSourceLanguage(sd.getLanguage());
                    dt.setTargetLanguage(td.getLanguage());
                    dt.setTargetDoc(td);
                    dt.setTaskName(dtpTaskOptions[2-1]);
                    dt.setOrderNum(new Integer(2));
                    
                    //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){                       
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(dt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(dt.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask().equals("DTP - "+dt.getTaskName())){                            
                                dt.setRate(clp[z].getRate());
                                dt.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                    
                    dtpTasks.add(dt);
                }
                
                if(dtp3.equals("on")) { //if checked in form, then add this task to target Doc
                    DtpTask dt = new DtpTask();
                    dt.setSourceLanguage(sd.getLanguage());
                    dt.setTargetLanguage(td.getLanguage());
                    dt.setTargetDoc(td);
                    dt.setTaskName(dtpTaskOptions[3-1]);
                    dt.setOrderNum(new Integer(3));
                    
                    //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){                       
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(dt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(dt.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask().equals("DTP - "+dt.getTaskName())){                            
                                dt.setRate(clp[z].getRate());
                                dt.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                    
                    
                    dtpTasks.add(dt);
                }

                if(dtp4.equals("on")) { //if checked in form, then add this task to target Doc
                    DtpTask dt = new DtpTask();
                    dt.setSourceLanguage(sd.getLanguage());
                    dt.setTargetLanguage(td.getLanguage());
                    dt.setTargetDoc(td);
                    dt.setTaskName(dtpTaskOptions[4-1]);
                    dt.setOrderNum(new Integer(4));
                    
                    //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){                       
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(dt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(dt.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask().equals("DTP - "+dt.getTaskName())){                            
                                dt.setRate(clp[z].getRate());
                                dt.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                    
                    dtpTasks.add(dt);
                }
                
                if(dtp5.equals("on")) { //if checked in form, then add this task to target Doc
                    DtpTask dt = new DtpTask();
                    dt.setSourceLanguage(sd.getLanguage());
                    dt.setTargetLanguage(td.getLanguage());
                    dt.setTargetDoc(td);
                    dt.setTaskName(dtpTaskOptions[5-1]);
                    dt.setOrderNum(new Integer(5));
                    
                    //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){                       
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(dt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(dt.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask().equals("DTP - "+dt.getTaskName())){                            
                                dt.setRate(clp[z].getRate());
                                dt.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                    
                    dtpTasks.add(dt);
                }
                
                if(dtp6.equals("on")) { //if checked in form, then add this task to target Doc
                    DtpTask dt = new DtpTask();
                    dt.setSourceLanguage(sd.getLanguage());
                    dt.setTargetLanguage(td.getLanguage());
                    dt.setTargetDoc(td);
                    dt.setTaskName(dtpTaskOptions[6-1]);
                    dt.setOrderNum(new Integer(6));
                    
                    //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){                       
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(dt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(dt.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask().equals("DTP - "+dt.getTaskName())){                            
                                dt.setRate(clp[z].getRate());
                                dt.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                    
                    dtpTasks.add(dt);
                }
                
                if(dtp7.equals("on")) { //if checked in form, then add this task to target Doc
                    DtpTask dt = new DtpTask();
                    dt.setNotes(request.getParameter("dtpOtherText"));
                    dt.setSourceLanguage(sd.getLanguage());
                    dt.setTargetLanguage(td.getLanguage());
                    dt.setTargetDoc(td);
                    dt.setTaskName(dtpTaskOptions[7-1]);
                    dt.setOrderNum(new Integer(7));
                    
                    //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){                       
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(dt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(dt.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask().equals("DTP - "+dt.getTaskName())){                            
                                dt.setRate(clp[z].getRate());
                                dt.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                    
                    dtpTasks.add(dt);
                }
                
             //for each DtpTask, add it to db and link it to this targetDoc
            for(Iterator iter = dtpTasks.iterator(); iter.hasNext();) {
                DtpTask dt = (DtpTask) iter.next();
                
                //link this dtpTask to the targetDoc; add new dtpTask to db
                Integer z = ProjectService.getInstance().linkTargetDocDtpTask(td, dt); 
            }
                        
                        //Add Engineering tasks
                        if(eng1.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                    et.setTargetDoc(td);
                    et.setTaskName(engTaskOptions[1-1]);
                    et.setOrderNum(new Integer(1));
                    
                    //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){                       
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(et.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(et.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask()!=null && clp[z].getTask().equals("ENG - "+et.getTaskName())){                            
                                System.out.println("alexxx:assigning clp[z].getRate()= "+clp[z].getRate());
                                et.setRate(clp[z].getRate());
                                et.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                    
                    engTasks.add(et);
                }

                if(eng2.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                    et.setTargetDoc(td);
                    et.setTaskName(engTaskOptions[2-1]);
                    et.setOrderNum(new Integer(2));
                    
                    //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){                       
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(et.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(et.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask()!=null && clp[z].getTask().equals("ENG - "+et.getTaskName())){                            
                                System.out.println("alexxx:assigning clp[z].getRate()= "+clp[z].getRate());
                                et.setRate(clp[z].getRate());
                                et.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                    
                    engTasks.add(et);
                }                
                
                if(eng3.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                    et.setTargetDoc(td);
                    et.setTaskName(engTaskOptions[3-1]);
                    et.setOrderNum(new Integer(3));
                    
                    //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){                       
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(et.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(et.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask()!=null && clp[z].getTask().equals("ENG - "+et.getTaskName())){                            
                                System.out.println("alexxx:assigning clp[z].getRate()= "+clp[z].getRate());
                                et.setRate(clp[z].getRate());
                                et.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                    
                    engTasks.add(et);
                }
                
                if(eng4.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                    et.setTargetDoc(td);
                    et.setTaskName(engTaskOptions[4-1]);
                    et.setOrderNum(new Integer(4));
                    
                    //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){                       
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(et.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(et.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask()!=null && clp[z].getTask().equals("ENG - "+et.getTaskName())){                            
                                System.out.println("alexxx:assigning clp[z].getRate()= "+clp[z].getRate());
                                et.setRate(clp[z].getRate());
                                et.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                    
                    engTasks.add(et);
                }
                
                if(eng5.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                    et.setTargetDoc(td);
                    et.setTaskName(engTaskOptions[5-1]);
                    et.setOrderNum(new Integer(5));
                    
                    //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){                       
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(et.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(et.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask()!=null && clp[z].getTask().equals("ENG - "+et.getTaskName())){                            
                                System.out.println("alexxx:assigning clp[z].getRate()= "+clp[z].getRate());
                                et.setRate(clp[z].getRate());
                                et.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                    
                    engTasks.add(et);
                }
                
                if(eng6.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                    et.setTargetDoc(td);
                    et.setTaskName(engTaskOptions[6-1]);
                    et.setOrderNum(new Integer(6));
                    
                    //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){                       
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(et.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(et.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask()!=null && clp[z].getTask().equals("ENG - "+et.getTaskName())){                            
                                System.out.println("alexxx:assigning clp[z].getRate()= "+clp[z].getRate());
                                et.setRate(clp[z].getRate());
                                et.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                    
                    engTasks.add(et);
                }
                
                if(eng7.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                    et.setTargetDoc(td);
                    et.setTaskName(engTaskOptions[7-1]);
                    et.setOrderNum(new Integer(7));
                    //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){                       
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(et.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(et.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask()!=null && clp[z].getTask().equals("ENG - "+et.getTaskName())){                            
                                System.out.println("alexxx:assigning clp[z].getRate()= "+clp[z].getRate());
                                et.setRate(clp[z].getRate());
                                et.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                    engTasks.add(et);
                }
                
                if(eng8.equals("on")) { //if checked in form, then add this task to target Doc
                    EngTask et = new EngTask();
                    et.setNotes(request.getParameter("engOtherText"));
                    et.setSourceLanguage(sd.getLanguage());
                    et.setTargetLanguage(td.getLanguage());
                    et.setTargetDoc(td);
                    et.setTaskName(engTaskOptions[8-1]);
                    et.setOrderNum(new Integer(8));
                    //Auto set rate fee
                    if(clp != null){  
                         for(int z=0; z<clp.length; z++){                       
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(et.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(et.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask()!=null && clp[z].getTask().equals("ENG - "+et.getTaskName())){                            
                                System.out.println("alexxx:assigning clp[z].getRate()= "+clp[z].getRate());
                                et.setRate(clp[z].getRate());
                                et.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                    engTasks.add(et);
                }
                
             //for each EngTask, add it to db and link it to this targetDoc
            for(Iterator iter = engTasks.iterator(); iter.hasNext();) {
                EngTask et = (EngTask) iter.next();
                
                //link this engTask to the targetDoc; add new engTask to db
                Integer z = ProjectService.getInstance().linkTargetDocEngTask(td, et); 
            }
                  
    }  
             
        
                       
            
            
        }
        
        // Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

    public String noNull(String s){
        if(s==null){
            return "";
        }else{
            return s;
        }
    }
}
