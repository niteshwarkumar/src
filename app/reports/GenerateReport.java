/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.reports;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.report.dbport.DBConnection;

import java.util.*;
import java.sql.*;
import java.io.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.*;

import app.user.*;
import app.security.SecurityService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author abc
 */
public class GenerateReport extends Action {

    // ----------------------------------------------------- Instance Variables
    /**
     * The
     * <code>Log</code> instance for this application.
     */
    private Log log =
            LogFactory.getLog("org.apache.struts.webapp.Example");
    

    // --------------------------------------------------------- Public Methods
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an
     * <code>ActionForward</code> instance describing where and how control
     * should be forwarded, or
     * <code>null</code> if the response has already been completed.
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

        //System.out.println("I am here 1");

        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
       
        String FromDate = "";
        String ToDate = "";

        Integer clientId = 1;

        String jasperReport = "";
        Integer pmId = 0;
        Integer resource = 0;
        String pmName = "";
        String reportId = request.getParameter("reportId");
       
            try {
                pmId = Integer.parseInt(request.getParameter("pmId"));
                User cl = UserService.getInstance().getSingleUser(clientId);
            pmName = cl.getFirstName() + " " + cl.getLastName();
            } catch (Exception e) {
            }
            try {
                resource = Integer.parseInt(request.getParameter("resource"));
            } catch (Exception e) {
            }
       
        try {
            FromDate = request.getParameter("dateB");            
            ToDate = request.getParameter("dateC");
            
            FromDate = FromDate.replaceAll("/", "-");
            String flip[] = FromDate.split("-");
            FromDate = flip[2] + "-" + flip[0] + "-" + flip[1];

            ToDate = ToDate.replaceAll("/", "-");
            flip = ToDate.split("-");
            ToDate = flip[2] + "-" + flip[0] + "-" + flip[1];
        } catch (Exception e) {
        }
        DBConnection dBConnection = new DBConnection();
        Connection con = dBConnection.getConnection();



        //System.out.println("I am here 4");
        Map parameters = new HashMap();
        parameters.put("pmId", pmId);
        parameters.put("resource", resource);
        parameters.put("FromDate", FromDate);
        parameters.put("ToDate", ToDate);
        parameters.put("pmName", pmName);

        jasperReport = "C://templates/Report/"+reportId+".jasper";

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, con);

        OutputStream ouputStream = response.getOutputStream();

        JRExporter exporter = null;
        //response.setContentType("application/pdf");
//        String imagePath="../Report/CS04.html_files/";
        response.setContentType("text/html");
        exporter = new JRHtmlExporter();
        Map imagesMap = new HashMap();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
        exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, imagesMap);
        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, false);
//        exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,imagePath );
        exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
        // exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML,false);
        exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "image.jsp?image=");
        //response.setHeader("Content-disposition", "attachment; filename="+reportId+".pdf");
        try {
            exporter.exportReport();
        } catch (JRException e) {
            //throw new ServletException(e);
            //System.out.println(e);
        } finally {
            if (ouputStream != null) {
                try {
                    ouputStream.close();
                } catch (IOException ex) {
                    //System.out.println("PLEASE CHECK THE CRITERIA" + ex);
                }
            }
        }
        return null;
    }
}
