/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.admin;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * @author Niteshwar
 */
public class VersionControl implements Serializable{

    /** identifier field */
    private Integer vcId;

    /** nullable persistent field */
    private String Version;

    /** nullable persistent field */
    private String Built;

    /** nullable persistent field */
    private Date ReleaseDate;

    /** nullable persistent field */
    private String ReleaseNote;

    /** nullable persistent field */
    private String Author;

    /**full constructor*/
    public VersionControl(String Version,String Built,Date ReleaseDate,String ReleaseNote){
    //this.Author=Author;
    this.Built=Built;
    this.ReleaseDate=ReleaseDate;
    this.ReleaseNote=ReleaseNote;
    this.Version=Version;

    }

    /**default constructor*/
    public VersionControl(){
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String Author) {
        this.Author = Author;
    }

    public String getBuilt() {
        return Built;
    }

    public void setBuilt(String Built) {
        this.Built = Built;
    }

    public Date getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(Date ReleaseDate) {
        this.ReleaseDate = ReleaseDate;
    }

    public String getReleaseNote() {
        return ReleaseNote;
    }

    public void setReleaseNote(String ReleaseNote) {
        this.ReleaseNote = ReleaseNote;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String Version) {
        this.Version = Version;
    }

    public Integer getVcId() {
        return vcId;
    }

    public void setVcId(Integer vcId) {
        this.vcId = vcId;
    }




}
