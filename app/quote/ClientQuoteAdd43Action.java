/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.quote;

import org.apache.struts.validator.DynaValidatorForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

/**
 *
 * @author Neil
 */
public class ClientQuoteAdd43Action extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {




        MessageResources messages = getResources(request);

        // save errors
        ActionMessages errors = new ActionMessages();

        //START check for login (security)
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        String lin1 = noNull(request.getParameter("lin1"));
        String lin2 = noNull(request.getParameter("lin2"));
   
        String lin4 = noNull(request.getParameter("lin4"));
        String dtp1 = noNull(request.getParameter("dtp1"));
        String dtp2 = noNull(request.getParameter("dtp2"));
        String eng1 = noNull(request.getParameter("eng1"));
        String oth0 = noNull(request.getParameter("oth0"));
        String oth1 = noNull(request.getParameter("oth1"));

      //  Quote1 newQ = QuoteService.getInstance().getSingleQuote(new Integer(request.getParameter("quoteViewId")));
        Client_Quote CQ = QuoteService.getInstance().getSingleClient_Quote(new Integer(request.getParameter("quoteViewId")));
        Quote1 newQ = QuoteService.getInstance().getSingleQuote(CQ.getQuote_ID());
        Integer productId = Integer.parseInt(request.getParameter("productId"));
        
        int qnum = Integer.parseInt(request.getSession(false).getAttribute("ClientQuoteId").toString());
        Client_Quote cq = QuoteService.getInstance().getSingleClient_Quote(qnum);
       // Client_Quote cq = QuoteService.getInstance().getSingleClientQuoteFromProduct(newQ.getQuote1Id(), productId);
        String clientTask = "";
        DynaValidatorForm qvpr = (DynaValidatorForm) form;
        if (lin1.equals("on")) {
            if(clientTask!="")clientTask +=",";
            clientTask += "Translation Only";
        }
        if (lin2.equals("on")) {
            if(clientTask!="")clientTask +=",";
            clientTask += "Full linguistic Service";
        }
        
        if (lin4.equals("on")) {
            if(clientTask!="")clientTask +=",";
            clientTask += "ICR";
        }
        if (dtp1.equals("on")) {
            if(clientTask!="")clientTask +=",";
            clientTask += "DTP";
        }
        if (dtp2.equals("on")) {
            if(clientTask!="")clientTask +=",";
            clientTask += "Graphics";
        }
        if (eng1.equals("on")) {
            if(clientTask!="")clientTask +=",";
            clientTask += "TM Management";
        }
        if (oth0.equals("on")) {
            if(clientTask!="")clientTask +=",";
            clientTask += "Interpretation";
        }
        if (oth1.equals("on")) {
            if(clientTask!="")clientTask +=",";
            clientTask += "Project Management";
        }

        String otherTask = (String) (qvpr.get("otherTask"));
       
        cq.setClientTask(clientTask);
        cq.setOtherTask(otherTask);

        QuoteService.getInstance().saveClientQuote(cq);

      //NK:Adding task to lintask and dtp task
        /*
         Client Task	should result in:	Internal Task(s):
            Full Translation	 	Translation
                                        Editing
            Translation Only	 	Translation
            ICR                         In-Country Review
            DTP                         DTP	(under DTP tab)
                                        Proofreading	(under Linguistic tab)
                                        FQA	(under DTP tab)
            Graphics                    Graphics 	(under DTP tab)


         */



  // Quote1 newQ = QuoteService.getInstance().getSingleQuote(new Integer(request.getParameter("quoteViewId")));
        Project currentProject = ProjectService.getInstance().getSingleProject(newQ.getProject().getProjectId());
        Project pLazyLoad = ProjectService.getInstance().getSingleProject(currentProject.getProjectId());

         String[] linTaskOptions = ProjectService.getInstance().getLinTaskOptions1();
        String[] dtpTaskOptions = ProjectService.getInstance().getDtpTaskOptions1();

       Client c =currentProject.getCompany();
        ClientLanguagePair[] clp = null;
        if(c!=null){
            if(c.getClientLanguagePairs()!=null){
                clp = (ClientLanguagePair[])c.getClientLanguagePairs().toArray(new ClientLanguagePair[0]);

            }
        }

         String[] defaultInspections = ProjectService.getInstance().getDefaultInspectionOptions();
        String[] inspections = ProjectService.getInstance().getInspectionOptions();


         List sourcelang=QuoteService.getInstance().getSourceLang(newQ,cq.getId());
         for (int ii = 0; ii < sourcelang.size(); ii++) {
                    // SourceDoc sd = (SourceDoc) iterSources.next();
                         SourceDoc sd = (SourceDoc) sourcelang.get(ii);

          for(Iterator iterTargets = sd.getTargetDocs().iterator(); iterTargets.hasNext();) {

                         TargetDoc td = (TargetDoc) iterTargets.next();
                        Set linTasks = new HashSet(); //list of new linTasks
                        Set dtpTasks = new HashSet(); //list of new dtpTasks

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

                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(td.getLanguage());
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[1-1]);
                lt.setOrderNum(new Integer(1));
                System.out.println(lt.getTaskName()+lt.getTargetLanguage()+lt.getSourceLanguage());

                //Auto set rate fee
                    if(clp != null){
                         for(int z=0; z<clp.length; z++){
                             if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage())
                                && clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){
                                lt.setRateFee(clp[z].getRate());
                                System.out.println(lt.getTaskName()+"<------->"+clp[z].getRate()+"<------->"+clp[z].getUnits()+"<------->"+clp[z].getSource()+","+clp[z].getTarget());
                                lt.setUnitsFee(clp[z].getUnits());
                                lt.setRate(clp[z].getRate());
                                lt.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }

                linTasks.add(lt);
                lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(td.getLanguage());
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[2-1]);
                lt.setOrderNum(new Integer(2));
                System.out.println(lt.getTaskName()+lt.getTargetLanguage()+lt.getSourceLanguage());

                //Auto set rate fee
                    if(clp != null){
                         for(int z=0; z<clp.length; z++){
                             if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage())
                                && clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){
                                lt.setRateFee(clp[z].getRate());
                                System.out.println(lt.getTaskName()+"<------->"+clp[z].getRate()+"<------->"+clp[z].getUnits()+"<------->"+clp[z].getSource()+","+clp[z].getTarget());
                                lt.setUnitsFee(clp[z].getUnits());
                                lt.setRate(clp[z].getRate());
                                lt.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }

                linTasks.add(lt);

            }

                  if(lin2.equals("on")&&!lin1.equals("on")) { //if checked in form, then add this task to target Doc

                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(td.getLanguage());
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[1-1]);
                lt.setOrderNum(new Integer(1));
                System.out.println(lt.getTaskName()+lt.getTargetLanguage()+lt.getSourceLanguage());

                //Auto set rate fee
                    if(clp != null){
                         for(int z=0; z<clp.length; z++){
                             if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage())
                                && clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){
                                lt.setRateFee(clp[z].getRate());
                                System.out.println(lt.getTaskName()+"<------->"+clp[z].getRate()+"<------->"+clp[z].getUnits()+"<------->"+clp[z].getSource()+","+clp[z].getTarget());
                                lt.setUnitsFee(clp[z].getUnits());
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
                lt.setTargetLanguage(td.getLanguage());
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[3-1]);
                lt.setOrderNum(new Integer(3));
                System.out.println(lt.getTaskName()+lt.getTargetLanguage()+lt.getSourceLanguage());

                //Auto set rate fee
                    if(clp != null){
                         for(int z=0; z<clp.length; z++){
                             if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage())
                                && clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){
                                lt.setRateFee(clp[z].getRate());
                                System.out.println(lt.getTaskName()+"<------->"+clp[z].getRate()+"<------->"+clp[z].getUnits()+"<------->"+clp[z].getSource()+","+clp[z].getTarget());
                                lt.setUnitsFee(clp[z].getUnits());
                                lt.setRate(clp[z].getRate());
                                lt.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }

                linTasks.add(lt);

              }

                              //for each LinTask, add it to db and link it to this targetDoc




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
                LinTask lt = new LinTask();
                lt.setSourceLanguage(sd.getLanguage());
                lt.setTargetLanguage(td.getLanguage());
                lt.setTargetDoc(td);
                lt.setTaskName(linTaskOptions[4-1]);
                lt.setOrderNum(new Integer(4));
                System.out.println(lt.getTaskName()+lt.getTargetLanguage()+lt.getSourceLanguage());

                //Auto set rate fee
                    if(clp != null){
                         for(int z=0; z<clp.length; z++){
                             if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage())
                                && clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){
                                lt.setRateFee(clp[z].getRate());
                                System.out.println(lt.getTaskName()+"<------->"+clp[z].getRate()+"<------->"+clp[z].getUnits()+"<------->"+clp[z].getSource()+","+clp[z].getTarget());
                                lt.setUnitsFee(clp[z].getUnits());
                                lt.setRate(clp[z].getRate());
                                lt.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }

                linTasks.add(lt);
                     dt = new DtpTask();
                    dt.setSourceLanguage(sd.getLanguage());
                    dt.setTargetLanguage(td.getLanguage());
                    dt.setTargetDoc(td);
                    dt.setTaskName("FQA");
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
    //for each DtpTask, add it to db and link it to this targetDoc
            for(Iterator iter = dtpTasks.iterator(); iter.hasNext();) {
                DtpTask dt = (DtpTask) iter.next();

                //link this dtpTask to the targetDoc; add new dtpTask to db
                Integer z = ProjectService.getInstance().linkTargetDocDtpTask(td, dt);
            }

  for(Iterator iter = linTasks.iterator(); iter.hasNext();) {
                LinTask lt = (LinTask) iter.next();
                //System.out.println("linking task id="+lt.getLinTaskId());

                //link this linTask to the targetDoc; add new linTask to db
                Integer y = ProjectService.getInstance().linkTargetDocLinTask(td, lt);
            }



          }




    }






        return (mapping.findForward("Success"));

    }

    public String noNull(String s) {
        if (s == null) {
            return "";
        } else {
            return s;
        }
    }
}
