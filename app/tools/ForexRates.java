/*
 * ForexRates.java
 *
 * Created on August 25, 2009, 8:32 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package app.tools;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import org.w3c.dom.Document;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


/**
 *
 * @author PP41387
 */
public class ForexRates {

    private static final String defaultServer
   = "http://rss.timegenie.com/forex.xml";

    /** Creates a new instance of ForexRates */
    private ForexRates() {
    }

    public static double getEuroUsdRate(){

        double result = 1.40;

        try{

            /*
            System.getProperties().put("proxySet","true");
            System.getProperties().put("proxyPort","8080");
            System.getProperties().put("proxyHost","ipsbcproxy.pncbank.com");
    */


            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

            //tsuji:note [b] change this
            //Document doc = docBuilder.parse (new File("book.xml"));
            URL url = new URL(defaultServer);
            InputStream stream = url.openStream();
            Document doc = docBuilder.parse(stream);

            // normalize text representation
            doc.getDocumentElement ().normalize ();
           // System.out.println ("Root element of the doc is " +
            //     doc.getDocumentElement().getNodeName());
            NodeList listOfPersons = doc.getElementsByTagName("data");
            int totalPersons = listOfPersons.getLength();
            //System.out.println("Total no of currencies : " + totalPersons);
            for(int s=0; s<listOfPersons.getLength() ; s++){
                Node firstPersonNode = listOfPersons.item(s);
                if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE){
                    Element firstPersonElement = (Element)firstPersonNode;
                    //-------
                    NodeList currencyCodeList = firstPersonElement.getElementsByTagName("code");
                    Element currencyCodeElement = (Element)currencyCodeList.item(0);
                    NodeList textFNList = currencyCodeElement.getChildNodes();
                    String ccyCode = ((Node)textFNList.item(0)).getNodeValue().trim();
                    if("USD".equals(ccyCode)){
                        NodeList rateList = firstPersonElement.getElementsByTagName("rate");
                        Element rateElement = (Element)rateList.item(0);
                        NodeList rateFNList = rateElement.getChildNodes();
                        String rate = ((Node)rateFNList.item(0)).getNodeValue().trim();
                        //System.out.println("EUR/USD rate:"+rate);
                        result = Double.parseDouble(rate);
                    }

                    //------
                }//end of if clause

            }//end of for loop with s var

        }catch (SAXParseException err) {
        System.out.println ("** Parsing error" + ", line "
             + err.getLineNumber () + ", uri " + err.getSystemId ());
        System.out.println(" " + err.getMessage ());
        }catch (SAXException e) {
        Exception x = e.getException ();
        ((x == null) ? e : x).printStackTrace ();
        }catch (Throwable t) {
        t.printStackTrace ();
        }
        //System.exit (0);
        return result;
    }//end of main






}
