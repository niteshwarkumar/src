/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import net.sf.hibernate.*;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.*;
import net.sf.hibernate.Hibernate;
import java.util.*;
import app.db.*;
import java.sql.PreparedStatement;

/**
 *
 * @author Niteshwar
 */
public class QMSServiceDelete {

  private static QMSServiceDelete instance = null;

  public QMSServiceDelete() {
  }

  //return the instance of QMSService
  public static synchronized QMSServiceDelete getInstance() {
    /*
     * Creates the Singleton instance, if needed.
     *
     */
    if (instance == null) {
      instance = new QMSServiceDelete();
    }
    return instance;
  }

  public QMSAudit deleteAudit(QMSAudit p) {
    Session session = ConnectionFactory.getInstance().getSession();
    boolean tf = true;

    Transaction tx = null;

    try {

      tx = session.beginTransaction();
      //System.out.println("product detailsin save-------------->" + p);
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
    return p;
  }

  public TrainingNotify deletetTraininNotify(TrainingNotify p) {
    Session session = ConnectionFactory.getInstance().getSession();
    boolean tf = true;

    Transaction tx = null;

    try {

      tx = session.beginTransaction();
      //System.out.println("product detailsin save-------------->" + p);
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
    return p;
  }

  public QMSReportUpload deletetQMSReport(QMSReportUpload p) {
    Session session = ConnectionFactory.getInstance().getSession();
    boolean tf = true;

    Transaction tx = null;

    try {

      tx = session.beginTransaction();
      //System.out.println("product detailsin save-------------->" + p);
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
    return p;
  }

  public static boolean deleteCapa(int id) {
    Session session = ConnectionFactory.getInstance().getSession();
    boolean t = true;
    Transaction tx = null;

    try {

      tx = session.beginTransaction();
      PreparedStatement st = session.connection().prepareStatement("delete from capa where capa_id=" + id);

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
  public static boolean deleteStrategicChange(int id) {
    Session session = ConnectionFactory.getInstance().getSession();
    boolean t = true;
    Transaction tx = null;

    try {

      tx = session.beginTransaction();
      PreparedStatement st = session.connection().prepareStatement("delete from strategicchange where id=" + id);

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
  
  public static boolean deleteIsoDoc(int docId) {
    Session session = ConnectionFactory.getInstance().getSession();
    boolean t = true;
    Transaction tx = null;

    try {

      tx = session.beginTransaction();
      PreparedStatement st = session.connection().prepareStatement("delete from iso_doc where docId=" + docId);

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

  //delete one client language pair
  public void deleteDocument(QMSLibrary libUp) {

    Session session = ConnectionFactory.getInstance().getSession();

    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      session.delete(libUp);
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

  public static boolean unlinkHistory(int id) {

    Session session = ConnectionFactory.getInstance().getSession();
    boolean t = true;
    Transaction tx = null;

    try {

      tx = session.beginTransaction();
      PreparedStatement st = session.connection().prepareStatement("delete from qmslibraryhistory where id = " + id);

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

  public RiskHazardIndex deleteRiskHazardIndex(RiskHazardIndex p) {
    Session session = ConnectionFactory.getInstance().getSession();
    boolean tf = true;

    Transaction tx = null;

    try {

      tx = session.beginTransaction();
      //System.out.println("product detailsin save-------------->" + p);
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
    return p;
  }

  public RiskMitigation deleteRiskMitigation(RiskMitigation p) {
    Session session = ConnectionFactory.getInstance().getSession();
    boolean tf = true;

    Transaction tx = null;

    try {

      tx = session.beginTransaction();
      //System.out.println("product detailsin save-------------->" + p);
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
    return p;
  }

  public RiskProbability deleteRiskProbability(RiskProbability p) {
    Session session = ConnectionFactory.getInstance().getSession();
    boolean tf = true;

    Transaction tx = null;

    try {

      tx = session.beginTransaction();
      //System.out.println("product detailsin save-------------->" + p);
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
    return p;
  }
  
  public RiskSeverity deleteRiskSeverity(RiskSeverity p) {
    Session session = ConnectionFactory.getInstance().getSession();
    boolean tf = true;

    Transaction tx = null;

    try {

      tx = session.beginTransaction();
      //System.out.println("product detailsin save-------------->" + p);
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
    return p;
  }


}
