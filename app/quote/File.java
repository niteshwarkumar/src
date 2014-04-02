package app.quote;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class File implements Serializable {

    /** identifier field */
    private Integer fileId;

    /** nullable persistent field */
    private String fileName;

    /** nullable persistent field */
    private String location;

    /** nullable persistent field */
    private String locationType;

    /** nullable persistent field */
    private String note;

    /** nullable persistent field */
    private String beforeAnalysis;

    /** nullable persistent field */
    private app.quote.Quote1 Quote;

    /** full constructor */
    public File(String fileName, String location, String locationType, String note, String beforeAnalysis, app.quote.Quote1 Quote) {
        this.fileName = fileName;
        this.location = location;
        this.locationType = locationType;
        this.note = note;
        this.beforeAnalysis = beforeAnalysis;
        this.Quote = Quote;
    }

    /** default constructor */
    public File() {
    }
 

    public Integer getFileId() {
        return this.fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationType() {
        return this.locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getBeforeAnalysis() {
        return this.beforeAnalysis;
    }

    public void setBeforeAnalysis(String beforeAnalysis) {
        this.beforeAnalysis = beforeAnalysis;
    }

    public app.quote.Quote1 getQuote() {
        return this.Quote;
    }

    public void setQuote(app.quote.Quote1 Quote) {
        this.Quote = Quote;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("fileId", getFileId())
            .toString();
    }

}
