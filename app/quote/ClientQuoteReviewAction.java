/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

import app.db.*;


import app.extjs.global.LanguageAbs;
import app.extjs.vo.Product;
import app.project.*;
import app.project.Project;
import app.project.SourceDoc;
import app.project.TargetDoc;
import app.standardCode.StandardCode;
import app.user.User;
import app.user.UserService;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;

/**
 *
 * @author Niteshwar
 */
public class ClientQuoteReviewAction extends Action {

    private Log log =
            LogFactory.getLog("org.apache.struts.webapp.Example");

    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        // Extract attributes we will need


        MessageResources messages = getResources(request);

        // save errors
        ActionMessages errors = new ActionMessages();

        List results = new ArrayList();

            String quoteId = request.getParameter("quoteViewId");
    

            System.out.println("quoteViewId" + quoteId + "         " + request.getParameter("quoteViewId"));

            //default client to first if not in request or cookie
            if (quoteId == null || "null".equals(quoteId)) {
                List results1 = QuoteService.getInstance().getQuoteList();
                Quote1 first = (Quote1) results1.get(0);
                quoteId = String.valueOf(first.getQuote1Id());
            }

            Integer id = Integer.valueOf(quoteId);
            Quote1 newQ = QuoteService.getInstance().getSingleQuote(id);
            String quoteViewId = quoteId;//request.getParameter("quoteViewId");
            Integer CQuote = Integer.parseInt(quoteViewId);
            Client_Quote cq1=QuoteService.getInstance().get_SingleClientQuote(CQuote);
            List clientQuoteList=QuoteService.getInstance().getClient_Quote(CQuote);
            Quote1 q = QuoteService.getInstance().getSingleQuote(CQuote);
            String unit = "";
            Project p = q.getProject();
            for(int i=0;i<clientQuoteList.size();i++){
            Client_Quote clientQuote= (Client_Quote) clientQuoteList.get(i);

    if(clientQuote.getInstruction()==null&&clientQuote.getRequirement()==null&&clientQuoteList.size()-1>i){
            QuoteService.getInstance().deleteClientQuote(clientQuote.getId());
    }else{
                JSONObject jo = new JSONObject();

                Product pr = app.client.ClientService.getInstance().getSingleProduct(clientQuote.getProduct_ID());
                List sourcelang = QuoteService.getInstance().getSourceLang(newQ, clientQuote.getId());
                try {unit = clientQuote.getVolume() + " " + clientQuote.getUnit();} catch (Exception e) {}
                try {jo.put("product", pr.getProduct());} catch (Exception e) {}
                try {jo.put("category", pr.getCategory());} catch (Exception e) {}
                try {jo.put("component",clientQuote.getComponent() );} catch (Exception e) {}
                try {jo.put("detail", clientQuote.getType());} catch (Exception e) {}
                try {jo.put("application", clientQuote.getApplication());} catch (Exception e) {}
                try {jo.put("os", clientQuote.getOs());} catch (Exception e) {}
                try {jo.put("version",clientQuote.getVersion());} catch (Exception e) {}
                try {jo.put("tos", clientQuote.getTarget_os());} catch (Exception e) {}
                try {jo.put("tapplication", clientQuote.getTarget_application());} catch (Exception e) {}
                try {jo.put("tversion",clientQuote.getTarget_version());} catch (Exception e) {}
                try {jo.put("ToT", clientQuote.getTypeOfText());} catch (Exception e) {}




                String targets = "";
                String sources = "";
                String task = "";
                int idTask = 0;
                HashMap alreadyAdded = new HashMap();


                for (int ii = 0; ii < sourcelang.size(); ii++) {
                    SourceDoc sd = (SourceDoc) sourcelang.get(ii);
                    sources += (String) LanguageAbs.getInstance().getAbs().get(sd.getLanguage());
                    if (ii != sourcelang.size() - 1) {
                        sources += ", ";
                    }
                    System.out.println("sources" + sources);
                    List targetlang = QuoteService.getInstance().getTargetLang(sd.getSourceDocId());
                    for (int jj = 0; jj < targetlang.size(); jj++) {
                        TargetDoc td = (TargetDoc) targetlang.get(jj);
                        String srcLang = td.getLanguage();

                        String abr = (String) LanguageAbs.getInstance().getAbs().get(srcLang);
                        //Also prevent duplicates
                        if (abr != null && !"".equals(srcLang) && !"".equals(abr) && alreadyAdded.get(abr) == null) {
                            targets += abr + ", ";
                            alreadyAdded.put(abr, abr);
                        } else if (abr == null && !"".equals(srcLang) && !"".equals(abr) && alreadyAdded.get(srcLang) == null) {
                            targets += srcLang + ", ";
                            alreadyAdded.put(srcLang, srcLang);
                        }


                        idTask = td.getTargetDocId();


                        }
                    System.out.println("targets" + targets);
                    if (targets.endsWith(", ")) {
                        targets = targets.substring(0, targets.length() - 2);
                    }

                }

                List linTaskList = QuoteService.getInstance().getLinTask(idTask);
                List engTaskList = QuoteService.getInstance().getEnggTask(idTask);
                List forTaskList = QuoteService.getInstance().getFormatTask(idTask);
                List otherTaskList = QuoteService.getInstance().getOtherTask(idTask);

                Integer tSize = linTaskList.size() + engTaskList.size() + forTaskList.size() + otherTaskList.size();
                Integer tt = 0;
                for (int ll = 0; ll < linTaskList.size(); ll++, tt++) {
                    LinTask lt = (LinTask) linTaskList.get(ll);
                    if (alreadyAdded.get(lt.getTaskName()) == null) {
                        task += lt.getTaskName();
                    }
                    System.out.println("linTaskList" + task);

                    if (tt != tSize - 1) {
                        task += ", ";
                    }
                }
                for (int ll = 0; ll < engTaskList.size(); ll++, tt++) {
                    EngTask lt = (EngTask) engTaskList.get(ll);
                    if (alreadyAdded.get(lt.getTaskName()) == null) {
                        task += lt.getTaskName();
                    }
                    System.out.println("linTaskList" + task);

                    if (tt != tSize - 1) {
                        task += ", ";
                    }
                }
                for (int ll = 0; ll < forTaskList.size(); ll++, tt++) {
                    DtpTask lt = (DtpTask) forTaskList.get(ll);
                    if (alreadyAdded.get(lt.getTaskName()) == null) {
                        task += lt.getTaskName();
                    }
//                    System.out.println("linTaskList" + task);

                    if (tt != tSize - 1) {
                        task += ", ";
                    }
                }
                for (int ll = 0; ll < otherTaskList.size(); ll++, tt++) {
                    OthTask lt = (OthTask) otherTaskList.get(ll);
                    if (alreadyAdded.get(lt.getTaskName()) == null) {
                        task += lt.getTaskName();
                    }
//                    System.out.println("linTaskList" + task);

                    if (tt != tSize - 1) {
                        task += ", ";
                    }
                }

                jo.put("SourceLangs", sources);
                jo.put("TargetLangs", targets);
                jo.put("EditLanguage", "<input type='button' class='x-btn' value='Edit' onclick=\"javascript:editLanguage('" + clientQuote.getId() + "')\">");
                User u = UserService.getInstance().getSingleUser((String) request.getSession(false).getAttribute("username"));
                String userType = u.getuserType();
                if (userType == null || userType.equalsIgnoreCase("admin")) {

                    jo.put("Task", task);
                } else {

                     jo.put("Task", StandardCode.getInstance().noNull(clientQuote.getClientTask()) + "," + StandardCode.getInstance().noNull(clientQuote.getOtherTask()));
                }
                jo.put("EditTask", "<input type='button' class='x-btn' value='Edit Task' onclick=\"javascript:editTask('" + clientQuote.getId() + "')\">");

                results.add(jo);
           }
        }
//            tx.commit();


                    String productname="";
                    String productdesc="";

         Project proj = ProjectService.getInstance().getSingleProject(newQ.getProject().getProjectId());
        List clList=QuoteService.getInstance().getClient_Quote(newQ.getQuote1Id());
           for(int i=0;i<clList.size();i++){
           Client_Quote clQ=(Client_Quote)clList.get(i);
           Product product = ClientService.getSingleProduct(clQ.getProduct_ID());
           productname+=product.getProduct();
           productdesc+=product.getDescription();
         if(i<clList.size()-1){
        productname+=",";
        productdesc+=",";
    }
}

                proj.setProduct(productname);


ProjectService.getInstance().updateProject(proj);


        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        // System.out.println(actResponse.toXML());
        PrintWriter out = response.getWriter();

        out.println(new JSONArray(results.toArray()));
        //request.setAttribute("blogJSArray",new JSONArray(results.toArray()));

        out.flush();



        // Forward control to the specified success URI
        return (null);

    }
}
