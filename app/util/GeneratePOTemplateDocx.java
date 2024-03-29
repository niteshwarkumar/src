/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.util;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.contenttype.ContentType;
import org.docx4j.openpackaging.io.SaveToZipFile;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.WordprocessingML.AlternativeFormatInputPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.CTAltChunk;

/**
 *
 * @author niteshwar
 */
public class GeneratePOTemplateDocx {

    private static long chunk = 0;
    private static final String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

    public void mergeDocx(InputStream s1, InputStream s2, OutputStream os) throws Exception {
        WordprocessingMLPackage target = WordprocessingMLPackage.load(s1);
        insertDocx(target.getMainDocumentPart(), IOUtils.toByteArray(s2));
        SaveToZipFile saver = new SaveToZipFile(target);
        saver.save(os);
    }

    private static void insertDocx(MainDocumentPart main, byte[] bytes) throws Exception {
            AlternativeFormatInputPart afiPart = new AlternativeFormatInputPart(new PartName("/part" + (chunk++) + ".docx"));
            afiPart.setContentType(new ContentType(CONTENT_TYPE));
            afiPart.setBinaryData(bytes);
            Relationship altChunkRel = main.addTargetPart(afiPart);

            CTAltChunk chunk = Context.getWmlObjectFactory().createCTAltChunk();
            chunk.setId(altChunkRel.getId());

            main.addObject(chunk);
    }

    public static void main(String[] args) {
       // InputStream s1 = new 
    }
}
