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
public class INReport implements Serializable {

  private Integer id;
  private Integer projectId;
  private String dtpNotes;
  private String dtpRequirements;
  private String enggNotes;
  private String enggRequirements;
  private boolean lingReq1;
  private boolean lingReq2;
  private boolean lingReq3;
  private boolean lingReq4;
  private boolean lingReq5;
  private boolean lingReq6;
  private String lingReqText5;
  private String lingReqText6;
  private String otherInfo;
  private boolean verified;
  private String verifiedBy;
  private Date verifiedDate;
  private String verifiedText;
  private Double pre_dtp;
  private Double post_dtp;
  private Double pre_engg;
  private Double post_engg;
  private Date quoteSentDate;

  public INReport() {
  }

  public Date getQuoteSentDate() {
    return quoteSentDate;
  }

  public void setQuoteSentDate(Date quoteSentDate) {
    this.quoteSentDate = quoteSentDate;
  }

  public Integer getProjectId() {
    return projectId;
  }

  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }

  public String getDtpNotes() {
    return dtpNotes;
  }

  public void setDtpNotes(String dtpNotes) {
    this.dtpNotes = dtpNotes;
  }

  public String getDtpRequirements() {
    return dtpRequirements;
  }

  public void setDtpRequirements(String dtpRequirements) {
    this.dtpRequirements = dtpRequirements;
  }

  public String getEnggNotes() {
    return enggNotes.replaceAll("/\\\\\\\\/g", "\\\\");
   
  }

  public void setEnggNotes(String enggNotes) {
    this.enggNotes = enggNotes;
  }

  public String getEnggRequirements() {
    return enggRequirements;
  }

  public void setEnggRequirements(String enggRequirements) {
    this.enggRequirements = enggRequirements;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public boolean isLingReq1() {
    return lingReq1;
  }

  public void setLingReq1(boolean lingReq1) {
    this.lingReq1 = lingReq1;
  }

  public boolean isLingReq2() {
    return lingReq2;
  }

  public void setLingReq2(boolean lingReq2) {
    this.lingReq2 = lingReq2;
  }

  public boolean isLingReq3() {
    return lingReq3;
  }

  public void setLingReq3(boolean lingReq3) {
    this.lingReq3 = lingReq3;
  }

  public boolean isLingReq4() {
    return lingReq4;
  }

  public void setLingReq4(boolean lingReq4) {
    this.lingReq4 = lingReq4;
  }

  public boolean isLingReq5() {
    return lingReq5;
  }

  public void setLingReq5(boolean lingReq5) {
    this.lingReq5 = lingReq5;
  }

  public boolean isLingReq6() {
    return lingReq6;
  }

  public void setLingReq6(boolean lingReq6) {
    this.lingReq6 = lingReq6;
  }

  public String getLingReqText5() {
    return lingReqText5;
  }

  public void setLingReqText5(String lingReqText5) {
    this.lingReqText5 = lingReqText5;
  }

  public String getLingReqText6() {
    return lingReqText6;
  }

  public void setLingReqText6(String lingReqText6) {
    this.lingReqText6 = lingReqText6;
  }

  public String getOtherInfo() {
    return otherInfo;
  }

  public void setOtherInfo(String otherInfo) {
    this.otherInfo = otherInfo;
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

  public Double getPost_dtp() {
    return post_dtp;
  }

  public void setPost_dtp(Double post_dtp) {
    this.post_dtp = post_dtp;
  }

  public Double getPost_engg() {
    return post_engg;
  }

  public void setPost_engg(Double post_engg) {
    this.post_engg = post_engg;
  }

  public Double getPre_dtp() {
    return pre_dtp;
  }

  public void setPre_dtp(Double pre_dtp) {
    this.pre_dtp = pre_dtp;
  }

  public Double getPre_engg() {
    return pre_engg;
  }

  public void setPre_engg(Double pre_engg) {
    this.pre_engg = pre_engg;
  }
}
