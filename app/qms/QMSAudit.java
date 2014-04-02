/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Niteshwar
 */
public class QMSAudit implements Serializable {

    private Integer auditno;
    private String standard;
    private String version;
    private String type;
    private String auditby;
    private String company;
    private String leadauditor;
    private String office;
    private Date scheduled;
    private Date actual;
    private String nonconformities;
    private String result;
    private String report;

    public QMSAudit() {
    }

   
    public Integer getAuditno() {
        return auditno;
    }

    public void setAuditno(Integer auditno) {
        this.auditno = auditno;
    }

    public String getAuditby() {
        return auditby;
    }

    public void setAuditby(String auditby) {
        this.auditby = auditby;
    }

   

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLeadauditor() {
        return leadauditor;
    }

    public void setLeadauditor(String leadauditor) {
        this.leadauditor = leadauditor;
    }

    public String getNonconformities() {
        return nonconformities;
    }

    public void setNonconformities(String nonconformities) {
        this.nonconformities = nonconformities;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getActual() {
        return actual;
    }

    public void setActual(Date actual) {
        this.actual = actual;
    }

    public Date getScheduled() {
        return scheduled;
    }

    public void setScheduled(Date scheduled) {
        this.scheduled = scheduled;
    }


    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }



}
