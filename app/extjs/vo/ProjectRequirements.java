/*
 * ProjectRequirements.java
 *
 * Created on March 4, 2009, 2:30 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package app.extjs.vo;

/**
 *
 * @author PP41387
 */
public class ProjectRequirements {
    
    /** Creates a new instance of ProjectRequirements */
    public ProjectRequirements() {
    }
    private int req_id;
    private int ID_Project;
    private String requirement;
    private String satisfied;
    private String notes;
    private String type;

    public int getReq_id() {
        return req_id;
    }

    public void setReq_id(int req_id) {
        this.req_id = req_id;
    }

    public int getID_Project() {
        return ID_Project;
    }

    public void setID_Project(int ID_Project) {
        this.ID_Project = ID_Project;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getSatisfied() {
        return satisfied;
    }

    public void setSatisfied(String satisfied) {
        this.satisfied = satisfied;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
   
    
}
