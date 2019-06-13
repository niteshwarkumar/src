/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import java.util.Date;

/**
 *
 * @author niteshwar
 */
public class RiskMitigation {
    
  private Integer id;
  private String hazard;
  private String description;
  private RiskProbability Probability;
  private RiskSeverity Severity;
  private RiskProbability Probability1;
  private RiskSeverity Severity1;
  private Integer hazardnumber;
  private Integer hazardnumber1;
  private String existingmitigation;
  private String nonexistingmitigation;
  private String comment;
  private String notePre;
  private Date date;
  private String approve1;
  private String approve2;
  private String indexcolor;
  private String indexcolor1;

    public RiskMitigation() {
    }

  
    public RiskMitigation(Integer id, String hazard, String description, RiskProbability Probability, RiskSeverity Severity, Integer hazardnumber, String existingmitigation, String nonexistingmitigation, String comment, Date date, String approve1, String approve2, String indexcolor) {
        this.id = id;
        this.hazard = hazard;
        this.description = description;
        this.Probability = Probability;
        this.Severity = Severity;
        this.hazardnumber = hazardnumber;
        this.existingmitigation = existingmitigation;
        this.nonexistingmitigation = nonexistingmitigation;
        this.comment = comment;
        this.date = date;
        this.approve1 = approve1;
        this.approve2 = approve2;
        this.indexcolor = indexcolor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHazard() {
        return hazard;
    }

    public void setHazard(String hazard) {
        this.hazard = hazard;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RiskProbability getProbability() {
        return Probability;
    }

    public void setProbability(RiskProbability Probability) {
        this.Probability = Probability;
    }

    public RiskSeverity getSeverity() {
        return Severity;
    }

    public void setSeverity(RiskSeverity Severity) {
        this.Severity = Severity;
    }

    public Integer getHazardnumber() {
        return hazardnumber;
    }

    public void setHazardnumber(Integer hazardnumber) {
        this.hazardnumber = hazardnumber;
    }

    public String getExistingmitigation() {
        return existingmitigation;
    }

    public void setExistingmitigation(String existingmitigation) {
        this.existingmitigation = existingmitigation;
    }

    public String getNonexistingmitigation() {
        return nonexistingmitigation;
    }

    public void setNonexistingmitigation(String nonexistingmitigation) {
        this.nonexistingmitigation = nonexistingmitigation;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getApprove1() {
        return approve1;
    }

    public void setApprove1(String approve1) {
        this.approve1 = approve1;
    }

    public String getApprove2() {
        return approve2;
    }

    public void setApprove2(String approve2) {
        this.approve2 = approve2;
    }

    public String getIndexcolor() {
        return indexcolor;
    }

    public void setIndexcolor(String indexcolor) {
        this.indexcolor = indexcolor;
    }

    public RiskProbability getProbability1() {
        return Probability1;
    }

    public void setProbability1(RiskProbability Probability1) {
        this.Probability1 = Probability1;
    }

    public RiskSeverity getSeverity1() {
        return Severity1;
    }

    public void setSeverity1(RiskSeverity Severity1) {
        this.Severity1 = Severity1;
    }

    public String getNotePre() {
        return notePre;
    }

    public void setNotePre(String notePre) {
        this.notePre = notePre;
    }

    public Integer getHazardnumber1() {
        return hazardnumber1;
    }

    public void setHazardnumber1(Integer hazardnumber1) {
        this.hazardnumber1 = hazardnumber1;
    }

    public String getIndexcolor1() {
        return indexcolor1;
    }

    public void setIndexcolor1(String indexcolor1) {
        this.indexcolor1 = indexcolor1;
    }
   
}
