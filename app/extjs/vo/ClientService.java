/*
 * ClientService.java
 *
 * Created on June 18, 2008, 6:13 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package app.extjs.vo;

import java.util.Date;

/**
 *
 * @author Aleks
 */
public class ClientService {
    
    private Integer ID_Service;
    private Integer ID_Client;
    private String service;
    private String requirements;
    private String last_edited_id;
    private Date last_edited_ts;
    
        
    /** Creates a new instance of ClientService */
    public ClientService() {
    }

    public Integer getID_Service() {
        return ID_Service;
    }

    public void setID_Service(Integer ID_Service) {
        this.ID_Service = ID_Service;
    }

    public Integer getID_Client() {
        return ID_Client;
    }

    public void setID_Client(Integer ID_Client) {
        this.ID_Client = ID_Client;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getLast_edited_id() {
        return last_edited_id;
    }

    public void setLast_edited_id(String last_edited_id) {
        this.last_edited_id = last_edited_id;
    }

    public Date getLast_edited_ts() {
        return last_edited_ts;
    }

    public void setLast_edited_ts(Date last_edited_ts) {
        this.last_edited_ts = last_edited_ts;
    }
    
    
    
    
}
