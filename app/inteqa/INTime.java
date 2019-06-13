/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.inteqa;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author niteshwar
 */
public class INTime {
    
    Integer id; 
    Date irtdate1;
    Date irtdate2;
    Date cstdate1;
    Date cstdate2;
    Time irttime1;
    Time irttime2;
    Time csttime1;
    Time csttime2;
    Integer projectId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getIrtdate1() {
        return irtdate1;
    }

    public void setIrtdate1(Date irtdate1) {
        this.irtdate1 = irtdate1;
    }

    public Date getIrtdate2() {
        return irtdate2;
    }

    public void setIrtdate2(Date irtdate2) {
        this.irtdate2 = irtdate2;
    }

    public Date getCstdate1() {
        return cstdate1;
    }

    public void setCstdate1(Date cstdate1) {
        this.cstdate1 = cstdate1;
    }

    public Date getCstdate2() {
        return cstdate2;
    }

    public void setCstdate2(Date cstdate2) {
        this.cstdate2 = cstdate2;
    }

    public Time getIrttime1() {
        return irttime1;
    }

    public void setIrttime1(Time irttime1) {
        this.irttime1 = irttime1;
    }

    public Time getIrttime2() {
        return irttime2;
    }

    public void setIrttime2(Time irttime2) {
        this.irttime2 = irttime2;
    }

    public Time getCsttime1() {
        return csttime1;
    }

    public void setCsttime1(Time csttime1) {
        this.csttime1 = csttime1;
    }

    public Time getCsttime2() {
        return csttime2;
    }

    public void setCsttime2(Time csttime2) {
        this.csttime2 = csttime2;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    
    
}
