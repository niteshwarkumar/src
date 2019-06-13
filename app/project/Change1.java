package app.project;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Hibernate CodeGenerator
 */
public class Change1 implements Serializable {

    /**
     * identifier field
     */
    private Integer change1Id;

    /**
     * nullable persistent field
     */
    private String number;

    /**
     * nullable persistent field
     */
    private String description;

    /**
     * nullable persistent field
     */
    private Date changeDate;

    /**
     * nullable persistent field
     */
    private String dollarTotal;

    /**
     * nullable persistent field
     */
    private boolean approved;

    private String name;
    private boolean finalVerification;
    private Date finalVerificationDate;
    private String finalVerificationBy;
    private boolean engVerification;
    private Date engVerificationDate;
    private String engVerificationBy;
    private boolean dtpVerification;
    private Date dtpVerificationDate;
    private String dtpVerificationBy;

    private String discountPercent;
    private String discountDollarTotal;
    private String pmPercent;
    private String pmPercentDollarTotal;
    private String rushPercent;
    private String rushPercentDollarTotal;
    private String locationFiles;
    private boolean clientApproval;
    private Date clientApprovalDate;
    private String clientApprovalSrc;
    private String clientApprovalDesc;

    /**
     * nullable persistent field
     */
    private app.project.Project Project;

    /**
     * full constructor
     */
    public Change1(String number, String description, Date changeDate, String dollarTotal, boolean approved, app.project.Project Project) {
        this.number = number;
        this.description = description;
        this.changeDate = changeDate;
        this.dollarTotal = dollarTotal;
        this.approved = approved;
        this.Project = Project;
    }

    public Change1(Integer change1Id, String number, String description, Date changeDate, String dollarTotal, boolean approved, String name, boolean finalVerification, Date finalVerificationDate, String finalVerificationBy, boolean engVerification, Date engVerificationDate, String engVerificationBy, boolean dtpVerification, Date dtpVerificationDate, String dtpVerificationBy, Project Project) {
        this.change1Id = change1Id;
        this.number = number;
        this.description = description;
        this.changeDate = changeDate;
        this.dollarTotal = dollarTotal;
        this.approved = approved;
        this.name = name;
        this.finalVerification = finalVerification;
        this.finalVerificationDate = finalVerificationDate;
        this.finalVerificationBy = finalVerificationBy;
        this.engVerification = engVerification;
        this.engVerificationDate = engVerificationDate;
        this.engVerificationBy = engVerificationBy;
        this.dtpVerification = dtpVerification;
        this.dtpVerificationDate = dtpVerificationDate;
        this.dtpVerificationBy = dtpVerificationBy;
        this.Project = Project;
    }

    /**
     * default constructor
     */
    public Change1() {
    }

    public Integer getChange1Id() {
        return this.change1Id;
    }

    public void setChange1Id(Integer change1Id) {
        this.change1Id = change1Id;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getChangeDate() {
        return this.changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public String getDollarTotal() {
        return this.dollarTotal;
    }

    public void setDollarTotal(String dollarTotal) {
        this.dollarTotal = dollarTotal;
    }

    public boolean isApproved() {
        return this.approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFinalVerification() {
        return finalVerification;
    }

    public void setFinalVerification(boolean finalVerification) {
        this.finalVerification = finalVerification;
    }

    public Date getFinalVerificationDate() {
        return finalVerificationDate;
    }

    public void setFinalVerificationDate(Date finalVerificationDate) {
        this.finalVerificationDate = finalVerificationDate;
    }

    public String getFinalVerificationBy() {
        return finalVerificationBy;
    }

    public void setFinalVerificationBy(String finalVerificationBy) {
        this.finalVerificationBy = finalVerificationBy;
    }

    public boolean isEngVerification() {
        return engVerification;
    }

    public void setEngVerification(boolean engVerification) {
        this.engVerification = engVerification;
    }

    public Date getEngVerificationDate() {
        return engVerificationDate;
    }

    public void setEngVerificationDate(Date engVerificationDate) {
        this.engVerificationDate = engVerificationDate;
    }

    public String getEngVerificationBy() {
        return engVerificationBy;
    }

    public void setEngVerificationBy(String engVerificationBy) {
        this.engVerificationBy = engVerificationBy;
    }

    public boolean isDtpVerification() {
        return dtpVerification;
    }

    public void setDtpVerification(boolean dtpVerification) {
        this.dtpVerification = dtpVerification;
    }

    public Date getDtpVerificationDate() {
        return dtpVerificationDate;
    }

    public void setDtpVerificationDate(Date dtpVerificationDate) {
        this.dtpVerificationDate = dtpVerificationDate;
    }

    public String getDtpVerificationBy() {
        return dtpVerificationBy;
    }

    public void setDtpVerificationBy(String dtpVerificationBy) {
        this.dtpVerificationBy = dtpVerificationBy;
    }

    public app.project.Project getProject() {
        return this.Project;
    }

    public void setProject(app.project.Project Project) {
        this.Project = Project;
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

    public String getPmPercent() {
        return pmPercent;
    }

    public void setPmPercent(String pmPercent) {
        this.pmPercent = pmPercent;
    }

    public String getPmPercentDollarTotal() {
        return pmPercentDollarTotal;
    }

    public void setPmPercentDollarTotal(String pmPercentDollarTotal) {
        this.pmPercentDollarTotal = pmPercentDollarTotal;
    }

    public String getRushPercent() {
        return rushPercent;
    }

    public void setRushPercent(String rushPercent) {
        this.rushPercent = rushPercent;
    }

    public String getRushPercentDollarTotal() {
        return rushPercentDollarTotal;
    }

    public void setRushPercentDollarTotal(String rushPercentDollarTotal) {
        this.rushPercentDollarTotal = rushPercentDollarTotal;
    }

    public String getLocationFiles() {
        return locationFiles;
    }

    public void setLocationFiles(String locationFiles) {
        this.locationFiles = locationFiles;
    }

    public boolean isClientApproval() {
        return clientApproval;
    }

    public void setClientApproval(boolean clientApproval) {
        this.clientApproval = clientApproval;
    }

    public Date getClientApprovalDate() {
        return clientApprovalDate;
    }

    public void setClientApprovalDate(Date clientApprovalDate) {
        this.clientApprovalDate = clientApprovalDate;
    }

    public String getClientApprovalSrc() {
        return clientApprovalSrc;
    }

    public void setClientApprovalSrc(String clientApprovalSrc) {
        this.clientApprovalSrc = clientApprovalSrc;
    }

    public String getClientApprovalDesc() {
        return clientApprovalDesc;
    }

    public void setClientApprovalDesc(String clientApprovalDesc) {
        this.clientApprovalDesc = clientApprovalDesc;
    }
    
    

    public String toString() {
        return new ToStringBuilder(this)
                .append("change1Id", getChange1Id())
                .toString();
    }

}
