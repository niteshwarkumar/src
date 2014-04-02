package app.user;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Training implements Serializable {

    /** identifier field */
    private Integer trainingId;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private Date dateCompleted;

    /** nullable persistent field */
    private String type;

    /** nullable persistent field */
    private String company;

    /** nullable persistent field */
    private String trainer;


    /** nullable persistent field */
    private String location;


    /** nullable persistent field */
    private Date dateStart;


    /** nullable persistent field */
    private String result;


    /** nullable persistent field */
    private String evidence;

    /** identifier field */
    private Integer docId;

    /** identifier field */
    private Integer departmentId;

    /** nullable persistent field */
    private String effectiveness;

    /** nullable persistent field */
    private String verifiedBy;

    /** nullable persistent field */
    private String verificationResult;

    /** nullable persistent field */
    private String notes;


    /** nullable persistent field */
    private app.user.User User;

    /** full constructor */
    public Training(String description, Date dateCompleted, app.user.User User) {
        this.description = description;
        this.dateCompleted = dateCompleted;
        this.User = User;
    }

    /** default constructor */
    public Training() {
    }

    public Integer getTrainingId() {
        return this.trainingId;
    }

    public void setTrainingId(Integer trainingId) {
        this.trainingId = trainingId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCompleted() {
        return this.dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public String getEffectiveness() {
        return effectiveness;
    }

    public void setEffectiveness(String effectiveness) {
        this.effectiveness = effectiveness;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getVerificationResult() {
        return verificationResult;
    }

    public void setVerificationResult(String verificationResult) {
        this.verificationResult = verificationResult;
    }

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    

    
    public app.user.User getUser() {
        return this.User;
    }

    public void setUser(app.user.User User) {
        this.User = User;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("trainingId", getTrainingId())
            .toString();
    }

}
