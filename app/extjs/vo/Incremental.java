/*
 * Incremental.java
 *
 * Created on February 20, 2009, 1:36 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package app.extjs.vo;

import java.util.Date;

/**
 *
 * @author pp41387
 */
public class Incremental {
    
    private Integer incremental_id;
    private Integer id_project;
    private Date incDate;
    private String incDescription;
    
    /** Creates a new instance of Incremental */
    public Incremental() {
    }

    public Integer getIncremental_id() {
        return incremental_id;
    }

    public void setIncremental_id(Integer incremental_id) {
        this.incremental_id = incremental_id;
    }

    public Integer getId_project() {
        return id_project;
    }

    public void setId_project(Integer id_project) {
        this.id_project = id_project;
    }

    public Date getIncDate() {
        return incDate;
    }

    public void setIncDate(Date incDate) {
        this.incDate = incDate;
    }

    public String getIncDescription() {
        return incDescription;
    }

    public void setIncDescription(String incDescription) {
        this.incDescription = incDescription;
    }
    
}
