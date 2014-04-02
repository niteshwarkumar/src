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

public class Technical implements Serializable {

private Integer technicalId;
private Integer clientQuote_ID;
private String sourceOs;
private String targetOs;
private String sourceApplication;
private String targetApplication;
private String sourceVersion;
private String targetVersion;

public Technical(){}

    public Integer getClientQuote_ID() {
        return clientQuote_ID;
    }

    public void setClientQuote_ID(Integer clientQuote_ID) {
        this.clientQuote_ID = clientQuote_ID;
    }

    public String getSourceApplication() {
        return sourceApplication;
    }

    public void setSourceApplication(String sourceApplication) {
        this.sourceApplication = sourceApplication;
    }

    public String getSourceOs() {
        return sourceOs;
    }

    public void setSourceOs(String sourceOs) {
        this.sourceOs = sourceOs;
    }

    public String getSourceVersion() {
        return sourceVersion;
    }

    public void setSourceVersion(String sourceVersion) {
        this.sourceVersion = sourceVersion;
    }

    public String getTargetApplication() {
        return targetApplication;
    }

    public void setTargetApplication(String targetApplication) {
        this.targetApplication = targetApplication;
    }

    public String getTargetOs() {
        return targetOs;
    }

    public void setTargetOs(String targetOs) {
        this.targetOs = targetOs;
    }

    public String getTargetVersion() {
        return targetVersion;
    }

    public void setTargetVersion(String targetVersion) {
        this.targetVersion = targetVersion;
    }

    public Integer getTechnicalId() {
        return technicalId;
    }

    public void setTechnicalId(Integer technicalId) {
        this.technicalId = technicalId;
    }




}
