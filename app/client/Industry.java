package app.client;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Industry implements Serializable {

    /** identifier field */
    private Integer industryId;

    /** nullable persistent field */
    private String Code;

    /** nullable persistent field */
    private String Description;

    /** persistent field */
    private Set Clients;

    /** persistent field */
    private Set SpecificIndustries;

    /** persistent field */
    private Set Resources;

    /** full constructor */
    public Industry(String Code, String Description, Set Clients, Set SpecificIndustries, Set Resources) {
        this.Code = Code;
        this.Description = Description;
        this.Clients = Clients;
        this.SpecificIndustries = SpecificIndustries;
        this.Resources = Resources;
    }

    /** default constructor */
    public Industry() {
    }

    /** minimal constructor */
    public Industry(Set Clients, Set SpecificIndustries, Set Resources) {
        this.Clients = Clients;
        this.SpecificIndustries = SpecificIndustries;
        this.Resources = Resources;
    }

    public Integer getIndustryId() {
        return this.industryId;
    }

    public void setIndustryId(Integer industryId) {
        this.industryId = industryId;
    }

    public String getCode() {
        return this.Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getDescription() {
        return this.Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public Set getClients() {
        return this.Clients;
    }

    public void setClients(Set Clients) {
        this.Clients = Clients;
    }

    public Set getSpecificIndustries() {
        return this.SpecificIndustries;
    }

    public void setSpecificIndustries(Set SpecificIndustries) {
        this.SpecificIndustries = SpecificIndustries;
    }

    public Set getResources() {
        return this.Resources;
    }

    public void setResources(Set Resources) {
        this.Resources = Resources;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("industryId", getIndustryId())
            .toString();
    }

}
