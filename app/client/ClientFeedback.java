/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.client;

import java.util.Date;

/**
 *
 * @author Niteshwar
 */
public class ClientFeedback {

    public ClientFeedback(){
    }

    private Integer feedbackId;
    private Date feedbackDate;
    private String clientContact;
    private String feedback;
    private Integer clientId;
    private String fromRole;
    private String department;
    private String language;
    private String details;
    private String rate;

    public String getClientContact() {
        return clientContact;
    }

    public void setClientContact(String clientContact) {
        this.clientContact = clientContact;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getFromRole() {
        return fromRole;
    }

    public void setFromRole(String fromRole) {
        this.fromRole = fromRole;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    
}
