//HrService.java contains the db code related to hr to actually read/write
//to/from db. It is different from UserService because UserService handles
//login and privileges; HrService handles hr module information like employee address
//NOTE: may not need: all user-related in UserService

package app.hr;

import java.util.List;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.Configuration;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.*;
import net.sf.hibernate.Hibernate;
import app.db.*;
import app.client.*;
import app.extjs.vo.DefaultValue;
import net.sf.hibernate.Criteria;
import net.sf.hibernate.expression.*;
                       
public class HrService
{

	private static HrService instance = null;

	public HrService()
	{

	}
        
        //return the instance of ClientService
	public static synchronized HrService getInstance()
	{
		/*
		 * Creates the Singleton instance, if needed.
		 * 
		 */
		if (instance == null)
		{
			instance = new HrService();
		}
		return instance;
	}
        
        //add a new client to the database
        public Client addClient(Client c)
	{

		Session session = ConnectionFactory.getInstance().getSession();
                
                Transaction tx = null;
                
		try
		{
                        
                        tx = session.beginTransaction();
                        
			session.save(c);
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
                return c;
	}
        
        //add a new client to the database
        public Industry addIndustry(Industry i)
	{

		Session session = ConnectionFactory.getInstance().getSession();
                
                Transaction tx = null;
                
		try
		{
                        
                        tx = session.beginTransaction();
                        
			session.save(i);
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
                return i;
	}

        //update an existing Client in database
	public Client updateClient(Client c)
	{
		Session session = ConnectionFactory.getInstance().getSession();
                boolean tf = true;
                
                Transaction tx = null;
                
		try
		{
                        
                        tx = session.beginTransaction();
                        
                        session.saveOrUpdate(c);
                        
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
                return c;
        }
        
	//get all clients
	public List getClientList()
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
			query =	session.createQuery("select client from app.client.Client client order by client.Company_name");
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
        
        
        
        //search for clients given the criteria
        public List getClientSearch(String status, String companyName, String clientContactLastName, String clientContactFirstName) 
	{
		
		Session session = ConnectionFactory.getInstance().getSession();
                Query query;
                List results = null;
                
		try
		{
                      	//retreive clients from database
                    
                        //this is the "one" class: it is the main criteria
                        Criteria criteria = session.createCriteria(Client.class);
                        
                        //if subcriteria values are present from the form (for the "many" class),
                        //then include subcriteria search values in main search (the "one" class)
                        if(clientContactLastName.length() >= 1 || clientContactFirstName.length() >= 1) {
                            Criteria subCriteria = criteria.createCriteria("Contacts");                        
                            subCriteria.add(Expression.like("Last_name", "%" + clientContactLastName + "%").ignoreCase());
                            subCriteria.add(Expression.like("First_name", "%" + clientContactFirstName + "%").ignoreCase());
                        }
                        
                        //add search on status if status from form does not equal "All"
                        if(!status.equals("All")) {
                            criteria.add(Expression.eq("Status", status));
                        }
                        
                        criteria.add(Expression.like("Company_name", "%" + companyName + "%").ignoreCase());
                        criteria.addOrder(Order.asc("Company_name"));
                        
                        //remove duplicates
                        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                        
                        results = criteria.list();
                                                
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
                
                if(results.isEmpty())
                    return null;
                else 
                    return results;
         }
        
        
        //return one client with id given in argument
        public Client getSingleClient(Integer id)
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
			
                        results = session.find("from Client as client where client.id = ?",
                                               new Object[] {id},
                                               new Type[] {Hibernate.INTEGER} );             
                        
			
		}
		
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
                if(results.isEmpty())
                    return null;
                else
                    return (Client) results.get(0);

	}
        
        //return one client contact with id given in argument
        public ClientContact getSingleClientContact(Integer id)
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
			
                        results = session.find("from ClientContact as clientContact where clientContact.id = ?",
                                               new Object[] {id},
                                               new Type[] {Hibernate.INTEGER} );             
                        
			
		}
		
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
                if(results.isEmpty())
                    return null;
                else
                    return (ClientContact) results.get(0);

	}
        
        //update just the client, nothing was changed to any of the client's contacts
        public boolean clientUpdateNoContact(Client c)
	{

		Session session = ConnectionFactory.getInstance().getSession();
                boolean t = true;
                Transaction tx = null;
                
		try
		{
                        
                        tx = session.beginTransaction();
                        
			session.saveOrUpdate(c);
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
                
                return t;
	}
        
        //update on contact within one client
        public boolean clientContactUpdate(ClientContact cc)
	{

		Session session = ConnectionFactory.getInstance().getSession();
                boolean t = true;
                Transaction tx = null;
                
		try
		{
                        
                        tx = session.beginTransaction();
                        
			session.saveOrUpdate(cc);
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
                
                return t;
	}
        
        //place a new contact in db
        public ClientContact addContact(Client c, ClientContact cc) {            
                Session session = ConnectionFactory.getInstance().getSession();                
                Transaction tx = null;
                
		try
		{
                        tx = session.beginTransaction();
                        c.getContacts().add(cc);
                        
			session.save(cc);
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
		try
		{
			
                        results = session.find("from Client as client where client.Company_name = ?",
                                               new Object[] {Company_name},
                                               new Type[] {Hibernate.STRING} );             
                        
			
		}
		
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
                if(results.isEmpty())
                    return null;
                else
                    return (Client) results.get(0);
	
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
		try
                {	
                        results = session.find("from Client as client where client.Company_code = ?",
                                               new Object[] {Company_code},
                                               new Type[] {Hibernate.STRING} );             
                        
			
		}
		
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
                if(results.isEmpty())
                    return null;
                else
                    return (Client) results.get(0);
	
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
		try
		{
			
                        results = session.find("from Industry as industry where industry.Description = ?",
                                               new Object[] {Description},
                                               new Type[] {Hibernate.STRING} );             
                        
			
		}
		
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
                if(results.isEmpty())
                    return null;
                else
                    return (Industry) results.get(0);
	
        }
        
        //get all industries
	public List getIndustryList()
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
			query =	session.createQuery("select industry from app.client.Industry industry");
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
        
        //provide a list of statuses for forms (in clients)
        public String[] getStatuses() {
            String Status[] = {"Prospect", "Active", "Lost"};
            return Status;
        }

         public List getDropdownList(String dropdownType)
	{
		/*
		 * Use the ConnectionFactory to retrieve an open
		 * Hibernate Session.
		 *
		 */
		Session session = ConnectionFactory.getInstance().getSession();
                Query query;
                 //System.out.println("dropdownType     " +dropdownType);
		try
		{
			/*
			* Build HQL (Hibernate Query Language) query to retrieve a list
			* of all the items currently stored by Hibernate.
			 */
                        //query =	session.createQuery("select client from app.client.Client client  where client.Backup_pm='"+pmName+"'");
			query =	session.createQuery("select dropdown from app.extjs.vo.Dropdown dropdown where dropdown.dropdownType='"+dropdownType+"' and dropdown.tab='' order by dropdown.priority desc");
                        ////System.out.println("Query List              "+ query.list());
                        // //System.out.println("Query              "+ query);
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

 public List getDropdownValue(String dropdownType)
	{
		/*
		 * Use the ConnectionFactory to retrieve an open
		 * Hibernate Session.
		 *
		 */
		Session session = ConnectionFactory.getInstance().getSession();
                Query query;
   //System.out.println("dropdownType     " +dropdownType);
		try
		{
			/*
			* Build HQL (Hibernate Query Language) query to retrieve a list
			* of all the items currently stored by Hibernate.
			 */
                        //query =	session.createQuery("select client from app.client.Client client  where client.Backup_pm='"+pmName+"'");
			query =	session.createQuery("select dropdownValue from app.extjs.vo.Dropdown dropdown where dropdown.dropdownType='"+dropdownType+"' and dropdown.tab=''");
                        ////System.out.println("Query List              "+ query.list());
                        // //System.out.println("Query              "+ query);
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

  public List getDefaultValueList()
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
                        //query =	session.createQuery("select client from app.client.Client client  where client.Backup_pm='"+pmName+"'");
			query =	session.createQuery("select DefaultValue from app.extjs.vo.DefaultValue DefaultValue ");
                        ////System.out.println("Query List              "+ query.list());
                        // //System.out.println("Query              "+ query);
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

//return a Client object, but search for the client by compny name instead of by integer id
        public String getDefaultValue(String defaultName) {
            /*
		 * Use the ConnectionFactory to retrieve an open
		 * Hibernate Session.
		 *
		 */
             DefaultValue dv=null;
		Session session = ConnectionFactory.getInstance().getSession();
                List results = null;
		try
		{

                        results = session.find("from DefaultValue as d where d.defaultName = ?",
                                               new Object[] {defaultName},
                                               new Type[] {Hibernate.STRING} );


		}

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
                if(results.isEmpty())
                    return null;
                else
                   dv =(DefaultValue) results.get(0);

                    return dv.getDefaultValue();

        }
         //add a new client to the database
        public DefaultValue addDefaultValue(DefaultValue d)
	{

		Session session = ConnectionFactory.getInstance().getSession();

                Transaction tx = null;

		try
		{

                        tx = session.beginTransaction();

			session.saveOrUpdate(d);
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
                return d;
	}

         







}

