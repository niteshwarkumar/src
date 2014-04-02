package app.tracker;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import java.sql.Timestamp;

/** @author Hibernate CodeGenerator */
public class MilestoneLanguage implements Serializable {

    private Integer millan_id;
  private Integer  milestone_id ;
  private Integer lang_id ;
  private Integer project_id ;
  private Timestamp completed_ts ;

    public Integer getMillan_id() {
        return millan_id;
    }

    public void setMillan_id(Integer millan_id) {
        this.millan_id = millan_id;
    }

    public Integer getMilestone_id() {
        return milestone_id;
    }

    public void setMilestone_id(Integer milestone_id) {
        this.milestone_id = milestone_id;
    }

    public Integer getLang_id() {
        return lang_id;
    }

    public void setLang_id(Integer lang_id) {
        this.lang_id = lang_id;
    }

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }

    public Timestamp getCompleted_ts() {
        return completed_ts;
    }

    public void setCompleted_ts(Timestamp completed_ts) {
        this.completed_ts = completed_ts;
    }

}
