/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.quote;

import app.client.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.*;
import java.util.*;
import app.user.*;
import app.project.*;
import app.standardCode.*;
import app.security.*;
import org.apache.jasper.tagplugins.jstl.core.Catch;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Neil
 */
public class QuoteWizard3 extends Action {

//QuoteViewProjectRequirementsUpdateAction.java gets the
//updated project requirements and loads them to db
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
        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
        //START get id of current quote from either request, attribute, or cookie
        //id of quote from request
        String quoteId = null;
        quoteId = request.getParameter("quoteViewId");

        //check attribute in request
        if (quoteId == null) {
            quoteId = (String) request.getAttribute("quoteViewId");
        }

        //id of quote from cookie
        if (quoteId == null) {
            quoteId = StandardCode.getInstance().getCookie("quoteViewId", request.getCookies());
        }


        Integer id = Integer.valueOf(quoteId);
        int qnum = Integer.parseInt(request.getSession(false).getAttribute("ClientQuoteId").toString());
        //END get id of current quote from either request, attribute, or cookie

        //get quote to and then its related project
        Quote1 q = QuoteService.getInstance().getSingleQuote(id);


        // (String)request.getSession(false).getAttribute("username")
        Client c = ClientService.getInstance().getSingleClient(u.getId_client());
        Client_Quote cq = QuoteService.getInstance().getSingleClient_Quote(qnum);
        List quoteList = QuoteService.getInstance().getSingleClientQuote(id);
        //cq1=QuoteService.getInstance().get_SingleClientQuote(id);
String sOs="";
String dOs="";
String sApp="";
String dApp="";
String sVersion="";
String tVersion="";


        //updated values of the project
        DynaValidatorForm qvpr = (DynaValidatorForm) form;


      //  String projectManager = (String) qvpr.get("projectManager");
    //    String beforeWorkTurn = (String) (qvpr.get("beforeWorkTurn"));
     //   String beforeWorkTurnUnits = (String) (qvpr.get("beforeWorkTurnUnits"));
    //    String afterWorkTurn = (String) (qvpr.get("afterWorkTurn"));
    //    String afterWorkTurnUnits = (String) (qvpr.get("afterWorkTurnUnits"));
     //   String afterWorkTurnReason = (String) (qvpr.get("afterWorkTurnReason"));
   //     String sourceOS = (String) qvpr.get("sourceOs");
    //    String sourceApplication = (String) qvpr.get("sourceApplication");
//        String sourceVersion = (String) qvpr.get("sourceVersion");
       String sourceTechNotes = (String) qvpr.get("sourceTechNotes");
//        //String deliverableOS = (String) qvpr.get("deliverableOs");
//      //  String deliverableApplication = qvpr.get("deliverableApplication").toString();
//        String deliverableVersion = (String) qvpr.get("deliverableVersion");
       String deliverableTechNotes = (String) qvpr.get("deliverableTechNotes");
//        String deliverableSame = request.getParameter("deliverableSame");


        //update the new values
        Project p = q.getProject();
        //  p.setProduct(product);
        //   p.setProductDescription(productDescription);

     //   try {
    //        p.setPm(c.getProject_mngr());
   //     } catch (Exception e) {
  //      }
  //      p.setBeforeWorkTurn(beforeWorkTurn);
  //      p.setBeforeWorkTurnUnits(beforeWorkTurnUnits);
   ////     p.setAfterWorkTurn(afterWorkTurn);
   //     p.setAfterWorkTurnUnits(afterWorkTurnUnits);
  //      p.setAfterWorkTurnReason(afterWorkTurnReason);
//        p.setSourceOS(sOs);
//        p.setSourceApplication(sApp);
//        p.setSourceVersion(sourceVersion);
//        p.setSourceTechNotes(sourceTechNotes);
//        p.setDeliverableOS(dOs);
//        p.setDeliverableApplication(dApp);
//        p.setDeliverableVersion(deliverableVersion);
//        p.setDeliverableTechNotes(deliverableTechNotes);
//
//
//        cq.setApplication(sApp);
//        cq.setOs(sOs);
//        cq.setVersion(sourceVersion);
//       cq.setTarget_application(dApp);
//       cq.setTarget_os(dOs);
//        cq.setTarget_version(deliverableVersion);


//        if (deliverableSame != null) {
//            p.setDeliverableSame("true");
//            p.setDeliverableOS(sOs);
//            p.setDeliverableApplication(sApp);
//            p.setDeliverableVersion(sourceVersion);
//            cq.setTarget_application(sApp);
//            cq.setTarget_os(sOs);
//            cq.setTarget_version(sourceVersion);
//            //p.setDeliverableTechNotes(sourceTechNotes);
//        } else {
//            p.setDeliverableSame("false");
//        }

        //update to db
//        ProjectService.getInstance().updateProject(p);
//        QuoteService.getInstance().saveClientQuote(cq);

try{
         String jsonTechnical=request.getParameter("technicalJSON");
              JSONArray technical=  new JSONArray(jsonTechnical);
               for(int i=0;i< technical.length();i++){
              JSONObject j=(JSONObject)technical.get(i);
              Technical tech=null;
              if(j.getString("id").equalsIgnoreCase("new")){tech=new Technical();}else{
               tech=QuoteService.getInstance().getSingleTechnical(Integer.parseInt(j.getString("id")));}
               tech.setClientQuote_ID(cq.getId());
               tech.setSourceApplication((j.getString("sourceApplication")));
               sApp=j.getString("sourceApplication");
               tech.setSourceOs((j.getString("sourceOs")));
               sOs=j.getString("sourceOs");
               tech.setSourceVersion((j.getString("sourceVersion")));
               sVersion=j.getString("sourceVersion");
               tech.setTargetOs((j.getString("targetOs")));
               dOs=j.getString("targetOs");
               tech.setTargetApplication((j.getString("targetApplication")));
               dApp=j.getString("targetApplication");
               tech.setTargetVersion(j.getString("targetVersion"));
               tVersion=j.getString("targetVersion");
               // tech.setTechnicalId((j.getString("ID_Technical")));
                QuoteService.getInstance().saveTechnical(tech);
                if(i<technical.length()-1){
                sApp+=" , ";
                dApp+=" , ";
                sOs+=" , ";
                dOs+=" , ";
                sVersion+=" , ";
                tVersion+=" , ";

                }
               }

 p.setSourceOS(sOs);
        p.setSourceApplication(sApp);
        p.setSourceVersion(sVersion);
        p.setSourceTechNotes(sourceTechNotes);
        p.setDeliverableOS(dOs);
        p.setDeliverableApplication(dApp);
        p.setDeliverableVersion(tVersion);
        p.setDeliverableTechNotes(deliverableTechNotes);


        cq.setApplication(sApp);
        cq.setOs(sOs);
        cq.setVersion(sVersion);
        cq.setTarget_application(dApp);
        cq.setTarget_os(dOs);
        cq.setTarget_version(tVersion);
        ProjectService.getInstance().updateProject(p);
        QuoteService.getInstance().saveClientQuote(cq);
        //place quote into attribute for display

}catch(Exception e){}

        request.setAttribute("quote", q);

        // Forward control to the specified success URI



        return (mapping.findForward("Success"));

    }
}
