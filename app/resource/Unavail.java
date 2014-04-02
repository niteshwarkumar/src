package app.resource;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Unavail implements Serializable {

    /** identifier field */
    private Integer unavailId;

    /** nullable persistent field */
    private String notes;

    /** nullable persistent field */
    private Date startDate;

    /** nullable persistent field */
    private Date endDate;

    /** nullable persistent field */
    private app.resource.Resource Resource;

    /** full constructor */
    public Unavail(String notes, Date startDate, Date endDate, app.resource.Resource Resource) {
        this.notes = notes;
        this.startDate = startDate;
        this.endDate = endDate;
        this.Resource = Resource;
    }

    /** default constructor */
    public Unavail() {
    }

    public Integer getUnavailId() {
        return this.unavailId;
    }

    public void setUnavailId(Integer unavailId) {
        this.unavailId = unavailId;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public app.resource.Resource getResource() {
        return this.Resource;
    }

    public void setResource(app.resource.Resource Resource) {
        this.Resource = Resource;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("unavailId", getUnavailId())
            .toString();
    }

}
