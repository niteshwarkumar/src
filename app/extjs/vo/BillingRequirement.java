/*
 * BillingRequirement.java
 *
 * Created on June 22, 2008, 3:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package app.extjs.vo;

public class BillingRequirement {
  private Integer billing_id;
  private Integer ID_Client;
  private String requirement;
  private String edited_by;
    /** Creates a new instance of BillingRequirement */
    public BillingRequirement() {
    }

    public Integer getID_Client() {
        return ID_Client;
    }

    public void setID_Client(Integer ID_Client) {
        this.ID_Client = ID_Client;
    }
    public Integer getBilling_id() {
        return billing_id;
    }

    public void setBilling_id(Integer billing_id) {
        this.billing_id = billing_id;
    }

    public String getRequirement() {
        return requirement.replaceAll("/\\\\\\\\/g", "\\\\");
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getEdited_by() {
        return edited_by;
    }

    public void setEdited_by(String edited_by) {
        this.edited_by = edited_by;
    }
    
}
