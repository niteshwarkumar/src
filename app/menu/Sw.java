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
public class Sw implements Serializable{

    private Integer software_no;

    private String application;

    private String description;

    private String platform;

    private String version;

    private String language;

    private Date purchase_date;

    private Boolean wish;

    public Sw(){}

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Date getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(Date purchase_date) {
        this.purchase_date = purchase_date;
    }

    public Integer getSoftware_no() {
        return software_no;
    }

    public void setSoftware_no(Integer software_no) {
        this.software_no = software_no;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean getWish() {
        return wish;
    }

    public void setWish(Boolean wish) {
        this.wish = wish;
    }




}
