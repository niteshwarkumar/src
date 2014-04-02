package app.user;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class UserAbscence implements Serializable {

    /** identifier field */
    private Integer ID_User;
    /** identifier field */
    private Integer ID_Location;

    /** persistent field */
    private int abscence_id;

    /** persistent field */
    private String reason;

    /** persistent field */
    private String notes;

    /** nullable persistent field */
    private Date abscence_date;
    
    /** persistent field */
    private Double timeOut;

   

    public int getAbscence_id() {
        return abscence_id;
    }

    public void setAbscence_id(int abscence_id) {
        this.abscence_id = abscence_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getAbscence_date() {
        return abscence_date;
    }

    public void setAbscence_date(Date abscence_date) {
        this.abscence_date = abscence_date;
    }

    public Integer getID_User() {
        return ID_User;
    }

    public void setID_User(Integer ID_User) {
        this.ID_User = ID_User;
    }

    public Double getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Double timeOut) {
        this.timeOut = timeOut;
    }

    public Integer getID_Location() {
        return ID_Location;
    }

    public void setID_Location(Integer ID_Location) {
        this.ID_Location = ID_Location;
    }

    

}
