//AdminService.java contains the db code related to the administration
//pages. It handles requests such as showing/hiding the old rates and scores
//in the team, Rate/Score tab. It is also responsible for privilege administration
package app.admin;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import app.user.*;
import java.sql.PreparedStatement;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.json.JSONArray;
import org.json.JSONObject; 
import sun.misc.BASE64Encoder;

public class AdminService {

  private static AdminService instance = null;

  public AdminService() {
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

  //delete an entire collection for an object
  public void deleteCollection(Set collection) {

    Session session = ConnectionFactory.getInstance().getSession();

    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      for (Iterator iter = collection.iterator(); iter.hasNext();) {
        session.delete(iter.next());
      }

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

  //delete an object
  public void deleteObject(Object o) {

    Session session = ConnectionFactory.getInstance().getSession();

    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      session.delete(o);


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

  //delete an object
  public void delete(Object o) {

    Session session = ConnectionFactory.getInstance().getSession();

    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      session.delete(o);

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
  
  //delete an object
  public void addObject(Object o) {

    Session session = ConnectionFactory.getInstance().getSession();

    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      session.saveOrUpdate(o);

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
  public void deleteMailingListClient(MailingListClient o) {

    Session session = ConnectionFactory.getInstance().getSession();

    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      session.delete(o);

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
 
  public void deleteMailingList(MailingList o) {

    Session session = ConnectionFactory.getInstance().getSession();

    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      session.delete(o);

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
  public void deleteMailingListVendor(MailingListVendor o) {

    Session session = ConnectionFactory.getInstance().getSession();

    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      session.delete(o);

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

  /**
   * getInstance() returns the instance of the
   * <code>ItemService</code> singleton.
   *
   * @return <code>ItemService</code> singleton.
   */
  public static synchronized AdminService getInstance() {
    /*
     * Creates the Singleton instance, if needed.
     * 
     */
    if (instance == null) {
      instance = new AdminService();
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

      results = session.find("from User as user where user.username = ? and user.password = ?",
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

  //get an admin-property option value for a specific module and option
  public String getOptionValue(String module, String option, boolean returnTrueFalseValue) {

    /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     * 
     */
    Session session = ConnectionFactory.getInstance().getSession();
    List results = null;
    try {

      results = session.find("from AdminOption as ao where ao.description = ? and ao.module = ?",
              new Object[]{option, module},
              new Type[]{Hibernate.STRING, Hibernate.STRING});



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
      AdminOption ao = (AdminOption) results.get(0);
      if (returnTrueFalseValue) {
        return ao.getTrueFalseOption();
      } else {
        return ao.getStringOption();
      }
    }
  }

  //get a position by its name
  //should be unique
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

  //get all team module options
  public List getTeamOptions() {
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
      query = session.createQuery("select adminOption from app.admin.AdminOption adminOption where adminOption.module = 'team'");
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

  //get all project module options
  public List getProjectOptions() {
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
      query = session.createQuery("select adminOption from app.admin.AdminOption adminOption where adminOption.module = 'project'");
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

  //get all misc options
  public List getMiscOptions() {
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
      query = session.createQuery("select adminOption from app.admin.AdminOption adminOption where adminOption.module = 'misc'");
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

  //get all quote module options
  public List getQuoteOptions() {
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
      query = session.createQuery("select adminOption from app.admin.AdminOption adminOption where adminOption.module = 'quote'");
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

  //get all client module options
  public List getClientOptions() {
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
      query = session.createQuery("select adminOption from app.admin.AdminOption adminOption where adminOption.module = 'client'");
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

  //update an existing admin option in database
  public AdminOption updateAdminOption(AdminOption ao) {
    Session session = ConnectionFactory.getInstance().getSession();
    boolean tf = true;

    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      session.saveOrUpdate(ao);

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
    return ao;
  }

  //update an object
  public Object updateObject(Object o) {
    Session session = ConnectionFactory.getInstance().getSession();
    boolean tf = true;

    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      session.saveOrUpdate(o);

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
    return o;
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

  //get all Version
  public List getVersionList() {
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
      query = session.createQuery("select versionControl from app.admin.VersionControl versionControl order by versionControl.ReleaseDate DESC");
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

  //add or update version Control
  public VersionControl saveOrUpdateVC(VersionControl vc) {
    Session session = ConnectionFactory.getInstance().getSession();
    boolean tf = true;

    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      session.saveOrUpdate(vc);

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
    return null;
  }

  public VersionControl getSingleVersionControl(Integer id) {

    /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
     */
    Session session = ConnectionFactory.getInstance().getSession();
    List results = null;
    try {

      results = session.find("from VersionControl as vc where vc.vcId = ?",
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
      return (VersionControl) results.get(0);
    }

  }

  public VersionControl getLatestVersionControl() {

    /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
     */
    Session session = ConnectionFactory.getInstance().getSession();
    List results = null;
    try {

      results = session.find("from VersionControl as vc order by vc.ReleaseDate DESC");



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
      return (VersionControl) results.get(0);
    }

  }

  //delete an entire collection for an object
  public void deleteInspection(String lang, Integer IdProject) {

    Session session = ConnectionFactory.getInstance().getSession();

    Transaction tx = null;

    try {

      tx = session.beginTransaction();
      PreparedStatement st = session.connection().prepareStatement("delete from inspection where ID_Project=? and ((`language` LIKE '%to " + lang + "')OR(`language`=?))");
      st.setInt(1, IdProject);
      st.setString(2, lang);



      st.executeUpdate();

      st.close();
    } catch (SQLException ex) {
      Logger.getLogger(AdminService.class.getName()).log(Level.SEVERE, null, ex);
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

  //update an existing admin option in database
  public Ticker updateTicker(Ticker ao) {
    Session session = ConnectionFactory.getInstance().getSession();
    boolean tf = true;

    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      session.saveOrUpdate(ao);

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
    return ao;
  }
  
  //delete an entire collection for an object
  public void deleteTicker( Integer userId) {

    Session session = ConnectionFactory.getInstance().getSession();

    Transaction tx = null;

    try {

      tx = session.beginTransaction();
      PreparedStatement st = session.connection().prepareStatement("delete from ticker where ticker.userId = ?");
      st.setInt(1, userId);
      st.executeUpdate();

      st.close();
    } catch (SQLException ex) {
      Logger.getLogger(AdminService.class.getName()).log(Level.SEVERE, null, ex);
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

  //get a user by integer id (in db)
  public Ticker getSingleTicker(Integer id, Integer boxNo) {

    /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
     */
    Session session = ConnectionFactory.getInstance().getSession();
    List results = null;
    try {

      results = session.find("from Ticker as ticker where ticker.userId = ? and ticker.boxNumber = ? ",
              new Object[]{id, boxNo},
              new Type[]{Hibernate.INTEGER, Hibernate.INTEGER});



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
      return (Ticker) results.get(0);
    }

  }

  public List getTickerList(Integer boxNo) {
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
      query = session.createQuery("select ticker from Ticker ticker where ticker.boxNumber =" + boxNo);
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

  public Excelnetissue saveOrUpdateExcelnetissue(Excelnetissue ei) {
    Session session = ConnectionFactory.getInstance().getSession();
    boolean tf = true;

    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      session.saveOrUpdate(ei);

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
    return null;
  }

  //get all Version
  public List getExcelnetIssueList(String status) {
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
      query = session.createQuery("select excelnetissue from app.admin.Excelnetissue excelnetissue where excelnetissue.status=" + status);
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

  //get all Version
  public List getExcelnetIssueList() {
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
      query = session.createQuery("select excelnetissue from app.admin.Excelnetissue excelnetissue where excelnetissue.status!=3");
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

  public static boolean unlinkExcelnetIssue() {

    Session session = ConnectionFactory.getInstance().getSession();
    boolean t = true;
    Transaction tx = null;

    try {

      tx = session.beginTransaction();
      PreparedStatement st = session.connection().prepareStatement("delete from Excelnetissue where status=1");

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

  //get a issue by integer id (in db)
  public Excelnetissue getSingleExcelnetIssue(Integer id) {

    /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
     */
    Session session = ConnectionFactory.getInstance().getSession();
    List results = null;
    try {

      results = session.find("from Excelnetissue as ei where ei.id = ? ",
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
      return (Excelnetissue) results.get(0);
    }

  }

  //place a new savedSearch in the db
  //build one-to-many link between user and savedSearch
  public <T> void saveResult(Class<T> u) {

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

  public List getMailingList(String mailCategory) {

    /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
     */
    Session session = ConnectionFactory.getInstance().getSession();
    List results = null;
    try {

      results = session.find("from MailingList as ml where ml.mailCategory = ?",
              new Object[]{mailCategory},
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
      return results;
    }

  }

  public MailingList getSingleMailingList(int id) {

    /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
     */
    Session session = ConnectionFactory.getInstance().getSession();
    List results = null;
    try {

      results = session.find("from MailingList as ml where ml.mailid = ?",
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
      return (MailingList) results.get(0);
    }

  }

  public List getMailingList(int id, String mailType) {

    /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
     */
    Session session = ConnectionFactory.getInstance().getSession();
    List results = null;
    try {
      if("C".equalsIgnoreCase(mailType)){
      results = session.find("from MailingListClient as ml where ml.mailId = ?",
              new Object[]{id},
              new Type[]{Hibernate.INTEGER});
      }else{
        results = session.find("from MailingListVendor as ml where ml.mailId = ?",
              new Object[]{id},
              new Type[]{Hibernate.INTEGER});
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
    if (results.isEmpty()) {
      return null;
    } else {
      return results;
    }

  }
  
  public MailingListClient getSingleClientMailingList(int id) {

    /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
     */
    Session session = ConnectionFactory.getInstance().getSession();
    List results = null;
    try {

      results = session.find("from MailingListClient as ml where ml.clientId = ?",
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
      return (MailingListClient) results.get(0);
    }

  }

  void saveMailingListClient(MailingListClient mlc) {
    Session session = ConnectionFactory.getInstance().getSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      session.saveOrUpdate(mlc);
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
  
    void saveMailingListVendor(MailingListVendor mlv) {
    Session session = ConnectionFactory.getInstance().getSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      session.saveOrUpdate(mlv);
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
  
 public Integer saveMailingList(MailingList ml) {
    Session session = ConnectionFactory.getInstance().getSession();
    Transaction tx = null;
     Integer id = null;
    try {
      tx = session.beginTransaction();
      session.saveOrUpdate(ml);
      
      tx.commit();
      id = ml.getMailid();
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

  MailingListVendor getSingleVendorMailingList(String id) {
   
    /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
     */
    Session session = ConnectionFactory.getInstance().getSession();
    List results = null;
    try {

      results = session.find("from MailingListVendor as ml where ml.vendorId = ?",
              new Object[]{id},
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
      return (MailingListVendor) results.get(0);
    }

  }
  
  
 public void addOptionSelect(OptionsSelect oDoc) {
        Session session = ConnectionFactory.getInstance().getSession();
    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      session.saveOrUpdate(oDoc);
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
  
  public void addOption(Options oDoc) {
        Session session = ConnectionFactory.getInstance().getSession();
    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      session.saveOrUpdate(oDoc);
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
    public List<Integer>  getOptionsSelect(int screen) {
          /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        List<Integer> res = new ArrayList<>();
        Set inQuery = new HashSet();
            inQuery.add(screen);
      
        
        JSONArray iresArr = new JSONArray();
        Session session = ConnectionFactory.getInstance().getSession();
        Criteria criteria = session.createCriteria(OptionsSelect.class);
        criteria.add(Expression.in("screen", inQuery));
        
       
        try {
             List<OptionsSelect> results = criteria.list();

            if (results.isEmpty()) {
                return res;
            } else {
                    
                     for(OptionsSelect os : results){
                         if(!res.contains(os.getOptionId())){
                            
                             res.add(os.getOptionId());
                         }
                     }
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

        return res;
    }
  
   public JSONArray getOptions(String type, int screen){
        List<String> optName = new ArrayList<>();
        JSONObject optObj = new JSONObject();
        
        
        JSONArray optArr = new JSONArray();
        Session session = ConnectionFactory.getInstance().getSession();
        List<Options> results = null;
        List<Integer> opsel = getOptionsSelect(screen);
        try {
            results = session.find("from Options as option where type = '"+type+"'");

            if (results.isEmpty()) {
                return null;
            } else {
                JSONObject optNameObj = new JSONObject();
                    
                     for(Options opt : results){
                         if(!optName.contains(opt.getTitle())){
                             optNameObj.put(opt.getTitle(), new JSONObject());
                             optName.add(opt.getTitle());
                         }
                     }
                     List<String> headerName = new ArrayList<>();
                        Map<String, Integer> headerMap = new HashMap();
//                     for(String iso : optName){
                        
                        for(Options option : results){
                            if(option.getParentId() == 0 &&!headerName.contains(option.getTitle())){
                                headerName.add(option.getTitle());
                                headerMap.put(option.getTitle(), option.getId());
                            }
                        }
//                     }
//                   int a = OptionsSelect.PRSAFF;    
                      JSONObject headerNameObj = new JSONObject();
                        for(String header : headerName){
                            JSONArray headerArray = new JSONArray();
                             JSONObject parent = new JSONObject();
                                parent.put("pid", 0);
                                parent.put("id", headerMap.get(header));
                                parent.put("title", header);
                                if(opsel.contains(headerMap.get(header))){
                                    parent.put("status", "checked");
                                }
                         if(headerMap.get(header)==4)  {
                             System.err.println(headerMap.get(header));
                         }     
                     
                        for(Options option : results){
                            if(option.getParentId().intValue() == headerMap.get(header).intValue()){
                                JSONObject child = new JSONObject();
                                child.put("pid", option.getParentId());
                                child.put("id", option.getId());
                                child.put("title", option.getTitle());
                                if(opsel.contains(option.getId())){
                                    child.put("status", "checked");
                                }
                                headerArray.put(child);
                            }
                        }
                        parent.put("child", headerArray);
                        optArr.put(parent);
                     }
                         //System.out.println(headerNameObj);   
                     
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

        return optArr;
        
   }

    public void removeOptionsChange(Integer sid) {
        
    Session session = ConnectionFactory.getInstance().getSession();

    Transaction tx = null;

    
    try {

      tx = session.beginTransaction();
      PreparedStatement st = session.connection().prepareStatement("delete from options_select where screen = "+sid);
//      st.setInt(1, sid);
      
      st.executeUpdate();
      tx.commit();
      st.close();
    } catch (SQLException ex) {
      Logger.getLogger(AdminService.class.getName()).log(Level.SEVERE, null, ex);
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

    public List getAdminMiscList(String type) {
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
      query = session.createQuery("select misc from app.admin.AdminMisc misc where misc.type = '"+type+"' order by misc.id");
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
    
     public ExcelnetIssueAction getSinglegetExcelnetIssueAction(int id) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from ExcelnetIssueAction as cp where cp.id = ?",
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
            return (ExcelnetIssueAction) results.get(0);
        }

    }

    public List<ExcelnetIssueAction> getExcelnetIssueAction(int vid) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List<ExcelnetIssueAction> results = new ArrayList<>();
        try {
            ////System.out.println("Initializing projectId: "+id);app.qms.RiskHazardIndex

            results = session.find("from ExcelnetIssueAction as scp where vid = ?",
                    new Object[]{vid},
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

        return results;
    }
}
