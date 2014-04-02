/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.comm;

import net.sf.hibernate.*;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.*;
import net.sf.hibernate.Hibernate;
import java.util.*;
import app.db.*;

/**
 *
 * @author Niteshwar
 */
public class CommService {

 private static CommService instance = null;

    public CommService() {
    }
        //return the instance of CommService
    public static synchronized CommService getInstance() {
        /*
         * Creates the Singleton instance, if needed.
         *
         */
        if (instance == null) {
            instance = new CommService();
        }
        return instance;
    }

     //add a new client to the database
    public LibraryUpload addLibrary(LibraryUpload l) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(l);
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
        return l;
    }

        //delete one client language pair
    public void deleteDocument(LibraryUpload libUp) {

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
    public List getLibraryDocumentByTabs(String tabName,String subTab) {
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
            query = session.createQuery("select ul from app.comm.LibraryUpload ul where maintab='"+tabName+"' and heading='"+subTab+"' order by uploaddate desc");
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
    
     //get  Library by Tabs
    public List getLibraryDocumentByTabs(String tabName,String subTab, Integer clientId) {
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
            query = session.createQuery("select ul from app.comm.LibraryUpload ul where maintab='"+tabName+"' and heading='"+subTab+"' and clientId="+clientId +" order by uploaddate desc");
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
    public LibraryUpload getSingleLibraryDocument(Integer libId) {
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
            results = session.find("from LibraryUpload as ul where ul.libId = ?",
                    new Object[]{libId},
                    new Type[]{Hibernate.INTEGER});
            if (results.isEmpty()) {
                return null;
            } else {
                LibraryUpload c = (LibraryUpload) results.get(0);
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



}
