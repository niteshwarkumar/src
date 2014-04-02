/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.inteqa;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Niteshwar
 */
public class INBasics implements Serializable {

    /** nullable persistent field */
    private Integer id;
    /** nullable persistent field */
    private Integer projectId;
    /** nullable persistent field */
    private String server;
    /** nullable persistent field */
    private String ftp;
    /** nullable persistent field */
    private String other;
    /** nullable persistent field */
    private boolean dtpReq1;
    /** nullable persistent field */
    private boolean dtpReq2;
    /** nullable persistent field */
    private boolean genGra1;
    /** nullable persistent field */
    private boolean genGra2;
    /** nullable persistent field */
    private boolean genGra3;
    /** nullable persistent field */
    private boolean genGra4;
    /** nullable persistent field */
    private boolean screen1;
    /** nullable persistent field */
    private boolean screen2;
    /** nullable persistent field */
    private boolean screen3;
    /** nullable persistent field */
    private boolean screen4;
    /** nullable persistent field */
    private boolean deliveryFormat1;
    /** nullable persistent field */
    private boolean deliveryFormatOth;
    /** nullable persistent field */
    private String deliveryFormatOthText;
    /** nullable persistent field */
    private boolean DeliveryFiles1;
    /** nullable persistent field */
    private boolean DeliveryFiles2;
    /** nullable persistent field */
    private boolean DeliveryFilesOth;
    /** nullable persistent field */
    private String DeliveryFilesOthText;
    /** nullable persistent field */
    private String OtherInstruction;
    /** nullable persistent field */
    private boolean verified;
    /** nullable persistent field */
    private String verifiedBy;
    /** nullable persistent field */
    private Date verifiedDate;
    /** nullable persistent field */
    private String verifiedText;
    /** nullable persistent field */
    private String textBox1;
    /** nullable persistent field */
    private String textBox2;
    /** nullable persistent field */
    private String textBox3;

    /** default constructor */
    public INBasics() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isDeliveryFiles1() {
        return DeliveryFiles1;
    }

    public void setDeliveryFiles1(boolean DeliveryFiles1) {
        this.DeliveryFiles1 = DeliveryFiles1;
    }

    public boolean isDeliveryFiles2() {
        return DeliveryFiles2;
    }

    public void setDeliveryFiles2(boolean DeliveryFiles2) {
        this.DeliveryFiles2 = DeliveryFiles2;
    }

    public boolean isDeliveryFilesOth() {
        return DeliveryFilesOth;
    }

    public void setDeliveryFilesOth(boolean DeliveryFilesOth) {
        this.DeliveryFilesOth = DeliveryFilesOth;
    }

    public String getDeliveryFilesOthText() {
        return DeliveryFilesOthText;
    }

    public void setDeliveryFilesOthText(String DeliveryFilesOthText) {
        this.DeliveryFilesOthText = DeliveryFilesOthText;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getOtherInstruction() {
        return OtherInstruction;
    }

    public void setOtherInstruction(String OtherInstruction) {
        this.OtherInstruction = OtherInstruction;
    }

    public boolean isDeliveryFormat1() {
        return deliveryFormat1;
    }

    public void setDeliveryFormat1(boolean deliveryFormat1) {
        this.deliveryFormat1 = deliveryFormat1;
    }

    public boolean isDeliveryFormatOth() {
        return deliveryFormatOth;
    }

    public void setDeliveryFormatOth(boolean deliveryFormatOth) {
        this.deliveryFormatOth = deliveryFormatOth;
    }

    public String getDeliveryFormatOthText() {
        return deliveryFormatOthText;
    }

    public void setDeliveryFormatOthText(String deliveryFormatOthText) {
        this.deliveryFormatOthText = deliveryFormatOthText;
    }

    public boolean isDtpReq1() {
        return dtpReq1;
    }

    public void setDtpReq1(boolean dtpReq1) {
        this.dtpReq1 = dtpReq1;
    }

    public boolean isDtpReq2() {
        return dtpReq2;
    }

    public void setDtpReq2(boolean dtpReq2) {
        this.dtpReq2 = dtpReq2;
    }

    public String getFtp() {
        return ftp;
    }

    public void setFtp(String ftp) {
        this.ftp = ftp;
    }

    public boolean isGenGra1() {
        return genGra1;
    }

    public void setGenGra1(boolean genGra1) {
        this.genGra1 = genGra1;
    }

    public boolean isGenGra2() {
        return genGra2;
    }

    public void setGenGra2(boolean genGra2) {
        this.genGra2 = genGra2;
    }

    public boolean isGenGra3() {
        return genGra3;
    }

    public void setGenGra3(boolean genGra3) {
        this.genGra3 = genGra3;
    }

    public boolean isGenGra4() {
        return genGra4;
    }

    public void setGenGra4(boolean genGra4) {
        this.genGra4 = genGra4;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public boolean isScreen1() {
        return screen1;
    }

    public void setScreen1(boolean screen1) {
        this.screen1 = screen1;
    }

    public boolean isScreen2() {
        return screen2;
    }

    public void setScreen2(boolean screen2) {
        this.screen2 = screen2;
    }

    public boolean isScreen3() {
        return screen3;
    }

    public void setScreen3(boolean screen3) {
        this.screen3 = screen3;
    }

    public boolean isScreen4() {
        return screen4;
    }

    public void setScreen4(boolean screen4) {
        this.screen4 = screen4;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public Date getVerifiedDate() {
        return verifiedDate;
    }

    public void setVerifiedDate(Date verifiedDate) {
        this.verifiedDate = verifiedDate;
    }

    public String getVerifiedText() {
        return verifiedText;
    }

    public void setVerifiedText(String verifiedText) {
        this.verifiedText = verifiedText;
    }

    public String getTextBox1() {
        return textBox1;
    }

    public void setTextBox1(String textBox1) {
        this.textBox1 = textBox1;
    }

    public String getTextBox2() {
        return textBox2;
    }

    public void setTextBox2(String textBox2) {
        this.textBox2 = textBox2;
    }

    public String getTextBox3() {
        return textBox3;
    }

    public void setTextBox3(String textBox3) {
        this.textBox3 = textBox3;
    }


}
