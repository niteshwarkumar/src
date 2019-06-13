//ClientViewResourceHistoryAction.java gets the current client from the
//db and places it in an attribute for display featuring resources
package app.extjs.helpers;

import app.client.Client;
import app.db.ConnectionFactory;
import app.extjs.global.LanguageAbs;
import app.newmodel.OSAModel;
import app.standardCode.StandardCode;
import java.util.*;
import app.project.*;
import app.resource.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

public final class TeamHelper {

    private TeamHelper() {
        //System.out.println("TeamHelper constuctor is calling***********************************");
    }
    private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    public static List getPMTeams(String pmName, int myExcelRange, Integer pmID) {



        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        //accumulate resource summary for display in ClientViewTeamDisplay object
        ArrayList results = new ArrayList();
        ArrayList projects = new ArrayList();

        try {

            //query =	session.createQuery("select client from app.client.Client client left join client.Projects project where (client.Project_mngr='"+pmName+"' or client.Sales_rep='"+pmName+"' or project.pm = '"+pmName+"')");
            //List clientList = query.list();
            long startProcessProjects = System.currentTimeMillis();

//            query = session.createQuery("select project from app.project.Project project left join fetch project.SourceDocs sds where project.pm = '" + pmName + "'  and project.startDate>DATE_SUB(current_date,INTERVAL  " + myExcelRange + " day) ");
            String qry = "select * from project project where project.pm_id = " + pmID + "  and project.startDate>DATE_SUB(current_date,INTERVAL  " + myExcelRange + " day) ";
//            List projects = query.list();
            
            PreparedStatement st = session.connection().prepareStatement(qry);
            ResultSet rs = st.executeQuery();
             Hashtable duplicateProjects = new Hashtable();
            while(rs.next()) {
                if (duplicateProjects.get(rs.getString("number")) != null || rs.getString("number").equals("000000")) {
                  
                } else {
                    duplicateProjects.put(rs.getString("number"),rs.getString("number"));
                    Project p = ProjectService.getInstance().getSingleProject(rs.getInt("Id_Project"));
//                Hibernate.initialize(p.getSourceDocs());
                projects.add(p);
                }
                
            }
            rs.close();
            st.close();
            // //System.out.println("projects.size():"+projects.size());
            // for(int i=0;i<clientList.size();i++){

            //     Client c = (Client)clientList.get(i);
            //     //System.out.println("Processing client: "+c.getCompany_name());
            //get Tasks for display
            List linTasksDisplay = new ArrayList();


            //iterate through tasks and accumulate by task type (lin, eng, dtp, oth)

            //Hibernate.initialize(c.getProjects());
            // Set projects = c.getProjects();
            HashMap fetchedCompanies = new HashMap();

            if (projects != null) {
                //for each project
                for (Iterator projectsIter = projects.iterator(); projectsIter.hasNext();) {
                    Project p = (Project) projectsIter.next();
                    //p = ProjectService.getInstance().getSingleProject(p.getProjectId());

                    Set sourceDocs = p.getSourceDocs();
                    if (sourceDocs != null) {
                        for (Iterator sourceDocsIter = sourceDocs.iterator(); sourceDocsIter.hasNext();) {
                            SourceDoc sd = (SourceDoc) sourceDocsIter.next();
                            //sd = ProjectService.getInstance().getSingleSourceDoc(sd.getSourceDocId());

                            Set targetDocs = sd.getTargetDocs();
                            if (targetDocs != null) {
                                for (Iterator targetDocsIter = targetDocs.iterator(); targetDocsIter.hasNext();) {
                                    TargetDoc td = (TargetDoc) targetDocsIter.next();
                                    Set linTasks = td.getLinTasks();
                                    if (linTasks != null) {
                                        for (Iterator linTasksIter = linTasks.iterator(); linTasksIter.hasNext();) {
                                            LinTask lt = (LinTask) linTasksIter.next();
                                            if (lt.getPersonName() != null) {
                                                linTasksDisplay.add(lt);
                                                fetchedCompanies.put(lt.getLinTaskId(), p.getCompany());
                                            }
                                        }
                                    }
                                    /*
                                    Set engTasks = td.getEngTasks();
                                    if(engTasks != null) {
                                    for(Iterator engTasksIter = engTasks.iterator(); engTasksIter.hasNext();) {
                                    EngTask et = (EngTask) engTasksIter.next();
                                    if(et.getPersonName() != null) {
                                    engTasksDisplay.add(et);
                                    }
                                    }
                                    }

                                    Set dtpTasks = td.getDtpTasks();
                                    if(dtpTasks != null) {
                                    for(Iterator dtpTasksIter = dtpTasks.iterator(); dtpTasksIter.hasNext();) {
                                    DtpTask dt = (DtpTask) dtpTasksIter.next();
                                    if(dt.getPersonName() != null) {
                                    dtpTasksDisplay.add(dt);
                                    }
                                    }
                                    }

                                    Set othTasks = td.getOthTasks();
                                    if(othTasks != null) {
                                    for(Iterator othTasksIter = othTasks.iterator(); othTasksIter.hasNext();) {
                                    OthTask ot = (OthTask) othTasksIter.next();
                                    if(ot.getPersonName() != null) {
                                    othTasksDisplay.add(ot);
                                    }
                                    }
                                    } */
                                }
                            }
                        }
                    }
                }
            }

            long endProcessProjects = System.currentTimeMillis();
            ////System.out.println("teamHelper took:"+((endProcessProjects-startProcessProjects)/1000.00));

            HashMap fetchedResources = new HashMap();
            HashMap myTotalCounts = new HashMap();
            HashMap myClientTotalCounts = new HashMap();
            HashMap myTeamDisplays = new HashMap();
            HashMap rateScoreLangs = new HashMap();

            for (ListIterator iter = linTasksDisplay.listIterator(); iter.hasNext();) {
                LinTask t = (LinTask) iter.next();



                /*   if((r = (Resource)fetchedResources.get(t.getPersonName()))==null){
                Query query2 =	session.createQuery("select resource from Resource as resource where resource.resourceId = "+t.getPersonName());
                List myList = query2.list();                 if(myList.size()>0){                     r = (Resource) query2.list().get(0);                     fetchedResources.put(t.getPersonName(), r);                  }else{                     fetchedResources.put(t.getPersonName(), new Resource());                  }
                fetchedResources.put(t.getPersonName(), r);                
                }*/
                Client c = (Client) fetchedCompanies.get(t.getLinTaskId());

                MyTeamsDisplay mtd = null;
                if ((mtd = (MyTeamsDisplay) myTeamDisplays.get(t.getSourceLanguage() + "_" + t.getTargetLanguage() + "_" + t.getPersonName())) == null) {

                    mtd = new MyTeamsDisplay();
                    mtd.resourceId = t.getPersonName();
                    mtd.sourceLang = t.getSourceLanguage();
                    mtd.targetLang = t.getTargetLanguage();
                    mtd.client = c.getCompany_name();
                    mtd.clientId = c.getClientId();
                    myTeamDisplays.put(t.getSourceLanguage() + "_" + t.getTargetLanguage() + "_" + t.getPersonName(), mtd);
                }

                MyTeamsClientTotals mtClient = null;
                if ((mtClient = (MyTeamsClientTotals) myClientTotalCounts.get(c.getCompany_name() + "_" + t.getPersonName())) == null) {
                    mtClient = new MyTeamsClientTotals();
                    mtClient.client = c.getCompany_name();
                    //mtClient.clientClean = c.getCompany_name();
                    mtClient.resourceId = t.getPersonName();
                    myClientTotalCounts.put(c.getCompany_name() + "_" + t.getPersonName(), mtClient);

                    String companyType = c.getIndustry().getDescription();

                    if (companyType.indexOf("Medical, Dental, Pharmaceutical") > -1) {

                        mtClient.client += " - Medical";
                        mtClient.clientType = "Medical";
                    } else if (companyType.indexOf("Business & Finance & Legal: Banking, Commerce, Management") > -1) {

                        mtClient.client += " - Finance/Legal";
                        mtClient.clientType = "Legal/Financial";
                    } else if (companyType.indexOf("Computer: Hardware & Software, Localization") > -1) {

                        mtClient.client += " - Software";
                        mtClient.clientType = "Software";
                    } else {

                        mtClient.client += " - Technical";
                        mtClient.clientType = "Technical";
                    }

                }

                MyTeamsTotals mtTotals = null;
                if ((mtTotals = (MyTeamsTotals) myTotalCounts.get(t.getPersonName())) == null) {
                    mtTotals = new MyTeamsTotals();
                    mtTotals.resourceId = t.getPersonName();
                    myTotalCounts.put(t.getPersonName(), mtTotals);
                }
                Resource r = null;
                if ((r = (Resource) fetchedResources.get(t.getPersonName())) == null) {
                    Query query2 = session.createQuery("select resource from Resource as resource where resource.resourceId = " + t.getPersonName());
                    List myList = query2.list();
                    if (myList.size() > 0) {
                        r = (Resource) query2.list().get(0);
                        fetchedResources.put(t.getPersonName(), r);
                    } else {
                        fetchedResources.put(t.getPersonName(), new Resource());
                    }
                    //fetchedResources.put(t.getPersonName(), r);

                    mtTotals.totalPaid = getAllProjectsTotalForPerson(session, t.getPersonName());
                    try{
                    //the list for display (load all ratescorelanguage entries)
                    //System.out.println("l=================================  "+r.getResourceId());
                    Set languagePairs = r.getLanguagePairs();
                    //System.out.println("languagePairs="+languagePairs+"resource:-   "+r.getResourceId());
                    for (Iterator iter2 = languagePairs.iterator(); iter2.hasNext();) {
                        LanguagePair lp = (LanguagePair) iter2.next();
                        for (Iterator rateScoreIter = lp.getRateScoreLanguages().iterator(); rateScoreIter.hasNext();) {
                            RateScoreLanguage rsl = (RateScoreLanguage) rateScoreIter.next();
                            rateScoreLangs.put(r.getResourceId() + "_" + rsl.getSource() + "_" + rsl.getTarget() + "_" + rsl.getSpecialty(), rsl);
                            ////System.out.println("rsl.getSpecialty()="+rsl.getSpecialty());
                        }

                    }

                    }catch(Exception e){}
                }

                if (t.getInternalDollarTotal() != null && !"".equals(t.getInternalDollarTotal())) {
                    mtClient.totalPaid += Double.valueOf(t.getInternalDollarTotal().replaceAll(",", "")).doubleValue();
                    //mtTotals.totalPaid += Double.valueOf(t.getInternalDollarTotal().replaceAll(",", "")).doubleValue();
                }
                mtClient.countProjects++;
                mtTotals.countProjects++;
                // //System.out.println("person="+t.getPersonName()+", score="+t.getScore());
                if (t.getScore() != null) {
                    mtTotals.totalScore += t.getScore().intValue();
                    //mtClient.totalScore += t.getScore().intValue();
                }

                if (t.getTaskName() != null) {
                    if (t.getTaskName().equalsIgnoreCase("Editing")) {
                        mtClient.countE++;
                        mtTotals.countE++;
                    } else if (t.getTaskName().equalsIgnoreCase("Proofreading / Linguistic QA")) {
                        mtClient.countP++;
                        mtTotals.countP++;
                    } else if (t.getTaskName().equalsIgnoreCase("Translation")) {
                        mtClient.countT++;
                        mtTotals.countT++;
                    }//Add the other two here once they are added
                }

                String companyType = c.getIndustry().getDescription();

                /*     if(companyType.indexOf("Medical, Dental, Pharmaceutical")>-1 ){
                if(r.getMedicalScore()!=null){
                mtClient.testScore = ""+r.getMedicalScore();
                }
                }else if(companyType.indexOf("Business & Finance & Legal: Banking, Commerce, Management")>-1){
                if(r.getLegalFinancialScore()!=null){
                mtClient.testScore = ""+r.getLegalFinancialScore();
                }
                }else if(companyType.indexOf("Computer: Hardware & Software, Localization")>-1 ){
                if(r.getSoftwareScore()!=null){
                mtClient.testScore = ""+r.getSoftwareScore();
                }
                }else {
                if(r.getTechnicalScore()!=null){
                mtClient.testScore = ""+r.getTechnicalScore();
                }

                }*/
            }


            for (Iterator iter = myTeamDisplays.values().iterator(); iter.hasNext();) {
                MyTeamsDisplay mtd = (MyTeamsDisplay) iter.next();
                JSONObject jo = new JSONObject();


                jo.put("sourceTarget", mtd.sourceLang + " to " + mtd.targetLang);

                Resource r = (Resource) fetchedResources.get(mtd.resourceId);


                if (!r.isAgency()) {
                    //jo.put("vendorName",r.getFirstName()+ " " + r.getLastName());
                    jo.put("vendorName", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:openVendorWindowReverse('" + HrHelper.jsSafe(r.getFirstName() + " " + r.getLastName()) + "','" + r.getResourceId() + "')\">" + r.getFirstName() + " " + r.getLastName() + "</a>");

                } else {
                    //jo.put("vendorName",r.getCompanyName());
                    jo.put("vendorName", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:openVendorWindowReverse('" + HrHelper.jsSafe(r.getCompanyName()) + "','" + r.getResourceId() + "')\">" + r.getCompanyName() + "</a>");

                }
                MyTeamsClientTotals mtClient = (MyTeamsClientTotals) myClientTotalCounts.get(mtd.client + "_" + mtd.resourceId);

                RateScoreLanguage rsl = (RateScoreLanguage) rateScoreLangs.get(r.getResourceId() + "_" + mtd.sourceLang + "_" + mtd.targetLang + "_" + mtClient.clientType);
                if (rsl != null) {
                    if (rsl.getMin() != null) {
                        jo.put("minimumFee", StandardCode.getInstance().formatMoney(rsl.getMin().doubleValue()));
                    } else {
                        jo.put("minimumFee", "0");
                    }
                    jo.put("testScore", rsl.getScore());
                    jo.put("currency", noNull(rsl.getCurrency()));
                    jo.put("T_Rate", rsl.getT());
                    jo.put("E_Rate", rsl.getE());
                    jo.put("P_Rate", rsl.getP());
                    jo.put("TE_Rate", rsl.getTe());
                    jo.put("ICR_Rate", 0);
                }

                MyTeamsTotals mtTotals = (MyTeamsTotals) myTotalCounts.get(mtd.resourceId);
                jo.put("T_Count_Total", mtTotals.countT);
                jo.put("E_Count_Total", mtTotals.countE);
                jo.put("P_Count_Total", mtTotals.countP);
                jo.put("TE_Count_Total", mtTotals.countTE);
                jo.put("ICR_Count_Total", mtTotals.countICR);

                //jo.put("clientName", mtClient.client);
                jo.put("clientName", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:openClientWindowReverse('" + HrHelper.jsSafe(mtClient.client) + "','" + mtd.clientId + "')\">" + mtClient.client + "</a>");
                //jo.put("clientClean",mtClient.clientClean);
                jo.put("T_Count_Client", mtClient.countT);
                jo.put("E_Count_Client", mtClient.countE);
                jo.put("P_Count_Client", mtClient.countP);
                jo.put("TE_Count_Client", mtClient.countTE);
                jo.put("ICR_Count_Client", mtClient.countICR);

                jo.put("countProjects", mtTotals.countProjects);
                jo.put("totalPaid", StandardCode.getInstance().formatMoney(mtTotals.totalPaid));
                //jo.put("averageScore",  StandardCode.getInstance().formatMoney( ((mtTotals.totalScore*1.0)/(mtTotals.countProjects*1.0))));
                String averagePerformanceScore = "0.00";
                if (r.getProjectScoreAverage() != null) {
                    averagePerformanceScore = StandardCode.getInstance().formatDouble(r.getProjectScoreAverage());
                }
                jo.put("averageScore", averagePerformanceScore);

                // jo.put("testScore", mtClient.testScore);
                jo.put("countProjectsClient", mtClient.countProjects);
                jo.put("totalPaidClient", StandardCode.getInstance().formatMoney(mtClient.totalPaid));
                //jo.put("averageScoreClient", (mtClient.totalScore/mtClient.countProjects));

                ////System.out.println(mtd.resourceId+":::"+mtd.sourceLang+" to " + mtd.targetLang);
                results.add(jo);
            }



        } catch (Exception e) {
            //System.out.println("Exception::" + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e) {
                    //System.out.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }
        return results;
    }

    public static List getPMDtpTeams(String pmName, int myExcelRange, Integer pmID) {



        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        //accumulate resource summary for display in ClientViewTeamDisplay object
        ArrayList results = new ArrayList();

        try {

            //query =	session.createQuery("select client from app.client.Client client left join client.Projects project where (client.Project_mngr='"+pmName+"' or client.Sales_rep='"+pmName+"' or project.pm = '"+pmName+"')");
            //List clientList = query.list();
            long startProcessProjects = System.currentTimeMillis();
            query = session.createQuery("select project from app.project.Project project left join fetch project.SourceDocs sds where project.pm_id = " + pmID + "  and project.startDate>DATE_SUB(current_date,INTERVAL  " + myExcelRange + " day) ");
            List projects = query.list();
            // //System.out.println("projects.size():"+projects.size());
            // for(int i=0;i<clientList.size();i++){

            //     Client c = (Client)clientList.get(i);
            //     //System.out.println("Processing client: "+c.getCompany_name());
            //get Tasks for display
            List linTasksDisplay = new ArrayList();
            List engTasksDisplay = new ArrayList();
            List dtpTasksDisplay = new ArrayList();
            List othTasksDisplay = new ArrayList();

            //iterate through tasks and accumulate by task type (lin, eng, dtp, oth)

            //Hibernate.initialize(c.getProjects());
            // Set projects = c.getProjects();
            HashMap fetchedCompanies = new HashMap();
            String appClients = "";
            if (projects != null) {
                //for each project
                for (Iterator projectsIter = projects.iterator(); projectsIter.hasNext();) {
                    Project p = (Project) projectsIter.next();
                    //p = ProjectService.getInstance().getSingleProject(p.getProjectId());
                    appClients += p.getSourceApplication() + "_" + p.getCompany().getCompany_name() + "___";
                    Set sourceDocs = p.getSourceDocs();
                    if (sourceDocs != null) {
                        for (Iterator sourceDocsIter = sourceDocs.iterator(); sourceDocsIter.hasNext();) {
                            SourceDoc sd = (SourceDoc) sourceDocsIter.next();
                            //sd = ProjectService.getInstance().getSingleSourceDoc(sd.getSourceDocId());

                            Set targetDocs = sd.getTargetDocs();
                            if (targetDocs != null) {
                                for (Iterator targetDocsIter = targetDocs.iterator(); targetDocsIter.hasNext();) {
                                    TargetDoc td = (TargetDoc) targetDocsIter.next();

                                    Set dtpTasks = td.getDtpTasks();
                                    if (dtpTasks != null) {
                                        for (Iterator dtpTasksIter = dtpTasks.iterator(); dtpTasksIter.hasNext();) {
                                            DtpTask dt = (DtpTask) dtpTasksIter.next();
                                            if (dt.getPersonName() != null) {
                                                dtpTasksDisplay.add(dt);
                                                fetchedCompanies.put(dt.getDtpTaskId(), p.getCompany());
                                            }
                                        }
                                    }


                                }
                            }
                        }
                    }
                }
            }

            long endProcessProjects = System.currentTimeMillis();
            // //System.out.println("processProjects took:"+((endProcessProjects-startProcessProjects)/1000.00));

            HashMap fetchedResources = new HashMap();
            HashMap myTotalCounts = new HashMap();
            HashMap myClientTotalCounts = new HashMap();
            HashMap myTeamDisplays = new HashMap();


            for (ListIterator iter = dtpTasksDisplay.listIterator(); iter.hasNext();) {
                DtpTask t = (DtpTask) iter.next();

                Client c = (Client) fetchedCompanies.get(t.getDtpTaskId());

                MyTeamsDisplay mtd = null;
                if ((mtd = (MyTeamsDisplay) myTeamDisplays.get(t.getSourceLanguage() + "_" + t.getTargetLanguage() + "_" + t.getPersonName())) == null) {

                    mtd = new MyTeamsDisplay();
                    mtd.resourceId = t.getPersonName();
                    mtd.sourceLang = t.getSourceLanguage();
                    mtd.targetLang = t.getTargetLanguage();
                    mtd.client = c.getCompany_name();
                    mtd.clientId = c.getClientId();
                    mtd.dtpApp = t.getTaskName();
                    mtd.dtpCCy = t.getCurrency();
                    mtd.dtpRate = t.getRate();
                    mtd.dtpUnit = t.getUnitsTeam();

                    myTeamDisplays.put(t.getSourceLanguage() + "_" + t.getTargetLanguage() + "_" + t.getPersonName(), mtd);
                }

                MyTeamsClientTotals mtClient = null;
                if ((mtClient = (MyTeamsClientTotals) myClientTotalCounts.get(c.getCompany_name() + "_" + t.getPersonName())) == null) {
                    mtClient = new MyTeamsClientTotals();
                    mtClient.client = c.getCompany_name();
                    mtClient.resourceId = t.getPersonName();
                    myClientTotalCounts.put(c.getCompany_name() + "_" + t.getPersonName(), mtClient);
                }

                MyTeamsTotals mtTotals = null;
                if ((mtTotals = (MyTeamsTotals) myTotalCounts.get(t.getPersonName())) == null) {
                    mtTotals = new MyTeamsTotals();
                    mtTotals.resourceId = t.getPersonName();
                    myTotalCounts.put(t.getPersonName(), mtTotals);
                }

                Resource r = null;
                if ((r = (Resource) fetchedResources.get(t.getPersonName())) == null) {
                    Query query2 = session.createQuery("select resource from Resource as resource where resource.resourceId = " + t.getPersonName());
                    List myList = query2.list();
                    if (myList.size() > 0) {
                        r = (Resource) query2.list().get(0);
                        fetchedResources.put(t.getPersonName(), r);
                    } else {
                        fetchedResources.put(t.getPersonName(), new Resource());
                    }
                    //fetchedResources.put(t.getPersonName(), r);
                    mtTotals.totalPaid = getAllProjectsTotalForPerson(session, t.getPersonName());
                }

                if (t.getInternalDollarTotal() != null && !"".equals(t.getInternalDollarTotal())) {
                    mtClient.totalPaid += Double.valueOf(t.getInternalDollarTotal().replaceAll(",", "")).doubleValue();
                    //mtTotals.totalPaid += Double.valueOf(t.getInternalDollarTotal().replaceAll(",", "")).doubleValue();
                }
                mtClient.countProjects++;
                mtTotals.countProjects++;

                if (t.getScore() != null) {
                    mtTotals.totalScore += t.getScore().intValue();
                    //mtClient.totalScore += t.getScore().intValue();
                }


                String companyType = c.getIndustry().getDescription();

                if (companyType.indexOf("Medical, Dental, Pharmaceutical") > -1 && r.getMedicalScore() != null) {
                    mtClient.testScore = "" + r.getMedicalScore();
                } else if (companyType.indexOf("Business & Finance & Legal: Banking, Commerce, Management") > -1 && r.getLegalFinancialScore() != null) {
                    mtClient.testScore = "" + r.getLegalFinancialScore();
                } else if (companyType.indexOf("Computer: Hardware & Software, Localization") > -1 && r.getSoftwareScore() != null) {
                    mtClient.testScore = "" + r.getSoftwareScore();
                } else if (r.getTechnicalScore() != null) {
                    mtClient.testScore = "" + r.getTechnicalScore();
                }
            }


            HashMap addedApps = new HashMap();
            // //System.out.println("appClients="+appClients);
            for (Iterator iter = myTeamDisplays.values().iterator(); iter.hasNext();) {
                MyTeamsDisplay mtd = (MyTeamsDisplay) iter.next();
                Resource r = (Resource) fetchedResources.get(mtd.resourceId);
                for (Iterator projectsIter = r.getRateScoreDtps().iterator(); projectsIter.hasNext();) {
                    RateScoreDtp rscore = (RateScoreDtp) projectsIter.next();

                    if (appClients.indexOf(rscore.getApplication() + "_" + mtd.client + "___") > -1 && addedApps.get(r.getResourceId() + "_" + mtd.sourceLang + "_" + mtd.targetLang + "_" + rscore.getApplication()) == null) {


                        JSONObject jo = new JSONObject();
                        jo.put("sourceTarget", mtd.sourceLang + " into " + mtd.targetLang);
                        // jo.put("clientName", mtd.client);
                        jo.put("clientName", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:openClientWindowReverse('" + HrHelper.jsSafe(mtd.client) + "','" + mtd.clientId + "')\">" + mtd.client + "</a>");


                        if (!r.isAgency()) {
                            //jo.put("vendorName",r.getFirstName()+ " " + r.getLastName());
                            jo.put("vendorName", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:openVendorWindowReverse('" + HrHelper.jsSafe(r.getFirstName() + " " + r.getLastName()) + "','" + r.getResourceId() + "')\">" + r.getFirstName() + " " + r.getLastName() + "</a>");

                        } else {
                            //jo.put("vendorName",r.getCompanyName());
                            jo.put("vendorName", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:openVendorWindowReverse('" + HrHelper.jsSafe(r.getCompanyName()) + "','" + r.getResourceId() + "')\">" + r.getCompanyName() + "</a>");

                        }

                        if (rscore.getMin() != null) {
                            jo.put("minimumFee", StandardCode.getInstance().formatMoney(rscore.getMin().doubleValue()));
                        } else {
                            jo.put("minimumFee", "0");
                        }

                        String ratesApps = "";



                        jo.put("rate", rscore.getRate());
                        jo.put("application", rscore.getApplication());
                        jo.put("currency", noNull(rscore.getCurrency()));
                        jo.put("dtpUnit", rscore.getUnit());
                        MyTeamsTotals mtTotals = (MyTeamsTotals) myTotalCounts.get(mtd.resourceId);
                        MyTeamsClientTotals mtClient = (MyTeamsClientTotals) myClientTotalCounts.get(mtd.client + "_" + mtd.resourceId);
                        jo.put("countProjects", mtTotals.countProjects);
                        jo.put("totalPaid", StandardCode.getInstance().formatMoney(mtTotals.totalPaid));
                        //jo.put("averageScore", (mtTotals.totalScore/mtTotals.countProjects));
                        //jo.put("averageScore",  StandardCode.getInstance().formatMoney( ((mtTotals.totalScore*1.0)/(mtTotals.countProjects*1.0))));
                        String averagePerformanceScore = "0.00";
                        if (r.getProjectScoreAverage() != null) {
                            averagePerformanceScore = StandardCode.getInstance().formatDouble(r.getProjectScoreAverage());
                        }
                        jo.put("averageScore", averagePerformanceScore);
                        jo.put("taskName", mtd.dtpApp);
                        jo.put("testScore", mtClient.testScore);
                        jo.put("countProjectsClient", mtClient.countProjects);
                        jo.put("totalPaidClient", StandardCode.getInstance().formatMoney(mtClient.totalPaid));
                        ////System.out.println(mtd.resourceId+":::"+mtd.sourceLang+" to " + mtd.targetLang);
                        results.add(jo);
                        addedApps.put(r.getResourceId() + "_" + mtd.sourceLang + "_" + mtd.targetLang + "_" + rscore.getApplication(), "");
                    }
                }

            }



        } catch (Exception e) {
            //System.out.println("Exception::" + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e) {
                    //System.out.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }
        return results;
    }

    public static List getPMEngTeams(String pmName, int myExcelRange, Integer pmID) {



        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        //accumulate resource summary for display in ClientViewTeamDisplay object
        ArrayList results = new ArrayList();

        try {

            //query =	session.createQuery("select client from app.client.Client client left join client.Projects project where (client.Project_mngr='"+pmName+"' or client.Sales_rep='"+pmName+"' or project.pm = '"+pmName+"')");
            //List clientList = query.list();
            long startProcessProjects = System.currentTimeMillis();
            query = session.createQuery("select project from app.project.Project project left join fetch project.SourceDocs sds  where project.pm_id = " + pmID + " and project.startDate>DATE_SUB(current_date,INTERVAL  " + myExcelRange + " day)");
            List projects = query.list();
            ////System.out.println("projects.size():"+projects.size());
            // for(int i=0;i<clientList.size();i++){

            //     Client c = (Client)clientList.get(i);
            //     //System.out.println("Processing client: "+c.getCompany_name());
            //get Tasks for display

            List engTasksDisplay = new ArrayList();


            //iterate through tasks and accumulate by task type (lin, eng, dtp, oth)

            //Hibernate.initialize(c.getProjects());
            // Set projects = c.getProjects();
            HashMap fetchedCompanies = new HashMap();

            if (projects != null) {
                //for each project
                for (Iterator projectsIter = projects.iterator(); projectsIter.hasNext();) {
                    Project p = (Project) projectsIter.next();
                    //p = ProjectService.getInstance().getSingleProject(p.getProjectId());

                    Set sourceDocs = p.getSourceDocs();
                    if (sourceDocs != null) {
                        for (Iterator sourceDocsIter = sourceDocs.iterator(); sourceDocsIter.hasNext();) {
                            SourceDoc sd = (SourceDoc) sourceDocsIter.next();
                            //sd = ProjectService.getInstance().getSingleSourceDoc(sd.getSourceDocId());

                            Set targetDocs = sd.getTargetDocs();
                            if (targetDocs != null) {
                                for (Iterator targetDocsIter = targetDocs.iterator(); targetDocsIter.hasNext();) {
                                    TargetDoc td = (TargetDoc) targetDocsIter.next();

                                    Set engTasks = td.getEngTasks();
                                    if (engTasks != null) {
                                        for (Iterator engTasksIter = engTasks.iterator(); engTasksIter.hasNext();) {
                                            EngTask et = (EngTask) engTasksIter.next();
                                            if (et.getPersonName() != null) {
                                                engTasksDisplay.add(et);
                                                fetchedCompanies.put(et.getEngTaskId(), p.getCompany());
                                            }
                                        }
                                    }


                                }
                            }
                        }
                    }
                }
            }

            long endProcessProjects = System.currentTimeMillis();
            //  //System.out.println("processProjects took:"+((endProcessProjects-startProcessProjects)/1000.00));

            HashMap fetchedResources = new HashMap();
            HashMap myTotalCounts = new HashMap();
            HashMap myClientTotalCounts = new HashMap();
            HashMap myTeamDisplays = new HashMap();


            for (ListIterator iter = engTasksDisplay.listIterator(); iter.hasNext();) {
                EngTask t = (EngTask) iter.next();
                Resource r = null;



                Client c = (Client) fetchedCompanies.get(t.getEngTaskId());

                MyTeamsDisplay mtd = null;
                if ((mtd = (MyTeamsDisplay) myTeamDisplays.get(t.getTaskName() + "_" + t.getSourceLanguage() + "_" + t.getTargetLanguage() + "_" + t.getPersonName())) == null) {

                    mtd = new MyTeamsDisplay();
                    mtd.resourceId = t.getPersonName();
                    mtd.sourceLang = t.getSourceLanguage();
                    mtd.targetLang = t.getTargetLanguage();
                    mtd.client = c.getCompany_name();
                    mtd.clientId = c.getClientId();
                    mtd.dtpApp = t.getTaskName();
                    mtd.dtpCCy = t.getCurrency();
                    mtd.dtpRate = t.getRate();
                    mtd.dtpUnit = t.getUnitsTeam();
                    // //System.out.println("t.getTaskName()="+t.getTaskName());
                    myTeamDisplays.put(t.getTaskName() + "_" + t.getSourceLanguage() + "_" + t.getTargetLanguage() + "_" + t.getPersonName(), mtd);
                }

                MyTeamsClientTotals mtClient = null;
                if ((mtClient = (MyTeamsClientTotals) myClientTotalCounts.get(c.getCompany_name() + "_" + t.getPersonName())) == null) {
                    mtClient = new MyTeamsClientTotals();
                    mtClient.client = c.getCompany_name();
                    mtClient.resourceId = t.getPersonName();
                    myClientTotalCounts.put(c.getCompany_name() + "_" + t.getPersonName(), mtClient);
                }

                MyTeamsTotals mtTotals = null;
                if ((mtTotals = (MyTeamsTotals) myTotalCounts.get(t.getPersonName())) == null) {
                    mtTotals = new MyTeamsTotals();
                    mtTotals.resourceId = t.getPersonName();
                    myTotalCounts.put(t.getPersonName(), mtTotals);
                }

                if ((r = (Resource) fetchedResources.get(t.getPersonName())) == null) {
                    Query query2 = session.createQuery("select resource from Resource as resource where resource.resourceId = " + t.getPersonName());
                    List myList = query2.list();
                    if (myList.size() > 0) {
                        r = (Resource) query2.list().get(0);
                        fetchedResources.put(t.getPersonName(), r);
                    } else {
                        fetchedResources.put(t.getPersonName(), new Resource());
                    }
                    //fetchedResources.put(t.getPersonName(), r);
                    mtTotals.totalPaid = getAllProjectsTotalForPerson(session, t.getPersonName());
                }


                if (t.getInternalDollarTotal() != null && !"".equals(t.getInternalDollarTotal())) {
                    mtClient.totalPaid += Double.valueOf(t.getInternalDollarTotal().replaceAll(",", "")).doubleValue();
                    // mtTotals.totalPaid += Double.valueOf(t.getInternalDollarTotal().replaceAll(",", "")).doubleValue();
                }
                mtClient.countProjects++;
                mtTotals.countProjects++;

                if (t.getScore() != null) {
                    mtTotals.totalScore += t.getScore().intValue();
                    //mtClient.totalScore += t.getScore().intValue();
                }


                String companyType = c.getIndustry().getDescription();

                if (companyType.indexOf("Medical, Dental, Pharmaceutical") > -1 && r.getMedicalScore() != null) {
                    mtClient.testScore = "" + r.getMedicalScore();
                } else if (companyType.indexOf("Business & Finance & Legal: Banking, Commerce, Management") > -1 && r.getLegalFinancialScore() != null) {
                    mtClient.testScore = "" + r.getLegalFinancialScore();
                } else if (companyType.indexOf("Computer: Hardware & Software, Localization") > -1 && r.getSoftwareScore() != null) {
                    mtClient.testScore = "" + r.getSoftwareScore();
                } else if (r.getTechnicalScore() != null) {
                    mtClient.testScore = "" + r.getTechnicalScore();
                }
            }


            HashMap addedApps = new HashMap();

            for (Iterator iter = myTeamDisplays.values().iterator(); iter.hasNext();) {
                MyTeamsDisplay mtd = (MyTeamsDisplay) iter.next();
                Resource r = (Resource) fetchedResources.get(mtd.resourceId);
                if (addedApps.get(mtd.sourceLang + "_" + mtd.targetLang + "_" + mtd.dtpApp) == null) {
                    JSONObject jo = new JSONObject();
                    jo.put("sourceTarget", mtd.sourceLang + " into " + mtd.targetLang);
                    //jo.put("clientName", mtd.client);
                    jo.put("clientName", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:openClientWindowReverse('" + HrHelper.jsSafe(mtd.client) + "','" + mtd.clientId + "')\">" + mtd.client + "</a>");


                    if (!r.isAgency()) {
                        //jo.put("vendorName",r.getFirstName()+ " " + r.getLastName());
                        jo.put("vendorName", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:openVendorWindowReverse('" + HrHelper.jsSafe(r.getFirstName() + " " + r.getLastName()) + "','" + r.getResourceId() + "')\">" + r.getFirstName() + " " + r.getLastName() + "</a>");

                    } else {
                        //jo.put("vendorName",r.getCompanyName());
                        jo.put("vendorName", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:openVendorWindowReverse('" + HrHelper.jsSafe(r.getCompanyName()) + "','" + r.getResourceId() + "')\">" + r.getCompanyName() + "</a>");

                    }
                    String ratesApps = "";


                    if (r.getMin() != null) {
                        jo.put("minimumFee", StandardCode.getInstance().formatMoney(r.getMin().doubleValue()));
                    } else {
                        jo.put("minimumFee", "0");
                    }

                    jo.put("rate", "");
                    jo.put("currency", "");
                    jo.put("unit", "");

                    MyTeamsTotals mtTotals = (MyTeamsTotals) myTotalCounts.get(mtd.resourceId);
                    MyTeamsClientTotals mtClient = (MyTeamsClientTotals) myClientTotalCounts.get(mtd.client + "_" + mtd.resourceId);
                    jo.put("countProjects", mtTotals.countProjects);
                    jo.put("totalPaid", StandardCode.getInstance().formatMoney(mtTotals.totalPaid));
                    //jo.put("averageScore", (mtTotals.totalScore/mtTotals.countProjects));
                    //jo.put("averageScore", StandardCode.getInstance().formatMoney(((mtTotals.totalScore*1.0)/(mtTotals.countProjects*1.0))));
                    String averagePerformanceScore = "0.00";
                    if (r.getProjectScoreAverage() != null) {
                        averagePerformanceScore = StandardCode.getInstance().formatDouble(r.getProjectScoreAverage());
                    }
                    jo.put("averageScore", averagePerformanceScore);


                    jo.put("taskName", mtd.dtpApp);
                    jo.put("testScore", mtClient.testScore);
                    jo.put("countProjectsClient", mtClient.countProjects);
                    jo.put("totalPaidClient", StandardCode.getInstance().formatMoney(mtClient.totalPaid));
                    ////System.out.println(mtd.resourceId+":::"+mtd.sourceLang+" to " + mtd.targetLang);
                    results.add(jo);
                    addedApps.put(mtd.sourceLang + "_" + mtd.targetLang + "_" + mtd.dtpApp, "");
                }


            }



        } catch (Exception e) {
            //System.out.println("Exception::" + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e) {
                    //System.out.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }
        return results;
    }

    public static double getAllProjectsTotalForPerson(Session session, String personId) throws Exception {
        double myTotal = 0;
        PreparedStatement st = session.connection().prepareStatement("Select SUM(CAST(internalDollarTotal AS DECIMAL)) as mysum from lintask where personName=?");
        st.setString(1, personId);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            myTotal += rs.getDouble("mysum");
        }
        st.close();
        st = null;

        st = session.connection().prepareStatement("Select SUM(CAST(internalDollarTotal AS DECIMAL)) as mysum from dtptask where personName=?");
        st.setString(1, personId);
        rs = st.executeQuery();
        if (rs.next()) {
            myTotal += rs.getDouble("mysum");
        }
        st.close();
        st = null;

        st = session.connection().prepareStatement("Select SUM(CAST(internalDollarTotal AS DECIMAL)) as mysum from engtask where personName=?");
        st.setString(1, personId);
        rs = st.executeQuery();
        if (rs.next()) {
            myTotal += rs.getDouble("mysum");
        }
        st.close();
        st = null;

        st = session.connection().prepareStatement("Select SUM(CAST(internalDollarTotal AS DECIMAL)) as mysum from othtask where personName=?");
        st.setString(1, personId);
        rs = st.executeQuery();
        if (rs.next()) {
            myTotal += rs.getDouble("mysum");
        }
        st.close();
        st = null;

        return myTotal;


    }

    public static int getAllProjectsCountForPerson(Session session, String personId) throws Exception {
        int myTotal = 0;
        PreparedStatement st = session.connection().prepareStatement("Select count(*) from lintask where personName=?");
        st.setString(1, personId);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            myTotal += rs.getInt(1);
        }
        st.close();
        st = null;

        st = session.connection().prepareStatement("Select count(*) from dtptask where personName=?");
        st.setString(1, personId);
        rs = st.executeQuery();
        if (rs.next()) {
            myTotal += rs.getInt(1);
        }
        st.close();
        st = null;

        st = session.connection().prepareStatement("Select count(*) from engtask where personName=?");
        st.setString(1, personId);
        rs = st.executeQuery();
        if (rs.next()) {
            myTotal += rs.getInt(1);
        }
        st.close();
        st = null;

        st = session.connection().prepareStatement("Select count(*)  from othtask where personName=?");
        st.setString(1, personId);
        rs = st.executeQuery();
        if (rs.next()) {
            myTotal += rs.getInt(1);
        }
        st.close();
        st = null;

        return myTotal;


    }

    public static String noNull(Object o) {
        if (o == null) {
            return "";
        } else {
            return o.toString();
        }
    }

    public static List getClientTeams(String clientId,String year) {

String sDate=year+"-01-01";
String eDate=year+"-12-31";

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        //accumulate resource summary for display in ClientViewTeamDisplay object
        ArrayList results = new ArrayList();

        try {

            //query =	session.createQuery("select client from app.client.Client client left join client.Projects project where (client.Project_mngr='"+pmName+"' or client.Sales_rep='"+pmName+"' or project.pm = '"+pmName+"')");
            //List clientList = query.list();
            long startProcessProjects = System.currentTimeMillis();


            //query = session.createQuery("select project from app.project.Project project left join fetch project.SourceDocs sds where project.Company.clientId="+clientId +" order by project.completeDate desc");
            //query.setMaxResults(100);
            //System.out.println("bfr query excuteed********************************************");
//          query = session.createQuery("select project from app.project.Project as project left join fetch project.SourceDocs sds where project.Company.clientId='" + clientId + "' order by project.completeDate desc");
            query = session.createQuery("select project from app.project.Project as project where project.Company.clientId='" + clientId + "' and project.completeDate <'"+eDate+"' and project.completeDate >'"+sDate+"'");
            //System.out.println("after query executed******************************" + query);
            List projects = query.list();
            //System.out.println("Query size of getClientTeams ********************" + projects.size());
            //  //System.out.println("projects.size():"+projects.size());
            // for(int i=0;i<clientList.size();i++){

            //     Client c = (Client)clientList.get(i);
            //     //System.out.println("Processing client: "+c.getCompany_name());
            //get Tasks for display
            List linTasksDisplay = new ArrayList();
            List engTasksDisplay = new ArrayList();
            List dtpTasksDisplay = new ArrayList();
            List othTasksDisplay = new ArrayList();


            //iterate through tasks and accumulate by task type (lin, eng, dtp, oth)

            //Hibernate.initialize(c.getProjects());
            // Set projects = c.getProjects();
            HashMap fetchedCompanies = new HashMap();

            if (projects != null) {
                //for each project
                for (Iterator projectsIter = projects.iterator(); projectsIter.hasNext();) {


                    Project p = (Project) projectsIter.next();
                    //p = ProjectService.getInstance().getSingleProject(p.getProjectId());

                    Set sourceDocs = p.getSourceDocs();
                    if (sourceDocs != null) {
                        for (Iterator sourceDocsIter = sourceDocs.iterator(); sourceDocsIter.hasNext();) {
                            SourceDoc sd = (SourceDoc) sourceDocsIter.next();
                            //sd = ProjectService.getInstance().getSingleSourceDoc(sd.getSourceDocId());

                            Set targetDocs = sd.getTargetDocs();
                            if (targetDocs != null) {
                                for (Iterator targetDocsIter = targetDocs.iterator(); targetDocsIter.hasNext();) {
                                    TargetDoc td = (TargetDoc) targetDocsIter.next();
                                    Set linTasks = td.getLinTasks();
                                    if (linTasks != null) {
                                        for (Iterator linTasksIter = linTasks.iterator(); linTasksIter.hasNext();) {
                                            LinTask lt = (LinTask) linTasksIter.next();
                                            if (lt.getPersonName() != null) {
                                                linTasksDisplay.add(lt);
                                                fetchedCompanies.put(lt.getLinTaskId(), p.getCompany());
                                            }
                                        }
                                    }

                                    Set engTasks = td.getEngTasks();
                                    if (engTasks != null) {
                                        for (Iterator engTasksIter = engTasks.iterator(); engTasksIter.hasNext();) {
                                            EngTask et = (EngTask) engTasksIter.next();
                                            if (et.getPersonName() != null) {
                                                engTasksDisplay.add(et);
                                                fetchedCompanies.put(et.getEngTaskId(), p.getCompany());
                                            }
                                        }
                                    }

                                    Set dtpTasks = td.getDtpTasks();
                                    if (dtpTasks != null) {
                                        for (Iterator dtpTasksIter = dtpTasks.iterator(); dtpTasksIter.hasNext();) {
                                            DtpTask dt = (DtpTask) dtpTasksIter.next();
                                            if (dt.getPersonName() != null) {
                                                dtpTasksDisplay.add(dt);
                                                fetchedCompanies.put(dt.getDtpTaskId(), p.getCompany());
                                            }
                                        }
                                    }

                                    Set othTasks = td.getOthTasks();
                                    if (othTasks != null) {
                                        for (Iterator othTasksIter = othTasks.iterator(); othTasksIter.hasNext();) {
                                            OthTask ot = (OthTask) othTasksIter.next();
                                            if (ot.getPersonName() != null) {
                                                othTasksDisplay.add(ot);
                                                fetchedCompanies.put(ot.getOthTaskId(), p.getCompany());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            long endProcessProjects = System.currentTimeMillis();
            ////System.out.println("got all tasks took:"+((endProcessProjects-startProcessProjects)/1000.00));

            HashMap fetchedResources = new HashMap();
            HashMap myTotalCounts = new HashMap();
            HashMap myClientTotalCounts = new HashMap();
            HashMap myTeamDisplays = new HashMap();
            HashMap rateScoreLangs = new HashMap();

            for (ListIterator iter = linTasksDisplay.listIterator(); iter.hasNext();) {
                LinTask t = (LinTask) iter.next();

                Client c = (Client) fetchedCompanies.get(t.getLinTaskId());
                //Test
                MyTeamsDisplay mtd = null;
                // if((mtd = (MyTeamsDisplay)myTeamDisplays.get(t.getPersonName()))==null){
                if ((mtd = (MyTeamsDisplay) myTeamDisplays.get("(LIN) " + t.getSourceLanguage() + "_" + t.getTargetLanguage() + "_" + t.getPersonName())) == null) {

                    mtd = new MyTeamsDisplay();
                    mtd.resourceId = t.getPersonName();
                    mtd.sourceLang = "(LIN) " + t.getSourceLanguage();
                    mtd.targetLang = t.getTargetLanguage();
                    mtd.client = c.getCompany_name();
                    mtd.clientId = c.getClientId();
                    myTeamDisplays.put("(LIN) " + t.getSourceLanguage() + "_" + t.getTargetLanguage() + "_" + t.getPersonName(), mtd);
                }

                MyTeamsClientTotals mtClient = null;
                if ((mtClient = (MyTeamsClientTotals) myClientTotalCounts.get(c.getCompany_name() + "_" + t.getPersonName())) == null) {
                    mtClient = new MyTeamsClientTotals();
                    mtClient.client = c.getCompany_name();
                    //mtClient.clientClean = c.getCompany_name();
                    mtClient.resourceId = t.getPersonName();
                    myClientTotalCounts.put(c.getCompany_name() + "_" + t.getPersonName(), mtClient);

                    String companyType = c.getIndustry().getDescription();

                    if (companyType.indexOf("Medical, Dental, Pharmaceutical") > -1) {

                        mtClient.client += " - Medical";
                        mtClient.clientType = "Medical";
                    } else if (companyType.indexOf("Business & Finance & Legal: Banking, Commerce, Management") > -1) {

                        mtClient.client += " - Finance/Legal";
                        mtClient.clientType = "Legal/Financial";
                    } else if (companyType.indexOf("Computer: Hardware & Software, Localization") > -1) {

                        mtClient.client += " - Software";
                        mtClient.clientType = "Software";
                    } else {

                        mtClient.client += " - Technical";
                        mtClient.clientType = "Technical";
                    }

                }

                MyTeamsTotals mtTotals = null;
                if ((mtTotals = (MyTeamsTotals) myTotalCounts.get(t.getPersonName())) == null) {
                    mtTotals = new MyTeamsTotals();
                    mtTotals.resourceId = t.getPersonName();
                    myTotalCounts.put(t.getPersonName(), mtTotals);
                }
                Resource r = null;
                if ((r = (Resource) fetchedResources.get(t.getPersonName())) == null) {
                  try{
//                  //System.out.println("Name==============>>"+t.getPersonName());
                    Query query2 = session.createQuery("select resource from Resource as resource where resource.resourceId = " + t.getPersonName());

                    List myList = query2.list();
//                    //System.out.println("Name==============>>"+t.getPersonName());
                    if (myList.size() > 0) {
                        r = (Resource) query2.list().get(0);
                        fetchedResources.put(t.getPersonName(), r);
                    } else {
                        fetchedResources.put(t.getPersonName(), new Resource());
                    }
                    // fetchedResources.put(t.getPersonName(), r);

                    mtTotals.totalPaid = getAllProjectsTotalForPerson(session, t.getPersonName());
                    mtTotals.countProjects = getAllProjectsCountForPerson(session, t.getPersonName());

                  }catch(Exception e){
                    //System.out.println("errorrrrrrrrrrrr"+t.getLinTaskId());
                  }

                }

                if (t.getInternalDollarTotal() != null && !"".equals(t.getInternalDollarTotal())) {
                    mtClient.totalPaid += Double.valueOf(t.getInternalDollarTotal().replaceAll(",", "")).doubleValue();
                    //mtTotals.totalPaid += Double.valueOf(t.getInternalDollarTotal().replaceAll(",", "")).doubleValue();
                }
                mtClient.countProjects++;
                // mtTotals.countProjects++;
                // //System.out.println("person="+t.getPersonName()+", score="+t.getScore());
                if (t.getScore() != null) {
                    mtTotals.totalScore += t.getScore().intValue();
                    if(t.getScore().intValue()==0){mtTotals.totalScore +=35;}
                    //mtClient.totalScore += t.getScore().intValue();
                }

                mtTotals.countLIN++;

                if (t.getTaskName() != null) {
                    if (t.getTaskName().equalsIgnoreCase("Editing")) {
                        mtClient.countE++;
                        mtTotals.countE++;
                    } else if (t.getTaskName().equalsIgnoreCase("Proofreading / Linguistic QA")) {
                        mtClient.countP++;
                        mtTotals.countP++;
                    } else if (t.getTaskName().equalsIgnoreCase("Translation")) {
                        mtClient.countT++;
                        mtTotals.countT++;
                    }//Add the other two here once they are added
                }


            }
            //  //System.out.println("dtp");

            for (ListIterator iter = dtpTasksDisplay.listIterator(); iter.hasNext();) {
                DtpTask t = (DtpTask) iter.next();


                Client c = (Client) fetchedCompanies.get(t.getDtpTaskId());

                MyTeamsDisplay mtd = null;
                //  if((mtd = (MyTeamsDisplay)myTeamDisplays.get(t.getPersonName()))==null){
                if ((mtd = (MyTeamsDisplay) myTeamDisplays.get("(DTP) " + t.getSourceLanguage() + "_" + t.getTargetLanguage() + "_" + t.getPersonName())) == null) {

                    mtd = new MyTeamsDisplay();
                    mtd.resourceId = t.getPersonName();
                    mtd.sourceLang = "(DTP) " + t.getSourceLanguage();
                    mtd.targetLang = t.getTargetLanguage();
                    mtd.client = c.getCompany_name();
                    mtd.clientId = c.getClientId();
                    myTeamDisplays.put("(DTP) " + t.getSourceLanguage() + "_" + t.getTargetLanguage() + "_" + t.getPersonName(), mtd);
                }

                MyTeamsClientTotals mtClient = null;
                if ((mtClient = (MyTeamsClientTotals) myClientTotalCounts.get(c.getCompany_name() + "_" + t.getPersonName())) == null) {
                    mtClient = new MyTeamsClientTotals();
                    mtClient.client = c.getCompany_name();
                    //mtClient.clientClean = c.getCompany_name();
                    mtClient.resourceId = t.getPersonName();
                    myClientTotalCounts.put(c.getCompany_name() + "_" + t.getPersonName(), mtClient);

                    String companyType = c.getIndustry().getDescription();

                    if (companyType.indexOf("Medical, Dental, Pharmaceutical") > -1) {

                        mtClient.client += " - Medical";
                        mtClient.clientType = "Medical";
                    } else if (companyType.indexOf("Business & Finance & Legal: Banking, Commerce, Management") > -1) {

                        mtClient.client += " - Finance/Legal";
                        mtClient.clientType = "Legal/Financial";
                    } else if (companyType.indexOf("Computer: Hardware & Software, Localization") > -1) {

                        mtClient.client += " - Software";
                        mtClient.clientType = "Software";
                    } else {

                        mtClient.client += " - Technical";
                        mtClient.clientType = "Technical";
                    }

                }

                MyTeamsTotals mtTotals = null;
                if ((mtTotals = (MyTeamsTotals) myTotalCounts.get(t.getPersonName())) == null) {
                    mtTotals = new MyTeamsTotals();
                    mtTotals.resourceId = t.getPersonName();
                    myTotalCounts.put(t.getPersonName(), mtTotals);
                }
                Resource r = null;
                if ((r = (Resource) fetchedResources.get(t.getPersonName())) == null) {
                    Query query2 = session.createQuery("select resource from Resource as resource where resource.resourceId = " + t.getPersonName());
                    List myList = query2.list();
                    if (myList.size() > 0) {
                        r = (Resource) query2.list().get(0);
                        fetchedResources.put(t.getPersonName(), r);
                    } else {
                        fetchedResources.put(t.getPersonName(), new Resource());
                    }
                    //fetchedResources.put(t.getPersonName(), r);

                    mtTotals.totalPaid = getAllProjectsTotalForPerson(session, t.getPersonName());
                    mtTotals.countProjects = getAllProjectsCountForPerson(session, t.getPersonName());

                    //the list for display (load all ratescorelanguage entries)
                    // Set languagePairs = r.getLanguagePairs();
                    ////System.out.println("languagePairs="+languagePairs);



                }

                if (t.getInternalDollarTotal() != null && !"".equals(t.getInternalDollarTotal())) {
                    mtClient.totalPaid += Double.valueOf(t.getInternalDollarTotal().replaceAll(",", "")).doubleValue();
                    //mtTotals.totalPaid += Double.valueOf(t.getInternalDollarTotal().replaceAll(",", "")).doubleValue();
                }
                mtClient.countProjects++;
                // mtTotals.countProjects++;
                // //System.out.println("person="+t.getPersonName()+", score="+t.getScore());
                if (t.getScore() != null) {
                    mtTotals.totalScore += t.getScore().intValue();
                    //mtClient.totalScore += t.getScore().intValue();
                }

                mtTotals.countDTP++;




            }

            ////System.out.println("eng");
            for (ListIterator iter = engTasksDisplay.listIterator(); iter.hasNext();) {
                EngTask t = (EngTask) iter.next();


                Client c = (Client) fetchedCompanies.get(t.getEngTaskId());

                MyTeamsDisplay mtd = null;
                // if((mtd = (MyTeamsDisplay)myTeamDisplays.get(t.getPersonName()))==null){
                if ((mtd = (MyTeamsDisplay) myTeamDisplays.get("(ENG) " + t.getSourceLanguage() + "_" + t.getTargetLanguage() + "_" + t.getPersonName())) == null) {

                    mtd = new MyTeamsDisplay();
                    mtd.resourceId = t.getPersonName();
                    mtd.sourceLang = "(ENG) " + t.getSourceLanguage();
                    mtd.targetLang = t.getTargetLanguage();
                    mtd.client = c.getCompany_name();
                    mtd.clientId = c.getClientId();
                    myTeamDisplays.put("(ENG) " + t.getSourceLanguage() + "_" + t.getTargetLanguage() + "_" + t.getPersonName(), mtd);
                }

                MyTeamsClientTotals mtClient = null;
                if ((mtClient = (MyTeamsClientTotals) myClientTotalCounts.get(c.getCompany_name() + "_" + t.getPersonName())) == null) {
                    mtClient = new MyTeamsClientTotals();
                    mtClient.client = c.getCompany_name();
                    //mtClient.clientClean = c.getCompany_name();
                    mtClient.resourceId = t.getPersonName();
                    myClientTotalCounts.put(c.getCompany_name() + "_" + t.getPersonName(), mtClient);

                    String companyType = c.getIndustry().getDescription();

                    if (companyType.indexOf("Medical, Dental, Pharmaceutical") > -1) {

                        mtClient.client += " - Medical";
                        mtClient.clientType = "Medical";
                    } else if (companyType.indexOf("Business & Finance & Legal: Banking, Commerce, Management") > -1) {

                        mtClient.client += " - Finance/Legal";
                        mtClient.clientType = "Legal/Financial";
                    } else if (companyType.indexOf("Computer: Hardware & Software, Localization") > -1) {

                        mtClient.client += " - Software";
                        mtClient.clientType = "Software";
                    } else {

                        mtClient.client += " - Technical";
                        mtClient.clientType = "Technical";
                    }

                }

                MyTeamsTotals mtTotals = null;
                if ((mtTotals = (MyTeamsTotals) myTotalCounts.get(t.getPersonName())) == null) {
                    mtTotals = new MyTeamsTotals();
                    mtTotals.resourceId = t.getPersonName();
                    myTotalCounts.put(t.getPersonName(), mtTotals);
                }
                Resource r = null;
                if ((r = (Resource) fetchedResources.get(t.getPersonName())) == null) {
                    Query query2 = session.createQuery("select resource from Resource as resource where resource.resourceId = " + t.getPersonName());
                    List myList = query2.list();
                    if (myList.size() > 0) {
                        r = (Resource) query2.list().get(0);
                        fetchedResources.put(t.getPersonName(), r);
                    } else {
                        fetchedResources.put(t.getPersonName(), new Resource());
                    }
                    // fetchedResources.put(t.getPersonName(), r);

                    mtTotals.totalPaid = getAllProjectsTotalForPerson(session, t.getPersonName());
                    mtTotals.countProjects = getAllProjectsCountForPerson(session, t.getPersonName());

                    //the list for display (load all ratescorelanguage entries)
                    // Set languagePairs = r.getLanguagePairs();
                    ////System.out.println("languagePairs="+languagePairs);



                }

                if (t.getInternalDollarTotal() != null && !"".equals(t.getInternalDollarTotal())) {
                    mtClient.totalPaid += Double.valueOf(t.getInternalDollarTotal().replaceAll(",", "")).doubleValue();
                    //mtTotals.totalPaid += Double.valueOf(t.getInternalDollarTotal().replaceAll(",", "")).doubleValue();
                }
                mtClient.countProjects++;
                //mtTotals.countProjects++;
                // //System.out.println("person="+t.getPersonName()+", score="+t.getScore());
                if (t.getScore() != null) {
                    mtTotals.totalScore += t.getScore().intValue();
                    //mtClient.totalScore += t.getScore().intValue();
                }

                mtTotals.countENG++;




            }

            // //System.out.println("oth");

            for (ListIterator iter = othTasksDisplay.listIterator(); iter.hasNext();) {
                OthTask t = (OthTask) iter.next();


                Client c = (Client) fetchedCompanies.get(t.getOthTaskId());

                MyTeamsDisplay mtd = null;
                // if((mtd = (MyTeamsDisplay)myTeamDisplays.get(t.getPersonName()))==null){
                if ((mtd = (MyTeamsDisplay) myTeamDisplays.get("(OTH) " + t.getSourceLanguage() + "_" + t.getTargetLanguage() + "_" + t.getPersonName())) == null) {

                    mtd = new MyTeamsDisplay();
                    mtd.resourceId = t.getPersonName();
                    mtd.sourceLang = "(OTH) " + t.getSourceLanguage();
                    mtd.targetLang = t.getTargetLanguage();
                    mtd.client = c.getCompany_name();
                    mtd.clientId = c.getClientId();
                    myTeamDisplays.put("(OTH) " + t.getSourceLanguage() + "_" + t.getTargetLanguage() + "_" + t.getPersonName(), mtd);
                }

                MyTeamsClientTotals mtClient = null;
                if ((mtClient = (MyTeamsClientTotals) myClientTotalCounts.get(c.getCompany_name() + "_" + t.getPersonName())) == null) {
                    mtClient = new MyTeamsClientTotals();
                    mtClient.client = c.getCompany_name();
                    //mtClient.clientClean = c.getCompany_name();
                    mtClient.resourceId = t.getPersonName();
                    myClientTotalCounts.put(c.getCompany_name() + "_" + t.getPersonName(), mtClient);

                    String companyType = c.getIndustry().getDescription();

                    if (companyType.indexOf("Medical, Dental, Pharmaceutical") > -1) {

                        mtClient.client += " - Medical";
                        mtClient.clientType = "Medical";
                    } else if (companyType.indexOf("Business & Finance & Legal: Banking, Commerce, Management") > -1) {

                        mtClient.client += " - Finance/Legal";
                        mtClient.clientType = "Legal/Financial";
                    } else if (companyType.indexOf("Computer: Hardware & Software, Localization") > -1) {

                        mtClient.client += " - Software";
                        mtClient.clientType = "Software";
                    } else {

                        mtClient.client += " - Technical";
                        mtClient.clientType = "Technical";
                    }

                }

                MyTeamsTotals mtTotals = null;
                if ((mtTotals = (MyTeamsTotals) myTotalCounts.get(t.getPersonName())) == null) {
                    mtTotals = new MyTeamsTotals();
                    mtTotals.resourceId = t.getPersonName();
                    myTotalCounts.put(t.getPersonName(), mtTotals);
                }
                Resource r = null;
                if ((r = (Resource) fetchedResources.get(t.getPersonName())) == null) {
                    Query query2 = session.createQuery("select resource from Resource as resource where resource.resourceId = " + t.getPersonName());
                    List myList = query2.list();
                    if (myList.size() > 0) {
                        r = (Resource) query2.list().get(0);
                        fetchedResources.put(t.getPersonName(), r);
                    } else {
                        fetchedResources.put(t.getPersonName(), new Resource());
                    }
                    //fetchedResources.put(t.getPersonName(), r);

                    mtTotals.totalPaid = getAllProjectsTotalForPerson(session, t.getPersonName());
                    mtTotals.countProjects = getAllProjectsCountForPerson(session, t.getPersonName());

                    //the list for display (load all ratescorelanguage entries)
                    // Set languagePairs = r.getLanguagePairs();
                    ////System.out.println("languagePairs="+languagePairs);



                }

                if (t.getInternalDollarTotal() != null && !"".equals(t.getInternalDollarTotal())) {
                    mtClient.totalPaid += Double.valueOf(t.getInternalDollarTotal().replaceAll(",", "")).doubleValue();
                    //mtTotals.totalPaid += Double.valueOf(t.getInternalDollarTotal().replaceAll(",", "")).doubleValue();
                }
                mtClient.countProjects++;
                // mtTotals.countProjects++;
                // //System.out.println("person="+t.getPersonName()+", score="+t.getScore());
                if (t.getScore() != null) {
                    mtTotals.totalScore += t.getScore().intValue();
                    //mtClient.totalScore += t.getScore().intValue();
                }
                mtTotals.countOTH++;



            }


            ////System.out.println("finished oth");

            for (Iterator iter = myTeamDisplays.values().iterator(); iter.hasNext();) {
             try{   
              MyTeamsDisplay mtd = (MyTeamsDisplay) iter.next();
                JSONObject jo = new JSONObject();


                jo.put("sourceTarget", mtd.sourceLang + " to " + mtd.targetLang);

                Resource r = (Resource) fetchedResources.get(mtd.resourceId);



                if (!r.isAgency()) {
                    //jo.put("vendorName",r.getFirstName()+ " " + r.getLastName());
                    jo.put("vendorName", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openVendorWindowReverse('" + HrHelper.jsSafe(r.getFirstName() + " " + r.getLastName()) + "','" + r.getResourceId() + "')\">" + r.getFirstName() + " " + r.getLastName() + "</a>");

                } else {
                    //jo.put("vendorName",r.getCompanyName());
                    jo.put("vendorName", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openVendorWindowReverse('" + HrHelper.jsSafe(r.getCompanyName()) + "','" + r.getResourceId() + "')\">" + r.getCompanyName() + "</a>");

                }

                MyTeamsClientTotals mtClient = (MyTeamsClientTotals) myClientTotalCounts.get(mtd.client + "_" + mtd.resourceId);



                MyTeamsTotals mtTotals = (MyTeamsTotals) myTotalCounts.get(mtd.resourceId);
                jo.put("T_Count_Total", mtTotals.countT);
                jo.put("E_Count_Total", mtTotals.countE);
                jo.put("P_Count_Total", mtTotals.countP);
                jo.put("TE_Count_Total", mtTotals.countTE);
                jo.put("ICR_Count_Total", mtTotals.countICR);

                //jo.put("clientName", mtClient.client);
                //jo.put("clientName","<a "+HrHelper.LINK_STYLE+" href=\"javascript:openClientWindowReverse('"+HrHelper.jsSafe(mtClient.client)+"','"+ mtd.clientId+"')\">"+mtClient.client+"</a>");
                //jo.put("clientClean",mtClient.clientClean);
                jo.put("T_Count_Client", mtClient.countT);
                jo.put("E_Count_Client", mtClient.countE);
                jo.put("P_Count_Client", mtClient.countP);
                jo.put("TE_Count_Client", mtClient.countTE);
                jo.put("ICR_Count_Client", mtClient.countICR);

                jo.put("countLIN", mtTotals.countLIN);
                jo.put("countDTP", mtTotals.countDTP);
                jo.put("countOTH", mtTotals.countOTH);
                jo.put("countENG", mtTotals.countENG);

                jo.put("countProjects", mtTotals.countProjects);
                jo.put("totalPaid", StandardCode.getInstance().formatMoney(mtTotals.totalPaid));
                //jo.put("averageScore",  StandardCode.getInstance().formatMoney( ((mtTotals.totalScore*1.0)/(mtTotals.countProjects*1.0))));



                //GET OSA SCORE and # OF PROJECTS FIRST
            PreparedStatement st = session.connection().prepareStatement(
                    "   select sum(score) AS score, count(id_project) AS projects  from " +
                    "  ( " +
                    "  select * from (  " +
                    "  select score,project.id_project, deliveryDate " +
                    "  from lintask, targetdoc, sourcedoc, project " +
                    "              where " +
                    "              personname=? " +
                    "              and lintask.id_targetDoc=targetdoc.id_targetDoc  " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "              and sourcedoc.id_project = project.id_project " +
                    "  ) AS T1  " +
                    "  union  " +
                    "  select * from (  " +
                    "  select score ,project.id_project , deliveryDate " +
                    "  from dtptask, targetdoc, sourcedoc , project " +
                    "              where  " +
                    "              personname=? " +
                    "              and dtptask.id_targetDoc=targetdoc.id_targetDoc " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " and sourcedoc.id_project = project.id_project " +
                    "  ) AS T2  " +
                    "  union  " +
                    "  select * from (  " +
                    "  select score ,project.id_project , deliveryDate " +
                    "  from engtask, targetdoc, sourcedoc , project " +
                    "              where " +
                    "              personname=? " +
                    "              and engtask.id_targetDoc=targetdoc.id_targetDoc " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " and sourcedoc.id_project = project.id_project " +
                    "  ) AS T3  " +
                    "  union  " +
                    "  select * from (  " +
                    "  select score ,project.id_project , deliveryDate " +
                    "  from othtask, targetdoc, sourcedoc , project " +
                    "              where " +
                    "              personname=? " +
                    "              and othtask.id_targetDoc=targetdoc.id_targetDoc " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " and sourcedoc.id_project = project.id_project " +
                    "  ) AS T4  " +
                    "  ) AS MAINTABLE "+
                    " group by year(deliveryDate) " +
                    " order by year(deliveryDate) desc ");


            st.setString

(1, "" + r.getResourceId());
            st.setString

(2, "" + r.getResourceId());
            st.setString

(3, "" + r.getResourceId());
              st.setString

(4, "" + r.getResourceId());
            ResultSet rs= st.executeQuery();
            int totalProjects = 0;
            double avgScore=0;
            int yearCount=0;
            while(rs.next()) {
             //   result += " | <b># PROJECTS: </b>" + rs.getInt("projects");
               // result +=" | <b>OSA: </b>" + StandardCode.getInstance().formatMoney(rs.getDouble("score") / rs.getInt("projects"));
                 totalProjects += rs.getInt("projects");
                avgScore+=rs.getDouble("score") / rs.getInt("projects") ;
                ++yearCount;

            }



//                String averagePerformanceScore = "0.00";
//                if (r.getProjectScoreAverage() != null) {
//                    averagePerformanceScore = StandardCode.getInstance().formatDouble(r.getProjectScoreAverage());
//
//                }
                jo.put("averageScore", StandardCode.getInstance().formatMoney(avgScore /yearCount));

                // jo.put("testScore", mtClient.testScore);
                jo.put("countProjectsClient", mtClient.countProjects);
                jo.put("totalPaidClient", StandardCode.getInstance().formatMoney(mtClient.totalPaid));
                //jo.put("averageScoreClient", (mtClient.totalScore/mtClient.countProjects));

                ////System.out.println(mtd.resourceId+":::"+mtd.sourceLang+" to " + mtd.targetLang);
                results.add(jo);
            }catch(Exception e1){
            }
            }



        } catch (Exception e) {
            //System.out.println("Exception::" + e);
            throw new RuntimeException(e);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e) {
                    //System.out.println("Hibernate Exception:" + e);
                    throw new RuntimeException(e);
                }

            }
        }
        return results;
    }

    public static String getVendorCurrentProjectsHtml(int resourceId) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        String result = "";

        try {


            PreparedStatement st = session.connection().prepareStatement(
                    "select lintask.targetLanguage, lintask.sourceLanguage, client_information.company_code, lintask.taskName, project.number, sourcedoc.id_project,lintask.personname, lintask.duedatedate from lintask, targetdoc, sourcedoc,project, client_information " +
                    "where " +
                    "personname=? " +
                    "and duedatedate>=sysdate() " +
                    "and lintask.id_targetDoc=targetdoc.id_targetDoc " +
                    "and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "and project.id_project = sourcedoc.id_project " +
                    "and project.id_client= client_information.id_client " +
                    "union " +
                    "select dtptask.targetLanguage, dtptask.sourceLanguage, client_information.company_code, dtptask.taskName, project.number, " +
                    "sourcedoc.id_project,dtptask.personname, dtptask.duedatedate from dtptask, targetdoc, sourcedoc,project , client_information " +
                    "where " +
                    "personname=? " +
                    "and duedatedate>=sysdate() " +
                    "and dtptask.id_targetDoc=targetdoc.id_targetDoc " +
                    "and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "and project.id_project = sourcedoc.id_project " +
                    "and project.id_client= client_information.id_client " +
                    "union " +
                    "select engtask.targetLanguage, engtask.sourceLanguage, client_information.company_code, engtask.taskName, project.number, " +
                    "sourcedoc.id_project,engtask.personname, engtask.duedatedate from engtask, targetdoc, sourcedoc,project, client_information " +
                    "where " +
                    "personname=? " +
                    " and duedatedate>=sysdate() " +
                    "and engtask.id_targetDoc=targetdoc.id_targetDoc " +
                    "and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "and project.id_project = sourcedoc.id_project " +
                    "and project.id_client= client_information.id_client " +
                    "union " +
                    "select othtask.targetLanguage, othtask.sourceLanguage, client_information.company_code, othtask.taskName, project.number, " +
                    "sourcedoc.id_project,othtask.personname, othtask.duedatedate from othtask, targetdoc, sourcedoc,project , client_information " +
                    "where " +
                    " personname=? " +
                    "and duedatedate>=sysdate() " +
                    "and othtask.id_targetDoc=targetdoc.id_targetDoc " +
                    "and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "and project.id_project = sourcedoc.id_project " +
                    "and project.id_client= client_information.id_client ");


            st.setString(1, "" + resourceId);
            st.setString(2, "" + resourceId);
            st.setString(3, "" + resourceId);
            st.setString(4, "" + resourceId);

            ResultSet rs = st.executeQuery();
            result += "<table width='75%' class='tableHighlight'><tr><td width='20%'><b>Current Projects</td><td width='30%'><b>Task</td><td width='35%'><b>Language</td><td width='15%'><b>Due Date</td></tr>";
            while (rs.next()) {
                result += "<tr><td>" +
                        rs.getString("number") + rs.getString("company_code") +
                        "</td><td>" + rs.getString("taskName") + "</td><td>" + rs.getString("sourceLanguage") + " to " + rs.getString("targetLanguage") + "</td><td>" + sdf.format(rs.getDate("duedatedate")) + "</td></tr>";
            }
            result += "</table>";

            return result;

        } catch (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
            if (session != null) {
                try {

                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }

    }

    public static String copyAllIndustries() {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        String result = "";
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st1 = session.connection().prepareStatement(
                    "select distinct id_resource from resource_specificindustry");

            PreparedStatement st2 = session.connection().prepareStatement(
                    "select distinct industry_id from resource_specificindustry rsi, specificindustry si " +
                    " where " +
                    " rsi.specificindustry_id=si.specificindustry_id " +
                    " and rsi.id_resource=? " +
                    " and si.industry_id " +
                    " not in (select ri.industry_id from resource_industry ri where ri.id_resource=?)");

            PreparedStatement st3 = session.connection().prepareStatement(
                    "insert  into resource_industry (industry_id, id_resource) " +
                    " values(?, ?) ");




            ResultSet rs = st1.executeQuery();

            while (rs.next()) {

                int resourceId = rs.getInt("id_resource");
                result += "Processing resourceId=" + resourceId + "<br>";
                st2.setInt(1, resourceId);
                st2.setInt(2, resourceId);
                ResultSet rs2 = st2.executeQuery();
                while (rs2.next()) {
                    int industryId = rs2.getInt("industry_id");
                    result += " Inserting industry_id=" + industryId + "<br>";
                    try {
                        st3.setInt(1, industryId);
                        st3.setInt(2, resourceId);
                        st3.executeUpdate();
                    } catch (Exception e) {
                        result += " Skipping industry_id=" + industryId + ", it already exists.<br>";

                    }
                }
                result += "--------------------------------<br><br>";
            }

            st1.close();
            st2.close();
            st3.close();
            tx.commit();
            return result;

        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (HibernateException he) {
                System.err.println("Hibernate Exception" + e.getMessage());
                throw new RuntimeException(e);
            }
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
            if (session != null) {
                try {

                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }

    }
    
        public static HashMap<String, Integer> getVendorProjCounts() {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        HashMap<String, Integer> result = new HashMap();

        try {
            String linq =" select count(distinct id_project) as id_projectc ,personname " +
                    " from lintask, targetdoc, sourcedoc " +
                    "             where " +
                    "             lintask.id_targetDoc=targetdoc.id_targetDoc " +
                    "             and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " group by personname ";
                 
                   String dtpq = " select count(distinct id_project) as id_projectc ,personname " +
                    " from dtptask, targetdoc, sourcedoc " +
                    "             where " +
                    "             dtptask.id_targetDoc=targetdoc.id_targetDoc " +
                    "             and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " group by personname ";
                 
                    String engq =" select count(distinct id_project) as id_projectc ,personname " +
                    " from engtask, targetdoc, sourcedoc " +
                    "             where " +
                    "             engtask.id_targetDoc=targetdoc.id_targetDoc " +
                    "             and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " group by personname " ;


            PreparedStatement st = session.connection().prepareStatement(linq);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                try {
                    if(!rs.getString(2).isEmpty()&&!rs.getString(2).equalsIgnoreCase("null"))
                    result.put(rs.getString(2),rs.getInt(1));
                } catch (Exception e) {}
            }st.close();
            
            st = session.connection().prepareStatement(dtpq);
            rs = st.executeQuery();
            while (rs.next()) {
                try {
                    if(!rs.getString(2).isEmpty()&&!rs.getString(2).equalsIgnoreCase("null"))
                    result.put(rs.getString("personname"),result.getOrDefault(rs.getString(2), 0)+rs.getInt(1));
                } catch (Exception e) {}
            }st.close();
            
            st = session.connection().prepareStatement(engq);
            rs = st.executeQuery();
            while (rs.next()) {
                try {
                    if(!rs.getString(2).isEmpty()&&!rs.getString(2).equalsIgnoreCase("null"))
                    result.put(rs.getString(2),result.getOrDefault(rs.getString(2), 0)+rs.getInt(1));
                } catch (Exception e) {}
            }st.close();


            return result;

        } catch (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
            if (session != null) {
                try {

                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }

    }
    
    public static Vector getVendorCounts(int resourceId) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Vector result = new Vector();

        try {


            PreparedStatement st = session.connection().prepareStatement(
                    " select count(distinct id_project)  from " +
                    " ( " +
                    " select * from ( " +
                    " select id_project " +
                    " from lintask, targetdoc, sourcedoc " +
                    "             where " +
                    "             personname=? " +
                    "             and lintask.id_targetDoc=targetdoc.id_targetDoc " +
                    "             and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " ) AS T1 " +
                    " union " +
                    " select * from ( " +
                    " select id_project " +
                    " from dtptask, targetdoc, sourcedoc " +
                    "             where " +
                    "             personname=? " +
                    "             and dtptask.id_targetDoc=targetdoc.id_targetDoc " +
                    "             and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " ) AS T2 " +
                    " union " +
                    " select * from ( " +
                    " select id_project " +
                    " from engtask, targetdoc, sourcedoc " +
                    "             where " +
                    "             personname=? " +
                    "             and engtask.id_targetDoc=targetdoc.id_targetDoc " +
                    "             and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " ) AS T1 " +
                    " ) AS MAINTABLE ");


            st.setString(1, "" + resourceId);
            st.setString(2, "" + resourceId);
            st.setString(3, "" + resourceId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                try {
                    result.add(0, rs.getString(1));
                } catch (NullPointerException e) {
                    result.add(0, "0");
                }
                
            }

            st.close();
            st = session.connection().prepareStatement(
                    "select sum(wordTotal) " +
                    " from lintask, targetdoc, sourcedoc " +
                    " where " +
                    " personname=? " +
                    " and lintask.id_targetDoc=targetdoc.id_targetDoc " +
                    " and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc ");
            st.setString(1, "" + resourceId);
            rs = st.executeQuery();
            if (rs.next()) {
                 try {
                    result.add(1, rs.getString(1));
                } catch (NullPointerException e) {
                    result.add(1, "0");
                }
//                result.add(1, rs.getString(1));
            }

            return result;

        } catch (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
            if (session != null) {
                try {

                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }

    }

    public static Vector getVendorCounts(int resourceId, int clientId) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Vector result = new Vector();

        try {


            PreparedStatement st = session.connection().prepareStatement(
                    " select count(distinct id_project)  from " +
                    " ( " +
                    " select * from ( " +
                    " select project.id_project as id_project" +
                    " from lintask, targetdoc, sourcedoc, project " +
                    "             where " +
                    "             personname=? " +
                    "             and lintask.id_targetDoc=targetdoc.id_targetDoc " +
                    "             and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                             " and project.id_project = sourcedoc.id_project " +
                             " and project.id_client = ? " +
                    " ) AS T1 " +
                    " union " +
                    " select * from ( " +
                    " select project.id_project as id_project " +
                    " from dtptask, targetdoc, sourcedoc, project " +
                    "             where " +
                    "             personname=? " +
                    "             and dtptask.id_targetDoc=targetdoc.id_targetDoc " +
                    "             and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                            " and project.id_project = sourcedoc.id_project " +
                             " and project.id_client = ? " +
                    " ) AS T2 " +
                    " union " +
                    " select * from ( " +
                    " select project.id_project as id_project " +
                    " from engtask, targetdoc, sourcedoc, project " +
                    "             where " +
                    "             personname=? " +
                    "             and engtask.id_targetDoc=targetdoc.id_targetDoc " +
                    "             and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                            " and project.id_project = sourcedoc.id_project " +
                             " and project.id_client = ? " +
                    " ) AS T1 " +
                    " ) AS MAINTABLE ");


            st.setString(1, "" + resourceId);
            st.setString(2, "" + clientId);
            st.setString(3, "" + resourceId);
            st.setString(4, "" + clientId);
            st.setString(5, "" + resourceId);
            st.setString(6, "" + clientId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                try {
                    result.add(0, rs.getString(1));
                } catch (NullPointerException e) {
                    result.add(0, "0");
                }
                
            }

            st.close();
            

            return result;

        } catch (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
            if (session != null) {
                try {

                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }

    }
    
    public static Vector getVendorCountsDTP(int resourceId) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Vector result = new Vector();

        try {


            PreparedStatement st = session.connection().prepareStatement(
                  
                    " select count(distinct id_project) " +
                    " from dtptask, targetdoc, sourcedoc " +
                    "             where " +
                    "             personname=? " +
                    "             and dtptask.id_targetDoc=targetdoc.id_targetDoc " +
                    "             and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc ");
                   


            st.setString(1, "" + resourceId);
           
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                try {
                    result.add(0, rs.getString(1));
                } catch (NullPointerException e) {
                    result.add(0, "0");
                }
                
            }

            st.close();
            st = session.connection().prepareStatement(
                    "select sum(wordTotal) " +
                    " from lintask, targetdoc, sourcedoc " +
                    " where " +
                    " personname=? " +
                    " and lintask.id_targetDoc=targetdoc.id_targetDoc " +
                    " and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc ");
            st.setString(1, "" + resourceId);
            rs = st.executeQuery();
            if (rs.next()) {
                 try {
                    result.add(1, rs.getString(1));
                } catch (NullPointerException e) {
                    result.add(1, "0");
                }
//                result.add(1, rs.getString(1));
            }

            return result;

        } catch (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
            if (session != null) {
                try {

                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }

    }

    public static Vector getVendorCountsDTP(int resourceId, int clientId) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Vector result = new Vector();

        try {


            PreparedStatement st = session.connection().prepareStatement(
                  
                    " select count(distinct project.id_project) as id_project " +
                    " from dtptask, targetdoc, sourcedoc, project " +
                    "             where " +
                    "             personname=? " +
                    "             and dtptask.id_targetDoc=targetdoc.id_targetDoc " +
                    "             and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                            " and project.id_project = sourcedoc.id_project " +
                             " and project.id_client = ? " +
                    "  ");


            st.setString(1, "" + resourceId);
            st.setString(2, "" + clientId);
           
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                try {
                    result.add(0, rs.getString(1));
                } catch (NullPointerException e) {
                    result.add(0, "0");
                }
                
            }

            st.close();
            

            return result;

        } catch (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
            if (session != null) {
                try {

                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }

    }
    
     public static Vector getVendorCounts(int resourceId, String source, String target) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Vector result = new Vector();

        try {


            PreparedStatement st = session.connection().prepareStatement(
                    " select count(distinct id_project)  from " +
                    " ( " +
                    " select * from ( " +
                    " select id_project " +
                    " from lintask, targetdoc, sourcedoc " +
                    "             where " +
                    "             personname=? " +
                    "             and targetdoc.language=? " +
                    "             and sourcedoc.language  = ? " +
                    "             and lintask.id_targetDoc=targetdoc.id_targetDoc " +
                    "             and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " ) AS T1 " +
                    " union " +
                    " select * from ( " +
                    " select id_project " +
                    " from dtptask, targetdoc, sourcedoc " +
                    "             where " +
                    "             personname=? " +
                    "             and targetdoc.language=? " +
                    "             and sourcedoc.language  = ? " +
                    "             and dtptask.id_targetDoc=targetdoc.id_targetDoc " +
                    "             and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " ) AS T2 " +
                    " union " +
                    " select * from ( " +
                    " select id_project " +
                    " from engtask, targetdoc, sourcedoc " +
                    "             where " +
                    "             personname=? " +
                    "             and targetdoc.language=? " +
                    "             and sourcedoc.language  = ? " +
                    "             and engtask.id_targetDoc=targetdoc.id_targetDoc " +
                    "             and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " ) AS T1 " +
                    " ) AS MAINTABLE ");


            st.setString(1, "" + resourceId);
            st.setString(2, "" + target);
            st.setString(3, "" + source);
            
            st.setString(4, "" + resourceId);
            st.setString(5, "" + target);
            st.setString(6, "" + source);
            
            st.setString(7, "" + resourceId);
            st.setString(8, "" + target);
            st.setString(9, "" + source);
            
 
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                try {
                    result.add(0, rs.getString(1));
                } catch (NullPointerException e) {
                    result.add(0, "0");
                }
                
            }

            st.close();
            st = session.connection().prepareStatement(
                    "select sum(wordTotal) " +
                    " from lintask, targetdoc, sourcedoc " +
                    " where " +
                    " personname=? " +
                    "             and targetdoc.language=? " +
                    "             and sourcedoc.language  = ? " +
                    " and lintask.id_targetDoc=targetdoc.id_targetDoc " +
                    " and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc ");
            st.setString(1, "" + resourceId);
            st.setString(2, "" + target);
            st.setString(3, "" + source);
            
            rs = st.executeQuery();
            if (rs.next()) {
                 try {
                    result.add(1, rs.getString(1));
                } catch (NullPointerException e) {
                    result.add(1, "0");
                }
//                result.add(1, rs.getString(1));
            }

            return result;

        } catch (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
            if (session != null) {
                try {

                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }

    }
    
       public static String getPaymentHistory(int resourceId) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        String result = "";

        try {

             String query =       "   select year(startDate) as startYear, sum( idt ) as amount from " +
                    "  ( " +
                    "  select * from (  " +
                    "  select  startDate, REPLACE(lintask.internalDollarTotal, \",\", \"\") as idt " +
                    "  from lintask, targetdoc, sourcedoc, project " +
                    "              where " +
                    "              personname=? " +
                    "              and lintask.id_targetDoc=targetdoc.id_targetDoc  " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "              and sourcedoc.id_project = project.id_project " +
                    "  ) AS T1  " +
                    "  union  " +
                    "  select * from (  " +
                    "  select startDate, REPLACE(dtptask.internalDollarTotal, \",\", \"\") as idt " +
                    "  from dtptask, targetdoc, sourcedoc , project " +
                    "              where  " +
                    "              personname=? " +
                    "              and dtptask.id_targetDoc=targetdoc.id_targetDoc " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " and sourcedoc.id_project = project.id_project " +
                    "  ) AS T2  " +
                    "  union  " +
                    "  select * from (  " +
                    "  select startDate, REPLACE(engtask.internalDollarTotal, \",\", \"\") as idt  " +
                    "  from engtask, targetdoc, sourcedoc , project " +
                    "              where " +
                    "              personname=? " +
                    "              and engtask.id_targetDoc=targetdoc.id_targetDoc " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " and sourcedoc.id_project = project.id_project " +
                    "  ) AS T3  " +
                    "  ) AS MAINTABLE " +
                    " group by year(startDate) " +
                    " order by year(startDate) desc ";
PreparedStatement st = session.connection().prepareStatement(query);

            st.setString(1, "" + resourceId);
            st.setString(2, "" + resourceId);
            st.setString(3, "" + resourceId);
            ResultSet rs = st.executeQuery();

            double totalInternalDollarTotal=0.00;
            int yearCount=0;
            while (rs.next()) {
                
                result += "<tr><td style=\"text-align:middle;\">" + StandardCode.getInstance().noNull(rs.getString("startYear")) + "</td>"
                        + "<td  style=\"text-align:right;\">" + StandardCode.getInstance().formatMoney(rs.getDouble("amount")) + "</td></tr>";
                totalInternalDollarTotal+= rs.getDouble("amount");
                ++yearCount;
            }

            st.close();
            result += "<tr><td colspan=4><hr></td></tr>";
          /*
           Calculate average per project use this
           */
            //  result += "<tr><td><b>OVERALL OSA:</td><td><b>" + StandardCode.getInstance().formatMoney(totalProjects) + "</td><td><b>" + StandardCode.getInstance().formatMoney(totalScore / totalProjects) + "</tr>";
          /*
           Calculate average per Annum use this
           */
            result += "<tr><td><b>Total:</td><td  style=\"text-align:right;\"><b>" + StandardCode.getInstance().formatMoney(totalInternalDollarTotal) + "</td></tr>";
           
            return result;

        } catch (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
            if (session != null) {
                try {

                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }

    }

    public static String getOSAHistory(int resourceId) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        String result = "<table><tr><td><b>Year</b></td><td><b>Projects #</b></td><td><b>Avg Annual OSA</td><td><b>Total Amount</td></tr>";

        try {


            PreparedStatement st = session.connection().prepareStatement(
                    "   select sum(score) AS score, count(id_project) AS projects, year(startDate) as startYear, sum( CAST(internalDollarTotal AS DECIMAL)) as amount from " +
                    "  ( " +
                    "  select * from (  " +
                    "  select score,project.id_project, startDate, lintask.internalDollarTotal " +
                    "  from lintask, targetdoc, sourcedoc, project " +
                    "              where " +
                    "              personname=? " +
                    "              and lintask.id_targetDoc=targetdoc.id_targetDoc  " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "              and sourcedoc.id_project = project.id_project " +
                    "  ) AS T1  " +
                    "  union  " +
                    "  select * from (  " +
                    "  select score ,project.id_project , startDate, dtptask.internalDollarTotal " +
                    "  from dtptask, targetdoc, sourcedoc , project " +
                    "              where  " +
                    "              personname=? " +
                    "              and dtptask.id_targetDoc=targetdoc.id_targetDoc " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " and sourcedoc.id_project = project.id_project " +
                    "  ) AS T2  " +
                    "  union  " +
                    "  select * from (  " +
                    "  select score ,project.id_project , startDate, engtask.internalDollarTotal  " +
                    "  from engtask, targetdoc, sourcedoc , project " +
                    "              where " +
                    "              personname=? " +
                    "              and engtask.id_targetDoc=targetdoc.id_targetDoc " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " and sourcedoc.id_project = project.id_project " +
                    "  ) AS T1  " +
                    "  ) AS MAINTABLE " +
                    " group by year(startDate) " +
                    " order by year(startDate) desc ");


            st.setString(1, "" + resourceId);
            st.setString(2, "" + resourceId);
            st.setString(3, "" + resourceId);
            ResultSet rs = st.executeQuery();

            int totalProjects = 0;
            double totalInternalDollarTotal=0.00;
            double totalScore = 0;
            double avgScore=0;
            int yearCount=0;
            while (rs.next()) {
                avgScore+=rs.getDouble("score") / rs.getInt("projects") ;
                result += "<tr><td>" + StandardCode.getInstance().noNull(rs.getString("startYear")) + "</td>"
                        + "<td>" + StandardCode.getInstance().formatMoney(1.0 * rs.getInt("projects")) + "</td>"
                        + "<td>" + StandardCode.getInstance().formatMoney(rs.getDouble("score") / rs.getInt("projects")) + "</td>"
                        + "<td>" + StandardCode.getInstance().formatMoney(rs.getDouble("amount")) + "</td></tr>";
                totalProjects += rs.getInt("projects");
                totalScore += rs.getDouble("score");
                totalInternalDollarTotal+= rs.getDouble("amount");
                ++yearCount;
            }

            st.close();
            result += "<tr><td colspan=4><hr></td></tr>";
          /*
           Calculate average per project use this
           */
            //  result += "<tr><td><b>OVERALL OSA:</td><td><b>" + StandardCode.getInstance().formatMoney(totalProjects) + "</td><td><b>" + StandardCode.getInstance().formatMoney(totalScore / totalProjects) + "</tr>";
          /*
           Calculate average per Annum use this
           */
            result += "<tr><td><b>OVERALL OSA:</td><td><b>" + StandardCode.getInstance().formatMoney(totalProjects) + "</td>"
                    + "<td><b>" + StandardCode.getInstance().formatMoney(avgScore / yearCount) + "</td>"
                    + "<td><b>" + StandardCode.getInstance().formatMoney(totalInternalDollarTotal) + "</td></tr>";
            result += "</table>";
            return result;

        } catch (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
            if (session != null) {
                try {

                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }

    }







    public static String getResourceEvaluator(String evaluator){
        Session session=ConnectionFactory.getInstance().getSession();
        boolean t=true;
        String result="<font size=4><b>Evaluator List</b></font><br><table width='75%'><tr><td><b>Evaluator</b></td><td><b>Category</b></td><td><b>ISA Score</b></td></tr>";
         result += "<tr><td colspan=6><hr></td></tr>";
          try {
                PreparedStatement st = session.connection().prepareStatement(
                        "select rc.firstName,rc.lastName,rc.companyName,specialty,score from ratescorelanguage rs inner join languagepair lp on rs.ID_LanguagePair=lp.ID_LanguagePair inner join resource rc on lp.ID_Resource=rc.ID_Resource where rs.evaluatedBy=?");
                 st.setString(1, evaluator);
                   ResultSet rs = st.executeQuery();
                    while (rs.next()) {

                        if(rs.getString("firstName")!= null&&!rs.getString("firstName").equalsIgnoreCase("")){
                        if(Double.parseDouble(rs.getString("score"))<=20){
              
                         result += "<tr><td><font color='red'>"+rs.getString("firstName")+" "+rs.getString("lastName")+"</td><td>"+rs.getString("specialty")+"</td><td>"+rs.getString("score")+"</font></td></tr>";
                    }else
                     result += "<tr><td>"+rs.getString("firstName")+" "+rs.getString("lastName")+"</td><td>"+rs.getString("specialty")+"</td><td>"+rs.getString("score")+"</td></tr>";

                    }else
                        {
                        if(Double.parseDouble(rs.getString("score"))<=20){

                         result += "<tr><td><font color='red'>"+rs.getString("companyName")+"</td><td>"+rs.getString("specialty")+"</td><td>"+rs.getString("score")+"</font></td></tr>";
                    }else
                     result += "<tr><td>"+rs.getString("companyName")+"</td><td>"+rs.getString("specialty")+"</td><td>"+rs.getString("score")+"</td></tr>";

                    }
                    }

            st.close();
            result += "</table>";





            return result;

        } catch (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
            if (session != null) {
                try {

                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }

    }



    public static String getResourceRatesHistory(int resourceId) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        String result = "<font size=4><b>LIN</b></font><br><table width='75%'><tr><td><b>Project</b></td><td><b>Date</b></td><td><b>Task</b></td><td><b>Rates</td><td><b>Units</td><td><b>Excel PM</td></tr>";
        result += "<tr><td colspan=6><hr></td></tr>";

        try {


            PreparedStatement st = session.connection().prepareStatement(
                    " select rateFee, currency, deliveryDate, taskName, pm, project.number, company_code, units, project.id_project " +
                    " from lintask, targetdoc, sourcedoc, project, client_information " +
                    "             where " +
                    "             personname=? " +
                    "            and lintask.id_targetDoc=targetdoc.id_targetDoc " +
                    "           and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "            and sourcedoc.id_project = project.id_project " +
                    "            and project.id_client = client_information.id_client " +
                    " order by deliveryDate desc  " +
                    " LIMIT 0,10 ");


            st.setString(1, "" + resourceId);
            //st.setString(2, ""+resourceId);
            //st.setString(3, ""+resourceId);
            ResultSet rs = st.executeQuery();


            while (rs.next()) {
              try{
                result += "<tr><td>" +
                        "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openSingleProjectWindow('" + rs.getString("number") + rs.getString("company_code") + "','" + rs.getString("id_project") + "')\">" + rs.getString("number") + rs.getString("company_code") + "</a>" +
                        "</td><td>" + sdf.format(rs.getDate("deliveryDate")) + "</td><td>" + rs.getString("taskName") + "</td>";

result+="<td>" + rs.getDouble("rateFee") + " " + StandardCode.getInstance().noNull(rs.getString("currency")) + "</td>";




      result+=" <td>" + rs.getString("units") + "</td><td>" + rs.getString("pm") + "</td></tr>";
      }catch(Exception e){}
            }

            st.close();
            result += "</table>";

            result += "<br><br><font size=4><b>DTP</b></font><br><table width='75%'><tr><td><b>Project</b></td><td><b>Date</b></td><td><b>Task</b></td><td><b>Rates</td><td><b>Units</td><td><b>Excel PM</td></tr>";
            result += "<tr><td colspan=6><hr></td></tr>";
            st = session.connection().prepareStatement(
                    " select internalRate, currency, deliveryDate, taskName, pm, project.number, company_code, units, project.id_project " +
                    " from dtptask, targetdoc, sourcedoc, project, client_information " +
                    "             where " +
                    "             personname=? " +
                    "            and dtptask.id_targetDoc=targetdoc.id_targetDoc " +
                    "           and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "            and sourcedoc.id_project = project.id_project " +
                    "            and project.id_client = client_information.id_client " +
                    " order by deliveryDate desc  " +
                    " LIMIT 0,10 ");


            st.setString(1, "" + resourceId);
            //st.setString(2, ""+resourceId);
            //st.setString(3, ""+resourceId);
            rs = st.executeQuery();


            while (rs.next()) {
                result += "<tr><td>" +
                        "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openSingleProjectWindow('" + rs.getString("number") + rs.getString("company_code") + "','" + rs.getString("id_project") + "')\">" + rs.getString("number") + rs.getString("company_code") + "</a>" +
                        "</td><td>" + sdf.format(rs.getDate("deliveryDate")) + "</td><td>" + rs.getString("taskName") + "</td><td>" + rs.getDouble("internalRate") + " " + rs.getString("currency") + "</td><td>" + rs.getString("units") + "</td><td>" + rs.getString("pm") + "</td></tr>";

            }

            st.close();
            result += "</table>";



            result += "<br><br><font size=4><b>ENG/OTH</b></font><br><table width='75%'><tr><td><b>Project</b></td><td><b>Date</b></td><td><b>Task</b></td><td><b>Rates</td><td><b>Units</td><td><b>Excel PM</td></tr>";
            result += "<tr><td colspan=6><hr></td></tr>";
            st = session.connection().prepareStatement(
                    " select rate, currency, deliveryDate, taskName, pm, project.number, company_code, units, project.id_project " +
                    " from engtask, targetdoc, sourcedoc, project, client_information " +
                    "             where " +
                    "             personname=? " +
                    "            and engtask.id_targetDoc=targetdoc.id_targetDoc " +
                    "           and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "            and sourcedoc.id_project = project.id_project " +
                    "            and project.id_client = client_information.id_client " +
                    " order by deliveryDate desc  " +
                    " LIMIT 0,10 ");


            st.setString(1, "" + resourceId);
            //st.setString(2, ""+resourceId);
            //st.setString(3, ""+resourceId);
            rs = st.executeQuery();


            while (rs.next()) {
                result += "<tr><td>" +
                        "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openSingleProjectWindow('" + rs.getString("number") + rs.getString("company_code") + "','" + rs.getString("id_project") + "')\">" + rs.getString("number") + rs.getString("company_code") + "</a>" +
                        "</td><td>" + sdf.format(rs.getDate("deliveryDate")) + "</td><td>" + rs.getString("taskName") + "</td><td>" + rs.getDouble("rate") + " " + rs.getString("currency") + "</td><td>" + rs.getString("units") + "</td><td>" + rs.getString("pm") + "</td></tr>";

            }

            st.close();
            result += "</table>";





            return result;

        } catch (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
            if (session != null) {
                try {

                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }

    }

    public static String getClientResources(String clientName) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        String result = "";

        try {


            PreparedStatement st = session.connection().prepareStatement(
                    "   select personname " +
                    "   from lintask, targetdoc, sourcedoc, project,client_information " +
                    "               where " +
                    "               lintask.id_targetDoc=targetdoc.id_targetDoc " +
                    "               and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc  " +
                    "               and sourcedoc.id_project = project.id_project " +
                    " 	      and client_information.id_client= project.id_client " +
                    " 	      and client_information.company_name like '%" + clientName + "%' " +
                    "   union " +
                    "   select personname  " +
                    "   from dtptask, targetdoc, sourcedoc , project , client_information " +
                    "               where   " +
                    "               dtptask.id_targetDoc=targetdoc.id_targetDoc " +
                    "               and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc  " +
                    " 	      and client_information.id_client= project.id_client " +
                    " 	      and client_information.company_name like '%" + clientName + "%' " +
                    "  and sourcedoc.id_project = project.id_project  " +
                    "   union " +
                    "   select personname " +
                    "   from engtask, targetdoc, sourcedoc , project ,client_information " +
                    "               where  " +
                    "               engtask.id_targetDoc=targetdoc.id_targetDoc " +
                    "               and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc 	       " +
                    " 	      and client_information.id_client= project.id_client " +
                    " 	      and client_information.company_name like '%" + clientName + "%' " +
                    "  and sourcedoc.id_project = project.id_project  ");

            ResultSet rs = st.executeQuery();

            int totalProjects = 0;
            double totalScore = 0;
            while (rs.next()) {

                result += "_" + rs.getString("personname") + "_";
            }

            st.close();

            return result;

        } catch (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
            if (session != null) {
                try {

                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }

    }

    public static String getResourceRatesLinHistory(int resourceId) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        String result = "<font size=4><b>LAST 10 LIN RATES</b></font><br><table width='75%'><tr><td><b>Project</b></td><td><b>Date</b></td><td><b>Task</b></td><td><b>Rate</td><td><b>Unit</td><td><b>Excel PM</td></tr>";
        result += "<tr><td colspan=6><hr></td></tr>";

        try {


            PreparedStatement st = session.connection().prepareStatement(
                    " select internalrate as rate, internalCurrency as currency, deliveryDate, taskName, pm, project.number, company_code, units, project.id_project " +
                    " from lintask, targetdoc, sourcedoc, project, client_information " +
                    "             where " +
                    "             personname=? " +
                    "            and lintask.id_targetDoc=targetdoc.id_targetDoc " +
                    "           and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "            and sourcedoc.id_project = project.id_project " +
                    "            and project.id_client = client_information.id_client " +
                    " order by deliveryDate desc  " +
                    " LIMIT 0,10 ");


            st.setString(1, "" + resourceId);
            //st.setString(2, ""+resourceId);
            //st.setString(3, ""+resourceId);
            ResultSet rs = st.executeQuery();


            while (rs.next()) {
                result += "<tr><td>" +
                        "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openSingleProjectWindow('" + rs.getString("number") + rs.getString("company_code") + "','" + rs.getString("id_project") + "')\">" + rs.getString("number") + rs.getString("company_code") + "</a>" +
                        "</td>";
                try{ 
                result += "<td>" + sdf.format(rs.getDate("deliveryDate")) + "</td>";
                }catch(Exception e){
                result +="<td></td>";
                }
                //System.out.println(rs.getString("number")+"---");
                //System.out.println(rs.getString("rate")+"---");
                    result += "<td>" + rs.getString("taskName") + "</td><td>" + rs.getString("rate") + " " + rs.getString("currency") + "</td><td>" + rs.getString("units") + "</td><td>" + rs.getString("pm") + "</td></tr>";

            }

            st.close();
            result += "</table>";




            return result;

        } catch (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
            if (session != null) {
                try {

                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }

    }

    public static String getResourceRatesDtpHistory(int resourceId) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        String result = "<font size=4><b>LAST 10 DTP RATES</b></font><br><table width='75%'><tr><td><b>Project</b></td><td><b>Date</b></td><td><b>Task</b></td><td><b>Rates</td><td><b>Units</td><td><b>Excel PM</td></tr>";
        result += "<tr><td colspan=6><hr></td></tr>";

        try {


            PreparedStatement st = session.connection().prepareStatement(
                    " select internalRate as rate, internalcurrency as currency, deliveryDate, taskName, pm, project.number, company_code, units, project.id_project " +
                    " from dtptask, targetdoc, sourcedoc, project, client_information " +
                    "             where " +
                    "             personname=? " +
                    "            and dtptask.id_targetDoc=targetdoc.id_targetDoc " +
                    "           and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "            and sourcedoc.id_project = project.id_project " +
                    "            and project.id_client = client_information.id_client " +
                    " order by deliveryDate desc  " +
                    " LIMIT 0,10 ");


            st.setString(1, "" + resourceId);
            //st.setString(2, ""+resourceId);
            //st.setString(3, ""+resourceId);
            ResultSet rs = st.executeQuery();


            while (rs.next()) {
                result += "<tr><td>" +
                        "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openSingleProjectWindow('" + rs.getString("number") + rs.getString("company_code") + "','" + rs.getString("id_project") + "')\">" + rs.getString("number") + rs.getString("company_code") + "</a>" +
                        "</td><td>" + sdf.format(rs.getDate("deliveryDate")) + "</td><td>" + rs.getString("taskName") + "</td><td>" + rs.getDouble("rate") + " " + rs.getString("currency") + "</td><td>" + rs.getString("units") + "</td><td>" + rs.getString("pm") + "</td></tr>";

            }

            st.close();
            result += "</table>";



            result += "<br><br><font size=4><b>LAST 10 ENG/OTH RATES</b></font><br><table width='75%'><tr><td><b>Project</b></td><td><b>Date</b></td><td><b>Task</b></td><td><b>Rates</td><td><b>Units</td><td><b>Excel PM</td></tr>";
            result += "<tr><td colspan=6><hr></td></tr>";
            st = session.connection().prepareStatement(
                    " select internalRate as rate, internalcurrency as currency, deliveryDate, taskName, pm, project.number, company_code, units, project.id_project " +
                    " from engtask, targetdoc, sourcedoc, project, client_information " +
                    "             where " +
                    "             personname=? " +
                    "            and engtask.id_targetDoc=targetdoc.id_targetDoc " +
                    "           and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "            and sourcedoc.id_project = project.id_project " +
                    "            and project.id_client = client_information.id_client " +
                    " order by deliveryDate desc  " +
                    " LIMIT 0,10 ");


            st.setString(1, "" + resourceId);
            //st.setString(2, ""+resourceId);
            //st.setString(3, ""+resourceId);
            rs = st.executeQuery();


            while (rs.next()) {
                result += "<tr><td>" +
                        "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openSingleProjectWindow('" + rs.getString("number") + rs.getString("company_code") + "','" + rs.getString("id_project") + "')\">" + rs.getString("number") + rs.getString("company_code") + "</a>" +
                        "</td><td>" + sdf.format(rs.getDate("deliveryDate")) + "</td><td>" + rs.getString("taskName") + "</td><td>" + rs.getDouble("rate") + " " + rs.getString("currency") + "</td><td>" + rs.getString("units") + "</td><td>" + rs.getString("pm") + "</td></tr>";

            }

            st.close();
            result += "</table>";





            return result;

        } catch (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
            if (session != null) {
                try {

                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }

    }

    public static ArrayList getLinEvaluatorsList(String sourceLanguage, String targetLanguage) {

        Session session = ConnectionFactory.getInstance().getSession();


        ArrayList result = new ArrayList();

        try {


            PreparedStatement st = session.connection().prepareStatement(
                    "select lastName, firstName,companyName from resource where id_resource in( " +
                    " select distinct id_resource from languagepair " +
                    " where id_languagepair in( " +
                    "   SELECT distinct id_languagepair " +
                    "   FROM ratescorelanguage " +
                    "   where source=? and target=? " +
                    " )" +
                    " ) order by lastName, firstName");
            st.setString(1, sourceLanguage);
            st.setString(2, targetLanguage);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                if (rs.getString("lastName") != null && !"".equals(rs.getString("lastName"))) {
                    result.add(rs.getString("firstName") + " " + rs.getString("lastName"));
                }else if(rs.getString("companyName") != null){
                    result.add(rs.getString("companyName"));
                }
            }
            st.close();
            return result;
        } catch (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
            if (session != null) {
                try {

                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }


    }
    
     public static JSONArray getAllOSAHistoryJSON(int resourceId) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        JSONArray result = new JSONArray();

        try {


            PreparedStatement st = session.connection().prepareStatement(
                    " select ID_LinTask as id, score, scoreDescription, targetLanguage, sourceLanguage, deliveryDate,project.startDate, taskName, pm, project.number, company_code, units, project.id_project, year(project.startDate) as dYear, 'LIN' as taskType,receivedDateDate " +
                    " from lintask, targetdoc, sourcedoc, project, client_information " +
                    "             where " +
                    "             personname=? " +
                    "            and lintask.id_targetDoc=targetdoc.id_targetDoc " +
                    "           and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "            and sourcedoc.id_project = project.id_project " +
                    "            and project.id_client = client_information.id_client " +
                    " union " +
                    " select ID_DtpTask as id, score, scoreDescription, targetLanguage, sourceLanguage, deliveryDate,project.startDate, taskName, pm, project.number, company_code, units, project.id_project, year(project.startDate) as dYear, 'DTP' as taskType,receivedDateDate   " +
                    " from dtptask, targetdoc, sourcedoc, project, client_information " +
                    "             where " +
                    "             personname=? " +
                    "            and dtptask.id_targetDoc=targetdoc.id_targetDoc " +
                    "           and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "            and sourcedoc.id_project = project.id_project " +
                    "            and project.id_client = client_information.id_client " +
                    " union " +
                    " select ID_EngTask as id, score, scoreDescription, targetLanguage, sourceLanguage, deliveryDate,project.startDate, taskName, pm, project.number, company_code, units, project.id_project, year(project.startDate) as dYear, 'ENG' as taskType,receivedDateDate   " +
                    " from engtask, targetdoc, sourcedoc, project, client_information " +
                    "             where " +
                    "             personname=? " +
                    "            and engtask.id_targetDoc=targetdoc.id_targetDoc " +
                    "           and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "            and sourcedoc.id_project = project.id_project " +
                    "            and project.id_client = client_information.id_client " +
                    " order by  number");


            st.setString(1, "" + resourceId);
            st.setString(2, "" + resourceId);
            st.setString(3, "" + resourceId);
            ResultSet rs = st.executeQuery();
            int dYear = 0;
            int osaScore;

            Map<String, List<OSAModel>> map = new HashMap<String, List<OSAModel>>();

            while (rs.next()) {
                if (rs.getString("startDate") != null) {
                    if(rs.getInt("score")!=35){

                    if (rs.getInt("dYear") != dYear) {

                        dYear = rs.getInt("dYear");
                       
                    }
                    List<OSAModel> aModels = null;
                    if (map.containsKey(dYear+"")) {
                        aModels = map.get(dYear+"");
                        OSAModel oSAModel = new OSAModel();
                        if(rs.getDate("deliveryDate")!=null) oSAModel.setDeliveryDate(rs.getDate("deliveryDate"));
                        oSAModel.setTaskName(rs.getString("taskName"));
                        oSAModel.setScore(rs.getInt("score"));
                        oSAModel.setPm(rs.getString("pm"));
                        oSAModel.setScoreDescription(rs.getString("scoreDescription"));
                        oSAModel.setNumber(rs.getString("number"));
                        oSAModel.setCompany_code(rs.getString("company_code"));
                        oSAModel.setId_project(rs.getString("id_project"));
                        oSAModel.setTaskType(rs.getString("taskType"));
                        oSAModel.setId(rs.getString("id"));
                        oSAModel.setSrc(rs.getString("sourceLanguage"));
                        oSAModel.setTgt(rs.getString("targetLanguage"));
                        aModels.add(oSAModel);
                        map.put(dYear+"", aModels);
                    } else {
                        aModels = new ArrayList<OSAModel>();
                        OSAModel oSAModel = new OSAModel();
                        if(rs.getDate("deliveryDate")!=null) oSAModel.setDeliveryDate(rs.getDate("deliveryDate"));
                        oSAModel.setTaskName(rs.getString("taskName"));
                        oSAModel.setScore(rs.getInt("score"));
                        oSAModel.setPm(rs.getString("pm"));
                        oSAModel.setScoreDescription(rs.getString("scoreDescription"));
                        oSAModel.setNumber(rs.getString("number"));
                        oSAModel.setCompany_code(rs.getString("company_code"));
                        oSAModel.setId_project(rs.getString("id_project"));
                        oSAModel.setTaskType(rs.getString("taskType"));
                        oSAModel.setId(rs.getString("id"));
                        oSAModel.setSrc(rs.getString("sourceLanguage"));
                        oSAModel.setTgt(rs.getString("targetLanguage"));
                        aModels.add(oSAModel);
                       
                        map.put(dYear+"", aModels);
                    }


                    }
                }
            }

                        TreeSet<String> treeSet = new TreeSet<String>(Collections.reverseOrder());
                        treeSet.addAll(map.keySet());
                        int osacount = 0;
            for(String year1 : treeSet){
               List<OSAModel> list = map.get(year1);

                Collections.sort(list);
                
                for(OSAModel osa:list)
                {
                   osaScore=osa.getScore();
                   if(osaScore==0)osaScore=35;

                        String delDate = "";
                          try {
                              
                              delDate = sdf.format(osa.getDeliveryDate());}catch(Exception e){}
                    if(osaScore!=35 && osacount<10) {  
                        osacount++;
                    JSONObject jo = new JSONObject();
                    jo.put("delDate", delDate);
                    jo.put("taskName", osa.getTaskName());
                    jo.put("osaScore", osaScore);
                    jo.put("scoreDesc", noNull(osa.getScoreDescription()));
                    jo.put("projectNumb", osa.getNumber()  + osa.getCompany_code());
                    jo.put("src", osa.getSrc());
                    jo.put("tgt", osa.getTgt());
                    jo.put("id", osa.getId());
                    
                    result.put(jo);
                    }
                }
                }
        st.close();
        return result;

    }
    catch



        (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
        throw new RuntimeException(e);
    } /*
     * Regardless of whether the above processing resulted in an Exception
     * or proceeded normally, we want to close the Hibernate session.  When
     * closing the session, we must allow for the possibility of a Hibernate
     * Exception.
     *
     */ finally

    {
        if (session != null) {
            try {

                session.close();
            } catch (HibernateException e) {
                System.err.println("Hibernate Exception" + e.getMessage());
                throw new RuntimeException(e);
            }

        }
    }
}


    public static String getAllOSAHistory(int resourceId, boolean isAgency) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        String result = "<table width='75%'><tr><td><b>Project</b></td><td><b>Date</b></td><td><b>Task</b></td><td></td><td><b>OSA</td><td><b>PM</td><td><b>Description</td></tr>";
        result += "<tr><td colspan=7><hr></td></tr>";

        try {


            PreparedStatement st = session.connection().prepareStatement(
                    " select ID_LinTask as id, score, scoreDescription, deliveryDate,project.startDate, taskName, pm, project.number, company_code, units, project.id_project, year(project.startDate) as dYear, 'LIN' as taskType,receivedDateDate " +
                    " from lintask, targetdoc, sourcedoc, project, client_information " +
                    "             where " +
                    "             personname=? " +
                    "            and lintask.id_targetDoc=targetdoc.id_targetDoc " +
                    "           and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "            and sourcedoc.id_project = project.id_project " +
                    "            and project.id_client = client_information.id_client " +
                    " union " +
                    " select ID_DtpTask as id, score, scoreDescription, deliveryDate,project.startDate, taskName, pm, project.number, company_code, units, project.id_project, year(project.startDate) as dYear, 'DTP' as taskType,receivedDateDate   " +
                    " from dtptask, targetdoc, sourcedoc, project, client_information " +
                    "             where " +
                    "             personname=? " +
                    "            and dtptask.id_targetDoc=targetdoc.id_targetDoc " +
                    "           and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "            and sourcedoc.id_project = project.id_project " +
                    "            and project.id_client = client_information.id_client " +
                    " union " +
                    " select ID_EngTask as id, score, scoreDescription, deliveryDate,project.startDate, taskName, pm, project.number, company_code, units, project.id_project, year(project.startDate) as dYear, 'ENG' as taskType,receivedDateDate   " +
                    " from engtask, targetdoc, sourcedoc, project, client_information " +
                    "             where " +
                    "             personname=? " +
                    "            and engtask.id_targetDoc=targetdoc.id_targetDoc " +
                    "           and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "            and sourcedoc.id_project = project.id_project " +
                    "            and project.id_client = client_information.id_client " +
                    " order by  deliveryDate desc ");


            st.setString(1, "" + resourceId);
            st.setString(2, "" + resourceId);
            st.setString(3, "" + resourceId);
            //st.setString(2, ""+resourceId);
            //st.setString(3, ""+resourceId);
            ResultSet rs = st.executeQuery();
            int dYear = 0;
            int totalYearlyOsa = 0;
            int totalYearlyProjects = 0;

            int totalLinOsa = 0;
            int totalDtpEngOsa = 0;
            int totalDtpEngProjects = 0;
            int totalLinProjects = 0;
            int osaScore;

            Map<String, List<OSAModel>> map = new HashMap<String, List<OSAModel>>();

            while (rs.next()) {
                if (rs.getString("startDate") != null) {
                    if(rs.getInt("score")!=35){

                    if (rs.getInt("dYear") != dYear) {

                        if (dYear != 0) {

                            


                        }
                        
                        dYear = rs.getInt("dYear");
                        //System.out.println("dYeardYeardYeardYeardYeardYear"+dYear);
                        totalYearlyOsa = 0;
                        totalYearlyProjects = 0;
                    }

                    //============================================================================
                    List<OSAModel> aModels = null;
                    if (map.containsKey(dYear+"")) {
                        aModels = map.get(dYear+"");
                        OSAModel oSAModel = new OSAModel();
                        if(rs.getDate("deliveryDate")!=null) oSAModel.setDeliveryDate(rs.getDate("deliveryDate"));
                        oSAModel.setTaskName(rs.getString("taskName"));
                        oSAModel.setScore(rs.getInt("score"));
                        oSAModel.setPm(rs.getString("pm"));
                        oSAModel.setScoreDescription(rs.getString("scoreDescription"));
                        oSAModel.setNumber(rs.getString("number"));
                        oSAModel.setCompany_code(rs.getString("company_code"));
                        oSAModel.setId_project(rs.getString("id_project"));
                        oSAModel.setTaskType(rs.getString("taskType"));
                        oSAModel.setId(rs.getString("id"));


                        aModels.add(oSAModel);
                        map.put(dYear+"", aModels);
                    } else {
                        aModels = new ArrayList<OSAModel>();
                        OSAModel oSAModel = new OSAModel();
                        if(rs.getDate("deliveryDate")!=null) oSAModel.setDeliveryDate(rs.getDate("deliveryDate"));
                        oSAModel.setTaskName(rs.getString("taskName"));
                        oSAModel.setScore(rs.getInt("score"));
                        oSAModel.setPm(rs.getString("pm"));
                        oSAModel.setScoreDescription(rs.getString("scoreDescription"));
                        oSAModel.setNumber(rs.getString("number"));
                        oSAModel.setCompany_code(rs.getString("company_code"));
                        oSAModel.setId_project(rs.getString("id_project"));
                        oSAModel.setTaskType(rs.getString("taskType"));
                        oSAModel.setId(rs.getString("id"));
                        aModels.add(oSAModel);
                       
                        map.put(dYear+"", aModels);
                    }



                    //===================================================================================



                    /*result += "<tr><td>" +
                    "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openSingleProjectWindow('" + rs.getString("number") + rs.getString("company_code") + "','" + rs.getString("id_project") + "')\">" + rs.getString("number") + rs.getString("company_code") + "</a>" +
                    "</td><td>" + sdf.format(rs.getDate("deliveryDate")) + "</td><td>" + rs.getString("taskName") + "</td><td>" + rs.getInt("score") + "</td><td>" + rs.getString("pm") + "</td><td>" + noNull(rs.getString("scoreDescription")) + "</td></tr>";

                    if ("LIN".equals(rs.getString("taskType"))) {
                    totalDtpEngOsa += rs.getInt("score");
                    totalDtpEngProjects++;
                    } else {
                    totalLinOsa += rs.getInt("score");
                    totalLinProjects++;
                    }

                    totalYearlyOsa += rs.getInt("score");
                    totalYearlyProjects++;*/
                    }
                }
            }

            
                     //   Set<String> s = map.keySet();
                        TreeSet<String> treeSet = new TreeSet<String>(Collections.reverseOrder());
                        treeSet.addAll(map.keySet());
            for(String year1 : treeSet){
               List<OSAModel> list = map.get(year1);
                //System.out.println("----"+year1);
                if(year1.equalsIgnoreCase("0")){
                result += "<tr><td colspan='10'><b><font size=4>Project Not delivered </td></tr>";
                }else{
                result += "<tr><td><b><font size=4>" +year1 + "</td></tr>";
                }
                
                Collections.sort(list);
                String imagePath="";
                String desc="";
                int totalYearlyOsa12=0;
                int count=0;
                for(OSAModel osa:list)
                {
                   osaScore=osa.getScore();
                   if(osaScore==0)osaScore=35;
                    //System.out.println("idddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd"+osa.getId()+osa.getTaskType());
                  // if(osaScore==-3)osaScore=20;
                  // else if(osaScore==-2)osaScore=25;
                  // else if(osaScore==-1)osaScore=30;
                  // else if(osaScore==0)osaScore=35;
                  // else if(osaScore==1)osaScore=40;
                 //  else if(osaScore==2)osaScore=45;
                  // else if(osaScore==3)osaScore=50;
                   if(osaScore>35){
                       imagePath="<img src=\"../images/bluedot.jpg\" width=\"8\" height=\"8\" />";
                       desc="<a href=\"javascript:scoreDescription("+osa.getId()+")\"><img src=\"../images/blueCallout.jpg\" width=\"8\" height=\"8\" /></a>";
                   }
                   else if(osaScore<35){
                       imagePath="<img src=\"../images/reddot.gif\" width=\"8\" height=\"8\" />";
                       desc="<a href=\"javascript:scoreDescription("+osa.getId()+")\"><img src=\"../images/redCallout.jpg\" width=\"8\" height=\"8\" /></a>";
                   }
                   else {imagePath="";desc="";}

                        String delDate = "";
                          try {
                              
                              delDate = sdf.format(osa.getDeliveryDate());}catch(Exception e){}

                    //System.out.println("prrrrrrrrrrrrrrrrrrrrrr" +osa.getNumber() + osa.getCompany_code() + "', '" +count++ + osa.getId_project());
                  result += "<tr><td>" +
                    "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openSingleProjectWindow('" + osa.getNumber() + osa.getCompany_code() + "','" + osa.getId_project() + "')\">" + osa.getNumber()  + osa.getCompany_code() + "</a>" +
                    
                    "</td><td>" + delDate + "</td><td>" + osa.getTaskName() + "</td><td>" +imagePath+"</td><td>"+ osaScore +"</td><td>" + osa.getPm() + "</td><td>" +desc+"  "+ noNull(osa.getScoreDescription()) + "</td></tr>";

                    if ("LIN".equals(osa.getTaskType())) {

                     totalLinOsa += osaScore;
                    totalLinProjects++;
                    } else {
                        totalDtpEngOsa += osaScore;
                        totalDtpEngProjects++;
                    }
                  
                   // totalDtpEngOsa += osa.getScore();
                   // totalDtpEngProjects++;
                    
                   // totalLinOsa += osa.getScore();
                   // totalLinProjects++;
                    

                    totalYearlyOsa12 += osaScore;
                    totalYearlyProjects=list.size();
                }
               result += "<tr><td colspan=3 align='right'><b>Average OSA: </td><td align='left'><b>" + StandardCode.getInstance().formatDouble(new Double(totalYearlyOsa12 * 1.0 / totalYearlyProjects)) + "</td></tr>";
                }
        


       // result += "<tr><td colspan=3 align='right'><b>Average OSA: </td><td align='left'><b>" + StandardCode.getInstance().formatDouble(new Double(totalYearlyOsa * 1.0 / totalYearlyProjects)) + "</td></tr>";


        st.close();
        result += "</table>";

        String topTable = "<b>ONGOING SUITABILITY ASSESSMENT (OSA)</b><br>";
        topTable += "<TABLE width='50%'><TR>";
      //  topTable += "<TABLE width='50%'><TR><TD>LINGUIST:</td><td>" + StandardCode.getInstance().formatDouble(new Double(totalLinOsa * 1.0 / totalLinProjects)) + "</td>";
        String checked = "";
        if (!isAgency) {
            checked = " checked ";
        }
        if( totalDtpEngProjects!=0)
       // topTable += "<td><input type='checkbox' disabled " + checked + "> Freelance</td></tr>";
        topTable += "</tr><TR><TD>ENG/DTP:</td><td>" + StandardCode.getInstance().formatDouble(new Double(totalDtpEngOsa * 1.0 / totalDtpEngProjects)) + "</td>";
        if (isAgency) {
            checked = " checked ";
        }
       // topTable += "<td><input type='checkbox' disabled " + checked + "> TSP/Agency</td></tr>";

        topTable += "</tr></table><br>";

        return topTable + result;

    }
    catch



        (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
        throw new RuntimeException(e);
    } /*
     * Regardless of whether the above processing resulted in an Exception
     * or proceeded normally, we want to close the Hibernate session.  When
     * closing the session, we must allow for the possibility of a Hibernate
     * Exception.
     *
     */ finally

    {
        if (session != null) {
            try {

                session.close();
            } catch (HibernateException e) {
                System.err.println("Hibernate Exception" + e.getMessage());
                throw new RuntimeException(e);
            }

        }
    }
}

public static String getResourceHeader(Resource r) {
        //get resource to edit
        Session session = ConnectionFactory.getInstance().getSession();


        String result

= "<table border='0' width='100%'>";
        //3 columns
String riskRating=r.getRiskrating();

        String name

= r.getCompanyName();
        if

(!r.isAgency()) {
            name = r.getFirstName() + " " + r.getLastName();
        }

result += "<tr><td align='center'><font size=5>" + name + "</td></tr>";

        result +=

"<tr><td align='center'><font size='2'><b>VENDOR ID: </b>" + r.getResourceId();
        //result +="<td align='right' colspan=2><font size='2' ><b>Status: </td><td align='left'>"+ noNull(r.getStatus())+"</td></tr>";




        try

{

            //GET OSA SCORE and # OF PROJECTS FIRST
            PreparedStatement st = session.connection().prepareStatement(
                    "   select sum(score) AS score, count(task) AS projects  from " +
                    "  ( " +
                    "  select * from (  " +
                    "  select score,project.id_project, deliveryDate, project.startDate,lintask.ID_LinTask as task  " +
                    "  from lintask, targetdoc, sourcedoc, project " +
                    "              where " +
                    "              personname=? " +
                    "              and lintask.id_targetDoc=targetdoc.id_targetDoc  " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "              and sourcedoc.id_project = project.id_project "+ 
                    "              and project.startDate is not null  and score <>0" +
                    "  ) AS T1  " +
                    "  union  " +
                    "  select * from (  " +
                    "  select score ,project.id_project , deliveryDate, project.startDate,dtptask.ID_DtpTask as task   " +
                    "  from dtptask, targetdoc, sourcedoc , project " +
                    "              where  " +
                    "              personname=? " +
                    "              and dtptask.id_targetDoc=targetdoc.id_targetDoc " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " and sourcedoc.id_project = project.id_project " +
                    "              and project.startDate is not null  and score <>0" +
                    "  ) AS T2  " +
                    "  union  " +
                    "  select * from (  " +
                    "  select score ,project.id_project , deliveryDate, project.startDate,engtask.ID_EngTask as task   " +
                    "  from engtask, targetdoc, sourcedoc , project " +
                    "              where " +
                    "              personname=? " +
                    "              and engtask.id_targetDoc=targetdoc.id_targetDoc " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " and sourcedoc.id_project = project.id_project and project.startDate is not null and score <>0" +
                    "  ) AS T3  " +
                    "  union  " +
                    "  select * from (  " +
                    "  select score ,project.id_project , deliveryDate, project.startDate,othtask.ID_OthTask as task   " +
                    "  from othtask, targetdoc, sourcedoc , project " +
                    "              where " +
                    "              personname=? " +
                    "              and othtask.id_targetDoc=targetdoc.id_targetDoc " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " and sourcedoc.id_project = project.id_project " +
                    "              and project.startDate is not null  and score <>0" +
                    "  ) AS T4  " +
                    "  ) AS MAINTABLE "+
//                    " group by year(deliveryDate) " +
//                    " order by year(deliveryDate) desc " +
                    "");


            st.setString

(1, "" + r.getResourceId());
            st.setString

(2, "" + r.getResourceId());
            st.setString

(3, "" + r.getResourceId());
              st.setString

(4, "" + r.getResourceId());
            ResultSet rs= st.executeQuery();
            int totalProjects = 0;
            double avgScore=0;
            double totalScore=0;
            int yearCount=0;
            while(rs.next()) {
             //   result += " | <b># PROJECTS: </b>" + rs.getInt("projects");
               // result +=" | <b>OSA: </b>" + StandardCode.getInstance().formatMoney(rs.getDouble("score") / rs.getInt("projects"));
                 totalProjects += rs.getInt("projects");
                //System.out.println("-----"+totalProjects);
//                avgScore+=rs.getDouble("score") / rs.getInt("projects") ;
                totalScore += rs.getDouble("score");
                ++yearCount;
            }
avgScore = totalScore/totalProjects;
   /*Use this for Annual Avg OSA*/
   result += " | <b># PROJECTS: </b>" + totalProjects;
   result +="| <b>Email: </b><a href='mailto:"+StandardCode.getInstance().noNull(r.getEmail_address1())+"'>"+StandardCode.getInstance().noNull(r.getEmail_address1())+"</a>";

   result +=" | <b>OSA: </b>" + StandardCode.getInstance().formatMoney(avgScore )+" ( "+StandardCode.getInstance().formatMoney(totalScore)+"/"+totalProjects+" )";
st.close();


            st =session.connection().prepareStatement(
                    "select sum(totalScore)/sum(numOfScores) as ISA from " +
                    " (select * from " +
                    " (select count(*) as numOfScores,sum(score) as totalScore from ratescoredtp " +
                    " where id_resource=? and score <>0) AS T1 " +
                    " union " +
                    " select * from " +
                    " (select count(*) as numOfScores,sum(score) as totalScore from " +
                    " ratescorelanguage rsl, languagepair lp " +
                    " where lp.id_resource=? and lp.id_languagepair=rsl.id_languagepair " +
                    " and score<>0) AS T2 " +
                    " ) AS MAIN_TABLE ");
            st.setString(1, "" + r.getResourceId());
            st.setString(2, "" + r.getResourceId());

            rs =st.executeQuery();
      ///      if(rs.next()) {
               result += " </td></tr><tr><td align='center'>";//<b>ISA:</b> " + StandardCode.getInstance().formatMoney(rs.getDouble("ISA"));
    //        }

st.close();
String specialty="";
for(int i=0;i<4;i++){
  if(i==0)  specialty="Medical";
  if(i==1)  specialty="Technical";
  if(i==2)  specialty="Software";
  if(i==3)  specialty="Legal/Financial";


st =session.connection().prepareStatement(
                    "select count(0) as numOfScores,sum(score) as totalScore,lp.id_languagepair,l.abr from  " +
                    "language l inner join ratescorelanguage rsl on l.language = rsl.target  inner join languagepair lp on lp.id_languagepair=rsl.id_languagepair " +
                    "where score<>0 and lp.id_resource=? and specialty=?  " +
                    "group by lp.id_resource, rsl.target");

            st.setString(1, "" + r.getResourceId());
            st.setString(2, specialty);
            rs =st.executeQuery();

           // //System.out.println("rsssssssssssssssssssssss"+rs);
           String isaScore="";
            while(rs.next()) {
                if(!isaScore.isEmpty())
                    isaScore += ",";
                isaScore += " "+StandardCode.getInstance().formatMoney(rs.getDouble("totalScore")/rs.getDouble("numOfScores")) + " " + rs.getString("abr");
            }
            if(!isaScore.isEmpty())
                result += "| <b>ISA "+ specialty +":</b> " +  isaScore;
            else
                result += "| <b>ISA "+ specialty +":</b> 0.00" ;
st.close();
}
            st =

session.connection().prepareStatement(
                    "select engdtpma.totalScore as dtpScore, " +
                    " expertma.totalScore as expertScore, " +
                    " linma.totalScore as linScore, " +
                    " otherma.totalScore as deductionScore, " +
                    " otherma.dtptotalScore as dtpdeductionScore, " +
                    " otherma.experttotalScore as expertdeductionScore " +
                    " from engdtpma, expertma, linma, otherma " +
                    " where engdtpma.id_resource=? and " +
                    " expertma.id_resource=? and " +
                    " linma.id_resource=? and " +
                    " otherma.id_resource=? ");

            st.setString

(1, "" + r.getResourceId());
            st.setString

(2, "" + r.getResourceId());
            st.setString

(3, "" + r.getResourceId());
            st.setString

(4, "" + r.getResourceId());

            rs =

st.executeQuery();
            if

(rs.next()) {
                double totalMA = rs.getDouble("dtpScore") + rs.getDouble("expertScore") + rs.getDouble("linScore") - rs.getDouble("deductionScore");
                result +=

" </td></tr><tr><td align='center'><font size='2'><b>LING MA:</b> " + StandardCode.getInstance().formatMoney(rs.getDouble("linScore") - rs.getDouble("deductionScore")) + " | " +
                        " <b>DTP/ENG MA:</b> " + StandardCode.getInstance().formatMoney(rs.getDouble("dtpScore") - rs.getDouble("dtpdeductionScore")) + " | " +
                        " <b>EXPERT MA:</b> " + StandardCode.getInstance().formatMoney(rs.getDouble("expertScore") - rs.getDouble("expertdeductionScore")) +
                        "</td></tr>";
            }

st.close();

            st =

session.connection().prepareStatement(
                    "SELECT l1.abr as sourceLanguage, l2.abr as targetLanguage FROM " +
                    "languagepair lp, language l1, language l2 " +
                    "where lp.id_resource=? " +
                    "and lp.sourceId=l1.language_id " +
                    "and lp.targetId=l2.language_id ");

            st.setString

(1, "" + r.getResourceId());
            rs =

st.executeQuery();
            String langs

= "";
            while

(rs.next()) {
                langs += rs.getString("sourceLanguage") + "-" + rs.getString("targetLanguage") + ", ";
            }

if (langs.endsWith(", ")) {
                langs = langs.substring(0, langs.length() - 2);
            }

result += " <tr><td align='center'><font size='2'><b>LANGUAGES: </b>" + langs ;

    try {
         if(riskRating.equalsIgnoreCase("")){}else{
     result+="| <b>Risk Rating: </b>"+riskRating;

     }       
    } catch (Exception e) {
    }
String service="";

if(r.isTranslator()==true)service+="T, ";
if(r.isEditor()==true)service+="E, ";
if(r.isTne()==true)service+="TE, ";
if(r.isProofreader()==true)service+="P, ";
if(r.isInterpreting()==true)service+="I, ";
if(r.isEvaluator()==true)service+="Ev, ";
if(r.isEngineering()==true)service+="Eng, ";
if(r.isDtp()==true)service+="DTP, ";
if(r.isFqa()==true)service+="FQA, ";
if(r.isIcr()==true)service+="ICR, ";
if(r.isExpert()==true)service+="Exp, ";
if(r.isQuality()==true)service+="Q, ";
if(r.isInformationTechnology()==true)service+="IT, ";
if(r.isConsultant()==true)service+="C, ";
if(r.isHumanResource()==true)service+="HR, ";
if(r.isAccounting()==true)service+="Acc, ";
if(r.isSales()==true)service+="SM, ";
if(r.isPartner()==true)service+="Prtnr, ";
if(r.isPostEditing()==true)service+="Pe, ";
//if(r.is==true)service+=", ";
//if(r.is==true)service+=", ";
//if(r.is==true)service+=", ";

if(!service.equalsIgnoreCase("")){
    result+="| <b>Service: </b>"+service;
}



result +="</td> </tr></table><hr>";

            return

result;
        }

catch (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
            throw

new RuntimeException(e);
        } /*
 * Regardless of whether the above processing resulted in an Exception
 * or proceeded normally, we want to close the Hibernate session.  When
 * closing the session, we must allow for the possibility of a Hibernate
 * Exception.
 *
 */ finally {
            if (session != null) {
                try {

                    session.close();
                }

catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw

new RuntimeException(e);
                }
            }
        }
    }

public static Integer getResourceTaskCount(int resourceId, int clientId, String taskname, String language) {
        //get resource to edit
        Session session = ConnectionFactory.getInstance().getSession();

                 if(taskname.equalsIgnoreCase("Translator"))taskname = "Translation";
                 if(taskname.equalsIgnoreCase("Editor"))taskname = "Editing";
                 if(taskname.equalsIgnoreCase("Proofreader"))taskname = "Proofreading";
                 if(taskname.equalsIgnoreCase("DTP"))taskname = "DTP";
                 if(taskname.equalsIgnoreCase("FQA"))taskname = "FQA";
                 if(taskname.equalsIgnoreCase("LQA"))taskname = "LQA";
                 if(taskname.equalsIgnoreCase("T&E"))taskname = "T&E";
                 if(taskname.equalsIgnoreCase("E&P"))taskname = "E&P";
                 if(taskname.equalsIgnoreCase("T&P"))taskname = "T&P";
                 
                 
                 String langQuery = "";
                 if(!StandardCode.getInstance().noNull(language).equalsIgnoreCase("")){
                     String[] languages  = language.split("-");
                                                 
                     langQuery = " and (sourcedoc.language = '"+(String) LanguageAbs.getInstance().getAbs().get(languages[0])+"' and targetdoc.language = '"+ (String) LanguageAbs.getInstance().getAbs().get(languages[1])+"') "; 
                 }
                 
         PreparedStatement st=null;
        
         try{
             if(taskname.equalsIgnoreCase("T&E")||taskname.equalsIgnoreCase("E&P")||taskname.equalsIgnoreCase("T&P")){
                 String query =  "  select count(project.id_project) AS projects " +
                    "  from lintask, targetdoc, sourcedoc, project " +
                    "              where " +
                    "              personname= " +resourceId+
                    "              and project.ID_Client= "+clientId+
                    "              and multi='" +taskname+"'"+langQuery+
                    "              and lintask.id_targetDoc=targetdoc.id_targetDoc  " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "              and sourcedoc.id_project = project.id_project ";
           st = session.connection().prepareStatement(query);
//           st = session.connection().prepareStatement(
//                    "  select count(project.id_project) AS projects " +
//                    "  from lintask, targetdoc, sourcedoc, project " +
//                    "              where " +
//                    "              personname=? " +
//                    "              and project.ID_Client=?     "+
//                    "              and multi=? ?" +
//                    "              and lintask.id_targetDoc=targetdoc.id_targetDoc  " +
//                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
//                    "              and sourcedoc.id_project = project.id_project ") ;
//
//        st.setInt(1, resourceId);st.setInt(2, clientId);st.setString(3, taskname);st.setString(4, langQuery);

             }else{
                 
                 String query = "   select  count(id_project) AS projects  from " +
                    "  ( " +
                    "  select * from (  " +
                    "  select project.id_project " +
                    "  from lintask, targetdoc, sourcedoc, project " +
                    "              where " +
                    "              personname= " +resourceId+
                    "              and project.ID_Client= "+clientId+
                    "              and taskname='" +taskname+"'"+langQuery+
                    "              and (multi is null or multi ='') " +
                    "              and lintask.id_targetDoc=targetdoc.id_targetDoc  " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "              and sourcedoc.id_project = project.id_project " +
                    "  ) AS T1  " +
                    "  union  " +
                    "  select * from (  " +
                    "  select project.id_project  " +
                    "  from dtptask, targetdoc, sourcedoc , project " +
                    "              where  " +
                    "              personname= " +resourceId+
                    "              and project.ID_Client= "+clientId+
                    "              and taskname='" +taskname+"'"+langQuery+                 
                    "              and dtptask.id_targetDoc=targetdoc.id_targetDoc " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " and sourcedoc.id_project = project.id_project " +
                    "  ) AS T2  " +
                    "  union  " +
                    "  select * from (  " +
                    "  select project.id_project  " +
                    "  from engtask, targetdoc, sourcedoc , project " +
                    "              where " +
                    "              personname= " +resourceId+
                    "              and project.ID_Client= "+clientId+
                    "              and taskname='" +taskname+"'"+langQuery+
                    "              and engtask.id_targetDoc=targetdoc.id_targetDoc " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " and sourcedoc.id_project = project.id_project " +
                    "  ) AS T3  " +
//                    "  union  " +
//                    "  select * from (  " +
//                    "  select project.id_project " +
//                    "  from othtask, targetdoc, sourcedoc , project " +
//                    "              where " +
//                    "              personname=? " +
//                    "              and taskname=? " +
//                    "              and project.ID_Client=?     "+
//                    "              and othtask.id_targetDoc=targetdoc.id_targetDoc " +
//                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
//                    " and sourcedoc.id_project = project.id_project " +
//                    "  ) AS T4  " +
                    "  ) AS MAINTABLE ";
st = session.connection().prepareStatement(query);
//            //GET OSA SCORE and # OF PROJECTS FIRST
//             st = session.connection().prepareStatement(
//                    "   select  count(id_project) AS projects  from " +
//                    "  ( " +
//                    "  select * from (  " +
//                    "  select project.id_project " +
//                    "  from lintask, targetdoc, sourcedoc, project " +
//                    "              where " +
//                    "              personname=? " +
//                    "              and project.ID_Client=?     "+
//                    "              and taskname=? ?" +
//                    "              and (multi is null or multi ='') " +
//                    "              and lintask.id_targetDoc=targetdoc.id_targetDoc  " +
//                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
//                    "              and sourcedoc.id_project = project.id_project " +
//                    "  ) AS T1  " +
//                    "  union  " +
//                    "  select * from (  " +
//                    "  select project.id_project  " +
//                    "  from dtptask, targetdoc, sourcedoc , project " +
//                    "              where  " +
//                    "              personname=? " +
//                    "              and project.ID_Client=?     "+
//                    "              and taskname=? ?" +                    
//                    "              and dtptask.id_targetDoc=targetdoc.id_targetDoc " +
//                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
//                    " and sourcedoc.id_project = project.id_project " +
//                    "  ) AS T2  " +
//                    "  union  " +
//                    "  select * from (  " +
//                    "  select project.id_project  " +
//                    "  from engtask, targetdoc, sourcedoc , project " +
//                    "              where " +
//                    "              personname=? " +
//                    "              and project.ID_Client=?     "+
//                    "              and taskname=? ?" +
//                    "              and engtask.id_targetDoc=targetdoc.id_targetDoc " +
//                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
//                    " and sourcedoc.id_project = project.id_project " +
//                    "  ) AS T3  " +
////                    "  union  " +
////                    "  select * from (  " +
////                    "  select project.id_project " +
////                    "  from othtask, targetdoc, sourcedoc , project " +
////                    "              where " +
////                    "              personname=? " +
////                    "              and taskname=? " +
////                    "              and project.ID_Client=?     "+
////                    "              and othtask.id_targetDoc=targetdoc.id_targetDoc " +
////                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
////                    " and sourcedoc.id_project = project.id_project " +
////                    "  ) AS T4  " +
//                    "  ) AS MAINTABLE ");


//            st.setInt(1, resourceId);st.setInt(2, clientId);st.setString(3, taskname);st.setString(4, langQuery);
//            st.setInt(5, resourceId);st.setInt(6, clientId);st.setString(7, taskname);st.setString(8, langQuery);
//            st.setInt(9, resourceId);st.setInt(10, clientId);st.setString(11, taskname);st.setString(12, langQuery);
//            st.setInt(10, resourceId);st.setInt(11, clientId);st.setString(12, taskname);
             }
            ResultSet rs= st.executeQuery();
            int totalProjects = 0;
            while(rs.next()) {
                 totalProjects += rs.getInt("projects");
            }

  
            return totalProjects;
        }

catch (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
            throw

new RuntimeException(e);
        } /*
 * Regardless of whether the above processing resulted in an Exception
 * or proceeded normally, we want to close the Hibernate session.  When
 * closing the session, we must allow for the possibility of a Hibernate
 * Exception.
 *
 */ finally {
            if (session != null) {
                try {

                    session.close();
                }

catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw

new RuntimeException(e);
                }
            }
        }
    }


    public static Double getAssessmentColumn1(Resource r) {
        //get resource to edit
        Session session = ConnectionFactory.getInstance().getSession();


        Double result=0.00;


        //3 columns

try

{

            //GET OSA SCORE and # OF PROJECTS FIRST
            PreparedStatement st = session.connection().prepareStatement(
                    "   select sum(score) AS score, count(id_project) AS projects  from " +
                    "  ( " +
                    "  select * from (  " +
                    "  select score,project.id_project, deliveryDate " +
                    "  from lintask, targetdoc, sourcedoc, project " +
                    "              where " +
                    "              personname=? " +
                    "              and lintask.id_targetDoc=targetdoc.id_targetDoc  " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "              and sourcedoc.id_project = project.id_project " +
                    "  ) AS T1  " +
                    "  union  " +
                    "  select * from (  " +
                    "  select score ,project.id_project , deliveryDate " +
                    "  from dtptask, targetdoc, sourcedoc , project " +
                    "              where  " +
                    "              personname=? " +
                    "              and dtptask.id_targetDoc=targetdoc.id_targetDoc " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " and sourcedoc.id_project = project.id_project " +
                    "  ) AS T2  " +
                    "  union  " +
                    "  select * from (  " +
                    "  select score ,project.id_project , deliveryDate " +
                    "  from engtask, targetdoc, sourcedoc , project " +
                    "              where " +
                    "              personname=? " +
                    "              and engtask.id_targetDoc=targetdoc.id_targetDoc " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " and sourcedoc.id_project = project.id_project " +
                    "  ) AS T1  " +
                    "  ) AS MAINTABLE ");


            st.setString(1, "" + r.getResourceId());
            st.setString(2, "" + r.getResourceId());
            st.setString(3, "" + r.getResourceId());
            ResultSet rs= st.executeQuery();


            if(rs.next()) {
                try{
                result += Double.parseDouble(StandardCode.getInstance().formatMoney(rs.getDouble("score") / rs.getInt("projects")));
                }catch(Exception e){}
            }

st.close();
return result;

 }

catch (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
            throw

new RuntimeException(e);
        } /*
 * Regardless of whether the above processing resulted in an Exception
 * or proceeded normally, we want to close the Hibernate session.  When
 * closing the session, we must allow for the possibility of a Hibernate
 * Exception.
 *
 */ finally {
            if (session != null) {
                try {

                    session.close();
                }

catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw

new RuntimeException(e);
                }

            }
        }
    }

    public static Double getAssessmentISA(Resource r) {
        //get resource to edit
        Session session = ConnectionFactory.getInstance().getSession();


        Double result=0.00;


        //3 columns


try{

            //GET OSA SCORE and # OF PROJECTS FIRST
            PreparedStatement st = session.connection().prepareStatement(
                    "select sum(totalScore)/sum(numOfScores) as ISA from " +
                    " (select * from " +
                    " (select count(*) as numOfScores,sum(score) as totalScore from ratescoredtp " +
                    " where id_resource=? and score <>0) AS T1 " +
                    " union " +
                    " select * from " +
                    " (select count(*) as numOfScores,sum(score) as totalScore from " +
                    " ratescorelanguage rsl, languagepair lp " +
                    " where lp.id_resource=? and lp.id_languagepair=rsl.id_languagepair " +
                    " and score<>0) AS T2 " +
                    " ) AS MAIN_TABLE ");


            st.setString(1, "" + r.getResourceId());
            st.setString(2, "" + r.getResourceId());

            ResultSet rs= st.executeQuery();


            if(rs.next()) {
                try{
                result += Double.parseDouble(StandardCode.getInstance().formatMoney(rs.getDouble("ISA") ));
                }catch(Exception e){}
            }

st.close();
return result;

 }

catch (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
            throw

new RuntimeException(e);
        } /*
 * Regardless of whether the above processing resulted in an Exception
 * or proceeded normally, we want to close the Hibernate session.  When
 * closing the session, we must allow for the possibility of a Hibernate
 * Exception.
 *
 */ finally {
            if (session != null) {
                try {

                    session.close();
                }

catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw

new RuntimeException(e);
                }

            }
        }
    }




    public static String getAssessmentColumn(Resource r) {
        //get resource to edit
        Session session = ConnectionFactory.getInstance().getSession();


        String result

= "";
        //3 columns






        try

{

            //GET OSA SCORE and # OF PROJECTS FIRST
            PreparedStatement st = session.connection().prepareStatement(
                    "   select sum(score) AS score, count(id_project) AS projects  from " +
                    "  ( " +
                    "  select * from (  " +
                    "  select score,project.id_project, deliveryDate " +
                    "  from lintask, targetdoc, sourcedoc, project " +
                    "              where " +
                    "              personname=? " +
                    "              and lintask.id_targetDoc=targetdoc.id_targetDoc  " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "              and sourcedoc.id_project = project.id_project " +
                    "  ) AS T1  " +
                    "  union  " +
                    "  select * from (  " +
                    "  select score ,project.id_project , deliveryDate " +
                    "  from dtptask, targetdoc, sourcedoc , project " +
                    "              where  " +
                    "              personname=? " +
                    "              and dtptask.id_targetDoc=targetdoc.id_targetDoc " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " and sourcedoc.id_project = project.id_project " +
                    "  ) AS T2  " +
                    "  union  " +
                    "  select * from (  " +
                    "  select score ,project.id_project , deliveryDate " +
                    "  from engtask, targetdoc, sourcedoc , project " +
                    "              where " +
                    "              personname=? " +
                    "              and engtask.id_targetDoc=targetdoc.id_targetDoc " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " and sourcedoc.id_project = project.id_project " +
                    "  ) AS T1  " +
                    "  ) AS MAINTABLE ");


            st.setString

(1, "" + r.getResourceId());
            st.setString

(2, "" + r.getResourceId());
            st.setString

(3, "" + r.getResourceId());
            ResultSet rs

= st.executeQuery();


            if

(rs.next()) {
                result += "<b>OSA: </b>" + StandardCode.getInstance().formatMoney(rs.getDouble("score") / rs.getInt("projects")) + "<br>";

            }

st.close();


            st =session.connection().prepareStatement(
                    "select sum(totalScore)/sum(numOfScores) as ISA from " +
                    " (select * from " +
                    " (select count(*) as numOfScores,sum(score) as totalScore from ratescoredtp " +
                    " where id_resource=? and score <>0) AS T1 " +
                    " union " +
                    " select * from " +
                    " (select count(*) as numOfScores,sum(score) as totalScore from " +
                    " ratescorelanguage rsl, languagepair lp " +
                    " where lp.id_resource=? and lp.id_languagepair=rsl.id_languagepair " +
                    " and score<>0) AS T2 " +
                    " ) AS MAIN_TABLE ");
            st.setString(1, "" + r.getResourceId());
            st.setString(2, "" + r.getResourceId());
            rs =st.executeQuery();
            if(rs.next()) {
                result += "<b>ISA:</b> " + StandardCode.getInstance().formatMoney(rs.getDouble("ISA")) + "<br>";
            }

st.close();


            st =

session.connection().prepareStatement(
                    "select engdtpma.totalScore as dtpScore, " +
                    " expertma.totalScore as expertScore, " +
                    " linma.totalScore as linScore, " +
                    " otherma.totalScore as deductionScore, " +
                    " otherma.dtptotalScore as dtpdeductionScore, " +
                    " otherma.experttotalScore as expertdeductionScore " +
                    " from engdtpma, expertma, linma, otherma " +
                    " where engdtpma.id_resource=? and " +
                    " expertma.id_resource=? and " +
                    " linma.id_resource=? and " +
                    " otherma.id_resource=? ");

            st.setString

(1, "" + r.getResourceId());
            st.setString

(2, "" + r.getResourceId());
            st.setString

(3, "" + r.getResourceId());
            st.setString

(4, "" + r.getResourceId());

            rs =

st.executeQuery();
            if

(rs.next()) {
                result += "<b>LIN MA:</b> " + StandardCode.getInstance().formatMoney(rs.getDouble("linScore") - rs.getDouble("deductionScore")) + "<br> " +
                        "<b>DTP/ENG:</b> " + StandardCode.getInstance().formatMoney(rs.getDouble("dtpScore") - rs.getDouble("dtpdeductionScore")) + "<br> " +
                        "<b>EXPERT:</b> " + StandardCode.getInstance().formatMoney(rs.getDouble("expertScore") - rs.getDouble("expertdeductionScore"));
            }

st.close();


            return

result;
        }

catch (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
            throw

new RuntimeException(e);
        } /*
 * Regardless of whether the above processing resulted in an Exception
 * or proceeded normally, we want to close the Hibernate session.  When
 * closing the session, we must allow for the possibility of a Hibernate
 * Exception.
 *
 */ finally {
            if (session != null) {
                try {

                    session.close();
                }

catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw

new RuntimeException(e);
                }
            }
        }
    }
    
      public static String getHighPerformer(String srcLang, String targetLang, String reportType) {
        //get resource to edit
        Session session = ConnectionFactory.getInstance().getSession();
        String result= "";
        Double osa =0.00;Double isa =0.00;
        Integer projectCount = 0 ;
        
        
        try{ 
            ArrayList results = new ArrayList();
            JSONObject jo = new JSONObject(); 
           List resourceList = ResourceService.getInstance().getResourceList();
           for(int re = 0; re< resourceList.size();re++){
               Resource r = (Resource) resourceList.get(re);

            //GET OSA SCORE and # OF PROJECTS FIRST
            PreparedStatement st = session.connection().prepareStatement(
                    "   select sum(score) AS score, count(id_project) AS projects  from " +
                    "  ( " +
                    "  select * from (  " +
                    "  select score,project.id_project, deliveryDate " +
                    "  from lintask, targetdoc, sourcedoc, project " +
                    "              where " +
                    "              personname=? " +
                    "              and lintask.id_targetDoc=targetdoc.id_targetDoc  " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "              and sourcedoc.id_project = project.id_project and sourcedoc.language = ? and targetdoc.language = ?" +
                    "  ) AS T1  " +
                    "  union  " +
                    "  select * from (  " +
                    "  select score ,project.id_project , deliveryDate " +
                    "  from dtptask, targetdoc, sourcedoc , project " +
                    "              where  " +
                    "              personname=? " +
                    "              and dtptask.id_targetDoc=targetdoc.id_targetDoc " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " and sourcedoc.id_project = project.id_project and sourcedoc.language = ? and targetdoc.language = ? " +
                    "  ) AS T2  " +
                    "  union  " +
                    "  select * from (  " +
                    "  select score ,project.id_project , deliveryDate " +
                    "  from engtask, targetdoc, sourcedoc , project " +
                    "              where " +
                    "              personname=? " +
                    "              and engtask.id_targetDoc=targetdoc.id_targetDoc " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " and sourcedoc.id_project = project.id_project and sourcedoc.language = ? and targetdoc.language = ? " +
                    "  ) AS T1  " +
                    "  ) AS MAINTABLE ");


            st.setString(1, "" + r.getResourceId());
            st.setString(4, "" + r.getResourceId());
            st.setString(7, "" + r.getResourceId());
            
            st.setString(2, "" + srcLang);
            st.setString(5, "" + srcLang);
            st.setString(8, "" + srcLang);
            
            st.setString(3, "" + targetLang);
            st.setString(6, "" + targetLang);
            st.setString(9, "" + targetLang);
            ResultSet rs= st.executeQuery();


            if(rs.next()) {
                osa = rs.getDouble("score") / rs.getInt("projects") ;
                projectCount = rs.getInt("projects");
            }
            st.close();


            st =session.connection().prepareStatement(
                    "select sum(totalScore)/sum(numOfScores) as ISA from " +
                    " (select * from " +
                    " (select count(*) as numOfScores,sum(score) as totalScore from ratescoredtp " +
                    " where id_resource=? and score <>0 and source =? and target = ?) AS T1 " +
                    " union " +
                    " select * from " +
                    " (select count(*) as numOfScores,sum(score) as totalScore from " +
                    " ratescorelanguage rsl, languagepair lp " +
                    " where lp.id_resource=? and lp.id_languagepair=rsl.id_languagepair and rsl.source = ? and rsl.target = ? " +
                    " and score<>0) AS T2 " +
                    " ) AS MAIN_TABLE ");
            st.setString(1, "" + r.getResourceId());
            st.setString(4, "" + r.getResourceId());
            st.setString(2, "" + srcLang);
            st.setString(5, "" + srcLang);
            st.setString(3, "" + targetLang);
            st.setString(6, "" + targetLang);
            
            rs =st.executeQuery();
            if(rs.next()) {
                isa = rs.getDouble("ISA");
            }
            st.close();
               st = session.connection().prepareStatement(
                    "    SELECT pm,COUNT(project.id_project) AS cnt  " +
                    "  from lintask, targetdoc, sourcedoc, project " +
                    "              where " +
                    "              personname=? " +
                    "              and lintask.id_targetDoc=targetdoc.id_targetDoc  " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "              and sourcedoc.id_project = project.id_project and sourcedoc.language = ? and targetdoc.language = ?" +
                    "  union  " +
                    "    SELECT pm,COUNT(project.id_project) AS cnt  " +
                    "  from dtptask, targetdoc, sourcedoc , project " +
                    "              where  " +
                    "              personname=? " +
                    "              and dtptask.id_targetDoc=targetdoc.id_targetDoc " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " and sourcedoc.id_project = project.id_project and sourcedoc.language = ? and targetdoc.language = ? " +
                    "  union  " +
                    "    SELECT pm,COUNT(project.id_project) AS cnt  " +
                    "  from engtask, targetdoc, sourcedoc , project " +
                    "              where " +
                    "              personname=? " +
                    "              and engtask.id_targetDoc=targetdoc.id_targetDoc " +
                    "              and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    " and sourcedoc.id_project = project.id_project and sourcedoc.language = ? and targetdoc.language = ? " );
            st.setString(1, "" + r.getResourceId());
            st.setString(4, "" + r.getResourceId());
            st.setString(7, "" + r.getResourceId());
            
            st.setString(2, "" + srcLang);
            st.setString(5, "" + srcLang);
            st.setString(8, "" + srcLang);
            
            st.setString(3, "" + targetLang);
            st.setString(6, "" + targetLang);
            st.setString(9, "" + targetLang);
            
            rs =st.executeQuery();
           String[ ][ ] aryPM = new String[20][20];
            if(rs.next()) { 
                int flg = 0;
                for(int i = 0; i < aryPM.length;i++){
                    if(aryPM[0][i]==null && flg == 0)
                    {
                        aryPM[1][i] ="" + rs.getInt("cnt");
                        aryPM[0][i] ="" + rs.getString("pm");
                        flg = 1;
                        break;
                    }
                    if(aryPM[0][i].equalsIgnoreCase(rs.getString("pm")))
                    {
                        aryPM[1][i] ="" +(Integer.parseInt(aryPM[1][i]) + rs.getInt("cnt"));
                        flg = 1;
                        break;
                    }  
                }
               
            }
            st.close();
           String pmString ="";
           
           for(int i = 0; i < aryPM.length;i++){
                    if(aryPM[0][i]==null)
                    {
                        break;
                    }
                    else
                        pmString = aryPM[0][i]+ "("+aryPM[0][2] +") \n";
                }
               //System.out.println(r.getResourceId()+"isa"+isa+"   "+osa +"   "+projectCount +"  "+pmString);
            if(reportType.equalsIgnoreCase("High")){
                if(isa>20){
                    try {
                         if(r.getCompanyName().equalsIgnoreCase("FreeLance")||r.getCompanyName().equalsIgnoreCase("null"))
                            jo.put("Resource", r.getFirstName()+" "+r.getLastName());
                        else
                            jo.put("Resource", r.getCompanyName());
                        jo.put("ISA", isa);
                        jo.put("OSA", osa);
                        jo.put("project", projectCount);
                        jo.put("pm", pmString);

                        results.add(jo);
                    } catch (Exception e) {
                    }
               
                
                }
                
                
            }
            
           }
            return result;
        }

catch (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
            throw

new RuntimeException(e); 
        } /*
 * Regardless of whether the above processing resulted in an Exception
 * or proceeded normally, we want to close the Hibernate session.  When
 * closing the session, we must allow for the possibility of a Hibernate
 * Exception.
 *
 */ finally {
            if (session != null) {
                try {

                    session.close();
                }

catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw

new RuntimeException(e);
                }
            }
        }
    }

    public static String getIssuesList(int resourceId) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        String result= "<table width='75%'><tr><td><b>Project</b></td><td><b>Date</b></td><td><b>Task</b></td><td><b>OSA</td><td><b>PM</td><td><b>Description</td></tr>";
        result +="<tr><td colspan=6><hr></td></tr>";

        try
        {
            PreparedStatement st = session.connection().prepareStatement(
                    " select id_lintask as taskId, score, scoreDescription, deliveryDate,project.startDate, taskName, pm, project.number, company_code, units, project.id_project, receivedDateDate, year(deliveryDate) as dYear, 'LIN' as taskType " +
                    " from lintask, targetdoc, sourcedoc, project, client_information " +
                    "             where " +
                    "             personname=? " +
                    "            and lintask.id_targetDoc=targetdoc.id_targetDoc " +
                    "           and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "            and sourcedoc.id_project = project.id_project " +
                    "            and project.id_client = client_information.id_client and score<>0 and score <> 35" +
                    " union " +
                    " select id_dtptask as taskId, score, scoreDescription, deliveryDate,project.startDate, taskName, pm, project.number, company_code, units, project.id_project, receivedDateDate, year(deliveryDate) as dYear, 'DTP' as taskType   " +
                    " from dtptask, targetdoc, sourcedoc, project, client_information " +
                    "             where " +
                    "             personname=? " +
                    "            and dtptask.id_targetDoc=targetdoc.id_targetDoc " +
                    "           and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "            and sourcedoc.id_project = project.id_project " +
                    "            and project.id_client = client_information.id_client  and score<>0 and score <> 35" +
                    " union " +
                    " select id_engtask as taskId, score, scoreDescription, deliveryDate,project.startDate, taskName, pm, project.number, company_code, units, project.id_project, receivedDateDate, year(deliveryDate) as dYear, 'DTP' as taskType   " +
                    " from engtask, targetdoc, sourcedoc, project, client_information " +
                    "             where " +
                    "             personname=? " +
                    "            and engtask.id_targetDoc=targetdoc.id_targetDoc " +
                    "           and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc " +
                    "            and sourcedoc.id_project = project.id_project " +
                    "            and project.id_client = client_information.id_client  and score<>0 and score <> 35" +
                    " order by deliveryDate desc  ");


            st.setString(1, "" + resourceId);
            st.setString(2, "" + resourceId);
            st.setString(3, "" + resourceId);
            //st.setString(2, ""+resourceId);
            //st.setString(3, ""+resourceId);
            ResultSet rs= st.executeQuery();
            int dYear = 0;
            int totalYearlyOsa = 0;
            int totalYearlyProjects = 0;
            int totalLinOsa = 0;
            int totalDtpEngOsa = 0;
            int totalDtpEngProjects = 0;
            int totalLinProjects = 0;

            while(rs.next()) {
                String delDate="";
                
           
                if (rs.getDate("deliveryDate") != null) {delDate = sdf.format(rs.getDate("deliveryDate"));}
                if (rs.getString("startDate") != null && rs.getInt("score")!=35) {
                    
                    result += "<tr><td>" +
                            "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openSingleProjectWindow('" + rs.getString("number") + rs.getString("company_code") + "','" + rs.getString("id_project") + "')\">" + rs.getString("number") + rs.getString("company_code") + "</a>" +
                            "</td><td>" + delDate + "</td><td>" + rs.getString("taskName") + "</td><td>" + rs.getInt("score") + "</td><td>" + rs.getString("pm") + "</td><td width='25%'>" + noNull(rs.getString("scoreDescription"))  + "</td></tr>";

                    if("LIN".equals(rs.getString("taskType"))) {
                        totalDtpEngOsa += rs.getInt("score");
                        totalDtpEngProjects++;
}
else {
                        totalLinOsa += rs.getInt("score");
                        totalLinProjects++;
}

totalYearlyOsa += rs.getInt("score");
                    totalYearlyProjects++;
}
}
            return result;
        }

catch (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
            throw

new RuntimeException(e);
        } /*
 * Regardless of whether the above processing resulted in an Exception
 * or proceeded normally, we want to close the Hibernate session.  When
 * closing the session, we must allow for the possibility of a Hibernate
 * Exception.
 *
 */ finally {
            if (session != null) {
                try {

                    session.close();
                }

catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw

new RuntimeException(e);
                }

}
        }

    }

    public static void

updateScoreDescription(String taskType, int taskId, String scoreDescription) {


        Session session = ConnectionFactory.getInstance().getSession();
        Transaction tx

= null;
        try

{
            tx = session.beginTransaction();
            PreparedStatement st

= null;

            if

(taskType.startsWith("LIN")) {
                st = session.connection().prepareStatement("update lintask set scoreDescription=? where id_lintask=?");
            }

else if (taskType.startsWith("DTP")) {

                st = session.connection().prepareStatement("update dtptask set scoreDescription=? where id_dtptask=?");

            }

else if (taskType.startsWith("ENG")) {
                st = session.connection().prepareStatement("update engtask set scoreDescription=? where id_engtask=?");
            }

st.setInt(2, taskId);
            st.setString

(1, scoreDescription);
            st.executeUpdate

();
            st.close

();
            tx.commit

();

        }

catch (Exception e) {
            try {
                tx.rollback();
            }

catch (HibernateException he) {
                System.err.println("Hibernate Exception" + e.getMessage());
                throw

new RuntimeException(e);
            }

System.err.println("Hibernate Exception" + e.getMessage());
            throw

new RuntimeException(e);
        } /*
 * Regardless of whether the above processing resulted in an Exception
 * or proceeded normally, we want to close the Hibernate session.  When
 * closing the session, we must allow for the possibility of a Hibernate
 * Exception.
 *
 */ finally {
            if (session != null) {
                try {

                    session.close();
                }

catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw

new RuntimeException(e);
                }




    }



        }

    }

public static String getAdminMaScore() {
                /*
                 * Use the ConnectionFactory to retrieve an open
                 * Hibernate Session.
                 *
                     */
String maScore[]={"nativeSpeaker","ft","pt","nativeLocation","profession","medicalExpirience","softwareExpirience","techExpirience","legalExpirience","marketingExpirience","iso9001","iso13485","iso14971","otherIso","industry1","industry2","industry3","countryCert1","countryCert2","countryCert3","reference","sizeFullTimeEmp","size1Score","sizeVolume","size2Score","location1","location2","location3","nicheLangs","nicheSpecialization","trados","sdlx","dejavu","transit","catalyst","otherTool","dtpFullTime","dtpYearFounded","dtpContentExpert1","dtpContentExpert2","dtpContentExpert3","dtpMAtools1","dtpMAtools2","dtpMAtools3","dtpMAiso1","dtpMAiso2","dtpMAiso3","dtpMAiso4","dtpMAindustry1","dtpMAindustry2","dtpMAindustry3","dtpMAcountryCert1","dtpMAcountryCert2","dtpMAcountryCert3"};

        Session session = ConnectionFactory.getInstance().getSession();
        int id=1;
    Transaction tx = null;
        try {
                        /*
                         * Build HQL (Hibernate Query Language) query to retrieve a list
                         * of all the items currently stored by Hibernate.
                         */
    tx = session.beginTransaction();


         PreparedStatement pstmt = session.connection().prepareStatement("select * from mechanical where id_ma=1");
         ResultSet rs = pstmt.executeQuery();
          //  //System.out.println("query"+excelAdminQuoteDropdowns+"                  "+query);
            String dropdownJSArray = "";
            int leng=maScore.length;
          while(rs.next())
            {
            for(int i=0; i<leng;i++){
               // Dropdown pr = (Dropdown)dropdowns.get(i);
////System.out.println("Ma Ma AMaaaaaa "+rs.getInt(maScore[i]));
                dropdownJSArray+= "[\""+i+"\",";
                dropdownJSArray+= "\""+HrHelper.jsSafe(maScore[i])+"\",";
                dropdownJSArray+= "\""+ rs.getString(maScore[i])+"\"]";
                

                if(i!=leng-1){
                    dropdownJSArray+=",";
                }

            }
          }
            return dropdownJSArray;


        } catch (Exception e) {
            try {
                tx.rollback(); //error
            }
            catch (HibernateException he) {
                System.err.println("Hibernate Exception" + e.getMessage());
                throw new RuntimeException(e);
            }
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        }
                /*
                 * Regardless of whether the above processing resulted in an Exception
                 * or proceeded normally, we want to close the Hibernate session.  When
                 * closing the session, we must allow for the possibility of a Hibernate
                 * Exception.
                 *
                 */
        finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }


    }

public static double ISA_Score(Integer rId,String specialty){
 Session session = ConnectionFactory.getInstance().getSession();
Transaction tx = null;

        Double result=0.00;

try{


tx = session.beginTransaction();
PreparedStatement st =session.connection().prepareStatement(
                    "select sum(totalScore)/sum(numOfScores) as ISAME from " +
                    " (select count(*) as numOfScores,sum(score) as totalScore from " +
                    " ratescorelanguage rsl, languagepair lp " +
                    " where lp.id_resource=? and lp.id_languagepair=rsl.id_languagepair " +
                    " and score<>0 and specialty=?) AS T2 ");

            st.setString(1, ""+rId);
            st.setString(2, specialty);
           ResultSet rs =st.executeQuery();


           if (rs.next()) {
            result = rs.getDouble("ISAME");
        }
          
    //System.out.println("rsssssssssssssss"+rs.getDouble("ISAME"));

           return result;


   } catch (Exception e) {
            try {
                tx.rollback(); //error
            }
            catch (HibernateException he) {
                System.err.println("Hibernate Exception" + e.getMessage());
                throw new RuntimeException(e);
            }
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        }
                /*
                 * Regardless of whether the above processing resulted in an Exception
                 * or proceeded normally, we want to close the Hibernate session.  When
                 * closing the session, we must allow for the possibility of a Hibernate
                 * Exception.
                 *
                 */
        finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }
}
}
class MyTeamsDisplay {

    public MyTeamsDisplay() {
    }
    public String resourceId;
    public String sourceLang;
    public String targetLang;
    public String client = "";
    public Integer clientId;
    public String dtpApp = "";
    public String dtpUnit = "";
    public String dtpRate = "";
    public String dtpCCy = "";
}

class MyTeamsTotals {

    public MyTeamsTotals() {
    }
    public String resourceId;
    public int countProjects = 0;
    public double totalPaid = 0;
    public int countT = 0;
    public int countE = 0;
    public int countP = 0;
    public int countTE = 0;
    public int countICR = 0;
    public int countDTP = 0;
    public int countOTH = 0;
    public int countLIN = 0;
    public int countENG = 0;
    public int totalScore = 0;
}

class MyTeamsClientTotals {

    public MyTeamsClientTotals() {
    }
    public String resourceId;
    public String client = "";
    public String clientType = "";
    // public String clientClean = "";
    public int countProjects = 0;
    public double totalPaid = 0;
    public int countT = 0;
    public int countE = 0;
    public int countP = 0;
    public int countTE = 0;
    public int countICR = 0;
    public String testScore = "0";
}








