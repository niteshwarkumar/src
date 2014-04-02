//ReportsService.java contains the db code related to generating
//reports, such as monthly dollar totals, etc.

package app.reports;

import java.util.*;
import net.sf.hibernate.*;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.*;
import net.sf.hibernate.Hibernate;
import app.db.*;
import app.client.*;
import app.project.*;
import app.menu.*;
import app.user.*;
import app.resource.*;
import app.standardCode.*;
import net.sf.hibernate.Criteria;
import net.sf.hibernate.expression.*;
import org.apache.xmlbeans.impl.xb.xsdschema.RestrictionDocument;
                       
public class ReportsService
{

	private static ReportsService instance = null;

	public ReportsService()
	{

	}
        
        //return the instance of ReportsService
	public static synchronized ReportsService getInstance()
	{
		/*
		 * Creates the Singleton instance, if needed.
		 * 
		 */
		if (instance == null)
		{
			instance = new ReportsService();
		}
		return instance;
	}
        
        //add a new resource (team member) to the database
        public Integer addResource(Resource r)
	{

		Session session = ConnectionFactory.getInstance().getSession();
                Integer resourceId = null;
                Transaction tx = null;
                
		try
		{
                        
                        tx = session.beginTransaction();
                        
			session.save(r);
                        
			tx.commit();
                        
                        resourceId = r.getResourceId();
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
                return resourceId;
	}
        
       
        
        //return count of a locations monthly projects
        String getProjectCount(int i, List locationList) {
            int countList = 0;
            if(locationList != null) {
                for(ListIterator iter = locationList.listIterator(); iter.hasNext();) {
                    Project p = (Project) iter.next();
                    if(p.getStartDate() != null) {
                        if(p.getStartDate().getMonth() == i) {
                            countList++;
                        }
                    }
                }
            }
                return String.valueOf(countList);
        }
        
        //return reveune of a locations monthly projects
        String getProjectRevenue(int i, List locationList) {
            double revenueList = 0.0;
            if(locationList != null) {
                for(ListIterator iter = locationList.listIterator(); iter.hasNext();) {
                    Project p = (Project) iter.next();
                    if(p.getStartDate() != null) {
                        if(p.getStartDate().getMonth() == i) {
                            if(p.getProjectAmount() != null) {
                                revenueList += p.getProjectAmount().doubleValue();
                            }
                        }
                    }
                }
            }
                return StandardCode.getInstance().formatDouble(new Double(revenueList));
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

        //return month string
        String getMonthName(int i) {
            if(i == 0)
                return "Jan";
            if(i == 1)
                return "Feb";
            if(i == 2)
                return "Mar";
            if(i == 3)
                return "Apr";
            if(i == 4)
                return "May";
            if(i == 5)
                return "Jun";
            if(i == 6)
                return "Jul";
            if(i == 7)
                return "Aug";
            if(i == 8)
                return "Sep";
            if(i == 9)
                return "Oct";
            if(i == 10)
                return "Nov";
            if(i == 11)
                return "Dec";
            
            return "-"; //default
        }
        
        //update an existing resource in database
	public Resource updateResource(Resource r)
	{
		Session session = ConnectionFactory.getInstance().getSession();
                boolean tf = true;
                
                Transaction tx = null;
                
		try
		{
                        
                        tx = session.beginTransaction();
                        
                        session.saveOrUpdate(r);
                        
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
                return r;
        }
        
        //update an existing resourceContact in database
	public ResourceContact updateResourceContact(ResourceContact rc)
	{
		Session session = ConnectionFactory.getInstance().getSession();
                boolean tf = true;
                
                Transaction tx = null;
                
		try
		{
                        
                        tx = session.beginTransaction();
                        
                        session.saveOrUpdate(rc);
                        
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
                return rc;
        }
        
        //update an existing todo list item in database
	public Todo updateTodo(Todo t)
	{
		Session session = ConnectionFactory.getInstance().getSession();
                boolean tf = true;
                
                Transaction tx = null;
                
		try
		{
                        
                        tx = session.beginTransaction();
                        
                        session.saveOrUpdate(t);
                        
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
        
        //update an existing languagePair in database
	public LanguagePair updateLanguagePair(LanguagePair lp)
	{
		Session session = ConnectionFactory.getInstance().getSession();
               
                Transaction tx = null;
                
		try
		{
                        
                        tx = session.beginTransaction();
                        
                        session.saveOrUpdate(lp);
                        
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
                return lp;
        }
        
        //update an existing rateScoreLanguage in database
	public RateScoreLanguage updateRateScoreLanguage(RateScoreLanguage rsl)
	{
		Session session = ConnectionFactory.getInstance().getSession();
               
                Transaction tx = null;
                
		try
		{
                        
                        tx = session.beginTransaction();
                        
                        session.saveOrUpdate(rsl);
                        
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
                return rsl;
        }
        
        //update an existing rateScoreDtp in database
	public RateScoreDtp updateRateScoreDtp(RateScoreDtp rsd)
	{
		Session session = ConnectionFactory.getInstance().getSession();
               
                Transaction tx = null;
                
		try
		{
                        
                        tx = session.beginTransaction();
                        
                        session.saveOrUpdate(rsd);
                        
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
                return rsd;
        }
        
        //update an existing resourceTool in database
	public ResourceTool updateResourceTool(ResourceTool rt)
	{
		Session session = ConnectionFactory.getInstance().getSession();
               
                Transaction tx = null;
                
		try
		{
                        
                        tx = session.beginTransaction();
                        
                        session.saveOrUpdate(rt);
                        
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
                return rt;
        }
        
	//get all announcements
        //use today's date to display only current announcements
	public List getAnnouncementList()
	{
		/*
		 * Use the ConnectionFactory to retrieve an open
		 * Hibernate Session.
		 * 
		 */
		Session session = ConnectionFactory.getInstance().getSession();
                List results;
                //get today to determine which announcements to display
                Date today = new Date();
                
		try
		{
			Criteria criteria = session.createCriteria(Announcement.class);
                        
                                               
                        criteria.add(Expression.le("startDate", today));
                        criteria.add(Expression.ge("endDate", today));
                        
                        criteria.addOrder(Order.asc("startDate"));
                                               
                        //remove duplicates
                        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                        
                        results = criteria.list();
                        
                        return results;
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
        
        //get all rateScoreCategories
	public List getRateScoreCategoryList()
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
			query =	session.createQuery("select rateScoreCategory from app.resource.RateScoreCategory rateScoreCategory");
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
        
        //get all agencies
	public List getResourceCompanyList()
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
			query =	session.createQuery("select resource from app.resource.Resource resource where resource.agency = true order by resource.companyName");
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
        
        //search for resources (team members) given the criteria
        //this is the main search without including old db rates and scores
        public List getResourceSearch(String FirstName, String LastName, String ContactFirstName, String ContactLastName, String Agency, String OldId, String SourceId,  String TargetId, String source, String target, String Status, String IncludeDoNot, String Translator, String Editor, String Proofreader, String Dtp, String Icr, String TRate, String ERate, String TERate, String PRate, String DtpRate, String dtpSource, String dtpTarget, String RateOldDb, String[] Specific, String[] General, String[] ScoresLin, List rscs, String ScoreOldDb, String ProjectScoreGreater, String UsesTrados, String UsesSdlx,  String UsesDejavu, String UsesCatalyst,  String UsesOtherTool1, String UsesOtherTool2, String UsesTransit,  String City,  String Country, String Resume) 
	{
		
		Session session = ConnectionFactory.getInstance().getSession();
                Query query;
                List results = null;
                
		try
		{
                      	//retreive resources from database
                    
                        //this is the "one" class: it is the main criteria
                        Criteria criteria = session.createCriteria(Resource.class);
                        
                        criteria.add(Expression.like("firstName", "%" + FirstName + "%").ignoreCase());
                        criteria.add(Expression.like("lastName", "%" + LastName + "%").ignoreCase());
                        criteria.add(Expression.like("companyName", "%" + Agency + "%").ignoreCase());
                        //if subcriteria values are present from the form (for the "many" class),
                        //then include subcriteria search values in main search (the "one" class)
                        if(ContactFirstName.length() >= 1 || ContactLastName.length() >= 1) {
                            Criteria subCriteria = criteria.createCriteria("ResourceContacts");                        
                            subCriteria.add(Expression.like("firstName", "%" + ContactFirstName + "%").ignoreCase());
                            subCriteria.add(Expression.like("lastName", "%" + ContactLastName + "%").ignoreCase());
                        }
                        if(OldId.length() >= 1) {
                            criteria.add(Expression.eq("resourceId", Integer.valueOf(OldId)));
                        }
                        
                        //if subcriteria values are present from the form (for the "many" class),
                        //then include subcriteria search values in main search (the "one" class)
                        Criteria subCriteriaLanguagePairs = null;
                        if(!SourceId.equals("0") || !TargetId.equals("0")) {
                            subCriteriaLanguagePairs = criteria.createCriteria("LanguagePairs"); 
                        }                         
                        if(!SourceId.equals("0")) {
                            subCriteriaLanguagePairs.add(Expression.eq("sourceId", Integer.valueOf(SourceId)));
                        }
                        if(!TargetId.equals("0")) {
                            subCriteriaLanguagePairs.add(Expression.eq("targetId", Integer.valueOf(TargetId)));
                        }   
                        
                        if(!Status.equals("0")) {
                            criteria.add(Expression.eq("status", Status)); 
                        }
                        if(IncludeDoNot == null) {
                            criteria.add(Expression.eq("doNotUse", new Boolean(false))); 
                        }
                      
                        if(Translator != null) {
                            criteria.add(Expression.eq("translator", new Boolean(true))); 
                        }
                        if(Editor != null) {
                            criteria.add(Expression.eq("editor", new Boolean(true))); 
                        }
                        if(Proofreader != null) {
                            criteria.add(Expression.eq("proofreader", new Boolean(true))); 
                        }
                        if(Dtp != null) {
                            criteria.add(Expression.eq("dtp", new Boolean(true))); 
                        }
                        if(Icr != null) {
                            criteria.add(Expression.eq("icr", new Boolean(true))); 
                        }
                        
                        //if subcriteria values are present from the form (for the "many" class),
                        //then include subcriteria search values in main search (the "one" class)
                        Criteria subCriteriaRateScoreLanguages = null;  
                        boolean sourceSet = false;
                        boolean targetSet = false;
                        boolean rateScoreLanguagesCon = false;
                        if(TRate.length() >= 1 || ERate.length() >= 1 || TERate.length() >= 1 || PRate.length() >= 1) {
                            subCriteriaRateScoreLanguages = subCriteriaLanguagePairs.createCriteria("RateScoreLanguages");  
                            rateScoreLanguagesCon = true;
                            if(source != null) {
                                subCriteriaRateScoreLanguages.add(Expression.eq("source", source));
                                sourceSet = true;
                            }
                            if(target != null) {
                                subCriteriaRateScoreLanguages.add(Expression.eq("target", target));
                                targetSet = true;
                            }
                            
                            Disjunction any = Expression.disjunction();
                            
                            if(TRate.length() >= 1) {
                                Conjunction all = Expression.conjunction();
                                all.add(Expression.gt("t", new Double(0)));
                                all.add(Expression.le("t", Double.valueOf(TRate)));
                                any.add(all);
                            }
                            if(ERate.length() >= 1) {
                                Conjunction all = Expression.conjunction();
                                all.add(Expression.gt("e", new Double(0)));
                                all.add(Expression.le("e", Double.valueOf(ERate)));
                                any.add(all);
                            }
                            if(TERate.length() >= 1) {
                                Conjunction all = Expression.conjunction();
                                all.add(Expression.gt("te", new Double(0)));
                                all.add(Expression.le("te", Double.valueOf(TERate)));
                                any.add(all);
                            }
                            if(PRate.length() >= 1) {
                                Conjunction all = Expression.conjunction();
                                all.add(Expression.gt("p", new Double(0)));
                                all.add(Expression.le("p", Double.valueOf(PRate)));
                                any.add(all);
                            }  
                            subCriteriaRateScoreLanguages.add(any);
                        }
                        //if subcriteria values are present from the form (for the "many" class),
                        //then include subcriteria search values in main search (the "one" class)
                        if(DtpRate.length() >= 1 || dtpSource != null || dtpTarget != null) {
                            Criteria subCriteria = criteria.createCriteria("RateScoreDtps"); 
                            if(dtpSource != null) {
                                subCriteria.add(Expression.eq("source", dtpSource));
                            }
                            if(dtpTarget != null) {
                                subCriteria.add(Expression.eq("target", dtpTarget));
                            }
                            if(DtpRate.length() >= 1) {
                                subCriteria.add(Expression.gt("rate", new Double(0)));
                                subCriteria.add(Expression.le("rate", Double.valueOf(DtpRate)));
                            }                           
                        }
//                        if(RateOldDb != null) {
//                            Disjunction any = Expression.disjunction();
//                                                     
//                            if(TRate.length() >= 1) {
//                                Conjunction all = Expression.conjunction();
//                                all.add(Expression.gt("t", new Double(0)));
//                                all.add(Expression.le("t", Double.valueOf(TRate)));
//                                any.add(all);
//                            }
//                            if(ERate.length() >= 1) {
//                                Conjunction all = Expression.conjunction();
//                                all.add(Expression.gt("e", new Double(0)));
//                                all.add(Expression.le("e", Double.valueOf(ERate)));
//                                any.add(all);
//                            }
//                            if(TERate.length() >= 1) {
//                                Conjunction all = Expression.conjunction();
//                                all.add(Expression.gt("te", new Double(0)));
//                                all.add(Expression.le("te", Double.valueOf(TERate)));
//                                any.add(all);
//                            }
//                            if(PRate.length() >= 1) {
//                                Conjunction all = Expression.conjunction();
//                                all.add(Expression.gt("p", new Double(0)));
//                                all.add(Expression.le("p", Double.valueOf(PRate)));
//                                any.add(all);
//                            }   
//                            criteria.add(any);
//                        }                        
                          
                        if(Specific.length > 0 && !Specific[0].equals("0")) { 
                            Criteria subCriteriaSpecificIndustries = criteria.createCriteria("SpecificIndustries"); 
                            Integer[] specificIds = new Integer[Specific.length];
                            for(int i = 0; i < specificIds.length; i++) {
                                specificIds[i] = new Integer(Specific[i]);
                            }
                            subCriteriaSpecificIndustries.add(Expression.in("specificIndustryId", specificIds));
                        }
                        if(General.length > 0 && !General[0].equals("0")) { 
                            Criteria subCriteriaIndustries = criteria.createCriteria("Industries");                          
                            Integer[] generalIds = new Integer[General.length];
                            for(int i = 0; i < generalIds.length; i++) {
                                generalIds[i] = new Integer(General[i]);
                            }
                            subCriteriaIndustries.add(Expression.in("industryId", generalIds));
                        }
                        
                        boolean rateScoreScore = false;
                        for(int i = 0; i < ScoresLin.length; i++) {
                            if(ScoresLin[i] != null && ScoresLin[i].length() >= 1 && rateScoreLanguagesCon == false) {
                                subCriteriaRateScoreLanguages = subCriteriaLanguagePairs.createCriteria("RateScoreLanguages");  
                                rateScoreLanguagesCon = true;
                                rateScoreScore = true;
                            }                                
                        }                          
                        for(int i = 0; i < ScoresLin.length; i++) {
                            if(ScoresLin[i] != null && ScoresLin[i].length() >= 1) {
                                if(source != null && sourceSet == false) {
                                    subCriteriaRateScoreLanguages.add(Expression.eq("source", source));
                                    sourceSet = true;
                                }
                                if(target != null && targetSet == false) {
                                    subCriteriaRateScoreLanguages.add(Expression.eq("target", target));
                                    targetSet = true;
                                }
                            }
                        }      
                        ListIterator iterRscs = rscs.listIterator();
                        Disjunction anyRateScore = Expression.disjunction();
                        for(int i = 0; i < ScoresLin.length; i++) {
                            RateScoreCategory rsc = (RateScoreCategory) iterRscs.next();
                            if(ScoresLin[i] != null && ScoresLin[i].length() >= 1) {
                               Conjunction all = Expression.conjunction();
                               all.add(Expression.eq("specialty", rsc.getCategory()));
                               all.add(Expression.ge("score", Double.valueOf(ScoresLin[i])));
                               anyRateScore.add(all);
                            }
                        }
                        if(rateScoreScore) {
                            subCriteriaRateScoreLanguages.add(anyRateScore);
                        }
                        
//                        if(ScoreOldDb != null) {
//                            Disjunction any = Expression.disjunction();
//                            
//                            if(ScoresLin[0] != null && ScoresLin[0].length() >= 1) {
//                                any.add(Expression.ge("medicalScore", Double.valueOf(ScoresLin[0])));
//                            }
//                            if(ScoresLin[1] != null && ScoresLin[1].length() >= 1) {
//                                any.add(Expression.ge("technicalScore", Double.valueOf(ScoresLin[1])));
//                            }
//                            if(ScoresLin[2] != null && ScoresLin[2].length() >= 1) {
//                                any.add(Expression.ge("softwareScore", Double.valueOf(ScoresLin[2])));
//                            }
//                            if(ScoresLin[3] != null && ScoresLin[3].length() >= 1) {
//                                any.add(Expression.ge("legalFinancialScore", Double.valueOf(ScoresLin[3])));
//                            }   
//                            criteria.add(any);
//                        }
                        
                        if(!ProjectScoreGreater.equals("null")) {
                            criteria.add(Expression.ge("projectScoreAverage", Double.valueOf(ProjectScoreGreater)));
                        }
                        
                        if(UsesTrados != null) {
                            criteria.add(Expression.eq("usesTrados", new Boolean(true))); 
                        }
                        if(UsesDejavu != null) {
                            criteria.add(Expression.eq("usesDejavu", new Boolean(true))); 
                        }
                        if(UsesCatalyst != null) {
                            criteria.add(Expression.eq("usesCatalyst", new Boolean(true))); 
                        }
                        if(UsesSdlx != null) {
                            criteria.add(Expression.eq("usesSdlx", new Boolean(true))); 
                        }
                        if(UsesTransit != null) {
                            criteria.add(Expression.eq("usesTransit", new Boolean(true))); 
                        }
                        if(UsesOtherTool1.length() >= 1 || UsesOtherTool2.length() >= 1) {
                            Criteria subCriteria = criteria.createCriteria("ResourceTools"); 
                            Disjunction any = Expression.disjunction();
                            if(UsesOtherTool2.length() == 0) {
                                subCriteria.add(Expression.like("description", "%" + UsesOtherTool1 + "%").ignoreCase());
                            }
                            else {
                                any.add(Expression.like("description", "%" + UsesOtherTool1 + "%").ignoreCase());
                                any.add(Expression.like("description", "%" + UsesOtherTool2 + "%").ignoreCase());
                                subCriteria.add(any);
                            }                            
                        }
                        
                        if(City.length() >= 1) {
                            criteria.add(Expression.like("City", "%" + City + "%").ignoreCase());
                        }
                        if(!Country.equals("0")) {
                            criteria.add(Expression.eq("Country", Country));
                        }
                        
                        if(Resume.length() >= 1) {
                            criteria.add(Expression.like("resume", "%" + Resume + "%").ignoreCase());
                        }
                        
                        //sort results by id from old database
                        criteria.addOrder(Order.asc("resourceId"));
                        
                        //remove duplicates
                        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                       
                        //get results
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
          
        //search for resources (team members) given the criteria
        //this adds the oldRates to the search results
        public List getResourceSearchOldRates(String FirstName, String LastName, String ContactFirstName, String ContactLastName, String Agency, String OldId, String SourceId,  String TargetId, String source, String target, String Status, String IncludeDoNot, String Translator, String Editor, String Proofreader, String Dtp, String Icr, String TRate, String ERate, String TERate, String PRate, String DtpRate, String RateOldDb, String[] Specific, String[] General, String[] ScoresLin, List rscs, String ScoreOldDb, String ProjectScoreGreater, String UsesTrados, String UsesSdlx,  String UsesDejavu, String UsesCatalyst,  String UsesOtherTool1, String UsesOtherTool2, String UsesTransit,  String City,  String Country, String Resume) 
	{
		
		Session session = ConnectionFactory.getInstance().getSession();
                Query query;
                List results = null;
                
		try
		{
                      	//retreive resources from database
                    
                        //this is the "one" class: it is the main criteria
                        Criteria criteria = session.createCriteria(Resource.class);
                        
                        criteria.add(Expression.like("firstName", "%" + FirstName + "%").ignoreCase());
                        criteria.add(Expression.like("lastName", "%" + LastName + "%").ignoreCase());
                        criteria.add(Expression.like("companyName", "%" + Agency + "%").ignoreCase());
                        //if subcriteria values are present from the form (for the "many" class),
                        //then include subcriteria search values in main search (the "one" class)
                        if(ContactFirstName.length() >= 1 || ContactLastName.length() >= 1) {
                            Criteria subCriteria = criteria.createCriteria("ResourceContacts");                        
                            subCriteria.add(Expression.like("firstName", "%" + ContactFirstName + "%").ignoreCase());
                            subCriteria.add(Expression.like("lastName", "%" + ContactLastName + "%").ignoreCase());
                        }
                        if(OldId.length() >= 1) {
                            criteria.add(Expression.eq("resourceId", Integer.valueOf(OldId)));
                        }
                        
                        //if subcriteria values are present from the form (for the "many" class),
                        //then include subcriteria search values in main search (the "one" class)
                        Criteria subCriteriaLanguagePairs = null;
                        if(!SourceId.equals("0") || !TargetId.equals("0")) {
                            subCriteriaLanguagePairs = criteria.createCriteria("LanguagePairs"); 
                        }                         
                        if(!SourceId.equals("0")) {
                            subCriteriaLanguagePairs.add(Expression.eq("sourceId", Integer.valueOf(SourceId)));
                        }
                        if(!TargetId.equals("0")) {
                            subCriteriaLanguagePairs.add(Expression.eq("targetId", Integer.valueOf(TargetId)));
                        }   
                        
                        if(!Status.equals("0")) {
                            criteria.add(Expression.eq("status", Status)); 
                        }
                        if(IncludeDoNot == null) {
                            criteria.add(Expression.eq("doNotUse", new Boolean(false))); 
                        }
                      
                        if(Translator != null) {
                            criteria.add(Expression.eq("translator", new Boolean(true))); 
                        }
                        if(Editor != null) {
                            criteria.add(Expression.eq("editor", new Boolean(true))); 
                        }
                        if(Proofreader != null) {
                            criteria.add(Expression.eq("proofreader", new Boolean(true))); 
                        }
                        if(Dtp != null) {
                            criteria.add(Expression.eq("dtp", new Boolean(true))); 
                        }
                        if(Icr != null) {
                            criteria.add(Expression.eq("icr", new Boolean(true))); 
                        }
                        
                        //if subcriteria values are present from the form (for the "many" class),
                        //then include subcriteria search values in main search (the "one" class)
                        Criteria subCriteriaRateScoreLanguages = null;  
                        boolean sourceSet = false;
                        boolean targetSet = false;
                        boolean rateScoreLanguagesCon = false;
                    
                        if(RateOldDb != null) {
                            Disjunction any = Expression.disjunction();
                                                     
                            if(TRate.length() >= 1) {
                                Conjunction all = Expression.conjunction();
                                all.add(Expression.gt("t", new Double(0)));
                                all.add(Expression.le("t", Double.valueOf(TRate)));
                                any.add(all);
                            }
                            if(ERate.length() >= 1) {
                                Conjunction all = Expression.conjunction();
                                all.add(Expression.gt("e", new Double(0)));
                                all.add(Expression.le("e", Double.valueOf(ERate)));
                                any.add(all);
                            }
                            if(TERate.length() >= 1) {
                                Conjunction all = Expression.conjunction();
                                all.add(Expression.gt("te", new Double(0)));
                                all.add(Expression.le("te", Double.valueOf(TERate)));
                                any.add(all);
                            }
                            if(PRate.length() >= 1) {
                                Conjunction all = Expression.conjunction();
                                all.add(Expression.gt("p", new Double(0)));
                                all.add(Expression.le("p", Double.valueOf(PRate)));
                                any.add(all);
                            }   
                            criteria.add(any);
                        }                        
                          
                        if(Specific.length > 0 && !Specific[0].equals("0")) { 
                            Criteria subCriteriaSpecificIndustries = criteria.createCriteria("SpecificIndustries"); 
                            Integer[] specificIds = new Integer[Specific.length];
                            for(int i = 0; i < specificIds.length; i++) {
                                specificIds[i] = new Integer(Specific[i]);
                            }
                            subCriteriaSpecificIndustries.add(Expression.in("specificIndustryId", specificIds));
                        }
                        if(General.length > 0 && !General[0].equals("0")) { 
                            Criteria subCriteriaIndustries = criteria.createCriteria("Industries");                          
                            Integer[] generalIds = new Integer[General.length];
                            for(int i = 0; i < generalIds.length; i++) {
                                generalIds[i] = new Integer(General[i]);
                            }
                            subCriteriaIndustries.add(Expression.in("industryId", generalIds));
                        }
                        
//                        boolean rateScoreScore = false;
//                        for(int i = 0; i < ScoresLin.length; i++) {
//                            if(ScoresLin[i] != null && ScoresLin[i].length() >= 1 && rateScoreLanguagesCon == false) {
//                                subCriteriaRateScoreLanguages = subCriteriaLanguagePairs.createCriteria("RateScoreLanguages");  
//                                rateScoreLanguagesCon = true;
//                                rateScoreScore = true;
//                            }                                
//                        }                          
//                        for(int i = 0; i < ScoresLin.length; i++) {
//                            if(ScoresLin[i] != null && ScoresLin[i].length() >= 1) {
//                                if(source != null && sourceSet == false) {
//                                    subCriteriaRateScoreLanguages.add(Expression.eq("source", source));
//                                    sourceSet = true;
//                                }
//                                if(target != null && targetSet == false) {
//                                    subCriteriaRateScoreLanguages.add(Expression.eq("target", target));
//                                    targetSet = true;
//                                }
//                            }
//                        }      
//                        ListIterator iterRscs = rscs.listIterator();
//                        Disjunction anyRateScore = Expression.disjunction();
//                        for(int i = 0; i < ScoresLin.length; i++) {
//                            RateScoreCategory rsc = (RateScoreCategory) iterRscs.next();
//                            if(ScoresLin[i] != null && ScoresLin[i].length() >= 1) {
//                               Conjunction all = Expression.conjunction();
//                               all.add(Expression.eq("specialty", rsc.getCategory()));
//                               all.add(Expression.ge("score", Double.valueOf(ScoresLin[i])));
//                               anyRateScore.add(all);
//                            }
//                        }
//                        if(rateScoreScore) {
//                            subCriteriaRateScoreLanguages.add(anyRateScore);
//                        }
                        
                                          
                        if(!ProjectScoreGreater.equals("null")) {
                            criteria.add(Expression.ge("projectScoreAverage", Double.valueOf(ProjectScoreGreater)));
                        }
                        
                        if(UsesTrados != null) {
                            criteria.add(Expression.eq("usesTrados", new Boolean(true))); 
                        }
                        if(UsesDejavu != null) {
                            criteria.add(Expression.eq("usesDejavu", new Boolean(true))); 
                        }
                        if(UsesCatalyst != null) {
                            criteria.add(Expression.eq("usesCatalyst", new Boolean(true))); 
                        }
                        if(UsesSdlx != null) {
                            criteria.add(Expression.eq("usesSdlx", new Boolean(true))); 
                        }
                        if(UsesTransit != null) {
                            criteria.add(Expression.eq("usesTransit", new Boolean(true))); 
                        }
                        if(UsesOtherTool1.length() >= 1 || UsesOtherTool2.length() >= 1) {
                            Criteria subCriteria = criteria.createCriteria("ResourceTools"); 
                            Disjunction any = Expression.disjunction();
                            if(UsesOtherTool2.length() == 0) {
                                subCriteria.add(Expression.like("description", "%" + UsesOtherTool1 + "%").ignoreCase());
                            }
                            else {
                                any.add(Expression.like("description", "%" + UsesOtherTool1 + "%").ignoreCase());
                                any.add(Expression.like("description", "%" + UsesOtherTool2 + "%").ignoreCase());
                                subCriteria.add(any);
                            }                            
                        }
                        
                        if(City.length() >= 1) {
                            criteria.add(Expression.like("City", "%" + City + "%").ignoreCase());
                        }
                        if(!Country.equals("0")) {
                            criteria.add(Expression.eq("Country", Country));
                        }
                        
                        if(Resume.length() >= 1) {
                            criteria.add(Expression.like("resume", "%" + Resume + "%").ignoreCase());
                        }
                        
                        //sort results by id from old database
                        criteria.addOrder(Order.asc("resourceId"));
                        
                        //remove duplicates
                        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                       
                        //get results
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
        
        //search for resources (team members) given the criteria
        //this adds the oldScores to the search results
        public List getResourceSearchOldScores(String FirstName, String LastName, String ContactFirstName, String ContactLastName, String Agency, String OldId, String SourceId,  String TargetId, String source, String target, String Status, String IncludeDoNot, String Translator, String Editor, String Proofreader, String Dtp, String Icr, String TRate, String ERate, String TERate, String PRate, String DtpRate, String RateOldDb, String[] Specific, String[] General, String[] ScoresLin, List rscs, String ScoreOldDb, String ProjectScoreGreater, String UsesTrados, String UsesSdlx,  String UsesDejavu, String UsesCatalyst,  String UsesOtherTool1, String UsesOtherTool2, String UsesTransit,  String City,  String Country, String Resume) 
	{
		
		Session session = ConnectionFactory.getInstance().getSession();
                Query query;
                List results = null;
                
		try
		{
                      	//retreive resources from database
                    
                        //this is the "one" class: it is the main criteria
                        Criteria criteria = session.createCriteria(Resource.class);
                        
                        criteria.add(Expression.like("firstName", "%" + FirstName + "%").ignoreCase());
                        criteria.add(Expression.like("lastName", "%" + LastName + "%").ignoreCase());
                        criteria.add(Expression.like("companyName", "%" + Agency + "%").ignoreCase());
                        //if subcriteria values are present from the form (for the "many" class),
                        //then include subcriteria search values in main search (the "one" class)
                        if(ContactFirstName.length() >= 1 || ContactLastName.length() >= 1) {
                            Criteria subCriteria = criteria.createCriteria("ResourceContacts");                        
                            subCriteria.add(Expression.like("firstName", "%" + ContactFirstName + "%").ignoreCase());
                            subCriteria.add(Expression.like("lastName", "%" + ContactLastName + "%").ignoreCase());
                        }
                        if(OldId.length() >= 1) {
                            criteria.add(Expression.eq("resourceId", Integer.valueOf(OldId)));
                        }
                        
                        //if subcriteria values are present from the form (for the "many" class),
                        //then include subcriteria search values in main search (the "one" class)
                        Criteria subCriteriaLanguagePairs = null;
                        if(!SourceId.equals("0") || !TargetId.equals("0")) {
                            subCriteriaLanguagePairs = criteria.createCriteria("LanguagePairs"); 
                        }                         
                        if(!SourceId.equals("0")) {
                            subCriteriaLanguagePairs.add(Expression.eq("sourceId", Integer.valueOf(SourceId)));
                        }
                        if(!TargetId.equals("0")) {
                            subCriteriaLanguagePairs.add(Expression.eq("targetId", Integer.valueOf(TargetId)));
                        }   
                        
                        if(!Status.equals("0")) {
                            criteria.add(Expression.eq("status", Status)); 
                        }
                        if(IncludeDoNot == null) {
                            criteria.add(Expression.eq("doNotUse", new Boolean(false))); 
                        }
                      
                        if(Translator != null) {
                            criteria.add(Expression.eq("translator", new Boolean(true))); 
                        }
                        if(Editor != null) {
                            criteria.add(Expression.eq("editor", new Boolean(true))); 
                        }
                        if(Proofreader != null) {
                            criteria.add(Expression.eq("proofreader", new Boolean(true))); 
                        }
                        if(Dtp != null) {
                            criteria.add(Expression.eq("dtp", new Boolean(true))); 
                        }
                        if(Icr != null) {
                            criteria.add(Expression.eq("icr", new Boolean(true))); 
                        }
                        
                        //if subcriteria values are present from the form (for the "many" class),
                        //then include subcriteria search values in main search (the "one" class)
//                        Criteria subCriteriaRateScoreLanguages = null;  
//                        boolean sourceSet = false;
//                        boolean targetSet = false;
//                        boolean rateScoreLanguagesCon = false;
//                        if(TRate.length() >= 1 || ERate.length() >= 1 || TERate.length() >= 1 || PRate.length() >= 1) {
//                            subCriteriaRateScoreLanguages = subCriteriaLanguagePairs.createCriteria("RateScoreLanguages");  
//                            rateScoreLanguagesCon = true;
//                            if(source != null) {
//                                subCriteriaRateScoreLanguages.add(Expression.eq("source", source));
//                                sourceSet = true;
//                            }
//                            if(target != null) {
//                                subCriteriaRateScoreLanguages.add(Expression.eq("target", target));
//                                targetSet = true;
//                            }
//                            
//                            Disjunction any = Expression.disjunction();
//                            
//                            if(TRate.length() >= 1) {
//                                Conjunction all = Expression.conjunction();
//                                all.add(Expression.gt("t", new Double(0)));
//                                all.add(Expression.le("t", Double.valueOf(TRate)));
//                                any.add(all);
//                            }
//                            if(ERate.length() >= 1) {
//                                Conjunction all = Expression.conjunction();
//                                all.add(Expression.gt("e", new Double(0)));
//                                all.add(Expression.le("e", Double.valueOf(ERate)));
//                                any.add(all);
//                            }
//                            if(TERate.length() >= 1) {
//                                Conjunction all = Expression.conjunction();
//                                all.add(Expression.gt("te", new Double(0)));
//                                all.add(Expression.le("te", Double.valueOf(TERate)));
//                                any.add(all);
//                            }
//                            if(PRate.length() >= 1) {
//                                Conjunction all = Expression.conjunction();
//                                all.add(Expression.gt("p", new Double(0)));
//                                all.add(Expression.le("p", Double.valueOf(PRate)));
//                                any.add(all);
//                            }  
//                            subCriteriaRateScoreLanguages.add(any);
//                        }
//                        //if subcriteria values are present from the form (for the "many" class),
//                        //then include subcriteria search values in main search (the "one" class)
//                        if(DtpRate.length() >= 1) {
//                            Criteria subCriteria = criteria.createCriteria("RateScoreDtps");                     
//                            if(DtpRate.length() >= 1) {
//                                subCriteria.add(Expression.gt("rate", new Double(0)));
//                                subCriteria.add(Expression.le("rate", Double.valueOf(DtpRate)));
//                            }                           
//                        }
//                        if(RateOldDb != null) {
//                            Disjunction any = Expression.disjunction();
//                                                     
//                            if(TRate.length() >= 1) {
//                                Conjunction all = Expression.conjunction();
//                                all.add(Expression.gt("t", new Double(0)));
//                                all.add(Expression.le("t", Double.valueOf(TRate)));
//                                any.add(all);
//                            }
//                            if(ERate.length() >= 1) {
//                                Conjunction all = Expression.conjunction();
//                                all.add(Expression.gt("e", new Double(0)));
//                                all.add(Expression.le("e", Double.valueOf(ERate)));
//                                any.add(all);
//                            }
//                            if(TERate.length() >= 1) {
//                                Conjunction all = Expression.conjunction();
//                                all.add(Expression.gt("te", new Double(0)));
//                                all.add(Expression.le("te", Double.valueOf(TERate)));
//                                any.add(all);
//                            }
//                            if(PRate.length() >= 1) {
//                                Conjunction all = Expression.conjunction();
//                                all.add(Expression.gt("p", new Double(0)));
//                                all.add(Expression.le("p", Double.valueOf(PRate)));
//                                any.add(all);
//                            }   
//                            criteria.add(any);
//                        }                        
                          
                        if(Specific.length > 0 && !Specific[0].equals("0")) { 
                            Criteria subCriteriaSpecificIndustries = criteria.createCriteria("SpecificIndustries"); 
                            Integer[] specificIds = new Integer[Specific.length];
                            for(int i = 0; i < specificIds.length; i++) {
                                specificIds[i] = new Integer(Specific[i]);
                            }
                            subCriteriaSpecificIndustries.add(Expression.in("specificIndustryId", specificIds));
                        }
                        if(General.length > 0 && !General[0].equals("0")) { 
                            Criteria subCriteriaIndustries = criteria.createCriteria("Industries");                          
                            Integer[] generalIds = new Integer[General.length];
                            for(int i = 0; i < generalIds.length; i++) {
                                generalIds[i] = new Integer(General[i]);
                            }
                            subCriteriaIndustries.add(Expression.in("industryId", generalIds));
                        }
                        
                                             
                        if(ScoreOldDb != null) {
                            Disjunction any = Expression.disjunction();
                            
                            if(ScoresLin[0] != null && ScoresLin[0].length() >= 1) {
                                any.add(Expression.ge("medicalScore", Double.valueOf(ScoresLin[0])));
                            }
                            if(ScoresLin[1] != null && ScoresLin[1].length() >= 1) {
                                any.add(Expression.ge("technicalScore", Double.valueOf(ScoresLin[1])));
                            }
                            if(ScoresLin[2] != null && ScoresLin[2].length() >= 1) {
                                any.add(Expression.ge("softwareScore", Double.valueOf(ScoresLin[2])));
                            }
                            if(ScoresLin[3] != null && ScoresLin[3].length() >= 1) {
                                any.add(Expression.ge("legalFinancialScore", Double.valueOf(ScoresLin[3])));
                            }   
                            criteria.add(any);
                        }
                        
                        if(!ProjectScoreGreater.equals("null")) {
                            criteria.add(Expression.ge("projectScoreAverage", Double.valueOf(ProjectScoreGreater)));
                        }
                        
                        if(UsesTrados != null) {
                            criteria.add(Expression.eq("usesTrados", new Boolean(true))); 
                        }
                        if(UsesDejavu != null) {
                            criteria.add(Expression.eq("usesDejavu", new Boolean(true))); 
                        }
                        if(UsesCatalyst != null) {
                            criteria.add(Expression.eq("usesCatalyst", new Boolean(true))); 
                        }
                        if(UsesSdlx != null) {
                            criteria.add(Expression.eq("usesSdlx", new Boolean(true))); 
                        }
                        if(UsesTransit != null) {
                            criteria.add(Expression.eq("usesTransit", new Boolean(true))); 
                        }
                        if(UsesOtherTool1.length() >= 1 || UsesOtherTool2.length() >= 1) {
                            Criteria subCriteria = criteria.createCriteria("ResourceTools"); 
                            Disjunction any = Expression.disjunction();
                            if(UsesOtherTool2.length() == 0) {
                                subCriteria.add(Expression.like("description", "%" + UsesOtherTool1 + "%").ignoreCase());
                            }
                            else {
                                any.add(Expression.like("description", "%" + UsesOtherTool1 + "%").ignoreCase());
                                any.add(Expression.like("description", "%" + UsesOtherTool2 + "%").ignoreCase());
                                subCriteria.add(any);
                            }                            
                        }
                        
                        if(City.length() >= 1) {
                            criteria.add(Expression.like("City", "%" + City + "%").ignoreCase());
                        }
                        if(!Country.equals("0")) {
                            criteria.add(Expression.eq("Country", Country));
                        }
                        
                        if(Resume.length() >= 1) {
                            criteria.add(Expression.like("resume", "%" + Resume + "%").ignoreCase());
                        }
                        
                        //sort results by id from old database
                        criteria.addOrder(Order.asc("resourceId"));
                        
                        //remove duplicates
                        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                       
                        //get results
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
        
        //search for resources (team members) given the criteria
        //this adds the oldRates AND oldScores to the search results
        public List getResourceSearchOldRatesScores(String FirstName, String LastName, String ContactFirstName, String ContactLastName, String Agency, String OldId, String SourceId,  String TargetId, String source, String target, String Status, String IncludeDoNot, String Translator, String Editor, String Proofreader, String Dtp, String Icr, String TRate, String ERate, String TERate, String PRate, String DtpRate, String RateOldDb, String[] Specific, String[] General, String[] ScoresLin, List rscs, String ScoreOldDb, String ProjectScoreGreater, String UsesTrados, String UsesSdlx,  String UsesDejavu, String UsesCatalyst,  String UsesOtherTool1, String UsesOtherTool2, String UsesTransit,  String City,  String Country, String Resume) 
	{
		
		Session session = ConnectionFactory.getInstance().getSession();
                Query query;
                List results = null;
                
		try
		{
                      	//retreive resources from database
                    
                        //this is the "one" class: it is the main criteria
                        Criteria criteria = session.createCriteria(Resource.class);
                        
                        criteria.add(Expression.like("firstName", "%" + FirstName + "%").ignoreCase());
                        criteria.add(Expression.like("lastName", "%" + LastName + "%").ignoreCase());
                        criteria.add(Expression.like("companyName", "%" + Agency + "%").ignoreCase());
                        //if subcriteria values are present from the form (for the "many" class),
                        //then include subcriteria search values in main search (the "one" class)
                        if(ContactFirstName.length() >= 1 || ContactLastName.length() >= 1) {
                            Criteria subCriteria = criteria.createCriteria("ResourceContacts");                        
                            subCriteria.add(Expression.like("firstName", "%" + ContactFirstName + "%").ignoreCase());
                            subCriteria.add(Expression.like("lastName", "%" + ContactLastName + "%").ignoreCase());
                        }
                        if(OldId.length() >= 1) {
                            criteria.add(Expression.eq("resourceId", Integer.valueOf(OldId)));
                        }
                        
                        //if subcriteria values are present from the form (for the "many" class),
                        //then include subcriteria search values in main search (the "one" class)
                        Criteria subCriteriaLanguagePairs = null;
                        if(!SourceId.equals("0") || !TargetId.equals("0")) {
                            subCriteriaLanguagePairs = criteria.createCriteria("LanguagePairs"); 
                        }                         
                        if(!SourceId.equals("0")) {
                            subCriteriaLanguagePairs.add(Expression.eq("sourceId", Integer.valueOf(SourceId)));
                        }
                        if(!TargetId.equals("0")) {
                            subCriteriaLanguagePairs.add(Expression.eq("targetId", Integer.valueOf(TargetId)));
                        }   
                        
                        if(!Status.equals("0")) {
                            criteria.add(Expression.eq("status", Status)); 
                        }
                        if(IncludeDoNot == null) {
                            criteria.add(Expression.eq("doNotUse", new Boolean(false))); 
                        }
                      
                        if(Translator != null) {
                            criteria.add(Expression.eq("translator", new Boolean(true))); 
                        }
                        if(Editor != null) {
                            criteria.add(Expression.eq("editor", new Boolean(true))); 
                        }
                        if(Proofreader != null) {
                            criteria.add(Expression.eq("proofreader", new Boolean(true))); 
                        }
                        if(Dtp != null) {
                            criteria.add(Expression.eq("dtp", new Boolean(true))); 
                        }
                        if(Icr != null) {
                            criteria.add(Expression.eq("icr", new Boolean(true))); 
                        }
                        
                        //if subcriteria values are present from the form (for the "many" class),
                        //then include subcriteria search values in main search (the "one" class)
                        Criteria subCriteriaRateScoreLanguages = null;  
                        boolean sourceSet = false;
                        boolean targetSet = false;
                        boolean rateScoreLanguagesCon = false;
                    
                        if(RateOldDb != null) {
                            Disjunction any = Expression.disjunction();
                                                     
                            if(TRate.length() >= 1) {
                                Conjunction all = Expression.conjunction();
                                all.add(Expression.gt("t", new Double(0)));
                                all.add(Expression.le("t", Double.valueOf(TRate)));
                                any.add(all);
                            }
                            if(ERate.length() >= 1) {
                                Conjunction all = Expression.conjunction();
                                all.add(Expression.gt("e", new Double(0)));
                                all.add(Expression.le("e", Double.valueOf(ERate)));
                                any.add(all);
                            }
                            if(TERate.length() >= 1) {
                                Conjunction all = Expression.conjunction();
                                all.add(Expression.gt("te", new Double(0)));
                                all.add(Expression.le("te", Double.valueOf(TERate)));
                                any.add(all);
                            }
                            if(PRate.length() >= 1) {
                                Conjunction all = Expression.conjunction();
                                all.add(Expression.gt("p", new Double(0)));
                                all.add(Expression.le("p", Double.valueOf(PRate)));
                                any.add(all);
                            }   
                            criteria.add(any);
                        }                        
                          
                        if(Specific.length > 0 && !Specific[0].equals("0")) { 
                            Criteria subCriteriaSpecificIndustries = criteria.createCriteria("SpecificIndustries"); 
                            Integer[] specificIds = new Integer[Specific.length];
                            for(int i = 0; i < specificIds.length; i++) {
                                specificIds[i] = new Integer(Specific[i]);
                            }
                            subCriteriaSpecificIndustries.add(Expression.in("specificIndustryId", specificIds));
                        }
                        if(General.length > 0 && !General[0].equals("0")) { 
                            Criteria subCriteriaIndustries = criteria.createCriteria("Industries");                          
                            Integer[] generalIds = new Integer[General.length];
                            for(int i = 0; i < generalIds.length; i++) {
                                generalIds[i] = new Integer(General[i]);
                            }
                            subCriteriaIndustries.add(Expression.in("industryId", generalIds));
                        }
                        
//                        boolean rateScoreScore = false;
//                        for(int i = 0; i < ScoresLin.length; i++) {
//                            if(ScoresLin[i] != null && ScoresLin[i].length() >= 1 && rateScoreLanguagesCon == false) {
//                                subCriteriaRateScoreLanguages = subCriteriaLanguagePairs.createCriteria("RateScoreLanguages");  
//                                rateScoreLanguagesCon = true;
//                                rateScoreScore = true;
//                            }                                
//                        }                          
//                        for(int i = 0; i < ScoresLin.length; i++) {
//                            if(ScoresLin[i] != null && ScoresLin[i].length() >= 1) {
//                                if(source != null && sourceSet == false) {
//                                    subCriteriaRateScoreLanguages.add(Expression.eq("source", source));
//                                    sourceSet = true;
//                                }
//                                if(target != null && targetSet == false) {
//                                    subCriteriaRateScoreLanguages.add(Expression.eq("target", target));
//                                    targetSet = true;
//                                }
//                            }
//                        }      
//                        ListIterator iterRscs = rscs.listIterator();
//                        Disjunction anyRateScore = Expression.disjunction();
//                        for(int i = 0; i < ScoresLin.length; i++) {
//                            RateScoreCategory rsc = (RateScoreCategory) iterRscs.next();
//                            if(ScoresLin[i] != null && ScoresLin[i].length() >= 1) {
//                               Conjunction all = Expression.conjunction();
//                               all.add(Expression.eq("specialty", rsc.getCategory()));
//                               all.add(Expression.ge("score", Double.valueOf(ScoresLin[i])));
//                               anyRateScore.add(all);
//                            }
//                        }
//                        if(rateScoreScore) {
//                            subCriteriaRateScoreLanguages.add(anyRateScore);
//                        }
                        
                        if(ScoreOldDb != null) {
                            Disjunction any = Expression.disjunction();
                            
                            if(ScoresLin[0] != null && ScoresLin[0].length() >= 1) {
                                any.add(Expression.ge("medicalScore", Double.valueOf(ScoresLin[0])));
                            }
                            if(ScoresLin[1] != null && ScoresLin[1].length() >= 1) {
                                any.add(Expression.ge("technicalScore", Double.valueOf(ScoresLin[1])));
                            }
                            if(ScoresLin[2] != null && ScoresLin[2].length() >= 1) {
                                any.add(Expression.ge("softwareScore", Double.valueOf(ScoresLin[2])));
                            }
                            if(ScoresLin[3] != null && ScoresLin[3].length() >= 1) {
                                any.add(Expression.ge("legalFinancialScore", Double.valueOf(ScoresLin[3])));
                            }   
                            criteria.add(any);
                        }
                        
                                          
                        if(!ProjectScoreGreater.equals("null")) {
                            criteria.add(Expression.ge("projectScoreAverage", Double.valueOf(ProjectScoreGreater)));
                        }
                        
                        if(UsesTrados != null) {
                            criteria.add(Expression.eq("usesTrados", new Boolean(true))); 
                        }
                        if(UsesDejavu != null) {
                            criteria.add(Expression.eq("usesDejavu", new Boolean(true))); 
                        }
                        if(UsesCatalyst != null) {
                            criteria.add(Expression.eq("usesCatalyst", new Boolean(true))); 
                        }
                        if(UsesSdlx != null) {
                            criteria.add(Expression.eq("usesSdlx", new Boolean(true))); 
                        }
                        if(UsesTransit != null) {
                            criteria.add(Expression.eq("usesTransit", new Boolean(true))); 
                        }
                        if(UsesOtherTool1.length() >= 1 || UsesOtherTool2.length() >= 1) {
                            Criteria subCriteria = criteria.createCriteria("ResourceTools"); 
                            Disjunction any = Expression.disjunction();
                            if(UsesOtherTool2.length() == 0) {
                                subCriteria.add(Expression.like("description", "%" + UsesOtherTool1 + "%").ignoreCase());
                            }
                            else {
                                any.add(Expression.like("description", "%" + UsesOtherTool1 + "%").ignoreCase());
                                any.add(Expression.like("description", "%" + UsesOtherTool2 + "%").ignoreCase());
                                subCriteria.add(any);
                            }                            
                        }
                        
                        if(City.length() >= 1) {
                            criteria.add(Expression.like("City", "%" + City + "%").ignoreCase());
                        }
                        if(!Country.equals("0")) {
                            criteria.add(Expression.eq("Country", Country));
                        }
                        
                        if(Resume.length() >= 1) {
                            criteria.add(Expression.like("resume", "%" + Resume + "%").ignoreCase());
                        }
                        
                        //sort results by id from old database
                        criteria.addOrder(Order.asc("resourceId"));
                        
                        //remove duplicates
                        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                       
                        //get results
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
                
        //return one todo list item with id given in argument
        public Todo getSingleTodo(Integer id)
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
			
                        results = session.find("from Todo as todo where todo.todoId = ?",
                                               new Object[] {id},
                                               new Type[] {Hibernate.INTEGER} );             
                        
                        if(results.isEmpty())
                            return null;
                        else {
                            Todo t = (Todo) results.get(0);
                                                        
                            return t;
                        }
			
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
	}  
        
    //delete one todo list item
    public void deleteTodo(Todo t)
    {

		Session session = ConnectionFactory.getInstance().getSession();
                
                Transaction tx = null;
                
		try
		{
                        
                        tx = session.beginTransaction();
                        
			session.delete(t);
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
        
        //return one resourceContact with name given in argument
        public ResourceContact getSingleResourceContact(Integer id)
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
			
                        results = session.find("from ResourceContact as resourceContact where resourceContact.resourceContactId = ?",
                                               new Object[] {id},
                                               new Type[] {Hibernate.INTEGER} );             
                        
                        if(results.isEmpty())
                            return null;
                        else {
                            ResourceContact rc = (ResourceContact) results.get(0);                           
                            return rc;
                        }
			
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
                
	}
        
        //return one languagePair with id given in argument
        public LanguagePair getSingleLanguagePair(Integer id)
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
			
                        results = session.find("from LanguagePair as languagePair where languagePair.languagePairId = ?",
                                               new Object[] {id},
                                               new Type[] {Hibernate.INTEGER} );             
                        
                        if(results.isEmpty())
                            return null;
                        else {
                            LanguagePair lp = (LanguagePair) results.get(0);                            
                            return lp;
                        }
			
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
        
        //place a new todo item in db
        //build one-to-many relationship between user and todo list item
        public Todo addTodo(User u, Todo t) {            
                Session session = ConnectionFactory.getInstance().getSession();                
                Transaction tx = null;
                
		try
		{
                        tx = session.beginTransaction();
                        u.getTodos().add(t);
                        t.setUser(u);
                        
			session.save(t);
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
        
    //run a report search
    public List runReport1(Client c, Date dateB, Date dateC) {
        
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;
        
        try {
            //retreive reports search from database
            
            Criteria criteria = session.createCriteria(Project.class);
            
            Criteria subCriteria = criteria.createCriteria("Company");
            subCriteria.add(Expression.eq("Company_name", c.getCompany_name()).ignoreCase());
            
            criteria.add(Expression.ge("startDate", dateB));
            
            criteria.add(Expression.le("startDate", dateC));
            
            
            criteria.addOrder(Order.desc("number"));
            
            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            
            results = criteria.list();
            
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
        
        if(results.isEmpty())
            return null;
        else
            return results;
    }
    
    //run a report search
    public List runReport2(Date dateA, Date dateB) {
        
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;
        
        try {
            //retreive reports search from database
            
            Criteria criteria = session.createCriteria(Project.class);
            
            criteria.add(Expression.ge("startDate", dateA));
            
            criteria.add(Expression.le("startDate", dateB));
//            criteria.add(Expression.or(Expression.eq("age", new Integer(24))),Expression.eq("age", new Integer(28)));
           
//            criteria.add(Expression.in("status", new String[] {"active","complete","onhold"}));
            criteria.addOrder(Order.desc("number"));
            
            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            
            results = criteria.list();
            
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
        
        if(results.isEmpty())
            return null;
        else
            return results;
    }
    
    //run a report search
    public List runReport3(Industry i, Date dateB, Date dateC) {
        
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;
        
        try {
            //retreive reports search from database
            
            Criteria criteria = session.createCriteria(Project.class);
            
            Criteria subCriteria = criteria.createCriteria("Company");
            Criteria sub2Criteria = subCriteria.createCriteria("Industry");
            sub2Criteria.add(Expression.eq("Description", i.getDescription()));
            
            criteria.add(Expression.ge("startDate", dateB));
            
            criteria.add(Expression.le("startDate", dateC)); 
            
            
            criteria.addOrder(Order.desc("number"));
            
            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            
            results = criteria.list();
            
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
        
        if(results.isEmpty())
            return null;
        else
            return results;
    }
    
    //run a report search
    public List runReport4(String pm, Date dateB, Date dateC) {
        
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;
        
        try {
            //retreive reports search from database
            
            Criteria criteria = session.createCriteria(Project.class);
            
            criteria.add(Expression.eq("pm", pm));
            
            criteria.add(Expression.ge("startDate", dateB));
            
            criteria.add(Expression.le("startDate", dateC));
            
            
            criteria.addOrder(Order.desc("number"));
            
            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            
            results = criteria.list();
            
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
        
        if(results.isEmpty())
            return null;
        else
            return results;
    }
    
    //run a report search
//    public List runReport5(String ae, Date dateB, Date dateC) {
//
//        Session session = ConnectionFactory.getInstance().getSession();
//        Query query;
//        List results = null;
//
//        try {
//            //retreive reports search from database
//
//            Criteria criteria = session.createCriteria(Project.class);
//
//            Criteria subCriteria = criteria.createCriteria("Company");
//            subCriteria.add(Expression.eq("Sales_rep", ae));
//
//            criteria.add(Expression.ge("startDate", dateB));
//
//            criteria.add(Expression.le("startDate", dateC));
//
//
//            criteria.addOrder(Order.desc("number"));
//
//            //remove duplicates
//            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//
//            results = criteria.list();
//
//        }
//        catch (HibernateException e) {
//            System.err.println("Hibernate Exception" + e.getMessage());
//            throw new RuntimeException(e);
//        }
//                /*
//                 * Regardless of whether the above processing resulted in an Exception
//                 * or proceeded normally, we want to close the Hibernate session.  When
//                 * closing the session, we must allow for the possibility of a Hibernate
//                 * Exception.
//                 *
//                 */
//        finally {
//            if (session != null) {
//                try {
//                    session.close();
//                }
//                catch (HibernateException e) {
//                    System.err.println("Hibernate Exception" + e.getMessage());
//                    throw new RuntimeException(e);
//                }
//
//            }
//        }
//
//        if(results.isEmpty())
//            return null;
//        else
//            return results;
//    }

        //run a report search
    public List runReport5(String ae, Date dateB, Date dateC,String dateCriteria) {

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;

        try {
            //retreive reports search from database

            Criteria criteria = session.createCriteria(Project.class);

            Criteria subCriteria = criteria.createCriteria("Company");
            subCriteria.add(Expression.eq("Sales_rep", ae));

            criteria.add(Expression.ge(dateCriteria, dateB));
            
            criteria.add(Expression.le(dateCriteria, dateC));


            criteria.addOrder(Order.desc("number"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            results = criteria.list();

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

        if(results.isEmpty())
            return null;
        else
            return results;
    }
    
    //run a report search
    public List runReport6(Date dateB, Date dateC) {
        
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;
        
        try {
            //retreive reports search from database
            
            Criteria criteria = session.createCriteria(Project.class);
            
            criteria.add(Expression.ge("startDate", dateB));
            
            criteria.add(Expression.le("startDate", dateC));
            
            
            criteria.addOrder(Order.desc("number"));
            
            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            
            results = criteria.list();
            
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
        
        if(results.isEmpty())
            return null;
        else
            return results;
    }
    
    //run a report search
    public List runReport6Active(Date dateB, Date dateC) {
        
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;
        
        try {
            //retreive reports search from database
            
            Criteria criteria = session.createCriteria(Project.class);
            
            criteria.add(Expression.eq("status", "active"));
            
            criteria.add(Expression.ge("startDate", dateB));
            
            criteria.add(Expression.le("startDate", dateC));
            
            
            criteria.addOrder(Order.desc("number"));
            
            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            
            results = criteria.list();
            
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
        
        if(results.isEmpty())
            return null;
        else
            return results;
    }
    
    //run a report search
    public List runReport6Complete(Date dateB, Date dateC) {
        
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;
        
        try {
            //retreive reports search from database
            
            Criteria criteria = session.createCriteria(Project.class);
            
            criteria.add(Expression.eq("status", "complete"));
            
            criteria.add(Expression.ge("startDate", dateB));
            
            criteria.add(Expression.le("startDate", dateC));
            
            
            criteria.addOrder(Order.desc("number"));
            
            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            
            results = criteria.list();
            
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
        
        if(results.isEmpty())
            return null;
        else
            return results;
    }
    
    //run a report search
    public List runReport7(String source, String target) {
        
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;
        
        try {
            //retreive reports search from database
            
            Criteria criteria = session.createCriteria(Project.class);
            
            Criteria subCriteria = criteria.createCriteria("SourceDocs");
            subCriteria.add(Expression.eq("language", source));
            
            Criteria sub2Criteria = subCriteria.createCriteria("TargetDocs");
            sub2Criteria.add(Expression.eq("language", target));
                        
            criteria.addOrder(Order.desc("number"));
            
            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            
            results = criteria.list();
            
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
        
        if(results.isEmpty())
            return null;
        else
            return results;
    }
    
    //run a report search
    public List runReport9(Double amountA, Double amountB, Date dateC, Date dateD) {
        
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;
        
        try {
            //retreive reports search from database
            
            Criteria criteria = session.createCriteria(Project.class);
            
            criteria.add(Expression.ge("projectAmount", amountA));
            
            criteria.add(Expression.le("projectAmount", amountB));
            
            criteria.add(Expression.ge("startDate", dateC));
            
            criteria.add(Expression.le("startDate", dateD));
            
            
            criteria.addOrder(Order.desc("number"));
            
            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            
            results = criteria.list();
            
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
        
        if(results.isEmpty())
            return null;
        else
            return results;
    }
    
    //run a report search
    public List runReport9Over(Double amountA, Date dateC, Date dateD) {
        
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;
        
        try {
            //retreive reports search from database
            
            Criteria criteria = session.createCriteria(Project.class);
            
            criteria.add(Expression.ge("projectAmount", amountA));
            
            criteria.add(Expression.ge("startDate", dateC));
            
            criteria.add(Expression.le("startDate", dateD));
            
            
            criteria.addOrder(Order.desc("number"));
            
            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            
            results = criteria.list();
            
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
        
        if(results.isEmpty())
            return null;
        else
            return results;
    }
    
    //run a report search
    public List runReport10(Date dateA, Date dateB) {
        
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;
        
        try {
            //retreive reports search from database
            
            Criteria criteria = session.createCriteria(Quality.class);
            
            criteria.add(Expression.ge("dateClosed", dateA));
            
            criteria.add(Expression.le("dateClosed", dateB));
            
            
            criteria.addOrder(Order.asc("dateClosed"));
            
            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            
            results = criteria.list();
            
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
        
        if(results.isEmpty())
            return null;
        else
            return results;
    }
    
    //run a report search
    public List runReport11(Date dateA, Date dateB) {
        
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;
        
        try {
            //retreive reports search from database
            
            Criteria criteria = session.createCriteria(UserAbscence.class);
            
            Conjunction all1 = Expression.conjunction();
            Conjunction all2 = Expression.conjunction(); 
            Conjunction all3 = Expression.conjunction();            
            Disjunction any = Expression.disjunction();
            
            all1.add(Expression.ge("abscence_date", dateA));
            all1.add(Expression.le("abscence_date", dateB));
            any.add(all1);
            
            all2.add(Expression.ge("abscence_date", dateA));
            all2.add(Expression.le("abscence_date", dateB));
            any.add(all2);
            
            all3.add(Expression.lt("abscence_date", dateA));
            all3.add(Expression.gt("abscence_date", dateB));
            any.add(all3);
            
            criteria.add(any);     
            
            criteria.addOrder(Order.asc("abscence_date"));
            
            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            
            results = criteria.list();
            
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
        
        if(results.isEmpty())
            return null;
        else
            return results;
    }
    
    //run a report search
    public List runReport13(Date yearA, Date yearB) {
        
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;
        
        try {
            //retreive reports search from database
            
            Criteria criteria = session.createCriteria(Project.class);
                        
            criteria.add(Expression.eq("status", "active"));
            
            
            criteria.add(Expression.ge("startDate", yearA));
            
            criteria.add(Expression.le("startDate", yearB));
            
            
            criteria.addOrder(Order.desc("number"));
            
            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            
            results = criteria.list();
            
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
        
        if(results.isEmpty())
            return null;
        else
            return results;
    }
    
    //run a report search
    public List runReport14(Date dateC, Date dateD) {
        
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;
        
        try {
            //retreive reports search from database
            
            Criteria criteria = session.createCriteria(Project.class);
                        
            criteria.add(Expression.ge("startDate", dateC));
            
            criteria.add(Expression.le("startDate", dateD));
            
            
            criteria.addOrder(Order.desc("number"));
            
            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            
            results = criteria.list();
            
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
        
        if(results.isEmpty())
            return null;
        else
            return results;
    }
    
        //place a new announcement in db
        public Announcement addAnnouncement(Announcement a) {            
                Session session = ConnectionFactory.getInstance().getSession();                
                Transaction tx = null;
                
		try
		{
                        tx = session.beginTransaction();
                        
                        session.save(a);
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
                return a;            
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
        
    //build the link between resource and resourceTool
    public Integer linkResourceResourceTool(Resource r, ResourceTool rt) {
        Session session = ConnectionFactory.getInstance().getSession();
        Integer id = null;
        Transaction tx = null;
        
        try {
            
            tx = session.beginTransaction();
            
            //link resource and resourceTool
            rt.setResource(r);
            r.getResourceTools().add(rt);
            
            session.saveOrUpdate(rt);
            
            tx.commit();
            return rt.getResourceToolId();
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
    
    //build the link between resource and resourceContact
    public Integer linkResourceResourceContact(Resource r, ResourceContact rc) {
        Session session = ConnectionFactory.getInstance().getSession();
        Integer id = null;
        Transaction tx = null;
        
        try {
            
            tx = session.beginTransaction();
            
            //link resource and resourceContact
            rc.setResource(r);
            r.getResourceContacts().add(rc);
            
            session.saveOrUpdate(rc);
            
            tx.commit();
            return rc.getResourceContactId();
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
    
    //build the link between resource and languagePair
    public Integer linkResourceLanguagePair(Resource r, LanguagePair lp) {
        Session session = ConnectionFactory.getInstance().getSession();
        Integer id = null;
        Transaction tx = null;
        
        try {
            
            tx = session.beginTransaction();
            
            //link resource and languagePair
            lp.setResource(r);
            r.getLanguagePairs().add(lp);
            
            session.saveOrUpdate(lp);
            
            tx.commit();
            return lp.getLanguagePairId();
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
    
    //build the link between resource and rateScoreDtp
    public Integer linkResourceRateScoreDtp(Resource r, RateScoreDtp rsd) {
        Session session = ConnectionFactory.getInstance().getSession();
        Integer id = null;
        Transaction tx = null;
        
        try {
            
            tx = session.beginTransaction();
            
            //link resource and rateScoreDtp
            r.getRateScoreDtps().add(rsd);
            rsd.setResource(r);
            
            session.saveOrUpdate(rsd);
            
            tx.commit();
            return rsd.getRateScoreDtpId();
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
        
    //build the link between languagePair and rateScoreLanguage
    public Integer linkLanguagePairRateScoreLanguage(LanguagePair lp, RateScoreLanguage rsl) {
        Session session = ConnectionFactory.getInstance().getSession();
        Integer id = null;
        Transaction tx = null;
        
        try {
            
            tx = session.beginTransaction();
            
            //link languagePair and rateScoreLanguage
            lp.getRateScoreLanguages().add(rsl);
            rsl.setLanguagePair(lp);
            
            session.saveOrUpdate(rsl);
            
            tx.commit();
            return rsl.getRateScoreLanguageId();
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
    
    //build the link (many-to-many) between resource and specificIndustry
    public boolean linkResourceSpecificIndustry(Resource r, SpecificIndustry si) {
        Session session = ConnectionFactory.getInstance().getSession();
        Integer id = null;
        Transaction tx = null;
        
        try {
            
            tx = session.beginTransaction();
            
            //link resource and specificIndustry
            r.getSpecificIndustries().add(si);
            si.getResources().add(r);
            
            session.saveOrUpdate(r);
            
            tx.commit();
            return true;
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
    
    //remove the link (many-to-many) between resource and industry
    public void unlinkResourceIndustry(Resource r, Industry[] generals, Integer[] generalIds) {
        List deleteList = new ArrayList();
        
            //search for repeats
            for(int i = 0; i < generalIds.length; i++) {
                boolean remove = true;
                for(int j = 0; j < generals.length; j++) {
                    if(generalIds[i].equals(generals[j].getIndustryId())) {
                        remove = false;
                    }
                }
                if(remove) {
                    Industry i2 = ClientService.getInstance().getSingleIndustry(generalIds[i]);
                    for(Iterator iter = r.getIndustries().iterator(); iter.hasNext();) {
                        i2 = (Industry) iter.next();
                        if(i2.getIndustryId().equals(generalIds[i])) {
                            deleteList.add(i2);
                        }
                    }                    
                }
            }
            
            //delete repeats
            for(ListIterator iter = deleteList.listIterator(); iter.hasNext();) {
                Industry i3 = (Industry) iter.next();
                unlinkResourceIndustryDelete(r, i3);
            }      
        
    }
    
    //remove one link (many-to-many) between resource and industry
    public void unlinkResourceIndustryDelete(Resource r, Industry i) {
        Session session = ConnectionFactory.getInstance().getSession();
        Transaction tx = null;
        try {
            
            tx = session.beginTransaction();
            
             //search for the one link to remove
             for(Iterator iter = r.getIndustries().iterator(); iter.hasNext();) {
                        Industry i2 = (Industry) iter.next();
                        if(i2.getIndustryId().equals(i.getIndustryId())) {
                            r.getIndustries().remove(i2);
                            break;
                        }
              }                    
               
            session.saveOrUpdate(r);
            
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
    
    //remove the link (many-to-many) between resource and specific industry
    public void unlinkResourceSpecificIndustry(Resource r, SpecificIndustry[] generals, Integer[] generalIds) {
        List deleteList = new ArrayList();
        
            //search for repeats
            for(int i = 0; i < generalIds.length; i++) {
                boolean remove = true;
                for(int j = 0; j < generals.length; j++) {
                    if(generalIds[i].equals(generals[j].getSpecificIndustryId())) {
                        remove = false;
                    }
                }
                if(remove) {
                    SpecificIndustry i2 = ClientService.getInstance().getSingleSpecificIndustry(generalIds[i]);
                    for(Iterator iter = r.getSpecificIndustries().iterator(); iter.hasNext();) {
                        i2 = (SpecificIndustry) iter.next();
                        if(i2.getSpecificIndustryId().equals(generalIds[i])) {
                            deleteList.add(i2);
                        }
                    }                    
                }
            }
            
            //delete repeats
            for(ListIterator iter = deleteList.listIterator(); iter.hasNext();) {
                SpecificIndustry i3 = (SpecificIndustry) iter.next();
                unlinkResourceSpecificIndustryDelete(r, i3);
            }      
        
    }
    
    //remove one link (many-to-many) between resource and industry
    public void unlinkResourceSpecificIndustryDelete(Resource r, SpecificIndustry i) {
        Session session = ConnectionFactory.getInstance().getSession();
        Transaction tx = null;
        try {
            
            tx = session.beginTransaction();
            
            //search for the one link to remove
            for(Iterator iter = r.getSpecificIndustries().iterator(); iter.hasNext();) {
                        SpecificIndustry i2 = (SpecificIndustry) iter.next();
                        if(i2.getSpecificIndustryId().equals(i.getSpecificIndustryId())) {
                            r.getSpecificIndustries().remove(i2);
                            break;
                        }
             }                    
               
            session.saveOrUpdate(r);
            
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
    
    //build the link (many-to-many) between resource and industry
    public boolean linkResourceIndustry(Resource r, Industry i) {
        Session session = ConnectionFactory.getInstance().getSession();
        Integer id = null;
        Transaction tx = null;
        
        try {
            
            tx = session.beginTransaction();
            
            //link resource and industry
            r.getIndustries().add(i);
            i.getResources().add(r);
            
            session.saveOrUpdate(r);
            
            tx.commit();
            return true;
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
    
        //provide a list of statuses for forms (in clients)
        public String[] getStatuses() {
            String Status[] = {"Prospect", "Active", "Lost"};
            return Status;
        }

        public List getMostUsedVendor() {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
String queryString="SELECT COUNT(resource.ID_Resource) AS cnt,"
    +" resource.firstName , resource.lastName"
    +", resource.companyName    , SUM(REPLACE(COALESCE (lintask.dollarTotal,0),',','')) AS fee"
    +", AVG(lintask.score) AS score    , SUM(lintask.wordTotal) AS word"
    +", resource.ID_Resource "
                + "FROM"
     +"app.project.Lintask lintask INNER JOIN app.resource.Resource resource ON lintask.personName = resource.ID_Resource"
    +" INNER JOIN app.project.Targetdoc targetdoc ON lintask.ID_TargetDoc = targetdoc.ID_TargetDoc"
     +" INNER JOIN app.project.Sourcedoc sourcedoc ON targetdoc.ID_SourceDoc = sourcedoc.ID_SourceDoc"
    +" INNER JOIN app.project.Project project ON sourcedoc.ID_Project = project.ID_Project"
+" GROUP BY resource.ID_Resource  ORDER BY word DESC LIMIT 0,50";
        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             *///select pathname from Upload_Doc as Upload_Doc where Upload_Doc.QuoteID ="+ CQuote
            query = session.createQuery(queryString);
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

