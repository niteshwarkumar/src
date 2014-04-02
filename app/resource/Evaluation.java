/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.resource;
import java.io.Serializable;

/**
 *
 * @author Neil
 */
public class Evaluation implements Serializable {

    private Integer eval_id;
    private String evaluation_area;
    private String enteredBy;
    private String projectId;
    private Integer rate;
    private String comment;
    private String resourceId;

    public Evaluation(){}

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEvaluation_area() {
        return evaluation_area;
    }

    public void setEvaluation_area(String evaluation_area) {
        this.evaluation_area = evaluation_area;
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

}
