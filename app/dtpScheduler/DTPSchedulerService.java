//HrService.java contains the db code related to hr to actually read/write
//to/from db. It is different from UserService because UserService handles
//login and privileges; HrService handles hr module information like employee address
//NOTE: may not need: all user-related in UserService

package app.dtpScheduler;

import java.util.List;

import net.sf.hibernate.*;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.*;
import net.sf.hibernate.Hibernate;
import app.db.*;
import java.sql.PreparedStatement;
import java.util.Calendar;

public class DTPSchedulerService
{

	private static DTPSchedulerService instance = null;

	public DTPSchedulerService()
	{

	}
        
        //return the instance of ClientService
	public static synchronized DTPSchedulerService getInstance()
	{
		/*
		 * Creates the Singleton instance, if needed.
		 * 
		 */
		if (instance == null)
		{
			instance = new DTPSchedulerService();
		}
		return instance;
	}
        
        //get all clients
	public List getActiveDTPProjectsList()
	{
		/*
		 * Use the ConnectionFactory to retrieve an open
		 * Hibernate Session.
		 * 
		 */
		Session session = ConnectionFactory.getInstance().getSession();
                Query query;
                
		try
		{
			/*
			* Build HQL (Hibernate Query Language) query to retrieve a list
			* of all the items currently stored by Hibernate.
			 */
			query =	session.createQuery("select DTPScheduler from app.dtpScheduler.DTPScheduler DTPScheduler where status='A'  order by priority");
                        return query.list();
		}
		catch (HibernateException e)
		{
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
 //get all clients
	public List getUpcomingDTPProjectsList()
	{
		/*
		 * Use the ConnectionFactory to retrieve an open
		 * Hibernate Session.
		 * 
		 */
		Session session = ConnectionFactory.getInstance().getSession();
                Query query;
                
		try
		{
			/*
			* Build HQL (Hibernate Query Language) query to retrieve a list
			* of all the items currently stored by Hibernate.
			 */
			query =	session.createQuery("select DTPScheduler from app.dtpScheduler.DTPScheduler DTPScheduler where status='U'  order by priority");
                        return query.list();
		}
		catch (HibernateException e)
		{
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
        
       //add a new client to the database
        public void addDtpProject(DTPScheduler dtp)
	{

		Session session = ConnectionFactory.getInstance().getSession();
                
                Transaction tx = null;
                
		try
		{
                        
                        tx = session.beginTransaction();
                        
			session.save(dtp);
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
		/*
		 * Regardless of whether the above processing resulted in an Exception
		 * or proceeded normally, we want to close the Hibernate session.  When
		 * closing the session, we must allow for the possibility of a Hibernate
		 * Exception.
		 * 
		 */
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
        
         //delete scheduled project
    public void deleteDTPProject(DTPScheduler dtp)
    {

		Session session = ConnectionFactory.getInstance().getSession();
                
                Transaction tx = null;
                
		try
		{
                        
                        tx = session.beginTransaction();
                        
			session.delete(dtp);
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
		/*
		 * Regardless of whether the above processing resulted in an Exception
		 * or proceeded normally, we want to close the Hibernate session.  When
		 * closing the session, we must allow for the possibility of a Hibernate
		 * Exception.
		 * 
		 */
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
    
    //return one quote with id given in argument
        public void promoteDTPProject(int id)
	{

		/*
		 * Use the ConnectionFactory to retrieve an open
		 * Hibernate Session.
		 * 
		 */
		Session session = ConnectionFactory.getInstance().getSession();
                
		try
		{
			
                      List results  = session.find("from DTPScheduler as DTPScheduler where DTPScheduler.ID_Schedule = ?",
                                               new Object[] {new Integer(id)},
                                               new Type[] {Hibernate.INTEGER} ); 
                        
                DTPScheduler dtpProject =   (DTPScheduler)results.get(0); 
		dtpProject.setStatus("A");
                dtpProject.setEndDate("");
                dtpProject.setOperatorEndDate("");
                dtpProject.setOperatorStartDate("");
                
                
                java.util.Calendar c = java.util.Calendar.getInstance();
                c.setTime(new java.util.Date());
                dtpProject.setStartDate( (c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DATE)+"/"+c.get(Calendar.YEAR));
                Transaction tx = session.beginTransaction();
                session.saveOrUpdate(dtpProject);
		tx.commit();
                

		}
		
		catch (ObjectNotFoundException onfe)
		{
			
		}
		catch (HibernateException e)
		{
			/*
			 * All Hibernate Exceptions are transformed into an unchecked
			 * RuntimeException.  This will have the effect of causing the
			 * user's request to return an error.
			 * 
			 */
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
        
        //return one quote with id given in argument
        public DTPScheduler getSingleDTPProject(int id)
	{

		/*
		 * Use the ConnectionFactory to retrieve an open
		 * Hibernate Session.
		 * 
		 */
		Session session = ConnectionFactory.getInstance().getSession();
                
		try
		{
			
                      List results  = session.find("from DTPScheduler as DTPScheduler where DTPScheduler.ID_Schedule = ?",
                                               new Object[] {new Integer(id)},
                                               new Type[] {Hibernate.INTEGER} ); 
                        
                DTPScheduler dtpProject =   (DTPScheduler)results.get(0); 
		return dtpProject;
                

		}
		
		catch (ObjectNotFoundException onfe)
		{
			
		}
		catch (HibernateException e)
		{
			/*
			 * All Hibernate Exceptions are transformed into an unchecked
			 * RuntimeException.  This will have the effect of causing the
			 * user's request to return an error.
			 * 
			 */
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
                return null;
	}
    //add a new client to the database
        public void modifyDtpProject(DTPScheduler dtp)
	{

		Session session = ConnectionFactory.getInstance().getSession();
                
                Transaction tx = null;
                
		try
		{
                        
                        tx = session.beginTransaction();
                        
			session.saveOrUpdate(dtp);
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
		/*
		 * Regardless of whether the above processing resulted in an Exception
		 * or proceeded normally, we want to close the Hibernate session.  When
		 * closing the session, we must allow for the possibility of a Hibernate
		 * Exception.
		 * 
		 */
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
        
//get all clients
	public List getAllDTPProjectsForAUserList(String userId)
	{
		/*
		 * Use the ConnectionFactory to retrieve an open
		 * Hibernate Session.
		 * 
		 */
		Session session = ConnectionFactory.getInstance().getSession();
                Query query;
                
		try
		{
			/*
			* Build HQL (Hibernate Query Language) query to retrieve a list
			* of all the items currently stored by Hibernate.
			 */
			query =	session.createQuery("select DTPScheduler from app.dtpScheduler.DTPScheduler DTPScheduler where operatorName='"+ userId+"' order by priority");
                        return query.list();
		}
		catch (HibernateException e)
		{
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

        public List getAllENGProjectsOrQuotes(String pORq)
	{
		/*
		 * Use the ConnectionFactory to retrieve an open
		 * Hibernate Session.
		 *
		 */
		Session session = ConnectionFactory.getInstance().getSession();
                Query query;

		try
		{
			/*
			* Build HQL (Hibernate Query Language) query to retrieve a list
			* of all the items currently stored by Hibernate.
			 */
			query =	session.createQuery("select ENGScheduler from app.dtpScheduler.ENGScheduler ENGScheduler where projectOrQuote='"+ pORq+"'");
                        return query.list();
		}
		catch (HibernateException e)
		{
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

//add a new client to the database
        public void addENGProject(ENGScheduler eng)
	{

		Session session = ConnectionFactory.getInstance().getSession();

                Transaction tx = null;

		try
		{

                        tx = session.beginTransaction();

			session.save(eng);
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
		/*
		 * Regardless of whether the above processing resulted in an Exception
		 * or proceeded normally, we want to close the Hibernate session.  When
		 * closing the session, we must allow for the possibility of a Hibernate
		 * Exception.
		 *
		 */
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

         //delete scheduled project
    public void deleteENGProject(ENGScheduler eng)
    {

		Session session = ConnectionFactory.getInstance().getSession();

                Transaction tx = null;

		try
		{

                        tx = session.beginTransaction();

			session.delete(eng);
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
		/*
		 * Regardless of whether the above processing resulted in an Exception
		 * or proceeded normally, we want to close the Hibernate session.  When
		 * closing the session, we must allow for the possibility of a Hibernate
		 * Exception.
		 *
		 */
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



     public static boolean unlinkEng(String pORq ) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from eng_scheduler where projectOrQuote='"+pORq+"'");

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
