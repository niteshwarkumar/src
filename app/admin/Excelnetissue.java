/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.admin;

import java.util.Date;

/**
 *
 * @author abc
 */
public class Excelnetissue {

    private Integer id;
    private Integer status;
    private Integer itemNo;
    private String priority;
    private String item;
    private String reference;
    private String tested;
    private String testedBy;
    private String result;
    private String uploaded;
    private String approval1;
    private String approval2;
     private String approval3;
    private String approval4;
    private String ffinal;
    private String issue;
    private String notes;
    private Date dateStart;
    private Date dateDue;
    private Date dateFinal;
    private Date date1;
    private Date date2;
    private Date date3;
    private Date date4;
    private String testTested;
    private String liveTested;
    private String liveIssue;
    
    public Excelnetissue() {
    }

    public Excelnetissue(Integer status, Integer itemNo, String priority, String item, String reference, String tested, String testedBy, String result, String uploaded, String approval1, String approval2, String ffinal, String issue, String notes, Date dateStart, Date dateDue, Date dateFinal, Date date1, Date date2, Date date3, Date date4,String approval3, String approval4, String testTested, String liveTested, String liveIssue) {
        this.status = status;
        this.itemNo = itemNo;
        this.priority = priority;
        this.item = item;
        this.reference = reference;
        this.tested = tested;
        this.testedBy = testedBy;
        this.result = result;
        this.uploaded = uploaded;
        this.approval1 = approval1;
        this.approval2 = approval2;
        this.ffinal = ffinal;
        this.issue = issue;
        this.notes = notes;
        this.dateStart = dateStart;
        this.dateDue = dateDue;
        this.dateFinal = dateFinal;
        this.date1 = date1;
        this.date2 = date2;
        this.date3 = date3;
        this.date4 = date4;
         this.approval3 = approval3;
        this.approval4 = approval4;
        this.testTested = testTested;
        this.liveTested = liveTested;
        this.liveTested = liveIssue;
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTested() {
        return tested;
    }

    public void setTested(String tested) {
        this.tested = tested;
    }

    public String getTestedBy() {
        return testedBy;
    }

    public void setTestedBy(String testedBy) {
        this.testedBy = testedBy;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUploaded() {
        return uploaded;
    }

    public void setUploaded(String uploaded) {
        this.uploaded = uploaded;
    }

    public String getApproval1() {
        return approval1;
    }

    public void setApproval1(String approval1) {
        this.approval1 = approval1;
    }

    public String getApproval2() {
        return approval2;
    }

    public void setApproval2(String approval2) {
        this.approval2 = approval2;
    }

    public String getFfinal() {
        return ffinal;
    }

    public void setFfinal(String ffinal) {
        this.ffinal = ffinal;
    }
    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public Date getDateFinal() {
        return dateFinal;
    }

    public void setDateFinal(Date dateFinal) {
        this.dateFinal = dateFinal;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public Date getDate3() {
        return date3;
    }

    public void setDate3(Date date3) {
        this.date3 = date3;
    }

    public Date getDate4() {
        return date4;
    }

    public void setDate4(Date date4) {
        this.date4 = date4;
    }

    public String getApproval3() {
        return approval3;
    }

    public void setApproval3(String approval3) {
        this.approval3 = approval3;
    }

    public String getApproval4() {
        return approval4;
    }

    public void setApproval4(String approval4) {
        this.approval4 = approval4;
    }

    public String getTestTested() {
        return testTested;
    }

    public void setTestTested(String testTested) {
        this.testTested = testTested;
    }

    public String getLiveTested() {
        return liveTested;
    }

    public void setLiveTested(String liveTested) {
        this.liveTested = liveTested;
    }

    public String getLiveIssue() {
        return liveIssue;
    }

    public void setLiveIssue(String liveIssue) {
        this.liveIssue = liveIssue;
    }
    
    
}
