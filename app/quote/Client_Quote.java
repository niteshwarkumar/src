/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.quote;
//import app.project.Project;
import app.project.Project;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Niteshwar
 */
public class Client_Quote implements Serializable {

    private Integer id;
    private Integer Quote_ID;
    private Integer ID_Client;
    private Integer Product_ID;
    private String medical;
    private String Type;
    private String productName;
    private String productText;
    private String application;
    private String os;
    private String unit;
    private String volume;
    private String version;
    private Project Project;
    private String target_application;
    private String target_os;
    private String target_version;
    private String deliverable;
    private String component;
    private String typeOfText;
    private String requirement;
    private String instruction;
    private String clientTask;
    private String otherTask;
    private String srcComment;

    private Set SourceDocs;

    public Client_Quote() {
    }

    public Project getProject() {
        return Project;
    }

    public void setProject(Project Project) {
        this.Project = Project;
    }

    public Integer getQuote_ID() {
        return Quote_ID;
    }

    public void setQuote_ID(Integer Quote_ID) {
        this.Quote_ID = Quote_ID;
    }

    public Integer getID_Client() {
        return ID_Client;
    }

    public void setID_Client(Integer ID_Client) {
        this.ID_Client = ID_Client;
    }

    public Integer getProduct_ID() {
        return Product_ID;
    }

    public void setProduct_ID(Integer Product_ID) {
        this.Product_ID = Product_ID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getDeliverable() {
        return deliverable;
    }

    public void setDeliverable(String deliverable) {
        this.deliverable = deliverable;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMedical() {
        return medical;
    }

    public void setMedical(String medical) {
        this.medical = medical;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getTarget_application() {
        return target_application;
    }

    public void setTarget_application(String target_application) {
        this.target_application = target_application;
    }

    public String getTarget_os() {
        return target_os;
    }

    public void setTarget_os(String target_os) {
        this.target_os = target_os;
    }

    public String getTarget_version() {
        return target_version;
    }

    public void setTarget_version(String target_version) {
        this.target_version = target_version;
    }

    public String getTypeOfText() {
        return typeOfText;
    }

    public void setTypeOfText(String typeOfText) {
        this.typeOfText = typeOfText;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getClientTask() {
        return clientTask;
    }

    public void setClientTask(String clientTask) {
        this.clientTask = clientTask;
    }

    public String getOtherTask() {
        return otherTask;
    }

    public void setOtherTask(String otherTask) {
        this.otherTask = otherTask;
    }

    public Set getSourceDocs() {
        return this.SourceDocs;
    }

    public void setSourceDocs(Set SourceDocs) {
        this.SourceDocs = SourceDocs;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    public String getSrcComment() {
        return srcComment;
    }

    public void setSrcComment(String srcComment) {
        this.srcComment = srcComment;
    }

    


}
