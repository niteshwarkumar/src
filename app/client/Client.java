package app.client;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Client implements Serializable {

    
    private int delinquent;
    /** identifier field */
    private Integer clientId;

    /** nullable persistent field */
    private String Company_name;

    /** nullable persistent field */
    private String Company_code;

    /** nullable persistent field */
    private String Address_1;

    /** nullable persistent field */
    private String Address_2;

    private String business_desc;
    /** nullable persistent field */
    private String billing1;

    /** nullable persistent field */
    private String billing2;

    /** nullable persistent field */
    private String billing3;

    /** nullable persistent field */
    private String City;

    /** nullable persistent field */
    private String State_prov;

    /** nullable persistent field */
    private String Zip_postal_code;

    /** nullable persistent field */
    private String Country;

    /** nullable persistent field */
    private String Main_telephone_numb;

    /** nullable persistent field */
    private String workPhoneEx;

    /** nullable persistent field */
    private String Fax_number;

    /** nullable persistent field */
    private String url;

    /** nullable persistent field */
    private String Status;

    /** nullable persistent field */
    private String Email_address;

    /** nullable persistent field */
    private String Note;

    /** nullable persistent field */
    private String Project_mngr;

    /** nullable persistent field */
    private String Backup_pm;

    /** nullable persistent field */
    private String Sales_rep;

    /** nullable persistent field */
    private String Satisfaction_score;

    /** nullable persistent field */
    private String Satisfaction_level;

    /** nullable persistent field */
    private String Ftp_host_excel;

    /** nullable persistent field */
    private String Ftp_user_id_excel;

    /** nullable persistent field */
    private String Ftp_password_excel;

    /** nullable persistent field */
    private String Ftp_host_client;

    /** nullable persistent field */
    private String Ftp_user_id_client;

    /** nullable persistent field */
    private String Ftp_password_client;

    /** nullable persistent field */
    private String logo;

    /** nullable persistent field */
    private String scaleRep;

    /** nullable persistent field */
    private String scale100;

    /** nullable persistent field */
    private String scale95;

    /** nullable persistent field */
    private String scale85;

    /** nullable persistent field */
    private String scale75;

    /** nullable persistent field */
    private String scaleNew;
    
    /** nullable persistent field */
    private String tracker_password;


    /** nullable persistent field */
    private String scale8599;

    /** nullable persistent field */
    private String scaleNew4;

    /** nullable persistent field */
    private String scalePerfect;

    /** nullable persistent field */
    private String scaleContext;

    /** nullable persistent field */
    private Boolean scaleDefault;

    /** nullable persistent field */
    private app.client.Industry Industry;

    /** persistent field */
    private Set Contacts;

    /** persistent field */
    private Set Projects;

    /** persistent field */
    private Set ClientLanguagePairs;

    /** persistent field */
    private Set ClientOtherRates;
    
    /** nullable persistent field */
    private String certifications;
    
    private String main_engineer;
    private String main_dtp;
    private String other_engineer;
    private String other_dtp;
      
    private String createdBy;
    private String modifiedBy;
    private Date createdByDate;
    private Date modifiedByDate;
        
    private String  lin_css_other;
    private String  tech_css_other   ;

    private String ccurrency;
    private String tm_maintained;

    private String mainSource;
    private String mainTarget;
    private String otherContact1;
    private String otherContact2;
    private String otherContact3;
    private String otherContact4;
    private String otherContact5;
    private String terms;
    private String termsNames;
    private String auto_alert;
    private Double pmfeePercentage;

    public String getCcurrency() {
        return ccurrency;
    }

    public void setCcurrency(String ccurrency) {
        this.ccurrency = ccurrency;
    }
    /** full constructor */
    public Client(String Company_name, String Company_code, String Address_1, String Address_2, String billing1, String billing2, String billing3, String City, String State_prov, String Zip_postal_code, String Country, String Main_telephone_numb, String workPhoneEx, String Fax_number, String url, String Status, String Email_address, String Note, String Project_mngr, String Backup_pm, String Sales_rep, String Satisfaction_score, String Satisfaction_level, String Ftp_host_excel, String Ftp_user_id_excel, String Ftp_password_excel, String Ftp_host_client, String Ftp_user_id_client, String Ftp_password_client, String logo, String scaleRep, String scale100, String scale95, String scale85, String scale75, String scaleNew, String scale8599, String scaleNew4, String scaleContext, String scalePerfect,Boolean scaleDefault, app.client.Industry Industry, Set Contacts, Set Projects, Set ClientLanguagePairs, Set ClientOtherRates,String otherContact1,String otherContact2,String otherContact3,String otherContact4,String otherContact5,String ccurrency,String terms,String termsNames) {
        this.Company_name = Company_name;
        this.Company_code = Company_code;
        this.Address_1 = Address_1;
        this.Address_2 = Address_2;
        this.billing1 = billing1;
        this.billing2 = billing2;
        this.billing3 = billing3;
        this.City = City;
        this.State_prov = State_prov;
        this.Zip_postal_code = Zip_postal_code;
        this.Country = Country;
        this.Main_telephone_numb = Main_telephone_numb;
        this.workPhoneEx = workPhoneEx;
        this.Fax_number = Fax_number;
        this.url = url;
        this.Status = Status;
        this.Email_address = Email_address;
        this.Note = Note;
        this.Project_mngr = Project_mngr;
        this.Backup_pm = Backup_pm;
        this.Sales_rep = Sales_rep;
        this.Satisfaction_score = Satisfaction_score;
        this.Satisfaction_level = Satisfaction_level;
        this.Ftp_host_excel = Ftp_host_excel;
        this.Ftp_user_id_excel = Ftp_user_id_excel;
        this.Ftp_password_excel = Ftp_password_excel;
        this.Ftp_host_client = Ftp_host_client;
        this.Ftp_user_id_client = Ftp_user_id_client;
        this.Ftp_password_client = Ftp_password_client;
        this.logo = logo;
        this.scaleRep = scaleRep;
        this.scale100 = scale100;
        this.scale95 = scale95;
        this.scale85 = scale85;
        this.scale75 = scale75;
        this.scaleNew = scaleNew;
        this.scale8599 = scale8599;
        this.scaleNew4 = scaleNew4;
        this.scaleContext = scaleContext;
        this.scalePerfect = scalePerfect;
        this.scaleDefault = scaleDefault;
        //this.tm_maintained=tm_maintained;
        this.Industry = Industry;
        this.Contacts = Contacts;
        this.Projects = Projects;
        this.ClientLanguagePairs = ClientLanguagePairs;
        this.ClientOtherRates = ClientOtherRates;
        this.otherContact1=otherContact1;
        this.otherContact2=otherContact2;
        this.otherContact3=otherContact3;
        this.otherContact4=otherContact4;
        this.otherContact5=otherContact5;
        this.ccurrency=ccurrency;
        this.terms=terms;
        this.termsNames=termsNames;
    }

    /** default constructor */
    public Client() {
    }

    /** minimal constructor */
    public Client(Set Contacts, Set Projects, Set ClientLanguagePairs, Set ClientOtherRates) {
        this.Contacts = Contacts;
        this.Projects = Projects;
        this.ClientLanguagePairs = ClientLanguagePairs;
        this.ClientOtherRates = ClientOtherRates;
    }

    public Integer getClientId() {
        return this.clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getCompany_name() {
        return this.Company_name;
    }

    public void setCompany_name(String Company_name) {
        this.Company_name = Company_name;
    }

    public String getCompany_code() {
        return this.Company_code;
    }

    public void setCompany_code(String Company_code) {
        this.Company_code = Company_code;
    }

    public String getAddress_1() {
        return this.Address_1;
    }

    public void setAddress_1(String Address_1) {
        this.Address_1 = Address_1;
    }

    public String getAddress_2() {
        return this.Address_2;
    }

    public void setAddress_2(String Address_2) {
        this.Address_2 = Address_2;
    }

    public String getBilling1() {
        return this.billing1;
    }

    public void setBilling1(String billing1) {
        this.billing1 = billing1;
    }

    public String getBilling2() {
        return this.billing2;
    }

    public void setBilling2(String billing2) {
        this.billing2 = billing2;
    }

    public String getBilling3() {
        return this.billing3;
    }

    public void setBilling3(String billing3) {
        this.billing3 = billing3;
    }

    public String getCity() {
        return this.City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getState_prov() {
        return this.State_prov;
    }

    public void setState_prov(String State_prov) {
        this.State_prov = State_prov;
    }

    public String getZip_postal_code() {
        return this.Zip_postal_code;
    }

    public void setZip_postal_code(String Zip_postal_code) {
        this.Zip_postal_code = Zip_postal_code;
    }

    public String getCountry() {
        return this.Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String getMain_telephone_numb() {
        return this.Main_telephone_numb;
    }

    public void setMain_telephone_numb(String Main_telephone_numb) {
        this.Main_telephone_numb = Main_telephone_numb;
    }

    public String getWorkPhoneEx() {
        return this.workPhoneEx;
    }

    public void setWorkPhoneEx(String workPhoneEx) {
        this.workPhoneEx = workPhoneEx;
    }

    public String getFax_number() {
        return this.Fax_number;
    }

    public void setFax_number(String Fax_number) {
        this.Fax_number = Fax_number;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return this.Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getEmail_address() {
        return this.Email_address;
    }

    public void setEmail_address(String Email_address) {
        this.Email_address = Email_address;
    }

    public String getNote() {
        return this.Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    public String getProject_mngr() {
        return this.Project_mngr;
    }

    public void setProject_mngr(String Project_mngr) {
        this.Project_mngr = Project_mngr;
    }

    public String getBackup_pm() {
        return this.Backup_pm;
    }

    public void setBackup_pm(String Backup_pm) {
        this.Backup_pm = Backup_pm;
    }

    public String getSales_rep() {
        return this.Sales_rep;
    }

    public void setSales_rep(String Sales_rep) {
        this.Sales_rep = Sales_rep;
    }

    public String getSatisfaction_score() {
        return this.Satisfaction_score;
    }

    public void setSatisfaction_score(String Satisfaction_score) {
        this.Satisfaction_score = Satisfaction_score;
    }

    public String getSatisfaction_level() {
        return this.Satisfaction_level;
    }

    public void setSatisfaction_level(String Satisfaction_level) {
        this.Satisfaction_level = Satisfaction_level;
    }

    public String getFtp_host_excel() {
        return this.Ftp_host_excel;
    }

    public void setFtp_host_excel(String Ftp_host_excel) {
        this.Ftp_host_excel = Ftp_host_excel;
    }

    public String getFtp_user_id_excel() {
        return this.Ftp_user_id_excel;
    }

    public void setFtp_user_id_excel(String Ftp_user_id_excel) {
        this.Ftp_user_id_excel = Ftp_user_id_excel;
    }

    public String getFtp_password_excel() {
        return this.Ftp_password_excel;
    }

    public void setFtp_password_excel(String Ftp_password_excel) {
        this.Ftp_password_excel = Ftp_password_excel;
    }

    public String getFtp_host_client() {
        return this.Ftp_host_client;
    }

    public void setFtp_host_client(String Ftp_host_client) {
        this.Ftp_host_client = Ftp_host_client;
    }

    public String getFtp_user_id_client() {
        return this.Ftp_user_id_client;
    }

    public void setFtp_user_id_client(String Ftp_user_id_client) {
        this.Ftp_user_id_client = Ftp_user_id_client;
    }

    public String getFtp_password_client() {
        return this.Ftp_password_client;
    }

    public void setFtp_password_client(String Ftp_password_client) {
        this.Ftp_password_client = Ftp_password_client;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getScaleRep() {
        return this.scaleRep;
    }

    public void setScaleRep(String scaleRep) {
        this.scaleRep = scaleRep;
    }

    public String getScale100() {
        return this.scale100;
    }

    public void setScale100(String scale100) {
        this.scale100 = scale100;
    }

    public String getScale95() {
        return this.scale95;
    }

    public void setScale95(String scale95) {
        this.scale95 = scale95;
    }

    public String getScale85() {
        return this.scale85;
    }

    public void setScale85(String scale85) {
        this.scale85 = scale85;
    }

    public String getScale75() {
        return this.scale75;
    }

    public void setScale75(String scale75) {
        this.scale75 = scale75;
    }

    public String getScaleNew() {
        return this.scaleNew;
    }

    public void setScaleNew(String scaleNew) {
        this.scaleNew = scaleNew;
    }

    public String getScale8599() {
        return this.scale8599;
    }

    public void setScale8599(String scale8599) {
        this.scale8599 = scale8599;
    }

    public String getScaleNew4() {
        return this.scaleNew4;
    }

    public void setScaleNew4(String scaleNew4) {
        this.scaleNew4 = scaleNew4;
    }

    public Boolean isScaleDefault() {
        return this.scaleDefault;
    }

    public void setScaleDefault(Boolean scaleDefault) {
        this.scaleDefault = scaleDefault;
    }

    public app.client.Industry getIndustry() {
        return this.Industry;
    }

    public void setIndustry(app.client.Industry Industry) {
        this.Industry = Industry;
    }

    public Set getContacts() {
        return this.Contacts;
    }

    public void setContacts(Set Contacts) {
        this.Contacts = Contacts;
    }

    public Set getProjects() {
        return this.Projects;
    }

    public void setProjects(Set Projects) {
        this.Projects = Projects;
    }

    public Set getClientLanguagePairs() {
        return this.ClientLanguagePairs;
    }

    public void setClientLanguagePairs(Set ClientLanguagePairs) {
        this.ClientLanguagePairs = ClientLanguagePairs;
    }

    public Set getClientOtherRates() {
        return this.ClientOtherRates;
    }

    public void setClientOtherRates(Set ClientOtherRates) {
        this.ClientOtherRates = ClientOtherRates;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("clientId", getClientId())
            .toString();
    }

    public int getDelinquent() {
        return delinquent;
    }

    public void setDelinquent(int delinquent) {
        this.delinquent = delinquent;
    }

    public String getTracker_password() {
        return tracker_password;
    }

    public void setTracker_password(String tracker_password) {
        this.tracker_password = tracker_password;
    }

    public String getBusiness_desc() {
        return business_desc;
    }

    public void setBusiness_desc(String business_desc) {
        this.business_desc = business_desc;
    }

    public String getCertifications() {
        return certifications;
    }

    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }

    public String getMain_engineer() {
        return main_engineer;
    }

    public void setMain_engineer(String main_engineer) {
        this.main_engineer = main_engineer;
    }

    public String getMain_dtp() {
        return main_dtp;
    }

    public void setMain_dtp(String main_dtp) {
        this.main_dtp = main_dtp;
    }

    public String getOther_engineer() {
        return other_engineer;
    }

    public void setOther_engineer(String other_engineer) {
        this.other_engineer = other_engineer;
    }

    public String getOther_dtp() {
        return other_dtp;
    }

    public void setOther_dtp(String other_dtp) {
        this.other_dtp = other_dtp;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getCreatedByDate() {
        return createdByDate;
    }

    public void setCreatedByDate(Date createdByDate) {
        this.createdByDate = createdByDate;
    }

    public Date getModifiedByDate() {
        return modifiedByDate;
    }

    public void setModifiedByDate(Date modifiedByDate) {
        this.modifiedByDate = modifiedByDate;
    }

   private String billing_address1;
  private String billing_address2;
  private String billing_zip;
  private String billing_city;
  private String billing_country;
  private String billing_attention;
  private String billing_reference;

    public String getBilling_address1() {
        return billing_address1;
    }

    public void setBilling_address1(String billing_address1) {
        this.billing_address1 = billing_address1;
    }

    public String getBilling_address2() {
        return billing_address2;
    }

    public void setBilling_address2(String billing_address2) {
        this.billing_address2 = billing_address2;
    }

    public String getBilling_zip() {
        return billing_zip;
    }

    public void setBilling_zip(String billing_zip) {
        this.billing_zip = billing_zip;
    }

    public String getBilling_city() {
        return billing_city;
    }

    public void setBilling_city(String billing_city) {
        this.billing_city = billing_city;
    }

    public String getBilling_country() {
        return billing_country;
    }

    public void setBilling_country(String billing_country) {
        this.billing_country = billing_country;
    }

    public String getBilling_attention() {
        return billing_attention;
    }

    public void setBilling_attention(String billing_attention) {
        this.billing_attention = billing_attention;
    }

    public String getBilling_reference() {
        return billing_reference;
    }

    public void setBilling_reference(String billing_reference) {
        this.billing_reference = billing_reference;
    }

    public String getLin_css_other() {
        return lin_css_other;
    }

    public void setLin_css_other(String lin_css_other) {
        this.lin_css_other = lin_css_other;
    }

    public String getTech_css_other() {
        
        return tech_css_other;
    }

    public void setTech_css_other(String tech_css_other) {
       
        this.tech_css_other = tech_css_other;
    }

    public String getuserType() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String getScaleContext() {
        return scaleContext;
    }

    public void setScaleContext(String scaleContext) {
        this.scaleContext = scaleContext;
    }

    public String getScalePerfect() {
        return scalePerfect;
    }

    public void setScalePerfect(String scalePerfect) {
        this.scalePerfect = scalePerfect;
    }

    public String getTm_maintained() {
        return tm_maintained;
    }

    public void setTm_maintained(String tm_maintained) {
        this.tm_maintained = tm_maintained;
    }

    public String getMainSource() {
        return mainSource;
    }

    public void setMainSource(String mainSource) {
        this.mainSource = mainSource;
    }

    public String getMainTarget() {
        return mainTarget;
    }

    public void setMainTarget(String mainTarget) {
        this.mainTarget = mainTarget;
    }

    public String getOtherContact1() {
        return otherContact1;
    }

    public void setOtherContact1(String otherContact1) {
        this.otherContact1 = otherContact1;
    }

    public String getOtherContact2() {
        return otherContact2;
    }

    public void setOtherContact2(String otherContact2) {
        this.otherContact2 = otherContact2;
    }

    public String getOtherContact3() {
        return otherContact3;
    }

    public void setOtherContact3(String otherContact3) {
        this.otherContact3 = otherContact3;
    }

    public String getOtherContact4() {
        return otherContact4;
    }

    public void setOtherContact4(String otherContact4) {
        this.otherContact4 = otherContact4;
    }

    public String getOtherContact5() {
        return otherContact5;
    }

    public void setOtherContact5(String otherContact5) {
        this.otherContact5 = otherContact5;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getTermsNames() {
        return termsNames;
    }

    public void setTermsNames(String termsNames) {
        this.termsNames = termsNames;
    }

    public Double getPmfeePercentage() {
        return pmfeePercentage;
    }

    public void setPmfeePercentage(Double pmfeePercentage) {
        this.pmfeePercentage = pmfeePercentage;
    }

  public String getAuto_alert() {
    return auto_alert;
  }

  public void setAuto_alert(String auto_alert) {
    this.auto_alert = auto_alert;
  }

  
}
