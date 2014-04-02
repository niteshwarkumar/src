package app.project;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Change1 implements Serializable {

    /** identifier field */
    private Integer change1Id;

    /** nullable persistent field */
    private String number;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private Date changeDate;

    /** nullable persistent field */
    private String dollarTotal;

    /** nullable persistent field */
    private boolean approved;

    /** nullable persistent field */
    private app.project.Project Project;

    /** full constructor */
    public Change1(String number, String description, Date changeDate, String dollarTotal, boolean approved, app.project.Project Project) {
        this.number = number;
        this.description = description;
        this.changeDate = changeDate;
        this.dollarTotal = dollarTotal;
        this.approved = approved;
        this.Project = Project;
    }

    /** default constructor */
    public Change1() {
    }

    public Integer getChange1Id() {
        return this.change1Id;
    }

    public void setChange1Id(Integer change1Id) {
        this.change1Id = change1Id;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getChangeDate() {
        return this.changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public String getDollarTotal() {
        return this.dollarTotal;
    }

    public void setDollarTotal(String dollarTotal) {
        this.dollarTotal = dollarTotal;
    }

    public boolean isApproved() {
        return this.approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public app.project.Project getProject() {
        return this.Project;
    }

    public void setProject(app.project.Project Project) {
        this.Project = Project;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("change1Id", getChange1Id())
            .toString();
    }

}
