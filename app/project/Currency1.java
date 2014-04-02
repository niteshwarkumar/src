package app.project;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Currency1 implements Serializable {

    /** identifier field */
    private Integer currency1Id;

    /** nullable persistent field */
    private String currency;

    /** full constructor */
    public Currency1(String currency) {
        this.currency = currency;
    }

    /** default constructor */
    public Currency1() {
    }

    public Integer getCurrency1Id() {
        return this.currency1Id;
    }

    public void setCurrency1Id(Integer currency1Id) {
        this.currency1Id = currency1Id;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("currency1Id", getCurrency1Id())
            .toString();
    }

}
