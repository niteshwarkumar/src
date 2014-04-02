/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.user;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Niteshwar
 */
public class TrainingInitial implements Serializable {

      private Integer id;
      private String department;
      private String trainingitem;
      private Date traindate;
      private String trainer;
      private String prevexp;
      private String completed;
      private Integer ID_User;

    public TrainingInitial() {
    }

    public TrainingInitial(Integer id, String department, String trainingitem, Date traindate, String trainer, String prevexp, String completed, Integer ID_User) {
        this.id = id;
        this.department = department;
        this.trainingitem = trainingitem;
        this.traindate = traindate;
        this.trainer = trainer;
        this.prevexp = prevexp;
        this.completed = completed;
        this.ID_User = ID_User;
    }

    public Integer getID_User() {
        return ID_User;
    }

    public void setID_User(Integer ID_User) {
        this.ID_User = ID_User;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrevexp() {
        return prevexp;
    }

    public void setPrevexp(String prevexp) {
        this.prevexp = prevexp;
    }

    public Date getTraindate() {
        return traindate;
    }

    public void setTraindate(Date traindate) {
        this.traindate = traindate;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getTrainingitem() {
        return trainingitem;
    }

    public void setTrainingitem(String trainingitem) {
        this.trainingitem = trainingitem;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}
