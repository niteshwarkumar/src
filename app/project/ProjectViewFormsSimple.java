//ProjectViewAccountingGenerateInvoiceAction.java creates the
//Client Invoice pdf
package app.project;

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
import java.text.*;
import java.io.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import app.user.*;
import app.resource.*;
import app.security.*;
import app.standardCode.*;

public final class ProjectViewFormsSimple extends Action {

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
        String pdfNameLng = "";
        String pdfNamePoNo = "";

        //START get id of current project from either request, attribute, or cookie
        //id of project from request
        String projectId = null;
        projectId = request.getParameter("projectViewId");

        //check attribute in request
        if (projectId == null) {
            projectId = (String) request.getAttribute("projectViewId");
        }

        //id of project from cookie
        if (projectId == null) {
            projectId = StandardCode.getInstance().getCookie("projectViewId", request.getCookies());
        }

        //default project to last if not in request or cookie
        if (projectId == null) {
            java.util.List results = ProjectService.getInstance().getProjectList();

            ListIterator iterScroll = null;
            for (iterScroll = results.listIterator(); iterScroll.hasNext(); iterScroll.next()) {
            }
            iterScroll.previous();
            Project p = (Project) iterScroll.next();
            projectId = String.valueOf(p.getProjectId());
        }
        Integer id = Integer.valueOf(projectId);

        //END get id of current project from either request, attribute, or cookie

        //get project
        Project p = ProjectService.getInstance().getSingleProject(id);

        //get invoice
        //lientInvoice ci = ProjectService.getInstance().getSingleClientInvoice(Integer.valueOf(request.getParameter("id")));

        //get user (project manager)
        User u = UserService.getInstance().getSingleUser(5);
        try {
            u = UserService.getInstance().getSingleUserRealName(StandardCode.getInstance().getFirstName(p.getPm()), StandardCode.getInstance().getLastName(p.getPm()));
        } catch (Exception e) {
        }
        if ("Y".equals(request.getParameter("generatePO"))) {

            //get task to create po
            String linId = null;
            String engId = null;
            String dtpId = null;
            String othId = null;

            linId = request.getParameter("linId");
            if (linId == null) {
                engId = request.getParameter("engId");
                if (engId == null) {
                    dtpId = request.getParameter("dtpId");
                    if (dtpId == null) {
                        othId = request.getParameter("othId");
                    }
                }
            }

            //get the next po number for this project
            String poNumber = ProjectService.getInstance().getNewPoNumber(p);

            if (linId != null) { //generate lin PO
                LinTask lt = (LinTask) ProjectService.getInstance().getSingleLinTask(Integer.valueOf(linId));


                //Add default score of "0"for this resource
                lt.setScore(new Integer(0));
                lt.setPoNumber(poNumber);
                ProjectService.getInstance().updateLinTask(lt);

            } else if (engId != null) { //generate eng PO
                EngTask et = (EngTask) ProjectService.getInstance().getSingleEngTask(Integer.valueOf(engId));


                //Add default score of "0"for this resource
                et.setScore(new Integer(0));
                et.setPoNumber(poNumber);
                ProjectService.getInstance().updateEngTask(et);


            } else if (dtpId != null) { //generate dtp PO
                DtpTask dt = (DtpTask) ProjectService.getInstance().getSingleDtpTask(Integer.valueOf(dtpId));


                //Add default score of "0"for this resource
                dt.setScore(new Integer(0));
                dt.setPoNumber(poNumber);
                ProjectService.getInstance().updateDtpTask(dt);



            } else if (othId != null) { //generate oth PO
                OthTask ot = (OthTask) ProjectService.getInstance().getSingleOthTask(Integer.valueOf(othId));


                //Add default score of "0"for this resource
                ot.setScore(new Integer(0));
                ot.setPoNumber(poNumber);
                ProjectService.getInstance().updateOthTask(ot);

            }

        }

        //START process pdf
        try {
            String poNumber = "";
            String formFileName = request.getParameter("simpleFormName");
            //"C:/templates/QUA01_001.pdf"

            PdfReader reader = new PdfReader("C:/templates/" + formFileName + ".pdf"); //the template
            //System.out.println("filenameeeeeeeeeeeeeeee"+reader.toString());
            //save the pdf in memory
            ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();

            //the filled-in pdf
            PdfStamper stamp = new PdfStamper(reader, pdfStream);

            //stamp.setEncryption(true, "pass", "pass", PdfWriter.AllowCopy | PdfWriter.AllowPrinting);
            AcroFields form1 = stamp.getAcroFields();
            /* HashMap hm = form1.getFields();
            for(Iterator iterSource = hm.keySet().iterator(); iterSource.hasNext();) {

            System.out.println("hm="+(String)iterSource.next());
            }*/


            //START POPULATING THE FIELDS
            if ("QUA01_001".equals(formFileName)) {
                form1.setField("project", p.getNumber() + p.getCompany().getCompany_code());
                form1.setField("pm", p.getPm());


                //get sources and targets
                StringBuffer sources = new StringBuffer("");
                StringBuffer targets = new StringBuffer("");
                if (p.getSourceDocs() != null) {
                    for (Iterator iterSource = p.getSourceDocs().iterator(); iterSource.hasNext();) {
                        SourceDoc sd = (SourceDoc) iterSource.next();
                        sources.append(sd.getLanguage() + " ");
                        if (sd.getTargetDocs() != null) {
                            for (Iterator iterTarget = sd.getTargetDocs().iterator(); iterTarget.hasNext();) {
                                TargetDoc td = (TargetDoc) iterTarget.next();
                                if (!td.getLanguage().equals("All")) {
                                    targets.append(td.getLanguage() + " ");
                                }
                            }
                        }
                    }
                }

                form1.setField("language", targets.toString());

            } else if ("LI10_001".equals(formFileName)) {
                form1.setField("project", p.getNumber() + p.getCompany().getCompany_code());
                form1.setField("product", p.getProduct());

                //get sources and targets
                StringBuffer sources = new StringBuffer("");
                StringBuffer targets = new StringBuffer("");
                if (p.getSourceDocs() != null) {
                    for (Iterator iterSource = p.getSourceDocs().iterator(); iterSource.hasNext();) {
                        SourceDoc sd = (SourceDoc) iterSource.next();
                        sources.append(sd.getLanguage() + " ");
                        if (sd.getTargetDocs() != null) {
                            for (Iterator iterTarget = sd.getTargetDocs().iterator(); iterTarget.hasNext();) {
                                TargetDoc td = (TargetDoc) iterTarget.next();
                                if (!td.getLanguage().equals("All")) {
                                    targets.append(td.getLanguage() + " ");
                                }
                            }
                        }
                    }
                }
                form1.setField("Language", targets.toString());
                form1.setField("ClientName", p.getCompany().getCompany_name());

            } else if ("LI09_001".equals(formFileName)) {
                form1.setField("project", p.getNumber() + p.getCompany().getCompany_code());
                form1.setField("product", p.getProduct());

                //get sources and targets
                StringBuffer sources = new StringBuffer("");
                StringBuffer targets = new StringBuffer("");
                if (p.getSourceDocs() != null) {
                    for (Iterator iterSource = p.getSourceDocs().iterator(); iterSource.hasNext();) {
                        SourceDoc sd = (SourceDoc) iterSource.next();
                        sources.append(sd.getLanguage() + " ");
                        if (sd.getTargetDocs() != null) {
                            for (Iterator iterTarget = sd.getTargetDocs().iterator(); iterTarget.hasNext();) {
                                TargetDoc td = (TargetDoc) iterTarget.next();
                                if (!td.getLanguage().equals("All")) {
                                    targets.append(td.getLanguage() + " ");
                                }
                            }
                        }
                    }
                }
                form1.setField("Language", targets.toString());
                form1.setField("ClientName", p.getCompany().getCompany_name());
                try {
                    if (p.getCompany().getLogo() != null && p.getCompany().getLogo().length() > 0) {
                        PdfContentByte over = null;
                        Image img = Image.getInstance("C:/Program Files (x86)/Apache Software Foundation/Tomcat 7.0/webapps/logo/images/" + p.getCompany().getLogo());
                        img.setAbsolutePosition(0, 0);
                        over = stamp.getOverContent(1);
                        //To position an image at (x,y) use addImage(image, image_width, 0, 0, image_height, x, y).
                        over.addImage(img, 100, 0, 0, 40, 450, 645);

                    } else {
                        form1.setField("Logo", "No logo found");
                    }
                } catch (Exception e) {
                    form1.setField("Logo", "No logo found");
                }
            } else if (("LI01_thru_LI04".equals(formFileName) || "LI11_001_6tier1".equals(formFileName)) && !request.getParameter("linId").equals("-1")) {




                //get resource to create form
                String linId = request.getParameter("linId");
                LinTask lt = ProjectService.getInstance().getSingleLinTask(Integer.valueOf(linId));
                //System.out.println("alexxxx:inside LI01_thru_LI04.pdf:lt.getPersonName()="+lt.getPersonName()+"linId="+linId);
                pdfNameLng = lt.getTargetDoc().getLanguage();
                pdfNamePoNo = lt.getPoNumber();
                if (lt.getPersonName() != null && lt.getPersonName().length() > 0) {
                    Resource r = ResourceService.getInstance().getSingleResource(Integer.valueOf(lt.getPersonName()));
                    //set the field values in the pdf form
                    if ((r.getFirstName().length() >= 1 && r.getFirstName() != null) && (r.getLastName().length() >= 1 && r.getLastName() != null)) {
                        form1.setField("contractor", StandardCode.getInstance().noNull(r.getFirstName()) + " " + StandardCode.getInstance().noNull(r.getLastName()));
                    } else {
                        form1.setField("contractor", StandardCode.getInstance().noNull(r.getCompanyName()));
                    }
                    form1.setField("Project", p.getNumber() + p.getCompany().getCompany_code());
                    form1.setField("PO", p.getNumber() + p.getCompany().getCompany_code() + "-PO-" + lt.getPoNumber());
                    poNumber = lt.getPoNumber();
                    form1.setField("Deliverby", p.getDeliveryMethod());
                    if (lt.getDueDateDate() != null) {
                        form1.setField("Deadline", DateFormat.getDateInstance(DateFormat.SHORT).format(lt.getDueDateDate()));
                    }

                    form1.setField("date", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
                    form1.setField("PM", p.getPm());
                    //form1.setField("Instructions", p.get); Instructions doesn't exist in DB
                    form1.setField("Currency", lt.getInternalCurrency());

                    //form1.setField("Email", r.getEmail_address1());
                    //form1.setField("Phone", r.getMain_telephone_numb1());
                    //form1.setField("Fax", r.getFax_number());
                    //form1.setField("Address", r.getAddress_1()+" "+StandardCode.getInstance().noNull(r.getAddress_2()));
                    form1.setField("Email", u.getWorkEmail1());
                    if (u.getWorkPhoneEx() != null && u.getWorkPhoneEx().length() > 0) { //ext present
                        form1.setField("Phone", StandardCode.getInstance().noNull(u.getWorkPhone()) + " ext " + StandardCode.getInstance().noNull(u.getWorkPhoneEx()));
                    } else { //no ext present
                        form1.setField("Phone", StandardCode.getInstance().noNull(u.getWorkPhone()));
                    }
                    form1.setField("Fax", StandardCode.getInstance().noNull(u.getLocation().getFax_number()));
                    form1.setField("Address", StandardCode.getInstance().printLocation(u.getLocation()));


                    //form1.setField("Language", targets.toString());
                    form1.setField("Language", lt.getTargetDoc().getLanguage());

                    form1.setField("Unit", lt.getUnits());



                    double rate = 0.0;
                    if (lt.getInternalRate() != null) {
                        form1.setField("Rate_new", lt.getInternalRate());
                        rate = Double.parseDouble(lt.getInternalRate());
                    }

                    double totalWords = 0;
                    double newWords = 0;

                    if ("LI11_001_6tier1".equals(formFileName) && lt.getWordNew() != null) {
                        form1.setField("Volume_new", lt.getWordNew().toString());
                        newWords = lt.getWordNew().doubleValue();
                        totalWords += newWords;
                    } else if (lt.getWordNew4() != null) {
                        form1.setField("Volume_new", lt.getWordNew4().toString());
                        newWords = lt.getWordNew4().doubleValue();
                        totalWords += newWords;
                    }

                    double costTotalNew = rate * newWords;
                    form1.setField("Cost_new", "" + (costTotalNew * 100 / 100.00));

                    //Set VOLUME fields
                    double word100Cost = 0.0;
                    if (lt.getWord100() != null) {
                        form1.setField("Volume_100", lt.getWord100().toString());
                        word100Cost = rate * Double.parseDouble(p.getCompany().getScale100()) * lt.getWord100().intValue();
                        totalWords += lt.getWord100().intValue();
                    }


                    double wordRepCost = 0.0;
                    if (lt.getWordRep() != null) {
                        form1.setField("Volume_rep", lt.getWordRep().toString());
                        wordRepCost = rate * Double.parseDouble(p.getCompany().getScaleRep()) * lt.getWordRep().intValue();
                        totalWords += lt.getWordRep().intValue();
                    }





                    double wordRep8599 = 0.0;
                    double wordRep7584 = 0.0;
                    double wordRep8594 = 0.0;
                    double wordRep9599 = 0.0;

                    if ("LI11_001_6tier1".equals(formFileName)) {


                        if (lt.getWord85() != null) {
                            form1.setField("Volume_8594", lt.getWord85().toString());
                            wordRep8594 = rate * Double.parseDouble(p.getCompany().getScale85()) * lt.getWord85().intValue();
                            totalWords += lt.getWord85().intValue();
                            form1.setField("Rate_8594", StandardCode.getInstance().formatDouble3(new Double(rate * Double.parseDouble(p.getCompany().getScale85()))));
                            form1.setField("Cost_8594", "" + StandardCode.getInstance().formatDouble(new Double(wordRep8594)));
                        }
                    }

                    if ("LI11_001_6tier1".equals(formFileName)) {


                        if (lt.getWord75() != null) {
                            form1.setField("Volume_7584", lt.getWord75().toString());
                            wordRep7584 = rate * Double.parseDouble(p.getCompany().getScale75()) * lt.getWord75().intValue();
                            totalWords += lt.getWord75().intValue();
                            form1.setField("Rate_7584", StandardCode.getInstance().formatDouble3(new Double(rate * (Double.parseDouble(p.getCompany().getScale75())))));
                            form1.setField("Cost_7584", "" + StandardCode.getInstance().formatDouble(new Double(wordRep7584)));
                        }
                    }


                    if (!"LI11_001_6tier1".equals(formFileName)) {


                        if (lt.getWord8599() != null) {
                            form1.setField("Volume_8599", lt.getWord8599().toString());
                            wordRep8599 = rate * Double.parseDouble(p.getCompany().getScale8599()) * lt.getWord8599().intValue();
                            totalWords += lt.getWord8599().intValue();
                            form1.setField("Rate_8599", StandardCode.getInstance().formatDouble3(new Double(rate * Double.parseDouble(p.getCompany().getScale8599()))));
                            form1.setField("Cost_8599", "" + StandardCode.getInstance().formatDouble(new Double(wordRep8599)));
                        }
                    } else {
                        if (lt.getWord95() != null) {
                            form1.setField("Volume_9599", lt.getWord95().toString());
                            wordRep9599 = rate * Double.parseDouble(p.getCompany().getScale95()) * lt.getWord95().intValue();
                            totalWords += lt.getWord95().intValue();
                            form1.setField("Rate_9599", StandardCode.getInstance().formatDouble3(new Double(rate * Double.parseDouble(p.getCompany().getScale95()))));
                            form1.setField("Cost_9599", "" + StandardCode.getInstance().formatDouble(new Double(wordRep9599)));
                        }
                    }

                    //SET Rate Fields
                    form1.setField("Rate_100", StandardCode.getInstance().formatDouble3(new Double(rate * Double.parseDouble(p.getCompany().getScale100()))));
                    form1.setField("Rate_rep", StandardCode.getInstance().formatDouble3(new Double(rate * Double.parseDouble(p.getCompany().getScaleRep()))));


                    //Set Cost

                    form1.setField("Cost_100", "" + StandardCode.getInstance().formatDouble(new Double(word100Cost)));
                    form1.setField("Cost_rep", "" + StandardCode.getInstance().formatDouble(new Double(wordRepCost)));

                    //form1.setField("Cost_total", StandardCode.getInstance().formatDouble(new Double(costTotalNew+word100Cost+wordRepCost+wordRep8599)));
                    double totalAmount = costTotalNew + word100Cost + wordRepCost + wordRep8599 + wordRep9599 + wordRep7584 + wordRep8594;
                    form1.setField("Cost_total", "" + StandardCode.getInstance().formatDouble(new Double(totalAmount)));

                    //Set total words
                    //form1.setField("Volume_total", StandardCode.getInstance().formatInteger(new Integer(totalWords)));
                    form1.setField("Volume_total", "" + totalWords);

                    //Add tasks
                    form1.setField("Task1", lt.getTaskName());
                }


            } else if ("DTP02_001".equals(formFileName)) {
                //List dtpTasks = (List) request.getAttribute("dtpTasks");
                String dtpt = (String) request.getAttribute("dtpT");
                String dtp1 = request.getParameter("dtpT");
                System.out.println(request.getParameter("dtpT"));
                System.out.println(request.getParameter("dtp1"));
                if (request.getParameter("dtpId") != null && !request.getParameter("dtpId").equals("-1")) {
                    String dtpId = request.getParameter("dtpId");
                    //get resource to create form
                    //  String dtpId = "9101";
                    //if(dtpId.equalsIgnoreCase("9101")){
                    //dtpId = "9101";
                    DtpTask dtp = ProjectService.getInstance().getSingleDtpTask(Integer.valueOf(dtpId));
                    pdfNameLng = dtp.getTargetDoc().getLanguage();
                    pdfNamePoNo = dtp.getPoNumber();
                    if (dtp.getTotal() != null) {
                        form1.setField("Volume_new", ""+dtp.getTotalTeam());
                        form1.setField("Volume_total", ""+dtp.getTotalTeam());

                    }
                    if (dtp.getInternalRate() != null) {
                        form1.setField("Rate_new", dtp.getInternalRate());
                    }
                    if (dtp.getInternalDollarTotal() != null) {
                        form1.setField("Cost_new", dtp.getInternalDollarTotal());
                        form1.setField("Cost_total", dtp.getInternalDollarTotal());

                    }

                    if (dtp.getDueDateDate() != null) {
                        form1.setField("Deadline", DateFormat.getDateInstance(DateFormat.SHORT).format(dtp.getDueDateDate()));
                    }

                    form1.setField("Unit", dtp.getUnits());
                    //Add tasks
                    form1.setField("Task1", dtp.getTaskName());
                    form1.setField("Language", dtp.getTargetDoc().getLanguage());
                    form1.setField("Currency", dtp.getCurrency());
                    if (dtp.getPersonName() != null) {
                        Resource r = ResourceService.getInstance().getSingleResource(Integer.valueOf(dtp.getPersonName()));
                        //set the field values in the pdf form
                        if ((r.getFirstName().length() >= 1 && r.getFirstName() != null) && (r.getLastName().length() >= 1 && r.getLastName() != null)) {
                            form1.setField("contractor", StandardCode.getInstance().noNull(r.getFirstName()) + " " + StandardCode.getInstance().noNull(r.getLastName()));
                        } else {
                            form1.setField("contractor", StandardCode.getInstance().noNull(r.getCompanyName()));
                        }
                    }
                    form1.setField("PO", p.getNumber() + p.getCompany().getCompany_code() + "-PO-" + dtp.getPoNumber());
                    poNumber = dtp.getPoNumber();

                } else if (request.getParameter("engId") != null) {
                    //get resource to create form
                    String engId = request.getParameter("engId");
                    EngTask dtp = ProjectService.getInstance().getSingleEngTask(Integer.valueOf(engId));
                    pdfNameLng = dtp.getTargetDoc().getLanguage();
                    pdfNamePoNo = dtp.getPoNumber();
                    if (dtp.getTotal() != null) {
                        form1.setField("Volume_new", ""+dtp.getTotal());
                        form1.setField("Volume_total", ""+dtp.getTotal());

                    }
                    if (dtp.getInternalRate() != null) {
                        form1.setField("Rate_new", dtp.getInternalRate());
                    }
                    if (dtp.getInternalDollarTotal() != null) {
                        form1.setField("Cost_new", dtp.getInternalDollarTotal());
                        form1.setField("Cost_total", dtp.getInternalDollarTotal());

                    }
                    form1.setField("Unit", dtp.getUnits());
                    //Add tasks
                    form1.setField("Task1", dtp.getTaskName());
                    form1.setField("Language", dtp.getTargetDoc().getLanguage());
                    form1.setField("Currency", dtp.getCurrency());

                    if (dtp.getPersonName() != null) {
                        Resource r = ResourceService.getInstance().getSingleResource(Integer.valueOf(dtp.getPersonName()));
                        //set the field values in the pdf form
                        if ((r.getFirstName().length() >= 1 && r.getFirstName() != null) && (r.getLastName().length() >= 1 && r.getLastName() != null)) {
                            form1.setField("contractor", StandardCode.getInstance().noNull(r.getFirstName()) + " " + StandardCode.getInstance().noNull(r.getLastName()));
                        } else {
                            form1.setField("contractor", StandardCode.getInstance().noNull(r.getCompanyName()));
                        }
                    }
                    form1.setField("PO", p.getNumber() + p.getCompany().getCompany_code() + "-PO-" + dtp.getPoNumber());
                    poNumber = dtp.getPoNumber();
                } else if (request.getParameter("othId") != null) {
                    //get resource to create form
                    String othId = request.getParameter("othId");
                    OthTask dtp = ProjectService.getInstance().getSingleOthTask(Integer.valueOf(othId));
                    pdfNameLng = dtp.getTargetDoc().getLanguage();
                    pdfNamePoNo = dtp.getPoNumber();
                    if (dtp.getTotal() != null) {
                        form1.setField("Volume_new", ""+dtp.getTotal());
                        form1.setField("Volume_total", ""+dtp.getTotal());

                    }
                    if (dtp.getInternalRate() != null) {
                        form1.setField("Rate_new", dtp.getInternalRate());
                    }
                    if (dtp.getInternalDollarTotal() != null) {
                        form1.setField("Cost_new", dtp.getInternalDollarTotal());
                        form1.setField("Cost_total", dtp.getInternalDollarTotal());

                    }
                    form1.setField("Unit", dtp.getUnits());
                    //Add tasks
                    form1.setField("Task1", dtp.getTaskName());
                    form1.setField("Language", dtp.getTargetDoc().getLanguage());
                    form1.setField("Currency", dtp.getCurrency());
                    if (dtp.getDueDateDate() != null) {
                        form1.setField("Deadline", DateFormat.getDateInstance(DateFormat.SHORT).format(dtp.getDueDateDate()));
                    }
                    if (dtp.getPersonName() != null) {
                        Resource r = ResourceService.getInstance().getSingleResource(Integer.valueOf(dtp.getPersonName()));
                        //set the field values in the pdf form
                        if ((r.getFirstName().length() >= 1 && r.getFirstName() != null) && (r.getLastName().length() >= 1 && r.getLastName() != null)) {
                            form1.setField("contractor", StandardCode.getInstance().noNull(r.getFirstName()) + " " + StandardCode.getInstance().noNull(r.getLastName()));
                        } else {
                            form1.setField("contractor", StandardCode.getInstance().noNull(r.getCompanyName()));
                        }
                    }
                    form1.setField("PO", p.getNumber() + p.getCompany().getCompany_code() + "-PO-" + dtp.getPoNumber());
                    poNumber = dtp.getPoNumber();
                }


                form1.setField("Project", p.getNumber() + p.getCompany().getCompany_code());

                form1.setField("Deliverby", p.getDeliveryMethod());



                form1.setField("date", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
                form1.setField("PM", p.getPm());
                //form1.setField("Instructions", p.get); Instructions doesn't exist in DB
                form1.setField("Email", u.getWorkEmail1());
                if (u.getWorkPhoneEx() != null && u.getWorkPhoneEx().length() > 0) { //ext present
                    form1.setField("Phone", StandardCode.getInstance().noNull(u.getWorkPhone()) + " ext " + StandardCode.getInstance().noNull(u.getWorkPhoneEx()));
                } else { //no ext present
                    form1.setField("Phone", StandardCode.getInstance().noNull(u.getWorkPhone()));
                }
                form1.setField("Fax", StandardCode.getInstance().noNull(u.getLocation().getFax_number()));
                form1.setField("Address", StandardCode.getInstance().printLocation(u.getLocation()));


                //form1.setField("Email", r.getEmail_address1());
                //form1.setField("Phone", r.getMain_telephone_numb1());
                //form1.setField("Fax", r.getFax_number());
                //form1.setField("Address", r.getAddress_1()+" "+StandardCode.getInstance().noNull(r.getAddress_2()));






            }else if ("DTP-PO".equals(formFileName)) {
                //List dtpTasks = (List) request.getAttribute("dtpTasks");
                String dtpt = (String) request.getAttribute("dtpT");
                String dtp1 = request.getParameter("dtpT");
                System.out.println(request.getParameter("dtpT"));
                System.out.println(request.getParameter("dtp1"));
                if (request.getParameter("dtpId") != null && !request.getParameter("dtpId").equals("-1")) {
                    String dtpId = request.getParameter("dtpId");
                    //get resource to create form
                    //  String dtpId = "9101";
                    //if(dtpId.equalsIgnoreCase("9101")){
                    //dtpId = "9101";
                    DtpTask dtp = ProjectService.getInstance().getSingleDtpTask(Integer.valueOf(dtpId));
                    pdfNameLng = dtp.getTargetDoc().getLanguage();
                    pdfNamePoNo = dtp.getPoNumber();
                    if (dtp.getTotal() != null) {
                        form1.setField("Volume_new", StandardCode.getInstance().formatDouble(dtp.getTotalTeam()));
                        form1.setField("Volume_total", StandardCode.getInstance().formatDouble(dtp.getTotalTeam()));

                    }
                    if (dtp.getInternalRate() != null) {
                        form1.setField("Rate_new", dtp.getInternalRate());
                    }
                    if (dtp.getInternalDollarTotal() != null) {
                        form1.setField("Cost_new", dtp.getInternalDollarTotal());
                        form1.setField("Cost_total", dtp.getInternalDollarTotal());

                    }

                    if (dtp.getDueDateDate() != null) {
                        form1.setField("Deadline", DateFormat.getDateInstance(DateFormat.SHORT).format(dtp.getDueDateDate()));
                    }

                    form1.setField("Unit", dtp.getUnits());
                    //Add tasks
                    form1.setField("Task1", dtp.getTaskName());
                    form1.setField("Language", dtp.getTargetDoc().getLanguage());
                    form1.setField("Currency", dtp.getCurrency());
                    if (dtp.getPersonName() != null) {
                        Resource r = ResourceService.getInstance().getSingleResource(Integer.valueOf(dtp.getPersonName()));
                        //set the field values in the pdf form
                        if ((r.getFirstName().length() >= 1 && r.getFirstName() != null) && (r.getLastName().length() >= 1 && r.getLastName() != null)) {
                            form1.setField("contractor", StandardCode.getInstance().noNull(r.getFirstName()) + " " + StandardCode.getInstance().noNull(r.getLastName()));
                        } else {
                            form1.setField("contractor", StandardCode.getInstance().noNull(r.getCompanyName()));
                        }
                    }
                    form1.setField("PO", p.getNumber() + p.getCompany().getCompany_code() + "-PO-" + dtp.getPoNumber());
                    poNumber = dtp.getPoNumber();

                } else if (request.getParameter("engId") != null) {
                    //get resource to create form
                    String engId = request.getParameter("engId");
                    EngTask dtp = ProjectService.getInstance().getSingleEngTask(Integer.valueOf(engId));
                    pdfNameLng = dtp.getTargetDoc().getLanguage();
                    pdfNamePoNo = dtp.getPoNumber();
                    if (dtp.getTotal() != null) {
                        form1.setField("Volume_new", StandardCode.getInstance().formatDouble(dtp.getTotal()));
                        form1.setField("Volume_total", StandardCode.getInstance().formatDouble(dtp.getTotal()));

                    }
                    if (dtp.getInternalRate() != null) {
                        form1.setField("Rate_new", dtp.getInternalRate());
                    }
                    if (dtp.getInternalDollarTotal() != null) {
                        form1.setField("Cost_new", dtp.getInternalDollarTotal());
                        form1.setField("Cost_total", dtp.getInternalDollarTotal());

                    }
                    form1.setField("Unit", dtp.getUnits());
                    //Add tasks
                    form1.setField("Task1", dtp.getTaskName());
                    form1.setField("Language", dtp.getTargetDoc().getLanguage());
                    form1.setField("Currency", dtp.getCurrency());

                    if (dtp.getPersonName() != null) {
                        Resource r = ResourceService.getInstance().getSingleResource(Integer.valueOf(dtp.getPersonName()));
                        //set the field values in the pdf form
                        if ((r.getFirstName().length() >= 1 && r.getFirstName() != null) && (r.getLastName().length() >= 1 && r.getLastName() != null)) {
                            form1.setField("contractor", StandardCode.getInstance().noNull(r.getFirstName()) + " " + StandardCode.getInstance().noNull(r.getLastName()));
                        } else {
                            form1.setField("contractor", StandardCode.getInstance().noNull(r.getCompanyName()));
                        }
                    }
                    form1.setField("PO", p.getNumber() + p.getCompany().getCompany_code() + "-PO-" + dtp.getPoNumber());
                    poNumber = dtp.getPoNumber();
                } else if (request.getParameter("othId") != null) {
                    //get resource to create form
                    String othId = request.getParameter("othId");
                    OthTask dtp = ProjectService.getInstance().getSingleOthTask(Integer.valueOf(othId));
                    pdfNameLng = dtp.getTargetDoc().getLanguage();
                    pdfNamePoNo = dtp.getPoNumber();
                    if (dtp.getTotal() != null) {
                        form1.setField("Volume_new", StandardCode.getInstance().formatDouble(dtp.getTotal()));
                        form1.setField("Volume_total", StandardCode.getInstance().formatDouble(dtp.getTotal()));

                    }
                    if (dtp.getInternalRate() != null) {
                        form1.setField("Rate_new", dtp.getInternalRate());
                    }
                    if (dtp.getInternalDollarTotal() != null) {
                        form1.setField("Cost_new", dtp.getInternalDollarTotal());
                        form1.setField("Cost_total", dtp.getInternalDollarTotal());

                    }
                    form1.setField("Unit", dtp.getUnits());
                    //Add tasks
                    form1.setField("Task1", dtp.getTaskName());
                    form1.setField("Language", dtp.getTargetDoc().getLanguage());
                    form1.setField("Currency", dtp.getCurrency());
                    if (dtp.getDueDateDate() != null) {
                        form1.setField("Deadline", DateFormat.getDateInstance(DateFormat.SHORT).format(dtp.getDueDateDate()));
                    }
                    if (dtp.getPersonName() != null) {
                        Resource r = ResourceService.getInstance().getSingleResource(Integer.valueOf(dtp.getPersonName()));
                        //set the field values in the pdf form
                        if ((r.getFirstName().length() >= 1 && r.getFirstName() != null) && (r.getLastName().length() >= 1 && r.getLastName() != null)) {
                            form1.setField("contractor", StandardCode.getInstance().noNull(r.getFirstName()) + " " + StandardCode.getInstance().noNull(r.getLastName()));
                        } else {
                            form1.setField("contractor", StandardCode.getInstance().noNull(r.getCompanyName()));
                        }
                    }
                    form1.setField("PO", p.getNumber() + p.getCompany().getCompany_code() + "-PO-" + dtp.getPoNumber());
                    poNumber = dtp.getPoNumber();
                }


                form1.setField("Project", p.getNumber() + p.getCompany().getCompany_code());

                form1.setField("Deliverby", p.getDeliveryMethod());



                form1.setField("date", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));
                form1.setField("PM", p.getPm());
                //form1.setField("Instructions", p.get); Instructions doesn't exist in DB
                form1.setField("Email", u.getWorkEmail1());
                if (u.getWorkPhoneEx() != null && u.getWorkPhoneEx().length() > 0) { //ext present
                    form1.setField("Phone", StandardCode.getInstance().noNull(u.getWorkPhone()) + " ext " + StandardCode.getInstance().noNull(u.getWorkPhoneEx()));
                } else { //no ext present
                    form1.setField("Phone", StandardCode.getInstance().noNull(u.getWorkPhone()));
                }
                form1.setField("Fax", StandardCode.getInstance().noNull(u.getLocation().getFax_number()));
                form1.setField("Address", StandardCode.getInstance().printLocation(u.getLocation()));


                //form1.setField("Email", r.getEmail_address1());
                //form1.setField("Phone", r.getMain_telephone_numb1());
                //form1.setField("Fax", r.getFax_number());
                //form1.setField("Address", r.getAddress_1()+" "+StandardCode.getInstance().noNull(r.getAddress_2()));






            }
            
            else if ("DTP03_001".equals(formFileName) && !request.getParameter("dtpId").equals("-1")) {

                String dtpId = request.getParameter("dtpId");
                DtpTask dtp = ProjectService.getInstance().getSingleDtpTask(Integer.valueOf(dtpId));
                Resource r = ResourceService.getInstance().getSingleResource(Integer.valueOf(dtp.getPersonName()));
                //set the field values in the pdf form
                if ((r.getFirstName().length() >= 1 && r.getFirstName() != null) && (r.getLastName().length() >= 1 && r.getLastName() != null)) {
                    form1.setField("contractor", StandardCode.getInstance().noNull(r.getFirstName()) + " " + StandardCode.getInstance().noNull(r.getLastName()));
                } else {
                    form1.setField("contractor", StandardCode.getInstance().noNull(r.getCompanyName()));
                }

                form1.setField("Project", p.getNumber() + p.getCompany().getCompany_code());
                form1.setField("PO", p.getNumber() + p.getCompany().getCompany_code() + "-PO-" + dtp.getPoNumber());
                poNumber = dtp.getPoNumber();

                form1.setField("PM", p.getPm());
                form1.setField("Email", u.getWorkEmail1());
                if (u.getWorkPhoneEx() != null && u.getWorkPhoneEx().length() > 0) { //ext present
                    form1.setField("Phone", StandardCode.getInstance().noNull(u.getWorkPhone()) + " ext " + StandardCode.getInstance().noNull(u.getWorkPhoneEx()));
                } else { //no ext present
                    form1.setField("Phone", StandardCode.getInstance().noNull(u.getWorkPhone()));
                }


                form1.setField("lang", dtp.getTargetDoc().getLanguage());

                form1.setField("OS1", p.getSourceOS());
                form1.setField("app1", p.getSourceApplication());
                form1.setField("v1", p.getSourceVersion());


            } else if ("DTP04_001".equals(formFileName) && !request.getParameter("dtpId").equals("-1")) {

                String dtpId = request.getParameter("dtpId");
                DtpTask dtp = ProjectService.getInstance().getSingleDtpTask(Integer.valueOf(dtpId));
                Resource r = ResourceService.getInstance().getSingleResource(Integer.valueOf(dtp.getPersonName()));



                //set the field values in the pdf form
                if (r != null) {
                    if ((r.getFirstName().length() >= 1 && r.getFirstName() != null) && (r.getLastName().length() >= 1 && r.getLastName() != null)) {
                        form1.setField("contractor", StandardCode.getInstance().noNull(r.getFirstName()) + " " + StandardCode.getInstance().noNull(r.getLastName()));
                    } else {
                        form1.setField("contractor", StandardCode.getInstance().noNull(r.getCompanyName()));
                    }
                }

                form1.setField("project", p.getNumber() + p.getCompany().getCompany_code());
                form1.setField("pm", p.getPm());


                if (dtp.getTargetDoc() != null) {
                    form1.setField("Text_language", dtp.getTargetDoc().getLanguage());
                }
                if (p.getSourceOS() != null) {
                    form1.setField("OS_source", p.getSourceOS());
                }
                if (p.getSourceApplication() != null) {
                    form1.setField("App_source", p.getSourceApplication());
                }
                if (p.getSourceVersion() != null) {
                    form1.setField("version_source", p.getSourceVersion());
                }



            } else if ("F2-001-Nonconformity".equals(formFileName)) {



                form1.setField("projectnr", p.getNumber() + p.getCompany().getCompany_code());

                form1.setField("name", p.getPm());
                form1.setField("email", u.getWorkEmail1());
                if (u.getWorkPhoneEx() != null && u.getWorkPhoneEx().length() > 0) { //ext present
                    form1.setField("phone", StandardCode.getInstance().noNull(u.getWorkPhone()) + " ext " + StandardCode.getInstance().noNull(u.getWorkPhoneEx()));
                } else { //no ext present
                    form1.setField("phone", StandardCode.getInstance().noNull(u.getWorkPhone()));
                }


                form1.setField("fax", StandardCode.getInstance().noNull(u.getLocation().getFax_number()));
                form1.setField("address", StandardCode.getInstance().printLocation(u.getLocation()));


            }


            //stamp.setFormFlattening(true);
            stamp.close();

            //write to client (web browser)
            //Save As: project number + sequential number, e.g. 006801newv-002

            //project number + sequential PO number + abbreviation of target language 
            // Example: 007941therm-002-DE.pdf
            if ("LI01_thru_LI04".equals(formFileName) || "DTP02_001".equals(formFileName)) {
                response.setHeader("Content-disposition", "attachment; filename=" + p.getNumber() + p.getCompany().getCompany_code() + "-" + pdfNamePoNo + "-" + pdfNameLng + ".pdf");
            } else {
                response.setHeader("Content-disposition", "attachment; filename=" + p.getNumber() + p.getCompany().getCompany_code() + "-" + poNumber + "-" + request.getParameter("simpleFormName") + ".pdf");
            }
            OutputStream os = response.getOutputStream();
            pdfStream.writeTo(os);
            os.flush();
        } catch (Exception e) {
            System.err.println("PDF Exception:" + e.toString());
            throw new RuntimeException(e);
        }
        //END process pdf

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }
}
