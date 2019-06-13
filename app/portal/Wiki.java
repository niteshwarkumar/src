/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.portal;

import java.io.Serializable;

/**
 *
 * @author niteshwar
 */
public class Wiki implements Serializable {

    private Integer id;
    private Integer projectId;
    private String notes;
    private String reference;
    private String ques;
    private String ans;
    private String type;
    private boolean ftp;

    public Wiki() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isFtp() {
        return ftp;
    }

    public void setFtp(boolean ftp) {
        this.ftp = ftp;
    } 
    
}
    
