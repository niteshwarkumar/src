/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import java.util.*;
import app.security.*;
import org.json.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author Niteshwar
 */
public class QMSAuditSaveAction extends Action {

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
        String type = request.getParameter("type");
        String jsonAudit = request.getParameter("auditJSON");
        String by ="";

        if (jsonAudit != null && !"".equals(jsonAudit)) {
            JSONArray qmsAudit = new JSONArray(jsonAudit);
            for (int i = 0; i < qmsAudit.length(); i++) {
                JSONObject j = (JSONObject) qmsAudit.get(i);
                QMSAudit audit = null;
                try {
                    audit = QMSService.getInstance().getSingleQmsAudit(Integer.parseInt(j.getString("id")));
                } catch (Exception e) {
                    audit = new QMSAudit();
                }
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                audit.setCompany(j.getString("company"));
                audit.setLeadauditor(j.getString("leadAuditor"));
                audit.setOffice(j.getString("office"));
                try {
                    java.sql.Date d2 = new java.sql.Date(df.parse(j.getString("scheduled")).getTime());
                    audit.setScheduled(d2);
                } catch (Exception e) {
                }
                audit.setStandard(j.getString("standard"));
                audit.setVersion(j.getString("version"));
                try {
                    java.sql.Date d3 = new java.sql.Date(df.parse(j.getString("actual")).getTime());
                    audit.setActual(d3);
                } catch (Exception e) {
                }
try{
                if (type == null) {
                    if (j.getString("by").equalsIgnoreCase("client")) {
                        audit.setType("external");
                        audit.setAuditby("client");
                    } else if (j.getString("by").equalsIgnoreCase("registrar")) {
                        audit.setType("external");
                        audit.setAuditby("registrar");
                    } else {
                        audit.setType("internal");
                        try {
                            audit.setAuditby(j.getString("by"));
                        } catch (Exception e) {
                        }
                    }
                } else if (type.equalsIgnoreCase("internal")) {
                    audit.setType("internal");
                    try {
                        audit.setAuditby(j.getString("by"));
                    } catch (Exception e) {
                    }
                    try {
                        audit.setNonconformities(j.getString("nonconformities"));
                    } catch (Exception e) {
                    }
                    try {
                        audit.setResult(j.getString("result"));
                    } catch (Exception e) {
                    }

                }


                else if (type.equalsIgnoreCase("extclient") || j.getString("by").equalsIgnoreCase("client")) {
                    audit.setType("external");
                    audit.setAuditby("client");
                    try {
                        audit.setNonconformities(j.getString("nonconformities"));
                    } catch (Exception e) {
                    }
                    try {
                        audit.setResult(j.getString("result"));
                    } catch (Exception e) {
                    }
                }
                else if (type.equalsIgnoreCase("extregistrar") || j.getString("by").equalsIgnoreCase("registrar")) {
                    audit.setType("external");
                    audit.setAuditby("registrar");
                    try {
                        audit.setNonconformities(j.getString("nonconformities"));
                    } catch (Exception e) {
                    }
                    try {
                        audit.setResult(j.getString("result"));
                    } catch (Exception e) {
                    }
                }

                }catch(Exception e){

                 if (type.equalsIgnoreCase("extclient")) {
                    audit.setType("external");
                    audit.setAuditby("client");
                    try {
                        audit.setNonconformities(j.getString("nonconformities"));
                    } catch (Exception e1) {
                    }
                    try {
                        audit.setResult(j.getString("result"));
                    } catch (Exception e1) {
                    }
                }
                else if (type.equalsIgnoreCase("extregistrar")) {
                    audit.setType("external");
                    audit.setAuditby("registrar");
                    try {
                        audit.setNonconformities(j.getString("nonconformities"));
                    } catch (Exception e1) {
                    }
                    try {
                        audit.setResult(j.getString("result"));
                    } catch (Exception e1) {
                    }
                }

                }
                QMSServiceAddUpdate.getInstance().addQMSAudit(audit);

            }
String atype= "";
by="and by='by'\"\"";
List auditList = QMSService.getInstance().getAuditSchedule();
try{

 if(type.equalsIgnoreCase("extregistrar")){atype="external";by=" and auditby='registrar'";}
 if(type.equalsIgnoreCase("extclient")){atype="external";by=" and auditby='client'\"\"";}
 if(type.equalsIgnoreCase("internal")){atype="internal";by="";}

            auditList = QMSService.getInstance().getAuditScheduleByType(atype, by);

              }catch(Exception e){

            }

            for (int i = 0; i < auditList.size(); i++) {
                QMSAudit aud = (QMSAudit) auditList.get(i);
                Integer flag = 0;
                for (int ii = 0; ii < qmsAudit.length(); ii++) {
                    JSONObject j = (JSONObject) qmsAudit.get(ii);
                    try {

                        if (aud.getAuditno() == Integer.parseInt(j.getString("id"))) {
                            flag = 1;
                        }

                    } catch (Exception e) {
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    QMSServiceDelete.getInstance().deleteAudit(aud);
                }

            }
        }
        if(type==null){return (mapping.findForward("Success"));}
            else if(type.equalsIgnoreCase("internal")){return (mapping.findForward("internal"));}
            else if(type.equalsIgnoreCase("extclient")){return (mapping.findForward("extclient"));}
            else if(type.equalsIgnoreCase("extregistrar")){return (mapping.findForward("extregistrar"));}
            else{return (mapping.findForward("Success"));}
    }
}


