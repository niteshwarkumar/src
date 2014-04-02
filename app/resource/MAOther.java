/*
 * MAOther.java
 *
 * Created on June 17, 2009, 3:03 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package app.resource;

/**
 *
 * @author PP41387
 */
public class MAOther {
    
    /** Creates a new instance of MAOther */
    public MAOther() {
    }
    private Integer id_ma;
    private Integer id_resource;
    
    private String refusingISA;
    private String deductionReason1;   
    private String deductionReason2;
    private String generalTandC;
    private String paymentPolicy;
    private String tradosPolicy;
    private String paymentCheck;
    private String paymentMail;
    private String paymentRegistered;
    private String wireTransfer;
    private String holdCcy;
    
     private double holdAmount;
    private double tRate;
    private double eRate;
    private double teRate;
    private double icrRate;
    
    private double refusingScore;
    private double rateScore;
    private String rateCcy;
    private double deductionReason2Score;
    private double totalScore;
    
    private double dtprefusingScore;
    private double dtprateScore;
    private double dtpdeductionReason2Score;
    private double dtptotalScore;
    
    private double expertrefusingScore;
    private double expertrateScore;
    private double expertdeductionReason2Score;
    private double experttotalScore;

    public Integer getId_ma() {
        return id_ma;
    }

    public void setId_ma(Integer id_ma) {
        this.id_ma = id_ma;
    }

    public Integer getId_resource() {
        return id_resource;
    }

    public void setId_resource(Integer id_resource) {
        this.id_resource = id_resource;
    }

    public String getRefusingISA() {
        return refusingISA;
    }

    public void setRefusingISA(String refusingISA) {
        this.refusingISA = refusingISA;
    }

    public String getDeductionReason1() {
        return deductionReason1;
    }

    public void setDeductionReason1(String deductionReason1) {
        this.deductionReason1 = deductionReason1;
    }

    public String getDeductionReason2() {
        return deductionReason2;
    }

    public void setDeductionReason2(String deductionReason2) {
        this.deductionReason2 = deductionReason2;
    }

    public String getGeneralTandC() {
        return generalTandC;
    }

    public void setGeneralTandC(String generalTandC) {
        this.generalTandC = generalTandC;
    }

    public String getPaymentPolicy() {
        return paymentPolicy;
    }

    public void setPaymentPolicy(String paymentPolicy) {
        this.paymentPolicy = paymentPolicy;
    }

    public String getTradosPolicy() {
        return tradosPolicy;
    }

    public void setTradosPolicy(String tradosPolicy) {
        this.tradosPolicy = tradosPolicy;
    }

    public String getPaymentCheck() {
        return paymentCheck;
    }

    public void setPaymentCheck(String paymentCheck) {
        this.paymentCheck = paymentCheck;
    }

    public String getPaymentMail() {
        return paymentMail;
    }

    public void setPaymentMail(String paymentMail) {
        this.paymentMail = paymentMail;
    }

    public String getPaymentRegistered() {
        return paymentRegistered;
    }

    public void setPaymentRegistered(String paymentRegistered) {
        this.paymentRegistered = paymentRegistered;
    }

    public String getWireTransfer() {
        return wireTransfer;
    }

    public void setWireTransfer(String wireTransfer) {
        this.wireTransfer = wireTransfer;
    }

    public String getHoldCcy() {
        return holdCcy;
    }

    public void setHoldCcy(String holdCcy) {
        this.holdCcy = holdCcy;
    }

    public double getHoldAmount() {
        return holdAmount;
    }

    public void setHoldAmount(double holdAmount) {
        this.holdAmount = holdAmount;
    }

    public double gettRate() {
        return tRate;
    }

    public void settRate(double tRate) {
        this.tRate = tRate;
    }

    public double geteRate() {
        return eRate;
    }

    public void seteRate(double eRate) {
        this.eRate = eRate;
    }

    public double getTeRate() {
        return teRate;
    }

    public void setTeRate(double teRate) {
        this.teRate = teRate;
    }

    public double getIcrRate() {
        return icrRate;
    }

    public void setIcrRate(double icrRate) {
        this.icrRate = icrRate;
    }

    public String getRateCcy() {
        return rateCcy;
    }

    public void setRateCcy(String rateCcy) {
        this.rateCcy = rateCcy;
    }

    public double getRefusingScore() {
        return refusingScore;
    }

    public void setRefusingScore(double refusingScore) {
        this.refusingScore = refusingScore;
    }

    public double getRateScore() {
        return rateScore;
    }

    public void setRateScore(double rateScore) {
        this.rateScore = rateScore;
    }

    public double getDeductionReason2Score() {
        return deductionReason2Score;
    }

    public void setDeductionReason2Score(double deductionReason2Score) {
        this.deductionReason2Score = deductionReason2Score;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public double getDtprefusingScore() {
        return dtprefusingScore;
    }

    public void setDtprefusingScore(double dtprefusingScore) {
        this.dtprefusingScore = dtprefusingScore;
    }

    public double getDtprateScore() {
        return dtprateScore;
    }

    public void setDtprateScore(double dtprateScore) {
        this.dtprateScore = dtprateScore;
    }

    public double getDtpdeductionReason2Score() {
        return dtpdeductionReason2Score;
    }

    public void setDtpdeductionReason2Score(double dtpdeductionReason2Score) {
        this.dtpdeductionReason2Score = dtpdeductionReason2Score;
    }

    public double getDtptotalScore() {
        return dtptotalScore;
    }

    public void setDtptotalScore(double dtptotalScore) {
        this.dtptotalScore = dtptotalScore;
    }

    public double getExpertrefusingScore() {
        return expertrefusingScore;
    }

    public void setExpertrefusingScore(double expertrefusingScore) {
        this.expertrefusingScore = expertrefusingScore;
    }

    public double getExpertrateScore() {
        return expertrateScore;
    }

    public void setExpertrateScore(double expertrateScore) {
        this.expertrateScore = expertrateScore;
    }

    public double getExpertdeductionReason2Score() {
        return expertdeductionReason2Score;
    }

    public void setExpertdeductionReason2Score(double expertdeductionReason2Score) {
        this.expertdeductionReason2Score = expertdeductionReason2Score;
    }

    public double getExperttotalScore() {
        return experttotalScore;
    }

    public void setExperttotalScore(double experttotalScore) {
        this.experttotalScore = experttotalScore;
    }

}
