//ClientEditUpdateAction.java gets updated client info
//from a form.  It then uploads the new values for
//this client to the db
package app.client;

import app.extjs.helpers.ClientHelper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import app.security.*;

public final class ClientUpdateRates extends Action {

    public ClientUpdateRates() {
        //System.out.println("ClientUpdateRates constructor*****************************************");
    }
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

        //which client to update from hidden value in form
        String clientId = request.getParameter("clientViewId");
        //System.out.println("Client id of client updaterate***********************" + clientId);

        //get the client to be updated from db
        Client c = null;
        if (clientId != null && clientId.trim() != "") {
            c = ClientService.getInstance().getSingleClient(Integer.parseInt(clientId));
        } else {
            c = new Client();
            c.setClientId(0);
        }
        //Client c = ClientService.getInstance().getSingleClient(Integer.valueOf(clientId));
        //System.out.println("client id value of client update rate*********************************");



        request.setAttribute("clientViewId", c);


        ClientHelper.updateClientLinRates(c, request.getParameter("linguisticsJSON"));
        ClientHelper.updateClientDtpRates(c, request.getParameter("dtpJSON"));
        ClientHelper.updateClientEngRates(c, request.getParameter("engJSON"));
        ClientHelper.updateClientOthRates(c, request.getParameter("othJSON"));

        c.setScaleRep(request.getParameter("scaleRep"));
        c.setScalePerfect(request.getParameter("scalePerfect"));
        c.setScaleContext(request.getParameter("scaleContext"));
        c.setScale100(request.getParameter("scale100"));
        c.setScale95(request.getParameter("scale95"));
        c.setScale85(request.getParameter("scale85"));
        try{c.setScale8599(request.getParameter("scale8599"));}catch(Exception e){}
        c.setScale75(request.getParameter("scale75"));
        c.setScaleNew(request.getParameter("scaleNew"));
        try{c.setScaleNew4(request.getParameter("scaleNew4"));}catch(Exception e){}
        
        c.setScaleRep_team(new Double(request.getParameter("scaleRepTeam")));
        c.setScalePerfect_team(new Double(request.getParameter("scalePerfectTeam")));
        c.setScaleContext_team(new Double(request.getParameter("scaleContextTeam")));
        c.setScale100_team(new Double(request.getParameter("scale100Team")));
        c.setScale95_team(new Double(request.getParameter("scale95Team")));
        c.setScale85_team(new Double(request.getParameter("scale85Team")));
        try{c.setScale8599_team(new Double(request.getParameter("scale8599Team")));}catch(Exception e){}
        c.setScale75_team(new Double(request.getParameter("scale75Team")));
        c.setScaleNew_team(new Double(request.getParameter("scaleNewTeam")));
        try{c.setScaleNew4_team(new Double(request.getParameter("scaleNew4Team")));}catch(Exception e){}
        
//        c.setCcurrency(request.getParameter("currency"));
//        if(request.getParameter("mainSource")!=null){
//                          if(!request.getParameter("mainSource").equalsIgnoreCase("")){
//            c.setMainSource(request.getParameter("mainSource"));}}
//        if(request.getParameter("mainTarget")!=null){
//              if(!request.getParameter("mainTarget").equalsIgnoreCase("")){
//            c.setMainTarget(request.getParameter("mainTarget"));}}

//        if (request.getParameter("rowsSubmit").equals("4")) {
//            c.setScaleDefault(true);
//        } else {
//            c.setScaleDefault(false);
//        }
        ClientService.getInstance().updateClient(c);

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
