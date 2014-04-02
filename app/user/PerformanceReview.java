package app.user;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class PerformanceReview implements Serializable {

    /** identifier field */
    private Integer performanceReviewId;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private Date dueDate;

    /** nullable persistent field */
    private Date actualDate;

    /** nullable persistent field */
    private Date signedDate;

    /** nullable persistent field */
    private Date filedDate;

    /** nullable persistent field */
    private app.user.User User;

    /** full constructor */
    public PerformanceReview(String description, Date dueDate, Date actualDate, Date signedDate, Date filedDate, app.user.User User) {
        this.description = description;
        this.dueDate = dueDate;
        this.actualDate = actualDate;
        this.signedDate = signedDate;
        this.filedDate = filedDate;
        this.User = User;
    }

    /** default constructor */
    public PerformanceReview() {
    }

    public Integer getPerformanceReviewId() {
        return this.performanceReviewId;
    }

    public void setPerformanceReviewId(Integer performanceReviewId) {
        this.performanceReviewId = performanceReviewId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getActualDate() {
        return this.actualDate;
    }

    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

    public Date getSignedDate() {
        return this.signedDate;
    }

    public void setSignedDate(Date signedDate) {
        this.signedDate = signedDate;
    }

    public Date getFiledDate() {
        return this.filedDate;
    }

    public void setFiledDate(Date filedDate) {
        this.filedDate = filedDate;
    }

    public app.user.User getUser() {
        return this.User;
    }

    public void setUser(app.user.User User) {
        this.User = User;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("performanceReviewId", getPerformanceReviewId())
            .toString();
    }

}
