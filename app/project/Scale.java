/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.project;

import app.standardCode.StandardCode;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author niteshwar
 */
public class Scale  implements Serializable {
    private static final long serialVersionUID = -5255306346414801456L;
    
     /** nullable persistent field */
    private String scaleRep;

    /** nullable persistent field */
    private String scale100;

    /** nullable persistent field */
    private String scale95;

    /** nullable persistent field */
    private String scale85;

    /** nullable persistent field */
    private String scale75;

    /** nullable persistent field */
    private String scaleNew;
    private String scalePerfect;
    private String scaleContext;
    private int id;
     /** persistent field */
    private Set LinTasks;

    public String getScaleRep() {
        return scaleRep;
    }

    public void setScaleRep(String scaleRep) {
        this.scaleRep = scaleRep;
    }

    public String getScale100() {
        return scale100;
    }

    public void setScale100(String scale100) {
        
        if(StandardCode.getInstance().noNull(scale100).equalsIgnoreCase("")){
        this.scale100 = scale100;
            System.err.println("Niteshwar| Scale: "+scale100+" Date: "+ new Date());
        }else if(Double.parseDouble(scale100)>1.0){
        this.scale100="0.2";
            System.err.println("Niteshwar| Scale: "+scale100+" Date: "+ new Date());
        }else{
        this.scale100 = scale100;
        }
    }

    public String getScale95() {
        return scale95;
    }

    public void setScale95(String scale95) {
        this.scale95 = scale95;
    }

    public String getScale85() {
        return scale85;
    }

    public void setScale85(String scale85) {
        this.scale85 = scale85;
    }

    public String getScale75() {
        return scale75;
    }

    public void setScale75(String scale75) {
        this.scale75 = scale75;
    }

    public String getScaleNew() {
        return scaleNew;
    }

    public void setScaleNew(String scaleNew) {
        this.scaleNew = scaleNew;
    }

    public String getScalePerfect() {
        return scalePerfect;
    }

    public void setScalePerfect(String scalePerfect) {
        this.scalePerfect = scalePerfect;
    }

    public String getScaleContext() {
        return scaleContext;
    }

    public void setScaleContext(String scaleContext) {
        this.scaleContext = scaleContext;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set getLinTasks() {
        return LinTasks;
    }

    public void setLinTasks(Set LinTasks) {
        this.LinTasks = LinTasks;
    }
    
    
    
    
}
