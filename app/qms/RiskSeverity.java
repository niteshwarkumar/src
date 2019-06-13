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
public class RiskSeverity {
    
    private Integer id;
    private String description;
    private String category;
    private String hazard;
    private Set Mitigation;

    public RiskSeverity() {
    }
    
    

    public RiskSeverity(Integer id, String description, String category, String hazard, Set Mitigation) {
        this.id = id;
        this.description = description;
        this.category = category;
        this.hazard = hazard;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHazard() {
        return hazard;
    }

    public void setHazard(String hazard) {
        this.hazard = hazard;
    }

    public Set getMitigation() {
        return Mitigation;
    }

    public void setMitigation(Set Mitigation) {
        this.Mitigation = Mitigation;
    }
    
    
    
    
}
