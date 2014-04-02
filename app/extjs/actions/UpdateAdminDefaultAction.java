/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.extjs.actions;

import app.db.ConnectionFactory;
import org.apache.struts.action.Action;
import app.extjs.helpers.HrHelper;
import app.extjs.vo.DefaultValue;
import app.extjs.vo.Dropdown;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import app.security.*;
import java.sql.PreparedStatement;
import net.sf.hibernate.HibernateException;

import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Niteshwar
 */
public class UpdateAdminDefaultAction extends Action{

    // ----------------------------------------------------- Instance Variables


    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log =
            LogFactory.getLog("org.apache.struts.webapp.Example");


    // --------------------------------------------------------- Public Methods


    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @exception Exception if business logic throws an exception
     */
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        // Extract attributes we will need
        MessageResources messages = getResources(request);

        // save errors
        ActionMessages errors = new ActionMessages();

        //START check for login (security)
        if(!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)

         Session session = ConnectionFactory.getInstance().getSession();
        boolean tf = true;

        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            PreparedStatement st = session.connection().prepareStatement("delete from default_value");
            st.executeUpdate();
            st.close();
            String jsonProducts = request.getParameter("dropdownJSON");
            //System.out.println("jsonProducts="+jsonProducts);

            //First delete all products, and then re-insert it
            if (jsonProducts != null && !"".equals(jsonProducts)) {
                JSONArray products = new JSONArray(jsonProducts);
                for (int i = 0; i < products.length(); i++) {
                    JSONObject j = (JSONObject) products.get(i);
                    DefaultValue pr = new DefaultValue();
                    pr.setDefaultName(j.getString("dropdownType"));
                    pr.setDefaultValue(j.getString("dropdownValue"));
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


        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
        //return null;
    }

}
