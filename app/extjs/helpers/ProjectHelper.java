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

import java.util.*;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import app.db.*;
import app.extjs.global.LanguageAbs;
import app.project.*;
import app.quote.Client_Quote;
import app.quote.Quote1;
import app.quote.QuoteService;
import app.standardCode.ExcelConstants;
import app.standardCode.StandardCode;
import app.user.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.Color;
import java.io.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.jfree.chart.*;
import org.jfree.chart.entity.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.*;

/**
 *
 * @author Aleks
 */
public class ProjectHelper {

    private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    //get all active projects

//    public static List getProjectListForPM(String pmName) {
//        /*
//         * Use the ConnectionFactory to retrieve an open
//         * Hibernate Session.
//         *
//         */
//        Session session = ConnectionFactory.getInstance().getSession();
//        Query query;
//
//        try {
//            /*
//             * Build HQL (Hibernate Query Language) query to retrieve a list
//             * of all the items currently stored by Hibernate.
//             *///and (project.pm='"+pmName+"' or project.Company.Sales_rep='"+pmName+"') order by project.number
//            query = session.createQuery("select project from app.project.Project project left join fetch project.SourceDocs tds  left join project.Company company where  (project.status = 'active' or project.status = 'onhold') and (project.pm = '" + pmName + "' OR company.Sales_rep='" + pmName + "' OR company.Sales='" + pmName + "' ) order by project.number desc ");
//            List temp = query.list();
//            ////System.out.println("Found:::"+temp.size());
//            Hashtable duplicateProjects = new Hashtable();
//            for (ListIterator iter = temp.listIterator(); iter.hasNext();) {
//                Project p = (Project) iter.next();
//                if (duplicateProjects.get(p.getNumber()) != null || p.getNumber().equals("000000")) {
//                    iter.remove();
//                } else {
//                    duplicateProjects.put(p.getNumber(), p.getNumber());
//                }
//            }
//            //List results = new ArrayList();
//           /* for(int i=0; i<temp.size();i++){
//            Project p = (Project) temp.get(i);
//            //if(pmName.equals(p.getPm()) || pmName.equals(p.getCompany().getSales_rep())){
//            Hibernate.initialize(p.getSourceDocs());
//            //results.add(p);
//
//            //}
//            }*/
//            //temp = null;
//            return temp;
//        } catch (HibernateException e) {
//            System.err.println("Hibernate Exception" + e.getMessage());
//            throw new RuntimeException(e);
//        } /*
//         * Regardless of whether the above processing resulted in an Exception
//         * or proceeded normally, we want to close the Hibernate session.  When
//         * closing the session, we must allow for the possibility of a Hibernate
//         * Exception.
//         *
//         */ finally {
//            if (session != null) {
//                try {
//                    session.close();
//                } catch (HibernateException e) {
//                    System.err.println("Hibernate Exception" + e.getMessage());
//                    throw new RuntimeException(e);
//                }
//
//            }
//        }
//    }
    public static List getProjectListForPM(String pmName) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
//        String myName = user.getFirstName() + " " + user.getLastName();

        try {

            boolean t = true;
            List results = new ArrayList();

            String qry = "SELECT * FROM project project LEFT JOIN client_information company ON project.Id_client  = company.id_client "
                    + " WHERE  (project.status = 'active' OR project.status = 'onhold') "
                    + "AND (project.pm = '" + pmName + "' OR company.Sales_rep='" + pmName + "' OR company.Sales='" + pmName + "' ) "
                    + "ORDER BY project.number DESC";

            PreparedStatement st = session.connection().prepareStatement(qry);
            ResultSet rs = st.executeQuery();
            Hashtable duplicateProjects = new Hashtable();
            while (rs.next()) {
                if (duplicateProjects.get(rs.getString("number")) != null || rs.getString("number").equals("000000")) {

                } else {
                    duplicateProjects.put(rs.getString("number"), rs.getString("number"));
                    Project p = ProjectService.getInstance().getSingleProject(rs.getInt("Id_Project"));
//                Hibernate.initialize(p.getSourceDocs());
                    results.add(p);
                }

            }
            rs.close();
            st.close();
            return results;

        } catch (HibernateException e) {
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } catch (SQLException ex) {
            Logger.getLogger(ProjectHelper.class.getName()).log(Level.SEVERE, null, ex);
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

        return null;
    }

    //get all active projects
    public static List getProjectListForBackupPM(String pmName) {
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
//            query = session.createQuery("select project from app.project.Project project left join project.Company company where (project.status = 'active' or project.status = 'onhold') and company.Backup_pm='" + pmName + "' order by project.number desc ");
//            List temp = query.list();
//
//            Hashtable duplicateProjects = new Hashtable();
//            for (ListIterator iter = temp.listIterator(); iter.hasNext();) {
//                Project p = (Project) iter.next();
//                if (duplicateProjects.get(p.getNumber()) != null || p.getNumber().equals("000000")) {
//                    iter.remove();
//                } else {
//                    duplicateProjects.put(p.getNumber(), p.getNumber());
//                }
//            }
            List results = new ArrayList();
            String qry = "select * from project project LEFT JOIN client_information company ON project.Id_client  = company.id_client  where (project.status = 'active' or project.status = 'onhold') and CONVERT(company.Backup_pm USING utf8)=CONVERT('" + pmName + "' USING utf8) order by project.number desc ";
//            List projects = query.list();

            PreparedStatement st = session.connection().prepareStatement(qry);
            ResultSet rs = st.executeQuery();
            Hashtable duplicateProjects = new Hashtable();
            while (rs.next()) {
                if (duplicateProjects.get(rs.getString("number")) != null || rs.getString("number").equals("000000")) {

                } else {
                    duplicateProjects.put(rs.getString("number"), rs.getString("number"));
                    Project p = ProjectService.getInstance().getSingleProject(rs.getInt("Id_Project"));
//                Hibernate.initialize(p.getSourceDocs());
                    results.add(p);
                }

            }
            rs.close();
            st.close();

            //query = session.createQuery("select project from app.project.Project project where project.status = 'onhold' and project.Company.Backup_pm='"+pmName+"'");
            //temp.addAll(query.list());
            // List results = new ArrayList();
            // //System.out.println("Found:::"+temp.size());
            /*for(int i=0; i<temp.size();i++){
             Project p = (Project) temp.get(i);
             // if(pmName.equals(p.getCompany().getBackup_pm()) && !pmName.equals(p.getCompany().getSales_rep()) && !pmName.equals(p.getPm())){
             Hibernate.initialize(p.getSourceDocs());
             // results.add(p);
             //}
             }*/
            //temp = null;
            return results;
        } catch (HibernateException e) {
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } catch (SQLException ex) {
            Logger.getLogger(ProjectHelper.class.getName()).log(Level.SEVERE, null, ex);
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
        return null;
    }

    public static JSONObject ProjectToJson(Project p) throws Exception {

        JSONObject jo = new JSONObject();
        //jo.put("projectNumber",p.getNumber()+p.getCompany().getCompany_code());
//                    Quote1 q=null;
//                    String quote="";
//        jo.put("projectNumber", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:openProjectWindow('" + p.getProjectId() + "','" + p.getNumber() + p.getCompany().getCompany_code() + "')\">" + p.getNumber() + p.getCompany().getCompany_code() + "</a>");
        jo.put("projectNumber", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:openProjectWindow('" + p.getNumber() + p.getCompany().getCompany_code() + "','" + p.getProjectId() + "')\">" + p.getNumber() + p.getCompany().getCompany_code() + "</a>");
//                   for(int i=0;i<p.getQuotes().size();i++){
//                     q=(Quote1) p.getQuotes();
//                    quote+=p.getQuotes().;
//                     if(i<=p.getQuotes().size())quote+=",";
//                   }

        try {
            Quote1 q = QuoteService.getInstance().getSingleQuoteFromProject(p.getProjectId());

            //      Quote1  q = QuoteService.getInstance().getLastQuote(p.getQuotes());
            jo.put("quoteNumber", q.getNumber());
        } catch (Exception e) {
            jo.put("quoteNumber", "No quote");
        }

        //jo.put("client",p.getCompany().getCompany_name());
        jo.put("client", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:openClientWindowReverse('" + HrHelper.jsSafe(p.getCompany().getCompany_name()) + "','" + p.getCompany().getClientId() + "')\">" + p.getCompany().getCompany_name() + "</a>");

        jo.put("description", p.getProductDescription());
        jo.put("product", p.getProduct());
        jo.put("dueDate", p.getDueDate());
        jo.put("startDate", p.getStartDate());// to be changed to start date
        jo.put("deliveryDate", p.getDeliveryDate());
        String targets = "";
        HashMap alreadyAdded = new HashMap();
        try{
        for (Iterator iterSources = p.getSourceDocs().iterator(); iterSources.hasNext();) {
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
        }catch(Exception e){}
        jo.put("targets", targets);
        jo.put("invoiced", StandardCode.getInstance().projectInvoicingStatus(p));
        jo.put("status", p.getStatus());

        return jo;
    }

    public static boolean unlinkSourcesAndTargets(Project p) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from sourcedoc where id_project=?");
            st.setInt(1, p.getProjectId().intValue());
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

    public static List getProjectListForClientActive(String clientId) {
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
            //System.out.println("bfr query of project helper********************");
            query = session.createQuery("select project from app.project.Project project left join fetch project.SourceDocs sds left join project.Company company where project.status = 'active' and project.number <> 0 and company.clientId='" + clientId + "' order by project.number desc");
            List temp = query.list();
            //System.out.println("list size of project helper********************" + temp.size());

//query = session.createQuery("select project from app.project.Project project where project.status = 'onhold' and project.Company.Backup_pm='"+pmName+"'");
//temp.addAll(query.list());
// List results = new ArrayList();
// //System.out.println("Found:::"+temp.size());
            Hashtable duplicateProjects = new Hashtable();
            for (ListIterator iter = temp.listIterator(); iter.hasNext();) {
                Project p = (Project) iter.next();

                if (!p.getNumber().equals("000000")) {
                    if (duplicateProjects.get(p.getNumber()) != null /*|| p.getNumber().equals("000000")*/) {
                        iter.remove();
                    } else {
                        duplicateProjects.put(p.getNumber(), p.getNumber());
                    }
                }
            }

            for (int i = 0; i < temp.size(); i++) {
                Project p = (Project) temp.get(i);
                //System.out.println(p.getNumber());
// if(pmName.equals(p.getCompany().getBackup_pm()) && !pmName.equals(p.getCompany().getSales_rep()) && !pmName.equals(p.getPm())){
                Hibernate.initialize(p.getQualities());
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
         * or proceeded normally, we want to close the Hibernate session. When
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

    public static List getProjectListForClientComplete(String clientId) {
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
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             *///and (project.pm='"+pmName+"' or project.Company.Sales_rep='"+pmName+"') order by project.number
            //System.out.println("bfr query of project helper********************" + nd);
//            query = session.createQuery("select project from app.project.Project project left join fetch project.SourceDocs sds left join project.Company company where project.status = 'complete' and company.clientId='" + clientId + "' and project.number <> 0 and DATEDIFF('" + nd + "',project.completeDate)<365 order by project.number desc");
            query = session.createQuery("select project from app.project.Project project left join fetch project.SourceDocs sds left join project.Company company where project.status = 'complete' and company.clientId='" + clientId + "' and project.number <> 0 and DATEDIFF('" + nd + "',project.completeDate)<365 order by project.number desc");
            List temp = query.list();
            //System.out.println("list size of project helper********************" + temp.size());

//query = session.createQuery("select project from app.project.Project project where project.status = 'onhold' and project.Company.Backup_pm='"+pmName+"'");
//temp.addAll(query.list());
// List results = new ArrayList();
// //System.out.println("Found:::"+temp.size());
            Hashtable duplicateProjects = new Hashtable();
            for (ListIterator iter = temp.listIterator(); iter.hasNext();) {
                Project p = (Project) iter.next();
                if (duplicateProjects.get(p.getNumber()) != null || p.getNumber().equals("000000")) {
                    iter.remove();
                } else {
                    duplicateProjects.put(p.getNumber(), p.getNumber());
                }
            }

            for (int i = 0; i < temp.size(); i++) {
                Project p = (Project) temp.get(i);
// if(pmName.equals(p.getCompany().getBackup_pm()) && !pmName.equals(p.getCompany().getSales_rep()) && !pmName.equals(p.getPm())){
                Hibernate.initialize(p.getQualities());
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
         * or proceeded normally, we want to close the Hibernate session. When
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

    public static List getProjectListForClientOnHold(String clientId) {
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
            //System.out.println("bfr query of project helper********************");
            query = session.createQuery("select project from app.project.Project project left join fetch project.SourceDocs sds left join project.Company company where project.status = 'onhold' and company.clientId='" + clientId + "' and project.number <> 0 order by project.number desc");
            List temp = query.list();
            //System.out.println("list size of project helper********************" + temp.size());

//query = session.createQuery("select project from app.project.Project project where project.status = 'onhold' and project.Company.Backup_pm='"+pmName+"'");
//temp.addAll(query.list());
// List results = new ArrayList();
// //System.out.println("Found:::"+temp.size());
            Hashtable duplicateProjects = new Hashtable();
            for (ListIterator iter = temp.listIterator(); iter.hasNext();) {
                Project p = (Project) iter.next();
                if (duplicateProjects.get(p.getNumber()) != null || p.getNumber().equals("000000")) {
                    iter.remove();
                } else {
                    duplicateProjects.put(p.getNumber(), p.getNumber());
                }
            }

            for (int i = 0; i < temp.size(); i++) {
                Project p = (Project) temp.get(i);
// if(pmName.equals(p.getCompany().getBackup_pm()) && !pmName.equals(p.getCompany().getSales_rep()) && !pmName.equals(p.getPm())){
                Hibernate.initialize(p.getQualities());
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
         * or proceeded normally, we want to close the Hibernate session. When
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

    public static List getProjectListForClientActive(String clientId, String year) {
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
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             *///and (project.pm='"+pmName+"' or project.Company.Sales_rep='"+pmName+"') order by project.number
            //System.out.println("bfr query of project helper********************");
            query = session.createQuery("select project from app.project.Project project left join fetch project.SourceDocs sds left join project.Company company where project.status = 'active' and project.number <> 0 and company.clientId='" + clientId + "'   and project.startDate <'" + eDate + "' and project.startDate >'" + sDate + "' order by project.number desc");
            List temp = query.list();
            //System.out.println("list size of project helper********************" + temp.size());

//query = session.createQuery("select project from app.project.Project project where project.status = 'onhold' and project.Company.Backup_pm='"+pmName+"'");
//temp.addAll(query.list());
// List results = new ArrayList();
// //System.out.println("Found:::"+temp.size());
            Hashtable duplicateProjects = new Hashtable();
            for (ListIterator iter = temp.listIterator(); iter.hasNext();) {
                Project p = (Project) iter.next();

                if (!p.getNumber().equals("000000")) {
                    if (duplicateProjects.get(p.getNumber()) != null /*|| p.getNumber().equals("000000")*/) {
                        iter.remove();
                    } else {
                        duplicateProjects.put(p.getNumber(), p.getNumber());
                    }
                }
            }

            for (int i = 0; i < temp.size(); i++) {
                Project p = (Project) temp.get(i);
                //System.out.println(p.getNumber());
// if(pmName.equals(p.getCompany().getBackup_pm()) && !pmName.equals(p.getCompany().getSales_rep()) && !pmName.equals(p.getPm())){
                Hibernate.initialize(p.getQualities());
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
         * or proceeded normally, we want to close the Hibernate session. When
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

    public static List getProjectListForClientComplete(String clientId, String year) {
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
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             *///and (project.pm='"+pmName+"' or project.Company.Sales_rep='"+pmName+"') order by project.number
            //System.out.println("bfr query of project helper********************" + nd);
            query = session.createQuery("select project from app.project.Project project left join fetch project.ClientInvoices sds left join project.Company company where project.status = 'complete' and company.clientId='" + clientId + "' and project.number <> 0  and project.startDate <'" + eDate + "' and project.startDate >'" + sDate + "'  order by project.number desc");
            List temp = query.list();
            //System.out.println("list size of project helper********************" + temp.size());

//query = session.createQuery("select project from app.project.Project project where project.status = 'onhold' and project.Company.Backup_pm='"+pmName+"'");
//temp.addAll(query.list());
// List results = new ArrayList();
// //System.out.println("Found:::"+temp.size());
            Hashtable duplicateProjects = new Hashtable();
            for (ListIterator iter = temp.listIterator(); iter.hasNext();) {
                Project p = (Project) iter.next();
                if (duplicateProjects.get(p.getNumber()) != null || p.getNumber().equals("000000")) {
                    iter.remove();
                } else {
                    duplicateProjects.put(p.getNumber(), p.getNumber());
                }
            }

//            for (int i = 0; i < temp.size(); i++) {
//                Project p = (Project) temp.get(i);
//// if(pmName.equals(p.getCompany().getBackup_pm()) && !pmName.equals(p.getCompany().getSales_rep()) && !pmName.equals(p.getPm())){
//                //Hibernate.initialize(p.getQualities());
//// results.add(p);
////}
//            }
//temp = null;
            return temp;
        } catch (HibernateException e) {
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session. When
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
    
    public static List getProjectListForClientInvoice(String clientId, String year) {
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
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             *///and (project.pm='"+pmName+"' or project.Company.Sales_rep='"+pmName+"') order by project.number
            //System.out.println("bfr query of project helper********************" + nd);
            query = session.createQuery("select project from app.project.Project project left join fetch project.ClientInvoices sds left join project.Company company where company.clientId='" + clientId + "' and project.number <> 0  and project.startDate <'" + eDate + "' and project.startDate >'" + sDate + "'  order by project.number desc");
            List temp = query.list();
            //System.out.println("list size of project helper********************" + temp.size());

//query = session.createQuery("select project from app.project.Project project where project.status = 'onhold' and project.Company.Backup_pm='"+pmName+"'");
//temp.addAll(query.list());
// List results = new ArrayList();
// //System.out.println("Found:::"+temp.size());
            Hashtable duplicateProjects = new Hashtable();
            for (ListIterator iter = temp.listIterator(); iter.hasNext();) {
                Project p = (Project) iter.next();
                if (duplicateProjects.get(p.getNumber()) != null || p.getNumber().equals("000000")) {
                    iter.remove();
                } else {
                    duplicateProjects.put(p.getNumber(), p.getNumber());
                }
            }

//            for (int i = 0; i < temp.size(); i++) {
//                Project p = (Project) temp.get(i);
//// if(pmName.equals(p.getCompany().getBackup_pm()) && !pmName.equals(p.getCompany().getSales_rep()) && !pmName.equals(p.getPm())){
//                //Hibernate.initialize(p.getQualities());
//// results.add(p);
////}
//            }
//temp = null;
            return temp;
        } catch (HibernateException e) {
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session. When
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

    public static List getProjectListForClientOnHold(String clientId, String year) {
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
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             *///and (project.pm='"+pmName+"' or project.Company.Sales_rep='"+pmName+"') order by project.number
            //System.out.println("bfr query of project helper********************");
            query = session.createQuery("select project from app.project.Project project left join fetch project.SourceDocs sds left join project.Company company where project.status = 'onhold' and company.clientId='" + clientId + "' and project.number <> 0  and project.startDate <'" + eDate + "' and project.startDate >'" + sDate + "' order by project.number desc");
            List temp = query.list();
            //System.out.println("list size of project helper********************" + temp.size());

//query = session.createQuery("select project from app.project.Project project where project.status = 'onhold' and project.Company.Backup_pm='"+pmName+"'");
//temp.addAll(query.list());
// List results = new ArrayList();
// //System.out.println("Found:::"+temp.size());
            Hashtable duplicateProjects = new Hashtable();
            for (ListIterator iter = temp.listIterator(); iter.hasNext();) {
                Project p = (Project) iter.next();
                if (duplicateProjects.get(p.getNumber()) != null || p.getNumber().equals("000000")) {
                    iter.remove();
                } else {
                    duplicateProjects.put(p.getNumber(), p.getNumber());
                }
            }

            for (int i = 0; i < temp.size(); i++) {
                Project p = (Project) temp.get(i);
// if(pmName.equals(p.getCompany().getBackup_pm()) && !pmName.equals(p.getCompany().getSales_rep()) && !pmName.equals(p.getPm())){
                Hibernate.initialize(p.getQualities());
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
         * or proceeded normally, we want to close the Hibernate session. When
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
    public static List getProjectListForClient(String clientId) {
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
            //System.out.println("bfr query of project helper********************");
            query = session.createQuery("select project from app.project.Project project left join project.Company company where project.status <> 'notApproved'  and company.clientId='" + clientId + "'  and project.number <> 0 order by project.number DESC");
            //  query = session.createQuery("select project from app.project.Project project left join project.Company company where project.status <> 'notApproved'  and company.clientId='"+clientId +"'  and project.number <> 0");
            List temp = query.list();
            //System.out.println("list size of project helper********************" + temp.size());

            //query = session.createQuery("select project from app.project.Project project where project.status = 'onhold' and project.Company.Backup_pm='"+pmName+"'");
            //temp.addAll(query.list());
            // List results = new ArrayList();
            // //System.out.println("Found:::"+temp.size());
            Hashtable duplicateProjects = new Hashtable();
            for (ListIterator iter = temp.listIterator(); iter.hasNext();) {
                Project p = (Project) iter.next();
                if (duplicateProjects.get(p.getNumber()) != null || p.getNumber().equals("000000")) {
                    iter.remove();
                } else {
                    duplicateProjects.put(p.getNumber(), p.getNumber());
                }
            }

            for (int i = 0; i < temp.size(); i++) {
                Project p = (Project) temp.get(i);
                // if(pmName.equals(p.getCompany().getBackup_pm()) && !pmName.equals(p.getCompany().getSales_rep()) && !pmName.equals(p.getPm())){
                Hibernate.initialize(p.getQualities());
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
    public static List getProjectListForClientPerYear(String clientId, String year) {
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
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             *///and (project.pm='"+pmName+"' or project.Company.Sales_rep='"+pmName+"') order by project.number
            //System.out.println("bfr query of project helper********************");//left join client_information c on p.ID_Client = c.ID_Client
            query = session.createQuery("select project from app.project.Project project left join fetch project.SourceDocs sds left join project.Company company where project.status <> 'notApproved'  and company.clientId='" + clientId + "'  and project.number <> 0  and project.completeDate <'" + eDate + "' and project.completeDate >'" + sDate + "' order by project.number DESC");
//            query = session.createQuery("select project from app.project.Project project left join project.Company"
//                    + " where project.status <> 'notApproved'  and company.clientId='" + clientId + "'  and project.number <> 0  "
//                    + "and project.deliveryDate <'"+eDate+"' and project.deliveryDate >'"+sDate+"' order by project.number DESC");

//  query = session.createQuery("select project from app.project.Project project left join project.Company company where project.status <> 'notApproved'  and company.clientId='"+clientId +"'  and project.number <> 0");
            List temp = query.list();
            //System.out.println("list size of project helper********************" + temp.size());

            //query = session.createQuery("select project from app.project.Project project where project.status = 'onhold' and project.Company.Backup_pm='"+pmName+"'");
            //temp.addAll(query.list());
            // List results = new ArrayList();
            // //System.out.println("Found:::"+temp.size());
            Hashtable duplicateProjects = new Hashtable();
            for (ListIterator iter = temp.listIterator(); iter.hasNext();) {
                Project p = (Project) iter.next();
                if (duplicateProjects.get(p.getNumber()) != null || p.getNumber().equals("000000")) {
                    iter.remove();
                } else {
                    duplicateProjects.put(p.getNumber(), p.getNumber());
                }
            }

            for (int i = 0; i < temp.size(); i++) {
                Project p = (Project) temp.get(i);
                // if(pmName.equals(p.getCompany().getBackup_pm()) && !pmName.equals(p.getCompany().getSales_rep()) && !pmName.equals(p.getPm())){
                Hibernate.initialize(p.getQualities());
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
    public static List<Project> getProjectListForClientNotComplete(String clientId) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;

        try {
          query = session.createQuery("select project from app.project.Project project "
                  + "left join fetch project.ClientInvoices sds left join project.Company company where project.status = 'active'  "
                  + "and company.clientId='" + clientId + "'  and project.number <> 0  order by project.number DESC");
          List<Project> temp = query.list();
         
            Hashtable duplicateProjects = new Hashtable();
            for (ListIterator iter = temp.listIterator(); iter.hasNext();) {
                Project p = (Project) iter.next();
                if (duplicateProjects.get(p.getNumber()) != null || p.getNumber().equals("000000")) {
                    iter.remove();
                } else {
                    duplicateProjects.put(p.getNumber(), p.getNumber());
                }
            }

            for (int i = 0; i < temp.size(); i++) {
                Project p = (Project) temp.get(i);
                // if(pmName.equals(p.getCompany().getBackup_pm()) && !pmName.equals(p.getCompany().getSales_rep()) && !pmName.equals(p.getPm())){
                Hibernate.initialize(p.getQualities());
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

    public static JSONObject ProjectToJsonPrint(Project p) throws Exception {

        try {
            JSONObject jo = new JSONObject();
            //jo.put("projectNumber",p.getNumber()+p.getCompany().getCompany_code());

            jo.put("Project Number",   p.getNumber() + p.getCompany().getCompany_code());
            if (p.getDeliveryDate() != null) {
                jo.put("Delivery Date", p.getDeliveryDate());

            }
            if (p.getStartDate() != null) {
                jo.put("year", p.getStartDate().getYear() + 1900);
            }
            jo.put("Project Manager", p.getPm());
            jo.put("ProjectAmount", p.getProjectAmount());
            jo.put("Project Description", p.getProductDescription());
            jo.put("Product", p.getProduct());

            if (p.getContact() != null) {
                jo.put("Client Contact", p.getContact().getFirst_name() + " " + p.getContact().getLast_name() );
            }
            String ncr = "";
            ////System.out.println("before p.getQualities()=");
            if (p.getQualities() != null && p.getCompany() != null) {
                ncr += "";
                String ncrs = "";
                for (Iterator iter = p.getQualities().iterator(); iter.hasNext();) {
                    Quality q = (Quality) iter.next();
                    ncrs += p.getNumber() + p.getCompany().getCompany_code() + "-NC-" + q.getNumber() + "<br>";
                }
                ncr += ncrs ;
            }
            // //System.out.println("after p.getQualities()");
//            jo.put("NCR", ncr);
          
                
            jo.put("SourceLangs", p.getSrcLang());
            jo.put("TargetLangs", p.getTargetLang());
            

            return jo;
        } catch (Exception e) {
            //System.out.println("Problem processing project:" + p.getNumber());
        }
        return new JSONObject();
    }
    
    public static JSONObject ProjectToJson2(Project p) throws Exception {

        try {
            JSONObject jo = new JSONObject();
            //jo.put("projectNumber",p.getNumber()+p.getCompany().getCompany_code());

            jo.put("projectNumber", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openSingleProjectWindow('" + p.getNumber() + p.getCompany().getCompany_code() + "','" + p.getProjectId() + "')\">" + p.getNumber() + p.getCompany().getCompany_code() + "</a>");
            if (p.getDeliveryDate() != null) {
                jo.put("DeliveryDate", p.getDeliveryDate());

            }
            if (p.getStartDate() != null) {
                jo.put("year", p.getStartDate().getYear() + 1900);
            }
            jo.put("pm", p.getPm());
            jo.put("ProjectAmount", p.getProjectAmount());
            jo.put("projectDescription", p.getProductDescription());
            jo.put("product", p.getProduct());

            if (p.getContact() != null) {
                // jo.put("ClientContact","<a "+HrHelper.LINK_STYLE+" href=../clientContactEdit.do?clientContactId=" + p.getContact().getClientContactId() + ">" + p.getContact().getFirst_name()+" " + p.getContact().getLast_name()+"</a>");

                jo.put("ClientContact", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openSingleContactWindow('" + p.getContact().getLast_name() + ", " + p.getContact().getFirst_name() + "','" + p.getContact().getClientContactId() + "')\">" + p.getContact().getLast_name() + ", " + p.getContact().getFirst_name() + "</a>");
            }
            String ncr = "";
            ////System.out.println("before p.getQualities()=");
            if (p.getQualities() != null && p.getCompany() != null) {
                ncr += "<a href='../projectViewQuality.do?projectViewId=" + String.valueOf(p.getProjectId()) + "'>";
                String ncrs = new String("");
                for (Iterator iter = p.getQualities().iterator(); iter.hasNext();) {
                    Quality q = (Quality) iter.next();
                    ncrs += p.getNumber() + p.getCompany().getCompany_code() + "-NC-" + q.getNumber() + "<br>";
                }
                ncr += ncrs + "</a>";
            }
            // //System.out.println("after p.getQualities()");
            jo.put("NCR", ncr);
            jo.put("newProjectShallow", "<input type='button' class='x-btn' value='Copy' onclick=\"javascript:createNewProject('" + String.valueOf(p.getProjectId()) + "')\">");
            jo.put("newProjectDeep", "<input type='button' value='Copy' class='x-btn' onclick=\"javascript:createNewProjectDeepCopy('" + String.valueOf(p.getProjectId()) + "')\">");

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
                    //  for(Iterator iterLintasks = td.getLinTasks().iterator(); iterLintasks.hasNext();) {
                    //  LinTask lt = (LinTask)iterLintasks.next();
                    //  if(!"".equalsIgnoreCase(lt.getTargetLanguage())){
                    String srcLang = td.getLanguage();

                    String abr = (String) LanguageAbs.getInstance().getAbs().get(srcLang);
                    //Also prevent duplicates
                    if (abr != null && !"".equals(srcLang) && !"".equals(abr) && alreadyAdded.get(abr) == null) {
                        targets += abr + ", ";
                        alreadyAdded.put(abr, abr);
                    } else if (abr == null && !"".equals(srcLang) && !"".equals(abr) && alreadyAdded.get(srcLang) == null) {
                        targets += srcLang + ", ";
                        alreadyAdded.put(srcLang, srcLang);
                        // }

                    }
                    //  }
                }
                if (targets.endsWith(", ")) {
                    targets = targets.substring(0, targets.length() - 2);
                }
            }
            jo.put("SourceLangs", sources);
            jo.put("TargetLangs", targets);

            return jo;
        } catch (Exception e) {
            //System.out.println("Problem processing project:" + p.getNumber());
        }
        return new JSONObject();
    }
    
    public static JSONObject ProjectToJson3(Project p) throws Exception {

        try {
            JSONObject jo = new JSONObject();
            //jo.put("projectNumber",p.getNumber()+p.getCompany().getCompany_code());

            jo.put("projectNumber", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openSingleProjectWindow('" + p.getNumber() + p.getCompany().getCompany_code() + "','" + p.getProjectId() + "')\">" + p.getNumber() + p.getCompany().getCompany_code() + "</a>");
            if (p.getDeliveryDate() != null) {
                jo.put("DeliveryDate", p.getDeliveryDate());

            }
            if (p.getStartDate() != null) {
                jo.put("year", p.getStartDate().getYear() + 1900);
            }
            jo.put("sales", p.getCompany().getSales());
                jo.put("ae", p.getAe());
                jo.put("pm", p.getPm());
                
            jo.put("ProjectAmount", p.getProjectAmount());
            jo.put("projectDescription", p.getProductDescription());
            jo.put("product", p.getProduct());
            try {
            Quote1 q = QuoteService.getInstance().getSingleQuoteFromProject(p.getProjectId());
            jo.put("quoteNumber", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openSingleQuoteWindow('" + q.getQuote1Id() + "','" + StandardCode.getInstance().noNull(q.getNumber()) + "')\">" + StandardCode.getInstance().noNull(q.getNumber()) + "</a>");

            //      Quote1  q = QuoteService.getInstance().getLastQuote(p.getQuotes());
           // jo.put("quoteNumber", q.getNumber());
        } catch (Exception e) {
            jo.put("quoteNumber", "No quote");
        }

        jo.put("invoiced", StandardCode.getInstance().projectInvoicingStatus(p));
        jo.put("status", p.getStatus());
            if (p.getContact() != null) {
                // jo.put("ClientContact","<a "+HrHelper.LINK_STYLE+" href=../clientContactEdit.do?clientContactId=" + p.getContact().getClientContactId() + ">" + p.getContact().getFirst_name()+" " + p.getContact().getLast_name()+"</a>");

                jo.put("ClientContact", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openSingleContactWindow('" + p.getContact().getLast_name() + ", " + p.getContact().getFirst_name() + "','" + p.getContact().getClientContactId() + "')\">" + p.getContact().getLast_name() + ", " + p.getContact().getFirst_name() + "</a>");
            }
//            String ncr = "";
            ////System.out.println("before p.getQualities()=");
//            if (p.getQualities() != null && p.getCompany() != null) {
//                ncr += "<a href='../projectViewQuality.do?projectViewId=" + String.valueOf(p.getProjectId()) + "'>";
//                String ncrs = new String("");
//                for (Iterator iter = p.getQualities().iterator(); iter.hasNext();) {
//                    Quality q = (Quality) iter.next();
//                    ncrs += p.getNumber() + p.getCompany().getCompany_code() + "-NC-" + q.getNumber() + "<br>";
//                }
//                ncr += ncrs + "</a>";
//            }
            // //System.out.println("after p.getQualities()");
//            jo.put("NCR", ncr);
//            jo.put("newProjectShallow", "<input type='button' class='x-btn' value='Copy' onclick=\"javascript:createNewProject('" + String.valueOf(p.getProjectId()) + "')\">");
//            jo.put("newProjectDeep", "<input type='button' value='Copy' class='x-btn' onclick=\"javascript:createNewProjectDeepCopy('" + String.valueOf(p.getProjectId()) + "')\">");

//            String targets = "";
//            String sources = "";
//            HashMap alreadyAdded = new HashMap();
//            List sourceDocList = ProjectService.getInstance().getSourceDoc(p);
//            for (Iterator iterSources = sourceDocList.iterator(); iterSources.hasNext();) {
//                SourceDoc sd = (SourceDoc) iterSources.next();
//
//                sources += (String) LanguageAbs.getInstance().getAbs().get(sd.getLanguage());
//                if (iterSources.hasNext()) {
//
//                    sources += ", ";
//                }
//                for (Iterator iterTargets = sd.getTargetDocs().iterator(); iterTargets.hasNext();) {
//                    TargetDoc td = (TargetDoc) iterTargets.next();
//                    //  for(Iterator iterLintasks = td.getLinTasks().iterator(); iterLintasks.hasNext();) {
//                    //  LinTask lt = (LinTask)iterLintasks.next();
//                    //  if(!"".equalsIgnoreCase(lt.getTargetLanguage())){
//                    String srcLang = td.getLanguage();
//
//                    String abr = (String) LanguageAbs.getInstance().getAbs().get(srcLang);
//                    //Also prevent duplicates
//                    if (abr != null && !"".equals(srcLang) && !"".equals(abr) && alreadyAdded.get(abr) == null) {
//                        targets += abr + ", ";
//                        alreadyAdded.put(abr, abr);
//                    } else if (abr == null && !"".equals(srcLang) && !"".equals(abr) && alreadyAdded.get(srcLang) == null) {
//                        targets += srcLang + ", ";
//                        alreadyAdded.put(srcLang, srcLang);
//                        // }
//
//                    }
//                    //  }
//                }
//                if (targets.endsWith(", ")) {
//                    targets = targets.substring(0, targets.length() - 2);
//                }
//            }
            
            jo.put("TargetLangs", p.getTargetLang());

            return jo;
        } catch (Exception e) {
            //System.out.println("Problem processing project:" + p.getNumber());
        }
        return new JSONObject();
    }


    public static JSONObject ClientProjectToJson2(Project p) throws Exception {

        String lineOfText = "";
        try {
            JSONObject jo = new JSONObject();

            //jo.put("projectNumber",p.getNumber()+p.getCompany().getCompany_code());
//ClientViewProject;
            jo.put("projectNumber", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openSingleClientProjectWindow('" + p.getProjectId() + "','" + StandardCode.getInstance().noNull(p.getNumber() + p.getCompany().getCompany_code()) + "')\">" + StandardCode.getInstance().noNull(p.getNumber() + p.getCompany().getCompany_code()) + "</a>");

            //  jo.put("projectNumber",p.getNumber()+p.getCompany().getCompany_code());
            try {
                if (p.getDeliveryDate() != null) {
                    jo.put("DeliveryDate", p.getDeliveryDate());

                }
            } catch (Exception e) {
            }

            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            try {
                if (p.getStartDate() != null) {
                    jo.put("startDate", p.getStartDate());

                }
            } catch (Exception e) {
            }

            try {
                if (p.getDueDate() != null) {
                    jo.put("dueDate", p.getDueDate());

                }
            } catch (Exception e) {
            }
            try {
                if (p.getStartDate() != null) {
                    jo.put("year", p.getStartDate().getYear() + 1900);
                }
            } catch (Exception e) {
            }
            try {

                jo.put("fee", noNull(StandardCode.getInstance().formatDouble(p.getProjectAmount())));

            } catch (Exception e) {
            }
            try {
                jo.put("ProjectAmount", p.getProjectAmount());
            } catch (Exception e) {
            }
            try {
                jo.put("projectDescription", p.getProductDescription());
            } catch (Exception e) {
            }
            try {
                jo.put("product", p.getProduct());
            } catch (Exception e) {
            }
            try {
                //jo.put("medical")
                // jo.put(Category, p.getProduct())
                if (p.getClientPO() != null) {
                    jo.put("clientPO", p.getClientPO());
                } else {
                    jo.put("clientPO", "No Client PO Available");
                }
            } catch (Exception e) {
            }
            try {

                if (p.getContact() != null) {
                    // jo.put("ClientContact","<a "+HrHelper.LINK_STYLE+" href=../clientContactEdit.do?clientContactId=" + p.getContact().getClientContactId() + ">" + p.getContact().getFirst_name()+" " + p.getContact().getLast_name()+"</a>");

                    jo.put("ClientContact", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openSingleContactWindow('" + p.getContact().getLast_name() + ", " + p.getContact().getFirst_name() + "','" + p.getContact().getClientContactId() + "')\">" + p.getContact().getLast_name() + ", " + p.getContact().getFirst_name() + "</a>");
                }
            } catch (Exception e) {
            }
            try {
                // String ncr = "";
                ////System.out.println("before p.getQualities()=");

                // //System.out.println("after p.getQualities()");
                if(p.getTargetLangCnt()==null){ProjectHelper.updateLanguageCount(p);}
                if(p.getTargetLang()==null){ProjectHelper.updateLanguageCount(p);}
//                int idTask1 = 0;
//                String task1 = "";
//                String targets = "";
//                String sources = "";
//                HashMap alreadyAdded = new HashMap();
//
//                for (Iterator iterSources = p.getSourceDocs().iterator(); iterSources.hasNext();) {
//                    SourceDoc sd = (SourceDoc) iterSources.next();
//
//                    sources += (String) LanguageAbs.getInstance().getAbs().get(sd.getLanguage());
//                    if (iterSources.hasNext()) {
//
//                        sources += ", ";
//                    }
//                    for (Iterator iterTargets = sd.getTargetDocs().iterator(); iterTargets.hasNext();) {
//                        TargetDoc td = (TargetDoc) iterTargets.next();
//                        for (Iterator iterLintasks = td.getLinTasks().iterator(); iterLintasks.hasNext();) {
//                            LinTask lt = (LinTask) iterLintasks.next();
//                            if (!"".equalsIgnoreCase(lt.getTargetLanguage())) {
//                                String srcLang = lt.getTargetLanguage();
//
//                                String abr = (String) LanguageAbs.getInstance().getAbs().get(srcLang);
//                                //Also prevent duplicates
//                                if (abr != null && !"".equals(srcLang) && !"".equals(abr) && alreadyAdded.get(abr) == null) {
//                                    targets += abr + ", ";
//                                    alreadyAdded.put(abr, abr);
//                                } else if (abr == null && !"".equals(srcLang) && !"".equals(abr) && alreadyAdded.get(srcLang) == null) {
//                                    targets += srcLang + ", ";
//                                    alreadyAdded.put(srcLang, srcLang);
//                                }
//
//                            }
//                        }
//                        idTask1 = td.getTargetDocId();
//                    }
//                    if (targets.endsWith(", ")) {
//                        targets = targets.substring(0, targets.length() - 2);
//                    }
//                }
//
//                List linTaskList = QuoteService.getInstance().getLinTask(idTask1);
//                List engTaskList = QuoteService.getInstance().getEnggTask(idTask1);
//                List forTaskList = QuoteService.getInstance().getFormatTask(idTask1);
//                List otherTaskList = QuoteService.getInstance().getOtherTask(idTask1);
//
//                for (int ll = 0; ll < linTaskList.size(); ll++) {
//                    LinTask lt = (LinTask) linTaskList.get(ll);
//                    if (alreadyAdded.get(lt.getTaskName()) == null) {
//
//                        if (task1 != "") {
//                            task1 += ", ";
//                        }
//                        task1 += lt.getTaskName();
//                    }
//
//                }
//                for (int ll = 0; ll < engTaskList.size(); ll++) {
//                    EngTask lt = (EngTask) engTaskList.get(ll);
//                    if (alreadyAdded.get(lt.getTaskName()) == null) {
//                        if (task1 != "") {
//                            task1 += ", ";
//                        }
//                        task1 += lt.getTaskName();
//                    }
//
//                }
//                for (int ll = 0; ll < forTaskList.size(); ll++) {
//                    DtpTask lt = (DtpTask) forTaskList.get(ll);
//                    if (alreadyAdded.get(lt.getTaskName()) == null) {
//                        if (task1 != "") {
//                            task1 += ", ";
//                        }
//                        task1 += lt.getTaskName();
//                    }
//
//                }
//                for (int ll = 0; ll < otherTaskList.size(); ll++) {
//
//                    OthTask lt = (OthTask) otherTaskList.get(ll);
//                    if (alreadyAdded.get(lt.getTaskName()) == null) {
//                        if (task1 != "") {
//                            task1 += ", ";
//                        }
//                        task1 += lt.getTaskName();
//                    }
//
//                }
                jo.put("Task", p.getTask());

                jo.put("SourceLangs", p.getSrcLang());
                jo.put("TargetLangs", p.getTargetLang());
            } catch (Exception e) {
            }
            try {

                Quote1 q = QuoteService.getInstance().getSingleQuoteFromProject(p.getProjectId());
                List cq = QuoteService.getInstance().getSingleClientQuote(q.getQuote1Id());
                for (int ii = 0; ii < cq.size(); ii++) {
                    // SourceDoc sd = (SourceDoc) iterSources.next();
                    Client_Quote newQA = (Client_Quote) cq.get(ii);

                    if (p.getCompany().getClientId() == 100) {
                        lineOfText += StandardCode.getInstance().noNull(newQA.getProductText());

                    }

                }

            } catch (Exception e) {
            }

            jo.put("lineOfText", lineOfText);

            return jo;
        } catch (Exception e) {
            //System.out.println("Problem processing project:" + p.getNumber());
        }

        return new JSONObject();
    }

    //get all clients
    public static double sumAllProjectAmounts(int clientId) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        double result = 0;

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            PreparedStatement st = session.connection().prepareStatement("select sum(projectAmount) from project where ID_Client=?");
            st.setInt(1, clientId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                result = rs.getDouble(1);
            }

            st.close();
            return result;

        } catch (Exception e) {
            System.err.println("Exception" + e.getMessage());
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

    //get all clients
    public static String getAllYearsRevenueTable(String clientId, String filepath) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        //System.out.println("getAllYearsRevenueTable of client view Accouting******************");
        Session session = ConnectionFactory.getInstance().getSession();
        String result = "<table border=0 cellpadding=2><tr><td colspan=2><font size=4>Revenue History</td></tr>";

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            PreparedStatement st = session.connection().prepareStatement("SELECT sum(projectAmount), extract(YEAR from startDate) as projectYear  from project  where ID_Client=? and status<>'onhold' and status<>'notApproved' and status<>'nonActive' group by extract(YEAR from startDate) order by projectYear desc");
            st.setInt(1, Integer.parseInt(clientId));
            ResultSet rs = st.executeQuery();
            double total = 0;
            while (rs.next()) {
                total += rs.getDouble(1);
                String amount = StandardCode.getInstance().formatMoney(rs.getDouble(1));
                int year = rs.getInt(2);
                result += "<tr><td>" + year + "</td><td align='right'>" + amount + "</td></tr>";
                dataset.setValue(rs.getDouble(1), "Revenue", String.valueOf(year));
            }
            result += "<tr><td>&nbsp;</td><td align='right' style='border-top-width: 3; border-top-style: solid;'><b>" + StandardCode.getInstance().formatMoney(total) + "</td></tr>";
            result += "</table>";
            st.close();

            /*
             * CHART CODE  added 11th Feb 2010 - SpatialIdeas
             */
            final JFreeChart chart = ChartFactory.createBarChart("Chart", "Year", "Revenue", dataset, PlotOrientation.VERTICAL, true, true, false);

            final CategoryPlot plot = chart.getCategoryPlot();
            plot.setForegroundAlpha(0.5f);

            chart.setBackgroundPaint(new Color(249, 231, 236));

            try {
                final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());

                //  ServletContext
                //   String path = "/Users/vishal/Desktop";//application.getRealPath("/");
                // String filePath = path + "/revenue.png";
                File file = new File(filepath);

                //System.out.println(file.getAbsolutePath());
                //System.out.println("abslotue........." + file.getAbsolutePath());
                ChartUtilities.saveChartAsPNG(file, chart, 600, 300, info);
                //System.out.println(file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;

        } catch (Exception e) {
            System.err.println("Exception" + e.getMessage());
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

    public static String getAllYearsRevenueTable(String clientId) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        //System.out.println("getAllYearsRevenueTable of client view Accouting******************");
        Session session = ConnectionFactory.getInstance().getSession();
        String result = "<table border=0 cellpadding=2><tr><td colspan=2><font size=4>Revenue History</td></tr>";

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            PreparedStatement st = session.connection().prepareStatement("SELECT sum(projectAmount), extract(YEAR from startDate) as projectYear  from project  where ID_Client=? and status<>'onhold' and status<>'notApproved' and status<>'nonActive' group by extract(YEAR from startDate) order by projectYear desc");
            st.setInt(1, Integer.parseInt(clientId));
            ResultSet rs = st.executeQuery();
            double total = 0;
            while (rs.next()) {
                total += rs.getDouble(1);
                String amount = StandardCode.getInstance().formatMoney(rs.getDouble(1));
                int year = rs.getInt(2);
                if (year > 0) {
                    result += "<tr><td>" + year + "</td><td align='right'>" + amount + "</td></tr>";
                }
                dataset.setValue(rs.getDouble(1), "Revenue", String.valueOf(year));
            }
            result += "<tr><td>&nbsp;</td><td align='right' style='border-top-width: 3; border-top-style: solid;'><b>" + StandardCode.getInstance().formatMoney(total) + "</td></tr>";
            result += "</table>";
            st.close();

            /*
             * CHART CODE  added 11th Feb 2010 - SpatialIdeas
             */
            //  final JFreeChart chart = ChartFactory.createBarChart("Chart", "Year", "Revenue", dataset, PlotOrientation.VERTICAL, true, true, false);
            //    final CategoryPlot plot = chart.getCategoryPlot();
            //  plot.setForegroundAlpha(0.5f);
            //   chart.setBackgroundPaint(new Color(249, 231, 236));
            //    try {
            //       final ChartRenderingInfo info = new ChartRenderingInfo
            //       (new StandardEntityCollection());
            //  ServletContext
            //   String path = "/Users/vishal/Desktop";//application.getRealPath("/");
            // String filePath = path + "/revenue.png";
            //      File file= new File(filepath);
            //       //System.out.println(file.getAbsolutePath());
            //       //System.out.println("abslotue........."+file.getAbsolutePath());
            //      ChartUtilities.saveChartAsPNG(file, chart, 600, 300, info);
            //      //System.out.println(file.getAbsolutePath());
            //    } catch (Exception e) {
            //           e.printStackTrace();
            //    }
            return result;

        } catch (Exception e) {
            System.err.println("Exception" + e.getMessage());
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

    public static List getRelatedProjectList(String clientId, String product) {
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
            query = session.createQuery("select distinct project from app.project.Project project join fetch project.SourceDocs tds  join project.Company company where project.Company.clientId='" + clientId + "' and project.product like '" + product + "%' ");
            List temp = query.list();

            for (int i = 0; i < temp.size(); i++) {
                Project p = (Project) temp.get(i);
                // if(pmName.equals(p.getCompany().getBackup_pm()) && !pmName.equals(p.getCompany().getSales_rep()) && !pmName.equals(p.getPm())){
                Hibernate.initialize(p.getQualities());
                // results.add(p);
                //}
            }
            for (int i = temp.size() - 1; i >= 0; i--) {

                Project p = (Project) temp.get(i);
                for (int j = i - 1; j >= 0; j--) {
                    Project p2 = (Project) temp.get(j);
                    if (p2.getNumber().equals(p.getNumber())) {
                        temp.remove(i);
                        break;
                    }
                }
            }
            return temp;
        } catch (HibernateException e) {
            //System.out.println("Hibernate Exception:" + e.getMessage());
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
                    //System.out.println("Hibernate Exception:" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }
    }

    public static String generateProjectHeaderHtml(Project p) {
        String status = noNull(p.getStatus());
        if (status.equalsIgnoreCase("active")) {
            status = "Active";
        } else if (status.equalsIgnoreCase("complete")) {
            status = "Complete";
        } else if (status.equalsIgnoreCase("nonActive")) {
            status = "Non Active";
        } else if (status.equalsIgnoreCase("notApproved")) {
            status = "Not Approved";
        } else if (status.equalsIgnoreCase("onhold")) {
            status = "On Hold";
        }

        String results = "<table border='0' width='100%'>";
        results += "<tr><td rowspan=4>" + "<a href=\"javascript:parent.openSingleClientWindow('" + StandardCode.getInstance().noNull(p.getCompany().getCompany_name()) + "'," + p.getCompany().getClientId() + ")\">" + "<img src='/logo/images/" + p.getCompany().getLogo() + "' width='163' height='59' border='0' title='Client Logo. Width must be up to 163px and height must be up to 59px' alt='Client Logo. Width must be up to 163px and height must be up to 59px'/></td>"
                + "</a>"
                + "<td colspan=6 align='center'><font size='4' color='blue'>" + noNull(p.getCompany().getCompany_name()) + "</td></tr>";

        results += "<tr><td align='right'><font size='2'><b>Component:  </td><td align='left'>" + " " + noNull(p.getComponent()) + "</td>"
                + "<td align='right'><font size='2' ><b>PM:  </td><td align='left'>" + " " + noNull(p.getPm()) + "</td>"
                + "<td align='right'><font size='2' ><b>Project No:  </td><td align='left'>" + " " + p.getNumber() + p.getCompany().getCompany_code() + "</td></tr>";

        results
                += "<tr><td align='right'><font size='2' ><b>Product:  </td><td align='left'>" + " " + noNull(p.getProduct()) + "</td>"
                + "<td align='right'><font size='2' ><b>AE:  </td><td align='left'>" + " " + noNull(p.getCompany().getSales_rep()) + "</td>"
                + "<td align='right'><font size='2' ><b>Status:  </td><td align='left'>" + " " + noNull(status) + "</td></tr>";

        results += "<tr><td align='right'><font size='2' ><b> Description:  </td><td  align='left'>" + " " + noNull(p.getProductDescription()) + "</td>"
                + "<td align='right'><font size='2' ><b>Sales:  </td><td align='left'>" + " " + noNull(p.getCompany().getSales()) + "</td>"
                + "<td align='right'><font size='2' ><b>Archive ID:  </td><td align='left'>" + " " + noNull(p.getArchiveId()) + "</td>"
                + "</tr>";

        results += "</table><hr>";

        return results;

    }

    public static String generateINTEQAHeaderHtml(Project p) {
        String status = noNull(p.getStatus());
        if (status.equalsIgnoreCase("active")) {
            status = "Active";
        } else if (status.equalsIgnoreCase("complete")) {
            status = "Complete";
        } else if (status.equalsIgnoreCase("nonActive")) {
            status = "Non Active";
        } else if (status.equalsIgnoreCase("notApproved")) {
            status = "Not Approved";
        } else if (status.equalsIgnoreCase("onhold")) {
            status = "On Hold";
        }
        Quote1 q = null;
        try {
            if (Integer.parseInt(p.getNumber()) == 0) {
                q = QuoteService.getInstance().getSingleQuoteFromProject(p.getProjectId());
            }
        } catch (Exception e) {
        }

        String results = "<table border='0' width='100%'>";
        results += "<tr><td rowspan=4>" + "<a href=\"javascript:parent.openSingleClientWindow('" + StandardCode.getInstance().noNull(p.getCompany().getCompany_name()) + "'," + p.getCompany().getClientId() + ")\">" + "<img src='/logo/images/" + p.getCompany().getLogo() + "' width='163' height='59' border='0' title='Client Logo. Width must be up to 163px and height must be up to 59px' alt='Client Logo. Width must be up to 163px and height must be up to 59px'/></td>"
                + "</a>"
                + "<td colspan=6 align='center'><font size='4' color='blue'>" + noNull(p.getCompany().getCompany_name()) + "</td></tr>";

        results += "<tr><td align='right'><font size='2'><b>Component:  </td><td align='left'>" + " " + noNull(p.getComponent()) + "</td>"
                + "<td align='right'><font size='2' ><b>PM:  </td><td align='left'>" + " " + noNull(p.getPm()) + "</td>";

        if (q == null) {
            results += "<td align='right'><font size='2' ><b>Project No:  </td><td align='left'>" + " " + p.getNumber() + p.getCompany().getCompany_code() + "</td></tr>";
        } else {
            results += "<td align='right'><font size='2' ><b>Quote No:  </td><td align='left'>" + " " + q.getNumber() + "</td></tr>";
        }
        results
                += "<tr><td align='right'><font size='2' ><b>Product:  </td><td align='left'>" + " " + noNull(p.getProduct()) + "</td>"
                + "<td align='right'><font size='2' ><b>AE:  </td><td align='left'>" + " " + noNull(p.getCompany().getSales_rep()) + "</td>";
        if (q == null) {
            results += "<td align='right'><font size='2' ><b>Status:  </td><td align='left'>" + " " + noNull(status) + "</td></tr>";
        } else {
            results += "<td align='right'><font size='2' ><b>Status:  </td><td align='left'>  Pending </td></tr>";
        }
        results += "<tr><td align='right'><font size='2' ><b> Description:  </td><td  align='left'>" + " " + noNull(p.getProductDescription()) + "</td>"
                + "<td align='right'><font size='2' ><b>Sales:  </td><td align='left'>" + " " + noNull(p.getCompany().getSales()) + "</td>"
                + "<td align='right'><font size='2' ><b>Archive ID:  </td><td align='left'>" + " " + noNull(p.getArchiveId()) + "</td>"
                + "</tr>";

        results += "</table><hr>";

        return results;

    }

    public static String noNull(String s) {
        if (s == null || "null".equalsIgnoreCase(s)) {
            return "";
        } else {
            return s;
        }

    }

    public static String formatDate(Date myDate) {
        if (myDate == null) {
            return "";
        } else {
            return sdf.format(myDate);
        }
    }

    public static String generateProjectFooterHtml(Project p) {
        Quote1 q = null;
        String projectStartDate;
        if (p.getQuotes() != null && p.getQuotes().size() > 0) { //this project many not have quotes
            q = QuoteService.getInstance().getLastQuote(p.getQuotes());
            projectStartDate = formatDate(q.getApprovalDate());
        } else {
            projectStartDate = formatDate(p.getCreatedDate());
        }
        String results = "<hr noshade size='2'/><table align='center' border='0' width='40%'>";
        results += "<tr><td align='right'><font size='2' color='#0000FF' >Project created by: </td>"
                + "<td align='left'>" + noNull(p.getCreatedBy()) + "</td>"
                + "<td align='right'><font size='2' color='#0000FF'>on: </td><td align='left'>" + projectStartDate + "</td></tr>";

        results += "<tr><td align='right'><font size='2' color='#0000FF' >Last modified by: </td>"
                + "<td align='left'>" + noNull(p.getLastModifiedBy()) + "</td>"
                + "<td align='right'><font size='2' color='#0000FF'>on: </td><td align='left'>" + formatDate(p.getLastModifiedDate()) + "</td></tr>";

        results += "</table>";
        return results;

    }

    //get all clients
    public static List getInspections(String projectId, String type) {
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
            //      query =	session.createQuery("select product from app.extjs.vo.Product product where ID_Client= "+ clientId + " order by product.product ");

            query = session.createQuery("select pi from app.project.Inspection pi where ID_Project= " + projectId + " and inspectionType='" + type + "'");
            return query.list();
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

    public static boolean updateInspections(int projectId, String jsonComm, String inspectionType) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from inspection where ID_Project=? and inspectionType=?");
            st.setInt(1, projectId);
            st.setString(2, inspectionType);

            st.executeUpdate();

            st.close();
            st = session.connection().prepareStatement("insert into inspection "
                    + "(id_project, milestone, inDate, inspector, approved, rejected, note, inspectionType, language,applicable,InDefault)"
                    + " values (?,?,?,?,?,?,?,?,?,1,1)");

            if (jsonComm != null && !"".equals(jsonComm)) {
                JSONArray comm = new JSONArray(jsonComm);
                //    //System.out.println("comm.length()="+comm.length());
                for (int i = 0; i < comm.length(); i++) {
                    JSONObject j = (JSONObject) comm.get(i);

                    st.setInt(1, projectId);
                    st.setString(2, j.getString("Milestone"));
                    if (!"null".equals(j.getString("InspectionDate")) && !"".equals(j.getString("InspectionDate"))) {
                        st.setDate(3, new java.sql.Date(sdf.parse(j.getString("InspectionDate")).getTime()));
                    } else {
                        st.setDate(3, null);
                    }

                    st.setString(4, j.getString("InspectionBy"));
                    if ("true".equals(j.getString("IsApproved"))) {
                        st.setInt(5, 1);
                    } else {
                        st.setInt(5, 0);
                    }

                    if ("true".equals(j.getString("IsRejected"))) {
                        st.setInt(6, 1);
                    } else {
                        st.setInt(6, 0);
                    }

                    st.setString(7, j.getString("Notes"));
                    st.setString(8, inspectionType);
                    st.setString(9, j.getString("Target"));

                    st.executeUpdate();

                }
            }

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

    //get all clients
    public static List getIncrementals(String projectId) {
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
            //      query =	session.createQuery("select product from app.extjs.vo.Product product where ID_Client= "+ clientId + " order by product.product ");

            query = session.createQuery("select pi from app.extjs.vo.Incremental pi where id_project= " + projectId);
            return query.list();
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

    public static boolean updateProjectIncrementals(int projectId, String jsonComm) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from incrementals where ID_Project=?");
            st.setInt(1, projectId);
            st.executeUpdate();

            if (jsonComm != null && !"".equals(jsonComm)) {
                JSONArray comm = new JSONArray(jsonComm);
                //    //System.out.println("comm.length()="+comm.length());
                for (int i = 0; i < comm.length(); i++) {
                    JSONObject j = (JSONObject) comm.get(i);
                    app.extjs.vo.Incremental pr = new app.extjs.vo.Incremental();
                    pr.setId_project(new Integer(projectId));
                    pr.setIncDate(sdf.parse(j.getString("IncDate")));
                    pr.setIncDescription(j.getString("IncDescription"));

                    session.save(pr);
                }
            }

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

    //get all clients
    public static List getProjectReqs(String projectId, String type) {
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
            //      query =	session.createQuery("select product from app.extjs.vo.Product product where ID_Client= "+ clientId + " order by product.product ");

            query = session.createQuery("select pi from app.extjs.vo.ProjectRequirements pi where id_project= " + projectId + " and type='" + type + "'");
            return query.list();
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

    public static boolean updateProjectReqs(int projectId, String jsonComm, String type) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from project_team_fee_requirements where ID_Project=? and type='" + type + "'");
            st.setInt(1, projectId);
            st.executeUpdate();

            if (jsonComm != null && !"".equals(jsonComm)) {
                JSONArray comm = new JSONArray(jsonComm);
                //    //System.out.println("comm.length()="+comm.length());
                for (int i = 0; i < comm.length(); i++) {
                    JSONObject j = (JSONObject) comm.get(i);
                    app.extjs.vo.ProjectRequirements pr = new app.extjs.vo.ProjectRequirements();

                    pr.setID_Project(projectId);
                    pr.setRequirement(j.getString("Requirement"));
                    pr.setSatisfied(j.getString("Satisfied"));
                    pr.setNotes(j.getString("Notes"));
                    pr.setType(j.getString("Type"));

                    session.save(pr);
                }
            }

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

    public static int selectMaxChangeNoForProject(int projectId) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        int result = 0;

        try {

            PreparedStatement st = session.connection().prepareStatement("select max(changeNo) maxChangeNo from sourcedoc where id_project=?");
            st.setInt(1, projectId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                result = rs.getInt("maxChangeNo");
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

    public static boolean unlinkSourcesAndTargetsForChangeNo(Project p, int changeNo) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from sourcedoc where id_project=? and changeNo=?");
            st.setInt(1, p.getProjectId().intValue());
            st.setInt(2, changeNo);
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

    public static boolean unlinkSourcesAndTargetsForLang(Project p, String lang) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from sourcedoc where id_project=? and language=?");
            st.setInt(1, p.getProjectId().intValue());
            st.setString(2, lang);
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

    public static boolean unlinkTargetsForLang(int id) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from targetdoc where id_targetDoc=?");
            st.setInt(1, id);
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

    public static String getChangesHtmlTable(int projectId) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        String result = "";
        result += "<tr><td><b>Change Desc</td><td><b>Tasks</td><td><b>Total Fee</td><td><b>Total Team</td><td><b>Profitability</td><td><b>Change Order</td></tr>";

        try {

            PreparedStatement st = session.connection().prepareStatement(
                    "  select "
                    + " sourceDoc.changeNo,  "
                    + " changeDesc, "
                    + " taskName , "
                    + " dollarTotalFee, "
                    + " internalDollarTotal "
                    + " from sourceDoc, targetDoc, linTask "
                    + " where id_project=? and sourceDoc.changeNo>0 "
                    + " and targetDoc.id_sourcedoc=sourceDoc.id_sourcedoc "
                    + " and linTask.id_targetDoc=targetDoc.id_targetDoc "
                    + " union "
                    + " select  "
                    + " sourceDoc.changeNo,  "
                    + " changeDesc, "
                    + " taskName , "
                    + " dollarTotal, "
                    + " internalDollarTotal "
                    + " from sourceDoc, targetDoc, dtpTask "
                    + " where id_project=? and sourceDoc.changeNo>0 "
                    + " and targetDoc.id_sourcedoc=sourceDoc.id_sourcedoc "
                    + " and dtpTask.id_targetDoc=targetDoc.id_targetDoc "
                    + " union "
                    + " select  "
                    + " sourceDoc.changeNo,  "
                    + " changeDesc, "
                    + " taskName , "
                    + " dollarTotal, "
                    + " internalDollarTotal "
                    + " from sourceDoc, targetDoc, engTask "
                    + " where id_project=? and sourceDoc.changeNo>0 "
                    + " and targetDoc.id_sourcedoc=sourceDoc.id_sourcedoc "
                    + " and engTask.id_targetDoc=targetDoc.id_targetDoc "
                    + " order by changeNo asc");
            st.setInt(1, projectId);
            st.setInt(2, projectId);
            st.setInt(3, projectId);
            ResultSet rs = st.executeQuery();
            String changeDescr = "";
            String changeDescr1 = "C1";
            String taskNames = "";
            double totalFee = 0;
            double totalTeam = 0;
            double profit = 0;
            String font = "";
            int changeNo = 0;
            while (rs.next()) {

                //System.out.println(changeNo + "-------------------------" + rs.getString("changeDesc"));
                if (changeDescr != rs.getString("changeDesc")) {
                    if (rs.getString("changeDesc") != null) {

                        // //System.out.println(taskNames+"-------------------------"+rs.getString("changeDesc"));
//                    if (changeNo != 0) {
                        if (totalTeam != 0.0) {
                            profit = (totalFee - totalTeam) / totalTeam;
                        } else {
                            profit = 0;
                        }

                        if (profit < 0.80) {
                            font = "<font color='red'>";
                        }

                        if (taskNames.length() > 0) {
                            taskNames = taskNames.substring(0, taskNames.length() - 1);
                        }
//
//                        result += "<tr><td>" + StandardCode.getInstance().noNull(rs.getString("changeDesc")) + "</td><td>" + taskNames + "</td><td>" + StandardCode.getInstance().formatDouble(new Double(totalFee)) + "</td><td>" + StandardCode.getInstance().formatDouble(new Double(totalTeam))
//                                + "</td><td>" + font + StandardCode.getInstance().formatDouble(new Double(profit * 100)) + "%</td>"
//                                + "<td><a href='../projectViewAccountingGenerateChange.do?id=" + changeNo + "&projectViewId=" + projectId + "'><img src='/logo/images/redC.gif' width='15' height='15' border='0' title='Generate Change PDF'/></td></tr>";

                        taskNames = "";
                        totalFee = 0;
                        totalTeam = 0;

                        changeDescr = rs.getString("changeDesc");
                        changeNo = rs.getInt("changeNo");
                  //   result += "<tr><td>" + changeDescr + "</td><td>" + taskNames + "</td><td>" + StandardCode.getInstance().formatDouble(new Double(totalFee)) + "</td><td>" + StandardCode.getInstance().formatDouble(new Double(totalTeam)) + "</td><td>" + font + StandardCode.getInstance().formatDouble(new Double(profit * 100)) + "%</td><td><a href='../projectViewAccountingGenerateChange.do?id=" + changeNo + "&projectViewId=" + projectId + "'><img src='/logo/images/redC.gif' width='15' height='15' border='0' title='Generate Change PDF'/></td></tr>";

                        if (taskNames.indexOf(rs.getString("taskName")) == -1) {
                            taskNames += rs.getString("taskName") + ",";
                        }
                        if (rs.getString("dollarTotalFee") != null) {
                            if (!rs.getString("dollarTotalFee").equalsIgnoreCase("")) {
                            totalFee += Double.parseDouble(rs.getString("dollarTotalFee").replaceAll(",", ""));
                            }
                        }
                        if (rs.getString("internalDollarTotal") != null) {
                            if (!rs.getString("internalDollarTotal").equalsIgnoreCase("")) {
                            totalTeam += Double.parseDouble(rs.getString("internalDollarTotal").replaceAll(",", ""));
                            }
                        }

                        if (taskNames.length() > 0) {
                            taskNames = taskNames.substring(0, taskNames.length() - 1);
                            result += "<tr><td>" + StandardCode.getInstance().noNull(rs.getString("changeDesc")) + "</td><td>" + taskNames + "</td><td>" + StandardCode.getInstance().formatDouble(new Double(totalFee)) + "</td><td>" + StandardCode.getInstance().formatDouble(new Double(totalTeam))
                                    + "</td><td>" + font + StandardCode.getInstance().formatDouble(new Double(profit * 100)) + "%</td>"
                                    + "<td><a href='../projectViewAccountingGenerateChange.do?id=" + changeNo + "&projectViewId=" + projectId + "'><img src='/logo/images/redC.gif' width='15' height='15' border='0' title='Generate Change PDF'/></td></tr>";

                        }

                        if (totalTeam != 0.0) {
                            profit = (totalFee - totalTeam) / totalTeam;
                        } else {
                            profit = 0;
                        }

                        if (profit < 0.80) {
                            font = "<font color='red'>";
                        }

                    }
                    result += "<tr><td>" + "C" + changeNo + "</td><td>" + taskNames + "</td><td>" + StandardCode.getInstance().formatDouble(new Double(totalFee)) + "</td><td>" + StandardCode.getInstance().formatDouble(new Double(totalTeam)) + "</td><td>" + font + StandardCode.getInstance().formatDouble(new Double(profit * 100)) + "%</td><td><a href='../projectViewAccountingGenerateChange.do?id=" + changeNo + "&projectViewId=" + projectId + "'><img src='/logo/images/redC.gif' width='15' height='15' border='0' title='Generate Change PDF'/></td></tr>";

                }
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

    public static Vector getSingleChangeTotal(int projectId, int changeId) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Vector result = new Vector();

        try {

            PreparedStatement st = session.connection().prepareStatement(
                    "  select "
                    + " sourceDoc.changeNo,  "
                    + " changeDesc, "
                    + " taskName , "
                    + " dollarTotalFee, "
                    + " internalDollarTotal "
                    + " from sourceDoc, targetDoc, linTask "
                    + " where id_project=? and sourceDoc.changeNo=? "
                    + " and targetDoc.id_sourcedoc=sourceDoc.id_sourcedoc "
                    + " and linTask.id_targetDoc=targetDoc.id_targetDoc "
                    + " union "
                    + " select  "
                    + " sourceDoc.changeNo,  "
                    + " changeDesc, "
                    + " taskName , "
                    + " dollarTotal, "
                    + " internalDollarTotal "
                    + " from sourceDoc, targetDoc, dtpTask "
                    + " where id_project=? and sourceDoc.changeNo=? "
                    + " and targetDoc.id_sourcedoc=sourceDoc.id_sourcedoc "
                    + " and dtpTask.id_targetDoc=targetDoc.id_targetDoc "
                    + " union "
                    + " select  "
                    + " sourceDoc.changeNo,  "
                    + " changeDesc, "
                    + " taskName , "
                    + " dollarTotal, "
                    + " internalDollarTotal "
                    + " from sourceDoc, targetDoc, engTask "
                    + " where id_project=? and sourceDoc.changeNo=? "
                    + " and targetDoc.id_sourcedoc=sourceDoc.id_sourcedoc "
                    + " and engTask.id_targetDoc=targetDoc.id_targetDoc "
                    + " order by changeNo asc");

            st.setInt(2, changeId);
            st.setInt(1, projectId);
            st.setInt(4, changeId);
            st.setInt(3, projectId);
            st.setInt(6, changeId);
            st.setInt(5, projectId);
            ResultSet rs = st.executeQuery();
            String changeDescr = "";
            String taskNames = "";
            double totalFee = 0;
            double totalTeam = 0;
            double profit = 0;
            String font = "";
            int changeNo = 0;
            while (rs.next()) {

                if (taskNames.indexOf(rs.getString("taskName")) == -1) {
                    taskNames += rs.getString("taskName") + ",";
                }
                if (rs.getString("dollarTotalFee") != null) {
                    totalFee += Double.parseDouble(rs.getString("dollarTotalFee").replaceAll(",", ""));
                }
                if (rs.getString("internalDollarTotal") != null) {
                    totalTeam += Double.parseDouble(rs.getString("internalDollarTotal").replaceAll(",", ""));
                }

            }
            if (taskNames.length() > 0) {
                taskNames = taskNames.substring(0, taskNames.length() - 1);
            }
            result.add(taskNames);
            result.add("" + totalFee);
            result.add("" + totalTeam);

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

    public static String getVendorsProjectHistory(int resourceId) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        String result = "";
        result += "<tr><td></td><td><b>  Project  </td><td><b>  Client  </td><td><b>  P.M.  </td><td><b>  Date Delivered  </td><td><b>  Tasks  </td><td><b>  Languages  </td><td><b>  Units  </td><td><b>  Amount  </td><td><b>  Invoice Received  </td><td><b>  Score  </td><td width='20%'><b>   Description</td></tr>";
        //  result += "<tr><td><font size=4>" + sdf.format(new Date()).substring(6, 10) + "</td></tr>";

        try {

            PreparedStatement st = session.connection().prepareStatement(
                    "	    select lintask.targetLanguage, lintask.sourceLanguage,  project.deliveryDate,project.startDate, internalRate, internalCurrency, wordTotal, units, score,scoreDescription,"
                    + "client_information.company_code, client_information.company_name, client_information.id_client, lintask.taskName,"
                    + " project.number, project.id_project,sourcedoc.id_project, internalDollarTotal,project.pm, lintask.receivedDateDate, lintask.invoiceDateDate, lintask.sentDateDate"
                    + " from lintask, targetdoc, sourcedoc,project, client_information"
                    + "            where"
                    + "(project.status='active' ||project.status='complete') and"
                    + "            personname='" + resourceId + "'"
                    + "            and lintask.id_targetDoc=targetdoc.id_targetDoc"
                    + "            and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc"
                    + "                    and project.id_project = sourcedoc.id_project"
                    + "                    and project.id_client= client_information.id_client and score <>0 "
                    + "            union"
                    + "            "
                    + "	    select dtptask.targetLanguage, dtptask.sourceLanguage, project.deliveryDate,project.startDate, internalRate, internalCurrency, total as wordTotal, units, score,scoreDescription,"
                    + "client_information.company_code, client_information.company_name, client_information.id_client, dtptask.taskName,"
                    + " project.number, project.id_project,sourcedoc.id_project, internalDollarTotal,project.pm, dtptask.receivedDateDate, dtptask.invoiceDateDate, dtptask.sentDateDate"
                    + " from dtptask, targetdoc, sourcedoc,project, client_information"
                    + "            where"
                    + "(project.status='active' ||project.status='complete') and"
                    + "            personname='" + resourceId + "'"
                    + "            and dtptask.id_targetDoc=targetdoc.id_targetDoc"
                    + "            and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc"
                    + "                    and project.id_project = sourcedoc.id_project"
                    + "                    and project.id_client= client_information.id_client and score <>0 "
                    + "            union"
                    + "            "
                    + "	    "
                    + "           select engtask.targetLanguage, engtask.sourceLanguage,  project.deliveryDate,project.startDate, internalRate, internalCurrency,  total as wordTotal, units, score,scoreDescription,"
                    + "client_information.company_code, client_information.company_name,client_information.id_client, engtask.taskName,"
                    + " project.number, project.id_project,sourcedoc.id_project, internalDollarTotal,project.pm, engtask.receivedDateDate, engtask.invoiceDateDate, engtask.sentDateDate"
                    + " from engtask, targetdoc, sourcedoc,project, client_information"
                    + "            where"
                    + "            personname='" + resourceId + "'"
                    + "            and engtask.id_targetDoc=targetdoc.id_targetDoc"
                    + "            and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc"
                    + "                    and project.id_project = sourcedoc.id_project"
                    + "                    and project.id_client= client_information.id_client and score <>0 "
                    + " order by startDate desc, sourceLanguage, targetLanguage ");

            ResultSet rs = st.executeQuery();
            String changeDescr = "";
            String taskNames = "";
            String languages = "";
            double totalFee = 0;
            double totalTeam = 0;
            double profit = 0;
            String font = "";
            String projectNo = "";
            String deliveryDate = "             ";
            String startDate = "             ";
            String clientCode = "";
            String units = "";
            String score = "";
            String company = "";
            String idClient = "";
            String idProject = "";
            String scoreDescription = "";
            String[] taskName = new String[30];
            Integer[] taskCount = new Integer[30];
            Integer index = 0;
            double wordTotalSum = 0;

            while (rs.next()) {

                if (projectNo != rs.getString("number")) {

                    if (projectNo != "0") {

                        if (taskNames.length() > 0) {
                            taskNames = taskNames.substring(0, taskNames.length() - 1);

                        }

                        if (languages.length() > 0) {
                            languages = languages.substring(0, languages.length() - 1);
                        }

                        if (rs.getDate("startDate") != null) {
                            String nextDeliveryDate = sdf.format(rs.getDate("startDate"));
                            try {
                                if (!nextDeliveryDate.substring(6, 10).equals(startDate.substring(6, 10))) {
                                    result += "<tr><td><font size=4>" + nextDeliveryDate.substring(6, 10) + "</td></tr>";
                                }
                            } catch (Exception e) {
                            }
                        }
//                        else if (rs.getDate("startDate") != null) {
//                            String nextStartDate = sdf.format(rs.getDate("startDate"));
//                            try {
//                                if (!nextStartDate.substring(6, 10).equals(deliveryDate.substring(6, 10))) {
//                                    result += "<tr><td colspan='2'><font size=4>" + nextStartDate.substring(6, 10) + "</td></tr>";
//                                }
//                            } catch (Exception e) {
//                            }
//                        }
                        taskNames = "";
                        languages = "";
                        totalFee = 0;
                        totalTeam = 0;
                        wordTotalSum = 0;
                        deliveryDate = "";
                        startDate = "";
                        projectNo = rs.getString("number");
                        clientCode = "";
                        units = "";
                        score = "";
                        company = "";
                        idClient = "";
                        idProject = "";
                        scoreDescription = "";

                        if (taskNames.indexOf(rs.getString("taskName")) == -1) {
                            taskNames += rs.getString("taskName") + " ";
                            for (int i = 0; i <= index; i++) {
                                try {
                                    if (StandardCode.getInstance().noNull(taskName[i]).equalsIgnoreCase(taskNames)) {
                                        taskCount[i]++;
                                    } else {
                                        taskName[i] = taskNames;
                                        taskCount[i] = 1;

                                    }
                                } catch (Exception e) {
                                }
                            }
                            //System.out.println(taskName[1] + "                       " + taskCount[1]);
                            index++;
                        }

                        if (languages.indexOf(rs.getString("targetLanguage")) == -1) {
                            languages += rs.getString("targetLanguage") + "(" + rs.getString("internalRate") + " " + rs.getString("internalCurrency") + ") ";
                        }
                // if(rs.getString("dollarTotalFee")!=null)
                        //       totalFee += Double.parseDouble(rs.getString("dollarTotalFee").replaceAll(",",""));
                        if (rs.getString("internalDollarTotal") != null && !"".equals(rs.getString("internalDollarTotal"))) {
                            totalTeam += Double.parseDouble(rs.getString("internalDollarTotal").replaceAll(",", ""));
                        }

                        try {
                            wordTotalSum += rs.getDouble("wordTotal");
                        } catch (Exception e) {
                        }
                        String fontcolor = "color:black;";
                        if (rs.getDate("deliveryDate") != null) {
                            deliveryDate = sdf.format(rs.getDate("deliveryDate"));
                        }
                        scoreDescription = rs.getString("scoreDescription");
                        String scoreDesc = "";
//                if (rs.getString("receivedDateDate") != null) {
                        if (rs.getString("score") != null) {
                            score = rs.getString("score");
                            scoreDesc = StandardCode.getInstance().noNull(scoreDescription);
                        } else {
                            score = "35";
                        }
                        if (Double.parseDouble(score) > 35) {
                            fontcolor = "color:green;";
                        } else if (Double.parseDouble(score) < 35) {
                            fontcolor = "color:red;";
                        }
//                }else{
//                   
//                    scoreDesc =  "<font color=\"Red\">Task not recieved</font>";
//                }
                        if (rs.getDate("startDate") != null) {
                            startDate = sdf.format(rs.getDate("startDate"));
                        }

                        clientCode = rs.getString("company_code");
                        units = rs.getString("units");

                        company = rs.getString("company_name");
                        idClient = rs.getString("id_client");
                        idProject = rs.getString("id_Project");
                        String invoiceDateDate="Not received yet";
                        String styleFontRed ="";
                        if (rs.getDate("invoiceDateDate") != null) {
                            invoiceDateDate = sdf.format(rs.getDate("invoiceDateDate"));
                            if (rs.getDate("sentDateDate") != null) {
                            Calendar startCalendar = new GregorianCalendar();
                            startCalendar.setTime(rs.getDate("sentDateDate"));
                            Calendar endCalendar = new GregorianCalendar();
                            endCalendar.setTime(rs.getDate("invoiceDateDate"));

                            int diffYear = startCalendar.get(Calendar.YEAR) - endCalendar.get(Calendar.YEAR);
                            int diffMonth = -(diffYear * 12 + startCalendar.get(Calendar.MONTH) - endCalendar.get(Calendar.MONTH));
                            if(diffMonth>12){
                                styleFontRed ="color:red;";
                            }else{
                                styleFontRed ="color:black;";
                            }
                            }
                        }else{
                        invoiceDateDate ="Not received yet";
                        }

                        result += "<tr><td></td><td>"
                                //                                + "<a href=\"#\" onclick=\"../resourceChangeProjectDetailsHistoryPre.do\">" + projectNo + clientCode + "</a>"
                                + "<a href=\"javascript:parent.openSingleProjectWindow('" + projectNo + clientCode + "','" + idProject + "')\">" + projectNo + clientCode + "</a>"
                                + "</td>"
                                + "<td>"
                                + "<a href=\"javascript:parent.openClientWindowReverse('" + company + "', '" + idClient + "')\">" + company + "</a>"
                                + "</td><td>" + rs.getString("pm")
                                + "</td><td>" + deliveryDate
                                + "</td><td>" + taskNames + "</td><td>" + languages
                                + "</td><td>" + wordTotalSum + " " + units
                                + "</td><td>" + StandardCode.getInstance().formatDouble(new Double(totalTeam))
                                + "</td><td  style=\"text-align: center; "+styleFontRed+"\">" + invoiceDateDate
                                + "</td><td style=\"text-align: center;" + fontcolor + "\">" + score 
                                + "</td><td>" + scoreDesc + "</td></tr>";

                    }
                }

                if (taskNames.indexOf(rs.getString("taskName")) == -1) {
                    taskNames += rs.getString("taskName") + ",";
                }

                if (languages.indexOf(rs.getString("targetLanguage")) == -1) {
                    languages += rs.getString("targetLanguage") + "(" + rs.getString("internalRate") + " " + rs.getString("internalCurrency") + "),";
                }
                // if(rs.getString("dollarTotalFee")!=null)
                //       totalFee += Double.parseDouble(rs.getString("dollarTotalFee").replaceAll(",",""));
                if (rs.getString("internalDollarTotal") != null && !"".equals(rs.getString("internalDollarTotal"))) {
                    totalTeam += Double.parseDouble(rs.getString("internalDollarTotal").replaceAll(",", ""));
                }

                try {
                    wordTotalSum += rs.getDouble("wordTotal");
                } catch (Exception e) {
                }

                if (rs.getDate("deliveryDate") != null) {
                    deliveryDate = sdf.format(rs.getDate("deliveryDate"));
                }
                if (rs.getDate("startDate") != null) {
                    startDate = sdf.format(rs.getDate("startDate"));
                }

                clientCode = rs.getString("company_code");
                units = rs.getString("units");
                if (rs.getString("score") != null) {
                    score = rs.getString("score");
                } else {
                    score = "35";
                }
                company = rs.getString("company_name");
                idClient = rs.getString("id_client");
                idProject = rs.getString("id_Project");
                scoreDescription = rs.getString("scoreDescription");

            }

            if (taskNames.length() > 0) {
                taskNames = taskNames.substring(0, taskNames.length() - 1);
            }
            if (languages.length() > 0) {
                languages = languages.substring(0, languages.length() - 1);
            }

//            result += "<tr><td></td><td>"
//                    + "<a href=\"javascript:parent.openSingleProjectWindow('" + projectNo + clientCode + "','" + idProject + "')\">" + projectNo + clientCode + "</a>"
//                    + "</td><td>"
//                    + "<a href=\"javascript:parent.openClientWindowReverse(" + company + ", " + idClient + ")\">" + company + "</a>"
//                    + "</td><td>" + deliveryDate + "</td><td>"
//                    + taskNames + "</td><td>" + languages + "</td><td>" + wordTotalSum + " " + units + "</td><td>"
//                    + StandardCode.getInstance().formatDouble(new Double(totalTeam))
//                    + "</td><td>" + score + "</td><td>" + StandardCode.getInstance().noNull(scoreDescription) + "</td></tr>";
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

    public static String getVendorsFinancialHistory(int resourceId) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        String result = "";
        result += "<tr><td><b>Year</td><td><b>Total</td><td><b>Number of projects</td></tr>";

        try {

            PreparedStatement st = session.connection().prepareStatement(
                    "select lintask.targetLanguage, lintask.sourceLanguage, project.startDate, internalRate, internalCurrency, wordTotal, units, score,"
                    + "client_information.company_code, client_information.company_name, client_information.id_client, lintask.taskName,  "
                    + " project.number, project.id_project,sourcedoc.id_project, internalDollarTotal"
                    + " from lintask, targetdoc, sourcedoc,project, client_information"
                    + "            where"
                    + "            personname='" + resourceId + "'"
                    + "            and lintask.id_targetDoc=targetdoc.id_targetDoc"
                    + "            and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc"
                    + "                    and project.id_project = sourcedoc.id_project"
                    + "                    and project.id_client= client_information.id_client"
                    + "            union"
                    + "            "
                    + "	    select dtptask.targetLanguage, dtptask.sourceLanguage, project.startDate, internalRate, internalCurrency, total as wordTotal, units, score,"
                    + "client_information.company_code, client_information.company_name, client_information.id_client, dtptask.taskName,"
                    + " project.number, project.id_project,sourcedoc.id_project, internalDollarTotal"
                    + " from dtptask, targetdoc, sourcedoc,project, client_information"
                    + "            where"
                    + "            personname='" + resourceId + "'"
                    + "            and dtptask.id_targetDoc=targetdoc.id_targetDoc"
                    + "            and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc"
                    + "                    and project.id_project = sourcedoc.id_project"
                    + "                    and project.id_client= client_information.id_client"
                    + "            union"
                    + "            "
                    + "	    "
                    + "           select engtask.targetLanguage, engtask.sourceLanguage, project.startDate, internalRate, internalCurrency,  total as wordTotal, units, score,"
                    + "client_information.company_code, client_information.company_name,client_information.id_client, engtask.taskName,"
                    + " project.number, project.id_project,sourcedoc.id_project, internalDollarTotal"
                    + " from engtask, targetdoc, sourcedoc,project, client_information"
                    + "            where"
                    + "            personname='" + resourceId + "'"
                    + "            and engtask.id_targetDoc=targetdoc.id_targetDoc"
                    + "            and sourcedoc.id_sourcedoc  = targetdoc.id_sourcedoc"
                    + "                    and project.id_project = sourcedoc.id_project"
                    + "                    and project.id_client= client_information.id_client"
                    + " order by startDate desc, sourceLanguage, targetLanguage ");

            ResultSet rs = st.executeQuery();
            String changeDescr = "";
            String taskNames = "";
            String languages = "";
            double totalFee = 0;
            double totalTeam = 0;
            double profit = 0;
            String font = "";
            int projectNo = 0;
            String startDate = "";
            String clientCode = "";
            String units = "";
            String score = "0";
            String company = "";
            String idClient = "";
            String idProject = "";
            String currentYear = "";
            int projectCount = 0;

            double wordTotalSum = 0;

            while (rs.next()) {
                String nextYear = "";
                if (rs.getDate("startDate") != null) {
                    nextYear = sdf.format(rs.getDate("startDate")).substring(6, 10);
                } else {
                    nextYear = currentYear;
                }

                if (!nextYear.equals(currentYear)) {

                    if (!currentYear.equals("")) {

                        result += "<tr><td>" + currentYear
                                + "</td><td>" + StandardCode.getInstance().formatDouble(new Double(totalTeam))
                                + "</td><td>" + projectCount + "</td></tr>";

                        taskNames = "";
                        languages = "";
                        totalFee = 0;
                        totalTeam = 0;
                        wordTotalSum = 0;
                        startDate = "";
                        projectNo = 0;
                        clientCode = "";
                        units = "";
                        score = "0";
                        company = "";
                        idClient = "";
                        idProject = "";
                        projectCount = 0;
                    }

                }

                if (projectNo != rs.getInt("number")) {
                    projectCount++;
                }
                // if(rs.getString("dollarTotalFee")!=null)
                //       totalFee += Double.parseDouble(rs.getString("dollarTotalFee").replaceAll(",",""));
                if (rs.getString("internalDollarTotal") != null && !"".equals(rs.getString("internalDollarTotal"))) {
                    totalTeam += Double.parseDouble(rs.getString("internalDollarTotal").replaceAll(",", ""));
                }

                //wordTotalSum+=rs.getDouble("wordTotal");
                if (rs.getDate("startDate") != null) {
                    currentYear = sdf.format(rs.getDate("startDate")).substring(6, 10);
                }

                clientCode = rs.getString("company_code");
                units = rs.getString("units");
                if (rs.getString("score") != null) {
                    score = rs.getString("score");
                } else {
                    score = "35";
                }
                company = rs.getString("company_name");
                idClient = rs.getString("id_client");
                idProject = rs.getString("id_Project");
                projectNo = rs.getInt("number");
            }

            result += "<tr><td>" + currentYear
                    + "</td><td>" + StandardCode.getInstance().formatDouble(new Double(totalTeam))
                    + "</td><td>" + projectCount + "</td></tr>";

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

    public static String workLoad(String pm, String expectedTime) {

        Session session = ConnectionFactory.getInstance().getSession();
        String pmString = "";
        String expectedTimeString = "";
        String result = "";
        if (pm != null) {
            pmString = "and  p.pm=pm";
        }
        if (expectedTime != null) {
            expectedTimeString = "and q.approvalTimeEsimate=expectedTime";
        }
        try {

            PreparedStatement st = session.connection().prepareStatement("select ID_Quote1,approvalTimeEsimate,pm from quote1 q Inner join Project p on q.ID_Project=p.ID_Project where p.number is not null " + pmString + "" + expectedTimeString);
            //st.setString(1, pm);
            // st.setString(2, expectedTime);
            ResultSet rs = st.executeQuery();
            result = "<table width='90%'><tr><td width='10%'><b>Project</td><td>Quote</td><td>Approval Time</td><td>Project Manager</td></tr>";
            result += "<tr><td colspan=4><hr></td></tr>";
            result += "<tr><td>" + rs.getString("ID_Quote1") + "</td><td>" + rs.getString("ID_Quote1") + "</td><td>" + rs.getString("approvalTimeEsimate") + "</td><td>" + rs.getString("pm") + "</td><td>";
            result += "</table>";
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

    public static Double getLatestCurrencyRate() {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Double conversionrate = 1.40;
        Session session = ConnectionFactory.getInstance().getSession();
        Transaction tx = null;
        /*
         * Build HQL (Hibernate Query Language) query to retrieve a list
         * of all the items currently stored by Hibernate.
         */

        String queryString = "SELECT conversionrate FROM dumpconversionrate where conversionrate>0 ORDER BY entryTime DESC  LIMIT 1";
        try {
            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement(queryString);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                conversionrate = rs.getDouble("conversionrate");
            }
            st.close();
            tx.commit();

        } catch (SQLException ex) {
            Logger.getLogger(ClientHelper.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("erooooor" + ex.getMessage());
        } catch (HibernateException e) {
            //System.out.println("erooooor" + e.getMessage());
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);

        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }
        return conversionrate;

    }

    public static void updateLanguageCount(Project p) {
        try {
            Hibernate.initialize(p.getSourceDocs());
        } catch (Exception e) {
        }
        
        int idTask1 = 0;
                String task1 = "";
               
                HashMap alreadyAdded = new HashMap();
        List sources = ProjectService.getInstance().getSourceDoc(p);
        List lang = new ArrayList();
        List srcLang = new ArrayList();
        String sL="";
        String tL="";
        for (Iterator sourceIter = sources.iterator(); sourceIter.hasNext();) {
            SourceDoc sd = (SourceDoc) sourceIter.next();
            String absSrcLang = (String) LanguageAbs.getInstance().getAbs().get(sd.getLanguage());
             if (!lang.contains(sd.getLanguage())) {
                    srcLang.add(sd.getLanguage());
                     if (sL != "") {
                            sL += ", ";
                        }
                        sL += absSrcLang;
                }
            for (Iterator linTargetIter = sd.getTargetDocs().iterator(); linTargetIter.hasNext();) {
                TargetDoc td = (TargetDoc) linTargetIter.next();
                String absTgtLang = (String) LanguageAbs.getInstance().getAbs().get(td.getLanguage());
                if (!lang.contains(td.getLanguage()) && !"All".equalsIgnoreCase(td.getLanguage())) {
                    lang.add(td.getLanguage());
                     if (tL != "") {
                            tL += ", ";
                        }
                        tL += absTgtLang;
                    idTask1=td.getTargetDocId();
                }

            }

        }
        List linTaskList = QuoteService.getInstance().getLinTask(idTask1);
                List engTaskList = QuoteService.getInstance().getEnggTask(idTask1);
                List forTaskList = QuoteService.getInstance().getFormatTask(idTask1);
                List otherTaskList = QuoteService.getInstance().getOtherTask(idTask1);

                for (int ll = 0; ll < linTaskList.size(); ll++) {
                    LinTask lt = (LinTask) linTaskList.get(ll);
                    if (alreadyAdded.get(lt.getTaskName()) == null) {

                        if (task1 != "") {
                            task1 += ", ";
                        }
                        task1 += lt.getTaskName();
                    }

                }
                for (int ll = 0; ll < engTaskList.size(); ll++) {
                    EngTask lt = (EngTask) engTaskList.get(ll);
                    if (alreadyAdded.get(lt.getTaskName()) == null) {
                        if (task1 != "") {
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
          
        
        
        
        
        p.setSrcLangCnt(sources.size());
        p.setTargetLangCnt(lang.size());
        p.setTargetLang(tL);
        p.setSrcLang(sL);
        p.setTask(task1);
        ProjectService.getInstance().updateProject(p);
    }

    public static boolean isProcessRate(Integer clientId, LinTask lt) {
       if(Objects.equals(clientId, ExcelConstants.CLIENT_BBS) && lt.getTaskName().contains("Proofreading")){
            Set<DtpTask> dtpTasks = lt.getTargetDoc().getDtpTasks();
            for (DtpTask dt : dtpTasks) {
                if(dt.getTaskName().contains("DTP")||dt.getTaskName().contains("Desktop Publishing") ){
                    if(Objects.equals(dt.getTargetDoc().getTargetDocId(), lt.getTargetDoc().getTargetDocId())){
                        return true;
                    }
                }
            }
       } 
       return false;
    }

}
