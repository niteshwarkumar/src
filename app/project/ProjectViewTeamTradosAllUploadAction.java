/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import app.quote.*;
import app.standardCode.*;
import app.security.*;
import app.extjs.global.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
//org/apache/xmlbeans/XmlOptions
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
//import org.apache.poi.xssf.*;
import org.apache.poi.xssf.usermodel.*;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Neil
 */
public final class ProjectViewTeamTradosAllUploadAction extends Action {

//QuoteViewGeneralTradosUploadAction.java adds the new
//trados values to the current lin task.
//It also collects the file names from the .log file
//and adds them to this quote
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
        String projectId = null;
        projectId = request.getParameter("projectViewId");

        //check attribute in request
        if (projectId == null) {
            projectId = (String) request.getAttribute("projectViewId");
        }

        //id of quote from cookie
        if (projectId == null) {
            projectId = StandardCode.getInstance().getCookie("projectViewId", request.getCookies());
        }

        //default client to first if not in request or cookie
        if (projectId == null) {
            List results = QuoteService.getInstance().getQuoteList();
            Quote1 first = (Quote1) results.get(0);
            projectId = String.valueOf(first.getQuote1Id());
        }
//projectId="6553";
        Integer id = Integer.valueOf(projectId);
        String[] dataValue = new String[11];
        Project p = ProjectService.getInstance().getSingleProject(Integer.parseInt(projectId));
        //END get id of current quote from either request, attribute, or cookie

        //get quote to add files to
        // Quote1 q = QuoteService.getInstance().getSingleQuote(p.getQuotes());
        Set sources = p.getSourceDocs();
        //get the lin task to update
        //String linTaskId = StandardCode.getInstance().getCookie("quoteViewGeneralTradosUploadId", request.getCookies());
        System.out.println(sources.size());

        File folder = new File("C:/log");
        File[] listOfFiles = folder.listFiles();
        for (int ij = 0; ij < listOfFiles.length; ij++) {
            if (listOfFiles[ij].isFile() && (listOfFiles[ij].getName().endsWith(".log")) || listOfFiles[ij].getName().endsWith(".xls") || listOfFiles[ij].getName().endsWith(".xlsx") || listOfFiles[ij].getName().endsWith(".xml")) {
                String lang = "";
                System.out.println("File " + listOfFiles[ij].getName());
                String myFile = listOfFiles[ij].getName();
                Integer leng = myFile.length();
                if (listOfFiles[ij].getName().endsWith(".log")) {
                    lang = (String) LanguageAbs.getInstance().getAbs().get(myFile.substring(leng - 6, leng - 4));
                } else if (listOfFiles[ij].getName().endsWith(".xlsx")) {
                    lang = (String) LanguageAbs.getInstance().getAbs().get(myFile.substring(leng - 7, leng - 5));
                } else {
                    lang = (String) LanguageAbs.getInstance().getAbs().get(myFile.substring(leng - 6, leng - 4));
                }
                // List sourceLang = QuoteService.getInstance().getSourceLang1(q);
                for (Iterator sourceIter = sources.iterator(); sourceIter.hasNext();) {
                    SourceDoc sd = (SourceDoc) sourceIter.next();

                    List targetLang = QuoteService.getInstance().getTargetLang(sd.getSourceDocId());
                    for (Iterator linTargetIter = sd.getTargetDocs().iterator(); linTargetIter.hasNext();) {


                        TargetDoc td = (TargetDoc) linTargetIter.next();
                        if (td.getLanguage().equalsIgnoreCase(lang)) {

                            List linTasklist = QuoteService.getInstance().getLinTask(td.getTargetDocId());
                            for (int k = 0; k < linTasklist.size(); k++) {

                                LinTask lt = (LinTask) linTasklist.get(k);



                                //get input stream
                                //InputStream in = listOfFiles[ij].getInputStream();

                                if (listOfFiles[ij].getName().endsWith(".log")) {

                                    FileInputStream in = new FileInputStream(listOfFiles[ij]);
                                    //byte[] fileData = listOfFiles[ij].getFileData(); //byte array of entire file
                                    long length = listOfFiles[ij].length();

                                    byte[] fileData = new byte[(int) length];

                                    in.read(fileData); //read data into fileData
                                    String entireRead = new String(fileData); //the entire file as a string
                                    String[] lines = entireRead.split("\n"); //lines within the file

                                    String line = new String("");  //each line

                                    //scroll to totals
                                    int j = 0; //line numbers
                                    try {
                                        while (true) {
                                            line = lines[j++];
                                            if (line != null && line.length() > 12 && line.substring(0, 13).equals("Analyse Total")) {
                                                break;
                                            }
                                        }

                                        //move to repetitions line
                                        j++;
                                        j++;
                                        j++;

                                        String[] parts; //each number per line
                                        String wordRep = null;
                                        String word100 = null;
                                        String word95 = null;
                                        String word85 = null;
                                        String word75 = null;
                                        String word50 = null;
                                        String wordNo = null;
                                        String wordPerfect = null;
                                        String wordContext = null;
                                        String wordTotal = null;

                                        //wordRep
                                        line = lines[j++];
                                        parts = line.split(" ");
                                        for (int i = 0, counter = 0; i < parts.length; i++) {
                                            if (parts[i].length() > 0) { //look for non-blank items and count them
                                                counter++;
                                            }
                                            if (counter == 3) { //if at the words column
                                                wordRep = parts[i];
                                                break;
                                            }
                                        }

                                        //word100
                                        line = lines[j++];
                                        parts = line.split(" ");
                                        for (int i = 0, counter = 0; i < parts.length; i++) {
                                            if (parts[i].length() > 0) { //look for non-blank items and count them
                                                counter++;
                                            }
                                            if (counter == 3) { //if at the words column
                                                word100 = parts[i];
                                                break;
                                            }
                                        }

                                        //word95
                                        line = lines[j++];
                                        parts = line.split(" ");
                                        for (int i = 0, counter = 0; i < parts.length; i++) {
                                            if (parts[i].length() > 0) { //look for non-blank items and count them
                                                counter++;
                                            }
                                            if (counter == 5) { //if at the words column
                                                word95 = parts[i];
                                                break;
                                            }
                                        }

                                        //word85
                                        line = lines[j++];
                                        parts = line.split(" ");
                                        for (int i = 0, counter = 0; i < parts.length; i++) {
                                            if (parts[i].length() > 0) { //look for non-blank items and count them
                                                counter++;
                                            }
                                            if (counter == 5) { //if at the words column
                                                word85 = parts[i];
                                                break;
                                            }
                                        }

                                        //word75
                                        line = lines[j++];
                                        parts = line.split(" ");
                                        for (int i = 0, counter = 0; i < parts.length; i++) {
                                            if (parts[i].length() > 0) { //look for non-blank items and count them
                                                counter++;
                                            }
                                            if (counter == 5) { //if at the words column
                                                word75 = parts[i];
                                                break;
                                            }
                                        }

                                        //word50
                                        line = lines[j++];
                                        parts = line.split(" ");
                                        for (int i = 0, counter = 0; i < parts.length; i++) {
                                            if (parts[i].length() > 0) { //look for non-blank items and count them
                                                counter++;
                                            }
                                            if (counter == 5) { //if at the words column
                                                word50 = parts[i];
                                                break;
                                            }
                                        }

                                        //wordNo
                                        line = lines[j++];
                                        parts = line.split(" ");
                                        for (int i = 0, counter = 0; i < parts.length; i++) {
                                            if (parts[i].length() > 0) { //look for non-blank items and count them
                                                counter++;
                                            }
                                            if (counter == 4) { //if at the words column
                                                wordNo = parts[i];
                                                break;
                                            }
                                        }

                                        //wordTotal
                                        line = lines[j++];
                                        parts = line.split(" ");
                                        for (int i = 0, counter = 0; i < parts.length; i++) {
                                            if (parts[i].length() > 0) { //look for non-blank items and count them
                                                counter++;
                                            }
                                            if (counter == 3) { //if at the words column
                                                wordTotal = parts[i];
                                                break;
                                            }
                                        }
                                        //END process the trados .log file

                                        //remove commas from trados values
                                        wordRep = wordRep.replaceAll(",", "");
                                        word100 = word100.replaceAll(",", "");
                                        word95 = word95.replaceAll(",", "");
                                        word85 = word85.replaceAll(",", "");
                                        word75 = word75.replaceAll(",", "");
                                        word50 = word50.replaceAll(",", "");
                                        wordNo = wordNo.replaceAll(",", "");
                                        // wordPerfect=null;
                                        // wordContext=null;
                                        wordTotal = wordTotal.replaceAll(",", "");

                                        //convert trados values from strings to numbers
                                        Integer numRep = Integer.valueOf(wordRep);
                                        Integer num100 = Integer.valueOf(word100);
                                        Integer num95 = Integer.valueOf(word95);
                                        Integer num85 = Integer.valueOf(word85);
                                        Integer num75 = Integer.valueOf(word75);
                                        Integer num50 = Integer.valueOf(word50);
                                        Integer numNo = Integer.valueOf(wordNo);
                                        Double numTotal = Double.valueOf(wordTotal);

                                        //find totals to save to lin task
                                        int numNew = num50.intValue() + numNo.intValue();
                                        int num8599 = num95.intValue() + num85.intValue();
                                        int numNew4 = num75.intValue() + numNew;
                                        if (lt.getTaskName().equalsIgnoreCase("Translation")) {
                                            //set new trados values for the lin task
                                            lt.setWordRep(numRep);
                                            lt.setWord100(num100);
                                            lt.setWord95(num95);
                                            lt.setWord85(num85);
                                            lt.setWord75(num75);
                                            lt.setWordNew(new Integer(numNew));
                                            lt.setWord8599(new Integer(num8599));
                                            lt.setWordNew4(new Double(numNew4));
                                            lt.setWordTotal(numTotal);
                                        } else if (lt.getTaskName().equalsIgnoreCase("editing")) {

                                            lt.setWordNew4(numTotal);
                                            lt.setWordTotal(numTotal);

                                        }
                                        //upload the new trados values to db
                                        ProjectService.getInstance().updateLinTask(lt);

                                        //START get file list
                                        //get input stream

                                        in.close();
                                        in = new FileInputStream(listOfFiles[ij]);

                                        length = listOfFiles[ij].length();

                                        fileData = new byte[(int) length];

                                        in.read(fileData); //read data into fileData
                                        entireRead = new String(fileData); //the entire file as a string
                                        lines = entireRead.split("\n"); //lines within the file

                                        line = new String("");  //each line


                                    } catch (Exception e) {
                                    }
                                    in.close();

                                } else if (listOfFiles[ij].isFile() && listOfFiles[ij].getName().endsWith(".xls")) {

                                    POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("C:/log/" + listOfFiles[ij].getName()));

                                    HSSFWorkbook wb = new HSSFWorkbook(fs);
                                    HSSFSheet sheet = wb.getSheetAt(0);
                                    HSSFRow row;
                                    HSSFCell cell;
                                    int count = 0, i = 0;
                                    String flag = "true";

                                    Iterator rows = sheet.rowIterator();

                                    while (rows.hasNext()) {
                                        row = (HSSFRow) rows.next();
                                        count = 0;
                                        Iterator cells = row.cellIterator();
                                        while (cells.hasNext()) {


                                            cell = (HSSFCell) cells.next();

                                            count++;
                                            try {
                                                if (count == 4 && flag.equalsIgnoreCase("true")) {
                                                    dataValue[i++] = cell.toString();

                                                    System.out.println("cel value---------->  " + cell.toString());

                                                    if (i > 10) {
                                                        flag = "false";
                                                    }

                                                }
                                            } catch (Exception e) {
                                                System.out.println("Integer Value" + count++);
                                            }
                                        }
                                    }

                                    Integer numRep = Math.round(Float.parseFloat(dataValue[2]));
                                    Integer num100 = Math.round(Float.parseFloat(dataValue[4]));
                                    Integer num95 = Math.round(Float.parseFloat(dataValue[5]));
                                    Integer num85 = Math.round(Float.parseFloat(dataValue[6]));
                                    Integer num75 = Math.round(Float.parseFloat(dataValue[7]));
                                    Integer num50 = Math.round(Float.parseFloat(dataValue[8]));
                                    Integer numNo = Math.round(Float.parseFloat(dataValue[9]));
                                    Integer numPerfect = Math.round(Float.parseFloat(dataValue[1]));
                                    Integer numContext = Math.round(Float.parseFloat(dataValue[3]));
                                    Double numTotal = Double.valueOf(dataValue[10]);
                                    // numRep = Integer.parseInt(dataValue[1]);

                                    int numNew = num50.intValue() + numNo.intValue();
                                    int num8599 = num95.intValue() + num85.intValue();
                                    int numNew4 = num75.intValue() + numNew;
                                    if (lt.getTaskName().equalsIgnoreCase("Translation")) {
                                        //set new trados values for the lin task
                                        lt.setWordRep(numRep);
                                        lt.setWord100(num100);
                                        lt.setWord95(num95);
                                        lt.setWord85(num85);
                                        lt.setWord75(num75);
                                        lt.setWordNew(new Integer(numNew));
                                        lt.setWord8599(new Integer(num8599));
                                        lt.setWordNew4(new Double(numNew4));
                                        lt.setWordContext(numContext);
                                        lt.setWordPerfect(numPerfect);
                                        lt.setWordTotal(numTotal);
                                    } else if (lt.getTaskName().equalsIgnoreCase("editing")) {

                                        lt.setWordNew4(numTotal);
                                        lt.setWordTotal(numTotal);

                                    }
                                    //upload the new trados values to db
                                    ProjectService.getInstance().updateLinTask(lt);


                                } else if (listOfFiles[ij].isFile() && listOfFiles[ij].getName().endsWith(".xlsx")) {
//                                      POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("C:/log/" + listOfFiles[ij].getName()));
//                                    File file = new File("C:/log/" + listOfFiles[ij].getName());
//                                   OPCPackage pkg = OPCPackage.open(new FileInputStream(file.getAbsolutePath()));
//                                    XSSFWorkbook wb = new XSSFWorkbook(pkg);
                                    InputStream fs = new FileInputStream("C:/log/" + listOfFiles[ij].getName());
                                    XSSFWorkbook wb = new XSSFWorkbook(fs);

//                                    XSSFWorkbook wb = new XSSFWorkbook(fs);
                                    XSSFSheet sheet = wb.getSheetAt(0);
                                    XSSFRow row;
                                    XSSFCell cell;
                                    int count = 0, i = 0;
                                    String flag = "true";

                                    Iterator rows = sheet.rowIterator();

                                    while (rows.hasNext()) {
                                        row = (XSSFRow) rows.next();
                                        count = 0;
                                        Iterator cells = row.cellIterator();
                                        while (cells.hasNext()) {


                                            cell = (XSSFCell) cells.next();

                                            count++;
                                            try {
                                                if (count == 4 && flag.equalsIgnoreCase("true")) {
                                                    dataValue[i++] = cell.toString();

                                                    System.out.println("cel value---------->  " + cell.toString());

                                                    if (i > 10) {
                                                        flag = "false";
                                                    }

                                                }
                                            } catch (Exception e) {
                                                System.out.println("Integer Value" + count++);
                                            }
                                        }
                                    }

                                    Integer numRep = Math.round(Float.parseFloat(dataValue[2]));
                                    Integer num100 = Math.round(Float.parseFloat(dataValue[4]));
                                    Integer num95 = Math.round(Float.parseFloat(dataValue[5]));
                                    Integer num85 = Math.round(Float.parseFloat(dataValue[6]));
                                    Integer num75 = Math.round(Float.parseFloat(dataValue[7]));
                                    Integer num50 = Math.round(Float.parseFloat(dataValue[8]));
                                    Integer numNo = Math.round(Float.parseFloat(dataValue[9]));
                                    Double numTotal = Double.valueOf(dataValue[10]);
                                    // numRep = Integer.parseInt(dataValue[1]);

                                    int numNew = num50.intValue() + numNo.intValue();
                                    int num8599 = num95.intValue() + num85.intValue();
                                    int numNew4 = num75.intValue() + numNew;
                                    if (lt.getTaskName().equalsIgnoreCase("Translation")) {
                                        //set new trados values for the lin task
                                        lt.setWordRep(numRep);
                                        lt.setWord100(num100);
                                        lt.setWord95(num95);
                                        lt.setWord85(num85);
                                        lt.setWord75(num75);
                                        lt.setWordNew(new Integer(numNew));
                                        lt.setWord8599(new Integer(num8599));
                                        lt.setWordNew4(new Double(numNew4));
                                        lt.setWordTotal(numTotal);
                                    } else if (lt.getTaskName().equalsIgnoreCase("editing")) {

                                        lt.setWordNew4(numTotal);
                                        lt.setWordTotal(numTotal);

                                    }
                                    //upload the new trados values to db
                                    ProjectService.getInstance().updateLinTask(lt);

                                } else if (listOfFiles[ij].isFile() && listOfFiles[ij].getName().endsWith(".xml")) {

                                    InputStream in = new FileInputStream("C:/log/" + listOfFiles[ij].getName());
                                    System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
                                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                                    Document doc = dBuilder.parse(in);
                                    doc.getDocumentElement().normalize();
                                    System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
                                    Integer numRep = 0;
                                    Integer num100 = 0;
                                    Integer num95 = 0;
                                    Integer num85 = 0;
                                    Integer num75 = 0;
                                    Integer num50 = 0;
                                    Integer numNo = 0;
                                    Integer numTotal =0;
                                    Integer numContext = 0;
                                    Integer numPerfect =0;
                                    NodeList batchTotal = doc.getElementsByTagName("batchTotal");
//                                     Element eElement = (Element) batchTotal;
//                                      NodeList analyse = eElement.getElementsByTagName("analyse");
                                    if (batchTotal != null && batchTotal.getLength() > 0) {
                                        Node node = batchTotal.item(0);
                                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                                            Element eElement = (Element) node;
                                            NodeList analyse = eElement.getElementsByTagName("analyse");

                                            if (analyse != null && analyse.getLength() > 0) {
                                                Node node1 = analyse.item(0);
                                                if (node1.getNodeType() == Node.ELEMENT_NODE) {
                                                    Element eElement1 = (Element) node1;
//                                                    NodeList analyse1 = eElement.getElementsByTagName("analyse");

                                                    eElement1.getElementsByTagName("fuzzy").item(0).getTextContent();
                                                    NodeList fuzzy = doc.getElementsByTagName("fuzzy");

                                                    for (int temp = 0; temp < fuzzy.getLength(); temp++) {

                                                        Node nNode = fuzzy.item(temp);

                                                        System.out.println("\nCurrent Element :" + nNode.getNodeName());

                                                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                                                            Element eElement2 = (Element) nNode;
                                                            if (eElement2.getAttribute("min").equalsIgnoreCase("50") && eElement2.getAttribute("max").equalsIgnoreCase("74")) {
                                                                num50 = Integer.parseInt(eElement2.getAttribute("words"));
                                                            }
                                                            if (eElement2.getAttribute("min").equalsIgnoreCase("75") && eElement2.getAttribute("max").equalsIgnoreCase("84")) {
                                                                num75 = Integer.parseInt(eElement2.getAttribute("words"));
                                                            }
                                                            if (eElement2.getAttribute("min").equalsIgnoreCase("85") && eElement2.getAttribute("max").equalsIgnoreCase("94")) {
                                                                num85 = Integer.parseInt(eElement2.getAttribute("words"));
                                                            }
                                                            if (eElement2.getAttribute("min").equalsIgnoreCase("95") && eElement2.getAttribute("max").equalsIgnoreCase("99")) {
                                                                num95 = Integer.parseInt(eElement2.getAttribute("words"));
                                                            }

                                                        }
                                                    }

//                                                  

                                                    eElement1.getElementsByTagName("new").item(0).getTextContent();
                                                    NodeList new1 = doc.getElementsByTagName("new");
                                                    for (int temp = 0; temp < new1.getLength(); temp++) {
                                                        Node nNode = new1.item(temp);
                                                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                                            Element eElement2 = (Element) nNode;
                                                            numNo = Integer.parseInt(eElement2.getAttribute("words"));
                                                        }
                                                    }
                                                    eElement1.getElementsByTagName("total").item(0).getTextContent();
                                                    NodeList total = doc.getElementsByTagName("total");
                                                    for (int temp = 0; temp < total.getLength(); temp++) {
                                                        Node nNode = total.item(temp);
                                                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                                            Element eElement2 = (Element) nNode;
                                                            numTotal = Integer.parseInt(eElement2.getAttribute("words"));                                                          
                                                        }
                                                    }
                                                     eElement1.getElementsByTagName("exact").item(0).getTextContent();
                                                    NodeList exact = doc.getElementsByTagName("exact");
                                                    for (int temp = 0; temp < exact.getLength(); temp++) {
                                                        Node nNode = exact.item(temp);
                                                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                                            Element eElement2 = (Element) nNode;
                                                            num100 = Integer.parseInt(eElement2.getAttribute("words"));
                                                        }
                                                    }

                                                     eElement1.getElementsByTagName("perfect").item(0).getTextContent();
                                                    NodeList perfect = doc.getElementsByTagName("perfect");
                                                    for (int temp = 0; temp < perfect.getLength(); temp++) {
                                                        Node nNode = perfect.item(temp);
                                                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                                            Element eElement2 = (Element) nNode;
                                                            numPerfect = Integer.parseInt(eElement2.getAttribute("words"));
                                                        }
                                                    }


                                                     eElement1.getElementsByTagName("repeated").item(0).getTextContent();
                                                    NodeList repeated = doc.getElementsByTagName("repeated");
                                                    for (int temp = 0; temp < repeated.getLength(); temp++) {
                                                        Node nNode = repeated.item(temp);
                                                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                                            Element eElement2 = (Element) nNode;
                                                            numRep = Integer.parseInt(eElement2.getAttribute("words"));
                                                        }
                                                    }
                                                    try {
                                                          eElement1.getElementsByTagName("crossFileRepeated").item(0).getTextContent();
                                                    NodeList crossFileRepeated  = doc.getElementsByTagName("crossFileRepeated");
                                                    for (int temp = 0; temp < crossFileRepeated.getLength(); temp++) {
                                                        Node nNode = crossFileRepeated.item(temp);
                                                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                                            Element eElement2 = (Element) nNode;
                                                            numRep += Integer.parseInt(eElement2.getAttribute("words"));
                                                        }
                                                    }
                                                    } catch (Exception e) {
                                                    }

                                                     eElement1.getElementsByTagName("inContextExact").item(0).getTextContent();
                                                    NodeList inContextExact = doc.getElementsByTagName("inContextExact");
                                                    for (int temp = 0; temp < inContextExact.getLength(); temp++) {
                                                        Node nNode = inContextExact.item(temp);
                                                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                                            Element eElement2 = (Element) nNode;
                                                            numContext = Integer.parseInt(eElement2.getAttribute("words"));
                                                        }
                                                    }


                                                }
                                            }
                                        }
                                    }


                                    int numNew = num50.intValue() + numNo.intValue();
                                    int num8599 = num95.intValue() + num85.intValue();
                                    int numNew4 = num75.intValue() + numNew;
                                    if (lt.getTaskName().equalsIgnoreCase("Translation")) {
                                        //set new trados values for the lin task
                                        lt.setWordRep(numRep);
                                        lt.setWord100(num100);
                                        lt.setWord95(num95);
                                        lt.setWord85(num85);
                                        lt.setWord75(num75);
                                        lt.setWordNew(new Integer(numNew));
                                        lt.setWord8599(new Integer(num8599));
                                        lt.setWordNew4(new Double(numNew4));
                                        lt.setWordTotal(new Double(numTotal));
                                        lt.setWordContext(numContext);
                                        lt.setWordPerfect(numPerfect);
                                    } else if (lt.getTaskName().equalsIgnoreCase("editing")) {
                                        lt.setWordNew(new Integer(numTotal));
                                        lt.setWordNew4(new Double(numTotal));
                                        lt.setWordTotal(new Double(numTotal));
                                    }
                                    //upload the new trados values to db
                                    ProjectService.getInstance().updateLinTask(lt);

                                } else {
                                     System.out.println("no Match");
                                     request.setAttribute("isError", "error");
                                     return (mapping.findForward("Error"));
                                    
                                }

                            }
                        }
                    }

                }

            } else if (listOfFiles[ij].isDirectory()) {
                System.out.println("Directory " + listOfFiles[ij].getName());
            }
        }

        deleteFile("C:/log");
        //END get file list

        // Forward control to the specified success URI
        return (mapping.findForward("Success"));
    }

    public static boolean deleteFile(String sFilePath) {
        File oFile = new File(sFilePath);
        if (oFile.isDirectory()) {
            File[] aFiles = oFile.listFiles();
            for (File oFileCur : aFiles) {
                deleteFile(oFileCur.getAbsolutePath());
            }
        }
        return oFile.delete();
    }
}
