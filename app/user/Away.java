package app.user;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Away implements Serializable {

    /** identifier field */
    private Integer awayId;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private Date requestedDate;

    /** nullable persistent field */
    private Date approvedDate;

    /** nullable persistent field */
    private Date startDate;

    /** nullable persistent field */
    private Date endDate;

    /** nullable persistent field */
    private Double daysUsed;

    /** nullable persistent field */
    private String type;

    /** nullable persistent field */
    private app.user.User User;

    /** full constructor */
    public Away(String description, Date requestedDate, Date approvedDate, Date startDate, Date endDate, Double daysUsed, String type, app.user.User User) {
        this.description = description;
        this.requestedDate = requestedDate;
        this.approvedDate = approvedDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.daysUsed = daysUsed;
        this.type = type;
        this.User = User;
    }

    /** default constructor */
    public Away() {
    }

    public Integer getAwayId() {
        return this.awayId;
    }

    public void setAwayId(Integer awayId) {
        this.awayId = awayId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRequestedDate() {
        return this.requestedDate;
    }

    public void setRequestedDate(Date requestedDate) {
        this.requestedDate = requestedDate;
    }

    public Date getApprovedDate() {
        return this.approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
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

    public Double getDaysUsed() {
        return this.daysUsed;
    }

    public void setDaysUsed(Double daysUsed) {
        this.daysUsed = daysUsed;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public app.user.User getUser() {
        return this.User;
    }

    public void setUser(app.user.User User) {
        this.User = User;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("awayId", getAwayId())
            .toString();
    }

}
