//ReportsList13Action.java gets the display
//ready for reporting info, such as graphs, dollar totals, etc.

package app.reports;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleException;
import org.apache.struts.util.MessageResources;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.validator.*;
import java.util.*;
import java.text.*;
import app.user.*;
import app.client.*;
import app.project.*;
import app.standardCode.*;
import app.db.*;
import app.workspace.*;
import app.security.*;


public final class ReportsList13Action extends Action {


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
        
        //PRIVS check that reports user is viewing this page 
        if(!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "reports")) {
            return (mapping.findForward("accessDenied"));
        }//END PRIVS check that reports user is viewing this page
        
        //START get report criteria
        String yearAString = request.getParameter("yearA");
        Date yearA = new Date();
        if(yearAString == null) { //default to current Year
            GregorianCalendar yearATemp = new GregorianCalendar();
            yearATemp.setTime(new Date());
            yearATemp.set(Calendar.MONTH, Calendar.JANUARY);
            yearATemp.set(Calendar.DATE, 1);
            yearA = yearATemp.getTime();
            yearAString = String.valueOf(yearATemp.get(Calendar.YEAR));
        }
        else {
            GregorianCalendar yearATemp = new GregorianCalendar();
            yearATemp.setTime(new Date());
            yearATemp.set(Calendar.YEAR, Integer.valueOf(yearAString).intValue());
            yearATemp.set(Calendar.MONTH, Calendar.JANUARY);
            yearATemp.set(Calendar.DATE, 1);
            yearA = yearATemp.getTime();
        }
        
        Date yearB = new Date();
        GregorianCalendar yearBTemp = new GregorianCalendar();
        yearBTemp.setTime(yearA);
        yearBTemp.set(Calendar.MONTH, Calendar.DECEMBER);
        yearBTemp.set(Calendar.DATE, 31);
        yearB = yearBTemp.getTime();   
        //END get report criteria        
                
        //run report search
        List results = new ArrayList();
        results = ReportsService.getInstance().runReport13(yearA, yearB);
        
        //START build totals chart
        String totals[][] = new String[13][12];
        if(results != null) {
            //START get projects per office
            ArrayList sanList = new ArrayList();
            for(ListIterator iter = results.listIterator(); iter.hasNext();) {
                 Project p = (Project) iter.next();
                 String pm = p.getPm();
                 try{
                  User u = UserService.getInstance().getSingleUserRealName(StandardCode.getInstance().getFirstName(p.getPm()), StandardCode.getInstance().getLastName(p.getPm()));
                  if(u.getLocation().getLocation().equals("San Francisco")) {
                       sanList.add(p);
                  }}catch(Exception e){}
            }
            ArrayList pitList = new ArrayList();
            for(ListIterator iter = results.listIterator(); iter.hasNext();) {
                 Project p = (Project) iter.next();
                 String pm = p.getPm();
                 try{
                  User u = UserService.getInstance().getSingleUserRealName(StandardCode.getInstance().getFirstName(p.getPm()), StandardCode.getInstance().getLastName(p.getPm()));
                  if(u.getLocation().getLocation().equals("Pittsburgh")) {
                       pitList.add(p);
                  }}catch(Exception e){}
            }
            ArrayList bilList = new ArrayList();
            for(ListIterator iter = results.listIterator(); iter.hasNext();) {
                 Project p = (Project) iter.next();
                 String pm = p.getPm();
                 try{
                  User u = UserService.getInstance().getSingleUserRealName(StandardCode.getInstance().getFirstName(p.getPm()), StandardCode.getInstance().getLastName(p.getPm()));
                  if(u.getLocation().getLocation().equals("Bilbao")) {
                       bilList.add(p);
                  }}catch(Exception e){}
            }
            //END get projects per office  
            
            //START add row data through average
            for(int i = 0; i < 12; i++) {
                for(int j = 0; j < 10; j++) {
                        if(j == 0)
                            totals[i][j] = ReportsService.getInstance().getMonthName(i);
                        if(j == 1)
                            totals[i][j] = ReportsService.getInstance().getProjectCount(i, sanList);  
                        if(j == 2)
                            totals[i][j] = ReportsService.getInstance().getProjectRevenue(i, sanList); 
                        if(j == 3)
                            totals[i][j] = ReportsService.getInstance().getProjectCount(i, pitList);  
                        if(j == 4)
                            totals[i][j] = ReportsService.getInstance().getProjectRevenue(i, pitList); 
                        if(j == 5)
                            totals[i][j] = ReportsService.getInstance().getProjectCount(i, bilList); 
                        if(j == 6)
                            totals[i][j] = ReportsService.getInstance().getProjectRevenue(i, bilList); 
                        if(j == 7)
                            totals[i][j] = String.valueOf(Integer.valueOf(totals[i][1]).intValue() + Integer.valueOf(totals[i][3]).intValue() + Integer.valueOf(totals[i][5]).intValue());
                        
                        Double monthTotalSan = StandardCode.getInstance().getDoubleFromString(ReportsService.getInstance().getProjectRevenue(i, sanList));
                        Double monthTotalPit = StandardCode.getInstance().getDoubleFromString(ReportsService.getInstance().getProjectRevenue(i, pitList));
                        Double monthTotalBil = StandardCode.getInstance().getDoubleFromString(ReportsService.getInstance().getProjectRevenue(i, bilList));
                        if(j == 8) {
                            totals[i][j] = StandardCode.getInstance().formatDouble(new Double(monthTotalSan.doubleValue() + monthTotalPit.doubleValue() + monthTotalBil.doubleValue()));
                        }
                        if(j == 9)
                            totals[i][j] = StandardCode.getInstance().formatDouble(new Double((monthTotalSan.doubleValue() + monthTotalPit.doubleValue() + monthTotalBil.doubleValue()) / Double.valueOf(String.valueOf(Integer.valueOf(totals[i][1]).intValue() + Integer.valueOf(totals[i][3]).intValue() + Integer.valueOf(totals[i][5]).intValue())).doubleValue())); 
                }
            }         
            //END add row data through average

            //START totals row
            totals[12][0] = "Totals";
            int san1Total = 0;
            for(int i = 0; i < 12; i++) {
                san1Total += Integer.valueOf(totals[i][1]).intValue();
            }
            totals[12][1] = String.valueOf(new Integer(san1Total));
            
            double san2Total = 0.0;
            for(int i = 0; i < 12; i++) {
                san2Total += StandardCode.getInstance().getDoubleFromString(totals[i][2]).doubleValue();
            }
            totals[12][2] = StandardCode.getInstance().formatDouble(new Double(san2Total));
            
            int pit3Total = 0;
            for(int i = 0; i < 12; i++) {
                pit3Total += Integer.valueOf(totals[i][3]).intValue();
            }
            totals[12][3] = String.valueOf(new Integer(pit3Total));
            
            double pit4Total = 0.0;
            for(int i = 0; i < 12; i++) {
                pit4Total += StandardCode.getInstance().getDoubleFromString(totals[i][4]).doubleValue();
            }
            totals[12][4] = StandardCode.getInstance().formatDouble(new Double(pit4Total));
            
            int bil5Total = 0;
            for(int i = 0; i < 12; i++) {
                bil5Total += Integer.valueOf(totals[i][5]).intValue();
            }
            totals[12][5] = String.valueOf(new Integer(bil5Total));
            
            double bil6Total = 0.0;
            for(int i = 0; i < 12; i++) {
                bil6Total += StandardCode.getInstance().getDoubleFromString(totals[i][6]).doubleValue();
            }
            totals[12][6] = StandardCode.getInstance().formatDouble(new Double(bil6Total));
            
            int tot7Total = 0;
            for(int i = 0; i < 12; i++) {
                tot7Total += Integer.valueOf(totals[i][7]).intValue();
            }
            totals[12][7] = String.valueOf(new Integer(tot7Total));
            
            double tot8Total = 0.0;
            for(int i = 0; i < 12; i++) {
                tot8Total += StandardCode.getInstance().getDoubleFromString(totals[i][8]).doubleValue();
            }
            totals[12][8] = StandardCode.getInstance().formatDouble(new Double(tot8Total));
            
            totals[12][9] = StandardCode.getInstance().formatDouble(new Double(tot8Total / (new Double(tot7Total).doubleValue())));
            
            //percentage columns
            for(int i = 0; i < 12; i++) {
                for(int j = 10; j < 12; j++) {
                    if(j == 10)
                            totals[i][j] = StandardCode.getInstance().formatDoublePercent(new Double(new Double(totals[i][7]).doubleValue() / new Double(totals[12][7]).doubleValue())); 
                    if(j == 11)
                            totals[i][j] = StandardCode.getInstance().formatDoublePercent(new Double(StandardCode.getInstance().getDoubleFromString(totals[i][8]).doubleValue() / StandardCode.getInstance().getDoubleFromString(totals[12][8]).doubleValue()));
                }
            } 
            totals[12][10] = "100.00";
            totals[12][11] = "100.00";
            //END totals row
            
        } //end if(results != null) {
        //END build totals chart
        
        
        //place results and criteria into request
        request.setAttribute("yearA", yearAString);
        request.setAttribute("totals", totals);
        String resultsSize;
        if(results != null) {
            resultsSize = "<br>Found " + String.valueOf(results.size()) + " Projects";
        }
        else {
            resultsSize = "<br>Found " + "0" + " Projects";
        }        
        request.setAttribute("title", "All Active Projects in Calendar Year " + yearAString  + resultsSize);
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
