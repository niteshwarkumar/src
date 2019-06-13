//ProjectEditUpdateAction.java gets updated project info
//(from General Info tab) from a form.  It then uploads the new values for
//this project to the db
package app.project;

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
import app.client.*;
import app.extjs.helpers.ProjectHelper;
import app.inteqa.InteqaService;
import app.security.*;
import app.standardCode.StandardCode;

public final class ProjectEditUpdateAction extends Action {

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

        //which project to update from hidden value in form
        String id = request.getParameter("projectViewId");

        //get the project to be updated from db
        Project p = ProjectService.getInstance().getSingleProject(Integer.valueOf(id));

        //values for update from form; change what is stored in db to these values
        DynaValidatorForm pvo = (DynaValidatorForm) form;

        String contactId = (String) pvo.get("contactId");
        String PM = (String) pvo.get("PM");
        String clientPO = (String) pvo.get("clientPO");
        String status = (String) (pvo.get("status"));
        String cancelled = request.getParameter("cancelled");
        String isTracked = request.getParameter("isTracked");
        String archiveId = request.getParameter("archiveId");
        String newProduct="";
        String tott="";
        String[] products=request.getParameterValues("product");
        String[] typeOfText=request.getParameterValues("typeOfText");
        String description =(String) pvo.get("description");
        String clientSatisfaction100=request.getParameter("clientSatisfaction100");
        String clientSatisfaction95=request.getParameter("clientSatisfaction95");
        String clientSatisfaction50=request.getParameter("clientSatisfaction50");
        String clientSatisfaction0=request.getParameter("clientSatisfaction0");
        String clientSatisfactionNull=request.getParameter("clientSatisfactionNull");
        String statusMessage = "";
        String caretaker =(String) pvo.get("caretaker");
        String orderReqNum =(String) pvo.get("orderReqNum");
          
         
        //update the project's values

        try{if(clientSatisfaction100.equalsIgnoreCase("100")){p.setClientSatisfaction(100.0);}}catch(Exception e){}
        try{if(clientSatisfaction95.equalsIgnoreCase("95")){p.setClientSatisfaction(95.0);}}catch(Exception e){}
        try{if(clientSatisfaction50.equalsIgnoreCase("75")){p.setClientSatisfaction(75.0);}}catch(Exception e){}
        try{if(clientSatisfaction0.equalsIgnoreCase("50")){p.setClientSatisfaction(50.0);}}catch(Exception e){}
        try{if(clientSatisfactionNull.equalsIgnoreCase("")){p.setClientSatisfaction(null);}}catch(Exception e){}
        
        p.setPm(PM);
        p.setClientPO(clientPO);
        p.setOrderReqNum(orderReqNum);
        if(products!=null){
            for(int i=0;i<products.length;i++){
            newProduct+=products[i];
            if(i<products.length-1){
            newProduct+=",";
            }}
        p.setProduct(newProduct);
        }

        if(typeOfText!=null){
            for(int i=0;i<typeOfText.length;i++){
            tott+=typeOfText[i];
            if(i<typeOfText.length-1){
            tott+=",";
            }}
        p.setTypeOfText(tott);
        }
        p.setClientAuthorization(request.getParameter("clientAuthorization"));
        String[] components = request.getParameterValues("component");
        if (components != null) {
            String compString = "";
            for (int i = 0; i < components.length; i++) {
                compString += components[i] + ",";
            }
            p.setComponent(compString);
        }

        if (status.equals("active")) {
            p.setCompleteDate(null);
            p.setStatus(status);
        } else if (!p.getStatus().equals("complete")) {
            if(status.equals("complete")){
//            check complete status
//                - if there is a vendor name in the Team tab, the Sent, Due and Received fields need to have dates. (if there is no vendor name, it’s ok if the date fields are empty)
//                - INTEQA > Delivery/Final: Verification Complete box should be checked.
//                - Forms > Accounting: total invoiced amount should match the total amount in RSF > Fee
                 //get this project's sources
        boolean flag =true;
        
        Set sources = p.getSourceDocs();
        
        //for each source
        for(Iterator sourceIter = sources.iterator(); sourceIter.hasNext();) {
            SourceDoc sd = (SourceDoc) sourceIter.next();
            
            //for each target of this source
            for(Iterator linTargetIter = sd.getTargetDocs().iterator(); linTargetIter.hasNext();) {
                TargetDoc td = (TargetDoc) linTargetIter.next();
                
                //for each lin Task of this target
                for(Iterator linTaskIter = td.getLinTasks().iterator(); linTaskIter.hasNext();) {
                   LinTask lt = (LinTask) linTaskIter.next();
                   if(flag){ 
                    if(null != lt.getPersonName()){
                        if(!"".equals(lt.getPersonName())){
                        if(null==lt.getSentDateDate()||null==lt.getDueDateDate()||null==lt.getReceivedDateDate()){
                        flag = false;
                        statusMessage="TEAM tab: There are dates missing for some vendors (Sent, Due or Received).";
                        }
                    }}}}
                    
                }
                
        } 
        if(flag){
        if(null != p.getTotalAmountInvoiced()){
            double amount = Double.parseDouble(StandardCode.getInstance().formatMoney(p.getProjectAmount()).replace(",", ""));
            if(p.getCompany().getCcurrency().equalsIgnoreCase("euro")){
                amount=p.getProjectAmount()/p.getEuroToUsdExchangeRate();
            }
        if(Math.abs(amount-Double.parseDouble(p.getTotalAmountInvoiced().replace(",", "")))>1){
            flag = false;statusMessage="FORMS > Accounting: Invoice is missing or total invoice amount is below total fee amount.";
        }}else{flag = false;statusMessage="FORMS > Accounting: Invoice is missing or total invoice amount is below total fee amount.";}}
        if(flag){if(!InteqaService.getInstance().isProjectBlocked(p.getProjectId())){
            flag = false;statusMessage="INTEQA > Delivery Final: There are missing verifications. Verification needs to take place prior to delivery to client.";
        }}
        if(flag){
        p.setCompleteDate(new Date());
        p.setStatus(status);
        }}else{
        p.setStatus(status);
        }
        }else{
        p.setStatus(status);
        }
     

        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
        p.setLastModifiedBy(u.getFirstName() + " " + u.getLastName());
        p.setLastModifiedDate(new Date(System.currentTimeMillis()));
        p.setArchiveId(archiveId);
        if (cancelled != null) {
            p.setCancelled("true");
        } else {
            p.setCancelled("false");
        }

        if (isTracked != null) {
            p.setIsTracked("Y");
            //If very first project for this client, create Master Access pwd
            if (p.getCompany().getTracker_password() == null) {
                String randomPwd = ProjectService.getInstance().generateRandomPassword(8);
                p.getCompany().setTracker_password(randomPwd);
                ClientService.getInstance().updateClient(p.getCompany());

            }
            //If very first project for this client contact, create Client Contact's pwd
            if (p.getContact().getTracker_password() == null) {
                String randomPwd = ProjectService.getInstance().generateRandomPassword(8);
                p.getContact().setTracker_password(randomPwd);
                ClientService.getInstance().clientContactUpdate(p.getContact());
            }
        } else {
            p.setIsTracked(null);
     
        }
        if(description!=null){
        p.setProductDescription(description);
        }
        //set updated values to db
        ProjectService.getInstance().updateProject(p);



        //update contact
        if (contactId != null && contactId.length() > 0) {
            ClientContact cc = ClientService.getInstance().getSingleClientContact(Integer.valueOf(contactId));
            //insert into db, building link between contact and p roject
            ProjectService.getInstance().linkProjectClientContact(p, cc);
        }
        
        //update contact
        if (caretaker!= null && caretaker.length() > 0) {
            ClientContact cc = ClientService.getInstance().getSingleClientContact(Integer.valueOf(caretaker));
            //insert into db, building link between contact and project
            ProjectService.getInstance().linkProjectCareTaker(p, cc);
        }
        ProjectHelper.updateLanguageCount(p);

     request.setAttribute("statusMessage", statusMessage);
        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
