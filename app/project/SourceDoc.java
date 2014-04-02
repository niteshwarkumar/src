package app.project;

import app.quote.Client_Quote;
import app.quote.Quote1;
import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class SourceDoc implements Serializable {

    /** identifier field */
    private Integer sourceDocId;
    
     /** nullable persistent field */
    private Integer changeNo;

    /** nullable persistent field */
    private String name;

    /** nullable persistent field */
    private String language;

    /** nullable persistent field */
    private app.project.Project Project;

    /** nullable persistent field */
    private Quote1 Quote;


    private Client_Quote Client_Quote;

    /** persistent field */
    private Set TargetDocs;

    /** full constructor */
    public SourceDoc(String name, String language, app.project.Project Project, Quote1 Quote,Client_Quote Client_Quote,Set TargetDocs) {
        this.name = name;
        this.language = language;
        this.Project = Project;
        this.Quote = Quote;
        this.Client_Quote=Client_Quote;
        this.TargetDocs = TargetDocs;
        this.setChangeNo(new Integer(0));
    }


    /** default constructor */
    public SourceDoc() {
        this.setChangeNo(new Integer(0));
    }

    /** minimal constructor */
    public SourceDoc(Set TargetDocs) {
        this.TargetDocs = TargetDocs;
        this.setChangeNo(new Integer(0));
    }



    public Integer getSourceDocId() {
        return this.sourceDocId;
    }

    public void setSourceDocId(Integer sourceDocId) {
        this.sourceDocId = sourceDocId;
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

    public app.project.Project getProject() {
        return this.Project;
    }

    public void setProject(app.project.Project Project) {
        this.Project = Project;
    }

    public Quote1 getQuote() {
        return this.Quote;
    }

    public void setQuote(Quote1 Quote) {
        this.Quote = Quote;
    }

     public Client_Quote getClient_Quote() {
        return Client_Quote;
    }

    public void setClient_Quote(Client_Quote Client_Quote) {
        this.Client_Quote = Client_Quote;
    }

    public Set getTargetDocs() {
        return this.TargetDocs;
    }

    public void setTargetDocs(Set TargetDocs) {
        this.TargetDocs = TargetDocs;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("sourceDocId", getSourceDocId())
            .toString();
    }

    public Integer getChangeNo() {
        return changeNo;
    }

    public void setChangeNo(Integer changeNo) {
        this.changeNo = changeNo;
    }
}
