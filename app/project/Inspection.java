package app.project;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Inspection implements Serializable {

    /** identifier field */
    private Integer inspectionId;

    /** nullable persistent field */
    private boolean applicable;

    /** nullable persistent field */
    private String milestone;

    /** nullable persistent field */
    private Date inDate;

    /** nullable persistent field */
    private String inspector;

    /** nullable persistent field */
    private boolean approved;

    /** nullable persistent field */
    private boolean rejected;

    /** nullable persistent field */
    private String note;

    /** nullable persistent field */
    private boolean inDefault;

    /** nullable persistent field */
    private Integer orderNum;

    /** nullable persistent field */
    private app.project.Project Project;
    
    private String inspectionType;
    private String language;

    /** full constructor */
    public Inspection(boolean applicable, String milestone, Date inDate, String inspector, boolean approved, boolean rejected, String note, boolean inDefault, Integer orderNum, app.project.Project Project) {
        this.applicable = applicable;
        this.milestone = milestone;
        this.inDate = inDate;
        this.inspector = inspector;
        this.approved = approved;
        this.rejected = rejected;
        this.note = note;
        this.inDefault = inDefault;
        this.orderNum = orderNum;
        this.Project = Project;
    }

    /** default constructor */
    public Inspection() {
    }

    public Integer getInspectionId() {
        return this.inspectionId;
    }

    public void setInspectionId(Integer inspectionId) {
        this.inspectionId = inspectionId;
    }

    public boolean isApplicable() {
        return this.applicable;
    }

    public void setApplicable(boolean applicable) {
        this.applicable = applicable;
    }

    public String getMilestone() {
        return this.milestone;
    }

    public void setMilestone(String milestone) {
        this.milestone = milestone;
    }

    public Date getInDate() {
        return this.inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public String getInspector() {
        return this.inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public boolean isApproved() {
        return this.approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isRejected() {
        return this.rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isInDefault() {
        return this.inDefault;
    }

    public void setInDefault(boolean inDefault) {
        this.inDefault = inDefault;
    }

    public Integer getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public app.project.Project getProject() {
        return this.Project;
    }

    public void setProject(app.project.Project Project) {
        this.Project = Project;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("inspectionId", getInspectionId())
            .toString();
    }

    public String getInspectionType() {
        return inspectionType;
    }

    public void setInspectionType(String inspectionType) {
        this.inspectionType = inspectionType;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
