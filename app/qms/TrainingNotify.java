/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import java.io.Serializable;

/**
 *
 * @author Niteshwar
 */
public class TrainingNotify implements Serializable {

    private Integer id;
    private Integer departmentId;
    private Integer userId;
    private Integer trainingId;
    private Integer trainingNeeded;

    public TrainingNotify() {
    }

    public TrainingNotify(Integer id, Integer departmentId, Integer userId, Integer trainingId, Integer trainingNeeded) {
        this.id = id;
        this.departmentId = departmentId;
        this.userId = userId;
        this.trainingId = trainingId;
        this.trainingNeeded = trainingNeeded;
    }

   

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Integer trainingId) {
        this.trainingId = trainingId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTrainingNeeded() {
        return trainingNeeded;
    }

    public void setTrainingNeeded(Integer trainingNeeded) {
        this.trainingNeeded = trainingNeeded;
    }

    
}
