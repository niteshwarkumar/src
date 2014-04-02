package app.project;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class EngTask implements Serializable {
private String changeDesc;
    /** identifier field */
    private Integer engTaskId;

    /** nullable persistent field */
    private String taskName;

    /** nullable persistent field */
    private String personName;

    /** nullable persistent field */
    private Integer orderNum;

    /** nullable persistent field */
    private String sourceLanguage;

    /** nullable persistent field */
    private String targetLanguage;

    /** nullable persistent field */
    private String notes;

    /** nullable persistent field */
    private String poNumber;
    
    /** nullable persistent field */
    private String scoreDescription;

    /** nullable persistent field */
    private Integer score;
private int changeNo;
    /** nullable persistent field */
    private String postQuote;

    /** nullable persistent field */
    private String sentDate;

    /** nullable persistent field */
    private String dueDate;

    /** nullable persistent field */
    private String receivedDate;

    /** nullable persistent field */
    private Date sentDateDate;

    /** nullable persistent field */
    private Date dueDateDate;

    /** nullable persistent field */
    private Date receivedDateDate;

    /** nullable persistent field */
    private String invoiceDate;

    /** nullable persistent field */
    private Date invoiceDateDate;

    /** nullable persistent field */
    private String units;

    /** nullable persistent field */
    private Double quantity;

    /** nullable persistent field */
    private String currency;

    /** nullable persistent field */
    private String internalCurrency;

    /** nullable persistent field */
    private Double total;

    /** nullable persistent field */
    private String rate;

    /** nullable persistent field */
    private String dollarTotal;

    /** nullable persistent field */
    private String internalRate;

    /** nullable persistent field */
    private String internalDollarTotal;

    /** nullable persistent field */
    private app.project.TargetDoc TargetDoc;
    
    private String notesTeam;
private String unitsTeam;
private Double totalTeam;

    /** full constructor */
    public EngTask(String taskName, String personName, Integer orderNum, String sourceLanguage, String targetLanguage, String notes, String poNumber, Integer score, String postQuote, String sentDate, String dueDate, String receivedDate, Date sentDateDate, Date dueDateDate, Date receivedDateDate, String invoiceDate, Date invoiceDateDate, String units, Double quantity, String currency, String internalCurrency, Double total, String rate, String dollarTotal, String internalRate, String internalDollarTotal, String notesTeam, String unitsTeam, Double totalTeam, app.project.TargetDoc TargetDoc) {
        
        this.notesTeam  = notesTeam;
        this.unitsTeam  = unitsTeam;
        this.totalTeam   = totalTeam;       
        
        this.taskName = taskName;
        this.personName = personName;
        this.orderNum = orderNum;
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
        this.notes = notes;
        this.poNumber = poNumber;
        this.score = score;
        this.postQuote = postQuote;
        this.sentDate = sentDate;
        this.dueDate = dueDate;
        this.receivedDate = receivedDate;
        this.sentDateDate = sentDateDate;
        this.dueDateDate = dueDateDate;
        this.receivedDateDate = receivedDateDate;
        this.invoiceDate = invoiceDate;
        this.invoiceDateDate = invoiceDateDate;
        this.units = units;
        this.quantity = quantity;
        this.currency = currency;
        this.internalCurrency = internalCurrency;
        this.total = total;
        this.rate = rate;
        this.dollarTotal = dollarTotal;
        this.internalRate = internalRate;
        this.internalDollarTotal = internalDollarTotal;
        this.TargetDoc = TargetDoc;
    }

    /** default constructor */
    public EngTask() {
    }

    public Integer getEngTaskId() {
        return this.engTaskId;
    }

    public void setEngTaskId(Integer engTaskId) {
        this.engTaskId = engTaskId;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getPersonName() {
        return this.personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Integer getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getSourceLanguage() {
        return this.sourceLanguage;
    }

    public void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public String getTargetLanguage() {
        return this.targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPoNumber() {
        return this.poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public Integer getScore() {
        return this.score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getPostQuote() {
        return this.postQuote;
    }

    public void setPostQuote(String postQuote) {
        this.postQuote = postQuote;
    }

    public String getSentDate() {
        return this.sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getReceivedDate() {
        return this.receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Date getSentDateDate() {
        return this.sentDateDate;
    }

    public void setSentDateDate(Date sentDateDate) {
        this.sentDateDate = sentDateDate;
    }

    public Date getDueDateDate() {
        return this.dueDateDate;
    }

    public void setDueDateDate(Date dueDateDate) {
        this.dueDateDate = dueDateDate;
    }

    public Date getReceivedDateDate() {
        return this.receivedDateDate;
    }

    public void setReceivedDateDate(Date receivedDateDate) {
        this.receivedDateDate = receivedDateDate;
    }

    public String getInvoiceDate() {
        return this.invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getInvoiceDateDate() {
        return this.invoiceDateDate;
    }

    public void setInvoiceDateDate(Date invoiceDateDate) {
        this.invoiceDateDate = invoiceDateDate;
    }

    public String getUnits() {
        return this.units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getInternalCurrency() {
        return this.internalCurrency;
    }

    public void setInternalCurrency(String internalCurrency) {
        this.internalCurrency = internalCurrency;
    }

    public Double getTotal() {
        return this.total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getRate() {
        return this.rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDollarTotal() {
        return this.dollarTotal;
    }

    public void setDollarTotal(String dollarTotal) {
        this.dollarTotal = dollarTotal;
    }

    public String getInternalRate() {
        return this.internalRate;
    }

    public void setInternalRate(String internalRate) {
        this.internalRate = internalRate;
    }

    public String getInternalDollarTotal() {
        return this.internalDollarTotal;
    }

    public void setInternalDollarTotal(String internalDollarTotal) {
        this.internalDollarTotal = internalDollarTotal;
    }

    public app.project.TargetDoc getTargetDoc() {
        return this.TargetDoc;
    }

    public void setTargetDoc(app.project.TargetDoc TargetDoc) {
        this.TargetDoc = TargetDoc;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("engTaskId", getEngTaskId())
            .toString();
    }

    public Double getTotalTeam() {
        return totalTeam;
    }

    public void setTotalTeam(Double totalTeam) {
        this.totalTeam = totalTeam;
    }

    public String getNotesTeam() {
        return notesTeam;
    }

    public void setNotesTeam(String notesTeam) {
        this.notesTeam = notesTeam;
    }

    public String getUnitsTeam() {
        return unitsTeam;
    }

    public void setUnitsTeam(String unitsTeam) {
        this.unitsTeam = unitsTeam;
    }

    public String getChangeDesc() {
        return changeDesc;
    }

    public void setChangeDesc(String changeDesc) {
        this.changeDesc = changeDesc;
    }

    public int getChangeNo() {
        return changeNo;
    }

    public void setChangeNo(int changeNo) {
        this.changeNo = changeNo;
    }

    public String getScoreDescription() {
        return scoreDescription;
    }

    public void setScoreDescription(String scoreDescription) {
        this.scoreDescription = scoreDescription;
    }

}
