/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.newmodel;

import java.util.Date;

/**
 *
 * @author Neil
 */
public class OSAModel implements Comparable<OSAModel>{

    Date deliveryDate;
    String taskName;
    int score;
    String pm;
    String scoreDescription;
    String number,company_code,id_project;
    String taskType;
    String id;

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public String getId_project() {
        return id_project;
    }

    public void setId_project(String id_project) {
        this.id_project = id_project;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

  

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    
    public String getScoreDescription() {
        return scoreDescription;
    }

    public void setScoreDescription(String scoreDescription) {
        this.scoreDescription = scoreDescription;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int compareTo(OSAModel o) {
        return this.score - o.score ;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
