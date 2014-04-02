package app.resource;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class RateScoreLanguage implements Serializable {

    /** nullable persistent field */
    private String evaluatedBy;
    
    /** nullable persistent field */
    private String evaluatedDate;
    
    /** identifier field */
    private Integer RateScoreLanguageId;

    /** nullable persistent field */
    private String specialty;

    /** nullable persistent field */
    private String source;

    /** nullable persistent field */
    private String target;

    /** nullable persistent field */
    private Double score;

    /** nullable persistent field */
    private String currency;

    /** nullable persistent field */
    private Double min;

    /** nullable persistent field */
    private Double t;

    /** nullable persistent field */
    private String tunit;
    
    /** nullable persistent field */
    private Double icr;

    /** nullable persistent field */
    private String icrunit;

    /** nullable persistent field */
    private Double e;

    /** nullable persistent field */
    private String eunit;

    /** nullable persistent field */
    private Double te;

    /** nullable persistent field */
    private String teunit;

    /** nullable persistent field */
    private Double p;

    /** nullable persistent field */
    private String punit;

    /** nullable persistent field */
    private app.resource.LanguagePair LanguagePair;

    /** nullable persistent field */
    private String scoreNotes;
  /** nullable persistent field */
    private String assesmentSent;  
    private String assesmentReceived;  
     /** nullable persistent field */
    private String evaluationSent;
    
    
    /** full constructor */
    public RateScoreLanguage(String specialty, String source, String target, Double score, String currency, Double min, Double t, String tunit, Double e, String eunit, Double te, String teunit, Double p, String punit, app.resource.LanguagePair LanguagePair) {
        this.specialty = specialty;
        this.source = source;
        this.target = target;
        this.score = score;
        this.currency = currency;
        this.min = min;
        this.t = t;
        this.tunit = tunit;
        this.e = e;
        this.eunit = eunit;
        this.te = te;
        this.teunit = teunit;
        this.p = p;
        this.punit = punit;
        this.LanguagePair = LanguagePair;
    }

    /** default constructor */
    public RateScoreLanguage() {
    }

    public Integer getRateScoreLanguageId() {
        return this.RateScoreLanguageId;
    }

    public void setRateScoreLanguageId(Integer RateScoreLanguageId) {
        this.RateScoreLanguageId = RateScoreLanguageId;
    }

    public String getSpecialty() {
        return this.specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
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

    public Double getScore() {
        return this.score;
    }

    public void setScore(Double score) {
        this.score = score;
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

    public Double getT() {
        return this.t;
    }

    public void setT(Double t) {
        this.t = t;
    }

    public String getTunit() {
        return this.tunit;
    }

    public void setTunit(String tunit) {
        this.tunit = tunit;
    }

    public Double getE() {
        return this.e;
    }

    public void setE(Double e) {
        this.e = e;
    }

    public String getEunit() {
        return this.eunit;
    }

    public void setEunit(String eunit) {
        this.eunit = eunit;
    }

    public Double getTe() {
        return this.te;
    }

    public void setTe(Double te) {
        this.te = te;
    }

    public String getTeunit() {
        return this.teunit;
    }

    public void setTeunit(String teunit) {
        this.teunit = teunit;
    }

    public Double getP() {
        return this.p;
    }

    public void setP(Double p) {
        this.p = p;
    }

    public String getPunit() {
        return this.punit;
    }

    public void setPunit(String punit) {
        this.punit = punit;
    }

    public app.resource.LanguagePair getLanguagePair() {
        return this.LanguagePair;
    }

    public void setLanguagePair(app.resource.LanguagePair LanguagePair) {
        this.LanguagePair = LanguagePair;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("RateScoreLanguageId", getRateScoreLanguageId())
            .toString();
    }

    public String getEvaluatedBy() {
        return evaluatedBy;
    }

    public void setEvaluatedBy(String evaluatedBy) {
        this.evaluatedBy = evaluatedBy;
    }

    public String getEvaluatedDate() {
        return evaluatedDate;
    }

    public void setEvaluatedDate(String evaluatedDate) {
        this.evaluatedDate = evaluatedDate;
    }

    public Double getIcr() {
        return icr;
    }

    public void setIcr(Double icr) {
        this.icr = icr;
    }

    public String getIcrunit() {
        return icrunit;
    }

    public void setIcrunit(String icrunit) {
        this.icrunit = icrunit;
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

}
