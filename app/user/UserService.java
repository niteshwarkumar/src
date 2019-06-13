//UserService.java contains the db code related to users to actually read/write
//to/from db.  it is also responsible for making sure users have provided
//proper login values (username and password match an entry in db).
//It also handles hr module related information such as getUserList()
//and updateUser()
package app.user;

import app.admin.AdminMisc;
import net.sf.hibernate.*;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.*;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.Criteria;
import net.sf.hibernate.expression.*;
import java.util.*;
import app.db.*;
import app.extjs.vo.Upload_Doc;
import app.standardCode.StandardCode;
import java.sql.PreparedStatement;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import sun.misc.BASE64Encoder;

public class UserService {

    private static UserService instance = null;

    public UserService() {
    }

    //place a new user in the db
    public Integer addUser(User u) {

        //the new User's id
        Integer id = null;

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.save(u);
            tx.commit();

            id = u.getUserId();
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


     //place a new user in the db
    public Integer addLocation(Location loc) {

        //the new User's id
        Integer id = null;

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(loc);
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

     //place a new Position in the db
    public Integer addPosition(Position1 pos) {

        //the new User's id
        Integer id = null;

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(pos);
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


    //place a new user in the db
    public Integer addDepartment(Department dep) {

        //the new User's id
        Integer id = null;

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(dep);
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


    //place a new user in the db
    public void addTrainingInitial(TrainingInitial ti) {

        //the new User's id
        Integer id = null;

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(ti);
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

      public void addTrainingInitialAdmin(TrainingInitialAdmin ti) {

        //the new User's id
        Integer id = null;

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(ti);
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


    //place a new away event (vacation/sick/travel day) in the db
    //build one-to-many link between user and away
    public Integer addAway(Away a, User u) {

        //the new User's id
        Integer id = null;

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            //build relationship
            u.getAway().add(a);
            a.setUser(u);

            session.save(a);
            tx.commit();

            id = a.getAwayId();
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

    //place a new user privilege in the db
    //build one-to-many link between user and privilege
    public Integer addPrivilege(Privilege p, User u) {

        //the new User's id
        Integer id = null;

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            //build relationship
            u.getPrivileges().add(p);
            p.setUser(u);

            session.save(p);
            tx.commit();

            id = p.getPrivilegeId();
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

    //place a new performanceReview in the db
    //build one-to-many link between user and performance review
    public Integer addPerformanceReview(PerformanceReview pr, User u) {

        //the new User's id
        Integer id = null;

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            //build relationship
            u.getPerformanceReviews().add(pr);
            pr.setUser(u);

            session.save(pr);
            tx.commit();

            id = pr.getPerformanceReviewId();
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

    //place a new savedSearch in the db
    //build one-to-many link between user and savedSearch
    public Integer addSavedSearch(User u, SavedSearch ss) {

        //the new User's id
        Integer id = null;

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            //build relationship
            u.getSavedSearches().add(ss);
            ss.setUser(u);

            session.save(ss);
            tx.commit();

            id = ss.getSavedSearchId();
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

    //place a new training event in the db
    //build one-to-many link between user and training
    public Integer addTraining(Training t, User u) {

        //the new User's id
        Integer id = null;

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            //build relationship
            u.getTraining().add(t);
            t.setUser(u);

            session.save(t);
            tx.commit();

            id = t.getTrainingId();
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

     //place a new training event in the db
    //build one-to-many link between user and training
    public void updateTraining(Training t) {

        //the new User's id
        Integer id = null;

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            //build relationship


            session.saveOrUpdate(t);
            tx.commit();

            id = t.getTrainingId();
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

    /**
     * getInstance() returns the instance of the <code>ItemService</code> singleton.
     *
     * @return <code>ItemService</code> singleton.
     */
    public static synchronized UserService getInstance() {
        /*
         * Creates the Singleton instance, if needed.
         *
         */
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    //has the user provided a proper username and password
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

            results = session.find("from User as user where user.username = ? and user.password = ? and currentEmployee='true'",
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

    //has the user provided a proper username and password
    public boolean checkClientLogin(String username, String password) {

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

            results = session.find("from User as user where user.username = ? and user.password = ? and usertype='client'",
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

    //get a user by integer id (in db)
    public User getSingleUser(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from User as user where user.userId = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});



        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
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
            return (User) results.get(0);
        }

    }
    
     //get a user by integer id (in db)
    public User getSingleUserCurrentEmployee(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from User as user where user.userId = ? and currentEmployee='true'",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});



        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
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
            return (User) results.get(0);
        }

    }


    //get a performanceReview log by integer id
    public PerformanceReview getSinglePerformanceReview(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from PerformanceReview as performanceReview where performanceReview.performanceReviewId = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});



        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
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
            return (PerformanceReview) results.get(0);
        }

    }

    //get a away event by integer id (in db)
    public Away getSingleAway(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Away as away where away.awayId = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});



        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
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
            return (Away) results.get(0);
        }

    }

    //get a projectCart by integer id (in db)
    public ProjectCart getSingleProjectCart(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from ProjectCart as projectCart where projectCart.projectCartId = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});



        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
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
            return (ProjectCart) results.get(0);
        }

    }

    //get a single savedSearch by integer id (in db)
    public SavedSearch getSingleSavedSearch(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from SavedSearch as savedSearch where savedSearch.savedSearchId = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});


            //System.out.println("resultssssssssss" + results.toString());
        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
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
            return (SavedSearch) results.get(0);
        }

    }

    //delete one savedSearch
    public void deleteSavedSearch(SavedSearch ss) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(ss);
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

     //delete one savedSearch
    public void deletePosition(Position1 p) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(p);
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

    //delete one away event
    public void deleteAway(Away a) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(a);
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


    //delete one away event
    public void deleteDepartment(Department a) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(a);
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


    //delete one user privilege
    public void deletePrivilege(Privilege p) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(p);
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

    //delete one performanceReview log
    public void deletePerformanceReview(PerformanceReview pr) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(pr);
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

    //delete one projectCart
    public void deleteProjectCart(ProjectCart pc) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(pc);
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

    //get a user by username
    //should be unique
    public User getSingleUser(String username) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from User as user where user.username = ?",
                    new Object[]{username},
                    new Type[]{Hibernate.STRING});



        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
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
            return (User) results.get(0);
        }

    }

    //get a user by real name
    //should be unique
    public User getSingleUserRealName(String firstName, String lastName) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from User as user where CONCAT_WS(' ',user.firstName, user.lastName) = ? ",
                    new Object[]{firstName+" "+lastName},
                    new Type[]{Hibernate.STRING});



        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
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
            return (User) results.get(0);
        }

    }
     //get a user by real name
    //should be unique
    public User getSingleUserRealName(String name) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from User as user where CONCAT_WS(' ',user.firstName, user.lastName) = ? ",
                    new Object[]{name},
                    new Type[]{Hibernate.STRING});



        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
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
            return (User) results.get(0);
        }

    }
    
    
    public User getSingleCurrentUserRealName(String firstName, String lastName) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from User as user where CONCAT_WS(' ',user.firstName, user.lastName) = ? and currentEmployee='true'",
                    new Object[]{firstName+" "+lastName},
                    new Type[]{Hibernate.STRING});



        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
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
            return (User) results.get(0);
        }

    }

      //should be unique
    public User getSingleUserLastName(String lastName) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from User as user where user.lastName = ?",
                    new Object[]{lastName},
                    new Type[]{Hibernate.STRING});



        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
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
            return (User) results.get(0);
        }

    }
    

    //get a position by its name
    //should be unique
    //public Client getSingleposition(String client){}
    public Position1 getSinglePosition(String position) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Position1 as position where position.position = ?",
                    new Object[]{position},
                    new Type[]{Hibernate.STRING});



        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
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
            return (Position1) results.get(0);
        }

    }

        public Position1 getSinglePositionById(Integer posId) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Position1 as position where position.position1Id = ?",
                    new Object[]{posId},
                    new Type[]{Hibernate.INTEGER});



        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
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
            return (Position1) results.get(0);
        }

    }

    //get a department by its name
    //should be unique
    public Department getSingleDepartment(String department) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Department as department where department.department = ?",
                    new Object[]{department},
                    new Type[]{Hibernate.STRING});



        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
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
            return (Department) results.get(0);
        }

    }

     //get a department by its name
    //should be unique
    public Department getSingleDepartment(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Department as department where department.departmentId = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});



        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
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
            return (Department) results.get(0);
        }

    }

    //get a location by its name
    //should be unique
    public Location getSingleLocation(String location) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Location as location where location.location = ?",
                    new Object[]{location},
                    new Type[]{Hibernate.STRING});



        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
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
            return (Location) results.get(0);
        }

    }

    //get a location by its name
    //should be unique
    public Location getSingleLocationByLocId(Integer locId) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Location as location where location.locationId = ?",
                    new Object[]{locId},
                    new Type[]{Hibernate.INTEGER});



        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
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
            return (Location) results.get(0);
        }

    }

    //get all users
    public List getUserList() {
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
            query = session.createQuery("select user from app.user.User user order by user.lastName, user.firstName");
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

    //get all users that are currently employeed
    public List getUserListCurrent() {
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
            query = session.createQuery("select user from app.user.User user where user.currentEmployee = 'true' order by user.lastName, user.firstName");
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

    //get all users that are currently employeed
    public List getUserListCurrentOrderByOffice() {
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
            query = session.createQuery("select user from app.user.User user where user.currentEmployee = 'true' order by user.Location.locationId, user.lastName, user.firstName");
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
    
//      //get all users that are currently employeed
//    public List getUserListCurrentOrderByOffice() {
//        /*
//         * Use the ConnectionFactory to retrieve an open
//         * Hibernate Session.
//         *
//         */
//        Session session = ConnectionFactory.getInstance().getSession();
//        Query query;
//
//        try {
//            /*
//             * Build HQL (Hibernate Query Language) query to retrieve a list
//             * of all the items currently stored by Hibernate.
//             */
//            query = session.createQuery("select user from app.user.User user where user.currentEmployee = 'true' order by user.Location.locationId, user.lastName, user.firstName");
//            return query.list();
//        } catch (HibernateException e) {
//            System.err.println("Hibernate Exception" + e.getMessage());
//            throw new RuntimeException(e);
//        } /*
//         * Regardless of whether the above processing resulted in an Exception
//         * or proceeded normally, we want to close the Hibernate session.  When
//         * closing the session, we must allow for the possibility of a Hibernate
//         * Exception.
//         *
//         */ finally {
//            if (session != null) {
//                try {
//                    session.close();
//                } catch (HibernateException e) {
//                    System.err.println("Hibernate Exception" + e.getMessage());
//                    throw new RuntimeException(e);
//                }
//
//            }
//        }
//    }
    

    //get all users that were employed
    public List<User> getUserListFormer() {
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
            query = session.createQuery("select user from app.user.User user where user.currentEmployee = 'false' and user.dropDown = 'false' order by user.lastName, user.firstName");
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
    //get all users that were employed

    public List getUserListShowToClient() {
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
            query = session.createQuery("select user from app.user.User user where user.currentEmployee = 'true' and user.clientShow = 'TRUE' order by user.lastName, user.firstName");
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
    
        public List getClientIDHavingPortal() {
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
          
            query = session.createQuery("select user.id_client from app.user.User user where user.userType = 'client'  group by user.id_client order by user.id_client");
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


    //get all users that are currently employeed or should be
    //in the Employee dropdown box, such as House
    public List getUserListCurrentDropDown() {
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
            query = session.createQuery("select user from app.user.User user where user.dropDown = 'true' order by user.lastName, user.firstName");
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

    //get all vacation days for this users employment year
    public List getUserVacationHistoryYear(Integer userId, Date hireDateStart) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */

        //build the current hireDateStart and hireDateEnd for the search range
        //current date
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.setTime(new Date());

        if (hireDateStart == null) {
            hireDateStart = new Date();
        }
        GregorianCalendar startHireDate = new GregorianCalendar();
        startHireDate.setTime(hireDateStart);
        //this is the start of current employment year
        startHireDate.set(Calendar.YEAR, currentDate.get(Calendar.YEAR));

        GregorianCalendar hireDateEnd = new GregorianCalendar();
        hireDateEnd.setTime(startHireDate.getTime());
        //advance its year by one
        //this is the end of the current employment year
        hireDateEnd.add(Calendar.YEAR, 1);

        //adjust to fit in current employment year
        if (startHireDate.after(currentDate)) {
            hireDateEnd.add(Calendar.YEAR, -1);
            startHireDate.add(Calendar.YEAR, -1);
        }

        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;

        try {
            //retreive away events from database

            //this is the main class
            Criteria criteria = session.createCriteria(Away.class);

            //sub criteria; the user
            Criteria subCriteria = criteria.createCriteria("User");
            subCriteria.add(Expression.eq("userId", userId));

            criteria.add(Expression.ge("startDate", startHireDate.getTime()));
            criteria.add(Expression.le("startDate", hireDateEnd.getTime()));
            criteria.add(Expression.eq("type", "Vacation"));

            criteria.addOrder(Order.asc("startDate"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            results = criteria.list();

            return results;
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

    //get all sick days for this users employment year
    public List getUserSickHistoryYear(Integer userId, Date hireDateStart) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */

        //build the current hireDateStart and hireDateEnd for the search range
        //current date
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.setTime(new Date());

        GregorianCalendar startHireDate = new GregorianCalendar();
        startHireDate.setTime(hireDateStart);
        //this is the start of current employment year
        startHireDate.set(Calendar.YEAR, currentDate.get(Calendar.YEAR));

        GregorianCalendar hireDateEnd = new GregorianCalendar();
        hireDateEnd.setTime(startHireDate.getTime());
        //advance its year by one
        //this is the end of the current employment year
        hireDateEnd.add(Calendar.YEAR, 1);

        //adjust to fit in current employment year
        if (startHireDate.after(currentDate)) {
            hireDateEnd.add(Calendar.YEAR, -1);
            startHireDate.add(Calendar.YEAR, -1);
        }

        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;

        try {
            //retreive away events from database

            //this is the main class
            Criteria criteria = session.createCriteria(Away.class);

            //sub criteria; the user
            Criteria subCriteria = criteria.createCriteria("User");
            subCriteria.add(Expression.eq("userId", userId));

            criteria.add(Expression.ge("startDate", startHireDate.getTime()));
            criteria.add(Expression.le("startDate", hireDateEnd.getTime()));
            criteria.add(Expression.eq("type", "Sick"));

            criteria.addOrder(Order.asc("startDate"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            results = criteria.list();

            return results;
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

    //get all travel days for this users employment year
    public List getUserTravelHistoryYear(Integer userId, Date hireDateStart) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */

        //build the current hireDateStart and hireDateEnd for the search range
        //current date
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.setTime(new Date());

        GregorianCalendar startHireDate = new GregorianCalendar();
        startHireDate.setTime(hireDateStart);
        //this is the start of current employment year
        startHireDate.set(Calendar.YEAR, currentDate.get(Calendar.YEAR));

        GregorianCalendar hireDateEnd = new GregorianCalendar();
        hireDateEnd.setTime(startHireDate.getTime());
        //advance its year by one
        //this is the end of the current employment year
        hireDateEnd.add(Calendar.YEAR, 1);

        //adjust to fit in current employment year
        if (startHireDate.after(currentDate)) {
            hireDateEnd.add(Calendar.YEAR, -1);
            startHireDate.add(Calendar.YEAR, -1);
        }

        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;

        try {
            //retreive away events from database

            //this is the main class
            Criteria criteria = session.createCriteria(Away.class);

            //sub criteria; the user
            Criteria subCriteria = criteria.createCriteria("User");
            subCriteria.add(Expression.eq("userId", userId));

            criteria.add(Expression.ge("startDate", startHireDate.getTime()));
            criteria.add(Expression.le("startDate", hireDateEnd.getTime()));
            criteria.add(Expression.eq("type", "Travel"));

            criteria.addOrder(Order.asc("startDate"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            results = criteria.list();

            return results;
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

    //get all unpaid days for this users employment year
    public List getUserUnpaidHistoryYear(Integer userId, Date hireDateStart) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */

        //build the current hireDateStart and hireDateEnd for the search range
        //current date
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.setTime(new Date());

        GregorianCalendar startHireDate = new GregorianCalendar();
        startHireDate.setTime(hireDateStart);
        //this is the start of current employment year
        startHireDate.set(Calendar.YEAR, currentDate.get(Calendar.YEAR));

        GregorianCalendar hireDateEnd = new GregorianCalendar();
        hireDateEnd.setTime(startHireDate.getTime());
        //advance its year by one
        //this is the end of the current employment year
        hireDateEnd.add(Calendar.YEAR, 1);

        //adjust to fit in current employment year
        if (startHireDate.after(currentDate)) {
            hireDateEnd.add(Calendar.YEAR, -1);
            startHireDate.add(Calendar.YEAR, -1);
        }

        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;

        try {
            //retreive away events from database

            //this is the main class
            Criteria criteria = session.createCriteria(Away.class);

            //sub criteria; the user
            Criteria subCriteria = criteria.createCriteria("User");
            subCriteria.add(Expression.eq("userId", userId));

            criteria.add(Expression.ge("startDate", startHireDate.getTime()));
            criteria.add(Expression.le("startDate", hireDateEnd.getTime()));
            criteria.add(Expression.eq("type", "Unpaid"));

            criteria.addOrder(Order.asc("startDate"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            results = criteria.list();

            return results;
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

    //get all away days (vacation, sick, travel) for this users employment year
    public List getUserAllAwayHistoryYear(Integer userId, Date hireDateStart) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */

        //build the current hireDateStart and hireDateEnd for the search range
        //current date
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.setTime(new Date());

        GregorianCalendar startHireDate = new GregorianCalendar();
        startHireDate.setTime(hireDateStart);
        //this is the start of current employment year
        startHireDate.set(Calendar.YEAR, currentDate.get(Calendar.YEAR));

        GregorianCalendar hireDateEnd = new GregorianCalendar();
        hireDateEnd.setTime(startHireDate.getTime());
        //advance its year by one
        //this is the end of the current employment year
        hireDateEnd.add(Calendar.YEAR, 1);

        //adjust to fit in current employment year
        if (startHireDate.after(currentDate)) {
            hireDateEnd.add(Calendar.YEAR, -1);
            startHireDate.add(Calendar.YEAR, -1);
        }

        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;

        try {
            //retreive away events from database

            //this is the main class
            Criteria criteria = session.createCriteria(Away.class);

            //sub criteria; the user
            Criteria subCriteria = criteria.createCriteria("User");
            subCriteria.add(Expression.eq("userId", userId));

            criteria.add(Expression.ge("startDate", startHireDate.getTime()));
            criteria.add(Expression.le("startDate", hireDateEnd.getTime()));

            criteria.addOrder(Order.asc("startDate"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            results = criteria.list();

            return results;
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


    //get a location by its name
    //should be unique
    public Training getSingleTraining(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Training as training where training.trainingId = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});



        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
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
            return (Training) results.get(0);
        }

    }

    //get a list of training for this user
    public List getTraining(Integer userId) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */

        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;

        try {
            //retreive away events from database

            //this is the main class
            Criteria criteria = session.createCriteria(Training.class);

            //sub criteria; the user
            Criteria subCriteria = criteria.createCriteria("User");
            subCriteria.add(Expression.eq("userId", userId));

            criteria.addOrder(Order.asc("dateCompleted"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            results = criteria.list();

            return results;
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


     //get a list of training for this user
    public List getTraining(Integer userId, Integer docId, Integer departmentId) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */

        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;

        try {
            //retreive away events from database

            //this is the main class
            Criteria criteria = session.createCriteria(Training.class);

            //sub criteria; the user
            Criteria subCriteria = criteria.createCriteria("User");
            subCriteria.add(Expression.eq("userId", userId));
            criteria.add(Expression.eq("docId", docId));
            criteria.add(Expression.eq("departmentId", departmentId));

            criteria.addOrder(Order.asc("dateCompleted"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            results = criteria.list();

            return results;
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
    
         //get a list of training for this user
    public List getTrainingByDocId(Integer docId) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */

        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;

        try {
            //retreive away events from database

            //this is the main class
            Criteria criteria = session.createCriteria(Training.class);

            //sub criteria; the user
            Criteria subCriteria = criteria.createCriteria("User");
//            subCriteria.add(Expression.eq("userId", userId));
            criteria.add(Expression.eq("docId", docId));
//            criteria.add(Expression.eq("departmentId", departmentId));

            criteria.addOrder(Order.asc("dateCompleted"));

            //remove duplicates
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            results = criteria.list();

            return results;
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

    //get all Positions
    public List getPositionList() {
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
            query = session.createQuery("select position1 from app.user.Position1 position1 order by position1.position");
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

    //get all Departments
    public List getDepartmentList() {
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
            query = session.createQuery("select department from app.user.Department department order by department.department");
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

    //get all Locations
    public List getLocationList() {
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
            query = session.createQuery("select location from app.user.Location location order by location.location");
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

    //update an existing user in database
    public User updateUser(User u) {
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
        return u;
    }

    //update an existing location in database
    public Location updateLocation(Location l) {
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
        return l;
    }

    //update an existing performanceReview in database
    public PerformanceReview updatePerformanceReview(PerformanceReview pr) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(pr);

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
        return pr;
    }

    //update an existing away event in database
    public Away updateAway(Away a) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(a);

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
        return a;
    }

    //update an existing projectCart in database
    public ProjectCart updateProjectCart(ProjectCart pc) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(pc);

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
        return pc;
    }

    //add this projectCart to the db, building one-to-many relationship between user and projectCart
    //return new SourceDoc id
    public Integer addProjectCartWithUser(User u, ProjectCart pc) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;
        Integer id = null;

        try {

            tx = session.beginTransaction();

            //build relationship
            u.getProjectCarts().add(pc);
            pc.setUser(u);

            session.save(pc);
            tx.commit();
            id = pc.getProjectCartId();
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

    //provide a list of Away types
    public String[] getAwayTypes() {
        String awayTypes[] = {"Holiday", "Sick", "Travel", "Vacation"};

        return awayTypes;
    }

    public String displayAnniversaryAnnouncement(String daysToAnniversary) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        String sql = "select user from app.user.User user where currentEmployee='true' and curdate()-user.hireDate>300 and (dayofyear(user.hireDate)>=dayofyear(curdate()) and "
                + "dayofyear(user.hireDate)<=dayofyear(curdate())+" + daysToAnniversary + ") or "
                + "(dayofyear(user.hireDate)+365>=dayofyear(curdate()) and "
                + "dayofyear(user.hireDate)+365<=dayofyear(curdate())+" + daysToAnniversary + ")";
        String html = "";
        try {

            Query query = session.createQuery(sql);
            results = query.list();
            for (int i = 0; i < results.size(); i++) {
                User u = (User) results.get(i);
                GregorianCalendar currentDate = new GregorianCalendar();
                currentDate.setTime(new Date());
                GregorianCalendar hireDate = new GregorianCalendar();
                hireDate.setTime(u.getHireDate());
                int yearsEmployed = currentDate.get(Calendar.YEAR) - hireDate.get(Calendar.YEAR);
                String yearsString = " years ";
                if (yearsEmployed == 1) {
                    yearsString = " year ";
                }
                html += "&nbsp;Congratulations, " + u.getFirstName() + ", on your " + yearsEmployed + yearsString + "of employment at Excel Translations!<br>";
            }

        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
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
        return html;

    }

    public String displayBirthdayAnnouncement() {


        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        String sql = "select user from app.user.User user where currentEmployee='true' and dayofyear(curdate())=dayofyear(user.birthDay) ";
        String html = "";
        try {

            Query query = session.createQuery(sql);
            results = query.list();
            for (int i = 0; i < results.size(); i++) {
                User u = (User) results.get(i);
                GregorianCalendar currentDate = new GregorianCalendar();
                currentDate.setTime(new Date());
                GregorianCalendar birthDay = new GregorianCalendar();
                birthDay.setTime(u.getBirthDay());
                html += "&nbsp; Happy Birthday, " + u.getFirstName() + "!<br>";
            }

        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
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
        return html;

    }

    //update an existing user in database
    public long updateUserSessionInfo(String username) {
        User u = UserService.getInstance().getSingleUser(username);



        long lastUpdateUserSessionTime = System.currentTimeMillis();

        u.setLast_activity(new java.sql.Timestamp(lastUpdateUserSessionTime));

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

    //has the cc provided a proper username and password
    public boolean checkTrackerLoginClientContact(String username, String password) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;


        try {

            results = session.find("from ClientContact as cc where cc.Email_address = ? and cc.tracker_password = ? and cc.tracker_password!=''",
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

    //has the cc provided a proper username and password
    public boolean checkTrackerLoginClient(String username, String password) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;


        try {

            results = session.find("from Client as cc where cc.Company_code = ? and cc.tracker_password = ? and cc.tracker_password!=''",
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

    //has the cc provided a proper username and password
    public boolean checkTrackerLogin(String username, String password) {




        try {

            if (!UserService.getInstance().checkTrackerLoginClient(username, password) && !UserService.getInstance().checkTrackerLoginClientContact(username, password)) {
                return false;
            }

            return true;


        } catch (Exception e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        }



    }

    //update an existing location in database
    public UserAbscence updateUserAbscence(UserAbscence l) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.save(l);

            tx.commit();
        } catch (HibernateException e) {
            try {
                tx.rollback(); //error
            } catch (HibernateException he) {
                System.err.println("Hibernate Exception: " + e.getMessage());
                throw new RuntimeException(e);
            }
            System.err.println("Hibernate Exception: " + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
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

    //get all users that are currently employeed
    public List getUserAbscences(Integer userId, int currentYear) {
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

            query = session.createQuery("select userabscence from app.user.UserAbscence userabscence where (userabscence.ID_User=" + userId.toString()
                    + ") and userabscence.abscence_date>= str_to_date('1/1/" + currentYear
                    + "','%d/%m/%Y') and userabscence.abscence_date< str_to_date('1/1/" + (currentYear + 2) + "','%d/%m/%Y') order by userabscence.abscence_date");
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

    //get all users that are currently employeed
    public List getLocationAbscences(Integer userId, int currentYear) {
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

            query = session.createQuery("select userabscence from app.user.UserAbscence userabscence where (userabscence.ID_Location=" + userId.toString()
                    + ") and userabscence.abscence_date>= str_to_date('1/1/" + currentYear
                    + "','%d/%m/%Y') and userabscence.abscence_date< str_to_date('1/1/" + (currentYear + 2) + "','%d/%m/%Y') order by userabscence.abscence_date");
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

    //get all users that are currently employeed
    public List getLocationAbscences(int currentYear) {
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

            query = session.createQuery("select userabscence from app.user.UserAbscence userabscence where   userabscence.abscence_date>= str_to_date('1/1/" + currentYear
                    + "','%d/%m/%Y') and userabscence.abscence_date< str_to_date('1/1/" + (currentYear + 2) + "','%d/%m/%Y') order by userabscence.abscence_date");
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

    //delete one savedSearch
    public void deleteAbscence(UserAbscence ss) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(ss);
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

    //get all users that are currently employeed
    public List getAllUserAbscencesForAYear(int currentYear) {
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

            query = session.createQuery("select userabscence from app.user.UserAbscence userabscence where "
                    + "  userabscence.abscence_date>= str_to_date('1/1/" + currentYear
                    + "','%d/%m/%Y') and userabscence.abscence_date< str_to_date('1/1/" + (currentYear + 2) + "','%d/%m/%Y') order by userabscence.abscence_date");
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

    //get all users that are currently employeed
    public List getAllUserAbscences() {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */

     //   DateFormat sdf = new SimpleDateFormat("MM/dd/yy");

        String today="";
        int year=new Date().getYear()+1900;
        int month=new Date().getMonth()+1;
        
            today = new Date().getDate()+"/"+ month+"/"+  year;
       

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */

            query = session.createQuery("select userabscence from app.user.UserAbscence userabscence where "
                    + "  userabscence.abscence_date= str_to_date('" + today
                    + "','%d/%m/%Y')");
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
      //get all users that are currently employeed
    public List getAllUserAbscencesMonth() {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */

     //   DateFormat sdf = new SimpleDateFormat("MM/dd/yy");

        String today="",monthBack="";
        int year=new Date().getYear()+1900;
        int month=new Date().getMonth()+1;
        int monthB = 1;int yearB=year;
        if(month==12){ monthB=1;yearB=yearB+1; }
        else { monthB=month+1;}
        
        
            today = new Date().getDate()+"/"+ month+"/"+  year;
            monthBack = new Date().getDate()+"/"+ monthB+"/"+  yearB;
       

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */

            query = session.createQuery("select userabscence from app.user.UserAbscence userabscence where "
                    + "  userabscence.abscence_date between str_to_date('" + today 
                    + "','%d/%m/%Y') and str_to_date('" + monthBack
                    + "','%d/%m/%Y') order by userabscence.abscence_date asc");
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
    
    public static String getTeamTaskDue(Integer pmid) {

        //get resource to edit
        Session session = ConnectionFactory.getInstance().getSession();
        String result = "";

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        Date date = new Date();
        String ndt = dateFormat.format(date);
        try {
            PreparedStatement st = session.connection().prepareStatement("SELECT p.number,l.taskname,s.language as sLang,t.language as tLang,l.dueDatedate, r.firstName,r.lastName,r.companyname,c.company_code FROM project p INNER JOIN sourcedoc s ON p.id_project=s.id_project "
                    + "INNER JOIN targetdoc t ON s.id_sourcedoc=t.id_sourcedoc "
                    + "INNER JOIN lintask l ON l.ID_TargetDoc=t.ID_TargetDoc "
                    + "INNER JOIN resource r ON l.personname=r.id_resource "
                    + "INNER JOIN client_information c ON p.id_client=c.id_client "
                    + "WHERE DATEDIFF ('" + ndt + "', l.dueDatedate)<0 AND l.receiveddatedate IS NULL AND p.pm_id=" + pmid + " AND p.status='active' "
                    + "UNION "
                    + "SELECT p.number,l.taskname,s.language as sLang,t.language as tLang,l.dueDatedate, r.firstName,r.lastName,r.companyname,c.company_code FROM project p INNER JOIN sourcedoc s ON p.id_project=s.id_project "
                    + "INNER JOIN targetdoc t ON s.id_sourcedoc=t.id_sourcedoc "
                    + "INNER JOIN dtptask l ON l.ID_TargetDoc=t.ID_TargetDoc "
                    + "INNER JOIN resource r ON l.personname=r.id_resource "
                    + "INNER JOIN client_information c ON p.id_client=c.id_client "
                    + "WHERE DATEDIFF ('" + ndt + "', l.dueDatedate)<0 AND l.receiveddatedate IS NULL AND p.pm_id=" + pmid + " AND p.status='active' "
                    + "UNION "
                    + "SELECT p.number,l.taskname,s.language as sLang,t.language as tLang,l.dueDatedate, r.firstName,r.lastName,r.companyname,c.company_code FROM project p INNER JOIN sourcedoc s ON p.id_project=s.id_project "
                    + "INNER JOIN targetdoc t ON s.id_sourcedoc=t.id_sourcedoc "
                    + "INNER JOIN engtask l ON l.ID_TargetDoc=t.ID_TargetDoc "
                    + "INNER JOIN resource r ON l.personname=r.id_resource "
                    + "INNER JOIN client_information c ON p.id_client=c.id_client "
                    + "WHERE DATEDIFF ('" + ndt + "', l.dueDatedate)<0 AND l.receiveddatedate IS NULL AND p.pm_id=" + pmid + " AND p.status='active' "
                    + "UNION "
                    + "SELECT p.number,l.taskname,s.language as sLang,t.language as tLang,l.dueDatedate, r.firstName,r.lastName,r.companyname,c.company_code FROM project p INNER JOIN sourcedoc s ON p.id_project=s.id_project "
                    + "INNER JOIN targetdoc t ON s.id_sourcedoc=t.id_sourcedoc "
                    + "INNER JOIN othtask l ON l.ID_TargetDoc=t.ID_TargetDoc "
                    + "INNER JOIN resource r ON l.personname=r.id_resource "
                    + "INNER JOIN client_information c ON p.id_client=c.id_client "
                    + "WHERE DATEDIFF ('" + ndt + "', l.dueDatedate)<0 AND l.receiveddatedate IS NULL AND p.pm_id=" + pmid + " AND p.status='active'");

            ResultSet rs = st.executeQuery();
           
           Integer count=1;
            while (rs.next()) {
            if(count==1) result = "<fieldset><legend>Due Tasks</legend><div align='center'><table class='tableHighlight' width='90%' align='center' ><tr><td>&nbsp;</td></tr>";
//                //System.out.println("AAAAAANNNNNAAAAAA" + rs.getString("sLang"));
//                //System.out.println("ddddddddddddddddd" + rs.getString("taskname"));
//                //System.out.println("qqqqqqqqqqqqqqqqq" + rs.getString("tLang"));
                String resource = "";
                if (rs.getString("firstName").equals("")) {
                    resource = rs.getString("companyname");
                } else {
                    resource = rs.getString("firstName") + " " + rs.getString("lastName");
                }
                result += "<tr>";
                result += "<td class=tableHeadingData><center>"+count++ +": " + rs.getString("taskname") + " ( " + rs.getString("sLang") + " - " + rs.getString("tLang") + ") of Project Number -<b><u>" + rs.getInt("number") +rs.getString("company_code")+ "</u></b>, <br> is due for <b>" + resource + "</b></center></td>";

                result += "</tr><tr><td>&nbsp;</td></tr>";

            }
            if (count > 1) {
                result += "</table></div></fieldset>";
            }
                st.close();
        } catch (Exception e) {
            //System.out.println("errrrrrrrrrrrrrror" + e.getMessage());
                } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e) {
                    //System.out.println("Hibernate Exception:" + e);
                    throw new RuntimeException(e);
                }

            }
        }
        return result;

    }

    public static String getTeamTaskDueInvoice(Integer pmid) {

        //get resource to edit
        Session session = ConnectionFactory.getInstance().getSession();
        String result = "";
        String invoiceAmt = StandardCode.getInstance().getPropertyValue("resource.invoice.alert.limit");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        Date date = new Date();
        String ndt = dateFormat.format(date);
        try {
            PreparedStatement st = session.connection().prepareStatement("SELECT p.number,l.taskname,l.internalDollarTotal,l.internalCurrency,s.language as sLang,t.language as tLang,l.dueDatedate, r.firstName,r.lastName,r.companyname,c.company_code FROM project p INNER JOIN sourcedoc s ON p.id_project=s.id_project "
                    + "INNER JOIN targetdoc t ON s.id_sourcedoc=t.id_sourcedoc "
                    + "INNER JOIN lintask l ON l.ID_TargetDoc=t.ID_TargetDoc "
                    + "INNER JOIN resource r ON l.personname=r.id_resource "
                    + "INNER JOIN client_information c ON p.id_client=c.id_client "
                    + "WHERE l.receiveddatedate IS NOT NULL AND REPLACE(l.internalDollarTotal,',','')>"+invoiceAmt+" AND l.invoicedatedate IS NULL AND p.pm_id=" + pmid + " "
                    + "UNION "
                    + "SELECT p.number,l.taskname,l.internalDollarTotal,l.internalCurrency,s.language as sLang,t.language as tLang,l.dueDatedate, r.firstName,r.lastName,r.companyname,c.company_code FROM project p INNER JOIN sourcedoc s ON p.id_project=s.id_project "
                    + "INNER JOIN targetdoc t ON s.id_sourcedoc=t.id_sourcedoc "
                    + "INNER JOIN dtptask l ON l.ID_TargetDoc=t.ID_TargetDoc "
                    + "INNER JOIN resource r ON l.personname=r.id_resource "
                    + "INNER JOIN client_information c ON p.id_client=c.id_client "
                    + "WHERE l.receiveddatedate IS NOT NULL AND REPLACE(l.internalDollarTotal,',','')>"+invoiceAmt+" AND l.invoicedatedate IS NULL AND p.pm_id=" + pmid + "  "
                    + "UNION "
                    + "SELECT p.number,l.taskname,l.internalDollarTotal,l.internalCurrency,s.language as sLang,t.language as tLang,l.dueDatedate, r.firstName,r.lastName,r.companyname,c.company_code FROM project p INNER JOIN sourcedoc s ON p.id_project=s.id_project "
                    + "INNER JOIN targetdoc t ON s.id_sourcedoc=t.id_sourcedoc "
                    + "INNER JOIN engtask l ON l.ID_TargetDoc=t.ID_TargetDoc "
                    + "INNER JOIN resource r ON l.personname=r.id_resource "
                    + "INNER JOIN client_information c ON p.id_client=c.id_client "
                    + "WHERE l.receiveddatedate IS NOT NULL AND REPLACE(l.internalDollarTotal,',','')>"+invoiceAmt+" AND l.invoicedatedate IS NULL AND p.pm_id=" + pmid + "  "
                    + "UNION "
                    + "SELECT p.number,l.taskname,l.internalDollarTotal,l.internalCurrency,s.language as sLang,t.language as tLang,l.dueDatedate, r.firstName,r.lastName,r.companyname,c.company_code FROM project p INNER JOIN sourcedoc s ON p.id_project=s.id_project "
                    + "INNER JOIN targetdoc t ON s.id_sourcedoc=t.id_sourcedoc "
                    + "INNER JOIN othtask l ON l.ID_TargetDoc=t.ID_TargetDoc "
                    + "INNER JOIN resource r ON l.personname=r.id_resource "
                    + "INNER JOIN client_information c ON p.id_client=c.id_client "
                    + "WHERE l.receiveddatedate IS NOT NULL  AND REPLACE(l.internalDollarTotal,',','')>"+invoiceAmt+" AND l.invoicedatedate IS NULL AND p.pm_id=" + pmid + " ");

            ResultSet rs = st.executeQuery();
           
           Integer count=1;
            while (rs.next()) {
            if(count==1) result = "<fieldset><legend>Due Invoice</legend>"
                    + "<div><font size=\"1\" color='red'>Note: (only invoices over $"+invoiceAmt+" are displayed here)</font></div>"
                    + "<div class =\"row\">";
//                //System.out.println("AAAAAANNNNNAAAAAA" + rs.getString("sLang"));
//                //System.out.println("ddddddddddddddddd" + rs.getString("taskname"));
//                //System.out.println("qqqqqqqqqqqqqqqqq" + rs.getString("tLang"));
                String resource = "";
                if (rs.getString("firstName").equals("")) {
                    resource = rs.getString("companyname");
                } else {
                    resource = rs.getString("firstName") + " " + rs.getString("lastName");
                }
                result += "<div class=\"six column\" style=\"padding-bottom: 10px\">";
                result+="<div class=\"w3-card-8 w3-light-grey\" style=\"padding-bottom: 5px\">";
                result += "<div class=\"w3-container\">"
                        +"</br><span style='font-size:15px'><b>" + resource + "</b></span>"
                        + "<span class='w3-right' style='color : blue'>"+rs.getString("internalDollarTotal")+ " " +rs.getString("internalCurrency")+"</span></br>"
                         +"</br></br><span style='font-size:12px'><b>" + rs.getInt("number") +rs.getString("company_code") + "</b></span>"
                        + "</br>"+ rs.getString("taskname") + " "
                        + "( " + rs.getString("sLang") + " - " + rs.getString("tLang") + ")  <br><br>" 
                        + "</div>";
               
              

                result += "</b></div></div>";
                count++;

            }
            if (count > 1) {
                result += "</fieldset>";
            }
                st.close();
        } catch (Exception e) {
            //System.out.println("errrrrrrrrrrrrrror" + e.getMessage());
                } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e) {
                    //System.out.println("Hibernate Exception:" + e);
                    throw new RuntimeException(e);
                }

            }
        }
        return result;

    }
    
    public String getSignImagePath(String user) {
        String[] userName=user.split(" ", 2);
        User u=getSingleUserRealName(userName[0], userName[1]);
        String signPath="<img src='/logo/images/signApproved.jpg' width='122' height='44' border='0' title='"+user+"' alt='"+user+"'/>";
        if(u.getSignature()!=null){
            signPath="<img src='/logo/images/"+u.getSignature()+"' width='122' height='44' border='0' title='"+user+"' alt='"+user+"'/>";
        }
        return signPath;

    }

       //get a user by integer id (in db)
        public DepartmentUser getSingleDepartmentUser(Integer userId,Integer departmentId)
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

                        results = session.find("from DepartmentUser as departmentUser where departmentUser.userId = ? and departmentUser.departmentId = ? ",
                                               new Object[] {userId,departmentId},
                                               new Type[] {Hibernate.INTEGER,Hibernate.INTEGER} );



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
                    return (DepartmentUser) results.get(0);

	}

         //place a new user in the db
    public void addDepartmentUser(DepartmentUser u) {

        //the new User's id
        Integer id = null;

        Session session = ConnectionFactory.getInstance().getSession();

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
    }


//delete one away event
    public void deleteDepartmentUser(DepartmentUser a) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(a);
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
 //get all users that are currently employeed
    public List getDepartmentUserByDEpartment(int departmentId) {
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

            query = session.createQuery("select DepartmentUser from app.user.DepartmentUser DepartmentUser where "
                    + "  DepartmentUser.departmentId= " + departmentId);
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

       public List getTrainingInitialAdmin(int userId) {
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

            query = session.createQuery("select TrainingInitialAdmin from app.user.TrainingInitialAdmin TrainingInitialAdmin where "
                    + "  TrainingInitialAdmin.ID_User= " + userId);
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

public static boolean unlinkTraining(Integer userId) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from training where ID_User="+userId);

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

public static boolean unlinkInitialTraining(Integer userId) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from traininginitial where ID_User="+userId);

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

public static boolean unlinkInitialTrainingAdmin() {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from traininginitialadmin");

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

    //delete one away event
    public void deleteTraining(Training a) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(a);
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
    
    //get a position by its name
    //should be unique
    //public Client getSingleAdminMiscById(String client){}
    public AdminMisc getSingleAdminMiscById(Integer id, String type) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from AdminMisc as am where am.id = ? and type = ? ",
                    new Object[]{id,type},
                    new Type[]{Hibernate.INTEGER,Hibernate.STRING});



        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
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
            return (AdminMisc) results.get(0);
        }

    }

    public AdminMisc getSingleAdminMiscById(Integer id) {
    
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from AdminMisc as am where am.id = ? ",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});



        } /*
         * If the object is not found, i.e., no Item exists with the
         * requested id, we want the method to return null rather
         * than throwing an Exception.
         *
         */ catch (ObjectNotFoundException onfe) {
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
            return (AdminMisc) results.get(0);
        }
    
    }

    public void deleteAdminMisc(AdminMisc l) {
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

    public void addUpdateAdminMisc(AdminMisc adminMisc) {
       
        //the new User's id
        Integer id = null;

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(adminMisc);
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


}
