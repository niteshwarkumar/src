/*
 * AdditionalDeliveryDate.java
 *
 * Created on February 25, 2007, 5:23 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package app.project;

/**
 *
 * @author Aleksandar
 */
public class AdditionalDeliveryDate {
    private int add_id;
    private int ID_Project;
    private java.util.Date delivery_date;
    private String description;
    /** Creates a new instance of AdditionalDeliveryDate */
    public AdditionalDeliveryDate() {
        
    }

    public int getAdd_id() {
        return add_id;
    }

    public void setAdd_id(int add_id) {
        this.add_id = add_id;
    }

    public int getID_Project() {
        return ID_Project;
    }

    public void setID_Project(int ID_Project) {
        this.ID_Project = ID_Project;
    }

    public java.util.Date getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(java.util.Date delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
