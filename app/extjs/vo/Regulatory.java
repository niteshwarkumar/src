/*
 * ClientService.java
 *
 * Created on June 18, 2008, 6:13 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package app.extjs.vo;

/**
 *
 * @author Aleks
 */
public class Regulatory {
    
    private Integer regulatory_id;
    private Integer ID_Client;
    private String regulatory_type;
    private String regulatory_requirement;
    private String edited_by ;
    
        
    /** Creates a new instance of ClientService */
    public Regulatory() {
    }

   public Integer getRegulatory_id() {
        return regulatory_id;
    }

    public void setRegulatory_id(Integer regulatory_id) {
        this.regulatory_id = regulatory_id;
    }

    public Integer getID_Client() {
        return ID_Client;
    }

    public void setID_Client(Integer ID_Client) {
        this.ID_Client = ID_Client;
    }

    public String getRegulatory_type() {
        return regulatory_type;
    }

    public void setRegulatory_type(String regulatory_type) {
        this.regulatory_type = regulatory_type;
    }

    public String getRegulatory_requirement() {
        return regulatory_requirement;
    }

    public void setRegulatory_requirement(String regulatory_requirement) {
        this.regulatory_requirement = regulatory_requirement;
    }

    public String getEdited_by() {
        return edited_by;
    }

    public void setEdited_by(String edited_by) {
        this.edited_by = edited_by;
    }

    
    
}
