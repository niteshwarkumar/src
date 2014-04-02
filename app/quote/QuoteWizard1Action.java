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

/**
 *
 * @author Niteshwar
 */
public class QuoteWizard1Action extends Action {

    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        DynaValidatorForm qvg = (DynaValidatorForm) form;

        User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));

        Client c = ClientService.getInstance().getSingleClient(u.getID_Client());
        Quote1 newQ = null;
        Client_Quote cQouote = null;


        Integer quoteId, clientQuoteId;
        if (Integer.parseInt(request.getSession(false).getAttribute("quoteViewId").toString()) == 0) {

            quoteId = QuoteService.getInstance().addQuoteWithNewProject(c, "000000");

            newQ = QuoteService.getInstance().getSingleQuote(quoteId);
            if (request.getSession(false).getAttribute("username") != null) {
                newQ.setEnteredById((String) request.getSession(false).getAttribute("username"));
                newQ.setEnteredByTS(new Date());
                newQ.setPublish(false);

            }
            QuoteService.getInstance().updateQuote(newQ);


        } else {
            quoteId = Integer.parseInt(request.getSession(false).getAttribute("quoteViewId").toString());
            newQ = QuoteService.getInstance().getSingleQuote(quoteId);

        }
        if (Integer.parseInt(request.getSession(false).getAttribute("clientQuoteId").toString()) == 0) {
            cQouote = new Client_Quote();
            cQouote.setQuote_ID(quoteId);
            cQouote.setID_Client(u.getID_Client());

            QuoteService.getInstance().updateClientQuote(cQouote);

        } else {
            clientQuoteId = Integer.parseInt(request.getSession(false).getAttribute("clientQuoteId").toString());
            cQouote = QuoteService.getInstance().getSingleClient_Quote(clientQuoteId);
        }

        Project p = QuoteService.getInstance().getSingleQuote(quoteId).getProject();
        Project pLazyLoad = ProjectService.getInstance().getSingleProject(p.getProjectId());

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


        response.addCookie(StandardCode.getInstance().setCookie("quoteViewId", String.valueOf(quoteId)));
        request.setAttribute("quoteViewId", String.valueOf(quoteId));
        response.addCookie(StandardCode.getInstance().setCookie("quoteAddClientId", String.valueOf(c.getClientId())));
        request.setAttribute("quoteAddClientId", String.valueOf(c.getClientId()));

        HttpSession session = request.getSession(false);
        session.setAttribute("quoteViewId", String.valueOf(quoteId));
        session.setAttribute("clientQuoteViewId", String.valueOf(cQouote.getId()));
        session.setAttribute("clientQuoteId", String.valueOf(cQouote.getId()));

        Product pro=ClientService.getInstance().getSingleProduct(u.getID_Client(), (String) (qvg.get("pName")));
        cQouote.setProduct_ID(pro.getID_Product());
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

        cQouote.setComponent(mComponent);
        cQouote.setType((String) (qvg.get("Type")));
        cQouote.setProductName((String) (qvg.get("pName")));
        cQouote.setProductText((String) qvg.get("productFreeText"));

        QuoteService.getInstance().updateClientQuote(cQouote);


        String productname = "";
        String productdesc = "";
        List clList = QuoteService.getInstance().getClient_Quote(newQ.getQuote1Id());
        for (int i = 0; i < clList.size(); i++) {
            Client_Quote clQ = (Client_Quote) clList.get(i);
            Product product = ClientService.getSingleProduct(clQ.getProduct_ID());
            productname += product.getProduct();
            productdesc += clQ.getType();
            if (i < clList.size() - 1) {
                productname += ",";
                productdesc += ",";
            }
        }
        p.setProduct(productname);
        p.setProductDescription(productdesc);
        p.setStatus("active");
        p.setPmPercent("10.00");
        p.setPm(c.getProject_mngr());
        p.setAe(c.getSales_rep());
        p.setCreatedBy(u.getFirstName() + " " + u.getLastName());
        p.setCreatedDate(new Date(System.currentTimeMillis()));


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
                    p.setPmPercent(clp[z].getRate());
                    break;
                }
            }
        }

        ProjectService.getInstance().updateProject(p);

        return (mapping.findForward("Success"));

    }
}
