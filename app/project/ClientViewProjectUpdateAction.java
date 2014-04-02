/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.project;

import org.apache.struts.action.Action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;
import app.security.SecurityService;
import app.standardCode.DateService;
import app.user.*;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpSession;
import net.sf.hibernate.collection.Set;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

/**
 *
 * @author Niteshwar
 */
public class ClientViewProjectUpdateAction extends Action {

    @SuppressWarnings({"static-access", "empty-statement"})
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        org.apache.struts.util.MessageResources messages = getResources(request);

        // save errors
        ActionMessages errors = new ActionMessages();

        //START check for login (security)
        if (!SecurityService.getInstance().checkForLogin(request.getSession(false))) {
            return (mapping.findForward("welcome"));
        }

        try {


            String projectId = request.getParameter("projectId");
            Project project = ProjectService.getInstance().getSingleProject(Integer.parseInt(projectId));

            Set sources = (Set) project.getSourceDocs();
            for (Iterator sourceIter = sources.iterator(); sourceIter.hasNext();) {
                SourceDoc sd = (SourceDoc) sourceIter.next();
                int count = 0;
                String icrTaskId = "";
                String icrSent = "";
                String icrRecieve = "";
                String icrComment = "";
                String icrTaskIdText = "";
                String icrSentText = "";
                String icrRecieveText = "";
                String icrCommentText = "";
                for (int targetIter =0;targetIter< sd.getTargetDocs().size(); targetIter++) {
                  //   for (Iterator targetIter = sd.getTargetDocs().iterator(); targetIter.hasNext();) {
                    try {
                        icrTaskIdText = "targetId" + count;
                        icrSentText = "sentDate" + count;
                        icrRecieveText = "recieveDate" + count;
                        icrCommentText = "comment" + count;
                        icrTaskId = request.getParameter(icrTaskIdText);
                        icrSent = request.getParameter(icrSentText);
                        icrRecieve = request.getParameter(icrRecieveText);
                        icrComment = request.getParameter(icrCommentText);

                        Client_Icr cIcr = ProjectService.getInstance().getSingleClientIcr(Integer.parseInt(icrTaskId));
                        if (cIcr == null) {
                            cIcr = new Client_Icr();
                        }
                        try {
                            cIcr.setID_TargetDoc(Integer.parseInt(icrTaskId));
                        } catch (Exception e) {
                        }
                        try {
                            cIcr.setComment(icrComment);
                        } catch (Exception e) {
                        }
                        try {
                            if(icrSent.length()>0 )
                            cIcr.setIcr_sent(DateService.getInstance().convertDate(icrSent).getTime());
                        } catch (Exception e) {
                        }
                        try {
                            if(icrRecieve.length()>0 )
                            cIcr.setIcr_recieve(DateService.getInstance().convertDate(icrRecieve).getTime());
                        } catch (Exception e) {
                        }


                        ProjectService.getInstance().updateClientIcr(cIcr);
                        count++;
                    } catch (Exception e) {
                    }

                }


            }
        } catch (Exception e) {
        }



        return (mapping.findForward("Success"));


    }
}
