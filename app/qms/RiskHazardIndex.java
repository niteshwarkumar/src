/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

/**
 *
 * @author niteshwar
 */
public class RiskHazardIndex {
  private Integer id;
  private Integer severity;
  private Integer probability;
  private Integer riskindex;
  private String indexcolor;

    public RiskHazardIndex() {
    }
  
  

    public RiskHazardIndex(Integer severity, Integer probability, Integer riskindex, String indexcolor) {
        this.severity = severity;
        this.probability = probability;
        this.riskindex = riskindex;
        this.indexcolor = indexcolor;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

    public Integer getProbability() {
        return probability;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRiskindex() {
        return riskindex;
    }

    public void setRiskindex(Integer riskindex) {
        this.riskindex = riskindex;
    }

    public String getIndexcolor() {
        return indexcolor;
    }

    public void setIndexcolor(String indexcolor) {
        this.indexcolor = indexcolor;
    }
    
    
}
