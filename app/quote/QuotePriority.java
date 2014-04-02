/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.quote;

import java.io.Serializable;

/**
 *
 * @author Niteshwar
 */
public class QuotePriority  implements Serializable {

    /** identifier field */
    private Integer id;

    /** identifier field */
    private Integer ID_Quote1;

    /** identifier field */
    private String number;

    /** identifier field */
    private Integer urgency;

    /** identifier field */
    private Integer priority;

    /** identifier field */
    private Integer status;

    public QuotePriority() {
    }

    public QuotePriority(Integer id, Integer ID_Quote1, String number, Integer urgency, Integer priority, Integer status) {
        this.id = id;
        this.ID_Quote1 = ID_Quote1;
        this.number = number;
        this.urgency = urgency;
        this.priority = priority;
        this.status = status;
    }



    public Integer getID_Quote1() {
        return ID_Quote1;
    }

    public void setID_Quote1(Integer ID_Quote1) {
        this.ID_Quote1 = ID_Quote1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUrgency() {
        return urgency;
    }

    public void setUrgency(Integer urgency) {
        this.urgency = urgency;
    }

    
}
