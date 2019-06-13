/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.inteqa;

import java.util.*;
import net.sf.hibernate.*;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.*;
import net.sf.hibernate.Hibernate;
import app.db.*;
import app.project.Project;
import app.project.ProjectService;
import app.project.SourceDoc;
import app.project.TargetDoc;
import java.sql.PreparedStatement;

/**
 *
 * @author Niteshwar
 */
public class InteqaService {

  private static InteqaService instance = null;

  public InteqaService() {
  }

  //return the instance of InteqaService
  public static synchronized InteqaService getInstance() {
    /*
     * Creates the Singleton instance, if needed.
     *
     */
    if (instance == null) {
      instance = new InteqaService();
    }
    return instance;
  }

  //add inspections to this project to the db
  public INReport updateInReport(INReport ir) {

    Session session = ConnectionFactory.getInstance().getSession();

    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      session.saveOrUpdate(ir);
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
    return ir;
  }

  //add inspections to this project to the db
  public INSourceFile updateInSourceFile(INSourceFile ir) {

    Session session = ConnectionFactory.getInstance().getSession();

    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      session.saveOrUpdate(ir);
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
    return ir;
  }

  //add inspections to this project to the db
  public INDelivery updateInDelivery(INDelivery idel) {

    Session session = ConnectionFactory.getInstance().getSession();

    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      session.saveOrUpdate(idel);
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
    return idel;
  }

  //add InDeliveryReq to this project to the db
  public INDeliveryReq updateInDeliveryReq(INDeliveryReq idel) {

    Session session = ConnectionFactory.getInstance().getSession();

    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      session.saveOrUpdate(idel);
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
    return idel;
  }

  public static boolean unlinkInDelReq(int id) {

    Session session = ConnectionFactory.getInstance().getSession();
    boolean t = true;
    Transaction tx = null;

    try {

      tx = session.beginTransaction();
      PreparedStatement st = session.connection().prepareStatement("delete from in_deliveryreq where id = " + id);

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
  //add InDeliveryReq to this project to the db
  public INReference updateInReference(INReference iRef) {

    Session session = ConnectionFactory.getInstance().getSession();

    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      session.saveOrUpdate(iRef);
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
    return iRef;
  }
  
   public INReference removeInReference(INReference iRef) {

    Session session = ConnectionFactory.getInstance().getSession();

    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      session.delete(iRef);
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
    return iRef;
  }

  public static boolean unlinkInref(int id) {

    Session session = ConnectionFactory.getInstance().getSession();
    boolean t = true;
    Transaction tx = null;

    try {

      tx = session.beginTransaction();
      PreparedStatement st = session.connection().prepareStatement("delete from in_reference where id = " + id);

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

  public static boolean unlinkInSourceFile(int id) {

    Session session = ConnectionFactory.getInstance().getSession();
    boolean t = true;
    Transaction tx = null;

    try {

      tx = session.beginTransaction();
      PreparedStatement st = session.connection().prepareStatement("delete from in_sourcefile where projectId = " + id);

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

  public INReport getINReport(Integer id) {

    /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
     */
    Session session = ConnectionFactory.getInstance().getSession();
    List results = null;
    try {
      ////System.out.println("Initializing projectId: "+id);

      results = session.find("from INReport as inreport where inreport.projectId = ?",
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

      return (INReport) results.get(0);
    }

  }

  //add inspections to this projpect to the db
  public INDtp updateInDtp(INDtp idtp) {

    Session session = ConnectionFactory.getInstance().getSession();

    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      session.saveOrUpdate(idtp);
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
    return idtp;
  }

  //add inspections to this projpect to the db
  public INEngineering updateInEngineering(INEngineering ieng) {

    Session session = ConnectionFactory.getInstance().getSession();

    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      session.saveOrUpdate(ieng);
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
    return ieng;
  }

  //add inspections to this projpect to the db
  public INBasics updateInBasics(INBasics iBasics) {

    Session session = ConnectionFactory.getInstance().getSession();

    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      session.saveOrUpdate(iBasics);
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
    return iBasics;
  }

  public INDtp getINDtp(Integer id) {

    /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
     */
    Session session = ConnectionFactory.getInstance().getSession();
    List results = null;
    try {
      ////System.out.println("Initializing projectId: "+id);



      results = session.find("from INDtp as indtp where indtp.projectId = ?",
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

      return (INDtp) results.get(0);
    }

  }

  public INEngineering getINEngineering(Integer id) {

    /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
     */
    Session session = ConnectionFactory.getInstance().getSession();
    List results = null;
    try {
      ////System.out.println("Initializing projectId: "+id);



      results = session.find("from INEngineering as INEngineering where INEngineering.projectId = ?",
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

      return (INEngineering) results.get(0);
    }

  }

  public INBasics getINBasics(Integer id) {

    /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
     */
    Session session = ConnectionFactory.getInstance().getSession();
    List results = null;
    try {
      ////System.out.println("Initializing projectId: "+id);



      results = session.find("from INBasics as INBasics where INBasics.projectId = ?",
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

      return (INBasics) results.get(0);
    }

  }

  public boolean isQuoteBlocked(Integer projectid) {

    INReport inRep = getINReport(projectid);
    if (inRep == null) {
      return false;
    }
    if (inRep.isVerified() == false) {
      return false;
    }
    return true;
  }

  public boolean isProjectBlocked(Integer projectid) {


    Project object = ProjectService.getInstance().getSingleProjectWithoutInitialize(projectid);
    boolean dtpTaskFlag = false;
    List sources = ProjectService.getInstance().getSourceDoc(object);
    for (Iterator iter = sources.iterator(); iter.hasNext();) {
      SourceDoc sd = (SourceDoc) iter.next();
      for (Iterator iterT = sd.getTargetDocs().iterator(); iterT.hasNext();) {
        TargetDoc td = (TargetDoc) iterT.next();
        if (0 != td.getDtpTasks().size()) {
          dtpTaskFlag = true;
          break;
        }
      }
    }
    if (dtpTaskFlag == true) {
      INDelivery indel = getINDelivery(projectid);
      if (indel == null) {
        return false;
      }
      if (indel.isVerified() == false) {
        return false;
      }
//      if (indel.isEngverified() == false) {
//        return false;
//      }
//      if (indel.isDtpverified() == false) {
//        return false;
//      }
    }
    return true;
  }
  public boolean isProjectBlocked1(Integer projectid) {


    Project object = ProjectService.getInstance().getSingleProjectWithoutInitialize(projectid);
//    boolean dtpTaskFlag = false;
//    List sources = ProjectService.getInstance().getSourceDoc(object);
//    for (Iterator iter = sources.iterator(); iter.hasNext();) {
//      SourceDoc sd = (SourceDoc) iter.next();
//      for (Iterator iterT = sd.getTargetDocs().iterator(); iterT.hasNext();) {
//        TargetDoc td = (TargetDoc) iterT.next();
//        if (0 != td.getDtpTasks().size()) {
//          dtpTaskFlag = true;
//          break;
//        }
//      }
//    }
//    if (dtpTaskFlag == true) {
      INDelivery indel = getINDelivery(projectid);
      if (indel == null) {
        return false;
      }
      if (indel.isVerified() == false) {
        return false;
      }
//      if (indel.isEngverified() == false) {
//        return false;
//      }
//      if (indel.isDtpverified() == false) {
//        return false;
//      }
//    }
    return true;
  }

  public boolean isProjectBlockedClientReq(Integer projectid) {
 Project object = ProjectService.getInstance().getSingleProject(projectid);
    boolean dtpTaskFlag = false;
    for (Iterator iter = object.getSourceDocs().iterator(); iter.hasNext();) {
      SourceDoc sd = (SourceDoc) iter.next();
      for (Iterator iterT = sd.getTargetDocs().iterator(); iterT.hasNext();) {
        TargetDoc td = (TargetDoc) iterT.next();
        if (0 != td.getDtpTasks().size()) {
          dtpTaskFlag = true;
          break;
        }
      }
    }
    if (dtpTaskFlag == true) {
    INDelivery indel = getINDelivery(projectid);
    if (indel == null) {
      return false;
    }
    List inDelReqList = getInDeliveryReqGrid(indel.getId(),"R");
    if (inDelReqList == null) {
      return true;
    }
    for (int i = 0; i < inDelReqList.size(); i++) {
      INDeliveryReq inDelReq = (INDeliveryReq) inDelReqList.get(i);
      if (inDelReq.isClientReqCheck() == false) {
        return false;
      }
    }}
    return true;
  }

  public INDelivery getINDelivery(Integer id) {

    /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
     */
    Session session = ConnectionFactory.getInstance().getSession();
    List results = null;
    try {
      ////System.out.println("Initializing projectId: "+id);



      results = session.find("from INDelivery as INDelivery where INDelivery.projectId = ?",
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

      return (INDelivery) results.get(0);
    }

  }

  public INDeliveryReq getINDeliveryReq(Integer id) {

    /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
     */
    Session session = ConnectionFactory.getInstance().getSession();
    List results = null;
    try {
      ////System.out.println("Initializing projectId: "+id);



      results = session.find("from INDeliveryReq as INDeliveryReq where INDeliveryReq.id = ?",
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

      return (INDeliveryReq) results.get(0);
    }

  }

  public List getInDeliveryReqGrid(int inDeliveryId, String type) {
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
      //  query = session.createQuery("select quote from app.quote.Quote1 quote where quote.status = 'pending' and DATEDIFF(" + date+",quote.quoteDate) > 120 order by quote.number ");
      //query = session.createQuery("select project from app.project.Project project order by project.number");
      query = session.createQuery("select inDeliveryReq from app.inteqa.INDeliveryReq inDeliveryReq where type='"+type+"' and inDeliveryReq.inDeliveryId=" + inDeliveryId);
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

  public List getSourceFileList(int projectId) {
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
      query = session.createQuery("select sourceFile from app.inteqa.INSourceFile sourceFile where projectId =" + projectId);
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

  public INSourceFile getSourceFile(Integer id) {

    /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
     */
    Session session = ConnectionFactory.getInstance().getSession();
    List results = null;
    try {

      results = session.find("from INSourceFile as sourceFile where sourceFile.id = ?",
              new Object[]{id},
              new Type[]{Hibernate.INTEGER});

      if (results.isEmpty()) {
        return null;
      } else {
        INSourceFile q = (INSourceFile) results.get(0);
        // Hibernate.initialize(q.getSourceDocs());
        // Hibernate.initialize(q.getFiles());

        return q;
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
  //add inspections to this project to the db

  public INQualityFeedback updateInQualityFeedback(INQualityFeedback ir) {

    Session session = ConnectionFactory.getInstance().getSession();

    Transaction tx = null;

    try {

      tx = session.beginTransaction();

      session.saveOrUpdate(ir);
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
    return ir;
  }

  public static boolean unlinkINQualityFeedback(int id) {

    Session session = ConnectionFactory.getInstance().getSession();
    boolean t = true;
    Transaction tx = null;

    try {

      tx = session.beginTransaction();
      PreparedStatement st = session.connection().prepareStatement("delete from in_qualityfeedback where projectId = " + id);

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

  public INQualityFeedback getINQualityFeedback(Integer inteqaid, String porq, String tab) {

    /*
     * Use the ConnectionFactory to retrieve an open
     * Hibernate Session.
     *
     */
    Session session = ConnectionFactory.getInstance().getSession();
    List results = null;
    try {

      results = session.find("from INQualityFeedback as qualityFeedback where qualityFeedback.inteqaid = ? and qualityFeedback.porq = ? and tab= ? ",
              new Object[]{inteqaid, porq, tab},
              new Type[]{Hibernate.INTEGER, Hibernate.STRING, Hibernate.STRING});

      if (results.isEmpty()) {
        return null;
      } else {
        INQualityFeedback q = (INQualityFeedback) results.get(0);
        // Hibernate.initialize(q.getSourceDocs());
        // Hibernate.initialize(q.getFiles());

        return q;
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

    public List getInReference(Integer projectId) {
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
      query = session.createQuery("select ref from app.inteqa.INReference ref where projectId =" + projectId);
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
