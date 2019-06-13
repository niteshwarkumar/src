/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.util;

import app.admin.AdminService;
import app.db.ConnectionFactory;
import app.user.Training;
import app.user.User;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.hibernate.Criteria;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.expression.Expression;
import net.sf.hibernate.expression.Order;
import net.sf.hibernate.type.Type;

/**
 *
 * @author abhisheksingh
 */
public class UtilService  {

  private static UtilService instance = null;

  public UtilService() {
  }

  //place a new user in the db
  public Integer addTemp(Temp t) {

    //the new User's id
    Integer id = null;

    Session session = ConnectionFactory.getInstance().getSession();

    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      session.saveOrUpdate(t);
      tx.commit();

      id = t.getId();
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
  public void hqlTruncate(String myTable) {

    Session session = ConnectionFactory.getInstance().getSession();

    Transaction tx = null;

    try {

      tx = session.beginTransaction();
      String hql = String.format("delete from %s",myTable);
      PreparedStatement st = session.connection().prepareStatement(hql);
      


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


  public void deleteTemp(Temp o) {

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
  
  /**
   * getInstance() returns the instance of the
   * <code>ItemService</code> singleton.
   *
   * @return <code>ItemService</code> singleton.
   */
  public static synchronized UtilService getInstance() {
    /*
     * Creates the Singleton instance, if needed.
     * 
     */
    if (instance == null) {
      instance = new UtilService();
    }
    return instance;
  }

  //get a user by integer id (in db)
  public Temp getSingleTemp(Integer id) {

    /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     * 
     */
    Session session = ConnectionFactory.getInstance().getSession();
    List results = null;
    try {

      results = session.find("from Temp as tmp where tmp.id = ?",
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
      return (Temp) results.get(0);
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

}

