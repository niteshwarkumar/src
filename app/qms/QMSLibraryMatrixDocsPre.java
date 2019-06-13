/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import app.security.SecurityService;
import app.standardCode.StandardCode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 *
 * @author Niteshwar
 */
public class QMSLibraryMatrixDocsPre extends Action {

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
            HttpServletResponse response) throws Exception {
        // save errors
        ActionMessages errors = new ActionMessages();

        //START check for login (security)
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }
        //END check for login (security)

//        //PRIVS check that admin user is viewing this page
//        if (!StandardCode.getInstance().checkPrivStringArray((String[]) request.getSession(false).getAttribute("userPrivs"), "admin")) {
//            return (mapping.findForward("accessDenied"));
//        }//END PRIVS check that admin user is viewing this page
        DynaValidatorForm qvg = (DynaValidatorForm) form;
        String allCheck = (String) qvg.get("allCheck");
        String pmCheck = (String) qvg.get("pmCheck");
        String engCheck = (String) qvg.get("engCheck");
        String dtpCheck = (String) qvg.get("dtpCheck");
        String vmCheck = (String) qvg.get("vmCheck");
        String smCheck = (String) qvg.get("smCheck");
        String manCheck = (String) qvg.get("manCheck");
        String accCheck = (String) qvg.get("accCheck");
        String hrCheck = (String) qvg.get("hrCheck");
        String itCheck = (String) qvg.get("itCheck");

        String quality = (String) qvg.get("quality1");
        String procedure = (String) qvg.get("procedure1");
        String guidance = (String) qvg.get("guidance1");
        String practice = (String) qvg.get("practice1");
        String form1 = (String) qvg.get("form1");
        String document = (String) qvg.get("document1");
        String chart = (String) qvg.get("chart1");
        String figure = (String) qvg.get("figure1");
        String template = (String) qvg.get("template1");

        String controlled = (String) qvg.get("controlled");
        String supporting = (String) qvg.get("supporting");

        String fullQuery = "";
        String query = "";
        String query1 = "";
        String query2 = "";
        List<String> isoKeyHeader = new ArrayList();
        List<String> isoKey = new ArrayList();
        String[] isoStandard = request.getParameterValues("isoStandard");
        String[] isoKeyHeaderArr = request.getParameterValues("isoKeyHeader");
        String[] isoKeyArr = request.getParameterValues("isoKey");
        boolean isoFilter = true;
        try{
        isoFilter = (boolean)request.getAttribute("isoHeader");
        }catch(Exception e){
            isoFilter = true;
        }
        //request.getAttributeNames()
        if(isoKeyHeaderArr!=null) isoKeyHeader =  Arrays.asList(isoKeyHeaderArr);
        if(isoKeyArr!=null) isoKey =  Arrays.asList(isoKeyArr);
        if(isoFilter){
        request.setAttribute("isoKeyHeader", isoKeyHeader); 
        request.setAttribute("isoKey", isoKey);
      
        if(isoStandard!=null){
            String queryIso = "";
        for(String iso : isoStandard){
            queryIso+=iso+"@";
        }
            queryIso += "iso";
            query+=queryIso;
        }}
        if (allCheck.equalsIgnoreCase("on")) {
            //query = "";
            request.setAttribute("query", query);
            qvg.set("allCheck", "on");
            return (mapping.findForward("Success"));
        } else {
            fullQuery = query;
            query ="";
            qvg.set("allCheck", "");
        }
        if (pmCheck.equalsIgnoreCase("on")) {
            if (query.equalsIgnoreCase("")) {
//                query += " and";
            } else {
                query += " or";
            }
            query += " pmCheck=True";
            qvg.set("pmCheck", "on");
        } else {
            qvg.set("pmCheck", "");
        }
        if (engCheck.equalsIgnoreCase("on")) {
            if (query.equalsIgnoreCase("")) {
//                query += " and";
            } else {
                query += " or";
            }
            query += " engCheck=True";
            qvg.set("engCheck", "on");
        } else {
            qvg.set("engCheck", "");
        }
        if (dtpCheck.equalsIgnoreCase("on")) {
            if (query.equalsIgnoreCase("")) {
//                query += " and";
            } else {
                query += " or";
            }
            query += " dtpCheck=True";
            qvg.set("dtpCheck", "on");
        } else {
            qvg.set("dtpCheck", "");
        }
        if (vmCheck.equalsIgnoreCase("on")) {
            if (query.equalsIgnoreCase("")) {
//                query += " and";
            } else {
                query += " or";
            }
            query += " vmCheck=True";
            qvg.set("vmCheck", "on");
        } else {
            qvg.set("vmCheck", "");
        }
        if (smCheck.equalsIgnoreCase("on")) {
            if (query.equalsIgnoreCase("")) {
//                query += " and";
            } else {
                query += " or";
            }
            query += " smCheck=True";
            qvg.set("smCheck", "on");
        } else {
            qvg.set("smCheck", "");
        }
        if (manCheck.equalsIgnoreCase("on")) {
            if (query.equalsIgnoreCase("")) {
               // query += " and";
            } else {
                query += " or";
            }
            query += " manCheck=True";
            qvg.set("manCheck", "on");
        } else {
            qvg.set("manCheck", "");
        }
        if (accCheck.equalsIgnoreCase("on")) {
            if (query.equalsIgnoreCase("")) {
//                query += " and";
            } else {
                query += " or";
            }
            query += " accCheck=True";
            qvg.set("accCheck", "on");
        } else {
            qvg.set("accCheck", "");
        }
        if (hrCheck.equalsIgnoreCase("on")) {
            if (query.equalsIgnoreCase("")) {
//                query += " and";
            } else {
                query += " or";
            }
            query += " hrCheck=True";
            qvg.set("hrCheck", "on");
        } else {
            qvg.set("hrCheck", "");
        }
        if (itCheck.equalsIgnoreCase("on")) {
            if (query.equalsIgnoreCase("")) {
//                query += " and";
            } else {
                query += " or";
            }
            query += " itCheck=True";
            qvg.set("itCheck", "on");
        } else {
            qvg.set("itCheck", "");
        }

        //////Category
        if (quality.equalsIgnoreCase("on")) {
            if (query1.equalsIgnoreCase("")) {
            } else {
                query1 += " or";
            }
            query1 += " category=\\'Quality Manual\\'";
            qvg.set("quality1", "on");
        } else {
            qvg.set("quality1", "");
        }
        if (procedure.equalsIgnoreCase("on")) {
            if (query1.equalsIgnoreCase("")) {
            } else {
                query1 += " or";
            }
            query1 += " category=\\'Procedure\\'";
            qvg.set("procedure1", "on");
        } else {
            qvg.set("procedure1", "");
        }
        if (guidance.equalsIgnoreCase("on")) {
            if (query1.equalsIgnoreCase("")) {
            } else {
                query1 += " or";
            }
            query1 += " category=\\'Guidance\\'";
            qvg.set("guidance1", "on");
        } else {
            qvg.set("guidance1", "");
        }
        if (practice.equalsIgnoreCase("on")) {
            if (query1.equalsIgnoreCase("")) {
            } else {
                query1 += " or";
            }
            query1 += " category=\\'Best Practice\\\'";
            qvg.set("practice1", "on");
        } else {
            qvg.set("practice1", "");
        }
        if (form1.equalsIgnoreCase("on")) {
            if (query1.equalsIgnoreCase("")) {
            } else {
                query1 += " or";
            }
            query1 += " category=\\'Form\\'";
            qvg.set("form1", "on");
        } else {
            qvg.set("form1", "");
        }
        if (document.equalsIgnoreCase("on")) {
            if (query1.equalsIgnoreCase("")) {
            } else {
                query1 += " or";
            }
            query1 += " category=\\'Document\\'";
            qvg.set("document1", "on");
        } else {
            qvg.set("document1", "");
        }
        if (chart.equalsIgnoreCase("on")) {
            if (query1.equalsIgnoreCase("")) {
            } else {
                query1 += " or";
            }
            query1 += " category=\\'Chart\\'";
            qvg.set("chart1", "on");
        } else {
            qvg.set("chart1", "");
        }
        if (figure.equalsIgnoreCase("on")) {
            if (query1.equalsIgnoreCase("")) {
            } else {
                query1 += " or";
            }
            query1 += " category=\\'Figure\\'";
            qvg.set("figure1", "on");
        } else {
            qvg.set("figure1", "");
        }
        if (template.equalsIgnoreCase("on")) {
            if (query1.equalsIgnoreCase("")) {
            } else {
                query1 += " or";
            }
            query1 += " category=\\'Template\\'";
            qvg.set("template1", "on");
        } else {
            qvg.set("template1", "");
        }

/////Type

        if (controlled.equalsIgnoreCase("on")) {
            if (query2.equalsIgnoreCase("")) {
            } else {
                query2 += " or";
            }
            query2 += " type=\\'C\\'";
            qvg.set("controlled", "on");
        } else {
            qvg.set("controlled", "");
        }
        if (supporting.equalsIgnoreCase("on")) {
            if (query2.equalsIgnoreCase("")) {
            } else {
                query2 += " or";
            }
            query2 += " type=\\'S\\'";
            qvg.set("supporting", "on");
        } else {
            qvg.set("supporting", "");
        }



        if (fullQuery.equalsIgnoreCase("")) {
            if (query.equalsIgnoreCase("")) {
            } else {
                fullQuery = " and ( ";
            }
        }

        fullQuery += query;

        if (fullQuery.equalsIgnoreCase("")) {
        } else {
            if (query.equalsIgnoreCase("")) {
            } else {
                fullQuery += " ) ";
            }
        }


//// query 1
        if (fullQuery.equalsIgnoreCase("")) {
            if (query1.equalsIgnoreCase("")) {
            } else {
                fullQuery = " and (";
            }
        } else {
            if (query1.equalsIgnoreCase("")) {
            } else {
                fullQuery += " and ( ";
            }
        }
        fullQuery += query1;

        if (fullQuery.equalsIgnoreCase("")) {
            if (query1.equalsIgnoreCase("")) {
            } else {
            }
        } else {
            if (query1.equalsIgnoreCase("")) {
            } else {
                fullQuery += " ) ";
            }
        }
        //query2

        if (fullQuery.equalsIgnoreCase("")) {
            if (query2.equalsIgnoreCase("")) {
            } else {
                fullQuery = " and ( ";
            }
        } else {
            if (query2.equalsIgnoreCase("")) {
            } else {
                fullQuery += " and ( ";
            }
        }
        fullQuery += query2;

        if (fullQuery.equalsIgnoreCase("")) {
            if (query2.equalsIgnoreCase("")) {
            } else {
            }
        } else {
            if (query2.equalsIgnoreCase("")) {
            } else {
                fullQuery += " ) ";
            }
        }

        

        request.setAttribute("query", fullQuery);

        return (mapping.findForward("Success"));
    }
}
