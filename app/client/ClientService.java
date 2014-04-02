//ClientService.java contains the db code related to clients to actually read/write
//to/from db
package app.client;

import java.util.*;
import net.sf.hibernate.*;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.*;
import net.sf.hibernate.Hibernate;
import app.db.*;
import app.extjs.vo.ClientLocation;
import app.extjs.vo.Communication;
import app.project.*;
import net.sf.hibernate.Criteria;
import net.sf.hibernate.expression.*;
import app.extjs.vo.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.json.JSONArray;
import org.json.JSONObject;

public class ClientService {

    private static ClientService instance = null;

    public ClientService() {
        System.out.println("ClientService construtor called@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

    }

    //return the instance of ClientService
    public static synchronized ClientService getInstance() {
        /*
         * Creates the Singleton instance, if needed.
         *
         */
        if (instance == null) {
            instance = new ClientService();
        }
        return instance;
    }

    //add a new client to the database
    public Client addClient(Client c) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.save(c);
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
        return c;
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

    //update an existing Client in database
    public Client updateClient(Client c) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

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
        return c;
    }

    //update an existing industry in database
    public Industry updateIndustry(Industry i) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(i);

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

    //update an existing specificIndustry in database
    public SpecificIndustry updateSpecificIndustry(SpecificIndustry i) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(i);

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

    //update an existing Client language pair in database
    public ClientLanguagePair updateClp(ClientLanguagePair clp) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(clp);

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
        return clp;
    }

    //update an existing Client other rate in database
    public ClientOtherRate updateCor(ClientOtherRate cor) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.saveOrUpdate(cor);

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
        return cor;
    }

    //get all clients
    public List getClientList() {
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
            query = session.createQuery("select client from app.client.Client client order by client.Company_name");
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

    //search project
    //search for clients given the criteria
    public List getClientSearch(String status, String companyName, String clientContactLastName, String clientContactFirstName,String clientLocation,String clientPhoneNo, String sales_rep, Integer industry_Id) {

        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List results = null;

        try {
            //retreive clients from database

            //this is the "one" class: it is the main criteria
            Criteria criteria = session.createCriteria(Client.class);

            //if subcriteria values are present from the form (for the "many" class),
            //then include subcriteria search values in main search (the "one" class)
            if (clientContactLastName.length() >= 1 || clientContactFirstName.length() >= 1) {
                Criteria subCriteria = criteria.createCriteria("Contacts");
                subCriteria.add(Expression.like("Last_name", "%" + clientContactLastName + "%").ignoreCase());
                subCriteria.add(Expression.like("First_name", "%" + clientContactFirstName + "%").ignoreCase());
            }

            //add search on status if status from form does not equal "All"
            if (!status.equals("All")) {
                criteria.add(Expression.eq("Status", status));
            }

            
            criteria.add(Expression.like("Company_name", companyName + "%").ignoreCase());
            criteria.addOrder(Order.desc("Company_name"));

            criteria.add(Expression.like("City", clientLocation + "%").ignoreCase());
            criteria.addOrder(Order.desc("City"));

            criteria.add(Expression.like("Sales_rep", sales_rep + "%").ignoreCase());
            criteria.addOrder(Order.desc("Sales_rep"));
            
            if(industry_Id>0){
                 Criteria subCriteria = criteria.createCriteria("Industry");
                subCriteria.add(Expression.like("industryId", industry_Id ));
            }
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

    //return one client with id given in argument
    public Client getSingleClient(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Client as client where client.id = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});
            if (results.isEmpty()) {
                return null;
            } else {
                Client c = (Client) results.get(0);
                System.out.println("resuklt>>>>>>>>>>>>>>>" + c);


                for (Iterator iter = c.getProjects().iterator(); iter.hasNext();) {
                    Project p = (Project) iter.next();
                    Hibernate.initialize(p.getQuotes());
                }

                Hibernate.initialize(c.getClientLanguagePairs());
                Hibernate.initialize(c.getClientOtherRates());

                return c;
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

       //return one client with id given in argument
    public Client getSingleClientById(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Client as client where client.id = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});
            if (results.isEmpty()) {
                return null;
            } else {
                Client c = (Client) results.get(0);
//                System.out.println("resuklt>>>>>>>>>>>>>>>" + c);
//
//
//                for (Iterator iter = c.getProjects().iterator(); iter.hasNext();) {
//                    Project p = (Project) iter.next();
//                    Hibernate.initialize(p.getQuotes());
//                }
//
//                Hibernate.initialize(c.getClientLanguagePairs());
//                Hibernate.initialize(c.getClientOtherRates());

                return c;
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

    public Client getSingleClient(String client) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Client as client where client.Company_name = ?",
                    new Object[]{client},
                    new Type[]{Hibernate.STRING});


            System.out.println("querry-------------------------" + results);
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
            return (Client) results.get(0);
        }

    }

    //return one client language pair with id given in argument
    public ClientLanguagePair getSingleClp(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from ClientLanguagePair as clp where clp.clientlanguagePairId = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});
            if (results.isEmpty()) {
                return null;
            } else {
                ClientLanguagePair clp = (ClientLanguagePair) results.get(0);

                return clp;
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

    //return one client other rate with id given in argument
    public ClientOtherRate getSingleCor(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from ClientOtherRate as cor where cor.clientOtherRateId = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});
            if (results.isEmpty()) {
                return null;
            } else {
                ClientOtherRate cor = (ClientOtherRate) results.get(0);

                return cor;
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

    //delete one client language pair
    public void deleteClp(ClientLanguagePair clp) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(clp);
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

    //delete one client other rate
    public void deleteCor(ClientOtherRate cor) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(cor);
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

    //delete one contact
    public void deleteContact(ClientContact object) {

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

    //delete one contact
    public void deleteClient(Client object) {

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

    //delete one Industry
    public void deleteIndustry(Industry i) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(i);
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

    //delete one SpecificIndustry
    public void deleteSpecificIndustry(SpecificIndustry i) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            session.delete(i);
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

    //add this client language pair to the db, building one-to-many relationship between client and client language pair
    //return new client language pair id
    public Integer addClpWithClient(ClientLanguagePair clp, Client c) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;
        Integer id = null;

        try {

            tx = session.beginTransaction();

            //build relationship
            c.getClientLanguagePairs().add(clp);
            clp.setCompany(c);

            session.save(clp);
            tx.commit();
            id = clp.getClientlanguagePairId();
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

    //add this specificIndustry to the db, building one-to-many relationship between industry and specificIndustry
    //return new specificIndustry id
    public Integer addSpecificIndustry(SpecificIndustry si, Industry i) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;
        Integer id = null;

        try {

            tx = session.beginTransaction();

            //build relationship
            i.getSpecificIndustries().add(si);
            si.setIndustry(i);

            session.save(si);
            tx.commit();
            id = si.getSpecificIndustryId();
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

    //add this client other rate to the db, building one-to-many relationship between client and client other rate
    //return new client other rate id
    public Integer addCorWithClient(ClientOtherRate cor, Client c) {
        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;
        Integer id = null;

        try {

            tx = session.beginTransaction();

            //build relationship
            c.getClientOtherRates().add(cor);
            cor.setCompany(c);

            session.save(cor);
            tx.commit();
            id = cor.getClientOtherRateId();
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

    //return one industry with id given in argument
    public Industry getSingleIndustry(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from Industry as industry where industry.industryId = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                Industry i = (Industry) results.get(0);

                Hibernate.initialize(i.getResources());

                Hibernate.initialize(i.getClients());

                return i;
            }

        } catch (ObjectNotFoundException onfe) {
            System.out.println("ObjectNotFoundException" + onfe.getMessage());
            return null;
        } catch (HibernateException e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
            System.out.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            /*
             * All Hibernate Exceptions are transformed into an unchecked
             * RuntimeException.  This will have the effect of causing the
             * user's request to return an error.
             *
             */
            System.out.println("Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
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

    //return one specificIndustry with id given in argument
    public SpecificIndustry getSingleSpecificIndustry(Integer id) {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {

            results = session.find("from SpecificIndustry as specificIndustry where specificIndustry.specificIndustryId = ?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});
            if (results.isEmpty()) {
                return null;
            } else {
                SpecificIndustry si = (SpecificIndustry) results.get(0);

                Hibernate.initialize(si.getResources());

                return si;
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

            results = session.find("from ClientContact as clientContact where clientContact.id = ?", new Object[]{id}, new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                ClientContact cc = (ClientContact) results.get(0);

                Hibernate.initialize(cc.getProjects());

                return cc;
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

            if (results.isEmpty()) {
                return null;
            } else {
                Client c = (Client) results.get(0);

                Hibernate.initialize(c.getProjects());

                return c;
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

            if (results.isEmpty()) {
                return null;
            } else {
                Client c = (Client) results.get(0);
                return c;
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
            query = session.createQuery("select industry from app.client.Industry industry order by industry.Description asc");
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

    //get all specific industries
    public List getSpecificIndustryList() {
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
            query = session.createQuery("select specificIndustry from app.client.SpecificIndustry specificIndustry order by specificIndustry.specificIndustry asc");
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

    //provide a list of statuses for forms (in clients)
    public String[] getStatuses() {
        String Status[] = {"Prospect", "Active", "Lost"};
        return Status;
    }

    //delete one change (project change)
    public void deleteClientContact(ClientContact c) {

        Session session = ConnectionFactory.getInstance().getSession();

        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            c.setDisplay(false);
            session.saveOrUpdate(c);
            //session.delete(c);
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

    public Client getClient(int client_ID) {
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {
            results = session.find("from Client as cc where cc.clientId = ?",
                    new Object[]{new Integer(client_ID)},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                Client c = (Client) results.get(0);
                return c;
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
    //return a Client object, but search for the client by compny code instead of by integer id
    //this client is already in db, so check if code is already in db






    public ClientContact getSingleClientContact(String clientContactId) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        List results = null;
        try {
            results = session.find("from ClientContact as cc where cc.clientContactId = ?",
                    new Object[]{new Integer(clientContactId)},
                    new Type[]{Hibernate.INTEGER});

            if (results.isEmpty()) {
                return null;
            } else {
                ClientContact c = (ClientContact) results.get(0);
                return c;
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

    //update an existing Client in database
    public Product saveProduct(Product p) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            System.out.println("product detailsin save-------------->" + p);
            session.saveOrUpdate(p);

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

     public Product deleteProduct(Product p) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            System.out.println("product detailsin save-------------->" + p);
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

    //update an existing Client in database

    public Blog saveBlog(Blog p) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            System.out.println("saveBlog@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            session.save(p);
            System.out.println("save@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            tx.commit();
            System.out.println("bfr commit@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
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

    public static boolean unlinkClientAndProducts(int clientId) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from Products where ID_Client=?");
            st.setInt(1, clientId);
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

    //blog
    public static boolean unlinkBlog() {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from blog");

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

    //get all clients




    public List getProductListForClient(Integer clientId) {
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
            query = session.createQuery("select product from app.extjs.vo.Product product where ID_Client= " + clientId + " order by product.product ");
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
    
    public List getProductArray() {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List result;
        

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            query = session.createQuery("select product from app.extjs.vo.Product product ");
            result = query.list();
            
            return result;
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

    //get all blog
    public List getBlogList(int id) {
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
            query = session.createQuery("select *  from blog where id=?");
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

    //get all clients
    public List getLocationsListForClient(String clientId) {
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
            query = session.createQuery("select clientLocation from app.extjs.vo.ClientLocation clientLocation where ID_Client= " + clientId);
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

     public List getDivisionForClient(String id) {
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
            query = session.createQuery("select clientLocation from app.extjs.vo.ClientLocation clientLocation where location_id= " + id);
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


    //get all clients
    public List getServiceListForClient(String clientId) {
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
            //      query =	session.createQuery("select product from app.extjs.vo.Product product where ID_Client= "+ clientId + " order by product.product ");

            query = session.createQuery("select cs from app.extjs.vo.ClientService cs where ID_Client='" + clientId + "'");
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

    //get all clients
    public String getAllCertifications(String certIds) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        String result = "<select name='certs' class='x-btn' multiple size='8'>";
        if (certIds == null) {
            certIds = "";
        }

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            PreparedStatement st = session.connection().prepareStatement("select * from Dropdown where dropdownType='Certifications' order by dropdownValue asc");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String selected = "";

                if (certIds.indexOf(rs.getString("dropdownValue") + "_____") > -1) {
                    selected = " selected ";
                }
                result += "<option " + selected + " value='" + rs.getString("dropdownValue") + "'>" + rs.getString("dropdownValue") + "  </option>";

            }
            result += "</select>";
            st.close();
            return result;

        } catch (Exception e) {
            System.err.println("Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
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

    //get all clients
    public String getClientProducts(int clientId, String product) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        String result = "<select name='product' id='product' property='pName' class='x-btn'><option value=''></option>";


        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            PreparedStatement st = session.connection().prepareStatement("select * from Products where  ID_Client=? order by product asc");
            st.setInt(1, clientId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String selected = "";

                if (rs.getString("product").equals(product)) {
                    selected = " selected ";
                }
                result += "<option " + selected + " value=\"" + rs.getString("product") + "\">" + rs.getString("product")+" - "+rs.getString("category") + "  </option>";

            }
            result += "</select>";
            st.close();
            return result;

        } catch (Exception e) {
            System.err.println("Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
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
    
        //get all clients
    public String getClientProductsList(int clientId, String product) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        String result = "<html:select property='pName' ><html:option value=''></html:option>";


        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            PreparedStatement st = session.connection().prepareStatement("select * from Products where  ID_Client=? order by product asc");
            st.setInt(1, clientId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
//                String selected = "";

//                if (rs.getString("product").equals(product)) {
//                    selected = " selected ";
//                }
                result += "<html:option value=\"" + rs.getString("product") + "\">" + rs.getString("product")+" - "+rs.getString("category") + "  </html:option>";

            }
            result += "</html:select>";
            st.close();
            return result;

        } catch (Exception e) {
            System.err.println("Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
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




   
    //get all clients
    public int countAllProjectsForContact(int contactId) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        int result = 0;

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            PreparedStatement st = session.connection().prepareStatement("select count(*) from project where ID_Client_contact=?");
            st.setInt(1, contactId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }

            st.close();
            return result;

        } catch (Exception e) {
            System.err.println("Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
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

    //get all clients
    public int countAllProjectsForClient(int clientId) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        int result = 0;

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            PreparedStatement st = session.connection().prepareStatement("select count(*) from project where ID_Client=?");
            st.setInt(1, clientId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }

            st.close();
            return result;

        } catch (Exception e) {
            System.err.println("Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
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

    //get all clients
    public String getPreviousPmsForClient(int clientId, String currentPm) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        String result = "";

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            PreparedStatement st = session.connection().prepareStatement("select count(*) from project where ID_Client=?");
            st.setInt(1, clientId);
            ResultSet rs2 = st.executeQuery();
            int totalProjects = 0;
            if (rs2.next()) {
                totalProjects = rs2.getInt(1);
            }
            rs2.close();
            st.close();

            st = session.connection().prepareStatement("select distinct pm from project where ID_Client=? ");
            st.setInt(1, clientId);
            //st.setString(2, currentPm);

            PreparedStatement st2 = session.connection().prepareStatement("select count(*) from project where ID_Client=? and pm=?");
            st2.setInt(1, clientId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                if (rs.getString("pm") != null) {
                    st2.setString(2, rs.getString("pm"));
                    rs2 = st2.executeQuery();
                    int pmCount = 0;
                    if (rs2.next()) {
                        pmCount = rs2.getInt(1);
                    }
                    result += rs.getString("pm") + " " + pmCount + " (" + totalProjects + ")";
                    if (!rs.isLast()) {
                        result += "<br> ";
                    }
                }
            }

            st.close();
            st2.close();

            return result;

        } catch (Exception e) {
            System.err.println("Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
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

    //get all clients
    public String getPreviousAesForClient(int clientId, String currentAe) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        String result = "";

        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            PreparedStatement st = session.connection().prepareStatement("select count(*) from project where ID_Client=?");
            st.setInt(1, clientId);
            ResultSet rs2 = st.executeQuery();
            int totalProjects = 0;
            if (rs2.next()) {
                totalProjects = rs2.getInt(1);
            }
            rs2.close();
            st.close();

            st = session.connection().prepareStatement("select distinct ae from project where ID_Client=? and ae<>?");
            st.setInt(1, clientId);
            st.setString(2, currentAe);

            PreparedStatement st2 = session.connection().prepareStatement("select count(*) from project where ID_Client=? and ae=?");
            st2.setInt(1, clientId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                st2.setString(2, rs.getString("ae"));
                rs2 = st2.executeQuery();
                int pmCount = 0;
                if (rs2.next()) {
                    pmCount = rs2.getInt(1);
                }
                result += rs.getString("ae") + " " + pmCount + " (" + totalProjects + ")";
                if (!rs.isLast()) {
                    result += "<br> ";
                }
            }

            st.close();
            st2.close();

            return result;

        } catch (Exception e) {
            System.err.println("Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
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

    //get all clients
    public List getCommunicationListForClient(String clientId) {
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
            query = session.createQuery("select communication from app.extjs.vo.Communication communication where ID_Client= " + clientId + " order by communication.communicationType ");
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

    public static boolean updateClientCommunication(int clientId, String jsonComm) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from Communication where ID_Client=?");
            st.setInt(1, clientId);
            st.executeUpdate();

            if (jsonComm != null && !"".equals(jsonComm)) {
                JSONArray comm = new JSONArray(jsonComm);
                for (int i = 0; i < comm.length(); i++) {
                    JSONObject j = (JSONObject) comm.get(i);
                    Communication pr = new Communication();
                    pr.setID_Client(new Integer(clientId));
                    pr.setDescription(j.getString("description"));
                    pr.setCommunicationType(j.getString("communicationType"));

                    session.save(pr);
                }
            }


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

    public static boolean updateClientLocation(int clientId, String jsonComm) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from client_additional_location where ID_Client=?");
            st.setInt(1, clientId);
            st.executeUpdate();

            if (jsonComm != null && !"".equals(jsonComm)) {
                JSONArray comm = new JSONArray(jsonComm);
                for (int i = 0; i < comm.length(); i++) {
                    JSONObject j = (JSONObject) comm.get(i);
                    ClientLocation pr = new ClientLocation();
                    pr.setID_Client(new Integer(clientId));
                    pr.setDivision(j.getString("division"));
                    pr.setAddress1(j.getString("address1"));
                    pr.setCity(j.getString("city"));
                    pr.setCountry(j.getString("country"));
                    pr.setState(j.getString("state"));
                    pr.setZip(j.getString("zip"));

                    session.save(pr);
                }
            }


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

    public static boolean updateClientServices(int clientId, String jsonComm) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from client_services where ID_Client=?");
            st.setInt(1, clientId);
            st.executeUpdate();

            if (jsonComm != null && !"".equals(jsonComm)) {
                JSONArray comm = new JSONArray(jsonComm);
                System.out.println("comm.length()=" + comm.length());
                for (int i = 0; i < comm.length(); i++) {
                    JSONObject j = (JSONObject) comm.get(i);
                    app.extjs.vo.ClientService pr = new app.extjs.vo.ClientService();
                    pr.setID_Client(new Integer(clientId));
                    pr.setService(j.getString("service"));
                    pr.setLast_edited_id(j.getString("lastEdited"));
                    pr.setRequirements(j.getString("requirement"));

                    session.save(pr);
                }
            }


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

    public static boolean updateClientAudit(int clientId, String jsonComm) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from audits where ID_Client=?");
            st.setInt(1, clientId);
            st.executeUpdate();


            if (jsonComm != null && !"".equals(jsonComm)) {
                JSONArray comm = new JSONArray(jsonComm);
                for (int i = 0; i < comm.length(); i++) {
                    JSONObject j = (JSONObject) comm.get(i);
                    app.extjs.vo.Audit pr = new app.extjs.vo.Audit();
                    pr.setID_Client(new Integer(clientId));
                    pr.setAuditor(j.getString("auditor"));
                    String auditDate = j.getString("audit_date").substring(0, 10);
                    System.out.println("audit_date=" + auditDate);
                    pr.setAudit_date(auditDate.substring(5, 7) + "/" + auditDate.substring(8, 10) + "/" + auditDate.substring(0, 4));

                    pr.setMajor_non(new Integer(j.getString("major_non")).intValue());
                    pr.setMinor_non(new Integer(j.getString("minor_non")).intValue());
                    pr.setReport_url(j.getString("report_url"));
                    pr.setResult(j.getString("result"));

                    session.save(pr);
                }
            }


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
    //get all clients

    public List getRegulatoryListForClient(String clientId) {
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
            //      query =	session.createQuery("select product from app.extjs.vo.Product product where ID_Client= "+ clientId + " order by product.product ");

            query = session.createQuery("select cs from app.extjs.vo.Regulatory cs where ID_Client='" + clientId + "'");
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

    //get all clients
    public List getAuditListForClient(String clientId) {
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
            //      query =	session.createQuery("select product from app.extjs.vo.Product product where ID_Client= "+ clientId + " order by product.product ");

            query = session.createQuery("select cs from app.extjs.vo.Audit cs where ID_Client='" + clientId + "'");
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

    public static boolean updateClientRegulatory(int clientId, String jsonComm) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from regulatory_client where ID_Client=?");
            st.setInt(1, clientId);
            st.executeUpdate();

            if (jsonComm != null && !"".equals(jsonComm)) {
                JSONArray comm = new JSONArray(jsonComm);
                //    System.out.println("comm.length()="+comm.length());
                for (int i = 0; i < comm.length(); i++) {
                    JSONObject j = (JSONObject) comm.get(i);
                    app.extjs.vo.Regulatory pr = new app.extjs.vo.Regulatory();
                    pr.setID_Client(new Integer(clientId));
                    pr.setRegulatory_type(j.getString("regulatory_type"));
                    pr.setEdited_by(j.getString("lastEdited"));
                    pr.setRegulatory_requirement(j.getString("regulatory_requirement"));

                    session.save(pr);
                }
            }


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

    public static boolean updateClientBillingReq(int clientId, String jsonComm) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from billing_requirements where ID_Client=?");
            st.setInt(1, clientId);
            st.executeUpdate();

            if (jsonComm != null && !"".equals(jsonComm)) {
                JSONArray comm = new JSONArray(jsonComm);
                //    System.out.println("comm.length()="+comm.length());
                for (int i = 0; i < comm.length(); i++) {
                    JSONObject j = (JSONObject) comm.get(i);
                    app.extjs.vo.BillingRequirement pr = new app.extjs.vo.BillingRequirement();
                    pr.setID_Client(new Integer(clientId));
                    pr.setEdited_by(j.getString("lastEdited"));
                    pr.setRequirement(j.getString("billingReqs_requirement"));

                    session.save(pr);
                }
            }


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

    //get all clients
    public List getBillingReqListForClient(String clientId) {
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
            //      query =	session.createQuery("select product from app.extjs.vo.Product product where ID_Client= "+ clientId + " order by product.product ");

            query = session.createQuery("select cs from app.extjs.vo.BillingRequirement cs where ID_Client='" + clientId + "'");
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

    //get all clients
    public List getLinguisticStyleListForClient(String clientId) {
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
            //      query =	session.createQuery("select product from app.extjs.vo.Product product where ID_Client= "+ clientId + " order by product.product ");

            query = session.createQuery("select cs from app.extjs.vo.LinStylesheets cs where ID_Client= '" + clientId + "'");
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

    public static boolean updateLinguisticStylesheets(int clientId, String jsonComm) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from linguistic_stylesheets where ID_Client=?");
            st.setInt(1, clientId);
            st.executeUpdate();

            if (jsonComm != null && !"".equals(jsonComm)) {
                JSONArray comm = new JSONArray(jsonComm);
                //    System.out.println("comm.length()="+comm.length());
                for (int i = 0; i < comm.length(); i++) {
                    JSONObject j = (JSONObject) comm.get(i);
                    app.extjs.vo.LinStylesheets pr = new app.extjs.vo.LinStylesheets();
                    pr.setID_Client(new Integer(clientId));
                    pr.setSrcLanguage(j.getString("SrcLanguage"));
                    pr.setTargetLanguage(j.getString("TargetLanguage"));
                    pr.setStylesheet(j.getString("Stylesheet"));
                    pr.setUpdatedBy(j.getString("UpdatedBy"));


                    session.save(pr);
                }
            }


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

    //get all clients
    public List getTechStyleListForClient(String clientId) {
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
            //      query =	session.createQuery("select product from app.extjs.vo.Product product where ID_Client= "+ clientId + " order by product.product ");

            query = session.createQuery("select cs from app.extjs.vo.TechStylesheets cs where ID_Client='" + clientId + "'");
            System.out.println("Query of getTechStyleListForClient****************************" + query);
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

    public static boolean updateTechStylesheets(int clientId, String jsonComm) {

        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from technical_stylesheets where ID_Client=?");
            st.setInt(1, clientId);
            st.executeUpdate();

            if (jsonComm != null && !"".equals(jsonComm)) {
                JSONArray comm = new JSONArray(jsonComm);
                //    System.out.println("comm.length()="+comm.length());
                for (int i = 0; i < comm.length(); i++) {
                    JSONObject j = (JSONObject) comm.get(i);
                    app.extjs.vo.TechStylesheets pr = new app.extjs.vo.TechStylesheets();
                    pr.setID_Client(new Integer(clientId));
                    pr.setSrcLanguage(j.getString("SrcLanguage"));
                    pr.setTargetLanguage(j.getString("TargetLanguage"));
                    pr.setStylesheet(j.getString("Stylesheet"));
                    pr.setUpdatedBy(j.getString("UpdatedBy"));


                    session.save(pr);
                }
            }


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

    //get all clients
    public static Product getSingleProduct(int clientId, String product) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */

        Session session = ConnectionFactory.getInstance().getSession();
        Product result = new Product();


        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            PreparedStatement st = session.connection().prepareStatement("select * from Products where  ID_Client=? and product=?");
            st.setInt(1, clientId);
            st.setString(2, product);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                result.setID_Product(rs.getInt("ID_Product"));
                //result.setProduct(rs.getInt("ID_Product"));
                result.setProduct(rs.getString("product"));
                result.setCategory(rs.getString("category"));
                result.setDescription(rs.getString("description"));
                result.setMedical(rs.getString("medical"));
            }

            st.close();
            return result;

        } catch (Exception e) {
            System.err.println("Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
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



    public static Product getSingleProduct(int productId) {
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */

        Session session = ConnectionFactory.getInstance().getSession();
        Product result = new Product();


        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            PreparedStatement st = session.connection().prepareStatement("select * from Products where  ID_Product=?");
            st.setInt(1, productId);
            // st.setString(2,product);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                result.setID_Product(rs.getInt("ID_Product"));
                //result.setProduct(rs.getInt("ID_Product"));
                result.setProduct(rs.getString("product"));
                result.setCategory(rs.getString("category"));
                result.setDescription(rs.getString("description"));
                result.setMedical(rs.getString("medical"));
            }

            st.close();
            return result;

        } catch (Exception e) {
            System.err.println("Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
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

    //get all clients
    public String getClientProductsHidden(int clientId, String product, boolean isHidden) {
        System.out.println("getClientProductsHidden" + clientId);
        System.out.println("getClientProductsHidden..." + product);
        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        String style = "";
        if (isHidden) {
            style = "style='visibility:hidden;'";
        }

        String result = "<td><select name='product' size='4' multiple='multiple' class='x-btn'  " + style + " onChange=\"if(!categoryHt[this.value])categoryDiv.innerHTML=''; else categoryDiv.innerHTML=categoryHt[this.value];if(!descHt[this.value])descDiv.innerHTML=''; else descDiv.innerHTML=descHt[this.value];\"><option value=''></option>";
        String hashtable = "<script language='javascript'> var categoryHt = [];";
        String hashtable2 = "<script language='javascript'> var descHt = [];";
        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */
            PreparedStatement st = session.connection().prepareStatement("select * from Products where  ID_Client=? order by product asc");
            st.setInt(1, clientId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String selected = "";

                if (rs.getString("product").equals(product)) {
                    selected = " selected ";
                }
                result += "<option " + selected + " value=\"" + rs.getString("product") + "\">" + rs.getString("product") + "  </option>";
                hashtable += "categoryHt[\"" + rs.getString("product") + "\"]=\"" + rs.getString("category") + "\";";
                hashtable2 += "descHt[\"" + rs.getString("product") + "\"]=\"" + rs.getString("description") + "\";";
            }
            result += "</select></td>";
            hashtable += "</script>";
            hashtable2 += "</script>";

            st.close();
            return result + " " + hashtable + " " + hashtable2;

        } catch (Exception e) {
            System.err.println("Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
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

     public List getProjectYearListForClient(Integer clientId) {
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
            //      query =	session.createQuery("select product from app.extjs.vo.Product product where ID_Client= "+ clientId + " order by product.product ");

            query = session.createQuery("SELECT DISTINCT(startdate,'YYYY') FROM project WHERE id_client="+clientId+" AND startDate IS NOT NULL");
            System.out.println("Query of getTechStyleListForClient****************************" + query);
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

