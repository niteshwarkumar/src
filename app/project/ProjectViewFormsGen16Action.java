/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.project;

import app.extjs.global.LanguageAbs;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import java.util.*;
import app.user.*;
import app.security.*;
import app.standardCode.*;
import java.io.FileInputStream;
import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *
 * @author Niteshwar
 */
public class ProjectViewFormsGen16Action extends Action{


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

        //START get id of current project from either request, attribute, or cookie
        //id of project from request
	String projectId = null;
	projectId = request.getParameter("projectViewId");

        //check attribute in request
        if(projectId == null) {
            projectId = (String) request.getAttribute("projectViewId");
        }

        //id of project from cookie
        if(projectId == null) {
            projectId = StandardCode.getInstance().getCookie("projectViewId", request.getCookies());
        }

        //default project to last if not in request or cookie
        if(projectId == null) {
                java.util.List results = ProjectService.getInstance().getProjectList();

                ListIterator iterScroll = null;
                for(iterScroll = results.listIterator(); iterScroll.hasNext(); iterScroll.next()) {}
                   iterScroll.previous();
                   Project p = (Project) iterScroll.next();
                   projectId = String.valueOf(p.getProjectId());
         }

        Integer id = Integer.valueOf(projectId);

        //END get id of current project from either request, attribute, or cookie

        //get project
        Project p = ProjectService.getInstance().getSingleProject(id);

        //get user (project manager)
        User u = UserService.getInstance().getSingleUserRealName(StandardCode.getInstance().getFirstName(p.getPm()), StandardCode.getInstance().getLastName(p.getPm()));

                String filein = "C:/templates/Tracking_Sheet.xls";
//		WorkbookSettings wbSettings = new WorkbookSettings();
//
//		wbSettings.setLocale(new Locale("en", "EN"));
//
//		WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
//		workbook.createSheet("Report", 0);
//		WritableSheet excelSheet = workbook.getSheet(0);
////		createLabel(excelSheet);
////		createContent(excelSheet);
//
//		workbook.write();
//		workbook.close();
                int tgtCount = 3;
               POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filein));
               HSSFWorkbook wb = new HSSFWorkbook(fs, true);
               HSSFSheet excelSheet = wb.getSheetAt(0);
               if(p.getSourceDocs()!=null){
                for(Iterator iterSource = p.getSourceDocs().iterator();iterSource.hasNext();){
                    SourceDoc sd = (SourceDoc) iterSource.next();
                    String abrSrc = (String) LanguageAbs.getInstance().getAbs().get(sd.getLanguage());
                     if (sd.getTargetDocs() != null) {
                         for (Iterator iterTarget = sd.getTargetDocs().iterator(); iterTarget.hasNext();) {
                                TargetDoc td = (TargetDoc) iterTarget.next();
                                 String abrTgt = (String) LanguageAbs.getInstance().getAbs().get(td.getLanguage());
                             printExcel(wb, 0, tgtCount++, 0, abrSrc+"-"+abrTgt );
                         }
                     }
                }
               }
        printExcel(wb, 0, 0, 8, p.getNumber()+p.getCompany().getCompany_code() );
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=Tracking-" + p.getNumber() + ".xls");

        ServletOutputStream out = response.getOutputStream();
        wb.write(out);
        out.flush();
        out.close();

        return null;

    }

    private void printExcel(HSSFWorkbook wb, int sheet, int row, int column, String data) {
        HSSFSheet excelSheet = wb.getSheetAt(sheet);
        HSSFRow myRow = excelSheet.getRow(row);
        HSSFCell myCell = myRow.getCell(column);
        myCell.setCellValue(data);
    }

}

