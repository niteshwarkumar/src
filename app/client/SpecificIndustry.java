package app.client;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class SpecificIndustry implements Serializable {

    /** identifier field */
    private Integer specificIndustryId;

    /** nullable persistent field */
    private String specificIndustry;

    /** nullable persistent field */
    private app.client.Industry Industry;

    /** persistent field */
    private Set Resources;

    /** full constructor */
    public SpecificIndustry(String specificIndustry, app.client.Industry Industry, Set Resources) {
        this.specificIndustry = specificIndustry;
        this.Industry = Industry;
        this.Resources = Resources;
    }

    /** default constructor */
    public SpecificIndustry() {
    }

    /** minimal constructor */
    public SpecificIndustry(Set Resources) {
        this.Resources = Resources;
    }

    public Integer getSpecificIndustryId() {
        return this.specificIndustryId;
    }

    public void setSpecificIndustryId(Integer specificIndustryId) {
        this.specificIndustryId = specificIndustryId;
    }

    public String getSpecificIndustry() {
        return this.specificIndustry;
    }

    public void setSpecificIndustry(String specificIndustry) {
        this.specificIndustry = specificIndustry;
    }

    public app.client.Industry getIndustry() {
        return this.Industry;
    }

    public void setIndustry(app.client.Industry Industry) {
        this.Industry = Industry;
    }

    public Set getResources() {
        return this.Resources;
    }

    public void setResources(Set Resources) {
        this.Resources = Resources;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("specificIndustryId", getSpecificIndustryId())
            .toString();
    }

}
