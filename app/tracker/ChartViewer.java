/*
 * ChartViewer.java
 *
 * Created on October 19, 2006, 2:03 PM
 */

package app.tracker;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGEncodeParam;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
//import java.awt.image.BufferedImage;
//import java.io.*;
//import org.jfree.chart.JFreeChart;
//import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author PP41387
 * @version
 */
public class ChartViewer extends HttpServlet {
    
//    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
//     * @param request servlet request
//     * @param response servlet response
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//    throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//        /* TODO output your page here
//        out.println("<html>");
//        out.println("<head>");
//        out.println("<title>Servlet ChartViewer</title>");
//        out.println("</head>");
//        out.println("<body>");
//        out.println("<h1>Servlet ChartViewer at " + request.getContextPath () + "</h1>");
//        out.println("</body>");
//        out.println("</html>");
//         */
//        out.close();
//    }
//    
//    
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /** Handles the HTTP <code>GET</code> method.
//     * @param request servlet request
//     * @param response servlet response
//     */
//    public void doGet( HttpServletRequest request, HttpServletResponse response ) 
//      throws ServletException, IOException 
//   { 
//      // get the chart from storage 
//      JFreeChart  chart = (JFreeChart) request.getSession(false).getAttribute( "chart" ); 
//      // set the content type so the browser can see this as it is 
//      response.setContentType( "image/jpeg" ); 
//      // send the picture 
//      int width = Integer.parseInt(request.getParameter("width"));
//      int height = Integer.parseInt(request.getParameter("height"));
//              
//      BufferedImage buf = chart.createBufferedImage(width, height, null); 
//      JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder( response.getOutputStream() ); 
//      JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam( buf ); 
//      param.setQuality( 0.75f, true ); 
//      encoder.encode( buf, param ); 
//   } 
//
//    
//    /** Handles the HTTP <code>POST</code> method.
//     * @param request servlet request
//     * @param response servlet response
//     */
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//    throws ServletException, IOException {
//        doGet(request, response);
//    }
//    
//    /** Returns a short description of the servlet.
//     */
//    public String getServletInfo() {
//        return "Short description";
//    }
//    // </editor-fold>
}
