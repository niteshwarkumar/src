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
public class INEngineering implements Serializable {

    private Integer id;
    private Integer projectId;
    private Float prepTime;
    private String prepLang;
    private Float prepTotal;
    private Float deliveryTime;
    private String deliveryLang;
    private Float deliveryTotal;
    private boolean filePrep01;
    private boolean filePrep02;
    private boolean filePrep1;
    private boolean filePrep2;
    private boolean filePrep3;
    private boolean filePrep4;
    private boolean filePrep5;
    private boolean filePrep6;
    private boolean extract1;
    private boolean extract2;
    private boolean check1;
    private boolean check2;
    private boolean check21;
    private boolean check22;
    private boolean check4;
    private boolean check5;
    private String extractText;
    private String check3Text;
    private String checkText1;
    private String checkText2;
    private boolean verified;
    private String verifiedBy;
    private Date verifiedDate;
    private String verifiedText;
    private String textBox1;
    private boolean lpr1;
    private boolean lpr2;
    private boolean lpr3;
    private boolean lpr4;
    private boolean lpr5;
    private boolean lpr6;
    private String lpr3Text;
    private String lpr5Text;
    private String lpr6Text;
    private String lprInfo;
    private String enggNotes;
    
        private boolean tmCheck1;
        private boolean tmCheck2;
        private boolean tmCheck3;
        private String tmPath1;
        private String tmPath2;
        private String tmPath3;
        private String tmName1;
        private String tmName2;
        private String tmName3;
        private String tmNotes;
    


    public INEngineering() {
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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

    public String getDeliveryLang() {
        return deliveryLang;
    }

    public void setDeliveryLang(String deliveryLang) {
        this.deliveryLang = deliveryLang;
    }

  

    public boolean isExtract1() {
        return extract1;
    }

    public void setExtract1(boolean extract1) {
        this.extract1 = extract1;
    }

    public boolean isExtract2() {
        return extract2;
    }

    public void setExtract2(boolean extract2) {
        this.extract2 = extract2;
    }

    public String getExtractText() {
        return extractText;
    }

    public void setExtractText(String extractText) {
        this.extractText = extractText;
    }

    public boolean isFilePrep1() {
        return filePrep1;
    }

    public void setFilePrep1(boolean filePrep1) {
        this.filePrep1 = filePrep1;
    }

    public boolean isFilePrep2() {
        return filePrep2;
    }

    public void setFilePrep2(boolean filePrep2) {
        this.filePrep2 = filePrep2;
    }

    public boolean isFilePrep3() {
        return filePrep3;
    }

    public void setFilePrep3(boolean filePrep3) {
        this.filePrep3 = filePrep3;
    }

    public boolean isFilePrep4() {
        return filePrep4;
    }

    public void setFilePrep4(boolean filePrep4) {
        this.filePrep4 = filePrep4;
    }

    public boolean isFilePrep5() {
        return filePrep5;
    }

    public void setFilePrep5(boolean filePrep5) {
        this.filePrep5 = filePrep5;
    }

    public boolean isFilePrep6() {
        return filePrep6;
    }

    public void setFilePrep6(boolean filePrep6) {
        this.filePrep6 = filePrep6;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrepLang() {
        return prepLang;
    }

    public void setPrepLang(String prepLang) {
        this.prepLang = prepLang;
    }



    public String getTextBox1() {
        return textBox1;
    }

    public void setTextBox1(String textBox1) {
        this.textBox1 = textBox1;
    }

    public Float getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Float deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Float getDeliveryTotal() {
        return deliveryTotal;
    }

    public void setDeliveryTotal(Float deliveryTotal) {
        this.deliveryTotal = deliveryTotal;
    }

    public Float getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(Float prepTime) {
        this.prepTime = prepTime;
    }

    public Float getPrepTotal() {
        return prepTotal;
    }

    public void setPrepTotal(Float prepTotal) {
        this.prepTotal = prepTotal;
    }

    public boolean isCheck1() {
        return check1;
    }

    public void setCheck1(boolean check1) {
        this.check1 = check1;
    }

    public boolean isCheck2() {
        return check2;
    }

    public void setCheck2(boolean check2) {
        this.check2 = check2;
    }

    public boolean isCheck21() {
        return check21;
    }

    public void setCheck21(boolean check21) {
        this.check21 = check21;
    }

    public boolean isCheck22() {
        return check22;
    }

    public void setCheck22(boolean check22) {
        this.check22 = check22;
    }

    public String getCheck3Text() {
        return check3Text;
    }

    public void setCheck3Text(String check3Text) {
        this.check3Text = check3Text;
    }

    public boolean isCheck4() {
        return check4;
    }

    public void setCheck4(boolean check4) {
        this.check4 = check4;
    }

    public boolean isCheck5() {
        return check5;
    }

    public void setCheck5(boolean check5) {
        this.check5 = check5;
    }

    public String getCheckText1() {
        return checkText1;
    }

    public void setCheckText1(String checkText1) {
        this.checkText1 = checkText1;
    }

    public String getCheckText2() {
        return checkText2;
    }

    public void setCheckText2(String checkText2) {
        this.checkText2 = checkText2;
    }

    public boolean isFilePrep01() {
        return filePrep01;
    }

    public void setFilePrep01(boolean filePrep01) {
        this.filePrep01 = filePrep01;
    }

    public boolean isFilePrep02() {
        return filePrep02;
    }

    public void setFilePrep02(boolean filePrep02) {
        this.filePrep02 = filePrep02;
    }

    public boolean isLpr1() {
        return lpr1;
    }

    public void setLpr1(boolean lpr1) {
        this.lpr1 = lpr1;
    }

    public boolean isLpr2() {
        return lpr2;
    }

    public void setLpr2(boolean lpr2) {
        this.lpr2 = lpr2;
    }

    public boolean isLpr3() {
        return lpr3;
    }

    public void setLpr3(boolean lpr3) {
        this.lpr3 = lpr3;
    }

    public boolean isLpr4() {
        return lpr4;
    }

    public void setLpr4(boolean lpr4) {
        this.lpr4 = lpr4;
    }

    public boolean isLpr5() {
        return lpr5;
    }

    public void setLpr5(boolean lpr5) {
        this.lpr5 = lpr5;
    }

    public String getLpr5Text() {
        return lpr5Text;
    }

    public void setLpr5Text(String lpr5Text) {
        this.lpr5Text = lpr5Text;
    }

    public boolean isLpr6() {
        return lpr6;
    }

    public void setLpr6(boolean lpr6) {
        this.lpr6 = lpr6;
    }

    public String getLpr6Text() {
        return lpr6Text;
    }

    public void setLpr6Text(String lpr6Text) {
        this.lpr6Text = lpr6Text;
    }

    public String getLprInfo() {
        return lprInfo;
    }

    public void setLprInfo(String lprInfo) {
        this.lprInfo = lprInfo;
    }

    public String getEnggNotes() {
        if(null!=enggNotes)
            return enggNotes.replaceAll("/\\\\\\\\/g", "\\\\");
        else
            return "";
        
    }

    public void setEnggNotes(String enggNotes) {
        this.enggNotes = enggNotes;
    }

    public boolean isTmCheck1() {
        return tmCheck1;
    }

    public void setTmCheck1(boolean tmCheck1) {
        this.tmCheck1 = tmCheck1;
    }

    public boolean isTmCheck2() {
        return tmCheck2;
    }

    public void setTmCheck2(boolean tmCheck2) {
        this.tmCheck2 = tmCheck2;
    }

    public boolean isTmCheck3() {
        return tmCheck3;
    }

    public void setTmCheck3(boolean tmCheck3) {
        this.tmCheck3 = tmCheck3;
    }

    public String getTmPath1() {
        return tmPath1;
    }

    public void setTmPath1(String tmPath1) {
        this.tmPath1 = tmPath1;
    }

    public String getTmPath2() {
        return tmPath2;
    }

    public void setTmPath2(String tmPath2) {
        this.tmPath2 = tmPath2;
    }

    public String getTmPath3() {
        return tmPath3;
    }

    public void setTmPath3(String tmPath3) {
        this.tmPath3 = tmPath3;
    }

    public String getTmName1() {
        return tmName1;
    }

    public void setTmName1(String tmName1) {
        this.tmName1 = tmName1;
    }

    public String getTmName2() {
        return tmName2;
    }

    public void setTmName2(String tmName2) {
        this.tmName2 = tmName2;
    }

    public String getTmName3() {
        return tmName3;
    }

    public void setTmName3(String tmName3) {
        this.tmName3 = tmName3;
    }

    public String getTmNotes() {
        return tmNotes;
    }

    public void setTmNotes(String tmNotes) {
        this.tmNotes = tmNotes;
    }

    public String getLpr3Text() {
        return lpr3Text;
    }

    public void setLpr3Text(String lpr3Text) {
        this.lpr3Text = lpr3Text;
    }

  
    
}
