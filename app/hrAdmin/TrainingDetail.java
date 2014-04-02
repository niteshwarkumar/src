/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.hrAdmin;

import java.util.Date;

/**
 *
 * @author Neil
 */
public class TrainingDetail {

    private String description;
    private String company;
    private String trainer;
    private String location;
    private Date fromDate;
    private Date toDate;
    private String result;
    private String evidence;
    private String trainingId;

    public String getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(String trainingId) {
        this.trainingId = trainingId;
    }



    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
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

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }
}
