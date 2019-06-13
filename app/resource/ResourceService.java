//ResourceService.java contains the db code related to resource to actually read/write
//to/from db. ResourceService handles resource module information like language pairs
//for a particular linguist
package app.resource;

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
import app.extjs.helpers.HrHelper;
import app.extjs.helpers.TeamHelper;
import app.extjs.vo.Upload_Doc;
import app.project.*;
import app.standardCode.StandardCode;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import net.sf.hibernate.Criteria;
import net.sf.hibernate.expression.*;
import org.json.JSONArray;
import org.json.JSONObject;
import sun.misc.BASE64Encoder;

public class ResourceService {

    private static ResourceService instance = null;

    public ResourceService() {
    }

    //return the instance of ResourceService
    public static synchronized ResourceService getInstance() {
        /*
     * Creates the Singleton instance, if needed.
     *
         */
        if (instance == null) {
            instance = new ResourceService();
        }
        return instance;
    }

    //add a new resource (team member) to the database
    public Integer addResource(Resource r) {

        Session session = ConnectionFactory.getInstance().getSession();
        Integer resourceId = null;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.save(r);

            tx.commit();

            resourceId = r.getResourceId();
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
        return resourceId;
    }

    public Evaluation addEvaluation(Evaluation r) {

        Session session = ConnectionFactory.getInstance().getSession();
        Integer resourceId = null;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(r);

            tx.commit();

            // resourceId = r.getResourceId();
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
        return null;
    }

    public Couples addCouples(Couples r) {
        Session session = ConnectionFactory.getInstance().getSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(r);

            tx.commit();

            // resourceId = r.getResourceId();
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
        return null;
    }

    public Couples unlinkCouples(int id) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from couples where resourceid = " + id);
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
        }
        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from couples where coupleid = " + id);
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

        return null;
    }

    public Evaluation unlinkEval(String rid) {

        Session session = ConnectionFactory.getInstance().getSession();
        Integer resourceId = null;
        resourceId = Integer.parseInt(rid);
        Transaction tx = null;
        Query query;
        try {
            query = session.createQuery("delete evaluation from app.resource.Evaluation evaluation where evaluation.resourceId=" + resourceId);
            return null;

            // resourceId = r.getResourceId();
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
        // return null;

    }

    public Evaluation addAssesEvaluation(AssesEval r) {

        Session session = ConnectionFactory.getInstance().getSession();
        Integer resourceId = null;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(r);

            tx.commit();

            // resourceId = r.getResourceId();
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
        return null;
    }

    public Evaluation unlinkAssesEval(String rid) {

        Session session = ConnectionFactory.getInstance().getSession();
        Integer resourceId = null;
        resourceId = Integer.parseInt(rid);
        Transaction tx = null;
        Query query;
        try {
            query = session.createQuery("delete asseseval from app.resource.AssesEval asseseval where asseseval.resourceId=" + resourceId);
            return null;

            // resourceId = r.getResourceId();
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
        // return null;

    }

    public List getTechnicalScore(String dropdown, String resource) {

        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        Query query;
        try {

            // results = session.find("from evaluation as evaluation where evaluation.evaluation_area =? and evaluation.resourceId=?",
            //                       new Object[] {dropdown,resource},
            //                       new Type[] {Hibernate.STRING,Hibernate.STRING} );
            if (dropdown == null) {
                query = session.createQuery("select evaluation from app.resource.Evaluation evaluation where evaluation.resourceId='" + resource + "'");
            } else {
                query = session.createQuery("select evaluation from app.resource.Evaluation evaluation where evaluation.evaluation_area = '" + dropdown + "' and evaluation.resourceId='" + resource + "'");
            }
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

    //check team unavailability
    public List checkAvail(Date dateA, Date dateB) {

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;

        try {
            //retreive reports search from database

            Criteria criteria = session.createCriteria(Unavail.class);

            Conjunction all1 = Expression.conjunction();
            Conjunction all2 = Expression.conjunction();
            Conjunction all3 = Expression.conjunction();
            Disjunction any = Expression.disjunction();

            all1.add(Expression.ge("startDate", dateA));
            all1.add(Expression.le("startDate", dateB));
            any.add(all1);

            all2.add(Expression.ge("endDate", dateA));
            all2.add(Expression.le("endDate", dateB));
            any.add(all2);

            all3.add(Expression.lt("startDate", dateA));
            all3.add(Expression.gt("endDate", dateB));
            any.add(all3);

            criteria.add(any);

            criteria.addOrder(Order.asc("startDate"));

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

    //add a new rateScoreCategory to the database
    public Integer addRateScoreCategory(RateScoreCategory r) {

        Session session = ConnectionFactory.getInstance().getSession();
        Integer rateScoreCategoryId = null;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.save(r);

            tx.commit();

            rateScoreCategoryId = r.getRateScoreCategoryId();
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
        return rateScoreCategoryId;
    }

    //add a new unavail to the database
    public Integer addUnavail(Unavail a, Resource r) {

        Session session = ConnectionFactory.getInstance().getSession();
        Integer unavailId = null;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            r.getUnavails().add(a);
            a.setResource(r);

            session.save(a);

            tx.commit();

            unavailId = a.getUnavailId();
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
        return unavailId;
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

    //update an existing resource in database
    public Resource updateResource(Resource r) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(r);

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
        return r;
    }

    //update an existing unavail in database
    public Unavail updateUnavail(Unavail r) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(r);

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
        return r;
    }

    //update an existing rateScoreCategory in database
    public RateScoreCategory updateRateScoreCategory(RateScoreCategory r) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(r);

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
        return r;
    }

    //delete one resource
    public void deleteResource(Resource object) {

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

    //delete one unavail
    public void deleteUnavail(Unavail object) {

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

    //delete one rateScoreCategory
    public void deleteRateScoreCategory(RateScoreCategory object) {

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

    //delete one ratescorelanguage
    public void deleteRateScoreLanguage(RateScoreLanguage object) {

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

    //update an existing resourceContact in database
    public ResourceContact updateResourceContact(ResourceContact rc) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(rc);

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
        return rc;
    }

    //update an existing languagePair in database
    public LanguagePair updateLanguagePair(LanguagePair lp) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(lp);

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
        return lp;
    }

    //update an existing ratescorelanguage in database
    public RateScoreLanguage updateRateScoreLanguage(RateScoreLanguage rsl) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(rsl);

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
        return rsl;
    }

    //update an existing rateScoreDtp in database
    public RateScoreDtp updateRateScoreDtp(RateScoreDtp rsd) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(rsd);

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
        return rsd;
    }

    //update an existing resourceTool in database
    public ResourceTool updateResourceTool(ResourceTool rt) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(rt);

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
        return rt;
    }

    //update an existing resourceTool in database
    public ResourceTool removeResourceTool(ResourceTool rt) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(rt);

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
        return rt;
    }

    //get all resources
    public List getResourceList() {
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
            query = session.createQuery("select resource from app.resource.Resource resource order by resource.firstName,resource.companyName");
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
    //get all resources

    public List getUnApprovedResourceList() {
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
            query = session.createQuery("select resource from app.resource.Resource resource where enteredById = 'external' and isEnabled = 0  order by resource.firstName,resource.companyName");
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

    //get all resources
    public List getResourceListAbc() {
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
            query = session.createQuery("select resource from app.resource.Resource resource order by resource.lastName, resource.firstName, resource.companyName");
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

    public List getPendingEvaluationList() {
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
            query = session.createQuery("select RateScoreLanguage from app.resource.RateScoreLanguage RateScoreLanguage where RateScoreLanguage.evaluationSent is not NULL and RateScoreLanguage.evaluatedDate is NULL ");
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

    //get all agencies
    public List getPendingAssessmentList() {
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
            query = session.createQuery("select RateScoreLanguage from app.resource.RateScoreLanguage RateScoreLanguage where RateScoreLanguage.assesmentSent is not NULL and RateScoreLanguage.assesmentReceived is NULL ");
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

    //get all rateScoreCategories
    public List getRateScoreCategoryList() {
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
            query = session.createQuery("select rateScoreCategory from app.resource.RateScoreCategory rateScoreCategory");
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

    //get all agencies
    public List getResourceCompanyList() {
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
            query = session.createQuery("select resource from app.resource.Resource resource where resource.agency = true order by resource.companyName");
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

    //search for resources (team members) given the criteria
    //this is the main search without including old db rates and scores
    public List getResourceSearch(String clientName, String FirstName, String LastName, String singleCompanyName, String ContactFirstName,
            String ContactLastName, String Agency, String OldId, String SourceId, String TargetId, String source, String target, String Status,
            String IncludeDoNot, String isagency, String Translator, String Editor, String Proofreader, String Evaluator, String Dtp, String Icr, String TRate,
            String ERate, String TERate, String PRate, String DtpRate, String dtpSource, String dtpTarget, String RateOldDb, String[] Specific,
            String[] General, String[] ScoresLin, List rscs, String ScoreOldDb, String ProjectScoreGreater, String UsesTrados, String UsesSdlx,
            String UsesDejavu, String UsesCatalyst, String UsesOtherTool1, String UsesOtherTool2, String UsesTransit, String City, String Country,
            String Resume, String other, String tne, String consultant, String partner, String engineering, String businesssuport, String fqa,
            String Quality, String Interpreting, String Expert, String RiskRating, String informationTechnology, String humanResource, String office,
            String sales, String accounting, String bsdOther, String prodOther, String postEditing, String Note, String medicalScore, String technicalScore,
            String softwareScore, String legalScore, String marketingScore, HashMap<String, String> resourceMap) {

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;
        List resultsTeamClient = new ArrayList();
        String teamClientSearchParam = "";
        try {

            String secondry = StandardCode.getInstance().noNull(resourceMap.get("secondary"));
            String teamClient = StandardCode.getInstance().noNull(resourceMap.get("teamClient"));
            String level = StandardCode.getInstance().noNull(resourceMap.get("level"));
            String primry = StandardCode.getInstance().noNull(resourceMap.get("primary"));
            String primaryCount = StandardCode.getInstance().noNull(resourceMap.get("primaryCount"));
            String secondaryCount = StandardCode.getInstance().noNull(resourceMap.get("secondaryCount"));
            teamClientSearchParam = secondry.concat(teamClient).concat(level).concat(primry).concat(primaryCount).concat(secondaryCount);
            if (teamClientSearchParam.length() >= 1) {

                Criteria criteria = session.createCriteria(ResourceClient.class);

                if (secondry.length() >= 1) {
                    criteria.add(Expression.like("secondry", secondry).ignoreCase());
                }
                if (primry.length() >= 1) {
                    criteria.add(Expression.like("primry", primry).ignoreCase());
                }
                if (teamClient.length() >= 1) {
                    criteria.add(Expression.like("client", teamClient).ignoreCase());
                }
                if (level.length() >= 1) {
                    criteria.add(Expression.like("level", level).ignoreCase());
                }
                if (primaryCount.length() >= 1) {
                    criteria.add(Expression.gt("primaryCount", Integer.valueOf(primaryCount)));
                }
                if (secondaryCount.length() >= 1) {
                    criteria.add(Expression.gt("secondryCount", Integer.valueOf(secondaryCount)));
                }

                criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                resultsTeamClient = criteria.list();
            }
        } catch (HibernateException e) {
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
        session = ConnectionFactory.getInstance().getSession();
        try {
            //retreive resources from database

            //this is the "one" class: it is the main criteria
            Criteria criteria = session.createCriteria(Resource.class);

            if (FirstName.length() >= 1) {
                criteria.add(Expression.like("firstName", "%" + FirstName + "%").ignoreCase()).addOrder(Order.asc("firstName"));
            }
            if (LastName.length() >= 1) {
                criteria.add(Expression.like("lastName", "%" + LastName + "%").ignoreCase()).addOrder(Order.asc("lastName"));
            }
            if (singleCompanyName.length() >= 1) {
//                            criteria.add(Expression.like("singleCompanyName", "%"+ singleCompanyName + "%").ignoreCase()).addOrder(Order.asc("singleCompanyName"));
                criteria.add(Expression.like("companyName", "%" + singleCompanyName + "%").ignoreCase()).addOrder(Order.asc("companyName"));
            }

            if (Agency.length() >= 1) {
                criteria.add(Expression.like("companyName", "%" + Agency + "%").ignoreCase()).addOrder(Order.asc("companyName"));
//                            criteria.add(Expression.like("agency", "%"+ Agency + "%").ignoreCase()).addOrder(Order.asc("agency"));
            }

            //if subcriteria values are present from the form (for the "many" class),
            //then include subcriteria search values in main search (the "one" class)
            if (ContactFirstName.length() >= 1 || ContactLastName.length() >= 1) {
                Criteria subCriteria = criteria.createCriteria("ResourceContacts");
                subCriteria.add(Expression.like("firstName", ContactFirstName + "%").ignoreCase());
                subCriteria.add(Expression.like("lastName", ContactLastName + "%").ignoreCase());
            }
            if (OldId.length() >= 1) {
                criteria.add(Expression.like("resourceId", Integer.valueOf(OldId)));
            }
            if (teamClientSearchParam.length() >= 1) {
                if (!resultsTeamClient.isEmpty()) {
                    Integer[] teamClientIds = new Integer[resultsTeamClient.size()];
                    for (int i = 0; i < resultsTeamClient.size(); i++) {
                        ResourceClient rc = (ResourceClient) resultsTeamClient.get(i);

                        teamClientIds[i] = new Integer(rc.getResourceId());
                    }

                    criteria.add(Expression.in("resourceId", teamClientIds));
                } else {
                    criteria.add(Expression.like("resourceId", Integer.valueOf(0)));
                }
            }
            try {
                if (RiskRating.length() >= 1) {
                    criteria.add(Expression.like("riskrating", "%" + RiskRating + "%").ignoreCase());
                }
            } catch (Exception e1) {
            }

            try {
                if (medicalScore.length() >= 1) {
                    criteria.add(Expression.gt("medicalScore", Double.valueOf(medicalScore)));
                }
            } catch (Exception e1) {
            }
            try {
                if (technicalScore.length() >= 1) {
                    criteria.add(Expression.gt("technicalScore", Double.valueOf(technicalScore)));
                }
            } catch (Exception e1) {
            }
            try {
                if (softwareScore.length() >= 1) {
                    criteria.add(Expression.gt("softwareScore", Double.valueOf(softwareScore)));
                }
            } catch (Exception e1) {
            }
            try {
                if (legalScore.length() >= 1) {
                    criteria.add(Expression.gt("legalFinancialScore", Double.valueOf(legalScore)));
                }
            } catch (Exception e1) {
            }
//      try {
//        if (marketingScore.length() >= 1) {
//          criteria.add(Expression.like("marketingScore", Double.valueOf(marketingScore)));
//        }
//      } catch (Exception e1) {
//      }

            //if subcriteria values are present from the form (for the "many" class),
            //then include subcriteria search values in main search (the "one" class)
            Criteria subCriteriaLanguagePairs = null;
            if (!SourceId.equals("0") || !TargetId.equals("0")) {
                subCriteriaLanguagePairs = criteria.createCriteria("LanguagePairs");
//        criteria.addOrder(Order.desc("LanguagePairs.prefferedVendor"));
            }
            if (!SourceId.equals("0")) {
                subCriteriaLanguagePairs.add(Expression.eq("sourceId", Integer.valueOf(SourceId)));
            }
            if (!TargetId.equals("0")) {
                subCriteriaLanguagePairs.add(Expression.eq("targetId", Integer.valueOf(TargetId)));
            }

            if (!Status.equals("0")) {
                criteria.add(Expression.eq("status", Status));
            }
            if (IncludeDoNot == null) {
                criteria.add(Expression.eq("doNotUse", new Boolean(false)));
            }

            if (isagency != null) {
                criteria.add(Expression.eq("agency", new Boolean(true)));
            }

            if (consultant != null) {
                criteria.add(Expression.eq("consultant", new Boolean(true)));
            }
            if (partner != null) {
                criteria.add(Expression.eq("partner", new Boolean(true)));
            }

            if (businesssuport != null) {
                criteria.add(Expression.eq("businesssuport", new Boolean(true)));
            }
            if (fqa != null) {
                criteria.add(Expression.eq("fqa", new Boolean(true)));
            }

            if (Translator != null) {
                criteria.add(Expression.eq("translator", new Boolean(true)));
            }
            if (Editor != null) {
                criteria.add(Expression.eq("editor", new Boolean(true)));
            }
            if (Proofreader != null) {
                criteria.add(Expression.eq("proofreader", new Boolean(true)));
            }
            if (Evaluator != null) {
                criteria.add(Expression.eq("evaluator", new Boolean(true)));
            }

            if (other != null) {
                criteria.add(Expression.eq("other", new Boolean(true)));
            }
            if (tne != null) {
                criteria.add(Expression.eq("tne", new Boolean(true)));
            }
            if (consultant != null) {
                criteria.add(Expression.eq("consultant", new Boolean(true)));
            }
            if (partner != null) {
                criteria.add(Expression.eq("partner", new Boolean(true)));
            }
            if (informationTechnology != null) {
                criteria.add(Expression.eq("informationTechnology", new Boolean(true)));
            }

            if (humanResource != null) {
                criteria.add(Expression.eq("humanResource", new Boolean(true)));
            }
            if (office != null) {
                criteria.add(Expression.eq("office", new Boolean(true)));
            }
            if (sales != null) {
                criteria.add(Expression.eq("sales", new Boolean(true)));
            }
            if (accounting != null) {
                criteria.add(Expression.eq("accounting", new Boolean(true)));
            }
            if (bsdOther != null) {
                criteria.add(Expression.eq("bsdOther", new Boolean(true)));
            }
            if (prodOther != null) {
                criteria.add(Expression.eq("prodOther", new Boolean(true)));
            }
            if (engineering != null) {
                criteria.add(Expression.eq("engineering", new Boolean(true)));
            }
            if (postEditing != null) {
                criteria.add(Expression.eq("postEditing", new Boolean(true)));
            }

            if (Quality != null) {
                criteria.add(Expression.eq("quality", new Boolean(true)));
            }

            if (Expert != null) {
                criteria.add(Expression.eq("expert", new Boolean(true)));
            }

            if (Interpreting != null) {
                criteria.add(Expression.eq("interpreting", new Boolean(true)));
            }

            if (Dtp != null) {
                criteria.add(Expression.eq("dtp", new Boolean(true)));
            }
            if (Icr != null) {
                criteria.add(Expression.eq("icr", new Boolean(true)));
            }
//bhjjk;
            //if subcriteria values are present from the form (for the "many" class),
            //then include subcriteria search values in main search (the "one" class)
            Criteria subCriteriaRateScoreLanguages = null;
            boolean sourceSet = false;
            boolean targetSet = false;
            boolean ratescorelanguagesCon = false;
            if (TRate.length() >= 1 || ERate.length() >= 1 || TERate.length() >= 1 || PRate.length() >= 1) {

                try {
                    subCriteriaRateScoreLanguages = subCriteriaLanguagePairs.createCriteria("RateScoreLanguages");
                } catch (Exception e) {
                }
                ratescorelanguagesCon = true;
                if (source != null) {
                    subCriteriaRateScoreLanguages.add(Expression.eq("source", source));
                    sourceSet = true;
                }
                if (target != null) {
                    subCriteriaRateScoreLanguages.add(Expression.eq("target", target));
                    targetSet = true;
                }

                Disjunction any = Expression.disjunction();
                try {
                    if (TRate.length() >= 1) {
                        Conjunction all = Expression.conjunction();
                        all.add(Expression.gt("t", new Double(0)));
                        all.add(Expression.le("t", Double.valueOf(TRate)));
                        any.add(all);
                    }
                } catch (Exception e) {
                }
                try {
                    if (ERate.length() >= 1) {
                        Conjunction all = Expression.conjunction();
                        all.add(Expression.gt("e", new Double(0)));
                        all.add(Expression.le("e", Double.valueOf(ERate)));
                        any.add(all);
                    }
                } catch (Exception e) {
                }
                try {
                    if (TERate.length() >= 1) {
                        Conjunction all = Expression.conjunction();
                        all.add(Expression.gt("te", new Double(0)));
                        all.add(Expression.le("te", Double.valueOf(TERate)));
                        any.add(all);
                    }
                } catch (Exception e) {
                }
                try {
                    if (PRate.length() >= 1) {
                        Conjunction all = Expression.conjunction();
                        all.add(Expression.gt("p", new Double(0)));
                        all.add(Expression.le("p", Double.valueOf(PRate)));
                        any.add(all);
                    }
                } catch (Exception e) {
                }

                try {
                    subCriteriaRateScoreLanguages.add(any);
                } catch (Exception e) {
                }
            }
            //if subcriteria values are present from the form (for the "many" class),
            //then include subcriteria search values in main search (the "one" class)
            if (DtpRate.length() >= 1 || dtpSource != null || dtpTarget != null) {
                Criteria subCriteria = criteria.createCriteria("RateScoreDtps");
                if (dtpSource != null) {
                    subCriteria.add(Expression.eq("source", dtpSource));
                }
                if (dtpTarget != null) {
                    subCriteria.add(Expression.eq("target", dtpTarget));
                }
                if (DtpRate.length() >= 1) {
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

            if (Specific.length > 0 && !Specific[0].equals("0")) {
                Criteria subCriteriaSpecificIndustries = criteria.createCriteria("SpecificIndustries");
                Integer[] specificIds = new Integer[Specific.length];
                for (int i = 0; i < specificIds.length; i++) {
                    specificIds[i] = new Integer(Specific[i]);
                }
                subCriteriaSpecificIndustries.add(Expression.in("specificIndustryId", specificIds));
            }
            if (General.length > 0 && !General[0].equals("0")) {
                Criteria subCriteriaIndustries = criteria.createCriteria("Industries");
                Integer[] generalIds = new Integer[General.length];
                for (int i = 0; i < generalIds.length; i++) {
                    generalIds[i] = new Integer(General[i]);
                }
                subCriteriaIndustries.add(Expression.in("industryId", generalIds));
            }

            boolean rateScoreScore = false;
            for (int i = 0; i < ScoresLin.length; i++) {
                if (ScoresLin[i] != null && ScoresLin[i].length() >= 1 && ratescorelanguagesCon == false) {
                    try {
                        subCriteriaRateScoreLanguages = subCriteriaLanguagePairs.createCriteria("RateScoreLanguages");
                    } catch (Exception e) {
                        //System.out.println("Exception Here ;-)");
                    }
                    ratescorelanguagesCon = true;
                    rateScoreScore = true;
                }
            }
            for (int i = 0; i < ScoresLin.length; i++) {
                if (ScoresLin[i] != null && ScoresLin[i].length() >= 1) {
                    if (source != null && sourceSet == false) {
                        subCriteriaRateScoreLanguages.add(Expression.eq("source", source));
                        sourceSet = true;
                    }
                    if (target != null && targetSet == false) {
                        subCriteriaRateScoreLanguages.add(Expression.eq("target", target));
                        targetSet = true;
                    }
                }
            }
            ListIterator iterRscs = rscs.listIterator();
            Disjunction anyRateScore = Expression.disjunction();
            for (int i = 0; i < ScoresLin.length; i++) {
                RateScoreCategory rsc = (RateScoreCategory) iterRscs.next();
                if (ScoresLin[i] != null && ScoresLin[i].length() >= 1) {
                    Conjunction all = Expression.conjunction();
                    all.add(Expression.eq("specialty", rsc.getCategory()));
                    try {
                        all.add(Expression.ge("score", Double.valueOf(ScoresLin[i])));
                    } catch (Exception e) {
                    }
                    anyRateScore.add(all);
                }
            }
            if (rateScoreScore) {
                try {
                    subCriteriaRateScoreLanguages.add(anyRateScore);
                } catch (Exception e) {
                }
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
            if (!ProjectScoreGreater.equals("null") && !ProjectScoreGreater.equals("null")) {
                criteria.add(Expression.ge("projectScoreAverage", Double.valueOf(ProjectScoreGreater)));
            }

            if (UsesTrados != null) {
                criteria.add(Expression.eq("usesTrados", new Boolean(true)));
            }
            if (UsesDejavu != null) {
                criteria.add(Expression.eq("usesDejavu", new Boolean(true)));
            }
            if (UsesCatalyst != null) {
                criteria.add(Expression.eq("usesCatalyst", new Boolean(true)));
            }
            if (UsesSdlx != null) {
                criteria.add(Expression.eq("usesSdlx", new Boolean(true)));
            }
            if (UsesTransit != null) {
                criteria.add(Expression.eq("usesTransit", new Boolean(true)));
            }
            if (UsesOtherTool1.length() >= 1 || UsesOtherTool2.length() >= 1) {
                Criteria subCriteria = criteria.createCriteria("ResourceTools");
                Disjunction any = Expression.disjunction();
                if (UsesOtherTool2.length() == 0) {
                    subCriteria.add(Expression.like("description", "%" + UsesOtherTool1 + "%").ignoreCase());
                } else {
                    any.add(Expression.like("description", "%" + UsesOtherTool1 + "%").ignoreCase());
                    any.add(Expression.like("description", "%" + UsesOtherTool2 + "%").ignoreCase());
                    subCriteria.add(any);
                }
            }

            if (City.length() >= 1) {
                criteria.add(Expression.like("City", "%" + City + "%").ignoreCase());
            }
            if (!Country.equals("0")) {
                criteria.add(Expression.eq("Country", Country));
            }

            if (Resume.length() >= 1) {
                criteria.add(Expression.like("resume", "%" + Resume + "%").ignoreCase());
            }

            if (Note.length() >= 1) {
                criteria.add(Expression.like("Note", "%" + Note + "%").ignoreCase());
            }

            //sort results by id from old database
            criteria.addOrder(Order.asc("resourceId"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            //get results
            results = criteria.list();
            Set sortedList = new HashSet();

            if (clientName != null && !"".equals(clientName)) {
                String clientResourceIds = TeamHelper.getClientResources(clientName);
                for (ListIterator iter = results.listIterator(); iter.hasNext();) {
                    Resource r = (Resource) iter.next();

                    if (clientResourceIds.indexOf("_" + r.getResourceId().toString() + "_") == -1) {
                        iter.remove();
                    }
                }
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

    //search for resources (team members) given the criteria
    //this is the main search without including old db rates and scores
    public List getResourceSearch(String clientName, String FirstName, String LastName, String singleCompanyName, String Resume, String Note) {

        Session session = ConnectionFactory.getInstance().getSession();

        List results = null;

        try {
            //retreive resources from database

            //this is the "one" class: it is the main criteria
            Criteria criteria = session.createCriteria(Resource.class);

            if (FirstName.length() >= 1) {
                criteria.add(Expression.like("firstName", "%" + FirstName + "%").ignoreCase());
            }
            if (LastName.length() >= 1) {
                criteria.add(Expression.like("lastName", "%" + LastName + "%").ignoreCase());
            }
            if (singleCompanyName.length() >= 1) {
//                            criteria.add(Expression.like("singleCompanyName", "%"+ singleCompanyName + "%").ignoreCase()).addOrder(Order.asc("singleCompanyName"));
                criteria.add(Expression.like("companyName", "%" + singleCompanyName + "%").ignoreCase());
            }

            if (Resume.length() >= 1) {
                criteria.add(Expression.like("resume", "%" + Resume + "%").ignoreCase());
            }

            if (Note.length() >= 1) {
                criteria.add(Expression.like("Note", "%" + Note + "%").ignoreCase());
            }

            //sort results by id from old database
            criteria.addOrder(Order.asc("resourceId"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            //get results
            results = criteria.list();
            Set sortedList = new HashSet();

            if (clientName != null && !"".equals(clientName)) {
                String clientResourceIds = TeamHelper.getClientResources(clientName);
                for (ListIterator iter = results.listIterator(); iter.hasNext();) {
                    Resource r = (Resource) iter.next();

                    if (clientResourceIds.indexOf("_" + r.getResourceId().toString() + "_") == -1) {
                        iter.remove();
                    }
                }
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

    //search for resources (team members) given the criteria
    //this is the main search without including old db rates and scores
    public List getPreferredResourceSearch(String SourceId, String TargetId) {

        Session session = ConnectionFactory.getInstance().getSession();

        List results = new ArrayList();

        try {
            //retreive resources from database

            //this is the "one" class: it is the main criteria
            Criteria criteria = session.createCriteria(Resource.class);
            Hibernate.initialize(Resource.class);

            Criteria subCriteriaLanguagePairs = null;
            if (!SourceId.equals("0") || !TargetId.equals("0")) {
                subCriteriaLanguagePairs = criteria.createCriteria("LanguagePairs");
                subCriteriaLanguagePairs.add(Expression.eq("prefferedVendor", true));
//        criteria.addOrder(Order.desc("LanguagePairs.prefferedVendor"));
            }
            if (!SourceId.equals("0")) {
                subCriteriaLanguagePairs.add(Expression.eq("sourceId", Integer.valueOf(SourceId)));
            }
            if (!TargetId.equals("0")) {
                subCriteriaLanguagePairs.add(Expression.eq("targetId", Integer.valueOf(TargetId)));
            }

            //sort results by id from old database
            criteria.addOrder(Order.asc("resourceId"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            //get results
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

    //search for resources (team members) given the criteria
    //this is the main search without including old db rates and scores
    public List getPreferredResourceSearchDTP(String SourceId, String targetId) {

        Session session = ConnectionFactory.getInstance().getSession();

        List results = new ArrayList();

        try {
            //retreive resources from database

            //this is the "one" class: it is the main criteria
            Criteria criteria = session.createCriteria(Resource.class);

            Criteria subCriteriaLanguagePairs = null;
            if (!SourceId.equals("0")) {
                subCriteriaLanguagePairs = criteria.createCriteria("LanguagePairs");
                subCriteriaLanguagePairs.add(Expression.eq("prefferedVendor", true));
//        criteria.addOrder(Order.desc("LanguagePairs.prefferedVendor"));
            }
            if (!SourceId.equals("0")) {
                subCriteriaLanguagePairs.add(Expression.eq("sourceId", Integer.valueOf(SourceId)));
            }
            if (!targetId.equals("0")) {
                subCriteriaLanguagePairs.add(Expression.eq("targetId", Integer.valueOf(targetId)));
            }

//     List<Integer> targetId = new ArrayList<>();
//     targetId.add(174);targetId.add(175);targetId.add(176);targetId.add(179);
//        subCriteriaLanguagePairs.add(Expression.in("targetId", targetId));
// 
            //sort results by id from old database
            criteria.addOrder(Order.asc("resourceId"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            //get results
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
    //search for resources (team members) given the criteria
    //this is the main search without including old db rates and scores

    public List getPreferredResourceSearchFQA() {

        Session session = ConnectionFactory.getInstance().getSession();

        List results = new ArrayList();

        try {

            Transaction tx = null;
            tx = session.beginTransaction();
            List<Integer> resourceIds = new ArrayList<>();

            PreparedStatement pstmt = session.connection().prepareStatement("select resourceId from asseseval where asseseval.preferred='yes'");
            ResultSet rs = pstmt.executeQuery();
            // ClientService.getInstance().getBlogList();

            while (rs.next()) {
                resourceIds.add(rs.getInt("resourceId"));
            }
            tx.commit();
            session.close();
            session = ConnectionFactory.getInstance().getSession();
            //retreive resources from database

            //this is the "one" class: it is the main criteria
            Criteria criteria = session.createCriteria(Resource.class);

            criteria.add(Expression.in("resourceId", resourceIds));
            //sort results by id from old database
            criteria.addOrder(Order.asc("resourceId"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            //get results
            results = criteria.list();

        } catch (HibernateException e) {
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } catch (SQLException ex) {
            Logger.getLogger(ResourceService.class.getName()).log(Level.SEVERE, null, ex);
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

    //search for resources (team members) given the criteria
    //this adds the oldRates to the search results
    public List getResourceSearchOldRates(String FirstName, String LastName, String singleCompanyName, String ContactFirstName, String ContactLastName, String Agency, String OldId, String SourceId, String TargetId, String source, String target, String Status, String IncludeDoNot, String Translator, String Editor, String Proofreader, String Dtp, String Icr, String TRate, String ERate, String TERate, String PRate, String DtpRate, String RateOldDb, String[] Specific, String[] General, String[] ScoresLin, List rscs, String ScoreOldDb, String ProjectScoreGreater, String UsesTrados, String UsesSdlx, String UsesDejavu, String UsesCatalyst, String UsesOtherTool1, String UsesOtherTool2, String UsesTransit, String City, String Country, String Resume, String other, String consultant, String partner, String engineering, String businesssuport, String fqa) {

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;

        try {
            //retreive resources from database

            //this is the "one" class: it is the main criteria
            Criteria criteria = session.createCriteria(Resource.class);

            if (FirstName.length() >= 1) {
                criteria.add(Expression.like("firstName", "%" + FirstName + "%").ignoreCase());
            }
            if (LastName.length() >= 1) {
                criteria.add(Expression.like("lastName", "%" + LastName + "%").ignoreCase());
            }
            if (singleCompanyName.length() >= 1) {
                criteria.add(Expression.like("singleCompanyName", "%" + singleCompanyName + "%").ignoreCase()).addOrder(Order.asc("singleCompanyName"));
            }
            if (Agency.length() >= 1) {
                criteria.add(Expression.like("companyName", "%" + Agency + "%").ignoreCase());
            }

            //if subcriteria values are present from the form (for the "many" class),
            //then include subcriteria search values in main search (the "one" class)
            if (ContactFirstName.length() >= 1 || ContactLastName.length() >= 1) {
                Criteria subCriteria = criteria.createCriteria("ResourceContacts");
                subCriteria.add(Expression.like("firstName", "%" + ContactFirstName + "%").ignoreCase());
                subCriteria.add(Expression.like("lastName", "%" + ContactLastName + "%").ignoreCase());
            }
            if (OldId.length() >= 1) {
                criteria.add(Expression.eq("resourceId", Integer.valueOf(OldId)));
            }

            //if subcriteria values are present from the form (for the "many" class),
            //then include subcriteria search values in main search (the "one" class)
            Criteria subCriteriaLanguagePairs = null;
            if (!SourceId.equals("0") || !TargetId.equals("0")) {
                subCriteriaLanguagePairs = criteria.createCriteria("LanguagePairs");
            }
            if (!SourceId.equals("0")) {
                subCriteriaLanguagePairs.add(Expression.eq("sourceId", Integer.valueOf(SourceId)));
            }
            if (!TargetId.equals("0")) {
                subCriteriaLanguagePairs.add(Expression.eq("targetId", Integer.valueOf(TargetId)));
            }

            if (!Status.equals("0")) {
                criteria.add(Expression.eq("status", Status));
            }
            if (IncludeDoNot == null) {
                criteria.add(Expression.eq("doNotUse", new Boolean(false)));
            }

            if (Translator != null) {
                criteria.add(Expression.eq("translator", new Boolean(true)));
            }
            if (Editor != null) {
                criteria.add(Expression.eq("editor", new Boolean(true)));
            }
            if (Proofreader != null) {
                criteria.add(Expression.eq("proofreader", new Boolean(true)));
            }

            if (other != null) {
                criteria.add(Expression.eq("other", new Boolean(true)));
            }
            if (consultant != null) {
                criteria.add(Expression.eq("consultant", new Boolean(true)));
            }
            if (partner != null) {
                criteria.add(Expression.eq("partner", new Boolean(true)));
            }
            if (engineering != null) {
                criteria.add(Expression.eq("engineering", new Boolean(true)));
            }
            if (businesssuport != null) {
                criteria.add(Expression.eq("businesssuport", new Boolean(true)));
            }
            if (fqa != null) {
                criteria.add(Expression.eq("fqa", new Boolean(true)));
            }

            if (Dtp != null) {
                criteria.add(Expression.eq("dtp", new Boolean(true)));
            }
            if (Icr != null) {
                criteria.add(Expression.eq("icr", new Boolean(true)));
            }

            //if subcriteria values are present from the form (for the "many" class),
            //then include subcriteria search values in main search (the "one" class)
            Criteria subCriteriaRateScoreLanguages = null;
            boolean sourceSet = false;
            boolean targetSet = false;
            boolean ratescorelanguagesCon = false;

            if (RateOldDb != null) {
                Disjunction any = Expression.disjunction();

                if (TRate.length() >= 1) {
                    Conjunction all = Expression.conjunction();
                    all.add(Expression.gt("t", new Double(0)));
                    all.add(Expression.le("t", Double.valueOf(TRate)));
                    any.add(all);
                }
                if (ERate.length() >= 1) {
                    Conjunction all = Expression.conjunction();
                    all.add(Expression.gt("e", new Double(0)));
                    all.add(Expression.le("e", Double.valueOf(ERate)));
                    any.add(all);
                }
                if (TERate.length() >= 1) {
                    Conjunction all = Expression.conjunction();
                    all.add(Expression.gt("te", new Double(0)));
                    all.add(Expression.le("te", Double.valueOf(TERate)));
                    any.add(all);
                }
                if (PRate.length() >= 1) {
                    Conjunction all = Expression.conjunction();
                    all.add(Expression.gt("p", new Double(0)));
                    all.add(Expression.le("p", Double.valueOf(PRate)));
                    any.add(all);
                }
                criteria.add(any);
            }

            if (Specific.length > 0 && !Specific[0].equals("0")) {
                Criteria subCriteriaSpecificIndustries = criteria.createCriteria("SpecificIndustries");
                Integer[] specificIds = new Integer[Specific.length];
                for (int i = 0; i < specificIds.length; i++) {
                    specificIds[i] = new Integer(Specific[i]);
                }
                subCriteriaSpecificIndustries.add(Expression.in("specificIndustryId", specificIds));
            }
            if (General.length > 0 && !General[0].equals("0")) {
                Criteria subCriteriaIndustries = criteria.createCriteria("Industries");
                Integer[] generalIds = new Integer[General.length];
                for (int i = 0; i < generalIds.length; i++) {
                    generalIds[i] = new Integer(General[i]);
                }
                subCriteriaIndustries.add(Expression.in("industryId", generalIds));
            }

//                        boolean rateScoreScore = false;
//                        for(int i = 0; i < ScoresLin.length; i++) {
//                            if(ScoresLin[i] != null && ScoresLin[i].length() >= 1 && ratescorelanguagesCon == false) {
//                                subCriteriaRateScoreLanguages = subCriteriaLanguagePairs.createCriteria("RateScoreLanguages");
//                                ratescorelanguagesCon = true;
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
            if (!ProjectScoreGreater.equals("null")) {
                criteria.add(Expression.ge("projectScoreAverage", Double.valueOf(ProjectScoreGreater)));
            }

            if (UsesTrados != null) {
                criteria.add(Expression.eq("usesTrados", new Boolean(true)));
            }
            if (UsesDejavu != null) {
                criteria.add(Expression.eq("usesDejavu", new Boolean(true)));
            }
            if (UsesCatalyst != null) {
                criteria.add(Expression.eq("usesCatalyst", new Boolean(true)));
            }
            if (UsesSdlx != null) {
                criteria.add(Expression.eq("usesSdlx", new Boolean(true)));
            }
            if (UsesTransit != null) {
                criteria.add(Expression.eq("usesTransit", new Boolean(true)));
            }
            if (UsesOtherTool1.length() >= 1 || UsesOtherTool2.length() >= 1) {
                Criteria subCriteria = criteria.createCriteria("ResourceTools");
                Disjunction any = Expression.disjunction();
                if (UsesOtherTool2.length() == 0) {
                    subCriteria.add(Expression.like("description", "%" + UsesOtherTool1 + "%").ignoreCase());
                } else {
                    any.add(Expression.like("description", "%" + UsesOtherTool1 + "%").ignoreCase());
                    any.add(Expression.like("description", "%" + UsesOtherTool2 + "%").ignoreCase());
                    subCriteria.add(any);
                }
            }

            if (City.length() >= 1) {
                criteria.add(Expression.like("City", "%" + City + "%").ignoreCase());
            }
            if (!Country.equals("0")) {
                criteria.add(Expression.eq("Country", Country));
            }

            if (Resume.length() >= 1) {
                criteria.add(Expression.like("resume", "%" + Resume + "%").ignoreCase());
            }

            //sort results by id from old database
            criteria.addOrder(Order.asc("resourceId"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            //get results
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

    //search for resources (team members) given the criteria
    //this adds the oldScores to the search results
    public List getResourceSearchOldScores(String FirstName, String LastName, String singleCompanyName, String ContactFirstName, String ContactLastName, String Agency, String OldId, String SourceId, String TargetId, String source, String target, String Status, String IncludeDoNot, String Translator, String Editor, String Proofreader, String Dtp, String Icr, String TRate, String ERate, String TERate, String PRate, String DtpRate, String RateOldDb, String[] Specific, String[] General, String[] ScoresLin, List rscs, String ScoreOldDb, String ProjectScoreGreater, String UsesTrados, String UsesSdlx, String UsesDejavu, String UsesCatalyst, String UsesOtherTool1, String UsesOtherTool2, String UsesTransit, String City, String Country, String Resume, String other, String consultant, String partner, String engineering, String businesssuport, String fqa) {

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;

        try {
            //retreive resources from database

            //this is the "one" class: it is the main criteria
            Criteria criteria = session.createCriteria(Resource.class);

            if (FirstName.length() >= 1) {
                criteria.add(Expression.like("firstName", "%" + FirstName + "%").ignoreCase());
            }
            if (LastName.length() >= 1) {
                criteria.add(Expression.like("lastName", "%" + LastName + "%").ignoreCase());
            }
            if (singleCompanyName.length() >= 1) {
                criteria.add(Expression.like("singleCompanyName", "%" + singleCompanyName + "%").ignoreCase()).addOrder(Order.asc("singleCompanyName"));
            }

            if (Agency.length() >= 1) {
                criteria.add(Expression.like("companyName", "%" + Agency + "%").ignoreCase());
            }

            //if subcriteria values are present from the form (for the "many" class),
            //then include subcriteria search values in main search (the "one" class)
            if (ContactFirstName.length() >= 1 || ContactLastName.length() >= 1) {
                Criteria subCriteria = criteria.createCriteria("ResourceContacts");
                subCriteria.add(Expression.like("firstName", "%" + ContactFirstName + "%").ignoreCase()).addOrder(Order.asc("firstName"));
                subCriteria.add(Expression.like("lastName", "%" + ContactLastName + "%").ignoreCase()).addOrder(Order.asc("lastName"));
            }
            if (OldId.length() >= 1) {
                criteria.add(Expression.eq("resourceId", Integer.valueOf(OldId)));
            }

            //if subcriteria values are present from the form (for the "many" class),
            //then include subcriteria search values in main search (the "one" class)
            Criteria subCriteriaLanguagePairs = null;
            if (!SourceId.equals("0") || !TargetId.equals("0")) {
                subCriteriaLanguagePairs = criteria.createCriteria("LanguagePairs");
            }
            if (!SourceId.equals("0")) {
                subCriteriaLanguagePairs.add(Expression.eq("sourceId", Integer.valueOf(SourceId)));
            }
            if (!TargetId.equals("0")) {
                subCriteriaLanguagePairs.add(Expression.eq("targetId", Integer.valueOf(TargetId)));
            }

            if (!Status.equals("0")) {
                criteria.add(Expression.eq("status", Status));
            }
            if (IncludeDoNot == null) {
                criteria.add(Expression.eq("doNotUse", new Boolean(false)));
            }

            if (Translator != null) {
                criteria.add(Expression.eq("translator", new Boolean(true)));
            }
            if (Editor != null) {
                criteria.add(Expression.eq("editor", new Boolean(true)));
            }
            if (Proofreader != null) {
                criteria.add(Expression.eq("proofreader", new Boolean(true)));
            }
            if (other != null) {
                criteria.add(Expression.eq("other", new Boolean(true)));
            }
            if (consultant != null) {
                criteria.add(Expression.eq("consultant", new Boolean(true)));
            }
            if (partner != null) {
                criteria.add(Expression.eq("partner", new Boolean(true)));
            }
            if (engineering != null) {
                criteria.add(Expression.eq("engineering", new Boolean(true)));
            }
            if (businesssuport != null) {
                criteria.add(Expression.eq("businesssuport", new Boolean(true)));
            }
            if (fqa != null) {
                criteria.add(Expression.eq("fqa", new Boolean(true)));
            }

            if (Dtp != null) {
                criteria.add(Expression.eq("dtp", new Boolean(true)));
            }
            if (Icr != null) {
                criteria.add(Expression.eq("icr", new Boolean(true)));
            }

            //if subcriteria values are present from the form (for the "many" class),
            //then include subcriteria search values in main search (the "one" class)
//                        Criteria subCriteriaRateScoreLanguages = null;
//                        boolean sourceSet = false;
//                        boolean targetSet = false;
//                        boolean ratescorelanguagesCon = false;
//                        if(TRate.length() >= 1 || ERate.length() >= 1 || TERate.length() >= 1 || PRate.length() >= 1) {
//                            subCriteriaRateScoreLanguages = subCriteriaLanguagePairs.createCriteria("RateScoreLanguages");
//                            ratescorelanguagesCon = true;
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
            if (Specific.length > 0 && !Specific[0].equals("0")) {
                Criteria subCriteriaSpecificIndustries = criteria.createCriteria("SpecificIndustries");
                Integer[] specificIds = new Integer[Specific.length];
                for (int i = 0; i < specificIds.length; i++) {
                    specificIds[i] = new Integer(Specific[i]);
                }
                subCriteriaSpecificIndustries.add(Expression.in("specificIndustryId", specificIds));
            }
            if (General.length > 0 && !General[0].equals("0")) {
                Criteria subCriteriaIndustries = criteria.createCriteria("Industries");
                Integer[] generalIds = new Integer[General.length];
                for (int i = 0; i < generalIds.length; i++) {
                    generalIds[i] = new Integer(General[i]);
                }
                subCriteriaIndustries.add(Expression.in("industryId", generalIds));
            }

            if (ScoreOldDb != null) {
                Disjunction any = Expression.disjunction();

                if (ScoresLin[0] != null && ScoresLin[0].length() >= 1) {
                    any.add(Expression.ge("medicalScore", Double.valueOf(ScoresLin[0])));
                }
                if (ScoresLin[1] != null && ScoresLin[1].length() >= 1) {
                    any.add(Expression.ge("technicalScore", Double.valueOf(ScoresLin[1])));
                }
                if (ScoresLin[2] != null && ScoresLin[2].length() >= 1) {
                    any.add(Expression.ge("softwareScore", Double.valueOf(ScoresLin[2])));
                }
                if (ScoresLin[3] != null && ScoresLin[3].length() >= 1) {
                    any.add(Expression.ge("legalFinancialScore", Double.valueOf(ScoresLin[3])));
                }
                criteria.add(any);
            }

            if (!ProjectScoreGreater.equals("null")) {
                criteria.add(Expression.ge("projectScoreAverage", Double.valueOf(ProjectScoreGreater)));
            }

            if (UsesTrados != null) {
                criteria.add(Expression.eq("usesTrados", new Boolean(true)));
            }
            if (UsesDejavu != null) {
                criteria.add(Expression.eq("usesDejavu", new Boolean(true)));
            }
            if (UsesCatalyst != null) {
                criteria.add(Expression.eq("usesCatalyst", new Boolean(true)));
            }
            if (UsesSdlx != null) {
                criteria.add(Expression.eq("usesSdlx", new Boolean(true)));
            }
            if (UsesTransit != null) {
                criteria.add(Expression.eq("usesTransit", new Boolean(true)));
            }
            if (UsesOtherTool1.length() >= 1 || UsesOtherTool2.length() >= 1) {
                Criteria subCriteria = criteria.createCriteria("ResourceTools");
                Disjunction any = Expression.disjunction();
                if (UsesOtherTool2.length() == 0) {
                    subCriteria.add(Expression.like("description", "%" + UsesOtherTool1 + "%").ignoreCase());
                } else {
                    any.add(Expression.like("description", "%" + UsesOtherTool1 + "%").ignoreCase());
                    any.add(Expression.like("description", "%" + UsesOtherTool2 + "%").ignoreCase());
                    subCriteria.add(any);
                }
            }

            if (City.length() >= 1) {
                criteria.add(Expression.like("City", "%" + City + "%").ignoreCase());
            }
            if (!Country.equals("0")) {
                criteria.add(Expression.eq("Country", Country));
            }

            if (Resume.length() >= 1) {
                criteria.add(Expression.like("resume", "%" + Resume + "%").ignoreCase());
            }

            //sort results by id from old database
            criteria.addOrder(Order.asc("resourceId"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            //get results
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

    //search for resources (team members) given the criteria
    //this adds the oldRates AND oldScores to the search results
    public List getResourceSearchOldRatesScores(String FirstName, String LastName, String singleCompanyName, String ContactFirstName,
            String ContactLastName, String Agency, String OldId, String SourceId, String TargetId, String source, String target, String Status,
            String IncludeDoNot, String Translator, String Editor, String Proofreader, String Dtp, String Icr, String TRate, String ERate,
            String TERate, String PRate, String DtpRate, String RateOldDb, String[] Specific, String[] General, String[] ScoresLin, List rscs,
            String ScoreOldDb, String ProjectScoreGreater, String UsesTrados, String UsesSdlx, String UsesDejavu, String UsesCatalyst,
            String UsesOtherTool1, String UsesOtherTool2, String UsesTransit, String City, String Country, String Resume, String other,
            String consultant, String partner, String engineering, String businesssuport, String fqa) {

        Session session = ConnectionFactory.getInstance().getSession();

        Query query;
        List results = null;

        try {
            //retreive resources from database

            //this is the "one" class: it is the main criteria
            Criteria criteria = session.createCriteria(Resource.class);

            if (FirstName.length() >= 1) {
                criteria.add(Expression.like("firstName", "%" + FirstName + "%").ignoreCase());

            }
            if (LastName.length() >= 1) {
                criteria.add(Expression.like("lastName", "%" + LastName + "%").ignoreCase());
            }
            if (singleCompanyName.length() >= 1) {
                criteria.add(Expression.like("singleCompanyName", "%" + singleCompanyName + "%").ignoreCase()).addOrder(Order.asc("singleCompanyName"));
            }

            if (Agency.length() >= 1) {
                criteria.add(Expression.like("companyName", "%" + Agency + "%").ignoreCase());
            }

            //if subcriteria values are present from the form (for the "many" class),
            //then include subcriteria search values in main search (the "one" class)
            if (ContactFirstName.length() >= 1 || ContactLastName.length() >= 1) {
                Criteria subCriteria = criteria.createCriteria("ResourceContacts");
                subCriteria.add(Expression.like("firstName", ContactFirstName + "%").ignoreCase()).addOrder(Order.asc("firstName"));
                subCriteria.add(Expression.like("lastName", ContactLastName + "%").ignoreCase()).addOrder(Order.asc("lastName"));
            }
            if (OldId.length() >= 1) {
                criteria.add(Expression.eq("resourceId", Integer.valueOf(OldId)));
            }

//        if(!resultsTeamClient.isEmpty()){
//         Integer[] teamClientIds = new Integer[resultsTeamClient.size()];
//         for(int i = 0; i< resultsTeamClient.size();i++){
//           ResourceClient rc = (ResourceClient) resultsTeamClient.get(i);
//        
//       
//          teamClientIds[i] = new Integer(rc.getResourceId());
//         }
//      
//        criteria.add(Expression.in("resourceId", teamClientIds));
//        }
            //if subcriteria values are present from the form (for the "many" class),
            //then include subcriteria search values in main search (the "one" class)
            Criteria subCriteriaLanguagePairs = null;
            if (!SourceId.equals("0") || !TargetId.equals("0")) {
                subCriteriaLanguagePairs = criteria.createCriteria("LanguagePairs");
            }
            if (!SourceId.equals("0")) {
                subCriteriaLanguagePairs.add(Expression.eq("sourceId", Integer.valueOf(SourceId)));
            }
            if (!TargetId.equals("0")) {
                subCriteriaLanguagePairs.add(Expression.eq("targetId", Integer.valueOf(TargetId)));
            }

            if (!Status.equals("0")) {
                criteria.add(Expression.eq("status", Status));
            }
            if (IncludeDoNot == null) {
                criteria.add(Expression.eq("doNotUse", new Boolean(false)));
            }

            if (Translator != null) {
                criteria.add(Expression.eq("translator", new Boolean(true)));
            }
            if (Editor != null) {
                criteria.add(Expression.eq("editor", new Boolean(true)));
            }
            if (Proofreader != null) {
                criteria.add(Expression.eq("proofreader", new Boolean(true)));
            }
            if (other != null) {
                criteria.add(Expression.eq("other", new Boolean(true)));
            }
            if (consultant != null) {
                criteria.add(Expression.eq("consultant", new Boolean(true)));
            }
            if (partner != null) {
                criteria.add(Expression.eq("partner", new Boolean(true)));
            }
            if (engineering != null) {
                criteria.add(Expression.eq("engineering", new Boolean(true)));
            }
            if (businesssuport != null) {
                criteria.add(Expression.eq("businesssuport", new Boolean(true)));
            }
            if (fqa != null) {
                criteria.add(Expression.eq("fqa", new Boolean(true)));
            }

            if (Dtp != null) {
                criteria.add(Expression.eq("dtp", new Boolean(true)));
            }
            if (Icr != null) {
                criteria.add(Expression.eq("icr", new Boolean(true)));
            }

            //if subcriteria values are present from the form (for the "many" class),
            //then include subcriteria search values in main search (the "one" class)
            Criteria subCriteriaRateScoreLanguages = null;
            boolean sourceSet = false;
            boolean targetSet = false;
            boolean ratescorelanguagesCon = false;

            if (RateOldDb != null) {
                Disjunction any = Expression.disjunction();

                if (TRate.length() >= 1) {
                    Conjunction all = Expression.conjunction();
                    all.add(Expression.gt("t", new Double(0)));
                    all.add(Expression.le("t", Double.valueOf(TRate)));
                    any.add(all);
                }
                if (ERate.length() >= 1) {
                    Conjunction all = Expression.conjunction();
                    all.add(Expression.gt("e", new Double(0)));
                    all.add(Expression.le("e", Double.valueOf(ERate)));
                    any.add(all);
                }
                if (TERate.length() >= 1) {
                    Conjunction all = Expression.conjunction();
                    all.add(Expression.gt("te", new Double(0)));
                    all.add(Expression.le("te", Double.valueOf(TERate)));
                    any.add(all);
                }
                if (PRate.length() >= 1) {
                    Conjunction all = Expression.conjunction();
                    all.add(Expression.gt("p", new Double(0)));
                    all.add(Expression.le("p", Double.valueOf(PRate)));
                    any.add(all);
                }
                criteria.add(any);
            }

            if (Specific.length > 0 && !Specific[0].equals("0")) {
                Criteria subCriteriaSpecificIndustries = criteria.createCriteria("SpecificIndustries");
                Integer[] specificIds = new Integer[Specific.length];
                for (int i = 0; i < specificIds.length; i++) {
                    specificIds[i] = new Integer(Specific[i]);
                }
                subCriteriaSpecificIndustries.add(Expression.in("specificIndustryId", specificIds));
            }
            if (General.length > 0 && !General[0].equals("0")) {
                Criteria subCriteriaIndustries = criteria.createCriteria("Industries");
                Integer[] generalIds = new Integer[General.length];
                for (int i = 0; i < generalIds.length; i++) {
                    generalIds[i] = new Integer(General[i]);
                }
                subCriteriaIndustries.add(Expression.in("industryId", generalIds));
            }

//                        boolean rateScoreScore = false;
//                        for(int i = 0; i < ScoresLin.length; i++) {
//                            if(ScoresLin[i] != null && ScoresLin[i].length() >= 1 && ratescorelanguagesCon == false) {
//                                subCriteriaRateScoreLanguages = subCriteriaLanguagePairs.createCriteria("RateScoreLanguages");
//                                ratescorelanguagesCon = true;
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
            if (ScoreOldDb != null) {
                Disjunction any = Expression.disjunction();

                if (ScoresLin[0] != null && ScoresLin[0].length() >= 1) {
                    any.add(Expression.ge("medicalScore", Double.valueOf(ScoresLin[0])));
                }
                if (ScoresLin[1] != null && ScoresLin[1].length() >= 1) {
                    any.add(Expression.ge("technicalScore", Double.valueOf(ScoresLin[1])));
                }
                if (ScoresLin[2] != null && ScoresLin[2].length() >= 1) {
                    any.add(Expression.ge("softwareScore", Double.valueOf(ScoresLin[2])));
                }
                if (ScoresLin[3] != null && ScoresLin[3].length() >= 1) {
                    any.add(Expression.ge("legalFinancialScore", Double.valueOf(ScoresLin[3])));
                }
                criteria.add(any);
            }

            if (!ProjectScoreGreater.equals("null")) {
                criteria.add(Expression.ge("projectScoreAverage", Double.valueOf(ProjectScoreGreater)));
            }

            if (UsesTrados != null) {
                criteria.add(Expression.eq("usesTrados", new Boolean(true)));
            }
            if (UsesDejavu != null) {
                criteria.add(Expression.eq("usesDejavu", new Boolean(true)));
            }
            if (UsesCatalyst != null) {
                criteria.add(Expression.eq("usesCatalyst", new Boolean(true)));
            }
            if (UsesSdlx != null) {
                criteria.add(Expression.eq("usesSdlx", new Boolean(true)));
            }
            if (UsesTransit != null) {
                criteria.add(Expression.eq("usesTransit", new Boolean(true)));
            }
            if (UsesOtherTool1.length() >= 1 || UsesOtherTool2.length() >= 1) {
                Criteria subCriteria = criteria.createCriteria("ResourceTools");
                Disjunction any = Expression.disjunction();
                if (UsesOtherTool2.length() == 0) {
                    subCriteria.add(Expression.like("description", "%" + UsesOtherTool1 + "%").ignoreCase());
                } else {
                    any.add(Expression.like("description", "%" + UsesOtherTool1 + "%").ignoreCase());
                    any.add(Expression.like("description", "%" + UsesOtherTool2 + "%").ignoreCase());
                    subCriteria.add(any);
                }
            }

            if (City.length() >= 1) {
                criteria.add(Expression.like("City", "%" + City + "%").ignoreCase());
            }
            if (!Country.equals("0")) {
                criteria.add(Expression.eq("Country", Country));
            }

            if (Resume.length() >= 1) {
                criteria.add(Expression.like("resume", "%" + Resume + "%").ignoreCase());
            }

            //sort results by id from old database
            criteria.addOrder(Order.asc("resourceId"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            //get results
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

    //search for lin tasks for a given team member
    public List getResourceLinTasks(String resourceId) {

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;

        try {
            //retreive clients from database

            //bind to the main criteria
            Criteria criteria = session.createCriteria(LinTask.class);

            criteria.add(Expression.eq("personName", resourceId));
            criteria.addOrder(Order.desc("linTaskId"));

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

    //search for eng tasks for a given team member
    public List getResourceEngTasks(String resourceId) {

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;

        try {
            //retreive clients from database

            //bind to the main criteria
            Criteria criteria = session.createCriteria(EngTask.class);

            criteria.add(Expression.eq("personName", resourceId));
            criteria.addOrder(Order.desc("engTaskId"));

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

    //search for dtp tasks for a given team member
    public List getResourceDtpTasks(String resourceId) {

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;

        try {
            //retreive clients from database

            //bind to the main criteria
            Criteria criteria = session.createCriteria(DtpTask.class);

            criteria.add(Expression.eq("personName", resourceId));
            criteria.addOrder(Order.desc("dtpTaskId"));

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

    //search for oth tasks for a given team member
    public List getResourceOthTasks(String resourceId) {

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;

        try {
            //retreive clients from database

            //bind to the main criteria
            Criteria criteria = session.createCriteria(OthTask.class);

            criteria.add(Expression.eq("personName", resourceId));
            criteria.addOrder(Order.desc("othTaskId"));

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

    //return one resource with id given in argument
    public Resource getSingleResource(Integer id) {

        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Resource as resource where resource.resourceId = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                Resource r = (Resource) results.get(0);

                //Hibernate.initialize(r.getProjects());
                Hibernate.initialize(r.getIndustries());
                Hibernate.initialize(r.getSpecificIndustries());
                Hibernate.initialize(r.getLanguagePairs());
                Hibernate.initialize(r.getUnavails());

                Hibernate.initialize(r.getResourceTools());
                Hibernate.initialize(r.getResourceContacts());

                Hibernate.initialize(r.getRateScoreDtps());

                //Hibernate.initialize(r.getProjectScores());
                return r;
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

    //return one unavail with id given in argument
    public Unavail getSingleUnavail(Integer id) {

        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Unavail as unavail where unavail.unavailId = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                Unavail r = (Unavail) results.get(0);

                return r;
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

    //return one rateScoreCategory with id given in argument
    public RateScoreCategory getSingleRateScoreCategory(Integer id) {

        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from RateScoreCategory as rateScoreCategory where rateScoreCategory.rateScoreCategoryId = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                RateScoreCategory r = (RateScoreCategory) results.get(0);

                return r;
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

    //return one ratescorelanguage with id given in argument
    public RateScoreLanguage getSingleRateScoreLanguage(Integer id) {

        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from RateScoreLanguage as ratescorelanguage where ratescorelanguage.RateScoreLanguageId = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                RateScoreLanguage r = (RateScoreLanguage) results.get(0);

                return r;
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

    //return one resource with id given in argument
    //don't load lazy collections
    public Resource getSingleResourceNoLazy(Integer id) {

        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Resource as resource where resource.resourceId = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                Resource r = (Resource) results.get(0);

                return r;
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

    //return one resourceContact with name given in argument
    public ResourceContact getSingleResourceContact(Integer id) {

        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from ResourceContact as resourceContact where resourceContact.resourceContactId = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                ResourceContact rc = (ResourceContact) results.get(0);
                return rc;
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

    //return one languagePair with id given in argument
    public LanguagePair getSingleLanguagePair(Integer id) {

        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from LanguagePair as languagePair where languagePair.languagePairId = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                LanguagePair lp = (LanguagePair) results.get(0);
                return lp;
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

    //return one client contact with id given in argument
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

    //get all industries
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
             */
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

    //build the link between languagePair and ratescorelanguage
    public Integer linkLanguagePairRateScoreLanguage(LanguagePair lp, RateScoreLanguage rsl) {
        Session session = ConnectionFactory.getInstance().getSession();
        Integer id = null;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            //link languagePair and ratescorelanguage
            lp.getRateScoreLanguages().add(rsl);
            rsl.setLanguagePair(lp);

            session.saveOrUpdate(rsl);

            tx.commit();
            return rsl.getRateScoreLanguageId();
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

    //build the link (many-to-many) between resource and specificIndustry
    public boolean linkResourceSpecificIndustry(Resource r, SpecificIndustry si) {
        Session session = ConnectionFactory.getInstance().getSession();
        Integer id = null;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            unlinkResourceSpecificIndustryDelete(r, si);

            //link resource and specificIndustry
            r.getSpecificIndustries().add(si);
            si.getResources().add(r);

            session.saveOrUpdate(r);

            tx.commit();
            return true;
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

    //remove the link (many-to-many) between resource and industry
    public void unlinkResourceIndustry(Resource r, Industry[] generals, Integer[] generalIds) {
        List deleteList = new ArrayList();

        //search for repeats
        for (int i = 0; i < generalIds.length; i++) {
            boolean remove = true;
            for (int j = 0; j < generals.length; j++) {
                if (generalIds[i].equals(generals[j].getIndustryId())) {
                    remove = false;
                }
            }
            if (remove) {
                Industry i2 = ClientService.getInstance().getSingleIndustry(generalIds[i]);
                for (Iterator iter = r.getIndustries().iterator(); iter.hasNext();) {
                    i2 = (Industry) iter.next();
                    if (i2.getIndustryId().equals(generalIds[i])) {
                        deleteList.add(i2);
                    }
                }
            }
        }

        //delete repeats
        for (ListIterator iter = deleteList.listIterator(); iter.hasNext();) {
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
            for (Iterator iter = r.getIndustries().iterator(); iter.hasNext();) {
                Industry i2 = (Industry) iter.next();
                if (i2.getIndustryId().equals(i.getIndustryId())) {
                    r.getIndustries().remove(i2);
                    break;
                }
            }

            session.saveOrUpdate(r);

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

    //remove the link (many-to-many) between resource and specific industry
    public void unlinkResourceSpecificIndustry(Resource r, SpecificIndustry[] generals, Integer[] generalIds) {
        List deleteList = new ArrayList();

        //search for repeats
        for (int i = 0; i < generalIds.length; i++) {
            boolean remove = true;
            for (int j = 0; j < generals.length; j++) {
                if (generalIds[i].equals(generals[j].getSpecificIndustryId())) {
                    remove = false;
                }
            }
            if (remove) {
                SpecificIndustry i2 = ClientService.getInstance().getSingleSpecificIndustry(generalIds[i]);
                for (Iterator iter = r.getSpecificIndustries().iterator(); iter.hasNext();) {
                    i2 = (SpecificIndustry) iter.next();
                    if (i2.getSpecificIndustryId().equals(generalIds[i])) {
                        deleteList.add(i2);
                    }
                }
            }
        }

        //delete repeats
        for (ListIterator iter = deleteList.listIterator(); iter.hasNext();) {
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
            for (Iterator iter = r.getSpecificIndustries().iterator(); iter.hasNext();) {
                SpecificIndustry i2 = (SpecificIndustry) iter.next();
                if (i2.getSpecificIndustryId().equals(i.getSpecificIndustryId())) {
                    r.getSpecificIndustries().remove(i2);
                    break;
                }
            }

            session.saveOrUpdate(r);

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

    //build the link (many-to-many) between resource and industry
    public boolean linkResourceIndustry(Resource r, Industry i) {
        Session session = ConnectionFactory.getInstance().getSession();
        Integer id = null;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            unlinkResourceIndustryDelete(r, i);
            //link resource and industry
            r.getIndustries().add(i);
            i.getResources().add(r);

            session.saveOrUpdate(r);

            tx.commit();
            return true;
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

    //provide a list of statuses for forms (in clients)
    public String[] getStatuses() {
        String Status[] = {"Prospect", "Active", "Lost"};
        return Status;
    }

    //delete one resource
    public void deleteResourceContact(ResourceContact rc) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(rc);
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
    public void deletePastDateUnavailableDates() {

        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Unavail as unavail where endDate<sysdate()",
                    new Object[]{},
                    new Type[]{});

            if (!results.isEmpty()) {
                for (Iterator unavails = results.iterator(); unavails.hasNext();) {
                    Unavail tempUnavail = (Unavail) unavails.next();
                    ResourceService.getInstance().deleteUnavail(tempUnavail);
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

    //update an existing resource in database
    public MALinguistic updateMALinguistic(MALinguistic r) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(r);

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
        return r;
    }

    //update an existing resource in database
    public MADtpEng updateMADtpEng(MADtpEng r) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(r);

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
        return r;
    }

    //update an existing resource in database
    public MAOther updateMAOther(MAOther r) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(r);

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
        return r;
    }

    //update an existing resource in database
    public MAExpert updateMAExpert(MAExpert r) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(r);

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
        return r;
    }

    //return one resource with id given in argument
    public MALinguistic getSingleLinMA(Integer id) {

        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from MALinguistic as maLinguistic where id_resource = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                MALinguistic r = new MALinguistic();
                r.setId_resource(id);
                return r;
            } else {
                MALinguistic r = (MALinguistic) results.get(0);

                //Hibernate.initialize(r.getProjectScores());
                return r;
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

    //return one resource with id given in argument
    public MADtpEng getSingleDtpEngMA(Integer id) {

        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from MADtpEng as maDtpEng where id_resource = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                MADtpEng r = new MADtpEng();
                r.setId_resource(id);
                return r;
            } else {
                MADtpEng r = (MADtpEng) results.get(0);

                //Hibernate.initialize(r.getProjectScores());
                return r;
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

    //return one resource with id given in argument
    public MAExpert getSingleExpertMA(Integer id) {

        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from MAExpert as maDtpEng where id_resource = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                MAExpert r = new MAExpert();
                r.setId_resource(id);
                return r;
            } else {
                MAExpert r = (MAExpert) results.get(0);

                //Hibernate.initialize(r.getProjectScores());
                return r;
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

    //return one resource with id given in argument
    public MAOther getSingleOtherMA(Integer id) {

        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from MAOther as maDtpEng where id_resource = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                MAOther r = new MAOther();
                r.setId_resource(id);
                return r;
            } else {
                MAOther r = (MAOther) results.get(0);

                //Hibernate.initialize(r.getProjectScores());
                return r;
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

    //return one rateScoreCategory with id given in argument
    public Mechanical getMaScore() {

        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
         */
        int id = 1;
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Mechanical as mechanical where mechanical.id_ma = 1");

            return (Mechanical) results.get(0);
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

    public Mechanical updateMechanical(Mechanical r) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(r);

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
        return r;
    }

    public Evaluation getEvaluation(Integer id) {
        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Evaluation as evaluation where evaluation.eval_id =" + id);

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
            return (Evaluation) results.get(0);
        }

    }

    public AssesEval getSingleAssesEval(Integer id) {
        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from AssesEval as evaluation where evaluation.eval_id =" + id);

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
            return (AssesEval) results.get(0);
        }

    }

    public static AssesEval getSingleAssesEvalForResource(Integer id) {
        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from AssesEval as evaluation where evaluation.resourceId =" + id);

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
            return (AssesEval) results.get(0);
        }

    }

    List getCoupleList(Integer resourceId, String yesno) {
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        Query query;
        try {

            // results = session.find("from evaluation as evaluation where evaluation.evaluation_area =? and evaluation.resourceId=?",
            //                       new Object[] {dropdown,resource},
            //                       new Type[] {Hibernate.STRING,Hibernate.STRING} );
            query = session.createQuery("select couples from app.resource.Couples couples where couples.yesno = '" + yesno + "' and couples.resourceid=" + resourceId);
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

    //get all resources
    public Couples getCoupleListByType(Integer resourceid, Integer coupleid, String type) {
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {
            results = session.find("from Couples as couples where couples.resourcetype = '" + type + "' and couples.resourceid=" + resourceid + " and couples.coupleid=" + coupleid);
            //        results = session.find("from AssesEval as evaluation where evaluation.eval_id ="+id);
        } catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (Couples) results.get(0);
        }
    }

    public Couples getSingleCouples(Integer id) {
        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {
            results = session.find("from Couples as couples where couples.id =" + id);
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
            return (Couples) results.get(0);
        }

    }

    Resource getSingleResourceByName(String name) {
        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     * resource.lastName, resource.firstName, resource.companyName
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;

        String[] nameSplit = name.split(" ", 2);
        try {
            results = session.find("from Resource as resource where resource.companyName ='" + name + "'");
            if (results.isEmpty()) {
                results = session.find("from Resource as resource where resource.firstName ='" + nameSplit[0] + "' and resource.lastName ='" + nameSplit[1] + "'");
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
        if (results.isEmpty()) {
            return null;
        } else {
            return (Resource) results.get(0);
        }
    }

    //get all resources
    public List getResourceEditorList() {
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
            query = session.createQuery("select resource from app.resource.Resource resource where resource.editor = true  order by resource.lastName, resource.firstName, resource.companyName");
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

    //get all resources
    public List getResourceTranslatorList() {
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
            query = session.createQuery("select resource from app.resource.Resource resource where resource.translator = true  order by resource.lastName, resource.firstName, resource.companyName");
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

    List getResourceClientList(Integer resourceId) {

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
            query = session.createQuery("select resourceClient from app.resource.ResourceClient resourceClient where resourceClient.resourceId = " + resourceId);
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

    void unlinkResourceClient(int id) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from resourceclient where resourceid = " + id);
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

    }

    public ResourceClient getSingleResourceClient(int id) {
        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {
            results = session.find("from ResourceClient as resourceClient where resourceClient.id =" + id);
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
            return (ResourceClient) results.get(0);
        }

    }

    void addResourceClient(ResourceClient rc) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(rc);

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

    public List getResourceSearch(String currency, String SourceId, String TargetId, String generalId, String medicalScore, String technicalScore, String softwareScore, String legalScore, String marketingScore,
            boolean isPrefVen, String pc_criteria, String projectCount) {

        Session session = ConnectionFactory.getInstance().getSession();


        List results = null;

   
        try {
            //retreive resources from database
             Map<String, Integer> res = null;
            if (projectCount.length() >= 1) {
                Map<String, Integer> result = TeamHelper.getVendorProjCounts();
               
                if (pc_criteria.equalsIgnoreCase("le")) {
                    res = result.entrySet().stream()
		.filter(x -> x.getValue() <= Integer.parseInt(projectCount))
		.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

                } else {
                   res = result.entrySet().stream()
		.filter(x -> x.getValue() >= Integer.parseInt(projectCount))
		.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

                }
                
                
            }

            //this is the "one" class: it is the main criteria
            Criteria criteria = session.createCriteria(Resource.class);
            if(res!=null){  
            Integer[] teamClientIds = new Integer[res.keySet().size()];
                if(res.keySet().size()>0){
                int i=0;
                 for(String key : res.keySet()){
                     teamClientIds[i++] = new Integer(key);
                 }
                  criteria.add(Expression.in("resourceId", teamClientIds));
                }
            }
   
//                if (!resultsTeamClient.isEmpty()) {
//                    Integer[] teamClientIds = new Integer[resultsTeamClient.size()];
//                    for (int i = 0; i < resultsTeamClient.size(); i++) {
//                        ResourceClient rc = (ResourceClient) resultsTeamClient.get(i);
//
//                        teamClientIds[i] = new Integer(rc.getResourceId());
//                    }
//
//                    criteria.add(Expression.in("resourceId", teamClientIds));
//                } else {
//                    criteria.add(Expression.like("resourceId", Integer.valueOf(0)));
//                }
  

            try {
                if (currency.length() >= 1) {
                    criteria.add(Expression.eq("currency", currency));
                }
            } catch (Exception e) {
            }

            Criteria subCriteriaLanguagePairs = null;
            if (SourceId.length() >= 1 || TargetId.length() >= 1 || isPrefVen) {
                subCriteriaLanguagePairs = criteria.createCriteria("LanguagePairs");
            }
            if (SourceId.length() >= 1) {
                subCriteriaLanguagePairs.add(Expression.eq("sourceId", Integer.valueOf(SourceId)));
            }
            if (TargetId.length() >= 1) {
                subCriteriaLanguagePairs.add(Expression.eq("targetId", Integer.valueOf(TargetId)));
            }
            if (isPrefVen) {
                subCriteriaLanguagePairs.add(Expression.eq("prefferedVendor", true));
            }

            Criteria subCriteriaRateScoreLanguages = null;
            if (medicalScore.length() >= 1 || technicalScore.length() >= 1 || softwareScore.length() >= 1 || legalScore.length() >= 1 || marketingScore.length() >= 1) {

                subCriteriaRateScoreLanguages = subCriteriaLanguagePairs.createCriteria("RateScoreLanguages");

                if (SourceId != null) {

                    subCriteriaRateScoreLanguages.add(Expression.eq("source", ProjectService.getInstance().getLanguage(Integer.valueOf(SourceId)).getLanguage()));
                }
                if (TargetId != null) {
                    subCriteriaRateScoreLanguages.add(Expression.eq("target", ProjectService.getInstance().getLanguage(Integer.valueOf(TargetId)).getLanguage()));
                }

                if (medicalScore.length() >= 1) {
                    subCriteriaRateScoreLanguages.add(Expression.eq("specialty", "Medical"));
                    subCriteriaRateScoreLanguages.add(Expression.ge("score", Double.valueOf(medicalScore)));
                }
                if (technicalScore.length() >= 1) {
                    subCriteriaRateScoreLanguages.add(Expression.eq("specialty", "Technical"));
                    subCriteriaRateScoreLanguages.add(Expression.ge("score", Double.valueOf(technicalScore)));
                }
                if (softwareScore.length() >= 1) {
                    subCriteriaRateScoreLanguages.add(Expression.eq("specialty", "Software"));
                    subCriteriaRateScoreLanguages.add(Expression.ge("score", Double.valueOf(softwareScore)));
                }
                if (legalScore.length() >= 1) {
                    subCriteriaRateScoreLanguages.add(Expression.eq("specialty", "Legal/Financial"));
                    subCriteriaRateScoreLanguages.add(Expression.ge("score", Double.valueOf(legalScore)));
                }
                if (marketingScore.length() >= 1) {
                    subCriteriaRateScoreLanguages.add(Expression.eq("specialty", "Marketing"));
                    subCriteriaRateScoreLanguages.add(Expression.ge("score", Double.valueOf(marketingScore)));
                }

            }

            if (generalId.length() >= 1) {
                Criteria subCriteriaIndustries = criteria.createCriteria("Industries");
                subCriteriaIndustries.add(Expression.eq("industryId", Integer.valueOf(generalId)));
            }

            //sort results by id from old database
            criteria.addOrder(Order.asc("resourceId"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            //get results
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

    public boolean checkLogin(String username, String password) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        MessageDigest md = null; //check password against hash of password
        try {
            md = MessageDigest.getInstance("SHA"); //step 2
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        try {
            md.update(password.getBytes("UTF-8")); //step 3
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        byte raw[] = md.digest(); //step 4
        String hash = (new BASE64Encoder()).encode(raw); //step 5
        password = hash;

        try {

            results = session.find("from  Resource as resource  where resource.username = ? and resource.password = ? and isEnabled = 1 ",
                    new Object[]{username, password},
                    new Type[]{Hibernate.STRING, Hibernate.STRING});

        } catch (ObjectNotFoundException onfe) {
            return false;
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
            return false;
        } else {
            return true;
        }
    }

    public Resource getSingleResourceByUserName(String username) {
        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     * resource.lastName, resource.firstName, resource.companyName
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;

        try {
            results = session.find("from Resource as resource where resource.username ='" + username + "'");

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
            return (Resource) results.get(0);
        }
    }

    public Resource getSingleResource(String resource) {
        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     * resource.lastName, resource.firstName, resource.companyName
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;

        try {
            results = session.find("from Resource as resource where resource.companyName ='" + resource + "'");

        } catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException e) {
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
        if (results.isEmpty()) {
            session = ConnectionFactory.getInstance().getSession();
            results = null;

            try {
                results = session.find("from Resource as resource where resource.firstName ='" + resource.split(" ", 2)[0] + "' and  resource.lastName ='" + resource.split(" ", 2)[1] + "'");

            } catch (ObjectNotFoundException onfe) {
                return null;
            } catch (HibernateException e) {
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
            if (results.isEmpty()) {
                return null;
            } else {
                return (Resource) results.get(0);
            }

        } else {
            return (Resource) results.get(0);
        }
    }

    public long updateUserSessionInfo(String username) {
        Resource u = ResourceService.getInstance().getSingleResourceByUserName(username);

        long lastUpdateUserSessionTime = System.currentTimeMillis();

//        u.setLast_activity(new java.sql.Timestamp(lastUpdateUserSessionTime));
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(u);

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

        return lastUpdateUserSessionTime;

    }

    public List getResumeList(int resourceId, String category) {
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
            query = session.createQuery("select uload from app.extjs.vo.Upload_Doc uload where type = '" + category + "' and resourceId =" + resourceId);
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

    public Upload_Doc getDocList(int docId) {
        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     * resource.lastName, resource.firstName, resource.companyName
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;

        try {
            results = session.find("from Upload_Doc as uploadDoc where uploadDoc.uploadDoc ='" + docId + "'");

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

    List<ResourceCompDomain> getCompetanceDomain(Integer ID_LanguagePair) {
        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     * resource.lastName, resource.firstName, resource.companyName
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;

        try {
            results = session.find("from ResourceCompDomain as compDomain where compDomain.ID_LanguagePair =" + ID_LanguagePair + "");

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
            return results;
        }

    }

    void addResourceCompDomain(ResourceCompDomain rcd) {
        Session session = ConnectionFactory.getInstance().getSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(rcd);

            tx.commit();

            // resourceId = r.getResourceId();
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

//    public List<Project> getProjectWithoutTot(int year){
//       /*
//     * Use the ConnectionFactory to retrieve an open
//     * Hibernate Session.
//     * resource.lastName, resource.firstName, resource.companyName
//     */
//    Session session = ConnectionFactory.getInstance().getSession();
//    List results = null;
//
//    try {
//        
//         String queryStr = "select  rsl.score,specialty,rsl.source,rsl.target ,lp.ID_Resource " +
//                              "from   languagepair lp  " +
//                              "left join ratescorelanguage rsl on lp.`ID_LanguagePair`=rsl.`ID_LanguagePair` " +
//                              "where rsl.score <21 ";
//            PreparedStatement st = session.connection().prepareStatement(queryStr);
//            ResultSet rs = st.executeQuery();
//            while(rs.next()) {
//                results.
//                String key = rs.getString(2)+"#"+rs.getString(3)+"#"+rs.getString(4)+"#"+rs.getString(5);
//                lowISAList.add(key);
//                isaVendor.put(key, rs.getInt(1));
////                //System.out.println(rs.getString(1)+"|"+rs.getString(2)+"|"+rs.getString(3)+"|"+rs.getString(4));
//            }
//      results = session.find("from Project as project where project.typeOfText = null and EXTRACT(YEAR from project.startDate) =" + year + "");
//      
//    } catch (ObjectNotFoundException onfe) {
//      return null;
//    } catch (HibernateException e) {
//      /*
//       * All Hibernate Exceptions are transformed into an unchecked
//       * RuntimeException.  This will have the effect of causing the
//       * user's request to return an error.
//       *
//       */
//      System.err.println("Hibernate Exception" + e.getMessage());
//      throw new RuntimeException(e);
//    } /*
//     * Regardless of whether the above processing resulted in an Exception
//     * or proceeded normally, we want to close the Hibernate session.  When
//     * closing the session, we must allow for the possibility of a Hibernate
//     * Exception.
//     *
//     */ finally {
//      if (session != null) {
//        try {
//          session.close();
//        } catch (HibernateException e) {
//          System.err.println("Hibernate Exception" + e.getMessage());
//          throw new RuntimeException(e);
//        }
//
//      }
//    }
//    if (results.isEmpty()) {
//      return null;
//    } else {
//        return results;
//    }   
//    
//       
//    }
    public JSONObject getVendorWithLowISA(int year) {

        List<String> lowISAList = new ArrayList<>();
        HashMap<String, Integer> isaVendor = new HashMap<>();
        JSONObject resultObj = new JSONObject();

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            String queryStr = "select  rsl.score,specialty,rsl.source,rsl.target ,lp.ID_Resource "
                    + "from   languagepair lp  "
                    + "left join ratescorelanguage rsl on lp.`ID_LanguagePair`=rsl.`ID_LanguagePair` "
                    + "where rsl.score <21 ";
            PreparedStatement st = session.connection().prepareStatement(queryStr);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String key = rs.getString(2) + "#" + rs.getString(3) + "#" + rs.getString(4) + "#" + rs.getString(5);
                lowISAList.add(key);
                isaVendor.put(key, rs.getInt(1));
//                //System.out.println(rs.getString(1)+"|"+rs.getString(2)+"|"+rs.getString(3)+"|"+rs.getString(4));
            }
            //  return query.list();
        } catch (HibernateException e) {
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } catch (SQLException ex) {
            Logger.getLogger(ResourceService.class.getName()).log(Level.SEVERE, null, ex);
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

        session = ConnectionFactory.getInstance().getSession();
        JSONArray result = new JSONArray();
        JSONArray resultNoTot = new JSONArray();
        HashMap<String, String> totHashMap = new HashMap<>();
        totHashMap.put("Medical", "Medical");
        totHashMap.put("Technical", "Technical");
        totHashMap.put("Software", "Software");
        totHashMap.put("General", "General");
        totHashMap.put("Marketing", "Marketing");
        totHashMap.put("Financial", "Legal/Financial");
        totHashMap.put("Legal", "Legal/Financial");
        totHashMap.put("Regulatory", "Technical");
//        totHashMap.put("", "Medical");

//        "Medical"    --> Medical
//"Technical"  --> Technical
//"Software"  --> Software
//"General"  --> Medical
//"Marketing"  --> Marketing
//"Legal"  --> Legal/Financial
//"Financial"  --> Legal/Financial
//"Regulatory"  --> Technical
        try {
            List pNum = new ArrayList();
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            String queryStr = "select typeoftext ,s.`language`,t.`language`, p.number, c.`Company_code`,  r.ID_Resource, "
                    + "p.`pm`,r.`companyName`,r.firstname,r.lastname, l.taskname, p.id_project, p.srcLang,p.targetLang,p.task "
                    + "from project p left join sourcedoc s on s.ID_Project =  p.`ID_Project`  "
                    + "left join client_Information c on c.`ID_Client`=p.`ID_Client` "
                    + "left join targetdoc t on t.`ID_SourceDoc` =s.`ID_SourceDoc` "
                    + "left join lintask l on l.`ID_TargetDoc`=t.`ID_TargetDoc`  "
                    + "left join resource r on r.`ID_Resource`= l.`personName` "
                    + "where EXTRACT(YEAR from p.startdate) = " + year + " and r.ID_Resource is not null order by p.number desc;";
            PreparedStatement st = session.connection().prepareStatement(queryStr);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                JSONObject res = new JSONObject();
                if (StandardCode.getInstance().noNull(rs.getString(1)).equalsIgnoreCase("")) {
                    if (!pNum.contains(rs.getInt("number"))) {
                        String name = "";
                        if (name.isEmpty()) {
                            name = rs.getString(9) + " " + rs.getString(10);
                        }
                        if (name.trim().isEmpty()) {
                            name = rs.getString(8);
                        }
                        pNum.add(rs.getInt("number"));
                        res.put("pNumber", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:openProjectWindow('" + rs.getInt("number") + rs.getString("Company_code") + "','" + rs.getString("ID_Project") + "')\">" + rs.getInt("number") + rs.getString("Company_code") + "</a>");
                        try {
                            res.put("source", rs.getString(13));
                            res.put("target", rs.getString(14));
                            res.put("taskname", rs.getString(15));
                        } catch (Exception e) {
                        }
                        res.put("typeOfText", "<p style='color:red'>No Text Type</p>");
                        res.put("score", "-");
                        res.put("resource", name);
                        res.put("pm", rs.getString(7));

                        resultNoTot.put(res);

                    }

                } else {

                    String key = totHashMap.get(rs.getString(1)) + "#" + rs.getString(2) + "#" + rs.getString(3) + "#" + rs.getString(6);
                    //System.out.println(key);
                    if (lowISAList.contains(key)) {

                        String name = "";
                        if (name.isEmpty()) {
                            name = rs.getString(9) + " " + rs.getString(10);
                        }
                        if (name.trim().isEmpty()) {
                            name = rs.getString(8);
                        }

                        res.put("pNumber", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:openProjectWindow('" + rs.getInt("number") + rs.getString("Company_code") + "','" + rs.getString("ID_Project") + "')\">" + rs.getInt("number") + rs.getString("Company_code") + "</a>");
                        res.put("source", rs.getString(2));
                        res.put("target", rs.getString(3));
                        res.put("typeOfText", rs.getString(1));
                        res.put("score", isaVendor.get(key));
                        res.put("resource", name);
                        res.put("pm", rs.getString(7));
                        res.put("taskname", rs.getString(11));
                        result.put(res);
                    }

                }
//                //System.out.println(rs.getString(1)+"|"+rs.getString(2)+"|"+rs.getString(3)+"|"+rs.getString(4));
            }
            //  return query.list();
        } catch (HibernateException e) {
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } catch (SQLException ex) {
            Logger.getLogger(ResourceService.class.getName()).log(Level.SEVERE, null, ex);
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

        resultObj.put("lowISA", result);
        resultObj.put("noTot", resultNoTot);

        return resultObj;
    }

    CompetanceTranslation getCompetanceTranslation(Integer resourceId) {
        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     * resource.lastName, resource.firstName, resource.companyName
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;

        try {
            results = session.find("select compTrans from CompetanceTranslation compTrans where resourceId =" + resourceId + "");

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
            return (CompetanceTranslation) results.get(0);
        }

    }

    void addUpdateCompetanceTranslattion(CompetanceTranslation cot) {

        Session session = ConnectionFactory.getInstance().getSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(cot);

            tx.commit();

            // resourceId = r.getResourceId();
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

    ResourceTool getResourceTool(Integer resourceToolId) {
        /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     * resource.lastName, resource.firstName, resource.companyName
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;

        try {
            results = session.find("select rt from ResourceTool rt where ID_ResourceTool =" + resourceToolId + "");

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
            return (ResourceTool) results.get(0);
        }

    }

    public String getVendorName(Resource r) {
        String name = "";
        if ((r.getFirstName().length() >= 1 && r.getFirstName() != null) && (r.getLastName().length() >= 1 && r.getLastName() != null)) {
            name = StandardCode.getInstance().noNull(r.getFirstName()) + " " + StandardCode.getInstance().noNull(r.getLastName());
        } else {
            name = StandardCode.getInstance().noNull(r.getCompanyName());
        }
        return name;
    }

}
