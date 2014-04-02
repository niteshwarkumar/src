//QuoteService contains the db code related to quotes to actually read/write
//to/from db 
package app.quote;

import app.admin.PmFee;
import net.sf.hibernate.*;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.*;
import net.sf.hibernate.Hibernate;
import app.db.*;
import app.client.*;
import app.extjs.vo.Product;
import app.project.*;
import net.sf.hibernate.Criteria;
import net.sf.hibernate.expression.*;
import java.util.*;
import app.extjs.vo.Upload_Doc;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class QuoteService {

    private static QuoteService instance = null;

    public QuoteService() {
    }
    //User u=UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
    //return the instance of ClientService

    public static synchronized QuoteService getInstance() {
        /*
         * Creates the Singleton instance, if needed.
         *
         */
        if (instance == null) {
            instance = new QuoteService();
        }
        return instance;
    }


     public static boolean unlinkTechnical(int id) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from technical where clientQuote_Id = "+id);

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


    //save or Update Technical for a New Quote
    public Technical saveTechnical(Technical tech){

 Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(tech);
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
        return tech;
    }

    //add a new client to the database
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

    public Client_Quote saveClientQuote(Client_Quote p) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            System.out.println(p.getId());
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
    public Client_Quote saveClientQuote1(Client_Quote p) {

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


    public Upload_Doc addUpload_Doc(Upload_Doc p) {

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

    //get all pending quotes
    public List getQuoteListPending() {
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
            query = session.createQuery("select quote from app.quote.Quote1 quote where quote.status = 'pending' order by quote.number");
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

      //get all pending quotes
    public List getQuoteListApproved() {
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
            query = session.createQuery("select quote from app.quote.Quote1 quote where quote.status = 'approved' order by quote.number");
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


    //get all pending quotes
    public List getQuoteListPendingByDate(String pm) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        
         DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
         Date date=new Date();
         String nd=dateFormat.format(date);
      //   sout

        System.out.println("Datrrrrrrrrrrrrrrrrr"+nd);

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
          //  query = session.createQuery("select quote from app.quote.Quote1 quote where quote.status = 'pending' and DATEDIFF(" + date+",quote.quoteDate) > 120 order by quote.number ");
            query = session.createQuery("select quote from app.quote.Quote1 quote where quote.status = 'pending' and quote.lastModifiedById='"+pm+"' and DATEDIFF('"+nd+"',quote.quoteDate)>120 order by quote.number DESC");
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

    //update an existing quote in database
    public Quote1 updateQuote(Quote1 q) {
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
        return q;
    }

    public Client_Quote updateClientQuote(Client_Quote q) {
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
        return q;
    }

    //update an existing file (quote file) in database
    public File updateFile(File f) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(f);

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
        return f;
    }

    //delete one file (quote file)
    public void deleteFile(File f) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(f);
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

     //delete one file (quote upload file)
    public void deleteUploadFile(Upload_Doc f) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(f);
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


    //delete one quote
    public void deleteQuote(Quote1 object) {

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
     public void deleteClient_Quote(Client_Quote object) {

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


        //get all quotes


 public List getQuoteList1() {
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
            query = session.createQuery("select quote1.number from app.quote.Quote1 quote1 order by quote1.number");
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



 public List getQuoteList2() {
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
            query = session.createQuery("select quote1.quote1Id from app.quote.Quote1 quote1 order by quote1.number");
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

    //get all quotes
    public List getQuoteList() {
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
            query = session.createQuery("select quote1 from app.quote.Quote1 quote1 order by quote1.number");
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

    public List getSourceLang(Quote1 q, int id) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        int t = q.getQuote1Id();

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            query = session.createQuery("select sourcedoc from app.project.SourceDoc sourcedoc where sourcedoc.Quote=" + t + " and sourcedoc.Client_Quote=" + id);
            System.out.println("Queryyyyyyyyyyyyyyyyyyyyy" + query.list());
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



     public List getSourceLang1(Quote1 q) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        int t = q.getQuote1Id();

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            query = session.createQuery("select sourcedoc from app.project.SourceDoc sourcedoc where sourcedoc.Quote=" + t );
            System.out.println("Queryyyyyyyyyyyyyyyyyyyyy" + query.list());
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

    public List getTargetLang(Integer id) {
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
            query = session.createQuery("select targetdoc from app.project.TargetDoc targetdoc where targetdoc.SourceDoc=" + id);
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
            query = session.createQuery("select DISTINCT lintask from app.project.LinTask lintask where lintask.TargetDoc=" + id +" order by lintask.taskName asc");
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
            query = session.createQuery("select DISTINCT engtask from app.project.EngTask engtask where engtask.TargetDoc=" + id+" order by engtask.taskName asc");
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
            query = session.createQuery("select DISTINCT dtptask from app.project.DtpTask dtptask where dtptask.TargetDoc=" + id+" order by dtptask.taskName asc");
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
            query = session.createQuery("select othtask from app.project.OthTask othtask where othtask.TargetDoc=" + id+" order by othtask.taskName asc");
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


    public List getClient_QuoteList() {
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
            query = session.createQuery("select client_quote from app.quote.Client_Quote client_quote order by client_quote.id");
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

    public List getClient_Quote(int Cquote) {
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
            query = session.createQuery("from app.quote.Client_Quote client_quote where client_quote.Quote_ID=" + Cquote);
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

    public List getClientQuoteList() {
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
            query = session.createQuery("Select * from Quote1 q inner join Project p on p.ID_Project=q.ID_Project  inner join Client_Information c on c.ID_Client=p.ID_Client inner join User u on  u.ID_Client=c.ID_Client where u.ID_Client=101");
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

    //Integer clientID,
    //search for quotes given the criteria
    public List getClientQuoteSearch(String status, Integer clientID, Project project, String productDescription, String quoteNum, Date startQuoteDate, Date endQuoteDate, Double startQuoteTotal, Double endQuoteTotal) {
//                    QuoteService.getInstance().getClientQuoteSearch(status,id, project,productDescription,quoteNum, startQuoteDateDate, endQuoteDateDate, startQuoteTotalDouble, endQuoteTotalDouble);
        String product = project.getProduct();
        System.out.println("product>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + product);
        String productDescription12 = project.getProductDescription();

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        //Integer clientID=100;
        List results = null;
        //System.out.println("alexxxx:quoteNum="+quoteNum);

        try {
            //retreive quotes from database

            //the main criteria
            Criteria criteria = session.createCriteria(Quote1.class);
            Criteria subCriteria = criteria.createCriteria("Project");
            Criteria subCriteria2 = subCriteria.createCriteria("Company");
            //  criteria.addOrder(Order.desc("quoteNum"));
            // criteria.addOrder(Order.desc("number"));
            subCriteria2.add(Expression.eq("clientId", clientID));


            //if subcriteria values are present from the form,
            //then include subcriteria search values in main search




            if (product.length() >= 1) {
                //  Criteria subCriteria = criteria.createCriteria("Project");

                subCriteria.add(Expression.like("product", "%" + product + "%").ignoreCase());
                // Criteria subCriteria2 = subCriteria.createCriteria("Company");
                // subCriteria2.add(Expression.eq("clientId", clientID  ));

            }

            if (productDescription12.length() >= 1) {
                // Criteria subCriteria = criteria.createCriteria("Project");
                //Criteria subCriteria2 = subCriteria.createCriteria("Product");
                subCriteria2.add(Expression.like("product", "%" + productDescription12 + "%").ignoreCase());
                //         Criteria subCriteria3 = subCriteria.createCriteria("Company");
                //   subCriteria3.add(Expression.eq("clientId", clientID  ));
            }

            //add search on status if status from form does not equal "All"
            if (!status.equals("All")) {
                criteria.add(Expression.eq("status", status));
                System.out.println("Status==============>" + status);
                //Criteria subCriteria2 = subCriteria.createCriteria("Company");
                //    subCriteria2.add(Expression.eq("clientId", clientID  ));
            }
            System.out.println("status of quote>>>>>>>>>>>>>>>>>>>>>" + status);





            if (quoteNum.length() > 0) { //if quoteNum is desired
                quoteNum = quoteNum.replaceAll("[a-zA-Z]", "");
                quoteNum = quoteNum.trim();
                criteria.add(Expression.like("number", "%" + quoteNum + "%").ignoreCase());
            }


            if (startQuoteDate != null) {
                criteria.add(Expression.ge("quoteDate", startQuoteDate));
            }
            if (endQuoteDate != null) {
                criteria.add(Expression.le("quoteDate", endQuoteDate));
            }

            criteria.addOrder(Order.desc("number"));
            //  criteria.addOrder(Order.desc("quoteNum"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            System.out.println("               jhnjklkljnkjkjkl        " + criteria.list());
            results = criteria.list();
            System.out.println("               jhnjklkljnkjkjkl        " + results);

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
            System.out.println("Resultsssssss      " + results);
        }
        return results;
    }

    //clent search criteria 17 feb 2010
    public List getQuoteSearch(String status, String companyName, Project project, String productDescription, String quoteNum, Date startQuoteDate, Date endQuoteDate, Double startQuoteTotal, Double endQuoteTotal) {

        String product = project.getProduct();
        //String id_client=User.getid_client();
        //String  Client_ID=User.getProduct();
        System.out.println("product>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + product);
        String productDescription12 = project.getProductDescription();

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;
        //System.out.println("alexxxx:quoteNum="+quoteNum);

        try {
            //retreive quotes from database

            //the main criteria

            Criteria criteria = session.createCriteria(Quote1.class);
 try{
            //if subcriteria values are present from the form,
            //then include subcriteria search values in main search
            if (companyName.length() >= 1) {
                Criteria subCriteria = criteria.createCriteria("Project");
                Criteria subCriteria2 = subCriteria.createCriteria("Company");
                subCriteria2.add(Expression.like("Company_name", "%" + companyName + "%").ignoreCase());
            }



            if (product.length() >= 1) {
                Criteria subCriteria = criteria.createCriteria("Project");
                subCriteria.add(Expression.like("product", "%" + product + "%").ignoreCase());
            }

            if (productDescription12.length() >= 1) {
                Criteria subCriteria = criteria.createCriteria("Project");
                Criteria subCriteria2 = subCriteria.createCriteria("Product");
                subCriteria2.add(Expression.like("product", "%" + productDescription12 + "%").ignoreCase());
            }

            //add search on status if status from form does not equal "All"
            if (!status.equals("All")) {
                criteria.add(Expression.eq("status", status));
            }
            System.out.println("status of quote>>>>>>>>>>>>>>>>>>>>>" + status);







            if (quoteNum.length() > 0) { //if quoteNum is desired
                quoteNum = quoteNum.replaceAll("[a-zA-Z]", "");
                quoteNum = quoteNum.trim();
                criteria.add(Expression.like("number", "%" + quoteNum + "%").ignoreCase());
            }

            if (startQuoteDate != null) {
                criteria.add(Expression.ge("quoteDate", startQuoteDate));
            }
            if (endQuoteDate != null) {
                criteria.add(Expression.le("quoteDate", endQuoteDate));
            }

            if (startQuoteTotal != null) {
                criteria.add(Expression.ge("quoteDollarTotal", startQuoteTotal));
            }
            if (endQuoteTotal != null) {
                criteria.add(Expression.le("quoteDollarTotal", endQuoteTotal));
            }


            criteria.addOrder(Order.desc("approvalDate"));
            criteria.addOrder(Order.desc("number"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
         }catch(Exception e){}
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

             System.out.println("resultsssssss"+results.toString());
            return results;
        }
    }

    //return one quote with id given in argument
    public Quote1 getSingleQuote(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Quote1 as quote1 where quote1.quote1Id = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                Quote1 q = (Quote1) results.get(0);
                try {
                    Hibernate.initialize(q.getSourceDocs());
                } catch (Exception e) {
                }
                
                Hibernate.initialize(q.getFiles());

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

    public Client_Quote getSingleClientQuoteFromProduct(Integer id, Integer pid) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {
            // query =	session.createQuery("select client_quote from app.quote.Client_Quote client_quote where client_quote.Quote_ID = "+id);

            results = session.find("from Client_Quote as client_quote where client_quote.Quote_ID = ? and client_quote.Product_ID=? ",
                    new Object[]{id, pid},
                    new Type[]{Hibernate.INTEGER, Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                Client_Quote q = (Client_Quote) results.get(0);
                //  Hibernate.initialize(q.getSourceDocs());
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

    public List getSingleClientQuote(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        // List results = null;
        Query query;
        try {

            // Query query;
            //query =	session.createQuery("select industry from app.client.Industry industry");
            // return query.list();
            //              query =	session.createQuery("select dropdown from app.extjs.vo.Dropdown dropdown where dropdown.dropdownType='"+dropdownType+"'");
            query = session.createQuery("select client_quote from app.quote.Client_Quote client_quote where client_quote.Quote_ID = " + id);
            //  System.out.println("");

            //if(results.isEmpty()){
            //       return null;
            //   }else {
            ///       Client_Quote q = (Client_Quote) results.get(0);
            //        Hibernate.initialize(q.getSourceDocs());
            //     Hibernate.initialize(q.getFiles());

            //       return q;
            //    }
            return query.list();

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

    public Client_Quote getSingleClient_Quote(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Client_Quote as client_quote where client_quote.id = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                Client_Quote q = (Client_Quote) results.get(0);
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

      public Quote1 getSingleQuoteFromProject(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Quote1 as quote where quote.Project.projectId = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                Quote1 q = (Quote1) results.get(0);
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

       public List getSubQuoteFromProject(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Quote1 as quote where quote.Project.projectId = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                Quote1 q = (Quote1) results.get(0);
                // Hibernate.initialize(q.getSourceDocs());
                // Hibernate.initialize(q.getFiles());

                return results;
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


    public Client_Quote get_SingleClientQuote(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {
//select client_quote from app.quote.Client_Quote client_quote where client_quote.Quote_ID = "+id
            results = session.find("from Client_Quote as client_quote where client_quote.Quote_ID = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                Client_Quote q = (Client_Quote) results.get(0);
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

    //return one file (quote file) with id given in argument
    public File getSingleFile(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from File as file where file.fileId = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                return (File) results.get(0);
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

        //return one file (quote file) with id given in argument
    public Upload_Doc getSingleUploadFile(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Upload_Doc as file where file.uploadDoc = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                return (Upload_Doc) results.get(0);
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

    //place a new quote and project in db
    //build relationship between client and project
    //and between project and quote
    public Integer addQuoteWithNewProject(Client c, String projectNumber) {
        //new objects created when a quote is built from scratch
        Project p = new Project(new HashSet(), new HashSet(), new HashSet(), new HashSet(), new HashSet(), new HashSet());
        Quote1 q = new Quote1(new HashSet(), new HashSet());
        Integer quoteId = new Integer(0); //to return the new quote id (int id)

        //assign new quote number
        q.setNumber(getNewQuoteNumber1());

        //quote is pending
        q.setStatus("pending");

        //set quote date as current day
        q.setQuoteDate(new Date());

        //set default pm percent
        q.setPmPercent("00.00");

        //project is not active yet (quote not approved)
        p.setStatus("notApproved");
        p.setNumber(projectNumber);

        //Copy Quote's Notes into Project notes intially
        //if(q.getNote()!=null){
        //   p.setNotes(q.getNote());
        // }


        Session session = ConnectionFactory.getInstance().getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();


            //between client and project
            c.getProjects().add(p);
            p.setCompany(c);

            //between project and quote
            p.getQuotes().add(q);
            q.setProject(p);

            //add project and quote to db
            session.save(p);
            session.save(q);

            //quote's id
            quoteId = q.getQuote1Id();

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
        return quoteId;
    }

    public Integer NaddQuoteWithNewProject(Client c, String projectNumber) {
        //new objects created when a quote is built from scratch
        Project p = new Project(new HashSet(), new HashSet(), new HashSet(), new HashSet(), new HashSet(), new HashSet());
        Client_Quote q = new Client_Quote();
        Integer quoteId = new Integer(0); //to return the new quote id (int id)

        //assign new quote number
        // q.getQuote_ID(getNewQuoteNumber());
        //  q.getQuote_ID(getNewClientQuoteNumber());
        //quote is pending
        //   q.setStatus("pending");

        //set quote date as current day
//                q.setQuoteDate(new Date());

        //set default pm percent
        //              q.setPmPercent("10.00");

        //project is not active yet (quote not approved)
        p.setStatus("notApproved");
        p.setNumber(projectNumber);

        //Copy Quote's Notes into Project notes intially
        //if(q.getNote()!=null){
        //   p.setNotes(q.getNote());
        // }


        Session session = ConnectionFactory.getInstance().getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();


            //between client and project
            c.getProjects().add(p);
            p.setCompany(c);

            //between project and quote
            //     p.getClient_Quote().add(true);
            //  q.setProject(p);

            //add project and quote to db
            session.save(p);
            //   session.save(q);

            //quote's id
            //  quoteId = q.getQuote1Id();

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
        return quoteId;
    }

    //get client id
    public Client getClient(int client_ID) {
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {
            results = session.find("from Client as cc where cc.clientId = ?",
                    new Object[]{new Integer(client_ID)},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                Client c = (Client) results.get(0);
                return c;
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

    //return the most recent quote1 object for a project
    //(it was probably approved)
    //call method as: .getLastQuote(p.getQuotes)
    //where p is the current project
    public Quote1 getLastQuote(Set quotes) {
        Iterator iterScroll = quotes.iterator();

        //quotes listed in desc order (e.g., Q000015, then Q000001)
        Quote1 resultQuote = null;
        /*while(iterScroll.hasNext()){
        Quote1 q = (Quote1) iterScroll.next();
        //System.out.println("alexxx:q.getApprovalDate()="+q.getApprovalDate());
        //System.out.println("alexxx:q.getApprovalDate().getTime()="+q.getApprovalDate().getTime());
        // System.out.println("alexxx:q.getQuote1Id()="+q.getQuote1Id());
        if(resultQuote == null){
        resultQuote = q;
        }else{
        if(resultQuote.getApprovalDate().getTime() == q.getApprovalDate().getTime()){
        if(q.getLastModifiedByTS().bef)
        resultQuote = q;
        }
        }
        }*/


        Quote1 q = (Quote1) iterScroll.next();

        return (q == null) ? null : q;
        //return resultQuote;
        }

     public String getNewQuoteNumber() {
        String newQuoteNumber = null;
        Quote1 q = null;

        //get last quote number
        ListIterator iter = null;
        List quotes = getQuoteList(); //list of current quotes sorted in order (e.g., Q000000, Q000001)
        String lastQuoteNumber = new String("");
        if (quotes != null && quotes.size() > 0) {
            for (iter = quotes.listIterator(); iter.hasNext(); iter.next()) {
            }
            iter.previous();
            q = (Quote1) iter.next();
            lastQuoteNumber = iter.toString(); //last quote number
            lastQuoteNumber = q.getNumber(); //last quote number
            } else { //for first quote
            lastQuoteNumber = "Q000000";
        }

        newQuoteNumber = lastQuoteNumber; //e.g., Q000009

        //build new quote number
        int old = Integer.valueOf(lastQuoteNumber.substring(1, 7)).intValue();  //e.g., 9
        old = old + 1; //e.g., 10
        String sIter = String.valueOf(new Integer(old)); //e.g., 10
        char[] oldChar = sIter.toCharArray(); //e.g., 10
        char[] newChar = lastQuoteNumber.toCharArray(); //e.g., Q000009

        //copy from right to left the new quote number
        for (int i = 6, j = sIter.length() - 1; i > (6 - sIter.length()); i--, j--) {
            newChar[i] = oldChar[j];
        }

        newQuoteNumber = String.valueOf(newChar); //covert new quote number to string
        return newQuoteNumber;
    }





    //return the new Quote Number
    public String getNewQuoteNumber1() {
        String newQuoteNumber = null;
        Quote1 q = null;

        //get last quote number
        ListIterator iter = null;
        List quotes = getQuoteList1(); //list of current quotes sorted in order (e.g., Q000000, Q000001)
        String lastQuoteNumber = new String("");
        if (quotes != null && quotes.size() > 0) {
            for (iter = quotes.listIterator(); iter.hasNext(); iter.next()) {
            }
            iter.previous();
          //  q = (Quote1) iter.next();
            lastQuoteNumber = iter.next().toString(); //last quote number
           // lastQuoteNumber = q.getNumber(); //last quote number
            } else { //for first quote
            lastQuoteNumber = "Q000000";
        }

        newQuoteNumber = lastQuoteNumber; //e.g., Q000009
        System.out.println("newQuoteNumbernewQuoteNumbernewQuoteNumbernewQuoteNumber"+newQuoteNumber);

        //build new quote number
        int old = Integer.valueOf(lastQuoteNumber.substring(1, 7)).intValue();  //e.g., 9
        old = old + 1; //e.g., 10
        String sIter = String.valueOf(new Integer(old)); //e.g., 10
        char[] oldChar = sIter.toCharArray(); //e.g., 10
        char[] newChar = lastQuoteNumber.toCharArray(); //e.g., Q000009

        //copy from right to left the new quote number
        for (int i = 6, j = sIter.length() - 1; i > (6 - sIter.length()); i--, j--) {
            newChar[i] = oldChar[j];
        }

        newQuoteNumber = String.valueOf(newChar); //covert new quote number to string
        return newQuoteNumber;
    }

    public Integer getNewClientQuoteNumber() {
        Integer newQuoteNumber = 0;
        Client_Quote q = null;

        //get last quote number
        ListIterator iter = null;
        List quotes = getClient_QuoteList(); //list of current quotes sorted in order (e.g., Q000000, Q000001)
        Integer lastQuoteNumber = 0;
        if (quotes != null && quotes.size() > 0) {
            for (iter = quotes.listIterator(); iter.hasNext(); iter.next()) {
            }
            iter.previous();
            q = (Client_Quote) iter.next();
            lastQuoteNumber = q.getQuote_ID(); //last quote number
            } else { //for first quote
            lastQuoteNumber = 0;
        }

        newQuoteNumber = lastQuoteNumber; //e.g., Q000009

        //build new quote number
        //int old = Integer.valueOf(lastQuoteNumber.substring(1, 7)).intValue();  //e.g., 9
        newQuoteNumber = lastQuoteNumber + 1; //e.g., 10
        // String sIter = String.valueOf(new Integer(old)); //e.g., 10
        // char[] oldChar = sIter.toCharArray(); //e.g., 10
        //char[] newChar = lastQuoteNumber.toCharArray(); //e.g., Q000009

        //copy from right to left the new quote number
        // for(int i = 6, j = sIter.length()-1; i > (6 - sIter.length()); i--, j--) {
        //      newChar[i] = oldChar[j];
        //  }

        //  newQuoteNumber = String.valueOf(newChar); //covert new quote number to string
        return newQuoteNumber;
    }

    public Integer getPresentQuoteid() {
        Integer newQuoteNumber = 0;
        Quote1 q = null;

        //get last quote number
        ListIterator iter = null;
        List quotes = getQuoteList(); //list of current quotes sorted in order (e.g., Q000000, Q000001)
        Integer lastQuoteid = 0;
        if (quotes != null && quotes.size() > 0) {
            for (iter = quotes.listIterator(); iter.hasNext(); iter.next()) {
            }
            iter.previous();
            q = (Quote1) iter.next();
            lastQuoteid = q.getQuote1Id(); //last quote number
            } else { //for first quote
            lastQuoteid = 0;
        }

        newQuoteNumber = lastQuoteid; //e.g., Q000009

        //build new quote number
        //int old = Integer.valueOf(lastQuoteNumber.substring(1, 7)).intValue();  //e.g., 9
        newQuoteNumber = lastQuoteid; //e.g., 10
        // String sIter = String.valueOf(new Integer(old)); //e.g., 10
        // char[] oldChar = sIter.toCharArray(); //e.g., 10
        //char[] newChar = lastQuoteNumber.toCharArray(); //e.g., Q000009

        //copy from right to left the new quote number
        // for(int i = 6, j = sIter.length()-1; i > (6 - sIter.length()); i--, j--) {
        //      newChar[i] = oldChar[j];
        //  }

        //  newQuoteNumber = String.valueOf(newChar); //covert new quote number to string
        return newQuoteNumber;
    }

    public Integer getNewClientQuoteid() {
        Integer newQuoteNumber = 0;
        Client_Quote q = null;

        //get last quote number
        ListIterator iter = null;
        List quotes = getClient_QuoteList(); //list of current quotes sorted in order (e.g., Q000000, Q000001)
        Integer lastQuoteid = 0;
        if (quotes != null && quotes.size() > 0) {
            for (iter = quotes.listIterator(); iter.hasNext(); iter.next()) {
            }
            iter.previous();
            q = (Client_Quote) iter.next();
            lastQuoteid = q.getId(); //last quote number
        } else { //for first quote
            lastQuoteid = 0;
        }

        newQuoteNumber = lastQuoteid; //e.g., Q000009

        //build new quote number
        //int old = Integer.valueOf(lastQuoteNumber.substring(1, 7)).intValue();  //e.g., 9
        newQuoteNumber = lastQuoteid + 1; //e.g., 10
        // String sIter = String.valueOf(new Integer(old)); //e.g., 10
        // char[] oldChar = sIter.toCharArray(); //e.g., 10
        //char[] newChar = lastQuoteNumber.toCharArray(); //e.g., Q000009

        //copy from right to left the new quote number
        // for(int i = 6, j = sIter.length()-1; i > (6 - sIter.length()); i--, j--) {
        //      newChar[i] = oldChar[j];
        //  }

        //  newQuoteNumber = String.valueOf(newChar); //covert new quote number to string
        System.out.println("id+lastQuoteid+newQuoteNumber" + lastQuoteid + "          " + newQuoteNumber);


        return newQuoteNumber;
    }

    //return the new Quote Number
    //NOTE: may need because two sessions is not a good idea
    public String getQuoteNumberInternal() {
        String newQuoteNumber = null;
        Quote1 q = null;

        //get last quote number
        ListIterator iter = null;
        List quotes = getQuoteList(); //list of current quotes sorted in order (e.g., Q000000, Q000001)
        for (iter = quotes.listIterator(); iter.hasNext(); iter.next()) {
        }
        iter.previous();
        q = (Quote1) iter.next();

        String lastQuoteNumber = q.getNumber(); //last quote number
        newQuoteNumber = lastQuoteNumber; //e.g., Q000009

        //build new quote number
        int old = Integer.valueOf(lastQuoteNumber.substring(1, 7)).intValue();  //e.g., 9
        old = old + 1; //e.g., 10
        String sIter = String.valueOf(new Integer(old)); //e.g., 10
        char[] oldChar = sIter.toCharArray(); //e.g., 10
        char[] newChar = lastQuoteNumber.toCharArray(); //e.g., Q000009

        //copy from right to left the new quote number
        for (int i = 6, j = sIter.length() - 1; i > (6 - sIter.length()); i--, j--) {
            newChar[i] = oldChar[j];
        }

        newQuoteNumber = String.valueOf(newChar); //covert new quote number to string
        return newQuoteNumber;
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

    public List getFileListByQuote(Integer QuoteId){
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
            Query query; */
            query = session.createQuery("select file from app.quote.File file where file.Quote="+QuoteId);
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


    //get all clients
    public List getIndustryList() {
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
            Query query; */
            query = session.createQuery("select industry from app.client.Industry industry");
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

    //add this source to the db, building one-to-many relationship between quote and sourceDoc
    //return new SourceDoc id
    public Integer addSourceWithQuote(Quote1 q, SourceDoc sd) {
        Session session = ConnectionFactory.getInstance().getSession();
        // Client_Quote clientQuote= get_SingleClientQuote(q.getQuote1Id());
        //Integer Client_QuoteId= clientQuote.getId();
        //get_SingleClientQuote
        Transaction tx = null;
        Integer id = null;

        try {

            tx = session.beginTransaction();

            //build relationship
            q.getSourceDocs().add(sd);
            sd.setQuote(q);
            sd.setProject(q.getProject());

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


      //add this source to the db, building one-to-many relationship between quote and sourceDoc
    //return new SourceDoc id
    public Integer addSourceWithQuote1(Quote1 q, SourceDoc sd) {
        Session session = ConnectionFactory.getInstance().getSession();
        // Client_Quote clientQuote= get_SingleClientQuote(q.getQuote1Id());
        //Integer Client_QuoteId= clientQuote.getId();
        //get_SingleClientQuote
        Transaction tx = null;
        Integer id = null;

        try {

            tx = session.beginTransaction();

            //build relationship
            q.getSourceDocs().add(sd);
            sd.setQuote(q);
           // sd.setProject(q.getProject());

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



    public Integer clientAddSourceWithQuote(Quote1 q, SourceDoc sd, Client_Quote Client_QuoteId) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;
        Integer id = null;

        try {

            tx = session.beginTransaction();

            //build relationship
            q.getSourceDocs().add(sd);
            sd.setQuote(q);
            sd.setClient_Quote(Client_QuoteId);

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

    //add this quote to the db, building one-to-many relationship between project and quote
    //return new quote
    public Integer addNewQuoteOldProject(Quote1 q, Project p) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;
        Integer id = null;

        try {

            tx = session.beginTransaction();
            // if(p.getNotes()==null || "".equals(p.getNotes())){
            //     if(q.getNote()!=null)
            //         p.setNotes(q.getNote());
            // }
            //build relationship
            q.setProject(p);
            p.getQuotes().add(q);

            session.save(q);
            tx.commit();
            id = q.getQuote1Id();
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

    //add this quote to the db
    //return new quote
    public Integer addNewQuote(Quote1 q) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;
        Integer id = null;

        try {

            tx = session.beginTransaction();


            session.save(q);
            tx.commit();
            id = q.getQuote1Id();
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

    //add this file (quote file) to the db, building one-to-many relationship between quote and file
    //return new file id
    public Integer addFileWithQuote(Quote1 q, File f) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;
        Integer id = null;

        try {

            tx = session.beginTransaction();

            //build relationship
            f.setQuote(q);
            q.getFiles().add(f);

            session.save(f);
            tx.commit();
            id = f.getFileId();
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

    //provide a list of statuses for forms (in clients)
    public String[] getStatuses() {
        String Status[] = {"Prospect", "Active", "Lost"};
        return Status;
    }

    //provide a list of lin Tasks for the quote
    public String[] getLinTaskOptions() {
        String linTaskOptions[] = {"Alignment", "Glossary Creation", "Translation", "Editing", "Recruitment of ICR Board", "In-Country / Client Review", "Implementation", "Proofreading / Linguistic QA", "Back Translation", "Evaluation", "Other"};

        return linTaskOptions;
    }

    //  public String[] getLinTaskOptions11() {
    //      String linTaskOptions[] = HrService.getInstance().getDropdownList("");
    //       return linTaskOptions;
    //   }
    //provide a list of eng Tasks for the quote
    public String[] getEngTaskOptions() {
        String engTaskOptions[] = {"TM Management/Processing", "In-Process QA", "Preparation / Analysis / Verification", "Testing / Troubleshooting", "Functionality Testing", "Compilation", "Final QA", "Other"};

        return engTaskOptions;
    }

    //provide a list of dtp Tasks for the quote
    public String[] getDtpTaskOptions() {
        String dtpTaskOptions[] = {"Desktop Publishing", "Graphics Localization", "Special Output", "Multilingual Deliverable", "Generation of Graphics Files", "Compilation", "Other"};

        return dtpTaskOptions;
    }

    public String getRejectionReasonDescription(String key) {

        if (key == null) {
            return "";
        }
        Hashtable htReasons = new Hashtable();
        htReasons.put("1", "Price Too High");
        htReasons.put("2", "Time frame too long");
        htReasons.put("3", "Technology requirements not met");
        htReasons.put("4", "Size of company");
        htReasons.put("5", "Used internal resources");
        htReasons.put("6", "Used current vendor");
        htReasons.put("7", "Used another vendor for other reasons");
        htReasons.put("8", "Project was cancelled or not approved");
        htReasons.put("9", "Scope changed and project was requoted");
        htReasons.put("10", "Never heard back from prospect");
        htReasons.put("11", "Other");
        htReasons.put("12", "For budget only");


        return (String) (htReasons.get(key));
    }

    //get all pending quotes
    public List getReportQuoteRejectionReasons() {
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
            query = session.createQuery("SELECT Quote FROM Quote1 q where q.rejectReason is not null order by q.rejectReason");

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

    public List getOsList() {
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
            query = session.createQuery("select os from app.quote.Os os order by os.os");
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

    public List getApplicationList() {
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
            query = session.createQuery("select application from app.quote.Application application order by application.application ");
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

    public List getUnitList() {
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
            query = session.createQuery("select unit from app.quote.Unit unit order by unit.unit ");
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

    public List getDeliverableList() {
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
            query = session.createQuery("select deliverable from app.quote.Deliverable deliverable order by deliverable.deliverable ");
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

    public List getComponentList() {
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
            query = session.createQuery("select component from app.quote.Client_Quote component order by component.component ");
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

    public Product getProductValues(Integer ClientId, String product) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *request.getSession(false).getAttribute("username")
         */
        //  User u=UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Product as products where products.ID_Client= ? and products.product = ? ",
                    new Object[]{ClientId, product},
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
            return (Product) results.get(0);
        }

    }

    public static boolean deleteClientQuote(Integer id) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from client_quote where id=" + id);

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

     public static boolean deleteClientQuotebyQuoteId(Integer id) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from client_quote where quote_id=" + id);

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

            System.out.println("results");
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


    // getSourceLang(newQ,ur.getProduct_ID())

 public List getTaskListforQuote(int quoteId) {

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
            query = session.createQuery("SELECT DISTINCT lintask.`taskName` AS lintask_taskName" +
 " FROM`sourcedoc` sourcedoc INNER JOIN `targetdoc` targetdoc ON sourcedoc.`ID_SourceDoc` = targetdoc.`ID_SourceDoc`"+
     " INNER JOIN `lintask` lintask ON targetdoc.`ID_TargetDoc` = lintask.`ID_TargetDoc`"+
     " INNER JOIN `quote1` quote1 ON sourcedoc.`ID_Quote1` = quote1.`ID_Quote1`"+
     " INNER JOIN `client_quote` client_quote_A ON quote1.`ID_Quote1` = client_quote_A.`Quote_ID`"+
" where quote1.`ID_Quote1`="+quoteId+
" UNION SELECT DISTINCT dtptask.`taskName` AS dtptask_taskName   "+
 " FROM `sourcedoc` sourcedoc INNER JOIN `targetdoc` targetdoc ON sourcedoc.`ID_SourceDoc` = targetdoc.`ID_SourceDoc`"+
     " INNER JOIN `dtptask` dtptask ON targetdoc.`ID_TargetDoc` = dtptask.`ID_TargetDoc`     "+
     " INNER JOIN `quote1` quote1 ON sourcedoc.`ID_Quote1` = quote1.`ID_Quote1`"+
    " INNER JOIN `client_quote` client_quote_A ON quote1.`ID_Quote1` = client_quote_A.`Quote_ID`"+
" where quote1.`ID_Quote1`="+quoteId+
" UNION SELECT DISTINCT engtask.`taskName` AS engtask_taskName   "+
 " FROM `sourcedoc` sourcedoc INNER JOIN `targetdoc` targetdoc ON sourcedoc.`ID_SourceDoc` = targetdoc.`ID_SourceDoc`"+
     " INNER JOIN `engtask` engtask ON targetdoc.`ID_TargetDoc` = engtask.`ID_TargetDoc`     "+
     " INNER JOIN `quote1` quote1 ON sourcedoc.`ID_Quote1` = quote1.`ID_Quote1`"+
     " INNER JOIN `client_quote` client_quote_A ON quote1.`ID_Quote1` = client_quote_A.`Quote_ID`"+
" where quote1.`ID_Quote1`="+quoteId+
" UNION SELECT DISTINCT othtask.`taskName` AS othtask_taskName   "+
 " FROM`sourcedoc` sourcedoc INNER JOIN `targetdoc` targetdoc ON sourcedoc.`ID_SourceDoc` = targetdoc.`ID_SourceDoc`"+
     " INNER JOIN `othtask` othtask ON targetdoc.`ID_TargetDoc` = othtask.`ID_TargetDoc`     "+
     " INNER JOIN `quote1` quote1 ON sourcedoc.`ID_Quote1` = quote1.`ID_Quote1`"+
     " INNER JOIN `client_quote` client_quote_A ON quote1.`ID_Quote1` = client_quote_A.`Quote_ID`"+
" where quote1.`ID_Quote1`="+quoteId);
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

 public List getTechnicalList(int CQuote) {
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
            query = session.createQuery("select technical from app.quote.Technical technical where ClientQuote_ID =" + CQuote);
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




  public Technical getSingleTechnical(Integer technicalId) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Technical as technical where technical.technicalId = ?",
                    new Object[]{technicalId},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                Technical q = (Technical) results.get(0);
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


   public List getPMFeeRangeList() {
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
            query = session.createQuery("select pmfee from app.admin.PmFee pmfee");
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

   public Double getPmFee(Double subTotal){

       List pmFeeRangeList=getPMFeeRangeList();
       for(int i=0;i<pmFeeRangeList.size();i++)
       {
            PmFee pm=(PmFee) pmFeeRangeList.get(i);
            if(subTotal>=pm.getPmfee1()&&subTotal<pm.getPmfee2()) {
                return pm.getPmpercent();
            }
       }
       

   return 0.0;
   }

//add a new client to the database
    public QuotePriority addQuotePriority(QuotePriority c) {

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

     public void updateQuotePriorityList() {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
//        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;
        Query query;

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("UPDATE  quotepriority qp INNER JOIN quote1 q ON qp.ID_Quote1=q.ID_Quote1 SET qp.priority=0 WHERE q.status<>'pending'");
            st.executeUpdate();
            st.close();

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

     public List getQuotePriorityList() {
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
           updateQuotePriorityList();

            query = session.createQuery("select QuotePriority from app.quote.QuotePriority QuotePriority where QuotePriority.priority > 0 order by QuotePriority.priority ");
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


      public List getQuotePriorityListBetween(int start, int end ) {
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
            query = session.createQuery("select QuotePriority from app.quote.QuotePriority QuotePriority where QuotePriority.priority > 0 and QuotePriority.priority >= " + start + " and QuotePriority.priority <= " + end + " order by QuotePriority.priority ");
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

 //return one quote with id given in argument
    public QuotePriority getSingleQuotePriority(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from QuotePriority as quote1 where quote1.ID_Quote1 = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                QuotePriority q = (QuotePriority) results.get(0);
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

    //return one quote with id given in argument
    public Integer getLastQuotePriority() {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from QuotePriority as quote1 order by quote1.priority desc");

            if (results.isEmpty()) {
                return 0;
            } else {
                QuotePriority q = (QuotePriority) results.get(0);
                        return q.getPriority();
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

