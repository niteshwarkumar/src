/*
 * ClientLocation.java
 *
 * Created on July 10, 2008, 3:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */
package app.extjs.vo;

/**
 *
 * @author pp41387
 */
public class ClientLocation {

    private Integer location_id;
    private String division;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String country;
    private Integer ID_Client;

    /** Creates a new instance of ClientLocation */
    public ClientLocation() {
    }

    public Integer getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Integer location_id) {
        this.location_id = location_id;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getID_Client() {
        return ID_Client;
    }

    public void setID_Client(Integer ID_Client) {
        this.ID_Client = ID_Client;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }
    
}
