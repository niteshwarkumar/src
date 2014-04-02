package app.project;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class TargetDoc implements Serializable {

    /** identifier field */
    private Integer targetDocId;

    /** nullable persistent field */
    private String name;

    /** nullable persistent field */
    private String language;

    /** nullable persistent field */
    private Integer changeNo;

    /** nullable persistent field */
    private app.project.SourceDoc SourceDoc;

    /** persistent field */
    private Set LinTasks;

    /** persistent field */
    private Set EngTasks;

    /** persistent field */
    private Set DtpTasks;

    /** persistent field */
    private Set OthTasks;

    /** full constructor */
    public TargetDoc(String name, String language, app.project.SourceDoc SourceDoc, Set LinTasks, Set EngTasks, Set DtpTasks, Set OthTasks) {
        this.name = name;
        this.language = language;
        this.SourceDoc = SourceDoc;
        this.LinTasks = LinTasks;
        this.EngTasks = EngTasks;
        this.DtpTasks = DtpTasks;
        this.OthTasks = OthTasks;
        this.setChangeNo(new Integer(0));
    }

    /** default constructor */
    public TargetDoc() {
        this.setChangeNo(new Integer(0));
    }

    /** minimal constructor */
    public TargetDoc(Set LinTasks, Set EngTasks, Set DtpTasks, Set OthTasks) {
        this.LinTasks = LinTasks;
        this.EngTasks = EngTasks;
        this.DtpTasks = DtpTasks;
        this.OthTasks = OthTasks;
        this.setChangeNo(new Integer(0));
    }

    public Integer getTargetDocId() {
        return this.targetDocId;
    }

    public void setTargetDocId(Integer targetDocId) {
        this.targetDocId = targetDocId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public app.project.SourceDoc getSourceDoc() {
        return this.SourceDoc;
    }

    public void setSourceDoc(app.project.SourceDoc SourceDoc) {
        this.SourceDoc = SourceDoc;
    }

    public Set getLinTasks() {
        return this.LinTasks;
    }

    public void setLinTasks(Set LinTasks) {
        this.LinTasks = LinTasks;
    }

    public Set getEngTasks() {
        return this.EngTasks;
    }

    public void setEngTasks(Set EngTasks) {
        this.EngTasks = EngTasks;
    }

    public Set getDtpTasks() {
        return this.DtpTasks;
    }

    public void setDtpTasks(Set DtpTasks) {
        this.DtpTasks = DtpTasks;
    }

    public Set getOthTasks() {
        return this.OthTasks;
    }

    public void setOthTasks(Set OthTasks) {
        this.OthTasks = OthTasks;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("targetDocId", getTargetDocId())
            .toString();
    }

    public Integer getChangeNo() {
        return changeNo;
    }

    public void setChangeNo(Integer changeNo) {
        this.changeNo = changeNo;
    }

    

}
