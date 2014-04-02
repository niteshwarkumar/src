package app.resource;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ResourceTool implements Serializable {

    /** identifier field */
    private Integer resourceToolId;

    /** nullable persistent field */
    private String description;

    private String application;

    private String os;
    /** nullable persistent field */
    private String version;

    /** nullable persistent field */
    private app.resource.Resource Resource;

    /** full constructor */
    public ResourceTool(String description, String version, app.resource.Resource Resource) {
        this.description = description;
        this.version = version;
        this.Resource = Resource;
    }

    /** default constructor */
    public ResourceTool() {
    }

    public Integer getResourceToolId() {
        return this.resourceToolId;
    }

    public void setResourceToolId(Integer resourceToolId) {
        this.resourceToolId = resourceToolId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public app.resource.Resource getResource() {
        return this.Resource;
    }

    public void setResource(app.resource.Resource Resource) {
        this.Resource = Resource;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("resourceToolId", getResourceToolId())
            .toString();
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }
    

}
