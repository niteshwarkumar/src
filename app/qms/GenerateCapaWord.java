/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.qms;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import java.text.*;
import java.io.*;
import app.user.*;
import app.security.*;
import app.standardCode.*;
/**
 *
 * @author Niteshwar
 */
public class GenerateCapaWord extends Action {

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
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)
User  u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));

            String number = request.getParameter("CapaNumber");
            Capa capa = QMSService.getInstance().getSingleCapa(number);
            CapaId capaId = QMSService.getInstance().getSingleCapaId(number);
            DateFormat df = new SimpleDateFormat("MM-dd-yyyy");

        byte[] template = getBytesFromFile(new java.io.File("C:/templates2/NC-CAPA InternalF2004.rtf"));

        String content = new String(template);
//         content = content.replaceAll("INSERT_DATE_INSERT", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
//        content = content.replaceAll("INSERT_COMPANYLOGO_INSERT", "");

        content = content.replaceAll("INSERT_NC_NO_INSERT", capaId.getCapa_number());
        content = content.replaceAll("INSERT_NC_CLASSIFICATION_INSERT", capaId.getNcyesno()+" - "+capaId.getNcminormajor());

        content = content.replaceAll("INSERT_OWNER1_INSERT", capaId.getOwner());
        content = content.replaceAll("INSERT_OWNER2_INSERT", capaId.getOwner2());
        content = content.replaceAll("INSERT_OWNER3_INSERT", capaId.getOwner3());

        content = content.replaceAll("INSERT_RCA_INSERT", capaId.getRca());
        content = content.replaceAll("INSERT_IMACT_INSERT", capaId.getImact());
        
        content = content.replaceAll("INSERT_ACTION_IMPLEMENTED_INSERT", capaId.getActionimp());
        content = content.replaceAll("INSERT_EFFECTIVENESS_IMPLEMENTED_INSERT", capaId.getEffectiveplan());
        content = content.replaceAll("INSERT_VERIFICATION_INSERT", capaId.getVerify());
        content = content.replaceAll("INSERT_COMMENT_INSERT", capaId.getComments());
        content = content.replaceAll("INSERT_ACTION_PLAN_INSERT", capaId.getActionplan());
        content = content.replaceAll("INSERT_DESCRIPTION_INSERT", capaId.getCapaid_description());

        try {
        content = content.replaceAll("INSERT_DISPOSITION_DATE_INSERT", DateFormat.getDateInstance(DateFormat.SHORT).format(capaId.getAdmin_disp_date()));
        } catch (Exception e) {
            content = content.replaceAll("INSERT_DISPOSITION_DATE_INSERT", "");
        }
          try {
        content = content.replaceAll("INSERT_OWNER_DATE_INSERT", DateFormat.getDateInstance(DateFormat.SHORT).format(capaId.getAdmin_own_date()));
        } catch (Exception e) { content = content.replaceAll("INSERT_OWNER_DATE_INSERT", "");
        }
          try {
        content = content.replaceAll("INSERT_RECIPT_DATE_INSERT", DateFormat.getDateInstance(DateFormat.SHORT).format(capaId.getAdmin_rec_date()));
        } catch (Exception e) { content = content.replaceAll("INSERT_RECIPT_DATE_INSERT", "");
        }
          try {
        content = content.replaceAll("INSERT_VER_ACTUAL_INSERT"," "+ DateFormat.getDateInstance(DateFormat.SHORT).format(capaId.getVerify_a_date()));
        } catch (Exception e) { content = content.replaceAll("INSERT_VER_ACTUAL_INSERT", "");
        }
          try {
        content = content.replaceAll("INSERT_VER_TARGET_INSERT"," "+ DateFormat.getDateInstance(DateFormat.SHORT).format(capaId.getVerify_t_date()));
        } catch (Exception e) { content = content.replaceAll("INSERT_VER_TARGET_INSERT", "");
        }
          try {
        content = content.replaceAll("INSERT_ACTION_ACTUAL_INSERT"," "+ DateFormat.getDateInstance(DateFormat.SHORT).format(capaId.getActionimp_a_date()));
        } catch (Exception e) { content = content.replaceAll("INSERT_ACTION_ACTUAL_INSERT", "");
        }
          try {
        content = content.replaceAll("INSERT_ACTION_TARGET_INSERT"," "+ DateFormat.getDateInstance(DateFormat.SHORT).format(capaId.getActionimp_t_date()));
        } catch (Exception e) { content = content.replaceAll("INSERT_ACTION_TARGET_INSERT", "");
        }
          try {
        content = content.replaceAll("INSERT_RCA_ACTUAL_INSERT"," "+ DateFormat.getDateInstance(DateFormat.SHORT).format(capaId.getRca_a_date()));
        } catch (Exception e) { content = content.replaceAll("INSERT_RCA_ACTUAL_INSERT", "");
        }
          try {
        content = content.replaceAll("INSERT_RCA_TARGET_INSERT"," "+ DateFormat.getDateInstance(DateFormat.SHORT).format(capaId.getRca_t_date()));
        } catch (Exception e) { content = content.replaceAll("INSERT_RCA_TARGET_INSERT", "");
        }
          
            try {
        content = content.replaceAll("INSERT_IMACT_TGT_INSERT"," "+ DateFormat.getDateInstance(DateFormat.SHORT).format(capaId.getImact_t_date()));
        } catch (Exception e) { content = content.replaceAll("INSERT_IMACT_TGT_INSERT", "");
        }
          try {
        content = content.replaceAll("INSERT_IMACT_ACT_INSERT"," "+ DateFormat.getDateInstance(DateFormat.SHORT).format(capaId.getImact_a_date()));
        } catch (Exception e) { content = content.replaceAll("INSERT_IMACT_ACT_INSERT", "");
        }

        content = content.replaceAll("INSERT_EMPLOYEE_INSERT", capa.getEmployee());
        content = content.replaceAll("INSERT_LOCATION_INSERT", capa.getLocation());
        content = content.replaceAll("INSERT_REPORTEDBY_INSERT",capa.getReportedby() );
        content = content.replaceAll("INSERT_FROM_INSERT", capa.getFromc());
        content = content.replaceAll("INSERT_ISSUEID_INSERT", capa.getIssueId());
        content = content.replaceAll("INSERT_SOURCE_INSERT", capa.getSource());
        content = content.replaceAll("INSERT_STATUS_INSERT", capa.getStatus());
        content = content.replaceAll("INSERT_NCCLASS_INSERT", "");
        content = content.replaceAll("INSERT_ORIGINATOR_INSERT", "");
        content = content.replaceAll("INSERT_PROJECTNR_INSERT", capa.getNumber());
        content = content.replaceAll("INSERT_LANGUAGES_INSERT", "");

        content = content.replaceAll("INSERT_ACTION_APPROVAL1_INSERT", capaId.getActionplan_approve());
        content = content.replaceAll("INSERT_ACTION_APPROVAL2_INSERT", capaId.getActionplan_approve2());
        
        content = content.replaceAll("INSERT_RECIPT_PERSON_INSERT", capaId.getAdmin_rec_person());
        content = content.replaceAll("INSERT_OWNER_PERSON_INSERT", capaId.getAdmin_own_person());
        content = content.replaceAll("INSERT_DISPOSITION_PERSON_INSERT", capaId.getAdmin_disp_person());

          String filename = capaId.getCapa_number()+"-NC-CAPA.doc";

        content = content.replaceAll("NC-CAPA Internal.rtf", StandardCode.getInstance().noNull(filename));
        //System.out.println("hereeeeee6");
        //write to client (web browser)
        response.setHeader("Content-Type", "Application/msword");
        response.setHeader("Content-disposition", "attachment; filename=" + filename);
        //response.setHeader("Content-disposition", "attachment; filename=" + q.getNumber() + ".doc");
        OutputStream os = response.getOutputStream();
        os.write(content.getBytes());
        os.flush();
        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }

    // Returns the contents of the file in a byte array.
    private static byte[] getBytesFromFile(java.io.File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }
}
