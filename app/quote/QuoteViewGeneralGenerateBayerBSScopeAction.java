/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.quote;

import app.client.Client;
import app.client.ClientContact;
import app.client.ClientService;
import app.extjs.vo.Product;
import app.extjs.vo.Upload_Doc;
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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.rtf.RTFTemplate;
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
public class QuoteViewGeneralGenerateBayerBSScopeAction extends Action {

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

        //START get id of current quote from either request, attribute, or cookie 
        //id of quote from request
        String quoteId = request.getParameter("quoteViewId");

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
        
        String type = request.getParameter("qtype");

        //END get id of current quote from either request, attribute, or cookie               
        //get quote to edit
        Quote1 q = QuoteService.getInstance().getSingleQuote(id);

        String rtfSource = QuoteTemplateHelper.templatePath+"BayerBSScope_dt20Jan2019.rtf";
   
        
        RTFTemplate rtfTemplate = QuoteTemplateHelper.getInstance().initializeRTFTemplate(new java.io.File(rtfSource));


        Client c = q.getProject().getCompany();

        //START content
        rtfTemplate.put("DATE", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));

        rtfTemplate.put("COMPANYNAME", q.getProject().getCompany().getCompany_name());
        try {
            rtfTemplate.put("CONTACTNAME", StandardCode.getInstance().noNull(q.getProject().getContact().getFirst_name()) + " " + StandardCode.getInstance().noNull(q.getProject().getContact().getLast_name()));
            rtfTemplate.put("CONTACTTITLE", StandardCode.getInstance().noNull(q.getProject().getContact().getTitle()));
            String comma = ", ";
            if ("".equalsIgnoreCase(StandardCode.getInstance().noNull(q.getProject().getContact().getDivision()))) {
                comma = "";
            }
            rtfTemplate.put("CONTACTADDRESS1", StandardCode.getInstance().noNull(q.getProject().getContact().getDivision()) + comma + StandardCode.getInstance().noNull(q.getProject().getContact().getAddress_1()));
            rtfTemplate.put("CONTACTCITY", StandardCode.getInstance().noNull(q.getProject().getContact().getCity()));
            rtfTemplate.put("CONTACTSTATE", StandardCode.getInstance().noNull(q.getProject().getContact().getState_prov()));
            rtfTemplate.put("CONTACTZIPCODE", StandardCode.getInstance().noNull(q.getProject().getCompany().getZip_postal_code()));
            if (q.getProject().getContact().getWorkPhoneEx() != null && q.getProject().getContact().getWorkPhoneEx().length() > 0) { //ext present
                rtfTemplate.put("CONTACTPHONE", StandardCode.getInstance().noNull(q.getProject().getContact().getTelephone_number()) + " ext " + StandardCode.getInstance().noNull(q.getProject().getContact().getWorkPhoneEx()));
            } else { //no ext present
                rtfTemplate.put("CONTACTPHONE", StandardCode.getInstance().noNull(q.getProject().getContact().getTelephone_number()));
            }
            rtfTemplate.put("CONTACTFAX", StandardCode.getInstance().noNull(q.getProject().getContact().getFax_number()));
            rtfTemplate.put("CONTACTEMAIL", StandardCode.getInstance().noNull(q.getProject().getContact().getEmail_address()));

        } catch (Exception e) {
            try {

                User u = UserService.getInstance().getSingleUser(q.getEnteredById());
                ClientContact cc = ClientService.getInstance().getSingleClientContact(u.getClient_contact());
                rtfTemplate.put("CONTACTNAME", StandardCode.getInstance().noNull(cc.getFirst_name()) + " " + StandardCode.getInstance().noNull(cc.getLast_name()));
                rtfTemplate.put("CONTACTTITLE", StandardCode.getInstance().noNull(" "));
                rtfTemplate.put("CONTACTADDRESS1", StandardCode.getInstance().noNull(cc.getAddress_1()));
                rtfTemplate.put("CONTACTCITY", StandardCode.getInstance().noNull(cc.getCity()));
                rtfTemplate.put("CONTACTSTATE", StandardCode.getInstance().noNull(cc.getState_prov()));
                rtfTemplate.put("CONTACTZIPCODE", StandardCode.getInstance().noNull(cc.getZip_postal_code()));

                if (cc.getTelephone_number() != null && cc.getTelephone_number().length() > 0) { //ext present
                    rtfTemplate.put("CONTACTPHONE", StandardCode.getInstance().noNull(cc.getTelephone_number()) + " ext " + StandardCode.getInstance().noNull(cc.getWorkPhoneEx()));
                } else { //no ext present
                    rtfTemplate.put("CONTACTPHONE", StandardCode.getInstance().noNull(cc.getCell_phone_number()));
                }
                rtfTemplate.put("CONTACTFAX", StandardCode.getInstance().noNull(""));
                rtfTemplate.put("CONTACTEMAIL", StandardCode.getInstance().noNull(cc.getEmail_address()));

            } catch (Exception e1) {
                try {
                    User u = UserService.getInstance().getSingleUser(q.getEnteredById());
                    Client client = ClientService.getInstance().getClient(u.getID_Client());
                    rtfTemplate.put("CONTACTNAME", StandardCode.getInstance().noNull(u.getFirstName()) + " " + StandardCode.getInstance().noNull(u.getLastName()));
                    rtfTemplate.put("CONTACTTITLE", StandardCode.getInstance().noNull(" "));
                    rtfTemplate.put("CONTACTADDRESS1", StandardCode.getInstance().noNull(client.getAddress_1()));
                    rtfTemplate.put("CONTACTCITY", StandardCode.getInstance().noNull(client.getCity()));
                    rtfTemplate.put("CONTACTSTATE", StandardCode.getInstance().noNull(client.getState_prov()));
                    rtfTemplate.put("CONTACTZIPCODE", StandardCode.getInstance().noNull(client.getZip_postal_code()));

                    rtfTemplate.put("CONTACTPHONE", StandardCode.getInstance().noNull(client.getWorkPhoneEx()));
                  rtfTemplate.put("CONTACTFAX", StandardCode.getInstance().noNull(""));
                    rtfTemplate.put("CONTACTEMAIL", StandardCode.getInstance().noNull(client.getEmail_address()));
                } catch (Exception e2) {

                    User u = UserService.getInstance().getSingleUser(q.getEnteredById());

                    rtfTemplate.put("CONTACTNAME", StandardCode.getInstance().noNull(u.getFirstName()) + " " + StandardCode.getInstance().noNull(u.getLastName()));
                    rtfTemplate.put("CONTACTTITLE", StandardCode.getInstance().noNull(" "));
                    rtfTemplate.put("CONTACTADDRESS1", StandardCode.getInstance().noNull(u.getWorkAddress()));
                    rtfTemplate.put("CONTACTCITY", StandardCode.getInstance().noNull(u.getWorkCity()));
                    rtfTemplate.put("CONTACTSTATE", StandardCode.getInstance().noNull(u.getWorkState()));
                    rtfTemplate.put("CONTACTZIPCODE", StandardCode.getInstance().noNull(u.getWorkZip()));

                    if (u.getWorkPhoneEx() != null && u.getWorkPhoneEx().length() > 0) { //ext present
                        rtfTemplate.put("CONTACTPHONE", StandardCode.getInstance().noNull(u.getWorkPhone()) + " ext " + StandardCode.getInstance().noNull(u.getWorkPhoneEx()));
                    } else { //no ext present
                        rtfTemplate.put("CONTACTPHONE", StandardCode.getInstance().noNull(u.getWorkPhone()));
                    }
                    rtfTemplate.put("CONTACTFAX", StandardCode.getInstance().noNull(""));
                    rtfTemplate.put("CONTACTEMAIL", StandardCode.getInstance().noNull(u.getWorkEmail1()));
                }
            }
        }
        try{
            rtfTemplate.put("CARETAKER", StandardCode.getInstance().noNull(q.getProject().getCareTaker().getFirst_name()) + " " + StandardCode.getInstance().noNull(q.getProject().getCareTaker().getLast_name()));
        }catch(Exception e){
            rtfTemplate.put("CARETAKER","");
        }
                
        StringBuilder files = new StringBuilder("");
        if (q.getFiles() != null) {
            for (Iterator iter = q.getFiles().iterator(); iter.hasNext();) {
                File f = (File) iter.next();
                if (f.getBeforeAnalysis().equals("false")) {
                    files.append(StandardCode.getInstance().noNull(f.getFileName() + ", "));
                }
            }
        }

        String med = "";
        String detail = "";
        String category = "";
        List quoteList = QuoteService.getInstance().getSingleClientQuote(id);
        for (int i = 0; i < quoteList.size(); i++) {
            Client_Quote cq = (Client_Quote) quoteList.get(i);
            Product prod = ClientService.getSingleProduct(cq.getProduct_ID());
            detail += cq.getType();
            med += StandardCode.getInstance().noNull(cq.getMedical());
            category += prod.getCategory();

            if (i != quoteList.size() - 1) {
                detail += ",";
                med += ",";
                category += ",";
            }
        }
        //System.out.println("ddddddddddddddddddddd" + category + med);
        Upload_Doc ud1 = QuoteService.getInstance().getUploadDoc(id);
        List pname = QuoteService.getInstance().getUploadDocList(id);
        String uDoc = null;
        if (ud1 != null) {
            for (int i = 0; i < pname.size(); i++) {
                Upload_Doc ud = (Upload_Doc) pname.get(i);
                uDoc += ud.getPathname();
                rtfTemplate.put("FILE_DATE", "" + ud.getUploadDate());
                if (i != pname.size() - 1) {
                    uDoc += ",";
                }
//File No.=i+1  :
            }
        }
        rtfTemplate.put("FILE_DATE", "");
        //rtfTemplate.put("FILELIST", StandardCode.getInstance().noNull(files.toString()));
        rtfTemplate.put("PRODUCT_HERE", StandardCode.getInstance().noNull(q.getProject().getProduct()));
//        rtfTemplate.put("CATEGORY_HERE", StandardCode.getInstance().noNull(category));
        rtfTemplate.put("CATEGORY_HERE", "");
        rtfTemplate.put("TYPE_HERE", StandardCode.getInstance().noNull(med));
        rtfTemplate.put("DETAIL_HERE", StandardCode.getInstance().noNull(detail));

//FILELIST
        rtfTemplate.put("FILELIST", StandardCode.getInstance().noNull(uDoc));

        List<Map> NOTES = new ArrayList<>();

        INDelivery indel = InteqaService.getInstance().getINDelivery(q.getProject().getProjectId());
        if (indel != null) {
            List inDel = InteqaService.getInstance().getInDeliveryReqGrid(indel.getId(), "R");
            for (int i = 0; i < inDel.size(); i++) {
                Map<String, String> NOTE = new HashMap<>();
                INDeliveryReq inDeliveryReq = (INDeliveryReq) inDel.get(i);
                NOTE.put("NOTE", inDeliveryReq.getClientReqText());
                NOTES.add(NOTE);

            }
        }
        rtfTemplate.put("NOTES", NOTES);

        Double linPrice = 0.0;
        Double linAmt = 0.0;
//        Double dtpAmt=0.0;
        Double engAmt = 0.0;
        Double othAmt = 0.0;
        Double linTaskIcr = 0.0;
        // Integer counter=1;

        Double Rate_MultiLingual = 0.0;
        Double Count_MultiLingual = 0.0;
        Double Cost_MultiLingual = 0.0;
        Double Total_Cost = 0.0, Total_Word = 0.0;

        //Description of Tasks Included in Pricing
        ArrayList tasksIncludedLin = new ArrayList();
        ArrayList tasksIncludedEng = new ArrayList();
        ArrayList tasksIncludedDtp = new ArrayList();
        ArrayList tasksIncludedOth = new ArrayList();
        if (q.getSourceDocs() != null) {
            for (Iterator iterSource = q.getSourceDocs().iterator(); iterSource.hasNext();) {
                SourceDoc sd = (SourceDoc) iterSource.next();
                if (sd.getTargetDocs() != null) {
                    for (Iterator iterTarget = sd.getTargetDocs().iterator(); iterTarget.hasNext();) {
                        TargetDoc td = (TargetDoc) iterTarget.next();

                        if (!td.getLanguage().equalsIgnoreCase("ALL")) {
                            linAmt = 0.0;
//                        dtpAmt=0.0;
                            engAmt = 0.0;
                            othAmt = 0.0;
                            linTaskIcr = 0.0;

                            if (td.getLinTasks() != null) {
                                for (Iterator iterLin = td.getLinTasks().iterator(); iterLin.hasNext();) {
                                    LinTask t = (LinTask) iterLin.next();
                                    //check if in list
                                    boolean isIn = false;
                                    for (ListIterator li = tasksIncludedLin.listIterator(); li.hasNext();) {
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
                                            tasksIncludedLin.add(t.getNotes());
                                        } else { //use taskName
                                            tasksIncludedLin.add(t.getTaskName());
                                        }
                                    }
                                }
                            }//end linTasks

                            if (td.getEngTasks() != null) {
                                for (Iterator iterEng = td.getEngTasks().iterator(); iterEng.hasNext();) {
                                    EngTask t = (EngTask) iterEng.next();
                                    //check if in list
                                    boolean isIn = false;
                                    for (ListIterator li = tasksIncludedEng.listIterator(); li.hasNext();) {
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
                                            tasksIncludedEng.add(t.getNotes());
                                        } else { //use taskName
                                            tasksIncludedEng.add(t.getTaskName());
                                        }
                                    }
                                }
                            }//end engTasks

                            if (td.getDtpTasks() != null) {
                                for (Iterator iterDtp = td.getDtpTasks().iterator(); iterDtp.hasNext();) {
                                    DtpTask t = (DtpTask) iterDtp.next();
                                    //check if in list
                                    boolean isIn = false;
                                    for (ListIterator li = tasksIncludedDtp.listIterator(); li.hasNext();) {
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
                                            tasksIncludedDtp.add(t.getNotes());
                                        } else { //use taskName
                                            tasksIncludedDtp.add(t.getTaskName());
                                        }
                                    }
                                }
                            }//end dtpTasks

                            if (td.getOthTasks() != null) {
                                for (Iterator iterOth = td.getOthTasks().iterator(); iterOth.hasNext();) {
                                    OthTask t = (OthTask) iterOth.next();
                                    //check if in list
                                    boolean isIn = false;
                                    for (ListIterator li = tasksIncludedOth.listIterator(); li.hasNext();) {
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
                                            tasksIncludedOth.add(t.getNotes());
                                        } else { //use taskName
                                            tasksIncludedOth.add(t.getTaskName());
                                        }
                                    }
                                }
                            }//end othTasks

                            if (othAmt != 0) {

                                rtfTemplate.put("OTHER", "Other (specify)");
                                rtfTemplate.put("OTHPRICE", "\\$" + StandardCode.getInstance().formatDouble(new Double(othAmt)));
                            } else {
                                rtfTemplate.put("OTHER", "");
                                rtfTemplate.put("OTHPRICE", "");
                            }

                        } else {
                            flag = 1;

                            for (Iterator dtpTaskIter = td.getDtpTasks().iterator(); dtpTaskIter.hasNext();) {
                                DtpTask dt = (DtpTask) dtpTaskIter.next();
                                if (dt.getTaskName().contains("Multilingual")) {
                                    Rate_MultiLingual = Double.parseDouble(dt.getRate());
                                    Count_MultiLingual = dt.getTotalTeam();
                                    Cost_MultiLingual = Rate_MultiLingual * Count_MultiLingual;
                                }
                            }
                        }
                    }
                }
            }
        }

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
        
        if (!tasksIncluded.contains("Project Management")) {
            tasksIncluded.add("Project Management");
        }
// 

        List sequence = new ArrayList();
        List tasksequence = new ArrayList();
        sequence.add("Translation");
        sequence.add("Editing");
        sequence.add("DTP");
        sequence.add("Proofing");
        sequence.add("FQA");
        sequence.add("Project Management");

        for (int i = 0; i < sequence.size(); i++) {
            if (tasksIncluded.contains(sequence.get(i))) {
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
        int size = (int) Math.ceil(sizex / 2);

        for (int i = 0; i < size; i++) {
            Map<String, String> TASK = new HashMap<>();
            String task1 = (String) tasksIncluded.get(i);
            if (task1.trim().equalsIgnoreCase("Proofreading")) {
                task1 = "Proofing";
            }

            TASK.put("TASK1", i + 1 + ". " + task1);
            if (tasksIncluded.size() > i + size) {
                String task2 = (String) tasksIncluded.get(size + i);
                if (task2.trim().equalsIgnoreCase("Proofreading")) {
                    task2 = "Proofing";
                }
                TASK.put("TASK2", (i + 1 + size) + ". " + task2);
            }
            TASK_LIST.add(TASK);
            counter++;
        }
        rtfTemplate.put("TASKLIST", TASK_LIST);
//        List<Map> LANG_LIST = new ArrayList<>();

        rtfTemplate.put("LANGUAGELISTSIZE", tLangSize);


        rtfTemplate.put("DELIVER", StandardCode.getInstance().noNull(q.getProject().getDeliverableTechNotes()));

        rtfTemplate.put("CURRENCY", StandardCode.getInstance().noNull("USD"));
        rtfTemplate.put("REFERENCE", StandardCode.getInstance().noNull(q.getNumber()));
        try {
            rtfTemplate.put("LEADTIME", StandardCode.getInstance().noNull(q.getProject().getBeforeWorkTurn()));
            rtfTemplate.put("LEADTIMEUNITS", StandardCode.getInstance().noNull(q.getProject().getBeforeWorkTurnUnits().toLowerCase()));

        } catch (Exception e) {
            rtfTemplate.put("LEADTIME", "");
            rtfTemplate.put("LEADTIMEUNITS", "");
            rtfTemplate.put("REFERENCE", "");
        }

        try {
            rtfTemplate.put("LEADTIMEAFTER", StandardCode.getInstance().noNull(q.getProject().getAfterWorkTurn()));
            rtfTemplate.put("LEADTIMEUNITSAFTER", StandardCode.getInstance().noNull(q.getProject().getAfterWorkTurnUnits().toLowerCase()));

        } catch (Exception e) {

            rtfTemplate.put("LEADTIMEAFTER", "");
            rtfTemplate.put("LEADTIMEUNITSAFTER", "");

        }

        String faxno = "";
        try {
//            String[] fname = q.getProject().getPm().split(" ");
            User enteredBy = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
            try {
                if (enteredBy.getUserType().equalsIgnoreCase("client")) {
                    faxno = ClientService.getInstance().getClient(enteredBy.getId_client()).getFax_number();
                }
            } catch (Exception e) {
                faxno = enteredBy.getLocation().getFax_number();
            }
            try {
                rtfTemplate.put("PMNAME", StandardCode.getInstance().noNull(enteredBy.getFirstName()) + " " + StandardCode.getInstance().noNull(enteredBy.getLastName()));
            } catch (Exception e) {
            }

            try {
                rtfTemplate.put("PM_TITLE", StandardCode.getInstance().noNull(enteredBy.getPosition().getPosition()));

            } catch (Exception e) {
            }
            rtfTemplate.put("PM_TITLE", "");
            rtfTemplate.put("EMAIL_PM", StandardCode.getInstance().noNull(enteredBy.getWorkEmail1()));
            rtfTemplate.put("PHONE_EXTENSION_PM", StandardCode.getInstance().noNull(enteredBy.getWorkPhone()) + " ext " + StandardCode.getInstance().noNull(enteredBy.getWorkPhoneEx()));

            rtfTemplate.put("EXCELNAME", "Excel Translations, Inc. ");
            rtfTemplate.put("EXCELADDRESS1", StandardCode.getInstance().noNull(enteredBy.getLocation().getAddress_1()));
            rtfTemplate.put("EXCELADDRESS2", StandardCode.getInstance().noNull(enteredBy.getLocation().getAddress_2()));
            rtfTemplate.put("EXCELADDRESS3", StandardCode.getInstance().noNull(enteredBy.getLocation().getCity() + ", "
                    + enteredBy.getLocation().getState_prov() + " " + enteredBy.getLocation().getZip_postal_code() + ", " + enteredBy.getLocation().getCountry()));

        } catch (Exception e) {

            rtfTemplate.put("EMAIL_PM", StandardCode.getInstance().noNull(""));
            rtfTemplate.put("PHONE_EXTENSION_PM", StandardCode.getInstance().noNull(""));
            //rtfTemplate.put("FAX_PM", StandardCode.getInstance().noNull(""));

        }
        rtfTemplate.put("FAX_PM", faxno);



        String bbsOrder = "";
        if(!StandardCode.getInstance().noNull(q.getProject().getOrderReqNum()).isEmpty()){
        bbsOrder =" / BBS Order "+q.getProject().getOrderReqNum();
        }
        
        rtfTemplate.put("QUOTE_NUMBER", q.getNumber()+bbsOrder);
        rtfTemplate.put("PRODUCT_NAME", q.getProject().getProduct());
        rtfTemplate.put("PRODUCT_DESCRIPTION", q.getProject().getProductDescription().replaceAll(q.getProject().getProduct() + " - ", "").replaceAll(q.getProject().getProduct() + "-", ""));

        //END content
        String rtfTarget = "SCOPE-"+q.getNumber() + "-" + q.getProject().getOrderReqNum().replaceAll(" ", "_").replaceAll(",", "_") + ".doc";
        rtfTemplate.put("QUOTEFILENAME", "SCOPE-"+q.getNumber() + "-BBS_Order_" + q.getProject().getOrderReqNum().replaceAll(" ", "_").replaceAll(",", "_") + "");

        rtfTemplate.merge(rtfTarget);
        
        QuoteTemplateHelper.getInstance().streamFile(rtfTarget, response);
        

        //write to client (web browser)
        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }


   
}
