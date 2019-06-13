/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.resource;

/**
 *
 * @author niteshwar
 */
import app.security.SecurityService;
import app.standardCode.StandardCode;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

public class ExportResource extends Action {

    // ----------------------------------------------------- Instance Variables
    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log
            = LogFactory.getLog("org.apache.struts.webapp.Example");
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

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
        List results = (List) request.getSession(false).getAttribute("finalResourceResults");
//        List results = (List) request.getAttribute("vendorres");
        String resource = "";
        resource += "FIRST NAME";
            resource += COMMA_DELIMITER;
            resource += "LAST NAME";
            resource += COMMA_DELIMITER;
            resource += "COMPANY";
            resource += COMMA_DELIMITER;
            resource += "EMAIL ADDRESS 1";
            resource += COMMA_DELIMITER;
            resource += "EMAIL ADDRESS 2";
            resource += COMMA_DELIMITER;
            resource += "CONTACT EMAIL ADDRESS";
            resource += COMMA_DELIMITER;
            resource += "PROJECT COUNT";
            resource += COMMA_DELIMITER;
            resource += "COUNTRY";
//            resource += COMMA_DELIMITER;resource += r.get
//            resource += COMMA_DELIMITER;resource += r.get
//            resource += COMMA_DELIMITER;resource += r.get
            resource += NEW_LINE_SEPARATOR;
        for (int i = 0; i < results.size(); i++) {

            Resource r = (Resource) results.get(i);
            Set contacts = r.getResourceContacts();
            String resourceEmail = "";
            if (contacts != null & contacts.size() > 0) {

                for (Iterator iter = contacts.iterator(); iter.hasNext();) {
                    ResourceContact cc = (ResourceContact) iter.next();
                    if (!resourceEmail.trim().endsWith(";")&&!resourceEmail.isEmpty()) {
                        resourceEmail += "; ";
                    }
                    resourceEmail += StandardCode.getInstance().noNull(cc.getEmail_address1()).trim();
                    if (!resourceEmail.trim().endsWith(";")&&!resourceEmail.isEmpty()) {
                        resourceEmail += "; ";
                    }
                    resourceEmail += StandardCode.getInstance().noNull(cc.getEmail_address3()).trim();
                    if (!resourceEmail.trim().endsWith(";")&&!resourceEmail.isEmpty()) {
                        resourceEmail += "; ";
                    }
                    resourceEmail += StandardCode.getInstance().noNull(cc.getEmail_address3()).trim();

                }
            }
            resource += r.getFirstName();
            resource += COMMA_DELIMITER;
            resource += r.getLastName();
            resource += COMMA_DELIMITER;
            resource += r.getCompanyName().replaceAll(",", " ");
            resource += COMMA_DELIMITER;
            resource += r.getEmail_address1();
            resource += COMMA_DELIMITER;
            resource += r.getEmail_address2();
            resource += COMMA_DELIMITER;
            resource += resourceEmail;
            resource += COMMA_DELIMITER;
            resource += r.getProjectCount();
            resource += COMMA_DELIMITER;
            resource += r.getCountry();
//            resource += COMMA_DELIMITER;resource += r.get
//            resource += COMMA_DELIMITER;resource += r.get
//            resource += COMMA_DELIMITER;resource += r.get
            resource += NEW_LINE_SEPARATOR;

           // //System.out.println(r.getResourceId());
        }

        //END content
        String rtfTarget = "Resource.csv";
        File fileToDownload = new File(rtfTarget);
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileToDownload, false));
        bw.write(resource);
        bw.newLine();
        bw.close();

        try {
            String strHeader = "Attachment;Filename=" + rtfTarget;
            response.setHeader("Content-Disposition", strHeader);

            FileInputStream fileInputStream = new FileInputStream(fileToDownload);
           
            int i;
            OutputStream out = response.getOutputStream();
            while ((i = fileInputStream.read()) != -1) {
                out.write(i);
            }
            fileInputStream.close();
            out.close();

        } catch (Exception e) // file IO errors
        {
            e.printStackTrace();
        }
        deleteFile(rtfTarget);

        return null;
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

    public static boolean deleteFile(String sFilePath) {
        java.io.File oFile = new java.io.File(sFilePath);
        if (oFile.isDirectory()) {
            java.io.File[] aFiles = oFile.listFiles();
            for (java.io.File oFileCur : aFiles) {
                deleteFile(oFileCur.getAbsolutePath());
            }
        }
        return oFile.delete();
    }
}
