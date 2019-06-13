package app.user;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class SavedSearch implements Serializable {

    /** identifier field */
    private Integer savedSearchId;

    /** identifier field */
    private String clientName;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String resultTotal;

    /** nullable persistent field */
    private String firstName;

    /** nullable persistent field */
    private String lastName;

    /** nullable persistent field */
    private String singleCompanyName;


    /** nullable persistent field */
    private String contactFirstName;

    /** nullable persistent field */
    private String contactLastName;

    /** nullable persistent field */
    private String agency;
    
     /** nullable persistent field */
    private String isagency;

    /** nullable persistent field */
    private String oldId;

    /** nullable persistent field */
    private String sourceId;

    /** nullable persistent field */
    private String targetId;

    /** nullable persistent field */
    private String status;

    /** nullable persistent field */
    private String includeDoNot;

    /** nullable persistent field */
    private String translator;

    /** nullable persistent field */
    private String editor;

    /** nullable persistent field */
    private String proofreader;

    /** nullable persistent field */
    private String dtp;

    /** nullable persistent field */
    private String icr;

    /** nullable persistent field */
    private String trate;

    /** nullable persistent field */
    private String erate;

    /** nullable persistent field */
    private String terate;

    /** nullable persistent field */
    private String prate;

    /** nullable persistent field */
    private String dtpRate;

    /** nullable persistent field */
    private String rateOldDb;

    /** nullable persistent field */
    private String dtpSourceId;

//    /** nullable persistent field */
      private String dtpTargetId;
//
//    /** nullable persistent field */
 //   private String specific;
//
//    /** nullable persistent field */
    private String general;
//
//    /** nullable persistent field */
    private String scoresLin;
//
//    /** nullable persistent field */
    private String scoreOldDb;
//
//    /** nullable persistent field */
    private String projectScoreGreater;
//
//    /** nullable persistent field */
    private String usesTrados;
//
//    /** nullable persistent field */
    private String usesSdlx;
//
//    /** nullable persistent field */
    private String usesDejavu;
//
//    /** nullable persistent field */
    private String usesCatalyst;
//
//    /** nullable persistent field */
    private String usesTransit;
//
//    /** nullable persistent field */
    private String usesOtherTool1;
//
//    /** nullable persistent field */
    private String usesOtherTool2;
//
//    /** nullable persistent field */
    private String city;
//
//    /** nullable persistent field */
    private String country;
//
//    /** nullable persistent field */
    private String resume;
//
//    /** nullable persistent field */
    private app.user.User User;
//    /** nullable persistent field */
    private String other;
    private String consultant;
    private String partner;
    private String engineering;
    private String businesssuport;
    private String fqa;

    /** full constructor */
    public SavedSearch(String description, String resultTotal, String firstName, String lastName, String singleCompanyName, String contactFirstName, String contactLastName, String agency, String isAgency, String oldId, String sourceId, String targetId, String status, String includeDoNot, String translator, String editor, String proofreader, String dtp, String icr, String trate, String erate, String terate, String prate, String dtpRate, String rateOldDb, String dtpSourceId, String dtpTargetId, String specific, String general, String scoresLin, String scoreOldDb, String projectScoreGreater, String usesTrados, String usesSdlx, String usesDejavu, String usesCatalyst, String usesTransit, String usesOtherTool1, String usesOtherTool2, String city, String country, String resume, app.user.User User, String other, String consultant, String partner, String engineering, String businesssuport, String fqa) {
        this.description = description;
        this.resultTotal = resultTotal;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactFirstName = contactFirstName;
        this.contactLastName = contactLastName;
        this.agency = agency;
        this.isagency = isAgency;
        this.oldId = oldId;
        this.sourceId = sourceId;
        this.targetId = targetId;
        this.status = status;
        this.includeDoNot = includeDoNot;
        this.translator = translator;
        this.editor = editor;
        this.proofreader = proofreader;
        this.dtp = dtp;
        this.icr = icr;
        this.trate = trate;
        this.erate = erate;
        this.terate = terate;
        this.prate = prate;
        this.dtpRate = dtpRate;
        this.rateOldDb = rateOldDb;
        this.dtpSourceId = dtpSourceId;
        this.singleCompanyName = singleCompanyName;
//        this.dtpTargetId = dtpTargetId;
//        this.specific = specific;
//        this.general = general;
//        this.scoresLin = scoresLin;
//        this.scoreOldDb = scoreOldDb;
//        this.projectScoreGreater = projectScoreGreater;
//        this.usesTrados = usesTrados;
//        this.usesSdlx = usesSdlx;
//        this.usesDejavu = usesDejavu;
//        this.usesCatalyst = usesCatalyst;
//        this.usesTransit = usesTransit;
//        this.usesOtherTool1 = usesOtherTool1;
//        this.usesOtherTool2 = usesOtherTool2;
//        this.city = city;
//        this.country = country;
//        this.resume = resume;
//        this.User = User;
//        this.other = other;
//        this.consultant = consultant;
//        this.partner = partner;
//        this.engineering = engineering;
//        this.businesssuport = businesssuport;
        
    }

    /** default constructor */
    public SavedSearch() {
    }

    public Integer getSavedSearchId() {
        return this.savedSearchId;
    }

    public void setSavedSearchId(Integer savedSearchId) {
        this.savedSearchId = savedSearchId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResultTotal() {
        return this.resultTotal;
    }

    public void setResultTotal(String resultTotal) {
        this.resultTotal = resultTotal;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactFirstName() {
        return this.contactFirstName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public String getContactLastName() {
        return this.contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public String getAgency() {
        return this.agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getOldId() {
        return this.oldId;
    }

    public void setOldId(String oldId) {
        this.oldId = oldId;
    }

    public String getSourceId() {
        return this.sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getTargetId() {
        return this.targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIncludeDoNot() {
        return this.includeDoNot;
    }

    public void setIncludeDoNot(String includeDoNot) {
        this.includeDoNot = includeDoNot;
    }

    public String getTranslator() {
        return this.translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getEditor() {
        return this.editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getProofreader() {
        return this.proofreader;
    }

    public void setProofreader(String proofreader) {
        this.proofreader = proofreader;
    }

    public String getDtp() {
        return this.dtp;
    }

    public void setDtp(String dtp) {
        this.dtp = dtp;
    }

    public String getIcr() {
        return this.icr;
    }

    public void setIcr(String icr) {
        this.icr = icr;
    }

    public String getTrate() {
        return this.trate;
    }

    public void setTrate(String trate) {
        this.trate = trate;
    }

    public String getErate() {
        return this.erate;
    }

    public void setErate(String erate) {
        this.erate = erate;
    }

    public String getTerate() {
        return this.terate;
    }

    public void setTerate(String terate) {
        this.terate = terate;
    }

    public String getPrate() {
        return this.prate;
    }

    public void setPrate(String prate) {
        this.prate = prate;
    }

    public String getDtpRate() {
        return this.dtpRate;
    }

    public void setDtpRate(String dtpRate) {
        this.dtpRate = dtpRate;
    }

    public String getRateOldDb() {
        return this.rateOldDb;
    }

    public void setRateOldDb(String rateOldDb) {
        this.rateOldDb = rateOldDb;
    }

    public String getDtpSourceId() {
        return this.dtpSourceId;
    }

    public void setDtpSourceId(String dtpSourceId) {
        this.dtpSourceId = dtpSourceId;
    }

    public String getDtpTargetId() {
        return this.dtpTargetId;
    }

    public void setDtpTargetId(String dtpTargetId) {
        this.dtpTargetId = dtpTargetId;
    }
//
//    public String getSpecific() {
//        return this.specific;
//    }
//
//    public void setSpecific(String specific) {
//        this.specific = specific;
//    }

    public String getGeneral() {
        return this.general;
    }

    public void setGeneral(String general) {
        this.general = general;
    }

    public String getScoresLin() {
        return this.scoresLin;
    }

    public void setScoresLin(String scoresLin) {
        this.scoresLin = scoresLin;
    }

    public String getScoreOldDb() {
        return this.scoreOldDb;
    }

    public void setScoreOldDb(String scoreOldDb) {
        this.scoreOldDb = scoreOldDb;
    }

    public String getProjectScoreGreater() {
        return this.projectScoreGreater;
    }

    public void setProjectScoreGreater(String projectScoreGreater) {
        this.projectScoreGreater = projectScoreGreater;
    }

    public String getUsesTrados() {
        return this.usesTrados;
    }
//
    public void setUsesTrados(String usesTrados) {
        this.usesTrados = usesTrados;
    }
//
    public String getUsesSdlx() {
        return this.usesSdlx;
    }

    public void setUsesSdlx(String usesSdlx) {
        this.usesSdlx = usesSdlx;
    }

    public String getUsesDejavu() {
        return this.usesDejavu;
    }

    public void setUsesDejavu(String usesDejavu) {
        this.usesDejavu = usesDejavu;
    }

    public String getUsesCatalyst() {
        return this.usesCatalyst;
    }

    public void setUsesCatalyst(String usesCatalyst) {
        this.usesCatalyst = usesCatalyst;
    }

    public String getUsesTransit() {
        return this.usesTransit;
    }

    public void setUsesTransit(String usesTransit) {
        this.usesTransit = usesTransit;
    }

    public String getUsesOtherTool1() {
        return this.usesOtherTool1;
    }

    public void setUsesOtherTool1(String usesOtherTool1) {
        this.usesOtherTool1 = usesOtherTool1;
    }

    public String getUsesOtherTool2() {
        return this.usesOtherTool2;
    }

    public void setUsesOtherTool2(String usesOtherTool2) {
        this.usesOtherTool2 = usesOtherTool2;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getResume() {
        return this.resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
//
    public app.user.User getUser() {
        return this.User;
    }

    public void setUser(app.user.User User) {
        this.User = User;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("savedSearchId", getSavedSearchId())
            .toString();
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getConsultant() {
        return consultant;
    }

    public void setConsultant(String consultant) {
        this.consultant = consultant;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getEngineering() {
        return engineering;
    }

    public void setEngineering(String engineering) {
        this.engineering = engineering;
    }

    public String getBusinesssuport() {
        return businesssuport;
    }

    public void setBusinesssuport(String businesssuport) {
        this.businesssuport = businesssuport;
    }

    public String getFqa() {
        return fqa;
    }

    public void setFqa(String fqa) {
        this.fqa = fqa;
    }


    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getSingleCompanyName() {
        return singleCompanyName;
    }

    public void setSingleCompanyName(String singleCompanyName) {
        this.singleCompanyName = singleCompanyName;
    }

    public String getIsagency() {
        return isagency;
    }

    public void setIsagency(String isagency) {
        this.isagency = isagency;
    }

 

}
