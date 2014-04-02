/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.tools;

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
public class ToolsService {

    private static ToolsService instance = null;

    public ToolsService(){}

      //return the instance of ToolsService
	public static synchronized ToolsService getInstance()
	{
		/*
		 * Creates the Singleton instance, if needed.
		 *
		 */
		if (instance == null)
		{
			instance = new ToolsService();
		}
		return instance;
	}

    public Conversion addConversionRate(Conversion con)
    {
            Session session = ConnectionFactory.getInstance().getSession();
                boolean tf = true;

                Transaction tx = null;

		try
		{

                        tx = session.beginTransaction();

                        session.saveOrUpdate(con);

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

        return null;
    }
       public Conversion getSingleConversion(Integer id)
	{

		/*
		 * Use the ConnectionFactory to retrieve an open
		 * Hibernate Session.
		 *
		 */
		Session session = ConnectionFactory.getInstance().getSession();
                List results = null;
		try
		{
                        results = session.find("from Conversion as vc where vc.id = ?",
                                               new Object[] {id},
                                               new Type[] {Hibernate.INTEGER} );



		}
		/*
		 * If the object is not found, i.e., no Item exists with the
		 * requested id, we want the method to return null rather
		 * than throwing an Exception.
		 *
		 */
		catch (ObjectNotFoundException onfe)
		{
			return null;
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
                if(results.isEmpty()) {
            return null;
        }
                else {
            return (Conversion) results.get(0);
        }

	}

       public Conversion getSingleConversionbyLang(String src,String tgt)
	{

		/*
		 * Use the ConnectionFactory to retrieve an open
		 * Hibernate Session.
		 *
		 */
		Session session = ConnectionFactory.getInstance().getSession();
                List results = null;
		try
		{

                     results = session.find("from Conversion as c where c.source = ? and c.target = ?",
                                               new Object[] {src,tgt},
                                               new Type[] {Hibernate.STRING,Hibernate.STRING} );




		}
		/*
		 * If the object is not found, i.e., no Item exists with the
		 * requested id, we want the method to return null rather
		 * than throwing an Exception.
		 *
		 */
		catch (ObjectNotFoundException onfe)
		{
			return null;
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
                if(results.isEmpty()) {
            return null;
        }
                else {
            return (Conversion) results.get(0);
        }

	}

          public Conversion getLatestConversion()
	{

		/*
		 * Use the ConnectionFactory to retrieve an open
		 * Hibernate Session.
		 *
		 */
		Session session = ConnectionFactory.getInstance().getSession();
                List results = null;
		try
		{

                        results = session.find("from Conversion as conv ");



		}
		/*
		 * If the object is not found, i.e., no Item exists with the
		 * requested id, we want the method to return null rather
		 * than throwing an Exception.
		 *
		 */
		catch (ObjectNotFoundException onfe)
		{
			return null;
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
                if(results.isEmpty()) {
            return null;
        }
                else {
            return (Conversion) results.get(0);
        }

	}
          public List getConversionList()
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
			query =	session.createQuery("select Conversion from app.tools.Conversion Conversion");
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
    public List getTimeZoneList()
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
			query =	session.createQuery("select clientContact from app.client.ClientContact clientContact where timeZone is not Null and timeZone <> ''");
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


}
