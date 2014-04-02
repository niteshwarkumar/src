package app.user;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class ProjectCart implements Serializable {

    /** identifier field */
    private Integer projectCartId;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String resourceIds;

    /** nullable persistent field */
    private app.user.User User;

    /** full constructor */
    public ProjectCart(String description, String resourceIds, app.user.User User) {
        this.description = description;
        this.resourceIds = resourceIds;
        this.User = User;
    }

    /** default constructor */
    public ProjectCart() {
    }

    public Integer getProjectCartId() {
        return this.projectCartId;
    }

    public void setProjectCartId(Integer projectCartId) {
        this.projectCartId = projectCartId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResourceIds() {
        return this.resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public app.user.User getUser() {
        return this.User;
    }

    public void setUser(app.user.User User) {
        this.User = User;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("projectCartId", getProjectCartId())
            .toString();
    }

}
