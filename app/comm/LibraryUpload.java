/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.comm;

import java.util.Date;
import java.io.Serializable;

/**
 *
 * @author Niteshwar
 */
public class LibraryUpload implements Serializable {

      private Integer libId;
      private String maintab;
      private String heading;
      private String title;
      private String description;
      private Date uploadDate;
      private String uploadBy;
      private String format;
      private String fileName;
      private String fileSaveName;
      private Integer libMainId;
      private String htmlLink;
      private Integer clientId;
    public LibraryUpload() {
    }


    public LibraryUpload(Integer libId, String maintab, String heading, String title, String description, Date uploadDate, String uploadBy, String format, String fileName, String fileSaveName, Integer libMainId, String htmlLink, Integer clientId) {
        this.libId = libId;
        this.maintab = maintab;
        this.heading = heading;
        this.title = title;
        this.description = description;
        this.uploadDate = uploadDate;
        this.uploadBy = uploadBy;
        this.format = format;
        this.fileName = fileName;
        this.fileSaveName = fileSaveName;
        this.libMainId = libMainId;
        this.htmlLink = htmlLink;
        this.clientId = clientId;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public Integer getLibId() {
        return libId;
    }

    public void setLibId(Integer libId) {
        this.libId = libId;
    }

    public Integer getLibMainId() {
        return libMainId;
    }

    public void setLibMainId(Integer libMainId) {
        this.libMainId = libMainId;
    }

    public String getMaintab() {
        return maintab;
    }

    public void setMaintab(String maintab) {
        this.maintab = maintab;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUploadBy() {
        return uploadBy;
    }

    public void setUploadBy(String uploadBy) {
        this.uploadBy = uploadBy;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getHtmlLink() {
        return htmlLink;
    }

    public void setHtmlLink(String htmlLink) {
        this.htmlLink = htmlLink;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }


}
