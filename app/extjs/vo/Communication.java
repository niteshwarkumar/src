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
public class Communication  implements Serializable{
      
    /** Creates a new instance of Product */
    public Communication() {
    }
    
    private Integer ID_Client;
    private Integer ID_Communication;
    private String communicationType;
    private String description;

    public Integer getID_Client() {
        return ID_Client;
    }

    public void setID_Client(Integer ID_Client) {
        this.ID_Client = ID_Client;
    }

    public Integer getID_Communication() {
        return ID_Communication;
    }

    public void setID_Communication(Integer ID_Communication) {
        this.ID_Communication = ID_Communication;
    }

    public String getCommunicationType() {
        return communicationType;
    }

    public void setCommunicationType(String communicationType) {
        this.communicationType = communicationType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
   
    
    
    
    
}
