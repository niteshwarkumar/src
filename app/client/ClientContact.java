package app.client;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ClientContact implements Serializable {

    /** identifier field */
    private Integer clientContactId;

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
    private boolean key_personnel;
    
    /** nullable persistent field */
    private boolean display = true;
    
    /** nullable persistent field */
    private boolean isformer;

    /** nullable persistent field */
    private String Zip_postal_code;

    /** nullable persistent field */
    private String Country;

     /** nullable persistent field */
    private String timeZone;

    /** nullable persistent field */
    private String Note;
    
    /** nullable persistent field */
    private String tracker_password;
    /** nullable persistent field */
    private app.client.Client Company;

    /** persistent field */
    private Set Projects;

    /** full constructor */
    public ClientContact(String First_name, String Last_name, String Title, String Email_address, String Email_address2, String Department, String Telephone_number, String workPhoneEx, String Fax_number, String Cell_phone_number, String Address_1, String Address_2, String City, String State_prov, String Zip_postal_code, String Country, String timeZone, String division, String Note, app.client.Client Company, Set Projects) {
        this.First_name = First_name;
        this.Last_name = Last_name;
        this.Title = Title;
        this.Email_address = Email_address;
        this.Email_address2 = Email_address2;
        this.Department = Department;
        this.Telephone_number = Telephone_number;
        this.workPhoneEx = workPhoneEx;
        this.Fax_number = Fax_number;
        this.Cell_phone_number = Cell_phone_number;
        this.Address_1 = Address_1;
        this.Address_2 = Address_2;
        this.City = City;
        this.State_prov = State_prov;
        this.Zip_postal_code = Zip_postal_code;
        this.Country = Country;
        this.timeZone=timeZone;
        this.Note = Note;
        this.Company = Company;
        this.Projects = Projects;
        this.division=division;
    }

    /** default constructor */
    public ClientContact() {
    }

    /** minimal constructor */
    public ClientContact(Set Projects) {
        this.Projects = Projects;
    }

    public Integer getClientContactId() {
        return this.clientContactId;
    }

    public void setClientContactId(Integer clientContactId) {
        this.clientContactId = clientContactId;
    }

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

    public String getNote() {
        return this.Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    public app.client.Client getCompany() {
        return this.Company;
    }

    public void setCompany(app.client.Client Company) {
        this.Company = Company;
    }

    public Set getProjects() {
        return this.Projects;
    }

    public void setProjects(Set Projects) {
        this.Projects = Projects;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("clientContactId", getClientContactId())
            .toString();
    }

    public String getTracker_password() {
        return tracker_password;
    }

    public void setTracker_password(String tracker_password) {
        this.tracker_password = tracker_password;
    }

    public boolean isKey_personnel() {
        return key_personnel;
    }

    public void setKey_personnel(boolean key_personnel) {
        this.key_personnel = key_personnel;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public boolean isIsformer() {
        
        return isformer;
    }

    public void setIsformer(boolean isformer) {
        this.isformer = isformer;
    }
    
    

}
