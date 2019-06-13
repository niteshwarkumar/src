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
public class InDeliveryUpdateAction extends Action {

    // ----------------------------------------------------- Instance Variables
    /**
     * The <code>Log</code> instance for this application.
     */
    private Log log
            = LogFactory.getLog("org.apache.struts.webapp.Example");

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
        INDelivery iDelivery = InteqaService.getInstance().getINDelivery(id);
        if (iDelivery == null) {
            iDelivery = new INDelivery();
            iDelivery.setProjectId(id);
        }
        String jsonProducts = request.getParameter("deliveryJSON");
        DynaValidatorForm upd = (DynaValidatorForm) form;
        String notes = (String) upd.get("notes");
        String caveats = (String) upd.get("caveats");
        String verified = (String) upd.get("verified");
        String verifiedBy = (String) upd.get("verifiedBy");
        String verifiedDate = (String) upd.get("verifiedDate");
        String verifiedText = (String) upd.get("verifiedText");

        String engverified = (String) upd.get("engverified");
        String engverifiedBy = (String) upd.get("engverifiedBy");
        String engverifiedDate = (String) upd.get("engverifiedDate");
        String engverifiedText = (String) upd.get("engverifiedText");

        String dtpverified = (String) upd.get("dtpverified");
        String dtpverifiedBy = (String) upd.get("dtpverifiedBy");
        String dtpverifiedDate = (String) upd.get("dtpverifiedDate");
        String dtpverifiedText = (String) upd.get("dtpverifiedText");
        String vendor = (String) upd.get("vendor");
        iDelivery.setCaveats(caveats);
        iDelivery.setNotes(notes);

        ///verification
        iDelivery.setVerifiedBy(verifiedBy);
        try {
            if (verifiedDate.length() > 0) { //if present
                iDelivery.setVerifiedDate(DateService.getInstance().convertDate(verifiedDate).getTime());
            }else{
                iDelivery.setVerifiedDate(null);
            }
        } catch (Exception e) {
            //System.out.println("Date Errooooorr " + e.getMessage());
        }
        iDelivery.setVerifiedText(verifiedText);

        if (verified.equalsIgnoreCase("on")) {
            iDelivery.setVerified(true);
        } else {
            iDelivery.setVerified(false);
        }

//eng
        iDelivery.setEngverifiedBy(engverifiedBy);
        try {
            if (verifiedDate.length() > 0) { //if present
                iDelivery.setEngverifiedDate(DateService.getInstance().convertDate(engverifiedDate).getTime());
            }else{iDelivery.setEngverifiedDate(null);}
        } catch (Exception e) {
            //System.out.println("Date Errooooorr " + e.getMessage());
        }
        iDelivery.setEngverifiedText(engverifiedText);

        if (engverified.equalsIgnoreCase("on")) {
            iDelivery.setEngverified(true);
        } else {
            iDelivery.setEngverified(false);
        }
        if (vendor.equalsIgnoreCase("on")) {
            iDelivery.setVendor(true);
        } else {
            iDelivery.setVendor(false);
        }

//dtp
        iDelivery.setDtpverifiedBy(dtpverifiedBy);
        try {
            if (dtpverifiedDate.length() > 0) { //if present
                iDelivery.setDtpverifiedDate(DateService.getInstance().convertDate(dtpverifiedDate).getTime());
            }else{iDelivery.setDtpverifiedDate(null);}
        } catch (Exception e) {
            //System.out.println("Date Errooooorr " + e.getMessage());
        }
        iDelivery.setDtpverifiedText(dtpverifiedText);

        if (dtpverified.equalsIgnoreCase("on")) {
            iDelivery.setDtpverified(true);
        } else {
            iDelivery.setDtpverified(false);
        }
        
        InteqaService.getInstance().updateInDelivery(iDelivery);
        iDelivery = InteqaService.getInstance().getINDelivery(id);

//        String delJson = request.getParameter("DelData");
//          try{
//          JSONArray delJsonArray = new JSONArray(delJson);
//          for(int i=0;i<delJson.length();i++){
//                JSONObject j=(JSONObject)delJsonArray.get(i);
//                InteqaService.getInstance().unlinkInDelReq(Integer.parseInt(j.getString("id")));
//          }
//        }catch(Exception e){}

String fromPorQ = request.getParameter("fromPorQ");

        String oper = request.getParameter("oper");
         String type=request.getParameter("type");
        if(null!=oper){
        if (oper.equalsIgnoreCase("edit")) {
            int reqid = Integer.parseInt(request.getParameter("id"));
            INDeliveryReq idr = InteqaService.getInstance().getINDeliveryReq(reqid);
            idr.setClientReqBy(request.getParameter("reqBy"));
            idr.setClientReqText(request.getParameter("requirement"));
            String verification = request.getParameter("reqCheck");
            idr.setType(type);
            idr.setNotes(request.getParameter("notes"));
            idr.setInstructionsFor(request.getParameter("instructionsFor")); 
            if (verification.equalsIgnoreCase("yes")) {
                idr.setClientReqCheck(true);
            } else {
                idr.setClientReqCheck(false);
            }
            InteqaService.getInstance().updateInDeliveryReq(idr);
        } else if (oper.equalsIgnoreCase("add")) {
           
            INDeliveryReq idr = new INDeliveryReq();

            idr.setClientReqBy(request.getParameter("reqBy"));
            idr.setClientReqText(request.getParameter("requirement"));
            String verification = request.getParameter("reqCheck");
            if (verification.equalsIgnoreCase("yes")) {
                idr.setClientReqCheck(true);
            } else {
                idr.setClientReqCheck(false);
            }
            idr.setInDeliveryId(iDelivery.getId());
            idr.setFromPorQ(fromPorQ);
            idr.setType(type);
            idr.setNotes(request.getParameter("notes"));
            idr.setInstructionsFor(request.getParameter("instructionsFor")); 
            
            InteqaService.getInstance().updateInDeliveryReq(idr);

        }else if (oper.equalsIgnoreCase("del")) {
            int reqid = Integer.parseInt(request.getParameter("id"));
            InteqaService.getInstance().unlinkInDelReq(reqid);
            

        }
    }
        
        
//        INDeliveryReq idr=null;
//        if (jsonProducts != null && !"".equals(jsonProducts)) {
//            JSONArray products = new JSONArray(jsonProducts);
//            for (int i = 0; i < products.length(); i++) {
//                JSONObject j = (JSONObject) products.get(i);
//                //System.out.println("JSONObject>>>>>>>>>>>>>>>>>>>>>>>>>" + j);
//                //INDeliveryReq idr = new INDeliveryReq();
//                try{
//                idr=InteqaService.getInstance().getINDeliveryReq(Integer.parseInt(j.getString("id")));
//                }catch(Exception e){
//                    idr = new INDeliveryReq();
//                }
//                idr.setClientReqBy(j.getString("reqBy"));
//
//                try {
//                    if(j.getString("reqCheck").equalsIgnoreCase("true")) {
//                        idr.setClientReqCheck(true);
//                    }else{
//                        idr.setClientReqCheck(false);
//                    }
//                } catch (Exception e) {
//                }
//
//                idr.setClientReqText(j.getString("requirement"));
//                idr.setInDeliveryId(iDelivery.getId());
//                try {
//                    if (idr.getFromPorQ() == null) {
//                        idr.setFromPorQ(fromPorQ);
//                    }
//                } catch (Exception e) {
//                }
//
//                InteqaService.getInstance().updateInDeliveryReq(idr);
//            }
//        }
        request.setAttribute("fromPorQ", fromPorQ);

        // Forward control to the specified success URI
        if (fromPorQ.equalsIgnoreCase("Q")) {
            return mapping.findForward("QuoteSuccess");
        }
        return (mapping.findForward("Success"));
    }
}
