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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import net.sourceforge.rtf.IRTFDocumentTransformer;
import net.sourceforge.rtf.RTFTemplate;
import net.sourceforge.rtf.handler.RTFDocumentHandler;
import net.sourceforge.rtf.template.velocity.RTFVelocityTransformerImpl;
import net.sourceforge.rtf.template.velocity.VelocityTemplateEngineImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.apache.velocity.app.VelocityEngine;

/**
 *
 * @author niteshwar
 */
public class QuoteViewGeneralGenerateBayerAction extends Action {

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
        Integer flag = 0;
        Integer id = Integer.valueOf(quoteId);

        //END get id of current quote from either request, attribute, or cookie               
        //get quote to edit
        Quote1 q = QuoteService.getInstance().getSingleQuote(id);

        String rtfSource = "/Users/abhisheksingh/Project/templates/Bayer_dt24Jul2017.rtf";
//        rtfSource = "/Users/abhisheksingh/Project/templates/GENERAL Template.rtf";
        rtfSource = "C:/templates/Bayer_dt24Jul2017.rtf";
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

        Client c = q.getProject().getCompany();

        //START content
        rtfTemplate.put("DATE", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));

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
//            rtfTemplate.put("CONTACTADDRESS2", StandardCode.getInstance().noNull(q.getProject().getContact().getAddress_2()));
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
//                rtfTemplate.put("CONTACTADDRESS2", StandardCode.getInstance().noNull(cc.getAddress_2()));
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
//                    rtfTemplate.put("CONTACTADDRESS2", StandardCode.getInstance().noNull(client.getAddress_2()));
                    rtfTemplate.put("CONTACTCITY", StandardCode.getInstance().noNull(client.getCity()));
                    rtfTemplate.put("CONTACTSTATE", StandardCode.getInstance().noNull(client.getState_prov()));
                    rtfTemplate.put("CONTACTZIPCODE", StandardCode.getInstance().noNull(client.getZip_postal_code()));

//            if (u.getWorkPhoneEx() != null && u.getWorkPhoneEx().length() > 0) { //ext present
                    rtfTemplate.put("CONTACTPHONE", StandardCode.getInstance().noNull(client.getWorkPhoneEx()));
//            } else { //no ext present
//                rtfTemplate.put("CONTACTPHONE", StandardCode.getInstance().noNull(u.getWorkPhone()));
//            }
//                    rtfTemplate.put("CONTACTFAX", StandardCode.getInstance().noNull(client.getFax_number()));
                    rtfTemplate.put("CONTACTFAX", StandardCode.getInstance().noNull(""));
                    rtfTemplate.put("CONTACTEMAIL", StandardCode.getInstance().noNull(client.getEmail_address()));
                } catch (Exception e2) {

                    User u = UserService.getInstance().getSingleUser(q.getEnteredById());

                    rtfTemplate.put("CONTACTNAME", StandardCode.getInstance().noNull(u.getFirstName()) + " " + StandardCode.getInstance().noNull(u.getLastName()));
                    rtfTemplate.put("CONTACTTITLE", StandardCode.getInstance().noNull(" "));
                    rtfTemplate.put("CONTACTADDRESS1", StandardCode.getInstance().noNull(u.getWorkAddress()));
//                    rtfTemplate.put("CONTACTADDRESS2", StandardCode.getInstance().noNull(" "));
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

        StringBuffer files = new StringBuffer("");
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
                                            //Count_Page=0.0,  Count_Hour=0.0,  Count_MultiLingual=0.0
                                            //Rate_Page=0.0,  Rate_Hour=0.0,  Rate_MultiLingual=0.0
                                            //Cost_Page=0.0,  Cost_Hour=0.0,  Cost_MultiLingual=0.0

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

//        rtfTemplate.put("TOTALPRICE", StandardCode.getInstance().formatDouble(new Double(totalPricePerLang)));
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
//        if (srcLanguageList.size() == 1) {
//            for (ListIterator iterDisplay = languageList.listIterator(); iterDisplay.hasNext();) {
//                Map<String, String> LANG = new HashMap<>();
//
//                LANG.put("LANG1", (String) iterDisplay.next());
//                if (iterDisplay.hasNext()) {
//                    LANG.put("LANG2", (String) iterDisplay.next());
//                }
//                LANG_LIST.add(LANG);
//                //System.out.println(LANG);
//            }
//            rtfTemplate.put("SRCLANGUAGE", srcLanguageList.get(0));
//            rtfTemplate.put("LANG_LIST", LANG_LIST);
//            rtfTemplate.put("SINGLESOURCE", true);
//        } else {
//            for (ListIterator iterSDisplay = srcLanguageList.listIterator(); iterSDisplay.hasNext();) {
//                String srcLang = (String) iterSDisplay.next();
//                for (ListIterator iterDisplay = languageList.listIterator(); iterDisplay.hasNext();) {
//                    Map<String, String> LANG = new HashMap<>();
//                    LANG.put("LANG1", srcLang + " into " + (String) iterDisplay.next());
//                    if (iterDisplay.hasNext()) {
//                        LANG.put("LANG2", srcLang + " into " + (String) iterDisplay.next());
//                    }
//                    LANG_LIST.add(LANG);
//                }
//            }
//            rtfTemplate.put("LANG_LIST", LANG_LIST);
//        }

        rtfTemplate.put("DELIVER", StandardCode.getInstance().noNull(q.getProject().getDeliverableTechNotes()));

        //START find billing details (tasks and changes)
        //get this project's sources
        Set sourceList = q.getSourceDocs();

        //for each source add each sources' Tasks
        java.util.List totalLinTasks = new java.util.ArrayList();
        java.util.List totalEngTasks = new java.util.ArrayList();
        java.util.List totalDtpTasks = new java.util.ArrayList();
        java.util.List totalOthTasks = new java.util.ArrayList();
        List<Map> INDIVIDUALTASKLIST = new ArrayList<>();
         double discount = 0.00;
         double discountPer = 0.00;
        if(null != q.getDiscountDollarTotal()){
        discount = new Double(q.getDiscountDollarTotal().replaceAll(",", ""));
        if(discount>0.00){
         rtfTemplate.put("DISCOUNT",  StandardCode.getInstance().formatDouble(discount));
         if(q.getDiscountPercent()!=null){
            if(new Double(q.getDiscountPercent().replaceAll(",", ""))>0.00){
                discountPer=new Double(q.getDiscountPercent().replaceAll(",", ""));
            }
         }
         
        }
        }
        //for each source
        for (Iterator sourceIter = sourceList.iterator(); sourceIter.hasNext();) {
            SourceDoc sd = (SourceDoc) sourceIter.next();
//for each target of this source
            Double enggtaskAllLangShare = 0.00;
            int tgtCnt = 0;
            for (Iterator linTargetIter = sd.getTargetDocs().iterator(); linTargetIter.hasNext();) {
                TargetDoc td = (TargetDoc) linTargetIter.next();

                if (td.getLanguage().equalsIgnoreCase("all")) {
                    for (Iterator engTaskIter = td.getEngTasks().iterator(); engTaskIter.hasNext();) {
                        EngTask et = (EngTask) engTaskIter.next();
                        try {
                            if (td.getLanguage().equalsIgnoreCase("all")) {
                                enggtaskAllLangShare += Double.parseDouble(et.getDollarTotal().replaceAll(",", "")) / sd.getTargetDocs().size();

                            } else {
                                tgtCnt++;
                            }
                        } catch (Exception e) {
                        }
                        totalEngTasks.add(et);
                    }
                }
            }
            if (tgtCnt == 0) {
                enggtaskAllLangShare = 0.00;
            } else {
                enggtaskAllLangShare = enggtaskAllLangShare / tgtCnt;
            }
            //for each target of this source
            for (Iterator linTargetIter = sd.getTargetDocs().iterator(); linTargetIter.hasNext();) {
                TargetDoc td = (TargetDoc) linTargetIter.next();

                if (!td.getLanguage().equalsIgnoreCase("all")) {
                    Map<String, String> ITL = new HashMap<>();
                    Double New_Rate = 0.0, Fuzzy_Rate = 0.0, Rep_Rate = 0.0, Rate100 = 0.0, Rate_Page = 0.0, Rate_Hour = 0.0;
                    Double New_Word = 0.0, Fuzzy_Word = 0.0, Rep_Word = 0.0, Word100 = 0.0, Count_Page = 0.0, Count_Hour = 0.0;
                    Double New_Cost = 0.0, Fuzzy_Cost = 0.0, Rep_Cost = 0.0, Cost100 = 0.0, Cost_Page = 0.0, Cost_Hour = 0.0;
//        Double Total_Cost=0.0, Total_Word=0.0;

                    Double taskTotal = 0.0;

                    if (!td.getLanguage().equalsIgnoreCase("all")) {
                        //  rtfTemplate.put("Replace_Table_Per_Language", tableTxt);
                        ITL.put("Target_Language1", counter++ + ": " + td.getLanguage());
                        ITL.put("Target_Language2", td.getLanguage());
                        String units = "";
                        Total_Cost = 0.0;
                        Total_Word = 0.0;
                        //for each lin Task of this target
                        for (Iterator linTaskIter = td.getLinTasks().iterator(); linTaskIter.hasNext();) {
                            LinTask lt = (LinTask) linTaskIter.next();
                            taskTotal += Double.parseDouble(lt.getDollarTotal().replaceAll(",", ""));

                            if (lt.getTaskName().trim().equalsIgnoreCase("Editing") && lt.getDollarTotal() != null) {
                                if (Double.parseDouble(lt.getDollarTotal().replaceAll(",", "")) > 0.00) {

                                    ITL.put("EDITWORDTOTAL", StandardCode.getInstance().formatDouble0(lt.getWordTotal()) + " " + lt.getUnits());
                                    ITL.put("EDITRATE", lt.getRate() + "/" + lt.getUnits());
                                    ITL.put("EDITCOST", lt.getDollarTotal());
                                    ITL.put("EDITING", "yes");

                                }
                            }
                            if (lt.getTaskName().contains("Proofreading") && lt.getDollarTotal() != null) {
                                if (Double.parseDouble(lt.getDollarTotal().replaceAll(",", "")) > 0.00) {

                                    ITL.put("PROOFWORDTOTAL", StandardCode.getInstance().formatDouble0(lt.getWordTotal()) + " " + lt.getUnits());
                                    ITL.put("PROOFRATE", lt.getRate() + "/" + lt.getUnits());
                                    ITL.put("PROOFCOST", lt.getDollarTotal());
                                    ITL.put("PROOFREADING", "yes");

                                }

                            }
                            if (!lt.getTaskName().contains("Proofreading") && !lt.getTaskName().equalsIgnoreCase("Translation") && !lt.getTaskName().trim().equalsIgnoreCase("Editing") && lt.getDollarTotal() != null) {
                                if (Double.parseDouble(lt.getDollarTotal().replaceAll(",", "")) > 0.00) {

                                    ITL.put("OTHERWORDTOTAL", StandardCode.getInstance().formatDouble0(lt.getWordTotal()) + " " + lt.getUnits());
                                    ITL.put("OTHERRATE", lt.getRate() + "/" + lt.getUnits());
                                    ITL.put("OTHERCOST", lt.getDollarTotal());
                                    ITL.put("OTHER", "yes");
                                    ITL.put("OTHERTASK", lt.getTaskName());

                                }

                            }
                            if (lt.getTaskName().equalsIgnoreCase("Translation")) {

                                units = lt.getUnits();
                                double wdNew = 0.00, wd100 = 0.00, wdRep = 0.00, wdFuzzy = 0.00;
                                try {
                                    wdNew += lt.getWordNew() + lt.getWord85().doubleValue() + lt.getWord75().doubleValue();
                                    New_Word += wdNew;
                                } catch (Exception e) {
                                }
                                try {
                                    wd100 += lt.getWord100().doubleValue() + lt.getWordPerfect().doubleValue() + lt.getWordContext();
                                    Word100 += wd100;
                                } catch (Exception e) {
                                }
                                try {
                                    wdRep += lt.getWordRep().doubleValue();
                                    Rep_Word += wdRep;
                                } catch (Exception e) {
                                }
                                try {
                                    wdFuzzy += lt.getWord95().doubleValue();
                                    Fuzzy_Word += wdFuzzy;
                                } catch (Exception e) {
                                }
                                Total_Word += wdNew + wd100 + wdRep + wdFuzzy;
                                Total_Cost += Double.parseDouble(lt.getDollarTotal().replaceAll(",", ""));
                                // New_Rate=0.0, Fuzzy_Rate=0.0,  Rep_Rate=0.0,  Rate100=0.0,
                                try {
                                    New_Rate = Double.parseDouble(lt.getRate()) * Double.parseDouble(c.getScaleNew(q.getProject().getProjectId(), c.getClientId()));
                                } catch (Exception e) {
                                }
                                try {
                                    Rate100 = Double.parseDouble(lt.getRate()) * Double.parseDouble(c.getScale100(q.getProject().getProjectId(), c.getClientId()));
                                } catch (Exception e) {
                                }
                                try {
                                    Rep_Rate = Double.parseDouble(lt.getRate()) * Double.parseDouble(c.getScaleRep(q.getProject().getProjectId(), c.getClientId()));
                                } catch (Exception e) {
                                }
                                try {
                                    Fuzzy_Rate = Double.parseDouble(lt.getRate()) * Double.parseDouble(c.getScale95(q.getProject().getProjectId(), c.getClientId()));
                                } catch (Exception e) {
                                }
                                //New_Cost=0.0, Fuzzy_Cost=0.0,  Rep_Cost=0.0,  Cost100=0.0
                                try {
                                    New_Cost += wdNew * New_Rate;
                                } catch (Exception e) {
                                }
                                try {
                                    Fuzzy_Cost += wdFuzzy * Fuzzy_Rate;
                                } catch (Exception e) {
                                }
                                try {
                                    Rep_Cost += wdRep * Rep_Rate;
                                } catch (Exception e) {
                                }
                                try {
                                    Cost100 += wd100 * Rate100;
                                } catch (Exception e) {
                                }

                            }
                            totalLinTasks.add(lt);
                        }
                        try {
                            ITL.put("New_Rate", "\\$ " + StandardCode.getInstance().formatDouble4(new Double(New_Rate)) + "/Word ");
                        } catch (Exception e) {
                        }
                        try {
                            ITL.put("New_Word", StandardCode.getInstance().formatDouble0(new Double(New_Word)) + " " + units);
                        } catch (Exception e) {
                        }
                        try {
                            ITL.put("New_Cost", "\\$ " + StandardCode.getInstance().formatDouble(new Double(New_Cost)));
                        } catch (Exception e) {
                        }

                        try {
                            ITL.put("Fuzzy_Rate", "\\$ " + StandardCode.getInstance().formatDouble4(new Double(Fuzzy_Rate)) + "/Word ");
                        } catch (Exception e) {
                        }
                        try {
                            ITL.put("Fuzzy_Word", StandardCode.getInstance().formatDouble0(new Double(Fuzzy_Word)) + " " + units);
                        } catch (Exception e) {
                        }
                        try {
                            ITL.put("Fuzzy_Cost", "\\$ " + StandardCode.getInstance().formatDouble(new Double(Fuzzy_Cost)));
                        } catch (Exception e) {
                        }

                        try {
                            ITL.put("Rep_Rate", "\\$ " + StandardCode.getInstance().formatDouble4(new Double(Rep_Rate)) + "/Word ");
                        } catch (Exception e) {
                        }
                        try {
                            ITL.put("Rep_Word", StandardCode.getInstance().formatDouble0(new Double(Rep_Word)) + " " + units);
                        } catch (Exception e) {
                        }
                        try {
                            ITL.put("Rep_Cost", "\\$ " + StandardCode.getInstance().formatDouble(new Double(Rep_Cost)));
                        } catch (Exception e) {
                        }

                        try {
                            ITL.put("W100_Rate", "\\$ " + StandardCode.getInstance().formatDouble4(new Double(Rate100)) + "/Word ");
                        } catch (Exception e) {
                        }
                        try {
                            ITL.put("W100_Word", StandardCode.getInstance().formatDouble0(new Double(Word100)) + " " + units);
                        } catch (Exception e) {
                        }
                        try {
                            ITL.put("W100_Cost", "\\$ " + StandardCode.getInstance().formatDouble(new Double(Cost100)));
                        } catch (Exception e) {
                        }

                        try {
                            ITL.put("Total_Word", StandardCode.getInstance().formatDouble0(Total_Word) + " " + units);
                        } catch (Exception e) {
                        }
                        try {
                            ITL.put("Total_Cost", "\\$ " + StandardCode.getInstance().formatDouble(Total_Cost));
                        } catch (Exception e) {
                        }
                    } else {
                        ITL.put("Target_Language1", td.getLanguage());
                        ITL.put("Target_Language2", td.getLanguage());

                    }

                    //for each eng Task of this target
                    double enggtasktot = 0.00;
                    for (Iterator engTaskIter = td.getEngTasks().iterator(); engTaskIter.hasNext();) {
                        EngTask et = (EngTask) engTaskIter.next();
                        try {
                            taskTotal += Double.parseDouble(et.getDollarTotal().replaceAll(",", ""));

                            if (!td.getLanguage().equalsIgnoreCase("all")) {
                                enggtasktot += Double.parseDouble(et.getDollarTotal().replaceAll(",", ""));

                            }
                        } catch (Exception e) {
                        }
                        totalEngTasks.add(et);
                    }
                    enggtasktot += enggtaskAllLangShare;
                    if (enggtasktot > 0.00) {
                        ITL.put("Engg_Cost", StandardCode.getInstance().formatDouble(enggtasktot));
                        ITL.put("ECCurr", "true");
                    } else {
                        ITL.put("Engg_Cost", "Included");
                    }

                    //for each dtp Task of this target
                    for (Iterator dtpTaskIter = td.getDtpTasks().iterator(); dtpTaskIter.hasNext();) {
                        DtpTask dt = (DtpTask) dtpTaskIter.next();
                        
                        taskTotal += Double.parseDouble(StandardCode.getInstance().noNull(dt.getDollarTotal(),"0.00").replaceAll(",", ""));
//                    if(flag==1){
//                     ITL.put("Rate_MultiLingual", StandardCode.getInstance().formatDouble(new Double(Rate_MultiLingual)));
//                                             ITL.put("Count_MultiLingual", StandardCode.getInstance().formatDouble(new Double(Count_MultiLingual)));
//                                             ITL.put("Cost_MultiLingual",c.getCcurrency()+ StandardCode.getInstance().formatDouble(new Double(Cost_MultiLingual)));
//
//                    }
                        if (!td.getLanguage().equalsIgnoreCase("all")) {
                            if (dt.getUnits().equalsIgnoreCase("Hours")) {
                                Rate_Hour += Double.parseDouble(dt.getRate());
                                Count_Hour += dt.getTotalTeam();
                                Cost_Hour += Rate_Hour * Count_Hour;

                                ITL.put("Rate_Hour", "\\$ " + StandardCode.getInstance().formatDouble(new Double(Rate_Hour)) + "/Hour ");
                                ITL.put("Count_Hour", StandardCode.getInstance().formatDouble(new Double(Count_Hour)) + " Hours");
                                ITL.put("Cost_Hour", "\\$ " + StandardCode.getInstance().formatDouble(new Double(Cost_Hour)));
                            }
                            if (dt.getUnits().equalsIgnoreCase("Pages")) {

                                Rate_Page += Double.parseDouble(dt.getRate());
                                Count_Page += dt.getTotalTeam();
                                Cost_Page += Rate_Page * Count_Page;

                                ITL.put("Rate_Page", "\\$ " + StandardCode.getInstance().formatDouble(new Double(Rate_Page)) + "/Page ");
                                ITL.put("Count_Page", StandardCode.getInstance().formatDouble(new Double(Count_Page)) + " Pages");
                                ITL.put("Cost_Page", "\\$ " + StandardCode.getInstance().formatDouble(new Double(Cost_Page)));

                            }
                        } else {

                            ITL.put("Rate_MultiLingual", "\\$ " + StandardCode.getInstance().formatDouble(new Double(Rate_MultiLingual)));
                            ITL.put("Count_MultiLingual", StandardCode.getInstance().formatDouble(new Double(Count_MultiLingual)));
                            ITL.put("Cost_MultiLingual", "\\$ " + StandardCode.getInstance().formatDouble(new Double(Cost_MultiLingual)));
                            ITL.put("Included", "\\$ " + StandardCode.getInstance().formatDouble(new Double(taskTotal)));
                        }

                        totalDtpTasks.add(dt);
                    }

                    //for each oth Task of this target
                    for (Iterator othTaskIter = td.getOthTasks().iterator(); othTaskIter.hasNext();) {
                        OthTask ot = (OthTask) othTaskIter.next();
                        try {
                            taskTotal += Double.parseDouble(ot.getDollarTotal().replaceAll(",", ""));
                        } catch (Exception e) {
                        }
                        totalOthTasks.add(ot);
                    }

                    Double pmFee = 0.0;
                    if (q.getPmPercent() != null) {
                        pmFee = taskTotal * Double.parseDouble(q.getPmPercent()) / 100;
                    }
                    
                    Double taskDiscount = 0.0;
                    if (discountPer > 0.00) {
                        taskDiscount = taskTotal * discountPer / 100;
                        ITL.put("Insert_Discount_Insert", "\\$ " + StandardCode.getInstance().formatDouble(taskDiscount));
                    }
                    ITL.put("Insert_PM_Insert", "\\$ " + StandardCode.getInstance().formatDouble(pmFee));
                    
                    taskTotal = taskTotal + pmFee-taskDiscount;
                    ITL.put("Insert_amount_Insert", "\\$ " + StandardCode.getInstance().formatDouble(taskTotal));

                    INDIVIDUALTASKLIST.add(ITL);

                }
            }
        }

        rtfTemplate.put("INDIVIDUALTASK", INDIVIDUALTASKLIST);

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
            quoteTotal = q.getQuoteDollarTotal().doubleValue();
        }

        Double totalPricePerLang = linAmt + engAmt + othAmt + linTaskIcr;
        Double pmcost = 0.00;
        try {
            pmcost = totalPricePerLang * Double.parseDouble(q.getPmPercent()) / 100;
        } catch (Exception e) {
            pmcost = 0.00;
        }
        totalPricePerLang = totalPricePerLang + pmcost;
//        rtfTemplate.put("PMPRICE", StandardCode.getInstance().formatDouble(new Double(pmcost)));
 
        //find pm total
        double pmTotal = 0;
        try {
            String strPmSubTotal = q.getSubDollarTotal().replaceAll(",", "");
            if (q.getQuoteDollarTotal() != null) {
                pmTotal = q.getQuoteDollarTotal().doubleValue() - (new Double(strPmSubTotal)).doubleValue()+discount;
            }
        } catch (Exception e) {
        }
       
        rtfTemplate.put("LINPRICE", StandardCode.getInstance().formatDouble(new Double(linTaskTotal)));
        rtfTemplate.put("DTPPRICE", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotal)));
        rtfTemplate.put("ENGPRICE", StandardCode.getInstance().formatDouble(new Double(engTaskTotal)));
        rtfTemplate.put("PMPRICE", StandardCode.getInstance().formatDouble(new Double(pmTotal)));
        rtfTemplate.put("OTHPRICE", StandardCode.getInstance().formatDouble(new Double(othTaskTotal)));
        rtfTemplate.put("TOTALPRICE", StandardCode.getInstance().formatDouble(new Double(quoteTotal)));
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

        if (linTaskTotalIcr != 0) {

            rtfTemplate.put("ICRTASK", "ICR");
            rtfTemplate.put("ICRPRICE", "\\$" + StandardCode.getInstance().formatDouble(new Double(linTaskTotalIcr)));
        } else {
            rtfTemplate.put("ICRTASK", "");
            rtfTemplate.put("ICRPRICE", "");
        }

        rtfTemplate.put("QUOTE_NUMBER", q.getNumber());
        rtfTemplate.put("PRODUCT_NAME", q.getProject().getProduct());
        rtfTemplate.put("PRODUCT_DESCRIPTION", q.getProject().getProductDescription().replaceAll(q.getProject().getProduct() + " - ", "").replaceAll(q.getProject().getProduct() + "-", ""));

        //END content
        String rtfTarget = q.getNumber() + "-" + q.getProject().getCompany().getCompany_name().replaceAll(" ", "_").replaceAll(",", "_") + "_quote.doc";
        rtfTemplate.put("QUOTEFILENAME", q.getNumber() + "-" + q.getProject().getCompany().getCompany_name().replaceAll(" ", "_").replaceAll(",", "_") + "_quote");

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

//        //write to client (web browser)
//        response.setHeader("Content-Type", "Application/msword");
//        response.setContentLength((int) content.length());  
////        response.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
//        String filename = q.getNumber() + "-" + q.getProject().getCompany().getCompany_name().replaceAll(" ", "_").replaceAll(",", "_") + "_quote.doc";
//        response.setHeader("Content-disposition", "attachment; filename=" + filename);
//        OutputStream os = response.getOutputStream();
//        os.write(content.getBytes());
//        os.flush(); 
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
