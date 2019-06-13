/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.project;

/**
 *
 * @author Niteshwar
 */
public class Project_Technical {
    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String sourceos;

    /** nullable persistent field */
    private String sourceapp;

    /** nullable persistent field */
    private String sourcever;

    /** nullable persistent field */
    private String targetos;

    /** nullable persistent field */
    private String targetapp;

    /** nullable persistent field */
    private String targetver;

    /** nullable persistent field */
    private Integer projectid;
    
    /** nullable persistent field */
    private Double unitCount;

    public Project_Technical() {
    }

 
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

   
    public String getSourceapp() {
        return sourceapp;
    }

    public void setSourceapp(String sourceapp) {
        this.sourceapp = sourceapp;
    }

    public String getSourceos() {
        return sourceos;
    }

    public void setSourceos(String sourceos) {
        this.sourceos = sourceos;
    }

    public String getSourcever() {
        return sourcever;
    }

    public void setSourcever(String sourcever) {
        this.sourcever = sourcever;
    }

    public String getTargetapp() {
        return targetapp;
    }

    public void setTargetapp(String targetapp) {
        this.targetapp = targetapp;
    }

    public String getTargetos() {
        return targetos;
    }

    public void setTargetos(String targetos) {
        this.targetos = targetos;
    }

    public String getTargetver() {
        return targetver;
    }

    public void setTargetver(String targetver) {
        this.targetver = targetver;
    }

    public Double getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(Double unitCount) {
        this.unitCount = unitCount;
    }
    
    

    

}
