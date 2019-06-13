/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

/**
 *
 * @author niteshwar
 */
public class Competence {
    private Integer id; 
    private Integer userId; 
    private Integer competence; 
    private Integer required; 
    private Integer actual;
    private Integer cyear;
    private Boolean isTrainingReq;
     private Integer role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCompetence() {
        return competence;
    }

    public void setCompetence(Integer competence) {
        this.competence = competence;
    }

    public Integer getRequired() {
        return required;
    }

    public void setRequired(Integer required) {
        this.required = required;
    }

    public Integer getActual() {
        return actual;
    }

    public void setActual(Integer actual) {
        this.actual = actual;
    }

    public Integer getCyear() {
        return cyear;
    }

    public void setCyear(Integer cyear) {
        this.cyear = cyear;
    }

    public Boolean getIsTrainingReq() {
        return isTrainingReq;
    }

    public void setIsTrainingReq(Boolean isTrainingReq) {
        this.isTrainingReq = isTrainingReq;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    

    
    
    
}
