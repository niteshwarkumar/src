package app.admin;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class AdminOption implements Serializable {

    /** identifier field */
    private Integer adminOptionId;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String trueFalseOption;

    /** nullable persistent field */
    private String stringOption;

    /** nullable persistent field */
    private String module;

    /** full constructor */
    public AdminOption(String description, String trueFalseOption, String stringOption, String module) {
        this.description = description;
        this.trueFalseOption = trueFalseOption;
        this.stringOption = stringOption;
        this.module = module;
    }

    /** default constructor */
    public AdminOption() {
    }

    public Integer getAdminOptionId() {
        return this.adminOptionId;
    }

    public void setAdminOptionId(Integer adminOptionId) {
        this.adminOptionId = adminOptionId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTrueFalseOption() {
        return this.trueFalseOption;
    }

    public void setTrueFalseOption(String trueFalseOption) {
        this.trueFalseOption = trueFalseOption;
    }

    public String getStringOption() {
        return this.stringOption;
    }

    public void setStringOption(String stringOption) {
        this.stringOption = stringOption;
    }

    public String getModule() {
        return this.module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("adminOptionId", getAdminOptionId())
            .toString();
    }

}
