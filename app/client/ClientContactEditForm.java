//ClientContactEditForm.java works to collect/display values
//to/from the web pages (jsp) related to a client's contacts

package app.client;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ClientContactEditForm extends ActionForm
{
    /** nullable persistent field */
    private String First_name;

    /** nullable persistent field */
    private String Last_name;

    /** nullable persistent field */
    private String Title;

    /** nullable persistent field */
    private String Email_address;

    /** nullable persistent field */
    private String Email_address2;

    /** nullable persistent field */
    private String Department;

    /** nullable persistent field */
    private String Telephone_number;

    /** nullable persistent field */
    private String workPhoneEx;
    
    /** nullable persistent field */
    private String Fax_number;

    /** nullable persistent field */
    private String Cell_phone_number;

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
    private String timeZone;

    /** nullable persistent field */
    private String Note;
    private boolean key_personnel;
    private boolean isFormer;

    public String getFirst_name() {
        return this.First_name;
    }

    public void setFirst_name(String First_name) {
        this.First_name = First_name;
    }

    public String getLast_name() {
        return this.Last_name;
    }

    public void setLast_name(String Last_name) {
        this.Last_name = Last_name;
    }

    public String getTitle() {
        return this.Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getEmail_address() {
        return this.Email_address;
    }

    public void setEmail_address(String Email_address) {
        this.Email_address = Email_address;
    }

    public String getEmail_address2() {
        return this.Email_address2;
    }

    public void setEmail_address2(String Email_address2) {
        this.Email_address2 = Email_address2;
    }

    public String getDepartment() {
        return this.Department;
    }

    public void setDepartment(String Department) {
        this.Department = Department;
    }

    public String getTelephone_number() {
        return this.Telephone_number;
    }

    public void setTelephone_number(String Telephone_number) {
        this.Telephone_number = Telephone_number;
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

    public String getCell_phone_number() {
        return this.Cell_phone_number;
    }

    public void setCell_phone_number(String Cell_phone_number) {
        this.Cell_phone_number = Cell_phone_number;
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

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }


    public String getNote() {
        return this.Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }
    
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
		if (Last_name == null || Last_name.length() < 1)
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
                this.First_name = null;
                
                /** nullable persistent field */
                this.Last_name = null;

                /** nullable persistent field */
                this.Title = null;
                
                /** nullable persistent field */
                this.Email_address = null;
                
                /** nullable persistent field */
                this.Email_address2 = null;
                
                /** nullable persistent field */
                this.Department = null;
                
                /** nullable persistent field */
                this.Telephone_number = null;
                
                this.workPhoneEx = null;
                
                /** nullable persistent field */
                this.Fax_number = null;
                
                /** nullable persistent field */
                this.Cell_phone_number = null;

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
                this.timeZone = null;

                  /** nullable persistent field */
                this.division = null;

                /** nullable persistent field */
                this.setNote(null);    
	}

    public boolean isKey_personnel() {
        return key_personnel;
    }

    public void setKey_personnel(boolean key_personnel) {
        this.key_personnel = key_personnel;
    }

    public boolean isIsFormer() {
        return isFormer;
    }

    public void setIsFormer(boolean isFormer) {
        this.isFormer = isFormer;
    }
    
    

}
