/*
 * ProjectHelper.java
 *
 * Created on April 7, 2008, 8:48 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */
package app.extjs.helpers;

import java.util.*;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import app.db.*;
import app.extjs.vo.DefaultValue;
import app.user.*;
import app.user.UserAbscence;
import app.user.UserService;
import app.extjs.vo.Dropdown;
import app.hrAdmin.TrainingDetail;
import app.standardCode.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.json.*;
import javax.servlet.http.HttpServletRequest;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.type.Type;

/**
 *
 * @author Aleks
 */
public class HrHelper {

    public static final String LINK_STYLE = " style='color:#666688; text-decoration:none;' onmouseover=\"this.style.color='#333';this.style.textDecoration='underline'\" onmouseout=\"this.style.color='#666688';this.style.textDecoration='none'\"";
    public static final String excelAdminCommDropdowns = "('Abscences')";
    public static final String excelAdminClientDropdowns = "('Units', 'Certifications','PO_BBS')";
    // excelAdminQuoteDropdowns
    public static final String excelAdminQuoteDropdowns = "('Application','Units', 'Deliverable','Os','Component','Linguistic','Format', 'Engineering','Other','Reject Reason','Expected Approval Time','Type of Text')";
    public static final String excelAdminQuoteLectraDropdowns = "('Format','Tool Type','Tool Category','Lectra Offer','Solution','Version','Entity')";
//excelAdminTaskDropdowns
    public static final String excelAdminDefaultDropdowns = "('Units')";
    public static final String excelAdminTaskDropdowns = "('Linguistic','Format', 'Engineering','Other')";
    /* +  Feedback from whom: [droplist with following options: administrator, language reviewer, product reviewer, regulatory reviewer]
    +  Feedback is: [droplist with following options: very positive, positive, somewhat positive, neutral, somewhat negative, negative, very negative].
    Each option corresponds to a certain number, e.g. [positive=10, positive=8, somewhat positive=7, somewhat negative=6, negative=5, very negative=4]
     */
    public static final String excelAdminProjectDropdowns = "('Delivery Method','Feedback - From Whom','Feedback - Satisfaction','Feedback - About','Milestone','Standard','Major/Minor')";
    public static final String excelAdminTeamDropdowns = "('Application','Evaluation Area')";
    public static final String excelAdminUserDropdowns = "('Status')";
//    public static final String excelAdminTrainingDropdowns = "('Topics')";
    public static final String excelAdminQMSDropdowns = "('Standard')";

    public static final String excelAdminTrainingDropdowns() {
        List departmentList = UserService.getInstance().getDepartmentList();
        String department = "(";
        for (int i = 0; i < departmentList.size(); i++) {
            Department dept = (Department) departmentList.get(i);
            department += "'" + dept.getDepartment() + "'";
            if (i < departmentList.size() - 1) {
                department += ", ";
            }
        }
        department += ")";
        return department;
    }

    public static final String excelAdminDepartmentDropdowns() {
//        ['Topic', 'Topic']
        List departmentList = UserService.getInstance().getDepartmentList();
        String department = "";
        for (int i = 0; i < departmentList.size(); i++) {
            Department dept = (Department) departmentList.get(i);
            department += "['" + dept.getDepartment() + "','" + dept.getDepartment() + "']";
            if (i < departmentList.size() - 1) {
                department += ", ";
            }
        }
        department += "";
        return department;
    }

    public static final String excelAdminTrainingUser() {
//        ['Topic', 'Topic']
        StringCleaner sc = new StringCleaner();

        List userList = UserService.getInstance().getUserListCurrentDropDown();
        String userStr = "";
        for (int i = 0; i < userList.size(); i++) {
            User user = (User) userList.get(i);
            userStr += "['" + user.getFirstName() + " " + user.getLastName() + "','"
                    + user.getFirstName() + " " + user.getLastName() + "']";
            if (i < userList.size() - 1) {
                userStr += ", ";
            }
        }
        userStr += "";
        return sc.convertToAscii(userStr, false);
    }

    public static final String excelAdminPositionDropdowns() {
//        ['Topic', 'Topic']
        List posList = UserService.getInstance().getPositionList();
        String pos = "";
        for (int i = 0; i < posList.size(); i++) {
            Position1 position1 = (Position1) posList.get(i);
            pos += "['" + position1.getPosition() + "','" + position1.getPosition() + "']";
            if (i < posList.size() - 1) {
                pos += ", ";
            }
        }
        pos += "";
        return pos;
    }

    //get all active projects
    public static List getAllEmployees(User user) {
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
             *///and (project.pm='"+pmName+"' or project.Company.Sales_rep='"+pmName+"') order by project.number
            query = session.createQuery("select user from app.user.User user order by user.lastName, user.firstName");

            List temp = query.list();

            List results = new ArrayList();
            for (int i = 0; i < temp.size(); i++) {
                User u = (User) temp.get(i);
                JSONObject jo = new JSONObject();
                if (u.getPosition() != null) {
                    jo.put("Position", u.getPosition().getPosition());
                } else {
                    jo.put("Position", "");
                }
                if (u.getSkypeId() != null) {
                    jo.put("Skype", u.getSkypeId());
                } else {
                    jo.put("Skype", "");
                }
                if (u.getHomeCell() != null) {
                    jo.put("Cell_Number", u.getHomeCell());
                } else {
                    jo.put("Cell_Number", "");
                }
                if (u.getDepartment() != null) {
                    jo.put("Department", u.getDepartment().getDepartment());
                } else {
                    jo.put("Department", "");
                }
                jo.put("Work_Email", u.getWorkEmail1());
                jo.put("Phone_Number", u.getWorkPhone());

                jo.put("Name", "<a " + LINK_STYLE + " href=\"javascript:parent.openSingleEmployeeWindow('" + HrHelper.jsSafe(u.getFirstName() + " " + u.getLastName()) + "','" + u.getUserId() + "')\">" + u.getFirstName() + " " + u.getLastName() + "</a>");

                if (!"true".equals(u.getCurrentEmployee())) {
                    jo.put("Location", "FORMER EMPLOYEES");
                } else if (u.getLocation() != null) {
                    jo.put("Location", u.getLocation().getLocation().toUpperCase());
                } else {
                    jo.put("Location", "UNKNOWN");
                }

                if ("true".equals(u.getCurrentEmployee())) {
                    jo.put("Action", "<a " + LINK_STYLE + " href=\"javascript:setActiveEmployee('" + u.getUserId() + "','false')\">Move to Former</a>");
                } else {
                    jo.put("Action", "<a  " + LINK_STYLE + " href=\"javascript:setActiveEmployee('" + u.getUserId() + "','true')\">Move to Current</a>");
                }

                if ("true".equals(u.getCurrentEmployee()) /*|| !user.getAdminUser().equals("false")*/) {
                    results.add(jo);
                }

            }

            return results;
        } catch (Exception e) {
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
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

    public static List getAllEmployeesFormer(User user) {
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
             *///and (project.pm='"+pmName+"' or project.Company.Sales_rep='"+pmName+"') order by project.number
            query = session.createQuery("select user from app.user.User user order by user.lastName, user.firstName");

            List temp = query.list();

            List results = new ArrayList();
            for (int i = 0; i < temp.size(); i++) {
                User u = (User) temp.get(i);
                JSONObject jo = new JSONObject();
                if (u.getPosition() != null) {
                    jo.put("Position", u.getPosition().getPosition());
                } else {
                    jo.put("Position", "");
                }
                if (u.getDepartment() != null) {
                    jo.put("Department", u.getDepartment().getDepartment());
                } else {
                    jo.put("Department", "");
                }
                jo.put("Work_Email", u.getWorkEmail1());
                jo.put("Phone_Number", u.getWorkPhone());

//                jo.put("Name", "<a " + LINK_STYLE + " href=\"javascript:parent.openSingleEmployeeWindow('" + HrHelper.jsSafe(u.getFirstName() + " " + u.getLastName()) + "','" + u.getUserId() + "')\">" + u.getFirstName() + " " + u.getLastName() + "</a>");
                jo.put("Name", "<a " + LINK_STYLE + " href=\"javascript:parent.openSingleEmployeeWindow('" + HrHelper.jsSafe(u.getFirstName() + " " + u.getLastName()) + "','" + u.getUserId() + "')\">" + u.getFirstName() + " " + u.getLastName() + "</a>");

                if (!"true".equals(u.getCurrentEmployee())) {
                    jo.put("Location", "FORMER EMPLOYEES");
                } else if (u.getLocation() != null) {
                    jo.put("Location", u.getLocation().getLocation().toUpperCase());
                } else {
                    jo.put("Location", "UNKNOWN");
                }

                if ("true".equals(u.getCurrentEmployee())) {
                    jo.put("Action", "<a " + LINK_STYLE + " href=\"javascript:setActiveEmployee('" + u.getUserId() + "','false')\">Move to Former</a>");
                } else {
                    jo.put("Action", "<a  " + LINK_STYLE + " href=\"javascript:setActiveEmployee('" + u.getUserId() + "','true')\">Move to Current</a>");
                }

                if ("false".equals(u.getCurrentEmployee()) && (u.getuserType() == null || u.getuserType().equalsIgnoreCase("admin"))) {
                    results.add(jo);
                }

            }

            return results;
        } catch (Exception e) {
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
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

    public static String jsSafe(String myString) {
        return myString.replaceAll("'", "").replaceAll("\n", "<br>").replaceAll("\r", "<br>");

    }

    public static String getExcelCalendarJS() {

        /*
         * Use the ConnectionFactory to retrieve an open
         * Hibernate Session.
         *
         */
        Session session = ConnectionFactory.getInstance().getSession();
        Query query;
        List allAbscences = null;
        try {
            /*
             * Build HQL (Hibernate Query Language) query to retrieve a list
             * of all the items currently stored by Hibernate.
             */

            query = session.createQuery("select userabscence from app.user.UserAbscence userabscence");
            allAbscences = query.list();
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
        String results = "var dates = [";
        HashMap users = new HashMap();
        HashMap locations = new HashMap();
        HashMap absc = new HashMap();
        String subResults = "var htDates = new Object(); \n";
        for (Iterator iter = allAbscences.iterator(); iter.hasNext();) {
            UserAbscence uAbs = (UserAbscence) iter.next();
            String username = "";
            if (uAbs.getID_User() != null) {
                if (users.get(uAbs.getID_User()) == null) {
                    users.put(uAbs.getID_User(), UserService.getInstance().getSingleUser(uAbs.getID_User()));
                }
                User user = (User) users.get(uAbs.getID_User());
                username = user.getFirstName() + " " + user.getLastName();
            } else {
                if (locations.get(uAbs.getID_Location()) == null) {
                    locations.put(uAbs.getID_Location(), UserService.getInstance().getSingleLocationByLocId(uAbs.getID_Location()));
                }
                Location myLoc = (Location) locations.get(uAbs.getID_Location());
                try {
                    username = myLoc.getLocation();
                } catch (Exception e) {
                }
            }
            if (absc.get((1900 + uAbs.getAbscence_date().getYear()) + "," + uAbs.getAbscence_date().getMonth() + "," + uAbs.getAbscence_date().getDate()) == null) {
                absc.put((1900 + uAbs.getAbscence_date().getYear()) + "," + uAbs.getAbscence_date().getMonth() + "," + uAbs.getAbscence_date().getDate(), username + " - " + uAbs.getReason().toLowerCase() + " - " + uAbs.getNotes());
            } else {
                String alreadyHere = (String) absc.get((1900 + uAbs.getAbscence_date().getYear()) + "," + uAbs.getAbscence_date().getMonth() + "," + uAbs.getAbscence_date().getDate());
                alreadyHere += "<br>" + username + " - " + uAbs.getReason().toLowerCase() + " - " + uAbs.getNotes();
                absc.put((1900 + uAbs.getAbscence_date().getYear()) + "," + uAbs.getAbscence_date().getMonth() + "," + uAbs.getAbscence_date().getDate(), alreadyHere);

            }

        }
        for (Iterator iter = absc.keySet().iterator(); iter.hasNext();) {

            String date = (String) iter.next();
            // //System.out.println((String)absc.get(reason));

            results += "{date: new Date(" + date + "),"
                    + //fixed date marked only on 2008/01/02
                    "text: \"" + (String) absc.get(date) + "\","
                    + "cls: 'x-datepickerplus-eventdates' }";

            subResults += "htDates[\"" + date + "\"] = new myDate(new Date(" + date + "),\"" + (String) absc.get(date) + "\");";

            if (iter.hasNext()) {
                results += ",";
            }

        }
        results += "];\n";
        results += subResults;
        return results;
    }

    //update an existing Client in database
    public static void saveDropdowns(HttpServletRequest request) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            String origin = request.getParameter("ORIGIN");
            String sqlAddition = "";
            if ("COMM".equals(origin)) {
                sqlAddition = excelAdminCommDropdowns + " and tab=''";
            } else if ("CLIENT".equals(origin)) {
                sqlAddition = excelAdminClientDropdowns + " and tab=''";
            } else if ("PROJECT".equals(origin)) {
                sqlAddition = excelAdminProjectDropdowns + " and tab=''";
            } else if ("TEAM".equals(origin)) {
                sqlAddition = excelAdminTeamDropdowns + " and tab=''";
            } else if ("QUOTE".equals(origin)) {
                sqlAddition = excelAdminQuoteDropdowns + " and tab=''";
            } else if ("TASK".equals(origin)) {
                sqlAddition = excelAdminTaskDropdowns + " and tab=''";
            } else if ("USER".equals(origin)) {
                sqlAddition = excelAdminUserDropdowns + " and tab=''";
            } else if ("QMS".equals(origin)) {
                sqlAddition = excelAdminQMSDropdowns + " and tab=''";
            } else if ("TRAINING".equals(origin)) {
                sqlAddition = excelAdminTrainingDropdowns() + " and tab='TRAINING'";
            }
            PreparedStatement st = session.connection().prepareStatement("delete from dropdown where dropdowntype in " + sqlAddition);
            st.executeUpdate();
            st.close();
            String jsonProducts = request.getParameter("dropdownJSON");
            ////System.out.println("jsonProducts="+jsonProducts);

            //First delete all products, and then re-insert it
            if (jsonProducts != null && !"".equals(jsonProducts)) {
                JSONArray products = new JSONArray(jsonProducts);
                for (int i = 0; i < products.length(); i++) {
                    JSONObject j = (JSONObject) products.get(i);
                    Dropdown pr = new Dropdown();
                    pr.setDropdownType(j.getString("dropdownType"));
                    pr.setDropdownValue(j.getString("dropdownValue"));
                    pr.setPriority(j.getString("priority"));

                    if ("TRAINING".equals(origin)) {
                        pr.setTab(origin);
                    } else {
                        pr.setTab("");
                    }

                    // HrHelper.saveDropdown(pr);
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

    }

    //get all clients
    public static String getAdminDropdownValuesClient(String dropdownValues) {
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
            query = session.createQuery("select dropdown from app.extjs.vo.Dropdown dropdown where dropdown.dropdownType in " + dropdownValues + " and dropdown.tab = '' order by  dropdown.dropdownType, dropdown.dropdownValue ");
            List dropdowns = query.list();

            //  //System.out.println("query"+excelAdminQuoteDropdowns+"                  "+query);
            String dropdownJSArray = "";
            for (int i = 0; i < dropdowns.size(); i++) {
                Dropdown pr = (Dropdown) dropdowns.get(i);

                dropdownJSArray += "[\"" + pr.getID_Dropdown() + "\",";
                dropdownJSArray += "\"" + HrHelper.jsSafe(pr.getDropdownType()) + "\",";
                dropdownJSArray += "\"" + HrHelper.jsSafe(pr.getDropdownValue()) + "\",";
                dropdownJSArray += "\"" + HrHelper.jsSafe(StandardCode.getInstance().noNull(pr.getPriority())) + "\"]";
                if (i != dropdowns.size() - 1) {
                    dropdownJSArray += ",";
                }

            }

            return dropdownJSArray;

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
    public static String getAdminDefaultValues(String dropdownType) {
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
            query = session.createQuery("select dropdown from app.extjs.vo.Dropdown dropdown where dropdown.dropdownType = '" + dropdownType + "' and dropdown.priority = 'Default'");
            List dropdowns = query.list();

            //  //System.out.println("query"+excelAdminQuoteDropdowns+"                  "+query);
            String res = "";
            for (int i = 0; i < dropdowns.size(); i++) {

                Dropdown pr = (Dropdown) dropdowns.get(i);
                res = HrHelper.jsSafe(pr.getDropdownValue());

            }

            return res;

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
    public static String getAdminDropdownValuesTraining(String dropdownValues, String tab) {
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
            query = session.createQuery("select dropdown from app.extjs.vo.Dropdown dropdown where dropdown.dropdownType in " + dropdownValues + " and dropdown.tab = '" + tab + "' order by  dropdown.dropdownType, dropdown.dropdownValue ");
            List dropdowns = query.list();

            //  //System.out.println("query"+excelAdminQuoteDropdowns+"                  "+query);
            String dropdownJSArray = "";
            for (int i = 0; i < dropdowns.size(); i++) {
                Dropdown pr = (Dropdown) dropdowns.get(i);

                dropdownJSArray += "[\"" + pr.getID_Dropdown() + "\",";
                dropdownJSArray += "\"" + HrHelper.jsSafe(pr.getDropdownType()) + "\",";
                dropdownJSArray += "\"" + HrHelper.jsSafe(pr.getDropdownValue()) + "\",";
                dropdownJSArray += "\"" + HrHelper.jsSafe(StandardCode.getInstance().noNull(pr.getPriority())) + "\"]";
                if (i != dropdowns.size() - 1) {
                    dropdownJSArray += ",";
                }

            }

            return dropdownJSArray;

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
    public static List getAdminTopicList(String dropdownValues, String tab) {
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
            query = session.createQuery("select dropdown from app.extjs.vo.Dropdown dropdown where dropdown.dropdownType = '" + dropdownValues + "' and dropdown.tab = '" + tab + "' order by  dropdown.dropdownType, dropdown.dropdownValue ");
            List dropdowns = query.list();

            return dropdowns;

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

    public static List getAdminTopicList(String tab) {
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
            query = session.createQuery("select dropdown from app.extjs.vo.Dropdown dropdown where dropdown.tab = '" + tab + "' order by  dropdown.dropdownType, dropdown.dropdownValue ");
            List dropdowns = query.list();

            return dropdowns;

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
    public static String getDefaultValueList() {
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
            query = session.createQuery("select DefaultValue from app.extjs.vo.DefaultValue DefaultValue ");
            List dropdowns = query.list();

            //  //System.out.println("query"+excelAdminQuoteDropdowns+"                  "+query);
            String dropdownJSArray = "";
            for (int i = 0; i < dropdowns.size(); i++) {
                DefaultValue pr = (DefaultValue) dropdowns.get(i);

                dropdownJSArray += "[\"" + pr.getId() + "\",";
                dropdownJSArray += "\"" + HrHelper.jsSafe(pr.getDefaultName()) + "\",";
                dropdownJSArray += "\"" + HrHelper.jsSafe(StandardCode.getInstance().noNull(pr.getDefaultValue())) + "\"]";
                if (i != dropdowns.size() - 1) {
                    dropdownJSArray += ",";
                }

            }

            return dropdownJSArray;

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

    public static void saveVariables(HttpServletRequest request) {
        Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            String origin = request.getParameter("ORIGIN");
            String sqlAddition = "";
            if ("COMM".equals(origin)) {
                sqlAddition = excelAdminCommDropdowns;
            } else if ("CLIENT".equals(origin)) {
                sqlAddition = excelAdminClientDropdowns;
            } else if ("PROJECT".equals(origin)) {
                sqlAddition = excelAdminProjectDropdowns;
            } else if ("TEAM".equals(origin)) {
                sqlAddition = excelAdminTeamDropdowns;
            }
            PreparedStatement st = session.connection().prepareStatement("delete from dropdown where dropdowntype in " + sqlAddition);
            st.executeUpdate();
            st.close();
            String jsonProducts = request.getParameter("dropdownJSON");
            ////System.out.println("jsonProducts="+jsonProducts);

            //First delete all products, and then re-insert it
            if (jsonProducts != null && !"".equals(jsonProducts)) {
                JSONArray products = new JSONArray(jsonProducts);
                for (int i = 0; i < products.length(); i++) {
                    JSONObject j = (JSONObject) products.get(i);
                    Dropdown pr = new Dropdown();
                    pr.setDropdownType(j.getString("dropdownType"));
                    pr.setDropdownValue(j.getString("dropdownValue"));
                    // HrHelper.saveDropdown(pr);
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

    }

//    public static String getosvalue() {
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
//            query = session.createQuery("select os from app.quote.Os os  order by os.os ");
//            List dropdowns = query.list();
//
//
//            String osJSArray = "";
//            for (int i = 0; i < dropdowns.size(); i++) {
//                Os pr = (Os) dropdowns.get(i);
//
//                osJSArray += "[\"" + pr.getOs_id() + "\",";
//                //  osJSArray+= "\""+HrHelper.jsSafe(pr.getOs())+"\",";
//                osJSArray += "\"" + HrHelper.jsSafe(pr.getOs()) + "\"]";
//                if (i != dropdowns.size() - 1) {
//                    osJSArray += ",";
//                }
//
//            }
//            //System.out.println("osJSArray                          ---" + osJSArray);
//            return osJSArray;
//
//
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
//
//
//    }
    public static String getDropdownValuesHtml(String dropdownName, String selectedValue) {

        Session session = ConnectionFactory.getInstance().getSession();

        String result = "";

        try {

            PreparedStatement st = session.connection().prepareStatement("select * from dropdown where dropdownType=? order by priority DESC , dropdownValue asc");
            st.setString(1, dropdownName);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String selected = "";
                if (rs.getString("dropdownValue").equals(selectedValue)) {
                    selected = " selected ";
                }
                result += "<option " + selected + " value=\"" + rs.getString("dropdownValue") + "\">" + rs.getString("dropdownValue") + "</option>";

            }
            st.close();
            return result;
        } catch (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
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

    public static String getDropdownValuesExtjs(String dropdownName, String sortOrder) {

        Session session = ConnectionFactory.getInstance().getSession();

        String result = "";

        try {

            PreparedStatement st = session.connection().prepareStatement("select * from dropdown where dropdownType=? order by dropdownValue " + sortOrder);
            st.setString(1, dropdownName);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {

                result += "[\"" + rs.getString("dropdownValue") + "\",";
                result += "\"" + rs.getString("dropdownValue") + "\"]";
                if (!rs.isLast()) {
                    result += ",";
                }

            }
            st.close();
            return result;
        } catch (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
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

    public static ArrayList getDropdownValuesList(String dropdownName) {

        Session session = ConnectionFactory.getInstance().getSession();

        ArrayList result = new ArrayList();

        try {

            PreparedStatement st = session.connection().prepareStatement("select * from dropdown where dropdownType=? order by dropdownValue asc");
            st.setString(1, dropdownName);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                result.add(rs.getString("dropdownValue"));
            }
            st.close();
            return result;
        } catch (Exception e) {

            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
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

    public static TrainingDetail updateTrainingDetail(TrainingDetail c) {
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

    public static Training getTrainingDetail(Integer id) {

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
            results = session.find("from TrainingDetail as training where training.ID_Training=?",
                    new Object[]{id},
                    new Type[]{Hibernate.INTEGER});
            if (results.isEmpty()) {
                return null;
            } else {
                Training c = (Training) results.get(0);
                return c;
            }
        } catch (ObjectNotFoundException onfe) {
            return null;
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
