/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import app.admin.AdminConstants;
import app.admin.AdminMisc;
import app.admin.AdminService;
import net.sf.hibernate.*;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.*;
import net.sf.hibernate.Hibernate;
import java.util.*;
import app.db.*;
import app.extjs.helpers.HrHelper;
import app.extjs.vo.Dropdown;
import app.hr.HrService;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.hibernate.expression.Expression;
import net.sf.hibernate.expression.Order;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Niteshwar
 */
public class QMSService {

    private static QMSService instance = null;

    public QMSService() {
    }

    //return the instance of QMSService
    public static synchronized QMSService getInstance() {
        /*
         * Creates the Singleton instance, if needed.
         *
         */
        if (instance == null) {
            instance = new QMSService();
        }
        return instance;
    }

    public List getAuditSchedule() {

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
            query = session.createQuery("select qmsaudit from app.qms.QMSAudit qmsaudit");
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

    public JSONArray getAuditInternalNonConformity() {
        JSONArray auditdata = new JSONArray();
        Session session = ConnectionFactory.getInstance().getSession();
        String q = "  SELECT sum(nonconformities) as cnt,EXTRACT(YEAR FROM actual) as yr "
                + "FROM qmsaudit where type = 'internal' "
                + "group by EXTRACT(YEAR FROM actual) order by EXTRACT(YEAR FROM actual)";

        try {

            PreparedStatement pstmt = session.connection().prepareStatement(q);

            JSONObject maindata = new JSONObject();
            maindata.put("key", "Internal Audit");
            maindata.put("color", "red");
            JSONArray values = new JSONArray();

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rs.getString("yr");
                JSONObject data = new JSONObject();
                data.put("x", "Year " + rs.getString("yr"));
                data.put("y", rs.getInt("cnt"));
                values.put(data);
            }

            maindata.put("values", values);
            auditdata.put(maindata);
            pstmt.close();

        } catch (HibernateException ex) {
            Logger.getLogger(QMSService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            // Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(QMSService.class.getName()).log(Level.SEVERE, null, ex);
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

        return auditdata;

    }

    public JSONArray getAuditExternalNonConformity() {
        JSONArray auditdata = new JSONArray();
        Session session = ConnectionFactory.getInstance().getSession();
        String q = "  SELECT auditby, sum(nonconformities) as cnt,EXTRACT(YEAR FROM actual) as yr FROM excel.qmsaudit \n"
                + " where EXTRACT(YEAR FROM actual) is not null and type = 'external' \n"
                + " group by auditby,EXTRACT(YEAR FROM actual) order by auditby,EXTRACT(YEAR FROM actual);";

        try {

            PreparedStatement pstmt = session.connection().prepareStatement(q);

            Calendar cal = new GregorianCalendar();
            JSONArray valuesClient = new JSONArray();
            JSONArray valuesRegistrar = new JSONArray();
            int cnt = 0;
            for (int ii = 2000; ii <= cal.get(Calendar.YEAR); ii++) {
                JSONObject data = new JSONObject();
                data.put("x", ii);
                data.put("y", 0);
                valuesClient.put(data);
                cnt++;
                data = new JSONObject();
                data.put("x", ii);
                data.put("y", 0);
                valuesRegistrar.put(data);
            }
//            for (int ii = 2000; ii <= cal.get(Calendar.YEAR); ii++){
//                JSONObject data = new JSONObject();
//                data.put("x",  ii);
//                data.put("y", 0);
//                valuesRegistrar.put(data);
//            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                JSONObject data = new JSONObject();
                data.put("x", rs.getInt("yr"));
                data.put("y", rs.getInt("cnt"));
                int year = rs.getInt("yr");
                if ("client".equalsIgnoreCase(rs.getString("auditby"))) {
                    for (int i = 0; i < cnt; i++) {
                        JSONObject valueClientObj = valuesClient.getJSONObject(i);
                        if (year == valueClientObj.getInt("x")) {
                            valueClientObj.remove("y");
                            valueClientObj.put("y", rs.getInt("cnt"));
                        }
                    }

                } else if ("registrar".equalsIgnoreCase(rs.getString("auditby"))) {
                    for (int i = 0; i < cnt; i++) {
                        JSONObject valuesRegistrarObj = valuesRegistrar.getJSONObject(i);
                        if (year == valuesRegistrarObj.getInt("x")) {
                            valuesRegistrarObj.remove("y");
                            valuesRegistrarObj.put("y", rs.getInt("cnt"));
                        }
                    }
                }
            }
            for (int i = 0; i < valuesRegistrar.length(); i++) {
                boolean isPresent = false;
                for (int j = 0; j < valuesClient.length(); j++) {
                    if (valuesClient.getJSONObject(j).get("x").equals(valuesRegistrar.getJSONObject(i).get("x"))) {
                        isPresent = true;
                    }

                }
                if (isPresent == false) {
                    JSONObject data = new JSONObject();
                    data.put("x", valuesRegistrar.getJSONObject(i).get("x"));
                    data.put("y", 0);
                    valuesClient.put(data);
                }

            }

            JSONObject maindata = new JSONObject();

            maindata.put("key", "External Audit - Client");
            maindata.put("values", valuesClient);
            maindata.put("color", "green");
            auditdata.put(maindata);
            maindata = new JSONObject();
            maindata.put("key", "External Audit - Registrar");
            maindata.put("values", valuesRegistrar);
            maindata.put("color", "red");
            auditdata.put(maindata);

            pstmt.close();

        } catch (HibernateException ex) {
            Logger.getLogger(QMSService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            // Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(QMSService.class.getName()).log(Level.SEVERE, null, ex);
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

        return auditdata;

    }

    public List getAuditScheduleByType(String type, String by) {

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
            query = session.createQuery("select qmsaudit from app.qms.QMSAudit qmsaudit where type='" + type + "'" + by);
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

    public QMSAudit getSingleQmsAudit(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from QMSAudit as vc where vc.id = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (QMSAudit) results.get(0);
        }

    }

    public List getAuditFileReport(Integer id) {

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
            query = session.createQuery("select qmsauditRep from app.qms.QMSReportUpload qmsauditRep where qmsauditRep.auditno=" + id);
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

    public QMSReportUpload getSingleQMSReport(Integer number) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from QMSReportUpload as ci where ci.id = ?",
                    new Object[]{number},
                    new Type[]{Hibernate.INTEGER});

        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (QMSReportUpload) results.get(0);
        }

    }

    public Capa getSingleCapa(String number) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Capa as ci where ci.number = ?",
                    new Object[]{number},
                    new Type[]{Hibernate.STRING});

        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (Capa) results.get(0);
        }

    }

    public String getNewCapaNumber(String yy) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {
            String newNumber = "";
//            Date d = new Date();
//            String year = "" + (d.getYear() + 1900);
            String year = yy;
            results = session.find("select ci.number from Capa as ci where ci.number like '" + yy + "-%' order by capa_id desc limit 1");
            try {
                if (results != null) {
                    String lNumber = (String) results.get(0);

                    String[] lNumberArray = lNumber.split("-");
                    if (year.equalsIgnoreCase(lNumberArray[0])) {
                        newNumber = "" + (Integer.parseInt(lNumberArray[1]) + 1);
                    } else {
                        newNumber = "" + 001;
                    }

                    if (newNumber.length() == 2) {
                        newNumber = "0" + newNumber;
                    } else if (newNumber.length() == 1) {
                        newNumber = "00" + newNumber;
                    }
                } else {
                    newNumber = "001";
                }

            } catch (Exception e) {
                newNumber = "001";
            }
            //String yy=(""+d2).substring(2, 4);
            newNumber = yy + "-" + newNumber;
            return newNumber;

        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
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

    public List getCapa() {

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
            query = session.createQuery("from app.qms.Capa capa order by number asc");
            return query.list();
        } catch (HibernateException e) {
            //System.err.println("Hibernate Exception" + e.getMessage());
            //System.out.println("ffffffffffffffffffffffffffff" + e.getMessage());
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

    public StrategicChange getSingleStrategicChange(String number) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from StrategicChange as ci where ci.number = ?",
                    new Object[]{number},
                    new Type[]{Hibernate.STRING});

        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (StrategicChange) results.get(0);
        }

    }

    public String getNewStrategicChangeNumber(String yy) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {
            String newNumber = "";
//            Date d = new Date();
//            String year = "" + (d.getYear() + 1900);
            String year = yy;
            results = session.find("select ci.number from StrategicChange as ci where ci.number like '" + yy + "-%' order by id desc limit 1");
            try {
                if (results != null) {
                    String lNumber = (String) results.get(0);

                    String[] lNumberArray = lNumber.split("-");
                    if (year.equalsIgnoreCase(lNumberArray[0])) {
                        newNumber = "" + (Integer.parseInt(lNumberArray[1]) + 1);
                    } else {
                        newNumber = "" + 001;
                    }

                    if (newNumber.length() == 2) {
                        newNumber = "0" + newNumber;
                    } else if (newNumber.length() == 1) {
                        newNumber = "00" + newNumber;
                    }
                } else {
                    newNumber = "001";
                }

            } catch (Exception e) {
                newNumber = "001";
            }
            //String yy=(""+d2).substring(2, 4);
            newNumber = yy + "-" + newNumber;
            return newNumber;

        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
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

    public List<StrategicChange> getStrategicChanges() {

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
            query = session.createQuery("from app.qms.StrategicChange sc order by number asc");
            return query.list();
        } catch (HibernateException e) {
            //System.err.println("Hibernate Exception" + e.getMessage());
            //System.out.println("ffffffffffffffffffffffffffff" + e.getMessage());
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

    public List getCapa(String clientId) {

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
            query = session.createQuery("from app.qms.Capa capa order by number asc where reportedbydesc = '" + clientId + "'");
            return query.list();
        } catch (HibernateException e) {
            //System.err.println("Hibernate Exception" + e.getMessage());
            //System.out.println("ffffffffffffffffffffffffffff" + e.getMessage());
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

    public String getCapaForYear(String clientId) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        String alertText = "";
        List result = new ArrayList();
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.add(Calendar.YEAR, -1); // to get previous year add -1
        Date lastYear = cal.getTime();

        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            query = session.createQuery("from app.qms.Capa capa where reportedbydesc = '" + clientId + "' and cdate between '" + df.format(lastYear) + "' and '" + df.format(date) + "' order by number asc ");
            List queryList = query.list();
            if (queryList.size() > 0) {
                alertText = "<div>This client has the following CAPA's in the past 12 months:</div>";
                for (int i = 0; i < queryList.size(); i++) {
                    Capa capa = (Capa) queryList.get(i);
                    alertText += "<div><a href='javascript:openSingleCapaWindow(" + capa.getNumber() + ")'>" + capa.getNumber() + "</a></div>";

                }
                alertText += "<div>Please review these CAPA's before proceeding.</div>";
            }

            return alertText;
        } catch (HibernateException e) {
            //System.err.println("Hibernate Exception" + e.getMessage());
            //System.out.println("ffffffffffffffffffffffffffff" + e.getMessage());
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

    public CapaId getSingleCapaId(String number) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from CapaId as ci where ci.capa_number = ?",
                    new Object[]{number},
                    new Type[]{Hibernate.STRING});

        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (CapaId) results.get(0);
        }

    }

    public StrategicChange_Id getSingleStrategicChange_Id(String number) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from StrategicChange_Id as ci where ci.number = ?",
                    new Object[]{number},
                    new Type[]{Hibernate.STRING});

        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (StrategicChange_Id) results.get(0);
        }

    }

    //get  Library by Tabs
    public List getQMSLibraryDocumentByTabs(String tabName, String queryCriteria) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        String queryString = "";
        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            if (tabName.equalsIgnoreCase("Capa")) {
                queryString = "select ul from app.qms.QMSLibrary ul where mainTab='Capa' and docId ='" + queryCriteria + "'";
            } else if (tabName.equalsIgnoreCase("Review")) {
                queryString = "select ul from app.qms.QMSLibrary ul" + queryCriteria;
            } else if (tabName.equalsIgnoreCase("Matrix")) {
                queryCriteria = " where mainTab!='Review' " + queryCriteria;
                queryString = "select ul from app.qms.QMSLibrary ul" + queryCriteria;
            } else {
                queryString = "select ul from app.qms.QMSLibrary ul where mainTab='" + tabName + "'";
            }
            query = session.createQuery(queryString);
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

    //get single Document
    public QMSLibrary getSingleQMSLibraryDocument(Integer libId) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            results = session.find("from QMSLibrary as ul where ul.id = ?",
                    new Object[]{libId},
                    new Type[]{Hibernate.INTEGER});
            if (results.isEmpty()) {
                return null;
            } else {
                QMSLibrary c = (QMSLibrary) results.get(0);
                return c;
            }

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

    public QMSLibraryHistory getSingleQMSLibraryHistory(Integer id) {
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
            query = session.createQuery("select ul from app.qms.QMSLibraryHistory ul where id=" + id);
            if (query.list().isEmpty()) {
                return null;
            } else {
                QMSLibraryHistory c = (QMSLibraryHistory) query.list().get(0);
                return c;
            }
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

//    public JSONObject getCompetenceList(){
//    /*
//         * Use the ConnectionFactory to retrieve an open
//         * Hibernate Session.
//         *
//         */
//         List<Competence> comp = new ArrayList<Competence>();
//        Session session = ConnectionFactory.getInstance().getSession();
//        Query query;
//
//        try {
//            /*
//             * Build HQL (Hibernate Query Language) query to retrieve a list
//             * of all the items currently stored by Hibernate.
//             */
//            query = session.createQuery("select ul from app.qms.QMSLibraryHistory ul where QMSLibId=" + id + " order by id desc");
//            return query.list();
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
    public List getQMSLibraryHistory(Integer id) {
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
            query = session.createQuery("select ul from app.qms.QMSLibraryHistory ul where QMSLibId=" + id + " order by id desc");
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

    public QMSLibraryHistory getLastQMSLibraryHistory(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from QMSLibraryHistory as vc where vc.QMSLibId = ? order by id desc",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (QMSLibraryHistory) results.get(0);
        }

    }

    public List getTrainingNotify(Integer trainingId) {
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
            query = session.createQuery("select ul from app.qms.TrainingNotify ul where trainingId=" + trainingId + " and userId<>" + 0);

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

    public List getTrainingNotifyPerUser(Integer userId) {
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

            // +"  AND (ul.trainingId NOT IN (SELECT t.docId FROM app.user.training t WHERE t.ID_user="+ userId+" AND t.docId IS NOT NULL) OR ul.departmentId NOT IN (SELECT t.departmentId FROM app.user.training t WHERE t.ID_user="+ userId+" AND t.departmentId IS NOT NULL))"
            query = session.createQuery("SELECT ul FROM app.qms.TrainingNotify ul WHERE ul.userId = " + userId + "  AND (ul.trainingId NOT IN (SELECT t.docId FROM app.user.Training t WHERE t.User=" + userId + " AND t.docId IS NOT NULL) OR ul.departmentId NOT IN (SELECT t.departmentId FROM app.user.Training t WHERE t.User=" + userId + " AND t.departmentId IS NOT NULL))");

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

    public TrainingNotify getTrainingNotify(Integer deptartmentId, Integer trainingId, Integer userId) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            results = session.find("from TrainingNotify as tn where tn.departmentId = ? and trainingId = ? and tn.userId = ?",
                    new Object[]{deptartmentId, trainingId, userId},
                    new Type[]{Hibernate.INTEGER, Hibernate.INTEGER, Hibernate.INTEGER});
            if (results.isEmpty()) {
                return null;
            } else {
                TrainingNotify c = (TrainingNotify) results.get(0);
                return c;
            }

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

    public List getQMSActionList() {
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

            query = session.createQuery("SELECT ul FROM app.qms.QMSAction ul");

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

    public QMSAction getSingleQMSAction(int id) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from QMSAction as ac where ac.id = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (QMSAction) results.get(0);
        }

    }

    public List getApproval() {

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
            query = session.createQuery("select approval from app.qms.Approval approval");
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

    public List getApproval(Integer userId) {

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
            query = session.createQuery("select approval from app.qms.Approval approval where userId = " + userId);
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

    public Approval getSingleApproval(int userId, int docId) throws HibernateException {
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
            query = session.createQuery("select approval from app.qms.Approval approval where docId = " + docId + " and userId = " + userId);
            return (Approval) query.list().get(0);
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

    public Approval getSingleApproval(int userId, String mainTab) throws HibernateException {
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
            query = session.createQuery("select approval from app.qms.Approval approval where mainTab = '" + mainTab + "' and userId = " + userId);
            return (Approval) query.list().get(0);
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

    List getAdminApproval() {

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
            query = session.createQuery("select approval from app.qms.Approval approval where mainTab is not null");
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

    public RiskProbability getSingleRiskProbability(int id) throws HibernateException {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {
            ////System.out.println("Initializing projectId: "+id);

            results = session.find("from RiskProbability as probability where probability.id = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                RiskProbability rm = (RiskProbability) results.get(0);

                return rm;
            }

        } catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
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

    public RiskSeverity getSingleRiskSeverity(int id) throws HibernateException {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {
            ////System.out.println("Initializing projectId: "+id);

            results = session.find("from RiskSeverity as severity where severity.id = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                RiskSeverity rm = (RiskSeverity) results.get(0);

                return rm;
            }

        } catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
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

    public RiskMitigation getSingleRiskMitigation(int id) throws HibernateException {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {
            ////System.out.println("Initializing projectId: "+id);

            results = session.find("from RiskMitigation as mitigation where mitigation.id = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                RiskMitigation rm = (RiskMitigation) results.get(0);

                Hibernate.initialize(rm.getProbability());
                Hibernate.initialize(rm.getSeverity());

                return rm;
            }

        } catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
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

    public List getAllRiskMitigation() {

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
            query = session.createQuery("select riskmitigation from app.qms.RiskMitigation riskmitigation");
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

    public List getAllRiskProbability() {

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
            query = session.createQuery("select riskprobability from app.qms.RiskProbability riskprobability");
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

    public List getAllRiskSeverity() {

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
            query = session.createQuery("select riskseverity from app.qms.RiskSeverity riskseverity");
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

    public RiskHazardIndex getSingleRiskAcceptabilityIndex(int severity, int probability) throws HibernateException {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {
            ////System.out.println("Initializing projectId: "+id);app.qms.RiskHazardIndex

            results = session.find("from RiskHazardIndex as hazardindex where hazardindex.severity = ? and hazardindex.probability = ?",
                    new Object[]{severity, probability},
                    new Type[]{Hibernate.INTEGER, Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                RiskHazardIndex rm = (RiskHazardIndex) results.get(0);

                return rm;
            }

        } catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
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

    public JSONArray getQMSDocsmatrix(String mainTab, String query, Boolean isAdminUser) {

        JSONArray results = new JSONArray();
        if (query == null) {
            query = "";
        }
        List docList = QMSService.getInstance().getQMSLibraryDocumentByTabs(mainTab, query);

        for (int i = 0; i < docList.size(); i++) {
            QMSLibrary lu = (QMSLibrary) docList.get(i);
            JSONObject jo = new JSONObject();

            jo.put("id", lu.getId());
            jo.put("title", lu.getTitle());
            jo.put("description", lu.getDescription());
            jo.put("category", lu.getCategory());
            jo.put("docId", lu.getDocId());
            jo.put("docFormat", lu.getFormat());
            if (lu.getLink() == null) {
            } else if (lu.getLink().equalsIgnoreCase("")) {
            } else {
                jo.put("htmlLink", "<a href=\"" + lu.getLink() + "\" target=\"_blank\">Go to Web</a>");
            }
            jo.put("version", lu.getVersion());
            jo.put("docType", lu.getType());
            if (lu.getFileSaveName() == null) {
            } else if (lu.getFileSaveName().equalsIgnoreCase("")) {
            } else {
                jo.put("readFile", "<a href=\"http://excelnet.xltrans.com/logo/Library/QMS/" + lu.getFileSaveName() + "\" target=\"_blank\">" + lu.getFileName() + "</a>");
//                jo.put("readFlie",  "NKS");
            }

            jo.put("release", lu.getReleaseDate());
            jo.put("owner", lu.getOwner());
            jo.put("mainTab", lu.getMainTab());
            jo.put("isoreference", lu.getIsoreference());

            jo.put("history", "<a " + HrHelper.LINK_STYLE + " href=../qmsLibraryHistory.do?mainTab=" + mainTab + "&id=" + lu.getId() + ">Click</a>");
            jo.put("admin", "<a " + HrHelper.LINK_STYLE + " href=../qmsLibraryAdmin.do?mainTab=" + mainTab + "&id=" + lu.getId() + ">Click</a>");
            if (isAdminUser) {
                jo.put("update", "<a " + HrHelper.LINK_STYLE + " href=../qmsLibUpdateDocumentPreAction.do?mainTab=" + mainTab + "&id=" + lu.getId() + ">Click</a>");
            }
            jo.put("training", "<a " + HrHelper.LINK_STYLE + " href=../qmsLibraryTraining.do?mainTab=" + mainTab + "&id=" + lu.getId() + ">Click</a>");

            results.put(jo);
        }
        return results;
    }

    public JSONObject getISOStandard() throws HibernateException {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        List<String> isoName = new ArrayList<>();

        JSONObject isoObj = new JSONObject();

        JSONArray isoArr = new JSONArray();
        Session session = ConnectionFactory.getInstance().getSession();
        List<ISOStandard> results = null;
        try {
            ////System.out.println("Initializing projectId: "+id);app.qms.RiskHazardIndex

            results = session.find("from ISOStandard as isoStandard ");

            if (results.isEmpty()) {
                return null;
            } else {
                JSONObject isoNameObj = new JSONObject();

                for (ISOStandard isoStandard : results) {
                    if (!isoName.contains(isoStandard.getIso())) {
                        isoObj.put(isoStandard.getIso(), new JSONObject());
                        isoName.add(isoStandard.getIso());
                    }
                }
                for (String iso : isoName) {
                    List<String> headerName = new ArrayList<>();
                    for (ISOStandard isoStandard : results) {
                        if (isoStandard.getIso().equalsIgnoreCase(iso) && !headerName.contains(isoStandard.getHeader())) {
                            headerName.add(isoStandard.getHeader());
                        }
                    }

                    JSONObject headerNameObj = new JSONObject();
                    for (String header : headerName) {
                        JSONArray headerArray = new JSONArray();

                        for (ISOStandard isoStandard : results) {
                            if (isoStandard.getIso().equalsIgnoreCase(iso) && header.equalsIgnoreCase(isoStandard.getHeader())) {
                                JSONObject headerObj = new JSONObject();
                                headerObj.put("content", isoStandard.getContent());
                                headerObj.put("id", isoStandard.getId());
                                headerObj.put("headerNo", isoStandard.getHeaderNo());
                                headerObj.put("contentNo", isoStandard.getContentNo());
                                headerArray.put(headerObj);
                            }
                        }
                        headerNameObj.put(header, headerArray);
                    }
                    //System.out.println(headerNameObj);   
                    isoObj.put(iso, headerNameObj);
                }
            }
        } catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
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

//        Set key = isoObj.keySet();
        ArrayList<String> keySet = new ArrayList<String>(isoObj.keySet());
        for (String key : keySet) {

        }
        return isoObj;
    }

    public JSONObject getISOStandard(int docId) throws HibernateException {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        List<String> isoName = new ArrayList<>();

        JSONObject isoObj = new JSONObject();

        JSONArray isoArr = new JSONArray();
        Session session = ConnectionFactory.getInstance().getSession();
        List<ISOStandard> results = null;
        List<Integer> isoDoc = getISODoc(docId);
        try {
            ////System.out.println("Initializing projectId: "+id);app.qms.RiskHazardIndex

            results = session.find("from ISOStandard as isoStandard");

            if (results.isEmpty()) {
                return null;
            } else {
                JSONObject isoNameObj = new JSONObject();

                for (ISOStandard isoStandard : results) {
                    if (!isoName.contains(isoStandard.getIso())) {
                        isoObj.put(isoStandard.getIso(), new JSONObject());
                        isoName.add(isoStandard.getIso());
                    }
                }
                for (String iso : isoName) {
                    List<String> headerName = new ArrayList<>();
                    for (ISOStandard isoStandard : results) {
                        if (isoStandard.getIso().equalsIgnoreCase(iso) && !headerName.contains(isoStandard.getHeader())) {
                            headerName.add(isoStandard.getHeader());
                        }
                    }

                    JSONObject headerNameObj = new JSONObject();
                    for (String header : headerName) {
                        JSONArray headerArray = new JSONArray();

                        for (ISOStandard isoStandard : results) {
                            if (isoStandard.getIso().equalsIgnoreCase(iso) && header.equalsIgnoreCase(isoStandard.getHeader())) {
                                JSONObject headerObj = new JSONObject();
                                headerObj.put("content", isoStandard.getContent());
                                headerObj.put("id", isoStandard.getId());
                                headerObj.put("headerNo", isoStandard.getHeaderNo());
                                headerObj.put("contentNo", isoStandard.getContentNo());
                                if (isoDoc.contains(isoStandard.getId())) {
                                    headerObj.put("status", "checked");
                                }
                                headerArray.put(headerObj);
                            }
                        }
                        headerNameObj.put(header, headerArray);
                    }
                    //System.out.println(headerNameObj);   
                    isoObj.put(iso, headerNameObj);
                }
            }
        } catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
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

        return isoObj;
    }

    private List<Integer> getISODoc(int docId) throws HibernateException {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        List<Integer> iso = new ArrayList<>();

        JSONObject isoObj = new JSONObject();

        JSONArray isoArr = new JSONArray();
        Session session = ConnectionFactory.getInstance().getSession();
        List<IsoDoc> results = new ArrayList<>();
        try {
            ////System.out.println("Initializing projectId: "+id);app.qms.RiskHazardIndex

            results = session.find("from IsoDoc as isoDoc where docId = ?",
                    new Object[]{docId},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return iso;
            } else {

                for (IsoDoc isoDoc : results) {
                    if (!iso.contains(isoDoc.getIsoStandard())) {

                        iso.add(isoDoc.getIsoStandard());
                    }
                }
            }
        } catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
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

        return iso;
    }

    List<Integer> getISODocList(String[] isoStandard) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        List<Integer> iso = new ArrayList<>();
        Set inQuery = new HashSet();
        for (String isoId : isoStandard) {

            inQuery.add(Integer.parseInt(isoId));
        }

        JSONArray isoArr = new JSONArray();
        Session session = ConnectionFactory.getInstance().getSession();
        Criteria criteria = session.createCriteria(IsoDoc.class);
        criteria.add(Expression.in("isoStandard", inQuery));

        try {
            List<IsoDoc> results = criteria.list();

            if (results.isEmpty()) {
                return iso;
            } else {

                for (IsoDoc isoDoc : results) {
                    if (!iso.contains(isoDoc.getDocId())) {

                        iso.add(isoDoc.getDocId());
                    }
                }
            }
        } catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
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

        return iso;
    }

    public StrategicChange_Planner getSingleStrategicChange_Planner(int id) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from StrategicChange_Planner as cp where cp.id = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (StrategicChange_Planner) results.get(0);
        }

    }

    public List<StrategicChange_Planner> getStrategicPlanners(int scid) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List<StrategicChange_Planner> results = new ArrayList<>();
        try {
            ////System.out.println("Initializing projectId: "+id);app.qms.RiskHazardIndex

            results = session.find("from StrategicChange_Planner as scp where scId = ?",
                    new Object[]{scid},
                    new Type[]{Hibernate.INTEGER});

        } catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
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

        return results;
    }

    Competence getSingleUserCompetence(int userId, Integer competence, int cYear) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Competence as cp where cp.userId = ? and cp.competence = ? and cp.cyear = ?",
                    new Object[]{userId, competence, cYear},
                    new Type[]{Hibernate.INTEGER, Hibernate.INTEGER, Hibernate.INTEGER});

        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (Competence) results.get(0);
        }
    }
    
    List<Competence> getUserCompetenceLst(int userId) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Competence as cp where cp.userId = ? ",
                    new Object[]{userId},
                    new Type[]{Hibernate.INTEGER});

        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
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
        if (results.isEmpty()) {
            return null;
        } else {
            return results;
        }
    }

    public HashMap<String, Integer> getUserCompetence(int userId) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        HashMap<String, Integer> compMap = new HashMap<>();
        Session session = ConnectionFactory.getInstance().getSession();
        List<Competence> results = new ArrayList<>();
        try {
            ////System.out.println("Initializing projectId: "+id);app.qms.RiskHazardIndex

            results = session.find("from Competence as cp where cp.userId = ? ",
                    new Object[]{userId},
                    new Type[]{Hibernate.INTEGER});
            for (Competence comp : results) {
                compMap.put("req" + comp.getCompetence() + comp.getCyear(), comp.getRequired());
                compMap.put("act" + comp.getCompetence() + comp.getCyear(), comp.getActual());
                if(comp.getIsTrainingReq()){
                    compMap.put("train" + comp.getCyear(), 1);
                }else{
                     compMap.put("train" + comp.getCyear(), 0);
                }
                 compMap.put("role", StandardCode.getInstance().noNull(comp.getRole()));
            }

        } catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
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

        return compMap;
    }

    public HashMap<String, Object> getCompetence(int cYear) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        HashMap<String, Object> compMap = new HashMap<>();
        Session session = ConnectionFactory.getInstance().getSession();
        List<Competence> results = new ArrayList<>();
        try {
            ////System.out.println("Initializing projectId: "+id);app.qms.RiskHazardIndex

            results = session.find("from Competence as cp where cp.cyear = ? ",
                    new Object[]{cYear},
                    new Type[]{Hibernate.INTEGER});
      
            for (Competence comp : results) {
                compMap.put("req" + comp.getCompetence() + comp.getUserId(), comp.getRequired());
                compMap.put("act" + comp.getCompetence() + comp.getUserId(), comp.getActual());
        
                if(StandardCode.getInstance().noNull(comp.getIsTrainingReq())){
                    compMap.put("train" + comp.getUserId(), 1);
                }else{
                     compMap.put("train" + comp.getUserId(), 0);
                }
                if(null!=comp.getRole()){
                AdminMisc am = UserService.getInstance().getSingleAdminMiscById(StandardCode.getInstance().noNull(comp.getRole()));
                compMap.put("userColor" + comp.getUserId(), am.getBgColor());
           
                }
                
            }
            List currentUser = UserService.getInstance().getUserListCurrent();
            List competence = AdminService.getInstance().getAdminMiscList(AdminConstants.COMPETANCE);
            List<Integer> userIdLst = new ArrayList<>();
            for (int i = 0; i < currentUser.size(); i++) {
                int reqSum = 0, actSum = 0;
                User user = (User) currentUser.get(i);
                for (int ii = 0; ii < competence.size(); ii++) {
                    AdminMisc am = (AdminMisc) competence.get(ii);
                    reqSum += (Integer) compMap.getOrDefault("req" + am.getId() + user.getUserId(), 0);
                    actSum += (Integer) compMap.getOrDefault("act" + am.getId() + user.getUserId(), 0);
                }
                if (reqSum > 0) {
                    double per = ((double)actSum / (double)reqSum) * 100;
                    compMap.put("comp" + user.getUserId(), StandardCode.getInstance().formatDouble(per));
                }
                if (!userIdLst.contains(user.getUserId())) {
                    userIdLst.add(user.getUserId());
                }
                
            }

            for (int ii = 0; ii < competence.size(); ii++) {
                int reqSum = 0, actSum = 0;
                AdminMisc am = (AdminMisc) competence.get(ii);
                for (Integer userId : userIdLst) {

                    reqSum += (Integer) compMap.getOrDefault("req" + am.getId() + userId, 0);
                    actSum += (Integer) compMap.getOrDefault("act" + am.getId() + userId, 0);
                }
                if (reqSum > 0) {
                     double per = ((double)actSum / (double)reqSum) * 100;
                    compMap.put("cov" + am.getId(), StandardCode.getInstance().formatDouble(per));
                }

            }

        } catch (ObjectNotFoundException onf) {
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
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

        return compMap;
    }

}
