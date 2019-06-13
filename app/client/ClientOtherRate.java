package app.client;

import app.user.*;
import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ClientOtherRate implements Serializable {
    public ClientOtherRate()
    {
        //System.out.println("ClientOtherRate constructor*******************************");
    }

    /** identifier field */
    private Integer clientOtherRateId;

    /** nullable persistent field */
    private String note;

    /** nullable persistent field */
    private String rate;

    /** nullable persistent field */
    private String units;

    /** nullable persistent field */
    private app.client.Client Company;

    /** full constructor */
    public ClientOtherRate(String note, String rate, String units, app.client.Client Company) {
        this.note = note;
        this.rate = rate;
        this.units = units;
        this.Company = Company;
    }

    
    public Integer getClientOtherRateId() {
        return this.clientOtherRateId;
    }

    public void setClientOtherRateId(Integer clientOtherRateId) {
        this.clientOtherRateId = clientOtherRateId;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
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
            .append("clientOtherRateId", getClientOtherRateId())
            .toString();
    }

  

}
