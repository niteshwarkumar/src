package app.resource;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class RateScoreDtp implements Serializable {

    /** identifier field */
    private Integer RateScoreDtpId;

    /** nullable persistent field */
    private String application;

    /** nullable persistent field */
    private String version;

    /** nullable persistent field */
    private String os;

    /** nullable persistent field */
    private String source;

    /** nullable persistent field */
    private String target;

    /** nullable persistent field */
    private String currency;

    /** nullable persistent field */
    private Double min;

    /** nullable persistent field */
    private Double rate;

    /** nullable persistent field */
    private String unit;
    
    

    /** nullable persistent field */
    private app.resource.Resource Resource;
    
    
     /** nullable persistent field */
    private String scoreNotes;
  /** nullable persistent field */
    private String assesmentSent;  
    private String assesmentReceived;  
     /** nullable persistent field */
    private String evaluationSent;
    private String evaluationReceived;
    private Double score;
    private String evaluator;

    /** full constructor */
    public RateScoreDtp(String application, String version, String os, String source, String target, String currency, Double min, Double rate, String unit, app.resource.Resource Resource) {
        this.application = application;
        this.version = version;
        this.os = os;
        this.source = source;
        this.target = target;
        this.currency = currency;
        this.min = min;
        this.rate = rate;
        this.unit = unit;
        this.Resource = Resource;
    }

    /** default constructor */
    public RateScoreDtp() {
    }

    public Integer getRateScoreDtpId() {
        return this.RateScoreDtpId;
    }

    public void setRateScoreDtpId(Integer RateScoreDtpId) {
        this.RateScoreDtpId = RateScoreDtpId;
    }

    public String getApplication() {
        return this.application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOs() {
        return this.os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getMin() {
        return this.min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getRate() {
        return this.rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public app.resource.Resource getResource() {
        return this.Resource;
    }

    public void setResource(app.resource.Resource Resource) {
        this.Resource = Resource;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("RateScoreDtpId", getRateScoreDtpId())
            .toString();
    }

    public String getScoreNotes() {
        return scoreNotes;
    }

    public void setScoreNotes(String scoreNotes) {
        this.scoreNotes = scoreNotes;
    }

    public String getAssesmentSent() {
        return assesmentSent;
    }

    public void setAssesmentSent(String assesmentSent) {
        this.assesmentSent = assesmentSent;
    }

    public String getAssesmentReceived() {
        return assesmentReceived;
    }

    public void setAssesmentReceived(String assesmentReceived) {
        this.assesmentReceived = assesmentReceived;
    }

    public String getEvaluationSent() {
        return evaluationSent;
    }

    public void setEvaluationSent(String evaluationSent) {
        this.evaluationSent = evaluationSent;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(String evaluator) {
        this.evaluator = evaluator;
    }

    public String getEvaluationReceived() {
        return evaluationReceived;
    }

    public void setEvaluationReceived(String evaluationReceived) {
        this.evaluationReceived = evaluationReceived;
    }

}
