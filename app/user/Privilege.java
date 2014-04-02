package app.user;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Privilege implements Serializable {
    
public Privilege()
{
    
}
    /** identifier field */
    private Integer privilegeId;

    /** nullable persistent field */
    private String privilege;

    /** nullable persistent field */
    private app.user.User User;

    /** full constructor */
    public Privilege(String privilege, app.user.User User) {
        this.privilege = privilege;
        this.User = User;
    }

    
    public Integer getPrivilegeId() {
        return this.privilegeId;
    }

    public void setPrivilegeId(Integer privilegeId) {
        this.privilegeId = privilegeId;
    }

    public String getPrivilege() {
        return this.privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public app.user.User getUser() {
        return this.User;
    }

    public void setUser(app.user.User User) {
        this.User = User;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("privilegeId", getPrivilegeId())
            .toString();
    }

}
