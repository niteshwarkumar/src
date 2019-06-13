/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.qms;

import app.standardCode.StandardCode;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Niteshwar
 */
public class Capa implements Serializable{

  private Integer capa_id;
  private String number;
  private Date cdate;
  private Date vofe;
  private String employee;
  private String location;
  private String description;
  private String reportedby;
  private String fromc;
  private String issueId;
  private String source;
  private String status;
  private String ncr;
  private String reportedbydesc;
  private Boolean isLocked;
  private String lockedby;
  private Date admin_lock_date;
   
  public Capa() {
    }

    public Capa(Integer capa_id, String number, Date cdate, Date vofe, String employee, String location, String description, String reportedby, String fromc, String issueId, String source, String status, String ncr, String reportedbydesc, Boolean isLocked, String lockedby, Date admin_lock_date) {
        this.capa_id = capa_id;
        this.number = number;
        this.cdate = cdate;
        this.vofe = vofe;
        this.employee = employee;
        this.location = location;
        this.description = description;
        this.reportedby = reportedby;
        this.fromc = fromc;
        this.issueId = issueId;
        this.source = source;
        this.status = status;
        this.ncr = ncr;
        this.reportedbydesc = reportedbydesc;
        this.isLocked = isLocked;
        this.lockedby = lockedby;
        this.admin_lock_date = admin_lock_date;
    }


    public Integer getCapa_id() {
        return capa_id;
    }

    public void setCapa_id(Integer capa_id) {
        this.capa_id = capa_id;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = StandardCode.getInstance().convertTextToUTF(description);
    }

    public String getFromc() {
        return fromc;
    }

    public void setFromc(String fromc) {
        this.fromc = fromc;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNcr() {
        return ncr;
    }

    public void setNcr(String ncr) {
        this.ncr = ncr;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getReportedby() {
        return reportedby;
    }

    public void setReportedby(String reportedby) {
        this.reportedby = reportedby;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public Date getVofe() {
        return vofe;
    }

    public void setVofe(Date vofe) {
        this.vofe = vofe;
    }

  public String getReportedbydesc() {
    return reportedbydesc;
  }

  public void setReportedbydesc(String reportedbydesc) {
    this.reportedbydesc = reportedbydesc;
  }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public String getLockedby() {
        return lockedby;
    }

    public void setLockedby(String lockedby) {
        this.lockedby = lockedby;
    }

    public Date getAdmin_lock_date() {
        return admin_lock_date;
    }

    public void setAdmin_lock_date(Date admin_lock_date) {
        this.admin_lock_date = admin_lock_date;
    }
    

}
