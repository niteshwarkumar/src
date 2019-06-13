/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.quote;
import app.client.ClientService;
import app.extjs.helpers.QuoteHelper;
import app.extjs.vo.Product;
import app.extjs.vo.Upload_Doc;
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
import java.text.*;
import java.io.*;
import app.user.*;
import app.project.*;
import app.security.*;
import app.standardCode.*;


/**
 *
 * @author Niteshwar
 */
public class QuoteViewGeneralCerusAction  extends Action {

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
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)

        //START get id of current quote from either request, attribute, or cookie
        //id of quote from request
        String quoteId = null;
        quoteId = request.getParameter("quoteViewId");

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

        Integer id = Integer.valueOf(quoteId);

        //END get id of current quote from either request, attribute, or cookie

        //get quote to edit
        Quote1 q = QuoteService.getInstance().getSingleQuote(id);
//RTFTemplateBuilder builder = RTFTemplateBuilder.newRTFTemplateBuilder();
        //save the pdf in memory
        //byte[] template = getBytesFromFile(new java.io.File("C:/templates/template_short_proposal.htm"));
        //  byte[] template = getBytesFromFile(new java.io.File("C:/templates2/T5.002 - Quote template - short_non_medical.rtf"));
        byte[] template = getBytesFromFile(new java.io.File("C:/templates2/T9 - Quote template - Cerus.rtf"));

        String content = new String(template);
        //START content
        content = content.replaceAll("INSERT_DATE_INSERT", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
        content = content.replaceAll("INSERT_COMPANYLOGO_INSERT", "");
        // content = content.replaceAll("INSERT_COMPANYLOGO_INSERT", <image url='../../images/q.getProject().getCompany().getLogo()'/>);
        //Image imgg=Image.getInstance("c://companymage.jpg");

        content = content.replaceAll("INSERT_DATE_INSERT", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
        //System.out.println("hereeeeee");
        content = content.replaceAll("INSERT_COMPANYNAME_INSERT", q.getProject().getCompany().getCompany_name());
        //System.out.println("hereeeeee2");
        try {
            content = content.replaceAll("INSERT_CONTACTNAME_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getFirst_name()) + " " + StandardCode.getInstance().noNull(q.getProject().getContact().getLast_name()));
            //System.out.println("hereeeeee3");
            content = content.replaceAll("INSERT_CONTACTTITLE_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getTitle()));
String comma=", ";
if("".equalsIgnoreCase(StandardCode.getInstance().noNull(q.getProject().getContact().getDivision()))){comma="";}
            content = content.replaceAll("INSERT_CONTACTADDRESS1_INSERT",StandardCode.getInstance().noNull(q.getProject().getContact().getDivision())+comma+ StandardCode.getInstance().noNull(q.getProject().getContact().getAddress_1()));
            content = content.replaceAll("INSERT_CONTACTADDRESS2_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getAddress_2()));
            content = content.replaceAll("INSERT_CONTACTCITY_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getCity()));
            content = content.replaceAll("INSERT_CONTACTSTATE_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getState_prov()));
            content = content.replaceAll("INSERT_CONTACTZIPCODE_INSERT", StandardCode.getInstance().noNull(q.getProject().getCompany().getZip_postal_code()));
            if (q.getProject().getContact().getWorkPhoneEx() != null && q.getProject().getContact().getWorkPhoneEx().length() > 0) { //ext present
                content = content.replaceAll("INSERT_CONTACTPHONE_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getTelephone_number()) + " ext " + StandardCode.getInstance().noNull(q.getProject().getContact().getWorkPhoneEx()));
            } else { //no ext present
                content = content.replaceAll("INSERT_CONTACTPHONE_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getTelephone_number()));
            }
            content = content.replaceAll("INSERT_CONTACTFAX_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getFax_number()));
            content = content.replaceAll("INSERT_CONTACTEMAIL_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getEmail_address()));



        } catch (Exception e) {

            User u = UserService.getInstance().getSingleUser(q.getEnteredById());
            content = content.replaceAll("INSERT_CONTACTNAME_INSERT", StandardCode.getInstance().noNull(u.getFirstName()) + " " + StandardCode.getInstance().noNull(u.getLastName()));
            content = content.replaceAll("INSERT_CONTACTTITLE_INSERT", StandardCode.getInstance().noNull(" "));
            content = content.replaceAll("INSERT_CONTACTADDRESS1_INSERT", StandardCode.getInstance().noNull(u.getWorkAddress()));
            content = content.replaceAll("INSERT_CONTACTADDRESS2_INSERT", StandardCode.getInstance().noNull(" "));
            content = content.replaceAll("INSERT_CONTACTCITY_INSERT", StandardCode.getInstance().noNull(u.getWorkCity()));
            content = content.replaceAll("INSERT_CONTACTSTATE_INSERT", StandardCode.getInstance().noNull(u.getWorkState()));
            content = content.replaceAll("INSERT_CONTACTZIPCODE_INSERT", StandardCode.getInstance().noNull(u.getWorkZip()));

            if (u.getWorkPhoneEx() != null && u.getWorkPhoneEx().length() > 0) { //ext present
                content = content.replaceAll("INSERT_CONTACTPHONE_INSERT", StandardCode.getInstance().noNull(u.getWorkPhone()) + " ext " + StandardCode.getInstance().noNull(u.getWorkPhoneEx()));
            } else { //no ext present
                content = content.replaceAll("INSERT_CONTACTPHONE_INSERT", StandardCode.getInstance().noNull(u.getWorkPhone()));
            }
            content = content.replaceAll("INSERT_CONTACTFAX_INSERT", StandardCode.getInstance().noNull(u.getWorkEmail1()));
            content = content.replaceAll("INSERT_CONTACTEMAIL_INSERT", StandardCode.getInstance().noNull(u.getWorkEmail2()));


        }
        // List productList=QuoteService.getInstance().get
        String med = "";
        String detail = "";
        String category = "";
        List quoteList = QuoteService.getInstance().getSingleClientQuote(id);
        for (int i = 0; i < quoteList.size(); i++) {
            Client_Quote cq = (Client_Quote) quoteList.get(i);
            Product prod = ClientService.getSingleProduct(cq.getProduct_ID());
            detail += StandardCode.getInstance().noNull(cq.getType());
            med += StandardCode.getInstance().noNull(cq.getMedical());
            category += StandardCode.getInstance().noNull(prod.getCategory());


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
        String txtt = " \\\\par { \\\\listtext \\\\pard \\\\plain \\\\s68  \\\\f3 \\\\fs22 \\\\cf2  \\\\loch \\\\af3 \\\\dbch \\\\af0 \\\\hich \\\\f3  \\\\'b7 \\\\tab}INSERT_TASKS_INSERT";
        //      content = content.replaceAll("INSERT_FILELIST_INSERT", txtt);
        if (ud1 != null) {
            for (int i = 0; i < pname.size() - 1; i++) {
                Upload_Doc ud = (Upload_Doc) pname.get(i);
                uDoc += ud.getPathname();
                content = content.replaceAll("INSERT_FILELIST_INSERT", ud.getPathname() + txtt);
                if (i != pname.size() - 1) {
                    uDoc += ",";

                }
//File No.=i+1  :
            }
            Upload_Doc ud = (Upload_Doc) pname.get(pname.size() - 1);
            content = content.replaceAll("INSERT_FILELIST_INSERT", ud.getPathname());
        } else {
            content = content.replaceAll("INSERT_FILELIST_INSERT", "- No Files -");

        }

 String replce="\\\\par \\\\hich \\\\dbch \\\\af31505 \\\\loch \\\\f0";

        //content = content.replaceAll("INSERT_FILELIST_INSERT", StandardCode.getInstance().noNull(files.toString()));
        if (!StandardCode.getInstance().noNull(q.getProject().getProduct()).equalsIgnoreCase("")) {
            content = content.replaceAll("INSERT_PRODUCT_HERE", StandardCode.getInstance().noNull(q.getProject().getProduct()));
        } else{content =content.replaceAll("INSERT_PRODUCT_HERE", "");}
//        if (!StandardCode.getInstance().noNull(category).equalsIgnoreCase("")) {
//            content = content.replaceAll("INSERT_CATEGORY_HERE", replce+StandardCode.getInstance().noNull(category));
//        } else{content =content.replaceAll("INSERT_CATEGORY_HERE", "");}
//        if (!StandardCode.getInstance().noNull(med).equalsIgnoreCase("")) {
//
//            content = content.replaceAll("INSERT_TYPE_HERE", StandardCode.getInstance().noNull(med));
//        }else{content =content.replaceAll("INSERT_TYPE_HERE", "");}
        if (!StandardCode.getInstance().noNull(detail).equalsIgnoreCase("")) {

            content = content.replaceAll("INSERT_DETAIL_HERE", StandardCode.getInstance().noNull(detail));
        } else{content =content.replaceAll("INSERT_DETAIL_HERE", "");}
//INSERT_FILELIST_INSERT

        //    content = content.replaceAll("INSERT_FILELIST_INSERT", txtt);


        //Language breakdown code
        Hashtable htBreakdown = QuoteHelper.getBreakdownByLanguageTable(q);
        for (int i = 1; i <= 35; i++) {
            String currentCounter = i + "";
            if (i < 10) {
                currentCounter = "0" + i;
            }


            content = content.replaceAll("bd_lang_" + currentCounter, StandardCode.getInstance().noNull((String) htBreakdown.get("bd_lang_" + currentCounter)));
            content = content.replaceAll("bd_lin_" + currentCounter, StandardCode.getInstance().noNull((String) htBreakdown.get("bd_lin_" + currentCounter)));
            content = content.replaceAll("bd_dtp_" + currentCounter, StandardCode.getInstance().noNull((String) htBreakdown.get("bd_dtp_" + currentCounter)));
            content = content.replaceAll("bd_eng_" + currentCounter, StandardCode.getInstance().noNull((String) htBreakdown.get("bd_eng_" + currentCounter)));
            content = content.replaceAll("bd_total_" + currentCounter, StandardCode.getInstance().noNull((String) htBreakdown.get("bd_total_" + currentCounter)));
        }

        content = content.replaceAll("bd_pm_fee", StandardCode.getInstance().noNull((String) htBreakdown.get("bd_pm_fee")));
        content = content.replaceAll("bd_rush_fee", StandardCode.getInstance().noNull((String) htBreakdown.get("bd_rush_fee")));
        content = content.replaceAll("bd_discount", StandardCode.getInstance().noNull((String) htBreakdown.get("bd_discount")));
        content = content.replaceAll("bd_grand_total", StandardCode.getInstance().noNull((String) htBreakdown.get("bd_grand_total")));


        //End of Language breakdown code

        StringBuffer files = new StringBuffer("");
        if (q.getFiles() != null) {
            for (Iterator iter = q.getFiles().iterator(); iter.hasNext();) {
                File f = (File) iter.next();
                if (f.getBeforeAnalysis().equals("false")) {
                    files.append(StandardCode.getInstance().noNull(f.getFileName() + ", "));
                }
            }
        }
        //   content = content.replaceAll("INSERT_FILELIST_INSERT", StandardCode.getInstance().noNull(files.toString()));
        //   content = content.replaceAll("INSERT_PRODUCTNAME_INSERT", StandardCode.getInstance().noNull(q.getProject().getProduct()));

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
                                    } else { //use taskName
                                        if (displayText.equals(t.getTaskName()) && !isIn) {
                                            isIn = true;
                                        }
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
                                    } else { //use taskName
                                        if (displayText.equals(t.getTaskName()) && !isIn) {
                                            isIn = true;
                                        }
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
                                    } else { //use taskName
                                        if (displayText.equals(t.getTaskName()) && !isIn) {
                                            isIn = true;
                                        }
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
                                    } else { //use taskName
                                        if (displayText.equals(t.getTaskName()) && !isIn) {
                                            isIn = true;
                                        }
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
                    }
                }
            }
        }




        StringBuffer tasks = new StringBuffer("");
        for (ListIterator iterDisplay = tasksIncludedLin.listIterator(); iterDisplay.hasNext();) {
            String display = (String) iterDisplay.next();
            tasks.append(StandardCode.getInstance().noNull(display) + ", ");
        }
        for (ListIterator iterDisplay = tasksIncludedEng.listIterator(); iterDisplay.hasNext();) {
            String display = (String) iterDisplay.next();
            tasks.append(StandardCode.getInstance().noNull(display) + ", ");
        }
        for (ListIterator iterDisplay = tasksIncludedDtp.listIterator(); iterDisplay.hasNext();) {
            String display = (String) iterDisplay.next();
            tasks.append(StandardCode.getInstance().noNull(display) + ", ");
        }
        for (ListIterator iterDisplay = tasksIncludedOth.listIterator(); iterDisplay.hasNext();) {
            String display = (String) iterDisplay.next();
            tasks.append(StandardCode.getInstance().noNull(display) + ", ");
        }

        //get sources and targets
        String curr = new String("");
        StringBuffer sources = new StringBuffer("");
        StringBuffer targets = new StringBuffer("");
        StringBuffer languages = new StringBuffer("");
        //StringBuffer source = new StringBuffer("");

        if (q.getSourceDocs() != null) {
            for (Iterator iterSource = q.getSourceDocs().iterator(); iterSource.hasNext();) {
                SourceDoc sd = (SourceDoc) iterSource.next();
                sources.append(sd.getLanguage() + " ");
                //languages.append(sd.getLanguage() + " to");
                if (sd.getTargetDocs() != null) {
                    for (Iterator iterTarget = sd.getTargetDocs().iterator(); iterTarget.hasNext();) {
                        TargetDoc td = (TargetDoc) iterTarget.next();
                        if (!td.getLanguage().equals("All")) {
                            targets.append(td.getLanguage() + "");
                            languages.append(" " + td.getLanguage() + ", ");
                            if ((curr.length() == 0) && (td.getLinTasks() != null)) {
                                for (Iterator iterTasks = td.getLinTasks().iterator(); iterTasks.hasNext();) {
                                    LinTask lt = (LinTask) iterTasks.next();
                                    if (lt.getCurrency() != null && lt.getCurrency().length() > 0) {
                                        curr = lt.getCurrency();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (languages != null && languages.toString().endsWith(", ")) {
            languages = new StringBuffer(languages.toString().substring(0, languages.toString().length() - 2));
        }
        content = content.replaceAll("INSERT_SOURCE", StandardCode.getInstance().noNull(sources.toString()));
        String[] taskList = tasks.toString().split(",");
        String taskText = "\\\\par}{\\\\rtlch\\\\fcs1\\\\af0\\\\afs22\\\\ltrch\\\\fcs0\\\\fs22\\\\cf2\\\\insrsid15225780\\\\hich\\\\af0\\\\dbch\\\\af31505\\\\loch\\\\f0INSERT_TASKS_INSERT";
        //String langText = "\\\\par {\\\\listtext\\\\pard\\\\plain\\\\ltrpar \\\\s68 \\\\rtlch\\\\fcs1 \\\\af3\\\\afs22 \\\\ltrch\\\\fcs0 \\\\f3\\\\fs22\\\\cf2\\\\lang1033\\\\langfe1033\\\\langnp1033\\\\langfenp1033 \\\\loch\\\\af3\\\\dbch\\\\af31505\\\\hich\\\\f3 \\\\'b7\\\\tab}}\\\\pard\\\\plain \\\\ltrpar \\\\s68\\\\ql \\\\fi-360\\\\li1080\\\\ri0\\\\widctlpar\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\ls39\\\\adjustright\\\\rin0\\\\lin1080\\\\itap0 \\\\rtlch\\\\fcs1 \\\\af0\\\\afs20\\\\alang1025 \\\\ltrch\\\\fcs0 \\\\fs20\\\\lang1033\\\\langfe1033\\\\loch\\\\af0\\\\hich\\\\af0\\\\dbch\\\\af31505\\\\cgrid\\\\langnp1033\\\\langfenp1033 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\fs22\\\\cf2\\\\insrsid15225780 \\\\hich\\\\af0\\\\dbch\\\\af31505\\\\loch\\\\f0 INSERT_LANGUAGES_INSERT";
        String langText = "\\\\par}{\\\\rtlch\\\\fcs1\\\\af0\\\\afs22\\\\ltrch\\\\fcs0\\\\fs22\\\\cf2\\\\insrsid15225780\\\\hich\\\\af0\\\\dbch\\\\af31505\\\\loch\\\\f0INSERT_LANGUAGES_INSERT";

        for (int i = 0; i < taskList.length - 1; i++) {
            content = content.replaceAll("INSERT_TASKS_INSERT", StandardCode.getInstance().noNull(taskList[i] + taskText));
        }
        content = content.replaceAll("INSERT_TASKS_INSERT", StandardCode.getInstance().noNull(taskList[taskList.length - 1]));
        String[] langList = languages.toString().split(",");
        //Integer count=0;
        for (int i = 0; i < (langList.length) - 1; i++) {
            try {
                content = content.replaceAll("INSERT_LANGUAGES_INSERT", StandardCode.getInstance().noNull(langList[i]).trim() + langText);
            } catch (Exception e) {
                content = content.replaceAll("INSERT_LANGUAGES_INSERT", "");
            }
            //System.out.println("---------------------------");
        }
        content = content.replaceAll("INSERT_LANGUAGES_INSERT", StandardCode.getInstance().noNull(langList[langList.length - 1]).trim());
        // content = content.replaceAll("INSERT_LANGUAGES_INSERT", StandardCode.getInstance().noNull(langList[langList.length-1]));

        content = content.replaceAll("INSERT_DELIVER_INSERT", StandardCode.getInstance().noNull(q.getProject().getDeliverableTechNotes()));

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
        Double linVolume = new Double(0.0);
        for (int i = 0; i < linTasksArray.length; i++) {
      if(!linTasksArray[i].getTaskName().equalsIgnoreCase("ICR")&&!linTasksArray[i].getTaskName().equalsIgnoreCase("ICR ")&&!linTasksArray[i].getTaskName().equalsIgnoreCase("In-Country Review (ICR)")&&!linTasksArray[i].getTaskName().equalsIgnoreCase("In-Country / Client Review")){

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
        }else{
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
            quoteTotal = q.getQuoteDollarTotal().doubleValue();
        }

        //find pm total
        double pmTotal = 0;


        if (q.getQuoteDollarTotal() != null && q.getSubDollarTotal() != null) {
            String strPmSubTotal = q.getSubDollarTotal().replaceAll(",", "");
            pmTotal = q.getQuoteDollarTotal().doubleValue() - (new Double(strPmSubTotal)).doubleValue();
        }
        //System.out.println("hereeeeee4");
        content = content.replaceAll("INSERT_LINPRICE_INSERT", StandardCode.getInstance().formatDouble(new Double(linTaskTotal)));
        content = content.replaceAll("INSERT_DTPPRICE_INSERT", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotal)));
        if (engTaskTotal == 0) {
            content = content.replaceAll("INSERT_ENGPRICE_INSERT", "Included");
        } else {
            String engtotal1 = "\\$" + (String) StandardCode.getInstance().formatDouble(new Double(engTaskTotal));
            //System.out.println("Testtttttttttttttttttt" + engtotal1);
            content = content.replaceAll("INSERT_ENGPRICE_INSERT", engtotal1);
        }

        content = content.replaceAll("INSERT_PMPRICE_INSERT", StandardCode.getInstance().formatDouble(new Double(pmTotal)));

          if (linTaskTotalIcr != 0) {

            content = content.replaceAll("INSERT_ICRTASK", "ICR");
            content = content.replaceAll("INSERT_ICRPRICE_INSERT", "\\$" + StandardCode.getInstance().formatDouble(new Double(linTaskTotalIcr)));
        } else {
            content = content.replaceAll("INSERT_ICRTASK", "");
            content = content.replaceAll("INSERT_ICRPRICE_INSERT", "");
        }


        if (othTaskTotal != 0) {

            content = content.replaceAll("INSERT_OTHER", "Other (specify)");
            content = content.replaceAll("INSERT_OTHPRICE_INSERT", "\\$" + StandardCode.getInstance().formatDouble(new Double(othTaskTotal)));
        } else {
            content = content.replaceAll("INSERT_OTHER", "");
            content = content.replaceAll("INSERT_OTHPRICE_INSERT", "");
        }
        try {


            content = content.replaceAll("INSERT_TOTALPRICE_INSERT", StandardCode.getInstance().formatDouble(new Double(quoteTotal)));
        } catch (Exception e) {
        }
        try {

            content = content.replaceAll("INSERT_CURRENCY_INSERT", StandardCode.getInstance().noNull(curr));


        } catch (Exception e) {
        }
        if(!curr.equalsIgnoreCase("USD")){content=content.replaceAll("\\$", "€");}
        try {
            //System.out.println("hereeeeee5");

            content = content.replaceAll("INSERT_LEADTIME_INSERT", StandardCode.getInstance().noNull(q.getProject().getBeforeWorkTurn()));
        } catch (Exception e) {
             content = content.replaceAll("INSERT_LEADTIME_INSERT","");
        }
        try {
            content = content.replaceAll("INSERT_LEADTIMEUNITS_INSERT", StandardCode.getInstance().noNull(q.getProject().getBeforeWorkTurnUnits().toLowerCase()));
        } catch (Exception e) {
            content = content.replaceAll("INSERT_LEADTIMEUNITS_INSERT", "");
        }
        try {
            content = content.replaceAll("INSERT_REFERENCE_INSERT", StandardCode.getInstance().noNull(q.getNumber()));
        } catch (Exception e) {
        }
     String faxno="";
        try {
//            String[] fname = q.getProject().getPm().split(" ");
            User enteredBy=UserService.getInstance().getSingleUser(q.getEnteredById());
try{
            if(enteredBy.getUserType().equalsIgnoreCase("client")){faxno= ClientService.getInstance().getClient(enteredBy.getId_client()).getFax_number();}
            }catch(Exception e){
            faxno=enteredBy.getLocation().getFax_number();
            }
               try {
            content = content.replaceAll("INSERT_PMNAME_INSERT", StandardCode.getInstance().noNull(enteredBy.getFirstName())+" " +StandardCode.getInstance().noNull(enteredBy.getLastName()));
        } catch (Exception e) {
        }

            content = content.replaceAll("INSERT_EMAIL_PM_INSERT", StandardCode.getInstance().noNull(enteredBy.getWorkEmail1()));
            content = content.replaceAll("INSERT_PHONE_EXTENSION_PM_INSERT", StandardCode.getInstance().noNull(enteredBy.getWorkPhone()) + " ext " + StandardCode.getInstance().noNull(enteredBy.getWorkPhoneEx()));

        } catch (Exception e) {

            content = content.replaceAll("INSERT_EMAIL_PM_INSERT", StandardCode.getInstance().noNull(""));
            content = content.replaceAll("INSERT_PHONE_EXTENSION_PM_INSERT", StandardCode.getInstance().noNull(""));
            //content = content.replaceAll("INSERT_FAX_PM_INSERT", StandardCode.getInstance().noNull(""));

        }
     content = content.replaceAll("INSERT_FAX_PM_INSERT", faxno);

        String filename = q.getNumber() + "-" + q.getProject().getCompany().getCompany_name().replaceAll(",", "_").replaceAll(" ", "_") + "_quote.doc";

        content = content.replaceAll("T5 - Quote template - short_non_medical.rtf", StandardCode.getInstance().noNull(filename));
        //System.out.println("hereeeeee6");
        // User myPm = UserService.getInstance().getSingleUserRealName(StandardCode.getInstance().getFirstName(q.getProject().getPm()), StandardCode.getInstance().getLastName(q.getProject().getPm()));
        // content = content.replaceAll("INSERT_PHONE_EXTENSION_PM_INSERT", StandardCode.getInstance().noNull(myPm.getWorkPhone())+"  Extension: "+StandardCode.getInstance().noNull(myPm.getWorkPhoneEx()));
        // content = content.replaceAll("INSERT_FAX_PM_INSERT", StandardCode.getInstance().noNull(myPm.getLocation().getFax_number()));
        // content = content.replaceAll("INSERT_EMAIL_PM_INSERT", StandardCode.getInstance().noNull(myPm.getWorkEmail1()));

        //END content

        //write to client (web browser)
        response.setHeader("Content-Type", "Application/msword");
        response.setHeader("Content-disposition", "attachment; filename=" + filename);
        //response.setHeader("Content-disposition", "attachment; filename=" + q.getNumber() + ".doc");
        OutputStream os = response.getOutputStream();
        os.write(content.getBytes());
        os.flush();
        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }

    // Returns the contents of the file in a byte array.
    private static byte[] getBytesFromFile(java.io.File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }
}
