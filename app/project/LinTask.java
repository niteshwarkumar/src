package app.project;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class LinTask implements Serializable {

    private int changeNo;
    private String changeDesc;
    /** identifier field */
    private Integer linTaskId;
 /** nullable persistent field */
    private String scoreDescription;
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
    private Integer score;

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

    private Date icrSentDate;

    private Date icrRecievedDate;

    private String icrFinal;


    private Boolean ICRcheck;

    private Boolean POCheck;

    /** nullable persistent field */
    private String invoiceDate;



    /** nullable persistent field */
    private Date invoiceDateDate;

    /** nullable persistent field */
    private Double quantity;

    /** nullable persistent field */
    private String units;

    /** nullable persistent field */
    private Integer word100;

    /** nullable persistent field */
    private Integer wordRep;

    /** nullable persistent field */
    private Integer word95;

    /** nullable persistent field */
    private Integer word85;

    /** nullable persistent field */
    private Integer word75;

    /** nullable persistent field */
    private Double wordNew;

    /** nullable persistent field */
    private Integer word8599;

    /** nullable persistent field */
    private Double wordNew4;

    /** nullable persistent field */
    private Double wordTotal;

    /** nullable persistent field */
    private String currency;

    /** nullable persistent field */
    private String rate;

    /** nullable persistent field */
    private String dollarTotal;

    /** nullable persistent field */
    private Integer wordPerfect;

    /** nullable persistent field */
    private Integer wordContext;

    /********FEE*******/
       /** nullable persistent field */
    private String unitsFee;

    /** nullable persistent field */
    private Integer word100Fee;

    /** nullable persistent field */
    private Integer wordRepFee;

    /** nullable persistent field */
    private Integer word95Fee;

    /** nullable persistent field */
    private Integer word85Fee;

    /** nullable persistent field */
    private Integer word75Fee;

    /** nullable persistent field */
    private Double wordNewFee;

    /** nullable persistent field */
    private Integer word8599Fee;

    /** nullable persistent field */
    private Double wordNew4Fee;

    /** nullable persistent field */
    private Double wordTotalFee;

    /** nullable persistent field */
    private String currencyFee;

    /** nullable persistent field */
    private String rateFee;

    /** nullable persistent field */
    private String dollarTotalFee;

	/** nullable persistent field */
    private String notesFee;

    private Double minFeeRsf;

    /** nullable persistent field */
    private Integer wordPerfectFee;

    /** nullable persistent field */
    private Integer WordContextFee;

    private String multi;

    /******************END OF FEE*******************/

    /** nullable persistent field */
    private String internalCurrency;

    /** nullable persistent field */
    private String internalRate;

    /** nullable persistent field */
    private String internalDollarTotal;

    /** nullable persistent field */
    private app.project.TargetDoc TargetDoc;
    
    private app.project.Scale Scale;

    private Double minFee;
    /** full constructor
     * @param taskName
     * @param scoreDescription
     * @param personName */
    public LinTask(String taskName,String scoreDescription, String personName, Integer orderNum, String sourceLanguage, String targetLanguage, String notes, String notesFee,String poNumber, Integer score, String postQuote, String sentDate, String dueDate, String receivedDate, Date sentDateDate, Date dueDateDate, Date receivedDateDate, String invoiceDate, Date invoiceDateDate, String units, String unitsFee,Double quantity, Integer word100, Integer wordRep, Integer word95, Integer word85, Integer word75, Double wordNew, Integer word8599, Double wordNew4, Double wordTotal, String currency, String internalCurrency, String rate, String dollarTotal, String internalRate, String internalDollarTotal, Integer wordRepFee, Integer word95Fee, Integer word100Fee, Integer word85Fee, Integer word75Fee, Double wordNewFee, Integer word8599Fee, Double wordNew4Fee, Double wordTotalFee, String currencyFee, String internalCurrencyFee, String rateFee, String dollarTotalFee, String internalRateFee, String internalDollarTotalFee, app.project.TargetDoc TargetDoc, String multi, app.project.Scale Scale) {
        this.taskName = taskName;
        this.personName = personName;
        this.orderNum = orderNum;
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;

        this.poNumber = poNumber;
        this.scoreDescription = scoreDescription;
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
        this.quantity = quantity;

        this.notes = notes;
        this.units = units;
        this.word100 = word100;
        this.wordRep = wordRep;
        this.word95 = word95;
        this.word85 = word85;
        this.word75 = word75;
        this.wordNew = wordNew;
        this.word8599 = word8599;
        this.wordNew4 = wordNew4;
        this.wordTotal = wordTotal;
        this.currency = currency;
        this.rate = rate;
        this.dollarTotal = dollarTotal;

        this.notesFee = notesFee;
        this.unitsFee = unitsFee;
        this.word100Fee = word100Fee;
        this.wordRepFee = wordRepFee;
        this.word95Fee = word95Fee;
        this.word85Fee = word85Fee;
        this.word75Fee = word75Fee;
        this.wordNewFee = wordNewFee;
        this.word8599Fee = word8599Fee;
        this.wordNew4Fee = wordNew4Fee;
        this.wordTotalFee = wordTotalFee;
        this.currencyFee = currencyFee;
        this.rateFee = rateFee;
        this.dollarTotalFee = dollarTotalFee;
        this.multi = multi;

        this.internalCurrency = internalCurrency;


        this.internalRate = internalRate;
        this.internalDollarTotal = internalDollarTotal;
        this.TargetDoc = TargetDoc;
        this.Scale = Scale;
    }

    /** default constructor */
    public LinTask() {
    }

    public Integer getLinTaskId() {
        return this.linTaskId;
    }

    public void setLinTaskId(Integer linTaskId) {
        this.linTaskId = linTaskId;
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

    public Date getIcrRecievedDate() {
        return icrRecievedDate;
    }

    public void setIcrRecievedDate(Date icrRecievedDate) {
        this.icrRecievedDate = icrRecievedDate;
    }

    public String getIcrFinal() {
        return icrFinal;
    }

    public void setIcrFinal(String icrFinal) {
        this.icrFinal = icrFinal;
    }

    public Date getIcrSentDate() {
        return icrSentDate;
    }

    public void setIcrSentDate(Date icrSentDate) {
        this.icrSentDate = icrSentDate;
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

    public Integer getWord100() {
        return this.word100;
    }

    public void setWord100(Integer word100) {
        this.word100 = word100;
    }

    public Integer getWordRep() {
        return this.wordRep;
    }

    public void setWordRep(Integer wordRep) {
        this.wordRep = wordRep;
    }

    public Integer getWord95() {
        return this.word95;
    }

    public void setWord95(Integer word95) {
        this.word95 = word95;
    }

    public Integer getWord85() {
        return this.word85;
    }

    public void setWord85(Integer word85) {
        this.word85 = word85;
    }

    public Integer getWord75() {
        return this.word75;
    }

    public void setWord75(Integer word75) {
        this.word75 = word75;
    }

    public Double getWordNew() {
        return this.wordNew;
    }

    public void setWordNew(Double wordNew) {
        this.wordNew = wordNew;
    }

    public Integer getWord8599() {
        return this.word8599;
    }

    public void setWord8599(Integer word8599) {
        this.word8599 = word8599;
    }

    public Double getWordNew4() {
        return this.wordNew4;
    }

    public void setWordNew4(Double wordNew4) {
        this.wordNew4 = wordNew4;
    }

    public Double getWordTotal() {
        return this.wordTotal;
    }

    public void setWordTotal(Double wordTotal) {
        this.wordTotal = wordTotal;
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
            .append("linTaskId", getLinTaskId())
            .toString();
    }

    public String getUnitsFee() {
        return unitsFee;
    }

    public void setUnitsFee(String unitsFee) {
        this.unitsFee = unitsFee;
    }

    public Integer getWord100Fee() {
        return word100Fee;
    }

    public void setWord100Fee(Integer word100Fee) {
        this.word100Fee = word100Fee;
    }

    public Integer getWordRepFee() {
        return wordRepFee;
    }

    public void setWordRepFee(Integer wordRepFee) {
        this.wordRepFee = wordRepFee;
    }

    public Integer getWord95Fee() {
        return word95Fee;
    }

    public void setWord95Fee(Integer word95Fee) {
        this.word95Fee = word95Fee;
    }

    public Integer getWord85Fee() {
        return word85Fee;
    }

    public void setWord85Fee(Integer word85Fee) {
        this.word85Fee = word85Fee;
    }

    public Integer getWord75Fee() {
        return word75Fee;
    }

    public void setWord75Fee(Integer word75Fee) {
        this.word75Fee = word75Fee;
    }

    public Double getWordNewFee() {
        return wordNewFee;
    }

    public void setWordNewFee(Double wordNewFee) {
        this.wordNewFee = wordNewFee;
    }

    public Integer getWord8599Fee() {
        return word8599Fee;
    }

    public void setWord8599Fee(Integer word8599Fee) {
        this.word8599Fee = word8599Fee;
    }

    public Double getWordNew4Fee() {
        return wordNew4Fee;
    }

    public void setWordNew4Fee(Double wordNew4Fee) {
        this.wordNew4Fee = wordNew4Fee;
    }

    public Double getWordTotalFee() {
        return wordTotalFee;
    }

    public void setWordTotalFee(Double wordTotalFee) {
        this.wordTotalFee = wordTotalFee;
    }

    public String getCurrencyFee() {
        return currencyFee;
    }

    public void setCurrencyFee(String currencyFee) {
        this.currencyFee = currencyFee;
    }

    public String getRateFee() {
        return rateFee;
    }

    public void setRateFee(String rateFee) {
        this.rateFee = rateFee;
    }

    public String getDollarTotalFee() {
        return dollarTotalFee;
    }

    public void setDollarTotalFee(String dollarTotalFee) {
        this.dollarTotalFee = dollarTotalFee;
    }

    public String getNotesFee() {
        return notesFee;
    }

    public void setNotesFee(String notesFee) {
        this.notesFee = notesFee;
    }

    public Double getMinFee() {
        return minFee;
    }

    public void setMinFee(Double minFee) {
        this.minFee = minFee;
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
      public Boolean getICRcheck() {
        return ICRcheck;
    }

    public void setICRcheck(Boolean ICRcheck) {
        this.ICRcheck = ICRcheck;
    }

    public Double getMinFeeRsf() {
        return minFeeRsf;
    }

    public void setMinFeeRsf(Double minFeeRsf) {
        this.minFeeRsf = minFeeRsf;
    }

    public Integer getWordContextFee() {
        return WordContextFee;
    }

    public void setWordContextFee(Integer WordContextFee) {
        this.WordContextFee = WordContextFee;
    }

    public Integer getWordContext() {
        return wordContext;
    }

    public void setWordContext(Integer wordContext) {
        this.wordContext = wordContext;
    }

    public Integer getWordPerfect() {
        return wordPerfect;
    }

    public void setWordPerfect(Integer wordPerfect) {
        this.wordPerfect = wordPerfect;
    }

    public Integer getWordPerfectFee() {
        return wordPerfectFee;
    }

    public void setWordPerfectFee(Integer wordPerfectFee) {
        this.wordPerfectFee = wordPerfectFee;
    }

    public Boolean getPOCheck() {
        return POCheck;
    }

    public void setPOCheck(Boolean POCheck) {
        this.POCheck = POCheck;
    }

    public String getMulti() {
        return multi;
    }

    public void setMulti(String multi) {
        this.multi = multi;
    }

    public Scale getScale() {
        return Scale;
    }

    public void setScale(Scale Scale) {
        this.Scale = Scale;
    }






}
