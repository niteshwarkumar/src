package app.resource;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class LanguagePair implements Serializable {

    /** identifier field */
    private Integer languagePairId;

    /** nullable persistent field */
    private String source;

    /** nullable persistent field */
    private Integer sourceId;

    /** nullable persistent field */
    private String target;

    /** nullable persistent field */
    private Integer targetId;

    /** nullable persistent field */
    private boolean nativeLanguage;
    
    /** nullable persistent field */
    private boolean prefferedVendor;

    /** nullable persistent field */
    private String country;
    
    /** nullable persistent field */
    private Integer experienceSince;
    
    /** nullable persistent field */
    private String level;

    /** nullable persistent field */
    private app.resource.Resource Resource;

    /** persistent field */
    private Set RateScoreLanguages;

    /** full constructor */
    public LanguagePair(String source, Integer sourceId, String target, Integer targetId, boolean nativeLanguage, String country, app.resource.Resource Resource, Set RateScoreLanguages) {
        this.source = source;
        this.sourceId = sourceId;
        this.target = target;
        this.targetId = targetId;
        this.nativeLanguage = nativeLanguage;
        this.country = country;
        this.Resource = Resource;
        this.RateScoreLanguages = RateScoreLanguages;
    }
      public LanguagePair( String source, Integer sourceId, String target, Integer targetId, boolean nativeLanguage, boolean prefferedVendor, String country, Resource Resource, Set RateScoreLanguages) {
        this.source = source;
        this.sourceId = sourceId;
        this.target = target;
        this.targetId = targetId;
        this.nativeLanguage = nativeLanguage;
        this.prefferedVendor = prefferedVendor;
        this.country = country;
        this.Resource = Resource;
        this.RateScoreLanguages = RateScoreLanguages;
    }


    /** default constructor */
    public LanguagePair() {
    }

    /** minimal constructor */
    public LanguagePair(Set RateScoreLanguages) {
        this.RateScoreLanguages = RateScoreLanguages;
    }

    public Integer getLanguagePairId() {
        return this.languagePairId;
    }

    public void setLanguagePairId(Integer languagePairId) {
        this.languagePairId = languagePairId;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getSourceId() {
        return this.sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getTargetId() {
        return this.targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public boolean isNativeLanguage() {
        return this.nativeLanguage;
    }

    public void setNativeLanguage(boolean nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public app.resource.Resource getResource() {
        return this.Resource;
    }

    public void setResource(app.resource.Resource Resource) {
        this.Resource = Resource;
    }

    public Set getRateScoreLanguages() {
        return this.RateScoreLanguages;
    }

    public void setRateScoreLanguages(Set RateScoreLanguages) {
        this.RateScoreLanguages = RateScoreLanguages;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("languagePairId", getLanguagePairId())
            .toString();
    }

    public boolean isPrefferedVendor() {
        return prefferedVendor;
    }

    public void setPrefferedVendor(boolean prefferedVendor) {
        this.prefferedVendor = prefferedVendor;
    }

    public Integer getExperienceSince() {
        return experienceSince;
    }

    public void setExperienceSince(Integer experienceSince) {
        this.experienceSince = experienceSince;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
    
    

}
