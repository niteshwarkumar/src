//ClientEditForm.java works to collect/display values
//to/from the web pages (jsp) related to an existing client

package app.client;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class ClientEditForm extends ActionForm
{
	
     
    private int delinquent;
    
    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String Company_name;

    /** nullable persistent field */
    private String Company_code;

        /** nullable persistent field */
    private String division;

    /** nullable persistent field */
    private String Address_1;

    /** nullable persistent field */
    private String Address_2;

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
    private String Industry;

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
    private String Sales;
    
     /** nullable persistent field */
    private String business_desc;

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
    private String certifications;
    
    /** nullable persistent field */
    private String main_dtp;
    /** nullable persistent field */
    private String other_engineer;
    /** nullable persistent field */
    private String other_dtp;
    /** nullable persistent field */
    private String main_engineer;
    /** nullable persistent field */
    private String otherContact1;
    /** nullable persistent field */
    private String otherContact2;
    /** nullable persistent field */
    private String otherContact3;
    /** nullable persistent field */
    private String otherContact4;
    /** nullable persistent field */
    private String otherContact5;

    /** nullable persistent field */
    private FormFile terms;

    private String termsNames;
    
    private String auto_alert;
    
    private String specialNotes;
 
    /** nullable persistent field */
    private FormFile logo;
    
     public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request)
{
		/*
		 * Create new ActionErrors object to hold any errors we discover
		 * upon validation of the form's fields.
		 * 
		 */
		ActionErrors errors = new ActionErrors();

		/*
		 * Check the name field to ensure that it is neither empty nor
		 * contains a zero length string.
		 * 
		 */
                if (Company_name == null || Company_name.length() < 1)
		{
			
			//errors.add("name", new ActionError("errors.required", "Name"));

		}		
                                
                return errors;
	}

	/**
	 * Clears the object prior to each request. 
	 * 
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		this.clear();
	}
	

	/**
	 *
	 * clear() sets all the object's fields to <code>null</code>.
	 *
	 */
	public void clear()
	{
		/** nullable persistent field */
                this.Company_name = null;

                /** nullable persistent field */
                this.Address_1 = null;

                /** nullable persistent field */
                this.Address_2 = null;

                /** nullable persistent field */
                this.City = null;

                /** nullable persistent field */
                this.State_prov = null;

                /** nullable persistent field */
                this.Zip_postal_code = null;

                /** nullable persistent field */
                this.Country = null;

                /** nullable persistent field */
                this.Main_telephone_numb = null;
                
                this.workPhoneEx = null;

                /** nullable persistent field */
                this.Fax_number = null;

                /** nullable persistent field */
                this.Email_address = null;

                /** nullable persistent field */
                this.Note = null;
                
                /** nullable persistent field */
                this.Company_code = null;
                
                /** nullable persistent field */
                this.Industry = null;
                
                /** nullable persistent field */
                this.url = null;
                
                /** nullable persistent field */
                this.Status = null;
                                
                /** nullable persistent field */
                this.Project_mngr = null;
                
                /** nullable persistent field */
                this.Backup_pm = null;
                
                /** nullable persistent field */
                this.Sales_rep = null;
                
                /** nullable persistent field */
                this.Sales = null;
                
                /** nullable persistent field */
                this.Satisfaction_score = null;
                
                /** nullable persistent field */
                this.Satisfaction_level = null;
                
                this.Ftp_host_excel = null;
                
                this.Ftp_user_id_excel = null;
                
                this.Ftp_password_excel = null;
                
                this.Ftp_host_client = null;
                
                this.Ftp_user_id_client = null;
                
                this.Ftp_password_client = null;
                
                this.logo = null;
                
                this.specialNotes = null;

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

    public String getIndustry() {
        return this.Industry;
    }

    public void setIndustry(String Industry) {
        this.Industry = Industry;
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
    
    public FormFile getLogo() {
        return this.logo;
    }

    public void setLogo(FormFile logo) {
        this.logo = logo;
    }

    public int getDelinquent() {
        return delinquent;
    }

    public void setDelinquent(int delinquent) {
        this.delinquent = delinquent;
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

    public String getMain_engineer() {
        return main_engineer;
    }

    public void setMain_engineer(String main_engineer) {
        this.main_engineer = main_engineer;
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

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public FormFile getTerms() {
        return terms;
    }

    public void setTerms(FormFile terms) {
        this.terms = terms;
    }

    public String getTermsNames() {
        return termsNames;
    }

    public void setTermsNames(String termsNames) {
        this.termsNames = termsNames;
    }

  public String getAuto_alert() {
    return auto_alert;
  }

  public void setAuto_alert(String auto_alert) {
    this.auto_alert = auto_alert;
  }

    public String getSales() {
        return Sales;
    }

    public void setSales(String Sales) {
        this.Sales = Sales;
    }

    public String getSpecialNotes() {
        return specialNotes;
    }

    public void setSpecialNotes(String specialNotes) {
        this.specialNotes = specialNotes;
    }

    

}
