//ClientViewResourceHistoryAction.java gets the current client from the
//db and places it in an attribute for display featuring resources

package app.client;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.ModuleException;
import org.apache.struts.util.MessageResources;
import org.apache.commons.beanutils.PropertyUtils;
import java.util.*;
import app.user.*;
import app.project.*;
import app.resource.*;
import app.db.*;
import app.workspace.*;
import app.security.*;
import app.standardCode.*;
import org.apache.struts.validator.*;


public final class ClientViewResourceHistoryAction extends Action {


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
        
        //get current client ID
        
        //id from request attribute
        String clientId = (String) request.getAttribute("clientViewId");
        
        if(clientId == null) {
            clientId = request.getParameter("clientViewId");	
        }
        //id from cookie
        if(clientId == null) {
            clientId = StandardCode.getInstance().getCookie("clientViewId", request.getCookies());	
        }
        
        Integer id = Integer.valueOf(clientId);
        
        //get the client from db
        Client c = ClientService.getInstance().getSingleClient(id);  
      /*  
        //get Tasks for display
        List linTasksDisplay = new ArrayList();
        List engTasksDisplay = new ArrayList();
        List dtpTasksDisplay = new ArrayList();
        List othTasksDisplay = new ArrayList();
        
        //iterate through tasks and accumulate by task type (lin, eng, dtp, oth)
        Set projects = c.getProjects();
        if(projects != null) {
            //for each project
            for(Iterator projectsIter = projects.iterator(); projectsIter.hasNext();) {
                Project p = (Project) projectsIter.next();
                p = ProjectService.getInstance().getSingleProject(p.getProjectId());
                
                Set sourceDocs = p.getSourceDocs();
                if(sourceDocs != null) {
                    for(Iterator sourceDocsIter = sourceDocs.iterator(); sourceDocsIter.hasNext();) {
                        SourceDoc sd = (SourceDoc) sourceDocsIter.next();
                        sd = ProjectService.getInstance().getSingleSourceDoc(sd.getSourceDocId());
                        
                        Set targetDocs = sd.getTargetDocs();
                        if(targetDocs != null) {
                            for(Iterator targetDocsIter = targetDocs.iterator(); targetDocsIter.hasNext();) {
                                TargetDoc td = (TargetDoc) targetDocsIter.next();
                                
                                Set linTasks = td.getLinTasks();
                                if(linTasks != null) {
                                    for(Iterator linTasksIter = linTasks.iterator(); linTasksIter.hasNext();) {
                                        LinTask lt = (LinTask) linTasksIter.next();
                                        if(lt.getPersonName() != null) {
                                            linTasksDisplay.add(lt);
                                        }
                                    }
                                }
                                
                                Set engTasks = td.getEngTasks();
                                if(engTasks != null) {
                                    for(Iterator engTasksIter = engTasks.iterator(); engTasksIter.hasNext();) {
                                        EngTask et = (EngTask) engTasksIter.next();
                                        if(et.getPersonName() != null) {
                                            engTasksDisplay.add(et);
                                        }
                                    }
                                }
                                
                                Set dtpTasks = td.getDtpTasks();
                                if(dtpTasks != null) {
                                    for(Iterator dtpTasksIter = dtpTasks.iterator(); dtpTasksIter.hasNext();) {
                                        DtpTask dt = (DtpTask) dtpTasksIter.next();
                                        if(dt.getPersonName() != null) {
                                            dtpTasksDisplay.add(dt);
                                        }
                                    }
                                }
                                
                                Set othTasks = td.getOthTasks();
                                if(othTasks != null) {
                                    for(Iterator othTasksIter = othTasks.iterator(); othTasksIter.hasNext();) {
                                        OthTask ot = (OthTask) othTasksIter.next();
                                        if(ot.getPersonName() != null) {
                                            othTasksDisplay.add(ot);
                                        }
                                    }
                                }                                
                            }
                        }
                    }
                }            
            }
        }
        
        //accumulate resource summary for display in ClientViewTeamDisplay object
        ArrayList cvtdList = new ArrayList();
        
        //START lin task
        if(linTasksDisplay != null) {
        for(ListIterator iter = linTasksDisplay.listIterator(); iter.hasNext();) {
            LinTask t = (LinTask) iter.next();
            //check if resource in list
            boolean inList = false;
            for(ListIterator li = cvtdList.listIterator(); li.hasNext();) {
                ClientViewTeamDisplay cvtd = (ClientViewTeamDisplay) li.next();
                if(cvtd.getResource().getResourceId().equals(Integer.valueOf(t.getPersonName()))) {
                    inList = true;
                    break;
                }
            }
            if(!inList) {
                ClientViewTeamDisplay cvtd = new ClientViewTeamDisplay();
                cvtd.setResource(ResourceService.getInstance().getSingleResourceNoLazy(Integer.valueOf(t.getPersonName())));
                cvtd.setClientProjects(1);
                ArrayList countedClientProjects = new ArrayList(); //string ("000001")
                countedClientProjects.add(t.getTargetDoc().getSourceDoc().getProject().getNumber());
                cvtd.setCountedClientProjects(countedClientProjects);
                if(t.getInternalDollarTotal() != null && t.getInternalDollarTotal().length() > 0) {
                    cvtd.setTotalForClient(t.getInternalDollarTotal());
                }
                else {
                    cvtd.setTotalForClient("0.00");
                }
                //set total projects
                int totalProjectsCounted = 0;
                ArrayList countedTotalProjects = new ArrayList(); //string ("000001")
                List totalLinTasks = ProjectService.getInstance().getResourceLinTasksClient(t.getPersonName());
                List totalEngTasks = ProjectService.getInstance().getResourceEngTasksClient(t.getPersonName());
                List totalDtpTasks = ProjectService.getInstance().getResourceDtpTasksClient(t.getPersonName());
                List totalOthTasks = ProjectService.getInstance().getResourceOthTasksClient(t.getPersonName());
                
                if(totalLinTasks != null) {
                    for(ListIterator linIter = totalLinTasks.listIterator(); linIter.hasNext();) {
                        LinTask lt = (LinTask) linIter.next();
                        //check if already counted
                        boolean projectCounted = false;
                        for(ListIterator linCountedIter = countedTotalProjects.listIterator(); linCountedIter.hasNext();) {
                            String lci = (String) linCountedIter.next();
                            if(lt.getTargetDoc().getSourceDoc().getProject().getNumber().equals(lci)) {
                                projectCounted = true;
                                break;
                            }
                        }
                        if(!projectCounted) {
                            countedTotalProjects.add(lt.getTargetDoc().getSourceDoc().getProject().getNumber());
                            totalProjectsCounted++;
                        }
                    }
                }
                if(totalEngTasks != null) {
                    for(ListIterator engIter = totalEngTasks.listIterator(); engIter.hasNext();) {
                        EngTask et = (EngTask) engIter.next();
                        //check if already counted
                        boolean projectCounted = false;
                        for(ListIterator linCountedIter = countedTotalProjects.listIterator(); linCountedIter.hasNext();) {
                            String lci = (String) linCountedIter.next();
                            if(et.getTargetDoc().getSourceDoc().getProject().getNumber().equals(lci)) {
                                projectCounted = true;
                                break;
                            }
                        }
                        if(!projectCounted) {
                            countedTotalProjects.add(et.getTargetDoc().getSourceDoc().getProject().getNumber());
                            totalProjectsCounted++;
                        }
                    }
                }
                if(totalDtpTasks != null) {
                    for(ListIterator dtpIter = totalDtpTasks.listIterator(); dtpIter.hasNext();) {
                        DtpTask dt = (DtpTask) dtpIter.next();
                        //check if already counted
                        boolean projectCounted = false;
                        for(ListIterator linCountedIter = countedTotalProjects.listIterator(); linCountedIter.hasNext();) {
                            String lci = (String) linCountedIter.next();
                            if(dt.getTargetDoc().getSourceDoc().getProject().getNumber().equals(lci)) {
                                projectCounted = true;
                                break;
                            }
                        }
                        if(!projectCounted) {
                            countedTotalProjects.add(dt.getTargetDoc().getSourceDoc().getProject().getNumber());
                            totalProjectsCounted++;
                        }
                    }
                }
                if(totalOthTasks != null) {
                    for(ListIterator othIter = totalOthTasks.listIterator(); othIter.hasNext();) {
                        OthTask ot = (OthTask) othIter.next();
                        //check if already counted
                        boolean projectCounted = false;
                        for(ListIterator linCountedIter = countedTotalProjects.listIterator(); linCountedIter.hasNext();) {
                            String lci = (String) linCountedIter.next();
                            if(ot.getTargetDoc().getSourceDoc().getProject().getNumber().equals(lci)) {
                                projectCounted = true;
                                break;
                            }
                        }
                        if(!projectCounted) {
                            countedTotalProjects.add(ot.getTargetDoc().getSourceDoc().getProject().getNumber());
                            totalProjectsCounted++;
                        }
                    }
                }
                cvtd.setTotalProjects(totalProjectsCounted);
                
                //add to list
                cvtdList.add(cvtd);
            }
            else if(inList) {
                //get this resouce from the cvtdList
                ClientViewTeamDisplay cvtd = new ClientViewTeamDisplay();
                for(ListIterator inIter = cvtdList.listIterator(); inIter.hasNext();) {
                    cvtd = (ClientViewTeamDisplay) inIter.next();
                    if(Integer.valueOf(t.getPersonName()).equals(cvtd.getResource().getResourceId())) {
                        break;
                    }
                }
                //update totalForClient
                if(t.getInternalDollarTotal() != null && t.getInternalDollarTotal().length() > 0) {
                    String existingString = cvtd.getTotalForClient().replaceAll(",","");
                    double existing = Double.valueOf(existingString).doubleValue();
                    String newString = t.getInternalDollarTotal().replaceAll(",","");
                    double newT = Double.valueOf(newString).doubleValue();
                    cvtd.setTotalForClient(StandardCode.getInstance().formatDouble(new Double(existing + newT)));
                }
                
                //check if clientProjects (count) should be updated
                boolean cpCounted = false;
                for(ListIterator cpIter = cvtd.getCountedClientProjects().listIterator(); cpIter.hasNext();) {
                    String cp = (String) cpIter.next();
                    if(cp.equals(t.getTargetDoc().getSourceDoc().getProject().getNumber())) {
                        cpCounted = true;
                    }
                }
                if(!cpCounted) {
                    cvtd.getCountedClientProjects().add(t.getTargetDoc().getSourceDoc().getProject().getNumber());
                    cvtd.setClientProjects(cvtd.getClientProjects() + 1);
                }
            }
        }  
        }
        //END lin task
        
        //START eng task
        if(engTasksDisplay != null) {
        for(ListIterator iter = engTasksDisplay.listIterator(); iter.hasNext();) {
            EngTask t = (EngTask) iter.next();
            //check if resource in list
            boolean inList = false;
            for(ListIterator li = cvtdList.listIterator(); li.hasNext();) {
                ClientViewTeamDisplay cvtd = (ClientViewTeamDisplay) li.next();
                if(cvtd.getResource().getResourceId().equals(Integer.valueOf(t.getPersonName()))) {
                    inList = true;
                    break;
                }
            }
            if(!inList) {
                ClientViewTeamDisplay cvtd = new ClientViewTeamDisplay();
                cvtd.setResource(ResourceService.getInstance().getSingleResourceNoLazy(Integer.valueOf(t.getPersonName())));
                cvtd.setClientProjects(1);
                ArrayList countedClientProjects = new ArrayList(); //string ("000001")
                countedClientProjects.add(t.getTargetDoc().getSourceDoc().getProject().getNumber());
                cvtd.setCountedClientProjects(countedClientProjects);
                if(t.getInternalDollarTotal() != null && t.getInternalDollarTotal().length() > 0) {
                    cvtd.setTotalForClient(t.getInternalDollarTotal());
                }
                else {
                    cvtd.setTotalForClient("0.00");
                }
                //set total projects
                int totalProjectsCounted = 0;
                ArrayList countedTotalProjects = new ArrayList(); //string ("000001")
                List totalLinTasks = ProjectService.getInstance().getResourceLinTasksClient(t.getPersonName());
                List totalEngTasks = ProjectService.getInstance().getResourceEngTasksClient(t.getPersonName());
                List totalDtpTasks = ProjectService.getInstance().getResourceDtpTasksClient(t.getPersonName());
                List totalOthTasks = ProjectService.getInstance().getResourceOthTasksClient(t.getPersonName());
                
                if(totalLinTasks != null) {
                    for(ListIterator linIter = totalLinTasks.listIterator(); linIter.hasNext();) {
                        LinTask lt = (LinTask) linIter.next();
                        //check if already counted
                        boolean projectCounted = false;
                        for(ListIterator linCountedIter = countedTotalProjects.listIterator(); linCountedIter.hasNext();) {
                            String lci = (String) linCountedIter.next();
                            if(lt.getTargetDoc().getSourceDoc().getProject().getNumber().equals(lci)) {
                                projectCounted = true;
                                break;
                            }
                        }
                        if(!projectCounted) {
                            countedTotalProjects.add(lt.getTargetDoc().getSourceDoc().getProject().getNumber());
                            totalProjectsCounted++;
                        }
                    }
                }
                if(totalEngTasks != null) {
                    for(ListIterator engIter = totalEngTasks.listIterator(); engIter.hasNext();) {
                        EngTask et = (EngTask) engIter.next();
                        //check if already counted
                        boolean projectCounted = false;
                        for(ListIterator linCountedIter = countedTotalProjects.listIterator(); linCountedIter.hasNext();) {
                            String lci = (String) linCountedIter.next();
                            if(et.getTargetDoc().getSourceDoc().getProject().getNumber().equals(lci)) {
                                projectCounted = true;
                                break;
                            }
                        }
                        if(!projectCounted) {
                            countedTotalProjects.add(et.getTargetDoc().getSourceDoc().getProject().getNumber());
                            totalProjectsCounted++;
                        }
                    }
                }
                if(totalDtpTasks != null) {
                    for(ListIterator dtpIter = totalDtpTasks.listIterator(); dtpIter.hasNext();) {
                        DtpTask dt = (DtpTask) dtpIter.next();
                        //check if already counted
                        boolean projectCounted = false;
                        for(ListIterator linCountedIter = countedTotalProjects.listIterator(); linCountedIter.hasNext();) {
                            String lci = (String) linCountedIter.next();
                            if(dt.getTargetDoc().getSourceDoc().getProject().getNumber().equals(lci)) {
                                projectCounted = true;
                                break;
                            }
                        }
                        if(!projectCounted) {
                            countedTotalProjects.add(dt.getTargetDoc().getSourceDoc().getProject().getNumber());
                            totalProjectsCounted++;
                        }
                    }
                }
                if(totalOthTasks != null) {
                    for(ListIterator othIter = totalOthTasks.listIterator(); othIter.hasNext();) {
                        OthTask ot = (OthTask) othIter.next();
                        //check if already counted
                        boolean projectCounted = false;
                        for(ListIterator linCountedIter = countedTotalProjects.listIterator(); linCountedIter.hasNext();) {
                            String lci = (String) linCountedIter.next();
                            if(ot.getTargetDoc().getSourceDoc().getProject().getNumber().equals(lci)) {
                                projectCounted = true;
                                break;
                            }
                        }
                        if(!projectCounted) {
                            countedTotalProjects.add(ot.getTargetDoc().getSourceDoc().getProject().getNumber());
                            totalProjectsCounted++;
                        }
                    }
                }
                cvtd.setTotalProjects(totalProjectsCounted);
                
                //add to list
                cvtdList.add(cvtd);
            }
            else if(inList) {
                //get this resouce from the cvtdList
                ClientViewTeamDisplay cvtd = new ClientViewTeamDisplay();
                for(ListIterator inIter = cvtdList.listIterator(); inIter.hasNext();) {
                    cvtd = (ClientViewTeamDisplay) inIter.next();
                    if(Integer.valueOf(t.getPersonName()).equals(cvtd.getResource().getResourceId())) {
                        break;
                    }
                }
                //update totalForClient
                if(t.getInternalDollarTotal() != null && t.getInternalDollarTotal().length() > 0) {
                    String existingString = cvtd.getTotalForClient().replaceAll(",","");
                    double existing = Double.valueOf(existingString).doubleValue();
                    String newString = t.getInternalDollarTotal().replaceAll(",","");
                    double newT = Double.valueOf(newString).doubleValue();
                    cvtd.setTotalForClient(StandardCode.getInstance().formatDouble(new Double(existing + newT)));
                }
                
                //check if clientProjects (count) should be updated
                boolean cpCounted = false;
                for(ListIterator cpIter = cvtd.getCountedClientProjects().listIterator(); cpIter.hasNext();) {
                    String cp = (String) cpIter.next();
                    if(cp.equals(t.getTargetDoc().getSourceDoc().getProject().getNumber())) {
                        cpCounted = true;
                    }
                }
                if(!cpCounted) {
                    cvtd.getCountedClientProjects().add(t.getTargetDoc().getSourceDoc().getProject().getNumber());
                    cvtd.setClientProjects(cvtd.getClientProjects() + 1);
                }
            }
        }  
        }
        //END eng task
        
        //START dtp task
        if(dtpTasksDisplay != null) {
        for(ListIterator iter = dtpTasksDisplay.listIterator(); iter.hasNext();) {
            DtpTask t = (DtpTask) iter.next();
            //check if resource in list
            boolean inList = false;
            for(ListIterator li = cvtdList.listIterator(); li.hasNext();) {
                ClientViewTeamDisplay cvtd = (ClientViewTeamDisplay) li.next();
                if(cvtd.getResource().getResourceId().equals(Integer.valueOf(t.getPersonName()))) {
                    inList = true;
                    break;
                }
            }
            if(!inList) {
                ClientViewTeamDisplay cvtd = new ClientViewTeamDisplay();
                cvtd.setResource(ResourceService.getInstance().getSingleResourceNoLazy(Integer.valueOf(t.getPersonName())));
                cvtd.setClientProjects(1);
                ArrayList countedClientProjects = new ArrayList(); //string ("000001")
                countedClientProjects.add(t.getTargetDoc().getSourceDoc().getProject().getNumber());
                cvtd.setCountedClientProjects(countedClientProjects);
                if(t.getInternalDollarTotal() != null && t.getInternalDollarTotal().length() > 0) {
                    cvtd.setTotalForClient(t.getInternalDollarTotal());
                }
                else {
                    cvtd.setTotalForClient("0.00");
                }
                //set total projects
                int totalProjectsCounted = 0;
                ArrayList countedTotalProjects = new ArrayList(); //string ("000001")
                List totalLinTasks = ProjectService.getInstance().getResourceLinTasksClient(t.getPersonName());
                List totalEngTasks = ProjectService.getInstance().getResourceEngTasksClient(t.getPersonName());
                List totalDtpTasks = ProjectService.getInstance().getResourceDtpTasksClient(t.getPersonName());
                List totalOthTasks = ProjectService.getInstance().getResourceOthTasksClient(t.getPersonName());
                
                if(totalLinTasks != null) {
                    for(ListIterator linIter = totalLinTasks.listIterator(); linIter.hasNext();) {
                        LinTask lt = (LinTask) linIter.next();
                        //check if already counted
                        boolean projectCounted = false;
                        for(ListIterator linCountedIter = countedTotalProjects.listIterator(); linCountedIter.hasNext();) {
                            String lci = (String) linCountedIter.next();
                            if(lt.getTargetDoc().getSourceDoc().getProject().getNumber().equals(lci)) {
                                projectCounted = true;
                                break;
                            }
                        }
                        if(!projectCounted) {
                            countedTotalProjects.add(lt.getTargetDoc().getSourceDoc().getProject().getNumber());
                            totalProjectsCounted++;
                        }
                    }
                }
                if(totalEngTasks != null) {
                    for(ListIterator engIter = totalEngTasks.listIterator(); engIter.hasNext();) {
                        EngTask et = (EngTask) engIter.next();
                        //check if already counted
                        boolean projectCounted = false;
                        for(ListIterator linCountedIter = countedTotalProjects.listIterator(); linCountedIter.hasNext();) {
                            String lci = (String) linCountedIter.next();
                            if(et.getTargetDoc().getSourceDoc().getProject().getNumber().equals(lci)) {
                                projectCounted = true;
                                break;
                            }
                        }
                        if(!projectCounted) {
                            countedTotalProjects.add(et.getTargetDoc().getSourceDoc().getProject().getNumber());
                            totalProjectsCounted++;
                        }
                    }
                }
                if(totalDtpTasks != null) {
                    for(ListIterator dtpIter = totalDtpTasks.listIterator(); dtpIter.hasNext();) {
                        DtpTask dt = (DtpTask) dtpIter.next();
                        //check if already counted
                        boolean projectCounted = false;
                        for(ListIterator linCountedIter = countedTotalProjects.listIterator(); linCountedIter.hasNext();) {
                            String lci = (String) linCountedIter.next();
                            if(dt.getTargetDoc().getSourceDoc().getProject().getNumber().equals(lci)) {
                                projectCounted = true;
                                break;
                            }
                        }
                        if(!projectCounted) {
                            countedTotalProjects.add(dt.getTargetDoc().getSourceDoc().getProject().getNumber());
                            totalProjectsCounted++;
                        }
                    }
                }
                if(totalOthTasks != null) {
                    for(ListIterator othIter = totalOthTasks.listIterator(); othIter.hasNext();) {
                        OthTask ot = (OthTask) othIter.next();
                        //check if already counted
                        boolean projectCounted = false;
                        for(ListIterator linCountedIter = countedTotalProjects.listIterator(); linCountedIter.hasNext();) {
                            String lci = (String) linCountedIter.next();
                            if(ot.getTargetDoc().getSourceDoc().getProject().getNumber().equals(lci)) {
                                projectCounted = true;
                                break;
                            }
                        }
                        if(!projectCounted) {
                            countedTotalProjects.add(ot.getTargetDoc().getSourceDoc().getProject().getNumber());
                            totalProjectsCounted++;
                        }
                    }
                }
                cvtd.setTotalProjects(totalProjectsCounted);
                
                //add to list
                cvtdList.add(cvtd);
            }
            else if(inList) {
                //get this resouce from the cvtdList
                ClientViewTeamDisplay cvtd = new ClientViewTeamDisplay();
                for(ListIterator inIter = cvtdList.listIterator(); inIter.hasNext();) {
                    cvtd = (ClientViewTeamDisplay) inIter.next();
                    if(Integer.valueOf(t.getPersonName()).equals(cvtd.getResource().getResourceId())) {
                        break;
                    }
                }
                //update totalForClient
                if(t.getInternalDollarTotal() != null && t.getInternalDollarTotal().length() > 0) {
                    String existingString = cvtd.getTotalForClient().replaceAll(",","");
                    double existing = Double.valueOf(existingString).doubleValue();
                    String newString = t.getInternalDollarTotal().replaceAll(",","");
                    double newT = Double.valueOf(newString).doubleValue();
                    cvtd.setTotalForClient(StandardCode.getInstance().formatDouble(new Double(existing + newT)));
                }
                
                //check if clientProjects (count) should be updated
                boolean cpCounted = false;
                for(ListIterator cpIter = cvtd.getCountedClientProjects().listIterator(); cpIter.hasNext();) {
                    String cp = (String) cpIter.next();
                    if(cp.equals(t.getTargetDoc().getSourceDoc().getProject().getNumber())) {
                        cpCounted = true;
                    }
                }
                if(!cpCounted) {
                    cvtd.getCountedClientProjects().add(t.getTargetDoc().getSourceDoc().getProject().getNumber());
                    cvtd.setClientProjects(cvtd.getClientProjects() + 1);
                }
            }
        }  
        }
        //END dtp task
        
        //START oth task
        if(othTasksDisplay != null) {
        for(ListIterator iter = othTasksDisplay.listIterator(); iter.hasNext();) {
            OthTask t = (OthTask) iter.next();
            //check if resource in list
            boolean inList = false;
            for(ListIterator li = cvtdList.listIterator(); li.hasNext();) {
                ClientViewTeamDisplay cvtd = (ClientViewTeamDisplay) li.next();
                if(cvtd.getResource().getResourceId().equals(Integer.valueOf(t.getPersonName()))) {
                    inList = true;
                    break;
                }
            }
            if(!inList) {
                ClientViewTeamDisplay cvtd = new ClientViewTeamDisplay();
                cvtd.setResource(ResourceService.getInstance().getSingleResourceNoLazy(Integer.valueOf(t.getPersonName())));
                cvtd.setClientProjects(1);
                ArrayList countedClientProjects = new ArrayList(); //string ("000001")
                countedClientProjects.add(t.getTargetDoc().getSourceDoc().getProject().getNumber());
                cvtd.setCountedClientProjects(countedClientProjects);
                if(t.getInternalDollarTotal() != null && t.getInternalDollarTotal().length() > 0) {
                    cvtd.setTotalForClient(t.getInternalDollarTotal());
                }
                else {
                    cvtd.setTotalForClient("0.00");
                }
                //set total projects
                int totalProjectsCounted = 0;
                ArrayList countedTotalProjects = new ArrayList(); //string ("000001")
                List totalLinTasks = ProjectService.getInstance().getResourceLinTasksClient(t.getPersonName());
                List totalEngTasks = ProjectService.getInstance().getResourceEngTasksClient(t.getPersonName());
                List totalDtpTasks = ProjectService.getInstance().getResourceDtpTasksClient(t.getPersonName());
                List totalOthTasks = ProjectService.getInstance().getResourceOthTasksClient(t.getPersonName());
                
                if(totalLinTasks != null) {
                    for(ListIterator linIter = totalLinTasks.listIterator(); linIter.hasNext();) {
                        LinTask lt = (LinTask) linIter.next();
                        //check if already counted
                        boolean projectCounted = false;
                        for(ListIterator linCountedIter = countedTotalProjects.listIterator(); linCountedIter.hasNext();) {
                            String lci = (String) linCountedIter.next();
                            if(lt.getTargetDoc().getSourceDoc().getProject().getNumber().equals(lci)) {
                                projectCounted = true;
                                break;
                            }
                        }
                        if(!projectCounted) {
                            countedTotalProjects.add(lt.getTargetDoc().getSourceDoc().getProject().getNumber());
                            totalProjectsCounted++;
                        }
                    }
                }
                if(totalEngTasks != null) {
                    for(ListIterator engIter = totalEngTasks.listIterator(); engIter.hasNext();) {
                        EngTask et = (EngTask) engIter.next();
                        //check if already counted
                        boolean projectCounted = false;
                        for(ListIterator linCountedIter = countedTotalProjects.listIterator(); linCountedIter.hasNext();) {
                            String lci = (String) linCountedIter.next();
                            if(et.getTargetDoc().getSourceDoc().getProject().getNumber().equals(lci)) {
                                projectCounted = true;
                                break;
                            }
                        }
                        if(!projectCounted) {
                            countedTotalProjects.add(et.getTargetDoc().getSourceDoc().getProject().getNumber());
                            totalProjectsCounted++;
                        }
                    }
                }
                if(totalDtpTasks != null) {
                    for(ListIterator dtpIter = totalDtpTasks.listIterator(); dtpIter.hasNext();) {
                        DtpTask dt = (DtpTask) dtpIter.next();
                        //check if already counted
                        boolean projectCounted = false;
                        for(ListIterator linCountedIter = countedTotalProjects.listIterator(); linCountedIter.hasNext();) {
                            String lci = (String) linCountedIter.next();
                            if(dt.getTargetDoc().getSourceDoc().getProject().getNumber().equals(lci)) {
                                projectCounted = true;
                                break;
                            }
                        }
                        if(!projectCounted) {
                            countedTotalProjects.add(dt.getTargetDoc().getSourceDoc().getProject().getNumber());
                            totalProjectsCounted++;
                        }
                    }
                }
                if(totalOthTasks != null) {
                    for(ListIterator othIter = totalOthTasks.listIterator(); othIter.hasNext();) {
                        OthTask ot = (OthTask) othIter.next();
                        //check if already counted
                        boolean projectCounted = false;
                        for(ListIterator linCountedIter = countedTotalProjects.listIterator(); linCountedIter.hasNext();) {
                            String lci = (String) linCountedIter.next();
                            if(ot.getTargetDoc().getSourceDoc().getProject().getNumber().equals(lci)) {
                                projectCounted = true;
                                break;
                            }
                        }
                        if(!projectCounted) {
                            countedTotalProjects.add(ot.getTargetDoc().getSourceDoc().getProject().getNumber());
                            totalProjectsCounted++;
                        }
                    }
                }
                cvtd.setTotalProjects(totalProjectsCounted);
                
                //add to list
                cvtdList.add(cvtd);
            }
            else if(inList) {
                //get this resouce from the cvtdList
                ClientViewTeamDisplay cvtd = new ClientViewTeamDisplay();
                for(ListIterator inIter = cvtdList.listIterator(); inIter.hasNext();) {
                    cvtd = (ClientViewTeamDisplay) inIter.next();
                    if(Integer.valueOf(t.getPersonName()).equals(cvtd.getResource().getResourceId())) {
                        break;
                    }
                }
                //update totalForClient
                if(t.getInternalDollarTotal() != null && t.getInternalDollarTotal().length() > 0) {
                    String existingString = cvtd.getTotalForClient().replaceAll(",","");
                    double existing = Double.valueOf(existingString).doubleValue();
                    String newString = t.getInternalDollarTotal().replaceAll(",","");
                    double newT = Double.valueOf(newString).doubleValue();
                    cvtd.setTotalForClient(StandardCode.getInstance().formatDouble(new Double(existing + newT)));
                }
                
                //check if clientProjects (count) should be updated
                boolean cpCounted = false;
                for(ListIterator cpIter = cvtd.getCountedClientProjects().listIterator(); cpIter.hasNext();) {
                    String cp = (String) cpIter.next();
                    if(cp.equals(t.getTargetDoc().getSourceDoc().getProject().getNumber())) {
                        cpCounted = true;
                    }
                }
                if(!cpCounted) {
                    cvtd.getCountedClientProjects().add(t.getTargetDoc().getSourceDoc().getProject().getNumber());
                    cvtd.setClientProjects(cvtd.getClientProjects() + 1);
                }
            }
        }  
        }
        //END oth task
        */
        //place ClientViewTeamDisplay into display
       // request.setAttribute("cvtdList", cvtdList);
        
        //place the client into an attribute for displaying in jsp
        request.setAttribute("client", c);
                
        //add this client id to cookies; this will remember the last client
        response.addCookie(StandardCode.getInstance().setCookie("clientViewId", clientId));
        
        //add tab location to cookies; this will remember which tab we are at
        response.addCookie(StandardCode.getInstance().setCookie("clientViewTab", "Team History"));
        
	// Forward control to the specified success URI
	return (mapping.findForward("Success"));
    }

}
