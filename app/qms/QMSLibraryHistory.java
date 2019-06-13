/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.qms;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Niteshwar
 */
public class QMSLibraryHistory implements Serializable {


    private Integer id;
    private Integer QMSLibId;
    private String title;
    private String docId;
    private String version;
    private Date releaseDate;
    private String changes;
    private String fileName;
    private String fileSaveName;
    private String trained;

    public QMSLibraryHistory() {
    }



    public Integer getQMSLibId() {
        return QMSLibId;
    }

    public void setQMSLibId(Integer QMSLibId) {
        this.QMSLibId = QMSLibId;
    }

    public String getChanges() {
        return changes;
    }

    public void setChanges(String changes) {
        this.changes = changes;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSaveName() {
        return fileSaveName;
    }

    public void setFileSaveName(String fileSaveName) {
        this.fileSaveName = fileSaveName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTrained() {
        return trained;
    }

    public void setTrained(String trained) {
        this.trained = trained;
    }

    
}


