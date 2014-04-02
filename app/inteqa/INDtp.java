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
public class INDtp implements Serializable {

    private Integer id;
    private Integer projectId;
    private Float prepTime;
    private String prepLang;
    private Float prepTotal;
    private Float deliveryTime;
    private String deliveryLang;
    private Float deliveryTotal;
    private boolean srcPf1;
    private boolean srcPf2;
    private boolean srcPf3;
    private boolean srcPf4;
    private boolean srcPf5;
    private boolean srcPf6;
    private String dtpToolName;
    private boolean dtpTool1;
    private boolean dtpToolOth;
    private String dtpToolOthText;
    private boolean srcFont1;
    private boolean srcFont2;
    private boolean tgtFont1;
    private boolean tgtFont2;
    private String TgtFontText;
    private boolean verified;
    private String verifiedBy;
    private Date verifiedDate;
    private String verifiedText;
    private String textBox1;
    private String textBox2;
    private String textBox3;
    private String textBox4;
    private boolean check1;
    private boolean check2;
    private boolean check3;
    private boolean check21;
    private boolean check22;
    private boolean check4;
    private boolean check5;
    private boolean check6;
    private boolean check7;
    private String check6text;
    private String check7text;
    private String checkText1;
    private String checkText2;
    private String checkText3;
    private Double hours0;
    private String unit0;
    private Double hours1;
    private String unit1;
    private Double hours2;
    private String unit2;
    private Double hours3;
    private String unit3;
    private Double hours4;
    private String unit4;
    private Double hours5;
    private String unit5;
    private Double hours6;
    private String unit6;
    private String dtpnotes;
    
    private String porq;

    public INDtp() {
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getTgtFontText() {
        return TgtFontText;
    }

    public void setTgtFontText(String TgtFontText) {
        this.TgtFontText = TgtFontText;
    }

    public String getDeliveryLang() {
        return deliveryLang;
    }

    public void setDeliveryLang(String deliveryLang) {
        this.deliveryLang = deliveryLang;
    }

    public boolean isDtpTool1() {
        return dtpTool1;
    }

    public void setDtpTool1(boolean dtpTool1) {
        this.dtpTool1 = dtpTool1;
    }

    public String getDtpToolName() {
        return dtpToolName;
    }

    public void setDtpToolName(String dtpToolName) {
        this.dtpToolName = dtpToolName;
    }

    public boolean isDtpToolOth() {
        return dtpToolOth;
    }

    public void setDtpToolOth(boolean dtpToolOth) {
        this.dtpToolOth = dtpToolOth;
    }

    public String getDtpToolOthText() {
        return dtpToolOthText;
    }

    public void setDtpToolOthText(String dtpToolOthText) {
        this.dtpToolOthText = dtpToolOthText;
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

    public boolean isSrcFont1() {
        return srcFont1;
    }

    public void setSrcFont1(boolean srcFont1) {
        this.srcFont1 = srcFont1;
    }

    public boolean isSrcFont2() {
        return srcFont2;
    }

    public void setSrcFont2(boolean srcFont2) {
        this.srcFont2 = srcFont2;
    }

    public boolean isSrcPf1() {
        return srcPf1;
    }

    public void setSrcPf1(boolean srcPf1) {
        this.srcPf1 = srcPf1;
    }

    public boolean isSrcPf2() {
        return srcPf2;
    }

    public void setSrcPf2(boolean srcPf2) {
        this.srcPf2 = srcPf2;
    }

    public boolean isSrcPf3() {
        return srcPf3;
    }

    public void setSrcPf3(boolean srcPf3) {
        this.srcPf3 = srcPf3;
    }

    public boolean isSrcPf4() {
        return srcPf4;
    }

    public void setSrcPf4(boolean srcPf4) {
        this.srcPf4 = srcPf4;
    }

    public boolean isSrcPf5() {
        return srcPf5;
    }

    public void setSrcPf5(boolean srcPf5) {
        this.srcPf5 = srcPf5;
    }

    public boolean isSrcPf6() {
        return srcPf6;
    }

    public void setSrcPf6(boolean srcPf6) {
        this.srcPf6 = srcPf6;
    }

    public boolean isTgtFont1() {
        return tgtFont1;
    }

    public void setTgtFont1(boolean tgtFont1) {
        this.tgtFont1 = tgtFont1;
    }

    public boolean isTgtFont2() {
        return tgtFont2;
    }

    public void setTgtFont2(boolean tgtFont2) {
        this.tgtFont2 = tgtFont2;
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

    public String getTextBox4() {
        return textBox4;
    }

    public void setTextBox4(String textBox4) {
        this.textBox4 = textBox4;
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

    public boolean isCheck3() {
        return check3;
    }

    public void setCheck3(boolean check3) {
        this.check3 = check3;
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

    public boolean isCheck6() {
        return check6;
    }

    public void setCheck6(boolean check6) {
        this.check6 = check6;
    }

    public boolean isCheck7() {
        return check7;
    }

    public void setCheck7(boolean check7) {
        this.check7 = check7;
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

    public String getCheckText3() {
        return checkText3;
    }

    public void setCheckText3(String checkText3) {
        this.checkText3 = checkText3;
    }

    public Double getHours0() {
        return hours0;
    }

    public void setHours0(Double hours0) {
        this.hours0 = hours0;
    }

    public Double getHours1() {
        return hours1;
    }

    public void setHours1(Double hours1) {
        this.hours1 = hours1;
    }

    public Double getHours2() {
        return hours2;
    }

    public void setHours2(Double hours2) {
        this.hours2 = hours2;
    }

    public Double getHours3() {
        return hours3;
    }

    public void setHours3(Double hours3) {
        this.hours3 = hours3;
    }

    public Double getHours4() {
        return hours4;
    }

    public void setHours4(Double hours4) {
        this.hours4 = hours4;
    }

    public Double getHours5() {
        return hours5;
    }

    public void setHours5(Double hours5) {
        this.hours5 = hours5;
    }

    public Double getHours6() {
        return hours6;
    }

    public void setHours6(Double hours6) {
        this.hours6 = hours6;
    }

    public String getUnit0() {
        return unit0;
    }

    public void setUnit0(String unit0) {
        this.unit0 = unit0;
    }

    public String getUnit1() {
        return unit1;
    }

    public void setUnit1(String unit1) {
        this.unit1 = unit1;
    }

    public String getUnit2() {
        return unit2;
    }

    public void setUnit2(String unit2) {
        this.unit2 = unit2;
    }

    public String getUnit3() {
        return unit3;
    }

    public void setUnit3(String unit3) {
        this.unit3 = unit3;
    }

    public String getUnit4() {
        return unit4;
    }

    public void setUnit4(String unit4) {
        this.unit4 = unit4;
    }

    public String getUnit5() {
        return unit5;
    }

    public void setUnit5(String unit5) {
        this.unit5 = unit5;
    }

    public String getUnit6() {
        return unit6;
    }

    public void setUnit6(String unit6) {
        this.unit6 = unit6;
    }

    public String getDtpnotes() {
        return dtpnotes;
    }

    public void setDtpnotes(String dtpnotes) {
        this.dtpnotes = dtpnotes;
    }

    public String getCheck6text() {
        return check6text;
    }

    public void setCheck6text(String check6text) {
        this.check6text = check6text;
    }

    public String getCheck7text() {
        return check7text;
    }

    public void setCheck7text(String check7text) {
        this.check7text = check7text;
    }

 
    public String getPorq() {
        return porq;
    }

    public void setPorq(String porq) {
        this.porq = porq;
    }
    
}
