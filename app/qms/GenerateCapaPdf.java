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
import java.io.*;
import com.lowagie.text.pdf.*;
import app.security.*;
import app.user.User;
import app.user.UserService;
import com.lowagie.text.Image;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author Niteshwar
 */
public class GenerateCapaPdf extends Action {

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

        //START process pdf
        try {

            PdfReader reader = new PdfReader("C:/templates/F3.002-CAPA.pdf"); //the template
            //save the pdf in memory
            ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();

            //the filled-in pdf
            PdfStamper stamp = new PdfStamper(reader, pdfStream);

            //stamp.setEncryption(true, "pass", "pass", PdfWriter.AllowCopy | PdfWriter.AllowPrinting);
            AcroFields form1 = stamp.getAcroFields();
            /* HashMap hm = form1.getFields();
            }*/
            String number = request.getParameter("CapaNumber");
            Capa capa = QMSService.getInstance().getSingleCapa(number);
            CapaId capaId = QMSService.getInstance().getSingleCapaId(number);
            DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
            form1.setField("ncr1", number);
            try {
                form1.setField("date", "" + df.format(capaId.getRca_t_date()));
            } catch (Exception e) {
            }

            try {


//            form1.setField("date", "" + capaId.getRca_t_date());
            form1.setField("owner", capaId.getOwner());
            form1.setField("2-1", capaId.getRca());
            form1.setField("imact", capaId.getImact());
            form1.setField("approval1", capaId.getActionplan_approve());
            form1.setField("approval2", capaId.getActionplan_approve2());
            form1.setField("approval3", capaId.getVerify_approve());
            form1.setField("approval4", capaId.getVerify_approve2());
            form1.setField("2-2", capaId.getActionplan());
            form1.setField("2-3", capaId.getEffectiveplan());
            form1.setField("2-4", capaId.getVerify());
            form1.setField("ncrnr", number);
            form1.setField("implemetned", capaId.getActionimp_status());

//            form1.setField("minor", "1");
//            form1.setField("major", "yes");
            if (capaId.getNcyesno().equalsIgnoreCase("no")) {
                form1.setField("nonc", "Yes");
            } else {
                if (capaId.getNcminormajor().equalsIgnoreCase("minor")) {
                    form1.setField("minor", "Yes");
                } else if (capaId.getNcminormajor().equalsIgnoreCase("major")) {
                    form1.setField("major", "Yes");
                }
            }
//             form1.setField("nonc","Yes");
            //form1.setField("nonc",number);
            form1.setField("nonc_explanation", capaId.getNc());
            try {
                form1.setField("recd1", "" + df.format(capaId.getAdmin_rec_date()));
            } catch (Exception e) {
            }
//            form1.setField("recd1", "" + capaId.getAdmin_rec_date());
            form1.setField("person1", capaId.getAdmin_rec_person());
            try {
                 form1.setField("recd2", "" + df.format(capaId.getAdmin_own_date()));
            } catch (Exception e) {
            }
//            form1.setField("recd2", "" + capaId.getAdmin_own_date());
            form1.setField("person2", capaId.getAdmin_own_person());
            try {
                form1.setField("recd3", "" + df.format(capaId.getAdmin_disp_date()));
            } catch (Exception e) {
            }
//            form1.setField("recd3", "" + capaId.getAdmin_disp_date());
            form1.setField("person3", capaId.getAdmin_disp_person());
            } catch (Exception e) {
            }
             if(u.getSignature() != null && u.getSignature().length() > 0) {
                    PdfContentByte over;
                    Image img = Image.getInstance("C:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/logo/images/" + u.getSignature());
                    img.setAbsolutePosition(200, 400);
                    over = stamp.getOverContent(1);
                    over.addImage(img, 190, 0,0, 60, 391,63);
                    //over.addImage(img);
                }


            stamp.close();
            response.setHeader("Content-disposition", "attachment; filename=CAPA" + number + ".pdf");

            OutputStream os = response.getOutputStream();
            pdfStream.writeTo(os);
            os.flush();
        } catch (Exception e) {
            System.err.println("PDF Exception:" + e.toString());
            throw new RuntimeException(e);
        }
        //END process pdf

        // Forward control to the specified success URI
        return (null);
    }
}
