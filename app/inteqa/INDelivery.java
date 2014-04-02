/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.inteqa;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Niteshwar
 */
public class INDelivery implements Serializable {

    /** nullable persistent field */
    private Integer id;
    /** nullable persistent field */
    private Integer projectId;
    private String notes;
    private String caveats;
    private boolean verified;
    private String verifiedBy;
    private Date verifiedDate;
    private String verifiedText;

    private boolean engverified;
    private String engverifiedBy;
    private Date engverifiedDate;
    private String engverifiedText;

    private boolean dtpverified;
    private String dtpverifiedBy;
    private Date dtpverifiedDate;
    private String dtpverifiedText;
    private boolean vendor;

    public INDelivery() {
    }

    public String getCaveats() {
        return caveats;
    }

    public void setCaveats(String caveats) {
        this.caveats = caveats;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public Date getVerifiedDate() {
        return verifiedDate;
    }

    public void setVerifiedDate(Date verifiedDate) {
        this.verifiedDate = verifiedDate;
    }

    public String getVerifiedText() {
        return verifiedText;
    }

    public void setVerifiedText(String verifiedText) {
        this.verifiedText = verifiedText;
    }

    public boolean isDtpverified() {
        return dtpverified;
    }

    public void setDtpverified(boolean dtpverified) {
        this.dtpverified = dtpverified;
    }

    public String getDtpverifiedBy() {
        return dtpverifiedBy;
    }

    public void setDtpverifiedBy(String dtpverifiedBy) {
        this.dtpverifiedBy = dtpverifiedBy;
    }

    public Date getDtpverifiedDate() {
        return dtpverifiedDate;
    }

    public void setDtpverifiedDate(Date dtpverifiedDate) {
        this.dtpverifiedDate = dtpverifiedDate;
    }

    public String getDtpverifiedText() {
        return dtpverifiedText;
    }

    public void setDtpverifiedText(String dtpverifiedText) {
        this.dtpverifiedText = dtpverifiedText;
    }

    public boolean isEngverified() {
        return engverified;
    }

    public void setEngverified(boolean engverified) {
        this.engverified = engverified;
    }

    public String getEngverifiedBy() {
        return engverifiedBy;
    }

    public void setEngverifiedBy(String engverifiedBy) {
        this.engverifiedBy = engverifiedBy;
    }

    public Date getEngverifiedDate() {
        return engverifiedDate;
    }

    public void setEngverifiedDate(Date engverifiedDate) {
        this.engverifiedDate = engverifiedDate;
    }

    public String getEngverifiedText() {
        return engverifiedText;
    }

    public void setEngverifiedText(String engverifiedText) {
        this.engverifiedText = engverifiedText;
    }

    public boolean isVendor() {
        return vendor;
    }

    public void setVendor(boolean vendor) {
        this.vendor = vendor;
    }



}
