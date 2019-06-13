/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.quote;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.rtf.IRTFDocumentTransformer;
import net.sourceforge.rtf.RTFTemplate;
import net.sourceforge.rtf.handler.RTFDocumentHandler;
import net.sourceforge.rtf.template.velocity.RTFVelocityTransformerImpl;
import net.sourceforge.rtf.template.velocity.VelocityTemplateEngineImpl;
import org.apache.velocity.app.VelocityEngine;

/**
 *
 * @author niteshwar
 */
public class QuoteTemplateHelper {
    
    private static QuoteTemplateHelper instance = null;

    public QuoteTemplateHelper() {
    }

    //return the instance of EmailHelper
    public static synchronized QuoteTemplateHelper getInstance() {
        /*
     * Creates the Singleton instance, if needed.
     *
         */
        if (instance == null) {
            instance = new QuoteTemplateHelper();
        }
        return instance;
    }

//    public static String templatePath = "/Users/abhisheksingh/Project/templates/";
    public static String templatePath = "C:/templates/";
    
     public boolean deleteFile(String sFilePath) {
        java.io.File oFile = new java.io.File(sFilePath);
        if (oFile.isDirectory()) {
            java.io.File[] aFiles = oFile.listFiles();
            for (java.io.File oFileCur : aFiles) {
                deleteFile(oFileCur.getAbsolutePath());
            }
        }
        return oFile.delete();
    }

    RTFTemplate initializeRTFTemplate(java.io.File file) {
       RTFTemplate rtfTemplate = new RTFTemplate();
        // Parser
        RTFDocumentHandler parser = new RTFDocumentHandler();
        rtfTemplate.setParser(parser);
        // Transformer
        IRTFDocumentTransformer transformer = new RTFVelocityTransformerImpl();
        rtfTemplate.setTransformer(transformer);
        // Template engine 
        VelocityTemplateEngineImpl templateEngine = new VelocityTemplateEngineImpl();
        // Initialize velocity engine
        VelocityEngine velocityEngine = new VelocityEngine();
        templateEngine.setVelocityEngine(velocityEngine);
        rtfTemplate.setTemplateEngine(templateEngine);
        try {
            rtfTemplate.setTemplate(file);
        } catch (FileNotFoundException ex) {
           ex.printStackTrace();
        }
        return rtfTemplate;
    }

    void streamFile(String  rtfTarget, HttpServletResponse response) {
        try {
            String strHeader = "Attachment;Filename=" + rtfTarget;
            response.setHeader("Content-Disposition", strHeader);
            java.io.File fileToDownload = new java.io.File(rtfTarget);
            OutputStream out;
            try (FileInputStream fileInputStream = new FileInputStream(fileToDownload)) {
                int i;
                out = response.getOutputStream();
                while ((i = fileInputStream.read()) != -1) {
                    out.write(i);
                }
            }
            out.close();

        } catch (IOException e) // file IO errors
        {
            e.printStackTrace();
        }
        QuoteTemplateHelper.getInstance().deleteFile(rtfTarget);
    }

    
}
