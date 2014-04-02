package app.project;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Quality implements Serializable {

    /** identifier field */
    private Integer qualityId;

    /** nullable persistent field */
    private String issue;

    /** nullable persistent field */
    private Date dateRaised;

    /** nullable persistent field */
    private String number;

    /** nullable persistent field */
    private Date dateClosed;

    /** nullable persistent field */
    private String minorMajor;

    /** nullable persistent field */
    private app.project.Project Project;

    /** full constructor */
    public Quality(String issue, Date dateRaised, String number, Date dateClosed, String minorMajor, app.project.Project Project) {
        this.issue = issue;
        this.dateRaised = dateRaised;
        this.number = number;
        this.dateClosed = dateClosed;
        this.minorMajor = minorMajor;
        this.Project = Project;
    }

    /** default constructor */
    public Quality() {
    }

    public Integer getQualityId() {
        return this.qualityId;
    }

    public void setQualityId(Integer qualityId) {
        this.qualityId = qualityId;
    }

    public String getIssue() {
        return this.issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public Date getDateRaised() {
        return this.dateRaised;
    }

    public void setDateRaised(Date dateRaised) {
        this.dateRaised = dateRaised;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDateClosed() {
        return this.dateClosed;
    }

    public void setDateClosed(Date dateClosed) {
        this.dateClosed = dateClosed;
    }

    public String getMinorMajor() {
        return this.minorMajor;
    }

    public void setMinorMajor(String minorMajor) {
        this.minorMajor = minorMajor;
    }

    public app.project.Project getProject() {
        return this.Project;
    }

    public void setProject(app.project.Project Project) {
        this.Project = Project;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("qualityId", getQualityId())
            .toString();
    }

}
