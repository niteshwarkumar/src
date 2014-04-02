package app.user;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Location implements Serializable {

    /** identifier field */
    private Integer locationId;

    /** persistent field */
    private String location;

    /** persistent field */
    private String friendlyName;

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
    private String url;

    /** nullable persistent field */
    private String Status;

    /** nullable persistent field */
    private String Email_address;

    /** nullable persistent field */
    private String Note;

    /** nullable persistent field */
    private String Ftp_host_excel;

    /** nullable persistent field */
    private String Ftp_user_id_excel;

    /** nullable persistent field */
    private String Ftp_password_excel;

    /** nullable persistent field */
    private String logo;

    /** persistent field */
    private Set Users;

    /** full constructor */
    public Location(String location,String friendlyName, String Address_1, String Address_2, String City, String State_prov, String Zip_postal_code, String Country, String Main_telephone_numb, String workPhoneEx, String Fax_number, String url, String Status, String Email_address, String Note, String Ftp_host_excel, String Ftp_user_id_excel, String Ftp_password_excel, String logo, Set Users) {
        this.location = location;
        this.friendlyName = friendlyName;
        this.Address_1 = Address_1;
        this.Address_2 = Address_2;
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
        this.Ftp_host_excel = Ftp_host_excel;
        this.Ftp_user_id_excel = Ftp_user_id_excel;
        this.Ftp_password_excel = Ftp_password_excel;
        this.logo = logo;
        this.Users = Users;
    }

    /** default constructor */
    public Location() {
    }

    /** minimal constructor */
    public Location(String location, Set Users) {
        this.location = location;
        this.Users = Users;
    }

    public Integer getLocationId() {
        return this.locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
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

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Set getUsers() {
        return this.Users;
    }

    public void setUsers(Set Users) {
        this.Users = Users;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("locationId", getLocationId())
            .toString();
    }

}
