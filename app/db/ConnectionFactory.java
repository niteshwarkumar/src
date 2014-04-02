//ConnectionFactory.java contains the code that provides
//pooled connections to the mysql db.
//These connections are refered to as "sessions."
//There is one session per user per opperation.
//For example, when a user creates a new client,
//one session is needed to write the new client
//to the db.  After the client is written to
//the db, the session is discarded.

package app.db;

import app.dtpScheduler.DTPScheduler;
import app.dtpScheduler.ENGScheduler;
import net.sf.hibernate.*;
import net.sf.hibernate.cfg.Configuration;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import app.user.*;
import app.client.*;
import app.project.*;
import app.quote.*;
import app.resource.*;
import app.menu.*;
import app.admin.*;
import app.hrAdmin.*;
import app.tracker.*;
import app.qms.*;
import app.inteqa.*;
import app.comm.*;


public class ConnectionFactory {
    
    private static ConnectionFactory instance = null;
    private SessionFactory sessionFactory = null;
    
    private ConnectionFactory() {
        // Establish SessionFactory for Hibernate
        try {
            
                        /*
                         * The Hibernate Configuration will contain all the classes to be
                         * persisted by Hibernate.  For each class persisted, Hibernate will
                         * expect to find a ClassName.hbm.xml file in the same location as the
                         * class file.  This XML file will define the mapping between the Java
                         * object and the database.
                         *
                         * To add additional classes to the configuration, you may cascade the
                         * method calls thusly:
                         *
                         * Configuration cfg = new Configuration().
                         *                         addClass(Foo.class).
                         *                         addClass(Bar.class);
                         *
                         */
            
            //add the java objects to the configuration, which can now be used to read/write to/from db
            Configuration cfg = new Configuration()
            .addClass(Hardware.class)
            .addClass(Sw.class)
            .addClass(Evaluation.class)
            .addClass(User.class)
            .addClass(Todo.class)
            .addClass(Client_Quote.class)
            .addClass(Technical.class)
            .addClass(Client.class)
            .addClass(Blog.class)
            .addClass(app.resource.Mechanical.class)
            .addClass(ClientLanguagePair.class)
            .addClass(ClientOtherRate.class)
            .addClass(ClientContact.class)
            .addClass(Industry.class)
            .addClass(SpecificIndustry.class)
            .addClass(Quote1.class)
            .addClass(File.class)
            .addClass(Project.class)
            .addClass(ClientInvoice.class)
            .addClass(Change1.class)
            .addClass(Currency1.class)
            .addClass(Quality.class)
            .addClass(Inspection.class)
            .addClass(SourceDoc.class)
            .addClass(Language.class)
            .addClass(TargetDoc.class)
            .addClass(LinTask.class)
            .addClass(EngTask.class)
            .addClass(DtpTask.class)
            .addClass(OthTask.class)
            .addClass(Location.class)
            .addClass(Position1.class)
            .addClass(Department.class)
            .addClass(Training.class)
            .addClass(app.hrAdmin.TrainingDetail.class)
            .addClass(Away.class)
            .addClass(PerformanceReview.class)
            .addClass(Privilege.class)
            .addClass(PrivilegeList.class)
            .addClass(Resource.class)
            .addClass(Unavail.class)
            .addClass(ResourceContact.class)
            .addClass(LanguagePair.class)
            .addClass(RateScoreLanguage.class)
            .addClass(RateScoreDtp.class)
            .addClass(RateScoreCategory.class)
            .addClass(ResourceTool.class)
            .addClass(ProjectScore.class)
            .addClass(SavedSearch.class)
            .addClass(ProjectCart.class)
            .addClass(Announcement.class)
            .addClass(AdminOption.class)
            .addClass(DTPScheduler.class)
            .addClass(ENGScheduler.class)
            .addClass(MilestoneLanguage.class)
            .addClass(AdditionalDeliveryDate.class)
            .addClass(UserAbscence.class)
            .addClass(app.extjs.vo.Product.class)
            .addClass(app.extjs.vo.Upload_Doc.class)
            .addClass(app.extjs.vo.Dropdown.class)
            .addClass(app.extjs.vo.Communication.class)
            .addClass(app.extjs.vo.ClientService.class)
            .addClass(app.extjs.vo.Regulatory.class)
            .addClass(app.extjs.vo.Audit.class)
            .addClass(app.extjs.vo.BillingRequirement.class)
            .addClass(app.extjs.vo.LinStylesheets.class)
            .addClass(app.extjs.vo.TechStylesheets.class)
            .addClass(app.extjs.vo.ClientLocation.class)
            .addClass(app.extjs.vo.ProjectInformal.class)
            .addClass(app.extjs.vo.Incremental.class)
            .addClass(app.extjs.vo.ProjectRequirements.class)
            .addClass(app.resource.MADtpEng.class)
            .addClass(app.resource.MAExpert.class)
            .addClass(app.resource.MALinguistic.class)
            .addClass(app.resource.MAOther.class)
            .addClass(VersionControl.class)
            .addClass(app.tools.Conversion.class)
            .addClass(QMSAudit.class)
            .addClass(QMSReportUpload.class)
            .addClass(ClientFeedback.class)
            .addClass(INBasics.class)
            .addClass(INDtp.class)
            .addClass(INEngineering.class)
            .addClass(INReport.class)
            .addClass(INDelivery.class)
            .addClass(INDeliveryReq.class)
            .addClass(Capa.class)
            .addClass(CapaId.class)
            .addClass(Client_Icr.class)
            .addClass(app.admin.PmFee.class)
            .addClass(app.admin.Ticker.class)
            .addClass(app.extjs.vo.DefaultValue.class)
            .addClass(LibraryUpload.class)
            .addClass(QMSLibrary.class)
            .addClass(QMSLibraryHistory.class)
            .addClass(QuotePriority.class)
            .addClass(DepartmentUser.class)
            .addClass(QualityReport.class)
            .addClass(TrainingNotify.class)
            .addClass(AssesEval.class)
            .addClass(TrainingInitial.class)
            .addClass(TrainingInitialAdmin.class)
            .addClass(Project_Technical.class)
            .addClass(INSourceFile.class)
            .addClass(Couples.class)
            .addClass(INQualityFeedback.class)
            .addClass(Excelnetissue.class)
            .addClass(QMSAction.class);

            
            sessionFactory = cfg.buildSessionFactory();
            
        } catch (MappingException e) {
                        /*
                         * Upon encountering a Hibernate generated Exception, we are throwing
                         * an unchecked RuntimeExcpetion that will cause the user's
                         * request to fail.
                         *
                         */
            System.err.println("Mapping Exception" + e.getMessage());
            throw new RuntimeException(e);
        } catch (HibernateException e) {
                        /*
                         * Upon encountering a Hibernate generated Exception, we are throwing
                         * an unchecked RuntimeExcpetion that will cause the user's request to fail.
                         *
                         */
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        }
        
    }
    
    /**
     * getInstance() returns the instance of the ConnectionFactory singleton.
     *
     * Example call to retrieve session:
     *
     * <pre>
     * Session session = ConnectionFactory.getInstance().getSession();
     * </pre>
     *
     * @return Instance of the <code>ConnectionFactory</code> singleton.
     */
    public static synchronized ConnectionFactory getInstance() {
                /*
                 * If the instance of the Singleton has not been created, create and
                 * return.
                 */
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }
    
    /**
     * getSession() returns a Hibernate <code>Session</code>
     *
     * @return <code>Session</code> retrieved from Hibernate <Code>SessionFactory</code>
     */
    public Session getSession() {
        try {
                        /*
                         * Use the Hibernate Session Factory to return an open session to the caller.
                         */
            Session s = sessionFactory.openSession();
            return s;
        } catch (HibernateException e) {
                        /*
                         * Upon encountering a Hibernate generated Exception, we are throwing
                         * an unchecked RuntimeExcpetion that will cause the user's request to fail.
                         *
                         */
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    //get a session during rebinding of lazy collections
    public Session getASession(Object anObject) {
        try {
                        /*
                         * Use the Hibernate Session Factory to return an open session to the caller.
                         */
            Session s = sessionFactory.openSession();
            rebindSession(anObject, s);
            return s;
        } catch (HibernateException e) {
                        /*
                         * Upon encountering a Hibernate generated Exception, we are throwing
                         * an unchecked RuntimeExcpetion that will cause the user's request to fail.
                         *
                         */
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    //Reassociates an object with a session so lazy loaded collections will still work
    public void rebindSession(Object anObject, Session aSession) throws RuntimeException {
        if (!aSession.contains(anObject)) {
            try {
                aSession.lock(anObject, LockMode.NONE);
            } catch (HibernateException e) {
                System.err.println("Hibernate Exception" + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }
    
    //close a session
    public void closeSession(Session aSession) {
        if (aSession != null) {
            try {
                
                aSession.close();
            } catch (HibernateException e) {
                System.err.println("Hibernate Exception" + e.getMessage());
                throw new RuntimeException(e);
            }
            
        }
    }
    
//        //reconnect all objects in a set
//        public void rebindSet(Set s) {
//            if (s != null)
//			{
//				try
//				{
//
//					for(Iterator iter = s.iterator(); iter.hasNext();) {
//
//                                        }
//				}
//				catch (HibernateException e)
//				{
//					System.err.println("Hibernate Exception" + e.getMessage());
//					throw new RuntimeException(e);
//				}
//
//			}
//        }
    
}
