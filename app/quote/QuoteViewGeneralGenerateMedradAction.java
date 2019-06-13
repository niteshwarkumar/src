//QuoteViewGeneralGenerateShortAction.java creates the
//Short Quote pdf
package app.quote;

import app.client.Client;
import app.client.ClientContact;
import app.client.ClientService;
import app.extjs.helpers.QuoteHelper;
import app.extjs.vo.Product;
import app.extjs.vo.Upload_Doc;
import app.inteqa.INDelivery;
import app.inteqa.INDeliveryReq;
import app.inteqa.InteqaService;
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
import app.user.*;
import app.project.*;
import app.security.*;
import app.standardCode.*;

public final class QuoteViewGeneralGenerateMedradAction extends Action {

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

        //START get id of current quote from either request, attribute, or cookie 
        //id of quote from request
        String quoteId = null;
        quoteId = request.getParameter("quoteViewId");

        //check attribute in request
        if (quoteId == null) {
            quoteId = (String) request.getAttribute("quoteViewId");
        }

        //id of quote from cookie
        if (quoteId == null) {
            quoteId = StandardCode.getInstance().getCookie("quoteViewId", request.getCookies());
        }

        //default client to first if not in request or cookie
        if (quoteId == null) {
            java.util.List results = QuoteService.getInstance().getQuoteList();
            Quote1 first = (Quote1) results.get(0);
            quoteId = String.valueOf(first.getQuote1Id());
        }
        Integer flag=0;
        Integer id = Integer.valueOf(quoteId);

        //END get id of current quote from either request, attribute, or cookie               

        
        String langTxt="\\\\cell }{\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 "
        +"\\\\fs22\\\\insrsid16728144 \\\\trowd \\\\irow0\\\\irowband0\\\\lastrow \\\\ltrrow\\\\ts11\\\\trrh283\\\\trleft717\\\\trftsWidth3"
        +"\\\\trwWidth9540\\\\trautofit1\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblind717\\\\tblindtype3 \\\\clvertalt\\\\clbrdrt"
        +"\\\\brdrnone \\\\clbrdrl\\\\brdrnone \\\\clbrdrb\\\\brdrnone \\\\clbrdrr\\\\brdrnone \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth9540"
        +"\\\\clshdrawnil \\\\cellx10257\\\\row }{\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\fs22\\\\cf19\\\\insrsid16728144 "
        +"INSERT_LANGUAGES_INSERT";
        
        
        //get quote to edit
        Quote1 q = QuoteService.getInstance().getSingleQuote(id);
       String tableTxt = "\\\\ltrrow}\\\\trowd \\\\irow0\\\\irowband0\\\\ltrrow\\\\ts11\\\\trgaph10\\\\trrh340\\\\trleft720\\\\trkeep\\\\trbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrh\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrv\n" +
"\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trftsWidth3\\\\trwWidth9180\\\\trftsWidthB3\\\\trftsWidthA3\\\\trpaddl10\\\\trpaddr10\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid8985313\\\\tblind730\\\\tblindtype3 \\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\n" +
"\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clcfpat9\\\\clcbpat8\\\\clshdng10000\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth6390\\\\clcbpatraw8\\\\clcfpatraw9\\\\clshdngraw10000 \\\\cellx7110\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\n" +
"\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clcfpat9\\\\clcbpat8\\\\clshdng10000\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2790\\\\clcbpatraw8\\\\clcfpatraw9\\\\clshdngraw10000 \\\\cellx9900\\\\pard\\\\plain \\\\ltrpar\n" +
"\\\\s1\\\\ql \\\\li0\\\\ri0\\\\sb60\\\\sa60\\\\keepn\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\outlinelevel0\\\\adjustright\\\\rin0\\\\lin0\\\\pararsid8142068 \\\\rtlch\\\\fcs1 \\\\af1\\\\afs28\\\\alang1033 \\\\ltrch\\\\fcs0 \\\\f1\\\\fs28\\\\lang1033\\\\langfe1033\\\\kerning28\\\\cgrid\\\\langnp1033\\\\langfenp1033 {\n" +
"\\\\rtlch\\\\fcs1 \\\\af1\\\\afs22 \\\\ltrch\\\\fcs0 \\\\b\\\\fs20\\\\insrsid8142068\\\\charrsid10769281     }{\\\\rtlch\\\\fcs1 \\\\af1\\\\afs22 \\\\ltrch\\\\fcs0 \\\\b\\\\fs20\\\\insrsid6774425\\\\charrsid10769281 Target_Language1 \\\\cell }\\\\pard \\\\ltrpar\n" +
"\\\\s1\\\\qr \\\\li0\\\\ri0\\\\sb60\\\\sa60\\\\keepn\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\outlinelevel0\\\\adjustright\\\\rin0\\\\lin0\\\\pararsid8142068 {\\\\rtlch\\\\fcs1 \\\\af1\\\\afs22 \\\\ltrch\\\\fcs0 \\\\fs20\\\\insrsid6774425\\\\charrsid10769281 \\\\cell }\\\\pard\\\\plain \\\\ltrpar\n" +
"\\\\ql \\\\li0\\\\ri0\\\\sa200\\\\sl276\\\\slmult1\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 \\\\rtlch\\\\fcs1 \\\\af0\\\\afs20\\\\alang1033 \\\\ltrch\\\\fcs0 \\\\fs20\\\\lang1033\\\\langfe1033\\\\cgrid\\\\langnp1033\\\\langfenp1033 {\\\\rtlch\\\\fcs1 \\\\ab\\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \n" +
"\\\\b\\\\insrsid6774425\\\\charrsid10769281 \\\\trowd \\\\irow0\\\\irowband0\\\\ltrrow\\\\ts11\\\\trgaph10\\\\trrh340\\\\trleft720\\\\trkeep\\\\trbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrh\n" +
"\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrv\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trftsWidth3\\\\trwWidth9180\\\\trftsWidthB3\\\\trftsWidthA3\\\\trpaddl10\\\\trpaddr10\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid8985313\\\\tblind730\\\\tblindtype3 \\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\n" +
"\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clcfpat9\\\\clcbpat8\\\\clshdng10000\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth6390\\\\clcbpatraw8\\\\clcfpatraw9\\\\clshdngraw10000 \\\\cellx7110\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\n" +
"\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clcfpat9\\\\clcbpat8\\\\clshdng10000\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2790\\\\clcbpatraw8\\\\clcfpatraw9\\\\clshdngraw10000 \\\\cellx9900\\\\row \\\\ltrrow}\\\\trowd \\\\irow1\\\\irowband1\\\\ltrrow\n" +
"\\\\ts11\\\\trgaph10\\\\trrh280\\\\trleft720\\\\trbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrh\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrv\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\trftsWidth3\\\\trwWidth9180\\\\trftsWidthB3\\\\trftsWidthA3\\\\trpaddl10\\\\trpaddr10\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid8985313\\\\tblind730\\\\tblindtype3 \\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth7200\\\\clshdrawnil \\\\cellx7920\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1980\\\\clshdrawnil \\\\cellx9900\\\\pard \\\\ltrpar\\\\ql \\\\li170\\\\ri0\\\\sl120\\\\slmult0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin170\\\\pararsid8985313 {\\\\rtlch\\\\fcs1 \\\\ab\\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \n" +
"\\\\b\\\\insrsid6774425\\\\charrsid10769281 Linguistic \\\\cell }\\\\pard \\\\ltrpar\\\\qr \\\\li0\\\\ri80\\\\sl120\\\\slmult0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin80\\\\lin0\\\\pararsid8985313 {\\\\rtlch\\\\fcs1 \\\\ab\\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \n" +
"\\\\b\\\\insrsid6774425\\\\charrsid10769281 \\\\cell }\\\\pard \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sa200\\\\sl276\\\\slmult1\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 {\\\\rtlch\\\\fcs1 \\\\ab\\\\af0 \\\\ltrch\\\\fcs0 \\\\b\\\\fs18\\\\insrsid6774425\\\\charrsid10769281 \n" +
"\\\\trowd \\\\irow1\\\\irowband1\\\\ltrrow\\\\ts11\\\\trgaph10\\\\trrh280\\\\trleft720\\\\trbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrh\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrv\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\trftsWidth3\\\\trwWidth9180\\\\trftsWidthB3\\\\trftsWidthA3\\\\trpaddl10\\\\trpaddr10\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid8985313\\\\tblind730\\\\tblindtype3 \\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth7200\\\\clshdrawnil \\\\cellx7920\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1980\\\\clshdrawnil \\\\cellx9900\\\\row \\\\ltrrow}\\\\trowd \\\\irow2\\\\irowband2\\\\ltrrow\\\\ts11\\\\trgaph10\\\\trrh331\\\\trleft720\\\\trbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrr\n" +
"\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrh\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrv\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trftsWidth3\\\\trwWidth9180\\\\trftsWidthB3\\\\trftsWidthA3\\\\trpaddl10\\\\trpaddr10\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid11407741\\\\tblind730\\\\tblindtype3 \\\\clvertalb\\\\clbrdrt\n" +
"\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2970\\\\clshdrawnil \\\\cellx3690\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\n" +
"\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1890\\\\clshdrawnil \\\\cellx5580\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2340\\\\clshdrawnil \\\\cellx7920\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1980\\\\clshdrawnil \\\\cellx9900\n" +
"\\\\pard \\\\ltrpar\\\\ql \\\\li170\\\\ri0\\\\sl120\\\\slmult0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin170\\\\pararsid8985313 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid5967397\\\\charrsid10769281 No Match\\\\cell }\\\\pard \\\\ltrpar\n" +
"\\\\qr \\\\li170\\\\ri0\\\\sl120\\\\slmult0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin170\\\\pararsid11407741 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid11407741\\\\charrsid11407741 New_Word}{\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid11407741  }\n" +
"{\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid5967397\\\\charrsid10769281 \\\\cell }{\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid11407741\\\\charrsid11407741 New_Rate}{\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid5967397\\\\charrsid10769281 \\\\cell }\\\\pard \\\\ltrpar\n" +
"\\\\qr \\\\li0\\\\ri80\\\\sl120\\\\slmult0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin80\\\\lin0\\\\pararsid8985313 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid11407741\\\\charrsid11407741 New_Cost}{\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \n" +
"\\\\insrsid5967397\\\\charrsid10769281 \\\\cell }\\\\pard \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sa200\\\\sl276\\\\slmult1\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\fs18\\\\insrsid5967397\\\\charrsid10769281 \n" +
"\\\\trowd \\\\irow2\\\\irowband2\\\\ltrrow\\\\ts11\\\\trgaph10\\\\trrh331\\\\trleft720\\\\trbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrh\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrv\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\trftsWidth3\\\\trwWidth9180\\\\trftsWidthB3\\\\trftsWidthA3\\\\trpaddl10\\\\trpaddr10\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid11407741\\\\tblind730\\\\tblindtype3 \\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2970\\\\clshdrawnil \\\\cellx3690\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1890\\\\clshdrawnil \\\\cellx5580\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2340\\\\clshdrawnil \\\\cellx7920\n" +
"\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1980\\\\clshdrawnil \\\\cellx9900\\\\row \\\\ltrrow}\\\\trowd \\\\irow3\\\\irowband3\\\\ltrrow\n" +
"\\\\ts11\\\\trgaph10\\\\trrh280\\\\trleft720\\\\trbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrh\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrv\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\trftsWidth3\\\\trwWidth9180\\\\trftsWidthB3\\\\trftsWidthA3\\\\trpaddl10\\\\trpaddr10\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid11407741\\\\tblind730\\\\tblindtype3 \\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2970\\\\clshdrawnil \\\\cellx3690\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1890\\\\clshdrawnil \\\\cellx5580\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2340\\\\clshdrawnil \\\\cellx7920\n" +
"\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1980\\\\clshdrawnil \\\\cellx9900\\\\pard \\\\ltrpar\\\\ql \\\\li170\\\\ri0\\\\sl120\\\\slmult0\n" +
"\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin170\\\\pararsid8985313 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid5967397\\\\charrsid5967397 100% Matches}{\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid5967397\\\\charrsid10769281 \\\\cell \n" +
"}\\\\pard \\\\ltrpar\\\\qr \\\\li170\\\\ri0\\\\sl120\\\\slmult0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin170\\\\pararsid11407741 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid5967397\\\\charrsid10769281 100_Word\\\\cell 100_Rate\\\\cell }\\\\pard \\\\ltrpar\n" +
"\\\\qr \\\\li0\\\\ri80\\\\sl120\\\\slmult0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin80\\\\lin0\\\\pararsid8985313 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid5967397\\\\charrsid10769281 100_Cost\\\\cell }\\\\pard \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sa200\\\\sl276\\\\slmult1\n" +
"\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\fs18\\\\insrsid5967397\\\\charrsid10769281 \\\\trowd \\\\irow3\\\\irowband3\\\\ltrrow\\\\ts11\\\\trgaph10\\\\trrh280\\\\trleft720\\\\trbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrl\n" +
"\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrh\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrv\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\trftsWidth3\\\\trwWidth9180\\\\trftsWidthB3\\\\trftsWidthA3\\\\trpaddl10\\\\trpaddr10\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid11407741\\\\tblind730\\\\tblindtype3 \\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2970\\\\clshdrawnil \\\\cellx3690\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1890\\\\clshdrawnil \\\\cellx5580\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2340\\\\clshdrawnil \\\\cellx7920\n" +
"\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1980\\\\clshdrawnil \\\\cellx9900\\\\row \\\\ltrrow}\\\\pard \\\\ltrpar\\\\ql \\\\li170\\\\ri0\\\\sl120\\\\slmult0\n" +
"\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin170\\\\pararsid8985313 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid5967397\\\\charrsid5967397 Fuzzy Match (95-99%)}{\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid5967397\\\\charrsid10769281 \n" +
"\\\\cell }\\\\pard \\\\ltrpar\\\\qr \\\\li170\\\\ri0\\\\sl120\\\\slmult0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin170\\\\pararsid11407741 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid5967397\\\\charrsid10769281 Fuzzy_Word\\\\cell Fuzzy_Rate \\\\cell \n" +
"}\\\\pard \\\\ltrpar\\\\qr \\\\li0\\\\ri80\\\\sl120\\\\slmult0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin80\\\\lin0\\\\pararsid8985313 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid5967397\\\\charrsid10769281 Fuzzy_Cost\\\\cell }\\\\pard \\\\ltrpar\n" +
"\\\\ql \\\\li0\\\\ri0\\\\sa200\\\\sl276\\\\slmult1\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\fs18\\\\insrsid5967397\\\\charrsid10769281 \\\\trowd \\\\irow4\\\\irowband4\\\\ltrrow\\\\ts11\\\\trgaph10\\\\trrh280\\\\trleft720\\\\trbrdrt\n" +
"\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrh\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrv\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\trftsWidth3\\\\trwWidth9180\\\\trftsWidthB3\\\\trftsWidthA3\\\\trpaddl10\\\\trpaddr10\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid11407741\\\\tblind730\\\\tblindtype3 \\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2970\\\\clshdrawnil \\\\cellx3690\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1890\\\\clshdrawnil \\\\cellx5580\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2340\\\\clshdrawnil \\\\cellx7920\n" +
"\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1980\\\\clshdrawnil \\\\cellx9900\\\\row \\\\ltrrow}\\\\pard \\\\ltrpar\\\\ql \\\\li170\\\\ri0\\\\sl120\\\\slmult0\n" +
"\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin170\\\\pararsid8985313 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid5967397\\\\charrsid5967397 Repetitions\\\\cell }\\\\pard \\\\ltrpar\\\\qr \\\\li170\\\\ri0\\\\sl120\\\\slmult0\n" +
"\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin170\\\\pararsid11407741 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid5967397\\\\charrsid10769281 Rep_Word\\\\cell Rep_Rate\\\\cell }\\\\pard \\\\ltrpar\\\\qr \\\\li0\\\\ri80\\\\sl120\\\\slmult0\n" +
"\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin80\\\\lin0\\\\pararsid8985313 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid5967397\\\\charrsid10769281 Rep_Cost\\\\cell }\\\\pard \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sa200\\\\sl276\\\\slmult1\n" +
"\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\fs18\\\\insrsid5967397\\\\charrsid10769281 \\\\trowd \\\\irow5\\\\irowband5\\\\ltrrow\\\\ts11\\\\trgaph10\\\\trrh280\\\\trleft720\\\\trbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrl\n" +
"\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrh\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrv\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\trftsWidth3\\\\trwWidth9180\\\\trftsWidthB3\\\\trftsWidthA3\\\\trpaddl10\\\\trpaddr10\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid11407741\\\\tblind730\\\\tblindtype3 \\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2970\\\\clshdrawnil \\\\cellx3690\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1890\\\\clshdrawnil \\\\cellx5580\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2340\\\\clshdrawnil \\\\cellx7920\n" +
"\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1980\\\\clshdrawnil \\\\cellx9900\\\\row \\\\ltrrow}\\\\pard \\\\ltrpar\\\\ql \\\\li170\\\\ri0\\\\sl120\\\\slmult0\n" +
"\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin170\\\\pararsid8985313 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid5967397\\\\charrsid5967397 Total}{\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid5967397\\\\charrsid10769281 \\\\cell }\\\\pard \\\\ltrpar\n" +
"\\\\qr \\\\li170\\\\ri0\\\\sl120\\\\slmult0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin170\\\\pararsid11407741 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid11407741 Total_Word}{\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid5967397\\\\charrsid10769281 \\\\cell \\\\cell }\\\\pard \\\\ltrpar\\\\qr \\\\li0\\\\ri80\\\\sl120\\\\slmult0\n" +
"\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin80\\\\lin0\\\\pararsid8985313 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid11407741 Total_Cost \\\\cell }\\\\pard \\\\ltrpar\n" +
"\\\\ql \\\\li0\\\\ri0\\\\sa200\\\\sl276\\\\slmult1\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\fs18\\\\insrsid5967397\\\\charrsid10769281 \\\\trowd \\\\irow6\\\\irowband6\\\\ltrrow\\\\ts11\\\\trgaph10\\\\trrh280\\\\trleft720\\\\trbrdrt\n" +
"\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrh\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrv\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\trftsWidth3\\\\trwWidth9180\\\\trftsWidthB3\\\\trftsWidthA3\\\\trpaddl10\\\\trpaddr10\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid11407741\\\\tblind730\\\\tblindtype3 \\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2970\\\\clshdrawnil \\\\cellx3690\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1890\\\\clshdrawnil \\\\cellx5580\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2340\\\\clshdrawnil \\\\cellx7920\n" +
"\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1980\\\\clshdrawnil \\\\cellx9900\\\\row \\\\ltrrow}\\\\trowd \\\\irow7\\\\irowband7\\\\ltrrow\n" +
"\\\\ts11\\\\trgaph10\\\\trrh280\\\\trleft720\\\\trbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrh\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrv\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\trftsWidth3\\\\trwWidth9180\\\\trftsWidthB3\\\\trftsWidthA3\\\\trpaddl10\\\\trpaddr10\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid8985313\\\\tblind730\\\\tblindtype3 \\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth7200\\\\clshdrawnil \\\\cellx7920\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1980\\\\clshdrawnil \\\\cellx9900\\\\pard \\\\ltrpar\\\\ql \\\\li170\\\\ri0\\\\sl120\\\\slmult0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin170\\\\pararsid8985313 {\\\\rtlch\\\\fcs1 \\\\ab\\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \n" +
"\\\\b\\\\insrsid6774425\\\\charrsid10769281 Desktop Publishing / Typesetting / Formatting\\\\cell }\\\\pard \\\\ltrpar\\\\qr \\\\li0\\\\ri80\\\\sl120\\\\slmult0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin80\\\\lin0\\\\pararsid8985313 {\\\\rtlch\\\\fcs1 \\\\ab\\\\af0\\\\afs22 \n" +
"\\\\ltrch\\\\fcs0 \\\\b\\\\insrsid6774425\\\\charrsid10769281 \\\\cell }\\\\pard \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sa200\\\\sl276\\\\slmult1\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 {\\\\rtlch\\\\fcs1 \\\\ab\\\\af0 \\\\ltrch\\\\fcs0 \\\\b\\\\fs18\\\\insrsid6774425\\\\charrsid10769281 \n" +
"\\\\trowd \\\\irow7\\\\irowband7\\\\ltrrow\\\\ts11\\\\trgaph10\\\\trrh280\\\\trleft720\\\\trbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrh\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrv\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\trftsWidth3\\\\trwWidth9180\\\\trftsWidthB3\\\\trftsWidthA3\\\\trpaddl10\\\\trpaddr10\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid8985313\\\\tblind730\\\\tblindtype3 \\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth7200\\\\clshdrawnil \\\\cellx7920\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1980\\\\clshdrawnil \\\\cellx9900\\\\row \\\\ltrrow}\\\\trowd \\\\irow8\\\\irowband8\\\\ltrrow\\\\ts11\\\\trgaph10\\\\trrh280\\\\trleft720\\\\trkeep\\\\trbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrr\n" +
"\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrh\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrv\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trftsWidth3\\\\trwWidth9180\\\\trftsWidthB3\\\\trftsWidthA3\\\\trpaddl10\\\\trpaddr10\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid11407741\\\\tblind730\\\\tblindtype3 \\\\clvertalb\\\\clbrdrt\n" +
"\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2970\\\\clshdrawnil \\\\cellx3690\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\n" +
"\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1890\\\\clshdrawnil \\\\cellx5580\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2340\\\\clshdrawnil \\\\cellx7920\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1980\\\\clshdrawnil \\\\cellx9900\n" +
"\\\\pard \\\\ltrpar\\\\ql \\\\li170\\\\ri0\\\\sl120\\\\slmult0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin170\\\\pararsid8985313 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid6774425\\\\charrsid10769281 Pages \\\\cell }\\\\pard \\\\ltrpar\n" +
"\\\\qr \\\\li170\\\\ri0\\\\sl120\\\\slmult0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin170\\\\pararsid11407741 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid5967397\\\\charrsid10769281 Rate_Page}{\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \n" +
"\\\\insrsid6774425\\\\charrsid10769281 \\\\cell }\\\\pard \\\\ltrpar\\\\qr \\\\li170\\\\ri0\\\\sl120\\\\slmult0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin170\\\\pararsid12088874 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid5967397\\\\charrsid10769281 Count_Page}{\n" +
"\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid6774425\\\\charrsid10769281 \\\\cell }\\\\pard \\\\ltrpar\\\\qr \\\\li0\\\\ri80\\\\sl120\\\\slmult0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin80\\\\lin0\\\\pararsid8985313 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \n" +
"\\\\insrsid5967397\\\\charrsid10769281 Cost_Page}{\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid6774425\\\\charrsid10769281 \\\\cell }\\\\pard \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sa200\\\\sl276\\\\slmult1\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 {\\\\rtlch\\\\fcs1 \n" +
"\\\\af0 \\\\ltrch\\\\fcs0 \\\\fs18\\\\insrsid6774425\\\\charrsid10769281 \\\\trowd \\\\irow8\\\\irowband8\\\\ltrrow\\\\ts11\\\\trgaph10\\\\trrh280\\\\trleft720\\\\trkeep\\\\trbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\trbrdrh\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrv\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trftsWidth3\\\\trwWidth9180\\\\trftsWidthB3\\\\trftsWidthA3\\\\trpaddl10\\\\trpaddr10\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid11407741\\\\tblind730\\\\tblindtype3 \\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2970\\\\clshdrawnil \\\\cellx3690\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1890\\\\clshdrawnil \\\\cellx5580\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2340\\\\clshdrawnil \\\\cellx7920\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1980\\\\clshdrawnil \\\\cellx9900\\\\row \n" +
"\\\\ltrrow}\\\\pard \\\\ltrpar\\\\ql \\\\li170\\\\ri0\\\\sl120\\\\slmult0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin170\\\\pararsid8985313 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid5967397 Hour}{\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \n" +
"\\\\insrsid6774425\\\\charrsid10769281 \\\\cell }\\\\pard \\\\ltrpar\\\\qr \\\\li170\\\\ri0\\\\sl120\\\\slmult0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin170\\\\pararsid11407741 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid6774425\\\\charrsid10769281 Rate_Hour\n" +
"\\\\cell }{\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid5967397\\\\charrsid10769281 Count_Hour}{\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid6774425\\\\charrsid10769281 \\\\cell }\\\\pard \\\\ltrpar\\\\qr \\\\li0\\\\ri80\\\\sl120\\\\slmult0\n" +
"\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin80\\\\lin0\\\\pararsid8985313 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid5967397\\\\charrsid10769281 Cost_Hour}{\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid6774425\\\\charrsid10769281 \\\\cell \n" +
"}\\\\pard \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sa200\\\\sl276\\\\slmult1\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\fs18\\\\insrsid6774425\\\\charrsid10769281 \\\\trowd \\\\irow9\\\\irowband9\\\\ltrrow\n" +
"\\\\ts11\\\\trgaph10\\\\trrh280\\\\trleft720\\\\trkeep\\\\trbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrh\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrv\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\trftsWidth3\\\\trwWidth9180\\\\trftsWidthB3\\\\trftsWidthA3\\\\trpaddl10\\\\trpaddr10\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid11407741\\\\tblind730\\\\tblindtype3 \\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2970\\\\clshdrawnil \\\\cellx3690\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1890\\\\clshdrawnil \\\\cellx5580\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2340\\\\clshdrawnil \\\\cellx7920\n" +
"\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1980\\\\clshdrawnil \\\\cellx9900\\\\row \\\\ltrrow}\\\\trowd \\\\irow10\\\\irowband10\\\\ltrrow\n" +
"\\\\ts11\\\\trgaph10\\\\trrh280\\\\trleft720\\\\trbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrh\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrv\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\trftsWidth3\\\\trwWidth9180\\\\trftsWidthB3\\\\trftsWidthA3\\\\trpaddl10\\\\trpaddr10\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid8985313\\\\tblind730\\\\tblindtype3 \\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth7200\\\\clshdrawnil \\\\cellx7920\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1980\\\\clshdrawnil \\\\cellx9900\\\\pard \\\\ltrpar\\\\ql \\\\li170\\\\ri0\\\\sl120\\\\slmult0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin170\\\\pararsid8985313 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \n" +
"\\\\insrsid6774425\\\\charrsid10769281 Engineering and Translation Memory Processing/Management\\\\cell }\\\\pard \\\\ltrpar\\\\qr \\\\li0\\\\ri80\\\\sl120\\\\slmult0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin80\\\\lin0\\\\pararsid8985313 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \n" +
"\\\\ltrch\\\\fcs0 \\\\insrsid6774425\\\\charrsid10769281 Included\\\\cell }\\\\pard \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sa200\\\\sl276\\\\slmult1\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\fs18\\\\insrsid6774425\\\\charrsid10769281 \n" +
"\\\\trowd \\\\irow10\\\\irowband10\\\\ltrrow\\\\ts11\\\\trgaph10\\\\trrh280\\\\trleft720\\\\trbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrh\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrv\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\trftsWidth3\\\\trwWidth9180\\\\trftsWidthB3\\\\trftsWidthA3\\\\trpaddl10\\\\trpaddr10\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid8985313\\\\tblind730\\\\tblindtype3 \\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth7200\\\\clshdrawnil \\\\cellx7920\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1980\\\\clshdrawnil \\\\cellx9900\\\\row \\\\ltrrow}\\\\pard \\\\ltrpar\\\\ql \\\\li170\\\\ri0\\\\sl120\\\\slmult0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin170\\\\pararsid8985313 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \n" +
"\\\\insrsid6774425\\\\charrsid10769281 Dedicated Project Management\\\\cell }\\\\pard \\\\ltrpar\\\\qr \\\\li0\\\\ri80\\\\sl120\\\\slmult0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin80\\\\lin0\\\\pararsid8985313 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \n" +
"\\\\insrsid8142068\\\\charrsid10769281 Insert_PM_Insert}{\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid6774425\\\\charrsid10769281 \\\\cell }\\\\pard \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sa200\\\\sl276\\\\slmult1\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 {\n" +
"\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\fs18\\\\insrsid6774425\\\\charrsid10769281 \\\\trowd \\\\irow11\\\\irowband11\\\\ltrrow\\\\ts11\\\\trgaph10\\\\trrh280\\\\trleft720\\\\trbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\trbrdrh\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrv\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trftsWidth3\\\\trwWidth9180\\\\trftsWidthB3\\\\trftsWidthA3\\\\trpaddl10\\\\trpaddr10\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid8985313\\\\tblind730\\\\tblindtype3 \\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth7200\\\\clshdrawnil \\\\cellx7920\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1980\\\\clshdrawnil \\\\cellx9900\\\\row \\\\ltrrow}\\\\pard \\\\ltrpar\\\\qr \\\\li0\\\\ri170\\\\sl120\\\\slmult0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin170\\\\lin0\\\\pararsid8985313 {\\\\rtlch\\\\fcs1 \n" +
"\\\\ab\\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\b\\\\insrsid6774425\\\\charrsid10769281    Total }{\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\insrsid8142068\\\\charrsid10769281 Target_Language2 }{\\\\rtlch\\\\fcs1 \\\\ab\\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\b\\\\insrsid6774425\\\\charrsid10769281 \\\\cell }\\\\pard \\\\ltrpar\n" +
"\\\\qr \\\\li344\\\\ri80\\\\sl120\\\\slmult0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin80\\\\lin344\\\\pararsid8142068 {\\\\rtlch\\\\fcs1 \\\\ab\\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\b\\\\insrsid8142068\\\\charrsid10769281 Insert_amount_Insert}{\\\\rtlch\\\\fcs1 \\\\ab\\\\af0\\\\afs22 \n" +
"\\\\ltrch\\\\fcs0 \\\\b\\\\insrsid6774425\\\\charrsid10769281 \\\\cell }\\\\pard \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sa200\\\\sl276\\\\slmult1\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 {\\\\rtlch\\\\fcs1 \\\\af0 \\\\ltrch\\\\fcs0 \\\\fs18\\\\insrsid6774425\\\\charrsid10769281 \n" +
"\\\\trowd \\\\irow12\\\\irowband12\\\\lastrow \\\\ltrrow\\\\ts11\\\\trgaph10\\\\trrh280\\\\trleft720\\\\trbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrh\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trbrdrv\n" +
"\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\trftsWidth3\\\\trwWidth9180\\\\trftsWidthB3\\\\trftsWidthA3\\\\trpaddl10\\\\trpaddr10\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid8985313\\\\tblind730\\\\tblindtype3 \\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\n" +
"\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth7200\\\\clshdrawnil \\\\cellx7920\\\\clvertalb\\\\clbrdrt\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrl\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrb\\\\brdrs\\\\brdrw15\\\\brdrcf9 \\\\clbrdrr\\\\brdrs\\\\brdrw15\\\\brdrcf9 \n" +
"\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1980\\\\clshdrawnil \\\\cellx9900\\\\row }\\\\pard\\\\plain \\\\ltrpar\\\\s87\\\\ql \\\\li0\\\\ri0\\\\sa120\\\\sl-200\\\\slmult0\\\\widctlpar\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0\\\\itap0\\\\pararsid6774425 \\\\rtlch\\\\fcs1 \\\\af0\\\\afs20\\\\alang1033 \\\\ltrch\\\\fcs0 \n" +
"\\\\fs20\\\\lang1033\\\\langfe1033\\\\cgrid\\\\langnp1033\\\\langfenp1033 {\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\fs22\\\\insrsid6774425 \n" +
"\\\\par Replace_Table_Per_Language "; 
        

      
String taskText="\\\\pard\\\\plain \\\\ltrpar\\\\s3\\\\ql \\\\li0\\\\ri0\\\\keepn\\\\widctlpar\\\\intbl\\\\tx720\\\\tqr\\\\tx9270\\\\tqr\\\\tx9360\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\outlinelevel2\\\\adjustright\\\\rin0\\\\lin0\\\\pararsid8324751 \\\\rtlch\\\\fcs1 \\\\ai\\\\af0\\\\afs22\\\\alang1025 \\\\ltrch\\\\fcs0 "
+"\\\\i\\\\fs22\\\\lang1033\\\\langfe1033\\\\cgrid\\\\langnp1033\\\\langfenp1033 "
+"{\\\\rtlch\\\\fcs1 \\\\ai0\\\\af0 \\\\ltrch\\\\fcs0 \\\\i0\\\\insrsid16021606\\\\charrsid9177496 Lin1\\\\cell Format1\\\\cell }"
+"{\\\\rtlch\\\\fcs1 \\\\ai0\\\\af0 \\\\ltrch\\\\fcs0 \\\\i0\\\\insrsid16021606 QA1}"
+"{\\\\rtlch\\\\fcs1 \\\\ai0\\\\af0 \\\\ltrch\\\\fcs0 \\\\i0\\\\insrsid16021606\\\\charrsid9177496 \\\\cell }"
+"{\\\\rtlch\\\\fcs1 \\\\ai0\\\\af0 \\\\ltrch\\\\fcs0 \\\\i0\\\\insrsid16021606 Other1}"
+"{\\\\rtlch\\\\fcs1 \\\\ai0\\\\af0 \\\\ltrch\\\\fcs0 \\\\i0\\\\insrsid16021606\\\\charrsid9177496 \\\\cell }\\\\pard"
+"\\\\plain \\\\ltrpar\\\\ql \\\\li0\\\\ri0\\\\sa200\\\\sl276\\\\slmult1"
+"\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 \\\\rtlch\\\\fcs1 \\\\af0\\\\afs20\\\\alang1025 \\\\ltrch\\\\fcs0 \\\\fs20\\\\lang1033\\\\langfe1033\\\\cgrid\\\\langnp1033\\\\langfenp1033 "
+"{\\\\rtlch\\\\fcs1 \\\\ai\\\\af0 \\\\ltrch\\\\fcs0 \\\\i\\\\insrsid16021606\\\\charrsid9177496 "
+"\\\\trowd \\\\irow1\\\\irowband1\\\\lastrow \\\\ltrrow\\\\ts11\\\\trgaph108\\\\trleft630\\\\trftsWidth1\\\\trftsWidthB3\\\\trftsWidthA3\\\\trautofit1\\\\trpaddl108\\\\trpaddr108\\\\trpaddfl3\\\\trpaddfr3\\\\tblrsid8324751\\\\tblind738\\\\tblindtype3 \\\\clvertalt\\\\clbrdrt\\\\brdrnone \\\\clbrdrl\\\\brdrnone \\\\clbrdrb"
+"\\\\brdrnone \\\\clbrdrr\\\\brdrnone \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1710\\\\clshdrawnil \\\\cellx2340\\\\clvertalt\\\\clbrdrt\\\\brdrnone \\\\clbrdrl\\\\brdrnone \\\\clbrdrb\\\\brdrnone \\\\clbrdrr\\\\brdrnone \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2070\\\\clshdrawnil \\\\cellx4410\\\\clvertalt\\\\clbrdrt\\\\brdrnone "
+"\\\\clbrdrl\\\\brdrnone \\\\clbrdrb\\\\brdrnone \\\\clbrdrr\\\\brdrnone \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth1890\\\\clshdrawnil \\\\cellx6300\\\\clvertalt\\\\clbrdrt\\\\brdrnone \\\\clbrdrl\\\\brdrnone \\\\clbrdrb\\\\brdrnone \\\\clbrdrr\\\\brdrnone \\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth2970\\\\clshdrawnil \\\\cellx9270\\\\row}"
+ "INSERT_TASK_HERE_INSERT";


        //save the pdf in memory
        //byte[] template = getBytesFromFile(new java.io.File("C:/templates/template_short_proposal.htm"));
        byte[] template = getBytesFromFile(new java.io.File("C:/templates/Bayer.rtf"));
//        byte[] template = getBytesFromFile(new java.io.File("/Users/abhisheksingh/Project/templates/Bayer.rtf"));
//        byte[] template = getBytesFromFile(new java.io.File("D:/template_short_proposal_medrad.rtf"));
        String content = new String(template);

        Client c=q.getProject().getCompany();

        //START content
        content = content.replaceAll("INSERT_DATE_INSERT", DateFormat.getDateInstance(DateFormat.SHORT).format(new Date()));

        content = content.replaceAll("INSERT_COMPANYNAME_INSERT", q.getProject().getCompany().getCompany_name());
        try {
            content = content.replaceAll("INSERT_CONTACTNAME_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getFirst_name()) + " " + StandardCode.getInstance().noNull(q.getProject().getContact().getLast_name()));
            //System.out.println("hereeeeee3");
            content = content.replaceAll("INSERT_CONTACTTITLE_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getTitle()));
            String comma = ", ";
            if ("".equalsIgnoreCase(StandardCode.getInstance().noNull(q.getProject().getContact().getDivision()))) {
                comma = "";
            }
            content = content.replaceAll("INSERT_CONTACTADDRESS1_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getDivision()) + comma + StandardCode.getInstance().noNull(q.getProject().getContact().getAddress_1()));
            content = content.replaceAll("INSERT_CONTACTADDRESS2_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getAddress_2()));
            content = content.replaceAll("INSERT_CONTACTCITY_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getCity()));
            content = content.replaceAll("INSERT_CONTACTSTATE_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getState_prov()));
            content = content.replaceAll("INSERT_CONTACTZIPCODE_INSERT", StandardCode.getInstance().noNull(q.getProject().getCompany().getZip_postal_code()));
            if (q.getProject().getContact().getWorkPhoneEx() != null && q.getProject().getContact().getWorkPhoneEx().length() > 0) { //ext present
                content = content.replaceAll("INSERT_CONTACTPHONE_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getTelephone_number()) + " ext " + StandardCode.getInstance().noNull(q.getProject().getContact().getWorkPhoneEx()));
            } else { //no ext present
                content = content.replaceAll("INSERT_CONTACTPHONE_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getTelephone_number()));
            }
            content = content.replaceAll("INSERT_CONTACTFAX_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getFax_number()));
            content = content.replaceAll("INSERT_CONTACTEMAIL_INSERT", StandardCode.getInstance().noNull(q.getProject().getContact().getEmail_address()));

        } catch (Exception e) {
            try {

                User u = UserService.getInstance().getSingleUser(q.getEnteredById());
                ClientContact cc = ClientService.getInstance().getSingleClientContact(u.getClient_contact());
                content = content.replaceAll("INSERT_CONTACTNAME_INSERT", StandardCode.getInstance().noNull(cc.getFirst_name()) + " " + StandardCode.getInstance().noNull(cc.getLast_name()));
                content = content.replaceAll("INSERT_CONTACTTITLE_INSERT", StandardCode.getInstance().noNull(" "));
                content = content.replaceAll("INSERT_CONTACTADDRESS1_INSERT", StandardCode.getInstance().noNull(cc.getAddress_1()));
                content = content.replaceAll("INSERT_CONTACTADDRESS2_INSERT", StandardCode.getInstance().noNull(cc.getAddress_2()));
                content = content.replaceAll("INSERT_CONTACTCITY_INSERT", StandardCode.getInstance().noNull(cc.getCity()));
                content = content.replaceAll("INSERT_CONTACTSTATE_INSERT", StandardCode.getInstance().noNull(cc.getState_prov()));
                content = content.replaceAll("INSERT_CONTACTZIPCODE_INSERT", StandardCode.getInstance().noNull(cc.getZip_postal_code()));

                if (cc.getTelephone_number() != null && cc.getTelephone_number().length() > 0) { //ext present
                    content = content.replaceAll("INSERT_CONTACTPHONE_INSERT", StandardCode.getInstance().noNull(cc.getTelephone_number()) + " ext " + StandardCode.getInstance().noNull(cc.getWorkPhoneEx()));
                } else { //no ext present
                    content = content.replaceAll("INSERT_CONTACTPHONE_INSERT", StandardCode.getInstance().noNull(cc.getCell_phone_number()));
                }
                content = content.replaceAll("INSERT_CONTACTFAX_INSERT", StandardCode.getInstance().noNull(""));
                content = content.replaceAll("INSERT_CONTACTEMAIL_INSERT", StandardCode.getInstance().noNull(cc.getEmail_address()));

            } catch (Exception e1) {
                try {
                    User u = UserService.getInstance().getSingleUser(q.getEnteredById());
                    Client client = ClientService.getInstance().getClient(u.getID_Client());
                    content = content.replaceAll("INSERT_CONTACTNAME_INSERT", StandardCode.getInstance().noNull(u.getFirstName()) + " " + StandardCode.getInstance().noNull(u.getLastName()));
                    content = content.replaceAll("INSERT_CONTACTTITLE_INSERT", StandardCode.getInstance().noNull(" "));
                    content = content.replaceAll("INSERT_CONTACTADDRESS1_INSERT", StandardCode.getInstance().noNull(client.getAddress_1()));
                    content = content.replaceAll("INSERT_CONTACTADDRESS2_INSERT", StandardCode.getInstance().noNull(client.getAddress_2()));
                    content = content.replaceAll("INSERT_CONTACTCITY_INSERT", StandardCode.getInstance().noNull(client.getCity()));
                    content = content.replaceAll("INSERT_CONTACTSTATE_INSERT", StandardCode.getInstance().noNull(client.getState_prov()));
                    content = content.replaceAll("INSERT_CONTACTZIPCODE_INSERT", StandardCode.getInstance().noNull(client.getZip_postal_code()));

//            if (u.getWorkPhoneEx() != null && u.getWorkPhoneEx().length() > 0) { //ext present
                    content = content.replaceAll("INSERT_CONTACTPHONE_INSERT", StandardCode.getInstance().noNull(client.getWorkPhoneEx()));
//            } else { //no ext present
//                content = content.replaceAll("INSERT_CONTACTPHONE_INSERT", StandardCode.getInstance().noNull(u.getWorkPhone()));
//            }
//                    content = content.replaceAll("INSERT_CONTACTFAX_INSERT", StandardCode.getInstance().noNull(client.getFax_number()));
                    content = content.replaceAll("INSERT_CONTACTFAX_INSERT", StandardCode.getInstance().noNull(""));
                    content = content.replaceAll("INSERT_CONTACTEMAIL_INSERT", StandardCode.getInstance().noNull(client.getEmail_address()));
                } catch (Exception e2) {

                    User u = UserService.getInstance().getSingleUser(q.getEnteredById());

                    content = content.replaceAll("INSERT_CONTACTNAME_INSERT", StandardCode.getInstance().noNull(u.getFirstName()) + " " + StandardCode.getInstance().noNull(u.getLastName()));
                    content = content.replaceAll("INSERT_CONTACTTITLE_INSERT", StandardCode.getInstance().noNull(" "));
                    content = content.replaceAll("INSERT_CONTACTADDRESS1_INSERT", StandardCode.getInstance().noNull(u.getWorkAddress()));
                    content = content.replaceAll("INSERT_CONTACTADDRESS2_INSERT", StandardCode.getInstance().noNull(" "));
                    content = content.replaceAll("INSERT_CONTACTCITY_INSERT", StandardCode.getInstance().noNull(u.getWorkCity()));
                    content = content.replaceAll("INSERT_CONTACTSTATE_INSERT", StandardCode.getInstance().noNull(u.getWorkState()));
                    content = content.replaceAll("INSERT_CONTACTZIPCODE_INSERT", StandardCode.getInstance().noNull(u.getWorkZip()));

                    if (u.getWorkPhoneEx() != null && u.getWorkPhoneEx().length() > 0) { //ext present
                        content = content.replaceAll("INSERT_CONTACTPHONE_INSERT", StandardCode.getInstance().noNull(u.getWorkPhone()) + " ext " + StandardCode.getInstance().noNull(u.getWorkPhoneEx()));
                    } else { //no ext present
                        content = content.replaceAll("INSERT_CONTACTPHONE_INSERT", StandardCode.getInstance().noNull(u.getWorkPhone()));
                    }
                    content = content.replaceAll("INSERT_CONTACTFAX_INSERT", StandardCode.getInstance().noNull(""));
                    content = content.replaceAll("INSERT_CONTACTEMAIL_INSERT", StandardCode.getInstance().noNull(u.getWorkEmail1()));
                }
            }
        }
    String notesTxt="{\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\cs18\\\\fs22\\\\insrsid8195069\\\\charrsid11762701 NOTE}{\\\\rtlch\\\\fcs1 \\\\af0\\\\afs22 \\\\ltrch\\\\fcs0 \\\\cs18\\\\fs22\\\\insrsid6383104\\\\charrsid11762701 \\\\par }";

String NOTES = "";
                     
        INDelivery indel=InteqaService.getInstance().getINDelivery(q.getProject().getProjectId());
        if(indel!=null){
            List inDel = InteqaService.getInstance().getInDeliveryReqGrid(indel.getId(),"R");
            for (int i = 0; i < inDel.size(); i++) {
                INDeliveryReq inDeliveryReq = (INDeliveryReq) inDel.get(i);
                NOTES+= notesTxt.replaceAll("NOTE",inDeliveryReq.getClientReqText());
                //NOTES.add(NOTE);
                content = content.replaceAll("NOTE",inDeliveryReq.getClientReqText());
                
            }
        } 
        content = content.replaceAll("INSERT_REQUIREMENT_INSERT", NOTES);

        StringBuffer files = new StringBuffer("");
        if (q.getFiles() != null) {
            for (Iterator iter = q.getFiles().iterator(); iter.hasNext();) {
                File f = (File) iter.next();
                if (f.getBeforeAnalysis().equals("false")) {
                    files.append(StandardCode.getInstance().noNull(f.getFileName() + ", "));
                }
            }
        }


        String med = "";
        String detail = "";
        String category = "";
        List quoteList = QuoteService.getInstance().getSingleClientQuote(id);
        for (int i = 0; i < quoteList.size(); i++) {
            Client_Quote cq = (Client_Quote) quoteList.get(i);
            Product prod = ClientService.getSingleProduct(cq.getProduct_ID());
            detail += cq.getType();
            med += StandardCode.getInstance().noNull(cq.getMedical());
            category += prod.getCategory();


            if (i != quoteList.size() - 1) {
                detail += ",";
                med += ",";
                category += ",";
            }
        }
        //System.out.println("ddddddddddddddddddddd" + category + med);
        Upload_Doc ud1 = QuoteService.getInstance().getUploadDoc(id);
        List pname = QuoteService.getInstance().getUploadDocList(id);
        String uDoc = null;
        if (ud1 != null) {
            for (int i = 0; i < pname.size(); i++) {
                Upload_Doc ud = (Upload_Doc) pname.get(i);
                uDoc += ud.getPathname();
    content = content.replaceAll("INSERT_FILE_DATE_INSERT","" + ud.getUploadDate());
                if (i != pname.size() - 1) {
                    uDoc += ",";
                }
//File No.=i+1  :
            }
        }
        content = content.replaceAll("INSERT_FILE_DATE_INSERT","" );
        //content = content.replaceAll("INSERT_FILELIST_INSERT", StandardCode.getInstance().noNull(files.toString()));
        content = content.replaceAll("INSERT_PRODUCT_HERE", StandardCode.getInstance().noNull(q.getProject().getProduct()));
//        content = content.replaceAll("INSERT_CATEGORY_HERE", StandardCode.getInstance().noNull(category));
        content = content.replaceAll("INSERT_CATEGORY_HERE", "");
        content = content.replaceAll("INSERT_TYPE_HERE", StandardCode.getInstance().noNull(med));
        content = content.replaceAll("INSERT_DETAIL_HERE", StandardCode.getInstance().noNull(detail));

//INSERT_FILELIST_INSERT
        content = content.replaceAll("INSERT_FILELIST_INSERT", StandardCode.getInstance().noNull(uDoc));

        Double linPrice=0.0;
        Double linAmt=0.0;
        Double dtpAmt=0.0;
        Double engAmt=0.0;
        Double othAmt=0.0;
        Double linTaskIcr=0.0;
        Integer counter=1;
        
        Double   Rate_MultiLingual=0.0;
        Double   Count_MultiLingual=0.0;
        Double   Cost_MultiLingual=0.0;
        Double Total_Cost=0.0, Total_Word=0.0;
        
        String linAmtTotal;
        String dtpAmtTotal;
        String engAmtTotal;
        String othAmtTotal;
        
        //Description of Tasks Included in Pricing
        ArrayList tasksIncludedLin = new ArrayList();
        ArrayList tasksIncludedEng = new ArrayList();
        ArrayList tasksIncludedDtp = new ArrayList();
        ArrayList tasksIncludedOth = new ArrayList();
        if (q.getSourceDocs() != null) {
            for (Iterator iterSource = q.getSourceDocs().iterator(); iterSource.hasNext();) {
                SourceDoc sd = (SourceDoc) iterSource.next();
                if (sd.getTargetDocs() != null) {
                    for (Iterator iterTarget = sd.getTargetDocs().iterator(); iterTarget.hasNext();) {
                        TargetDoc td = (TargetDoc) iterTarget.next();

                         if(!td.getLanguage().equalsIgnoreCase("ALL")){
                        linAmt=0.0;
                        dtpAmt=0.0;
                        engAmt=0.0;
                        othAmt=0.0;
                        linTaskIcr=0.0;
                        

                        if (td.getLinTasks() != null) {
                            for (Iterator iterLin = td.getLinTasks().iterator(); iterLin.hasNext();) {
                                LinTask t = (LinTask) iterLin.next();
                                //check if in list
                                boolean isIn = false;
                                for (ListIterator li = tasksIncludedLin.listIterator(); li.hasNext();) {
                                    String displayText = (String) li.next();
                                    if (t.getTaskName().equals("Other")) { //use notes
                                        if (displayText.equals(t.getNotes()) && !isIn) {
                                            isIn = true;
                                        }
                                    } else { //use taskName
                                        if (displayText.equals(t.getTaskName()) && !isIn) {
                                            isIn = true;
                                            
                                            
                                            
                                        }
                                    }
                                }
                                if (!isIn) {
                                    if (t.getTaskName().equals("Other")) { //use notes
                                        tasksIncludedLin.add(t.getNotes());
                                    } else { //use taskName
                                        tasksIncludedLin.add(t.getTaskName());
                                    }
                                }
                            }
                        }//end linTasks

                        if (td.getEngTasks() != null) {
                            for (Iterator iterEng = td.getEngTasks().iterator(); iterEng.hasNext();) {
                                EngTask t = (EngTask) iterEng.next();
                                //check if in list
                                boolean isIn = false;
                                for (ListIterator li = tasksIncludedEng.listIterator(); li.hasNext();) {
                                    String displayText = (String) li.next();
                                    if (t.getTaskName().equals("Other")) { //use notes
                                        if (displayText.equals(t.getNotes()) && !isIn) {
                                            isIn = true;
                                        }
                                    } else { //use taskName
                                        if (displayText.equals(t.getTaskName()) && !isIn) {
                                            isIn = true;
                                        }
                                    }
                                }
                                if (!isIn) {
                                    if (t.getTaskName().equals("Other")) { //use notes
                                        tasksIncludedEng.add(t.getNotes());
                                    } else { //use taskName
                                        tasksIncludedEng.add(t.getTaskName());
                                    }
                                }
                            }
                        }//end engTasks

                        if (td.getDtpTasks() != null) {
                            for (Iterator iterDtp = td.getDtpTasks().iterator(); iterDtp.hasNext();) {
                                DtpTask t = (DtpTask) iterDtp.next();
                                //check if in list
                                boolean isIn = false;
                                for (ListIterator li = tasksIncludedDtp.listIterator(); li.hasNext();) {
                                    String displayText = (String) li.next();
                                    if (t.getTaskName().equals("Other")) { //use notes
                                        if (displayText.equals(t.getNotes()) && !isIn) {
                                            isIn = true;
                                        }
                                    } else { //use taskName
                                        if (displayText.equals(t.getTaskName()) && !isIn) {
                                            isIn = true;
                                            //Count_Page=0.0,  Count_Hour=0.0,  Count_MultiLingual=0.0
                                            //Rate_Page=0.0,  Rate_Hour=0.0,  Rate_MultiLingual=0.0
                                            //Cost_Page=0.0,  Cost_Hour=0.0,  Cost_MultiLingual=0.0
                                            

                                        }
                                    }
                                }
                                if (!isIn) {
                                    if (t.getTaskName().equals("Other")) { //use notes
                                        tasksIncludedDtp.add(t.getNotes());
                                    } else { //use taskName
                                        tasksIncludedDtp.add(t.getTaskName());
                                    }
                                }
                            }
                        }//end dtpTasks

                        if (td.getOthTasks() != null) {
                            for (Iterator iterOth = td.getOthTasks().iterator(); iterOth.hasNext();) {
                                OthTask t = (OthTask) iterOth.next();
                                //check if in list
                                boolean isIn = false;
                                for (ListIterator li = tasksIncludedOth.listIterator(); li.hasNext();) {
                                    String displayText = (String) li.next();
                                    if (t.getTaskName().equals("Other")) { //use notes
                                        if (displayText.equals(t.getNotes()) && !isIn) {
                                            isIn = true;
                                        }
                                    } else { //use taskName
                                        if (displayText.equals(t.getTaskName()) && !isIn) {
                                            isIn = true;
                                        }
                                    }
                                }
                                if (!isIn) {
                                    if (t.getTaskName().equals("Other")) { //use notes
                                        tasksIncludedOth.add(t.getNotes());
                                    } else { //use taskName
                                        tasksIncludedOth.add(t.getTaskName());
                                    }
                                }
                            }
                        }//end othTasks


        if (othAmt != 0) {

            content = content.replaceAll("INSERT_OTHER", "Other (specify)");
            content = content.replaceAll("INSERT_OTHPRICE_INSERT", "\\$" + StandardCode.getInstance().formatDouble(new Double(othAmt)));
        } else {
            content = content.replaceAll("INSERT_OTHER", "");
            content = content.replaceAll("INSERT_OTHPRICE_INSERT", "");
        }

//        content = content.replaceAll("INSERT_TOTALPRICE", StandardCode.getInstance().formatDouble(new Double(totalPricePerLang)));
                        }else{
                            flag=1;
                            
                             for (Iterator dtpTaskIter = td.getDtpTasks().iterator(); dtpTaskIter.hasNext();) {
                    DtpTask dt = (DtpTask) dtpTaskIter.next();
                    if(dt.getTaskName().contains("Multilingual")){
                     Rate_MultiLingual=Double.parseDouble(dt.getRate());
                     Count_MultiLingual=dt.getTotalTeam();
                     Cost_MultiLingual=Rate_MultiLingual*Count_MultiLingual;
                                 }
                            }
                        }
                    }
                }
            }
        }
 

        StringBuffer tasks = new StringBuffer("");
        int size=(tasksIncludedLin.size() > tasksIncludedEng.size()) ? tasksIncludedLin.size() : tasksIncludedEng.size();
        size=(size > tasksIncludedDtp.size()) ? size : tasksIncludedDtp.size();
        size=(size > tasksIncludedOth.size()) ? size : tasksIncludedOth.size();
        
        //if(tasksIncludedLin.size()>tasksIncludedEng.size())?size=tasksIncludedLin.size;
        for(int i=0;i<size;i++){
            try{content = content.replaceAll("INSERT_TASK_HERE_INSERT", taskText);}catch(Exception e){}
            try{content = content.replaceAll("Lin1", (String) tasksIncludedLin.get(i));}catch(Exception e){}
            try{content = content.replaceAll("QA1", (String) tasksIncludedEng.get(i));}catch(Exception e){}
            try{content = content.replaceAll("Format1", (String) tasksIncludedDtp.get(i));}catch(Exception e){}
            try{content = content.replaceAll("Other1", (String) tasksIncludedOth.get(i));}catch(Exception e){}
//        String display = (String) tasksIncludedLin.get(i);
        }

         try{content = content.replaceAll("INSERT_TASK_HERE_INSERT", "");}catch(Exception e){}
            try{content = content.replaceAll("Lin1", "");}catch(Exception e){}
            try{content = content.replaceAll("QA1", "");}catch(Exception e){}
            try{content = content.replaceAll("Format1", "");}catch(Exception e){}
            try{content = content.replaceAll("Other1", "");}catch(Exception e){}

        for (ListIterator iterDisplay = tasksIncludedLin.listIterator(); iterDisplay.hasNext();) {
            String display = (String) iterDisplay.next();
            tasks.append(StandardCode.getInstance().noNull(display) + ", ");
        }
        for (ListIterator iterDisplay = tasksIncludedEng.listIterator(); iterDisplay.hasNext();) {
            String display = (String) iterDisplay.next();
            tasks.append(StandardCode.getInstance().noNull(display) + ", ");
        }
        for (ListIterator iterDisplay = tasksIncludedDtp.listIterator(); iterDisplay.hasNext();) {
            String display = (String) iterDisplay.next();
            tasks.append(StandardCode.getInstance().noNull(display) + ", ");
        }
        for (ListIterator iterDisplay = tasksIncludedOth.listIterator(); iterDisplay.hasNext();) {
            String display = (String) iterDisplay.next();
            tasks.append(StandardCode.getInstance().noNull(display) + ", ");
        }
//        content = content.replaceAll("INSERT_TASKS_INSERT", StandardCode.getInstance().noNull(tasks.toString()));

        //get sources and targets
        String curr = new String("");
        StringBuffer sources = new StringBuffer("");
        StringBuffer targets = new StringBuffer("");
        StringBuffer languages = new StringBuffer("");
        int langCounter = 1;
        if (q.getSourceDocs() != null) {
            for (Iterator iterSource = q.getSourceDocs().iterator(); iterSource.hasNext();) {
                SourceDoc sd = (SourceDoc) iterSource.next();
                sources.append(sd.getLanguage() + " ");
                languages.append(sd.getLanguage() + "to");
                if (sd.getTargetDocs() != null) {
                    for (Iterator iterTarget = sd.getTargetDocs().iterator(); iterTarget.hasNext();) {
                        TargetDoc td = (TargetDoc) iterTarget.next();
                        if (!td.getLanguage().equals("All")) {
                            targets.append(td.getLanguage() + "");
                            content = content.replaceAll("INSERT_LANGUAGES_INSERT",langCounter++ +": "+ StandardCode.getInstance().noNull(td.getLanguage()) +langTxt);
                           // languages.append(" " + td.getLanguage() + ", ");
                            if ((curr.length() == 0) && (td.getLinTasks() != null)) {
                                for (Iterator iterTasks = td.getLinTasks().iterator(); iterTasks.hasNext();) {
                                    LinTask lt = (LinTask) iterTasks.next();
                                    if (lt.getCurrency() != null && lt.getCurrency().length() > 0) {
                                        curr = lt.getCurrency();
                                    }
                                }
                            }
                        }






                    }
//                    if (languages != null && languages.toString().endsWith(", ")) {
//                        languages = new StringBuffer(languages.toString().substring(0, languages.toString().length() - 2));
//                    }
//                    languages.append("\n. ");
                    
                }
            }
        }

        content = content.replaceAll("INSERT_LANGUAGES_INSERT","");
        content = content.replaceAll("INSERT_SOURCE_INSERT", StandardCode.getInstance().noNull(sources.toString()));
        content = content.replaceAll("INSERT_SOURCE_INSERT", "");

        content = content.replaceAll("INSERT_DELIVER_INSERT", StandardCode.getInstance().noNull(q.getProject().getDeliverableTechNotes()));

        //START find billing details (tasks and changes)
        //get this project's sources
        Set sourceList = q.getSourceDocs();

        //for each source add each sources' Tasks
        java.util.List totalLinTasks = new java.util.ArrayList();
        java.util.List totalEngTasks = new java.util.ArrayList();
        java.util.List totalDtpTasks = new java.util.ArrayList();
        java.util.List totalOthTasks = new java.util.ArrayList();

        //for each source
        for (Iterator sourceIter = sourceList.iterator(); sourceIter.hasNext();) {
            SourceDoc sd = (SourceDoc) sourceIter.next();

            //for each target of this source
            for (Iterator linTargetIter = sd.getTargetDocs().iterator(); linTargetIter.hasNext();) {
                TargetDoc td = (TargetDoc) linTargetIter.next();
//                if(!td.getLanguage().equalsIgnoreCase("all")){

        Double New_Rate=0.0, Fuzzy_Rate=0.0,  Rep_Rate=0.0,  Rate100=0.0,  Rate_Page=0.0,  Rate_Hour=0.0;
        Double New_Word=0.0, Fuzzy_Word=0.0,  Rep_Word=0.0,  Word100=0.0,  Count_Page=0.0,  Count_Hour=0.0;
        Double New_Cost=0.0, Fuzzy_Cost=0.0,  Rep_Cost=0.0,  Cost100=0.0,  Cost_Page=0.0,  Cost_Hour=0.0;
//        Double Total_Cost=0.0, Total_Word=0.0;
        
        
                Double taskTotal=0.0;

                         
                          if(!td.getLanguage().equalsIgnoreCase("all")){
                              content = content.replaceAll("Replace_Table_Per_Language", tableTxt);
                         content = content.replaceAll("Target_Language1",counter++ +": "+ td.getLanguage());
                         content = content.replaceAll("Target_Language2",td.getLanguage());
                String units="";
                //for each lin Task of this target
                for (Iterator linTaskIter = td.getLinTasks().iterator(); linTaskIter.hasNext();) {
                    LinTask lt = (LinTask) linTaskIter.next();
                    taskTotal+=Double.parseDouble(lt.getDollarTotal().replaceAll(",", ""));
                     if (lt.getTaskName().equalsIgnoreCase("Translation"))
                                            {
                                                Total_Cost=0.0; Total_Word=0.0;
                                                units=lt.getUnits();
                                                double wdNew=0.00,wd100=0.00,wdRep=0.00,wdFuzzy=0.00;
                                                try{
                                                    wdNew=lt.getWordNew().doubleValue()+lt.getWord85().doubleValue()+lt.getWord75().doubleValue();
                                                    New_Word += wdNew;
                                                }catch(Exception e){}
                                                try{
                                                    wd100=lt.getWord100().doubleValue()+lt.getWordPerfect().doubleValue()+lt.getWordContext();
                                                    Word100+=wd100;}catch(Exception e){}
                                                try{
                                                    wdRep = lt.getWordRep().doubleValue();
                                                    Rep_Word+=wdRep;}catch(Exception e){}
                                                try{
                                                    wdFuzzy=lt.getWord95().doubleValue();
                                                    Fuzzy_Word+=wdFuzzy;}catch(Exception e){}
                                                Total_Word += wdNew+wd100+wdRep+wdFuzzy;
                                                Total_Cost += Double.parseDouble(lt.getDollarTotal().replaceAll(",", ""));
                                                // New_Rate=0.0, Fuzzy_Rate=0.0,  Rep_Rate=0.0,  Rate100=0.0,
                                                try{New_Rate =Double.parseDouble(lt.getRate())*Double.parseDouble(c.getScaleNew(q.getProject().getProjectId(),c.getClientId()));}catch(Exception e){}
                                                try{Rate100=Double.parseDouble(lt.getRate())*Double.parseDouble(c.getScale100(q.getProject().getProjectId(),c.getClientId()));}catch(Exception e){}
                                                try{Rep_Rate=Double.parseDouble(lt.getRate())*Double.parseDouble(c.getScaleRep(q.getProject().getProjectId(),c.getClientId()));}catch(Exception e){}
                                                try{Fuzzy_Rate=Double.parseDouble(lt.getRate())*Double.parseDouble(c.getScale95(q.getProject().getProjectId(),c.getClientId()));}catch(Exception e){}
                                                //New_Cost=0.0, Fuzzy_Cost=0.0,  Rep_Cost=0.0,  Cost100=0.0
                                                try{New_Cost += wdNew*New_Rate;}catch(Exception e){}
                                                try{Fuzzy_Cost+=wdFuzzy*Fuzzy_Rate;}catch(Exception e){}
                                                try{Rep_Cost+=wdRep*Rep_Rate;}catch(Exception e){}
                                                try{Cost100+=wd100*Rate100;}catch(Exception e){}
         
                                            }
                    totalLinTasks.add(lt);
                }
                try{content = content.replaceAll("New_Rate", "\\$ "+StandardCode.getInstance().formatDouble4(new Double(New_Rate))+"/Word ");}catch(Exception e){}
        try{content = content.replaceAll("New_Word", StandardCode.getInstance().formatDouble0(new Double(New_Word))+" "+units);}catch(Exception e){}
        try{content = content.replaceAll("New_Cost","\\$ "+ StandardCode.getInstance().formatDouble(new Double(New_Cost)));}catch(Exception e){}

        try{content = content.replaceAll("Fuzzy_Rate","\\$ "+ StandardCode.getInstance().formatDouble4(new Double(Fuzzy_Rate))+"/Word ");}catch(Exception e){}
        try{content = content.replaceAll("Fuzzy_Word", StandardCode.getInstance().formatDouble0(new Double(Fuzzy_Word))+" "+units);}catch(Exception e){}
        try{content = content.replaceAll("Fuzzy_Cost","\\$ "+ StandardCode.getInstance().formatDouble(new Double(Fuzzy_Cost)));}catch(Exception e){}

        try{content = content.replaceAll("Rep_Rate","\\$ "+ StandardCode.getInstance().formatDouble4(new Double(Rep_Rate))+"/Word ");}catch(Exception e){}
        try{content = content.replaceAll("Rep_Word", StandardCode.getInstance().formatDouble0(new Double(Rep_Word))+" "+units);}catch(Exception e){}
        try{content = content.replaceAll("Rep_Cost","\\$ "+ StandardCode.getInstance().formatDouble(new Double(Rep_Cost)));}catch(Exception e){}

        try{content = content.replaceAll("100_Rate","\\$ "+ StandardCode.getInstance().formatDouble4(new Double(Rate100))+"/Word ");}catch(Exception e){}
        try{content = content.replaceAll("100_Word", StandardCode.getInstance().formatDouble0(new Double(Word100))+" "+units);}catch(Exception e){}
        try{content = content.replaceAll("100_Cost","\\$ "+ StandardCode.getInstance().formatDouble(new Double(Cost100)));}catch(Exception e){}
        
        try{content = content.replaceAll("Total_Word", StandardCode.getInstance().formatDouble0(Total_Word)+" "+units);}catch(Exception e){}
        try{content = content.replaceAll("Total_Cost","\\$ "+ StandardCode.getInstance().formatDouble(Total_Cost));}catch(Exception e){}   
}else{
                    content = content.replaceAll("Target_Language1", td.getLanguage());
                         content = content.replaceAll("Target_Language2",td.getLanguage());

                }
    

                //for each eng Task of this target
                for (Iterator engTaskIter = td.getEngTasks().iterator(); engTaskIter.hasNext();) {
                    EngTask et = (EngTask) engTaskIter.next();
                    try{taskTotal+=Double.parseDouble(et.getDollarTotal());}catch(Exception e){}
                    totalEngTasks.add(et);
                }



                //for each dtp Task of this target
                for (Iterator dtpTaskIter = td.getDtpTasks().iterator(); dtpTaskIter.hasNext();) {
                    DtpTask dt = (DtpTask) dtpTaskIter.next();
                    taskTotal+=Double.parseDouble(dt.getDollarTotal().replaceAll(",", ""));
//                    if(flag==1){
//                     content = content.replaceAll("Rate_MultiLingual", StandardCode.getInstance().formatDouble(new Double(Rate_MultiLingual)));
//                                             content = content.replaceAll("Count_MultiLingual", StandardCode.getInstance().formatDouble(new Double(Count_MultiLingual)));
//                                             content = content.replaceAll("Cost_MultiLingual",c.getCcurrency()+ StandardCode.getInstance().formatDouble(new Double(Cost_MultiLingual)));
//
//                    }
                   if(!td.getLanguage().equalsIgnoreCase("all")){
                    if(dt.getUnits().equalsIgnoreCase("Hours")){
                    Rate_Hour=Double.parseDouble(dt.getRate());
                                            Count_Hour=dt.getTotalTeam();
                                            Cost_Hour=Rate_Hour*Count_Hour;

                                             content = content.replaceAll("Rate_Hour","\\$ "+ StandardCode.getInstance().formatDouble(new Double(Rate_Hour))+"/Hour ");
                                             content = content.replaceAll("Count_Hour", StandardCode.getInstance().formatDouble(new Double(Count_Hour))+" Hours");
                                             content = content.replaceAll("Cost_Hour","\\$ "+ StandardCode.getInstance().formatDouble(new Double(Cost_Hour)));
                                            }
                                            if(dt.getUnits().equalsIgnoreCase("Pages")){

                                            Rate_Page=Double.parseDouble(dt.getRate());
                                            Count_Page=dt.getTotalTeam();
                                            Cost_Page=Rate_Page*Count_Page;

                                            content = content.replaceAll("Rate_Page","\\$ "+ StandardCode.getInstance().formatDouble(new Double(Rate_Page))+"/Page ");
                                            content = content.replaceAll("Count_Page", StandardCode.getInstance().formatDouble(new Double(Count_Page))+" Pages");
                                            content = content.replaceAll("Cost_Page","\\$ "+ StandardCode.getInstance().formatDouble(new Double(Cost_Page)));

                  }}else{

                                             content = content.replaceAll("Rate_MultiLingual", "\\$ "+StandardCode.getInstance().formatDouble(new Double(Rate_MultiLingual)));
                                             content = content.replaceAll("Count_MultiLingual", StandardCode.getInstance().formatDouble(new Double(Count_MultiLingual)));
                                             content = content.replaceAll("Cost_MultiLingual","\\$ "+ StandardCode.getInstance().formatDouble(new Double(Cost_MultiLingual)));
                                             content = content.replaceAll("Included","\\$ "+ StandardCode.getInstance().formatDouble(new Double(taskTotal)));
                  }

                    totalDtpTasks.add(dt);
                }


        
                //for each oth Task of this target
                for (Iterator othTaskIter = td.getOthTasks().iterator(); othTaskIter.hasNext();) {
                    OthTask ot = (OthTask) othTaskIter.next();
                    try{taskTotal+=Double.parseDouble(ot.getDollarTotal().replaceAll(",", ""));}catch(Exception e){}
                    totalOthTasks.add(ot);
                }

                          
                          Double pmFee = 0.0;
                          if(q.getPmPercent()!=null){ pmFee=taskTotal*Double.parseDouble(q.getPmPercent())/100;}
                          content = content.replaceAll("Insert_PM_Insert","\\$ "+ StandardCode.getInstance().formatDouble(new Double(pmFee)));

                          taskTotal=taskTotal+pmFee;
                          content = content.replaceAll("Insert_amount_Insert","\\$ "+StandardCode.getInstance().formatDouble(new Double(taskTotal)));


             //   }
                          content = content.replaceAll("Rate_Hour", "");
        content = content.replaceAll("Count_Hour", "");
        content = content.replaceAll("Cost_Hour", "");

        content = content.replaceAll("Rate_Page", "");
        content = content.replaceAll("Count_Page", "");
        content = content.replaceAll("Cost_Page", "");

        content = content.replaceAll("Rate_MultiLingual", "");
        content = content.replaceAll("Count_MultiLingual", "");
        content = content.replaceAll("Cost_MultiLingual", "");

        content = content.replaceAll("New_Rate", "");
        content = content.replaceAll("New_Word", "");
        content = content.replaceAll("New_Cost", "");

        content = content.replaceAll("Fuzzy_Rate", "");
        content = content.replaceAll("Fuzzy_Word", "");
        content = content.replaceAll("Fuzzy_Cost", "");

        content = content.replaceAll("Rep_Rate", "");
        content = content.replaceAll("Rep_Word", "");
        content = content.replaceAll("Rep_Cost", "");

        content = content.replaceAll("100_Rate", "");
        content = content.replaceAll("100_Word", "");
        content = content.replaceAll("100_Cost", "");



            }
        }

        

        //array for display in jsp
        LinTask[] linTasksArray = (LinTask[]) totalLinTasks.toArray(new LinTask[0]);
        EngTask[] engTasksArray = (EngTask[]) totalEngTasks.toArray(new EngTask[0]);
        DtpTask[] dtpTasksArray = (DtpTask[]) totalDtpTasks.toArray(new DtpTask[0]);
        OthTask[] othTasksArray = (OthTask[]) totalOthTasks.toArray(new OthTask[0]);

        //find total of LinTasks
        double linTaskTotal = 0;
        double linTaskTotalIcr = 0;
        boolean gotFirstLin = false;
        Double linVolume = new Double(0.0);
        for (int i = 0; i < linTasksArray.length; i++) {
            if (!linTasksArray[i].getTaskName().equalsIgnoreCase("ICR") && !linTasksArray[i].getTaskName().equalsIgnoreCase("ICR ") && !linTasksArray[i].getTaskName().equalsIgnoreCase("In-Country Review (ICR)") && !linTasksArray[i].getTaskName().equalsIgnoreCase("In-Country / Client Review")) {

                if (!gotFirstLin && linTasksArray[i].getWordTotal() != null) {
                    linVolume = linTasksArray[i].getWordTotal();
                    gotFirstLin = true;
                }
                if (linTasksArray[i].getDollarTotal() != null) {
                    //remove comma's
                    String linTotal = linTasksArray[i].getDollarTotal();
                    linTotal = linTotal.replaceAll(",", "");
                    Double total = Double.valueOf(linTotal);
                    linTaskTotal += total.doubleValue();
                }
            } else {
                if (!gotFirstLin && linTasksArray[i].getWordTotal() != null) {
                    linVolume = linTasksArray[i].getWordTotal();
                    gotFirstLin = true;
                }
                if (linTasksArray[i].getDollarTotal() != null) {
                    //remove comma's
                    String linTotalIcr = linTasksArray[i].getDollarTotal();
                    linTotalIcr = linTotalIcr.replaceAll(",", "");
                    Double totalIcr = Double.valueOf(linTotalIcr);
                    linTaskTotalIcr += totalIcr.doubleValue();
                }
            }
        }

        //find total of EngTasks
        double engTaskTotal = 0;
        double engVolume = 0.0;
        for (int i = 0; i < engTasksArray.length; i++) {
            if (engTasksArray[i].getTotal() != null) {
                engVolume += engTasksArray[i].getTotal().doubleValue();
            }
            if (engTasksArray[i].getDollarTotal() != null) {
                //remove comma's
                String engTotal = engTasksArray[i].getDollarTotal();
                engTotal = engTotal.replaceAll(",", "");
                Double total = Double.valueOf(engTotal);
                engTaskTotal += total.doubleValue();
            }
        }

        //find total of DtpTasks
        double dtpTaskTotal = 0;
        double dtpVolume = 0.0;
        for (int i = 0; i < dtpTasksArray.length; i++) {
            if (dtpTasksArray[i].getTotal() != null) {
                dtpVolume += dtpTasksArray[i].getTotal().doubleValue();
            }
            if (dtpTasksArray[i].getDollarTotal() != null) {
                //remove comma's
                String dtpTotal = dtpTasksArray[i].getDollarTotal();
                dtpTotal = dtpTotal.replaceAll(",", "");
                Double total = Double.valueOf(dtpTotal);
                dtpTaskTotal += total.doubleValue();
            }
        }

        //find total of OthTasks
        double othTaskTotal = 0;
        double othVolume = 0.0;
        for (int i = 0; i < othTasksArray.length; i++) {
            if (othTasksArray[i].getTotal() != null) {
                othVolume += othTasksArray[i].getTotal().doubleValue();
            }
            if (othTasksArray[i].getDollarTotal() != null) {
                //remove comma's
                String othTotal = othTasksArray[i].getDollarTotal();
                othTotal = othTotal.replaceAll(",", "");
                Double total = Double.valueOf(othTotal);
                othTaskTotal += total.doubleValue();
            }
        }

        //quote TOTAL
        double quoteTotal = 0;
        if (q.getQuoteDollarTotal() != null) {
            quoteTotal = q.getQuoteDollarTotal().doubleValue();
        }

         Double totalPricePerLang=linAmt+dtpAmt+engAmt+othAmt+linTaskIcr;
                        Double pmcost=0.00;
                        try {
                             pmcost=totalPricePerLang*Double.parseDouble(q.getPmPercent())/100;
                        } catch (Exception e) {
                            pmcost=0.00;
                        }
                        totalPricePerLang=totalPricePerLang+pmcost;
//        content = content.replaceAll("INSERT_PMPRICE", StandardCode.getInstance().formatDouble(new Double(pmcost)));

        //find pm total
        double pmTotal = 0;
        try {
            String strPmSubTotal = q.getSubDollarTotal().replaceAll(",", "");
            if (q.getQuoteDollarTotal() != null) {
                pmTotal = q.getQuoteDollarTotal().doubleValue() - (new Double(strPmSubTotal)).doubleValue();
            }
        } catch (Exception e) {
        }
        content = content.replaceAll("INSERT_LINPRICE_INSERT", StandardCode.getInstance().formatDouble(new Double(linTaskTotal)));
        content = content.replaceAll("INSERT_DTPPRICE_INSERT", StandardCode.getInstance().formatDouble(new Double(dtpTaskTotal)));
        content = content.replaceAll("INSERT_ENGPRICE_INSERT", StandardCode.getInstance().formatDouble(new Double(engTaskTotal)));
        content = content.replaceAll("INSERT_PMPRICE_INSERT", StandardCode.getInstance().formatDouble(new Double(pmTotal)));
        content = content.replaceAll("INSERT_OTHPRICE_INSERT", StandardCode.getInstance().formatDouble(new Double(othTaskTotal)));
        content = content.replaceAll("INSERT_TOTALPRICE_INSERT", StandardCode.getInstance().formatDouble(new Double(quoteTotal)));
        content = content.replaceAll("INSERT_CURRENCY_INSERT", StandardCode.getInstance().noNull(curr));
       content = content.replaceAll("INSERT_REFERENCE_INSERT", StandardCode.getInstance().noNull(q.getNumber()));
        try {
            content = content.replaceAll("INSERT_LEADTIME_INSERT", StandardCode.getInstance().noNull(q.getProject().getBeforeWorkTurn()));
            content = content.replaceAll("INSERT_LEADTIMEUNITS_INSERT", StandardCode.getInstance().noNull(q.getProject().getBeforeWorkTurnUnits().toLowerCase()));
            
        } catch (Exception e) {
            content = content.replaceAll("INSERT_LEADTIME_INSERT", "");
            content = content.replaceAll("INSERT_LEADTIMEUNITS_INSERT", "");
            content = content.replaceAll("INSERT_REFERENCE_INSERT", "");
        }


                try {
            content = content.replaceAll("INSERT_LEADTIMEAFTER_INSERT", StandardCode.getInstance().noNull(q.getProject().getAfterWorkTurn()));
            content = content.replaceAll("INSERT_LEADTIMEUNITSAFTER_INSERT", StandardCode.getInstance().noNull(q.getProject().getAfterWorkTurnUnits().toLowerCase()));

        } catch (Exception e) {

            content = content.replaceAll("INSERT_LEADTIMEAFTER_INSERT", "");
            content = content.replaceAll("INSERT_LEADTIMEUNITSAFTER_INSERT", "");



        }
//                try{
//                content = content.replaceAll("INSERT_PMNAME_INSERT", StandardCode.getInstance().noNull(q.getProject().getPm()));
//
//                User myPm = UserService.getInstance().getSingleUserRealName(StandardCode.getInstance().getFirstName(q.getProject().getPm()), StandardCode.getInstance().getLastName(q.getProject().getPm()));
//               String[] fname=q.getProject().getPm().split(" ");
//                User pm=UserService.getInstance().getSingleUserRealName(fname[0],fname[1]);
//
//
//               content = content.replaceAll("INSERT_EMAIL_PM_INSERT", StandardCode.getInstance().noNull(pm.getWorkEmail1()));
//                content = content.replaceAll("INSERT_PHONE_EXTENSION_PM_INSERT", StandardCode.getInstance().noNull(pm.getWorkPhone()));
//                content = content.replaceAll("INSERT_FAX_PM_INSERT", StandardCode.getInstance().noNull(pm.getWorkPhoneEx()));
//                }catch(Exception e){
//
//                content = content.replaceAll("INSERT_PMNAME_INSERT", StandardCode.getInstance().noNull(""));
//                content = content.replaceAll("INSERT_EMAIL_PM_INSERT", StandardCode.getInstance().noNull(""));
//                content = content.replaceAll("INSERT_PHONE_EXTENSION_PM_INSERT", StandardCode.getInstance().noNull(""));
//                content = content.replaceAll("INSERT_FAX_PM_INSERT", StandardCode.getInstance().noNull(""));
//
//
//                }


        String faxno = "";
        try {
//            String[] fname = q.getProject().getPm().split(" ");
            User enteredBy = UserService.getInstance().getSingleUser((String)request.getSession(false).getAttribute("username"));
            try {
                if (enteredBy.getUserType().equalsIgnoreCase("client")) {
                    faxno = ClientService.getInstance().getClient(enteredBy.getId_client()).getFax_number();
                }
            } catch (Exception e) {
                faxno = enteredBy.getLocation().getFax_number();
            }
            try {
                content = content.replaceAll("INSERT_PMNAME_INSERT", StandardCode.getInstance().noNull(enteredBy.getFirstName()) + " " + StandardCode.getInstance().noNull(enteredBy.getLastName()));
            } catch (Exception e) {
            }

            try{
                content = content.replaceAll("INSERT_PM_TITLE_INSERT", StandardCode.getInstance().noNull(enteredBy.getPosition().getPosition()));
                
            }catch(Exception e){}
            content = content.replaceAll("INSERT_PM_TITLE_INSERT", "");
            content = content.replaceAll("INSERT_EMAIL_PM_INSERT", StandardCode.getInstance().noNull(enteredBy.getWorkEmail1()));
            content = content.replaceAll("INSERT_PHONE_EXTENSION_PM_INSERT", StandardCode.getInstance().noNull(enteredBy.getWorkPhone()) + " ext " + StandardCode.getInstance().noNull(enteredBy.getWorkPhoneEx()));

            content = content.replaceAll("INSERT_EXCELNAME_INSERT", "Excel Translations, Inc. ");
       content = content.replaceAll("INSERT_EXCELADDRESS1_INSERT", StandardCode.getInstance().noNull(enteredBy.getLocation().getAddress_1()));
       content = content.replaceAll("INSERT_EXCELADDRESS2_INSERT", StandardCode.getInstance().noNull(enteredBy.getLocation().getAddress_2()));
       content = content.replaceAll("INSERT_EXCELADDRESS3_INSERT", StandardCode.getInstance().noNull(enteredBy.getLocation().getCity()+", "
               +enteredBy.getLocation().getState_prov()+" "+enteredBy.getLocation().getZip_postal_code()+", "+enteredBy.getLocation().getCountry()));

        } catch (Exception e) {

            content = content.replaceAll("INSERT_EMAIL_PM_INSERT", StandardCode.getInstance().noNull(""));
            content = content.replaceAll("INSERT_PHONE_EXTENSION_PM_INSERT", StandardCode.getInstance().noNull(""));
            //content = content.replaceAll("INSERT_FAX_PM_INSERT", StandardCode.getInstance().noNull(""));

        }
        content = content.replaceAll("INSERT_FAX_PM_INSERT", faxno);

        if (linTaskTotalIcr != 0) {

            content = content.replaceAll("INSERT_ICRTASK", "ICR");
            content = content.replaceAll("INSERT_ICRPRICE_INSERT", "\\$" + StandardCode.getInstance().formatDouble(new Double(linTaskTotalIcr)));
        } else {
            content = content.replaceAll("INSERT_ICRTASK", "");
            content = content.replaceAll("INSERT_ICRPRICE_INSERT", "");
        }
        content = content.replaceAll("Replace_Table_Per_Language", "");


        //END content

        //write to client (web browser)
        response.setHeader("Content-Type", "Application/msword");
        response.setContentLength((int) content.length());  
//        response.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        String filename = q.getNumber() + "-" + q.getProject().getCompany().getCompany_name().replaceAll(" ", "_").replaceAll(",", "_") + "_quote.doc";
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
