/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.client;

import app.extjs.global.LanguageAbs;
import app.extjs.vo.Product;
import app.project.LinTask;
import app.project.Project;
import app.project.ProjectService;
import app.project.SourceDoc;
import app.project.TargetDoc;
import app.security.SecurityService;
import app.standardCode.StandardCode;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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

/**
 *
 * @author Nishika
 */
public class ClientProfileGenerateAction extends Action {

  // ----------------------------------------------------- Instance Variables
  /**
   * The
   * <code>Log</code> instance for this application.
   */
  private Log log =
          LogFactory.getLog("org.apache.struts.webapp.Example");

  // --------------------------------------------------------- Public Methods
  /**
   * Process the specified HTTP request, and create the corresponding
   * HTTP response (or forward to another web component that will
   * create it). Return an
   * <code>ActionForward</code> instance describing where and how
   * control should be forwarded, or
   * <code>null</code> if the response has already been completed.
   *
   * @param mapping The ActionMapping used to select this instance
   * @param form The optional ActionForm bean for this request (if
   * any)
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

    String profileId = request.getParameter("profileId");
    ClientProfile clientProfile = ClientService.getInstance().getSingleClientProfile(Integer.parseInt(profileId));
    Integer clientId = clientProfile.getClientId();
    Client client = ClientService.getInstance().getClient(clientId);
    List projectList = ProjectService.getInstance().getLastProjectListForClient(clientId, 10);

    String productTemplate = "{\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid8157860\\\\charrsid815482"
            + "\\\\trowd \\\\irow0\\\\irowband0\\\\ltrrow\\\\ts21\\\\trgaph108\\\\trleft-108\\\\trbrdrt\\\\brdrs\\\\brdrw10 \\\\trbrdrl\\\\brdrs\\\\brdrw10 \\\\trbrdrb\\\\brdrs\\\\brdrw10 \\\\trbrdrr\\\\brdrs\\\\brdrw10 \\\\trbrdrh\\\\brdrs\\\\brdrw10 \\\\trbrdrv\\\\brdrs\\\\brdrw10 "
            + "\\\\trftsWidth1\\\\trftsWidthB3\\\\trautofit1\\\\trpaddl108\\\\trpaddr108\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid8157860\\\\tbllkhdrrows\\\\tbllkhdrcols\\\\tbllknocolband\\\\tblind0\\\\tblindtype3 \\\\clvertalt\\\\clbrdrt\\\\brdrs\\\\brdrw10 \\\\clbrdrl\\\\brdrs\\\\brdrw10 \\\\clbrdrb\\\\brdrs\\\\brdrw10 "
            + "\\\\clbrdrr\\\\brdrs\\\\brdrw10 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2394\\\\clshdrawnil \\\\cellx2286\\\\clvertalt\\\\clbrdrt\\\\brdrs\\\\brdrw10 \\\\clbrdrl\\\\brdrs\\\\brdrw10 \\\\clbrdrb\\\\brdrs\\\\brdrw10 \\\\clbrdrr\\\\brdrs\\\\brdrw10 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2394\\\\clshdrawnil \\\\cellx4680\\\\clvertalt"
            + "\\\\clbrdrt\\\\brdrs\\\\brdrw10 \\\\clbrdrl\\\\brdrs\\\\brdrw10 \\\\clbrdrb\\\\brdrs\\\\brdrw10 \\\\clbrdrr\\\\brdrs\\\\brdrw10 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2394\\\\clshdrawnil \\\\cellx7074\\\\clvertalt\\\\clbrdrt\\\\brdrs\\\\brdrw10 \\\\clbrdrl\\\\brdrs\\\\brdrw10 \\\\clbrdrb\\\\brdrs\\\\brdrw10 \\\\clbrdrr\\\\brdrs\\\\brdrw10 "
            + "\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2394\\\\clshdrawnil \\\\cellx9468\\\\row \\\\ltrrow}\\\\pard\\\\plain \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0\\\\yts21 \\\\rtlch\\\\fcs1 \\\\af0\\\\afs22\\\\alang1025 \\\\ltrch\\\\fcs0 "
            + "\\\\f37\\\\fs22\\\\lang1033\\\\langfe1033\\\\cgrid\\\\langnp1033\\\\langfenp1033 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\f0\\\\cf18\\\\insrsid4063271\\\\charrsid815482 INSERT_PRODUCT_INSERT}{\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\f0\\\\cf18\\\\insrsid8157860\\\\charrsid815482 \\\\cell }{\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 "
            + "\\\\f0\\\\cf18\\\\insrsid4063271\\\\charrsid815482 INSERT_CATEGORY_INSERT}{\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\f0\\\\cf18\\\\insrsid8157860\\\\charrsid815482 \\\\cell }{\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\f0\\\\cf18\\\\insrsid4063271\\\\charrsid815482 INSERT_DESCRIPTION_INSERT}{\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 "
            + "\\\\f0\\\\cf18\\\\insrsid8157860\\\\charrsid815482 \\\\cell \\\\cell }\\\\pard\\\\plain \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 \\\\rtlch\\\\fcs1 \\\\af0\\\\afs22\\\\alang1025 \\\\ltrch\\\\fcs0 "
            + "\\\\f37\\\\fs22\\\\lang1033\\\\langfe1033\\\\cgrid\\\\langnp1033\\\\langfenp1033 INSERT_PRODUCT_LIST_INSERT";


    String contactTemplate = "{\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\insrsid4063271\\\\charrsid815482 \\\\trowd \\\\irow0\\\\irowband0\\\\ltrrow\\\\ts21\\\\trgaph108\\\\trleft-108\\\\trbrdrt\\\\brdrs\\\\brdrw10 \\\\trbrdrl\\\\brdrs\\\\brdrw10 \\\\trbrdrb\\\\brdrs\\\\brdrw10 \\\\trbrdrr\n"
            + "\\\\brdrs\\\\brdrw10 \\\\trbrdrh\\\\brdrs\\\\brdrw10 \\\\trbrdrv\\\\brdrs\\\\brdrw10 \\\\trftsWidth1\\\\trftsWidthB3\\\\trautofit1\\\\trpaddl108\\\\trpaddr108\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid3882826\\\\tbllkhdrrows\\\\tbllkhdrcols\\\\tbllknocolband\\\\tblind0\\\\tblindtype3 \\\\clvertalt\\\\clbrdrt\n"
            + "\\\\brdrs\\\\brdrw10 \\\\clbrdrl\\\\brdrs\\\\brdrw10 \\\\clbrdrb\\\\brdrs\\\\brdrw10 \\\\clbrdrr\\\\brdrs\\\\brdrw10 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2394\\\\clshdrawnil \\\\cellx2286\\\\clvertalt\\\\clbrdrt\\\\brdrs\\\\brdrw10 \\\\clbrdrl\\\\brdrs\\\\brdrw10 \\\\clbrdrb\\\\brdrs\\\\brdrw10 \\\\clbrdrr\\\\brdrs\\\\brdrw10 \n"
            + "\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2394\\\\clshdrawnil \\\\cellx4680\\\\clvertalt\\\\clbrdrt\\\\brdrs\\\\brdrw10 \\\\clbrdrl\\\\brdrs\\\\brdrw10 \\\\clbrdrb\\\\brdrs\\\\brdrw10 \\\\clbrdrr\\\\brdrs\\\\brdrw10 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth4788\\\\clshdrawnil \\\\cellx9468\\\\row \\\\ltrrow}\\\\pard\\\\plain \\\\ltrpar\n"
            + "\\\\ql \\\\li0\\\\ri0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0\\\\yts21 \\\\rtlch\\\\fcs1 \\\\af0\\\\afs22\\\\alang1025 \\\\ltrch\\\\fcs0 \\\\f37\\\\fs22\\\\lang1033\\\\langfe1033\\\\cgrid\\\\langnp1033\\\\langfenp1033 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\f0\\\\cf21\\\\insrsid2758104 INSERT_CONTACT_INSERT\n"
            + "}{\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\f0\\\\cf18\\\\insrsid4063271\\\\charrsid815482 \\\\cell }{\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\f0\\\\cf21\\\\insrsid2758104 INSERT_TITLE_INSERT}{\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\f0\\\\cf18\\\\insrsid4063271\\\\charrsid815482 \\\\cell }{\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \n"
            + "\\\\f0\\\\cf21\\\\insrsid2758104 }{\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\f0\\\\cf18\\\\insrsid4063271\\\\charrsid815482 \\\\cell }\\\\pard\\\\plain \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 \\\\rtlch\\\\fcs1 \\\\af0\\\\afs22\\\\alang1025 \\\\ltrch\\\\fcs0 \n"
            + "\\\\f37\\\\fs22\\\\lang1033\\\\langfe1033\\\\cgrid\\\\langnp1033\\\\langfenp1033 INSERT_CLIENT_CONTACT_INSERT";
    //save the pdf in memory
    
    String templateLocation  = StandardCode.getInstance().getPropertyValue("mac.templates.path");
    byte[] template = getBytesFromFile(new java.io.File(templateLocation+"/templates/client_profile.rtf"));
    String content = new String(template);
//          content = content.replaceAll("", );int cnt =1;
    content = content.replaceAll("INSERT_CLIENT_INSERT", client.getCompany_name());
    content = content.replaceAll("INSERT_BUSINESS_INSERT", StandardCode.getInstance().noNull(client.getBusiness_desc()));
    content = content.replaceAll("INSERT_CERTIFICATION_INSERT", StandardCode.getInstance().noNull(client.getCertifications()));
    int cnt = 1;
    content = content.replaceAll("INSERT_AE_INSERT", StandardCode.getInstance().noNull(client.getSales_rep()));

    List productList = ClientService.getInstance().getProductListForClient(client.getClientId());
    for (int i = 0; i < productList.size(); i++) {

      Product prod = (Product) productList.get(i);
      content = content.replaceAll("INSERT_PRODUCT_LIST_INSERT", productTemplate);
      content = content.replaceAll("INSERT_DESCRIPTION_INSERT", prod.getDescription());
      content = content.replaceAll("INSERT_PRODUCT_INSERT", prod.getProduct());
      content = content.replaceAll("INSERT_CATEGORY_INSERT", prod.getCategory());

    }
    content = content.replaceAll("INSERT_PRODUCT_LIST_INSERT", "");
    Set contacts = null;
    contacts = client.getContacts();
    if (contacts != null) {
      for (Iterator iter = contacts.iterator(); iter.hasNext();) {
        ClientContact cc = (ClientContact) iter.next();
        if (cc.isDisplay()) {
          content = content.replaceAll("INSERT_CLIENT_CONTACT_INSERT", contactTemplate);
          content = content.replaceAll("INSERT_CONTACT_INSERT", cc.getFirst_name() + " " + cc.getLast_name());
          content = content.replaceAll("INSERT_TITLE_INSERT", cc.getTitle());

        }
      }

    }

    content = content.replaceAll("INSERT_CLIENT_CONTACT_INSERT", "");
    for (int i = 0; i < projectList.size(); i++) {
      try {


        Project proj = (Project) projectList.get(i);
        Project p = ProjectService.getInstance().getSingleProject(proj.getProjectId());
        HashMap alreadyAdded = new HashMap();
        String sources = "", targets = "";
        List<String> sourceLang111 = new ArrayList<String>();
        for (Iterator iterSources = p.getSourceDocs().iterator(); iterSources.hasNext();) {
          SourceDoc sd = (SourceDoc) iterSources.next();
          int fflag = 0;
          Iterator it = sourceLang111.iterator();
          while (it.hasNext()) {
            if (sd.getLanguage().equalsIgnoreCase((String) it.next())) {
              fflag = 1;
            }
          }
          if (fflag == 0) {
            sources += sd.getLanguage() + "";

          }
          sourceLang111.add(sd.getLanguage());
          for (Iterator iterTargets = sd.getTargetDocs().iterator(); iterTargets.hasNext();) {
            TargetDoc td = (TargetDoc) iterTargets.next();
            for (Iterator iterLintasks = td.getLinTasks().iterator(); iterLintasks.hasNext();) {
              LinTask lt = (LinTask) iterLintasks.next();
              if (!"".equalsIgnoreCase(lt.getTargetLanguage())) {
                String tgtLang = lt.getTargetLanguage();

                String abr = (String) LanguageAbs.getInstance().getAbs().get(tgtLang);
                //Also prevent duplicates
                if (abr != null && !"".equals(tgtLang) && !"".equals(abr) && alreadyAdded.get(abr) == null) {
                  targets += abr + ", ";
                  alreadyAdded.put(abr, abr);
                } else if (abr == null && !"".equals(tgtLang) && !"".equals(abr) && alreadyAdded.get(tgtLang) == null) {
                  targets += tgtLang + ", ";
                  alreadyAdded.put(tgtLang, tgtLang);
                }

              }
            }
          }
          if (targets.endsWith(", ")) {
            targets = targets.substring(0, targets.length() - 2);
          }
        }


        //START content
//            //System.out.println(cnt+"|"+p.getNumber()+"|"+p.getStatus()+"|"+ p.getSourceApplication()+"|"+p.getProjectAmount()+"|"+p.getDeliveryDate());
        content = content.replaceAll("#PROJECT" + cnt + "#", p.getNumber() + p.getCompany().getCompany_code());
        content = content.replaceAll("#SOURCE" + cnt + "#", sources);
        content = content.replaceAll("#FORMAT" + cnt + "#", StandardCode.getInstance().noNull(p.getSourceApplication()));
        content = content.replaceAll("#TARGET" + cnt + "#", targets);
        if (null == p.getProjectAmount()) {
          content = content.replaceAll("#FEE" + cnt + "#", "");
        }
        content = content.replaceAll("#FEE" + cnt + "#", StandardCode.getInstance().noNull(client.getCcurrency()) + " " + StandardCode.getInstance().formatMoney(p.getProjectAmount()));
        content = content.replaceAll("#DELIVERED" + cnt + "#", "" + p.getDeliveryDate());
        content = content.replaceAll("#PROJECT" + cnt + "#", "");
        content = content.replaceAll("#SOURCE" + cnt + "#", "");
        content = content.replaceAll("#FORMAT" + cnt + "#", "");
        content = content.replaceAll("#TARGET" + cnt + "#", "");
        content = content.replaceAll("#FEE" + cnt + "#", "");
        content = content.replaceAll("#DELIVERED" + cnt + "#", "");
        content = content.replaceAll("null", "");
        cnt++;
      } catch (Exception e) {
        System.err.println("Error: " + e);
        content = content.replaceAll("#PROJECT" + cnt + "#", "");
        content = content.replaceAll("#SOURCE" + cnt + "#", "");
        content = content.replaceAll("#FORMAT" + cnt + "#", "");
        content = content.replaceAll("#TARGET" + cnt + "#", "");
        content = content.replaceAll("#FEE" + cnt + "#", "");
        content = content.replaceAll("#DELIVERED" + cnt + "#", "");
        content = content.replaceAll("null", "");
      }

    }
    content = content.replaceAll("INSERT_AE_TEXT_INSERT", StandardCode.getInstance().noNull(clientProfile.getAe_text()));
    content = content.replaceAll("INSERT_PROCESS_TEXT_INSERT", StandardCode.getInstance().noNull(clientProfile.getProcess_text()));
    content = content.replaceAll("INSERT_ICR_TEXT_INSERT", StandardCode.getInstance().noNull(clientProfile.getIcr_text()));
    content = content.replaceAll("INSERT_DELIVERABLE_TEXT_INSERT", StandardCode.getInstance().noNull(clientProfile.getDeliverable_text()));
    content = content.replaceAll("INSERT_TECHNICAL_TEXT_INSERT", StandardCode.getInstance().noNull(clientProfile.getTechnical_project_text()));
    content = content.replaceAll("INSERT_TYPICAL_TEXT_INSERT", StandardCode.getInstance().noNull(clientProfile.getTypical_project_text()));
    content = content.replaceAll("INSERT_PRICING_TEXT_INSERT", StandardCode.getInstance().noNull(clientProfile.getPricing_text()));
    content = content.replaceAll("INSERT_INVOICING_TEXT_INSERT", StandardCode.getInstance().noNull(clientProfile.getCurrent_invoicing_process_text()));

    //END content

    //write to client (web browser)
    response.setHeader("Content-Type", "Application/msword");
    String filename = client.getCompany_name().replaceAll(" ", "_").replaceAll(",", "_") + "_Profile.doc";
    response.setHeader("Content-disposition", "attachment; filename=" + filename);
    OutputStream os = response.getOutputStream();
    os.write(content.getBytes());
    os.flush();
    // Forward control to the specified success URI
    return (mapping.findForward("Success"));
  }

  // Returns the contents of the file in a byte array.
  private static byte[] getBytesFromFile(java.io.File file) throws IOException {
    InputStream is = new FileInputStream(file);

    // Get the size of the file
    long length = file.length();

    // You cannot create an array using a long type.
    // It needs to be an int type.
    // Before converting to an int type, check
    // to ensure that file is not larger than Integer.MAX_VALUE.
    if (length > Integer.MAX_VALUE) {
      // File is too large
    }

    // Create the byte array to hold the data
    byte[] bytes = new byte[(int) length];

    // Read in the bytes
    int offset = 0;
    int numRead = 0;
    while (offset < bytes.length
            && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
      offset += numRead;
    }

    // Ensure all the bytes have been read in
    if (offset < bytes.length) {
      throw new IOException("Could not completely read file " + file.getName());
    }

    // Close the input stream and return bytes
    is.close();
    return bytes;
  }
}
