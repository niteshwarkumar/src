/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.resource;

import java.util.Date;

/**
 *
 * @author niteshwar
 */
public class ResourceCompDomain {
    private Integer ID_comp;
    private Integer ID_LanguagePair;
    private String domain;
    private String level;
    private String notes;
    private Integer experienceSince;

    public Integer getID_comp() {
        return ID_comp;
    }

    public void setID_comp(Integer ID_comp) {
        this.ID_comp = ID_comp;
    }

    public Integer getID_LanguagePair() {
        return ID_LanguagePair;
    }

    public void setID_LanguagePair(Integer ID_LanguagePair) {
        this.ID_LanguagePair = ID_LanguagePair;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getExperienceSince() {
        return experienceSince;
    }

    public void setExperienceSince(Integer experienceSince) {
        this.experienceSince = experienceSince;
    }
    
    
}
