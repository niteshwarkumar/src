/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.admin;

import app.client.Client;
import app.client.ClientService;
import app.extjs.helpers.HrHelper;
import app.extjs.helpers.ProjectHelper;
import app.extjs.helpers.QuoteHelper;
import app.project.Project;
import app.project.ProjectService;
import app.quote.Quote1;
import app.quote.QuoteService;
import app.resource.Resource;
import app.resource.ResourceService;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.DynaValidatorForm;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author niteshwar
 */
public class GeneralSearchAction extends Action {

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

        DynaValidatorForm ps = (DynaValidatorForm) form;

        String generalSearchClient = (String) ps.get("generalSearchClient");
        String generalSearchTeam = (String) ps.get("generalSearchTeam");
        String generalSearchCriteria = (String) ps.get("generalSearchCriteria");
        String generalSearchProject = (String) ps.get("generalSearchProject");
        String generalSearchQuote = (String) ps.get("generalSearchQuote");

        boolean searchProject = false;
        boolean searchQuote = false;
//        boolean searchProject= false;
//        boolean searchProject= false;

        JSONArray searchResult = new JSONArray();
        String searchParam = request.getParameter("searchBox");
        String status = "";
        String companyName = "";
        String projectNumber = "";
        String product = "";
        String productDescription = "";
        String notes = "";
        String srcLang = "";
        String targetLang = "";
        String pm = "";
        String ae = "";
        String sales = "";
        String company = "";
        String firstName = "";
        String lastName = "";
        String resume = "";
        if (generalSearchCriteria.equalsIgnoreCase("Number")) {
            projectNumber = searchParam;
        }
        if (generalSearchCriteria.equalsIgnoreCase("PM")) {
            pm = searchParam;
        }
        if (generalSearchCriteria.equalsIgnoreCase("AE")) {
            ae = searchParam;
        }
        if (generalSearchCriteria.equalsIgnoreCase("Sales")) {
            sales = searchParam;
        }
        if (generalSearchCriteria.equalsIgnoreCase("Client") || generalSearchCriteria.equalsIgnoreCase("Client1")) {
            companyName = searchParam;
        }

        if (generalSearchCriteria.equalsIgnoreCase("Product")) {
            product = searchParam;
        }
        if (generalSearchCriteria.equalsIgnoreCase("ProductDesc")) {
            productDescription = searchParam;
        }
        if (generalSearchCriteria.equalsIgnoreCase("Notes")) {
            notes = searchParam;
        }
        if (generalSearchCriteria.equalsIgnoreCase("Company")) {
            company = searchParam;
        }
        if (generalSearchCriteria.equalsIgnoreCase("FirstName")) {
            firstName = searchParam;
        }
        if (generalSearchCriteria.equalsIgnoreCase("LastName")) {
            lastName = searchParam;
        }
        if (generalSearchCriteria.equalsIgnoreCase("Resume")) {
            resume = searchParam;
        }
        if (generalSearchCriteria.equalsIgnoreCase("Notes1")) {
            notes = searchParam;
        }

        JSONArray teamSearchResult = new JSONArray();
        JSONArray clientSearchResult = new JSONArray();
        String sumQuery = (status + companyName + projectNumber
                + product + productDescription + notes + srcLang
                + targetLang + pm + ae + sales + company + firstName
                + lastName + resume).trim();
        if (sumQuery.length() > 0) {
            List pNum = new ArrayList();
            if (generalSearchProject.equalsIgnoreCase("on") && !searchParam.equalsIgnoreCase("")) {
                List projectList = ProjectService.getInstance().getProjectSearch(status, companyName, projectNumber, product, productDescription, notes, srcLang, targetLang, pm, ae, sales);
//        ProjectService.getInstance().getProjectSearch(searchParam, searchParam, searchParam, searchParam, searchParam, searchParam, searchParam, searchParam, searchParam, searchParam, searchParam)
                if (projectList != null) {
                    for (int i = 0; i < projectList.size(); i++) {
                        Project p = (Project) projectList.get(i);
                        if (!pNum.contains(p.getProjectId())) {
                            searchResult.put(ProjectHelper.ProjectToJson3(p));
                        }
                        pNum.add(p.getProjectId());
                    }
                }
            }
            if (generalSearchQuote.equalsIgnoreCase("on") && !searchParam.equalsIgnoreCase("")) {
                List quoteList = QuoteService.getInstance().getQuoteSearch(status, companyName, projectNumber, product, productDescription, notes, srcLang, targetLang, pm, ae, sales);
//        ProjectService.getInstance().getProjectSearch(searchParam, searchParam, searchParam, searchParam, searchParam, searchParam, searchParam, searchParam, searchParam, searchParam, searchParam)
                if (quoteList != null) {
                    for (int i = 0; i < quoteList.size(); i++) {
                        Quote1 q = (Quote1) quoteList.get(i);
                        if (!pNum.contains(q.getProject().getProjectId())) {
                            searchResult.put(QuoteHelper.QuoteToJson3(q));
                        }
                        pNum.add(q.getProject().getProjectId());

                    }
                }
            }

            if (generalSearchClient.equalsIgnoreCase("on") && !searchParam.equalsIgnoreCase("")) {

                List clientList = ClientService.getInstance().getClientSearch(status, companyName,firstName, lastName, pm, ae, sales, notes);
//        ProjectService.getInstance().getProjectSearch(searchParam, searchParam, searchParam, searchParam, searchParam, searchParam, searchParam, searchParam, searchParam, searchParam, searchParam)
                if (clientList != null) {
                    for (int i = 0; i < clientList.size(); i++) {
                        Client c = (Client) clientList.get(i);
                        JSONObject clientObj = new JSONObject();
                        clientObj.put("Company", "<a href=\"javascript:parent.openClientWindowReverse('" + c.getCompany_name() + "','" + c.getClientId() + "')\">" + c.getCompany_name() + "</a>");

                        clientObj.put("code", c.getCompany_code());
                        clientObj.put("currency", c.getCcurrency());
                        clientObj.put("email", c.getEmail_address());
                        clientObj.put("notes", c.getNote());
                        clientObj.put("Industry", c.getIndustry().getDescription());
                        clientObj.put("ae", c.getSales_rep());
                        clientObj.put("sales", c.getSales());
                        clientObj.put("status", c.getStatus());
                        clientObj.put("ext", c.getWorkPhoneEx());
                        clientObj.put("tel", c.getMain_telephone_numb());
                        clientObj.put("SRC", c.getMainSource());
                        clientObj.put("TGT", c.getMainTarget());
                        clientObj.put("DTP", c.getMain_dtp());
                        clientObj.put("Engineer", c.getMain_engineer());

                        clientSearchResult.put(clientObj);

                    }
                }
            }

            if (generalSearchTeam.equalsIgnoreCase("on") && !searchParam.equalsIgnoreCase("")) {
                List teamList = ResourceService.getInstance().getResourceSearch(companyName, firstName, lastName, company, resume, notes);
//        ProjectService.getInstance().getProjectSearch(searchParam, searchParam, searchParam, searchParam, searchParam, searchParam, searchParam, searchParam, searchParam, searchParam, searchParam)
                if (teamList != null) {
                    for (int i = 0; i < teamList.size(); i++) {
                        Resource r = (Resource) teamList.get(i);
                        JSONObject teamObj = new JSONObject();
                        String name = "";
                        if (StandardCode.getInstance().noNull(r.getFirstName()).equalsIgnoreCase("")) {
                            name = r.getCompanyName();
                        } else {
                            name = r.getFirstName() + " " + r.getLastName();
                        }

                        teamObj.put("name", "<a " + HrHelper.LINK_STYLE + " href=\"javascript:parent.openVendorWindowReverse('" + HrHelper.jsSafe(name) + "','" + r.getResourceId() + "')\">" + name + "</a>");

                        teamObj.put("currency", StandardCode.getInstance().noNull(r.getCurrency()));
                        teamObj.put("email", StandardCode.getInstance().noNull(r.getEmail_address1()));
                        teamObj.put("tel", StandardCode.getInstance().noNull(r.getMain_telephone_numb1()));
                        teamObj.put("skype", StandardCode.getInstance().noNull(r.getSkypeId()));
                        // teamObj.put("Industry",r.getIndustries());
                        teamObj.put("pCount", r.getProjectCount());
                        teamObj.put("status", StandardCode.getInstance().noNull(r.getStatus()));
                        teamObj.put("nativeCountry", StandardCode.getInstance().noNull(r.getNativeCountry()));
                        teamObj.put("nativeLang", StandardCode.getInstance().noNull(r.getNativeLanguage()));
                        if (r.isDoNotUse()) {
                            teamObj.put("doNotUse", "Do Not Use");
                        }

                        teamSearchResult.put(teamObj);

                    }
                }
            }
        }
        request.setAttribute("teamSearchResult", teamSearchResult);
        request.setAttribute("searchResult", searchResult);
        request.setAttribute("clientSearchResult", clientSearchResult);
        request.setAttribute("searchBoxVal", searchParam);

        String resultSummary = "";
     
            if (searchResult.length() > 0) {
                resultSummary += "Quotes/Projects: " + searchResult.length();
            }


        if (clientSearchResult.length() > 0) {
            if (!resultSummary.equalsIgnoreCase("")) {
                resultSummary += " | ";
            }
            resultSummary += "Client: " + clientSearchResult.length();
        }

        if (teamSearchResult.length() > 0) {
            if (!resultSummary.equalsIgnoreCase("")) {
                resultSummary += " | ";
            }
            resultSummary += "Team: " + teamSearchResult.length();
        }

        if (!resultSummary.equalsIgnoreCase("")) {
            resultSummary = "Found " + resultSummary;
        }
        request.setAttribute("resultSummary", resultSummary);

        //END check for login (security)
// Forward control to the specified success URI
        return (mapping.findForward("Success"));

    }
}
