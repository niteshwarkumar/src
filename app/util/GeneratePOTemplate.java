/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.util;

/**
 *
 * @author niteshwar
 */
import java.io.FileOutputStream;
import java.io.IOException;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;

public class GeneratePOTemplate {
//    public static String ext ="9Arp18";

    public static String ext = "28Nov18";

    public static void main(String[] args) {
        // String ext ="01May18";
        try {

            PdfCopyFields copy = new PdfCopyFields(new FileOutputStream("/Users/abhisheksingh/Project/PO/New/PO_Translator" + ext + ".pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg01_requirements.pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg03_8-tier.pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg02_translator.pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg04_translator.pdf"));
//            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg01_requirements.pdf"));
            copy.close();
            /**
             * ************************************************************************************************************************************
             */
            copy = new PdfCopyFields(new FileOutputStream("/Users/abhisheksingh/Project/PO/New/PO_Editor" + ext + ".pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg01_requirements.pdf"));
//            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg01_8-tier.pdf"));
//            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg02_editor.pdf"));
//            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg03_editor.pdf"));
//            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg01_requirements.pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg03_8-tier.pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg02_editor.pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg04_editor.pdf"));
            copy.close();
            /**
             * ************************************************************************************************************************************
             */
            copy = new PdfCopyFields(new FileOutputStream("/Users/abhisheksingh/Project/PO/New/PO_Proofreader" + ext + ".pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg01_requirements.pdf"));
//            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg01_8-tier.pdf"));
//            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg02_proofreader.pdf"));
//            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg03_proofreader.pdf"));
//            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg01_requirements.pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg03_8-tier.pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg02_proofreader.pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg04_proofreader.pdf"));
            copy.close();

            /**
             * ************************************************************************************************************************************
             */
            copy = new PdfCopyFields(new FileOutputStream("/Users/abhisheksingh/Project/PO/New/PO_TE" + ext + ".pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg01_requirements.pdf"));
//            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg01_8-tier.pdf"));
//            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg02_editor.pdf"));
//            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg03_editor.pdf"));
//            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg01_requirements.pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg03_8-tier.pdf"));

            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg02_editor.pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg04_editor.pdf"));
            copy.close();
            /**
             * ************************************************************************************************************************************
             */
            copy = new PdfCopyFields(new FileOutputStream("/Users/abhisheksingh/Project/PO/New/PO_TP" + ext + ".pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg01_requirements.pdf"));
//            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg01_8-tier.pdf"));
//            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg02_translator.pdf"));
//            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg02_proofreader.pdf"));
//            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg03_translator.pdf"));
//            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg01_requirements.pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg03_8-tier.pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg02_translator.pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg02_proofreader.pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg04_translator.pdf"));
            copy.close();
            /**
             * ************************************************************************************************************************************
             */
            copy = new PdfCopyFields(new FileOutputStream("/Users/abhisheksingh/Project/PO/New/PO_EP" + ext + ".pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg01_requirements.pdf"));
//            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg01_8-tier.pdf"));
//            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg02_editor.pdf"));
//            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg02_proofreader.pdf"));
//            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg03_proofreader.pdf"));
//            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg01_requirements.pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg03_8-tier.pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg02_editor.pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg02_proofreader.pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/pg04_proofreader.pdf"));
            copy.close();
            /**
             * ************************************************************************************************************************************
             */
            copy = new PdfCopyFields(new FileOutputStream("/Users/abhisheksingh/Project/PO/New/DTP02_001" + ext + ".pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/DTP_pg01.pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/DTP_pg02.pdf"));
            copy.addDocument(new PdfReader("/Users/abhisheksingh/Project/PO/DTP_pg03.pdf"));
            copy.close();

        } catch (DocumentException | IOException ex) {
        }
    }

}
