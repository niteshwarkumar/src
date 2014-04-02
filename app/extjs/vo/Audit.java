/*
 * Audit.java
 *
 * Created on June 19, 2008, 6:43 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package app.extjs.vo;

/**
 *
 * @author Sasa
 */
public class Audit {
    
 private Integer  audit_id;
  private String    audit_date   ;   
  private String auditor;
 private int      minor_non ;      
  private int  major_non;
  private Integer     ID_Client ;      
  private String  result ; 
   private String         report_url;

    /** Creates a new instance of Audit */
    public Audit() {
    }

    public Integer getAudit_id() {
        return audit_id;
    }

    public void setAudit_id(Integer audit_id) {
        this.audit_id = audit_id;
    }

    public String getAudit_date() {
        return audit_date;
    }

    public void setAudit_date(String audit_date) {
        this.audit_date = audit_date;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public int getMinor_non() {
        return minor_non;
    }

    public void setMinor_non(int minor_non) {
        this.minor_non = minor_non;
    }

    public int getMajor_non() {
        return major_non;
    }

    public void setMajor_non(int major_non) {
        this.major_non = major_non;
    }

    public Integer getID_Client() {
        return ID_Client;
    }

    public void setID_Client(Integer ID_Client) {
        this.ID_Client = ID_Client;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReport_url() {
        return report_url;
    }

    public void setReport_url(String report_url) {
        this.report_url = report_url;
    }
    
}
