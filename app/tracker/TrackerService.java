//ProjectService.java contains the db code related to clients to actually read/write
//to/from db

package app.tracker;

import java.util.*;
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
import app.project.*;
import app.user.*;
import app.quote.*;
import app.resource.*;
import app.standardCode.*;
import net.sf.hibernate.Criteria;
import net.sf.hibernate.expression.*;

public class TrackerService {
    
    private static TrackerService instance = null;
    
    public TrackerService() {
        
    }
    
    //return the instance of ProjectService
    public static synchronized TrackerService getInstance() {
                /*
                 * Creates the Singleton instance, if needed.
                 *
                 */
        if (instance == null) {
            instance = new TrackerService();
        }
        return instance;
    }
    
    
    
    //get all projects
    public List getProjectList(String username) {
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
            List results = null;
            if(username.indexOf("@")>-1){
                query = session.createQuery("select project from app.project.Project project where project.isTracked='Y' "+
                    "  order by project.number");
                //and p.Contact.Email_address='"+username+"'
                results =  query.list();
                
                
                for(ListIterator iter = results.listIterator(); iter.hasNext();) {
                    
                    Project p = (Project)iter.next();
                    if(p.getContact().getEmail_address()==null || !p.getContact().getEmail_address().equals(username)){
                        iter.remove();
                    }
                    
                }
            }else{
            
                query = session.createQuery("select project from app.project.Project project where project.isTracked='Y' "+
                    " order by project.number");
                // and p.Company.Company_code='"+username+"'
                results =  query.list();
                
                for(ListIterator iter = results.listIterator(); iter.hasNext();) {
                    
                    Project p = (Project)iter.next();
                    if(p.getCompany().getCompany_code()==null || !p.getCompany().getCompany_code().equals(username)){
                        iter.remove();
                    }
                    
                }
                
            }
            return results;
        }
        catch (HibernateException e) {
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
        finally {
            if (session != null) {
                try {
                    session.close();
                }
                catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }
                
            }
        }
    }
    
    //update an existing MilestoneLanguage in database
    public MilestoneLanguage updateMilestoneLanguage(MilestoneLanguage i) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;
        
        Transaction tx = null;
        
        try {
            
            tx = session.beginTransaction();
            
            session.saveOrUpdate(i);
            
            tx.commit();
        }
        catch (HibernateException e) {
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
        finally {
            if (session != null) {
                try {
                    
                    session.close();
                }
                catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }
                
            }
        }
        return i;
    }

    //update an existing MilestoneLanguage in database
    public void deleteAllMilestonsForProject(String  projectId) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;
        
        Transaction tx = null;
        
        try {
            
            tx = session.beginTransaction();
            
            //session.saveOrUpdate(i);
           session.delete("from MilestoneLanguage ml where ml.project_id=?",new Integer(projectId), Hibernate.INTEGER);
            
            
            tx.commit();
        }
        catch (HibernateException e) {
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
        finally {
            if (session != null) {
                try {
                    
                    session.close();
                }
                catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }
                
            }
        }
      
    }
     
    //get a location by its name
        //should be unique
        public List getMilLanList(Integer projectId)
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
			
                        results = session.find("from MilestoneLanguage as ml where ml.project_id = ?",
                                               new Object[] {projectId},
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
                if(results.isEmpty())
                    return null;
                else 
                    return  results;

	}
    
}

