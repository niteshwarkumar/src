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
import org.json.JSONArray;
import org.json.JSONObject;
import app.client.*;
import app.project.Inspection;
import app.project.Project;
import app.project.ProjectService;
import app.standardCode.StandardCode;
import app.user.*;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;

public class ClientQuoteAdd1Action extends Action {

    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {


        String jsonProducts = request.getParameter("clientQuoteJSON");

        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));


        String productname = "";
        String productdesc = "";
        Integer quoteId;


        if (jsonProducts != null && !"".equals(jsonProducts)) {
            JSONArray products = new JSONArray(jsonProducts);
            Integer clientId = 0;
            if (u.getuserType() != null && u.getuserType().equalsIgnoreCase("client")) {

                clientId = u.getId_client();
            } else {
                //  String quoteId = StandardCode.getInstance().getCookie("quoteAddId", request.getCookies())
                clientId = Integer.parseInt(StandardCode.getInstance().getCookie("clientViewId", request.getCookies()));
                //requestClientId=getA
                String requestClientId = request.getParameter("clientViewId");
                System.out.println("Client Id" + clientId + "          " + requestClientId);
            }

            Client c = ClientService.getInstance().getSingleClient(clientId);
            System.out.println("u.getId_client()" + clientId);
            Quote1 newQ = null;
            System.out.println(request.getSession(false).getAttribute("quoteViewId").toString());



            if (Integer.parseInt(request.getSession(false).getAttribute("quoteViewId").toString()) == 0) {
                System.out.println("new quote main enter hua hai");
                quoteId = QuoteService.getInstance().addQuoteWithNewProject(c, "000000");
                System.out.println("new quote                      " + quoteId);
                newQ = QuoteService.getInstance().getSingleQuote(quoteId);
                if (request.getSession(false).getAttribute("username") != null) {
                    newQ.setEnteredById((String) request.getSession(false).getAttribute("username"));
                    newQ.setEnteredByTS(new Date());
                    newQ.setPublish(false);
                }
                if (c.getPmfeePercentage() > 0.0) {
                    newQ.setPmPercent("" + c.getPmfeePercentage());
                }
                QuoteService.getInstance().updateQuote(newQ);

            } else {
                quoteId = Integer.parseInt(request.getSession(false).getAttribute("quoteViewId").toString());
                newQ = QuoteService.getInstance().getSingleQuote(quoteId);
//                System.out.println("newQ" + newQ.getNumber());
                // quoteId = Integer.parseInt(request.getSession(false).getAttribute("quoteViewId").toString());
            }


//            System.out.println("quoteIdquoteIdquoteIdquoteIdquoteId" + quoteId);

            Project p = QuoteService.getInstance().getSingleQuote(quoteId).getProject();
            Project pLazyLoad = ProjectService.getInstance().getSingleProject(p.getProjectId());


            Project proj = ProjectService.getInstance().getSingleProject(newQ.getProject().getProjectId());

            String[] defaultInspections = ProjectService.getInstance().getDefaultInspectionOptions();
            String[] inspections = ProjectService.getInstance().getInspectionOptions();

            int k = 0;
            for (int i = 0; i < inspections.length; i++) {
                Inspection i2 = new Inspection();
                if (k < 7 && defaultInspections[k].equals(inspections[i])) { //if default inspection
                    i2.setInDefault(true);
                    i2.setApplicable(true);
                    k++;
                } else {
                    i2.setInDefault(false);
                    i2.setApplicable(false);
                }
                i2.setOrderNum(new Integer(i + 1));
                i2.setMilestone(inspections[i]);
                //upload to db
                ProjectService.getInstance().addInspectionWithProject(pLazyLoad, i2);
            }
            //END add Inspection list to this project

            //add this quote id to cookies; this will remember the last quote
            response.addCookie(StandardCode.getInstance().setCookie("quoteAddId", String.valueOf(quoteId)));
            response.addCookie(StandardCode.getInstance().setCookie("quoteViewId", String.valueOf(quoteId)));
            request.setAttribute("quoteViewId", String.valueOf(quoteId));
            //place client into cookie; this will be used later in wizard
            System.out.println("quoteAddId" + StandardCode.getInstance().getCookie("quoteViewId", request.getCookies()));
            response.addCookie(StandardCode.getInstance().setCookie("quoteAddClientId", String.valueOf(c.getClientId())));

            request.setAttribute("quoteAddClientId", String.valueOf(c.getClientId()));
            HttpSession session = request.getSession(false);
            session.setAttribute("quoteViewId", String.valueOf(quoteId));

            System.out.println("Quote View ID kitna hai" + String.valueOf(quoteId));

            System.out.println("cookie value  " + request.getParameter("quoteViewId"));
            System.out.println("cookie value  " + request.getAttribute("quoteViewId"));
            //  System.out.println("cookie value  " + StandardCode.getInstance().getCookie("quoteAddId", request.getCookies()));






            List QC = QuoteService.getInstance().getSingleClientQuote(newQ.getQuote1Id());
            try {

                for (int i = 0; i < products.length(); i++) {

                    Client_Quote pr1 = (Client_Quote) QC.get(i);
                    Client_Quote pr = QuoteService.getInstance().getSingleClient_Quote(pr1.getId());

                    JSONObject j = (JSONObject) products.get(i);

                    pr.setQuote_ID(quoteId);
                    pr.setID_Client(clientId);
                    Product prod = ClientService.getSingleProduct(clientId, j.getString("product"));
                    //   System.out.println("Product ...................."+(j.getString("product"))+"====="+p.getProduct() +"======id== "+prod.getID_Product());

                    pr.setMedical(j.getString("medical"));
                    pr.setProduct_ID(prod.getID_Product());
                    pr.setType(j.getString("detail"));
                    pr.setComponent(j.getString("component"));
                    pr.setDeliverable(" ");
                    pr.setOs(" ");
                    pr.setUnit(" ");
                    pr.setVolume(" ");
                    pr.setApplication(" ");
                    pr.setVersion(" ");
                    // pr.setComponent(j.getString("component"));

                    productname += j.getString("product") + ",";
                    productdesc += j.getString("description") + ",";
                    //  Integer quoteId = QuoteService.getInstance().NaddQuoteWithNewProject(c, "000000");

                    QuoteService.getInstance().saveClientQuote(pr);


                }
            } catch (Exception e) {

                Client_Quote pr = new Client_Quote();
                System.out.println("Product Length  " + products.length());
                for (int i = 0; i < products.length(); i++) {

                    JSONObject j = (JSONObject) products.get(i);

                    pr.setQuote_ID(quoteId);
                    pr.setID_Client(clientId);
                    Product prod = ClientService.getSingleProduct(clientId, j.getString("product"));
                    //   System.out.println("Product ...................."+(j.getString("product"))+"====="+p.getProduct() +"======id== "+prod.getID_Product());
                    try {
                        pr.setMedical(StandardCode.getInstance().noNull(j.getString("medical")));
                    } catch (Exception e1) {
                    }
                    pr.setProduct_ID(prod.getID_Product());
                    pr.setType(j.getString("detail"));
                    try {
                        pr.setComponent(j.getString("component"));
                    } catch (Exception e1) {
                    }
                    pr.setDeliverable(" ");
                    pr.setOs(" ");
                    pr.setUnit(" ");
                    pr.setVolume(" ");
                    pr.setApplication(" ");
                    pr.setVersion(" ");
                    // pr.setComponent(j.getString("component"));

                    productname += j.getString("product") + ",";
                    productdesc += j.getString("description") + ",";
                    //  Integer quoteId = QuoteService.getInstance().NaddQuoteWithNewProject(c, "000000");

                    QuoteService.getInstance().saveClientQuote1(pr);


                }
            }
            proj.setProduct(productname);
            // proj.setProductDescription(productdesc);







            proj.setStatus("active");
            proj.setPmPercent("10.00");
            //  p.setProduct(request.getParameter("product"));
            //  p.setProductDescription(request.getParameter("productDescription") );
            if (u.getuserType() != null && u.getuserType().equalsIgnoreCase("client")) {
                proj.setPm(c.getProject_mngr());


            } else {

                proj.setPm(request.getSession(false).getAttribute("projectManager").toString());
                String contactId = request.getSession(false).getAttribute("contactId").toString();
                ClientContact cc = ClientService.getInstance().getSingleClientContact(Integer.valueOf(contactId));
                ProjectService.getInstance().linkProjectClientContact(proj, cc);

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

            //update project to db
            ///    ProjectService.getInstance().updateProject(p);










            System.out.println("productnameproductnameproductname" + productname);

            ProjectService.getInstance().updateProject(proj);

            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + proj.getProduct());
            System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" + proj.getProjectRequirements());
            System.out.println("cccccccccccccccccccccccccccccccc" + proj.getProjectId());
        }



        return (mapping.findForward("Success"));

    }
}
