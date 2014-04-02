package app.project;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Language implements Serializable {

    /** identifier field */
    private Integer languageId;

    /** nullable persistent field */
    private String language;
    
    /** nullable persistent field */
    private String abr;


    private String lang_group;

    /** full constructor */
    public Language(String language) {
        this.language = language;
    }

    /** default constructor */
    public Language() {
    }

    public Integer getLanguageId() {
        return this.languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("languageId", getLanguageId())
            .toString();
    }

    public String getAbr() {
        return abr;
    }

    public void setAbr(String abr) {
        this.abr = abr;
    }

    public String getLang_group() {
        return lang_group;
    }

    public void setLang_group(String lang_group) {
        this.lang_group = lang_group;
    }



}
