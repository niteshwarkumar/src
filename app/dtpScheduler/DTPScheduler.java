package app.dtpScheduler;

import app.project.Project;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class DTPScheduler implements Serializable {

  private Project project;
  private int ID_Project;
  private int ID_Schedule;
  private String startDate;
  private String endDate;
  private String operatorStartDate;
  private String operatorEndDate;
  private String updatedId;
  private java.sql.Timestamp updatedTS;
  private int priority;
  private String status;
  private String operatorName;
  private String application;
  private String volume;
  private String units;
  private String stage;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getID_Schedule() {
        return ID_Schedule;
    }

    public void setID_Schedule(int ID_Schedule) {
        this.ID_Schedule = ID_Schedule;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getOperatorStartDate() {
        return operatorStartDate;
    }

    public void setOperatorStartDate(String operatorStartDate) {
        this.operatorStartDate = operatorStartDate;
    }

    public String getOperatorEndDate() {
        return operatorEndDate;
    }

    public void setOperatorEndDate(String operatorEndDate) {
        this.operatorEndDate = operatorEndDate;
    }

    public String getUpdatedId() {
        return updatedId;
    }

    public void setUpdatedId(String updatedId) {
        this.updatedId = updatedId;
    }

    public java.sql.Timestamp getUpdatedTS() {
        return updatedTS;
    }

    public void setUpdatedTS(java.sql.Timestamp updatedTS) {
        this.updatedTS = updatedTS;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getID_Project() {
        return ID_Project;
    }

    public void setID_Project(int ID_Project) {
        this.ID_Project = ID_Project;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

}
