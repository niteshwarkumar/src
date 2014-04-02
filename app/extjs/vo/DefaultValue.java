/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.extjs.vo;
import java.io.Serializable;

/**
 *
 * @author Niteshwar
 */
public class DefaultValue  implements Serializable{



    private Integer id;
    private String defaultName;
    public String defaultValue;

    public DefaultValue() {
    }

    public DefaultValue(Integer id, String defaultName, String defaultValue) {
        this.id = id;
        this.defaultName = defaultName;
        this.defaultValue = defaultValue;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }




}
