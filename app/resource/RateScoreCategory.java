package app.resource;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class RateScoreCategory implements Serializable {

    /** identifier field */
    private Integer rateScoreCategoryId;

    /** nullable persistent field */
    private String category;

    /** full constructor */
    public RateScoreCategory(String category) {
        this.category = category;
    }

    /** default constructor */
    public RateScoreCategory() {
    }

    public Integer getRateScoreCategoryId() {
        return this.rateScoreCategoryId;
    }

    public void setRateScoreCategoryId(Integer rateScoreCategoryId) {
        this.rateScoreCategoryId = rateScoreCategoryId;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rateScoreCategoryId", getRateScoreCategoryId())
            .toString();
    }

}
