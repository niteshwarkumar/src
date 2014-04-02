/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.inteqa;

import app.project.*;
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
import app.security.*;
import app.standardCode.*;

import org.apache.struts.validator.*;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Niteshwar
 */
public class InBasicsUpdateAction extends Action {

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

        //START get id of current project from either request, attribute, or cookie
        //id of project from request
        String projectId = null;
        projectId = request.getParameter("projectViewId");

        //check attribute in request
        if (projectId == null) {
            projectId = (String) request.getAttribute("projectViewId");
        }

        //id of project from cookie
        if (projectId == null) {
            projectId = StandardCode.getInstance().getCookie("projectViewId", request.getCookies());
        }

        //default project to last if not in request or cookie
        if (projectId == null) {
            List results = ProjectService.getInstance().getProjectList();

            ListIterator iterScroll = null;
            for (iterScroll = results.listIterator(); iterScroll.hasNext(); iterScroll.next()) {
            }
            iterScroll.previous();
            Project p = (Project) iterScroll.next();
            projectId = String.valueOf(p.getProjectId());
        }

        Integer id = Integer.valueOf(projectId);

        //END get id of current project from either request, attribute, or cookie



        INBasics INBasics = InteqaService.getInstance().getINBasics(id);
        if (INBasics == null) {
            INBasics = new INBasics();
            INBasics.setProjectId(id);
        }

        //place this project into request for further display in jsp page
        DynaValidatorForm upd = (DynaValidatorForm) form;



        String server = (String) upd.get("server");
        String ftp = (String) upd.get("ftp");
        String other = (String) upd.get("other");
        String dtpReq1 = (String) upd.get("dtpReq1");
        String dtpReq2 = (String) upd.get("dtpReq2");
        String genGra1 = (String) upd.get("genGra1");
        String genGra2 = (String) upd.get("genGra2");
        String genGra3 = (String) upd.get("genGra3");
        String genGra4 = (String) upd.get("genGra4");
        String screen1 = (String) upd.get("screen1");
        String screen2 = (String) upd.get("screen2");
        String screen3 = (String) upd.get("screen3");
        String screen4 = (String) upd.get("screen4");
        String deliveryFormat1 = (String) upd.get("deliveryFormat1");
        String deliveryFormatOth = (String) upd.get("deliveryFormatOth");
        String deliveryFormatOthText = (String) upd.get("deliveryFormatOthText");
        String DeliveryFiles1 = (String) upd.get("DeliveryFiles1");
        String DeliveryFiles2 = (String) upd.get("DeliveryFiles2");
        String DeliveryFilesOth = (String) upd.get("DeliveryFilesOth");
        String DeliveryFilesOthText = (String) upd.get("DeliveryFilesOthText");
        String OtherInstruction = (String) upd.get("OtherInstruction");
        String verified = (String) upd.get("verified");
        String verifiedBy = (String) upd.get("verifiedBy");
        String verifiedDate = (String) upd.get("verifiedDate");
        String verifiedText = (String) upd.get("verifiedText");
        String textBox1 = (String) upd.get("textBox1");
        String textBox2 = (String) upd.get("textBox2");
        String textBox3 = (String) upd.get("textBox3");


        try {
            if (verifiedDate.length() > 0) { //if present
                INBasics.setVerifiedDate(DateService.getInstance().convertDate(verifiedDate).getTime());
            }
        } catch (Exception e) {
            System.out.println("Date Errooooorr " + e.getMessage());
        }

        INBasics.setDeliveryFilesOthText(DeliveryFilesOthText);
        INBasics.setDeliveryFormatOthText(deliveryFormatOthText);
        INBasics.setFtp(ftp);
        INBasics.setOther(other);
        INBasics.setOtherInstruction(OtherInstruction);
        INBasics.setServer(server);
        INBasics.setVerifiedBy(verifiedBy);
        INBasics.setTextBox1(textBox1);
        INBasics.setTextBox2(textBox2);
        INBasics.setTextBox3(textBox3);
        INBasics.setVerifiedText(verifiedText);


        if (DeliveryFiles1.equalsIgnoreCase("on")) {
            INBasics.setDeliveryFiles1(true);
        } else {
            INBasics.setDeliveryFiles1(false);
        }
        if (DeliveryFiles2.equalsIgnoreCase("on")) {
            INBasics.setDeliveryFiles2(true);
        } else {
            INBasics.setDeliveryFiles2(false);
        }
        if (DeliveryFilesOth.equalsIgnoreCase("on")) {
            INBasics.setDeliveryFilesOth(true);
        } else {
            INBasics.setDeliveryFilesOth(false);
        }
        if (deliveryFormat1.equalsIgnoreCase("on")) {
            INBasics.setDeliveryFormat1(true);
        } else {
            INBasics.setDeliveryFormat1(false);
        }
        if (deliveryFormatOth.equalsIgnoreCase("on")) {
            INBasics.setDeliveryFormatOth(true);
        } else {
            INBasics.setDeliveryFormatOth(false);
        }
        if (dtpReq1.equalsIgnoreCase("on")) {
            INBasics.setDtpReq1(true);
        } else {
            INBasics.setDtpReq1(false);
        }
        if (dtpReq2.equalsIgnoreCase("on")) {
            INBasics.setDtpReq2(true);
        } else {
            INBasics.setDtpReq2(false);
        }
        if (genGra1.equalsIgnoreCase("on")) {
            INBasics.setGenGra1(true);
        } else {
            INBasics.setGenGra1(false);
        }
        if (genGra2.equalsIgnoreCase("on")) {
            INBasics.setGenGra2(true);
        } else {
            INBasics.setGenGra2(false);
        }
        if (genGra3.equalsIgnoreCase("on")) {
            INBasics.setGenGra3(true);
        } else {
            INBasics.setGenGra3(false);
        }
        if (genGra4.equalsIgnoreCase("on")) {
            INBasics.setGenGra4(true);
        } else {
            INBasics.setGenGra4(false);
        }
        if (screen1.equalsIgnoreCase("on")) {
            INBasics.setScreen1(true);
        } else {
            INBasics.setScreen1(false);
        }
        if (screen2.equalsIgnoreCase("on")) {
            INBasics.setScreen2(true);
        } else {
            INBasics.setScreen2(false);
        }
        if (screen3.equalsIgnoreCase("on")) {
            INBasics.setScreen3(true);
        } else {
            INBasics.setScreen3(false);
        }
        if (screen4.equalsIgnoreCase("on")) {
            INBasics.setScreen4(true);
        } else {
            INBasics.setScreen4(false);
        }
        if (verified.equalsIgnoreCase("on")) {
            INBasics.setVerified(true);
        } else {
            INBasics.setVerified(false);
        }

        try {
            boolean unlinkInSourceFile = InteqaService.unlinkInSourceFile(id);
            String sourceFileJSON=request.getParameter("sourceFileJSON");
             JSONArray sourceFileJSONArray=  new JSONArray(sourceFileJSON);
            for(int i=0;i< sourceFileJSONArray.length();i++){
             JSONObject j=(JSONObject)sourceFileJSONArray.get(i);
             INSourceFile sourceFile=new INSourceFile();
//              if(j.getString("id").equalsIgnoreCase("new")){sourceFile=new INSourceFile();}else{
//               sourceFile=InteqaService.getInstance().getSourceFile(Integer.parseInt(j.getString("id")));}

             sourceFile.setExtension(j.getString("extension"));
             sourceFile.setNotes(j.getString("notes"));
             sourceFile.setProjectId(Integer.valueOf(projectId));
             sourceFile.setQuantity(Integer.parseInt(j.getString("quantity")));

             InteqaService.getInstance().updateInSourceFile(sourceFile);
             
        }
        } catch (Exception e) {
        }

        

        InteqaService.getInstance().updateInBasics(INBasics);


        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
