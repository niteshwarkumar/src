/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.extjs.actions;

import app.db.ConnectionFactory;
import app.extjs.vo.Upload_Doc;
import app.resource.ResourceService;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.Type;

/**
 *
 * @author niteshwar
 */
public class DocService {

    private static DocService instance = null;

    public DocService() {
    }

    //return the instance of ResourceService
    public static synchronized DocService getInstance() {
        /*
     * Creates the Singleton instance, if needed.
     *
         */
        if (instance == null) {
            instance = new DocService();
        }
        return instance;
    }

    public List<Upload_Doc> getDocList(int resourceId, String type) {
        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     * resource.lastName, resource.firstName, resource.companyName
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List<Upload_Doc> results = new ArrayList();

        try {
            results = session.find("from Upload_Doc as uploadDoc where uploadDoc.resourceId =" + resourceId + " and uploadDoc.type ='" + type + "'");
        } catch (HibernateException ex) {
            Logger.getLogger(ResourceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
       * Regardless of whether the above processing resulted in an Exception
       * or proceeded normally, we want to close the Hibernate session.  When
       * closing the session, we must allow for the possibility of a Hibernate
       * Exception.
       *
         */
        if (session != null) {
            try {
                session.close();
            } catch (HibernateException e) {
                System.err.println("Hibernate Exception" + e.getMessage());
                throw new RuntimeException(e);
            }

        }
        if (results.isEmpty()) {
            return new ArrayList();
        } else {
            return results;
        }
    }

    public List<Upload_Doc> getDocList(String type) {
        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     * resource.lastName, resource.firstName, resource.companyName
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List<Upload_Doc> results = new ArrayList();

        try {
            results = session.find("from Upload_Doc as uploadDoc where uploadDoc.type ='" + type + "'");
        } catch (HibernateException ex) {
            Logger.getLogger(ResourceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
       * Regardless of whether the above processing resulted in an Exception
       * or proceeded normally, we want to close the Hibernate session.  When
       * closing the session, we must allow for the possibility of a Hibernate
       * Exception.
       *
         */
        if (session != null) {
            try {
                session.close();
            } catch (HibernateException e) {
                System.err.println("Hibernate Exception" + e.getMessage());
                throw new RuntimeException(e);
            }

        }
        if (results.isEmpty()) {
            return new ArrayList();
        } else {
            return results;
        }
    }
    
    public Upload_Doc getUploadDoc(int CQuote) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Upload_Doc as Upload_Doc where Upload_Doc.QuoteID = ? ",
                    new Object[]{CQuote},
                    new Type[]{Hibernate.INTEGER});

            //System.out.println("results");
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (Upload_Doc) results.get(0);
        }

    }
    
    public List getUploadDocList(int CQuote) {
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
             *///select pathname from Upload_Doc as Upload_Doc where Upload_Doc.QuoteID ="+ CQuote
            query = session.createQuery("select uload from app.extjs.vo.Upload_Doc uload where QuoteID =" + CQuote);
            // "select quote from app.quote.Quote1 quote where quote.status = 'pending' order by quote.number"
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

    public List getUploadDocList(int pid, String category) {
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
             *///select pathname from Upload_Doc as Upload_Doc where Upload_Doc.QuoteID ="+ CQuote
            query = session.createQuery("select uload from app.extjs.vo.Upload_Doc uload where  type = '"+category+"' and  projectID =" + pid);
            // "select quote from app.quote.Quote1 quote where quote.status = 'pending' order by quote.number"
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
    
    public Upload_Doc getUploadDoc(int pid, String category) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
    List results = null;
        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             *///select pathname from Upload_Doc as Upload_Doc where Upload_Doc.QuoteID ="+ CQuote
            query = session.createQuery("select uload from app.extjs.vo.Upload_Doc uload where  type = '"+category+"' and  projectID =" + pid);
            // "select quote from app.quote.Quote1 quote where quote.status = 'pending' order by quote.number"
            results =  query.list();
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (Upload_Doc) results.get(0);
        }



    }
    
    public List getUploadDocList(int id, String category, String identifier) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        
        String qryIdentStr = "projectID";
        if(identifier.equalsIgnoreCase("OTHID")){
            qryIdentStr = "othId";
        }

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             *///select pathname from Upload_Doc as Upload_Doc where Upload_Doc.QuoteID ="+ CQuote
            query = session.createQuery("select uload from app.extjs.vo.Upload_Doc uload where  type = '"+category+"' and  "+qryIdentStr+" =" + id);
            // "select quote from app.quote.Quote1 quote where quote.status = 'pending' order by quote.number"
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

}
