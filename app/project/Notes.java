/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.project;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author abhisheksingh
 */
public class Notes  implements Serializable {
    
    private Integer idnotes;
    private Integer userid;
    private Integer quoteId;
    private Integer projectId;
    
    private String author;
    private String notes;
    private String bgcolor;
    
    private Date createDate;
    private Date editDate;
    
    

    public Notes() {
    }

    public Notes(Integer userid, Integer quoteId, Integer projectId, String author, String notes, Date createDate, Date editDate) {
        this.userid = userid;
        this.quoteId = quoteId;
        this.projectId = projectId;
        this.author = author;
        this.notes = notes;
        this.createDate = createDate;
        this.editDate = editDate;
    }

    public Integer getIdnotes() {
        return idnotes;
    }

    public void setIdnotes(Integer idnotes) {
        this.idnotes = idnotes;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Integer quoteId) {
        this.quoteId = quoteId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }
    
    
    
}
