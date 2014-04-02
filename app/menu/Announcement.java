package app.menu;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Announcement implements Serializable {

    /** identifier field */
    private Integer announcementId;

    /** nullable persistent field */
    private Date startDate;

    /** nullable persistent field */
    private Date endDate;

    /** nullable persistent field */
    private String header;

    /** nullable persistent field */
    private String body;

    /** nullable persistent field */
    private String picture;
    
    /** nullable persistent field */
    private Integer ID_Client;

    /** full constructor */
//    public Announcement(Date startDate, Date endDate, String header, String body, String picture) {
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.header = header;
//        this.body = body;
//        this.picture = picture;
//    }

    public Announcement(Date startDate, Date endDate, String header, String body, String picture, Integer ID_Client) {
         this.startDate = startDate;
        this.endDate = endDate;
        this.header = header;
        this.body = body;
        this.picture = picture;
        this.ID_Client = ID_Client;
    }
    
    

    /** default constructor */
    public Announcement() {
    }

    public Integer getAnnouncementId() {
        return this.announcementId;
    }

    public void setAnnouncementId(Integer announcementId) {
        this.announcementId = announcementId;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("announcementId", getAnnouncementId())
            .toString();
    }

    public Integer getID_Client() {
        return ID_Client;
    }

    public void setID_Client(Integer ID_Client) {
        this.ID_Client = ID_Client;
    }

    
  

}
