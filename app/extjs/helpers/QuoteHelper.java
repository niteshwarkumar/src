/*
 * ProjectHelper.java
 *
 * Created on April 7, 2008, 8:48 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */
package app.extjs.helpers;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import app.client.Client;
import app.client.ClientService;
import java.util.*;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import app.db.*;
import app.extjs.global.LanguageAbs;
import app.extjs.vo.Product;
import app.project.*;
import app.standardCode.StandardCode;
import org.json.JSONObject;
import app.quote.*;
import app.user.User;
import app.user.UserService;
//import com.sun.rsasign.r;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.Transaction;

/**
 *
 * @author Aleks
 */
public class QuoteHelper {

    public static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    //get all active projects
    public static List getPMPendingQuotes(String pmName) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            query = session.createQuery("select quote from app.quote.Quote1 quote left join quote.Project.Company company where quote.status = 'pending' and (quote.Project.pm ='" + pmName + "' OR quote.Project.Company.Sales_rep ='" + pmName + "') order by quote.quote1Id desc");

            List temp = query.list();
            List results = new ArrayList();
            System.out.println("Found:::" + temp.size());

            Hashtable duplicateQuotes = new Hashtable();
            for (ListIterator iter = temp.listIterator(); iter.hasNext();) {
                Quote1 p = (Quote1) iter.next();
                if (duplicateQuotes.get(p.getNumber()) != null) {
                    iter.remove();
                } else {
                    duplicateQuotes.put(p.getNumber(), p.getNumber());
                }
            }
            //query = session.createQuery("select quote from app.quote.Quote1 quote where quote.status = 'pending' and quote.Project.Company.Sales_rep ='"+pmName+"'");
            //temp.addAll(query.list());
            for (int i = 0; i < temp.size(); i++) {
                Quote1 q = (Quote1) temp.get(i);
                Project p = q.getProject();
                Quote1 recentQ = QuoteService.getInstance().getLastQuote(p.getQuotes());



                if (q.getNumber().equals(recentQ.getNumber())) { //only add most recent quote

                    //if(pmName.equals(q.getProject().getPm()) || pmName.equals(q.getProject().getCompany().getSales_rep())) {
                    try {
                        Hibernate.initialize(q.getSourceDocs());
                    } catch (Exception e) {
                    }
                    results.add(q);
                    q.setSubquotes("");
                    try {
                        Set subQuotes = p.getQuotes();

                        for (Iterator iter = subQuotes.iterator(); iter.hasNext();) {
                            Quote1 subQ = (Quote1) iter.next();
                            if (subQ != null) {
                                // System.out.println("subQ.getQuote1Id()="+subQ.getQuote1Id());
                                if (subQ.getQuote1Id().compareTo(q.getQuote1Id()) != 0) {

                                    //Hibernate.initialize(subQ.getSourceDocs());
                                    // subQ.setNumber(" "+q.getNumber()+"<sub>"+subQ.getNumber()+"</sub>");

                                    q.setSubquotes(q.getSubquotes() + "<a " + HrHelper.LINK_STYLE + " href=\"javascript:openQuoteWindow('" + q.getQuote1Id() + "','" + subQ.getNumber() + q.getProject().getCompany().getCompany_code() + "')\">" + subQ.getNumber() + q.getProject().getCompany().getCompany_code() + " / " + StandardCode.getInstance().formatMoney(subQ.getQuoteDollarTotal().doubleValue()) + " / " + sdf.format(subQ.getQuoteDate()) + "</a>" + "<br>");
                                    //results.add(subQ);
                                    System.out.println("Project no and Quote No" + p.getNumber() + "       " + q.getNumber());
                                }
                            }

                        }
                    } catch (Exception e) {
                    }

                    //}


                }


                /*if(q.getNumber().equals(recentQ.getNumber())) { //only add most recent quote
                 if(q.getProject().getPm() != null) {
                 if(q.getProject().getPm().equals(pmName)) {
                 results.add(q);
                 added = true;
                 }
                 }
                 if(!added && q.getProject().getCompany().getSales_rep() != null) {
                 if(pmName.equals(q.getProject().getCompany().getSales_rep())) {
                 //Hibernate.initialize(q.getProject());
                 //Hibernate.initialize(q.getProject().getCompany());
                 Hibernate.initialize(q.getSourceDocs());
                 results.add(q);
                 }
                 }
                 }*/
            }
            temp = null;
            //System.out.println("Returning:::" + results.size());
            return results;
        } catch (Exception e) {
            System.out.println("Exception::" + e.getMessage());
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
                    System.out.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }
    }

//get all active projects
    public static List getPMBackupQuotes(String pmName) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            query = session.createQuery("select quote from app.quote.Quote1 quote where quote.status = 'pending' and quote.Project.Company.Backup_pm ='" + pmName + "'");

            List temp = query.list();
            List results = new ArrayList();
            //System.out.println("Found:::" + temp.size());

            for (int i = 0; i < temp.size(); i++) {
                Quote1 q = (Quote1) temp.get(i);

                Project p = q.getProject();


                Quote1 recentQ = QuoteService.getInstance().getLastQuote(p.getQuotes());
                if (q.getNumber().equals(recentQ.getNumber())) { //only add most recent quote
                    //if(pmName.equals(q.getProject().getCompany().getBackup_pm())) {

                    Hibernate.initialize(q.getSourceDocs());
                    results.add(q);
                    //  System.out.println("q.getQuote1Id()="+q.getQuote1Id());
                    Set subQuotes = p.getQuotes();
                    q.setSubquotes("");
                    for (Iterator iter = subQuotes.iterator(); iter.hasNext();) {
                        Quote1 subQ = (Quote1) iter.next();
                        if (subQ != null) {
                            // System.out.println("subQ.getQuote1Id()="+subQ.getQuote1Id());
                            if (subQ.getQuote1Id().compareTo(q.getQuote1Id()) != 0) {

                                //Hibernate.initialize(subQ.getSourceDocs());
                                //subQ.setNumber("  "+q.getNumber()+"/"+subQ.getNumber());
                                //results.add(subQ);
                                q.setSubquotes(q.getSubquotes() + "<a " + HrHelper.LINK_STYLE + " href=\"javascript:openQuoteWindow('" + q.getQuote1Id() + "','" + subQ.getNumber() + q.getProject().getCompany().getCompany_code() + "')\">" + subQ.getNumber() + q.getProject().getCompany().getCompany_code() + " / " + StandardCode.getInstance().formatMoney(subQ.getQuoteDollarTotal().doubleValue()) + " / " + sdf.format(subQ.getQuoteDate()) + "</a>" + "<br>");


                            }
                        }

                    }
                    //}
                }


                /*   Quote1 recentQ = QuoteService.getInstance().getLastQuote(p.getQuotes());
                 if(q.getNumber().equals(recentQ.getNumber())) { //only add most recent quote
                 if(q.getProject().getCompany().getBackup_pm() != null) {
                 if(pmName.equals(q.getProject().getCompany().getBackup_pm())) {
                 Hibernate.initialize(q.getSourceDocs());
                 results.add(q);

                 }
                 }
                 }*/
            }
            temp = null;
            System.out.println("results:::" + results.size());
            return results;
        } catch (Exception e) {
            System.out.println("Exception:" + e);
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
                    System.out.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }
    }

    public static JSONObject quoteToJson(Quote1 q) throws Exception {
        JSONObject jo = new JSONObject();
        //jo.put("quoteNumber", q.getNumber());
        jo.put("quoteNumber", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:openQuoteWindow('" + q.getQuote1Id() + "','" + q.getNumber() + "')\">" + q.getNumber() + "</a>");
        if (q.getStatus().equalsIgnoreCase("pending")) {
            jo.put("projectNumber", "");
        } else {
            jo.put("projectNumber", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:openProjectWindow('" + q.getProject().getProjectId() + "','" + q.getProject().getNumber() + q.getProject().getCompany().getCompany_code() + "')\">" + q.getProject().getNumber() + q.getProject().getCompany().getCompany_code() + "</a>");
        }
        try {
            jo.put("approvalTime", StandardCode.getInstance().noNull(q.getApprovalTimeEsimate()));
        } catch (Exception e) {
        }
       
        try {  QuotePriority qp = QuoteService.getInstance().getSingleQuotePriority(q.getQuote1Id());
                jo.put("quoteStatus", ""+qp.getUrgency());
            }catch(Exception e){}
        
        
        User enteredByUser = UserService.getInstance().getSingleUser(q.getEnteredById());
        //jo.put("client", q.getProject().getCompany().getCompany_name());
        jo.put("client", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:openClientWindowReverse('" + HrHelper.jsSafe(q.getProject().getCompany().getCompany_name()) + "','" + q.getProject().getCompany().getClientId() + "')\">" + q.getProject().getCompany().getCompany_name() + "</a>");
        jo.put("srcApp", q.getProject().getSourceApplication());
        jo.put("description", q.getProject().getProductDescription());
        jo.put("product", q.getProject().getProduct());
        jo.put("quoteDate", q.getQuoteDate());
        jo.put("enteredBy", enteredByUser.getFirstName() + " " + enteredByUser.getLastName());
        jo.put("subquotes", q.getSubquotes());
        String targets = "";
        String fullTargets = "";
        HashMap alreadyAdded = new HashMap();
        try {
            for (Iterator iterSources = q.getSourceDocs().iterator(); iterSources.hasNext();) {
                SourceDoc sd = (SourceDoc) iterSources.next();
                for (Iterator iterTargets = sd.getTargetDocs().iterator(); iterTargets.hasNext();) {
                    TargetDoc td = (TargetDoc) iterTargets.next();
                    for (Iterator iterLintasks = td.getLinTasks().iterator(); iterLintasks.hasNext();) {
                        LinTask lt = (LinTask) iterLintasks.next();
                        if (!"".equalsIgnoreCase(lt.getTargetLanguage())) {
                            String srcLang = lt.getTargetLanguage();
                            String abr = (String) LanguageAbs.getInstance().getAbs().get(srcLang);
                            //Also prevent duplicates
                            if (abr != null && !"".equals(srcLang) && !"".equals(abr) && alreadyAdded.get(abr) == null) {
                                targets += abr + ", ";
                                fullTargets += lt.getTargetLanguage() + ", ";
                                alreadyAdded.put(abr, abr);
                            } else if (abr == null && !"".equals(srcLang) && !"".equals(abr) && alreadyAdded.get(srcLang) == null) {
                                targets += srcLang + ", ";
                                fullTargets += lt.getTargetLanguage() + ", ";
                                alreadyAdded.put(srcLang, srcLang);
                            }

                        }
                    }
                }
            }

            if (targets.endsWith(", ")) {
                targets = targets.substring(0, targets.length() - 2);
            }
            if (fullTargets.endsWith(", ")) {
                fullTargets = fullTargets.substring(0, fullTargets.length() - 2);
            }

            jo.put("targets", targets);
            jo.put("fullTargets", fullTargets);
        } catch (Exception e) {
        }
        if (q.getQuoteDollarTotal() != null) {
            jo.put("fee", StandardCode.getInstance().formatDouble(q.getQuoteDollarTotal()));
        }


        return jo;
    }

    public static JSONObject QuoteToJson2(Quote1 q) throws Exception {


        try {
            JSONObject jo = new JSONObject();
            //jo.put("projectNumber",p.getNumber()+p.getCompany().getCompany_code());
            Project p = q.getProject();
            // Product pr=p.get
            if (p != null) {
                jo.put("projectNumber", "<a " + HrHelper.LINK_STYLE + " href='../projectViewOverview.do?projectViewId=" + p.getProjectId() + "'>" + p.getNumber() + p.getCompany().getCompany_code() + "</a>");
                jo.put("AE", p.getCompany().getSales_rep());
                jo.put("projectDescription", p.getProductDescription());
                jo.put("product", p.getProduct());
                jo.put("medical", p.getProduct());

                String targets = "";
                String sources = "";
                HashMap alreadyAdded = new HashMap();

                for (Iterator iterSources = p.getSourceDocs().iterator(); iterSources.hasNext();) {
                    SourceDoc sd = (SourceDoc) iterSources.next();
                    sources += (String) LanguageAbs.getInstance().getAbs().get(sd.getLanguage());
                    if (iterSources.hasNext()) {

                        sources += ", ";
                    }
                    for (Iterator iterTargets = sd.getTargetDocs().iterator(); iterTargets.hasNext();) {
                        TargetDoc td = (TargetDoc) iterTargets.next();
                        for (Iterator iterLintasks = td.getLinTasks().iterator(); iterLintasks.hasNext();) {
                            LinTask lt = (LinTask) iterLintasks.next();
                            if (!"".equalsIgnoreCase(lt.getTargetLanguage())) {
                                String srcLang = lt.getTargetLanguage();

                                String abr = (String) LanguageAbs.getInstance().getAbs().get(srcLang);
                                //Also prevent duplicates
                                if (abr != null && !"".equals(srcLang) && !"".equals(abr) && alreadyAdded.get(abr) == null) {
                                    targets += abr + ", ";
                                    alreadyAdded.put(abr, abr);
                                } else if (abr == null && !"".equals(srcLang) && !"".equals(abr) && alreadyAdded.get(srcLang) == null) {
                                    targets += srcLang + ", ";
                                    alreadyAdded.put(srcLang, srcLang);
                                }

                            }
                        }
                    }
                    if (targets.endsWith(", ")) {
                        targets = targets.substring(0, targets.length() - 2);
                    }
                }

                try {
                    if (sources.equals("") || q.getStatus().equalsIgnoreCase("pending")) {
                        for (Iterator iterSources = q.getSourceDocs().iterator(); iterSources.hasNext();) {
                            SourceDoc sd = (SourceDoc) iterSources.next();
                            sources += (String) LanguageAbs.getInstance().getAbs().get(sd.getLanguage());
                            if (iterSources.hasNext()) {

                                sources += ", ";
                            }
                            for (Iterator iterTargets = sd.getTargetDocs().iterator(); iterTargets.hasNext();) {
                                TargetDoc td = (TargetDoc) iterTargets.next();
                                for (Iterator iterLintasks = td.getLinTasks().iterator(); iterLintasks.hasNext();) {
                                    LinTask lt = (LinTask) iterLintasks.next();
                                    if (!"".equalsIgnoreCase(lt.getTargetLanguage())) {
                                        String srcLang = lt.getTargetLanguage();

                                        String abr = (String) LanguageAbs.getInstance().getAbs().get(srcLang);
                                        //Also prevent duplicates
                                        if (abr != null && !"".equals(srcLang) && !"".equals(abr) && alreadyAdded.get(abr) == null) {
                                            targets += abr + ", ";
                                            alreadyAdded.put(abr, abr);
                                        } else if (abr == null && !"".equals(srcLang) && !"".equals(abr) && alreadyAdded.get(srcLang) == null) {
                                            targets += srcLang + ", ";
                                            alreadyAdded.put(srcLang, srcLang);
                                        }

                                    }
                                }
                            }
                            if (targets.endsWith(", ")) {
                                targets = targets.substring(0, targets.length() - 2);
                            }
                        }
                    }
                } catch (Exception e) {
                }
                jo.put("SourceLangs", sources);
                jo.put("TargetLangs", targets);

                if (p.getContact() != null) {
                    //jo.put("ClientContact","<a "+HrHelper.LINK_STYLE+" href=../clientContactEdit.do?clientContactId=" + p.getContact().getClientContactId() + ">" + p.getContact().getFirst_name()+" " + p.getContact().getLast_name()+"</a>");
                    jo.put("ClientContact", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openSingleContactWindow('" + p.getContact().getLast_name() + ", " + p.getContact().getFirst_name() + "','" + p.getContact().getClientContactId() + "')\">" + p.getContact().getLast_name() + ", " + p.getContact().getFirst_name() + "</a>");

                }

            }

            if (q.getQuoteDate() != null) {
                jo.put("QuoteDate", q.getQuoteDate());
                jo.put("year", q.getQuoteDate().getYear() + 1900);
            }

            jo.put("Status", q.getStatus());
            jo.put("QuoteAmount", q.getQuoteDollarTotal());
            jo.put("QuoteNumber", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openSingleQuoteWindow('" + q.getQuote1Id() + "','" + StandardCode.getInstance().noNull(q.getNumber()) + "')\">" + StandardCode.getInstance().noNull(q.getNumber()) + "</a>");



            jo.put("newQuoteShallow", "<input type='button' class='x-btn' value='Copy' onclick=\"javascript:createNewQuote('" + q.getQuote1Id() + "')\">");
            jo.put("newQuoteDeep", "<input type='button' value='Copy' class='x-btn' onclick=\"javascript:createNewQuoteDeepCopy('" + q.getQuote1Id() + "')\">");




            return jo;
        } catch (Exception e) {
            System.out.println("Problem processing quote:" + q.getNumber());
        }
        return new JSONObject();
    }

    public static JSONObject ClientQuoteToJson2(Quote1 q) throws Exception {


        try {

            //jo.put("projectNumber",p.getNumber()+p.getCompany().getCompany_code());

            String medical = "";
            String product = "";
            String category = "";
            String desc = "";
            String clientTask = "";
            String lineOfText ="";

            Project p = q.getProject();
            List cq = null;

            try {
                cq = QuoteService.getInstance().getSingleClientQuote(q.getQuote1Id());


                for (int ii = 0; ii < cq.size(); ii++) {
                    System.out.println("Sizeeeeeeeeeeeeeeeeeeeee" + cq.size());
                    // SourceDoc sd = (SourceDoc) iterSources.next();
                    Client_Quote newQA = (Client_Quote) cq.get(ii);
                    Product prod = ClientService.getInstance().getSingleProduct(newQA.getProduct_ID());
                    product += prod.getProduct();
                    medical += newQA.getMedical();
                    category += prod.getCategory();
                    if (p.getCompany().getClientId()==100) {
                         lineOfText += StandardCode.getInstance().noNull(newQA.getProductText());
                         desc +=StandardCode.getInstance().noNull(newQA.getType());
                    }else{
                     desc += prod.getDescription();
                    }
                    
                    desc += prod.getDescription();
                    clientTask += StandardCode.getInstance().noNull(newQA.getClientTask()) + StandardCode.getInstance().noNull(newQA.getOtherTask());
                    //if(iterSources.hasNext()){
                    if (ii != cq.size() - 1) {
                        medical += ", ";
                        product += ", ";
                        category += ", ";
                        desc += ", ";
                        clientTask += ", ";
                        lineOfText += ", ";
                    }

                }
                System.out.println("getQuote1IdgetQuote1IdgetQuote1IdgetQuote1IdgetQuote1Id" + q.getQuote1Id());
                System.out.println("getClient_QuotegetClient_Quote   size" + cq.size());

            } catch (Exception e) {

                medical = "";
                product = "";
                category = "";
                desc = "";

            }




            try {
                System.out.println(medical + product + category);
            } catch (Exception e) {
                System.out.println("error here");
            }
            JSONObject jo = new JSONObject();
            ///    Client_Quote cq=QuoteService.getInstance().getSingleClient_Quote(q.getQuote1Id());
            if (p != null) {
                jo.put("projectNumber", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openSingleClientProjectWindow('" + p.getProjectId() + "','" + StandardCode.getInstance().noNull(p.getNumber() + p.getCompany().getCompany_code()) + "')\">" + StandardCode.getInstance().noNull(p.getNumber() + p.getCompany().getCompany_code()) + "</a>");
                jo.put("AE", p.getCompany().getSales_rep());
                try {
                    jo.put("clientFee", StandardCode.getInstance().formatDouble(q.getQuoteDollarTotal()));
                } catch (Exception e) {
                }

                if (product != "") {

                    jo.put("product", product);
                   
                    jo.put("projectDescription", desc);
                    jo.put("lineOfText", lineOfText);
                    jo.put("medicalDevice", medical);
                    jo.put("Category", category);

                    // jo.put("projectDescription",p.getProductDescription());
                    //      jo.put("product",p.getProduct());
                } else {
                    try {
                        jo.put("projectDescription", StandardCode.getInstance().noNull(p.getProductDescription()));
                        jo.put("product", StandardCode.getInstance().noNull(p.getProduct()));
                    } catch (Exception e) {
                        System.out.println("error here");
                    }
                    //   jo.put("product",product);
                    //    jo.put("projectDescription",desc);
                    //   jo.put("medicalDevice",medical);
                    //    jo.put("Category",category);
                }
                int idTask1 = 0;
                String targets = "";
                String sources = "";
                String task1 = "";
                HashMap alreadyAdded = new HashMap();
//try{Iterator iterSources = p.getSourceDocs().iterator();}catch(Exception ){Iterator iterSources = q.getSourceDocs().iterator();}
                List sourcelang1 = QuoteService.getInstance().getSourceLang1(q);


                for (int ii = 0; ii < sourcelang1.size(); ii++) {
                    SourceDoc sd = (SourceDoc) sourcelang1.get(ii);
                    sources += (String) LanguageAbs.getInstance().getAbs().get(sd.getLanguage());
                    if (ii != sourcelang1.size() - 1) {
                        sources += ", ";
                    }
                    for (Iterator iterTargets = sd.getTargetDocs().iterator(); iterTargets.hasNext();) {
                        TargetDoc td = (TargetDoc) iterTargets.next();
                        for (Iterator iterLintasks = td.getLinTasks().iterator(); iterLintasks.hasNext();) {
                            LinTask lt = (LinTask) iterLintasks.next();
                            if (!"".equalsIgnoreCase(lt.getTargetLanguage())) {
                                String srcLang = lt.getTargetLanguage();
                                // String srcLang = td.getLanguage();
                                String abr = (String) LanguageAbs.getInstance().getAbs().get(srcLang);
                                //Also prevent duplicates
                                if (abr != null && !"".equals(srcLang) && !"".equals(abr) && alreadyAdded.get(abr) == null) {
                                    targets += abr + ", ";
                                    alreadyAdded.put(abr, abr);
                                } else if (abr == null && !"".equals(srcLang) && !"".equals(abr) && alreadyAdded.get(srcLang) == null) {
                                    targets += srcLang + ", ";
                                    alreadyAdded.put(srcLang, srcLang);
                                }

                            }
                        }
                        idTask1 = td.getTargetDocId();
                    }
                    if (targets.endsWith(", ")) {
                        targets = targets.substring(0, targets.length() - 2);
                    }
                }


                List linTaskList = QuoteService.getInstance().getLinTask(idTask1);
                List engTaskList = QuoteService.getInstance().getEnggTask(idTask1);
                List forTaskList = QuoteService.getInstance().getFormatTask(idTask1);
                List otherTaskList = QuoteService.getInstance().getOtherTask(idTask1);
                if (linTaskList.size() == 0 && engTaskList.size() == 0 && forTaskList.size() == 0 && otherTaskList.size() == 0) {


                    jo.put("Task", clientTask);

                } else {

                    for (int ll = 0; ll < linTaskList.size(); ll++) {
                        LinTask lt = (LinTask) linTaskList.get(ll);
                        if (alreadyAdded.get(lt.getTaskName()) == null) {

                            if (task1 == null ? "" != null : !task1.equals("")) {
                                task1 += ", ";
                            }
                            task1 += lt.getTaskName();
                        }


                    }
                    for (int ll = 0; ll < engTaskList.size(); ll++) {
                        EngTask lt = (EngTask) engTaskList.get(ll);
                        if (alreadyAdded.get(lt.getTaskName()) == null) {
                            if (task1 == null ? "" != null : !task1.equals("")) {
                                task1 += ", ";
                            }
                            task1 += lt.getTaskName();
                        }


                    }
                    for (int ll = 0; ll < forTaskList.size(); ll++) {
                        DtpTask lt = (DtpTask) forTaskList.get(ll);
                        if (alreadyAdded.get(lt.getTaskName()) == null) {
                            if (task1 != "") {
                                task1 += ", ";
                            }
                            task1 += lt.getTaskName();
                        }


                    }
                    for (int ll = 0; ll < otherTaskList.size(); ll++) {

                        OthTask lt = (OthTask) otherTaskList.get(ll);
                        if (alreadyAdded.get(lt.getTaskName()) == null) {
                            if (task1 != "") {
                                task1 += ", ";
                            }
                            task1 += lt.getTaskName();
                        }


                    }
                    jo.put("Task", task1);
                }

                jo.put("SourceLangs", sources);
                jo.put("TargetLangs", targets);


                if (p.getContact() != null) {
                    //jo.put("ClientContact","<a "+HrHelper.LINK_STYLE+" href=../clientContactEdit.do?clientContactId=" + p.getContact().getClientContactId() + ">" + p.getContact().getFirst_name()+" " + p.getContact().getLast_name()+"</a>");
                    jo.put("ClientContact", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openSingleContactWindow('" + p.getContact().getLast_name() + ", " + p.getContact().getFirst_name() + "','" + p.getContact().getClientContactId() + "')\">" + p.getContact().getLast_name() + ", " + p.getContact().getFirst_name() + "</a>");

                }

            }
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date = new Date();
            String dateString = dateFormat.format(date);
            try {
                jo.put("ApprovalDate", dateFormat.format(q.getApprovalDate()));
            } catch (Exception e) {
            }
            if (q.getQuoteDate() != null) {
                jo.put("QuoteDate", dateFormat.format(q.getQuoteDate()));
                jo.put("year", q.getQuoteDate().getYear() + 1900);
            }
            System.out.println("======================================================>" + q.getQuoteDate().getYear() + 1900 + "   " + q.getNumber());


            try {
                jo.put("publishDate", dateFormat.format(q.getPublishDate()));
                jo.put("publishedBy", q.getPublishBy());
            } catch (Exception e) {
            }

            try {

                jo.put("RejectionDate", q.getApprovalDate());
                jo.put("RejectReason", StandardCode.getInstance().noNull(q.getRejectReason()));
            } catch (Exception e) {
                jo.put("RejectReason", q.getRejectReason());
            }

            jo.put("Status", q.getStatus());
            jo.put("QuoteAmount", q.getQuoteDollarTotal());
            jo.put("approvalTimeEsimate", q.getApprovalTimeEsimate());
            if (q.getPublish() == null || q.getPublish() == true) {
                jo.put("QuoteNumber", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openSingleClientPublishQuoteWindow('" + q.getQuote1Id() + "','" + StandardCode.getInstance().noNull(q.getNumber()) + "')\">" + StandardCode.getInstance().noNull(q.getNumber()) + "</a>");
                jo.put("approvebtn", "<input type='button' class='x-btn' value='Approve' onclick=\"if(confirm('Please proceed with this project. I accept the estimated terms, conditions, and price as described in this Proposal. I acknowledge that the terms and conditions in this Proposal prevail over any purchase order or other document my company may wish to issue."
                        + "Furthermore I acknowledge that this document represents the entire agreement made by and between Excel Translations and my organization. Any changes to this Agreement with respect to fees, payment or performance schedules, or any reduction or increase in service, must be agreed to in writing by both parties."
                        + "'))javascript:approvebutton('" + q.getQuote1Id() + "')\">");
                jo.put("rejectbtn", "<input type='button' value='Reject' class='x-btn' onclick=\"javascript:rejectbutton('" + q.getQuote1Id() + "')\">");

            } else {
                //          q.getQuote1Id() + "','"+StandardCode.getInstance().noNull(q.getNumber())   ('" + q.getQuote1Id() + "','"+StandardCode.getInstance().noNull(q.getNumber())+"')\">"
                jo.put("QuoteNumber", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openSingleClientQuoteWindow('" + q.getQuote1Id() + "','" + StandardCode.getInstance().noNull(q.getNumber()) + "')\">" + StandardCode.getInstance().noNull(q.getNumber()) + "</a>");
                //jo.put("QuoteNumber","<a "+HrHelper.LINK_STYLE+" href=\"javascript:parent.openSingleClientQuoteWindow('" + q.getQuote1Id() + "','"+StandardCode.getInstance().noNull(q.getNumber())+"')\">" + StandardCode.getInstance().noNull(q.getNumber()) + "</a>");
            } //  jo.put("QuoteNumber",StandardCode.getInstance().noNull(q.getNumber()) + "</a>");

//String QuoteID=""+q.getQuote1Id();
            jo.put("deletebtn", "<input type='button' class='x-btn' value='Delete' onclick=\"javascript:deletebutton('" + q.getQuote1Id() + "')\">");

            jo.put("newQuoteShallow", "<input type='button' class='x-btn' value='Copy' onclick=\"javascript:createNewQuote('" + q.getQuote1Id() + "')\">");
            jo.put("newQuoteDeep", "<input type='button' value='Copy' class='x-btn' onclick=\"javascript:createNewQuoteDeepCopy('" + q.getQuote1Id() + "')\">");

            // System.out.println("Quote IDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD"+q.getQuote1Id());
//?resourceViewId="+resourceViewId


            System.out.println(q.getQuoteDate().getYear() + 1900);
            return jo;
        } catch (Exception e) {
            System.out.println("Problem processing quote:" + q.getNumber());
        }
        return new JSONObject();
    }

    //get all active projects
    public static List getQuoteListForClient(String clientId) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             *///and (project.pm='"+pmName+"' or project.Company.Sales_rep='"+pmName+"') order by project.number

            query = session.createQuery("select quote from app.quote.Quote1 quote left join quote.Project.Company company where company.clientId='" + clientId + "' order by quote.number");
            List temp = query.list();


            //query = session.createQuery("select project from app.project.Project project where project.status = 'onhold' and project.Company.Backup_pm='"+pmName+"'");
            //temp.addAll(query.list());
            // List results = new ArrayList();

            // System.out.println("Found:::"+temp.size());
            for (int i = 0; i < temp.size(); i++) {
                Quote1 q = (Quote1) temp.get(i);
                // if(pmName.equals(p.getCompany().getBackup_pm()) && !pmName.equals(p.getCompany().getSales_rep()) && !pmName.equals(p.getPm())){
                Hibernate.initialize(q.getProject());
                Hibernate.initialize(q.getProject().getSourceDocs());
                Hibernate.initialize(q.getSourceDocs());

                // results.add(p);
                //}
            }
            //temp = null;
            return temp;
        } catch (HibernateException e) {
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
    
    //get all active projects
    public static List getQuoteListForClientPerYear(String clientId,String year) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        
         String sDate=year+"-01-01";
        String eDate=year+"-12-31";
        
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             *///and (project.pm='"+pmName+"' or project.Company.Sales_rep='"+pmName+"') order by project.number

            query = session.createQuery("select quote from app.quote.Quote1 quote left join quote.Project.Company company where company.clientId='" + clientId + "' and quote.quoteDate <'"+eDate+"' and quote.quoteDate >'"+sDate+"' order by quote.number");
            List temp = query.list();


            //query = session.createQuery("select project from app.project.Project project where project.status = 'onhold' and project.Company.Backup_pm='"+pmName+"'");
            //temp.addAll(query.list());
            // List results = new ArrayList();

            // System.out.println("Found:::"+temp.size());
            for (int i = 0; i < temp.size(); i++) {
                Quote1 q = (Quote1) temp.get(i);
                // if(pmName.equals(p.getCompany().getBackup_pm()) && !pmName.equals(p.getCompany().getSales_rep()) && !pmName.equals(p.getPm())){
                Hibernate.initialize(q.getProject());
                Hibernate.initialize(q.getProject().getSourceDocs());
                Hibernate.initialize(q.getSourceDocs());

                // results.add(p);
                //}
            }
            //temp = null;
            return temp;
        } catch (HibernateException e) {
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

    public static List getClientRejectQuoteListForClient(String clientId) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        Date date = new Date();
        String nd = dateFormat.format(date);
        try {
            //String status1="approved";
                        /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             *///and (project.pm='"+pmName+"' or project.Company.Sales_rep='"+pmName+"') order by project.number

            query = session.createQuery("select quote from app.quote.Quote1 quote left join quote.Project.Company company where quote.status = 'rejected' and (company.clientId='" + clientId + "')  order by number desc");
            //         query = session.createQuery("select quote from app.quote.Quote1 quote left join quote.Project.Company company where quote.status = 'pending' and (quote.Project.pm ='"+pmName+"' OR quote.Project.Company.Sales_rep ='"+pmName+"')");

            List temp = query.list();


            //query = session.createQuery("select project from app.project.Project project where project.status = 'onhold' and project.Company.Backup_pm='"+pmName+"'");
            //temp.addAll(query.list());
            // List results = new ArrayList();

            // System.out.println("Found:::"+temp.size());
            for (int i = 0; i < temp.size(); i++) {
                Quote1 q = (Quote1) temp.get(i);
                // if(pmName.equals(p.getCompany().getBackup_pm()) && !pmName.equals(p.getCompany().getSales_rep()) && !pmName.equals(p.getPm())){
                Hibernate.initialize(q.getProject());
                Hibernate.initialize(q.getProject().getSourceDocs());
                // results.add(p);
                //}
            }
            //temp = null;
            return temp;
        } catch (HibernateException e) {
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

    public static List getClientRejectQuoteListForClient(String clientId, String year) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        String sDate = year + "-01-01";
        String eDate = year + "-12-31";
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        Date date = new Date();
        String nd = dateFormat.format(date);
        try {
            //String status1="approved";
                        /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             *///and (project.pm='"+pmName+"' or project.Company.Sales_rep='"+pmName+"') order by project.number

            query = session.createQuery("select quote from app.quote.Quote1 quote left join quote.Project.Company company where quote.status = 'rejected' and (company.clientId='" + clientId + "') and quote.quoteDate <'" + eDate + "' and quote.quoteDate >'" + sDate + "'  order by number desc");
            //         query = session.createQuery("select quote from app.quote.Quote1 quote left join quote.Project.Company company where quote.status = 'pending' and (quote.Project.pm ='"+pmName+"' OR quote.Project.Company.Sales_rep ='"+pmName+"')");

            List temp = query.list();


            //query = session.createQuery("select project from app.project.Project project where project.status = 'onhold' and project.Company.Backup_pm='"+pmName+"'");
            //temp.addAll(query.list());
            // List results = new ArrayList();

            // System.out.println("Found:::"+temp.size());
            for (int i = 0; i < temp.size(); i++) {
                Quote1 q = (Quote1) temp.get(i);
                // if(pmName.equals(p.getCompany().getBackup_pm()) && !pmName.equals(p.getCompany().getSales_rep()) && !pmName.equals(p.getPm())){
                Hibernate.initialize(q.getProject());
                Hibernate.initialize(q.getProject().getSourceDocs());
                // results.add(p);
                //}
            }
            //temp = null;
            return temp;
        } catch (HibernateException e) {
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

    public static List getClientPendingQuoteListForClient(String clientId) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;

        try {
            //String status1="approved";
                        /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             *///and (project.pm='"+pmName+"' or project.Company.Sales_rep='"+pmName+"') order by project.number
            System.out.println("Beforrrrrrrrrrrrrrrrrrrrrrrrrrrr");
            query = session.createQuery("select quote from app.quote.Quote1 quote left join  quote.Project.Company company where quote.status = 'pending' and (company.clientId='" + clientId + "')order by number desc");
            //      query = session.createQuery("select quote from app.quote.Quote1 quote left join quote.Project.Company company where quote.Project.projectId=7148 and quote.status = 'pending' and (company.clientId='"+clientId+"')order by number desc");
            //         query = session.createQuery("select quote from app.quote.Quote1 quote left join quote.Project.Company company where quote.status = 'pending' and (quote.Project.pm ='"+pmName+"' OR quote.Project.Company.Sales_rep ='"+pmName+"')");

            List temp = query.list();


            //query = session.createQuery("select project from app.project.Project project where project.status = 'onhold' and project.Company.Backup_pm='"+pmName+"'");
            //temp.addAll(query.list());
            // List results = new ArrayList();

            // System.out.println("Found:::"+temp.size());
            for (int i = 0; i < temp.size(); i++) {
                Quote1 q = (Quote1) temp.get(i);
                // if(pmName.equals(p.getCompany().getBackup_pm()) && !pmName.equals(p.getCompany().getSales_rep()) && !pmName.equals(p.getPm())){
                Hibernate.initialize(q.getProject());
                try {
                    Hibernate.initialize(q.getProject().getSourceDocs());
                } catch (Exception e) {
                }
                // results.add(p);
                //}
            }
            //temp = null;
            return temp;
        } catch (HibernateException e) {
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

    public static List getClientPendingQuoteListForClient(String clientId, String year) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        String sDate = year + "-01-01";
        String eDate = year + "-12-31";
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;

        try {
            //String status1="approved";
                        /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             *///and (project.pm='"+pmName+"' or project.Company.Sales_rep='"+pmName+"') order by project.number
            System.out.println("Beforrrrrrrrrrrrrrrrrrrrrrrrrrrr");

            query = session.createQuery("select quote from app.quote.Quote1 quote left join  quote.Project.Company company where quote.status = 'pending' and quote.quoteDate <'" + eDate + "' and quote.quoteDate >'" + sDate + "' and  (company.clientId='" + clientId + "')order by number desc");
            //      query = session.createQuery("select quote from app.quote.Quote1 quote left join quote.Project.Company company where quote.Project.projectId=7148 and quote.status = 'pending' and (company.clientId='"+clientId+"')order by number desc");
            //         query = session.createQuery("select quote from app.quote.Quote1 quote left join quote.Project.Company company where quote.status = 'pending' and (quote.Project.pm ='"+pmName+"' OR quote.Project.Company.Sales_rep ='"+pmName+"')");

            List temp = query.list();


            //query = session.createQuery("select project from app.project.Project project where project.status = 'onhold' and project.Company.Backup_pm='"+pmName+"'");
            //temp.addAll(query.list());
            // List results = new ArrayList();

            // System.out.println("Found:::"+temp.size());
            for (int i = 0; i < temp.size(); i++) {
                Quote1 q = (Quote1) temp.get(i);
                // if(pmName.equals(p.getCompany().getBackup_pm()) && !pmName.equals(p.getCompany().getSales_rep()) && !pmName.equals(p.getPm())){
                Hibernate.initialize(q.getProject());
                try {
                    Hibernate.initialize(q.getProject().getSourceDocs());
                } catch (Exception e) {
                }
                // results.add(p);
                //}
            }
            //temp = null;
            return temp;
        } catch (HibernateException e) {
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

    public static List getClientQuoteListForClient(String clientId) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        Date date = new Date();
        String nd = dateFormat.format(date);
        try {
            //String status1="approved";
                        /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             *///and (project.pm='"+pmName+"' or project.Company.Sales_rep='"+pmName+"') order by project.number

            query = session.createQuery("select quote from app.quote.Quote1 quote left join quote.Project.Company company where quote.status = 'approved' and (company.clientId='" + clientId + "')  order by quote.number desc");
            //         query = session.createQuery("select quote from app.quote.Quote1 quote left join quote.Project.Company company where quote.status = 'pending' and (quote.Project.pm ='"+pmName+"' OR quote.Project.Company.Sales_rep ='"+pmName+"')");

            List temp = query.list();


            //query = session.createQuery("select project from app.project.Project project where project.status = 'onhold' and project.Company.Backup_pm='"+pmName+"'");
            //temp.addAll(query.list());
            // List results = new ArrayList();

            // System.out.println("Found:::"+temp.size());
            for (int i = 0; i < temp.size(); i++) {
                Quote1 q = (Quote1) temp.get(i);
                // if(pmName.equals(p.getCompany().getBackup_pm()) && !pmName.equals(p.getCompany().getSales_rep()) && !pmName.equals(p.getPm())){
                try {
                    Hibernate.initialize(q.getProject());
                    Hibernate.initialize(q.getProject().getSourceDocs());
                } catch (Exception e) {
                }
                // results.add(p);
                //}
            }
            //temp = null;
            return temp;
        } catch (HibernateException e) {
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

    public static List getClientQuoteListForClient(String clientId, String year) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        String sDate = year + "-01-01";
        String eDate = year + "-12-31";
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        Date date = new Date();
        String nd = dateFormat.format(date);
        try {
            //String status1="approved";
                        /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             *///and (project.pm='"+pmName+"' or project.Company.Sales_rep='"+pmName+"') order by project.number

            query = session.createQuery("select quote from app.quote.Quote1 quote left join quote.Project.Company company where quote.status = 'approved' and quote.quoteDate <'" + eDate + "' and quote.quoteDate >'" + sDate + "'  and (company.clientId='" + clientId + "') order by quote.number desc");
            //         query = session.createQuery("select quote from app.quote.Quote1 quote left join quote.Project.Company company where quote.status = 'pending' and (quote.Project.pm ='"+pmName+"' OR quote.Project.Company.Sales_rep ='"+pmName+"')");

            List temp = query.list();


            //query = session.createQuery("select project from app.project.Project project where project.status = 'onhold' and project.Company.Backup_pm='"+pmName+"'");
            //temp.addAll(query.list());
            // List results = new ArrayList();

            // System.out.println("Found:::"+temp.size());
            for (int i = 0; i < temp.size(); i++) {
                Quote1 q = (Quote1) temp.get(i);
                // if(pmName.equals(p.getCompany().getBackup_pm()) && !pmName.equals(p.getCompany().getSales_rep()) && !pmName.equals(p.getPm())){
                try {
                    Hibernate.initialize(q.getProject());
                    Hibernate.initialize(q.getProject().getSourceDocs());
                } catch (Exception e) {
                }
                // results.add(p);
                //}
            }
            //temp = null;
            return temp;
        } catch (HibernateException e) {
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

    public static List getClientActiveQuoteListForClient(String clientId) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;

        try {
            //String status1="approved";
                        /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             *///and (project.pm='"+pmName+"' or project.Company.Sales_rep='"+pmName+"') order by project.number

            query = session.createQuery("select quote from app.quote.Quote1 quote left join quote.Project.Company company where quote.status = 'approved' and (company.clientId='" + clientId + "')");
            //         query = session.createQuery("select quote from app.quote.Quote1 quote left join quote.Project.Company company where quote.status = 'pending' and (quote.Project.pm ='"+pmName+"' OR quote.Project.Company.Sales_rep ='"+pmName+"')");

            List temp = query.list();


            //query = session.createQuery("select project from app.project.Project project where project.status = 'onhold' and project.Company.Backup_pm='"+pmName+"'");
            //temp.addAll(query.list());
            // List results = new ArrayList();

            // System.out.println("Found:::"+temp.size());
            for (int i = 0; i < temp.size(); i++) {
                Quote1 q = (Quote1) temp.get(i);
                // if(pmName.equals(p.getCompany().getBackup_pm()) && !pmName.equals(p.getCompany().getSales_rep()) && !pmName.equals(p.getPm())){
                Hibernate.initialize(q.getProject());
                Hibernate.initialize(q.getProject().getSourceDocs());
                // results.add(p);
                //}
            }
            //temp = null;
            return temp;
        } catch (HibernateException e) {
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

    public static String generateQuoteHeaderHtml(Quote1 q) {
        Project p = q.getProject();
        String publishStatus;
        //  Client c=ClientService.getInstance().getSingleClient(id);
        try {
            if (q.getPublish() == false && q.getPublish() != null) {
                publishStatus = "Excel Translations is analyzing and preparing your quote";
            } else {
                publishStatus = q.getStatus();
            }
        } catch (Exception e) {
            publishStatus = q.getStatus();
        }
        String results = "<table border='0' width='75%'>";
        results += "<tr><td></td>"
                + "<td colspan=6 align='center'><a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openClientWindowReverse('" + HrHelper.jsSafe(q.getProject().getCompany().getCompany_name()) + "','" + q.getProject().getCompany().getClientId() + "')\"><font size='4' color='blue'>" + p.getCompany().getCompany_name() + "</font></a></td></tr>";
        // System.out.println("qqqqqqqqqqqqqqqqqqqqqq"+q.getNumber());

        results += "<tr><td align='right'><font size='2'><b>Quote Number:</td><td align='left'>" + q.getNumber() + "</td>"
                + "<td align='right'><font size='2' ><b> Status:</td><td align='left'>" + publishStatus + "</td>"
                + "<td align='right'><font size='2' ><b> Archive ID:</td><td align='left'>" + StandardCode.getInstance().noNull(q.getArchiveId()) + "</td></tr>";
        results += "</table><hr>";
        try {        //if(q.getEnteredById()!=null){
            User enteredByUser = UserService.getInstance().getSingleUser(q.getEnteredById());
            User modifiedByUser = UserService.getInstance().getSingleUser(q.getLastModifiedById());
//User approvedByUser = UserService.getInstance().getSingleUser(q.getA)
            results += "<table><tr><td align='right'>Quote Entered By :    </td><td>" + enteredByUser.getFirstName() + " " + enteredByUser.getLastName() + "</td><td>on   </td><td align='left'>" + q.getEnteredByTS() + "</td></tr>"
                    + "<tr><td align='right'>Quote Modified By :   </td><td>" + modifiedByUser.getFirstName() + " " + modifiedByUser.getLastName() + "</td><td>on   </td><td align='left'>" + q.getLastModifiedByTS() + "</td></tr>";
            if (q.getStatus().equalsIgnoreCase("pending")) {
            } else {

                results += "<tr><td align='right'>Quote Approved By :   </td><td>" + modifiedByUser.getFirstName() + " " + modifiedByUser.getLastName() + "</td><td>on   </td><td align='left'>" + q.getApprovedTS() + "</td></tr>";
            }
            results += "</table><hr>";

        } catch (Exception e) {

            System.out.println("Errorrrrrrrrrrrrrrrrrrrrrrrrr");
        }
        return results;


    }

    public static String generateClient_QuoteHeaderHtml(Quote1 q, Integer id) {
        // Project p = q.getProject();
        // User u=UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
        //  User u=UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
        Client c = ClientService.getInstance().getSingleClient(id);
        Project p = q.getProject();
        String results = "<table border='0' width='75%'>";
        results += "<tr><td></td>"
                + "<td colspan=6 align='center'><font size='4' color='blue'>" + c.getCompany_name() + "</td></tr>";


        results += "<tr><td align='right'><font size='2'><b>Quote Number:</td><td align='left'>" + q.getNumber() + "</td>"
                + "<td align='right'><font size='2' ><b>PM:</td><td align='left'>" + c.getProject_mngr() + "</td>"
                + "<td align='right'><font size='2' ><b>Status:</td><td align='left'> pending </td></tr>";



        results += "</table><hr>";



        return results;


    }

    public static String searchRates(String srcLanguage, String targetLanguage, String taskName, String client) {
        //get resource to edit
        Session session = ConnectionFactory.getInstance().getSession();


        String result = "";
        //3 columns
        String clientQuery = "";
        if (client != null && !"".equals(client)) {
            clientQuery = " and company_name=? ";
        }
        String taskQuery1 = "";
        String taskQuery2 = "";
        String taskQuery3 = "";
        if (taskName != null && !"".equals(taskName)) {
            taskQuery1 = " and lintask.taskname=? ";
            taskQuery2 = " and engtask.taskname=? ";
            taskQuery3 = " and dtptask.taskname=? ";
        }



        try {

            //GET OSA SCORE and # OF PROJECTS FIRST
            PreparedStatement st = session.connection().prepareStatement(
                    " select id_lintask as taskId, rateFee, unitsFee, currencyFee, deliveryDate, taskName, pm, project.number, project.id_project, company_code, company_name, project.id_project, 'LIN' as taskType "
                    + " from lintask, targetdoc, sourcedoc, project, client_information "
                    + " where  "
                    + " lintask.sourceLanguage=? and lintask.targetLanguage=? " + taskQuery1
                    + " and lintask.id_targetDoc=targetdoc.id_targetDoc "
                    + " and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc  "
                    + " and sourcedoc.id_project = project.id_project  "
                    + " and project.id_client = client_information.id_client and rateFee>0 " + clientQuery
                    + " union  "
                    + " select id_dtptask as taskId, rate as rateFee, units as unitsFee, currency as currencyFee, deliveryDate, taskName, pm, project.number, project.id_project, company_code, company_name, project.id_project, 'DTP' as taskType "
                    + " from dtptask, targetdoc, sourcedoc, project, client_information "
                    + " where  "
                    + " dtptask.sourceLanguage=? and dtptask.targetLanguage=? " + taskQuery3
                    + " and dtptask.id_targetDoc=targetdoc.id_targetDoc "
                    + " and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc  "
                    + " and sourcedoc.id_project = project.id_project  "
                    + " and project.id_client = client_information.id_client and rate>0 " + clientQuery
                    + " union  "
                    + " select id_engtask as taskId, rate as rateFee, units as unitsFee, currency as currencyFee, deliveryDate, taskName, pm, project.number, project.id_project, company_code, company_name, project.id_project, 'DTP' as taskType "
                    + " from engtask, targetdoc, sourcedoc, project, client_information "
                    + " where  "
                    + " engtask.sourceLanguage=? and engtask.targetLanguage=? " + taskQuery2
                    + " and engtask.id_targetDoc=targetdoc.id_targetDoc "
                    + " and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc  "
                    + " and sourcedoc.id_project = project.id_project  "
                    + " and project.id_client = client_information.id_client and rate>0 " + clientQuery
                    + " order by taskType desc, deliveryDate desc, company_name, rateFee desc   ");


//        if(taskName==null && "".equals(taskName)){
//        if(client==null || "".equals(client)){
//            st.setString(1, srcLanguage);
//            st.setString(2, targetLanguage);
//            st.setString(3, srcLanguage);
//            st.setString(4, targetLanguage);
//            st.setString(5, srcLanguage);
//            st.setString(6, targetLanguage);
//        }else{
//            st.setString(1, srcLanguage);
//            st.setString(2, targetLanguage);
//            st.setString(3, client);
//            st.setString(4, srcLanguage);
//            st.setString(5, targetLanguage);
//            st.setString(6, client);
//            st.setString(7, srcLanguage);
//            st.setString(8, targetLanguage);
//            st.setString(9, client);
//
//        }}else{

            if (client == null || "".equals(client)) {
                st.setString(1, srcLanguage);
                st.setString(2, targetLanguage);
                st.setString(3, taskName);
                st.setString(4, srcLanguage);
                st.setString(5, targetLanguage);
                st.setString(6, taskName);
                st.setString(7, srcLanguage);
                st.setString(8, targetLanguage);
                st.setString(9, taskName);
            } else {
                st.setString(1, srcLanguage);
                st.setString(2, targetLanguage);
                st.setString(3, taskName);
                st.setString(4, client);
                st.setString(5, srcLanguage);
                st.setString(6, targetLanguage);
                st.setString(7, taskName);
                st.setString(8, client);
                st.setString(9, srcLanguage);
                st.setString(10, targetLanguage);
                st.setString(11, taskName);
                st.setString(12, client);

                //    }
            }
            ResultSet rs = st.executeQuery();
            result = "<table width='90%'><tr><td width='10%'><b>Type</td><td width='10%'><b>Project</td><td width='20%'><b>Client</td><td width='30%'><b>Task</td><td width='10%'><b>Rate</td><td width='10%'><b>Units</td><td width='10%'><b>Date</td></tr>";
            result += "<tr><td colspan=7><hr></td></tr>";
            boolean anyResults = false;

            while (rs.next()) {
                anyResults = true;
                String date = "";
                if (rs.getDate("deliveryDate") != null) {
                    date = sdf.format(rs.getDate("deliveryDate"));
                }
                result += "<tr><td>" + rs.getString("taskType") + "</td>"
                        + "<td>"
                        + "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openSingleProjectWindow('" + rs.getString("number") + rs.getString("company_code") + "','" + rs.getString("id_project") + "')\">" + rs.getString("number") + rs.getString("company_code") + "</a>"
                        + "</td><td>" + rs.getString("company_name") + "</td><td>" + rs.getString("taskName")
                        + "</td><td>" + rs.getString("rateFee") + " " + rs.getString("currencyFee") + "</td>"
                        + "<td>" + rs.getString("unitsFee") + "</td><td>" + date + "</td></tr>";
            }
            result += "</table>";
            if (!anyResults) {
                result += "<font color='red'>No rates found for this search criteria.</font>";

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

    public static Hashtable getBreakdownByLanguageTable(Quote1 q) {
        //get resource to edit
        Session session = ConnectionFactory.getInstance().getSession();


        Hashtable resultHt = new Hashtable();
        //3 columns

        int quoteId = q.getQuote1Id().intValue();




        try {

            //GET OSA SCORE and # OF PROJECTS FIRST
            PreparedStatement st = session.connection().prepareStatement(
                    "  select "
                    + "    sum(totalAmountLin) as totalLin, "
                    + "    sum(totalAmountDtp) as totalDtp, "
                    + "    sum(totalAmountEng) as totalEng, sum(totalAmountLin)+sum(totalAmountDtp)+sum(totalAmountEng) as myTotal, "
                    + "    languages "
                    + "  from "
                    + "   (  "
                    + "  SELECT * FROM( "
                    + " select  sum(replace(lintask.dollarTotal,',','')) as totalAmountLin, 0 as totalAmountDtp, 0 as totalAmountEng, "
                    + "         targetLanguage as languages "
                    + // "         CONCAT(sourceLanguage,' to ', targetLanguage) as languages "+
                    " 	from "
                    + "         lintask, targetdoc, sourcedoc, quote1 "
                    + " 	where "
                    + "             quote1.id_Quote1=? "
                    + "               and lintask.id_targetDoc=targetdoc.id_targetDoc "
                    + "               and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc "
                    + "               and sourcedoc.id_Quote1 = quote1.id_Quote1 "
                    + " 	group by languages "
                    + "   ) AS T1   "
                    + "   union   "
                    + "   select * from (   "
                    + " 	select  0 as totalAmountLin, sum(replace(dtptask.dollarTotal,',','')) as totalAmountDtp,0 as totalAmountEng, "
                    + "        targetLanguage as languages "
                    + " 	from "
                    + "         dtptask, targetdoc, sourcedoc, quote1 "
                    + " 	where "
                    + "             quote1.id_Quote1=? "
                    + "               and dtptask.id_targetDoc=targetdoc.id_targetDoc "
                    + "               and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc "
                    + "               and sourcedoc.id_Quote1 = quote1.id_Quote1 "
                    + " 	group by languages "
                    + "   ) AS T2   "
                    + "   union   "
                    + "   select * from (   "
                    + " 	select  0 as totalAmountLin,  0 as totalAmountDtp,  sum(replace(engtask.dollarTotal,',','')) as totalAmountEng, "
                    + "         targetLanguage as languages "
                    + " 	from "
                    + "         engtask, targetdoc, sourcedoc, quote1 "
                    + " 	where "
                    + "             quote1.id_Quote1=? "
                    + "               and engtask.id_targetDoc=targetdoc.id_targetDoc "
                    + "               and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc "
                    + "               and sourcedoc.id_Quote1 = quote1.id_Quote1 "
                    + " 	group by languages "
                    + "   ) AS T3   "
                    + "   ) AS MAINTABLE "
                    + " group by languages ");


            st.setInt(1, quoteId);
            st.setInt(2, quoteId);
            st.setInt(3, quoteId);
            ResultSet rs = st.executeQuery();
            double grandTotal = 0;
            String myTab = "          ";
            String engTab = "           ";
            String dtpTab = "   ";
            String linTab = "          ";

            /*String result = QuoteHelper.padMyString("LANGUAGE","RIGHT",45) +
             QuoteHelper.padMyString("LINGUISTIC","LEFT",15) +
             QuoteHelper.padMyString("DTP","LEFT",15) +
             QuoteHelper.padMyString("ENGINEERING","LEFT",15) +
             QuoteHelper.padMyString("TOTAL","LEFT",15);

             resultHt.put("INSERT_BREAKDOWN_1_INSERT", result);*/

            /*System.out.println(QuoteHelper.padMyString("LANGUAGE","RIGHT",45) +
             QuoteHelper.padMyString("LINGUISTIC","LEFT",15) +
             QuoteHelper.padMyString("DTP","LEFT",15) +
             QuoteHelper.padMyString("ENGINEERING","LEFT",15) +
             QuoteHelper.padMyString("TOTAL","LEFT",15));*/

            int counter = 1;
            while (rs.next()) {
                /* result+=rs.getString("languages")+myTab+myTab+myTab+rs.getDouble("totalLin")+
                 myTab+rs.getDouble("totalDtp")+myTab+rs.getDouble("totalEng")+
                 myTab+rs.getDouble("myTotal")+"\\\\line ";
                 grandTotal+=rs.getDouble("myTotal");*/

                /*  result =QuoteHelper.padMyString(rs.getString("languages"),"RIGHT",45) +
                 QuoteHelper.padMyString(rs.getDouble("totalLin")+"","LEFT",15) +
                 QuoteHelper.padMyString(rs.getDouble("totalDtp")+"","LEFT",15) +
                 QuoteHelper.padMyString(rs.getDouble("totalEng")+"","LEFT",15) +
                 QuoteHelper.padMyString(rs.getDouble("myTotal")+"","LEFT",15) ;  */


                String currentCounter = counter + "";
                if (counter < 10) {
                    currentCounter = "0" + currentCounter;
                }

                resultHt.put("bd_lang_" + currentCounter, rs.getString("languages") + "");
                resultHt.put("bd_lin_" + currentCounter, StandardCode.getInstance().formatDouble(new Double(rs.getDouble("totalLin"))));
                resultHt.put("bd_dtp_" + currentCounter, StandardCode.getInstance().formatDouble(new Double(rs.getDouble("totalDtp"))));
                resultHt.put("bd_eng_" + currentCounter, StandardCode.getInstance().formatDouble(new Double(rs.getDouble("totalEng"))));
                resultHt.put("bd_total_" + currentCounter, StandardCode.getInstance().formatDouble(new Double(rs.getDouble("myTotal"))));

                /*System.out.println(QuoteHelper.padMyString(rs.getString("languages"),"RIGHT",45) +
                 QuoteHelper.padMyString(rs.getDouble("totalLin")+"","LEFT",15) +
                 QuoteHelper.padMyString(rs.getDouble("totalDtp")+"","LEFT",15) +
                 QuoteHelper.padMyString(rs.getDouble("totalEng")+"","LEFT",15) +
                 QuoteHelper.padMyString(rs.getDouble("myTotal")+"","LEFT",15));*/

                grandTotal += rs.getDouble("myTotal");
                counter++;
            }
            st.close();

            //result+="-----------------------------------------------------------------------------------------\\\\line ";
            //result+="PROJECT MANAGEMENT"+myTab+myTab+myTab+myTab+q.getPmPercentDollarTotal()+"\\\\line ";
            resultHt.put("bd_subtotal", "\\$" + grandTotal + "");

            if (q.getPmPercentDollarTotal() != null) {
                grandTotal += Double.parseDouble(q.getPmPercentDollarTotal().replaceAll(",", ""));
                resultHt.put("bd_pm_fee", "\\$" + q.getPmPercentDollarTotal());
            }
            //result+="RUSH FEE"+myTab+myTab+myTab+myTab+myTab+myTab+q.getRushPercentDollarTotal()+"\\\\line ";

            if (q.getRushPercentDollarTotal() != null) {
                grandTotal += Double.parseDouble(q.getRushPercentDollarTotal().replaceAll(",", ""));
                resultHt.put("bd_rush_fee", "\\$" + q.getRushPercentDollarTotal());
            }
            //result+="DISCOUNT"+myTab+myTab+myTab+myTab+myTab+myTab+q.getDiscountDollarTotal()+"\\\\line ";

            if (q.getDiscountDollarTotal() != null) {
                grandTotal -= Double.parseDouble(q.getDiscountDollarTotal().replaceAll(",", ""));
                resultHt.put("bd_discount", "\\$" + q.getDiscountDollarTotal());
            } else {
                resultHt.put("bd_discount", "Not Applicable");
            }
            //result+="-----------------------------------------------------------------------------------------\\\\line ";

            //result+="GRAND TOTAL"+myTab+myTab+myTab+myTab+grandTotal+"\\\\line ";
            resultHt.put("bd_grand_total", "\\$" + StandardCode.getInstance().formatDouble(new Double(grandTotal)));

            return resultHt;
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

    public static String padMyString(String s, String sideToPad, int maxSize) {

        String result = s;
        if ("RIGHT".equals(sideToPad)) {
            for (int i = 0; i < maxSize - s.length(); i++) {
                result += " ";
            }
        } else {
            for (int i = 0; i < maxSize - s.length(); i++) {
                result = " " + result;
            }
        }

        return result;
    }

    public static boolean unlinkSourcesAndTargets(Quote1 q) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from sourcedoc where id_quote1=?");
            st.setInt(1, q.getQuote1Id().intValue());
            st.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            try {
                tx.rollback(); //error
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

        return t;
    }

    public static boolean clientUnlinkSourcesAndTargets(Quote1 q, int pid) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            System.out.println("PIDPIDPID" + pid);
            PreparedStatement st = session.connection().prepareStatement("delete from sourcedoc where id_quote1=? and Id_Client_Quote=" + pid);
            st.setInt(1, q.getQuote1Id().intValue());
            st.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            try {
                tx.rollback(); //error
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

        return t;
    }

    public static boolean deleteClientQuote(int q) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from sourcedoc where id_quote1=?");
            //st.setInt(1, q.getQuote1Id().intValue());
            st.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            try {
                tx.rollback(); //error
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

        return t;
    }
}
