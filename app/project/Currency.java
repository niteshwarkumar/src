package app.project;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Currency implements Serializable {

    /** identifier field */
    private Integer currencyId;

    /** nullable persistent field */
    private String currency;

    /** full constructor */
    public Currency(String currency) {
        this.currency = currency;
    }

    /** default constructor */
    public Currency() {
    }

    public Integer getCurrencyId() {
        return this.currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("currencyId", getCurrencyId())
            .toString();
    }

}
