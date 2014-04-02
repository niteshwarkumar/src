/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.extjs.actions;

import app.extjs.vo.Upload_Doc;
import app.quote.QuoteService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.*;
import java.io.*;
import java.util.*;
import app.security.*;
import app.user.*;
import java.util.Date;
import java.util.zip.*;

/**
 *
 * @author Neil
 */
public class UploadFilesAction extends Action {

//ProjectAddPreFilesAction.java adds the new
//file (pre trados) to the db and builds the one-to-many
//relationship between quote and file (quote file)
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
    @Override
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

        (new File("C:/log")).mkdir();
        DynaValidatorForm uvg = (DynaValidatorForm) form;
        FormFile Upload = (FormFile) uvg.get("Upload");
        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));      
        Upload_Doc newDoc = new Upload_Doc();
        System.out.println("Upload File Name" + Upload);
        String saveFileName = null;
        if (Upload.getFileName().length() > 0) {
            String fileName = Upload.getFileName();
            byte[] fileData = Upload.getFileData(); //byte array of file data
            //random number in image name to prevent repeats
            Random gen = new Random(new Date().getSeconds());
            saveFileName = String.valueOf(gen.nextInt()) + fileName;
            File saveFile = new File("C:/log/" + Upload);
            FileOutputStream out = new FileOutputStream(saveFile);
            out.write(fileData);
            out.flush();
            out.close();
           
            System.out.println("Filename=====================>>>>>>>" + saveFileName);
        }

        newDoc.setPathname(Upload.getFileName());
        newDoc.setClientID(0);
        newDoc.setQuoteID(0);
        if (saveFileName != null) { //was a new picture uploaded
            newDoc.setPath(saveFileName);

        }
      //  QuoteService.getInstance().addUpload_Doc(newDoc);
      
        try
        {
            String destinationname = "C:/log/";
            byte[] buf = new byte[1024];
            ZipInputStream zipinputstream = null;
            ZipEntry zipentry;
            File newFile=null;

            zipinputstream = new ZipInputStream(new FileInputStream("C:/log/"+Upload.getFileName()));

            zipentry = zipinputstream.getNextEntry();
            while (zipentry != null)
            {
                //for each entry to be extracted
                String entryName = zipentry.getName();
                System.out.println("entryname "+entryName);
                int n;
                FileOutputStream fileoutputstream;
                newFile = new File(entryName);
                String directory = newFile.getParent();

                if(directory == null)
                {
                    if(newFile.isDirectory())
                        break;
                }

                fileoutputstream = new FileOutputStream(
                   destinationname+entryName);

                while ((n = zipinputstream.read(buf, 0, 1024)) > -1)
                    fileoutputstream.write(buf, 0, n);

                fileoutputstream.close();
                zipinputstream.closeEntry();
                zipentry = zipinputstream.getNextEntry();

            }//while

            zipinputstream.close();
            
        }
        catch (Exception e)
        {
//            e.printStackTrace();
            request.setAttribute("isError", "error");
            return (mapping.findForward("Error"));
        }






        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
