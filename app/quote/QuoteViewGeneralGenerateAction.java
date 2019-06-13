/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.quote;

import app.client.Client;
import app.client.ClientContact;
import app.client.ClientService;
import app.extjs.helpers.QuoteHelper;
import app.extjs.vo.Upload_Doc;
import app.inteqa.INBasics;
import app.inteqa.INDelivery;
import app.inteqa.INDeliveryReq;
import app.inteqa.InteqaService;
import app.project.DtpTask;
import app.project.EngTask;
import app.project.LinTask;
import app.project.OthTask;
import app.project.SourceDoc;
import app.project.TargetDoc;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.sourceforge.rtf.IRTFDocumentTransformer;
import net.sourceforge.rtf.RTFTemplate;
import net.sourceforge.rtf.handler.RTFDocumentHandler;
import net.sourceforge.rtf.template.velocity.RTFVelocityTransformerImpl;
import net.sourceforge.rtf.template.velocity.VelocityTemplateEngineImpl;
//import org.apache.struts.action.Action;
//import org.apache.struts.action.ActionMessages;
import org.apache.velocity.app.VelocityEngine;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;


/**
 *
 * @author niteshwar
 */
public class QuoteViewGeneralGenerateAction extends Action {

    // ----------------------------------------------------- Instance Variables
    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log
            = LogFactory.getLog("org.apache.struts.webapp.Example");

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
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)

        String rtfSource = "/Users/abhisheksingh/Project/templates/GENERAL Template.rtf";
        rtfSource = "C:/templates/GENERAL Template.rtf";
        RTFTemplate rtfTemplate = new RTFTemplate();
        // Parser
        RTFDocumentHandler parser = new RTFDocumentHandler();
        rtfTemplate.setParser(parser);
        // Transformer
        IRTFDocumentTransformer transformer = new RTFVelocityTransformerImpl();
        rtfTemplate.setTransformer(transformer);
        // Template engine 
        VelocityTemplateEngineImpl templateEngine = new VelocityTemplateEngineImpl();
        // Initialize velocity engine
        VelocityEngine velocityEngine = new VelocityEngine();
        templateEngine.setVelocityEngine(velocityEngine);
        rtfTemplate.setTemplateEngine(templateEngine);

        java.io.File file = new java.io.File(rtfSource);
//  // 1. Get default RTFtemplateBuilder
//  RTFTemplateBuilder builder = RTFTemplateBuilder.newRTFTemplateBuilder();            
//    
//  // 2. Get RTFtemplate with default Implementation of template engine (Velocity) 
//  RTFTemplate rtfTemplate = builder.newRTFTemplate();    
//  
        // 3. Set the RTF model source 
        rtfTemplate.setTemplate(file);

        //id of quote from request
        String quoteId = null;
        quoteId = request.getParameter("quoteViewId");
        String tmStatement = request.getParameter("tmStatement");

        //check attribute in request
        if (quoteId == null) {
            quoteId = (String) request.getAttribute("quoteViewId");
        }

        //id of quote from cookie
        if (quoteId == null) {
            quoteId = StandardCode.getInstance().getCookie("quoteViewId", request.getCookies());
        }

        //default client to first if not in request or cookie
        if (quoteId == null) {
            java.util.List results = QuoteService.getInstance().getQuoteList();
            Quote1 first = (Quote1) results.get(0);
            quoteId = String.valueOf(first.getQuote1Id());
        }
        Integer flag = 0;
        Integer id = Integer.valueOf(quoteId);
        //get quote
        Quote1 q = QuoteService.getInstance().getSingleQuote(id);

        //END get id of current quote from either request, attribute, or cookie               
        Client c = q.getProject().getCompany();

  // 4. Put the context           
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
        rtfTemplate.put("CURRENT_DATE", sdf.format(new Date()));
        rtfTemplate.put("QUOTE_NUMBER", q.getNumber());
        if (null != tmStatement && !tmStatement.equalsIgnoreCase("false")) {
            rtfTemplate.put("tmStatement", true);
        }
//client Contact
        rtfTemplate.put("COMPANYNAME", q.getProject().getCompany().getCompany_name());
        try {
            rtfTemplate.put("CONTACTNAME", StandardCode.getInstance().noNull(q.getProject().getContact().getFirst_name()) + " " + StandardCode.getInstance().noNull(q.getProject().getContact().getLast_name()));
            //System.out.println("hereeeeee3");
            rtfTemplate.put("CONTACTTITLE", StandardCode.getInstance().noNull(q.getProject().getContact().getTitle()));
            String comma = ", ";
            if ("".equalsIgnoreCase(StandardCode.getInstance().noNull(q.getProject().getContact().getDivision()))) {
                comma = "";
            }
            rtfTemplate.put("CONTACTADDRESS1", StandardCode.getInstance().noNull(q.getProject().getContact().getDivision()) + comma + StandardCode.getInstance().noNull(q.getProject().getContact().getAddress_1()));
            rtfTemplate.put("CONTACTADDRESS2", StandardCode.getInstance().noNull(q.getProject().getContact().getAddress_2()));
            rtfTemplate.put("CONTACTCITY", StandardCode.getInstance().noNull(q.getProject().getContact().getCity()));
            rtfTemplate.put("CONTACTSTATE", StandardCode.getInstance().noNull(q.getProject().getContact().getState_prov()));
            rtfTemplate.put("CONTACTZIPCODE", StandardCode.getInstance().noNull(q.getProject().getCompany().getZip_postal_code()));
            if (q.getProject().getContact().getWorkPhoneEx() != null && q.getProject().getContact().getWorkPhoneEx().length() > 0) { //ext present
                rtfTemplate.put("CONTACTEXT", StandardCode.getInstance().noNull(q.getProject().getContact().getWorkPhoneEx()));
            }
            rtfTemplate.put("CONTACTPHONE", StandardCode.getInstance().noNull(q.getProject().getContact().getTelephone_number()));
            rtfTemplate.put("CONTACTFAX", StandardCode.getInstance().noNull(q.getProject().getContact().getFax_number()));
            rtfTemplate.put("CONTACTEMAIL", StandardCode.getInstance().noNull(q.getProject().getContact().getEmail_address()));
            if(!StandardCode.getInstance().noNull(q.getProject().getContact().getWorkPhoneEx()).equalsIgnoreCase(""))
                rtfTemplate.put("CLIENT_EXTENSION", "ext: "+StandardCode.getInstance().noNull(q.getProject().getContact().getWorkPhoneEx()));

        } catch (Exception e) {
            try {

                User u = UserService.getInstance().getSingleUser(q.getEnteredById());
                ClientContact cc = ClientService.getInstance().getSingleClientContact(u.getClient_contact());
                rtfTemplate.put("CONTACTNAME", StandardCode.getInstance().noNull(cc.getFirst_name()) + " " + StandardCode.getInstance().noNull(cc.getLast_name()));
                rtfTemplate.put("CONTACTADDRESS1", StandardCode.getInstance().noNull(cc.getAddress_1()));
                rtfTemplate.put("CONTACTADDRESS2", StandardCode.getInstance().noNull(cc.getAddress_2()));
                rtfTemplate.put("CONTACTCITY", StandardCode.getInstance().noNull(cc.getCity()));
                rtfTemplate.put("CONTACTSTATE", StandardCode.getInstance().noNull(cc.getState_prov()));
                rtfTemplate.put("CONTACTZIPCODE", StandardCode.getInstance().noNull(cc.getZip_postal_code()));

                if (cc.getTelephone_number() != null && cc.getTelephone_number().length() > 0) { //ext present
                    rtfTemplate.put("CONTACTEXT", StandardCode.getInstance().noNull(cc.getWorkPhoneEx()));
                }
                rtfTemplate.put("CONTACTPHONE", StandardCode.getInstance().noNull(cc.getCell_phone_number()));
                rtfTemplate.put("CONTACTEMAIL", StandardCode.getInstance().noNull(cc.getEmail_address()));
                if(!StandardCode.getInstance().noNull(cc.getWorkPhoneEx()).equalsIgnoreCase(""))
                rtfTemplate.put("CLIENT_EXTENSION", "ext: "+StandardCode.getInstance().noNull(cc.getWorkPhoneEx()));

            } catch (Exception e1) {
                try {
                    User u = UserService.getInstance().getSingleUser(q.getEnteredById());
                    Client client = ClientService.getInstance().getClient(u.getID_Client());
                    rtfTemplate.put("CONTACTNAME", StandardCode.getInstance().noNull(u.getFirstName()) + " " + StandardCode.getInstance().noNull(u.getLastName()));
                    rtfTemplate.put("CONTACTADDRESS1", StandardCode.getInstance().noNull(client.getAddress_1()));
                    rtfTemplate.put("CONTACTADDRESS2", StandardCode.getInstance().noNull(client.getAddress_2()));
                    rtfTemplate.put("CONTACTCITY", StandardCode.getInstance().noNull(client.getCity()));
                    rtfTemplate.put("CONTACTSTATE", StandardCode.getInstance().noNull(client.getState_prov()));
                    rtfTemplate.put("CONTACTZIPCODE", StandardCode.getInstance().noNull(client.getZip_postal_code()));
                    rtfTemplate.put("CONTACTPHONE", StandardCode.getInstance().noNull(client.getWorkPhoneEx()));
                    rtfTemplate.put("CONTACTEMAIL", StandardCode.getInstance().noNull(client.getEmail_address()));
                } catch (Exception e2) {
                    User u = UserService.getInstance().getSingleUser(q.getEnteredById());
                    rtfTemplate.put("CONTACTNAME", StandardCode.getInstance().noNull(u.getFirstName()) + " " + StandardCode.getInstance().noNull(u.getLastName()));
                    rtfTemplate.put("CONTACTADDRESS1", StandardCode.getInstance().noNull(u.getWorkAddress()));
                    rtfTemplate.put("CONTACTCITY", StandardCode.getInstance().noNull(u.getWorkCity()));
                    rtfTemplate.put("CONTACTSTATE", StandardCode.getInstance().noNull(u.getWorkState()));
                    rtfTemplate.put("CONTACTZIPCODE", StandardCode.getInstance().noNull(u.getWorkZip()));
                    if (u.getWorkPhoneEx() != null && u.getWorkPhoneEx().length() > 0) { //ext present
                        rtfTemplate.put("CONTACTEXT", StandardCode.getInstance().noNull(u.getWorkPhoneEx()));
                    }
                    rtfTemplate.put("CONTACTPHONE", StandardCode.getInstance().noNull(u.getWorkPhone()));
                    rtfTemplate.put("CONTACTEMAIL", StandardCode.getInstance().noNull(u.getWorkEmail1()));
                }
            }
        }

  //pm
//  rtfTemplate.put("PROJECT_MANAGER", q.getNumber());
//  rtfTemplate.put("PM_POSITION", q.getNumber());
//  rtfTemplate.put("PM_PHONE", q.getNumber());
//  rtfTemplate.put("PM_MOBILE", q.getNumber());
//  rtfTemplate.put("PM_EMAIL", q.getNumber());
        try {
//            String[] fname = q.getProject().getPm().split(" ");
            User enteredBy = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));

            try {
                rtfTemplate.put("PMNAME", StandardCode.getInstance().noNull(enteredBy.getFirstName()) + " " + StandardCode.getInstance().noNull(enteredBy.getLastName()));
            } catch (Exception e) {
            }

            try {
                rtfTemplate.put("PM_TITLE", StandardCode.getInstance().noNull(enteredBy.getPosition().getPosition()));

            } catch (Exception e) {
            }

            rtfTemplate.put("EMAIL_PM", StandardCode.getInstance().noNull(enteredBy.getWorkEmail1()));
            String pmPhone = StandardCode.getInstance().noNull(enteredBy.getWorkPhone());
            if(!StandardCode.getInstance().noNull(enteredBy.getWorkPhoneEx()).trim().equalsIgnoreCase(""))
                pmPhone+=" ext " + StandardCode.getInstance().noNull(enteredBy.getWorkPhoneEx());
            rtfTemplate.put("PHONE_EXTENSION_PM", pmPhone);

            rtfTemplate.put("EXCELNAME", "Excel Translations, Inc. ");
            rtfTemplate.put("EXCELADDRESS1", StandardCode.getInstance().noNull(enteredBy.getLocation().getAddress_1()));
            rtfTemplate.put("EXCELADDRESS2", StandardCode.getInstance().noNull(enteredBy.getLocation().getAddress_2()));
            rtfTemplate.put("EXCELADDRESS3", StandardCode.getInstance().noNull(enteredBy.getLocation().getCity() + ", "
                    + enteredBy.getLocation().getState_prov() + " " + enteredBy.getLocation().getZip_postal_code() + ", " + enteredBy.getLocation().getCountry()));
            if (null != enteredBy.getWorkCell()) {
                rtfTemplate.put("PM_MOBILE", enteredBy.getWorkCell());
            }

        } catch (Exception e) {

        }

        List<Map> NOTES = new ArrayList<>();
  
    rtfTemplate.put("DTPTASKNAME", "Formatting/Desktop Publishing");
        INBasics inBasics = InteqaService.getInstance().getINBasics(q.getProject().getProjectId());
        if(null != inBasics){
        if(inBasics.isDtpReq21()){
           rtfTemplate.put("DTPTASKNAME", "Formatting to match original");
        }
        }
        INDelivery indel=InteqaService.getInstance().getINDelivery(q.getProject().getProjectId());
        if(indel!=null){
            List inDel = InteqaService.getInstance().getInDeliveryReqGrid(indel.getId(),"R");
            for (int i = 0; i < inDel.size(); i++) {
                Map<String, String> NOTE = new HashMap<>();
                INDeliveryReq inDeliveryReq = (INDeliveryReq) inDel.get(i);
                NOTE.put("NOTE", inDeliveryReq.getClientReqText());
                NOTES.add(NOTE);

            }
        } 
        
         rtfTemplate.put("NOTES", NOTES);
  //notes
//  rtfTemplate.put("NOTES", q.getNumber());
  //list of filenames
        List<Map> FILELIST = new ArrayList<Map>();
        Upload_Doc ud1 = QuoteService.getInstance().getUploadDoc(id);
        List pname = QuoteService.getInstance().getUploadDocList(id);
        String uDoc = null;
        if (ud1 != null) {
            for (int i = 0; i < pname.size(); i++) {
                Map<String, String> FILENAME = new HashMap<String, String>();

                Upload_Doc ud = (Upload_Doc) pname.get(i);
                uDoc += ud.getPathname();
                FILENAME.put("FILENAME", ud.getPathname());
                FILELIST.add(FILENAME);
            }
        }

        rtfTemplate.put("FILELIST", FILELIST);

        //product
        rtfTemplate.put("PRODUCT_NAME", q.getProject().getProduct());
        rtfTemplate.put("PRODUCT_DESCRIPTION", q.getProject().getProductDescription().replaceAll(q.getProject().getProduct()+ " - ", "").replaceAll(q.getProject().getProduct()+ "-", ""));
//        try {
//            INDelivery iDelivery = InteqaService.getInstance().getINDelivery(q.getProject().getProjectId());
//            rtfTemplate.put("NOTES", iDelivery.getNotes());
//        } catch (Exception e) {
//        }

//task list
        //Description of Tasks Included in Pricing
        ArrayList tasksIncluded = new ArrayList();
        int tLangSize=0;
        List srcLanguageList = new ArrayList();

        List<Map> LANG_LIST = new ArrayList<>();
        
        if (q.getSourceDocs() != null) {
            for (Iterator iterSource = q.getSourceDocs().iterator(); iterSource.hasNext();) {
                SourceDoc sd = (SourceDoc) iterSource.next();
                Map<String, Object> INDLANG = new HashMap<>();
                
                if (sd.getTargetDocs() != null) {
                
                    if (!srcLanguageList.contains(sd.getLanguage())) {
                        srcLanguageList.add(sd.getLanguage());
                        INDLANG.put("SLANG",sd.getLanguage());
                        
                    }

                    List languageList = new ArrayList();

                    for (Iterator iterTarget = sd.getTargetDocs().iterator(); iterTarget.hasNext();) {
                        TargetDoc td = (TargetDoc) iterTarget.next();
                        if (!languageList.contains(td.getLanguage()) && !"All".equalsIgnoreCase(td.getLanguage())) {
                            languageList.add(td.getLanguage());

                        }

                        if (td.getLinTasks() != null) {
                            for (Iterator iterLin = td.getLinTasks().iterator(); iterLin.hasNext();) {
                                LinTask t = (LinTask) iterLin.next();
                                //check if in list
                                boolean isIn = false;
                                for (ListIterator li = tasksIncluded.listIterator(); li.hasNext();) {
                                    String displayText = (String) li.next();
                                    if (t.getTaskName().equals("Other")) { //use notes
                                        if (displayText.equals(t.getNotes()) && !isIn) {
                                            isIn = true;
                                        }
                                    } else //use taskName
                                    if (displayText.equals(t.getTaskName()) && !isIn) {
                                        isIn = true;
                                    }
                                }
                                if (!isIn) {
                                    if (t.getTaskName().equals("Other")) { //use notes
                                        tasksIncluded.add(t.getNotes());
                                    } else { //use taskName
                                        tasksIncluded.add(t.getTaskName());
                                    }
                                }
                            }
                        }//end linTasks

                        if (td.getEngTasks() != null) {
                            for (Iterator iterEng = td.getEngTasks().iterator(); iterEng.hasNext();) {
                                EngTask t = (EngTask) iterEng.next();
                                //check if in list
                                boolean isIn = false;
                                for (ListIterator li = tasksIncluded.listIterator(); li.hasNext();) {
                                    String displayText = (String) li.next();
                                    if (t.getTaskName().equals("Other")) { //use notes
                                        if (displayText.equals(t.getNotes()) && !isIn) {
                                            isIn = true;
                                        }
                                    } else //use taskName
                                    if (displayText.equals(t.getTaskName()) && !isIn) {
                                        isIn = true;
                                    }
                                }
                                if (!isIn) {
                                    if (t.getTaskName().equals("Other")) { //use notes
                                        tasksIncluded.add(t.getNotes());
                                    } else { //use taskName
                                        tasksIncluded.add(t.getTaskName());
                                    }
                                }
                            }
                        }//end engTasks

                        if (td.getDtpTasks() != null) {
                            for (Iterator iterDtp = td.getDtpTasks().iterator(); iterDtp.hasNext();) {
                                DtpTask t = (DtpTask) iterDtp.next();
                                //check if in list
                                boolean isIn = false;
                                for (ListIterator li = tasksIncluded.listIterator(); li.hasNext();) {
                                    String displayText = (String) li.next();
                                    if (t.getTaskName().equals("Other")) { //use notes
                                        if (displayText.equals(t.getNotes()) && !isIn) {
                                            isIn = true;
                                        }
                                    } else //use taskName
                                    if (displayText.equals(t.getTaskName()) && !isIn) {
                                        isIn = true;
                                    }
                                }
                                if (!isIn) {
                                    if (t.getTaskName().equals("Other")) { //use notes
                                        tasksIncluded.add(t.getNotes());
                                    } else { //use taskName
                                        tasksIncluded.add(t.getTaskName());
                                    }
                                }
                            }
                        }//end dtpTasks

                        if (td.getOthTasks() != null) {
                            for (Iterator iterOth = td.getOthTasks().iterator(); iterOth.hasNext();) {
                                OthTask t = (OthTask) iterOth.next();
                                //check if in list
                                boolean isIn = false;
                                for (ListIterator li = tasksIncluded.listIterator(); li.hasNext();) {
                                    String displayText = (String) li.next();
                                    if (t.getTaskName().equals("Other")) { //use notes
                                        if (displayText.equals(t.getNotes()) && !isIn) {
                                            isIn = true;
                                        }
                                    } else //use taskName
                                    if (displayText.equals(t.getTaskName()) && !isIn) {
                                        isIn = true;
                                    }
                                }
                                if (!isIn) {
                                    if (t.getTaskName().equals("Other")) { //use notes
                                        tasksIncluded.add(t.getNotes());
                                    } else { //use taskName
                                        tasksIncluded.add(t.getTaskName());
                                    }
                                }
                            }
                        }//end othTasks
                        
                    } 
                    
                    List<Map> TLANG_LIST = new ArrayList<>();

        tLangSize += languageList.size();
        double sizex = languageList.size();
        int size = (int) Math.ceil(sizex / 2);

        for (int i = 0; i < size; i++) {
            Map<String, String> TLANG = new HashMap<>();
            String tLang = (String) languageList.get(i);
            TLANG.put("LANG1", i + 1 + ". " + tLang);
            if (languageList.size() > i + size) {
                String tLang2 = (String) languageList.get(size + i);
                TLANG.put("LANG2", (i + 1 + size) + ". " + tLang2);
            }
            TLANG_LIST.add(TLANG);
        }
        
                    INDLANG.put("TLANG", TLANG_LIST);
                    LANG_LIST.add(INDLANG);
                }

            }
        }
        rtfTemplate.put("LANG_LIST", LANG_LIST);
        if(!tasksIncluded.contains("Project Management")) {
            tasksIncluded.add("Project Management");
        }
        
        List sequence = new ArrayList();
        List tasksequence = new ArrayList();
        sequence.add("Translation");
        sequence.add("Editing");
        sequence.add("DTP");
        sequence.add("Proofing");
        sequence.add("FQA");
        sequence.add("Project Management");
        
//        for(int i = 0; i < tasksIncluded.size() ;i++){
//            boolean rem = true;
//        if(sequence.contains(tasksIncluded.get(i))){
//            sequence.remove(tasksIncluded.get(i));  
//        }
//        }
        for(int i = 0; i < sequence.size() ;i++){
            boolean rem = true;
        if(tasksIncluded.contains(sequence.get(i))){
            tasksIncluded.remove(sequence.get(i));  
            tasksequence.add(sequence.get(i));
        }
        }
        tasksequence.addAll(tasksIncluded);
        tasksIncluded.clear();
        tasksIncluded.addAll(tasksequence);
        
        List<Map> TASK_LIST = new ArrayList<>();
        int counter = 1;
        double sizex = tasksIncluded.size();
        int size = (int) Math.ceil(sizex/2);
        
        for(int i = 0;i<size; i++){
        Map<String, String> TASK = new HashMap<>();
        String task1 = (String) tasksIncluded.get(i);
        if(task1.trim().equalsIgnoreCase("Proofreading"))task1="Proofing";
            
            TASK.put("TASK1",i+1 +". "+ task1);
            if (tasksIncluded.size()>i+size) {
                String task2 = (String) tasksIncluded.get(size+i);
                if(task2.trim().equalsIgnoreCase("Proofreading"))task2="Proofing";
                TASK.put("TASK2", (i+1 + size) +". "+task2);
            }
            TASK_LIST.add(TASK);
            counter++;
        }

        
        rtfTemplate.put("TASKLIST", TASK_LIST);

//        List<Map> LANG_LIST = new ArrayList<>();
        rtfTemplate.put("LANGUAGELISTSIZE",tLangSize);
        
        //START find billing details (tasks and changes)
        //get this project's sources
        Set sourceList = q.getSourceDocs();

        //for each source add each sources' Tasks
        java.util.List totalLinTasks = new java.util.ArrayList();
        java.util.List totalEngTasks = new java.util.ArrayList();
        java.util.List totalDtpTasks = new java.util.ArrayList();
        java.util.List totalOthTasks = new java.util.ArrayList();

        //for each source
        for (Iterator sourceIter = sourceList.iterator(); sourceIter.hasNext();) {
            SourceDoc sd = (SourceDoc) sourceIter.next();

            //for each target of this source
            for (Iterator linTargetIter = sd.getTargetDocs().iterator(); linTargetIter.hasNext();) {
                TargetDoc td = (TargetDoc) linTargetIter.next();

                //for each lin Task of this target
                for (Iterator linTaskIter = td.getLinTasks().iterator(); linTaskIter.hasNext();) {
                    LinTask lt = (LinTask) linTaskIter.next();
                    totalLinTasks.add(lt);
                }

                //for each eng Task of this target
                for (Iterator engTaskIter = td.getEngTasks().iterator(); engTaskIter.hasNext();) {
                    EngTask et = (EngTask) engTaskIter.next();
                    totalEngTasks.add(et);
                }

                //for each dtp Task of this target
                for (Iterator dtpTaskIter = td.getDtpTasks().iterator(); dtpTaskIter.hasNext();) {
                    DtpTask dt = (DtpTask) dtpTaskIter.next();
                    totalDtpTasks.add(dt);
                }

                //for each oth Task of this target
                for (Iterator othTaskIter = td.getOthTasks().iterator(); othTaskIter.hasNext();) {
                    OthTask ot = (OthTask) othTaskIter.next();
                    totalOthTasks.add(ot);
                }
            }
        }

        //array for display in jsp
        LinTask[] linTasksArray = (LinTask[]) totalLinTasks.toArray(new LinTask[0]);
        EngTask[] engTasksArray = (EngTask[]) totalEngTasks.toArray(new EngTask[0]);
        DtpTask[] dtpTasksArray = (DtpTask[]) totalDtpTasks.toArray(new DtpTask[0]);
        OthTask[] othTasksArray = (OthTask[]) totalOthTasks.toArray(new OthTask[0]);

        //find total of LinTasks
        double linTaskTotal = 0;
        double linTaskTotalIcr = 0;
        boolean gotFirstLin = false;
        Double linVolume = 0.0;
        for (int i = 0; i < linTasksArray.length; i++) {
            if (!linTasksArray[i].getTaskName().equalsIgnoreCase("ICR") && !linTasksArray[i].getTaskName().equalsIgnoreCase("ICR ") && !linTasksArray[i].getTaskName().equalsIgnoreCase("In-Country Review (ICR)") && !linTasksArray[i].getTaskName().equalsIgnoreCase("In-Country / Client Review")) {

                if (!gotFirstLin && linTasksArray[i].getWordTotal() != null) {
                    linVolume = linTasksArray[i].getWordTotal();
                    gotFirstLin = true;
                }
                if (linTasksArray[i].getDollarTotal() != null) {
                    //remove comma's
                    String linTotal = linTasksArray[i].getDollarTotal();
                    linTotal = linTotal.replaceAll(",", "");
                    Double total = Double.valueOf(linTotal);
                    linTaskTotal += total.doubleValue();
                }
            } else {
                if (!gotFirstLin && linTasksArray[i].getWordTotal() != null) {
                    linVolume = linTasksArray[i].getWordTotal();
                    gotFirstLin = true;
                }
                if (linTasksArray[i].getDollarTotal() != null) {
                    //remove comma's
                    String linTotalIcr = linTasksArray[i].getDollarTotal();
                    linTotalIcr = linTotalIcr.replaceAll(",", "");
                    Double totalIcr = Double.valueOf(linTotalIcr);
                    linTaskTotalIcr += totalIcr.doubleValue();
                }
            }
        }
        //find total of EngTasks
        double engTaskTotal = 0;
        double engVolume = 0.0;
        for (int i = 0; i < engTasksArray.length; i++) {
            if (engTasksArray[i].getTotal() != null) {
                engVolume += engTasksArray[i].getTotal().doubleValue();
            }
            if (engTasksArray[i].getDollarTotal() != null) {
                //remove comma's
                String engTotal = engTasksArray[i].getDollarTotal();
                engTotal = engTotal.replaceAll(",", "");
                Double total = Double.valueOf(engTotal);
                engTaskTotal += total.doubleValue();
            }
        }

        //find total of DtpTasks
        double dtpTaskTotal = 0;
        double dtpVolume = 0.0;
        for (int i = 0; i < dtpTasksArray.length; i++) {
            if (dtpTasksArray[i].getTotal() != null) {
                dtpVolume += dtpTasksArray[i].getTotal().doubleValue();
            }
            if (dtpTasksArray[i].getDollarTotal() != null) {
                //remove comma's
                String dtpTotal = dtpTasksArray[i].getDollarTotal();
                dtpTotal = dtpTotal.replaceAll(",", "");
                Double total = Double.valueOf(dtpTotal);
                dtpTaskTotal += total.doubleValue();
            }
        }

        //find total of OthTasks
        double othTaskTotal = 0;
        double othVolume = 0.0;
        for (int i = 0; i < othTasksArray.length; i++) {
            if (othTasksArray[i].getTotal() != null) {
                othVolume += othTasksArray[i].getTotal().doubleValue();
            }
            if (othTasksArray[i].getDollarTotal() != null) {
                //remove comma's
                String othTotal = othTasksArray[i].getDollarTotal();
                othTotal = othTotal.replaceAll(",", "");
                Double total = Double.valueOf(othTotal);
                othTaskTotal += total.doubleValue();
            }
        }

        //quote TOTAL
        double quoteTotal = 0;
        if (q.getQuoteDollarTotal() != null) {
            quoteTotal = q.getQuoteDollarTotal();
        }
        
         double discount = 0.00;
        if(null != q.getDiscountDollarTotal()){
        discount = new Double(q.getDiscountDollarTotal().replaceAll(",", ""));
        if(discount>0.00){
         rtfTemplate.put("DISCOUNT",  StandardCode.getInstance().formatDouble(discount));
        }
        }

        //find pm total
        double pmTotal = 0;
        try {
            String strPmSubTotal = q.getSubDollarTotal().replaceAll(",", "");
            if (q.getQuoteDollarTotal() != null) {
                pmTotal = q.getQuoteDollarTotal() - (new Double(strPmSubTotal))+discount;
            }
        } catch (Exception e) {
        }

        if (linTaskTotalIcr != 0) {

            rtfTemplate.put("ICRTASK", "ICR");
            rtfTemplate.put("ICRPRICE", "\\$" + StandardCode.getInstance().formatDouble(linTaskTotalIcr));
        } else {
            rtfTemplate.put("ICRTASK", "");
            rtfTemplate.put("ICRPRICE", "");
        }

        rtfTemplate.put("LINPRICE",  StandardCode.getInstance().formatDouble(linTaskTotal));
        rtfTemplate.put("DTPPRICE",  StandardCode.getInstance().formatDouble(dtpTaskTotal));
        rtfTemplate.put("ENGPRICE", StandardCode.getInstance().formatDouble(engTaskTotal));
        rtfTemplate.put("PMPRICE", StandardCode.getInstance().formatDouble(pmTotal));
//        rtfTemplate.put("OTHPRICE", StandardCode.getInstance().formatDouble(new Double(othTaskTotal)));
        rtfTemplate.put("TOTALPRICE",  StandardCode.getInstance().formatDouble(quoteTotal));
        rtfTemplate.put("bd_grand_total", StandardCode.getInstance().formatDouble(quoteTotal));

        rtfTemplate.put("CURRENCY", c.getCcurrency());
         
        
        //Language breakdown code
        
        
         List<Map> ITEMIZEDCOST = new ArrayList<Map>();
        Hashtable htBreakdown = QuoteHelper.getBreakdownByLanguageTable(q);
        double totalfee = Double.parseDouble(StandardCode.getInstance().noNull((String) htBreakdown.get("bd_grand_total"))
                        .replaceAll(",", "").replaceAll("\\$", "").replaceAll("\\\\", ""));
       
        double totalFeePerLang =(Double.parseDouble(StandardCode.getInstance().noNull((String) htBreakdown.get("bd_pm_fee")).replaceAll(",", "").replaceAll("\\$", "").replaceAll("\\\\", ""))
               -Double.parseDouble(StandardCode.getInstance().noNull((String) htBreakdown.get("bd_discount")).replaceAll(",", "").replaceAll("\\$", "").replaceAll("\\\\", ""))
               +Double.parseDouble(StandardCode.getInstance().noNull((String) htBreakdown.get("bd_rush_fee")).replaceAll(",", "").replaceAll("\\$", "").replaceAll("\\\\", "")));
         double allLangCost=0.00;
         
        for (int i = 1; i <= 35; i++) {
            String currentCounter = i + "";
            if (i < 10) {
                currentCounter = "0" + i;
            }
            Map<String, String> LANG = new HashMap<String, String>();
           
            String language = StandardCode.getInstance().noNull((String) htBreakdown.get("bd_lang_" + currentCounter));
            String languageCombo = StandardCode.getInstance().noNull((String) htBreakdown.get("bd_langCombo_" + currentCounter));
            if ("All".equalsIgnoreCase(language)) {
                allLangCost = Double.parseDouble(StandardCode.getInstance().noNull
        ((String) htBreakdown.get("bd_total_" + currentCounter)).replaceAll(",", "").replaceAll("\\$", "").replaceAll("\\\\", ""))/tLangSize;
                System.err.println("langCost"+allLangCost);
                
            }
        }
        for (int i = 1; i <= 35; i++) {
            String currentCounter = i + "";
            if (i < 10) {
                currentCounter = "0" + i;
            }
            Map<String, String> LANG = new HashMap<String, String>();
            
            String language = StandardCode.getInstance().noNull((String) htBreakdown.get("bd_lang_" + currentCounter));
            String languageCombo = StandardCode.getInstance().noNull((String) htBreakdown.get("bd_langCombo_" + currentCounter));
            if (!"".equalsIgnoreCase(language)&&!"All".equalsIgnoreCase(language)) {
                double langCostWoFee = Double.parseDouble(StandardCode.getInstance().noNull((String) htBreakdown.get("bd_total_" + currentCounter))
                        .replaceAll(",", "").replaceAll("\\$", "").replaceAll("\\\\", ""))+allLangCost;
                double partTotalFeePerLang =1-(totalfee - langCostWoFee-totalFeePerLang)/(totalfee-totalFeePerLang);
                double langCost = totalfee*partTotalFeePerLang;
//                langCost = (langCost+allLangCost)+((Double.parseDouble(q.getPmPercent()) / 100)*(langCost+allLangCost));
                
                if(srcLanguageList.size()==1)
                    LANG.put("LANG", StandardCode.getInstance().noNull((String) htBreakdown.get("bd_lang_" + currentCounter)));
                else
                    LANG.put("LANG", StandardCode.getInstance().noNull(languageCombo));
//            rtfTemplate.put("bd_lin_" + currentCounter, StandardCode.getInstance().noNull((String) htBreakdown.get("bd_lin_" + currentCounter)));
//            rtfTemplate.put("bd_dtp_" + currentCounter, StandardCode.getInstance().noNull((String) htBreakdown.get("bd_dtp_" + currentCounter)));
//            rtfTemplate.put("bd_eng_" + currentCounter, StandardCode.getInstance().noNull((String) htBreakdown.get("bd_eng_" + currentCounter)));
                LANG.put("TOTALLANG", StandardCode.getInstance().formatDouble3(langCost));
                ITEMIZEDCOST.add(LANG);
            }
        }
        rtfTemplate.put("ITEMIZEDCOST", ITEMIZEDCOST);

        rtfTemplate.put("bd_pm_fee", StandardCode.getInstance().noNull((String) htBreakdown.get("bd_pm_fee")));
        rtfTemplate.put("bd_rush_fee", StandardCode.getInstance().noNull((String) htBreakdown.get("bd_rush_fee")));
        rtfTemplate.put("bd_discount", StandardCode.getInstance().noNull((String) htBreakdown.get("bd_discount")));
        rtfTemplate.put("bd_grand_total",  StandardCode.getInstance().noNull((String) htBreakdown.get("bd_grand_total")));
//       double totalFeePerLang =(Double.parseDouble(StandardCode.getInstance().noNull((String) htBreakdown.get("bd_grand_total")).replaceAll(",", ""))
//               -Double.parseDouble(StandardCode.getInstance().noNull((String) htBreakdown.get("bd_pm_fee")).replaceAll(",", ""))
//               +Double.parseDouble(StandardCode.getInstance().noNull((String) htBreakdown.get("bd_discount")).replaceAll(",", ""))
//               -Double.parseDouble(StandardCode.getInstance().noNull((String) htBreakdown.get("bd_rush_fee")).replaceAll(",", "")))/languageList.size();
//        languageList.size()

        try {
            rtfTemplate.put("LEADTIME", StandardCode.getInstance().noNull(q.getProject().getBeforeWorkTurn()));
        } catch (Exception e) {
            rtfTemplate.put("LEADTIME", "");

        }
        try {
            rtfTemplate.put("LEADTIMEUNITS", StandardCode.getInstance().noNull(q.getProject().getBeforeWorkTurnUnits().toLowerCase()));
        } catch (Exception e) {
            rtfTemplate.put("LEADTIMEUNITS", "");
        }
        rtfTemplate.put("REFERENCE", StandardCode.getInstance().noNull(q.getNumber()));

        String rtfTarget = q.getNumber() + "-" + q.getProject().getCompany().getCompany_name().replaceAll(" ", "_").replaceAll(",", "_") + "_quote.doc";
        rtfTemplate.put("QUOTEFILENAME", q.getNumber() + "-" + q.getProject().getCompany().getCompany_name().replaceAll(" ", "_").replaceAll(",", "_") + "_quote");

        // 5. Merge the RTF source model and the context  
        rtfTemplate.merge(rtfTarget);
        //System.out.println(rtfTemplate.getTransformedDocument().toString());

        try {
//            String fname = "C:/PO" + p.getNumber() + p.getCompany().getCompany_code() + ".zip";
            String strHeader = "Attachment;Filename=" + rtfTarget;
            response.setHeader("Content-Disposition", strHeader);
            java.io.File fileToDownload = new java.io.File(rtfTarget);
            FileInputStream fileInputStream = new FileInputStream(fileToDownload);
            int i;
            OutputStream out = response.getOutputStream();
            while ((i = fileInputStream.read()) != -1) {
                out.write(i);
            }
            fileInputStream.close();
            out.close();

        } catch (Exception e) // file IO errors
        {
            e.printStackTrace();
        }
        deleteFile(rtfTarget);
//        saveFileFromUrlWithJavaIO("Q123456.docx",rtfTarget);
        //write to client (web browser)
//        java.io.File output = new java.io.File(rtfTarget);
//        response.setHeader("Content-Type", "Application/msword");
//        response.setContentLength((int) output.length());  
////        response.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
////        String filename = q.getNumber() + "-" + q.getProject().getCompany().getCompany_name().replaceAll(" ", "_").replaceAll(",", "_") + "_quote.doc";
//        response.setHeader("Content-disposition", "attachment; filename=" + rtfTarget);
//        OutputStream os = response.getOutputStream();
//        os.write(saveFileFromUrlWithJavaIO("Q123456.docx",rtfTarget));
//        os.flush();

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }

    // Using Java IO
    public static byte[] saveFileFromUrlWithJavaIO(String fileName, String fileUrl)
            throws MalformedURLException, IOException {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        byte data[] = new byte[1024];
        try {
            in = new BufferedInputStream(new FileInputStream(fileUrl));
            fout = new FileOutputStream(fileName);

            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
        return data;
    }

    public static boolean deleteFile(String sFilePath) {
        java.io.File oFile = new java.io.File(sFilePath);
        if (oFile.isDirectory()) {
            java.io.File[] aFiles = oFile.listFiles();
            for (java.io.File oFileCur : aFiles) {
                deleteFile(oFileCur.getAbsolutePath());
            }
        }
        return oFile.delete();
    }

}
