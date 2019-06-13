/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.quote;

import org.apache.struts.action.Action;
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
public class ClientQuoteViewUnpublishAction extends Action{

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
        Session session = ConnectionFactory.getInstance().getSession();
        boolean t = true;
        Transaction tx = null;


        try {
            tx = session.beginTransaction();


            //	String quoteId = null;
            String quoteId = request.getParameter("quoteViewId");


            //System.out.println("quoteViewId" + quoteId + "         " + request.getParameter("quoteViewId"));

            //default client to first if not in request or cookie
            if (quoteId == null || "null".equals(quoteId)) {
                List results1 = QuoteService.getInstance().getQuoteList();
                Quote1 first = (Quote1) results1.get(0);
                quoteId = String.valueOf(first.getQuote1Id());
            }

            Integer id = Integer.valueOf(quoteId);


            Quote1 newQ = QuoteService.getInstance().getSingleQuote(id);

            //  String quoteId = StandardCode.getInstance().getCookie("quoteAddId", request.getCookies());
            String quoteViewId = quoteId;//request.getParameter("quoteViewId");

            //System.out.println("quoteViewId" + quoteId + "         " + request.getParameter("quoteViewId") + "        " + quoteViewId);
            Integer CQuote = Integer.parseInt(quoteViewId);
            // Integer CQuote=1410;
            //System.out.println("getNewClientQuoteNumber" + CQuote);

            PreparedStatement pstmt = session.connection().prepareStatement("select * from client_quote where Quote_ID = " + CQuote);
            ResultSet rs = pstmt.executeQuery();
//Client_Quote cq=QuoteService.getInstance().get_SingleClientQuote(CQuote);
// ClientService.getInstance().getBlogList();
            //  Integer result = null;
            // //System.out.println("result+================"+rs+"================="+rs.getString("Type"));
            //System.out.println("                  working                     ");
            //String ty="lk";
            Quote1 q = QuoteService.getInstance().getSingleQuote(CQuote);
            String unit = "";
            Project p = q.getProject();
            while (rs.next()) {

                JSONObject jo = new JSONObject();
                Client_Quote cq = QuoteService.getInstance().getSingleClient_Quote(rs.getInt("id"));

                Product pr = app.client.ClientService.getInstance().getSingleProduct(cq.getProduct_ID());
                List sourcelang = QuoteService.getInstance().getSourceLang(newQ, cq.getId());

                //System.out.println("pid&&&&&&&&&&&&&&id" + cq.getProduct_ID() + "        " + cq.getProduct_ID());
                unit = rs.getString("volume") + " " + rs.getString("unit");
                jo.put("product", pr.getProduct());
                jo.put("category", pr.getCategory());


                jo.put("component", rs.getString("component"));
                jo.put("detail", rs.getString("Type"));
                jo.put("application", rs.getString("application"));

                jo.put("os", rs.getString("os"));
                jo.put("version", rs.getString("version"));
                jo.put("tos", rs.getString("target_os"));
                jo.put("tapplication", rs.getString("target_application"));
                jo.put("tversion", rs.getString("target_version"));


//for (int ii = 0; ii < sourcelang.size(); ii++) {
                //   SourceDoc sd = (SourceDoc) sourcelang.get(ii);
                //        sources += sd.getLanguage() + "_";
                //           if (i != deliverablelist.size() - 1) {
                //   deliverableJSArray += ",";
                //  }


                String targets = "";
                String sources = "";
                String task = "";
                int idTask = 0;
                HashMap alreadyAdded = new HashMap();


                //     for(Iterator iterSources = q.getSourceDocs().iterator(); iterSources.hasNext();) {
                for (int ii = 0; ii < sourcelang.size(); ii++) {
                    // SourceDoc sd = (SourceDoc) iterSources.next();
                    SourceDoc sd = (SourceDoc) sourcelang.get(ii);
                    sources += (String) LanguageAbs.getInstance().getAbs().get(sd.getLanguage());
                    //if(iterSources.hasNext()){
                    if (ii != sourcelang.size() - 1) {
                        sources += ", ";
                    }
                    //System.out.println("sources" + sources);
                    List targetlang = QuoteService.getInstance().getTargetLang(sd.getSourceDocId());
                    for (int jj = 0; jj < targetlang.size(); jj++) {
                        TargetDoc td = (TargetDoc) targetlang.get(jj);
                        // for(Iterator iterLintasks = td.getLinTasks().iterator(); iterLintasks.hasNext();) {
                        //   LinTask lt = (LinTask)iterLintasks.next();
                        //  if(!"".equalsIgnoreCase(lt.getTargetLanguage())){
                        String srcLang = td.getLanguage();
                        //  Integer tdid=td.getTargetDocId();

                        //SourceDoc ur = (Dropdown) oslist.get(i);
//getTargetLang

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
                    //System.out.println("targets" + targets);
                    if (targets.endsWith(", ")) {
                        targets = targets.substring(0, targets.length() - 2);
                    }

                }

                List linTaskList = QuoteService.getInstance().getLinTask(idTask);
                List engTaskList = QuoteService.getInstance().getEnggTask(idTask);
                List forTaskList = QuoteService.getInstance().getFormatTask(idTask);
                List otherTaskList = QuoteService.getInstance().getOtherTask(idTask);
                // for(Iterator iterLinTasks=td.getLinTasks().iterator();iterLinTasks.hasNext();){
                // LinTask lt=(LinTask).iterLinTasks.next();
                // }
                Integer tSize = linTaskList.size() + engTaskList.size() + forTaskList.size() + otherTaskList.size();
                Integer tt = 0;
                for (int ll = 0; ll < linTaskList.size(); ll++, tt++) {
                    LinTask lt = (LinTask) linTaskList.get(ll);
                    if (alreadyAdded.get(lt.getTaskName()) == null) {
                        task += lt.getTaskName();
                    }
                    //System.out.println("linTaskList" + task);

                    if (tt != tSize - 1) {
                        task += ", ";
                    }
                }
                for (int ll = 0; ll < engTaskList.size(); ll++, tt++) {
                    EngTask lt = (EngTask) engTaskList.get(ll);
                    if (alreadyAdded.get(lt.getTaskName()) == null) {
                        task += lt.getTaskName();
                    }
                    //System.out.println("linTaskList" + task);

                    if (tt != tSize - 1) {
                        task += ", ";
                    }
                }
                for (int ll = 0; ll < forTaskList.size(); ll++, tt++) {
                    DtpTask lt = (DtpTask) forTaskList.get(ll);
                    if (alreadyAdded.get(lt.getTaskName()) == null) {
                        task += lt.getTaskName();
                    }
                    //System.out.println("linTaskList" + task);

                    if (tt != tSize - 1) {
                        task += ", ";
                    }
                }
                for (int ll = 0; ll < otherTaskList.size(); ll++, tt++) {
                    OthTask lt = (OthTask) otherTaskList.get(ll);
                    if (alreadyAdded.get(lt.getTaskName()) == null) {
                        task += lt.getTaskName();
                    }
                    //System.out.println("linTaskList" + task);

                    if (tt != tSize - 1) {
                        task += ", ";
                    }
                }

                jo.put("SourceLangs", sources);
                jo.put("TargetLangs", targets);



                //   String task = "";







                //System.out.println("Type.............." + rs.getString("Type"));
                results.add(jo);
            }
            tx.commit();
        } catch (Exception e) {
            try {
                tx.rollback(); //error
            } catch (HibernateException he) {
                System.err.println("Hibernate Exception" + e.getMessage());
                throw new RuntimeException(e);
            }
            System.err.println("Hibernate Exception" + e.getMessage());
            throw new RuntimeException(e);
        } /*
         * Regardless of whether the above processing resulted in an Exception
         * or proceeded normally, we want to close the Hibernate session.  When
         * closing the session, we must allow for the possibility of a Hibernate
         * Exception.
         *
         */ finally {
            if (session != null) {
                try {

                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Hibernate Exception" + e.getMessage());
                    throw new RuntimeException(e);
                }

            }
        }
        //ClientService.getInstance().updateClientLocation(Integer.parseInt(id),request.getParameter("locationJSON"));

        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        // //System.out.println(actResponse.toXML());
        PrintWriter out = response.getWriter();

        out.println(new JSONArray(results.toArray()));
        //request.setAttribute("blogJSArray",new JSONArray(results.toArray()));

        out.flush();

        // Forward control to the specified success URI
        return (null);

    }
}
