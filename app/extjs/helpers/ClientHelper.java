/*
 * ClientHelper.java
 *
 * Created on April 7, 2008, 8:48 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */
package app.extjs.helpers;

import app.admin.AdminService;
import app.admin.Ticker;
import app.client.Blog;
import app.client.Client;
import app.client.ClientContact;
import app.client.ClientLanguagePair;
import app.client.ClientOtherRate;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import app.db.*;
import static app.extjs.helpers.QuoteHelper.sdf;
import app.project.Project;
import app.project.ProjectService;
import app.project.Quality;
import app.project.QualityReport;
import app.quote.Quote1;
import app.quote.QuoteService;
import app.standardCode.StandardCode;
import app.tools.SendEmail;
import app.user.User;
import app.user.UserService;
import java.io.FileReader;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import net.sf.hibernate.Criteria;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.expression.Expression;
import net.sf.hibernate.expression.Order;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Aleks
 */
public class ClientHelper {

    public static List getClientListForPM(String pmName) {
        List results = new ArrayList();
        Integer result = null;
        Query myQuery = null;
        List temp = new ArrayList();

        Session session = ConnectionFactory.getInstance().getSession();
        try {

            Criteria criteria = session.createCriteria(Client.class);
            Hibernate.initialize(Client.class);
            try {

                criteria.add(Expression.or(Expression.like("Project_mngr", "%" + pmName + "%").ignoreCase(),
                        Expression.or(Expression.like("Sales", "%" + pmName + "%").ignoreCase(),
                                Expression.like("Sales_rep", "%" + pmName + "%").ignoreCase())));

                //remove duplicates
                criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            } catch (Exception e) {
                //System.out.println(e.toString());
            }

            List criteriaResult = criteria.list();
            for (int i = 0; i < criteriaResult.size(); i++) {
                Client c = (Client) criteriaResult.get(i);
                temp.add(c);
            }

            for (int i = 0; i < temp.size(); i++) {
                Client c = (Client) temp.get(i);

                JSONObject jo = new JSONObject();
                //jo.put("clientName",c.getCompany_name());
                jo.put("clientName", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:openClientWindowReverse('" + HrHelper.jsSafe(c.getCompany_name()) + "','" + c.getClientId() + "')\">" + c.getCompany_name() + "</a>");

                jo.put("industry", c.getIndustry().getDescription());
                jo.put("ae", c.getSales_rep());

                //Get Projects
                String totalProjectsHql
                        = "select count(project) from app.project.Project project where project.number>0 and project.Company.clientId=" + c.getClientId();
                myQuery = session.createQuery(totalProjectsHql);
                result = (Integer) myQuery.uniqueResult();
                jo.put("totalProjects", result.intValue());
                ////System.out.println(c.getCompany_name()+" has "+result+" projects.");
                //Get Quotes
                String totalQuotesHql
                        = "select count(quote) from app.quote.Quote1 quote where quote.Project.Company.clientId=" + c.getClientId();
                myQuery = session.createQuery(totalQuotesHql);
                result = (Integer) myQuery.uniqueResult();
                jo.put("totalQuotes", result.intValue());
                ////System.out.println(c.getCompany_name()+" has "+result+" quotes.");
                //Get Active Projects
                String totalActiveProjectsHql
                        = "select count(project) from app.project.Project project where project.Company.clientId=" + c.getClientId() + " and project.status='active' and project.number>0";
                myQuery = session.createQuery(totalActiveProjectsHql);
                result = (Integer) myQuery.uniqueResult();
                jo.put("totalActiveProjects", result.intValue());
                ////System.out.println(c.getCompany_name()+" has "+result+" active projects.");

                //Get Quotes
                String totalPendingQuotesHql
                        = "select count(quote) from app.quote.Quote1 quote where quote.Project.Company.clientId=" + c.getClientId() + " and quote.status='pending'";
                ;
                myQuery = session.createQuery(totalPendingQuotesHql);
                result = (Integer) myQuery.uniqueResult();
                jo.put("totalPendingQuotes", result.intValue());
                ////System.out.println(c.getCompany_name()+" has "+result+" pending quotes.");

                ////System.out.println(c.getCompany_name()+" has "+result+" projects.");
                results.add(jo);
            }

//            Hibernate.initialize(results);
        } catch (HibernateException e) {
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } catch (JSONException ex) {
            Logger.getLogger(ClientHelper.class.getName()).log(Level.SEVERE, null, ex);
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

//        results = new ArrayList(set);
        if (results.isEmpty()) {
            return null;
        } else {

            //System.out.println("resultsssssss" + results.toString());
            return results;
        }
    }
    
    public static List getClientListForPMPrint(String pmName) {
        List results = new ArrayList();
        Integer result = null;
        Query myQuery = null;
        List temp = new ArrayList();

        Session session = ConnectionFactory.getInstance().getSession();
        try {

            Criteria criteria = session.createCriteria(Client.class);
            Hibernate.initialize(Client.class);
            try {

                criteria.add(Expression.or(Expression.like("Project_mngr", "%" + pmName + "%").ignoreCase(),
                        Expression.or(Expression.like("Sales", "%" + pmName + "%").ignoreCase(),
                                Expression.like("Sales_rep", "%" + pmName + "%").ignoreCase())));

                //remove duplicates
                criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            } catch (Exception e) {
                //System.out.println(e.toString());
            }

            List criteriaResult = criteria.list();
            for (int i = 0; i < criteriaResult.size(); i++) {
                Client c = (Client) criteriaResult.get(i);
                temp.add(c);
            }

            for (int i = 0; i < temp.size(); i++) {
                Client c = (Client) temp.get(i);

                JSONObject jo = new JSONObject();
                //jo.put("clientName",c.getCompany_name());
                jo.put("Client Name", c.getCompany_name());

                jo.put("Industry", c.getIndustry().getDescription());
                jo.put("AE", c.getSales_rep());

                //Get Projects
                String totalProjectsHql
                        = "select count(project) from app.project.Project project where project.number>0 and project.Company.clientId=" + c.getClientId();
                myQuery = session.createQuery(totalProjectsHql);
                result = (Integer) myQuery.uniqueResult();
                jo.put("Total Projects", result.intValue());
                ////System.out.println(c.getCompany_name()+" has "+result+" projects.");
                //Get Quotes
                String totalQuotesHql
                        = "select count(quote) from app.quote.Quote1 quote where quote.Project.Company.clientId=" + c.getClientId();
                myQuery = session.createQuery(totalQuotesHql);
                result = (Integer) myQuery.uniqueResult();
                jo.put("Total Quotes", result.intValue());
                ////System.out.println(c.getCompany_name()+" has "+result+" quotes.");
                //Get Active Projects
                String totalActiveProjectsHql
                        = "select count(project) from app.project.Project project where project.Company.clientId=" + c.getClientId() + " and project.status='active' and project.number>0";
                myQuery = session.createQuery(totalActiveProjectsHql);
                result = (Integer) myQuery.uniqueResult();
                jo.put("Total Active Projects", result.intValue());
                ////System.out.println(c.getCompany_name()+" has "+result+" active projects.");

                //Get Quotes
                String totalPendingQuotesHql
                        = "select count(quote) from app.quote.Quote1 quote where quote.Project.Company.clientId=" + c.getClientId() + " and quote.status='pending'";
                ;
                myQuery = session.createQuery(totalPendingQuotesHql);
                result = (Integer) myQuery.uniqueResult();
                jo.put("Total Pending Quotes", result.intValue());
                ////System.out.println(c.getCompany_name()+" has "+result+" pending quotes.");

                ////System.out.println(c.getCompany_name()+" has "+result+" projects.");
                results.add(jo);
            }

//            Hibernate.initialize(results);
        } catch (HibernateException e) {
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } catch (JSONException ex) {
            Logger.getLogger(ClientHelper.class.getName()).log(Level.SEVERE, null, ex);
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

//        results = new ArrayList(set);
        if (results.isEmpty()) {
            return null;
        } else {

            //System.out.println("resultsssssss" + results.toString());
            return results;
        }
    }

    public static List getClientListForBackupPM(String pmName) {
        List results = new ArrayList();
        Integer result = null;
        Query myQuery = null;
        List temp = new ArrayList();

        List<Integer> qNumber = new ArrayList<Integer>();

        Session session = ConnectionFactory.getInstance().getSession();

        try {

            Criteria criteria = session.createCriteria(Client.class);
            Hibernate.initialize(Client.class);
            try {

                criteria.add(Expression.like("Backup_pm", "%" + pmName + "%").ignoreCase());
                //remove duplicates
                criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            } catch (Exception e) {
                //System.out.println(e.toString());
            }

            List criteriaResult = criteria.list();
            for (int i = 0; i < criteriaResult.size(); i++) {
                Client c = (Client) criteriaResult.get(i);
                temp.add(c);
            }

            for (int i = 0; i < temp.size(); i++) {
                Client c = (Client) temp.get(i);

                JSONObject jo = new JSONObject();
                //jo.put("clientName",c.getCompany_name());
                jo.put("clientName", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:openClientWindowReverse('" + HrHelper.jsSafe(c.getCompany_name()) + "'," + c.getClientId() + ")\">" + c.getCompany_name() + "</a>");

                jo.put("industry", c.getIndustry().getDescription());
                jo.put("ae", c.getSales_rep());

                //Get Projects
                String totalProjectsHql
                        = "select count(project) from app.project.Project project where project.Company.clientId=" + c.getClientId();
                myQuery = session.createQuery(totalProjectsHql);
                result = (Integer) myQuery.uniqueResult();
                jo.put("totalProjects", result.intValue());
                ////System.out.println(c.getCompany_name()+" has "+result+" projects.");
                //Get Quotes
                String totalQuotesHql
                        = "select count(quote) from app.quote.Quote1 quote where quote.Project.Company.clientId=" + c.getClientId();
                myQuery = session.createQuery(totalQuotesHql);
                result = (Integer) myQuery.uniqueResult();
                jo.put("totalQuotes", result.intValue());
                ////System.out.println(c.getCompany_name()+" has "+result+" quotes.");
                //Get Active Projects
                String totalActiveProjectsHql
                        = "select count(project) from app.project.Project project where project.Company.clientId=" + c.getClientId() + " and project.status='active'";
                myQuery = session.createQuery(totalActiveProjectsHql);
                result = (Integer) myQuery.uniqueResult();
                jo.put("totalActiveProjects", result.intValue());
                ////System.out.println(c.getCompany_name()+" has "+result+" active projects.");

                //Get Quotes
                String totalPendingQuotesHql
                        = "select count(quote) from app.quote.Quote1 quote where quote.Project.Company.clientId=" + c.getClientId() + " and quote.status='pending'";
                ;
                myQuery = session.createQuery(totalPendingQuotesHql);
                result = (Integer) myQuery.uniqueResult();
                jo.put("totalPendingQuotes", result.intValue());
                ////System.out.println(c.getCompany_name()+" has "+result+" pending quotes.");

                ////System.out.println(c.getCompany_name()+" has "+result+" projects.");
                results.add(jo);
            }

//            Hibernate.initialize(results);
        } catch (HibernateException e) {
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } catch (JSONException ex) {
            Logger.getLogger(ClientHelper.class.getName()).log(Level.SEVERE, null, ex);
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

//        results = new ArrayList(set);
        if (results.isEmpty()) {
            return null;
        } else {

            //System.out.println("resultsssssss" + results.toString());
            return results;
        }
    }

    //get all active projects
    public static List getClientListForPM_DEP(String pmName) {
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
            query = session.createQuery("select client from app.client.Client client order by client.Company_name where client.Project_mngr='" + pmName + "' or client.Sales_rep='" + pmName + "' OR client.Sales='" + pmName + "'");
            List temp = query.list();

            List results = new ArrayList();
            Integer result = null;
            Query myQuery = null;
            for (int i = 0; i < temp.size(); i++) {
                Client c = (Client) temp.get(i);

                JSONObject jo = new JSONObject();
                //jo.put("clientName",c.getCompany_name());
                jo.put("clientName", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:openClientWindowReverse('" + HrHelper.jsSafe(c.getCompany_name()) + "','" + c.getClientId() + "')\">" + c.getCompany_name() + "</a>");

                jo.put("industry", c.getIndustry().getDescription());
                jo.put("ae", c.getSales_rep());

                //Get Projects
                String totalProjectsHql
                        = "select count(project) from app.project.Project project where project.number>0 and project.Company.clientId=" + c.getClientId();
                myQuery = session.createQuery(totalProjectsHql);
                result = (Integer) myQuery.uniqueResult();
                jo.put("totalProjects", result.intValue());
                ////System.out.println(c.getCompany_name()+" has "+result+" projects.");
                //Get Quotes
                String totalQuotesHql
                        = "select count(quote) from app.quote.Quote1 quote where quote.Project.Company.clientId=" + c.getClientId();
                myQuery = session.createQuery(totalQuotesHql);
                result = (Integer) myQuery.uniqueResult();
                jo.put("totalQuotes", result.intValue());
                ////System.out.println(c.getCompany_name()+" has "+result+" quotes.");
                //Get Active Projects
                String totalActiveProjectsHql
                        = "select count(project) from app.project.Project project where project.Company.clientId=" + c.getClientId() + " and project.status='active' and project.number>0";
                myQuery = session.createQuery(totalActiveProjectsHql);
                result = (Integer) myQuery.uniqueResult();
                jo.put("totalActiveProjects", result.intValue());
                ////System.out.println(c.getCompany_name()+" has "+result+" active projects.");

                //Get Quotes
                String totalPendingQuotesHql
                        = "select count(quote) from app.quote.Quote1 quote where quote.Project.Company.clientId=" + c.getClientId() + " and quote.status='pending'";
                ;
                myQuery = session.createQuery(totalPendingQuotesHql);
                result = (Integer) myQuery.uniqueResult();
                jo.put("totalPendingQuotes", result.intValue());
                ////System.out.println(c.getCompany_name()+" has "+result+" pending quotes.");

                ////System.out.println(c.getCompany_name()+" has "+result+" projects.");
                results.add(jo);

            }

            return results;
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

    public static List getClientListForPMan(String pmName) {
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
            query = session.createQuery("select client from app.client.Client client order by client.Company_name where client.Project_mngr='" + pmName + "'");
            List results = query.list();

            return results;
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

    //get all active projects
    public static List getClientListForBackupPM_DEP(String pmName) {
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
            query = session.createQuery("select client from app.client.Client client order by client.Company_name where client.Backup_pm='" + pmName + "'");
            List temp = query.list();

            List results = new ArrayList();
            Integer result = null;
            Query myQuery = null;
            for (int i = 0; i < temp.size(); i++) {
                Client c = (Client) temp.get(i);

                JSONObject jo = new JSONObject();
                //jo.put("clientName",c.getCompany_name());
                jo.put("clientName", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:openClientWindowReverse('" + HrHelper.jsSafe(c.getCompany_name()) + "'," + c.getClientId() + ")\">" + c.getCompany_name() + "</a>");

                jo.put("industry", c.getIndustry().getDescription());
                jo.put("ae", c.getSales_rep());

                //Get Projects
                String totalProjectsHql
                        = "select count(project) from app.project.Project project where project.Company.clientId=" + c.getClientId();
                myQuery = session.createQuery(totalProjectsHql);
                result = (Integer) myQuery.uniqueResult();
                jo.put("totalProjects", result.intValue());
                ////System.out.println(c.getCompany_name()+" has "+result+" projects.");
                //Get Quotes
                String totalQuotesHql
                        = "select count(quote) from app.quote.Quote1 quote where quote.Project.Company.clientId=" + c.getClientId();
                myQuery = session.createQuery(totalQuotesHql);
                result = (Integer) myQuery.uniqueResult();
                jo.put("totalQuotes", result.intValue());
                ////System.out.println(c.getCompany_name()+" has "+result+" quotes.");
                //Get Active Projects
                String totalActiveProjectsHql
                        = "select count(project) from app.project.Project project where project.Company.clientId=" + c.getClientId() + " and project.status='active'";
                myQuery = session.createQuery(totalActiveProjectsHql);
                result = (Integer) myQuery.uniqueResult();
                jo.put("totalActiveProjects", result.intValue());
                ////System.out.println(c.getCompany_name()+" has "+result+" active projects.");

                //Get Quotes
                String totalPendingQuotesHql
                        = "select count(quote) from app.quote.Quote1 quote where quote.Project.Company.clientId=" + c.getClientId() + " and quote.status='pending'";
                ;
                myQuery = session.createQuery(totalPendingQuotesHql);
                result = (Integer) myQuery.uniqueResult();
                jo.put("totalPendingQuotes", result.intValue());
                ////System.out.println(c.getCompany_name()+" has "+result+" pending quotes.");

                ////System.out.println(c.getCompany_name()+" has "+result+" projects.");
                results.add(jo);

            }

            return results;
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

    //get all active projects
    public static List getKeyPersonal(String clientId) {
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
            query = session.createQuery("select clientContact from app.client.ClientContact clientContact where clientContact.Company.clientId=" + clientId + " and clientContact.key_personnel=true and clientContact.display=true");
            List temp = query.list();

            List results = new ArrayList();
            Integer result = null;
            Query myQuery = null;

            //Get Projects
            String totalProjectsAllHql
                    = "select count(project) from app.project.Project project where project.Company.clientId=" + clientId;
            myQuery = session.createQuery(totalProjectsAllHql);
            result = (Integer) myQuery.uniqueResult();
            String totalProjects = " (" + result + ")";

            for (int i = 0; i < temp.size(); i++) {
                ClientContact c = (ClientContact) temp.get(i);

                JSONObject jo = new JSONObject();
                //jo.put("clientName",c.getCompany_name());
                // jo.put("clientContactName",+" href=\"javascript:openClientWindow('"+ c.getClientId()+"','"+HrHelper.jsSafe(c.getCompany_name())+"')\">"+c.getCompany_name()+"</a>");
                jo.put("clientContactName", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openSingleContactWindow('" + c.getLast_name() + ", " + c.getFirst_name() + "','" + c.getClientContactId() + "')\">" + c.getLast_name() + ", " + c.getFirst_name() + "</a>");
                jo.put("title", c.getTitle());
                jo.put("city", c.getCity());
                jo.put("country", c.getCountry());

                //Get Projects
                String totalProjectsHql
                        = "select count(project) from app.project.Project project where project.Contact.clientContactId=" + c.getClientContactId();
                myQuery = session.createQuery(totalProjectsHql);
                result = (Integer) myQuery.uniqueResult();
                jo.put("totalProjects", result.intValue() + totalProjects);
                ////System.out.println(c.getCompany_name()+" has "+result+" projects.");
                //Get Quotes

                ////System.out.println(c.getCompany_name()+" has "+result+" pending quotes.");
                ////System.out.println(c.getCompany_name()+" has "+result+" projects.");
                results.add(jo);

            }

            return results;
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

    public static boolean updateClientLinRates(Client c, String jsonComm) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from ClientLanguagePair where ID_Client=? and task like 'LIN -%'");
            st.setInt(1, c.getClientId().intValue());
            st.executeUpdate();

            if (jsonComm != null && !"".equals(jsonComm)) {
                JSONArray comm = new JSONArray(jsonComm);

                for (int i = 0; i < comm.length(); i++) {
                    JSONObject j = (JSONObject) comm.get(i);

                    String task = "";
                    String rate = "";
                    String units = "";
                    if (!"0".equals(j.getString("AlignmentRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("AlignmentRate"));
                        pr.setUnits(j.getString("AlignmentUnits"));
                        pr.setTask("LIN - Alignment");
                        pr.setTypeOfText(j.getString("TypeOfText"));
                        session.save(pr);
                    }

                    if (!"0".equals(j.getString("GlossaryRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("GlossaryRate"));
                        pr.setUnits(j.getString("GlossaryUnits"));
                        pr.setTask("LIN - Glossary Creation");
                        pr.setTypeOfText(j.getString("TypeOfText"));
                        session.save(pr);
                    }

                    if (!"0".equals(j.getString("TranslationRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("TranslationRate"));
                        pr.setUnits(j.getString("TranslationUnits"));
                        pr.setTask("LIN - Translation");
                        pr.setTypeOfText(j.getString("TypeOfText"));
                        session.save(pr);
                    }

                    if (!"0".equals(j.getString("EditingRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("EditingRate"));
                        pr.setUnits(j.getString("EditingUnits"));
                        pr.setTask("LIN - Editing");
                        pr.setTypeOfText(j.getString("TypeOfText"));
                        session.save(pr);
                    }

                    if (!"0".equals(j.getString("RecruitmentRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("RecruitmentRate"));
                        pr.setUnits(j.getString("RecruitmentUnits"));
                        pr.setTask("LIN - Recruitment of ICR Board");
                        pr.setTypeOfText(j.getString("TypeOfText"));
                        session.save(pr);
                    }

                    if (!"0".equals(j.getString("ICRRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("ICRRate"));
                        pr.setUnits(j.getString("ICRUnits"));
                        pr.setTask("LIN - In-Country / Client Review");
                        pr.setTypeOfText(j.getString("TypeOfText"));
                        session.save(pr);
                    }

                    if (!"0".equals(j.getString("ImplementationRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("ImplementationRate"));
                        pr.setUnits(j.getString("ImplementationUnits"));
                        pr.setTask("LIN - Implementation");
                        pr.setTypeOfText(j.getString("TypeOfText"));
                        session.save(pr);
                    }

                    if (!"0".equals(j.getString("ProofreadingRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("ProofreadingRate"));
                        pr.setUnits(j.getString("ProofreadingUnits"));
                        pr.setTask("LIN - Proofreading / Linguistic QA");
                        pr.setTypeOfText(j.getString("TypeOfText"));
                        session.save(pr);
                    }

                    if (!"0".equals(j.getString("BackRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("BackRate"));
                        pr.setUnits(j.getString("BackUnits"));
                        pr.setTask("LIN - Back Translation");
                        pr.setTypeOfText(j.getString("TypeOfText"));
                        session.save(pr);
                    }

                    if (!"0".equals(j.getString("TranslationEditingRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("TranslationEditingRate"));
                        pr.setUnits(j.getString("TranslationEditingUnits"));
                        pr.setTask("LIN - Translation+Editing");
                        pr.setTypeOfText(j.getString("TypeOfText"));
                        session.save(pr);
                    }

                    if (!"0".equals(j.getString("EvaluationRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("EvaluationRate"));
                        pr.setUnits(j.getString("EvaluationUnits"));
                        pr.setTask("LIN - Evaluation");
                        pr.setTypeOfText(j.getString("TypeOfText"));
                        session.save(pr);
                    }

                    if (!"0".equals(j.getString("OtherRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("OtherRate"));
                        pr.setUnits(j.getString("OtherUnits"));
                        pr.setTask("LIN - Other");
                        pr.setTypeOfText(j.getString("TypeOfText"));
                        session.save(pr);
                    }

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

    public static boolean updateClientDtpRates(Client c, String jsonComm) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from ClientLanguagePair where ID_Client=? and task like 'DTP -%'");
            st.setInt(1, c.getClientId().intValue());
            st.executeUpdate();

            if (jsonComm != null && !"".equals(jsonComm)) {
                JSONArray comm = new JSONArray(jsonComm);

                for (int i = 0; i < comm.length(); i++) {
                    JSONObject j = (JSONObject) comm.get(i);

                    String task = "";
                    String rate = "";
                    String units = "";

                    if (!"0".equals(j.getString("DesktopRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("DesktopRate"));
                        pr.setUnits(j.getString("DesktopUnits"));
                        pr.setTask("DTP - Desktop Publishing");
                        session.save(pr);
                    }

                    if (!"0".equals(j.getString("GraphicsRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("GraphicsRate"));
                        pr.setUnits(j.getString("GraphicsUnits"));
                        pr.setTask("DTP - Graphics Localization");
                        session.save(pr);
                    }

                    if (!"0".equals(j.getString("SpecialRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("SpecialRate"));
                        pr.setUnits(j.getString("SpecialUnits"));
                        pr.setTask("DTP - Special Output");
                        session.save(pr);
                    }

                    if (!"0".equals(j.getString("MultilingualRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("MultilingualRate"));
                        pr.setUnits(j.getString("MultilingualUnits"));
                        pr.setTask("DTP - Multilingual Deliverable");
                        session.save(pr);
                    }

                    if (!"0".equals(j.getString("GenerationRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("GenerationRate"));
                        pr.setUnits(j.getString("GenerationUnits"));
                        pr.setTask("DTP - Generation of Graphics Files");
                        session.save(pr);
                    }

                    if (!"0".equals(j.getString("CompilationRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("CompilationRate"));
                        pr.setUnits(j.getString("CompilationUnits"));
                        pr.setTask("DTP - Compilation");
                        session.save(pr);
                    }

                    if (!"0".equals(j.getString("OtherRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("OtherRate"));
                        pr.setUnits(j.getString("OtherUnits"));
                        pr.setTask("DTP - Other");
                        session.save(pr);
                    }

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

    public static boolean updateClientEngRates(Client c, String jsonComm) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from ClientLanguagePair where ID_Client=? and task like 'ENG -%'");
            st.setInt(1, c.getClientId().intValue());
            st.executeUpdate();

            if (jsonComm != null && !"".equals(jsonComm)) {
                JSONArray comm = new JSONArray(jsonComm);

                for (int i = 0; i < comm.length(); i++) {
                    JSONObject j = (JSONObject) comm.get(i);

                    String task = "";
                    String rate = "";
                    String units = "";

                    if (!"0".equals(j.getString("TMRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("TMRate"));
                        pr.setUnits(j.getString("TMUnits"));
                        pr.setTask("ENG - TM Management/Processing");
                        session.save(pr);
                    }

                    if (!"0".equals(j.getString("ProcessRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("ProcessRate"));
                        pr.setUnits(j.getString("ProcessUnits"));
                        pr.setTask("ENG - In-Process QA");
                        session.save(pr);
                    }

                    if (!"0".equals(j.getString("PreparationRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("PreparationRate"));
                        pr.setUnits(j.getString("PreparationUnits"));
                        pr.setTask("ENG - Preparation / Analysis / Verification");
                        session.save(pr);
                    }

                    if (!"0".equals(j.getString("TestingRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("TestingRate"));
                        pr.setUnits(j.getString("TestingUnits"));
                        pr.setTask("ENG - Testing / Troubleshooting");
                        session.save(pr);
                    }

                    if (!"0".equals(j.getString("FunctionalityRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("FunctionalityRate"));
                        pr.setUnits(j.getString("FunctionalityUnits"));
                        pr.setTask("ENG - Functionality Testing");
                        session.save(pr);
                    }

                    if (!"0".equals(j.getString("CompilationRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("CompilationRate"));
                        pr.setUnits(j.getString("CompilationUnits"));
                        pr.setTask("ENG - Compilation");
                        session.save(pr);
                    }

                    if (!"0".equals(j.getString("FinalRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("FinalRate"));
                        pr.setUnits(j.getString("FinalUnits"));
                        pr.setTask("ENG - Final QA");
                        session.save(pr);
                    }

                    if (!"0".equals(j.getString("OtherRate"))) {
                        ClientLanguagePair pr = new ClientLanguagePair();
                        pr.setCompany(c);
                        pr.setSource(j.getString("Source"));
                        pr.setTarget(j.getString("Target"));
                        pr.setRate(j.getString("OtherRate"));
                        pr.setUnits(j.getString("OtherUnits"));
                        pr.setTask("ENG - Other");
                        session.save(pr);
                    }

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

    public static boolean updateClientOthRates(Client c, String jsonComm) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from ClientOtherRate where ID_Client=?");
            st.setInt(1, c.getClientId().intValue());
            st.executeUpdate();
            st.close();
            ////System.out.println("jsonComm="+jsonComm);

            if (jsonComm != null && !"".equals(jsonComm)) {
                JSONArray comm = new JSONArray(jsonComm);

                for (int i = 0; i < comm.length(); i++) {
                    JSONObject j = (JSONObject) comm.get(i);
                    ClientOtherRate pr = new ClientOtherRate();
                    pr.setCompany(c);
                    pr.setRate(j.getString("rate"));
                    pr.setUnits(j.getString("unit"));
                    pr.setNote(j.getString("note"));
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

    ///////////////////////////////
    public static List getClientTop10(int clientId) {

        Session session = ConnectionFactory.getInstance().getSession();
        List results = new ArrayList();

        try {
            String sql
                    = "select lt.sourceLanguage, lt.targetLanguage, "
                    + "sum(lt.word100)+sum(lt.wordRep)+sum(lt.word95)+sum(lt.word85)+  "
                    + "sum(lt.word75)+sum(lt.wordNew)+sum(lt.word8599) totalWordCount,  "
                    + "sum(lt.word75)+sum(lt.wordNew) countless85, "
                    + "sum(lt.word85)+sum(lt.word8599)+sum(lt.word95) count8599, "
                    + "sum(lt.wordRep) countReps, sum(lt.word100) count100 "
                    + "from sourceDoc sd, targetDoc td, lintask lt, project p "
                    + "where p.id_client=? and sd.id_project=p.id_project and sd.id_sourceDoc=td.id_sourcedoc "
                    + "and (p.status='Complete' or p.status='active') "
                    + "and lt.id_targetdoc=td.id_targetdoc  group by lt.sourceLanguage, lt.targetLanguage order by totalWordCount desc "
                    + " limit 0,10";

            ////System.out.println("sql="+sql);
            PreparedStatement st = session.connection().prepareStatement(sql);
            st.setInt(1, clientId);

            ////System.out.println("jsonComm="+jsonComm);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                JSONObject jo = new JSONObject();
                jo.put("sourceLanguage", rs.getString("sourceLanguage"));
                jo.put("targetLanguage", rs.getString("targetLanguage"));
                jo.put("totalWordCount", rs.getInt("totalWordCount"));
                jo.put("countless85", rs.getInt("countless85"));
                jo.put("countReps", rs.getInt("countReps"));
                jo.put("count100", rs.getInt("count100"));
                // jo.put("count95",rs.getInt("count95"));
                jo.put("count8599", rs.getInt("count8599"));
                jo.put("sourceTarget", rs.getString("sourceLanguage") + " to " + rs.getString("targetLanguage"));

                results.add(jo);
            }

            st.close();
        } catch (Exception e) {

            System.err.println("Hibernate Exception:" + e.getMessage());
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
                    System.err.println("Hibernate Exception:" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }

        return results;
    }

    ///////////////////////////////
    public static List getClientStatsProjectSize(int clientId) {

        Session session = ConnectionFactory.getInstance().getSession();
        List results = new ArrayList();

        try {
            String sql
                    = "select myYear, CASE "
                    + "  WHEN projectAmount BETWEEN 0 AND 2000 THEN '0-2,000' "
                    + "   WHEN projectAmount BETWEEN 2001 AND 5000 THEN '2,001-5,000' "
                    + "       WHEN projectAmount BETWEEN 5001 AND 10000 THEN '5,001-10,000' "
                    + " WHEN projectAmount BETWEEN 10001 AND 50000 THEN '10,001-20,000' "
                    + " WHEN projectAmount BETWEEN 20001 AND 50000 THEN '20,001-50,000' "
                    + " WHEN projectAmount BETWEEN 50001 AND 75000 THEN '50,001-75,000' "
                    + " WHEN projectAmount BETWEEN 75001 AND 50000 THEN '75,001-100,000' "
                    + "     ELSE 'over 100,000' "
                    + " END AS amountRange, count(*) myCountAll "
                    + "  from "
                    + " ( "
                    + " select myYear, "
                    + " sum(feeTotal) projectAmount, "
                    + " count(*), id_project "
                    + " from( "
                    + " select "
                    + " extract(YEAR from  p.startDate) myYear, "
                    + " sum(DollarTotalFee) feeTotal, "
                    + " p.id_project id_project "
                    + "  from sourceDoc sd, targetDoc td, project p, lintask lt "
                    + " where "
                    + " p.id_client=? "
                    + "  and sd.id_project=p.id_project "
                    + "   and sd.id_sourceDoc=td.id_sourcedoc "
                    + " and lt.id_targetdoc=td.id_targetdoc "
                    + " and (p.status='Complete' or p.status='active') and p.startDate is not null "
                    + " group by myYear,id_project "
                    + " union all "
                    + " select "
                    + " extract(YEAR from  p.startDate) myYear, "
                    + " sum(dtp.dollarTotal) feeTotal, "
                    + " p.id_project id_project "
                    + "  from sourceDoc sd, targetDoc td, project p, dtptask dtp "
                    + " where "
                    + " p.id_client=? and "
                    + " sd.id_project=p.id_project "
                    + "   and sd.id_sourceDoc=td.id_sourcedoc "
                    + " and dtp.id_targetdoc=td.id_targetdoc "
                    + " and (p.status='Complete' or p.status='active') and p.startDate is not null "
                    + " group by myYear,id_project "
                    + " union all "
                    + " select "
                    + " extract(YEAR from  p.startDate) myYear, "
                    + " sum(eng.dollarTotal) feeTotal, "
                    + " p.id_project id_project "
                    + "  from sourceDoc sd, targetDoc td, project p, engtask eng where "
                    + " p.id_client=? and "
                    + " sd.id_project=p.id_project and sd.id_sourceDoc=td.id_sourcedoc and eng.id_targetdoc=td.id_targetdoc "
                    + " and (p.status='Complete' or p.status='active') and p.startDate is not null  group by myYear,id_project "
                    + " union all "
                    + " select extract(YEAR from  p.startDate) myYear, "
                    + " sum(oth.dollarTotal) feeTotal, "
                    + " p.id_project id_project "
                    + "  from sourceDoc sd, targetDoc td, project p, othtask oth "
                    + " where p.id_client=? and sd.id_project=p.id_project "
                    + "   and sd.id_sourceDoc=td.id_sourcedoc and oth.id_targetdoc=td.id_targetdoc and (p.status='Complete' or p.status='active') and p.startDate is not null "
                    + " group by myYear,id_project "
                    + " ) myUnion "
                    + " group by myYear,id_project "
                    + "  ) myFinalResult "
                    + " group by myYear, amountRange ";

            //System.out.println("sql=" + sql);

            PreparedStatement st = session.connection().prepareStatement(sql);
            st.setInt(1, clientId);
            st.setInt(2, clientId);
            st.setInt(3, clientId);
            st.setInt(4, clientId);
            ////System.out.println("jsonComm="+jsonComm);
            ResultSet rs = st.executeQuery();
            int previousYear = -1;
            JSONObject jo = null;
            while (rs.next()) {
                ////System.out.println(rs.getInt("myyear")+","+rs.getString("amountRange")+","+rs.getInt("myCountAll"));
                if (rs.getInt("myyear") != previousYear) {
                    if (previousYear != -1) {
                        results.add(jo);
                    }
                    jo = new JSONObject();
                    previousYear = rs.getInt("myyear");
                    jo.put("dummyValue", "Project Size");
                    jo.put("myyear", rs.getInt("myyear"));
                }

                jo.put(rs.getString("amountRange"), rs.getInt("myCountAll"));

            }
            //Add last record
            if (previousYear != -1) {
                results.add(jo);
            }

            st.close();
        } catch (Exception e) {

            System.err.println("Hibernate Exception:" + e.getMessage());
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
                    System.err.println("Hibernate Exception:" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }

        return results;
    }

    ///////////////////////////////
    public static List getClientStatsPM(int clientId) {

        Session session = ConnectionFactory.getInstance().getSession();
        List results = new ArrayList();

        try {
            String sql
                    = "select sum(feeTotal) projectTotal,pm FROM all_pm_amounts_view  group by pm";

            ////System.out.println("sql="+sql);
            HashMap totalsTable = new HashMap();
            PreparedStatement st = session.connection().prepareStatement(sql);
            ResultSet rs = null;
            /*     ResultSet rs = st.executeQuery();
             while(rs.next()){
             totalsTable.put(rs.getString("pm"), rs.getString("projectTotal"));
             }
             *
             */
            st.close();

            String newSQL
                    = "           select sum(feeTotal) projectTotal,pm "
                    + "from( "
                    + "select "
                    + "sum(DollarTotalFee) feeTotal,pm "
                    + "from sourceDoc sd, targetDoc td, project p, lintask lt "
                    + "where "
                    + "p.id_client=? and "
                    + "sd.id_project=p.id_project "
                    + " and sd.id_sourceDoc=td.id_sourcedoc "
                    + "and lt.id_targetdoc=td.id_targetdoc "
                    + "and (p.status='Complete' or p.status='active') "
                    + "group by pm "
                    + "union all "
                    + "select "
                    + "sum(DollarTotal) feeTotal,pm "
                    + " from sourceDoc sd, targetDoc td, project p, dtptask lt "
                    + "where "
                    + "p.id_client=? and "
                    + "sd.id_project=p.id_project "
                    + "  and sd.id_sourceDoc=td.id_sourcedoc "
                    + "and lt.id_targetdoc=td.id_targetdoc "
                    + "and (p.status='Complete' or p.status='active') "
                    + "group by pm "
                    + "union all "
                    + "select "
                    + "sum(DollarTotal) feeTotal,pm "
                    + " from sourceDoc sd, targetDoc td, project p, engtask lt "
                    + "where "
                    + "p.id_client=? and "
                    + "sd.id_project=p.id_project "
                    + "  and sd.id_sourceDoc=td.id_sourcedoc "
                    + "and lt.id_targetdoc=td.id_targetdoc "
                    + "and (p.status='Complete' or p.status='active') "
                    + "group by pm "
                    + "union all "
                    + "select "
                    + "sum(DollarTotal) feeTotal,pm "
                    + " from sourceDoc sd, targetDoc td, project p, othtask lt "
                    + "where "
                    + "p.id_client=? and "
                    + "sd.id_project=p.id_project "
                    + "  and sd.id_sourceDoc=td.id_sourcedoc "
                    + "and lt.id_targetdoc=td.id_targetdoc "
                    + "and (p.status='Complete' or p.status='active') "
                    + "group by pm "
                    + ") myUnion group by pm; ";

            st = session.connection().prepareStatement(newSQL);
            st.setInt(1, clientId);
            st.setInt(2, clientId);
            st.setInt(3, clientId);
            st.setInt(4, clientId);
            rs = st.executeQuery();
            ////System.out.println("jsonComm="+jsonComm);

            while (rs.next()) {

                String pm = rs.getString("pm");
                //System.out.println("XXXXX" + pm);
                String newSQL2
                        = "           select sum(feeTotal) projectTotal,pm "
                        + "from( "
                        + "select "
                        + "sum(DollarTotalFee) feeTotal,pm "
                        + "from sourceDoc sd, targetDoc td, project p, lintask lt "
                        + "where "
                        + "p.pm=? and "
                        + "sd.id_project=p.id_project "
                        + " and sd.id_sourceDoc=td.id_sourcedoc "
                        + "and lt.id_targetdoc=td.id_targetdoc "
                        + "and (p.status='Complete' or p.status='active') "
                        + "group by pm "
                        + "union all "
                        + "select "
                        + "sum(DollarTotal) feeTotal,pm "
                        + " from sourceDoc sd, targetDoc td, project p, dtptask lt "
                        + "where "
                        + "p.pm=? and "
                        + "sd.id_project=p.id_project "
                        + "  and sd.id_sourceDoc=td.id_sourcedoc "
                        + "and lt.id_targetdoc=td.id_targetdoc "
                        + "and (p.status='Complete' or p.status='active') "
                        + "group by pm "
                        + "union all "
                        + "select "
                        + "sum(DollarTotal) feeTotal,pm "
                        + " from sourceDoc sd, targetDoc td, project p, engtask lt "
                        + "where "
                        + "p.pm=? and "
                        + "sd.id_project=p.id_project "
                        + "  and sd.id_sourceDoc=td.id_sourcedoc "
                        + "and lt.id_targetdoc=td.id_targetdoc "
                        + "and (p.status='Complete' or p.status='active') "
                        + "group by pm "
                        + "union all "
                        + "select "
                        + "sum(DollarTotal) feeTotal,pm "
                        + " from sourceDoc sd, targetDoc td, project p, othtask lt "
                        + "where "
                        + "p.pm=? and "
                        + "sd.id_project=p.id_project "
                        + "  and sd.id_sourceDoc=td.id_sourcedoc "
                        + "and lt.id_targetdoc=td.id_targetdoc "
                        + "and (p.status='Complete' or p.status='active') "
                        + "group by pm "
                        + ") myUnion group by pm; ";

                PreparedStatement st2 = session.connection().prepareStatement(newSQL2);
                st2.setString(1, pm);
                st2.setString(2, pm);
                st2.setString(3, pm);
                st2.setString(4, pm);
                ResultSet rs2 = st2.executeQuery();
                rs2.next();
                double total = rs2.getDouble("projectTotal");
                //System.out.println("XXXXX" + total);

                JSONObject jo = new JSONObject();
                jo.put("pm", pm);
                double allProjectsTotal = 0;
                //     if( true){
                //   allProjectsTotal = Double.parseDouble((String)totalsTable.get(rs.getString("pm")));
                //   }
                jo.put("allProjectsTotal", total);
                jo.put("projectTotal", rs.getDouble("projectTotal"));
                // jo.put("projectTotal",total);
                String percentage = StandardCode.getInstance().formatMoney(((rs.getDouble("projectTotal") / total)) * 100) + "%";
                jo.put("percentage", percentage);

                results.add(jo);
            }

            st.close();
        } catch (Exception e) {

            System.err.println("Hibernate Exception:" + e.getMessage());
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
                    System.err.println("Hibernate Exception:" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }

        return results;
    }
    private static NumberFormat format = NumberFormat.getIntegerInstance();
    //format an integer value

    public static String formatInteger(int num) {

        format.setGroupingUsed(true);
        return format.format(num);
    }

    ///////////////////////////////
    public static List getClientStatsWorkLoad(int clientId) {

        Session session = ConnectionFactory.getInstance().getSession();
        List results = new ArrayList();

        try {
            String sql
                    = "select extract(YEAR from  p.startDate) myYear, extract(month from  p.startDate) myMonth, "
                    + "sum(lt.word100)+sum(lt.wordRep)+sum(lt.word95)+sum(lt.word85)+ "
                    + "sum(lt.word75)+sum(lt.wordNew)+sum(lt.word8599) myWordCount, "
                    + "(select count(*) from project p2 where id_client=? and "
                    + "(select extract(YEAR from  p2.startDate))=myYear and (select extract(month from  p2.startDate))=myMonth) projectCount "
                    + " from sourceDoc sd, targetDoc td, lintask lt, project p "
                    + "where "
                    + "p.id_client=? "
                    + "and sd.id_project=p.id_project "
                    + "and sd.id_sourceDoc=td.id_sourcedoc "
                    + "and (p.status='Complete' or p.status='active') and p.startDate is not null "
                    + "and lt.id_targetdoc=td.id_targetdoc  group by myYear, myMonth "
                    + "order by myyear desc";

            ////System.out.println("sql="+sql);
            String[] mymonths = {"", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

            PreparedStatement st = session.connection().prepareStatement(sql);
            st.setInt(1, clientId);
            st.setInt(2, clientId);
            ////System.out.println("jsonComm="+jsonComm);
            ResultSet rs = st.executeQuery();
            int previousYear = -1;
            JSONObject jo = null;
            while (rs.next()) {

                if (rs.getInt("myyear") != previousYear) {
                    if (previousYear != -1) {
                        results.add(jo);
                    }
                    previousYear = rs.getInt("myyear");
                    jo = new JSONObject();
                    jo.put("myYear", rs.getInt("myYear"));
                }
                jo.put(mymonths[rs.getInt("myMonth")] + "_myWordProjectCount", formatInteger(rs.getInt("myWordCount")) + " (" + rs.getInt("projectCount") + ")");
                //jo.put("projectCount",rs.getInt("projectCount"));
            }

            st.close();
        } catch (Exception e) {

            System.err.println("Hibernate Exception:" + e.getMessage());
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
                    System.err.println("Hibernate Exception:" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }

        return results;
    }

    public static List getClientStatsVolume(int clientId) {

        Session session = ConnectionFactory.getInstance().getSession();
        List results = new ArrayList();

        try {
            String sql
                    = "select myYear,  "
                    + "(select count(*) from project p2 where p2.id_client=? and (select extract(YEAR from  p2.startDate))=myYear) myCount, "
                    + "(select sum(projectAmount) from project p2 where p2.id_client=? and (select extract(YEAR from  p2.startDate))=myYear) myFee,"
                    + "(SELECT sum(REPLACE(pmPercentDollarTotal,',','')) FROM project p2 where p2.id_client=? and (select extract(YEAR from  p2.startDate))=myYear) pmTotal, "
                    + "sum(myWordCount) myWordCount, sum(countless85) countless85,sum(count8599)count8599, "
                    + "sum(countRep) countRep,sum(count100) count100, "
                    + "sum(totalLinTeam) totalLinTeamSum,sum(totalLinFee) totalLinFeeSum,sum(dtpFeeTotal) dtpFeeTotalSum,sum(dtpTeamTotal) dtpTeamTotalSum, "
                    + "sum(engFeeTotal) engFeeTotalSum,sum(engTeamTotal) engTeamTotalSum,sum(othFeeTotal) othFeeTotalSum,sum(othTeamTotal) othTeamTotalSum "
                    + "from( select extract(YEAR from  p.startDate) myYear, "
                    + "sum(lt.word100)+sum(lt.wordRep)+sum(lt.word95)+sum(lt.word85)+ "
                    + "sum(lt.word75)+sum(lt.wordNew)+sum(lt.word8599) myWordCount, "
                    + "sum(lt.word75)+sum(lt.wordNew) countless85, "
                    + "sum(lt.word85)+sum(lt.word8599) count8599, "
                    + "sum(lt.wordRep) countRep,  "
                    + "sum(lt.word100) count100, "
                    + "sum(REPLACE(DollarTotalFee,',','')) totalLinFee, "
                    + "sum(REPLACE(InternalDollarTotal,',','')) totalLinTeam, "
                    + "0 dtpFeeTotal, "
                    + "0 dtpTeamTotal, "
                    + "0 engFeeTotal, "
                    + "0 engTeamTotal, "
                    + "0 othFeeTotal, "
                    + "0 othTeamTotal "
                    + " from sourceDoc sd, targetDoc td, lintask lt, project p where "
                    + " p.id_client=? "
                    + " and sd.id_project=p.id_project "
                    + " and sd.id_sourceDoc=td.id_sourcedoc "
                    + "and lt.id_targetdoc=td.id_targetdoc "
                    + "and (p.status='Complete' or p.status='active') and p.startDate is not null  "
                    + "group by myYear union all "
                    + "select extract(YEAR from  p.startDate) myYear, "
                    + "0 myWordCount, "
                    + "0 countless85, "
                    + "0 count8599, "
                    + "0 countRep, "
                    + "0 count100, "
                    + "0 totalLinTeam, "
                    + "0 totalLinFee, "
                    + "sum(REPLACE(dtp.dollarTotal,',','')) dtpFeeTotal, "
                    + "sum(REPLACE(dtp.InternalDollarTotal,',','')) dtpTeamTotal, "
                    + "0 engFeeTotal, "
                    + "0 engTeamTotal, "
                    + "0 othFeeTotal, "
                    + "0 othTeamTotal "
                    + " from sourceDoc sd, targetDoc td, project p, dtptask dtp where "
                    + " p.id_client=? "
                    + "and sd.id_project=p.id_project "
                    + "  and sd.id_sourceDoc=td.id_sourcedoc "
                    + "and dtp.id_targetdoc=td.id_targetdoc "
                    + "and (p.status='Complete' or p.status='active')  and p.startDate is not null  "
                    + "group by myYear "
                    + "union all   "
                    + "select "
                    + "extract(YEAR from  p.startDate) myYear, "
                    + "0 myWordCount, "
                    + "0 countless85, "
                    + "0 count8599, "
                    + "0 countRep, "
                    + "0 count100, "
                    + "0 totalLinTeam, "
                    + "0 totalLinFee, "
                    + "0 dtpFeeTotal, "
                    + "0 dtpTeamTotal, "
                    + "sum(REPLACE(dtp.dollarTotal,',','')) engFeeTotal, "
                    + "sum(REPLACE(dtp.InternalDollarTotal,',','')) engTeamTotal, "
                    + "0 othFeeTotal, "
                    + "0 othTeamTotal "
                    + "from sourceDoc sd, targetDoc td, project p, engtask dtp where "
                    + " p.id_client=? "
                    + "and sd.id_project=p.id_project "
                    + "  and sd.id_sourceDoc=td.id_sourcedoc "
                    + "and dtp.id_targetdoc=td.id_targetdoc "
                    + "and (p.status='Complete' or p.status='active')  and p.startDate is not null  "
                    + "group by myYear "
                    + "union all select extract(YEAR from  p.startDate) myYear, "
                    + "0 myWordCount, "
                    + "0 countless85, "
                    + "0 count8599, "
                    + "0 countRep, "
                    + "0 count100, "
                    + "0 totalLinTeam, "
                    + "0 totalLinFee, "
                    + "0 dtpFeeTotal, "
                    + "0 dtpTeamTotal, "
                    + "0 engFeeTotal, "
                    + "0 engTeamTotal, "
                    + "sum(REPLACE(dtp.dollarTotal,',','')) othFeeTotal, "
                    + "sum(REPLACE(dtp.InternalDollarTotal,',','')) othTeamTotal "
                    + " from sourceDoc sd, targetDoc td, project p, othtask dtp where "
                    + " p.id_client=? "
                    + " and sd.id_project=p.id_project "
                    + "  and sd.id_sourceDoc=td.id_sourcedoc "
                    + "and dtp.id_targetdoc=td.id_targetdoc "
                    + "and (p.status='Complete' or p.status='active')  and p.startDate is not null  "
                    + "group by myYear "
                    + ") myUnion  group by myYear";

            //System.out.println("sql="+sql);
            PreparedStatement st = session.connection().prepareStatement(sql);
            st.setInt(1, clientId);
            st.setInt(2, clientId);
            st.setInt(3, clientId);
            st.setInt(4, clientId);
            st.setInt(5, clientId);
            st.setInt(6, clientId);
            st.setInt(7, clientId);
            ////System.out.println("jsonComm="+jsonComm);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                JSONObject jo = new JSONObject();
                jo.put("myYear", rs.getInt("myYear"));
                jo.put("myFee", rs.getDouble("myFee"));
                jo.put("myCount", rs.getInt("myCount"));
                jo.put("myWordCount", rs.getInt("myWordCount"));
                jo.put("countless85", rs.getInt("countless85"));
                jo.put("count8599", rs.getInt("count8599"));
                jo.put("countRep", rs.getInt("countRep"));
                jo.put("count100", rs.getInt("count100"));

                jo.put("totalLinTeamSum", rs.getDouble("totalLinTeamSum"));
                jo.put("totalLinFeeSum", rs.getDouble("totalLinFeeSum"));
                jo.put("dtpFeeTotalSum", rs.getDouble("dtpFeeTotalSum"));
                jo.put("dtpTeamTotalSum", rs.getDouble("dtpTeamTotalSum"));
                jo.put("engFeeTotalSum", rs.getDouble("engFeeTotalSum"));
                jo.put("engTeamTotalSum", rs.getDouble("engTeamTotalSum"));
                jo.put("othFeeTotalSum", rs.getDouble("othFeeTotalSum"));
                jo.put("othTeamTotalSum", rs.getDouble("othTeamTotalSum"));
                jo.put("pmTotal", rs.getDouble("pmTotal"));

                double totalFeeYear = rs.getDouble("totalLinFeeSum") + rs.getDouble("dtpFeeTotalSum")
                        + rs.getDouble("engFeeTotalSum") + rs.getDouble("othFeeTotalSum") + rs.getDouble("pmTotal");
                double totalTeamYear = rs.getDouble("totalLinTeamSum") + rs.getDouble("dtpTeamTotalSum")
                        + rs.getDouble("engTeamTotalSum") + rs.getDouble("othTeamTotalSum");

                jo.put("totalFeeYear", totalFeeYear);
                jo.put("totalTeamYear", totalTeamYear);

                if (totalFeeYear != 0) {
                    jo.put("percentageProfit", StandardCode.getInstance().formatMoney((100 * totalTeamYear / totalFeeYear)) + "%");
                } else {
                    jo.put("percentageProfit", "0%");
                }
                if (rs.getInt("myCount") != 0) {
                    jo.put("avgProjectSize", totalFeeYear / rs.getInt("myCount"));
                } else {
                    jo.put("avgProjectSize", 0);
                }

                results.add(jo);
            }

            st.close();
        } catch (Exception e) {

            System.err.println("Hibernate Exception:" + e.getMessage());
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
                    System.err.println("Hibernate Exception:" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }

        return results;
    }

///////////////////////////////
    public static String getClientNamesForComboBox() {

        Session session = ConnectionFactory.getInstance().getSession();
        String results = "";

        try {
            String sql
                    = "select Company_name,ID_Client FROM client_information order by Company_name asc";

            ////System.out.println("sql="+sql);
            PreparedStatement st = session.connection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                results += "[\"" + rs.getString("ID_Client") + "\",\"" + rs.getString("Company_name") + "\"]";

                if (!rs.isLast()) {
                    results += ",\n";
                }
            }
            st.close();

        } catch (Exception e) {

            System.err.println("Hibernate Exception:" + e.getMessage());
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
                    System.err.println("Hibernate Exception:" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }

        return results;
    }

    public static List getBlogList() {
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
            query = session.createQuery("select author,topic,firstDraft,finalDraft,postedOn,id from blog ");
            List temp = query.list();

            List results = new ArrayList();
            Integer result = null;
            Query myQuery = null;
            for (int i = 0; i < temp.size(); i++) {
                Blog c = (Blog) temp.get(i);
                //System.out.println("list val>>>>>>>>>>>" + c.getAuthor());

                ////System.out.println(c.getCompany_name()+" has "+result+" projects.");
            }

            return results;
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

    //get all active projects
    public static List getAllClientContacts(String clientId) {
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
            query = session.createQuery("select clientContact from app.client.ClientContact clientContact where clientContact.Company.clientId=" + clientId + " and clientContact.display=true");
            List temp = query.list();

            return temp;
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

    //get all clients
    public static List getProjectInformals(String projectId) {
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

            query = session.createQuery("select pi from app.extjs.vo.ProjectInformal pi where ID_Project= " + projectId);
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

    public static boolean updateProjectInformals(int projectId, String jsonComm, String userName) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from project_informal where ID_Project=?");
            st.setInt(1, projectId);
            st.executeUpdate();
            Project proj = ProjectService.getInstance().getSingleProject(projectId);

            if (jsonComm != null && !"".equals(jsonComm)) {
                JSONArray comm = new JSONArray(jsonComm);
                //    //System.out.println("comm.length()="+comm.length());
                for (int i = 0; i < comm.length(); i++) {
                    JSONObject j = (JSONObject) comm.get(i);
                    app.extjs.vo.ProjectInformal pr = new app.extjs.vo.ProjectInformal();
                    pr.setID_Project(new Integer(projectId));

                    try {
                        pr.setFeedback(j.getString("Feedback"));
                    } catch (Exception e) {
                    }
                    try {
                        pr.setInformalDate(sdf.parse(j.getString("InformalDate")));
                    } catch (Exception e) {
                    }
                    try {
                        pr.setClientContact(j.getString("ClientContact"));
                    } catch (Exception e) {
                    }
                    try {
                        pr.setDepartment(j.getString("Department"));
                    } catch (Exception e) {
                    }
                    try {
                        pr.setDetails("");
                    } catch (Exception e) {
                    }

                    try {
                        pr.setFromRole(j.getString("FromRole"));
                    } catch (Exception e) {
                    }
                    try {
                        pr.setLanguage(j.getString("Language"));
                    } catch (Exception e) {
                    }
                    try {
                        pr.setTextType(j.getString("TextType"));
                    } catch (Exception e) {
                    }
                    try {
                        pr.setRate(j.getString("Rate"));
                    } catch (Exception e) {
                    }
                    try {
                        pr.setCause(j.getString("Cause"));
                    } catch (Exception e) {
                    }
                    try {
                        if(StandardCode.getInstance().noNull(j.getString("Disposition")).equalsIgnoreCase("Valid (NC)"))
                            pr.setNc(j.getString("nc"));
                        
                    } catch (Exception e) {
                    }
                    try {
                        pr.setDisposition(j.getString("Disposition"));
                    } catch (Exception e) {
                    }
                    try {
                        pr.setNotes(j.getString("Notes"));
                    } catch (Exception e) {
                    }

                    //pr.setSatisfaction(new Integer(j.getString("Satisfaction")));
                    session.save(pr);
                    try {

                        if (j.getString("Details").equalsIgnoreCase("new")) {
                            Calendar cal = Calendar.getInstance();
                            SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yy");
                            
                            String fname="C://templates/qualityAlert.html";
                            // fname="/Users/abhisheksingh/Desktop/qualityAlert.html";

                                java.io.File file= new java.io.File(fname);
                                int len;
                                  char[] chr = new char[4096];
                                  final StringBuffer buffer = new StringBuffer();
                                  final FileReader reader = new FileReader(file);
                                  try {
                                      while ((len = reader.read(chr)) > 0) {
                                          buffer.append(chr, 0, len);
                                      }
                                  } finally {
                                      reader.close();
                                  }
                            ////System.out.println(buffer.toString());
                            String emailMsgTxt=buffer.toString();
                            emailMsgTxt = emailMsgTxt.replaceAll("_USERNAME_", userName);
                            emailMsgTxt = emailMsgTxt.replaceAll("_PROJECTNUMBER_", proj.getNumber() + proj.getCompany().getCompany_code());
                            emailMsgTxt = emailMsgTxt.replaceAll("_CURRENTTIME_", sdf1.format(cal.getTime()));
                            emailMsgTxt = emailMsgTxt.replaceAll("_RATE_", j.getString("Rate"));
                            emailMsgTxt = emailMsgTxt.replaceAll("_CLIENTCONTACT_", j.getString("ClientContact"));
                            emailMsgTxt = emailMsgTxt.replaceAll("_LANGUAGE_", j.getString("Language"));
                            emailMsgTxt = emailMsgTxt.replaceAll("_TEXTTYPE_", j.getString("TextType"));
                            emailMsgTxt = emailMsgTxt.replaceAll("_FEEDBACK_", j.getString("Feedback"));
                            
                            
//                             Client client=ClientService.getInstance().getSingleClient(clientId);

//                     String emailMsgTxt="<div>Hello,</div><div>&nbsp;</div><div>This message is automatically generated by ExcelNet.</div><div>&nbsp;</div><b>"
//                                        + userName +"</b> has made the following entry in <b>"+proj.getNumber()+proj.getCompany().getCompany_code()+"</b>:<br>"
//                                        +"<br>=============================<br><br>"+sdf1.format(cal.getTime())+" &ndash; <b>"+j.getString("Rate")+"<br>"
//                             + ""+j.getString("Feedback")+"<br>"
//                                        +"<br>=============================<br><br>Best regards,<br><br>ExcelNet Administrator<br><br>"
//                                        + "<div><img src=https://excelnet.xltrans.com/logo/images/excel-logo-blue.jpg><div>";
//                            String emailMsgTxt = "<p><font face=\"verdana\" size=\"2\">Hello,</font></p>\n"
//                                    + "<p><font face=\"verdana\" size=\"2\">This message is automatically generated by ExcelNet.</font></p>\n"
//                                    + "<p><font face=\"verdana\" size=\"2\">" + userName + " has made the following entry in " + proj.getNumber() + proj.getCompany().getCompany_code() + ":</font></p>\n"
//                                    + "<p><font face=\"verdana\" size=\"2\">=============================</font></p>\n"
//                                    + "<p><font face=\"verdana\" size=\"2\">" + sdf1.format(cal.getTime()) + "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; " + j.getString("Rate") + "</font></p>\n"
//                                    + "<p><font face=\"verdana\" size=\"2\">Name:&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; " + j.getString("ClientContact") + "</font></p>\n"
//                                    + "<p><font face=\"verdana\" size=\"2\">Language: &nbsp; &nbsp; &nbsp; " + j.getString("Language") + "</font></p>\n"
//                                    + "<p><font face=\"verdana\" size=\"2\">Text Type: &nbsp; &nbsp; &nbsp; " + j.getString("TextType") + "</font></p>\n"
//                                    + "<p class=\"MsoNormal\" style=\"margin-bottom: 0.0001pt;\"></p>\n"
//                                    + "<p><font face=\"verdana\" size=\"2\">Feedback:&nbsp; &nbsp; &nbsp; &nbsp; " + j.getString("Feedback") + "</font></p>\n"
//                                    + "<p><font face=\"verdana\" size=\"2\">=============================</font></p>" + "<br>"
//                                    + "<br>=============================<br><br>Best regards,<br><br>ExcelNet Administrator<br><br>"
//                                    + "<div><img src=https://excelnet.xltrans.com/logo/images/excel-logo-blue.jpg><div>";
                            String toAddress = "";

                            List tickerList = AdminService.getInstance().getTickerList(10);

                            User pm = UserService.getInstance().getSingleUser(proj.getPm_id());
                            try {
                                if (!tickerList.contains(pm.getWorkEmail1())) {
                                    if (toAddress.equalsIgnoreCase("")) {
                                        try {
                                            toAddress = pm.getWorkEmail1();
                                        } catch (Exception e) {
                                        }
                                    } else {
                                        try {
                                            toAddress += "," + pm.getWorkEmail1();
                                        } catch (Exception e) {
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }
                            try {
                                String[] aeEmail = (proj.getAe()).split(" ", 2);
                                User ae = UserService.getInstance().getSingleCurrentUserRealName(aeEmail[0], aeEmail[1]);
                                if (!tickerList.contains(ae.getWorkEmail1())) {
                                    if (toAddress.equalsIgnoreCase("")) {
                                        try {
                                            toAddress = ae.getWorkEmail1();
                                        } catch (Exception e) {
                                        }
                                    } else {
                                        try {
                                            toAddress += "," + ae.getWorkEmail1();
                                        } catch (Exception e) {
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }

                            for (int ii = 0; ii < tickerList.size(); ii++) {
                                Ticker ticker = (Ticker) tickerList.get(ii);
                                if (toAddress.equalsIgnoreCase("")) {
                                    try {
                                        toAddress = ticker.getUserEmail();
                                    } catch (Exception e) {
                                    }
                                } else {
                                    try {
                                        toAddress += "," + ticker.getUserEmail();
                                    } catch (Exception e) {
                                    }
                                }
                            }
                           
                            String[] emailList = toAddress.split(",");
                            //System.out.println("app.extjs.helpers.ClientHelper.updateProjectInformals()\n"+emailMsgTxt);
                            String emailSubjectTxt = "Quality Alert: " + proj.getNumber() + proj.getCompany().getCompany_code();

                            SendEmail smtpMailSender = new SendEmail();
                            smtpMailSender.postMail(emailList, emailSubjectTxt, emailMsgTxt, "excelnet@xltrans.com");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

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

    public static String getClientQuality(int clientId) {
        //System.out.println("getClientQuality@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        Session session = ConnectionFactory.getInstance().getSession();
        String results = "<table id='ClientQuality' class='tableHighlight' style='white-space:normal;width:900px'";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            String sql
                    = "select * from client_information, project, project_informal "
                    + " where "
                    + " client_information.id_client=? "
                    + " and client_information.id_client=project.id_client "
                    + " and project_informal.id_project=project.id_project";

            //System.out.println("sql=" + sql);

            PreparedStatement st = session.connection().prepareStatement(sql);
            st.setInt(1, clientId);

            ResultSet rs = st.executeQuery();
            results += "<tr><td colspan=10><B>FEEDBACK</td></tr>";
            results += "<tr><td><b>Project</td><td><b>Date</td><td><b>From</td><td><b>Name</td><td><b>Department</td><td><b>Language</td><td><b>Details</td><td><b>Rate</td><td><b>Feedback</td><td><b>Satisfaction</td></tr>";

            int totalCount = 0;
            int totalSatisfaction = 0;

            while (rs.next()) {

                results += "<tr>";

                results += "<td width=80px >" + "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openProjectWindow('" + rs.getInt("number") + rs.getString("Company_code") + "','" + rs.getString("ID_Project") + "')\">" + rs.getInt("number") + rs.getString("Company_code") + "</a>" + "</td>";

                String informalDate = "";
                if (rs.getDate("InformalDate") != null) {

                    informalDate = sdf.format(rs.getDate("InformalDate"));
                }
                results += "<td width=90px >" + informalDate + "</td>";
                results += "<td width=100px >" + StandardCode.getInstance().noNull(rs.getString("FromRole")) + "</td>";
                results += "<td width=130px >" + StandardCode.getInstance().noNull(rs.getString("ClientContact")) + "</td>";
                results += "<td width=160px >" + StandardCode.getInstance().noNull(rs.getString("Department")) + "</td>";
                results += "<td width=70px >" + StandardCode.getInstance().noNull(rs.getString("Language")) + "</td>";
                results += "<td width=180px>" + StandardCode.getInstance().noNull(rs.getString("Details")) + "</td>";
                results += "<td width=70px >" + StandardCode.getInstance().noNull(rs.getString("Rate")) + "</td>";
                results += "<td width=250px>" + StandardCode.getInstance().noNull(rs.getString("Feedback")) + "</td>";

                int satisfaction = 0;
                /*10- Very positive
                 9- Positive
                 8- Somewhat positive
                 7- Neutral
                 6 - Somewhat negative
                 5- Negative
                 4-Very negative
                 */

                if ("Very positive".equalsIgnoreCase(rs.getString("Rate"))) {
                    satisfaction = 10;
                } else if ("Positive".equalsIgnoreCase(rs.getString("Rate"))) {
                    satisfaction = 9;
                } else if ("Somewhat positive".equalsIgnoreCase(rs.getString("Rate"))) {
                    satisfaction = 8;
                } else if ("Neutral".equalsIgnoreCase(rs.getString("Rate"))) {
                    satisfaction = 7;
                } else if ("Somewhat negative".equalsIgnoreCase(rs.getString("Rate"))) {
                    satisfaction = 6;
                } else if ("Negative".equalsIgnoreCase(rs.getString("Rate"))) {
                    satisfaction = 5;
                } else if ("Very negative".equalsIgnoreCase(rs.getString("Rate"))) {
                    satisfaction = 4;
                }

                /*if(satisfaction!=0){*/
                totalCount++;
                totalSatisfaction += satisfaction;
                //}
                results += "<td width='8%' align='center'>" + satisfaction + "</td>";
                results += "</tr>";

            }
            st.close();
            results += "</table>";

            NumberFormat format1 = NumberFormat.getInstance();
            format1.setGroupingUsed(true);
            format1.setMaximumFractionDigits(1);

            String lastVal = "0";

            if (totalCount > 0) {
                lastVal = format1.format((totalSatisfaction * 1.0));
            }
            String colorPref = "Green";
            double satisfaction = 0.1;
            double satisfactionScore = 0.00;
            if (totalCount != 0) {
                satisfaction = (totalSatisfaction * 1.0) / totalCount;
                satisfactionScore = satisfaction;
            }
            // results += "<table border='1' width='95%'><tr><td align='right'><font color='blue'><b>Overall Satisfaction: "+ lastVal +"</td></tr></table>";
            results += "<br><br><table border='1' width='95%'><tr><td align='right'><font color='blue'><b>Overall Satisfaction: " + format1.format(satisfactionScore) + "</td></tr></table>";
            if (satisfaction <= 5) {
                colorPref = "Red";
            }
            if (satisfaction <= 8) {
                colorPref = "Orange";
            }

            results += "<table border='1' width='95%'><tr><td align='right'width='" + format1.format(satisfaction * 10) + "%' bgcolor='" + colorPref + "'><font color='" + colorPref + "'>|</td></td><td></td></tr></table>";
            results += "<table width='95%'><tr><td align='left' width='1%'>0   </td>"
                    + "<td align='right' width='9%'>1   </td>"
                    + "<td align='right' width='10%'>2   </td>"
                    + "<td align='right' width='10%'>3   </td>"
                    + "<td align='right' width='10%'>4   </td>"
                    + "<td align='right' width='10%'>5   </td>"
                    + "<td align='right' width='10%'>6   </td>"
                    + "<td align='right' width='10%'>7   </td>"
                    + "<td align='right' width='10%'>8   </td>"
                    + "<td align='right' width='10%'>9   </td>"
                    + "<td align='right'>10</td></tr></table>";
        } catch (Exception e) {

            System.err.println("Hibernate Exception:" + e.getMessage());
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
                    System.err.println("Hibernate Exception:" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }

        return results;
    }

    public static String getClientNC(int clientId) {
        //System.out.println("getClientNC@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        Session session = ConnectionFactory.getInstance().getSession();
        String results = "<table id='ClientNC' class='tableHighlight' width='95%'>";
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            String sql
                    = "select *, quality.number as ncNumber, project.number as projectNumber from client_information, project, quality "
                    + " where "
                    + " client_information.id_client=? "
                    + " and client_information.id_client=project.id_client "
                    + " and quality.id_project=project.id_project";

            //System.out.println("sql=" + sql);

            PreparedStatement st = session.connection().prepareStatement(sql);
            st.setInt(1, clientId);

            ResultSet rs = st.executeQuery();

            ////System.out.println("result size of client NC>>>>>>>>>>>>>>>>>>>>>>"+rs.getString(1));
            results += "<tr><td colspan=5><B>NONCONFORMITY</td></tr>";
            results += "<tr><td><b>Project</td><td><b>Quality Number</td><td><b>Issue</td><td><b>Date Raised</td><td><b>Minor/Major</td><td><b>Date Closed</td></tr>";

            int totalCount = 0;
            int totalSatisfaction = 0;

            while (rs.next()) {

                results += "<tr>";

                results += "<td>" + "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openProjectWindow('" + rs.getInt("number") + rs.getString("Company_code") + "','" + rs.getString("ID_Project") + "')\">" + rs.getInt("projectNumber") + rs.getString("Company_code") + "</a>" + "</td>";

                String qualityNumber = rs.getInt("projectNumber") + rs.getString("Company_code") + "-NC-" + rs.getString("ncNumber");
                results += "<td>" + qualityNumber + "</td>";

                results += "<td>" + rs.getString("issue") + "</td>";

                String dateRaised = "";
                if (rs.getDate("dateRaised") != null) {

                    dateRaised = sdf.format(rs.getDate("dateRaised"));
                }
                results += "<td>" + dateRaised + "</td>";

                results += "<td>" + rs.getString("minorMajor") + "</td>";

                String dateClosed = "";
                if (rs.getDate("dateClosed") != null) {

                    dateClosed = sdf.format(rs.getDate("dateClosed"));
                }
                results += "<td>" + dateClosed + "</td>";

                results += "</tr>";

            }
            st.close();
            results += "</table>";

        } catch (Exception e) {

            System.err.println("Hibernate Exception:" + e.getMessage());
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
                    System.err.println("Hibernate Exception:" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }

        return results;
    }

    public static boolean updateDepartmentInventory(String inventory, String updatedBy) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from departmentsInventory");

            st.executeUpdate();
            st.close();
            st = session.connection().prepareStatement("insert into departmentsInventory (inventory,lastUpdatedBy) values(?,?)");
            st.setString(1, inventory);
            st.setString(2, updatedBy);
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

    public static ArrayList getDepartmentInventory() {

        Session session = ConnectionFactory.getInstance().getSession();
        ArrayList t = new ArrayList();
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("select * from departmentsInventory");

            ResultSet rs = st.executeQuery();
            if (rs.next()) {

                t.add(rs.getString("inventory"));
                t.add(rs.getString("lastUpdatedBy"));
                t.add("" + rs.getTimestamp("lastUpdateTs").getTime());
            } else {
                t.add("");
                t.add("");
                t.add(new Date());
            }
            st.close();
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

    //get all clients feedback
    public static List getClientFeedback(Integer clientId) {
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

            query = session.createQuery("select cf from app.client.ClientFeedback cf where clientId= " + clientId);
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

    //delete all clients feedback
    public static void deleteClientFeedback(Integer id) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
//         Session session = ConnectionFactory.getInstance().getSession();
//        ArrayList t = new ArrayList();
        Transaction tx = null;
        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            //      query =	session.createQuery("select product from app.extjs.vo.Product product where ID_Client= "+ clientId + " order by product.product ");
            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from app.client.ClientFeedback cf where feedbackId=?");
            st.setInt(1, id);
            st.executeUpdate();
//            query = session.createQuery("delete from app.client.ClientFeedback cf where feedbackId= " + id);
//            return query.list();
        } catch (SQLException ex) {
            Logger.getLogger(ClientHelper.class.getName()).log(Level.SEVERE, null, ex);
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

    public static String getClientQuality(String sDate, String eDate) {
        //System.out.println("getClientQuality@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        Session session = ConnectionFactory.getInstance().getSession();
        List results = new ArrayList();
        List finalresults = new ArrayList();
        Transaction tx = null;
        String resultString = "";

        String queryStringQuality = "select pr.ID_Client as cid, cinfo.Company_name as cName, AVG(CASE "
                + "when pin.rate ='Very positive' then 10 "
                + "when pin.rate ='Positive' then 9 "
                + "when pin.rate ='Somewhat positive' then 8 "
                + "when pin.rate ='Neutral' then 0 "
                + "when pin.rate ='Somewhat negative' then 6 "
                + "when pin.rate ='Negative' then 5 "
                + "when pin.rate ='Very negative' then 4 "
                + "ELSE 0 "
                + "END ) AS Rate "
                + "FROM project pr,project_informal pin,client_information cinfo "
                + "WHERE pin.rate <> 'Neutral' and "
                + "pr.startDate between '" + sDate + "' AND '" + eDate + "' AND "
                + "pin.ID_Project=pr.ID_Project and pr.ID_Client=cinfo.ID_Client "
                + "GROUP BY pr.ID_Client "
                + "order by cinfo.Company_name";

        String queryStringFeedBack = "SELECT cf.clientid as cid,cinfo.Company_name as cName,AVG(CASE "
                + "WHEN cf.rate ='Very positive' THEN 10 "
                + "WHEN cf.rate ='Positive' THEN 9 "
                + "WHEN cf.rate ='Somewhat positive' THEN 8 "
                + "WHEN cf.rate ='Neutral' THEN 0 "
                + "WHEN cf.rate ='Somewhat negative' THEN 6 "
                + "WHEN cf.rate ='Negative' THEN 5 "
                + "WHEN cf.rate ='Very negative' THEN 4 "
                + "ELSE 0 "
                + "END  ) AS Rate "
                + "FROM client_feedback cf ,client_information cinfo "
                + "WHERE cf.rate <>'Neutral' and "
                + "cf.feedbackDate between '" + sDate + "' AND '" + eDate + "' AND "
                + "cf.clientid=cinfo.ID_Client "
                + "GROUP BY cf.clientid "
                + "order by cinfo.Company_name";

        try {
            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement(queryStringQuality);
//            query = session.createQuery(queryString);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {

                JSONObject jo = new JSONObject();
                jo.put("cid", rs.getInt("cid"));
                jo.put("cName", rs.getString("cName"));
                jo.put("QRate", rs.getFloat("Rate"));
                jo.put("FRate", 0.00);
                jo.put("avg", rs.getFloat("Rate"));
                results.add(jo);
            }
            resultString = results.toString();
            st.close();
            tx.commit();
        } catch (JSONException ex) {
            Logger.getLogger(ClientHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClientHelper.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("erooooor" + ex.getMessage());
        } catch (HibernateException e) {
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
        try {
            session = ConnectionFactory.getInstance().getSession();
            tx = session.beginTransaction();
            Double tAvg = 0.00;
            PreparedStatement st = session.connection().prepareStatement(queryStringFeedBack);
//            query = session.createQuery(queryString);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int flag = 0;
                JSONArray cq = new JSONArray(resultString);
                for (int i = 0; i < cq.length(); i++) {
                    JSONObject j = (JSONObject) cq.get(i);
                    if (rs.getInt("cid") == j.getInt("cid")) {
                        Double qRate = j.getDouble("QRate");
                        results.remove(i);
                        JSONObject jo = new JSONObject();
                        tAvg = (qRate + rs.getFloat("Rate")) / 2;
                        jo.put("cid", rs.getInt("cid"));
                        jo.put("cName", rs.getString("cName"));
                        jo.put("QRate", qRate);
                        jo.put("FRate", rs.getFloat("Rate"));
                        jo.put("avg", tAvg);
                        results.add(jo);

                        flag = 1;

                    }
                }
                if (flag == 0) {
                    JSONObject jo = new JSONObject();
                    jo.put("cid", rs.getInt("cid"));
                    jo.put("cName", rs.getString("cName"));
                    jo.put("QRate", 0.00);
                    jo.put("FRate", rs.getFloat("Rate"));
                    jo.put("avg", rs.getFloat("Rate"));
                    results.add(jo);

                }

            }
            st.close();
            tx.commit();
        } catch (JSONException ex) {
            Logger.getLogger(ClientHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClientHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HibernateException e) {
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

        return results.toString();
    }

    public static String getReportOnTimeDelovery(String sDate, String eDate) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */

        Session session = ConnectionFactory.getInstance().getSession();
        List results = new ArrayList();
        Transaction tx = null;
        String resultString = "";


        /*
         * Build HQL (Hibernate Query Language) query to retrieve a list
         * of all the items currently stored by Hibernate.
         */
        String queryString = "SELECT COUNT(ID_Project) as countNo,DATEDIFF(deliveryDate,dueDate) as diffDate FROM project WHERE  startDate between '" + sDate + "' AND '" + eDate + "' AND DATEDIFF(deliveryDate,dueDate)>0 GROUP BY DATEDIFF(deliveryDate,dueDate)";
        // String queryStringPCount="SELECT COUNT(ID_Project) as pCount FROM project WHERE STATUS='complete'";

        try {
            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement(queryString);
            ResultSet rs = st.executeQuery();
            int pcount = 0;
            while (rs.next()) {
                if (rs.getInt("diffDate") < 6) {
                    JSONObject jo = new JSONObject();
                    jo.put("countNo", rs.getInt("countNo"));
                    jo.put("diffDate", rs.getInt("diffDate"));
                    //  jo.put("totalProj", rs1.getInt("pCount"));

                    results.add(jo);
                } else {
                    pcount += rs.getInt("countNo");
                }
            }
            JSONObject jo = new JSONObject();
            jo.put("countNo", pcount);
            jo.put("diffDate", 6);
            results.add(jo);
            st.close();
            tx.commit();
        } catch (JSONException ex) {
            Logger.getLogger(ClientHelper.class.getName()).log(Level.SEVERE, null, ex);
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
        return results.toString();

    }

    public static String getQualityReports(String quality, String sDate, String eDate) {
        Session session = ConnectionFactory.getInstance().getSession();
        String results = "";
        Transaction tx = null;
        String queryString = "";
        if (quality.equalsIgnoreCase("Overall Complaints")) {
            queryString = "SELECT (((SELECT COUNT(ID_Project) FROM quality WHERE dateRaised BETWEEN '" + sDate + "' AND '" + eDate + "')+(SELECT COUNT(ID_Project) FROM project_informal WHERE rate LIKE '%Negative%' AND informalDate BETWEEN '" + sDate + "' AND '" + eDate + "'))*100/(SELECT COUNT(ID_Project) FROM project WHERE STATUS='complete' and startdate BETWEEN '" + sDate + "' AND '" + eDate + "')) AS res";
        } else if (quality.equalsIgnoreCase("Overall Kudos")) {
            queryString = "SELECT (((SELECT COUNT(ID_Project) FROM project_informal WHERE rate LIKE '%Positive%' AND informalDate BETWEEN '" + sDate + "' AND '" + eDate + "'))*100/(SELECT COUNT(ID_Project) FROM project WHERE STATUS='complete' and startdate BETWEEN '" + sDate + "' AND '" + eDate + "')) AS res";

            // String queryStringPCount="SELECT COUNT(ID_Project) as pCount FROM project WHERE STATUS='complete'";
        } else if (quality.equalsIgnoreCase("Overall Client Satisfaction")) {
            queryString = "SELECT AVG(clientSatisfaction) AS res FROM project WHERE STATUS='complete' and startDate BETWEEN '" + sDate + "' AND '" + eDate + "'";
        } else if (quality.equalsIgnoreCase("Overall On-Time Delivery")) {
            queryString = "SELECT ((SELECT COUNT(ID_Project) FROM project WHERE STATUS='complete' and  startDate BETWEEN '" + sDate + "' AND '" + eDate + "' AND DATEDIFF(deliveryDate,dueDate)<=0)*100/(SELECT COUNT(ID_Project) FROM project WHERE  STATUS='complete'  and startdate BETWEEN '" + sDate + "' AND '" + eDate + "')) AS res";
        } else {
            return "";
        }

        try {
            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement(queryString);
            ResultSet rs = st.executeQuery();
            int pcount = 0;
            while (rs.next()) {

                results = "" + StandardCode.getInstance().formatDouble(rs.getDouble("res"));

            }

            st.close();
            tx.commit();
        } catch (Exception ex) {
            Logger.getLogger(ClientHelper.class.getName()).log(Level.SEVERE, null, ex);
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
        return results;

    }

    public static String getQualityReports(String quality, String sDate, String eDate, String clientId) {
        Session session = ConnectionFactory.getInstance().getSession();
        String results = "";
        Transaction tx = null;
        String queryString = "";
        if (quality.equalsIgnoreCase("Overall Complaints")) {
            queryString = "SELECT (((SELECT COUNT(q.ID_Project) FROM quality q, project p WHERE q.ID_Project=p.ID_Project AND p.id_client=" + clientId + " and  dateRaised BETWEEN '" + sDate + "' AND '" + eDate + "')+(SELECT COUNT(i.ID_Project) FROM project_informal i,project p  WHERE i.ID_Project=p.ID_Project AND p.id_client=" + clientId + " and rate LIKE '%Negative%' AND informalDate BETWEEN '" + sDate + "' AND '" + eDate + "'))*100/(SELECT COUNT(ID_Project) FROM project WHERE STATUS='complete' and startdate BETWEEN '" + sDate + "' AND '" + eDate + "' and id_client=" + clientId + ")) AS res";
        } else if (quality.equalsIgnoreCase("Overall Kudos")) {
            queryString = "SELECT (((SELECT COUNT(i.ID_Project) FROM project_informal i,project p  WHERE i.ID_Project=p.ID_Project AND p.id_client=" + clientId + " and rate LIKE '%Positive%' AND informalDate BETWEEN '" + sDate + "' AND '" + eDate + "'))*100/(SELECT COUNT(ID_Project) FROM project WHERE STATUS='complete' and startdate BETWEEN '" + sDate + "' AND '" + eDate + "' and id_client=" + clientId + ")) AS res";

            // String queryStringPCount="SELECT COUNT(ID_Project) as pCount FROM project WHERE STATUS='complete'";
        } else if (quality.equalsIgnoreCase("Overall Client Satisfaction")) {
            queryString = "SELECT AVG(clientSatisfaction) AS res FROM project WHERE STATUS='complete' and startDate BETWEEN '" + sDate + "' AND '" + eDate + "' and id_client=" + clientId;
        } else if (quality.equalsIgnoreCase("Overall On-Time Delivery")) {
            queryString = "SELECT ((SELECT COUNT(ID_Project) FROM project WHERE  id_client=" + clientId + " and startDate BETWEEN '" + sDate + "' AND '" + eDate + "' AND DATEDIFF(deliveryDate,dueDate)<=0)*100/(SELECT COUNT(ID_Project) FROM project WHERE STATUS='complete' and id_client=" + clientId + " and startdate BETWEEN '" + sDate + "' AND '" + eDate + "')) AS res";
        } else {
            return "";
        }

        try {
            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement(queryString);
            ResultSet rs = st.executeQuery();
            int pcount = 0;
            while (rs.next()) {

                results = "" + StandardCode.getInstance().formatDouble(rs.getDouble("res"));

            }

            st.close();
            tx.commit();
        } catch (Exception ex) {
            Logger.getLogger(ClientHelper.class.getName()).log(Level.SEVERE, null, ex);
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
        return results;

    }

    public static String getQualityReportsScore(String quality, String sDate, String eDate) {

        Session session = ConnectionFactory.getInstance().getSession();
        String results = "";
        Transaction tx = null;
        String queryString = "";
        if (quality.equalsIgnoreCase("Overall Complaints")) {
            queryString = "SELECT (((SELECT COUNT(ID_Project) FROM quality WHERE dateRaised BETWEEN '" + sDate + "' AND '" + eDate + "')+(SELECT COUNT(ID_Project) FROM project_informal WHERE rate LIKE '%Negative%' AND informalDate BETWEEN '" + sDate + "' AND '" + eDate + "'))) AS res";
        } else if (quality.equalsIgnoreCase("Overall Kudos")) {
            queryString = "SELECT (((SELECT COUNT(ID_Project) FROM project_informal WHERE rate LIKE '%Positive%' AND informalDate BETWEEN '" + sDate + "' AND '" + eDate + "'))) AS res";

            // String queryStringPCount="SELECT COUNT(ID_Project) as pCount FROM project WHERE STATUS='complete'";
        } else if (quality.equalsIgnoreCase("Overall Client Satisfaction")) {
            queryString = "SELECT SUM(clientSatisfaction) AS res FROM project WHERE STATUS='complete' and startDate BETWEEN '" + sDate + "' AND '" + eDate + "'";
        } else if (quality.equalsIgnoreCase("Overall On-Time Delivery")) {
            queryString = "SELECT ((SELECT COUNT(ID_Project) FROM project "
                    + "WHERE STATUS='complete' and  startDate BETWEEN '" + sDate + "' AND '" + eDate + "' AND "
                    + "DATEDIFF(deliveryDate,dueDate)<=0)) AS res";
        } else {
            return "";
        }

        try {
            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement(queryString);
            ResultSet rs = st.executeQuery();
            int pcount = 0;
            while (rs.next()) {

                results = "" + StandardCode.getInstance().formatDouble(rs.getDouble("res"));

            }

            st.close();
            tx.commit();
        } catch (Exception ex) {
            Logger.getLogger(ClientHelper.class.getName()).log(Level.SEVERE, null, ex);
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
        return results;

    }
    
    public static HashMap<String, Integer> getProjectFeedbackCount() {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */

        HashMap<String, Integer> pcount = new HashMap<>();
        Session session = ConnectionFactory.getInstance().getSession();
        Transaction tx = null;
        /*
         * Build HQL (Hibernate Query Language) query to retrieve a list
         * of all the items currently stored by Hibernate.
         */

        String queryString = "SELECT COUNT(pr.ID_Project) AS res,EXTRACT(YEAR FROM pr.startDate) as yr,EXTRACT(quarter FROM pr.startDate) as quater "
                    + "FROM project_informal pi left join project pr on pi.ID_Project = pr.ID_Project "
                    + " "
                    + "Group by EXTRACT(YEAR FROM pr.startDate),EXTRACT(quarter FROM pr.startDate)";
        try {
            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement(queryString);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                pcount.put(rs.getInt("yr") + "#" + rs.getInt("quater"), rs.getInt("res"));
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
        return pcount;

    }

    public static JSONArray getQualityReportsScoreChart(String quality, String sDate, String eDate) {
        JSONArray reportData = new JSONArray();
        Session session = ConnectionFactory.getInstance().getSession();
        String results = "";
        Transaction tx = null;
        String queryString = "";
        List qualityScorelist = new ArrayList();
        if (quality.equalsIgnoreCase("QOComplaints")) {
            queryString = "SELECT COUNT(q.ID_Project)  AS res,EXTRACT(YEAR FROM pr.startDate) as yr,EXTRACT(quarter FROM pr.startDate) as quater "
                    + "FROM quality q left join project pr on q.ID_Project = pr.ID_Project group by EXTRACT(YEAR FROM pr.startDate),EXTRACT(quarter FROM pr.startDate)"
                    + " UNION "
                    + "SELECT COUNT(pi.ID_Project)  AS res,EXTRACT(YEAR FROM informalDate) as yr,EXTRACT(quarter FROM informalDate) as quater "
                    + "FROM project_informal pi left join project pr on pi.ID_Project = pr.ID_Project  WHERE rate LIKE '%Negative%'  group by EXTRACT(YEAR FROM pr.startDate),EXTRACT(quarter FROM pr.startDate)";
            qualityScorelist = ProjectService.getInstance().getQualityReportPerQuality("Overall Complaints");
        } else if (quality.equalsIgnoreCase("QOKudos")) {
            queryString = "SELECT COUNT(pi.ID_Project) AS res,EXTRACT(YEAR FROM pr.startDate) as yr,EXTRACT(quarter FROM pr.startDate) as quater "
                    + "FROM project_informal pi left join project pr on pi.ID_Project = pr.ID_Project "
                    + "WHERE rate LIKE '%Positive%' "
                    + "Group by EXTRACT(YEAR FROM pr.startDate),EXTRACT(quarter FROM pr.startDate)";
            qualityScorelist = ProjectService.getInstance().getQualityReportPerQuality("Overall Kudos");

            // String queryStringPCount="SELECT COUNT(ID_Project) as pCount FROM project WHERE STATUS='complete'";
        } else if (quality.equalsIgnoreCase("QOClientSatisfaction")) {
            queryString = "SELECT EXTRACT(YEAR FROM startdate) as yr,EXTRACT(quarter FROM startdate) as quater,SUM(clientSatisfaction) AS res, "
                    + "Count(0) as cnt FROM project where STATUS='complete' and clientSatisfaction > 0 Group by EXTRACT(YEAR FROM startdate),EXTRACT(quarter FROM startdate)";
            qualityScorelist = ProjectService.getInstance().getQualityReportPerQuality("Overall Client Satisfaction");
        } else if (quality.equalsIgnoreCase("QOOnTime")) {
            queryString = "SELECT COUNT(ID_Project) AS res,EXTRACT(YEAR FROM startdate) as yr,EXTRACT(quarter FROM startdate) as quater "
                    + "FROM project WHERE STATUS='complete' and DATEDIFF(deliveryDate,dueDate)<=0 Group by EXTRACT(YEAR FROM startdate),EXTRACT(quarter FROM startdate)";
            qualityScorelist = ProjectService.getInstance().getQualityReportPerQuality("Overall On-Time Delivery");
        } else {
            return null;
        }
        JSONObject qualityScore = new JSONObject();
        for (int i = 0; i < qualityScorelist.size(); i++) {
            try {
                QualityReport q = (QualityReport) qualityScorelist.get(i);
                qualityScore.put(q.getQualityyear(), q.getQualityscore());
            } catch (JSONException ex) {
                Logger.getLogger(ClientHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement(queryString);
            ResultSet rs = st.executeQuery();
            int pcount = 0;

            Calendar cal = new GregorianCalendar();
            JSONArray q1 = new JSONArray();
            JSONArray q2 = new JSONArray();
            JSONArray q3 = new JSONArray();
            JSONArray q4 = new JSONArray();
            int cnt = 0;
            HashMap<String, Integer> feedbackCount = getProjectFeedbackCount();
            for (int ii = 2005; ii <= cal.get(Calendar.YEAR); ii++) {
                for (int iq = 1; iq <= 4; iq++) {
                    int fCount = 0;
                if (feedbackCount.containsKey(ii + "#" + iq)) {
                            fCount =  feedbackCount.get(ii + "#" + iq);
                       }
                    if (iq == 1) {
                        JSONObject data = new JSONObject();
                        data.put("x", "" + ii);
                        data.put("y", 0);
                        data.put("projectCount", 0);
                        data.put("Score", 0);
                        data.put("feedback", fCount );
                        data.put("goal", qualityScore.get("" + ii));
                        
                        q1.put(data);
                    }
                    if (iq == 2) {
                        JSONObject data = new JSONObject();
                        data.put("x", "" + ii);
                        data.put("projectCount", 0);
                        data.put("Score", 0);
                        data.put("feedback", fCount);
                        data.put("goal", qualityScore.get("" + ii));
                        data.put("y", 0);
                        q2.put(data);
                    }
                    if (iq == 3) {
                        JSONObject data = new JSONObject();
                        data.put("x", "" + ii);
                        data.put("projectCount", 0);
                        data.put("Score", 0);
                        data.put("feedback", fCount);
                        data.put("goal", qualityScore.get("" + ii));
                        data.put("y", 0);
                        q3.put(data);
                    }
                    if (iq == 4) {
                        JSONObject data = new JSONObject();
                        data.put("x", "" + ii);
                        data.put("projectCount", 0);
                        data.put("Score", 0);
                        data.put("feedback", fCount);
                        data.put("goal", qualityScore.get("" + ii));
                        data.put("y", 0);
                        q4.put(data);
                    }
                }
                cnt++;
            }

            HashMap<String, Integer> projectCount = getProjectCountPerQuater();

            while (rs.next()) {
                int qtr = rs.getInt("quater");
                int yr = rs.getInt("yr");

                double pcnt = 0,fCnt = 0;
                if (quality.equalsIgnoreCase("QOClientSatisfaction")) {
                    pcnt = rs.getInt("cnt");
                } else {
                    if (projectCount.containsKey(yr + "#" + qtr)) {
                        pcnt = projectCount.get(yr + "#" + qtr).doubleValue() / 100;
                    }
                }
                if (feedbackCount.containsKey(yr + "#" + qtr)) {
                        fCnt = feedbackCount.get(yr + "#" + qtr).doubleValue() ;
                    }
                if (pcnt > 0) {
                    String year = "" + yr;
                    if (qtr == 1) {
                        for (int i = 0; i < cnt; i++) {
                            JSONObject valueObj = q1.getJSONObject(i);
                            if (year.equalsIgnoreCase(valueObj.getString("x"))) {
                                double p = valueObj.getDouble("projectCount") + pcnt;
                                double s = valueObj.getDouble("Score") + rs.getDouble("res");
                                valueObj.remove("y");
                                valueObj.remove("projectCount");
                                valueObj.remove("Score");
                                valueObj.remove("feedback");
                                valueObj.put("feedback", fCnt);
                                valueObj.put("projectCount", p);
                                valueObj.put("Score", s);
                                valueObj.put("y", rs.getDouble("res") / pcnt);
                            }
                        }

                    }
                    if (qtr == 2) {
                        for (int i = 0; i < cnt; i++) {
                            JSONObject valueObj = q2.getJSONObject(i);
                            if (year.equalsIgnoreCase(valueObj.getString("x"))) {
                                double p = valueObj.getDouble("projectCount") + pcnt;
                                double s = valueObj.getDouble("Score") + rs.getDouble("res");
                                valueObj.remove("y");
                                valueObj.remove("projectCount");
                                valueObj.remove("Score");
                                valueObj.remove("feedback");
                                valueObj.put("feedback", fCnt);
                                valueObj.put("projectCount", p);
                                valueObj.put("Score", s);
                                valueObj.put("y", rs.getDouble("res") / pcnt);
                            }
                        }

                    }
                    if (qtr == 3) {
                        for (int i = 0; i < cnt; i++) {
                            JSONObject valueObj = q3.getJSONObject(i);
                            if (year.equalsIgnoreCase(valueObj.getString("x"))) {
                                double p = valueObj.getDouble("projectCount") + pcnt;
                                double s = valueObj.getDouble("Score") + rs.getDouble("res");
                                valueObj.remove("y");
                                valueObj.remove("projectCount");
                                valueObj.remove("Score");
                                valueObj.remove("feedback");
                                valueObj.put("feedback", fCnt);
                                valueObj.put("projectCount", p);
                                valueObj.put("Score", s);
                                valueObj.put("y", rs.getDouble("res") / pcnt);
                            }
                        }

                    }
                    if (qtr == 4) {
                        for (int i = 0; i < cnt; i++) {
                            JSONObject valueObj = q4.getJSONObject(i);
                            if (year.equalsIgnoreCase(valueObj.getString("x"))) {
                                double p = valueObj.getDouble("projectCount") + pcnt;
                                double s = valueObj.getDouble("Score") + rs.getDouble("res");
                                valueObj.remove("y");
                                valueObj.remove("projectCount");
                                valueObj.remove("Score");
                                valueObj.remove("feedback");
                                valueObj.put("feedback", fCnt);
                                valueObj.put("projectCount", p);
                                valueObj.put("Score", s);
                                valueObj.put("y", rs.getDouble("res") / pcnt);
                            }
                        }
                    }
                }
            }

            JSONObject maindata = new JSONObject();

            maindata.put("key", "Quarter 1");
            maindata.put("values", q1);
            maindata.put("color", "#00CC00");
            reportData.put(maindata);
            maindata = new JSONObject();
            maindata.put("key", "Quarter 2");
            maindata.put("values", q2);
            maindata.put("color", "#996600");
            reportData.put(maindata);
            maindata = new JSONObject();
            maindata.put("key", "Quarter 3");
            maindata.put("values", q3);
            maindata.put("color", "#0066FF");
            reportData.put(maindata);
            maindata = new JSONObject();
            maindata.put("key", "Quarter 4");
            maindata.put("values", q4);
            maindata.put("color", "#CC33FF");
            reportData.put(maindata);

            st.close();
            tx.commit();
        } catch (Exception ex) {
            Logger.getLogger(ClientHelper.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Hibernate Exception" + ex.getMessage());
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

        return reportData;

    }

    public static HashMap<String, Integer> getProjectCountPerQuater() {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */

        HashMap<String, Integer> pcount = new HashMap<>();
        Session session = ConnectionFactory.getInstance().getSession();
        Transaction tx = null;
        /*
         * Build HQL (Hibernate Query Language) query to retrieve a list
         * of all the items currently stored by Hibernate.
         */

        String queryString = "SELECT COUNT(ID_Project) as pCount , EXTRACT(YEAR FROM startdate) as yr,EXTRACT(quarter FROM startdate) as quater "
                + "FROM project WHERE STATUS='complete' Group by EXTRACT(YEAR FROM startdate),EXTRACT(quarter FROM startdate)";
        try {
            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement(queryString);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                pcount.put(rs.getInt("yr") + "#" + rs.getInt("quater"), rs.getInt("pCount"));
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
        return pcount;

    }

    public static int getProjectCount(String sDate, String eDate) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        int pcount = 0;
        Session session = ConnectionFactory.getInstance().getSession();
        Transaction tx = null;
        /*
         * Build HQL (Hibernate Query Language) query to retrieve a list
         * of all the items currently stored by Hibernate.
         */

        String queryString = "SELECT COUNT(ID_Project) as pCount FROM project WHERE STATUS='complete' and startDate between '" + sDate + "' AND '" + eDate + "'";
        try {
            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement(queryString);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                pcount = rs.getInt("pCount");
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
        return pcount;

    }

    public static int getCompleteProjectCount() {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        int pcount = 0;
        Session session = ConnectionFactory.getInstance().getSession();
        Transaction tx = null;
        /*
         * Build HQL (Hibernate Query Language) query to retrieve a list
         * of all the items currently stored by Hibernate.
         */

        String queryString = "SELECT COUNT(ID_Project) as pCount FROM project WHERE STATUS='complete'";
        try {
            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement(queryString);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                pcount = rs.getInt("pCount");
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
        return pcount;

    }

    public static int getCompleteProjectCount(String sDate, String eDate) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        int pcount = 0;
        Session session = ConnectionFactory.getInstance().getSession();
        Transaction tx = null;
        /*
         * Build HQL (Hibernate Query Language) query to retrieve a list
         * of all the items currently stored by Hibernate.
         */

        String queryString = "SELECT COUNT(ID_Project) as pCount FROM project WHERE STATUS='complete' and startDate between '" + sDate + "' AND '" + eDate + "'";
        try {
            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement(queryString);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                pcount = rs.getInt("pCount");
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
        return pcount;

    }

    public static String getClientIndidualSatisfaction(String clientId, String sDate, String eDate) {
        String resultString = "";
        List results = new ArrayList();
        Session session = ConnectionFactory.getInstance().getSession();
        Integer client = Integer.parseInt(clientId);

        Transaction tx = null;

        String queryStringQuality = " SELECT YEAR(pr.startDate) AS yyyy, AVG(CASE "
                + "WHEN pin.rate ='Very positive' THEN 10 "
                + "WHEN pin.rate ='Positive' THEN 9 "
                + "WHEN pin.rate ='Somewhat positive' THEN 8 "
                + "WHEN pin.rate ='Neutral' THEN 7 "
                + "WHEN pin.rate ='Somewhat negative' THEN 6 "
                + "WHEN pin.rate ='Negative' THEN 5 "
                + "WHEN pin.rate ='Very negative' THEN 4 "
                + "ELSE 0 "
                + "END ) AS Rate "
                + "FROM project pr,project_informal pin "
                + "WHERE "
                + "pr.ID_Client=" + client + " and "
                + "pin.informalDate between '" + sDate + "' AND '" + eDate + "' AND "
                + "pin.ID_Project=pr.ID_Project "
                + "GROUP BY YEAR(pr.startDate) "
                + "ORDER BY YEAR(pr.startDate) desc";

        String queryStringFeedBack = "SELECT YEAR(cf.feedbackDate) AS yyyy,AVG(CASE  "
                + "WHEN cf.rate ='Very positive' THEN 10  "
                + "WHEN cf.rate ='Positive' THEN 9  "
                + "WHEN cf.rate ='Somewhat positive' THEN 8  "
                + "WHEN cf.rate ='Neutral' THEN 7  "
                + "WHEN cf.rate ='Somewhat negative' THEN 6  "
                + "WHEN cf.rate ='Negative' THEN 5  "
                + "WHEN cf.rate ='Very negative' THEN 4  "
                + "ELSE 0 END  ) AS Rate  "
                + "FROM client_feedback cf  "
                + "WHERE cf.clientid=" + client + "  "
                + "and cf.feedbackDate between '" + sDate + "' AND '" + eDate + "' "
                + "GROUP BY YEAR(cf.feedbackDate) "
                + "ORDER BY YEAR(cf.feedbackDate) DESC ";

        try {
            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement(queryStringQuality);
//            query = session.createQuery(queryString);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {

                JSONObject jo = new JSONObject();
                jo.put("year", rs.getString("yyyy"));
                jo.put("QRate", rs.getFloat("Rate"));
                jo.put("FRate", 0.00);
                jo.put("avg", rs.getFloat("Rate"));
                results.add(jo);
            }
            resultString = results.toString();
            st.close();
            tx.commit();
        } catch (JSONException ex) {
            Logger.getLogger(ClientHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClientHelper.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("erooooor" + ex.getMessage());
        } catch (HibernateException e) {
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
        try {
            session = ConnectionFactory.getInstance().getSession();
            tx = session.beginTransaction();
            Double tAvg = 0.00;
            PreparedStatement st = session.connection().prepareStatement(queryStringFeedBack);
//            query = session.createQuery(queryString);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int flag = 0;
                JSONArray cq = new JSONArray(resultString);
                for (int i = 0; i < cq.length(); i++) {
                    JSONObject j = (JSONObject) cq.get(i);
                    if (rs.getInt("yyyy") == j.getInt("year")) {
                        Double qRate = j.getDouble("QRate");
                        results.remove(i);
                        JSONObject jo = new JSONObject();
                        tAvg = (qRate + rs.getFloat("Rate")) / 2;
                        jo.put("year", rs.getString("yyyy"));
                        jo.put("QRate", qRate);
                        jo.put("FRate", rs.getFloat("Rate"));
                        jo.put("avg", tAvg);
                        results.add(jo);

//                    Double qRate=j.getDouble("QRate");
//                    //results.remove(j);
//                    //delete j.;
//                    //JSONObject jo = new JSONObject();
//                    tAvg = (qRate + rs.getFloat("Rate")) / 2;
//                    //j.put("year", rs.getString("yyyy"));
//                    //j.append("QRate", qRate);
//                    j.put("FRate", rs.getFloat("Rate"));
//                    j.put("avg", tAvg);
//                    j.putAll();
//                    results.add(j);
                        flag = 1;

                    }
                }
                if (flag == 0) {
                    JSONObject jo = new JSONObject();
                    jo.put("year", rs.getString("yyyy"));
                    jo.put("QRate", 0.00);
                    jo.put("FRate", rs.getFloat("Rate"));
                    jo.put("avg", rs.getFloat("Rate"));
                    results.add(jo);

                }

            }
            st.close();
            tx.commit();
        } catch (JSONException ex) {
            Logger.getLogger(ClientHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClientHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HibernateException e) {
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

        return results.toString();
    }

    public static String getReportOnTimeDeliveryByClient(String clientId, String sDate, String eDate) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */

        Session session = ConnectionFactory.getInstance().getSession();
        List results = new ArrayList();
        Transaction tx = null;
        String resultString = "";


        /*
         * Build HQL (Hibernate Query Language) query to retrieve a list
         * of all the items currently stored by Hibernate.
         */
//"startDate between "+sDate+" AND '"+eDate+"' AND "+
        String queryString = "SELECT COUNT(ID_Project) as countNo,DATEDIFF(deliveryDate,dueDate) as diffDate FROM project WHERE startDate between '" + sDate + "' AND '" + eDate + "' AND  ID_Client=" + clientId + " and   DATEDIFF(deliveryDate,dueDate)>0 GROUP BY DATEDIFF(deliveryDate,dueDate)";
        // String queryStringPCount="SELECT COUNT(ID_Project) as pCount FROM project WHERE STATUS='complete'";

        try {
            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement(queryString);
            ResultSet rs = st.executeQuery();
            int pcount = 0;
            while (rs.next()) {
                if (rs.getInt("diffDate") < 6) {
                    JSONObject jo = new JSONObject();
                    jo.put("countNo", rs.getInt("countNo"));
                    jo.put("diffDate", rs.getInt("diffDate"));
                    //  jo.put("totalProj", rs1.getInt("pCount"));

                    results.add(jo);
                } else {
                    pcount += rs.getInt("countNo");
                }
            }
            if (pcount > 0) {
                JSONObject jo = new JSONObject();
                jo.put("countNo", pcount);
                jo.put("diffDate", 6);
                results.add(jo);
            }
            st.close();
            tx.commit();
        } catch (JSONException ex) {
            Logger.getLogger(ClientHelper.class.getName()).log(Level.SEVERE, null, ex);
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
        return results.toString();

    }

    public static int getProjectCountByClient(String clientId, String sDate, String eDate) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        int pcount = 0;
        Session session = ConnectionFactory.getInstance().getSession();
        Transaction tx = null;
        /*
         * Build HQL (Hibernate Query Language) query to retrieve a list
         * of all the items currently stored by Hibernate.
         */

        String queryString = "SELECT COUNT(ID_Project) as pCount FROM project WHERE startDate between '" + sDate + "' AND '" + eDate + "' AND   ID_Client=" + clientId + " and STATUS='complete'";
        try {
            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement(queryString);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                pcount = rs.getInt("pCount");
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
        return pcount;

    }

    //get all active projects
    public static int getClientProjectCount(Integer cId) {
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

            //Get Projects
            String totalProjectsHql
                    = "select count(project) from app.project.Project project where project.number>0 and project.status !='notApproved' and project.status !='onhold' and project.Company.clientId=" + cId;
            Query myQuery = session.createQuery(totalProjectsHql);

            return (Integer) myQuery.uniqueResult();
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

    //get all active projects

    public static double getClientProjectAmount(Integer cId) {
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

            //Get Projects
            String totalProjectsHql
                    = "select sum(project.projectAmount) from app.project.Project project where project.number>0 and project.status !='notApproved' and project.status !='onhold' and project.Company.clientId=" + cId;
            Query myQuery = session.createQuery(totalProjectsHql);
            if (myQuery.uniqueResult() == null) {
                return 0.00;
            } else {
                return (Double) myQuery.uniqueResult();
            }
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

    public JSONArray getClientCountPerYear(int pCount, int year) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.*/

            String query = "SELECT Extract(YEAR FROM c.createdByDate) myYear,c.ID_Client,sum(projectAmount) as projectAmount,c.Company_name,Count(c.ID_Client) as cnt ,Count(p.ID_Project) as pcnt  \n"
                    + "FROM excel.client_information c inner join project p on p.ID_Client = c.ID_Client\n"
                    + "where (Extract(YEAR FROM c.createdByDate) = "+year++ +" OR Extract(YEAR FROM c.createdByDate)="+year +") "
                    + "and p.status !='notApproved' and p.status !='onhold'  and p.number !=0 "
                    + "group by c.ID_Client,Extract(YEAR FROM c.createdByDate)\n"
                    + "Having Count(p.ID_Project)>=" + pCount
                    + " order by c.createdByDate asc;";

            JSONArray clientCount = new JSONArray();

            try (PreparedStatement st = session.connection().prepareStatement(query);
                    ResultSet rs = st.executeQuery()) {
//                clientCount = new HashMap<>();
                while (rs.next()) {
                    JSONObject newClient = new JSONObject();
                    newClient.put("year", rs.getString("myYear"));
                    newClient.put("cnt", rs.getInt("cnt"));
                    newClient.put("company", rs.getString("Company_name"));
                    newClient.put("id", rs.getString("ID_Client"));
                    newClient.put("pAmount", StandardCode.getInstance().formatDouble(rs.getDouble("projectAmount")));
                    clientCount.put(newClient);

                }
                 return clientCount;
            } catch (JSONException ex) {
                Logger.getLogger(ClientHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        } catch (HibernateException e) {
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public static Map<String, Double> getClientQuality() {
       
        Session session = ConnectionFactory.getInstance().getSession();
        
        Transaction tx = null;

        String queryStringFeedBack = "SELECT AVG(CASE "
                + "WHEN cf.rate ='Very positive' THEN 10 "
                + "WHEN cf.rate ='Positive' THEN 9 "
                + "WHEN cf.rate ='Somewhat positive' THEN 8 "
                + "WHEN cf.rate ='Neutral' THEN 0 "
                + "WHEN cf.rate ='Somewhat negative' THEN 6 "
                + "WHEN cf.rate ='Negative' THEN 5 "
                + "WHEN cf.rate ='Very negative' THEN 4 "
                + "ELSE 0 "
                + "END  ) AS rate, EXTRACT(YEAR FROM cf.feedbackDate) as myYear "
                + "FROM client_feedback cf  "
                + "WHERE cf.rate <>'Neutral' "
                + "GROUP BY myYear "
                + "order by myYear";


        try {
            session = ConnectionFactory.getInstance().getSession();
            tx = session.beginTransaction();
            Double tAvg = 0.00;
            PreparedStatement st = session.connection().prepareStatement(queryStringFeedBack);
            ResultSet rs = st.executeQuery();
            
             Map<String, Double> revenue = new HashMap();
            while(rs.next()) {
                String year = rs.getString("myYear");
                revenue.put(year, rs.getDouble("rate"));
                
                
            }
            
            st.close();
            tx.commit();
             return revenue;
        } catch (SQLException ex) {
            Logger.getLogger(ClientHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HibernateException e) {
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
        return null;
    }


}
