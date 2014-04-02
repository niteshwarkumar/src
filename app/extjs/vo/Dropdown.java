/*
 * Product.java
 *
 * Created on May 15, 2008, 11:15 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package app.extjs.vo;

import java.io.Serializable;

/**
 *
 * @author pp41387
 */
public class Dropdown  implements Serializable{
    
    /** Creates a new instance of Product */
    public Dropdown() {
    }
    
    private Integer ID_Dropdown;
    private String dropdownType;
    public String dropdownValue;
    private String priority;
    private String tab;

    public Integer getID_Dropdown() {
        return ID_Dropdown;
    }

    public void setID_Dropdown(Integer ID_Dropdown) {
        this.ID_Dropdown = ID_Dropdown;
    }

    public String getDropdownType() {
        return dropdownType;
    }

    public void setDropdownType(String dropdownType) {
        this.dropdownType = dropdownType;
    }

    public String getDropdownValue() {
        return dropdownValue;
    }

    public void setDropdownValue(String dropdownValue) {
        this.dropdownValue = dropdownValue;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }
    
     
    
    
}
