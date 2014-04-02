package app.resource;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ProjectScore implements Serializable {

    /** identifier field */
    private Integer ProjectScoreId;

    /** nullable persistent field */
    private String projectNumber;

    /** nullable persistent field */
    private Integer score;

    /** full constructor */
    public ProjectScore(String projectNumber, Integer score) {
        this.projectNumber = projectNumber;
        this.score = score;
    }

    /** default constructor */
    public ProjectScore() {
    }

    public Integer getProjectScoreId() {
        return this.ProjectScoreId;
    }

    public void setProjectScoreId(Integer ProjectScoreId) {
        this.ProjectScoreId = ProjectScoreId;
    }

    public String getProjectNumber() {
        return this.projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public Integer getScore() {
        return this.score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("ProjectScoreId", getProjectScoreId())
            .toString();
    }

}
