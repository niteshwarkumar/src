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
public class TrainingInitialAdmin implements Serializable {

      private Integer id;
      private String empname;
      private Date hiredate;
      private String department;
      private String position;
      private String mentor;
      private Integer ID_User;
      private String supervisor;

    public TrainingInitialAdmin() {
    }

    public TrainingInitialAdmin(Integer id, String empname, Date hiredate, String department, String position, String mentor, Integer ID_User, String supervisor) {
        this.id = id;
        this.empname = empname;
        this.hiredate = hiredate;
        this.department = department;
        this.position = position;
        this.mentor = mentor;
        this.ID_User = ID_User;
        this.supervisor = supervisor;
    }

    public Integer getID_User() {
        return ID_User;
    }

    public void setID_User(Integer ID_User) {
        this.ID_User = ID_User;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMentor() {
        return mentor;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    

}
