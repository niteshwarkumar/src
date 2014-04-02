/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.project;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Niteshwar
 */
public class Client_Icr implements Serializable {


    private Integer icrno;
    private Integer ID_TargetDoc;
    private Date icr_sent;
    private Date icr_recieve;
    private String comment;

    public Client_Icr() {
    }

    public Client_Icr(Integer ID_TargetDoc, Date icr_sent, Date icr_recieve, String comment) {
        this.ID_TargetDoc = ID_TargetDoc;
        this.icr_sent = icr_sent;
        this.icr_recieve = icr_recieve;
        this.comment = comment;
    }

    public Integer getID_TargetDoc() {
        return ID_TargetDoc;
    }

    public void setID_TargetDoc(Integer ID_TargetDoc) {
        this.ID_TargetDoc = ID_TargetDoc;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getIcr_recieve() {
        return icr_recieve;
    }

    public void setIcr_recieve(Date icr_recieve) {
        this.icr_recieve = icr_recieve;
    }

    public Date getIcr_sent() {
        return icr_sent;
    }

    public void setIcr_sent(Date icr_sent) {
        this.icr_sent = icr_sent;
    }

    public Integer getIcrno() {
        return icrno;
    }

    public void setIcrno(Integer icrno) {
        this.icrno = icrno;
    }
}
