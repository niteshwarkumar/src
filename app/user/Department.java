package app.user;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Department implements Serializable {

    /** identifier field */
    private Integer departmentId;

    /** persistent field */
    private String department;

    /** persistent field */
    private Set Users;

    /** full constructor */
    public Department(String department, Set Users) {
        this.department = department;
        this.Users = Users;
    }

    /** default constructor */
    public Department() {
    }

    public Integer getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Set getUsers() {
        return this.Users;
    }

    public void setUsers(Set Users) {
        this.Users = Users;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("departmentId", getDepartmentId())
            .toString();
    }

}
