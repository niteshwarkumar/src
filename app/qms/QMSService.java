/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import net.sf.hibernate.*;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.*;
import net.sf.hibernate.Hibernate;
import java.util.*;
import app.db.*;
import java.sql.PreparedStatement;

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

    public QMSAudit addQMSAudit(QMSAudit q) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(q);

            tx.commit();
        } catch (HibernateException e) {
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

        return null;

    }

    public TrainingNotify addTrainingNotify(TrainingNotify t) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(t);

            tx.commit();
        } catch (HibernateException e) {
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

        return null;

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

    public Capa addCapa(Capa q) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(q);

            tx.commit();
        } catch (HibernateException e) {
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

        return null;

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

    public QMSAudit deleteAudit(QMSAudit p) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            System.out.println("product detailsin save-------------->" + p);
            session.delete(p);

            tx.commit();
        } catch (HibernateException e) {
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
        return p;
    }

     public TrainingNotify deletetTraininNotify(TrainingNotify p) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            System.out.println("product detailsin save-------------->" + p);
            session.delete(p);

            tx.commit();
        } catch (HibernateException e) {
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
        return p;
    }

         public QMSReportUpload deletetQMSReport(QMSReportUpload p) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            System.out.println("product detailsin save-------------->" + p);
            session.delete(p);

            tx.commit();
        } catch (HibernateException e) {
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
        return p;
    }

    public QMSReportUpload addUpload_Doc(QMSReportUpload p) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            session.save(p);

            tx.commit();

        } catch (HibernateException e) {
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
        return p;
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
            System.out.println("ffffffffffffffffffffffffffff" + e.getMessage());
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

    public static boolean deleteCapa(int id) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from capa where capa_id=" + id);

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

    public CapaId saveCapaId(CapaId capaId) {


        Session session = ConnectionFactory.getInstance().getSession();
        //boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(capaId);

            tx.commit();
        } catch (HibernateException e) {
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

        return null;

    }

    public Capa saveCapa(Capa q) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(q);

            tx.commit();
        } catch (HibernateException e) {
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

        return null;

    }

    //add a new client to the database
    public Integer addLibrary(QMSLibrary l) {

        Session session = ConnectionFactory.getInstance().getSession();
        Integer id = null;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(l);
            tx.commit();
            id = l.getId();
        } catch (HibernateException e) {
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
        return id;
    }

    //delete one client language pair
    public void deleteDocument(QMSLibrary libUp) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(libUp);
            tx.commit();
        } catch (HibernateException e) {
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
    }

    //get  Library by Tabs
    public List getQMSLibraryDocumentByTabs(String tabName,String queryCriteria) {
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
             if (tabName.equalsIgnoreCase("Review")) {
                queryString="select ul from app.qms.QMSLibrary ul"+queryCriteria ;
            } else
            if (tabName.equalsIgnoreCase("Matrix")) {
                queryCriteria=" where mainTab!='Review' "+queryCriteria ;
                queryString="select ul from app.qms.QMSLibrary ul"+queryCriteria ;
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

    public QMSLibraryHistory addHistory(QMSLibraryHistory q) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(q);

            tx.commit();
        } catch (HibernateException e) {
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

        return null;

    }

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

     public static boolean unlinkHistory(int id) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from qmslibraryhistory where id = "+id);

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
            query = session.createQuery("select ul from app.qms.TrainingNotify ul where trainingId=" + trainingId + " and userId<>"+ 0);

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
            query = session.createQuery("SELECT ul FROM app.qms.TrainingNotify ul WHERE ul.userId = "+ userId +"  AND (ul.trainingId NOT IN (SELECT t.docId FROM app.user.Training t WHERE t.User="+ userId+" AND t.docId IS NOT NULL) OR ul.departmentId NOT IN (SELECT t.departmentId FROM app.user.Training t WHERE t.User="+ userId+" AND t.departmentId IS NOT NULL))");


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


    public TrainingNotify getTrainingNotify(Integer deptartmentId, Integer trainingId,Integer userId) {
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
                    new Object[]{deptartmentId,trainingId,userId},
                    new Type[]{Hibernate.INTEGER,Hibernate.INTEGER,Hibernate.INTEGER});
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

     public QMSAction saveOrUpdateQMSAction(QMSAction ei) {
     Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(ei);

            tx.commit();
        } catch (HibernateException e) {
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
        return null;
       
    }

    
    public  QMSAction getSingleQMSAction(int id){
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

//    public QMSAction deleteQMSAction(QMSAction p) {
//        Session session = ConnectionFactory.getInstance().getSession();
//        boolean tf = true;
//
//        Transaction tx = null;
//
//        try {
//
//            tx = session.beginTransaction();
//            System.out.println("product detailsin save-------------->" + p);
//            session.delete(p);
//
//            tx.commit();
//        } catch (HibernateException e) {
//            try {
//                tx.rollback(); //error
//            } catch (HibernateException he) {
//                System.err.println("Hibernate Exception" + e.getMessage());
//                throw new RuntimeException(e);
//            }
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
//
//                    session.close();
//                } catch (HibernateException e) {
//                    System.err.println("Hibernate Exception" + e.getMessage());
//                    throw new RuntimeException(e);
//                }
//
//            }
//        }
//        return p;
//    }

}
