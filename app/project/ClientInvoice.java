package app.project;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ClientInvoice implements Serializable {

    /** identifier field */
    private Integer clientInvoiceId;

    /** nullable persistent field */
    private String number;

    /** nullable persistent field */
    private Date invoiceRequestDate;

    /** nullable persistent field */
    private String amount;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private Date invoicePaidDate;

    /** nullable persistent field */
    private app.project.Project Project;
    
    /** nullable persistent field */
    private String invoicePeriod;


    /** full constructor */
    public ClientInvoice(String number, Date invoiceRequestDate, String amount, String description, Date invoicePaidDate, app.project.Project Project) {
        this.number = number;
        this.invoiceRequestDate = invoiceRequestDate;
        this.amount = amount;
        this.description = description;
        this.invoicePaidDate = invoicePaidDate;
        this.Project = Project;
    }

    /** default constructor */
    public ClientInvoice() {
    }

    public Integer getClientInvoiceId() {
        return this.clientInvoiceId;
    }

    public void setClientInvoiceId(Integer clientInvoiceId) {
        this.clientInvoiceId = clientInvoiceId;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getInvoiceRequestDate() {
        return this.invoiceRequestDate;
    }

    public void setInvoiceRequestDate(Date invoiceRequestDate) {
        this.invoiceRequestDate = invoiceRequestDate;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getInvoicePaidDate() {
        return this.invoicePaidDate;
    }

    public void setInvoicePaidDate(Date invoicePaidDate) {
        this.invoicePaidDate = invoicePaidDate;
    }

    public app.project.Project getProject() {
        return this.Project;
    }

    public void setProject(app.project.Project Project) {
        this.Project = Project;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("clientInvoiceId", getClientInvoiceId())
            .toString();
    }

    public String getInvoicePeriod() {
        return invoicePeriod;
    }

    public void setInvoicePeriod(String invoicePeriod) {
        this.invoicePeriod = invoicePeriod;
    }
    
    

}
