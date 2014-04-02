package app.user;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class User implements Serializable {

    /** identifier field */
    private Integer userId;

    private String userType;

    private Integer id_client;

    /** persistent field */
    private String username;

    /** persistent field */
    private String password;

    /** persistent field */
    private String currentEmployee;

    /** nullable persistent field */
    private String firstName;

    /** nullable persistent field */
    private String lastName;

    /** nullable persistent field */
    private String picture;

    /** nullable persistent field */
    private String adminUser;

    /** nullable persistent field */
    private String dropDown;

    /** nullable persistent field */
    private String human;

    /** nullable persistent field */
    private String homeAddress;

    /** nullable persistent field */
    private String homeCity;

    /** nullable persistent field */
    private String homeState;

    /** nullable persistent field */
    private String homeCountry;

    /** nullable persistent field */
    private String homeZip;

    /** nullable persistent field */
    private String homePhone;

    /** nullable persistent field */
    private String homeCell;

    /** nullable persistent field */
    private String homeEmail1;

    /** nullable persistent field */
    private String homeEmail2;

    /** nullable persistent field */
    private String workAddress;

    /** nullable persistent field */
    private String workCity;

    /** nullable persistent field */
    private String workState;

    /** nullable persistent field */
    private String workCountry;

    /** nullable persistent field */
    private String workZip;

    /** nullable persistent field */
    private String workPhone;

    /** nullable persistent field */
    private String workPhoneEx;

    /** nullable persistent field */
    private String workCell;

    /** nullable persistent field */
    private String workEmail1;

    /** nullable persistent field */
    private String workEmail2;

    /** nullable persistent field */
    private String activeLevel;

    /** nullable persistent field */
    private String emergencyContactName;

    /** nullable persistent field */
    private String emergencyContactPhone;

    /** nullable persistent field */
    private String emergencyContactRelation;

    /** nullable persistent field */
    private Date birthDay;

    /** nullable persistent field */
    private Date hireDate;

    /** nullable persistent field */
    private Double vacationDaysTotal;

    /** nullable persistent field */
    private Double vacationDaysUsed;

    /** nullable persistent field */
    private Double sickDaysTotal;

    /** nullable persistent field */
    private Double sickDaysUsed;

    /** nullable persistent field */
    private Double unpaidDaysUsed;

    /** nullable persistent field */
    private String notes;

    /** nullable persistent field */
    private String skypeId;

    /** nullable persistent field */
    private String status;

    /** nullable persistent field */
    private String clientShow;

    /** nullable persistent field */
    private String signature;

    /** nullable persistent field */
    private Integer client_contact;


    /** nullable persistent field */
    private app.user.Location Location;

    /** nullable persistent field */
    private app.user.Department Department;

    /** nullable persistent field */
    private app.user.Position1 Position;

    /** persistent field */
    private Set Training;

    /** persistent field */
    private Set PerformanceReviews;

    /** persistent field */
    private Set Away;

    /** persistent field */
    private Set Privileges;

    /** persistent field */
    private Set SavedSearches;

    /** persistent field */
    private Set ProjectCarts;

    /** persistent field */
    private Set Todos;



    private java.sql.Timestamp last_activity;

    private String extjsTheme = "";
    private int myExcelRange = 0;

    /** full constructor */
    public User(String username, String password, String currentEmployee,String userType, String firstName, String lastName, String picture, String adminUser, String dropDown, String human, String homeAddress, String homeCity, String homeState, String homeCountry, String homeZip, String homePhone, String homeCell, String homeEmail1, String homeEmail2, String workAddress, String workCity, String workState, String workCountry, String workZip, String workPhone, String workPhoneEx, String workCell, String workEmail1, String workEmail2, String activeLevel, String emergencyContactName, String emergencyContactPhone, String emergencyContactRelation, String skypeId, Date birthDay, Date hireDate, Double vacationDaysTotal, Double vacationDaysUsed, Double sickDaysTotal, Double sickDaysUsed, Double unpaidDaysUsed, String notes, app.user.Location Location, app.user.Department Department, app.user.Position1 Position, Set Training, Set PerformanceReviews, Set Away, Set Privileges, Set SavedSearches, Set ProjectCarts, Set Todos, int id_client) {
        this.username = username;
        this.password = password;
        this.userType=userType;
        this.currentEmployee = currentEmployee;
        this.firstName = firstName;
        this.lastName = lastName;
        this.picture = picture;
        this.adminUser = adminUser;
        this.dropDown = dropDown;
        this.human = human;
        this.homeAddress = homeAddress;
        this.homeCity = homeCity;
        this.homeState = homeState;
        this.homeCountry = homeCountry;
        this.homeZip = homeZip;
        this.homePhone = homePhone;
        this.homeCell = homeCell;
        this.homeEmail1 = homeEmail1;
        this.homeEmail2 = homeEmail2;
        this.workAddress = workAddress;
        this.workCity = workCity;
        this.workState = workState;
        this.workCountry = workCountry;
        this.workZip = workZip;
        this.workPhone = workPhone;
        this.workPhoneEx = workPhoneEx;
        this.workCell = workCell;
        this.workEmail1 = workEmail1;
        this.workEmail2 = workEmail2;
        this.activeLevel = activeLevel;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhone = emergencyContactPhone;
        this.emergencyContactRelation = emergencyContactRelation;
        this.skypeId = skypeId;
        this.birthDay = birthDay;
        this.hireDate = hireDate;
        this.vacationDaysTotal = vacationDaysTotal;
        this.vacationDaysUsed = vacationDaysUsed;
        this.sickDaysTotal = sickDaysTotal;
        this.sickDaysUsed = sickDaysUsed;
        this.unpaidDaysUsed = unpaidDaysUsed;
        this.notes = notes;
        this.Location = Location;
        this.Department = Department;
        this.Position = Position;
        this.Training = Training;
        this.PerformanceReviews = PerformanceReviews;
        this.Away = Away;
        this.Privileges = Privileges;
        this.SavedSearches = SavedSearches;
        this.ProjectCarts = ProjectCarts;
        this.Todos = Todos;
        this.id_client = id_client;
    }

     public Integer getId_client() {
        return id_client;
    }

    public void setId_client(Integer id_client) {
        this.id_client = id_client;
    }

   
    /** default constructor */
    public User() {
    }


    /** minimal constructor */
    public User(String username, String password, String id_client,String currentEmployee, Set Training, Set PerformanceReviews, Set Away, Set Privileges, Set SavedSearches, Set ProjectCarts, Set Todos) {
        this.username = username;
        this.password = password;
        this.currentEmployee = currentEmployee;
        this.Training = Training;
        this.PerformanceReviews = PerformanceReviews;
        this.Away = Away;
        this.Privileges = Privileges;
        this.SavedSearches = SavedSearches;
        this.ProjectCarts = ProjectCarts;
        this.Todos = Todos;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCurrentEmployee() {
        return this.currentEmployee;
    }

    public void setCurrentEmployee(String currentEmployee) {
        this.currentEmployee = currentEmployee;
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

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAdminUser() {
        return this.adminUser;
    }

    public void setAdminUser(String adminUser) {
        this.adminUser = adminUser;
    }

    public String getDropDown() {
        return this.dropDown;
    }

    public void setDropDown(String dropDown) {
        this.dropDown = dropDown;
    }

    public String getHuman() {
        return this.human;
    }

    public void setHuman(String human) {
        this.human = human;
    }

    public String getHomeAddress() {
        return this.homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getHomeCity() {
        return this.homeCity;
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    public String getHomeState() {
        return this.homeState;
    }

    public void setHomeState(String homeState) {
        this.homeState = homeState;
    }

    public String getHomeCountry() {
        return this.homeCountry;
    }

    public void setHomeCountry(String homeCountry) {
        this.homeCountry = homeCountry;
    }

    public String getHomeZip() {
        return this.homeZip;
    }

    public void setHomeZip(String homeZip) {
        this.homeZip = homeZip;
    }

    public String getHomePhone() {
        return this.homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getHomeCell() {
        return this.homeCell;
    }

    public void setHomeCell(String homeCell) {
        this.homeCell = homeCell;
    }

    public String getHomeEmail1() {
        return this.homeEmail1;
    }

    public void setHomeEmail1(String homeEmail1) {
        this.homeEmail1 = homeEmail1;
    }

    public String getHomeEmail2() {
        return this.homeEmail2;
    }

    public void setHomeEmail2(String homeEmail2) {
        this.homeEmail2 = homeEmail2;
    }

    public String getWorkAddress() {
        return this.workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getWorkCity() {
        return this.workCity;
    }

    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }

    public String getWorkState() {
        return this.workState;
    }

    public void setWorkState(String workState) {
        this.workState = workState;
    }

    public String getWorkCountry() {
        return this.workCountry;
    }

    public void setWorkCountry(String workCountry) {
        this.workCountry = workCountry;
    }

    public String getWorkZip() {
        return this.workZip;
    }

    public void setWorkZip(String workZip) {
        this.workZip = workZip;
    }

    public String getWorkPhone() {
        return this.workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getWorkPhoneEx() {
        return this.workPhoneEx;
    }

    public void setWorkPhoneEx(String workPhoneEx) {
        this.workPhoneEx = workPhoneEx;
    }

    public String getWorkCell() {
        return this.workCell;
    }

    public void setWorkCell(String workCell) {
        this.workCell = workCell;
    }

    public String getWorkEmail1() {
        return this.workEmail1;
    }

    public void setWorkEmail1(String workEmail1) {
        this.workEmail1 = workEmail1;
    }

    public String getWorkEmail2() {
        return this.workEmail2;
    }

    public void setWorkEmail2(String workEmail2) {
        this.workEmail2 = workEmail2;
    }

    public String getActiveLevel() {
        return this.activeLevel;
    }

    public void setActiveLevel(String activeLevel) {
        this.activeLevel = activeLevel;
    }

    public String getEmergencyContactName() {
        return this.emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactPhone() {
        return this.emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public String getEmergencyContactRelation() {
        return this.emergencyContactRelation;
    }

    public void setEmergencyContactRelation(String emergencyContactRelation) {
        this.emergencyContactRelation = emergencyContactRelation;
    }

    public Date getBirthDay() {
        return this.birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Date getHireDate() {
        return this.hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Double getVacationDaysTotal() {
        return this.vacationDaysTotal;
    }

    public void setVacationDaysTotal(Double vacationDaysTotal) {
        this.vacationDaysTotal = vacationDaysTotal;
    }

    public Double getVacationDaysUsed() {
        return this.vacationDaysUsed;
    }

    public void setVacationDaysUsed(Double vacationDaysUsed) {
        this.vacationDaysUsed = vacationDaysUsed;
    }

    public Double getSickDaysTotal() {
        return this.sickDaysTotal;
    }

    public void setSickDaysTotal(Double sickDaysTotal) {
        this.sickDaysTotal = sickDaysTotal;
    }

    public Double getSickDaysUsed() {
        return this.sickDaysUsed;
    }

    public void setSickDaysUsed(Double sickDaysUsed) {
        this.sickDaysUsed = sickDaysUsed;
    }

    public Double getUnpaidDaysUsed() {
        return this.unpaidDaysUsed;
    }

    public void setUnpaidDaysUsed(Double unpaidDaysUsed) {
        this.unpaidDaysUsed = unpaidDaysUsed;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public app.user.Location getLocation() {
        return this.Location;
    }

    public void setLocation(app.user.Location Location) {
        this.Location = Location;
    }

    public app.user.Department getDepartment() {
        return this.Department;
    }

    public void setDepartment(app.user.Department Department) {
        this.Department = Department;
    }

    public app.user.Position1 getPosition() {
        return this.Position;
    }

    public void setPosition(app.user.Position1 Position) {
        this.Position = Position;
    }

    public Set getTraining() {
        return this.Training;
    }

    public void setTraining(Set Training) {
        this.Training = Training;
    }

    public Set getPerformanceReviews() {
        return this.PerformanceReviews;
    }

    public void setPerformanceReviews(Set PerformanceReviews) {
        this.PerformanceReviews = PerformanceReviews;
    }

    public Set getAway() {
        return this.Away;
    }

    public void setAway(Set Away) {
        this.Away = Away;
    }

    public Set getPrivileges() {

        return this.Privileges;
    }

    public void setPrivileges(Set Privileges) {
        this.Privileges = Privileges;

    }

    public Set getSavedSearches() {
        return this.SavedSearches;
    }

    public void setSavedSearches(Set SavedSearches) {
        this.SavedSearches = SavedSearches;
    }

    public Set getProjectCarts() {
        return this.ProjectCarts;
    }

    public void setProjectCarts(Set ProjectCarts) {
        this.ProjectCarts = ProjectCarts;
    }

    public Set getTodos() {
        return this.Todos;
    }

    public void setTodos(Set Todos) {
        this.Todos = Todos;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("userId", getUserId())
            .toString();
    }

    public java.sql.Timestamp getLast_activity() {
        return last_activity;
    }

    public void setLast_activity(java.sql.Timestamp last_activity) {
        this.last_activity = last_activity;
    }

    public String getExtjsTheme() {
        return extjsTheme;
    }

    public void setExtjsTheme(String extjsTheme) {
        this.extjsTheme = extjsTheme;
    }

      public int getMyExcelRange() {
        return myExcelRange;
    }

    public void setMyExcelRange(int myExcelRange) {
        this.myExcelRange = myExcelRange;
    }
    public Integer getID_Client(){
        return id_client;
    }
    public void setID_Client(app.client.Client C){
        this.id_client=C.getClientId();
    }
    public String getuserType() {
        return userType;
    }

    public void setuserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSkypeId() {
        return skypeId;
    }

    public void setSkypeId(String skypeId) {
        this.skypeId = skypeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClientShow() {
        return clientShow;
    }

    public void setClientShow(String clientShow) {
        this.clientShow = clientShow;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getClient_contact() {
        return client_contact;
    }

    public void setClient_contact(Integer client_contact) {
        this.client_contact = client_contact;
    }

    

}
