/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.user;

import java.io.Serializable;

/**
 *
 * @author Niteshwar
 */
public class DepartmentUser  implements Serializable {

    /** identifier field */
    private Integer id;

    /** identifier field */
    private Integer departmentId;

    /** identifier field */
    private Integer userId;

    public DepartmentUser() {
    }

    public DepartmentUser(Integer id, Integer departmentId, Integer userId) {
        this.id = id;
        this.departmentId = departmentId;
        this.userId = userId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


}
