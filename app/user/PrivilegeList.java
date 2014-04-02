package app.user;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class PrivilegeList implements Serializable {

    /** identifier field */
    private Integer privilegeListId;

    /** persistent field */
    private String privilege;

    /** full constructor */
    public PrivilegeList(String privilege) {
        this.privilege = privilege;
    }

    /** default constructor */
    public PrivilegeList() {
    }

    public Integer getPrivilegeListId() {
        return this.privilegeListId;
    }

    public void setPrivilegeListId(Integer privilegeListId) {
        this.privilegeListId = privilegeListId;
    }

    public String getPrivilege() {
        return this.privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("privilegeListId", getPrivilegeListId())
            .toString();
    }

}
