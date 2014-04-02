/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.resource;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Neil
 *
 */
public class AssesEval implements Serializable {

    private Integer eval_id;
    private String test;
    private String enteredBy;
    private Date assessmentDate;
    private Integer score;
    private String approved;
    private String rejected;
    private Integer resourceId;

    public AssesEval(){}

    public AssesEval(Integer eval_id, String test, String enteredBy, Date assessmentDate, Integer score, String approved, String rejected) {
        this.eval_id = eval_id;
        this.test = test;
        this.enteredBy = enteredBy;
        this.assessmentDate = assessmentDate;
        this.score = score;
        this.approved = approved;
        this.rejected = rejected;
    }

    

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public Integer getEval_id() {
        return eval_id;
    }

    public void setEval_id(Integer eval_id) {
        this.eval_id = eval_id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

   
    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public Date getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(Date assessmentDate) {
        this.assessmentDate = assessmentDate;
    }


    public String getRejected() {
        return rejected;
    }

    public void setRejected(String rejected) {
        this.rejected = rejected;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

  
}
