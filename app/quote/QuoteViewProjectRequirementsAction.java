//QuoteViewProjectRequirementsAction.java gets the
//project requirements and places them into a form

package app.quote;

import app.client.ClientService;
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
import app.project.*;
import app.standardCode.*;
import app.extjs.vo.Product;
import app.security.*;


public final class QuoteViewProjectRequirementsAction extends Action {


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
	
        //START get id of current quote from either request, attribute, or cookie 
        //id of quote from request
	String quoteId = null;
	quoteId = request.getParameter("quoteViewId");
        
        //check attribute in request
        if(quoteId == null) {
            quoteId = (String) request.getAttribute("quoteViewId");
        }
        
        //id of quote from cookie
        if(quoteId == null) {            
            quoteId = StandardCode.getInstance().getCookie("quoteViewId", request.getCookies());
        }
      
        
        Integer id = Integer.valueOf(quoteId);
        String product="";
        String type="";
        String typeOfText="";
        String component="";
        String projectRequirements="";
        String ProjectDescription="";
        String Task="";
        String prod;

        //END get id of current quote from either request, attribute, or cookie               
        
        //get quote to and then its sources
        Quote1 q = QuoteService.getInstance().getSingleQuote(id); 
        Project p = q.getProject();
        List cQuote=QuoteService.getInstance().getClient_Quote(id);
        for(int i=0;i<cQuote.size();i++){
        Client_Quote cq=(Client_Quote) cQuote.get(i);
        Product pr=ClientService.getSingleProduct(cq.getProduct_ID());
        prod=pr.getProduct();
        product+=prod;
        if(cQuote.size()==1){
            type+=cq.getType();
            typeOfText+=StandardCode.getInstance().noNull(cq.getTypeOfText());
            component+=cq.getComponent();
            projectRequirements+=cq.getRequirement();
            ProjectDescription+=cq.getInstruction();
            try {
                 if(p.getProjectDescription().length()>0)
                 type=p.getProjectDescription();
            } catch (Exception e) {
            }
       
        Task+=cq.getClientTask()+"\n"+cq.getOtherTask()+"\n";
        }else{
            type+=prod+"-"+cq.getType();
            typeOfText+=prod+"-"+StandardCode.getInstance().noNull(cq.getTypeOfText());
            component+=prod+"-"+cq.getComponent();
            projectRequirements+=prod+"-"+cq.getRequirement();
            ProjectDescription+=prod+"-"+cq.getInstruction();
            try {
                 if(p.getProjectDescription().length()>0)
                 type=prod+(p.getProjectDescription().replaceAll(p.getProjectDescription().split("-")[0], ""));
            } catch (Exception e) {
            }
       
        Task+=prod+"-"+cq.getClientTask()+"\n"+cq.getOtherTask()+"\n";
        }
        
            

        if(i < cQuote.size()-1 ){
         product +=",";
         type+="\n";
         component+="\n";
         typeOfText+="\n";
         ProjectDescription+="\n";
         projectRequirements+="\n";
        }
        }if(product.equalsIgnoreCase("")){
        try{
            product=p.getProduct();
        }catch(Exception e){}
        type="---Old Quote(No Data)---";
        component="---Old Quote(No Data)---";
        typeOfText="---Old Quote(No Data)---";
        ProjectDescription="---Old Quote(No Data)---";
        projectRequirements="---Old Quote(No Data)---";
       }

        
        //values of the project
        DynaValidatorForm qvpr = (DynaValidatorForm) form;
        qvpr.set("product",  product);
        qvpr.set("component", component);
        qvpr.set("productDescription", type);
        qvpr.set("typeOfText", typeOfText);
        qvpr.set("taskDetail", Task);
        qvpr.set("productUnits", q.getProject().getProductUnits());
        qvpr.set("projectRequirements", projectRequirements);
        qvpr.set("projectDescription",  ProjectDescription);
        qvpr.set("projectManager", q.getProject().getPm());
        qvpr.set("beforeWorkTurn", q.getProject().getBeforeWorkTurn());
        qvpr.set("beforeWorkTurnUnits", q.getProject().getBeforeWorkTurnUnits());
        qvpr.set("afterWorkTurn", q.getProject().getAfterWorkTurn());
        qvpr.set("afterWorkTurnUnits", q.getProject().getAfterWorkTurnUnits());
        qvpr.set("afterWorkTurnReason", q.getProject().getAfterWorkTurnReason());
        if(q.getProject().getReqProjDelDate()!=null)
        qvpr.set("reqProjDelDate", q.getProject().getReqProjDelDate().toString());
        qvpr.set("sourceOs", p.getSourceOS());
        qvpr.set("sourceApplication", p.getSourceApplication());
        qvpr.set("sourceVersion", p.getSourceVersion());
        qvpr.set("sourceTechNotes", p.getSourceTechNotes());
        qvpr.set("deliverableOs", p.getDeliverableOS());
        qvpr.set("deliverableApplication", p.getDeliverableApplication());
        qvpr.set("deliverableVersion", p.getDeliverableVersion());
        qvpr.set("deliverableTechNotes", p.getDeliverableTechNotes());
        qvpr.set("productFeeUnit", p.getFee());
        qvpr.set("orderReqNum", p.getOrderReqNum());
        if(p.getContact()!=null)
            qvpr.set("clientContact", ""+p.getContact().getClientContactId());
        else
            qvpr.set("clientContact", "");
        
        if(p.getCareTaker()!=null)
            qvpr.set("caretaker", ""+p.getCareTaker().getClientContactId());
        else
            qvpr.set("caretaker", "");
        
        //place quote into attribute for display
        request.setAttribute("quote", q);
        
        if(p.getDeliverableSame() != null && p.getDeliverableSame().equals("true")) {
            request.setAttribute("deliverableSame", "checked");
        }


        //place sources into request for display
         List sdList = QuoteService.getInstance().getSourceLang1(q);
        SourceDoc[] sources = new SourceDoc[sdList.size()];
       
        
//        Iterator iter = q.getSourceDocs().iterator();
        for(int j = 0; j < sources.length; j++) {
            SourceDoc sd = (SourceDoc) sdList.get(j);
            sources[j] = sd;
        }
      

        request.setAttribute("sourceArray", sources); 
        
        //add this quote id to cookies; this will remember the last quote
        response.addCookie(StandardCode.getInstance().setCookie("quoteViewId", quoteId));
        
        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("quoteViewTab", "Project Requirements"));
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
