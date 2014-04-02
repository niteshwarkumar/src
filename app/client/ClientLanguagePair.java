package app.client;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ClientLanguagePair implements Serializable {
   
    private String task;
    /** identifier field */
    private Integer clientlanguagePairId;

    /** nullable persistent field */
    private String source;

    /** nullable persistent field */
    private String target;

    /** nullable persistent field */
    private String rate;

    /** nullable persistent field */
    private String units;

    /** nullable persistent field */
    private app.client.Client Company;

    /** full constructor */
    public ClientLanguagePair(String source, String target, String rate, String units, app.client.Client Company) {
        this.source = source;
        this.target = target;
        this.rate = rate;
        this.units = units;
        this.Company = Company;
    }

    /** default constructor */
    public ClientLanguagePair() {
        
    }

    public Integer getClientlanguagePairId() {
        return this.clientlanguagePairId;
    }

    public void setClientlanguagePairId(Integer clientlanguagePairId) {
        this.clientlanguagePairId = clientlanguagePairId;
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

    public String getRate() {
        return this.rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getUnits() {
        return this.units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public app.client.Client getCompany() {
        return this.Company;
    }

    public void setCompany(app.client.Client Company) {
        this.Company = Company;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("clientlanguagePairId", getClientlanguagePairId())
            .toString();
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

}
