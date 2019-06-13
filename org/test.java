/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org;

/**
 *
 * @author Nishika
 */

import java.io.*;
import com.lowagie.text.DocumentException;
//import org.xhtmlrenderer.pdf.ITextRenderer; 
import org.docx4j.org.xhtmlrenderer.pdf.ITextRenderer;


public class test {
    
    public static void main(String[] args) 
            throws IOException, DocumentException {
        String inputFile = "http://stackoverflow.com/questions/12889449/how-to-generate-pdf-from-xhtml-page-dynamically-using-itextflying-saucer-with-j";
//        String url = new File(inputFile).toURI().toURL().toString();
//        String url = new File(inputFile).toURI().toURL().toString();
        //System.out.println(inputFile);
        String outputFile = "firstdoc.pdf";
        OutputStream os = new FileOutputStream(outputFile);
        
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(inputFile);
        renderer.layout();
        renderer.createPDF(os);
        
        os.close();
    }
}