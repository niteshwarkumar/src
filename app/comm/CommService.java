/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.comm;

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
import net.sf.hibernate.expression.Expression;

/**
 *
 * @author Niteshwar
 */
public class CommService {

 private static CommService instance = null;

    public CommService() {
    }
        //return the instance of CommService
    public static synchronized CommService getInstance() {
        /*
         * Creates the Singleton instance, if needed.
         *
         */
        if (instance == null) {
            instance = new CommService();
        }
        return instance;
    }

     //add a new client to the database
    public LibraryUpload addLibrary(LibraryUpload l) {

        Session session = ConnectionFactory.getInstance().getSession();

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

        //delete one client language pair
    public void deleteDocument(LibraryUpload libUp) {

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

    //get  Library by Tabs
    public List getLibraryDocumentByTabs(String tabName,String subTab) {
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
            query = session.createQuery("select ul from app.comm.LibraryUpload ul where maintab='"+tabName+"' and heading='"+subTab+"' order by uploaddate desc");
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
    
     //get  Library by Tabs
    public List getLibraryDocumentByTabs(String tabName,String subTab, Integer clientId) {
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
            query = session.createQuery("select ul from app.comm.LibraryUpload ul where maintab='"+tabName+"' and heading='"+subTab+"' and clientId="+clientId +" order by uploaddate desc");
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



     //get single Document
    public LibraryUpload getSingleLibraryDocument(Integer libId) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            results = session.find("from LibraryUpload as ul where ul.libId = ?",
                    new Object[]{libId},
                    new Type[]{Hibernate.INTEGER});
            if (results.isEmpty()) {
                return null;
            } else {
                LibraryUpload c = (LibraryUpload) results.get(0);
                return c;
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
    }

 //add a new client to the database
    public Requirement addRequirement(Requirement req) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.save(req);
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
        return req;
    }
    
    //add a new client to the database
    public Requirement updateRequirement(Requirement req) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(req);
            tx.commit();
        } catch (HibernateException e) {
            try {
                tx.rollback(); //error
            } catch (HibernateException he) {
                System.err.println("Hibernate Exception" + e.getMessage());
                throw new RuntimeException(e);
            }
            System.err.println("Hibernate Exception" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
            if (session != null) {
                try {

                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }
        return req;
    }

        //delete one client language pair
    public void deleteRequirement(Requirement req) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(req);
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

    public List<Requirement> getRequirement(String type, String tab, int clientId, int projectId) {
     /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
//        if(type.equalsIgnoreCase("G")){
//            clientId=0;
//            projectId=0;
//        }
//        if(type.equalsIgnoreCase("C")){
//            projectId=0;
//        }

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            query = session.createQuery("select ul from app.comm.Requirement ul where type='"+type+"' and tab='"+tab+"' and clientId ="+clientId+" and projectId ="+projectId);
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

    Requirement getRequirement(int id) {
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
            query = session.createQuery("select ul from app.comm.Requirement ul where id="+id);
            return (Requirement) query.list().get(0);
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

    void deleteReqPO(int projectId, String type) {
    Session session = ConnectionFactory.getInstance().getSession();
    
    Transaction tx = null;

    try {

      tx = session.beginTransaction();
      PreparedStatement st = session.connection().prepareStatement("delete from requirementPO where projectId=" + projectId  +" and type = '"+type+"'");

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

    void addReqPo(RequirementPO reqPo) {
Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.save(reqPo);
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
    
       public List<Integer> getReqPoList(Integer projectId) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        List<Integer> iso = new ArrayList<>();
        
  
        Session session = ConnectionFactory.getInstance().getSession();
        Criteria criteria = session.createCriteria(RequirementPO.class);
        criteria.add(Expression.eq("projectId", projectId));
        
       
        try {
             List<RequirementPO> results = criteria.list();

            if (results.isEmpty()) {
                return iso;
            } else {
                    
                     for(RequirementPO isoDoc : results){
                         if(!iso.contains(isoDoc.getRequirementId())){
                            
                             iso.add(isoDoc.getRequirementId());
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

        return iso;
    }

   

    public void addProjectRequirementOnQuoteApprove(Integer projectId, Integer clientId) {
        List<Requirement> reqList = new ArrayList();
        reqList.addAll(getRequirement("G",  "D",  0,  0));
        reqList.addAll(getRequirement("G",  "E",  0,  0));
        reqList.addAll(getRequirement("C",  "D",  clientId,  0));
        reqList.addAll(getRequirement("C",  "E",  clientId,  0));
        for(Requirement req : reqList){
            Requirement newReq = new Requirement();
            newReq.setClientId(0);
            newReq.setProjectId(projectId);
            newReq.setRequirement(req.getRequirement());
            newReq.setTab(req.getTab());
            newReq.setType(req.getType());
            newReq.setUserId(0);
            addRequirement(newReq);
                            
        }

    
    }

    public static void main(String[] args) {
        CommService.getInstance().addProjectRequirementOnQuoteApprove(13712, 104);
    }


}
