package app.resource;

import app.project.LinTask;
import app.project.ProjectService;
import app.standardCode.*;
import java.util.Iterator;
import java.util.*;
import java.util.ListIterator;
import javax.servlet.http.HttpSession;
import app.client.*;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author niteshwar
 */
public class ResourceSearchOutput {
    
   private static ResourceSearchOutput instance = null; 
  //return the instance of ResourceSearchOutput
  public static synchronized ResourceSearchOutput getInstance() {
    /*
     * Creates the Singleton instance, if needed.
     *
     */
    if (instance == null) {
      instance = new ResourceSearchOutput();
    }
    return instance;
  }
  
  public String printResource(Resource r,String color, String source, String target, boolean TRate, boolean ERate, boolean TERate, boolean PRate, boolean RateOldDb, String[] ScoresLin, List rscs, boolean ScoreOldDb, boolean DtpRate, boolean AveProScore, int j, HttpSession session, String clientSpecialty) {

        //which task to bind to
        String linId = (String) session.getAttribute("projectViewTeamBindLinId");
        String engId = (String) session.getAttribute("projectViewTeamBindEngId");
        String dtpId = (String) session.getAttribute("projectViewTeamBindDtpId");
        String othId = (String) session.getAttribute("projectViewTeamBindOthId");
         HashMap<String, String> totHashMap = new HashMap<>();
        totHashMap.put("Medical", "Medical");
        totHashMap.put("Technical", "Technical");
        totHashMap.put("Software", "Software");
        totHashMap.put("General", "General");
        totHashMap.put("Marketing", "Marketing");
        totHashMap.put("Financial", "Legal/Financial");
        totHashMap.put("Legal", "Legal/Financial");
        totHashMap.put("Regulatory", "Technical");
//        totHashMap.put("", "Medical");
//        String projectid = (String) session.getAttribute("projectid");
//        //System.out.println("projectidprojectidprojectid" + projectid);
        RateScoreLanguage rsl = null;
        ListIterator rscsIter = rscs.listIterator();
        Double isaScore = 0.00;
//        String isaScore = "0.00";
        rscsIter = rscs.listIterator();
        int columnCount = 0;
        int columnUsed = 0;
        for(int i = 0; i < ScoresLin.length; i++) {
          RateScoreCategory rsc = (RateScoreCategory) rscsIter.next();
          if (ScoresLin[i].length() >= 1) {
            columnCount++;
            for (Iterator outer = r.getLanguagePairs().iterator(); outer.hasNext();) {
              LanguagePair lp = (LanguagePair) outer.next();
              for (Iterator inner = lp.getRateScoreLanguages().iterator(); inner.hasNext();) {
                rsl = (RateScoreLanguage) inner.next();
//                if(rsl.getSpecialty().equalsIgnoreCase("Regulatory")){
//                    System.err.println("ddd");
//                }
//                 if(rsc.getCategory().equalsIgnoreCase("Regulatory")){
//                    System.err.println("ddd");
//                }

//            
//System.out.println(rsl.getSpecialty() + "==========================" + rsc.getCategory() + "=====================" + rsl.getScore());
try{                
if (rsl.getSource().equals(source) && rsl.getTarget().equals(target) && totHashMap.get(rsl.getSpecialty()).equals(rsc.getCategory())) {
                  isaScore = StandardCode.getInstance().noNull(rsl.getScore());
                  columnUsed++;
                  //System.out.println("==========================" + columnUsed);
                }
}catch(Exception e){
    try {System.out.println("1"+rsl.getSource());} catch (Exception e1) {}
    try {System.out.println("2"+rsl.getTarget());} catch (Exception e1) {}
    try {System.out.println("3"+totHashMap);} catch (Exception e1) {}
    try {System.out.println("4"+rsc.getCategory());} catch (Exception e1) {}
    try {System.out.println("5"+source);} catch (Exception e1) {}
    try {System.out.println("6"+target);} catch (Exception e1) {}
    try {System.out.println("7"+rsl.getSpecialty());} catch (Exception e1) {}
    
}
              }
            }
          }
        }
        String vendorname = "";
        if ((r.getFirstName() != null && r.getFirstName().length() >= 1) && (r.getLastName() != null && r.getLastName().length() >= 1)) {
            vendorname = StandardCode.getInstance().noNull(r.getFirstName())+" "+StandardCode.getInstance().noNull(r.getLastName());
          } else {
            vendorname = StandardCode.getInstance().noNull(r.getCompanyName());
          }

        
        StringBuffer sb = new StringBuffer("");
        String functiontext = "";
        String alertText = "This vendor does not have an approved score for this text type. "
                + "\n Vendor: "+vendorname
                + "\n Source: "+source
                + "\n Target: "+target
                + "\n"
                + "\nThis vendor must:\n" +
"\n" +
"(1) be tested and approved prior to being used for this project, or \n" +
"(2) s/he may be used if you have a valid justification (see vendor selection procedure). \n" +
"\n" +
"The justification must be noted in the Notes tab of the project. \n" +
"After the project, the project manager must obtain evaluation and scoring of the vendor and record the results in ExcelNet.";
        

         if("".equals(color)){
                                sb.append("<tr class=tableRow>");
                            }else{
                                    sb.append("<tr bgcolor="+color+">");
                            }

        if (linId != null && r != null) {
          String colorStyle = "";
          if(isaScore<21.0){
          functiontext = "<td onclick='alert('"+alertText+"');'><a " + colorStyle + " onclick=\"return confirm_click();\" href=../projectViewTeamBindFromSearch.do?linId=" + linId + "&resourceId=" + String.valueOf(r.getResourceId()) + ">";
          }else{
          functiontext = "<td><a " + colorStyle + " href=../projectViewTeamBindFromSearch.do?linId=" + linId + "&resourceId=" + String.valueOf(r.getResourceId()) + ">";

          }
          if ((r.getFirstName() != null && r.getFirstName().length() >= 1) && (r.getLastName() != null && r.getLastName().length() >= 1)) {

            sb.append(functiontext).append(StandardCode.getInstance().noNull(r.getFirstName())).append(" ").append(StandardCode.getInstance().noNull(r.getLastName())).append("</a></td>");
          } else {
            sb.append(functiontext).append(StandardCode.getInstance().noNull(r.getCompanyName())).append("</a></td>");
          }

        } else if (engId != null) {
          if ((r.getFirstName() != null && r.getFirstName().length() >= 1) && (r.getLastName() != null && r.getLastName().length() >= 1)) {
            sb.append("<td><a href=../projectViewTeamBindFromSearch.do?engId=").append(engId).append("&resourceId=").append(String.valueOf(r.getResourceId())).append(">").append(StandardCode.getInstance().noNull(r.getFirstName())).append(" ").append(StandardCode.getInstance().noNull(r.getLastName())).append("</a></td>");
          } else {
            sb.append("<td><a href=../projectViewTeamBindFromSearch.do?engId=").append(engId).append("&resourceId=").append(String.valueOf(r.getResourceId())).append(">").append(StandardCode.getInstance().noNull(r.getCompanyName())).append("</a></td>");
          }
        } else if (dtpId != null && r != null) {
          try {
            if ((r.getFirstName().length() >= 1 && r.getFirstName() != null) && (r.getLastName().length() >= 1 && r.getLastName() != null)) {
              sb.append("<td><a href=../projectViewTeamBindFromSearch.do?dtpId=").append(dtpId).append("&resourceId=").append(String.valueOf(r.getResourceId())).append(">").append(StandardCode.getInstance().noNull(r.getFirstName())).append(" ").append(StandardCode.getInstance().noNull(r.getLastName())).append("</a></td>");
            } else {
              sb.append("<td><a href=../projectViewTeamBindFromSearch.do?dtpId=").append(dtpId).append("&resourceId=").append(String.valueOf(r.getResourceId())).append(">").append(StandardCode.getInstance().noNull(r.getCompanyName())).append("</a></td>");
            }
          } catch (NullPointerException e) {
            sb.append("<td><a href=../projectViewTeamBindFromSearch.do?dtpId=").append(dtpId).append("&resourceId=").append(String.valueOf(r.getResourceId())).append(">").append(StandardCode.getInstance().noNull(r.getCompanyName())).append("</a></td>");
          }

        } else if (othId != null && r != null) {
          if ((r.getFirstName().length() >= 1 && r.getFirstName() != null) && (r.getLastName().length() >= 1 && r.getLastName() != null)) {
            sb.append("<td><a href=../projectViewTeamBindFromSearch.do?othId=").append(othId).append("&resourceId=").append(String.valueOf(r.getResourceId())).append(">").append(StandardCode.getInstance().noNull(r.getFirstName())).append(" ").append(StandardCode.getInstance().noNull(r.getLastName())).append("</a></td>");
          } else {
            sb.append("<td><a href=../projectViewTeamBindFromSearch.do?othId=").append(othId).append("&resourceId=").append(String.valueOf(r.getResourceId())).append(">").append(StandardCode.getInstance().noNull(r.getCompanyName())).append("</a></td>");
          }
        }

        //resource table data
        sb.append("<td align='center'><a href=mailto:").append(StandardCode.getInstance().noNull(r.getEmail_address1())).append(">Send</a></td>");

        String unavailString = "";
        for (Iterator iter = r.getUnavails().iterator(); iter.hasNext();) {
          if (!"".equals(unavailString)) {
            unavailString += "<br>";
          }
          Unavail un = (Unavail) iter.next();
          String endDate = " TBD";
          if (un.getEndDate() != null) {
            endDate = un.getEndDate().toString();
            if (un.getEndDate().after(new Date())) {
              unavailString += un.getStartDate() + " to " + endDate;
            }
          } else {
            unavailString += un.getStartDate() + " to " + endDate;
          }
        }
        sb.append("<td align='center'>" + unavailString + "</td>");
        // if(clientSpecialty!=null){

        sb.append("<td align='center'>" + StandardCode.getInstance().noNull(r.getCurrency()) + "</td>");
        // }

        if ( StandardCode.getInstance().noNull(r.getCurrency()).equalsIgnoreCase("Euro")) {
          sb.append("<td align='center'>");
          for (Iterator outer = r.getLanguagePairs().iterator(); outer.hasNext();) {
            LanguagePair lp = (LanguagePair) outer.next();
            for (Iterator inner = lp.getRateScoreLanguages().iterator(); inner.hasNext();) {
              rsl = (RateScoreLanguage) inner.next();
              if (rsl.getSource().equals(source) && rsl.getTarget().equals(target)) {
                if ("MEDICAL".equals(clientSpecialty) && "Medical".equals(rsl.getSpecialty())) {
                  sb.append("&euro;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getMin())) + "(&#36;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getMin() * StandardCode.getInstance().getEuro())) + ")<br>");
                } else if ("BUSINESS".equals(clientSpecialty) && "Legal/Financial".equals(rsl.getSpecialty())) {
                  sb.append("&euro;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getMin())) + "(&#36;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getMin() * StandardCode.getInstance().getEuro())) + ")<br>");
                } else if ("COMPUTER".equals(clientSpecialty) && "Software".equals(rsl.getSpecialty())) {
                  sb.append("&euro;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getMin())) + "(&#36;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getMin() * StandardCode.getInstance().getEuro())) + ")<br>");
                } else if ("TECH".equals(clientSpecialty) && "Technical".equals(rsl.getSpecialty())) {
                  sb.append("&euro;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getMin())) + "(&#36;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getMin() * StandardCode.getInstance().getEuro())) + ")<br>");
                }
              }
            }
          }
          sb.append("</td>");


          if (TRate) {
            sb.append("<td align='center'>");
            for (Iterator outer = r.getLanguagePairs().iterator(); outer.hasNext();) {
              LanguagePair lp = (LanguagePair) outer.next();
              for (Iterator inner = lp.getRateScoreLanguages().iterator(); inner.hasNext();) {
                rsl = (RateScoreLanguage) inner.next();
                if (rsl.getSource().equals(source) && rsl.getTarget().equals(target)) {
                  if ("MEDICAL".equals(clientSpecialty) && "Medical".equals(rsl.getSpecialty())) {
                    sb.append("&euro;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getT())) + "(&#36;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getT() * StandardCode.getInstance().getEuro())) + ")<br>");
                  } else if ("BUSINESS".equals(clientSpecialty) && "Legal/Financial".equals(rsl.getSpecialty())) {
                    sb.append("&euro;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getT())) + "(&#36;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getT() * StandardCode.getInstance().getEuro())) + ")<br>");
                  } else if ("COMPUTER".equals(clientSpecialty) && "Software".equals(rsl.getSpecialty())) {
                    sb.append("&euro;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getT())) + "(&#36;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getT() * StandardCode.getInstance().getEuro())) + ")<br>");
                  } else if ("TECH".equals(clientSpecialty) && "Technical".equals(rsl.getSpecialty())) {
                    sb.append("&euro;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getT())) + "(&#36;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getT() * StandardCode.getInstance().getEuro())) + ")<br>");
                  }


                }
              }
            }
            sb.append("</td>");
          }
          if (ERate) {
            sb.append("<td align='center'>");
            for (Iterator outer = r.getLanguagePairs().iterator(); outer.hasNext();) {
              LanguagePair lp = (LanguagePair) outer.next();
              for (Iterator inner = lp.getRateScoreLanguages().iterator(); inner.hasNext();) {
                rsl = (RateScoreLanguage) inner.next();
                if (rsl.getSource().equals(source) && rsl.getTarget().equals(target)) {
                  if ("MEDICAL".equals(clientSpecialty) && "Medical".equals(rsl.getSpecialty())) {
                    sb.append("&euro;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getE())) + "(&#36;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getE() * StandardCode.getInstance().getEuro())) + ")<br>");
                  } else if ("BUSINESS".equals(clientSpecialty) && "Legal/Financial".equals(rsl.getSpecialty())) {
                    sb.append("&euro;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getE())) + "(&#36;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getE() * StandardCode.getInstance().getEuro())) + ")<br>");
                  } else if ("COMPUTER".equals(clientSpecialty) && "Software".equals(rsl.getSpecialty())) {
                    sb.append("&euro;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getE())) + "(&#36;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getE() * StandardCode.getInstance().getEuro())) + ")<br>");
                  } else if ("TECH".equals(clientSpecialty) && "Technical".equals(rsl.getSpecialty())) {
                    sb.append("&euro;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getE())) + "(&#36;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getE() * StandardCode.getInstance().getEuro())) + ")<br>");
                  }
                }
              }
            }
            sb.append("</td>");
          }
          if (TERate) {
            sb.append("<td align='center'>");
            for (Iterator outer = r.getLanguagePairs().iterator(); outer.hasNext();) {
              LanguagePair lp = (LanguagePair) outer.next();
              for (Iterator inner = lp.getRateScoreLanguages().iterator(); inner.hasNext();) {
                rsl = (RateScoreLanguage) inner.next();
                if (rsl.getSource().equals(source) && rsl.getTarget().equals(target)) {
                  if ("MEDICAL".equals(clientSpecialty) && "Medical".equals(rsl.getSpecialty())) {
                    sb.append("&euro;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getTe())) + "(&#36;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getTe() * StandardCode.getInstance().getEuro())) + ")<br>");
                  } else if ("BUSINESS".equals(clientSpecialty) && "Legal/Financial".equals(rsl.getSpecialty())) {
                    sb.append("&euro;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getTe())) + "(&#36;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getTe() * StandardCode.getInstance().getEuro())) + ")<br>");
                  } else if ("COMPUTER".equals(clientSpecialty) && "Software".equals(rsl.getSpecialty())) {
                    sb.append("&euro;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getTe())) + "(&#36;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getTe() * StandardCode.getInstance().getEuro())) + ")<br>");
                  } else if ("TECH".equals(clientSpecialty) && "Technical".equals(rsl.getSpecialty())) {
                    sb.append("&euro;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getTe())) + "(&#36;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getTe() * StandardCode.getInstance().getEuro())) + ")<br>");
                  }


                }
              }
            }
            sb.append("</td>");
          }
          if (PRate) {
            sb.append("<td align='center'>");
            for (Iterator outer = r.getLanguagePairs().iterator(); outer.hasNext();) {
              LanguagePair lp = (LanguagePair) outer.next();
              for (Iterator inner = lp.getRateScoreLanguages().iterator(); inner.hasNext();) {
                rsl = (RateScoreLanguage) inner.next();
                if (rsl.getSource().equals(source) && rsl.getTarget().equals(target)) {
                  sb.append(StandardCode.getInstance().noNull(rsl.getSpecialty()) + "= &euro;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getP())) + "(&#36;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getP() * StandardCode.getInstance().getEuro())) + ")<br>");
                }
              }
            }
            sb.append("</td>");
          }
        } else {
          sb.append("<td align='center'>");
          for (Iterator outer = r.getLanguagePairs().iterator(); outer.hasNext();) {
            LanguagePair lp = (LanguagePair) outer.next();
            for (Iterator inner = lp.getRateScoreLanguages().iterator(); inner.hasNext();) {
              rsl = (RateScoreLanguage) inner.next();
              if (rsl.getSource().equals(source) && rsl.getTarget().equals(target)) {
                if ("MEDICAL".equals(clientSpecialty) && "Medical".equals(rsl.getSpecialty())) {
                  sb.append("&#36;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getMin())) + "(&euro;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getMin() / StandardCode.getInstance().getEuro())) + ")<br>");
                } else if ("BUSINESS".equals(clientSpecialty) && "Legal/Financial".equals(rsl.getSpecialty())) {
                  sb.append("&#36;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getMin())) + "(&euro;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getMin() / StandardCode.getInstance().getEuro())) + ")<br>");
                } else if ("COMPUTER".equals(clientSpecialty) && "Software".equals(rsl.getSpecialty())) {
                  sb.append("&#36;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getMin())) + "(&euro;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getMin() / StandardCode.getInstance().getEuro())) + ")<br>");
                } else if ("TECH".equals(clientSpecialty) && "Technical".equals(rsl.getSpecialty())) {
                  sb.append("&#36;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getMin())) + "(&euro;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getMin() / StandardCode.getInstance().getEuro())) + ")<br>");
                }
              }
            }
          }
          sb.append("</td>");


          if (TRate) {
            sb.append("<td align='center'>");
            for (Iterator outer = r.getLanguagePairs().iterator(); outer.hasNext();) {
              LanguagePair lp = (LanguagePair) outer.next();
              for (Iterator inner = lp.getRateScoreLanguages().iterator(); inner.hasNext();) {
                rsl = (RateScoreLanguage) inner.next();
                if (rsl.getSource().equals(source) && rsl.getTarget().equals(target)) {
                  if ("MEDICAL".equals(clientSpecialty) && "Medical".equals(rsl.getSpecialty())) {
                    sb.append("&#36;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getT())) + "(&euro;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getT() / StandardCode.getInstance().getEuro())) + ")<br>");
                  } else if ("BUSINESS".equals(clientSpecialty) && "Legal/Financial".equals(rsl.getSpecialty())) {
                    sb.append("&#36;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getT())) + "(&euro;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getT() / StandardCode.getInstance().getEuro())) + ")<br>");
                  } else if ("COMPUTER".equals(clientSpecialty) && "Software".equals(rsl.getSpecialty())) {
                    sb.append("&#36;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getT())) + "(&euro;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getT() / StandardCode.getInstance().getEuro())) + ")<br>");
                  } else if ("TECH".equals(clientSpecialty) && "Technical".equals(rsl.getSpecialty())) {
                    sb.append("&#36;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getT())) + "(&euro;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getT() / StandardCode.getInstance().getEuro())) + ")<br>");
                  }


                }
              }
            }
            sb.append("</td>");
          }
          if (ERate) {
            sb.append("<td align='center'>");
            for (Iterator outer = r.getLanguagePairs().iterator(); outer.hasNext();) {
              LanguagePair lp = (LanguagePair) outer.next();
              for (Iterator inner = lp.getRateScoreLanguages().iterator(); inner.hasNext();) {
                rsl = (RateScoreLanguage) inner.next();
                if (rsl.getSource().equals(source) && rsl.getTarget().equals(target)) {
                  if ("MEDICAL".equals(clientSpecialty) && "Medical".equals(rsl.getSpecialty())) {
                    sb.append("&#36;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getE())) + "(&euro;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getE() / StandardCode.getInstance().getEuro())) + ")<br>");
                  } else if ("BUSINESS".equals(clientSpecialty) && "Legal/Financial".equals(rsl.getSpecialty())) {
                    sb.append("&#36;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getE())) + "(&euro;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getE() / StandardCode.getInstance().getEuro())) + ")<br>");
                  } else if ("COMPUTER".equals(clientSpecialty) && "Software".equals(rsl.getSpecialty())) {
                    sb.append("&#36;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getE())) + "(&euro;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getE() / StandardCode.getInstance().getEuro())) + ")<br>");
                  } else if ("TECH".equals(clientSpecialty) && "Technical".equals(rsl.getSpecialty())) {
                    sb.append("&#36;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getE())) + "(&euro;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getE() / StandardCode.getInstance().getEuro())) + ")<br>");
                  }
                }
              }
            }
            sb.append("</td>");
          }
          if (TERate) {
            sb.append("<td align='center'>");
            for (Iterator outer = r.getLanguagePairs().iterator(); outer.hasNext();) {
              LanguagePair lp = (LanguagePair) outer.next();
              for (Iterator inner = lp.getRateScoreLanguages().iterator(); inner.hasNext();) {
                rsl = (RateScoreLanguage) inner.next();
                if (rsl.getSource().equals(source) && rsl.getTarget().equals(target)) {
                  if ("MEDICAL".equals(clientSpecialty) && "Medical".equals(rsl.getSpecialty())) {
                    sb.append("&#36;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getTe())) + "(&euro;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getTe() / StandardCode.getInstance().getEuro())) + ")<br>");
                  } else if ("BUSINESS".equals(clientSpecialty) && "Legal/Financial".equals(rsl.getSpecialty())) {
                    sb.append("&#36;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getTe())) + "(&euro;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getTe() / StandardCode.getInstance().getEuro())) + ")<br>");
                  } else if ("COMPUTER".equals(clientSpecialty) && "Software".equals(rsl.getSpecialty())) {
                    sb.append("&#36;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getTe())) + "(&euro;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getTe() / StandardCode.getInstance().getEuro())) + ")<br>");
                  } else if ("TECH".equals(clientSpecialty) && "Technical".equals(rsl.getSpecialty())) {
                    sb.append("&#36;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getTe())) + "(&euro;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getTe() / StandardCode.getInstance().getEuro())) + ")<br>");
                  }


                }
              }
            }
            sb.append("</td>");
          }
          if (PRate) {
            sb.append("<td align='center'>");
            for (Iterator outer = r.getLanguagePairs().iterator(); outer.hasNext();) {
              LanguagePair lp = (LanguagePair) outer.next();
              for (Iterator inner = lp.getRateScoreLanguages().iterator(); inner.hasNext();) {
                rsl = (RateScoreLanguage) inner.next();
                if (rsl.getSource().equals(source) && rsl.getTarget().equals(target)) {
                  sb.append(StandardCode.getInstance().noNull(rsl.getSpecialty()) + "= &#36;" + StandardCode.getInstance().noNull(String.valueOf(rsl.getP())) + "(&euro;" + StandardCode.getInstance().noNull(StandardCode.getInstance().formatDouble4(rsl.getP() / StandardCode.getInstance().getEuro())) + ")<br>");
                }
              }
            }
            sb.append("</td>");
          }

        }

        if (RateOldDb) {
          sb.append("<td align='center'>" + "T=" + StandardCode.getInstance().noNull(String.valueOf(r.getT())) + "<br>" + "E=" + StandardCode.getInstance().noNull(String.valueOf(r.getE())) + "<br>" + "TE=" + StandardCode.getInstance().noNull(String.valueOf(r.getTe())) + "<br>" + "P=" + StandardCode.getInstance().noNull(String.valueOf(r.getP())) + "</td>");
        }

         
        sb.append("<td align='center'>").append(isaScore).append("</td>");
        for (int i = 0; i < columnCount - columnUsed; i++) {
          sb.append("<td></td>");

        }
        if (ScoreOldDb) {
          sb.append("<td align='center'>Medical=").append(StandardCode.getInstance().noNull(String.valueOf(r.getMedicalScore()))).append("<br>Technical=").append(StandardCode.getInstance().noNull(String.valueOf(r.getTechnicalScore()))).append("<br>Software=").append(StandardCode.getInstance().noNull(String.valueOf(r.getSoftwareScore()))).append("<br>Legal/Fin=").append(StandardCode.getInstance().noNull(String.valueOf(r.getLegalFinancialScore()))).append("</td>");

        }
        if (DtpRate) {
          sb.append("<td align='center'>");
          for (Iterator outer = r.getRateScoreDtps().iterator(); outer.hasNext();) {
            RateScoreDtp rsd = (RateScoreDtp) outer.next();
            //if(rsd.getSource().equals(source) && rsd.getTarget().equals(target)) {
            sb.append(StandardCode.getInstance().noNull(rsd.getApplication())).append("=").append(StandardCode.getInstance().noNull(String.valueOf(rsd.getRate()))).append("<br>");

            //}
          }
          sb.append("</td>");
        }

        if (AveProScore) {

          String avgs =  StandardCode.getInstance().noNull(String.valueOf(r.getProjectScoreAverage()));
          if (r.getProjectScoreAverage() != null) {
            avgs = StandardCode.getInstance().formatDouble3(r.getProjectScoreAverage());

          }

          if (r.getProjectScoreAverage() != null && r.getProjectScoreAverage().doubleValue() < 0) {


            sb.append("<td align='center'><font color='red'>" + avgs + "</font></td>");
          } else {
            sb.append("<td align='center'>" + avgs + "</td>");

          }

        }


        sb.append("</tr>"); //end resource table data

        return sb.toString();
      }
}
