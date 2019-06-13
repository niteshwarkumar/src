/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.client;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author abhisheksingh
 */
public class ClientNotes  implements Serializable {
    
    private Integer idnotes;
    private Integer userid;
    private Integer Id_Client;
    private String tab;
    
    private String author;
    private String notes;
    private String bgcolor;
    
    private Date createDate;
    private Date editDate;

    public ClientNotes() {
    }

    public ClientNotes(Integer userid, Integer Id_Client, String tab, String author, String notes, Date createDate, Date editDate) {
        this.userid = userid;
        this.Id_Client = Id_Client;
        this.tab = tab;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNotes() {
        return notes.replaceAll("/\\\\\\\\/g", "\\\\");
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

    public Integer getId_Client() {
        return Id_Client;
    }

    public void setId_Client(Integer Id_Client) {
        this.Id_Client = Id_Client;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }
    
    
    
}
