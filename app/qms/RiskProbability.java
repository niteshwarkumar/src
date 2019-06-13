/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import java.util.Set;

/**
 *
 * @author niteshwar
 */
public class RiskProbability {
    
      private Integer id;
  private String description;
  private String level;
  private String probability;
  private Set Mitigation;

    public RiskProbability() {
    }
  
  

    public RiskProbability(Integer id, String description, String level, String probability, Set Mitigation) {
        this.id = id;
        this.description = description;
        this.level = level;
        this.probability = probability;
        this.Mitigation = Mitigation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getProbability() {
        return probability;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }

    public Set getMitigation() {
        return Mitigation;
    }

    public void setMitigation(Set Mitigation) {
        this.Mitigation = Mitigation;
    }
    
  
  
    
}
