package app.quote;

import app.project.Project;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Quote1 implements Serializable {

    /** nullable persistent field */
    private String rejectReason;
    /** nullable persistent field */
    private String enteredById;
    /** nullable persistent field */
    private String isTracked;
    /** nullable persistent field */
    private Date enteredByTS;
    /** nullable persistent field */
    private String lastModifiedById;
    /** nullable persistent field */
    private Date lastModifiedByTS;
    /** identifier field */
    private Integer quote1Id;
    /** nullable persistent field */
    private String number;
    /** nullable persistent field */
    private Date approvalDate;
    /** nullable persistent field */
    private Date quoteDate;
    /** nullable persistent field */
    private String subQuoteId;
    /** nullable persistent field */
    private String status;
    /** nullable persistent field */
    private String note;
    /** nullable persistent field */
    private String subDollarTotal;
    /** nullable persistent field */
    private String subPmDollarTotal;
    /** nullable persistent field */
    private String pmPercent;
    /** nullable persistent field */
    private String pmPercentDollarTotal;
    /** nullable persistent field */
    private String rushPercent;
    /** nullable persistent field */
    private String rushPercentDollarTotal;
    /** nullable persistent field */
    private Double quoteDollarTotal;
    /** nullable persistent field */
    private Project Project;
    /** persistent field */
    private Set SourceDocs;
    /** persistent field */
    private Set Files;
    /** persistent field */
    private java.sql.Timestamp approvedTS;
    private String subquotes;
    /** nullable persistent field */
    private String subDiscountDollarTotal;
    /** nullable persistent field */
    private String discountPercent;
    /** nullable persistent field */
    private String discountDollarTotal;
    private Boolean publish;
    private String approvalTimeEsimate;
    private String archiveId;
    private String clientRejectReason;
    private Date publishDate;
    private String publishBy;
    private String clientFileNote;
    private String manualPMFee;

    /** full constructor */
    public Quote1(String number, Date approvalDate, Date quoteDate, String subQuoteId, String status, String note, String subDollarTotal, String subPmDollarTotal, String pmPercent, String pmPercentDollarTotal, String rushPercent, String rushPercentDollarTotal, Double quoteDollarTotal, Project Project, Set SourceDocs, Set Files, Timestamp approvedTS, String subquotes, String subDiscountDollarTotal, String discountPercent, String discountDollarTotal, Boolean publish, String approvalTimeEsimate, String archiveId, Date publishDate, String publishBy, String clientFileNote, String manualPMFee) {
        this.number = number;
        this.approvalDate = approvalDate;
        this.quoteDate = quoteDate;
        this.subQuoteId = subQuoteId;
        this.status = status;
        this.note = note;
        this.subDollarTotal = subDollarTotal;
        this.subPmDollarTotal = subPmDollarTotal;
        this.pmPercent = pmPercent;
        this.pmPercentDollarTotal = pmPercentDollarTotal;
        this.rushPercent = rushPercent;
        this.rushPercentDollarTotal = rushPercentDollarTotal;
        this.quoteDollarTotal = quoteDollarTotal;
        this.Project = Project;
        this.SourceDocs = SourceDocs;
        this.Files = Files;
        this.approvedTS = approvedTS;
        this.subquotes = subquotes;
        this.subDiscountDollarTotal = subDiscountDollarTotal;
        this.discountPercent = discountPercent;
        this.discountDollarTotal = discountDollarTotal;
        this.publish = publish;
        this.approvalTimeEsimate = approvalTimeEsimate;
        this.archiveId = archiveId;
        this.publishDate = publishDate;
        this.publishBy = publishBy;
        this.clientFileNote = clientFileNote;
        this.manualPMFee = manualPMFee;

    }


    /** default constructor */
    public Quote1() {
    }

    /** minimal constructor */
    public Quote1(Set SourceDocs, Set Files) {
        this.SourceDocs = SourceDocs;
        this.Files = Files;
    }

    public Integer getQuote1Id() {
        return this.quote1Id;
    }

    public void setQuote1Id(Integer quote1Id) {
        this.quote1Id = quote1Id;
    }

    public String getNumber() {
        String companyCode = "";
        try {
            companyCode = this.getProject().getCompany().getCompany_code();
        } catch (Exception e) {
            //don't do anything
        }
        return this.number + companyCode;
    }

    public void setNumber(String number) {
        if (number != null) {
            if (number.length() > 7) {
                number = number.substring(0, 7);
            }
        }
        this.number = number;
    }

    public String getProductNumber() {
        String productCode = "";
        try {
            productCode = this.getProject().getProduct();
            System.out.println("product cose>>>>>>>>>>>>>" + productCode);
            //getCompany().getCompany_code();
        } catch (Exception e) {
            //don't do anything
        }
        return this.number + productCode;
    }

    public void setProductNumber(String productNumber) {
        if (productNumber != null) {
            if (productNumber.length() > 7) {
                productNumber = productNumber.substring(0, 7);
            }
        }
        this.number = productNumber;
    }

    public Date getApprovalDate() {
        return this.approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Date getQuoteDate() {
        return this.quoteDate;
    }

    public void setQuoteDate(Date quoteDate) {
        this.quoteDate = quoteDate;
    }

    public String getSubQuoteId() {
        return this.subQuoteId;
    }

    public void setSubQuoteId(String subQuoteId) {
        this.subQuoteId = subQuoteId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public Double getQuoteDollarTotal() {
        return this.quoteDollarTotal;
    }

    public void setQuoteDollarTotal(Double quoteDollarTotal) {
        this.quoteDollarTotal = quoteDollarTotal;
    }

    public Project getProject() {
        return this.Project;
    }

    public void setProject(Project Project) {
        this.Project = Project;
    }

    public Set getSourceDocs() {
        return this.SourceDocs;
    }

    public void setSourceDocs(Set SourceDocs) {
        this.SourceDocs = SourceDocs;
    }

    public Set getFiles() {
        return this.Files;
    }

    public void setFiles(Set Files) {
        this.Files = Files;
    }

    public String toString() {
        return new ToStringBuilder(this).append("quote1Id", getQuote1Id()).toString();
    }

    public String getEnteredById() {
        return enteredById;
    }

    public void setEnteredById(String enteredById) {
        this.enteredById = enteredById;
    }

    public Date getEnteredByTS() {
        return enteredByTS;
    }

    public void setEnteredByTS(Date enteredByTS) {
        this.enteredByTS = enteredByTS;
    }

    public String getLastModifiedById() {
        return lastModifiedById;
    }

    public void setLastModifiedById(String lastModifiedById) {
        this.lastModifiedById = lastModifiedById;
    }

    public Date getLastModifiedByTS() {
        return lastModifiedByTS;
    }

    public void setLastModifiedByTS(Date lastModifiedByTS) {
        this.lastModifiedByTS = lastModifiedByTS;
    }

    public java.sql.Timestamp getApprovedTS() {
        return new java.sql.Timestamp(System.currentTimeMillis());
    }

    public void setApprovedTS(java.sql.Timestamp approvedTS) {
        this.approvedTS = approvedTS;
    }

    public String getApprovalTimeEsimate() {
        return approvalTimeEsimate;
    }

    public void setApprovalTimeEsimate(String approvalTimeEsimate) {
        this.approvalTimeEsimate = approvalTimeEsimate;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getIsTracked() {
        return isTracked;
    }

    public void setIsTracked(String isTracked) {
        this.isTracked = isTracked;
    }

    public String getSubquotes() {
        return subquotes;
    }

    public void setSubquotes(String subquotes) {
        this.subquotes = subquotes;
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

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
    }

    public String getArchiveId() {
        return archiveId;
    }

    public void setArchiveId(String archiveId) {
        this.archiveId = archiveId;
    }

    public String getClientRejectReason() {
        return clientRejectReason;
    }

    public void setClientRejectReason(String clientRejectReason) {
        this.clientRejectReason = clientRejectReason;
    }

    public String getClientFileNote() {
        return clientFileNote;
    }

    public void setClientFileNote(String clientFileNote) {
        this.clientFileNote = clientFileNote;
    }

    public String getPublishBy() {
        return publishBy;
    }

    public void setPublishBy(String publishBy) {
        this.publishBy = publishBy;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getManualPMFee() {
        return manualPMFee;
    }

    public void setManualPMFee(String manualPMFee) {
        this.manualPMFee = manualPMFee;
    }

  

}
