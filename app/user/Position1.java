package app.user;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Position1 implements Serializable {

    /** identifier field */
    private Integer position1Id;
    /** persistent field */
    private String position;
    private String department;
    private String reportto;
    private String positiondesc;
    private String duties;
    private String supervisoryduties;
    private String jobqualification;
    private String experience;
    private String skills;
    private String note;
    /** persistent field */
    private Set Users;

    public Position1(String position, String department, String reportto, String positiondesc, String duties, String supervisoryduties, String jobqualification, String experience, String skills, String note, Set Users) {
        this.position = position;
        this.department = department;
        this.reportto = reportto;
        this.positiondesc = positiondesc;
        this.duties = duties;
        this.supervisoryduties = supervisoryduties;
        this.jobqualification = jobqualification;
        this.experience = experience;
        this.skills = skills;
        this.note = note;
        this.Users = Users;
    }

    /** default constructor */
    public Position1() {
    }

    public Integer getPosition1Id() {
        return this.position1Id;
    }

    public void setPosition1Id(Integer position1Id) {
        this.position1Id = position1Id;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDuties() {
        return duties;
    }

    public void setDuties(String duties) {
        this.duties = duties;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getJobqualification() {
        return jobqualification;
    }

    public void setJobqualification(String jobqualification) {
        this.jobqualification = jobqualification;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPositiondesc() {
        return positiondesc;
    }

    public void setPositiondesc(String positiondesc) {
        this.positiondesc = positiondesc;
    }

    public String getReportto() {
        return reportto;
    }

    public void setReportto(String reportto) {
        this.reportto = reportto;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getSupervisoryduties() {
        return supervisoryduties;
    }

    public void setSupervisoryduties(String supervisoryduties) {
        this.supervisoryduties = supervisoryduties;
    }

    public Set getUsers() {
        return this.Users;
    }

    public void setUsers(Set Users) {
        this.Users = Users;
    }

    public String toString() {
        return new ToStringBuilder(this).append("position1Id", getPosition1Id()).toString();
    }
}
