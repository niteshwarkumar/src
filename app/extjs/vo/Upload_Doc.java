/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.extjs.vo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Neil
 */
public class Upload_Doc implements Serializable {

  private Integer uploadDoc;
  private Integer ClientID;
  private Integer projectID;
  private Integer QuoteID;
  private Integer resourceId;
  private String pathname;
  private String uploadedBy;
  private Date uploadDate;
  private String type;
  private String owner;
  private String title;
  private String docFormat;
  private String description;
  private Integer othId;
  private String path;

    public Upload_Doc() {
    }

    public Integer getUploadDoc() {
        return uploadDoc;
    }

    public void setUploadDoc(Integer uploadDoc) {
        this.uploadDoc = uploadDoc;
    }

    public Integer getClientID() {
        return ClientID;
    }

    public void setClientID(Integer ClientID) {
        this.ClientID = ClientID;
    }

    public Integer getProjectID() {
        return projectID;
    }

    public void setProjectID(Integer projectID) {
        this.projectID = projectID;
    }

    public Integer getQuoteID() {
        return QuoteID;
    }

    public void setQuoteID(Integer QuoteID) {
        this.QuoteID = QuoteID;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getPathname() {
        return pathname;
    }

    public void setPathname(String pathname) {
        this.pathname = pathname;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocFormat() {
        return docFormat;
    }

    public void setDocFormat(String docFormat) {
        this.docFormat = docFormat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOthId() {
        return othId;
    }

    public void setOthId(Integer othId) {
        this.othId = othId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
  
    
}
