/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.qms;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author abc
 */
public class QMSAction  implements Serializable {

    private Integer id;
    private String mrm;
    private String department;
    private String area;
    private String actionItem;
    private String responsible;
    private Date target;
    private Date actual;
    private String disposition;

    public QMSAction() {
    }

    public QMSAction(String mrm, String department, String area, String actionItem, String responsible, Date target, Date actual, String disposition) {
        this.mrm = mrm;
        this.department = department;
        this.area = area;
        this.actionItem = actionItem;
        this.responsible = responsible;
        this.target = target;
        this.actual = actual;
        this.disposition = disposition;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMrm() {
        return mrm;
    }

    public void setMrm(String mrm) {
        this.mrm = mrm;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getActionItem() {
        return actionItem;
    }

    public void setActionItem(String actionItem) {
        this.actionItem = actionItem;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public Date getTarget() {
        return target;
    }

    public void setTarget(Date target) {
        this.target = target;
    }

    public Date getActual() {
        return actual;
    }

    public void setActual(Date actual) {
        this.actual = actual;
    }

    public String getDisposition() {
        return disposition;
    }

    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }
    
    
}
