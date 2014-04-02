package app.resource;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ResourceContact implements Serializable {

    /** identifier field */
    private Integer resourceContactId;

    /** nullable persistent field */
    private String firstName;

    /** nullable persistent field */
    private String lastName;

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
    private String Main_telephone_numb1;

    /** nullable persistent field */
    private String workPhoneEx1;

    /** nullable persistent field */
    private String Main_telephone_numb2;

    /** nullable persistent field */
    private String workPhoneEx2;

    /** nullable persistent field */
    private String Fax_number;

    /** nullable persistent field */
    private String cellPhone;

    /** nullable persistent field */
    private String url;

    /** nullable persistent field */
    private String Email_address1;

    /** nullable persistent field */
    private String Email_address2;

    /** nullable persistent field */
    private String Email_address3;

    /** nullable persistent field */
    private String Note;
    
    /** nullable persistent field */
    private String skype;
    
    /** nullable persistent field */
    private String timeDifference;

    /** nullable persistent field */
    private app.resource.Resource Resource;

    /** full constructor */
    public ResourceContact(String firstName, String lastName, String Address_1, String Address_2, String City, String State_prov, String Zip_postal_code, String Country, String Main_telephone_numb1, String workPhoneEx1, String Main_telephone_numb2, String workPhoneEx2, String Fax_number, String cellPhone, String url, String Email_address1, String Email_address2, String Email_address3, String Note, app.resource.Resource Resource) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.Address_1 = Address_1;
        this.Address_2 = Address_2;
        this.City = City;
        this.State_prov = State_prov;
        this.Zip_postal_code = Zip_postal_code;
        this.Country = Country;
        this.Main_telephone_numb1 = Main_telephone_numb1;
        this.workPhoneEx1 = workPhoneEx1;
        this.Main_telephone_numb2 = Main_telephone_numb2;
        this.workPhoneEx2 = workPhoneEx2;
        this.Fax_number = Fax_number;
        this.cellPhone = cellPhone;
        this.url = url;
        this.Email_address1 = Email_address1;
        this.Email_address2 = Email_address2;
        this.Email_address3 = Email_address3;
        this.Note = Note;
        this.Resource = Resource;
    }

    /** default constructor */
    public ResourceContact() {
    }

    public Integer getResourceContactId() {
        return this.resourceContactId;
    }

    public void setResourceContactId(Integer resourceContactId) {
        this.resourceContactId = resourceContactId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getMain_telephone_numb1() {
        return this.Main_telephone_numb1;
    }

    public void setMain_telephone_numb1(String Main_telephone_numb1) {
        this.Main_telephone_numb1 = Main_telephone_numb1;
    }

    public String getWorkPhoneEx1() {
        return this.workPhoneEx1;
    }

    public void setWorkPhoneEx1(String workPhoneEx1) {
        this.workPhoneEx1 = workPhoneEx1;
    }

    public String getMain_telephone_numb2() {
        return this.Main_telephone_numb2;
    }

    public void setMain_telephone_numb2(String Main_telephone_numb2) {
        this.Main_telephone_numb2 = Main_telephone_numb2;
    }

    public String getWorkPhoneEx2() {
        return this.workPhoneEx2;
    }

    public void setWorkPhoneEx2(String workPhoneEx2) {
        this.workPhoneEx2 = workPhoneEx2;
    }

    public String getFax_number() {
        return this.Fax_number;
    }

    public void setFax_number(String Fax_number) {
        this.Fax_number = Fax_number;
    }

    public String getCellPhone() {
        return this.cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail_address1() {
        return this.Email_address1;
    }

    public void setEmail_address1(String Email_address1) {
        this.Email_address1 = Email_address1;
    }

    public String getEmail_address2() {
        return this.Email_address2;
    }

    public void setEmail_address2(String Email_address2) {
        this.Email_address2 = Email_address2;
    }

    public String getEmail_address3() {
        return this.Email_address3;
    }

    public void setEmail_address3(String Email_address3) {
        this.Email_address3 = Email_address3;
    }

    public String getNote() {
        return this.Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    public app.resource.Resource getResource() {
        return this.Resource;
    }

    public void setResource(app.resource.Resource Resource) {
        this.Resource = Resource;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("resourceContactId", getResourceContactId())
            .toString();
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getTimeDifference() {
        return timeDifference;
    }

    public void setTimeDifference(String timeDifference) {
        this.timeDifference = timeDifference;
    }

}
