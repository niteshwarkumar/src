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
  
    private Integer QuoteID;

    private String pathname;

    private String uploadedBy;

    private Date uploadDate;

    public String getPathname() {
        return pathname;
    }

    public void setPathname(String pathname) {
        this.pathname = pathname;
    }

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

    public Integer getQuoteID() {
        return QuoteID;
    }

    public void setQuoteID(Integer QuoteID) {
        this.QuoteID = QuoteID;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    
   
}
