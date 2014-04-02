/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.menu;


import java.io.Serializable;
import java.util.Date;


/**
 *
 * @author Niteshwar
 */
public class Hardware implements Serializable{

    private Integer hardware_no;

    private String equipment;

    private String description;

    private String make;

    private String model;

    private String detail;

    private Date purchase_date;

    private Boolean wish;


    public  Hardware(){}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public Integer getHardware_no() {
        return hardware_no;
    }

    public void setHardware_no(Integer hardware_no) {
        this.hardware_no = hardware_no;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(Date purchase_date) {
        this.purchase_date = purchase_date;
    }

    public Boolean getWish() {
        return wish;
    }

    public void setWish(Boolean wish) {
        this.wish = wish;
    }






}
