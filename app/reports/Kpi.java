/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.reports;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author niteshwar
 */
public class Kpi  implements Serializable {

    private Integer id;
    private String type;
    private String goal;
    private String referancevalue;
    private String responsibility;
    private String source;
    private String action;
    private Date referanceDate;
    private Integer year;
    private String referancevalue2;
    private Date referanceDate2;

    public Kpi() {
    }

    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getReferancevalue() {
        return referancevalue;
    }

    public void setReferancevalue(String referancevalue) {
        this.referancevalue = referancevalue;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getReferanceDate() {
        return referanceDate;
    }

    public void setReferanceDate(Date referanceDate) {
        this.referanceDate = referanceDate;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getReferancevalue2() {
        return referancevalue2;
    }

    public void setReferancevalue2(String referancevalue2) {
        this.referancevalue2 = referancevalue2;
    }

    public Date getReferanceDate2() {
        return referanceDate2;
    }

    public void setReferanceDate2(Date referanceDate2) {
        this.referanceDate2 = referanceDate2;
    }
    
    
}

