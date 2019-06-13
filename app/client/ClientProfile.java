/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.client;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author niteshwar
 */
public class ClientProfile implements Serializable {
    
    private Integer clientId;
    private Integer id;
    private String ae_text;
    private String process_text;
    private String icr_text;
    private String deliverable_text;
    private String technical_project_text;
    private String pricing_text;
    private String current_invoicing_process_text;
    private String typical_project_text;
    private String updatedBy;
    private Date updateDate;

    public ClientProfile() {
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAe_text() {
        return ae_text;
    }

    public void setAe_text(String ae_text) {
        this.ae_text = ae_text;
    }

    public String getProcess_text() {
        return process_text;
    }

    public void setProcess_text(String process_text) {
        this.process_text = process_text;
    }

    public String getIcr_text() {
        return icr_text;
    }

    public void setIcr_text(String icr_text) {
        this.icr_text = icr_text;
    }

    public String getDeliverable_text() {
        return deliverable_text;
    }

    public void setDeliverable_text(String deliverable_text) {
        this.deliverable_text = deliverable_text;
    }

    public String getTechnical_project_text() {
        return technical_project_text;
    }

    public void setTechnical_project_text(String technical_project_text) {
        this.technical_project_text = technical_project_text;
    }

    public String getPricing_text() {
        return pricing_text;
    }

    public void setPricing_text(String pricing_text) {
        this.pricing_text = pricing_text;
    }

    public String getCurrent_invoicing_process_text() {
        return current_invoicing_process_text;
    }

    public void setCurrent_invoicing_process_text(String current_invoicing_process_text) {
        this.current_invoicing_process_text = current_invoicing_process_text;
    }

    public String getTypical_project_text() {
        return typical_project_text;
    }

    public void setTypical_project_text(String typical_project_text) {
        this.typical_project_text = typical_project_text;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    
    
    
    
}
