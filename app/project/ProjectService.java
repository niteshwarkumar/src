//ProjectService.java contains the db code related to clients to actually read/write
//to/from db
package app.project;

import app.admin.AdminService;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.hibernate.*;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.*;
import net.sf.hibernate.Hibernate;
import app.db.*;
import app.client.*;
import app.extjs.vo.Dropdown;
import app.hr.HrService;
import app.user.*;
import app.quote.*;
import app.standardCode.*;
import net.sf.hibernate.Criteria;
import net.sf.hibernate.expression.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProjectService {

    private static ProjectService instance = null;

    public ProjectService() {
    }

    //return the instance of ProjectService
    public static synchronized ProjectService getInstance() {
        /*
         * Creates the Singleton instance, if needed.
         *
         */
        if (instance == null) {
            instance = new ProjectService();
        }
        return instance;
    }

    //add inspections to this project to the db
    public Client addClient(Client c) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.save(c);
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
        return c;
    }

      //add inspections to this project to the db
    public Project_Technical addUpdateProjectTechnical(Project_Technical pt) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.save(pt);
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
        return pt;
    }

       public static boolean unlinkProjectTechnical(int id) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from project_technical where projectid = "+id);

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


      //add inspections to this project to the db
    public QualityReport updateQulityReport(QualityReport c) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(c);
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
        return c;
    }

    //delete one inspection
    public void deleteInspection(Inspection object) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(object);
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

    public void deleteProjectData(Session session, Project object) {



        // Transaction tx = null;

        try {
            // System.out.println("deleting...");
            // tx = session.beginTransaction();
            if (object.getSourceDocs() != null) {

                for (Iterator iter = object.getSourceDocs().iterator(); iter.hasNext();) {
                    SourceDoc sd = (SourceDoc) iter.next();
                    for (Iterator iter2 = sd.getTargetDocs().iterator(); iter2.hasNext();) {
                        TargetDoc td = (TargetDoc) iter2.next();
                        //Now, delete all tasks from target docs
                        for (Iterator iter3 = td.getLinTasks().iterator(); iter3.hasNext();) {
                            LinTask lt = (LinTask) iter3.next();
                            System.out.println("deleting lt:" + lt);
                            session.delete(lt);
                        }

                        for (Iterator iter4 = td.getDtpTasks().iterator(); iter4.hasNext();) {
                            DtpTask lt = (DtpTask) iter4.next();
                            System.out.println("deleting DtpTask:" + lt);
                            session.delete(lt);
                        }

                        for (Iterator iter4 = td.getEngTasks().iterator(); iter4.hasNext();) {
                            EngTask lt = (EngTask) iter4.next();
                            System.out.println("deleting EngTask:" + lt);
                            session.delete(lt);
                        }

                        for (Iterator iter4 = td.getOthTasks().iterator(); iter4.hasNext();) {
                            OthTask lt = (OthTask) iter4.next();
                            System.out.println("deleting OthTask:" + lt);
                            session.delete(lt);
                        }

                        //delete target doc
                        System.out.println("deleting Target Doc");
                        session.delete(td);

                    }

                    //delete source doc
                    System.out.println("deleting Source Doc");
                    session.delete(sd);

                }

            }

            //delete all inspections:
            for (Iterator iter5 = object.getInspections().iterator(); iter5.hasNext();) {
                Inspection insp = (Inspection) iter5.next();
                System.out.println("deleting Inspection");
                session.delete(insp);
            }

            //tx.commit();


            //finally, delete the project

            //System.out.println("deleting Project");
            //session.delete(object);

        } catch (HibernateException e) {
            // try {
            //     tx.rollback(); //error
            //  }
            //  catch (HibernateException he) {
            //      System.out.println("Hibernate Exception" + e.getMessage());
            //	throw new RuntimeException(e);
            //  }
            System.out.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            /*if (session != null)
            {
            try
            {

            session.close();
            }
            catch (HibernateException e)
            {
            System.out.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
            }

            }*/
        }
    }

    /* public void deleteProject(Project object){
    Session session = ConnectionFactory.getInstance().getSession();



    if (session != null)
    {
    try
    {

    session.close();
    }
    catch (HibernateException e)
    {
    System.err.println("Hibernate Exception" + e.getMessage());
    throw new RuntimeException(e);
    }

    }

    }*/
    public void deleteProject(Project object) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            deleteProjectData(session, object);
            try {
                tx.commit();
            } catch (Exception e) {
                System.out.println(e);
            }
            session.close();
            System.out.println("here 1");

            session = ConnectionFactory.getInstance().getSession();
            tx = session.beginTransaction();
            deleteProjectsQuoteData(session, object);
            try {
                tx.commit();
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("here 2");

            session.close();
            session = ConnectionFactory.getInstance().getSession();
            tx = session.beginTransaction();
            deleteProjectItself(session, object);
            System.out.println("after 3");

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
    }

    public void deleteProjectsQuoteData(Session session, Project object) {
        try {



            //Now delete Quotes
            for (Iterator iter5 = object.getQuotes().iterator(); iter5.hasNext();) {
                Quote1 q = (Quote1) iter5.next();
                Quote1 q2 = QuoteService.getInstance().getSingleQuote(q.getQuote1Id());
                for (Iterator iter = q2.getSourceDocs().iterator(); iter.hasNext();) {
                    SourceDoc sd = (SourceDoc) iter.next();
                    for (Iterator iter2 = sd.getTargetDocs().iterator(); iter2.hasNext();) {
                        TargetDoc td = (TargetDoc) iter2.next();
                        //Now, delete all tasks from target docs
                        for (Iterator iter3 = td.getLinTasks().iterator(); iter3.hasNext();) {
                            LinTask lt = (LinTask) iter3.next();
                            System.out.println("deleting lt for quotes:" + lt);
                            session.delete(lt);
                        }

                        for (Iterator iter4 = td.getDtpTasks().iterator(); iter4.hasNext();) {
                            DtpTask lt = (DtpTask) iter4.next();
                            System.out.println("deleting DtpTask for quotes:" + lt);
                            session.delete(lt);
                        }

                        for (Iterator iter4 = td.getEngTasks().iterator(); iter4.hasNext();) {
                            EngTask lt = (EngTask) iter4.next();
                            System.out.println("deleting EngTask for quotes:" + lt);
                            session.delete(lt);
                        }

                        for (Iterator iter4 = td.getOthTasks().iterator(); iter4.hasNext();) {
                            OthTask lt = (OthTask) iter4.next();
                            System.out.println("deleting OthTask for quotes:" + lt);
                            session.delete(lt);
                        }



                        //delete target doc
                        System.out.println("deleting Target Doc for quotes");
                        session.delete(td);

                    }


                    //delete source doc
                    System.out.println("deleting Source Doc for quotes");
                    session.delete(sd);

                }
                List fObjList = QuoteService.getInstance().getFileListByQuote(q.getQuote1Id());
                for (int f = 0; f < fObjList.size(); f++) {
                    File fObj = (File) fObjList.get(f);
                    QuoteService.getInstance().deleteFile(fObj);
                }






            }

            for (Iterator iter5 = object.getQuotes().iterator(); iter5.hasNext();) {
                Quote1 obj = (Quote1) iter5.next();


                List cq = QuoteService.getInstance().getClient_Quote(Integer.valueOf(obj.getQuote1Id()));


                try {
                    for (int i = 0; i < cq.size(); i++) {
                        Client_Quote q1 = (Client_Quote) cq.get(i);
                        QuoteService.getInstance().deleteClient_Quote(q1);
                    }
                } catch (Exception e) {
                }


                QuoteService.getInstance().deleteQuote(obj);


                //QuoteService.getInstance().deleteQuote(q);
            }


        } catch (HibernateException e) {
        } finally {
            /*if (session != null)
            {
            try
            {

            session.close();
            }
            catch (HibernateException e)
            {
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
            }

            }*/
        }
    }

    public void deleteProjectItself(Session session, Project object) {

        // session = ConnectionFactory.getInstance().getSession();

        //Transaction tx = null;

        try {

            //tx = session.beginTransaction();

            session.delete(object);
            //tx.commit();
        } catch (HibernateException e) {
            // try {
            // tx.rollback(); //error
            // }
            // catch (HibernateException he) {
            //     System.err.println("Hibernate Exception" + e.getMessage());
            //	throw new RuntimeException(e);
            // }
            System.err.println("Hibernate Exception" + e.getMessage());
            //throw new RuntimeException(e);
        } finally {
            /*if (session != null)
            {
            try
            {

            session.close();
            }
            catch (HibernateException e)
            {
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
            }

            }*/
        }
    }
    //delete one project
    /*
    public void deleteProject(Project object)
    {

    Session session = ConnectionFactory.getInstance().getSession();

    Transaction tx = null;

    try
    {

    tx = session.beginTransaction();

    session.delete(object);
    tx.commit();
    }
    catch (HibernateException e)
    {
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

    finally
    {
    if (session != null)
    {
    try
    {

    session.close();
    }
    catch (HibernateException e)
    {
    System.err.println("Hibernate Exception" + e.getMessage());
    throw new RuntimeException(e);
    }

    }
    }
    }
     */
    //delete one linTask

    public void deleteLinTask(LinTask lt) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(lt);
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

    //return one linTask with id given in argument
    public LinTask getSingleLinTask(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from LinTask as linTask where linTask.linTaskId = ?",
                    new Object[]{id},
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (LinTask) results.get(0);
        }

    }

    //delete one engTask
    public void deleteEngTask(EngTask et) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(et);
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

    //return one engTask with id given in argument
    public EngTask getSingleEngTask(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from EngTask as engTask where engTask.engTaskId = ?",
                    new Object[]{id},
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (EngTask) results.get(0);
        }

    }

    //delete one dtpTask
    public void deleteDtpTask(DtpTask dt) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(dt);
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

    //return one dtpTask with id given in argument
    public DtpTask getSingleDtpTask(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from DtpTask as dtpTask where dtpTask.dtpTaskId = ?",
                    new Object[]{id},
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (DtpTask) results.get(0);
        }

    }

    //delete one othTask
    public void deleteOthTask(OthTask ot) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(ot);
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

    //return one othTask with id given in argument
    public OthTask getSingleOthTask(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from OthTask as othTask where othTask.othTaskId = ?",
                    new Object[]{id},
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (OthTask) results.get(0);
        }

    }

    //delete one language
    public void deleteLanguage(Language l) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(l);
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

    //delete one currency
    public void deleteCurrency(Currency1 c) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(c);
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

    //return one language with id given in argument
    public Language getLanguage(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Language as language where language.languageId = ?",
                    new Object[]{id},
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (Language) results.get(0);
        }

    }

    //delete one quality (quality control)
    public void deleteQuality(Quality q) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(q);
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

    //delete one change (project change)
    public void deleteChange(Change1 c) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(c);
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

    //return one quality (quality control) with id given in argument
    public Quality getSingleQuality(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Quality as quality where quality.qualityId = ?",
                    new Object[]{id},
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (Quality) results.get(0);
        }

    }

    //return one change (project change) with id given in argument
    public Change1 getSingleChange(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Change1 as change where change.change1Id = ?",
                    new Object[]{id},
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (Change1) results.get(0);
        }

    }
    //return one invoice

    public ClientInvoice getSingleClientInvoice(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from ClientInvoice as ci where ci.clientInvoiceId = ?",
                    new Object[]{id},
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (ClientInvoice) results.get(0);
        }

    }

    //return invoice by Project
    public ClientInvoice getClientInvoiceByProject(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from ClientInvoice as ci where ci.Project = ?",
                    new Object[]{id},
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (ClientInvoice) results.get(0);
        }

    }

    //return one language with id given in argument
    public Language getSingleLanguage(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Language as language where language.languageId = ?",
                    new Object[]{id},
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (Language) results.get(0);
        }

    }

    //return one currency with id given in argument
    public Currency1 getSingleCurrency(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Currency1 as currency where currency.currency1Id = ?",
                    new Object[]{id},
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (Currency1) results.get(0);
        }

    }

    //add this source to the db, building one-to-many relationship between project and sourceDoc
    //return new SourceDoc id
    public Integer addSourceWithProject(Project p, SourceDoc sd) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;
        Integer id = null;

        try {

            tx = session.beginTransaction();

            //build relationship
            p.getSourceDocs().add(sd);
            sd.setProject(p);

            session.saveOrUpdate(sd);
            tx.commit();
            id = sd.getSourceDocId();
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

    public Integer UpdateSource(SourceDoc sd) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;
        Integer id = null;

        try {
            tx = session.beginTransaction();

            //build relationship

            session.saveOrUpdate(sd);
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
        return id;
    }

    public Integer UpdateTarget(TargetDoc td) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;
        Integer id = null;

        try {
            tx = session.beginTransaction();

            //build relationship

            session.saveOrUpdate(td);
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
        return id;
    }

    public Integer addSourceWithProject(Project p, SourceDoc sd, Client_Quote Client_QuoteId, Quote1 q) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;
        Integer id = null;

        try {

            tx = session.beginTransaction();

            //build relationship
            p.getSourceDocs().add(sd);
            //sd.setProject(p);
            sd.setClient_Quote(Client_QuoteId);
            sd.setQuote(q);
            // sd.setQuote(null)
            session.save(sd);
            tx.commit();
            id = sd.getSourceDocId();
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

    //add this inspection to the db, building one-to-many relationship between project and inspection
    //return new inspection id
    public Integer addInspectionWithProject(Project p, Inspection i) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;
        Integer id = null;

        try {

            tx = session.beginTransaction();

            //build relationship
            p.getInspections().add(i);
            i.setProject(p);

            session.save(i);
            tx.commit();
            id = i.getInspectionId();
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

    //add this project to the db, building one-to-many relationship between client and project
    //return new project id
    public Integer addProjectWithClient(Project p, Client c) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;
        Integer id = null;

        try {

            tx = session.beginTransaction();

            //build relationship
            p.setCompany(c);
            c.getProjects().add(p);

            session.saveOrUpdate(p);
            tx.commit();
            id = p.getProjectId();
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

    //add this quality to the db, building one-to-many relationship between project and quality
    //return new quality id
    public Integer addQualityWithProject(Project p, Quality q) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;
        Integer id = null;

        try {

            tx = session.beginTransaction();

            //build relationship
            p.getQualities().add(q);
            q.setProject(p);

            session.save(q);
            tx.commit();
            id = q.getQualityId();
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

    //add this change to the db, building one-to-many relationship between project and change
    //return new change id
    public Integer addChangeWithProject(Project p, Change1 c) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;
        Integer id = null;

        try {

            tx = session.beginTransaction();

            //build relationship
            p.getChange1s().add(c);
            c.setProject(p);

            session.save(c);
            tx.commit();
            id = c.getChange1Id();
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

    //add this invoice to the db, building one-to-many relationship between project and invoice
    //return new invoice id
    public Integer addInvoiceWithProject(Project p, ClientInvoice c) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;
        Integer id = null;

        try {

            tx = session.beginTransaction();

            //build relationship
            p.getChange1s().add(c);
            c.setProject(p);

            session.save(c);
            tx.commit();
            id = c.getClientInvoiceId();
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

    //return the language string (e.g., "English") given its id
    public String getLanguageString(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Language as language where language.languageId = ?",
                    new Object[]{id},
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
        if (results.isEmpty()) {
            return null;
        } else {
            return ((Language) results.get(0)).getLanguage();
        }

    }

    //return the language id given its string ("English")
    public Integer getLanguageId(String language) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Language as language where language.language = ?",
                    new Object[]{language},
                    new Type[]{Hibernate.STRING});


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
            return ((Language) results.get(0)).getLanguageId();
        }

    }

    //add a new client to the database
    public Industry addIndustry(Industry i) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.save(i);
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
        return i;
    }

    //add this language to the db
    //return new languageId
    public Integer addLanguage(Language l) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;
        Integer id = null;

        try {

            tx = session.beginTransaction();


            session.save(l);
            tx.commit();
            id = l.getLanguageId();
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

    //add this currency to the db
    //return new currencyId
    public Integer addCurrency(Currency1 c) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;
        Integer id = null;

        try {

            tx = session.beginTransaction();


            session.save(c);
            tx.commit();
            id = c.getCurrency1Id();
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

    //update an existing Project in database
    public Project updateProject(Project p) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(p);

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

    //update an existing Inspection in database
    public Inspection updateInspection(Inspection i) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(i);

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
        return i;
    }

    //update an existing LinTask in database
    public LinTask updateLinTask(LinTask lt) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(lt);

            tx.commit();
        } catch (HibernateException e) {
            try {
                tx.rollback(); //error
            } //commentted out statements in catch because the row, from a dyna form, could have been
            //deleted by someone else, while the .jsp page was being viewed 
            catch (HibernateException he) {
                System.err.println("Hibernate Exception" + e.getMessage());
                //throw new RuntimeException(e);
            }
            System.err.println("Hibernate Exception" + e.getMessage());
            //throw new RuntimeException(e);
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
        return lt;
    }

    //update an existing EngTask in database
    public EngTask updateEngTask(EngTask et) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(et);

            tx.commit();
        } catch (HibernateException e) {
            try {
                tx.rollback(); //error
            } //commentted out statements in catch because the row, from a dyna form, could have been
            //deleted by someone else, while the .jsp page was being viewed 
            catch (HibernateException he) {
                System.err.println("Hibernate Exception" + e.getMessage());
                //throw new RuntimeException(e);
            }
            System.err.println("Hibernate Exception" + e.getMessage());
            //throw new RuntimeException(e);
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
        return et;
    }

    //update an existing DtpTask in database
    public DtpTask updateDtpTask(DtpTask dt) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(dt);

            tx.commit();
        } catch (HibernateException e) {
            try {
                tx.rollback(); //error
            } //commentted out statements in catch because the row, from a dyna form, could have been
            //deleted by someone else, while the .jsp page was being viewed 
            catch (HibernateException he) {
                System.err.println("Hibernate Exception" + e.getMessage());
                //throw new RuntimeException(e);
            }
            System.err.println("Hibernate Exception" + e.getMessage());
            //throw new RuntimeException(e);
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
        return dt;
    }

    //update an existing OthTask in database
    public OthTask updateOthTask(OthTask ot) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(ot);

            tx.commit();
        } catch (HibernateException e) {
            try {
                tx.rollback(); //error
            } //commentted out statements in catch because the row, from a dyna form, could have been
            //deleted by someone else, while the .jsp page was being viewed 
            catch (HibernateException he) {
                System.err.println("Hibernate Exception" + e.getMessage());
                //throw new RuntimeException(e);
            }
            System.err.println("Hibernate Exception" + e.getMessage());
            //throw new RuntimeException(e);
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
        return ot;
    }

    //update an existing quality (quality control) in database
    public Quality updateQuality(Quality q) {
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
            } //commentted out statements in catch because the row, from a dyna form, could have been
            //deleted by someone else, while the .jsp page was being viewed 
            catch (HibernateException he) {
                System.err.println("Hibernate Exception" + e.getMessage());
                //throw new RuntimeException(e);
            }
            System.err.println("Hibernate Exception" + e.getMessage());
            //throw new RuntimeException(e);
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
        return q;
    }

    //update an existing language in database
    public Language updateLanguage(Language l) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(l);

            tx.commit();
        } catch (HibernateException e) {
            try {
                tx.rollback(); //error
            } //commentted out statements in catch because the row, from a dyna form, could have been
            //deleted by someone else, while the .jsp page was being viewed 
            catch (HibernateException he) {
                System.err.println("Hibernate Exception" + e.getMessage());
                //throw new RuntimeException(e);
            }
            System.err.println("Hibernate Exception" + e.getMessage());
            //throw new RuntimeException(e);
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

    //update an existing currency in database
    public Currency1 updateCurrency1(Currency1 c) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(c);

            tx.commit();
        } catch (HibernateException e) {
            try {
                tx.rollback(); //error
            } //commentted out statements in catch because the row, from a dyna form, could have been
            //deleted by someone else, while the .jsp page was being viewed 
            catch (HibernateException he) {
                System.err.println("Hibernate Exception" + e.getMessage());
                //throw new RuntimeException(e);
            }
            System.err.println("Hibernate Exception" + e.getMessage());
            //throw new RuntimeException(e);
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
        return c;
    }

    //get all projects
    public List getProjectList() {
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
            query = session.createQuery("select project from app.project.Project project order by project.number");

            // System.out.println(query.list().size());
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

    //get Last projects
    public List getLastProjectList() {
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
            query = session.createQuery("select project from app.project.Project project order by project.number desc Limit 0,1");

            // System.out.println(query.list().size());
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

    public List getProjectList1() {
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
            query = session.createQuery("select project.number from app.project.Project project order by project.number ");
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

    public List getProjectList2() {
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
            query = session.createQuery("select project.projectId from app.project.Project project order by project.number");
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

    //update an existing project change in database
    public Change1 updateChange(Change1 c) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(c);

            tx.commit();
        } catch (HibernateException e) {
            try {
                tx.rollback(); //error
            } //commentted out statements in catch because the row, from a dyna form, could have been
            //deleted by someone else, while the .jsp page was being viewed 
            catch (HibernateException he) {
                System.err.println("Hibernate Exception" + e.getMessage());
                //throw new RuntimeException(e);
            }
            System.err.println("Hibernate Exception" + e.getMessage());
            //throw new RuntimeException(e);
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
        return c;
    }

    //get all active or complete projects
    public List getProjectListActiveComplete() {
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
            query = session.createQuery("select project from app.project.Project project where project.status = 'active' or project.status = 'complete' order by project.number");
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

    //get all active projects
    public List getProjectListActive() {
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
            query = session.createQuery("select project from app.project.Project project where project.status = 'active' order by project.number");
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

    //get all active projects
    public List getProjectListActiveForPM(String pmName) {
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
            query = session.createQuery("select project from app.project.Project project where project.status = 'active' and (project.pm='" + pmName + "' or project.Company.Sales_rep='" + pmName + "') order by project.number");
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

    public List getClientProjectSearch(String status, Integer clientID, String projectNumber, String product, String productDescription, String notes, String invoicing, Date deliveryFrom, Date deliveryTo) {

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;
        //Integer clientID=100;
        System.out.println("product=====================================================================" + product);
        try {
            //retreive projects from database

            //this is the "one" class: it is the main criteria
            Criteria criteria = session.createCriteria(Project.class);

            //if subcriteria values are present from the form (for the "many" class),
            //then include subcriteria search values in main search (the "one" class)

            Criteria subCriteria2 = criteria.createCriteria("Company");
            subCriteria2.add(Expression.eq("clientId", clientID));


            if (status.equals("All")) {
                Disjunction any = Expression.disjunction();
                any.add(Expression.eq("status", "active"));
                any.add(Expression.eq("status", "complete"));
                any.add(Expression.eq("status", "on hold"));
                any.add(Expression.eq("status", "onhold"));
                criteria.add(any);
            } else {
                criteria.add(Expression.eq("status", status));
            }

            if (projectNumber.length() > 0) { //if projectNumber is desired
                projectNumber = projectNumber.replaceAll("[a-zA-Z]", "");
                projectNumber = projectNumber.trim();
                criteria.add(Expression.like("number", "%" + projectNumber + "%").ignoreCase());
            }

            if (product.length() > 0) {
                criteria.add(Expression.like("product", "%" + product + "%").ignoreCase());
            }
            if (productDescription.length() > 0) {
                criteria.add(Expression.like("productDescription", "%" + productDescription + "%").ignoreCase());
            }
            if (notes.length() > 0) {
                criteria.add(Expression.like("notes", "%" + notes + "%").ignoreCase());
            }

            //criteria.addOrder(Order.desc("startDate"));
            criteria.addOrder(Order.desc("number"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            results = criteria.list();

            //Now let's check if Invoicing is used as a search parameter, and trim down the list
            if ("invoiced".equals(invoicing) || "nonInvoiced".equals(invoicing)) {
                for (ListIterator iter = results.listIterator(); iter.hasNext();) {

                    Project p = (Project) iter.next();

                    boolean needsInvoicing = StandardCode.getInstance().isNonInvoiced(p);

                    if ("invoiced".equals(invoicing) && needsInvoicing) {
                        //Remove if searching for invoiced only and this one still needs invoicing
                        iter.remove();
                    } else if ("nonInvoiced".equals(invoicing) && !needsInvoicing) {
                        //Remove if searching for non-invoiced only and this does not need invoicing
                        iter.remove();
                    }

                }
            }

            //Now let's check if Invoicing is used as a search parameter, and trim down the list
            if (deliveryFrom != null || deliveryTo != null) {
                for (ListIterator iter = results.listIterator(); iter.hasNext();) {

                    Project p = (Project) iter.next();


                    if (p.getDeliveryDate() == null) {
                        iter.remove();
                    } else if (deliveryFrom != null && p.getDeliveryDate().compareTo(deliveryFrom) < 0) {
                        iter.remove();
                    } else if (deliveryTo != null && p.getDeliveryDate().compareTo(deliveryTo) > 0) {
                        iter.remove();
                    }

                }
            }

            try {
                for (ListIterator iter = results.listIterator(); iter.hasNext();) {
                    Project p = (Project) iter.next();
                    if (p.getNumber().contains("000000")) {
                        iter.remove();
                    }
                }
            } catch (Exception e) {
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

        if (results.isEmpty()) {
            return null;
        } else {
            return results;
        }
    }
 
    //search for project given the criteria
    public List getProjectSearch(String status, String companyName, String projectNumber, String product, String productDescription, String notes, String invoicing, Date deliveryFrom, Date deliveryTo, Date startFrom, Date startTo, String cancelled, String srcLang, String targetLang) {

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;
        System.out.println("product=====================================================================" + product);
        try {
            //retreive projects from database

            //this is the "one" class: it is the main criteria
            Criteria criteria = session.createCriteria(Project.class);

            //if subcriteria values are present from the form (for the "many" class),
            //then include subcriteria search values in main search (the "one" class)
            if (companyName.length() >= 1) {
                Criteria subCriteria = criteria.createCriteria("Company");
                subCriteria.add(Expression.like("Company_name", "%" + companyName + "%").ignoreCase());

            }
                
                  if (srcLang.length() >= 1) {
                      Criteria subCriteria = criteria.createCriteria("SourceDocs");                  
                      subCriteria.add(Expression.like("language", "%" + srcLang + "%").ignoreCase());
                      
                       if (targetLang.length() >= 1) {
                           Criteria subCriteria1 = subCriteria.createCriteria("TargetDocs");
                            subCriteria1.add(Expression.like("language", "%" + targetLang + "%").ignoreCase());
                       }
                    }  

            if (status.equals("All")) {
                Disjunction any = Expression.disjunction();
                any.add(Expression.eq("status", "active"));
                any.add(Expression.eq("status", "complete"));
                any.add(Expression.eq("status", "on hold"));
                any.add(Expression.eq("status", "onhold"));
                criteria.add(any);
            } else {
                criteria.add(Expression.eq("status", status));
            }

            if (projectNumber.length() > 0) { //if projectNumber is desired
                projectNumber = projectNumber.replaceAll("[a-zA-Z]", "");
                projectNumber = projectNumber.trim();
                criteria.add(Expression.like("number", "%" + projectNumber + "%").ignoreCase());
            }

            if (product.length() > 0) {
                criteria.add(Expression.like("product", "%" + product + "%").ignoreCase());
            }
            if (productDescription.length() > 0) {
                criteria.add(Expression.like("productDescription", "%" + productDescription + "%").ignoreCase());
            }
            if (notes.length() > 0) {
                criteria.add(Expression.like("notes", "%" + notes + "%").ignoreCase());
            }
             if(cancelled != null) {
                            criteria.add(Expression.eq("cancelled", "true"));
                        }
            

            //criteria.addOrder(Order.desc("startDate"));
            criteria.addOrder(Order.desc("number"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            results = criteria.list();

            //Now let's check if Invoicing is used as a search parameter, and trim down the list
            if ("invoiced".equals(invoicing) || "nonInvoiced".equals(invoicing)) {
                for (ListIterator iter = results.listIterator(); iter.hasNext();) {

                    Project p = (Project) iter.next();

                    boolean needsInvoicing = StandardCode.getInstance().isNonInvoiced(p);

                    if ("invoiced".equals(invoicing) && needsInvoicing) {
                        //Remove if searching for invoiced only and this one still needs invoicing
                        iter.remove();
                    } else if ("nonInvoiced".equals(invoicing) && !needsInvoicing) {
                        //Remove if searching for non-invoiced only and this does not need invoicing
                        iter.remove();
                    }

                }
            }

            //Now let's check if Invoicing is used as a search parameter, and trim down the list
            if (deliveryFrom != null || deliveryTo != null) {
                for (ListIterator iter = results.listIterator(); iter.hasNext();) {

                    Project p = (Project) iter.next();

                    if (p.getDeliveryDate() == null) {
                        iter.remove();
                    } else if (deliveryFrom != null && p.getDeliveryDate().compareTo(deliveryFrom) < 0) {
                        iter.remove();
                    } else if (deliveryTo != null && p.getDeliveryDate().compareTo(deliveryTo) > 0) {
                        iter.remove();
                    }

                }
            }
              //Now let's check if Invoicing is used as a search parameter, and trim down the list
            if (startFrom != null || startTo != null) {
                for (ListIterator iter = results.listIterator(); iter.hasNext();) {

                    Project p = (Project) iter.next();

                    if (p.getStartDate() == null) {
                        iter.remove();
                    } else if (startFrom != null && p.getStartDate().compareTo(startFrom) < 0) {
                        iter.remove();
                    } else if (startTo != null && p.getStartDate().compareTo(startTo) > 0) {
                        iter.remove();
                    }

                }
            }

            try {
                for (ListIterator iter = results.listIterator(); iter.hasNext();) {
                    Project p = (Project) iter.next();
                    if (p.getNumber().contains("000000")) {
                        iter.remove();
                    }
                }
            } catch (Exception e) {
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

        if (results.isEmpty()) {
            return null;
        } else {
            return results;
        }
    }

    //get my projects for this user
    public List getMyProjects(User u) {

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;

        try {
            //retreive projects from database

            //this is the "one" class: it is the main criteria
            Criteria criteria = session.createCriteria(Project.class);

            Criteria subCriteria = criteria.createCriteria("Company");
            //subCriteria.add(Expression.like("Company_name", "%" + companyName + "%").ignoreCase());

            criteria.add(Expression.eq("pm", u.getFirstName() + " " + u.getLastName()));
            criteria.add(Expression.eq("status", "active"));

            criteria.addOrder(Order.desc("number"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            results = criteria.list();

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
            return results;
        }
    }

    //get a resource's lin tasks
    public List getResourceLinTasksClient(String resourceId) {

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;

        try {
            //this is the "one" class: it is the main criteria
            Criteria criteria = session.createCriteria(LinTask.class);

            //get all tasks this resource has worked on
            criteria.add(Expression.eq("personName", resourceId));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            results = criteria.list();

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
            return results;
        }
    }

    //get a resource's eng tasks
    public List getResourceEngTasksClient(String resourceId) {

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;

        try {
            //this is the "one" class: it is the main criteria
            Criteria criteria = session.createCriteria(EngTask.class);

            //get all tasks this resource has worked on
            criteria.add(Expression.eq("personName", resourceId));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            results = criteria.list();

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
            return results;
        }
    }

    //get a resource's dtp tasks
    public List getResourceDtpTasksClient(String resourceId) {

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;

        try {
            //this is the "one" class: it is the main criteria
            Criteria criteria = session.createCriteria(DtpTask.class);

            //get all tasks this resource has worked on
            criteria.add(Expression.eq("personName", resourceId));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            results = criteria.list();

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
            return results;
        }
    }

    //get a resource's oth tasks
    public List getResourceOthTasksClient(String resourceId) {

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;

        try {
            //this is the "one" class: it is the main criteria
            Criteria criteria = session.createCriteria(OthTask.class);

            //get all tasks this resource has worked on
            criteria.add(Expression.eq("personName", resourceId));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            results = criteria.list();

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
            return results;
        }
    }

    //get a resource's lin tasks
    public List getResourceLinTasks(String resourceId) {

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;

        try {
            //this is the "one" class: it is the main criteria
            Criteria criteria = session.createCriteria(LinTask.class);

            //get all tasks this resource has worked on
            criteria.add(Expression.eq("personName", resourceId));

            //make sure score present
            criteria.add(Expression.isNotNull("score"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            results = criteria.list();

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
            return results;
        }
    }

    //get a resource's eng tasks
    public List getResourceEngTasks(String resourceId) {

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;

        try {
            //this is the "one" class: it is the main criteria
            Criteria criteria = session.createCriteria(EngTask.class);

            //get all tasks this resource has worked on
            criteria.add(Expression.eq("personName", resourceId));

            //make sure score present
            criteria.add(Expression.isNotNull("score"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            results = criteria.list();

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
            return results;
        }
    }

    //get a resource's dtp tasks
    public List getResourceDtpTasks(String resourceId) {

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;

        try {
            //this is the "one" class: it is the main criteria
            Criteria criteria = session.createCriteria(DtpTask.class);

            //get all tasks this resource has worked on
            criteria.add(Expression.eq("personName", resourceId));

            //make sure score present
            criteria.add(Expression.isNotNull("score"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            results = criteria.list();

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
            return results;
        }
    }

    //get a resource's oth tasks
    public List getResourceOthTasks(String resourceId) {

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;

        try {
            //this is the "one" class: it is the main criteria
            Criteria criteria = session.createCriteria(OthTask.class);

            //get all tasks this resource has worked on
            criteria.add(Expression.eq("personName", resourceId));

            //make sure score present
            criteria.add(Expression.isNotNull("score"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            results = criteria.list();

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
            return results;
        }
    }

    //return one project with id given in argument
    public Project getSingleProject(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {
            //System.out.println("Initializing projectId: "+id);

            results = session.find("from Project as project where project.projectId = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                Project p = (Project) results.get(0);

                Hibernate.initialize(p.getSourceDocs());
                //System.out.println("after p.getSourceDocs()");
                Hibernate.initialize(p.getQualities());
                //System.out.println("after p.getQualities()");
                Hibernate.initialize(p.getQuotes());
                // System.out.println("after p.getQuotes()");
                Hibernate.initialize(p.getInspections());
                //System.out.println("after p.getInspections()");
                Hibernate.initialize(p.getChange1s());
                //System.out.println("after p.getChange1s()");
                Hibernate.initialize(p.getClientInvoices());
                //System.out.println("after p.getClientInvoices()");
                Hibernate.initialize(p.getCompany());
                //System.out.println("after p.getCompany()");
                try {
                    Hibernate.initialize(p.getCompany().getClientLanguagePairs());
                    //System.out.println("Finished initializing projectId: "+id);
                } catch (Exception e) {
                }
                return p;
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

    //getSingleSource
    //return one project with projectNumber given in argument
    public Project getSingleProjectByNumber(String projectNumber) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Project as project where project.number = ?",
                    new Object[]{projectNumber},
                    new Type[]{Hibernate.STRING});

            if (results.isEmpty()) {
                return null;
            } else {
                Project p = (Project) results.get(0);

                return p;
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

    //return one client with id given in argument
    public ClientContact getSingleClientContact(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from ClientContact as clientContact where clientContact.id = ?",
                    new Object[]{id},
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (ClientContact) results.get(0);
        }

    }

    //update just the client, nothing was changed to any of the client's contacts
    public boolean clientUpdateNoContact(Client c) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(c);
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

        return t;
    }

    //update on contact within one client
    public boolean clientContactUpdate(ClientContact cc) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(cc);
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

        return t;
    }

    //place a new contact in db
    public ClientContact addContact(Client c, ClientContact cc) {
        Session session = ConnectionFactory.getInstance().getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            c.getContacts().add(cc);

            session.save(cc);
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
        return cc;
    }

    //return a Client object, but search for the client by compny name instead of by integer id
    public Client getSingleClientByName(String Company_name) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Client as client where client.Company_name = ?",
                    new Object[]{Company_name},
                    new Type[]{Hibernate.STRING});


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
            return (Client) results.get(0);
        }

    }

    //return a Client object, but search for the client by compny code instead of by integer id
    //this client is already in db, so check if code is already in db
    public Client getSingleClientByCode(String Company_code) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {
            results = session.find("from Client as client where client.Company_code = ?",
                    new Object[]{Company_code},
                    new Type[]{Hibernate.STRING});


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
            return (Client) results.get(0);
        }

    }

    //return a Client object, but search for the client by compny name instead of by integer id
    public Industry getClientIndustry(String Description) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Industry as industry where industry.Description = ?",
                    new Object[]{Description},
                    new Type[]{Hibernate.STRING});


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
            return (Industry) results.get(0);
        }

    }

    //return List of all languages
    public List getLanguageList() {
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
            query = session.createQuery("select language from app.project.Language language order by language.language");
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

    //return List of all currencies
    public List getCurrencyList() {
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
            query = session.createQuery("select currency from app.project.Currency1 currency");
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

    //return the new Project Number
    public String getNewProjectNumber() {
        String newProjectNumber = null;
        Project p = null;

        //get last project number
        ListIterator iter = null;
        //List projects = getProjectList(); //list of current projects sorted in order (e.g., 000000, 000001)
        List projects = getLastProjectList();
        String lastProjectNumber = new String("");
        if (projects != null && projects.size() > 0) {
            for (iter = projects.listIterator(); iter.hasNext(); iter.next()) {
            }
            iter.previous();
            p = (Project) iter.next();
            lastProjectNumber = p.getNumber(); //last project number
        } else { //for first project
            lastProjectNumber = "000000";
        }

        //start at 6500
        if (lastProjectNumber.compareTo("006500") < 0) {
            lastProjectNumber = "006499";
        }


        newProjectNumber = lastProjectNumber; //e.g., 000000

        if (lastProjectNumber.length() == 5) {
            lastProjectNumber = "0" + lastProjectNumber;
        } else if (lastProjectNumber.length() == 4) {
            lastProjectNumber = "00" + lastProjectNumber;
        }

        //build new project number
        int old = Integer.valueOf(lastProjectNumber.substring(0, 6)).intValue();  //e.g., 0
        old = old + 1; //e.g., 1
        String sIter = String.valueOf(new Integer(old)); //e.g., 1
        char[] oldChar = sIter.toCharArray(); //e.g., 1
        char[] newChar = lastProjectNumber.toCharArray(); //e.g., 000000

        //copy from right to left the new project number
        for (int i = 5, j = sIter.length() - 1; i > (5 - sIter.length()); i--, j--) {
            newChar[i] = oldChar[j];
        }

        newProjectNumber = String.valueOf(newChar); //covert new project number to string
        return newProjectNumber;
    }

    //return the new Po Number
    public String getNewPoNumber(Project p) {
        String newPoNumber = null;

        //START get last po number for this project (in one of four tasks)
        ArrayList poNums = new ArrayList();
        //for each source
        for (Iterator iter1 = p.getSourceDocs().iterator(); iter1.hasNext();) {
            SourceDoc sd = (SourceDoc) iter1.next();

            //for each target
            for (Iterator iter2 = sd.getTargetDocs().iterator(); iter2.hasNext();) {
                TargetDoc td = (TargetDoc) iter2.next();

                //for each of the four tasks
                for (Iterator iter = td.getLinTasks().iterator(); iter.hasNext();) {
                    LinTask t = (LinTask) iter.next();
                    //if po number assigned
                    if (t.getPoNumber() != null && t.getPoNumber().length() > 0) {
                        poNums.add(t.getPoNumber());
                    }
                }

                for (Iterator iter = td.getEngTasks().iterator(); iter.hasNext();) {
                    EngTask t = (EngTask) iter.next();
                    //if po number assigned
                    if (t.getPoNumber() != null && t.getPoNumber().length() > 0) {
                        poNums.add(t.getPoNumber());
                    }
                }

                for (Iterator iter = td.getDtpTasks().iterator(); iter.hasNext();) {
                    DtpTask t = (DtpTask) iter.next();
                    //if po number assigned
                    if (t.getPoNumber() != null && t.getPoNumber().length() > 0) {
                        poNums.add(t.getPoNumber());
                    }
                }

                for (Iterator iter = td.getOthTasks().iterator(); iter.hasNext();) {
                    OthTask t = (OthTask) iter.next();
                    //if po number assigned
                    if (t.getPoNumber() != null && t.getPoNumber().length() > 0) {
                        poNums.add(t.getPoNumber());
                    }
                }
            }
        } //END for each source

        //sort the poNumbers
        Collections.sort(poNums, ComparePoNumber.getInstance());

        String lastPoNumber;
        if (poNums.size() > 0) { //this project has po nums already
            lastPoNumber = (String) poNums.listIterator().next(); //last po number
        } else {
            lastPoNumber = "000";
        }
        newPoNumber = lastPoNumber; //e.g., 000        
        //END get last po number for this project (in one of four tasks)

        //build new po number
        int old = Integer.valueOf(lastPoNumber.substring(0, 3)).intValue();  //e.g., 0
        old = old + 1; //e.g., 1
        String sIter = String.valueOf(new Integer(old)); //e.g., 1
        char[] oldChar = sIter.toCharArray(); //e.g., 1
        char[] newChar = lastPoNumber.toCharArray(); //e.g., 000

        //copy from right to left the new po number
        for (int i = 2, j = sIter.length() - 1; i > (2 - sIter.length()); i--, j--) {
            newChar[i] = oldChar[j];
        }

        newPoNumber = String.valueOf(newChar); //covert new po number to string
        return newPoNumber;
    }

    //return the new Quality Number
    public String getNewQualityNumber(Project p) {
        String newQualityNumber = null;

        //START get last quality number for this project
        Set qualityNums = p.getQualities();
        ArrayList qualities = new ArrayList();
        for (Iterator iter = qualityNums.iterator(); iter.hasNext();) {
            Quality q = (Quality) iter.next();
            qualities.add(q.getNumber());
        }

        //sort by quality number
        Collections.sort(qualities, CompareQualityNumber.getInstance());

        String lastQualityNumber;
        if (qualities.size() > 0) { //this project has quality nums already
            lastQualityNumber = (String) qualities.listIterator().next(); //last quality number
        } else {
            lastQualityNumber = "000";
        }
        newQualityNumber = lastQualityNumber; //e.g., 000        
        //END get last quality number for this project

        //build new quality number
        int old = Integer.valueOf(lastQualityNumber.substring(0, 3)).intValue();  //e.g., 0
        old = old + 1; //e.g., 1
        String sIter = String.valueOf(new Integer(old)); //e.g., 1
        char[] oldChar = sIter.toCharArray(); //e.g., 1
        char[] newChar = lastQualityNumber.toCharArray(); //e.g., 000

        //copy from right to left the new quality number
        for (int i = 2, j = sIter.length() - 1; i > (2 - sIter.length()); i--, j--) {
            newChar[i] = oldChar[j];
        }

        newQualityNumber = String.valueOf(newChar); //covert new quality number to string
        return newQualityNumber;
    }

    //return the new Change Number
    public String getNewChangeNumber(Project p) {
        String newChangeNumber = null;

        //START get last change number for this project
        Set changeNums = p.getChange1s();
        ArrayList changes = new ArrayList();
        for (Iterator iter = changeNums.iterator(); iter.hasNext();) {
            Change1 c = (Change1) iter.next();
            changes.add(c.getNumber());
        }

        //sort by change number
        Collections.sort(changes, CompareChangeNumber.getInstance());

        String lastChangeNumber;
        if (changes.size() > 0) { //this project has change nums already
            lastChangeNumber = (String) changes.listIterator().next(); //last change number
        } else {
            lastChangeNumber = "000";
        }
        newChangeNumber = lastChangeNumber; //e.g., 000        
        //END get last change number for this project

        //build new quality number
        int old = Integer.valueOf(lastChangeNumber.substring(0, 3)).intValue();  //e.g., 0
        old = old + 1; //e.g., 1
        String sIter = String.valueOf(new Integer(old)); //e.g., 1
        char[] oldChar = sIter.toCharArray(); //e.g., 1
        char[] newChar = lastChangeNumber.toCharArray(); //e.g., 000

        //copy from right to left the new change number
        for (int i = 2, j = sIter.length() - 1; i > (2 - sIter.length()); i--, j--) {
            newChar[i] = oldChar[j];
        }

        newChangeNumber = String.valueOf(newChar); //covert new change number to string
        return newChangeNumber;
    }

    //return the new Invoice Number
    public String getNewInvoiceNumber(Project p) {
        String newChangeNumber = null;

        //START get last change number for this project
        Set changeNums = p.getClientInvoices();
        ArrayList changes = new ArrayList();
        for (Iterator iter = changeNums.iterator(); iter.hasNext();) {
            ClientInvoice c = (ClientInvoice) iter.next();
            changes.add(c.getNumber());
        }

        //sort by change number
        Collections.sort(changes, CompareChangeNumber.getInstance());

        String lastChangeNumber;
        if (changes.size() > 0) { //this project has change nums already
            lastChangeNumber = (String) changes.listIterator().next(); //last change number
        } else {
            lastChangeNumber = "000";
        }
        newChangeNumber = lastChangeNumber; //e.g., 000        
        //END get last change number for this project

        //build new quality number
        int old = Integer.valueOf(lastChangeNumber.substring(0, 3)).intValue();  //e.g., 0
        old = old + 1; //e.g., 1
        String sIter = String.valueOf(new Integer(old)); //e.g., 1
        char[] oldChar = sIter.toCharArray(); //e.g., 1
        char[] newChar = lastChangeNumber.toCharArray(); //e.g., 000

        //copy from right to left the new change number
        for (int i = 2, j = sIter.length() - 1; i > (2 - sIter.length()); i--, j--) {
            newChar[i] = oldChar[j];
        }

        newChangeNumber = String.valueOf(newChar); //covert new change number to string
        return newChangeNumber;
    }

    //build the link between project and contact
    public void linkProjectClientContact(Project p, ClientContact cc) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            //link project and client contact
            p.setContact(cc);
            cc.getProjects().add(p);

            session.saveOrUpdate(p);

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

    //return one sourceDoc with id given in argument
    public SourceDoc getSingleSourceDoc(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from SourceDoc as sourceDoc where sourceDoc.id = ?",
                    new Object[]{id},
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (SourceDoc) results.get(0);
        }

    }
    //return one sourceDoc with id given in argument

    public SourceDoc getSingleSourceDoc(Integer pid, String lang) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from SourceDoc as sourceDoc where sourceDoc.Project = ? and sourceDoc.language = ?",
                    new Object[]{pid, lang},
                    new Type[]{Hibernate.INTEGER, Hibernate.STRING});


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
            return (SourceDoc) results.get(0);
        }

    }

    //return one targetDoc with id given in argument
    public TargetDoc getSingleTargetDoc(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from TargetDoc as targetDoc where targetDoc.id = ?",
                    new Object[]{id},
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (TargetDoc) results.get(0);
        }

    }
    //return one targetDoc with id given in argument

    public TargetDoc getSingleTargetDoc(String lang, Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from TargetDoc as targetDoc where targetDoc.language = ? and targetDoc.SourceDoc = ? ",
                    new Object[]{lang, id},
                    new Type[]{Hibernate.STRING, Hibernate.INTEGER});


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
            return (TargetDoc) results.get(0);
        }

    }

    //build the link between source and target
    public Integer linkSourceDocTargetDoc(SourceDoc sd, TargetDoc td) {
        Session session = ConnectionFactory.getInstance().getSession();
        Integer id = null;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            //link sourceDoc and targetDoc
            td.setSourceDoc(sd);
            sd.getTargetDocs().add(td);

            session.saveOrUpdate(td);

            tx.commit();

            id = td.getTargetDocId();
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

    //build the link between target Doc and linTask
    public Integer linkTargetDocLinTask(TargetDoc td, LinTask lt) {
        Session session = ConnectionFactory.getInstance().getSession();
        Integer id = null;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            //link targetDoc and linTask
            lt.setTargetDoc(td);
            td.getLinTasks().add(lt);

            session.saveOrUpdate(lt);

            tx.commit();
            return lt.getLinTaskId();
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

    //build the link between target Doc and engTask
    public Integer linkTargetDocEngTask(TargetDoc td, EngTask et) {
        Session session = ConnectionFactory.getInstance().getSession();
        Integer id = null;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            //link targetDoc and engTask
            et.setTargetDoc(td);
            td.getEngTasks().add(et);

            session.saveOrUpdate(et);

            tx.commit();
            return et.getEngTaskId();
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

    //build the link between target Doc and dtpTask
    public Integer linkTargetDocDtpTask(TargetDoc td, DtpTask dt) {
        Session session = ConnectionFactory.getInstance().getSession();
        Integer id = null;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            //link targetDoc and dtpTask
            dt.setTargetDoc(td);
            td.getDtpTasks().add(dt);

            session.saveOrUpdate(dt);

            tx.commit();
            return dt.getDtpTaskId();
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

    //build the link between target Doc and othTask
    public Integer linkTargetDocOthTask(TargetDoc td, OthTask ot) {
        Session session = ConnectionFactory.getInstance().getSession();
        Integer id = null;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            //link targetDoc and othTask
            ot.setTargetDoc(td);
            td.getEngTasks().add(ot);

            session.saveOrUpdate(ot);

            tx.commit();
            return ot.getOthTaskId();
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

    public String[] getLinTaskOptionsNew() {
        // String linTaskOptions[] = {"Alignment", "Glossary Creation", "Translation", "Editing", "ICR ", "ICR", "Implementation", "Proofreading ", "Back Translation", "Evaluation", "Other"};
        int n = 4;
        List linlist = HrService.getInstance().getDropdownList("Linguistic");
        String[] linTaskOptions = new String[n + linlist.size()];
        linTaskOptions[0] = "Translation";
        linTaskOptions[1] = "Editing";
        linTaskOptions[2] = "ICR ";
        linTaskOptions[3] = "Proofreading ";


        //     linTaskOptions[0] = "Full linguistic Service";
        //    linTaskOptions[1] =  "Translation(No Proofreading)";
        //     linTaskOptions[2] =  "ICR";
        // linTaskOptions[3] =  "";

        //List linlist = HrService.getInstance().getDropdownList("Linguistic");
        // for(int i=0;i<linlist.size()+4;i++)
        // System.out.println("LinTask"+i+"   "+linTaskOptions[i] );


        for (int i = 4; i < linlist.size() + 4; i++) {
            Dropdown ur = (Dropdown) linlist.get(i - 4);
            // Os or = (Os)oslist.get(i);
            //  if (i != linlist.size() - 1) {
            //    linArray += ",";
            // }
            linTaskOptions[i] = ur.getDropdownValue();
            System.out.println("LinTask" + i + "  " + linTaskOptions[i]);
        }

        // for(int i=0;i<linlist.size()+4;i++)
        //  System.out.println("LinTask" +linTaskOptions[i] );
        return linTaskOptions;
    }

    public String[] getEngTaskOptionsNew() {
        // String linTaskOptions[] = {"Alignment", "Glossary Creation", "Translation", "Editing", "ICR ", "ICR", "Implementation", "Proofreading ", "Back Translation", "Evaluation", "Other"};
        int n = 3;
        List linlist = HrService.getInstance().getDropdownList("Engineering");
        String[] linTaskOptions = new String[n + linlist.size()];
        linTaskOptions[0] = "TM Management";
        linTaskOptions[1] = "Compilation";
        linTaskOptions[2] = "QA";

        //List linlist = HrService.getInstance().getDropdownList("Linguistic");
        //for(int i=0;i<linlist.size()+3;i++)
        // System.out.println("LinTask"+i+"   "+linTaskOptions[i] );


        for (int i = 3; i < linlist.size() + 3; i++) {
            Dropdown ur = (Dropdown) linlist.get(i - 3);
            // Os or = (Os)oslist.get(i);
            //  if (i != linlist.size() - 1) {
            //    linArray += ",";
            // }
            linTaskOptions[i] = ur.getDropdownValue();
            System.out.println("LinTask" + i + "  " + linTaskOptions[i]);
        }

        //for(int i=0;i<linlist.size()+3;i++)
        //System.out.println("LinTask" +linTaskOptions[i] );
        return linTaskOptions;
    }

    public String[] getDtpTaskOptionsNew() {
        // String linTaskOptions[] = {"Alignment", "Glossary Creation", "Translation", "Editing", "ICR ", "ICR", "Implementation", "Proofreading ", "Back Translation", "Evaluation", "Other"};
        int n = 2;
        List linlist = HrService.getInstance().getDropdownList("Format");
        String[] linTaskOptions = new String[n + linlist.size()];
        linTaskOptions[0] = "DTP";
        linTaskOptions[1] = "Graphics";

        //List linlist = HrService.getInstance().getDropdownList("Linguistic");
        //for(int i=0;i<linlist.size()+2;i++)
        // System.out.println("LinTask"+i+"   "+linTaskOptions[i] );


        for (int i = 2; i < linlist.size() + 2; i++) {
            Dropdown ur = (Dropdown) linlist.get(i - 2);
            // Os or = (Os)oslist.get(i);
            //  if (i != linlist.size() - 1) {
            //    linArray += ",";
            // }
            linTaskOptions[i] = ur.getDropdownValue();
            System.out.println("LinTask" + i + "  " + linTaskOptions[i]);
        }

        // for(int i=0;i<linlist.size()+2;i++)
        // System.out.println("LinTask" +linTaskOptions[i] );
        return linTaskOptions;
    }

    public String[] getOtherTaskOptionsNew() {
        // String linTaskOptions[] = {"Alignment", "Glossary Creation", "Translation", "Editing", "ICR ", "ICR", "Implementation", "Proofreading ", "Back Translation", "Evaluation", "Other"};
        int n = 2;
        List linlist = HrService.getInstance().getDropdownList("Other");
        String[] linTaskOptions = new String[n + linlist.size()];
        linTaskOptions[0] = "Interpretation  ";
        linTaskOptions[1] = "Project Management";

        //List linlist = HrService.getInstance().getDropdownList("Linguistic");
        //    for(int i=0;i<linlist.size()+1;i++)
        //  System.out.println("LinTask"+i+"   "+linTaskOptions[i] );


        for (int i = n; i < linlist.size() + n; i++) {
            Dropdown ur = (Dropdown) linlist.get(i - 2);
            // Os or = (Os)oslist.get(i);
            //  if (i != linlist.size() - 1) {
            //    linArray += ",";
            // }
            linTaskOptions[i] = ur.getDropdownValue();
            System.out.println("LinTask" + i + "  " + linTaskOptions[i]);
        }

        //for(int i=0;i<linlist.size()+n;i++)
        // System.out.println("LinTask" +linTaskOptions[i] );
        return linTaskOptions;
    }

    //provide a list of lin Tasks for the quote
    public String[] getLinTaskOptions() {
        // String linTaskOptions[] = {"Alignment", "Glossary Creation", "Translation", "Editing", "ICR ", "ICR", "Implementation", "Proofreading ", "Back Translation", "Evaluation", "Other"};

        String linTaskOptions[] = {"Alignment", "Glossary Creation", "Translation", "Editing", "Recruitment of ICR Board", "In-Country / Client Review", "Implementation", "Proofreading / Linguistic QA", "Back Translation", "Evaluation", "Other"};

        return linTaskOptions;
    }

    public String[] getLinTaskOptions1() {
        String linTaskOptions1[] = {"Translation", "Editing", "ICR ", "Proofreading "};
        //       String linTaskOptions[] = {"Alignment", "Glossary Creation", "Translation", "Editing", "Recruitment of ICR Board", "In-Country / Client Review", "Implementation", "Proofreading / Linguistic QA", "Back Translation", "Evaluation", "Other"};

        return linTaskOptions1;
    }

    public String[] getLinTaskOptions2() {
        String linTaskOptions2[] = {"Translation", "Editing", "ICR "};
        //       String linTaskOptions[] = {"Alignment", "Glossary Creation", "Translation", "Editing", "Recruitment of ICR Board", "In-Country / Client Review", "Implementation", "Proofreading / Linguistic QA", "Back Translation", "Evaluation", "Other"};

        return linTaskOptions2;
    }
    //

    //provide a list of eng Tasks for the quote
    public String[] getEngTaskOptions() {
        //  String engTaskOptions[] = {"TM Management", "QA", "Preparation / Analysis / Verification", "Testing / Troubleshooting", "Functionality Testing", "Compilation", "Final QA", "Other"};
        String engTaskOptions[] = {"TM Management/Processing", "In-Process QA", "Preparation / Analysis / Verification", "Testing / Troubleshooting", "Functionality Testing", "Compilation", "Final QA", "Other"};

        return engTaskOptions;
    }

    public String[] getEngTaskOptions1() {
        String engTaskOptions[] = {"TM Management", "Compilation", "QA"};
        //   String engTaskOptions[] = {"TM Management/Processing", "In-Process QA", "Preparation / Analysis / Verification", "Testing / Troubleshooting", "Functionality Testing", "Compilation", "Final QA", "Other"};

        return engTaskOptions;
    }

    //provide a list of dtp Tasks for the quote
    public String[] getDtpTaskOptions() {
        //  String dtpTaskOptions[] = {"DTP", "Graphics ", "Special Output", "Multilingual Deliverable", "Generation of Graphics Files", "Compilation", "Other"};
        String dtpTaskOptions[] = {"Desktop Publishing", "Graphics Localization", "Special Output", "Multilingual Deliverable", "Generation of Graphics Files", "Compilation", "Other"};

        return dtpTaskOptions;
    }

    public String[] getDtpTaskOptions1() {
        String dtpTaskOptions[] = {"DTP", "Graphics "};
        //            String dtpTaskOptions[] = {"Desktop Publishing", "Graphics Localization", "Special Output", "Multilingual Deliverable", "Generation of Graphics Files", "Compilation", "Other"};

        return dtpTaskOptions;
    }

    public String[] getDtpTaskOptions2() {
        String dtpTaskOptions[] = {"Desktop Publishing (DTP)", "Graphics Editing"};
        //            String dtpTaskOptions[] = {"Desktop Publishing", "Graphics Localization", "Special Output", "Multilingual Deliverable", "Generation of Graphics Files", "Compilation", "Other"};

        return dtpTaskOptions;
    }

    public String[] getOtherTaskOptions() {
        String dtpTaskOptions[] = {"Interpretation", "Project Management"};
        //            String dtpTaskOptions[] = {"Desktop Publishing", "Graphics Localization", "Special Output", "Multilingual Deliverable", "Generation of Graphics Files", "Compilation", "Other"};

        return dtpTaskOptions;
    }

    //provide a list of Pm Tasks for the project
    public String[] getPmTaskOptions() {
        String pmTaskOptions[] = {"Preliminary Analysis", "Verification of Integrity", "QA", "Preparation", "Format Proofing", "Final QA", "Delivery", "Close-out", "Post-Mortem"};

        return pmTaskOptions;
    }

    //provide a list of default Inspections for the project
    public String[] getDefaultInspectionOptions() {
        String defaultInspectionOptions[] = {"Preliminary Analysis", "Verification of Integrity of Submitted Materials", "Preparation and Start-Up", "Translation", "Editing", "Delivery", "Close-out"};

        return defaultInspectionOptions;
    }

    //provide a list of all Inspections for the project
    public String[] getInspectionOptions() {
        String inspectionOptions[] = {"Preliminary Analysis", "Verification of Integrity of Submitted Materials", "Preparation and Start-Up", "Q&A and Initial Troubleshooting", "Compilation of Glossaries", "Compilation of Style Sheets", "Translation", "Editing", "Proofreading", "Desktop Publishing", "Format Proofing", "Linguistic Proofreading", "Functionality Testing", "Engineering testing, QA, & Troubleshooting", "In-country Client Review", "Evaluation of Client Review", "Implementation of Client Changes", "Proofreading of Client Changes", "Final Linguistic QA", "Final Engineering QA", "Delivery", "Close-out"};

        return inspectionOptions;
    }

    //delete one inspection
    public void deleteQuote1(Quote1 object) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(object);
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

    //delete one inspection
    public void deleteClientInvoice(ClientInvoice object) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(object);
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

    public String generateRandomPassword(int pwdLength) {
        char[] pw = new char[pwdLength];
        int c = 'A';
        int r1 = 0;
        for (int i = 0; i < pwdLength; i++) {
            r1 = (int) (Math.random() * 3);
            switch (r1) {
                case 0:
                    c = '0' + (int) (Math.random() * 10);
                    break;
                case 1:
                    c = 'a' + (int) (Math.random() * 26);
                    break;
                case 2:
                    c = 'A' + (int) (Math.random() * 26);
                    break;
            }
            pw[i] = (char) c;
        }
        return new String(pw);
    }

    //add inspections to this project to the db
    public AdditionalDeliveryDate addAdditionalDeliveryDate(AdditionalDeliveryDate c) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.save(c);
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
        return c;
    }

    //delete one inspection
    public void deleteAdditionalDeliveryDate(AdditionalDeliveryDate object) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(object);
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

    //delete all unavails if past date
    public void deleteAllAdditionalDates(Integer projectId) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from AdditionalDeliveryDate as addds where addds.ID_Project = ?",
                    new Object[]{projectId},
                    new Type[]{Hibernate.INTEGER});

            if (!results.isEmpty()) {
                for (Iterator unavails = results.iterator(); unavails.hasNext();) {
                    AdditionalDeliveryDate tempUnavail = (AdditionalDeliveryDate) unavails.next();
                    deleteAdditionalDeliveryDate(tempUnavail);
                }
            }

        } catch (ObjectNotFoundException onfe) {
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

    //delete all unavails if past date
    public List getAllAdditionalDates(Integer projectId) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;

        Query query;

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            query = session.createQuery("select adds from app.project.AdditionalDeliveryDate adds where adds.ID_Project=" + projectId + " order by adds.delivery_date ASC");
            return query.list();


            //System.out.println("results===="+results.size());

        } catch (ObjectNotFoundException onfe) {
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

    //get all active projects
    public List getProjectListActiveDesc() {
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
            query = session.createQuery("select project from app.project.Project project where project.status = 'active' and project.number > 0 order by project.number desc ");
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

    public List getProjectListDue(Integer pm) {
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
        //   sout

        System.out.println("Datrrrrrrrrrrrrrrrrr" + nd);

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            //  query = session.createQuery("select quote from app.quote.Quote1 quote where quote.status = 'pending' and DATEDIFF(" + date+",quote.quoteDate) > 120 order by quote.number ");
            query = session.createQuery("select project from app.project.Project project where project.status='active' and project.pm_id=" + pm + " and DATEDIFF('" + nd + "',project.dueDate)<3  order by project.number desc ");
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

    public List getProjectListByStartDate(String startDate, String endDate) {
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
        //   sout

        System.out.println("Datrrrrrrrrrrrrrrrrr" + nd);

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            //  query = session.createQuery("select quote from app.quote.Quote1 quote where quote.status = 'pending' and DATEDIFF(" + date+",quote.quoteDate) > 120 order by quote.number ");
            query = session.createQuery("select project from app.project.Project project where project.startDate between '" + startDate + "' AND '" + endDate + "' AND (project.status='active' OR project.status='complete')  order by project.number ");
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

    public List getLinTask(Integer id) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        //  int t = q.getQuote1Id();

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            query = session.createQuery("select DISTINCT lintask from app.project.LinTask lintask where lintask.TargetDoc=" + id + " order by lintask.taskName asc");
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

    public List getEnggTask(Integer id) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        //  int t = q.getQuote1Id();

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            query = session.createQuery("select DISTINCT engtask from app.project.EngTask engtask where engtask.TargetDoc=" + id + " order by engtask.taskName asc");
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

    public List getFormatTask(Integer id) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        //  int t = q.getQuote1Id();

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            query = session.createQuery("select DISTINCT dtptask from app.project.DtpTask dtptask where dtptask.TargetDoc=" + id + " order by dtptask.taskName asc");
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

    public List getOtherTask(Integer id) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        //  int t = q.getQuote1Id();

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            query = session.createQuery("select othtask from app.project.OthTask othtask where othtask.TargetDoc=" + id + " order by othtask.taskName asc");
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

    public Client_Icr getSingleClientIcr(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Client_Icr as Client_Icr where Client_Icr.ID_TargetDoc = ?",
                    new Object[]{id},
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (Client_Icr) results.get(0);
        }

    }

    public Client_Icr updateClientIcr(Client_Icr c) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(c);
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
        return c;
    }

    public Double getScoreOSA(int resourceId, String task, int projectid) {

        Project p = getSingleProject(projectid);
        Double score = 0.00;
        String specialty = "";
        String[] specialtyList = p.getTypeOfText().split(",");
        Session session = ConnectionFactory.getInstance().getSession();


        try {
            for (int i = 0; i < specialtyList.length; i++) {

                if (specialtyList[i].contains("Medical")) {
                    specialty = "Medical";
                }
                if (specialtyList[i].contains("Technical")) {
                    specialty = "Technical";
                }
                if (specialtyList[i].contains("Software")) {
                    specialty = "Software";
                }
                if (specialtyList[i].contains("Legal")) {
                    specialty = "Legal/Financial";
                }
                PreparedStatement pstmt = session.connection().prepareStatement(
                        "select sum(totalScore)/sum(numOfScores) as ISAME from "
                        + " (select count(*) as numOfScores,sum(score) as totalScore from "
                        + " rateScoreLanguage rsl, languagepair lp "
                        + " where lp.id_resource=? and lp.id_languagepair=rsl.id_languagepair "
                        + " and score<>0 and specialty=?) AS T2 ");

                pstmt.setString(1, "" + resourceId);
                pstmt.setString(2, specialty);
                ResultSet rs = pstmt.executeQuery();
                score += rs.getDouble("ISAME");
                pstmt.close();

            }
       } catch  (HibernateException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
           // Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }
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
  
    return (score/specialtyList.length);
    }

      public List getQualityReportPerYear(String yr) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        //  int t = q.getQuote1Id();

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            query = session.createQuery("select qr from app.project.QualityReport qr where qr.qualityyear=" + yr + " order by qr.qualityno asc");
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

       public QualityReport getQualityReport(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from QualityReport as QualityReport where QualityReport.qualityno = ?",
                    new Object[]{id},
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (QualityReport) results.get(0);
        }

    }

           // getSourceLang(newQ,ur.getProduct_ID())

 public List getLinTaskListforProject(int projectId) {

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
         query = session.createQuery("SELECT DISTINCT lintask.taskName " +
 " FROM SourceDoc sourcedoc, TargetDoc targetdoc, Project project, LinTask lintask "+
"  WHERE sourcedoc.sourceDocId = targetdoc.SourceDoc AND targetdoc.targetDocId = lintask.TargetDoc "+
 " AND sourcedoc.Project = project.projectId AND project.projectId="+projectId);

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

  public List getDTPTaskListforProject(int projectId) {

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
         query = session.createQuery("SELECT DISTINCT dtptask.taskName " +
 " FROM SourceDoc sourcedoc, TargetDoc targetdoc, Project project, DtpTask dtptask "+
"  WHERE sourcedoc.sourceDocId = targetdoc.SourceDoc AND targetdoc.targetDocId = dtptask.TargetDoc "+
 " AND sourcedoc.Project = project.projectId AND project.projectId="+projectId);

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

   public List getEngTaskListforProject(int projectId) {

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
         query = session.createQuery("SELECT DISTINCT engtask.taskName " +
 " FROM SourceDoc sourcedoc, TargetDoc targetdoc, Project project, EngTask engtask "+
"  WHERE sourcedoc.sourceDocId = targetdoc.SourceDoc AND targetdoc.targetDocId = engtask.TargetDoc "+
 " AND sourcedoc.Project = project.projectId AND project.projectId="+projectId);

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

    public List getOthTaskListforProject(int projectId) {

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
         query = session.createQuery("SELECT DISTINCT othtask.taskName " +
 " FROM SourceDoc sourcedoc, TargetDoc targetdoc, Project project, OthTask othtask "+
"  WHERE sourcedoc.sourceDocId = targetdoc.SourceDoc AND targetdoc.targetDocId = othtask.TargetDoc "+
 " AND sourcedoc.Project = project.projectId AND project.projectId="+projectId);

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

    public List getProjectTechnicalList(int projectid) {
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
            query = session.createQuery("select technical from app.project.Project_Technical technical where projectid =" + projectid);
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




  public Project_Technical getSingleTechnical(Integer technicalId) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Project_Technical as technical where technical.id = ?",
                    new Object[]{technicalId},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                Project_Technical q = (Project_Technical) results.get(0);
                // Hibernate.initialize(q.getSourceDocs());
                // Hibernate.initialize(q.getFiles());

                return q;
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


}
