package app.project;

import app.client.Client;
import app.client.ClientContact;
import app.standardCode.StandardCode;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Project implements Serializable {
 
    /** identifier field */
    private Integer projectId;
    /** nullable persistent field */
    private String number;
    /** nullable persistent field */
    private String status;
    /** nullable persistent field */
    private String product;
    /** nullable persistent field */
    private String productDescription;
    /** nullable persistent field */
    private String productUnits;
    /** nullable persistent field */
    private String projectRequirements;
    /** nullable persistent field */
    private String projectDescription;
    /** nullable persistent field */
    private Date startDate;
    /** nullable persistent field */
    private Date dueDate;
    /** nullable persistent field */
    private Date deliveryDate;
    /** nullable persistent field */
    private Date completeDate;
    /** nullable persistent field */
    private String deliveryMethod;
    /** nullable persistent field */
    private String beforeWorkTurn;
    /** nullable persistent field */
    private String afterWorkTurn;
    /** nullable persistent field */
    private String cancelled;
    /** nullable persistent field */
    private String beforeWorkTurnUnits;
    /** nullable persistent field */
    private String afterWorkTurnUnits;
    /** nullable persistent field */
    private String afterWorkTurnReason;
    /** nullable persistent field */
    private String deliverableSame;
    /** nullable persistent field */
    private String clientPO;
    /** nullable persistent field */
    private String clientAuthorization;
    /** nullable persistent field */
    private String component;
    /** nullable persistent field */
    private String fee;
    /** nullable persistent field */
    private Date invoiceDate;
    /** nullable persistent field */
    private Date invoicePaid;
    /** nullable persistent field */
    private String totalAmountInvoiced;
    /** nullable persistent field */
    private String notes;
    /** nullable persistent field */
    private String linRequirements;
    /** nullable persistent field */
    private String dtpRequirements;
    /** nullable persistent field */
    private String engRequirements;
    /** nullable persistent field */
    private String othRequirements;
    /** nullable persistent field */
    private String sourceOS;
    /** nullable persistent field */
    private String sourceApplication;
    /** nullable persistent field */
    private String sourceVersion;
    /** nullable persistent field */
    private String sourceTechNotes;
    /** nullable persistent field */
    private String deliverableOS;
    /** nullable persistent field */
    private String deliverableApplication;
    /** nullable persistent field */
    private String deliverableVersion;
    /** nullable persistent field */
    private String deliverableTechNotes;
    /** nullable persistent field */
    private String pm;
    /** nullable persistent field */
    private String ae;
    /** nullable persistent field */
    private String subDollarTotal;
    /** nullable persistent field */
    private String subPmDollarTotal;
    /** nullable persistent field */
    private String pmPercent;
    /** nullable persistent field */
    private String pmPercentDollarTotal;
    /** nullable persistent field */
    private String subDiscountDollarTotal;
    /** nullable persistent field */
    private String discountPercent;
    /** nullable persistent field */
    private String discountDollarTotal;
    /** nullable persistent field */
    private String rushPercent;
    /** nullable persistent field */
    private String rushPercentDollarTotal;
    /** nullable persistent field */
    private String legacyCost;
    /** nullable persistent field */
    private Double projectAmount;
    /** nullable persistent field */
    private Client Company;
    /** nullable persistent field */
    private ClientContact Contact;
    private ClientContact CareTaker;
    /** persistent field */
    private Set ClientInvoices;
    /** persistent field */
    private Set Inspections;
    /** persistent field */
    private Set Quotes;
    private Set Client_Quote;
    /** persistent field */
    private Set SourceDocs;
    /** persistent field */
    private Set Qualities;
    /** persistent field */
    private Set Change1s;
    /** nullable persistent field */
    private String isTracked;
    /** nullable persistent field */
    private String typeOfText;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Integer pm_id;
    private String archiveId;
    private String original_project_number;
    private Integer original_project_id;
    private String allInOne;
    private String orderConfirmation;
    private String deliveryConfirmation;
    private String translationApprovalConfirmation;
    private String translationApprovalConfirmationWord;
    private String orderConfirmationMail;
    private String deliveryConfirmationMail;
    private String translationApprovalConfirmationMulti;
    private String translationApprovalConfirmationWordMulti;
    private String other;
    private String otherText;
    private String otherPercent;
    private String otherDollarTotal;
    private Double euroToUsdExchangeRate = new Double(0);
    private boolean independent;
    private boolean confirmationRecieved;
    private Double clientSatisfaction;
    private Integer srcLangCnt;
    private Integer targetLangCnt;
    
    private String orderReqNum;
    private String srcLang;
    private String targetLang;
    private String task;
    private Date reqProjDelDate;
    private boolean postProjectReview;

    /** full constructor */
    public Project(String number, String status, String product, String productDescription, String productUnits, String projectRequirements, String projectDescription, Date startDate, Date dueDate, Date deliveryDate, Date completeDate, String deliveryMethod, String beforeWorkTurn, String afterWorkTurn, String cancelled, String beforeWorkTurnUnits, String afterWorkTurnUnits, String afterWorkTurnReason, String deliverableSame, String clientPO, String fee, Date invoiceDate, Date invoicePaid, String totalAmountInvoiced, String notes, String linRequirements, String dtpRequirements, String engRequirements, String othRequirements, String sourceOS, String sourceApplication, String sourceVersion, String sourceTechNotes, String deliverableOS, String deliverableApplication, String deliverableVersion, String deliverableTechNotes, String pm, String subDollarTotal, String subPmDollarTotal, String pmPercent, String pmPercentDollarTotal, String rushPercent, String rushPercentDollarTotal, String legacyCost, Double projectAmount, Client Company, ClientContact Contact, Set ClientInvoices, Set Inspections, Set Quotes, Set SourceDocs, Set Qualities, Set Change1s) {
        this.setNumber(number);
        this.setStatus(status);
        this.setProduct(product);
        this.setProductDescription(productDescription);
        this.setProductUnits(productUnits);
        this.setProjectRequirements(projectRequirements);
        this.setProjectDescription(projectDescription);
        this.setStartDate(startDate);
        this.setDueDate(dueDate);
        this.setDeliveryDate(deliveryDate);
        this.setCompleteDate(completeDate);
        this.setDeliveryMethod(deliveryMethod);
        this.setBeforeWorkTurn(beforeWorkTurn);
        this.setAfterWorkTurn(afterWorkTurn);
        this.setCancelled(cancelled);
        this.setBeforeWorkTurnUnits(beforeWorkTurnUnits);
        this.setAfterWorkTurnUnits(afterWorkTurnUnits);
        this.setAfterWorkTurnReason(afterWorkTurnReason);
        this.setDeliverableSame(deliverableSame);
        this.setClientPO(clientPO);
        this.setFee(fee);
        this.setInvoiceDate(invoiceDate);
        this.setInvoicePaid(invoicePaid);
        this.setTotalAmountInvoiced(totalAmountInvoiced);
        this.setNotes(notes);
        this.setLinRequirements(linRequirements);
        this.setDtpRequirements(dtpRequirements);
        this.setEngRequirements(engRequirements);
        this.setOthRequirements(othRequirements);
        this.setSourceOS(sourceOS);
        this.setSourceApplication(sourceApplication);
        this.setSourceVersion(sourceVersion);
        this.setSourceTechNotes(sourceTechNotes);
        this.setDeliverableOS(deliverableOS);
        this.setDeliverableApplication(deliverableApplication);
        this.setDeliverableVersion(deliverableVersion);
        this.setDeliverableTechNotes(deliverableTechNotes);
        this.setPm(pm);
        this.setSubDollarTotal(subDollarTotal);
        this.setSubPmDollarTotal(subPmDollarTotal);
        this.setPmPercent(pmPercent);
        this.setPmPercentDollarTotal(pmPercentDollarTotal);
        this.setRushPercent(rushPercent);
        this.setRushPercentDollarTotal(rushPercentDollarTotal);
        this.setLegacyCost(legacyCost);
        this.setProjectAmount(projectAmount);
        this.setCompany(Company);
        this.setContact(Contact);
        this.setClientInvoices(ClientInvoices);
        this.setInspections(Inspections);
        this.setQuotes(Quotes);
        this.setSourceDocs(SourceDocs);
        this.setQualities(Qualities);
        this.setChange1s(Change1s);
    }

    /** default constructor */
    public Project() {
    }

    /** minimal constructor */
    public Project(Set ClientInvoices, Set Inspections, Set Quotes, Set SourceDocs, Set Qualities, Set Change1s) {
        this.setClientInvoices(ClientInvoices);
        this.setInspections(Inspections);
        this.setQuotes(Quotes);
        this.setSourceDocs(SourceDocs);
        this.setQualities(Qualities);
        this.setChange1s(Change1s);
    }

    public Integer getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProduct() {
        return this.product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProductDescription() {
        return this.productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductUnits() {
        return this.productUnits;
    }

    public void setProductUnits(String productUnits) {
        this.productUnits = productUnits;
    }

    public String getProjectRequirements() {
        return this.projectRequirements;
    }

    public void setProjectRequirements(String projectRequirements) {
        this.projectRequirements = projectRequirements;
    }

    public String getProjectDescription() {
        return this.projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDeliveryDate() {
        return this.deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getCompleteDate() {
        return this.completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public String getDeliveryMethod() {
        return this.deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getBeforeWorkTurn() {
        return this.beforeWorkTurn;
    }

    public void setBeforeWorkTurn(String beforeWorkTurn) {
        this.beforeWorkTurn = beforeWorkTurn;
    }

    public String getAfterWorkTurn() {
        return this.afterWorkTurn;
    }

    public void setAfterWorkTurn(String afterWorkTurn) {
        this.afterWorkTurn = afterWorkTurn;
    }

    public String getCancelled() {
        return this.cancelled;
    }

    public void setCancelled(String cancelled) {
        this.cancelled = cancelled;
    }

    public String getBeforeWorkTurnUnits() {
        return this.beforeWorkTurnUnits;
    }

    public void setBeforeWorkTurnUnits(String beforeWorkTurnUnits) {
        this.beforeWorkTurnUnits = beforeWorkTurnUnits;
    }

    public String getAfterWorkTurnUnits() {
        return this.afterWorkTurnUnits;
    }

    public void setAfterWorkTurnUnits(String afterWorkTurnUnits) {
        this.afterWorkTurnUnits = afterWorkTurnUnits;
    }

    public String getAfterWorkTurnReason() {
        return this.afterWorkTurnReason;
    }

    public void setAfterWorkTurnReason(String afterWorkTurnReason) {
        this.afterWorkTurnReason = afterWorkTurnReason;
    }

    public String getDeliverableSame() {
        return this.deliverableSame;
    }

    public void setDeliverableSame(String deliverableSame) {
        this.deliverableSame = deliverableSame;
    }

    public String getClientPO() {
        return this.clientPO;
    }

    public void setClientPO(String clientPO) {
        this.clientPO = clientPO;
    }

    public String getFee() {
        return this.fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public Date getInvoiceDate() {
        return this.invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getInvoicePaid() {
        return this.invoicePaid;
    }

    public void setInvoicePaid(Date invoicePaid) {
        this.invoicePaid = invoicePaid;
    }

    public String getTotalAmountInvoiced() {
        return this.totalAmountInvoiced;
    }

    public void setTotalAmountInvoiced(String totalAmountInvoiced) {
        this.totalAmountInvoiced = totalAmountInvoiced;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
      this.notes = StandardCode.getInstance().convertTextToUTF(notes);
     
    }

    public String getLinRequirements() {
        return this.linRequirements;
    }

    public void setLinRequirements(String linRequirements) {
        this.linRequirements = linRequirements;
    }

    public String getDtpRequirements() {
        return this.dtpRequirements;
    }

    public void setDtpRequirements(String dtpRequirements) {
        this.dtpRequirements = dtpRequirements;
    }

    public String getEngRequirements() {
        return this.engRequirements;
    }

    public void setEngRequirements(String engRequirements) {
        this.engRequirements = engRequirements;
    }

    public String getOthRequirements() {
        return this.othRequirements;
    }

    public void setOthRequirements(String othRequirements) {
        this.othRequirements = othRequirements;
    }

    public String getSourceOS() {
        return this.sourceOS;
    }

    public void setSourceOS(String sourceOS) {
        this.sourceOS = sourceOS;
    }

    public String getSourceApplication() {
        return this.sourceApplication;
    }

    public void setSourceApplication(String sourceApplication) {
        this.sourceApplication = sourceApplication;
    }

    public String getSourceVersion() {
        return this.sourceVersion;
    }

    public void setSourceVersion(String sourceVersion) {
        this.sourceVersion = sourceVersion;
    }

    public String getSourceTechNotes() {
        return this.sourceTechNotes;
    }

    public void setSourceTechNotes(String sourceTechNotes) {
        this.sourceTechNotes = sourceTechNotes;
    }

    public String getDeliverableOS() {
        return this.deliverableOS;
    }

    public void setDeliverableOS(String deliverableOS) {
        this.deliverableOS = deliverableOS;
    }

    public String getDeliverableApplication() {
        return this.deliverableApplication;
    }

    public void setDeliverableApplication(String deliverableApplication) {
        this.deliverableApplication = deliverableApplication;
    }

    public String getDeliverableVersion() {
        return this.deliverableVersion;
    }

    public void setDeliverableVersion(String deliverableVersion) {
        this.deliverableVersion = deliverableVersion;
    }

    public String getDeliverableTechNotes() {
        return this.deliverableTechNotes;
    }

    public void setDeliverableTechNotes(String deliverableTechNotes) {
        this.deliverableTechNotes = deliverableTechNotes;
    }

    public String getPm() {
        return this.pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getSubDollarTotal() {
        return this.subDollarTotal;
    }

    public void setSubDollarTotal(String subDollarTotal) {
        this.subDollarTotal = subDollarTotal;
    }

    public String getSubPmDollarTotal() {
        return this.subPmDollarTotal;
    }

    public void setSubPmDollarTotal(String subPmDollarTotal) {
        this.subPmDollarTotal = subPmDollarTotal;
    }

//    public String getPmPercent(String change) {
//        if(change.isEmpty())
//            return this.pmPercent;
//        else
//            return "100";
//    }
    
     public String getPmPercent() {
            return this.pmPercent;
    }

    public void setPmPercent(String pmPercent) {

        this.pmPercent = pmPercent;
    }

    public String getPmPercentDollarTotal() {
        return this.pmPercentDollarTotal;
    }

    public void setPmPercentDollarTotal(String pmPercentDollarTotal) {
        this.pmPercentDollarTotal = pmPercentDollarTotal;
    }

    public String getRushPercent() {
        return this.rushPercent;
    }

    public void setRushPercent(String rushPercent) {
        this.rushPercent = rushPercent;
    }

    public String getRushPercentDollarTotal() {
        return this.rushPercentDollarTotal;
    }

    public void setRushPercentDollarTotal(String rushPercentDollarTotal) {
        this.rushPercentDollarTotal = rushPercentDollarTotal;
    }

    public String getLegacyCost() {
        return this.legacyCost;
    }

    public void setLegacyCost(String legacyCost) {
        this.legacyCost = legacyCost;
    }

    public Double getProjectAmount() {
        return this.projectAmount;
    }

    public void setProjectAmount(Double projectAmount) {
        this.projectAmount = projectAmount;
    }

    public Client getCompany() {
        return this.Company;
    }

    public void setCompany(Client Company) {
        this.Company = Company;
    }

    public ClientContact getContact() {
        return this.Contact;
    }

    public void setContact(ClientContact Contact) {
        this.Contact = Contact;
    }

    public ClientContact getCareTaker() {
        return CareTaker;
    }

    public void setCareTaker(ClientContact CareTaker) {
        this.CareTaker = CareTaker;
    }
    

    public Set getClientInvoices() {
        return this.ClientInvoices;
    }

    public void setClientInvoices(Set ClientInvoices) {
        this.ClientInvoices = ClientInvoices;
    }

    public Set getInspections() {
        return this.Inspections;
    }

    public void setInspections(Set Inspections) {
        this.Inspections = Inspections;
    }

    public Set getQuotes() {
        return this.Quotes;
    }

    public void setQuotes(Set Quotes) {
        this.Quotes = Quotes;
    }

    public Set getClient_Quote() {
        return Client_Quote;
    }

    public void setClient_Quote(Set Client_Quote) {
        this.Client_Quote = Client_Quote;
    }

    public Set getSourceDocs() {
        return this.SourceDocs;
    }

    public void setSourceDocs(Set SourceDocs) {
        this.SourceDocs = SourceDocs;
    }

    public Set getQualities() {
        return this.Qualities;
    }

    public void setQualities(Set Qualities) {
        this.Qualities = Qualities;
    }

    public Set getChange1s() {
        return this.Change1s;
    }

    public void setChange1s(Set Change1s) {
        this.Change1s = Change1s;
    }

    public String toString() {
        return new ToStringBuilder(this).append("projectId", getProjectId()).toString();
    }

    public String getIsTracked() {
        return isTracked;
    }

    public void setIsTracked(String isTracked) {
        this.isTracked = isTracked;
    }

    public String getAe() {
        return ae;
    }

    public void setAe(String ae) {
        this.ae = ae;
    }

    public String getOriginal_project_number() {
        return original_project_number;
    }

    public void setOriginal_project_number(String original_project_number) {
        this.original_project_number = original_project_number;
    }

    public Integer getOriginal_project_id() {
        return original_project_id;
    }

    public void setOriginal_project_id(Integer original_project_id) {
        this.original_project_id = original_project_id;
    }

    public String getClientAuthorization() {
        return clientAuthorization;
    }

    public void setClientAuthorization(String clientAuthorization) {
        this.clientAuthorization = clientAuthorization;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getAllInOne() {
        return allInOne;
    }

    public void setAllInOne(String allInOne) {
        this.allInOne = allInOne;
    }

    public String getOrderConfirmation() {
        return orderConfirmation;
    }

    public void setOrderConfirmation(String orderConfirmation) {
        this.orderConfirmation = orderConfirmation;
    }

    public String getDeliveryConfirmation() {
        return deliveryConfirmation;
    }

    public void setDeliveryConfirmation(String deliveryConfirmation) {
        this.deliveryConfirmation = deliveryConfirmation;
    }

    public String getTranslationApprovalConfirmation() {
        return translationApprovalConfirmation;
    }

    public void setTranslationApprovalConfirmation(String translationApprovalConfirmation) {
        this.translationApprovalConfirmation = translationApprovalConfirmation;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getSubDiscountDollarTotal() {
        return subDiscountDollarTotal;
    }

    public void setSubDiscountDollarTotal(String subDiscountDollarTotal) {
        this.subDiscountDollarTotal = subDiscountDollarTotal;
    }

    public String getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getDiscountDollarTotal() {
        return discountDollarTotal;
    }

    public void setDiscountDollarTotal(String discountDollarTotal) {
        this.discountDollarTotal = discountDollarTotal;
    }

    public Double getEuroToUsdExchangeRate() {
        return euroToUsdExchangeRate;
    }

    public void setEuroToUsdExchangeRate(Double euroToUsdExchangeRate) {
        this.euroToUsdExchangeRate = euroToUsdExchangeRate;
    }

    public boolean isIndependent() {
        return independent;
    }

    public void setIndependent(boolean independent) {
        this.independent = independent;
    }

    public Integer getPm_id() {
        return pm_id;
    }

    public void setPm_id(Integer pm_id) {
        this.pm_id = pm_id;
    }

    public String getArchiveId() {
        return archiveId;
    }

    public void setArchiveId(String archiveId) {
        this.archiveId = archiveId;
    }

    public boolean isConfirmationRecieved() {
        return confirmationRecieved;
    }

    public void setConfirmationRecieved(boolean confirmationRecieved) {
        this.confirmationRecieved = confirmationRecieved;
    }

    public String getOtherDollarTotal() {
        return otherDollarTotal;
    }

    public void setOtherDollarTotal(String otherDollarTotal) {
        this.otherDollarTotal = otherDollarTotal;
    }

    public String getOtherPercent() {
        return otherPercent;
    }

    public void setOtherPercent(String otherPercent) {
        this.otherPercent = otherPercent;
    }

    public String getOtherText() {
        return otherText;
    }

    public void setOtherText(String otherText) {
        this.otherText = otherText;
    }

    public String getTypeOfText() {
        return typeOfText;
    }

    public void setTypeOfText(String typeOfText) {
        this.typeOfText = typeOfText;
    }

    public Double getClientSatisfaction() {
        return clientSatisfaction;
    }

    public void setClientSatisfaction(Double clientSatisfaction) {
        this.clientSatisfaction = clientSatisfaction;
    }

    public String getOrderConfirmationMail() {
        return orderConfirmationMail;
    }

    public void setOrderConfirmationMail(String orderConfirmationMail) {
        this.orderConfirmationMail = orderConfirmationMail;
    }

    public String getDeliveryConfirmationMail() {
        return deliveryConfirmationMail;
    }

    public void setDeliveryConfirmationMail(String deliveryConfirmationMail) {
        this.deliveryConfirmationMail = deliveryConfirmationMail;
    }

    public String getTranslationApprovalConfirmationMulti() {
        return translationApprovalConfirmationMulti;
    }

    public void setTranslationApprovalConfirmationMulti(String translationApprovalConfirmationMulti) {
        this.translationApprovalConfirmationMulti = translationApprovalConfirmationMulti;
    }

    public Integer getSrcLangCnt() {
        return srcLangCnt;
    }

    public void setSrcLangCnt(Integer srcLangCnt) {
        this.srcLangCnt = srcLangCnt;
    }

    public Integer getTargetLangCnt() {
        return targetLangCnt;
    }

    public void setTargetLangCnt(Integer targetLangCnt) {
        this.targetLangCnt = targetLangCnt;
    }

    public String getSrcLang() {
        return srcLang;
    }

    public void setSrcLang(String srcLang) {
        this.srcLang = srcLang;
    }

    public String getTargetLang() {
        return targetLang;
    }

    public void setTargetLang(String targetLang) {
        this.targetLang = targetLang;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTranslationApprovalConfirmationWord() {
        return translationApprovalConfirmationWord;
    }

    public void setTranslationApprovalConfirmationWord(String translationApprovalConfirmationWord) {
        this.translationApprovalConfirmationWord = translationApprovalConfirmationWord;
    }

    public String getTranslationApprovalConfirmationWordMulti() {
        return translationApprovalConfirmationWordMulti;
    }

    public void setTranslationApprovalConfirmationWordMulti(String translationApprovalConfirmationWordMulti) {
        this.translationApprovalConfirmationWordMulti = translationApprovalConfirmationWordMulti;
    }

    public Date getReqProjDelDate() {
        return reqProjDelDate;
    }

    public void setReqProjDelDate(Date reqProjDelDate) {
        this.reqProjDelDate = reqProjDelDate;
    }

    public boolean isPostProjectReview() {
        return postProjectReview;
    }

    public void setPostProjectReview(boolean postProjectReview) {
        this.postProjectReview = postProjectReview;
    }

    public String getOrderReqNum() {
        return orderReqNum;
    }

    public void setOrderReqNum(String orderReqNum) {
        this.orderReqNum = orderReqNum;
    }


  
}
