/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.comm;

import app.client.ClientNotes;
import app.client.ClientService;
import app.extjs.global.LanguageAbs;
import app.inteqa.INBasics;
import app.inteqa.INDelivery;
import app.inteqa.INDeliveryReq;
import app.inteqa.INDtp;
import app.inteqa.INEngineering;
import app.inteqa.INReference;
import app.inteqa.INSourceFile;
import app.inteqa.InteqaService;
import app.project.DtpTask;
import app.project.EngTask;
import app.project.LinTask;
import app.project.OthTask;
import app.project.Project;
import app.project.ProjectService;
import app.project.Project_Technical;
import app.project.SourceDoc;
import app.project.TargetDoc;
import app.quote.Client_Quote;
import app.quote.Quote1;
import app.quote.QuotePriority;
import app.quote.QuoteService;
import app.quote.Technical;
import app.standardCode.StandardCode;
import app.user.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

/**
 *
 * @author niteshwar
 */
public class EmailHelper {

    private static EmailHelper instance = null;

    public EmailHelper() {
    }

    //return the instance of EmailHelper
    public static synchronized EmailHelper getInstance() {
        /*
     * Creates the Singleton instance, if needed.
     *
         */
        if (instance == null) {
            instance = new EmailHelper();
        }
        return instance;
    }

    public static String senderId = "excelnet@xltrans.com";
    private static final String referenceTh ="color:#ffffff;background-color:#555555;border:1px solid #555555;padding:3px;vertical-align:top;text-align:left;font-size: 10.0pt;font-family: verdana,tahoma,arial,helvetica,sans-serif;";
    private static final String headerColor="color:blue;font-size: 10.0pt;font-family: verdana,tahoma,arial,helvetica,sans-serif;";
    private static String notes="color:darkred;font-size: 10.0pt;font-family: verdana,tahoma,arial,helvetica,sans-serif;";
    private static String styleTD="border:1px solid #d4d4d4;padding:5px;padding-top:7px;padding-bottom:7px;vertical-align:top;font-size: 10.0pt;font-family: verdana,tahoma,arial,helvetica,sans-serif;";
    private static String styleTR="vertical-align: top;padding-top:7px;font-size: 10.0pt;font-family: verdana,tahoma,arial,helvetica,sans-serif;";
    
    public String getReqAnalysisEmailBody(Project p, Quote1 q, String porq) {
        INDtp indtp = InteqaService.getInstance().getINDtp(p.getProjectId());
        INBasics inbasic = InteqaService.getInstance().getINBasics(p.getProjectId());
        INEngineering inengg = InteqaService.getInstance().getINEngineering(p.getProjectId());
        QuotePriority qp = null;
        List technicalList = new ArrayList();
        List insList = InteqaService.getInstance().getSourceFileList(p.getProjectId());
        INDelivery indel = InteqaService.getInstance().getINDelivery(p.getProjectId());
        JSONArray requirement = new JSONArray();
        JSONArray instruction = new JSONArray();
        if (null != indel) {
            List inDelR = InteqaService.getInstance().getInDeliveryReqGrid(indel.getId(), "R");
            for (int i = 0; i < inDelR.size(); i++) {
                INDeliveryReq inDeliveryReq = (INDeliveryReq) inDelR.get(i);
                JSONObject jo = new JSONObject();
                if (porq.equalsIgnoreCase("quote")) {
                    if (inDeliveryReq.getFromPorQ().equalsIgnoreCase("Q")) {
                        jo.put("requirement", inDeliveryReq.getClientReqText());
                        jo.put("reqCheck", inDeliveryReq.isClientReqCheck());
                        jo.put("reqBy", inDeliveryReq.getClientReqBy());
                        jo.put("id", inDeliveryReq.getId());
                        requirement.put(jo);
                    }
                } else {
                    jo.put("requirement", inDeliveryReq.getClientReqText());
                    jo.put("reqCheck", inDeliveryReq.isClientReqCheck());
                    jo.put("reqBy", inDeliveryReq.getClientReqBy());
                    jo.put("id", inDeliveryReq.getId());
                    requirement.put(jo);
                }

            }

            List inDelI = InteqaService.getInstance().getInDeliveryReqGrid(indel.getId(), "I");
            for (int i = 0; i < inDelI.size(); i++) {
                INDeliveryReq inDeliveryReq = (INDeliveryReq) inDelI.get(i);
                JSONObject jo = new JSONObject();
                if (porq.equalsIgnoreCase("quote")) {
                    if (inDeliveryReq.getFromPorQ().equalsIgnoreCase("Q")) {
                        jo.put("requirement", inDeliveryReq.getClientReqText());
                        jo.put("reqCheck", inDeliveryReq.isClientReqCheck());
                        jo.put("instructionsFor", StandardCode.getInstance().noNull(inDeliveryReq.getInstructionsFor()));

                        jo.put("reqBy", inDeliveryReq.getClientReqBy());
                        jo.put("notes", inDeliveryReq.getNotes());
                        jo.put("id", inDeliveryReq.getId());
                        instruction.put(jo);
                    }
                } else {
                    jo.put("requirement", inDeliveryReq.getClientReqText());
                    jo.put("reqCheck", inDeliveryReq.isClientReqCheck());
                    jo.put("reqBy", inDeliveryReq.getClientReqBy());
                    jo.put("instructionsFor", StandardCode.getInstance().noNull(inDeliveryReq.getInstructionsFor()));
                    jo.put("notes", inDeliveryReq.getNotes());
                    jo.put("id", inDeliveryReq.getId());
                    instruction.put(jo);
                }

            }

        }

        if (porq.equalsIgnoreCase("quote")) {
            qp = QuoteService.getInstance().getSingleQuotePriority(q.getQuote1Id());
            List clq = QuoteService.getInstance().getClient_Quote(q.getQuote1Id());
            Client_Quote cq = (Client_Quote) clq.get(0);

            technicalList = QuoteService.getInstance().getTechnicalList(cq.getId());
//            request.setAttribute("q_technicalList", technicalList);
        } else {
            technicalList = ProjectService.getInstance().getProjectTechnicalList(p.getProjectId());
        }
        String indent = "&#9658";
        String emailMsgTxt = "";
        try {
            /////////////////////////////////////////////////////////////////////Basics/////////////////////////////////////////////////////////////////////////
            if (inbasic != null) {
                emailMsgTxt += "<div align='left' style='text-align: center; background-color: dimgray;height: 26px;color: white;margin: 5px 0px 5px 0px;line-height: 2;font-size: 11.0pt;letter-spacing: 5px;'><b>&nbsp;&nbsp;&nbsp;&nbsp; BASICS </b></div>";
                emailMsgTxt += "<div align='left' >";
                emailMsgTxt += "<table style=' font-size: 10.0pt; font-family: verdana,tahoma,arial,helvetica,sans-serif;'>";
                emailMsgTxt += "<tr style='"+styleTR+"'><td style='width:250px'><td></tr>";
                if (!(StandardCode.getInstance().noNull(inbasic.getServer())).equalsIgnoreCase("") || !StandardCode.getInstance().noNull(inbasic.getFtp()).equalsIgnoreCase("") || !StandardCode.getInstance().noNull(inbasic.getOther()).equalsIgnoreCase("")) {
                    emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+headerColor+"  width:250px;'> File Location</td><td>";
                    if (!StandardCode.getInstance().noNull(inbasic.getServer()).equalsIgnoreCase("")) {
                        emailMsgTxt += "<div>Server: <span style='color:darkred;'>" + inbasic.getServer().replace("\\", "\\\\") + "</span></div>";
                    }
                    if (!StandardCode.getInstance().noNull(inbasic.getFtp()).equalsIgnoreCase("")) {
                        emailMsgTxt += "<div>FTP: <span style='color:darkred;'>" + inbasic.getFtp().replace("\\", "\\\\") + "</span></div>";
                    }
                    if (!StandardCode.getInstance().noNull(inbasic.getOther()).equalsIgnoreCase("")) {
                        emailMsgTxt += "<div>Other: <span style='color:darkred;'>" + inbasic.getOther().replace("\\", "\\\\") + "</span></div>";
                    }
                    emailMsgTxt += "</td></tr>";
                }

                if (inbasic.isDtpReq2() == true || inbasic.isDtpReq1() == true || !inbasic.getTextBox1().equalsIgnoreCase("")) {

                    emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+headerColor+"  width:250px;'> DTP required  </td><td>";
                    if (inbasic.isDtpReq2() == true) {
                        emailMsgTxt += "<div>" + indent + " Yes  </div>";
//                        dtpAddress = true;
                        if (inbasic.isDtpReq21() == true) {
                            emailMsgTxt += "<div class='padl20'>" + indent + " Basic Formatting  </div>";
                        }
                        if (inbasic.isDtpReq22() == true) {
                            emailMsgTxt += "<div class='padl20'>" + indent + " Full DTP  </div>";
                        }

                    }
                    if (inbasic.isDtpReq1() == true) {
                        emailMsgTxt += "<div> No</div>";
                    }
                    emailMsgTxt += "</td></tr>";
                    if (!inbasic.getTextBox1().equalsIgnoreCase("")) {
                        emailMsgTxt += "<tr style='"+styleTR+"'><td>Notes:</td><td style='color:darkred;'> " + inbasic.getTextBox1().replace("\\", "\\\\") + "  </td></tr>";
                    }

                }
                if (inbasic.isGenGra1() == true || inbasic.isGenGra2() == true || inbasic.isGenGra3() == true || inbasic.isGenGra4() == true || !inbasic.getTextBox2().equalsIgnoreCase("")) {
                    emailMsgTxt += "<br><tr style='"+styleTR+"'><td style='"+headerColor+"  width:250px;'> General Graphics  </td><td>";
                    if (inbasic.isGenGra1() == true) {
                        emailMsgTxt += "<div>" + indent + "  No, graphic localization is not needed   </div>";
                    }
                    if (inbasic.isGenGra2() == true) {
                        emailMsgTxt += " <div>" + indent + " Yes, graphic localization is needed</div>";
                    }
                    if (inbasic.isGenGra3() == true) {
                        emailMsgTxt += "<div class='padl20'>" + indent + " Excel will receive editable graphics from client</div>";
                    }
                    if (inbasic.isGenGra4() == true) {
                        emailMsgTxt += " <div class='padl20'>" + indent + " Client will not provide editable graphics. Excel will recreate, fake or edit.</div>";
                    }
                    emailMsgTxt += "</td></tr>";
                    if (!inbasic.getTextBox2().equalsIgnoreCase("")) {
                        emailMsgTxt += "<tr style='"+styleTR+"'><td>Notes:</td><td style='color:darkred;'> " + inbasic.getTextBox2().replace("\\", "\\\\") + " </td></tr>";
                    }

                }

                if (inbasic.isScreen1() == true || inbasic.isScreen2() == true || inbasic.isScreen3() == true || inbasic.isScreen4() == true || !inbasic.getTextBox3().equalsIgnoreCase("")) {
                    emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+headerColor+"  width:250px;'> Screenshot </td><td>";
                    if (inbasic.isScreen1() == true) {
                        emailMsgTxt += "<div>" + indent + " No, screenshot localization is not needed   </div>";
                    }
                    if (inbasic.isScreen2() == true) {
                        emailMsgTxt += "<div>" + indent + " Yes, screenshot localization is needed </div>";
                    }
                    if (inbasic.isScreen3() == true) {
                        emailMsgTxt += "<div class='padl20'>" + indent + "  Excel will receive localized screenshots from client  </div>";
                    }
                    if (inbasic.isScreen4() == true) {
                        emailMsgTxt += "<div class='padl20'>" + indent + " Excel will create localized screenshots </div>";
                    }
                    emailMsgTxt += "</td></tr>";
                    if (!inbasic.getTextBox3().equalsIgnoreCase("")) {
                        emailMsgTxt += "<tr style='"+styleTR+"'><td>Notes:</td><td style='color:darkred;'> " + inbasic.getTextBox3().replace("\\", "\\\\") + " </td></tr>";
                    }

                }

                if (inbasic.isClientReview1() == true || inbasic.isClientReview2() == true || inbasic.isClientReview3() == true || inbasic.isClientReview4() == true || inbasic.isClientReview5() == true || !inbasic.getTextBox5().equalsIgnoreCase("")) {
                    emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+headerColor+"  width:250px;'> Client Review </td><td>";
                    if (inbasic.isClientReview1() == true) {
                        emailMsgTxt += "<div>" + indent + " No </div>";
                    }
                    if (inbasic.isClientReview2() == true) {
                        emailMsgTxt += "<div>" + indent + " Yes</div>";
                    }
                    if (inbasic.isClientReview3() == true) {
                        emailMsgTxt += "<div>" + indent + "  External Review File (SDL Trados)</div>";
                    }
                    if (inbasic.isClientReview4() == true) {
                        emailMsgTxt += "<div>" + indent + "  Bilingual table </div>";
                    }
                    if (inbasic.isClientReview5() == true) {
                        emailMsgTxt += "<div>  Other: " + inbasic.getTextBox4().replace("\\", "\\\\") + "</div>";
                    }
                    emailMsgTxt += "</td></tr>";
                    if (!inbasic.getTextBox5().equalsIgnoreCase("")) {
                        emailMsgTxt += "<tr style='"+styleTR+"'><td></td><td style='color:darkred;'>" + inbasic.getTextBox5().replace("\\", "\\\\") + "</td></tr>";
                    }

                }

                if (inbasic.isDeliveryFormat1() == true || inbasic.isDeliveryFormatOth() == true) {
                    emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+headerColor+"  width:250px;'> Delivery Format </td><td>";
                    if (inbasic.isDeliveryFormat1() == true) {
                        emailMsgTxt += "<div>" + indent + " Same as source </div>";
                    }
                    if (inbasic.isDeliveryFormatOth() == true) {
                        emailMsgTxt += "<div> Other:</div>";
                    }
                    emailMsgTxt += "</td></tr>";
                    if (!inbasic.getDeliveryFormatOthText().equalsIgnoreCase("")) {
                        emailMsgTxt += "<tr style='"+styleTR+"'><td>Notes:</td><td style='color:darkred;'> " + inbasic.getDeliveryFormatOthText().replace("\\", "\\\\") + " </td></tr>";
                    }

                }

                if (inbasic.isDeliveryFiles1() == true || inbasic.isDeliveryFiles2() == true || inbasic.isDeliveryFilesOth() == true) {
                    emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+headerColor+"  width:250px;'> Deliverable(s)</td><td>";

                    if (inbasic.isDeliveryFiles1() == true) {
                        emailMsgTxt += "<div>" + indent + " Single-language files </div>";
                    }
                    if (inbasic.isDeliveryFiles2() == true) {
                        emailMsgTxt += "<div>" + indent + " Multi-language</div>";
                    }
                    if (inbasic.isDeliveryFilesOth() == true) {
                        emailMsgTxt += "<div> Other:  </div>";
                    }
                    emailMsgTxt += "</td></tr>";
                    if (!inbasic.getDeliveryFilesOthText().equalsIgnoreCase("")) {
                        emailMsgTxt += "<tr style='"+styleTR+"'><td>Notes:</td><td style='color:darkred;'> " + inbasic.getDeliveryFilesOthText().replace("\\", "\\\\") + " </td></tr>";
                    }
                }
                if (!inbasic.getOtherInstruction().equalsIgnoreCase("")) {
                    emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+headerColor+"  width:250px;'> Other Special Instruction: </td><td style='color:darkred;'>" + inbasic.getOtherInstruction().replace("\\", "\\\\") + "</td></tr>";
                }

                if (!insList.isEmpty()) {

                    emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+headerColor+"  width:250px;'>  Source File Information </td>";

                    emailMsgTxt += "<td><table align='center' style=' width:100%' class='reference'>";
                    emailMsgTxt += "<tr style='"+styleTR+"'><th style='"+referenceTh+"'>Quantity</th><th style='"+referenceTh+"'>Extension For</th><th style='"+referenceTh+"'>Notes</th></tr>";
                    for (int i = 0; i < insList.size(); i++) {
                        INSourceFile ins = (INSourceFile) insList.get(i);
                        emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+styleTD+"'>" + ins.getQuantity() + "</td><td style='"+styleTD+"'>" + ins.getExtension() + "</td><td style='"+styleTD+"'>" + ins.getNotes().replace("\\", "\\\\") + "</td></tr>";
                    }
                    emailMsgTxt += "</table>";
                    emailMsgTxt += "</td></tr>";

                }

                List inRefList = InteqaService.getInstance().getInReference(p.getProjectId());
                if (inRefList.size() > 0) {
                    emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+headerColor+"  width:250px;'>Reference Project/Quote</td><td>";
                    emailMsgTxt += "<table align='center' class='reference'";
                    emailMsgTxt += "<tr style='"+styleTR+"'><th style='"+referenceTh+"'>Quote</th><th style='"+referenceTh+"'>Project</th><th style='"+referenceTh+"'>Products</th><th style='"+referenceTh+"'>Products Description</th></tr>";
                    for (int i = 0; i < inRefList.size(); i++) {
                        INReference ref = (INReference) inRefList.get(i);
                        emailMsgTxt += "<tr style='"+styleTR+"'>";
                        try {
                            emailMsgTxt += "<td style='"+styleTD+"'>" + ref.getQuoteNumber().split("#")[0] + "</td>";
                        } catch (Exception e) {
                            emailMsgTxt += " <td style='"+styleTD+"'></td>";
                        }
                        if (Integer.parseInt(ref.getProjectNumber().split("#")[0].replaceAll("[A-Za-z]", "")) > 0) {
                            try {
                                emailMsgTxt += " <td style='"+styleTD+"'>" + ref.getProjectNumber().split("#")[0] + "</td>";
                            } catch (Exception e) {
                                emailMsgTxt += " <td style='"+styleTD+"'></td>";
                            }
                        } else {
                            emailMsgTxt += " <td style='"+styleTD+"'></td>";
                        }

                        emailMsgTxt += " <td style='"+styleTD+"'>" + ref.getProduct() + "</td>";
                        emailMsgTxt += " <td style='"+styleTD+"'>" + ref.getDescription() + "</td>";
                        emailMsgTxt += "</tr>";
                    }
                    emailMsgTxt += "</table></td></tr>";
                }
                emailMsgTxt += " </table>";
                emailMsgTxt += " </div>";

            }
        } catch (Exception e) {
        }

/////////////////////////////////////////////////////////////////////Engg/////////////////////////////////////////////////////////////////////////
        try {
            if (inengg != null) {
                // recipientEmailId += "engineering@xltrans.com";

                emailMsgTxt += "<div align='left' style='text-align: center; background-color: dimgray;height: 26px;color: white;margin: 5px 0px 5px 0px;line-height: 2;font-size: 11.0pt;letter-spacing: 5px;'><b>&nbsp;&nbsp;&nbsp;&nbsp; ENGINEERING </b></div>";
                emailMsgTxt += "<div  align='left'>";
                emailMsgTxt += " <table>";
                emailMsgTxt += "<tr style='"+styleTR+"'><td style='width:250px'>&nbsp;<td></tr>";
                if (inengg.isFilePrep01() == true || inengg.isFilePrep02() == true || inengg.isFilePrep1() == true || inengg.isFilePrep2() == true || inengg.isFilePrep3() == true
                        || inengg.isFilePrep4() == true || inengg.isFilePrep5() == true || inengg.isFilePrep6() == true) {
                    emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+headerColor+"  width:250px;' td-regular'> File preparation </td><td>";

                    if (inengg.isFilePrep01() == true) {
                        emailMsgTxt += "<div>" + indent + " Prep File</div>";
                    }
                    if (inengg.isFilePrep02() == true) {
                        emailMsgTxt += "<div>" + indent + " Do not Prep Files </div>";
                    }
                    if (inengg.isFilePrep1() == true) {
                        emailMsgTxt += "<div>" + indent + " Leave source file as is.</div>";
                    }
                    if (inengg.isFilePrep2() == true) {
                        emailMsgTxt += "<div>" + indent + " Split source file.</div>";
                    }
                    if (inengg.isFilePrep3() == true) {
                        emailMsgTxt += "<div>" + indent + " Remove embedded images.</div>";
                    }
                    if (inengg.isFilePrep4() == true) {
                        emailMsgTxt += "<div>" + indent + " Text extraction in Word table format</div>";
                    }
                    if (inengg.isFilePrep5() == true) {
                        emailMsgTxt += "<div>" + indent + " PDF-to-Word conversion</div>";
                    }
                    if (inengg.isFilePrep6() == true) {
                        emailMsgTxt += "<div>" + indent + " OCR - Text recognition</div>";
                    }
                    emailMsgTxt += "</td></tr>";
                    if (!StandardCode.getInstance().noNull(inengg.getTextBox1()).equalsIgnoreCase("")) {

                        emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+headerColor+"  width:250px;'>Notes: </td><td class='notes td-regular'>" + StandardCode.getInstance().noNull(inengg.getTextBox1()) + "";
                    }
                    emailMsgTxt += "</td></tr>";
                }
                if (inengg.isExtract1() == true || inengg.isExtract2() == true) {
                    emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+headerColor+"  width:250px;'> Extract/Merge </td><td class='td-regular'>";
                    if (inengg.isExtract1() == true) {
                        emailMsgTxt += "<div>" + indent + " Automatically: Filter tools (CopyFlow, StoryCollector, Sysfilter, etc)</div>";
                    }
                    if (inengg.isExtract2() == true) {
                        emailMsgTxt += "<div>" + indent + " Manually: Copy & Paste </div>";
                    }
                    emailMsgTxt += "</td></tr>";
                    if (!StandardCode.getInstance().noNull(inengg.getTextBox1()).equalsIgnoreCase("")) {
                        emailMsgTxt += "<tr style='"+styleTR+"'><td>Notes:</td><td class='notes td-regular'>" + StandardCode.getInstance().noNull(inengg.getExtractText()) + "";
                    }
                    emailMsgTxt += "</td></tr>";
                }
                if (inengg.isLpr1() == true || inengg.isLpr2() == true || inengg.isLpr3() == true || inengg.isLpr4() == true || inengg.isLpr5() == true || inengg.isLpr6() == true) {
                    emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+headerColor+" width:250px;'>Linguistic and Production Requirements </td><td>";
                    if (inengg.isLpr1() == true) {
                        emailMsgTxt += "<div>" + indent + " Keep file name as is </div>";
                    }
                    if (inengg.isLpr2() == true) {
                        emailMsgTxt += "<div>" + indent + " Keep folder structure as is </div>";
                    }
                    if (inengg.isLpr3() == true) {
                        emailMsgTxt += "<div>" + indent + " Use Trados Studio &nbsp  <span style='color:darkred;'>" + inengg.getLpr3Text() + " </span> </div>";
                    }
                    if (inengg.isLpr5() == true) {
                        emailMsgTxt += "<div class=' notes'> " + inengg.getLpr5Text() + "  </div>";
                    }
                    if (inengg.isLpr6() == true) {
                        emailMsgTxt += "<div class=' notes'> " + inengg.getLpr6Text() + "  </div>";
                    }
                    emailMsgTxt += "</td></tr>";
                    if (!StandardCode.getInstance().noNull(inengg.getLprInfo()).equalsIgnoreCase("")) {
                        emailMsgTxt += "<tr style='"+styleTR+"'><td>Notes:</td><td style='color:darkred;'>" + StandardCode.getInstance().noNull(inengg.getLprInfo()) + "";
                    }
                    emailMsgTxt += "</td></tr>";
                }
                if (inengg.isCheck1() == true || inengg.isCheck2() == true || inengg.isCheck4() == true || inengg.isCheck5() == true) {
                    emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+headerColor+"  width:250px;'>Engineering Checklist </td><td>";

                    if (inengg.isCheck1() == true) {
                        emailMsgTxt += "<div>" + indent + "  Word count analysis is performed (Trados or other)</div>";
                    }
                    if (inengg.isCheck2() == true) {
                        emailMsgTxt += "<div>" + indent + " Files are prepared and ready for translation: </div>";
                    }

                    if (inengg.isCheck21() == true) {
                        emailMsgTxt += "<div>" + indent + " Yes</div>";
                    }
                    if (inengg.isCheck22() == true) {
                        emailMsgTxt += "<div>" + indent + " No</div>";
                    }
                    emailMsgTxt += "<div class=' notes'>" + StandardCode.getInstance().noNull(inengg.getCheck3Text()) + "</div>";
                    if (inengg.isCheck4() == true) {
                        emailMsgTxt += "<div>  Production instructions are prepared/provided (e.g. for linguist, PM, Sales)</div>";
                    }
                    if (inengg.isCheck5() == true) {
                        emailMsgTxt += "<div> ExcelNet and Quote directory on server are updated</div>";
                    }
                    emailMsgTxt += "<div class=' notes'>" + StandardCode.getInstance().noNull(inengg.getCheckText1()) + "</div>";
                    emailMsgTxt += "<div class=' notes'>" + StandardCode.getInstance().noNull(inengg.getCheckText2()) + "</div>";
                    emailMsgTxt += "</td></tr>";
                }
                if (!StandardCode.getInstance().noNull(inengg.getEnggNotes()).equalsIgnoreCase("")) {
                    emailMsgTxt += " <tr style='"+styleTR+"'><td>Notes:</td><td style='color:darkred;'>" + StandardCode.getInstance().noNull(inengg.getEnggNotes());
                    emailMsgTxt += "</td></tr>";
                }
                emailMsgTxt += " </table>";
                emailMsgTxt += "</div><br><br>";
            }

        } catch (Exception e) {
        }
////////////////////////////////DTP//////////////////////////////////////////

        if (indtp != null) {
            emailMsgTxt += "<div align='left' style='text-align: center; background-color: dimgray;height: 26px;color: white;margin: 5px 0px 5px 0px;line-height: 2;font-size: 11.0pt;letter-spacing: 5px;'><b>&nbsp;&nbsp;&nbsp;&nbsp; DTP </b></div>";
            emailMsgTxt += "<div  align='left'>";
            emailMsgTxt += " <table>";
            emailMsgTxt += "<tr style='"+styleTR+"'><td style='width:250px'><td></tr>";
            if (indtp.isSrcPf1() == true || indtp.isSrcPf2() == true) {
                emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+headerColor+"  width:250px;'>Source </td><td>";
                if (indtp.isSrcPf1() == true) {
                    emailMsgTxt += "<div>" + indent + " PC  </div>";
                }
                if (indtp.isSrcPf2() == true) {
                    emailMsgTxt += "<div>" + indent + " Mac  </div>";
                }
                if (!StandardCode.getInstance().noNull(indtp.getDtpToolName()).equalsIgnoreCase("")) {
                    emailMsgTxt += "<div>DTP tool name:  - " + indtp.getDtpToolName() + "  </div>";

                }
                emailMsgTxt += "</td></tr>";
            }
            if (technicalList.size() > 0) {

                emailMsgTxt += "<tr style='"+styleTR+"'><td></td><td> <table align='center' class='reference' width='100%'>";
                emailMsgTxt += "<tr style='"+styleTR+"'><th style='"+referenceTh+"'>Source OS</th><th style='"+referenceTh+"'>Target OS</th><th style='"+referenceTh+"'>Source Application</th><th style='"+referenceTh+"'>Target Application</th><th style='"+referenceTh+"'>Source Version</th><th style='"+referenceTh+"'>Target Version</th><th style='"+referenceTh+"'>Page Count</th></tr>";

                if (porq.equalsIgnoreCase("project")) {
                    for (int i = 0; i < technicalList.size(); i++) {

                        Project_Technical tech = (Project_Technical) technicalList.get(i);
                        emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+styleTD+"'>" + tech.getSourceos() + "</td><td style='"+styleTD+"'>" + tech.getTargetos() + "</td> <td style='"+styleTD+"'>" + tech.getSourceapp() + "</td> <td style='"+styleTD+"'>" + tech.getTargetapp() + "</td><td style='"+styleTD+"'>" + tech.getSourcever() + "</td> <td style='"+styleTD+"'>" + tech.getTargetver() + "</td> <td style='"+styleTD+"'>" + StandardCode.getInstance().noNull(tech.getUnitCount()) + "</td> </tr>";

                    }
                } else {
                    for (int i = 0; i < technicalList.size(); i++) {

                        Technical tech = (Technical) technicalList.get(i);
                        emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+styleTD+"'>" + tech.getSourceOs() + "</td><td style='"+styleTD+"'>" + tech.getTargetOs() + "</td> <td style='"+styleTD+"'>" + tech.getSourceApplication() + "</td> <td style='"+styleTD+"'>" + tech.getTargetApplication() + "</td><td style='"+styleTD+"'>" + tech.getSourceVersion() + "</td> <td style='"+styleTD+"'>" + tech.getTargetVersion() + "</td> <td style='"+styleTD+"'>" + tech.getUnitCount() + "</td> </tr>";

                    }
                }

                emailMsgTxt += "</table>";
                emailMsgTxt += "</td></tr>";
            }

            if (indtp.isSrcFont1() == true || indtp.isSrcFont2() == true || indtp.isTgtFont1() == true || indtp.isTgtFont2() == true) {
                emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+headerColor+"  width:250px;'>Font </td><td>";
                emailMsgTxt += "<div>Source </div>";
                if (indtp.isSrcFont1() == true) {
                    emailMsgTxt += " <div class='padl20'>" + indent + " Standard fonts (Arial, Times, Courier, etc)  </div>";
                }
                if (indtp.isSrcFont2() == true) {
                    emailMsgTxt += " <div class='padl20'>" + indent + " Fancy fonts  </div>";
                }
                emailMsgTxt += "<div>Target </div>";
                if (indtp.isTgtFont1() == true) {
                    emailMsgTxt += "<div class='padl20'>" + indent + " Same fonts used on the source  </div>";
                }
                if (indtp.isTgtFont2() == true) {
                    emailMsgTxt += " <div class='padl20'>" + indent + " Special fonts are necessary  </div>";
                }

                if (indtp.isDtpTool1() == true) {
                    emailMsgTxt += "<div>Target languages will use the same DTP tool, version and platform as the source </div>";
                }
                if (indtp.isDtpToolOth() == true) {
                    emailMsgTxt += " <div>Other specific tools/version/platform are necessary: <span style='color:darkred;'>" + indtp.getDtpToolOthText() + "  </span> </div>";
                }
                emailMsgTxt += "</td></tr>";
            }
            if (indtp.isCheck1() == true || indtp.isCheck2() == true || indtp.isCheck3() == true || indtp.isCheck4() == true || indtp.isCheck5() == true) {
                emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+headerColor+"  width:250px;'>DTP Checklist </td><td>";
                if (indtp.isCheck1() == true) {
                    emailMsgTxt += "<div>" + indent + " Low-res PDF for Sales and Production created  </div>";
                }
                if (indtp.isCheck3() == true) {
                    emailMsgTxt += " <div>" + indent + " Document analysis is performed </div>";
                }
                if (indtp.isCheck21() == true) {
                    emailMsgTxt += " <div>" + indent + " Yes  </div>";
                }
                if (indtp.isCheck22() == true) {
                    emailMsgTxt += " <div>" + indent + " No  </div>";
                }
                if (indtp.isCheck22() == true) {
                    emailMsgTxt += " <div class=' notes'>" + StandardCode.getInstance().noNull(indtp.getCheckText1()) + "</div>";

                }
                if (indtp.isCheck4() == true) {
                    emailMsgTxt += "<div>" + indent + " Production instructions are prepared/provided (e.g. for linguist, PM, Sales)</div>";
                }
                if (indtp.isCheck5() == true) {
                    emailMsgTxt += " <div>" + indent + " ExcelNet and Quote directory on server are updated</div>";
                }
                emailMsgTxt += "<div class=' notes'>" + StandardCode.getInstance().noNull(indtp.getCheckText2()) + "</div>";
                emailMsgTxt += "<div class=' notes'>" + StandardCode.getInstance().noNull(indtp.getCheckText3()) + "</div>";
                emailMsgTxt += "</td></tr>";
            }
            if (indtp.isCheck6() == true || indtp.isCheck7() == true) {
                emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+headerColor+"  width:250px;'>General Graphics / Screenshots </td><td>";
                if (indtp.isCheck6() == true) {
                    emailMsgTxt += " <div>" + indent + " outlined (flattened) images <span style='color:darkred;'>" + StandardCode.getInstance().noNull(indtp.getCheck6text()) + " </span></div>";
                }
                if (indtp.isCheck7() == true) {
                    emailMsgTxt += "<div>" + indent + " editable (layered) images  <span style='color:darkred;'>" + StandardCode.getInstance().noNull(indtp.getCheck7text()) + "</span></div>";
                }
                emailMsgTxt += "</td></tr>";
            }
            if (!StandardCode.getInstance().noNull(indtp.getDtpnotes()).equalsIgnoreCase("")) {
                emailMsgTxt += " <tr style='"+styleTR+"'><td>Notes:</td><td style='color:darkred;'>" + StandardCode.getInstance().noNull(indtp.getDtpnotes()) + " ";
                emailMsgTxt += "</td></tr>";
            }
            emailMsgTxt += " </table>";
            emailMsgTxt += "</div>";
        }
//////////////////////////////////////////////////////////////////////////////////////////
        emailMsgTxt += "<div align='left' style='text-align: center; background-color: dimgray;height: 26px;color: white;margin: 5px 0px 5px 0px;line-height: 2;font-size: 11.0pt;letter-spacing: 5px;'><b>&nbsp;&nbsp;&nbsp;&nbsp; CLIENT REQUIREMENT </b></div><br>";
        if (requirement.length() > 0 || instruction.length() > 0) {

            if (requirement.length() > 0) {
                emailMsgTxt += "<table align='center' class='reference ' width='90%' >";
                emailMsgTxt += "<tr style='"+styleTR+"'><th style='"+referenceTh+"'>Requirements</th></tr>";
                for (int r = 0; r < requirement.length(); r++) {
                    JSONObject reqObj = requirement.getJSONObject(r);
                    emailMsgTxt += "<tr style='"+styleTR+"'>";
                    emailMsgTxt += "<td style='"+styleTD+notes+"'>" + reqObj.getString("requirement") + "</td>";
//            if(reqObj.getString("reqCheck").equalsIgnoreCase("true")){
//                emailMsgTxt += " <td><input type=\"checkbox\" checked disabled></td>";
//            }else{
//                emailMsgTxt += "<td></td>";
//            }
//            emailMsgTxt += " <td>"+reqObj.getString("reqBy")+"</td>";
                    emailMsgTxt += "</tr>";
                }
                emailMsgTxt += "</table><br/>";
            }
        }
            emailMsgTxt += "<div align='left' style='text-align: center; background-color: dimgray;height: 26px;color: white;margin: 5px 0px 5px 0px;line-height: 2;font-size: 11.0pt;letter-spacing: 5px;'><b>&nbsp;&nbsp;&nbsp;&nbsp; INTERNAL </b></div><br>";
             if (requirement.length() > 0 || instruction.length() > 0) {
            if (instruction.length() > 0) {
                emailMsgTxt += "<table align='center' class='reference' width='90%'>";
                emailMsgTxt += "<tr style='"+styleTR+"'><th style='"+referenceTh+"'>Instructions</th><th style='"+referenceTh+"'>Instructions For</th><th style='"+referenceTh+"'>Notes</th></tr>";
                for (int i = 0; i < instruction.length(); i++) {
                    JSONObject insObj = instruction.getJSONObject(i);
                    emailMsgTxt += "<tr style='"+styleTR+"'>";
                    emailMsgTxt += "<td style='"+styleTD+notes+"'>" + insObj.getString("requirement") + "</td>";
                    emailMsgTxt += "<td style='"+styleTD+notes+"'>" + insObj.getString("instructionsFor") + "</td>";
//            if(insObj.getString("reqCheck").equalsIgnoreCase("true")){
//                emailMsgTxt += " <td><input type=\"checkbox\" checked disabled></td>";
//            }else{
//                emailMsgTxt += "<td></td>";
//            }
//            emailMsgTxt += " <td>"+insObj.getString("reqBy")+"</td>";
                    emailMsgTxt += "<td style='"+styleTD+notes+"'>" + insObj.getString("notes").replaceAll("\\\\", "\\\\\\\\") + "</td>";
                    emailMsgTxt += "</tr>";
                }
                emailMsgTxt += "</table><br/>";
            }
        }
             
         emailMsgTxt += getRequirements(p,q); 
        

//////////////////////////////////////////////////////////////////////////////////////////
        try {
            List linguisticStyles = ClientService.getInstance().getNotesList(p.getCompany().getClientId(), "LIN");
            List billingReqs = ClientService.getInstance().getBillingReqListForClient(p.getCompany().getClientId().toString());
            List regulatoryList = ClientService.getInstance().getRegulatoryListForClient(p.getCompany().getClientId().toString());
            List auditList = ClientService.getInstance().getAuditListForClient(p.getCompany().getClientId().toString());
            List serviceList = ClientService.getInstance().getServiceListForClient(p.getCompany().getClientId().toString());
            List techStyles = ClientService.getInstance().getNotesList(p.getCompany().getClientId(), "TECH");
            emailMsgTxt += "<div align='left' style='text-align: center; background-color: dimgray;height: 26px;color: white;margin: 5px 0px 5px 0px;line-height: 2;font-size: 11.0pt;letter-spacing: 5px;'><b>&nbsp;&nbsp;&nbsp;&nbsp; PROFILE </b></div>";

            emailMsgTxt += "<table align='center'>";

            if (null != linguisticStyles && linguisticStyles.size() > 0) {
                emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+headerColor+"  width:250px;'>Linguistic</td></tr>";
                emailMsgTxt += "<tr style='"+styleTR+"'><td>";
                emailMsgTxt += "<table align='center' class='reference' >";
                emailMsgTxt += "<tr style='"+styleTR+"'><th style='"+referenceTh+"'>Note</th><th style='"+referenceTh+"'>Author</th>   </tr>         ";

                for (int i = 0; i < linguisticStyles.size(); i++) {
                    ClientNotes pr = (ClientNotes) linguisticStyles.get(i);

                    emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+styleTD+notes+"'>" + pr.getNotes().replaceAll("\\\\", "\\\\\\\\") + "</td> <td style='"+styleTD+notes+"'>" + pr.getAuthor() + "</td></tr>    ";
                }
                emailMsgTxt += "</table><br/></td></tr>";
            }

            if (null != techStyles && techStyles.size() > 0) {
                emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+headerColor+"  width:250px;'>Technical</td></tr>";
                emailMsgTxt += "<tr style='"+styleTR+"'><td>";
                emailMsgTxt += "<table align='center' class='reference' >";
                emailMsgTxt += "<tr style='"+styleTR+"'>";
                emailMsgTxt += "<th style='"+referenceTh+"'>Note</th>";

                emailMsgTxt += "<th style='"+referenceTh+"'>Author</th>    ";
                emailMsgTxt += "</tr>     ";

                for (int i = 0; i < techStyles.size(); i++) {
                    ClientNotes tech = (ClientNotes) techStyles.get(i);

                    emailMsgTxt += "<tr style='"+styleTR+"'>";
                    emailMsgTxt += "<td style='"+styleTD+notes+"'>" + tech.getNotes().replaceAll("\\\\", "\\\\\\\\") + "</td>";
                    emailMsgTxt += "<td style='"+styleTD+notes+"'>" + tech.getAuthor() + "</td> ";
                    emailMsgTxt += "</tr>                 ";
                }
                emailMsgTxt += "</table><br/></td></tr>";
            }

            if (null != serviceList && serviceList.size() > 0) {
                emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+headerColor+"  width:250px;'>Service</td></tr>";
                emailMsgTxt += "<tr style='"+styleTR+"'><td>";
                emailMsgTxt += "<table align='center' class='reference' >";
                emailMsgTxt += "<tr style='"+styleTR+"'>";
                emailMsgTxt += "<th style='"+referenceTh+"'>Service</th>";
                emailMsgTxt += "<th style='"+referenceTh+"'>Requirement</th>";
                emailMsgTxt += " <th style='"+referenceTh+"'>Last Edited By</th>                     ";
                emailMsgTxt += "</tr>                  ";

                for (int i = 0; i < serviceList.size(); i++) {
                    app.extjs.vo.ClientService pr = (app.extjs.vo.ClientService) serviceList.get(i);

                    emailMsgTxt += "<tr style='"+styleTR+"'>";
                    emailMsgTxt += "<td style='"+styleTD+notes+"'>" + pr.getService() + "</td>";
                    emailMsgTxt += " <td style='"+styleTD+notes+"'>" + pr.getRequirements() + "</td>";
                    emailMsgTxt += " <td style='"+styleTD+notes+"'>" + pr.getLast_edited_id() + "</td>";

                    emailMsgTxt += "</tr>                 ";
                }
                emailMsgTxt += "</table><br/></td></tr>";
            }
            if (null != regulatoryList && regulatoryList.size() > 0) {
                emailMsgTxt += "<tr style='"+styleTR+"'><td>Regulatory</td></tr>";
                emailMsgTxt += "<tr style='"+styleTR+"'><td>";
                emailMsgTxt += "<table align='center' class='reference' >";
                emailMsgTxt += "<tr style='"+styleTR+"'>";
                emailMsgTxt += "<th style='"+referenceTh+"'>Type</th>";
                emailMsgTxt += "<th style='"+referenceTh+"'>Requirement</th>";
                emailMsgTxt += "<th style='"+referenceTh+"'>Last Edited By</th>      ";
                emailMsgTxt += "</tr>       ";

                for (int i = 0; i < regulatoryList.size(); i++) {
                    app.extjs.vo.Regulatory pr = (app.extjs.vo.Regulatory) regulatoryList.get(i);

                    emailMsgTxt += "<tr style='"+styleTR+"'>";
                    emailMsgTxt += "<td style='"+styleTD+notes+"'>" + pr.getRegulatory_type() + "</td>";
                    emailMsgTxt += "<td style='"+styleTD+notes+"'>" + pr.getRegulatory_requirement() + "</td>";
                    emailMsgTxt += "<td style='"+styleTD+notes+"'>" + pr.getEdited_by() + "</td>";
                    emailMsgTxt += "</tr>            ";
                }
                emailMsgTxt += "</table><br/></td></tr>";
            }

            if (null != billingReqs && billingReqs.size() > 0) {
                emailMsgTxt += "<tr style='"+styleTR+"'><td style='"+headerColor+"  width:250px;'>Accounting</td></tr>";
                emailMsgTxt += "<tr style='"+styleTR+"'><td>";
                emailMsgTxt += "<table align='center' class='reference'>";
                emailMsgTxt += "<tr style='"+styleTR+"'>";
                emailMsgTxt += "<th style='"+referenceTh+"'>Requirement</th>";
                emailMsgTxt += "<th style='"+referenceTh+"'>Edited By</th>";

                emailMsgTxt += "</tr>                ";

                for (int i = 0; i < billingReqs.size(); i++) {
                    app.extjs.vo.BillingRequirement pr = (app.extjs.vo.BillingRequirement) billingReqs.get(i);

                    emailMsgTxt += "<tr style='"+styleTR+"'> ";
                    emailMsgTxt += "<td style='"+styleTD+notes+"'>" + pr.getRequirement() + "</td>";
                    emailMsgTxt += "<td style='"+styleTD+notes+"'>" + pr.getEdited_by() + "</td>";

                    emailMsgTxt += "</tr>     ";
                }
                emailMsgTxt += "</table><br/></td></tr>";
            }

            emailMsgTxt += "</table>";
            emailMsgTxt += "<br/>";

            emailMsgTxt += "<br>";

        } catch (Exception e) {
        }
        return emailMsgTxt;
    }

    
   
    public String getReqAnalysisEmailCss() {
        String emailMsgTxt = "<style>"
                + "div{font-size: 10.0pt; font-family: verdana,tahoma,arial,helvetica,sans-serif;}"
                + "table,th,td,input,textarea {font-size: 10.0pt;font-family: verdana,tahoma,arial,helvetica,sans-serif;}"
                + "table tr td[class=\"td-regular\"]{font-family: 'Verdana', Arial, sans-serif; font-size:10.0pt; word-break: break-word;}\n" 
                + ".head{text-align: center; background-color: dimgray;height: 26px;color: white;margin: 5px 0px 5px 0px;line-height: 2;font-size: 11.0pt;letter-spacing: 5px;}"
                + "table.reference,table.tecspec{border-collapse:collapse;width:100%;}"
                + "table.reference tr:nth-child(odd)	{background-color:#E0EAFF;}"
                + "table.reference tr:nth-child(even)	{background-color:#ffffff;}"
                + "table.reference tr.fixzebra{background-color:#f1f1f1;}"
                + "table.reference th{color:#ffffff;background-color:#555555;border:1px solid #555555;padding:3px;vertical-align:top;text-align:left;}"
                + "table.reference th a:link,table.reference th a:visited{color:#ffffff;}"
                + "table.reference th a:hover,table.reference th a:active{color:#EE872A;}"
                + "table.reference td{border:1px solid #d4d4d4;padding:5px;padding-top:7px;padding-bottom:7px;vertical-align:top;}"
                + "table.reference td.example_code{vertical-align:bottom;}"
                + "table.summary{border:1px solid #d4d4d4;padding:5px;font-size:10.0pt;color:#555555;background-color:#fafad2;}"
                + "td.wdth200{width:250;}"
                + "tr{vertical-align: top;padding-top:7px;} "
                + ".padl20{padding-left: 20px} "
                + ".notes{color:darkred;}"
                
                + ".divTable{display: table;}"
                + ".divTableRow {display: table-row;}"
                + ".divTableCell, .divTableHead {display: table-cell;padding: 2px 10px;}"
                + ".divTableBody {display: table-row-group;}"
                + ".headerColor{color:blue;}"
                + ".checkmark{font-family: arial;-ms-transform: scaleX(-1) rotate(-35deg);-webkit-transform: scaleX(-1) rotate(-35deg);transform: scaleX(-1) rotate(-35deg);}"
                + "</style>"
                + "<link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.4.1/css/all.css\" integrity=\"sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz\" crossorigin=\"anonymous\">"
                + "</head>";

        return emailMsgTxt;

    }

    public String getReqAnalysisEmailLanguage(Quote1 q, Project p, boolean isChange, String changeNumber) {

        List sourcelang1 = new ArrayList();
        if (q != null) {
            sourcelang1 = QuoteService.getInstance().getSourceLang1(q);
        } else {
            sourcelang1 = ProjectService.getInstance().getSourceDoc(p);
        }

        StringBuilder sources1 = new StringBuilder();
        StringBuilder targets = new StringBuilder();
        String targetAbb = "";
        List targetList = new ArrayList();
        if (!isChange) {
            if (p.getSourceDocs() != null) {
                for (Iterator iterSource = sourcelang1.iterator(); iterSource.hasNext();) {
                    SourceDoc sd = (SourceDoc) iterSource.next();
                    sources1.append(sd.getLanguage()).append("  ");
                    if (sd.getTargetDocs() != null) {
                        for (Iterator iterTarget = sd.getTargetDocs().iterator(); iterTarget.hasNext();) {
                            TargetDoc td = (TargetDoc) iterTarget.next();
                            if (!td.getLanguage().equals("All")) {
                                targets.append(td.getLanguage()).append("  ");
                                targetList.add((String) LanguageAbs.getInstance().getAbs().get(td.getLanguage()));
                            }
                        }
                    }
                }
            }
        } else {
            if (p.getSourceDocs() != null) {
                List<String> tgtLst = new ArrayList();
                List<String> srcLst = new ArrayList();
                for (Iterator iterSource = sourcelang1.iterator(); iterSource.hasNext();) {
                    SourceDoc sd = (SourceDoc) iterSource.next();
                    //System.out.println("&&&"+sd.getTargetDocs().size()+sd.getSourceDocId());
                    if (sd.getTargetDocs() != null) {
                        for (Iterator iterTarget = sd.getTargetDocs().iterator(); iterTarget.hasNext();) {
                            TargetDoc td = (TargetDoc) iterTarget.next();
                            if (!td.getLanguage().equals("All")) {

                                Set<LinTask> linTask = td.getLinTasks();
                                Set<DtpTask> dtpTask = td.getDtpTasks();
                                Set<EngTask> engTask = td.getEngTasks();
                                Set<OthTask> othTask = td.getOthTasks();
                                for (LinTask lt : linTask) {
                                    //System.out.println("&&&"+lt.getTaskName()+lt.getChangeDesc()+td.getLanguage()+sd.getLanguage());
                                    if (StandardCode.getInstance().noNull(lt.getChangeDesc()).equalsIgnoreCase(changeNumber)) {
                                        if (!tgtLst.contains(td.getLanguage())) {
                                            tgtLst.add(td.getLanguage());
                                            targets.append(td.getLanguage()).append("  ");
                                            targetList.add((String) LanguageAbs.getInstance().getAbs().get(td.getLanguage()));
                                        }
                                        if (!srcLst.contains(sd.getLanguage())) {
                                            srcLst.add(sd.getLanguage());
                                            sources1.append(sd.getLanguage()).append("  ");
                                        }

                                    }
                                }
                                for (DtpTask lt : dtpTask) {
                                    //System.out.println("&&&"+lt.getTaskName()+lt.getChangeDesc()+td.getLanguage()+sd.getLanguage());
                                    if (StandardCode.getInstance().noNull(lt.getChangeDesc()).equalsIgnoreCase(changeNumber)) {
                                        if (!tgtLst.contains(td.getLanguage())) {
                                            tgtLst.add(td.getLanguage());
                                            targets.append(td.getLanguage()).append("  ");
                                            targetList.add((String) LanguageAbs.getInstance().getAbs().get(td.getLanguage()));
                                        }
                                        if (!srcLst.contains(sd.getLanguage())) {
                                            srcLst.add(sd.getLanguage());
                                            sources1.append(sd.getLanguage()).append("  ");
                                        }

                                    }
                                }
                                for (EngTask lt : engTask) {
                                    //System.out.println("&&&"+lt.getTaskName()+lt.getChangeDesc()+td.getLanguage()+sd.getLanguage());
                                    if (StandardCode.getInstance().noNull(lt.getChangeDesc()).equalsIgnoreCase(changeNumber)) {
                                        if (!tgtLst.contains(td.getLanguage())) {
                                            tgtLst.add(td.getLanguage());
                                            targets.append(td.getLanguage()).append("  ");
                                            targetList.add((String) LanguageAbs.getInstance().getAbs().get(td.getLanguage()));
                                        }
                                        if (!srcLst.contains(sd.getLanguage())) {
                                            srcLst.add(sd.getLanguage());
                                            sources1.append(sd.getLanguage()).append("  ");
                                        }

                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
        Collections.sort(targetList);
        for (int i = 0; i < targetList.size(); i++) {
            if (targetAbb.equalsIgnoreCase("")) {
                targetAbb += targetList.get(i);
            } else {
                targetAbb += ", " + targetList.get(i);
            }
        }
        targetAbb += " (" + targetList.size() + ")";

        String emailMsgTxt = "<font color=\"blue\">Languages<br></font> "
                + "<table>"
                + "<tr style='"+styleTR+"'><td style='width:250px'><b>Source</b></td><td>" + sources1.toString() + "</td></div>"
                + "			<tr style='"+styleTR+"'><td style='width:250px'><b>Target</b></td><td>" + targets.toString() + "</td></tr>"
                + "			<tr style='"+styleTR+"'><td style='width:250px'><b>Codes</b></td><td>" + targetAbb + "</td></tr>"
                + "</table>";

        return emailMsgTxt;

    }

    public String getReqAnalysisEmailPriority(Quote1 q) {
        QuotePriority qp = null;
        String quotePriority = "";
        try {
            qp = QuoteService.getInstance().getSingleQuotePriority(q.getQuote1Id());
            if (qp != null) {
                if (qp.getUrgency() != null) {
                    if (qp.getUrgency() == 0) {
                        quotePriority = "Urgency: Normal (24 hrs) <br>";
                    }
                    if (qp.getUrgency() == 1) {
                        quotePriority = "Urgency: Somewhat Urgent <br>";
                    }
                    if (qp.getUrgency() == 2) {
                        quotePriority = "Urgency: Very urgent<br>";
                    }

                }
            }
        } catch (Exception e) {
        }

        String emailMsgTxt = "<tr style='"+styleTR+"'><td style='width:250px'><font color='blue'>Analysis Due</td><td style='color:darkred;'>" + quotePriority + "</font></td></tr>";
        return emailMsgTxt;

    }

    public String getReqAnalysisEmailFooter(User u) {

        String emailMsgTxt = "";
        emailMsgTxt += "<div style=\"padding-left: 40px;\"> Thanks,</div>";
        emailMsgTxt += "<br><div style=\"padding-left: 40px;\"><b>" + u.getFirstName() + "</b></div><br><br></div>";
        return emailMsgTxt;

    }

    public String getReqAnalysisEmailProduct(Project p) {
        String emailMsgTxt = "<tr style='"+styleTR+"'><td style='width:250px'><b>Product</b></td><td>" + p.getProduct() + "</td></tr>"
                + "	<tr style='"+styleTR+"'><td style='width:250px'><b>Product Description</b></td><td>" + p.getProductDescription() + "</td></tr>";

        return emailMsgTxt;

    }

    public boolean ifReqAnalysisEmailDtpAddress(int projectId) {
        INBasics inbasic = InteqaService.getInstance().getINBasics(projectId);
        boolean dtpAddress = false;
        if (inbasic.isDtpReq2() == true) {
            dtpAddress = true;
        }
        return dtpAddress;
    }
    
    public String getRequirements(Project p, Quote1 q) {
        String emailMsgTxt ="";
       List<Integer> poList = CommService.getInstance().getReqPoList(p.getProjectId());
        List<Requirement> generalReqL = CommService.getInstance().getRequirement("G", "L", 0, 0);
        List<Requirement> generalReqE = CommService.getInstance().getRequirement("G", "E", 0, 0);
        List<Requirement> generalReqD = CommService.getInstance().getRequirement("G", "D", 0, 0);
        
        List<Requirement> clientReqL = CommService.getInstance().getRequirement("C", "L", p.getCompany().getClientId(), 0);
        List<Requirement> clientReqE = CommService.getInstance().getRequirement("C", "E", p.getCompany().getClientId(), 0);
        List<Requirement> clientReqD = CommService.getInstance().getRequirement("C", "D", p.getCompany().getClientId(), 0);
        
        List<Requirement> projectReqL = CommService.getInstance().getRequirement("P", "L", 0, p.getProjectId());
        List<Requirement> projectReqE = CommService.getInstance().getRequirement("P", "E", 0, p.getProjectId());
        List<Requirement> projectReqD = CommService.getInstance().getRequirement("P", "D", 0, p.getProjectId());
        projectReqL.addAll(CommService.getInstance().getRequirement("P", "L", p.getCompany().getClientId(), p.getProjectId()));
        projectReqE.addAll(CommService.getInstance().getRequirement("P", "E", p.getCompany().getClientId(), p.getProjectId()));
        projectReqD.addAll(CommService.getInstance().getRequirement("P", "D", p.getCompany().getClientId(), p.getProjectId()));
        if (generalReqL.size() > 0) {
            emailMsgTxt += "<table align='center' class='reference' width='100%' >";
            emailMsgTxt += "<tr style='"+styleTR+"'><th style='"+referenceTh+"'>General Requirements - Linguistic</th></tr>";
            for (Requirement req : generalReqL) {
                if (poList.contains(req.getId()) || poList.isEmpty()) {
                    emailMsgTxt += "<tr style='"+styleTR+"'>";
                    emailMsgTxt += "<td style='"+styleTD+notes+"'>" + Jsoup.parse(req.getRequirement()).text().replaceAll("&nbsp;", "") + "</td>";
                    emailMsgTxt += "</tr>";
                }
            }
            emailMsgTxt += "</table><br/>";
        }
        if (generalReqD.size() > 0) {
            emailMsgTxt += "<table align='center' class='reference' width='90%' >";
            emailMsgTxt += "<tr style='"+styleTR+"'><th style='"+referenceTh+"'>General Requirements - DTP</th></tr>";
            for (Requirement req : generalReqD) {
                if (poList.contains(req.getId()) || poList.isEmpty()) {
                    emailMsgTxt += "<tr style='"+styleTR+"'>";
                    emailMsgTxt += "<td style='"+styleTD+notes+"'>" + Jsoup.parse(req.getRequirement()).text().replaceAll("&nbsp;", "") + "</td>";
                    emailMsgTxt += "</tr>";
                }
            }
            emailMsgTxt += "</table><br/>";
        }
        if (generalReqE.size() > 0) {
            emailMsgTxt += "<table align='center' class='reference' width='90%' >";
            emailMsgTxt += "<tr style='"+styleTR+"'><th style='"+referenceTh+"'>General Requirements - Engineering</th></tr>";
            for (Requirement req : generalReqE) {
                if (poList.contains(req.getId()) || poList.isEmpty()) {
                    emailMsgTxt += "<tr style='"+styleTR+"'>";
                    emailMsgTxt += "<td style='"+styleTD+notes+"'>" + Jsoup.parse(req.getRequirement()).text().replaceAll("&nbsp;", "") + "</td>";
                    emailMsgTxt += "</tr>";
                }
            }
            emailMsgTxt += "</table><br/>";
        }
        if (clientReqL.size() > 0) {
            emailMsgTxt += "<table align='center' class='reference' width='90%' >";
            emailMsgTxt += "<tr style='"+styleTR+"'><th style='"+referenceTh+"'>" + p.getCompany().getCompany_name() + " Requirements - Linguistic</th></tr>";
            for (Requirement req : clientReqL) {
                if (poList.contains(req.getId()) || poList.isEmpty()) {
                    emailMsgTxt += "<tr style='"+styleTR+"'>";
                    emailMsgTxt += "<td style='"+styleTD+notes+"'>" + Jsoup.parse(req.getRequirement()).text().replaceAll("&nbsp;", "") + "</td>";
                    emailMsgTxt += "</tr>";
                }
            }
            emailMsgTxt += "</table><br/>";
        }
        if (clientReqD.size() > 0) {
            emailMsgTxt += "<table align='center' class='reference' width='90%' >";
            emailMsgTxt += "<tr style='"+styleTR+"'><th style='"+referenceTh+"'>" + p.getCompany().getCompany_name() + " Requirements - DTP</th></tr>";
            for (Requirement req : clientReqD) {
                if (poList.contains(req.getId()) || poList.isEmpty()) {
                    emailMsgTxt += "<tr style='"+styleTR+"'>";
                    emailMsgTxt += "<td style='"+styleTD+notes+"'>" + Jsoup.parse(req.getRequirement()).text().replaceAll("&nbsp;", "") + "</td>";
                    emailMsgTxt += "</tr>";
                }
            }
            emailMsgTxt += "</table><br/>";
        }
        if (clientReqE.size() > 0) {
            emailMsgTxt += "<table align='center' class='reference' width='90%' >";
            emailMsgTxt += "<tr style='"+styleTR+"'><th style='"+referenceTh+"'>" + p.getCompany().getCompany_name() + " Requirements - Engineering</th></tr>";
            for (Requirement req : clientReqE) {
                if (poList.contains(req.getId()) || poList.isEmpty()) {
                    emailMsgTxt += "<tr style='"+styleTR+"'>";
                    emailMsgTxt += "<td style='"+styleTD+notes+"'>" + Jsoup.parse(req.getRequirement()).text().replaceAll("&nbsp;", "") + "</td>";
                    emailMsgTxt += "</tr>";
                }
            }
            emailMsgTxt += "</table><br/>";
        }
         String pNum = "";
            if(p.getNumber().equals("000000")){
                pNum = q.getNumber();
            }else{
                pNum = p.getNumber() + p.getCompany().getCompany_code();
            }
        
            if (projectReqL.size() > 0) {
           
            emailMsgTxt += "<table align='center' class='reference' width='90%' >";
            emailMsgTxt += "<tr style='"+styleTR+"'><th style='"+referenceTh+"'>Specific Requirements for " + pNum + " - Linguistic</th></tr>";
            for (Requirement req : projectReqL) {
                if (poList.contains(req.getId()) || poList.isEmpty()) {
                    emailMsgTxt += "<tr style='"+styleTR+"'>";
                    emailMsgTxt += "<td style='"+styleTD+notes+"'>" + Jsoup.parse(req.getRequirement()).text().replaceAll("&nbsp;", "") + "</td>";
                    emailMsgTxt += "</tr>";
                }
            }
            emailMsgTxt += "</table><br/>";
        }
            if (projectReqD.size() > 0) {
           
            emailMsgTxt += "<table align='center' class='reference' width='90%' >";
            emailMsgTxt += "<tr style='"+styleTR+"'><th style='"+referenceTh+"'>Specific Requirements for " + pNum + " - DTP</th></tr>";
            for (Requirement req : projectReqD) {
                if (poList.contains(req.getId()) || poList.isEmpty()) {
                    emailMsgTxt += "<tr style='"+styleTR+"'>";
                    emailMsgTxt += "<td style='"+styleTD+notes+"'>" + Jsoup.parse(req.getRequirement()).text().replaceAll("&nbsp;", "") + "</td>";
                    emailMsgTxt += "</tr>";
                }
            }
            emailMsgTxt += "</table><br/>";
        }
            if (projectReqE.size() > 0) {
           
            emailMsgTxt += "<table align='center' class='reference' width='90%' >";
            emailMsgTxt += "<tr style='"+styleTR+"'><th style='"+referenceTh+"'>Specific Requirements for " + pNum + " - Engineering</th></tr>";
            for (Requirement req : projectReqE) {
                if (poList.contains(req.getId()) || poList.isEmpty()) {
                    emailMsgTxt += "<tr style='"+styleTR+"'>";
                    emailMsgTxt += "<td style='"+styleTD+"'>" + Jsoup.parse(req.getRequirement()).text().replaceAll("&nbsp;", "") + "</td>";
                    emailMsgTxt += "</tr>";
                }
            }
            emailMsgTxt += "</table><br/>";
        }
 return emailMsgTxt;

    }
    

}
