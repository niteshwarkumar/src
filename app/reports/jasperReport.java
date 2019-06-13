/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.reports;

import com.mysql.jdbc.Connection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
//import net.sf.jasperreports.engine.*;
//import net.sf.jasperreports.engine.export.*;
import java.util.*;
import java.io.*;
import org.report.dbport.*;
import app.user.*;
import java.text.*;
//import javax.xml.parsers.SAXParser;
//import javax.xml.parsers.SAXParserFactory;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.util.*;


//import org.xml.sax.SAXNotRecognizedException;

/**
 *
 * @author Niteshwar
 */
public class jasperReport extends Action {

    public static String SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage",
            XML_SCHEMA = "http://www.w3.org/2001/XMLSchema",
            SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {


//check for login (security) values


        //System.out.println("I am here 1");

        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));

        String FromDate = "";
        String ToDate = "";
        String reportId;
        String reportName;
        Integer clientName = 0;
        String jasperReport = "";
        Integer clientId = u.getId_client();
        reportId = request.getParameter("reportId");
        //System.out.println("I am here 2");
        try {
            //System.out.println("I am here 3");
            try {
                clientName = Integer.parseInt(request.getParameter("clientName"));
            } catch (Exception e) {
            }
            //System.out.println("I am here 4");
            FromDate = request.getParameter("dateB");
            //System.out.println("I am here 5");
            ToDate = request.getParameter("dateC");
            //System.out.println("I am here 6");
            //System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY" + reportId);
            FromDate = FromDate.replaceAll("/", "-");
            String flip[] = FromDate.split("-");
            FromDate = flip[2] + "-" + flip[0] + "-" + flip[1];

            //System.out.println("XXXXXXXXXXX" + FromDate);
            ToDate = ToDate.replaceAll("/", "-");

            flip = ToDate.split("-");
            ToDate = flip[2] + "-" + flip[0] + "-" + flip[1];

        } catch (Exception e) {
        }
        DBConnection dBConnection = new DBConnection();
        Connection con = (Connection) dBConnection.getConnection();
        //System.out.println("I am here 4");

        Map parameters = new HashMap();
        try {
            parameters.put("clientName", clientName);
        } catch (Exception e) {
        }
        parameters.put("clientId", clientId);
        parameters.put("FromDate", FromDate);
        parameters.put("ToDate", ToDate);
        System.setProperty("jasper.reports.compile.class.path","D:/ExcelTransCode/test 3/test/WEB-INF/lib/jasperreports-0.6.1.jar" +System.getProperty("path.separator") +("D:/ExcelTransCode/test 3/test/WEB-INF/classes/"));
	//System.out.println("path1:== " +System.getProperty("jasper.reports.compile.class.path"));
        System.setProperty("jasper.reports.compile.temp","D:/ExcelTransCode/test 3/test/pages/Report/");
        //System.out.println("path2:== " +System.getProperty("jasper.reports.compile.temp"));

       // JasperDesign jasperDesign = JRXmlLoader.load("D:/ExcelTransCode/test 3/test/pages/Report/report20.jrxml");
        JasperReport jReport = JasperCompileManager.compileReport("D:/ExcelTransCode/test 3/test/pages/Report/report20.jrxml");
//PRODUCTION

        reportId = "";
        try {
            reportId = request.getParameter("reportId");
            //System.out.println("report Name:" + reportId);
        } catch (Exception e) {
            reportId = "P18";
        }
        if (reportId == null) {
            reportId = "P18";
        }




        JasperPrint jasperPrint = JasperFillManager.fillReport(jReport, parameters, con);

        OutputStream ouputStream = response.getOutputStream();

        JRExporter exporter = null;
        String imagePath = "";
        //response.setContentType("application/pdf");
        if (reportId.equalsIgnoreCase("RR")) {
            imagePath = "../Report/RejectReason.html_files/";
        } else {
            imagePath = "../Report/" + reportId + ".html_files/";
        }
        response.setContentType("text/html");
        //PrintWriter out = response.getWriter();
        // response.setContentType("application/html");
        //   response.setHeader("Content-Disposition",  "inline; filename=\"report.html\"");

        exporter = new JRHtmlExporter();
        Map imagesMap = new HashMap();
        //session.setAttribute("IMAGES_MAP", imagesMap);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
        exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, imagesMap);
        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, false);
        exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, imagePath);
        exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
        // exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML,false);
        //exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "image.jsp?image=");
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
                    //System.out.println("workorder finally catch:" + ex);
                }
            }
        }






        return (mapping.findForward("Success"));
    }
}
