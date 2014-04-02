/*
 * LinStylesheets.java
 *
 * Created on June 25, 2008, 8:56 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package app.extjs.vo;

/**
 *
 * @author pp41387
 */
public class LinStylesheets {
    private Integer style_id;
    private Integer ID_Client;
    private String srcLanguage;
    private String targetLanguage;
    private String stylesheet;
    private String updatedBy;
    
    
    /** Creates a new instance of LinStylesheets */
    public LinStylesheets() {
    }

    public Integer getStyle_id() {
        return style_id;
    }

    public void setStyle_id(Integer style_id) {
        this.style_id = style_id;
    }

    public Integer getID_Client() {
        return ID_Client;
    }

    public void setID_Client(Integer ID_Client) {
        this.ID_Client = ID_Client;
    }

    public String getSrcLanguage() {
        return srcLanguage;
    }

    public void setSrcLanguage(String srcLanguage) {
        this.srcLanguage = srcLanguage;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public String getStylesheet() {
        return stylesheet;
    }

    public void setStylesheet(String stylesheet) {
        this.stylesheet = stylesheet;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    
}
