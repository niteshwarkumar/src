/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.quote;

import app.extjs.vo.Product;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import app.client.*;
import app.project.Inspection;
import app.project.Project;
import app.project.ProjectService;
import app.standardCode.StandardCode;
import app.user.*;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.struts.validator.DynaValidatorForm;

public class QuoteWizard2 extends Action {

    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        String jsonProducts = request.getParameter("clientQuoteJSON");

        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));

        DynaValidatorForm qvg = (DynaValidatorForm) form;

        String productname = "";
        String productdesc = "";
        Integer quoteId;

        //     if (jsonProducts != null && !"".equals(jsonProducts)) {
        //  JSONArray products = new JSONArray(jsonProducts);
        Integer clientId = 0;
        if (u.getuserType() != null && u.getuserType().equalsIgnoreCase("client")) {

            clientId = u.getId_client();
        } else {
            //  String quoteId = StandardCode.getInstance().getCookie("quoteAddId", request.getCookies())
            clientId = Integer.parseInt(StandardCode.getInstance().getCookie("clientViewId", request.getCookies()));
            //requestClientId=getA
            String requestClientId = request.getParameter("clientViewId");
            //System.out.println("Client Id" + clientId + "          " + requestClientId);
        }

        Client c = ClientService.getInstance().getSingleClient(clientId);
        //System.out.println("u.getId_client()" + clientId);
        Quote1 newQ = null;
        //System.out.println(request.getSession(false).getAttribute("quoteViewId").toString());

        if (Integer.parseInt(request.getSession(false).getAttribute("quoteViewId").toString()) == 0) {
            //System.out.println("new quote main enter hua hai");
            quoteId = QuoteService.getInstance().addQuoteWithNewProject(c, "000000");
            //System.out.println("new quote                      " + quoteId);
            newQ = QuoteService.getInstance().getSingleQuote(quoteId);
            if (request.getSession(false).getAttribute("username") != null) {
                newQ.setEnteredById((String) request.getSession(false).getAttribute("username"));
                newQ.setEnteredByTS(new Date());
                newQ.setPublish(false);
            }
            if (!StandardCode.getInstance().noNull(c.getAutoPmFee())) {
                newQ.setManualPMFee("1");
            }
            newQ.setPmPercent("" + StandardCode.getInstance().noNull(c.getPmfeePercentage()));

            QuoteService.getInstance().updateQuote(newQ, (String) request.getSession(false).getAttribute("username"));

        } else {
            quoteId = Integer.parseInt(request.getSession(false).getAttribute("quoteViewId").toString());
            newQ = QuoteService.getInstance().getSingleQuote(quoteId);
            //System.out.println("newQ" + newQ.getNumber());
            // quoteId = Integer.parseInt(request.getSession(false).getAttribute("quoteViewId").toString());
        }

        //System.out.println("quoteIdquoteIdquoteIdquoteIdquoteId" + quoteId);
        Project p = QuoteService.getInstance().getSingleQuote(quoteId).getProject();
        Project pLazyLoad = ProjectService.getInstance().getSingleProject(p.getProjectId());

        Project proj = ProjectService.getInstance().getSingleProject(newQ.getProject().getProjectId());

//        String[] defaultInspections = ProjectService.getInstance().getDefaultInspectionOptions();
//        String[] inspections = ProjectService.getInstance().getInspectionOptions();

        int k = 0;
//        for (int i = 0; i < inspections.length; i++) {
//            Inspection i2 = new Inspection();
//            if (k < 7 && defaultInspections[k].equals(inspections[i])) { //if default inspection
//                i2.setInDefault(true);
//                i2.setApplicable(true);
//                k++;
//            } else {
//                i2.setInDefault(false);
//                i2.setApplicable(false);
//            }
//            i2.setOrderNum(new Integer(i + 1));
//            i2.setMilestone(inspections[i]);
//
//            //upload to db
//            ProjectService.getInstance().addInspectionWithProject(pLazyLoad, i2);
//        }
        //END add Inspection list to this project

        //add this quote id to cookies; this will remember the last quote
        response.addCookie(StandardCode.getInstance().setCookie("quoteAddId", String.valueOf(quoteId)));
        response.addCookie(StandardCode.getInstance().setCookie("quoteViewId", String.valueOf(quoteId)));
        request.setAttribute("quoteViewId", String.valueOf(quoteId));
        //place client into cookie; this will be used later in wizard
        //System.out.println("quoteAddId" + StandardCode.getInstance().getCookie("quoteViewId", request.getCookies()));
        response.addCookie(StandardCode.getInstance().setCookie("quoteAddClientId", String.valueOf(c.getClientId())));

        request.setAttribute("quoteAddClientId", String.valueOf(c.getClientId()));
        HttpSession session = request.getSession(false);
        session.setAttribute("quoteViewId", String.valueOf(quoteId));

        //System.out.println("Quote View ID kitna hai" + String.valueOf(quoteId));
        //System.out.println("cookie value  " + request.getParameter("quoteViewId"));
        //System.out.println("cookie value  " + request.getAttribute("quoteViewId"));
        //  //System.out.println("cookie value  " + StandardCode.getInstance().getCookie("quoteAddId", request.getCookies()));
        List QC = QuoteService.getInstance().getSingleClientQuote(newQ.getQuote1Id());
        try {
            Product prod = ClientService.getSingleProduct(clientId, request.getParameter("product"));

            Client_Quote pr = null;

            pr = QuoteService.getInstance().getSingleClientQuoteFromProduct(quoteId, prod.getID_Product());
            if (pr == null) {
                pr = new Client_Quote();
            }

            pr.setQuote_ID(quoteId);
            pr.setID_Client(clientId);

            String mComponent = "";
            String[] mainComponent = request.getParameterValues("mainComponent");
            if (mainComponent != null) {
                for (int j = 0; j < mainComponent.length; j++) {
                    mComponent += mainComponent[j];
                    if (j < mainComponent.length - 1) {
                        mComponent += " ,";
                    }

                }
            }

            pr.setProduct_ID(prod.getID_Product());
//            //System.out.println(request.getParameter("product"));
            pr.setType((String) (qvg.get("Type")));
            String Type = (String) (qvg.get("Type"));
            pr.setTypeOfText(request.getParameter("tot"));

            pr.setComponent(mComponent);

            pr.setOs(" ");
            pr.setApplication(" ");
            pr.setVersion(" ");
            // pr.setComponent(j.getString("component"));

            productname += request.getParameter("product") + "";
            productdesc += Type + "";
            //  Integer quoteId = QuoteService.getInstance().NaddQuoteWithNewProject(c, "000000");

            QuoteService.getInstance().saveClientQuote(pr);
//            Client_Quote ccq = QuoteService.getInstance().getSingleClientQuoteFromProduct(quoteId, prod.getID_Product());

            //System.out.println("Client Quote Idddddd " + ccq.getId());
            session = request.getSession(false);
            session.setAttribute("ClientQuoteId", String.valueOf(pr.getId()));
            //System.out.println("Client Quote Idddddd " + ccq.getId());

            //  }
        } catch (Exception e) {
            //System.out.println("Errrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");

        }
        //  //System.out.println("Client Quote Idddddd "+ccq.getId());
        try {
            if (proj.getProduct() == null) {

                proj.setProduct(productname);
                proj.setProductDescription(productdesc);
            } else {
                Quote1 q = QuoteService.getInstance().getSingleQuoteFromProject(proj.getProjectId());
                List clList = QuoteService.getInstance().getClient_Quote(newQ.getQuote1Id());
                for (int i = 0; i < clList.size(); i++) {
                    Client_Quote clQ = (Client_Quote) clList.get(i);
                    Product product = ClientService.getSingleProduct(clQ.getProduct_ID());
                    productname += product.getProduct();
                    if (i < clList.size() - 1) {
                        productname += ",";
                    }
                }

                //  productname=proj.getProduct()+","+productname;
                //  productdesc=proj.getProductDescription()+","+productdesc;
                proj.setProduct(productname);
                proj.setProductDescription(productdesc);
            }
        } catch (Exception e) {
            proj.setProduct(productname);
            proj.setProductDescription(productdesc);

        }
        proj.setStatus("active");
        proj.setPmPercent("10.00");
        //  p.setProduct(request.getParameter("product"));
        //  p.setProductDescription(request.getParameter("productDescription") );
        if (u.getuserType() != null && u.getuserType().equalsIgnoreCase("client")) {
            proj.setPm(c.getProject_mngr());

        } else {

            proj.setPm(request.getSession(false).getAttribute("projectManager").toString());

        }
        try {
            String contactId = request.getSession(false).getAttribute("contactId").toString();
            ClientContact cc = ClientService.getInstance().getSingleClientContact(Integer.valueOf(contactId));
            ProjectService.getInstance().linkProjectClientContact(proj, cc);
        } catch (Exception e) {
        }
        proj.setAe(c.getSales_rep());
        //contactId
        //  User u = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
        proj.setCreatedBy(u.getFirstName() + " " + u.getLastName());
        proj.setCreatedDate(new Date(System.currentTimeMillis()));

        Integer projectId = ProjectService.getInstance().addProjectWithClient(proj, c);

        //insert into db, building link between contact and project
        //Set PM RATE
        ClientLanguagePair[] clp = null;
        if (c != null) {
            if (c.getClientLanguagePairs() != null) {
                clp = (ClientLanguagePair[]) c.getClientLanguagePairs().toArray(new ClientLanguagePair[0]);

            }
        }
        if (clp != null) {

            for (int z = 0; z < clp.length; z++) {

                if (clp[z].getTask() != null && clp[z].getTask().equals("PM - Percentage")) {
                    proj.setPmPercent(clp[z].getRate());
                    break;
                }
            }
        }

        ProjectService.getInstance().updateProject(proj);

        return (mapping.findForward("Success"));

    }
}
