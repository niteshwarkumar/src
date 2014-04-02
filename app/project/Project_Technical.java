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

    public Project_Technical() {
    }

    public Project_Technical(Integer id, String sourceos, String sourceapp, String sourcever, String targetos, String targetapp, String targetver, Integer projectid) {
        this.id = id;
        this.sourceos = sourceos;
        this.sourceapp = sourceapp;
        this.sourcever = sourcever;
        this.targetos = targetos;
        this.targetapp = targetapp;
        this.targetver = targetver;
        this.projectid = projectid;
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

    

}
