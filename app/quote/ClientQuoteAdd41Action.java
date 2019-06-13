/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.quote;

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
public class ClientQuoteAdd41Action extends Action{
     public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {




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



        String[] linOther = request.getParameterValues("lintask");
        // List linqlist = HrService.getInstance().getDropdownList("Linguistic");


        // String[] sourceLanguage = request.getParameterValues("sourceLanguage");
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

        String[] dtpOther = request.getParameterValues("formattask");

        String dtp0 = noNull(request.getParameter("dtp0"));
        String dtp1 = noNull(request.getParameter("dtp1"));
        String dtp2 = noNull(request.getParameter("dtp2"));
        String dtp3 = noNull(request.getParameter("dtp3"));
        String dtp4 = noNull(request.getParameter("dtp4"));
        String dtp5 = noNull(request.getParameter("dtp5"));
        String dtp6 = noNull(request.getParameter("dtp6"));
        String dtp7 = noNull(request.getParameter("dtp7"));

        String[] engOther = request.getParameterValues("enggtask");
        String eng0 = noNull(request.getParameter("eng0"));
        String eng1 = noNull(request.getParameter("eng1"));
        String eng2 = noNull(request.getParameter("eng2"));
        String eng3 = noNull(request.getParameter("eng3"));
        String eng4 = noNull(request.getParameter("eng4"));
        String eng5 = noNull(request.getParameter("eng5"));
        String eng6 = noNull(request.getParameter("eng6"));
        String eng7 = noNull(request.getParameter("eng7"));
        String eng8 = noNull(request.getParameter("eng8"));


          String[] othOther = request.getParameterValues("othertask");
          String oth0=noNull(request.getParameter("oth0"));
          String oth1=noNull(request.getParameter("oth1"));

           //System.out.println("linnnnnnnnnnnnnnnnnn"+linOther);
          //System.out.println("otherrrrrrrrrrrrrrrrrrrrrrr"+othOther);
          //System.out.println("otherrrrrrrrrrrrrrrrrrrrrrr"+oth0);


        String projectViewId = request.getParameter("projectViewId");
        Client_Quote cq1=QuoteService.getInstance().getSingleClient_Quote(new Integer(request.getParameter("quoteViewId")));
        //Project pLazyLoad = ProjectService.getInstance().getSingleProject(currentProject.getProjectId());
        Quote1 newQ = QuoteService.getInstance().getSingleQuote(cq1.getQuote_ID());
         //System.out.println(request.getParameter("quoteViewId"));
        Project currentProject = ProjectService.getInstance().getSingleProject(newQ.getProject().getProjectId());
        Project pLazyLoad = ProjectService.getInstance().getSingleProject(currentProject.getProjectId());
        //Project currentProject = ProjectService.getInstance().getSingleProject(Integer.valueOf(projectViewId));
         Integer productId=Integer.parseInt(request.getParameter("productId"));
         //System.out.println("productIdproductIdproductId"+productId);


        String[] linTaskOptions = ProjectService.getInstance().getLinTaskOptions1();
        String[] dtpTaskOptions = ProjectService.getInstance().getDtpTaskOptions1();
        String[] engTaskOptions = ProjectService.getInstance().getEngTaskOptions1();
        String[] otherTaskOptions = ProjectService.getInstance().getOtherTaskOptions();
     //   String[] othTaskOptions = ProjectService.getInstance().getOthTaskOptions();
        //String[] othTaskOptions = ProjectService.getInstance().getOthTaskOptions();

        Client c =currentProject.getCompany();
        ClientLanguagePair[] clp = null;
         //System.out.println(currentProject.getCompany().getCompany_name());

         //System.out.println("ling Task  "+linOther);
         //System.out.println("request.getParameter(lin1)"+request.getParameter("lin1"));
         //System.out.println("request.getParameter(lin1)"+request.getParameter("lin2"));
         //System.out.println("request.getParameter(lin1)"+request.getParameter("lin3"));


        if(c!=null){
            if(c.getClientLanguagePairs()!=null){
                clp = (ClientLanguagePair[])c.getClientLanguagePairs().toArray(new ClientLanguagePair[0]);

            }
        }

                        //START add Inspection list to this project

        String[] defaultInspections = ProjectService.getInstance().getDefaultInspectionOptions();
        String[] inspections = ProjectService.getInstance().getInspectionOptions();
          Client_Quote cq = QuoteService.getInstance().getSingleClient_Quote(new Integer(request.getSession(false).getAttribute("cid").toString()));
        ////System.out.println("currentProject.getProjectId()="+currentProject.getProjectId());
        List sourcelang=QuoteService.getInstance().getSourceLang(newQ,cq.getId());
         for (int ii = 0; ii < sourcelang.size(); ii++) {
                    // SourceDoc sd = (SourceDoc) iterSources.next();
                         SourceDoc sd = (SourceDoc) sourcelang.get(ii);
      //  for(Iterator iterSources = newQ.getSourceDocs().iterator(); iterSources.hasNext();) {
       // for(Iterator iterSources = currentProject.getSourceDocs().iterator(); iterSources.hasNext();) {
        //             SourceDoc sd = (SourceDoc) iterSources.next();




          for(Iterator iterTargets = sd.getTargetDocs().iterator(); iterTargets.hasNext();) {

                         TargetDoc td = (TargetDoc) iterTargets.next();
                        Set linTasks = new HashSet(); //list of new linTasks
                        Set dtpTasks = new HashSet(); //list of new dtpTasks
                        Set engTasks = new HashSet(); //list of new engTasks
                        Set othTasks = new HashSet(); //list of new engTasks






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

                //Auto set rate fee
                    if(clp != null){
                         for(int z=0; z<clp.length; z++){
                             if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage())
                                && clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){
                                lt.setRateFee(clp[z].getRate());
                                lt.setUnitsFee(clp[z].getUnits());
                                 lt.setRate(clp[z].getRate());
                                lt.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }

                linTasks.add(lt);

            }
       if(linOther!=null){
           //System.out.println("Lin Length"+linOther.length);
        for(int i=0;i<=linOther.length-1;i++)
        {  if(!linOther[i].equalsIgnoreCase("")){
            //System.out.println("Linguistic task" +linOther[i]);
            LinTask lt=new LinTask();
             lt.setSourceLanguage(sd.getLanguage());
             lt.setTargetLanguage(td.getLanguage());
             lt.setTargetDoc(td);
             lt.setTaskName(linOther[i]);
             lt.setOrderNum(new Integer(12+i));
//             pLazyLoad.getTypeOfText();
             if(clp != null){
                         for(int z=0; z<clp.length; z++){
                             if(clp[z].getSource()!=null && clp[z].getSource().equals(lt.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(lt.getTargetLanguage())
                                && clp[z].getTask()!=null && clp[z].getTask().equals("LIN - "+lt.getTaskName())){
                                 //System.out.println("lt.getTaskName()"+lt.getTaskName());
                                 if(clp[z].getTypeOfText().equals(pLazyLoad.getTypeOfText())){
                                lt.setRateFee(clp[z].getRate());
                                lt.setUnitsFee(clp[z].getUnits());
                                lt.setRate(clp[z].getRate());
                                lt.setUnits(clp[z].getUnits());
                                break;
                                }
                            }
                        }
                      }

                linTasks.add(lt);
                //System.out.println("lt"+lt);

            }
           }

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
                                if(clp[z].getTypeOfText().equals(pLazyLoad.getTypeOfText())){
                                 lt.setRateFee(clp[z].getRate());
                                lt.setUnitsFee(clp[z].getUnits());
                                break;
                                }
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
                                 {
                                 lt.setRateFee(clp[z].getRate());
                                lt.setUnitsFee(clp[z].getUnits());
                                break;
                                 }
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
                                if(clp[z].getTypeOfText().equals(pLazyLoad.getTypeOfText())){
                                 lt.setRateFee(clp[z].getRate());
                                lt.setUnitsFee(clp[z].getUnits());
                                break;
                                }
                            }
                        }
                      }
                linTasks.add(lt);
            }


                  //for each LinTask, add it to db and link it to this targetDoc
            for(Iterator iter = linTasks.iterator(); iter.hasNext();) {
                LinTask lt = (LinTask) iter.next();
                ////System.out.println("linking task id="+lt.getLinTaskId());

                //link this linTask to the targetDoc; add new linTask to db
                Integer y = ProjectService.getInstance().linkTargetDocLinTask(td, lt);
            }

          //Add DTP tasks now

        if(dtpOther!=null){
      //  //System.out.println("Lin Length"+linOther.length);
        for(int i=0;i<=dtpOther.length-1;i++)
        {
            if(!dtpOther[i].equalsIgnoreCase("")){
            //System.out.println("Linguistic task" +dtpOther[i]);
            DtpTask dt=new DtpTask();
             dt.setSourceLanguage(sd.getLanguage());
             dt.setTargetLanguage(td.getLanguage());
             dt.setTargetDoc(td);
             dt.setTaskName(dtpOther[i]);
             dt.setOrderNum(new Integer(8+i));
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
            }
       }

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
                                ////System.out.println("alexxx:assigning clp[z].getRate()= "+clp[z].getRate());
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
                                ////System.out.println("alexxx:assigning clp[z].getRate()= "+clp[z].getRate());
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
                                ////System.out.println("alexxx:assigning clp[z].getRate()= "+clp[z].getRate());
                                et.setRate(clp[z].getRate());
                                et.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }

                    engTasks.add(et);
                }



        if(engOther!=null){
           //System.out.println("Lin Length"+engOther.length);
        for(int i=0;i<=engOther.length-1;i++)
        {
             if(!engOther[i].equalsIgnoreCase("")){
            //System.out.println("Linguistic task" +engOther[i]);
            EngTask et=new EngTask();
             et.setSourceLanguage(sd.getLanguage());
             et.setTargetLanguage(td.getLanguage());
             et.setTargetDoc(td);
             et.setTaskName(engOther[i]);
             et.setOrderNum(new Integer(9+i));
             if(clp != null){
                         for(int z=0; z<clp.length; z++){
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(et.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(et.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask()!=null && clp[z].getTask().equals("ENG - "+et.getTaskName())){
                               // //System.out.println("alexxx:assigning clp[z].getRate()= "+clp[z].getRate());
                                et.setRate(clp[z].getRate());
                                et.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                    engTasks.add(et);
                }
            }

       }

             //for each EngTask, add it to db and link it to this targetDoc
            for(Iterator iter = engTasks.iterator(); iter.hasNext();) {
                EngTask et = (EngTask) iter.next();

                //link this engTask to the targetDoc; add new engTask to db
                Integer z = ProjectService.getInstance().linkTargetDocEngTask(td, et);
            }





                    if(oth0.equals("on")) { //if checked in form, then add this task to target Doc
                    OthTask ot = new OthTask();
                    ot.setSourceLanguage(sd.getLanguage());
                    ot.setTargetLanguage(td.getLanguage());
                    ot.setTargetDoc(td);
                    ot.setTaskName(otherTaskOptions[1-1]);
                    ot.setOrderNum(new Integer(2));

                    //Auto set rate fee
                    if(clp != null){
                         for(int z=0; z<clp.length; z++){
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(ot.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(ot.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask()!=null && clp[z].getTask().equals("ENG - "+ot.getTaskName())){
                                ////System.out.println("alexxx:assigning clp[z].getRate()= "+clp[z].getRate());
                                ot.setRate(clp[z].getRate());
                                ot.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }

                    othTasks.add(ot);
                }
                    
        
                    if(oth1.equals("on")) { //if checked in form, then add this task to target Doc
                    OthTask ot = new OthTask();
                    ot.setSourceLanguage(sd.getLanguage());
                    ot.setTargetLanguage(td.getLanguage());
                    ot.setTargetDoc(td);
                    ot.setTaskName(otherTaskOptions[2-1]);
                    ot.setOrderNum(new Integer(1));

                    //Auto set rate fee
                    if(clp != null){
                         for(int z=0; z<clp.length; z++){
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(ot.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(ot.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask()!=null && clp[z].getTask().equals("ENG - "+ot.getTaskName())){
                                ////System.out.println("alexxx:assigning clp[z].getRate()= "+clp[z].getRate());
                                ot.setRate(clp[z].getRate());
                                ot.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }

                    othTasks.add(ot);
                }



        if(othOther!=null){
           //System.out.println("Lin Length"+othOther.length);
        for(int i=0;i<=othOther.length-1;i++)
        {
            if(!othOther[i].equalsIgnoreCase("")){
            //System.out.println("Linguistic task" +othOther[i]);
            OthTask ot=new OthTask();
             ot.setSourceLanguage(sd.getLanguage());
             ot.setTargetLanguage(td.getLanguage());
             ot.setTargetDoc(td);
             ot.setTaskName(othOther[i]);
             ot.setOrderNum(new Integer(9+i));
             if(clp != null){
                         for(int z=0; z<clp.length; z++){
                            if(clp[z].getSource()!=null && clp[z].getSource().equals(ot.getSourceLanguage()) && clp[z].getTarget()!=null && clp[z].getTarget().equals(ot.getTargetLanguage())&& clp[z].getTask()!=null && clp[z].getTask()!=null && clp[z].getTask().equals("OTH - "+ot.getTaskName())){
                               // //System.out.println("alexxx:assigning clp[z].getRate()= "+clp[z].getRate());
                                ot.setRate(clp[z].getRate());
                                ot.setUnits(clp[z].getUnits());
                                break;
                            }
                        }
                      }
                    othTasks.add(ot);
                }
            }

       }

             //for each EngTask, add it to db and link it to this targetDoc
            for(Iterator iter = othTasks.iterator(); iter.hasNext();) {
                OthTask ot = (OthTask) iter.next();

                //link this engTask to the targetDoc; add new engTask to db
                Integer z = ProjectService.getInstance().linkTargetDocOthTask(td, ot);
            }










          }




    }






    return (mapping.findForward("Success"));

        // Forward control to the specified success URI
	//return (mapping.findForward("Success"));
    }

    public String noNull(String s){
        if(s==null){
            return "";
        }else{
            return s;
        }
    }
}